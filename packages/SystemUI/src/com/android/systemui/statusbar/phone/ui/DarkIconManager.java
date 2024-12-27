package com.android.systemui.statusbar.phone.ui;

import android.widget.LinearLayout;
import com.android.internal.statusbar.StatusBarIcon;
import com.android.systemui.R;
import com.android.systemui.plugins.DarkIconDispatcher;
import com.android.systemui.statusbar.connectivity.ui.MobileContextProvider;
import com.android.systemui.statusbar.phone.StatusBarIconHolder;
import com.android.systemui.statusbar.phone.StatusBarLocation;
import com.android.systemui.statusbar.pipeline.mobile.ui.MobileUiAdapter;
import com.android.systemui.statusbar.pipeline.shared.ui.BTTetherUiAdapter;
import com.android.systemui.statusbar.pipeline.wifi.ui.WifiUiAdapter;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class DarkIconManager extends IconManager {
    public final DarkIconDispatcher mDarkIconDispatcher;
    public final int mIconHorizontalMargin;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Factory {
        public final BTTetherUiAdapter mBTTetherUiAdapter;
        public final DarkIconDispatcher mDarkIconDispatcher;
        public final MobileContextProvider mMobileContextProvider;
        public final MobileUiAdapter mMobileUiAdapter;
        public final WifiUiAdapter mWifiUiAdapter;

        public Factory(WifiUiAdapter wifiUiAdapter, MobileContextProvider mobileContextProvider, MobileUiAdapter mobileUiAdapter, DarkIconDispatcher darkIconDispatcher, BTTetherUiAdapter bTTetherUiAdapter) {
            this.mWifiUiAdapter = wifiUiAdapter;
            this.mMobileContextProvider = mobileContextProvider;
            this.mMobileUiAdapter = mobileUiAdapter;
            this.mDarkIconDispatcher = darkIconDispatcher;
            this.mBTTetherUiAdapter = bTTetherUiAdapter;
        }
    }

    public DarkIconManager(LinearLayout linearLayout, StatusBarLocation statusBarLocation, WifiUiAdapter wifiUiAdapter, MobileUiAdapter mobileUiAdapter, MobileContextProvider mobileContextProvider, DarkIconDispatcher darkIconDispatcher, BTTetherUiAdapter bTTetherUiAdapter) {
        super(linearLayout, statusBarLocation, wifiUiAdapter, mobileUiAdapter, mobileContextProvider, bTTetherUiAdapter);
        this.mIconHorizontalMargin = this.mContext.getResources().getDimensionPixelSize(R.dimen.status_bar_icon_horizontal_margin);
        this.mDarkIconDispatcher = darkIconDispatcher;
    }

    @Override // com.android.systemui.statusbar.phone.ui.IconManager
    public final void destroy() {
        for (int i = 0; i < this.mGroup.getChildCount(); i++) {
            this.mDarkIconDispatcher.removeDarkReceiver((DarkIconDispatcher.DarkReceiver) this.mGroup.getChildAt(i));
        }
        this.mGroup.removeAllViews();
    }

    @Override // com.android.systemui.statusbar.phone.ui.IconManager
    public final void exitDemoMode() {
        this.mDarkIconDispatcher.removeDarkReceiver(this.mDemoStatusIcons);
        super.exitDemoMode();
    }

    @Override // com.android.systemui.statusbar.phone.ui.IconManager
    public final LinearLayout.LayoutParams onCreateLayoutParams() {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, this.mIconSize);
        int i = this.mIconHorizontalMargin;
        layoutParams.setMargins(i, 0, i, 0);
        return layoutParams;
    }

    @Override // com.android.systemui.statusbar.phone.ui.IconManager
    public final void onIconAdded(int i, String str, boolean z, StatusBarIconHolder statusBarIconHolder) {
        this.mDarkIconDispatcher.addDarkReceiver(addHolder(i, str, z, statusBarIconHolder));
    }

    @Override // com.android.systemui.statusbar.phone.ui.IconManager
    public final void onRemoveIcon(int i) {
        this.mDarkIconDispatcher.removeDarkReceiver((DarkIconDispatcher.DarkReceiver) this.mGroup.getChildAt(i));
        super.onRemoveIcon(i);
    }

    @Override // com.android.systemui.statusbar.phone.ui.IconManager
    public final void onSetIcon(int i, StatusBarIcon statusBarIcon) {
        super.onSetIcon(i, statusBarIcon);
        this.mDarkIconDispatcher.applyDark((DarkIconDispatcher.DarkReceiver) this.mGroup.getChildAt(i));
    }
}
