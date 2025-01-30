package vendor.samsung.hardware.radio.messaging;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes6.dex */
public interface ISehRadioMessaging extends IInterface {
  public static final String DESCRIPTOR =
      "vendor$samsung$hardware$radio$messaging$ISehRadioMessaging".replace('$', '.');
  public static final String HASH = "c1a8596db57e3bcc8e4e86f1eb7b2df7839ca140";
  public static final int VERSION = 1;

  void getCellBroadcastConfig(int i) throws RemoteException;

  void getImsRegistrationState(int i) throws RemoteException;

  String getInterfaceHash() throws RemoteException;

  int getInterfaceVersion() throws RemoteException;

  void getStoredMsgCountFromSim(int i) throws RemoteException;

  void readSmsFromSim(int i, int i2) throws RemoteException;

  void sendCdmaSms(int i, SehCdmaSmsMessage sehCdmaSmsMessage) throws RemoteException;

  void sendCdmaSmsExpectMore(int i, SehCdmaSmsMessage sehCdmaSmsMessage) throws RemoteException;

  void sendSMSExpectMore(int i, SehGsmSmsMessage sehGsmSmsMessage) throws RemoteException;

  void sendSms(int i, SehGsmSmsMessage sehGsmSmsMessage) throws RemoteException;

  void setResponseFunctions(
      ISehRadioMessagingResponse iSehRadioMessagingResponse,
      ISehRadioMessagingIndication iSehRadioMessagingIndication)
      throws RemoteException;

  void writeSmsToSim(int i, SehSimMsgArgs sehSimMsgArgs) throws RemoteException;

  public static class Default implements ISehRadioMessaging {
    @Override // vendor.samsung.hardware.radio.messaging.ISehRadioMessaging
    public void getCellBroadcastConfig(int serial) throws RemoteException {}

    @Override // vendor.samsung.hardware.radio.messaging.ISehRadioMessaging
    public void getImsRegistrationState(int serial) throws RemoteException {}

    @Override // vendor.samsung.hardware.radio.messaging.ISehRadioMessaging
    public void getStoredMsgCountFromSim(int serial) throws RemoteException {}

    @Override // vendor.samsung.hardware.radio.messaging.ISehRadioMessaging
    public void readSmsFromSim(int serial, int index) throws RemoteException {}

    @Override // vendor.samsung.hardware.radio.messaging.ISehRadioMessaging
    public void sendCdmaSms(int serial, SehCdmaSmsMessage sms) throws RemoteException {}

    @Override // vendor.samsung.hardware.radio.messaging.ISehRadioMessaging
    public void sendCdmaSmsExpectMore(int serial, SehCdmaSmsMessage sms) throws RemoteException {}

    @Override // vendor.samsung.hardware.radio.messaging.ISehRadioMessaging
    public void sendSMSExpectMore(int serial, SehGsmSmsMessage message) throws RemoteException {}

    @Override // vendor.samsung.hardware.radio.messaging.ISehRadioMessaging
    public void sendSms(int serial, SehGsmSmsMessage message) throws RemoteException {}

    @Override // vendor.samsung.hardware.radio.messaging.ISehRadioMessaging
    public void setResponseFunctions(
        ISehRadioMessagingResponse radioMessagingResponse,
        ISehRadioMessagingIndication radioMessagingIndication)
        throws RemoteException {}

    @Override // vendor.samsung.hardware.radio.messaging.ISehRadioMessaging
    public void writeSmsToSim(int serial, SehSimMsgArgs arg) throws RemoteException {}

    @Override // vendor.samsung.hardware.radio.messaging.ISehRadioMessaging
    public int getInterfaceVersion() {
      return 0;
    }

    @Override // vendor.samsung.hardware.radio.messaging.ISehRadioMessaging
    public String getInterfaceHash() {
      return "";
    }

    @Override // android.os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements ISehRadioMessaging {
    static final int TRANSACTION_getCellBroadcastConfig = 1;
    static final int TRANSACTION_getImsRegistrationState = 2;
    static final int TRANSACTION_getInterfaceHash = 16777214;
    static final int TRANSACTION_getInterfaceVersion = 16777215;
    static final int TRANSACTION_getStoredMsgCountFromSim = 3;
    static final int TRANSACTION_readSmsFromSim = 4;
    static final int TRANSACTION_sendCdmaSms = 5;
    static final int TRANSACTION_sendCdmaSmsExpectMore = 6;
    static final int TRANSACTION_sendSMSExpectMore = 7;
    static final int TRANSACTION_sendSms = 8;
    static final int TRANSACTION_setResponseFunctions = 9;
    static final int TRANSACTION_writeSmsToSim = 10;

    public Stub() {
      markVintfStability();
      attachInterface(this, DESCRIPTOR);
    }

