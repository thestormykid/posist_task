package posis_package;

import java.util.Scanner;

public class mainClass {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner scan = new Scanner(System.in);

		System.out.println("Enter the number of owners you want to create");

		int n = scan.nextInt();

		owner ownerList[] = new owner[n];

		for (int i = 0; i < n; i++) {
			System.out.println("Enter the name of the owner");
			String ownerName = scan.next();
			ownerList[i] = new owner(ownerName);
		}
		
//		testing with input of value 2 
//		Node genesis = ownerList[0].addNode(null, null, 30);
//		genesis.transferOwner(ownerList[1], ownerList[0]);
//		System.out.println(ownerList[0].checkOwner(genesis));
//		System.out.println(ownerList[1].checkOwner(genesis));

	}

}
