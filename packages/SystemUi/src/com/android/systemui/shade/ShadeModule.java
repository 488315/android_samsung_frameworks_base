package com.android.systemui.shade;

import android.view.ViewStub;
import androidx.constraintlayout.motion.widget.MotionLayout;
import com.android.systemui.BasicRune;
import com.android.systemui.R;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.statusbar.phone.IndicatorCutoutUtil;
import com.android.systemui.statusbar.phone.IndicatorScaleGardener;
import com.android.systemui.statusbar.policy.NetspeedView;
import com.android.systemui.statusbar.policy.NetspeedViewController;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public abstract class ShadeModule {
    public static final Companion Companion = new Companion(null);

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static NetspeedViewController provideNetspeedViewController(MotionLayout motionLayout, IndicatorScaleGardener indicatorScaleGardener, IndicatorCutoutUtil indicatorCutoutUtil, UserTracker userTracker) {
            if (!BasicRune.STATUS_REAL_TIME_NETWORK_SPEED) {
                return null;
            }
            ViewStub viewStub = (ViewStub) motionLayout.findViewById(R.id.quick_qs_network_speed_viewstub);
            if (viewStub != null) {
                viewStub.inflate();
            }
            return new NetspeedViewController((NetspeedView) motionLayout.findViewById(R.id.networkSpeed), indicatorScaleGardener, indicatorCutoutUtil, userTracker);
        }
    }
}
