package vendor.samsung.hardware.radio.data;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes6.dex */
public interface ISehRadioDataIndication extends IInterface {
  public static final String DESCRIPTOR =
      "vendor$samsung$hardware$radio$data$ISehRadioDataIndication".replace('$', '.');
  public static final String HASH = "1c18f89373d68cf0030dbdb95f4a9287fe232a2e";
  public static final int VERSION = 1;

  String getInterfaceHash() throws RemoteException;

  int getInterfaceVersion() throws RemoteException;

  SehApnProfile needApnProfileIndication(String str) throws RemoteException;

  SehPacketUsage needPacketUsage(String str) throws RemoteException;

  int needSettingValueIndication(String str, String str2) throws RemoteException;

  void rrcStateChanged(int i, SehRrcStateInfo sehRrcStateInfo) throws RemoteException;

  void timerStatusChangedInd(int i, int[] iArr) throws RemoteException;

  public static class Default implements ISehRadioDataIndication {
    @Override // vendor.samsung.hardware.radio.data.ISehRadioDataIndication
    public void rrcStateChanged(int indicationType, SehRrcStateInfo state) throws RemoteException {}

    @Override // vendor.samsung.hardware.radio.data.ISehRadioDataIndication
    public void timerStatusChangedInd(int indicationType, int[] eventNoti) throws RemoteException {}

    @Override // vendor.samsung.hardware.radio.data.ISehRadioDataIndication
    public SehApnProfile needApnProfileIndication(String select) throws RemoteException {
      return null;
    }

    @Override // vendor.samsung.hardware.radio.data.ISehRadioDataIndication
    public int needSettingValueIndication(String key, String table) throws RemoteException {
      return 0;
    }

    @Override // vendor.samsung.hardware.radio.data.ISehRadioDataIndication
    public SehPacketUsage needPacketUsage(String iface) throws RemoteException {
      return null;
    }

    @Override // vendor.samsung.hardware.radio.data.ISehRadioDataIndication
    public int getInterfaceVersion() {
      return 0;
    }

    @Override // vendor.samsung.hardware.radio.data.ISehRadioDataIndication
    public String getInterfaceHash() {
      return "";
    }

    @Override // android.os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements ISehRadioDataIndication {
    static final int TRANSACTION_getInterfaceHash = 16777214;
    static final int TRANSACTION_getInterfaceVersion = 16777215;
    static final int TRANSACTION_needApnProfileIndication = 3;
    static final int TRANSACTION_needPacketUsage = 5;
    static final int TRANSACTION_needSettingValueIndication = 4;
    static final int TRANSACTION_rrcStateChanged = 1;
    static final int TRANSACTION_timerStatusChangedInd = 2;

    public Stub() {
      markVintfStability();
      attachInterface(this, DESCRIPTOR);
    }

    public static ISehRadioDataIndication asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
      if (iin != null && (iin instanceof ISehRadioDataIndication)) {
        return (ISehRadioDataIndication) iin;
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
              SehRrcStateInfo _arg1 =
                  (SehRrcStateInfo) data.readTypedObject(SehRrcStateInfo.CREATOR);
              data.enforceNoDataAvail();
              rrcStateChanged(_arg0, _arg1);
              return true;
            case 2:
              int _arg02 = data.readInt();
              int[] _arg12 = data.createIntArray();
              data.enforceNoDataAvail();
              timerStatusChangedInd(_arg02, _arg12);
              return true;
            case 3:
              String _arg03 = data.readString();
              data.enforceNoDataAvail();
              SehApnProfile _result = needApnProfileIndication(_arg03);
              reply.writeNoException();
              reply.writeTypedObject(_result, 1);
              return true;
            case 4:
              String _arg04 = data.readString();
              String _arg13 = data.readString();
              data.enforceNoDataAvail();
              int _result2 = needSettingValueIndication(_arg04, _arg13);
              reply.writeNoException();
              reply.writeInt(_result2);
              return true;
            case 5:
              String _arg05 = data.readString();
              data.enforceNoDataAvail();
              SehPacketUsage _result3 = needPacketUsage(_arg05);
              reply.writeNoException();
              reply.writeTypedObject(_result3, 1);
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements ISehRadioDataIndication {
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

      @Override // vendor.samsung.hardware.radio.data.ISehRadioDataIndication
      public void rrcStateChanged(int indicationType, SehRrcStateInfo state)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeInt(indicationType);
          _data.writeTypedObject(state, 0);
          boolean _status = this.mRemote.transact(1, _data, null, 1);
          if (!_status) {
            throw new RemoteException("Method rrcStateChanged is unimplemented.");
          }
        } finally {
          _data.recycle();
        }
      }

      @Override // vendor.samsung.hardware.radio.data.ISehRadioDataIndication
      public void timerStatusChangedInd(int indicationType, int[] eventNoti)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeInt(indicationType);
          _data.writeIntArray(eventNoti);
          boolean _status = this.mRemote.transact(2, _data, null, 1);
          if (!_status) {
            throw new RemoteException("Method timerStatusChangedInd is unimplemented.");
          }
        } finally {
          _data.recycle();
        }
      }

      @Override // vendor.samsung.hardware.radio.data.ISehRadioDataIndication
      public SehApnProfile needApnProfileIndication(String select) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeString(select);
          boolean _status = this.mRemote.transact(3, _data, _reply, 0);
          if (!_status) {
            throw new RemoteException("Method needApnProfileIndication is unimplemented.");
          }
          _reply.readException();
          SehApnProfile _result = (SehApnProfile) _reply.readTypedObject(SehApnProfile.CREATOR);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // vendor.samsung.hardware.radio.data.ISehRadioDataIndication
      public int needSettingValueIndication(String key, String table) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeString(key);
          _data.writeString(table);
          boolean _status = this.mRemote.transact(4, _data, _reply, 0);
          if (!_status) {
            throw new RemoteException("Method needSettingValueIndication is unimplemented.");
          }
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // vendor.samsung.hardware.radio.data.ISehRadioDataIndication
      public SehPacketUsage needPacketUsage(String iface) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeString(iface);
          boolean _status = this.mRemote.transact(5, _data, _reply, 0);
          if (!_status) {
            throw new RemoteException("Method needPacketUsage is unimplemented.");
          }
          _reply.readException();
          SehPacketUsage _result = (SehPacketUsage) _reply.readTypedObject(SehPacketUsage.CREATOR);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // vendor.samsung.hardware.radio.data.ISehRadioDataIndication
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

      @Override // vendor.samsung.hardware.radio.data.ISehRadioDataIndication
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
