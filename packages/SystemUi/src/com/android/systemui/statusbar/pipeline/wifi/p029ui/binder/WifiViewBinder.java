package com.android.systemui.statusbar.pipeline.wifi.p029ui.binder;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.android.systemui.R;
import com.android.systemui.lifecycle.RepeatWhenAttachedKt;
import com.android.systemui.statusbar.StatusBarIconView;
import com.android.systemui.statusbar.phone.StatusBarLocation;
import com.android.systemui.statusbar.pipeline.shared.p028ui.binder.ModernStatusBarViewBinding;
import com.android.systemui.statusbar.pipeline.wifi.p029ui.model.WifiIcon;
import com.android.systemui.statusbar.pipeline.wifi.p029ui.view.ModernStatusBarWifiView;
import com.android.systemui.statusbar.pipeline.wifi.p029ui.viewmodel.LocationBasedWifiViewModel;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.jvm.internal.Ref$BooleanRef;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class WifiViewBinder {
    static {
        new WifiViewBinder();
    }

    private WifiViewBinder() {
    }

    /* JADX WARN: Type inference failed for: r6v1, types: [com.android.systemui.statusbar.pipeline.wifi.ui.binder.WifiViewBinder$bind$3] */
    public static final WifiViewBinder$bind$3 bind(ModernStatusBarWifiView modernStatusBarWifiView, final LocationBasedWifiViewModel locationBasedWifiViewModel) {
        final Ref$BooleanRef ref$BooleanRef;
        ViewGroup viewGroup = (ViewGroup) modernStatusBarWifiView.requireViewById(R.id.wifi_group);
        ImageView imageView = (ImageView) modernStatusBarWifiView.requireViewById(R.id.wifi_signal);
        StatusBarIconView statusBarIconView = (StatusBarIconView) modernStatusBarWifiView.requireViewById(R.id.status_bar_dot);
        ImageView imageView2 = (ImageView) modernStatusBarWifiView.requireViewById(R.id.wifi_in);
        ImageView imageView3 = (ImageView) modernStatusBarWifiView.requireViewById(R.id.wifi_out);
        View requireViewById = modernStatusBarWifiView.requireViewById(R.id.inout_container);
        View requireViewById2 = modernStatusBarWifiView.requireViewById(R.id.wifi_airplane_spacer);
        View requireViewById3 = modernStatusBarWifiView.requireViewById(R.id.wifi_signal_spacer);
        ImageView imageView4 = (ImageView) modernStatusBarWifiView.requireViewById(R.id.wifi_activity);
        modernStatusBarWifiView.setVisibility(0);
        imageView.setVisibility(0);
        final StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(2);
        int i = locationBasedWifiViewModel.defaultColor;
        final StateFlowImpl MutableStateFlow2 = StateFlowKt.MutableStateFlow(Integer.valueOf(i));
        final StateFlowImpl MutableStateFlow3 = StateFlowKt.MutableStateFlow(Integer.valueOf(i));
        Ref$BooleanRef ref$BooleanRef2 = new Ref$BooleanRef();
        RepeatWhenAttachedKt.repeatWhenAttached(modernStatusBarWifiView, EmptyCoroutineContext.INSTANCE, new WifiViewBinder$bind$1(ref$BooleanRef2, MutableStateFlow, viewGroup, statusBarIconView, locationBasedWifiViewModel, modernStatusBarWifiView, imageView, imageView4, MutableStateFlow2, imageView2, imageView3, MutableStateFlow3, requireViewById, requireViewById2, requireViewById3, null));
        if (locationBasedWifiViewModel.location == StatusBarLocation.HOME) {
            ref$BooleanRef = ref$BooleanRef2;
            RepeatWhenAttachedKt.repeatWhenAttached(modernStatusBarWifiView, EmptyCoroutineContext.INSTANCE, new WifiViewBinder$bind$2(ref$BooleanRef, locationBasedWifiViewModel, null));
        } else {
            ref$BooleanRef = ref$BooleanRef2;
        }
        return new ModernStatusBarViewBinding() { // from class: com.android.systemui.statusbar.pipeline.wifi.ui.binder.WifiViewBinder$bind$3
            @Override // com.android.systemui.statusbar.pipeline.shared.p028ui.binder.ModernStatusBarViewBinding
            public final boolean getShouldIconBeVisible() {
                return LocationBasedWifiViewModel.this.getWifiIcon().getValue() instanceof WifiIcon.Visible;
            }

            @Override // com.android.systemui.statusbar.pipeline.shared.p028ui.binder.ModernStatusBarViewBinding
            public final boolean isCollecting() {
                return ref$BooleanRef.element;
            }

            @Override // com.android.systemui.statusbar.pipeline.shared.p028ui.binder.ModernStatusBarViewBinding
            public final void onDecorTintChanged(int i2) {
                LocationBasedWifiViewModel.this.getClass();
                ((StateFlowImpl) MutableStateFlow3).setValue(Integer.valueOf(i2));
            }

            @Override // com.android.systemui.statusbar.pipeline.shared.p028ui.binder.ModernStatusBarViewBinding
            public final void onIconTintChanged(int i2) {
                LocationBasedWifiViewModel.this.getClass();
                ((StateFlowImpl) MutableStateFlow2).setValue(Integer.valueOf(i2));
            }

            @Override // com.android.systemui.statusbar.pipeline.shared.p028ui.binder.ModernStatusBarViewBinding
            public final void onVisibilityStateChanged(int i2) {
                ((StateFlowImpl) MutableStateFlow).setValue(Integer.valueOf(i2));
            }
        };
    }
}
