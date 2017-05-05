package com.programs.lala.lala.activties;

import android.annotation.TargetApi;
import android.app.DialogFragment;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.VideoView;

import com.programs.lala.lala.R;

/**
 * Created by UsersFiles on 10/8/2016.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class popmenu extends DialogFragment {
    @TargetApi(Build.VERSION_CODES.M)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().setTitle("Playing video ");
          View view =inflater.inflate(R.layout.pop_dialoage,container,false);
                VideoView vidView = (VideoView)view.findViewById(R.id.myVideo);

        Bundle bundle=new Bundle();
        bundle=this.getArguments();
        String vidAddress= bundle.getString("post_id");

        if (bundle!=null)
        {
        //String vidAddress = "https://archive.org/download/ksnn_compilation_master_the_internet/ksnn_compilation_master_the_internet_512kb.mp4";
        Uri vidUri = Uri.parse(vidAddress);
        vidView.setVideoURI(vidUri);
        MediaController vidControl = new MediaController(getActivity());
        vidControl.setAnchorView(vidView);
        vidView.setMediaController(vidControl);
        vidView.start();
        }
        return view ;
    }
}
