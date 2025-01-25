/*
 * Author: Aiman Syed 
 * Date: January 24, 2025
 * Purpose: to play an Escape Room game and solve puzzles
 */ 

 import javax.swing.*;
 import java.awt.*;
 import java.awt.event.ActionEvent;
 import java.awt.event.ActionListener;
 import java.util.Timer;
 import java.util.TimerTask;
 
public class EscapeTheAI
{
    //declare and initalize variables
    private static int roomNumber = 0;           //tracks the room the player is in
    private static EscapeRoom[] rooms;          //Array that stores the objects in the escape room
    private static JLabel labelTimer;          //Shows the timer and how much time is left
    private static int remainingTimer = 600;  //the amount of the time the player has

    public static void main(String[] args) 
    {
        titleScreen();     //shows the title screen when the game first begins
    }

    public static void titleScreen()
    {
        //The frame for the title screen
        JFrame frame = new JFrame("Escape the AI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);

        //The text which is outputted to the title screen with the welcome sign and instructions
        JLabel labelTitle = new JLabel("Welcome to Escape the AI", SwingConstants.CENTER);
        JLabel labelInstructions = new JLabel("<html>These are the instructions:<br>1. To escape the rooms you must solve the puzzles.<br>2. You will have 10 minutes to escape and you can use one hint per room.<br>3. If You do not escape you will be trapped by the AI forever!<br>GoodLuck!</html>", SwingConstants.CENTER);
        JButton startButton = new JButton("Start Game");  //The button clicked to start the game

        //listens to see if the button has been clicked
        startButton.addActionListener(new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) 
        {
            frame.dispose();   //close the screen
            initializeRooms();  //initalize the 4 game rooms
            startTimer();       //start the timer
            goToNextRoom();     //Go to the next room 
        }
    });

        //add each of the components to the screen and frame
        frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().add(labelTitle, BorderLayout.NORTH);
        frame.getContentPane().add(labelInstructions, BorderLayout.CENTER);
        frame.getContentPane().add(startButton, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    //method to initalize the four rooms
    private static void initializeRooms()
    {
        //adds the puzzle rooms into the array
        rooms = new EscapeRoom[] 
        {
            new CodeBreakingRoom(),
            new PuzzleBreakingRoom(),
            new MemorizeRoom(),
            new ImagesRoom(),
        };
    }

    //method to start the timer 
    private static void startTimer()
    {
        //shows the user how much time they have left
        labelTimer = new JLabel("Time remaining: 10:00", SwingConstants.CENTER);
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask()  //schedules the time a task happens
        {
            @Override
            public void run()
            {
                //The time decreases each second
                remainingTimer--;

                //calculates how many minutes and seconds are remaning
                int min = remainingTimer/60;
                int sec = remainingTimer % 60;
                labelTimer.setText(String.format("Time remaining: %02d:%02d", min, sec));   //updates the time each time a second passes

                //if the timer has run out a message will pop up telling the user they have run out of time
                if(remainingTimer <= 0)
                {
                    timer.cancel();
                    exitScreen("The time is up. YOU HAVE BEEN TRAPPED BY THE AI FOREVER");
                }
            }
        }, 1000, 1000);   //delays every second
    }

    //method to display the timer label on the screen
    public static JLabel getLabelTimer()
    {
        return labelTimer;
    }

    //method to show the exit screen after the game has ended
    public static void exitScreen(String message)
    {
        //create a frame for the end of the game
        JFrame frame = new JFrame("Game Over");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);

        JLabel label = new JLabel(message, SwingConstants.CENTER);
        JButton buttonExit = new JButton("Exit");   //creates a button to exit the game

        //listens to see if the exit button has been pressed or not
        buttonExit.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        //add the components to the frame and screen
        frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().add(label, BorderLayout.CENTER);
        frame.getContentPane().add(buttonExit, BorderLayout.SOUTH);
        frame.setVisible(true);    
    }

    //method to go to the next room of the escape room
    public static void goToNextRoom()
    {
        //checks to see if there are more rooms in the game
        if(roomNumber < rooms.length)
        {
            EscapeRoom newRoom = rooms[roomNumber];
            newRoom.solvePuzzles();   //solve the puzzles in the current room user is in

            //if the room has been unlocked print out that it has been unlocked
            if(!newRoom.isLocked())
                {
                    System.out.println(newRoom.getName() + " is unlocked");
                    roomNumber++;  //add the room number to the counter to know it has been completed
                }

                //if the room has not been completed or completed incorrectly keep the room locked
                else
                {
                    System.out.println(newRoom.getName() + " is still locked");
                }

        }       
                //if the player has managed to escape in time, output a message congratulating the player
                else
                {
                    exitScreen("Congratulations! You have escaped the AI");        
                }
    }
}