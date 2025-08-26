package com.samsung.android.knox.container;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.samsung.android.knox.ContextInfo;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public interface IRCPPolicy extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.container.IRCPPolicy";

    boolean allowMoveAppsToContainer(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean allowMoveFilesToContainer(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean allowMoveFilesToOwner(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean allowShareClipboardDataToContainer(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean allowShareClipboardDataToOwner(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean getAllowChangeDataSyncPolicy(ContextInfo contextInfo, String str, String str2) throws RemoteException;

    List<String> getListFromAllowChangeDataSyncPolicy(ContextInfo contextInfo, String str, boolean z) throws RemoteException;

    String getNotificationSyncPolicy(ContextInfo contextInfo, String str, String str2) throws RemoteException;

    List<String> getPackagesFromNotificationSyncPolicy(ContextInfo contextInfo, String str, String str2) throws RemoteException;

    boolean isMoveAppsToContainerAllowed(ContextInfo contextInfo) throws RemoteException;

    boolean isMoveFilesToContainerAllowed(ContextInfo contextInfo) throws RemoteException;

    boolean isMoveFilesToOwnerAllowed(ContextInfo contextInfo) throws RemoteException;

    boolean isShareClipboardDataToContainerAllowed(ContextInfo contextInfo) throws RemoteException;

    boolean isShareClipboardDataToOwnerAllowed(ContextInfo contextInfo) throws RemoteException;

    void sendRCPPolicyChangedBroadcastToGearManager(String str, int i) throws RemoteException;

    boolean setAllowChangeDataSyncPolicy(ContextInfo contextInfo, List<String> list, String str, boolean z) throws RemoteException;

    boolean setNotificationSyncPolicy(ContextInfo contextInfo, List<String> list, String str, String str2) throws RemoteException;

    public abstract class Stub extends Binder implements IRCPPolicy {
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
        public static final int TRANSACTION_sendRCPPolicyChangedBroadcastToGearManager = 17;
        public static final int TRANSACTION_setAllowChangeDataSyncPolicy = 1;
        public static final int TRANSACTION_setNotificationSyncPolicy = 4;

        class Proxy implements IRCPPolicy {
            public IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // com.samsung.android.knox.container.IRCPPolicy
            public boolean allowMoveAppsToContainer(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRCPPolicy.DESCRIPTOR);
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

            @Override // com.samsung.android.knox.container.IRCPPolicy
            public boolean allowMoveFilesToContainer(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRCPPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.container.IRCPPolicy
            public boolean allowMoveFilesToOwner(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRCPPolicy.DESCRIPTOR);
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

            @Override // com.samsung.android.knox.container.IRCPPolicy
            public boolean allowShareClipboardDataToContainer(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRCPPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(15, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.container.IRCPPolicy
            public boolean allowShareClipboardDataToOwner(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRCPPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
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

            @Override // com.samsung.android.knox.container.IRCPPolicy
            public boolean getAllowChangeDataSyncPolicy(ContextInfo contextInfo, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRCPPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            public String getInterfaceDescriptor() {
                return IRCPPolicy.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.container.IRCPPolicy
            public List<String> getListFromAllowChangeDataSyncPolicy(ContextInfo contextInfo, String str, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRCPPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.container.IRCPPolicy
            public String getNotificationSyncPolicy(ContextInfo contextInfo, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRCPPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.container.IRCPPolicy
            public List<String> getPackagesFromNotificationSyncPolicy(ContextInfo contextInfo, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRCPPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.container.IRCPPolicy
            public boolean isMoveAppsToContainerAllowed(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRCPPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.container.IRCPPolicy
            public boolean isMoveFilesToContainerAllowed(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRCPPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.container.IRCPPolicy
            public boolean isMoveFilesToOwnerAllowed(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRCPPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.container.IRCPPolicy
            public boolean isShareClipboardDataToContainerAllowed(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRCPPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(16, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.container.IRCPPolicy
            public boolean isShareClipboardDataToOwnerAllowed(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRCPPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.container.IRCPPolicy
            public void sendRCPPolicyChangedBroadcastToGearManager(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRCPPolicy.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(17, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.container.IRCPPolicy
            public boolean setAllowChangeDataSyncPolicy(ContextInfo contextInfo, List<String> list, String str, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRCPPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeStringList(list);
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.container.IRCPPolicy
            public boolean setNotificationSyncPolicy(ContextInfo contextInfo, List<String> list, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRCPPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeStringList(list);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IRCPPolicy.DESCRIPTOR);
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof IRCPPolicy)) ? new Proxy(iBinder) : (IRCPPolicy) iInterfaceQueryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
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
                    ArrayList<String> arrayListCreateStringArrayList = parcel.createStringArrayList();
                    String string = parcel.readString();
                    boolean z = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean allowChangeDataSyncPolicy = setAllowChangeDataSyncPolicy(contextInfo, arrayListCreateStringArrayList, string, z);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(allowChangeDataSyncPolicy);
                    return true;
                case 2:
                    ContextInfo contextInfo2 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string2 = parcel.readString();
                    String string3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean allowChangeDataSyncPolicy2 = getAllowChangeDataSyncPolicy(contextInfo2, string2, string3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(allowChangeDataSyncPolicy2);
                    return true;
                case 3:
                    ContextInfo contextInfo3 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string4 = parcel.readString();
                    boolean z2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    List<String> listFromAllowChangeDataSyncPolicy = getListFromAllowChangeDataSyncPolicy(contextInfo3, string4, z2);
                    parcel2.writeNoException();
                    parcel2.writeStringList(listFromAllowChangeDataSyncPolicy);
                    return true;
                case 4:
                    ContextInfo contextInfo4 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    ArrayList<String> arrayListCreateStringArrayList2 = parcel.createStringArrayList();
                    String string5 = parcel.readString();
                    String string6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean notificationSyncPolicy = setNotificationSyncPolicy(contextInfo4, arrayListCreateStringArrayList2, string5, string6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(notificationSyncPolicy);
                    return true;
                case 5:
                    ContextInfo contextInfo5 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string7 = parcel.readString();
                    String string8 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String notificationSyncPolicy2 = getNotificationSyncPolicy(contextInfo5, string7, string8);
                    parcel2.writeNoException();
                    parcel2.writeString(notificationSyncPolicy2);
                    return true;
                case 6:
                    ContextInfo contextInfo6 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string9 = parcel.readString();
                    String string10 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    List<String> packagesFromNotificationSyncPolicy = getPackagesFromNotificationSyncPolicy(contextInfo6, string9, string10);
                    parcel2.writeNoException();
                    parcel2.writeStringList(packagesFromNotificationSyncPolicy);
                    return true;
                case 7:
                    ContextInfo contextInfo7 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zAllowMoveFilesToOwner = allowMoveFilesToOwner(contextInfo7, z3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAllowMoveFilesToOwner);
                    return true;
                case 8:
                    ContextInfo contextInfo8 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zIsMoveFilesToOwnerAllowed = isMoveFilesToOwnerAllowed(contextInfo8);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsMoveFilesToOwnerAllowed);
                    return true;
                case 9:
                    ContextInfo contextInfo9 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zAllowMoveFilesToContainer = allowMoveFilesToContainer(contextInfo9, z4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAllowMoveFilesToContainer);
                    return true;
                case 10:
                    ContextInfo contextInfo10 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zIsMoveFilesToContainerAllowed = isMoveFilesToContainerAllowed(contextInfo10);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsMoveFilesToContainerAllowed);
                    return true;
                case 11:
                    ContextInfo contextInfo11 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z5 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zAllowMoveAppsToContainer = allowMoveAppsToContainer(contextInfo11, z5);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAllowMoveAppsToContainer);
                    return true;
                case 12:
                    ContextInfo contextInfo12 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zIsMoveAppsToContainerAllowed = isMoveAppsToContainerAllowed(contextInfo12);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsMoveAppsToContainerAllowed);
                    return true;
                case 13:
                    ContextInfo contextInfo13 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z6 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zAllowShareClipboardDataToOwner = allowShareClipboardDataToOwner(contextInfo13, z6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAllowShareClipboardDataToOwner);
                    return true;
                case 14:
                    ContextInfo contextInfo14 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zIsShareClipboardDataToOwnerAllowed = isShareClipboardDataToOwnerAllowed(contextInfo14);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsShareClipboardDataToOwnerAllowed);
                    return true;
                case 15:
                    ContextInfo contextInfo15 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z7 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zAllowShareClipboardDataToContainer = allowShareClipboardDataToContainer(contextInfo15, z7);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAllowShareClipboardDataToContainer);
                    return true;
                case 16:
                    ContextInfo contextInfo16 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zIsShareClipboardDataToContainerAllowed = isShareClipboardDataToContainerAllowed(contextInfo16);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsShareClipboardDataToContainerAllowed);
                    return true;
                case 17:
                    String string11 = parcel.readString();
                    int i3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    sendRCPPolicyChangedBroadcastToGearManager(string11, i3);
                    parcel2.writeNoException();
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

    public class Default implements IRCPPolicy {
        @Override // com.samsung.android.knox.container.IRCPPolicy
        public boolean allowMoveAppsToContainer(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.container.IRCPPolicy
        public boolean allowMoveFilesToContainer(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.container.IRCPPolicy
        public boolean allowMoveFilesToOwner(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.container.IRCPPolicy
        public boolean allowShareClipboardDataToContainer(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.container.IRCPPolicy
        public boolean allowShareClipboardDataToOwner(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.knox.container.IRCPPolicy
        public boolean getAllowChangeDataSyncPolicy(ContextInfo contextInfo, String str, String str2) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.container.IRCPPolicy
        public List<String> getListFromAllowChangeDataSyncPolicy(ContextInfo contextInfo, String str, boolean z) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.container.IRCPPolicy
        public String getNotificationSyncPolicy(ContextInfo contextInfo, String str, String str2) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.container.IRCPPolicy
        public List<String> getPackagesFromNotificationSyncPolicy(ContextInfo contextInfo, String str, String str2) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.container.IRCPPolicy
        public boolean isMoveAppsToContainerAllowed(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.container.IRCPPolicy
        public boolean isMoveFilesToContainerAllowed(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.container.IRCPPolicy
        public boolean isMoveFilesToOwnerAllowed(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.container.IRCPPolicy
        public boolean isShareClipboardDataToContainerAllowed(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.container.IRCPPolicy
        public boolean isShareClipboardDataToOwnerAllowed(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.container.IRCPPolicy
        public boolean setAllowChangeDataSyncPolicy(ContextInfo contextInfo, List<String> list, String str, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.container.IRCPPolicy
        public boolean setNotificationSyncPolicy(ContextInfo contextInfo, List<String> list, String str, String str2) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.container.IRCPPolicy
        public void sendRCPPolicyChangedBroadcastToGearManager(String str, int i) throws RemoteException {
        }
    }
}
