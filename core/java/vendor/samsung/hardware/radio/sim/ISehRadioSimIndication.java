package vendor.samsung.hardware.radio.sim;

import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;

/* loaded from: classes6.dex */
public interface ISehRadioSimIndication extends IInterface {
  public static final String DESCRIPTOR =
      "vendor$samsung$hardware$radio$sim$ISehRadioSimIndication".replace('$', '.');
  public static final String HASH = "e94363ef2041c98feabe84e756d983ad196394e8";
  public static final int VERSION = 1;

  String getInterfaceHash() throws RemoteException;

  int getInterfaceVersion() throws RemoteException;

  void phonebookInitCompleteIndication(int i) throws RemoteException;

  void releaseCompleteMessageIndication(int i, SehSsReleaseComplete sehSsReleaseComplete)
      throws RemoteException;

  void sapNotify(int i, byte[] bArr) throws RemoteException;

  void simCountMismatchedIndication(int i, int i2) throws RemoteException;

  void simOnOffStateChangedNotify(int i, int i2) throws RemoteException;

  void simPhonebookReadyIndication(int i) throws RemoteException;

  void simSwapStateChangedIndication(int i, int i2) throws RemoteException;

  void stkCallControlResultIndication(int i, String str) throws RemoteException;

  void stkSmsSendResultIndication(int i, int i2) throws RemoteException;

  public static class Default implements ISehRadioSimIndication {
    @Override // vendor.samsung.hardware.radio.sim.ISehRadioSimIndication
    public void simPhonebookReadyIndication(int indicationType) throws RemoteException {}

    @Override // vendor.samsung.hardware.radio.sim.ISehRadioSimIndication
    public void phonebookInitCompleteIndication(int indicationType) throws RemoteException {}

    @Override // vendor.samsung.hardware.radio.sim.ISehRadioSimIndication
    public void stkSmsSendResultIndication(int indicationType, int result) throws RemoteException {}

    @Override // vendor.samsung.hardware.radio.sim.ISehRadioSimIndication
    public void stkCallControlResultIndication(int indicationType, String cmd)
        throws RemoteException {}

    @Override // vendor.samsung.hardware.radio.sim.ISehRadioSimIndication
    public void simSwapStateChangedIndication(int indicationType, int state)
        throws RemoteException {}

    @Override // vendor.samsung.hardware.radio.sim.ISehRadioSimIndication
    public void simCountMismatchedIndication(int indicationType, int state)
        throws RemoteException {}

    @Override // vendor.samsung.hardware.radio.sim.ISehRadioSimIndication
    public void simOnOffStateChangedNotify(int indicationType, int mode) throws RemoteException {}

    @Override // vendor.samsung.hardware.radio.sim.ISehRadioSimIndication
    public void releaseCompleteMessageIndication(int indicationType, SehSsReleaseComplete result)
        throws RemoteException {}

    @Override // vendor.samsung.hardware.radio.sim.ISehRadioSimIndication
    public void sapNotify(int indicationType, byte[] data) throws RemoteException {}

    @Override // vendor.samsung.hardware.radio.sim.ISehRadioSimIndication
    public int getInterfaceVersion() {
      return 0;
    }

    @Override // vendor.samsung.hardware.radio.sim.ISehRadioSimIndication
    public String getInterfaceHash() {
      return "";
    }

    @Override // android.p009os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements ISehRadioSimIndication {
    static final int TRANSACTION_getInterfaceHash = 16777214;
    static final int TRANSACTION_getInterfaceVersion = 16777215;
    static final int TRANSACTION_phonebookInitCompleteIndication = 2;
    static final int TRANSACTION_releaseCompleteMessageIndication = 8;
    static final int TRANSACTION_sapNotify = 9;
    static final int TRANSACTION_simCountMismatchedIndication = 6;
    static final int TRANSACTION_simOnOffStateChangedNotify = 7;
    static final int TRANSACTION_simPhonebookReadyIndication = 1;
    static final int TRANSACTION_simSwapStateChangedIndication = 5;
    static final int TRANSACTION_stkCallControlResultIndication = 4;
    static final int TRANSACTION_stkSmsSendResultIndication = 3;

    public Stub() {
      markVintfStability();
      attachInterface(this, DESCRIPTOR);
    }

