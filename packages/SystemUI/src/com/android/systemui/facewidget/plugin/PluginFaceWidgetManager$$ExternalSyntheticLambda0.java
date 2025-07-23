package com.android.systemui.facewidget.plugin;

import android.util.Log;
import com.android.systemui.plugins.keyguardstatusview.PluginKeyguardStatusView;
import com.android.systemui.statusbar.phone.nio.KeyguardStatusBarNioLayoutModel;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class PluginFaceWidgetManager$$ExternalSyntheticLambda0 {
    public final /* synthetic */ PluginFaceWidgetManager f$0;

    public /* synthetic */ PluginFaceWidgetManager$$ExternalSyntheticLambda0(PluginFaceWidgetManager pluginFaceWidgetManager) {
        this.f$0 = pluginFaceWidgetManager;
    }

    public final void onNioLayoutUpdated(KeyguardStatusBarNioLayoutModel keyguardStatusBarNioLayoutModel) {
        PluginFaceWidgetManager pluginFaceWidgetManager = this.f$0;
        PluginKeyguardStatusView pluginKeyguardStatusView = pluginFaceWidgetManager.mFaceWidgetPlugin;
        if (pluginKeyguardStatusView != null) {
            int i = keyguardStatusBarNioLayoutModel.paddingLeft;
            int i2 = keyguardStatusBarNioLayoutModel.paddingRight;
            int i3 = keyguardStatusBarNioLayoutModel.totalHeight;
            int i4 = keyguardStatusBarNioLayoutModel.topMargin;
            int i5 = keyguardStatusBarNioLayoutModel.bottomMargin;
            int i6 = keyguardStatusBarNioLayoutModel.containerStartX;
            int i7 = keyguardStatusBarNioLayoutModel.containerEndX;
            int i8 = keyguardStatusBarNioLayoutModel.iconSize;
            float f = keyguardStatusBarNioLayoutModel.iconScaleRatio;
            if (i != pluginFaceWidgetManager.mPreviousPaddingLeft || i2 != pluginFaceWidgetManager.mPreviousPaddingRight || i3 != pluginFaceWidgetManager.mPreviousTotalHeight || i4 != pluginFaceWidgetManager.mPreviousTopMargin || i5 != pluginFaceWidgetManager.mPreviousBottomMargin || i6 != pluginFaceWidgetManager.mPreviousContainerStartX || i7 != pluginFaceWidgetManager.mPreviousContainerEndX || i8 != pluginFaceWidgetManager.mPreviousIconSize || f != pluginFaceWidgetManager.mPreviousIconScaleRatio) {
                try {
                    pluginKeyguardStatusView.onNioLayoutUpdated(i, i2, i3, i4, i5, i6, i7, i8, f);
                } catch (Throwable unused) {
                    Log.e("PluginFaceWidgetManager", "onNioLayoutUpdated: NoSuchMethodError");
                }
                pluginFaceWidgetManager.mPreviousPaddingLeft = i;
                pluginFaceWidgetManager.mPreviousPaddingRight = i2;
                pluginFaceWidgetManager.mPreviousTotalHeight = i3;
                pluginFaceWidgetManager.mPreviousTopMargin = i4;
                pluginFaceWidgetManager.mPreviousBottomMargin = i5;
                pluginFaceWidgetManager.mPreviousContainerStartX = i6;
                pluginFaceWidgetManager.mPreviousContainerEndX = i7;
                pluginFaceWidgetManager.mPreviousIconSize = i8;
                pluginFaceWidgetManager.mPreviousIconScaleRatio = f;
            }
            float f2 = keyguardStatusBarNioLayoutModel.keyguardStatusBarViewAlpha;
            int i9 = keyguardStatusBarNioLayoutModel.keyguardStatusBarViewVisibility;
            if (f2 == pluginFaceWidgetManager.mPreviousAlpha && i9 == pluginFaceWidgetManager.mPreviousVisibility) {
                return;
            }
            pluginFaceWidgetManager.mFaceWidgetPlugin.onNioViewStateUpdated(f2, i9);
            pluginFaceWidgetManager.mPreviousAlpha = f2;
            pluginFaceWidgetManager.mPreviousVisibility = i9;
        }
    }
}
