package com.samsung.android.knox.container;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import com.samsung.android.knox.ContextInfo;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public interface IRCPPolicy extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.container.IRCPPolicy";

    boolean allowMoveAppsToContainer(ContextInfo contextInfo, boolean z);

    boolean allowMoveFilesToContainer(ContextInfo contextInfo, boolean z);

    boolean allowMoveFilesToOwner(ContextInfo contextInfo, boolean z);

    boolean allowShareClipboardDataToContainer(ContextInfo contextInfo, boolean z);

    boolean allowShareClipboardDataToOwner(ContextInfo contextInfo, boolean z);

    boolean getAllowChangeDataSyncPolicy(ContextInfo contextInfo, String str, String str2);

    List<String> getListFromAllowChangeDataSyncPolicy(ContextInfo contextInfo, String str, boolean z);

    String getNotificationSyncPolicy(ContextInfo contextInfo, String str, String str2);

    List<String> getPackagesFromNotificationSyncPolicy(ContextInfo contextInfo, String str, String str2);

    boolean isMoveAppsToContainerAllowed(ContextInfo contextInfo);

    boolean isMoveFilesToContainerAllowed(ContextInfo contextInfo);

    boolean isMoveFilesToOwnerAllowed(ContextInfo contextInfo);

    boolean isShareClipboardDataToContainerAllowed(ContextInfo contextInfo);

    boolean isShareClipboardDataToOwnerAllowed(ContextInfo contextInfo);

    void sendRCPPolicyChangeBroadcast(String str, String str2, int i);

    void sendRCPPolicyChangedBroadcast(int i);

    void sendRCPPolicyChangedBroadcastToGearManager(String str, int i);

    boolean setAllowChangeDataSyncPolicy(ContextInfo contextInfo, List<String> list, String str, boolean z);

    boolean setNotificationSyncPolicy(ContextInfo contextInfo, List<String> list, String str, String str2);

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static abstract class Stub extends Binder implements IRCPPolicy {
        public static final int TRANSACTION_allowMoveAppsToContainer = 11;
        public static final int TRANSACTION_allowMoveFilesToContainer = 9;
        public static final int TRANSACTION_allowMoveFilesToOwner = 7;
        public static final int TRANSACTION_allowShareClipboardDataToContainer = 15;
        public static final int TRANSACTION_allowShareClipboardDataToOwner = 13;
        public static final int TRANSACTION_getAllowChangeDataSyncPolicy = 2;
        public static final int TRANSACTION_getListFromAllowChangeDataSyncPolicy = 3;
        public static final int TRANSACTION_getNotificationSyncPolicy = 5;
        public static final int TRANSACTION_getPackagesFromNotificationSyncPolicy = 6;
        public static final int TRANSACTION_isMoveAppsToContainerAllowed = 12;
        public static final int TRANSACTION_isMoveFilesToContainerAllowed = 10;
        public static final int TRANSACTION_isMoveFilesToOwnerAllowed = 8;
        public static final int TRANSACTION_isShareClipboardDataToContainerAllowed = 16;
        public static final int TRANSACTION_isShareClipboardDataToOwnerAllowed = 14;
        public static final int TRANSACTION_sendRCPPolicyChangeBroadcast = 19;
        public static final int TRANSACTION_sendRCPPolicyChangedBroadcast = 18;
        public static final int TRANSACTION_sendRCPPolicyChangedBroadcastToGearManager = 17;
        public static final int TRANSACTION_setAllowChangeDataSyncPolicy = 1;
        public static final int TRANSACTION_setNotificationSyncPolicy = 4;

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public static class Proxy implements IRCPPolicy {
            public IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // com.samsung.android.knox.container.IRCPPolicy
            public final boolean allowMoveAppsToContainer(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRCPPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.container.IRCPPolicy
            public final boolean allowMoveFilesToContainer(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRCPPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.container.IRCPPolicy
            public final boolean allowMoveFilesToOwner(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRCPPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.container.IRCPPolicy
            public final boolean allowShareClipboardDataToContainer(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRCPPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.container.IRCPPolicy
            public final boolean allowShareClipboardDataToOwner(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRCPPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IInterface
            public final IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.samsung.android.knox.container.IRCPPolicy
            public final boolean getAllowChangeDataSyncPolicy(ContextInfo contextInfo, String str, String str2) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRCPPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public final String getInterfaceDescriptor() {
                return IRCPPolicy.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.container.IRCPPolicy
            public final List<String> getListFromAllowChangeDataSyncPolicy(ContextInfo contextInfo, String str, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRCPPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.container.IRCPPolicy
            public final String getNotificationSyncPolicy(ContextInfo contextInfo, String str, String str2) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRCPPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.container.IRCPPolicy
            public final List<String> getPackagesFromNotificationSyncPolicy(ContextInfo contextInfo, String str, String str2) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRCPPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.container.IRCPPolicy
            public final boolean isMoveAppsToContainerAllowed(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRCPPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.container.IRCPPolicy
            public final boolean isMoveFilesToContainerAllowed(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRCPPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.container.IRCPPolicy
            public final boolean isMoveFilesToOwnerAllowed(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRCPPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.container.IRCPPolicy
            public final boolean isShareClipboardDataToContainerAllowed(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRCPPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.container.IRCPPolicy
            public final boolean isShareClipboardDataToOwnerAllowed(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRCPPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.container.IRCPPolicy
            public final void sendRCPPolicyChangeBroadcast(String str, String str2, int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRCPPolicy.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.container.IRCPPolicy
            public final void sendRCPPolicyChangedBroadcast(int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRCPPolicy.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.container.IRCPPolicy
            public final void sendRCPPolicyChangedBroadcastToGearManager(String str, int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRCPPolicy.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.container.IRCPPolicy
            public final boolean setAllowChangeDataSyncPolicy(ContextInfo contextInfo, List<String> list, String str, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRCPPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeStringList(list);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.container.IRCPPolicy
            public final boolean setNotificationSyncPolicy(ContextInfo contextInfo, List<String> list, String str, String str2) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRCPPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeStringList(list);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, IRCPPolicy.DESCRIPTOR);
        }

        public static IRCPPolicy asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IRCPPolicy.DESCRIPTOR);
            return (queryLocalInterface == null || !(queryLocalInterface instanceof IRCPPolicy)) ? new Proxy(iBinder) : (IRCPPolicy) queryLocalInterface;
        }

        @Override // android.os.Binder
        public final boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IRCPPolicy.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IRCPPolicy.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    ContextInfo contextInfo = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    ArrayList<String> createStringArrayList = parcel.createStringArrayList();
                    String readString = parcel.readString();
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean allowChangeDataSyncPolicy = setAllowChangeDataSyncPolicy(contextInfo, createStringArrayList, readString, readBoolean);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(allowChangeDataSyncPolicy);
                    return true;
                case 2:
                    ContextInfo contextInfo2 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString2 = parcel.readString();
                    String readString3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean allowChangeDataSyncPolicy2 = getAllowChangeDataSyncPolicy(contextInfo2, readString2, readString3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(allowChangeDataSyncPolicy2);
                    return true;
                case 3:
                    ContextInfo contextInfo3 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString4 = parcel.readString();
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    List<String> listFromAllowChangeDataSyncPolicy = getListFromAllowChangeDataSyncPolicy(contextInfo3, readString4, readBoolean2);
                    parcel2.writeNoException();
                    parcel2.writeStringList(listFromAllowChangeDataSyncPolicy);
                    return true;
                case 4:
                    ContextInfo contextInfo4 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    ArrayList<String> createStringArrayList2 = parcel.createStringArrayList();
                    String readString5 = parcel.readString();
                    String readString6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean notificationSyncPolicy = setNotificationSyncPolicy(contextInfo4, createStringArrayList2, readString5, readString6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(notificationSyncPolicy);
                    return true;
                case 5:
                    ContextInfo contextInfo5 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString7 = parcel.readString();
                    String readString8 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String notificationSyncPolicy2 = getNotificationSyncPolicy(contextInfo5, readString7, readString8);
                    parcel2.writeNoException();
                    parcel2.writeString(notificationSyncPolicy2);
                    return true;
                case 6:
                    ContextInfo contextInfo6 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString9 = parcel.readString();
                    String readString10 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    List<String> packagesFromNotificationSyncPolicy = getPackagesFromNotificationSyncPolicy(contextInfo6, readString9, readString10);
                    parcel2.writeNoException();
                    parcel2.writeStringList(packagesFromNotificationSyncPolicy);
                    return true;
                case 7:
                    ContextInfo contextInfo7 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean allowMoveFilesToOwner = allowMoveFilesToOwner(contextInfo7, readBoolean3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(allowMoveFilesToOwner);
                    return true;
                case 8:
                    ContextInfo contextInfo8 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isMoveFilesToOwnerAllowed = isMoveFilesToOwnerAllowed(contextInfo8);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isMoveFilesToOwnerAllowed);
                    return true;
                case 9:
                    ContextInfo contextInfo9 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean allowMoveFilesToContainer = allowMoveFilesToContainer(contextInfo9, readBoolean4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(allowMoveFilesToContainer);
                    return true;
                case 10:
                    ContextInfo contextInfo10 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isMoveFilesToContainerAllowed = isMoveFilesToContainerAllowed(contextInfo10);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isMoveFilesToContainerAllowed);
                    return true;
                case 11:
                    ContextInfo contextInfo11 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean5 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean allowMoveAppsToContainer = allowMoveAppsToContainer(contextInfo11, readBoolean5);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(allowMoveAppsToContainer);
                    return true;
                case 12:
                    ContextInfo contextInfo12 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isMoveAppsToContainerAllowed = isMoveAppsToContainerAllowed(contextInfo12);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isMoveAppsToContainerAllowed);
                    return true;
                case 13:
                    ContextInfo contextInfo13 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean6 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean allowShareClipboardDataToOwner = allowShareClipboardDataToOwner(contextInfo13, readBoolean6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(allowShareClipboardDataToOwner);
                    return true;
                case 14:
                    ContextInfo contextInfo14 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isShareClipboardDataToOwnerAllowed = isShareClipboardDataToOwnerAllowed(contextInfo14);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isShareClipboardDataToOwnerAllowed);
                    return true;
                case 15:
                    ContextInfo contextInfo15 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean7 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean allowShareClipboardDataToContainer = allowShareClipboardDataToContainer(contextInfo15, readBoolean7);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(allowShareClipboardDataToContainer);
                    return true;
                case 16:
                    ContextInfo contextInfo16 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isShareClipboardDataToContainerAllowed = isShareClipboardDataToContainerAllowed(contextInfo16);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isShareClipboardDataToContainerAllowed);
                    return true;
                case 17:
                    String readString11 = parcel.readString();
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    sendRCPPolicyChangedBroadcastToGearManager(readString11, readInt);
                    parcel2.writeNoException();
                    return true;
                case 18:
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    sendRCPPolicyChangedBroadcast(readInt2);
                    parcel2.writeNoException();
                    return true;
                case 19:
                    String readString12 = parcel.readString();
                    String readString13 = parcel.readString();
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    sendRCPPolicyChangeBroadcast(readString12, readString13, readInt3);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        @Override // android.os.IInterface
        public final IBinder asBinder() {
            return this;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class Default implements IRCPPolicy {
        @Override // com.samsung.android.knox.container.IRCPPolicy
        public final boolean allowMoveAppsToContainer(ContextInfo contextInfo, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.container.IRCPPolicy
        public final boolean allowMoveFilesToContainer(ContextInfo contextInfo, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.container.IRCPPolicy
        public final boolean allowMoveFilesToOwner(ContextInfo contextInfo, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.container.IRCPPolicy
        public final boolean allowShareClipboardDataToContainer(ContextInfo contextInfo, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.container.IRCPPolicy
        public final boolean allowShareClipboardDataToOwner(ContextInfo contextInfo, boolean z) {
            return false;
        }

        @Override // android.os.IInterface
        public final IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.knox.container.IRCPPolicy
        public final boolean getAllowChangeDataSyncPolicy(ContextInfo contextInfo, String str, String str2) {
            return false;
        }

        @Override // com.samsung.android.knox.container.IRCPPolicy
        public final List<String> getListFromAllowChangeDataSyncPolicy(ContextInfo contextInfo, String str, boolean z) {
            return null;
        }

        @Override // com.samsung.android.knox.container.IRCPPolicy
        public final String getNotificationSyncPolicy(ContextInfo contextInfo, String str, String str2) {
            return null;
        }

        @Override // com.samsung.android.knox.container.IRCPPolicy
        public final List<String> getPackagesFromNotificationSyncPolicy(ContextInfo contextInfo, String str, String str2) {
            return null;
        }

        @Override // com.samsung.android.knox.container.IRCPPolicy
        public final boolean isMoveAppsToContainerAllowed(ContextInfo contextInfo) {
            return false;
        }

        @Override // com.samsung.android.knox.container.IRCPPolicy
        public final boolean isMoveFilesToContainerAllowed(ContextInfo contextInfo) {
            return false;
        }

        @Override // com.samsung.android.knox.container.IRCPPolicy
        public final boolean isMoveFilesToOwnerAllowed(ContextInfo contextInfo) {
            return false;
        }

        @Override // com.samsung.android.knox.container.IRCPPolicy
        public final boolean isShareClipboardDataToContainerAllowed(ContextInfo contextInfo) {
            return false;
        }

        @Override // com.samsung.android.knox.container.IRCPPolicy
        public final boolean isShareClipboardDataToOwnerAllowed(ContextInfo contextInfo) {
            return false;
        }

        @Override // com.samsung.android.knox.container.IRCPPolicy
        public final boolean setAllowChangeDataSyncPolicy(ContextInfo contextInfo, List<String> list, String str, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.container.IRCPPolicy
        public final boolean setNotificationSyncPolicy(ContextInfo contextInfo, List<String> list, String str, String str2) {
            return false;
        }

        @Override // com.samsung.android.knox.container.IRCPPolicy
        public final void sendRCPPolicyChangedBroadcast(int i) {
        }

        @Override // com.samsung.android.knox.container.IRCPPolicy
        public final void sendRCPPolicyChangedBroadcastToGearManager(String str, int i) {
        }

        @Override // com.samsung.android.knox.container.IRCPPolicy
        public final void sendRCPPolicyChangeBroadcast(String str, String str2, int i) {
        }
    }
}
