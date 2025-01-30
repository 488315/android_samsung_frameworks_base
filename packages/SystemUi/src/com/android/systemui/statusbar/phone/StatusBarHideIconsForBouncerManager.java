package com.android.systemui.statusbar.phone;

import com.android.keyguard.ActiveUnlockConfig$$ExternalSyntheticOutline0;
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import com.android.systemui.Dumpable;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.shade.ShadeExpansionStateManager;
import com.android.systemui.shade.ShadeFullExpansionListener;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.window.StatusBarWindowStateController;
import com.android.systemui.statusbar.window.StatusBarWindowStateListener;
import com.android.systemui.util.concurrency.DelayableExecutor;
import java.io.PrintWriter;
import java.util.HashSet;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class StatusBarHideIconsForBouncerManager implements Dumpable {
    public boolean bouncerShowing;
    public boolean bouncerWasShowingWhenHidden;
    public final CommandQueue commandQueue;
    public int displayId;
    public boolean hideIconsForBouncer;
    public boolean isOccluded;
    public final DelayableExecutor mainExecutor;
    public boolean panelExpanded;
    public boolean statusBarWindowHidden;
    public boolean topAppHidesStatusBar;
    public boolean wereIconsJustHidden;

    public StatusBarHideIconsForBouncerManager(CommandQueue commandQueue, DelayableExecutor delayableExecutor, StatusBarWindowStateController statusBarWindowStateController, ShadeExpansionStateManager shadeExpansionStateManager, DumpManager dumpManager) {
        this.commandQueue = commandQueue;
        this.mainExecutor = delayableExecutor;
        dumpManager.registerDumpable(this);
        ((HashSet) statusBarWindowStateController.listeners).add(new StatusBarWindowStateListener() { // from class: com.android.systemui.statusbar.phone.StatusBarHideIconsForBouncerManager.1
            @Override // com.android.systemui.statusbar.window.StatusBarWindowStateListener
            public final void onStatusBarWindowStateChanged(int i) {
                StatusBarHideIconsForBouncerManager statusBarHideIconsForBouncerManager = StatusBarHideIconsForBouncerManager.this;
                statusBarHideIconsForBouncerManager.getClass();
                statusBarHideIconsForBouncerManager.statusBarWindowHidden = i == 2;
                statusBarHideIconsForBouncerManager.updateHideIconsForBouncer(false);
            }
        });
        shadeExpansionStateManager.addFullExpansionListener(new ShadeFullExpansionListener() { // from class: com.android.systemui.statusbar.phone.StatusBarHideIconsForBouncerManager.2
            @Override // com.android.systemui.shade.ShadeFullExpansionListener
            public final void onShadeExpansionFullyChanged(boolean z) {
                StatusBarHideIconsForBouncerManager statusBarHideIconsForBouncerManager = StatusBarHideIconsForBouncerManager.this;
                if (statusBarHideIconsForBouncerManager.panelExpanded != z) {
                    statusBarHideIconsForBouncerManager.panelExpanded = z;
                    statusBarHideIconsForBouncerManager.updateHideIconsForBouncer(false);
                }
            }
        });
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.println("---- State variables set externally ----");
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m53m("panelExpanded=", this.panelExpanded, printWriter);
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m53m("isOccluded=", this.isOccluded, printWriter);
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m53m("bouncerShowing=", this.bouncerShowing, printWriter);
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m53m("topAppHideStatusBar=", this.topAppHidesStatusBar, printWriter);
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m53m("statusBarWindowHidden=", this.statusBarWindowHidden, printWriter);
        printWriter.println("displayId=" + this.displayId);
        printWriter.println("---- State variables calculated internally ----");
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m53m("hideIconsForBouncer=", this.hideIconsForBouncer, printWriter);
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m53m("bouncerWasShowingWhenHidden=", this.bouncerWasShowingWhenHidden, printWriter);
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m53m("wereIconsJustHidden=", this.wereIconsJustHidden, printWriter);
    }

    public final boolean getShouldHideStatusBarIconsForBouncer() {
        boolean z = this.hideIconsForBouncer;
        if (z || this.wereIconsJustHidden) {
            EmergencyButtonController$$ExternalSyntheticOutline0.m59m("hideIconsForBouncer=", z, " wereIconsJustHidden=", this.wereIconsJustHidden, "StatusBarHideIconsForBouncerManager");
        }
        return this.hideIconsForBouncer || this.wereIconsJustHidden;
    }

    public final void updateHideIconsForBouncer(boolean z) {
        boolean z2 = (this.topAppHidesStatusBar && this.isOccluded && (this.statusBarWindowHidden || this.bouncerShowing)) || (!this.panelExpanded && !this.isOccluded && this.bouncerShowing);
        if (this.hideIconsForBouncer != z2) {
            this.hideIconsForBouncer = z2;
            if (z2 || !this.bouncerWasShowingWhenHidden) {
                this.commandQueue.recomputeDisableFlags(this.displayId, z);
            } else {
                this.wereIconsJustHidden = true;
                this.mainExecutor.executeDelayed(500L, new Runnable() { // from class: com.android.systemui.statusbar.phone.StatusBarHideIconsForBouncerManager$updateHideIconsForBouncer$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        StatusBarHideIconsForBouncerManager statusBarHideIconsForBouncerManager = StatusBarHideIconsForBouncerManager.this;
                        statusBarHideIconsForBouncerManager.wereIconsJustHidden = false;
                        statusBarHideIconsForBouncerManager.commandQueue.recomputeDisableFlags(statusBarHideIconsForBouncerManager.displayId, true);
                    }
                });
            }
        }
        if (z2) {
            this.bouncerWasShowingWhenHidden = this.bouncerShowing;
        }
    }
}
