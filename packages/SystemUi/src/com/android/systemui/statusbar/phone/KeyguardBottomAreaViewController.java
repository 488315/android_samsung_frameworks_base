package com.android.systemui.statusbar.phone;

import android.content.ComponentName;
import android.os.Bundle;
import com.android.systemui.Dumpable;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.util.ViewController;
import java.io.PrintWriter;
import kotlin.NotImplementedError;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class KeyguardBottomAreaViewController extends ViewController implements KeyguardStateController.Callback, Dumpable {
    public KeyguardBottomAreaViewController(KeyguardBottomAreaView keyguardBottomAreaView) {
        super(keyguardBottomAreaView);
    }

    @Override // com.android.systemui.Dumpable
    public void dump(PrintWriter printWriter, String[] strArr) {
        throw new NotImplementedError("An operation is not implemented: Not yet implemented");
    }

    public /* bridge */ /* synthetic */ boolean isNoUnlockNeed(String str) {
        return false;
    }

    public /* bridge */ /* synthetic */ Bundle onUiInfoRequested(boolean z) {
        return null;
    }

    public void launchAffordance(boolean z) {
    }

    public /* bridge */ /* synthetic */ void launchApp(ComponentName componentName) {
    }

    public /* bridge */ /* synthetic */ void onViewModeChanged(int i) {
    }

    public void setAffordanceAlpha(float f) {
    }

    public void setDozing(boolean z) {
    }

    public void setUserSetupComplete(boolean z) {
    }

    public void cancelIndicationAreaAnim() {
    }

    public void onDensityOrFontScaleChanged() {
    }

    @Override // com.android.systemui.util.ViewController
    public void onViewAttached() {
    }

    @Override // com.android.systemui.util.ViewController
    public void onViewDetached() {
    }

    public /* bridge */ /* synthetic */ void updateBottomView() {
    }
}
