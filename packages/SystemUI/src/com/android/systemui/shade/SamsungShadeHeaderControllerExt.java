package com.android.systemui.shade;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Handler;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.MenuPopupWindow$MenuDropDownListView$$ExternalSyntheticOutline0;
import androidx.compose.animation.FlingCalculator$FlingInfo$$ExternalSyntheticOutline0;
import androidx.compose.animation.core.TransitionKt$$ExternalSyntheticOutline0;
import androidx.constraintlayout.motion.widget.MotionLayout;
import com.android.settingslib.Utils;
import com.android.settingslib.volume.data.repository.LocalMediaRepositoryImpl$DevicesUpdate$DeviceListUpdate$$ExternalSyntheticOutline0;
import com.android.systemui.BasicRune;
import com.android.systemui.R;
import com.android.systemui.battery.BatteryMeterView;
import com.android.systemui.plugins.DarkIconDispatcher;
import com.android.systemui.privacy.PrivacyItemController;
import com.android.systemui.qs.SecQSPanelResourcePicker;
import com.android.systemui.shade.SamsungShadeHeaderControllerExt;
import com.android.systemui.shade.carrier.ShadeCarrier;
import com.android.systemui.shade.carrier.ShadeCarrierGroup;
import com.android.systemui.statusbar.LockscreenShadeTransitionController;
import com.android.systemui.statusbar.phone.IndicatorGardenModel;
import com.android.systemui.statusbar.phone.IndicatorGardenPresenter;
import com.android.systemui.statusbar.phone.IndicatorScaleGardener;
import com.android.systemui.statusbar.phone.KeyguardStatusBarWallpaperHelper;
import com.android.systemui.statusbar.phone.KeyguardStatusBarWallpaperListener;
import com.android.systemui.statusbar.phone.SidelingCutoutContainerInfo;
import com.android.systemui.statusbar.phone.StatusIconContainer;
import com.android.systemui.statusbar.phone.StatusIconContainerController;
import com.android.systemui.statusbar.phone.ui.SamsungPopOverIconManager;
import com.android.systemui.statusbar.phone.ui.TintedIconManager;
import com.android.systemui.statusbar.policy.BatteryController;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.statusbar.policy.NetspeedView;
import com.android.systemui.statusbar.policy.NetspeedViewController;
import com.android.systemui.statusbar.policy.QSClockPopOverImmersiveView;
import com.android.systemui.util.DeviceState;
import com.android.systemui.util.DeviceType;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;
import kotlin.text.StringsKt__StringsKt;

