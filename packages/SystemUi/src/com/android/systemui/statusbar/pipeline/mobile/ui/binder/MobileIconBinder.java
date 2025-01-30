package com.android.systemui.statusbar.pipeline.mobile.ui.binder;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Space;
import com.android.settingslib.graph.SignalDrawable;
import com.android.systemui.R;
import com.android.systemui.lifecycle.RepeatWhenAttachedKt;
import com.android.systemui.statusbar.StatusBarIconView;
import com.android.systemui.statusbar.pipeline.mobile.ui.MobileViewLogger;
import com.android.systemui.statusbar.pipeline.mobile.ui.view.ModernStatusBarMobileView;
import com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.LocationBasedMobileViewModel;
import com.android.systemui.statusbar.pipeline.shared.ui.binder.ModernStatusBarViewBinding;
import com.android.systemui.statusbar.policy.ConfigurationController;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref$BooleanRef;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class MobileIconBinder {
    static {
        new MobileIconBinder();
    }

    private MobileIconBinder() {
    }

    /* JADX WARN: Type inference failed for: r6v3, types: [com.android.systemui.statusbar.pipeline.mobile.ui.binder.MobileIconBinder$bind$3] */
    public static final MobileIconBinder$bind$3 bind(ModernStatusBarMobileView modernStatusBarMobileView, final LocationBasedMobileViewModel locationBasedMobileViewModel, MobileViewLogger mobileViewLogger, ConfigurationController configurationController) {
        ViewGroup viewGroup = (ViewGroup) modernStatusBarMobileView.requireViewById(R.id.mobile_group);
        View requireViewById = modernStatusBarMobileView.requireViewById(R.id.inout_container);
        ImageView imageView = (ImageView) modernStatusBarMobileView.requireViewById(R.id.mobile_in);
        ImageView imageView2 = (ImageView) modernStatusBarMobileView.requireViewById(R.id.mobile_out);
        ImageView imageView3 = (ImageView) modernStatusBarMobileView.requireViewById(R.id.mobile_type);
        ImageView imageView4 = (ImageView) modernStatusBarMobileView.requireViewById(R.id.mobile_signal);
        SignalDrawable signalDrawable = new SignalDrawable(modernStatusBarMobileView.getContext());
        ImageView imageView5 = (ImageView) modernStatusBarMobileView.requireViewById(R.id.mobile_roaming);
        Space space = (Space) modernStatusBarMobileView.requireViewById(R.id.mobile_roaming_space);
        StatusBarIconView statusBarIconView = (StatusBarIconView) modernStatusBarMobileView.requireViewById(R.id.status_bar_dot);
        ImageView imageView6 = (ImageView) modernStatusBarMobileView.requireViewById(R.id.data_activity);
        ImageView imageView7 = (ImageView) modernStatusBarMobileView.requireViewById(R.id.voice_no_service);
        modernStatusBarMobileView.setVisibility(0);
        imageView4.setVisibility(0);
        final StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(2);
        int i = locationBasedMobileViewModel.defaultColor;
        final StateFlowImpl MutableStateFlow2 = StateFlowKt.MutableStateFlow(Integer.valueOf(i));
        final StateFlowImpl MutableStateFlow3 = StateFlowKt.MutableStateFlow(Integer.valueOf(i));
        final Ref$BooleanRef ref$BooleanRef = new Ref$BooleanRef();
        RepeatWhenAttachedKt.repeatWhenAttached(modernStatusBarMobileView, EmptyCoroutineContext.INSTANCE, new MobileIconBinder$bind$1(mobileViewLogger, modernStatusBarMobileView, locationBasedMobileViewModel, ref$BooleanRef, MutableStateFlow, viewGroup, statusBarIconView, imageView4, configurationController, signalDrawable, imageView3, imageView5, space, imageView, imageView2, requireViewById, imageView7, MutableStateFlow2, imageView6, MutableStateFlow3, null));
        if (Intrinsics.areEqual(locationBasedMobileViewModel.locationName, "Home")) {
            RepeatWhenAttachedKt.repeatWhenAttached(modernStatusBarMobileView, EmptyCoroutineContext.INSTANCE, new MobileIconBinder$bind$2(mobileViewLogger, modernStatusBarMobileView, locationBasedMobileViewModel, ref$BooleanRef, null));
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
            public final void onDecorTintChanged(int i2) {
                LocationBasedMobileViewModel.this.getClass();
                ((StateFlowImpl) MutableStateFlow3).setValue(Integer.valueOf(i2));
            }

            @Override // com.android.systemui.statusbar.pipeline.shared.ui.binder.ModernStatusBarViewBinding
            public final void onIconTintChanged(int i2) {
                LocationBasedMobileViewModel.this.getClass();
                ((StateFlowImpl) MutableStateFlow2).setValue(Integer.valueOf(i2));
            }

            @Override // com.android.systemui.statusbar.pipeline.shared.ui.binder.ModernStatusBarViewBinding
            public final void onVisibilityStateChanged(int i2) {
                ((StateFlowImpl) MutableStateFlow).setValue(Integer.valueOf(i2));
            }
        };
    }
}
