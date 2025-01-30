package android.hardware.input;

import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;
import android.view.PointerIcon;

/* loaded from: classes2.dex */
public interface IPointerIconChangedListener extends IInterface {
  public static final String DESCRIPTOR = "android.hardware.input.IPointerIconChangedListener";

  void onPointerIconChanged(int i, PointerIcon pointerIcon) throws RemoteException;

  public static class Default implements IPointerIconChangedListener {
    @Override // android.hardware.input.IPointerIconChangedListener
    public void onPointerIconChanged(int type, PointerIcon icon) throws RemoteException {}

    @Override // android.p009os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements IPointerIconChangedListener {
    static final int TRANSACTION_onPointerIconChanged = 1;

    public Stub() {
      attachInterface(this, IPointerIconChangedListener.DESCRIPTOR);
    }

    public static IPointerIconChangedListener asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(IPointerIconChangedListener.DESCRIPTOR);
      if (iin != null && (iin instanceof IPointerIconChangedListener)) {
        return (IPointerIconChangedListener) iin;
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
          return "onPointerIconChanged";
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
        data.enforceInterface(IPointerIconChangedListener.DESCRIPTOR);
      }
      switch (code) {
        case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
          reply.writeString(IPointerIconChangedListener.DESCRIPTOR);
          return true;
        default:
          switch (code) {
            case 1:
              int _arg0 = data.readInt();
              PointerIcon _arg1 = (PointerIcon) data.readTypedObject(PointerIcon.CREATOR);
              data.enforceNoDataAvail();
              onPointerIconChanged(_arg0, _arg1);
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements IPointerIconChangedListener {
      private IBinder mRemote;

      Proxy(IBinder remote) {
        this.mRemote = remote;
      }

      @Override // android.p009os.IInterface
      public IBinder asBinder() {
        return this.mRemote;
      }

      public String getInterfaceDescriptor() {
        return IPointerIconChangedListener.DESCRIPTOR;
      }

      @Override // android.hardware.input.IPointerIconChangedListener
      public void onPointerIconChanged(int type, PointerIcon icon) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(IPointerIconChangedListener.DESCRIPTOR);
          _data.writeInt(type);
          _data.writeTypedObject(icon, 0);
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
