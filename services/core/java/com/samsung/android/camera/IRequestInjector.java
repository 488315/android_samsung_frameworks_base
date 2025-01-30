package com.samsung.android.camera;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.PersistableBundle;

/* loaded from: classes2.dex */
public interface IRequestInjector extends IInterface {
    public static final String DESCRIPTOR = "com$samsung$android$camera$IRequestInjector".replace('$', '.');

    public class Default implements IRequestInjector {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    void applyRequests(PersistableBundle[] persistableBundleArr);

    void clearRequests();

    String getInterfaceHash();

    int getInterfaceVersion();

    public abstract class Stub extends Binder implements IRequestInjector {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, IRequestInjector.DESCRIPTOR);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            String str = IRequestInjector.DESCRIPTOR;
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(str);
            }
            switch (i) {
                case 16777214:
                    parcel2.writeNoException();
                    parcel2.writeString(getInterfaceHash());
                    break;
                case 16777215:
                    parcel2.writeNoException();
                    parcel2.writeInt(getInterfaceVersion());
                    break;
                case 1598968902:
                    parcel2.writeString(str);
                    break;
                default:
                    if (i == 1) {
                        PersistableBundle[] persistableBundleArr = (PersistableBundle[]) parcel.createTypedArray(PersistableBundle.CREATOR);
                        parcel.enforceNoDataAvail();
                        applyRequests(persistableBundleArr);
                        parcel2.writeNoException();
                        break;
                    } else if (i == 2) {
                        clearRequests();
                        parcel2.writeNoException();
                        break;
                    } else {
                        break;
                    }
            }
            return true;
        }

        public class Proxy implements IRequestInjector {
            public IBinder mRemote;

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }
        }
    }
}
