package hamidasonia.com.androidcallit;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import hamidasonia.com.androidcallit.Common.Common;
import hamidasonia.com.androidcallit.Model.Menus;

public class MenusDetail extends AppCompatActivity {

    TextView menus_name, menus_number, menus_description;
    ImageView menus_image;
    CollapsingToolbarLayout collapsingToolbarLayout;


    String menusId = "";

    FirebaseDatabase database;
    DatabaseReference menus;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menus_detail);

        // Firebase
        database = FirebaseDatabase.getInstance();
        menus = database.getReference("Menus");

        //Init View
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.btnCall);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:085290604749"));

                if (ActivityCompat.checkSelfPermission(MenusDetail.this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                startActivity(intent);
            }
        });



        menus_description = (TextView)findViewById(R.id.menus_description);
        menus_name = (TextView)findViewById(R.id.menus_name);
        menus_number = (TextView)findViewById(R.id.menus_number);
        menus_image = (ImageView)findViewById(R.id.img_menus);

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collpsing);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppbar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppbar);

        //Get Menus Id from Intent
        if (getIntent() !=null)
            menusId = getIntent().getStringExtra("MenusId");
        if (!menusId.isEmpty())
        {
            if (Common.isConnectedToInterner(getBaseContext()))
                getMenusDetail(menusId);
            else
            {
                Toast.makeText(MenusDetail.this, "Please check your connection!!", Toast.LENGTH_SHORT).show();
                return;
            }
        }
    }





    private void getMenusDetail(String menusId) {

        menus.child(menusId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Menus menus = dataSnapshot.getValue(Menus.class);


                //set Image
                Picasso.with(getBaseContext()).load(menus.getImage())
                        .into(menus_image);

                collapsingToolbarLayout.setTitle(menus.getName());

                menus_number.setText(menus.getNumber());

                menus_name.setText(menus.getName());

                menus_description.setText(menus.getDescription());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
