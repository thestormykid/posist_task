package posis_package;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Date;

public class Node {

	// data class contain all the feilds which it must contain in it;
	public class data {
		public String ownerName, ownerId;
		public float value;
		public int uniqueKey;

		public data(owner currentOwner, float value) {
			this.ownerName = currentOwner.ownerName();
			this.ownerId = currentOwner.ownerId();
			this.uniqueKey = generateKey();
			currentOwner.nodesWithInOwner.add(this.uniqueKey);
			this.value = value;
		}

		public int generateKey() {
			SecureRandom random = new SecureRandom();
			return random.nextInt();
		}

	}

	private Date currentTime;
	private Node referenceNodeId, genesisNodeId;
	public ArrayList<Node> childNodeList;
	private int nodeNumber, uniqueKey;
	private static int nodeCount;
	data NodeData;
	int nodeId;
	static int maxHeight = 0;

	// node constructor
	public Node(owner currentOwner, Node genesis, Node parent, float value) {
		this.currentTime = new Date();
		this.nodeNumber = nodeCount++;
		this.referenceNodeId = parent;
		this.genesisNodeId = genesis;
		this.childNodeList = new ArrayList<Node>();
		this.uniqueKey = generateKey();
		this.nodeId = this.uniqueKey;
		this.NodeData = new data(currentOwner, value);
	}

	// creation of encrypted key
	public int generateKey() {
		SecureRandom random = new SecureRandom();
		return random.nextInt();
	}

	// creation of new node
	public Node addNode(owner currentOwner, Node genesis, float data) {
		if (conditionOnNewNodeCreation(data, genesis, currentOwner)) {
			Node newNode = new Node(currentOwner, genesis, this, data);
			this.childNodeList.add(newNode);
			return newNode;
		} else {
			System.out
					.println("The node value which you want to create is more than the sum of children plus the value so no node will be formed try to insert less value");
			return null;
		}
	}

	// check condition before creation of new node
	private boolean conditionOnNewNodeCreation(float data, Node genesis,
			owner currentOwner) {
		float parentData = this.NodeData.value;
		float childValueData = 0;

		for (Node temp : this.childNodeList) {
			childValueData += temp.NodeData.value;
		}

		float genesisData = genesis.NodeData.value;

		float totalNodeValue = currentOwner.totalNodeData - genesisData;

		return (data <= (parentData - childValueData))
				&& ((data + totalNodeValue) <= (genesisData));
	}

	// transfer of ownerShip
	public void transferOwner(owner o1, owner o2) {
		this.NodeData.ownerName = o1.ownerName();
		this.NodeData.ownerId = o1.ownerId();
		o2.nodesWithInOwner.remove(this.NodeData.uniqueKey);
		o1.nodesWithInOwner.add(this.NodeData.uniqueKey);
	}

	// find the longest chain of genesis node or any other node;
	public int longestChain(Node n) {
		maxHeight(n, 0);
		int height = this.maxHeight;
		this.maxHeight = 0;
		return height;
	}
	
	// helper function to find the maximum longest chain.
	private static void maxHeight(Node n, int t) {
		if (n.childNodeList.size() == 0) {
			if (t > maxHeight) {
				maxHeight = t;
			}
		}

		for (Node temp : n.childNodeList) {
			maxHeight(temp, t + 1);
		}
	}

	// editing the node
	public void editNodeValue(float newValue, Node genesis, owner o1) {
		if (checkCanBeUpdatedOrNot(newValue, genesis, o1)) {
			this.NodeData.value = newValue;
			return;
		}
		
		System.out.println("the value which you want to update inside the node alters with the condition");
		return;
	}

	// checking if after updation of node value, dosen't alter the condition;
	private boolean checkCanBeUpdatedOrNot(float newValue, Node genesis,
			owner o1) {
		float parentData = referenceNodeId.NodeData.value;
		float genesisData = genesis.NodeData.value;
		float totalChildValue = 0;

		// checking if after updation of node value, the value of the children
		// shall be less than parent.
		for (Node temp : referenceNodeId.childNodeList) {
			if (temp == this) {
				totalChildValue += newValue;
			} else {
				totalChildValue += temp.NodeData.value;
			}
		}

		return (parentData >= (totalChildValue));
	}

	// merging of nodes by changing their ownership from one owner to another owner
	public void MergeTwoNodes(Node n1, Node n2, owner o1, owner o2) {
		int chain1 = n1.longestChain(n1);
		int chain2 = n2.longestChain(n2);
		
		if (chain1 < chain2) {
			n1.NodeData.ownerName = o2.ownerName();
			n1.NodeData.ownerId = o2.ownerId();
			o1.nodesWithInOwner.remove(n1.NodeData.uniqueKey);
			o2.nodesWithInOwner.add(n1.NodeData.uniqueKey);
		} else {
			n2.NodeData.ownerName = o1.ownerName();
			n2.NodeData.ownerId = o1.ownerId();
			o2.nodesWithInOwner.remove(n2.NodeData.uniqueKey);
			o1.nodesWithInOwner.add(n2.NodeData.uniqueKey);
		}
	}
	
}