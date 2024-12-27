package com.android.systemui.statusbar.pipeline.wifi.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.datastore.preferences.core.MutablePreferences$toString$1$$ExternalSyntheticOutline0;
import com.android.settingslib.flags.Flags;
import com.android.systemui.R;
import com.android.systemui.statusbar.StatusBarIconView;
import com.android.systemui.statusbar.pipeline.shared.ui.binder.ModernStatusBarViewBinding;
import com.android.systemui.statusbar.pipeline.shared.ui.view.ModernStatusBarView;
import com.android.systemui.statusbar.pipeline.wifi.ui.binder.WifiViewBinder;
import com.android.systemui.statusbar.pipeline.wifi.ui.viewmodel.LocationBasedWifiViewModel;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;

public final class ModernStatusBarWifiView extends ModernStatusBarView {
    public static final Companion Companion = new Companion(null);

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public ModernStatusBarWifiView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public static final ModernStatusBarWifiView constructAndBind(Context context, String str, final LocationBasedWifiViewModel locationBasedWifiViewModel) {
        Companion.getClass();
        final ModernStatusBarWifiView modernStatusBarWifiView = (ModernStatusBarWifiView) LayoutInflater.from(context).inflate(R.layout.new_status_bar_wifi_group, (ViewGroup) null);
        Flags.newStatusBarIcons();
        modernStatusBarWifiView.initView(str, new Function0() { // from class: com.android.systemui.statusbar.pipeline.wifi.ui.view.ModernStatusBarWifiView$Companion$constructAndBind$1$1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return WifiViewBinder.bind(ModernStatusBarWifiView.this, locationBasedWifiViewModel);
            }
        });
        return modernStatusBarWifiView;
    }

    @Override // android.view.View
    public final String toString() {
        ModernStatusBarViewBinding modernStatusBarViewBinding = this.binding;
        if (modernStatusBarViewBinding == null) {
            modernStatusBarViewBinding = null;
        }
        Boolean valueOf = modernStatusBarViewBinding != null ? Boolean.valueOf(modernStatusBarViewBinding.isCollecting()) : null;
        String slot = getSlot();
        String visibleStateString = StatusBarIconView.getVisibleStateString(this.iconVisibleState);
        String frameLayout = super.toString();
        StringBuilder sb = new StringBuilder("ModernStatusBarWifiView(slot='");
        sb.append(slot);
        sb.append("', isCollecting=");
        sb.append(valueOf);
        sb.append(", visibleState=");
        return MutablePreferences$toString$1$$ExternalSyntheticOutline0.m(sb, visibleStateString, "); viewString=", frameLayout);
    }
}
