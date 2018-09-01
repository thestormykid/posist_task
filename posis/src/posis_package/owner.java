package posis_package;

import java.security.SecureRandom;
import java.util.HashSet;

public class owner {

	private String ownerName, ownerId;
	public static int totalOwner; 
	public int totalNodeData = 0;
	public HashSet<Integer> nodesWithInOwner;

	// owner constructor 
	public owner(String ownername) {
		this.ownerName = ownername;
		this.ownerId = createOwnerId();
		this.nodesWithInOwner = new HashSet<Integer>();
		totalOwner++;
	}

	// adding new node 
	public Node addNode(Node genesis, Node parent, float value) {
//		System.out.println(isFirstNode(genesis, parent));
		if (isFirstNode(genesis, parent)) {
			updateTotalownerData(value);
			Node newNode = new Node(this, genesis, parent, value);
			return newNode;
		} else if (value <= allowedValue(parent)
				&& compareWithGenesisValue(genesis, value)) {
			updateTotalownerData(value);
			Node newNode = new Node(this, genesis, parent, value);
			return newNode;
		}

		System.out
				.println("value is either less than genesis or parent value is less than the child value after adding this"
						+ value + "value");
		return null;
	}

	// condition of creation of new node
	private boolean isFirstNode(Node genesis, Node parent) {
		return genesis == null && parent == null;
	}

	// second condition of creation of new node
	private boolean compareWithGenesisValue(Node genesis, float val) {
		float genesisData = genesis.NodeData.value;
		return ((getTotalownerData() + val - genesisData) <= genesis.NodeData.value);
	}

	// third condition of creation of new node
	private float allowedValue(Node parent) {
		float parentData = parent.NodeData.value;
		int childData = 0;

		for (Node temp : parent.childNodeList) {
			childData += temp.NodeData.value;
		}

		float maximumAllowedValue = parentData - childData;
		return maximumAllowedValue;
	}
	
	// check ownership of the node
	public boolean checkOwner(Node n) {
		return this.nodesWithInOwner.contains(n.NodeData.uniqueKey);
	}

	// calucalte total node data of the owner
	private void updateTotalownerData(float data) {
		this.totalNodeData += data;
	}

	// retrival of total node data of the owner
	private int getTotalownerData() {
		return this.totalNodeData;
	}
	
	// to create new ownerId
	private String createOwnerId() {
		SecureRandom random = new SecureRandom();
		String uniqueId = random.nextInt() + "";
		return uniqueId;
	}

	// to get Owner Name
	public String ownerName() {
		return this.ownerName;
	}

	// to get ownerId
	public String ownerId() {
		return this.ownerId;
	}
}
