package vendor.samsung.hardware.radio.sim;

import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;
import vendor.samsung.hardware.radio.SehRadioResponseInfo;

/* loaded from: classes6.dex */
public interface ISehRadioSimResponse extends IInterface {
  public static final String DESCRIPTOR =
      "vendor$samsung$hardware$radio$sim$ISehRadioSimResponse".replace('$', '.');
  public static final String HASH = "e94363ef2041c98feabe84e756d983ad196394e8";
  public static final int VERSION = 1;

  void accessPhonebookEntryResponse(SehRadioResponseInfo sehRadioResponseInfo, int i)
      throws RemoteException;

  void changeIccPersonalizationResponse(SehRadioResponseInfo sehRadioResponseInfo)
      throws RemoteException;

  void getAtrResponse(SehRadioResponseInfo sehRadioResponseInfo, String str) throws RemoteException;

  void getIccCardStatusResponse(
      SehRadioResponseInfo sehRadioResponseInfo, SehCardStatus sehCardStatus)
      throws RemoteException;

  String getInterfaceHash() throws RemoteException;

  int getInterfaceVersion() throws RemoteException;

  void getPhonebookEntryResponse(
      SehRadioResponseInfo sehRadioResponseInfo, SehSimPhonebookResponse sehSimPhonebookResponse)
      throws RemoteException;

  void getPhonebookStorageInfoResponse(
      SehRadioResponseInfo sehRadioResponseInfo, SehPhonebookInfo sehPhonebookInfo)
      throws RemoteException;

  void getSimLockInfoResponse(
      SehRadioResponseInfo sehRadioResponseInfo, SehSimLockInfo sehSimLockInfo)
      throws RemoteException;

  void getUsimPhonebookCapabilityResponse(SehRadioResponseInfo sehRadioResponseInfo, int[] iArr)
      throws RemoteException;

  void setSimInitEventResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException;

  void setSimOnOffResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException;

  void supplyIccPersonalizationResponse(SehRadioResponseInfo sehRadioResponseInfo)
      throws RemoteException;

  void supplyNetworkDepersonalizationResponse(SehRadioResponseInfo sehRadioResponseInfo, int i)
      throws RemoteException;

  public static class Default implements ISehRadioSimResponse {
    @Override // vendor.samsung.hardware.radio.sim.ISehRadioSimResponse
    public void getIccCardStatusResponse(SehRadioResponseInfo info, SehCardStatus cardStatus)
        throws RemoteException {}

    @Override // vendor.samsung.hardware.radio.sim.ISehRadioSimResponse
    public void supplyNetworkDepersonalizationResponse(
        SehRadioResponseInfo info, int remainingRetries) throws RemoteException {}

    @Override // vendor.samsung.hardware.radio.sim.ISehRadioSimResponse
    public void getPhonebookStorageInfoResponse(
        SehRadioResponseInfo info, SehPhonebookInfo phonebookInfo) throws RemoteException {}

    @Override // vendor.samsung.hardware.radio.sim.ISehRadioSimResponse
    public void getUsimPhonebookCapabilityResponse(
        SehRadioResponseInfo info, int[] phonebookCapability) throws RemoteException {}

    @Override // vendor.samsung.hardware.radio.sim.ISehRadioSimResponse
    public void setSimOnOffResponse(SehRadioResponseInfo info) throws RemoteException {}

    @Override // vendor.samsung.hardware.radio.sim.ISehRadioSimResponse
    public void setSimInitEventResponse(SehRadioResponseInfo info) throws RemoteException {}

    @Override // vendor.samsung.hardware.radio.sim.ISehRadioSimResponse
    public void getSimLockInfoResponse(SehRadioResponseInfo info, SehSimLockInfo simLockInfo)
        throws RemoteException {}

    @Override // vendor.samsung.hardware.radio.sim.ISehRadioSimResponse
    public void supplyIccPersonalizationResponse(SehRadioResponseInfo info)
        throws RemoteException {}

    @Override // vendor.samsung.hardware.radio.sim.ISehRadioSimResponse
    public void changeIccPersonalizationResponse(SehRadioResponseInfo info)
        throws RemoteException {}

