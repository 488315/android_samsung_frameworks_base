package android.companion;

import android.Manifest;
import android.app.ActivityThread;
import android.app.PendingIntent;
import android.companion.IAssociationRequestCallback;
import android.companion.IOnAssociationsChangedListener;
import android.companion.IOnMessageReceivedListener;
import android.companion.IOnTransportsChangedListener;
import android.companion.ISystemDataTransferCallback;
import android.companion.datatransfer.PermissionSyncRequest;
import android.content.ComponentName;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.PermissionEnforcer;
import android.os.RemoteException;
import java.util.List;

/* loaded from: classes.dex */
public interface ICompanionDeviceManager extends IInterface {

    public static class Default implements ICompanionDeviceManager {
        @Override // android.companion.ICompanionDeviceManager
        public void addOnAssociationsChangedListener(IOnAssociationsChangedListener iOnAssociationsChangedListener, int i) throws RemoteException {
        }

        @Override // android.companion.ICompanionDeviceManager
        public void addOnMessageReceivedListener(int i, IOnMessageReceivedListener iOnMessageReceivedListener) throws RemoteException {
        }

        @Override // android.companion.ICompanionDeviceManager
        public void addOnTransportsChangedListener(IOnTransportsChangedListener iOnTransportsChangedListener) throws RemoteException {
        }

        @Override // android.companion.ICompanionDeviceManager
        public void applyRestoredPayload(byte[] bArr, int i) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.companion.ICompanionDeviceManager
        public void associate(AssociationRequest associationRequest, IAssociationRequestCallback iAssociationRequestCallback, String str, int i) throws RemoteException {
        }

        @Override // android.companion.ICompanionDeviceManager
        public void attachSystemDataTransport(String str, int i, int i2, ParcelFileDescriptor parcelFileDescriptor, int i3) throws RemoteException {
        }

        @Override // android.companion.ICompanionDeviceManager
        public PendingIntent buildAssociationCancellationIntent(String str, int i) throws RemoteException {
            return null;
        }

        @Override // android.companion.ICompanionDeviceManager
        public PendingIntent buildPermissionTransferUserConsentIntent(String str, int i, int i2) throws RemoteException {
            return null;
        }

        @Override // android.companion.ICompanionDeviceManager
        public boolean canPairWithoutPrompt(String str, String str2, int i) throws RemoteException {
            return false;
        }

        @Override // android.companion.ICompanionDeviceManager
        public void createAssociation(String str, String str2, int i, byte[] bArr) throws RemoteException {
        }

        @Override // android.companion.ICompanionDeviceManager
        public void detachSystemDataTransport(String str, int i, int i2) throws RemoteException {
        }

        @Override // android.companion.ICompanionDeviceManager
        public void disablePermissionsSync(int i) throws RemoteException {
        }

        @Override // android.companion.ICompanionDeviceManager
        public void disableSystemDataSync(int i, int i2) throws RemoteException {
        }

        @Override // android.companion.ICompanionDeviceManager
        public void disassociate(int i) throws RemoteException {
        }

        @Override // android.companion.ICompanionDeviceManager
        public void enablePermissionsSync(int i) throws RemoteException {
        }

        @Override // android.companion.ICompanionDeviceManager
        public void enableSecureTransport(boolean z) throws RemoteException {
        }

        @Override // android.companion.ICompanionDeviceManager
        public void enableSystemDataSync(int i, int i2) throws RemoteException {
        }

        @Override // android.companion.ICompanionDeviceManager
        public List<AssociationInfo> getAllAssociationsForUser(int i) throws RemoteException {
            return null;
        }

        @Override // android.companion.ICompanionDeviceManager
        public List<AssociationInfo> getAssociations(String str, int i) throws RemoteException {
            return null;
        }

        @Override // android.companion.ICompanionDeviceManager
        public byte[] getBackupPayload(int i) throws RemoteException {
            return null;
        }

        @Override // android.companion.ICompanionDeviceManager
        public PermissionSyncRequest getPermissionSyncRequest(int i) throws RemoteException {
            return null;
        }

        @Override // android.companion.ICompanionDeviceManager
        public boolean hasNotificationAccess(ComponentName componentName) throws RemoteException {
            return false;
        }

