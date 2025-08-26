package com.samsung.android.nexus.particle.emitter;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.samsung.android.nexus.base.animator.AnimatorCore;
import com.samsung.android.nexus.base.context.NexusContext;
import com.samsung.android.nexus.base.layer.LayerContainer;
import com.samsung.android.nexus.base.utils.Log;

/* loaded from: classes4.dex */
public class FrameController {
    public final LayerContainer mContainer;
    public boolean mIsStarted = false;
    public int mCurFrameRate = 60;
    public final AnonymousClass1 mFrameRateControlHandler = new Handler(Looper.myLooper()) { // from class: com.samsung.android.nexus.particle.emitter.FrameController.1
        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            boolean z;
            FrameController frameController = FrameController.this;
            frameController.getClass();
            int i = frameController.mCurFrameRate - 10;
            frameController.mCurFrameRate = i;
            if (i <= 20) {
                frameController.mCurFrameRate = 20;
                z = false;
            } else {
                z = true;
            }
            Log.i("FrameController", "Do frame down rate : " + frameController.mCurFrameRate);
            LayerContainer layerContainer = frameController.mContainer;
            int i2 = frameController.mCurFrameRate;
            NexusContext nexusContext = layerContainer.getNexusContext();
            if (i2 <= 0) {
                nexusContext.getClass();
                Log.e("NexusContext", "setFrameRate() : Do NOT set a negative value.");
            }
            AnimatorCore animatorCore = nexusContext.mAnimatorCore;
            animatorCore.mFrameRate = i2;
            animatorCore.mFrameTime = 1000000000 / (i2 + 1);
            if (z) {
                frameController.mFrameRateControlHandler.sendEmptyMessageDelayed(0, 500);
            }
        }
    };

    /* JADX WARN: Type inference failed for: r0v2, types: [com.samsung.android.nexus.particle.emitter.FrameController$1] */
    public FrameController(LayerContainer layerContainer) {
        this.mContainer = layerContainer;
    }

    public final void startFrameRateDown() {
        if (this.mIsStarted) {
            return;
        }
        this.mIsStarted = true;
        Log.i("FrameController", "Start frame control.");
        this.mCurFrameRate = 60;
        AnonymousClass1 anonymousClass1 = this.mFrameRateControlHandler;
        anonymousClass1.removeMessages(0);
        anonymousClass1.sendEmptyMessageDelayed(0, 500);
    }
}
