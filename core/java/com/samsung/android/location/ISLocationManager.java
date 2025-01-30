package com.samsung.android.location;

import android.app.PendingIntent;
import android.location.Location;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import java.util.List;

/* loaded from: classes5.dex */
public interface ISLocationManager extends IInterface {
  public static final String DESCRIPTOR = "com.samsung.android.location.ISLocationManager";

  int addGeofences(List<SemGeofence> list, PendingIntent pendingIntent, String str, String str2)
      throws RemoteException;

  void flushBatchedLocations() throws RemoteException;

  boolean isAvailable(int i) throws RemoteException;

  void notifyAppForeground(int i, boolean z) throws RemoteException;

  void onGnssStatusChanged(boolean z) throws RemoteException;

  void onSvStatusChanged(
      int i, int[] iArr, float[] fArr, float[] fArr2, float[] fArr3, float[] fArr4, float[] fArr5)
      throws RemoteException;

  int removeBatchedLocations(
      PendingIntent pendingIntent, ISLocationBatchingListener iSLocationBatchingListener)
      throws RemoteException;

  int removeGeofences(List<String> list, String str, String str2) throws RemoteException;

  int removeGeofencesPendingIntent(PendingIntent pendingIntent) throws RemoteException;

  int removeLocation(ISLocationListener iSLocationListener) throws RemoteException;

  void removePassiveLocation(PendingIntent pendingIntent, ISLocationListener iSLocationListener)
      throws RemoteException;

  int removeSingleLocation(PendingIntent pendingIntent, ISLocationListener iSLocationListener)
      throws RemoteException;

  void reportGpsGeofenceAddStatus(int i, int i2) throws RemoteException;

  void reportGpsGeofencePauseStatus(int i, int i2) throws RemoteException;

  void reportGpsGeofenceRemoveStatus(int i, int i2) throws RemoteException;

  void reportGpsGeofenceResumeStatus(int i, int i2) throws RemoteException;

  void reportGpsGeofenceStatus(int i, Location location) throws RemoteException;

  void reportGpsGeofenceTransition(int i, Location location, int i2, long j) throws RemoteException;

  int requestBatchedLocations(
      SemLocationBatchingRequest semLocationBatchingRequest,
      PendingIntent pendingIntent,
      ISLocationBatchingListener iSLocationBatchingListener,
      String str,
      String str2)
      throws RemoteException;

  int requestLocation(boolean z, ISLocationListener iSLocationListener, String str, String str2)
      throws RemoteException;

  int requestMostAccurateLocation(
      int i,
      int i2,
      int i3,
      PendingIntent pendingIntent,
      ISLocationListener iSLocationListener,
      String str,
      String str2)
      throws RemoteException;

  void requestPassiveLocation(
      PendingIntent pendingIntent, ISLocationListener iSLocationListener, String str, String str2)
      throws RemoteException;

  int requestSingleLocation(
      int i,
      int i2,
      boolean z,
      PendingIntent pendingIntent,
      ISLocationListener iSLocationListener,
      String str,
      String str2)
      throws RemoteException;

  void setSLocationLMSHook(ISLocationLMSHook iSLocationLMSHook) throws RemoteException;

  public static class Default implements ISLocationManager {
    @Override // com.samsung.android.location.ISLocationManager
    public boolean isAvailable(int module) throws RemoteException {
      return false;
    }

    @Override // com.samsung.android.location.ISLocationManager
    public int removeLocation(ISLocationListener listener) throws RemoteException {
      return 0;
    }

    @Override // com.samsung.android.location.ISLocationManager
    public int removeSingleLocation(PendingIntent intent, ISLocationListener listener)
        throws RemoteException {
      return 0;
    }

    @Override // com.samsung.android.location.ISLocationManager
    public int requestSingleLocation(
        int accuracy,
        int timeout,
        boolean isAddress,
        PendingIntent intent,
        ISLocationListener listener,
        String packageName,
        String attributionTag)
        throws RemoteException {
      return 0;
    }

