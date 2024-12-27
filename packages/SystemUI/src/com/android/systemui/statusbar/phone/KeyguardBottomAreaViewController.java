package com.android.systemui.statusbar.phone;

import com.android.systemui.Dumpable;
import com.android.systemui.flags.FeatureFlagsClassic;
import com.android.systemui.flags.Flags;
import com.android.systemui.pluginlock.PluginLockBottomAreaCallback;
import com.android.systemui.statusbar.lockscreen.LockscreenSmartspaceController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.util.ViewController;
import java.io.PrintWriter;
import java.util.ArrayList;
import kotlin.NotImplementedError;

public class KeyguardBottomAreaViewController extends ViewController implements KeyguardStateController.Callback, PluginLockBottomAreaCallback, Dumpable {
    public KeyguardBottomAreaViewController(KeyguardBottomAreaView keyguardBottomAreaView, LockscreenSmartspaceController lockscreenSmartspaceController, FeatureFlagsClassic featureFlagsClassic) {
        super(keyguardBottomAreaView);
        Flags flags = Flags.INSTANCE;
        keyguardBottomAreaView.isLockscreenLandscapeEnabled = false;
    }

    @Override // com.android.systemui.Dumpable
    public void dump(PrintWriter printWriter, String[] strArr) {
        throw new NotImplementedError("An operation is not implemented: Not yet implemented");
    }

    public ArrayList getShortCutAreaViews() {
        return null;
    }

    public final KeyguardBottomAreaView getView() {
        return (KeyguardBottomAreaView) this.mView;
    }

    @Override // com.android.systemui.util.ViewController
    public void onViewAttached() {
        com.android.systemui.Flags.FEATURE_FLAGS.getClass();
    }

    public void cancelIndicationAreaAnim() {
    }

    public void onDensityOrFontScaleChanged() {
    }

    @Override // com.android.systemui.util.ViewController
    public void onViewDetached() {
    }

    public void updateIndicationPosition() {
    }

    public void launchAffordance(boolean z) {
    }

    public void setAffordanceAlpha(float f) {
    }

    public void setDozing(boolean z) {
    }

    public void setNowBarVisibility(boolean z) {
    }

    public void setUserSetupComplete$1(boolean z) {
    }

    public void showShortcutAnimation(boolean z) {
    }
}