        @Override // android.companion.ICompanionDeviceManager
        public boolean isCompanionApplicationBound(String str, int i) throws RemoteException {
            return false;
        }

        @Override // android.companion.ICompanionDeviceManager
        public boolean isDeviceAssociatedForWifiConnection(String str, String str2, int i) throws RemoteException {
            return false;
        }

        @Override // android.companion.ICompanionDeviceManager
        public boolean isPermissionTransferUserConsented(String str, int i, int i2) throws RemoteException {
            return false;
        }

        @Override // android.companion.ICompanionDeviceManager
        public void legacyDisassociate(String str, String str2, int i) throws RemoteException {
        }

        @Override // android.companion.ICompanionDeviceManager
        public void legacyStartObservingDevicePresence(String str, String str2, int i) throws RemoteException {
        }

        @Override // android.companion.ICompanionDeviceManager
        public void legacyStopObservingDevicePresence(String str, String str2, int i) throws RemoteException {
        }

        @Override // android.companion.ICompanionDeviceManager
        public void notifySelfManagedDeviceAppeared(int i) throws RemoteException {
        }

        @Override // android.companion.ICompanionDeviceManager
        public void notifySelfManagedDeviceDisappeared(int i) throws RemoteException {
        }

        @Override // android.companion.ICompanionDeviceManager
        public boolean removeBond(int i, String str, int i2) throws RemoteException {
            return false;
        }

        @Override // android.companion.ICompanionDeviceManager
        public void removeOnAssociationsChangedListener(IOnAssociationsChangedListener iOnAssociationsChangedListener, int i) throws RemoteException {
        }

        @Override // android.companion.ICompanionDeviceManager
        public void removeOnMessageReceivedListener(int i, IOnMessageReceivedListener iOnMessageReceivedListener) throws RemoteException {
        }

        @Override // android.companion.ICompanionDeviceManager
        public void removeOnTransportsChangedListener(IOnTransportsChangedListener iOnTransportsChangedListener) throws RemoteException {
        }

        @Override // android.companion.ICompanionDeviceManager
        public PendingIntent requestNotificationAccess(ComponentName componentName, int i) throws RemoteException {
            return null;
        }

        @Override // android.companion.ICompanionDeviceManager
        public void sendMessage(int i, byte[] bArr, int[] iArr) throws RemoteException {
        }

        @Override // android.companion.ICompanionDeviceManager
        public void setDeviceId(int i, DeviceId deviceId) throws RemoteException {
        }

        @Override // android.companion.ICompanionDeviceManager
        public void startObservingDevicePresence(ObservingDevicePresenceRequest observingDevicePresenceRequest, String str, int i) throws RemoteException {
        }

        @Override // android.companion.ICompanionDeviceManager
        public void startSystemDataTransfer(String str, int i, int i2, ISystemDataTransferCallback iSystemDataTransferCallback) throws RemoteException {
        }

        @Override // android.companion.ICompanionDeviceManager
        public void stopObservingDevicePresence(ObservingDevicePresenceRequest observingDevicePresenceRequest, String str, int i) throws RemoteException {
        }
    }

    void addOnAssociationsChangedListener(IOnAssociationsChangedListener iOnAssociationsChangedListener, int i) throws RemoteException;

    void addOnMessageReceivedListener(int i, IOnMessageReceivedListener iOnMessageReceivedListener) throws RemoteException;

    void addOnTransportsChangedListener(IOnTransportsChangedListener iOnTransportsChangedListener) throws RemoteException;

    void applyRestoredPayload(byte[] bArr, int i) throws RemoteException;

    void associate(AssociationRequest associationRequest, IAssociationRequestCallback iAssociationRequestCallback, String str, int i) throws RemoteException;

    void attachSystemDataTransport(String str, int i, int i2, ParcelFileDescriptor parcelFileDescriptor, int i3) throws RemoteException;

    PendingIntent buildAssociationCancellationIntent(String str, int i) throws RemoteException;

    PendingIntent buildPermissionTransferUserConsentIntent(String str, int i, int i2) throws RemoteException;

    boolean canPairWithoutPrompt(String str, String str2, int i) throws RemoteException;

    void createAssociation(String str, String str2, int i, byte[] bArr) throws RemoteException;

    void detachSystemDataTransport(String str, int i, int i2) throws RemoteException;

    void disablePermissionsSync(int i) throws RemoteException;

