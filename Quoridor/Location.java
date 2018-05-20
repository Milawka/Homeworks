package com.company;

public class Location {
    public enum Wall { Horizontal, Vertical }
    int row;
    int column;
    boolean is_wall;
    Wall orientation;
    Location parent;


    public Location(String move) throws IllegalArgumentException {
        if (!is_valid_move_string(move)) {throw new IllegalArgumentException("Invalid move ");}
        this.column = move.charAt(0)- '1' ; // потому что программа и юзер по-разному видят доску
        this.row = move.charAt(1)- '1' ;
        this.is_wall = false;
        if (move.length() == 3) {
            this.is_wall = true;
            this.orientation = move.charAt(2) == 'h' ? Wall.Horizontal : Wall.Vertical;
        }
    }

    public Location(int row, int col) {
        this.row = row;
        this.column = col;
        this.is_wall = false;
    }

    public Location adjacent_square(Direction d) {
        int new_row = this.row;
        int new_column = this.column;
        if (d == Direction.Down) new_row++;
        else if (d == Direction.Up) new_row--;
        else if (d == Direction.Left) new_column--;
        else if (d == Direction.Right) new_column++;
        return new Location(new_row, new_column);
    }

    public boolean is_valid_move_string(String move) {
        boolean validity = (move.length() == 2 || move.length() == 3);
        validity = validity && move.charAt(0) >= '1' && move.charAt(0) <= '9';
        validity = validity && move.charAt(1) >= '1' && move.charAt(1) <= '9';
        if (move.length() == 3) {
            validity = validity && (move.charAt(2) == 'v' ||  move.charAt(2) == 'h');
            validity = validity && move.charAt(0) <= '9';
            validity = validity && move.charAt(1) <= '9';
        }
        else if (move.length() > 3) {return false;}
        return validity;
    }

    public int get_column() { return column;}

    public int get_row() {return row;}

    public Wall get_orientation() throws IllegalStateException {
        if (!is_wall()) { throw new IllegalStateException("wrong"); }
        return this.orientation;
    }

    public boolean is_wall() {return  is_wall;}

     int path_length() {
        if (parent == null) { return 1;}
        else { return parent.path_length() + 1;}
    }


    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        char row = '1';
        row += this.row;
        char column = '1';
        column += this.column;
        s.append(column);
        s.append(row);
        if (is_wall()) {s.append(this.orientation == Wall.Vertical ? 'v' : 'h');}
        return s.toString();
    }

    @Override
    public boolean equals(Object m) {
        if (!(m instanceof Location)) {return false;}
        else { Location move = (Location) m;
            boolean same = move.get_row() == get_row() && move.get_column() == get_column();
            if (move.is_wall() && is_wall()) {return same && this.get_orientation() == move.get_orientation();}
            else if (move.is_wall() != is_wall()) {return false;}
            return same;
        }
    }
}
