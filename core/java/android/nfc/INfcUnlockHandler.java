package android.nfc;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface INfcUnlockHandler extends IInterface {
  boolean onUnlockAttempted(Tag tag) throws RemoteException;

  public static class Default implements INfcUnlockHandler {
    @Override // android.nfc.INfcUnlockHandler
    public boolean onUnlockAttempted(Tag tag) throws RemoteException {
      return false;
    }

    @Override // android.os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements INfcUnlockHandler {
    public static final String DESCRIPTOR = "android.nfc.INfcUnlockHandler";
    static final int TRANSACTION_onUnlockAttempted = 1;

    public Stub() {
      attachInterface(this, DESCRIPTOR);
    }

    public static INfcUnlockHandler asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
      if (iin != null && (iin instanceof INfcUnlockHandler)) {
        return (INfcUnlockHandler) iin;
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
          return "onUnlockAttempted";
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
              Tag _arg0 = (Tag) data.readTypedObject(Tag.CREATOR);
              data.enforceNoDataAvail();
              boolean _result = onUnlockAttempted(_arg0);
              reply.writeNoException();
              reply.writeBoolean(_result);
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements INfcUnlockHandler {
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

      @Override // android.nfc.INfcUnlockHandler
      public boolean onUnlockAttempted(Tag tag) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeTypedObject(tag, 0);
          this.mRemote.transact(1, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
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
