package com.samsung.android.edge;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes5.dex */
public interface IEdgeLightingCallback extends IInterface {
  public static final String DESCRIPTOR = "com.samsung.android.edge.IEdgeLightingCallback";

  void onEdgeLightingStarted() throws RemoteException;

  void onEdgeLightingStopped() throws RemoteException;

  void onScreenChanged(boolean z) throws RemoteException;

  void onStartEdgeLighting(String str, SemEdgeLightingInfo semEdgeLightingInfo, int i)
      throws RemoteException;

  void onStopEdgeLighting(String str, int i) throws RemoteException;

  public static class Default implements IEdgeLightingCallback {
    @Override // com.samsung.android.edge.IEdgeLightingCallback
    public void onStartEdgeLighting(String packageName, SemEdgeLightingInfo info, int reason)
        throws RemoteException {}

    @Override // com.samsung.android.edge.IEdgeLightingCallback
    public void onStopEdgeLighting(String packageName, int reason) throws RemoteException {}

    @Override // com.samsung.android.edge.IEdgeLightingCallback
    public void onScreenChanged(boolean on) throws RemoteException {}

    @Override // com.samsung.android.edge.IEdgeLightingCallback
    public void onEdgeLightingStarted() throws RemoteException {}

    @Override // com.samsung.android.edge.IEdgeLightingCallback
    public void onEdgeLightingStopped() throws RemoteException {}

    @Override // android.os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements IEdgeLightingCallback {
    static final int TRANSACTION_onEdgeLightingStarted = 4;
    static final int TRANSACTION_onEdgeLightingStopped = 5;
    static final int TRANSACTION_onScreenChanged = 3;
    static final int TRANSACTION_onStartEdgeLighting = 1;
    static final int TRANSACTION_onStopEdgeLighting = 2;

    public Stub() {
      attachInterface(this, IEdgeLightingCallback.DESCRIPTOR);
    }

    public static IEdgeLightingCallback asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(IEdgeLightingCallback.DESCRIPTOR);
      if (iin != null && (iin instanceof IEdgeLightingCallback)) {
        return (IEdgeLightingCallback) iin;
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
          return "onStartEdgeLighting";
        case 2:
          return "onStopEdgeLighting";
        case 3:
          return "onScreenChanged";
        case 4:
          return "onEdgeLightingStarted";
        case 5:
          return "onEdgeLightingStopped";
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
        data.enforceInterface(IEdgeLightingCallback.DESCRIPTOR);
      }
      switch (code) {
        case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
          reply.writeString(IEdgeLightingCallback.DESCRIPTOR);
          return true;
        default:
          switch (code) {
            case 1:
              String _arg0 = data.readString();
              SemEdgeLightingInfo _arg1 =
                  (SemEdgeLightingInfo) data.readTypedObject(SemEdgeLightingInfo.CREATOR);
              int _arg2 = data.readInt();
              data.enforceNoDataAvail();
              onStartEdgeLighting(_arg0, _arg1, _arg2);
              return true;
            case 2:
              String _arg02 = data.readString();
              int _arg12 = data.readInt();
              data.enforceNoDataAvail();
              onStopEdgeLighting(_arg02, _arg12);
              return true;
            case 3:
              boolean _arg03 = data.readBoolean();
              data.enforceNoDataAvail();
              onScreenChanged(_arg03);
              return true;
            case 4:
              onEdgeLightingStarted();
              return true;
            case 5:
              onEdgeLightingStopped();
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements IEdgeLightingCallback {
      private IBinder mRemote;

      Proxy(IBinder remote) {
        this.mRemote = remote;
      }

      @Override // android.os.IInterface
      public IBinder asBinder() {
        return this.mRemote;
      }

      public String getInterfaceDescriptor() {
        return IEdgeLightingCallback.DESCRIPTOR;
      }

      @Override // com.samsung.android.edge.IEdgeLightingCallback
      public void onStartEdgeLighting(String packageName, SemEdgeLightingInfo info, int reason)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(IEdgeLightingCallback.DESCRIPTOR);
          _data.writeString(packageName);
          _data.writeTypedObject(info, 0);
          _data.writeInt(reason);
          this.mRemote.transact(1, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // com.samsung.android.edge.IEdgeLightingCallback
      public void onStopEdgeLighting(String packageName, int reason) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(IEdgeLightingCallback.DESCRIPTOR);
          _data.writeString(packageName);
          _data.writeInt(reason);
          this.mRemote.transact(2, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // com.samsung.android.edge.IEdgeLightingCallback
      public void onScreenChanged(boolean on) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(IEdgeLightingCallback.DESCRIPTOR);
          _data.writeBoolean(on);
          this.mRemote.transact(3, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // com.samsung.android.edge.IEdgeLightingCallback
      public void onEdgeLightingStarted() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(IEdgeLightingCallback.DESCRIPTOR);
          this.mRemote.transact(4, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // com.samsung.android.edge.IEdgeLightingCallback
      public void onEdgeLightingStopped() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(IEdgeLightingCallback.DESCRIPTOR);
          this.mRemote.transact(5, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }
    }

    @Override // android.os.Binder
    public int getMaxTransactionId() {
      return 4;
    }
  }
}
