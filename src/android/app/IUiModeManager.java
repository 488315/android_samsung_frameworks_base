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
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IUiModeManager)) {
                return (IUiModeManager) queryLocalInterface;
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
                    IUiModeManagerCallback asInterface = IUiModeManagerCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    addCallback(asInterface);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    int readInt = parcel.readInt();
                    int readInt2 = parcel.readInt();
                    String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    enableCarMode(readInt, readInt2, readString);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    disableCarMode(readInt3);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    int readInt4 = parcel.readInt();
                    String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    disableCarModeByCallingPackage(readInt4, readString2);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    int currentModeType = getCurrentModeType();
                    parcel2.writeNoException();
                    parcel2.writeInt(currentModeType);
                    return true;
                case 6:
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setNightMode(readInt5);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    int nightMode = getNightMode();
                    parcel2.writeNoException();
                    parcel2.writeInt(nightMode);
                    return true;
                case 8:
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setNightModeCustomType(readInt6);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    int nightModeCustomType = getNightModeCustomType();
                    parcel2.writeNoException();
                    parcel2.writeInt(nightModeCustomType);
                    return true;
                case 10:
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setAttentionModeThemeOverlay(readInt7);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    int attentionModeThemeOverlay = getAttentionModeThemeOverlay();
                    parcel2.writeNoException();
                    parcel2.writeInt(attentionModeThemeOverlay);
                    return true;
                case 12:
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setApplicationNightMode(readInt8);
                    parcel2.writeNoException();
                    return true;
                case 13:
                    String readString3 = parcel.readString();
                    int readInt9 = parcel.readInt();
                    int readInt10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setPackageNightMode(readString3, readInt9, readInt10);
                    parcel2.writeNoException();
                    return true;
                case 14:
                    String readString4 = parcel.readString();
                    int readInt11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int packageNightMode = getPackageNightMode(readString4, readInt11);
                    parcel2.writeNoException();
                    parcel2.writeInt(packageNightMode);
                    return true;
                case 15:
                    ArrayList<String> createStringArrayList = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    setNightPriorityAllowedPackagesFromScpm(createStringArrayList);
                    parcel2.writeNoException();
                    return true;
                case 16:
                    List<String> nightPriorityAllowedPackagesFromScpm = getNightPriorityAllowedPackagesFromScpm();
                    parcel2.writeNoException();
                    parcel2.writeStringList(nightPriorityAllowedPackagesFromScpm);
                    return true;
                case 17:
                    String readString5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    addNightPriorityAllowedPackageFromShell(readString5);
                    parcel2.writeNoException();
                    return true;
                case 18:
                    int readInt12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    resetNightPriorityAppliedPackages(readInt12);
                    parcel2.writeNoException();
                    return true;
                case 19:
                    boolean isUiModeLocked = isUiModeLocked();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isUiModeLocked);
                    return true;
                case 20:
                    boolean isNightModeLocked = isNightModeLocked();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isNightModeLocked);
                    return true;
                case 21:
                    int readInt13 = parcel.readInt();
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean nightModeActivatedForCustomMode = setNightModeActivatedForCustomMode(readInt13, readBoolean);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(nightModeActivatedForCustomMode);
                    return true;
                case 22:
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean nightModeActivated = setNightModeActivated(readBoolean2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(nightModeActivated);
                    return true;
                case 23:
                    long customNightModeStart = getCustomNightModeStart();
                    parcel2.writeNoException();
                    parcel2.writeLong(customNightModeStart);
                    return true;
                case 24:
                    long readLong = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    setCustomNightModeStart(readLong);
                    parcel2.writeNoException();
                    return true;
                case 25:
                    long customNightModeEnd = getCustomNightModeEnd();
                    parcel2.writeNoException();
                    parcel2.writeLong(customNightModeEnd);
                    return true;
                case 26:
                    long readLong2 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    setCustomNightModeEnd(readLong2);
                    parcel2.writeNoException();
                    return true;
                case 27:
                    IBinder readStrongBinder = parcel.readStrongBinder();
                    int readInt14 = parcel.readInt();
                    String readString6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean requestProjection = requestProjection(readStrongBinder, readInt14, readString6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(requestProjection);
                    return true;
                case 28:
                    int readInt15 = parcel.readInt();
                    String readString7 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean releaseProjection = releaseProjection(readInt15, readString7);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(releaseProjection);
                    return true;
                case 29:
                    IOnProjectionStateChangedListener asInterface2 = IOnProjectionStateChangedListener.Stub.asInterface(parcel.readStrongBinder());
                    int readInt16 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    addOnProjectionStateChangedListener(asInterface2, readInt16);
                    parcel2.writeNoException();
                    return true;
                case 30:
                    IOnProjectionStateChangedListener asInterface3 = IOnProjectionStateChangedListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    removeOnProjectionStateChangedListener(asInterface3);
                    parcel2.writeNoException();
                    return true;
                case 31:
                    int readInt17 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<String> projectingPackages = getProjectingPackages(readInt17);
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
                    boolean readBoolean3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setDesktopMode(readBoolean3);
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
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iUiModeManagerCallback);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IUiModeManager
            public void enableCarMode(int i, int i2, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IUiModeManager
            public void disableCarMode(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IUiModeManager
            public void disableCarModeByCallingPackage(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IUiModeManager
            public int getCurrentModeType() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IUiModeManager
            public void setNightMode(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IUiModeManager
            public int getNightMode() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IUiModeManager
            public void setNightModeCustomType(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IUiModeManager
            public int getNightModeCustomType() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IUiModeManager
            public void setAttentionModeThemeOverlay(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IUiModeManager
            public int getAttentionModeThemeOverlay() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IUiModeManager
            public void setApplicationNightMode(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IUiModeManager
            public void setPackageNightMode(String str, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IUiModeManager
            public int getPackageNightMode(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IUiModeManager
            public void setNightPriorityAllowedPackagesFromScpm(List<String> list) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStringList(list);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IUiModeManager
            public List<String> getNightPriorityAllowedPackagesFromScpm() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IUiModeManager
            public void addNightPriorityAllowedPackageFromShell(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IUiModeManager
            public void resetNightPriorityAppliedPackages(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IUiModeManager
            public boolean isUiModeLocked() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IUiModeManager
            public boolean isNightModeLocked() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IUiModeManager
            public boolean setNightModeActivatedForCustomMode(int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IUiModeManager
            public boolean setNightModeActivated(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IUiModeManager
            public long getCustomNightModeStart() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IUiModeManager
            public void setCustomNightModeStart(long j) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeLong(j);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IUiModeManager
            public long getCustomNightModeEnd() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IUiModeManager
            public void setCustomNightModeEnd(long j) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeLong(j);
                    this.mRemote.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IUiModeManager
            public boolean requestProjection(IBinder iBinder, int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(27, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IUiModeManager
            public boolean releaseProjection(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(28, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IUiModeManager
            public void addOnProjectionStateChangedListener(IOnProjectionStateChangedListener iOnProjectionStateChangedListener, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iOnProjectionStateChangedListener);
                    obtain.writeInt(i);
                    this.mRemote.transact(29, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IUiModeManager
            public void removeOnProjectionStateChangedListener(IOnProjectionStateChangedListener iOnProjectionStateChangedListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iOnProjectionStateChangedListener);
                    this.mRemote.transact(30, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IUiModeManager
            public List<String> getProjectingPackages(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(31, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IUiModeManager
            public int getActiveProjectionTypes() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(32, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IUiModeManager
            public float getContrast() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(33, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readFloat();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IUiModeManager
            public int getForceInvertState() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(34, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IUiModeManager
            public void setDesktopMode(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(35, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
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
