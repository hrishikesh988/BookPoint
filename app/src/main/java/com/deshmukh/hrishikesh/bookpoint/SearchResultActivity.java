package com.deshmukh.hrishikesh.bookpoint;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SearchResultActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private FirebaseAuth mFirebaseAuth;
    private DatabaseReference mDatabaseReference, mDatabaseReference1;

    public String mTitle;
    public String mPrice;
    public String mPublisher;
    public String mCondition;
    public String mUID;
    public String msold_by = "";
    public String mships_from ="";

    private List<BookList> listItems;

    public static Intent newIntent(Context packageContext, String username, String title) {
        Intent i = new Intent( packageContext, SearchResultActivity.class);
        i.putExtra("user_name", username);
        i.putExtra("title", title);
        return i;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        mRecyclerView = (RecyclerView) findViewById(R.id.RecyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference();
        mDatabaseReference1 = FirebaseDatabase.getInstance().getReference();

        listItems = new ArrayList<>();

        Bundle extras = getIntent().getExtras();
        String username = extras.getString("user_name");
        String title = extras.getString("title");

        mDatabaseReference.child("title").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

              Iterable<DataSnapshot> children = dataSnapshot.getChildren();

                for (DataSnapshot child : children ){

                    Books book = dataSnapshot.getValue(Books.class);

                    mTitle = book.Title;
                    mPrice = book.Price;
                    mPublisher = book.Publisher;
                    mCondition = book.Condition;
                    mUID = book.UID;

/*                    mDatabaseReference1.child(mUID).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            UserInformation user = dataSnapshot.getValue(UserInformation.class);

                            msold_by = user.mFirstName +" "+user.LastName;
                            mships_from = user.Location;

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });*/

                    BookList BL = new BookList(mTitle, mPrice, mPublisher, mCondition, msold_by, mships_from);
                    listItems.add(BL);

                }

                mAdapter = new BookListAdapter(listItems, SearchResultActivity.this);
                mRecyclerView.setAdapter(mAdapter);





            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });






    }
}
