package android.hardware.camera2.utils;

import android.hardware.camera2.CameraAccessException;
import android.os.DeadObjectException;
import android.os.RemoteException;
import android.os.ServiceSpecificException;

/* loaded from: classes2.dex */
public class ExceptionUtils {
    public static CameraAccessException throwAsPublicException(ServiceSpecificException serviceSpecificException) throws CameraAccessException {
        int i;
        switch (serviceSpecificException.errorCode) {
            case 1:
                throw new SecurityException(serviceSpecificException.getMessage(), serviceSpecificException);
            case 2:
            case 3:
                throw new IllegalArgumentException(serviceSpecificException.getMessage(), serviceSpecificException);
            case 4:
                i = 2;
                break;
            case 5:
            default:
                i = 3;
                break;
            case 6:
                i = 1;
                break;
            case 7:
                i = 4;
                break;
            case 8:
                i = 5;
                break;
            case 9:
                i = 1000;
                break;
        }
        throw new CameraAccessException(i, serviceSpecificException.getMessage(), serviceSpecificException);
    }

    public static CameraAccessException throwAsPublicException(RemoteException remoteException) throws CameraAccessException {
        if (remoteException instanceof DeadObjectException) {
            throw new CameraAccessException(2, "Camera service has died unexpectedly", remoteException);
        }
        throw new UnsupportedOperationException("An unknown RemoteException was thrown which should never happen.", remoteException);
    }

    private ExceptionUtils() {
    }
}
