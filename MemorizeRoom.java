/*
 * Author: Aiman Syed 
 * Date: January 24, 2025
 * Purpose: to copy a shape sequence to move to the next room
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class MemorizeRoom extends EscapeRoom 
{
    //The shape pattern the player will need to repeat
    private final List<Shape> pattern = new ArrayList<>();
    private final List<Shape> playerShapes = new ArrayList<>();  //an array for the players input for the pattern
    //labels and buttons to tell the player the instructions and make the button for the next room
    private final JPanel panel = new JPanel();
    private final JLabel labelInstructions = new JLabel("Recreate the pattern");
    private final JLabel labelResults = new JLabel();
    private final Random rand = new Random();
    private final JButton nextRoomButton = new JButton("The Next Room");

    //Constructor for the PuzzleBreakingRoom
    public MemorizeRoom()
    {
        //use inheritance to pass the name to the constructor
        super("Memorize Room");
        generatePattern();      //generates the sequence the player has to copy
        nextRoomButton.setEnabled(false);  //sets next room button to false until player solves the puzzle
        //listens to when the next room button is clicked
        nextRoomButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                //closes the current window and opens a new one for the next room
                ((JFrame) SwingUtilities.getWindowAncestor(nextRoomButton)).dispose();
                EscapeTheAI.goToNextRoom();
            }
        });
    }

    //use the solvePuzzles method to define how the puzzle will actually be solved
    @Override
    public void solvePuzzles()
    {
        JFrame frame = new JFrame(getName());    //frame for the room
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);    //dimensions of the frame
        
        //add the hint button and label to the screen and the timer label as well
        JPanel panel1 = new JPanel(new BorderLayout());
        JButton buttonHints = new JButton("Hint");
        JLabel labelHints = new JLabel("Hint:");
        panel1.add(buttonHints, BorderLayout.WEST);
        panel1.add(labelHints, BorderLayout.CENTER);
        panel1.add(EscapeTheAI.getLabelTimer(), BorderLayout.EAST);

        //listens to see if the hint button has been clicked
        buttonHints.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                //if the hint has not been used output the hint to the player
                if(!hintDone)
                {
                    labelHints.setText("Hint: Have you correctly inputted the sequence. Sometimes if the shape name appears for longer it is pressed multiple times not once");
                    hintUse();     //set the hint has been used to true
                }

                //if the hint has been used output that the hint has been used already
                else
                {
                    labelHints.setText("Hint: You have already used your hint for this room");
                }
            }
        });

        //set up a grid for the labels and buttons
        panel.setLayout(new GridLayout(4, 2));
        panel.add(labelInstructions);

        //add a button for each shape in the sequence
        addButtonShapes(panel, "Circle");
        addButtonShapes(panel, "Triangle");
        addButtonShapes(panel, "Square");

        //add the label for the results and the button to move to the next room
        panel.add(labelResults);
        panel.add(nextRoomButton);

        //write the labels in a certain font and size
        labelInstructions.setFont(new Font("Arial", Font.BOLD, 20));
        labelResults.setFont(new Font("Arial", Font.BOLD, 20));

        //add these panels to the frame of the screen and make it visible to the player
        frame.getContentPane().add(BorderLayout.NORTH, panel1);
        frame.getContentPane().add(BorderLayout.CENTER, panel);
        frame.setVisible(true);

        countDown();   //the countdown method to show the pattern
    }

    //method to generate a random sequence using the shapes
    private void generatePattern()
    {
        String[] shapes = {"Circle", "Triangle", "Square"};
        for(int x = 0; x < 6; x++)
        {
            //add the shapes to the pattern
            pattern.add(new Shape(shapes[rand.nextInt(shapes.length)]));
        }
    }

    //method to create the buttons for each shape in the sequence
    private void addButtonShapes(JPanel panel, String shape)
    {
        JButton button = new JButton(shape);    //create the button
        //listens to if the buttons have been pressed
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                playerShapes.add(new Shape(shape));   //adds the shapes to the players sequence
                patternCheck();     //checks if the players sequence and actual sequence matches
            }
        });

        panel.add(button);   //adds the button to the panel
    }

    //method to countdown the time until the pattern shows up
    private void countDown() 
    {
        new Thread(() -> {
            try 
            {
                //countdown from three
                for(int x = 3; x > 0; x--)
                {
                    final int countDown = x;
                    SwingUtilities.invokeLater(() -> {
                    labelResults.setText("The pattern will start in" + " " + countDown);
                    });
                        //the countdown changes every one second
                        TimeUnit.SECONDS.sleep(1);
                }
                    //shows the pattern after the countdown has ended
                    showPattern();
            } 
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }).start();
    }

    //method to show the pattern to the player one by one
    private void showPattern()
    {
        new Thread(() -> {
        //go through the array to generate the sequence
        for(Shape shape : pattern)
        {
                SwingUtilities.invokeLater(() -> {
                    //output each shape
                    labelResults.setText("Shape:" + shape.name);
                });
            try
            {
                //each shape is displayed for one second
                TimeUnit.SECONDS.sleep(1);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }

            SwingUtilities.invokeLater(() -> {
                //asks the player to repeat the pattern
                labelResults.setText("Repeat the pattern");
            });
        }).start();
    }

    //method to check if the player has entered the right patten
    private void patternCheck()
    {
        //if the player has entered the right sequence
        if(playerShapes.size() == pattern.size())
        {
            //output both the players sequence and actual sequence to see if they match
            System.out.println("Players shape pattern:" + playerShapes);
            System.out.println("Shape pattern:" + pattern);
            //if the player sequence does match open the next room
            if(playerShapes.equals(pattern))
            {
                SwingUtilities.invokeLater(() -> {
                    labelResults.setText("That is right! Move to the final room");
                    nextRoomButton.setEnabled(true);    //set to true as the button will be enabled
                    isLocked = false;    //set to false as the next room has been unlocked
                });
            }

            //if the right sequence has not been inputted it will tell the user that it is incorrect
            else
            {
                SwingUtilities.invokeLater(() -> {
                    labelResults.setText("That is incorrect. try again");
                });

                playerShapes.clear();     //clear the input for a new input to be added
                countDown();    //countdown to the sequence again
            }

        }
    }

    //associates the shape with its name
    private static class Shape
    {
        String name;

        //constructor to initalize the name
        Shape(String name)
        {
            this.name = name;   //initalize the shape name
        }

        @Override
        public boolean equals(Object object)
        {
            //if the name of the object is equal to the name of the shape
            if(this == object)
            return true;     //return it as true

            //if the object is null or if the objects are not from the same class/subclass
            if(object == null || getClass() != object.getClass())
            return false;   //it will return false

            Shape shape = (Shape) object;
            return name.equals(shape.name);
        }

        //generates a hash code to return an integer value allowing easier search for the shape name
        @Override
        public int hashCode()
        {
            return name.hashCode();
        }

        //returns the shape name
        @Override
        public String toString()
        {
            return name;
        }
    }

    //method to give the user a hint for the room
    @Override
    public String getHint()
    {
        return " Have you correctly inputted the sequence. Sometimes if the shape name appears for longer it is pressed multiple times not once";
    }
}
            
    
