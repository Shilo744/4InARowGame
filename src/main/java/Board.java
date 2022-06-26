import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.*;

public class Board extends JFrame {
    private final JButton[][] squares = new JButton[7][];
    boolean player1Turn=true;
    private int []row=new int[7];
    private int maxLine=6;

    public Board() {
        for(int i = 0; i < Constant.BOARD_WIDTH; ++i) {
            this.squares[i] = new JButton[Constant.BOARD_WIDTH];

            for(int j = 0; j < Constant.BOARD_WIDTH; ++j) {
                JButton square = new JButton();
                row[i]=1;
                if (i == 0) {
                    square.setText(String.valueOf(j + 1));
                    square.setBackground(Color.WHITE);
                    square.setFont(new Font(Constant.FONT_NAME, 1, Constant.FONT_SIZE));
                    square.addActionListener((e) -> {
                        int turn;
                        if(row[Integer.parseInt(square.getText())-1]<=maxLine){
                        if(player1Turn){
                            player1Turn=false;
                            turn=1;
                        }
                        else {
                            turn=2;
                            player1Turn=true;
                        }
                        int x = Integer.parseInt(square.getText());
                        int y = row[Integer.parseInt(square.getText())-1];
                        placeSquare(x,y,turn);

                        row[Integer.parseInt(square.getText())-1]++;}
                        int winner=victory();
                        if(winner==1 || winner==2){
                            maxLine=0;
                            squares[0][0].setText("replay");
                            squares[0][0].setFont(new Font("arial", Font.BOLD, 14));
                            squares[0][0].setBackground(Color.orange);
                        }
                    });
                    if(j==0){
                    square.addActionListener((e ->{
                        if(maxLine==0){
                        replay();
                        square.setFont(new Font("arial", Font.BOLD, 20));
                        square.setBackground(Color.WHITE);
                        square.setText("1");}
                    }));
                    }
                } else {
                    square.setEnabled(false);
                }

                this.squares[i][j] = square;
                this.add(square);
            }
        }

        this.setLocationRelativeTo((Component)null);
        GridLayout gridLayout = new GridLayout(Constant.BOARD_WIDTH, Constant.BOARD_HEIGHT);
        this.setLayout(gridLayout);
        this.setSize(Constant.BOARD_HEIGHT * Constant.SQUARE_SIZE, Constant.BOARD_HEIGHT * Constant.SQUARE_SIZE);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
    public void replay(){
        for(int i = 1; i < Constant.BOARD_WIDTH; ++i) {
            row[i]=1;
            for(int j = 1; j < Constant.BOARD_WIDTH+1; ++j) {
                placeSquare(j,i,0);

            }
        row[0]=0;}
    }
    public void placeSquare(int x, int y, int player) {
        try {
            if(player==0){
                this.squares[Constant.BOARD_WIDTH - y][x - 1].setBackground(null);
            maxLine=6;}
            else
            this.squares[Constant.BOARD_WIDTH - y][x - 1].setBackground(player == 1 ? Color.RED : Color.YELLOW);
        } catch (Exception e) {
        }
    }
    private int victory(){
        if(maxLine==6){
        int winner= checkWinnerLine();
        if(winner==1 || winner==2){
        printWinner(winner);
    return winner;}

        winner=checkWinnerRow();
        if(winner==1 || winner==2){
            printWinner(winner);
            return winner;}

            winner=checkWinnerX();
            if(winner==1 || winner==2){
                printWinner(winner);
                return winner;}

        }
    return 0;}

    private void printWinner(int winner) {

        if (winner == 1) {
            System.out.println("player 1 won");
        }

        if (winner == 2) {
            System.out.println("player 2 won");
        }
    }
    private int checkWinnerX(){
        int width=7;
        int height=8;
        int counter1=0;
        int counter2=0;
        for (int j = 1; j < width; j++) {
            for (int i = 1; i < height; i++) {
                    if (getPlayerInSquare(i, j) == 1 &&
                            getPlayerInSquare(i + 1, j + 1) == 1 &&
                            getPlayerInSquare(i + 2, j + 2) == 1 &&
                            getPlayerInSquare(i + 3, j + 3) == 1) {
                        counter1 = 4;
                    } else if (getPlayerInSquare(i, j) == 2 &&
                            getPlayerInSquare(i + 1, j + 1) == 2 &&
                            getPlayerInSquare(i + 2, j + 2) == 2 &&
                            getPlayerInSquare(i + 3, j + 3) == 2) {
                        counter2 = 4;
                    }
                    if (counter1 == 4) {
                        return 1;
                    }
                    if (counter2 == 4) {
                        return 2;
                    }
                else if (getPlayerInSquare(i, j) == 1 &&
                        getPlayerInSquare(i + 1, j - 1) == 1 &&
                        getPlayerInSquare(i + 2, j - 2) == 1 &&
                        getPlayerInSquare(i + 3, j - 3) == 1) {
                    counter1 = 4;
                } else if (getPlayerInSquare(i, j) == 2 &&
                        getPlayerInSquare(i + 1, j - 1) == 2 &&
                        getPlayerInSquare(i + 2, j - 2) == 2 &&
                        getPlayerInSquare(i + 3, j - 3) == 2) {
                    counter2 = 4;
                }
                if (counter1 == 4) {
                    return 1;
                }
                if (counter2 == 4) {
                    return 2;
                }
            }
        }
        return 0;}
    private int checkWinnerRow(){
        int width=7;
        int height=8;
        int counter1=0;
        int counter2=0;
        for (int j = 1; j < height; j++) {
            for (int i = 1; i < width; i++) {
                if(getPlayerInSquare(j,i)==1){
                    counter1++;
                    counter2=0;
                }else if(getPlayerInSquare(j,i)==2){
                    counter2++;
                    counter1=0;
                }else {
                    counter1=0;
                    counter2=0;
                }
                if(counter1==4){
                    return 1;
                }
                if(counter2==4){
                    return 2;
                }
            }
            counter1=0;
            counter2=0;
        }
        return 0;}
    private int checkWinnerLine(){
        int width=7;
        int height=8;
        int counter1=0;
        int counter2=0;
        for (int j = 1; j < width; j++) {
            for (int i = 1; i < height; i++) {
                if(getPlayerInSquare(i,j)==1){
                    counter1++;
                    counter2=0;
                }else if(getPlayerInSquare(i,j)==2){
                    counter2++;
                    counter1=0;
                }else {
                    counter1=0;
                    counter2=0;
                }
                if(counter1==4){
                    return 1;
                }
                if(counter2==4){
                return 2;
                }
            }
            counter1=0;
            counter2=0;
        }
    return 0;}

    public int getPlayerInSquare(int x, int y) {
        byte player = 0;

        try {
            Color backgroundColor = this.squares[Constant.BOARD_WIDTH - y][x - 1].getBackground();
            if (backgroundColor.equals(Color.RED)) {
                player = 1;
            } else if (backgroundColor.equals(Color.YELLOW)) {
                player = 2;
            }
        } catch (Exception var5) {
        }

        return player;
    }
}
