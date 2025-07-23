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
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IEdgeManager.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IEdgeManager)) {
                return (IEdgeManager) queryLocalInterface;
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
                    IBinder readStrongBinder = parcel.readStrongBinder();
                    int readInt = parcel.readInt();
                    ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    bindEdgeLightingService(readStrongBinder, readInt, componentName);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    IBinder readStrongBinder2 = parcel.readStrongBinder();
                    String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    unbindEdgeLightingService(readStrongBinder2, readString);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    String readString2 = parcel.readString();
                    ArrayList<String> createStringArrayList = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    updateEdgeLightingPackageList(readString2, createStringArrayList);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    String readString3 = parcel.readString();
                    EdgeLightingPolicy edgeLightingPolicy = (EdgeLightingPolicy) parcel.readTypedObject(EdgeLightingPolicy.CREATOR);
                    parcel.enforceNoDataAvail();
                    updateEdgeLightingPolicy(readString3, edgeLightingPolicy);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    IBinder readStrongBinder3 = parcel.readStrongBinder();
                    ComponentName componentName2 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    registerEdgeLightingListener(readStrongBinder3, componentName2);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    IBinder readStrongBinder4 = parcel.readStrongBinder();
                    String readString4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    unregisterEdgeLightingListener(readStrongBinder4, readString4);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    String readString5 = parcel.readString();
                    SemEdgeLightingInfo semEdgeLightingInfo = (SemEdgeLightingInfo) parcel.readTypedObject(SemEdgeLightingInfo.CREATOR);
                    IBinder readStrongBinder5 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    startEdgeLighting(readString5, semEdgeLightingInfo, readStrongBinder5);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    String readString6 = parcel.readString();
                    IBinder readStrongBinder6 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    stopEdgeLighting(readString6, readStrongBinder6);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    int edgeLightingState = getEdgeLightingState();
                    parcel2.writeNoException();
                    parcel2.writeInt(edgeLightingState);
                    return true;
                case 10:
                    String readString7 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isEdgeLightingNotificationAllowed = isEdgeLightingNotificationAllowed(readString7);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isEdgeLightingNotificationAllowed);
                    return true;
                case 11:
                    int readInt2 = parcel.readInt();
                    String readString8 = parcel.readString();
                    IBinder readStrongBinder7 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    disable(readInt2, readString8, readStrongBinder7);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    String readString9 = parcel.readString();
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    disableEdgeLightingNotification(readString9, readBoolean);
                    parcel2.writeNoException();
                    return true;
                case 13:
                    String readString10 = parcel.readString();
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isPackageEnabled = isPackageEnabled(readString10, readInt3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isPackageEnabled);
                    return true;
                case 14:
                    String readString11 = parcel.readString();
                    String readString12 = parcel.readString();
                    int readInt4 = parcel.readInt();
                    int readInt5 = parcel.readInt();
                    String readString13 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    cancelNotification(readString11, readString12, readInt4, readInt5, readString13);
                    parcel2.writeNoException();
                    return true;
                case 15:
                    String readString14 = parcel.readString();
                    String readString15 = parcel.readString();
                    int readInt6 = parcel.readInt();
                    int readInt7 = parcel.readInt();
                    String readString16 = parcel.readString();
                    String readString17 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    cancelNotificationByGroupKey(readString14, readString15, readInt6, readInt7, readString16, readString17);
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
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEdgeManager.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.edge.IEdgeManager
            public void unbindEdgeLightingService(IBinder iBinder, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEdgeManager.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeString(str);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.edge.IEdgeManager
            public void updateEdgeLightingPackageList(String str, List<String> list) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEdgeManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStringList(list);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.edge.IEdgeManager
            public void updateEdgeLightingPolicy(String str, EdgeLightingPolicy edgeLightingPolicy) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEdgeManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(edgeLightingPolicy, 0);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.edge.IEdgeManager
            public void registerEdgeLightingListener(IBinder iBinder, ComponentName componentName) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEdgeManager.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.edge.IEdgeManager
            public void unregisterEdgeLightingListener(IBinder iBinder, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEdgeManager.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeString(str);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.edge.IEdgeManager
            public void startEdgeLighting(String str, SemEdgeLightingInfo semEdgeLightingInfo, IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEdgeManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(semEdgeLightingInfo, 0);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.edge.IEdgeManager
            public void stopEdgeLighting(String str, IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEdgeManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.edge.IEdgeManager
            public int getEdgeLightingState() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEdgeManager.DESCRIPTOR);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.edge.IEdgeManager
            public boolean isEdgeLightingNotificationAllowed(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEdgeManager.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.edge.IEdgeManager
            public void disable(int i, String str, IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEdgeManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.edge.IEdgeManager
            public void disableEdgeLightingNotification(String str, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEdgeManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.edge.IEdgeManager
            public boolean isPackageEnabled(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEdgeManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.edge.IEdgeManager
            public void cancelNotification(String str, String str2, int i, int i2, String str3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEdgeManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str3);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.edge.IEdgeManager
            public void cancelNotificationByGroupKey(String str, String str2, int i, int i2, String str3, String str4) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEdgeManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str3);
                    obtain.writeString(str4);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
