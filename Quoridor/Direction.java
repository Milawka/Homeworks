package com.company;


public enum  Direction {
    Up(0), Down(1), Right(2), Left(3);
    public final int index_of_direction;

    Direction(int index){
        this.index_of_direction = index;
    }

    public int index() {
        return index_of_direction;
    }

    //Get the opposite direction
    public Direction reverse() {
        if (this == Direction.Down) {
            return Direction.Up;
        } else if (this == Direction.Up) {
            return Direction.Down;
        } else if (this == Direction.Left) {
            return Direction.Right;
        } else {
            return Direction.Left;
        }
    }
}


