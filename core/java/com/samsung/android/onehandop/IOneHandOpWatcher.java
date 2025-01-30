package com.samsung.android.onehandop;

import android.Manifest;
import android.app.ActivityThread;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.PermissionEnforcer;
import android.os.RemoteException;

/* loaded from: classes5.dex */
public interface IOneHandOpWatcher extends IInterface {
  public static final String DESCRIPTOR = "com.samsung.android.onehandop.IOneHandOpWatcher";

  void onInputFilterChanged() throws RemoteException;

  void onMagnificationSpecChanged() throws RemoteException;

  public static class Default implements IOneHandOpWatcher {
    @Override // com.samsung.android.onehandop.IOneHandOpWatcher
    public void onMagnificationSpecChanged() throws RemoteException {}

    @Override // com.samsung.android.onehandop.IOneHandOpWatcher
    public void onInputFilterChanged() throws RemoteException {}

    @Override // android.os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements IOneHandOpWatcher {
    static final int TRANSACTION_onInputFilterChanged = 2;
    static final int TRANSACTION_onMagnificationSpecChanged = 1;
    private final PermissionEnforcer mEnforcer;

    public Stub(PermissionEnforcer enforcer) {
      attachInterface(this, IOneHandOpWatcher.DESCRIPTOR);
      if (enforcer == null) {
        throw new IllegalArgumentException("enforcer cannot be null");
      }
      this.mEnforcer = enforcer;
    }

    @Deprecated
    public Stub() {
      this(
          PermissionEnforcer.fromContext(
              ActivityThread.currentActivityThread().getSystemContext()));
    }

    public static IOneHandOpWatcher asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(IOneHandOpWatcher.DESCRIPTOR);
      if (iin != null && (iin instanceof IOneHandOpWatcher)) {
        return (IOneHandOpWatcher) iin;
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
          return "onMagnificationSpecChanged";
        case 2:
          return "onInputFilterChanged";
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
        data.enforceInterface(IOneHandOpWatcher.DESCRIPTOR);
      }
      switch (code) {
        case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
          reply.writeString(IOneHandOpWatcher.DESCRIPTOR);
          return true;
        default:
          switch (code) {
            case 1:
              onMagnificationSpecChanged();
              return true;
            case 2:
              onInputFilterChanged();
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements IOneHandOpWatcher {
      private IBinder mRemote;

      Proxy(IBinder remote) {
        this.mRemote = remote;
      }

      @Override // android.os.IInterface
      public IBinder asBinder() {
        return this.mRemote;
      }

      public String getInterfaceDescriptor() {
        return IOneHandOpWatcher.DESCRIPTOR;
      }

      @Override // com.samsung.android.onehandop.IOneHandOpWatcher
      public void onMagnificationSpecChanged() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(IOneHandOpWatcher.DESCRIPTOR);
          this.mRemote.transact(1, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // com.samsung.android.onehandop.IOneHandOpWatcher
      public void onInputFilterChanged() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(IOneHandOpWatcher.DESCRIPTOR);
          this.mRemote.transact(2, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }
    }

    protected void onMagnificationSpecChanged_enforcePermission() throws SecurityException {
      this.mEnforcer.enforcePermission(
          Manifest.permission.WRITE_SECURE_SETTINGS, getCallingPid(), getCallingUid());
    }

    protected void onInputFilterChanged_enforcePermission() throws SecurityException {
      this.mEnforcer.enforcePermission(
          Manifest.permission.WRITE_SECURE_SETTINGS, getCallingPid(), getCallingUid());
    }

    @Override // android.os.Binder
    public int getMaxTransactionId() {
      return 1;
    }
  }
}
