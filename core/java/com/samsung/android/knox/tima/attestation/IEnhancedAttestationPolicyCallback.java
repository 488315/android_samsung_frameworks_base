package com.samsung.android.knox.tima.attestation;

import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;

/* loaded from: classes5.dex */
public interface IEnhancedAttestationPolicyCallback extends IInterface {
  public static final String DESCRIPTOR =
      "com.samsung.android.knox.tima.attestation.IEnhancedAttestationPolicyCallback";

  void onAttestationFinished(EnhancedAttestationResult enhancedAttestationResult)
      throws RemoteException;

  public static class Default implements IEnhancedAttestationPolicyCallback {
    @Override // com.samsung.android.knox.tima.attestation.IEnhancedAttestationPolicyCallback
    public void onAttestationFinished(EnhancedAttestationResult result) throws RemoteException {}

    @Override // android.p009os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements IEnhancedAttestationPolicyCallback {
    static final int TRANSACTION_onAttestationFinished = 1;

    public Stub() {
      attachInterface(this, IEnhancedAttestationPolicyCallback.DESCRIPTOR);
    }

    public static IEnhancedAttestationPolicyCallback asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(IEnhancedAttestationPolicyCallback.DESCRIPTOR);
      if (iin != null && (iin instanceof IEnhancedAttestationPolicyCallback)) {
        return (IEnhancedAttestationPolicyCallback) iin;
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
          return "onAttestationFinished";
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
        data.enforceInterface(IEnhancedAttestationPolicyCallback.DESCRIPTOR);
      }
      switch (code) {
        case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
          reply.writeString(IEnhancedAttestationPolicyCallback.DESCRIPTOR);
          return true;
        default:
          switch (code) {
            case 1:
              EnhancedAttestationResult _arg0 =
                  (EnhancedAttestationResult)
                      data.readTypedObject(EnhancedAttestationResult.CREATOR);
              data.enforceNoDataAvail();
              onAttestationFinished(_arg0);
              reply.writeNoException();
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements IEnhancedAttestationPolicyCallback {
      private IBinder mRemote;

      Proxy(IBinder remote) {
        this.mRemote = remote;
      }

      @Override // android.p009os.IInterface
      public IBinder asBinder() {
        return this.mRemote;
      }

      public String getInterfaceDescriptor() {
        return IEnhancedAttestationPolicyCallback.DESCRIPTOR;
      }

      @Override // com.samsung.android.knox.tima.attestation.IEnhancedAttestationPolicyCallback
      public void onAttestationFinished(EnhancedAttestationResult result) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IEnhancedAttestationPolicyCallback.DESCRIPTOR);
          _data.writeTypedObject(result, 0);
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
