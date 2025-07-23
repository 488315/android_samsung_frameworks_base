package com.android.systemui.plugins.clocks;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import androidx.constraintlayout.widget.ConstraintSet;
import com.android.internal.policy.SystemBarUtils;
import java.util.Collections;
import java.util.List;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class DefaultClockFaceLayout implements ClockFaceLayout {
    private final View view;
    private final List<View> views;
    public static final Companion Companion = new Companion(null);
    public static final int $stable = 8;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final ConstraintSet applyDefaultPreviewConstraints(ClockPreviewConfig clockPreviewConfig, ConstraintSet constraintSet) {
            Context context = clockPreviewConfig.getContext();
            ContextExt contextExt = ContextExt.INSTANCE;
            int id = contextExt.getId(context, "lockscreen_clock_view_large");
            constraintSet.constrainWidth(id, -2);
            constraintSet.constrainHeight(id, -2);
            constraintSet.constrainMaxHeight(id, 0);
            constraintSet.connect(id, 3, 0, 3, contextExt.getDimen(context, "enhanced_smartspace_height") + contextExt.getDimen(context, "date_weather_view_height") + contextExt.getDimen(context, "keyguard_smartspace_top_offset") + contextExt.getDimen(context, "small_clock_padding_top") + SystemBarUtils.getStatusBarHeight(context));
            constraintSet.connect(id, 6, 0, 6);
            constraintSet.connect(id, 7, 0, 7);
            Float udfpsTop = clockPreviewConfig.getUdfpsTop();
            if (udfpsTop != null) {
                constraintSet.connect(id, 4, 0, 4, (int) (context.getResources().getDisplayMetrics().heightPixels - udfpsTop.floatValue()));
            } else {
                Integer lockId = clockPreviewConfig.getLockId();
                if (lockId != null) {
                    constraintSet.connect(id, 4, lockId.intValue(), 3);
                } else {
                    constraintSet.connect(id, 4, 0, 4, (((int) ((DisplayMetrics.DENSITY_DEVICE_STABLE / 160.0f) * 36)) * 2) + contextExt.getDimen(context, "lock_icon_margin_bottom"));
                }
            }
            int id2 = contextExt.getId(context, "lockscreen_clock_view");
            constraintSet.constrainWidth(id2, -2);
            constraintSet.constrainHeight(id2, contextExt.getDimen(context, "small_clock_height"));
            constraintSet.connect(id2, 6, 0, 6, contextExt.getDimen(context, "status_view_margin_horizontal") + contextExt.getDimen(context, "clock_padding_start"));
            constraintSet.connect(id2, 3, 0, 3, ClockPreviewConfig.getSmallClockTopPadding$default(clockPreviewConfig, 0, 1, null));
            return constraintSet;
        }

        private Companion() {
        }
    }

    public DefaultClockFaceLayout(View view) {
        this.view = view;
        this.views = Collections.singletonList(view);
    }

    @Override // com.android.systemui.plugins.clocks.ClockFaceLayout
    public ConstraintSet applyConstraints(ConstraintSet constraintSet) {
        if (getViews().size() == 1) {
            return constraintSet;
        }
        throw new IllegalArgumentException("Should have only one container view when using DefaultClockFaceLayout");
    }

    @Override // com.android.systemui.plugins.clocks.ClockFaceLayout
    public ConstraintSet applyPreviewConstraints(ClockPreviewConfig clockPreviewConfig, ConstraintSet constraintSet) {
        return Companion.applyDefaultPreviewConstraints(clockPreviewConfig, constraintSet);
    }

    public final View getView() {
        return this.view;
    }

    @Override // com.android.systemui.plugins.clocks.ClockFaceLayout
    public List<View> getViews() {
        return this.views;
    }

    @Override // com.android.systemui.plugins.clocks.ClockFaceLayout
    public void applyAodBurnIn(AodClockBurnInModel aodClockBurnInModel) {
    }
}
