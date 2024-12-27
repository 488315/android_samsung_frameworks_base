package com.android.systemui.media.controls.ui.controller;

import android.content.Context;
import android.content.res.Configuration;
import android.util.IndentingPrintWriter;
import com.android.systemui.Dumpable;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.media.controls.ui.view.MediaHost;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.StatusBarState;
import com.android.systemui.statusbar.StatusBarStateControllerImpl;
import com.android.systemui.statusbar.SysuiStatusBarStateController;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.phone.KeyguardBypassController;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.SplitShadeStateController;
import com.android.systemui.statusbar.policy.SplitShadeStateControllerImpl;
import com.android.systemui.util.DumpUtilsKt;
import java.io.PrintWriter;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class KeyguardMediaController implements Dumpable {
    public final KeyguardBypassController bypassController;
    public final Context context;
    public boolean isDozeWakeUpAnimationWaiting;
    public final int lastUsedStatusBarState = -1;
    public final MediaHost mediaHost;
    public final SplitShadeStateController splitShadeStateController;
    public final SysuiStatusBarStateController statusBarStateController;
    public boolean useSplitShade;

    public KeyguardMediaController(MediaHost mediaHost, KeyguardBypassController keyguardBypassController, SysuiStatusBarStateController sysuiStatusBarStateController, Context context, ConfigurationController configurationController, SplitShadeStateController splitShadeStateController, KeyguardMediaControllerLogger keyguardMediaControllerLogger, DumpManager dumpManager) {
        this.bypassController = keyguardBypassController;
        this.statusBarStateController = sysuiStatusBarStateController;
        this.context = context;
        this.splitShadeStateController = splitShadeStateController;
        dumpManager.registerDumpable(this);
        ((StatusBarStateControllerImpl) sysuiStatusBarStateController).addCallback(new StatusBarStateController.StateListener() { // from class: com.android.systemui.media.controls.ui.controller.KeyguardMediaController.1
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
                if (keyguardMediaController.useSplitShade) {
                    keyguardMediaController.useSplitShade = false;
                }
            }
        });
        mediaHost.setExpansion(1.0f);
        Boolean bool = Boolean.TRUE;
        MediaHost.MediaHostStateHolder mediaHostStateHolder = mediaHost.state;
        if (!bool.equals(Boolean.valueOf(mediaHostStateHolder.showsOnlyActiveMedia))) {
            mediaHostStateHolder.showsOnlyActiveMedia = true;
            Function0 function0 = mediaHostStateHolder.changedListener;
            if (function0 != null) {
                function0.invoke();
            }
        }
        if (!mediaHostStateHolder.falsingProtectionNeeded) {
            mediaHostStateHolder.falsingProtectionNeeded = true;
            Function0 function02 = mediaHostStateHolder.changedListener;
            if (function02 != null) {
                function02.invoke();
            }
        }
        mediaHost.init(2);
        context.getResources();
        ((SplitShadeStateControllerImpl) splitShadeStateController).shouldUseSplitNotificationShade();
        if (this.useSplitShade) {
            this.useSplitShade = false;
        }
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        IndentingPrintWriter asIndenting = DumpUtilsKt.asIndenting(printWriter);
        asIndenting.println("KeyguardMediaController");
        asIndenting.increaseIndent();
        try {
            DumpUtilsKt.println(asIndenting, "Self", this);
            DumpUtilsKt.println(asIndenting, "visible", Boolean.FALSE);
            DumpUtilsKt.println(asIndenting, "useSplitShade", Boolean.valueOf(this.useSplitShade));
            DumpUtilsKt.println(asIndenting, "bypassController.bypassEnabled", Boolean.valueOf(this.bypassController.getBypassEnabled()));
            DumpUtilsKt.println(asIndenting, "isDozeWakeUpAnimationWaiting", Boolean.valueOf(this.isDozeWakeUpAnimationWaiting));
            DumpUtilsKt.println(asIndenting, "singlePaneContainer", null);
            DumpUtilsKt.println(asIndenting, "splitShadeContainer", null);
            int i = this.lastUsedStatusBarState;
            if (i != -1) {
                DumpUtilsKt.println(asIndenting, "lastUsedStatusBarState", StatusBarState.toString(i));
            }
            DumpUtilsKt.println(asIndenting, "statusBarStateController.state", StatusBarState.toString(((StatusBarStateControllerImpl) this.statusBarStateController).mState));
            asIndenting.decreaseIndent();
        } catch (Throwable th) {
            asIndenting.decreaseIndent();
            throw th;
        }
    }

    public static /* synthetic */ void getUseSplitShade$annotations() {
    }
}
