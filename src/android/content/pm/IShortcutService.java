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
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IShortcutService)) {
                return (IShortcutService) queryLocalInterface;
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
                    String readString = parcel.readString();
                    ParceledListSlice parceledListSlice = (ParceledListSlice) parcel.readTypedObject(ParceledListSlice.CREATOR);
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean dynamicShortcuts = setDynamicShortcuts(readString, parceledListSlice, readInt);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(dynamicShortcuts);
                    return true;
                case 2:
                    String readString2 = parcel.readString();
                    ParceledListSlice parceledListSlice2 = (ParceledListSlice) parcel.readTypedObject(ParceledListSlice.CREATOR);
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean addDynamicShortcuts = addDynamicShortcuts(readString2, parceledListSlice2, readInt2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(addDynamicShortcuts);
                    return true;
                case 3:
                    String readString3 = parcel.readString();
                    ArrayList<String> createStringArrayList = parcel.createStringArrayList();
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    removeDynamicShortcuts(readString3, createStringArrayList, readInt3);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    String readString4 = parcel.readString();
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    removeAllDynamicShortcuts(readString4, readInt4);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    String readString5 = parcel.readString();
                    ParceledListSlice parceledListSlice3 = (ParceledListSlice) parcel.readTypedObject(ParceledListSlice.CREATOR);
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean updateShortcuts = updateShortcuts(readString5, parceledListSlice3, readInt5);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(updateShortcuts);
                    return true;
                case 6:
                    String readString6 = parcel.readString();
                    ShortcutInfo shortcutInfo = (ShortcutInfo) parcel.readTypedObject(ShortcutInfo.CREATOR);
                    IntentSender intentSender = (IntentSender) parcel.readTypedObject(IntentSender.CREATOR);
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean requestPinShortcut = requestPinShortcut(readString6, shortcutInfo, intentSender, readInt6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(requestPinShortcut);
                    return true;
                case 7:
                    String readString7 = parcel.readString();
                    ShortcutInfo shortcutInfo2 = (ShortcutInfo) parcel.readTypedObject(ShortcutInfo.CREATOR);
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    Intent createShortcutResultIntent = createShortcutResultIntent(readString7, shortcutInfo2, readInt7);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(createShortcutResultIntent, 1);
                    return true;
                case 8:
                    String readString8 = parcel.readString();
                    ArrayList<String> createStringArrayList2 = parcel.createStringArrayList();
                    CharSequence charSequence = (CharSequence) parcel.readTypedObject(TextUtils.CHAR_SEQUENCE_CREATOR);
                    int readInt8 = parcel.readInt();
                    int readInt9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    disableShortcuts(readString8, createStringArrayList2, charSequence, readInt8, readInt9);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    String readString9 = parcel.readString();
                    ArrayList<String> createStringArrayList3 = parcel.createStringArrayList();
                    int readInt10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    enableShortcuts(readString9, createStringArrayList3, readInt10);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    String readString10 = parcel.readString();
                    int readInt11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int maxShortcutCountPerActivity = getMaxShortcutCountPerActivity(readString10, readInt11);
                    parcel2.writeNoException();
                    parcel2.writeInt(maxShortcutCountPerActivity);
                    return true;
                case 11:
                    String readString11 = parcel.readString();
                    int readInt12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int remainingCallCount = getRemainingCallCount(readString11, readInt12);
                    parcel2.writeNoException();
                    parcel2.writeInt(remainingCallCount);
                    return true;
                case 12:
                    String readString12 = parcel.readString();
                    int readInt13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    long rateLimitResetTime = getRateLimitResetTime(readString12, readInt13);
                    parcel2.writeNoException();
                    parcel2.writeLong(rateLimitResetTime);
                    return true;
                case 13:
                    String readString13 = parcel.readString();
                    int readInt14 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int iconMaxDimensions = getIconMaxDimensions(readString13, readInt14);
                    parcel2.writeNoException();
                    parcel2.writeInt(iconMaxDimensions);
                    return true;
                case 14:
                    String readString14 = parcel.readString();
                    String readString15 = parcel.readString();
                    int readInt15 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    reportShortcutUsed(readString14, readString15, readInt15);
                    parcel2.writeNoException();
                    return true;
                case 15:
                    resetThrottling();
                    parcel2.writeNoException();
                    return true;
                case 16:
                    String readString16 = parcel.readString();
                    int readInt16 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onApplicationActive(readString16, readInt16);
                    return true;
                case 17:
                    int readInt17 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    byte[] backupPayload = getBackupPayload(readInt17);
                    parcel2.writeNoException();
                    parcel2.writeByteArray(backupPayload);
                    return true;
                case 18:
                    byte[] createByteArray = parcel.createByteArray();
                    int readInt18 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    applyRestore(createByteArray, readInt18);
                    parcel2.writeNoException();
                    return true;
                case 19:
                    int readInt19 = parcel.readInt();
                    int readInt20 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isRequestPinItemSupported = isRequestPinItemSupported(readInt19, readInt20);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isRequestPinItemSupported);
                    return true;
                case 20:
                    String readString17 = parcel.readString();
                    IntentFilter intentFilter = (IntentFilter) parcel.readTypedObject(IntentFilter.CREATOR);
                    int readInt21 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ParceledListSlice shareTargets = getShareTargets(readString17, intentFilter, readInt21);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(shareTargets, 1);
                    return true;
                case 21:
                    String readString18 = parcel.readString();
                    String readString19 = parcel.readString();
                    int readInt22 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean hasShareTargets = hasShareTargets(readString18, readString19, readInt22);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(hasShareTargets);
                    return true;
                case 22:
                    String readString20 = parcel.readString();
                    ArrayList<String> createStringArrayList4 = parcel.createStringArrayList();
                    int readInt23 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    removeLongLivedShortcuts(readString20, createStringArrayList4, readInt23);
                    parcel2.writeNoException();
                    return true;
                case 23:
                    String readString21 = parcel.readString();
                    int readInt24 = parcel.readInt();
                    int readInt25 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ParceledListSlice shortcuts = getShortcuts(readString21, readInt24, readInt25);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(shortcuts, 1);
                    return true;
                case 24:
                    String readString22 = parcel.readString();
                    ShortcutInfo shortcutInfo3 = (ShortcutInfo) parcel.readTypedObject(ShortcutInfo.CREATOR);
                    int readInt26 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    pushDynamicShortcut(readString22, shortcutInfo3, readInt26);
                    parcel2.writeNoException();
                    return true;
                case 25:
                    ParcelFileDescriptor parcelFileDescriptor = (ParcelFileDescriptor) parcel.readTypedObject(ParcelFileDescriptor.CREATOR);
                    int readInt27 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int applyRestoreSmartSwitch = applyRestoreSmartSwitch(parcelFileDescriptor, readInt27);
                    parcel2.writeNoException();
                    parcel2.writeInt(applyRestoreSmartSwitch);
                    return true;
                case 26:
                    IParcelFileDescriptorFactory shortcutBitmapsFileDescriptor = getShortcutBitmapsFileDescriptor();
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(shortcutBitmapsFileDescriptor);
                    return true;
                case 27:
                    ParcelFileDescriptor parcelFileDescriptor2 = (ParcelFileDescriptor) parcel.readTypedObject(ParcelFileDescriptor.CREATOR);
                    String readString23 = parcel.readString();
                    String readString24 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    restoreBitmapsFromBackupService(parcelFileDescriptor2, readString23, readString24);
                    parcel2.writeNoException();
                    return true;
                case 28:
                    int readInt28 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String[] bitmapPathList = getBitmapPathList(readInt28);
                    parcel2.writeNoException();
                    parcel2.writeStringArray(bitmapPathList);
                    return true;
                case 29:
                    int readInt29 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ParcelFileDescriptor backupShortcut = getBackupShortcut(readInt29);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(backupShortcut, 1);
                    return true;
                case 30:
                    String readString25 = parcel.readString();
                    ShortcutInfo shortcutInfo4 = (ShortcutInfo) parcel.readTypedObject(ShortcutInfo.CREATOR);
                    IntentSender intentSender2 = (IntentSender) parcel.readTypedObject(IntentSender.CREATOR);
                    int readInt30 = parcel.readInt();
                    int readInt31 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean requestPinShortcutAsDisplay = requestPinShortcutAsDisplay(readString25, shortcutInfo4, intentSender2, readInt30, readInt31);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(requestPinShortcutAsDisplay);
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
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(parceledListSlice, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IShortcutService
            public boolean addDynamicShortcuts(String str, ParceledListSlice parceledListSlice, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(parceledListSlice, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IShortcutService
            public void removeDynamicShortcuts(String str, List<String> list, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStringList(list);
                    obtain.writeInt(i);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IShortcutService
            public void removeAllDynamicShortcuts(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IShortcutService
            public boolean updateShortcuts(String str, ParceledListSlice parceledListSlice, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(parceledListSlice, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IShortcutService
            public boolean requestPinShortcut(String str, ShortcutInfo shortcutInfo, IntentSender intentSender, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(shortcutInfo, 0);
                    obtain.writeTypedObject(intentSender, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IShortcutService
            public Intent createShortcutResultIntent(String str, ShortcutInfo shortcutInfo, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(shortcutInfo, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Intent) obtain2.readTypedObject(Intent.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IShortcutService
            public void disableShortcuts(String str, List<String> list, CharSequence charSequence, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStringList(list);
                    if (charSequence != null) {
                        obtain.writeInt(1);
                        TextUtils.writeToParcel(charSequence, obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IShortcutService
            public void enableShortcuts(String str, List<String> list, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStringList(list);
                    obtain.writeInt(i);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IShortcutService
            public int getMaxShortcutCountPerActivity(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IShortcutService
            public int getRemainingCallCount(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IShortcutService
            public long getRateLimitResetTime(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IShortcutService
            public int getIconMaxDimensions(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IShortcutService
            public void reportShortcutUsed(String str, String str2, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IShortcutService
            public void resetThrottling() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IShortcutService
            public void onApplicationActive(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(16, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IShortcutService
            public byte[] getBackupPayload(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createByteArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IShortcutService
            public void applyRestore(byte[] bArr, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    obtain.writeInt(i);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IShortcutService
            public boolean isRequestPinItemSupported(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IShortcutService
            public ParceledListSlice getShareTargets(String str, IntentFilter intentFilter, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(intentFilter, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ParceledListSlice) obtain2.readTypedObject(ParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IShortcutService
            public boolean hasShareTargets(String str, String str2, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IShortcutService
            public void removeLongLivedShortcuts(String str, List<String> list, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStringList(list);
                    obtain.writeInt(i);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IShortcutService
            public ParceledListSlice getShortcuts(String str, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ParceledListSlice) obtain2.readTypedObject(ParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IShortcutService
            public void pushDynamicShortcut(String str, ShortcutInfo shortcutInfo, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(shortcutInfo, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IShortcutService
            public int applyRestoreSmartSwitch(ParcelFileDescriptor parcelFileDescriptor, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(parcelFileDescriptor, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IShortcutService
            public IParcelFileDescriptorFactory getShortcutBitmapsFileDescriptor() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                    return IParcelFileDescriptorFactory.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IShortcutService
            public void restoreBitmapsFromBackupService(ParcelFileDescriptor parcelFileDescriptor, String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(parcelFileDescriptor, 0);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(27, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IShortcutService
            public String[] getBitmapPathList(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(28, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IShortcutService
            public ParcelFileDescriptor getBackupShortcut(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(29, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ParcelFileDescriptor) obtain2.readTypedObject(ParcelFileDescriptor.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IShortcutService
            public boolean requestPinShortcutAsDisplay(String str, ShortcutInfo shortcutInfo, IntentSender intentSender, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(shortcutInfo, 0);
                    obtain.writeTypedObject(intentSender, 0);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(30, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
