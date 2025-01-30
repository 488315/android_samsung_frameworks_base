package android.service.assist.classification;

import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.ICancellationSignal;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;

/* loaded from: classes3.dex */
public interface IFieldClassificationCallback extends IInterface {
  public static final String DESCRIPTOR =
      "android.service.assist.classification.IFieldClassificationCallback";

  void cancel() throws RemoteException;

  boolean isCompleted() throws RemoteException;

  void onCancellable(ICancellationSignal iCancellationSignal) throws RemoteException;

  void onFailure() throws RemoteException;

  void onSuccess(FieldClassificationResponse fieldClassificationResponse) throws RemoteException;

  public static class Default implements IFieldClassificationCallback {
    @Override // android.service.assist.classification.IFieldClassificationCallback
    public void onCancellable(ICancellationSignal cancellation) throws RemoteException {}

    @Override // android.service.assist.classification.IFieldClassificationCallback
    public void onSuccess(FieldClassificationResponse response) throws RemoteException {}

    @Override // android.service.assist.classification.IFieldClassificationCallback
    public void onFailure() throws RemoteException {}

    @Override // android.service.assist.classification.IFieldClassificationCallback
    public boolean isCompleted() throws RemoteException {
      return false;
    }

    @Override // android.service.assist.classification.IFieldClassificationCallback
    public void cancel() throws RemoteException {}

    @Override // android.p009os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements IFieldClassificationCallback {
    static final int TRANSACTION_cancel = 5;
    static final int TRANSACTION_isCompleted = 4;
    static final int TRANSACTION_onCancellable = 1;
    static final int TRANSACTION_onFailure = 3;
    static final int TRANSACTION_onSuccess = 2;

    public Stub() {
      attachInterface(this, IFieldClassificationCallback.DESCRIPTOR);
    }

    public static IFieldClassificationCallback asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(IFieldClassificationCallback.DESCRIPTOR);
      if (iin != null && (iin instanceof IFieldClassificationCallback)) {
        return (IFieldClassificationCallback) iin;
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
          return "onCancellable";
        case 2:
          return "onSuccess";
        case 3:
          return "onFailure";
        case 4:
          return "isCompleted";
        case 5:
          return "cancel";
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
        data.enforceInterface(IFieldClassificationCallback.DESCRIPTOR);
      }
      switch (code) {
        case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
          reply.writeString(IFieldClassificationCallback.DESCRIPTOR);
          return true;
        default:
          switch (code) {
            case 1:
              ICancellationSignal _arg0 =
                  ICancellationSignal.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              onCancellable(_arg0);
              reply.writeNoException();
              return true;
            case 2:
              FieldClassificationResponse _arg02 =
                  (FieldClassificationResponse)
                      data.readTypedObject(FieldClassificationResponse.CREATOR);
              data.enforceNoDataAvail();
              onSuccess(_arg02);
              reply.writeNoException();
              return true;
            case 3:
              onFailure();
              reply.writeNoException();
              return true;
            case 4:
              boolean _result = isCompleted();
              reply.writeNoException();
              reply.writeBoolean(_result);
              return true;
            case 5:
              cancel();
              reply.writeNoException();
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements IFieldClassificationCallback {
      private IBinder mRemote;

      Proxy(IBinder remote) {
        this.mRemote = remote;
      }

      @Override // android.p009os.IInterface
      public IBinder asBinder() {
        return this.mRemote;
      }

      public String getInterfaceDescriptor() {
        return IFieldClassificationCallback.DESCRIPTOR;
      }

      @Override // android.service.assist.classification.IFieldClassificationCallback
      public void onCancellable(ICancellationSignal cancellation) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IFieldClassificationCallback.DESCRIPTOR);
          _data.writeStrongInterface(cancellation);
          this.mRemote.transact(1, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.service.assist.classification.IFieldClassificationCallback
      public void onSuccess(FieldClassificationResponse response) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IFieldClassificationCallback.DESCRIPTOR);
          _data.writeTypedObject(response, 0);
          this.mRemote.transact(2, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.service.assist.classification.IFieldClassificationCallback
      public void onFailure() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IFieldClassificationCallback.DESCRIPTOR);
          this.mRemote.transact(3, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.service.assist.classification.IFieldClassificationCallback
      public boolean isCompleted() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IFieldClassificationCallback.DESCRIPTOR);
          this.mRemote.transact(4, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.service.assist.classification.IFieldClassificationCallback
      public void cancel() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IFieldClassificationCallback.DESCRIPTOR);
          this.mRemote.transact(5, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }
    }

    @Override // android.p009os.Binder
    public int getMaxTransactionId() {
      return 4;
    }
  }
}
