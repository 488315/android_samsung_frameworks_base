package android.hardware.location;

import android.Manifest;
import android.app.ActivityThread;
import android.app.PendingIntent;
import android.hardware.contexthub.HubEndpointInfo;
import android.hardware.contexthub.IContextHubEndpoint;
import android.hardware.contexthub.IContextHubEndpointCallback;
import android.hardware.contexthub.IContextHubEndpointDiscoveryCallback;
import android.hardware.location.IContextHubCallback;
import android.hardware.location.IContextHubClient;
import android.hardware.location.IContextHubClientCallback;
import android.hardware.location.IContextHubTransactionCallback;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.PermissionEnforcer;
import android.os.RemoteException;
import java.util.List;

/* loaded from: classes2.dex */
public interface IContextHubService extends IInterface {

    public static class Default implements IContextHubService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.hardware.location.IContextHubService
        public IContextHubClient createClient(int i, IContextHubClientCallback iContextHubClientCallback, String str, String str2) throws RemoteException {
            return null;
        }

        @Override // android.hardware.location.IContextHubService
        public IContextHubClient createPendingIntentClient(int i, PendingIntent pendingIntent, long j, String str) throws RemoteException {
            return null;
        }

        @Override // android.hardware.location.IContextHubService
        public void disableNanoApp(int i, IContextHubTransactionCallback iContextHubTransactionCallback, long j) throws RemoteException {
        }

        @Override // android.hardware.location.IContextHubService
        public void enableNanoApp(int i, IContextHubTransactionCallback iContextHubTransactionCallback, long j) throws RemoteException {
        }

        @Override // android.hardware.location.IContextHubService
        public List<HubEndpointInfo> findEndpoints(long j) throws RemoteException {
            return null;
        }

        @Override // android.hardware.location.IContextHubService
        public List<HubEndpointInfo> findEndpointsWithService(String str) throws RemoteException {
            return null;
        }

        @Override // android.hardware.location.IContextHubService
        public int[] findNanoAppOnHub(int i, NanoAppFilter nanoAppFilter) throws RemoteException {
            return null;
        }

        @Override // android.hardware.location.IContextHubService
        public int[] getContextHubHandles() throws RemoteException {
            return null;
        }

        @Override // android.hardware.location.IContextHubService
        public ContextHubInfo getContextHubInfo(int i) throws RemoteException {
            return null;
        }

        @Override // android.hardware.location.IContextHubService
        public List<ContextHubInfo> getContextHubs() throws RemoteException {
            return null;
        }

        @Override // android.hardware.location.IContextHubService
        public List<HubInfo> getHubs() throws RemoteException {
            return null;
        }

        @Override // android.hardware.location.IContextHubService
        public NanoAppInstanceInfo getNanoAppInstanceInfo(int i) throws RemoteException {
            return null;
        }

        @Override // android.hardware.location.IContextHubService
        public long[] getPreloadedNanoAppIds(ContextHubInfo contextHubInfo) throws RemoteException {
            return null;
        }

        @Override // android.hardware.location.IContextHubService
        public int loadNanoApp(int i, NanoApp nanoApp) throws RemoteException {
            return 0;
        }

        @Override // android.hardware.location.IContextHubService
        public void loadNanoAppOnHub(int i, IContextHubTransactionCallback iContextHubTransactionCallback, NanoAppBinary nanoAppBinary) throws RemoteException {
        }

        @Override // android.hardware.location.IContextHubService
        public void onDiscoveryCallbackFinished() throws RemoteException {
        }

        @Override // android.hardware.location.IContextHubService
        public void queryNanoApps(int i, IContextHubTransactionCallback iContextHubTransactionCallback) throws RemoteException {
        }

        @Override // android.hardware.location.IContextHubService
        public int registerCallback(IContextHubCallback iContextHubCallback) throws RemoteException {
            return 0;
        }

        @Override // android.hardware.location.IContextHubService
        public IContextHubEndpoint registerEndpoint(HubEndpointInfo hubEndpointInfo, IContextHubEndpointCallback iContextHubEndpointCallback, String str, String str2) throws RemoteException {
            return null;
        }

