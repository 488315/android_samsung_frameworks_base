package android.content.pm;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.text.TextUtils;
import com.android.internal.os.IParcelFileDescriptorFactory;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public interface IShortcutService extends IInterface {

    public static class Default implements IShortcutService {
        @Override // android.content.pm.IShortcutService
        public boolean addDynamicShortcuts(String str, ParceledListSlice parceledListSlice, int i) throws RemoteException {
            return false;
        }

        @Override // android.content.pm.IShortcutService
        public void applyRestore(byte[] bArr, int i) throws RemoteException {
        }

        @Override // android.content.pm.IShortcutService
        public int applyRestoreSmartSwitch(ParcelFileDescriptor parcelFileDescriptor, int i) throws RemoteException {
            return 0;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.content.pm.IShortcutService
        public Intent createShortcutResultIntent(String str, ShortcutInfo shortcutInfo, int i) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IShortcutService
        public void disableShortcuts(String str, List<String> list, CharSequence charSequence, int i, int i2) throws RemoteException {
        }

        @Override // android.content.pm.IShortcutService
        public void enableShortcuts(String str, List<String> list, int i) throws RemoteException {
        }

        @Override // android.content.pm.IShortcutService
        public byte[] getBackupPayload(int i) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IShortcutService
        public ParcelFileDescriptor getBackupShortcut(int i) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IShortcutService
        public String[] getBitmapPathList(int i) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IShortcutService
        public int getIconMaxDimensions(String str, int i) throws RemoteException {
            return 0;
        }

        @Override // android.content.pm.IShortcutService
        public int getMaxShortcutCountPerActivity(String str, int i) throws RemoteException {
            return 0;
        }

        @Override // android.content.pm.IShortcutService
        public long getRateLimitResetTime(String str, int i) throws RemoteException {
            return 0L;
        }

        @Override // android.content.pm.IShortcutService
        public int getRemainingCallCount(String str, int i) throws RemoteException {
            return 0;
        }

        @Override // android.content.pm.IShortcutService
        public ParceledListSlice getShareTargets(String str, IntentFilter intentFilter, int i) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IShortcutService
        public IParcelFileDescriptorFactory getShortcutBitmapsFileDescriptor() throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IShortcutService
        public ParceledListSlice getShortcuts(String str, int i, int i2) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IShortcutService
        public boolean hasShareTargets(String str, String str2, int i) throws RemoteException {
            return false;
        }

        @Override // android.content.pm.IShortcutService
        public boolean isRequestPinItemSupported(int i, int i2) throws RemoteException {
            return false;
        }

        @Override // android.content.pm.IShortcutService
        public void onApplicationActive(String str, int i) throws RemoteException {
        }

        @Override // android.content.pm.IShortcutService
        public void pushDynamicShortcut(String str, ShortcutInfo shortcutInfo, int i) throws RemoteException {
        }

        @Override // android.content.pm.IShortcutService
        public void removeAllDynamicShortcuts(String str, int i) throws RemoteException {
        }

        @Override // android.content.pm.IShortcutService
        public void removeDynamicShortcuts(String str, List<String> list, int i) throws RemoteException {
        }

        @Override // android.content.pm.IShortcutService
        public void removeLongLivedShortcuts(String str, List<String> list, int i) throws RemoteException {
        }

        @Override // android.content.pm.IShortcutService
        public void reportShortcutUsed(String str, String str2, int i) throws RemoteException {
        }

        @Override // android.content.pm.IShortcutService
        public boolean requestPinShortcut(String str, ShortcutInfo shortcutInfo, IntentSender intentSender, int i) throws RemoteException {
            return false;
        }

        @Override // android.content.pm.IShortcutService
        public boolean requestPinShortcutAsDisplay(String str, ShortcutInfo shortcutInfo, IntentSender intentSender, int i, int i2) throws RemoteException {
            return false;
        }

        @Override // android.content.pm.IShortcutService
        public void resetThrottling() throws RemoteException {
        }

        @Override // android.content.pm.IShortcutService
        public void restoreBitmapsFromBackupService(ParcelFileDescriptor parcelFileDescriptor, String str, String str2) throws RemoteException {
        }

        @Override // android.content.pm.IShortcutService
        public boolean setDynamicShortcuts(String str, ParceledListSlice parceledListSlice, int i) throws RemoteException {
            return false;
        }

        @Override // android.content.pm.IShortcutService
        public boolean updateShortcuts(String str, ParceledListSlice parceledListSlice, int i) throws RemoteException {
            return false;
        }
    }

    boolean addDynamicShortcuts(String str, ParceledListSlice parceledListSlice, int i) throws RemoteException;

    void applyRestore(byte[] bArr, int i) throws RemoteException;

    int applyRestoreSmartSwitch(ParcelFileDescriptor parcelFileDescriptor, int i) throws RemoteException;

    Intent createShortcutResultIntent(String str, ShortcutInfo shortcutInfo, int i) throws RemoteException;

    void disableShortcuts(String str, List<String> list, CharSequence charSequence, int i, int i2) throws RemoteException;

    void enableShortcuts(String str, List<String> list, int i) throws RemoteException;

    byte[] getBackupPayload(int i) throws RemoteException;

    ParcelFileDescriptor getBackupShortcut(int i) throws RemoteException;

    String[] getBitmapPathList(int i) throws RemoteException;

    int getIconMaxDimensions(String str, int i) throws RemoteException;

    int getMaxShortcutCountPerActivity(String str, int i) throws RemoteException;

    long getRateLimitResetTime(String str, int i) throws RemoteException;

    int getRemainingCallCount(String str, int i) throws RemoteException;

    ParceledListSlice getShareTargets(String str, IntentFilter intentFilter, int i) throws RemoteException;

    IParcelFileDescriptorFactory getShortcutBitmapsFileDescriptor() throws RemoteException;

    ParceledListSlice getShortcuts(String str, int i, int i2) throws RemoteException;

    boolean hasShareTargets(String str, String str2, int i) throws RemoteException;

    boolean isRequestPinItemSupported(int i, int i2) throws RemoteException;

    void onApplicationActive(String str, int i) throws RemoteException;

    void pushDynamicShortcut(String str, ShortcutInfo shortcutInfo, int i) throws RemoteException;

    void removeAllDynamicShortcuts(String str, int i) throws RemoteException;

    void removeDynamicShortcuts(String str, List<String> list, int i) throws RemoteException;

    void removeLongLivedShortcuts(String str, List<String> list, int i) throws RemoteException;

    void reportShortcutUsed(String str, String str2, int i) throws RemoteException;

    boolean requestPinShortcut(String str, ShortcutInfo shortcutInfo, IntentSender intentSender, int i) throws RemoteException;

    boolean requestPinShortcutAsDisplay(String str, ShortcutInfo shortcutInfo, IntentSender intentSender, int i, int i2) throws RemoteException;

    void resetThrottling() throws RemoteException;

    void restoreBitmapsFromBackupService(ParcelFileDescriptor parcelFileDescriptor, String str, String str2) throws RemoteException;

    boolean setDynamicShortcuts(String str, ParceledListSlice parceledListSlice, int i) throws RemoteException;

    boolean updateShortcuts(String str, ParceledListSlice parceledListSlice, int i) throws RemoteException;

    public static abstract class Stub extends Binder implements IShortcutService {
        public static final String DESCRIPTOR = "android.content.pm.IShortcutService";
        static final int TRANSACTION_addDynamicShortcuts = 2;
        static final int TRANSACTION_applyRestore = 18;
        static final int TRANSACTION_applyRestoreSmartSwitch = 25;
        static final int TRANSACTION_createShortcutResultIntent = 7;
        static final int TRANSACTION_disableShortcuts = 8;
        static final int TRANSACTION_enableShortcuts = 9;
        static final int TRANSACTION_getBackupPayload = 17;
        static final int TRANSACTION_getBackupShortcut = 29;
        static final int TRANSACTION_getBitmapPathList = 28;
        static final int TRANSACTION_getIconMaxDimensions = 13;
        static final int TRANSACTION_getMaxShortcutCountPerActivity = 10;
        static final int TRANSACTION_getRateLimitResetTime = 12;
        static final int TRANSACTION_getRemainingCallCount = 11;
        static final int TRANSACTION_getShareTargets = 20;
        static final int TRANSACTION_getShortcutBitmapsFileDescriptor = 26;
        static final int TRANSACTION_getShortcuts = 23;
        static final int TRANSACTION_hasShareTargets = 21;
        static final int TRANSACTION_isRequestPinItemSupported = 19;
        static final int TRANSACTION_onApplicationActive = 16;
        static final int TRANSACTION_pushDynamicShortcut = 24;
        static final int TRANSACTION_removeAllDynamicShortcuts = 4;
        static final int TRANSACTION_removeDynamicShortcuts = 3;
        static final int TRANSACTION_removeLongLivedShortcuts = 22;
        static final int TRANSACTION_reportShortcutUsed = 14;
        static final int TRANSACTION_requestPinShortcut = 6;
        static final int TRANSACTION_requestPinShortcutAsDisplay = 30;
        static final int TRANSACTION_resetThrottling = 15;
        static final int TRANSACTION_restoreBitmapsFromBackupService = 27;
        static final int TRANSACTION_setDynamicShortcuts = 1;
        static final int TRANSACTION_updateShortcuts = 5;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 29;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IShortcutService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IShortcutService)) {
                return (IShortcutService) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "setDynamicShortcuts";
                case 2:
                    return "addDynamicShortcuts";
                case 3:
                    return "removeDynamicShortcuts";
                case 4:
                    return "removeAllDynamicShortcuts";
                case 5:
                    return "updateShortcuts";
                case 6:
                    return "requestPinShortcut";
                case 7:
                    return "createShortcutResultIntent";
                case 8:
                    return "disableShortcuts";
                case 9:
                    return "enableShortcuts";
                case 10:
                    return "getMaxShortcutCountPerActivity";
                case 11:
                    return "getRemainingCallCount";
                case 12:
                    return "getRateLimitResetTime";
                case 13:
                    return "getIconMaxDimensions";
                case 14:
                    return "reportShortcutUsed";
                case 15:
                    return "resetThrottling";
                case 16:
                    return "onApplicationActive";
                case 17:
                    return "getBackupPayload";
                case 18:
                    return "applyRestore";
                case 19:
                    return "isRequestPinItemSupported";
                case 20:
                    return "getShareTargets";
                case 21:
                    return "hasShareTargets";
                case 22:
                    return "removeLongLivedShortcuts";
                case 23:
                    return "getShortcuts";
                case 24:
                    return "pushDynamicShortcut";
                case 25:
                    return "applyRestoreSmartSwitch";
                case 26:
                    return "getShortcutBitmapsFileDescriptor";
                case 27:
                    return "restoreBitmapsFromBackupService";
                case 28:
                    return "getBitmapPathList";
                case 29:
                    return "getBackupShortcut";
                case 30:
                    return "requestPinShortcutAsDisplay";
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
                    String string = parcel.readString();
                    ParceledListSlice parceledListSlice = (ParceledListSlice) parcel.readTypedObject(ParceledListSlice.CREATOR);
                    int i3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean dynamicShortcuts = setDynamicShortcuts(string, parceledListSlice, i3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(dynamicShortcuts);
                    return true;
                case 2:
                    String string2 = parcel.readString();
                    ParceledListSlice parceledListSlice2 = (ParceledListSlice) parcel.readTypedObject(ParceledListSlice.CREATOR);
                    int i4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zAddDynamicShortcuts = addDynamicShortcuts(string2, parceledListSlice2, i4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAddDynamicShortcuts);
                    return true;
                case 3:
                    String string3 = parcel.readString();
                    ArrayList<String> arrayListCreateStringArrayList = parcel.createStringArrayList();
                    int i5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    removeDynamicShortcuts(string3, arrayListCreateStringArrayList, i5);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    String string4 = parcel.readString();
                    int i6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    removeAllDynamicShortcuts(string4, i6);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    String string5 = parcel.readString();
                    ParceledListSlice parceledListSlice3 = (ParceledListSlice) parcel.readTypedObject(ParceledListSlice.CREATOR);
                    int i7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zUpdateShortcuts = updateShortcuts(string5, parceledListSlice3, i7);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zUpdateShortcuts);
                    return true;
                case 6:
                    String string6 = parcel.readString();
                    ShortcutInfo shortcutInfo = (ShortcutInfo) parcel.readTypedObject(ShortcutInfo.CREATOR);
                    IntentSender intentSender = (IntentSender) parcel.readTypedObject(IntentSender.CREATOR);
                    int i8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zRequestPinShortcut = requestPinShortcut(string6, shortcutInfo, intentSender, i8);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zRequestPinShortcut);
                    return true;
                case 7:
                    String string7 = parcel.readString();
                    ShortcutInfo shortcutInfo2 = (ShortcutInfo) parcel.readTypedObject(ShortcutInfo.CREATOR);
                    int i9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    Intent intentCreateShortcutResultIntent = createShortcutResultIntent(string7, shortcutInfo2, i9);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(intentCreateShortcutResultIntent, 1);
                    return true;
                case 8:
                    String string8 = parcel.readString();
                    ArrayList<String> arrayListCreateStringArrayList2 = parcel.createStringArrayList();
                    CharSequence charSequence = (CharSequence) parcel.readTypedObject(TextUtils.CHAR_SEQUENCE_CREATOR);
                    int i10 = parcel.readInt();
                    int i11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    disableShortcuts(string8, arrayListCreateStringArrayList2, charSequence, i10, i11);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    String string9 = parcel.readString();
                    ArrayList<String> arrayListCreateStringArrayList3 = parcel.createStringArrayList();
                    int i12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    enableShortcuts(string9, arrayListCreateStringArrayList3, i12);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    String string10 = parcel.readString();
                    int i13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int maxShortcutCountPerActivity = getMaxShortcutCountPerActivity(string10, i13);
                    parcel2.writeNoException();
                    parcel2.writeInt(maxShortcutCountPerActivity);
                    return true;
                case 11:
                    String string11 = parcel.readString();
                    int i14 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int remainingCallCount = getRemainingCallCount(string11, i14);
                    parcel2.writeNoException();
                    parcel2.writeInt(remainingCallCount);
                    return true;
                case 12:
                    String string12 = parcel.readString();
                    int i15 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    long rateLimitResetTime = getRateLimitResetTime(string12, i15);
                    parcel2.writeNoException();
                    parcel2.writeLong(rateLimitResetTime);
                    return true;
                case 13:
                    String string13 = parcel.readString();
                    int i16 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int iconMaxDimensions = getIconMaxDimensions(string13, i16);
                    parcel2.writeNoException();
                    parcel2.writeInt(iconMaxDimensions);
                    return true;
                case 14:
                    String string14 = parcel.readString();
                    String string15 = parcel.readString();
                    int i17 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    reportShortcutUsed(string14, string15, i17);
                    parcel2.writeNoException();
                    return true;
                case 15:
                    resetThrottling();
                    parcel2.writeNoException();
                    return true;
                case 16:
                    String string16 = parcel.readString();
                    int i18 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onApplicationActive(string16, i18);
                    return true;
                case 17:
                    int i19 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    byte[] backupPayload = getBackupPayload(i19);
                    parcel2.writeNoException();
                    parcel2.writeByteArray(backupPayload);
                    return true;
                case 18:
                    byte[] bArrCreateByteArray = parcel.createByteArray();
                    int i20 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    applyRestore(bArrCreateByteArray, i20);
                    parcel2.writeNoException();
                    return true;
                case 19:
                    int i21 = parcel.readInt();
                    int i22 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsRequestPinItemSupported = isRequestPinItemSupported(i21, i22);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsRequestPinItemSupported);
                    return true;
                case 20:
                    String string17 = parcel.readString();
                    IntentFilter intentFilter = (IntentFilter) parcel.readTypedObject(IntentFilter.CREATOR);
                    int i23 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ParceledListSlice shareTargets = getShareTargets(string17, intentFilter, i23);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(shareTargets, 1);
                    return true;
                case 21:
                    String string18 = parcel.readString();
                    String string19 = parcel.readString();
                    int i24 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zHasShareTargets = hasShareTargets(string18, string19, i24);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zHasShareTargets);
                    return true;
                case 22:
                    String string20 = parcel.readString();
                    ArrayList<String> arrayListCreateStringArrayList4 = parcel.createStringArrayList();
                    int i25 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    removeLongLivedShortcuts(string20, arrayListCreateStringArrayList4, i25);
                    parcel2.writeNoException();
                    return true;
                case 23:
                    String string21 = parcel.readString();
                    int i26 = parcel.readInt();
                    int i27 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ParceledListSlice shortcuts = getShortcuts(string21, i26, i27);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(shortcuts, 1);
                    return true;
                case 24:
                    String string22 = parcel.readString();
                    ShortcutInfo shortcutInfo3 = (ShortcutInfo) parcel.readTypedObject(ShortcutInfo.CREATOR);
                    int i28 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    pushDynamicShortcut(string22, shortcutInfo3, i28);
                    parcel2.writeNoException();
                    return true;
                case 25:
                    ParcelFileDescriptor parcelFileDescriptor = (ParcelFileDescriptor) parcel.readTypedObject(ParcelFileDescriptor.CREATOR);
                    int i29 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int iApplyRestoreSmartSwitch = applyRestoreSmartSwitch(parcelFileDescriptor, i29);
                    parcel2.writeNoException();
                    parcel2.writeInt(iApplyRestoreSmartSwitch);
                    return true;
                case 26:
                    IParcelFileDescriptorFactory shortcutBitmapsFileDescriptor = getShortcutBitmapsFileDescriptor();
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(shortcutBitmapsFileDescriptor);
                    return true;
                case 27:
                    ParcelFileDescriptor parcelFileDescriptor2 = (ParcelFileDescriptor) parcel.readTypedObject(ParcelFileDescriptor.CREATOR);
                    String string23 = parcel.readString();
                    String string24 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    restoreBitmapsFromBackupService(parcelFileDescriptor2, string23, string24);
                    parcel2.writeNoException();
                    return true;
                case 28:
                    int i30 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String[] bitmapPathList = getBitmapPathList(i30);
                    parcel2.writeNoException();
                    parcel2.writeStringArray(bitmapPathList);
                    return true;
                case 29:
                    int i31 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ParcelFileDescriptor backupShortcut = getBackupShortcut(i31);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(backupShortcut, 1);
                    return true;
                case 30:
                    String string25 = parcel.readString();
                    ShortcutInfo shortcutInfo4 = (ShortcutInfo) parcel.readTypedObject(ShortcutInfo.CREATOR);
                    IntentSender intentSender2 = (IntentSender) parcel.readTypedObject(IntentSender.CREATOR);
                    int i32 = parcel.readInt();
                    int i33 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zRequestPinShortcutAsDisplay = requestPinShortcutAsDisplay(string25, shortcutInfo4, intentSender2, i32, i33);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zRequestPinShortcutAsDisplay);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IShortcutService {
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

            @Override // android.content.pm.IShortcutService
            public boolean setDynamicShortcuts(String str, ParceledListSlice parceledListSlice, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(parceledListSlice, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IShortcutService
            public boolean addDynamicShortcuts(String str, ParceledListSlice parceledListSlice, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(parceledListSlice, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IShortcutService
            public void removeDynamicShortcuts(String str, List<String> list, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeStringList(list);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IShortcutService
            public void removeAllDynamicShortcuts(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IShortcutService
            public boolean updateShortcuts(String str, ParceledListSlice parceledListSlice, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(parceledListSlice, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IShortcutService
            public boolean requestPinShortcut(String str, ShortcutInfo shortcutInfo, IntentSender intentSender, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(shortcutInfo, 0);
                    parcelObtain.writeTypedObject(intentSender, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IShortcutService
            public Intent createShortcutResultIntent(String str, ShortcutInfo shortcutInfo, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(shortcutInfo, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Intent) parcelObtain2.readTypedObject(Intent.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IShortcutService
            public void disableShortcuts(String str, List<String> list, CharSequence charSequence, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeStringList(list);
                    if (charSequence != null) {
                        parcelObtain.writeInt(1);
                        TextUtils.writeToParcel(charSequence, parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IShortcutService
            public void enableShortcuts(String str, List<String> list, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeStringList(list);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IShortcutService
            public int getMaxShortcutCountPerActivity(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IShortcutService
            public int getRemainingCallCount(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IShortcutService
            public long getRateLimitResetTime(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readLong();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IShortcutService
            public int getIconMaxDimensions(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IShortcutService
            public void reportShortcutUsed(String str, String str2, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IShortcutService
            public void resetThrottling() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(15, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IShortcutService
            public void onApplicationActive(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(16, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IShortcutService
            public byte[] getBackupPayload(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(17, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createByteArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IShortcutService
            public void applyRestore(byte[] bArr, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeByteArray(bArr);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(18, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IShortcutService
            public boolean isRequestPinItemSupported(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(19, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IShortcutService
            public ParceledListSlice getShareTargets(String str, IntentFilter intentFilter, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(intentFilter, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(20, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ParceledListSlice) parcelObtain2.readTypedObject(ParceledListSlice.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IShortcutService
            public boolean hasShareTargets(String str, String str2, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(21, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IShortcutService
            public void removeLongLivedShortcuts(String str, List<String> list, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeStringList(list);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(22, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IShortcutService
            public ParceledListSlice getShortcuts(String str, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(23, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ParceledListSlice) parcelObtain2.readTypedObject(ParceledListSlice.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IShortcutService
            public void pushDynamicShortcut(String str, ShortcutInfo shortcutInfo, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(shortcutInfo, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(24, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IShortcutService
            public int applyRestoreSmartSwitch(ParcelFileDescriptor parcelFileDescriptor, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(parcelFileDescriptor, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(25, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IShortcutService
            public IParcelFileDescriptorFactory getShortcutBitmapsFileDescriptor() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(26, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return IParcelFileDescriptorFactory.Stub.asInterface(parcelObtain2.readStrongBinder());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IShortcutService
            public void restoreBitmapsFromBackupService(ParcelFileDescriptor parcelFileDescriptor, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(parcelFileDescriptor, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(27, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IShortcutService
            public String[] getBitmapPathList(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(28, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IShortcutService
            public ParcelFileDescriptor getBackupShortcut(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(29, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ParcelFileDescriptor) parcelObtain2.readTypedObject(ParcelFileDescriptor.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IShortcutService
            public boolean requestPinShortcutAsDisplay(String str, ShortcutInfo shortcutInfo, IntentSender intentSender, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(shortcutInfo, 0);
                    parcelObtain.writeTypedObject(intentSender, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(30, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
