package android.service.autofill;

import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;

/* loaded from: classes3.dex */
public interface IInlineSuggestionUi extends IInterface {
  public static final String DESCRIPTOR = "android.service.autofill.IInlineSuggestionUi";

  void getSurfacePackage(ISurfacePackageResultCallback iSurfacePackageResultCallback)
      throws RemoteException;

  void releaseSurfaceControlViewHost() throws RemoteException;

  public static class Default implements IInlineSuggestionUi {
    @Override // android.service.autofill.IInlineSuggestionUi
    public void getSurfacePackage(ISurfacePackageResultCallback callback) throws RemoteException {}

    @Override // android.service.autofill.IInlineSuggestionUi
    public void releaseSurfaceControlViewHost() throws RemoteException {}

    @Override // android.p009os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements IInlineSuggestionUi {
    static final int TRANSACTION_getSurfacePackage = 1;
    static final int TRANSACTION_releaseSurfaceControlViewHost = 2;

    public Stub() {
      attachInterface(this, IInlineSuggestionUi.DESCRIPTOR);
    }

    public static IInlineSuggestionUi asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(IInlineSuggestionUi.DESCRIPTOR);
      if (iin != null && (iin instanceof IInlineSuggestionUi)) {
        return (IInlineSuggestionUi) iin;
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
          return "getSurfacePackage";
        case 2:
          return "releaseSurfaceControlViewHost";
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
        data.enforceInterface(IInlineSuggestionUi.DESCRIPTOR);
      }
      switch (code) {
        case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
          reply.writeString(IInlineSuggestionUi.DESCRIPTOR);
          return true;
        default:
          switch (code) {
            case 1:
              ISurfacePackageResultCallback _arg0 =
                  ISurfacePackageResultCallback.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              getSurfacePackage(_arg0);
              return true;
            case 2:
              releaseSurfaceControlViewHost();
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements IInlineSuggestionUi {
      private IBinder mRemote;

      Proxy(IBinder remote) {
        this.mRemote = remote;
      }

      @Override // android.p009os.IInterface
      public IBinder asBinder() {
        return this.mRemote;
      }

      public String getInterfaceDescriptor() {
        return IInlineSuggestionUi.DESCRIPTOR;
      }

      @Override // android.service.autofill.IInlineSuggestionUi
      public void getSurfacePackage(ISurfacePackageResultCallback callback) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(IInlineSuggestionUi.DESCRIPTOR);
          _data.writeStrongInterface(callback);
          this.mRemote.transact(1, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.service.autofill.IInlineSuggestionUi
      public void releaseSurfaceControlViewHost() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(IInlineSuggestionUi.DESCRIPTOR);
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
