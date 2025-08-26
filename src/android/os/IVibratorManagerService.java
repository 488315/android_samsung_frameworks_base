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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IVibratorManagerService.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IVibratorManagerService)) {
                return (IVibratorManagerService) iInterfaceQueryLocalInterface;
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
                    int i3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    VibratorInfo vibratorInfo = getVibratorInfo(i3);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(vibratorInfo, 1);
                    return true;
                case 4:
                    int i4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsVibrating = isVibrating(i4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsVibrating);
                    return true;
                case 5:
                    int i5 = parcel.readInt();
                    IVibratorStateListener iVibratorStateListenerAsInterface = IVibratorStateListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    boolean zRegisterVibratorStateListener = registerVibratorStateListener(i5, iVibratorStateListenerAsInterface);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zRegisterVibratorStateListener);
                    return true;
                case 6:
                    int i6 = parcel.readInt();
                    IVibratorStateListener iVibratorStateListenerAsInterface2 = IVibratorStateListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    boolean zUnregisterVibratorStateListener = unregisterVibratorStateListener(i6, iVibratorStateListenerAsInterface2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zUnregisterVibratorStateListener);
                    return true;
                case 7:
                    int i7 = parcel.readInt();
                    String string = parcel.readString();
                    int i8 = parcel.readInt();
                    CombinedVibration combinedVibration = (CombinedVibration) parcel.readTypedObject(CombinedVibration.CREATOR);
                    VibrationAttributes vibrationAttributes = (VibrationAttributes) parcel.readTypedObject(VibrationAttributes.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean alwaysOnEffect = setAlwaysOnEffect(i7, string, i8, combinedVibration, vibrationAttributes);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(alwaysOnEffect);
                    return true;
                case 8:
                    int i9 = parcel.readInt();
                    int i10 = parcel.readInt();
                    String string2 = parcel.readString();
                    CombinedVibration combinedVibration2 = (CombinedVibration) parcel.readTypedObject(CombinedVibration.CREATOR);
                    VibrationAttributes vibrationAttributes2 = (VibrationAttributes) parcel.readTypedObject(VibrationAttributes.CREATOR);
                    String string3 = parcel.readString();
                    IBinder strongBinder = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    vibrate(i9, i10, string2, combinedVibration2, vibrationAttributes2, string3, strongBinder);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    int i11 = parcel.readInt();
                    IBinder strongBinder2 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    cancelVibrate(i11, strongBinder2);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    int i12 = parcel.readInt();
                    int i13 = parcel.readInt();
                    String string4 = parcel.readString();
                    int i14 = parcel.readInt();
                    String string5 = parcel.readString();
                    int i15 = parcel.readInt();
                    int i16 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    performHapticFeedback(i12, i13, string4, i14, string5, i15, i16);
                    return true;
                case 11:
                    int i17 = parcel.readInt();
                    int i18 = parcel.readInt();
                    String string6 = parcel.readString();
                    int i19 = parcel.readInt();
                    int i20 = parcel.readInt();
                    int i21 = parcel.readInt();
                    String string7 = parcel.readString();
                    int i22 = parcel.readInt();
                    int i23 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    performHapticFeedbackForInputDevice(i17, i18, string6, i19, i20, i21, string7, i22, i23);
                    return true;
                case 12:
                    int i24 = parcel.readInt();
                    int i25 = parcel.readInt();
                    String string8 = parcel.readString();
                    int[] iArrCreateIntArray = parcel.createIntArray();
                    VibrationAttributes vibrationAttributes3 = (VibrationAttributes) parcel.readTypedObject(VibrationAttributes.CREATOR);
                    String string9 = parcel.readString();
                    IVibrationSessionCallback iVibrationSessionCallbackAsInterface = IVibrationSessionCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    ICancellationSignal iCancellationSignalStartVendorVibrationSession = startVendorVibrationSession(i24, i25, string8, iArrCreateIntArray, vibrationAttributes3, string9, iVibrationSessionCallbackAsInterface);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(iCancellationSignalStartVendorVibrationSession);
                    return true;
                case 13:
                    int iSemGetNumberOfSupportedPatterns = semGetNumberOfSupportedPatterns();
                    parcel2.writeNoException();
                    parcel2.writeInt(iSemGetNumberOfSupportedPatterns);
                    return true;
                case 14:
                    int supportedVibratorGroup = getSupportedVibratorGroup();
                    parcel2.writeNoException();
                    parcel2.writeInt(supportedVibratorGroup);
                    return true;
                case 15:
                    VibrationDebugInfo vibrationDebugInfo = (VibrationDebugInfo) parcel.readTypedObject(VibrationDebugInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    String strExecuteVibrationDebugCommand = executeVibrationDebugCommand(vibrationDebugInfo);
                    parcel2.writeNoException();
                    parcel2.writeString(strExecuteVibrationDebugCommand);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVibratorManagerService.DESCRIPTOR);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createIntArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IVibratorManagerService
            public int getCapabilities() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVibratorManagerService.DESCRIPTOR);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IVibratorManagerService
            public VibratorInfo getVibratorInfo(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVibratorManagerService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (VibratorInfo) parcelObtain2.readTypedObject(VibratorInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IVibratorManagerService
            public boolean isVibrating(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVibratorManagerService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IVibratorManagerService
            public boolean registerVibratorStateListener(int i, IVibratorStateListener iVibratorStateListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVibratorManagerService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iVibratorStateListener);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IVibratorManagerService
            public boolean unregisterVibratorStateListener(int i, IVibratorStateListener iVibratorStateListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVibratorManagerService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iVibratorStateListener);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IVibratorManagerService
            public boolean setAlwaysOnEffect(int i, String str, int i2, CombinedVibration combinedVibration, VibrationAttributes vibrationAttributes) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVibratorManagerService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeTypedObject(combinedVibration, 0);
                    parcelObtain.writeTypedObject(vibrationAttributes, 0);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IVibratorManagerService
            public void vibrate(int i, int i2, String str, CombinedVibration combinedVibration, VibrationAttributes vibrationAttributes, String str2, IBinder iBinder) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVibratorManagerService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(combinedVibration, 0);
                    parcelObtain.writeTypedObject(vibrationAttributes, 0);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IVibratorManagerService
            public void cancelVibrate(int i, IBinder iBinder) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVibratorManagerService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IVibratorManagerService
            public void performHapticFeedback(int i, int i2, String str, int i3, String str2, int i4, int i5) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IVibratorManagerService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i4);
                    parcelObtain.writeInt(i5);
                    this.mRemote.transact(10, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IVibratorManagerService
            public void performHapticFeedbackForInputDevice(int i, int i2, String str, int i3, int i4, int i5, String str2, int i6, int i7) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IVibratorManagerService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeInt(i4);
                    parcelObtain.writeInt(i5);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i6);
                    parcelObtain.writeInt(i7);
                    this.mRemote.transact(11, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IVibratorManagerService
            public ICancellationSignal startVendorVibrationSession(int i, int i2, String str, int[] iArr, VibrationAttributes vibrationAttributes, String str2, IVibrationSessionCallback iVibrationSessionCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVibratorManagerService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeString(str);
                    parcelObtain.writeIntArray(iArr);
                    parcelObtain.writeTypedObject(vibrationAttributes, 0);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeStrongInterface(iVibrationSessionCallback);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return ICancellationSignal.Stub.asInterface(parcelObtain2.readStrongBinder());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IVibratorManagerService
            public int semGetNumberOfSupportedPatterns() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVibratorManagerService.DESCRIPTOR);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IVibratorManagerService
            public int getSupportedVibratorGroup() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVibratorManagerService.DESCRIPTOR);
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IVibratorManagerService
            public String executeVibrationDebugCommand(VibrationDebugInfo vibrationDebugInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVibratorManagerService.DESCRIPTOR);
                    parcelObtain.writeTypedObject(vibrationDebugInfo, 0);
                    this.mRemote.transact(15, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
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
