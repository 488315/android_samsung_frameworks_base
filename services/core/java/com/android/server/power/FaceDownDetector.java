package com.android.server.power;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.hardware.SensorEventListener;

/* loaded from: classes3.dex */
public abstract class FaceDownDetector implements SensorEventListener {
    /* renamed from: -$$Nest$fputmInteractive, reason: not valid java name */
    public static /* bridge */ /* synthetic */ void m10242$$Nest$fputmInteractive(FaceDownDetector faceDownDetector, boolean z) {
        throw null;
    }

    /* renamed from: -$$Nest$mupdateActiveState, reason: not valid java name */
    public static /* bridge */ /* synthetic */ void m10243$$Nest$mupdateActiveState(FaceDownDetector faceDownDetector) {
        throw null;
    }

    public final class ScreenStateReceiver extends BroadcastReceiver {
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if ("android.intent.action.SCREEN_OFF".equals(intent.getAction())) {
                FaceDownDetector.m10242$$Nest$fputmInteractive(null, false);
                FaceDownDetector.m10243$$Nest$mupdateActiveState(null);
            } else if ("android.intent.action.SCREEN_ON".equals(intent.getAction())) {
                FaceDownDetector.m10242$$Nest$fputmInteractive(null, true);
                FaceDownDetector.m10243$$Nest$mupdateActiveState(null);
            }
        }
    }
}
