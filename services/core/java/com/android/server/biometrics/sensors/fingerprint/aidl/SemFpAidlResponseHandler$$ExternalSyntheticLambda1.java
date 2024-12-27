package com.android.server.biometrics.sensors.fingerprint.aidl;

import android.hardware.fingerprint.Fingerprint;

import com.android.server.biometrics.sensors.AuthenticationConsumer;
import com.android.server.biometrics.sensors.fingerprint.SemFpAuthenticationListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class SemFpAidlResponseHandler$$ExternalSyntheticLambda1
        implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ SemFpAidlResponseHandler f$0;

    public /* synthetic */ SemFpAidlResponseHandler$$ExternalSyntheticLambda1(
            SemFpAidlResponseHandler semFpAidlResponseHandler, int i) {
        this.$r8$classId = i;
        this.f$0 = semFpAidlResponseHandler;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = this.$r8$classId;
        SemFpAidlResponseHandler semFpAidlResponseHandler = this.f$0;
        switch (i) {
            case 0:
                semFpAidlResponseHandler.mLockoutHalImpl.resetFailedAttemptsForUser(
                        semFpAidlResponseHandler.mUserId, true);
                break;
            case 1:
                semFpAidlResponseHandler.mLockoutHalImpl.resetFailedAttemptsForUser(
                        semFpAidlResponseHandler.mUserId, false);
                break;
            case 2:
                semFpAidlResponseHandler.onInteractionDetected();
                break;
            case 3:
                AuthenticationConsumer authenticationConsumer = (AuthenticationConsumer) obj;
                semFpAidlResponseHandler.getClass();
                authenticationConsumer.onAuthenticated(
                        new Fingerprint("", 0, semFpAidlResponseHandler.mSensorId), false, null);
                Iterator it =
                        ((ArrayList)
                                        semFpAidlResponseHandler
                                                .mHalCallbackEx
                                                .mAuthenticationListeners)
                                .iterator();
                while (it.hasNext()) {
                    ((SemFpAuthenticationListener) it.next()).onAuthenticationResult(0);
                }
                if (!authenticationConsumer.canIgnoreLockout()) {
                    semFpAidlResponseHandler.mLockoutHalImpl.addFailedAttemptForUser(
                            semFpAidlResponseHandler.mUserId);
                    int lockoutModeForUser =
                            semFpAidlResponseHandler.mLockoutHalImpl.getLockoutModeForUser(
                                    semFpAidlResponseHandler.mUserId);
                    if (lockoutModeForUser != 2) {
                        if (lockoutModeForUser == 1) {
                            semFpAidlResponseHandler.onLockoutTimed(30000L);
                            break;
                        }
                    } else {
                        semFpAidlResponseHandler.onLockoutPermanent();
                        break;
                    }
                }
                break;
            default:
                semFpAidlResponseHandler.onInteractionDetected();
                break;
        }
    }
}
