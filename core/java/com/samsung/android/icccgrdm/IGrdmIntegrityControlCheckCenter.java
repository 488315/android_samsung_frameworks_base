package com.samsung.android.icccgrdm;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes5.dex */
public interface IGrdmIntegrityControlCheckCenter extends IInterface {
  public static final String DESCRIPTOR =
      "com.samsung.android.icccgrdm.IGrdmIntegrityControlCheckCenter";

  byte[] grdmSetAttestationData(byte[] bArr) throws RemoteException;

  public static class Default implements IGrdmIntegrityControlCheckCenter {
    @Override // com.samsung.android.icccgrdm.IGrdmIntegrityControlCheckCenter
    public byte[] grdmSetAttestationData(byte[] blob) throws RemoteException {
      return null;
    }

    @Override // android.os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements IGrdmIntegrityControlCheckCenter {
    static final int TRANSACTION_grdmSetAttestationData = 1;

    public Stub() {
      attachInterface(this, IGrdmIntegrityControlCheckCenter.DESCRIPTOR);
    }

    public static IGrdmIntegrityControlCheckCenter asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(IGrdmIntegrityControlCheckCenter.DESCRIPTOR);
      if (iin != null && (iin instanceof IGrdmIntegrityControlCheckCenter)) {
        return (IGrdmIntegrityControlCheckCenter) iin;
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
          return "grdmSetAttestationData";
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
        data.enforceInterface(IGrdmIntegrityControlCheckCenter.DESCRIPTOR);
      }
      switch (code) {
        case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
          reply.writeString(IGrdmIntegrityControlCheckCenter.DESCRIPTOR);
          return true;
        default:
          switch (code) {
            case 1:
              byte[] _arg0 = data.createByteArray();
              data.enforceNoDataAvail();
              byte[] _result = grdmSetAttestationData(_arg0);
              reply.writeNoException();
              reply.writeByteArray(_result);
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements IGrdmIntegrityControlCheckCenter {
      private IBinder mRemote;

      Proxy(IBinder remote) {
        this.mRemote = remote;
      }

      @Override // android.os.IInterface
      public IBinder asBinder() {
        return this.mRemote;
      }

      public String getInterfaceDescriptor() {
        return IGrdmIntegrityControlCheckCenter.DESCRIPTOR;
      }

      @Override // com.samsung.android.icccgrdm.IGrdmIntegrityControlCheckCenter
      public byte[] grdmSetAttestationData(byte[] blob) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IGrdmIntegrityControlCheckCenter.DESCRIPTOR);
          _data.writeByteArray(blob);
          this.mRemote.transact(1, _data, _reply, 0);
          _reply.readException();
          byte[] _result = _reply.createByteArray();
          return _result;
        } finally {
          _reply.recycle();
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
