package bargainingchips.actions;

/**
 * An ENUM which its value is either `bid', `accept', `breakoff', or `end'.
 * 
 * BCG rules are based on a non-alternating offer protocol in each bilateral negotiation thread. 
 * 
 * The negotiating agents at both sides can send offers to each other at any time without taking any turn and they can repeat any offers previously submitted by either sides at any time. 
 * This interaction ends successfully if both agents send the same proposal to each other consecutively and mutually confirm the proposal by exchanging an accept. 
 * The agent who first sent the proposal, Accepts this proposal which was just previously resent by the other agent. 
 * This will be finalized by an Accept from the other agent to the first agent. 
 * Also, any agent can end the negotiation at any time. That is, if not Ended, they can go through several rounds of exchanging proposals until one side, 
 * e.g. the seller agent, sends a proposal repeating the proposal received from the other side, e.g. the buyer agent. 
 * In response, if the buyer agent sends an Accept message to the seller agent, this is like a pre-accept requiring to be finally-accepted by the seller agent, as well. 
 * It outcomes successfully, if the same proposal be exchanged consecutively by both parties and mutually confirmed by exchanging an Accept}. 
 * When both parties exchange Accept offers, the deal is legally binding and forcing the parties to commit. 
 * 
 * This interactions are based on some concepts including: offer, action, thread, deadline, validation, outcome, ...
 * 
 * Offers are being exchanged as actions which also describe the sender and receiver of the offer.
 * Offers are exchanged in a negotiation thread, a bilateral negotiation, under deadline.
 * The negotiation is one-to-many including parallel negotiation threads.
 * Each thread follows a non-alternating offer protocol, and conclude a deal or fails.
 * Outcome of one-to-many negotiation is aggregation of multiple deals gained by concurrent negotiation threads.
 */
public enum OfferType 
{
	BID, ACCEPT, BREAKOFF, 
	END;
}
