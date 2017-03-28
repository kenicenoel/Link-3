package kenice.noel.link3;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity
{
    private GridLayout gridLayout;

    // Keep track of active player
    private int player = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void dropIn(View v)
    {
        ImageView counter = (ImageView) v;
        counter.setTranslationY(-1000f);

        // If the player is 1 use the yellow coin and if not, use the green coin
        if (player == 1)
        {
            counter.setImageResource(R.drawable.ic_nutmeg_coin_yellow);
            player = 2;
        }

        else if (player == 2)
        {
            counter.setImageResource(R.drawable.ic_nutmeg_coin_green);
            player = 1;
        }

        counter.animate().translationYBy(1000f).rotation(720).setDuration(500);
    }


    public void checkForWinningCondition()
    {

    }


}
