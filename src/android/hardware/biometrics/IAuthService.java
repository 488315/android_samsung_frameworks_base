package android.hardware.biometrics;

import android.Manifest;
import android.app.ActivityThread;
import android.hardware.biometrics.AuthenticationStateListener;
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
import android.text.TextUtils;
import java.util.List;

/* loaded from: classes2.dex */
public interface IAuthService extends IInterface {
    public static final String DESCRIPTOR = "android.hardware.biometrics.IAuthService";

    public static class Default implements IAuthService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.hardware.biometrics.IAuthService
        public long authenticate(IBinder iBinder, long j, int i, IBiometricServiceReceiver iBiometricServiceReceiver, String str, PromptInfo promptInfo) throws RemoteException {
            return 0L;
        }

        @Override // android.hardware.biometrics.IAuthService
        public int canAuthenticate(String str, int i, int i2) throws RemoteException {
            return 0;
        }

        @Override // android.hardware.biometrics.IAuthService
        public void cancelAuthentication(IBinder iBinder, String str, long j) throws RemoteException {
        }

        @Override // android.hardware.biometrics.IAuthService
        public ITestSession createTestSession(int i, ITestSessionCallback iTestSessionCallback, String str) throws RemoteException {
            return null;
        }

        @Override // android.hardware.biometrics.IAuthService
        public long[] getAuthenticatorIds(int i) throws RemoteException {
            return null;
        }

        @Override // android.hardware.biometrics.IAuthService
        public CharSequence getButtonLabel(int i, String str, int i2) throws RemoteException {
            return null;
        }

        @Override // android.hardware.biometrics.IAuthService
        public long getLastAuthenticationTime(int i, int i2) throws RemoteException {
            return 0L;
        }

        @Override // android.hardware.biometrics.IAuthService
        public CharSequence getPromptMessage(int i, String str, int i2) throws RemoteException {
            return null;
        }

        @Override // android.hardware.biometrics.IAuthService
        public List<SensorPropertiesInternal> getSensorProperties(String str) throws RemoteException {
            return null;
        }

        @Override // android.hardware.biometrics.IAuthService
        public CharSequence getSettingName(int i, String str, int i2) throws RemoteException {
            return null;
        }

        @Override // android.hardware.biometrics.IAuthService
        public String getUiPackage() throws RemoteException {
            return null;
        }

        @Override // android.hardware.biometrics.IAuthService
        public boolean hasEnrolledBiometrics(int i, String str) throws RemoteException {
            return false;
        }

        @Override // android.hardware.biometrics.IAuthService
        public void invalidateAuthenticatorIds(int i, int i2, IInvalidationCallback iInvalidationCallback) throws RemoteException {
        }

        @Override // android.hardware.biometrics.IAuthService
        public void registerAuthenticationStateListener(AuthenticationStateListener authenticationStateListener) throws RemoteException {
        }

        @Override // android.hardware.biometrics.IAuthService
        public void registerEnabledOnKeyguardCallback(IBiometricEnabledOnKeyguardCallback iBiometricEnabledOnKeyguardCallback) throws RemoteException {
        }

        @Override // android.hardware.biometrics.IAuthService
        public void resetLockout(int i, byte[] bArr) throws RemoteException {
        }

        @Override // android.hardware.biometrics.IAuthService
        public void resetLockoutTimeBound(IBinder iBinder, String str, int i, int i2, byte[] bArr) throws RemoteException {
        }

