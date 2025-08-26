package com.android.systemui.keyguard.ui.view.layout.sections;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import com.android.keyguard.KeyguardSliceView;
import com.android.keyguard.KeyguardSliceViewController;
import com.android.systemui.R;
import com.android.systemui.customization.R$id;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.keyguard.shared.model.KeyguardSection;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.settings.DisplayTracker;
import com.android.systemui.statusbar.lockscreen.LockscreenSmartspaceController;
import com.android.systemui.statusbar.policy.ConfigurationController;

/* loaded from: classes2.dex */
public final class KeyguardSliceViewSection extends KeyguardSection {
    public final ActivityStarter activityStarter;
    public final Handler bgHandler;
    public final ConfigurationController configurationController;
    public final DisplayTracker displayTracker;
    public final DumpManager dumpManager;
    public final Handler handler;
    public final LayoutInflater layoutInflater;
    public KeyguardSliceView sliceView;
    public final LockscreenSmartspaceController smartspaceController;

    public KeyguardSliceViewSection(LockscreenSmartspaceController lockscreenSmartspaceController, LayoutInflater layoutInflater, Handler handler, Handler handler2, ActivityStarter activityStarter, ConfigurationController configurationController, DumpManager dumpManager, DisplayTracker displayTracker) {
        this.smartspaceController = lockscreenSmartspaceController;
        this.layoutInflater = layoutInflater;
        this.handler = handler;
        this.bgHandler = handler2;
        this.activityStarter = activityStarter;
        this.configurationController = configurationController;
        this.dumpManager = dumpManager;
        this.displayTracker = displayTracker;
    }

    @Override // com.android.systemui.keyguard.shared.model.KeyguardSection
    public final void addViews(ConstraintLayout constraintLayout) {
        if (this.smartspaceController.isEnabled) {
            return;
        }
        KeyguardSliceView keyguardSliceView = (KeyguardSliceView) this.layoutInflater.inflate(R.layout.keyguard_slice_view, (ViewGroup) null, false);
        this.sliceView = keyguardSliceView;
        constraintLayout.addView(keyguardSliceView != null ? keyguardSliceView : null);
    }

    @Override // com.android.systemui.keyguard.shared.model.KeyguardSection
    public final void applyConstraints(ConstraintSet constraintSet) {
        if (this.smartspaceController.isEnabled) {
            return;
        }
        constraintSet.connect(R.id.keyguard_slice_view, 6, 0, 6);
        constraintSet.connect(R.id.keyguard_slice_view, 7, 0, 7);
        constraintSet.constrainHeight(R.id.keyguard_slice_view, -2);
        constraintSet.connect(R.id.keyguard_slice_view, 3, R$id.lockscreen_clock_view, 4);
        constraintSet.createBarrier(R.id.smart_space_barrier_bottom, 3, 0, R.id.keyguard_slice_view);
    }

    @Override // com.android.systemui.keyguard.shared.model.KeyguardSection
    public final void bindData(ConstraintLayout constraintLayout) {
        if (this.smartspaceController.isEnabled) {
            return;
        }
        KeyguardSliceView keyguardSliceView = this.sliceView;
        if (keyguardSliceView == null) {
            keyguardSliceView = null;
        }
        DumpManager dumpManager = this.dumpManager;
        DisplayTracker displayTracker = this.displayTracker;
        new KeyguardSliceViewController(this.handler, this.bgHandler, keyguardSliceView, this.activityStarter, this.configurationController, dumpManager, displayTracker).init();
    }

    @Override // com.android.systemui.keyguard.shared.model.KeyguardSection
    public final void removeViews(ConstraintLayout constraintLayout) {
        if (this.smartspaceController.isEnabled) {
            return;
        }
        ExtensionsKt.removeView(constraintLayout, R.id.keyguard_slice_view);
    }
}
