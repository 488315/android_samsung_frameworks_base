package com.samsung.android.cover;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes5.dex */
public interface INfcLedCoverTouchListenerCallback extends IInterface {
  public static final String DESCRIPTOR =
      "com.samsung.android.cover.INfcLedCoverTouchListenerCallback";

  void onCoverTapLeft() throws RemoteException;

  void onCoverTapMid() throws RemoteException;

  void onCoverTapRight() throws RemoteException;

  void onCoverTouchAccept() throws RemoteException;

  void onCoverTouchReject() throws RemoteException;

  void onSystemCoverEvent(int i, Bundle bundle) throws RemoteException;

  public static class Default implements INfcLedCoverTouchListenerCallback {
    @Override // com.samsung.android.cover.INfcLedCoverTouchListenerCallback
    public void onCoverTouchAccept() throws RemoteException {}

    @Override // com.samsung.android.cover.INfcLedCoverTouchListenerCallback
    public void onCoverTouchReject() throws RemoteException {}

    @Override // com.samsung.android.cover.INfcLedCoverTouchListenerCallback
    public void onCoverTapLeft() throws RemoteException {}

    @Override // com.samsung.android.cover.INfcLedCoverTouchListenerCallback
    public void onCoverTapMid() throws RemoteException {}

    @Override // com.samsung.android.cover.INfcLedCoverTouchListenerCallback
    public void onCoverTapRight() throws RemoteException {}

    @Override // com.samsung.android.cover.INfcLedCoverTouchListenerCallback
    public void onSystemCoverEvent(int event, Bundle args) throws RemoteException {}

    @Override // android.os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements INfcLedCoverTouchListenerCallback {
    static final int TRANSACTION_onCoverTapLeft = 3;
    static final int TRANSACTION_onCoverTapMid = 4;
    static final int TRANSACTION_onCoverTapRight = 5;
    static final int TRANSACTION_onCoverTouchAccept = 1;
    static final int TRANSACTION_onCoverTouchReject = 2;
    static final int TRANSACTION_onSystemCoverEvent = 6;

    public Stub() {
      attachInterface(this, INfcLedCoverTouchListenerCallback.DESCRIPTOR);
    }

    public static INfcLedCoverTouchListenerCallback asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(INfcLedCoverTouchListenerCallback.DESCRIPTOR);
      if (iin != null && (iin instanceof INfcLedCoverTouchListenerCallback)) {
        return (INfcLedCoverTouchListenerCallback) iin;
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
          return "onCoverTouchAccept";
        case 2:
          return "onCoverTouchReject";
        case 3:
          return "onCoverTapLeft";
        case 4:
          return "onCoverTapMid";
        case 5:
          return "onCoverTapRight";
        case 6:
          return "onSystemCoverEvent";
        default:
          return null;
      }
    }

    @Override // android.os.Binder
    public String getTransactionName(int transactionCode) {
      return getDefaultTransactionName(transactionCode);
    }

    @Override // android.os.Binder
    public boolean onTransact(int code, Parcel data, Parcel reply, int flags)
        throws RemoteException {
      if (code >= 1 && code <= 16777215) {
        data.enforceInterface(INfcLedCoverTouchListenerCallback.DESCRIPTOR);
      }
      switch (code) {
        case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
          reply.writeString(INfcLedCoverTouchListenerCallback.DESCRIPTOR);
          return true;
        default:
          switch (code) {
            case 1:
              onCoverTouchAccept();
              reply.writeNoException();
              return true;
            case 2:
              onCoverTouchReject();
              reply.writeNoException();
              return true;
            case 3:
              onCoverTapLeft();
              reply.writeNoException();
              return true;
            case 4:
              onCoverTapMid();
              reply.writeNoException();
              return true;
            case 5:
              onCoverTapRight();
              reply.writeNoException();
              return true;
            case 6:
              int _arg0 = data.readInt();
              Bundle _arg1 = (Bundle) data.readTypedObject(Bundle.CREATOR);
              data.enforceNoDataAvail();
              onSystemCoverEvent(_arg0, _arg1);
              reply.writeNoException();
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements INfcLedCoverTouchListenerCallback {
      private IBinder mRemote;

      Proxy(IBinder remote) {
        this.mRemote = remote;
      }

      @Override // android.os.IInterface
      public IBinder asBinder() {
        return this.mRemote;
      }

      public String getInterfaceDescriptor() {
        return INfcLedCoverTouchListenerCallback.DESCRIPTOR;
      }

      @Override // com.samsung.android.cover.INfcLedCoverTouchListenerCallback
      public void onCoverTouchAccept() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(INfcLedCoverTouchListenerCallback.DESCRIPTOR);
          this.mRemote.transact(1, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.cover.INfcLedCoverTouchListenerCallback
      public void onCoverTouchReject() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(INfcLedCoverTouchListenerCallback.DESCRIPTOR);
          this.mRemote.transact(2, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.cover.INfcLedCoverTouchListenerCallback
      public void onCoverTapLeft() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(INfcLedCoverTouchListenerCallback.DESCRIPTOR);
          this.mRemote.transact(3, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.cover.INfcLedCoverTouchListenerCallback
      public void onCoverTapMid() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(INfcLedCoverTouchListenerCallback.DESCRIPTOR);
          this.mRemote.transact(4, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.cover.INfcLedCoverTouchListenerCallback
      public void onCoverTapRight() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(INfcLedCoverTouchListenerCallback.DESCRIPTOR);
          this.mRemote.transact(5, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.cover.INfcLedCoverTouchListenerCallback
      public void onSystemCoverEvent(int event, Bundle args) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(INfcLedCoverTouchListenerCallback.DESCRIPTOR);
          _data.writeInt(event);
          _data.writeTypedObject(args, 0);
          this.mRemote.transact(6, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }
    }

    @Override // android.os.Binder
    public int getMaxTransactionId() {
      return 5;
    }
  }
}
