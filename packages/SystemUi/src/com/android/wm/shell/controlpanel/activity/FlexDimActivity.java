package com.android.wm.shell.controlpanel.activity;

import android.app.Activity;
import android.app.ActivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.systemui.R;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class FlexDimActivity extends Activity implements View.OnTouchListener {
    public static FlexDimActivity sFlexDimActivity;
    public Handler mHandler;
    public boolean mIsScreenOffMode = false;
    public final FlexDimActivity$$ExternalSyntheticLambda0 mScreenOffRunnable = new Runnable() { // from class: com.android.wm.shell.controlpanel.activity.FlexDimActivity$$ExternalSyntheticLambda0
        @Override // java.lang.Runnable
        public final void run() {
            FlexDimActivity flexDimActivity = FlexDimActivity.this;
            FlexDimActivity flexDimActivity2 = FlexDimActivity.sFlexDimActivity;
            flexDimActivity.setContentView(R.layout.flex_screen_off_mode_layout);
            flexDimActivity.mIsScreenOffMode = true;
        }
    };

    @Override // android.app.Activity
    public final void finish() {
        KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m78m(5, new StringBuilder("finish: Caller="), "FlexDimActivity");
        super.finish();
    }

    @Override // android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        sFlexDimActivity = this;
        setContentView(R.layout.flex_dim_layout);
        getWindow().getAttributes().samsungFlags |= 16777216;
        getWindow().setDecorFitsSystemWindows(false);
        overrideActivityTransition(0, 0, 0);
        int intForUser = Settings.System.getIntForUser(getContentResolver(), "screen_off_timeout", 30000, ActivityManager.semGetCurrentUser()) - 5000;
        int i = intForUser > 0 ? intForUser : 0;
        Handler handler = new Handler();
        this.mHandler = handler;
        handler.postDelayed(this.mScreenOffRunnable, i);
        findViewById(R.id.flex_dim_area).setOnTouchListener(this);
    }

    @Override // android.app.Activity
    public final void onDestroy() {
        sFlexDimActivity = null;
        super.onDestroy();
    }

    @Override // android.view.View.OnTouchListener
    public final boolean onTouch(View view, MotionEvent motionEvent) {
        if (this.mIsScreenOffMode || FlexPanelActivity.sFlexPanelActivity == null) {
            return false;
        }
        int action = motionEvent.getAction();
        if (action == 0) {
            Log.d("FlexDimActivity", "Touch Down mIsScreenOffMode=" + this.mIsScreenOffMode + " x=" + motionEvent.getX() + " y=" + motionEvent.getY());
            findViewById(R.id.flex_dim_area).setBackgroundColor(0);
            this.mHandler.removeCallbacks(this.mScreenOffRunnable);
            FlexPanelActivity.sFlexPanelActivity.mIsDimTouched = true;
        } else if (action == 1 || action == 3) {
            Log.d("FlexDimActivity", "Touch Up mIsScreenOffMode=" + this.mIsScreenOffMode + " x=" + motionEvent.getX() + " y=" + motionEvent.getY());
            finish();
        }
        FlexPanelActivity.sFlexPanelActivity.dispatchTouchEvent(motionEvent);
        return true;
    }
}
