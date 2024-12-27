package com.android.server.chimera;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.os.IDeviceIdleController;
import android.os.PowerManager;
import android.os.RemoteException;

import com.android.server.am.mars.filter.filter.DefaultAppFilter;

import java.util.Arrays;
import java.util.function.Function;

public final /* synthetic */ class SystemRepository$$ExternalSyntheticLambda1 implements Function {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ SystemRepository$$ExternalSyntheticLambda1(int i) {
        this.$r8$classId = i;
    }

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                return ((AccessibilityServiceInfo) obj).getResolveInfo().serviceInfo.packageName;
            case 1:
                return ((DefaultAppFilter) obj).mDefaultHomePackage;
            case 2:
                try {
                    return ((IDeviceIdleController) obj).getFullPowerWhitelist();
                } catch (RemoteException e) {
                    e.printStackTrace();
                    return null;
                }
            case 3:
                return Arrays.asList((String[]) obj);
            default:
                return ((PowerManager) obj).getWakeLockPackageList();
        }
    }
}
