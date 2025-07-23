package android.accessibilityservice;

import android.accessibilityservice.BrailleDisplayController;
import android.accessibilityservice.BrailleDisplayControllerImpl;
import android.accessibilityservice.IBrailleDisplayController;
import android.bluetooth.BluetoothDevice;
import android.hardware.usb.UsbDevice;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.SystemProperties;
import android.view.accessibility.AccessibilityInteractionClient;
import com.android.internal.util.FunctionalUtils;
import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.Executor;

/* loaded from: classes.dex */
public final class BrailleDisplayControllerImpl implements BrailleDisplayController {
    private static final boolean IS_HIDRAW_SUPPORTED = SystemProperties.getBoolean("ro.accessibility.support_hidraw", true);
    private final AccessibilityService mAccessibilityService;
    private IBrailleDisplayConnection mBrailleDisplayConnection;
    private BrailleDisplayController.BrailleDisplayCallback mCallback;
    private Executor mCallbackExecutor;
    private final boolean mIsHidrawSupported;
    private final Object mLock;

    BrailleDisplayControllerImpl(AccessibilityService accessibilityService, Object obj) {
        this(accessibilityService, obj, IS_HIDRAW_SUPPORTED);
    }

    public BrailleDisplayControllerImpl(AccessibilityService accessibilityService, Object obj, boolean z) {
        this.mAccessibilityService = accessibilityService;
        this.mLock = obj;
        this.mIsHidrawSupported = z;
    }

    @Override // android.accessibilityservice.BrailleDisplayController
    public void connect(BluetoothDevice bluetoothDevice, BrailleDisplayController.BrailleDisplayCallback brailleDisplayCallback) {
        connect(bluetoothDevice, this.mAccessibilityService.getMainExecutor(), brailleDisplayCallback);
    }

