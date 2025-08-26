package com.android.systemui.statusbar.pipeline.mobile.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.constraintlayout.widget.ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.statusbar.StatusBarIconView;
import com.android.systemui.statusbar.pipeline.mobile.ui.MobileViewLogger;
import com.android.systemui.statusbar.pipeline.mobile.ui.MobileViewLogger$$ExternalSyntheticLambda1;
import com.android.systemui.statusbar.pipeline.mobile.ui.binder.MobileIconBinder;
import com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.LocationBasedMobileViewModel;
import com.android.systemui.statusbar.pipeline.shared.ui.binder.ModernStatusBarViewBinding;
import com.android.systemui.statusbar.pipeline.shared.ui.view.ModernStatusBarView;
import com.android.systemui.statusbar.policy.ConfigurationController;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes3.dex */
public final class ModernStatusBarMobileView extends ModernStatusBarView {
    public static final Companion Companion = new Companion(null);
    public int subId;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public ModernStatusBarMobileView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.subId = -1;
    }

    public static final ModernStatusBarMobileView constructAndBind(Context context, final MobileViewLogger mobileViewLogger, String str, final LocationBasedMobileViewModel locationBasedMobileViewModel, final ConfigurationController configurationController) {
        Companion.getClass();
        final ModernStatusBarMobileView modernStatusBarMobileView = (ModernStatusBarMobileView) LayoutInflater.from(context).inflate(R.layout.status_bar_mobile_signal_group_new, (ViewGroup) null);
        modernStatusBarMobileView.subId = locationBasedMobileViewModel.commonImpl.getSubscriptionId();
        modernStatusBarMobileView.initView(str, new Function0() { // from class: com.android.systemui.statusbar.pipeline.mobile.ui.view.ModernStatusBarMobileView$Companion$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return MobileIconBinder.bind$default(this.f$0, locationBasedMobileViewModel, mobileViewLogger, configurationController);
            }
        });
        mobileViewLogger.getClass();
        String strName = locationBasedMobileViewModel.location.name();
        mobileViewLogger.getClass();
        LogLevel logLevel = LogLevel.INFO;
        MobileViewLogger$$ExternalSyntheticLambda1 mobileViewLogger$$ExternalSyntheticLambda1 = new MobileViewLogger$$ExternalSyntheticLambda1(1);
        LogBuffer logBuffer = mobileViewLogger.buffer;
        LogMessage logMessageObtain = logBuffer.obtain("MobileViewLogger", logLevel, mobileViewLogger$$ExternalSyntheticLambda1, null);
        MobileViewLogger.Companion.getClass();
        LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
        logMessageImpl.str1 = MobileViewLogger.Companion.getIdForLogging(modernStatusBarMobileView);
        logMessageImpl.str2 = MobileViewLogger.Companion.getIdForLogging(locationBasedMobileViewModel);
        logMessageImpl.str3 = strName;
        logBuffer.commit(logMessageObtain);
        return modernStatusBarMobileView;
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
        int i = this.subId;
        String visibleStateString = StatusBarIconView.getVisibleStateString(this.iconVisibleState);
        String string = super.toString();
        StringBuilder sbM890m = ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0.m890m(i, "ModernStatusBarMobileView(slot='", str2, "', subId=", ", isCollecting=");
        sbM890m.append(boolValueOf);
        sbM890m.append(", visibleState=");
        sbM890m.append(visibleStateString);
        sbM890m.append("); viewString=");
        sbM890m.append(string);
        return sbM890m.toString();
    }
}
