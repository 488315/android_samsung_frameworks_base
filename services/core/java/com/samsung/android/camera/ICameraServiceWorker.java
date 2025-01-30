package com.samsung.android.camera;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;

/* loaded from: classes2.dex */
public interface ICameraServiceWorker extends IInterface {
    public static final String DESCRIPTOR = "com$samsung$android$camera$ICameraServiceWorker".replace('$', '.');

    public class Default implements ICameraServiceWorker {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    IBinder acquireRequestInjector();

    boolean getDeviceInjectorOverride(String str, int i);

    int getDeviceOrientationForDeviceInjector(String str, int i);

    String getInterfaceHash();

    int getInterfaceVersion();

    void notifyCameraSessionEvent(int i, String str);

    void notifyCameraState(String str, int i, int i2, String str2, int i3);

    void pingForUpdate();

    String queryPackageName(int i, int i2);

    void setDeviceOrientationListener(boolean z);

    void storeLoggingData(int i, String str);

    public abstract class Stub extends Binder implements ICameraServiceWorker {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, ICameraServiceWorker.DESCRIPTOR);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            String str = ICameraServiceWorker.DESCRIPTOR;
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(str);
            }
            switch (i) {
                case 16777214:
                    parcel2.writeNoException();
                    parcel2.writeString(getInterfaceHash());
                    return true;
                case 16777215:
                    parcel2.writeNoException();
                    parcel2.writeInt(getInterfaceVersion());
                    return true;
                case 1598968902:
                    parcel2.writeString(str);
                    return true;
                default:
                    switch (i) {
                        case 1:
                            pingForUpdate();
                            return true;
                        case 2:
                            String readString = parcel.readString();
                            int readInt = parcel.readInt();
                            int readInt2 = parcel.readInt();
                            String readString2 = parcel.readString();
                            int readInt3 = parcel.readInt();
                            parcel.enforceNoDataAvail();
                            notifyCameraState(readString, readInt, readInt2, readString2, readInt3);
                            return true;
                        case 3:
                            int readInt4 = parcel.readInt();
                            int readInt5 = parcel.readInt();
                            parcel.enforceNoDataAvail();
                            String queryPackageName = queryPackageName(readInt4, readInt5);
                            parcel2.writeNoException();
                            parcel2.writeString(queryPackageName);
                            return true;
                        case 4:
                            IBinder acquireRequestInjector = acquireRequestInjector();
                            parcel2.writeNoException();
                            parcel2.writeStrongBinder(acquireRequestInjector);
                            return true;
                        case 5:
                            int readInt6 = parcel.readInt();
                            String readString3 = parcel.readString();
                            parcel.enforceNoDataAvail();
                            notifyCameraSessionEvent(readInt6, readString3);
                            return true;
                        case 6:
                            boolean readBoolean = parcel.readBoolean();
                            parcel.enforceNoDataAvail();
                            setDeviceOrientationListener(readBoolean);
                            parcel2.writeNoException();
                            return true;
                        case 7:
                            String readString4 = parcel.readString();
                            int readInt7 = parcel.readInt();
                            parcel.enforceNoDataAvail();
                            int deviceOrientationForDeviceInjector = getDeviceOrientationForDeviceInjector(readString4, readInt7);
                            parcel2.writeNoException();
                            parcel2.writeInt(deviceOrientationForDeviceInjector);
                            return true;
                        case 8:
                            String readString5 = parcel.readString();
                            int readInt8 = parcel.readInt();
                            parcel.enforceNoDataAvail();
                            boolean deviceInjectorOverride = getDeviceInjectorOverride(readString5, readInt8);
                            parcel2.writeNoException();
                            parcel2.writeBoolean(deviceInjectorOverride);
                            return true;
                        case 9:
                            int readInt9 = parcel.readInt();
                            String readString6 = parcel.readString();
                            parcel.enforceNoDataAvail();
                            storeLoggingData(readInt9, readString6);
                            return true;
                        default:
                            return super.onTransact(i, parcel, parcel2, i2);
                    }
            }
        }

        public class Proxy implements ICameraServiceWorker {
            public IBinder mRemote;

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }
        }
    }
}
