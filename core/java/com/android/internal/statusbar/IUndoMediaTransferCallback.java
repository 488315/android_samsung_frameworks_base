package com.android.internal.statusbar;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes5.dex */
public interface IUndoMediaTransferCallback extends IInterface {
  public static final String DESCRIPTOR =
      "com.android.internal.statusbar.IUndoMediaTransferCallback";

  void onUndoTriggered() throws RemoteException;

  public static class Default implements IUndoMediaTransferCallback {
    @Override // com.android.internal.statusbar.IUndoMediaTransferCallback
    public void onUndoTriggered() throws RemoteException {}

    @Override // android.os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements IUndoMediaTransferCallback {
    static final int TRANSACTION_onUndoTriggered = 1;

    public Stub() {
      attachInterface(this, IUndoMediaTransferCallback.DESCRIPTOR);
    }

    public static IUndoMediaTransferCallback asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(IUndoMediaTransferCallback.DESCRIPTOR);
      if (iin != null && (iin instanceof IUndoMediaTransferCallback)) {
        return (IUndoMediaTransferCallback) iin;
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
          return "onUndoTriggered";
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
        data.enforceInterface(IUndoMediaTransferCallback.DESCRIPTOR);
      }
      switch (code) {
        case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
          reply.writeString(IUndoMediaTransferCallback.DESCRIPTOR);
          return true;
        default:
          switch (code) {
            case 1:
              onUndoTriggered();
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements IUndoMediaTransferCallback {
      private IBinder mRemote;

      Proxy(IBinder remote) {
        this.mRemote = remote;
      }

      @Override // android.os.IInterface
      public IBinder asBinder() {
        return this.mRemote;
      }

      public String getInterfaceDescriptor() {
        return IUndoMediaTransferCallback.DESCRIPTOR;
      }

      @Override // com.android.internal.statusbar.IUndoMediaTransferCallback
      public void onUndoTriggered() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(IUndoMediaTransferCallback.DESCRIPTOR);
          this.mRemote.transact(1, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }
    }

    @Override // android.os.Binder
    public int getMaxTransactionId() {
      return 0;
    }
  }
}
