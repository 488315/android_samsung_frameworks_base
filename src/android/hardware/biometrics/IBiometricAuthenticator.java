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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IBiometricAuthenticator.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IBiometricAuthenticator)) {
                return (IBiometricAuthenticator) iInterfaceQueryLocalInterface;
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
                    ITestSessionCallback iTestSessionCallbackAsInterface = ITestSessionCallback.Stub.asInterface(parcel.readStrongBinder());
                    String string = parcel.readString();
                    parcel.enforceNoDataAvail();
                    ITestSession iTestSessionCreateTestSession = createTestSession(iTestSessionCallbackAsInterface, string);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(iTestSessionCreateTestSession);
                    return true;
                case 2:
                    String string2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    SensorPropertiesInternal sensorProperties = getSensorProperties(string2);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(sensorProperties, 1);
                    return true;
                case 3:
                    boolean z = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    byte[] bArrDumpSensorServiceStateProto = dumpSensorServiceStateProto(z);
                    parcel2.writeNoException();
                    parcel2.writeByteArray(bArrDumpSensorServiceStateProto);
                    return true;
                case 4:
                    boolean z2 = parcel3.readBoolean();
                    IBinder strongBinder = parcel3.readStrongBinder();
                    long j = parcel.readLong();
                    int i3 = parcel.readInt();
                    IBiometricSensorReceiver iBiometricSensorReceiverAsInterface = IBiometricSensorReceiver.Stub.asInterface(parcel.readStrongBinder());
                    String string3 = parcel.readString();
                    long j2 = parcel.readLong();
                    int i4 = parcel.readInt();
                    boolean z3 = parcel.readBoolean();
                    boolean z4 = parcel.readBoolean();
                    boolean z5 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    prepareForAuthentication(z2, strongBinder, j, i3, iBiometricSensorReceiverAsInterface, string3, j2, i4, z3, z4, z5);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    int i5 = parcel3.readInt();
                    parcel3.enforceNoDataAvail();
                    startPreparedClient(i5);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    IBinder strongBinder2 = parcel3.readStrongBinder();
                    String string4 = parcel3.readString();
                    long j3 = parcel3.readLong();
                    parcel3.enforceNoDataAvail();
                    cancelAuthenticationFromService(strongBinder2, string4, j3);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    String string5 = parcel3.readString();
                    parcel3.enforceNoDataAvail();
                    boolean zIsHardwareDetected = isHardwareDetected(string5);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsHardwareDetected);
                    return true;
                case 8:
                    int i6 = parcel3.readInt();
                    String string6 = parcel3.readString();
                    parcel3.enforceNoDataAvail();
                    boolean zHasEnrolledTemplates = hasEnrolledTemplates(i6, string6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zHasEnrolledTemplates);
                    return true;
                case 9:
                    int i7 = parcel3.readInt();
                    parcel3.enforceNoDataAvail();
                    int lockoutModeForUser = getLockoutModeForUser(i7);
                    parcel2.writeNoException();
                    parcel2.writeInt(lockoutModeForUser);
                    return true;
                case 10:
                    int i8 = parcel3.readInt();
                    IInvalidationCallback iInvalidationCallbackAsInterface = IInvalidationCallback.Stub.asInterface(parcel3.readStrongBinder());
                    parcel3.enforceNoDataAvail();
                    invalidateAuthenticatorId(i8, iInvalidationCallbackAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    int i9 = parcel3.readInt();
                    parcel3.enforceNoDataAvail();
                    long authenticatorId = getAuthenticatorId(i9);
                    parcel2.writeNoException();
                    parcel2.writeLong(authenticatorId);
                    return true;
                case 12:
                    IBinder strongBinder3 = parcel3.readStrongBinder();
                    String string7 = parcel3.readString();
                    int i10 = parcel3.readInt();
                    byte[] bArrCreateByteArray = parcel3.createByteArray();
                    parcel3.enforceNoDataAvail();
                    resetLockout(strongBinder3, string7, i10, bArrCreateByteArray);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IBiometricAuthenticator.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iTestSessionCallback);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return ITestSession.Stub.asInterface(parcelObtain2.readStrongBinder());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.IBiometricAuthenticator
            public SensorPropertiesInternal getSensorProperties(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IBiometricAuthenticator.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (SensorPropertiesInternal) parcelObtain2.readTypedObject(SensorPropertiesInternal.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.IBiometricAuthenticator
            public byte[] dumpSensorServiceStateProto(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IBiometricAuthenticator.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createByteArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.IBiometricAuthenticator
            public void prepareForAuthentication(boolean z, IBinder iBinder, long j, int i, IBiometricSensorReceiver iBiometricSensorReceiver, String str, long j2, int i2, boolean z2, boolean z3, boolean z4) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IBiometricAuthenticator.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iBiometricSensorReceiver);
                    parcelObtain.writeString(str);
                    parcelObtain.writeLong(j2);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeBoolean(z2);
                    parcelObtain.writeBoolean(z3);
                    parcelObtain.writeBoolean(z4);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.IBiometricAuthenticator
            public void startPreparedClient(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IBiometricAuthenticator.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.IBiometricAuthenticator
            public void cancelAuthenticationFromService(IBinder iBinder, String str, long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IBiometricAuthenticator.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeString(str);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.IBiometricAuthenticator
            public boolean isHardwareDetected(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IBiometricAuthenticator.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.IBiometricAuthenticator
            public boolean hasEnrolledTemplates(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IBiometricAuthenticator.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.IBiometricAuthenticator
            public int getLockoutModeForUser(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IBiometricAuthenticator.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.IBiometricAuthenticator
            public void invalidateAuthenticatorId(int i, IInvalidationCallback iInvalidationCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IBiometricAuthenticator.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iInvalidationCallback);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.IBiometricAuthenticator
            public long getAuthenticatorId(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IBiometricAuthenticator.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readLong();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.IBiometricAuthenticator
            public void resetLockout(IBinder iBinder, String str, int i, byte[] bArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IBiometricAuthenticator.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeByteArray(bArr);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
