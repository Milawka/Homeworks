package com.company;

public class Cell {
    int  column;
    int row;
    Agent agent_inside_this_cell;
    Cell[] neighbor;

    public Cell(int row, int column){
        this.column = column;
        this.row = row;
        this.neighbor = new Cell[4];
    }

    public void set_player(Agent agent){
        this.agent_inside_this_cell = agent;
    }

    public void set_neighbor(Direction direction, Cell cell){
        neighbor[direction.index()] = cell;
    }

    public Agent get_player_inside_this_cell() {return this.agent_inside_this_cell;}

    public Cell get_neighbor(Direction direction) {
        return neighbor[direction.index()];
    }

    public Location get_position_of_cell() {  
        return new Location(this.row, this.column);
    }
}
