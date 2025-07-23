package com.android.systemui.shade;

import android.content.Context;
import android.graphics.Rect;
import android.os.Handler;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.Log;
import android.view.View;
import androidx.collection.MutableObjectList$$ExternalSyntheticOutline0;
import androidx.compose.animation.FlingCalculator$FlingInfo$$ExternalSyntheticOutline0;
import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.viewpager.widget.ViewPager$$ExternalSyntheticOutline0;
import com.android.settingslib.volume.data.repository.LocalMediaRepositoryImpl$DevicesUpdate$DeviceListUpdate$$ExternalSyntheticOutline0;
import com.android.systemui.BasicRune;
import com.android.systemui.R;
import com.android.systemui.battery.BatteryMeterView;
import com.android.systemui.plugins.DarkIconDispatcher;
import com.android.systemui.privacy.PrivacyItemController;
import com.android.systemui.qs.SecQSPanelResourcePicker;
import com.android.systemui.shade.carrier.ShadeCarrier;
import com.android.systemui.shade.carrier.ShadeCarrierGroup;
import com.android.systemui.statusbar.LockscreenShadeTransitionController;
import com.android.systemui.statusbar.phone.IndicatorGardenPresenter;
import com.android.systemui.statusbar.phone.IndicatorScaleGardener;
import com.android.systemui.statusbar.phone.StatusIconContainer;
import com.android.systemui.statusbar.phone.StatusIconContainerController;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.NetspeedView;
import com.android.systemui.statusbar.policy.NetspeedViewController;
import com.android.systemui.util.DeviceState;
import com.android.systemui.util.DeviceType;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class SamsungShadeHeaderControllerExt implements LockscreenShadeTransitionController.Callback, DarkIconDispatcher.DarkReceiver {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final BatteryMeterView batteryIcon;
    public final Context context;
    public final DarkIconDispatcher darkIconDispatcher;
    public final MotionLayout headerView;
    public final StatusIconContainer iconContainer;
    public final IndicatorGardenPresenter indicatorGardenPresenter;
    public final IndicatorScaleGardener indicatorScaleGardener;
    public final LockscreenShadeTransitionController lockscreenShadeTransitionController;
    public final Handler mainHandler;
    public final NetspeedViewController netspeedViewController;
    public final View privacyContainer;
    public final PrivacyItemController privacyItemController;
    public boolean prvFragmentToShade;
    public final SecQSPanelResourcePicker qsPanelResourcePicker;
    public final ShadeCarrierGroup shadeCarrierGroup;
    public final StatusIconContainerController statusIconContainerController;
    public final boolean debug = DeviceType.isEngOrUTBinary();
    public final PrivacyItemController.Callback privacyItemControllerCallback = new PrivacyItemController.Callback() { // from class: com.android.systemui.shade.SamsungShadeHeaderControllerExt$privacyItemControllerCallback$1
        @Override // com.android.systemui.privacy.PrivacyItemController.Callback
        public final void onPrivacyItemsChanged(List list) {
            if (BasicRune.STATUS_POP_OVER_PANEL_BAR && BasicRune.STATUS_LAYOUT_SIDELING_CUTOUT) {
                String m = LocalMediaRepositoryImpl$DevicesUpdate$DeviceListUpdate$$ExternalSyntheticOutline0.m("onPrivacyItemsChanged(", list, ")");
                int i = SamsungShadeHeaderControllerExt.$r8$clinit;
                final SamsungShadeHeaderControllerExt samsungShadeHeaderControllerExt = SamsungShadeHeaderControllerExt.this;
                samsungShadeHeaderControllerExt.printLog$1(m);
                samsungShadeHeaderControllerExt.mainHandler.post(new Runnable() { // from class: com.android.systemui.shade.SamsungShadeHeaderControllerExt$privacyItemControllerCallback$1$onPrivacyItemsChanged$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        SamsungShadeHeaderControllerExt.this.iconContainer.requestLayout();
                    }
                });
            }
        }
    };
    public final SamsungShadeHeaderControllerExtModel model = new SamsungShadeHeaderControllerExtModel(0, 0, 0, 0, false, null, 0.0f, 0, 255, null);

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class SamsungShadeHeaderControllerExtModel {
        public int bottomPadding;
        public ArrayList iconAreas;
        public float iconIntensity;
        public int iconTintColor;
        public boolean isPopOverStatusBar;
        public int leftPadding;
        public int rightPadding;
        public int topPadding;

        public SamsungShadeHeaderControllerExtModel() {
            this(0, 0, 0, 0, false, null, 0.0f, 0, 255, null);
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof SamsungShadeHeaderControllerExtModel)) {
                return false;
            }
            SamsungShadeHeaderControllerExtModel samsungShadeHeaderControllerExtModel = (SamsungShadeHeaderControllerExtModel) obj;
            return this.leftPadding == samsungShadeHeaderControllerExtModel.leftPadding && this.topPadding == samsungShadeHeaderControllerExtModel.topPadding && this.rightPadding == samsungShadeHeaderControllerExtModel.rightPadding && this.bottomPadding == samsungShadeHeaderControllerExtModel.bottomPadding && this.isPopOverStatusBar == samsungShadeHeaderControllerExtModel.isPopOverStatusBar && Intrinsics.areEqual(this.iconAreas, samsungShadeHeaderControllerExtModel.iconAreas) && Float.compare(this.iconIntensity, samsungShadeHeaderControllerExtModel.iconIntensity) == 0 && this.iconTintColor == samsungShadeHeaderControllerExtModel.iconTintColor;
        }

        public final int hashCode() {
            int m = TransitionData$$ExternalSyntheticOutline0.m(ReorderTile$$ExternalSyntheticOutline0.m(this.bottomPadding, ReorderTile$$ExternalSyntheticOutline0.m(this.rightPadding, ReorderTile$$ExternalSyntheticOutline0.m(this.topPadding, Integer.hashCode(this.leftPadding) * 31, 31), 31), 31), 31, this.isPopOverStatusBar);
            ArrayList arrayList = this.iconAreas;
            return Integer.hashCode(this.iconTintColor) + FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.iconIntensity, (m + (arrayList == null ? 0 : arrayList.hashCode())) * 31, 31);
        }

        public final String toString() {
            int i = this.leftPadding;
            int i2 = this.topPadding;
            int i3 = this.rightPadding;
            int i4 = this.bottomPadding;
            boolean z = this.isPopOverStatusBar;
            ArrayList arrayList = this.iconAreas;
            float f = this.iconIntensity;
            int i5 = this.iconTintColor;
            StringBuilder m = MutableObjectList$$ExternalSyntheticOutline0.m(i, i2, "SamsungShadeHeaderControllerExtModel(leftPadding=", ", topPadding=", ", rightPadding=");
            ViewPager$$ExternalSyntheticOutline0.m(m, i3, ", bottomPadding=", i4, ", isPopOverStatusBar=");
            m.append(z);
            m.append(", iconAreas=");
            m.append(arrayList);
            m.append(", iconIntensity=");
            m.append(f);
            m.append(", iconTintColor=");
            m.append(i5);
            m.append(")");
            return m.toString();
        }

        public SamsungShadeHeaderControllerExtModel(int i, int i2, int i3, int i4, boolean z, ArrayList<Rect> arrayList, float f, int i5) {
            this.leftPadding = i;
            this.topPadding = i2;
            this.rightPadding = i3;
            this.bottomPadding = i4;
            this.isPopOverStatusBar = z;
            this.iconAreas = arrayList;
            this.iconIntensity = f;
            this.iconTintColor = i5;
        }

        public /* synthetic */ SamsungShadeHeaderControllerExtModel(int i, int i2, int i3, int i4, boolean z, ArrayList arrayList, float f, int i5, int i6, DefaultConstructorMarker defaultConstructorMarker) {
            this((i6 & 1) != 0 ? -1 : i, (i6 & 2) != 0 ? -1 : i2, (i6 & 4) != 0 ? -1 : i3, (i6 & 8) != 0 ? -1 : i4, (i6 & 16) != 0 ? DeviceState.isShowingPopOverStatusBar() : z, (i6 & 32) != 0 ? null : arrayList, (i6 & 64) != 0 ? -1.0f : f, (i6 & 128) != 0 ? 0 : i5);
        }
    }

    static {
        new Companion(null);
    }

    public SamsungShadeHeaderControllerExt(Context context, Handler handler, MotionLayout motionLayout, ConfigurationController configurationController, SecQSPanelResourcePicker secQSPanelResourcePicker, DarkIconDispatcher darkIconDispatcher, StatusIconContainerController statusIconContainerController, IndicatorGardenPresenter indicatorGardenPresenter, IndicatorScaleGardener indicatorScaleGardener, NetspeedViewController netspeedViewController, LockscreenShadeTransitionController lockscreenShadeTransitionController, PrivacyItemController privacyItemController) {
        this.context = context;
        this.mainHandler = handler;
        this.headerView = motionLayout;
        this.qsPanelResourcePicker = secQSPanelResourcePicker;
        this.darkIconDispatcher = darkIconDispatcher;
        this.statusIconContainerController = statusIconContainerController;
        this.indicatorGardenPresenter = indicatorGardenPresenter;
        this.indicatorScaleGardener = indicatorScaleGardener;
        this.netspeedViewController = netspeedViewController;
        this.lockscreenShadeTransitionController = lockscreenShadeTransitionController;
        this.privacyItemController = privacyItemController;
        this.batteryIcon = (BatteryMeterView) motionLayout.requireViewById(R.id.batteryRemainingIcon);
        this.privacyContainer = motionLayout.requireViewById(R.id.privacy_container);
        this.iconContainer = (StatusIconContainer) motionLayout.requireViewById(R.id.statusIcons);
        this.shadeCarrierGroup = (ShadeCarrierGroup) motionLayout.requireViewById(R.id.carrier_group);
    }

    @Override // com.android.systemui.plugins.DarkIconDispatcher.DarkReceiver
    public final void onDarkChanged(ArrayList arrayList, float f, int i) {
        NetspeedViewController netspeedViewController;
        NetspeedView view;
        if (BasicRune.STATUS_POP_OVER_PANEL_BAR) {
            boolean isShowingPopOverStatusBar = DeviceState.isShowingPopOverStatusBar();
            SamsungShadeHeaderControllerExtModel samsungShadeHeaderControllerExtModel = this.model;
            samsungShadeHeaderControllerExtModel.isPopOverStatusBar = isShowingPopOverStatusBar;
            if (isShowingPopOverStatusBar) {
                samsungShadeHeaderControllerExtModel.iconAreas = arrayList;
                samsungShadeHeaderControllerExtModel.iconIntensity = f;
                samsungShadeHeaderControllerExtModel.iconTintColor = i;
            } else {
                samsungShadeHeaderControllerExtModel.iconAreas = new ArrayList(Collections.singleton(new Rect(0, 0, 0, 0)));
                samsungShadeHeaderControllerExtModel.iconIntensity = 0.0f;
                samsungShadeHeaderControllerExtModel.iconTintColor = i;
            }
            ((ShadeCarrier) this.shadeCarrierGroup.findViewById(R.id.carrier1)).mCarrierText.setTextColor(samsungShadeHeaderControllerExtModel.iconTintColor);
            this.batteryIcon.onDarkChanged(samsungShadeHeaderControllerExtModel.iconAreas, samsungShadeHeaderControllerExtModel.iconIntensity, samsungShadeHeaderControllerExtModel.iconTintColor);
            if (!BasicRune.STATUS_REAL_TIME_NETWORK_SPEED || (netspeedViewController = this.netspeedViewController) == null || (view = netspeedViewController.getView()) == null) {
                return;
            }
            view.onDarkChanged(samsungShadeHeaderControllerExtModel.iconAreas, samsungShadeHeaderControllerExtModel.iconIntensity, samsungShadeHeaderControllerExtModel.iconTintColor);
        }
    }

    public final void printLog$1(String str) {
        if (this.debug) {
            StringBuilder m = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(StringsKt__StringsKt.padEnd(50, str), " ");
            m.append(this.model);
            Log.d("SamsungShadeHeaderControllerExt", m.toString());
        }
    }

    public final void updateHeaderPadding() {
        int shadeHeaderSidePadding;
        int shadeHeaderSidePadding2;
        boolean isShowingPopOverStatusBar = DeviceState.isShowingPopOverStatusBar();
        SecQSPanelResourcePicker secQSPanelResourcePicker = this.qsPanelResourcePicker;
        IndicatorGardenPresenter indicatorGardenPresenter = this.indicatorGardenPresenter;
        if (isShowingPopOverStatusBar) {
            shadeHeaderSidePadding = indicatorGardenPresenter.cachedGardenModel.paddingLeft;
        } else {
            shadeHeaderSidePadding = secQSPanelResourcePicker.resourcePickHelper.getTargetPicker().getShadeHeaderSidePadding(this.context);
        }
        if (DeviceState.isShowingPopOverStatusBar()) {
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
            sb.append(" l " + i5 + " >> " + i);
            samsungShadeHeaderControllerExtModel.leftPadding = i;
            z = true;
        } else {
            z = false;
        }
        int i6 = samsungShadeHeaderControllerExtModel.topPadding;
        if (i6 != i2) {
            sb.append(" t " + i6 + " >> " + i2);
            samsungShadeHeaderControllerExtModel.topPadding = i2;
            z = true;
        }
        int i7 = samsungShadeHeaderControllerExtModel.rightPadding;
        if (i7 != i3) {
            sb.append(" r " + i7 + " >> " + i3);
            samsungShadeHeaderControllerExtModel.rightPadding = i3;
            z = true;
        }
        int i8 = samsungShadeHeaderControllerExtModel.bottomPadding;
        if (i8 != i4) {
            sb.append(" b " + i8 + " >> " + i4);
            samsungShadeHeaderControllerExtModel.bottomPadding = i4;
        } else {
            z2 = z;
        }
        if (z2) {
            printLog$1(sb.toString());
            this.headerView.setPaddingRelative(samsungShadeHeaderControllerExtModel.leftPadding, samsungShadeHeaderControllerExtModel.topPadding, samsungShadeHeaderControllerExtModel.rightPadding, samsungShadeHeaderControllerExtModel.bottomPadding);
        }
    }
}
