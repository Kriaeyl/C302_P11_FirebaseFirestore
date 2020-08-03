package sg.edu.rp.webservices.c302_p11_firebasefirestore;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter<Inventory> {
    private Context ctx;
    private ArrayList<Inventory> al;

    public CustomAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Inventory> objects) {
        super(context, resource, objects);
        this.ctx = context;
        this.al = objects;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable final View convertView, @NonNull final ViewGroup parent) {
        LayoutInflater li = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = li.inflate(R.layout.row, parent, false);
        TextView tv1 = rowView.findViewById(R.id.textView);
        ImageView iv1 = rowView.findViewById(R.id.imageView);
        ImageView iv2 = rowView.findViewById(R.id.imageView2);

        tv1.setText(al.get(position).getName());
        iv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ctx, EditStudentDetailsActivity.class);
                intent.putExtra("ID", al.get(position).getId());
                ctx.startActivity(intent);
            }
        });

        iv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseFirestore fbfs = FirebaseFirestore.getInstance();
                DocumentReference dr = fbfs.collection("inventory").document(al.get(position).getId());
                dr.delete();
                getView(position, convertView, parent);
            }
        });

        return rowView;
    }
}
