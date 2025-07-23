package com.samsung.android.internal.telecom;

import android.content.pm.ParceledListSlice;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.os.UserHandle;
import android.telecom.PhoneAccount;
import com.samsung.android.telecom.SemPhoneAccount;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes6.dex */
public interface ISamsungTelecomService extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.internal.telecom.ISamsungTelecomService";

    public static class Default implements ISamsungTelecomService {
        @Override // com.samsung.android.internal.telecom.ISamsungTelecomService
        public void acceptRingingCall(int i, Bundle bundle, String str, String str2) throws RemoteException {
        }

        @Override // com.samsung.android.internal.telecom.ISamsungTelecomService
        public void acceptRingingCallWithVideoState(int i, int i2, Bundle bundle, String str, String str2) throws RemoteException {
        }

        @Override // com.samsung.android.internal.telecom.ISamsungTelecomService
        public void addConferenceParticipants(List<Uri> list, Bundle bundle, String str, String str2) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.internal.telecom.ISamsungTelecomService
        public boolean endCall(int i, Bundle bundle, String str, String str2) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.internal.telecom.ISamsungTelecomService
        public ParceledListSlice<Bundle> getAllowedPhoneAccountInfo(String str, String str2) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.internal.telecom.ISamsungTelecomService
        public ParceledListSlice<Bundle> getAllowedPhoneAccountInfos(boolean z, boolean z2, String str, String str2) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.internal.telecom.ISamsungTelecomService
        public ParceledListSlice<SemPhoneAccount> getAllowedPhoneAccounts(boolean z, boolean z2, String str, String str2) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.internal.telecom.ISamsungTelecomService
        public ParceledListSlice<PhoneAccount> getAllowedSelfManagedPhoneAccounts(String str, String str2) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.internal.telecom.ISamsungTelecomService
        public boolean isInCall(int i, boolean z, String str, String str2) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.internal.telecom.ISamsungTelecomService
        public void showInCallScreen(boolean z, UserHandle userHandle, String str, String str2) throws RemoteException {
        }

        @Override // com.samsung.android.internal.telecom.ISamsungTelecomService
        public void silenceRinger(int i, Bundle bundle, String str, String str2) throws RemoteException {
        }
    }

    void acceptRingingCall(int i, Bundle bundle, String str, String str2) throws RemoteException;

    void acceptRingingCallWithVideoState(int i, int i2, Bundle bundle, String str, String str2) throws RemoteException;

    void addConferenceParticipants(List<Uri> list, Bundle bundle, String str, String str2) throws RemoteException;

    boolean endCall(int i, Bundle bundle, String str, String str2) throws RemoteException;

    ParceledListSlice<Bundle> getAllowedPhoneAccountInfo(String str, String str2) throws RemoteException;

    ParceledListSlice<Bundle> getAllowedPhoneAccountInfos(boolean z, boolean z2, String str, String str2) throws RemoteException;

    ParceledListSlice<SemPhoneAccount> getAllowedPhoneAccounts(boolean z, boolean z2, String str, String str2) throws RemoteException;

    ParceledListSlice<PhoneAccount> getAllowedSelfManagedPhoneAccounts(String str, String str2) throws RemoteException;

    boolean isInCall(int i, boolean z, String str, String str2) throws RemoteException;

    void showInCallScreen(boolean z, UserHandle userHandle, String str, String str2) throws RemoteException;

    void silenceRinger(int i, Bundle bundle, String str, String str2) throws RemoteException;

    public static abstract class Stub extends Binder implements ISamsungTelecomService {
        static final int TRANSACTION_acceptRingingCall = 8;
        static final int TRANSACTION_acceptRingingCallWithVideoState = 9;
        static final int TRANSACTION_addConferenceParticipants = 5;
        static final int TRANSACTION_endCall = 7;
        static final int TRANSACTION_getAllowedPhoneAccountInfo = 2;
        static final int TRANSACTION_getAllowedPhoneAccountInfos = 3;
        static final int TRANSACTION_getAllowedPhoneAccounts = 4;
        static final int TRANSACTION_getAllowedSelfManagedPhoneAccounts = 1;
        static final int TRANSACTION_isInCall = 10;
        static final int TRANSACTION_showInCallScreen = 11;
        static final int TRANSACTION_silenceRinger = 6;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 10;
        }

        public Stub() {
            attachInterface(this, ISamsungTelecomService.DESCRIPTOR);
        }

        public static ISamsungTelecomService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ISamsungTelecomService.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ISamsungTelecomService)) {
                return (ISamsungTelecomService) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "getAllowedSelfManagedPhoneAccounts";
                case 2:
                    return "getAllowedPhoneAccountInfo";
                case 3:
                    return "getAllowedPhoneAccountInfos";
                case 4:
                    return "getAllowedPhoneAccounts";
                case 5:
                    return "addConferenceParticipants";
                case 6:
                    return "silenceRinger";
                case 7:
                    return "endCall";
                case 8:
                    return "acceptRingingCall";
                case 9:
                    return "acceptRingingCallWithVideoState";
                case 10:
                    return "isInCall";
                case 11:
                    return "showInCallScreen";
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
                parcel.enforceInterface(ISamsungTelecomService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ISamsungTelecomService.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    String readString = parcel.readString();
                    String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    ParceledListSlice<PhoneAccount> allowedSelfManagedPhoneAccounts = getAllowedSelfManagedPhoneAccounts(readString, readString2);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(allowedSelfManagedPhoneAccounts, 1);
                    return true;
                case 2:
                    String readString3 = parcel.readString();
                    String readString4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    ParceledListSlice<Bundle> allowedPhoneAccountInfo = getAllowedPhoneAccountInfo(readString3, readString4);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(allowedPhoneAccountInfo, 1);
                    return true;
                case 3:
                    boolean readBoolean = parcel.readBoolean();
                    boolean readBoolean2 = parcel.readBoolean();
                    String readString5 = parcel.readString();
                    String readString6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    ParceledListSlice<Bundle> allowedPhoneAccountInfos = getAllowedPhoneAccountInfos(readBoolean, readBoolean2, readString5, readString6);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(allowedPhoneAccountInfos, 1);
                    return true;
                case 4:
                    boolean readBoolean3 = parcel.readBoolean();
                    boolean readBoolean4 = parcel.readBoolean();
                    String readString7 = parcel.readString();
                    String readString8 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    ParceledListSlice<SemPhoneAccount> allowedPhoneAccounts = getAllowedPhoneAccounts(readBoolean3, readBoolean4, readString7, readString8);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(allowedPhoneAccounts, 1);
                    return true;
                case 5:
                    ArrayList createTypedArrayList = parcel.createTypedArrayList(Uri.CREATOR);
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    String readString9 = parcel.readString();
                    String readString10 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    addConferenceParticipants(createTypedArrayList, bundle, readString9, readString10);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    int readInt = parcel.readInt();
                    Bundle bundle2 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    String readString11 = parcel.readString();
                    String readString12 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    silenceRinger(readInt, bundle2, readString11, readString12);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    int readInt2 = parcel.readInt();
                    Bundle bundle3 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    String readString13 = parcel.readString();
                    String readString14 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean endCall = endCall(readInt2, bundle3, readString13, readString14);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(endCall);
                    return true;
                case 8:
                    int readInt3 = parcel.readInt();
                    Bundle bundle4 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    String readString15 = parcel.readString();
                    String readString16 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    acceptRingingCall(readInt3, bundle4, readString15, readString16);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    int readInt4 = parcel.readInt();
                    int readInt5 = parcel.readInt();
                    Bundle bundle5 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    String readString17 = parcel.readString();
                    String readString18 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    acceptRingingCallWithVideoState(readInt4, readInt5, bundle5, readString17, readString18);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    int readInt6 = parcel.readInt();
                    boolean readBoolean5 = parcel.readBoolean();
                    String readString19 = parcel.readString();
                    String readString20 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isInCall = isInCall(readInt6, readBoolean5, readString19, readString20);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isInCall);
                    return true;
                case 11:
                    boolean readBoolean6 = parcel.readBoolean();
                    UserHandle userHandle = (UserHandle) parcel.readTypedObject(UserHandle.CREATOR);
                    String readString21 = parcel.readString();
                    String readString22 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    showInCallScreen(readBoolean6, userHandle, readString21, readString22);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements ISamsungTelecomService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISamsungTelecomService.DESCRIPTOR;
            }

            @Override // com.samsung.android.internal.telecom.ISamsungTelecomService
            public ParceledListSlice<PhoneAccount> getAllowedSelfManagedPhoneAccounts(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISamsungTelecomService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ParceledListSlice) obtain2.readTypedObject(ParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.internal.telecom.ISamsungTelecomService
            public ParceledListSlice<Bundle> getAllowedPhoneAccountInfo(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISamsungTelecomService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ParceledListSlice) obtain2.readTypedObject(ParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.internal.telecom.ISamsungTelecomService
            public ParceledListSlice<Bundle> getAllowedPhoneAccountInfos(boolean z, boolean z2, String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISamsungTelecomService.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeBoolean(z2);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ParceledListSlice) obtain2.readTypedObject(ParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.internal.telecom.ISamsungTelecomService
            public ParceledListSlice<SemPhoneAccount> getAllowedPhoneAccounts(boolean z, boolean z2, String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISamsungTelecomService.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeBoolean(z2);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ParceledListSlice) obtain2.readTypedObject(ParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.internal.telecom.ISamsungTelecomService
            public void addConferenceParticipants(List<Uri> list, Bundle bundle, String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISamsungTelecomService.DESCRIPTOR);
                    obtain.writeTypedList(list, 0);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.internal.telecom.ISamsungTelecomService
            public void silenceRinger(int i, Bundle bundle, String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISamsungTelecomService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.internal.telecom.ISamsungTelecomService
            public boolean endCall(int i, Bundle bundle, String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISamsungTelecomService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.internal.telecom.ISamsungTelecomService
            public void acceptRingingCall(int i, Bundle bundle, String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISamsungTelecomService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.internal.telecom.ISamsungTelecomService
            public void acceptRingingCallWithVideoState(int i, int i2, Bundle bundle, String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISamsungTelecomService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.internal.telecom.ISamsungTelecomService
            public boolean isInCall(int i, boolean z, String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISamsungTelecomService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.internal.telecom.ISamsungTelecomService
            public void showInCallScreen(boolean z, UserHandle userHandle, String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISamsungTelecomService.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeTypedObject(userHandle, 0);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
