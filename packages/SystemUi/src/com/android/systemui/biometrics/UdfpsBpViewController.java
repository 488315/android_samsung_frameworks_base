package com.android.systemui.biometrics;

import com.android.systemui.dump.DumpManager;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.shade.ShadeExpansionStateManager;
import com.android.systemui.statusbar.phone.SystemUIDialogManager;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class UdfpsBpViewController extends UdfpsAnimationViewController {
    public final String tag;

    public UdfpsBpViewController(UdfpsBpView udfpsBpView, StatusBarStateController statusBarStateController, ShadeExpansionStateManager shadeExpansionStateManager, SystemUIDialogManager systemUIDialogManager, DumpManager dumpManager) {
        super(udfpsBpView, statusBarStateController, shadeExpansionStateManager, systemUIDialogManager, dumpManager);
        this.tag = "UdfpsBpViewController";
    }

    @Override // com.android.systemui.biometrics.UdfpsAnimationViewController
    public final String getTag() {
        return this.tag;
    }
}
