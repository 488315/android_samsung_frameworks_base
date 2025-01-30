package android.app;

import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;

/* loaded from: classes.dex */
public interface INotificationPlayerOnCompletionListener extends IInterface {
  public static final String DESCRIPTOR = "android.app.INotificationPlayerOnCompletionListener";

  void onCompletion() throws RemoteException;

  public static class Default implements INotificationPlayerOnCompletionListener {
    @Override // android.app.INotificationPlayerOnCompletionListener
    public void onCompletion() throws RemoteException {}

    @Override // android.p009os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder
      implements INotificationPlayerOnCompletionListener {
    static final int TRANSACTION_onCompletion = 1;

    public Stub() {
      attachInterface(this, INotificationPlayerOnCompletionListener.DESCRIPTOR);
    }

    public static INotificationPlayerOnCompletionListener asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(INotificationPlayerOnCompletionListener.DESCRIPTOR);
      if (iin != null && (iin instanceof INotificationPlayerOnCompletionListener)) {
        return (INotificationPlayerOnCompletionListener) iin;
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
          return "onCompletion";
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
        data.enforceInterface(INotificationPlayerOnCompletionListener.DESCRIPTOR);
      }
      switch (code) {
        case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
          reply.writeString(INotificationPlayerOnCompletionListener.DESCRIPTOR);
          return true;
        default:
          switch (code) {
            case 1:
              onCompletion();
              reply.writeNoException();
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements INotificationPlayerOnCompletionListener {
      private IBinder mRemote;

      Proxy(IBinder remote) {
        this.mRemote = remote;
      }

      @Override // android.p009os.IInterface
      public IBinder asBinder() {
        return this.mRemote;
      }

      public String getInterfaceDescriptor() {
        return INotificationPlayerOnCompletionListener.DESCRIPTOR;
      }

      @Override // android.app.INotificationPlayerOnCompletionListener
      public void onCompletion() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(INotificationPlayerOnCompletionListener.DESCRIPTOR);
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
