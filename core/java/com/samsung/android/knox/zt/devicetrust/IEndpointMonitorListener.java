package com.samsung.android.knox.zt.devicetrust;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes5.dex */
public interface IEndpointMonitorListener extends IInterface {
  public static final String DESCRIPTOR =
      "com.samsung.android.knox.zt.devicetrust.IEndpointMonitorListener";

  void onEvent(int i, Bundle bundle) throws RemoteException;

  void onEventGeneralized(int i, String str) throws RemoteException;

  void onEventSimplified(int i, String str) throws RemoteException;

  public static class Default implements IEndpointMonitorListener {
    @Override // com.samsung.android.knox.zt.devicetrust.IEndpointMonitorListener
    public void onEventSimplified(int event, String data) throws RemoteException {}

    @Override // com.samsung.android.knox.zt.devicetrust.IEndpointMonitorListener
    public void onEventGeneralized(int event, String data) throws RemoteException {}

    @Override // com.samsung.android.knox.zt.devicetrust.IEndpointMonitorListener
    public void onEvent(int event, Bundle data) throws RemoteException {}

    @Override // android.os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements IEndpointMonitorListener {
    static final int TRANSACTION_onEvent = 3;
    static final int TRANSACTION_onEventGeneralized = 2;
    static final int TRANSACTION_onEventSimplified = 1;

    public Stub() {
      attachInterface(this, IEndpointMonitorListener.DESCRIPTOR);
    }

    public static IEndpointMonitorListener asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(IEndpointMonitorListener.DESCRIPTOR);
      if (iin != null && (iin instanceof IEndpointMonitorListener)) {
        return (IEndpointMonitorListener) iin;
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
          return "onEventSimplified";
        case 2:
          return "onEventGeneralized";
        case 3:
          return "onEvent";
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
        data.enforceInterface(IEndpointMonitorListener.DESCRIPTOR);
      }
      switch (code) {
        case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
          reply.writeString(IEndpointMonitorListener.DESCRIPTOR);
          return true;
        default:
          switch (code) {
            case 1:
              int _arg0 = data.readInt();
              String _arg1 = data.readString();
              data.enforceNoDataAvail();
              onEventSimplified(_arg0, _arg1);
              return true;
            case 2:
              int _arg02 = data.readInt();
              String _arg12 = data.readString();
              data.enforceNoDataAvail();
              onEventGeneralized(_arg02, _arg12);
              return true;
            case 3:
              int _arg03 = data.readInt();
              Bundle _arg13 = (Bundle) data.readTypedObject(Bundle.CREATOR);
              data.enforceNoDataAvail();
              onEvent(_arg03, _arg13);
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements IEndpointMonitorListener {
      private IBinder mRemote;

      Proxy(IBinder remote) {
        this.mRemote = remote;
      }

      @Override // android.os.IInterface
      public IBinder asBinder() {
        return this.mRemote;
      }

      public String getInterfaceDescriptor() {
        return IEndpointMonitorListener.DESCRIPTOR;
      }

      @Override // com.samsung.android.knox.zt.devicetrust.IEndpointMonitorListener
      public void onEventSimplified(int event, String data) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(IEndpointMonitorListener.DESCRIPTOR);
          _data.writeInt(event);
          _data.writeString(data);
          this.mRemote.transact(1, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // com.samsung.android.knox.zt.devicetrust.IEndpointMonitorListener
      public void onEventGeneralized(int event, String data) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(IEndpointMonitorListener.DESCRIPTOR);
          _data.writeInt(event);
          _data.writeString(data);
          this.mRemote.transact(2, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // com.samsung.android.knox.zt.devicetrust.IEndpointMonitorListener
      public void onEvent(int event, Bundle data) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(IEndpointMonitorListener.DESCRIPTOR);
          _data.writeInt(event);
          _data.writeTypedObject(data, 0);
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
