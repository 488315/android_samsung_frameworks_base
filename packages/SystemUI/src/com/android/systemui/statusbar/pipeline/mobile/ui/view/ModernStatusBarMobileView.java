package com.android.systemui.statusbar.pipeline.mobile.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.constraintlayout.widget.ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0;
import com.android.settingslib.flags.Flags;
import com.android.systemui.R;
import com.android.systemui.statusbar.StatusBarIconView;
import com.android.systemui.statusbar.pipeline.mobile.ui.MobileViewLogger;
import com.android.systemui.statusbar.pipeline.mobile.ui.binder.MobileIconBinder;
import com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.LocationBasedMobileViewModel;
import com.android.systemui.statusbar.pipeline.shared.ui.binder.ModernStatusBarViewBinding;
import com.android.systemui.statusbar.pipeline.shared.ui.view.ModernStatusBarView;
import com.android.systemui.statusbar.policy.ConfigurationController;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class ModernStatusBarMobileView extends ModernStatusBarView {
    public static final Companion Companion = new Companion(null);
    public int subId;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
        Flags.newStatusBarIcons();
        modernStatusBarMobileView.subId = locationBasedMobileViewModel.commonImpl.getSubscriptionId();
        modernStatusBarMobileView.initView(str, new Function0() { // from class: com.android.systemui.statusbar.pipeline.mobile.ui.view.ModernStatusBarMobileView$Companion$constructAndBind$1$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return MobileIconBinder.bind$default(ModernStatusBarMobileView.this, locationBasedMobileViewModel, mobileViewLogger, configurationController);
            }
        });
        mobileViewLogger.logNewViewBinding(modernStatusBarMobileView, locationBasedMobileViewModel);
        return modernStatusBarMobileView;
    }

    @Override // android.view.View
    public final String toString() {
        ModernStatusBarViewBinding modernStatusBarViewBinding = this.binding;
        if (modernStatusBarViewBinding == null) {
            modernStatusBarViewBinding = null;
        }
        Boolean valueOf = modernStatusBarViewBinding != null ? Boolean.valueOf(modernStatusBarViewBinding.isCollecting()) : null;
        String slot = getSlot();
        int i = this.subId;
        String visibleStateString = StatusBarIconView.getVisibleStateString(this.iconVisibleState);
        String frameLayout = super.toString();
        StringBuilder m = ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0.m(i, "ModernStatusBarMobileView(slot='", slot, "', subId=", ", isCollecting=");
        m.append(valueOf);
        m.append(", visibleState=");
        m.append(visibleStateString);
        m.append("); viewString=");
        m.append(frameLayout);
        return m.toString();
    }
}
