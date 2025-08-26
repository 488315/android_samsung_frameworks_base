package com.android.systemui.statusbar.pipeline.wifi.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.datastore.preferences.core.MutablePreferences$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.systemui.statusbar.StatusBarIconView;
import com.android.systemui.statusbar.pipeline.shared.ui.binder.ModernStatusBarViewBinding;
import com.android.systemui.statusbar.pipeline.shared.ui.view.ModernStatusBarView;
import com.android.systemui.statusbar.pipeline.wifi.ui.binder.WifiViewBinder;
import com.android.systemui.statusbar.pipeline.wifi.ui.viewmodel.LocationBasedWifiViewModel;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes3.dex */
public final class ModernStatusBarWifiView extends ModernStatusBarView {
    public static final Companion Companion = new Companion(null);

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public ModernStatusBarWifiView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public static final ModernStatusBarWifiView constructAndBind(Context context, String str, LocationBasedWifiViewModel locationBasedWifiViewModel) {
        Companion.getClass();
        ModernStatusBarWifiView modernStatusBarWifiView = (ModernStatusBarWifiView) LayoutInflater.from(context).inflate(R.layout.new_status_bar_wifi_group, (ViewGroup) null);
        modernStatusBarWifiView.slot = str;
        Context context2 = ((FrameLayout) modernStatusBarWifiView).mContext;
        String str2 = modernStatusBarWifiView.slot;
        if (str2 == null) {
            str2 = null;
        }
        StatusBarIconView statusBarIconView = new StatusBarIconView(context2, str2, null);
        statusBarIconView.setId(R.id.status_bar_dot);
        statusBarIconView.setVisibleState(1);
        int dimensionPixelSize = ((FrameLayout) modernStatusBarWifiView).mContext.getResources().getDimensionPixelSize(R.dimen.status_bar_icon_size_sp);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(dimensionPixelSize, dimensionPixelSize);
        layoutParams.gravity = 8388627;
        modernStatusBarWifiView.addView(statusBarIconView, layoutParams);
        modernStatusBarWifiView.binding = WifiViewBinder.bind(modernStatusBarWifiView, locationBasedWifiViewModel);
        return modernStatusBarWifiView;
    }

    @Override // android.view.View
    public final String toString() {
        ModernStatusBarViewBinding modernStatusBarViewBinding = this.binding;
        if (modernStatusBarViewBinding == null) {
            modernStatusBarViewBinding = null;
        }
        Boolean boolValueOf = modernStatusBarViewBinding != null ? Boolean.valueOf(modernStatusBarViewBinding.isCollecting()) : null;
        String str = this.slot;
        String str2 = str != null ? str : null;
        String visibleStateString = StatusBarIconView.getVisibleStateString(this.iconVisibleState);
        String string = super.toString();
        StringBuilder sb = new StringBuilder("ModernStatusBarWifiView(slot='");
        sb.append(str2);
        sb.append("', isCollecting=");
        sb.append(boolValueOf);
        sb.append(", visibleState=");
        return MutablePreferences$$ExternalSyntheticOutline0.m(sb, visibleStateString, "); viewString=", string);
    }
}
