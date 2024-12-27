package android.hardware.camera2.utils;

import android.hardware.camera2.CameraAccessException;
import android.os.DeadObjectException;
import android.os.RemoteException;
import android.os.ServiceSpecificException;

/* loaded from: classes2.dex */
public class ExceptionUtils {
    public static CameraAccessException throwAsPublicException(ServiceSpecificException e)
            throws CameraAccessException {
        int reason;
        switch (e.errorCode) {
            case 1:
                throw new SecurityException(e.getMessage(), e);
            case 2:
            case 3:
                throw new IllegalArgumentException(e.getMessage(), e);
            case 4:
                reason = 2;
                break;
            case 5:
            default:
                reason = 3;
                break;
            case 6:
                reason = 1;
                break;
            case 7:
                reason = 4;
                break;
            case 8:
                reason = 5;
                break;
            case 9:
                reason = 1000;
                break;
        }
        throw new CameraAccessException(reason, e.getMessage(), e);
    }

    public static CameraAccessException throwAsPublicException(RemoteException e)
            throws CameraAccessException {
        if (e instanceof DeadObjectException) {
            throw new CameraAccessException(2, "Camera service has died unexpectedly", e);
        }
        throw new UnsupportedOperationException(
                "An unknown RemoteException was thrown which should never happen.", e);
    }

    private ExceptionUtils() {}
}
