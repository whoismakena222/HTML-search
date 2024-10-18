import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;


public class SearchEngine implements ActionListener {

    private JFrame mainFrame;
    private JPanel gridPanel;
    private JButton button;
    private JTextField text1, text2;
    private JTextArea text3;
    private int linkCounter;
    private boolean images;



    public static void main(String[] args) {
        SearchEngine ex = new SearchEngine();

    }

    public SearchEngine() {

        mainFrame = new JFrame("Search Engine");
        gridPanel = new JPanel(new GridLayout(3,1));

        layout();


    }

    public void layout(){
        text1 = new JTextField("enter url");
        text2 = new JTextField("enter search term");
        button = new JButton("go");
        button.addActionListener(this);
        text3 = new JTextArea(50,10);
        JScrollPane scrollPlane = new JScrollPane(text3);
        scrollPlane.createVerticalScrollBar();

        gridPanel.add(text1);
        gridPanel.add(text2);
        gridPanel.add(button);

        mainFrame.setLayout(new BorderLayout());
        mainFrame.add(gridPanel, BorderLayout.NORTH);
        mainFrame.add(scrollPlane, BorderLayout.CENTER);

        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(500,400);
        mainFrame.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {


        String u = text1.getText();
        String s = text2.getText().toLowerCase();

        if(!u.isEmpty() || s.isEmpty()){
            text3.setText("");
            linkCounter = 0;
        }
        try {
            URL url = new URL(u);
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(url.openStream())
            );
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.toLowerCase();
                int linkIndex = line.indexOf("https:");

                while (linkIndex != -1) {
                    int spaceIndex = line.indexOf("\"", linkIndex);
                    if (spaceIndex == -1) spaceIndex = line.length();
                    String link = line.substring(linkIndex, spaceIndex);

                    if(link.contains("images")){
                        images = true;
                    }
                    else{
                        images = false;
                    }

                    if (link.contains(s) && images == false) {
                        linkCounter ++;
                        System.out.println(linkCounter + ". " + link);
                        text3.append(linkCounter + ". " + link + "\n");
                        text3.setCaretPosition(text3.getDocument().getLength());
                    }
                    linkIndex = line.indexOf("https:", spaceIndex);
                }
            }
            reader.close();
            if(linkCounter == 0){
                text3.setText("no links were found");
                System.out.println("no links were found");
            }

        } catch (IOException err) {
            System.out.println(err);
        }

    }
}


