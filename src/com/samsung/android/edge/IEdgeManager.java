package com.samsung.android.edge;

import android.content.ComponentName;
import android.hardware.display.SemWifiDisplayParameter;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes6.dex */
public interface IEdgeManager extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.edge.IEdgeManager";

    public static class Default implements IEdgeManager {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.edge.IEdgeManager
        public void bindEdgeLightingService(IBinder iBinder, int i, ComponentName componentName) throws RemoteException {
        }

        @Override // com.samsung.android.edge.IEdgeManager
        public void cancelNotification(String str, String str2, int i, int i2, String str3) throws RemoteException {
        }

        @Override // com.samsung.android.edge.IEdgeManager
        public void cancelNotificationByGroupKey(String str, String str2, int i, int i2, String str3, String str4) throws RemoteException {
        }

        @Override // com.samsung.android.edge.IEdgeManager
        public void disable(int i, String str, IBinder iBinder) throws RemoteException {
        }

        @Override // com.samsung.android.edge.IEdgeManager
        public void disableEdgeLightingNotification(String str, boolean z) throws RemoteException {
        }

        @Override // com.samsung.android.edge.IEdgeManager
        public int getEdgeLightingState() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.edge.IEdgeManager
        public boolean isEdgeLightingNotificationAllowed(String str) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.edge.IEdgeManager
        public boolean isPackageEnabled(String str, int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.edge.IEdgeManager
        public void registerEdgeLightingListener(IBinder iBinder, ComponentName componentName) throws RemoteException {
        }

        @Override // com.samsung.android.edge.IEdgeManager
        public void startEdgeLighting(String str, SemEdgeLightingInfo semEdgeLightingInfo, IBinder iBinder) throws RemoteException {
        }

        @Override // com.samsung.android.edge.IEdgeManager
        public void stopEdgeLighting(String str, IBinder iBinder) throws RemoteException {
        }

        @Override // com.samsung.android.edge.IEdgeManager
        public void unbindEdgeLightingService(IBinder iBinder, String str) throws RemoteException {
        }

        @Override // com.samsung.android.edge.IEdgeManager
        public void unregisterEdgeLightingListener(IBinder iBinder, String str) throws RemoteException {
        }

        @Override // com.samsung.android.edge.IEdgeManager
        public void updateEdgeLightingPackageList(String str, List<String> list) throws RemoteException {
        }

        @Override // com.samsung.android.edge.IEdgeManager
        public void updateEdgeLightingPolicy(String str, EdgeLightingPolicy edgeLightingPolicy) throws RemoteException {
        }
    }

    void bindEdgeLightingService(IBinder iBinder, int i, ComponentName componentName) throws RemoteException;

    void cancelNotification(String str, String str2, int i, int i2, String str3) throws RemoteException;

    void cancelNotificationByGroupKey(String str, String str2, int i, int i2, String str3, String str4) throws RemoteException;

    void disable(int i, String str, IBinder iBinder) throws RemoteException;

    void disableEdgeLightingNotification(String str, boolean z) throws RemoteException;

    int getEdgeLightingState() throws RemoteException;

    boolean isEdgeLightingNotificationAllowed(String str) throws RemoteException;

    boolean isPackageEnabled(String str, int i) throws RemoteException;

    void registerEdgeLightingListener(IBinder iBinder, ComponentName componentName) throws RemoteException;

    void startEdgeLighting(String str, SemEdgeLightingInfo semEdgeLightingInfo, IBinder iBinder) throws RemoteException;

    void stopEdgeLighting(String str, IBinder iBinder) throws RemoteException;

    void unbindEdgeLightingService(IBinder iBinder, String str) throws RemoteException;

    void unregisterEdgeLightingListener(IBinder iBinder, String str) throws RemoteException;

    void updateEdgeLightingPackageList(String str, List<String> list) throws RemoteException;

    void updateEdgeLightingPolicy(String str, EdgeLightingPolicy edgeLightingPolicy) throws RemoteException;

    public static abstract class Stub extends Binder implements IEdgeManager {
        static final int TRANSACTION_bindEdgeLightingService = 1;
        static final int TRANSACTION_cancelNotification = 14;
        static final int TRANSACTION_cancelNotificationByGroupKey = 15;
        static final int TRANSACTION_disable = 11;
        static final int TRANSACTION_disableEdgeLightingNotification = 12;
        static final int TRANSACTION_getEdgeLightingState = 9;
        static final int TRANSACTION_isEdgeLightingNotificationAllowed = 10;
        static final int TRANSACTION_isPackageEnabled = 13;
        static final int TRANSACTION_registerEdgeLightingListener = 5;
        static final int TRANSACTION_startEdgeLighting = 7;
        static final int TRANSACTION_stopEdgeLighting = 8;
        static final int TRANSACTION_unbindEdgeLightingService = 2;
        static final int TRANSACTION_unregisterEdgeLightingListener = 6;
        static final int TRANSACTION_updateEdgeLightingPackageList = 3;
        static final int TRANSACTION_updateEdgeLightingPolicy = 4;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 14;
        }

        public Stub() {
            attachInterface(this, IEdgeManager.DESCRIPTOR);
        }

        public static IEdgeManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IEdgeManager.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IEdgeManager)) {
                return (IEdgeManager) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "bindEdgeLightingService";
                case 2:
                    return "unbindEdgeLightingService";
                case 3:
                    return "updateEdgeLightingPackageList";
                case 4:
                    return "updateEdgeLightingPolicy";
                case 5:
                    return "registerEdgeLightingListener";
                case 6:
                    return "unregisterEdgeLightingListener";
                case 7:
                    return "startEdgeLighting";
                case 8:
                    return "stopEdgeLighting";
                case 9:
                    return "getEdgeLightingState";
                case 10:
                    return "isEdgeLightingNotificationAllowed";
                case 11:
                    return SemWifiDisplayParameter.VALUE_DISABLE;
                case 12:
                    return "disableEdgeLightingNotification";
                case 13:
                    return "isPackageEnabled";
                case 14:
                    return "cancelNotification";
                case 15:
                    return "cancelNotificationByGroupKey";
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
                parcel.enforceInterface(IEdgeManager.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IEdgeManager.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    IBinder strongBinder = parcel.readStrongBinder();
                    int i3 = parcel.readInt();
                    ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    bindEdgeLightingService(strongBinder, i3, componentName);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    IBinder strongBinder2 = parcel.readStrongBinder();
                    String string = parcel.readString();
                    parcel.enforceNoDataAvail();
                    unbindEdgeLightingService(strongBinder2, string);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    String string2 = parcel.readString();
                    ArrayList<String> arrayListCreateStringArrayList = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    updateEdgeLightingPackageList(string2, arrayListCreateStringArrayList);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    String string3 = parcel.readString();
                    EdgeLightingPolicy edgeLightingPolicy = (EdgeLightingPolicy) parcel.readTypedObject(EdgeLightingPolicy.CREATOR);
                    parcel.enforceNoDataAvail();
                    updateEdgeLightingPolicy(string3, edgeLightingPolicy);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    IBinder strongBinder3 = parcel.readStrongBinder();
                    ComponentName componentName2 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    registerEdgeLightingListener(strongBinder3, componentName2);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    IBinder strongBinder4 = parcel.readStrongBinder();
                    String string4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    unregisterEdgeLightingListener(strongBinder4, string4);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    String string5 = parcel.readString();
                    SemEdgeLightingInfo semEdgeLightingInfo = (SemEdgeLightingInfo) parcel.readTypedObject(SemEdgeLightingInfo.CREATOR);
                    IBinder strongBinder5 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    startEdgeLighting(string5, semEdgeLightingInfo, strongBinder5);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    String string6 = parcel.readString();
                    IBinder strongBinder6 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    stopEdgeLighting(string6, strongBinder6);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    int edgeLightingState = getEdgeLightingState();
                    parcel2.writeNoException();
                    parcel2.writeInt(edgeLightingState);
                    return true;
                case 10:
                    String string7 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zIsEdgeLightingNotificationAllowed = isEdgeLightingNotificationAllowed(string7);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsEdgeLightingNotificationAllowed);
                    return true;
                case 11:
                    int i4 = parcel.readInt();
                    String string8 = parcel.readString();
                    IBinder strongBinder7 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    disable(i4, string8, strongBinder7);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    String string9 = parcel.readString();
                    boolean z = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    disableEdgeLightingNotification(string9, z);
                    parcel2.writeNoException();
                    return true;
                case 13:
                    String string10 = parcel.readString();
                    int i5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsPackageEnabled = isPackageEnabled(string10, i5);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsPackageEnabled);
                    return true;
                case 14:
                    String string11 = parcel.readString();
                    String string12 = parcel.readString();
                    int i6 = parcel.readInt();
                    int i7 = parcel.readInt();
                    String string13 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    cancelNotification(string11, string12, i6, i7, string13);
                    parcel2.writeNoException();
                    return true;
                case 15:
                    String string14 = parcel.readString();
                    String string15 = parcel.readString();
                    int i8 = parcel.readInt();
                    int i9 = parcel.readInt();
                    String string16 = parcel.readString();
                    String string17 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    cancelNotificationByGroupKey(string14, string15, i8, i9, string16, string17);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IEdgeManager {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IEdgeManager.DESCRIPTOR;
            }

            @Override // com.samsung.android.edge.IEdgeManager
            public void bindEdgeLightingService(IBinder iBinder, int i, ComponentName componentName) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEdgeManager.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.edge.IEdgeManager
            public void unbindEdgeLightingService(IBinder iBinder, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEdgeManager.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.edge.IEdgeManager
            public void updateEdgeLightingPackageList(String str, List<String> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEdgeManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeStringList(list);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.edge.IEdgeManager
            public void updateEdgeLightingPolicy(String str, EdgeLightingPolicy edgeLightingPolicy) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEdgeManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(edgeLightingPolicy, 0);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.edge.IEdgeManager
            public void registerEdgeLightingListener(IBinder iBinder, ComponentName componentName) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEdgeManager.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.edge.IEdgeManager
            public void unregisterEdgeLightingListener(IBinder iBinder, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEdgeManager.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.edge.IEdgeManager
            public void startEdgeLighting(String str, SemEdgeLightingInfo semEdgeLightingInfo, IBinder iBinder) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEdgeManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(semEdgeLightingInfo, 0);
                    parcelObtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.edge.IEdgeManager
            public void stopEdgeLighting(String str, IBinder iBinder) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEdgeManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.edge.IEdgeManager
            public int getEdgeLightingState() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEdgeManager.DESCRIPTOR);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.edge.IEdgeManager
            public boolean isEdgeLightingNotificationAllowed(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEdgeManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.edge.IEdgeManager
            public void disable(int i, String str, IBinder iBinder) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEdgeManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.edge.IEdgeManager
            public void disableEdgeLightingNotification(String str, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEdgeManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.edge.IEdgeManager
            public boolean isPackageEnabled(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEdgeManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.edge.IEdgeManager
            public void cancelNotification(String str, String str2, int i, int i2, String str3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEdgeManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeString(str3);
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.edge.IEdgeManager
            public void cancelNotificationByGroupKey(String str, String str2, int i, int i2, String str3, String str4) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEdgeManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeString(str3);
                    parcelObtain.writeString(str4);
                    this.mRemote.transact(15, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