    @Override // android.accessibilityservice.BrailleDisplayController
    public void connect(final BluetoothDevice bluetoothDevice, Executor executor, BrailleDisplayController.BrailleDisplayCallback brailleDisplayCallback) {
        Objects.requireNonNull(bluetoothDevice);
        Objects.requireNonNull(executor);
        Objects.requireNonNull(brailleDisplayCallback);
        connect(new FunctionalUtils.RemoteExceptionIgnoringConsumer() { // from class: android.accessibilityservice.BrailleDisplayControllerImpl$$ExternalSyntheticLambda1
            @Override // com.android.internal.util.FunctionalUtils.RemoteExceptionIgnoringConsumer
            public final void acceptOrThrow(Object obj) {
                BrailleDisplayControllerImpl.this.lambda$connect$0(bluetoothDevice, (IAccessibilityServiceConnection) obj);
            }
        }, executor, brailleDisplayCallback);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$connect$0(BluetoothDevice bluetoothDevice, IAccessibilityServiceConnection iAccessibilityServiceConnection) throws RemoteException {
        iAccessibilityServiceConnection.connectBluetoothBrailleDisplay(bluetoothDevice.getAddress(), new IBrailleDisplayControllerWrapper());
    }

    @Override // android.accessibilityservice.BrailleDisplayController
    public void connect(UsbDevice usbDevice, BrailleDisplayController.BrailleDisplayCallback brailleDisplayCallback) {
        connect(usbDevice, this.mAccessibilityService.getMainExecutor(), brailleDisplayCallback);
    }

    @Override // android.accessibilityservice.BrailleDisplayController
    public void connect(final UsbDevice usbDevice, Executor executor, BrailleDisplayController.BrailleDisplayCallback brailleDisplayCallback) {
        Objects.requireNonNull(usbDevice);
        Objects.requireNonNull(executor);
        Objects.requireNonNull(brailleDisplayCallback);
        connect(new FunctionalUtils.RemoteExceptionIgnoringConsumer() { // from class: android.accessibilityservice.BrailleDisplayControllerImpl$$ExternalSyntheticLambda2
            @Override // com.android.internal.util.FunctionalUtils.RemoteExceptionIgnoringConsumer
            public final void acceptOrThrow(Object obj) {
                BrailleDisplayControllerImpl.this.lambda$connect$1(usbDevice, (IAccessibilityServiceConnection) obj);
            }
        }, executor, brailleDisplayCallback);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$connect$1(UsbDevice usbDevice, IAccessibilityServiceConnection iAccessibilityServiceConnection) throws RemoteException {
        iAccessibilityServiceConnection.connectUsbBrailleDisplay(usbDevice, new IBrailleDisplayControllerWrapper());
    }

    private void connect(FunctionalUtils.RemoteExceptionIgnoringConsumer<IAccessibilityServiceConnection> remoteExceptionIgnoringConsumer, Executor executor, final BrailleDisplayController.BrailleDisplayCallback brailleDisplayCallback) {
        BrailleDisplayController.checkApiFlagIsEnabled();
        if (!this.mIsHidrawSupported) {
            executor.execute(new Runnable() { // from class: android.accessibilityservice.BrailleDisplayControllerImpl$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    BrailleDisplayController.BrailleDisplayCallback.this.onConnectionFailed(1);
                }
            });
            return;
        }
        if (isConnected()) {
            throw new IllegalStateException("This service already has a connected Braille display");
        }
        IAccessibilityServiceConnection connection = AccessibilityInteractionClient.getConnection(this.mAccessibilityService.getConnectionId());
        if (connection == null) {
            throw new IllegalStateException("Accessibility service is not connected");
        }
        synchronized (this.mLock) {
            this.mCallbackExecutor = executor;
            this.mCallback = brailleDisplayCallback;
        }
        try {
            remoteExceptionIgnoringConsumer.acceptOrThrow(connection);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.accessibilityservice.BrailleDisplayController
    public boolean isConnected() {
        BrailleDisplayController.checkApiFlagIsEnabled();
        return this.mBrailleDisplayConnection != null;
    }

    @Override // android.accessibilityservice.BrailleDisplayController
    public void write(byte[] bArr) throws IOException {
        BrailleDisplayController.checkApiFlagIsEnabled();
        Objects.requireNonNull(bArr);
        if (bArr.length > IBinder.getSuggestedMaxIpcSizeBytes()) {
            throw new IllegalArgumentException("Invalid write buffer size " + bArr.length);
        }
        synchronized (this.mLock) {
            IBrailleDisplayConnection iBrailleDisplayConnection = this.mBrailleDisplayConnection;
            if (iBrailleDisplayConnection == null) {
                throw new IOException("Braille display is not connected");
            }
            try {
                iBrailleDisplayConnection.write(bArr);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    @Override // android.accessibilityservice.BrailleDisplayController
    public void disconnect() {
        BrailleDisplayController.checkApiFlagIsEnabled();
        synchronized (this.mLock) {
            try {
                try {
                    IBrailleDisplayConnection iBrailleDisplayConnection = this.mBrailleDisplayConnection;
                    if (iBrailleDisplayConnection != null) {
                        iBrailleDisplayConnection.disconnect();
                    }
                } catch (RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            } finally {
                clearConnectionLocked();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    final class IBrailleDisplayControllerWrapper extends IBrailleDisplayController.Stub {
        private IBrailleDisplayControllerWrapper() {
        }

        @Override // android.accessibilityservice.IBrailleDisplayController
        public void onConnected(IBrailleDisplayConnection iBrailleDisplayConnection, final byte[] bArr) {
            BrailleDisplayController.checkApiFlagIsEnabled();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (BrailleDisplayControllerImpl.this.mLock) {
                    BrailleDisplayControllerImpl.this.mBrailleDisplayConnection = iBrailleDisplayConnection;
                    BrailleDisplayControllerImpl.this.mCallbackExecutor.execute(new Runnable() { // from class: android.accessibilityservice.BrailleDisplayControllerImpl$IBrailleDisplayControllerWrapper$$ExternalSyntheticLambda3
                        @Override // java.lang.Runnable
                        public final void run() {
                            BrailleDisplayControllerImpl.IBrailleDisplayControllerWrapper.this.lambda$onConnected$0(bArr);
                        }
                    });
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onConnected$0(byte[] bArr) {
            BrailleDisplayControllerImpl.this.mCallback.onConnected(bArr);
        }

        @Override // android.accessibilityservice.IBrailleDisplayController
        public void onConnectionFailed(final int i) {
            BrailleDisplayController.checkApiFlagIsEnabled();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (BrailleDisplayControllerImpl.this.mLock) {
                    BrailleDisplayControllerImpl.this.mCallbackExecutor.execute(new Runnable() { // from class: android.accessibilityservice.BrailleDisplayControllerImpl$IBrailleDisplayControllerWrapper$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            BrailleDisplayControllerImpl.IBrailleDisplayControllerWrapper.this.lambda$onConnectionFailed$1(i);
                        }
                    });
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onConnectionFailed$1(int i) {
            BrailleDisplayControllerImpl.this.mCallback.onConnectionFailed(i);
        }

        @Override // android.accessibilityservice.IBrailleDisplayController
        public void onInput(final byte[] bArr) {
            BrailleDisplayController.checkApiFlagIsEnabled();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (BrailleDisplayControllerImpl.this.mLock) {
                    if (BrailleDisplayControllerImpl.this.mBrailleDisplayConnection != null) {
                        BrailleDisplayControllerImpl.this.mCallbackExecutor.execute(new Runnable() { // from class: android.accessibilityservice.BrailleDisplayControllerImpl$IBrailleDisplayControllerWrapper$$ExternalSyntheticLambda2
                            @Override // java.lang.Runnable
                            public final void run() {
                                BrailleDisplayControllerImpl.IBrailleDisplayControllerWrapper.this.lambda$onInput$2(bArr);
                            }
                        });
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onInput$2(byte[] bArr) {
            BrailleDisplayControllerImpl.this.mCallback.onInput(bArr);
        }

        @Override // android.accessibilityservice.IBrailleDisplayController
        public void onDisconnected() {
            BrailleDisplayController.checkApiFlagIsEnabled();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (BrailleDisplayControllerImpl.this.mLock) {
                    Executor executor = BrailleDisplayControllerImpl.this.mCallbackExecutor;
                    final BrailleDisplayController.BrailleDisplayCallback brailleDisplayCallback = BrailleDisplayControllerImpl.this.mCallback;
                    Objects.requireNonNull(brailleDisplayCallback);
                    executor.execute(new Runnable() { // from class: android.accessibilityservice.BrailleDisplayControllerImpl$IBrailleDisplayControllerWrapper$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            BrailleDisplayController.BrailleDisplayCallback.this.onDisconnected();
                        }
                    });
                    BrailleDisplayControllerImpl.this.clearConnectionLocked();
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clearConnectionLocked() {
        this.mBrailleDisplayConnection = null;
    }
}
