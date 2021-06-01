package com.inkquoir.tictactoe;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements PlayersName.PlayerNameListner, View.OnClickListener{

//    Designed and developed By Ikhlaq Yousif Malik

    private Button[][] buttons = new Button[3][3];
    private TextView scoreTextView, roundsTextView;
    private TextView winCount_O, winCount_X, drawCounts, playerName1, playerName2;

    private boolean player1Turn = true;
    private int moves, rounds;
    private int player1Score, player2Score;
    private int drawMatchesCount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // hides out the default action bar
        getSupportActionBar().hide();

        init();
        openDialog();
    }

    private void openDialog() {
        PlayersName playersName = new PlayersName();
        playersName.show(getSupportFragmentManager(), "Player Names");
    }

    private void init() {

        scoreTextView = findViewById(R.id.scoreTextView);
        roundsTextView = findViewById(R.id.roundsTextView);

        winCount_O = findViewById(R.id.winCount_O);
        winCount_X = findViewById(R.id.winCount_X);
        drawCounts = findViewById(R.id.drawCounts);
        playerName1 = findViewById(R.id.playerName1);
        playerName2 = findViewById(R.id.playerName2);


        for(int i=0; i<3; i++){
            for(int j=0; j<3; j++){
                String buttonID = "button_" + i + j;
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());

                buttons[i][j] = findViewById(resID);
                buttons[i][j].setOnClickListener(this);
            }
        }
    }

    @Override
    public void onClick(View view) {
        if(!((Button) view).getText().toString().equals("")){
            return;
        }

        if(player1Turn){
            ((Button) view).setText("0");
            ((Button) view).setBackgroundColor(Color.GREEN);
        }else{
            ((Button) view).setText("X");
            ((Button) view).setBackgroundColor(Color.RED);
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
        player1Score ++;
        rounds ++;
        updateScore();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                clearAllMoves();
            }
        },2000);
    }

    private void player2Wins() {
        Toast.makeText(getApplicationContext(), "Player 2 wins", Toast.LENGTH_SHORT).show();
        player2Score ++;
        rounds ++;
        updateScore();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                clearAllMoves();
            }
        },2000);

}

    private void clearAllMoves(){

        for(int i=0; i<3; i++){
            for(int j=0; j<3; j++) {
                buttons[i][j].setText("");
                buttons[i][j].setBackgroundColor(Color.WHITE);
            }
        }


        moves = 0;
        player1Turn = true;

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

    @Override
    public void applyTexts(String player1, String player2) {
        playerName1.setText(player1);
        playerName2.setText(player2);
    }
}