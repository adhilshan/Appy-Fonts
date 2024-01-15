package com.appy.fonts.barcode;

import android.Manifest;
import android.animation.*;
import android.app.*;
import android.app.Activity;
import android.content.*;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
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
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.*;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.annotation.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
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

public class ViewActivity extends AppCompatActivity {
	
	private Timer _timer = new Timer();
	
	private double n = 0;
	private HashMap<String, Object> map = new HashMap<>();
	private HashMap<String, Object> temp_map = new HashMap<>();
	private boolean isThemeLight = false;
	private HashMap<String, Object> map2 = new HashMap<>();
	private double itemSelected = 0;
	private String fileName = "";
	private double downloadID = 0;
	private double progress = 0;
	private double itemDownloaded = 0;
	
	private ArrayList<String> str = new ArrayList<>();
	private ArrayList<String> keys_url = new ArrayList<>();
	private ArrayList<String> values_url = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> temp_lmap = new ArrayList<>();
	private ArrayList<Double> dwnld_items = new ArrayList<>();
	
	private LinearLayout toolbar;
	private LinearLayout linear2;
	private LinearLayout linear3;
	private ImageView back;
	private TextView textview1;
	private ImageView dorl;
	private ScrollView vscroll1;
	private LinearLayout linear4;
	private LinearLayout linear18;
	private LinearLayout linear5;
	private LinearLayout linear6;
	private LinearLayout linear7;
	private LinearLayout linear8;
	private LinearLayout linear9;
	private WebView webview1;
	private LinearLayout dwnld_lin;
	private TextView name;
	private TextView category;
	private LinearLayout linear15;
	private LinearLayout linear17;
	private ImageView imageview1;
	private LinearLayout linear22;
	private LinearLayout linear19;
	private RelativeLayout rl;
	private ProgressBar progressbar1;
	private LinearLayout linear21;
	private TextView dwnld_statement;
	private TextView textview11;
	private RecyclerView recyclerview1;
	private TextView textview8;
	private TextView textview9;
	private TextView version;
	private TextView textview10;
	private TextView date;
	private LinearLayout linear14;
	private LinearLayout lin_download_theme;
	private LinearLayout lin_download;
	private TextView textview12;
	private TextView textview2;
	
