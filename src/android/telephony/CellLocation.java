package android.telephony;

import android.app.ActivityThread;
import android.app.Application;
import android.os.Bundle;
import android.os.RemoteException;
import android.telephony.cdma.CdmaCellLocation;
import android.telephony.gsm.GsmCellLocation;
import com.android.internal.telephony.ITelephony;

@Deprecated
/* loaded from: classes4.dex */
public abstract class CellLocation {
    public abstract void fillInNotifierBundle(Bundle bundle);

    public abstract boolean isEmpty();

    public abstract void setStateInvalid();

    @Deprecated
    public static void requestLocationUpdate() {
        Application applicationCurrentApplication = ActivityThread.currentApplication();
        if (applicationCurrentApplication == null) {
            return;
        }
        try {
            ITelephony iTelephonyAsInterface = ITelephony.Stub.asInterface(TelephonyFrameworkInitializer.getTelephonyServiceManager().getTelephonyServiceRegisterer().get());
            if (iTelephonyAsInterface != null) {
                iTelephonyAsInterface.updateServiceLocationWithPackageName(applicationCurrentApplication.getOpPackageName());
            }
        } catch (RemoteException unused) {
        }
    }

    public static CellLocation newFromBundle(Bundle bundle) {
        int currentPhoneType = TelephonyManager.getDefault().getCurrentPhoneType();
        if (currentPhoneType == 1) {
            return new GsmCellLocation(bundle);
        }
        if (currentPhoneType != 2) {
            return null;
        }
        return new CdmaCellLocation(bundle);
    }

    public static CellLocation getEmpty() {
        int currentPhoneType = TelephonyManager.getDefault().getCurrentPhoneType();
        if (currentPhoneType == 1) {
            return new GsmCellLocation();
        }
        if (currentPhoneType != 2) {
            return null;
        }
        return new CdmaCellLocation();
    }
}
