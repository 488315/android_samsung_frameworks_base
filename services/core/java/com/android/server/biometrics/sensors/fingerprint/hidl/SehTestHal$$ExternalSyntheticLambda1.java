package com.android.server.biometrics.sensors.fingerprint.hidl;

import android.os.RemoteException;
import android.util.Slog;
import com.android.server.biometrics.sensors.SemTestHalHelper;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class SehTestHal$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ SehTestHal f$0;

    public /* synthetic */ SehTestHal$$ExternalSyntheticLambda1(SehTestHal sehTestHal, int i) {
        this.$r8$classId = i;
        this.f$0 = sehTestHal;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        SehTestHal sehTestHal = this.f$0;
        switch (i) {
            case 0:
                int nextInt = sehTestHal.mRandom.nextInt();
                while (true) {
                    if (!((HashSet) sehTestHal.mEnrollmentIds).contains(Integer.valueOf(nextInt))) {
                        sehTestHal.mCurrentEnrollmentId = nextInt;
                        List list = sehTestHal.mTestHalHelper.mEnrollActionList;
                        StringBuilder sb = new StringBuilder("start enrollTPA: ");
                        sb.append(sehTestHal.mCurrentEnrollmentId);
                        sb.append(", action size = ");
                        ArrayList arrayList = (ArrayList) list;
                        sb.append(arrayList.size());
                        Slog.d("fingerprint.hidl.SehTestHal", sb.toString());
                        Iterator it = arrayList.iterator();
                        while (it.hasNext()) {
                            SemTestHalHelper.Action action = (SemTestHalHelper.Action) it.next();
                            if (sehTestHal.mIsCancellation.get()) {
                                break;
                            } else {
                                try {
                                    Thread.sleep(action.delay);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                action.run();
                                if (action.callbackType == SemTestHalHelper.CallbackType.ENROLL_RESULT && action.value == 0) {
                                    ((HashSet) sehTestHal.mEnrollmentIds).add(Integer.valueOf(sehTestHal.mCurrentEnrollmentId));
                                    sehTestHal.mCurrentEnrollmentId = 0;
                                    sehTestHal.mAuthenticatorID.put(sehTestHal.mCurrentUserID, sehTestHal.mRandom.nextLong());
                                }
                            }
                        }
                        break;
                    } else {
                        nextInt = sehTestHal.mRandom.nextInt();
                    }
                }
                break;
            case 1:
                sehTestHal.mRequestActionTable.clear();
                sehTestHal.mRequestActionTable.put(6, 100040);
                SemTestHalHelper semTestHalHelper = sehTestHal.mTestHalHelper;
                if (semTestHalHelper != null) {
                    semTestHalHelper.initActions();
                    break;
                }
                break;
            default:
                sehTestHal.getClass();
                try {
                    sehTestHal.mTestHal.cancel();
                } catch (RemoteException e2) {
                    e2.printStackTrace();
                }
                sehTestHal.mIsCancellation.set(false);
                sehTestHal.mCurrentEnrollmentId = 0;
                break;
        }
    }
}
