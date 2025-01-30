package com.android.internal.telecom;

import android.content.ComponentName;
import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;
import java.util.List;

/* loaded from: classes5.dex */
public interface RemoteServiceCallback extends IInterface {
  void onError() throws RemoteException;

  void onResult(List<ComponentName> list, List<IBinder> list2) throws RemoteException;

  public static class Default implements RemoteServiceCallback {
    @Override // com.android.internal.telecom.RemoteServiceCallback
    public void onError() throws RemoteException {}

    @Override // com.android.internal.telecom.RemoteServiceCallback
    public void onResult(List<ComponentName> components, List<IBinder> callServices)
        throws RemoteException {}

    @Override // android.p009os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements RemoteServiceCallback {
    public static final String DESCRIPTOR = "com.android.internal.telecom.RemoteServiceCallback";
    static final int TRANSACTION_onError = 1;
    static final int TRANSACTION_onResult = 2;

    public Stub() {
      attachInterface(this, DESCRIPTOR);
    }

    public static RemoteServiceCallback asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
      if (iin != null && (iin instanceof RemoteServiceCallback)) {
        return (RemoteServiceCallback) iin;
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
          return "onError";
        case 2:
          return "onResult";
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
        data.enforceInterface(DESCRIPTOR);
      }
      switch (code) {
        case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
          reply.writeString(DESCRIPTOR);
          return true;
        default:
          switch (code) {
            case 1:
              onError();
              return true;
            case 2:
              List<ComponentName> _arg0 = data.createTypedArrayList(ComponentName.CREATOR);
              List<IBinder> _arg1 = data.createBinderArrayList();
              data.enforceNoDataAvail();
              onResult(_arg0, _arg1);
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements RemoteServiceCallback {
      private IBinder mRemote;

      Proxy(IBinder remote) {
        this.mRemote = remote;
      }

      @Override // android.p009os.IInterface
      public IBinder asBinder() {
        return this.mRemote;
      }

      public String getInterfaceDescriptor() {
        return Stub.DESCRIPTOR;
      }

      @Override // com.android.internal.telecom.RemoteServiceCallback
      public void onError() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          this.mRemote.transact(1, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // com.android.internal.telecom.RemoteServiceCallback
      public void onResult(List<ComponentName> components, List<IBinder> callServices)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeTypedList(components, 0);
          _data.writeBinderList(callServices);
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
