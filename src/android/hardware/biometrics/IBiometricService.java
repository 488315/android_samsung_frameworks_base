package android.hardware.biometrics;

import android.Manifest;
import android.app.ActivityThread;
import android.hardware.biometrics.IBiometricAuthenticator;
import android.hardware.biometrics.IBiometricEnabledOnKeyguardCallback;
import android.hardware.biometrics.IBiometricServiceReceiver;
import android.hardware.biometrics.IInvalidationCallback;
import android.hardware.biometrics.ITestSession;
import android.hardware.biometrics.ITestSessionCallback;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.PermissionEnforcer;
import android.os.RemoteException;
import java.util.List;

/* loaded from: classes2.dex */
public interface IBiometricService extends IInterface {
    public static final String DESCRIPTOR = "android.hardware.biometrics.IBiometricService";

    public static class Default implements IBiometricService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.hardware.biometrics.IBiometricService
        public long authenticate(IBinder iBinder, long j, int i, IBiometricServiceReceiver iBiometricServiceReceiver, String str, PromptInfo promptInfo) throws RemoteException {
            return 0L;
        }

        @Override // android.hardware.biometrics.IBiometricService
        public int canAuthenticate(String str, int i, int i2, int i3) throws RemoteException {
            return 0;
        }

        @Override // android.hardware.biometrics.IBiometricService
        public void cancelAuthentication(IBinder iBinder, String str, long j) throws RemoteException {
        }

        @Override // android.hardware.biometrics.IBiometricService
        public ITestSession createTestSession(int i, ITestSessionCallback iTestSessionCallback, String str) throws RemoteException {
            return null;
        }

        @Override // android.hardware.biometrics.IBiometricService
        public long[] getAuthenticatorIds(int i) throws RemoteException {
            return null;
        }

        @Override // android.hardware.biometrics.IBiometricService
        public int getCurrentModality(String str, int i, int i2, int i3) throws RemoteException {
            return 0;
        }

        @Override // android.hardware.biometrics.IBiometricService
        public int getCurrentStrength(int i) throws RemoteException {
            return 0;
        }

        @Override // android.hardware.biometrics.IBiometricService
        public long getLastAuthenticationTime(int i, int i2) throws RemoteException {
            return 0L;
        }

        @Override // android.hardware.biometrics.IBiometricService
        public List<SensorPropertiesInternal> getSensorProperties(String str) throws RemoteException {
            return null;
        }

        @Override // android.hardware.biometrics.IBiometricService
        public int getSupportedModalities(int i) throws RemoteException {
            return 0;
        }

        @Override // android.hardware.biometrics.IBiometricService
        public boolean hasEnrolledBiometrics(int i, String str) throws RemoteException {
            return false;
        }

        @Override // android.hardware.biometrics.IBiometricService
        public void invalidateAuthenticatorIds(int i, int i2, IInvalidationCallback iInvalidationCallback) throws RemoteException {
        }

        @Override // android.hardware.biometrics.IBiometricService
        public void onReadyForAuthentication(long j, int i) throws RemoteException {
        }

        @Override // android.hardware.biometrics.IBiometricService
        public void registerAuthenticator(int i, int i2, int i3, IBiometricAuthenticator iBiometricAuthenticator) throws RemoteException {
        }

        @Override // android.hardware.biometrics.IBiometricService
        public void registerEnabledOnKeyguardCallback(IBiometricEnabledOnKeyguardCallback iBiometricEnabledOnKeyguardCallback) throws RemoteException {
        }

        @Override // android.hardware.biometrics.IBiometricService
        public void resetLockout(int i, byte[] bArr) throws RemoteException {
        }

        @Override // android.hardware.biometrics.IBiometricService
        public void resetLockoutTimeBound(IBinder iBinder, String str, int i, int i2, byte[] bArr) throws RemoteException {
        }

