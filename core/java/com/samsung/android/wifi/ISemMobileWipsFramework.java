package com.samsung.android.wifi;

import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Message;
import android.p009os.Parcel;
import android.p009os.RemoteException;
import java.util.List;

/* loaded from: classes6.dex */
public interface ISemMobileWipsFramework extends IInterface {
  public static final String DESCRIPTOR = "com.samsung.android.wifi.ISemMobileWipsFramework";

  List<SemMobileWipsScanResult> getScanResults() throws RemoteException;

  boolean invokeMethodBool(int i) throws RemoteException;

  String invokeMethodStr(int i) throws RemoteException;

  void partialScanStart(Message message) throws RemoteException;

  void sendHWParamToHQMwithAppId(
      int i,
      String str,
      String str2,
      String str3,
      String str4,
      String str5,
      String str6,
      String str7,
      String str8,
      String str9)
      throws RemoteException;

  public static class Default implements ISemMobileWipsFramework {
    @Override // com.samsung.android.wifi.ISemMobileWipsFramework
    public boolean invokeMethodBool(int value) throws RemoteException {
      return false;
    }

    @Override // com.samsung.android.wifi.ISemMobileWipsFramework
    public String invokeMethodStr(int index) throws RemoteException {
      return null;
    }

    @Override // com.samsung.android.wifi.ISemMobileWipsFramework
    public void partialScanStart(Message msg) throws RemoteException {}

    @Override // com.samsung.android.wifi.ISemMobileWipsFramework
    public List<SemMobileWipsScanResult> getScanResults() throws RemoteException {
      return null;
    }

    @Override // com.samsung.android.wifi.ISemMobileWipsFramework
    public void sendHWParamToHQMwithAppId(
        int type,
        String compId,
        String feature,
        String hitType,
        String compVer,
        String compManufacture,
        String devCustomDataSet,
        String basicCustomDataSet,
        String priCustomDataSet,
        String appId)
        throws RemoteException {}

    @Override // android.p009os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements ISemMobileWipsFramework {
    static final int TRANSACTION_getScanResults = 4;
    static final int TRANSACTION_invokeMethodBool = 1;
    static final int TRANSACTION_invokeMethodStr = 2;
    static final int TRANSACTION_partialScanStart = 3;
    static final int TRANSACTION_sendHWParamToHQMwithAppId = 5;

    public Stub() {
      attachInterface(this, ISemMobileWipsFramework.DESCRIPTOR);
    }

    public static ISemMobileWipsFramework asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(ISemMobileWipsFramework.DESCRIPTOR);
      if (iin != null && (iin instanceof ISemMobileWipsFramework)) {
        return (ISemMobileWipsFramework) iin;
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
          return "invokeMethodBool";
        case 2:
          return "invokeMethodStr";
        case 3:
          return "partialScanStart";
        case 4:
          return "getScanResults";
        case 5:
          return "sendHWParamToHQMwithAppId";
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
        data.enforceInterface(ISemMobileWipsFramework.DESCRIPTOR);
      }
      switch (code) {
        case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
          reply.writeString(ISemMobileWipsFramework.DESCRIPTOR);
          return true;
        default:
          switch (code) {
            case 1:
              int _arg0 = data.readInt();
              data.enforceNoDataAvail();
              boolean _result = invokeMethodBool(_arg0);
              reply.writeNoException();
              reply.writeBoolean(_result);
              return true;
            case 2:
              int _arg02 = data.readInt();
              data.enforceNoDataAvail();
              String _result2 = invokeMethodStr(_arg02);
              reply.writeNoException();
              reply.writeString(_result2);
              return true;
            case 3:
              Message _arg03 = (Message) data.readTypedObject(Message.CREATOR);
              data.enforceNoDataAvail();
              partialScanStart(_arg03);
              reply.writeNoException();
              return true;
            case 4:
              List<SemMobileWipsScanResult> _result3 = getScanResults();
              reply.writeNoException();
              reply.writeTypedList(_result3, 1);
              return true;
            case 5:
              int _arg04 = data.readInt();
              String _arg1 = data.readString();
              String _arg2 = data.readString();
              String _arg3 = data.readString();
              String _arg4 = data.readString();
              String _arg5 = data.readString();
              String _arg6 = data.readString();
              String _arg7 = data.readString();
              String _arg8 = data.readString();
              String _arg9 = data.readString();
              data.enforceNoDataAvail();
              sendHWParamToHQMwithAppId(
                  _arg04, _arg1, _arg2, _arg3, _arg4, _arg5, _arg6, _arg7, _arg8, _arg9);
              reply.writeNoException();
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements ISemMobileWipsFramework {
      private IBinder mRemote;

      Proxy(IBinder remote) {
        this.mRemote = remote;
      }

      @Override // android.p009os.IInterface
      public IBinder asBinder() {
        return this.mRemote;
      }

      public String getInterfaceDescriptor() {
        return ISemMobileWipsFramework.DESCRIPTOR;
      }

      @Override // com.samsung.android.wifi.ISemMobileWipsFramework
      public boolean invokeMethodBool(int value) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(ISemMobileWipsFramework.DESCRIPTOR);
          _data.writeInt(value);
          this.mRemote.transact(1, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.wifi.ISemMobileWipsFramework
      public String invokeMethodStr(int index) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(ISemMobileWipsFramework.DESCRIPTOR);
          _data.writeInt(index);
          this.mRemote.transact(2, _data, _reply, 0);
          _reply.readException();
          String _result = _reply.readString();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.wifi.ISemMobileWipsFramework
      public void partialScanStart(Message msg) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(ISemMobileWipsFramework.DESCRIPTOR);
          _data.writeTypedObject(msg, 0);
          this.mRemote.transact(3, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.wifi.ISemMobileWipsFramework
      public List<SemMobileWipsScanResult> getScanResults() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(ISemMobileWipsFramework.DESCRIPTOR);
          this.mRemote.transact(4, _data, _reply, 0);
          _reply.readException();
          List<SemMobileWipsScanResult> _result =
              _reply.createTypedArrayList(SemMobileWipsScanResult.CREATOR);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.wifi.ISemMobileWipsFramework
      public void sendHWParamToHQMwithAppId(
          int type,
          String compId,
          String feature,
          String hitType,
          String compVer,
          String compManufacture,
          String devCustomDataSet,
          String basicCustomDataSet,
          String priCustomDataSet,
          String appId)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(ISemMobileWipsFramework.DESCRIPTOR);
          _data.writeInt(type);
          _data.writeString(compId);
          _data.writeString(feature);
          _data.writeString(hitType);
          _data.writeString(compVer);
          _data.writeString(compManufacture);
          _data.writeString(devCustomDataSet);
          _data.writeString(basicCustomDataSet);
          _data.writeString(priCustomDataSet);
          _data.writeString(appId);
          this.mRemote.transact(5, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }
    }

    @Override // android.p009os.Binder
    public int getMaxTransactionId() {
      return 4;
    }
  }
}
