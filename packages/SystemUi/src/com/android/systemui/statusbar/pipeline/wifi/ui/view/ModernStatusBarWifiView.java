package com.android.systemui.statusbar.pipeline.wifi.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.fragment.app.FragmentTransaction$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.systemui.statusbar.StatusBarIconView;
import com.android.systemui.statusbar.pipeline.shared.ui.view.ModernStatusBarView;
import com.android.systemui.statusbar.pipeline.wifi.ui.binder.WifiViewBinder;
import com.android.systemui.statusbar.pipeline.wifi.ui.viewmodel.LocationBasedWifiViewModel;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class ModernStatusBarWifiView extends ModernStatusBarView {
    public static final Companion Companion = new Companion(null);

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
        modernStatusBarWifiView.initView(str, new Function0() { // from class: com.android.systemui.statusbar.pipeline.wifi.ui.view.ModernStatusBarWifiView$Companion$constructAndBind$1$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
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
        String str = this.slot;
        if (str == null) {
            str = null;
        }
        boolean isCollecting = m211xb00bd415().isCollecting();
        String visibleStateString = StatusBarIconView.getVisibleStateString(this.iconVisibleState);
        String frameLayout = super.toString();
        StringBuilder sb = new StringBuilder("ModernStatusBarWifiView(slot='");
        sb.append(str);
        sb.append("', isCollecting=");
        sb.append(isCollecting);
        sb.append(", visibleState=");
        return FragmentTransaction$$ExternalSyntheticOutline0.m38m(sb, visibleStateString, "); viewString=", frameLayout);
    }
}
