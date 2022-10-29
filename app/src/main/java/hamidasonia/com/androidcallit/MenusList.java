package hamidasonia.com.androidcallit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import hamidasonia.com.androidcallit.Common.Common;
import hamidasonia.com.androidcallit.Interface.ItemClickListener;
import hamidasonia.com.androidcallit.Model.Menus;
import hamidasonia.com.androidcallit.ViewHolder.MenusViewHolder;

public class MenusList extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference menusList;

    String categoryId = "";
    FirebaseRecyclerAdapter<Menus, MenusViewHolder> adapter;


    //Search functionally
    FirebaseRecyclerAdapter<Menus, MenusViewHolder> searchadapter;
    List<String> suggestList = new ArrayList<>();
    MaterialSearchBar materialSearchBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menus_list);

        //database
        database = FirebaseDatabase.getInstance();
        menusList = database.getReference("Menus");

        recyclerView = (RecyclerView) findViewById(R.id.recycler_menus);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        //Get Intent here
        if (getIntent() !=null)
            categoryId = getIntent().getStringExtra("CategoryId");
        if (!categoryId.isEmpty() && categoryId !=null)
        {
            if (Common.isConnectedToInterner(getBaseContext()))
                    loadListMenus(categoryId);
            else
            {
                Toast.makeText(MenusList.this, "Please check your connection!!", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        //SEARCH
        materialSearchBar = (MaterialSearchBar) findViewById(R.id.searchBar);
        materialSearchBar.setHint("Cari");
       // materialSearchBar.setSpeechMode(false); tidak perlu karena sudah di Coding xml
        loadSuggest(); //untuk memanggil firebase
        materialSearchBar.setLastSuggestions(suggestList);
        materialSearchBar.setCardViewElevation(10);
        materialSearchBar.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                List<String> suggest = new ArrayList<String>();
                for (String search:suggestList)
                {
                    if (search.toLowerCase().contains(materialSearchBar.getText().toLowerCase()))
                        suggest.add(search);
                }
                materialSearchBar.setLastSuggestions(suggest);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        materialSearchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {
                //When search bar is close
                //Restore original adapter
                if (!enabled)
                    recyclerView.setAdapter(adapter);
            }

            @Override
            public void onSearchConfirmed(CharSequence text) {
                //When search finish
                //Show result or adapter
                startSearch(text);
            }

            @Override
            public void onButtonClicked(int buttonCode) {

            }
        });

    }

    private void startSearch(CharSequence text) {
        searchadapter = new FirebaseRecyclerAdapter<Menus, MenusViewHolder>(
                Menus.class,
                R.layout.menus_item,
                MenusViewHolder.class,
                menusList.orderByChild("Name").equalTo(text.toString())
        ) {
            @Override
            protected void populateViewHolder(MenusViewHolder viewHolder, Menus model, int position) {

                viewHolder.menus_name.setText(model.getName());
                Picasso.with(getBaseContext()).load(model.getImage())
                        .into(viewHolder.menus_image);

                final Menus local = model;
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        //Start New Activity
                        Intent menusDetail = new Intent(MenusList.this, MenusDetail.class);
                        menusDetail.putExtra("MenusId", searchadapter.getRef(position).getKey());  //send memus id to new activity
                        startActivity(menusDetail);

                    }
                });
            }
        };
        recyclerView.setAdapter(searchadapter);
    }

    private void loadSuggest() {
        menusList.orderByChild("MenuId").equalTo(categoryId)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot postSnapshot:dataSnapshot.getChildren())
                        {
                            Menus item = postSnapshot.getValue(Menus.class);
                            suggestList.add(item.getName()); //to suggest list
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }

    private void loadListMenus(String categoryId) {
        adapter = new FirebaseRecyclerAdapter<Menus, MenusViewHolder>(Menus.class,
                R.layout.menus_item,
                MenusViewHolder.class,
                menusList.orderByChild("MenuId").equalTo(categoryId) // Like : Select * from  Menus where MenuId =
                ) {
            @Override
            protected void populateViewHolder(MenusViewHolder viewHolder, Menus model, int position) {

                viewHolder.menus_name.setText(model.getName());
                Picasso.with(getBaseContext()).load(model.getImage())
                        .into(viewHolder.menus_image);

                final Menus local = model;
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        //Start New Activity
                        Intent menusDetail = new Intent(MenusList.this, MenusDetail.class);
                        menusDetail.putExtra("MenusId",adapter.getRef(position).getKey());  //send memus id to new activity
                        startActivity(menusDetail);
                    }
                });

            }
        };

        recyclerView.setAdapter(adapter);
    }
}
