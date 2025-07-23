package com.android.internal.telephony.euicc;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.android.internal.telephony.euicc.IAuthenticateServerCallback;
import com.android.internal.telephony.euicc.ICancelSessionCallback;
import com.android.internal.telephony.euicc.IDeleteProfileCallback;
import com.android.internal.telephony.euicc.IDisableProfileCallback;
import com.android.internal.telephony.euicc.IGetAllProfilesCallback;
import com.android.internal.telephony.euicc.IGetDefaultSmdpAddressCallback;
import com.android.internal.telephony.euicc.IGetEuiccChallengeCallback;
import com.android.internal.telephony.euicc.IGetEuiccInfo1Callback;
import com.android.internal.telephony.euicc.IGetEuiccInfo2Callback;
import com.android.internal.telephony.euicc.IGetProfileCallback;
import com.android.internal.telephony.euicc.IGetRulesAuthTableCallback;
import com.android.internal.telephony.euicc.IGetSmdsAddressCallback;
import com.android.internal.telephony.euicc.IListNotificationsCallback;
import com.android.internal.telephony.euicc.ILoadBoundProfilePackageCallback;
import com.android.internal.telephony.euicc.IPrepareDownloadCallback;
import com.android.internal.telephony.euicc.IRemoveNotificationFromListCallback;
import com.android.internal.telephony.euicc.IResetMemoryCallback;
import com.android.internal.telephony.euicc.IRetrieveNotificationCallback;
import com.android.internal.telephony.euicc.IRetrieveNotificationListCallback;
import com.android.internal.telephony.euicc.ISetDefaultSmdpAddressCallback;
import com.android.internal.telephony.euicc.ISetNicknameCallback;
import com.android.internal.telephony.euicc.ISwitchToProfileCallback;

/* loaded from: classes4.dex */
public interface IEuiccCardController extends IInterface {
    public static final String DESCRIPTOR = "com.android.internal.telephony.euicc.IEuiccCardController";

    public static class Default implements IEuiccCardController {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.android.internal.telephony.euicc.IEuiccCardController
        public void authenticateServer(String str, String str2, String str3, byte[] bArr, byte[] bArr2, byte[] bArr3, byte[] bArr4, IAuthenticateServerCallback iAuthenticateServerCallback) throws RemoteException {
        }

        @Override // com.android.internal.telephony.euicc.IEuiccCardController
        public void cancelSession(String str, String str2, byte[] bArr, int i, ICancelSessionCallback iCancelSessionCallback) throws RemoteException {
        }

        @Override // com.android.internal.telephony.euicc.IEuiccCardController
        public void deleteProfile(String str, String str2, String str3, IDeleteProfileCallback iDeleteProfileCallback) throws RemoteException {
        }

        @Override // com.android.internal.telephony.euicc.IEuiccCardController
        public void disableProfile(String str, String str2, String str3, boolean z, IDisableProfileCallback iDisableProfileCallback) throws RemoteException {
        }

        @Override // com.android.internal.telephony.euicc.IEuiccCardController
        public void getAllProfiles(String str, String str2, IGetAllProfilesCallback iGetAllProfilesCallback) throws RemoteException {
        }

        @Override // com.android.internal.telephony.euicc.IEuiccCardController
        public void getDefaultSmdpAddress(String str, String str2, IGetDefaultSmdpAddressCallback iGetDefaultSmdpAddressCallback) throws RemoteException {
        }

        @Override // com.android.internal.telephony.euicc.IEuiccCardController
        public void getEnabledProfile(String str, String str2, int i, IGetProfileCallback iGetProfileCallback) throws RemoteException {
        }

        @Override // com.android.internal.telephony.euicc.IEuiccCardController
        public void getEuiccChallenge(String str, String str2, IGetEuiccChallengeCallback iGetEuiccChallengeCallback) throws RemoteException {
        }

        @Override // com.android.internal.telephony.euicc.IEuiccCardController
        public void getEuiccInfo1(String str, String str2, IGetEuiccInfo1Callback iGetEuiccInfo1Callback) throws RemoteException {
        }

