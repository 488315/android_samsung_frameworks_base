package com.samsung.android.core.pm.install;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemProperties;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.android.internal.R;
import com.android.internal.app.AlertActivity;
import com.samsung.android.feature.SemCscFeature;
import com.samsung.android.wallpaperbackup.BnRConstants;

/* loaded from: classes6.dex */
public class UnknownSourceConfirmActivity extends AlertActivity implements DialogInterface.OnClickListener {
    public static final String EXTRA_INSTALL_TYPE = "android.content.pm.extra.unknown.installtype";
    private static final String EXTRA_SESSION_ID = "android.content.pm.extra.SESSION_ID";
    private static final String TAG = "UnknownSourceAppManager";
    private boolean mButtonClicked = false;
    private int mSessionId;

    @Override // com.android.internal.app.AlertActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mSessionId = getIntent().getIntExtra("android.content.pm.extra.SESSION_ID", 0);
        int installType = getIntent().getIntExtra(EXTRA_INSTALL_TYPE, 0);
        initUI(installType);
    }

    private void initUI(int installType) {
        getWindow().setGravity(80);
        initAlertParams(installType);
        setupAlert();
    }

    private void initAlertParams(final int installType) {
        boolean isTablet = SystemProperties.get("ro.build.characteristics").contains(BnRConstants.DEVICETYPE_TABLET);
        switch (installType) {
            case 1:
                this.mAlertParams.mTitle = getString(R.string.unknown_install_blocked_dlg_title);
                this.mAlertParams.mMessage = getString(R.string.unknown_install_blocked_dlg_desc);
                this.mAlertParams.mPositiveButtonText = getString(17039370);
                this.mAlertParams.mPositiveButtonListener = this;
                break;
            case 100:
                this.mAlertParams.mTitle = getString(R.string.unknown_install_dlg_general_title);
                if (isTablet) {
                    this.mAlertParams.mMessage = getString(R.string.unknown_install_dlg_desc_tablet);
                } else {
                    this.mAlertParams.mMessage = getString(R.string.unknown_install_dlg_desc_phone);
                }
                this.mAlertParams.mPositiveButtonText = getString(R.string.unknown_install_dlg_negative_button);
                this.mAlertParams.mNegativeButtonText = getString(R.string.unknown_install_dlg_positive_button);
                this.mAlertParams.mNegativeButtonListener = this;
                break;
            case 101:
                this.mAlertParams.mTitle = getString(R.string.unknown_install_dlg_general_title);
                if (isTablet) {
                    this.mAlertParams.mMessage = getString(R.string.unknown_install_dlg_desc_global_tablet);
                } else {
                    this.mAlertParams.mMessage = getString(R.string.unknown_install_dlg_desc_global_phone);
                }
                this.mAlertParams.mPositiveButtonText = getString(R.string.unknown_install_dlg_negative_button);
                this.mAlertParams.mNegativeButtonText = getString(R.string.unknown_install_dlg_positive_button);
                this.mAlertParams.mNegativeButtonListener = this;
                break;
            case 102:
                this.mAlertParams.mTitle = getString(R.string.unknown_install_dlg_title);
                if (isTablet) {
                    this.mAlertParams.mMessage = getString(R.string.unknown_install_publicsign_dlg_desc_tablet);
                } else {
                    this.mAlertParams.mMessage = getString(R.string.unknown_install_publicsign_dlg_desc_phone);
                }
                this.mAlertParams.mPositiveButtonText = getString(R.string.unknown_install_dlg_negative_button);
                this.mAlertParams.mNegativeButtonText = getString(R.string.unknown_install_dlg_positive_button);
                this.mAlertParams.mNegativeButtonListener = this;
                break;
            case 127:
                this.mAlertParams.mCustomTitleView = LayoutInflater.from(this).inflate(R.layout.dialog_unknownsource_autoblocker, (ViewGroup) null);
                if (isChinaDevice()) {
                    if (isTablet) {
                        this.mAlertParams.mMessage = getString(R.string.unknown_install_blocked_esm_desc_tablet_china);
                    } else {
                        this.mAlertParams.mMessage = getString(R.string.unknown_install_blocked_esm_desc_phone_china);
                    }
                } else if (isTablet) {
                    this.mAlertParams.mMessage = getString(R.string.unknown_install_blocked_esm_desc_tablet);
                } else {
                    this.mAlertParams.mMessage = getString(R.string.unknown_install_blocked_esm_desc_phone);
                }
                this.mAlertParams.mPositiveButtonText = getString(17039370);
                this.mAlertParams.mPositiveButtonListener = this;
                break;
            case 140:
                this.mAlertParams.mTitle = getString(R.string.unknown_install_dlg_title);
                if (isTablet) {
                    this.mAlertParams.mMessage = getString(R.string.unknown_install_dlg_desc_phishing_tablet);
                } else {
                    this.mAlertParams.mMessage = getString(R.string.unknown_install_dlg_desc_phishing_phone);
                }
                this.mAlertParams.mPositiveButtonText = getString(R.string.unknown_install_dlg_negative_button);
                this.mAlertParams.mNegativeButtonText = getString(R.string.unknown_install_dlg_positive_button);
                this.mAlertParams.mNegativeButtonListener = new DialogInterface.OnClickListener() { // from class: com.samsung.android.core.pm.install.UnknownSourceConfirmActivity$$ExternalSyntheticLambda0
                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i) {
                        UnknownSourceConfirmActivity.this.lambda$initAlertParams$0(installType, dialogInterface, i);
                    }
                };
                break;
            default:
                Log.d(TAG, "Invalid install type: " + installType);
                break;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initAlertParams$0(int installType, DialogInterface dialogInterface, int i) {
        this.mButtonClicked = true;
        Intent intent = new Intent();
        intent.setClass(getApplicationContext(), UnknownSourceAppBlockActivity.class);
        intent.setFlags(268468224);
        intent.putExtra("android.content.pm.extra.SESSION_ID", this.mSessionId);
        intent.putExtra(EXTRA_INSTALL_TYPE, installType);
        startActivity(intent);
    }

    @Override // android.app.Activity
    public void onStop() {
        super.onStop();
        if (!this.mButtonClicked) {
            rejectInstall();
        }
    }

    @Override // android.content.DialogInterface.OnClickListener
    public void onClick(DialogInterface dialog, int which) {
        Log.d(TAG, "onClick " + which);
        this.mButtonClicked = true;
        switch (which) {
            case -2:
                allowInstall();
                break;
            case -1:
                rejectInstall();
                break;
        }
        finish();
    }

    private void rejectInstall() {
        Log.d(TAG, "Reject installing");
        getPackageManager().getPackageInstaller().setUnknownSourceConfirmResult(this.mSessionId, false);
    }

    private void allowInstall() {
        Log.d(TAG, "Allow installing");
        getPackageManager().getPackageInstaller().setUnknownSourceConfirmResult(this.mSessionId, true);
    }

    private boolean isChinaDevice() {
        return "CN".equalsIgnoreCase(SemCscFeature.getInstance().getString("CountryISO"));
    }
}
