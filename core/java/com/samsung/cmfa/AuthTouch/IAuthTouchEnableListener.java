package com.samsung.cmfa.AuthTouch;

import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;

/* loaded from: classes6.dex */
public interface IAuthTouchEnableListener extends IInterface {
  public static final String DESCRIPTOR = "com.samsung.cmfa.AuthTouch.IAuthTouchEnableListener";

  void notifyTouchEventEnabled(boolean z, boolean z2) throws RemoteException;

  public static class Default implements IAuthTouchEnableListener {
    @Override // com.samsung.cmfa.AuthTouch.IAuthTouchEnableListener
    public void notifyTouchEventEnabled(boolean enable, boolean debugMode) throws RemoteException {}

    @Override // android.p009os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements IAuthTouchEnableListener {
    static final int TRANSACTION_notifyTouchEventEnabled = 1;

    public Stub() {
      attachInterface(this, IAuthTouchEnableListener.DESCRIPTOR);
    }

    public static IAuthTouchEnableListener asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(IAuthTouchEnableListener.DESCRIPTOR);
      if (iin != null && (iin instanceof IAuthTouchEnableListener)) {
        return (IAuthTouchEnableListener) iin;
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
          return "notifyTouchEventEnabled";
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
        data.enforceInterface(IAuthTouchEnableListener.DESCRIPTOR);
      }
      switch (code) {
        case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
          reply.writeString(IAuthTouchEnableListener.DESCRIPTOR);
          return true;
        default:
          switch (code) {
            case 1:
              boolean _arg0 = data.readBoolean();
              boolean _arg1 = data.readBoolean();
              data.enforceNoDataAvail();
              notifyTouchEventEnabled(_arg0, _arg1);
              reply.writeNoException();
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements IAuthTouchEnableListener {
      private IBinder mRemote;

      Proxy(IBinder remote) {
        this.mRemote = remote;
      }

      @Override // android.p009os.IInterface
      public IBinder asBinder() {
        return this.mRemote;
      }

      public String getInterfaceDescriptor() {
        return IAuthTouchEnableListener.DESCRIPTOR;
      }

      @Override // com.samsung.cmfa.AuthTouch.IAuthTouchEnableListener
      public void notifyTouchEventEnabled(boolean enable, boolean debugMode)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IAuthTouchEnableListener.DESCRIPTOR);
          _data.writeBoolean(enable);
          _data.writeBoolean(debugMode);
          this.mRemote.transact(1, _data, _reply, 0);
          _reply.readException();
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
