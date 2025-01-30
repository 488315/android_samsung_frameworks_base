package android.content.p002pm;

import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;

/* loaded from: classes.dex */
public interface IDexModuleRegisterCallback extends IInterface {
  void onDexModuleRegistered(String str, boolean z, String str2) throws RemoteException;

  public static class Default implements IDexModuleRegisterCallback {
    @Override // android.content.p002pm.IDexModuleRegisterCallback
    public void onDexModuleRegistered(String dexModulePath, boolean success, String message)
        throws RemoteException {}

    @Override // android.p009os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements IDexModuleRegisterCallback {
    public static final String DESCRIPTOR = "android.content.pm.IDexModuleRegisterCallback";
    static final int TRANSACTION_onDexModuleRegistered = 1;

    public Stub() {
      attachInterface(this, DESCRIPTOR);
    }

    public static IDexModuleRegisterCallback asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
      if (iin != null && (iin instanceof IDexModuleRegisterCallback)) {
        return (IDexModuleRegisterCallback) iin;
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
          return "onDexModuleRegistered";
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
              String _arg0 = data.readString();
              boolean _arg1 = data.readBoolean();
              String _arg2 = data.readString();
              data.enforceNoDataAvail();
              onDexModuleRegistered(_arg0, _arg1, _arg2);
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements IDexModuleRegisterCallback {
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

      @Override // android.content.p002pm.IDexModuleRegisterCallback
      public void onDexModuleRegistered(String dexModulePath, boolean success, String message)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(dexModulePath);
          _data.writeBoolean(success);
          _data.writeString(message);
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
