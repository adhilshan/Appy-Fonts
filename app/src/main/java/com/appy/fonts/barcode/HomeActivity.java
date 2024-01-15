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
import android.text.style.*;
import android.util.*;
import android.view.*;
import android.view.View;
import android.view.View.*;
import android.view.animation.*;
import android.webkit.*;
import android.widget.*;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.*;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.*;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import androidx.webkit.*;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.gson.Gson;
import com.wuyr.rippleanimation.*;
import java.io.*;
import java.io.InputStream;
import java.text.*;
import java.util.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.*;
import org.json.*;
import com.wuyr.rippleanimation.RippleAnimation;;

public class HomeActivity extends AppCompatActivity {
	
	private Toolbar _toolbar;
	private AppBarLayout _app_bar;
	private CoordinatorLayout _coordinator;
	private DrawerLayout _drawer;
	private String variant = "";
	private String family = "";
	private String category = "";
	private String version = "";
	private String errorMessage = "";
	private HashMap<String, Object> map1 = new HashMap<>();
	private String variable = "";
	private double k = 0;
	private boolean isThemeLight = false;
	private double n = 0;
	private HashMap<String, Object> map = new HashMap<>();
	private HashMap<String, Object> temp_map = new HashMap<>();
	
	private ArrayList<HashMap<String, Object>> list = new ArrayList<>();
	private ArrayList<String> str2 = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> varient_list = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> link = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> lmap = new ArrayList<>();
	
	private CollapsingToolbarLayout collapsingtoolbar1;
	private LinearLayout linear1;
	private LinearLayout toollayout;
	private LinearLayout linear2;
	private LinearLayout searchbar;
	private ImageView drawer;
	private TextView textview1;
	private ImageView imageview2;
	private ImageView dorl;
	private LinearLayout srch_l;
	private LinearLayout srch_r;
	private TextView textview2;
	private ImageView ic_search_view;
	private LinearLayout buffer_lin;
	private RecyclerView recyclerview1;
	private ProgressBar progressbar1;
	private LinearLayout error_lin;
	private ImageView imageview1;
	private TextView textview3;
	private LinearLayout _drawer_linear1;
	private LinearLayout _drawer_linear4;
	private LinearLayout _drawer_linear3;
	private LinearLayout _drawer_linear2;
	private ImageView _drawer_imageview1;
	private LinearLayout _drawer_linear5;
	private ImageView _drawer_close;
	private TextView _drawer_textview8;
	private LinearLayout _drawer_linear_home;
	private LinearLayout _drawer_linear_credit;
	private LinearLayout _drawer_linear_rate;
	private LinearLayout _drawer_linear_other;
	private LinearLayout _drawer_linear6;
	private TextView _drawer_textview9;
	private ImageView _drawer_home_img;
	private TextView _drawer_textview1;
	private ImageView _drawer_about_img;
	private TextView _drawer_textview3;
	private ImageView _drawer_rate_img;
	private TextView _drawer_textview6;
	private ImageView _drawer_other_img;
	private TextView _drawer_textview5;
	private ImageView _drawer_imageview2;
	
