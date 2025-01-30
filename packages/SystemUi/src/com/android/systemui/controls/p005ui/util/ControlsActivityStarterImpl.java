package com.android.systemui.controls.p005ui.util;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.android.systemui.animation.ActivityLaunchAnimator;
import com.android.systemui.controls.dagger.ControlsComponent;
import com.android.systemui.controls.p005ui.ControlsUiController;
import com.android.systemui.controls.p005ui.CustomControlsUiController;
import com.android.systemui.controls.p005ui.CustomControlsUiControllerImpl;
import com.android.systemui.plugins.ActivityStarter;
import java.util.Optional;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class ControlsActivityStarterImpl implements ControlsActivityStarter {
    public final ActivityStarter activityStarter;
    public final ControlsComponent controlsComponent;

    public ControlsActivityStarterImpl(ActivityStarter activityStarter, ControlsComponent controlsComponent, ControlsUtil controlsUtil) {
        this.activityStarter = activityStarter;
        this.controlsComponent = controlsComponent;
    }

    public final void startActivity(Context context, Class cls) {
        Intent intent = new Intent(context, (Class<?>) cls);
        intent.addFlags(335544320);
        this.activityStarter.startActivity(intent, true);
    }

    public final void startCustomControlsActivity(Context context) {
        ControlsComponent controlsComponent = this.controlsComponent;
        if (!controlsComponent.getCustomControlsUiController().isPresent()) {
            Log.w("ControlsActivityStarterImpl", "feature:android.software.controls is disabled");
            return;
        }
        Intent intent = new Intent();
        intent.setComponent(new ComponentName(context, (Class<?>) ((CustomControlsUiControllerImpl) ((ControlsUiController) (controlsComponent.featureEnabled ? Optional.of(controlsComponent.lazyControlsUiController.get()) : Optional.empty()).get())).resolveActivity()));
        intent.addFlags(335544320);
        this.activityStarter.startActivity(intent, true, (ActivityLaunchAnimator.Controller) null, ((CustomControlsUiControllerImpl) ((CustomControlsUiController) controlsComponent.getCustomControlsUiController().get())).isShowOverLockscreenWhenLocked);
    }
}
