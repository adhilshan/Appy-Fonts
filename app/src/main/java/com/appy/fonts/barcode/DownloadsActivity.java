package com.appy.fonts.barcode;

import android.Manifest;
import android.animation.*;
import android.app.*;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.*;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.*;
import android.graphics.*;
import android.graphics.drawable.*;
import android.media.*;
import android.net.*;
import android.net.Uri;
import android.os.*;
import android.text.*;
import android.text.style.*;
import android.util.*;
import android.view.*;
import android.view.View;
import android.view.View.*;
import android.view.animation.*;
import android.webkit.*;
import android.widget.*;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import androidx.annotation.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.webkit.*;
import com.google.gson.Gson;
import com.wuyr.rippleanimation.*;
import java.io.*;
import java.text.*;
import java.util.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.*;
import org.json.*;

public class DownloadsActivity extends AppCompatActivity {
	
	private double i = 0;
	private boolean setDialogTheme = false;
	private String file1 = "";
	private String file2 = "";
	private String fileLastName = "";
	private HashMap<String, Object> addData = new HashMap<>();
	
	private ArrayList<String> files = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> file_lists = new ArrayList<>();
	
	private LinearLayout linear1;
	private LinearLayout linear2;
	private ListView listview1;
	private ImageView imageview1;
	private TextView textview1;
	
