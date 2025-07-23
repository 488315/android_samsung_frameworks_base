package com.android.systemui.statusbar.pipeline.mobile.data.repository.prod;

import android.net.ConnectivityManager;
import android.net.Uri;
import android.util.Log;
import com.android.systemui.statusbar.pipeline.shared.data.repository.ImsRegStateUtil;
import com.android.systemui.util.SettingsHelper;
import java.util.ArrayList;
import java.util.List;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class MobileConnectionRepositoryImpl$imsRegState$1$$ExternalSyntheticLambda0 implements Function0 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ MobileConnectionRepositoryImpl$imsRegState$1$$ExternalSyntheticLambda0(int i, Object obj, Object obj2) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = obj2;
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        SettingsHelper settingsHelper;
        SettingsHelper settingsHelper2;
        switch (this.$r8$classId) {
            case 0:
                MobileConnectionRepositoryImpl mobileConnectionRepositoryImpl = (MobileConnectionRepositoryImpl) this.f$0;
                int i = mobileConnectionRepositoryImpl.slotId;
                if (i != -1) {
                    ImsRegStateUtil imsRegStateUtil = mobileConnectionRepositoryImpl.imsRegStateUtil;
                    imsRegStateUtil.getClass();
                    Log.d("ImsRegStateUtil", "UNregisterImsRegStateChangedCallback");
                    ((List) ((ArrayList) imsRegStateUtil.imsRegStateChangedCallbacks).get(i)).remove((MobileConnectionRepositoryImpl$imsRegState$1$mImsRegStateChangedCallback$1) this.f$1);
                    imsRegStateUtil.imsRegStates.remove(Integer.valueOf(i));
                    imsRegStateUtil._ePDGConnected.updateState(null, Boolean.valueOf(imsRegStateUtil.ePDGConnected()));
                }
                break;
            case 1:
                ((MobileConnectionRepositoryImpl) this.f$0).telephonyManager.unregisterTelephonyCallback((MobileConnectionRepositoryImpl$callbackEvents$1$1$callback$1) this.f$1);
                break;
            case 2:
                ((ConnectivityManager) this.f$0).unregisterNetworkCallback((MobileConnectionRepositoryImpl$hasPrioritizedNetworkCapabilities$1$callback$1) this.f$1);
                break;
            case 3:
                settingsHelper = ((MobileConnectionRepositoryImpl) this.f$0).settingsHelper;
                final MobileConnectionRepositoryImpl$mobileDataEnabledChanged$1$callback$1 mobileConnectionRepositoryImpl$mobileDataEnabledChanged$1$callback$1 = (MobileConnectionRepositoryImpl$mobileDataEnabledChanged$1$callback$1) this.f$1;
                settingsHelper.unregisterCallback(new SettingsHelper.OnChangedCallback() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionRepositoryImpl$mobileDataEnabledChanged$1$1$1
                    @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
                    public final void onChanged(Uri uri) {
                    }
                });
                break;
            case 4:
                ((MobileConnectionRepositoryImpl) this.f$0).context.unregisterReceiver((MobileConnectionRepositoryImpl$networkName$1$receiver$1) this.f$1);
                break;
            default:
                settingsHelper2 = ((MobileConnectionRepositoryImpl) this.f$0).settingsHelper;
                final MobileConnectionRepositoryImpl$semSatelliteEnabled$1$callback$1 mobileConnectionRepositoryImpl$semSatelliteEnabled$1$callback$1 = (MobileConnectionRepositoryImpl$semSatelliteEnabled$1$callback$1) this.f$1;
                settingsHelper2.unregisterCallback(new SettingsHelper.OnChangedCallback() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionRepositoryImpl$semSatelliteEnabled$1$1$1
                    @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
                    public final void onChanged(Uri uri) {
                    }
                });
                break;
        }
        return Unit.INSTANCE;
    }
}
