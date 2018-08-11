package com.fickinger.arnaud.inreal.activity;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.fickinger.arnaud.inreal.R;
import com.fickinger.arnaud.inreal.fragment.ConnectedFragment;
import com.fickinger.arnaud.inreal.fragment.DisconnectedFragment;

import org.json.JSONObject;

public class ConnectActivity extends AppCompatActivity implements DisconnectedFragment.DisconnectedFragmentListener{

    private final String TAG = "ConnectActivity";

    private boolean isLoggedIn;
    private Profile profile;
    private AccessToken accessToken;
    private ProfileTracker mProfileTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connected);
    }

    @Override
    protected void onResume() {
        super.onResume();
        accessToken = AccessToken.getCurrentAccessToken();
        if (accessToken == null){
            openDisconnectedFragment();
        }
        else {
            onLoginSuccess();
        }
    }

    @Override
    public void onLoginSuccess() {
        if(Profile.getCurrentProfile() == null) {
            mProfileTracker = new ProfileTracker() {
                @Override
                protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
                    profile = currentProfile;
                    openConnectedFragment();
                    mProfileTracker.stopTracking();
                }
            };
            // no need to call startTracking() on mProfileTracker
            // because it is called by its constructor, internally.
        }
        else {
            profile = Profile.getCurrentProfile();
            openConnectedFragment();
        }


    }

    private void openDisconnectedFragment() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        DisconnectedFragment disconnectedFragment = DisconnectedFragment.newInstance(this);
        disconnectedFragment.setRetainInstance(true);
        fragmentTransaction.replace(R.id.fragment_container, disconnectedFragment, "disconnectedFragment");
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.commit();
    }

    private void openConnectedFragment() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        ConnectedFragment connectedFragment = ConnectedFragment.newInstance(profile, accessToken);
        connectedFragment.setRetainInstance(true);
        fragmentTransaction.replace(R.id.fragment_container, connectedFragment, "connectedFragment");
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.commit();
    }
}
