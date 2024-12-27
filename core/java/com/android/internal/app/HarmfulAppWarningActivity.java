package com.android.internal.app;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.android.internal.R;
import com.android.internal.app.AlertController;

/* loaded from: classes5.dex */
public class HarmfulAppWarningActivity extends AlertActivity implements DialogInterface.OnClickListener {
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
        this.mTarget = (IntentSender) intent.getParcelableExtra("android.intent.extra.INTENT", IntentSender.class);
        this.mHarmfulAppWarning = intent.getStringExtra(EXTRA_HARMFUL_APP_WARNING);
        if (this.mPackageName == null || this.mTarget == null || this.mHarmfulAppWarning == null) {
            Log.wtf(TAG, "Invalid intent: " + intent.toString());
            finish();
        }
        try {
            ApplicationInfo applicationInfo = getPackageManager().getApplicationInfo(this.mPackageName, 0);
            AlertController.AlertParams p = this.mAlertParams;
            p.mTitle = getString(R.string.harmful_app_warning_title);
            p.mView = createView(applicationInfo);
            p.mPositiveButtonText = getString(R.string.harmful_app_warning_uninstall);
            p.mPositiveButtonListener = this;
            p.mNegativeButtonText = getString(R.string.harmful_app_warning_open_anyway);
            p.mNegativeButtonListener = this;
            this.mAlert.installContent(this.mAlertParams);
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "Could not show warning because package does not exist ", e);
            finish();
        }
    }

    private View createView(ApplicationInfo applicationInfo) {
        View view = getLayoutInflater().inflate(R.layout.harmful_app_warning_dialog, (ViewGroup) null);
        ((TextView) view.findViewById(R.id.app_name_text)).lambda$setTextAsync$0(applicationInfo.loadSafeLabel(getPackageManager(), 1000.0f, 5));
        ((TextView) view.findViewById(16908299)).lambda$setTextAsync$0(this.mHarmfulAppWarning);
        return view;
    }

    @Override // android.content.DialogInterface.OnClickListener
    public void onClick(DialogInterface dialog, int which) {
        switch (which) {
            case -2:
                getPackageManager().setHarmfulAppWarning(this.mPackageName, null);
                IntentSender target = (IntentSender) getIntent().getParcelableExtra("android.intent.extra.INTENT", IntentSender.class);
                Bundle activityOptions = ActivityOptions.makeBasic().setPendingIntentBackgroundActivityStartMode(1).toBundle();
                try {
                    startIntentSenderForResult(target, -1, (Intent) null, 0, 0, 0, activityOptions);
                } catch (IntentSender.SendIntentException e) {
                    Log.e(TAG, "Error while starting intent sender", e);
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

    public static Intent createHarmfulAppWarningIntent(Context context, String targetPackageName, IntentSender target, CharSequence harmfulAppWarning) {
        Intent intent = new Intent();
        intent.setClass(context, HarmfulAppWarningActivity.class);
        intent.putExtra("android.intent.extra.PACKAGE_NAME", targetPackageName);
        intent.putExtra("android.intent.extra.INTENT", target);
        intent.putExtra(EXTRA_HARMFUL_APP_WARNING, harmfulAppWarning);
        return intent;
    }
}
