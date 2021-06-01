package com.inkquoir.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    /*
      * Tic Tac Toe Game
      * Developed by Ikhlaq Yousuf Malik on 01-june-2021
     */

    private Button[][] buttons = new Button[3][3];
    private TextView playerFirstName, playerSecondName;
    private TextView scoreTextView, roundsTextView;
    private TextView winCount_O, winCount_X, drawCounts;
    private ImageView refreshImageView;

    private boolean player1Turn = true;
    private int moves, rounds;
    private int player1Score, player2Score;
    private int drawMatchesCount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        selectNamesDialogue();

        refreshImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                clearEveryData();
            }
        });



    }

    private void init() {

        scoreTextView = findViewById(R.id.scoreTextView);
        roundsTextView = findViewById(R.id.roundsTextView);

        playerFirstName = findViewById(R.id.playerFirstName);
        playerSecondName = findViewById(R.id.playerSecondName);

        refreshImageView = findViewById(R.id.refreshImageView);



        winCount_O = findViewById(R.id.winCount_O);
        winCount_X = findViewById(R.id.winCount_X);
        drawCounts = findViewById(R.id.drawCounts);


        for(int i=0; i<3; i++){
            for(int j=0; j<3; j++){
                String buttonID = "button_" + i + j;
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());

                buttons[i][j] = findViewById(resID);
                buttons[i][j].setOnClickListener(this);
            }
        }


    }

    private void clearEveryData() {

        for(int i=0; i<3; i++){
            for(int j=0; j<3; j++) {
                buttons[i][j].setText("");
            }
        }

        moves = 0;
        player1Turn = true;

        scoreTextView.setText("0 : 0");
        roundsTextView.setText("Rounds 0");

        winCount_O.setText("0");
        winCount_X.setText("0");
        drawCounts.setText("0");


    }

    @Override
    public void onClick(View view) {
        if(!((Button) view).getText().toString().equals("")){
            return;
        }

        if(player1Turn){
            ((Button) view).setText("0");
        }else{
            ((Button) view).setText("X");
        }

        moves ++;

        if(checkIfAnyPlayerWins())
        {
            if(player1Turn)
            {
                player1Wins();
            }
            else
            {
                player2Wins();
            }
        }
        else if( moves == 9)
        {
            draw();
        }
        else
        {
            player1Turn = ! player1Turn;

        }


    }

    private void draw() {
        Toast.makeText(getApplicationContext(), "Draw ", Toast.LENGTH_SHORT).show();
        drawMatchesCount ++;
        rounds ++;
        updateScore();
        clearAllMoves();
    }

    private void updateScore(){

        String score = player1Score + " : " + player2Score;
        scoreTextView.setText(score);


        String roundsString = "Rounds " + rounds;
        roundsTextView.setText(roundsString);

        winCount_O.setText(String.valueOf(player1Score));
        winCount_X.setText(String.valueOf(player2Score));
        drawCounts.setText(String.valueOf(drawMatchesCount));


    }

    private void player1Wins() {
        Toast.makeText(getApplicationContext(), "Player 1 wins", Toast.LENGTH_SHORT).show();
        showWinnersDialogue(playerFirstName.getText() + " wins !!!!");
        player1Score ++;
        rounds ++;
        updateScore();
        clearAllMoves();
    }

    private void player2Wins() {
        Toast.makeText(getApplicationContext(), "Player 2 wins", Toast.LENGTH_SHORT).show();
        showWinnersDialogue(playerSecondName.getText() + " wins !!!!");
        player2Score ++;
        rounds ++;
        updateScore();
        clearAllMoves();

    }

    private void clearAllMoves(){

        Thread timer = new Thread(){
            public  void run(){
                try{
                    sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finally {

                    for(int i=0; i<3; i++){
                        for(int j=0; j<3; j++) {
                            buttons[i][j].setText("");
                        }
                    }

                    moves = 0;
                    player1Turn = true;
                    
                }
            }

        };

        timer.start();





    }

    private boolean checkIfAnyPlayerWins() {

        String[][] playedMovesMatrix = new String[3][3];
        for(int i=0; i<3;i++){
            for(int j=0;j<3; j++){
                playedMovesMatrix[i][j] = buttons[i][j].getText().toString();
            }
        }

        for(int i=0;i< 3; i++){
            if(playedMovesMatrix[i][0].equals(playedMovesMatrix[i][1]) &&
                    playedMovesMatrix[i][0].equals(playedMovesMatrix[i][2]) && ! playedMovesMatrix[i][0].equals("")){
                return true;
            }
        }

        for(int i=0;i< 3; i++){
            if(playedMovesMatrix[0][i].equals(playedMovesMatrix[1][i]) &&
                    playedMovesMatrix[0][i].equals(playedMovesMatrix[2][i]) && ! playedMovesMatrix[0][i].equals("")){
                return true;
            }
        }

        if(playedMovesMatrix[0][0].equals(playedMovesMatrix[1][1]) &&
                playedMovesMatrix[0][0].equals(playedMovesMatrix[2][2])  && ! playedMovesMatrix[0][0].equals("")){
            return true;
        }

        if(playedMovesMatrix[0][2].equals(playedMovesMatrix[1][1]) &&
                playedMovesMatrix[0][2].equals(playedMovesMatrix[2][0])  && ! playedMovesMatrix[0][2].equals("")){
            return true;
        }

        return false;

    }

    private void selectNamesDialogue()
    {
        Dialog dialogView = new Dialog(this);
        dialogView.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogView.setContentView(R.layout.choose_names_for_game);
        dialogView.setCancelable(false);
        dialogView.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        EditText player1_name, player2_name;
        Button submitNames;

        player1_name = dialogView.findViewById(R.id.player1_name);
        player2_name = dialogView.findViewById(R.id.player2_name);
        submitNames = dialogView.findViewById(R.id.submitNames);


        submitNames.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                playerFirstName.setText(player1_name.getText());
                playerSecondName.setText(player2_name.getText());

                dialogView.cancel();

            }
        });

        dialogView.show();



    }

    private void showWinnersDialogue(String textToSet){

        Dialog dialogView = new Dialog(this);
        dialogView.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogView.setContentView(R.layout.winner_dialogue);

        dialogView.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        TextView winnersTextView = dialogView.findViewById(R.id.winnersTextView);
        winnersTextView.setText(textToSet);

        dialogView.show();

    }










}