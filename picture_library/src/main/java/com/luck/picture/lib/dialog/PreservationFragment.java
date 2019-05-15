package com.luck.picture.lib.dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.luck.picture.lib.R;
import com.luck.picture.lib.eventbus.PreservationEvent;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by lenovo on 2018/12/10.
 */

public class PreservationFragment extends DialogFragment {

    private LinearLayout mPreservationLl;
    private String path;

    public static PreservationFragment newInstance(String path) {
        PreservationFragment fragment = new PreservationFragment();
        Bundle bundle = new Bundle();
        bundle.putString("path", path);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        path = getArguments().getString("path");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_video_path_dialog, container, false);
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mPreservationLl = (LinearLayout) view.findViewById(R.id.ll_preservation);
        mPreservationLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PreservationFragment.this.dismiss();
                EventBus.getDefault().post(new PreservationEvent(path));
            }
        });
    }
}
