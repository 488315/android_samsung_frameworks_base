package com.android.systemui.statusbar.pipeline.mobile.p026ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.android.systemui.AbstractC0950x8906c950;
import com.android.systemui.R;
import com.android.systemui.statusbar.StatusBarIconView;
import com.android.systemui.statusbar.pipeline.mobile.p026ui.MobileViewLogger;
import com.android.systemui.statusbar.pipeline.mobile.p026ui.binder.MobileIconBinder;
import com.android.systemui.statusbar.pipeline.mobile.p026ui.viewmodel.LocationBasedMobileViewModel;
import com.android.systemui.statusbar.pipeline.shared.p028ui.view.ModernStatusBarView;
import com.android.systemui.statusbar.policy.ConfigurationController;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class ModernStatusBarMobileView extends ModernStatusBarView {
    public static final Companion Companion = new Companion(null);
    public int subId;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public ModernStatusBarMobileView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.subId = -1;
    }

    public static final ModernStatusBarMobileView constructAndBind(Context context, final MobileViewLogger mobileViewLogger, String str, final LocationBasedMobileViewModel locationBasedMobileViewModel, final ConfigurationController configurationController) {
        Companion.getClass();
        final ModernStatusBarMobileView modernStatusBarMobileView = (ModernStatusBarMobileView) LayoutInflater.from(context).inflate(R.layout.status_bar_mobile_signal_group_new, (ViewGroup) null);
        modernStatusBarMobileView.subId = locationBasedMobileViewModel.getSubscriptionId();
        modernStatusBarMobileView.initView(str, new Function0() { // from class: com.android.systemui.statusbar.pipeline.mobile.ui.view.ModernStatusBarMobileView$Companion$constructAndBind$1$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return MobileIconBinder.bind(ModernStatusBarMobileView.this, locationBasedMobileViewModel, mobileViewLogger, configurationController);
            }
        });
        mobileViewLogger.logNewViewBinding(modernStatusBarMobileView, locationBasedMobileViewModel);
        return modernStatusBarMobileView;
    }

    @Override // android.view.View
    public final String toString() {
        String str = this.slot;
        if (str == null) {
            str = null;
        }
        int i = this.subId;
        boolean isCollecting = m211xb00bd415().isCollecting();
        String visibleStateString = StatusBarIconView.getVisibleStateString(this.iconVisibleState);
        String frameLayout = super.toString();
        StringBuilder m92m = AbstractC0950x8906c950.m92m("ModernStatusBarMobileView(slot='", str, "', subId=", i, ", isCollecting=");
        m92m.append(isCollecting);
        m92m.append(", visibleState=");
        m92m.append(visibleStateString);
        m92m.append("); viewString=");
        m92m.append(frameLayout);
        return m92m.toString();
    }
}
