package com.android.systemui.wifi;

import android.util.EventLog;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;
import com.android.systemui.R;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class WifiDebuggingActivity$$ExternalSyntheticLambda0 implements View.OnTouchListener {
    @Override // android.view.View.OnTouchListener
    public final boolean onTouch(View view, MotionEvent motionEvent) {
        int i = WifiDebuggingActivity.$r8$clinit;
        if ((motionEvent.getFlags() & 1) == 0 && (motionEvent.getFlags() & 2) == 0) {
            return false;
        }
        if (motionEvent.getAction() != 1) {
            return true;
        }
        EventLog.writeEvent(1397638484, "62187985");
        Toast.makeText(view.getContext(), R.string.touch_filtered_warning, 0).show();
        return true;
    }
}
