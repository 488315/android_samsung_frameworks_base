package com.android.systemui.statusbar.pipeline.mobile.ui.binder;

import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Space;
import com.android.settingslib.graph.SignalDrawable;
import com.android.systemui.R;
import com.android.systemui.lifecycle.RepeatWhenAttachedKt;
import com.android.systemui.statusbar.StatusBarIconView;
import com.android.systemui.statusbar.phone.StatusBarLocation;
import com.android.systemui.statusbar.pipeline.mobile.ui.MobileViewLogger;
import com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.LocationBasedMobileViewModel;
import com.android.systemui.statusbar.pipeline.shared.ui.binder.ModernStatusBarViewBinding;
import com.android.systemui.statusbar.policy.ConfigurationController;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.jvm.internal.Ref$BooleanRef;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

public final class MobileIconBinder {
    static {
        new MobileIconBinder();
    }

    private MobileIconBinder() {
    }

    public static MobileIconBinder$bind$3 bind$default(ViewGroup viewGroup, final LocationBasedMobileViewModel locationBasedMobileViewModel, MobileViewLogger mobileViewLogger, ConfigurationController configurationController) {
        ViewGroup viewGroup2 = (ViewGroup) viewGroup.requireViewById(R.id.mobile_group);
        View requireViewById = viewGroup.requireViewById(R.id.inout_container);
        ImageView imageView = (ImageView) viewGroup.requireViewById(R.id.mobile_in);
        ImageView imageView2 = (ImageView) viewGroup.requireViewById(R.id.mobile_out);
        ImageView imageView3 = (ImageView) viewGroup.requireViewById(R.id.mobile_type);
        FrameLayout frameLayout = (FrameLayout) viewGroup.requireViewById(R.id.mobile_type_container);
        ImageView imageView4 = (ImageView) viewGroup.requireViewById(R.id.mobile_signal);
        SignalDrawable signalDrawable = new SignalDrawable(viewGroup.getContext());
        ImageView imageView5 = (ImageView) viewGroup.requireViewById(R.id.mobile_roaming);
        Space space = (Space) viewGroup.requireViewById(R.id.mobile_roaming_space);
        StatusBarIconView statusBarIconView = (StatusBarIconView) viewGroup.requireViewById(R.id.status_bar_dot);
        ImageView imageView6 = (ImageView) viewGroup.requireViewById(R.id.data_activity);
        ImageView imageView7 = (ImageView) viewGroup.requireViewById(R.id.voice_no_service);
        viewGroup.setVisibility(((Boolean) locationBasedMobileViewModel.isVisible().getValue()).booleanValue() ? 0 : 8);
        imageView4.setVisibility(0);
        final StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(2);
        final StateFlowImpl MutableStateFlow2 = StateFlowKt.MutableStateFlow(new Colors(-301989889, -16777216));
        final StateFlowImpl MutableStateFlow3 = StateFlowKt.MutableStateFlow(Integer.valueOf(locationBasedMobileViewModel.defaultColor));
        final Ref$BooleanRef ref$BooleanRef = new Ref$BooleanRef();
        RepeatWhenAttachedKt.repeatWhenAttached(viewGroup, EmptyCoroutineContext.INSTANCE, new MobileIconBinder$bind$1(locationBasedMobileViewModel, viewGroup, mobileViewLogger, ref$BooleanRef, MutableStateFlow, viewGroup2, statusBarIconView, imageView4, configurationController, signalDrawable, frameLayout, imageView3, MutableStateFlow2, imageView5, space, imageView, imageView2, requireViewById, imageView7, imageView6, MutableStateFlow3, null));
        if (locationBasedMobileViewModel.location == StatusBarLocation.HOME) {
            RepeatWhenAttachedKt.repeatWhenAttached(viewGroup, EmptyCoroutineContext.INSTANCE, new MobileIconBinder$bind$2(mobileViewLogger, viewGroup, locationBasedMobileViewModel, ref$BooleanRef, null));
        }
        return new ModernStatusBarViewBinding() { // from class: com.android.systemui.statusbar.pipeline.mobile.ui.binder.MobileIconBinder$bind$3
            @Override // com.android.systemui.statusbar.pipeline.shared.ui.binder.ModernStatusBarViewBinding
            public final boolean getShouldIconBeVisible() {
                return ((Boolean) LocationBasedMobileViewModel.this.isVisible().getValue()).booleanValue();
            }

            @Override // com.android.systemui.statusbar.pipeline.shared.ui.binder.ModernStatusBarViewBinding
            public final boolean isCollecting() {
                return ref$BooleanRef.element;
            }

            @Override // com.android.systemui.statusbar.pipeline.shared.ui.binder.ModernStatusBarViewBinding
            public final void onDecorTintChanged(int i) {
                ((StateFlowImpl) MutableStateFlow3).updateState(null, Integer.valueOf(i));
            }

            @Override // com.android.systemui.statusbar.pipeline.shared.ui.binder.ModernStatusBarViewBinding
            public final void onIconTintChanged(int i, int i2) {
                ((StateFlowImpl) MutableStateFlow2).updateState(null, new Colors(i, i2));
            }

            @Override // com.android.systemui.statusbar.pipeline.shared.ui.binder.ModernStatusBarViewBinding
            public final void onVisibilityStateChanged(int i) {
                ((StateFlowImpl) MutableStateFlow).updateState(null, Integer.valueOf(i));
            }
        };
    }
}
