package sg.edu.np.mad.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        User user = new User("Eric","student",12248,true); //create an instance of user obj

        //loadUserObj(user); //For (Prac 2) loading user obj to access the attributes and perform some functions

        //receiving Intent
//        Intent receivingEnd = getIntent();
//        int message = receivingEnd.getIntExtra("randomNum", -1); //the behind is a default value, if no value has been passed in through intent
//        setRandomNum(message);

        Intent receivingEnd = getIntent();
        String user_name = receivingEnd.getStringExtra("user_name");
        String user_desc = receivingEnd.getStringExtra("user_desc");
        //int user_id = receivingEnd.getIntExtra("user_id", 0); //Note when you getIntExtra you must set it with a default value in case the actual value is not passed
        Boolean user_followed = receivingEnd.getBooleanExtra("user_followed", false); //Same when you getBoolean you must set a default value unlike string

        setUserDetails(user_name, user_desc, user_followed);
    }




    //Reacts to the user "followed" attribute (Prac 2)
    public void loadUserObj(User user){
        //can store Button as TextView, as button is a child of TextView
        TextView followButton = findViewById(R.id.followButton); //retrieving the element, to be used to set txt later

        if(user.isFollowed() == true){                          //determining the attribute Follow is set to what
            followButton.setText("UNFOLLOW");
        }
        else{
            followButton.setText("FOLLOW");
        }
    }

    //Reacts to when the button is clicked
    public void onFollowBtnClick (View view){
        Button followButton = findViewById(R.id.followButton);

        if (followButton.getText() == "FOLLOW"){
            followButton.setText("UNFOLLOW"); //Thus, in the cases its a follow button, if clicked it will display "Followed" & "Unfollow" opt will be provided for the user.
            Toast.makeText(getApplicationContext(), "Followed", Toast.LENGTH_SHORT).show();

        }else{
            followButton.setText("FOLLOW");                 //the capitalization does not matter android is smart enough
            Toast.makeText(getApplicationContext(), "Unfollowed", Toast.LENGTH_SHORT).show();   //displays toast msg, if user clicks Unfollow button, Unfollowed tst msg appears

        }

    }

    //Sets Random Number from ListActivity class (Practical 3)
    public void setRandomNum(int randomNum){
        TextView madTxt = findViewById(R.id.helloTxt);

        madTxt.setText("MAD " + String.valueOf(randomNum));
    }

    //Sets Random user name & Random user description, in the Main activity (Practical 4)
    public void setUserDetails(String name, String desc, Boolean followed){
        TextView userName = findViewById(R.id.helloTxt);
        TextView userDesc = findViewById(R.id.randomUserDesc);
        TextView followButton = findViewById(R.id.followButton);

        userName.setText("Name " + name);
        userDesc.setText("Description " + desc);

        if(followed == true){                          //determining the attribute Follow is set to what
            followButton.setText("UNFOLLOW");
        }
        else{
            followButton.setText("FOLLOW");
        }
    }


    //Intent created to Traverse from this activity(Main activty) to MessageGroup class.
    public void onMessageBtnClick(View view){
        //intent created to traverse to the other page
        Intent myIntent = new Intent(MainActivity.this, MessageGroup.class);
        startActivity(myIntent);
    }


}