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

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class ModernStatusBarMobileView extends ModernStatusBarView {
    public static final Companion Companion = new Companion(null);
    public int subId;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
                return MobileIconBinder.bind$default(ModernStatusBarMobileView.this, locationBasedMobileViewModel, mobileViewLogger, configurationController);
            }
        });
        mobileViewLogger.getClass();
        String name = locationBasedMobileViewModel.location.name();
        mobileViewLogger.getClass();
        LogLevel logLevel = LogLevel.INFO;
        MobileViewLogger$$ExternalSyntheticLambda1 mobileViewLogger$$ExternalSyntheticLambda1 = new MobileViewLogger$$ExternalSyntheticLambda1(1);
        LogBuffer logBuffer = mobileViewLogger.buffer;
        LogMessage obtain = logBuffer.obtain("MobileViewLogger", logLevel, mobileViewLogger$$ExternalSyntheticLambda1, null);
        MobileViewLogger.Companion.getClass();
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.str1 = MobileViewLogger.Companion.getIdForLogging(modernStatusBarMobileView);
        logMessageImpl.str2 = MobileViewLogger.Companion.getIdForLogging(locationBasedMobileViewModel);
        logMessageImpl.str3 = name;
        logBuffer.commit(obtain);
        return modernStatusBarMobileView;
    }

    @Override // android.view.View
    public final String toString() {
        ModernStatusBarViewBinding modernStatusBarViewBinding = this.binding;
        if (modernStatusBarViewBinding == null) {
            modernStatusBarViewBinding = null;
        }
        Boolean valueOf = modernStatusBarViewBinding != null ? Boolean.valueOf(modernStatusBarViewBinding.isCollecting()) : null;
        String str = this.slot;
        String str2 = str != null ? str : null;
        int i = this.subId;
        String visibleStateString = StatusBarIconView.getVisibleStateString(this.iconVisibleState);
        String frameLayout = super.toString();
        StringBuilder m888m = ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0.m888m(i, "ModernStatusBarMobileView(slot='", str2, "', subId=", ", isCollecting=");
        m888m.append(valueOf);
        m888m.append(", visibleState=");
        m888m.append(visibleStateString);
        m888m.append("); viewString=");
        m888m.append(frameLayout);
        return m888m.toString();
    }
}