        @Override // android.hardware.location.IContextHubService
        public void registerEndpointDiscoveryCallbackDescriptor(String str, IContextHubEndpointDiscoveryCallback iContextHubEndpointDiscoveryCallback) throws RemoteException {
        }

        @Override // android.hardware.location.IContextHubService
        public void registerEndpointDiscoveryCallbackId(long j, IContextHubEndpointDiscoveryCallback iContextHubEndpointDiscoveryCallback) throws RemoteException {
        }

        @Override // android.hardware.location.IContextHubService
        public int sendMessage(int i, int i2, ContextHubMessage contextHubMessage) throws RemoteException {
            return 0;
        }

        @Override // android.hardware.location.IContextHubService
        public boolean setTestMode(boolean z) throws RemoteException {
            return false;
        }

        @Override // android.hardware.location.IContextHubService
        public int unloadNanoApp(int i) throws RemoteException {
            return 0;
        }

        @Override // android.hardware.location.IContextHubService
        public void unloadNanoAppFromHub(int i, IContextHubTransactionCallback iContextHubTransactionCallback, long j) throws RemoteException {
        }

        @Override // android.hardware.location.IContextHubService
        public void unregisterEndpointDiscoveryCallback(IContextHubEndpointDiscoveryCallback iContextHubEndpointDiscoveryCallback) throws RemoteException {
        }
    }

    IContextHubClient createClient(int i, IContextHubClientCallback iContextHubClientCallback, String str, String str2) throws RemoteException;

    IContextHubClient createPendingIntentClient(int i, PendingIntent pendingIntent, long j, String str) throws RemoteException;

    void disableNanoApp(int i, IContextHubTransactionCallback iContextHubTransactionCallback, long j) throws RemoteException;

    void enableNanoApp(int i, IContextHubTransactionCallback iContextHubTransactionCallback, long j) throws RemoteException;

    List<HubEndpointInfo> findEndpoints(long j) throws RemoteException;

    List<HubEndpointInfo> findEndpointsWithService(String str) throws RemoteException;

    int[] findNanoAppOnHub(int i, NanoAppFilter nanoAppFilter) throws RemoteException;

    int[] getContextHubHandles() throws RemoteException;

    ContextHubInfo getContextHubInfo(int i) throws RemoteException;

    List<ContextHubInfo> getContextHubs() throws RemoteException;

    List<HubInfo> getHubs() throws RemoteException;

    NanoAppInstanceInfo getNanoAppInstanceInfo(int i) throws RemoteException;

    long[] getPreloadedNanoAppIds(ContextHubInfo contextHubInfo) throws RemoteException;

    int loadNanoApp(int i, NanoApp nanoApp) throws RemoteException;

    void loadNanoAppOnHub(int i, IContextHubTransactionCallback iContextHubTransactionCallback, NanoAppBinary nanoAppBinary) throws RemoteException;

    void onDiscoveryCallbackFinished() throws RemoteException;

    void queryNanoApps(int i, IContextHubTransactionCallback iContextHubTransactionCallback) throws RemoteException;

    int registerCallback(IContextHubCallback iContextHubCallback) throws RemoteException;

    IContextHubEndpoint registerEndpoint(HubEndpointInfo hubEndpointInfo, IContextHubEndpointCallback iContextHubEndpointCallback, String str, String str2) throws RemoteException;

    void registerEndpointDiscoveryCallbackDescriptor(String str, IContextHubEndpointDiscoveryCallback iContextHubEndpointDiscoveryCallback) throws RemoteException;

    void registerEndpointDiscoveryCallbackId(long j, IContextHubEndpointDiscoveryCallback iContextHubEndpointDiscoveryCallback) throws RemoteException;

    int sendMessage(int i, int i2, ContextHubMessage contextHubMessage) throws RemoteException;

    boolean setTestMode(boolean z) throws RemoteException;

    int unloadNanoApp(int i) throws RemoteException;

    void unloadNanoAppFromHub(int i, IContextHubTransactionCallback iContextHubTransactionCallback, long j) throws RemoteException;

    void unregisterEndpointDiscoveryCallback(IContextHubEndpointDiscoveryCallback iContextHubEndpointDiscoveryCallback) throws RemoteException;

    public static abstract class Stub extends Binder implements IContextHubService {
        public static final String DESCRIPTOR = "android.hardware.location.IContextHubService";
        static final int TRANSACTION_createClient = 9;
        static final int TRANSACTION_createPendingIntentClient = 10;
        static final int TRANSACTION_disableNanoApp = 16;
        static final int TRANSACTION_enableNanoApp = 15;
        static final int TRANSACTION_findEndpoints = 20;
        static final int TRANSACTION_findEndpointsWithService = 21;
        static final int TRANSACTION_findNanoAppOnHub = 7;
        static final int TRANSACTION_getContextHubHandles = 2;
        static final int TRANSACTION_getContextHubInfo = 3;
        static final int TRANSACTION_getContextHubs = 11;
        static final int TRANSACTION_getHubs = 12;
        static final int TRANSACTION_getNanoAppInstanceInfo = 6;
        static final int TRANSACTION_getPreloadedNanoAppIds = 18;
        static final int TRANSACTION_loadNanoApp = 4;
        static final int TRANSACTION_loadNanoAppOnHub = 13;
        static final int TRANSACTION_onDiscoveryCallbackFinished = 26;
        static final int TRANSACTION_queryNanoApps = 17;
        static final int TRANSACTION_registerCallback = 1;
        static final int TRANSACTION_registerEndpoint = 22;
        static final int TRANSACTION_registerEndpointDiscoveryCallbackDescriptor = 24;
        static final int TRANSACTION_registerEndpointDiscoveryCallbackId = 23;
        static final int TRANSACTION_sendMessage = 8;
        static final int TRANSACTION_setTestMode = 19;
        static final int TRANSACTION_unloadNanoApp = 5;
        static final int TRANSACTION_unloadNanoAppFromHub = 14;
        static final int TRANSACTION_unregisterEndpointDiscoveryCallback = 25;
        private final PermissionEnforcer mEnforcer;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 25;
        }

        public Stub(PermissionEnforcer permissionEnforcer) {
            attachInterface(this, DESCRIPTOR);
            if (permissionEnforcer == null) {
                throw new IllegalArgumentException("enforcer cannot be null");
            }
            this.mEnforcer = permissionEnforcer;
        }

        @Deprecated
        public Stub() {
            this(PermissionEnforcer.fromContext(ActivityThread.currentActivityThread().getSystemContext()));
        }

        public static IContextHubService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IContextHubService)) {
                return (IContextHubService) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "registerCallback";
                case 2:
                    return "getContextHubHandles";
                case 3:
                    return "getContextHubInfo";
                case 4:
                    return "loadNanoApp";
                case 5:
                    return "unloadNanoApp";
                case 6:
                    return "getNanoAppInstanceInfo";
                case 7:
                    return "findNanoAppOnHub";
                case 8:
                    return "sendMessage";
                case 9:
                    return "createClient";
                case 10:
                    return "createPendingIntentClient";
                case 11:
                    return "getContextHubs";
                case 12:
                    return "getHubs";
                case 13:
                    return "loadNanoAppOnHub";
                case 14:
                    return "unloadNanoAppFromHub";
                case 15:
                    return "enableNanoApp";
                case 16:
                    return "disableNanoApp";
                case 17:
                    return "queryNanoApps";
                case 18:
                    return "getPreloadedNanoAppIds";
                case 19:
                    return "setTestMode";
                case 20:
                    return "findEndpoints";
                case 21:
                    return "findEndpointsWithService";
                case 22:
                    return "registerEndpoint";
                case 23:
                    return "registerEndpointDiscoveryCallbackId";
                case 24:
                    return "registerEndpointDiscoveryCallbackDescriptor";
                case 25:
                    return "unregisterEndpointDiscoveryCallback";
                case 26:
                    return "onDiscoveryCallbackFinished";
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
                parcel.enforceInterface(DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    IContextHubCallback iContextHubCallbackAsInterface = IContextHubCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int iRegisterCallback = registerCallback(iContextHubCallbackAsInterface);
                    parcel2.writeNoException();
                    parcel2.writeInt(iRegisterCallback);
                    return true;
                case 2:
                    int[] contextHubHandles = getContextHubHandles();
                    parcel2.writeNoException();
                    parcel2.writeIntArray(contextHubHandles);
                    return true;
                case 3:
                    int i3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ContextHubInfo contextHubInfo = getContextHubInfo(i3);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(contextHubInfo, 1);
                    return true;
                case 4:
                    int i4 = parcel.readInt();
                    NanoApp nanoApp = (NanoApp) parcel.readTypedObject(NanoApp.CREATOR);
                    parcel.enforceNoDataAvail();
                    int iLoadNanoApp = loadNanoApp(i4, nanoApp);
                    parcel2.writeNoException();
                    parcel2.writeInt(iLoadNanoApp);
                    return true;
                case 5:
                    int i5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int iUnloadNanoApp = unloadNanoApp(i5);
                    parcel2.writeNoException();
                    parcel2.writeInt(iUnloadNanoApp);
                    return true;
                case 6:
                    int i6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    NanoAppInstanceInfo nanoAppInstanceInfo = getNanoAppInstanceInfo(i6);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(nanoAppInstanceInfo, 1);
                    return true;
                case 7:
                    int i7 = parcel.readInt();
                    NanoAppFilter nanoAppFilter = (NanoAppFilter) parcel.readTypedObject(NanoAppFilter.CREATOR);
                    parcel.enforceNoDataAvail();
                    int[] iArrFindNanoAppOnHub = findNanoAppOnHub(i7, nanoAppFilter);
                    parcel2.writeNoException();
                    parcel2.writeIntArray(iArrFindNanoAppOnHub);
                    return true;
                case 8:
                    int i8 = parcel.readInt();
                    int i9 = parcel.readInt();
                    ContextHubMessage contextHubMessage = (ContextHubMessage) parcel.readTypedObject(ContextHubMessage.CREATOR);
                    parcel.enforceNoDataAvail();
                    int iSendMessage = sendMessage(i8, i9, contextHubMessage);
                    parcel2.writeNoException();
                    parcel2.writeInt(iSendMessage);
                    return true;
                case 9:
                    int i10 = parcel.readInt();
                    IContextHubClientCallback iContextHubClientCallbackAsInterface = IContextHubClientCallback.Stub.asInterface(parcel.readStrongBinder());
                    String string = parcel.readString();
                    String string2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    IContextHubClient iContextHubClientCreateClient = createClient(i10, iContextHubClientCallbackAsInterface, string, string2);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(iContextHubClientCreateClient);
                    return true;
                case 10:
                    int i11 = parcel.readInt();
                    PendingIntent pendingIntent = (PendingIntent) parcel.readTypedObject(PendingIntent.CREATOR);
                    long j = parcel.readLong();
                    String string3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    IContextHubClient iContextHubClientCreatePendingIntentClient = createPendingIntentClient(i11, pendingIntent, j, string3);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(iContextHubClientCreatePendingIntentClient);
                    return true;
                case 11:
                    List<ContextHubInfo> contextHubs = getContextHubs();
                    parcel2.writeNoException();
                    parcel2.writeTypedList(contextHubs, 1);
                    return true;
                case 12:
                    List<HubInfo> hubs = getHubs();
                    parcel2.writeNoException();
                    parcel2.writeTypedList(hubs, 1);
                    return true;
                case 13:
                    int i12 = parcel.readInt();
                    IContextHubTransactionCallback iContextHubTransactionCallbackAsInterface = IContextHubTransactionCallback.Stub.asInterface(parcel.readStrongBinder());
                    NanoAppBinary nanoAppBinary = (NanoAppBinary) parcel.readTypedObject(NanoAppBinary.CREATOR);
                    parcel.enforceNoDataAvail();
                    loadNanoAppOnHub(i12, iContextHubTransactionCallbackAsInterface, nanoAppBinary);
                    parcel2.writeNoException();
                    return true;
                case 14:
                    int i13 = parcel.readInt();
                    IContextHubTransactionCallback iContextHubTransactionCallbackAsInterface2 = IContextHubTransactionCallback.Stub.asInterface(parcel.readStrongBinder());
                    long j2 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    unloadNanoAppFromHub(i13, iContextHubTransactionCallbackAsInterface2, j2);
                    parcel2.writeNoException();
                    return true;
                case 15:
                    int i14 = parcel.readInt();
                    IContextHubTransactionCallback iContextHubTransactionCallbackAsInterface3 = IContextHubTransactionCallback.Stub.asInterface(parcel.readStrongBinder());
                    long j3 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    enableNanoApp(i14, iContextHubTransactionCallbackAsInterface3, j3);
                    parcel2.writeNoException();
                    return true;
                case 16:
                    int i15 = parcel.readInt();
                    IContextHubTransactionCallback iContextHubTransactionCallbackAsInterface4 = IContextHubTransactionCallback.Stub.asInterface(parcel.readStrongBinder());
                    long j4 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    disableNanoApp(i15, iContextHubTransactionCallbackAsInterface4, j4);
                    parcel2.writeNoException();
                    return true;
                case 17:
                    int i16 = parcel.readInt();
                    IContextHubTransactionCallback iContextHubTransactionCallbackAsInterface5 = IContextHubTransactionCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    queryNanoApps(i16, iContextHubTransactionCallbackAsInterface5);
                    parcel2.writeNoException();
                    return true;
                case 18:
                    ContextHubInfo contextHubInfo2 = (ContextHubInfo) parcel.readTypedObject(ContextHubInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    long[] preloadedNanoAppIds = getPreloadedNanoAppIds(contextHubInfo2);
                    parcel2.writeNoException();
                    parcel2.writeLongArray(preloadedNanoAppIds);
                    return true;
                case 19:
                    boolean z = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean testMode = setTestMode(z);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(testMode);
                    return true;
                case 20:
                    long j5 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    List<HubEndpointInfo> listFindEndpoints = findEndpoints(j5);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(listFindEndpoints, 1);
                    return true;
                case 21:
                    String string4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    List<HubEndpointInfo> listFindEndpointsWithService = findEndpointsWithService(string4);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(listFindEndpointsWithService, 1);
                    return true;
                case 22:
                    HubEndpointInfo hubEndpointInfo = (HubEndpointInfo) parcel.readTypedObject(HubEndpointInfo.CREATOR);
                    IContextHubEndpointCallback iContextHubEndpointCallbackAsInterface = IContextHubEndpointCallback.Stub.asInterface(parcel.readStrongBinder());
                    String string5 = parcel.readString();
                    String string6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    IContextHubEndpoint iContextHubEndpointRegisterEndpoint = registerEndpoint(hubEndpointInfo, iContextHubEndpointCallbackAsInterface, string5, string6);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(iContextHubEndpointRegisterEndpoint);
                    return true;
                case 23:
                    long j6 = parcel.readLong();
                    IContextHubEndpointDiscoveryCallback iContextHubEndpointDiscoveryCallbackAsInterface = IContextHubEndpointDiscoveryCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerEndpointDiscoveryCallbackId(j6, iContextHubEndpointDiscoveryCallbackAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 24:
                    String string7 = parcel.readString();
                    IContextHubEndpointDiscoveryCallback iContextHubEndpointDiscoveryCallbackAsInterface2 = IContextHubEndpointDiscoveryCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerEndpointDiscoveryCallbackDescriptor(string7, iContextHubEndpointDiscoveryCallbackAsInterface2);
                    parcel2.writeNoException();
                    return true;
                case 25:
                    IContextHubEndpointDiscoveryCallback iContextHubEndpointDiscoveryCallbackAsInterface3 = IContextHubEndpointDiscoveryCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterEndpointDiscoveryCallback(iContextHubEndpointDiscoveryCallbackAsInterface3);
                    parcel2.writeNoException();
                    return true;
                case 26:
                    onDiscoveryCallbackFinished();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IContextHubService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // android.hardware.location.IContextHubService
            public int registerCallback(IContextHubCallback iContextHubCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iContextHubCallback);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.location.IContextHubService
            public int[] getContextHubHandles() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createIntArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.location.IContextHubService
            public ContextHubInfo getContextHubInfo(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ContextHubInfo) parcelObtain2.readTypedObject(ContextHubInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.location.IContextHubService
            public int loadNanoApp(int i, NanoApp nanoApp) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(nanoApp, 0);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.location.IContextHubService
            public int unloadNanoApp(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.location.IContextHubService
            public NanoAppInstanceInfo getNanoAppInstanceInfo(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (NanoAppInstanceInfo) parcelObtain2.readTypedObject(NanoAppInstanceInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.location.IContextHubService
            public int[] findNanoAppOnHub(int i, NanoAppFilter nanoAppFilter) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(nanoAppFilter, 0);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createIntArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.location.IContextHubService
            public int sendMessage(int i, int i2, ContextHubMessage contextHubMessage) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeTypedObject(contextHubMessage, 0);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.location.IContextHubService
            public IContextHubClient createClient(int i, IContextHubClientCallback iContextHubClientCallback, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iContextHubClientCallback);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return IContextHubClient.Stub.asInterface(parcelObtain2.readStrongBinder());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.location.IContextHubService
            public IContextHubClient createPendingIntentClient(int i, PendingIntent pendingIntent, long j, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(pendingIntent, 0);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return IContextHubClient.Stub.asInterface(parcelObtain2.readStrongBinder());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.location.IContextHubService
            public List<ContextHubInfo> getContextHubs() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(ContextHubInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.location.IContextHubService
            public List<HubInfo> getHubs() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(HubInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.location.IContextHubService
            public void loadNanoAppOnHub(int i, IContextHubTransactionCallback iContextHubTransactionCallback, NanoAppBinary nanoAppBinary) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iContextHubTransactionCallback);
                    parcelObtain.writeTypedObject(nanoAppBinary, 0);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.location.IContextHubService
            public void unloadNanoAppFromHub(int i, IContextHubTransactionCallback iContextHubTransactionCallback, long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iContextHubTransactionCallback);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.location.IContextHubService
            public void enableNanoApp(int i, IContextHubTransactionCallback iContextHubTransactionCallback, long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iContextHubTransactionCallback);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(15, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.location.IContextHubService
            public void disableNanoApp(int i, IContextHubTransactionCallback iContextHubTransactionCallback, long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iContextHubTransactionCallback);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(16, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.location.IContextHubService
            public void queryNanoApps(int i, IContextHubTransactionCallback iContextHubTransactionCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iContextHubTransactionCallback);
                    this.mRemote.transact(17, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.location.IContextHubService
            public long[] getPreloadedNanoAppIds(ContextHubInfo contextHubInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextHubInfo, 0);
                    this.mRemote.transact(18, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createLongArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.location.IContextHubService
            public boolean setTestMode(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(19, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.location.IContextHubService
            public List<HubEndpointInfo> findEndpoints(long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(20, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(HubEndpointInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.location.IContextHubService
            public List<HubEndpointInfo> findEndpointsWithService(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(21, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(HubEndpointInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.location.IContextHubService
            public IContextHubEndpoint registerEndpoint(HubEndpointInfo hubEndpointInfo, IContextHubEndpointCallback iContextHubEndpointCallback, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(hubEndpointInfo, 0);
                    parcelObtain.writeStrongInterface(iContextHubEndpointCallback);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(22, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return IContextHubEndpoint.Stub.asInterface(parcelObtain2.readStrongBinder());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.location.IContextHubService
            public void registerEndpointDiscoveryCallbackId(long j, IContextHubEndpointDiscoveryCallback iContextHubEndpointDiscoveryCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeStrongInterface(iContextHubEndpointDiscoveryCallback);
                    this.mRemote.transact(23, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.location.IContextHubService
            public void registerEndpointDiscoveryCallbackDescriptor(String str, IContextHubEndpointDiscoveryCallback iContextHubEndpointDiscoveryCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeStrongInterface(iContextHubEndpointDiscoveryCallback);
                    this.mRemote.transact(24, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.location.IContextHubService
            public void unregisterEndpointDiscoveryCallback(IContextHubEndpointDiscoveryCallback iContextHubEndpointDiscoveryCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iContextHubEndpointDiscoveryCallback);
                    this.mRemote.transact(25, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.location.IContextHubService
            public void onDiscoveryCallbackFinished() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(26, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }

        protected void registerCallback_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.ACCESS_CONTEXT_HUB, getCallingPid(), getCallingUid());
        }

        protected void getContextHubHandles_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.ACCESS_CONTEXT_HUB, getCallingPid(), getCallingUid());
        }

        protected void getContextHubInfo_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.ACCESS_CONTEXT_HUB, getCallingPid(), getCallingUid());
        }

        protected void loadNanoApp_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.ACCESS_CONTEXT_HUB, getCallingPid(), getCallingUid());
        }

        protected void unloadNanoApp_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.ACCESS_CONTEXT_HUB, getCallingPid(), getCallingUid());
        }

        protected void getNanoAppInstanceInfo_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.ACCESS_CONTEXT_HUB, getCallingPid(), getCallingUid());
        }

        protected void findNanoAppOnHub_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.ACCESS_CONTEXT_HUB, getCallingPid(), getCallingUid());
        }

        protected void sendMessage_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.ACCESS_CONTEXT_HUB, getCallingPid(), getCallingUid());
        }

        protected void createClient_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.ACCESS_CONTEXT_HUB, getCallingPid(), getCallingUid());
        }

        protected void createPendingIntentClient_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.ACCESS_CONTEXT_HUB, getCallingPid(), getCallingUid());
        }

        protected void getContextHubs_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.ACCESS_CONTEXT_HUB, getCallingPid(), getCallingUid());
        }

        protected void getHubs_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.ACCESS_CONTEXT_HUB, getCallingPid(), getCallingUid());
        }

        protected void loadNanoAppOnHub_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.ACCESS_CONTEXT_HUB, getCallingPid(), getCallingUid());
        }

        protected void unloadNanoAppFromHub_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.ACCESS_CONTEXT_HUB, getCallingPid(), getCallingUid());
        }

        protected void enableNanoApp_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.ACCESS_CONTEXT_HUB, getCallingPid(), getCallingUid());
        }

        protected void disableNanoApp_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.ACCESS_CONTEXT_HUB, getCallingPid(), getCallingUid());
        }

        protected void queryNanoApps_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.ACCESS_CONTEXT_HUB, getCallingPid(), getCallingUid());
        }

        protected void getPreloadedNanoAppIds_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.ACCESS_CONTEXT_HUB, getCallingPid(), getCallingUid());
        }

        protected void setTestMode_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.ACCESS_CONTEXT_HUB, getCallingPid(), getCallingUid());
        }

        protected void findEndpoints_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.ACCESS_CONTEXT_HUB, getCallingPid(), getCallingUid());
        }

        protected void findEndpointsWithService_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.ACCESS_CONTEXT_HUB, getCallingPid(), getCallingUid());
        }

        protected void registerEndpoint_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.ACCESS_CONTEXT_HUB, getCallingPid(), getCallingUid());
        }

        protected void registerEndpointDiscoveryCallbackId_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.ACCESS_CONTEXT_HUB, getCallingPid(), getCallingUid());
        }

        protected void registerEndpointDiscoveryCallbackDescriptor_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.ACCESS_CONTEXT_HUB, getCallingPid(), getCallingUid());
        }

        protected void unregisterEndpointDiscoveryCallback_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.ACCESS_CONTEXT_HUB, getCallingPid(), getCallingUid());
        }

        protected void onDiscoveryCallbackFinished_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.ACCESS_CONTEXT_HUB, getCallingPid(), getCallingUid());
        }
    }
}
