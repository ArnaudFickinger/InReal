package com.fickinger.arnaud.inreal.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.Profile;
import com.fickinger.arnaud.inreal.R;
import com.fickinger.arnaud.inreal.adapter.ViewPagerAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class ConnectedFragment extends Fragment {

    private final String TAG = "ConnectedFragment";

    private Profile mProfile;
    private AccessToken mAccessToken;

    private ViewPager viewPager;
    private TabLayout tabLayout;

    private ViewPagerAdapter viewPagerAdapter;

    private String username;


    public ConnectedFragment() {
        // Required empty public constructor
    }

    public static ConnectedFragment newInstance(Profile profile, AccessToken accessToken){
        ConnectedFragment fragment = new ConnectedFragment();
        fragment.mProfile = profile;
        fragment.mAccessToken = accessToken;
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_connected, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewPager = view.findViewById(R.id.view_pager);
        tabLayout = view.findViewById(R.id.tab_layout);

        viewPagerAdapter.add(ProfileFragment.newInstance(mProfile, mAccessToken), "Profile");
        viewPagerAdapter.add(new DiscoverFragment(), "Discover");
        viewPagerAdapter.add(new ChatFragment(), "Chat");

        viewPager.setAdapter(viewPagerAdapter);

        tabLayout.setupWithViewPager(viewPager);
    }
}
