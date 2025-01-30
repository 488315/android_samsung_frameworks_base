package android.telephony.ims.aidl;

import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;

/* loaded from: classes3.dex */
public interface IImsSmsListener extends IInterface {
  public static final String DESCRIPTOR = "android.telephony.ims.aidl.IImsSmsListener";

  void onMemoryAvailableResult(int i, int i2, int i3) throws RemoteException;

  void onReceiveSmsDeliveryReportAck(int i, int i2) throws RemoteException;

  void onSendSmsResponse(int i, int i2, int i3, int i4, int i5, int i6) throws RemoteException;

  void onSendSmsResult(int i, int i2, int i3, int i4, int i5) throws RemoteException;

  void onSmsReceived(int i, String str, byte[] bArr) throws RemoteException;

  void onSmsStatusReportReceived(int i, String str, byte[] bArr) throws RemoteException;

  public static class Default implements IImsSmsListener {
    @Override // android.telephony.ims.aidl.IImsSmsListener
    public void onSendSmsResult(
        int token, int messageRef, int status, int reason, int networkErrorCode)
        throws RemoteException {}

    @Override // android.telephony.ims.aidl.IImsSmsListener
    public void onSmsStatusReportReceived(int token, String format, byte[] pdu)
        throws RemoteException {}

    @Override // android.telephony.ims.aidl.IImsSmsListener
    public void onSmsReceived(int token, String format, byte[] pdu) throws RemoteException {}

    @Override // android.telephony.ims.aidl.IImsSmsListener
    public void onSendSmsResponse(
        int token, int messageRef, int status, int reason, int errorCode, int errorClass)
        throws RemoteException {}

    @Override // android.telephony.ims.aidl.IImsSmsListener
    public void onReceiveSmsDeliveryReportAck(int token, int reasonCode) throws RemoteException {}

    @Override // android.telephony.ims.aidl.IImsSmsListener
    public void onMemoryAvailableResult(int token, int status, int networkErrorCode)
        throws RemoteException {}

    @Override // android.p009os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements IImsSmsListener {
    static final int TRANSACTION_onMemoryAvailableResult = 6;
    static final int TRANSACTION_onReceiveSmsDeliveryReportAck = 5;
    static final int TRANSACTION_onSendSmsResponse = 4;
    static final int TRANSACTION_onSendSmsResult = 1;
    static final int TRANSACTION_onSmsReceived = 3;
    static final int TRANSACTION_onSmsStatusReportReceived = 2;

    public Stub() {
      attachInterface(this, IImsSmsListener.DESCRIPTOR);
    }

    public static IImsSmsListener asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(IImsSmsListener.DESCRIPTOR);
      if (iin != null && (iin instanceof IImsSmsListener)) {
        return (IImsSmsListener) iin;
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
          return "onSendSmsResult";
        case 2:
          return "onSmsStatusReportReceived";
        case 3:
          return "onSmsReceived";
        case 4:
          return "onSendSmsResponse";
        case 5:
          return "onReceiveSmsDeliveryReportAck";
        case 6:
          return "onMemoryAvailableResult";
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
        data.enforceInterface(IImsSmsListener.DESCRIPTOR);
      }
      switch (code) {
        case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
          reply.writeString(IImsSmsListener.DESCRIPTOR);
          return true;
        default:
          switch (code) {
            case 1:
              int _arg0 = data.readInt();
              int _arg1 = data.readInt();
              int _arg2 = data.readInt();
              int _arg3 = data.readInt();
              int _arg4 = data.readInt();
              data.enforceNoDataAvail();
              onSendSmsResult(_arg0, _arg1, _arg2, _arg3, _arg4);
              return true;
            case 2:
              int _arg02 = data.readInt();
              String _arg12 = data.readString();
              byte[] _arg22 = data.createByteArray();
              data.enforceNoDataAvail();
              onSmsStatusReportReceived(_arg02, _arg12, _arg22);
              return true;
            case 3:
              int _arg03 = data.readInt();
              String _arg13 = data.readString();
              byte[] _arg23 = data.createByteArray();
              data.enforceNoDataAvail();
              onSmsReceived(_arg03, _arg13, _arg23);
              return true;
            case 4:
              int _arg04 = data.readInt();
              int _arg14 = data.readInt();
              int _arg24 = data.readInt();
              int _arg32 = data.readInt();
              int _arg42 = data.readInt();
              int _arg5 = data.readInt();
              data.enforceNoDataAvail();
              onSendSmsResponse(_arg04, _arg14, _arg24, _arg32, _arg42, _arg5);
              return true;
            case 5:
              int _arg05 = data.readInt();
              int _arg15 = data.readInt();
              data.enforceNoDataAvail();
              onReceiveSmsDeliveryReportAck(_arg05, _arg15);
              return true;
            case 6:
              int _arg06 = data.readInt();
              int _arg16 = data.readInt();
              int _arg25 = data.readInt();
              data.enforceNoDataAvail();
              onMemoryAvailableResult(_arg06, _arg16, _arg25);
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements IImsSmsListener {
      private IBinder mRemote;

      Proxy(IBinder remote) {
        this.mRemote = remote;
      }

      @Override // android.p009os.IInterface
      public IBinder asBinder() {
        return this.mRemote;
      }

      public String getInterfaceDescriptor() {
        return IImsSmsListener.DESCRIPTOR;
      }

      @Override // android.telephony.ims.aidl.IImsSmsListener
      public void onSendSmsResult(
          int token, int messageRef, int status, int reason, int networkErrorCode)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(IImsSmsListener.DESCRIPTOR);
          _data.writeInt(token);
          _data.writeInt(messageRef);
          _data.writeInt(status);
          _data.writeInt(reason);
          _data.writeInt(networkErrorCode);
          this.mRemote.transact(1, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.telephony.ims.aidl.IImsSmsListener
      public void onSmsStatusReportReceived(int token, String format, byte[] pdu)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(IImsSmsListener.DESCRIPTOR);
          _data.writeInt(token);
          _data.writeString(format);
          _data.writeByteArray(pdu);
          this.mRemote.transact(2, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.telephony.ims.aidl.IImsSmsListener
      public void onSmsReceived(int token, String format, byte[] pdu) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(IImsSmsListener.DESCRIPTOR);
          _data.writeInt(token);
          _data.writeString(format);
          _data.writeByteArray(pdu);
          this.mRemote.transact(3, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.telephony.ims.aidl.IImsSmsListener
      public void onSendSmsResponse(
          int token, int messageRef, int status, int reason, int errorCode, int errorClass)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(IImsSmsListener.DESCRIPTOR);
          _data.writeInt(token);
          _data.writeInt(messageRef);
          _data.writeInt(status);
          _data.writeInt(reason);
          _data.writeInt(errorCode);
          _data.writeInt(errorClass);
          this.mRemote.transact(4, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.telephony.ims.aidl.IImsSmsListener
      public void onReceiveSmsDeliveryReportAck(int token, int reasonCode) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(IImsSmsListener.DESCRIPTOR);
          _data.writeInt(token);
          _data.writeInt(reasonCode);
          this.mRemote.transact(5, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.telephony.ims.aidl.IImsSmsListener
      public void onMemoryAvailableResult(int token, int status, int networkErrorCode)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(IImsSmsListener.DESCRIPTOR);
          _data.writeInt(token);
          _data.writeInt(status);
          _data.writeInt(networkErrorCode);
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
