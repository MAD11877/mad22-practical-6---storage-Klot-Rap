package sg.edu.np.mad.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        FirebaseDatabase database = FirebaseDatabase.getInstance();


        ((Button)findViewById(R.id.buttonLogin)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Retrive all the user input vales
                EditText usernameEditText = findViewById(R.id.etUsername);
                EditText passwordEditText = findViewById(R.id.etPassword);

                String etUsername = usernameEditText.getText().toString();
                String etPassword = passwordEditText.getText().toString();



                //Reference to the Database
                DatabaseReference myRef = database.getReference("Users/" + etUsername);

                // Read from the database
                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot Snapshot) {
                        //A HashMap, store items in "key/value" pairs, and you can access them by an index of another type (e.g. a String).
                        //One object is used as a key (index) to another object (value).
                        //To access a value in the HashMap, use the get() method and refer to its key
                        Map<String, Object> map = (HashMap<String, Object>) Snapshot.getValue();
                        //this obj can be then accessed via its get method

                        String dbPassword; //sets global variable

                        //Determines if such Username exisits in the db
                        try {
                            dbPassword = map.get("password").toString(); //retrivers the user attribute's password
                        } //if unsuccessful userName does not exists in the DB
                        catch (Exception e) {
                            //if unsuccessful does not exists send toast msg
                            Toast.makeText(Login.this, "USERNAME NOT FOUND", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        //determine if entered passwd with comparison to the respective dbPW
                        if (dbPassword.equals(etPassword)) { //once password is recieved(get) compare password
                            Intent intent = new Intent(Login.this, ListActivity.class); //send to ListActivity Class
                            startActivity(intent);  //if yes redirect user to the login page
                        } else {
                            Toast.makeText(Login.this, "WRONG PASSWORD", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                    }
                });
            }
        });

    }
}