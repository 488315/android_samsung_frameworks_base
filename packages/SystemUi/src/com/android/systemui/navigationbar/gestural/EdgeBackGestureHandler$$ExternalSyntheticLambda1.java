package com.android.systemui.navigationbar.gestural;

import android.os.Trace;
import android.provider.DeviceConfig;
import android.util.Log;
import com.android.systemui.util.Assert;
import java.util.HashMap;
import java.util.Map;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final /* synthetic */ class EdgeBackGestureHandler$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ EdgeBackGestureHandler f$0;

    public /* synthetic */ EdgeBackGestureHandler$$ExternalSyntheticLambda1(EdgeBackGestureHandler edgeBackGestureHandler, int i) {
        this.$r8$classId = i;
        this.f$0 = edgeBackGestureHandler;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                EdgeBackGestureHandler edgeBackGestureHandler = this.f$0;
                boolean isHandlingGestures = edgeBackGestureHandler.isHandlingGestures();
                edgeBackGestureHandler.updateCurrentUserResources();
                if (edgeBackGestureHandler.mStateChangeCallback != null && isHandlingGestures != edgeBackGestureHandler.isHandlingGestures()) {
                    edgeBackGestureHandler.mStateChangeCallback.run();
                    break;
                }
                break;
            default:
                final EdgeBackGestureHandler edgeBackGestureHandler2 = this.f$0;
                final BackGestureTfClassifierProvider backGestureTfClassifierProvider = (BackGestureTfClassifierProvider) edgeBackGestureHandler2.mBackGestureTfClassifierProviderProvider.get();
                final float f = DeviceConfig.getFloat("systemui", "back_gesture_ml_model_threshold", 0.9f);
                final HashMap hashMap = null;
                if (backGestureTfClassifierProvider != null) {
                    Log.w("EdgeBackGestureHandler", "Cannot load model because it isn't active");
                    backGestureTfClassifierProvider = null;
                }
                if (backGestureTfClassifierProvider != null) {
                    Trace.beginSection("EdgeBackGestureHandler#loadVocab");
                    edgeBackGestureHandler2.mContext.getAssets();
                    hashMap = new HashMap();
                    Trace.endSection();
                }
                edgeBackGestureHandler2.mMainExecutor.execute(new Runnable() { // from class: com.android.systemui.navigationbar.gestural.EdgeBackGestureHandler$$ExternalSyntheticLambda6
                    @Override // java.lang.Runnable
                    public final void run() {
                        EdgeBackGestureHandler edgeBackGestureHandler3 = EdgeBackGestureHandler.this;
                        BackGestureTfClassifierProvider backGestureTfClassifierProvider2 = backGestureTfClassifierProvider;
                        Map map = hashMap;
                        float f2 = f;
                        edgeBackGestureHandler3.getClass();
                        Assert.isMainThread();
                        edgeBackGestureHandler3.mMLModelIsLoading = false;
                        if (!edgeBackGestureHandler3.mUseMLModel) {
                            Log.d("EdgeBackGestureHandler", "Model finished loading but isn't needed.");
                            return;
                        }
                        edgeBackGestureHandler3.mBackGestureTfClassifierProvider = backGestureTfClassifierProvider2;
                        edgeBackGestureHandler3.mVocab = map;
                        edgeBackGestureHandler3.mMLModelThreshold = f2;
                    }
                });
                break;
        }
    }
}
