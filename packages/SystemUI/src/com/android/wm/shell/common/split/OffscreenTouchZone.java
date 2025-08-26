package com.android.wm.shell.common.split;

import android.view.MotionEvent;
import android.view.SurfaceControl;
import android.view.SurfaceControlViewHost;
import android.view.View;
import com.samsung.android.core.CoreSaLogger;
import com.samsung.android.rune.CoreRune;

/* loaded from: classes3.dex */
public class OffscreenTouchZone {
    public final boolean mIsTopLeft;
    public SurfaceControl mLeash;
    public final Runnable mOnClickRunnable;
    public SurfaceControlViewHost mViewHost;

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
