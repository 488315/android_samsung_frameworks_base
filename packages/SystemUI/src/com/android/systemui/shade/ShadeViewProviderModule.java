package com.android.systemui.shade;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
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

/* loaded from: classes3.dex */
public abstract class ShadeViewProviderModule {
    public static final Companion Companion = new Companion(null);

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static NetspeedViewController provideNetspeedViewController(MotionLayout motionLayout, Context context, IndicatorScaleGardener indicatorScaleGardener, IndicatorCutoutUtil indicatorCutoutUtil, UserTracker userTracker, WakefulnessLifecycle wakefulnessLifecycle) {
            if (!BasicRune.STATUS_REAL_TIME_NETWORK_SPEED) {
                return null;
            }
            ViewGroup viewGroup = (ViewGroup) motionLayout.findViewById(R.id.hover_system_icons_container);
            NetspeedView netspeedView = (NetspeedView) LayoutInflater.from(context).inflate(R.layout.samsung_status_bar_network_speed_view, (ViewGroup) null);
            if (BasicRune.STATUS_LAYOUT_SIDELING_CUTOUT && BasicRune.STATUS_POP_OVER_PANEL_BAR) {
                netspeedView.mInStatusBar = true;
            }
            viewGroup.addView(netspeedView, 0);
            return new NetspeedViewController((NetspeedView) motionLayout.findViewById(R.id.networkSpeed), indicatorScaleGardener, indicatorCutoutUtil, userTracker, wakefulnessLifecycle);
        }

        public static AuthRippleView providesAuthRippleView(NotificationShadeWindowView notificationShadeWindowView) {
            return (AuthRippleView) notificationShadeWindowView.requireViewById(R.id.auth_ripple);
        }

        private Companion() {
        }
    }
}
