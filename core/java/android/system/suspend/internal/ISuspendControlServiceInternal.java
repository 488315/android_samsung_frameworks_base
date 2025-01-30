package android.system.suspend.internal;

import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;

/* loaded from: classes3.dex */
public interface ISuspendControlServiceInternal extends IInterface {
  public static final String DESCRIPTOR =
      "android$system$suspend$internal$ISuspendControlServiceInternal".replace('$', '.');

  boolean enableAutosuspend(IBinder iBinder) throws RemoteException;

  boolean forceSuspend() throws RemoteException;

  SuspendInfo getSuspendStats() throws RemoteException;

  WakeLockInfo[] getWakeLockStats() throws RemoteException;

  WakeupInfo[] getWakeupStats() throws RemoteException;

  public static class Default implements ISuspendControlServiceInternal {
    @Override // android.system.suspend.internal.ISuspendControlServiceInternal
    public boolean enableAutosuspend(IBinder token) throws RemoteException {
      return false;
    }

    @Override // android.system.suspend.internal.ISuspendControlServiceInternal
    public boolean forceSuspend() throws RemoteException {
      return false;
    }

    @Override // android.system.suspend.internal.ISuspendControlServiceInternal
    public WakeLockInfo[] getWakeLockStats() throws RemoteException {
      return null;
    }

    @Override // android.system.suspend.internal.ISuspendControlServiceInternal
    public WakeupInfo[] getWakeupStats() throws RemoteException {
      return null;
    }

    @Override // android.system.suspend.internal.ISuspendControlServiceInternal
    public SuspendInfo getSuspendStats() throws RemoteException {
      return null;
    }

    @Override // android.p009os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements ISuspendControlServiceInternal {
    static final int TRANSACTION_enableAutosuspend = 1;
    static final int TRANSACTION_forceSuspend = 2;
    static final int TRANSACTION_getSuspendStats = 5;
    static final int TRANSACTION_getWakeLockStats = 3;
    static final int TRANSACTION_getWakeupStats = 4;

    public Stub() {
      attachInterface(this, DESCRIPTOR);
    }

    public static ISuspendControlServiceInternal asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
      if (iin != null && (iin instanceof ISuspendControlServiceInternal)) {
        return (ISuspendControlServiceInternal) iin;
      }
      return new Proxy(obj);
    }

    @Override // android.p009os.IInterface
    public IBinder asBinder() {
      return this;
    }

    @Override // android.p009os.Binder
    public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
      String str = DESCRIPTOR;
      if (i >= 1 && i <= 16777215) {
        parcel.enforceInterface(str);
      }
      switch (i) {
        case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
          parcel2.writeString(str);
          return true;
        default:
          switch (i) {
            case 1:
              boolean enableAutosuspend = enableAutosuspend(parcel.readStrongBinder());
              parcel2.writeNoException();
              parcel2.writeInt(enableAutosuspend ? 1 : 0);
              return true;
            case 2:
              boolean forceSuspend = forceSuspend();
              parcel2.writeNoException();
              parcel2.writeInt(forceSuspend ? 1 : 0);
              return true;
            case 3:
              WakeLockInfo[] wakeLockStats = getWakeLockStats();
              parcel2.writeNoException();
              parcel2.writeTypedArray(wakeLockStats, 1);
              return true;
            case 4:
              WakeupInfo[] wakeupStats = getWakeupStats();
              parcel2.writeNoException();
              parcel2.writeTypedArray(wakeupStats, 1);
              return true;
            case 5:
              SuspendInfo suspendStats = getSuspendStats();
              parcel2.writeNoException();
              parcel2.writeTypedObject(suspendStats, 1);
              return true;
            default:
              return super.onTransact(i, parcel, parcel2, i2);
          }
      }
    }

    private static class Proxy implements ISuspendControlServiceInternal {
      private IBinder mRemote;

      Proxy(IBinder remote) {
        this.mRemote = remote;
      }

      @Override // android.p009os.IInterface
      public IBinder asBinder() {
        return this.mRemote;
      }

      public String getInterfaceDescriptor() {
        return DESCRIPTOR;
      }

      @Override // android.system.suspend.internal.ISuspendControlServiceInternal
      public boolean enableAutosuspend(IBinder token) throws RemoteException {
        Parcel _data = Parcel.obtain();
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeStrongBinder(token);
          this.mRemote.transact(1, _data, _reply, 0);
          _reply.readException();
          boolean _status = _reply.readInt() != 0;
          return _status;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.system.suspend.internal.ISuspendControlServiceInternal
      public boolean forceSuspend() throws RemoteException {
        Parcel _data = Parcel.obtain();
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          this.mRemote.transact(2, _data, _reply, 0);
          _reply.readException();
          boolean _status = _reply.readInt() != 0;
          return _status;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.system.suspend.internal.ISuspendControlServiceInternal
      public WakeLockInfo[] getWakeLockStats() throws RemoteException {
        Parcel _data = Parcel.obtain();
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          this.mRemote.transact(3, _data, _reply, 0);
          _reply.readException();
          WakeLockInfo[] _result = (WakeLockInfo[]) _reply.createTypedArray(WakeLockInfo.CREATOR);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.system.suspend.internal.ISuspendControlServiceInternal
      public WakeupInfo[] getWakeupStats() throws RemoteException {
        Parcel _data = Parcel.obtain();
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          this.mRemote.transact(4, _data, _reply, 0);
          _reply.readException();
          WakeupInfo[] _result = (WakeupInfo[]) _reply.createTypedArray(WakeupInfo.CREATOR);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.system.suspend.internal.ISuspendControlServiceInternal
      public SuspendInfo getSuspendStats() throws RemoteException {
        Parcel _data = Parcel.obtain();
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          this.mRemote.transact(5, _data, _reply, 0);
          _reply.readException();
          SuspendInfo _result = (SuspendInfo) _reply.readTypedObject(SuspendInfo.CREATOR);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }
    }
  }
}
