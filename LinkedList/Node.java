package com.company;


public class Node<T> {
    T value;
    int number_of_values;
    Node<T> next_node;

    public Node(T val, int number_of_vals) {
        value = val;
        number_of_values = number_of_vals;
        next_node = null;
    }

    public Node(T val, int number_of_vals, Node next){
        value = val;
        number_of_values = number_of_vals;
        next_node = next;
    }

    public void setNext_node(Node next_node) {
        this.next_node = next_node;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    public Node<T> getNext_node() {
        return next_node;
    }

    public void setNumber_of_values(int number_of_values) {
        this.number_of_values = number_of_values;
    }

    public int getNumber_of_values() {
        return number_of_values;
    }




}
