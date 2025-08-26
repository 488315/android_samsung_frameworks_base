package com.android.internal.app;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityOptions;
import android.app.ActivityTaskManager;
import android.app.ActivityThread;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.internal.R;

/* loaded from: classes5.dex */
public class HeavyWeightSwitcherActivity extends Activity {
    public static final String KEY_CUR_APP = "cur_app";
    public static final String KEY_CUR_TASK = "cur_task";
    public static final String KEY_HAS_RESULT = "has_result";
    public static final String KEY_INTENT = "intent";
    public static final String KEY_NEW_APP = "new_app";
    String mCurApp;
    int mCurTask;
    boolean mHasResult;
    String mNewApp;
    IntentSender mStartIntent;
    private View.OnClickListener mSwitchOldListener = new View.OnClickListener() { // from class: com.android.internal.app.HeavyWeightSwitcherActivity.1
        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            try {
                ActivityTaskManager.getService().moveTaskToFront(ActivityThread.currentActivityThread().getApplicationThread(), HeavyWeightSwitcherActivity.this.getPackageName(), HeavyWeightSwitcherActivity.this.mCurTask, 0, null);
            } catch (RemoteException unused) {
            }
            HeavyWeightSwitcherActivity.this.finish();
        }
    };
    private View.OnClickListener mSwitchNewListener = new View.OnClickListener() { // from class: com.android.internal.app.HeavyWeightSwitcherActivity.2
        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            try {
                ActivityManager.getService().finishHeavyWeightApp();
            } catch (RemoteException unused) {
            }
            try {
                if (HeavyWeightSwitcherActivity.this.mHasResult) {
                    HeavyWeightSwitcherActivity heavyWeightSwitcherActivity = HeavyWeightSwitcherActivity.this;
                    heavyWeightSwitcherActivity.startIntentSenderForResult(heavyWeightSwitcherActivity.mStartIntent, -1, null, 33554432, 33554432, 0);
                } else {
                    ActivityOptions pendingIntentBackgroundActivityStartMode = ActivityOptions.makeBasic().setPendingIntentBackgroundActivityStartMode(1);
                    HeavyWeightSwitcherActivity heavyWeightSwitcherActivity2 = HeavyWeightSwitcherActivity.this;
                    heavyWeightSwitcherActivity2.startIntentSenderForResult(heavyWeightSwitcherActivity2.mStartIntent, -1, (Intent) null, 0, 0, 0, pendingIntentBackgroundActivityStartMode.toBundle());
                }
            } catch (IntentSender.SendIntentException e) {
                Log.w("HeavyWeightSwitcherActivity", "Failure starting", e);
            }
            HeavyWeightSwitcherActivity.this.finish();
        }
    };

    @Override // android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(1);
        this.mStartIntent = (IntentSender) getIntent().getParcelableExtra("intent", IntentSender.class);
        this.mHasResult = getIntent().getBooleanExtra(KEY_HAS_RESULT, false);
        this.mCurApp = getIntent().getStringExtra(KEY_CUR_APP);
        this.mCurTask = getIntent().getIntExtra(KEY_CUR_TASK, 0);
        this.mNewApp = getIntent().getStringExtra(KEY_NEW_APP);
        setContentView(R.layout.heavy_weight_switcher);
        setIconAndText(R.id.old_app_icon, R.id.old_app_action, 0, this.mCurApp, this.mNewApp, R.string.old_app_action, 0);
        setIconAndText(R.id.new_app_icon, R.id.new_app_action, R.id.new_app_description, this.mNewApp, this.mCurApp, R.string.new_app_action, R.string.new_app_description);
        findViewById(R.id.switch_old).setOnClickListener(this.mSwitchOldListener);
        findViewById(R.id.switch_new).setOnClickListener(this.mSwitchNewListener);
    }

    void setText(int i, CharSequence charSequence) {
        ((TextView) findViewById(i)).lambda$setTextAsync$0(charSequence);
    }

    void setDrawable(int i, Drawable drawable) {
        if (drawable != null) {
            ((ImageView) findViewById(i)).lambda$setImageURIAsync$0(drawable);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r8v1 */
    /* JADX WARN: Type inference failed for: r8v2, types: [java.lang.CharSequence] */
    /* JADX WARN: Type inference failed for: r9v2, types: [java.lang.CharSequence] */
    void setIconAndText(int i, int i2, int i3, String str, String str2, int i4, int i5) {
        Drawable drawableLoadIcon = null;
        if (str != 0) {
            try {
                ApplicationInfo applicationInfo = getPackageManager().getApplicationInfo(str, 0);
                str = applicationInfo.loadLabel(getPackageManager());
                drawableLoadIcon = applicationInfo.loadIcon(getPackageManager());
            } catch (PackageManager.NameNotFoundException unused) {
            }
        }
        setDrawable(i, drawableLoadIcon);
        setText(i2, getString(i4, str));
        if (i3 != 0) {
            if (str2 != 0) {
                try {
                    str2 = getPackageManager().getApplicationInfo(str2, 0).loadLabel(getPackageManager());
                } catch (PackageManager.NameNotFoundException unused2) {
                }
            }
            setText(i3, getString(i5, str2));
        }
    }
}
