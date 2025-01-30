package vendor.samsung.hardware.light;

import android.hardware.light.HwLightState;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface ISehLights extends IInterface {
    public static final String DESCRIPTOR = "vendor$samsung$hardware$light$ISehLights".replace('$', '.');

    public class Default implements ISehLights {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // vendor.samsung.hardware.light.ISehLights
        public SehHwLight[] getLights() {
            return null;
        }

        @Override // vendor.samsung.hardware.light.ISehLights
        public void setLightState(int i, HwLightState hwLightState, int i2) {
        }
    }

    String getInterfaceHash();

    int getInterfaceVersion();

    SehHwLight[] getLights();

    void setLightState(int i, HwLightState hwLightState, int i2);

    public abstract class Stub extends Binder implements ISehLights {
        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "setLightState";
            }
            if (i == 2) {
                return "getLights";
            }
            switch (i) {
                case 16777214:
                    return "getInterfaceHash";
                case 16777215:
                    return "getInterfaceVersion";
                default:
                    return null;
            }
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public int getMaxTransactionId() {
            return 16777214;
        }

        public Stub() {
            markVintfStability();
            attachInterface(this, ISehLights.DESCRIPTOR);
        }

        public static ISehLights asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ISehLights.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ISehLights)) {
                return (ISehLights) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            String str = ISehLights.DESCRIPTOR;
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
                        int readInt = parcel.readInt();
                        HwLightState hwLightState = (HwLightState) parcel.readTypedObject(HwLightState.CREATOR);
                        int readInt2 = parcel.readInt();
                        parcel.enforceNoDataAvail();
                        setLightState(readInt, hwLightState, readInt2);
                        parcel2.writeNoException();
                        break;
                    } else if (i == 2) {
                        SehHwLight[] lights = getLights();
                        parcel2.writeNoException();
                        parcel2.writeTypedArray(lights, 1);
                        break;
                    } else {
                        break;
                    }
            }
            return true;
        }

        public class Proxy implements ISehLights {
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

            @Override // vendor.samsung.hardware.light.ISehLights
            public void setLightState(int i, HwLightState hwLightState, int i2) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISehLights.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(hwLightState, 0);
                    obtain.writeInt(i2);
                    if (!this.mRemote.transact(1, obtain, obtain2, 0)) {
                        throw new RemoteException("Method setLightState is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.light.ISehLights
            public SehHwLight[] getLights() {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISehLights.DESCRIPTOR);
                    if (!this.mRemote.transact(2, obtain, obtain2, 0)) {
                        throw new RemoteException("Method getLights is unimplemented.");
                    }
                    obtain2.readException();
                    return (SehHwLight[]) obtain2.createTypedArray(SehHwLight.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
