package sg.edu.rp.webservices.c302_p11_firebasefirestore;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class EditStudentDetailsActivity extends AppCompatActivity {

    TextView tv1;
    Button b1;
    LinearLayout ll1;
    EditText et1, et2;
    FirebaseFirestore fbfs;
    CollectionReference cr;
    DocumentReference dr;
    ArrayList<CheckBox> cbList;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_details);

        Intent i = getIntent();
        id = i.getStringExtra("ID");

        tv1 = findViewById(R.id.textView2);
        b1 = findViewById(R.id.button);
        ll1 = findViewById(R.id.checkBoxContainer);
        et1 = findViewById(R.id.et1);
        et2 = findViewById(R.id.et2);

        fbfs = FirebaseFirestore.getInstance();
        cr = fbfs.collection("options");
        tv1.setText("Edit Item");
        cr.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                cbList = new ArrayList<>();
                for (QueryDocumentSnapshot doc : value) {
                    CheckBox checkBox = new CheckBox(EditStudentDetailsActivity.this);
                    checkBox.setText(doc.getString("name"));
                    checkBox.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    // Add Checkbox to LinearLayout
                    if (ll1 != null) {
                        ll1.addView(checkBox);
                        cbList.add(checkBox);
                    }
                }
            }
        });

        dr = fbfs.collection("inventory").document(id);
        dr.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot ds = task.getResult();
                et1.setText(ds.getString("name"));
                et2.setText(ds.getDouble("cost") + "");
                ArrayList<String> al = (ArrayList<String>) ds.get("options");
                for (String i:al) {
                    for (CheckBox j:cbList) {
                        if (i.equalsIgnoreCase(j.getText().toString())) {
                            j.setChecked(true);
                        }
                    }
                }
            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> alnames = new ArrayList<>();
                for (CheckBox i:cbList) {
                    if (i.isChecked()) {
                        alnames.add(i.getText().toString());
                    }
                }
                dr.set(new Inventory(id, Double.parseDouble(et2.getText().toString()), et1.getText().toString(), alnames));
                finish();
            }
        });

    }
}