        @Override // android.hardware.biometrics.IBiometricService
        public PromptInfo semGetPromptInfo(int i) throws RemoteException {
            return null;
        }
    }

    long authenticate(IBinder iBinder, long j, int i, IBiometricServiceReceiver iBiometricServiceReceiver, String str, PromptInfo promptInfo) throws RemoteException;

    int canAuthenticate(String str, int i, int i2, int i3) throws RemoteException;

    void cancelAuthentication(IBinder iBinder, String str, long j) throws RemoteException;

    ITestSession createTestSession(int i, ITestSessionCallback iTestSessionCallback, String str) throws RemoteException;

    long[] getAuthenticatorIds(int i) throws RemoteException;

    int getCurrentModality(String str, int i, int i2, int i3) throws RemoteException;

    int getCurrentStrength(int i) throws RemoteException;

    long getLastAuthenticationTime(int i, int i2) throws RemoteException;

    List<SensorPropertiesInternal> getSensorProperties(String str) throws RemoteException;

    int getSupportedModalities(int i) throws RemoteException;

    boolean hasEnrolledBiometrics(int i, String str) throws RemoteException;

    void invalidateAuthenticatorIds(int i, int i2, IInvalidationCallback iInvalidationCallback) throws RemoteException;

    void onReadyForAuthentication(long j, int i) throws RemoteException;

    void registerAuthenticator(int i, int i2, int i3, IBiometricAuthenticator iBiometricAuthenticator) throws RemoteException;

    void registerEnabledOnKeyguardCallback(IBiometricEnabledOnKeyguardCallback iBiometricEnabledOnKeyguardCallback) throws RemoteException;

    void resetLockout(int i, byte[] bArr) throws RemoteException;

    void resetLockoutTimeBound(IBinder iBinder, String str, int i, int i2, byte[] bArr) throws RemoteException;

    PromptInfo semGetPromptInfo(int i) throws RemoteException;

    public static abstract class Stub extends Binder implements IBiometricService {
        static final int TRANSACTION_authenticate = 3;
        static final int TRANSACTION_canAuthenticate = 5;
        static final int TRANSACTION_cancelAuthentication = 4;
        static final int TRANSACTION_createTestSession = 1;
        static final int TRANSACTION_getAuthenticatorIds = 12;
        static final int TRANSACTION_getCurrentModality = 16;
        static final int TRANSACTION_getCurrentStrength = 15;
        static final int TRANSACTION_getLastAuthenticationTime = 6;
        static final int TRANSACTION_getSensorProperties = 2;
        static final int TRANSACTION_getSupportedModalities = 17;
        static final int TRANSACTION_hasEnrolledBiometrics = 7;
        static final int TRANSACTION_invalidateAuthenticatorIds = 11;
        static final int TRANSACTION_onReadyForAuthentication = 10;
        static final int TRANSACTION_registerAuthenticator = 8;
        static final int TRANSACTION_registerEnabledOnKeyguardCallback = 9;
        static final int TRANSACTION_resetLockout = 14;
        static final int TRANSACTION_resetLockoutTimeBound = 13;
        static final int TRANSACTION_semGetPromptInfo = 18;
        private final PermissionEnforcer mEnforcer;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 17;
        }

        public Stub(PermissionEnforcer permissionEnforcer) {
            attachInterface(this, IBiometricService.DESCRIPTOR);
            if (permissionEnforcer == null) {
                throw new IllegalArgumentException("enforcer cannot be null");
            }
            this.mEnforcer = permissionEnforcer;
        }

        @Deprecated
        public Stub() {
            this(PermissionEnforcer.fromContext(ActivityThread.currentActivityThread().getSystemContext()));
        }

        public static IBiometricService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IBiometricService.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IBiometricService)) {
                return (IBiometricService) queryLocalInterface;
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
                    return "authenticate";
                case 4:
                    return "cancelAuthentication";
                case 5:
                    return "canAuthenticate";
                case 6:
                    return "getLastAuthenticationTime";
                case 7:
                    return "hasEnrolledBiometrics";
                case 8:
                    return "registerAuthenticator";
                case 9:
                    return "registerEnabledOnKeyguardCallback";
                case 10:
                    return "onReadyForAuthentication";
                case 11:
                    return "invalidateAuthenticatorIds";
                case 12:
                    return "getAuthenticatorIds";
                case 13:
                    return "resetLockoutTimeBound";
                case 14:
                    return "resetLockout";
                case 15:
                    return "getCurrentStrength";
                case 16:
                    return "getCurrentModality";
                case 17:
                    return "getSupportedModalities";
                case 18:
                    return "semGetPromptInfo";
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
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IBiometricService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IBiometricService.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int readInt = parcel.readInt();
                    ITestSessionCallback asInterface = ITestSessionCallback.Stub.asInterface(parcel.readStrongBinder());
                    String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    ITestSession createTestSession = createTestSession(readInt, asInterface, readString);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(createTestSession);
                    return true;
                case 2:
                    String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    List<SensorPropertiesInternal> sensorProperties = getSensorProperties(readString2);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(sensorProperties, 1);
                    return true;
                case 3:
                    IBinder readStrongBinder = parcel.readStrongBinder();
                    long readLong = parcel.readLong();
                    int readInt2 = parcel.readInt();
                    IBiometricServiceReceiver asInterface2 = IBiometricServiceReceiver.Stub.asInterface(parcel.readStrongBinder());
                    String readString3 = parcel.readString();
                    PromptInfo promptInfo = (PromptInfo) parcel.readTypedObject(PromptInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    long authenticate = authenticate(readStrongBinder, readLong, readInt2, asInterface2, readString3, promptInfo);
                    parcel2.writeNoException();
                    parcel2.writeLong(authenticate);
                    return true;
                case 4:
                    IBinder readStrongBinder2 = parcel.readStrongBinder();
                    String readString4 = parcel.readString();
                    long readLong2 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    cancelAuthentication(readStrongBinder2, readString4, readLong2);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    String readString5 = parcel.readString();
                    int readInt3 = parcel.readInt();
                    int readInt4 = parcel.readInt();
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int canAuthenticate = canAuthenticate(readString5, readInt3, readInt4, readInt5);
                    parcel2.writeNoException();
                    parcel2.writeInt(canAuthenticate);
                    return true;
                case 6:
                    int readInt6 = parcel.readInt();
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    long lastAuthenticationTime = getLastAuthenticationTime(readInt6, readInt7);
                    parcel2.writeNoException();
                    parcel2.writeLong(lastAuthenticationTime);
                    return true;
                case 7:
                    int readInt8 = parcel.readInt();
                    String readString6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean hasEnrolledBiometrics = hasEnrolledBiometrics(readInt8, readString6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(hasEnrolledBiometrics);
                    return true;
                case 8:
                    int readInt9 = parcel.readInt();
                    int readInt10 = parcel.readInt();
                    int readInt11 = parcel.readInt();
                    IBiometricAuthenticator asInterface3 = IBiometricAuthenticator.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerAuthenticator(readInt9, readInt10, readInt11, asInterface3);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    IBiometricEnabledOnKeyguardCallback asInterface4 = IBiometricEnabledOnKeyguardCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerEnabledOnKeyguardCallback(asInterface4);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    long readLong3 = parcel.readLong();
                    int readInt12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onReadyForAuthentication(readLong3, readInt12);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    int readInt13 = parcel.readInt();
                    int readInt14 = parcel.readInt();
                    IInvalidationCallback asInterface5 = IInvalidationCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    invalidateAuthenticatorIds(readInt13, readInt14, asInterface5);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    int readInt15 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    long[] authenticatorIds = getAuthenticatorIds(readInt15);
                    parcel2.writeNoException();
                    parcel2.writeLongArray(authenticatorIds);
                    return true;
                case 13:
                    IBinder readStrongBinder3 = parcel.readStrongBinder();
                    String readString7 = parcel.readString();
                    int readInt16 = parcel.readInt();
                    int readInt17 = parcel.readInt();
                    byte[] createByteArray = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    resetLockoutTimeBound(readStrongBinder3, readString7, readInt16, readInt17, createByteArray);
                    parcel2.writeNoException();
                    return true;
                case 14:
                    int readInt18 = parcel.readInt();
                    byte[] createByteArray2 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    resetLockout(readInt18, createByteArray2);
                    parcel2.writeNoException();
                    return true;
                case 15:
                    int readInt19 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int currentStrength = getCurrentStrength(readInt19);
                    parcel2.writeNoException();
                    parcel2.writeInt(currentStrength);
                    return true;
                case 16:
                    String readString8 = parcel.readString();
                    int readInt20 = parcel.readInt();
                    int readInt21 = parcel.readInt();
                    int readInt22 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int currentModality = getCurrentModality(readString8, readInt20, readInt21, readInt22);
                    parcel2.writeNoException();
                    parcel2.writeInt(currentModality);
                    return true;
                case 17:
                    int readInt23 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int supportedModalities = getSupportedModalities(readInt23);
                    parcel2.writeNoException();
                    parcel2.writeInt(supportedModalities);
                    return true;
                case 18:
                    int readInt24 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    PromptInfo semGetPromptInfo = semGetPromptInfo(readInt24);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(semGetPromptInfo, 1);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IBiometricService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IBiometricService.DESCRIPTOR;
            }

            @Override // android.hardware.biometrics.IBiometricService
            public ITestSession createTestSession(int i, ITestSessionCallback iTestSessionCallback, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IBiometricService.DESCRIPTOR);
                    obtain.writeInt(i);
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

            @Override // android.hardware.biometrics.IBiometricService
            public List<SensorPropertiesInternal> getSensorProperties(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IBiometricService.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(SensorPropertiesInternal.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.IBiometricService
            public long authenticate(IBinder iBinder, long j, int i, IBiometricServiceReceiver iBiometricServiceReceiver, String str, PromptInfo promptInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IBiometricService.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeLong(j);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iBiometricServiceReceiver);
                    obtain.writeString(str);
                    obtain.writeTypedObject(promptInfo, 0);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.IBiometricService
            public void cancelAuthentication(IBinder iBinder, String str, long j) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IBiometricService.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeString(str);
                    obtain.writeLong(j);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.IBiometricService
            public int canAuthenticate(String str, int i, int i2, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IBiometricService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.IBiometricService
            public long getLastAuthenticationTime(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IBiometricService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.IBiometricService
            public boolean hasEnrolledBiometrics(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IBiometricService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.IBiometricService
            public void registerAuthenticator(int i, int i2, int i3, IBiometricAuthenticator iBiometricAuthenticator) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IBiometricService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeStrongInterface(iBiometricAuthenticator);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.IBiometricService
            public void registerEnabledOnKeyguardCallback(IBiometricEnabledOnKeyguardCallback iBiometricEnabledOnKeyguardCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IBiometricService.DESCRIPTOR);
                    obtain.writeStrongInterface(iBiometricEnabledOnKeyguardCallback);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.IBiometricService
            public void onReadyForAuthentication(long j, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IBiometricService.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeInt(i);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.IBiometricService
            public void invalidateAuthenticatorIds(int i, int i2, IInvalidationCallback iInvalidationCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IBiometricService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeStrongInterface(iInvalidationCallback);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.IBiometricService
            public long[] getAuthenticatorIds(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IBiometricService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createLongArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.IBiometricService
            public void resetLockoutTimeBound(IBinder iBinder, String str, int i, int i2, byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IBiometricService.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.IBiometricService
            public void resetLockout(int i, byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IBiometricService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.IBiometricService
            public int getCurrentStrength(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IBiometricService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.IBiometricService
            public int getCurrentModality(String str, int i, int i2, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IBiometricService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.IBiometricService
            public int getSupportedModalities(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IBiometricService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.IBiometricService
            public PromptInfo semGetPromptInfo(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IBiometricService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                    return (PromptInfo) obtain2.readTypedObject(PromptInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        protected void createTestSession_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.USE_BIOMETRIC_INTERNAL, getCallingPid(), getCallingUid());
        }

        protected void getSensorProperties_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.USE_BIOMETRIC_INTERNAL, getCallingPid(), getCallingUid());
        }

        protected void authenticate_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.USE_BIOMETRIC_INTERNAL, getCallingPid(), getCallingUid());
        }

        protected void cancelAuthentication_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.USE_BIOMETRIC_INTERNAL, getCallingPid(), getCallingUid());
        }

        protected void canAuthenticate_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.USE_BIOMETRIC_INTERNAL, getCallingPid(), getCallingUid());
        }

        protected void getLastAuthenticationTime_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.USE_BIOMETRIC_INTERNAL, getCallingPid(), getCallingUid());
        }

        protected void hasEnrolledBiometrics_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.USE_BIOMETRIC_INTERNAL, getCallingPid(), getCallingUid());
        }

        protected void registerAuthenticator_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.USE_BIOMETRIC_INTERNAL, getCallingPid(), getCallingUid());
        }

        protected void registerEnabledOnKeyguardCallback_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.USE_BIOMETRIC_INTERNAL, getCallingPid(), getCallingUid());
        }

        protected void onReadyForAuthentication_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.USE_BIOMETRIC_INTERNAL, getCallingPid(), getCallingUid());
        }

        protected void invalidateAuthenticatorIds_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.USE_BIOMETRIC_INTERNAL, getCallingPid(), getCallingUid());
        }

        protected void getAuthenticatorIds_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.USE_BIOMETRIC_INTERNAL, getCallingPid(), getCallingUid());
        }

        protected void resetLockoutTimeBound_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.USE_BIOMETRIC_INTERNAL, getCallingPid(), getCallingUid());
        }

        protected void resetLockout_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.USE_BIOMETRIC_INTERNAL, getCallingPid(), getCallingUid());
        }

        protected void getCurrentStrength_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.USE_BIOMETRIC_INTERNAL, getCallingPid(), getCallingUid());
        }

        protected void getCurrentModality_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.USE_BIOMETRIC_INTERNAL, getCallingPid(), getCallingUid());
        }

        protected void getSupportedModalities_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.USE_BIOMETRIC_INTERNAL, getCallingPid(), getCallingUid());
        }

        protected void semGetPromptInfo_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.USE_BIOMETRIC_INTERNAL, getCallingPid(), getCallingUid());
        }
    }
}
