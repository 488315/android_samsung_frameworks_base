package android.hardware.location;

import android.location.Location;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface IGeofenceHardwareCallback extends IInterface {
  void onGeofenceAdd(int i, int i2) throws RemoteException;

  void onGeofencePause(int i, int i2) throws RemoteException;

  void onGeofenceRemove(int i, int i2) throws RemoteException;

  void onGeofenceResume(int i, int i2) throws RemoteException;

  void onGeofenceTransition(int i, int i2, Location location, long j, int i3)
      throws RemoteException;

  public static class Default implements IGeofenceHardwareCallback {
    @Override // android.hardware.location.IGeofenceHardwareCallback
    public void onGeofenceTransition(
        int geofenceId, int transition, Location location, long timestamp, int monitoringType)
        throws RemoteException {}

    @Override // android.hardware.location.IGeofenceHardwareCallback
    public void onGeofenceAdd(int geofenceId, int status) throws RemoteException {}

    @Override // android.hardware.location.IGeofenceHardwareCallback
    public void onGeofenceRemove(int geofenceId, int status) throws RemoteException {}

    @Override // android.hardware.location.IGeofenceHardwareCallback
    public void onGeofencePause(int geofenceId, int status) throws RemoteException {}

    @Override // android.hardware.location.IGeofenceHardwareCallback
    public void onGeofenceResume(int geofenceId, int status) throws RemoteException {}

    @Override // android.os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements IGeofenceHardwareCallback {
    public static final String DESCRIPTOR = "android.hardware.location.IGeofenceHardwareCallback";
    static final int TRANSACTION_onGeofenceAdd = 2;
    static final int TRANSACTION_onGeofencePause = 4;
    static final int TRANSACTION_onGeofenceRemove = 3;
    static final int TRANSACTION_onGeofenceResume = 5;
    static final int TRANSACTION_onGeofenceTransition = 1;

    public Stub() {
      attachInterface(this, DESCRIPTOR);
    }

    public static IGeofenceHardwareCallback asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
      if (iin != null && (iin instanceof IGeofenceHardwareCallback)) {
        return (IGeofenceHardwareCallback) iin;
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
          return "onGeofenceTransition";
        case 2:
          return "onGeofenceAdd";
        case 3:
          return "onGeofenceRemove";
        case 4:
          return "onGeofencePause";
        case 5:
          return "onGeofenceResume";
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
              int _arg0 = data.readInt();
              int _arg1 = data.readInt();
              Location _arg2 = (Location) data.readTypedObject(Location.CREATOR);
              long _arg3 = data.readLong();
              int _arg4 = data.readInt();
              data.enforceNoDataAvail();
              onGeofenceTransition(_arg0, _arg1, _arg2, _arg3, _arg4);
              return true;
            case 2:
              int _arg02 = data.readInt();
              int _arg12 = data.readInt();
              data.enforceNoDataAvail();
              onGeofenceAdd(_arg02, _arg12);
              return true;
            case 3:
              int _arg03 = data.readInt();
              int _arg13 = data.readInt();
              data.enforceNoDataAvail();
              onGeofenceRemove(_arg03, _arg13);
              return true;
            case 4:
              int _arg04 = data.readInt();
              int _arg14 = data.readInt();
              data.enforceNoDataAvail();
              onGeofencePause(_arg04, _arg14);
              return true;
            case 5:
              int _arg05 = data.readInt();
              int _arg15 = data.readInt();
              data.enforceNoDataAvail();
              onGeofenceResume(_arg05, _arg15);
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements IGeofenceHardwareCallback {
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

      @Override // android.hardware.location.IGeofenceHardwareCallback
      public void onGeofenceTransition(
          int geofenceId, int transition, Location location, long timestamp, int monitoringType)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(geofenceId);
          _data.writeInt(transition);
          _data.writeTypedObject(location, 0);
          _data.writeLong(timestamp);
          _data.writeInt(monitoringType);
          this.mRemote.transact(1, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.hardware.location.IGeofenceHardwareCallback
      public void onGeofenceAdd(int geofenceId, int status) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(geofenceId);
          _data.writeInt(status);
          this.mRemote.transact(2, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.hardware.location.IGeofenceHardwareCallback
      public void onGeofenceRemove(int geofenceId, int status) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(geofenceId);
          _data.writeInt(status);
          this.mRemote.transact(3, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.hardware.location.IGeofenceHardwareCallback
      public void onGeofencePause(int geofenceId, int status) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(geofenceId);
          _data.writeInt(status);
          this.mRemote.transact(4, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.hardware.location.IGeofenceHardwareCallback
      public void onGeofenceResume(int geofenceId, int status) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(geofenceId);
          _data.writeInt(status);
          this.mRemote.transact(5, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }
    }

    @Override // android.os.Binder
    public int getMaxTransactionId() {
      return 4;
    }
  }
}
