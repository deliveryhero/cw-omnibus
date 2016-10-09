/***
 Copyright (c) 2012-2016 CommonsWare, LLC
 Licensed under the Apache License, Version 2.0 (the "License"); you may not
 use this file except in compliance with the License. You may obtain a copy
 of the License at http://www.apache.org/licenses/LICENSE-2.0. Unless required
 by applicable law or agreed to in writing, software distributed under the
 License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS
 OF ANY KIND, either express or implied. See the License for the specific
 language governing permissions and limitations under the License.

 From _The Busy Coder's Guide to Android Development_
 https://commonsware.com/Android
 */

package com.commonsware.android.containers.sampler;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MainActivity extends Activity {
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);

    ViewPager pager=(ViewPager)findViewById(R.id.pager);

    pager.setAdapter(new SampleAdapter(getFragmentManager()));
  }

  private class SampleAdapter extends FragmentPagerAdapter {
    private int[] layouts;
    private String[] titles;

    SampleAdapter(FragmentManager mgr) {
      super(mgr);
      layouts=getLayoutsArray(R.array.layouts);
      titles=getResources().getStringArray(R.array.titles);
    }

    @Override
    public int getCount() {
      return(titles.length);
    }

    @Override
    public Fragment getItem(int position) {
      return(LayoutFragment.newInstance(layouts[position]));
    }

    @Override
    public CharSequence getPageTitle(int position) {
      return(titles[position]);
    }

    int[] getLayoutsArray(int arrayResourceId) {
      TypedArray typedArray=
        getResources().obtainTypedArray(arrayResourceId);
      int[] result=new int[typedArray.length()];

      for (int i=0;i<typedArray.length();i++) {
        result[i]=typedArray.getResourceId(i, -1);
      }

      return(result);
    }
  }

  public static class LayoutFragment extends Fragment {
    private static final String ARG_LAYOUT="layout";

    static LayoutFragment newInstance(int layoutId) {
      LayoutFragment result=new LayoutFragment();
      Bundle args=new Bundle();

      args.putInt(ARG_LAYOUT, layoutId);
      result.setArguments(args);

      return(result);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
      return(inflater.inflate(getArguments().getInt(ARG_LAYOUT),
        container, false));
    }
  }
}