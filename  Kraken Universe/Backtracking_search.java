package com.company;

import java.util.LinkedList;
import java.util.Stack;

public class Backtracking_search {

    // it will our working position
  static Node current_position = new Node(0,0,0);
    static Node root = new Node(0,0,0);

    // there we will store nodes,  where spaceship already been
    static LinkedList<Node> is_visited = new LinkedList<>();

    // there we will store nodes, where spaceship plans to go, but they(points) will go to stack, only if they(points) are safe
    // (I mean it is not Kraken or Black hole, also that means that the node in valid space (not go out),
    // and also this means that it is a new node (we don't visit this point before))
    // I use stack to emulate return
    static Stack<Node> stack = new Stack();

    static Stack<Node> resulted_path = new Stack<>();

    public static void print_solution(){
        System.out.println();
    }


    public static void make_step(){
        long startTime = System.currentTimeMillis();

        //current position is safe from the conditions, that's why we push it onto the stack
        stack.push(current_position);

        //spaceship exploring the space for searching planet
        while (!Data_from_file.planets.contains(current_position)){

// every time the spaceship will took the current position (the point with which we work) from the stack, because we put there only safe coordinats

            current_position = new Node(stack.pop());
// as spacechip in current position, this means that he already check it's safety, and for the future this will means that it was there
            is_visited.add(new Node(current_position));

//check movement to the up: check if that point are it the space, that we explore
            if (current_position.y+1<=100){

                //create new node for easily adding to the stack
                Node want_go_up = new Node(current_position.x, current_position.y+1, current_position.z);
                //check was the spaceship there before
                if (!is_visited.contains(want_go_up)){
                    // check that this point is not kraken and not black hole
                    if (!Data_from_file.krakens.contains(want_go_up) && !Data_from_file.black_holes.contains(want_go_up)){
                        // if this point pass all checks, then it is safe, so add to the stack, for future exploration
                        stack.push(want_go_up);
                    }
                }
            }
            if (current_position.y-1>=0){
                Node want_go_down = new Node(current_position.x, current_position.y-1, current_position.z);   //create new node for easily adding to the stack

                //check was the spaceship there before
                if (!is_visited.contains(want_go_down)){
                     if (!Data_from_file.krakens.contains(want_go_down) && !Data_from_file.black_holes.contains(want_go_down)){
// check that this point is not kraken and not black hole
                            stack.push(want_go_down); // if this point pass all checks, then it is safe, so add to the stack, for future exploration
                    }
                }
            }
            if (current_position.z+1<=100){
                Node want_go_forward = new Node(current_position.x, current_position.y, current_position.z+1);  //create new node for easily adding to the stack

                //check was the spaceship there before
                if (!is_visited.contains(want_go_forward)){
                    if (!Data_from_file.krakens.contains(want_go_forward) && !Data_from_file.black_holes.contains(want_go_forward)){
                        // check that this point is not kraken and not black hole

                        stack.push(want_go_forward);// if this point pass all checks, then it is safe, so add to the stack, for future exploration

                    }
                }
            }
            if (current_position.z-1>=0){
                Node want_go_backward = new Node(current_position.x, current_position.y, current_position.z-1); //create new node for easily adding to the stack
                //check was the spaceship there before
                if (!is_visited.contains(want_go_backward)){
                    if (!Data_from_file.krakens.contains(want_go_backward) && !Data_from_file.black_holes.contains(want_go_backward)){
// check that this point is not kraken and not black hole

                        stack.push(want_go_backward);// if this point pass all checks, then it is safe, so add to the stack, for future exploration

                    }
                }
            }
            if (current_position.x+1<=100){
                Node want_go_right = new Node(current_position.x+1, current_position.y, current_position.z); //create new node for easily adding to the stack
                //check was the spaceship there before
                if (!is_visited.contains(want_go_right)){
                    if (!Data_from_file.krakens.contains(want_go_right) && !Data_from_file.black_holes.contains(want_go_right)){
                        // check that this point is not kraken and not black hole

                        stack.push(want_go_right);// if this point pass all checks, then it is safe, so add to the stack, for future exploration

                    }
                }
            }
            if (current_position.x-1>=0){
                Node want_go_left = new Node(current_position.x-1, current_position.y, current_position.z); //create new node for easily adding to the stack
                //check was the spaceship there before
                if (!is_visited.contains(want_go_left)){
                    if (!Data_from_file.krakens.contains(want_go_left) && !Data_from_file.black_holes.contains(want_go_left)){// check that this point is not kraken and not black hole
                        stack.push(want_go_left);// if this point pass all checks, then it is safe, so add to the stack, for future exploration

                    }
                }
            }
        }
        System.out.println(is_visited.size());
        while (!is_visited.isEmpty()) {
           System.out.println(is_visited.getFirst().x+" "+is_visited.getFirst().y+" "+is_visited.getFirst().z);
            is_visited.removeFirst();
        }
        System.out.println(System.currentTimeMillis()-startTime + " msec");




    }

}

