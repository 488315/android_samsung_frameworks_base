package android.hardware.light;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;

/* loaded from: classes.dex */
public interface ILights extends IInterface {
    public static final String DESCRIPTOR = "android$hardware$light$ILights".replace('$', '.');

    public class Default implements ILights {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    String getInterfaceHash();

    int getInterfaceVersion();

    HwLight[] getLights();

    void setLightState(int i, HwLightState hwLightState);

    public abstract class Stub extends Binder implements ILights {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            markVintfStability();
            attachInterface(this, ILights.DESCRIPTOR);
        }

        public static ILights asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ILights.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ILights)) {
                return (ILights) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            String str = ILights.DESCRIPTOR;
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
                        parcel.enforceNoDataAvail();
                        setLightState(readInt, hwLightState);
                        parcel2.writeNoException();
                        break;
                    } else if (i == 2) {
                        HwLight[] lights = getLights();
                        parcel2.writeNoException();
                        parcel2.writeTypedArray(lights, 1);
                        break;
                    } else {
                        break;
                    }
            }
            return true;
        }

        public class Proxy implements ILights {
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
        }
    }
}
