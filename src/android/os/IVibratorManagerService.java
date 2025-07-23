package android.os;

import android.Manifest;
import android.app.ActivityThread;
import android.os.ICancellationSignal;
import android.os.IVibratorStateListener;
import android.os.vibrator.IVibrationSessionCallback;
import com.samsung.android.edge.EdgeManagerInternal;
import com.samsung.android.vibrator.VibrationDebugInfo;

/* loaded from: classes3.dex */
public interface IVibratorManagerService extends IInterface {
    public static final String DESCRIPTOR = "android.os.IVibratorManagerService";

    public static class Default implements IVibratorManagerService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.os.IVibratorManagerService
        public void cancelVibrate(int i, IBinder iBinder) throws RemoteException {
        }

        @Override // android.os.IVibratorManagerService
        public String executeVibrationDebugCommand(VibrationDebugInfo vibrationDebugInfo) throws RemoteException {
            return null;
        }

        @Override // android.os.IVibratorManagerService
        public int getCapabilities() throws RemoteException {
            return 0;
        }

        @Override // android.os.IVibratorManagerService
        public int getSupportedVibratorGroup() throws RemoteException {
            return 0;
        }

        @Override // android.os.IVibratorManagerService
        public int[] getVibratorIds() throws RemoteException {
            return null;
        }

        @Override // android.os.IVibratorManagerService
        public VibratorInfo getVibratorInfo(int i) throws RemoteException {
            return null;
        }

        @Override // android.os.IVibratorManagerService
        public boolean isVibrating(int i) throws RemoteException {
            return false;
        }

        @Override // android.os.IVibratorManagerService
        public void performHapticFeedback(int i, int i2, String str, int i3, String str2, int i4, int i5) throws RemoteException {
        }

        @Override // android.os.IVibratorManagerService
        public void performHapticFeedbackForInputDevice(int i, int i2, String str, int i3, int i4, int i5, String str2, int i6, int i7) throws RemoteException {
        }

        @Override // android.os.IVibratorManagerService
        public boolean registerVibratorStateListener(int i, IVibratorStateListener iVibratorStateListener) throws RemoteException {
            return false;
        }

        @Override // android.os.IVibratorManagerService
        public int semGetNumberOfSupportedPatterns() throws RemoteException {
            return 0;
        }

        @Override // android.os.IVibratorManagerService
        public boolean setAlwaysOnEffect(int i, String str, int i2, CombinedVibration combinedVibration, VibrationAttributes vibrationAttributes) throws RemoteException {
            return false;
        }

        @Override // android.os.IVibratorManagerService
        public ICancellationSignal startVendorVibrationSession(int i, int i2, String str, int[] iArr, VibrationAttributes vibrationAttributes, String str2, IVibrationSessionCallback iVibrationSessionCallback) throws RemoteException {
            return null;
        }

        @Override // android.os.IVibratorManagerService
        public boolean unregisterVibratorStateListener(int i, IVibratorStateListener iVibratorStateListener) throws RemoteException {
            return false;
        }

