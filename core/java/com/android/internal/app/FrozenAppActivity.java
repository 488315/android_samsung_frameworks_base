package com.android.internal.app;

import android.content.Intent;
import android.content.p002pm.ApplicationInfo;
import android.content.p002pm.PackageManager;
import android.p009os.Bundle;
import android.text.TextUtils;
import android.util.Slog;
import android.widget.Toast;
import com.android.internal.C4337R;

/* loaded from: classes4.dex */
public class FrozenAppActivity extends AlertActivity {
  private static final String EXTRA_FROZEN_PACKAGE =
      "com.android.internal.app.extra.FROZEN_PACKAGE";
  private static final String PACKAGE_NAME = "com.android.internal.app";
  private static final String TAG = "FrozenAppActivity";

  @Override // com.android.internal.app.AlertActivity, android.app.Activity
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Intent intent = getIntent();
    int userId = intent.getIntExtra("android.intent.extra.USER_ID", -1);
    if (userId < 0) {
      Slog.wtf(TAG, "Invalid user: " + userId);
      finish();
      return;
    }
    String packageName = intent.getStringExtra(EXTRA_FROZEN_PACKAGE);
    if (TextUtils.isEmpty(packageName)) {
      Slog.wtf(TAG, "Invalid package: " + packageName);
      finish();
    } else {
      CharSequence appLabel = getAppLabel(userId, packageName);
      String toastMsg = getString(C4337R.string.app_updating_message, appLabel);
      Toast.makeText(getApplicationContext(), toastMsg, 1).show();
      finish();
    }
  }

  private CharSequence getAppLabel(int userId, String packageName) {
    PackageManager pm = getPackageManager();
    try {
      ApplicationInfo aInfo = pm.getApplicationInfoAsUser(packageName, 0, userId);
      return aInfo.loadLabel(pm);
    } catch (PackageManager.NameNotFoundException ne) {
      Slog.m116e(TAG, "Package " + packageName + " not found", ne);
      return packageName;
    }
  }

  public static Intent createIntent(int userId, String packageName) {
    return new Intent()
        .setClassName("android", FrozenAppActivity.class.getName())
        .putExtra("android.intent.extra.USER_ID", userId)
        .putExtra(EXTRA_FROZEN_PACKAGE, packageName)
        .setFlags(276824064);
  }
}
