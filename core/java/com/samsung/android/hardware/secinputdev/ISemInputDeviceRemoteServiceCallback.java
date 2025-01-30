package com.samsung.android.hardware.secinputdev;

import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;

/* loaded from: classes5.dex */
public interface ISemInputDeviceRemoteServiceCallback extends IInterface {
  public static final String DESCRIPTOR =
      "com.samsung.android.hardware.secinputdev.ISemInputDeviceRemoteServiceCallback";

  void deliveryLastData(int[] iArr, float f) throws RemoteException;

  void deliveryRawdata(int[] iArr) throws RemoteException;

  public static class Default implements ISemInputDeviceRemoteServiceCallback {
    @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceRemoteServiceCallback
    public void deliveryRawdata(int[] map) throws RemoteException {}

    @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceRemoteServiceCallback
    public void deliveryLastData(int[] croppedVideoClip, float result) throws RemoteException {}

    @Override // android.p009os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements ISemInputDeviceRemoteServiceCallback {
    static final int TRANSACTION_deliveryLastData = 2;
    static final int TRANSACTION_deliveryRawdata = 1;

    public Stub() {
      attachInterface(this, ISemInputDeviceRemoteServiceCallback.DESCRIPTOR);
    }

    public static ISemInputDeviceRemoteServiceCallback asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(ISemInputDeviceRemoteServiceCallback.DESCRIPTOR);
      if (iin != null && (iin instanceof ISemInputDeviceRemoteServiceCallback)) {
        return (ISemInputDeviceRemoteServiceCallback) iin;
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
          return "deliveryRawdata";
        case 2:
          return "deliveryLastData";
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
        data.enforceInterface(ISemInputDeviceRemoteServiceCallback.DESCRIPTOR);
      }
      switch (code) {
        case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
          reply.writeString(ISemInputDeviceRemoteServiceCallback.DESCRIPTOR);
          return true;
        default:
          switch (code) {
            case 1:
              int[] _arg0 = data.createIntArray();
              data.enforceNoDataAvail();
              deliveryRawdata(_arg0);
              return true;
            case 2:
              int[] _arg02 = data.createIntArray();
              float _arg1 = data.readFloat();
              data.enforceNoDataAvail();
              deliveryLastData(_arg02, _arg1);
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements ISemInputDeviceRemoteServiceCallback {
      private IBinder mRemote;

      Proxy(IBinder remote) {
        this.mRemote = remote;
      }

      @Override // android.p009os.IInterface
      public IBinder asBinder() {
        return this.mRemote;
      }

      public String getInterfaceDescriptor() {
        return ISemInputDeviceRemoteServiceCallback.DESCRIPTOR;
      }

      @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceRemoteServiceCallback
      public void deliveryRawdata(int[] map) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(ISemInputDeviceRemoteServiceCallback.DESCRIPTOR);
          _data.writeIntArray(map);
          this.mRemote.transact(1, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceRemoteServiceCallback
      public void deliveryLastData(int[] croppedVideoClip, float result) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(ISemInputDeviceRemoteServiceCallback.DESCRIPTOR);
          _data.writeIntArray(croppedVideoClip);
          _data.writeFloat(result);
          this.mRemote.transact(2, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }
    }

    @Override // android.p009os.Binder
    public int getMaxTransactionId() {
      return 1;
    }
  }
}
