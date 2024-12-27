package com.samsung.android.biometrics.app.setting.fingerprint.vi;

import android.os.SystemClock;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.RenderMode;

import java.util.ArrayList;
import java.util.Iterator;

public final /* synthetic */ class VisualEffectContainer$$ExternalSyntheticLambda0
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ VisualEffectContainer f$0;

    public /* synthetic */ VisualEffectContainer$$ExternalSyntheticLambda0(
            VisualEffectContainer visualEffectContainer, int i) {
        this.$r8$classId = i;
        this.f$0 = visualEffectContainer;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        VisualEffectContainer visualEffectContainer = this.f$0;
        switch (i) {
            case 0:
                String str = VisualEffectContainer.ASSET_NAME_ULTRASONIC;
                visualEffectContainer.getClass();
                Log.i("BSS_VisualEffectContainer", "start()");
                Iterator it = ((ArrayList) visualEffectContainer.mEffectViews).iterator();
                while (it.hasNext()) {
                    Pair pair = (Pair) it.next();
                    Object obj = pair.first;
                    if (obj instanceof LottieAnimationView) {
                        visualEffectContainer.mEffectAnimationView.setRenderMode(
                                RenderMode.HARDWARE);
                        visualEffectContainer.mEffectAnimationView.setFrame(0);
                        if (!visualEffectContainer.mEffectAnimationView.isAnimating()) {
                            visualEffectContainer.mEffectAnimationView.playAnimation();
                        }
                    } else if (obj instanceof ImageView) {
                        ((View) obj).startAnimation((Animation) pair.second);
                    }
                }
                visualEffectContainer.mHandler.removeCallbacks(
                        visualEffectContainer.mStartCallback);
                visualEffectContainer.mHandler.removeCallbacks(visualEffectContainer.mStopCallback);
                visualEffectContainer.mHandler.postDelayed(
                        visualEffectContainer.mStopCallback,
                        visualEffectContainer.mAnimationDuration);
                visualEffectContainer.mIsReadTouchMap = true;
                visualEffectContainer.mTouchMapStartTime = SystemClock.elapsedRealtime();
                if (visualEffectContainer.mReadTouchMapThread == null) {
                    Thread thread =
                            new Thread(
                                    new VisualEffectContainer$$ExternalSyntheticLambda0(
                                            visualEffectContainer, 2));
                    visualEffectContainer.mReadTouchMapThread = thread;
                    thread.start();
                }
                visualEffectContainer.updateLayout();
                visualEffectContainer.setVisibility(0);
                return;
            case 1:
                String str2 = VisualEffectContainer.ASSET_NAME_ULTRASONIC;
                visualEffectContainer.stopVI();
                return;
            case 2:
                break;
            default:
                visualEffectContainer.invalidate();
                return;
        }
        while (visualEffectContainer.mTouchMap != null
                && visualEffectContainer.mIsReadTouchMap
                && SystemClock.elapsedRealtime() - visualEffectContainer.mTouchMapStartTime
                        < 2000) {
            try {
                VisualEffectContainer.SemVisualEffectTouchMap semVisualEffectTouchMap =
                        visualEffectContainer.mTouchMap;
                synchronized (semVisualEffectTouchMap) {
                    if (semVisualEffectTouchMap.mWidth != 0
                            && semVisualEffectTouchMap.mHeight != 0) {
                        int[][] readTouchMatrix =
                                semVisualEffectTouchMap.mTouchMapReader.readTouchMatrix();
                        if (readTouchMatrix != null) {
                            semVisualEffectTouchMap.mEffectPath =
                                    VisualEffectContainer.SemVisualEffectTouchMap
                                            .calculateClipOutPath(
                                                    semVisualEffectTouchMap.getOuterLine(
                                                            readTouchMatrix));
                        }
                    }
                }
                visualEffectContainer.mHandler.post(
                        new VisualEffectContainer$$ExternalSyntheticLambda0(
                                visualEffectContainer, 3));
                Thread.sleep(30L);
            } catch (InterruptedException unused) {
            }
        }
        visualEffectContainer.mReadTouchMapThread = null;
    }
}