        @Override // android.hardware.biometrics.IAuthService
        public void unregisterAuthenticationStateListener(AuthenticationStateListener authenticationStateListener) throws RemoteException {
        }
    }

    long authenticate(IBinder iBinder, long j, int i, IBiometricServiceReceiver iBiometricServiceReceiver, String str, PromptInfo promptInfo) throws RemoteException;

    int canAuthenticate(String str, int i, int i2) throws RemoteException;

    void cancelAuthentication(IBinder iBinder, String str, long j) throws RemoteException;

    ITestSession createTestSession(int i, ITestSessionCallback iTestSessionCallback, String str) throws RemoteException;

    long[] getAuthenticatorIds(int i) throws RemoteException;

    CharSequence getButtonLabel(int i, String str, int i2) throws RemoteException;

    long getLastAuthenticationTime(int i, int i2) throws RemoteException;

    CharSequence getPromptMessage(int i, String str, int i2) throws RemoteException;

    List<SensorPropertiesInternal> getSensorProperties(String str) throws RemoteException;

    CharSequence getSettingName(int i, String str, int i2) throws RemoteException;

    String getUiPackage() throws RemoteException;

    boolean hasEnrolledBiometrics(int i, String str) throws RemoteException;

    void invalidateAuthenticatorIds(int i, int i2, IInvalidationCallback iInvalidationCallback) throws RemoteException;

    void registerAuthenticationStateListener(AuthenticationStateListener authenticationStateListener) throws RemoteException;

    void registerEnabledOnKeyguardCallback(IBiometricEnabledOnKeyguardCallback iBiometricEnabledOnKeyguardCallback) throws RemoteException;

    void resetLockout(int i, byte[] bArr) throws RemoteException;

    void resetLockoutTimeBound(IBinder iBinder, String str, int i, int i2, byte[] bArr) throws RemoteException;

    void unregisterAuthenticationStateListener(AuthenticationStateListener authenticationStateListener) throws RemoteException;

    public static abstract class Stub extends Binder implements IAuthService {
        static final int TRANSACTION_authenticate = 4;
        static final int TRANSACTION_canAuthenticate = 6;
        static final int TRANSACTION_cancelAuthentication = 5;
        static final int TRANSACTION_createTestSession = 1;
        static final int TRANSACTION_getAuthenticatorIds = 13;
        static final int TRANSACTION_getButtonLabel = 16;
        static final int TRANSACTION_getLastAuthenticationTime = 7;
        static final int TRANSACTION_getPromptMessage = 17;
        static final int TRANSACTION_getSensorProperties = 2;
        static final int TRANSACTION_getSettingName = 18;
        static final int TRANSACTION_getUiPackage = 3;
        static final int TRANSACTION_hasEnrolledBiometrics = 8;
        static final int TRANSACTION_invalidateAuthenticatorIds = 12;
        static final int TRANSACTION_registerAuthenticationStateListener = 10;
        static final int TRANSACTION_registerEnabledOnKeyguardCallback = 9;
        static final int TRANSACTION_resetLockout = 15;
        static final int TRANSACTION_resetLockoutTimeBound = 14;
        static final int TRANSACTION_unregisterAuthenticationStateListener = 11;
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
            attachInterface(this, IAuthService.DESCRIPTOR);
            if (permissionEnforcer == null) {
                throw new IllegalArgumentException("enforcer cannot be null");
            }
            this.mEnforcer = permissionEnforcer;
        }

        @Deprecated
        public Stub() {
            this(PermissionEnforcer.fromContext(ActivityThread.currentActivityThread().getSystemContext()));
        }

        public static IAuthService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IAuthService.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IAuthService)) {
                return (IAuthService) queryLocalInterface;
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
                    return "getUiPackage";
                case 4:
                    return "authenticate";
                case 5:
                    return "cancelAuthentication";
                case 6:
                    return "canAuthenticate";
                case 7:
                    return "getLastAuthenticationTime";
                case 8:
                    return "hasEnrolledBiometrics";
                case 9:
                    return "registerEnabledOnKeyguardCallback";
                case 10:
                    return "registerAuthenticationStateListener";
                case 11:
                    return "unregisterAuthenticationStateListener";
                case 12:
                    return "invalidateAuthenticatorIds";
                case 13:
                    return "getAuthenticatorIds";
                case 14:
                    return "resetLockoutTimeBound";
                case 15:
                    return "resetLockout";
                case 16:
                    return "getButtonLabel";
                case 17:
                    return "getPromptMessage";
                case 18:
                    return "getSettingName";
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
                parcel.enforceInterface(IAuthService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IAuthService.DESCRIPTOR);
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
                    String uiPackage = getUiPackage();
                    parcel2.writeNoException();
                    parcel2.writeString(uiPackage);
                    return true;
                case 4:
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
                case 5:
                    IBinder readStrongBinder2 = parcel.readStrongBinder();
                    String readString4 = parcel.readString();
                    long readLong2 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    cancelAuthentication(readStrongBinder2, readString4, readLong2);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    String readString5 = parcel.readString();
                    int readInt3 = parcel.readInt();
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int canAuthenticate = canAuthenticate(readString5, readInt3, readInt4);
                    parcel2.writeNoException();
                    parcel2.writeInt(canAuthenticate);
                    return true;
                case 7:
                    int readInt5 = parcel.readInt();
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    long lastAuthenticationTime = getLastAuthenticationTime(readInt5, readInt6);
                    parcel2.writeNoException();
                    parcel2.writeLong(lastAuthenticationTime);
                    return true;
                case 8:
                    int readInt7 = parcel.readInt();
                    String readString6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean hasEnrolledBiometrics = hasEnrolledBiometrics(readInt7, readString6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(hasEnrolledBiometrics);
                    return true;
                case 9:
                    IBiometricEnabledOnKeyguardCallback asInterface3 = IBiometricEnabledOnKeyguardCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerEnabledOnKeyguardCallback(asInterface3);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    AuthenticationStateListener asInterface4 = AuthenticationStateListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerAuthenticationStateListener(asInterface4);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    AuthenticationStateListener asInterface5 = AuthenticationStateListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterAuthenticationStateListener(asInterface5);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    int readInt8 = parcel.readInt();
                    int readInt9 = parcel.readInt();
                    IInvalidationCallback asInterface6 = IInvalidationCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    invalidateAuthenticatorIds(readInt8, readInt9, asInterface6);
                    parcel2.writeNoException();
                    return true;
                case 13:
                    int readInt10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    long[] authenticatorIds = getAuthenticatorIds(readInt10);
                    parcel2.writeNoException();
                    parcel2.writeLongArray(authenticatorIds);
                    return true;
                case 14:
                    IBinder readStrongBinder3 = parcel.readStrongBinder();
                    String readString7 = parcel.readString();
                    int readInt11 = parcel.readInt();
                    int readInt12 = parcel.readInt();
                    byte[] createByteArray = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    resetLockoutTimeBound(readStrongBinder3, readString7, readInt11, readInt12, createByteArray);
                    parcel2.writeNoException();
                    return true;
                case 15:
                    int readInt13 = parcel.readInt();
                    byte[] createByteArray2 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    resetLockout(readInt13, createByteArray2);
                    parcel2.writeNoException();
                    return true;
                case 16:
                    int readInt14 = parcel.readInt();
                    String readString8 = parcel.readString();
                    int readInt15 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    CharSequence buttonLabel = getButtonLabel(readInt14, readString8, readInt15);
                    parcel2.writeNoException();
                    if (buttonLabel != null) {
                        parcel2.writeInt(1);
                        TextUtils.writeToParcel(buttonLabel, parcel2, 1);
                    } else {
                        parcel2.writeInt(0);
                    }
                    return true;
                case 17:
                    int readInt16 = parcel.readInt();
                    String readString9 = parcel.readString();
                    int readInt17 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    CharSequence promptMessage = getPromptMessage(readInt16, readString9, readInt17);
                    parcel2.writeNoException();
                    if (promptMessage != null) {
                        parcel2.writeInt(1);
                        TextUtils.writeToParcel(promptMessage, parcel2, 1);
                    } else {
                        parcel2.writeInt(0);
                    }
                    return true;
                case 18:
                    int readInt18 = parcel.readInt();
                    String readString10 = parcel.readString();
                    int readInt19 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    CharSequence settingName = getSettingName(readInt18, readString10, readInt19);
                    parcel2.writeNoException();
                    if (settingName != null) {
                        parcel2.writeInt(1);
                        TextUtils.writeToParcel(settingName, parcel2, 1);
                    } else {
                        parcel2.writeInt(0);
                    }
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IAuthService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IAuthService.DESCRIPTOR;
            }

            @Override // android.hardware.biometrics.IAuthService
            public ITestSession createTestSession(int i, ITestSessionCallback iTestSessionCallback, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAuthService.DESCRIPTOR);
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

            @Override // android.hardware.biometrics.IAuthService
            public List<SensorPropertiesInternal> getSensorProperties(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAuthService.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(SensorPropertiesInternal.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.IAuthService
            public String getUiPackage() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAuthService.DESCRIPTOR);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.IAuthService
            public long authenticate(IBinder iBinder, long j, int i, IBiometricServiceReceiver iBiometricServiceReceiver, String str, PromptInfo promptInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAuthService.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeLong(j);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iBiometricServiceReceiver);
                    obtain.writeString(str);
                    obtain.writeTypedObject(promptInfo, 0);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.IAuthService
            public void cancelAuthentication(IBinder iBinder, String str, long j) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAuthService.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeString(str);
                    obtain.writeLong(j);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.IAuthService
            public int canAuthenticate(String str, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAuthService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.IAuthService
            public long getLastAuthenticationTime(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAuthService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.IAuthService
            public boolean hasEnrolledBiometrics(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAuthService.DESCRIPTOR);
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

            @Override // android.hardware.biometrics.IAuthService
            public void registerEnabledOnKeyguardCallback(IBiometricEnabledOnKeyguardCallback iBiometricEnabledOnKeyguardCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAuthService.DESCRIPTOR);
                    obtain.writeStrongInterface(iBiometricEnabledOnKeyguardCallback);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.IAuthService
            public void registerAuthenticationStateListener(AuthenticationStateListener authenticationStateListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAuthService.DESCRIPTOR);
                    obtain.writeStrongInterface(authenticationStateListener);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.IAuthService
            public void unregisterAuthenticationStateListener(AuthenticationStateListener authenticationStateListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAuthService.DESCRIPTOR);
                    obtain.writeStrongInterface(authenticationStateListener);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.IAuthService
            public void invalidateAuthenticatorIds(int i, int i2, IInvalidationCallback iInvalidationCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAuthService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeStrongInterface(iInvalidationCallback);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.IAuthService
            public long[] getAuthenticatorIds(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAuthService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createLongArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.IAuthService
            public void resetLockoutTimeBound(IBinder iBinder, String str, int i, int i2, byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAuthService.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.IAuthService
            public void resetLockout(int i, byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAuthService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.IAuthService
            public CharSequence getButtonLabel(int i, String str, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAuthService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeInt(i2);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                    return (CharSequence) obtain2.readTypedObject(TextUtils.CHAR_SEQUENCE_CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.IAuthService
            public CharSequence getPromptMessage(int i, String str, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAuthService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeInt(i2);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                    return (CharSequence) obtain2.readTypedObject(TextUtils.CHAR_SEQUENCE_CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.IAuthService
            public CharSequence getSettingName(int i, String str, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAuthService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeInt(i2);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                    return (CharSequence) obtain2.readTypedObject(TextUtils.CHAR_SEQUENCE_CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        protected void createTestSession_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.TEST_BIOMETRIC, getCallingPid(), getCallingUid());
        }

        protected void getSensorProperties_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.TEST_BIOMETRIC, getCallingPid(), getCallingUid());
        }

        protected void getUiPackage_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.TEST_BIOMETRIC, getCallingPid(), getCallingUid());
        }
    }
}
