package com.samsung.android.chimera;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import java.util.List;

/* loaded from: classes5.dex */
public interface IChimera extends IInterface {
  public static final String DESCRIPTOR = "com.samsung.android.chimera.IChimera";

  List<PSIAvailableMem> getAvailableMemInfo(long j, long j2) throws RemoteException;

  public static class Default implements IChimera {
    @Override // com.samsung.android.chimera.IChimera
    public List<PSIAvailableMem> getAvailableMemInfo(long startTime, long endTime)
        throws RemoteException {
      return null;
    }

    @Override // android.os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements IChimera {
    static final int TRANSACTION_getAvailableMemInfo = 1;

    public Stub() {
      attachInterface(this, IChimera.DESCRIPTOR);
    }

    public static IChimera asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(IChimera.DESCRIPTOR);
      if (iin != null && (iin instanceof IChimera)) {
        return (IChimera) iin;
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
          return "getAvailableMemInfo";
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
        data.enforceInterface(IChimera.DESCRIPTOR);
      }
      switch (code) {
        case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
          reply.writeString(IChimera.DESCRIPTOR);
          return true;
        default:
          switch (code) {
            case 1:
              long _arg0 = data.readLong();
              long _arg1 = data.readLong();
              data.enforceNoDataAvail();
              List<PSIAvailableMem> _result = getAvailableMemInfo(_arg0, _arg1);
              reply.writeNoException();
              reply.writeTypedList(_result, 1);
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements IChimera {
      private IBinder mRemote;

      Proxy(IBinder remote) {
        this.mRemote = remote;
      }

      @Override // android.os.IInterface
      public IBinder asBinder() {
        return this.mRemote;
      }

      public String getInterfaceDescriptor() {
        return IChimera.DESCRIPTOR;
      }

      @Override // com.samsung.android.chimera.IChimera
      public List<PSIAvailableMem> getAvailableMemInfo(long startTime, long endTime)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IChimera.DESCRIPTOR);
          _data.writeLong(startTime);
          _data.writeLong(endTime);
          this.mRemote.transact(1, _data, _reply, 0);
          _reply.readException();
          List<PSIAvailableMem> _result = _reply.createTypedArrayList(PSIAvailableMem.CREATOR);
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
