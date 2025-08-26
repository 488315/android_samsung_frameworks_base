package com.android.wm.shell.windowdecor.education;

import android.content.Context;
import android.view.View;
import android.window.DisplayAreaInfo;
import android.window.WindowContainerTransaction;
import androidx.dynamicanimation.animation.DynamicAnimation;
import com.android.wm.shell.common.DisplayChangeController;
import com.android.wm.shell.common.DisplayController;
import com.android.wm.shell.shared.animation.PhysicsAnimator;
import com.android.wm.shell.windowdecor.additionalviewcontainer.AdditionalSystemViewContainer;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Unit;

/* loaded from: classes3.dex */
public final class DesktopWindowingEducationTooltipController implements DisplayChangeController.OnDisplayChangingListener {
    public final AdditionalSystemViewContainer.Factory additionalSystemViewContainerFactory;
    public PhysicsAnimator animator;
    public final Context context;
    public final DisplayController displayController;
    public AdditionalSystemViewContainer popupWindow;
    public final Lazy springConfig$delegate = LazyKt__LazyJVMKt.lazy(new DesktopWindowingEducationTooltipController$$ExternalSyntheticLambda0());
    public View tooltipView;

    public DesktopWindowingEducationTooltipController(Context context, AdditionalSystemViewContainer.Factory factory, DisplayController displayController) {
        this.context = context;
        this.additionalSystemViewContainerFactory = factory;
        this.displayController = displayController;
    }

    @Override // com.android.wm.shell.common.DisplayChangeController.OnDisplayChangingListener
    public final void onDisplayChange(int i, int i2, int i3, DisplayAreaInfo displayAreaInfo, WindowContainerTransaction windowContainerTransaction) {
        if (i2 % 2 == i3 % 2) {
            return;
        }
        PhysicsAnimator physicsAnimator = this.animator;
        if (physicsAnimator != null) {
            physicsAnimator.spring(DynamicAnimation.ALPHA, 0.0f);
            physicsAnimator.spring(DynamicAnimation.SCALE_X, 0.0f);
            physicsAnimator.spring(DynamicAnimation.SCALE_Y, 0.0f);
            physicsAnimator.start();
        }
        this.animator = null;
        AdditionalSystemViewContainer additionalSystemViewContainer = this.popupWindow;
        if (additionalSystemViewContainer != null) {
            additionalSystemViewContainer.releaseView();
        }
        this.popupWindow = null;
        this.displayController.mChangeController.mDisplayChangeListener.remove(this);
        Unit unit = Unit.INSTANCE;
    }
}