    void disableSystemDataSync(int i, int i2) throws RemoteException;

    void disassociate(int i) throws RemoteException;

    void enablePermissionsSync(int i) throws RemoteException;

    void enableSecureTransport(boolean z) throws RemoteException;

    void enableSystemDataSync(int i, int i2) throws RemoteException;

    List<AssociationInfo> getAllAssociationsForUser(int i) throws RemoteException;

    List<AssociationInfo> getAssociations(String str, int i) throws RemoteException;

    byte[] getBackupPayload(int i) throws RemoteException;

    PermissionSyncRequest getPermissionSyncRequest(int i) throws RemoteException;

    @Deprecated
    boolean hasNotificationAccess(ComponentName componentName) throws RemoteException;

    boolean isCompanionApplicationBound(String str, int i) throws RemoteException;

    boolean isDeviceAssociatedForWifiConnection(String str, String str2, int i) throws RemoteException;

    boolean isPermissionTransferUserConsented(String str, int i, int i2) throws RemoteException;

    @Deprecated
    void legacyDisassociate(String str, String str2, int i) throws RemoteException;

    void legacyStartObservingDevicePresence(String str, String str2, int i) throws RemoteException;

    void legacyStopObservingDevicePresence(String str, String str2, int i) throws RemoteException;

    void notifySelfManagedDeviceAppeared(int i) throws RemoteException;

    void notifySelfManagedDeviceDisappeared(int i) throws RemoteException;

    boolean removeBond(int i, String str, int i2) throws RemoteException;

    void removeOnAssociationsChangedListener(IOnAssociationsChangedListener iOnAssociationsChangedListener, int i) throws RemoteException;

    void removeOnMessageReceivedListener(int i, IOnMessageReceivedListener iOnMessageReceivedListener) throws RemoteException;

    void removeOnTransportsChangedListener(IOnTransportsChangedListener iOnTransportsChangedListener) throws RemoteException;

    PendingIntent requestNotificationAccess(ComponentName componentName, int i) throws RemoteException;

    void sendMessage(int i, byte[] bArr, int[] iArr) throws RemoteException;

    void setDeviceId(int i, DeviceId deviceId) throws RemoteException;

    void startObservingDevicePresence(ObservingDevicePresenceRequest observingDevicePresenceRequest, String str, int i) throws RemoteException;

    void startSystemDataTransfer(String str, int i, int i2, ISystemDataTransferCallback iSystemDataTransferCallback) throws RemoteException;

    void stopObservingDevicePresence(ObservingDevicePresenceRequest observingDevicePresenceRequest, String str, int i) throws RemoteException;

    public static abstract class Stub extends Binder implements ICompanionDeviceManager {
        public static final String DESCRIPTOR = "android.companion.ICompanionDeviceManager";
        static final int TRANSACTION_addOnAssociationsChangedListener = 15;
        static final int TRANSACTION_addOnMessageReceivedListener = 20;
        static final int TRANSACTION_addOnTransportsChangedListener = 17;
        static final int TRANSACTION_applyRestoredPayload = 39;
        static final int TRANSACTION_associate = 1;
        static final int TRANSACTION_attachSystemDataTransport = 27;
        static final int TRANSACTION_buildAssociationCancellationIntent = 30;
        static final int TRANSACTION_buildPermissionTransferUserConsentIntent = 24;
        static final int TRANSACTION_canPairWithoutPrompt = 13;
        static final int TRANSACTION_createAssociation = 14;
        static final int TRANSACTION_detachSystemDataTransport = 28;
        static final int TRANSACTION_disablePermissionsSync = 34;
        static final int TRANSACTION_disableSystemDataSync = 32;
        static final int TRANSACTION_disassociate = 5;
        static final int TRANSACTION_enablePermissionsSync = 33;
        static final int TRANSACTION_enableSecureTransport = 36;
        static final int TRANSACTION_enableSystemDataSync = 31;
        static final int TRANSACTION_getAllAssociationsForUser = 3;
        static final int TRANSACTION_getAssociations = 2;
        static final int TRANSACTION_getBackupPayload = 38;
        static final int TRANSACTION_getPermissionSyncRequest = 35;
        static final int TRANSACTION_hasNotificationAccess = 6;
        static final int TRANSACTION_isCompanionApplicationBound = 29;
        static final int TRANSACTION_isDeviceAssociatedForWifiConnection = 8;
        static final int TRANSACTION_isPermissionTransferUserConsented = 25;
        static final int TRANSACTION_legacyDisassociate = 4;
        static final int TRANSACTION_legacyStartObservingDevicePresence = 9;
        static final int TRANSACTION_legacyStopObservingDevicePresence = 10;
        static final int TRANSACTION_notifySelfManagedDeviceAppeared = 22;
        static final int TRANSACTION_notifySelfManagedDeviceDisappeared = 23;
        static final int TRANSACTION_removeBond = 40;
        static final int TRANSACTION_removeOnAssociationsChangedListener = 16;
        static final int TRANSACTION_removeOnMessageReceivedListener = 21;
        static final int TRANSACTION_removeOnTransportsChangedListener = 18;
        static final int TRANSACTION_requestNotificationAccess = 7;
        static final int TRANSACTION_sendMessage = 19;
        static final int TRANSACTION_setDeviceId = 37;
        static final int TRANSACTION_startObservingDevicePresence = 11;
        static final int TRANSACTION_startSystemDataTransfer = 26;
        static final int TRANSACTION_stopObservingDevicePresence = 12;
        private final PermissionEnforcer mEnforcer;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 39;
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