    public static ISehRadioSimIndication asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
      if (iin != null && (iin instanceof ISehRadioSimIndication)) {
        return (ISehRadioSimIndication) iin;
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
              data.enforceNoDataAvail();
              simPhonebookReadyIndication(_arg0);
              return true;
            case 2:
              int _arg02 = data.readInt();
              data.enforceNoDataAvail();
              phonebookInitCompleteIndication(_arg02);
              return true;
            case 3:
              int _arg03 = data.readInt();
              int _arg1 = data.readInt();
              data.enforceNoDataAvail();
              stkSmsSendResultIndication(_arg03, _arg1);
              return true;
            case 4:
              int _arg04 = data.readInt();
              String _arg12 = data.readString();
              data.enforceNoDataAvail();
              stkCallControlResultIndication(_arg04, _arg12);
              return true;
            case 5:
              int _arg05 = data.readInt();
              int _arg13 = data.readInt();
              data.enforceNoDataAvail();
              simSwapStateChangedIndication(_arg05, _arg13);
              return true;
            case 6:
              int _arg06 = data.readInt();
              int _arg14 = data.readInt();
              data.enforceNoDataAvail();
              simCountMismatchedIndication(_arg06, _arg14);
              return true;
            case 7:
              int _arg07 = data.readInt();
              int _arg15 = data.readInt();
              data.enforceNoDataAvail();
              simOnOffStateChangedNotify(_arg07, _arg15);
              return true;
            case 8:
              int _arg08 = data.readInt();
              SehSsReleaseComplete _arg16 =
                  (SehSsReleaseComplete) data.readTypedObject(SehSsReleaseComplete.CREATOR);
              data.enforceNoDataAvail();
              releaseCompleteMessageIndication(_arg08, _arg16);
              return true;
            case 9:
              int _arg09 = data.readInt();
              byte[] _arg17 = data.createByteArray();
              data.enforceNoDataAvail();
              sapNotify(_arg09, _arg17);
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements ISehRadioSimIndication {
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

      @Override // vendor.samsung.hardware.radio.sim.ISehRadioSimIndication
      public void simPhonebookReadyIndication(int indicationType) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeInt(indicationType);
          boolean _status = this.mRemote.transact(1, _data, null, 1);
          if (!_status) {
            throw new RemoteException("Method simPhonebookReadyIndication is unimplemented.");
          }
        } finally {
          _data.recycle();
        }
      }

      @Override // vendor.samsung.hardware.radio.sim.ISehRadioSimIndication
      public void phonebookInitCompleteIndication(int indicationType) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeInt(indicationType);
          boolean _status = this.mRemote.transact(2, _data, null, 1);
          if (!_status) {
            throw new RemoteException("Method phonebookInitCompleteIndication is unimplemented.");
          }
        } finally {
          _data.recycle();
        }
      }

      @Override // vendor.samsung.hardware.radio.sim.ISehRadioSimIndication
      public void stkSmsSendResultIndication(int indicationType, int result)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeInt(indicationType);
          _data.writeInt(result);
          boolean _status = this.mRemote.transact(3, _data, null, 1);
          if (!_status) {
            throw new RemoteException("Method stkSmsSendResultIndication is unimplemented.");
          }
        } finally {
          _data.recycle();
        }
      }

      @Override // vendor.samsung.hardware.radio.sim.ISehRadioSimIndication
      public void stkCallControlResultIndication(int indicationType, String cmd)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeInt(indicationType);
          _data.writeString(cmd);
          boolean _status = this.mRemote.transact(4, _data, null, 1);
          if (!_status) {
            throw new RemoteException("Method stkCallControlResultIndication is unimplemented.");
          }
        } finally {
          _data.recycle();
        }
      }

      @Override // vendor.samsung.hardware.radio.sim.ISehRadioSimIndication
      public void simSwapStateChangedIndication(int indicationType, int state)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeInt(indicationType);
          _data.writeInt(state);
          boolean _status = this.mRemote.transact(5, _data, null, 1);
          if (!_status) {
            throw new RemoteException("Method simSwapStateChangedIndication is unimplemented.");
          }
        } finally {
          _data.recycle();
        }
      }

      @Override // vendor.samsung.hardware.radio.sim.ISehRadioSimIndication
      public void simCountMismatchedIndication(int indicationType, int state)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeInt(indicationType);
          _data.writeInt(state);
          boolean _status = this.mRemote.transact(6, _data, null, 1);
          if (!_status) {
            throw new RemoteException("Method simCountMismatchedIndication is unimplemented.");
          }
        } finally {
          _data.recycle();
        }
      }

      @Override // vendor.samsung.hardware.radio.sim.ISehRadioSimIndication
      public void simOnOffStateChangedNotify(int indicationType, int mode) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeInt(indicationType);
          _data.writeInt(mode);
          boolean _status = this.mRemote.transact(7, _data, null, 1);
          if (!_status) {
            throw new RemoteException("Method simOnOffStateChangedNotify is unimplemented.");
          }
        } finally {
          _data.recycle();
        }
      }

      @Override // vendor.samsung.hardware.radio.sim.ISehRadioSimIndication
      public void releaseCompleteMessageIndication(int indicationType, SehSsReleaseComplete result)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeInt(indicationType);
          _data.writeTypedObject(result, 0);
          boolean _status = this.mRemote.transact(8, _data, null, 1);
          if (!_status) {
            throw new RemoteException("Method releaseCompleteMessageIndication is unimplemented.");
          }
        } finally {
          _data.recycle();
        }
      }

      @Override // vendor.samsung.hardware.radio.sim.ISehRadioSimIndication
      public void sapNotify(int indicationType, byte[] data) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeInt(indicationType);
          _data.writeByteArray(data);
          boolean _status = this.mRemote.transact(9, _data, null, 1);
          if (!_status) {
            throw new RemoteException("Method sapNotify is unimplemented.");
          }
        } finally {
          _data.recycle();
        }
      }

      @Override // vendor.samsung.hardware.radio.sim.ISehRadioSimIndication
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

      @Override // vendor.samsung.hardware.radio.sim.ISehRadioSimIndication
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
