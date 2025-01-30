package vendor.samsung.hardware.displayquality;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface ISehDisplayQuality extends IInterface {
    public static final String DESCRIPTOR = "vendor$samsung$hardware$displayquality$ISehDisplayQuality".replace('$', '.');

    public class Default implements ISehDisplayQuality {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // vendor.samsung.hardware.displayquality.ISehDisplayQuality
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // vendor.samsung.hardware.displayquality.ISehDisplayQuality
        public int setOutdoorVisibilityEnhancerEnabled(boolean z) {
            return 0;
        }
    }

    String getInterfaceHash();

    int getInterfaceVersion();

    int getStatus();

    int setOutdoorVisibilityEnhancerEnabled(boolean z);

    public abstract class Stub extends Binder implements ISehDisplayQuality {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            markVintfStability();
            attachInterface(this, ISehDisplayQuality.DESCRIPTOR);
        }

        public static ISehDisplayQuality asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ISehDisplayQuality.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ISehDisplayQuality)) {
                return (ISehDisplayQuality) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            String str = ISehDisplayQuality.DESCRIPTOR;
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
                        boolean readBoolean = parcel.readBoolean();
                        parcel.enforceNoDataAvail();
                        int outdoorVisibilityEnhancerEnabled = setOutdoorVisibilityEnhancerEnabled(readBoolean);
                        parcel2.writeNoException();
                        parcel2.writeInt(outdoorVisibilityEnhancerEnabled);
                        break;
                    } else if (i == 2) {
                        int status = getStatus();
                        parcel2.writeNoException();
                        parcel2.writeInt(status);
                        break;
                    } else {
                        break;
                    }
            }
            return true;
        }

        public class Proxy implements ISehDisplayQuality {
            public IBinder mRemote;
            public int mCachedVersion = -1;
            public String mCachedHash = "-1";

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override // vendor.samsung.hardware.displayquality.ISehDisplayQuality
            public int setOutdoorVisibilityEnhancerEnabled(boolean z) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISehDisplayQuality.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    if (!this.mRemote.transact(1, obtain, obtain2, 0)) {
                        throw new RemoteException("Method setOutdoorVisibilityEnhancerEnabled is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.displayquality.ISehDisplayQuality
            public int getInterfaceVersion() {
                if (this.mCachedVersion == -1) {
                    Parcel obtain = Parcel.obtain(asBinder());
                    Parcel obtain2 = Parcel.obtain();
                    try {
                        obtain.writeInterfaceToken(ISehDisplayQuality.DESCRIPTOR);
                        this.mRemote.transact(16777215, obtain, obtain2, 0);
                        obtain2.readException();
                        this.mCachedVersion = obtain2.readInt();
                    } finally {
                        obtain2.recycle();
                        obtain.recycle();
                    }
                }
                return this.mCachedVersion;
            }
        }
    }
}