    @Override // com.samsung.android.location.ISLocationManager
    public int requestMostAccurateLocation(
        int accuracyLimit,
        int requestTimeout,
        int locationTimeout,
        PendingIntent intent,
        ISLocationListener listener,
        String packageName,
        String attributionTag)
        throws RemoteException {
      return 0;
    }

    @Override // com.samsung.android.location.ISLocationManager
    public int requestLocation(
        boolean isAddress, ISLocationListener listener, String packageName, String attributionTag)
        throws RemoteException {
      return 0;
    }

    @Override // com.samsung.android.location.ISLocationManager
    public void requestPassiveLocation(
        PendingIntent intent,
        ISLocationListener listener,
        String packageName,
        String attributionTag)
        throws RemoteException {}

    @Override // com.samsung.android.location.ISLocationManager
    public int requestBatchedLocations(
        SemLocationBatchingRequest req,
        PendingIntent intent,
        ISLocationBatchingListener listener,
        String packageName,
        String attributionTag)
        throws RemoteException {
      return 0;
    }

    @Override // com.samsung.android.location.ISLocationManager
    public int addGeofences(
        List<SemGeofence> params, PendingIntent intent, String packageName, String attributionTag)
        throws RemoteException {
      return 0;
    }

    @Override // com.samsung.android.location.ISLocationManager
    public void reportGpsGeofenceTransition(
        int geofenceId, Location location, int transition, long transitionTimestamp)
        throws RemoteException {}

    @Override // com.samsung.android.location.ISLocationManager
    public void reportGpsGeofenceStatus(int status, Location location) throws RemoteException {}

    @Override // com.samsung.android.location.ISLocationManager
    public void reportGpsGeofenceAddStatus(int geofenceId, int status) throws RemoteException {}

    @Override // com.samsung.android.location.ISLocationManager
    public void reportGpsGeofenceRemoveStatus(int geofenceId, int status) throws RemoteException {}

    @Override // com.samsung.android.location.ISLocationManager
    public void reportGpsGeofencePauseStatus(int geofenceId, int status) throws RemoteException {}

    @Override // com.samsung.android.location.ISLocationManager
    public void reportGpsGeofenceResumeStatus(int geofenceId, int status) throws RemoteException {}

    @Override // com.samsung.android.location.ISLocationManager
    public void removePassiveLocation(PendingIntent intent, ISLocationListener listener)
        throws RemoteException {}

    @Override // com.samsung.android.location.ISLocationManager
    public int removeBatchedLocations(PendingIntent intent, ISLocationBatchingListener listener)
        throws RemoteException {
      return 0;
    }

    @Override // com.samsung.android.location.ISLocationManager
    public void flushBatchedLocations() throws RemoteException {}

    @Override // com.samsung.android.location.ISLocationManager
    public void onSvStatusChanged(
        int svCount,
        int[] svidWithFlags,
        float[] cn0s,
        float[] svElevations,
        float[] svAzimuths,
        float[] svCarrierFreqs,
        float[] basebandCn0s)
        throws RemoteException {}

    @Override // com.samsung.android.location.ISLocationManager
    public void onGnssStatusChanged(boolean isNavigating) throws RemoteException {}

    @Override // com.samsung.android.location.ISLocationManager
    public int removeGeofences(List<String> names, String packageName, String attributionTag)
        throws RemoteException {
      return 0;
    }

    @Override // com.samsung.android.location.ISLocationManager
    public int removeGeofencesPendingIntent(PendingIntent intent) throws RemoteException {
      return 0;
    }

    @Override // com.samsung.android.location.ISLocationManager
    public void setSLocationLMSHook(ISLocationLMSHook iSLocationLMSHook) throws RemoteException {}

    @Override // com.samsung.android.location.ISLocationManager
    public void notifyAppForeground(int uid, boolean foreground) throws RemoteException {}

