package com.android.systemui.shade;

import android.view.ViewStub;
import androidx.constraintlayout.motion.widget.MotionLayout;
import com.android.systemui.BasicRune;
import com.android.systemui.R;
import com.android.systemui.biometrics.AuthRippleView;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.statusbar.phone.IndicatorCutoutUtil;
import com.android.systemui.statusbar.phone.IndicatorScaleGardener;
import com.android.systemui.statusbar.policy.NetspeedView;
import com.android.systemui.statusbar.policy.NetspeedViewController;
import kotlin.jvm.internal.DefaultConstructorMarker;

public abstract class ShadeViewProviderModule {
    public static final Companion Companion = new Companion(null);

    public final class Companion {
        private Companion() {
        }

        public static NetspeedViewController provideNetspeedViewController(MotionLayout motionLayout, IndicatorScaleGardener indicatorScaleGardener, IndicatorCutoutUtil indicatorCutoutUtil, UserTracker userTracker, WakefulnessLifecycle wakefulnessLifecycle) {
            if (!BasicRune.STATUS_REAL_TIME_NETWORK_SPEED) {
                return null;
            }
            ViewStub viewStub = (ViewStub) motionLayout.findViewById(R.id.quick_qs_network_speed_viewstub);
            if (viewStub != null) {
                viewStub.inflate();
            }
            return new NetspeedViewController((NetspeedView) motionLayout.findViewById(R.id.networkSpeed), indicatorScaleGardener, indicatorCutoutUtil, userTracker, wakefulnessLifecycle);
        }

        public static AuthRippleView providesAuthRippleView(NotificationShadeWindowView notificationShadeWindowView) {
            return (AuthRippleView) notificationShadeWindowView.requireViewById(R.id.auth_ripple);
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }
}
