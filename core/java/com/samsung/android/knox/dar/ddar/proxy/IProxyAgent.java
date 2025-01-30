package com.samsung.android.knox.dar.ddar.proxy;

import android.p009os.Binder;
import android.p009os.Bundle;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;

/* loaded from: classes5.dex */
public interface IProxyAgent extends IInterface {
  public static final String DESCRIPTOR = "com.samsung.android.knox.dar.ddar.proxy.IProxyAgent";

  String initializeSecureSession(int i, String str, String str2, String str3)
      throws RemoteException;

  void onAgentReconnected() throws RemoteException;

  Bundle onMessage(int i, String str, String str2, Bundle bundle) throws RemoteException;

  boolean terminateSecureSession(int i, String str, String str2) throws RemoteException;

  public static class Default implements IProxyAgent {
    @Override // com.samsung.android.knox.dar.ddar.proxy.IProxyAgent
    public Bundle onMessage(int callingUid, String svcName, String msgName, Bundle args)
        throws RemoteException {
      return null;
    }

    @Override // com.samsung.android.knox.dar.ddar.proxy.IProxyAgent
    public String initializeSecureSession(
        int callingUid, String svcName, String secureClientId, String secureClientPubKey)
        throws RemoteException {
      return null;
    }

    @Override // com.samsung.android.knox.dar.ddar.proxy.IProxyAgent
    public boolean terminateSecureSession(int callingUid, String svcName, String secureClientId)
        throws RemoteException {
      return false;
    }

    @Override // com.samsung.android.knox.dar.ddar.proxy.IProxyAgent
    public void onAgentReconnected() throws RemoteException {}

    @Override // android.p009os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements IProxyAgent {
    static final int TRANSACTION_initializeSecureSession = 2;
    static final int TRANSACTION_onAgentReconnected = 4;
    static final int TRANSACTION_onMessage = 1;
    static final int TRANSACTION_terminateSecureSession = 3;

    public Stub() {
      attachInterface(this, IProxyAgent.DESCRIPTOR);
    }

    public static IProxyAgent asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(IProxyAgent.DESCRIPTOR);
      if (iin != null && (iin instanceof IProxyAgent)) {
        return (IProxyAgent) iin;
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
          return "onMessage";
        case 2:
          return "initializeSecureSession";
        case 3:
          return "terminateSecureSession";
        case 4:
          return "onAgentReconnected";
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
        data.enforceInterface(IProxyAgent.DESCRIPTOR);
      }
      switch (code) {
        case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
          reply.writeString(IProxyAgent.DESCRIPTOR);
          return true;
        default:
          switch (code) {
            case 1:
              int _arg0 = data.readInt();
              String _arg1 = data.readString();
              String _arg2 = data.readString();
              Bundle _arg3 = (Bundle) data.readTypedObject(Bundle.CREATOR);
              data.enforceNoDataAvail();
              Bundle _result = onMessage(_arg0, _arg1, _arg2, _arg3);
              reply.writeNoException();
              reply.writeTypedObject(_result, 1);
              return true;
            case 2:
              int _arg02 = data.readInt();
              String _arg12 = data.readString();
              String _arg22 = data.readString();
              String _arg32 = data.readString();
              data.enforceNoDataAvail();
              String _result2 = initializeSecureSession(_arg02, _arg12, _arg22, _arg32);
              reply.writeNoException();
              reply.writeString(_result2);
              return true;
            case 3:
              int _arg03 = data.readInt();
              String _arg13 = data.readString();
              String _arg23 = data.readString();
              data.enforceNoDataAvail();
              boolean _result3 = terminateSecureSession(_arg03, _arg13, _arg23);
              reply.writeNoException();
              reply.writeBoolean(_result3);
              return true;
            case 4:
              onAgentReconnected();
              reply.writeNoException();
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements IProxyAgent {
      private IBinder mRemote;

      Proxy(IBinder remote) {
        this.mRemote = remote;
      }

      @Override // android.p009os.IInterface
      public IBinder asBinder() {
        return this.mRemote;
      }

      public String getInterfaceDescriptor() {
        return IProxyAgent.DESCRIPTOR;
      }

      @Override // com.samsung.android.knox.dar.ddar.proxy.IProxyAgent
      public Bundle onMessage(int callingUid, String svcName, String msgName, Bundle args)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IProxyAgent.DESCRIPTOR);
          _data.writeInt(callingUid);
          _data.writeString(svcName);
          _data.writeString(msgName);
          _data.writeTypedObject(args, 0);
          this.mRemote.transact(1, _data, _reply, 0);
          _reply.readException();
          Bundle _result = (Bundle) _reply.readTypedObject(Bundle.CREATOR);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.knox.dar.ddar.proxy.IProxyAgent
      public String initializeSecureSession(
          int callingUid, String svcName, String secureClientId, String secureClientPubKey)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IProxyAgent.DESCRIPTOR);
          _data.writeInt(callingUid);
          _data.writeString(svcName);
          _data.writeString(secureClientId);
          _data.writeString(secureClientPubKey);
          this.mRemote.transact(2, _data, _reply, 0);
          _reply.readException();
          String _result = _reply.readString();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.knox.dar.ddar.proxy.IProxyAgent
      public boolean terminateSecureSession(int callingUid, String svcName, String secureClientId)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IProxyAgent.DESCRIPTOR);
          _data.writeInt(callingUid);
          _data.writeString(svcName);
          _data.writeString(secureClientId);
          this.mRemote.transact(3, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.knox.dar.ddar.proxy.IProxyAgent
      public void onAgentReconnected() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IProxyAgent.DESCRIPTOR);
          this.mRemote.transact(4, _data, _reply, 0);
          _reply.readException();
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