    @Override // android.os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements ISLocationManager {
    static final int TRANSACTION_addGeofences = 9;
    static final int TRANSACTION_flushBatchedLocations = 18;
    static final int TRANSACTION_isAvailable = 1;
    static final int TRANSACTION_notifyAppForeground = 24;
    static final int TRANSACTION_onGnssStatusChanged = 20;
    static final int TRANSACTION_onSvStatusChanged = 19;
    static final int TRANSACTION_removeBatchedLocations = 17;
    static final int TRANSACTION_removeGeofences = 21;
    static final int TRANSACTION_removeGeofencesPendingIntent = 22;
    static final int TRANSACTION_removeLocation = 2;
    static final int TRANSACTION_removePassiveLocation = 16;
    static final int TRANSACTION_removeSingleLocation = 3;
    static final int TRANSACTION_reportGpsGeofenceAddStatus = 12;
    static final int TRANSACTION_reportGpsGeofencePauseStatus = 14;
    static final int TRANSACTION_reportGpsGeofenceRemoveStatus = 13;
    static final int TRANSACTION_reportGpsGeofenceResumeStatus = 15;
    static final int TRANSACTION_reportGpsGeofenceStatus = 11;
    static final int TRANSACTION_reportGpsGeofenceTransition = 10;
    static final int TRANSACTION_requestBatchedLocations = 8;
    static final int TRANSACTION_requestLocation = 6;
    static final int TRANSACTION_requestMostAccurateLocation = 5;
    static final int TRANSACTION_requestPassiveLocation = 7;
    static final int TRANSACTION_requestSingleLocation = 4;
    static final int TRANSACTION_setSLocationLMSHook = 23;

    public Stub() {
      attachInterface(this, ISLocationManager.DESCRIPTOR);
    }

