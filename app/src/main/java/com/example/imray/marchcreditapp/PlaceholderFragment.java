package com.example.imray.marchcreditapp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    private RecyclerView recyclerView;
    private View rootView;
    private ImageUpdatedListener updateViewListener = new ImageUpdatedListener();
    private MyRecycleAdapter myRecycleAdapter = new MyRecycleAdapter(new ImageKeeper(updateViewListener));


    private class ImageUpdatedListener implements ImageReadyListener
    {

        @Override
        public void onImageDownloaded(byte[] image) {
            if(recyclerView != null) {
                recyclerView.getAdapter().notifyDataSetChanged();
            }
        }
    }

    public PlaceholderFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static PlaceholderFragment newInstance(int sectionNumber) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("Fragment", "onCreateView");
        rootView = inflater.inflate(R.layout.fragment_main, container, false);
        TextView textView = (TextView) rootView.findViewById(R.id.section_label);
        textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));

        recyclerView = (RecyclerView) rootView.findViewById(R.id.imagelist);
        if(recyclerView == null)
        {
            Log.e("PlaceholderFragment", "NULL RECYCLER VIEW");
        }
        recyclerView.setLayoutManager(new GridLayoutManager(recyclerView.getContext(), 3));
        recyclerView.setAdapter(myRecycleAdapter);

        return rootView;
    }
}

