package com.android.systemui.statusbar.pipeline.wifi.p029ui.viewmodel;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import com.android.systemui.flags.Flags;
import com.android.systemui.statusbar.phone.DoubleShadowStatusBarIconDrawable;
import com.android.systemui.statusbar.phone.StatusBarLocation;
import com.android.systemui.statusbar.pipeline.StatusBarPipelineFlags;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.math.MathKt__MathJVMKt;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.StateFlow;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public abstract class LocationBasedWifiViewModel implements WifiViewModelCommon {
    public static final Companion Companion = new Companion(null);
    public final WifiViewModelCommon commonImpl;
    public final int defaultColor;
    public final StatusBarLocation location;

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

    public LocationBasedWifiViewModel(WifiViewModelCommon wifiViewModelCommon, StatusBarPipelineFlags statusBarPipelineFlags, int i, StatusBarLocation statusBarLocation) {
        this.commonImpl = wifiViewModelCommon;
        this.location = statusBarLocation;
        statusBarPipelineFlags.getClass();
        Flags.INSTANCE.getClass();
        statusBarPipelineFlags.featureFlags.getClass();
        this.defaultColor = -1;
    }

    public static DoubleShadowStatusBarIconDrawable getShadowDrawable(View view, int i) {
        Context context = view.getContext();
        Drawable drawable = context.getResources().getDrawable(i, null);
        int height = view.getHeight();
        return new DoubleShadowStatusBarIconDrawable(drawable, context, MathKt__MathJVMKt.roundToInt((drawable.getIntrinsicWidth() / drawable.getIntrinsicHeight()) * height), height);
    }

    @Override // com.android.systemui.statusbar.pipeline.wifi.p029ui.viewmodel.WifiViewModelCommon
    public final StateFlow getActivityIcon() {
        return this.commonImpl.getActivityIcon();
    }

    @Override // com.android.systemui.statusbar.pipeline.wifi.p029ui.viewmodel.WifiViewModelCommon
    public final Flow getDeXWifiIcon() {
        return this.commonImpl.getDeXWifiIcon();
    }

    @Override // com.android.systemui.statusbar.pipeline.wifi.p029ui.viewmodel.WifiViewModelCommon
    public final StateFlow getUpdateDeXWifiIconModel() {
        return this.commonImpl.getUpdateDeXWifiIconModel();
    }

    @Override // com.android.systemui.statusbar.pipeline.wifi.p029ui.viewmodel.WifiViewModelCommon
    public final StateFlow getWifiIcon() {
        return this.commonImpl.getWifiIcon();
    }
}
