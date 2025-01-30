package com.android.ims.internal.uce.options;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.android.ims.internal.uce.common.StatusCode;

/* loaded from: classes4.dex */
public interface IOptionsListener extends IInterface {
  void cmdStatus(OptionsCmdStatus optionsCmdStatus) throws RemoteException;

  void getVersionCb(String str) throws RemoteException;

  void incomingOptions(String str, OptionsCapInfo optionsCapInfo, int i) throws RemoteException;

  void serviceAvailable(StatusCode statusCode) throws RemoteException;

  void serviceUnavailable(StatusCode statusCode) throws RemoteException;

  void sipResponseReceived(
      String str, OptionsSipResponse optionsSipResponse, OptionsCapInfo optionsCapInfo)
      throws RemoteException;

  public static class Default implements IOptionsListener {
    @Override // com.android.ims.internal.uce.options.IOptionsListener
    public void getVersionCb(String version) throws RemoteException {}

    @Override // com.android.ims.internal.uce.options.IOptionsListener
    public void serviceAvailable(StatusCode statusCode) throws RemoteException {}

    @Override // com.android.ims.internal.uce.options.IOptionsListener
    public void serviceUnavailable(StatusCode statusCode) throws RemoteException {}

    @Override // com.android.ims.internal.uce.options.IOptionsListener
    public void sipResponseReceived(
        String uri, OptionsSipResponse sipResponse, OptionsCapInfo capInfo)
        throws RemoteException {}

    @Override // com.android.ims.internal.uce.options.IOptionsListener
    public void cmdStatus(OptionsCmdStatus cmdStatus) throws RemoteException {}

    @Override // com.android.ims.internal.uce.options.IOptionsListener
    public void incomingOptions(String uri, OptionsCapInfo capInfo, int tID)
        throws RemoteException {}

    @Override // android.os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements IOptionsListener {
    public static final String DESCRIPTOR = "com.android.ims.internal.uce.options.IOptionsListener";
    static final int TRANSACTION_cmdStatus = 5;
    static final int TRANSACTION_getVersionCb = 1;
    static final int TRANSACTION_incomingOptions = 6;
    static final int TRANSACTION_serviceAvailable = 2;
    static final int TRANSACTION_serviceUnavailable = 3;
    static final int TRANSACTION_sipResponseReceived = 4;

    public Stub() {
      attachInterface(this, DESCRIPTOR);
    }

    public static IOptionsListener asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
      if (iin != null && (iin instanceof IOptionsListener)) {
        return (IOptionsListener) iin;
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
          return "getVersionCb";
        case 2:
          return "serviceAvailable";
        case 3:
          return "serviceUnavailable";
        case 4:
          return "sipResponseReceived";
        case 5:
          return "cmdStatus";
        case 6:
          return "incomingOptions";
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
        data.enforceInterface(DESCRIPTOR);
      }
      switch (code) {
        case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
          reply.writeString(DESCRIPTOR);
          return true;
        default:
          switch (code) {
            case 1:
              String _arg0 = data.readString();
              data.enforceNoDataAvail();
              getVersionCb(_arg0);
              reply.writeNoException();
              return true;
            case 2:
              StatusCode _arg02 = (StatusCode) data.readTypedObject(StatusCode.CREATOR);
              data.enforceNoDataAvail();
              serviceAvailable(_arg02);
              reply.writeNoException();
              return true;
            case 3:
              StatusCode _arg03 = (StatusCode) data.readTypedObject(StatusCode.CREATOR);
              data.enforceNoDataAvail();
              serviceUnavailable(_arg03);
              reply.writeNoException();
              return true;
            case 4:
              String _arg04 = data.readString();
              OptionsSipResponse _arg1 =
                  (OptionsSipResponse) data.readTypedObject(OptionsSipResponse.CREATOR);
              OptionsCapInfo _arg2 = (OptionsCapInfo) data.readTypedObject(OptionsCapInfo.CREATOR);
              data.enforceNoDataAvail();
              sipResponseReceived(_arg04, _arg1, _arg2);
              reply.writeNoException();
              return true;
            case 5:
              OptionsCmdStatus _arg05 =
                  (OptionsCmdStatus) data.readTypedObject(OptionsCmdStatus.CREATOR);
              data.enforceNoDataAvail();
              cmdStatus(_arg05);
              reply.writeNoException();
              return true;
            case 6:
              String _arg06 = data.readString();
              OptionsCapInfo _arg12 = (OptionsCapInfo) data.readTypedObject(OptionsCapInfo.CREATOR);
              int _arg22 = data.readInt();
              data.enforceNoDataAvail();
              incomingOptions(_arg06, _arg12, _arg22);
              reply.writeNoException();
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements IOptionsListener {
      private IBinder mRemote;

      Proxy(IBinder remote) {
        this.mRemote = remote;
      }

      @Override // android.os.IInterface
      public IBinder asBinder() {
        return this.mRemote;
      }

      public String getInterfaceDescriptor() {
        return Stub.DESCRIPTOR;
      }

      @Override // com.android.ims.internal.uce.options.IOptionsListener
      public void getVersionCb(String version) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(version);
          this.mRemote.transact(1, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.android.ims.internal.uce.options.IOptionsListener
      public void serviceAvailable(StatusCode statusCode) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeTypedObject(statusCode, 0);
          this.mRemote.transact(2, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.android.ims.internal.uce.options.IOptionsListener
      public void serviceUnavailable(StatusCode statusCode) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeTypedObject(statusCode, 0);
          this.mRemote.transact(3, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.android.ims.internal.uce.options.IOptionsListener
      public void sipResponseReceived(
          String uri, OptionsSipResponse sipResponse, OptionsCapInfo capInfo)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(uri);
          _data.writeTypedObject(sipResponse, 0);
          _data.writeTypedObject(capInfo, 0);
          this.mRemote.transact(4, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.android.ims.internal.uce.options.IOptionsListener
      public void cmdStatus(OptionsCmdStatus cmdStatus) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeTypedObject(cmdStatus, 0);
          this.mRemote.transact(5, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.android.ims.internal.uce.options.IOptionsListener
      public void incomingOptions(String uri, OptionsCapInfo capInfo, int tID)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(uri);
          _data.writeTypedObject(capInfo, 0);
          _data.writeInt(tID);
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
