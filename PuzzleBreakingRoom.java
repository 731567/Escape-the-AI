/*
 * Author: Aiman Syed 
 * Date: January 24, 2025
 * Purpose: to copy a pattern with colours to move to the next room
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class PuzzleBreakingRoom extends EscapeRoom 
{
    //The colour pattern the player will need to repeat
    private final List<Color> sequence = List.of(Color.GREEN, Color. YELLOW, Color.BLUE, Color.GREEN, Color.BLUE, Color.RED, Color.YELLOW);
    private final List<Color> playerSequence = new ArrayList<>();   //an array for the players input for the pattern
    private final JPanel panel = new JPanel();
    //labels and buttons to tell the player the instrcutions and make the button for the next room
    private final JLabel labelInstructions = new JLabel("Click the sequence in order: Green, Yellow, Blue, Green, Blue, Red, Yellow");
    private final JLabel labelResults = new JLabel();
    private final JButton nextRoomButton = new JButton("The Next Room");

    //Constructor for the PuzzleBreakingRoom
    public PuzzleBreakingRoom()
    {
        //use inheritance to pass the name to the constructor
        super("Puzzle Breaking Room");
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
        JFrame frame = new JFrame(getName()); //frame for the room
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);   //dimensions of the frame
        
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
                    labelHints.setText("Hint: Have you clicked the right sequence to match the text?");
                    hintUse(); //set the hint has been used to true
                }

                //if the hint has been used output that the hint has been used already
                else
                {
                    labelHints.setText("Hint: You have already used your hint for this room");
                }
            }
        });

        //set up a grid for the labels and buttons
        panel.setLayout(new GridLayout(3, 2));
        panel.add(labelInstructions);

        //add a button for each colour in the sequence
        addButtonColour(panel, Color.RED);
        addButtonColour(panel, Color.GREEN);
        addButtonColour(panel, Color.BLUE);
        addButtonColour(panel, Color.YELLOW);

        //add the label for the results and the button to move to the next room
        panel.add(labelResults);
        panel.add(nextRoomButton);

        //add these panels to the frame of the screen and make it visible to the player
        frame.getContentPane().add(BorderLayout.NORTH, panel1);
        frame.getContentPane().add(BorderLayout.CENTER, panel);
        frame.setVisible(true);
    }

    //method to create the buttons for each colour in the sequence
    private void addButtonColour(JPanel panel, Color colour)
    {
        JButton button = new JButton();   //create the button
        button.setBackground(colour);    //make the button the same colour as the colours in the sequence
        //listens to if the buttons have been pressed
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                //check the sequence the player makes to see if it matches
                playerSequence.add(colour);
                sequenceCheck();
            }
        });
            panel.add(button);   //add the buttons to the panel
    }

    //method to check if the players sequence is correct
    private void sequenceCheck()
    {
        //Check if the players sequence matches the actual one 
        if(playerSequence.size() == sequence.size())
        {
            //if the sequences do match open the next room to the player
            if(playerSequence.equals(sequence))
            {
                labelResults.setText("That is Correct. Go to the next room");
                isLocked = false;    //set to false as the next room has been unlocked
                nextRoomButton.setEnabled(true);    //set to true as the button will be enabled
            }

            //if the right code has not been inputted it will tell the user that it is incorrect
            else
            {
                labelResults.setText("That is incorrect. Try again");
                playerSequence.clear();    //clear the input for a new input to be added
            }
        }
    }

    //method to give the user a hint for the room
    @Override
    public String getHint()
    {
        return "Correspond the colours to the pattern";
    }
}