package com.fickinger.arnaud.inreal.fragment;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.fickinger.arnaud.inreal.R;
import com.fickinger.arnaud.inreal.util.GlideUtil;

import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    private final String TAG = "ProfileFragment";

    private ImageView mImageView;
    private TextView usernameTextView;

    private Profile mProfile;
    private AccessToken mAccessToken;


    public ProfileFragment() {
        // Required empty public constructor
    }

    public static ProfileFragment newInstance(Profile profile, AccessToken accessToken){
        ProfileFragment fragment = new ProfileFragment();
        fragment.mAccessToken = accessToken;
        fragment.mProfile = profile;
        return  fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        usernameTextView = view.findViewById(R.id.username);
        usernameTextView.setText(mProfile.getName());

        mImageView = view.findViewById(R.id.profile_picture);
        GraphRequest request = GraphRequest.newMeRequest(
                mAccessToken,
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(
                            JSONObject object,
                            GraphResponse response) {
                        if (object!=null){
                            Log.d(TAG, object.toString());
                            Log.d(TAG, response.getJSONObject().toString());
                            try {
                                String profilePicUrl = object.getJSONObject("picture").getJSONObject("data").getString("url");
                                GlideUtil.loadProfileIcon(profilePicUrl, mImageView);
                            }
                            catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                        else{
                            Log.d(TAG, "null response");
                        }
                    }
                });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "picture.type(large)");
        request.setParameters(parameters);

        request.executeAsync();



    }

    private class RequestPictureAsync extends AsyncTask<Integer, Void, Integer> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Integer doInBackground(Integer... params) {
            GraphRequest request = GraphRequest.newMeRequest(
                    mAccessToken,
                    new GraphRequest.GraphJSONObjectCallback() {
                        @Override
                        public void onCompleted(
                                JSONObject object,
                                GraphResponse response) {
                            if (object!=null){
                                Log.d(TAG, object.toString());
                                Log.d(TAG, response.getJSONObject().toString());
                                try {
                                    String profilePicUrl = object.getJSONObject("picture").getJSONObject("data").getString("url");
                                    URL url = new URL(profilePicUrl);
                                    Bitmap profilePic = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                                    mImageView.setImageBitmap(profilePic);
                                }
                                catch (Exception e) {
                                    e.printStackTrace();
                                }

                            }
                            else{
                                Log.d(TAG, "null response");
                            }
                        }
                    });

            Bundle parameters = new Bundle();
            parameters.putString("fields", "picture.type(large)");
            request.setParameters(parameters);

            request.executeAsync();
            return 0;
        }

        @Override
        protected void onPostExecute(Integer i) {
            super.onPostExecute(i);

        }
    }
}
