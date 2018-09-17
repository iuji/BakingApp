package com.example.android.bakingapp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.android.bakingapp.model.Step;

import org.parceler.Parcels;

public class InfoStepFragment extends Fragment {
    TextView toolbarTitle;
    TextView video;
    TextView textStepDescription;
    ImageButton beforeButton;
    ImageButton nextButton;
    int mPosition;

    // Define a new interface OnImageClickListener that triggers a callback in the host activity
    OnChangeStepClickListener mCallback;

    public InfoStepFragment(){
        // constructor
    }
    // OnImageClickListener interface, calls a method in the host activity named onImageSelected
    public interface OnChangeStepClickListener {
        void onChangeStep(int position);
    }

    // Override onAttach to make sure that the container activity has implemented the callback
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // This makes sure that the host activity has implemented the callback interface
        // If not, it throws an exception
        try {
            mCallback = (OnChangeStepClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnChangeStepClickListener");
        }
    }

    public void populateView(Step step, int position){
        mPosition = position;

        toolbarTitle.setText(step.getShortDescription());
        video.setText(step.getVideoURL());
        textStepDescription.setText(step.getDescription());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_info_step, container, false);

        toolbarTitle = rootView.findViewById(R.id.tv_toolbar_title);
        video = rootView.findViewById(R.id.video);
        textStepDescription = rootView.findViewById(R.id.tv_step_description);
        beforeButton = rootView.findViewById(R.id.ic_before);
        nextButton = rootView.findViewById(R.id.ic_next);

        Bundle bundle = getArguments();

        if(bundle != null){
            Step step = Parcels.unwrap(bundle.getParcelable("step"));
            int postion = bundle.getInt("position");
            populateView(step, postion);
        }

        beforeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCallback.onChangeStep(mPosition--);
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mCallback.onChangeStep(mPosition++);
            }
        });

        return rootView;
    }
}
