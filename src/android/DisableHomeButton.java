/*
       Licensed to the Apache Software Foundation (ASF) under one
       or more contributor license agreements.  See the NOTICE file
       distributed with this work for additional information
       regarding copyright ownership.  The ASF licenses this file
       to you under the Apache License, Version 2.0 (the
       "License"); you may not use this file except in compliance
       with the License.  You may obtain a copy of the License at

         http://www.apache.org/licenses/LICENSE-2.0

       Unless required by applicable law or agreed to in writing,
       software distributed under the License is distributed on an
       "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
       KIND, either express or implied.  See the License for the
       specific language governing permissions and limitations
       under the License.
*/

package org.apache.cordova.disablehomebutton;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.net.Uri;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;

public class DisableHomeButton extends CordovaPlugin {
	
    private static final String TAG = "FBLOG";
	private static int disable_chk = 0;
	private WindowManager manager;
	private customViewGroup view;
	
	/* *** BITNO *** */
	// na telefonu postaviti opciju Screen Lock > None //
	/* *** BITNO *** */
		
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
		
		JSONObject options = args.optJSONObject(0);
		
		if (action.equals("DisableButton")) { 
			
			if (disable_chk == 0) {
				disable_chk = 1;
				disablePullNotificationTouch(); //zatvara notification bar
				//HomeKeyLocker_Lock(); //zatvara home dugme
			}
				
			JSONObject r = new JSONObject();
			if (options != null) r.put("options", options.getString("ActionOption")); //opcija koju smo mu poslali preko JS
			r.put("custom", "neki moj text disable");
            callbackContext.success(r);
			
			
        } else if (action.equals("EnableButton")) { 
			
			if (disable_chk == 1) {
				disable_chk = 0;
				enablePullNotificationTouch(); //otvara notification bar
				//HomeKeyLocker_UnLock(); //otvara home dugme
			}
				
			JSONObject r = new JSONObject();
			if (options != null) r.put("options", options.getString("ActionOption")); //opcija koju smo mu poslali preko JS
			r.put("custom", "neki moj text enable");
            callbackContext.success(r);
			
			
        } else {
			
			callbackContext.error("Action not recognised");
            return false;
			
        }
		
        return true;
		
    }
	
	
	
	/* NOTIFICATION BAR */ 
	private void disablePullNotificationTouch() {
		manager = ((WindowManager) this.cordova.getActivity().getApplicationContext().getSystemService(Context.WINDOW_SERVICE));
        WindowManager.LayoutParams localLayoutParams = new WindowManager.LayoutParams();
        localLayoutParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ERROR;
        localLayoutParams.gravity = Gravity.TOP;
        localLayoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |

                // this is to enable the notification to recieve touch events
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL |

                // Draws over status bar
                WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;

        localLayoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        localLayoutParams.height = (int) (25 * this.cordova.getActivity().getResources()
                .getDisplayMetrics().scaledDensity);
        localLayoutParams.format = PixelFormat.RGBX_8888;
        view = new customViewGroup(this.cordova.getActivity());
        manager.addView(view, localLayoutParams);
    }
	
	
	private void enablePullNotificationTouch() {
		
		 manager.removeView(view);
		
	}

	
	//Add this class in your project
	public class customViewGroup extends ViewGroup {

	    public customViewGroup(Context context) {
	        super(context);
	    }

	    @Override
	    protected void onLayout(boolean changed, int l, int t, int r, int b) {
	    }

	    @Override
	    public boolean onInterceptTouchEvent(MotionEvent ev) {
	    	//Log.i(TAG, String.valueOf("Intercepted"));
	        return true;
	    }
	    
	}
	/* NOTIFICATION BAR - END */


}
