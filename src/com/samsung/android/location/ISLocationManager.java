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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(ISLocationManager.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ISLocationManager)) {
                return (ISLocationManager) iInterfaceQueryLocalInterface;
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
                    int i3 = parcel.readInt();
                    int[] iArrCreateIntArray = parcel.createIntArray();
                    float[] fArrCreateFloatArray = parcel.createFloatArray();
                    float[] fArrCreateFloatArray2 = parcel.createFloatArray();
                    float[] fArrCreateFloatArray3 = parcel.createFloatArray();
                    float[] fArrCreateFloatArray4 = parcel.createFloatArray();
                    float[] fArrCreateFloatArray5 = parcel.createFloatArray();
                    parcel.enforceNoDataAvail();
                    onSvStatusChanged(i3, iArrCreateIntArray, fArrCreateFloatArray, fArrCreateFloatArray2, fArrCreateFloatArray3, fArrCreateFloatArray4, fArrCreateFloatArray5);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    boolean z = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onGnssStatusChanged(z);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    ISLocationSystemCallV1 iSLocationSystemCallV1AsInterface = ISLocationSystemCallV1.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setSystemCallV1(iSLocationSystemCallV1AsInterface);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    int i4 = parcel.readInt();
                    boolean z2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    notifyAppForeground(i4, z2);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    int i5 = parcel.readInt();
                    String string = parcel.readString();
                    String string2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zIsAvailable = isAvailable(i5, string, string2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsAvailable);
                    return true;
                case 6:
                    ArrayList arrayListCreateTypedArrayList = parcel.createTypedArrayList(SemGeofence.CREATOR);
                    PendingIntent pendingIntent = (PendingIntent) parcel.readTypedObject(PendingIntent.CREATOR);
                    String string3 = parcel.readString();
                    String string4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int iAddGeofences = addGeofences(arrayListCreateTypedArrayList, pendingIntent, string3, string4);
                    parcel2.writeNoException();
                    parcel2.writeInt(iAddGeofences);
                    return true;
                case 7:
                    ArrayList<String> arrayListCreateStringArrayList = parcel.createStringArrayList();
                    String string5 = parcel.readString();
                    String string6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int iRemoveGeofences = removeGeofences(arrayListCreateStringArrayList, string5, string6);
                    parcel2.writeNoException();
                    parcel2.writeInt(iRemoveGeofences);
                    return true;
                case 8:
                    PendingIntent pendingIntent2 = (PendingIntent) parcel.readTypedObject(PendingIntent.CREATOR);
                    String string7 = parcel.readString();
                    String string8 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int iRemoveGeofencesPendingIntent = removeGeofencesPendingIntent(pendingIntent2, string7, string8);
                    parcel2.writeNoException();
                    parcel2.writeInt(iRemoveGeofencesPendingIntent);
                    return true;
                case 9:
                    int i6 = parcel.readInt();
                    int i7 = parcel.readInt();
                    boolean z3 = parcel.readBoolean();
                    PendingIntent pendingIntent3 = (PendingIntent) parcel.readTypedObject(PendingIntent.CREATOR);
                    ISLocationListener iSLocationListenerAsInterface = ISLocationListener.Stub.asInterface(parcel.readStrongBinder());
                    String string9 = parcel.readString();
                    String string10 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int iRequestSingleLocation = requestSingleLocation(i6, i7, z3, pendingIntent3, iSLocationListenerAsInterface, string9, string10);
                    parcel2.writeNoException();
                    parcel2.writeInt(iRequestSingleLocation);
                    return true;
                case 10:
                    boolean z4 = parcel.readBoolean();
                    ISLocationListener iSLocationListenerAsInterface2 = ISLocationListener.Stub.asInterface(parcel.readStrongBinder());
                    String string11 = parcel.readString();
                    String string12 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int iRequestLocation = requestLocation(z4, iSLocationListenerAsInterface2, string11, string12);
                    parcel2.writeNoException();
                    parcel2.writeInt(iRequestLocation);
                    return true;
                case 11:
                    PendingIntent pendingIntent4 = (PendingIntent) parcel.readTypedObject(PendingIntent.CREATOR);
                    ISLocationListener iSLocationListenerAsInterface3 = ISLocationListener.Stub.asInterface(parcel.readStrongBinder());
                    String string13 = parcel.readString();
                    String string14 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    requestPassiveLocation(pendingIntent4, iSLocationListenerAsInterface3, string13, string14);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    PendingIntent pendingIntent5 = (PendingIntent) parcel.readTypedObject(PendingIntent.CREATOR);
                    ISLocationListener iSLocationListenerAsInterface4 = ISLocationListener.Stub.asInterface(parcel.readStrongBinder());
                    String string15 = parcel.readString();
                    String string16 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int iRemoveSingleLocation = removeSingleLocation(pendingIntent5, iSLocationListenerAsInterface4, string15, string16);
                    parcel2.writeNoException();
                    parcel2.writeInt(iRemoveSingleLocation);
                    return true;
                case 13:
                    ISLocationListener iSLocationListenerAsInterface5 = ISLocationListener.Stub.asInterface(parcel.readStrongBinder());
                    String string17 = parcel.readString();
                    String string18 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int iRemoveLocation = removeLocation(iSLocationListenerAsInterface5, string17, string18);
                    parcel2.writeNoException();
                    parcel2.writeInt(iRemoveLocation);
                    return true;
                case 14:
                    PendingIntent pendingIntent6 = (PendingIntent) parcel.readTypedObject(PendingIntent.CREATOR);
                    ISLocationListener iSLocationListenerAsInterface6 = ISLocationListener.Stub.asInterface(parcel.readStrongBinder());
                    String string19 = parcel.readString();
                    String string20 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    removePassiveLocation(pendingIntent6, iSLocationListenerAsInterface6, string19, string20);
                    parcel2.writeNoException();
                    return true;
                case 15:
                    SemLocationBatchingRequest semLocationBatchingRequest = (SemLocationBatchingRequest) parcel.readTypedObject(SemLocationBatchingRequest.CREATOR);
                    PendingIntent pendingIntent7 = (PendingIntent) parcel.readTypedObject(PendingIntent.CREATOR);
                    ISLocationBatchingListener iSLocationBatchingListenerAsInterface = ISLocationBatchingListener.Stub.asInterface(parcel.readStrongBinder());
                    String string21 = parcel.readString();
                    String string22 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int iRequestBatchedLocations = requestBatchedLocations(semLocationBatchingRequest, pendingIntent7, iSLocationBatchingListenerAsInterface, string21, string22);
                    parcel2.writeNoException();
                    parcel2.writeInt(iRequestBatchedLocations);
                    return true;
                case 16:
                    PendingIntent pendingIntent8 = (PendingIntent) parcel.readTypedObject(PendingIntent.CREATOR);
                    ISLocationBatchingListener iSLocationBatchingListenerAsInterface2 = ISLocationBatchingListener.Stub.asInterface(parcel.readStrongBinder());
                    String string23 = parcel.readString();
                    String string24 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int iRemoveBatchedLocations = removeBatchedLocations(pendingIntent8, iSLocationBatchingListenerAsInterface2, string23, string24);
                    parcel2.writeNoException();
                    parcel2.writeInt(iRemoveBatchedLocations);
                    return true;
                case 17:
                    String string25 = parcel.readString();
                    String string26 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    flushBatchedLocations(string25, string26);
                    parcel2.writeNoException();
                    return true;
                case 18:
                    int i8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onPermissionsChangedForSLocation(i8);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISLocationManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeIntArray(iArr);
                    parcelObtain.writeFloatArray(fArr);
                    parcelObtain.writeFloatArray(fArr2);
                    parcelObtain.writeFloatArray(fArr3);
                    parcelObtain.writeFloatArray(fArr4);
                    parcelObtain.writeFloatArray(fArr5);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.location.ISLocationManager
            public void onGnssStatusChanged(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISLocationManager.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.location.ISLocationManager
            public void setSystemCallV1(ISLocationSystemCallV1 iSLocationSystemCallV1) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISLocationManager.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iSLocationSystemCallV1);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.location.ISLocationManager
            public void notifyAppForeground(int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISLocationManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.location.ISLocationManager
            public boolean isAvailable(int i, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISLocationManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.location.ISLocationManager
            public int addGeofences(List<SemGeofence> list, PendingIntent pendingIntent, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISLocationManager.DESCRIPTOR);
                    parcelObtain.writeTypedList(list, 0);
                    parcelObtain.writeTypedObject(pendingIntent, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.location.ISLocationManager
            public int removeGeofences(List<String> list, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISLocationManager.DESCRIPTOR);
                    parcelObtain.writeStringList(list);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.location.ISLocationManager
            public int removeGeofencesPendingIntent(PendingIntent pendingIntent, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISLocationManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(pendingIntent, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.location.ISLocationManager
            public int requestSingleLocation(int i, int i2, boolean z, PendingIntent pendingIntent, ISLocationListener iSLocationListener, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISLocationManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeTypedObject(pendingIntent, 0);
                    parcelObtain.writeStrongInterface(iSLocationListener);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.location.ISLocationManager
            public int requestLocation(boolean z, ISLocationListener iSLocationListener, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISLocationManager.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeStrongInterface(iSLocationListener);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.location.ISLocationManager
            public void requestPassiveLocation(PendingIntent pendingIntent, ISLocationListener iSLocationListener, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISLocationManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(pendingIntent, 0);
                    parcelObtain.writeStrongInterface(iSLocationListener);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.location.ISLocationManager
            public int removeSingleLocation(PendingIntent pendingIntent, ISLocationListener iSLocationListener, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISLocationManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(pendingIntent, 0);
                    parcelObtain.writeStrongInterface(iSLocationListener);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.location.ISLocationManager
            public int removeLocation(ISLocationListener iSLocationListener, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISLocationManager.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iSLocationListener);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.location.ISLocationManager
            public void removePassiveLocation(PendingIntent pendingIntent, ISLocationListener iSLocationListener, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISLocationManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(pendingIntent, 0);
                    parcelObtain.writeStrongInterface(iSLocationListener);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.location.ISLocationManager
            public int requestBatchedLocations(SemLocationBatchingRequest semLocationBatchingRequest, PendingIntent pendingIntent, ISLocationBatchingListener iSLocationBatchingListener, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISLocationManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(semLocationBatchingRequest, 0);
                    parcelObtain.writeTypedObject(pendingIntent, 0);
                    parcelObtain.writeStrongInterface(iSLocationBatchingListener);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(15, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.location.ISLocationManager
            public int removeBatchedLocations(PendingIntent pendingIntent, ISLocationBatchingListener iSLocationBatchingListener, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISLocationManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(pendingIntent, 0);
                    parcelObtain.writeStrongInterface(iSLocationBatchingListener);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(16, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.location.ISLocationManager
            public void flushBatchedLocations(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISLocationManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(17, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.location.ISLocationManager
            public void onPermissionsChangedForSLocation(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISLocationManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(18, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
