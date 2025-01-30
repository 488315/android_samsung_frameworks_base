package vendor.samsung.hardware.authfw;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes6.dex */
public interface ISehAuthenticationFramework extends IInterface {
  public static final String DESCRIPTOR =
      "vendor$samsung$hardware$authfw$ISehAuthenticationFramework".replace('$', '.');
  public static final String HASH = "ea997bca08b7fab1afc1f53991c5a068408d1670";
  public static final int VERSION = 1;

  SehResult execute(int i, byte[] bArr) throws RemoteException;

  String getInterfaceHash() throws RemoteException;

  int getInterfaceVersion() throws RemoteException;

  boolean load(int i, byte[] bArr) throws RemoteException;

  boolean terminate(int i) throws RemoteException;

  public static class Default implements ISehAuthenticationFramework {
    @Override // vendor.samsung.hardware.authfw.ISehAuthenticationFramework
    public boolean load(int trustedAppType, byte[] fileBuffer) throws RemoteException {
      return false;
    }

    @Override // vendor.samsung.hardware.authfw.ISehAuthenticationFramework
    public boolean terminate(int trustedAppType) throws RemoteException {
      return false;
    }

    @Override // vendor.samsung.hardware.authfw.ISehAuthenticationFramework
    public SehResult execute(int trustedAppType, byte[] command) throws RemoteException {
      return null;
    }

    @Override // vendor.samsung.hardware.authfw.ISehAuthenticationFramework
    public int getInterfaceVersion() {
      return 0;
    }

    @Override // vendor.samsung.hardware.authfw.ISehAuthenticationFramework
    public String getInterfaceHash() {
      return "";
    }

    @Override // android.os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements ISehAuthenticationFramework {
    static final int TRANSACTION_execute = 3;
    static final int TRANSACTION_getInterfaceHash = 16777214;
    static final int TRANSACTION_getInterfaceVersion = 16777215;
    static final int TRANSACTION_load = 1;
    static final int TRANSACTION_terminate = 2;

    public Stub() {
      markVintfStability();
      attachInterface(this, DESCRIPTOR);
    }

    public static ISehAuthenticationFramework asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
      if (iin != null && (iin instanceof ISehAuthenticationFramework)) {
        return (ISehAuthenticationFramework) iin;
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
              byte[] _arg1 = data.createByteArray();
              data.enforceNoDataAvail();
              boolean _result = load(_arg0, _arg1);
              reply.writeNoException();
              reply.writeBoolean(_result);
              return true;
            case 2:
              int _arg02 = data.readInt();
              data.enforceNoDataAvail();
              boolean _result2 = terminate(_arg02);
              reply.writeNoException();
              reply.writeBoolean(_result2);
              return true;
            case 3:
              int _arg03 = data.readInt();
              byte[] _arg12 = data.createByteArray();
              data.enforceNoDataAvail();
              SehResult _result3 = execute(_arg03, _arg12);
              reply.writeNoException();
              reply.writeTypedObject(_result3, 1);
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements ISehAuthenticationFramework {
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

      @Override // vendor.samsung.hardware.authfw.ISehAuthenticationFramework
      public boolean load(int trustedAppType, byte[] fileBuffer) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeInt(trustedAppType);
          _data.writeByteArray(fileBuffer);
          boolean _status = this.mRemote.transact(1, _data, _reply, 0);
          if (!_status) {
            throw new RemoteException("Method load is unimplemented.");
          }
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // vendor.samsung.hardware.authfw.ISehAuthenticationFramework
      public boolean terminate(int trustedAppType) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeInt(trustedAppType);
          boolean _status = this.mRemote.transact(2, _data, _reply, 0);
          if (!_status) {
            throw new RemoteException("Method terminate is unimplemented.");
          }
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // vendor.samsung.hardware.authfw.ISehAuthenticationFramework
      public SehResult execute(int trustedAppType, byte[] command) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeInt(trustedAppType);
          _data.writeByteArray(command);
          boolean _status = this.mRemote.transact(3, _data, _reply, 0);
          if (!_status) {
            throw new RemoteException("Method execute is unimplemented.");
          }
          _reply.readException();
          SehResult _result = (SehResult) _reply.readTypedObject(SehResult.CREATOR);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // vendor.samsung.hardware.authfw.ISehAuthenticationFramework
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

      @Override // vendor.samsung.hardware.authfw.ISehAuthenticationFramework
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