    public static ISLocationManager asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(ISLocationManager.DESCRIPTOR);
      if (iin != null && (iin instanceof ISLocationManager)) {
        return (ISLocationManager) iin;
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
          return "isAvailable";
        case 2:
          return "removeLocation";
        case 3:
          return "removeSingleLocation";
        case 4:
          return "requestSingleLocation";
        case 5:
          return "requestMostAccurateLocation";
        case 6:
          return "requestLocation";
        case 7:
          return "requestPassiveLocation";
        case 8:
          return "requestBatchedLocations";
        case 9:
          return "addGeofences";
        case 10:
          return "reportGpsGeofenceTransition";
        case 11:
          return "reportGpsGeofenceStatus";
        case 12:
          return "reportGpsGeofenceAddStatus";
        case 13:
          return "reportGpsGeofenceRemoveStatus";
        case 14:
          return "reportGpsGeofencePauseStatus";
        case 15:
          return "reportGpsGeofenceResumeStatus";
        case 16:
          return "removePassiveLocation";
        case 17:
          return "removeBatchedLocations";
        case 18:
          return "flushBatchedLocations";
        case 19:
          return "onSvStatusChanged";
        case 20:
          return "onGnssStatusChanged";
        case 21:
          return "removeGeofences";
        case 22:
          return "removeGeofencesPendingIntent";
        case 23:
          return "setSLocationLMSHook";
        case 24:
          return "notifyAppForeground";
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
        data.enforceInterface(ISLocationManager.DESCRIPTOR);
      }
      switch (code) {
        case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
          reply.writeString(ISLocationManager.DESCRIPTOR);
          return true;
        default:
          switch (code) {
            case 1:
              int _arg0 = data.readInt();
              data.enforceNoDataAvail();
              boolean _result = isAvailable(_arg0);
              reply.writeNoException();
              reply.writeBoolean(_result);
              return true;
            case 2:
              ISLocationListener _arg02 =
                  ISLocationListener.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              int _result2 = removeLocation(_arg02);
              reply.writeNoException();
              reply.writeInt(_result2);
              return true;
            case 3:
              PendingIntent _arg03 = (PendingIntent) data.readTypedObject(PendingIntent.CREATOR);
              ISLocationListener _arg1 =
                  ISLocationListener.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              int _result3 = removeSingleLocation(_arg03, _arg1);
              reply.writeNoException();
              reply.writeInt(_result3);
              return true;
            case 4:
              int _arg04 = data.readInt();
              int _arg12 = data.readInt();
              boolean _arg2 = data.readBoolean();
              PendingIntent _arg3 = (PendingIntent) data.readTypedObject(PendingIntent.CREATOR);
              ISLocationListener _arg4 =
                  ISLocationListener.Stub.asInterface(data.readStrongBinder());
              String _arg5 = data.readString();
              String _arg6 = data.readString();
              data.enforceNoDataAvail();
              int _result4 =
                  requestSingleLocation(_arg04, _arg12, _arg2, _arg3, _arg4, _arg5, _arg6);
              reply.writeNoException();
              reply.writeInt(_result4);
              return true;
            case 5:
              int _arg05 = data.readInt();
              int _arg13 = data.readInt();
              int _arg22 = data.readInt();
              PendingIntent _arg32 = (PendingIntent) data.readTypedObject(PendingIntent.CREATOR);
              ISLocationListener _arg42 =
                  ISLocationListener.Stub.asInterface(data.readStrongBinder());
              String _arg52 = data.readString();
              String _arg62 = data.readString();
              data.enforceNoDataAvail();
              int _result5 =
                  requestMostAccurateLocation(
                      _arg05, _arg13, _arg22, _arg32, _arg42, _arg52, _arg62);
              reply.writeNoException();
              reply.writeInt(_result5);
              return true;
            case 6:
              boolean _arg06 = data.readBoolean();
              ISLocationListener _arg14 =
                  ISLocationListener.Stub.asInterface(data.readStrongBinder());
              String _arg23 = data.readString();
              String _arg33 = data.readString();
              data.enforceNoDataAvail();
              int _result6 = requestLocation(_arg06, _arg14, _arg23, _arg33);
              reply.writeNoException();
              reply.writeInt(_result6);
              return true;
            case 7:
              PendingIntent _arg07 = (PendingIntent) data.readTypedObject(PendingIntent.CREATOR);
              ISLocationListener _arg15 =
                  ISLocationListener.Stub.asInterface(data.readStrongBinder());
              String _arg24 = data.readString();
              String _arg34 = data.readString();
              data.enforceNoDataAvail();
              requestPassiveLocation(_arg07, _arg15, _arg24, _arg34);
              reply.writeNoException();
              return true;
            case 8:
              SemLocationBatchingRequest _arg08 =
                  (SemLocationBatchingRequest)
                      data.readTypedObject(SemLocationBatchingRequest.CREATOR);
              PendingIntent _arg16 = (PendingIntent) data.readTypedObject(PendingIntent.CREATOR);
              ISLocationBatchingListener _arg25 =
                  ISLocationBatchingListener.Stub.asInterface(data.readStrongBinder());
              String _arg35 = data.readString();
              String _arg43 = data.readString();
              data.enforceNoDataAvail();
              int _result7 = requestBatchedLocations(_arg08, _arg16, _arg25, _arg35, _arg43);
              reply.writeNoException();
              reply.writeInt(_result7);
              return true;
            case 9:
              List<SemGeofence> _arg09 = data.createTypedArrayList(SemGeofence.CREATOR);
              PendingIntent _arg17 = (PendingIntent) data.readTypedObject(PendingIntent.CREATOR);
              String _arg26 = data.readString();
              String _arg36 = data.readString();
              data.enforceNoDataAvail();
              int _result8 = addGeofences(_arg09, _arg17, _arg26, _arg36);
              reply.writeNoException();
              reply.writeInt(_result8);
              return true;
            case 10:
              int _arg010 = data.readInt();
              Location _arg18 = (Location) data.readTypedObject(Location.CREATOR);
              int _arg27 = data.readInt();
              long _arg37 = data.readLong();
              data.enforceNoDataAvail();
              reportGpsGeofenceTransition(_arg010, _arg18, _arg27, _arg37);
              reply.writeNoException();
              return true;
            case 11:
              int _arg011 = data.readInt();
              Location _arg19 = (Location) data.readTypedObject(Location.CREATOR);
              data.enforceNoDataAvail();
              reportGpsGeofenceStatus(_arg011, _arg19);
              reply.writeNoException();
              return true;
            case 12:
              int _arg012 = data.readInt();
              int _arg110 = data.readInt();
              data.enforceNoDataAvail();
              reportGpsGeofenceAddStatus(_arg012, _arg110);
              reply.writeNoException();
              return true;
            case 13:
              int _arg013 = data.readInt();
              int _arg111 = data.readInt();
              data.enforceNoDataAvail();
              reportGpsGeofenceRemoveStatus(_arg013, _arg111);
              reply.writeNoException();
              return true;
            case 14:
              int _arg014 = data.readInt();
              int _arg112 = data.readInt();
              data.enforceNoDataAvail();
              reportGpsGeofencePauseStatus(_arg014, _arg112);
              reply.writeNoException();
              return true;
            case 15:
              int _arg015 = data.readInt();
              int _arg113 = data.readInt();
              data.enforceNoDataAvail();
              reportGpsGeofenceResumeStatus(_arg015, _arg113);
              reply.writeNoException();
              return true;
            case 16:
              PendingIntent _arg016 = (PendingIntent) data.readTypedObject(PendingIntent.CREATOR);
              ISLocationListener _arg114 =
                  ISLocationListener.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              removePassiveLocation(_arg016, _arg114);
              reply.writeNoException();
              return true;
            case 17:
              PendingIntent _arg017 = (PendingIntent) data.readTypedObject(PendingIntent.CREATOR);
              ISLocationBatchingListener _arg115 =
                  ISLocationBatchingListener.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              int _result9 = removeBatchedLocations(_arg017, _arg115);
              reply.writeNoException();
              reply.writeInt(_result9);
              return true;
            case 18:
              flushBatchedLocations();
              reply.writeNoException();
              return true;
            case 19:
              int _arg018 = data.readInt();
              int[] _arg116 = data.createIntArray();
              float[] _arg28 = data.createFloatArray();
              float[] _arg38 = data.createFloatArray();
              float[] _arg44 = data.createFloatArray();
              float[] _arg53 = data.createFloatArray();
              float[] _arg63 = data.createFloatArray();
              data.enforceNoDataAvail();
              onSvStatusChanged(_arg018, _arg116, _arg28, _arg38, _arg44, _arg53, _arg63);
              reply.writeNoException();
              return true;
            case 20:
              boolean _arg019 = data.readBoolean();
              data.enforceNoDataAvail();
              onGnssStatusChanged(_arg019);
              reply.writeNoException();
              return true;
            case 21:
              List<String> _arg020 = data.createStringArrayList();
              String _arg117 = data.readString();
              String _arg29 = data.readString();
              data.enforceNoDataAvail();
              int _result10 = removeGeofences(_arg020, _arg117, _arg29);
              reply.writeNoException();
              reply.writeInt(_result10);
              return true;
            case 22:
              PendingIntent _arg021 = (PendingIntent) data.readTypedObject(PendingIntent.CREATOR);
              data.enforceNoDataAvail();
              int _result11 = removeGeofencesPendingIntent(_arg021);
              reply.writeNoException();
              reply.writeInt(_result11);
              return true;
            case 23:
              ISLocationLMSHook _arg022 =
                  ISLocationLMSHook.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              setSLocationLMSHook(_arg022);
              reply.writeNoException();
              return true;
            case 24:
              int _arg023 = data.readInt();
              boolean _arg118 = data.readBoolean();
              data.enforceNoDataAvail();
              notifyAppForeground(_arg023, _arg118);
              reply.writeNoException();
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements ISLocationManager {
      private IBinder mRemote;

      Proxy(IBinder remote) {
        this.mRemote = remote;
      }

      @Override // android.os.IInterface
      public IBinder asBinder() {
        return this.mRemote;
      }

      public String getInterfaceDescriptor() {
        return ISLocationManager.DESCRIPTOR;
      }

      @Override // com.samsung.android.location.ISLocationManager
      public boolean isAvailable(int module) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(ISLocationManager.DESCRIPTOR);
          _data.writeInt(module);
          this.mRemote.transact(1, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.location.ISLocationManager
      public int removeLocation(ISLocationListener listener) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(ISLocationManager.DESCRIPTOR);
          _data.writeStrongInterface(listener);
          this.mRemote.transact(2, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.location.ISLocationManager
      public int removeSingleLocation(PendingIntent intent, ISLocationListener listener)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(ISLocationManager.DESCRIPTOR);
          _data.writeTypedObject(intent, 0);
          _data.writeStrongInterface(listener);
          this.mRemote.transact(3, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.location.ISLocationManager
      public int requestSingleLocation(
          int accuracy,
          int timeout,
          boolean isAddress,
          PendingIntent intent,
          ISLocationListener listener,
          String packageName,
          String attributionTag)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(ISLocationManager.DESCRIPTOR);
          _data.writeInt(accuracy);
          _data.writeInt(timeout);
          _data.writeBoolean(isAddress);
          _data.writeTypedObject(intent, 0);
          _data.writeStrongInterface(listener);
          _data.writeString(packageName);
          _data.writeString(attributionTag);
          this.mRemote.transact(4, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.location.ISLocationManager
      public int requestMostAccurateLocation(
          int accuracyLimit,
          int requestTimeout,
          int locationTimeout,
          PendingIntent intent,
          ISLocationListener listener,
          String packageName,
          String attributionTag)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(ISLocationManager.DESCRIPTOR);
          _data.writeInt(accuracyLimit);
          _data.writeInt(requestTimeout);
          _data.writeInt(locationTimeout);
          _data.writeTypedObject(intent, 0);
          _data.writeStrongInterface(listener);
          _data.writeString(packageName);
          _data.writeString(attributionTag);
          this.mRemote.transact(5, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.location.ISLocationManager
      public int requestLocation(
          boolean isAddress, ISLocationListener listener, String packageName, String attributionTag)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(ISLocationManager.DESCRIPTOR);
          _data.writeBoolean(isAddress);
          _data.writeStrongInterface(listener);
          _data.writeString(packageName);
          _data.writeString(attributionTag);
          this.mRemote.transact(6, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.location.ISLocationManager
      public void requestPassiveLocation(
          PendingIntent intent,
          ISLocationListener listener,
          String packageName,
          String attributionTag)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(ISLocationManager.DESCRIPTOR);
          _data.writeTypedObject(intent, 0);
          _data.writeStrongInterface(listener);
          _data.writeString(packageName);
          _data.writeString(attributionTag);
          this.mRemote.transact(7, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.location.ISLocationManager
      public int requestBatchedLocations(
          SemLocationBatchingRequest req,
          PendingIntent intent,
          ISLocationBatchingListener listener,
          String packageName,
          String attributionTag)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(ISLocationManager.DESCRIPTOR);
          _data.writeTypedObject(req, 0);
          _data.writeTypedObject(intent, 0);
          _data.writeStrongInterface(listener);
          _data.writeString(packageName);
          _data.writeString(attributionTag);
          this.mRemote.transact(8, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.location.ISLocationManager
      public int addGeofences(
          List<SemGeofence> params, PendingIntent intent, String packageName, String attributionTag)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(ISLocationManager.DESCRIPTOR);
          _data.writeTypedList(params, 0);
          _data.writeTypedObject(intent, 0);
          _data.writeString(packageName);
          _data.writeString(attributionTag);
          this.mRemote.transact(9, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.location.ISLocationManager
      public void reportGpsGeofenceTransition(
          int geofenceId, Location location, int transition, long transitionTimestamp)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(ISLocationManager.DESCRIPTOR);
          _data.writeInt(geofenceId);
          _data.writeTypedObject(location, 0);
          _data.writeInt(transition);
          _data.writeLong(transitionTimestamp);
          this.mRemote.transact(10, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.location.ISLocationManager
      public void reportGpsGeofenceStatus(int status, Location location) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(ISLocationManager.DESCRIPTOR);
          _data.writeInt(status);
          _data.writeTypedObject(location, 0);
          this.mRemote.transact(11, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.location.ISLocationManager
      public void reportGpsGeofenceAddStatus(int geofenceId, int status) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(ISLocationManager.DESCRIPTOR);
          _data.writeInt(geofenceId);
          _data.writeInt(status);
          this.mRemote.transact(12, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.location.ISLocationManager
      public void reportGpsGeofenceRemoveStatus(int geofenceId, int status) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(ISLocationManager.DESCRIPTOR);
          _data.writeInt(geofenceId);
          _data.writeInt(status);
          this.mRemote.transact(13, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.location.ISLocationManager
      public void reportGpsGeofencePauseStatus(int geofenceId, int status) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(ISLocationManager.DESCRIPTOR);
          _data.writeInt(geofenceId);
          _data.writeInt(status);
          this.mRemote.transact(14, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.location.ISLocationManager
      public void reportGpsGeofenceResumeStatus(int geofenceId, int status) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(ISLocationManager.DESCRIPTOR);
          _data.writeInt(geofenceId);
          _data.writeInt(status);
          this.mRemote.transact(15, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.location.ISLocationManager
      public void removePassiveLocation(PendingIntent intent, ISLocationListener listener)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(ISLocationManager.DESCRIPTOR);
          _data.writeTypedObject(intent, 0);
          _data.writeStrongInterface(listener);
          this.mRemote.transact(16, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.location.ISLocationManager
      public int removeBatchedLocations(PendingIntent intent, ISLocationBatchingListener listener)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(ISLocationManager.DESCRIPTOR);
          _data.writeTypedObject(intent, 0);
          _data.writeStrongInterface(listener);
          this.mRemote.transact(17, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.location.ISLocationManager
      public void flushBatchedLocations() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(ISLocationManager.DESCRIPTOR);
          this.mRemote.transact(18, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.location.ISLocationManager
      public void onSvStatusChanged(
          int svCount,
          int[] svidWithFlags,
          float[] cn0s,
          float[] svElevations,
          float[] svAzimuths,
          float[] svCarrierFreqs,
          float[] basebandCn0s)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(ISLocationManager.DESCRIPTOR);
          _data.writeInt(svCount);
          _data.writeIntArray(svidWithFlags);
          _data.writeFloatArray(cn0s);
          _data.writeFloatArray(svElevations);
          _data.writeFloatArray(svAzimuths);
          _data.writeFloatArray(svCarrierFreqs);
          _data.writeFloatArray(basebandCn0s);
          this.mRemote.transact(19, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.location.ISLocationManager
      public void onGnssStatusChanged(boolean isNavigating) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(ISLocationManager.DESCRIPTOR);
          _data.writeBoolean(isNavigating);
          this.mRemote.transact(20, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.location.ISLocationManager
      public int removeGeofences(List<String> names, String packageName, String attributionTag)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(ISLocationManager.DESCRIPTOR);
          _data.writeStringList(names);
          _data.writeString(packageName);
          _data.writeString(attributionTag);
          this.mRemote.transact(21, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.location.ISLocationManager
      public int removeGeofencesPendingIntent(PendingIntent intent) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(ISLocationManager.DESCRIPTOR);
          _data.writeTypedObject(intent, 0);
          this.mRemote.transact(22, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.location.ISLocationManager
      public void setSLocationLMSHook(ISLocationLMSHook iSLocationLMSHook) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(ISLocationManager.DESCRIPTOR);
          _data.writeStrongInterface(iSLocationLMSHook);
          this.mRemote.transact(23, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.location.ISLocationManager
      public void notifyAppForeground(int uid, boolean foreground) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(ISLocationManager.DESCRIPTOR);
          _data.writeInt(uid);
          _data.writeBoolean(foreground);
          this.mRemote.transact(24, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }
    }

    @Override // android.os.Binder
    public int getMaxTransactionId() {
      return 23;
    }
  }
}
