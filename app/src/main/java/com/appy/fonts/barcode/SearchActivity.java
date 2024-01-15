package com.appy.fonts.barcode;

import android.animation.*;
import android.app.*;
import android.app.Activity;
import android.content.*;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.*;
import android.graphics.*;
import android.graphics.drawable.*;
import android.media.*;
import android.net.*;
import android.net.Uri;
import android.os.*;
import android.os.Bundle;
import android.text.*;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.style.*;
import android.util.*;
import android.view.*;
import android.view.View;
import android.view.View.*;
import android.view.animation.*;
import android.webkit.*;
import android.widget.*;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.annotation.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.*;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import androidx.webkit.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wuyr.rippleanimation.*;
import java.io.*;
import java.io.InputStream;
import java.text.*;
import java.util.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.*;
import org.json.*;

public class SearchActivity extends AppCompatActivity {
	
	private Timer _timer = new Timer();
	
	private HashMap<String, Object> temp_map = new HashMap<>();
	private double n1 = 0;
	private HashMap<String, Object> cacheMap = new HashMap<>();
	
	private ArrayList<HashMap<String, Object>> lmap = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> itemsList = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> cache = new ArrayList<>();
	
	private LinearLayout linear1;
	private LinearLayout linear2;
	private LinearLayout linear3;
	private LinearLayout linear4;
	private RecyclerView recyclerview1;
	private ImageView imageview1;
	private EditText edittext1;
	private ImageView imageview3;
	
