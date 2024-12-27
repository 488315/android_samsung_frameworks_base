package com.android.systemui.statusbar.phone.ui;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.android.internal.statusbar.StatusBarIcon;
import com.android.systemui.R;
import com.android.systemui.demomode.DemoModeCommandReceiver;
import com.android.systemui.statusbar.StatusBarIconView;
import com.android.systemui.statusbar.StatusIconDisplayable;
import com.android.systemui.statusbar.connectivity.ui.MobileContextProvider;
import com.android.systemui.statusbar.phone.DemoStatusIcons;
import com.android.systemui.statusbar.phone.StatusBarIconHolder;
import com.android.systemui.statusbar.phone.StatusBarLocation;
import com.android.systemui.statusbar.pipeline.mobile.ui.MobileUiAdapter;
import com.android.systemui.statusbar.pipeline.mobile.ui.binder.MobileIconsBinder;
import com.android.systemui.statusbar.pipeline.mobile.ui.view.ModernStatusBarMobileView;
import com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.MobileIconsViewModel;
import com.android.systemui.statusbar.pipeline.satellite.ui.DeviceBasedSatelliteBindableIcon$initializer$1;
import com.android.systemui.statusbar.pipeline.shared.ui.BTTetherUiAdapter;
import com.android.systemui.statusbar.pipeline.wifi.ui.WifiUiAdapter;
import com.android.systemui.statusbar.pipeline.wifi.ui.view.ModernStatusBarWifiView;
import com.android.systemui.statusbar.pipeline.wifi.ui.viewmodel.LocationBasedWifiViewModel;
import com.sec.ims.settings.ImsProfile;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.function.Predicate;

public class IconManager implements DemoModeCommandReceiver {
    public final ArrayList excludeSlotsForPadding;
    public float mAdditionalScaleFactor;
    public final Context mContext;
    public StatusBarIconController mController;
    public DemoStatusIcons mDemoStatusIcons;
    public final ViewGroup mGroup;
    public int mIconSize;
    public boolean mIsInDemoMode;
    public final StatusBarLocation mLocation;
    public final MobileContextProvider mMobileContextProvider;
    public final MobileIconsViewModel mMobileIconsViewModel;
    public float mRatio;
    public final LocationBasedWifiViewModel mWifiViewModel;
    public final Map mBindableIcons = new HashMap();
    public boolean mShouldLog = false;
    public final boolean mDemoable = true;
    public final ArrayList mBlockList = new ArrayList();

