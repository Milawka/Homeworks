package com.company;

public class Game {
    Board board;
    Agent[] agents;

    public Game(Agent[] agents) {
        this.agents = agents;
        this.board = new Board(agents);
    }

    public void play() {
        while (!finished()) {
            String next = agents[board.current_agent() - 1].get_move(this);
            if (is_valid_move(next)) {
                board = board.make_move(next);}
              }
            }

    public boolean is_valid_move(String move) {
        return  board.is_valid_move(move);
    }

    public boolean finished() {
        boolean finished = false;
        for (Agent current : agents) {
            Location location = board.location_of(current);
            if (current.get_end() == Direction.Up) {
                finished = finished || location.get_row() == 0;
            } else if (current.get_end() == Direction.Down) {
                finished = finished || location.get_row() == 8;
            }
        }
        return finished;
    }

    public Agent get_winner() {
        for (Agent current : agents) {
            Location location = board.location_of(current);
            if (current.get_end() == Direction.Up) {
                if (location.get_row() == 0) { return current; }
            }
            else if (current.get_end() == Direction.Down) {
                if (location.get_row() == 8) { return current;}
            }
        }
        return null;
    }

    public Board get_board() {
        return board.clone();
    }

}



