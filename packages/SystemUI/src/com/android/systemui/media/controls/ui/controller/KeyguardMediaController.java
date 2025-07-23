package com.android.systemui.media.controls.ui.controller;

import android.content.Context;
import android.content.res.Configuration;
import android.util.IndentingPrintWriter;
import android.view.ViewGroup;
import android.view.ViewParent;
import com.android.systemui.Dumpable;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.media.controls.ui.view.MediaHost;
import com.android.systemui.media.controls.ui.view.MediaHost$$ExternalSyntheticLambda0;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.StatusBarState;
import com.android.systemui.statusbar.SysuiStatusBarStateController;
import com.android.systemui.statusbar.notification.stack.MediaContainerView;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.phone.KeyguardBypassController;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.SplitShadeStateController;
import com.android.systemui.statusbar.policy.SplitShadeStateControllerImpl;
import com.android.systemui.util.DumpUtilsKt;
import com.android.systemui.util.animation.UniqueObjectHostView;
import java.io.PrintWriter;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class KeyguardMediaController implements Dumpable {
    public final KeyguardBypassController bypassController;
    public final Context context;
    public final MediaHost mediaHost;
    public MediaContainerView singlePaneContainer;
    public ViewGroup splitShadeContainer;
    public final SplitShadeStateController splitShadeStateController;
    public final SysuiStatusBarStateController statusBarStateController;

    public KeyguardMediaController(MediaHost mediaHost, KeyguardBypassController keyguardBypassController, SysuiStatusBarStateController sysuiStatusBarStateController, Context context, ConfigurationController configurationController, SplitShadeStateController splitShadeStateController, KeyguardMediaControllerLogger keyguardMediaControllerLogger, DumpManager dumpManager) {
        this.mediaHost = mediaHost;
        this.bypassController = keyguardBypassController;
        this.statusBarStateController = sysuiStatusBarStateController;
        this.context = context;
        this.splitShadeStateController = splitShadeStateController;
        dumpManager.registerDumpable(this);
        sysuiStatusBarStateController.addCallback(new StatusBarStateController.StateListener() { // from class: com.android.systemui.media.controls.ui.controller.KeyguardMediaController.1
            @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
            public final void onDozingChanged(boolean z) {
                KeyguardMediaController.this.getClass();
            }

            @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
            public final void onStateChanged(int i) {
                KeyguardMediaController.this.getClass();
            }
        });
        ((ConfigurationControllerImpl) configurationController).addCallback(new ConfigurationController.ConfigurationListener() { // from class: com.android.systemui.media.controls.ui.controller.KeyguardMediaController.2
            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public final void onConfigChanged(Configuration configuration) {
                KeyguardMediaController keyguardMediaController = KeyguardMediaController.this;
                keyguardMediaController.context.getResources();
                ((SplitShadeStateControllerImpl) keyguardMediaController.splitShadeStateController).shouldUseSplitNotificationShade();
            }
        });
        mediaHost.setExpansion(1.0f);
        mediaHost.setShowsOnlyActiveMedia(true);
        MediaHost.MediaHostStateHolder mediaHostStateHolder = mediaHost.state;
        if (!mediaHostStateHolder.falsingProtectionNeeded) {
            mediaHostStateHolder.falsingProtectionNeeded = true;
            MediaHost$$ExternalSyntheticLambda0 mediaHost$$ExternalSyntheticLambda0 = mediaHostStateHolder.changedListener;
            if (mediaHost$$ExternalSyntheticLambda0 != null) {
                mediaHost$$ExternalSyntheticLambda0.invoke();
            }
        }
        mediaHost.init(2);
        context.getResources();
        ((SplitShadeStateControllerImpl) splitShadeStateController).shouldUseSplitNotificationShade();
    }

    public final void attachSinglePaneContainer(MediaContainerView mediaContainerView) {
        boolean z = this.singlePaneContainer == null;
        this.singlePaneContainer = mediaContainerView;
        MediaHost mediaHost = this.mediaHost;
        if (z) {
            mediaHost.visibleChangedListeners.add(new KeyguardMediaController$attachSinglePaneContainer$1(this));
        }
        reattachHostView();
        if (mediaHost.state.visible) {
            UniqueObjectHostView uniqueObjectHostView = mediaHost.hostView;
            if (uniqueObjectHostView == null) {
                uniqueObjectHostView = null;
            }
            ViewGroup.LayoutParams layoutParams = uniqueObjectHostView.getLayoutParams();
            layoutParams.height = -2;
            layoutParams.width = -1;
        }
        MediaContainerView mediaContainerView2 = this.singlePaneContainer;
        if (mediaContainerView2 != null) {
            mediaContainerView2.setImportantForAccessibility(2);
        }
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        IndentingPrintWriter asIndenting = DumpUtilsKt.asIndenting(printWriter);
        asIndenting.println("KeyguardMediaController");
        asIndenting.increaseIndent();
        try {
            DumpUtilsKt.println(asIndenting, "Self", this);
            Boolean bool = Boolean.FALSE;
            DumpUtilsKt.println(asIndenting, "visible", bool);
            DumpUtilsKt.println(asIndenting, "useSplitShade", bool);
            DumpUtilsKt.println(asIndenting, "bypassController.bypassEnabled", Boolean.valueOf(this.bypassController.getBypassEnabled()));
            DumpUtilsKt.println(asIndenting, "singlePaneContainer", this.singlePaneContainer);
            DumpUtilsKt.println(asIndenting, "splitShadeContainer", this.splitShadeContainer);
            DumpUtilsKt.println(asIndenting, "statusBarStateController.state", StatusBarState.toString(this.statusBarStateController.getState()));
        } finally {
            asIndenting.decreaseIndent();
        }
    }

    public final void reattachHostView() {
        ViewGroup viewGroup = this.splitShadeContainer;
        MediaContainerView mediaContainerView = this.singlePaneContainer;
        if (viewGroup != null && viewGroup.getChildCount() == 1) {
            viewGroup.removeAllViews();
        }
        if (mediaContainerView == null || mediaContainerView.getChildCount() != 0) {
            return;
        }
        MediaHost mediaHost = this.mediaHost;
        UniqueObjectHostView uniqueObjectHostView = mediaHost.hostView;
        if (uniqueObjectHostView == null) {
            uniqueObjectHostView = null;
        }
        ViewParent parent = uniqueObjectHostView.getParent();
        if (parent != null) {
            ViewGroup viewGroup2 = parent instanceof ViewGroup ? (ViewGroup) parent : null;
            if (viewGroup2 != null) {
                UniqueObjectHostView uniqueObjectHostView2 = mediaHost.hostView;
                if (uniqueObjectHostView2 == null) {
                    uniqueObjectHostView2 = null;
                }
                viewGroup2.removeView(uniqueObjectHostView2);
            }
        }
        UniqueObjectHostView uniqueObjectHostView3 = mediaHost.hostView;
        mediaContainerView.addView(uniqueObjectHostView3 != null ? uniqueObjectHostView3 : null);
    }

    public static /* synthetic */ void getUseSplitShade$annotations() {
    }
}
