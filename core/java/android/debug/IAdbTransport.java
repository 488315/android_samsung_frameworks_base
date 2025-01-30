package android.debug;

import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;

/* loaded from: classes.dex */
public interface IAdbTransport extends IInterface {
  public static final String DESCRIPTOR = "android.debug.IAdbTransport";

  void onAdbEnabled(boolean z, byte b) throws RemoteException;

  public static class Default implements IAdbTransport {
    @Override // android.debug.IAdbTransport
    public void onAdbEnabled(boolean enabled, byte type) throws RemoteException {}

    @Override // android.p009os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements IAdbTransport {
    static final int TRANSACTION_onAdbEnabled = 1;

    public Stub() {
      attachInterface(this, IAdbTransport.DESCRIPTOR);
    }

    public static IAdbTransport asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(IAdbTransport.DESCRIPTOR);
      if (iin != null && (iin instanceof IAdbTransport)) {
        return (IAdbTransport) iin;
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
          return "onAdbEnabled";
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
        data.enforceInterface(IAdbTransport.DESCRIPTOR);
      }
      switch (code) {
        case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
          reply.writeString(IAdbTransport.DESCRIPTOR);
          return true;
        default:
          switch (code) {
            case 1:
              boolean _arg0 = data.readBoolean();
              byte _arg1 = data.readByte();
              data.enforceNoDataAvail();
              onAdbEnabled(_arg0, _arg1);
              reply.writeNoException();
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements IAdbTransport {
      private IBinder mRemote;

      Proxy(IBinder remote) {
        this.mRemote = remote;
      }

      @Override // android.p009os.IInterface
      public IBinder asBinder() {
        return this.mRemote;
      }

      public String getInterfaceDescriptor() {
        return IAdbTransport.DESCRIPTOR;
      }

      @Override // android.debug.IAdbTransport
      public void onAdbEnabled(boolean enabled, byte type) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IAdbTransport.DESCRIPTOR);
          _data.writeBoolean(enabled);
          _data.writeByte(type);
          this.mRemote.transact(1, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
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
