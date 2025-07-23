package android.telephony;

import android.os.RemoteException;
import com.android.internal.telephony.ITelephony;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes4.dex */
public class NetworkScan {
    public static final int ERROR_INTERRUPTED = 10002;
    public static final int ERROR_INVALID_SCAN = 2;
    public static final int ERROR_INVALID_SCANID = 10001;
    public static final int ERROR_MODEM_ERROR = 1;
    public static final int ERROR_MODEM_UNAVAILABLE = 3;
    public static final int ERROR_RADIO_INTERFACE_ERROR = 10000;
    public static final int ERROR_UNSUPPORTED = 4;
    public static final int SUCCESS = 0;
    private static final String TAG = "NetworkScan";
    private final int mScanId;
    private final int mSubId;

    @Retention(RetentionPolicy.SOURCE)
    public @interface ScanErrorCode {
    }

    public void stopScan() {
        ITelephony iTelephony = getITelephony();
        if (iTelephony == null) {
            com.android.telephony.Rlog.e(TAG, "Failed to get the ITelephony instance.");
        }
        try {
            iTelephony.stopNetworkScan(this.mSubId, this.mScanId);
        } catch (RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "stopNetworkScan  RemoteException", e);
        } catch (IllegalArgumentException unused) {
            com.android.telephony.Rlog.d(TAG, "stopNetworkScan - no active scan for ScanID=" + this.mScanId);
        } catch (RuntimeException e2) {
            com.android.telephony.Rlog.e(TAG, "stopNetworkScan  RuntimeException", e2);
        }
    }

    @Deprecated
    public void stop() throws RemoteException {
        try {
            stopScan();
        } catch (RuntimeException unused) {
            throw new RemoteException("Failed to stop the network scan with id " + this.mScanId);
        }
    }

    public NetworkScan(int i, int i2) {
        this.mScanId = i;
        this.mSubId = i2;
    }

    private ITelephony getITelephony() {
        return ITelephony.Stub.asInterface(TelephonyFrameworkInitializer.getTelephonyServiceManager().getTelephonyServiceRegisterer().get());
    }
}
