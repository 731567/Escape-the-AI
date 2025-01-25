/*
 * Author: Aiman Syed 
 * Date: January 24, 2025
 * Purpose: to decipher images for a secret code to move to escape the game
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class ImagesRoom extends EscapeRoom 
{
    //An array to store the words the player will need to decipher the message from
    private final String[] images = {"Cupcake", "Hat", "Apple", "Tree", "Guitar", "Pencil", "Tree"};
    //labels and buttons to tell the player the instructions and make the button for the next room
    private final JPanel panel = new JPanel();
    private final JLabel labelInstructions = new JLabel("Break the code from the images");
    private final JTextField text = new JTextField(10);   //field for the users input
    private final JLabel labelResults = new JLabel();
    private final JButton nextRoomButton = new JButton("The Next Room");

    //Constructor for the ImagesRoom
    public ImagesRoom()
    {
        //use inheritance to pass the name to the constructor
        super("Images Room");
        nextRoomButton.setEnabled(false);   //sets next room button to false until player solves the puzzle
        //listens to when the next room button is clicked
        nextRoomButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                //closes the current window and opens a new one for the next room
                ((JFrame) SwingUtilities.getWindowAncestor(nextRoomButton)).dispose();
                EscapeTheAI.exitScreen("Congratulations! You have escaped the AI");   //response to the user if they escape the game
            }
        });
    }

    //use the solvePuzzles method to define how the puzzle will actually be solved
    @Override
    public void solvePuzzles()
    {
        JFrame frame = new JFrame(getName());  //frame for the room
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
                    labelHints.setText("Hint: Have you looked at the letters/spelling of the words. Perhaps Combined them?");
                    hintUse();  //set the hint has been used to true
                }

                //if the hint has been used output that the hint has been used already
                else
                {
                    labelHints.setText("Hint: You have already used your hint for this room");
                }
            }
        });

        //set up a grid for the labels and buttons
        panel.setLayout(new GridLayout(4, 3));
        panel.add(labelInstructions);

        //loop through the array with the drawings to add them to the panel
        for(String images : images)
        {
            panel.add(createDrawing(images));   //add the images to the panel
        }

        //button for the user to submit their response
        JButton buttonSubmit = new JButton("Submit");
        //listens if the submit button has been pressed
        buttonSubmit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String input = text.getText();    //user input
                //if the user has put in the correct code
                if(input.equalsIgnoreCase("ChatGPT"))
                {
                    //Output that this is correct and they have escaped the house
                    labelResults.setText("That is Correct. The Exit is unlocked");
                    isLocked = false;     //set to false as the next room has been unlocked
                    nextRoomButton.setEnabled(true);    //set to true as the button will be enabled
                }

                //if the right code has not been inputted it will tell the user that it is incorrect
                else
                {
                    labelResults.setText("That is incorrect. Try again");
                }
            }
        });

        //add the different labels and buttons to the panel
        panel.add(labelResults);
        panel.add(text);
        panel.add(buttonSubmit);
        panel.add(nextRoomButton);

        //add these panels to the frame of the screen and make it visible to the player
        frame.getContentPane().add(BorderLayout.NORTH, panel1);
        frame.getContentPane().add(BorderLayout.CENTER, panel);
        frame.setVisible(true);
    }

    //method to create the drawings of each of the objects
    private JPanel createDrawing(String images)
    {
        JPanel panelImages = new JPanel(new BorderLayout());
        //draws the images to a certain scale
        JLabel labelImages = new JLabel(new ImageIcon(new ImageIcon(drawImage(images)).getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH)));
        JLabel labelName = new JLabel(images, SwingConstants.CENTER);   //adds the labels of the names of each image
        panelImages.add(labelImages, BorderLayout.CENTER);   //adds the image to the center
        panelImages.add(labelName, BorderLayout.SOUTH);     //adds the label below the image
        return panelImages;   //return the completed drawing
    }

    //draw the image based on which image is asked to be drawn
    private BufferedImage drawImage(String images)
    {
        BufferedImage image = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);    //creates a transparent image
        Graphics2D g2d = image.createGraphics();   //the graphics for the drawing

        //draw the images based on which image name comes up
        switch(images)
        {
            //case for if cupcake comes up
            case "Cupcake":
            g2d.setColor(Color.PINK);
            g2d.fillOval(20, 40, 60, 40);
            g2d.setColor(Color.YELLOW);
            g2d.fillRect(40, 80, 20, 20);
            break;

            //case for if hat comes up
            case "Hat":
            g2d.setColor(Color.BLUE);
            g2d.fillRect(30, 50, 40, 20);
            g2d.fillRect(20, 70, 60, 10);
            break;

            //case for if apple comes up
            case "Apple":
            g2d.setColor(Color.RED);
            g2d.fillOval(30, 40, 40, 40);
            g2d.setColor(Color.GREEN);
            g2d.fillRect(45, 20, 10, 20);
            break;

            //case for if Tree comes up
            case "Tree":
            g2d.setColor(Color.GREEN);
            g2d.fillOval(30, 20, 40, 60);
            g2d.setColor(Color.BLACK);
            g2d.fillRect(45, 80, 10, 20);
            break;

            //case for if guitar comes up
            case "Guitar":
            g2d.setColor(Color.ORANGE);
            g2d.fillRect(45, 20, 10, 60);
            g2d.setColor(Color.YELLOW);
            g2d.fillOval(35, 60, 30, 30);
            break;

            //case for if pencil comes up
            case "Pencil":
            g2d.setColor(Color.ORANGE);
            g2d.fillRect(45, 20, 10, 60);
            g2d.setColor(Color.YELLOW);
            g2d.fillOval(35, 60, 30, 30);
            break;
        }

        g2d.dispose();
        return image;
    }

    //method to give the user a hint for the room
    @Override
    public String getHint()
    {
        return "Have you looked at the letters/spelling of the words. Perhaps Combined them?";
    }
}
