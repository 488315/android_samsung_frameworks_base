package com.sec.android.smartfpsadjuster;

import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;

/* loaded from: classes6.dex */
public interface IIntelligentDynamicFpsService extends IInterface {
  public static final String DESCRIPTOR =
      "com.sec.android.smartfpsadjuster.IIntelligentDynamicFpsService";

  int cameraPolicyChange(int i) throws RemoteException;

  void cameraPolicyStart() throws RemoteException;

  void cameraPolicyStop() throws RemoteException;

  public static class Default implements IIntelligentDynamicFpsService {
    @Override // com.sec.android.smartfpsadjuster.IIntelligentDynamicFpsService
    public void cameraPolicyStart() throws RemoteException {}

    @Override // com.sec.android.smartfpsadjuster.IIntelligentDynamicFpsService
    public void cameraPolicyStop() throws RemoteException {}

    @Override // com.sec.android.smartfpsadjuster.IIntelligentDynamicFpsService
    public int cameraPolicyChange(int fps) throws RemoteException {
      return 0;
    }

    @Override // android.p009os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements IIntelligentDynamicFpsService {
    static final int TRANSACTION_cameraPolicyChange = 3;
    static final int TRANSACTION_cameraPolicyStart = 1;
    static final int TRANSACTION_cameraPolicyStop = 2;

    public Stub() {
      attachInterface(this, IIntelligentDynamicFpsService.DESCRIPTOR);
    }

    public static IIntelligentDynamicFpsService asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(IIntelligentDynamicFpsService.DESCRIPTOR);
      if (iin != null && (iin instanceof IIntelligentDynamicFpsService)) {
        return (IIntelligentDynamicFpsService) iin;
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
          return "cameraPolicyStart";
        case 2:
          return "cameraPolicyStop";
        case 3:
          return "cameraPolicyChange";
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
        data.enforceInterface(IIntelligentDynamicFpsService.DESCRIPTOR);
      }
      switch (code) {
        case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
          reply.writeString(IIntelligentDynamicFpsService.DESCRIPTOR);
          return true;
        default:
          switch (code) {
            case 1:
              cameraPolicyStart();
              reply.writeNoException();
              return true;
            case 2:
              cameraPolicyStop();
              reply.writeNoException();
              return true;
            case 3:
              int _arg0 = data.readInt();
              data.enforceNoDataAvail();
              int _result = cameraPolicyChange(_arg0);
              reply.writeNoException();
              reply.writeInt(_result);
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements IIntelligentDynamicFpsService {
      private IBinder mRemote;

      Proxy(IBinder remote) {
        this.mRemote = remote;
      }

      @Override // android.p009os.IInterface
      public IBinder asBinder() {
        return this.mRemote;
      }

      public String getInterfaceDescriptor() {
        return IIntelligentDynamicFpsService.DESCRIPTOR;
      }

      @Override // com.sec.android.smartfpsadjuster.IIntelligentDynamicFpsService
      public void cameraPolicyStart() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IIntelligentDynamicFpsService.DESCRIPTOR);
          this.mRemote.transact(1, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.sec.android.smartfpsadjuster.IIntelligentDynamicFpsService
      public void cameraPolicyStop() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IIntelligentDynamicFpsService.DESCRIPTOR);
          this.mRemote.transact(2, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.sec.android.smartfpsadjuster.IIntelligentDynamicFpsService
      public int cameraPolicyChange(int fps) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IIntelligentDynamicFpsService.DESCRIPTOR);
          _data.writeInt(fps);
          this.mRemote.transact(3, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }
    }

    @Override // android.p009os.Binder
    public int getMaxTransactionId() {
      return 2;
    }
  }
}
