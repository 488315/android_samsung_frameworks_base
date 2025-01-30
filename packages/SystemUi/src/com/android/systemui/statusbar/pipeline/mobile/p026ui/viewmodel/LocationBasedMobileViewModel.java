package com.android.systemui.statusbar.pipeline.mobile.p026ui.viewmodel;

import android.view.View;
import com.android.systemui.flags.Flags;
import com.android.systemui.statusbar.phone.DoubleShadowStatusBarIconDrawable;
import com.android.systemui.statusbar.phone.StatusBarLocation;
import com.android.systemui.statusbar.pipeline.StatusBarPipelineFlags;
import com.android.systemui.statusbar.pipeline.mobile.p026ui.VerboseMobileViewLogger;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.StateFlow;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public abstract class LocationBasedMobileViewModel implements MobileIconViewModelCommon {
    public static final Companion Companion = new Companion(null);
    public final MobileIconViewModelCommon commonImpl;
    public final int defaultColor;
    public final String locationName;
    public final VerboseMobileViewLogger verboseLogger;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public abstract /* synthetic */ class WhenMappings {
            public static final /* synthetic */ int[] $EnumSwitchMapping$0;

            static {
                int[] iArr = new int[StatusBarLocation.values().length];
                try {
                    iArr[StatusBarLocation.HOME.ordinal()] = 1;
                } catch (NoSuchFieldError unused) {
                }
                try {
                    iArr[StatusBarLocation.KEYGUARD.ordinal()] = 2;
                } catch (NoSuchFieldError unused2) {
                }
                try {
                    iArr[StatusBarLocation.QS.ordinal()] = 3;
                } catch (NoSuchFieldError unused3) {
                }
                try {
                    iArr[StatusBarLocation.SUB_SCREEN_QUICK_PANEL.ordinal()] = 4;
                } catch (NoSuchFieldError unused4) {
                }
                $EnumSwitchMapping$0 = iArr;
            }
        }

        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public LocationBasedMobileViewModel(MobileIconViewModelCommon mobileIconViewModelCommon, StatusBarPipelineFlags statusBarPipelineFlags, int i, String str, VerboseMobileViewLogger verboseMobileViewLogger) {
        this.commonImpl = mobileIconViewModelCommon;
        this.locationName = str;
        this.verboseLogger = verboseMobileViewLogger;
        statusBarPipelineFlags.getClass();
        Flags.INSTANCE.getClass();
        statusBarPipelineFlags.featureFlags.getClass();
        this.defaultColor = -1;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.p026ui.viewmodel.MobileIconViewModelCommon
    public final Flow getActivityContainerVisible() {
        return this.commonImpl.getActivityContainerVisible();
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.p026ui.viewmodel.MobileIconViewModelCommon
    public final Flow getActivityIcon() {
        return this.commonImpl.getActivityIcon();
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.p026ui.viewmodel.MobileIconViewModelCommon
    public final Flow getActivityInVisible() {
        return this.commonImpl.getActivityInVisible();
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.p026ui.viewmodel.MobileIconViewModelCommon
    public final Flow getActivityOutVisible() {
        return this.commonImpl.getActivityOutVisible();
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.p026ui.viewmodel.MobileIconViewModelCommon
    public final Flow getAnyChanges() {
        return this.commonImpl.getAnyChanges();
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.p026ui.viewmodel.MobileIconViewModelCommon
    public final Flow getContentDescription() {
        return this.commonImpl.getContentDescription();
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.p026ui.viewmodel.MobileIconViewModelCommon
    public final Flow getDexStatusBarIcon() {
        return this.commonImpl.getDexStatusBarIcon();
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.p026ui.viewmodel.MobileIconViewModelCommon
    public final Flow getIcon() {
        return this.commonImpl.getIcon();
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.p026ui.viewmodel.MobileIconViewModelCommon
    public final Flow getNetworkTypeIcon() {
        return this.commonImpl.getNetworkTypeIcon();
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.p026ui.viewmodel.MobileIconViewModelCommon
    public final Flow getRoaming() {
        return this.commonImpl.getRoaming();
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.p026ui.viewmodel.MobileIconViewModelCommon
    public final Flow getRoamingIcon() {
        return this.commonImpl.getRoamingIcon();
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.p026ui.viewmodel.MobileIconViewModelCommon
    public final DoubleShadowStatusBarIconDrawable getShadowDrawable(View view, int i) {
        return this.commonImpl.getShadowDrawable(view, i);
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.p026ui.viewmodel.MobileIconViewModelCommon
    public final int getSubscriptionId() {
        return this.commonImpl.getSubscriptionId();
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.p026ui.viewmodel.MobileIconViewModelCommon
    public final StateFlow getUpdateDeXStatusBarIconModel() {
        return this.commonImpl.getUpdateDeXStatusBarIconModel();
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.p026ui.viewmodel.MobileIconViewModelCommon
    public final StateFlow getVoiceNoServiceIcon() {
        return this.commonImpl.getVoiceNoServiceIcon();
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.p026ui.viewmodel.MobileIconViewModelCommon
    public final StateFlow isVisible() {
        return this.commonImpl.isVisible();
    }
}
