package android.service.carrier;

import android.annotation.SystemApi;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.UserHandle;
import android.service.carrier.CarrierMessagingServiceWrapper;
import android.service.carrier.ICarrierMessagingCallback;
import android.service.carrier.ICarrierMessagingService;
import com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.Flags;
import com.android.internal.util.Preconditions;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executor;

@SystemApi
/* loaded from: classes3.dex */
public final class CarrierMessagingServiceWrapper implements AutoCloseable {
    private volatile CarrierMessagingServiceConnection mCarrierMessagingServiceConnection;
    private Context mContext;
    private volatile ICarrierMessagingService mICarrierMessagingService;
    private Runnable mOnServiceReadyCallback;
    private Executor mServiceReadyCallbackExecutor;

    @SystemApi
    public interface CarrierMessagingCallback {
        default void onDownloadMmsComplete(int i) {
        }

        default void onReceiveSmsComplete(int i) {
        }

        default void onSendMmsComplete(int i, byte[] bArr) {
        }

        default void onSendMultipartSmsComplete(int i, int[] iArr) {
        }

        default void onSendSmsComplete(int i, int i2) {
        }
    }

    @SystemApi
    public boolean bindToCarrierMessagingService(Context context, String str, Executor executor, Runnable runnable) {
        Preconditions.checkState(this.mCarrierMessagingServiceConnection == null);
        Objects.requireNonNull(context);
        Objects.requireNonNull(str);
        Objects.requireNonNull(executor);
        Objects.requireNonNull(runnable);
        Intent intent = new Intent(CarrierMessagingService.SERVICE_INTERFACE);
        intent.setPackage(str);
        this.mCarrierMessagingServiceConnection = new CarrierMessagingServiceConnection();
        this.mOnServiceReadyCallback = runnable;
        this.mServiceReadyCallbackExecutor = executor;
        this.mContext = context;
        if (Flags.supportCarrierServicesForHsum()) {
            return context.bindServiceAsUser(intent, this.mCarrierMessagingServiceConnection, 1, UserHandle.of(ActivityManager.getCurrentUser()));
        }
        return context.bindService(intent, this.mCarrierMessagingServiceConnection, 1);
    }

