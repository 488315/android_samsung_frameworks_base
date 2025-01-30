package android.hardware.location;

import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;
import java.util.List;

/* loaded from: classes2.dex */
public interface IContextHubTransactionCallback extends IInterface {
  void onQueryResponse(int i, List<NanoAppState> list) throws RemoteException;

  void onTransactionComplete(int i) throws RemoteException;

  public static class Default implements IContextHubTransactionCallback {
    @Override // android.hardware.location.IContextHubTransactionCallback
    public void onQueryResponse(int result, List<NanoAppState> nanoappList)
        throws RemoteException {}

    @Override // android.hardware.location.IContextHubTransactionCallback
    public void onTransactionComplete(int result) throws RemoteException {}

    @Override // android.p009os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements IContextHubTransactionCallback {
    public static final String DESCRIPTOR =
        "android.hardware.location.IContextHubTransactionCallback";
    static final int TRANSACTION_onQueryResponse = 1;
    static final int TRANSACTION_onTransactionComplete = 2;

    public Stub() {
      attachInterface(this, DESCRIPTOR);
    }

    public static IContextHubTransactionCallback asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
      if (iin != null && (iin instanceof IContextHubTransactionCallback)) {
        return (IContextHubTransactionCallback) iin;
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
          return "onQueryResponse";
        case 2:
          return "onTransactionComplete";
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
              int _arg0 = data.readInt();
              List<NanoAppState> _arg1 = data.createTypedArrayList(NanoAppState.CREATOR);
              data.enforceNoDataAvail();
              onQueryResponse(_arg0, _arg1);
              return true;
            case 2:
              int _arg02 = data.readInt();
              data.enforceNoDataAvail();
              onTransactionComplete(_arg02);
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements IContextHubTransactionCallback {
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

      @Override // android.hardware.location.IContextHubTransactionCallback
      public void onQueryResponse(int result, List<NanoAppState> nanoappList)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(result);
          _data.writeTypedList(nanoappList, 0);
          this.mRemote.transact(1, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.hardware.location.IContextHubTransactionCallback
      public void onTransactionComplete(int result) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(result);
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
