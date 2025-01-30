package android.hardware.input;

import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;

/* loaded from: classes2.dex */
public interface IMultiFingerGestureListener extends IInterface {
  public static final String DESCRIPTOR = "android.hardware.input.IMultiFingerGestureListener";

  void onMultiFingerGesture(int i, int i2) throws RemoteException;

  public static class Default implements IMultiFingerGestureListener {
    @Override // android.hardware.input.IMultiFingerGestureListener
    public void onMultiFingerGesture(int behavior, int reserved) throws RemoteException {}

    @Override // android.p009os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements IMultiFingerGestureListener {
    static final int TRANSACTION_onMultiFingerGesture = 1;

    public Stub() {
      attachInterface(this, IMultiFingerGestureListener.DESCRIPTOR);
    }

    public static IMultiFingerGestureListener asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(IMultiFingerGestureListener.DESCRIPTOR);
      if (iin != null && (iin instanceof IMultiFingerGestureListener)) {
        return (IMultiFingerGestureListener) iin;
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
          return "onMultiFingerGesture";
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
        data.enforceInterface(IMultiFingerGestureListener.DESCRIPTOR);
      }
      switch (code) {
        case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
          reply.writeString(IMultiFingerGestureListener.DESCRIPTOR);
          return true;
        default:
          switch (code) {
            case 1:
              int _arg0 = data.readInt();
              int _arg1 = data.readInt();
              data.enforceNoDataAvail();
              onMultiFingerGesture(_arg0, _arg1);
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements IMultiFingerGestureListener {
      private IBinder mRemote;

      Proxy(IBinder remote) {
        this.mRemote = remote;
      }

      @Override // android.p009os.IInterface
      public IBinder asBinder() {
        return this.mRemote;
      }

      public String getInterfaceDescriptor() {
        return IMultiFingerGestureListener.DESCRIPTOR;
      }

      @Override // android.hardware.input.IMultiFingerGestureListener
      public void onMultiFingerGesture(int behavior, int reserved) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(IMultiFingerGestureListener.DESCRIPTOR);
          _data.writeInt(behavior);
          _data.writeInt(reserved);
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
