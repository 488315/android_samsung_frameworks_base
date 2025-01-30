package com.samsung.android.camera.iris;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes5.dex */
public interface IIrisDaemonCallback extends IInterface {
  public static final String DESCRIPTOR = "com.samsung.android.camera.iris.IIrisDaemonCallback";

  void onAcquired(long j, EyeInfo eyeInfo) throws RemoteException;

  void onAuthenticated(long j, int i, int i2, byte[] bArr, byte[] bArr2) throws RemoteException;

  void onEnrollResult(long j, int i, int i2, int i3) throws RemoteException;

  void onEnumerate(long j, int[] iArr, int[] iArr2) throws RemoteException;

  void onError(long j, int i) throws RemoteException;

  void onGeneralParameterChanged(long j, String str, String str2) throws RemoteException;

  void onIRPropertyChanged(long j, String str, String str2) throws RemoteException;

  void onImageProcessed(long j, int i, byte[] bArr, int i2, int i3) throws RemoteException;

  void onRemoved(long j, int i, int i2) throws RemoteException;

  public static class Default implements IIrisDaemonCallback {
    @Override // com.samsung.android.camera.iris.IIrisDaemonCallback
    public void onEnrollResult(long deviceId, int irisId, int groupId, int remaining)
        throws RemoteException {}

    @Override // com.samsung.android.camera.iris.IIrisDaemonCallback
    public void onAcquired(long deviceId, EyeInfo eyeInfo) throws RemoteException {}

    @Override // com.samsung.android.camera.iris.IIrisDaemonCallback
    public void onAuthenticated(
        long deviceId, int irisId, int groupId, byte[] hatData, byte[] fidoResultData)
        throws RemoteException {}

    @Override // com.samsung.android.camera.iris.IIrisDaemonCallback
    public void onError(long deviceId, int error) throws RemoteException {}

    @Override // com.samsung.android.camera.iris.IIrisDaemonCallback
    public void onRemoved(long deviceId, int irisId, int groupId) throws RemoteException {}

    @Override // com.samsung.android.camera.iris.IIrisDaemonCallback
    public void onIRPropertyChanged(long deviceId, String key, String value)
        throws RemoteException {}

    @Override // com.samsung.android.camera.iris.IIrisDaemonCallback
    public void onEnumerate(long deviceId, int[] irisIds, int[] groupIds) throws RemoteException {}

    @Override // com.samsung.android.camera.iris.IIrisDaemonCallback
    public void onImageProcessed(
        long deviceId, int imageId, byte[] previewImage, int width, int height)
        throws RemoteException {}

    @Override // com.samsung.android.camera.iris.IIrisDaemonCallback
    public void onGeneralParameterChanged(long deviceId, String key, String value)
        throws RemoteException {}

    @Override // android.os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements IIrisDaemonCallback {
    static final int TRANSACTION_onAcquired = 2;
    static final int TRANSACTION_onAuthenticated = 3;
    static final int TRANSACTION_onEnrollResult = 1;
    static final int TRANSACTION_onEnumerate = 7;
    static final int TRANSACTION_onError = 4;
    static final int TRANSACTION_onGeneralParameterChanged = 9;
    static final int TRANSACTION_onIRPropertyChanged = 6;
    static final int TRANSACTION_onImageProcessed = 8;
    static final int TRANSACTION_onRemoved = 5;

    public Stub() {
      attachInterface(this, IIrisDaemonCallback.DESCRIPTOR);
    }

