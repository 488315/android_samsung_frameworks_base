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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(ISamsungTelecomService.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ISamsungTelecomService)) {
                return (ISamsungTelecomService) iInterfaceQueryLocalInterface;
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
                    String string = parcel.readString();
                    String string2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    ParceledListSlice<PhoneAccount> allowedSelfManagedPhoneAccounts = getAllowedSelfManagedPhoneAccounts(string, string2);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(allowedSelfManagedPhoneAccounts, 1);
                    return true;
                case 2:
                    String string3 = parcel.readString();
                    String string4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    ParceledListSlice<Bundle> allowedPhoneAccountInfo = getAllowedPhoneAccountInfo(string3, string4);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(allowedPhoneAccountInfo, 1);
                    return true;
                case 3:
                    boolean z = parcel.readBoolean();
                    boolean z2 = parcel.readBoolean();
                    String string5 = parcel.readString();
                    String string6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    ParceledListSlice<Bundle> allowedPhoneAccountInfos = getAllowedPhoneAccountInfos(z, z2, string5, string6);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(allowedPhoneAccountInfos, 1);
                    return true;
                case 4:
                    boolean z3 = parcel.readBoolean();
                    boolean z4 = parcel.readBoolean();
                    String string7 = parcel.readString();
                    String string8 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    ParceledListSlice<SemPhoneAccount> allowedPhoneAccounts = getAllowedPhoneAccounts(z3, z4, string7, string8);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(allowedPhoneAccounts, 1);
                    return true;
                case 5:
                    ArrayList arrayListCreateTypedArrayList = parcel.createTypedArrayList(Uri.CREATOR);
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    String string9 = parcel.readString();
                    String string10 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    addConferenceParticipants(arrayListCreateTypedArrayList, bundle, string9, string10);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    int i3 = parcel.readInt();
                    Bundle bundle2 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    String string11 = parcel.readString();
                    String string12 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    silenceRinger(i3, bundle2, string11, string12);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    int i4 = parcel.readInt();
                    Bundle bundle3 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    String string13 = parcel.readString();
                    String string14 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zEndCall = endCall(i4, bundle3, string13, string14);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zEndCall);
                    return true;
                case 8:
                    int i5 = parcel.readInt();
                    Bundle bundle4 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    String string15 = parcel.readString();
                    String string16 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    acceptRingingCall(i5, bundle4, string15, string16);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    int i6 = parcel.readInt();
                    int i7 = parcel.readInt();
                    Bundle bundle5 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    String string17 = parcel.readString();
                    String string18 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    acceptRingingCallWithVideoState(i6, i7, bundle5, string17, string18);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    int i8 = parcel.readInt();
                    boolean z5 = parcel.readBoolean();
                    String string19 = parcel.readString();
                    String string20 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zIsInCall = isInCall(i8, z5, string19, string20);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsInCall);
                    return true;
                case 11:
                    boolean z6 = parcel.readBoolean();
                    UserHandle userHandle = (UserHandle) parcel.readTypedObject(UserHandle.CREATOR);
                    String string21 = parcel.readString();
                    String string22 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    showInCallScreen(z6, userHandle, string21, string22);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISamsungTelecomService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ParceledListSlice) parcelObtain2.readTypedObject(ParceledListSlice.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.internal.telecom.ISamsungTelecomService
            public ParceledListSlice<Bundle> getAllowedPhoneAccountInfo(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISamsungTelecomService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ParceledListSlice) parcelObtain2.readTypedObject(ParceledListSlice.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.internal.telecom.ISamsungTelecomService
            public ParceledListSlice<Bundle> getAllowedPhoneAccountInfos(boolean z, boolean z2, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISamsungTelecomService.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeBoolean(z2);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ParceledListSlice) parcelObtain2.readTypedObject(ParceledListSlice.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.internal.telecom.ISamsungTelecomService
            public ParceledListSlice<SemPhoneAccount> getAllowedPhoneAccounts(boolean z, boolean z2, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISamsungTelecomService.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeBoolean(z2);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ParceledListSlice) parcelObtain2.readTypedObject(ParceledListSlice.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.internal.telecom.ISamsungTelecomService
            public void addConferenceParticipants(List<Uri> list, Bundle bundle, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISamsungTelecomService.DESCRIPTOR);
                    parcelObtain.writeTypedList(list, 0);
                    parcelObtain.writeTypedObject(bundle, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.internal.telecom.ISamsungTelecomService
            public void silenceRinger(int i, Bundle bundle, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISamsungTelecomService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(bundle, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.internal.telecom.ISamsungTelecomService
            public boolean endCall(int i, Bundle bundle, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISamsungTelecomService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(bundle, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.internal.telecom.ISamsungTelecomService
            public void acceptRingingCall(int i, Bundle bundle, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISamsungTelecomService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(bundle, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.internal.telecom.ISamsungTelecomService
            public void acceptRingingCallWithVideoState(int i, int i2, Bundle bundle, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISamsungTelecomService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeTypedObject(bundle, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.internal.telecom.ISamsungTelecomService
            public boolean isInCall(int i, boolean z, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISamsungTelecomService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.internal.telecom.ISamsungTelecomService
            public void showInCallScreen(boolean z, UserHandle userHandle, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISamsungTelecomService.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeTypedObject(userHandle, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
