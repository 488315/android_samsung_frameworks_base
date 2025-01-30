package com.samsung.android.media.codec;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes5.dex */
public interface IVideoTranscodingServiceCallback extends IInterface {
  public static final String DESCRIPTOR =
      "com.samsung.android.media.codec.IVideoTranscodingServiceCallback";

  void onCompleted() throws RemoteException;

  void onError() throws RemoteException;

  void onProgressChanged(int i) throws RemoteException;

  void onReady() throws RemoteException;

  void onStarted() throws RemoteException;

  public static class Default implements IVideoTranscodingServiceCallback {
    @Override // com.samsung.android.media.codec.IVideoTranscodingServiceCallback
    public void onReady() throws RemoteException {}

    @Override // com.samsung.android.media.codec.IVideoTranscodingServiceCallback
    public void onStarted() throws RemoteException {}

    @Override // com.samsung.android.media.codec.IVideoTranscodingServiceCallback
    public void onProgressChanged(int i) throws RemoteException {}

    @Override // com.samsung.android.media.codec.IVideoTranscodingServiceCallback
    public void onCompleted() throws RemoteException {}

    @Override // com.samsung.android.media.codec.IVideoTranscodingServiceCallback
    public void onError() throws RemoteException {}

    @Override // android.os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements IVideoTranscodingServiceCallback {
    static final int TRANSACTION_onCompleted = 4;
    static final int TRANSACTION_onError = 5;
    static final int TRANSACTION_onProgressChanged = 3;
    static final int TRANSACTION_onReady = 1;
    static final int TRANSACTION_onStarted = 2;

    public Stub() {
      attachInterface(this, IVideoTranscodingServiceCallback.DESCRIPTOR);
    }

    public static IVideoTranscodingServiceCallback asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(IVideoTranscodingServiceCallback.DESCRIPTOR);
      if (iin != null && (iin instanceof IVideoTranscodingServiceCallback)) {
        return (IVideoTranscodingServiceCallback) iin;
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
          return "onReady";
        case 2:
          return "onStarted";
        case 3:
          return "onProgressChanged";
        case 4:
          return "onCompleted";
        case 5:
          return "onError";
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
        data.enforceInterface(IVideoTranscodingServiceCallback.DESCRIPTOR);
      }
      switch (code) {
        case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
          reply.writeString(IVideoTranscodingServiceCallback.DESCRIPTOR);
          return true;
        default:
          switch (code) {
            case 1:
              onReady();
              reply.writeNoException();
              return true;
            case 2:
              onStarted();
              reply.writeNoException();
              return true;
            case 3:
              int _arg0 = data.readInt();
              data.enforceNoDataAvail();
              onProgressChanged(_arg0);
              reply.writeNoException();
              return true;
            case 4:
              onCompleted();
              reply.writeNoException();
              return true;
            case 5:
              onError();
              reply.writeNoException();
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements IVideoTranscodingServiceCallback {
      private IBinder mRemote;

      Proxy(IBinder remote) {
        this.mRemote = remote;
      }

      @Override // android.os.IInterface
      public IBinder asBinder() {
        return this.mRemote;
      }

      public String getInterfaceDescriptor() {
        return IVideoTranscodingServiceCallback.DESCRIPTOR;
      }

      @Override // com.samsung.android.media.codec.IVideoTranscodingServiceCallback
      public void onReady() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IVideoTranscodingServiceCallback.DESCRIPTOR);
          this.mRemote.transact(1, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.media.codec.IVideoTranscodingServiceCallback
      public void onStarted() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IVideoTranscodingServiceCallback.DESCRIPTOR);
          this.mRemote.transact(2, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.media.codec.IVideoTranscodingServiceCallback
      public void onProgressChanged(int i) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IVideoTranscodingServiceCallback.DESCRIPTOR);
          _data.writeInt(i);
          this.mRemote.transact(3, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.media.codec.IVideoTranscodingServiceCallback
      public void onCompleted() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IVideoTranscodingServiceCallback.DESCRIPTOR);
          this.mRemote.transact(4, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.media.codec.IVideoTranscodingServiceCallback
      public void onError() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IVideoTranscodingServiceCallback.DESCRIPTOR);
          this.mRemote.transact(5, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }
    }

    @Override // android.os.Binder
    public int getMaxTransactionId() {
      return 4;
    }
  }
}
