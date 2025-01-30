package android.media;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface INativeSpatializerCallback extends IInterface {
  public static final String DESCRIPTOR =
      "android$media$INativeSpatializerCallback".replace('$', '.');

  void onLevelChanged(byte b) throws RemoteException;

  void onOutputChanged(int i) throws RemoteException;

  public static class Default implements INativeSpatializerCallback {
    @Override // android.media.INativeSpatializerCallback
    public void onLevelChanged(byte level) throws RemoteException {}

    @Override // android.media.INativeSpatializerCallback
    public void onOutputChanged(int output) throws RemoteException {}

    @Override // android.os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements INativeSpatializerCallback {
    static final int TRANSACTION_onLevelChanged = 1;
    static final int TRANSACTION_onOutputChanged = 2;

    public Stub() {
      attachInterface(this, DESCRIPTOR);
    }

    public static INativeSpatializerCallback asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
      if (iin != null && (iin instanceof INativeSpatializerCallback)) {
        return (INativeSpatializerCallback) iin;
      }
      return new Proxy(obj);
    }

    @Override // android.os.IInterface
    public IBinder asBinder() {
      return this;
    }

    @Override // android.os.Binder
    public boolean onTransact(int code, Parcel data, Parcel reply, int flags)
        throws RemoteException {
      String descriptor = DESCRIPTOR;
      if (code >= 1 && code <= 16777215) {
        data.enforceInterface(descriptor);
      }
      switch (code) {
        case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
          reply.writeString(descriptor);
          return true;
        default:
          switch (code) {
            case 1:
              byte _arg0 = data.readByte();
              data.enforceNoDataAvail();
              onLevelChanged(_arg0);
              return true;
            case 2:
              int _arg02 = data.readInt();
              data.enforceNoDataAvail();
              onOutputChanged(_arg02);
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements INativeSpatializerCallback {
      private IBinder mRemote;

      Proxy(IBinder remote) {
        this.mRemote = remote;
      }

      @Override // android.os.IInterface
      public IBinder asBinder() {
        return this.mRemote;
      }

      public String getInterfaceDescriptor() {
        return DESCRIPTOR;
      }

      @Override // android.media.INativeSpatializerCallback
      public void onLevelChanged(byte level) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeByte(level);
          this.mRemote.transact(1, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.media.INativeSpatializerCallback
      public void onOutputChanged(int output) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeInt(output);
          this.mRemote.transact(2, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }
    }
  }
}
