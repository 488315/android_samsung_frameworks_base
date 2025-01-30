package com.samsung.android.desktopmode;

import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;

/* loaded from: classes5.dex */
public interface IDesktopModeBlocker extends IInterface {
  public static final String DESCRIPTOR = "com.samsung.android.desktopmode.IDesktopModeBlocker";

  String onBlocked() throws RemoteException;

  public static class Default implements IDesktopModeBlocker {
    @Override // com.samsung.android.desktopmode.IDesktopModeBlocker
    public String onBlocked() throws RemoteException {
      return null;
    }

    @Override // android.p009os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements IDesktopModeBlocker {
    static final int TRANSACTION_onBlocked = 1;

    public Stub() {
      attachInterface(this, IDesktopModeBlocker.DESCRIPTOR);
    }

    public static IDesktopModeBlocker asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(IDesktopModeBlocker.DESCRIPTOR);
      if (iin != null && (iin instanceof IDesktopModeBlocker)) {
        return (IDesktopModeBlocker) iin;
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
          return "onBlocked";
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
        data.enforceInterface(IDesktopModeBlocker.DESCRIPTOR);
      }
      switch (code) {
        case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
          reply.writeString(IDesktopModeBlocker.DESCRIPTOR);
          return true;
        default:
          switch (code) {
            case 1:
              String _result = onBlocked();
              reply.writeNoException();
              reply.writeString(_result);
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements IDesktopModeBlocker {
      private IBinder mRemote;

      Proxy(IBinder remote) {
        this.mRemote = remote;
      }

      @Override // android.p009os.IInterface
      public IBinder asBinder() {
        return this.mRemote;
      }

      public String getInterfaceDescriptor() {
        return IDesktopModeBlocker.DESCRIPTOR;
      }

      @Override // com.samsung.android.desktopmode.IDesktopModeBlocker
      public String onBlocked() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IDesktopModeBlocker.DESCRIPTOR);
          this.mRemote.transact(1, _data, _reply, 0);
          _reply.readException();
          String _result = _reply.readString();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }
    }

    @Override // android.p009os.Binder
    public int getMaxTransactionId() {
      return 0;
    }
  }
}