        public static ICompanionDeviceManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ICompanionDeviceManager)) {
                return (ICompanionDeviceManager) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "associate";
                case 2:
                    return "getAssociations";
                case 3:
                    return "getAllAssociationsForUser";
                case 4:
                    return "legacyDisassociate";
                case 5:
                    return "disassociate";
                case 6:
                    return "hasNotificationAccess";
                case 7:
                    return "requestNotificationAccess";
                case 8:
                    return "isDeviceAssociatedForWifiConnection";
                case 9:
                    return "legacyStartObservingDevicePresence";
                case 10:
                    return "legacyStopObservingDevicePresence";
                case 11:
                    return "startObservingDevicePresence";
                case 12:
                    return "stopObservingDevicePresence";
                case 13:
                    return "canPairWithoutPrompt";
                case 14:
                    return "createAssociation";
                case 15:
                    return "addOnAssociationsChangedListener";
                case 16:
                    return "removeOnAssociationsChangedListener";
                case 17:
                    return "addOnTransportsChangedListener";
                case 18:
                    return "removeOnTransportsChangedListener";
                case 19:
                    return "sendMessage";
                case 20:
                    return "addOnMessageReceivedListener";
                case 21:
                    return "removeOnMessageReceivedListener";
                case 22:
                    return "notifySelfManagedDeviceAppeared";
                case 23:
                    return "notifySelfManagedDeviceDisappeared";
                case 24:
                    return "buildPermissionTransferUserConsentIntent";
                case 25:
                    return "isPermissionTransferUserConsented";
                case 26:
                    return "startSystemDataTransfer";
                case 27:
                    return "attachSystemDataTransport";
                case 28:
                    return "detachSystemDataTransport";
                case 29:
                    return "isCompanionApplicationBound";
                case 30:
                    return "buildAssociationCancellationIntent";
                case 31:
                    return "enableSystemDataSync";
                case 32:
                    return "disableSystemDataSync";
                case 33:
                    return "enablePermissionsSync";
                case 34:
                    return "disablePermissionsSync";
                case 35:
                    return "getPermissionSyncRequest";
                case 36:
                    return "enableSecureTransport";
                case 37:
                    return "setDeviceId";
                case 38:
                    return "getBackupPayload";
                case 39:
                    return "applyRestoredPayload";
                case 40:
                    return "removeBond";
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
                    AssociationRequest associationRequest = (AssociationRequest) parcel.readTypedObject(AssociationRequest.CREATOR);
                    IAssociationRequestCallback asInterface = IAssociationRequestCallback.Stub.asInterface(parcel.readStrongBinder());
                    String readString = parcel.readString();
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    associate(associationRequest, asInterface, readString, readInt);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    String readString2 = parcel.readString();
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<AssociationInfo> associations = getAssociations(readString2, readInt2);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(associations, 1);
                    return true;
                case 3:
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<AssociationInfo> allAssociationsForUser = getAllAssociationsForUser(readInt3);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(allAssociationsForUser, 1);
                    return true;
                case 4:
                    String readString3 = parcel.readString();
                    String readString4 = parcel.readString();
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    legacyDisassociate(readString3, readString4, readInt4);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    disassociate(readInt5);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean hasNotificationAccess = hasNotificationAccess(componentName);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(hasNotificationAccess);
                    return true;
                case 7:
                    ComponentName componentName2 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    PendingIntent requestNotificationAccess = requestNotificationAccess(componentName2, readInt6);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(requestNotificationAccess, 1);
                    return true;
                case 8:
                    String readString5 = parcel.readString();
                    String readString6 = parcel.readString();
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isDeviceAssociatedForWifiConnection = isDeviceAssociatedForWifiConnection(readString5, readString6, readInt7);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isDeviceAssociatedForWifiConnection);
                    return true;
                case 9:
                    String readString7 = parcel.readString();
                    String readString8 = parcel.readString();
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    legacyStartObservingDevicePresence(readString7, readString8, readInt8);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    String readString9 = parcel.readString();
                    String readString10 = parcel.readString();
                    int readInt9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    legacyStopObservingDevicePresence(readString9, readString10, readInt9);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    ObservingDevicePresenceRequest observingDevicePresenceRequest = (ObservingDevicePresenceRequest) parcel.readTypedObject(ObservingDevicePresenceRequest.CREATOR);
                    String readString11 = parcel.readString();
                    int readInt10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    startObservingDevicePresence(observingDevicePresenceRequest, readString11, readInt10);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    ObservingDevicePresenceRequest observingDevicePresenceRequest2 = (ObservingDevicePresenceRequest) parcel.readTypedObject(ObservingDevicePresenceRequest.CREATOR);
                    String readString12 = parcel.readString();
                    int readInt11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    stopObservingDevicePresence(observingDevicePresenceRequest2, readString12, readInt11);
                    parcel2.writeNoException();
                    return true;
                case 13:
                    String readString13 = parcel.readString();
                    String readString14 = parcel.readString();
                    int readInt12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean canPairWithoutPrompt = canPairWithoutPrompt(readString13, readString14, readInt12);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(canPairWithoutPrompt);
                    return true;
                case 14:
                    String readString15 = parcel.readString();
                    String readString16 = parcel.readString();
                    int readInt13 = parcel.readInt();
                    byte[] createByteArray = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    createAssociation(readString15, readString16, readInt13, createByteArray);
                    parcel2.writeNoException();
                    return true;
                case 15:
                    IOnAssociationsChangedListener asInterface2 = IOnAssociationsChangedListener.Stub.asInterface(parcel.readStrongBinder());
                    int readInt14 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    addOnAssociationsChangedListener(asInterface2, readInt14);
                    parcel2.writeNoException();
                    return true;
                case 16:
                    IOnAssociationsChangedListener asInterface3 = IOnAssociationsChangedListener.Stub.asInterface(parcel.readStrongBinder());
                    int readInt15 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    removeOnAssociationsChangedListener(asInterface3, readInt15);
                    parcel2.writeNoException();
                    return true;
                case 17:
                    IOnTransportsChangedListener asInterface4 = IOnTransportsChangedListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    addOnTransportsChangedListener(asInterface4);
                    parcel2.writeNoException();
                    return true;
                case 18:
                    IOnTransportsChangedListener asInterface5 = IOnTransportsChangedListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    removeOnTransportsChangedListener(asInterface5);
                    parcel2.writeNoException();
                    return true;
                case 19:
                    int readInt16 = parcel.readInt();
                    byte[] createByteArray2 = parcel.createByteArray();
                    int[] createIntArray = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    sendMessage(readInt16, createByteArray2, createIntArray);
                    parcel2.writeNoException();
                    return true;
                case 20:
                    int readInt17 = parcel.readInt();
                    IOnMessageReceivedListener asInterface6 = IOnMessageReceivedListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    addOnMessageReceivedListener(readInt17, asInterface6);
                    parcel2.writeNoException();
                    return true;
                case 21:
                    int readInt18 = parcel.readInt();
                    IOnMessageReceivedListener asInterface7 = IOnMessageReceivedListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    removeOnMessageReceivedListener(readInt18, asInterface7);
                    parcel2.writeNoException();
                    return true;
                case 22:
                    int readInt19 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifySelfManagedDeviceAppeared(readInt19);
                    parcel2.writeNoException();
                    return true;
                case 23:
                    int readInt20 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifySelfManagedDeviceDisappeared(readInt20);
                    parcel2.writeNoException();
                    return true;
                case 24:
                    String readString17 = parcel.readString();
                    int readInt21 = parcel.readInt();
                    int readInt22 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    PendingIntent buildPermissionTransferUserConsentIntent = buildPermissionTransferUserConsentIntent(readString17, readInt21, readInt22);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(buildPermissionTransferUserConsentIntent, 1);
                    return true;
                case 25:
                    String readString18 = parcel.readString();
                    int readInt23 = parcel.readInt();
                    int readInt24 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isPermissionTransferUserConsented = isPermissionTransferUserConsented(readString18, readInt23, readInt24);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isPermissionTransferUserConsented);
                    return true;
                case 26:
                    String readString19 = parcel.readString();
                    int readInt25 = parcel.readInt();
                    int readInt26 = parcel.readInt();
                    ISystemDataTransferCallback asInterface8 = ISystemDataTransferCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    startSystemDataTransfer(readString19, readInt25, readInt26, asInterface8);
                    parcel2.writeNoException();
                    return true;
                case 27:
                    String readString20 = parcel.readString();
                    int readInt27 = parcel.readInt();
                    int readInt28 = parcel.readInt();
                    ParcelFileDescriptor parcelFileDescriptor = (ParcelFileDescriptor) parcel.readTypedObject(ParcelFileDescriptor.CREATOR);
                    int readInt29 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    attachSystemDataTransport(readString20, readInt27, readInt28, parcelFileDescriptor, readInt29);
                    parcel2.writeNoException();
                    return true;
                case 28:
                    String readString21 = parcel.readString();
                    int readInt30 = parcel.readInt();
                    int readInt31 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    detachSystemDataTransport(readString21, readInt30, readInt31);
                    parcel2.writeNoException();
                    return true;
                case 29:
                    String readString22 = parcel.readString();
                    int readInt32 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isCompanionApplicationBound = isCompanionApplicationBound(readString22, readInt32);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isCompanionApplicationBound);
                    return true;
                case 30:
                    String readString23 = parcel.readString();
                    int readInt33 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    PendingIntent buildAssociationCancellationIntent = buildAssociationCancellationIntent(readString23, readInt33);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(buildAssociationCancellationIntent, 1);
                    return true;
                case 31:
                    int readInt34 = parcel.readInt();
                    int readInt35 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    enableSystemDataSync(readInt34, readInt35);
                    parcel2.writeNoException();
                    return true;
                case 32:
                    int readInt36 = parcel.readInt();
                    int readInt37 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    disableSystemDataSync(readInt36, readInt37);
                    parcel2.writeNoException();
                    return true;
                case 33:
                    int readInt38 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    enablePermissionsSync(readInt38);
                    parcel2.writeNoException();
                    return true;
                case 34:
                    int readInt39 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    disablePermissionsSync(readInt39);
                    parcel2.writeNoException();
                    return true;
                case 35:
                    int readInt40 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    PermissionSyncRequest permissionSyncRequest = getPermissionSyncRequest(readInt40);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(permissionSyncRequest, 1);
                    return true;
                case 36:
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    enableSecureTransport(readBoolean);
                    parcel2.writeNoException();
                    return true;
                case 37:
                    int readInt41 = parcel.readInt();
                    DeviceId deviceId = (DeviceId) parcel.readTypedObject(DeviceId.CREATOR);
                    parcel.enforceNoDataAvail();
                    setDeviceId(readInt41, deviceId);
                    parcel2.writeNoException();
                    return true;
                case 38:
                    int readInt42 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    byte[] backupPayload = getBackupPayload(readInt42);
                    parcel2.writeNoException();
                    parcel2.writeByteArray(backupPayload);
                    return true;
                case 39:
                    byte[] createByteArray3 = parcel.createByteArray();
                    int readInt43 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    applyRestoredPayload(createByteArray3, readInt43);
                    parcel2.writeNoException();
                    return true;
                case 40:
                    int readInt44 = parcel.readInt();
                    String readString24 = parcel.readString();
                    int readInt45 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean removeBond = removeBond(readInt44, readString24, readInt45);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(removeBond);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements ICompanionDeviceManager {
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

            @Override // android.companion.ICompanionDeviceManager
            public void associate(AssociationRequest associationRequest, IAssociationRequestCallback iAssociationRequestCallback, String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(associationRequest, 0);
                    obtain.writeStrongInterface(iAssociationRequestCallback);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.ICompanionDeviceManager
            public List<AssociationInfo> getAssociations(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(AssociationInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.ICompanionDeviceManager
            public List<AssociationInfo> getAllAssociationsForUser(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(AssociationInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.ICompanionDeviceManager
            public void legacyDisassociate(String str, String str2, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.ICompanionDeviceManager
            public void disassociate(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.ICompanionDeviceManager
            public boolean hasNotificationAccess(ComponentName componentName) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.ICompanionDeviceManager
            public PendingIntent requestNotificationAccess(ComponentName componentName, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return (PendingIntent) obtain2.readTypedObject(PendingIntent.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.ICompanionDeviceManager
            public boolean isDeviceAssociatedForWifiConnection(String str, String str2, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.ICompanionDeviceManager
            public void legacyStartObservingDevicePresence(String str, String str2, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.ICompanionDeviceManager
            public void legacyStopObservingDevicePresence(String str, String str2, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.ICompanionDeviceManager
            public void startObservingDevicePresence(ObservingDevicePresenceRequest observingDevicePresenceRequest, String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(observingDevicePresenceRequest, 0);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.ICompanionDeviceManager
            public void stopObservingDevicePresence(ObservingDevicePresenceRequest observingDevicePresenceRequest, String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(observingDevicePresenceRequest, 0);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.ICompanionDeviceManager
            public boolean canPairWithoutPrompt(String str, String str2, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.ICompanionDeviceManager
            public void createAssociation(String str, String str2, int i, byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.ICompanionDeviceManager
            public void addOnAssociationsChangedListener(IOnAssociationsChangedListener iOnAssociationsChangedListener, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iOnAssociationsChangedListener);
                    obtain.writeInt(i);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.ICompanionDeviceManager
            public void removeOnAssociationsChangedListener(IOnAssociationsChangedListener iOnAssociationsChangedListener, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iOnAssociationsChangedListener);
                    obtain.writeInt(i);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.ICompanionDeviceManager
            public void addOnTransportsChangedListener(IOnTransportsChangedListener iOnTransportsChangedListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iOnTransportsChangedListener);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.ICompanionDeviceManager
            public void removeOnTransportsChangedListener(IOnTransportsChangedListener iOnTransportsChangedListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iOnTransportsChangedListener);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.ICompanionDeviceManager
            public void sendMessage(int i, byte[] bArr, int[] iArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeByteArray(bArr);
                    obtain.writeIntArray(iArr);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.ICompanionDeviceManager
            public void addOnMessageReceivedListener(int i, IOnMessageReceivedListener iOnMessageReceivedListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iOnMessageReceivedListener);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.ICompanionDeviceManager
            public void removeOnMessageReceivedListener(int i, IOnMessageReceivedListener iOnMessageReceivedListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iOnMessageReceivedListener);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.ICompanionDeviceManager
            public void notifySelfManagedDeviceAppeared(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.ICompanionDeviceManager
            public void notifySelfManagedDeviceDisappeared(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.ICompanionDeviceManager
            public PendingIntent buildPermissionTransferUserConsentIntent(String str, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                    return (PendingIntent) obtain2.readTypedObject(PendingIntent.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.ICompanionDeviceManager
            public boolean isPermissionTransferUserConsented(String str, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.ICompanionDeviceManager
            public void startSystemDataTransfer(String str, int i, int i2, ISystemDataTransferCallback iSystemDataTransferCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeStrongInterface(iSystemDataTransferCallback);
                    this.mRemote.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.ICompanionDeviceManager
            public void attachSystemDataTransport(String str, int i, int i2, ParcelFileDescriptor parcelFileDescriptor, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(parcelFileDescriptor, 0);
                    obtain.writeInt(i3);
                    this.mRemote.transact(27, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.ICompanionDeviceManager
            public void detachSystemDataTransport(String str, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(28, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.ICompanionDeviceManager
            public boolean isCompanionApplicationBound(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(29, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.ICompanionDeviceManager
            public PendingIntent buildAssociationCancellationIntent(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(30, obtain, obtain2, 0);
                    obtain2.readException();
                    return (PendingIntent) obtain2.readTypedObject(PendingIntent.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.ICompanionDeviceManager
            public void enableSystemDataSync(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(31, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.ICompanionDeviceManager
            public void disableSystemDataSync(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(32, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.ICompanionDeviceManager
            public void enablePermissionsSync(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(33, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.ICompanionDeviceManager
            public void disablePermissionsSync(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(34, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.ICompanionDeviceManager
            public PermissionSyncRequest getPermissionSyncRequest(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(35, obtain, obtain2, 0);
                    obtain2.readException();
                    return (PermissionSyncRequest) obtain2.readTypedObject(PermissionSyncRequest.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.ICompanionDeviceManager
            public void enableSecureTransport(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(36, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.ICompanionDeviceManager
            public void setDeviceId(int i, DeviceId deviceId) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(deviceId, 0);
                    this.mRemote.transact(37, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.ICompanionDeviceManager
            public byte[] getBackupPayload(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(38, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createByteArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.ICompanionDeviceManager
            public void applyRestoredPayload(byte[] bArr, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    obtain.writeInt(i);
                    this.mRemote.transact(39, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.ICompanionDeviceManager
            public boolean removeBond(int i, String str, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeInt(i2);
                    this.mRemote.transact(40, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        protected void getAllAssociationsForUser_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_COMPANION_DEVICES, getCallingPid(), getCallingUid());
        }

        protected void isDeviceAssociatedForWifiConnection_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_COMPANION_DEVICES, getCallingPid(), getCallingUid());
        }

        protected void legacyStartObservingDevicePresence_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.REQUEST_OBSERVE_COMPANION_DEVICE_PRESENCE, getCallingPid(), getCallingUid());
        }

        protected void legacyStopObservingDevicePresence_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.REQUEST_OBSERVE_COMPANION_DEVICE_PRESENCE, getCallingPid(), getCallingUid());
        }

        protected void startObservingDevicePresence_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.REQUEST_OBSERVE_COMPANION_DEVICE_PRESENCE, getCallingPid(), getCallingUid());
        }

        protected void stopObservingDevicePresence_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.REQUEST_OBSERVE_COMPANION_DEVICE_PRESENCE, getCallingPid(), getCallingUid());
        }

        protected void createAssociation_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.ASSOCIATE_COMPANION_DEVICES, getCallingPid(), getCallingUid());
        }

        protected void addOnAssociationsChangedListener_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_COMPANION_DEVICES, getCallingPid(), getCallingUid());
        }

        protected void removeOnAssociationsChangedListener_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_COMPANION_DEVICES, getCallingPid(), getCallingUid());
        }

        protected void addOnTransportsChangedListener_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.USE_COMPANION_TRANSPORTS, getCallingPid(), getCallingUid());
        }

        protected void removeOnTransportsChangedListener_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.USE_COMPANION_TRANSPORTS, getCallingPid(), getCallingUid());
        }

        protected void sendMessage_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.USE_COMPANION_TRANSPORTS, getCallingPid(), getCallingUid());
        }

        protected void addOnMessageReceivedListener_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.USE_COMPANION_TRANSPORTS, getCallingPid(), getCallingUid());
        }

        protected void removeOnMessageReceivedListener_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.USE_COMPANION_TRANSPORTS, getCallingPid(), getCallingUid());
        }

        protected void notifySelfManagedDeviceAppeared_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.REQUEST_COMPANION_SELF_MANAGED, getCallingPid(), getCallingUid());
        }

        protected void notifySelfManagedDeviceDisappeared_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.REQUEST_COMPANION_SELF_MANAGED, getCallingPid(), getCallingUid());
        }

        protected void attachSystemDataTransport_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.DELIVER_COMPANION_MESSAGES, getCallingPid(), getCallingUid());
        }

        protected void detachSystemDataTransport_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.DELIVER_COMPANION_MESSAGES, getCallingPid(), getCallingUid());
        }

        protected void enableSecureTransport_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_COMPANION_DEVICES, getCallingPid(), getCallingUid());
        }

        protected void removeBond_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.BLUETOOTH_CONNECT, getCallingPid(), getCallingUid());
        }
    }
}
