package com.samsung.android.ims;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes5.dex */
public interface SemAutoConfigListener extends IInterface {
  public static final String DESCRIPTOR = "com.samsung.android.ims.SemAutoConfigListener";

  void onAutoConfigurationCompleted(boolean z) throws RemoteException;

  void onIidTokenNeeded() throws RemoteException;

  void onMsisdnNumberNeeded() throws RemoteException;

  void onVerificationCodeNeeded() throws RemoteException;

  public static class Default implements SemAutoConfigListener {
    @Override // com.samsung.android.ims.SemAutoConfigListener
    public void onVerificationCodeNeeded() throws RemoteException {}

    @Override // com.samsung.android.ims.SemAutoConfigListener
    public void onMsisdnNumberNeeded() throws RemoteException {}

    @Override // com.samsung.android.ims.SemAutoConfigListener
    public void onIidTokenNeeded() throws RemoteException {}

    @Override // com.samsung.android.ims.SemAutoConfigListener
    public void onAutoConfigurationCompleted(boolean result) throws RemoteException {}

    @Override // android.os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements SemAutoConfigListener {
    static final int TRANSACTION_onAutoConfigurationCompleted = 4;
    static final int TRANSACTION_onIidTokenNeeded = 3;
    static final int TRANSACTION_onMsisdnNumberNeeded = 2;
    static final int TRANSACTION_onVerificationCodeNeeded = 1;

    public Stub() {
      attachInterface(this, SemAutoConfigListener.DESCRIPTOR);
    }

    public static SemAutoConfigListener asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(SemAutoConfigListener.DESCRIPTOR);
      if (iin != null && (iin instanceof SemAutoConfigListener)) {
        return (SemAutoConfigListener) iin;
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
          return "onVerificationCodeNeeded";
        case 2:
          return "onMsisdnNumberNeeded";
        case 3:
          return "onIidTokenNeeded";
        case 4:
          return "onAutoConfigurationCompleted";
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
        data.enforceInterface(SemAutoConfigListener.DESCRIPTOR);
      }
      switch (code) {
        case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
          reply.writeString(SemAutoConfigListener.DESCRIPTOR);
          return true;
        default:
          switch (code) {
            case 1:
              onVerificationCodeNeeded();
              reply.writeNoException();
              return true;
            case 2:
              onMsisdnNumberNeeded();
              reply.writeNoException();
              return true;
            case 3:
              onIidTokenNeeded();
              reply.writeNoException();
              return true;
            case 4:
              boolean _arg0 = data.readBoolean();
              data.enforceNoDataAvail();
              onAutoConfigurationCompleted(_arg0);
              reply.writeNoException();
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements SemAutoConfigListener {
      private IBinder mRemote;

      Proxy(IBinder remote) {
        this.mRemote = remote;
      }

      @Override // android.os.IInterface
      public IBinder asBinder() {
        return this.mRemote;
      }

      public String getInterfaceDescriptor() {
        return SemAutoConfigListener.DESCRIPTOR;
      }

      @Override // com.samsung.android.ims.SemAutoConfigListener
      public void onVerificationCodeNeeded() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(SemAutoConfigListener.DESCRIPTOR);
          this.mRemote.transact(1, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.ims.SemAutoConfigListener
      public void onMsisdnNumberNeeded() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(SemAutoConfigListener.DESCRIPTOR);
          this.mRemote.transact(2, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.ims.SemAutoConfigListener
      public void onIidTokenNeeded() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(SemAutoConfigListener.DESCRIPTOR);
          this.mRemote.transact(3, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.ims.SemAutoConfigListener
      public void onAutoConfigurationCompleted(boolean result) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(SemAutoConfigListener.DESCRIPTOR);
          _data.writeBoolean(result);
          this.mRemote.transact(4, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }
    }

    @Override // android.os.Binder
    public int getMaxTransactionId() {
      return 3;
    }
  }
}
