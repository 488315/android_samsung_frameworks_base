package com.android.systemui.facewidget.plugin;

import com.android.systemui.keyguard.KeyguardFoldController;
import com.android.systemui.plugins.keyguardstatusview.PluginKeyguardStatusView;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
