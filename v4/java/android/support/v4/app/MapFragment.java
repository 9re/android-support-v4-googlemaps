/*
 * Copyright (C) 2012 Massimiliano Leone - massimiliano.leone@iubris.net
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package android.support.v4.app;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.google.android.maps.MapView;

public abstract class MapFragment extends Fragment {

	private View mapViewContainer;
	
	/**
	 * here we instance the viewcontainer returned by onViewCreated,
	 * inflating from layout provided by (your concrete) providesMapViewContainerId method;
	 * something as:
	 * 
	 * <pre> return LayoutInflater.from(activity).inflate(R.layout.yourmaplayout, null);</pre> <br/>
	 */
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);		
		
		if (mapViewContainer == null) {
			mapViewContainer = 	LayoutInflater.from(activity).inflate( providesMapViewContainerId(), null);
		}
	}	
	
	/**
	 * something as: <br/>
	 * in your concrete class you have to provide the layout containing mapview:
	 * <pre> return R.layout.map;</pre> <br/>
	 * where R.layout.map is map.xml file in layout directory, declaring a RelativeLayout,
	 * which contains Mapview, as below:<br/>
	 * <pre>
&ltRelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
	android:id="@+id/maplayout"
	android:orientation="vertical"
	android:layout_width="fill_parent"
	android:layout_height="fill_parent"&gt
 	
 	&ltcom.google.android.maps.MapView
		android:id="@+id/mapview"
		android:layout_width="fill_parent"
		android:layout_height="fill_parent"
		android:clickable="true"
		android:apiKey="YOUR_APIKEY"/&gt
&lt/RelativeLayout&gt
	 *</pre>
	 * 
	 * @param activity
	 * @return View
	 */
	protected abstract int providesMapViewContainerId();
	
	protected void addMapViewToViewParent(View view, MapView mapView) {

		ViewGroup viewGroup =	 (ViewGroup) view.getParent();

		if (viewGroup == null) viewGroup = (ViewGroup) view;

		int childs = viewGroup.getChildCount();
		if (childs == 0) {
			viewGroup.addView(mapView); 
		}else {
			for (int i = 0; i < viewGroup.getChildCount(); i++) {
				View child = viewGroup.getChildAt(i);
				if (child instanceof FrameLayout) {
					continue;
				} else if (child instanceof MapView) {
					viewGroup.removeView(child);
	            	viewGroup.addView(mapView, 1);	            	
	            } else {
	            	viewGroup.removeView(child);            	
	            }
			}
			//viewGroup.addView(mapView, 0);
		}
      	viewGroup.invalidate();
	}	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		return mapViewContainer;
	}
	
	@Override
	public void onDestroyView() {
		super.onDestroyView();
		ViewGroup parentViewGroup = (ViewGroup) mapViewContainer.getParent();
		if( parentViewGroup != null ) {			
			parentViewGroup.removeView( mapViewContainer );
			//parentViewGroup.removeAllViews();
		}
	}	
}
