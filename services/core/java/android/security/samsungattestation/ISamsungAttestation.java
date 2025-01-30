package android.security.samsungattestation;

import android.hardware.security.keymint.Certificate;
import android.hardware.security.keymint.KeyParameter;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.system.keystore2.KeyDescriptor;

/* loaded from: classes.dex */
public interface ISamsungAttestation extends IInterface {
    public static final String DESCRIPTOR = "android$security$samsungattestation$ISamsungAttestation".replace('$', '.');

    public class Default implements ISamsungAttestation {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.security.samsungattestation.ISamsungAttestation
        public Certificate[] attestKey(KeyDescriptor keyDescriptor, KeyParameter[] keyParameterArr) {
            return null;
        }
    }

    Certificate[] attestKey(KeyDescriptor keyDescriptor, KeyParameter[] keyParameterArr);

    public abstract class Stub extends Binder implements ISamsungAttestation {
        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "attestKey";
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, ISamsungAttestation.DESCRIPTOR);
        }

        public static ISamsungAttestation asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ISamsungAttestation.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ISamsungAttestation)) {
                return (ISamsungAttestation) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            String str = ISamsungAttestation.DESCRIPTOR;
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(str);
            }
            if (i == 1598968902) {
                parcel2.writeString(str);
                return true;
            }
            if (i == 1) {
                KeyDescriptor keyDescriptor = (KeyDescriptor) parcel.readTypedObject(KeyDescriptor.CREATOR);
                KeyParameter[] keyParameterArr = (KeyParameter[]) parcel.createTypedArray(KeyParameter.CREATOR);
                parcel.enforceNoDataAvail();
                Certificate[] attestKey = attestKey(keyDescriptor, keyParameterArr);
                parcel2.writeNoException();
                parcel2.writeTypedArray(attestKey, 1);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        public class Proxy implements ISamsungAttestation {
            public IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override // android.security.samsungattestation.ISamsungAttestation
            public Certificate[] attestKey(KeyDescriptor keyDescriptor, KeyParameter[] keyParameterArr) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISamsungAttestation.DESCRIPTOR);
                    obtain.writeTypedObject(keyDescriptor, 0);
                    obtain.writeTypedArray(keyParameterArr, 0);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Certificate[]) obtain2.createTypedArray(Certificate.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
