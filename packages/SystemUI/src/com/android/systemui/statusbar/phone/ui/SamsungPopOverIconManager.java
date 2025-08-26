package com.android.systemui.statusbar.phone.ui;

import android.view.KeyEvent;
import android.view.ViewGroup;
import androidx.compose.foundation.gestures.ContentInViewNode$Request$$ExternalSyntheticOutline0;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import com.android.systemui.kairos.KairosNetwork;
import com.android.systemui.plugins.DarkIconDispatcher;
import com.android.systemui.statusbar.StatusIconDisplayable;
import com.android.systemui.statusbar.connectivity.ui.MobileContextProvider;
import com.android.systemui.statusbar.phone.StatusBarLocation;
import com.android.systemui.statusbar.pipeline.mobile.ui.MobileUiAdapter;
import com.android.systemui.statusbar.pipeline.shared.ui.BTTetherUiAdapter;
import com.android.systemui.statusbar.pipeline.wifi.ui.WifiUiAdapter;
import com.android.systemui.util.DeviceType;
import dagger.Lazy;
import java.util.ArrayList;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsKt;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes3.dex */
public final class SamsungPopOverIconManager extends TintedIconManager {
    public final ViewGroup group;
    public ArrayList iconAreas;
    public float iconIntensity;
    public boolean shouldUseTintIconArea;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public final class Factory {
        public final CoroutineScope mAppScope;
        public final BTTetherUiAdapter mBTTetherUiAdapter;
        public final KairosNetwork mKairosNetwork;
        public final MobileContextProvider mMobileContextProvider;
        public final MobileUiAdapter mMobileUiAdapter;
        public final Lazy mMobileUiAdapterKairos;
        public final WifiUiAdapter mWifiUiAdapter;

        public Factory(WifiUiAdapter wifiUiAdapter, MobileUiAdapter mobileUiAdapter, Lazy lazy, MobileContextProvider mobileContextProvider, KairosNetwork kairosNetwork, CoroutineScope coroutineScope, BTTetherUiAdapter bTTetherUiAdapter) {
            this.mWifiUiAdapter = wifiUiAdapter;
            this.mMobileUiAdapter = mobileUiAdapter;
            this.mMobileUiAdapterKairos = lazy;
            this.mMobileContextProvider = mobileContextProvider;
            this.mKairosNetwork = kairosNetwork;
            this.mAppScope = coroutineScope;
            this.mBTTetherUiAdapter = bTTetherUiAdapter;
        }
    }

    static {
        new Companion(null);
    }

    public SamsungPopOverIconManager(ViewGroup viewGroup, StatusBarLocation statusBarLocation, WifiUiAdapter wifiUiAdapter, MobileUiAdapter mobileUiAdapter, Lazy lazy, MobileContextProvider mobileContextProvider, KairosNetwork kairosNetwork, CoroutineScope coroutineScope, BTTetherUiAdapter bTTetherUiAdapter) {
        super(viewGroup, statusBarLocation, wifiUiAdapter, mobileUiAdapter, lazy, mobileContextProvider, kairosNetwork, coroutineScope, bTTetherUiAdapter);
        this.group = viewGroup;
        this.iconAreas = new ArrayList();
        this.iconIntensity = -1.0f;
    }

    @Override // com.android.systemui.statusbar.phone.ui.TintedIconManager
    public final void setTint(int i, int i2) {
        this.mColor = i;
        this.mForegroundColor = i2;
        int childCount = this.mGroup.getChildCount();
        for (int i3 = 0; i3 < childCount; i3++) {
            KeyEvent.Callback childAt = this.mGroup.getChildAt(i3);
            if (childAt instanceof StatusIconDisplayable) {
                StatusIconDisplayable statusIconDisplayable = (StatusIconDisplayable) childAt;
                if (Intrinsics.areEqual(statusIconDisplayable.getSlot(), "mute")) {
                    float f = this.iconIntensity;
                    if (f <= 0.0f || f >= 1.0f) {
                        String strM = ContentInViewNode$Request$$ExternalSyntheticOutline0.m("updateIconColor(", statusIconDisplayable.getSlot(), ")");
                        if (DeviceType.isEngOrUTBinary()) {
                            String strPadEnd = StringsKt__StringsKt.padEnd(40, strM);
                            boolean z = this.shouldUseTintIconArea;
                            ArrayList arrayList = this.iconAreas;
                            float f2 = this.iconIntensity;
                            String hexString = Integer.toHexString(this.mColor);
                            String hexString2 = Integer.toHexString(this.mForegroundColor);
                            StringBuilder sb = new StringBuilder();
                            sb.append(strPadEnd);
                            sb.append(" shouldUseTintIconArea?");
                            sb.append(z);
                            sb.append(", area:");
                            sb.append(arrayList);
                            sb.append(", intensity:");
                            sb.append(f2);
                            sb.append(", color:");
                            sb.append(hexString);
                            ExifInterface$$ExternalSyntheticOutline0.m(sb, ", fgColor:", hexString2, "SamsungShadeHeaderControllerExt_PopOverIconManager");
                        }
                    }
                }
                if (this.shouldUseTintIconArea) {
                    statusIconDisplayable.setStaticDrawableColor(0, 0);
                    statusIconDisplayable.setDecorColor(0);
                    DarkIconDispatcher.DarkReceiver darkReceiver = (DarkIconDispatcher.DarkReceiver) childAt;
                    darkReceiver.onDarkChanged(this.iconAreas, this.iconIntensity, this.mColor);
                    darkReceiver.onDarkChangedWithContrast(this.iconAreas, this.mColor, this.mForegroundColor);
                } else {
                    statusIconDisplayable.setStaticDrawableColor(this.mColor, this.mForegroundColor);
                    statusIconDisplayable.setDecorColor(this.mColor);
                }
            }
        }
    }
}
