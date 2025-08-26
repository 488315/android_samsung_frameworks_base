package com.android.systemui.facewidget.plugin;

import com.android.systemui.keyguard.KeyguardFoldController;
import com.android.systemui.plugins.keyguardstatusview.PluginKeyguardStatusView;

/* loaded from: classes2.dex */
public final /* synthetic */ class PluginFaceWidgetManager$$ExternalSyntheticLambda4 implements KeyguardFoldController.StateListener {
    public final /* synthetic */ PluginFaceWidgetManager f$0;

    @Override // com.android.systemui.keyguard.KeyguardFoldController.StateListener
    public void onFoldStateChanged(boolean z) {
        PluginKeyguardStatusView pluginKeyguardStatusView = this.f$0.mFaceWidgetPlugin;
        if (pluginKeyguardStatusView != null) {
            pluginKeyguardStatusView.onFolderStateChanged(z);
        }
    }
}
