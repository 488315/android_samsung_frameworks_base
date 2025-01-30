package android.security.securekeygeneration;

import android.hardware.security.keymint.KeyParameter;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;

/* loaded from: classes.dex */
public interface ISecureKeyGeneration extends IInterface {
    public static final String DESCRIPTOR = "android$security$securekeygeneration$ISecureKeyGeneration".replace('$', '.');

    public class Default implements ISecureKeyGeneration {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.security.securekeygeneration.ISecureKeyGeneration
        public SecureKeyInfo generateSecureKey(KeyParameter[] keyParameterArr, KeyParameter[] keyParameterArr2) {
            return null;
        }
    }

    SecureKeyInfo generateSecureKey(KeyParameter[] keyParameterArr, KeyParameter[] keyParameterArr2);

    public abstract class Stub extends Binder implements ISecureKeyGeneration {
        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "generateSecureKey";
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, ISecureKeyGeneration.DESCRIPTOR);
        }

        public static ISecureKeyGeneration asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ISecureKeyGeneration.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ISecureKeyGeneration)) {
                return (ISecureKeyGeneration) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            String str = ISecureKeyGeneration.DESCRIPTOR;
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(str);
            }
            if (i == 1598968902) {
                parcel2.writeString(str);
                return true;
            }
            if (i == 1) {
                KeyParameter[] keyParameterArr = (KeyParameter[]) parcel.createTypedArray(KeyParameter.CREATOR);
                KeyParameter[] keyParameterArr2 = (KeyParameter[]) parcel.createTypedArray(KeyParameter.CREATOR);
                parcel.enforceNoDataAvail();
                SecureKeyInfo generateSecureKey = generateSecureKey(keyParameterArr, keyParameterArr2);
                parcel2.writeNoException();
                parcel2.writeTypedObject(generateSecureKey, 1);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        public class Proxy implements ISecureKeyGeneration {
            public IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override // android.security.securekeygeneration.ISecureKeyGeneration
            public SecureKeyInfo generateSecureKey(KeyParameter[] keyParameterArr, KeyParameter[] keyParameterArr2) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISecureKeyGeneration.DESCRIPTOR);
                    obtain.writeTypedArray(keyParameterArr, 0);
                    obtain.writeTypedArray(keyParameterArr2, 0);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return (SecureKeyInfo) obtain2.readTypedObject(SecureKeyInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
