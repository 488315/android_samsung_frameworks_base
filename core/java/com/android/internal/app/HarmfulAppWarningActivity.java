package com.android.internal.app;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.p002pm.ApplicationInfo;
import android.content.p002pm.PackageManager;
import android.p009os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.android.internal.C4337R;

/* loaded from: classes4.dex */
public class HarmfulAppWarningActivity extends AlertActivity
    implements DialogInterface.OnClickListener {
  private static final String EXTRA_HARMFUL_APP_WARNING = "harmful_app_warning";
  private static final String TAG = HarmfulAppWarningActivity.class.getSimpleName();
  private String mHarmfulAppWarning;
  private String mPackageName;
  private IntentSender mTarget;

  @Override // com.android.internal.app.AlertActivity, android.app.Activity
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    getWindow().addSystemFlags(524288);
    Intent intent = getIntent();
    this.mPackageName = intent.getStringExtra("android.intent.extra.PACKAGE_NAME");
    this.mTarget =
        (IntentSender) intent.getParcelableExtra("android.intent.extra.INTENT", IntentSender.class);
    String stringExtra = intent.getStringExtra(EXTRA_HARMFUL_APP_WARNING);
    this.mHarmfulAppWarning = stringExtra;
    if (this.mPackageName == null || this.mTarget == null || stringExtra == null) {
      Log.wtf(TAG, "Invalid intent: " + intent.toString());
      finish();
    }
    try {
      ApplicationInfo applicationInfo =
          getPackageManager().getApplicationInfo(this.mPackageName, 0);
      AlertController.AlertParams p = this.mAlertParams;
      p.mTitle = getString(C4337R.string.harmful_app_warning_title);
      p.mView = createView(applicationInfo);
      p.mPositiveButtonText = getString(C4337R.string.harmful_app_warning_uninstall);
      p.mPositiveButtonListener = this;
      p.mNegativeButtonText = getString(C4337R.string.harmful_app_warning_open_anyway);
      p.mNegativeButtonListener = this;
      this.mAlert.installContent(this.mAlertParams);
    } catch (PackageManager.NameNotFoundException e) {
      Log.m97e(TAG, "Could not show warning because package does not exist ", e);
      finish();
    }
  }

  private View createView(ApplicationInfo applicationInfo) {
    View view =
        getLayoutInflater().inflate(C4337R.layout.harmful_app_warning_dialog, (ViewGroup) null);
    ((TextView) view.findViewById(C4337R.id.app_name_text))
        .setText(applicationInfo.loadSafeLabel(getPackageManager(), 1000.0f, 5));
    ((TextView) view.findViewById(16908299)).setText(this.mHarmfulAppWarning);
    return view;
  }

  @Override // android.content.DialogInterface.OnClickListener
  public void onClick(DialogInterface dialog, int which) {
    switch (which) {
      case -2:
        getPackageManager().setHarmfulAppWarning(this.mPackageName, null);
        IntentSender target =
            (IntentSender)
                getIntent().getParcelableExtra("android.intent.extra.INTENT", IntentSender.class);
        Bundle activityOptions =
            ActivityOptions.makeBasic().setPendingIntentBackgroundActivityStartMode(1).toBundle();
        try {
          startIntentSenderForResult(target, -1, (Intent) null, 0, 0, 0, activityOptions);
        } catch (IntentSender.SendIntentException e) {
          Log.m97e(TAG, "Error while starting intent sender", e);
        }
        EventLogTags.writeHarmfulAppWarningLaunchAnyway(this.mPackageName);
        finish();
        break;
      case -1:
        getPackageManager().deletePackage(this.mPackageName, null, 0);
        EventLogTags.writeHarmfulAppWarningUninstall(this.mPackageName);
        finish();
        break;
    }
  }

  public static Intent createHarmfulAppWarningIntent(
      Context context,
      String targetPackageName,
      IntentSender target,
      CharSequence harmfulAppWarning) {
    Intent intent = new Intent();
    intent.setClass(context, HarmfulAppWarningActivity.class);
    intent.putExtra("android.intent.extra.PACKAGE_NAME", targetPackageName);
    intent.putExtra("android.intent.extra.INTENT", target);
    intent.putExtra(EXTRA_HARMFUL_APP_WARNING, harmfulAppWarning);
    return intent;
  }
}
