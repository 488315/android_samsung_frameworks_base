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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IEuiccCardController.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IEuiccCardController)) {
                return (IEuiccCardController) iInterfaceQueryLocalInterface;
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
                    String string = parcel.readString();
                    String string2 = parcel.readString();
                    IGetAllProfilesCallback iGetAllProfilesCallbackAsInterface = IGetAllProfilesCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    getAllProfiles(string, string2, iGetAllProfilesCallbackAsInterface);
                    return true;
                case 2:
                    String string3 = parcel.readString();
                    String string4 = parcel.readString();
                    String string5 = parcel.readString();
                    IGetProfileCallback iGetProfileCallbackAsInterface = IGetProfileCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    getProfile(string3, string4, string5, iGetProfileCallbackAsInterface);
                    return true;
                case 3:
                    String string6 = parcel.readString();
                    String string7 = parcel.readString();
                    int i3 = parcel.readInt();
                    IGetProfileCallback iGetProfileCallbackAsInterface2 = IGetProfileCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    getEnabledProfile(string6, string7, i3, iGetProfileCallbackAsInterface2);
                    return true;
                case 4:
                    String string8 = parcel.readString();
                    String string9 = parcel.readString();
                    String string10 = parcel.readString();
                    boolean z = parcel.readBoolean();
                    IDisableProfileCallback iDisableProfileCallbackAsInterface = IDisableProfileCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    disableProfile(string8, string9, string10, z, iDisableProfileCallbackAsInterface);
                    return true;
                case 5:
                    String string11 = parcel.readString();
                    String string12 = parcel.readString();
                    String string13 = parcel.readString();
                    int i4 = parcel.readInt();
                    boolean z2 = parcel.readBoolean();
                    ISwitchToProfileCallback iSwitchToProfileCallbackAsInterface = ISwitchToProfileCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    switchToProfile(string11, string12, string13, i4, z2, iSwitchToProfileCallbackAsInterface);
                    return true;
                case 6:
                    String string14 = parcel.readString();
                    String string15 = parcel.readString();
                    String string16 = parcel.readString();
                    String string17 = parcel.readString();
                    ISetNicknameCallback iSetNicknameCallbackAsInterface = ISetNicknameCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setNickname(string14, string15, string16, string17, iSetNicknameCallbackAsInterface);
                    return true;
                case 7:
                    String string18 = parcel.readString();
                    String string19 = parcel.readString();
                    String string20 = parcel.readString();
                    IDeleteProfileCallback iDeleteProfileCallbackAsInterface = IDeleteProfileCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    deleteProfile(string18, string19, string20, iDeleteProfileCallbackAsInterface);
                    return true;
                case 8:
                    String string21 = parcel.readString();
                    String string22 = parcel.readString();
                    int i5 = parcel.readInt();
                    IResetMemoryCallback iResetMemoryCallbackAsInterface = IResetMemoryCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    resetMemory(string21, string22, i5, iResetMemoryCallbackAsInterface);
                    return true;
                case 9:
                    String string23 = parcel.readString();
                    String string24 = parcel.readString();
                    IGetDefaultSmdpAddressCallback iGetDefaultSmdpAddressCallbackAsInterface = IGetDefaultSmdpAddressCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    getDefaultSmdpAddress(string23, string24, iGetDefaultSmdpAddressCallbackAsInterface);
                    return true;
                case 10:
                    String string25 = parcel.readString();
                    String string26 = parcel.readString();
                    IGetSmdsAddressCallback iGetSmdsAddressCallbackAsInterface = IGetSmdsAddressCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    getSmdsAddress(string25, string26, iGetSmdsAddressCallbackAsInterface);
                    return true;
                case 11:
                    String string27 = parcel.readString();
                    String string28 = parcel.readString();
                    String string29 = parcel.readString();
                    ISetDefaultSmdpAddressCallback iSetDefaultSmdpAddressCallbackAsInterface = ISetDefaultSmdpAddressCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setDefaultSmdpAddress(string27, string28, string29, iSetDefaultSmdpAddressCallbackAsInterface);
                    return true;
                case 12:
                    String string30 = parcel.readString();
                    String string31 = parcel.readString();
                    IGetRulesAuthTableCallback iGetRulesAuthTableCallbackAsInterface = IGetRulesAuthTableCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    getRulesAuthTable(string30, string31, iGetRulesAuthTableCallbackAsInterface);
                    return true;
                case 13:
                    String string32 = parcel.readString();
                    String string33 = parcel.readString();
                    IGetEuiccChallengeCallback iGetEuiccChallengeCallbackAsInterface = IGetEuiccChallengeCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    getEuiccChallenge(string32, string33, iGetEuiccChallengeCallbackAsInterface);
                    return true;
                case 14:
                    String string34 = parcel.readString();
                    String string35 = parcel.readString();
                    IGetEuiccInfo1Callback iGetEuiccInfo1CallbackAsInterface = IGetEuiccInfo1Callback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    getEuiccInfo1(string34, string35, iGetEuiccInfo1CallbackAsInterface);
                    return true;
                case 15:
                    String string36 = parcel.readString();
                    String string37 = parcel.readString();
                    IGetEuiccInfo2Callback iGetEuiccInfo2CallbackAsInterface = IGetEuiccInfo2Callback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    getEuiccInfo2(string36, string37, iGetEuiccInfo2CallbackAsInterface);
                    return true;
                case 16:
                    String string38 = parcel.readString();
                    String string39 = parcel.readString();
                    String string40 = parcel.readString();
                    byte[] bArrCreateByteArray = parcel.createByteArray();
                    byte[] bArrCreateByteArray2 = parcel.createByteArray();
                    byte[] bArrCreateByteArray3 = parcel.createByteArray();
                    byte[] bArrCreateByteArray4 = parcel.createByteArray();
                    IAuthenticateServerCallback iAuthenticateServerCallbackAsInterface = IAuthenticateServerCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    authenticateServer(string38, string39, string40, bArrCreateByteArray, bArrCreateByteArray2, bArrCreateByteArray3, bArrCreateByteArray4, iAuthenticateServerCallbackAsInterface);
                    return true;
                case 17:
                    String string41 = parcel.readString();
                    String string42 = parcel.readString();
                    byte[] bArrCreateByteArray5 = parcel.createByteArray();
                    byte[] bArrCreateByteArray6 = parcel.createByteArray();
                    byte[] bArrCreateByteArray7 = parcel.createByteArray();
                    byte[] bArrCreateByteArray8 = parcel.createByteArray();
                    IPrepareDownloadCallback iPrepareDownloadCallbackAsInterface = IPrepareDownloadCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    prepareDownload(string41, string42, bArrCreateByteArray5, bArrCreateByteArray6, bArrCreateByteArray7, bArrCreateByteArray8, iPrepareDownloadCallbackAsInterface);
                    return true;
                case 18:
                    String string43 = parcel.readString();
                    String string44 = parcel.readString();
                    byte[] bArrCreateByteArray9 = parcel.createByteArray();
                    ILoadBoundProfilePackageCallback iLoadBoundProfilePackageCallbackAsInterface = ILoadBoundProfilePackageCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    loadBoundProfilePackage(string43, string44, bArrCreateByteArray9, iLoadBoundProfilePackageCallbackAsInterface);
                    return true;
                case 19:
                    String string45 = parcel.readString();
                    String string46 = parcel.readString();
                    byte[] bArrCreateByteArray10 = parcel.createByteArray();
                    int i6 = parcel.readInt();
                    ICancelSessionCallback iCancelSessionCallbackAsInterface = ICancelSessionCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    cancelSession(string45, string46, bArrCreateByteArray10, i6, iCancelSessionCallbackAsInterface);
                    return true;
                case 20:
                    String string47 = parcel.readString();
                    String string48 = parcel.readString();
                    int i7 = parcel.readInt();
                    IListNotificationsCallback iListNotificationsCallbackAsInterface = IListNotificationsCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    listNotifications(string47, string48, i7, iListNotificationsCallbackAsInterface);
                    return true;
                case 21:
                    String string49 = parcel.readString();
                    String string50 = parcel.readString();
                    int i8 = parcel.readInt();
                    IRetrieveNotificationListCallback iRetrieveNotificationListCallbackAsInterface = IRetrieveNotificationListCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    retrieveNotificationList(string49, string50, i8, iRetrieveNotificationListCallbackAsInterface);
                    return true;
                case 22:
                    String string51 = parcel.readString();
                    String string52 = parcel.readString();
                    int i9 = parcel.readInt();
                    IRetrieveNotificationCallback iRetrieveNotificationCallbackAsInterface = IRetrieveNotificationCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    retrieveNotification(string51, string52, i9, iRetrieveNotificationCallbackAsInterface);
                    return true;
                case 23:
                    String string53 = parcel.readString();
                    String string54 = parcel.readString();
                    int i10 = parcel.readInt();
                    IRemoveNotificationFromListCallback iRemoveNotificationFromListCallbackAsInterface = IRemoveNotificationFromListCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    removeNotificationFromList(string53, string54, i10, iRemoveNotificationFromListCallbackAsInterface);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IEuiccCardController.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeStrongInterface(iGetAllProfilesCallback);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.euicc.IEuiccCardController
            public void getProfile(String str, String str2, String str3, IGetProfileCallback iGetProfileCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IEuiccCardController.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    parcelObtain.writeStrongInterface(iGetProfileCallback);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.euicc.IEuiccCardController
            public void getEnabledProfile(String str, String str2, int i, IGetProfileCallback iGetProfileCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IEuiccCardController.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iGetProfileCallback);
                    this.mRemote.transact(3, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.euicc.IEuiccCardController
            public void disableProfile(String str, String str2, String str3, boolean z, IDisableProfileCallback iDisableProfileCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IEuiccCardController.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeStrongInterface(iDisableProfileCallback);
                    this.mRemote.transact(4, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.euicc.IEuiccCardController
            public void switchToProfile(String str, String str2, String str3, int i, boolean z, ISwitchToProfileCallback iSwitchToProfileCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IEuiccCardController.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeStrongInterface(iSwitchToProfileCallback);
                    this.mRemote.transact(5, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.euicc.IEuiccCardController
            public void setNickname(String str, String str2, String str3, String str4, ISetNicknameCallback iSetNicknameCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IEuiccCardController.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    parcelObtain.writeString(str4);
                    parcelObtain.writeStrongInterface(iSetNicknameCallback);
                    this.mRemote.transact(6, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.euicc.IEuiccCardController
            public void deleteProfile(String str, String str2, String str3, IDeleteProfileCallback iDeleteProfileCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IEuiccCardController.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    parcelObtain.writeStrongInterface(iDeleteProfileCallback);
                    this.mRemote.transact(7, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.euicc.IEuiccCardController
            public void resetMemory(String str, String str2, int i, IResetMemoryCallback iResetMemoryCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IEuiccCardController.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iResetMemoryCallback);
                    this.mRemote.transact(8, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.euicc.IEuiccCardController
            public void getDefaultSmdpAddress(String str, String str2, IGetDefaultSmdpAddressCallback iGetDefaultSmdpAddressCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IEuiccCardController.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeStrongInterface(iGetDefaultSmdpAddressCallback);
                    this.mRemote.transact(9, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.euicc.IEuiccCardController
            public void getSmdsAddress(String str, String str2, IGetSmdsAddressCallback iGetSmdsAddressCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IEuiccCardController.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeStrongInterface(iGetSmdsAddressCallback);
                    this.mRemote.transact(10, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.euicc.IEuiccCardController
            public void setDefaultSmdpAddress(String str, String str2, String str3, ISetDefaultSmdpAddressCallback iSetDefaultSmdpAddressCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IEuiccCardController.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    parcelObtain.writeStrongInterface(iSetDefaultSmdpAddressCallback);
                    this.mRemote.transact(11, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.euicc.IEuiccCardController
            public void getRulesAuthTable(String str, String str2, IGetRulesAuthTableCallback iGetRulesAuthTableCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IEuiccCardController.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeStrongInterface(iGetRulesAuthTableCallback);
                    this.mRemote.transact(12, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.euicc.IEuiccCardController
            public void getEuiccChallenge(String str, String str2, IGetEuiccChallengeCallback iGetEuiccChallengeCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IEuiccCardController.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeStrongInterface(iGetEuiccChallengeCallback);
                    this.mRemote.transact(13, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.euicc.IEuiccCardController
            public void getEuiccInfo1(String str, String str2, IGetEuiccInfo1Callback iGetEuiccInfo1Callback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IEuiccCardController.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeStrongInterface(iGetEuiccInfo1Callback);
                    this.mRemote.transact(14, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.euicc.IEuiccCardController
            public void getEuiccInfo2(String str, String str2, IGetEuiccInfo2Callback iGetEuiccInfo2Callback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IEuiccCardController.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeStrongInterface(iGetEuiccInfo2Callback);
                    this.mRemote.transact(15, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.euicc.IEuiccCardController
            public void authenticateServer(String str, String str2, String str3, byte[] bArr, byte[] bArr2, byte[] bArr3, byte[] bArr4, IAuthenticateServerCallback iAuthenticateServerCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IEuiccCardController.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    parcelObtain.writeByteArray(bArr);
                    parcelObtain.writeByteArray(bArr2);
                    parcelObtain.writeByteArray(bArr3);
                    parcelObtain.writeByteArray(bArr4);
                    parcelObtain.writeStrongInterface(iAuthenticateServerCallback);
                    this.mRemote.transact(16, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.euicc.IEuiccCardController
            public void prepareDownload(String str, String str2, byte[] bArr, byte[] bArr2, byte[] bArr3, byte[] bArr4, IPrepareDownloadCallback iPrepareDownloadCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IEuiccCardController.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeByteArray(bArr);
                    parcelObtain.writeByteArray(bArr2);
                    parcelObtain.writeByteArray(bArr3);
                    parcelObtain.writeByteArray(bArr4);
                    parcelObtain.writeStrongInterface(iPrepareDownloadCallback);
                    this.mRemote.transact(17, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.euicc.IEuiccCardController
            public void loadBoundProfilePackage(String str, String str2, byte[] bArr, ILoadBoundProfilePackageCallback iLoadBoundProfilePackageCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IEuiccCardController.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeByteArray(bArr);
                    parcelObtain.writeStrongInterface(iLoadBoundProfilePackageCallback);
                    this.mRemote.transact(18, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.euicc.IEuiccCardController
            public void cancelSession(String str, String str2, byte[] bArr, int i, ICancelSessionCallback iCancelSessionCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IEuiccCardController.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeByteArray(bArr);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iCancelSessionCallback);
                    this.mRemote.transact(19, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.euicc.IEuiccCardController
            public void listNotifications(String str, String str2, int i, IListNotificationsCallback iListNotificationsCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IEuiccCardController.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iListNotificationsCallback);
                    this.mRemote.transact(20, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.euicc.IEuiccCardController
            public void retrieveNotificationList(String str, String str2, int i, IRetrieveNotificationListCallback iRetrieveNotificationListCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IEuiccCardController.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iRetrieveNotificationListCallback);
                    this.mRemote.transact(21, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.euicc.IEuiccCardController
            public void retrieveNotification(String str, String str2, int i, IRetrieveNotificationCallback iRetrieveNotificationCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IEuiccCardController.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iRetrieveNotificationCallback);
                    this.mRemote.transact(22, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.euicc.IEuiccCardController
            public void removeNotificationFromList(String str, String str2, int i, IRemoveNotificationFromListCallback iRemoveNotificationFromListCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IEuiccCardController.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iRemoveNotificationFromListCallback);
                    this.mRemote.transact(23, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
