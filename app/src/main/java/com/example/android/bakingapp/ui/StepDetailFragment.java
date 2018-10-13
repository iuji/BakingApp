package com.example.android.bakingapp.ui;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.model.Step;
import com.example.android.bakingapp.utils.Validator;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class StepDetailFragment extends Fragment {
    @BindView(R.id.tv_toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.tv_no_video)
    TextView textNoVideo;
    @BindView(R.id.media_player)
    SimpleExoPlayerView mPlayerView;
    @BindView(R.id.tv_step_description)
    TextView textStepDescription;
    @BindView(R.id.include)
    ConstraintLayout toolbar;
    @BindView(R.id.view_before)
    View viewBefore;
    @BindView(R.id.ic_before)
    ImageButton iconBefore;
    @BindView(R.id.tv_before)
    TextView textBefore;
    @BindView(R.id.view_next)
    View viewNext;
    @BindView(R.id.ic_next)
    ImageButton iconNext;
    @BindView(R.id.tv_next)
    TextView textNext;

    private SimpleExoPlayer mExoPlayer;
    private int mPosition;
    private Step mStep;

    // Define a new interface OnImageClickListener that triggers a callback in the host activity
    OnChangeStepClickListener mCallback;

    public StepDetailFragment() {
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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_info_step, container, false);
        ButterKnife.bind(this, rootView);

        Bundle bundle = getArguments();
        if (Validator.isLandscapeAndIsNotTablet(getContext())) {
            toolbar.setVisibility(View.GONE);
            viewBefore.setVisibility(View.GONE);
            iconBefore.setVisibility(View.GONE);
            textBefore.setVisibility(View.GONE);
            viewNext.setVisibility(View.GONE);
            iconNext.setVisibility(View.GONE);
            textNext.setVisibility(View.GONE);
        }

        if (savedInstanceState == null) {
            if (bundle != null) {
                Step step = Parcels.unwrap(bundle.getParcelable("step"));
                int postion = bundle.getInt("position");
                populateView(step, postion);
            }
        } else {
            Step step = Parcels.unwrap(savedInstanceState.getParcelable("step"));
            int position = savedInstanceState.getInt("position");
            populateView(step, position);
        }

        return rootView;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("step", Parcels.wrap(mStep));
        outState.putInt("position", mPosition);
    }

    @OnClick(R.id.view_before)
    void onBeforeButtonClick() {
        mCallback.onChangeStep(mPosition - 1);
    }

    @OnClick(R.id.view_next)
    void onNextButtonClick() {
        mCallback.onChangeStep(mPosition + 1);
    }

    private void setupPlayer() {
        if (!mStep.getVideoURL().equals("")) {
            textNoVideo.setVisibility(View.GONE);
            mPlayerView.setVisibility(View.VISIBLE);
            initializePlayer(Uri.parse(mStep.getVideoURL()));
        } else {
            mPlayerView.setVisibility(View.GONE);
            textNoVideo.setVisibility(View.VISIBLE);
        }
    }

    public void populateView(Step step, int position) {
        if(step != null){
            mStep = step;
            mPosition = position;
            toolbarTitle.setText(step.getShortDescription());
            textStepDescription.setText(step.getDescription());
            releasePlayer();
            setupPlayer();
        }
    }

    private void initializePlayer(Uri mediaUri) {
        if (mExoPlayer == null) {
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelector, loadControl);
            mPlayerView.setPlayer(mExoPlayer);

            String userAgent = Util.getUserAgent(getActivity(), "Baking video");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                    getActivity(), userAgent), new DefaultExtractorsFactory(), null, null);
            mExoPlayer.prepare(mediaSource);
            mExoPlayer.setPlayWhenReady(true);
        }
    }

    private void releasePlayer() {
        if (!mStep.getVideoURL().equals("") && mExoPlayer != null) {
            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mStep != null) releasePlayer();
    }
}
