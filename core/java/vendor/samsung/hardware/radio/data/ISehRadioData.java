package vendor.samsung.hardware.radio.data;

import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;

/* loaded from: classes6.dex */
public interface ISehRadioData extends IInterface {
  public static final String DESCRIPTOR =
      "vendor$samsung$hardware$radio$data$ISehRadioData".replace('$', '.');
  public static final String HASH = "1c18f89373d68cf0030dbdb95f4a9287fe232a2e";
  public static final int VERSION = 1;

  String getInterfaceHash() throws RemoteException;

  int getInterfaceVersion() throws RemoteException;

  void setDataAllowed(int i, boolean z, SehAllowDataParam sehAllowDataParam) throws RemoteException;

  void setMobileDataSetting(int i, boolean z, boolean z2) throws RemoteException;

  void setResponseFunctions(
      ISehRadioDataResponse iSehRadioDataResponse, ISehRadioDataIndication iSehRadioDataIndication)
      throws RemoteException;

  public static class Default implements ISehRadioData {
    @Override // vendor.samsung.hardware.radio.data.ISehRadioData
    public void setDataAllowed(int serial, boolean allow, SehAllowDataParam param)
        throws RemoteException {}

    @Override // vendor.samsung.hardware.radio.data.ISehRadioData
    public void setMobileDataSetting(int serial, boolean enabled, boolean roamingEnabled)
        throws RemoteException {}

    @Override // vendor.samsung.hardware.radio.data.ISehRadioData
    public void setResponseFunctions(
        ISehRadioDataResponse radioResponse, ISehRadioDataIndication radioIndication)
        throws RemoteException {}

    @Override // vendor.samsung.hardware.radio.data.ISehRadioData
    public int getInterfaceVersion() {
      return 0;
    }

    @Override // vendor.samsung.hardware.radio.data.ISehRadioData
    public String getInterfaceHash() {
      return "";
    }

    @Override // android.p009os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements ISehRadioData {
    static final int TRANSACTION_getInterfaceHash = 16777214;
    static final int TRANSACTION_getInterfaceVersion = 16777215;
    static final int TRANSACTION_setDataAllowed = 1;
    static final int TRANSACTION_setMobileDataSetting = 2;
    static final int TRANSACTION_setResponseFunctions = 3;

    public Stub() {
      markVintfStability();
      attachInterface(this, DESCRIPTOR);
    }

    public static ISehRadioData asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
      if (iin != null && (iin instanceof ISehRadioData)) {
        return (ISehRadioData) iin;
      }
      return new Proxy(obj);
    }

    @Override // android.p009os.IInterface
    public IBinder asBinder() {
      return this;
    }

    @Override // android.p009os.Binder
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
              boolean _arg1 = data.readBoolean();
              SehAllowDataParam _arg2 =
                  (SehAllowDataParam) data.readTypedObject(SehAllowDataParam.CREATOR);
              data.enforceNoDataAvail();
              setDataAllowed(_arg0, _arg1, _arg2);
              return true;
            case 2:
              int _arg02 = data.readInt();
              boolean _arg12 = data.readBoolean();
              boolean _arg22 = data.readBoolean();
              data.enforceNoDataAvail();
              setMobileDataSetting(_arg02, _arg12, _arg22);
              return true;
            case 3:
              ISehRadioDataResponse _arg03 =
                  ISehRadioDataResponse.Stub.asInterface(data.readStrongBinder());
              ISehRadioDataIndication _arg13 =
                  ISehRadioDataIndication.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              setResponseFunctions(_arg03, _arg13);
              reply.writeNoException();
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements ISehRadioData {
      private IBinder mRemote;
      private int mCachedVersion = -1;
      private String mCachedHash = "-1";

      Proxy(IBinder remote) {
        this.mRemote = remote;
      }

      @Override // android.p009os.IInterface
      public IBinder asBinder() {
        return this.mRemote;
      }

      public String getInterfaceDescriptor() {
        return DESCRIPTOR;
      }

      @Override // vendor.samsung.hardware.radio.data.ISehRadioData
      public void setDataAllowed(int serial, boolean allow, SehAllowDataParam param)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeInt(serial);
          _data.writeBoolean(allow);
          _data.writeTypedObject(param, 0);
          boolean _status = this.mRemote.transact(1, _data, null, 1);
          if (!_status) {
            throw new RemoteException("Method setDataAllowed is unimplemented.");
          }
        } finally {
          _data.recycle();
        }
      }

      @Override // vendor.samsung.hardware.radio.data.ISehRadioData
      public void setMobileDataSetting(int serial, boolean enabled, boolean roamingEnabled)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeInt(serial);
          _data.writeBoolean(enabled);
          _data.writeBoolean(roamingEnabled);
          boolean _status = this.mRemote.transact(2, _data, null, 1);
          if (!_status) {
            throw new RemoteException("Method setMobileDataSetting is unimplemented.");
          }
        } finally {
          _data.recycle();
        }
      }

      @Override // vendor.samsung.hardware.radio.data.ISehRadioData
      public void setResponseFunctions(
          ISehRadioDataResponse radioResponse, ISehRadioDataIndication radioIndication)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeStrongInterface(radioResponse);
          _data.writeStrongInterface(radioIndication);
          boolean _status = this.mRemote.transact(3, _data, _reply, 0);
          if (!_status) {
            throw new RemoteException("Method setResponseFunctions is unimplemented.");
          }
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // vendor.samsung.hardware.radio.data.ISehRadioData
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

      @Override // vendor.samsung.hardware.radio.data.ISehRadioData
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