    public IconManager(ViewGroup viewGroup, StatusBarLocation statusBarLocation, WifiUiAdapter wifiUiAdapter, MobileUiAdapter mobileUiAdapter, MobileContextProvider mobileContextProvider, BTTetherUiAdapter bTTetherUiAdapter) {
        ArrayList arrayList = new ArrayList();
        this.excludeSlotsForPadding = arrayList;
        this.mAdditionalScaleFactor = 0.0f;
        this.mGroup = viewGroup;
        this.mMobileContextProvider = mobileContextProvider;
        Context context = viewGroup.getContext();
        this.mContext = context;
        this.mLocation = statusBarLocation;
        MobileIconsViewModel mobileIconsViewModel = mobileUiAdapter.mobileIconsViewModel;
        this.mMobileIconsViewModel = mobileIconsViewModel;
        MobileIconsBinder.bind(viewGroup, mobileIconsViewModel);
        this.mWifiViewModel = wifiUiAdapter.bindGroup(viewGroup, statusBarLocation);
        bTTetherUiAdapter.bindGroup(viewGroup);
        arrayList.add(context.getString(17043140));
        arrayList.add(context.getString(17043141));
        arrayList.add(context.getString(17043119));
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r5v3, types: [com.android.systemui.statusbar.StatusIconDisplayable] */
    public final StatusIconDisplayable addHolder(int i, String str, boolean z, StatusBarIconHolder statusBarIconHolder) {
        View constructAndBind;
        if (this.mBlockList.contains(str)) {
            z = true;
        }
        int type = statusBarIconHolder.getType();
        if (type == 0) {
            return addIcon(i, str, z, statusBarIconHolder.icon);
        }
        if (type == 3) {
            int i2 = statusBarIconHolder.tag;
            Context context = this.mContext;
            MobileContextProvider mobileContextProvider = this.mMobileContextProvider;
            Context mobileContextForSub = mobileContextProvider.getMobileContextForSub(i2, context);
            MobileIconsViewModel mobileIconsViewModel = this.mMobileIconsViewModel;
            constructAndBind = ModernStatusBarMobileView.constructAndBind(mobileContextForSub, mobileIconsViewModel.logger, str, mobileIconsViewModel.viewModelForSub(i2, this.mLocation, str), mobileIconsViewModel.configuration);
            this.mGroup.addView(constructAndBind, i, onCreateLayoutParams());
            if (this.mIsInDemoMode) {
                Context mobileContextForSub2 = mobileContextProvider.getMobileContextForSub(i2, this.mContext);
                DemoStatusIcons demoStatusIcons = this.mDemoStatusIcons;
                demoStatusIcons.getClass();
                Log.d("DemoStatusIcons", "addModernMobileView (subId=" + i2 + ")");
                ModernStatusBarMobileView constructAndBind2 = ModernStatusBarMobileView.constructAndBind(mobileContextForSub2, mobileIconsViewModel.logger, "mobile", demoStatusIcons.mMobileIconsViewModel.viewModelForSub(i2, demoStatusIcons.mLocation, "mobile"), demoStatusIcons.mMobileIconsViewModel.configuration);
                demoStatusIcons.mModernMobileViews.add(constructAndBind2);
                demoStatusIcons.addView(constructAndBind2, demoStatusIcons.getChildCount(), new LinearLayout.LayoutParams(-2, demoStatusIcons.mIconSize));
            }
        } else if (type == 4) {
            Context context2 = this.mContext;
            LocationBasedWifiViewModel locationBasedWifiViewModel = this.mWifiViewModel;
            constructAndBind = ModernStatusBarWifiView.constructAndBind(context2, str, locationBasedWifiViewModel);
            this.mGroup.addView(constructAndBind, i, onCreateLayoutParams());
            if (this.mIsInDemoMode) {
                this.mDemoStatusIcons.addModernWifiView(locationBasedWifiViewModel);
            }
        } else {
            if (type != 5) {
                return null;
            }
            StatusBarIconHolder.BindableIconHolder bindableIconHolder = (StatusBarIconHolder.BindableIconHolder) statusBarIconHolder;
            ((HashMap) this.mBindableIcons).put(bindableIconHolder.slot, bindableIconHolder);
            constructAndBind = ((DeviceBasedSatelliteBindableIcon$initializer$1) bindableIconHolder.initializer).createAndBind(this.mContext);
            this.mGroup.addView(constructAndBind, i, onCreateLayoutParams());
            if (this.mIsInDemoMode) {
                this.mDemoStatusIcons.addBindableIcon(bindableIconHolder);
            }
        }
        return constructAndBind;
    }

    public StatusBarIconView addIcon(int i, String str, boolean z, StatusBarIcon statusBarIcon) {
        StatusBarIconView statusBarIconView = new StatusBarIconView(this.mContext, str, null, z);
        float f = this.mRatio;
        StatusBarLocation statusBarLocation = StatusBarLocation.SUB_SCREEN_QUICK_PANEL;
        StatusBarLocation statusBarLocation2 = this.mLocation;
        if (statusBarLocation2 == statusBarLocation) {
            f *= this.mAdditionalScaleFactor;
        }
        statusBarIconView.mIconScaleFactor = f;
        final String str2 = statusBarIconView.mSlot;
        if (this.excludeSlotsForPadding.stream().noneMatch(new Predicate() { // from class: com.android.systemui.statusbar.phone.ui.IconManager$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((String) obj).equals(str2);
            }
        })) {
            statusBarIconView.setPaddingRelative(0, 0, Math.round(this.mContext.getResources().getDimensionPixelSize(R.dimen.status_bar_system_icon_padding_end) * this.mRatio), 0);
        }
        if (statusBarLocation2 == StatusBarLocation.QS) {
            statusBarIconView.mApplyShadowEffect = true;
        }
        statusBarIconView.set(statusBarIcon);
        this.mGroup.addView(statusBarIconView, i, onCreateLayoutParams());
        return statusBarIconView;
    }

