package com.android.wm.shell.common.split;

import android.view.MotionEvent;
import android.view.SurfaceControl;
import android.view.SurfaceControlViewHost;
import android.view.View;
import com.samsung.android.core.CoreSaLogger;
import com.samsung.android.rune.CoreRune;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class OffscreenTouchZone {
    public final boolean mIsTopLeft;
    public SurfaceControl mLeash;
    public final Runnable mOnClickRunnable;
    public SurfaceControlViewHost mViewHost;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class OffscreenTouchListener implements View.OnTouchListener {
        public /* synthetic */ OffscreenTouchListener(OffscreenTouchZone offscreenTouchZone, int i) {
            this();
        }

        @Override // android.view.View.OnTouchListener
        public final boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() != 1) {
                return false;
            }
            OffscreenTouchZone.this.mOnClickRunnable.run();
            if (CoreRune.MW_SA_LOGGING) {
                CoreSaLogger.logForAdvanced("1007");
            }
            return true;
        }

        private OffscreenTouchListener() {
        }
    }

    public OffscreenTouchZone(boolean z, Runnable runnable) {
        this.mIsTopLeft = z;
        this.mOnClickRunnable = runnable;
    }
}
