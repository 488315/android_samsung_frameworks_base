package com.android.systemui.statusbar.pipeline.wifi.ui.viewmodel;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import com.android.systemui.statusbar.phone.DoubleShadowStatusBarIconDrawable;
import com.android.systemui.statusbar.phone.StatusBarLocation;
import com.android.systemui.statusbar.pipeline.StatusBarPipelineFlags;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.math.MathKt__MathJVMKt;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.StateFlow;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public abstract class LocationBasedWifiViewModel implements WifiViewModelCommon {
    public static final Companion Companion = new Companion(null);
    public final WifiViewModelCommon commonImpl;
    public final int defaultColor = -1;
    public final StatusBarLocation location;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {

        /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
                try {
                    iArr[StatusBarLocation.SHADE_CARRIER_GROUP.ordinal()] = 5;
                } catch (NoSuchFieldError unused5) {
                }
                try {
                    iArr[StatusBarLocation.AOD.ordinal()] = 6;
                } catch (NoSuchFieldError unused6) {
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
    }

    public static DoubleShadowStatusBarIconDrawable getShadowDrawable(View view, int i) {
        Context context = view.getContext();
        Drawable drawable = context.getResources().getDrawable(i, null);
        int height = view.getHeight();
        return new DoubleShadowStatusBarIconDrawable(drawable, context, MathKt__MathJVMKt.roundToInt((drawable.getIntrinsicWidth() / drawable.getIntrinsicHeight()) * height), height);
    }

    @Override // com.android.systemui.statusbar.pipeline.wifi.ui.viewmodel.WifiViewModelCommon
    public final StateFlow getActivityIcon() {
        return this.commonImpl.getActivityIcon();
    }

    @Override // com.android.systemui.statusbar.pipeline.wifi.ui.viewmodel.WifiViewModelCommon
    public final Flow getDeXWifiIcon() {
        return this.commonImpl.getDeXWifiIcon();
    }

    @Override // com.android.systemui.statusbar.pipeline.wifi.ui.viewmodel.WifiViewModelCommon
    public final StateFlow getUpdateDeXWifiIconModel() {
        return this.commonImpl.getUpdateDeXWifiIconModel();
    }

    @Override // com.android.systemui.statusbar.pipeline.wifi.ui.viewmodel.WifiViewModelCommon
    public final StateFlow getWifiIcon() {
        return this.commonImpl.getWifiIcon();
    }
}
