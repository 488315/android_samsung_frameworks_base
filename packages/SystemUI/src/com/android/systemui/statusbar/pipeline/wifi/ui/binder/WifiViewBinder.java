package com.android.systemui.statusbar.pipeline.wifi.ui.binder;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.android.systemui.R;
import com.android.systemui.lifecycle.RepeatWhenAttachedKt;
import com.android.systemui.statusbar.StatusBarIconView;
import com.android.systemui.statusbar.phone.StatusBarLocation;
import com.android.systemui.statusbar.pipeline.shared.ui.binder.ModernStatusBarViewBinding;
import com.android.systemui.statusbar.pipeline.wifi.ui.model.WifiIcon;
import com.android.systemui.statusbar.pipeline.wifi.ui.viewmodel.LocationBasedWifiViewModel;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.jvm.internal.Ref$BooleanRef;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class WifiViewBinder {
    static {
        new WifiViewBinder();
    }

    private WifiViewBinder() {
    }

    /* JADX WARN: Type inference failed for: r6v2, types: [com.android.systemui.statusbar.pipeline.wifi.ui.binder.WifiViewBinder$bind$3] */
    public static final WifiViewBinder$bind$3 bind(ViewGroup viewGroup, final LocationBasedWifiViewModel locationBasedWifiViewModel) {
        final Ref$BooleanRef ref$BooleanRef;
        ViewGroup viewGroup2 = (ViewGroup) viewGroup.requireViewById(R.id.wifi_group);
        ImageView imageView = (ImageView) viewGroup.requireViewById(R.id.wifi_signal);
        StatusBarIconView statusBarIconView = (StatusBarIconView) viewGroup.requireViewById(R.id.status_bar_dot);
        ImageView imageView2 = (ImageView) viewGroup.requireViewById(R.id.wifi_in);
        ImageView imageView3 = (ImageView) viewGroup.requireViewById(R.id.wifi_out);
        View requireViewById = viewGroup.requireViewById(R.id.inout_container);
        View requireViewById2 = viewGroup.requireViewById(R.id.wifi_airplane_spacer);
        View requireViewById3 = viewGroup.requireViewById(R.id.wifi_signal_spacer);
        ImageView imageView4 = (ImageView) viewGroup.requireViewById(R.id.wifi_activity);
        viewGroup.setVisibility(0);
        imageView.setVisibility(0);
        final StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(2);
        int i = locationBasedWifiViewModel.defaultColor;
        final StateFlowImpl MutableStateFlow2 = StateFlowKt.MutableStateFlow(Integer.valueOf(i));
        final StateFlowImpl MutableStateFlow3 = StateFlowKt.MutableStateFlow(Integer.valueOf(i));
        Ref$BooleanRef ref$BooleanRef2 = new Ref$BooleanRef();
        RepeatWhenAttachedKt.repeatWhenAttached(viewGroup, EmptyCoroutineContext.INSTANCE, new WifiViewBinder$bind$1(ref$BooleanRef2, MutableStateFlow, viewGroup2, statusBarIconView, locationBasedWifiViewModel, viewGroup, imageView, imageView4, MutableStateFlow2, imageView2, imageView3, MutableStateFlow3, requireViewById, requireViewById2, requireViewById3, null));
        if (locationBasedWifiViewModel.location == StatusBarLocation.HOME) {
            ref$BooleanRef = ref$BooleanRef2;
            RepeatWhenAttachedKt.repeatWhenAttached(viewGroup, EmptyCoroutineContext.INSTANCE, new WifiViewBinder$bind$2(ref$BooleanRef, locationBasedWifiViewModel, null));
        } else {
            ref$BooleanRef = ref$BooleanRef2;
        }
        return new ModernStatusBarViewBinding() { // from class: com.android.systemui.statusbar.pipeline.wifi.ui.binder.WifiViewBinder$bind$3
            @Override // com.android.systemui.statusbar.pipeline.shared.ui.binder.ModernStatusBarViewBinding
            public final boolean getShouldIconBeVisible() {
                return LocationBasedWifiViewModel.this.commonImpl.getWifiIcon().getValue() instanceof WifiIcon.Visible;
            }

            @Override // com.android.systemui.statusbar.pipeline.shared.ui.binder.ModernStatusBarViewBinding
            public final boolean isCollecting() {
                return ref$BooleanRef.element;
            }

            @Override // com.android.systemui.statusbar.pipeline.shared.ui.binder.ModernStatusBarViewBinding
            public final void onDecorTintChanged(int i2) {
                ((StateFlowImpl) MutableStateFlow3).updateState(null, Integer.valueOf(i2));
            }

            @Override // com.android.systemui.statusbar.pipeline.shared.ui.binder.ModernStatusBarViewBinding
            public final void onIconTintChanged(int i2, int i3) {
                ((StateFlowImpl) MutableStateFlow2).updateState(null, Integer.valueOf(i2));
            }

            @Override // com.android.systemui.statusbar.pipeline.shared.ui.binder.ModernStatusBarViewBinding
            public final void onVisibilityStateChanged(int i2) {
                ((StateFlowImpl) MutableStateFlow).updateState(null, Integer.valueOf(i2));
            }
        };
    }
}