	private SharedPreferences theme;
	private Intent i1 = new Intent();
	private TimerTask t1;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.view);
		initialize(_savedInstanceState);
		
		if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED
		|| ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
			ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1000);
		} else {
			initializeLogic();
		}
	}
	
	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		if (requestCode == 1000) {
			initializeLogic();
		}
	}
	
	private void initialize(Bundle _savedInstanceState) {
		toolbar = findViewById(R.id.toolbar);
		linear2 = findViewById(R.id.linear2);
		linear3 = findViewById(R.id.linear3);
		back = findViewById(R.id.back);
		textview1 = findViewById(R.id.textview1);
		dorl = findViewById(R.id.dorl);
		vscroll1 = findViewById(R.id.vscroll1);
		linear4 = findViewById(R.id.linear4);
		linear18 = findViewById(R.id.linear18);
		linear5 = findViewById(R.id.linear5);
		linear6 = findViewById(R.id.linear6);
		linear7 = findViewById(R.id.linear7);
		linear8 = findViewById(R.id.linear8);
		linear9 = findViewById(R.id.linear9);
		webview1 = findViewById(R.id.webview1);
		webview1.getSettings().setJavaScriptEnabled(true);
		webview1.getSettings().setSupportZoom(true);
		dwnld_lin = findViewById(R.id.dwnld_lin);
		name = findViewById(R.id.name);
		category = findViewById(R.id.category);
		linear15 = findViewById(R.id.linear15);
		linear17 = findViewById(R.id.linear17);
		imageview1 = findViewById(R.id.imageview1);
		linear22 = findViewById(R.id.linear22);
		linear19 = findViewById(R.id.linear19);
		rl = findViewById(R.id.rl);
		progressbar1 = findViewById(R.id.progressbar1);
		linear21 = findViewById(R.id.linear21);
		dwnld_statement = findViewById(R.id.dwnld_statement);
		textview11 = findViewById(R.id.textview11);
		recyclerview1 = findViewById(R.id.recyclerview1);
		textview8 = findViewById(R.id.textview8);
		textview9 = findViewById(R.id.textview9);
		version = findViewById(R.id.version);
		textview10 = findViewById(R.id.textview10);
		date = findViewById(R.id.date);
		linear14 = findViewById(R.id.linear14);
		lin_download_theme = findViewById(R.id.lin_download_theme);
		lin_download = findViewById(R.id.lin_download);
		textview12 = findViewById(R.id.textview12);
		textview2 = findViewById(R.id.textview2);
		theme = getSharedPreferences("theme", Activity.MODE_PRIVATE);
		
		back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				finish();
			}
		});
		
		dorl.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (isThemeLight) {
					RippleAnimation.create(_view).setDuration(500).start();
					theme.edit().putString("theme", "false").commit();
					_dark();
				}
				else {
					RippleAnimation.create(_view).setDuration(500).start();
					theme.edit().putString("theme", "true").commit();
					_light();
				}
			}
		});
		
		webview1.setWebViewClient(new WebViewClient() {
			@Override
			public void onPageStarted(WebView _param1, String _param2, Bitmap _param3) {
				final String _url = _param2;
				
				super.onPageStarted(_param1, _param2, _param3);
			}
			
			@Override
			public void onPageFinished(WebView _param1, String _param2) {
				final String _url = _param2;
				
				super.onPageFinished(_param1, _param2);
			}
		});
		
		imageview1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				DownloadManager manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
				manager.remove((long)downloadID);
				dwnld_lin.setVisibility(View.GONE);
				lin_download.setAlpha((float)(1));
				lin_download.setEnabled(true);
				textview2.setText("Download");
				progressbar1.setProgress((int)0);
				dwnld_statement.setText("");
				dwnld_items.remove((int)(dwnld_items.size() - 1));
			}
		});
		
		lin_download_theme.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				i1.setClass(getApplicationContext(), ViewMoreActivity.class);
				i1.putExtra("url", map2.get("url").toString());
				startActivity(i1);
			}
		});
		
		lin_download.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View _view) {
				((ClipboardManager) getSystemService(getApplicationContext().CLIPBOARD_SERVICE)).setPrimaryClip(ClipData.newPlainText("clipboard", values_url.get((int)(itemSelected))));
				return true;
			}
		});
		
		lin_download.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				
				FileUtil.makeDir(FileUtil.getExternalStorageDir().concat("/Appy Fonts/"));
				_download(values_url.get((int)(itemSelected)), "/Appy Fonts/", map2.get("family").toString().concat("_".concat(keys_url.get((int)(itemSelected)).concat(".ttf"))));
				lin_download.setAlpha((float)(0.5d));
				dwnld_statement.setText("Please wait.....");
				lin_download.setEnabled(false);
				dwnld_lin.setVisibility(View.VISIBLE);
				dwnld_statement.setTextColor(0xFFFFFFFF);
				dwnld_items.add(Double.valueOf(itemSelected));
			}
		});
	}
	
	private void initializeLogic() {
		int nightModeFlags = getResources().getConfiguration().uiMode & android.content.res.Configuration.UI_MODE_NIGHT_MASK;
		if (nightModeFlags == android.content.res.Configuration.UI_MODE_NIGHT_YES) {
			
			_dark();
		} else {
			_light();
		}
		n = 0;
		map = new Gson().fromJson(getIntent().getStringExtra("json"), new TypeToken<HashMap<String, Object>>(){}.getType());
		map2 = new Gson().fromJson(getIntent().getStringExtra("data"), new TypeToken<HashMap<String, Object>>(){}.getType());
		SketchwareUtil.getAllKeysFromMap(map, str);
		for(int _repeat14 = 0; _repeat14 < (int)(str.size()); _repeat14++) {
			keys_url.add(str.get((int)(n)));
			values_url.add(map.get(str.get((int)(n))).toString());
			{
				HashMap<String, Object> _item = new HashMap<>();
				_item.put("x", "x");
				temp_lmap.add(_item);
			}
			
			n++;
		}
		recyclerview1.setAdapter(new Recyclerview1Adapter(temp_lmap));
		recyclerview1.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false));
		webview1.loadUrl("data:text/html,".concat("<style type=\"text/css\">\n@font-face {\n    font-family: \"My Custom Font\";\n    src: url(".concat(map2.get("url").toString().concat(") format(\"truetype\");\n}\np.customfont { \n    font-family: \"My Custom Font\", Verdana, Tahoma;\n  font-size: 30px;\n}\n</style>\n<center>\n<p class=\"customfont\">".concat(map2.get("family").toString().concat("</p>\n</center>"))))));
		name.setText(map2.get("family").toString());
		category.setText(map2.get("category").toString());
		version.setText(map2.get("version").toString());
		date.setText(map2.get("modified").toString());
		dwnld_lin.setVisibility(View.GONE);
		_removeScollBar(vscroll1);
		_click_effect(lin_download, "#FFFFFF");
		_clickAnimation(lin_download);
		_RadiusGradient4(lin_download, "#6DDA6D", "#01EC8F", 60, 0, 0, 60, 0, "#FFFFFF");
		if (theme.getString("theme", "").equals("true")) {
			_light();
		}
		else {
			_dark();
		}
	}
	
	@Override
	public void onResume() {
		super.onResume();
		if (theme.getString("theme", "").equals("true")) {
			_light();
		}
		else {
			_dark();
		}
	}
	public void _rounded_btn_outline(final View _view, final double _rd, final double _sw, final String _sc, final String _bc) {
		android.graphics.drawable.GradientDrawable drawable = new android.graphics.drawable.GradientDrawable(); drawable.setShape(android.graphics.drawable.GradientDrawable.RECTANGLE);
		    drawable.setStroke((int)_sw, Color.parseColor(_sc)); drawable.setColor(Color.parseColor(_bc)); drawable.setCornerRadius((int)_rd); _view.setBackgroundDrawable(drawable);
	}
	
	
	public void _removeScollBar(final View _view) {
		_view.setVerticalScrollBarEnabled(false); _view.setHorizontalScrollBarEnabled(false);
	}
	
	
	public void _click_effect(final View _view, final String _c) {
		_view.setBackground(Drawables.getSelectableDrawableFor(Color.parseColor(_c)));
		_view.setClickable(true);
		
	}
	
	public static class Drawables {
		    public static android.graphics.drawable.Drawable getSelectableDrawableFor(int color) {
			        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
				            android.graphics.drawable.StateListDrawable stateListDrawable = new android.graphics.drawable.StateListDrawable();
				            stateListDrawable.addState(
				                new int[]{android.R.attr.state_pressed},
				                new android.graphics.drawable.ColorDrawable(Color.parseColor("#ffffff"))
				            );
				            stateListDrawable.addState(
				                new int[]{android.R.attr.state_focused},
				                new android.graphics.drawable.ColorDrawable(Color.parseColor("#00ffffff"))
				            );
				            stateListDrawable.addState(
				                new int[]{},
				                new android.graphics.drawable.ColorDrawable(Color.parseColor("#00ffffff"))
				            );
				            return stateListDrawable;
				        } else {
				            android.content.res.ColorStateList pressedColor = android.content.res.ColorStateList.valueOf(color);
				            android.graphics.drawable.ColorDrawable defaultColor = new android.graphics.drawable.ColorDrawable(Color.parseColor("#00ffffff"));
				            
				android.graphics.drawable.Drawable rippleColor = getRippleColor(color);
				            return new android.graphics.drawable.RippleDrawable(
				                pressedColor,
				                defaultColor,
				                rippleColor
				            );
				        }
			    }
		
		    private static android.graphics.drawable.Drawable getRippleColor(int color) {
			        float[] outerRadii = new float[8];
			        Arrays.fill(outerRadii, 0);
			        android.graphics.drawable.shapes.RoundRectShape r = new android.graphics.drawable.shapes.RoundRectShape(outerRadii, null, null);
			        
			android.graphics.drawable.ShapeDrawable shapeDrawable = new 
			android.graphics.drawable.ShapeDrawable(r);
			        shapeDrawable.getPaint().setColor(color);
			        return shapeDrawable;
			    }
		 
		    private static int lightenOrDarken(int color, double fraction) {
			        if (canLighten(color, fraction)) {
				            return lighten(color, fraction);
				        } else {
				            return darken(color, fraction);
				        }
			    }
		 
		    private static int lighten(int color, double fraction) {
			        int red = Color.red(color);
			        int green = Color.green(color);
			        int blue = Color.blue(color);
			        red = lightenColor(red, fraction);
			        green = lightenColor(green, fraction);
			        blue = lightenColor(blue, fraction);
			        int alpha = Color.alpha(color);
			        return Color.argb(alpha, red, green, blue);
			    }
		 
		    private static int darken(int color, double fraction) {
			        int red = Color.red(color);
			        int green = Color.green(color);
			        int blue = Color.blue(color);
			        red = darkenColor(red, fraction);
			        green = darkenColor(green, fraction);
			        blue = darkenColor(blue, fraction);
			        int alpha = Color.alpha(color);
			 
			        return Color.argb(alpha, red, green, blue);
			    }
		 
		    private static boolean canLighten(int color, double fraction) {
			        int red = Color.red(color);
			        int green = Color.green(color);
			        int blue = Color.blue(color);
			        return canLightenComponent(red, fraction)
			            && canLightenComponent(green, fraction)
			            && canLightenComponent(blue, fraction);
			    }
		 
		    private static boolean canLightenComponent(int colorComponent, double fraction) {
			        int red = Color.red(colorComponent);
			        int green = Color.green(colorComponent);
			        int blue = Color.blue(colorComponent);
			        return red + (red * fraction) < 255
			            && green + (green * fraction) < 255
			            && blue + (blue * fraction) < 255;
			    }
		 
		    private static int darkenColor(int color, double fraction) {
			        return (int) Math.max(color - (color * fraction), 0);
			    }
		 
		    private static int lightenColor(int color, double fraction) {
			        return (int) Math.min(color + (color * fraction), 255);
			    }
	}
	public static class CircleDrawables {
		    public static android.graphics.drawable.Drawable getSelectableDrawableFor(int color) {
			        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
				            android.graphics.drawable.StateListDrawable stateListDrawable = new android.graphics.drawable.StateListDrawable();
				            stateListDrawable.addState(
				                new int[]{android.R.attr.state_pressed},
				                new android.graphics.drawable.ColorDrawable(Color.parseColor("#ffffff"))
				            );
				            stateListDrawable.addState(
				                new int[]{android.R.attr.state_focused},
				                new android.graphics.drawable.ColorDrawable(Color.parseColor("#00ffffff"))
				            );
				            stateListDrawable.addState(
				                new int[]{},
				                new android.graphics.drawable.ColorDrawable(Color.parseColor("#00ffffff"))
				            );
				            return stateListDrawable;
				        } else {
				            android.content.res.ColorStateList pressedColor = android.content.res.ColorStateList.valueOf(color);
				            android.graphics.drawable.ColorDrawable defaultColor = new android.graphics.drawable.ColorDrawable(Color.parseColor("#00ffffff"));
				            
				android.graphics.drawable.Drawable rippleColor = getRippleColor(color);
				            return new android.graphics.drawable.RippleDrawable(
				                pressedColor,
				                defaultColor,
				                rippleColor
				            );
				        }
			    }
		
		    private static android.graphics.drawable.Drawable getRippleColor(int color) {
			        float[] outerRadii = new float[180];
			        Arrays.fill(outerRadii, 80);
			        android.graphics.drawable.shapes.RoundRectShape r = new android.graphics.drawable.shapes.RoundRectShape(outerRadii, null, null);
			        
			android.graphics.drawable.ShapeDrawable shapeDrawable = new 
			android.graphics.drawable.ShapeDrawable(r);
			        shapeDrawable.getPaint().setColor(color);
			        return shapeDrawable;
			    }
		 
		    private static int lightenOrDarken(int color, double fraction) {
			        if (canLighten(color, fraction)) {
				            return lighten(color, fraction);
				        } else {
				            return darken(color, fraction);
				        }
			    }
		 
		    private static int lighten(int color, double fraction) {
			        int red = Color.red(color);
			        int green = Color.green(color);
			        int blue = Color.blue(color);
			        red = lightenColor(red, fraction);
			        green = lightenColor(green, fraction);
			        blue = lightenColor(blue, fraction);
			        int alpha = Color.alpha(color);
			        return Color.argb(alpha, red, green, blue);
			    }
		 
		    private static int darken(int color, double fraction) {
			        int red = Color.red(color);
			        int green = Color.green(color);
			        int blue = Color.blue(color);
			        red = darkenColor(red, fraction);
			        green = darkenColor(green, fraction);
			        blue = darkenColor(blue, fraction);
			        int alpha = Color.alpha(color);
			 
			        return Color.argb(alpha, red, green, blue);
			    }
		 
		    private static boolean canLighten(int color, double fraction) {
			        int red = Color.red(color);
			        int green = Color.green(color);
			        int blue = Color.blue(color);
			        return canLightenComponent(red, fraction)
			            && canLightenComponent(green, fraction)
			            && canLightenComponent(blue, fraction);
			    }
		 
		    private static boolean canLightenComponent(int colorComponent, double fraction) {
			        int red = Color.red(colorComponent);
			        int green = Color.green(colorComponent);
			        int blue = Color.blue(colorComponent);
			        return red + (red * fraction) < 255
			            && green + (green * fraction) < 255
			            && blue + (blue * fraction) < 255;
			    }
		 
		    private static int darkenColor(int color, double fraction) {
			        return (int) Math.max(color - (color * fraction), 0);
			    }
		 
		    private static int lightenColor(int color, double fraction) {
			        return (int) Math.min(color + (color * fraction), 255);
		}
	}
	
	public void drawableclass() {
	}
	
	
	public void _clickAnimation(final View _view) {
		ScaleAnimation fade_in = new ScaleAnimation(0.9f, 1f, 0.9f, 1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.7f);
		fade_in.setDuration(300);
		fade_in.setFillAfter(true);
		_view.startAnimation(fade_in);
	}
	
	
	public void _cornerRadius(final View _view, final String _color, final double _RT, final double _LT, final double _RB, final double _LB) {
		android.graphics.drawable.GradientDrawable gd = new android.graphics.drawable.GradientDrawable();
		gd.setColor(Color.parseColor(_color));
		gd.setCornerRadii(new float[] { (float)_LT, (float)_LT, (float)_RT, (float)_RT, (float)_RB, (float)_RB, (float)_LB, (float)_LB });
		_view.setBackground(gd);
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
	
	
	public void _dark() {
		isThemeLight = false;
		View view = getWindow().getDecorView();
		    view.setSystemUiVisibility(view.getSystemUiVisibility() & ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
		if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
			Window w =ViewActivity.this.getWindow();
			w.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			w.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS); w.setStatusBarColor(0xFF121212);
		}
		if (WebViewFeature.isFeatureSupported(WebViewFeature.FORCE_DARK));
		WebSettingsCompat.setForceDark(webview1.getSettings(), WebSettingsCompat.FORCE_DARK_ON);
		_Card_View(toolbar, 0, "#121212", 15, 0, "");
		back.setColorFilter(0xFFFFFFFF, PorterDuff.Mode.MULTIPLY);
		dorl.setColorFilter(0xFFFFFFFF, PorterDuff.Mode.MULTIPLY);
		name.setTextColor(0xFFFFFFFF);
		textview1.setTextColor(0xFFFFFFFF);
		textview8.setTextColor(0xFFFFFFFF);
		textview11.setTextColor(0xFFFFFFFF);
		linear18.setBackgroundColor(0xFF121212);
		linear5.setBackgroundColor(0xFF121212);
		linear4.setBackgroundColor(0xFF121212);
		linear2.setBackgroundColor(0xFF121212);
		linear6.setBackgroundColor(0xFF999999);
	}
	
	
	public void _light() {
		isThemeLight = true;
		getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
		getWindow().setStatusBarColor(0xFFFFFFFF);
		if (WebViewFeature.isFeatureSupported(WebViewFeature.FORCE_DARK));
		WebSettingsCompat.setForceDark(webview1.getSettings(), WebSettingsCompat.FORCE_DARK_OFF);
		_Card_View(toolbar, 0, "#FFFFFF", 15, 0, "");
		back.setColorFilter(0xFF000000, PorterDuff.Mode.MULTIPLY);
		dorl.setColorFilter(0xFF000000, PorterDuff.Mode.MULTIPLY);
		name.setTextColor(0xFF000000);
		textview1.setTextColor(0xFF000000);
		textview8.setTextColor(0xFF000000);
		textview11.setTextColor(0xFF000000);
		linear18.setBackgroundColor(0xFFFFFFFF);
		linear5.setBackgroundColor(0xFFFFFFFF);
		linear4.setBackgroundColor(0xFFFFFFFF);
		linear2.setBackgroundColor(0xFFFFFFFF);
		linear6.setBackgroundColor(0xFF263238);
	}
	
	
	public void _refresh() {
		itemDownloaded = 0;
		recyclerview1.setAdapter(new Recyclerview1Adapter(temp_lmap));
		for(int _repeat15 = 0; _repeat15 < (int)(dwnld_items.size()); _repeat15++) {
			if (itemSelected == dwnld_items.get((int)(itemDownloaded)).doubleValue()) {
				lin_download.setEnabled(false);
				textview2.setText("Downloaded");
			}
			else {
				lin_download.setEnabled(true);
				textview2.setText("Download");
			}
			itemDownloaded++;
		}
		if (dwnld_items.size() == 0) {
			lin_download.setEnabled(true);
			textview2.setText("Download");
		}
	}
	
	
	public void _RadiusGradient4(final View _view, final String _color1, final String _color2, final double _lt, final double _rt, final double _rb, final double _lb, final double _border, final String _color3) {
		int[] colors = { Color.parseColor(_color1), Color.parseColor(_color2) }; android.graphics.drawable.GradientDrawable gd = new android.graphics.drawable.GradientDrawable(android.graphics.drawable.GradientDrawable.Orientation.TOP_BOTTOM, colors);
		gd.setCornerRadii(new float[]{(int)_lt,(int)_lt,(int)_rt,(int)_rt,(int)_rb,(int)_rb,(int)_lb,(int)_lb});
		gd.setStroke((int) _border, Color.parseColor(_color3));
		_view.setBackground(gd);
	}
	
	
	public void _download(final String _url, final String _path, final String _name) {
		fileName = _name;
		
		android.net.ConnectivityManager connMgr = (android.net.ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
		android.net.NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		if (networkInfo != null && networkInfo.isConnected()) {
			
			
			final String urlDownload = _url;
			
			DownloadManager.Request request = new DownloadManager.Request(Uri.parse(urlDownload));
			
			request.setDescription("Downloading");
			
			request.setTitle(fileName);
			
			request.allowScanningByMediaScanner();
			
			request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_HIDDEN);
			
			request.setDestinationInExternalPublicDir(_path, fileName);
			
			final DownloadManager manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
			
			
			
			downloadID = manager.enqueue(request);
			t1 = new TimerTask() {
				@Override
				public void run() {
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							DownloadManager.Query q = new DownloadManager.Query();
							
							q.setFilterById((long)downloadID);
							
							android.database.Cursor c = manager.query(q);
							
							if (c.moveToFirst())
							{
								int sizeIndex = c.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES);
								int downloadedIndex = c.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR);
								long size = c.getInt(sizeIndex);
								long downloaded = c.getInt(downloadedIndex);
								if (size != -1)
								{
									// At this point you have the progress as a percentage
									progress = downloaded*100.0/size;
									textview2.setText("Downloading  (".concat(String.valueOf((long)(progress)).concat("%)")));
									progressbar1.setProgress((int)progress);
									if (c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS)) == 2) {
										dwnld_statement.setText("Downloading....");
									}
									else {
										if (c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS)) == 4) {
											dwnld_statement.setText("Waiting for network....");
										}
										else {
											if (c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS)) == 8) {
												dwnld_statement.setText("Downloaded");
												textview2.setText("Downloaded");
												lin_download.setAlpha((float)(1));
												SketchwareUtil.showMessage(getApplicationContext(), "Font Is Saved To The Path ".concat(_path.concat(_name)));
												dwnld_statement.setText("");
												lin_download.setEnabled(false);
												lin_download.setAlpha((float)(1));
												progressbar1.setProgress((int)0);
												dwnld_lin.setVisibility(View.GONE);
												t1.cancel();
											}
											else {
												dwnld_statement.setText("");
												textview2.setText("Retry");
												lin_download.setEnabled(true);
												lin_download.setAlpha((float)(1));
												progressbar1.setProgress((int)0);
												dwnld_lin.setVisibility(View.GONE);
												t1.cancel();
											}
										}
									}
								}}
						}
					});
				}
			};
			_timer.scheduleAtFixedRate(t1, (int)(0), (int)(500));
		} else {
			showMessage("Connection Error");
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
			View _v = _inflater.inflate(R.layout.horizontal_items_01, null);
			RecyclerView.LayoutParams _lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			_v.setLayoutParams(_lp);
			return new ViewHolder(_v);
		}
		
		@Override
		public void onBindViewHolder(ViewHolder _holder, final int _position) {
			View _view = _holder.itemView;
			
			final TextView type1 = _view.findViewById(R.id.type1);
			
			RecyclerView.LayoutParams _lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			_view.setLayoutParams(_lp);
			_Card_View(type1, 35, "#00f0f8ff", 0, 4, "#ff999999");
			type1.setTextColor(0xFF999999);
			type1.setText(keys_url.get((int)(_position)));
			type1.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View _view) {
					itemSelected = _position;
					_refresh();
				}
			});
			if (itemSelected == _position) {
				_Card_View(type1, 35, "#01EC8F", 0, 4, "#60A85C");
				type1.setTextColor(0xFFFFFFFF);
			}
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