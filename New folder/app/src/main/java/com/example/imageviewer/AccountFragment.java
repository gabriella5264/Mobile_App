package com.example.imageviewer;


import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.*;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import static com.example.imageviewer.R.id.sendEmail;

/**
 * A simple {@link Fragment} subclass.
 */
public class AccountFragment extends Fragment {

    Context context;
    FloatingActionButton sendEmailbtn;
    private static final String TAG = "AccountFragment";
    private ListView mListView;
    ListAdapter adapter;

    public AccountFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        sendEmailbtn = (FloatingActionButton) view.findViewById(sendEmail);

        sendEmailbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = null, chooser = null;
                intent = new Intent(Intent.ACTION_SEND);
                intent.setData(Uri.parse("mailto:"));
                String[] to = {"yjj3@kent.ac.uk"};
                intent.putExtra(Intent.EXTRA_EMAIL, to);
                intent.putExtra(Intent.EXTRA_SUBJECT, "hi, this was sent from my app");
                intent.putExtra(Intent.EXTRA_TEXT, "hi, I want to get in touch with you");
                intent.setType("message/rfc822");
                chooser = Intent.createChooser(intent, "Send Email");
                startActivity(chooser);
            }
        });


        return view;
    }

}
