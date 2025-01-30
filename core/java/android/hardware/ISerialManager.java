package android.hardware;

import android.Manifest;
import android.app.ActivityThread;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.PermissionEnforcer;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface ISerialManager extends IInterface {
  String[] getSerialPorts() throws RemoteException;

  ParcelFileDescriptor openSerialPort(String str) throws RemoteException;

  public static class Default implements ISerialManager {
    @Override // android.hardware.ISerialManager
    public String[] getSerialPorts() throws RemoteException {
      return null;
    }

    @Override // android.hardware.ISerialManager
    public ParcelFileDescriptor openSerialPort(String name) throws RemoteException {
      return null;
    }

    @Override // android.os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements ISerialManager {
    public static final String DESCRIPTOR = "android.hardware.ISerialManager";
    static final int TRANSACTION_getSerialPorts = 1;
    static final int TRANSACTION_openSerialPort = 2;
    private final PermissionEnforcer mEnforcer;

    public Stub(PermissionEnforcer enforcer) {
      attachInterface(this, DESCRIPTOR);
      if (enforcer == null) {
        throw new IllegalArgumentException("enforcer cannot be null");
      }
      this.mEnforcer = enforcer;
    }

    @Deprecated
    public Stub() {
      this(
          PermissionEnforcer.fromContext(
              ActivityThread.currentActivityThread().getSystemContext()));
    }

    public static ISerialManager asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
      if (iin != null && (iin instanceof ISerialManager)) {
        return (ISerialManager) iin;
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
          return "getSerialPorts";
        case 2:
          return "openSerialPort";
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
              String[] _result = getSerialPorts();
              reply.writeNoException();
              reply.writeStringArray(_result);
              return true;
            case 2:
              String _arg0 = data.readString();
              data.enforceNoDataAvail();
              ParcelFileDescriptor _result2 = openSerialPort(_arg0);
              reply.writeNoException();
              reply.writeTypedObject(_result2, 1);
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements ISerialManager {
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

      @Override // android.hardware.ISerialManager
      public String[] getSerialPorts() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          this.mRemote.transact(1, _data, _reply, 0);
          _reply.readException();
          String[] _result = _reply.createStringArray();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.hardware.ISerialManager
      public ParcelFileDescriptor openSerialPort(String name) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(name);
          this.mRemote.transact(2, _data, _reply, 0);
          _reply.readException();
          ParcelFileDescriptor _result =
              (ParcelFileDescriptor) _reply.readTypedObject(ParcelFileDescriptor.CREATOR);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }
    }

    protected void getSerialPorts_enforcePermission() throws SecurityException {
      this.mEnforcer.enforcePermission(
          Manifest.permission.SERIAL_PORT, getCallingPid(), getCallingUid());
    }

    protected void openSerialPort_enforcePermission() throws SecurityException {
      this.mEnforcer.enforcePermission(
          Manifest.permission.SERIAL_PORT, getCallingPid(), getCallingUid());
    }

    @Override // android.os.Binder
    public int getMaxTransactionId() {
      return 1;
    }
  }
}
