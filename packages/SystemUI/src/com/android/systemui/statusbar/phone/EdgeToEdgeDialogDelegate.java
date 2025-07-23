package com.android.systemui.statusbar.phone;

import android.app.Dialog;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Interpolator;
import android.window.BackEvent;
import com.android.app.animation.Interpolators;
import com.android.systemui.animation.back.BackAnimationSpec;
import com.android.systemui.util.DimensionKt;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class EdgeToEdgeDialogDelegate implements DialogDelegate {
    @Override // com.android.systemui.statusbar.phone.DialogDelegate
    public final BackAnimationSpec getBackAnimationSpec(final SystemUIDialog$$ExternalSyntheticLambda2 systemUIDialog$$ExternalSyntheticLambda2) {
        BackAnimationSpec.Companion companion = BackAnimationSpec.Companion;
        final Interpolator interpolator = Interpolators.BACK_GESTURE;
        return new BackAnimationSpec() { // from class: com.android.systemui.animation.back.BottomsheetBackAnimationSpecKt$createBottomsheetAnimationSpec$1
            @Override // com.android.systemui.animation.back.BackAnimationSpec
            public final void getBackTransformation(BackEvent backEvent, float f, BackTransformation backTransformation) {
                backTransformation.scale = 1.0f - ((1.0f - (1 - (DimensionKt.dpToPx((Number) 48, (DisplayMetrics) Function0.this.invoke()) / r5.widthPixels))) * interpolator.getInterpolation(backEvent.getProgress()));
                backTransformation.scalePivotPosition = ScalePivotPosition.BOTTOM_CENTER;
            }
        };
    }

    @Override // com.android.systemui.statusbar.phone.DialogDelegate
    public final /* bridge */ /* synthetic */ int getHeight(SystemUIDialog systemUIDialog) {
        return -1;
    }

    @Override // com.android.systemui.statusbar.phone.DialogDelegate
    public final /* bridge */ /* synthetic */ int getWidth(SystemUIDialog systemUIDialog) {
        return -1;
    }

    @Override // com.android.systemui.statusbar.phone.DialogDelegate
    public final void onCreate(Dialog dialog, Bundle bundle) {
        Window window = ((SystemUIDialog) dialog).getWindow();
        if (window != null) {
            window.setGravity(81);
            WindowManager.LayoutParams attributes = window.getAttributes();
            attributes.setFitInsetsSides(0);
            window.getAttributes().layoutInDisplayCutoutMode = 3;
            window.setAttributes(attributes);
        }
    }
}