    @Override // vendor.samsung.hardware.radio.sim.ISehRadioSimResponse
    public void getPhonebookEntryResponse(
        SehRadioResponseInfo info, SehSimPhonebookResponse simPhonebookResponseInfo)
        throws RemoteException {}

    @Override // vendor.samsung.hardware.radio.sim.ISehRadioSimResponse
    public void accessPhonebookEntryResponse(SehRadioResponseInfo info, int SimPhonebookAccessResp)
        throws RemoteException {}

    @Override // vendor.samsung.hardware.radio.sim.ISehRadioSimResponse
    public void getAtrResponse(SehRadioResponseInfo info, String atr) throws RemoteException {}

    @Override // vendor.samsung.hardware.radio.sim.ISehRadioSimResponse
    public int getInterfaceVersion() {
      return 0;
    }

    @Override // vendor.samsung.hardware.radio.sim.ISehRadioSimResponse
    public String getInterfaceHash() {
      return "";
    }

    @Override // android.p009os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements ISehRadioSimResponse {
    static final int TRANSACTION_accessPhonebookEntryResponse = 11;
    static final int TRANSACTION_changeIccPersonalizationResponse = 9;
    static final int TRANSACTION_getAtrResponse = 12;
    static final int TRANSACTION_getIccCardStatusResponse = 1;
    static final int TRANSACTION_getInterfaceHash = 16777214;
    static final int TRANSACTION_getInterfaceVersion = 16777215;
    static final int TRANSACTION_getPhonebookEntryResponse = 10;
    static final int TRANSACTION_getPhonebookStorageInfoResponse = 3;
    static final int TRANSACTION_getSimLockInfoResponse = 7;
    static final int TRANSACTION_getUsimPhonebookCapabilityResponse = 4;
    static final int TRANSACTION_setSimInitEventResponse = 6;
    static final int TRANSACTION_setSimOnOffResponse = 5;
    static final int TRANSACTION_supplyIccPersonalizationResponse = 8;
    static final int TRANSACTION_supplyNetworkDepersonalizationResponse = 2;

    public Stub() {
      markVintfStability();
      attachInterface(this, DESCRIPTOR);
    }

