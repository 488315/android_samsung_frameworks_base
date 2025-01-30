package com.android.systemui.keyguard.ui.binder;

import android.app.ActivityManager;
import com.android.keyguard.KeyguardSecSecurityContainerController;
import com.android.keyguard.KeyguardSecurityContainer;
import com.android.systemui.LsRune;
import com.android.systemui.R;
import com.android.systemui.keyguard.data.BouncerViewDelegate;
import java.io.File;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class KeyguardBouncerViewBinder$bind$delegate$1 implements BouncerViewDelegate {
    public final /* synthetic */ KeyguardSecSecurityContainerController $securityContainerController;

    public KeyguardBouncerViewBinder$bind$delegate$1(KeyguardSecSecurityContainerController keyguardSecSecurityContainerController) {
        this.$securityContainerController = keyguardSecSecurityContainerController;
    }

    public final boolean shouldDismissOnMenuPressed() {
        KeyguardSecSecurityContainerController keyguardSecSecurityContainerController = this.$securityContainerController;
        keyguardSecSecurityContainerController.getClass();
        if (LsRune.SECURITY_SIM_PERM_DISABLED && keyguardSecSecurityContainerController.mUpdateMonitor.isSimDisabledPermanently()) {
            return false;
        }
        return !((KeyguardSecurityContainer) keyguardSecSecurityContainerController.mView).getResources().getBoolean(R.bool.config_disableMenuKeyInLockScreen) || ActivityManager.isRunningInTestHarness() || new File("/data/local/enable_menu_key").exists();
    }
}
