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
    private static final String TAG = "HarmfulAppWarningActivity";
    private String mHarmfulAppWarning;
    private String mPackageName;
    private IntentSender mTarget;

    @Override // com.android.internal.app.AlertActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getWindow().addSystemFlags(524288);
        Intent intent = getIntent();
        this.mPackageName = intent.getStringExtra("android.intent.extra.PACKAGE_NAME");
        this.mTarget = (IntentSender) intent.getParcelableExtra("android.intent.extra.INTENT", IntentSender.class);
        String stringExtra = intent.getStringExtra(EXTRA_HARMFUL_APP_WARNING);
        this.mHarmfulAppWarning = stringExtra;
        if (this.mPackageName == null || this.mTarget == null || stringExtra == null) {
            Log.wtf(TAG, "Invalid intent: " + intent.toString());
            finish();
        }
        try {
            ApplicationInfo applicationInfo = getPackageManager().getApplicationInfo(this.mPackageName, 0);
            AlertController.AlertParams alertParams = this.mAlertParams;
            alertParams.mTitle = getString(R.string.harmful_app_warning_title);
            alertParams.mView = createView(applicationInfo);
            alertParams.mPositiveButtonText = getString(R.string.harmful_app_warning_uninstall);
            alertParams.mPositiveButtonListener = this;
            alertParams.mNegativeButtonText = getString(R.string.harmful_app_warning_open_anyway);
            alertParams.mNegativeButtonListener = this;
            this.mAlert.installContent(this.mAlertParams);
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "Could not show warning because package does not exist ", e);
            finish();
        }
    }

    private View createView(ApplicationInfo applicationInfo) {
        View inflate = getLayoutInflater().inflate(R.layout.harmful_app_warning_dialog, (ViewGroup) null);
        ((TextView) inflate.findViewById(R.id.app_name_text)).lambda$setTextAsync$0(applicationInfo.loadSafeLabel(getPackageManager(), 1000.0f, 5));
        ((TextView) inflate.findViewById(16908299)).lambda$setTextAsync$0(this.mHarmfulAppWarning);
        return inflate;
    }

    @Override // android.content.DialogInterface.OnClickListener
    public void onClick(DialogInterface dialogInterface, int i) {
        if (i != -2) {
            if (i != -1) {
                return;
            }
            getPackageManager().deletePackage(this.mPackageName, null, 0);
            EventLogTags.writeHarmfulAppWarningUninstall(this.mPackageName);
            finish();
            return;
        }
        getPackageManager().setHarmfulAppWarning(this.mPackageName, null);
        try {
            startIntentSenderForResult((IntentSender) getIntent().getParcelableExtra("android.intent.extra.INTENT", IntentSender.class), -1, (Intent) null, 0, 0, 0, ActivityOptions.makeBasic().setPendingIntentBackgroundActivityStartMode(1).toBundle());
        } catch (IntentSender.SendIntentException e) {
            Log.e(TAG, "Error while starting intent sender", e);
        }
        EventLogTags.writeHarmfulAppWarningLaunchAnyway(this.mPackageName);
        finish();
    }

    public static Intent createHarmfulAppWarningIntent(Context context, String str, IntentSender intentSender, CharSequence charSequence) {
        Intent intent = new Intent();
        intent.setClass(context, HarmfulAppWarningActivity.class);
        intent.putExtra("android.intent.extra.PACKAGE_NAME", str);
        intent.putExtra("android.intent.extra.INTENT", intentSender);
        intent.putExtra(EXTRA_HARMFUL_APP_WARNING, charSequence);
        return intent;
    }
}
