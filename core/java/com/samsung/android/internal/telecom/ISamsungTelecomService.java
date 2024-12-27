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
import java.util.List;

/* loaded from: classes6.dex */
public interface ISamsungTelecomService extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.internal.telecom.ISamsungTelecomService";

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

    public static class Default implements ISamsungTelecomService {
        @Override // com.samsung.android.internal.telecom.ISamsungTelecomService
        public ParceledListSlice<PhoneAccount> getAllowedSelfManagedPhoneAccounts(String callingPackage, String callingFeatureId) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.internal.telecom.ISamsungTelecomService
        public ParceledListSlice<Bundle> getAllowedPhoneAccountInfo(String callingPackage, String callingFeatureId) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.internal.telecom.ISamsungTelecomService
        public ParceledListSlice<Bundle> getAllowedPhoneAccountInfos(boolean includeRegisteredAccounts, boolean includeSimSubscriptionAccounts, String callingPackage, String callingFeatureId) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.internal.telecom.ISamsungTelecomService
        public ParceledListSlice<SemPhoneAccount> getAllowedPhoneAccounts(boolean includeRegisteredAccounts, boolean includeSimSubscriptionAccounts, String callingPackage, String callingFeatureId) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.internal.telecom.ISamsungTelecomService
        public void addConferenceParticipants(List<Uri> participants, Bundle extras, String callingPackage, String callingFeatureId) throws RemoteException {
        }

        @Override // com.samsung.android.internal.telecom.ISamsungTelecomService
        public void silenceRinger(int keyCode, Bundle reason, String callingPackage, String callingFeatureId) throws RemoteException {
        }

        @Override // com.samsung.android.internal.telecom.ISamsungTelecomService
        public boolean endCall(int keyCode, Bundle reason, String callingPackage, String callingFeatureId) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.internal.telecom.ISamsungTelecomService
        public void acceptRingingCall(int keyCode, Bundle reason, String callingPackage, String callingFeatureId) throws RemoteException {
        }

        @Override // com.samsung.android.internal.telecom.ISamsungTelecomService
        public void acceptRingingCallWithVideoState(int videoState, int keyCode, Bundle reason, String callingPackage, String callingFeatureId) throws RemoteException {
        }

        @Override // com.samsung.android.internal.telecom.ISamsungTelecomService
        public boolean isInCall(int callFilter, boolean includeExternalCall, String callingPackage, String callingFeatureId) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.internal.telecom.ISamsungTelecomService
        public void showInCallScreen(boolean showDialpad, UserHandle callingUser, String callingPackage, String callingFeatureId) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

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

        public Stub() {
            attachInterface(this, ISamsungTelecomService.DESCRIPTOR);
        }

        public static ISamsungTelecomService asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(ISamsungTelecomService.DESCRIPTOR);
            if (iin != null && (iin instanceof ISamsungTelecomService)) {
                return (ISamsungTelecomService) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
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
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(ISamsungTelecomService.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(ISamsungTelecomService.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    String _arg0 = data.readString();
                    String _arg1 = data.readString();
                    data.enforceNoDataAvail();
                    ParceledListSlice<PhoneAccount> _result = getAllowedSelfManagedPhoneAccounts(_arg0, _arg1);
                    reply.writeNoException();
                    reply.writeTypedObject(_result, 1);
                    return true;
                case 2:
                    String _arg02 = data.readString();
                    String _arg12 = data.readString();
                    data.enforceNoDataAvail();
                    ParceledListSlice<Bundle> _result2 = getAllowedPhoneAccountInfo(_arg02, _arg12);
                    reply.writeNoException();
                    reply.writeTypedObject(_result2, 1);
                    return true;
                case 3:
                    boolean _arg03 = data.readBoolean();
                    boolean _arg13 = data.readBoolean();
                    String _arg2 = data.readString();
                    String _arg3 = data.readString();
                    data.enforceNoDataAvail();
                    ParceledListSlice<Bundle> _result3 = getAllowedPhoneAccountInfos(_arg03, _arg13, _arg2, _arg3);
                    reply.writeNoException();
                    reply.writeTypedObject(_result3, 1);
                    return true;
                case 4:
                    boolean _arg04 = data.readBoolean();
                    boolean _arg14 = data.readBoolean();
                    String _arg22 = data.readString();
                    String _arg32 = data.readString();
                    data.enforceNoDataAvail();
                    ParceledListSlice<SemPhoneAccount> _result4 = getAllowedPhoneAccounts(_arg04, _arg14, _arg22, _arg32);
                    reply.writeNoException();
                    reply.writeTypedObject(_result4, 1);
                    return true;
                case 5:
                    List<Uri> _arg05 = data.createTypedArrayList(Uri.CREATOR);
                    Bundle _arg15 = (Bundle) data.readTypedObject(Bundle.CREATOR);
                    String _arg23 = data.readString();
                    String _arg33 = data.readString();
                    data.enforceNoDataAvail();
                    addConferenceParticipants(_arg05, _arg15, _arg23, _arg33);
                    reply.writeNoException();
                    return true;
                case 6:
                    int _arg06 = data.readInt();
                    Bundle _arg16 = (Bundle) data.readTypedObject(Bundle.CREATOR);
                    String _arg24 = data.readString();
                    String _arg34 = data.readString();
                    data.enforceNoDataAvail();
                    silenceRinger(_arg06, _arg16, _arg24, _arg34);
                    reply.writeNoException();
                    return true;
                case 7:
                    int _arg07 = data.readInt();
                    Bundle _arg17 = (Bundle) data.readTypedObject(Bundle.CREATOR);
                    String _arg25 = data.readString();
                    String _arg35 = data.readString();
                    data.enforceNoDataAvail();
                    boolean _result5 = endCall(_arg07, _arg17, _arg25, _arg35);
                    reply.writeNoException();
                    reply.writeBoolean(_result5);
                    return true;
                case 8:
                    int _arg08 = data.readInt();
                    Bundle _arg18 = (Bundle) data.readTypedObject(Bundle.CREATOR);
                    String _arg26 = data.readString();
                    String _arg36 = data.readString();
                    data.enforceNoDataAvail();
                    acceptRingingCall(_arg08, _arg18, _arg26, _arg36);
                    reply.writeNoException();
                    return true;
                case 9:
                    int _arg09 = data.readInt();
                    int _arg19 = data.readInt();
                    Bundle _arg27 = (Bundle) data.readTypedObject(Bundle.CREATOR);
                    String _arg37 = data.readString();
                    String _arg4 = data.readString();
                    data.enforceNoDataAvail();
                    acceptRingingCallWithVideoState(_arg09, _arg19, _arg27, _arg37, _arg4);
                    reply.writeNoException();
                    return true;
                case 10:
                    int _arg010 = data.readInt();
                    boolean _arg110 = data.readBoolean();
                    String _arg28 = data.readString();
                    String _arg38 = data.readString();
                    data.enforceNoDataAvail();
                    boolean _result6 = isInCall(_arg010, _arg110, _arg28, _arg38);
                    reply.writeNoException();
                    reply.writeBoolean(_result6);
                    return true;
                case 11:
                    boolean _arg011 = data.readBoolean();
                    UserHandle _arg111 = (UserHandle) data.readTypedObject(UserHandle.CREATOR);
                    String _arg29 = data.readString();
                    String _arg39 = data.readString();
                    data.enforceNoDataAvail();
                    showInCallScreen(_arg011, _arg111, _arg29, _arg39);
                    reply.writeNoException();
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements ISamsungTelecomService {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISamsungTelecomService.DESCRIPTOR;
            }

            @Override // com.samsung.android.internal.telecom.ISamsungTelecomService
            public ParceledListSlice<PhoneAccount> getAllowedSelfManagedPhoneAccounts(String callingPackage, String callingFeatureId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISamsungTelecomService.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    _data.writeString(callingFeatureId);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                    ParceledListSlice<PhoneAccount> _result = (ParceledListSlice) _reply.readTypedObject(ParceledListSlice.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.internal.telecom.ISamsungTelecomService
            public ParceledListSlice<Bundle> getAllowedPhoneAccountInfo(String callingPackage, String callingFeatureId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISamsungTelecomService.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    _data.writeString(callingFeatureId);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                    ParceledListSlice<Bundle> _result = (ParceledListSlice) _reply.readTypedObject(ParceledListSlice.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.internal.telecom.ISamsungTelecomService
            public ParceledListSlice<Bundle> getAllowedPhoneAccountInfos(boolean includeRegisteredAccounts, boolean includeSimSubscriptionAccounts, String callingPackage, String callingFeatureId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISamsungTelecomService.DESCRIPTOR);
                    _data.writeBoolean(includeRegisteredAccounts);
                    _data.writeBoolean(includeSimSubscriptionAccounts);
                    _data.writeString(callingPackage);
                    _data.writeString(callingFeatureId);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                    ParceledListSlice<Bundle> _result = (ParceledListSlice) _reply.readTypedObject(ParceledListSlice.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.internal.telecom.ISamsungTelecomService
            public ParceledListSlice<SemPhoneAccount> getAllowedPhoneAccounts(boolean includeRegisteredAccounts, boolean includeSimSubscriptionAccounts, String callingPackage, String callingFeatureId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISamsungTelecomService.DESCRIPTOR);
                    _data.writeBoolean(includeRegisteredAccounts);
                    _data.writeBoolean(includeSimSubscriptionAccounts);
                    _data.writeString(callingPackage);
                    _data.writeString(callingFeatureId);
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                    ParceledListSlice<SemPhoneAccount> _result = (ParceledListSlice) _reply.readTypedObject(ParceledListSlice.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.internal.telecom.ISamsungTelecomService
            public void addConferenceParticipants(List<Uri> participants, Bundle extras, String callingPackage, String callingFeatureId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISamsungTelecomService.DESCRIPTOR);
                    _data.writeTypedList(participants, 0);
                    _data.writeTypedObject(extras, 0);
                    _data.writeString(callingPackage);
                    _data.writeString(callingFeatureId);
                    this.mRemote.transact(5, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.internal.telecom.ISamsungTelecomService
            public void silenceRinger(int keyCode, Bundle reason, String callingPackage, String callingFeatureId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISamsungTelecomService.DESCRIPTOR);
                    _data.writeInt(keyCode);
                    _data.writeTypedObject(reason, 0);
                    _data.writeString(callingPackage);
                    _data.writeString(callingFeatureId);
                    this.mRemote.transact(6, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.internal.telecom.ISamsungTelecomService
            public boolean endCall(int keyCode, Bundle reason, String callingPackage, String callingFeatureId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISamsungTelecomService.DESCRIPTOR);
                    _data.writeInt(keyCode);
                    _data.writeTypedObject(reason, 0);
                    _data.writeString(callingPackage);
                    _data.writeString(callingFeatureId);
                    this.mRemote.transact(7, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.internal.telecom.ISamsungTelecomService
            public void acceptRingingCall(int keyCode, Bundle reason, String callingPackage, String callingFeatureId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISamsungTelecomService.DESCRIPTOR);
                    _data.writeInt(keyCode);
                    _data.writeTypedObject(reason, 0);
                    _data.writeString(callingPackage);
                    _data.writeString(callingFeatureId);
                    this.mRemote.transact(8, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.internal.telecom.ISamsungTelecomService
            public void acceptRingingCallWithVideoState(int videoState, int keyCode, Bundle reason, String callingPackage, String callingFeatureId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISamsungTelecomService.DESCRIPTOR);
                    _data.writeInt(videoState);
                    _data.writeInt(keyCode);
                    _data.writeTypedObject(reason, 0);
                    _data.writeString(callingPackage);
                    _data.writeString(callingFeatureId);
                    this.mRemote.transact(9, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.internal.telecom.ISamsungTelecomService
            public boolean isInCall(int callFilter, boolean includeExternalCall, String callingPackage, String callingFeatureId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISamsungTelecomService.DESCRIPTOR);
                    _data.writeInt(callFilter);
                    _data.writeBoolean(includeExternalCall);
                    _data.writeString(callingPackage);
                    _data.writeString(callingFeatureId);
                    this.mRemote.transact(10, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.internal.telecom.ISamsungTelecomService
            public void showInCallScreen(boolean showDialpad, UserHandle callingUser, String callingPackage, String callingFeatureId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISamsungTelecomService.DESCRIPTOR);
                    _data.writeBoolean(showDialpad);
                    _data.writeTypedObject(callingUser, 0);
                    _data.writeString(callingPackage);
                    _data.writeString(callingFeatureId);
                    this.mRemote.transact(11, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 10;
        }
    }
}
