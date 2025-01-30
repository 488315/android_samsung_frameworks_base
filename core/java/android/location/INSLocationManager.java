package android.location;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Message;
import android.os.Parcel;
import android.os.RemoteException;
import java.util.Map;

/* loaded from: classes2.dex */
public interface INSLocationManager extends IInterface {
  public static final String DESCRIPTOR = "android.location.INSLocationManager";

  Map getGPSUsingApps() throws RemoteException;

  void onGnssEngineStatusUpdated(boolean z) throws RemoteException;

  void onGnssEventUpdated(String str) throws RemoteException;

  void onMessageUpdated(Message message) throws RemoteException;

  void onPassiveLocationReported(Location location) throws RemoteException;

  void onSatelliteStatusUpdated(GnssStatus gnssStatus) throws RemoteException;

  void onStateUpdated(LocationConstants.STATE_TYPE state_type, Bundle bundle)
      throws RemoteException;

  void setCallback(INSLocationCallback iNSLocationCallback) throws RemoteException;

  public static class Default implements INSLocationManager {
    @Override // android.location.INSLocationManager
    public Map getGPSUsingApps() throws RemoteException {
      return null;
    }

    @Override // android.location.INSLocationManager
    public void setCallback(INSLocationCallback callback) throws RemoteException {}

    @Override // android.location.INSLocationManager
    public void onMessageUpdated(Message msg) throws RemoteException {}

    @Override // android.location.INSLocationManager
    public void onStateUpdated(LocationConstants.STATE_TYPE stateType, Bundle bundle)
        throws RemoteException {}

    @Override // android.location.INSLocationManager
    public void onPassiveLocationReported(Location location) throws RemoteException {}

    @Override // android.location.INSLocationManager
    public void onGnssEngineStatusUpdated(boolean enabled) throws RemoteException {}

    @Override // android.location.INSLocationManager
    public void onSatelliteStatusUpdated(GnssStatus gnssStatus) throws RemoteException {}

    @Override // android.location.INSLocationManager
    public void onGnssEventUpdated(String message) throws RemoteException {}

    @Override // android.os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements INSLocationManager {
    static final int TRANSACTION_getGPSUsingApps = 1;
    static final int TRANSACTION_onGnssEngineStatusUpdated = 6;
    static final int TRANSACTION_onGnssEventUpdated = 8;
    static final int TRANSACTION_onMessageUpdated = 3;
    static final int TRANSACTION_onPassiveLocationReported = 5;
    static final int TRANSACTION_onSatelliteStatusUpdated = 7;
    static final int TRANSACTION_onStateUpdated = 4;
    static final int TRANSACTION_setCallback = 2;

    public Stub() {
      attachInterface(this, INSLocationManager.DESCRIPTOR);
    }

    public static INSLocationManager asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(INSLocationManager.DESCRIPTOR);
      if (iin != null && (iin instanceof INSLocationManager)) {
        return (INSLocationManager) iin;
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
          return "getGPSUsingApps";
        case 2:
          return "setCallback";
        case 3:
          return "onMessageUpdated";
        case 4:
          return "onStateUpdated";
        case 5:
          return "onPassiveLocationReported";
        case 6:
          return "onGnssEngineStatusUpdated";
        case 7:
          return "onSatelliteStatusUpdated";
        case 8:
          return "onGnssEventUpdated";
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
        data.enforceInterface(INSLocationManager.DESCRIPTOR);
      }
      switch (code) {
        case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
          reply.writeString(INSLocationManager.DESCRIPTOR);
          return true;
        default:
          switch (code) {
            case 1:
              Map _result = getGPSUsingApps();
              reply.writeNoException();
              reply.writeMap(_result);
              return true;
            case 2:
              INSLocationCallback _arg0 =
                  INSLocationCallback.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              setCallback(_arg0);
              reply.writeNoException();
              return true;
            case 3:
              Message _arg02 = (Message) data.readTypedObject(Message.CREATOR);
              data.enforceNoDataAvail();
              onMessageUpdated(_arg02);
              reply.writeNoException();
              return true;
            case 4:
              LocationConstants.STATE_TYPE _arg03 =
                  (LocationConstants.STATE_TYPE)
                      data.readTypedObject(LocationConstants.STATE_TYPE.CREATOR);
              Bundle _arg1 = (Bundle) data.readTypedObject(Bundle.CREATOR);
              data.enforceNoDataAvail();
              onStateUpdated(_arg03, _arg1);
              reply.writeNoException();
              return true;
            case 5:
              Location _arg04 = (Location) data.readTypedObject(Location.CREATOR);
              data.enforceNoDataAvail();
              onPassiveLocationReported(_arg04);
              reply.writeNoException();
              return true;
            case 6:
              boolean _arg05 = data.readBoolean();
              data.enforceNoDataAvail();
              onGnssEngineStatusUpdated(_arg05);
              reply.writeNoException();
              return true;
            case 7:
              GnssStatus _arg06 = (GnssStatus) data.readTypedObject(GnssStatus.CREATOR);
              data.enforceNoDataAvail();
              onSatelliteStatusUpdated(_arg06);
              reply.writeNoException();
              return true;
            case 8:
              String _arg07 = data.readString();
              data.enforceNoDataAvail();
              onGnssEventUpdated(_arg07);
              reply.writeNoException();
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements INSLocationManager {
      private IBinder mRemote;

      Proxy(IBinder remote) {
        this.mRemote = remote;
      }

      @Override // android.os.IInterface
      public IBinder asBinder() {
        return this.mRemote;
      }

      public String getInterfaceDescriptor() {
        return INSLocationManager.DESCRIPTOR;
      }

      @Override // android.location.INSLocationManager
      public Map getGPSUsingApps() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(INSLocationManager.DESCRIPTOR);
          this.mRemote.transact(1, _data, _reply, 0);
          _reply.readException();
          ClassLoader cl = getClass().getClassLoader();
          Map _result = _reply.readHashMap(cl);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.location.INSLocationManager
      public void setCallback(INSLocationCallback callback) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(INSLocationManager.DESCRIPTOR);
          _data.writeStrongInterface(callback);
          this.mRemote.transact(2, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.location.INSLocationManager
      public void onMessageUpdated(Message msg) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(INSLocationManager.DESCRIPTOR);
          _data.writeTypedObject(msg, 0);
          this.mRemote.transact(3, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.location.INSLocationManager
      public void onStateUpdated(LocationConstants.STATE_TYPE stateType, Bundle bundle)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(INSLocationManager.DESCRIPTOR);
          _data.writeTypedObject(stateType, 0);
          _data.writeTypedObject(bundle, 0);
          this.mRemote.transact(4, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.location.INSLocationManager
      public void onPassiveLocationReported(Location location) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(INSLocationManager.DESCRIPTOR);
          _data.writeTypedObject(location, 0);
          this.mRemote.transact(5, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.location.INSLocationManager
      public void onGnssEngineStatusUpdated(boolean enabled) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(INSLocationManager.DESCRIPTOR);
          _data.writeBoolean(enabled);
          this.mRemote.transact(6, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.location.INSLocationManager
      public void onSatelliteStatusUpdated(GnssStatus gnssStatus) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(INSLocationManager.DESCRIPTOR);
          _data.writeTypedObject(gnssStatus, 0);
          this.mRemote.transact(7, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.location.INSLocationManager
      public void onGnssEventUpdated(String message) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(INSLocationManager.DESCRIPTOR);
          _data.writeString(message);
          this.mRemote.transact(8, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }
    }

    @Override // android.os.Binder
    public int getMaxTransactionId() {
      return 7;
    }
  }
}
