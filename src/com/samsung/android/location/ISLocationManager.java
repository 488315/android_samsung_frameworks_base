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
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes6.dex */
public interface ISLocationManager extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.location.ISLocationManager";

    public static class Default implements ISLocationManager {
        @Override // com.samsung.android.location.ISLocationManager
        public int addGeofences(List<SemGeofence> list, PendingIntent pendingIntent, String str, String str2) throws RemoteException {
            return 0;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.location.ISLocationManager
        public void flushBatchedLocations(String str, String str2) throws RemoteException {
        }

        @Override // com.samsung.android.location.ISLocationManager
        public boolean isAvailable(int i, String str, String str2) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.location.ISLocationManager
        public void notifyAppForeground(int i, boolean z) throws RemoteException {
        }

        @Override // com.samsung.android.location.ISLocationManager
        public void onGnssStatusChanged(boolean z) throws RemoteException {
        }

        @Override // com.samsung.android.location.ISLocationManager
        public void onPermissionsChangedForSLocation(int i) throws RemoteException {
        }

        @Override // com.samsung.android.location.ISLocationManager
        public void onSvStatusChanged(int i, int[] iArr, float[] fArr, float[] fArr2, float[] fArr3, float[] fArr4, float[] fArr5) throws RemoteException {
        }

        @Override // com.samsung.android.location.ISLocationManager
        public int removeBatchedLocations(PendingIntent pendingIntent, ISLocationBatchingListener iSLocationBatchingListener, String str, String str2) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.location.ISLocationManager
        public int removeGeofences(List<String> list, String str, String str2) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.location.ISLocationManager
        public int removeGeofencesPendingIntent(PendingIntent pendingIntent, String str, String str2) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.location.ISLocationManager
        public int removeLocation(ISLocationListener iSLocationListener, String str, String str2) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.location.ISLocationManager
        public void removePassiveLocation(PendingIntent pendingIntent, ISLocationListener iSLocationListener, String str, String str2) throws RemoteException {
        }

        @Override // com.samsung.android.location.ISLocationManager
        public int removeSingleLocation(PendingIntent pendingIntent, ISLocationListener iSLocationListener, String str, String str2) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.location.ISLocationManager
        public int requestBatchedLocations(SemLocationBatchingRequest semLocationBatchingRequest, PendingIntent pendingIntent, ISLocationBatchingListener iSLocationBatchingListener, String str, String str2) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.location.ISLocationManager
        public int requestLocation(boolean z, ISLocationListener iSLocationListener, String str, String str2) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.location.ISLocationManager
        public void requestPassiveLocation(PendingIntent pendingIntent, ISLocationListener iSLocationListener, String str, String str2) throws RemoteException {
        }

        @Override // com.samsung.android.location.ISLocationManager
        public int requestSingleLocation(int i, int i2, boolean z, PendingIntent pendingIntent, ISLocationListener iSLocationListener, String str, String str2) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.location.ISLocationManager
        public void setSystemCallV1(ISLocationSystemCallV1 iSLocationSystemCallV1) throws RemoteException {
        }
    }

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

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 17;
        }

        public Stub() {
            attachInterface(this, ISLocationManager.DESCRIPTOR);
        }

        public static ISLocationManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ISLocationManager.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ISLocationManager)) {
                return (ISLocationManager) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
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
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ISLocationManager.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ISLocationManager.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int readInt = parcel.readInt();
                    int[] createIntArray = parcel.createIntArray();
                    float[] createFloatArray = parcel.createFloatArray();
                    float[] createFloatArray2 = parcel.createFloatArray();
                    float[] createFloatArray3 = parcel.createFloatArray();
                    float[] createFloatArray4 = parcel.createFloatArray();
                    float[] createFloatArray5 = parcel.createFloatArray();
                    parcel.enforceNoDataAvail();
                    onSvStatusChanged(readInt, createIntArray, createFloatArray, createFloatArray2, createFloatArray3, createFloatArray4, createFloatArray5);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onGnssStatusChanged(readBoolean);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    ISLocationSystemCallV1 asInterface = ISLocationSystemCallV1.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setSystemCallV1(asInterface);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    int readInt2 = parcel.readInt();
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    notifyAppForeground(readInt2, readBoolean2);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    int readInt3 = parcel.readInt();
                    String readString = parcel.readString();
                    String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isAvailable = isAvailable(readInt3, readString, readString2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isAvailable);
                    return true;
                case 6:
                    ArrayList createTypedArrayList = parcel.createTypedArrayList(SemGeofence.CREATOR);
                    PendingIntent pendingIntent = (PendingIntent) parcel.readTypedObject(PendingIntent.CREATOR);
                    String readString3 = parcel.readString();
                    String readString4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int addGeofences = addGeofences(createTypedArrayList, pendingIntent, readString3, readString4);
                    parcel2.writeNoException();
                    parcel2.writeInt(addGeofences);
                    return true;
                case 7:
                    ArrayList<String> createStringArrayList = parcel.createStringArrayList();
                    String readString5 = parcel.readString();
                    String readString6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int removeGeofences = removeGeofences(createStringArrayList, readString5, readString6);
                    parcel2.writeNoException();
                    parcel2.writeInt(removeGeofences);
                    return true;
                case 8:
                    PendingIntent pendingIntent2 = (PendingIntent) parcel.readTypedObject(PendingIntent.CREATOR);
                    String readString7 = parcel.readString();
                    String readString8 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int removeGeofencesPendingIntent = removeGeofencesPendingIntent(pendingIntent2, readString7, readString8);
                    parcel2.writeNoException();
                    parcel2.writeInt(removeGeofencesPendingIntent);
                    return true;
                case 9:
                    int readInt4 = parcel.readInt();
                    int readInt5 = parcel.readInt();
                    boolean readBoolean3 = parcel.readBoolean();
                    PendingIntent pendingIntent3 = (PendingIntent) parcel.readTypedObject(PendingIntent.CREATOR);
                    ISLocationListener asInterface2 = ISLocationListener.Stub.asInterface(parcel.readStrongBinder());
                    String readString9 = parcel.readString();
                    String readString10 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int requestSingleLocation = requestSingleLocation(readInt4, readInt5, readBoolean3, pendingIntent3, asInterface2, readString9, readString10);
                    parcel2.writeNoException();
                    parcel2.writeInt(requestSingleLocation);
                    return true;
                case 10:
                    boolean readBoolean4 = parcel.readBoolean();
                    ISLocationListener asInterface3 = ISLocationListener.Stub.asInterface(parcel.readStrongBinder());
                    String readString11 = parcel.readString();
                    String readString12 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int requestLocation = requestLocation(readBoolean4, asInterface3, readString11, readString12);
                    parcel2.writeNoException();
                    parcel2.writeInt(requestLocation);
                    return true;
                case 11:
                    PendingIntent pendingIntent4 = (PendingIntent) parcel.readTypedObject(PendingIntent.CREATOR);
                    ISLocationListener asInterface4 = ISLocationListener.Stub.asInterface(parcel.readStrongBinder());
                    String readString13 = parcel.readString();
                    String readString14 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    requestPassiveLocation(pendingIntent4, asInterface4, readString13, readString14);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    PendingIntent pendingIntent5 = (PendingIntent) parcel.readTypedObject(PendingIntent.CREATOR);
                    ISLocationListener asInterface5 = ISLocationListener.Stub.asInterface(parcel.readStrongBinder());
                    String readString15 = parcel.readString();
                    String readString16 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int removeSingleLocation = removeSingleLocation(pendingIntent5, asInterface5, readString15, readString16);
                    parcel2.writeNoException();
                    parcel2.writeInt(removeSingleLocation);
                    return true;
                case 13:
                    ISLocationListener asInterface6 = ISLocationListener.Stub.asInterface(parcel.readStrongBinder());
                    String readString17 = parcel.readString();
                    String readString18 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int removeLocation = removeLocation(asInterface6, readString17, readString18);
                    parcel2.writeNoException();
                    parcel2.writeInt(removeLocation);
                    return true;
                case 14:
                    PendingIntent pendingIntent6 = (PendingIntent) parcel.readTypedObject(PendingIntent.CREATOR);
                    ISLocationListener asInterface7 = ISLocationListener.Stub.asInterface(parcel.readStrongBinder());
                    String readString19 = parcel.readString();
                    String readString20 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    removePassiveLocation(pendingIntent6, asInterface7, readString19, readString20);
                    parcel2.writeNoException();
                    return true;
                case 15:
                    SemLocationBatchingRequest semLocationBatchingRequest = (SemLocationBatchingRequest) parcel.readTypedObject(SemLocationBatchingRequest.CREATOR);
                    PendingIntent pendingIntent7 = (PendingIntent) parcel.readTypedObject(PendingIntent.CREATOR);
                    ISLocationBatchingListener asInterface8 = ISLocationBatchingListener.Stub.asInterface(parcel.readStrongBinder());
                    String readString21 = parcel.readString();
                    String readString22 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int requestBatchedLocations = requestBatchedLocations(semLocationBatchingRequest, pendingIntent7, asInterface8, readString21, readString22);
                    parcel2.writeNoException();
                    parcel2.writeInt(requestBatchedLocations);
                    return true;
                case 16:
                    PendingIntent pendingIntent8 = (PendingIntent) parcel.readTypedObject(PendingIntent.CREATOR);
                    ISLocationBatchingListener asInterface9 = ISLocationBatchingListener.Stub.asInterface(parcel.readStrongBinder());
                    String readString23 = parcel.readString();
                    String readString24 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int removeBatchedLocations = removeBatchedLocations(pendingIntent8, asInterface9, readString23, readString24);
                    parcel2.writeNoException();
                    parcel2.writeInt(removeBatchedLocations);
                    return true;
                case 17:
                    String readString25 = parcel.readString();
                    String readString26 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    flushBatchedLocations(readString25, readString26);
                    parcel2.writeNoException();
                    return true;
                case 18:
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onPermissionsChangedForSLocation(readInt6);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements ISLocationManager {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISLocationManager.DESCRIPTOR;
            }

            @Override // com.samsung.android.location.ISLocationManager
            public void onSvStatusChanged(int i, int[] iArr, float[] fArr, float[] fArr2, float[] fArr3, float[] fArr4, float[] fArr5) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISLocationManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeIntArray(iArr);
                    obtain.writeFloatArray(fArr);
                    obtain.writeFloatArray(fArr2);
                    obtain.writeFloatArray(fArr3);
                    obtain.writeFloatArray(fArr4);
                    obtain.writeFloatArray(fArr5);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.location.ISLocationManager
            public void onGnssStatusChanged(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISLocationManager.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.location.ISLocationManager
            public void setSystemCallV1(ISLocationSystemCallV1 iSLocationSystemCallV1) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISLocationManager.DESCRIPTOR);
                    obtain.writeStrongInterface(iSLocationSystemCallV1);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.location.ISLocationManager
            public void notifyAppForeground(int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISLocationManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.location.ISLocationManager
            public boolean isAvailable(int i, String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISLocationManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.location.ISLocationManager
            public int addGeofences(List<SemGeofence> list, PendingIntent pendingIntent, String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISLocationManager.DESCRIPTOR);
                    obtain.writeTypedList(list, 0);
                    obtain.writeTypedObject(pendingIntent, 0);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.location.ISLocationManager
            public int removeGeofences(List<String> list, String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISLocationManager.DESCRIPTOR);
                    obtain.writeStringList(list);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.location.ISLocationManager
            public int removeGeofencesPendingIntent(PendingIntent pendingIntent, String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISLocationManager.DESCRIPTOR);
                    obtain.writeTypedObject(pendingIntent, 0);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.location.ISLocationManager
            public int requestSingleLocation(int i, int i2, boolean z, PendingIntent pendingIntent, ISLocationListener iSLocationListener, String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISLocationManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeBoolean(z);
                    obtain.writeTypedObject(pendingIntent, 0);
                    obtain.writeStrongInterface(iSLocationListener);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.location.ISLocationManager
            public int requestLocation(boolean z, ISLocationListener iSLocationListener, String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISLocationManager.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeStrongInterface(iSLocationListener);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.location.ISLocationManager
            public void requestPassiveLocation(PendingIntent pendingIntent, ISLocationListener iSLocationListener, String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISLocationManager.DESCRIPTOR);
                    obtain.writeTypedObject(pendingIntent, 0);
                    obtain.writeStrongInterface(iSLocationListener);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.location.ISLocationManager
            public int removeSingleLocation(PendingIntent pendingIntent, ISLocationListener iSLocationListener, String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISLocationManager.DESCRIPTOR);
                    obtain.writeTypedObject(pendingIntent, 0);
                    obtain.writeStrongInterface(iSLocationListener);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.location.ISLocationManager
            public int removeLocation(ISLocationListener iSLocationListener, String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISLocationManager.DESCRIPTOR);
                    obtain.writeStrongInterface(iSLocationListener);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.location.ISLocationManager
            public void removePassiveLocation(PendingIntent pendingIntent, ISLocationListener iSLocationListener, String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISLocationManager.DESCRIPTOR);
                    obtain.writeTypedObject(pendingIntent, 0);
                    obtain.writeStrongInterface(iSLocationListener);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.location.ISLocationManager
            public int requestBatchedLocations(SemLocationBatchingRequest semLocationBatchingRequest, PendingIntent pendingIntent, ISLocationBatchingListener iSLocationBatchingListener, String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISLocationManager.DESCRIPTOR);
                    obtain.writeTypedObject(semLocationBatchingRequest, 0);
                    obtain.writeTypedObject(pendingIntent, 0);
                    obtain.writeStrongInterface(iSLocationBatchingListener);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.location.ISLocationManager
            public int removeBatchedLocations(PendingIntent pendingIntent, ISLocationBatchingListener iSLocationBatchingListener, String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISLocationManager.DESCRIPTOR);
                    obtain.writeTypedObject(pendingIntent, 0);
                    obtain.writeStrongInterface(iSLocationBatchingListener);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.location.ISLocationManager
            public void flushBatchedLocations(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISLocationManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.location.ISLocationManager
            public void onPermissionsChangedForSLocation(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISLocationManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
