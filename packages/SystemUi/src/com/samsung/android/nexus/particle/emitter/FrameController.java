package com.samsung.android.nexus.particle.emitter;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.samsung.android.nexus.base.animator.AnimatorCore;
import com.samsung.android.nexus.base.context.NexusContext;
import com.samsung.android.nexus.base.layer.LayerContainer;
import com.samsung.android.nexus.base.utils.Log;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class FrameController {
    public final LayerContainer mContainer;
    public boolean mIsStarted = false;
    public final int mMaxFrameRate = 60;
    public final int mMinFrameRate = 20;
    public int mCurFrameRate = 60;
    public final int mCheckInterval = 500;
    public final int mFrameDownStepFrame = 10;
    public final HandlerC47471 mFrameRateControlHandler = new Handler(Looper.myLooper()) { // from class: com.samsung.android.nexus.particle.emitter.FrameController.1
        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            boolean z;
            FrameController frameController = FrameController.this;
            int i = frameController.mCurFrameRate - frameController.mFrameDownStepFrame;
            frameController.mCurFrameRate = i;
            int i2 = frameController.mMinFrameRate;
            if (i <= i2) {
                frameController.mCurFrameRate = i2;
                z = false;
            } else {
                z = true;
            }
            Log.m262i("FrameController", "Do frame down rate : " + FrameController.this.mCurFrameRate);
            FrameController frameController2 = FrameController.this;
            LayerContainer layerContainer = frameController2.mContainer;
            int i3 = frameController2.mCurFrameRate;
            NexusContext nexusContext = layerContainer.getNexusContext();
            if (i3 <= 0) {
                nexusContext.getClass();
                Log.m261e("NexusContext", "setFrameRate() : Do NOT set a negative value.");
            }
            AnimatorCore animatorCore = nexusContext.mAnimatorCore;
            animatorCore.mFrameRate = i3;
            animatorCore.mFrameTime = 1000000000 / (i3 + 1);
            if (z) {
                sendEmptyMessageDelayed(0, r6.mCheckInterval);
            }
        }
    };

    /* JADX WARN: Type inference failed for: r0v4, types: [com.samsung.android.nexus.particle.emitter.FrameController$1] */
    public FrameController(LayerContainer layerContainer) {
        this.mContainer = layerContainer;
    }

    public final void startFrameRateDown() {
        if (this.mIsStarted) {
            return;
        }
        this.mIsStarted = true;
        Log.m262i("FrameController", "Start frame control.");
        this.mCurFrameRate = this.mMaxFrameRate;
        HandlerC47471 handlerC47471 = this.mFrameRateControlHandler;
        handlerC47471.removeMessages(0);
        handlerC47471.sendEmptyMessageDelayed(0, this.mCheckInterval);
    }
}
