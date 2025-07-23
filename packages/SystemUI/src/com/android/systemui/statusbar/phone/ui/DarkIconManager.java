package com.android.systemui.statusbar.phone.ui;

import android.widget.LinearLayout;
import com.android.internal.statusbar.StatusBarIcon;
import com.android.systemui.R;
import com.android.systemui.kairos.KairosNetwork;
import com.android.systemui.plugins.DarkIconDispatcher;
import com.android.systemui.statusbar.connectivity.ui.MobileContextProvider;
import com.android.systemui.statusbar.phone.StatusBarIconHolder;
import com.android.systemui.statusbar.phone.StatusBarLocation;
import com.android.systemui.statusbar.pipeline.mobile.ui.MobileUiAdapter;
import com.android.systemui.statusbar.pipeline.shared.ui.BTTetherUiAdapter;
import com.android.systemui.statusbar.pipeline.wifi.ui.WifiUiAdapter;
import dagger.Lazy;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class DarkIconManager extends IconManager {
    public final DarkIconDispatcher mDarkIconDispatcher;
    public final int mIconHorizontalMargin;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface Factory {
        DarkIconManager create(LinearLayout linearLayout, StatusBarLocation statusBarLocation, DarkIconDispatcher darkIconDispatcher);
    }

    public DarkIconManager(LinearLayout linearLayout, StatusBarLocation statusBarLocation, WifiUiAdapter wifiUiAdapter, MobileUiAdapter mobileUiAdapter, Lazy lazy, MobileContextProvider mobileContextProvider, KairosNetwork kairosNetwork, CoroutineScope coroutineScope, DarkIconDispatcher darkIconDispatcher, BTTetherUiAdapter bTTetherUiAdapter) {
        super(linearLayout, statusBarLocation, wifiUiAdapter, mobileUiAdapter, lazy, mobileContextProvider, kairosNetwork, coroutineScope, bTTetherUiAdapter);
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
    public final LinearLayout.LayoutParams onCreateLayoutParams(StatusBarIcon.Shape shape) {
        LinearLayout.LayoutParams onCreateLayoutParams = super.onCreateLayoutParams(shape);
        int i = this.mIconHorizontalMargin;
        onCreateLayoutParams.setMargins(i, 0, i, 0);
        return onCreateLayoutParams;
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