	private Intent i1 = new Intent();
	private SharedPreferences theme;
	private TimerTask tmr;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.search);
		initialize(_savedInstanceState);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		linear1 = findViewById(R.id.linear1);
		linear2 = findViewById(R.id.linear2);
		linear3 = findViewById(R.id.linear3);
		linear4 = findViewById(R.id.linear4);
		recyclerview1 = findViewById(R.id.recyclerview1);
		imageview1 = findViewById(R.id.imageview1);
		edittext1 = findViewById(R.id.edittext1);
		imageview3 = findViewById(R.id.imageview3);
		theme = getSharedPreferences("theme", Activity.MODE_PRIVATE);
		
		imageview1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				finish();
			}
		});
		
		edittext1.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {
				final String _charSeq = _param1.toString();
				if (edittext1.getText().toString().length() > 0) {
					_endTimer(tmr);
					tmr = new TimerTask() {
						@Override
						public void run() {
							runOnUiThread(new Runnable() {
								@Override
								public void run() {
									_searchInListmap(edittext1.getText().toString().toLowerCase(), cache, "family");
									recyclerview1.setAdapter(new Recyclerview1Adapter(cache));
									if (cache.size() > 0) {
										recyclerview1.setVisibility(View.VISIBLE);
									}
									else {
										recyclerview1.setVisibility(View.GONE);
									}
								}
							});
						}
					};
					_timer.schedule(tmr, (int)(500));
				}
				else {
					recyclerview1.setAdapter(new Recyclerview1Adapter(lmap));
				}
			}
			
			@Override
			public void beforeTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {
				
			}
			
			@Override
			public void afterTextChanged(Editable _param1) {
				
			}
		});
	}
	
	private void initializeLogic() {
		lmap = new Gson().fromJson(getIntent().getStringExtra("data"), new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());
		recyclerview1.setAdapter(new Recyclerview1Adapter(lmap));
		recyclerview1.setLayoutManager(new LinearLayoutManager(this));
		int nightModeFlags = getResources().getConfiguration().uiMode & android.content.res.Configuration.UI_MODE_NIGHT_MASK;
		if (nightModeFlags == android.content.res.Configuration.UI_MODE_NIGHT_YES) {
			
			//dark mode
			_dark();
		} else {
			//light mode
			_light();
		}
		if (theme.getString("theme", "").equals("true")) {
			_light();
		}
		else {
			_dark();
		}
		edittext1.setSingleLine(true);
	}
	
	public void _clickAnimation(final View _view) {
		ScaleAnimation fade_in = new ScaleAnimation(0.9f, 1f, 0.9f, 1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.7f);
		fade_in.setDuration(300);
		fade_in.setFillAfter(true);
		_view.startAnimation(fade_in);
	}
	
	
	public void _light() {
		getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
		getWindow().setStatusBarColor(0xFFFFFFFF);
		linear1.setBackgroundColor(0xFFFFFFFF);
	}
	
	
	public void _dark() {
		View view = getWindow().getDecorView();
		    view.setSystemUiVisibility(view.getSystemUiVisibility() & ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
		if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
			Window w =SearchActivity.this.getWindow();
			w.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			w.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS); w.setStatusBarColor(0xFF000000);
		}
		linear1.setBackgroundColor(0xFF000000);
	}
	
	
	public void _searchInListmap(final String _value, final ArrayList<HashMap<String, Object>> _listmap, final String _key) {
		_listmap.clear();
		n1 = 0;
		for(int _repeat13 = 0; _repeat13 < (int)(lmap.size()); _repeat13++) {
			if (lmap.get((int)n1).get(_key).toString().toLowerCase().contains(_value) || lmap.get((int)n1).get("category").toString().toLowerCase().contains(_value)) {
				cacheMap = lmap.get((int)n1);
				_listmap.add(cacheMap);
			}
			n1++;
		}
	}
	
	
	public void _endTimer(final TimerTask _tmr) {
		try {
			_tmr.cancel();
		} catch (Exception e) {
		};
	}
	
	public class Recyclerview1Adapter extends RecyclerView.Adapter<Recyclerview1Adapter.ViewHolder> {
		
		ArrayList<HashMap<String, Object>> _data;
		
		public Recyclerview1Adapter(ArrayList<HashMap<String, Object>> _arr) {
			_data = _arr;
		}
		
		@Override
		public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			LayoutInflater _inflater = getLayoutInflater();
			View _v = _inflater.inflate(R.layout.listfonts_adapter, null);
			RecyclerView.LayoutParams _lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			_v.setLayoutParams(_lp);
			return new ViewHolder(_v);
		}
		
		@Override
		public void onBindViewHolder(ViewHolder _holder, final int _position) {
			View _view = _holder.itemView;
			
			final LinearLayout linear1 = _view.findViewById(R.id.linear1);
			final androidx.cardview.widget.CardView cardview1 = _view.findViewById(R.id.cardview1);
			final LinearLayout bg = _view.findViewById(R.id.bg);
			final WebView webview1 = _view.findViewById(R.id.webview1);
			final TextView textview4 = _view.findViewById(R.id.textview4);
			final LinearLayout linear3 = _view.findViewById(R.id.linear3);
			final TextView prev_btn = _view.findViewById(R.id.prev_btn);
			
			int nightModeFlags = getResources().getConfiguration().uiMode & android.content.res.Configuration.UI_MODE_NIGHT_MASK;
			if (nightModeFlags == android.content.res.Configuration.UI_MODE_NIGHT_YES) {
				
				//dark mode
				textview4.setTextColor(0xFFFFFFFF);
				cardview1.setCardBackgroundColor(0xFF121212);
				linear1.setBackgroundColor(0xFF000000);
				bg.setBackgroundColor(0xFF121212);
				if (WebViewFeature.isFeatureSupported(WebViewFeature.FORCE_DARK));
				WebSettingsCompat.setForceDark(webview1.getSettings(), WebSettingsCompat.FORCE_DARK_ON);
			} else {
				//light mode
				textview4.setTextColor(0xFF000000);
				cardview1.setCardBackgroundColor(0xFFFFFFFF);
				bg.setBackgroundColor(0xFFFFFFFF);
				linear1.setBackgroundColor(0xFFFFFFFF);
				if (WebViewFeature.isFeatureSupported(WebViewFeature.FORCE_DARK));
				WebSettingsCompat.setForceDark(webview1.getSettings(), WebSettingsCompat.FORCE_DARK_OFF);
			}
			if (theme.getString("theme", "").equals("true")) {
				textview4.setTextColor(0xFF000000);
				cardview1.setCardBackgroundColor(0xFFFFFFFF);
				bg.setBackgroundColor(0xFFFFFFFF);
				linear1.setBackgroundColor(0xFFFFFFFF);
				if (WebViewFeature.isFeatureSupported(WebViewFeature.FORCE_DARK));
				WebSettingsCompat.setForceDark(webview1.getSettings(), WebSettingsCompat.FORCE_DARK_OFF);
			}
			else {
				textview4.setTextColor(0xFFFFFFFF);
				cardview1.setCardBackgroundColor(0xFF121212);
				linear1.setBackgroundColor(0xFF000000);
				bg.setBackgroundColor(0xFF121212);
				if (WebViewFeature.isFeatureSupported(WebViewFeature.FORCE_DARK));
				WebSettingsCompat.setForceDark(webview1.getSettings(), WebSettingsCompat.FORCE_DARK_ON);
			}
			webview1.loadUrl("data:text/html,".concat("<style type=\"text/css\">\n@font-face {\n    font-family: \"My Custom Font\";\n    src: url(".concat(_data.get((int)_position).get("url").toString().concat(") format(\"truetype\");\n}\np.customfont { \n    font-family: \"My Custom Font\", Verdana, Tahoma;\n  font-size: 30px;\n}\n</style>\n<center>\n<p class=\"customfont\">".concat(_data.get((int)_position).get("family").toString().concat("</p>\n</center>"))))));
			if (_data.get((int)_position).containsKey("category")) {
				textview4.setText(_data.get((int)_position).get("category").toString());
			}
			cardview1.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View _view) {
					_clickAnimation(cardview1);
					temp_map = _data.get((int)_position);
					i1.setClass(getApplicationContext(), ViewActivity.class);
					i1.putExtra("json", _data.get((int)_position).get("json").toString());
					i1.putExtra("data", new Gson().toJson(temp_map));
					startActivity(i1);
					finish();
				}
			});
		}
		
		@Override
		public int getItemCount() {
			return _data.size();
		}
		
		public class ViewHolder extends RecyclerView.ViewHolder {
			public ViewHolder(View v) {
				super(v);
			}
		}
	}
	
	@Deprecated
	public void showMessage(String _s) {
		Toast.makeText(getApplicationContext(), _s, Toast.LENGTH_SHORT).show();
	}
	
	@Deprecated
	public int getLocationX(View _v) {
		int _location[] = new int[2];
		_v.getLocationInWindow(_location);
		return _location[0];
	}
	
	@Deprecated
	public int getLocationY(View _v) {
		int _location[] = new int[2];
		_v.getLocationInWindow(_location);
		return _location[1];
	}
	
	@Deprecated
	public int getRandom(int _min, int _max) {
		Random random = new Random();
		return random.nextInt(_max - _min + 1) + _min;
	}
	
	@Deprecated
	public ArrayList<Double> getCheckedItemPositionsToArray(ListView _list) {
		ArrayList<Double> _result = new ArrayList<Double>();
		SparseBooleanArray _arr = _list.getCheckedItemPositions();
		for (int _iIdx = 0; _iIdx < _arr.size(); _iIdx++) {
			if (_arr.valueAt(_iIdx))
			_result.add((double)_arr.keyAt(_iIdx));
		}
		return _result;
	}
	
	@Deprecated
	public float getDip(int _input) {
		return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, _input, getResources().getDisplayMetrics());
	}
	
	@Deprecated
	public int getDisplayWidthPixels() {
		return getResources().getDisplayMetrics().widthPixels;
	}
	
	@Deprecated
	public int getDisplayHeightPixels() {
		return getResources().getDisplayMetrics().heightPixels;
	}
}