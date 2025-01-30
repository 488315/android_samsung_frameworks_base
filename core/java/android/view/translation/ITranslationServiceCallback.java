package android.view.translation;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes4.dex */
public interface ITranslationServiceCallback extends IInterface {
  public static final String DESCRIPTOR = "android.view.translation.ITranslationServiceCallback";

  void updateTranslationCapability(TranslationCapability translationCapability)
      throws RemoteException;

  public static class Default implements ITranslationServiceCallback {
    @Override // android.view.translation.ITranslationServiceCallback
    public void updateTranslationCapability(TranslationCapability capability)
        throws RemoteException {}

    @Override // android.os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements ITranslationServiceCallback {
    static final int TRANSACTION_updateTranslationCapability = 1;

    public Stub() {
      attachInterface(this, ITranslationServiceCallback.DESCRIPTOR);
    }

    public static ITranslationServiceCallback asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(ITranslationServiceCallback.DESCRIPTOR);
      if (iin != null && (iin instanceof ITranslationServiceCallback)) {
        return (ITranslationServiceCallback) iin;
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
          return "updateTranslationCapability";
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
        data.enforceInterface(ITranslationServiceCallback.DESCRIPTOR);
      }
      switch (code) {
        case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
          reply.writeString(ITranslationServiceCallback.DESCRIPTOR);
          return true;
        default:
          switch (code) {
            case 1:
              TranslationCapability _arg0 =
                  (TranslationCapability) data.readTypedObject(TranslationCapability.CREATOR);
              data.enforceNoDataAvail();
              updateTranslationCapability(_arg0);
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements ITranslationServiceCallback {
      private IBinder mRemote;

      Proxy(IBinder remote) {
        this.mRemote = remote;
      }

      @Override // android.os.IInterface
      public IBinder asBinder() {
        return this.mRemote;
      }

      public String getInterfaceDescriptor() {
        return ITranslationServiceCallback.DESCRIPTOR;
      }

      @Override // android.view.translation.ITranslationServiceCallback
      public void updateTranslationCapability(TranslationCapability capability)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(ITranslationServiceCallback.DESCRIPTOR);
          _data.writeTypedObject(capability, 0);
          this.mRemote.transact(1, _data, null, 1);
        } finally {
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
