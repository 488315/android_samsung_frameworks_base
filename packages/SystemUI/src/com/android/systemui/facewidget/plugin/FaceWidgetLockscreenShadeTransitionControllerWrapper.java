package com.android.systemui.facewidget.plugin;

import com.android.systemui.plugins.keyguardstatusview.PluginLockscreenShadeTransitionController;
import com.android.systemui.plugins.keyguardstatusview.PluginLockscreenShadeTransitionControllerCallback;
import com.android.systemui.statusbar.LockscreenShadeTransitionController;
import java.util.HashMap;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class FaceWidgetLockscreenShadeTransitionControllerWrapper implements PluginLockscreenShadeTransitionController {
    public final LockscreenShadeTransitionController mController;
    public final HashMap mHashMap = new HashMap();

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class CallbackWrapper implements LockscreenShadeTransitionController.Callback {
        public final PluginLockscreenShadeTransitionControllerCallback mCallback;

        public CallbackWrapper(PluginLockscreenShadeTransitionControllerCallback pluginLockscreenShadeTransitionControllerCallback) {
            this.mCallback = pluginLockscreenShadeTransitionControllerCallback;
        }

        @Override // com.android.systemui.statusbar.LockscreenShadeTransitionController.Callback
        public final void onExpansionFinished() {
            this.mCallback.onExpansionFinished();
        }

        @Override // com.android.systemui.statusbar.LockscreenShadeTransitionController.Callback
        public final void onExpansionReset() {
            this.mCallback.onExpansionReset();
        }

        @Override // com.android.systemui.statusbar.LockscreenShadeTransitionController.Callback
        public final void onExpansionStarted() {
            this.mCallback.onExpansionStarted();
        }

        @Override // com.android.systemui.statusbar.LockscreenShadeTransitionController.Callback
        public final void onPulseExpansionFinished() {
            this.mCallback.onPulseExpansionFinished();
        }

        @Override // com.android.systemui.statusbar.LockscreenShadeTransitionController.Callback
        public final void setTransitionToFullShadeAmount(float f, boolean z, long j) {
            this.mCallback.setTransitionToFullShadeAmount(f, z, j);
        }

        @Override // com.android.systemui.statusbar.LockscreenShadeTransitionController.Callback
        public final void setTransitionToFullShadeAmount(float f) {
            this.mCallback.setTransitionToFullShadeAmount(f);
        }
    }

    public FaceWidgetLockscreenShadeTransitionControllerWrapper(LockscreenShadeTransitionController lockscreenShadeTransitionController) {
        this.mController = lockscreenShadeTransitionController;
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginLockscreenShadeTransitionController
    public final void addCallback(PluginLockscreenShadeTransitionControllerCallback pluginLockscreenShadeTransitionControllerCallback) {
        CallbackWrapper callbackWrapper = new CallbackWrapper(pluginLockscreenShadeTransitionControllerCallback);
        this.mController.addCallback(callbackWrapper);
        this.mHashMap.put(pluginLockscreenShadeTransitionControllerCallback, callbackWrapper);
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginLockscreenShadeTransitionController
    public final void removeCallback(PluginLockscreenShadeTransitionControllerCallback pluginLockscreenShadeTransitionControllerCallback) {
        CallbackWrapper callbackWrapper = (CallbackWrapper) this.mHashMap.get(pluginLockscreenShadeTransitionControllerCallback);
        if (callbackWrapper != null) {
            this.mController.removeCallback(callbackWrapper);
        }
    }
}
