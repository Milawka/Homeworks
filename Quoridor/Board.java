package com.company;

import java.util.List;
import java.util.LinkedList;
import java.util.Queue;

public class Board {
     int current_agent = 0;
     Cell[][] cells;
     Agent[] agents;
     List<String> history;

    public void create_board() {
        cells = new Cell[9][9];
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells.length; j++) {
                cells[i][j] = new Cell(i, j);
            }
        }
        for (int i = 1; i < cells.length; i++) {
            for (int j = 0; j < cells.length; j++) {
                cells[i][j].set_neighbor(Direction.Up, cells[i - 1][j]);
            }
        }
        for (int i = 0; i < cells.length - 1; i++) {
            for (int j = 0; j < cells.length; j++) {
                cells[i][j].set_neighbor(Direction.Down, cells[i + 1][j]);
            }
        }
        for (int i = 0; i < cells.length; i++) {
            for (int j = 1; j < cells.length; j++) {
                cells[i][j].set_neighbor(Direction.Left, cells[i][j - 1]);
            }
        }
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells.length - 1; j++) {
                cells[i][j].set_neighbor(Direction.Right, cells[i][j + 1]);
            }
        }
    }

    public Board(Agent[] agents) {
        this.history = new LinkedList<String>();
        this.agents = agents;
        create_board();
        Direction[] defaults = { Direction.Up, Direction.Down };
        Location[] positions_of_players = { new Location(8, 4), new Location(0,4)};
        for (int i = 0; i < agents.length; i++) {
            agents[i].initialise(i + 1, defaults[i] );
            Location cell_will_be_with_player = positions_of_players[i];
            cells[cell_will_be_with_player.get_row()][cell_will_be_with_player.get_column()].set_player(agents[i]);
        }
    }

    private Board(Agent[] agents, List<String> history) {
        this.history = new LinkedList<String>();
        this.agents = agents;
        create_board();
        Direction[] defaults = { Direction.Up, Direction.Down };
        Location[] positions_of_players = { new Location(8, 4), new Location(0, 4)};
        for (int i = 0; i < 2; i++) {
            Location m = positions_of_players[i];
            cells[m.get_row()][m.get_column()].set_player(agents[i]);
        }
        if (history.size()!=0) {
            for (String move : history) {
                Location p = new Location(move.length() == 3 ? move : move.split(" ")[1]);
                this.move(p);
            }
        }
    }

    // accept id of the player
    public int remaining_walls(int player) {
        return agents[player - 1].get_num_walls();
    }

    // return id of the current player
    public int current_agent() {
        return agents[current_agent].get_id();
    }

    public Location location_of(Agent agent) {
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells.length; j++) {
                if (agent.equals(cells[i][j].get_player_inside_this_cell())) { return new Location(i, j); }
            }
        }
        return null;
    }

    // return a location of a player (we gave to the method player's id)
    public Location location_of(int id) {
        return location_of(agents[id - 1]);
    }

    //Get the id of the player at the specified location
    public Integer agent_at(Location p) {
        Agent playerr = cells[p.get_row()][p.get_column()].agent_inside_this_cell;
        if (playerr != null) { return playerr.get_id(); }
        else { return null; }
    }

    // return id of all players
    public List<Integer> get_agents() {
        List<Integer> agents = new LinkedList<Integer>();
        for (Agent agent : this.agents) {
            agents.add(agent.get_id());
        }
        return agents;
    }

    // Checking of existing a wall at specified location and d is  direction to check
    public boolean wall_exists(Location origin_cell, Direction d) {
        return cells[origin_cell.get_row()][origin_cell.get_column()].get_neighbor(d) == null;
    }

    //check if the board can be flooded
    public boolean flood() {
        LinkedList<Cell> visited = new LinkedList<Cell>();
        Queue<Cell> queue = new LinkedList<Cell>();
        queue.add(cells[0][0]);
        visited.add(cells[0][0]);
        Cell current;
        Cell temp;
        while (!queue.isEmpty()) {
            current = queue.remove();
            for (Direction dir : Direction.values()) {
                temp = current.get_neighbor(dir);
                if (temp != null && !visited.contains(temp)) {
                    visited.add(temp);
                    queue.add(temp);
                }
            }
        }
        return visited.size() == 81;
    }

    //  Give a list where the agent could go
    public List<Location> valid_moves(Location location) {
        LinkedList<Location> adjacent = new LinkedList<Location>();
        LinkedList<Location> list_of_moves = new LinkedList<Location>();
        LinkedList<Direction> list_with_directions = new LinkedList<Direction>();
        Cell current = cells[location.get_row()][location.get_column()];
        Cell neighbour;
        Direction directionn;
        for (Direction dir : Direction.values()) {
            neighbour = current.get_neighbor(dir);
            if (neighbour != null) {
                adjacent.add(neighbour.get_position_of_cell());
                list_with_directions.add(dir);
            }
        }
        for (Location p : adjacent) {
            directionn = list_with_directions.removeFirst();
            if (agent_at(p) != null) {
                for (Location pos: jump(p, directionn, false)) {
                    list_of_moves.add(pos);
                }
            } else { list_of_moves.add(p); }
        }
        return list_of_moves;
    }

    // implementation of 'jump' rule
    public Location[] jump(Location p, Direction d, boolean giveUp) {
        if (!wall_exists(p, d) && agent_at(p.adjacent_square(d)) == null) {
            Location[] target_square = new Location[1];
            target_square[0] = p.adjacent_square(d);
            return target_square;
        } else if (giveUp) {
            Location[] target = new Location[0];
            return target; // yes, it is an empty massive (for not writing null)
        } else {
            LinkedList<Location> moves = new LinkedList<Location>();
            for (Direction dir : Direction.values()) {
                if (!dir.equals(d) && !dir.equals(d.reverse())) {
                    for (Location pos : jump(p, dir, true)) {
                        moves.add(pos);
                    }
                }
            }
            Location target[] = new Location[moves.size()];
            return moves.toArray(target);
        }
    }

    //  Check if a wall or move  is a valid step
    public boolean is_valid_move(Location m) {
        boolean validity = true;
        if (m.is_wall() && agents[current_agent].get_num_walls() >= 0) {
            Cell northwest = cells[m.get_row()][m.get_column()];
            Cell northeast = cells[m.get_row()][m.get_column() + 1];
            Cell southwest = cells[m.get_row() + 1][m.get_column()];
            // check if there neighbours
            if (m.get_orientation() == Location.Wall.Vertical) {
                validity = validity && northwest.get_neighbor(Direction.Right) != null;
                validity = validity && southwest.get_neighbor(Direction.Right) != null;
                validity = validity && !(northwest.get_neighbor(Direction.Down) == null && northeast.get_neighbor(Direction.Down) == null);
            }
            else if (m.get_orientation() == Location.Wall.Horizontal) {
                validity = validity && northwest.get_neighbor(Direction.Down) != null;
                validity = validity && northeast.get_neighbor(Direction.Down) != null;
                validity = validity && !(northwest.get_neighbor(Direction.Right) == null && southwest.get_neighbor(Direction.Right) == null);
            }
            else { validity = false;}
            if (validity) {
                // Test if we can flood the board
                Board test = clone();
                test.place_wall(m);
                if (!test.flood()) { validity = false;}
            }
        } else { // if it is not wall or there not enough walls
            List<Location> adjacent = valid_moves(location_of(agents[current_agent]));
            validity = validity && adjacent.contains(m);
        }
        return validity;
    }

    //Check if move is valid
    public boolean is_valid_move(String m) {
        return is_valid_move(new Location(m));
    }

    // Create a copy of the board with the move applied.
    public Board make_move(String move) {
            Location m;
            try {
                m = new Location(move);
            } catch (IllegalArgumentException e) {
                return null; // means that move is invalid
            }
            if (is_valid_move(m)) {
                Board return_value = clone();
                return_value.move(m);
                return return_value; // resulting board
            }
        return null;
    }

    // Apply a move to the current board
    public void move(Location m) {
        if (m.is_wall()) {
            history.add(m.toString());
            place_wall(m);
        } else {
            history.add(location_of(agents[current_agent]).toString() + " " + m.toString());
            place_move(m);
        }
        current_agent = (current_agent + 1) % agents.length;
    }

    //  Move the current player to the specified location.
    public void place_move(Location m) {
        Agent p = agents[current_agent];
        Location from = location_of(p);
        cells[from.get_row()][from.get_column()].set_player(null);
        cells[m.get_row()][m.get_column()].set_player(p);
    }

    // Place a wall at the specified location.
    public void place_wall(Location location) {
        Cell northwest = cells[location.get_row()][location.get_column()];
        Cell northeast = cells[location.get_row()][location.get_column() + 1];
        Cell southwest = cells[location.get_row() + 1][location.get_column()];
        Cell southeast = cells[location.get_row() + 1][location.get_column() + 1];
        if (location.get_orientation() == Location.Wall.Vertical) {
            northwest.set_neighbor(Direction.Right, null);
            northeast.set_neighbor(Direction.Left, null);
            southwest.set_neighbor(Direction.Right, null);
            southeast.set_neighbor(Direction.Left, null);
        }
        else {
            northwest.set_neighbor(Direction.Down, null);
            northeast.set_neighbor(Direction.Down, null);
            southwest.set_neighbor(Direction.Up, null);
            southeast.set_neighbor(Direction.Up, null);
        }
    }

    public void print_board() {
        for (Agent current : agents) {
            System.out.println("Agent " + current.get_id().toString() +  " has " + current.get_num_walls().toString() + " walls remaining.");

        }
        System.out.println(" [4m 1 2 3 4 5 6 7 8 9 [24m");
        for (int i = 0; i < cells.length; i++) {
            System.out.print(i + 1);
            System.out.print("|");
            for (Cell cell : cells[i]) {
                String cell_name;
                if (cell.get_player_inside_this_cell() == null) {
                    cell_name = " ";
                } else {
                    cell_name = cell.get_player_inside_this_cell().get_id().toString();
                    if (cell.get_player_inside_this_cell().get_id() == current_agent()) {
                        cell_name = "[1m" + cell_name + "[0m";
                    }
                }
                if (cell.get_neighbor(Direction.Down) != null && i != 8) {
                    cell_name = "[24m" + cell_name;
                } else {
                    cell_name = "[4m" + cell_name;
                    if (cell.get_player_inside_this_cell() != null && cell.get_player_inside_this_cell().get_id() == current_agent()) {
                        cell_name += "[4m";
                    }
                }
                if (cell.get_neighbor(Direction.Right) == null) {
                    cell_name += "|";
                } else if (i != 8) {
                    cell_name += ".";
                } else {
                    cell_name += " ";
                }
                System.out.print(cell_name);
            }
            System.out.println("[24m");
        }
        System.out.println("It is " + agents[current_agent].get_id() + "'s turn.");

    }

    public Board clone() {
        return new Board(agents, history);
    }
}