/* loaded from: classes3.dex */
public final class SamsungShadeHeaderControllerExt implements LockscreenShadeTransitionController.Callback, DarkIconDispatcher.DarkReceiver {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final BatteryController batteryController;
    public final BatteryMeterView batteryIcon;
    public final QSClockPopOverImmersiveView clockPopOverImmersiveView;
    public final ConfigurationController configurationController;
    public final Context context;
    public final DarkIconDispatcher darkIconDispatcher;
    public final MotionLayout headerView;
    public final View hoverSystemIconsContainer;
    public final StatusIconContainer iconContainer;
    public final IndicatorGardenPresenter indicatorGardenPresenter;
    public final IndicatorScaleGardener indicatorScaleGardener;
    public final KeyguardStateController keyguardStateController;
    public final KeyguardStatusBarWallpaperHelper keyguardStatusBarWallpaperHelper;
    public final LockscreenShadeTransitionController lockscreenShadeTransitionController;
    public final Handler mainHandler;
    public final NetspeedViewController netspeedViewController;
    public final SamsungPopOverIconManager.Factory popOverIconManagerFactory;
    public final View privacyContainer;
    public final PrivacyItemController privacyItemController;
    public boolean prvFragmentToShade;
    public final SecQSPanelResourcePicker qsPanelResourcePicker;
    public int recursiveCallCnt;
    public final ShadeCarrierGroup shadeCarrierGroup;
    public final StatusIconContainerController statusIconContainerController;
    public TintedIconManager tintedIconManager;
    public final boolean debug = DeviceType.isEngOrUTBinary();
    public final SamsungShadeHeaderControllerExt$keyguardStateControllerCallback$1 keyguardStateControllerCallback = new KeyguardStateController.Callback() { // from class: com.android.systemui.shade.SamsungShadeHeaderControllerExt$keyguardStateControllerCallback$1
        @Override // com.android.systemui.statusbar.policy.KeyguardStateController.Callback
        public final void onKeyguardShowingChanged() {
            final SamsungShadeHeaderControllerExt samsungShadeHeaderControllerExt = this.this$0;
            samsungShadeHeaderControllerExt.mainHandler.post(new Runnable() { // from class: com.android.systemui.shade.SamsungShadeHeaderControllerExt$keyguardStateControllerCallback$1$onKeyguardShowingChanged$1
                @Override // java.lang.Runnable
                public final void run() {
                    SamsungShadeHeaderControllerExt samsungShadeHeaderControllerExt2 = samsungShadeHeaderControllerExt;
                    if (!((KeyguardStateControllerImpl) samsungShadeHeaderControllerExt2.keyguardStateController).mShowing || !DeviceState.isShowingPopOverStatusBar(samsungShadeHeaderControllerExt2.context)) {
                        samsungShadeHeaderControllerExt.updateViewColors();
                        return;
                    }
                    SamsungShadeHeaderControllerExt samsungShadeHeaderControllerExt3 = samsungShadeHeaderControllerExt;
                    KeyguardStatusBarWallpaperHelper keyguardStatusBarWallpaperHelper = samsungShadeHeaderControllerExt3.keyguardStatusBarWallpaperHelper;
                    samsungShadeHeaderControllerExt3.printLog$1("onKeyguardShowingChanged(true) color:" + Integer.toHexString(keyguardStatusBarWallpaperHelper.fontColorFromWallPaper));
                    samsungShadeHeaderControllerExt3.updateColorModel(samsungShadeHeaderControllerExt3.model.colorModelPopOverLock, samsungShadeHeaderControllerExt3.emptyTintRect, keyguardStatusBarWallpaperHelper.intensity, keyguardStatusBarWallpaperHelper.fontColorFromWallPaper);
                }
            });
        }
    };
    public final SamsungShadeHeaderControllerExt$keyguardStatusBarWallpaperListener$1 keyguardStatusBarWallpaperListener = new KeyguardStatusBarWallpaperListener() { // from class: com.android.systemui.shade.SamsungShadeHeaderControllerExt$keyguardStatusBarWallpaperListener$1
        @Override // com.android.systemui.statusbar.phone.KeyguardStatusBarWallpaperListener
        public final void onWallpaperUpdated() {
            final SamsungShadeHeaderControllerExt samsungShadeHeaderControllerExt = this.this$0;
            samsungShadeHeaderControllerExt.mainHandler.post(new Runnable() { // from class: com.android.systemui.shade.SamsungShadeHeaderControllerExt$keyguardStatusBarWallpaperListener$1$onWallpaperUpdated$1
                @Override // java.lang.Runnable
                public final void run() {
                    SamsungShadeHeaderControllerExt samsungShadeHeaderControllerExt2 = samsungShadeHeaderControllerExt;
                    if (((KeyguardStateControllerImpl) samsungShadeHeaderControllerExt2.keyguardStateController).mShowing) {
                        if (!DeviceState.isShowingPopOverStatusBar(samsungShadeHeaderControllerExt2.context)) {
                            samsungShadeHeaderControllerExt.updateViewColors();
                            return;
                        }
                        SamsungShadeHeaderControllerExt samsungShadeHeaderControllerExt3 = samsungShadeHeaderControllerExt;
                        KeyguardStatusBarWallpaperHelper keyguardStatusBarWallpaperHelper = samsungShadeHeaderControllerExt3.keyguardStatusBarWallpaperHelper;
                        samsungShadeHeaderControllerExt3.printLog$1("onWallpaperUpdated(KeyguardShowing) color:" + Integer.toHexString(keyguardStatusBarWallpaperHelper.fontColorFromWallPaper));
                        samsungShadeHeaderControllerExt3.updateColorModel(samsungShadeHeaderControllerExt3.model.colorModelPopOverLock, samsungShadeHeaderControllerExt3.emptyTintRect, keyguardStatusBarWallpaperHelper.intensity, keyguardStatusBarWallpaperHelper.fontColorFromWallPaper);
                    }
                }
            });
        }
    };
    public final SamsungShadeHeaderControllerExt$configurationControllerListener$1 configurationControllerListener = new ConfigurationController.ConfigurationListener() { // from class: com.android.systemui.shade.SamsungShadeHeaderControllerExt$configurationControllerListener$1
        @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
        public final void onConfigChanged(Configuration configuration) {
            update$16();
        }

        @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
        public final void onDensityOrFontScaleChanged() {
            int i = SamsungShadeHeaderControllerExt.$r8$clinit;
            SamsungShadeHeaderControllerExt samsungShadeHeaderControllerExt = this.this$0;
            samsungShadeHeaderControllerExt.printLog$1("onDensityOrFontScaleChanged");
            float f = samsungShadeHeaderControllerExt.indicatorScaleGardener.getLatestScaleModel(samsungShadeHeaderControllerExt.context).ratio;
            samsungShadeHeaderControllerExt.clockPopOverImmersiveView.setTextSize(0, r1.getContext().getResources().getDimensionPixelSize(R.dimen.status_bar_clock_size) * f);
            update$16();
        }

        @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
        public final void onDisplayDeviceTypeChanged() {
            int i = SamsungShadeHeaderControllerExt.$r8$clinit;
            this.this$0.printLog$1("onDisplayDeviceTypeChanged");
            update$16();
        }

        public final void update$16() {
            final SamsungShadeHeaderControllerExt samsungShadeHeaderControllerExt = this.this$0;
            samsungShadeHeaderControllerExt.mainHandler.post(new Runnable() { // from class: com.android.systemui.shade.SamsungShadeHeaderControllerExt$configurationControllerListener$1$update$1
                @Override // java.lang.Runnable
                public final void run() {
                    if (SamsungShadeHeaderControllerExt.access$updateIsPopOverStatusBar(samsungShadeHeaderControllerExt)) {
                        return;
                    }
                    SamsungShadeHeaderControllerExt samsungShadeHeaderControllerExt2 = samsungShadeHeaderControllerExt;
                    SamsungShadeHeaderControllerExt.SamsungShadeHeaderControllerExtModel samsungShadeHeaderControllerExtModel = samsungShadeHeaderControllerExt2.model;
                    samsungShadeHeaderControllerExt2.updateViewColors();
                }
            });
        }
    };
    public final PrivacyItemController.Callback privacyItemControllerCallback = new PrivacyItemController.Callback() { // from class: com.android.systemui.shade.SamsungShadeHeaderControllerExt$privacyItemControllerCallback$1
        @Override // com.android.systemui.privacy.PrivacyItemController.Callback
        public final void onPrivacyItemsChanged(List list) {
            if (BasicRune.STATUS_POP_OVER_PANEL_BAR && BasicRune.STATUS_LAYOUT_SIDELING_CUTOUT) {
                String strM = LocalMediaRepositoryImpl$DevicesUpdate$DeviceListUpdate$$ExternalSyntheticOutline0.m("onPrivacyItemsChanged(", list, ")");
                int i = SamsungShadeHeaderControllerExt.$r8$clinit;
                final SamsungShadeHeaderControllerExt samsungShadeHeaderControllerExt = this.this$0;
                samsungShadeHeaderControllerExt.printLog$1(strM);
                samsungShadeHeaderControllerExt.mainHandler.post(new Runnable() { // from class: com.android.systemui.shade.SamsungShadeHeaderControllerExt$privacyItemControllerCallback$1$onPrivacyItemsChanged$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        samsungShadeHeaderControllerExt.iconContainer.requestLayout();
                    }
                });
            }
        }
    };
    public final SamsungShadeHeaderControllerExt$batteryStateChangeCallback$1 batteryStateChangeCallback = new BatteryController.BatteryStateChangeCallback() { // from class: com.android.systemui.shade.SamsungShadeHeaderControllerExt$batteryStateChangeCallback$1
        public boolean mCharging;
        public int mLevel = -1;

        @Override // com.android.systemui.statusbar.policy.BatteryController.BatteryStateChangeCallback
        public final void onBatteryLevelChanged(int i, boolean z, boolean z2) {
            if (this.mLevel == i && this.mCharging == z2) {
                return;
            }
            this.mLevel = i;
            this.mCharging = z2;
            final SamsungShadeHeaderControllerExt samsungShadeHeaderControllerExt = this.this$0;
            samsungShadeHeaderControllerExt.mainHandler.post(new Runnable() { // from class: com.android.systemui.shade.SamsungShadeHeaderControllerExt$batteryStateChangeCallback$1$onBatteryLevelChanged$1
                @Override // java.lang.Runnable
                public final void run() {
                    StatusIconContainer statusIconContainer = samsungShadeHeaderControllerExt.iconContainer;
                    statusIconContainer.measure(0, 0);
                    statusIconContainer.requestLayout();
                }
            });
        }
    };
    public final SamsungShadeHeaderControllerExt$sidelingCutoutContainerInfo$1 sidelingCutoutContainerInfo = new SidelingCutoutContainerInfo() { // from class: com.android.systemui.shade.SamsungShadeHeaderControllerExt$sidelingCutoutContainerInfo$1
        @Override // com.android.systemui.statusbar.phone.SidelingCutoutContainerInfo
        public final int getRightSideAvailableWidth(Rect rect) {
            SamsungShadeHeaderControllerExt samsungShadeHeaderControllerExt = this.this$0;
            if (!DeviceState.isShowingPopOverStatusBar(samsungShadeHeaderControllerExt.context)) {
                return 0;
            }
            int iWidth = samsungShadeHeaderControllerExt.context.getResources().getConfiguration().windowConfiguration.getBounds().width();
            int i = rect.right;
            int paddingEnd = samsungShadeHeaderControllerExt.headerView.getPaddingEnd();
            int paddingEnd2 = samsungShadeHeaderControllerExt.iconContainer.getPaddingEnd();
            return (iWidth - ((((samsungShadeHeaderControllerExt.privacyContainer.getMeasuredWidth() + samsungShadeHeaderControllerExt.batteryIcon.getMeasuredWidth()) + paddingEnd) + paddingEnd2) + samsungShadeHeaderControllerExt.hoverSystemIconsContainer.getPaddingEnd())) - i;
        }
    };
    public final SamsungShadeHeaderControllerExt$indicatorGardenPresenterListener$1 indicatorGardenPresenterListener = new IndicatorGardenPresenter.GardenListener() { // from class: com.android.systemui.shade.SamsungShadeHeaderControllerExt$indicatorGardenPresenterListener$1
        @Override // com.android.systemui.statusbar.phone.IndicatorGardenPresenter.GardenListener
        public final void onGardenChanged(IndicatorGardenModel indicatorGardenModel) {
            final SamsungShadeHeaderControllerExt samsungShadeHeaderControllerExt = this.this$0;
            samsungShadeHeaderControllerExt.mainHandler.post(new Runnable() { // from class: com.android.systemui.shade.SamsungShadeHeaderControllerExt$indicatorGardenPresenterListener$1$onGardenChanged$1
                @Override // java.lang.Runnable
                public final void run() {
                    if (SamsungShadeHeaderControllerExt.access$updateIsPopOverStatusBar(samsungShadeHeaderControllerExt)) {
                        return;
                    }
                    samsungShadeHeaderControllerExt.updateHeaderPadding();
                    samsungShadeHeaderControllerExt.updateShadeCarrierGroupMaxWidth();
                }
            });
        }
    };
    public final ArrayList emptyTintRect = new ArrayList();
    public float prvIconIntensity = -1.0f;
    public final SamsungShadeHeaderControllerExtModel model = new SamsungShadeHeaderControllerExtModel(false, 0, 0, 0, 0, null, null, null, 255, null);

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public final class SamsungShadeHeaderColorModel {
        public ArrayList iconAreas;
        public int iconForegroundColor;
        public float iconIntensity;
        public int iconTintColor;
        public final String type;

        public SamsungShadeHeaderColorModel() {
            this(null, null, 0.0f, 0, 0, 31, null);
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof SamsungShadeHeaderColorModel)) {
                return false;
            }
            SamsungShadeHeaderColorModel samsungShadeHeaderColorModel = (SamsungShadeHeaderColorModel) obj;
            return Intrinsics.areEqual(this.type, samsungShadeHeaderColorModel.type) && Intrinsics.areEqual(this.iconAreas, samsungShadeHeaderColorModel.iconAreas) && Float.compare(this.iconIntensity, samsungShadeHeaderColorModel.iconIntensity) == 0 && this.iconTintColor == samsungShadeHeaderColorModel.iconTintColor && this.iconForegroundColor == samsungShadeHeaderColorModel.iconForegroundColor;
        }

        public final int hashCode() {
            int iHashCode = this.type.hashCode() * 31;
            ArrayList arrayList = this.iconAreas;
            return Integer.hashCode(this.iconForegroundColor) + ReorderTile$$ExternalSyntheticOutline0.m(this.iconTintColor, FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.iconIntensity, (iHashCode + (arrayList == null ? 0 : arrayList.hashCode())) * 31, 31), 31);
        }

        public final String toString() {
            ArrayList arrayList = this.iconAreas;
            int i = StringCompanionObject.$r8$clinit;
            String str = String.format("%.2f", Arrays.copyOf(new Object[]{Float.valueOf(this.iconIntensity)}, 1));
            String hexString = Integer.toHexString(this.iconTintColor);
            String hexString2 = Integer.toHexString(this.iconForegroundColor);
            StringBuilder sb = new StringBuilder();
            sb.append(this.type);
            sb.append("-Color(");
            sb.append(arrayList);
            sb.append(", ");
            MoveResult$$ExternalSyntheticOutline0.m(sb, str, ", t=", hexString, ", f=");
            return TransitionKt$$ExternalSyntheticOutline0.m(sb, hexString2, ")");
        }

        public SamsungShadeHeaderColorModel(String str, ArrayList<Rect> arrayList, float f, int i, int i2) {
            this.type = str;
            this.iconAreas = arrayList;
            this.iconIntensity = f;
            this.iconTintColor = i;
            this.iconForegroundColor = i2;
        }

        /*  JADX ERROR: NullPointerException in pass: InitCodeVariables
            java.lang.NullPointerException
            */
        public /* synthetic */ SamsungShadeHeaderColorModel(java.lang.String r2, java.util.ArrayList r3, float r4, int r5, int r6, int r7, kotlin.jvm.internal.DefaultConstructorMarker r8) {
            /*
                r1 = this;
                r8 = r7 & 1
                if (r8 == 0) goto L6
                java.lang.String r2 = ""
            L6:
                r8 = r7 & 2
                if (r8 == 0) goto Lf
                java.util.ArrayList r3 = new java.util.ArrayList
                r3.<init>()
            Lf:
                r8 = r7 & 4
                if (r8 == 0) goto L15
                r4 = -1082130432(0xffffffffbf800000, float:-1.0)
            L15:
                r8 = r7 & 8
                r0 = 0
                if (r8 == 0) goto L1b
                r5 = r0
            L1b:
                r7 = r7 & 16
                if (r7 == 0) goto L26
                r8 = r0
                r6 = r4
                r7 = r5
                r4 = r2
                r5 = r3
                r3 = r1
                goto L2c
            L26:
                r8 = r6
                r7 = r5
                r5 = r3
                r6 = r4
                r3 = r1
                r4 = r2
            L2c:
                r3.<init>(r4, r5, r6, r7, r8)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.shade.SamsungShadeHeaderControllerExt.SamsungShadeHeaderColorModel.<init>(java.lang.String, java.util.ArrayList, float, int, int, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
        }
    }

    public final class SamsungShadeHeaderControllerExtModel {
        public int bottomPadding;
        public final SamsungShadeHeaderColorModel colorModelNormalPanel;
        public final SamsungShadeHeaderColorModel colorModelPopOverHome;
        public final SamsungShadeHeaderColorModel colorModelPopOverLock;
        public boolean isPopOverStatusBar;
        public int leftPadding;
        public int rightPadding;
        public int topPadding;

        public SamsungShadeHeaderControllerExtModel() {
            this(false, 0, 0, 0, 0, null, null, null, 255, null);
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof SamsungShadeHeaderControllerExtModel)) {
                return false;
            }
            SamsungShadeHeaderControllerExtModel samsungShadeHeaderControllerExtModel = (SamsungShadeHeaderControllerExtModel) obj;
            return this.isPopOverStatusBar == samsungShadeHeaderControllerExtModel.isPopOverStatusBar && this.leftPadding == samsungShadeHeaderControllerExtModel.leftPadding && this.topPadding == samsungShadeHeaderControllerExtModel.topPadding && this.rightPadding == samsungShadeHeaderControllerExtModel.rightPadding && this.bottomPadding == samsungShadeHeaderControllerExtModel.bottomPadding && Intrinsics.areEqual(this.colorModelNormalPanel, samsungShadeHeaderControllerExtModel.colorModelNormalPanel) && Intrinsics.areEqual(this.colorModelPopOverHome, samsungShadeHeaderControllerExtModel.colorModelPopOverHome) && Intrinsics.areEqual(this.colorModelPopOverLock, samsungShadeHeaderControllerExtModel.colorModelPopOverLock);
        }

        public final int hashCode() {
            return this.colorModelPopOverLock.hashCode() + ((this.colorModelPopOverHome.hashCode() + ((this.colorModelNormalPanel.hashCode() + ReorderTile$$ExternalSyntheticOutline0.m(this.bottomPadding, ReorderTile$$ExternalSyntheticOutline0.m(this.rightPadding, ReorderTile$$ExternalSyntheticOutline0.m(this.topPadding, ReorderTile$$ExternalSyntheticOutline0.m(this.leftPadding, Boolean.hashCode(this.isPopOverStatusBar) * 31, 31), 31), 31), 31)) * 31)) * 31);
        }

        public final String toString() {
            boolean z = this.isPopOverStatusBar;
            int i = StringCompanionObject.$r8$clinit;
            return "Model[ PopOver?" + z + ", paddings(" + String.format("%3s,%3s,%3s,%3s", Arrays.copyOf(new Object[]{Integer.valueOf(this.leftPadding), Integer.valueOf(this.topPadding), Integer.valueOf(this.rightPadding), Integer.valueOf(this.bottomPadding)}, 4)) + "),  " + this.colorModelNormalPanel + ",  " + this.colorModelPopOverHome + ",  " + this.colorModelPopOverLock + "]";
        }

        public SamsungShadeHeaderControllerExtModel(boolean z, int i, int i2, int i3, int i4, SamsungShadeHeaderColorModel samsungShadeHeaderColorModel, SamsungShadeHeaderColorModel samsungShadeHeaderColorModel2, SamsungShadeHeaderColorModel samsungShadeHeaderColorModel3) {
            this.isPopOverStatusBar = z;
            this.leftPadding = i;
            this.topPadding = i2;
            this.rightPadding = i3;
            this.bottomPadding = i4;
            this.colorModelNormalPanel = samsungShadeHeaderColorModel;
            this.colorModelPopOverHome = samsungShadeHeaderColorModel2;
            this.colorModelPopOverLock = samsungShadeHeaderColorModel3;
        }

        public /* synthetic */ SamsungShadeHeaderControllerExtModel(boolean z, int i, int i2, int i3, int i4, SamsungShadeHeaderColorModel samsungShadeHeaderColorModel, SamsungShadeHeaderColorModel samsungShadeHeaderColorModel2, SamsungShadeHeaderColorModel samsungShadeHeaderColorModel3, int i5, DefaultConstructorMarker defaultConstructorMarker) {
            this((i5 & 1) != 0 ? false : z, (i5 & 2) != 0 ? -1 : i, (i5 & 4) != 0 ? -1 : i2, (i5 & 8) != 0 ? -1 : i3, (i5 & 16) == 0 ? i4 : -1, (i5 & 32) != 0 ? new SamsungShadeHeaderColorModel("nPanel", null, 0.0f, 0, 0, 30, null) : samsungShadeHeaderColorModel, (i5 & 64) != 0 ? new SamsungShadeHeaderColorModel("pHome", null, 0.0f, 0, 0, 30, null) : samsungShadeHeaderColorModel2, (i5 & 128) != 0 ? new SamsungShadeHeaderColorModel("pLock", null, 0.0f, 0, 0, 30, null) : samsungShadeHeaderColorModel3);
        }
    }

    static {
        new Companion(null);
    }

    /* JADX WARN: Type inference failed for: r8v26, types: [com.android.systemui.shade.SamsungShadeHeaderControllerExt$keyguardStateControllerCallback$1] */
    /* JADX WARN: Type inference failed for: r8v27, types: [com.android.systemui.shade.SamsungShadeHeaderControllerExt$keyguardStatusBarWallpaperListener$1] */
    /* JADX WARN: Type inference failed for: r8v28, types: [com.android.systemui.shade.SamsungShadeHeaderControllerExt$configurationControllerListener$1] */
    /* JADX WARN: Type inference failed for: r8v30, types: [com.android.systemui.shade.SamsungShadeHeaderControllerExt$batteryStateChangeCallback$1] */
    /* JADX WARN: Type inference failed for: r8v31, types: [com.android.systemui.shade.SamsungShadeHeaderControllerExt$sidelingCutoutContainerInfo$1] */
    /* JADX WARN: Type inference failed for: r8v32, types: [com.android.systemui.shade.SamsungShadeHeaderControllerExt$indicatorGardenPresenterListener$1] */
    public SamsungShadeHeaderControllerExt(Context context, Handler handler, MotionLayout motionLayout, ConfigurationController configurationController, SecQSPanelResourcePicker secQSPanelResourcePicker, DarkIconDispatcher darkIconDispatcher, SamsungPopOverIconManager.Factory factory, StatusIconContainerController statusIconContainerController, IndicatorGardenPresenter indicatorGardenPresenter, IndicatorScaleGardener indicatorScaleGardener, NetspeedViewController netspeedViewController, LockscreenShadeTransitionController lockscreenShadeTransitionController, PrivacyItemController privacyItemController, BatteryController batteryController, KeyguardStatusBarWallpaperHelper keyguardStatusBarWallpaperHelper, KeyguardStateController keyguardStateController) {
        this.context = context;
        this.mainHandler = handler;
        this.headerView = motionLayout;
        this.configurationController = configurationController;
        this.qsPanelResourcePicker = secQSPanelResourcePicker;
        this.darkIconDispatcher = darkIconDispatcher;
        this.popOverIconManagerFactory = factory;
        this.statusIconContainerController = statusIconContainerController;
        this.indicatorGardenPresenter = indicatorGardenPresenter;
        this.indicatorScaleGardener = indicatorScaleGardener;
        this.netspeedViewController = netspeedViewController;
        this.lockscreenShadeTransitionController = lockscreenShadeTransitionController;
        this.privacyItemController = privacyItemController;
        this.batteryController = batteryController;
        this.keyguardStatusBarWallpaperHelper = keyguardStatusBarWallpaperHelper;
        this.keyguardStateController = keyguardStateController;
        this.batteryIcon = (BatteryMeterView) motionLayout.requireViewById(R.id.batteryRemainingIcon);
        this.privacyContainer = motionLayout.requireViewById(R.id.privacy_container);
        this.iconContainer = (StatusIconContainer) motionLayout.requireViewById(R.id.statusIcons);
        this.shadeCarrierGroup = (ShadeCarrierGroup) motionLayout.requireViewById(R.id.carrier_group);
        this.clockPopOverImmersiveView = (QSClockPopOverImmersiveView) motionLayout.requireViewById(R.id.pop_over_immersive_clock);
        this.hoverSystemIconsContainer = motionLayout.requireViewById(R.id.hover_system_icons_container);
    }

    public static final boolean access$updateIsPopOverStatusBar(SamsungShadeHeaderControllerExt samsungShadeHeaderControllerExt) {
        boolean zIsShowingPopOverStatusBar = DeviceState.isShowingPopOverStatusBar(samsungShadeHeaderControllerExt.context);
        SamsungShadeHeaderControllerExtModel samsungShadeHeaderControllerExtModel = samsungShadeHeaderControllerExt.model;
        boolean z = samsungShadeHeaderControllerExtModel.isPopOverStatusBar;
        boolean z2 = false;
        if (z != zIsShowingPopOverStatusBar) {
            samsungShadeHeaderControllerExt.printLog$1("Changed popover " + z + " >> " + zIsShowingPopOverStatusBar);
            samsungShadeHeaderControllerExtModel.isPopOverStatusBar = zIsShowingPopOverStatusBar;
            samsungShadeHeaderControllerExt.updateHeaderViewPaddings(samsungShadeHeaderControllerExtModel.leftPadding, samsungShadeHeaderControllerExtModel.topPadding, samsungShadeHeaderControllerExtModel.rightPadding, samsungShadeHeaderControllerExtModel.bottomPadding);
            samsungShadeHeaderControllerExt.updateHeaderPadding();
            samsungShadeHeaderControllerExt.updateShadeCarrierGroupMaxWidth();
            samsungShadeHeaderControllerExt.clockPopOverImmersiveView.setVisibility(samsungShadeHeaderControllerExtModel.isPopOverStatusBar ? 0 : 8);
            boolean z3 = samsungShadeHeaderControllerExtModel.isPopOverStatusBar;
            ShadeCarrierGroup shadeCarrierGroup = samsungShadeHeaderControllerExt.shadeCarrierGroup;
            shadeCarrierGroup.getClass();
            z2 = true;
            if (BasicRune.STATUS_POP_OVER_PANEL_BAR) {
                boolean z4 = !z3;
                shadeCarrierGroup.getCarrier1View().mCarrierText.shadowEnabled = z4;
                ((ShadeCarrier) shadeCarrierGroup.findViewById(R.id.carrier2)).mCarrierText.shadowEnabled = z4;
                ((ShadeCarrier) shadeCarrierGroup.findViewById(R.id.carrier3)).mCarrierText.shadowEnabled = z4;
            }
            samsungShadeHeaderControllerExt.updateViewColors();
        }
        return z2;
    }

    @Override // com.android.systemui.plugins.DarkIconDispatcher.DarkReceiver
    public final void onDarkChanged(ArrayList arrayList, float f, int i) {
        if (BasicRune.STATUS_POP_OVER_PANEL_BAR) {
            updateColorModel(this.model.colorModelPopOverHome, arrayList, f, i);
        }
    }

    public final void printLog$1(String str) {
        if (this.debug) {
            StringBuilder sbM = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(StringsKt__StringsKt.padEnd(70, str), " ");
            sbM.append(this.model);
            Log.d("SamsungShadeHeaderControllerExt", sbM.toString());
        }
    }

    public final void setChildFocusableFalse(View view) {
        int i = this.recursiveCallCnt + 1;
        this.recursiveCallCnt = i;
        if (i > 1000 || view == null) {
            return;
        }
        view.setFocusable(false);
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            int childCount = viewGroup.getChildCount();
            for (int i2 = 0; i2 < childCount; i2++) {
                setChildFocusableFalse(viewGroup.getChildAt(i2));
            }
        }
    }

    public final void updateColorModel(SamsungShadeHeaderColorModel samsungShadeHeaderColorModel, ArrayList arrayList, float f, int i) {
        int colorAttrDefaultColor = ((KeyguardStateControllerImpl) this.keyguardStateController).mShowing ? ((double) Color.luminance(i)) < 0.5d ? -301989889 : -16777216 : Utils.getColorAttrDefaultColor(this.context, android.R.attr.textColorPrimaryInverse, 0);
        samsungShadeHeaderColorModel.iconAreas = arrayList;
        samsungShadeHeaderColorModel.iconIntensity = f;
        samsungShadeHeaderColorModel.iconTintColor = i;
        samsungShadeHeaderColorModel.iconForegroundColor = colorAttrDefaultColor;
        updateViewColors();
    }

    public final void updateHeaderPadding() {
        int shadeHeaderSidePadding;
        int shadeHeaderSidePadding2;
        boolean zIsShowingPopOverStatusBar = DeviceState.isShowingPopOverStatusBar(this.context);
        SecQSPanelResourcePicker secQSPanelResourcePicker = this.qsPanelResourcePicker;
        IndicatorGardenPresenter indicatorGardenPresenter = this.indicatorGardenPresenter;
        if (zIsShowingPopOverStatusBar) {
            shadeHeaderSidePadding = indicatorGardenPresenter.cachedGardenModel.paddingLeft;
        } else {
            shadeHeaderSidePadding = secQSPanelResourcePicker.resourcePickHelper.getTargetPicker().getShadeHeaderSidePadding(this.context);
        }
        if (DeviceState.isShowingPopOverStatusBar(this.context)) {
            shadeHeaderSidePadding2 = indicatorGardenPresenter.cachedGardenModel.paddingRight;
        } else {
            shadeHeaderSidePadding2 = secQSPanelResourcePicker.resourcePickHelper.getTargetPicker().getShadeHeaderSidePadding(this.context);
        }
        SamsungShadeHeaderControllerExtModel samsungShadeHeaderControllerExtModel = this.model;
        updateHeaderViewPaddings(shadeHeaderSidePadding, samsungShadeHeaderControllerExtModel.topPadding, shadeHeaderSidePadding2, samsungShadeHeaderControllerExtModel.bottomPadding);
    }

    public final void updateHeaderViewPaddings(int i, int i2, int i3, int i4) {
        boolean z;
        StringBuilder sb = new StringBuilder();
        SamsungShadeHeaderControllerExtModel samsungShadeHeaderControllerExtModel = this.model;
        int i5 = samsungShadeHeaderControllerExtModel.leftPadding;
        boolean z2 = true;
        if (i5 != i) {
            sb.append("   l " + i5 + " >> " + i);
            samsungShadeHeaderControllerExtModel.leftPadding = i;
            z = true;
        } else {
            z = false;
        }
        int i6 = samsungShadeHeaderControllerExtModel.topPadding;
        if (i6 != i2) {
            sb.append("   t " + i6 + " >> " + i2);
            samsungShadeHeaderControllerExtModel.topPadding = i2;
            z = true;
        }
        int i7 = samsungShadeHeaderControllerExtModel.rightPadding;
        if (i7 != i3) {
            sb.append("   r " + i7 + " >> " + i3);
            samsungShadeHeaderControllerExtModel.rightPadding = i3;
            z = true;
        }
        int i8 = samsungShadeHeaderControllerExtModel.bottomPadding;
        if (i8 != i4) {
            sb.append("   b " + i8 + " >> " + i4);
            samsungShadeHeaderControllerExtModel.bottomPadding = i4;
        } else {
            z2 = z;
        }
        if (z2) {
            printLog$1(sb.toString());
            this.headerView.setPaddingRelative(samsungShadeHeaderControllerExtModel.leftPadding, samsungShadeHeaderControllerExtModel.topPadding, samsungShadeHeaderControllerExtModel.rightPadding, samsungShadeHeaderControllerExtModel.bottomPadding);
        }
    }

    public final void updateShadeCarrierGroupMaxWidth() {
        IndicatorGardenModel indicatorGardenModel = this.indicatorGardenPresenter.cachedGardenModel;
        this.shadeCarrierGroup.setGardenMaxWidth(DeviceState.isShowingPopOverStatusBar(this.context) ? MenuPopupWindow$MenuDropDownListView$$ExternalSyntheticOutline0.m(this.context) == 0 ? indicatorGardenModel.maxWidthRightContainer : indicatorGardenModel.maxWidthLeftContainer : -1);
    }

    public final void updateViewColors(SamsungShadeHeaderColorModel samsungShadeHeaderColorModel) {
        NetspeedViewController netspeedViewController;
        NetspeedView view;
        boolean z = BasicRune.STATUS_POP_OVER_PANEL_BAR;
        if (z) {
            float f = samsungShadeHeaderColorModel.iconIntensity;
            if (this.prvIconIntensity != f && (f >= 1.0f || f <= 0.0f)) {
                printLog$1("updateViewColors(" + samsungShadeHeaderColorModel.type + ")");
                this.prvIconIntensity = f;
            }
            ArrayList arrayList = samsungShadeHeaderColorModel.iconAreas;
            int i = samsungShadeHeaderColorModel.iconTintColor;
            ShadeCarrierGroup shadeCarrierGroup = this.shadeCarrierGroup;
            shadeCarrierGroup.getClass();
            if (z) {
                shadeCarrierGroup.getCarrier1View().mCarrierText.setTextColor(DarkIconDispatcher.getTint(arrayList, shadeCarrierGroup.getCarrier1View(), i));
            }
            ArrayList arrayList2 = samsungShadeHeaderColorModel.iconAreas;
            int i2 = samsungShadeHeaderColorModel.iconTintColor;
            QSClockPopOverImmersiveView qSClockPopOverImmersiveView = this.clockPopOverImmersiveView;
            qSClockPopOverImmersiveView.setTextColor(DarkIconDispatcher.getTint(arrayList2, qSClockPopOverImmersiveView, i2));
            if (BasicRune.STATUS_REAL_TIME_NETWORK_SPEED && (netspeedViewController = this.netspeedViewController) != null && (view = netspeedViewController.getView()) != null) {
                view.onDarkChanged(samsungShadeHeaderColorModel.iconAreas, samsungShadeHeaderColorModel.iconIntensity, samsungShadeHeaderColorModel.iconTintColor);
            }
            TintedIconManager tintedIconManager = this.tintedIconManager;
            if (tintedIconManager != null) {
                SamsungPopOverIconManager samsungPopOverIconManager = tintedIconManager instanceof SamsungPopOverIconManager ? (SamsungPopOverIconManager) tintedIconManager : null;
                if (samsungPopOverIconManager != null) {
                    SamsungShadeHeaderControllerExtModel samsungShadeHeaderControllerExtModel = this.model;
                    boolean z2 = samsungShadeHeaderControllerExtModel.isPopOverStatusBar && samsungShadeHeaderColorModel.equals(samsungShadeHeaderControllerExtModel.colorModelPopOverHome);
                    ArrayList arrayList3 = samsungShadeHeaderColorModel.iconAreas;
                    float f2 = samsungShadeHeaderColorModel.iconIntensity;
                    samsungPopOverIconManager.shouldUseTintIconArea = z2;
                    samsungPopOverIconManager.iconAreas = arrayList3;
                    samsungPopOverIconManager.iconIntensity = f2;
                }
                tintedIconManager.setTint(samsungShadeHeaderColorModel.iconTintColor, samsungShadeHeaderColorModel.iconForegroundColor);
            }
            this.batteryIcon.onDarkChanged(samsungShadeHeaderColorModel.iconAreas, samsungShadeHeaderColorModel.iconIntensity, samsungShadeHeaderColorModel.iconTintColor);
        }
    }

    public final void updateViewColors() {
        SamsungShadeHeaderControllerExtModel samsungShadeHeaderControllerExtModel = this.model;
        if (samsungShadeHeaderControllerExtModel.isPopOverStatusBar) {
            if (((KeyguardStateControllerImpl) this.keyguardStateController).mShowing) {
                updateViewColors(samsungShadeHeaderControllerExtModel.colorModelPopOverLock);
                return;
            } else {
                updateViewColors(samsungShadeHeaderControllerExtModel.colorModelPopOverHome);
                return;
            }
        }
        updateViewColors(samsungShadeHeaderControllerExtModel.colorModelNormalPanel);
    }
}