        @Override // com.android.internal.telephony.euicc.IEuiccCardController
        public void getEuiccInfo2(String str, String str2, IGetEuiccInfo2Callback iGetEuiccInfo2Callback) throws RemoteException {
        }

        @Override // com.android.internal.telephony.euicc.IEuiccCardController
        public void getProfile(String str, String str2, String str3, IGetProfileCallback iGetProfileCallback) throws RemoteException {
        }

        @Override // com.android.internal.telephony.euicc.IEuiccCardController
        public void getRulesAuthTable(String str, String str2, IGetRulesAuthTableCallback iGetRulesAuthTableCallback) throws RemoteException {
        }

        @Override // com.android.internal.telephony.euicc.IEuiccCardController
        public void getSmdsAddress(String str, String str2, IGetSmdsAddressCallback iGetSmdsAddressCallback) throws RemoteException {
        }

        @Override // com.android.internal.telephony.euicc.IEuiccCardController
        public void listNotifications(String str, String str2, int i, IListNotificationsCallback iListNotificationsCallback) throws RemoteException {
        }

        @Override // com.android.internal.telephony.euicc.IEuiccCardController
        public void loadBoundProfilePackage(String str, String str2, byte[] bArr, ILoadBoundProfilePackageCallback iLoadBoundProfilePackageCallback) throws RemoteException {
        }

        @Override // com.android.internal.telephony.euicc.IEuiccCardController
        public void prepareDownload(String str, String str2, byte[] bArr, byte[] bArr2, byte[] bArr3, byte[] bArr4, IPrepareDownloadCallback iPrepareDownloadCallback) throws RemoteException {
        }

        @Override // com.android.internal.telephony.euicc.IEuiccCardController
        public void removeNotificationFromList(String str, String str2, int i, IRemoveNotificationFromListCallback iRemoveNotificationFromListCallback) throws RemoteException {
        }

        @Override // com.android.internal.telephony.euicc.IEuiccCardController
        public void resetMemory(String str, String str2, int i, IResetMemoryCallback iResetMemoryCallback) throws RemoteException {
        }

        @Override // com.android.internal.telephony.euicc.IEuiccCardController
        public void retrieveNotification(String str, String str2, int i, IRetrieveNotificationCallback iRetrieveNotificationCallback) throws RemoteException {
        }

        @Override // com.android.internal.telephony.euicc.IEuiccCardController
        public void retrieveNotificationList(String str, String str2, int i, IRetrieveNotificationListCallback iRetrieveNotificationListCallback) throws RemoteException {
        }

        @Override // com.android.internal.telephony.euicc.IEuiccCardController
        public void setDefaultSmdpAddress(String str, String str2, String str3, ISetDefaultSmdpAddressCallback iSetDefaultSmdpAddressCallback) throws RemoteException {
        }

        @Override // com.android.internal.telephony.euicc.IEuiccCardController
        public void setNickname(String str, String str2, String str3, String str4, ISetNicknameCallback iSetNicknameCallback) throws RemoteException {
        }

        @Override // com.android.internal.telephony.euicc.IEuiccCardController
        public void switchToProfile(String str, String str2, String str3, int i, boolean z, ISwitchToProfileCallback iSwitchToProfileCallback) throws RemoteException {
        }
    }

    void authenticateServer(String str, String str2, String str3, byte[] bArr, byte[] bArr2, byte[] bArr3, byte[] bArr4, IAuthenticateServerCallback iAuthenticateServerCallback) throws RemoteException;

    void cancelSession(String str, String str2, byte[] bArr, int i, ICancelSessionCallback iCancelSessionCallback) throws RemoteException;

    void deleteProfile(String str, String str2, String str3, IDeleteProfileCallback iDeleteProfileCallback) throws RemoteException;

    void disableProfile(String str, String str2, String str3, boolean z, IDisableProfileCallback iDisableProfileCallback) throws RemoteException;

