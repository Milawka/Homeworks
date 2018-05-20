package com.company;
import java.util.LinkedList;
import java.util.Random;

public class Random_Search {
    // create  linked-list for storing history

    static LinkedList<Node> history_first = new LinkedList<>();
    static LinkedList<Node> history_that_will_be_presented = new LinkedList<>();

    // create node "current" for storing current position
    public Node current = new Node(0,0,0);


//    // these variable for counting steps
//    public int counter = 0;


public  void start_searching_the_planet(){

    long startTime = System.currentTimeMillis();

        for (int i=0; i<100; i++){

        make_step_in_random_direction();
            current = new Node(0,0,0);


            if (history_that_will_be_presented.size()==0||history_first.size()<history_that_will_be_presented.size()){
                if (!history_that_will_be_presented.isEmpty()){
                    history_that_will_be_presented=new LinkedList<>();
                }


                while (!history_first.isEmpty()){
                history_that_will_be_presented.add(history_first.getFirst());
                    history_first.removeFirst();
            }
            }
            history_first = new LinkedList<>();
            // create node "current" for storing current position
            Node current = new Node(0,0,0);


        }
    System.out.println(history_that_will_be_presented.size());
     while (!history_that_will_be_presented.isEmpty()){
        System.out.println(history_that_will_be_presented.getFirst().x + " " + history_that_will_be_presented.getFirst().y + " " + history_that_will_be_presented.getFirst().z);
  history_that_will_be_presented.removeFirst();
    }
    System.out.println(System.currentTimeMillis()-startTime + " msec");

}
    public  void make_step_in_random_direction(){

        history_first.add(new Node(current));


// spaceship explore the route, to find the planet
        while(!Data_from_file.planets.contains(current)){

            // there I try to emulate random decision for making step
            final Random random_object = new Random();
            // as spaceship in 3D space, he might go in 6 different directions: up, down, left, right, forward and backward
            int random_number = random_object.nextInt(6); //it returns number from 0 to 5 (include)
            // depending on what number we get, spaceship do step
            if (random_number==0) {current.x = current.x+1; }

            if (random_number==1) {current.y = current.y+1;  }

            if (random_number==2){ current.z = current.z+1; }

            if (random_number==3){current.x = current.x-1; }

            if (random_number==4){current.y = current.y-1; }

            if (random_number==5) {current.z = current.z-1;  }



            // check that new location of spaceship is not go out the exploring area
            // if these happen(go out the area), then return to the valid location (previous)
            if (current.x>100 ||current.y>100 || current.z>100 || current.x<0 || current.y<0 || current.z<0){
                if (random_number==0) {current.x = current.x-1;  }

                if (random_number==1) {current.y = current.y-1;  }

                if (random_number==2){ current.z = current.z-1; }

                if (random_number==3){current.x = current.x+1; }

                if (random_number==4){current.y = current.y+1; }

                if (random_number==5) {current.z = current.z+1; }


            }  // check that spaceship is not get into the kraken or black hole
            else if(Data_from_file.krakens.contains(current)||Data_from_file.black_holes.contains(current)){
                current=new Node(0,0,0);
            } // these means that everything is okay, and spaceship continue his searching
            else {
                history_first.add(new Node(current));

            }
        }
    }
}





