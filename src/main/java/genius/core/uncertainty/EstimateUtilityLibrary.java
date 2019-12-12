package genius.core.uncertainty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import genius.core.Domain;
import genius.core.issue.Issue;
import genius.core.issue.IssueDiscrete;
import genius.core.uncertainty.Helper_KS_Movement.Movement;
import genius.core.utility.AbstractUtilitySpace;
import genius.core.utility.AdditiveUtilitySpace;
import genius.core.parties.AbstractNegotiationParty;
import java.util.Random;

/**
 * This class intends to serve as a library for different estimateUtilitySpace functions. Although a default one is used 
 * under preference uncertainty, there are many more ways to estimate a utility space from a bid ranking which we fully collect here.
 * @author Adel Magra
 */

public class EstimateUtilityLibrary {

	/**
	 * The domain and bid ranking over which the utility space is to be estimated.
	 */
	private Domain domain;
	private BidRanking bidRank;
	public EstimateUtilityLibrary(Domain domain, BidRanking bidRank){
		this.domain = domain;
		this.bidRank = bidRank;
	}
	
	/**
	 * The estimateUtilitySpace function of the agent "KakeSoba". Uses helper functions from the Helper_KS_Movement class.
	 */
	public AdditiveUtilitySpace kS_Movement(){
		Helper_KS_Movement helper = new Helper_KS_Movement(domain, bidRank);
		List<Movement> TabuList = new ArrayList<Movement>();
		AdditiveUtilitySpace additiveUtilitySpace = helper.generateRandomUtilitySpace();
		AdditiveUtilitySpace hallOfFame = additiveUtilitySpace;
		double hallOfFameScore = helper.getScore(hallOfFame, false);

		int domainSize = 0;
		for (Issue issue : domain.getIssues()) {
			domainSize += ((IssueDiscrete) issue).getValues().size() + 1;
		}

		int numOfMovement = 5000;
		final double wightRate = domain.getIssues().size() * 1.0D / domainSize;

		for (int i = 0; i < numOfMovement; i ++) {
			Map<Movement, AdditiveUtilitySpace> moveToNeighbors = new HashMap<Movement, AdditiveUtilitySpace>();

			for (int j = 0; j < domainSize; j ++) {
				Movement movement = helper.new Movement(domain, wightRate);
				while (TabuList.contains(movement)) {
					movement = helper.new Movement(domain, wightRate);
				}
				moveToNeighbors.put(movement, helper.getNeighbor(additiveUtilitySpace, movement));
			}

			Iterator<Map.Entry<Movement, AdditiveUtilitySpace>> iterator = moveToNeighbors.entrySet().iterator();
			Map.Entry<Movement, AdditiveUtilitySpace> bestEntry = iterator.next();
			double bestScore = -100.0D;
			while (iterator.hasNext()) {
				Map.Entry<Movement, AdditiveUtilitySpace> entry = iterator.next();
				double score = helper.getScore(entry.getValue(), false);
				if (score > bestScore) {
					bestEntry = entry;
					bestScore = score;
				}
			}

			additiveUtilitySpace = bestEntry.getValue();
			if (bestScore > hallOfFameScore) {
				hallOfFame = additiveUtilitySpace;
				hallOfFameScore = bestScore;
			}

			TabuList.add(bestEntry.getKey());
			if (TabuList.size() > Math.sqrt(domainSize) / 2) {
				TabuList.remove(0);
			}
		}
		return hallOfFame;
	}
			
	/**
	 * The estimateUtility function of the agent "SAGA". Uses helper functions from the Helper_SAGA class.
	 */
	protected Random rnd = new Random();
	public AbstractUtilitySpace fitnessCross_SAGA() {
		Helper_SAGA helper = new Helper_SAGA(domain, bidRank, rnd);
        int popSize = 500;  // 集団サイズ
        int maxGeneration = 200;   // 打ち切り世代数
        double crossRate = 3.0; // 交叉回数 = popSize * crossRate

        // 初期集団の生成
        List<AbstractUtilitySpace> population = new ArrayList<>();
        for (int i = 0; i < popSize * (1.0 + crossRate); i++) {
            population.add(helper.generateRandomUtilitySpace());
        }

        // maxGeneration世代まで繰り返す
        for (int gen = 0; gen < maxGeneration; gen++) {
            ////System.out.print("gen " + gen + ": ");

            // 適応度関数の計算
            List<Double> fitnessList = new ArrayList<>();
            for (AbstractUtilitySpace ind : population) {
                fitnessList.add(helper.fitness(ind, false));
            }

            // ルーレット選択
            population = helper.selectByRoulette(population, fitnessList, popSize);

            // 交叉と突然変異
            int parentSize = population.size();
            for (int i = 0; i < popSize * crossRate; i++) {
                AbstractUtilitySpace parent1 = population.get(rnd.nextInt(parentSize));
                AbstractUtilitySpace parent2 = population.get(rnd.nextInt(parentSize));
                AbstractUtilitySpace child = helper.crossover((AdditiveUtilitySpace) parent1, (AdditiveUtilitySpace) parent2);
                population.add(child);
            }
        }

        // 適応度関数の計算
        List<Double> fitnessList = new ArrayList<>();
        for (AbstractUtilitySpace ind : population) {
            fitnessList.add(helper.fitness(ind, false));
        }

        // 適応度が最大の要素を求める
        double maxFit = -1.0;
        int maxIndex = -1;
        for (int i = 0; i < population.size(); i++) {
            if (fitnessList.get(i) > maxFit) {
                maxFit = fitnessList.get(i);
                maxIndex = i;
            }
        }

        ////System.out.println("最終結果:\n" + population.get(maxIndex).toString());

//        // テスト用
//        sortedOS = new SortedOutcomeSpace(population.get(maxIndex));

        return population.get(maxIndex);
    }
	
	/**
	 * This is the default estimate utility function.
	 */
	public AbstractUtilitySpace default_estimation(){
		UserModel um = new UserModel(this.bidRank);
		AbstractUtilitySpace utilspace = AbstractNegotiationParty.defaultUtilitySpaceEstimator(this.domain,um);
		return utilspace;
	}
	
}
