package com.johnwoconnor.experiments.apod;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class DetailFragment extends Fragment {

    private String mName;
    private String mDetails;

    TextView mDetailName;
    TextView mDetailDescription;

//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment DetailFragment.
//     */
//    // TODO: Rename and change types and number of parameters
//    public static DetailFragment newInstance(String name, String details) {
//        DetailFragment fragment = new DetailFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }
    // DetailFragment f = new DetailFragment("name", "details");
    // PoiDialog dialog = new PoiDialog(POI myPoi);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        mDetailName = (TextView)view.findViewById(R.id.detailName);
        mDetailDescription = (TextView) view.findViewById(R.id.detailDescription);

        if (getArguments() != null) {
            mName = getArguments().getString("Title");
            mDetails = getArguments().getString("Description");
            mDetailName.setText(mName);
            mDetailDescription.setText(mDetails);
        }

        return view;
    }
}
