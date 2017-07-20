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
import android.content.Intent;
import android.app.Activity;
import android.net.Uri;

public class DisableHomeButton extends CordovaPlugin {
	
    public static final String TAG = "disablehomebutton";


    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
		
		JSONObject options = args.optJSONObject(0);
		
		if (action.equals("Disable")) { 
			/*
			if (options != null) {
				
				Uri myUri = Uri.parse(options.getString("PhotoURI"));
				
				Intent intent = new Intent();
				intent.setAction(Intent.ACTION_VIEW);
				intent.setDataAndType(myUri, "image/*");
				cordova.getActivity().startActivity(intent);	
				
			}
			*/
			
			JSONObject r = new JSONObject();
			if (options != null) r.put("options", options.getString("Action")); //opcija koju smo mu poslali preko JS
            r.put("custom", "neki moj text");
            //r.put("model", this.getModel());
			
			
            callbackContext.success(r);
			
        } else {
			callbackContext.error("action not recognised");
            return false;
        }
        return true;
		
    }



}
