package com.dnake.desktop;

import com.dnake.v700.sys;
import com.dnake.widget.Button2;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;

public class MainActivity extends BaseLabel {
	private static int mSdtUsed = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		Button2 b = (Button2) this.findViewById(R.id.main_apps);
		b.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent it = new Intent(MainActivity.this, AppsLabel.class);
				it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(it);
			}
		});

		b = (Button2) this.findViewById(R.id.main_face);
		b.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				PackageManager pm = getPackageManager();
				Intent it = pm.getLaunchIntentForPackage("com.dnake.panel");
				if (it != null)
					startActivity(it);
			}
		});

		Intent it = new Intent("com.dnake.broadcast");
		it.putExtra("event", "com.dnake.boot");
		sendBroadcast(it);

		it = new Intent(this, SysService.class);
		this.startService(it);
	}

	@Override
	public void onStart() {
		super.onStart();
		this.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
		bFinish = false;
		tPeriod = 1000;
	}

	@Override
	public void onStop() {
		super.onStop();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onTimer() {
		if (this.getWindow().getDecorView().getSystemUiVisibility() != View.SYSTEM_UI_FLAG_HIDE_NAVIGATION) {
			this.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
		}

		if (mSdtUsed == 0 && sys.sdt() > 0) {
			mSdtUsed = 1;
			Button2 b = (Button2) this.findViewById(R.id.main_face);
			b.setVisibility(View.VISIBLE);
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (event.getKeyCode() == KeyEvent.KEYCODE_BACK)
			return true;
		return super.onKeyDown(keyCode, event);
	}
}