	private SharedPreferences theme;
	private AlertDialog.Builder d1_light;
	private AlertDialog.Builder d1_dark;
	private AlertDialog.Builder d2_light;
	private AlertDialog.Builder d2_dark;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.downloads);
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
		linear1 = findViewById(R.id.linear1);
		linear2 = findViewById(R.id.linear2);
		listview1 = findViewById(R.id.listview1);
		imageview1 = findViewById(R.id.imageview1);
		textview1 = findViewById(R.id.textview1);
		theme = getSharedPreferences("theme", Activity.MODE_PRIVATE);
		d1_light = new AlertDialog.Builder(this);
		d1_dark = new AlertDialog.Builder(this);
		d2_light = new AlertDialog.Builder(this);
		d2_dark = new AlertDialog.Builder(this);
		
		imageview1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				finish();
			}
		});
	}
	
	private void initializeLogic() {
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
		
		FileUtil.listDir(FileUtil.getExternalStorageDir().concat("/Appy Fonts"), files);
		i = 0;
		for(int _repeat15 = 0; _repeat15 < (int)(files.size()); _repeat15++) {
			if (files.get((int)(i)).endsWith(".ttf")) {
				{
					HashMap<String, Object> _item = new HashMap<>();
					_item.put("file", files.get((int)(i)));
					file_lists.add(_item);
				}
				
			}
			i++;
		}
		_SortMap(file_lists, "file", false, true);
		listview1.setAdapter(new Listview1Adapter(file_lists));
		((BaseAdapter)listview1.getAdapter()).notifyDataSetChanged();
		
		listview1.setHorizontalScrollBarEnabled(false);
		listview1.setVerticalScrollBarEnabled(false);
		listview1.setOverScrollMode(ListView.OVER_SCROLL_NEVER);
		d1_dark = new AlertDialog.Builder(this,AlertDialog.THEME_DEVICE_DEFAULT_DARK);
		d2_dark = new AlertDialog.Builder(this,AlertDialog.THEME_DEVICE_DEFAULT_DARK);
	}
	
	public void _dark() {
		linear1.setBackgroundColor(0xFF111A21);
		View view = getWindow().getDecorView();
		    view.setSystemUiVisibility(view.getSystemUiVisibility() & ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
		if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
			Window w =DownloadsActivity.this.getWindow();
			w.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			w.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS); w.setStatusBarColor(0xFF111A21);
		}
	}
	
	
	public void _light() {
		getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
		getWindow().setStatusBarColor(0xFFFFFFFF);
		linear1.setBackgroundColor(0xFFFFFFFF);
	}
	
	
	public void _SortMap(final ArrayList<HashMap<String, Object>> _listMap, final String _key, final boolean _isNumber, final boolean _Ascending) {
		Collections.sort(_listMap, new Comparator<HashMap<String,Object>>(){
			public int compare(HashMap<String,Object> _compareMap1, HashMap<String,Object> _compareMap2){
				if (_isNumber) {
					int _count1 = Integer.valueOf(_compareMap1.get(_key).toString());
					int _count2 = Integer.valueOf(_compareMap2.get(_key).toString());
					if (_Ascending) {
						return _count1 < _count2 ? -1 : _count1 < _count2 ? 1 : 0;
					}
					else {
						return _count1 > _count2 ? -1 : _count1 > _count2 ? 1 : 0;
					}
				}
				else {
					if (_Ascending) {
						return (_compareMap1.get(_key).toString()).compareTo(_compareMap2.get(_key).toString());
					}
					else {
						return (_compareMap2.get(_key).toString()).compareTo(_compareMap1.get(_key).toString());
					}
				}
			}});
	}
	
	
	public void _delete(final double _position, final ArrayList<HashMap<String, Object>> _listmap) {
		int nightModeFlags = getResources().getConfiguration().uiMode & android.content.res.Configuration.UI_MODE_NIGHT_MASK;
		if (nightModeFlags == android.content.res.Configuration.UI_MODE_NIGHT_YES) {
			
			//dark mode
			setDialogTheme = false;
		} else {
			//light mode
			setDialogTheme = true;
		}
		if (theme.getString("theme", "").equals("true")) {
			setDialogTheme = true;
		}
		else {
			setDialogTheme = false;
		}
		if (setDialogTheme) {
			d1_light.setTitle("Delete !?");
			d1_light.setMessage("Do You Want To Delete This Font?\nThis Will Be Deleted Permanently.");
			d1_light.setPositiveButton("Ok , Continue", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface _dialog, int _which) {
					FileUtil.deleteFile(_listmap.get((int)_position).get("file").toString());
					_listmap.remove((int)(_position));
					listview1.setAdapter(new Listview1Adapter(file_lists));
					((BaseAdapter)listview1.getAdapter()).notifyDataSetChanged();
				}
			});
			d1_light.setNegativeButton("No , I Don't Want To Delete", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface _dialog, int _which) {
					
				}
			});
			d1_light.create().show();
		}
		else {
			d1_dark.setTitle("Delete !?");
			d1_dark.setMessage("Do You Want To Delete This Font?\nThis Will Be Deleted Permanently.");
			d1_dark.setPositiveButton("Ok , Continue", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface _dialog, int _which) {
					FileUtil.deleteFile(_listmap.get((int)_position).get("file").toString());
					_listmap.remove((int)(_position));
					listview1.setAdapter(new Listview1Adapter(file_lists));
					((BaseAdapter)listview1.getAdapter()).notifyDataSetChanged();
				}
			});
			d1_dark.setNegativeButton("No , I Don't Want To Delete", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface _dialog, int _which) {
					
				}
			});
			d1_dark.create().show();
		}
	}
	
	
	public void _addFont(final double _position, final ArrayList<HashMap<String, Object>> _listmap) {
		int nightModeFlags = getResources().getConfiguration().uiMode & android.content.res.Configuration.UI_MODE_NIGHT_MASK;
		if (nightModeFlags == android.content.res.Configuration.UI_MODE_NIGHT_YES) {
			
			//dark mode
			setDialogTheme = false;
		} else {
			//light mode
			setDialogTheme = true;
		}
		if (theme.getString("theme", "").equals("true")) {
			setDialogTheme = true;
		}
		else {
			setDialogTheme = false;
		}
		if (setDialogTheme) {
			d2_light.setTitle("Add To Sketchware");
			d2_light.setMessage("Do You Want To This Font In Sketchware Resources?\nThis Font Can Access Through Font Collections In Sketchware.");
			d2_light.setPositiveButton("Add Now", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface _dialog, int _which) {
					
					fileLastName = Uri.parse(_listmap.get((int)_position).get("file").toString()).getLastPathSegment().toLowerCase().replace(" ", "_").replace("-", "_");
					FileUtil.copyFile(_listmap.get((int)_position).get("file").toString(), FileUtil.getExternalStorageDir().concat("/.sketchware/collection/font/data/").concat(fileLastName));
					addData = new HashMap<>();
					addData.put("data", fileLastName.replace(".ttf", ""));
					addData.put("name", fileLastName.replace(".ttf", ""));
					FileUtil.writeFile(FileUtil.getExternalStorageDir().concat("/.sketchware/collection/font/list"), FileUtil.readFile(FileUtil.getExternalStorageDir().concat("/.sketchware/collection/font/list")).concat("\n".concat(new Gson().toJson(addData))));
					SketchwareUtil.showMessage(getApplicationContext(), "Font Is Added In Sketchware Collection");
					addData.clear();
				}
			});
			d2_light.setNegativeButton("Not Now", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface _dialog, int _which) {
					
				}
			});
			d2_light.create().show();
		}
		else {
			d2_dark.setTitle("Add To Sketchware");
			d2_dark.setMessage("Do You Want To This Font In Sketchware Resources?\nThis Font Can Access Through Font Collections In Sketchware.");
			d2_dark.setPositiveButton("Add Now", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface _dialog, int _which) {
					
					fileLastName = Uri.parse(_listmap.get((int)_position).get("file").toString()).getLastPathSegment().toLowerCase().replace(" ", "_").replace("-", "_");
					if (74 < fileLastName.length()) {
						fileLastName = fileLastName.replace(".ttf", "").substring((int)(0), (int)(70)).concat(".ttf");
					}
					FileUtil.copyFile(_listmap.get((int)_position).get("file").toString(), FileUtil.getExternalStorageDir().concat("/.sketchware/collection/font/data/").concat(fileLastName));
					addData = new HashMap<>();
					addData.put("data", fileLastName);
					addData.put("name", fileLastName.replace(".ttf", ""));
					FileUtil.writeFile(FileUtil.getExternalStorageDir().concat("/.sketchware/collection/font/list"), FileUtil.readFile(FileUtil.getExternalStorageDir().concat("/.sketchware/collection/font/list")).concat("\n".concat(new Gson().toJson(addData))));
					SketchwareUtil.showMessage(getApplicationContext(), "Font Is Added In Sketchware Collection");
					addData.clear();
				}
			});
			d2_dark.setNegativeButton("Not Now", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface _dialog, int _which) {
					
				}
			});
			d2_dark.create().show();
		}
	}
	
	
	public void _setFontFromPath(final TextView _textview, final String _path) {
		_textview.setTypeface(Typeface.createFromFile(_path));
	}
	
	public class Listview1Adapter extends BaseAdapter {
		
		ArrayList<HashMap<String, Object>> _data;
		
		public Listview1Adapter(ArrayList<HashMap<String, Object>> _arr) {
			_data = _arr;
		}
		
		@Override
		public int getCount() {
			return _data.size();
		}
		
		@Override
		public HashMap<String, Object> getItem(int _index) {
			return _data.get(_index);
		}
		
		@Override
		public long getItemId(int _index) {
			return _index;
		}
		
		@Override
		public View getView(final int _position, View _v, ViewGroup _container) {
			LayoutInflater _inflater = getLayoutInflater();
			View _view = _v;
			if (_view == null) {
				_view = _inflater.inflate(R.layout.font_list, null);
			}
			
			final LinearLayout linear2 = _view.findViewById(R.id.linear2);
			final androidx.cardview.widget.CardView cardview1 = _view.findViewById(R.id.cardview1);
			final LinearLayout linear1 = _view.findViewById(R.id.linear1);
			final ImageView imageview1 = _view.findViewById(R.id.imageview1);
			final TextView textview1 = _view.findViewById(R.id.textview1);
			final ImageView imageview2 = _view.findViewById(R.id.imageview2);
			
			int nightModeFlags = getResources().getConfiguration().uiMode & android.content.res.Configuration.UI_MODE_NIGHT_MASK;
			if (nightModeFlags == android.content.res.Configuration.UI_MODE_NIGHT_YES) {
				
				linear1.setBackgroundColor(0xFF23303F);
				textview1.setTextColor(0xFFFFFFFF);
				linear2.setBackgroundColor(0xFF111A21);
				imageview2.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View _view) {
						View popupView = getLayoutInflater().inflate(R.layout.popup, null);
						
						final PopupWindow popup = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
						TextView tx1 = popupView.findViewById(R.id.tx1);
						
						TextView tx2 = popupView.findViewById(R.id.tx2);
						
						
						LinearLayout ly = popupView.findViewById(R.id.ly);
						
						LinearLayout lin1 = popupView.findViewById(R.id.lin1);
						
						LinearLayout lin2 = popupView.findViewById(R.id.lin2);
						
						LinearLayout bar1 = popupView.findViewById(R.id.bar1);
						
						if (!FileUtil.isExistFile(FileUtil.getExternalStorageDir().concat("/.sketchware"))) {
								lin1.setVisibility(View.GONE);
								bar1.setVisibility(View.GONE);
						}
						lin1.setOnClickListener(new OnClickListener() { public void onClick(View view) {
								_addFont(_position, _data);
								popup.dismiss();
							} });
						
						lin2.setOnClickListener(new OnClickListener() { public void onClick(View view) {
								_delete(_position, _data);
								popup.dismiss();
							} });
						
						android.graphics.drawable.GradientDrawable lny = new android.graphics.drawable.GradientDrawable ();
						lny.setColor (Color.parseColor("#2E2F32"));
						
						lny.setCornerRadius (20);
						
						ly.setBackground (lny);
						
						ly.setElevation(5);
						
						tx1.setTextColor(0xFFFFFFFF);
						tx2.setTextColor(0xFFFFFFFF);
						popup.setAnimationStyle(android.R.style.Animation_Dialog);
						
						popup.showAsDropDown(imageview2, 0,0);
					}
				});
				//dark mode
			} else {
				linear1.setBackgroundColor(0xFFFFFFFF);
				textview1.setTextColor(0xFF000000);
				linear2.setBackgroundColor(0xFFFFFFFF);
				imageview2.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View _view) {
						View popupView = getLayoutInflater().inflate(R.layout.popup, null);
						
						final PopupWindow popup = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
						TextView tx1 = popupView.findViewById(R.id.tx1);
						
						TextView tx2 = popupView.findViewById(R.id.tx2);
						
						
						LinearLayout ly = popupView.findViewById(R.id.ly);
						
						LinearLayout lin1 = popupView.findViewById(R.id.lin1);
						
						LinearLayout lin2 = popupView.findViewById(R.id.lin2);
						
						LinearLayout bar1 = popupView.findViewById(R.id.bar1);
						
						if (!FileUtil.isExistFile(FileUtil.getExternalStorageDir().concat("/.sketchware"))) {
								lin1.setVisibility(View.GONE);
								bar1.setVisibility(View.GONE);
						}
						lin1.setOnClickListener(new OnClickListener() { public void onClick(View view) {
								_addFont(_position, _data);
								popup.dismiss();
							} });
						
						lin2.setOnClickListener(new OnClickListener() { public void onClick(View view) {
								_delete(_position, _data);
								popup.dismiss();
							} });
						
						android.graphics.drawable.GradientDrawable lny = new android.graphics.drawable.GradientDrawable ();
						lny.setColor (Color.parseColor("#FFFFFF"));
						
						lny.setCornerRadius (20);
						
						ly.setBackground (lny);
						
						ly.setElevation(5);
						
						tx1.setTextColor(0xFF000000);
						tx2.setTextColor(0xFF000000);
						popup.setAnimationStyle(android.R.style.Animation_Dialog);
						
						popup.showAsDropDown(imageview2, 0,0);
					}
				});
				//light mode
			}
			textview1.setText(Uri.parse(_data.get((int)_position).get("file").toString()).getLastPathSegment().replace(".ttf", ""));
			_setFontFromPath(textview1, _data.get((int)_position).get("file").toString());
			if (theme.getString("theme", "").equals("true")) {
				linear1.setBackgroundColor(0xFFFFFFFF);
				textview1.setTextColor(0xFF000000);
				linear2.setBackgroundColor(0xFFFFFFFF);
				imageview2.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View _view) {
						View popupView = getLayoutInflater().inflate(R.layout.popup, null);
						
						final PopupWindow popup = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
						TextView tx1 = popupView.findViewById(R.id.tx1);
						
						TextView tx2 = popupView.findViewById(R.id.tx2);
						
						
						LinearLayout ly = popupView.findViewById(R.id.ly);
						
						LinearLayout lin1 = popupView.findViewById(R.id.lin1);
						
						LinearLayout lin2 = popupView.findViewById(R.id.lin2);
						
						LinearLayout bar1 = popupView.findViewById(R.id.bar1);
						
						if (!FileUtil.isExistFile(FileUtil.getExternalStorageDir().concat("/.sketchware"))) {
								lin1.setVisibility(View.GONE);
								bar1.setVisibility(View.GONE);
						}
						lin1.setOnClickListener(new OnClickListener() { public void onClick(View view) {
								_addFont(_position, _data);
								popup.dismiss();
							} });
						
						lin2.setOnClickListener(new OnClickListener() { public void onClick(View view) {
								_delete(_position, _data);
								popup.dismiss();
							} });
						
						android.graphics.drawable.GradientDrawable lny = new android.graphics.drawable.GradientDrawable ();
						lny.setColor (Color.parseColor("#FFFFFF"));
						
						lny.setCornerRadius (20);
						
						ly.setBackground (lny);
						
						ly.setElevation(5);
						
						tx1.setTextColor(0xFF000000);
						tx2.setTextColor(0xFF000000);
						popup.setAnimationStyle(android.R.style.Animation_Dialog);
						
						popup.showAsDropDown(imageview2, 0,0);
					}
				});
			}
			else {
				linear1.setBackgroundColor(0xFF23303F);
				linear2.setBackgroundColor(0xFF111A21);
				textview1.setTextColor(0xFFFFFFFF);
				imageview2.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View _view) {
						View popupView = getLayoutInflater().inflate(R.layout.popup, null);
						
						final PopupWindow popup = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
						TextView tx1 = popupView.findViewById(R.id.tx1);
						
						TextView tx2 = popupView.findViewById(R.id.tx2);
						
						
						LinearLayout ly = popupView.findViewById(R.id.ly);
						
						LinearLayout lin1 = popupView.findViewById(R.id.lin1);
						
						LinearLayout lin2 = popupView.findViewById(R.id.lin2);
						
						LinearLayout bar1 = popupView.findViewById(R.id.bar1);
						
						if (!FileUtil.isExistFile(FileUtil.getExternalStorageDir().concat("/.sketchware"))) {
								lin1.setVisibility(View.GONE);
								bar1.setVisibility(View.GONE);
						}
						lin1.setOnClickListener(new OnClickListener() { public void onClick(View view) {
								_addFont(_position, _data);
								popup.dismiss();
							} });
						
						lin2.setOnClickListener(new OnClickListener() { public void onClick(View view) {
								_delete(_position, _data);
								popup.dismiss();
							} });
						
						android.graphics.drawable.GradientDrawable lny = new android.graphics.drawable.GradientDrawable ();
						lny.setColor (Color.parseColor("#2E2F32"));
						
						lny.setCornerRadius (20);
						
						ly.setBackground (lny);
						
						ly.setElevation(5);
						
						tx1.setTextColor(0xFFFFFFFF);
						tx2.setTextColor(0xFFFFFFFF);
						popup.setAnimationStyle(android.R.style.Animation_Dialog);
						
						popup.showAsDropDown(imageview2, 0,0);
					}
				});
			}
			
			return _view;
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