    void getAllProfiles(String str, String str2, IGetAllProfilesCallback iGetAllProfilesCallback) throws RemoteException;

    void getDefaultSmdpAddress(String str, String str2, IGetDefaultSmdpAddressCallback iGetDefaultSmdpAddressCallback) throws RemoteException;

    void getEnabledProfile(String str, String str2, int i, IGetProfileCallback iGetProfileCallback) throws RemoteException;

    void getEuiccChallenge(String str, String str2, IGetEuiccChallengeCallback iGetEuiccChallengeCallback) throws RemoteException;

    void getEuiccInfo1(String str, String str2, IGetEuiccInfo1Callback iGetEuiccInfo1Callback) throws RemoteException;

    void getEuiccInfo2(String str, String str2, IGetEuiccInfo2Callback iGetEuiccInfo2Callback) throws RemoteException;

    void getProfile(String str, String str2, String str3, IGetProfileCallback iGetProfileCallback) throws RemoteException;

    void getRulesAuthTable(String str, String str2, IGetRulesAuthTableCallback iGetRulesAuthTableCallback) throws RemoteException;

    void getSmdsAddress(String str, String str2, IGetSmdsAddressCallback iGetSmdsAddressCallback) throws RemoteException;

    void listNotifications(String str, String str2, int i, IListNotificationsCallback iListNotificationsCallback) throws RemoteException;

    void loadBoundProfilePackage(String str, String str2, byte[] bArr, ILoadBoundProfilePackageCallback iLoadBoundProfilePackageCallback) throws RemoteException;

    void prepareDownload(String str, String str2, byte[] bArr, byte[] bArr2, byte[] bArr3, byte[] bArr4, IPrepareDownloadCallback iPrepareDownloadCallback) throws RemoteException;

    void removeNotificationFromList(String str, String str2, int i, IRemoveNotificationFromListCallback iRemoveNotificationFromListCallback) throws RemoteException;

    void resetMemory(String str, String str2, int i, IResetMemoryCallback iResetMemoryCallback) throws RemoteException;

    void retrieveNotification(String str, String str2, int i, IRetrieveNotificationCallback iRetrieveNotificationCallback) throws RemoteException;

    void retrieveNotificationList(String str, String str2, int i, IRetrieveNotificationListCallback iRetrieveNotificationListCallback) throws RemoteException;

    void setDefaultSmdpAddress(String str, String str2, String str3, ISetDefaultSmdpAddressCallback iSetDefaultSmdpAddressCallback) throws RemoteException;

    void setNickname(String str, String str2, String str3, String str4, ISetNicknameCallback iSetNicknameCallback) throws RemoteException;

    void switchToProfile(String str, String str2, String str3, int i, boolean z, ISwitchToProfileCallback iSwitchToProfileCallback) throws RemoteException;

    public static abstract class Stub extends Binder implements IEuiccCardController {
        static final int TRANSACTION_authenticateServer = 16;
        static final int TRANSACTION_cancelSession = 19;
        static final int TRANSACTION_deleteProfile = 7;
        static final int TRANSACTION_disableProfile = 4;
        static final int TRANSACTION_getAllProfiles = 1;
        static final int TRANSACTION_getDefaultSmdpAddress = 9;
        static final int TRANSACTION_getEnabledProfile = 3;
        static final int TRANSACTION_getEuiccChallenge = 13;
        static final int TRANSACTION_getEuiccInfo1 = 14;
        static final int TRANSACTION_getEuiccInfo2 = 15;
        static final int TRANSACTION_getProfile = 2;
        static final int TRANSACTION_getRulesAuthTable = 12;
        static final int TRANSACTION_getSmdsAddress = 10;
        static final int TRANSACTION_listNotifications = 20;
        static final int TRANSACTION_loadBoundProfilePackage = 18;
        static final int TRANSACTION_prepareDownload = 17;
        static final int TRANSACTION_removeNotificationFromList = 23;
        static final int TRANSACTION_resetMemory = 8;
        static final int TRANSACTION_retrieveNotification = 22;
        static final int TRANSACTION_retrieveNotificationList = 21;
        static final int TRANSACTION_setDefaultSmdpAddress = 11;
        static final int TRANSACTION_setNickname = 6;
        static final int TRANSACTION_switchToProfile = 5;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 22;
        }

