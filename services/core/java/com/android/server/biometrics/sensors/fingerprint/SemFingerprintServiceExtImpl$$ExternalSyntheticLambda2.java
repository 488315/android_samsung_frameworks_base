package com.android.server.biometrics.sensors.fingerprint;

import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Slog;

import com.android.server.HermesService$3$$ExternalSyntheticOutline0;
import com.android.server.accessibility.magnification.MagnificationConnectionManager$$ExternalSyntheticOutline0;
import com.android.server.biometrics.Utils;
import com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider;
import com.android.server.biometrics.sensors.fingerprint.aidl.SemTestSehFingerprint;
import com.android.server.biometrics.sensors.fingerprint.aidl.SemTpaTestHal;

import com.samsung.android.biometrics.ISemBiometricSysUiDisplayBrightnessCallback;
import com.samsung.android.biometrics.ISemBiometricSysUiDisplayStateCallback;

import java.util.Iterator;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class SemFingerprintServiceExtImpl$$ExternalSyntheticLambda2
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ SemFingerprintServiceExtImpl$$ExternalSyntheticLambda2(
            int i, Object obj, Object obj2) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = obj2;
    }

    @Override // java.lang.Runnable
    public final void run() {
        SemTpaTestHal semTpaTestHal;
        SemFpProtectiveFilmGuide semFpProtectiveFilmGuide;
        switch (this.$r8$classId) {
            case 0:
                ((SemFingerprintServiceExtImpl) this.f$0).mSysUiDisplayBrightnessCallback =
                        (ISemBiometricSysUiDisplayBrightnessCallback) this.f$1;
                break;
            case 1:
                SemFingerprintServiceExtImpl semFingerprintServiceExtImpl =
                        (SemFingerprintServiceExtImpl) this.f$0;
                String[] strArr = (String[]) this.f$1;
                ServiceProvider serviceProvider = semFingerprintServiceExtImpl.getServiceProvider();
                if (serviceProvider != null) {
                    String[] strArr2 = {strArr[2], strArr[3]};
                    FingerprintProvider fingerprintProvider = (FingerprintProvider) serviceProvider;
                    if (fingerprintProvider.mTpaHalModeEnabled
                            && (semTpaTestHal = fingerprintProvider.mTpaTestHal) != null) {
                        SemTestSehFingerprint semTestSehFingerprint = semTpaTestHal.mSehFingerprint;
                        semTestSehFingerprint.getClass();
                        try {
                            semTestSehFingerprint.mRequestActionTable.put(
                                    Integer.parseInt(strArr2[0]), Integer.parseInt(strArr2[1]));
                            break;
                        } catch (Exception e) {
                            MagnificationConnectionManager$$ExternalSyntheticOutline0.m(
                                    e,
                                    new StringBuilder("setTpaRequestCommandAction: "),
                                    "fingerprint.aidl.SemTestSehFingerprint");
                            return;
                        }
                    }
                }
                break;
            case 2:
                SemFingerprintServiceExtImpl semFingerprintServiceExtImpl2 =
                        (SemFingerprintServiceExtImpl) this.f$0;
                IBinder iBinder = (IBinder) this.f$1;
                semFingerprintServiceExtImpl2.mInjector.getClass();
                boolean z = SemUdfpsHelper.DEBUG;
                SemUdfpsHelper.InstanceHolder.INSTANCE.mOpticalImpl.removeMaskView(iBinder);
                break;
            case 3:
                ((SemFingerprintServiceExtImpl) this.f$0).mSysUiDisplayStateCallback =
                        (ISemBiometricSysUiDisplayStateCallback) this.f$1;
                break;
            default:
                SemFingerprintServiceExtImpl.AnonymousClass2 anonymousClass2 =
                        (SemFingerprintServiceExtImpl.AnonymousClass2) this.f$0;
                Intent intent = (Intent) this.f$1;
                anonymousClass2.getClass();
                String action = intent.getAction();
                Slog.i("FingerprintService.Ext", "onReceive : " + action);
                if ("android.intent.action.USER_REMOVED".equals(action)) {
                    SemFingerprintServiceExtImpl semFingerprintServiceExtImpl3 =
                            SemFingerprintServiceExtImpl.this;
                    semFingerprintServiceExtImpl3.getClass();
                    int intExtra = intent.getIntExtra("android.intent.extra.user_handle", -10000);
                    HermesService$3$$ExternalSyntheticOutline0.m(
                            intExtra, "ACTION_USER_REMOVED: ", "FingerprintService.Ext");
                    if (intExtra != -10000) {
                        Context context = semFingerprintServiceExtImpl3.mContext;
                        semFingerprintServiceExtImpl3.mInjector.getClass();
                        int userOrWorkProfileId = Utils.getUserOrWorkProfileId(context, intExtra);
                        if (userOrWorkProfileId != 0) {
                            Iterator it =
                                    semFingerprintServiceExtImpl3
                                            .mRegistry
                                            .getProviders()
                                            .iterator();
                            while (it.hasNext()) {
                                ((FingerprintProvider) ((ServiceProvider) it.next()))
                                        .onUserRemoved(userOrWorkProfileId);
                            }
                            if (semFingerprintServiceExtImpl3.mHasUltrasonicUdfps
                                    && (semFpProtectiveFilmGuide =
                                                    semFingerprintServiceExtImpl3
                                                            .mFpProtectiveFilmGuide)
                                            != null) {
                                semFpProtectiveFilmGuide.mBadQualityCount.delete(
                                        userOrWorkProfileId);
                                break;
                            }
                        }
                    }
                }
                break;
        }
    }
}