    @SystemApi
    public void disconnect() {
        Preconditions.checkNotNull(this.mCarrierMessagingServiceConnection);
        this.mContext.unbindService(this.mCarrierMessagingServiceConnection);
        this.mCarrierMessagingServiceConnection = null;
        this.mOnServiceReadyCallback = null;
        this.mServiceReadyCallbackExecutor = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onServiceReady(ICarrierMessagingService iCarrierMessagingService) {
        this.mICarrierMessagingService = iCarrierMessagingService;
        if (this.mOnServiceReadyCallback == null || this.mServiceReadyCallbackExecutor == null) {
            return;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            this.mServiceReadyCallbackExecutor.execute(this.mOnServiceReadyCallback);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @SystemApi
    public void receiveSms(MessagePdu messagePdu, String str, int i, int i2, Executor executor, CarrierMessagingCallback carrierMessagingCallback) {
        if (this.mICarrierMessagingService != null) {
            try {
                this.mICarrierMessagingService.filterSms(messagePdu, str, i, i2, new CarrierMessagingCallbackInternal(this, carrierMessagingCallback, executor));
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @SystemApi
    public void sendTextSms(String str, int i, String str2, int i2, Executor executor, CarrierMessagingCallback carrierMessagingCallback) {
        Objects.requireNonNull(this.mICarrierMessagingService);
        try {
            this.mICarrierMessagingService.sendTextSms(str, i, str2, i2, new CarrierMessagingCallbackInternal(this, carrierMessagingCallback, executor));
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    @SystemApi
    public void sendDataSms(byte[] bArr, int i, String str, int i2, int i3, Executor executor, CarrierMessagingCallback carrierMessagingCallback) {
        Objects.requireNonNull(this.mICarrierMessagingService);
        try {
            this.mICarrierMessagingService.sendDataSms(bArr, i, str, i2, i3, new CarrierMessagingCallbackInternal(this, carrierMessagingCallback, executor));
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    @SystemApi
    public void sendMultipartTextSms(List<String> list, int i, String str, int i2, Executor executor, CarrierMessagingCallback carrierMessagingCallback) {
        Objects.requireNonNull(this.mICarrierMessagingService);
        try {
            this.mICarrierMessagingService.sendMultipartTextSms(list, i, str, i2, new CarrierMessagingCallbackInternal(this, carrierMessagingCallback, executor));
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    @SystemApi
    public void sendMms(Uri uri, int i, Uri uri2, Executor executor, CarrierMessagingCallback carrierMessagingCallback) {
        Objects.requireNonNull(this.mICarrierMessagingService);
        try {
            this.mICarrierMessagingService.sendMms(uri, i, uri2, new CarrierMessagingCallbackInternal(this, carrierMessagingCallback, executor));
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    @SystemApi
    public void downloadMms(Uri uri, int i, Uri uri2, Executor executor, CarrierMessagingCallback carrierMessagingCallback) {
        Objects.requireNonNull(this.mICarrierMessagingService);
        try {
            this.mICarrierMessagingService.downloadMms(uri, i, uri2, new CarrierMessagingCallbackInternal(this, carrierMessagingCallback, executor));
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    @Override // java.lang.AutoCloseable
    public void close() {
        disconnect();
    }

    private final class CarrierMessagingServiceConnection implements ServiceConnection {
        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName componentName) {
        }

        private CarrierMessagingServiceConnection() {
        }

        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            CarrierMessagingServiceWrapper.this.onServiceReady(ICarrierMessagingService.Stub.asInterface(iBinder));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    final class CarrierMessagingCallbackInternal extends ICarrierMessagingCallback.Stub {
        final CarrierMessagingCallback mCarrierMessagingCallback;
        final Executor mExecutor;

        CarrierMessagingCallbackInternal(CarrierMessagingServiceWrapper carrierMessagingServiceWrapper, CarrierMessagingCallback carrierMessagingCallback, Executor executor) {
            this.mCarrierMessagingCallback = carrierMessagingCallback;
            this.mExecutor = executor;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onFilterComplete$0(int i) {
            this.mCarrierMessagingCallback.onReceiveSmsComplete(i);
        }

        @Override // android.service.carrier.ICarrierMessagingCallback
        public void onFilterComplete(final int i) throws RemoteException {
            this.mExecutor.execute(new Runnable() { // from class: android.service.carrier.CarrierMessagingServiceWrapper$CarrierMessagingCallbackInternal$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    CarrierMessagingServiceWrapper.CarrierMessagingCallbackInternal.this.lambda$onFilterComplete$0(i);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSendSmsComplete$1(int i, int i2) {
            this.mCarrierMessagingCallback.onSendSmsComplete(i, i2);
        }

        @Override // android.service.carrier.ICarrierMessagingCallback
        public void onSendSmsComplete(final int i, final int i2) throws RemoteException {
            this.mExecutor.execute(new Runnable() { // from class: android.service.carrier.CarrierMessagingServiceWrapper$CarrierMessagingCallbackInternal$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    CarrierMessagingServiceWrapper.CarrierMessagingCallbackInternal.this.lambda$onSendSmsComplete$1(i, i2);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSendMultipartSmsComplete$2(int i, int[] iArr) {
            this.mCarrierMessagingCallback.onSendMultipartSmsComplete(i, iArr);
        }

        @Override // android.service.carrier.ICarrierMessagingCallback
        public void onSendMultipartSmsComplete(final int i, final int[] iArr) throws RemoteException {
            this.mExecutor.execute(new Runnable() { // from class: android.service.carrier.CarrierMessagingServiceWrapper$CarrierMessagingCallbackInternal$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    CarrierMessagingServiceWrapper.CarrierMessagingCallbackInternal.this.lambda$onSendMultipartSmsComplete$2(i, iArr);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSendMmsComplete$3(int i, byte[] bArr) {
            this.mCarrierMessagingCallback.onSendMmsComplete(i, bArr);
        }

        @Override // android.service.carrier.ICarrierMessagingCallback
        public void onSendMmsComplete(final int i, final byte[] bArr) throws RemoteException {
            this.mExecutor.execute(new Runnable() { // from class: android.service.carrier.CarrierMessagingServiceWrapper$CarrierMessagingCallbackInternal$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    CarrierMessagingServiceWrapper.CarrierMessagingCallbackInternal.this.lambda$onSendMmsComplete$3(i, bArr);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onDownloadMmsComplete$4(int i) {
            this.mCarrierMessagingCallback.onDownloadMmsComplete(i);
        }

        @Override // android.service.carrier.ICarrierMessagingCallback
        public void onDownloadMmsComplete(final int i) throws RemoteException {
            this.mExecutor.execute(new Runnable() { // from class: android.service.carrier.CarrierMessagingServiceWrapper$CarrierMessagingCallbackInternal$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    CarrierMessagingServiceWrapper.CarrierMessagingCallbackInternal.this.lambda$onDownloadMmsComplete$4(i);
                }
            });
        }
    }
}
