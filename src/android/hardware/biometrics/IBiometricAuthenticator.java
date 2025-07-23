package android.hardware.biometrics;

import android.hardware.biometrics.IBiometricSensorReceiver;
import android.hardware.biometrics.IInvalidationCallback;
import android.hardware.biometrics.ITestSession;
import android.hardware.biometrics.ITestSessionCallback;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface IBiometricAuthenticator extends IInterface {
    public static final String DESCRIPTOR = "android.hardware.biometrics.IBiometricAuthenticator";

    public static class Default implements IBiometricAuthenticator {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.hardware.biometrics.IBiometricAuthenticator
        public void cancelAuthenticationFromService(IBinder iBinder, String str, long j) throws RemoteException {
        }

        @Override // android.hardware.biometrics.IBiometricAuthenticator
        public ITestSession createTestSession(ITestSessionCallback iTestSessionCallback, String str) throws RemoteException {
            return null;
        }

        @Override // android.hardware.biometrics.IBiometricAuthenticator
        public byte[] dumpSensorServiceStateProto(boolean z) throws RemoteException {
            return null;
        }

        @Override // android.hardware.biometrics.IBiometricAuthenticator
        public long getAuthenticatorId(int i) throws RemoteException {
            return 0L;
        }

        @Override // android.hardware.biometrics.IBiometricAuthenticator
        public int getLockoutModeForUser(int i) throws RemoteException {
            return 0;
        }

        @Override // android.hardware.biometrics.IBiometricAuthenticator
        public SensorPropertiesInternal getSensorProperties(String str) throws RemoteException {
            return null;
        }

        @Override // android.hardware.biometrics.IBiometricAuthenticator
        public boolean hasEnrolledTemplates(int i, String str) throws RemoteException {
            return false;
        }

        @Override // android.hardware.biometrics.IBiometricAuthenticator
        public void invalidateAuthenticatorId(int i, IInvalidationCallback iInvalidationCallback) throws RemoteException {
        }

        @Override // android.hardware.biometrics.IBiometricAuthenticator
        public boolean isHardwareDetected(String str) throws RemoteException {
            return false;
        }

        @Override // android.hardware.biometrics.IBiometricAuthenticator
        public void prepareForAuthentication(boolean z, IBinder iBinder, long j, int i, IBiometricSensorReceiver iBiometricSensorReceiver, String str, long j2, int i2, boolean z2, boolean z3, boolean z4) throws RemoteException {
        }

        @Override // android.hardware.biometrics.IBiometricAuthenticator
        public void resetLockout(IBinder iBinder, String str, int i, byte[] bArr) throws RemoteException {
        }

        @Override // android.hardware.biometrics.IBiometricAuthenticator
        public void startPreparedClient(int i) throws RemoteException {
        }
    }

    void cancelAuthenticationFromService(IBinder iBinder, String str, long j) throws RemoteException;

    ITestSession createTestSession(ITestSessionCallback iTestSessionCallback, String str) throws RemoteException;

    byte[] dumpSensorServiceStateProto(boolean z) throws RemoteException;

    long getAuthenticatorId(int i) throws RemoteException;

    int getLockoutModeForUser(int i) throws RemoteException;

    SensorPropertiesInternal getSensorProperties(String str) throws RemoteException;

    boolean hasEnrolledTemplates(int i, String str) throws RemoteException;

    void invalidateAuthenticatorId(int i, IInvalidationCallback iInvalidationCallback) throws RemoteException;

    boolean isHardwareDetected(String str) throws RemoteException;

    void prepareForAuthentication(boolean z, IBinder iBinder, long j, int i, IBiometricSensorReceiver iBiometricSensorReceiver, String str, long j2, int i2, boolean z2, boolean z3, boolean z4) throws RemoteException;

    void resetLockout(IBinder iBinder, String str, int i, byte[] bArr) throws RemoteException;

    void startPreparedClient(int i) throws RemoteException;

    public static abstract class Stub extends Binder implements IBiometricAuthenticator {
        static final int TRANSACTION_cancelAuthenticationFromService = 6;
        static final int TRANSACTION_createTestSession = 1;
        static final int TRANSACTION_dumpSensorServiceStateProto = 3;
        static final int TRANSACTION_getAuthenticatorId = 11;
        static final int TRANSACTION_getLockoutModeForUser = 9;
        static final int TRANSACTION_getSensorProperties = 2;
        static final int TRANSACTION_hasEnrolledTemplates = 8;
        static final int TRANSACTION_invalidateAuthenticatorId = 10;
        static final int TRANSACTION_isHardwareDetected = 7;
        static final int TRANSACTION_prepareForAuthentication = 4;
        static final int TRANSACTION_resetLockout = 12;
        static final int TRANSACTION_startPreparedClient = 5;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 11;
        }

        public Stub() {
            attachInterface(this, IBiometricAuthenticator.DESCRIPTOR);
        }

        public static IBiometricAuthenticator asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IBiometricAuthenticator.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IBiometricAuthenticator)) {
                return (IBiometricAuthenticator) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "createTestSession";
                case 2:
                    return "getSensorProperties";
                case 3:
                    return "dumpSensorServiceStateProto";
                case 4:
                    return "prepareForAuthentication";
                case 5:
                    return "startPreparedClient";
                case 6:
                    return "cancelAuthenticationFromService";
                case 7:
                    return "isHardwareDetected";
                case 8:
                    return "hasEnrolledTemplates";
                case 9:
                    return "getLockoutModeForUser";
                case 10:
                    return "invalidateAuthenticatorId";
                case 11:
                    return "getAuthenticatorId";
                case 12:
                    return "resetLockout";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            Parcel parcel3;
            if (i < 1 || i > 16777215) {
                parcel3 = parcel;
            } else {
                parcel3 = parcel;
                parcel3.enforceInterface(IBiometricAuthenticator.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IBiometricAuthenticator.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    ITestSessionCallback asInterface = ITestSessionCallback.Stub.asInterface(parcel.readStrongBinder());
                    String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    ITestSession createTestSession = createTestSession(asInterface, readString);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(createTestSession);
                    return true;
                case 2:
                    String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    SensorPropertiesInternal sensorProperties = getSensorProperties(readString2);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(sensorProperties, 1);
                    return true;
                case 3:
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    byte[] dumpSensorServiceStateProto = dumpSensorServiceStateProto(readBoolean);
                    parcel2.writeNoException();
                    parcel2.writeByteArray(dumpSensorServiceStateProto);
                    return true;
                case 4:
                    boolean readBoolean2 = parcel3.readBoolean();
                    IBinder readStrongBinder = parcel3.readStrongBinder();
                    long readLong = parcel.readLong();
                    int readInt = parcel.readInt();
                    IBiometricSensorReceiver asInterface2 = IBiometricSensorReceiver.Stub.asInterface(parcel.readStrongBinder());
                    String readString3 = parcel.readString();
                    long readLong2 = parcel.readLong();
                    int readInt2 = parcel.readInt();
                    boolean readBoolean3 = parcel.readBoolean();
                    boolean readBoolean4 = parcel.readBoolean();
                    boolean readBoolean5 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    prepareForAuthentication(readBoolean2, readStrongBinder, readLong, readInt, asInterface2, readString3, readLong2, readInt2, readBoolean3, readBoolean4, readBoolean5);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    int readInt3 = parcel3.readInt();
                    parcel3.enforceNoDataAvail();
                    startPreparedClient(readInt3);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    IBinder readStrongBinder2 = parcel3.readStrongBinder();
                    String readString4 = parcel3.readString();
                    long readLong3 = parcel3.readLong();
                    parcel3.enforceNoDataAvail();
                    cancelAuthenticationFromService(readStrongBinder2, readString4, readLong3);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    String readString5 = parcel3.readString();
                    parcel3.enforceNoDataAvail();
                    boolean isHardwareDetected = isHardwareDetected(readString5);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isHardwareDetected);
                    return true;
                case 8:
                    int readInt4 = parcel3.readInt();
                    String readString6 = parcel3.readString();
                    parcel3.enforceNoDataAvail();
                    boolean hasEnrolledTemplates = hasEnrolledTemplates(readInt4, readString6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(hasEnrolledTemplates);
                    return true;
                case 9:
                    int readInt5 = parcel3.readInt();
                    parcel3.enforceNoDataAvail();
                    int lockoutModeForUser = getLockoutModeForUser(readInt5);
                    parcel2.writeNoException();
                    parcel2.writeInt(lockoutModeForUser);
                    return true;
                case 10:
                    int readInt6 = parcel3.readInt();
                    IInvalidationCallback asInterface3 = IInvalidationCallback.Stub.asInterface(parcel3.readStrongBinder());
                    parcel3.enforceNoDataAvail();
                    invalidateAuthenticatorId(readInt6, asInterface3);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    int readInt7 = parcel3.readInt();
                    parcel3.enforceNoDataAvail();
                    long authenticatorId = getAuthenticatorId(readInt7);
                    parcel2.writeNoException();
                    parcel2.writeLong(authenticatorId);
                    return true;
                case 12:
                    IBinder readStrongBinder3 = parcel3.readStrongBinder();
                    String readString7 = parcel3.readString();
                    int readInt8 = parcel3.readInt();
                    byte[] createByteArray = parcel3.createByteArray();
                    parcel3.enforceNoDataAvail();
                    resetLockout(readStrongBinder3, readString7, readInt8, createByteArray);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IBiometricAuthenticator {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IBiometricAuthenticator.DESCRIPTOR;
            }

            @Override // android.hardware.biometrics.IBiometricAuthenticator
            public ITestSession createTestSession(ITestSessionCallback iTestSessionCallback, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IBiometricAuthenticator.DESCRIPTOR);
                    obtain.writeStrongInterface(iTestSessionCallback);
                    obtain.writeString(str);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return ITestSession.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.IBiometricAuthenticator
            public SensorPropertiesInternal getSensorProperties(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IBiometricAuthenticator.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return (SensorPropertiesInternal) obtain2.readTypedObject(SensorPropertiesInternal.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.IBiometricAuthenticator
            public byte[] dumpSensorServiceStateProto(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IBiometricAuthenticator.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createByteArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.IBiometricAuthenticator
            public void prepareForAuthentication(boolean z, IBinder iBinder, long j, int i, IBiometricSensorReceiver iBiometricSensorReceiver, String str, long j2, int i2, boolean z2, boolean z3, boolean z4) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IBiometricAuthenticator.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeLong(j);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iBiometricSensorReceiver);
                    obtain.writeString(str);
                    obtain.writeLong(j2);
                    obtain.writeInt(i2);
                    obtain.writeBoolean(z2);
                    obtain.writeBoolean(z3);
                    obtain.writeBoolean(z4);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.IBiometricAuthenticator
            public void startPreparedClient(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IBiometricAuthenticator.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.IBiometricAuthenticator
            public void cancelAuthenticationFromService(IBinder iBinder, String str, long j) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IBiometricAuthenticator.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeString(str);
                    obtain.writeLong(j);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.IBiometricAuthenticator
            public boolean isHardwareDetected(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IBiometricAuthenticator.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.IBiometricAuthenticator
            public boolean hasEnrolledTemplates(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IBiometricAuthenticator.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.IBiometricAuthenticator
            public int getLockoutModeForUser(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IBiometricAuthenticator.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.IBiometricAuthenticator
            public void invalidateAuthenticatorId(int i, IInvalidationCallback iInvalidationCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IBiometricAuthenticator.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iInvalidationCallback);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.IBiometricAuthenticator
            public long getAuthenticatorId(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IBiometricAuthenticator.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.IBiometricAuthenticator
            public void resetLockout(IBinder iBinder, String str, int i, byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IBiometricAuthenticator.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
