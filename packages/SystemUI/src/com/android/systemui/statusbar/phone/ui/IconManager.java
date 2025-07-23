package com.android.systemui.statusbar.phone.ui;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.collection.MutableIntObjectMap;
import com.android.internal.statusbar.StatusBarIcon;
import com.android.systemui.R;
import com.android.systemui.demomode.DemoModeCommandReceiver;
import com.android.systemui.kairos.KairosNetwork;
import com.android.systemui.modes.shared.ModesUiIcons;
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
import com.android.systemui.statusbar.pipeline.shared.ui.BTTetherUiAdapter;
import com.android.systemui.statusbar.pipeline.shared.ui.view.ModernStatusBarView;
import com.android.systemui.statusbar.pipeline.wifi.ui.WifiUiAdapter;
import com.android.systemui.statusbar.pipeline.wifi.ui.view.ModernStatusBarWifiView;
import com.android.systemui.statusbar.pipeline.wifi.ui.viewmodel.LocationBasedWifiViewModel;
import com.android.systemui.util.Assert;
import com.sec.ims.settings.ImsProfile;
import dagger.Lazy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class IconManager implements DemoModeCommandReceiver {
    public final ArrayList excludeSlotsForPadding;
    public float mAdditionalScaleFactor;
    public final CoroutineScope mAppScope;
    public final Map mBindableIcons;
    public final ArrayList mBlockList;
    public final Context mContext;
    public StatusBarIconControllerImpl mController;
    public DemoStatusIcons mDemoStatusIcons;
    public final boolean mDemoable;
    public final ViewGroup mGroup;
    public int mIconSize;
    public boolean mIsInDemoMode;
    public final KairosNetwork mKairosNetwork;
    public final StatusBarLocation mLocation;
    public final MobileContextProvider mMobileContextProvider;
    public final MobileIconsViewModel mMobileIconsViewModel;
    public final Lazy mMobileUiAdapterKairos;
    public float mRatio;
    public boolean mShouldLog;
    public final LocationBasedWifiViewModel mWifiViewModel;

    public IconManager(ViewGroup viewGroup, StatusBarLocation statusBarLocation, WifiUiAdapter wifiUiAdapter, MobileUiAdapter mobileUiAdapter, Lazy lazy, MobileContextProvider mobileContextProvider, KairosNetwork kairosNetwork, CoroutineScope coroutineScope, BTTetherUiAdapter bTTetherUiAdapter) {
        new MutableIntObjectMap();
        this.mBindableIcons = new HashMap();
        this.mShouldLog = false;
        this.mDemoable = true;
        this.mBlockList = new ArrayList();
        ArrayList arrayList = new ArrayList();
        this.excludeSlotsForPadding = arrayList;
        this.mAdditionalScaleFactor = 0.0f;
        this.mGroup = viewGroup;
        this.mMobileContextProvider = mobileContextProvider;
        Context context = viewGroup.getContext();
        this.mContext = context;
        this.mLocation = statusBarLocation;
        this.mKairosNetwork = kairosNetwork;
        this.mAppScope = coroutineScope;
        MobileIconsViewModel mobileIconsViewModel = mobileUiAdapter.mobileIconsViewModel;
        this.mMobileIconsViewModel = mobileIconsViewModel;
        MobileIconsBinder.bind(viewGroup, mobileIconsViewModel);
        this.mMobileUiAdapterKairos = lazy;
        this.mWifiViewModel = wifiUiAdapter.bindGroup(viewGroup, statusBarLocation);
        bTTetherUiAdapter.bindGroup(viewGroup);
        arrayList.add(context.getString(17043278));
        arrayList.add(context.getString(17043279));
        arrayList.add(context.getString(17043257));
    }

    public final StatusIconDisplayable addHolder(int i, String str, boolean z, StatusBarIconHolder statusBarIconHolder) {
        if (this.mBlockList.contains(str)) {
            z = true;
        }
        int type = statusBarIconHolder.getType();
        StatusBarLocation statusBarLocation = this.mLocation;
        if (type == 0) {
            StatusBarIcon statusBarIcon = statusBarIconHolder.icon;
            StatusBarIconView statusBarIconView = new StatusBarIconView(this.mContext, str, null, z);
            float f = this.mRatio;
            if (statusBarLocation == StatusBarLocation.SUB_SCREEN_QUICK_PANEL) {
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
            if (statusBarLocation == StatusBarLocation.QS) {
                statusBarIconView.mApplyShadowEffect = true;
            }
            statusBarIconView.set(statusBarIcon);
            this.mGroup.addView(statusBarIconView, i, onCreateLayoutParams(statusBarIcon.shape));
            return statusBarIconView;
        }
        if (type != 3) {
            if (type == 4) {
                Context context = this.mContext;
                LocationBasedWifiViewModel locationBasedWifiViewModel = this.mWifiViewModel;
                ModernStatusBarWifiView constructAndBind = ModernStatusBarWifiView.constructAndBind(context, str, locationBasedWifiViewModel);
                this.mGroup.addView(constructAndBind, i, onCreateLayoutParams(StatusBarIcon.Shape.WRAP_CONTENT));
                if (this.mIsInDemoMode) {
                    this.mDemoStatusIcons.addModernWifiView(locationBasedWifiViewModel);
                }
                return constructAndBind;
            }
            if (type != 5) {
                return null;
            }
            StatusBarIconHolder.BindableIconHolder bindableIconHolder = (StatusBarIconHolder.BindableIconHolder) statusBarIconHolder;
            ((HashMap) this.mBindableIcons).put(bindableIconHolder.slot, bindableIconHolder);
            ModernStatusBarView createAndBind = bindableIconHolder.initializer.createAndBind(this.mContext);
            this.mGroup.addView(createAndBind, i, onCreateLayoutParams(StatusBarIcon.Shape.WRAP_CONTENT));
            if (this.mIsInDemoMode) {
                this.mDemoStatusIcons.addBindableIcon(bindableIconHolder);
            }
            return createAndBind;
        }
        int i2 = statusBarIconHolder.tag;
        Context context2 = this.mContext;
        MobileContextProvider mobileContextProvider = this.mMobileContextProvider;
        Context mobileContextForSub = mobileContextProvider.getMobileContextForSub(i2, context2);
        MobileIconsViewModel mobileIconsViewModel = this.mMobileIconsViewModel;
        ModernStatusBarMobileView constructAndBind2 = ModernStatusBarMobileView.constructAndBind(mobileContextForSub, mobileIconsViewModel.logger, str, mobileIconsViewModel.viewModelForSub(i2, statusBarLocation, str), mobileIconsViewModel.configuration);
        this.mGroup.addView(constructAndBind2, i, onCreateLayoutParams(StatusBarIcon.Shape.WRAP_CONTENT));
        if (this.mIsInDemoMode) {
            Context mobileContextForSub2 = mobileContextProvider.getMobileContextForSub(i2, this.mContext);
            DemoStatusIcons demoStatusIcons = this.mDemoStatusIcons;
            demoStatusIcons.getClass();
            Log.d("DemoStatusIcons", "addModernMobileView (subId=" + i2 + ")");
            ModernStatusBarMobileView constructAndBind3 = ModernStatusBarMobileView.constructAndBind(mobileContextForSub2, mobileIconsViewModel.logger, "mobile", demoStatusIcons.mMobileIconsViewModel.viewModelForSub(i2, demoStatusIcons.mLocation, "mobile"), demoStatusIcons.mMobileIconsViewModel.configuration);
            demoStatusIcons.mModernMobileViews.add(constructAndBind3);
            demoStatusIcons.addView(constructAndBind3, demoStatusIcons.getChildCount(), new LinearLayout.LayoutParams(-2, demoStatusIcons.mIconSize));
        }
        return constructAndBind2;
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

    public LinearLayout.LayoutParams onCreateLayoutParams(StatusBarIcon.Shape shape) {
        int i = ModesUiIcons.$r8$clinit;
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
                ArrayList arrayList = demoStatusIcons.mModernMobileViews;
                int size = arrayList.size();
                int i2 = 0;
                while (true) {
                    if (i2 >= size) {
                        break;
                    }
                    Object obj = arrayList.get(i2);
                    i2++;
                    ModernStatusBarMobileView modernStatusBarMobileView3 = (ModernStatusBarMobileView) obj;
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
        StatusBarIconView statusBarIconView = (StatusBarIconView) this.mGroup.getChildAt(i);
        int i2 = ModesUiIcons.$r8$clinit;
        statusBarIconView.set(statusBarIcon);
    }

    public void onSetIconHolder(int i, StatusBarIconHolder statusBarIconHolder) {
        if (statusBarIconHolder.getType() != 0) {
            return;
        }
        onSetIcon(i, statusBarIconHolder.icon);
    }

    public final void setBlockList(List list) {
        Assert.isMainThread();
        this.mBlockList.clear();
        this.mBlockList.addAll(list);
        StatusBarIconControllerImpl statusBarIconControllerImpl = this.mController;
        if (statusBarIconControllerImpl != null) {
            destroy();
            statusBarIconControllerImpl.mIconGroups.remove(this);
            statusBarIconControllerImpl.addIconGroup(this);
        }
    }
}
