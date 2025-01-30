package com.samsung.android.infoextraction;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import java.util.List;

/* loaded from: classes5.dex */
public interface IKerykeionCallBack extends IInterface {
  public static final String DESCRIPTOR = "com.samsung.android.infoextraction.IKerykeionCallBack";

  void onCompleted(List<SemExtractedInfo> list) throws RemoteException;

  public static class Default implements IKerykeionCallBack {
    @Override // com.samsung.android.infoextraction.IKerykeionCallBack
    public void onCompleted(List<SemExtractedInfo> results) throws RemoteException {}

    @Override // android.os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements IKerykeionCallBack {
    static final int TRANSACTION_onCompleted = 1;

    public Stub() {
      attachInterface(this, IKerykeionCallBack.DESCRIPTOR);
    }

    public static IKerykeionCallBack asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(IKerykeionCallBack.DESCRIPTOR);
      if (iin != null && (iin instanceof IKerykeionCallBack)) {
        return (IKerykeionCallBack) iin;
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
          return "onCompleted";
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
        data.enforceInterface(IKerykeionCallBack.DESCRIPTOR);
      }
      switch (code) {
        case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
          reply.writeString(IKerykeionCallBack.DESCRIPTOR);
          return true;
        default:
          switch (code) {
            case 1:
              List<SemExtractedInfo> _arg0 = data.createTypedArrayList(SemExtractedInfo.CREATOR);
              data.enforceNoDataAvail();
              onCompleted(_arg0);
              reply.writeNoException();
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements IKerykeionCallBack {
      private IBinder mRemote;

      Proxy(IBinder remote) {
        this.mRemote = remote;
      }

      @Override // android.os.IInterface
      public IBinder asBinder() {
        return this.mRemote;
      }

      public String getInterfaceDescriptor() {
        return IKerykeionCallBack.DESCRIPTOR;
      }

      @Override // com.samsung.android.infoextraction.IKerykeionCallBack
      public void onCompleted(List<SemExtractedInfo> results) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IKerykeionCallBack.DESCRIPTOR);
          _data.writeTypedList(results, 0);
          this.mRemote.transact(1, _data, _reply, 0);
          _reply.readException();
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
