package com.android.server.pm;

import android.os.Binder;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;

import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.ddar.IDualDARPolicy;

import java.util.List;
import java.util.function.Function;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class PersonaServiceHelper$$ExternalSyntheticLambda0
        implements Function {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ int f$0;

    public /* synthetic */ PersonaServiceHelper$$ExternalSyntheticLambda0() {
        this.$r8$classId = 2;
        this.f$0 = 0;
    }

    public /* synthetic */ PersonaServiceHelper$$ExternalSyntheticLambda0(int i, int i2) {
        this.$r8$classId = i2;
        this.f$0 = i;
    }

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        Bundle bundle = null;
        int i = this.$r8$classId;
        int i2 = this.f$0;
        IDualDARPolicy iDualDARPolicy = (IDualDARPolicy) obj;
        switch (i) {
            case 0:
                List list = PersonaServiceHelper.ALLOWED_BLUETOOTH_TARGET;
                try {
                    bundle = iDualDARPolicy.getConfig(new ContextInfo(Binder.getCallingUid(), i2));
                } catch (RemoteException e) {
                    Log.d(
                            "PersonaServiceHelper",
                            "isDERestrictionEnforced exception " + e.getMessage());
                }
                if (bundle == null) {
                    break;
                } else {
                    break;
                }
            case 1:
                List list2 = PersonaServiceHelper.ALLOWED_BLUETOOTH_TARGET;
                try {
                    break;
                } catch (RemoteException e2) {
                    e2.printStackTrace();
                    return null;
                }
            case 2:
                List list3 = PersonaServiceHelper.ALLOWED_BLUETOOTH_TARGET;
                try {
                    break;
                } catch (RemoteException e3) {
                    e3.printStackTrace();
                    return null;
                }
            default:
                List list4 = PersonaServiceHelper.ALLOWED_BLUETOOTH_TARGET;
                try {
                    break;
                } catch (RemoteException e4) {
                    e4.printStackTrace();
                    return null;
                }
        }
        return null;
    }
}
