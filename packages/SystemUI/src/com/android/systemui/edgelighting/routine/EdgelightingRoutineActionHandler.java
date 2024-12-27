package com.android.systemui.edgelighting.routine;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.PowerManager;
import android.util.Slog;
import com.android.systemui.edgelighting.effect.container.EdgeLightingDialog;
import com.android.systemui.edgelighting.effect.data.EdgeEffectInfo;
import com.android.systemui.edgelighting.effectservice.EdgeLightingDispatcher;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class EdgelightingRoutineActionHandler {
    public Context mContext;
    public EdgeLightingDispatcher mController;
    public String mEdgeLightingEffect;
    public EdgeEffectInfo mEdgeLightingInfo;
    public ScreenStateReceiver mScreenStateReceiver = null;
    public final AnonymousClass1 mHandler = new Handler(Looper.getMainLooper()) { // from class: com.android.systemui.edgelighting.routine.EdgelightingRoutineActionHandler.1
        @Override // android.os.Handler
        public final void dispatchMessage(Message message) {
            int i = message.what;
            EdgelightingRoutineActionHandler edgelightingRoutineActionHandler = EdgelightingRoutineActionHandler.this;
            if (i != 0) {
                if (i != 1) {
                    return;
                }
                Slog.d("EdgelightingRoutineActionHandler", "stopEdgeEffect()");
                EdgeLightingDispatcher edgeLightingDispatcher = edgelightingRoutineActionHandler.mController;
                if (edgeLightingDispatcher != null) {
                    edgeLightingDispatcher.stopEdgeEffect();
                }
                ScreenStateReceiver screenStateReceiver = edgelightingRoutineActionHandler.mScreenStateReceiver;
                if (screenStateReceiver != null) {
                    edgelightingRoutineActionHandler.mContext.unregisterReceiver(screenStateReceiver);
                    edgelightingRoutineActionHandler.mScreenStateReceiver = null;
                    return;
                }
                return;
            }
            int i2 = 0;
            EdgeLightingDispatcher edgeLightingDispatcher2 = new EdgeLightingDispatcher(edgelightingRoutineActionHandler.mContext, 2227, false);
            edgelightingRoutineActionHandler.mController = edgeLightingDispatcher2;
            EdgeLightingDialog edgeLightingDialog = edgeLightingDispatcher2.mDialog;
            if (edgeLightingDialog != null ? edgeLightingDialog.isShowing() : edgeLightingDispatcher2.mEffectServiceConrtroller.mStarting) {
                edgelightingRoutineActionHandler.mController.stopEdgeEffect();
                if (edgelightingRoutineActionHandler.mHandler.hasMessages(1)) {
                    edgelightingRoutineActionHandler.mHandler.removeMessages(1);
                }
            }
            edgelightingRoutineActionHandler.mController.setForceSettingValue(edgelightingRoutineActionHandler.mEdgeLightingEffect);
            EdgeLightingDialog edgeLightingDialog2 = edgelightingRoutineActionHandler.mController.mDialog;
            if (edgeLightingDialog2 != null) {
                edgeLightingDialog2.mDozeDraw = true;
            }
            if (!((PowerManager) edgelightingRoutineActionHandler.mContext.getSystemService("power")).isInteractive()) {
                Slog.d("EdgelightingRoutineActionHandler", "registerScreenStateReceiver");
                edgelightingRoutineActionHandler.mScreenStateReceiver = new ScreenStateReceiver(edgelightingRoutineActionHandler, i2);
                edgelightingRoutineActionHandler.mContext.registerReceiver(edgelightingRoutineActionHandler.mScreenStateReceiver, new IntentFilter("android.intent.action.SCREEN_ON"));
            }
            Slog.d("EdgelightingRoutineActionHandler", "startEdgeEffect()");
            edgelightingRoutineActionHandler.mController.startEdgeEffect(edgelightingRoutineActionHandler.mEdgeLightingInfo);
            edgelightingRoutineActionHandler.mHandler.sendEmptyMessageDelayed(1, edgelightingRoutineActionHandler.mEdgeLightingInfo.mLightingDuration);
        }
    };

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class ScreenStateReceiver extends BroadcastReceiver {
        public /* synthetic */ ScreenStateReceiver(EdgelightingRoutineActionHandler edgelightingRoutineActionHandler, int i) {
            this();
        }

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            if (EdgelightingRoutineActionHandler.this.mController != null) {
                Slog.d("EdgelightingRoutineActionHandler", "onReceive");
                EdgelightingRoutineActionHandler.this.mController.refreshBackground();
            }
        }

        private ScreenStateReceiver() {
        }
    }
}
