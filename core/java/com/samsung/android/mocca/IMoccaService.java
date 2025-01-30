package com.samsung.android.mocca;

import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;
import java.util.List;

/* loaded from: classes5.dex */
public interface IMoccaService extends IInterface {
  public static final String DESCRIPTOR = "com.samsung.android.mocca.IMoccaService";

  List<String> getSupportedTypes() throws RemoteException;

  boolean hasContextAvailabilityListener(IMoccaEventListener iMoccaEventListener)
      throws RemoteException;

  boolean hasContextListener(IMoccaEventListener iMoccaEventListener) throws RemoteException;

  boolean isAvailableType(String str) throws RemoteException;

  boolean registerContextAvailabilityListener(IMoccaEventListener iMoccaEventListener, String str)
      throws RemoteException;

  boolean registerContextListener(
      IMoccaEventListener iMoccaEventListener, String str, ContextParam contextParam)
      throws RemoteException;

  void unregisterContextAvailabilityListener(IMoccaEventListener iMoccaEventListener, String str)
      throws RemoteException;

  void unregisterContextListener(IMoccaEventListener iMoccaEventListener, String str)
      throws RemoteException;

  public static class Default implements IMoccaService {
    @Override // com.samsung.android.mocca.IMoccaService
    public List<String> getSupportedTypes() throws RemoteException {
      return null;
    }

    @Override // com.samsung.android.mocca.IMoccaService
    public boolean isAvailableType(String context) throws RemoteException {
      return false;
    }

    @Override // com.samsung.android.mocca.IMoccaService
    public boolean registerContextAvailabilityListener(
        IMoccaEventListener listener, String contextType) throws RemoteException {
      return false;
    }

    @Override // com.samsung.android.mocca.IMoccaService
    public void unregisterContextAvailabilityListener(
        IMoccaEventListener listener, String contextType) throws RemoteException {}

    @Override // com.samsung.android.mocca.IMoccaService
    public boolean hasContextAvailabilityListener(IMoccaEventListener listener)
        throws RemoteException {
      return false;
    }

    @Override // com.samsung.android.mocca.IMoccaService
    public boolean registerContextListener(
        IMoccaEventListener listener, String contextType, ContextParam param)
        throws RemoteException {
      return false;
    }

    @Override // com.samsung.android.mocca.IMoccaService
    public void unregisterContextListener(IMoccaEventListener listener, String contextType)
        throws RemoteException {}

    @Override // com.samsung.android.mocca.IMoccaService
    public boolean hasContextListener(IMoccaEventListener listener) throws RemoteException {
      return false;
    }

    @Override // android.p009os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements IMoccaService {
    static final int TRANSACTION_getSupportedTypes = 1;
    static final int TRANSACTION_hasContextAvailabilityListener = 5;
    static final int TRANSACTION_hasContextListener = 8;
    static final int TRANSACTION_isAvailableType = 2;
    static final int TRANSACTION_registerContextAvailabilityListener = 3;
    static final int TRANSACTION_registerContextListener = 6;
    static final int TRANSACTION_unregisterContextAvailabilityListener = 4;
    static final int TRANSACTION_unregisterContextListener = 7;

    public Stub() {
      attachInterface(this, IMoccaService.DESCRIPTOR);
    }