    public static ISehRadioSimResponse asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
      if (iin != null && (iin instanceof ISehRadioSimResponse)) {
        return (ISehRadioSimResponse) iin;
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
              SehRadioResponseInfo _arg0 =
                  (SehRadioResponseInfo) data.readTypedObject(SehRadioResponseInfo.CREATOR);
              SehCardStatus _arg1 = (SehCardStatus) data.readTypedObject(SehCardStatus.CREATOR);
              data.enforceNoDataAvail();
              getIccCardStatusResponse(_arg0, _arg1);
              return true;
            case 2:
              SehRadioResponseInfo _arg02 =
                  (SehRadioResponseInfo) data.readTypedObject(SehRadioResponseInfo.CREATOR);
              int _arg12 = data.readInt();
              data.enforceNoDataAvail();
              supplyNetworkDepersonalizationResponse(_arg02, _arg12);
              return true;
            case 3:
              SehRadioResponseInfo _arg03 =
                  (SehRadioResponseInfo) data.readTypedObject(SehRadioResponseInfo.CREATOR);
              SehPhonebookInfo _arg13 =
                  (SehPhonebookInfo) data.readTypedObject(SehPhonebookInfo.CREATOR);
              data.enforceNoDataAvail();
              getPhonebookStorageInfoResponse(_arg03, _arg13);
              return true;
            case 4:
              SehRadioResponseInfo _arg04 =
                  (SehRadioResponseInfo) data.readTypedObject(SehRadioResponseInfo.CREATOR);
              int[] _arg14 = data.createIntArray();
              data.enforceNoDataAvail();
              getUsimPhonebookCapabilityResponse(_arg04, _arg14);
              return true;
            case 5:
              SehRadioResponseInfo _arg05 =
                  (SehRadioResponseInfo) data.readTypedObject(SehRadioResponseInfo.CREATOR);
              data.enforceNoDataAvail();
              setSimOnOffResponse(_arg05);
              return true;
            case 6:
              SehRadioResponseInfo _arg06 =
                  (SehRadioResponseInfo) data.readTypedObject(SehRadioResponseInfo.CREATOR);
              data.enforceNoDataAvail();
              setSimInitEventResponse(_arg06);
              return true;
            case 7:
              SehRadioResponseInfo _arg07 =
                  (SehRadioResponseInfo) data.readTypedObject(SehRadioResponseInfo.CREATOR);
              SehSimLockInfo _arg15 = (SehSimLockInfo) data.readTypedObject(SehSimLockInfo.CREATOR);
              data.enforceNoDataAvail();
              getSimLockInfoResponse(_arg07, _arg15);
              return true;
            case 8:
              SehRadioResponseInfo _arg08 =
                  (SehRadioResponseInfo) data.readTypedObject(SehRadioResponseInfo.CREATOR);
              data.enforceNoDataAvail();
              supplyIccPersonalizationResponse(_arg08);
              return true;
            case 9:
              SehRadioResponseInfo _arg09 =
                  (SehRadioResponseInfo) data.readTypedObject(SehRadioResponseInfo.CREATOR);
              data.enforceNoDataAvail();
              changeIccPersonalizationResponse(_arg09);
              return true;
            case 10:
              SehRadioResponseInfo _arg010 =
                  (SehRadioResponseInfo) data.readTypedObject(SehRadioResponseInfo.CREATOR);
              SehSimPhonebookResponse _arg16 =
                  (SehSimPhonebookResponse) data.readTypedObject(SehSimPhonebookResponse.CREATOR);
              data.enforceNoDataAvail();
              getPhonebookEntryResponse(_arg010, _arg16);
              return true;
            case 11:
              SehRadioResponseInfo _arg011 =
                  (SehRadioResponseInfo) data.readTypedObject(SehRadioResponseInfo.CREATOR);
              int _arg17 = data.readInt();
              data.enforceNoDataAvail();
              accessPhonebookEntryResponse(_arg011, _arg17);
              return true;
            case 12:
              SehRadioResponseInfo _arg012 =
                  (SehRadioResponseInfo) data.readTypedObject(SehRadioResponseInfo.CREATOR);
              String _arg18 = data.readString();
              data.enforceNoDataAvail();
              getAtrResponse(_arg012, _arg18);
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements ISehRadioSimResponse {
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

      @Override // vendor.samsung.hardware.radio.sim.ISehRadioSimResponse
      public void getIccCardStatusResponse(SehRadioResponseInfo info, SehCardStatus cardStatus)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeTypedObject(info, 0);
          _data.writeTypedObject(cardStatus, 0);
          boolean _status = this.mRemote.transact(1, _data, null, 1);
          if (!_status) {
            throw new RemoteException("Method getIccCardStatusResponse is unimplemented.");
          }
        } finally {
          _data.recycle();
        }
      }

