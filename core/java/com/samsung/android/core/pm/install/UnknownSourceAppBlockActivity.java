package com.samsung.android.core.pm.install;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Intent;
import android.content.pm.PackageInstaller;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemProperties;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.internal.R;

import com.samsung.android.wallpaperbackup.BnRConstants;

public class UnknownSourceAppBlockActivity extends Activity {
    private static final String SECURITY_PORTAL =
            "https://security.samsungmobile.com/securityPost.smsb";
    private static final String TAG = "UnknownSourceAppManager";
    private ActivityManager mAm;
    private int mBrowserUidForLink;
    private int mInstallType;
    private int mSessionId;
    private int mUiMode;
    private boolean mButtonClicked = false;
    private boolean mLinkClicked = false;
    private boolean mIsAppBlockActivityClosed = false;
    private boolean mIsBrowserClosed = false;
    private final ActivityManager.SemProcessListener mSemProcessListener =
            new ActivityManager
                    .SemProcessListener() { // from class:
                                            // com.samsung.android.core.pm.install.UnknownSourceAppBlockActivity.1
                @Override // android.app.ActivityManager.SemProcessListener
                public void onForegroundActivitiesChanged(
                        int pid, int uid, boolean foregroundActivities) {
                    if (foregroundActivities) {
                        if (uid == UnknownSourceAppBlockActivity.this.mBrowserUidForLink) {
                            UnknownSourceAppBlockActivity.this.mIsBrowserClosed = false;
                            return;
                        } else {
                            if (uid == 1000) {
                                UnknownSourceAppBlockActivity.this.mIsAppBlockActivityClosed =
                                        false;
                                return;
                            }
                            return;
                        }
                    }
                    if (uid == UnknownSourceAppBlockActivity.this.mBrowserUidForLink) {
                        UnknownSourceAppBlockActivity.this.mIsBrowserClosed = true;
                        if (UnknownSourceAppBlockActivity.this.mIsBrowserClosed
                                && UnknownSourceAppBlockActivity.this.mIsAppBlockActivityClosed) {
                            UnknownSourceAppBlockActivity.this.rejectInstall();
                            return;
                        }
                        return;
                    }
                    if (uid == 1000) {
                        UnknownSourceAppBlockActivity.this.mIsAppBlockActivityClosed = true;
                    }
                }

                @Override // android.app.ActivityManager.SemProcessListener
                public void onProcessDied(int pid, int uid) {}
            };

    @Override // android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mUiMode = getResources().getConfiguration().uiMode;
        this.mSessionId = getIntent().getIntExtra(PackageInstaller.EXTRA_SESSION_ID, 0);
        this.mInstallType =
                getIntent().getIntExtra(UnknownSourceConfirmActivity.EXTRA_INSTALL_TYPE, 0);
        this.mAm = (ActivityManager) getApplicationContext().getSystemService("activity");
        this.mAm.semRegisterProcessListener(this.mSemProcessListener);
        setContentView();
    }

    private void setContentView() {
        setContentView(R.layout.activity_unknownsource_appblock);
        ImageView icon = (ImageView) findViewById(R.id.appblock_icon);
        TextView title = (TextView) findViewById(R.id.appblock_title);
        TextView desc = (TextView) findViewById(R.id.appblock_desc);
        TextView link = (TextView) findViewById(R.id.appblock_link);
        link.setPaintFlags(link.getPaintFlags() | 8);
        link.setVisibility(8);
        if (this.mInstallType == 150) {
            icon.setImageResource(R.drawable.ic_unknownsource_error);
            title.setText(R.string.unknown_install_activity_warning_title);
            boolean isTablet =
                    SystemProperties.get("ro.build.characteristics")
                            .contains(BnRConstants.DEVICETYPE_TABLET);
            if (isTablet) {
                desc.setText(R.string.appblock_block_text_tablet);
            } else {
                desc.setText(R.string.appblock_block_text_phone);
            }
            changeToBlockButton();
        }
    }

    private void changeToBlockButton() {
        Button installAnywayBtn = (Button) findViewById(R.id.install_anyway_button);
        Button installDenyBtn = (Button) findViewById(R.id.install_deny_button);
        installAnywayBtn.setVisibility(8);
        installDenyBtn.lambda$setTextAsync$0(getString(17039370));
    }

    @Override // android.app.Activity, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        setContentView();
        if (newConfig.uiMode != this.mUiMode) {
            this.mUiMode = newConfig.uiMode;
            int newColor = getResources().getColor(R.color.unknownsource_background);
            ImageView icon = (ImageView) findViewById(R.id.appblock_icon);
            icon.semSetDisplayCutoutBackgroundColor(newColor);
            getWindow().setNavigationBarColor(newColor);
            getWindow().setStatusBarColor(newColor);
        }
    }

    @Override // android.app.Activity
    public void onStop() {
        super.onStop();
        if (!this.mButtonClicked && !this.mLinkClicked) {
            rejectInstall();
        }
        this.mLinkClicked = false;
    }

    @Override // android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        this.mAm.semUnregisterProcessListener(this.mSemProcessListener);
    }

    public void onInstallButtonClick(View view) {
        this.mButtonClicked = true;
        if (view.getId() == 16909199) {
            Log.d(TAG, "Allow installing");
            getPackageManager()
                    .getPackageInstaller()
                    .setUnknownSourceConfirmResult(this.mSessionId, true);
        } else if (view.getId() == 16909200) {
            rejectInstall();
        }
        finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void rejectInstall() {
        Log.d(TAG, "Reject installing");
        getPackageManager()
                .getPackageInstaller()
                .setUnknownSourceConfirmResult(this.mSessionId, false);
    }

    public void onLinkClick(View view) {
        this.mLinkClicked = true;
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setData(Uri.parse(SECURITY_PORTAL));
        ResolveInfo resolveInfo = getPackageManager().resolveActivity(intent, 0);
        try {
            this.mBrowserUidForLink =
                    getPackageManager()
                            .getApplicationInfo(resolveInfo.activityInfo.packageName, 0)
                            .uid;
        } catch (PackageManager.NameNotFoundException | NullPointerException e) {
            Log.e(TAG, "Cannot resolve a browser for link", e);
            rejectInstall();
        }
        startActivity(intent);
    }
}
