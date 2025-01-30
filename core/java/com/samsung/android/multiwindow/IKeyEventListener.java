package com.samsung.android.multiwindow;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.view.KeyEvent;

/* loaded from: classes5.dex */
public interface IKeyEventListener extends IInterface {
  public static final String DESCRIPTOR = "com.samsung.android.multiwindow.IKeyEventListener";

  void sendShortcutKeyWithFocusedTask(int i, KeyEvent keyEvent) throws RemoteException;

  public static class Default implements IKeyEventListener {
    @Override // com.samsung.android.multiwindow.IKeyEventListener
    public void sendShortcutKeyWithFocusedTask(int taskId, KeyEvent key) throws RemoteException {}

    @Override // android.os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements IKeyEventListener {
    static final int TRANSACTION_sendShortcutKeyWithFocusedTask = 1;

    public Stub() {
      attachInterface(this, IKeyEventListener.DESCRIPTOR);
    }

    public static IKeyEventListener asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(IKeyEventListener.DESCRIPTOR);
      if (iin != null && (iin instanceof IKeyEventListener)) {
        return (IKeyEventListener) iin;
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
          return "sendShortcutKeyWithFocusedTask";
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
        data.enforceInterface(IKeyEventListener.DESCRIPTOR);
      }
      switch (code) {
        case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
          reply.writeString(IKeyEventListener.DESCRIPTOR);
          return true;
        default:
          switch (code) {
            case 1:
              int _arg0 = data.readInt();
              KeyEvent _arg1 = (KeyEvent) data.readTypedObject(KeyEvent.CREATOR);
              data.enforceNoDataAvail();
              sendShortcutKeyWithFocusedTask(_arg0, _arg1);
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements IKeyEventListener {
      private IBinder mRemote;

      Proxy(IBinder remote) {
        this.mRemote = remote;
      }

      @Override // android.os.IInterface
      public IBinder asBinder() {
        return this.mRemote;
      }

      public String getInterfaceDescriptor() {
        return IKeyEventListener.DESCRIPTOR;
      }

      @Override // com.samsung.android.multiwindow.IKeyEventListener
      public void sendShortcutKeyWithFocusedTask(int taskId, KeyEvent key) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(IKeyEventListener.DESCRIPTOR);
          _data.writeInt(taskId);
          _data.writeTypedObject(key, 0);
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