      @Override // vendor.samsung.hardware.radio.sim.ISehRadioSimResponse
      public void supplyNetworkDepersonalizationResponse(
          SehRadioResponseInfo info, int remainingRetries) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeTypedObject(info, 0);
          _data.writeInt(remainingRetries);
          boolean _status = this.mRemote.transact(2, _data, null, 1);
          if (!_status) {
            throw new RemoteException(
                "Method supplyNetworkDepersonalizationResponse is unimplemented.");
          }
        } finally {
          _data.recycle();
        }
      }

      @Override // vendor.samsung.hardware.radio.sim.ISehRadioSimResponse
      public void getPhonebookStorageInfoResponse(
          SehRadioResponseInfo info, SehPhonebookInfo phonebookInfo) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeTypedObject(info, 0);
          _data.writeTypedObject(phonebookInfo, 0);
          boolean _status = this.mRemote.transact(3, _data, null, 1);
          if (!_status) {
            throw new RemoteException("Method getPhonebookStorageInfoResponse is unimplemented.");
          }
        } finally {
          _data.recycle();
        }
      }

      @Override // vendor.samsung.hardware.radio.sim.ISehRadioSimResponse
      public void getUsimPhonebookCapabilityResponse(
          SehRadioResponseInfo info, int[] phonebookCapability) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeTypedObject(info, 0);
          _data.writeIntArray(phonebookCapability);
          boolean _status = this.mRemote.transact(4, _data, null, 1);
          if (!_status) {
            throw new RemoteException(
                "Method getUsimPhonebookCapabilityResponse is unimplemented.");
          }
        } finally {
          _data.recycle();
        }
      }

      @Override // vendor.samsung.hardware.radio.sim.ISehRadioSimResponse
      public void setSimOnOffResponse(SehRadioResponseInfo info) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeTypedObject(info, 0);
          boolean _status = this.mRemote.transact(5, _data, null, 1);
          if (!_status) {
            throw new RemoteException("Method setSimOnOffResponse is unimplemented.");
          }
        } finally {
          _data.recycle();
        }
      }

      @Override // vendor.samsung.hardware.radio.sim.ISehRadioSimResponse
      public void setSimInitEventResponse(SehRadioResponseInfo info) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeTypedObject(info, 0);
          boolean _status = this.mRemote.transact(6, _data, null, 1);
          if (!_status) {
            throw new RemoteException("Method setSimInitEventResponse is unimplemented.");
          }
        } finally {
          _data.recycle();
        }
      }

      @Override // vendor.samsung.hardware.radio.sim.ISehRadioSimResponse
      public void getSimLockInfoResponse(SehRadioResponseInfo info, SehSimLockInfo simLockInfo)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeTypedObject(info, 0);
          _data.writeTypedObject(simLockInfo, 0);
          boolean _status = this.mRemote.transact(7, _data, null, 1);
          if (!_status) {
            throw new RemoteException("Method getSimLockInfoResponse is unimplemented.");
          }
        } finally {
          _data.recycle();
        }
      }

      @Override // vendor.samsung.hardware.radio.sim.ISehRadioSimResponse
      public void supplyIccPersonalizationResponse(SehRadioResponseInfo info)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeTypedObject(info, 0);
          boolean _status = this.mRemote.transact(8, _data, null, 1);
          if (!_status) {
            throw new RemoteException("Method supplyIccPersonalizationResponse is unimplemented.");
          }
        } finally {
          _data.recycle();
        }
      }

      @Override // vendor.samsung.hardware.radio.sim.ISehRadioSimResponse
      public void changeIccPersonalizationResponse(SehRadioResponseInfo info)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeTypedObject(info, 0);
          boolean _status = this.mRemote.transact(9, _data, null, 1);
          if (!_status) {
            throw new RemoteException("Method changeIccPersonalizationResponse is unimplemented.");
          }
        } finally {
          _data.recycle();
        }
      }

      @Override // vendor.samsung.hardware.radio.sim.ISehRadioSimResponse
      public void getPhonebookEntryResponse(
          SehRadioResponseInfo info, SehSimPhonebookResponse simPhonebookResponseInfo)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeTypedObject(info, 0);
          _data.writeTypedObject(simPhonebookResponseInfo, 0);
          boolean _status = this.mRemote.transact(10, _data, null, 1);
          if (!_status) {
            throw new RemoteException("Method getPhonebookEntryResponse is unimplemented.");
          }
        } finally {
          _data.recycle();
        }
      }

      @Override // vendor.samsung.hardware.radio.sim.ISehRadioSimResponse
      public void accessPhonebookEntryResponse(
          SehRadioResponseInfo info, int SimPhonebookAccessResp) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeTypedObject(info, 0);
          _data.writeInt(SimPhonebookAccessResp);
          boolean _status = this.mRemote.transact(11, _data, null, 1);
          if (!_status) {
            throw new RemoteException("Method accessPhonebookEntryResponse is unimplemented.");
          }
        } finally {
          _data.recycle();
        }
      }

      @Override // vendor.samsung.hardware.radio.sim.ISehRadioSimResponse
      public void getAtrResponse(SehRadioResponseInfo info, String atr) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeTypedObject(info, 0);
          _data.writeString(atr);
          boolean _status = this.mRemote.transact(12, _data, null, 1);
          if (!_status) {
            throw new RemoteException("Method getAtrResponse is unimplemented.");
          }
        } finally {
          _data.recycle();
        }
      }

      @Override // vendor.samsung.hardware.radio.sim.ISehRadioSimResponse
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

      @Override // vendor.samsung.hardware.radio.sim.ISehRadioSimResponse
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