    public void destroy() {
        this.mGroup.removeAllViews();
    }

    @Override // com.android.systemui.demomode.DemoModeCommandReceiver
    public final void dispatchDemoCommand(Bundle bundle, String str) {
        if (this.mDemoable) {
            this.mDemoStatusIcons.dispatchDemoCommand(bundle, str);
        }
    }

    public void exitDemoMode() {
        DemoStatusIcons demoStatusIcons = this.mDemoStatusIcons;
        ((ViewGroup) demoStatusIcons.getParent()).removeView(demoStatusIcons);
        this.mDemoStatusIcons = null;
    }

    public LinearLayout.LayoutParams onCreateLayoutParams() {
        return new LinearLayout.LayoutParams(-2, this.mIconSize);
    }

    @Override // com.android.systemui.demomode.DemoModeCommandReceiver
    public final void onDemoModeFinished() {
        DemoStatusIcons demoStatusIcons = this.mDemoStatusIcons;
        if (demoStatusIcons != null) {
            demoStatusIcons.onDemoModeFinished();
            exitDemoMode();
            this.mIsInDemoMode = false;
        }
    }

    public void onIconAdded(int i, String str, boolean z, StatusBarIconHolder statusBarIconHolder) {
        addHolder(i, str, z, statusBarIconHolder);
    }

    public void onRemoveIcon(int i) {
        if (this.mIsInDemoMode) {
            DemoStatusIcons demoStatusIcons = this.mDemoStatusIcons;
            StatusIconDisplayable statusIconDisplayable = (StatusIconDisplayable) this.mGroup.getChildAt(i);
            demoStatusIcons.getClass();
            ModernStatusBarMobileView modernStatusBarMobileView = null;
            if (statusIconDisplayable.getSlot().equals(ImsProfile.PDN_WIFI)) {
                if (statusIconDisplayable instanceof ModernStatusBarWifiView) {
                    Log.d("DemoStatusIcons", "onRemoveIcon: removing modern wifi view");
                    demoStatusIcons.removeView(demoStatusIcons.mModernWifiView);
                    demoStatusIcons.mModernWifiView = null;
                }
            } else if (statusIconDisplayable instanceof ModernStatusBarMobileView) {
                ModernStatusBarMobileView modernStatusBarMobileView2 = (ModernStatusBarMobileView) statusIconDisplayable;
                Iterator it = demoStatusIcons.mModernMobileViews.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    ModernStatusBarMobileView modernStatusBarMobileView3 = (ModernStatusBarMobileView) it.next();
                    if (modernStatusBarMobileView3.subId == modernStatusBarMobileView2.subId) {
                        modernStatusBarMobileView = modernStatusBarMobileView3;
                        break;
                    }
                }
                if (modernStatusBarMobileView != null) {
                    demoStatusIcons.removeView(modernStatusBarMobileView);
                    demoStatusIcons.mModernMobileViews.remove(modernStatusBarMobileView);
                }
            }
        }
        this.mGroup.removeViewAt(i);
    }

    public void onSetIcon(int i, StatusBarIcon statusBarIcon) {
        ((StatusBarIconView) this.mGroup.getChildAt(i)).set(statusBarIcon);
    }

    public void onSetIconHolder(int i, StatusBarIconHolder statusBarIconHolder) {
        if (statusBarIconHolder.getType() != 0) {
            return;
        }
        onSetIcon(i, statusBarIconHolder.icon);
    }
}
