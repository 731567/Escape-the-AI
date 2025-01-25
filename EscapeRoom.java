/*
 * Author: Aiman Syed 
 * Date: January 24, 2025
 * Purpose: to play an Escape Room game and solve puzzles abstract class
 */

import javax.swing.*;

public abstract class EscapeRoom 
{
    protected String name;        //the escape room name
    protected boolean isLocked;  //checks to see if the room is locked or unlocked
    protected boolean hintDone; //checks to see if the hint has been used already or not

    //EscapeRoom constructor
    public EscapeRoom(String name)
    {
        this.name = name;      //initialize the room name
        this.isLocked = true;  //set the door being locked to true
        this.hintDone = false;  //sets the hint has been used up to false
    }

    //getter method to get the room name
    public String getName()
    {
        return name;
    }

    //method to see if the room is locked. 
    public boolean isLocked()
    {
        //returns true if locked, false if unlocked
        return isLocked;
    }

    //method to see if the hint has been used already
    public boolean getHintDone()
    {
        //return true if the hint has been used and false if it hasn't
        return hintDone;
    }

    //method to set the hints used as true
    public void hintUse()
    {
        //if the hint has been used up, it will switch to true
        this.hintDone = true;
    }

    public abstract void solvePuzzles();   //abstract method to implement the puzzles in the room
    public abstract String getHint();      //abstract method which gives the user the hint

}