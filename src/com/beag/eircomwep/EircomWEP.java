/*
 * Copyright (C) 2009 Paul McCann
 * Contact ismisepaul@gmail.com
 *    
 * Program to generate an Eircom WEP password from an Eircom SSID
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 */

package com.beag.eircomwep;

import com.beag.eircomwep.calc.CalcWEP;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.ClipboardManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class EircomWEP extends Activity implements OnClickListener {
	
	EditText ssidTextEdit;
	TextView eircom;
	Button go;

	EditText key1Text;
	EditText key2Text;
	EditText key3Text;
	EditText key4Text;
		
	TextView info;
	TextView wepkey;

	private AlertDialog alertDialog;

	private static final String TAG = "Debug of EircomWEPGen";

public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		ssidTextEdit = (EditText) this.findViewById(R.id.ssidTextEdit);
		ssidTextEdit.setOnClickListener(this);

		go = (Button) this.findViewById(R.id.go);
		// go.setEnabled(false);
		go.setOnClickListener(this);

		key1Text = (EditText) this.findViewById(R.id.key1Text);
		key2Text = (EditText) this.findViewById(R.id.key2Text);
		key3Text = (EditText) this.findViewById(R.id.key3Text);
		key4Text = (EditText) this.findViewById(R.id.key4Text);
			
		key1Text.setOnClickListener(this);

	}

	public void onClick(View v) {
			
		if (key1Text.isPressed()){
			ClipboardManager clipboard = 
			      (ClipboardManager) getSystemService(CLIPBOARD_SERVICE); 

			 clipboard.setText(key1Text.getText());
		}

		if (ssidTextEdit.isPressed()) {
			ssidTextEdit.setText("");
		}

		if (go.isPressed()) {
			String str = ssidTextEdit.getText().toString();
			for (int i = 0; i < str.length(); i++) {
				Log.d(TAG, "the vaule is =" + i);
				if (str.charAt(i) == '8' | str.charAt(i) == '9') {
					alertDialog = new AlertDialog.Builder(this).create();
					alertDialog.setTitle("Invalid SSID");
					alertDialog.setMessage("Numbers from 0 - 7 only");
					alertDialog.setButton("OK",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									return;
								}
							});
					alertDialog.show();
				} else {
					InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(ssidTextEdit.getWindowToken(), 0);
					getKeys();
				}
			}
		}
	}

	public void getKeys() {
		String s = null;
		s = ssidTextEdit.getText().toString();
		CalcWEP cw = new CalcWEP(s);
		cw.calc();
		key1Text.setText(cw.format().substring(0, 26));
		key2Text.setText(cw.format().substring(26, 52));
		key3Text.setText(cw.format().substring(52, 78));
		key4Text.setText(cw.format().substring(78, 104));

	}

}
