package com.samsung.android.knox.kiosk;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.samsung.android.knox.ContextInfo;
import java.util.List;
import java.util.Map;

/* loaded from: classes4.dex */
public interface IKioskMode extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.kiosk.IKioskMode";

    boolean allowAirCommandMode(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean allowAirViewMode(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean allowEdgeScreen(ContextInfo contextInfo, int i, boolean z) throws RemoteException;

    int[] allowHardwareKeys(ContextInfo contextInfo, int[] iArr, boolean z) throws RemoteException;

    boolean allowMultiWindowMode(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean allowTaskManager(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean clearAllNotifications(ContextInfo contextInfo) throws RemoteException;

    void disableKioskMode(ContextInfo contextInfo) throws RemoteException;

    void enableKioskMode(ContextInfo contextInfo, String str) throws RemoteException;

    List getAllBlockedHardwareKeys(ContextInfo contextInfo) throws RemoteException;

    int getBlockedEdgeScreen(ContextInfo contextInfo) throws RemoteException;

    Map getBlockedHwKeysCache() throws RemoteException;

    List getHardwareKeyList(ContextInfo contextInfo) throws RemoteException;

    String getKioskHomePackage(ContextInfo contextInfo) throws RemoteException;

    String getKioskHomePackageAsUser(int i) throws RemoteException;

    boolean hideNavigationBar(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean hideStatusBar(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean hideSystemBar(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean isAirCommandModeAllowed(ContextInfo contextInfo) throws RemoteException;

    boolean isAirViewModeAllowed(ContextInfo contextInfo) throws RemoteException;

    boolean isHardwareKeyAllowed(ContextInfo contextInfo, int i, boolean z) throws RemoteException;

    boolean isKioskModeEnabled(ContextInfo contextInfo) throws RemoteException;

    boolean isKioskModeEnabledAsUser(int i) throws RemoteException;

    boolean isMultiWindowModeAllowed(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean isMultiWindowModeAllowedAsUser(int i) throws RemoteException;

    boolean isNavigationBarHidden(ContextInfo contextInfo) throws RemoteException;

    boolean isStatusBarHidden(ContextInfo contextInfo) throws RemoteException;

    boolean isStatusBarHiddenAsUser(int i) throws RemoteException;

    boolean isSystemBarHidden(ContextInfo contextInfo) throws RemoteException;

    boolean isTaskManagerAllowed(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean isTaskManagerAllowedAsUser(boolean z, int i) throws RemoteException;

    boolean wipeRecentTasks(ContextInfo contextInfo) throws RemoteException;

    public abstract class Stub extends Binder implements IKioskMode {
        public static final int TRANSACTION_allowAirCommandMode = 28;
        public static final int TRANSACTION_allowAirViewMode = 30;
        public static final int TRANSACTION_allowEdgeScreen = 31;
        public static final int TRANSACTION_allowHardwareKeys = 15;
        public static final int TRANSACTION_allowMultiWindowMode = 23;
        public static final int TRANSACTION_allowTaskManager = 11;
        public static final int TRANSACTION_clearAllNotifications = 26;
        public static final int TRANSACTION_disableKioskMode = 2;
        public static final int TRANSACTION_enableKioskMode = 1;
        public static final int TRANSACTION_getAllBlockedHardwareKeys = 17;
        public static final int TRANSACTION_getBlockedEdgeScreen = 32;
        public static final int TRANSACTION_getBlockedHwKeysCache = 18;
        public static final int TRANSACTION_getHardwareKeyList = 14;
        public static final int TRANSACTION_getKioskHomePackage = 5;
        public static final int TRANSACTION_getKioskHomePackageAsUser = 6;
        public static final int TRANSACTION_hideNavigationBar = 21;
        public static final int TRANSACTION_hideStatusBar = 19;
        public static final int TRANSACTION_hideSystemBar = 7;
        public static final int TRANSACTION_isAirCommandModeAllowed = 27;
        public static final int TRANSACTION_isAirViewModeAllowed = 29;
        public static final int TRANSACTION_isHardwareKeyAllowed = 16;
        public static final int TRANSACTION_isKioskModeEnabled = 3;
        public static final int TRANSACTION_isKioskModeEnabledAsUser = 4;
        public static final int TRANSACTION_isMultiWindowModeAllowed = 24;
        public static final int TRANSACTION_isMultiWindowModeAllowedAsUser = 25;
        public static final int TRANSACTION_isNavigationBarHidden = 22;
        public static final int TRANSACTION_isStatusBarHidden = 20;
        public static final int TRANSACTION_isStatusBarHiddenAsUser = 9;
        public static final int TRANSACTION_isSystemBarHidden = 8;
        public static final int TRANSACTION_isTaskManagerAllowed = 12;
        public static final int TRANSACTION_isTaskManagerAllowedAsUser = 13;
        public static final int TRANSACTION_wipeRecentTasks = 10;

        class Proxy implements IKioskMode {
            public IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // com.samsung.android.knox.kiosk.IKioskMode
            public boolean allowAirCommandMode(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKioskMode.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(28, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.kiosk.IKioskMode
            public boolean allowAirViewMode(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKioskMode.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(30, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.kiosk.IKioskMode
            public boolean allowEdgeScreen(ContextInfo contextInfo, int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKioskMode.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(31, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.kiosk.IKioskMode
            public int[] allowHardwareKeys(ContextInfo contextInfo, int[] iArr, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKioskMode.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeIntArray(iArr);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(15, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createIntArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.kiosk.IKioskMode
            public boolean allowMultiWindowMode(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKioskMode.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(23, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.kiosk.IKioskMode
            public boolean allowTaskManager(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKioskMode.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.samsung.android.knox.kiosk.IKioskMode
            public boolean clearAllNotifications(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKioskMode.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(26, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.kiosk.IKioskMode
            public void disableKioskMode(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKioskMode.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.kiosk.IKioskMode
            public void enableKioskMode(ContextInfo contextInfo, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKioskMode.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.kiosk.IKioskMode
            public List getAllBlockedHardwareKeys(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKioskMode.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(17, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readArrayList(getClass().getClassLoader());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.kiosk.IKioskMode
            public int getBlockedEdgeScreen(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKioskMode.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(32, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.kiosk.IKioskMode
            public Map getBlockedHwKeysCache() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKioskMode.DESCRIPTOR);
                    this.mRemote.transact(18, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readHashMap(getClass().getClassLoader());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.kiosk.IKioskMode
            public List getHardwareKeyList(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKioskMode.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readArrayList(getClass().getClassLoader());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            public String getInterfaceDescriptor() {
                return IKioskMode.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.kiosk.IKioskMode
            public String getKioskHomePackage(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKioskMode.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.kiosk.IKioskMode
            public String getKioskHomePackageAsUser(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKioskMode.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.kiosk.IKioskMode
            public boolean hideNavigationBar(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKioskMode.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(21, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.kiosk.IKioskMode
            public boolean hideStatusBar(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKioskMode.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(19, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.kiosk.IKioskMode
            public boolean hideSystemBar(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKioskMode.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.kiosk.IKioskMode
            public boolean isAirCommandModeAllowed(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKioskMode.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(27, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.kiosk.IKioskMode
            public boolean isAirViewModeAllowed(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKioskMode.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(29, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.kiosk.IKioskMode
            public boolean isHardwareKeyAllowed(ContextInfo contextInfo, int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKioskMode.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(16, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.kiosk.IKioskMode
            public boolean isKioskModeEnabled(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKioskMode.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.kiosk.IKioskMode
            public boolean isKioskModeEnabledAsUser(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKioskMode.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.kiosk.IKioskMode
            public boolean isMultiWindowModeAllowed(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKioskMode.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(24, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.kiosk.IKioskMode
            public boolean isMultiWindowModeAllowedAsUser(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKioskMode.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(25, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.kiosk.IKioskMode
            public boolean isNavigationBarHidden(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKioskMode.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(22, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.kiosk.IKioskMode
            public boolean isStatusBarHidden(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKioskMode.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(20, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.kiosk.IKioskMode
            public boolean isStatusBarHiddenAsUser(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKioskMode.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.kiosk.IKioskMode
            public boolean isSystemBarHidden(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKioskMode.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.kiosk.IKioskMode
            public boolean isTaskManagerAllowed(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKioskMode.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.kiosk.IKioskMode
            public boolean isTaskManagerAllowedAsUser(boolean z, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKioskMode.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.kiosk.IKioskMode
            public boolean wipeRecentTasks(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKioskMode.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, IKioskMode.DESCRIPTOR);
        }

        public static IKioskMode asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IKioskMode.DESCRIPTOR);
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof IKioskMode)) ? new Proxy(iBinder) : (IKioskMode) iInterfaceQueryLocalInterface;
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "enableKioskMode";
                case 2:
                    return "disableKioskMode";
                case 3:
                    return "isKioskModeEnabled";
                case 4:
                    return "isKioskModeEnabledAsUser";
                case 5:
                    return "getKioskHomePackage";
                case 6:
                    return "getKioskHomePackageAsUser";
                case 7:
                    return "hideSystemBar";
                case 8:
                    return "isSystemBarHidden";
                case 9:
                    return "isStatusBarHiddenAsUser";
                case 10:
                    return "wipeRecentTasks";
                case 11:
                    return "allowTaskManager";
                case 12:
                    return "isTaskManagerAllowed";
                case 13:
                    return "isTaskManagerAllowedAsUser";
                case 14:
                    return "getHardwareKeyList";
                case 15:
                    return "allowHardwareKeys";
                case 16:
                    return "isHardwareKeyAllowed";
                case 17:
                    return "getAllBlockedHardwareKeys";
                case 18:
                    return "getBlockedHwKeysCache";
                case 19:
                    return "hideStatusBar";
                case 20:
                    return "isStatusBarHidden";
                case 21:
                    return "hideNavigationBar";
                case 22:
                    return "isNavigationBarHidden";
                case 23:
                    return "allowMultiWindowMode";
                case 24:
                    return "isMultiWindowModeAllowed";
                case 25:
                    return "isMultiWindowModeAllowedAsUser";
                case 26:
                    return "clearAllNotifications";
                case 27:
                    return "isAirCommandModeAllowed";
                case 28:
                    return "allowAirCommandMode";
                case 29:
                    return "isAirViewModeAllowed";
                case 30:
                    return "allowAirViewMode";
                case 31:
                    return "allowEdgeScreen";
                case 32:
                    return "getBlockedEdgeScreen";
                default:
                    return null;
            }
        }

        public int getMaxTransactionId() {
            return 31;
        }

        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IKioskMode.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IKioskMode.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    ContextInfo contextInfo = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string = parcel.readString();
                    parcel.enforceNoDataAvail();
                    enableKioskMode(contextInfo, string);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    ContextInfo contextInfo2 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    disableKioskMode(contextInfo2);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    ContextInfo contextInfo3 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zIsKioskModeEnabled = isKioskModeEnabled(contextInfo3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsKioskModeEnabled);
                    return true;
                case 4:
                    int i3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsKioskModeEnabledAsUser = isKioskModeEnabledAsUser(i3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsKioskModeEnabledAsUser);
                    return true;
                case 5:
                    ContextInfo contextInfo4 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    String kioskHomePackage = getKioskHomePackage(contextInfo4);
                    parcel2.writeNoException();
                    parcel2.writeString(kioskHomePackage);
                    return true;
                case 6:
                    int i4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String kioskHomePackageAsUser = getKioskHomePackageAsUser(i4);
                    parcel2.writeNoException();
                    parcel2.writeString(kioskHomePackageAsUser);
                    return true;
                case 7:
                    ContextInfo contextInfo5 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zHideSystemBar = hideSystemBar(contextInfo5, z);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zHideSystemBar);
                    return true;
                case 8:
                    ContextInfo contextInfo6 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zIsSystemBarHidden = isSystemBarHidden(contextInfo6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsSystemBarHidden);
                    return true;
                case 9:
                    int i5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsStatusBarHiddenAsUser = isStatusBarHiddenAsUser(i5);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsStatusBarHiddenAsUser);
                    return true;
                case 10:
                    ContextInfo contextInfo7 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zWipeRecentTasks = wipeRecentTasks(contextInfo7);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zWipeRecentTasks);
                    return true;
                case 11:
                    ContextInfo contextInfo8 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zAllowTaskManager = allowTaskManager(contextInfo8, z2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAllowTaskManager);
                    return true;
                case 12:
                    ContextInfo contextInfo9 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zIsTaskManagerAllowed = isTaskManagerAllowed(contextInfo9, z3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsTaskManagerAllowed);
                    return true;
                case 13:
                    boolean z4 = parcel.readBoolean();
                    int i6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsTaskManagerAllowedAsUser = isTaskManagerAllowedAsUser(z4, i6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsTaskManagerAllowedAsUser);
                    return true;
                case 14:
                    ContextInfo contextInfo10 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    List hardwareKeyList = getHardwareKeyList(contextInfo10);
                    parcel2.writeNoException();
                    parcel2.writeList(hardwareKeyList);
                    return true;
                case 15:
                    ContextInfo contextInfo11 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    int[] iArrCreateIntArray = parcel.createIntArray();
                    boolean z5 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int[] iArrAllowHardwareKeys = allowHardwareKeys(contextInfo11, iArrCreateIntArray, z5);
                    parcel2.writeNoException();
                    parcel2.writeIntArray(iArrAllowHardwareKeys);
                    return true;
                case 16:
                    ContextInfo contextInfo12 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    int i7 = parcel.readInt();
                    boolean z6 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zIsHardwareKeyAllowed = isHardwareKeyAllowed(contextInfo12, i7, z6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsHardwareKeyAllowed);
                    return true;
                case 17:
                    ContextInfo contextInfo13 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    List allBlockedHardwareKeys = getAllBlockedHardwareKeys(contextInfo13);
                    parcel2.writeNoException();
                    parcel2.writeList(allBlockedHardwareKeys);
                    return true;
                case 18:
                    Map blockedHwKeysCache = getBlockedHwKeysCache();
                    parcel2.writeNoException();
                    parcel2.writeMap(blockedHwKeysCache);
                    return true;
                case 19:
                    ContextInfo contextInfo14 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z7 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zHideStatusBar = hideStatusBar(contextInfo14, z7);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zHideStatusBar);
                    return true;
                case 20:
                    ContextInfo contextInfo15 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zIsStatusBarHidden = isStatusBarHidden(contextInfo15);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsStatusBarHidden);
                    return true;
                case 21:
                    ContextInfo contextInfo16 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z8 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zHideNavigationBar = hideNavigationBar(contextInfo16, z8);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zHideNavigationBar);
                    return true;
                case 22:
                    ContextInfo contextInfo17 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zIsNavigationBarHidden = isNavigationBarHidden(contextInfo17);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsNavigationBarHidden);
                    return true;
                case 23:
                    ContextInfo contextInfo18 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z9 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zAllowMultiWindowMode = allowMultiWindowMode(contextInfo18, z9);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAllowMultiWindowMode);
                    return true;
                case 24:
                    ContextInfo contextInfo19 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z10 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zIsMultiWindowModeAllowed = isMultiWindowModeAllowed(contextInfo19, z10);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsMultiWindowModeAllowed);
                    return true;
                case 25:
                    int i8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsMultiWindowModeAllowedAsUser = isMultiWindowModeAllowedAsUser(i8);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsMultiWindowModeAllowedAsUser);
                    return true;
                case 26:
                    ContextInfo contextInfo20 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zClearAllNotifications = clearAllNotifications(contextInfo20);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zClearAllNotifications);
                    return true;
                case 27:
                    ContextInfo contextInfo21 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zIsAirCommandModeAllowed = isAirCommandModeAllowed(contextInfo21);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsAirCommandModeAllowed);
                    return true;
                case 28:
                    ContextInfo contextInfo22 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z11 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zAllowAirCommandMode = allowAirCommandMode(contextInfo22, z11);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAllowAirCommandMode);
                    return true;
                case 29:
                    ContextInfo contextInfo23 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zIsAirViewModeAllowed = isAirViewModeAllowed(contextInfo23);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsAirViewModeAllowed);
                    return true;
                case 30:
                    ContextInfo contextInfo24 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z12 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zAllowAirViewMode = allowAirViewMode(contextInfo24, z12);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAllowAirViewMode);
                    return true;
                case 31:
                    ContextInfo contextInfo25 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    int i9 = parcel.readInt();
                    boolean z13 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zAllowEdgeScreen = allowEdgeScreen(contextInfo25, i9, z13);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAllowEdgeScreen);
                    return true;
                case 32:
                    ContextInfo contextInfo26 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    int blockedEdgeScreen = getBlockedEdgeScreen(contextInfo26);
                    parcel2.writeNoException();
                    parcel2.writeInt(blockedEdgeScreen);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }
    }

    public class Default implements IKioskMode {
        @Override // com.samsung.android.knox.kiosk.IKioskMode
        public boolean allowAirCommandMode(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.kiosk.IKioskMode
        public boolean allowAirViewMode(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.kiosk.IKioskMode
        public boolean allowEdgeScreen(ContextInfo contextInfo, int i, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.kiosk.IKioskMode
        public int[] allowHardwareKeys(ContextInfo contextInfo, int[] iArr, boolean z) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.kiosk.IKioskMode
        public boolean allowMultiWindowMode(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.kiosk.IKioskMode
        public boolean allowTaskManager(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.knox.kiosk.IKioskMode
        public boolean clearAllNotifications(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.kiosk.IKioskMode
        public List getAllBlockedHardwareKeys(ContextInfo contextInfo) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.kiosk.IKioskMode
        public int getBlockedEdgeScreen(ContextInfo contextInfo) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.kiosk.IKioskMode
        public Map getBlockedHwKeysCache() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.kiosk.IKioskMode
        public List getHardwareKeyList(ContextInfo contextInfo) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.kiosk.IKioskMode
        public String getKioskHomePackage(ContextInfo contextInfo) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.kiosk.IKioskMode
        public String getKioskHomePackageAsUser(int i) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.kiosk.IKioskMode
        public boolean hideNavigationBar(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.kiosk.IKioskMode
        public boolean hideStatusBar(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.kiosk.IKioskMode
        public boolean hideSystemBar(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.kiosk.IKioskMode
        public boolean isAirCommandModeAllowed(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.kiosk.IKioskMode
        public boolean isAirViewModeAllowed(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.kiosk.IKioskMode
        public boolean isHardwareKeyAllowed(ContextInfo contextInfo, int i, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.kiosk.IKioskMode
        public boolean isKioskModeEnabled(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.kiosk.IKioskMode
        public boolean isKioskModeEnabledAsUser(int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.kiosk.IKioskMode
        public boolean isMultiWindowModeAllowed(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.kiosk.IKioskMode
        public boolean isMultiWindowModeAllowedAsUser(int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.kiosk.IKioskMode
        public boolean isNavigationBarHidden(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.kiosk.IKioskMode
        public boolean isStatusBarHidden(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.kiosk.IKioskMode
        public boolean isStatusBarHiddenAsUser(int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.kiosk.IKioskMode
        public boolean isSystemBarHidden(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.kiosk.IKioskMode
        public boolean isTaskManagerAllowed(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.kiosk.IKioskMode
        public boolean isTaskManagerAllowedAsUser(boolean z, int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.kiosk.IKioskMode
        public boolean wipeRecentTasks(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.kiosk.IKioskMode
        public void disableKioskMode(ContextInfo contextInfo) throws RemoteException {
        }

        @Override // com.samsung.android.knox.kiosk.IKioskMode
        public void enableKioskMode(ContextInfo contextInfo, String str) throws RemoteException {
        }
    }
}
