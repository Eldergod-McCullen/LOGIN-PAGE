package com.example.log_inpage;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    //EditText Username_Textbox, Password_textbox;
    //TextView Forgot_Password, Signup_text;                       // VARIABLES FOR THE ITEMS IN THE ACTIVITY LAYOUT
    //Button Login_Button;
    //LoginData ld = new LoginData(this);
    //Cursor loginStatus = ld.validate(Username_Textbox, Password_textbox);
    TextView btn;
    EditText Username_Textbox, Password_Textbox;
    Button Login_Button;


    private FirebaseAuth mAuth;
    ProgressDialog mLoadingBar;

    //-------------- CODE FOR WHEN THE Sign up TEXT IS CLICKED----------------------------------------------------------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        btn = findViewById(R.id.Signup_text);
        Username_Textbox = findViewById(R.id.Username_Textbox);
        Password_Textbox = findViewById(R.id.Password_Textbox);

        Login_Button = findViewById(R.id.Login_Button);

        mAuth= FirebaseAuth.getInstance();
        mLoadingBar = new ProgressDialog(MainActivity.this);

        Login_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkCredentials();        //METHOD FOR CHECKING THE CREDENTIALS
            }
        });

        TextView btn = findViewById(R.id.Signup_text);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MainActivity2.class));
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void checkCredentials() {
        String username = Username_Textbox.getText().toString();
        String password = Password_Textbox.getText().toString();                // NEW VARIABLES FOR THE METHOD


        if (username.isEmpty() || username.length()<=7)
        {
            showError(Username_Textbox, "This Username is not valid");
        }
        else if(password.isEmpty() || password.length()<=9)
        {
            showError(Password_Textbox, "Password length should be above 9 characters");
        }
        else
        {
            //Toast.makeText(this, "Call Log-in Method", Toast.LENGTH_SHORT).show();
            mLoadingBar.setTitle("LOG-IN PAGE");
            mLoadingBar.setMessage("HOLD UP,WHILE WE CHECK YOUR CREDENTIALS");
            mLoadingBar.setCanceledOnTouchOutside(false);
            mLoadingBar.show();

            mAuth.signInWithEmailAndPassword(username,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful())
                    {
                        //Toast.makeText(MainActivity.this, "Error: " + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                        mLoadingBar.dismiss();
                        Intent intent = new Intent(MainActivity.this, ActualApplication.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

                        startActivity(intent);

                    }

                }
            });
        }
    }

    private void showError(EditText input, String s) {
        input.setError(s);
        input.requestFocus();
    }
        /*
        getSupportActionBar().hide();
        Username_Textbox = findViewById(R.id.Username_textbox);
        Password_textbox = findViewById(R.id.Email_Textbox);
        Login_Button = findViewById(R.id.Login_Button);            //ASSIGN THE APPROPRIATE ELEMENT IDs TO THEIR RESPECTIVE VARIABLES
        Signup_text = findViewById(R.id.Signup_text);
        Forgot_Password = findViewById(R.id.Forgot_Password);
         */
        //loginUser();
        //changePassword();


}

    /*
    //----------------- CODE FOR WHEN THE USER LOGS IN---------------------------------------------------------
    public void loginUser() {
        Login_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = Username_Textbox.getText().toString();
                String pass = Password_textbox.getText().toString();

                showMessage("Name", name);         //SHOWING THE DETAILS BEING ENTERED
                showMessage("Pass", pass);

               Cursor loginStatus = ld.validate(name, pass);   //VALIDATING THE LOG-IN CREDENTIALS

                if (name.isEmpty() || pass.isEmpty()) {
                    showMessage("ERROR!!", "THE REQUIRED FIELDS ARE EMPTY");
                } else if (loginStatus != null && loginStatus.getCount() == 0) {
                    // IF THE LOG-IN IS SUCCESSFUL
                    Toast.makeText(getApplicationContext(), "LOG-IN SUCCESSFUL", Toast.LENGTH_LONG).show();  //AT THE MOMENT,THERE IS NO APP TO RUN
                    //Intent intent = new Intent(getApplicationContext(), Home.class);
                    //startActivity(intent);
                } else {
                    // IF THE LOG-IN FAILS
                    Toast.makeText(getApplicationContext(), "Login Failed", Toast.LENGTH_LONG).show();
                    showMessage("ERROR", "TRY AGAIN!!!!!");
                }
            }
        });
    }


    
     //--------------------- CODE FOR CHANGING THE PASSWORD -------------------------------------------------------------------------------------------------------------
    public void changePassword()
    {
        Forgot_Password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);    // AT THE MOMENT,THIS LEADS BACK TO THE MAIN ACTIVITY--REVERTS
                startActivity(i);
            }
        });
    }

    
    public void showMessage(String title, String message)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        AlertDialog.Builder builder1 = builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
}

 */