    public static ISehRadioMessaging asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
      if (iin != null && (iin instanceof ISehRadioMessaging)) {
        return (ISehRadioMessaging) iin;
      }
      return new Proxy(obj);
    }

    @Override // android.os.IInterface
    public IBinder asBinder() {
      return this;
    }

    @Override // android.os.Binder
    public boolean onTransact(int code, Parcel data, Parcel reply, int flags)
        throws RemoteException {
      String descriptor = DESCRIPTOR;
      if (code >= 1 && code <= 16777215) {
        data.enforceInterface(descriptor);
      }
      switch (code) {
        case 16777214:
          reply.writeNoException();
          reply.writeString(getInterfaceHash());
          return true;
        case 16777215:
          reply.writeNoException();
          reply.writeInt(getInterfaceVersion());
          return true;
        case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
          reply.writeString(descriptor);
          return true;
        default:
          switch (code) {
            case 1:
              int _arg0 = data.readInt();
              data.enforceNoDataAvail();
              getCellBroadcastConfig(_arg0);
              return true;
            case 2:
              int _arg02 = data.readInt();
              data.enforceNoDataAvail();
              getImsRegistrationState(_arg02);
              return true;
            case 3:
              int _arg03 = data.readInt();
              data.enforceNoDataAvail();
              getStoredMsgCountFromSim(_arg03);
              return true;
            case 4:
              int _arg04 = data.readInt();
              int _arg1 = data.readInt();
              data.enforceNoDataAvail();
              readSmsFromSim(_arg04, _arg1);
              return true;
            case 5:
              int _arg05 = data.readInt();
              SehCdmaSmsMessage _arg12 =
                  (SehCdmaSmsMessage) data.readTypedObject(SehCdmaSmsMessage.CREATOR);
              data.enforceNoDataAvail();
              sendCdmaSms(_arg05, _arg12);
              return true;
            case 6:
              int _arg06 = data.readInt();
              SehCdmaSmsMessage _arg13 =
                  (SehCdmaSmsMessage) data.readTypedObject(SehCdmaSmsMessage.CREATOR);
              data.enforceNoDataAvail();
              sendCdmaSmsExpectMore(_arg06, _arg13);
              return true;
            case 7:
              int _arg07 = data.readInt();
              SehGsmSmsMessage _arg14 =
                  (SehGsmSmsMessage) data.readTypedObject(SehGsmSmsMessage.CREATOR);
              data.enforceNoDataAvail();
              sendSMSExpectMore(_arg07, _arg14);
              return true;
            case 8:
              int _arg08 = data.readInt();
              SehGsmSmsMessage _arg15 =
                  (SehGsmSmsMessage) data.readTypedObject(SehGsmSmsMessage.CREATOR);
              data.enforceNoDataAvail();
              sendSms(_arg08, _arg15);
              return true;
            case 9:
              ISehRadioMessagingResponse _arg09 =
                  ISehRadioMessagingResponse.Stub.asInterface(data.readStrongBinder());
              ISehRadioMessagingIndication _arg16 =
                  ISehRadioMessagingIndication.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              setResponseFunctions(_arg09, _arg16);
              reply.writeNoException();
              return true;
            case 10:
              int _arg010 = data.readInt();
              SehSimMsgArgs _arg17 = (SehSimMsgArgs) data.readTypedObject(SehSimMsgArgs.CREATOR);
              data.enforceNoDataAvail();
              writeSmsToSim(_arg010, _arg17);
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements ISehRadioMessaging {
      private IBinder mRemote;
      private int mCachedVersion = -1;
      private String mCachedHash = "-1";

      Proxy(IBinder remote) {
        this.mRemote = remote;
      }

      @Override // android.os.IInterface
      public IBinder asBinder() {
        return this.mRemote;
      }

      public String getInterfaceDescriptor() {
        return DESCRIPTOR;
      }

      @Override // vendor.samsung.hardware.radio.messaging.ISehRadioMessaging
      public void getCellBroadcastConfig(int serial) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeInt(serial);
          boolean _status = this.mRemote.transact(1, _data, null, 1);
          if (!_status) {
            throw new RemoteException("Method getCellBroadcastConfig is unimplemented.");
          }
        } finally {
          _data.recycle();
        }
      }

      @Override // vendor.samsung.hardware.radio.messaging.ISehRadioMessaging
      public void getImsRegistrationState(int serial) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeInt(serial);
          boolean _status = this.mRemote.transact(2, _data, null, 1);
          if (!_status) {
            throw new RemoteException("Method getImsRegistrationState is unimplemented.");
          }
        } finally {
          _data.recycle();
        }
      }

