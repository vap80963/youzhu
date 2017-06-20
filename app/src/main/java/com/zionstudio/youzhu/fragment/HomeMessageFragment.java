package com.zionstudio.youzhu.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zionstudio.youzhu.R;


/**
 * Created by hasee on 11/11/2016.
 */
public class HomeMessageFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_message,container,false);
		return view;
	}
}
