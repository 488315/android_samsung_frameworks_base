package com.samsung.android.core.pm.install;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Intent;
import android.content.pm.PackageInstaller;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Insets;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemProperties;
import android.util.Log;
import android.view.View;
import android.view.WindowInsets;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.internal.R;
import com.samsung.android.wallpaperbackup.BnRConstants;

/* loaded from: classes6.dex */
public class UnknownSourceAppBlockActivity extends Activity {
    private static final String SECURITY_PORTAL = "https://security.samsungmobile.com/securityPost.smsb";
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
    private final ActivityManager.SemProcessListener mSemProcessListener = new ActivityManager.SemProcessListener() { // from class: com.samsung.android.core.pm.install.UnknownSourceAppBlockActivity.1
        @Override // android.app.ActivityManager.SemProcessListener
        public void onProcessDied(int i, int i2) {
        }

        @Override // android.app.ActivityManager.SemProcessListener
        public void onForegroundActivitiesChanged(int i, int i2, boolean z) {
            if (z) {
                if (i2 == UnknownSourceAppBlockActivity.this.mBrowserUidForLink) {
                    UnknownSourceAppBlockActivity.this.mIsBrowserClosed = false;
                    return;
                } else {
                    if (i2 == 1000) {
                        UnknownSourceAppBlockActivity.this.mIsAppBlockActivityClosed = false;
                        return;
                    }
                    return;
                }
            }
            if (i2 != UnknownSourceAppBlockActivity.this.mBrowserUidForLink) {
                if (i2 == 1000) {
                    UnknownSourceAppBlockActivity.this.mIsAppBlockActivityClosed = true;
                }
            } else {
                UnknownSourceAppBlockActivity.this.mIsBrowserClosed = true;
                if (UnknownSourceAppBlockActivity.this.mIsBrowserClosed && UnknownSourceAppBlockActivity.this.mIsAppBlockActivityClosed) {
                    UnknownSourceAppBlockActivity.this.rejectInstall();
                }
            }
        }
    };

    @Override // android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getWindow().setDecorFitsSystemWindows(false);
        getWindow().getDecorView().setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener() { // from class: com.samsung.android.core.pm.install.UnknownSourceAppBlockActivity$$ExternalSyntheticLambda0
            @Override // android.view.View.OnApplyWindowInsetsListener
            public final WindowInsets onApplyWindowInsets(View view, WindowInsets windowInsets) {
                return UnknownSourceAppBlockActivity.lambda$onCreate$0(view, windowInsets);
            }
        });
        this.mUiMode = getResources().getConfiguration().uiMode;
        this.mSessionId = getIntent().getIntExtra(PackageInstaller.EXTRA_SESSION_ID, 0);
        this.mInstallType = getIntent().getIntExtra(UnknownSourceConfirmActivity.EXTRA_INSTALL_TYPE, 0);
        ActivityManager activityManager = (ActivityManager) getApplicationContext().getSystemService("activity");
        this.mAm = activityManager;
        activityManager.semRegisterProcessListener(this.mSemProcessListener);
        setContentView();
    }

    static /* synthetic */ WindowInsets lambda$onCreate$0(View view, WindowInsets windowInsets) {
        Insets insets = windowInsets.getInsets(WindowInsets.Type.systemBars());
        view.setPadding(insets.left, insets.top, insets.right, insets.bottom);
        return WindowInsets.CONSUMED;
    }

    private void setContentView() {
        setContentView(R.layout.activity_unknownsource_appblock);
        ImageView imageView = (ImageView) findViewById(R.id.appblock_icon);
        TextView textView = (TextView) findViewById(R.id.appblock_title);
        TextView textView2 = (TextView) findViewById(R.id.appblock_desc);
        TextView textView3 = (TextView) findViewById(R.id.appblock_link);
        textView3.setPaintFlags(textView3.getPaintFlags() | 8);
        textView3.setVisibility(8);
        if (this.mInstallType == 150) {
            imageView.setImageResource(R.drawable.ic_unknownsource_error);
            textView.setText(R.string.unknown_install_activity_warning_title);
            if (SystemProperties.get("ro.build.characteristics").contains(BnRConstants.DEVICETYPE_TABLET)) {
                textView2.setText(R.string.appblock_block_text_tablet);
            } else {
                textView2.setText(R.string.appblock_block_text_phone);
            }
            changeToBlockButton();
        }
    }

    private void changeToBlockButton() {
        Button button = (Button) findViewById(R.id.install_anyway_button);
        Button button2 = (Button) findViewById(R.id.install_deny_button);
        button.setVisibility(8);
        button2.lambda$setTextAsync$0(getString(17039370));
    }

    @Override // android.app.Activity, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) throws Resources.NotFoundException {
        super.onConfigurationChanged(configuration);
        setContentView();
        if (configuration.uiMode != this.mUiMode) {
            this.mUiMode = configuration.uiMode;
            int color = getResources().getColor(R.color.unknownsource_background);
            ((ImageView) findViewById(R.id.appblock_icon)).semSetDisplayCutoutBackgroundColor(color);
            getWindow().setNavigationBarColor(color);
            getWindow().setStatusBarColor(color);
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
        if (view.getId() == 16909239) {
            Log.d(TAG, "Allow installing");
            getPackageManager().getPackageInstaller().setUnknownSourceConfirmResult(this.mSessionId, true);
        } else if (view.getId() == 16909240) {
            rejectInstall();
        }
        finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void rejectInstall() {
        Log.d(TAG, "Reject installing");
        getPackageManager().getPackageInstaller().setUnknownSourceConfirmResult(this.mSessionId, false);
    }

    public void onLinkClick(View view) {
        this.mLinkClicked = true;
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setData(Uri.parse(SECURITY_PORTAL));
        try {
            this.mBrowserUidForLink = getPackageManager().getApplicationInfo(getPackageManager().resolveActivity(intent, 0).activityInfo.packageName, 0).uid;
        } catch (PackageManager.NameNotFoundException | NullPointerException e) {
            Log.e(TAG, "Cannot resolve a browser for link", e);
            rejectInstall();
        }
        startActivity(intent);
    }
}
