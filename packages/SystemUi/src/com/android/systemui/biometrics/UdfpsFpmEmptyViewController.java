package com.android.systemui.biometrics;

import com.android.systemui.dump.DumpManager;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.shade.ShadeExpansionStateManager;
import com.android.systemui.statusbar.phone.SystemUIDialogManager;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class UdfpsFpmEmptyViewController extends UdfpsAnimationViewController {
    public final String tag;

    public UdfpsFpmEmptyViewController(UdfpsFpmEmptyView udfpsFpmEmptyView, StatusBarStateController statusBarStateController, ShadeExpansionStateManager shadeExpansionStateManager, SystemUIDialogManager systemUIDialogManager, DumpManager dumpManager) {
        super(udfpsFpmEmptyView, statusBarStateController, shadeExpansionStateManager, systemUIDialogManager, dumpManager);
        this.tag = "UdfpsFpmOtherViewController";
    }

    @Override // com.android.systemui.biometrics.UdfpsAnimationViewController
    public final String getTag() {
        return this.tag;
    }
}
