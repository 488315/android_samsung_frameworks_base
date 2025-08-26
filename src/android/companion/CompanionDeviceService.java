package android.companion;

import android.app.Service;
import android.companion.ICompanionDeviceService;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import com.android.internal.hidden_from_bootclasspath.android.companion.Flags;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Objects;

/* loaded from: classes.dex */
public abstract class CompanionDeviceService extends Service {
    private static final String LOG_TAG = "CDM_CompanionDeviceService";
    public static final String SERVICE_INTERFACE = "android.companion.CompanionDeviceService";
    private final Stub mRemote = new Stub();

    public void onBindCompanionDeviceService(Intent intent) {
    }

    @Deprecated
    public void onDeviceAppeared(String str) {
    }

    @Deprecated
    public void onDeviceDisappeared(String str) {
    }

    public void onDevicePresenceEvent(DevicePresenceEvent devicePresenceEvent) {
    }

    @Deprecated
    public void onMessageDispatchedFromSystem(int i, int i2, byte[] bArr) {
        Log.w(LOG_TAG, "Replaced by attachSystemDataTransport");
    }

    @Deprecated
    public final void dispatchMessageToSystem(int i, int i2, byte[] bArr) throws DeviceNotAssociatedException {
        Log.w(LOG_TAG, "Replaced by attachSystemDataTransport");
    }

    public final void attachSystemDataTransport(int i, InputStream inputStream, OutputStream outputStream) throws DeviceNotAssociatedException {
        ((CompanionDeviceManager) getSystemService(CompanionDeviceManager.class)).attachSystemDataTransport(i, (InputStream) Objects.requireNonNull(inputStream), (OutputStream) Objects.requireNonNull(outputStream));
    }

    public final void detachSystemDataTransport(int i) throws DeviceNotAssociatedException {
        ((CompanionDeviceManager) getSystemService(CompanionDeviceManager.class)).detachSystemDataTransport(i);
    }

    @Deprecated
    public void onDeviceAppeared(AssociationInfo associationInfo) {
        if (associationInfo.isSelfManaged()) {
            return;
        }
        onDeviceAppeared(associationInfo.getDeviceMacAddressAsString());
    }

    @Deprecated
    public void onDeviceDisappeared(AssociationInfo associationInfo) {
        if (associationInfo.isSelfManaged()) {
            return;
        }
        onDeviceDisappeared(associationInfo.getDeviceMacAddressAsString());
    }

    @Override // android.app.Service
    public final IBinder onBind(Intent intent) {
        if (Objects.equals(intent.getAction(), SERVICE_INTERFACE)) {
            onBindCompanionDeviceService(intent);
            return this.mRemote;
        }
        Log.w(LOG_TAG, "Tried to bind to wrong intent (should be android.companion.CompanionDeviceService): " + intent);
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    class Stub extends ICompanionDeviceService.Stub {
        final Handler mMainHandler;
        final CompanionDeviceService mService;

        private Stub() {
            this.mMainHandler = Handler.getMain();
            this.mService = CompanionDeviceService.this;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onDeviceAppeared$0(AssociationInfo associationInfo) {
            this.mService.onDeviceAppeared(associationInfo);
        }

        @Override // android.companion.ICompanionDeviceService
        public void onDeviceAppeared(final AssociationInfo associationInfo) {
            this.mMainHandler.postAtFrontOfQueue(new Runnable() { // from class: android.companion.CompanionDeviceService$Stub$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onDeviceAppeared$0(associationInfo);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onDeviceDisappeared$1(AssociationInfo associationInfo) {
            this.mService.onDeviceDisappeared(associationInfo);
        }

        @Override // android.companion.ICompanionDeviceService
        public void onDeviceDisappeared(final AssociationInfo associationInfo) {
            this.mMainHandler.postAtFrontOfQueue(new Runnable() { // from class: android.companion.CompanionDeviceService$Stub$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onDeviceDisappeared$1(associationInfo);
                }
            });
        }

        @Override // android.companion.ICompanionDeviceService
        public void onDevicePresenceEvent(final DevicePresenceEvent devicePresenceEvent) {
            if (Flags.devicePresence()) {
                this.mMainHandler.postAtFrontOfQueue(new Runnable() { // from class: android.companion.CompanionDeviceService$Stub$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$onDevicePresenceEvent$2(devicePresenceEvent);
                    }
                });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onDevicePresenceEvent$2(DevicePresenceEvent devicePresenceEvent) {
            this.mService.onDevicePresenceEvent(devicePresenceEvent);
        }
    }
}
