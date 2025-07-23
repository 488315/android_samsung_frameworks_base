package com.android.systemui.statusbar.phone.ui;

import android.view.KeyEvent;
import android.view.ViewGroup;
import com.android.systemui.kairos.KairosNetwork;
import com.android.systemui.statusbar.StatusIconDisplayable;
import com.android.systemui.statusbar.connectivity.ui.MobileContextProvider;
import com.android.systemui.statusbar.phone.DemoStatusIcons;
import com.android.systemui.statusbar.phone.StatusBarIconHolder;
import com.android.systemui.statusbar.phone.StatusBarLocation;
import com.android.systemui.statusbar.pipeline.mobile.ui.MobileUiAdapter;
import com.android.systemui.statusbar.pipeline.shared.ui.BTTetherUiAdapter;
import com.android.systemui.statusbar.pipeline.wifi.ui.WifiUiAdapter;
import dagger.Lazy;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class TintedIconManager extends IconManager {
    public int mColor;
    public int mForegroundColor;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class Factory {
        public final CoroutineScope mAppScope;
        public final BTTetherUiAdapter mBTTetherUiAdapter;
        public final KairosNetwork mKairosNetwork;
        public final MobileContextProvider mMobileContextProvider;
        public final MobileUiAdapter mMobileUiAdapter;
        public final Lazy mMobileUiAdapterKairos;
        public final WifiUiAdapter mWifiUiAdapter;

        public Factory(WifiUiAdapter wifiUiAdapter, MobileUiAdapter mobileUiAdapter, MobileContextProvider mobileContextProvider, Lazy lazy, KairosNetwork kairosNetwork, CoroutineScope coroutineScope, BTTetherUiAdapter bTTetherUiAdapter) {
            this.mWifiUiAdapter = wifiUiAdapter;
            this.mMobileUiAdapter = mobileUiAdapter;
            this.mMobileContextProvider = mobileContextProvider;
            this.mMobileUiAdapterKairos = lazy;
            this.mKairosNetwork = kairosNetwork;
            this.mAppScope = coroutineScope;
            this.mBTTetherUiAdapter = bTTetherUiAdapter;
        }

        public final TintedIconManager create(ViewGroup viewGroup, StatusBarLocation statusBarLocation) {
            return new TintedIconManager(viewGroup, statusBarLocation, this.mWifiUiAdapter, this.mMobileUiAdapter, this.mMobileUiAdapterKairos, this.mMobileContextProvider, this.mKairosNetwork, this.mAppScope, this.mBTTetherUiAdapter);
        }
    }

    public TintedIconManager(ViewGroup viewGroup, StatusBarLocation statusBarLocation, WifiUiAdapter wifiUiAdapter, MobileUiAdapter mobileUiAdapter, Lazy lazy, MobileContextProvider mobileContextProvider, KairosNetwork kairosNetwork, CoroutineScope coroutineScope, BTTetherUiAdapter bTTetherUiAdapter) {
        super(viewGroup, statusBarLocation, wifiUiAdapter, mobileUiAdapter, lazy, mobileContextProvider, kairosNetwork, coroutineScope, bTTetherUiAdapter);
    }

    @Override // com.android.systemui.statusbar.phone.ui.IconManager
    public void onIconAdded(int i, String str, boolean z, StatusBarIconHolder statusBarIconHolder) {
        StatusIconDisplayable addHolder = addHolder(i, str, z, statusBarIconHolder);
        addHolder.setStaticDrawableColor(this.mColor, this.mForegroundColor);
        addHolder.setDecorColor(this.mColor);
    }

    public final void setTint(int i, int i2) {
        this.mColor = i;
        this.mForegroundColor = i2;
        for (int i3 = 0; i3 < this.mGroup.getChildCount(); i3++) {
            KeyEvent.Callback childAt = this.mGroup.getChildAt(i3);
            if (childAt instanceof StatusIconDisplayable) {
                StatusIconDisplayable statusIconDisplayable = (StatusIconDisplayable) childAt;
                statusIconDisplayable.setStaticDrawableColor(this.mColor, this.mForegroundColor);
                statusIconDisplayable.setDecorColor(this.mColor);
            }
        }
        DemoStatusIcons demoStatusIcons = this.mDemoStatusIcons;
        if (demoStatusIcons != null) {
            demoStatusIcons.setColor(i, i2);
        }
    }
}
