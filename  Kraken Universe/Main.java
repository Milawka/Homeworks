package com.company;

import java.io.File;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {


        try {
            Scanner in = new Scanner(new File("input.txt"));
            while (in.hasNext()) {
                String input = in.next();
                if (input.equals("B")) {
                    Data_from_file.black_holes.add(new Node(in.nextInt(), in.nextInt(), in.nextInt()));
                } else if (input.equals("K")) {
                  Data_from_file.krakens.add(new Node(in.nextInt(), in.nextInt(), in.nextInt()));
                } else if (input.equals("P")) {
                   Data_from_file.planets.add(new Node(in.nextInt(), in.nextInt(), in.nextInt()));
                }

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

//Backtracking_search.make_step();
        Random_Search random_search_class = new Random_Search();
random_search_class.start_searching_the_planet();




    }}
