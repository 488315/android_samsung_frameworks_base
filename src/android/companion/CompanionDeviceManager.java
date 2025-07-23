package android.companion;

import android.annotation.SystemApi;
import android.app.ActivityManagerInternal;
import android.app.ActivityOptions;
import android.app.PendingIntent;
import android.companion.CompanionDeviceManager;
import android.companion.IAssociationRequestCallback;
import android.companion.IOnAssociationsChangedListener;
import android.companion.IOnMessageReceivedListener;
import android.companion.IOnTransportsChangedListener;
import android.companion.ISystemDataTransferCallback;
import android.companion.datatransfer.PermissionSyncRequest;
import android.content.ComponentName;
import android.content.Context;
import android.content.IntentSender;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.net.MacAddress;
import android.os.Binder;
import android.os.Handler;
import android.os.OutcomeReceiver;
import android.os.ParcelFileDescriptor;
import android.os.Process;
import android.os.RemoteException;
import android.os.UserHandle;
import android.util.ExceptionUtils;
import android.util.Log;
import android.util.SparseArray;
import com.android.internal.hidden_from_bootclasspath.android.companion.Flags;
import com.android.internal.util.CollectionUtils;
import com.android.server.LocalServices;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import libcore.io.IoUtils;

/* loaded from: classes.dex */
public final class CompanionDeviceManager {
    public static final String EXTRA_ASSOCIATION = "android.companion.extra.ASSOCIATION";

    @Deprecated
    public static final String EXTRA_DEVICE = "android.companion.extra.DEVICE";
    public static final int FLAG_CALL_METADATA = 1;
    private static final int ICON_TARGET_SIZE = 24;
    public static final int MESSAGE_ONEWAY_FROM_WEARABLE = 1131446919;
    public static final int MESSAGE_ONEWAY_PING = 1132491640;
    public static final int MESSAGE_ONEWAY_TO_WEARABLE = 1132755335;
    public static final int MESSAGE_REQUEST_CONTEXT_SYNC = 1667729539;
    public static final int MESSAGE_REQUEST_PERMISSION_RESTORE = 1669491075;
    public static final int MESSAGE_REQUEST_PING = 1669362552;
    public static final int MESSAGE_REQUEST_REMOTE_AUTHENTICATION = 1669494629;
    public static final String REASON_CANCELED = "canceled";
    public static final String REASON_DISCOVERY_TIMEOUT = "discovery_timeout";
    public static final String REASON_INTERNAL_ERROR = "internal_error";
    public static final String REASON_USER_REJECTED = "user_rejected";
    public static final int RESULT_CANCELED = 0;
    public static final int RESULT_DISCOVERY_TIMEOUT = 2;
    public static final int RESULT_INTERNAL_ERROR = 3;
    public static final int RESULT_OK = -1;
    public static final int RESULT_SECURITY_ERROR = 4;
    public static final int RESULT_USER_REJECTED = 1;
    private static final String TAG = "CDM_CompanionDeviceManager";
    public static final int TRANSPORT_FLAG_EXTEND_PATCH_DIFF = 1;
    private final Context mContext;
    private final ICompanionDeviceManager mService;
    private final ArrayList<OnAssociationsChangedListenerProxy> mListeners = new ArrayList<>();
    private final ArrayList<OnTransportsChangedListenerProxy> mTransportsChangedListeners = new ArrayList<>();
    private final SparseArray<Transport> mTransports = new SparseArray<>();

    @Retention(RetentionPolicy.SOURCE)
    public @interface DataSyncTypes {
    }

