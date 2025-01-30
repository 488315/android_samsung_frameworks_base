package com.samsung.android.knox.tima.attestation;

import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;

/* loaded from: classes5.dex */
public interface IEnhancedAttestation extends IInterface {
  public static final String DESCRIPTOR =
      "com.samsung.android.knox.tima.attestation.IEnhancedAttestation";

  void enhancedAttestation(
      String str,
      String str2,
      IEnhancedAttestationPolicyCallback iEnhancedAttestationPolicyCallback,
      boolean z)
      throws RemoteException;

  public static class Default implements IEnhancedAttestation {
    @Override // com.samsung.android.knox.tima.attestation.IEnhancedAttestation
    public void enhancedAttestation(
        String nonce, String auk, IEnhancedAttestationPolicyCallback cb, boolean onPrem)
        throws RemoteException {}

    @Override // android.p009os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements IEnhancedAttestation {
    static final int TRANSACTION_enhancedAttestation = 1;

    public Stub() {
      attachInterface(this, IEnhancedAttestation.DESCRIPTOR);
    }

    public static IEnhancedAttestation asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(IEnhancedAttestation.DESCRIPTOR);
      if (iin != null && (iin instanceof IEnhancedAttestation)) {
        return (IEnhancedAttestation) iin;
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
          return "enhancedAttestation";
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
        data.enforceInterface(IEnhancedAttestation.DESCRIPTOR);
      }
      switch (code) {
        case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
          reply.writeString(IEnhancedAttestation.DESCRIPTOR);
          return true;
        default:
          switch (code) {
            case 1:
              String _arg0 = data.readString();
              String _arg1 = data.readString();
              IEnhancedAttestationPolicyCallback _arg2 =
                  IEnhancedAttestationPolicyCallback.Stub.asInterface(data.readStrongBinder());
              boolean _arg3 = data.readBoolean();
              data.enforceNoDataAvail();
              enhancedAttestation(_arg0, _arg1, _arg2, _arg3);
              reply.writeNoException();
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements IEnhancedAttestation {
      private IBinder mRemote;

      Proxy(IBinder remote) {
        this.mRemote = remote;
      }

      @Override // android.p009os.IInterface
      public IBinder asBinder() {
        return this.mRemote;
      }

      public String getInterfaceDescriptor() {
        return IEnhancedAttestation.DESCRIPTOR;
      }

      @Override // com.samsung.android.knox.tima.attestation.IEnhancedAttestation
      public void enhancedAttestation(
          String nonce, String auk, IEnhancedAttestationPolicyCallback cb, boolean onPrem)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IEnhancedAttestation.DESCRIPTOR);
          _data.writeString(nonce);
          _data.writeString(auk);
          _data.writeStrongInterface(cb);
          _data.writeBoolean(onPrem);
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
