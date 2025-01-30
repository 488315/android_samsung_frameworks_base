package android.service.credentials;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface ICredentialProviderService extends IInterface {
  public static final String DESCRIPTOR = "android.service.credentials.ICredentialProviderService";

  void onBeginCreateCredential(
      BeginCreateCredentialRequest beginCreateCredentialRequest,
      IBeginCreateCredentialCallback iBeginCreateCredentialCallback)
      throws RemoteException;

  void onBeginGetCredential(
      BeginGetCredentialRequest beginGetCredentialRequest,
      IBeginGetCredentialCallback iBeginGetCredentialCallback)
      throws RemoteException;

  void onClearCredentialState(
      ClearCredentialStateRequest clearCredentialStateRequest,
      IClearCredentialStateCallback iClearCredentialStateCallback)
      throws RemoteException;

  public static class Default implements ICredentialProviderService {
    @Override // android.service.credentials.ICredentialProviderService
    public void onBeginGetCredential(
        BeginGetCredentialRequest request, IBeginGetCredentialCallback callback)
        throws RemoteException {}

    @Override // android.service.credentials.ICredentialProviderService
    public void onBeginCreateCredential(
        BeginCreateCredentialRequest request, IBeginCreateCredentialCallback callback)
        throws RemoteException {}

    @Override // android.service.credentials.ICredentialProviderService
    public void onClearCredentialState(
        ClearCredentialStateRequest request, IClearCredentialStateCallback callback)
        throws RemoteException {}

    @Override // android.os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements ICredentialProviderService {
    static final int TRANSACTION_onBeginCreateCredential = 2;
    static final int TRANSACTION_onBeginGetCredential = 1;
    static final int TRANSACTION_onClearCredentialState = 3;

    public Stub() {
      attachInterface(this, ICredentialProviderService.DESCRIPTOR);
    }

    public static ICredentialProviderService asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(ICredentialProviderService.DESCRIPTOR);
      if (iin != null && (iin instanceof ICredentialProviderService)) {
        return (ICredentialProviderService) iin;
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
          return "onBeginGetCredential";
        case 2:
          return "onBeginCreateCredential";
        case 3:
          return "onClearCredentialState";
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
        data.enforceInterface(ICredentialProviderService.DESCRIPTOR);
      }
      switch (code) {
        case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
          reply.writeString(ICredentialProviderService.DESCRIPTOR);
          return true;
        default:
          switch (code) {
            case 1:
              BeginGetCredentialRequest _arg0 =
                  (BeginGetCredentialRequest)
                      data.readTypedObject(BeginGetCredentialRequest.CREATOR);
              IBeginGetCredentialCallback _arg1 =
                  IBeginGetCredentialCallback.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              onBeginGetCredential(_arg0, _arg1);
              return true;
            case 2:
              BeginCreateCredentialRequest _arg02 =
                  (BeginCreateCredentialRequest)
                      data.readTypedObject(BeginCreateCredentialRequest.CREATOR);
              IBeginCreateCredentialCallback _arg12 =
                  IBeginCreateCredentialCallback.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              onBeginCreateCredential(_arg02, _arg12);
              return true;
            case 3:
              ClearCredentialStateRequest _arg03 =
                  (ClearCredentialStateRequest)
                      data.readTypedObject(ClearCredentialStateRequest.CREATOR);
              IClearCredentialStateCallback _arg13 =
                  IClearCredentialStateCallback.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              onClearCredentialState(_arg03, _arg13);
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements ICredentialProviderService {
      private IBinder mRemote;

      Proxy(IBinder remote) {
        this.mRemote = remote;
      }

      @Override // android.os.IInterface
      public IBinder asBinder() {
        return this.mRemote;
      }

      public String getInterfaceDescriptor() {
        return ICredentialProviderService.DESCRIPTOR;
      }

      @Override // android.service.credentials.ICredentialProviderService
      public void onBeginGetCredential(
          BeginGetCredentialRequest request, IBeginGetCredentialCallback callback)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(ICredentialProviderService.DESCRIPTOR);
          _data.writeTypedObject(request, 0);
          _data.writeStrongInterface(callback);
          this.mRemote.transact(1, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.service.credentials.ICredentialProviderService
      public void onBeginCreateCredential(
          BeginCreateCredentialRequest request, IBeginCreateCredentialCallback callback)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(ICredentialProviderService.DESCRIPTOR);
          _data.writeTypedObject(request, 0);
          _data.writeStrongInterface(callback);
          this.mRemote.transact(2, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.service.credentials.ICredentialProviderService
      public void onClearCredentialState(
          ClearCredentialStateRequest request, IClearCredentialStateCallback callback)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(ICredentialProviderService.DESCRIPTOR);
          _data.writeTypedObject(request, 0);
          _data.writeStrongInterface(callback);
          this.mRemote.transact(3, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }
    }

    @Override // android.os.Binder
    public int getMaxTransactionId() {
      return 2;
    }
  }
}
