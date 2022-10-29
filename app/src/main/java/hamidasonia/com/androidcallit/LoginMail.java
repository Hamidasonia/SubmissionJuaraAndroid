package hamidasonia.com.androidcallit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginMail extends AppCompatActivity {

    EditText mUser, mPassword;
    Button mSendmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_mail);
        mUser = (EditText) findViewById(R.id.edt_username_login);
        mPassword = (EditText) findViewById(R.id.edt_password_login);
        mSendmail = (Button) findViewById(R.id.btn_login_login);

        mSendmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = mUser.getText().toString();
                String pass = mPassword.getText().toString();
                if (TextUtils.isEmpty(user)) {
                    mUser.setError("Isi Email");
                } else if (TextUtils.isEmpty(pass)) {
                    mPassword.setError("Isi Password");
                } else {
                    Intent intent = new Intent(getApplicationContext(), SendMail.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("mail", mUser.getText().toString());
                    bundle.putString("pass", mPassword.getText().toString());
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });
    }
}
