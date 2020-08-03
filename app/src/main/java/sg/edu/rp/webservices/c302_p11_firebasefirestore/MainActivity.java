package sg.edu.rp.webservices.c302_p11_firebasefirestore;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private ListView lvStudent;
    private ArrayList<Inventory> alInventory;
    private ArrayAdapter<Inventory> aaStudent;

    // TODO: Task 1 - Declare Firebase variables
    FirebaseFirestore fbfs;
    CollectionReference cr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvStudent = findViewById(R.id.lv1);

        // TODO: Task 2: Get FirebaseFirestore instance and reference
        fbfs = FirebaseFirestore.getInstance();
        cr = fbfs.collection("inventory");


        alInventory = new ArrayList<Inventory>();
        aaStudent = new CustomAdapter(this, R.layout.row, alInventory);
        lvStudent.setAdapter(aaStudent);

        //TODO: Task 3: Get real time updates from firestore by listening to collection "students"
        cr.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Log.w(TAG, "Listen failed.", error);
                    return;
                }
                alInventory.clear();
                for (QueryDocumentSnapshot doc : value) {
                    alInventory.add(new Inventory(doc.getId(), doc.getDouble("cost"), doc.getString("name"), (ArrayList<String>) doc.get("options")));
                }
                aaStudent.notifyDataSetChanged();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        // Handle action bar item clicks here.
        int id = item.getItemId();

        if (id == R.id.addStudent) {

            Intent intent = new Intent(getApplicationContext(), AddStudentDetailsActivity.class);
            startActivity(intent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}