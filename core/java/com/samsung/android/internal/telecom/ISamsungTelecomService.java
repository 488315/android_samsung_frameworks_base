package com.samsung.android.internal.telecom;

import android.content.p002pm.ParceledListSlice;
import android.p009os.Binder;
import android.p009os.Bundle;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;
import android.p009os.UserHandle;
import com.samsung.android.telecom.SemPhoneAccount;

/* loaded from: classes5.dex */
public interface ISamsungTelecomService extends IInterface {
  public static final String DESCRIPTOR =
      "com.samsung.android.internal.telecom.ISamsungTelecomService";

  void acceptRingingCall(int i, Bundle bundle, String str, String str2) throws RemoteException;

  void acceptRingingCallWithVideoState(int i, int i2, Bundle bundle, String str, String str2)
      throws RemoteException;

  boolean endCall(int i, Bundle bundle, String str, String str2) throws RemoteException;

  ParceledListSlice<SemPhoneAccount> getAllowedPhoneAccounts(
      boolean z, boolean z2, String str, String str2) throws RemoteException;

  boolean isInCall(int i, boolean z, String str, String str2) throws RemoteException;

  void showInCallScreen(boolean z, UserHandle userHandle, String str, String str2)
      throws RemoteException;

  void silenceRinger(int i, Bundle bundle, String str, String str2) throws RemoteException;

  public static class Default implements ISamsungTelecomService {
    @Override // com.samsung.android.internal.telecom.ISamsungTelecomService
    public ParceledListSlice<SemPhoneAccount> getAllowedPhoneAccounts(
        boolean includeRegisteredAccounts,
        boolean includeSimSubscriptionAccounts,
        String callingPackage,
        String callingFeatureId)
        throws RemoteException {
      return null;
    }

    @Override // com.samsung.android.internal.telecom.ISamsungTelecomService
    public void silenceRinger(
        int keyCode, Bundle reason, String callingPackage, String callingFeatureId)
        throws RemoteException {}

    @Override // com.samsung.android.internal.telecom.ISamsungTelecomService
    public boolean endCall(
        int keyCode, Bundle reason, String callingPackage, String callingFeatureId)
        throws RemoteException {
      return false;
    }

    @Override // com.samsung.android.internal.telecom.ISamsungTelecomService
    public void acceptRingingCall(
        int keyCode, Bundle reason, String callingPackage, String callingFeatureId)
        throws RemoteException {}

    @Override // com.samsung.android.internal.telecom.ISamsungTelecomService
    public void acceptRingingCallWithVideoState(
        int videoState, int keyCode, Bundle reason, String callingPackage, String callingFeatureId)
        throws RemoteException {}

    @Override // com.samsung.android.internal.telecom.ISamsungTelecomService
    public boolean isInCall(
        int callFilter, boolean includeExternalCall, String callingPackage, String callingFeatureId)
        throws RemoteException {
      return false;
    }

    @Override // com.samsung.android.internal.telecom.ISamsungTelecomService
    public void showInCallScreen(
        boolean showDialpad, UserHandle callingUser, String callingPackage, String callingFeatureId)
        throws RemoteException {}

    @Override // android.p009os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements ISamsungTelecomService {
    static final int TRANSACTION_acceptRingingCall = 4;
    static final int TRANSACTION_acceptRingingCallWithVideoState = 5;
    static final int TRANSACTION_endCall = 3;
    static final int TRANSACTION_getAllowedPhoneAccounts = 1;
    static final int TRANSACTION_isInCall = 6;
    static final int TRANSACTION_showInCallScreen = 7;
    static final int TRANSACTION_silenceRinger = 2;

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

    @Override // android.p009os.IInterface
    public IBinder asBinder() {
      return this;
    }

    public static String getDefaultTransactionName(int transactionCode) {
      switch (transactionCode) {
        case 1:
          return "getAllowedPhoneAccounts";
        case 2:
          return "silenceRinger";
        case 3:
          return "endCall";
        case 4:
          return "acceptRingingCall";
        case 5:
          return "acceptRingingCallWithVideoState";
        case 6:
          return "isInCall";
        case 7:
          return "showInCallScreen";
        default:
          return null;
      }
    }

    @Override // android.p009os.Binder
    public String getTransactionName(int transactionCode) {
      return getDefaultTransactionName(transactionCode);
    }

