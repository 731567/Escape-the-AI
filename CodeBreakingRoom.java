/*
 * Author: Aiman Syed 
 * Date: January 24, 2025
 * Purpose: to decipher a story for the code to move to the next room
 */

 import javax.swing.*;
 import java.awt.*;
 import java.awt.event.ActionEvent;
 import java.awt.event.ActionListener;
 
 public class CodeBreakingRoom extends EscapeRoom {
    private final String puzzle;   //puzzle shown to the player
 
    //Constructor for the CodeBreakingRoom
    public CodeBreakingRoom()
    {
        //use inheritance to pass the name to the constructor
        super("Code Breaking Room");
        //outputs the story the player will need to decipher to solve the code
        this.puzzle = "<html>The AI has a special routine it follows everyday. <br>" + 
        "At 6pm, it codes 3 different files of each of its victims. <br>" + 
        "At 3pm, it traps 6 victims in its house hoping they won’t escape. <br>" + 
        "At 3am it undergoes new updates for 2 minutes. <br>" + 
        "At 11pm, it goes to sleep for 4 hours. <br>" + 
        "And at 9am, it studies each of its newest features for 3 minutes. <html>";
    }
 
    //use the solvePuzzles method to define how the puzzle will actually be solved
    @Override
    public void solvePuzzles()
    {
        JFrame frame = new JFrame(getName());   //frame for the room
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
                    labelHints.setText("Hint: Have you tried to organize the story by time?");
                    hintUse();   //set the hint has been used to true
                }

                //if the hint has been used output that the hint has been used already
                else
                {
                    labelHints.setText("Hint: You have already used your hint for this room");
                }
            }
        });

        JLabel label = new JLabel(puzzle);        //outputs the puzzle to the screen
        JTextField text = new JTextField(10);    //A field for the users input/answer
        JButton buttonSubmit = new JButton("Submit");   //a button which allows the user to submit their answer
        JButton nextRoomButton = new JButton("The Next Room");   //a button which will take the user to the next room
        nextRoomButton.setEnabled(false);   //the next room button will be set to false until the player solves the puzzle

        //listens to if the submit button has been clicked or not
        buttonSubmit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String input = text.getText();
                
                //if the player has entered the correct code it will open the next room for them
                if(input.equals("23634"))
                {
                    JOptionPane.showMessageDialog(frame, "That is correct! The next Room is now unlocked");
                    isLocked = false;    //set to false as the next room has been unlocked
                    nextRoomButton.setEnabled(true);   //set to true as the button will be enabled
                }

                //if the right code has not been inputted it will tell the user that it is incorrect
                else
                {
                    JOptionPane.showMessageDialog(frame, "That is incorrect. The next room is still locked");
                    text.setText(" ");    //clear the input for a new input to be added
                }
            }
        });

        //listens to see if the next room button has been clicked 
        nextRoomButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                frame.dispose();
                EscapeTheAI.goToNextRoom();
            }
        });

        //Creates the main panel for the screen 
        JPanel panelMain = new JPanel();
        panelMain.add(label);   //adds the puzzle 
        panelMain.add(text);    //adds the players input
        panelMain.add(buttonSubmit);    //adds the button to submit the answer
        panelMain.add(nextRoomButton);   //adds the next room button

        //adds the panels to the frame
        frame.getContentPane().add(BorderLayout.NORTH, panel1);
        frame.getContentPane().add(BorderLayout.CENTER, panelMain);
        frame.setVisible(true);
    }

    //method to give the user a hint for the room
    @Override
    public String getHint()
    {
        return "Have you tried to organize the story by time?";
    }
}