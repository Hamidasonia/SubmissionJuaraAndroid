package hamidasonia.com.androidcallit;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

public class SendMail extends AppCompatActivity {

    TextView mResultEmail, mResultPassword;
    EditText mkepada, msubjek, mpesan;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_mail);

        final Button mSendMailMail = (Button) findViewById(R.id.btn_send_send_mail);
        mResultEmail = (TextView) findViewById(R.id.result_email); // email pribadi
        mResultPassword = (TextView) findViewById(R.id.result_password); // password pribadi
        mkepada = (EditText) findViewById(R.id.edt_kepada_mail); // email kepada
        msubjek = (EditText) findViewById(R.id.edt_subjek_mail); // subjek email
        mpesan = (EditText) findViewById(R.id.edt_isi_mail); // pesan untuk dia

        Bundle bundle = getIntent().getExtras();
        mResultEmail.setText(bundle.getCharSequence("mail"));
        mResultPassword.setText(bundle.getCharSequence("pass"));

        mSendMailMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("SendMailActivity", "Send Button Clicked.");
                String fromEmail = mResultEmail.getText().toString();
                String fromPassword = mResultPassword.getText().toString();
                String toEmails = mkepada.getText().toString();
                List<String> toEmailList = Arrays.asList(toEmails
                        .split("\\s*,\\s*"));
                Log.i("SendMailActivity", "To List: " + toEmailList);
                String emailSubject = msubjek.getText().toString();
                String emailBody = mpesan.getText().toString();
                new SendMailTask(SendMail.this).execute(fromEmail,
                        fromPassword, toEmailList, emailSubject, emailBody);
            }
        });

    }

    public void kirim(View view) {
    }

}
