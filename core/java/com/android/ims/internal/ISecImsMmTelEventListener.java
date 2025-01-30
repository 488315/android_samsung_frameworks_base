package com.android.ims.internal;

import android.p009os.Binder;
import android.p009os.Bundle;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;

/* loaded from: classes4.dex */
public interface ISecImsMmTelEventListener extends IInterface {
  public static final String DESCRIPTOR = "com.android.ims.internal.ISecImsMmTelEventListener";

  void onCdpnInfo(String str, int i) throws RemoteException;

  void onIncomingCall(int i, Bundle bundle) throws RemoteException;

  public static class Default implements ISecImsMmTelEventListener {
    @Override // com.android.ims.internal.ISecImsMmTelEventListener
    public void onIncomingCall(int callId, Bundle extras) throws RemoteException {}

    @Override // com.android.ims.internal.ISecImsMmTelEventListener
    public void onCdpnInfo(String calledPartyNumber, int timeout) throws RemoteException {}

    @Override // android.p009os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements ISecImsMmTelEventListener {
    static final int TRANSACTION_onCdpnInfo = 2;
    static final int TRANSACTION_onIncomingCall = 1;

    public Stub() {
      attachInterface(this, ISecImsMmTelEventListener.DESCRIPTOR);
    }

    public static ISecImsMmTelEventListener asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(ISecImsMmTelEventListener.DESCRIPTOR);
      if (iin != null && (iin instanceof ISecImsMmTelEventListener)) {
        return (ISecImsMmTelEventListener) iin;
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
          return "onIncomingCall";
        case 2:
          return "onCdpnInfo";
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
        data.enforceInterface(ISecImsMmTelEventListener.DESCRIPTOR);
      }
      switch (code) {
        case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
          reply.writeString(ISecImsMmTelEventListener.DESCRIPTOR);
          return true;
        default:
          switch (code) {
            case 1:
              int _arg0 = data.readInt();
              Bundle _arg1 = (Bundle) data.readTypedObject(Bundle.CREATOR);
              data.enforceNoDataAvail();
              onIncomingCall(_arg0, _arg1);
              reply.writeNoException();
              return true;
            case 2:
              String _arg02 = data.readString();
              int _arg12 = data.readInt();
              data.enforceNoDataAvail();
              onCdpnInfo(_arg02, _arg12);
              reply.writeNoException();
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements ISecImsMmTelEventListener {
      private IBinder mRemote;

      Proxy(IBinder remote) {
        this.mRemote = remote;
      }

      @Override // android.p009os.IInterface
      public IBinder asBinder() {
        return this.mRemote;
      }

      public String getInterfaceDescriptor() {
        return ISecImsMmTelEventListener.DESCRIPTOR;
      }

      @Override // com.android.ims.internal.ISecImsMmTelEventListener
      public void onIncomingCall(int callId, Bundle extras) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(ISecImsMmTelEventListener.DESCRIPTOR);
          _data.writeInt(callId);
          _data.writeTypedObject(extras, 0);
          this.mRemote.transact(1, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.android.ims.internal.ISecImsMmTelEventListener
      public void onCdpnInfo(String calledPartyNumber, int timeout) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(ISecImsMmTelEventListener.DESCRIPTOR);
          _data.writeString(calledPartyNumber);
          _data.writeInt(timeout);
          this.mRemote.transact(2, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }
    }

    @Override // android.p009os.Binder
    public int getMaxTransactionId() {
      return 1;
    }
  }
}
