package com.samsung.android.location;

import android.app.PendingIntent;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.samsung.android.location.ISLocationBatchingListener;
import com.samsung.android.location.ISLocationListener;
import com.samsung.android.location.ISLocationSystemCallV1;
import java.util.List;

/* loaded from: classes6.dex */
public interface ISLocationManager extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.location.ISLocationManager";

    int addGeofences(List<SemGeofence> list, PendingIntent pendingIntent, String str, String str2) throws RemoteException;

    void flushBatchedLocations(String str, String str2) throws RemoteException;

    boolean isAvailable(int i, String str, String str2) throws RemoteException;

    void notifyAppForeground(int i, boolean z) throws RemoteException;

    void onGnssStatusChanged(boolean z) throws RemoteException;

    void onPermissionsChangedForSLocation(int i) throws RemoteException;

    void onSvStatusChanged(int i, int[] iArr, float[] fArr, float[] fArr2, float[] fArr3, float[] fArr4, float[] fArr5) throws RemoteException;

    int removeBatchedLocations(PendingIntent pendingIntent, ISLocationBatchingListener iSLocationBatchingListener, String str, String str2) throws RemoteException;

    int removeGeofences(List<String> list, String str, String str2) throws RemoteException;

    int removeGeofencesPendingIntent(PendingIntent pendingIntent, String str, String str2) throws RemoteException;

    int removeLocation(ISLocationListener iSLocationListener, String str, String str2) throws RemoteException;

    void removePassiveLocation(PendingIntent pendingIntent, ISLocationListener iSLocationListener, String str, String str2) throws RemoteException;

    int removeSingleLocation(PendingIntent pendingIntent, ISLocationListener iSLocationListener, String str, String str2) throws RemoteException;

    int requestBatchedLocations(SemLocationBatchingRequest semLocationBatchingRequest, PendingIntent pendingIntent, ISLocationBatchingListener iSLocationBatchingListener, String str, String str2) throws RemoteException;

    int requestLocation(boolean z, ISLocationListener iSLocationListener, String str, String str2) throws RemoteException;

    void requestPassiveLocation(PendingIntent pendingIntent, ISLocationListener iSLocationListener, String str, String str2) throws RemoteException;

    int requestSingleLocation(int i, int i2, boolean z, PendingIntent pendingIntent, ISLocationListener iSLocationListener, String str, String str2) throws RemoteException;

    void setSystemCallV1(ISLocationSystemCallV1 iSLocationSystemCallV1) throws RemoteException;

    public static class Default implements ISLocationManager {
        @Override // com.samsung.android.location.ISLocationManager
        public void onSvStatusChanged(int svCount, int[] svidWithFlags, float[] cn0s, float[] svElevations, float[] svAzimuths, float[] svCarrierFreqs, float[] basebandCn0s) throws RemoteException {
        }

        @Override // com.samsung.android.location.ISLocationManager
        public void onGnssStatusChanged(boolean isNavigating) throws RemoteException {
        }

        @Override // com.samsung.android.location.ISLocationManager
        public void setSystemCallV1(ISLocationSystemCallV1 iSystemCall) throws RemoteException {
        }

        @Override // com.samsung.android.location.ISLocationManager
        public void notifyAppForeground(int uid, boolean foreground) throws RemoteException {
        }

        @Override // com.samsung.android.location.ISLocationManager
        public boolean isAvailable(int module, String packageName, String attributionTag) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.location.ISLocationManager
        public int addGeofences(List<SemGeofence> params, PendingIntent intent, String packageName, String attributionTag) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.location.ISLocationManager
        public int removeGeofences(List<String> names, String packageName, String attributionTag) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.location.ISLocationManager
        public int removeGeofencesPendingIntent(PendingIntent intent, String packageName, String attributionTag) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.location.ISLocationManager
        public int requestSingleLocation(int accuracy, int timeout, boolean isAddress, PendingIntent intent, ISLocationListener listener, String packageName, String attributionTag) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.location.ISLocationManager
        public int requestLocation(boolean isAddress, ISLocationListener listener, String packageName, String attributionTag) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.location.ISLocationManager
        public void requestPassiveLocation(PendingIntent intent, ISLocationListener listener, String packageName, String attributionTag) throws RemoteException {
        }

        @Override // com.samsung.android.location.ISLocationManager
        public int removeSingleLocation(PendingIntent intent, ISLocationListener listener, String packageName, String attributionTag) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.location.ISLocationManager
        public int removeLocation(ISLocationListener listener, String packageName, String attributionTag) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.location.ISLocationManager
        public void removePassiveLocation(PendingIntent intent, ISLocationListener listener, String packageName, String attributionTag) throws RemoteException {
        }

        @Override // com.samsung.android.location.ISLocationManager
        public int requestBatchedLocations(SemLocationBatchingRequest req, PendingIntent intent, ISLocationBatchingListener listener, String packageName, String attributionTag) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.location.ISLocationManager
        public int removeBatchedLocations(PendingIntent intent, ISLocationBatchingListener listener, String packageName, String attributionTag) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.location.ISLocationManager
        public void flushBatchedLocations(String packageName, String attributionTag) throws RemoteException {
        }

        @Override // com.samsung.android.location.ISLocationManager
        public void onPermissionsChangedForSLocation(int uid) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements ISLocationManager {
        static final int TRANSACTION_addGeofences = 6;
        static final int TRANSACTION_flushBatchedLocations = 17;
        static final int TRANSACTION_isAvailable = 5;
        static final int TRANSACTION_notifyAppForeground = 4;
        static final int TRANSACTION_onGnssStatusChanged = 2;
        static final int TRANSACTION_onPermissionsChangedForSLocation = 18;
        static final int TRANSACTION_onSvStatusChanged = 1;
        static final int TRANSACTION_removeBatchedLocations = 16;
        static final int TRANSACTION_removeGeofences = 7;
        static final int TRANSACTION_removeGeofencesPendingIntent = 8;
        static final int TRANSACTION_removeLocation = 13;
        static final int TRANSACTION_removePassiveLocation = 14;
        static final int TRANSACTION_removeSingleLocation = 12;
        static final int TRANSACTION_requestBatchedLocations = 15;
        static final int TRANSACTION_requestLocation = 10;
        static final int TRANSACTION_requestPassiveLocation = 11;
        static final int TRANSACTION_requestSingleLocation = 9;
        static final int TRANSACTION_setSystemCallV1 = 3;

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
                    return "onSvStatusChanged";
                case 2:
                    return "onGnssStatusChanged";
                case 3:
                    return "setSystemCallV1";
                case 4:
                    return "notifyAppForeground";
                case 5:
                    return "isAvailable";
                case 6:
                    return "addGeofences";
                case 7:
                    return "removeGeofences";
                case 8:
                    return "removeGeofencesPendingIntent";
                case 9:
                    return "requestSingleLocation";
                case 10:
                    return "requestLocation";
                case 11:
                    return "requestPassiveLocation";
                case 12:
                    return "removeSingleLocation";
                case 13:
                    return "removeLocation";
                case 14:
                    return "removePassiveLocation";
                case 15:
                    return "requestBatchedLocations";
                case 16:
                    return "removeBatchedLocations";
                case 17:
                    return "flushBatchedLocations";
                case 18:
                    return "onPermissionsChangedForSLocation";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(ISLocationManager.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(ISLocationManager.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    int _arg0 = data.readInt();
                    int[] _arg1 = data.createIntArray();
                    float[] _arg2 = data.createFloatArray();
                    float[] _arg3 = data.createFloatArray();
                    float[] _arg4 = data.createFloatArray();
                    float[] _arg5 = data.createFloatArray();
                    float[] _arg6 = data.createFloatArray();
                    data.enforceNoDataAvail();
                    onSvStatusChanged(_arg0, _arg1, _arg2, _arg3, _arg4, _arg5, _arg6);
                    reply.writeNoException();
                    return true;
                case 2:
                    boolean _arg02 = data.readBoolean();
                    data.enforceNoDataAvail();
                    onGnssStatusChanged(_arg02);
                    reply.writeNoException();
                    return true;
                case 3:
                    ISLocationSystemCallV1 _arg03 = ISLocationSystemCallV1.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    setSystemCallV1(_arg03);
                    reply.writeNoException();
                    return true;
                case 4:
                    int _arg04 = data.readInt();
                    boolean _arg12 = data.readBoolean();
                    data.enforceNoDataAvail();
                    notifyAppForeground(_arg04, _arg12);
                    reply.writeNoException();
                    return true;
                case 5:
                    int _arg05 = data.readInt();
                    String _arg13 = data.readString();
                    String _arg22 = data.readString();
                    data.enforceNoDataAvail();
                    boolean _result = isAvailable(_arg05, _arg13, _arg22);
                    reply.writeNoException();
                    reply.writeBoolean(_result);
                    return true;
                case 6:
                    List<SemGeofence> _arg06 = data.createTypedArrayList(SemGeofence.CREATOR);
                    PendingIntent _arg14 = (PendingIntent) data.readTypedObject(PendingIntent.CREATOR);
                    String _arg23 = data.readString();
                    String _arg32 = data.readString();
                    data.enforceNoDataAvail();
                    int _result2 = addGeofences(_arg06, _arg14, _arg23, _arg32);
                    reply.writeNoException();
                    reply.writeInt(_result2);
                    return true;
                case 7:
                    List<String> _arg07 = data.createStringArrayList();
                    String _arg15 = data.readString();
                    String _arg24 = data.readString();
                    data.enforceNoDataAvail();
                    int _result3 = removeGeofences(_arg07, _arg15, _arg24);
                    reply.writeNoException();
                    reply.writeInt(_result3);
                    return true;
                case 8:
                    PendingIntent _arg08 = (PendingIntent) data.readTypedObject(PendingIntent.CREATOR);
                    String _arg16 = data.readString();
                    String _arg25 = data.readString();
                    data.enforceNoDataAvail();
                    int _result4 = removeGeofencesPendingIntent(_arg08, _arg16, _arg25);
                    reply.writeNoException();
                    reply.writeInt(_result4);
                    return true;
                case 9:
                    int _arg09 = data.readInt();
                    int _arg17 = data.readInt();
                    boolean _arg26 = data.readBoolean();
                    PendingIntent _arg33 = (PendingIntent) data.readTypedObject(PendingIntent.CREATOR);
                    ISLocationListener _arg42 = ISLocationListener.Stub.asInterface(data.readStrongBinder());
                    String _arg52 = data.readString();
                    String _arg62 = data.readString();
                    data.enforceNoDataAvail();
                    int _result5 = requestSingleLocation(_arg09, _arg17, _arg26, _arg33, _arg42, _arg52, _arg62);
                    reply.writeNoException();
                    reply.writeInt(_result5);
                    return true;
                case 10:
                    boolean _arg010 = data.readBoolean();
                    ISLocationListener _arg18 = ISLocationListener.Stub.asInterface(data.readStrongBinder());
                    String _arg27 = data.readString();
                    String _arg34 = data.readString();
                    data.enforceNoDataAvail();
                    int _result6 = requestLocation(_arg010, _arg18, _arg27, _arg34);
                    reply.writeNoException();
                    reply.writeInt(_result6);
                    return true;
                case 11:
                    PendingIntent _arg011 = (PendingIntent) data.readTypedObject(PendingIntent.CREATOR);
                    ISLocationListener _arg19 = ISLocationListener.Stub.asInterface(data.readStrongBinder());
                    String _arg28 = data.readString();
                    String _arg35 = data.readString();
                    data.enforceNoDataAvail();
                    requestPassiveLocation(_arg011, _arg19, _arg28, _arg35);
                    reply.writeNoException();
                    return true;
                case 12:
                    PendingIntent _arg012 = (PendingIntent) data.readTypedObject(PendingIntent.CREATOR);
                    ISLocationListener _arg110 = ISLocationListener.Stub.asInterface(data.readStrongBinder());
                    String _arg29 = data.readString();
                    String _arg36 = data.readString();
                    data.enforceNoDataAvail();
                    int _result7 = removeSingleLocation(_arg012, _arg110, _arg29, _arg36);
                    reply.writeNoException();
                    reply.writeInt(_result7);
                    return true;
                case 13:
                    ISLocationListener _arg013 = ISLocationListener.Stub.asInterface(data.readStrongBinder());
                    String _arg111 = data.readString();
                    String _arg210 = data.readString();
                    data.enforceNoDataAvail();
                    int _result8 = removeLocation(_arg013, _arg111, _arg210);
                    reply.writeNoException();
                    reply.writeInt(_result8);
                    return true;
                case 14:
                    PendingIntent _arg014 = (PendingIntent) data.readTypedObject(PendingIntent.CREATOR);
                    ISLocationListener _arg112 = ISLocationListener.Stub.asInterface(data.readStrongBinder());
                    String _arg211 = data.readString();
                    String _arg37 = data.readString();
                    data.enforceNoDataAvail();
                    removePassiveLocation(_arg014, _arg112, _arg211, _arg37);
                    reply.writeNoException();
                    return true;
                case 15:
                    SemLocationBatchingRequest _arg015 = (SemLocationBatchingRequest) data.readTypedObject(SemLocationBatchingRequest.CREATOR);
                    PendingIntent _arg113 = (PendingIntent) data.readTypedObject(PendingIntent.CREATOR);
                    ISLocationBatchingListener _arg212 = ISLocationBatchingListener.Stub.asInterface(data.readStrongBinder());
                    String _arg38 = data.readString();
                    String _arg43 = data.readString();
                    data.enforceNoDataAvail();
                    int _result9 = requestBatchedLocations(_arg015, _arg113, _arg212, _arg38, _arg43);
                    reply.writeNoException();
                    reply.writeInt(_result9);
                    return true;
                case 16:
                    PendingIntent _arg016 = (PendingIntent) data.readTypedObject(PendingIntent.CREATOR);
                    ISLocationBatchingListener _arg114 = ISLocationBatchingListener.Stub.asInterface(data.readStrongBinder());
                    String _arg213 = data.readString();
                    String _arg39 = data.readString();
                    data.enforceNoDataAvail();
                    int _result10 = removeBatchedLocations(_arg016, _arg114, _arg213, _arg39);
                    reply.writeNoException();
                    reply.writeInt(_result10);
                    return true;
                case 17:
                    String _arg017 = data.readString();
                    String _arg115 = data.readString();
                    data.enforceNoDataAvail();
                    flushBatchedLocations(_arg017, _arg115);
                    reply.writeNoException();
                    return true;
                case 18:
                    int _arg018 = data.readInt();
                    data.enforceNoDataAvail();
                    onPermissionsChangedForSLocation(_arg018);
                    reply.writeNoException();
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
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
            public void onSvStatusChanged(int svCount, int[] svidWithFlags, float[] cn0s, float[] svElevations, float[] svAzimuths, float[] svCarrierFreqs, float[] basebandCn0s) throws RemoteException {
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
                    this.mRemote.transact(1, _data, _reply, 0);
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
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.location.ISLocationManager
            public void setSystemCallV1(ISLocationSystemCallV1 iSystemCall) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISLocationManager.DESCRIPTOR);
                    _data.writeStrongInterface(iSystemCall);
                    this.mRemote.transact(3, _data, _reply, 0);
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
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.location.ISLocationManager
            public boolean isAvailable(int module, String packageName, String attributionTag) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISLocationManager.DESCRIPTOR);
                    _data.writeInt(module);
                    _data.writeString(packageName);
                    _data.writeString(attributionTag);
                    this.mRemote.transact(5, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.location.ISLocationManager
            public int addGeofences(List<SemGeofence> params, PendingIntent intent, String packageName, String attributionTag) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISLocationManager.DESCRIPTOR);
                    _data.writeTypedList(params, 0);
                    _data.writeTypedObject(intent, 0);
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
            public int removeGeofences(List<String> names, String packageName, String attributionTag) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISLocationManager.DESCRIPTOR);
                    _data.writeStringList(names);
                    _data.writeString(packageName);
                    _data.writeString(attributionTag);
                    this.mRemote.transact(7, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.location.ISLocationManager
            public int removeGeofencesPendingIntent(PendingIntent intent, String packageName, String attributionTag) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISLocationManager.DESCRIPTOR);
                    _data.writeTypedObject(intent, 0);
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
            public int requestSingleLocation(int accuracy, int timeout, boolean isAddress, PendingIntent intent, ISLocationListener listener, String packageName, String attributionTag) throws RemoteException {
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
            public int requestLocation(boolean isAddress, ISLocationListener listener, String packageName, String attributionTag) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISLocationManager.DESCRIPTOR);
                    _data.writeBoolean(isAddress);
                    _data.writeStrongInterface(listener);
                    _data.writeString(packageName);
                    _data.writeString(attributionTag);
                    this.mRemote.transact(10, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.location.ISLocationManager
            public void requestPassiveLocation(PendingIntent intent, ISLocationListener listener, String packageName, String attributionTag) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISLocationManager.DESCRIPTOR);
                    _data.writeTypedObject(intent, 0);
                    _data.writeStrongInterface(listener);
                    _data.writeString(packageName);
                    _data.writeString(attributionTag);
                    this.mRemote.transact(11, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.location.ISLocationManager
            public int removeSingleLocation(PendingIntent intent, ISLocationListener listener, String packageName, String attributionTag) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISLocationManager.DESCRIPTOR);
                    _data.writeTypedObject(intent, 0);
                    _data.writeStrongInterface(listener);
                    _data.writeString(packageName);
                    _data.writeString(attributionTag);
                    this.mRemote.transact(12, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.location.ISLocationManager
            public int removeLocation(ISLocationListener listener, String packageName, String attributionTag) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISLocationManager.DESCRIPTOR);
                    _data.writeStrongInterface(listener);
                    _data.writeString(packageName);
                    _data.writeString(attributionTag);
                    this.mRemote.transact(13, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.location.ISLocationManager
            public void removePassiveLocation(PendingIntent intent, ISLocationListener listener, String packageName, String attributionTag) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISLocationManager.DESCRIPTOR);
                    _data.writeTypedObject(intent, 0);
                    _data.writeStrongInterface(listener);
                    _data.writeString(packageName);
                    _data.writeString(attributionTag);
                    this.mRemote.transact(14, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.location.ISLocationManager
            public int requestBatchedLocations(SemLocationBatchingRequest req, PendingIntent intent, ISLocationBatchingListener listener, String packageName, String attributionTag) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISLocationManager.DESCRIPTOR);
                    _data.writeTypedObject(req, 0);
                    _data.writeTypedObject(intent, 0);
                    _data.writeStrongInterface(listener);
                    _data.writeString(packageName);
                    _data.writeString(attributionTag);
                    this.mRemote.transact(15, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.location.ISLocationManager
            public int removeBatchedLocations(PendingIntent intent, ISLocationBatchingListener listener, String packageName, String attributionTag) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISLocationManager.DESCRIPTOR);
                    _data.writeTypedObject(intent, 0);
                    _data.writeStrongInterface(listener);
                    _data.writeString(packageName);
                    _data.writeString(attributionTag);
                    this.mRemote.transact(16, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.location.ISLocationManager
            public void flushBatchedLocations(String packageName, String attributionTag) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISLocationManager.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeString(attributionTag);
                    this.mRemote.transact(17, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.location.ISLocationManager
            public void onPermissionsChangedForSLocation(int uid) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISLocationManager.DESCRIPTOR);
                    _data.writeInt(uid);
                    this.mRemote.transact(18, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 17;
        }
    }
}
