package com.company;

public class MyClass {

        static int[] values = {10,120,100,10,60};
        static int[] weights = {5,30,5,20,10};

        static int capacity = 50; 
        static int summary_value_of_a_knapsack = 0;  //what we need to find
        static int max = -1;
        static int number_in_array = -1;

    public static void find_Max() {
            // find max
            for (int i = 0; i < values.length; i++) {
                if (values[i] > max) {
                    max = values[i];
                    number_in_array = i;
                }
            }
        }

    public static void find_the_answer(){
            while (capacity!=0) {
                // check if its weight fit to the knapsack capacity, if yes perform some actions
                if ((capacity - weights[number_in_array]) >= 0) {
                    capacity = capacity - weights[number_in_array];
                    summary_value_of_a_knapsack =  summary_value_of_a_knapsack + max;
                }
                // "delete" those elements from array for not to interfere in searching in the future"
                values[number_in_array] = 0;
                weights[number_in_array] = 0;
                max=-1;
               find_Max();
                // we have some max value of item, but weight of this item is too big for knapsack
                // that's why we should exclude them from our searching list and continue searching 
                if ((capacity - weights[number_in_array])<0){
                    values[number_in_array] = 0;
                    weights[number_in_array] = 0;
                    max=0;
                }
                // if there is place in knapsack, but not enough, as none of the remaining things not fit
                //we should end searching
                if (max==0){
                    break;
                }
            }
        }
    }

///////////////////////////////////////////////////////////////////////////////

package com.company;

public class Main {
    public static void main(String args[]){
        MyClass.find_Max();
        MyClass.find_the_answer();
        System.out.println(MyClass.summary_value_of_a_knapsack);

    }
}

