package com.samsung.android.knox.knoxai;

import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;

/* loaded from: classes5.dex */
public interface IKnoxAiManagerService extends IInterface {
  public static final String DESCRIPTOR = "com.samsung.android.knox.knoxai.IKnoxAiManagerService";

  int initializeTaSession(TaLoaderOptions taLoaderOptions) throws RemoteException;

  void processTaCommand(
      int i, TaProcessBuffer[] taProcessBufferArr, TaProcessBuffer[] taProcessBufferArr2)
      throws RemoteException;

  int terminateTaSession() throws RemoteException;

  public static class Default implements IKnoxAiManagerService {
    @Override // com.samsung.android.knox.knoxai.IKnoxAiManagerService
    public int initializeTaSession(TaLoaderOptions options) throws RemoteException {
      return 0;
    }

    @Override // com.samsung.android.knox.knoxai.IKnoxAiManagerService
    public int terminateTaSession() throws RemoteException {
      return 0;
    }

    @Override // com.samsung.android.knox.knoxai.IKnoxAiManagerService
    public void processTaCommand(int command, TaProcessBuffer[] input, TaProcessBuffer[] output)
        throws RemoteException {}

    @Override // android.p009os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements IKnoxAiManagerService {
    static final int TRANSACTION_initializeTaSession = 1;
    static final int TRANSACTION_processTaCommand = 3;
    static final int TRANSACTION_terminateTaSession = 2;

    public Stub() {
      attachInterface(this, IKnoxAiManagerService.DESCRIPTOR);
    }

    public static IKnoxAiManagerService asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(IKnoxAiManagerService.DESCRIPTOR);
      if (iin != null && (iin instanceof IKnoxAiManagerService)) {
        return (IKnoxAiManagerService) iin;
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
          return "initializeTaSession";
        case 2:
          return "terminateTaSession";
        case 3:
          return "processTaCommand";
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
        data.enforceInterface(IKnoxAiManagerService.DESCRIPTOR);
      }
      switch (code) {
        case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
          reply.writeString(IKnoxAiManagerService.DESCRIPTOR);
          return true;
        default:
          switch (code) {
            case 1:
              TaLoaderOptions _arg0 =
                  (TaLoaderOptions) data.readTypedObject(TaLoaderOptions.CREATOR);
              data.enforceNoDataAvail();
              int _result = initializeTaSession(_arg0);
              reply.writeNoException();
              reply.writeInt(_result);
              return true;
            case 2:
              int _result2 = terminateTaSession();
              reply.writeNoException();
              reply.writeInt(_result2);
              return true;
            case 3:
              int _arg02 = data.readInt();
              TaProcessBuffer[] _arg1 =
                  (TaProcessBuffer[]) data.createTypedArray(TaProcessBuffer.CREATOR);
              TaProcessBuffer[] _arg2 =
                  (TaProcessBuffer[]) data.createTypedArray(TaProcessBuffer.CREATOR);
              data.enforceNoDataAvail();
              processTaCommand(_arg02, _arg1, _arg2);
              reply.writeNoException();
              reply.writeTypedArray(_arg2, 1);
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements IKnoxAiManagerService {
      private IBinder mRemote;

      Proxy(IBinder remote) {
        this.mRemote = remote;
      }

      @Override // android.p009os.IInterface
      public IBinder asBinder() {
        return this.mRemote;
      }

      public String getInterfaceDescriptor() {
        return IKnoxAiManagerService.DESCRIPTOR;
      }

      @Override // com.samsung.android.knox.knoxai.IKnoxAiManagerService
      public int initializeTaSession(TaLoaderOptions options) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IKnoxAiManagerService.DESCRIPTOR);
          _data.writeTypedObject(options, 0);
          this.mRemote.transact(1, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.knox.knoxai.IKnoxAiManagerService
      public int terminateTaSession() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IKnoxAiManagerService.DESCRIPTOR);
          this.mRemote.transact(2, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.knox.knoxai.IKnoxAiManagerService
      public void processTaCommand(int command, TaProcessBuffer[] input, TaProcessBuffer[] output)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IKnoxAiManagerService.DESCRIPTOR);
          _data.writeInt(command);
          _data.writeTypedArray(input, 0);
          _data.writeTypedArray(output, 0);
          this.mRemote.transact(3, _data, _reply, 0);
          _reply.readException();
          _reply.readTypedArray(output, TaProcessBuffer.CREATOR);
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