    @SystemApi
    public interface OnAssociationsChangedListener {
        void onAssociationsChanged(List<AssociationInfo> list);
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ResultCode {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface TransportFlags {
    }

    public static abstract class Callback {
        public void onAssociationCreated(AssociationInfo associationInfo) {
        }

        @Deprecated
        public void onDeviceFound(IntentSender intentSender) {
        }

        public void onFailure(int i, CharSequence charSequence) {
        }

        public abstract void onFailure(CharSequence charSequence);

        public void onAssociationPending(IntentSender intentSender) {
            onDeviceFound(intentSender);
        }
    }

    public CompanionDeviceManager(ICompanionDeviceManager iCompanionDeviceManager, Context context) {
        this.mService = iCompanionDeviceManager;
        this.mContext = context;
    }

    public void associate(AssociationRequest associationRequest, Callback callback, Handler handler) {
        Icon deviceIcon;
        if (this.mService == null) {
            Log.w(TAG, "CompanionDeviceManager service is not available.");
            return;
        }
        Objects.requireNonNull(associationRequest, "Request cannot be null");
        Objects.requireNonNull(callback, "Callback cannot be null");
        Handler mainIfNull = Handler.mainIfNull(handler);
        if (Flags.associationDeviceIcon() && (deviceIcon = associationRequest.getDeviceIcon()) != null) {
            associationRequest.setDeviceIcon(scaleIcon(deviceIcon, this.mContext));
        }
        try {
            this.mService.associate(associationRequest, new AssociationRequestCallbackProxy(mainIfNull, callback), this.mContext.getOpPackageName(), this.mContext.getUserId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void associate(AssociationRequest associationRequest, Executor executor, Callback callback) {
        Icon deviceIcon;
        if (this.mService == null) {
            Log.w(TAG, "CompanionDeviceManager service is not available.");
            return;
        }
        Objects.requireNonNull(associationRequest, "Request cannot be null");
        Objects.requireNonNull(executor, "Executor cannot be null");
        Objects.requireNonNull(callback, "Callback cannot be null");
        if (Flags.associationDeviceIcon() && (deviceIcon = associationRequest.getDeviceIcon()) != null) {
            associationRequest.setDeviceIcon(scaleIcon(deviceIcon, this.mContext));
        }
        try {
            this.mService.associate(associationRequest, new AssociationRequestCallbackProxy(executor, callback), this.mContext.getOpPackageName(), this.mContext.getUserId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public IntentSender buildAssociationCancellationIntent() {
        ICompanionDeviceManager iCompanionDeviceManager = this.mService;
        if (iCompanionDeviceManager == null) {
            Log.w(TAG, "CompanionDeviceManager service is not available.");
            return null;
        }
        try {
            return iCompanionDeviceManager.buildAssociationCancellationIntent(this.mContext.getOpPackageName(), this.mContext.getUserId()).getIntentSender();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void enableSystemDataSyncForTypes(int i, int i2) {
        ICompanionDeviceManager iCompanionDeviceManager = this.mService;
        if (iCompanionDeviceManager == null) {
            Log.w(TAG, "CompanionDeviceManager service is not available.");
            return;
        }
        try {
            iCompanionDeviceManager.enableSystemDataSync(i, i2);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void disableSystemDataSyncForTypes(int i, int i2) {
        ICompanionDeviceManager iCompanionDeviceManager = this.mService;
        if (iCompanionDeviceManager == null) {
            Log.w(TAG, "CompanionDeviceManager service is not available.");
            return;
        }
        try {
            iCompanionDeviceManager.disableSystemDataSync(i, i2);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void enablePermissionsSync(int i) {
        ICompanionDeviceManager iCompanionDeviceManager = this.mService;
        if (iCompanionDeviceManager == null) {
            Log.w(TAG, "CompanionDeviceManager service is not available.");
            return;
        }
        try {
            iCompanionDeviceManager.enablePermissionsSync(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void disablePermissionsSync(int i) {
        ICompanionDeviceManager iCompanionDeviceManager = this.mService;
        if (iCompanionDeviceManager == null) {
            Log.w(TAG, "CompanionDeviceManager service is not available.");
            return;
        }
        try {
            iCompanionDeviceManager.disablePermissionsSync(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public PermissionSyncRequest getPermissionSyncRequest(int i) {
        ICompanionDeviceManager iCompanionDeviceManager = this.mService;
        if (iCompanionDeviceManager == null) {
            Log.w(TAG, "CompanionDeviceManager service is not available.");
            return null;
        }
        try {
            return iCompanionDeviceManager.getPermissionSyncRequest(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Deprecated
    public List<String> getAssociations() {
        return CollectionUtils.mapNotNull(getMyAssociations(), new Function() { // from class: android.companion.CompanionDeviceManager$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return CompanionDeviceManager.lambda$getAssociations$0((AssociationInfo) obj);
            }
        });
    }

    static /* synthetic */ String lambda$getAssociations$0(AssociationInfo associationInfo) {
        if (associationInfo.isSelfManaged()) {
            return null;
        }
        return associationInfo.getDeviceMacAddressAsString();
    }

    public List<AssociationInfo> getMyAssociations() {
        ICompanionDeviceManager iCompanionDeviceManager = this.mService;
        if (iCompanionDeviceManager == null) {
            Log.w(TAG, "CompanionDeviceManager service is not available.");
            return Collections.EMPTY_LIST;
        }
        try {
            return iCompanionDeviceManager.getAssociations(this.mContext.getOpPackageName(), this.mContext.getUserId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Deprecated
    public void disassociate(String str) {
        ICompanionDeviceManager iCompanionDeviceManager = this.mService;
        if (iCompanionDeviceManager == null) {
            Log.w(TAG, "CompanionDeviceManager service is not available.");
            return;
        }
        try {
            iCompanionDeviceManager.legacyDisassociate(str, this.mContext.getOpPackageName(), this.mContext.getUserId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void disassociate(int i) {
        ICompanionDeviceManager iCompanionDeviceManager = this.mService;
        if (iCompanionDeviceManager == null) {
            Log.w(TAG, "CompanionDeviceManager service is not available.");
            return;
        }
        try {
            iCompanionDeviceManager.disassociate(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void requestNotificationAccess(ComponentName componentName) {
        ICompanionDeviceManager iCompanionDeviceManager = this.mService;
        if (iCompanionDeviceManager == null) {
            Log.w(TAG, "CompanionDeviceManager service is not available.");
            return;
        }
        try {
            PendingIntent requestNotificationAccess = iCompanionDeviceManager.requestNotificationAccess(componentName, this.mContext.getUserId());
            if (requestNotificationAccess == null) {
                return;
            }
            this.mContext.startIntentSender(requestNotificationAccess.getIntentSender(), null, 0, 0, 0, ActivityOptions.makeBasic().setPendingIntentBackgroundActivityStartMode(1).toBundle());
        } catch (IntentSender.SendIntentException e) {
            throw new RuntimeException(e);
        } catch (RemoteException e2) {
            throw e2.rethrowFromSystemServer();
        }
    }

    @Deprecated
    public boolean hasNotificationAccess(ComponentName componentName) {
        ICompanionDeviceManager iCompanionDeviceManager = this.mService;
        if (iCompanionDeviceManager == null) {
            Log.w(TAG, "CompanionDeviceManager service is not available.");
            return false;
        }
        try {
            return iCompanionDeviceManager.hasNotificationAccess(componentName);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public boolean isDeviceAssociatedForWifiConnection(String str, MacAddress macAddress, UserHandle userHandle) {
        if (this.mService == null) {
            Log.w(TAG, "CompanionDeviceManager service is not available.");
            return false;
        }
        Objects.requireNonNull(str, "package name cannot be null");
        Objects.requireNonNull(macAddress, "mac address cannot be null");
        Objects.requireNonNull(userHandle, "user cannot be null");
        try {
            return this.mService.isDeviceAssociatedForWifiConnection(str, macAddress.toString(), userHandle.getIdentifier());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public List<AssociationInfo> getAllAssociations() {
        return getAllAssociations(this.mContext.getUserId());
    }

    public List<AssociationInfo> getAllAssociations(int i) {
        ICompanionDeviceManager iCompanionDeviceManager = this.mService;
        if (iCompanionDeviceManager == null) {
            Log.w(TAG, "CompanionDeviceManager service is not available.");
            return Collections.EMPTY_LIST;
        }
        try {
            return iCompanionDeviceManager.getAllAssociationsForUser(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void addOnAssociationsChangedListener(Executor executor, OnAssociationsChangedListener onAssociationsChangedListener) {
        addOnAssociationsChangedListener(executor, onAssociationsChangedListener, this.mContext.getUserId());
    }

    public void addOnAssociationsChangedListener(Executor executor, OnAssociationsChangedListener onAssociationsChangedListener, int i) {
        if (this.mService == null) {
            Log.w(TAG, "CompanionDeviceManager service is not available.");
            return;
        }
        synchronized (this.mListeners) {
            OnAssociationsChangedListenerProxy onAssociationsChangedListenerProxy = new OnAssociationsChangedListenerProxy(executor, onAssociationsChangedListener);
            try {
                this.mService.addOnAssociationsChangedListener(onAssociationsChangedListenerProxy, i);
                this.mListeners.add(onAssociationsChangedListenerProxy);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    @SystemApi
    public void removeOnAssociationsChangedListener(OnAssociationsChangedListener onAssociationsChangedListener) {
        if (this.mService == null) {
            Log.w(TAG, "CompanionDeviceManager service is not available.");
            return;
        }
        synchronized (this.mListeners) {
            Iterator<OnAssociationsChangedListenerProxy> it = this.mListeners.iterator();
            while (it.hasNext()) {
                OnAssociationsChangedListenerProxy next = it.next();
                if (next.mListener == onAssociationsChangedListener) {
                    try {
                        this.mService.removeOnAssociationsChangedListener(next, this.mContext.getUserId());
                        it.remove();
                    } catch (RemoteException e) {
                        throw e.rethrowFromSystemServer();
                    }
                }
            }
        }
    }

    public void addOnTransportsChangedListener(Executor executor, Consumer<List<AssociationInfo>> consumer) {
        if (this.mService == null) {
            Log.w(TAG, "CompanionDeviceManager service is not available.");
            return;
        }
        synchronized (this.mTransportsChangedListeners) {
            OnTransportsChangedListenerProxy onTransportsChangedListenerProxy = new OnTransportsChangedListenerProxy(executor, consumer);
            try {
                this.mService.addOnTransportsChangedListener(onTransportsChangedListenerProxy);
                this.mTransportsChangedListeners.add(onTransportsChangedListenerProxy);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void removeOnTransportsChangedListener(Consumer<List<AssociationInfo>> consumer) {
        if (this.mService == null) {
            Log.w(TAG, "CompanionDeviceManager service is not available.");
            return;
        }
        synchronized (this.mTransportsChangedListeners) {
            Iterator<OnTransportsChangedListenerProxy> it = this.mTransportsChangedListeners.iterator();
            while (it.hasNext()) {
                OnTransportsChangedListenerProxy next = it.next();
                if (next.mListener == consumer) {
                    try {
                        this.mService.removeOnTransportsChangedListener(next);
                        it.remove();
                    } catch (RemoteException e) {
                        throw e.rethrowFromSystemServer();
                    }
                }
            }
        }
    }

    public void sendMessage(int i, byte[] bArr, int[] iArr) {
        ICompanionDeviceManager iCompanionDeviceManager = this.mService;
        if (iCompanionDeviceManager == null) {
            Log.w(TAG, "CompanionDeviceManager service is not available.");
            return;
        }
        try {
            iCompanionDeviceManager.sendMessage(i, bArr, iArr);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void addOnMessageReceivedListener(Executor executor, int i, BiConsumer<Integer, byte[]> biConsumer) {
        if (this.mService == null) {
            Log.w(TAG, "CompanionDeviceManager service is not available.");
            return;
        }
        try {
            this.mService.addOnMessageReceivedListener(i, new OnMessageReceivedListenerProxy(executor, biConsumer));
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void removeOnMessageReceivedListener(int i, BiConsumer<Integer, byte[]> biConsumer) {
        if (this.mService == null) {
            Log.w(TAG, "CompanionDeviceManager service is not available.");
            return;
        }
        try {
            this.mService.removeOnMessageReceivedListener(i, new OnMessageReceivedListenerProxy(null, biConsumer));
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public boolean canPairWithoutPrompt(String str, String str2, UserHandle userHandle) {
        if (this.mService == null) {
            Log.w(TAG, "CompanionDeviceManager service is not available.");
            return false;
        }
        Objects.requireNonNull(str, "package name cannot be null");
        Objects.requireNonNull(str2, "device mac address cannot be null");
        Objects.requireNonNull(userHandle, "user handle cannot be null");
        try {
            return this.mService.canPairWithoutPrompt(str, str2, userHandle.getIdentifier());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean removeBond(int i) {
        ICompanionDeviceManager iCompanionDeviceManager = this.mService;
        if (iCompanionDeviceManager == null) {
            Log.w(TAG, "CompanionDeviceManager service is not available.");
            return false;
        }
        try {
            return iCompanionDeviceManager.removeBond(i, this.mContext.getOpPackageName(), this.mContext.getUserId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Deprecated
    public void startObservingDevicePresence(String str) throws DeviceNotAssociatedException {
        if (this.mService == null) {
            Log.w(TAG, "CompanionDeviceManager service is not available.");
            return;
        }
        Objects.requireNonNull(str, "address cannot be null");
        try {
            this.mService.legacyStartObservingDevicePresence(str, this.mContext.getOpPackageName(), this.mContext.getUserId());
            int callingUid = Binder.getCallingUid();
            int callingPid = Binder.getCallingPid();
            ActivityManagerInternal activityManagerInternal = (ActivityManagerInternal) LocalServices.getService(ActivityManagerInternal.class);
            if (activityManagerInternal != null) {
                activityManagerInternal.logFgsApiBegin(9, callingUid, callingPid);
            }
        } catch (RemoteException e) {
            ExceptionUtils.propagateIfInstanceOf(e.getCause(), DeviceNotAssociatedException.class);
            throw e.rethrowFromSystemServer();
        }
    }

    @Deprecated
    public void stopObservingDevicePresence(String str) throws DeviceNotAssociatedException {
        if (this.mService == null) {
            Log.w(TAG, "CompanionDeviceManager service is not available.");
            return;
        }
        Objects.requireNonNull(str, "address cannot be null");
        try {
            this.mService.legacyStopObservingDevicePresence(str, this.mContext.getPackageName(), this.mContext.getUserId());
        } catch (RemoteException e) {
            ExceptionUtils.propagateIfInstanceOf(e.getCause(), DeviceNotAssociatedException.class);
        }
        int callingUid = Binder.getCallingUid();
        int callingPid = Binder.getCallingPid();
        ActivityManagerInternal activityManagerInternal = (ActivityManagerInternal) LocalServices.getService(ActivityManagerInternal.class);
        if (activityManagerInternal != null) {
            activityManagerInternal.logFgsApiEnd(9, callingUid, callingPid);
        }
    }

    public void startObservingDevicePresence(ObservingDevicePresenceRequest observingDevicePresenceRequest) {
        if (this.mService == null) {
            Log.w(TAG, "CompanionDeviceManager service is not available.");
            return;
        }
        Objects.requireNonNull(observingDevicePresenceRequest, "request cannot be null");
        try {
            this.mService.startObservingDevicePresence(observingDevicePresenceRequest, this.mContext.getOpPackageName(), this.mContext.getUserId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void stopObservingDevicePresence(ObservingDevicePresenceRequest observingDevicePresenceRequest) {
        if (this.mService == null) {
            Log.w(TAG, "CompanionDeviceManager service is not available.");
            return;
        }
        Objects.requireNonNull(observingDevicePresenceRequest, "request cannot be null");
        try {
            this.mService.stopObservingDevicePresence(observingDevicePresenceRequest, this.mContext.getOpPackageName(), this.mContext.getUserId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Deprecated
    public void dispatchMessage(int i, int i2, byte[] bArr) throws DeviceNotAssociatedException {
        Log.w(TAG, "dispatchMessage replaced by attachSystemDataTransport");
    }

    public void attachSystemDataTransport(int i, InputStream inputStream, OutputStream outputStream) throws DeviceNotAssociatedException {
        if (this.mService == null) {
            Log.w(TAG, "CompanionDeviceManager service is not available.");
            return;
        }
        synchronized (this.mTransports) {
            if (this.mTransports.contains(i)) {
                detachSystemDataTransport(i);
            }
            try {
                Transport transport = new Transport(i, inputStream, outputStream, 0);
                this.mTransports.put(i, transport);
                transport.start();
            } catch (IOException e) {
                throw new RuntimeException("Failed to attach transport", e);
            }
        }
    }

    public void attachSystemDataTransport(int i, InputStream inputStream, OutputStream outputStream, int i2) throws DeviceNotAssociatedException {
        if (this.mService == null) {
            Log.w(TAG, "CompanionDeviceManager service is not available.");
            return;
        }
        synchronized (this.mTransports) {
            if (this.mTransports.contains(i)) {
                detachSystemDataTransport(i);
            }
            try {
                Transport transport = new Transport(i, inputStream, outputStream, i2);
                this.mTransports.put(i, transport);
                transport.start();
            } catch (IOException e) {
                throw new RuntimeException("Failed to attach transport", e);
            }
        }
    }

    public void detachSystemDataTransport(int i) throws DeviceNotAssociatedException {
        if (this.mService == null) {
            Log.w(TAG, "CompanionDeviceManager service is not available.");
            return;
        }
        synchronized (this.mTransports) {
            Transport transport = this.mTransports.get(i);
            if (transport != null) {
                this.mTransports.delete(i);
                transport.stop();
            }
        }
    }

    @SystemApi
    public void associate(String str, MacAddress macAddress, byte[] bArr) {
        if (this.mService == null) {
            Log.w(TAG, "CompanionDeviceManager service is not available.");
            return;
        }
        Objects.requireNonNull(str, "package name cannot be null");
        Objects.requireNonNull(macAddress, "mac address cannot be null");
        try {
            this.mService.createAssociation(str, macAddress.toString(), Process.myUserHandle().getIdentifier(), bArr);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void notifyDeviceAppeared(int i) {
        ICompanionDeviceManager iCompanionDeviceManager = this.mService;
        if (iCompanionDeviceManager == null) {
            Log.w(TAG, "CompanionDeviceManager service is not available.");
            return;
        }
        try {
            iCompanionDeviceManager.notifySelfManagedDeviceAppeared(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void notifyDeviceDisappeared(int i) {
        ICompanionDeviceManager iCompanionDeviceManager = this.mService;
        if (iCompanionDeviceManager == null) {
            Log.w(TAG, "CompanionDeviceManager service is not available.");
            return;
        }
        try {
            iCompanionDeviceManager.notifySelfManagedDeviceDisappeared(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public IntentSender buildPermissionTransferUserConsentIntent(int i) throws DeviceNotAssociatedException {
        ICompanionDeviceManager iCompanionDeviceManager = this.mService;
        if (iCompanionDeviceManager == null) {
            Log.w(TAG, "CompanionDeviceManager service is not available.");
            return null;
        }
        try {
            PendingIntent buildPermissionTransferUserConsentIntent = iCompanionDeviceManager.buildPermissionTransferUserConsentIntent(this.mContext.getOpPackageName(), this.mContext.getUserId(), i);
            if (buildPermissionTransferUserConsentIntent == null) {
                return null;
            }
            return buildPermissionTransferUserConsentIntent.getIntentSender();
        } catch (RemoteException e) {
            ExceptionUtils.propagateIfInstanceOf(e.getCause(), DeviceNotAssociatedException.class);
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isPermissionTransferUserConsented(int i) {
        ICompanionDeviceManager iCompanionDeviceManager = this.mService;
        if (iCompanionDeviceManager == null) {
            Log.w(TAG, "CompanionDeviceManager service is not available.");
            return false;
        }
        try {
            return iCompanionDeviceManager.isPermissionTransferUserConsented(this.mContext.getOpPackageName(), this.mContext.getUserId(), i);
        } catch (RemoteException e) {
            ExceptionUtils.propagateIfInstanceOf(e.getCause(), DeviceNotAssociatedException.class);
            throw e.rethrowFromSystemServer();
        }
    }

    @Deprecated
    public void startSystemDataTransfer(int i) throws DeviceNotAssociatedException {
        ICompanionDeviceManager iCompanionDeviceManager = this.mService;
        if (iCompanionDeviceManager == null) {
            Log.w(TAG, "CompanionDeviceManager service is not available.");
            return;
        }
        try {
            iCompanionDeviceManager.startSystemDataTransfer(this.mContext.getOpPackageName(), this.mContext.getUserId(), i, null);
        } catch (RemoteException e) {
            ExceptionUtils.propagateIfInstanceOf(e.getCause(), DeviceNotAssociatedException.class);
            throw e.rethrowFromSystemServer();
        }
    }

    public void startSystemDataTransfer(int i, Executor executor, OutcomeReceiver<Void, CompanionException> outcomeReceiver) throws DeviceNotAssociatedException {
        ICompanionDeviceManager iCompanionDeviceManager = this.mService;
        if (iCompanionDeviceManager == null) {
            Log.w(TAG, "CompanionDeviceManager service is not available.");
            return;
        }
        try {
            iCompanionDeviceManager.startSystemDataTransfer(this.mContext.getOpPackageName(), this.mContext.getUserId(), i, new SystemDataTransferCallbackProxy(executor, outcomeReceiver));
        } catch (RemoteException e) {
            ExceptionUtils.propagateIfInstanceOf(e.getCause(), DeviceNotAssociatedException.class);
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isCompanionApplicationBound() {
        ICompanionDeviceManager iCompanionDeviceManager = this.mService;
        if (iCompanionDeviceManager == null) {
            Log.w(TAG, "CompanionDeviceManager service is not available.");
            return false;
        }
        try {
            return iCompanionDeviceManager.isCompanionApplicationBound(this.mContext.getOpPackageName(), this.mContext.getUserId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void enableSecureTransport(boolean z) {
        ICompanionDeviceManager iCompanionDeviceManager = this.mService;
        if (iCompanionDeviceManager == null) {
            Log.w(TAG, "CompanionDeviceManager service is not available.");
            return;
        }
        try {
            iCompanionDeviceManager.enableSecureTransport(z);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setDeviceId(int i, DeviceId deviceId) {
        ICompanionDeviceManager iCompanionDeviceManager = this.mService;
        if (iCompanionDeviceManager == null) {
            Log.w(TAG, "CompanionDeviceManager service is not available.");
            return;
        }
        try {
            iCompanionDeviceManager.setDeviceId(i, deviceId);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class AssociationRequestCallbackProxy extends IAssociationRequestCallback.Stub {
        private final Callback mCallback;
        private final Executor mExecutor;
        private final Handler mHandler;

        private AssociationRequestCallbackProxy(Executor executor, Callback callback) {
            this.mExecutor = executor;
            this.mHandler = null;
            this.mCallback = callback;
        }

        private AssociationRequestCallbackProxy(Handler handler, Callback callback) {
            this.mHandler = handler;
            this.mExecutor = null;
            this.mCallback = callback;
        }

        @Override // android.companion.IAssociationRequestCallback
        public void onAssociationPending(PendingIntent pendingIntent) {
            final Callback callback = this.mCallback;
            Objects.requireNonNull(callback);
            execute(new Consumer() { // from class: android.companion.CompanionDeviceManager$AssociationRequestCallbackProxy$$ExternalSyntheticLambda4
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    CompanionDeviceManager.Callback.this.onAssociationPending((IntentSender) obj);
                }
            }, pendingIntent.getIntentSender());
        }

        @Override // android.companion.IAssociationRequestCallback
        public void onAssociationCreated(AssociationInfo associationInfo) {
            final Callback callback = this.mCallback;
            Objects.requireNonNull(callback);
            execute(new Consumer() { // from class: android.companion.CompanionDeviceManager$AssociationRequestCallbackProxy$$ExternalSyntheticLambda2
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    CompanionDeviceManager.Callback.this.onAssociationCreated((AssociationInfo) obj);
                }
            }, associationInfo);
        }

        @Override // android.companion.IAssociationRequestCallback
        public void onFailure(int i, CharSequence charSequence) {
            if (Flags.associationFailureCode()) {
                final Callback callback = this.mCallback;
                Objects.requireNonNull(callback);
                execute(new BiConsumer() { // from class: android.companion.CompanionDeviceManager$AssociationRequestCallbackProxy$$ExternalSyntheticLambda5
                    @Override // java.util.function.BiConsumer
                    public final void accept(Object obj, Object obj2) {
                        CompanionDeviceManager.Callback.this.onFailure(((Integer) obj).intValue(), (CharSequence) obj2);
                    }
                }, Integer.valueOf(i), charSequence);
            }
            final Callback callback2 = this.mCallback;
            Objects.requireNonNull(callback2);
            execute(new Consumer() { // from class: android.companion.CompanionDeviceManager$AssociationRequestCallbackProxy$$ExternalSyntheticLambda6
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    CompanionDeviceManager.Callback.this.onFailure((CharSequence) obj);
                }
            }, charSequence);
        }

        private <T> void execute(final Consumer<T> consumer, final T t) {
            Executor executor = this.mExecutor;
            if (executor != null) {
                executor.execute(new Runnable() { // from class: android.companion.CompanionDeviceManager$AssociationRequestCallbackProxy$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        consumer.accept(t);
                    }
                });
                return;
            }
            Handler handler = this.mHandler;
            if (handler != null) {
                handler.post(new Runnable() { // from class: android.companion.CompanionDeviceManager$AssociationRequestCallbackProxy$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        consumer.accept(t);
                    }
                });
            }
        }

        private <T, U> void execute(final BiConsumer<T, U> biConsumer, final T t, final U u) {
            Executor executor = this.mExecutor;
            if (executor != null) {
                executor.execute(new Runnable() { // from class: android.companion.CompanionDeviceManager$AssociationRequestCallbackProxy$$ExternalSyntheticLambda3
                    @Override // java.lang.Runnable
                    public final void run() {
                        biConsumer.accept(t, u);
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class OnAssociationsChangedListenerProxy extends IOnAssociationsChangedListener.Stub {
        private final Executor mExecutor;
        private final OnAssociationsChangedListener mListener;

        private OnAssociationsChangedListenerProxy(Executor executor, OnAssociationsChangedListener onAssociationsChangedListener) {
            this.mExecutor = executor;
            this.mListener = onAssociationsChangedListener;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onAssociationsChanged$0(List list) {
            this.mListener.onAssociationsChanged(list);
        }

        @Override // android.companion.IOnAssociationsChangedListener
        public void onAssociationsChanged(final List<AssociationInfo> list) {
            this.mExecutor.execute(new Runnable() { // from class: android.companion.CompanionDeviceManager$OnAssociationsChangedListenerProxy$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    CompanionDeviceManager.OnAssociationsChangedListenerProxy.this.lambda$onAssociationsChanged$0(list);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class OnTransportsChangedListenerProxy extends IOnTransportsChangedListener.Stub {
        private final Executor mExecutor;
        private final Consumer<List<AssociationInfo>> mListener;

        private OnTransportsChangedListenerProxy(Executor executor, Consumer<List<AssociationInfo>> consumer) {
            this.mExecutor = executor;
            this.mListener = consumer;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onTransportsChanged$0(List list) {
            this.mListener.accept(list);
        }

        @Override // android.companion.IOnTransportsChangedListener
        public void onTransportsChanged(final List<AssociationInfo> list) {
            this.mExecutor.execute(new Runnable() { // from class: android.companion.CompanionDeviceManager$OnTransportsChangedListenerProxy$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    CompanionDeviceManager.OnTransportsChangedListenerProxy.this.lambda$onTransportsChanged$0(list);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class OnMessageReceivedListenerProxy extends IOnMessageReceivedListener.Stub {
        private final Executor mExecutor;
        private final BiConsumer<Integer, byte[]> mListener;

        private OnMessageReceivedListenerProxy(Executor executor, BiConsumer<Integer, byte[]> biConsumer) {
            this.mExecutor = executor;
            this.mListener = biConsumer;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onMessageReceived$0(int i, byte[] bArr) {
            this.mListener.accept(Integer.valueOf(i), bArr);
        }

        @Override // android.companion.IOnMessageReceivedListener
        public void onMessageReceived(final int i, final byte[] bArr) {
            this.mExecutor.execute(new Runnable() { // from class: android.companion.CompanionDeviceManager$OnMessageReceivedListenerProxy$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    CompanionDeviceManager.OnMessageReceivedListenerProxy.this.lambda$onMessageReceived$0(i, bArr);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class SystemDataTransferCallbackProxy extends ISystemDataTransferCallback.Stub {
        private final OutcomeReceiver<Void, CompanionException> mCallback;
        private final Executor mExecutor;

        private SystemDataTransferCallbackProxy(Executor executor, OutcomeReceiver<Void, CompanionException> outcomeReceiver) {
            this.mExecutor = executor;
            this.mCallback = outcomeReceiver;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onResult$0() {
            this.mCallback.onResult(null);
        }

        @Override // android.companion.ISystemDataTransferCallback
        public void onResult() {
            this.mExecutor.execute(new Runnable() { // from class: android.companion.CompanionDeviceManager$SystemDataTransferCallbackProxy$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    CompanionDeviceManager.SystemDataTransferCallbackProxy.this.lambda$onResult$0();
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onError$1(String str) {
            this.mCallback.onError(new CompanionException(str));
        }

        @Override // android.companion.ISystemDataTransferCallback
        public void onError(final String str) {
            this.mExecutor.execute(new Runnable() { // from class: android.companion.CompanionDeviceManager$SystemDataTransferCallbackProxy$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    CompanionDeviceManager.SystemDataTransferCallbackProxy.this.lambda$onError$1(str);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    class Transport {
        private final int mAssociationId;
        private final int mFlags;
        private InputStream mLocalIn;
        private OutputStream mLocalOut;
        private final InputStream mRemoteIn;
        private final OutputStream mRemoteOut;
        private volatile boolean mStopped;

        Transport(CompanionDeviceManager companionDeviceManager, int i, InputStream inputStream, OutputStream outputStream) {
            this(i, inputStream, outputStream, 0);
        }

        Transport(int i, InputStream inputStream, OutputStream outputStream, int i2) {
            this.mAssociationId = i;
            this.mRemoteIn = inputStream;
            this.mRemoteOut = outputStream;
            this.mFlags = i2;
        }

        public void start() throws IOException {
            if (CompanionDeviceManager.this.mService == null) {
                Log.w(CompanionDeviceManager.TAG, "CompanionDeviceManager service is not available.");
                return;
            }
            ParcelFileDescriptor[] createSocketPair = ParcelFileDescriptor.createSocketPair();
            ParcelFileDescriptor parcelFileDescriptor = createSocketPair[0];
            ParcelFileDescriptor parcelFileDescriptor2 = createSocketPair[1];
            this.mLocalIn = new ParcelFileDescriptor.AutoCloseInputStream(parcelFileDescriptor);
            this.mLocalOut = new ParcelFileDescriptor.AutoCloseOutputStream(parcelFileDescriptor);
            try {
                CompanionDeviceManager.this.mService.attachSystemDataTransport(CompanionDeviceManager.this.mContext.getOpPackageName(), CompanionDeviceManager.this.mContext.getUserId(), this.mAssociationId, parcelFileDescriptor2, this.mFlags);
                new Thread(new Runnable() { // from class: android.companion.CompanionDeviceManager$Transport$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        CompanionDeviceManager.Transport.this.lambda$start$0();
                    }
                }).start();
                new Thread(new Runnable() { // from class: android.companion.CompanionDeviceManager$Transport$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        CompanionDeviceManager.Transport.this.lambda$start$1();
                    }
                }).start();
            } catch (RemoteException e) {
                throw new IOException("Failed to configure transport", e);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$start$0() {
            try {
                copyWithFlushing(this.mLocalIn, this.mRemoteOut);
            } catch (IOException e) {
                if (this.mStopped) {
                    return;
                }
                Log.w(CompanionDeviceManager.TAG, "Trouble during outgoing transport", e);
                stop();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$start$1() {
            try {
                copyWithFlushing(this.mRemoteIn, this.mLocalOut);
            } catch (IOException e) {
                if (this.mStopped) {
                    return;
                }
                Log.w(CompanionDeviceManager.TAG, "Trouble during incoming transport", e);
                stop();
            }
        }

        public void stop() {
            if (CompanionDeviceManager.this.mService == null) {
                Log.w(CompanionDeviceManager.TAG, "CompanionDeviceManager service is not available.");
                return;
            }
            this.mStopped = true;
            try {
                CompanionDeviceManager.this.mService.detachSystemDataTransport(CompanionDeviceManager.this.mContext.getOpPackageName(), CompanionDeviceManager.this.mContext.getUserId(), this.mAssociationId);
            } catch (RemoteException | IllegalArgumentException e) {
                Log.w(CompanionDeviceManager.TAG, "Failed to detach transport", e);
            }
            IoUtils.closeQuietly(this.mRemoteIn);
            IoUtils.closeQuietly(this.mRemoteOut);
            IoUtils.closeQuietly(this.mLocalIn);
            IoUtils.closeQuietly(this.mLocalOut);
        }

        private void copyWithFlushing(InputStream inputStream, OutputStream outputStream) throws IOException {
            byte[] bArr = new byte[8192];
            while (true) {
                int read = inputStream.read(bArr);
                if (read == -1) {
                    return;
                }
                outputStream.write(bArr, 0, read);
                outputStream.flush();
            }
        }
    }

    private Icon scaleIcon(Icon icon, Context context) {
        Bitmap bitmap;
        if (icon == null) {
            return null;
        }
        if (icon.getType() == 6 || icon.getType() == 4) {
            throw new IllegalArgumentException("The URI based Icon is not supported.");
        }
        Drawable loadDrawable = icon.loadDrawable(context);
        if (loadDrawable instanceof BitmapDrawable) {
            bitmap = Bitmap.createScaledBitmap(((BitmapDrawable) loadDrawable).getBitmap(), 24, 24, false);
        } else {
            Bitmap createBitmap = Bitmap.createBitmap(context.getResources().getDisplayMetrics(), 24, 24, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(createBitmap);
            loadDrawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            loadDrawable.draw(canvas);
            bitmap = createBitmap;
        }
        return Icon.createWithBitmap(bitmap);
    }
}
