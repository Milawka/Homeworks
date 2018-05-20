package com.company;

/**
 * Created by inno on 2/16/2017.
 */
public class Node {

    public int x;
    public int y;
    public int z;

   public Node parent;

    // constructor
    Node(int x_income, int y_income, int z_income){
        x=x_income;
        y=y_income;
        z=z_income;
    }

    // copy constructor
    Node(Node another_node){
        this.x = another_node.x;
        this.y = another_node.y;
        this.z = another_node.z;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setZ(int z) {
        this.z = z;
    }

    public int getX() {
        return x;
    }

    public int getZ() {
        return z;
    }

    public int getY() {
        return y;
    }

    // method for comparing nodes and also for right working of "contains" method of linked list
    public boolean equals(Object node1){

        if (this.x==((Node) node1).getX() && this.y==((Node) node1).getY() && this.z==((Node) node1).getZ()){
            return true;
        } else {
            return false;
        }
    }

    public void print(){
        System.out.println(x+" "+y+" "+z);
    }

}
