package com.android.systemui.statusbar.policy;

import android.database.ContentObserver;
import android.os.Handler;
import android.util.Log;
import com.android.keyguard.logging.KeyguardUpdateMonitorLogger$$ExternalSyntheticOutline0;
import com.android.systemui.BasicRune;
import com.android.systemui.BootAnimationFinishedCache;
import com.android.systemui.BootAnimationFinishedCacheImpl;
import com.android.systemui.util.DeviceType;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.settings.SystemSettings;
import com.samsung.android.feature.SemCscFeature;
import kotlin.text.StringsKt__StringsKt;

/* compiled from: qb/100414692 4322fc59b788332aad98beead1998ffd9b673ada786966d91cea3cb6d4467f0e */
/* loaded from: classes3.dex */
public final class SamsungLocationControllerExt {
    public final boolean DEBUG = DeviceType.isEngOrUTBinary();
    public final String SHOW_STATUS_BAR_LOCATION_ICON = SettingsHelper.INDEX_SHOW_STATUS_BAR_LOCATION_ICON;
    public final Handler bgHandler;
    public boolean isShowOnSettingsValue;
    public final SystemSettings systemSettings;

    public SamsungLocationControllerExt(Handler handler, BootAnimationFinishedCache bootAnimationFinishedCache, SystemSettings systemSettings) {
        this.bgHandler = handler;
        this.systemSettings = systemSettings;
        if (BasicRune.STATUS_LAYOUT_SYSTEM_ICONS_LOCATION) {
            ((BootAnimationFinishedCacheImpl) bootAnimationFinishedCache).addListener(new BootAnimationFinishedCache.BootAnimationFinishedListener() { // from class: com.android.systemui.statusbar.policy.SamsungLocationControllerExt.1
                @Override // com.android.systemui.BootAnimationFinishedCache.BootAnimationFinishedListener
                public final void onBootAnimationFinished() {
                    final SamsungLocationControllerExt samsungLocationControllerExt = SamsungLocationControllerExt.this;
                    SamsungLocationControllerExt.access$updateIsShowOnSettingsValue(samsungLocationControllerExt);
                    samsungLocationControllerExt.printLog("onBootAnimationFinished()");
                    SystemSettings systemSettings2 = samsungLocationControllerExt.systemSettings;
                    String str = samsungLocationControllerExt.SHOW_STATUS_BAR_LOCATION_ICON;
                    final Handler handler2 = samsungLocationControllerExt.bgHandler;
                    systemSettings2.registerContentObserverForUserSync(str, new ContentObserver(handler2) { // from class: com.android.systemui.statusbar.policy.SamsungLocationControllerExt$1$onBootAnimationFinished$1
                        @Override // android.database.ContentObserver
                        public final void onChange(boolean z) {
                            SamsungLocationControllerExt.access$updateIsShowOnSettingsValue(samsungLocationControllerExt);
                        }
                    }, -1);
                }
            });
        }
    }

    public static final void access$updateIsShowOnSettingsValue(SamsungLocationControllerExt samsungLocationControllerExt) {
        SystemSettings systemSettings = samsungLocationControllerExt.systemSettings;
        String str = samsungLocationControllerExt.SHOW_STATUS_BAR_LOCATION_ICON;
        boolean z = systemSettings.getIntForUser(str, 1, -2) == 1;
        if (samsungLocationControllerExt.isShowOnSettingsValue != z) {
            samsungLocationControllerExt.isShowOnSettingsValue = z;
            samsungLocationControllerExt.printLog("onChange(" + str + ") >> " + z);
        }
    }

    public final void printLog(String str) {
        if (this.DEBUG) {
            String strPadEnd = StringsKt__StringsKt.padEnd(70, str);
            StringBuilder sb = new StringBuilder(KeyguardUpdateMonitorLogger$$ExternalSyntheticOutline0.m("isShowOnSettingsValue:", this.isShowOnSettingsValue));
            sb.append(", BasicRune.STATUS_LAYOUT_SYSTEM_ICONS_LOCATION:" + BasicRune.STATUS_LAYOUT_SYSTEM_ICONS_LOCATION);
            sb.append(", Operator.isSupportSamsungLocationChip():" + "US".equals(SemCscFeature.getInstance().getString("CountryISO", "")));
            Log.d("SamsungLocationControllerExt", strPadEnd + " " + sb.toString());
        }
    }
}
