package com.nadeem.pawan.makeyourtrip;



import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;
	private String[] navigation_list;
	final Context context=this;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		navigation_list = getResources().getStringArray(R.array.navigation_list);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);
		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
		mDrawerList.setAdapter(new ArrayAdapter<String>(this,R.layout.drawer_list_item, navigation_list));
		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
		mDrawerToggle = new ActionBarDrawerToggle(this,mDrawerLayout,R.drawable.ic_drawer,R.string.drawer_open,R.string.drawer_close){
			public void onDrawerClosed(View view) {
				getActionBar().setTitle("Home");
				invalidateOptionsMenu(); 
			}
			public void onDrawerOpened(View drawerView) {
				getActionBar().setTitle("Home");
				invalidateOptionsMenu();
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);
		if (savedInstanceState == null) {
			selectItem(0);
		}
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		switch(item.getItemId()) {
		case R.id.action_websearch:
			Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
			intent.putExtra(SearchManager.QUERY, getActionBar().getTitle());
			if (intent.resolveActivity(getPackageManager()) != null) {
				startActivity(intent);
			} else {
				Toast.makeText(this, R.string.app_not_available, Toast.LENGTH_LONG).show();
			}
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	private class DrawerItemClickListener implements ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			Log.i("pawan",position+""+view.getId());
			selectItem(position);

		}
	}
	private void selectItem(int position) {
		Fragment fragment = new NavigationFragment();
		Bundle args = new Bundle();
		args.putInt(NavigationFragment.NAVIGATION_LIST_NUMBER, position);
		fragment.setArguments(args);
		FragmentManager fragmentManager = getFragmentManager();
		fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
		mDrawerList.setItemChecked(position, true);
		setTitle(navigation_list[position]);
		mDrawerLayout.closeDrawer(mDrawerList);
	}
	@Override
	public void setTitle(CharSequence title) {
		getActionBar().setTitle(title);
	}

	public static class NavigationFragment extends Fragment {
		public static final String NAVIGATION_LIST_NUMBER = "";
		public NavigationFragment() {
		}
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
			int i = getArguments().getInt(NAVIGATION_LIST_NUMBER);
			View rootView = inflater.inflate(R.layout.flight_status, container, false);
			if(i==1){
				rootView = inflater.inflate(R.layout.pnr_status, container, false);
			}
			getActivity().setTitle("Home");
			return rootView;
		}
	}
}