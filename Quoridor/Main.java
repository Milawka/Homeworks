package com.company;

import java.io.IOException;

public class Main {


        private static Game game;

    public static void main (String[] argv) throws IOException {
        Integer size = 2;

        Agent[] agents;

        agents = new Agent[size];
                            // enter "mini_max" or "alpha_beta"  and then int value (which will indicate depth)
        agents[0]=new Agent("mini_max", 1);

      agents[1]=new Agent("mini_max", 1);


            game = new Game(agents);
           game.play();
            game.get_board().print_board();
            System.out.println("Agent " + game.get_winner().get_id() + " wins!");



    }
}


