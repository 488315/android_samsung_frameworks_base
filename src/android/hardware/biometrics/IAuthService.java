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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IAuthService.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IAuthService)) {
                return (IAuthService) iInterfaceQueryLocalInterface;
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
                    String uiPackage = getUiPackage();
                    parcel2.writeNoException();
                    parcel2.writeString(uiPackage);
                    return true;
                case 4:
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
                case 5:
                    IBinder strongBinder2 = parcel.readStrongBinder();
                    String string4 = parcel.readString();
                    long j2 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    cancelAuthentication(strongBinder2, string4, j2);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    String string5 = parcel.readString();
                    int i5 = parcel.readInt();
                    int i6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int iCanAuthenticate = canAuthenticate(string5, i5, i6);
                    parcel2.writeNoException();
                    parcel2.writeInt(iCanAuthenticate);
                    return true;
                case 7:
                    int i7 = parcel.readInt();
                    int i8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    long lastAuthenticationTime = getLastAuthenticationTime(i7, i8);
                    parcel2.writeNoException();
                    parcel2.writeLong(lastAuthenticationTime);
                    return true;
                case 8:
                    int i9 = parcel.readInt();
                    String string6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zHasEnrolledBiometrics = hasEnrolledBiometrics(i9, string6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zHasEnrolledBiometrics);
                    return true;
                case 9:
                    IBiometricEnabledOnKeyguardCallback iBiometricEnabledOnKeyguardCallbackAsInterface = IBiometricEnabledOnKeyguardCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerEnabledOnKeyguardCallback(iBiometricEnabledOnKeyguardCallbackAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    AuthenticationStateListener authenticationStateListenerAsInterface = AuthenticationStateListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerAuthenticationStateListener(authenticationStateListenerAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    AuthenticationStateListener authenticationStateListenerAsInterface2 = AuthenticationStateListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterAuthenticationStateListener(authenticationStateListenerAsInterface2);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    int i10 = parcel.readInt();
                    int i11 = parcel.readInt();
                    IInvalidationCallback iInvalidationCallbackAsInterface = IInvalidationCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    invalidateAuthenticatorIds(i10, i11, iInvalidationCallbackAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 13:
                    int i12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    long[] authenticatorIds = getAuthenticatorIds(i12);
                    parcel2.writeNoException();
                    parcel2.writeLongArray(authenticatorIds);
                    return true;
                case 14:
                    IBinder strongBinder3 = parcel.readStrongBinder();
                    String string7 = parcel.readString();
                    int i13 = parcel.readInt();
                    int i14 = parcel.readInt();
                    byte[] bArrCreateByteArray = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    resetLockoutTimeBound(strongBinder3, string7, i13, i14, bArrCreateByteArray);
                    parcel2.writeNoException();
                    return true;
                case 15:
                    int i15 = parcel.readInt();
                    byte[] bArrCreateByteArray2 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    resetLockout(i15, bArrCreateByteArray2);
                    parcel2.writeNoException();
                    return true;
                case 16:
                    int i16 = parcel.readInt();
                    String string8 = parcel.readString();
                    int i17 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    CharSequence buttonLabel = getButtonLabel(i16, string8, i17);
                    parcel2.writeNoException();
                    if (buttonLabel != null) {
                        parcel2.writeInt(1);
                        TextUtils.writeToParcel(buttonLabel, parcel2, 1);
                    } else {
                        parcel2.writeInt(0);
                    }
                    return true;
                case 17:
                    int i18 = parcel.readInt();
                    String string9 = parcel.readString();
                    int i19 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    CharSequence promptMessage = getPromptMessage(i18, string9, i19);
                    parcel2.writeNoException();
                    if (promptMessage != null) {
                        parcel2.writeInt(1);
                        TextUtils.writeToParcel(promptMessage, parcel2, 1);
                    } else {
                        parcel2.writeInt(0);
                    }
                    return true;
                case 18:
                    int i20 = parcel.readInt();
                    String string10 = parcel.readString();
                    int i21 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    CharSequence settingName = getSettingName(i20, string10, i21);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAuthService.DESCRIPTOR);
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

            @Override // android.hardware.biometrics.IAuthService
            public List<SensorPropertiesInternal> getSensorProperties(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAuthService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(SensorPropertiesInternal.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.IAuthService
            public String getUiPackage() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAuthService.DESCRIPTOR);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.IAuthService
            public long authenticate(IBinder iBinder, long j, int i, IBiometricServiceReceiver iBiometricServiceReceiver, String str, PromptInfo promptInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAuthService.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iBiometricServiceReceiver);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(promptInfo, 0);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readLong();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.IAuthService
            public void cancelAuthentication(IBinder iBinder, String str, long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAuthService.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeString(str);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.IAuthService
            public int canAuthenticate(String str, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAuthService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.IAuthService
            public long getLastAuthenticationTime(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAuthService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readLong();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.IAuthService
            public boolean hasEnrolledBiometrics(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAuthService.DESCRIPTOR);
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

            @Override // android.hardware.biometrics.IAuthService
            public void registerEnabledOnKeyguardCallback(IBiometricEnabledOnKeyguardCallback iBiometricEnabledOnKeyguardCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAuthService.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iBiometricEnabledOnKeyguardCallback);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.IAuthService
            public void registerAuthenticationStateListener(AuthenticationStateListener authenticationStateListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAuthService.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(authenticationStateListener);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.IAuthService
            public void unregisterAuthenticationStateListener(AuthenticationStateListener authenticationStateListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAuthService.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(authenticationStateListener);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.IAuthService
            public void invalidateAuthenticatorIds(int i, int i2, IInvalidationCallback iInvalidationCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAuthService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeStrongInterface(iInvalidationCallback);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.IAuthService
            public long[] getAuthenticatorIds(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAuthService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createLongArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.IAuthService
            public void resetLockoutTimeBound(IBinder iBinder, String str, int i, int i2, byte[] bArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAuthService.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeByteArray(bArr);
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.IAuthService
            public void resetLockout(int i, byte[] bArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAuthService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeByteArray(bArr);
                    this.mRemote.transact(15, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.IAuthService
            public CharSequence getButtonLabel(int i, String str, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAuthService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(16, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (CharSequence) parcelObtain2.readTypedObject(TextUtils.CHAR_SEQUENCE_CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.IAuthService
            public CharSequence getPromptMessage(int i, String str, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAuthService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(17, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (CharSequence) parcelObtain2.readTypedObject(TextUtils.CHAR_SEQUENCE_CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.IAuthService
            public CharSequence getSettingName(int i, String str, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAuthService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(18, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (CharSequence) parcelObtain2.readTypedObject(TextUtils.CHAR_SEQUENCE_CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
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
