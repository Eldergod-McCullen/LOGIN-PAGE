package com.example.log_inpage;

import static android.app.ProgressDialog.show;

import android.app.ProgressDialog;
import android.app.appsearch.SearchResult;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.contract.ActivityResultContracts;
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

public class MainActivity2 extends AppCompatActivity {

    TextView Signup_text;

    private EditText Username_textbox, Email_Textbox, Password_Textbox, Confirm_Textbox;
    Button CreateAccount_Button;

    private FirebaseAuth mAuth;
    private ProgressDialog mLoadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main2);

        Signup_text = findViewById(R.id.Signin_Text);
        Username_textbox = findViewById(R.id.Username_Textbox);
        Email_Textbox = findViewById(R.id.Email_Textbox);
        Password_Textbox = findViewById(R.id.Password_Textbox);
        Confirm_Textbox = findViewById(R.id.Confirm_Textbox);

        mAuth= FirebaseAuth.getInstance();

        CreateAccount_Button = findViewById(R.id.CreateAccount_Button);
        CreateAccount_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkCredentials();        //METHOD FOR CHECKING THE CREDENTIALS
            }
        });

        TextView Signup_text = findViewById(R.id.Signin_Text);
        Signup_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity2.this,MainActivity.class));
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void checkCredentials() {
        String username = Username_textbox.getText().toString();
        String email = Email_Textbox.getText().toString();                // NEW VARIABLES FOR THE METHOD
        String password = Password_Textbox.getText().toString();
        String confirm_password = Confirm_Textbox.getText().toString();

        if (username.isEmpty() || username.length()<=7)
        {
            showError(Username_textbox, "This Username is not valid");
        }
        else if(email.isEmpty() || !email.contains("@"))
        {
            showError(Email_Textbox, "This e-mail is not valid");
        }
        else if(password.isEmpty() || password.length()<=9)
        {
            showError(Password_Textbox, "Password length should be above 9 characters");
        }
        else if(confirm_password.isEmpty() || !confirm_password.equals(password))
        {
            showError(Confirm_Textbox, "Passwords not matching");
        }
        else
        {
           mLoadingBar.setTitle("REGISTRATION PAGE");
           mLoadingBar.setMessage("HOLD UP,WHILE WE CHECK YOUR CREDENTIALS");
           mLoadingBar.setCanceledOnTouchOutside(false);
           mLoadingBar.show();

           mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
               @Override
               public void onComplete(@NonNull Task<AuthResult> task) {
                   if (task.isSuccessful())
                   {
                      // Toast.makeText(MainActivity2.this, "ERROR"task.getException().toString(),"",Toast.LENGTH_SHORT).show();
                       Toast.makeText(MainActivity2.this, "Error: " + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();

                       mLoadingBar.dismiss();
                       Intent intent = new Intent(MainActivity2.this, ActualApplication.class);
                       intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                       startActivity(intent);
                   }
                   else
                   {
                       Toast.makeText(MainActivity2.this,task.getException().toString(),Toast.LENGTH_SHORT).show();
                   }
               }
           });
        }
    }

    private void showError(EditText input, String s) {
        input.setError(s);
        input.requestFocus();
    }


}