	private RequestNetwork req;
	private RequestNetwork.RequestListener _req_request_listener;
	private Intent i1 = new Intent();
	private SharedPreferences theme;
	private Intent i2 = new Intent();
	private Intent tele1 = new Intent();
	private Intent sketchrate = new Intent();
	private Intent sketchother = new Intent();
	private Intent i3 = new Intent();
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.home);
		initialize(_savedInstanceState);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		_app_bar = findViewById(R.id._app_bar);
		_coordinator = findViewById(R.id._coordinator);
		_toolbar = findViewById(R.id._toolbar);
		setSupportActionBar(_toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);
		_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _v) {
				onBackPressed();
			}
		});
		_drawer = findViewById(R.id._drawer);
		ActionBarDrawerToggle _toggle = new ActionBarDrawerToggle(HomeActivity.this, _drawer, _toolbar, R.string.app_name, R.string.app_name);
		_drawer.addDrawerListener(_toggle);
		_toggle.syncState();
		
		LinearLayout _nav_view = findViewById(R.id._nav_view);
		
		collapsingtoolbar1 = findViewById(R.id.collapsingtoolbar1);
		linear1 = findViewById(R.id.linear1);
		toollayout = findViewById(R.id.toollayout);
		linear2 = findViewById(R.id.linear2);
		searchbar = findViewById(R.id.searchbar);
		drawer = findViewById(R.id.drawer);
		textview1 = findViewById(R.id.textview1);
		imageview2 = findViewById(R.id.imageview2);
		dorl = findViewById(R.id.dorl);
		srch_l = findViewById(R.id.srch_l);
		srch_r = findViewById(R.id.srch_r);
		textview2 = findViewById(R.id.textview2);
		ic_search_view = findViewById(R.id.ic_search_view);
		buffer_lin = findViewById(R.id.buffer_lin);
		recyclerview1 = findViewById(R.id.recyclerview1);
		progressbar1 = findViewById(R.id.progressbar1);
		error_lin = findViewById(R.id.error_lin);
		imageview1 = findViewById(R.id.imageview1);
		textview3 = findViewById(R.id.textview3);
		_drawer_linear1 = _nav_view.findViewById(R.id.linear1);
		_drawer_linear4 = _nav_view.findViewById(R.id.linear4);
		_drawer_linear3 = _nav_view.findViewById(R.id.linear3);
		_drawer_linear2 = _nav_view.findViewById(R.id.linear2);
		_drawer_imageview1 = _nav_view.findViewById(R.id.imageview1);
		_drawer_linear5 = _nav_view.findViewById(R.id.linear5);
		_drawer_close = _nav_view.findViewById(R.id.close);
		_drawer_textview8 = _nav_view.findViewById(R.id.textview8);
		_drawer_linear_home = _nav_view.findViewById(R.id.linear_home);
		_drawer_linear_credit = _nav_view.findViewById(R.id.linear_credit);
		_drawer_linear_rate = _nav_view.findViewById(R.id.linear_rate);
		_drawer_linear_other = _nav_view.findViewById(R.id.linear_other);
		_drawer_linear6 = _nav_view.findViewById(R.id.linear6);
		_drawer_textview9 = _nav_view.findViewById(R.id.textview9);
		_drawer_home_img = _nav_view.findViewById(R.id.home_img);
		_drawer_textview1 = _nav_view.findViewById(R.id.textview1);
		_drawer_about_img = _nav_view.findViewById(R.id.about_img);
		_drawer_textview3 = _nav_view.findViewById(R.id.textview3);
		_drawer_rate_img = _nav_view.findViewById(R.id.rate_img);
		_drawer_textview6 = _nav_view.findViewById(R.id.textview6);
		_drawer_other_img = _nav_view.findViewById(R.id.other_img);
		_drawer_textview5 = _nav_view.findViewById(R.id.textview5);
		_drawer_imageview2 = _nav_view.findViewById(R.id.imageview2);
		req = new RequestNetwork(this);
		theme = getSharedPreferences("theme", Activity.MODE_PRIVATE);
		
		searchbar.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				i2.setClass(getApplicationContext(), SearchActivity.class);
				i2.putExtra("data", new Gson().toJson(lmap));
				startActivity(i2);
			}
		});
		
		drawer.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				_drawer.openDrawer(GravityCompat.START);
			}
		});
		
		imageview2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				i3.setClass(getApplicationContext(), DownloadsActivity.class);
				startActivity(i3);
			}
		});
		
		dorl.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (isThemeLight) {
					RippleAnimation.create(_view).setDuration(500).start();
					_dark_mode();
					theme.edit().putString("theme", "false").commit();
				}
				else {
					RippleAnimation.create(_view).setDuration(500).start();
					_light_mode();
					theme.edit().putString("theme", "true").commit();
				}
			}
		});
		
		_req_request_listener = new RequestNetwork.RequestListener() {
			@Override
			public void onResponse(String _param1, String _param2, HashMap<String, Object> _param3) {
				final String _tag = _param1;
				final String _response = _param2;
				final HashMap<String, Object> _responseHeaders = _param3;
				try {
					org.json.JSONObject obg = new org.json.JSONObject(_response); org.json.JSONArray array = obg.getJSONArray("items");
					for(int i=0;i<array.length();i++){
						org.json.JSONObject object = array.getJSONObject(i);
						
						n = 0;
						map = new HashMap<>();
						map.put("family", object.getString("family"));
						map.put("version", object.getString("version"));
						map.put("modified", object.getString("lastModified"));
						org.json.JSONObject files = object.getJSONObject("files");
						map.put("url", files.getString("regular"));
						map.put("category", object.getString("category"));
						map.put("json", object.getString("files"));
						lmap.add(map);
						buffer_lin.setVisibility(View.GONE);
						recyclerview1.setAdapter(new Recyclerview1Adapter(lmap));
					}
					
				} catch (org.json.JSONException e)
				{
					errorMessage = e.getMessage();
					}
			}
			
			@Override
			public void onErrorResponse(String _param1, String _param2) {
				final String _tag = _param1;
				final String _message = _param2;
				progressbar1.setVisibility(View.GONE);
				error_lin.setVisibility(View.VISIBLE);
			}
		};
		
		_drawer_linear1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				
			}
		});
		
		_drawer_close.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				_drawer.closeDrawer(GravityCompat.START);
			}
		});
		
		_drawer_linear_home.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				
			}
		});
		
		_drawer_linear_credit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				SketchwareUtil.showMessage(getApplicationContext(), "From Barcode Softwares");
				tele1.setAction(Intent.ACTION_VIEW);
				tele1.setData(Uri.parse("https://telegram.me/ansil_shan"));
				startActivity(tele1);
			}
		});
		
		_drawer_linear_rate.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				sketchrate.setAction(Intent.ACTION_VIEW);
				sketchrate.setData(Uri.parse("https://web.sketchub.in/p/2799"));
				startActivity(sketchrate);
			}
		});
		
		_drawer_linear_other.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				sketchother.setAction(Intent.ACTION_VIEW);
				sketchother.setData(Uri.parse("https://web.sketchub.in/u/BARCODE_Developers"));
				startActivity(sketchother);
			}
		});
	}
	
	private void initializeLogic() {
		_drawerWithoutActionBar(false);
		int nightModeFlags = getResources().getConfiguration().uiMode & android.content.res.Configuration.UI_MODE_NIGHT_MASK;
		if (nightModeFlags == android.content.res.Configuration.UI_MODE_NIGHT_YES) {
			
			_dark_mode();
		} else {
			_light_mode();
		};
		LinearLayout _nav_view = (LinearLayout) findViewById(R.id._nav_view);  androidx.drawerlayout.widget.DrawerLayout .LayoutParams params = (androidx.drawerlayout.widget.DrawerLayout .LayoutParams)_nav_view.getLayoutParams();  params.width = (int)getDip((int)300);  params.height = androidx.drawerlayout.widget.DrawerLayout .LayoutParams.MATCH_PARENT;  _nav_view.setLayoutParams(params);
		 _nav_view.setBackgroundDrawable(new android.graphics.drawable.ColorDrawable(Color.TRANSPARENT));
		if (theme.getString("theme", "").equals("true")) {
			_light_mode();
		}
		else {
			if (theme.getString("theme", "").equals("false")) {
				_dark_mode();
			}
			else {
				theme.edit().putString("theme", "true").commit();
				_light_mode();
			}
		}
		error_lin.setVisibility(View.GONE);
		// Secondary URL : https://firebasestorage.googleapis.com/v0/b/example-8defa.appspot.com/o/webfonts.json?alt=media&token=3432d5b4-5121-4e8a-94ae-94c8b82051c7
		
		//Use it in case of limit reached in google apis
		req.startRequestNetwork(RequestNetworkController.GET, "https://www.googleapis.com/webfonts/v1/webfonts?key=AIzaSyBVhm32QSWpuxmGuurKi0ECgNfw9sw0t9A", "A", _req_request_listener);
		recyclerview1.setAdapter(new Recyclerview1Adapter(lmap));
		recyclerview1.setLayoutManager(new LinearLayoutManager(this));
	}
	
	
	@Override
	public void onResume() {
		super.onResume();
		if (theme.getString("theme", "").equals("true")) {
			_light_mode();
		}
		else {
			_dark_mode();
		}
	}
	
	@Override
	public void onBackPressed() {
		if (_drawer.isDrawerOpen(GravityCompat.START)) {
			_drawer.closeDrawer(GravityCompat.START);
		} else {
			super.onBackPressed();
		}
	}
	public void _dark_mode() {
		View view = getWindow().getDecorView();
		    view.setSystemUiVisibility(view.getSystemUiVisibility() & ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
		if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
			Window w =HomeActivity.this.getWindow();
			w.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			w.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS); w.setStatusBarColor(0xFF000000);
		}
		linear1.setBackgroundColor(0xFF000000);
		toollayout.setBackgroundColor(0xFF000000);
		searchbar.setBackgroundColor(0xFF000000);
		buffer_lin.setBackgroundColor(0xFF000000);
		textview1.setTextColor(0xFFFFFFFF);
		textview2.setTextColor(0xFFFFFFFF);
		_cornerRadius(srch_l, "#000000", 0, 45, 0, 45);
		_cornerRadius(srch_r, "#000000", 45, 0, 45, 0);
		isThemeLight = false;
		dorl.setImageResource(R.drawable.ic_brightness_5_grey);
		recyclerview1.setAdapter(new Recyclerview1Adapter(lmap));
		_drawer_textview8.setTextColor(0xFFFFFFFF);
		_drawer_textview9.setTextColor(0xFFFFFFFF);
		_drawer_linear1.setBackgroundColor(0xFF111A21);
		_Card_View(_drawer_linear_credit, 15, "#FFFFFF", 8, 0, "");
		_Card_View(_drawer_linear_rate, 15, "#FFFFFF", 8, 0, "");
		_Card_View(_drawer_linear_other, 15, "#FFFFFF", 8, 0, "");
	}
	
	
	public void _light_mode() {
		getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
		getWindow().setStatusBarColor(0xFFFFFFFF);
		linear1.setBackgroundColor(0xFFFFFFFF);
		toollayout.setBackgroundColor(0xFFFFFFFF);
		searchbar.setBackgroundColor(0xFFFFFFFF);
		buffer_lin.setBackgroundColor(0xFFFFFFFF);
		textview1.setTextColor(0xFF000000);
		textview2.setTextColor(0xFF000000);
		_cornerRadius(srch_l, "#FFFFFF", 0, 45, 0, 45);
		_cornerRadius(srch_r, "#FFFFFF", 45, 0, 45, 0);
		isThemeLight = true;
		dorl.setImageResource(R.drawable.ic_brightness_4_grey);
		recyclerview1.setAdapter(new Recyclerview1Adapter(lmap));
		_drawer_textview9.setTextColor(0xFF000000);
		_drawer_textview8.setTextColor(0xFF000000);
		_drawer_linear1.setBackgroundColor(0xFFFFFFFF);
		_Card_View(_drawer_linear_credit, 15, "#FFFFFF", 8, 0, "");
		_Card_View(_drawer_linear_rate, 15, "#FFFFFF", 8, 0, "");
		_Card_View(_drawer_linear_other, 15, "#FFFFFF", 8, 0, "");
	}
	
	
	public void _drawerWithoutActionBar(final boolean _abshown) {
		if(_abshown==true){
			getSupportActionBar().show();
		}else{
			if(_abshown==false){
				getSupportActionBar().hide();
			}else{
			}
		}
	}
	
	
	public void _cornerRadius(final View _view, final String _color, final double _RT, final double _LT, final double _RB, final double _LB) {
		android.graphics.drawable.GradientDrawable gd = new android.graphics.drawable.GradientDrawable();
		gd.setColor(Color.parseColor(_color));
		gd.setCornerRadii(new float[] { (float)_LT, (float)_LT, (float)_RT, (float)_RT, (float)_RB, (float)_RB, (float)_LB, (float)_LB });
		gd.setStroke((int)2, Color.parseColor("#9E9E9E"));
		_view.setBackground(gd);
	}
	
	
	public void _clickAnimation(final View _view) {
		ScaleAnimation fade_in = new ScaleAnimation(0.9f, 1f, 0.9f, 1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.7f);
		fade_in.setDuration(300);
		fade_in.setFillAfter(true);
		_view.startAnimation(fade_in);
	}
	
	
	public void _Card_View(final View _view, final double _cornerradius, final String _bgcolor, final double _elevation, final double _stroke, final String _strokecolor) {
		if (_stroke == 0) {
			//𝐁𝐥𝐨𝐜𝐤 𝐜𝐫𝐞𝐚𝐭𝐞𝐝 𝐛𝐲 𝐇-6𝐢𝐱
			android.graphics.drawable.GradientDrawable cv = new android.graphics.drawable.GradientDrawable(); 
			float cornerradius = (float) _cornerradius;
			cv.setCornerRadius(cornerradius);
			cv.setColor(Color.parseColor("#" + _bgcolor.replace("#", "")));
			_view.setBackground(cv);
			float elevation = (float) _elevation;
			_view.setElevation(elevation);
		}
		else {
			android.graphics.drawable.GradientDrawable cv = new android.graphics.drawable.GradientDrawable(); 
			float cornerradius = (float) _cornerradius;
			cv.setStroke((int)_stroke, Color.parseColor("#" + _strokecolor.replace("#", "")));
			cv.setCornerRadius(cornerradius);
			cv.setColor(Color.parseColor("#" + _bgcolor.replace("#", "")));
			_view.setBackground(cv);
			float elevation = (float) _elevation;
			_view.setElevation(elevation);
		}
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
			if (isThemeLight) {
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
					temp_map = lmap.get((int)_position);
					i1.setClass(getApplicationContext(), ViewActivity.class);
					i1.putExtra("json", _data.get((int)_position).get("json").toString());
					i1.putExtra("data", new Gson().toJson(temp_map));
					startActivity(i1);
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