      @Override // vendor.samsung.hardware.radio.messaging.ISehRadioMessaging
      public void getStoredMsgCountFromSim(int serial) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeInt(serial);
          boolean _status = this.mRemote.transact(3, _data, null, 1);
          if (!_status) {
            throw new RemoteException("Method getStoredMsgCountFromSim is unimplemented.");
          }
        } finally {
          _data.recycle();
        }
      }

      @Override // vendor.samsung.hardware.radio.messaging.ISehRadioMessaging
      public void readSmsFromSim(int serial, int index) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeInt(serial);
          _data.writeInt(index);
          boolean _status = this.mRemote.transact(4, _data, null, 1);
          if (!_status) {
            throw new RemoteException("Method readSmsFromSim is unimplemented.");
          }
        } finally {
          _data.recycle();
        }
      }

      @Override // vendor.samsung.hardware.radio.messaging.ISehRadioMessaging
      public void sendCdmaSms(int serial, SehCdmaSmsMessage sms) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeInt(serial);
          _data.writeTypedObject(sms, 0);
          boolean _status = this.mRemote.transact(5, _data, null, 1);
          if (!_status) {
            throw new RemoteException("Method sendCdmaSms is unimplemented.");
          }
        } finally {
          _data.recycle();
        }
      }

      @Override // vendor.samsung.hardware.radio.messaging.ISehRadioMessaging
      public void sendCdmaSmsExpectMore(int serial, SehCdmaSmsMessage sms) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeInt(serial);
          _data.writeTypedObject(sms, 0);
          boolean _status = this.mRemote.transact(6, _data, null, 1);
          if (!_status) {
            throw new RemoteException("Method sendCdmaSmsExpectMore is unimplemented.");
          }
        } finally {
          _data.recycle();
        }
      }

      @Override // vendor.samsung.hardware.radio.messaging.ISehRadioMessaging
      public void sendSMSExpectMore(int serial, SehGsmSmsMessage message) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeInt(serial);
          _data.writeTypedObject(message, 0);
          boolean _status = this.mRemote.transact(7, _data, null, 1);
          if (!_status) {
            throw new RemoteException("Method sendSMSExpectMore is unimplemented.");
          }
        } finally {
          _data.recycle();
        }
      }

      @Override // vendor.samsung.hardware.radio.messaging.ISehRadioMessaging
      public void sendSms(int serial, SehGsmSmsMessage message) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeInt(serial);
          _data.writeTypedObject(message, 0);
          boolean _status = this.mRemote.transact(8, _data, null, 1);
          if (!_status) {
            throw new RemoteException("Method sendSms is unimplemented.");
          }
        } finally {
          _data.recycle();
        }
      }

      @Override // vendor.samsung.hardware.radio.messaging.ISehRadioMessaging
      public void setResponseFunctions(
          ISehRadioMessagingResponse radioMessagingResponse,
          ISehRadioMessagingIndication radioMessagingIndication)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeStrongInterface(radioMessagingResponse);
          _data.writeStrongInterface(radioMessagingIndication);
          boolean _status = this.mRemote.transact(9, _data, _reply, 0);
          if (!_status) {
            throw new RemoteException("Method setResponseFunctions is unimplemented.");
          }
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // vendor.samsung.hardware.radio.messaging.ISehRadioMessaging
      public void writeSmsToSim(int serial, SehSimMsgArgs arg) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeInt(serial);
          _data.writeTypedObject(arg, 0);
          boolean _status = this.mRemote.transact(10, _data, null, 1);
          if (!_status) {
            throw new RemoteException("Method writeSmsToSim is unimplemented.");
          }
        } finally {
          _data.recycle();
        }
      }

      @Override // vendor.samsung.hardware.radio.messaging.ISehRadioMessaging
      public int getInterfaceVersion() throws RemoteException {
        if (this.mCachedVersion == -1) {
          Parcel data = Parcel.obtain(asBinder());
          Parcel reply = Parcel.obtain();
          try {
            data.writeInterfaceToken(DESCRIPTOR);
            this.mRemote.transact(16777215, data, reply, 0);
            reply.readException();
            this.mCachedVersion = reply.readInt();
          } finally {
            reply.recycle();
            data.recycle();
          }
        }
        return this.mCachedVersion;
      }

      @Override // vendor.samsung.hardware.radio.messaging.ISehRadioMessaging
      public synchronized String getInterfaceHash() throws RemoteException {
        if ("-1".equals(this.mCachedHash)) {
          Parcel data = Parcel.obtain(asBinder());
          Parcel reply = Parcel.obtain();
          try {
            data.writeInterfaceToken(DESCRIPTOR);
            this.mRemote.transact(16777214, data, reply, 0);
            reply.readException();
            this.mCachedHash = reply.readString();
            reply.recycle();
            data.recycle();
          } catch (Throwable th) {
            reply.recycle();
            data.recycle();
            throw th;
          }
        }
        return this.mCachedHash;
      }
    }
  }
}
