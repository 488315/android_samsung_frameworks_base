package android.security.keymaster;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface IKeyAttestationApplicationIdProvider extends IInterface {
  KeyAttestationApplicationId getKeyAttestationApplicationId(int i) throws RemoteException;

  public static class Default implements IKeyAttestationApplicationIdProvider {
    @Override // android.security.keymaster.IKeyAttestationApplicationIdProvider
    public KeyAttestationApplicationId getKeyAttestationApplicationId(int uid)
        throws RemoteException {
      return null;
    }

    @Override // android.os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements IKeyAttestationApplicationIdProvider {
    public static final String DESCRIPTOR =
        "android.security.keymaster.IKeyAttestationApplicationIdProvider";
    static final int TRANSACTION_getKeyAttestationApplicationId = 1;

    public Stub() {
      attachInterface(this, DESCRIPTOR);
    }

    public static IKeyAttestationApplicationIdProvider asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
      if (iin != null && (iin instanceof IKeyAttestationApplicationIdProvider)) {
        return (IKeyAttestationApplicationIdProvider) iin;
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
          return "getKeyAttestationApplicationId";
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
              data.enforceNoDataAvail();
              KeyAttestationApplicationId _result = getKeyAttestationApplicationId(_arg0);
              reply.writeNoException();
              reply.writeTypedObject(_result, 1);
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements IKeyAttestationApplicationIdProvider {
      private IBinder mRemote;

      Proxy(IBinder remote) {
        this.mRemote = remote;
      }

      @Override // android.os.IInterface
      public IBinder asBinder() {
        return this.mRemote;
      }

      public String getInterfaceDescriptor() {
        return Stub.DESCRIPTOR;
      }

      @Override // android.security.keymaster.IKeyAttestationApplicationIdProvider
      public KeyAttestationApplicationId getKeyAttestationApplicationId(int uid)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(uid);
          this.mRemote.transact(1, _data, _reply, 0);
          _reply.readException();
          KeyAttestationApplicationId _result =
              (KeyAttestationApplicationId)
                  _reply.readTypedObject(KeyAttestationApplicationId.CREATOR);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }
    }

    @Override // android.os.Binder
    public int getMaxTransactionId() {
      return 0;
    }
  }
}