        public Stub() {
            attachInterface(this, IEuiccCardController.DESCRIPTOR);
        }

        public static IEuiccCardController asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IEuiccCardController.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IEuiccCardController)) {
                return (IEuiccCardController) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "getAllProfiles";
                case 2:
                    return "getProfile";
                case 3:
                    return "getEnabledProfile";
                case 4:
                    return "disableProfile";
                case 5:
                    return "switchToProfile";
                case 6:
                    return "setNickname";
                case 7:
                    return "deleteProfile";
                case 8:
                    return "resetMemory";
                case 9:
                    return "getDefaultSmdpAddress";
                case 10:
                    return "getSmdsAddress";
                case 11:
                    return "setDefaultSmdpAddress";
                case 12:
                    return "getRulesAuthTable";
                case 13:
                    return "getEuiccChallenge";
                case 14:
                    return "getEuiccInfo1";
                case 15:
                    return "getEuiccInfo2";
                case 16:
                    return "authenticateServer";
                case 17:
                    return "prepareDownload";
                case 18:
                    return "loadBoundProfilePackage";
                case 19:
                    return "cancelSession";
                case 20:
                    return "listNotifications";
                case 21:
                    return "retrieveNotificationList";
                case 22:
                    return "retrieveNotification";
                case 23:
                    return "removeNotificationFromList";
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
                parcel.enforceInterface(IEuiccCardController.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IEuiccCardController.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    String readString = parcel.readString();
                    String readString2 = parcel.readString();
                    IGetAllProfilesCallback asInterface = IGetAllProfilesCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    getAllProfiles(readString, readString2, asInterface);
                    return true;
                case 2:
                    String readString3 = parcel.readString();
                    String readString4 = parcel.readString();
                    String readString5 = parcel.readString();
                    IGetProfileCallback asInterface2 = IGetProfileCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    getProfile(readString3, readString4, readString5, asInterface2);
                    return true;
                case 3:
                    String readString6 = parcel.readString();
                    String readString7 = parcel.readString();
                    int readInt = parcel.readInt();
                    IGetProfileCallback asInterface3 = IGetProfileCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    getEnabledProfile(readString6, readString7, readInt, asInterface3);
                    return true;
                case 4:
                    String readString8 = parcel.readString();
                    String readString9 = parcel.readString();
                    String readString10 = parcel.readString();
                    boolean readBoolean = parcel.readBoolean();
                    IDisableProfileCallback asInterface4 = IDisableProfileCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    disableProfile(readString8, readString9, readString10, readBoolean, asInterface4);
                    return true;
                case 5:
                    String readString11 = parcel.readString();
                    String readString12 = parcel.readString();
                    String readString13 = parcel.readString();
                    int readInt2 = parcel.readInt();
                    boolean readBoolean2 = parcel.readBoolean();
                    ISwitchToProfileCallback asInterface5 = ISwitchToProfileCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    switchToProfile(readString11, readString12, readString13, readInt2, readBoolean2, asInterface5);
                    return true;
                case 6:
                    String readString14 = parcel.readString();
                    String readString15 = parcel.readString();
                    String readString16 = parcel.readString();
                    String readString17 = parcel.readString();
                    ISetNicknameCallback asInterface6 = ISetNicknameCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setNickname(readString14, readString15, readString16, readString17, asInterface6);
                    return true;
                case 7:
                    String readString18 = parcel.readString();
                    String readString19 = parcel.readString();
                    String readString20 = parcel.readString();
                    IDeleteProfileCallback asInterface7 = IDeleteProfileCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    deleteProfile(readString18, readString19, readString20, asInterface7);
                    return true;
                case 8:
                    String readString21 = parcel.readString();
                    String readString22 = parcel.readString();
                    int readInt3 = parcel.readInt();
                    IResetMemoryCallback asInterface8 = IResetMemoryCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    resetMemory(readString21, readString22, readInt3, asInterface8);
                    return true;
                case 9:
                    String readString23 = parcel.readString();
                    String readString24 = parcel.readString();
                    IGetDefaultSmdpAddressCallback asInterface9 = IGetDefaultSmdpAddressCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    getDefaultSmdpAddress(readString23, readString24, asInterface9);
                    return true;
                case 10:
                    String readString25 = parcel.readString();
                    String readString26 = parcel.readString();
                    IGetSmdsAddressCallback asInterface10 = IGetSmdsAddressCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    getSmdsAddress(readString25, readString26, asInterface10);
                    return true;
                case 11:
                    String readString27 = parcel.readString();
                    String readString28 = parcel.readString();
                    String readString29 = parcel.readString();
                    ISetDefaultSmdpAddressCallback asInterface11 = ISetDefaultSmdpAddressCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setDefaultSmdpAddress(readString27, readString28, readString29, asInterface11);
                    return true;
                case 12:
                    String readString30 = parcel.readString();
                    String readString31 = parcel.readString();
                    IGetRulesAuthTableCallback asInterface12 = IGetRulesAuthTableCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    getRulesAuthTable(readString30, readString31, asInterface12);
                    return true;
                case 13:
                    String readString32 = parcel.readString();
                    String readString33 = parcel.readString();
                    IGetEuiccChallengeCallback asInterface13 = IGetEuiccChallengeCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    getEuiccChallenge(readString32, readString33, asInterface13);
                    return true;
                case 14:
                    String readString34 = parcel.readString();
                    String readString35 = parcel.readString();
                    IGetEuiccInfo1Callback asInterface14 = IGetEuiccInfo1Callback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    getEuiccInfo1(readString34, readString35, asInterface14);
                    return true;
                case 15:
                    String readString36 = parcel.readString();
                    String readString37 = parcel.readString();
                    IGetEuiccInfo2Callback asInterface15 = IGetEuiccInfo2Callback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    getEuiccInfo2(readString36, readString37, asInterface15);
                    return true;
                case 16:
                    String readString38 = parcel.readString();
                    String readString39 = parcel.readString();
                    String readString40 = parcel.readString();
                    byte[] createByteArray = parcel.createByteArray();
                    byte[] createByteArray2 = parcel.createByteArray();
                    byte[] createByteArray3 = parcel.createByteArray();
                    byte[] createByteArray4 = parcel.createByteArray();
                    IAuthenticateServerCallback asInterface16 = IAuthenticateServerCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    authenticateServer(readString38, readString39, readString40, createByteArray, createByteArray2, createByteArray3, createByteArray4, asInterface16);
                    return true;
                case 17:
                    String readString41 = parcel.readString();
                    String readString42 = parcel.readString();
                    byte[] createByteArray5 = parcel.createByteArray();
                    byte[] createByteArray6 = parcel.createByteArray();
                    byte[] createByteArray7 = parcel.createByteArray();
                    byte[] createByteArray8 = parcel.createByteArray();
                    IPrepareDownloadCallback asInterface17 = IPrepareDownloadCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    prepareDownload(readString41, readString42, createByteArray5, createByteArray6, createByteArray7, createByteArray8, asInterface17);
                    return true;
                case 18:
                    String readString43 = parcel.readString();
                    String readString44 = parcel.readString();
                    byte[] createByteArray9 = parcel.createByteArray();
                    ILoadBoundProfilePackageCallback asInterface18 = ILoadBoundProfilePackageCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    loadBoundProfilePackage(readString43, readString44, createByteArray9, asInterface18);
                    return true;
                case 19:
                    String readString45 = parcel.readString();
                    String readString46 = parcel.readString();
                    byte[] createByteArray10 = parcel.createByteArray();
                    int readInt4 = parcel.readInt();
                    ICancelSessionCallback asInterface19 = ICancelSessionCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    cancelSession(readString45, readString46, createByteArray10, readInt4, asInterface19);
                    return true;
                case 20:
                    String readString47 = parcel.readString();
                    String readString48 = parcel.readString();
                    int readInt5 = parcel.readInt();
                    IListNotificationsCallback asInterface20 = IListNotificationsCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    listNotifications(readString47, readString48, readInt5, asInterface20);
                    return true;
                case 21:
                    String readString49 = parcel.readString();
                    String readString50 = parcel.readString();
                    int readInt6 = parcel.readInt();
                    IRetrieveNotificationListCallback asInterface21 = IRetrieveNotificationListCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    retrieveNotificationList(readString49, readString50, readInt6, asInterface21);
                    return true;
                case 22:
                    String readString51 = parcel.readString();
                    String readString52 = parcel.readString();
                    int readInt7 = parcel.readInt();
                    IRetrieveNotificationCallback asInterface22 = IRetrieveNotificationCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    retrieveNotification(readString51, readString52, readInt7, asInterface22);
                    return true;
                case 23:
                    String readString53 = parcel.readString();
                    String readString54 = parcel.readString();
                    int readInt8 = parcel.readInt();
                    IRemoveNotificationFromListCallback asInterface23 = IRemoveNotificationFromListCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    removeNotificationFromList(readString53, readString54, readInt8, asInterface23);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IEuiccCardController {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IEuiccCardController.DESCRIPTOR;
            }

            @Override // com.android.internal.telephony.euicc.IEuiccCardController
            public void getAllProfiles(String str, String str2, IGetAllProfilesCallback iGetAllProfilesCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IEuiccCardController.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeStrongInterface(iGetAllProfilesCallback);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.euicc.IEuiccCardController
            public void getProfile(String str, String str2, String str3, IGetProfileCallback iGetProfileCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IEuiccCardController.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeStrongInterface(iGetProfileCallback);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.euicc.IEuiccCardController
            public void getEnabledProfile(String str, String str2, int i, IGetProfileCallback iGetProfileCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IEuiccCardController.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iGetProfileCallback);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.euicc.IEuiccCardController
            public void disableProfile(String str, String str2, String str3, boolean z, IDisableProfileCallback iDisableProfileCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IEuiccCardController.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeBoolean(z);
                    obtain.writeStrongInterface(iDisableProfileCallback);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.euicc.IEuiccCardController
            public void switchToProfile(String str, String str2, String str3, int i, boolean z, ISwitchToProfileCallback iSwitchToProfileCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IEuiccCardController.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    obtain.writeStrongInterface(iSwitchToProfileCallback);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.euicc.IEuiccCardController
            public void setNickname(String str, String str2, String str3, String str4, ISetNicknameCallback iSetNicknameCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IEuiccCardController.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeString(str4);
                    obtain.writeStrongInterface(iSetNicknameCallback);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.euicc.IEuiccCardController
            public void deleteProfile(String str, String str2, String str3, IDeleteProfileCallback iDeleteProfileCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IEuiccCardController.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeStrongInterface(iDeleteProfileCallback);
                    this.mRemote.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.euicc.IEuiccCardController
            public void resetMemory(String str, String str2, int i, IResetMemoryCallback iResetMemoryCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IEuiccCardController.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iResetMemoryCallback);
                    this.mRemote.transact(8, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.euicc.IEuiccCardController
            public void getDefaultSmdpAddress(String str, String str2, IGetDefaultSmdpAddressCallback iGetDefaultSmdpAddressCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IEuiccCardController.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeStrongInterface(iGetDefaultSmdpAddressCallback);
                    this.mRemote.transact(9, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.euicc.IEuiccCardController
            public void getSmdsAddress(String str, String str2, IGetSmdsAddressCallback iGetSmdsAddressCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IEuiccCardController.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeStrongInterface(iGetSmdsAddressCallback);
                    this.mRemote.transact(10, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.euicc.IEuiccCardController
            public void setDefaultSmdpAddress(String str, String str2, String str3, ISetDefaultSmdpAddressCallback iSetDefaultSmdpAddressCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IEuiccCardController.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeStrongInterface(iSetDefaultSmdpAddressCallback);
                    this.mRemote.transact(11, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.euicc.IEuiccCardController
            public void getRulesAuthTable(String str, String str2, IGetRulesAuthTableCallback iGetRulesAuthTableCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IEuiccCardController.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeStrongInterface(iGetRulesAuthTableCallback);
                    this.mRemote.transact(12, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.euicc.IEuiccCardController
            public void getEuiccChallenge(String str, String str2, IGetEuiccChallengeCallback iGetEuiccChallengeCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IEuiccCardController.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeStrongInterface(iGetEuiccChallengeCallback);
                    this.mRemote.transact(13, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.euicc.IEuiccCardController
            public void getEuiccInfo1(String str, String str2, IGetEuiccInfo1Callback iGetEuiccInfo1Callback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IEuiccCardController.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeStrongInterface(iGetEuiccInfo1Callback);
                    this.mRemote.transact(14, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.euicc.IEuiccCardController
            public void getEuiccInfo2(String str, String str2, IGetEuiccInfo2Callback iGetEuiccInfo2Callback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IEuiccCardController.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeStrongInterface(iGetEuiccInfo2Callback);
                    this.mRemote.transact(15, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.euicc.IEuiccCardController
            public void authenticateServer(String str, String str2, String str3, byte[] bArr, byte[] bArr2, byte[] bArr3, byte[] bArr4, IAuthenticateServerCallback iAuthenticateServerCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IEuiccCardController.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeByteArray(bArr);
                    obtain.writeByteArray(bArr2);
                    obtain.writeByteArray(bArr3);
                    obtain.writeByteArray(bArr4);
                    obtain.writeStrongInterface(iAuthenticateServerCallback);
                    this.mRemote.transact(16, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.euicc.IEuiccCardController
            public void prepareDownload(String str, String str2, byte[] bArr, byte[] bArr2, byte[] bArr3, byte[] bArr4, IPrepareDownloadCallback iPrepareDownloadCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IEuiccCardController.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeByteArray(bArr);
                    obtain.writeByteArray(bArr2);
                    obtain.writeByteArray(bArr3);
                    obtain.writeByteArray(bArr4);
                    obtain.writeStrongInterface(iPrepareDownloadCallback);
                    this.mRemote.transact(17, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.euicc.IEuiccCardController
            public void loadBoundProfilePackage(String str, String str2, byte[] bArr, ILoadBoundProfilePackageCallback iLoadBoundProfilePackageCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IEuiccCardController.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeByteArray(bArr);
                    obtain.writeStrongInterface(iLoadBoundProfilePackageCallback);
                    this.mRemote.transact(18, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.euicc.IEuiccCardController
            public void cancelSession(String str, String str2, byte[] bArr, int i, ICancelSessionCallback iCancelSessionCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IEuiccCardController.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeByteArray(bArr);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iCancelSessionCallback);
                    this.mRemote.transact(19, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.euicc.IEuiccCardController
            public void listNotifications(String str, String str2, int i, IListNotificationsCallback iListNotificationsCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IEuiccCardController.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iListNotificationsCallback);
                    this.mRemote.transact(20, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.euicc.IEuiccCardController
            public void retrieveNotificationList(String str, String str2, int i, IRetrieveNotificationListCallback iRetrieveNotificationListCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IEuiccCardController.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iRetrieveNotificationListCallback);
                    this.mRemote.transact(21, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.euicc.IEuiccCardController
            public void retrieveNotification(String str, String str2, int i, IRetrieveNotificationCallback iRetrieveNotificationCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IEuiccCardController.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iRetrieveNotificationCallback);
                    this.mRemote.transact(22, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.euicc.IEuiccCardController
            public void removeNotificationFromList(String str, String str2, int i, IRemoveNotificationFromListCallback iRemoveNotificationFromListCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IEuiccCardController.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iRemoveNotificationFromListCallback);
                    this.mRemote.transact(23, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
