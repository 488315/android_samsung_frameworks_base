package com.android.systemui.statusbar.phone;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.ArraySet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.core.os.LocaleListCompatWrapper$$ExternalSyntheticOutline0;
import com.android.internal.statusbar.StatusBarIcon;
import com.android.systemui.R;
import com.android.systemui.demomode.DemoModeCommandReceiver;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.Flags;
import com.android.systemui.plugins.DarkIconDispatcher;
import com.android.systemui.statusbar.StatusBarIconView;
import com.android.systemui.statusbar.StatusBarMobileView;
import com.android.systemui.statusbar.StatusBarWifiView;
import com.android.systemui.statusbar.StatusIconDisplayable;
import com.android.systemui.statusbar.connectivity.ui.MobileContextProvider;
import com.android.systemui.statusbar.phone.StatusBarIconHolder;
import com.android.systemui.statusbar.phone.StatusBarSignalPolicy;
import com.android.systemui.statusbar.pipeline.StatusBarPipelineFlags;
import com.android.systemui.statusbar.pipeline.mobile.ui.MobileUiAdapter;
import com.android.systemui.statusbar.pipeline.mobile.ui.binder.MobileIconsBinder;
import com.android.systemui.statusbar.pipeline.mobile.ui.view.ModernStatusBarMobileView;
import com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.MobileIconsViewModel;
import com.android.systemui.statusbar.pipeline.satellite.ui.DeviceBasedSatelliteBindableIcon$initializer$1;
import com.android.systemui.statusbar.pipeline.shared.ui.BTTetherUiAdapter;
import com.android.systemui.statusbar.pipeline.shared.ui.view.SingleBindableStatusBarIconView;
import com.android.systemui.statusbar.pipeline.wifi.ui.WifiUiAdapter;
import com.android.systemui.statusbar.pipeline.wifi.ui.view.ModernStatusBarWifiView;
import com.android.systemui.statusbar.pipeline.wifi.ui.viewmodel.LocationBasedWifiViewModel;
import com.sec.ims.settings.ImsProfile;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.function.Predicate;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public interface StatusBarIconController {

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class DarkIconManager extends IconManager {
        public final DarkIconDispatcher mDarkIconDispatcher;
        public final int mIconHPadding;

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public final class Factory {
            public final BTTetherUiAdapter mBTTetherUiAdapter;
            public final DarkIconDispatcher mDarkIconDispatcher;
            public final MobileContextProvider mMobileContextProvider;
            public final MobileUiAdapter mMobileUiAdapter;
            public final StatusBarPipelineFlags mStatusBarPipelineFlags;
            public final WifiUiAdapter mWifiUiAdapter;

            public Factory(StatusBarPipelineFlags statusBarPipelineFlags, WifiUiAdapter wifiUiAdapter, MobileContextProvider mobileContextProvider, MobileUiAdapter mobileUiAdapter, BTTetherUiAdapter bTTetherUiAdapter, DarkIconDispatcher darkIconDispatcher) {
                this.mStatusBarPipelineFlags = statusBarPipelineFlags;
                this.mWifiUiAdapter = wifiUiAdapter;
                this.mMobileContextProvider = mobileContextProvider;
                this.mMobileUiAdapter = mobileUiAdapter;
                this.mBTTetherUiAdapter = bTTetherUiAdapter;
                this.mDarkIconDispatcher = darkIconDispatcher;
            }
        }

        public DarkIconManager(LinearLayout linearLayout, StatusBarLocation statusBarLocation, StatusBarPipelineFlags statusBarPipelineFlags, WifiUiAdapter wifiUiAdapter, MobileUiAdapter mobileUiAdapter, MobileContextProvider mobileContextProvider, BTTetherUiAdapter bTTetherUiAdapter, DarkIconDispatcher darkIconDispatcher) {
            super(linearLayout, statusBarLocation, statusBarPipelineFlags, wifiUiAdapter, mobileUiAdapter, mobileContextProvider, bTTetherUiAdapter);
            this.mIconHPadding = this.mContext.getResources().getDimensionPixelSize(R.dimen.status_bar_icon_padding);
            this.mDarkIconDispatcher = darkIconDispatcher;
        }

        @Override // com.android.systemui.statusbar.phone.StatusBarIconController.IconManager
        public final void destroy() {
            int i = 0;
            while (true) {
                ViewGroup viewGroup = this.mGroup;
                if (i >= viewGroup.getChildCount()) {
                    viewGroup.removeAllViews();
                    return;
                } else {
                    this.mDarkIconDispatcher.removeDarkReceiver((DarkIconDispatcher.DarkReceiver) viewGroup.getChildAt(i));
                    i++;
                }
            }
        }

        @Override // com.android.systemui.statusbar.phone.StatusBarIconController.IconManager
        public final void exitDemoMode() {
            this.mDarkIconDispatcher.removeDarkReceiver(this.mDemoStatusIcons);
            super.exitDemoMode();
        }

        @Override // com.android.systemui.statusbar.phone.StatusBarIconController.IconManager
        public final LinearLayout.LayoutParams onCreateLayoutParams() {
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, this.mIconSize);
            int i = this.mIconHPadding;
            layoutParams.setMargins(i, 0, i, 0);
            return layoutParams;
        }

        @Override // com.android.systemui.statusbar.phone.StatusBarIconController.IconManager
        public final void onIconAdded(int i, String str, boolean z, StatusBarIconHolder statusBarIconHolder) {
            this.mDarkIconDispatcher.addDarkReceiver(addHolder(i, str, z, statusBarIconHolder));
        }

        @Override // com.android.systemui.statusbar.phone.StatusBarIconController.IconManager
        public final void onRemoveIcon(int i) {
            this.mDarkIconDispatcher.removeDarkReceiver((DarkIconDispatcher.DarkReceiver) this.mGroup.getChildAt(i));
            super.onRemoveIcon(i);
        }

        @Override // com.android.systemui.statusbar.phone.StatusBarIconController.IconManager
        public final void onSetIcon(int i, StatusBarIcon statusBarIcon) {
            super.onSetIcon(i, statusBarIcon);
            this.mDarkIconDispatcher.applyDark((DarkIconDispatcher.DarkReceiver) this.mGroup.getChildAt(i));
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
        public final StatusBarPipelineFlags mStatusBarPipelineFlags;
        public final LocationBasedWifiViewModel mWifiViewModel;
        public boolean mShouldLog = false;
        public final boolean mDemoable = true;
        public final ArrayList mBlockList = new ArrayList();

        public IconManager(ViewGroup viewGroup, StatusBarLocation statusBarLocation, StatusBarPipelineFlags statusBarPipelineFlags, WifiUiAdapter wifiUiAdapter, MobileUiAdapter mobileUiAdapter, MobileContextProvider mobileContextProvider, BTTetherUiAdapter bTTetherUiAdapter) {
            ArrayList arrayList = new ArrayList();
            this.excludeSlotsForPadding = arrayList;
            this.mAdditionalScaleFactor = 0.0f;
            this.mGroup = viewGroup;
            this.mStatusBarPipelineFlags = statusBarPipelineFlags;
            this.mMobileContextProvider = mobileContextProvider;
            Context context = viewGroup.getContext();
            this.mContext = context;
            this.mIconSize = context.getResources().getDimensionPixelSize(17106181);
            this.mLocation = statusBarLocation;
            statusBarPipelineFlags.getClass();
            Flags flags = Flags.INSTANCE;
            flags.getClass();
            FeatureFlags featureFlags = statusBarPipelineFlags.featureFlags;
            featureFlags.getClass();
            statusBarPipelineFlags.useNewMobileIcons();
            MobileIconsViewModel mobileIconsViewModel = mobileUiAdapter.mobileIconsViewModel;
            this.mMobileIconsViewModel = mobileIconsViewModel;
            MobileIconsBinder.bind(viewGroup, mobileIconsViewModel);
            flags.getClass();
            featureFlags.getClass();
            statusBarPipelineFlags.useNewWifiIcon();
            this.mWifiViewModel = wifiUiAdapter.bindGroup(viewGroup, statusBarLocation);
            bTTetherUiAdapter.bindGroup(viewGroup);
            arrayList.add(context.getString(17042925));
            arrayList.add(context.getString(17042926));
            arrayList.add(context.getString(17042905));
        }

        public final StatusIconDisplayable addHolder(int i, String str, boolean z, StatusBarIconHolder statusBarIconHolder) {
            if (this.mBlockList.contains(str)) {
                z = true;
            }
            int type = statusBarIconHolder.getType();
            if (type == 0) {
                return addIcon(i, str, z, statusBarIconHolder.icon);
            }
            if (type == 1) {
                return addMobileIcon(i, str, null);
            }
            if (type == 2) {
                return addWifiIcon(i, str, null);
            }
            StatusBarPipelineFlags statusBarPipelineFlags = this.mStatusBarPipelineFlags;
            ViewGroup viewGroup = this.mGroup;
            Context context = this.mContext;
            if (type != 3) {
                if (type != 4) {
                    if (type != 5) {
                        return null;
                    }
                    SingleBindableStatusBarIconView createAndBind = ((DeviceBasedSatelliteBindableIcon$initializer$1) ((StatusBarIconHolder.BindableIconHolder) statusBarIconHolder).initializer).createAndBind(context);
                    viewGroup.addView(createAndBind, i, onCreateLayoutParams());
                    return createAndBind;
                }
                statusBarPipelineFlags.useNewWifiIcon();
                LocationBasedWifiViewModel locationBasedWifiViewModel = this.mWifiViewModel;
                ModernStatusBarWifiView constructAndBind = ModernStatusBarWifiView.constructAndBind(context, str, locationBasedWifiViewModel);
                viewGroup.addView(constructAndBind, i, onCreateLayoutParams());
                if (this.mIsInDemoMode) {
                    this.mDemoStatusIcons.addModernWifiView(locationBasedWifiViewModel);
                }
                return constructAndBind;
            }
            int i2 = statusBarIconHolder.tag;
            statusBarPipelineFlags.useNewMobileIcons();
            MobileContextProvider mobileContextProvider = this.mMobileContextProvider;
            Context mobileContextForSub = mobileContextProvider.getMobileContextForSub(i2, context);
            MobileIconsViewModel mobileIconsViewModel = this.mMobileIconsViewModel;
            ModernStatusBarMobileView constructAndBind2 = ModernStatusBarMobileView.constructAndBind(mobileContextForSub, mobileIconsViewModel.logger, str, mobileIconsViewModel.viewModelForSub(i2, this.mLocation, str), mobileIconsViewModel.configuration);
            viewGroup.addView(constructAndBind2, i, onCreateLayoutParams());
            if (this.mIsInDemoMode) {
                Context mobileContextForSub2 = mobileContextProvider.getMobileContextForSub(i2, context);
                DemoStatusIcons demoStatusIcons = this.mDemoStatusIcons;
                demoStatusIcons.getClass();
                Log.d("DemoStatusIcons", "addModernMobileView (subId=" + i2 + ")");
                ModernStatusBarMobileView constructAndBind3 = ModernStatusBarMobileView.constructAndBind(mobileContextForSub2, mobileIconsViewModel.logger, "mobile", demoStatusIcons.mMobileIconsViewModel.viewModelForSub(i2, demoStatusIcons.mLocation, "mobile"), null);
                demoStatusIcons.mModernMobileViews.add(constructAndBind3);
                demoStatusIcons.addView(constructAndBind3, demoStatusIcons.getChildCount(), new LinearLayout.LayoutParams(-2, demoStatusIcons.mIconSize));
            }
            return constructAndBind2;
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
            if (this.excludeSlotsForPadding.stream().noneMatch(new Predicate() { // from class: com.android.systemui.statusbar.phone.StatusBarIconController$IconManager$$ExternalSyntheticLambda0
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return ((String) obj).equals(str2);
                }
            })) {
                statusBarIconView.setPaddingRelative(0, 0, Math.round(r2.getResources().getDimensionPixelSize(R.dimen.status_bar_system_icon_padding_end) * this.mRatio), 0);
            }
            if (statusBarLocation2 == StatusBarLocation.QS) {
                statusBarIconView.mApplyShadowEffect = true;
            }
            statusBarIconView.set(statusBarIcon);
            this.mGroup.addView(statusBarIconView, i, onCreateLayoutParams());
            return statusBarIconView;
        }

        public StatusIconDisplayable addMobileIcon(int i, String str, StatusBarSignalPolicy.MobileIconState mobileIconState) {
            this.mStatusBarPipelineFlags.useNewMobileIcons();
            throw new IllegalStateException("Attempting to add a mobile icon while the new icons are enabled is not supported");
        }

        public StatusIconDisplayable addWifiIcon(int i, String str, StatusBarSignalPolicy.WifiIconState wifiIconState) {
            this.mStatusBarPipelineFlags.useNewWifiIcon();
            throw new IllegalStateException("Attempting to add a wifi icon while the new icons are enabled is not supported");
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
            demoStatusIcons.mMobileViews.clear();
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

        /* JADX WARN: Multi-variable type inference failed */
        public void onRemoveIcon(int i) {
            boolean z = this.mIsInDemoMode;
            ViewGroup viewGroup = this.mGroup;
            if (z) {
                DemoStatusIcons demoStatusIcons = this.mDemoStatusIcons;
                StatusIconDisplayable statusIconDisplayable = (StatusIconDisplayable) viewGroup.getChildAt(i);
                demoStatusIcons.getClass();
                ModernStatusBarMobileView modernStatusBarMobileView = null;
                if (!statusIconDisplayable.getSlot().equals(ImsProfile.PDN_WIFI)) {
                    boolean z2 = statusIconDisplayable instanceof StatusBarMobileView;
                    if (z2) {
                        if (z2) {
                            StatusBarMobileView statusBarMobileView = (StatusBarMobileView) statusIconDisplayable;
                            Iterator it = demoStatusIcons.mMobileViews.iterator();
                            while (true) {
                                if (!it.hasNext()) {
                                    break;
                                }
                                StatusBarMobileView statusBarMobileView2 = (StatusBarMobileView) it.next();
                                if (statusBarMobileView2.getState().subId == statusBarMobileView.getState().subId) {
                                    modernStatusBarMobileView = statusBarMobileView2;
                                    break;
                                }
                            }
                        }
                        if (modernStatusBarMobileView != null) {
                            demoStatusIcons.removeView(modernStatusBarMobileView);
                            demoStatusIcons.mMobileViews.remove(modernStatusBarMobileView);
                        }
                    } else if (statusIconDisplayable instanceof ModernStatusBarMobileView) {
                        ModernStatusBarMobileView modernStatusBarMobileView2 = (ModernStatusBarMobileView) statusIconDisplayable;
                        Iterator it2 = demoStatusIcons.mModernMobileViews.iterator();
                        while (true) {
                            if (!it2.hasNext()) {
                                break;
                            }
                            ModernStatusBarMobileView modernStatusBarMobileView3 = (ModernStatusBarMobileView) it2.next();
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
                } else if (statusIconDisplayable instanceof StatusBarWifiView) {
                    demoStatusIcons.removeView(demoStatusIcons.mWifiView);
                    demoStatusIcons.mWifiView = null;
                } else if (statusIconDisplayable instanceof ModernStatusBarWifiView) {
                    Log.d("DemoStatusIcons", "onRemoveIcon: removing modern wifi view");
                    demoStatusIcons.removeView(demoStatusIcons.mModernWifiView);
                    demoStatusIcons.mModernWifiView = null;
                }
            }
            viewGroup.removeViewAt(i);
        }

        public void onSetIcon(int i, StatusBarIcon statusBarIcon) {
            ((StatusBarIconView) this.mGroup.getChildAt(i)).set(statusBarIcon);
        }

        public void onSetIconHolder(int i, StatusBarIconHolder statusBarIconHolder) {
            int type = statusBarIconHolder.getType();
            if (type == 0) {
                onSetIcon(i, statusBarIconHolder.icon);
                return;
            }
            ViewGroup viewGroup = this.mGroup;
            if (type == 1) {
                View childAt = viewGroup.getChildAt(i);
                if (!(childAt instanceof StatusBarMobileView)) {
                    throw new IllegalStateException("Cannot update ModernStatusBarMobileView outside ofthe new pipeline");
                }
                ((StatusBarMobileView) childAt).applyMobileState(null);
                if (this.mIsInDemoMode) {
                    throw null;
                }
                return;
            }
            if (type != 2) {
                return;
            }
            View childAt2 = viewGroup.getChildAt(i);
            if (childAt2 instanceof StatusBarWifiView) {
                ((StatusBarWifiView) childAt2).applyWifiState(null);
            } else if (!(childAt2 instanceof ModernStatusBarWifiView)) {
                throw new IllegalStateException(LocaleListCompatWrapper$$ExternalSyntheticOutline0.m31m("View at ", i, " must be of type StatusBarWifiView or ModernStatusBarWifiView"));
            }
            if (this.mIsInDemoMode) {
                DemoStatusIcons demoStatusIcons = this.mDemoStatusIcons;
                demoStatusIcons.getClass();
                Log.d("DemoStatusIcons", "updateWifiState: ");
                StatusBarWifiView statusBarWifiView = demoStatusIcons.mWifiView;
                if (statusBarWifiView == null) {
                    demoStatusIcons.addDemoWifiView(null);
                } else {
                    statusBarWifiView.applyWifiState(null);
                }
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public class TintedIconManager extends IconManager {
        public int mColor;

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public final class Factory {
            public final BTTetherUiAdapter mBTTetherUiAdapter;
            public final MobileContextProvider mMobileContextProvider;
            public final MobileUiAdapter mMobileUiAdapter;
            public final StatusBarPipelineFlags mStatusBarPipelineFlags;
            public final WifiUiAdapter mWifiUiAdapter;

            public Factory(StatusBarPipelineFlags statusBarPipelineFlags, WifiUiAdapter wifiUiAdapter, MobileUiAdapter mobileUiAdapter, BTTetherUiAdapter bTTetherUiAdapter, MobileContextProvider mobileContextProvider) {
                this.mStatusBarPipelineFlags = statusBarPipelineFlags;
                this.mWifiUiAdapter = wifiUiAdapter;
                this.mMobileUiAdapter = mobileUiAdapter;
                this.mBTTetherUiAdapter = bTTetherUiAdapter;
                this.mMobileContextProvider = mobileContextProvider;
            }
        }

        public TintedIconManager(ViewGroup viewGroup, StatusBarLocation statusBarLocation, StatusBarPipelineFlags statusBarPipelineFlags, WifiUiAdapter wifiUiAdapter, MobileUiAdapter mobileUiAdapter, MobileContextProvider mobileContextProvider, BTTetherUiAdapter bTTetherUiAdapter) {
            super(viewGroup, statusBarLocation, statusBarPipelineFlags, wifiUiAdapter, mobileUiAdapter, mobileContextProvider, bTTetherUiAdapter);
        }

        @Override // com.android.systemui.statusbar.phone.StatusBarIconController.IconManager
        public void onIconAdded(int i, String str, boolean z, StatusBarIconHolder statusBarIconHolder) {
            StatusIconDisplayable addHolder = addHolder(i, str, z, statusBarIconHolder);
            addHolder.setStaticDrawableColor(this.mColor);
            addHolder.setDecorColor(this.mColor);
        }

        public final void setTint(int i) {
            this.mColor = i;
            int i2 = 0;
            while (true) {
                ViewGroup viewGroup = this.mGroup;
                if (i2 >= viewGroup.getChildCount()) {
                    return;
                }
                KeyEvent.Callback childAt = viewGroup.getChildAt(i2);
                if (childAt instanceof StatusIconDisplayable) {
                    StatusIconDisplayable statusIconDisplayable = (StatusIconDisplayable) childAt;
                    statusIconDisplayable.setStaticDrawableColor(this.mColor);
                    statusIconDisplayable.setDecorColor(this.mColor);
                }
                i2++;
            }
        }
    }

    static ArraySet getIconHideList(Context context, String str) {
        ArraySet arraySet = new ArraySet();
        for (String str2 : str == null ? context.getResources().getStringArray(R.array.config_statusBarIconsToExclude) : str.split(",")) {
            if (!TextUtils.isEmpty(str2)) {
                arraySet.add(str2);
            }
        }
        return arraySet;
    }
}
