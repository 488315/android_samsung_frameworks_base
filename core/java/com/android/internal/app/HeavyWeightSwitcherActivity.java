package com.android.internal.app;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityOptions;
import android.app.ActivityTaskManager;
import android.app.ActivityThread;
import android.app.IApplicationThread;
import android.content.Intent;
import android.content.IntentSender;
import android.content.p002pm.ApplicationInfo;
import android.content.p002pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.p009os.Bundle;
import android.p009os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.internal.C4337R;

/* loaded from: classes4.dex */
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
        public void onClick(View v) {
            try {
                ActivityThread thread = ActivityThread.currentActivityThread();
                IApplicationThread appThread = thread.getApplicationThread();
                ActivityTaskManager.getService().moveTaskToFront(appThread, HeavyWeightSwitcherActivity.this.getPackageName(), HeavyWeightSwitcherActivity.this.mCurTask, 0, null);
            } catch (RemoteException e) {
            }
            HeavyWeightSwitcherActivity.this.finish();
        }
    };
    private View.OnClickListener mSwitchNewListener = new View.OnClickListener() { // from class: com.android.internal.app.HeavyWeightSwitcherActivity.2
        @Override // android.view.View.OnClickListener
        public void onClick(View v) {
            try {
                ActivityManager.getService().finishHeavyWeightApp();
            } catch (RemoteException e) {
            }
            try {
                if (HeavyWeightSwitcherActivity.this.mHasResult) {
                    HeavyWeightSwitcherActivity heavyWeightSwitcherActivity = HeavyWeightSwitcherActivity.this;
                    heavyWeightSwitcherActivity.startIntentSenderForResult(heavyWeightSwitcherActivity.mStartIntent, -1, null, 33554432, 33554432, 0);
                } else {
                    ActivityOptions activityOptions = ActivityOptions.makeBasic().setPendingIntentBackgroundActivityStartMode(1);
                    HeavyWeightSwitcherActivity heavyWeightSwitcherActivity2 = HeavyWeightSwitcherActivity.this;
                    heavyWeightSwitcherActivity2.startIntentSenderForResult(heavyWeightSwitcherActivity2.mStartIntent, -1, (Intent) null, 0, 0, 0, activityOptions.toBundle());
                }
            } catch (IntentSender.SendIntentException ex) {
                Log.m103w("HeavyWeightSwitcherActivity", "Failure starting", ex);
            }
            HeavyWeightSwitcherActivity.this.finish();
        }
    };

    @Override // android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        this.mStartIntent = (IntentSender) getIntent().getParcelableExtra("intent", IntentSender.class);
        this.mHasResult = getIntent().getBooleanExtra(KEY_HAS_RESULT, false);
        this.mCurApp = getIntent().getStringExtra(KEY_CUR_APP);
        this.mCurTask = getIntent().getIntExtra(KEY_CUR_TASK, 0);
        this.mNewApp = getIntent().getStringExtra(KEY_NEW_APP);
        setContentView(C4337R.layout.heavy_weight_switcher);
        setIconAndText(C4337R.id.old_app_icon, C4337R.id.old_app_action, 0, this.mCurApp, this.mNewApp, C4337R.string.old_app_action, 0);
        setIconAndText(C4337R.id.new_app_icon, C4337R.id.new_app_action, C4337R.id.new_app_description, this.mNewApp, this.mCurApp, C4337R.string.new_app_action, C4337R.string.new_app_description);
        View button = findViewById(C4337R.id.switch_old);
        button.setOnClickListener(this.mSwitchOldListener);
        View button2 = findViewById(C4337R.id.switch_new);
        button2.setOnClickListener(this.mSwitchNewListener);
    }

    void setText(int id, CharSequence text) {
        ((TextView) findViewById(id)).setText(text);
    }

    void setDrawable(int id, Drawable dr) {
        if (dr != null) {
            ((ImageView) findViewById(id)).lambda$setImageURIAsync$2(dr);
        }
    }

    void setIconAndText(int iconId, int actionId, int descriptionId, String packageName, String otherPackageName, int actionStr, int descriptionStr) {
        CharSequence appName = packageName;
        Drawable appIcon = null;
        if (packageName != null) {
            try {
                ApplicationInfo info = getPackageManager().getApplicationInfo(packageName, 0);
                appName = info.loadLabel(getPackageManager());
                appIcon = info.loadIcon(getPackageManager());
            } catch (PackageManager.NameNotFoundException e) {
            }
        }
        setDrawable(iconId, appIcon);
        setText(actionId, getString(actionStr, appName));
        if (descriptionId != 0) {
            CharSequence otherAppName = otherPackageName;
            if (otherPackageName != null) {
                try {
                    otherAppName = getPackageManager().getApplicationInfo(otherPackageName, 0).loadLabel(getPackageManager());
                } catch (PackageManager.NameNotFoundException e2) {
                }
            }
            setText(descriptionId, getString(descriptionStr, otherAppName));
        }
    }
}