    public static IMoccaService asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(IMoccaService.DESCRIPTOR);
      if (iin != null && (iin instanceof IMoccaService)) {
        return (IMoccaService) iin;
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
          return "getSupportedTypes";
        case 2:
          return "isAvailableType";
        case 3:
          return "registerContextAvailabilityListener";
        case 4:
          return "unregisterContextAvailabilityListener";
        case 5:
          return "hasContextAvailabilityListener";
        case 6:
          return "registerContextListener";
        case 7:
          return "unregisterContextListener";
        case 8:
          return "hasContextListener";
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
        data.enforceInterface(IMoccaService.DESCRIPTOR);
      }
      switch (code) {
        case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
          reply.writeString(IMoccaService.DESCRIPTOR);
          return true;
        default:
          switch (code) {
            case 1:
              List<String> _result = getSupportedTypes();
              reply.writeNoException();
              reply.writeStringList(_result);
              return true;
            case 2:
              String _arg0 = data.readString();
              data.enforceNoDataAvail();
              boolean _result2 = isAvailableType(_arg0);
              reply.writeNoException();
              reply.writeBoolean(_result2);
              return true;
            case 3:
              IMoccaEventListener _arg02 =
                  IMoccaEventListener.Stub.asInterface(data.readStrongBinder());
              String _arg1 = data.readString();
              data.enforceNoDataAvail();
              boolean _result3 = registerContextAvailabilityListener(_arg02, _arg1);
              reply.writeNoException();
              reply.writeBoolean(_result3);
              return true;
            case 4:
              IMoccaEventListener _arg03 =
                  IMoccaEventListener.Stub.asInterface(data.readStrongBinder());
              String _arg12 = data.readString();
              data.enforceNoDataAvail();
              unregisterContextAvailabilityListener(_arg03, _arg12);
              reply.writeNoException();
              return true;
            case 5:
              IMoccaEventListener _arg04 =
                  IMoccaEventListener.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              boolean _result4 = hasContextAvailabilityListener(_arg04);
              reply.writeNoException();
              reply.writeBoolean(_result4);
              return true;
            case 6:
              IMoccaEventListener _arg05 =
                  IMoccaEventListener.Stub.asInterface(data.readStrongBinder());
              String _arg13 = data.readString();
              ContextParam _arg2 = (ContextParam) data.readTypedObject(ContextParam.CREATOR);
              data.enforceNoDataAvail();
              boolean _result5 = registerContextListener(_arg05, _arg13, _arg2);
              reply.writeNoException();
              reply.writeBoolean(_result5);
              return true;
            case 7:
              IMoccaEventListener _arg06 =
                  IMoccaEventListener.Stub.asInterface(data.readStrongBinder());
              String _arg14 = data.readString();
              data.enforceNoDataAvail();
              unregisterContextListener(_arg06, _arg14);
              reply.writeNoException();
              return true;
            case 8:
              IMoccaEventListener _arg07 =
                  IMoccaEventListener.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              boolean _result6 = hasContextListener(_arg07);
              reply.writeNoException();
              reply.writeBoolean(_result6);
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements IMoccaService {
      private IBinder mRemote;

      Proxy(IBinder remote) {
        this.mRemote = remote;
      }

      @Override // android.p009os.IInterface
      public IBinder asBinder() {
        return this.mRemote;
      }

      public String getInterfaceDescriptor() {
        return IMoccaService.DESCRIPTOR;
      }

      @Override // com.samsung.android.mocca.IMoccaService
      public List<String> getSupportedTypes() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IMoccaService.DESCRIPTOR);
          this.mRemote.transact(1, _data, _reply, 0);
          _reply.readException();
          List<String> _result = _reply.createStringArrayList();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.mocca.IMoccaService
      public boolean isAvailableType(String context) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IMoccaService.DESCRIPTOR);
          _data.writeString(context);
          this.mRemote.transact(2, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.mocca.IMoccaService
      public boolean registerContextAvailabilityListener(
          IMoccaEventListener listener, String contextType) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IMoccaService.DESCRIPTOR);
          _data.writeStrongInterface(listener);
          _data.writeString(contextType);
          this.mRemote.transact(3, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.mocca.IMoccaService
      public void unregisterContextAvailabilityListener(
          IMoccaEventListener listener, String contextType) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IMoccaService.DESCRIPTOR);
          _data.writeStrongInterface(listener);
          _data.writeString(contextType);
          this.mRemote.transact(4, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.mocca.IMoccaService
      public boolean hasContextAvailabilityListener(IMoccaEventListener listener)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IMoccaService.DESCRIPTOR);
          _data.writeStrongInterface(listener);
          this.mRemote.transact(5, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.mocca.IMoccaService
      public boolean registerContextListener(
          IMoccaEventListener listener, String contextType, ContextParam param)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IMoccaService.DESCRIPTOR);
          _data.writeStrongInterface(listener);
          _data.writeString(contextType);
          _data.writeTypedObject(param, 0);
          this.mRemote.transact(6, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.mocca.IMoccaService
      public void unregisterContextListener(IMoccaEventListener listener, String contextType)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IMoccaService.DESCRIPTOR);
          _data.writeStrongInterface(listener);
          _data.writeString(contextType);
          this.mRemote.transact(7, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.mocca.IMoccaService
      public boolean hasContextListener(IMoccaEventListener listener) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IMoccaService.DESCRIPTOR);
          _data.writeStrongInterface(listener);
          this.mRemote.transact(8, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }
    }

    @Override // android.p009os.Binder
    public int getMaxTransactionId() {
      return 7;
    }
  }
}