    @Override // android.p009os.Binder
    public boolean onTransact(int code, Parcel data, Parcel reply, int flags)
        throws RemoteException {
      if (code >= 1 && code <= 16777215) {
        data.enforceInterface(ISamsungTelecomService.DESCRIPTOR);
      }
      switch (code) {
        case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
          reply.writeString(ISamsungTelecomService.DESCRIPTOR);
          return true;
        default:
          switch (code) {
            case 1:
              boolean _arg0 = data.readBoolean();
              boolean _arg1 = data.readBoolean();
              String _arg2 = data.readString();
              String _arg3 = data.readString();
              data.enforceNoDataAvail();
              ParceledListSlice<SemPhoneAccount> _result =
                  getAllowedPhoneAccounts(_arg0, _arg1, _arg2, _arg3);
              reply.writeNoException();
              reply.writeTypedObject(_result, 1);
              return true;
            case 2:
              int _arg02 = data.readInt();
              Bundle _arg12 = (Bundle) data.readTypedObject(Bundle.CREATOR);
              String _arg22 = data.readString();
              String _arg32 = data.readString();
              data.enforceNoDataAvail();
              silenceRinger(_arg02, _arg12, _arg22, _arg32);
              reply.writeNoException();
              return true;
            case 3:
              int _arg03 = data.readInt();
              Bundle _arg13 = (Bundle) data.readTypedObject(Bundle.CREATOR);
              String _arg23 = data.readString();
              String _arg33 = data.readString();
              data.enforceNoDataAvail();
              boolean _result2 = endCall(_arg03, _arg13, _arg23, _arg33);
              reply.writeNoException();
              reply.writeBoolean(_result2);
              return true;
            case 4:
              int _arg04 = data.readInt();
              Bundle _arg14 = (Bundle) data.readTypedObject(Bundle.CREATOR);
              String _arg24 = data.readString();
              String _arg34 = data.readString();
              data.enforceNoDataAvail();
              acceptRingingCall(_arg04, _arg14, _arg24, _arg34);
              reply.writeNoException();
              return true;
            case 5:
              int _arg05 = data.readInt();
              int _arg15 = data.readInt();
              Bundle _arg25 = (Bundle) data.readTypedObject(Bundle.CREATOR);
              String _arg35 = data.readString();
              String _arg4 = data.readString();
              data.enforceNoDataAvail();
              acceptRingingCallWithVideoState(_arg05, _arg15, _arg25, _arg35, _arg4);
              reply.writeNoException();
              return true;
            case 6:
              int _arg06 = data.readInt();
              boolean _arg16 = data.readBoolean();
              String _arg26 = data.readString();
              String _arg36 = data.readString();
              data.enforceNoDataAvail();
              boolean _result3 = isInCall(_arg06, _arg16, _arg26, _arg36);
              reply.writeNoException();
              reply.writeBoolean(_result3);
              return true;
            case 7:
              boolean _arg07 = data.readBoolean();
              UserHandle _arg17 = (UserHandle) data.readTypedObject(UserHandle.CREATOR);
              String _arg27 = data.readString();
              String _arg37 = data.readString();
              data.enforceNoDataAvail();
              showInCallScreen(_arg07, _arg17, _arg27, _arg37);
              reply.writeNoException();
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements ISamsungTelecomService {
      private IBinder mRemote;

      Proxy(IBinder remote) {
        this.mRemote = remote;
      }

      @Override // android.p009os.IInterface
      public IBinder asBinder() {
        return this.mRemote;
      }

      public String getInterfaceDescriptor() {
        return ISamsungTelecomService.DESCRIPTOR;
      }

      @Override // com.samsung.android.internal.telecom.ISamsungTelecomService
      public ParceledListSlice<SemPhoneAccount> getAllowedPhoneAccounts(
          boolean includeRegisteredAccounts,
          boolean includeSimSubscriptionAccounts,
          String callingPackage,
          String callingFeatureId)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(ISamsungTelecomService.DESCRIPTOR);
          _data.writeBoolean(includeRegisteredAccounts);
          _data.writeBoolean(includeSimSubscriptionAccounts);
          _data.writeString(callingPackage);
          _data.writeString(callingFeatureId);
          this.mRemote.transact(1, _data, _reply, 0);
          _reply.readException();
          ParceledListSlice<SemPhoneAccount> _result =
              (ParceledListSlice) _reply.readTypedObject(ParceledListSlice.CREATOR);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.internal.telecom.ISamsungTelecomService
      public void silenceRinger(
          int keyCode, Bundle reason, String callingPackage, String callingFeatureId)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(ISamsungTelecomService.DESCRIPTOR);
          _data.writeInt(keyCode);
          _data.writeTypedObject(reason, 0);
          _data.writeString(callingPackage);
          _data.writeString(callingFeatureId);
          this.mRemote.transact(2, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.internal.telecom.ISamsungTelecomService
      public boolean endCall(
          int keyCode, Bundle reason, String callingPackage, String callingFeatureId)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(ISamsungTelecomService.DESCRIPTOR);
          _data.writeInt(keyCode);
          _data.writeTypedObject(reason, 0);
          _data.writeString(callingPackage);
          _data.writeString(callingFeatureId);
          this.mRemote.transact(3, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.internal.telecom.ISamsungTelecomService
      public void acceptRingingCall(
          int keyCode, Bundle reason, String callingPackage, String callingFeatureId)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(ISamsungTelecomService.DESCRIPTOR);
          _data.writeInt(keyCode);
          _data.writeTypedObject(reason, 0);
          _data.writeString(callingPackage);
          _data.writeString(callingFeatureId);
          this.mRemote.transact(4, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.internal.telecom.ISamsungTelecomService
      public void acceptRingingCallWithVideoState(
          int videoState,
          int keyCode,
          Bundle reason,
          String callingPackage,
          String callingFeatureId)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(ISamsungTelecomService.DESCRIPTOR);
          _data.writeInt(videoState);
          _data.writeInt(keyCode);
          _data.writeTypedObject(reason, 0);
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
      public boolean isInCall(
          int callFilter,
          boolean includeExternalCall,
          String callingPackage,
          String callingFeatureId)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(ISamsungTelecomService.DESCRIPTOR);
          _data.writeInt(callFilter);
          _data.writeBoolean(includeExternalCall);
          _data.writeString(callingPackage);
          _data.writeString(callingFeatureId);
          this.mRemote.transact(6, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.internal.telecom.ISamsungTelecomService
      public void showInCallScreen(
          boolean showDialpad,
          UserHandle callingUser,
          String callingPackage,
          String callingFeatureId)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(ISamsungTelecomService.DESCRIPTOR);
          _data.writeBoolean(showDialpad);
          _data.writeTypedObject(callingUser, 0);
          _data.writeString(callingPackage);
          _data.writeString(callingFeatureId);
          this.mRemote.transact(7, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }
    }

    @Override // android.p009os.Binder
    public int getMaxTransactionId() {
      return 6;
    }
  }
}
