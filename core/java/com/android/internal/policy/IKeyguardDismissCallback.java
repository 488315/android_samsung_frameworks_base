package com.android.internal.policy;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes5.dex */
public interface IKeyguardDismissCallback extends IInterface {
  void onDismissCancelled() throws RemoteException;

  void onDismissError() throws RemoteException;

  void onDismissSucceeded() throws RemoteException;

  public static class Default implements IKeyguardDismissCallback {
    @Override // com.android.internal.policy.IKeyguardDismissCallback
    public void onDismissError() throws RemoteException {}

    @Override // com.android.internal.policy.IKeyguardDismissCallback
    public void onDismissSucceeded() throws RemoteException {}

    @Override // com.android.internal.policy.IKeyguardDismissCallback
    public void onDismissCancelled() throws RemoteException {}

    @Override // android.os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements IKeyguardDismissCallback {
    public static final String DESCRIPTOR = "com.android.internal.policy.IKeyguardDismissCallback";
    static final int TRANSACTION_onDismissCancelled = 3;
    static final int TRANSACTION_onDismissError = 1;
    static final int TRANSACTION_onDismissSucceeded = 2;

    public Stub() {
      attachInterface(this, DESCRIPTOR);
    }

    public static IKeyguardDismissCallback asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
      if (iin != null && (iin instanceof IKeyguardDismissCallback)) {
        return (IKeyguardDismissCallback) iin;
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
          return "onDismissError";
        case 2:
          return "onDismissSucceeded";
        case 3:
          return "onDismissCancelled";
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
        data.enforceInterface(DESCRIPTOR);
      }
      switch (code) {
        case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
          reply.writeString(DESCRIPTOR);
          return true;
        default:
          switch (code) {
            case 1:
              onDismissError();
              return true;
            case 2:
              onDismissSucceeded();
              return true;
            case 3:
              onDismissCancelled();
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements IKeyguardDismissCallback {
      private IBinder mRemote;

      Proxy(IBinder remote) {
        this.mRemote = remote;
      }

      @Override // android.os.IInterface
      public IBinder asBinder() {
        return this.mRemote;
      }

      public String getInterfaceDescriptor() {
        return Stub.DESCRIPTOR;
      }

      @Override // com.android.internal.policy.IKeyguardDismissCallback
      public void onDismissError() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          this.mRemote.transact(1, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // com.android.internal.policy.IKeyguardDismissCallback
      public void onDismissSucceeded() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          this.mRemote.transact(2, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // com.android.internal.policy.IKeyguardDismissCallback
      public void onDismissCancelled() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          this.mRemote.transact(3, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }
    }

    @Override // android.os.Binder
    public int getMaxTransactionId() {
      return 2;
    }
  }
}
