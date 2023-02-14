/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
// 37-left, 38-up, 39-right, 40-down;
 */
package game;
import java.util.*;
//import java.io.*;
import java.awt.*;        // Using AWT layouts
import java.awt.event.*;  // Using AWT event classes and listener interfaces
import javax.swing.*;     // Using Swing components and containers

public class WithColors extends JFrame implements KeyListener{
    private JFrame mainFrame;
    private JLabel headerLabel;
    private JLabel statusLabel;
    private  JPanel controlPanel;
    private JLabel[] box;
    int board[][] = new int[4][4];
    int posX,posY;
    String lastMove = "  ";
    Random rand = new Random();
    public void printBoard(int board[][]){
        int temp[] = new int[16];
        int k=0;
        for(int i=0;i<4;i++){
            for(int j=0;j<4;j++){
              temp[k++] = board[i][j];
            }
        }
        for(int i=0;i<16;i++){
            box[i].setText(""+temp[i]);
        }
        mainFrame.setVisible(true);
    }
    public int[] changeLine(int list[]){
        int count = 0;
        int i;
        int j=0;
        //To know no of Zero's in row
        for(i=0;i<list.length;i++){
            if(list[i]==0){
              count++;
            }
        }
        if(count==4){
            return list;
        }
        int sublist[] = new int[list.length-count];
        //Collecting NonZero numbers
        for(i=0;i<list.length;i++){
            if(list[i]!=0){
                sublist[j++] = list[i];
            }
        }
        //Updating Line according
        if(count==3){
            list[0] = sublist[0];
            list[1]=0;
            list[2]=0;
            list[3]=0;
            return list;
        }
        for(i=0;i<sublist.length-1;i++){
            if(sublist[i]==sublist[i+1]){
                sublist[i] = sublist[i]+sublist[i+1];
                if(i==sublist.length-2){
                    sublist[i+1] = 0;
                }
                else{
                  if(i==sublist.length-1){
                    sublist[i+1] = 0;
                  }
                  else{
                    sublist[i+1] = sublist[i+2];
                  }
                  for(j=i+2;j<sublist.length-1;j++){
                    sublist[j] = sublist[j+1];
                  }
                  sublist[sublist.length-1] = 0;
                }
            }
        }
        for(i=0;i<sublist.length;i++){
            list[i] = sublist[i];
        }
        for(i=sublist.length;i<list.length;i++){
            list[i] = 0;
        }
        return list;
    }
    public int[][] updateBoard(int board[][],int dir){
        if(dir==0){
            for(int i=0;i<4;i++){
                int list[] = {board[0][i],board[1][i],board[2][i],board[3][i]};
                list = changeLine(list);
                for(int j=0;j<4;j++){
                    board[j][i] = list[j];
                }
            }
        }
        else if(dir==1){
            for(int i=0;i<4;i++){
                int list[] = {board[i][3],board[i][2],board[i][1],board[i][0]};
                list = changeLine(list);
                for(int j=3,k=0;j>=0;j--,k++){
                    board[i][j] = list[k];
                }
            }
        }
        else if(dir==2){
            for(int i=0;i<4;i++){
                int list[] = {board[3][i],board[2][i],board[1][i],board[0][i]};
                list = changeLine(list);
                for(int j=3,k=0;j>=0;j--,k++){
                    board[j][i] = list[k];
                }
            }
        }
        else if(dir==3){
            for(int i=0;i<4;i++){
                int list[] = {board[i][0],board[i][1],board[i][2],board[i][3]};
                list = changeLine(list);
                for(int j=0;j<4;j++){
                    board[i][j] = list[j];
                }
            }
        }
        return board;
    }
    public String generate(){
        posX = rand.nextInt(4);
        posY = rand.nextInt(4);
        Map<String, Integer> pos = new TreeMap<>();
        for(int i=0;i<4;i++){
            for(int j=0;j<4;j++){
              pos.put(Integer.toString(i)+Integer.toString(j),0);
            }
        }
        while(board[posX][posY]!=0){
            pos.put(Integer.toString(posX)+Integer.toString(posY),1);
            if(!(pos.containsValue(0))){
                return "Over";
            }
            posX = rand.nextInt(4);
            posY = rand.nextInt(4);
        }
        board[posX][posY] = ((rand.nextInt(2))*2)+2;
        printBoard(board);
        return "";
    }
    public void gameOver(String s){
        if(s=="Over"){
            statusLabel.setText("Last Move:"+lastMove+"        Game Over");
            mainFrame.setVisible(true);
            mainFrame.removeKeyListener(this);
        }
    }
    public void setColors(){
        Map<String, String> colors = new TreeMap<>();
        colors.put("0","0xFF0000");
        colors.put("2","0xFFFFFF");
        colors.put("4","0xFFEEEE");
        colors.put("8","0xFFDDDD");
        colors.put("16","0xFFCCCC");
        colors.put("32","0xFFBBBB");
        colors.put("64","0xFFAAAA");
        colors.put("128","0xFF9999");
        colors.put("256","0xFF8888");
        colors.put("512","0xFF7777");
        colors.put("1024","0xFF6666");
        colors.put("2048","0xFF5555");
        colors.put("4096","0xFF4444");
        colors.put("8192","0xFF3333");
        colors.put("16384","0xFF2222");
        colors.put("32768","0xFF1111");
        for(int i=0;i<16;i++){
            box[i].setBackground(Color.decode(colors.get(box[i].getText())));
            //box[i].setForeground(Color.decode(colors.get(box[i].getText())));
        }
    }
    public void Game(){
        box = new JLabel[16];
        mainFrame = new JFrame("2048");
        mainFrame.setSize(500,600);
        mainFrame.setLayout(new GridLayout(3, 1));
        headerLabel = new JLabel("",JLabel.CENTER );
        statusLabel = new JLabel("Thanks for playing",JLabel.CENTER);        
        statusLabel.setSize(500,50);
        mainFrame.addWindowListener(new WindowAdapter() {
           @Override
           public void windowClosing(WindowEvent windowEvent){
              System.exit(0);
           }        
        });
        mainFrame.addKeyListener(this);
        controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout());
        mainFrame.add(headerLabel);
        mainFrame.add(controlPanel);
        mainFrame.add(statusLabel);      
        headerLabel.setText("Welcome to 2048");        
        headerLabel.setSize(500,50);          
        JPanel panel = new JPanel();
        //panel.setBackground(Color.lightGray);
        panel.setSize(500,400);
        GridLayout layout = new GridLayout(4,4);
        layout.setHgap(20);
        layout.setVgap(20);
        panel.setLayout(layout);
        for(int i=0;i<16;i++){
            box[i] = new JLabel();
            box[i].setText("0");
            box[i].setPreferredSize(new Dimension(50, 30));
            box[i].setForeground(Color.BLUE);
            box[i].setBackground(Color.RED);
            panel.add(box[i]);
        }
        controlPanel.add(panel);
        gameOver(generate());
        printBoard(board);
    }
    // The entry main() method
    public static void main(String[] args) {        
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
               new Game();  // Let the constructor do the job
            }
        });
    } 

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        String direction = "";
        switch(code){
            case 37:
                direction = "Left";
                board = updateBoard(board,3);
                break;
            case 38:
                direction = "Up";
                board = updateBoard(board,0);
                break;
            case 39:
                direction = "Right";
                board = updateBoard(board,1);
                break;
            case 40:
                direction = "Down";
                board = updateBoard(board,2);
                break;
        }
        if(direction==""){
            statusLabel.setText("Please use Arrow keys to play");
        }
        else{
            statusLabel.setText("Last Move:"+lastMove+"        Current Move:"+direction);
        }
        lastMove = direction;
        gameOver(generate());        
        setColors();
        printBoard(board);
        mainFrame.setVisible(true);
    }
    @Override public void keyTyped(KeyEvent e) {}
    @Override public void keyReleased(KeyEvent e) {}
}