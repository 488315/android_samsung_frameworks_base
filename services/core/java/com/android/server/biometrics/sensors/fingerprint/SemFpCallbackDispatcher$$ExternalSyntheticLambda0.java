package com.android.server.biometrics.sensors.fingerprint;

import java.util.ArrayList;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class SemFpCallbackDispatcher$$ExternalSyntheticLambda0
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ SemFpCallbackDispatcher f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ SemFpCallbackDispatcher$$ExternalSyntheticLambda0(
            SemFpCallbackDispatcher semFpCallbackDispatcher, Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = semFpCallbackDispatcher;
        this.f$1 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                SemFpCallbackDispatcher semFpCallbackDispatcher = this.f$0;
                SemFpEventListener semFpEventListener = (SemFpEventListener) this.f$1;
                if (semFpEventListener == null) {
                    semFpCallbackDispatcher.getClass();
                    break;
                } else if (!((ArrayList) semFpCallbackDispatcher.mEventListeners)
                        .contains(semFpEventListener)) {
                    ((ArrayList) semFpCallbackDispatcher.mEventListeners).add(semFpEventListener);
                    break;
                }
                break;
            case 1:
                SemFpCallbackDispatcher semFpCallbackDispatcher2 = this.f$0;
                SemFpEnrollmentListener semFpEnrollmentListener =
                        (SemFpEnrollmentListener) this.f$1;
                if (semFpEnrollmentListener == null) {
                    semFpCallbackDispatcher2.getClass();
                    break;
                } else if (!((ArrayList) semFpCallbackDispatcher2.mEnrollListeners)
                        .contains(semFpEnrollmentListener)) {
                    ((ArrayList) semFpCallbackDispatcher2.mEnrollListeners)
                            .add(semFpEnrollmentListener);
                    break;
                }
                break;
            case 2:
                SemFpCallbackDispatcher semFpCallbackDispatcher3 = this.f$0;
                SemFpResetLockoutDispatcher semFpResetLockoutDispatcher =
                        (SemFpResetLockoutDispatcher) this.f$1;
                if (semFpResetLockoutDispatcher == null) {
                    semFpCallbackDispatcher3.getClass();
                    break;
                } else if (!((ArrayList) semFpCallbackDispatcher3.mResetLockoutListeners)
                        .contains(semFpResetLockoutDispatcher)) {
                    ((ArrayList) semFpCallbackDispatcher3.mResetLockoutListeners)
                            .add(semFpResetLockoutDispatcher);
                    break;
                }
                break;
            case 3:
                SemFpCallbackDispatcher semFpCallbackDispatcher4 = this.f$0;
                SemFpAuthenticationListener semFpAuthenticationListener =
                        (SemFpAuthenticationListener) this.f$1;
                if (semFpAuthenticationListener == null) {
                    semFpCallbackDispatcher4.getClass();
                    break;
                } else if (!((ArrayList) semFpCallbackDispatcher4.mAuthenticationListeners)
                        .contains(semFpAuthenticationListener)) {
                    ((ArrayList) semFpCallbackDispatcher4.mAuthenticationListeners)
                            .add(semFpAuthenticationListener);
                    break;
                }
                break;
            default:
                SemFpCallbackDispatcher semFpCallbackDispatcher5 = this.f$0;
                SemFpEnrollSessionMonitor semFpEnrollSessionMonitor =
                        (SemFpEnrollSessionMonitor) this.f$1;
                if (semFpEnrollSessionMonitor == null) {
                    semFpCallbackDispatcher5.getClass();
                    break;
                } else if (!((ArrayList) semFpCallbackDispatcher5.mChallengeListeners)
                        .contains(semFpEnrollSessionMonitor)) {
                    ((ArrayList) semFpCallbackDispatcher5.mChallengeListeners)
                            .add(semFpEnrollSessionMonitor);
                    break;
                }
                break;
        }
    }
}
