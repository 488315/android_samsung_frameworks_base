package com.android.internal.statusbar;

import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;
import com.android.internal.logging.InstanceId;

/* loaded from: classes5.dex */
public interface ISessionListener extends IInterface {
  public static final String DESCRIPTOR = "com.android.internal.statusbar.ISessionListener";

  void onSessionEnded(int i, InstanceId instanceId) throws RemoteException;

  void onSessionStarted(int i, InstanceId instanceId) throws RemoteException;

  public static class Default implements ISessionListener {
    @Override // com.android.internal.statusbar.ISessionListener
    public void onSessionStarted(int sessionType, InstanceId instance) throws RemoteException {}

    @Override // com.android.internal.statusbar.ISessionListener
    public void onSessionEnded(int sessionType, InstanceId instance) throws RemoteException {}

    @Override // android.p009os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements ISessionListener {
    static final int TRANSACTION_onSessionEnded = 2;
    static final int TRANSACTION_onSessionStarted = 1;

    public Stub() {
      attachInterface(this, ISessionListener.DESCRIPTOR);
    }

    public static ISessionListener asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(ISessionListener.DESCRIPTOR);
      if (iin != null && (iin instanceof ISessionListener)) {
        return (ISessionListener) iin;
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
          return "onSessionStarted";
        case 2:
          return "onSessionEnded";
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
        data.enforceInterface(ISessionListener.DESCRIPTOR);
      }
      switch (code) {
        case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
          reply.writeString(ISessionListener.DESCRIPTOR);
          return true;
        default:
          switch (code) {
            case 1:
              int _arg0 = data.readInt();
              InstanceId _arg1 = (InstanceId) data.readTypedObject(InstanceId.CREATOR);
              data.enforceNoDataAvail();
              onSessionStarted(_arg0, _arg1);
              return true;
            case 2:
              int _arg02 = data.readInt();
              InstanceId _arg12 = (InstanceId) data.readTypedObject(InstanceId.CREATOR);
              data.enforceNoDataAvail();
              onSessionEnded(_arg02, _arg12);
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements ISessionListener {
      private IBinder mRemote;

      Proxy(IBinder remote) {
        this.mRemote = remote;
      }

      @Override // android.p009os.IInterface
      public IBinder asBinder() {
        return this.mRemote;
      }

      public String getInterfaceDescriptor() {
        return ISessionListener.DESCRIPTOR;
      }

      @Override // com.android.internal.statusbar.ISessionListener
      public void onSessionStarted(int sessionType, InstanceId instance) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(ISessionListener.DESCRIPTOR);
          _data.writeInt(sessionType);
          _data.writeTypedObject(instance, 0);
          this.mRemote.transact(1, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // com.android.internal.statusbar.ISessionListener
      public void onSessionEnded(int sessionType, InstanceId instance) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(ISessionListener.DESCRIPTOR);
          _data.writeInt(sessionType);
          _data.writeTypedObject(instance, 0);
          this.mRemote.transact(2, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }
    }

    @Override // android.p009os.Binder
    public int getMaxTransactionId() {
      return 1;
    }
  }
}
