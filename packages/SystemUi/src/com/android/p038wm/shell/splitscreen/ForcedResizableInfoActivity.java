package com.android.p038wm.shell.splitscreen;

import android.app.Activity;
import android.app.ActivityManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import com.android.systemui.R;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class ForcedResizableInfoActivity extends Activity implements View.OnTouchListener {
    public final RunnableC40971 mFinishRunnable = new Runnable() { // from class: com.android.wm.shell.splitscreen.ForcedResizableInfoActivity.1
        @Override // java.lang.Runnable
        public final void run() {
            ForcedResizableInfoActivity.this.finish();
        }
    };

    @Override // android.app.Activity
    public final void finish() {
        super.finish();
        overridePendingTransition(0, R.anim.forced_resizable_exit);
    }

    @Override // android.app.Activity
    public final void onCreate(Bundle bundle) {
        String string;
        if (!isInMultiWindowMode()) {
            finish();
        }
        super.onCreate(bundle);
        setContentView(R.layout.forced_resizable_activity);
        TextView textView = (TextView) findViewById(android.R.id.message);
        int intExtra = getIntent().getIntExtra("extra_forced_resizeable_reason", -1);
        if (intExtra == 1) {
            string = getString(R.string.multiwindow_forced_resizable);
        } else if (intExtra == 2) {
            string = getString(R.string.forced_resizable_secondary_display);
        } else if (intExtra == 3) {
            string = getString(R.string.multiwindow_forced_resizable);
        } else {
            if (intExtra != 4) {
                finish();
                return;
            }
            string = getString(R.string.freeform_forced_resizable_samsung_dex);
        }
        textView.setText(string);
        getWindow().setTitle(string);
        getWindow().getDecorView().setOnTouchListener(this);
    }

    @Override // android.app.Activity, android.view.KeyEvent.Callback
    public final boolean onKeyDown(int i, KeyEvent keyEvent) {
        finish();
        return true;
    }

    @Override // android.app.Activity
    public final void onStart() {
        super.onStart();
        getWindow().getDecorView().postDelayed(this.mFinishRunnable, 2500L);
    }

    @Override // android.app.Activity
    public final void onStop() {
        super.onStop();
        finish();
    }

    @Override // android.view.View.OnTouchListener
    public final boolean onTouch(View view, MotionEvent motionEvent) {
        finish();
        return true;
    }

    @Override // android.app.Activity
    public final void setTaskDescription(ActivityManager.TaskDescription taskDescription) {
    }
}
