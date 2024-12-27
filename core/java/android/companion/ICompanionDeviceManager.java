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
    void addOnAssociationsChangedListener(IOnAssociationsChangedListener iOnAssociationsChangedListener, int i) throws RemoteException;

    void addOnMessageReceivedListener(int i, IOnMessageReceivedListener iOnMessageReceivedListener) throws RemoteException;

    void addOnTransportsChangedListener(IOnTransportsChangedListener iOnTransportsChangedListener) throws RemoteException;

    void applyRestoredPayload(byte[] bArr, int i) throws RemoteException;

    void associate(AssociationRequest associationRequest, IAssociationRequestCallback iAssociationRequestCallback, String str, int i) throws RemoteException;

    void attachSystemDataTransport(String str, int i, int i2, ParcelFileDescriptor parcelFileDescriptor) throws RemoteException;

    PendingIntent buildAssociationCancellationIntent(String str, int i) throws RemoteException;

    PendingIntent buildPermissionTransferUserConsentIntent(String str, int i, int i2) throws RemoteException;

    boolean canPairWithoutPrompt(String str, String str2, int i) throws RemoteException;

    void clearAssociationTag(int i) throws RemoteException;

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

    void setAssociationTag(int i, String str) throws RemoteException;

    void startObservingDevicePresence(ObservingDevicePresenceRequest observingDevicePresenceRequest, String str, int i) throws RemoteException;

    void startSystemDataTransfer(String str, int i, int i2, ISystemDataTransferCallback iSystemDataTransferCallback) throws RemoteException;

    void stopObservingDevicePresence(ObservingDevicePresenceRequest observingDevicePresenceRequest, String str, int i) throws RemoteException;

    public static class Default implements ICompanionDeviceManager {
        @Override // android.companion.ICompanionDeviceManager
        public void associate(AssociationRequest request, IAssociationRequestCallback callback, String callingPackage, int userId) throws RemoteException {
        }

        @Override // android.companion.ICompanionDeviceManager
        public List<AssociationInfo> getAssociations(String callingPackage, int userId) throws RemoteException {
            return null;
        }

        @Override // android.companion.ICompanionDeviceManager
        public List<AssociationInfo> getAllAssociationsForUser(int userId) throws RemoteException {
            return null;
        }

        @Override // android.companion.ICompanionDeviceManager
        public void legacyDisassociate(String deviceMacAddress, String callingPackage, int userId) throws RemoteException {
        }

        @Override // android.companion.ICompanionDeviceManager
        public void disassociate(int associationId) throws RemoteException {
        }

        @Override // android.companion.ICompanionDeviceManager
        public boolean hasNotificationAccess(ComponentName component) throws RemoteException {
            return false;
        }

        @Override // android.companion.ICompanionDeviceManager
        public PendingIntent requestNotificationAccess(ComponentName component, int userId) throws RemoteException {
            return null;
        }

        @Override // android.companion.ICompanionDeviceManager
        public boolean isDeviceAssociatedForWifiConnection(String packageName, String macAddress, int userId) throws RemoteException {
            return false;
        }

        @Override // android.companion.ICompanionDeviceManager
        public void legacyStartObservingDevicePresence(String deviceAddress, String callingPackage, int userId) throws RemoteException {
        }

        @Override // android.companion.ICompanionDeviceManager
        public void legacyStopObservingDevicePresence(String deviceAddress, String callingPackage, int userId) throws RemoteException {
        }

        @Override // android.companion.ICompanionDeviceManager
        public void startObservingDevicePresence(ObservingDevicePresenceRequest request, String packageName, int userId) throws RemoteException {
        }

        @Override // android.companion.ICompanionDeviceManager
        public void stopObservingDevicePresence(ObservingDevicePresenceRequest request, String packageName, int userId) throws RemoteException {
        }

        @Override // android.companion.ICompanionDeviceManager
        public boolean canPairWithoutPrompt(String packageName, String deviceMacAddress, int userId) throws RemoteException {
            return false;
        }

        @Override // android.companion.ICompanionDeviceManager
        public void createAssociation(String packageName, String macAddress, int userId, byte[] certificate) throws RemoteException {
        }

        @Override // android.companion.ICompanionDeviceManager
        public void addOnAssociationsChangedListener(IOnAssociationsChangedListener listener, int userId) throws RemoteException {
        }

        @Override // android.companion.ICompanionDeviceManager
        public void removeOnAssociationsChangedListener(IOnAssociationsChangedListener listener, int userId) throws RemoteException {
        }

        @Override // android.companion.ICompanionDeviceManager
        public void addOnTransportsChangedListener(IOnTransportsChangedListener listener) throws RemoteException {
        }

        @Override // android.companion.ICompanionDeviceManager
        public void removeOnTransportsChangedListener(IOnTransportsChangedListener listener) throws RemoteException {
        }

        @Override // android.companion.ICompanionDeviceManager
        public void sendMessage(int messageType, byte[] data, int[] associationIds) throws RemoteException {
        }

        @Override // android.companion.ICompanionDeviceManager
        public void addOnMessageReceivedListener(int messageType, IOnMessageReceivedListener listener) throws RemoteException {
        }

        @Override // android.companion.ICompanionDeviceManager
        public void removeOnMessageReceivedListener(int messageType, IOnMessageReceivedListener listener) throws RemoteException {
        }

        @Override // android.companion.ICompanionDeviceManager
        public void notifySelfManagedDeviceAppeared(int associationId) throws RemoteException {
        }

        @Override // android.companion.ICompanionDeviceManager
        public void notifySelfManagedDeviceDisappeared(int associationId) throws RemoteException {
        }

        @Override // android.companion.ICompanionDeviceManager
        public PendingIntent buildPermissionTransferUserConsentIntent(String callingPackage, int userId, int associationId) throws RemoteException {
            return null;
        }

        @Override // android.companion.ICompanionDeviceManager
        public boolean isPermissionTransferUserConsented(String callingPackage, int userId, int associationId) throws RemoteException {
            return false;
        }

        @Override // android.companion.ICompanionDeviceManager
        public void startSystemDataTransfer(String packageName, int userId, int associationId, ISystemDataTransferCallback callback) throws RemoteException {
        }

        @Override // android.companion.ICompanionDeviceManager
        public void attachSystemDataTransport(String packageName, int userId, int associationId, ParcelFileDescriptor fd) throws RemoteException {
        }

        @Override // android.companion.ICompanionDeviceManager
        public void detachSystemDataTransport(String packageName, int userId, int associationId) throws RemoteException {
        }

        @Override // android.companion.ICompanionDeviceManager
        public boolean isCompanionApplicationBound(String packageName, int userId) throws RemoteException {
            return false;
        }

        @Override // android.companion.ICompanionDeviceManager
        public PendingIntent buildAssociationCancellationIntent(String callingPackage, int userId) throws RemoteException {
            return null;
        }

        @Override // android.companion.ICompanionDeviceManager
        public void enableSystemDataSync(int associationId, int flags) throws RemoteException {
        }

        @Override // android.companion.ICompanionDeviceManager
        public void disableSystemDataSync(int associationId, int flags) throws RemoteException {
        }

        @Override // android.companion.ICompanionDeviceManager
        public void enablePermissionsSync(int associationId) throws RemoteException {
        }

        @Override // android.companion.ICompanionDeviceManager
        public void disablePermissionsSync(int associationId) throws RemoteException {
        }

        @Override // android.companion.ICompanionDeviceManager
        public PermissionSyncRequest getPermissionSyncRequest(int associationId) throws RemoteException {
            return null;
        }

        @Override // android.companion.ICompanionDeviceManager
        public void enableSecureTransport(boolean enabled) throws RemoteException {
        }

        @Override // android.companion.ICompanionDeviceManager
        public void setAssociationTag(int associationId, String tag) throws RemoteException {
        }

        @Override // android.companion.ICompanionDeviceManager
        public void clearAssociationTag(int associationId) throws RemoteException {
        }

        @Override // android.companion.ICompanionDeviceManager
        public byte[] getBackupPayload(int userId) throws RemoteException {
            return null;
        }

        @Override // android.companion.ICompanionDeviceManager
        public void applyRestoredPayload(byte[] payload, int userId) throws RemoteException {
        }

        @Override // android.companion.ICompanionDeviceManager
        public boolean removeBond(int associationId, String packageName, int userId) throws RemoteException {
            return false;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements ICompanionDeviceManager {
        public static final String DESCRIPTOR = "android.companion.ICompanionDeviceManager";
        static final int TRANSACTION_addOnAssociationsChangedListener = 15;
        static final int TRANSACTION_addOnMessageReceivedListener = 20;
        static final int TRANSACTION_addOnTransportsChangedListener = 17;
        static final int TRANSACTION_applyRestoredPayload = 40;
        static final int TRANSACTION_associate = 1;
        static final int TRANSACTION_attachSystemDataTransport = 27;
        static final int TRANSACTION_buildAssociationCancellationIntent = 30;
        static final int TRANSACTION_buildPermissionTransferUserConsentIntent = 24;
        static final int TRANSACTION_canPairWithoutPrompt = 13;
        static final int TRANSACTION_clearAssociationTag = 38;
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
        static final int TRANSACTION_getBackupPayload = 39;
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
        static final int TRANSACTION_removeBond = 41;
        static final int TRANSACTION_removeOnAssociationsChangedListener = 16;
        static final int TRANSACTION_removeOnMessageReceivedListener = 21;
        static final int TRANSACTION_removeOnTransportsChangedListener = 18;
        static final int TRANSACTION_requestNotificationAccess = 7;
        static final int TRANSACTION_sendMessage = 19;
        static final int TRANSACTION_setAssociationTag = 37;
        static final int TRANSACTION_startObservingDevicePresence = 11;
        static final int TRANSACTION_startSystemDataTransfer = 26;
        static final int TRANSACTION_stopObservingDevicePresence = 12;
        private final PermissionEnforcer mEnforcer;

        public Stub(PermissionEnforcer enforcer) {
            attachInterface(this, DESCRIPTOR);
            if (enforcer == null) {
                throw new IllegalArgumentException("enforcer cannot be null");
            }
            this.mEnforcer = enforcer;
        }

        @Deprecated
        public Stub() {
            this(PermissionEnforcer.fromContext(ActivityThread.currentActivityThread().getSystemContext()));
        }

        public static ICompanionDeviceManager asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof ICompanionDeviceManager)) {
                return (ICompanionDeviceManager) iin;
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
                    return "setAssociationTag";
                case 38:
                    return "clearAssociationTag";
                case 39:
                    return "getBackupPayload";
                case 40:
                    return "applyRestoredPayload";
                case 41:
                    return "removeBond";
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
                data.enforceInterface(DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    AssociationRequest _arg0 = (AssociationRequest) data.readTypedObject(AssociationRequest.CREATOR);
                    IAssociationRequestCallback _arg1 = IAssociationRequestCallback.Stub.asInterface(data.readStrongBinder());
                    String _arg2 = data.readString();
                    int _arg3 = data.readInt();
                    data.enforceNoDataAvail();
                    associate(_arg0, _arg1, _arg2, _arg3);
                    reply.writeNoException();
                    return true;
                case 2:
                    String _arg02 = data.readString();
                    int _arg12 = data.readInt();
                    data.enforceNoDataAvail();
                    List<AssociationInfo> _result = getAssociations(_arg02, _arg12);
                    reply.writeNoException();
                    reply.writeTypedList(_result, 1);
                    return true;
                case 3:
                    int _arg03 = data.readInt();
                    data.enforceNoDataAvail();
                    List<AssociationInfo> _result2 = getAllAssociationsForUser(_arg03);
                    reply.writeNoException();
                    reply.writeTypedList(_result2, 1);
                    return true;
                case 4:
                    String _arg04 = data.readString();
                    String _arg13 = data.readString();
                    int _arg22 = data.readInt();
                    data.enforceNoDataAvail();
                    legacyDisassociate(_arg04, _arg13, _arg22);
                    reply.writeNoException();
                    return true;
                case 5:
                    int _arg05 = data.readInt();
                    data.enforceNoDataAvail();
                    disassociate(_arg05);
                    reply.writeNoException();
                    return true;
                case 6:
                    ComponentName _arg06 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    data.enforceNoDataAvail();
                    boolean _result3 = hasNotificationAccess(_arg06);
                    reply.writeNoException();
                    reply.writeBoolean(_result3);
                    return true;
                case 7:
                    ComponentName _arg07 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    int _arg14 = data.readInt();
                    data.enforceNoDataAvail();
                    PendingIntent _result4 = requestNotificationAccess(_arg07, _arg14);
                    reply.writeNoException();
                    reply.writeTypedObject(_result4, 1);
                    return true;
                case 8:
                    String _arg08 = data.readString();
                    String _arg15 = data.readString();
                    int _arg23 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result5 = isDeviceAssociatedForWifiConnection(_arg08, _arg15, _arg23);
                    reply.writeNoException();
                    reply.writeBoolean(_result5);
                    return true;
                case 9:
                    String _arg09 = data.readString();
                    String _arg16 = data.readString();
                    int _arg24 = data.readInt();
                    data.enforceNoDataAvail();
                    legacyStartObservingDevicePresence(_arg09, _arg16, _arg24);
                    reply.writeNoException();
                    return true;
                case 10:
                    String _arg010 = data.readString();
                    String _arg17 = data.readString();
                    int _arg25 = data.readInt();
                    data.enforceNoDataAvail();
                    legacyStopObservingDevicePresence(_arg010, _arg17, _arg25);
                    reply.writeNoException();
                    return true;
                case 11:
                    ObservingDevicePresenceRequest _arg011 = (ObservingDevicePresenceRequest) data.readTypedObject(ObservingDevicePresenceRequest.CREATOR);
                    String _arg18 = data.readString();
                    int _arg26 = data.readInt();
                    data.enforceNoDataAvail();
                    startObservingDevicePresence(_arg011, _arg18, _arg26);
                    reply.writeNoException();
                    return true;
                case 12:
                    ObservingDevicePresenceRequest _arg012 = (ObservingDevicePresenceRequest) data.readTypedObject(ObservingDevicePresenceRequest.CREATOR);
                    String _arg19 = data.readString();
                    int _arg27 = data.readInt();
                    data.enforceNoDataAvail();
                    stopObservingDevicePresence(_arg012, _arg19, _arg27);
                    reply.writeNoException();
                    return true;
                case 13:
                    String _arg013 = data.readString();
                    String _arg110 = data.readString();
                    int _arg28 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result6 = canPairWithoutPrompt(_arg013, _arg110, _arg28);
                    reply.writeNoException();
                    reply.writeBoolean(_result6);
                    return true;
                case 14:
                    String _arg014 = data.readString();
                    String _arg111 = data.readString();
                    int _arg29 = data.readInt();
                    byte[] _arg32 = data.createByteArray();
                    data.enforceNoDataAvail();
                    createAssociation(_arg014, _arg111, _arg29, _arg32);
                    reply.writeNoException();
                    return true;
                case 15:
                    IOnAssociationsChangedListener _arg015 = IOnAssociationsChangedListener.Stub.asInterface(data.readStrongBinder());
                    int _arg112 = data.readInt();
                    data.enforceNoDataAvail();
                    addOnAssociationsChangedListener(_arg015, _arg112);
                    reply.writeNoException();
                    return true;
                case 16:
                    IOnAssociationsChangedListener _arg016 = IOnAssociationsChangedListener.Stub.asInterface(data.readStrongBinder());
                    int _arg113 = data.readInt();
                    data.enforceNoDataAvail();
                    removeOnAssociationsChangedListener(_arg016, _arg113);
                    reply.writeNoException();
                    return true;
                case 17:
                    IOnTransportsChangedListener _arg017 = IOnTransportsChangedListener.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    addOnTransportsChangedListener(_arg017);
                    reply.writeNoException();
                    return true;
                case 18:
                    IOnTransportsChangedListener _arg018 = IOnTransportsChangedListener.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    removeOnTransportsChangedListener(_arg018);
                    reply.writeNoException();
                    return true;
                case 19:
                    int _arg019 = data.readInt();
                    byte[] _arg114 = data.createByteArray();
                    int[] _arg210 = data.createIntArray();
                    data.enforceNoDataAvail();
                    sendMessage(_arg019, _arg114, _arg210);
                    reply.writeNoException();
                    return true;
                case 20:
                    int _arg020 = data.readInt();
                    IOnMessageReceivedListener _arg115 = IOnMessageReceivedListener.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    addOnMessageReceivedListener(_arg020, _arg115);
                    reply.writeNoException();
                    return true;
                case 21:
                    int _arg021 = data.readInt();
                    IOnMessageReceivedListener _arg116 = IOnMessageReceivedListener.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    removeOnMessageReceivedListener(_arg021, _arg116);
                    reply.writeNoException();
                    return true;
                case 22:
                    int _arg022 = data.readInt();
                    data.enforceNoDataAvail();
                    notifySelfManagedDeviceAppeared(_arg022);
                    reply.writeNoException();
                    return true;
                case 23:
                    int _arg023 = data.readInt();
                    data.enforceNoDataAvail();
                    notifySelfManagedDeviceDisappeared(_arg023);
                    reply.writeNoException();
                    return true;
                case 24:
                    String _arg024 = data.readString();
                    int _arg117 = data.readInt();
                    int _arg211 = data.readInt();
                    data.enforceNoDataAvail();
                    PendingIntent _result7 = buildPermissionTransferUserConsentIntent(_arg024, _arg117, _arg211);
                    reply.writeNoException();
                    reply.writeTypedObject(_result7, 1);
                    return true;
                case 25:
                    String _arg025 = data.readString();
                    int _arg118 = data.readInt();
                    int _arg212 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result8 = isPermissionTransferUserConsented(_arg025, _arg118, _arg212);
                    reply.writeNoException();
                    reply.writeBoolean(_result8);
                    return true;
                case 26:
                    String _arg026 = data.readString();
                    int _arg119 = data.readInt();
                    int _arg213 = data.readInt();
                    ISystemDataTransferCallback _arg33 = ISystemDataTransferCallback.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    startSystemDataTransfer(_arg026, _arg119, _arg213, _arg33);
                    reply.writeNoException();
                    return true;
                case 27:
                    String _arg027 = data.readString();
                    int _arg120 = data.readInt();
                    int _arg214 = data.readInt();
                    ParcelFileDescriptor _arg34 = (ParcelFileDescriptor) data.readTypedObject(ParcelFileDescriptor.CREATOR);
                    data.enforceNoDataAvail();
                    attachSystemDataTransport(_arg027, _arg120, _arg214, _arg34);
                    reply.writeNoException();
                    return true;
                case 28:
                    String _arg028 = data.readString();
                    int _arg121 = data.readInt();
                    int _arg215 = data.readInt();
                    data.enforceNoDataAvail();
                    detachSystemDataTransport(_arg028, _arg121, _arg215);
                    reply.writeNoException();
                    return true;
                case 29:
                    String _arg029 = data.readString();
                    int _arg122 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result9 = isCompanionApplicationBound(_arg029, _arg122);
                    reply.writeNoException();
                    reply.writeBoolean(_result9);
                    return true;
                case 30:
                    String _arg030 = data.readString();
                    int _arg123 = data.readInt();
                    data.enforceNoDataAvail();
                    PendingIntent _result10 = buildAssociationCancellationIntent(_arg030, _arg123);
                    reply.writeNoException();
                    reply.writeTypedObject(_result10, 1);
                    return true;
                case 31:
                    int _arg031 = data.readInt();
                    int _arg124 = data.readInt();
                    data.enforceNoDataAvail();
                    enableSystemDataSync(_arg031, _arg124);
                    reply.writeNoException();
                    return true;
                case 32:
                    int _arg032 = data.readInt();
                    int _arg125 = data.readInt();
                    data.enforceNoDataAvail();
                    disableSystemDataSync(_arg032, _arg125);
                    reply.writeNoException();
                    return true;
                case 33:
                    int _arg033 = data.readInt();
                    data.enforceNoDataAvail();
                    enablePermissionsSync(_arg033);
                    reply.writeNoException();
                    return true;
                case 34:
                    int _arg034 = data.readInt();
                    data.enforceNoDataAvail();
                    disablePermissionsSync(_arg034);
                    reply.writeNoException();
                    return true;
                case 35:
                    int _arg035 = data.readInt();
                    data.enforceNoDataAvail();
                    PermissionSyncRequest _result11 = getPermissionSyncRequest(_arg035);
                    reply.writeNoException();
                    reply.writeTypedObject(_result11, 1);
                    return true;
                case 36:
                    boolean _arg036 = data.readBoolean();
                    data.enforceNoDataAvail();
                    enableSecureTransport(_arg036);
                    reply.writeNoException();
                    return true;
                case 37:
                    int _arg037 = data.readInt();
                    String _arg126 = data.readString();
                    data.enforceNoDataAvail();
                    setAssociationTag(_arg037, _arg126);
                    reply.writeNoException();
                    return true;
                case 38:
                    int _arg038 = data.readInt();
                    data.enforceNoDataAvail();
                    clearAssociationTag(_arg038);
                    reply.writeNoException();
                    return true;
                case 39:
                    int _arg039 = data.readInt();
                    data.enforceNoDataAvail();
                    byte[] _result12 = getBackupPayload(_arg039);
                    reply.writeNoException();
                    reply.writeByteArray(_result12);
                    return true;
                case 40:
                    byte[] _arg040 = data.createByteArray();
                    int _arg127 = data.readInt();
                    data.enforceNoDataAvail();
                    applyRestoredPayload(_arg040, _arg127);
                    reply.writeNoException();
                    return true;
                case 41:
                    int _arg041 = data.readInt();
                    String _arg128 = data.readString();
                    int _arg216 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result13 = removeBond(_arg041, _arg128, _arg216);
                    reply.writeNoException();
                    reply.writeBoolean(_result13);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements ICompanionDeviceManager {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // android.companion.ICompanionDeviceManager
            public void associate(AssociationRequest request, IAssociationRequestCallback callback, String callingPackage, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(request, 0);
                    _data.writeStrongInterface(callback);
                    _data.writeString(callingPackage);
                    _data.writeInt(userId);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.companion.ICompanionDeviceManager
            public List<AssociationInfo> getAssociations(String callingPackage, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    _data.writeInt(userId);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                    List<AssociationInfo> _result = _reply.createTypedArrayList(AssociationInfo.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.companion.ICompanionDeviceManager
            public List<AssociationInfo> getAllAssociationsForUser(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                    List<AssociationInfo> _result = _reply.createTypedArrayList(AssociationInfo.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.companion.ICompanionDeviceManager
            public void legacyDisassociate(String deviceMacAddress, String callingPackage, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(deviceMacAddress);
                    _data.writeString(callingPackage);
                    _data.writeInt(userId);
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.companion.ICompanionDeviceManager
            public void disassociate(int associationId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(associationId);
                    this.mRemote.transact(5, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.companion.ICompanionDeviceManager
            public boolean hasNotificationAccess(ComponentName component) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(component, 0);
                    this.mRemote.transact(6, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.companion.ICompanionDeviceManager
            public PendingIntent requestNotificationAccess(ComponentName component, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(component, 0);
                    _data.writeInt(userId);
                    this.mRemote.transact(7, _data, _reply, 0);
                    _reply.readException();
                    PendingIntent _result = (PendingIntent) _reply.readTypedObject(PendingIntent.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.companion.ICompanionDeviceManager
            public boolean isDeviceAssociatedForWifiConnection(String packageName, String macAddress, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeString(macAddress);
                    _data.writeInt(userId);
                    this.mRemote.transact(8, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.companion.ICompanionDeviceManager
            public void legacyStartObservingDevicePresence(String deviceAddress, String callingPackage, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(deviceAddress);
                    _data.writeString(callingPackage);
                    _data.writeInt(userId);
                    this.mRemote.transact(9, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.companion.ICompanionDeviceManager
            public void legacyStopObservingDevicePresence(String deviceAddress, String callingPackage, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(deviceAddress);
                    _data.writeString(callingPackage);
                    _data.writeInt(userId);
                    this.mRemote.transact(10, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.companion.ICompanionDeviceManager
            public void startObservingDevicePresence(ObservingDevicePresenceRequest request, String packageName, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(request, 0);
                    _data.writeString(packageName);
                    _data.writeInt(userId);
                    this.mRemote.transact(11, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.companion.ICompanionDeviceManager
            public void stopObservingDevicePresence(ObservingDevicePresenceRequest request, String packageName, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(request, 0);
                    _data.writeString(packageName);
                    _data.writeInt(userId);
                    this.mRemote.transact(12, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.companion.ICompanionDeviceManager
            public boolean canPairWithoutPrompt(String packageName, String deviceMacAddress, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeString(deviceMacAddress);
                    _data.writeInt(userId);
                    this.mRemote.transact(13, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.companion.ICompanionDeviceManager
            public void createAssociation(String packageName, String macAddress, int userId, byte[] certificate) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeString(macAddress);
                    _data.writeInt(userId);
                    _data.writeByteArray(certificate);
                    this.mRemote.transact(14, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.companion.ICompanionDeviceManager
            public void addOnAssociationsChangedListener(IOnAssociationsChangedListener listener, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(listener);
                    _data.writeInt(userId);
                    this.mRemote.transact(15, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.companion.ICompanionDeviceManager
            public void removeOnAssociationsChangedListener(IOnAssociationsChangedListener listener, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(listener);
                    _data.writeInt(userId);
                    this.mRemote.transact(16, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.companion.ICompanionDeviceManager
            public void addOnTransportsChangedListener(IOnTransportsChangedListener listener) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(listener);
                    this.mRemote.transact(17, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.companion.ICompanionDeviceManager
            public void removeOnTransportsChangedListener(IOnTransportsChangedListener listener) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(listener);
                    this.mRemote.transact(18, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.companion.ICompanionDeviceManager
            public void sendMessage(int messageType, byte[] data, int[] associationIds) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(messageType);
                    _data.writeByteArray(data);
                    _data.writeIntArray(associationIds);
                    this.mRemote.transact(19, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.companion.ICompanionDeviceManager
            public void addOnMessageReceivedListener(int messageType, IOnMessageReceivedListener listener) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(messageType);
                    _data.writeStrongInterface(listener);
                    this.mRemote.transact(20, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.companion.ICompanionDeviceManager
            public void removeOnMessageReceivedListener(int messageType, IOnMessageReceivedListener listener) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(messageType);
                    _data.writeStrongInterface(listener);
                    this.mRemote.transact(21, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.companion.ICompanionDeviceManager
            public void notifySelfManagedDeviceAppeared(int associationId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(associationId);
                    this.mRemote.transact(22, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.companion.ICompanionDeviceManager
            public void notifySelfManagedDeviceDisappeared(int associationId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(associationId);
                    this.mRemote.transact(23, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.companion.ICompanionDeviceManager
            public PendingIntent buildPermissionTransferUserConsentIntent(String callingPackage, int userId, int associationId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    _data.writeInt(userId);
                    _data.writeInt(associationId);
                    this.mRemote.transact(24, _data, _reply, 0);
                    _reply.readException();
                    PendingIntent _result = (PendingIntent) _reply.readTypedObject(PendingIntent.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.companion.ICompanionDeviceManager
            public boolean isPermissionTransferUserConsented(String callingPackage, int userId, int associationId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    _data.writeInt(userId);
                    _data.writeInt(associationId);
                    this.mRemote.transact(25, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.companion.ICompanionDeviceManager
            public void startSystemDataTransfer(String packageName, int userId, int associationId, ISystemDataTransferCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(userId);
                    _data.writeInt(associationId);
                    _data.writeStrongInterface(callback);
                    this.mRemote.transact(26, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.companion.ICompanionDeviceManager
            public void attachSystemDataTransport(String packageName, int userId, int associationId, ParcelFileDescriptor fd) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(userId);
                    _data.writeInt(associationId);
                    _data.writeTypedObject(fd, 0);
                    this.mRemote.transact(27, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.companion.ICompanionDeviceManager
            public void detachSystemDataTransport(String packageName, int userId, int associationId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(userId);
                    _data.writeInt(associationId);
                    this.mRemote.transact(28, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.companion.ICompanionDeviceManager
            public boolean isCompanionApplicationBound(String packageName, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(userId);
                    this.mRemote.transact(29, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.companion.ICompanionDeviceManager
            public PendingIntent buildAssociationCancellationIntent(String callingPackage, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    _data.writeInt(userId);
                    this.mRemote.transact(30, _data, _reply, 0);
                    _reply.readException();
                    PendingIntent _result = (PendingIntent) _reply.readTypedObject(PendingIntent.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.companion.ICompanionDeviceManager
            public void enableSystemDataSync(int associationId, int flags) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(associationId);
                    _data.writeInt(flags);
                    this.mRemote.transact(31, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.companion.ICompanionDeviceManager
            public void disableSystemDataSync(int associationId, int flags) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(associationId);
                    _data.writeInt(flags);
                    this.mRemote.transact(32, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.companion.ICompanionDeviceManager
            public void enablePermissionsSync(int associationId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(associationId);
                    this.mRemote.transact(33, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.companion.ICompanionDeviceManager
            public void disablePermissionsSync(int associationId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(associationId);
                    this.mRemote.transact(34, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.companion.ICompanionDeviceManager
            public PermissionSyncRequest getPermissionSyncRequest(int associationId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(associationId);
                    this.mRemote.transact(35, _data, _reply, 0);
                    _reply.readException();
                    PermissionSyncRequest _result = (PermissionSyncRequest) _reply.readTypedObject(PermissionSyncRequest.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.companion.ICompanionDeviceManager
            public void enableSecureTransport(boolean enabled) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBoolean(enabled);
                    this.mRemote.transact(36, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.companion.ICompanionDeviceManager
            public void setAssociationTag(int associationId, String tag) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(associationId);
                    _data.writeString(tag);
                    this.mRemote.transact(37, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.companion.ICompanionDeviceManager
            public void clearAssociationTag(int associationId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(associationId);
                    this.mRemote.transact(38, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.companion.ICompanionDeviceManager
            public byte[] getBackupPayload(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    this.mRemote.transact(39, _data, _reply, 0);
                    _reply.readException();
                    byte[] _result = _reply.createByteArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.companion.ICompanionDeviceManager
            public void applyRestoredPayload(byte[] payload, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeByteArray(payload);
                    _data.writeInt(userId);
                    this.mRemote.transact(40, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.companion.ICompanionDeviceManager
            public boolean removeBond(int associationId, String packageName, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(associationId);
                    _data.writeString(packageName);
                    _data.writeInt(userId);
                    this.mRemote.transact(41, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
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

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 40;
        }
    }
}
