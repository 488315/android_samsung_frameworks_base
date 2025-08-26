package android.app;

import android.Manifest;
import android.app.IOnProjectionStateChangedListener;
import android.app.IUiModeManagerCallback;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.PermissionEnforcer;
import android.os.RemoteException;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public interface IUiModeManager extends IInterface {

    public static class Default implements IUiModeManager {
        @Override // android.app.IUiModeManager
        public void addCallback(IUiModeManagerCallback iUiModeManagerCallback) throws RemoteException {
        }

        @Override // android.app.IUiModeManager
        public void addNightPriorityAllowedPackageFromShell(String str) throws RemoteException {
        }

        @Override // android.app.IUiModeManager
        public void addOnProjectionStateChangedListener(IOnProjectionStateChangedListener iOnProjectionStateChangedListener, int i) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.app.IUiModeManager
        public void disableCarMode(int i) throws RemoteException {
        }

        @Override // android.app.IUiModeManager
        public void disableCarModeByCallingPackage(int i, String str) throws RemoteException {
        }

        @Override // android.app.IUiModeManager
        public void enableCarMode(int i, int i2, String str) throws RemoteException {
        }

        @Override // android.app.IUiModeManager
        public int getActiveProjectionTypes() throws RemoteException {
            return 0;
        }

        @Override // android.app.IUiModeManager
        public int getAttentionModeThemeOverlay() throws RemoteException {
            return 0;
        }

        @Override // android.app.IUiModeManager
        public float getContrast() throws RemoteException {
            return 0.0f;
        }

        @Override // android.app.IUiModeManager
        public int getCurrentModeType() throws RemoteException {
            return 0;
        }

        @Override // android.app.IUiModeManager
        public long getCustomNightModeEnd() throws RemoteException {
            return 0L;
        }

        @Override // android.app.IUiModeManager
        public long getCustomNightModeStart() throws RemoteException {
            return 0L;
        }

        @Override // android.app.IUiModeManager
        public int getForceInvertState() throws RemoteException {
            return 0;
        }

        @Override // android.app.IUiModeManager
        public int getNightMode() throws RemoteException {
            return 0;
        }

        @Override // android.app.IUiModeManager
        public int getNightModeCustomType() throws RemoteException {
            return 0;
        }

        @Override // android.app.IUiModeManager
        public List<String> getNightPriorityAllowedPackagesFromScpm() throws RemoteException {
            return null;
        }

        @Override // android.app.IUiModeManager
        public int getPackageNightMode(String str, int i) throws RemoteException {
            return 0;
        }

        @Override // android.app.IUiModeManager
        public List<String> getProjectingPackages(int i) throws RemoteException {
            return null;
        }

        @Override // android.app.IUiModeManager
        public boolean isNightModeLocked() throws RemoteException {
            return false;
        }

        @Override // android.app.IUiModeManager
        public boolean isUiModeLocked() throws RemoteException {
            return false;
        }

        @Override // android.app.IUiModeManager
        public boolean releaseProjection(int i, String str) throws RemoteException {
            return false;
        }

        @Override // android.app.IUiModeManager
        public void removeOnProjectionStateChangedListener(IOnProjectionStateChangedListener iOnProjectionStateChangedListener) throws RemoteException {
        }

        @Override // android.app.IUiModeManager
        public boolean requestProjection(IBinder iBinder, int i, String str) throws RemoteException {
            return false;
        }

        @Override // android.app.IUiModeManager
        public void resetNightPriorityAppliedPackages(int i) throws RemoteException {
        }

        @Override // android.app.IUiModeManager
        public void setApplicationNightMode(int i) throws RemoteException {
        }

        @Override // android.app.IUiModeManager
        public void setAttentionModeThemeOverlay(int i) throws RemoteException {
        }

        @Override // android.app.IUiModeManager
        public void setCustomNightModeEnd(long j) throws RemoteException {
        }

        @Override // android.app.IUiModeManager
        public void setCustomNightModeStart(long j) throws RemoteException {
        }

        @Override // android.app.IUiModeManager
        public void setDesktopMode(boolean z) throws RemoteException {
        }

        @Override // android.app.IUiModeManager
        public void setNightMode(int i) throws RemoteException {
        }

        @Override // android.app.IUiModeManager
        public boolean setNightModeActivated(boolean z) throws RemoteException {
            return false;
        }

        @Override // android.app.IUiModeManager
        public boolean setNightModeActivatedForCustomMode(int i, boolean z) throws RemoteException {
            return false;
        }

        @Override // android.app.IUiModeManager
        public void setNightModeCustomType(int i) throws RemoteException {
        }

        @Override // android.app.IUiModeManager
        public void setNightPriorityAllowedPackagesFromScpm(List<String> list) throws RemoteException {
        }

        @Override // android.app.IUiModeManager
        public void setPackageNightMode(String str, int i, int i2) throws RemoteException {
        }
    }

    void addCallback(IUiModeManagerCallback iUiModeManagerCallback) throws RemoteException;

    void addNightPriorityAllowedPackageFromShell(String str) throws RemoteException;

    void addOnProjectionStateChangedListener(IOnProjectionStateChangedListener iOnProjectionStateChangedListener, int i) throws RemoteException;

    void disableCarMode(int i) throws RemoteException;

    void disableCarModeByCallingPackage(int i, String str) throws RemoteException;

    void enableCarMode(int i, int i2, String str) throws RemoteException;

    int getActiveProjectionTypes() throws RemoteException;

    int getAttentionModeThemeOverlay() throws RemoteException;

    float getContrast() throws RemoteException;

    int getCurrentModeType() throws RemoteException;

    long getCustomNightModeEnd() throws RemoteException;

    long getCustomNightModeStart() throws RemoteException;

    int getForceInvertState() throws RemoteException;

    int getNightMode() throws RemoteException;

    int getNightModeCustomType() throws RemoteException;

    List<String> getNightPriorityAllowedPackagesFromScpm() throws RemoteException;

    int getPackageNightMode(String str, int i) throws RemoteException;

    List<String> getProjectingPackages(int i) throws RemoteException;

    boolean isNightModeLocked() throws RemoteException;

    boolean isUiModeLocked() throws RemoteException;

    boolean releaseProjection(int i, String str) throws RemoteException;

    void removeOnProjectionStateChangedListener(IOnProjectionStateChangedListener iOnProjectionStateChangedListener) throws RemoteException;

    boolean requestProjection(IBinder iBinder, int i, String str) throws RemoteException;

    void resetNightPriorityAppliedPackages(int i) throws RemoteException;

    void setApplicationNightMode(int i) throws RemoteException;

    void setAttentionModeThemeOverlay(int i) throws RemoteException;

    void setCustomNightModeEnd(long j) throws RemoteException;

    void setCustomNightModeStart(long j) throws RemoteException;

    void setDesktopMode(boolean z) throws RemoteException;

    void setNightMode(int i) throws RemoteException;

    boolean setNightModeActivated(boolean z) throws RemoteException;

    boolean setNightModeActivatedForCustomMode(int i, boolean z) throws RemoteException;

    void setNightModeCustomType(int i) throws RemoteException;

    void setNightPriorityAllowedPackagesFromScpm(List<String> list) throws RemoteException;

    void setPackageNightMode(String str, int i, int i2) throws RemoteException;

    public static abstract class Stub extends Binder implements IUiModeManager {
        public static final String DESCRIPTOR = "android.app.IUiModeManager";
        static final int TRANSACTION_addCallback = 1;
        static final int TRANSACTION_addNightPriorityAllowedPackageFromShell = 17;
        static final int TRANSACTION_addOnProjectionStateChangedListener = 29;
        static final int TRANSACTION_disableCarMode = 3;
        static final int TRANSACTION_disableCarModeByCallingPackage = 4;
        static final int TRANSACTION_enableCarMode = 2;
        static final int TRANSACTION_getActiveProjectionTypes = 32;
        static final int TRANSACTION_getAttentionModeThemeOverlay = 11;
        static final int TRANSACTION_getContrast = 33;
        static final int TRANSACTION_getCurrentModeType = 5;
        static final int TRANSACTION_getCustomNightModeEnd = 25;
        static final int TRANSACTION_getCustomNightModeStart = 23;
        static final int TRANSACTION_getForceInvertState = 34;
        static final int TRANSACTION_getNightMode = 7;
        static final int TRANSACTION_getNightModeCustomType = 9;
        static final int TRANSACTION_getNightPriorityAllowedPackagesFromScpm = 16;
        static final int TRANSACTION_getPackageNightMode = 14;
        static final int TRANSACTION_getProjectingPackages = 31;
        static final int TRANSACTION_isNightModeLocked = 20;
        static final int TRANSACTION_isUiModeLocked = 19;
        static final int TRANSACTION_releaseProjection = 28;
        static final int TRANSACTION_removeOnProjectionStateChangedListener = 30;
        static final int TRANSACTION_requestProjection = 27;
        static final int TRANSACTION_resetNightPriorityAppliedPackages = 18;
        static final int TRANSACTION_setApplicationNightMode = 12;
        static final int TRANSACTION_setAttentionModeThemeOverlay = 10;
        static final int TRANSACTION_setCustomNightModeEnd = 26;
        static final int TRANSACTION_setCustomNightModeStart = 24;
        static final int TRANSACTION_setDesktopMode = 35;
        static final int TRANSACTION_setNightMode = 6;
        static final int TRANSACTION_setNightModeActivated = 22;
        static final int TRANSACTION_setNightModeActivatedForCustomMode = 21;
        static final int TRANSACTION_setNightModeCustomType = 8;
        static final int TRANSACTION_setNightPriorityAllowedPackagesFromScpm = 15;
        static final int TRANSACTION_setPackageNightMode = 13;
        private final PermissionEnforcer mEnforcer;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 34;
        }

        public Stub(PermissionEnforcer permissionEnforcer) {
            attachInterface(this, DESCRIPTOR);
            if (permissionEnforcer == null) {
                throw new IllegalArgumentException("enforcer cannot be null");
            }
            this.mEnforcer = permissionEnforcer;
        }

        @Deprecated
        public Stub() {
            this(PermissionEnforcer.fromContext(ActivityThread.currentActivityThread().getSystemContext()));
        }

        public static IUiModeManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IUiModeManager)) {
                return (IUiModeManager) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "addCallback";
                case 2:
                    return "enableCarMode";
                case 3:
                    return "disableCarMode";
                case 4:
                    return "disableCarModeByCallingPackage";
                case 5:
                    return "getCurrentModeType";
                case 6:
                    return "setNightMode";
                case 7:
                    return "getNightMode";
                case 8:
                    return "setNightModeCustomType";
                case 9:
                    return "getNightModeCustomType";
                case 10:
                    return "setAttentionModeThemeOverlay";
                case 11:
                    return "getAttentionModeThemeOverlay";
                case 12:
                    return "setApplicationNightMode";
                case 13:
                    return "setPackageNightMode";
                case 14:
                    return "getPackageNightMode";
                case 15:
                    return "setNightPriorityAllowedPackagesFromScpm";
                case 16:
                    return "getNightPriorityAllowedPackagesFromScpm";
                case 17:
                    return "addNightPriorityAllowedPackageFromShell";
                case 18:
                    return "resetNightPriorityAppliedPackages";
                case 19:
                    return "isUiModeLocked";
                case 20:
                    return "isNightModeLocked";
                case 21:
                    return "setNightModeActivatedForCustomMode";
                case 22:
                    return "setNightModeActivated";
                case 23:
                    return "getCustomNightModeStart";
                case 24:
                    return "setCustomNightModeStart";
                case 25:
                    return "getCustomNightModeEnd";
                case 26:
                    return "setCustomNightModeEnd";
                case 27:
                    return "requestProjection";
                case 28:
                    return "releaseProjection";
                case 29:
                    return "addOnProjectionStateChangedListener";
                case 30:
                    return "removeOnProjectionStateChangedListener";
                case 31:
                    return "getProjectingPackages";
                case 32:
                    return "getActiveProjectionTypes";
                case 33:
                    return "getContrast";
                case 34:
                    return "getForceInvertState";
                case 35:
                    return "setDesktopMode";
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
                parcel.enforceInterface(DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    IUiModeManagerCallback iUiModeManagerCallbackAsInterface = IUiModeManagerCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    addCallback(iUiModeManagerCallbackAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    int i3 = parcel.readInt();
                    int i4 = parcel.readInt();
                    String string = parcel.readString();
                    parcel.enforceNoDataAvail();
                    enableCarMode(i3, i4, string);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    int i5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    disableCarMode(i5);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    int i6 = parcel.readInt();
                    String string2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    disableCarModeByCallingPackage(i6, string2);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    int currentModeType = getCurrentModeType();
                    parcel2.writeNoException();
                    parcel2.writeInt(currentModeType);
                    return true;
                case 6:
                    int i7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setNightMode(i7);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    int nightMode = getNightMode();
                    parcel2.writeNoException();
                    parcel2.writeInt(nightMode);
                    return true;
                case 8:
                    int i8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setNightModeCustomType(i8);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    int nightModeCustomType = getNightModeCustomType();
                    parcel2.writeNoException();
                    parcel2.writeInt(nightModeCustomType);
                    return true;
                case 10:
                    int i9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setAttentionModeThemeOverlay(i9);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    int attentionModeThemeOverlay = getAttentionModeThemeOverlay();
                    parcel2.writeNoException();
                    parcel2.writeInt(attentionModeThemeOverlay);
                    return true;
                case 12:
                    int i10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setApplicationNightMode(i10);
                    parcel2.writeNoException();
                    return true;
                case 13:
                    String string3 = parcel.readString();
                    int i11 = parcel.readInt();
                    int i12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setPackageNightMode(string3, i11, i12);
                    parcel2.writeNoException();
                    return true;
                case 14:
                    String string4 = parcel.readString();
                    int i13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int packageNightMode = getPackageNightMode(string4, i13);
                    parcel2.writeNoException();
                    parcel2.writeInt(packageNightMode);
                    return true;
                case 15:
                    ArrayList<String> arrayListCreateStringArrayList = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    setNightPriorityAllowedPackagesFromScpm(arrayListCreateStringArrayList);
                    parcel2.writeNoException();
                    return true;
                case 16:
                    List<String> nightPriorityAllowedPackagesFromScpm = getNightPriorityAllowedPackagesFromScpm();
                    parcel2.writeNoException();
                    parcel2.writeStringList(nightPriorityAllowedPackagesFromScpm);
                    return true;
                case 17:
                    String string5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    addNightPriorityAllowedPackageFromShell(string5);
                    parcel2.writeNoException();
                    return true;
                case 18:
                    int i14 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    resetNightPriorityAppliedPackages(i14);
                    parcel2.writeNoException();
                    return true;
                case 19:
                    boolean zIsUiModeLocked = isUiModeLocked();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsUiModeLocked);
                    return true;
                case 20:
                    boolean zIsNightModeLocked = isNightModeLocked();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsNightModeLocked);
                    return true;
                case 21:
                    int i15 = parcel.readInt();
                    boolean z = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean nightModeActivatedForCustomMode = setNightModeActivatedForCustomMode(i15, z);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(nightModeActivatedForCustomMode);
                    return true;
                case 22:
                    boolean z2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean nightModeActivated = setNightModeActivated(z2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(nightModeActivated);
                    return true;
                case 23:
                    long customNightModeStart = getCustomNightModeStart();
                    parcel2.writeNoException();
                    parcel2.writeLong(customNightModeStart);
                    return true;
                case 24:
                    long j = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    setCustomNightModeStart(j);
                    parcel2.writeNoException();
                    return true;
                case 25:
                    long customNightModeEnd = getCustomNightModeEnd();
                    parcel2.writeNoException();
                    parcel2.writeLong(customNightModeEnd);
                    return true;
                case 26:
                    long j2 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    setCustomNightModeEnd(j2);
                    parcel2.writeNoException();
                    return true;
                case 27:
                    IBinder strongBinder = parcel.readStrongBinder();
                    int i16 = parcel.readInt();
                    String string6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zRequestProjection = requestProjection(strongBinder, i16, string6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zRequestProjection);
                    return true;
                case 28:
                    int i17 = parcel.readInt();
                    String string7 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zReleaseProjection = releaseProjection(i17, string7);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zReleaseProjection);
                    return true;
                case 29:
                    IOnProjectionStateChangedListener iOnProjectionStateChangedListenerAsInterface = IOnProjectionStateChangedListener.Stub.asInterface(parcel.readStrongBinder());
                    int i18 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    addOnProjectionStateChangedListener(iOnProjectionStateChangedListenerAsInterface, i18);
                    parcel2.writeNoException();
                    return true;
                case 30:
                    IOnProjectionStateChangedListener iOnProjectionStateChangedListenerAsInterface2 = IOnProjectionStateChangedListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    removeOnProjectionStateChangedListener(iOnProjectionStateChangedListenerAsInterface2);
                    parcel2.writeNoException();
                    return true;
                case 31:
                    int i19 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<String> projectingPackages = getProjectingPackages(i19);
                    parcel2.writeNoException();
                    parcel2.writeStringList(projectingPackages);
                    return true;
                case 32:
                    int activeProjectionTypes = getActiveProjectionTypes();
                    parcel2.writeNoException();
                    parcel2.writeInt(activeProjectionTypes);
                    return true;
                case 33:
                    float contrast = getContrast();
                    parcel2.writeNoException();
                    parcel2.writeFloat(contrast);
                    return true;
                case 34:
                    int forceInvertState = getForceInvertState();
                    parcel2.writeNoException();
                    parcel2.writeInt(forceInvertState);
                    return true;
                case 35:
                    boolean z3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setDesktopMode(z3);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IUiModeManager {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // android.app.IUiModeManager
            public void addCallback(IUiModeManagerCallback iUiModeManagerCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iUiModeManagerCallback);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IUiModeManager
            public void enableCarMode(int i, int i2, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IUiModeManager
            public void disableCarMode(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IUiModeManager
            public void disableCarModeByCallingPackage(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IUiModeManager
            public int getCurrentModeType() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IUiModeManager
            public void setNightMode(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IUiModeManager
            public int getNightMode() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IUiModeManager
            public void setNightModeCustomType(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IUiModeManager
            public int getNightModeCustomType() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IUiModeManager
            public void setAttentionModeThemeOverlay(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IUiModeManager
            public int getAttentionModeThemeOverlay() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IUiModeManager
            public void setApplicationNightMode(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IUiModeManager
            public void setPackageNightMode(String str, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IUiModeManager
            public int getPackageNightMode(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IUiModeManager
            public void setNightPriorityAllowedPackagesFromScpm(List<String> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStringList(list);
                    this.mRemote.transact(15, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IUiModeManager
            public List<String> getNightPriorityAllowedPackagesFromScpm() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(16, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IUiModeManager
            public void addNightPriorityAllowedPackageFromShell(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(17, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IUiModeManager
            public void resetNightPriorityAppliedPackages(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(18, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IUiModeManager
            public boolean isUiModeLocked() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(19, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IUiModeManager
            public boolean isNightModeLocked() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(20, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IUiModeManager
            public boolean setNightModeActivatedForCustomMode(int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(21, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IUiModeManager
            public boolean setNightModeActivated(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(22, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IUiModeManager
            public long getCustomNightModeStart() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(23, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readLong();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IUiModeManager
            public void setCustomNightModeStart(long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(24, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IUiModeManager
            public long getCustomNightModeEnd() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(25, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readLong();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IUiModeManager
            public void setCustomNightModeEnd(long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(26, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IUiModeManager
            public boolean requestProjection(IBinder iBinder, int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(27, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IUiModeManager
            public boolean releaseProjection(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(28, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IUiModeManager
            public void addOnProjectionStateChangedListener(IOnProjectionStateChangedListener iOnProjectionStateChangedListener, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iOnProjectionStateChangedListener);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(29, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IUiModeManager
            public void removeOnProjectionStateChangedListener(IOnProjectionStateChangedListener iOnProjectionStateChangedListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iOnProjectionStateChangedListener);
                    this.mRemote.transact(30, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IUiModeManager
            public List<String> getProjectingPackages(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(31, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IUiModeManager
            public int getActiveProjectionTypes() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(32, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IUiModeManager
            public float getContrast() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(33, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readFloat();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IUiModeManager
            public int getForceInvertState() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(34, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IUiModeManager
            public void setDesktopMode(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(35, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        protected void setNightModeCustomType_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MODIFY_DAY_NIGHT_MODE, getCallingPid(), getCallingUid());
        }

        protected void getNightModeCustomType_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MODIFY_DAY_NIGHT_MODE, getCallingPid(), getCallingUid());
        }

        protected void setAttentionModeThemeOverlay_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MODIFY_DAY_NIGHT_MODE, getCallingPid(), getCallingUid());
        }

        protected void getAttentionModeThemeOverlay_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MODIFY_DAY_NIGHT_MODE, getCallingPid(), getCallingUid());
        }

        protected void addOnProjectionStateChangedListener_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.READ_PROJECTION_STATE, getCallingPid(), getCallingUid());
        }

        protected void removeOnProjectionStateChangedListener_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.READ_PROJECTION_STATE, getCallingPid(), getCallingUid());
        }

        protected void getProjectingPackages_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.READ_PROJECTION_STATE, getCallingPid(), getCallingUid());
        }

        protected void getActiveProjectionTypes_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.READ_PROJECTION_STATE, getCallingPid(), getCallingUid());
        }
    }
}