        @Override // android.os.IVibratorManagerService
        public void vibrate(int i, int i2, String str, CombinedVibration combinedVibration, VibrationAttributes vibrationAttributes, String str2, IBinder iBinder) throws RemoteException {
        }
    }

    void cancelVibrate(int i, IBinder iBinder) throws RemoteException;

    String executeVibrationDebugCommand(VibrationDebugInfo vibrationDebugInfo) throws RemoteException;

    int getCapabilities() throws RemoteException;

    int getSupportedVibratorGroup() throws RemoteException;

    int[] getVibratorIds() throws RemoteException;

    VibratorInfo getVibratorInfo(int i) throws RemoteException;

    boolean isVibrating(int i) throws RemoteException;

    void performHapticFeedback(int i, int i2, String str, int i3, String str2, int i4, int i5) throws RemoteException;

    void performHapticFeedbackForInputDevice(int i, int i2, String str, int i3, int i4, int i5, String str2, int i6, int i7) throws RemoteException;

    boolean registerVibratorStateListener(int i, IVibratorStateListener iVibratorStateListener) throws RemoteException;

    int semGetNumberOfSupportedPatterns() throws RemoteException;

    boolean setAlwaysOnEffect(int i, String str, int i2, CombinedVibration combinedVibration, VibrationAttributes vibrationAttributes) throws RemoteException;

    ICancellationSignal startVendorVibrationSession(int i, int i2, String str, int[] iArr, VibrationAttributes vibrationAttributes, String str2, IVibrationSessionCallback iVibrationSessionCallback) throws RemoteException;

    boolean unregisterVibratorStateListener(int i, IVibratorStateListener iVibratorStateListener) throws RemoteException;

    void vibrate(int i, int i2, String str, CombinedVibration combinedVibration, VibrationAttributes vibrationAttributes, String str2, IBinder iBinder) throws RemoteException;

    public static abstract class Stub extends Binder implements IVibratorManagerService {
        static final String[] PERMISSIONS_startVendorVibrationSession = {Manifest.permission.VIBRATE, Manifest.permission.VIBRATE_VENDOR_EFFECTS, Manifest.permission.START_VIBRATION_SESSIONS};
        static final int TRANSACTION_cancelVibrate = 9;
        static final int TRANSACTION_executeVibrationDebugCommand = 15;
        static final int TRANSACTION_getCapabilities = 2;
        static final int TRANSACTION_getSupportedVibratorGroup = 14;
        static final int TRANSACTION_getVibratorIds = 1;
        static final int TRANSACTION_getVibratorInfo = 3;
        static final int TRANSACTION_isVibrating = 4;
        static final int TRANSACTION_performHapticFeedback = 10;
        static final int TRANSACTION_performHapticFeedbackForInputDevice = 11;
        static final int TRANSACTION_registerVibratorStateListener = 5;
        static final int TRANSACTION_semGetNumberOfSupportedPatterns = 13;
        static final int TRANSACTION_setAlwaysOnEffect = 7;
        static final int TRANSACTION_startVendorVibrationSession = 12;
        static final int TRANSACTION_unregisterVibratorStateListener = 6;
        static final int TRANSACTION_vibrate = 8;
        private final PermissionEnforcer mEnforcer;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 14;
        }

        public Stub(PermissionEnforcer permissionEnforcer) {
            attachInterface(this, IVibratorManagerService.DESCRIPTOR);
            if (permissionEnforcer == null) {
                throw new IllegalArgumentException("enforcer cannot be null");
            }
            this.mEnforcer = permissionEnforcer;
        }

        @Deprecated
        public Stub() {
            this(PermissionEnforcer.fromContext(ActivityThread.currentActivityThread().getSystemContext()));
        }

        public static IVibratorManagerService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IVibratorManagerService.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IVibratorManagerService)) {
                return (IVibratorManagerService) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "getVibratorIds";
                case 2:
                    return "getCapabilities";
                case 3:
                    return "getVibratorInfo";
                case 4:
                    return "isVibrating";
                case 5:
                    return "registerVibratorStateListener";
                case 6:
                    return "unregisterVibratorStateListener";
                case 7:
                    return "setAlwaysOnEffect";
                case 8:
                    return EdgeManagerInternal.NOTIFICATION_KEY_VIBRATE;
                case 9:
                    return "cancelVibrate";
                case 10:
                    return "performHapticFeedback";
                case 11:
                    return "performHapticFeedbackForInputDevice";
                case 12:
                    return "startVendorVibrationSession";
                case 13:
                    return "semGetNumberOfSupportedPatterns";
                case 14:
                    return "getSupportedVibratorGroup";
                case 15:
                    return "executeVibrationDebugCommand";
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
                parcel.enforceInterface(IVibratorManagerService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IVibratorManagerService.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int[] vibratorIds = getVibratorIds();
                    parcel2.writeNoException();
                    parcel2.writeIntArray(vibratorIds);
                    return true;
                case 2:
                    int capabilities = getCapabilities();
                    parcel2.writeNoException();
                    parcel2.writeInt(capabilities);
                    return true;
                case 3:
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    VibratorInfo vibratorInfo = getVibratorInfo(readInt);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(vibratorInfo, 1);
                    return true;
                case 4:
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isVibrating = isVibrating(readInt2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isVibrating);
                    return true;
                case 5:
                    int readInt3 = parcel.readInt();
                    IVibratorStateListener asInterface = IVibratorStateListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    boolean registerVibratorStateListener = registerVibratorStateListener(readInt3, asInterface);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(registerVibratorStateListener);
                    return true;
                case 6:
                    int readInt4 = parcel.readInt();
                    IVibratorStateListener asInterface2 = IVibratorStateListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    boolean unregisterVibratorStateListener = unregisterVibratorStateListener(readInt4, asInterface2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(unregisterVibratorStateListener);
                    return true;
                case 7:
                    int readInt5 = parcel.readInt();
                    String readString = parcel.readString();
                    int readInt6 = parcel.readInt();
                    CombinedVibration combinedVibration = (CombinedVibration) parcel.readTypedObject(CombinedVibration.CREATOR);
                    VibrationAttributes vibrationAttributes = (VibrationAttributes) parcel.readTypedObject(VibrationAttributes.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean alwaysOnEffect = setAlwaysOnEffect(readInt5, readString, readInt6, combinedVibration, vibrationAttributes);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(alwaysOnEffect);
                    return true;
                case 8:
                    int readInt7 = parcel.readInt();
                    int readInt8 = parcel.readInt();
                    String readString2 = parcel.readString();
                    CombinedVibration combinedVibration2 = (CombinedVibration) parcel.readTypedObject(CombinedVibration.CREATOR);
                    VibrationAttributes vibrationAttributes2 = (VibrationAttributes) parcel.readTypedObject(VibrationAttributes.CREATOR);
                    String readString3 = parcel.readString();
                    IBinder readStrongBinder = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    vibrate(readInt7, readInt8, readString2, combinedVibration2, vibrationAttributes2, readString3, readStrongBinder);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    int readInt9 = parcel.readInt();
                    IBinder readStrongBinder2 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    cancelVibrate(readInt9, readStrongBinder2);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    int readInt10 = parcel.readInt();
                    int readInt11 = parcel.readInt();
                    String readString4 = parcel.readString();
                    int readInt12 = parcel.readInt();
                    String readString5 = parcel.readString();
                    int readInt13 = parcel.readInt();
                    int readInt14 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    performHapticFeedback(readInt10, readInt11, readString4, readInt12, readString5, readInt13, readInt14);
                    return true;
                case 11:
                    int readInt15 = parcel.readInt();
                    int readInt16 = parcel.readInt();
                    String readString6 = parcel.readString();
                    int readInt17 = parcel.readInt();
                    int readInt18 = parcel.readInt();
                    int readInt19 = parcel.readInt();
                    String readString7 = parcel.readString();
                    int readInt20 = parcel.readInt();
                    int readInt21 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    performHapticFeedbackForInputDevice(readInt15, readInt16, readString6, readInt17, readInt18, readInt19, readString7, readInt20, readInt21);
                    return true;
                case 12:
                    int readInt22 = parcel.readInt();
                    int readInt23 = parcel.readInt();
                    String readString8 = parcel.readString();
                    int[] createIntArray = parcel.createIntArray();
                    VibrationAttributes vibrationAttributes3 = (VibrationAttributes) parcel.readTypedObject(VibrationAttributes.CREATOR);
                    String readString9 = parcel.readString();
                    IVibrationSessionCallback asInterface3 = IVibrationSessionCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    ICancellationSignal startVendorVibrationSession = startVendorVibrationSession(readInt22, readInt23, readString8, createIntArray, vibrationAttributes3, readString9, asInterface3);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(startVendorVibrationSession);
                    return true;
                case 13:
                    int semGetNumberOfSupportedPatterns = semGetNumberOfSupportedPatterns();
                    parcel2.writeNoException();
                    parcel2.writeInt(semGetNumberOfSupportedPatterns);
                    return true;
                case 14:
                    int supportedVibratorGroup = getSupportedVibratorGroup();
                    parcel2.writeNoException();
                    parcel2.writeInt(supportedVibratorGroup);
                    return true;
                case 15:
                    VibrationDebugInfo vibrationDebugInfo = (VibrationDebugInfo) parcel.readTypedObject(VibrationDebugInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    String executeVibrationDebugCommand = executeVibrationDebugCommand(vibrationDebugInfo);
                    parcel2.writeNoException();
                    parcel2.writeString(executeVibrationDebugCommand);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IVibratorManagerService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IVibratorManagerService.DESCRIPTOR;
            }

            @Override // android.os.IVibratorManagerService
            public int[] getVibratorIds() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVibratorManagerService.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createIntArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVibratorManagerService
            public int getCapabilities() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVibratorManagerService.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVibratorManagerService
            public VibratorInfo getVibratorInfo(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVibratorManagerService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return (VibratorInfo) obtain2.readTypedObject(VibratorInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVibratorManagerService
            public boolean isVibrating(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVibratorManagerService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVibratorManagerService
            public boolean registerVibratorStateListener(int i, IVibratorStateListener iVibratorStateListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVibratorManagerService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iVibratorStateListener);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVibratorManagerService
            public boolean unregisterVibratorStateListener(int i, IVibratorStateListener iVibratorStateListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVibratorManagerService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iVibratorStateListener);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVibratorManagerService
            public boolean setAlwaysOnEffect(int i, String str, int i2, CombinedVibration combinedVibration, VibrationAttributes vibrationAttributes) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVibratorManagerService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(combinedVibration, 0);
                    obtain.writeTypedObject(vibrationAttributes, 0);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVibratorManagerService
            public void vibrate(int i, int i2, String str, CombinedVibration combinedVibration, VibrationAttributes vibrationAttributes, String str2, IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVibratorManagerService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str);
                    obtain.writeTypedObject(combinedVibration, 0);
                    obtain.writeTypedObject(vibrationAttributes, 0);
                    obtain.writeString(str2);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVibratorManagerService
            public void cancelVibrate(int i, IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVibratorManagerService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVibratorManagerService
            public void performHapticFeedback(int i, int i2, String str, int i3, String str2, int i4, int i5) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IVibratorManagerService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str);
                    obtain.writeInt(i3);
                    obtain.writeString(str2);
                    obtain.writeInt(i4);
                    obtain.writeInt(i5);
                    this.mRemote.transact(10, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.os.IVibratorManagerService
            public void performHapticFeedbackForInputDevice(int i, int i2, String str, int i3, int i4, int i5, String str2, int i6, int i7) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IVibratorManagerService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    obtain.writeInt(i5);
                    obtain.writeString(str2);
                    obtain.writeInt(i6);
                    obtain.writeInt(i7);
                    this.mRemote.transact(11, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.os.IVibratorManagerService
            public ICancellationSignal startVendorVibrationSession(int i, int i2, String str, int[] iArr, VibrationAttributes vibrationAttributes, String str2, IVibrationSessionCallback iVibrationSessionCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVibratorManagerService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str);
                    obtain.writeIntArray(iArr);
                    obtain.writeTypedObject(vibrationAttributes, 0);
                    obtain.writeString(str2);
                    obtain.writeStrongInterface(iVibrationSessionCallback);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return ICancellationSignal.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVibratorManagerService
            public int semGetNumberOfSupportedPatterns() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVibratorManagerService.DESCRIPTOR);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVibratorManagerService
            public int getSupportedVibratorGroup() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVibratorManagerService.DESCRIPTOR);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVibratorManagerService
            public String executeVibrationDebugCommand(VibrationDebugInfo vibrationDebugInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVibratorManagerService.DESCRIPTOR);
                    obtain.writeTypedObject(vibrationDebugInfo, 0);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        protected void isVibrating_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.ACCESS_VIBRATOR_STATE, getCallingPid(), getCallingUid());
        }

        protected void registerVibratorStateListener_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.ACCESS_VIBRATOR_STATE, getCallingPid(), getCallingUid());
        }

        protected void unregisterVibratorStateListener_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.ACCESS_VIBRATOR_STATE, getCallingPid(), getCallingUid());
        }

        protected void startVendorVibrationSession_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermissionAllOf(PERMISSIONS_startVendorVibrationSession, getCallingPid(), getCallingUid());
        }
    }
}
