package com.android.server.companion.virtual.camera;

import android.companion.virtualcamera.IVirtualCameraCallback;
import android.os.Binder;
import android.os.IBinder;
import android.os.Parcel;
import android.view.Surface;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class VirtualCameraConversionUtil$1 extends Binder implements IVirtualCameraCallback {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final /* synthetic */ android.companion.virtual.camera.IVirtualCameraCallback val$camera;

    public VirtualCameraConversionUtil$1(android.companion.virtual.camera.IVirtualCameraCallback iVirtualCameraCallback) {
        this.val$camera = iVirtualCameraCallback;
        attachInterface(this, "android.companion.virtualcamera.IVirtualCameraCallback");
    }

    @Override // android.os.IInterface
    public final IBinder asBinder() {
        return this;
    }

    public final int getMaxTransactionId() {
        return 2;
    }

    public final String getTransactionName(int i) {
        if (i == 1) {
            return "onStreamConfigured";
        }
        if (i == 2) {
            return "onProcessCaptureRequest";
        }
        if (i != 3) {
            return null;
        }
        return "onStreamClosed";
    }

    @Override // android.os.Binder
    public final boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
        int i3;
        if (i >= 1 && i <= 16777215) {
            parcel.enforceInterface("android.companion.virtualcamera.IVirtualCameraCallback");
        }
        if (i == 1598968902) {
            parcel2.writeString("android.companion.virtualcamera.IVirtualCameraCallback");
            return true;
        }
        if (i == 1) {
            int readInt = parcel.readInt();
            Surface surface = (Surface) parcel.readTypedObject(Surface.CREATOR);
            int readInt2 = parcel.readInt();
            int readInt3 = parcel.readInt();
            int readInt4 = parcel.readInt();
            parcel.enforceNoDataAvail();
            android.companion.virtual.camera.IVirtualCameraCallback iVirtualCameraCallback = this.val$camera;
            if (readInt4 != 1) {
                i3 = readInt4 != 35 ? 0 : 35;
            } else {
                i3 = 1;
            }
            iVirtualCameraCallback.onStreamConfigured(readInt, surface, readInt2, readInt3, i3);
        } else if (i == 2) {
            int readInt5 = parcel.readInt();
            int readInt6 = parcel.readInt();
            parcel.enforceNoDataAvail();
            this.val$camera.onProcessCaptureRequest(readInt5, readInt6);
        } else {
            if (i != 3) {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            int readInt7 = parcel.readInt();
            parcel.enforceNoDataAvail();
            this.val$camera.onStreamClosed(readInt7);
        }
        return true;
    }
}
