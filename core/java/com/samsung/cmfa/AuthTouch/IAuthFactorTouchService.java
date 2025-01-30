package com.samsung.cmfa.AuthTouch;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.view.MotionEvent;

/* loaded from: classes6.dex */
public interface IAuthFactorTouchService extends IInterface {
  public static final String DESCRIPTOR = "com.samsung.cmfa.AuthTouch.IAuthFactorTouchService";

  void onPointerEvent(MotionEvent motionEvent) throws RemoteException;

  void registerAuthTouchEnableListener(IAuthTouchEnableListener iAuthTouchEnableListener)
      throws RemoteException;

  void registerAuthTouchEventListener(IAuthTouchEventListener iAuthTouchEventListener)
      throws RemoteException;

  void setTouchEvent(boolean z, boolean z2) throws RemoteException;

  void unregisterAuthTouchEnableListener(IAuthTouchEnableListener iAuthTouchEnableListener)
      throws RemoteException;

  void unregisterAuthTouchEventListener(IAuthTouchEventListener iAuthTouchEventListener)
      throws RemoteException;

  public static class Default implements IAuthFactorTouchService {
    @Override // com.samsung.cmfa.AuthTouch.IAuthFactorTouchService
    public void registerAuthTouchEnableListener(IAuthTouchEnableListener listener)
        throws RemoteException {}

    @Override // com.samsung.cmfa.AuthTouch.IAuthFactorTouchService
    public void unregisterAuthTouchEnableListener(IAuthTouchEnableListener listener)
        throws RemoteException {}

    @Override // com.samsung.cmfa.AuthTouch.IAuthFactorTouchService
    public void registerAuthTouchEventListener(IAuthTouchEventListener listener)
        throws RemoteException {}

    @Override // com.samsung.cmfa.AuthTouch.IAuthFactorTouchService
    public void unregisterAuthTouchEventListener(IAuthTouchEventListener listener)
        throws RemoteException {}

    @Override // com.samsung.cmfa.AuthTouch.IAuthFactorTouchService
    public void setTouchEvent(boolean ret, boolean debugMode) throws RemoteException {}

    @Override // com.samsung.cmfa.AuthTouch.IAuthFactorTouchService
    public void onPointerEvent(MotionEvent event) throws RemoteException {}

    @Override // android.os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements IAuthFactorTouchService {
    static final int TRANSACTION_onPointerEvent = 6;
    static final int TRANSACTION_registerAuthTouchEnableListener = 1;
    static final int TRANSACTION_registerAuthTouchEventListener = 3;
    static final int TRANSACTION_setTouchEvent = 5;
    static final int TRANSACTION_unregisterAuthTouchEnableListener = 2;
    static final int TRANSACTION_unregisterAuthTouchEventListener = 4;

    public Stub() {
      attachInterface(this, IAuthFactorTouchService.DESCRIPTOR);
    }

    public static IAuthFactorTouchService asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(IAuthFactorTouchService.DESCRIPTOR);
      if (iin != null && (iin instanceof IAuthFactorTouchService)) {
        return (IAuthFactorTouchService) iin;
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
          return "registerAuthTouchEnableListener";
        case 2:
          return "unregisterAuthTouchEnableListener";
        case 3:
          return "registerAuthTouchEventListener";
        case 4:
          return "unregisterAuthTouchEventListener";
        case 5:
          return "setTouchEvent";
        case 6:
          return "onPointerEvent";
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
        data.enforceInterface(IAuthFactorTouchService.DESCRIPTOR);
      }
      switch (code) {
        case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
          reply.writeString(IAuthFactorTouchService.DESCRIPTOR);
          return true;
        default:
          switch (code) {
            case 1:
              IAuthTouchEnableListener _arg0 =
                  IAuthTouchEnableListener.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              registerAuthTouchEnableListener(_arg0);
              reply.writeNoException();
              return true;
            case 2:
              IAuthTouchEnableListener _arg02 =
                  IAuthTouchEnableListener.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              unregisterAuthTouchEnableListener(_arg02);
              reply.writeNoException();
              return true;
            case 3:
              IAuthTouchEventListener _arg03 =
                  IAuthTouchEventListener.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              registerAuthTouchEventListener(_arg03);
              reply.writeNoException();
              return true;
            case 4:
              IAuthTouchEventListener _arg04 =
                  IAuthTouchEventListener.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              unregisterAuthTouchEventListener(_arg04);
              reply.writeNoException();
              return true;
            case 5:
              boolean _arg05 = data.readBoolean();
              boolean _arg1 = data.readBoolean();
              data.enforceNoDataAvail();
              setTouchEvent(_arg05, _arg1);
              reply.writeNoException();
              return true;
            case 6:
              MotionEvent _arg06 = (MotionEvent) data.readTypedObject(MotionEvent.CREATOR);
              data.enforceNoDataAvail();
              onPointerEvent(_arg06);
              reply.writeNoException();
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements IAuthFactorTouchService {
      private IBinder mRemote;

      Proxy(IBinder remote) {
        this.mRemote = remote;
      }

      @Override // android.os.IInterface
      public IBinder asBinder() {
        return this.mRemote;
      }

      public String getInterfaceDescriptor() {
        return IAuthFactorTouchService.DESCRIPTOR;
      }

      @Override // com.samsung.cmfa.AuthTouch.IAuthFactorTouchService
      public void registerAuthTouchEnableListener(IAuthTouchEnableListener listener)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IAuthFactorTouchService.DESCRIPTOR);
          _data.writeStrongInterface(listener);
          this.mRemote.transact(1, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.cmfa.AuthTouch.IAuthFactorTouchService
      public void unregisterAuthTouchEnableListener(IAuthTouchEnableListener listener)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IAuthFactorTouchService.DESCRIPTOR);
          _data.writeStrongInterface(listener);
          this.mRemote.transact(2, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.cmfa.AuthTouch.IAuthFactorTouchService
      public void registerAuthTouchEventListener(IAuthTouchEventListener listener)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IAuthFactorTouchService.DESCRIPTOR);
          _data.writeStrongInterface(listener);
          this.mRemote.transact(3, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.cmfa.AuthTouch.IAuthFactorTouchService
      public void unregisterAuthTouchEventListener(IAuthTouchEventListener listener)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IAuthFactorTouchService.DESCRIPTOR);
          _data.writeStrongInterface(listener);
          this.mRemote.transact(4, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.cmfa.AuthTouch.IAuthFactorTouchService
      public void setTouchEvent(boolean ret, boolean debugMode) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IAuthFactorTouchService.DESCRIPTOR);
          _data.writeBoolean(ret);
          _data.writeBoolean(debugMode);
          this.mRemote.transact(5, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.cmfa.AuthTouch.IAuthFactorTouchService
      public void onPointerEvent(MotionEvent event) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IAuthFactorTouchService.DESCRIPTOR);
          _data.writeTypedObject(event, 0);
          this.mRemote.transact(6, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }
    }

    @Override // android.os.Binder
    public int getMaxTransactionId() {
      return 5;
    }
  }
}
