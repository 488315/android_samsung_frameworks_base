package android.window;

import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.ParcelFileDescriptor;
import android.p009os.RemoteException;

/* loaded from: classes4.dex */
public interface IDumpCallback extends IInterface {
  public static final String DESCRIPTOR = "android.window.IDumpCallback";

  void onDump(ParcelFileDescriptor parcelFileDescriptor) throws RemoteException;

  public static class Default implements IDumpCallback {
    @Override // android.window.IDumpCallback
    public void onDump(ParcelFileDescriptor outFd) throws RemoteException {}

    @Override // android.p009os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements IDumpCallback {
    static final int TRANSACTION_onDump = 1;

    public Stub() {
      attachInterface(this, IDumpCallback.DESCRIPTOR);
    }

    public static IDumpCallback asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(IDumpCallback.DESCRIPTOR);
      if (iin != null && (iin instanceof IDumpCallback)) {
        return (IDumpCallback) iin;
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
          return "onDump";
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
        data.enforceInterface(IDumpCallback.DESCRIPTOR);
      }
      switch (code) {
        case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
          reply.writeString(IDumpCallback.DESCRIPTOR);
          return true;
        default:
          switch (code) {
            case 1:
              ParcelFileDescriptor _arg0 =
                  (ParcelFileDescriptor) data.readTypedObject(ParcelFileDescriptor.CREATOR);
              data.enforceNoDataAvail();
              onDump(_arg0);
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements IDumpCallback {
      private IBinder mRemote;

      Proxy(IBinder remote) {
        this.mRemote = remote;
      }

      @Override // android.p009os.IInterface
      public IBinder asBinder() {
        return this.mRemote;
      }

      public String getInterfaceDescriptor() {
        return IDumpCallback.DESCRIPTOR;
      }

      @Override // android.window.IDumpCallback
      public void onDump(ParcelFileDescriptor outFd) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(IDumpCallback.DESCRIPTOR);
          _data.writeTypedObject(outFd, 0);
          this.mRemote.transact(1, _data, null, 1);
        } finally {
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
