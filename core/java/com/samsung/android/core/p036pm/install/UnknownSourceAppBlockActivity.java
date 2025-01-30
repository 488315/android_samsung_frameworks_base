package com.samsung.android.core.p036pm.install;

import android.app.Activity;
import android.content.p002pm.PackageInstaller;
import android.content.res.Configuration;
import android.p009os.Bundle;
import android.p009os.SystemProperties;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.internal.C4337R;
import com.samsung.android.wallpaperbackup.BnRConstants;

/* loaded from: classes5.dex */
public class UnknownSourceAppBlockActivity extends Activity {
  private static final String TAG = "UnknownSourceAppManager";
  private boolean mButtonClicked = false;
  private int mInstallType;
  private int mSessionId;

  @Override // android.app.Activity
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    this.mSessionId = getIntent().getIntExtra(PackageInstaller.EXTRA_SESSION_ID, 0);
    this.mInstallType = getIntent().getIntExtra(UnknownSourceConfirmActivity.EXTRA_INSTALL_TYPE, 0);
    setContentView();
  }

  private void setContentView() {
    setContentView(C4337R.layout.activity_unknownsource_appblock);
    ImageView icon = (ImageView) findViewById(C4337R.id.appblock_icon);
    TextView title = (TextView) findViewById(C4337R.id.appblock_title);
    TextView subtitle = (TextView) findViewById(C4337R.id.appblock_subtitle);
    TextView desc = (TextView) findViewById(C4337R.id.appblock_desc);
    switch (this.mInstallType) {
      case 140:
        subtitle.setVisibility(8);
        break;
      case 141:
        title.setText(C4337R.string.unknown_install_activity_warning_title);
        subtitle.setText(C4337R.string.appblock_warn_subtitle);
        desc.setText(C4337R.string.appblock_warn_text2);
        break;
      case 150:
        icon.setImageResource(C4337R.drawable.ic_unknownsource_error);
        title.setText(C4337R.string.unknown_install_activity_warning_title);
        boolean isTablet =
            SystemProperties.get("ro.build.characteristics")
                .contains(BnRConstants.DEVICETYPE_TABLET);
        if (isTablet) {
          subtitle.setText(C4337R.string.appblock_block_subtitle1_phone);
        } else {
          subtitle.setText(C4337R.string.appblock_block_subtitle1_tablet);
        }
        desc.setText(C4337R.string.appblock_block_text1);
        changeToBlockButton();
        break;
      case 151:
        icon.setImageResource(C4337R.drawable.ic_unknownsource_error);
        title.setText(C4337R.string.unknown_install_activity_warning_title);
        subtitle.setText(C4337R.string.appblock_block_subtitle2);
        desc.setText(C4337R.string.appblock_block_text2);
        changeToBlockButton();
        break;
    }
  }

  private void changeToBlockButton() {
    Button installAnywayBtn = (Button) findViewById(C4337R.id.install_anyway_button);
    Button installDenyBtn = (Button) findViewById(C4337R.id.install_deny_button);
    installAnywayBtn.setVisibility(8);
    installDenyBtn.setText(getString(17039370));
  }

  @Override // android.app.Activity, android.content.ComponentCallbacks
  public void onConfigurationChanged(Configuration newConfig) {
    super.onConfigurationChanged(newConfig);
    setContentView();
  }

  @Override // android.app.Activity
  public void onStop() {
    super.onStop();
    if (!this.mButtonClicked) {
      rejectInstall();
    }
  }

  public void onInstallButtonClick(View view) {
    this.mButtonClicked = true;
    if (view.getId() == 16909195) {
      Log.m94d(TAG, "Allow installing");
      getPackageManager()
          .getPackageInstaller()
          .setUnknownSourceConfirmResult(this.mSessionId, true);
    } else if (view.getId() == 16909196) {
      rejectInstall();
    }
    finish();
  }

  private void rejectInstall() {
    Log.m94d(TAG, "Reject installing");
    getPackageManager().getPackageInstaller().setUnknownSourceConfirmResult(this.mSessionId, false);
  }
}
