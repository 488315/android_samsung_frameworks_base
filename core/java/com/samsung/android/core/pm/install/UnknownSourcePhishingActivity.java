package com.samsung.android.core.pm.install;

import android.app.Activity;
import android.content.pm.ASKSManager;
import android.content.pm.PackageInstaller;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.android.internal.C4337R;
import java.util.HashMap;
import java.util.Locale;

/* loaded from: classes5.dex */
public class UnknownSourcePhishingActivity extends Activity {
  private static final String AASA_ENG_BLOCK_FILE = "ASKS_UNKNOWN_BLOCK_DETAIL_ENG.html";
  private static final String AASA_ENG_WARN_FILE = "ASKS_UNKNOWN_WARNING_DETAIL_ENG.html";
  private static final String AASA_KOR_BLOCK_FILE_0 = "ASKS_UNKNOWN_BLOCK_DETAIL_0.html";
  private static final String AASA_KOR_BLOCK_FILE_1 = "ASKS_UNKNOWN_BLOCK_DETAIL_1.html";
  private static final String AASA_KOR_BLOCK_FILE_2 = "ASKS_UNKNOWN_BLOCK_DETAIL_2.html";
  private static final String AASA_KOR_BLOCK_FILE_3 = "ASKS_UNKNOWN_BLOCK_DETAIL_3.html";
  private static final String AASA_KOR_BLOCK_FILE_4 = "ASKS_UNKNOWN_BLOCK_DETAIL_4.html";
  private static final String AASA_KOR_WARN_FILE_0 = "ASKS_UNKNOWN_WARNING_DETAIL_0.html";
  private static final String AASA_KOR_WARN_FILE_1 = "ASKS_UNKNOWN_WARNING_DETAIL_1.html";
  private static final String AASA_KOR_WARN_FILE_2 = "ASKS_UNKNOWN_WARNING_DETAIL_2.html";
  private static final String AASA_KOR_WARN_FILE_3 = "ASKS_UNKNOWN_WARNING_DETAIL_3.html";
  private static final String AASA_KOR_WARN_FILE_4 = "ASKS_UNKNOWN_WARNING_DETAIL_4.html";
  private static final String LANGUAGE_KOR = "ko";
  private static final String TAG = "UnknownSourceAppManager";
  private HashMap<Integer, String> htmlMap =
      new HashMap<
          Integer,
          String>() { // from class:
                      // com.samsung.android.core.pm.install.UnknownSourcePhishingActivity.1
        {
          put(110, UnknownSourcePhishingActivity.AASA_KOR_WARN_FILE_0);
          put(111, UnknownSourcePhishingActivity.AASA_KOR_WARN_FILE_1);
          put(112, UnknownSourcePhishingActivity.AASA_KOR_WARN_FILE_2);
          put(113, UnknownSourcePhishingActivity.AASA_KOR_WARN_FILE_3);
          put(114, UnknownSourcePhishingActivity.AASA_KOR_WARN_FILE_4);
          put(120, UnknownSourcePhishingActivity.AASA_KOR_BLOCK_FILE_0);
          put(121, UnknownSourcePhishingActivity.AASA_KOR_BLOCK_FILE_1);
          put(122, UnknownSourcePhishingActivity.AASA_KOR_BLOCK_FILE_2);
          put(123, UnknownSourcePhishingActivity.AASA_KOR_BLOCK_FILE_3);
          put(124, UnknownSourcePhishingActivity.AASA_KOR_BLOCK_FILE_4);
        }
      };
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
    String file;
    setContentView(C4337R.layout.activity_unknownsource_warn_phishing);
    int i = this.mInstallType;
    if (i >= 120 && i <= 124) {
      Button installAnywayBtn = (Button) findViewById(C4337R.id.install_anyway_button);
      Button installDenyBtn = (Button) findViewById(C4337R.id.install_deny_button);
      installAnywayBtn.setVisibility(8);
      installDenyBtn.setText(getString(17039370));
    }
    if (Locale.getDefault().getLanguage().equals(LANGUAGE_KOR)) {
      file = this.htmlMap.get(Integer.valueOf(this.mInstallType));
    } else {
      int i2 = this.mInstallType;
      if (i2 >= 120 && i2 <= 124) {
        file = AASA_ENG_BLOCK_FILE;
      } else {
        file = AASA_ENG_WARN_FILE;
      }
    }
    TextView view = (TextView) findViewById(C4337R.id.unknownsource_warning_text);
    view.setText(Html.fromHtml(getHtmlText(file)));
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

  private String getHtmlText(String file) {
    try {
      return ASKSManager.getASKSManager().readASKSFiles("/data/system/.aasa/AASApolicy/", file);
    } catch (Exception e) {
      e.printStackTrace();
      return "";
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
