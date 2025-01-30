package android.hardware.display;

import android.p009os.Binder;
import android.p009os.Bundle;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;
import java.util.List;

/* loaded from: classes2.dex */
public interface IDisplayManagerCallback extends IInterface {
  void onAsyncBinderBufferFillEvent(int i, Bundle bundle) throws RemoteException;

  void onDeviceEvent(Bundle bundle, int i) throws RemoteException;

  void onDisplayEvent(int i, int i2) throws RemoteException;

  void onDisplayVolumeEvent(int i, Bundle bundle) throws RemoteException;

  void onDisplayVolumeKeyEvent(int i) throws RemoteException;

  void onWifiDisplayParameterEvent(int i, List<SemWifiDisplayParameter> list)
      throws RemoteException;

  public static class Default implements IDisplayManagerCallback {
    @Override // android.hardware.display.IDisplayManagerCallback
    public void onDisplayEvent(int displayId, int event) throws RemoteException {}

    @Override // android.hardware.display.IDisplayManagerCallback
    public void onDisplayVolumeEvent(int event, Bundle msg) throws RemoteException {}

    @Override // android.hardware.display.IDisplayManagerCallback
    public void onDisplayVolumeKeyEvent(int event) throws RemoteException {}

    @Override // android.hardware.display.IDisplayManagerCallback
    public void onWifiDisplayParameterEvent(int event, List<SemWifiDisplayParameter> parameters)
        throws RemoteException {}

    @Override // android.hardware.display.IDisplayManagerCallback
    public void onDeviceEvent(Bundle msg, int event) throws RemoteException {}

    @Override // android.hardware.display.IDisplayManagerCallback
    public void onAsyncBinderBufferFillEvent(int event, Bundle msg) throws RemoteException {}

    @Override // android.p009os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements IDisplayManagerCallback {
    public static final String DESCRIPTOR = "android.hardware.display.IDisplayManagerCallback";
    static final int TRANSACTION_onAsyncBinderBufferFillEvent = 6;
    static final int TRANSACTION_onDeviceEvent = 5;
    static final int TRANSACTION_onDisplayEvent = 1;
    static final int TRANSACTION_onDisplayVolumeEvent = 2;
    static final int TRANSACTION_onDisplayVolumeKeyEvent = 3;
    static final int TRANSACTION_onWifiDisplayParameterEvent = 4;

    public Stub() {
      attachInterface(this, DESCRIPTOR);
    }

    public static IDisplayManagerCallback asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
      if (iin != null && (iin instanceof IDisplayManagerCallback)) {
        return (IDisplayManagerCallback) iin;
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
          return "onDisplayEvent";
        case 2:
          return "onDisplayVolumeEvent";
        case 3:
          return "onDisplayVolumeKeyEvent";
        case 4:
          return "onWifiDisplayParameterEvent";
        case 5:
          return "onDeviceEvent";
        case 6:
          return "onAsyncBinderBufferFillEvent";
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
        data.enforceInterface(DESCRIPTOR);
      }
      switch (code) {
        case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
          reply.writeString(DESCRIPTOR);
          return true;
        default:
          switch (code) {
            case 1:
              int _arg0 = data.readInt();
              int _arg1 = data.readInt();
              data.enforceNoDataAvail();
              onDisplayEvent(_arg0, _arg1);
              return true;
            case 2:
              int _arg02 = data.readInt();
              Bundle _arg12 = (Bundle) data.readTypedObject(Bundle.CREATOR);
              data.enforceNoDataAvail();
              onDisplayVolumeEvent(_arg02, _arg12);
              return true;
            case 3:
              int _arg03 = data.readInt();
              data.enforceNoDataAvail();
              onDisplayVolumeKeyEvent(_arg03);
              return true;
            case 4:
              int _arg04 = data.readInt();
              List<SemWifiDisplayParameter> _arg13 =
                  data.createTypedArrayList(SemWifiDisplayParameter.CREATOR);
              data.enforceNoDataAvail();
              onWifiDisplayParameterEvent(_arg04, _arg13);
              return true;
            case 5:
              Bundle _arg05 = (Bundle) data.readTypedObject(Bundle.CREATOR);
              int _arg14 = data.readInt();
              data.enforceNoDataAvail();
              onDeviceEvent(_arg05, _arg14);
              return true;
            case 6:
              int _arg06 = data.readInt();
              Bundle _arg15 = (Bundle) data.readTypedObject(Bundle.CREATOR);
              data.enforceNoDataAvail();
              onAsyncBinderBufferFillEvent(_arg06, _arg15);
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements IDisplayManagerCallback {
      private IBinder mRemote;

      Proxy(IBinder remote) {
        this.mRemote = remote;
      }

      @Override // android.p009os.IInterface
      public IBinder asBinder() {
        return this.mRemote;
      }

      public String getInterfaceDescriptor() {
        return Stub.DESCRIPTOR;
      }

      @Override // android.hardware.display.IDisplayManagerCallback
      public void onDisplayEvent(int displayId, int event) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(displayId);
          _data.writeInt(event);
          this.mRemote.transact(1, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.hardware.display.IDisplayManagerCallback
      public void onDisplayVolumeEvent(int event, Bundle msg) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(event);
          _data.writeTypedObject(msg, 0);
          this.mRemote.transact(2, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.hardware.display.IDisplayManagerCallback
      public void onDisplayVolumeKeyEvent(int event) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(event);
          this.mRemote.transact(3, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.hardware.display.IDisplayManagerCallback
      public void onWifiDisplayParameterEvent(int event, List<SemWifiDisplayParameter> parameters)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(event);
          _data.writeTypedList(parameters, 0);
          this.mRemote.transact(4, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.hardware.display.IDisplayManagerCallback
      public void onDeviceEvent(Bundle msg, int event) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeTypedObject(msg, 0);
          _data.writeInt(event);
          this.mRemote.transact(5, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.hardware.display.IDisplayManagerCallback
      public void onAsyncBinderBufferFillEvent(int event, Bundle msg) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(event);
          _data.writeTypedObject(msg, 0);
          this.mRemote.transact(6, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }
    }

    @Override // android.p009os.Binder
    public int getMaxTransactionId() {
      return 5;
    }
  }
}
