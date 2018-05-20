package com.company;

import java.util.*;
import java.util.List;

public class Agent {
        Direction direction;
        int walls;
        int id;   // this is indicate player
        boolean initialised = false;
        boolean is_mini_max;
        boolean is_alpha_beta;
        Random rand = new Random();
        int depth;

        public Agent(String сhoose_algorythm, int enter_depth){
        if (сhoose_algorythm == "mini_max"){ this.is_mini_max=true; this.depth = enter_depth;}
        if (сhoose_algorythm == "alpha_beta"){ this.is_alpha_beta=true; this.depth = enter_depth;}
         }

         // we use it when we initialise game
        public final void initialise(int id, Direction d) {
            if (!initialised) {
                this.id = id;
                this.direction = d;  // target direction
                this.walls = 10;
                this.initialised = true;
                this.on_creation();
            }
        }

        // return target direction, also direction of the wall
        public final Direction get_end() {
            return this.direction;
        }

        public final Integer get_id() {
            return id;
        }

        // remaining walls
        public final Integer get_num_walls() {
            return walls;
        }

        public final void use_wall() {
            walls--;
        }

        //to get the player's next move from a given certain game state.
        public final String get_move(Game g) {
            String move = this.next_move(g.get_board());
            while (!g.is_valid_move(move) || (move.length() == 3 && get_num_walls() == 0)) {
                on_failure(g.get_board());
                move = this.next_move(g.get_board());
            }
            if (move.length() == 3) { use_wall();}
            return move;
        }

        protected void on_creation() {}

        protected void on_failure(Board b) { System.out.println("Invalid move!"); }

        private String next_move(Board b){
            String return_value = "";
            if (this.is_mini_max) { return_value = mini_max(b).toString();}
            else if (this.is_alpha_beta){ return_value = alpha_beta(b).toString();}
            b.print_board();
            return return_value;
             }

        public Location mini_max(Board board) {
            List<Integer> players = board.get_agents();
            while (players.get(0) != this.get_id()) {
                players.add(players.remove(0)); // leave opponent in the list
            }
                List<Location> moves = generate_moves(board);
                Location max_move = moves.remove(0);
                int max_score = mini_max_algorythm(players, this.get_id(), board.make_move(max_move.toString()), 0);
                for (Location current : moves) {
                    int temp = mini_max_algorythm(players, this.get_id(), board.make_move(current.toString()), 0);
                    if (temp > max_score) {
                        max_score = temp;
                        max_move = current;
                        break;
                    }
                }
                return max_move;
            }

    public Location alpha_beta(Board board) {
        List<Integer> players = board.get_agents();
        while (players.get(0) != this.get_id()) {
            players.add(players.remove(0));// opponent
        }
        List<Location> moves = generate_moves(board);
        Location maxMove = moves.remove(0);
        int max_score = alpha_beta_algorythm(players, this.get_id(), board.make_move(maxMove.toString()), 0, -1000000, +1000000);
        for (Location current : moves) {
            int temp = alpha_beta_algorythm(players, this.get_id(), board.make_move(current.toString()), 0, -1000000, +1000000);
            if (temp >= max_score) {
                max_score = temp;
                maxMove = current;
                break;
            }
        }
        return maxMove;
    }

    public Integer mini_max_algorythm(List<Integer> players, Integer max, Board state, Integer level ) {
        Integer p = players.remove(0);
        players.add(p);
        int v;
        int best_value = 0; // score
        if (level == depth) { return score(state, p); }
        else {
            List<Location> moves = generate_moves(state);
            if (p.equals(max)) {
                for (Location next : moves) {
                    v = mini_max_algorythm(players, max, state.make_move(next.toString()), level+1);
                      if ( v > best_value ) { best_value= v; }
                         }
                return best_value;
            }
            else {
              best_value = 1000000;
                for (Location next : moves) {
                   v = mini_max_algorythm(players, max, state.make_move(next.toString()), level+1);
                        if (v < best_value) {best_value = v;}
                    }
                return best_value;
            }
        }
    }

    //Give heuristic score for a given board state from a given player
    public Integer score(Board state, int player){ // id 1 or 2
            Location move_of_current = get_shortest_path(state, player);
        List<Integer> other = state.get_agents();
        other.remove(player-1); // remove current player to leave in the list opponent
        int move_of_other_player = get_shortest_path(state, other.get(0)).path_length();
        return (move_of_other_player-move_of_current.path_length());
    }

        // generate possible moves for player
        public List<Location> generate_moves(Board state) {
            int player = state.current_agent();
            // for current position all possible steps
            List<Location> moves = state.valid_moves(state.location_of(player));
            if (state.remaining_walls(player) > 0) {
                // there if walls>0 generate all possible walls
                for (char i = '1'; i < '9'; i++) {
                    for (char j = '1'; j < '9'; j++) {
                        Location horizontal_state = new Location(String.valueOf(j) + String.valueOf(i) + "h");
                        Location vertical_state = new Location(String.valueOf(j) + String.valueOf(i) + "v");
                        if (state.is_valid_move(horizontal_state.toString())) {
                            moves.add(horizontal_state);
                        }
                        if (state.is_valid_move(vertical_state.toString())) {
                            moves.add(vertical_state);
                        }
                    }
                }
            }
         if (moves.size() > 2) {
                moves.remove(rand.nextInt(moves.size()));
            }
            return moves;
        }

        public Location get_shortest_path(Board board, int player_id) {
            Queue<Location> locations = new LinkedList<Location>(); // here store steps
            LinkedList<Location> visited = new LinkedList<Location>(); // for checking, for not to be twice in the same place
            Location current_location = board.location_of(player_id);
            boolean finished = false;
            locations.add(current_location);
            visited.add(current_location);
            while (!locations.isEmpty() && !finished) {
                current_location = locations.remove();
                // берём все валидные шаги с текущей позиции
                for (Location position : board.valid_moves(current_location)) {
                    if (!visited.contains(position)) {
                        position.parent = current_location;
                        visited.add(position);
                        locations.add(position);
                        if (finished(position, player_id)) {
                            current_location = position;
                            finished = true;
                            break;
                        }
                    }
                }
            }

            return current_location;
        }

        // check if the game is finished
        public boolean finished(Location p, Integer pl) {
            if (pl == 1 && p.get_row() == 0) { return true;}
            else if (pl == 2 && p.get_row() == 8) { return true; }
            return false;
        }

        public Integer alpha_beta_algorythm(List<Integer> players, Integer max, Board state, Integer level, Integer alpha, Integer beta) {
            Integer p = players.remove(0);
            players.add(p);
            int score = 0;

            if (level == depth) {
                return score(state, p);
            } else {
                List<Location> moves = generate_moves(state);
                if (p.equals(max)) {
                    for (Location next : moves) {
                        score = alpha_beta_algorythm(players, max, state.make_move(next.toString()), level + 1, alpha, beta);
                        if (score > alpha) { alpha = score;}
                        if (beta <= alpha) {break;}
                    }
                    return alpha;
                } else {
                    for (Location next : moves) {
                        score = alpha_beta_algorythm(players, max, state.make_move(next.toString()), level + 1, alpha, beta);
                        if (score < beta) {beta = score;}
                        if (beta <= alpha) {break;}
                    }
                    return beta;
                }
            }
        }

    @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + id;
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null) return false;
            if (getClass() != obj.getClass()) return false;
            Agent other = (Agent) obj;
            if (id != other.id) return false;
            return true;
        }
    }