    public static IIrisDaemonCallback asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(IIrisDaemonCallback.DESCRIPTOR);
      if (iin != null && (iin instanceof IIrisDaemonCallback)) {
        return (IIrisDaemonCallback) iin;
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
          return "onEnrollResult";
        case 2:
          return "onAcquired";
        case 3:
          return "onAuthenticated";
        case 4:
          return "onError";
        case 5:
          return "onRemoved";
        case 6:
          return "onIRPropertyChanged";
        case 7:
          return "onEnumerate";
        case 8:
          return "onImageProcessed";
        case 9:
          return "onGeneralParameterChanged";
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
        data.enforceInterface(IIrisDaemonCallback.DESCRIPTOR);
      }
      switch (code) {
        case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
          reply.writeString(IIrisDaemonCallback.DESCRIPTOR);
          return true;
        default:
          switch (code) {
            case 1:
              long _arg0 = data.readLong();
              int _arg1 = data.readInt();
              int _arg2 = data.readInt();
              int _arg3 = data.readInt();
              data.enforceNoDataAvail();
              onEnrollResult(_arg0, _arg1, _arg2, _arg3);
              reply.writeNoException();
              return true;
            case 2:
              long _arg02 = data.readLong();
              EyeInfo _arg12 = (EyeInfo) data.readTypedObject(EyeInfo.CREATOR);
              data.enforceNoDataAvail();
              onAcquired(_arg02, _arg12);
              reply.writeNoException();
              return true;
            case 3:
              long _arg03 = data.readLong();
              int _arg13 = data.readInt();
              int _arg22 = data.readInt();
              byte[] _arg32 = data.createByteArray();
              byte[] _arg4 = data.createByteArray();
              data.enforceNoDataAvail();
              onAuthenticated(_arg03, _arg13, _arg22, _arg32, _arg4);
              reply.writeNoException();
              return true;
            case 4:
              long _arg04 = data.readLong();
              int _arg14 = data.readInt();
              data.enforceNoDataAvail();
              onError(_arg04, _arg14);
              reply.writeNoException();
              return true;
            case 5:
              long _arg05 = data.readLong();
              int _arg15 = data.readInt();
              int _arg23 = data.readInt();
              data.enforceNoDataAvail();
              onRemoved(_arg05, _arg15, _arg23);
              reply.writeNoException();
              return true;
            case 6:
              long _arg06 = data.readLong();
              String _arg16 = data.readString();
              String _arg24 = data.readString();
              data.enforceNoDataAvail();
              onIRPropertyChanged(_arg06, _arg16, _arg24);
              reply.writeNoException();
              return true;
            case 7:
              long _arg07 = data.readLong();
              int[] _arg17 = data.createIntArray();
              int[] _arg25 = data.createIntArray();
              data.enforceNoDataAvail();
              onEnumerate(_arg07, _arg17, _arg25);
              reply.writeNoException();
              return true;
            case 8:
              long _arg08 = data.readLong();
              int _arg18 = data.readInt();
              byte[] _arg26 = data.createByteArray();
              int _arg33 = data.readInt();
              int _arg42 = data.readInt();
              data.enforceNoDataAvail();
              onImageProcessed(_arg08, _arg18, _arg26, _arg33, _arg42);
              reply.writeNoException();
              return true;
            case 9:
              long _arg09 = data.readLong();
              String _arg19 = data.readString();
              String _arg27 = data.readString();
              data.enforceNoDataAvail();
              onGeneralParameterChanged(_arg09, _arg19, _arg27);
              reply.writeNoException();
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements IIrisDaemonCallback {
      private IBinder mRemote;

      Proxy(IBinder remote) {
        this.mRemote = remote;
      }

      @Override // android.os.IInterface
      public IBinder asBinder() {
        return this.mRemote;
      }

      public String getInterfaceDescriptor() {
        return IIrisDaemonCallback.DESCRIPTOR;
      }

      @Override // com.samsung.android.camera.iris.IIrisDaemonCallback
      public void onEnrollResult(long deviceId, int irisId, int groupId, int remaining)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IIrisDaemonCallback.DESCRIPTOR);
          _data.writeLong(deviceId);
          _data.writeInt(irisId);
          _data.writeInt(groupId);
          _data.writeInt(remaining);
          this.mRemote.transact(1, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.camera.iris.IIrisDaemonCallback
      public void onAcquired(long deviceId, EyeInfo eyeInfo) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IIrisDaemonCallback.DESCRIPTOR);
          _data.writeLong(deviceId);
          _data.writeTypedObject(eyeInfo, 0);
          this.mRemote.transact(2, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.camera.iris.IIrisDaemonCallback
      public void onAuthenticated(
          long deviceId, int irisId, int groupId, byte[] hatData, byte[] fidoResultData)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IIrisDaemonCallback.DESCRIPTOR);
          _data.writeLong(deviceId);
          _data.writeInt(irisId);
          _data.writeInt(groupId);
          _data.writeByteArray(hatData);
          _data.writeByteArray(fidoResultData);
          this.mRemote.transact(3, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.camera.iris.IIrisDaemonCallback
      public void onError(long deviceId, int error) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IIrisDaemonCallback.DESCRIPTOR);
          _data.writeLong(deviceId);
          _data.writeInt(error);
          this.mRemote.transact(4, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.camera.iris.IIrisDaemonCallback
      public void onRemoved(long deviceId, int irisId, int groupId) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IIrisDaemonCallback.DESCRIPTOR);
          _data.writeLong(deviceId);
          _data.writeInt(irisId);
          _data.writeInt(groupId);
          this.mRemote.transact(5, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.camera.iris.IIrisDaemonCallback
      public void onIRPropertyChanged(long deviceId, String key, String value)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IIrisDaemonCallback.DESCRIPTOR);
          _data.writeLong(deviceId);
          _data.writeString(key);
          _data.writeString(value);
          this.mRemote.transact(6, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.camera.iris.IIrisDaemonCallback
      public void onEnumerate(long deviceId, int[] irisIds, int[] groupIds) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IIrisDaemonCallback.DESCRIPTOR);
          _data.writeLong(deviceId);
          _data.writeIntArray(irisIds);
          _data.writeIntArray(groupIds);
          this.mRemote.transact(7, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.camera.iris.IIrisDaemonCallback
      public void onImageProcessed(
          long deviceId, int imageId, byte[] previewImage, int width, int height)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IIrisDaemonCallback.DESCRIPTOR);
          _data.writeLong(deviceId);
          _data.writeInt(imageId);
          _data.writeByteArray(previewImage);
          _data.writeInt(width);
          _data.writeInt(height);
          this.mRemote.transact(8, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.camera.iris.IIrisDaemonCallback
      public void onGeneralParameterChanged(long deviceId, String key, String value)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IIrisDaemonCallback.DESCRIPTOR);
          _data.writeLong(deviceId);
          _data.writeString(key);
          _data.writeString(value);
          this.mRemote.transact(9, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }
    }

    @Override // android.os.Binder
    public int getMaxTransactionId() {
      return 8;
    }
  }
}
