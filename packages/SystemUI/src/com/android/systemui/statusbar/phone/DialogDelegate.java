package com.android.systemui.statusbar.phone;

import android.app.Dialog;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.animation.Interpolator;
import com.android.app.animation.Interpolators;
import com.android.systemui.animation.back.BackAnimationSpec;
import com.android.systemui.animation.back.BackAnimationSpecKt$createFloatingSurfaceAnimationSpec$1;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public interface DialogDelegate {
    default BackAnimationSpec getBackAnimationSpec(SystemUIDialog$$ExternalSyntheticLambda1 systemUIDialog$$ExternalSyntheticLambda1) {
        BackAnimationSpec.Companion companion = BackAnimationSpec.Companion;
        Interpolator interpolator = Interpolators.BACK_GESTURE;
        return new BackAnimationSpecKt$createFloatingSurfaceAnimationSpec$1(systemUIDialog$$ExternalSyntheticLambda1, 8.0f, 8.0f, 0.9f, interpolator, Interpolators.LINEAR, interpolator);
    }

    default int getHeight(Dialog dialog) {
        int i = SystemUIDialog.$r8$clinit;
        return -2;
    }

    default int getWidth(Dialog dialog) {
        return SystemUIDialog.getDefaultDialogWidth(dialog);
    }

    default void beforeCreate(Dialog dialog) {
    }

    default void onStart(Dialog dialog) {
    }

    default void onStop(Dialog dialog) {
    }

    default void onConfigurationChanged(Dialog dialog, Configuration configuration) {
    }

    default void onCreate(Dialog dialog, Bundle bundle) {
    }

    default void onWindowFocusChanged(Dialog dialog, boolean z) {
    }
}
