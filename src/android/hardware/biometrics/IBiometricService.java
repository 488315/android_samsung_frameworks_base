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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IBiometricService.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IBiometricService)) {
                return (IBiometricService) iInterfaceQueryLocalInterface;
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
                    int i3 = parcel.readInt();
                    ITestSessionCallback iTestSessionCallbackAsInterface = ITestSessionCallback.Stub.asInterface(parcel.readStrongBinder());
                    String string = parcel.readString();
                    parcel.enforceNoDataAvail();
                    ITestSession iTestSessionCreateTestSession = createTestSession(i3, iTestSessionCallbackAsInterface, string);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(iTestSessionCreateTestSession);
                    return true;
                case 2:
                    String string2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    List<SensorPropertiesInternal> sensorProperties = getSensorProperties(string2);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(sensorProperties, 1);
                    return true;
                case 3:
                    IBinder strongBinder = parcel.readStrongBinder();
                    long j = parcel.readLong();
                    int i4 = parcel.readInt();
                    IBiometricServiceReceiver iBiometricServiceReceiverAsInterface = IBiometricServiceReceiver.Stub.asInterface(parcel.readStrongBinder());
                    String string3 = parcel.readString();
                    PromptInfo promptInfo = (PromptInfo) parcel.readTypedObject(PromptInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    long jAuthenticate = authenticate(strongBinder, j, i4, iBiometricServiceReceiverAsInterface, string3, promptInfo);
                    parcel2.writeNoException();
                    parcel2.writeLong(jAuthenticate);
                    return true;
                case 4:
                    IBinder strongBinder2 = parcel.readStrongBinder();
                    String string4 = parcel.readString();
                    long j2 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    cancelAuthentication(strongBinder2, string4, j2);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    String string5 = parcel.readString();
                    int i5 = parcel.readInt();
                    int i6 = parcel.readInt();
                    int i7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int iCanAuthenticate = canAuthenticate(string5, i5, i6, i7);
                    parcel2.writeNoException();
                    parcel2.writeInt(iCanAuthenticate);
                    return true;
                case 6:
                    int i8 = parcel.readInt();
                    int i9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    long lastAuthenticationTime = getLastAuthenticationTime(i8, i9);
                    parcel2.writeNoException();
                    parcel2.writeLong(lastAuthenticationTime);
                    return true;
                case 7:
                    int i10 = parcel.readInt();
                    String string6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zHasEnrolledBiometrics = hasEnrolledBiometrics(i10, string6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zHasEnrolledBiometrics);
                    return true;
                case 8:
                    int i11 = parcel.readInt();
                    int i12 = parcel.readInt();
                    int i13 = parcel.readInt();
                    IBiometricAuthenticator iBiometricAuthenticatorAsInterface = IBiometricAuthenticator.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerAuthenticator(i11, i12, i13, iBiometricAuthenticatorAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    IBiometricEnabledOnKeyguardCallback iBiometricEnabledOnKeyguardCallbackAsInterface = IBiometricEnabledOnKeyguardCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerEnabledOnKeyguardCallback(iBiometricEnabledOnKeyguardCallbackAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    long j3 = parcel.readLong();
                    int i14 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onReadyForAuthentication(j3, i14);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    int i15 = parcel.readInt();
                    int i16 = parcel.readInt();
                    IInvalidationCallback iInvalidationCallbackAsInterface = IInvalidationCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    invalidateAuthenticatorIds(i15, i16, iInvalidationCallbackAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    int i17 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    long[] authenticatorIds = getAuthenticatorIds(i17);
                    parcel2.writeNoException();
                    parcel2.writeLongArray(authenticatorIds);
                    return true;
                case 13:
                    IBinder strongBinder3 = parcel.readStrongBinder();
                    String string7 = parcel.readString();
                    int i18 = parcel.readInt();
                    int i19 = parcel.readInt();
                    byte[] bArrCreateByteArray = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    resetLockoutTimeBound(strongBinder3, string7, i18, i19, bArrCreateByteArray);
                    parcel2.writeNoException();
                    return true;
                case 14:
                    int i20 = parcel.readInt();
                    byte[] bArrCreateByteArray2 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    resetLockout(i20, bArrCreateByteArray2);
                    parcel2.writeNoException();
                    return true;
                case 15:
                    int i21 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int currentStrength = getCurrentStrength(i21);
                    parcel2.writeNoException();
                    parcel2.writeInt(currentStrength);
                    return true;
                case 16:
                    String string8 = parcel.readString();
                    int i22 = parcel.readInt();
                    int i23 = parcel.readInt();
                    int i24 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int currentModality = getCurrentModality(string8, i22, i23, i24);
                    parcel2.writeNoException();
                    parcel2.writeInt(currentModality);
                    return true;
                case 17:
                    int i25 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int supportedModalities = getSupportedModalities(i25);
                    parcel2.writeNoException();
                    parcel2.writeInt(supportedModalities);
                    return true;
                case 18:
                    int i26 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    PromptInfo promptInfoSemGetPromptInfo = semGetPromptInfo(i26);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(promptInfoSemGetPromptInfo, 1);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IBiometricService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
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

            @Override // android.hardware.biometrics.IBiometricService
            public List<SensorPropertiesInternal> getSensorProperties(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IBiometricService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(SensorPropertiesInternal.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.IBiometricService
            public long authenticate(IBinder iBinder, long j, int i, IBiometricServiceReceiver iBiometricServiceReceiver, String str, PromptInfo promptInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IBiometricService.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iBiometricServiceReceiver);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(promptInfo, 0);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readLong();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.IBiometricService
            public void cancelAuthentication(IBinder iBinder, String str, long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IBiometricService.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeString(str);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.IBiometricService
            public int canAuthenticate(String str, int i, int i2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IBiometricService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.IBiometricService
            public long getLastAuthenticationTime(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IBiometricService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readLong();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.IBiometricService
            public boolean hasEnrolledBiometrics(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IBiometricService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.IBiometricService
            public void registerAuthenticator(int i, int i2, int i3, IBiometricAuthenticator iBiometricAuthenticator) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IBiometricService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeStrongInterface(iBiometricAuthenticator);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.IBiometricService
            public void registerEnabledOnKeyguardCallback(IBiometricEnabledOnKeyguardCallback iBiometricEnabledOnKeyguardCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IBiometricService.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iBiometricEnabledOnKeyguardCallback);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.IBiometricService
            public void onReadyForAuthentication(long j, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IBiometricService.DESCRIPTOR);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.IBiometricService
            public void invalidateAuthenticatorIds(int i, int i2, IInvalidationCallback iInvalidationCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IBiometricService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeStrongInterface(iInvalidationCallback);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.IBiometricService
            public long[] getAuthenticatorIds(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IBiometricService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createLongArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.IBiometricService
            public void resetLockoutTimeBound(IBinder iBinder, String str, int i, int i2, byte[] bArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IBiometricService.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeByteArray(bArr);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.IBiometricService
            public void resetLockout(int i, byte[] bArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IBiometricService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeByteArray(bArr);
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.IBiometricService
            public int getCurrentStrength(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IBiometricService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(15, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.IBiometricService
            public int getCurrentModality(String str, int i, int i2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IBiometricService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(16, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.IBiometricService
            public int getSupportedModalities(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IBiometricService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(17, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.IBiometricService
            public PromptInfo semGetPromptInfo(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IBiometricService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(18, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (PromptInfo) parcelObtain2.readTypedObject(PromptInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
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
