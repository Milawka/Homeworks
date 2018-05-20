package com.company;

public class Main {

    public static void main(String[] args) {
	List<String> temp = new List<>();
	temp.add("aa", 3);

	temp.add("mi", 1);
	temp.printList();

	temp.add("aa", 2);
	temp.printList();

    temp.remove("aa", 2);
    temp.printList();

	temp.add("bb", 2);
	temp.printList();


	List<String> temp2 = new List<>();
	temp2.add("aa", 2);
	temp2.add("bb", 2);

	temp.subtract(temp2);
	temp.printList();
    }
}
