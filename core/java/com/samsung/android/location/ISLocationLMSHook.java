package com.samsung.android.location;

import android.location.IGpsGeofenceHardware;
import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;

/* loaded from: classes5.dex */
public interface ISLocationLMSHook extends IInterface {
  public static final String DESCRIPTOR = "com.samsung.android.location.ISLocationLMSHook";

  IGpsGeofenceHardware getHWGeofence() throws RemoteException;

  boolean isProviderEnabledForUser(String str, int i) throws RemoteException;

  boolean isUidForeground(int i) throws RemoteException;

  void updateRequestInfo(boolean z, int i, int i2, String str, String str2, boolean z2, int i3)
      throws RemoteException;

  public static class Default implements ISLocationLMSHook {
    @Override // com.samsung.android.location.ISLocationLMSHook
    public boolean isProviderEnabledForUser(String provider, int userId) throws RemoteException {
      return false;
    }

    @Override // com.samsung.android.location.ISLocationLMSHook
    public IGpsGeofenceHardware getHWGeofence() throws RemoteException {
      return null;
    }

    @Override // com.samsung.android.location.ISLocationLMSHook
    public void updateRequestInfo(
        boolean isRequest,
        int uid,
        int pid,
        String pkgName,
        String listenerId,
        boolean isSingleshot,
        int callbackType)
        throws RemoteException {}

    @Override // com.samsung.android.location.ISLocationLMSHook
    public boolean isUidForeground(int uid) throws RemoteException {
      return false;
    }

    @Override // android.p009os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements ISLocationLMSHook {
    static final int TRANSACTION_getHWGeofence = 2;
    static final int TRANSACTION_isProviderEnabledForUser = 1;
    static final int TRANSACTION_isUidForeground = 4;
    static final int TRANSACTION_updateRequestInfo = 3;

    public Stub() {
      attachInterface(this, ISLocationLMSHook.DESCRIPTOR);
    }

    public static ISLocationLMSHook asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(ISLocationLMSHook.DESCRIPTOR);
      if (iin != null && (iin instanceof ISLocationLMSHook)) {
        return (ISLocationLMSHook) iin;
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
          return "isProviderEnabledForUser";
        case 2:
          return "getHWGeofence";
        case 3:
          return "updateRequestInfo";
        case 4:
          return "isUidForeground";
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
        data.enforceInterface(ISLocationLMSHook.DESCRIPTOR);
      }
      switch (code) {
        case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
          reply.writeString(ISLocationLMSHook.DESCRIPTOR);
          return true;
        default:
          switch (code) {
            case 1:
              String _arg0 = data.readString();
              int _arg1 = data.readInt();
              data.enforceNoDataAvail();
              boolean _result = isProviderEnabledForUser(_arg0, _arg1);
              reply.writeNoException();
              reply.writeBoolean(_result);
              return true;
            case 2:
              IGpsGeofenceHardware _result2 = getHWGeofence();
              reply.writeNoException();
              reply.writeStrongInterface(_result2);
              return true;
            case 3:
              boolean _arg02 = data.readBoolean();
              int _arg12 = data.readInt();
              int _arg2 = data.readInt();
              String _arg3 = data.readString();
              String _arg4 = data.readString();
              boolean _arg5 = data.readBoolean();
              int _arg6 = data.readInt();
              data.enforceNoDataAvail();
              updateRequestInfo(_arg02, _arg12, _arg2, _arg3, _arg4, _arg5, _arg6);
              reply.writeNoException();
              return true;
            case 4:
              int _arg03 = data.readInt();
              data.enforceNoDataAvail();
              boolean _result3 = isUidForeground(_arg03);
              reply.writeNoException();
              reply.writeBoolean(_result3);
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements ISLocationLMSHook {
      private IBinder mRemote;

      Proxy(IBinder remote) {
        this.mRemote = remote;
      }

      @Override // android.p009os.IInterface
      public IBinder asBinder() {
        return this.mRemote;
      }

      public String getInterfaceDescriptor() {
        return ISLocationLMSHook.DESCRIPTOR;
      }

      @Override // com.samsung.android.location.ISLocationLMSHook
      public boolean isProviderEnabledForUser(String provider, int userId) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(ISLocationLMSHook.DESCRIPTOR);
          _data.writeString(provider);
          _data.writeInt(userId);
          this.mRemote.transact(1, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.location.ISLocationLMSHook
      public IGpsGeofenceHardware getHWGeofence() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(ISLocationLMSHook.DESCRIPTOR);
          this.mRemote.transact(2, _data, _reply, 0);
          _reply.readException();
          IGpsGeofenceHardware _result =
              IGpsGeofenceHardware.Stub.asInterface(_reply.readStrongBinder());
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.location.ISLocationLMSHook
      public void updateRequestInfo(
          boolean isRequest,
          int uid,
          int pid,
          String pkgName,
          String listenerId,
          boolean isSingleshot,
          int callbackType)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(ISLocationLMSHook.DESCRIPTOR);
          _data.writeBoolean(isRequest);
          _data.writeInt(uid);
          _data.writeInt(pid);
          _data.writeString(pkgName);
          _data.writeString(listenerId);
          _data.writeBoolean(isSingleshot);
          _data.writeInt(callbackType);
          this.mRemote.transact(3, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.location.ISLocationLMSHook
      public boolean isUidForeground(int uid) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(ISLocationLMSHook.DESCRIPTOR);
          _data.writeInt(uid);
          this.mRemote.transact(4, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }
    }

    @Override // android.p009os.Binder
    public int getMaxTransactionId() {
      return 3;
    }
  }
}
