package com.company;

public class List<T> {

    Node<T> head;

    public void printList() {

        Node<T> somenode;

        if (!isEmpty()) {
            System.out.print("{");
            for (somenode = head; somenode.getNext_node() != null; somenode = somenode.getNext_node()) {
                for (int i = 0; i < somenode.getNumber_of_values(); i++) {
                    System.out.print(somenode.getValue() + ";");
                }
            }
            for (int i = 0; i < somenode.getNumber_of_values(); i++) {
                System.out.print(somenode.getValue() + ";");
            }
            System.out.println("}");
        }
    }

    public void add(T value, int number_of_values) {

        Node<T> somenode;

        // if the list is empty
        if (isEmpty()) {
            head = new Node<T>(value, number_of_values);
        } else {

            for (somenode = head; somenode.getNext_node() != null && somenode.getValue() != value; somenode = somenode.getNext_node()) {
            }

            if (somenode.getValue() == value) {
                somenode.setNumber_of_values(somenode.getNumber_of_values() + number_of_values);
            } else {
                somenode.setNext_node(new Node<T>(value, number_of_values));
            }

        }

    }

    public void remove(T value, int number_of_values) {
        // if the list is empty
        if (isEmpty()) {
            return;
        } else {
            Node<T> somenode = head;
            Node<T> previous = head;

            while (somenode != null && somenode.getValue() != value) {
                previous = somenode;
                somenode = somenode.getNext_node();
            }

            if (somenode != null) {
                if (somenode == head) {
                    if (number_of_values >= somenode.getNumber_of_values()) {
                        head = somenode.getNext_node();
                    } else {
                        head.setNumber_of_values(head.getNumber_of_values() - number_of_values);
                    }
                } else {

                    if (number_of_values >= somenode.getNumber_of_values()) {
                        previous.setNext_node(somenode.getNext_node());
                    } else {
                        previous.setNumber_of_values(head.getNumber_of_values() - number_of_values);
                    }

                }
            }
        }
    }

    public void subtract(List<T> other) {
        Node<T> somenode;
        for (somenode = other.head; somenode != null; somenode = somenode.getNext_node()) {
            remove(somenode.getValue(), somenode.getNumber_of_values());
        }
    }

    public boolean isEmpty() {
        return (head == null);
    }
}
