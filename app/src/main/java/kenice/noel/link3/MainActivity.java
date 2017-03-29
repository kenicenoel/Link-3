package kenice.noel.link3;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
{
    private GridLayout gridLayout;
    private boolean gameWon = false;
    private TextView playerOneScoreTextView;
    private TextView playerTwoScoreTextView;
    private final String TAG = MainActivity.class.getSimpleName();

    // Keep track of active player with player 1 = yellow and player 2 = green
    private int player = 1;

    // A value to initialize the gameboard cells to
    private final int DEFAULT_STATE_UNPLAYED = 9;

    // Set the player scores to Zero at first
    private int playerOneWins = 0;
    private int playerTwoWins = 0;
    private MediaPlayer crunchSound;
    private LinearLayout gameOverLayout;
    private Button playAgainButton;
    private boolean gameIsActive = true;


    // Keeps track of the position on the board where a move was made (9 = no moves made)
    private int[] gameState = new int[9];



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        playerOneScoreTextView = (TextView) findViewById(R.id.scorePlayerOne);
        playerTwoScoreTextView = (TextView) findViewById(R.id.scorePlayerTwo);
        crunchSound = MediaPlayer.create(this, R.raw.crunch_sound);
        gameOverLayout = (LinearLayout) findViewById(R.id.gameOverLayout);
//        gameOverLayout.setTranslationY(-1000f);
        gameOverLayout.setScaleX(0f);
        gameOverLayout.setScaleY(0f);

        playAgainButton = (Button) findViewById(R.id.buttonPlayAgain);
        playAgainButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                startNewGame();
            }
        });

        // Initialize the game to a fresh state
        initialize();

    }

    public void dropIn(View v)
    {
        ImageView counter = (ImageView) v;


        // get the tag from the view
        int tag = Integer.parseInt(v.getTag().toString());

        // If the tapped position doesn't have a coin
        if (gameState[tag] == DEFAULT_STATE_UNPLAYED && gameIsActive)
        {
            counter.setTranslationY(-1000f);

            // If the player is 1 use the yellow coin and if not, use the green coin
            if (player == 1)
            {
                counter.setImageResource(R.drawable.ic_nutmeg_coin_yellow);

            }

            else if (player == 2)
            {
                counter.setImageResource(R.drawable.ic_nutmeg_coin_green);

            }

            // Set the tapped position as being played
            gameState[tag] = player;
            counter.animate().translationYBy(1000f).rotation(720).setDuration(300);
            crunchSound.start();

            if(isGameWon())
            {
                if (player == 1)
                {
                    playerOneWins++;
                    playerOneScoreTextView.setText(String.valueOf(playerOneWins));
                    playerOneScoreTextView.animate().scaleX(2f).scaleY(2f).setDuration(600);
                    playerOneScoreTextView.animate().scaleX(1f).scaleY(1f).setDuration(600);
                }

                else
                {
                    playerTwoWins++;
                    playerTwoScoreTextView.setText(String.valueOf(playerTwoWins));
                    playerTwoScoreTextView.animate().scaleX(2f).scaleY(2f).setDuration(600);
                    playerTwoScoreTextView.animate().scaleX(1f).scaleY(1f).setDuration(600);

                }
                gameIsActive = false;
                gameOverLayout.setVisibility(View.VISIBLE);
                gameOverLayout.animate().alpha(1f).scaleX(1f).scaleY(1f).setDuration(700);

                TextView winningPlayer = (TextView) findViewById(R.id.winningPlayerName);
                winningPlayer.setText("Player "+player+ " won the round.");



            }

            else
            {
                if (isGameDrawn())
                {
                    gameIsActive = false;
                    gameOverLayout.setVisibility(View.VISIBLE);
                    gameOverLayout.animate().alpha(1f).scaleX(1f).scaleY(1f).setDuration(700);

                    TextView winningPlayer = (TextView) findViewById(R.id.winningPlayerName);
                    winningPlayer.setText("The game ended in a draw.");
                }
            }

            switchTurnPlayer();

        }

        else
        {
            counter.animate().rotation(360f).setDuration(500);
        }

    }


    public boolean isGameWon()
    {

        if(gameState[0] + gameState[1] + gameState[2] == 3 || gameState[0] + gameState[1] + gameState[2] == 6)
        {
            return true;
        }


        else if(gameState[3] + gameState[4] + gameState[5] == 3 || gameState[3] + gameState[4] + gameState[5] == 6)
        {
            return true;
        }
        else if(gameState[6] + gameState[7] + gameState[8] == 3 || gameState[6] + gameState[7] + gameState[8] == 6 )
        {
            return true;
        }

        else if(gameState[0] + gameState[3] + gameState[6] == 3 || gameState[0] + gameState[3] + gameState[6] == 6 )
        {
            return true;
        }
        else if(gameState[0] + gameState[4] + gameState[8] == 3 || gameState[0] + gameState[4] + gameState[8] == 6)
        {
            return true;
        }

        else if(gameState[1] + gameState[4] + gameState[7] == 3 || gameState[1] + gameState[4] + gameState[7] == 6 )
        {
            return true;
        }

        else if(gameState[2] + gameState[5] + gameState[8] == 3 || gameState[2] + gameState[5] + gameState[8] == 6)
        {
            return true;
        }

        else if(gameState[2] + gameState[4] + gameState[6] == 3 || gameState[2] + gameState[4] + gameState[6] == 6)
        {
            return true;
        }



        return false;

    }


    public void startNewGame()
    {
        gameOverLayout.setVisibility(View.GONE);
        gameIsActive = true;
        player = 1;
        GridLayout gameboard = (GridLayout) findViewById(R.id.gameBoardLayout);
        for(int i = 0; i < gameboard.getChildCount(); i++)
        {
            ((ImageView) gameboard.getChildAt(i)).setImageResource(0);
        }

        initialize();

    }


    public void initialize()
    {

        for (int i = 0; i < gameState.length; i++)
        {
            gameState[i] = DEFAULT_STATE_UNPLAYED;
        }

        Bundle savedvalues = getIntent().getExtras();
        if (savedvalues != null)
        {
            playerOneWins = savedvalues.getInt("scoreA");
            playerTwoWins = savedvalues.getInt("scoreB");

            playerOneScoreTextView.setText(String.valueOf(playerOneWins));
            playerTwoScoreTextView.setText(String.valueOf(playerTwoWins));
        }

    }


    public void switchTurnPlayer()
    {
        if (player == 1)
        {
            player = 2;
        }

        else if (player == 2)
        {
            player = 1;
        }
    }

    public boolean isGameDrawn()
    {
        boolean draw = true;
        for (int state : gameState)
        {
            if (state == DEFAULT_STATE_UNPLAYED)
            {
                draw = false;
            }


        }
        return draw;
    }


}
