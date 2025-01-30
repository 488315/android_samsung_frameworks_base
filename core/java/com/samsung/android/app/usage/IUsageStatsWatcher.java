package com.samsung.android.app.usage;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes5.dex */
public interface IUsageStatsWatcher extends IInterface {
  public static final String DESCRIPTOR = "com.samsung.android.app.usage.IUsageStatsWatcher";

  void notePauseComponent(ComponentName componentName, Intent intent, int i, int i2)
      throws RemoteException;

  void noteResumeComponent(ComponentName componentName, Intent intent, int i, int i2)
      throws RemoteException;

  void noteStopComponent(ComponentName componentName, Intent intent, int i, int i2)
      throws RemoteException;

  public static class Default implements IUsageStatsWatcher {
    @Override // com.samsung.android.app.usage.IUsageStatsWatcher
    public void noteResumeComponent(
        ComponentName resumeComponent, Intent intent, int instanceId, int userId)
        throws RemoteException {}

    @Override // com.samsung.android.app.usage.IUsageStatsWatcher
    public void notePauseComponent(
        ComponentName pauseComponent, Intent intent, int instanceId, int userId)
        throws RemoteException {}

    @Override // com.samsung.android.app.usage.IUsageStatsWatcher
    public void noteStopComponent(
        ComponentName stopComponent, Intent intent, int instanceId, int userId)
        throws RemoteException {}

    @Override // android.os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements IUsageStatsWatcher {
    static final int TRANSACTION_notePauseComponent = 2;
    static final int TRANSACTION_noteResumeComponent = 1;
    static final int TRANSACTION_noteStopComponent = 3;

    public Stub() {
      attachInterface(this, IUsageStatsWatcher.DESCRIPTOR);
    }

    public static IUsageStatsWatcher asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(IUsageStatsWatcher.DESCRIPTOR);
      if (iin != null && (iin instanceof IUsageStatsWatcher)) {
        return (IUsageStatsWatcher) iin;
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
          return "noteResumeComponent";
        case 2:
          return "notePauseComponent";
        case 3:
          return "noteStopComponent";
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
        data.enforceInterface(IUsageStatsWatcher.DESCRIPTOR);
      }
      switch (code) {
        case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
          reply.writeString(IUsageStatsWatcher.DESCRIPTOR);
          return true;
        default:
          switch (code) {
            case 1:
              ComponentName _arg0 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
              Intent _arg1 = (Intent) data.readTypedObject(Intent.CREATOR);
              int _arg2 = data.readInt();
              int _arg3 = data.readInt();
              data.enforceNoDataAvail();
              noteResumeComponent(_arg0, _arg1, _arg2, _arg3);
              return true;
            case 2:
              ComponentName _arg02 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
              Intent _arg12 = (Intent) data.readTypedObject(Intent.CREATOR);
              int _arg22 = data.readInt();
              int _arg32 = data.readInt();
              data.enforceNoDataAvail();
              notePauseComponent(_arg02, _arg12, _arg22, _arg32);
              return true;
            case 3:
              ComponentName _arg03 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
              Intent _arg13 = (Intent) data.readTypedObject(Intent.CREATOR);
              int _arg23 = data.readInt();
              int _arg33 = data.readInt();
              data.enforceNoDataAvail();
              noteStopComponent(_arg03, _arg13, _arg23, _arg33);
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements IUsageStatsWatcher {
      private IBinder mRemote;

      Proxy(IBinder remote) {
        this.mRemote = remote;
      }

      @Override // android.os.IInterface
      public IBinder asBinder() {
        return this.mRemote;
      }

      public String getInterfaceDescriptor() {
        return IUsageStatsWatcher.DESCRIPTOR;
      }

      @Override // com.samsung.android.app.usage.IUsageStatsWatcher
      public void noteResumeComponent(
          ComponentName resumeComponent, Intent intent, int instanceId, int userId)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(IUsageStatsWatcher.DESCRIPTOR);
          _data.writeTypedObject(resumeComponent, 0);
          _data.writeTypedObject(intent, 0);
          _data.writeInt(instanceId);
          _data.writeInt(userId);
          this.mRemote.transact(1, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // com.samsung.android.app.usage.IUsageStatsWatcher
      public void notePauseComponent(
          ComponentName pauseComponent, Intent intent, int instanceId, int userId)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(IUsageStatsWatcher.DESCRIPTOR);
          _data.writeTypedObject(pauseComponent, 0);
          _data.writeTypedObject(intent, 0);
          _data.writeInt(instanceId);
          _data.writeInt(userId);
          this.mRemote.transact(2, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // com.samsung.android.app.usage.IUsageStatsWatcher
      public void noteStopComponent(
          ComponentName stopComponent, Intent intent, int instanceId, int userId)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(IUsageStatsWatcher.DESCRIPTOR);
          _data.writeTypedObject(stopComponent, 0);
          _data.writeTypedObject(intent, 0);
          _data.writeInt(instanceId);
          _data.writeInt(userId);
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
