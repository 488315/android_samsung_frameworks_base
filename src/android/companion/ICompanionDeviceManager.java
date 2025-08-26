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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ICompanionDeviceManager)) {
                return (ICompanionDeviceManager) iInterfaceQueryLocalInterface;
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
                    IAssociationRequestCallback iAssociationRequestCallbackAsInterface = IAssociationRequestCallback.Stub.asInterface(parcel.readStrongBinder());
                    String string = parcel.readString();
                    int i3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    associate(associationRequest, iAssociationRequestCallbackAsInterface, string, i3);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    String string2 = parcel.readString();
                    int i4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<AssociationInfo> associations = getAssociations(string2, i4);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(associations, 1);
                    return true;
                case 3:
                    int i5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<AssociationInfo> allAssociationsForUser = getAllAssociationsForUser(i5);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(allAssociationsForUser, 1);
                    return true;
                case 4:
                    String string3 = parcel.readString();
                    String string4 = parcel.readString();
                    int i6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    legacyDisassociate(string3, string4, i6);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    int i7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    disassociate(i7);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zHasNotificationAccess = hasNotificationAccess(componentName);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zHasNotificationAccess);
                    return true;
                case 7:
                    ComponentName componentName2 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    int i8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    PendingIntent pendingIntentRequestNotificationAccess = requestNotificationAccess(componentName2, i8);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(pendingIntentRequestNotificationAccess, 1);
                    return true;
                case 8:
                    String string5 = parcel.readString();
                    String string6 = parcel.readString();
                    int i9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsDeviceAssociatedForWifiConnection = isDeviceAssociatedForWifiConnection(string5, string6, i9);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsDeviceAssociatedForWifiConnection);
                    return true;
                case 9:
                    String string7 = parcel.readString();
                    String string8 = parcel.readString();
                    int i10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    legacyStartObservingDevicePresence(string7, string8, i10);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    String string9 = parcel.readString();
                    String string10 = parcel.readString();
                    int i11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    legacyStopObservingDevicePresence(string9, string10, i11);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    ObservingDevicePresenceRequest observingDevicePresenceRequest = (ObservingDevicePresenceRequest) parcel.readTypedObject(ObservingDevicePresenceRequest.CREATOR);
                    String string11 = parcel.readString();
                    int i12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    startObservingDevicePresence(observingDevicePresenceRequest, string11, i12);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    ObservingDevicePresenceRequest observingDevicePresenceRequest2 = (ObservingDevicePresenceRequest) parcel.readTypedObject(ObservingDevicePresenceRequest.CREATOR);
                    String string12 = parcel.readString();
                    int i13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    stopObservingDevicePresence(observingDevicePresenceRequest2, string12, i13);
                    parcel2.writeNoException();
                    return true;
                case 13:
                    String string13 = parcel.readString();
                    String string14 = parcel.readString();
                    int i14 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zCanPairWithoutPrompt = canPairWithoutPrompt(string13, string14, i14);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zCanPairWithoutPrompt);
                    return true;
                case 14:
                    String string15 = parcel.readString();
                    String string16 = parcel.readString();
                    int i15 = parcel.readInt();
                    byte[] bArrCreateByteArray = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    createAssociation(string15, string16, i15, bArrCreateByteArray);
                    parcel2.writeNoException();
                    return true;
                case 15:
                    IOnAssociationsChangedListener iOnAssociationsChangedListenerAsInterface = IOnAssociationsChangedListener.Stub.asInterface(parcel.readStrongBinder());
                    int i16 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    addOnAssociationsChangedListener(iOnAssociationsChangedListenerAsInterface, i16);
                    parcel2.writeNoException();
                    return true;
                case 16:
                    IOnAssociationsChangedListener iOnAssociationsChangedListenerAsInterface2 = IOnAssociationsChangedListener.Stub.asInterface(parcel.readStrongBinder());
                    int i17 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    removeOnAssociationsChangedListener(iOnAssociationsChangedListenerAsInterface2, i17);
                    parcel2.writeNoException();
                    return true;
                case 17:
                    IOnTransportsChangedListener iOnTransportsChangedListenerAsInterface = IOnTransportsChangedListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    addOnTransportsChangedListener(iOnTransportsChangedListenerAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 18:
                    IOnTransportsChangedListener iOnTransportsChangedListenerAsInterface2 = IOnTransportsChangedListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    removeOnTransportsChangedListener(iOnTransportsChangedListenerAsInterface2);
                    parcel2.writeNoException();
                    return true;
                case 19:
                    int i18 = parcel.readInt();
                    byte[] bArrCreateByteArray2 = parcel.createByteArray();
                    int[] iArrCreateIntArray = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    sendMessage(i18, bArrCreateByteArray2, iArrCreateIntArray);
                    parcel2.writeNoException();
                    return true;
                case 20:
                    int i19 = parcel.readInt();
                    IOnMessageReceivedListener iOnMessageReceivedListenerAsInterface = IOnMessageReceivedListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    addOnMessageReceivedListener(i19, iOnMessageReceivedListenerAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 21:
                    int i20 = parcel.readInt();
                    IOnMessageReceivedListener iOnMessageReceivedListenerAsInterface2 = IOnMessageReceivedListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    removeOnMessageReceivedListener(i20, iOnMessageReceivedListenerAsInterface2);
                    parcel2.writeNoException();
                    return true;
                case 22:
                    int i21 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifySelfManagedDeviceAppeared(i21);
                    parcel2.writeNoException();
                    return true;
                case 23:
                    int i22 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifySelfManagedDeviceDisappeared(i22);
                    parcel2.writeNoException();
                    return true;
                case 24:
                    String string17 = parcel.readString();
                    int i23 = parcel.readInt();
                    int i24 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    PendingIntent pendingIntentBuildPermissionTransferUserConsentIntent = buildPermissionTransferUserConsentIntent(string17, i23, i24);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(pendingIntentBuildPermissionTransferUserConsentIntent, 1);
                    return true;
                case 25:
                    String string18 = parcel.readString();
                    int i25 = parcel.readInt();
                    int i26 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsPermissionTransferUserConsented = isPermissionTransferUserConsented(string18, i25, i26);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsPermissionTransferUserConsented);
                    return true;
                case 26:
                    String string19 = parcel.readString();
                    int i27 = parcel.readInt();
                    int i28 = parcel.readInt();
                    ISystemDataTransferCallback iSystemDataTransferCallbackAsInterface = ISystemDataTransferCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    startSystemDataTransfer(string19, i27, i28, iSystemDataTransferCallbackAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 27:
                    String string20 = parcel.readString();
                    int i29 = parcel.readInt();
                    int i30 = parcel.readInt();
                    ParcelFileDescriptor parcelFileDescriptor = (ParcelFileDescriptor) parcel.readTypedObject(ParcelFileDescriptor.CREATOR);
                    int i31 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    attachSystemDataTransport(string20, i29, i30, parcelFileDescriptor, i31);
                    parcel2.writeNoException();
                    return true;
                case 28:
                    String string21 = parcel.readString();
                    int i32 = parcel.readInt();
                    int i33 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    detachSystemDataTransport(string21, i32, i33);
                    parcel2.writeNoException();
                    return true;
                case 29:
                    String string22 = parcel.readString();
                    int i34 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsCompanionApplicationBound = isCompanionApplicationBound(string22, i34);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsCompanionApplicationBound);
                    return true;
                case 30:
                    String string23 = parcel.readString();
                    int i35 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    PendingIntent pendingIntentBuildAssociationCancellationIntent = buildAssociationCancellationIntent(string23, i35);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(pendingIntentBuildAssociationCancellationIntent, 1);
                    return true;
                case 31:
                    int i36 = parcel.readInt();
                    int i37 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    enableSystemDataSync(i36, i37);
                    parcel2.writeNoException();
                    return true;
                case 32:
                    int i38 = parcel.readInt();
                    int i39 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    disableSystemDataSync(i38, i39);
                    parcel2.writeNoException();
                    return true;
                case 33:
                    int i40 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    enablePermissionsSync(i40);
                    parcel2.writeNoException();
                    return true;
                case 34:
                    int i41 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    disablePermissionsSync(i41);
                    parcel2.writeNoException();
                    return true;
                case 35:
                    int i42 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    PermissionSyncRequest permissionSyncRequest = getPermissionSyncRequest(i42);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(permissionSyncRequest, 1);
                    return true;
                case 36:
                    boolean z = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    enableSecureTransport(z);
                    parcel2.writeNoException();
                    return true;
                case 37:
                    int i43 = parcel.readInt();
                    DeviceId deviceId = (DeviceId) parcel.readTypedObject(DeviceId.CREATOR);
                    parcel.enforceNoDataAvail();
                    setDeviceId(i43, deviceId);
                    parcel2.writeNoException();
                    return true;
                case 38:
                    int i44 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    byte[] backupPayload = getBackupPayload(i44);
                    parcel2.writeNoException();
                    parcel2.writeByteArray(backupPayload);
                    return true;
                case 39:
                    byte[] bArrCreateByteArray3 = parcel.createByteArray();
                    int i45 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    applyRestoredPayload(bArrCreateByteArray3, i45);
                    parcel2.writeNoException();
                    return true;
                case 40:
                    int i46 = parcel.readInt();
                    String string24 = parcel.readString();
                    int i47 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zRemoveBond = removeBond(i46, string24, i47);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zRemoveBond);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(associationRequest, 0);
                    parcelObtain.writeStrongInterface(iAssociationRequestCallback);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.companion.ICompanionDeviceManager
            public List<AssociationInfo> getAssociations(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(AssociationInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.companion.ICompanionDeviceManager
            public List<AssociationInfo> getAllAssociationsForUser(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(AssociationInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.companion.ICompanionDeviceManager
            public void legacyDisassociate(String str, String str2, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.companion.ICompanionDeviceManager
            public void disassociate(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.companion.ICompanionDeviceManager
            public boolean hasNotificationAccess(ComponentName componentName) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.companion.ICompanionDeviceManager
            public PendingIntent requestNotificationAccess(ComponentName componentName, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (PendingIntent) parcelObtain2.readTypedObject(PendingIntent.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.companion.ICompanionDeviceManager
            public boolean isDeviceAssociatedForWifiConnection(String str, String str2, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.companion.ICompanionDeviceManager
            public void legacyStartObservingDevicePresence(String str, String str2, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.companion.ICompanionDeviceManager
            public void legacyStopObservingDevicePresence(String str, String str2, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.companion.ICompanionDeviceManager
            public void startObservingDevicePresence(ObservingDevicePresenceRequest observingDevicePresenceRequest, String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(observingDevicePresenceRequest, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.companion.ICompanionDeviceManager
            public void stopObservingDevicePresence(ObservingDevicePresenceRequest observingDevicePresenceRequest, String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(observingDevicePresenceRequest, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.companion.ICompanionDeviceManager
            public boolean canPairWithoutPrompt(String str, String str2, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.companion.ICompanionDeviceManager
            public void createAssociation(String str, String str2, int i, byte[] bArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeByteArray(bArr);
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.companion.ICompanionDeviceManager
            public void addOnAssociationsChangedListener(IOnAssociationsChangedListener iOnAssociationsChangedListener, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iOnAssociationsChangedListener);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(15, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.companion.ICompanionDeviceManager
            public void removeOnAssociationsChangedListener(IOnAssociationsChangedListener iOnAssociationsChangedListener, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iOnAssociationsChangedListener);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(16, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.companion.ICompanionDeviceManager
            public void addOnTransportsChangedListener(IOnTransportsChangedListener iOnTransportsChangedListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iOnTransportsChangedListener);
                    this.mRemote.transact(17, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.companion.ICompanionDeviceManager
            public void removeOnTransportsChangedListener(IOnTransportsChangedListener iOnTransportsChangedListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iOnTransportsChangedListener);
                    this.mRemote.transact(18, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.companion.ICompanionDeviceManager
            public void sendMessage(int i, byte[] bArr, int[] iArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeByteArray(bArr);
                    parcelObtain.writeIntArray(iArr);
                    this.mRemote.transact(19, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.companion.ICompanionDeviceManager
            public void addOnMessageReceivedListener(int i, IOnMessageReceivedListener iOnMessageReceivedListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iOnMessageReceivedListener);
                    this.mRemote.transact(20, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.companion.ICompanionDeviceManager
            public void removeOnMessageReceivedListener(int i, IOnMessageReceivedListener iOnMessageReceivedListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iOnMessageReceivedListener);
                    this.mRemote.transact(21, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.companion.ICompanionDeviceManager
            public void notifySelfManagedDeviceAppeared(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(22, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.companion.ICompanionDeviceManager
            public void notifySelfManagedDeviceDisappeared(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(23, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.companion.ICompanionDeviceManager
            public PendingIntent buildPermissionTransferUserConsentIntent(String str, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(24, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (PendingIntent) parcelObtain2.readTypedObject(PendingIntent.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.companion.ICompanionDeviceManager
            public boolean isPermissionTransferUserConsented(String str, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(25, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.companion.ICompanionDeviceManager
            public void startSystemDataTransfer(String str, int i, int i2, ISystemDataTransferCallback iSystemDataTransferCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeStrongInterface(iSystemDataTransferCallback);
                    this.mRemote.transact(26, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.companion.ICompanionDeviceManager
            public void attachSystemDataTransport(String str, int i, int i2, ParcelFileDescriptor parcelFileDescriptor, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeTypedObject(parcelFileDescriptor, 0);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(27, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.companion.ICompanionDeviceManager
            public void detachSystemDataTransport(String str, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(28, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.companion.ICompanionDeviceManager
            public boolean isCompanionApplicationBound(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(29, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.companion.ICompanionDeviceManager
            public PendingIntent buildAssociationCancellationIntent(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(30, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (PendingIntent) parcelObtain2.readTypedObject(PendingIntent.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.companion.ICompanionDeviceManager
            public void enableSystemDataSync(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(31, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.companion.ICompanionDeviceManager
            public void disableSystemDataSync(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(32, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.companion.ICompanionDeviceManager
            public void enablePermissionsSync(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(33, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.companion.ICompanionDeviceManager
            public void disablePermissionsSync(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(34, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.companion.ICompanionDeviceManager
            public PermissionSyncRequest getPermissionSyncRequest(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(35, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (PermissionSyncRequest) parcelObtain2.readTypedObject(PermissionSyncRequest.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.companion.ICompanionDeviceManager
            public void enableSecureTransport(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(36, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.companion.ICompanionDeviceManager
            public void setDeviceId(int i, DeviceId deviceId) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(deviceId, 0);
                    this.mRemote.transact(37, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.companion.ICompanionDeviceManager
            public byte[] getBackupPayload(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(38, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createByteArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.companion.ICompanionDeviceManager
            public void applyRestoredPayload(byte[] bArr, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeByteArray(bArr);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(39, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.companion.ICompanionDeviceManager
            public boolean removeBond(int i, String str, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(40, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
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
