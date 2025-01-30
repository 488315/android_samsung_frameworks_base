package android.speech;

import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;

/* loaded from: classes3.dex */
public interface IRecognitionServiceManagerCallback extends IInterface {
  public static final String DESCRIPTOR = "android.speech.IRecognitionServiceManagerCallback";

  void onError(int i) throws RemoteException;

  void onSuccess(IRecognitionService iRecognitionService) throws RemoteException;

  public static class Default implements IRecognitionServiceManagerCallback {
    @Override // android.speech.IRecognitionServiceManagerCallback
    public void onSuccess(IRecognitionService service) throws RemoteException {}

    @Override // android.speech.IRecognitionServiceManagerCallback
    public void onError(int errorCode) throws RemoteException {}

    @Override // android.p009os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements IRecognitionServiceManagerCallback {
    static final int TRANSACTION_onError = 2;
    static final int TRANSACTION_onSuccess = 1;

    public Stub() {
      attachInterface(this, IRecognitionServiceManagerCallback.DESCRIPTOR);
    }

    public static IRecognitionServiceManagerCallback asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(IRecognitionServiceManagerCallback.DESCRIPTOR);
      if (iin != null && (iin instanceof IRecognitionServiceManagerCallback)) {
        return (IRecognitionServiceManagerCallback) iin;
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
          return "onSuccess";
        case 2:
          return "onError";
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
        data.enforceInterface(IRecognitionServiceManagerCallback.DESCRIPTOR);
      }
      switch (code) {
        case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
          reply.writeString(IRecognitionServiceManagerCallback.DESCRIPTOR);
          return true;
        default:
          switch (code) {
            case 1:
              IRecognitionService _arg0 =
                  IRecognitionService.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              onSuccess(_arg0);
              return true;
            case 2:
              int _arg02 = data.readInt();
              data.enforceNoDataAvail();
              onError(_arg02);
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements IRecognitionServiceManagerCallback {
      private IBinder mRemote;

      Proxy(IBinder remote) {
        this.mRemote = remote;
      }

      @Override // android.p009os.IInterface
      public IBinder asBinder() {
        return this.mRemote;
      }

      public String getInterfaceDescriptor() {
        return IRecognitionServiceManagerCallback.DESCRIPTOR;
      }

      @Override // android.speech.IRecognitionServiceManagerCallback
      public void onSuccess(IRecognitionService service) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(IRecognitionServiceManagerCallback.DESCRIPTOR);
          _data.writeStrongInterface(service);
          this.mRemote.transact(1, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.speech.IRecognitionServiceManagerCallback
      public void onError(int errorCode) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(IRecognitionServiceManagerCallback.DESCRIPTOR);
          _data.writeInt(errorCode);
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
