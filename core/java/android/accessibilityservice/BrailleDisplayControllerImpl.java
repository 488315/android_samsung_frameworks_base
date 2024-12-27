package android.accessibilityservice;

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

public final class BrailleDisplayControllerImpl implements BrailleDisplayController {
    private static final boolean IS_HIDRAW_SUPPORTED =
            SystemProperties.getBoolean("ro.accessibility.support_hidraw", true);
    private final AccessibilityService mAccessibilityService;
    private IBrailleDisplayConnection mBrailleDisplayConnection;
    private BrailleDisplayController.BrailleDisplayCallback mCallback;
    private Executor mCallbackExecutor;
    private final boolean mIsHidrawSupported;
    private final Object mLock;

    BrailleDisplayControllerImpl(AccessibilityService accessibilityService, Object lock) {
        this(accessibilityService, lock, IS_HIDRAW_SUPPORTED);
    }

    public BrailleDisplayControllerImpl(
            AccessibilityService accessibilityService, Object lock, boolean isHidrawSupported) {
        this.mAccessibilityService = accessibilityService;
        this.mLock = lock;
        this.mIsHidrawSupported = isHidrawSupported;
    }

    @Override // android.accessibilityservice.BrailleDisplayController
    public void connect(
            BluetoothDevice bluetoothDevice,
            BrailleDisplayController.BrailleDisplayCallback callback) {
        connect(bluetoothDevice, this.mAccessibilityService.getMainExecutor(), callback);
    }

    @Override // android.accessibilityservice.BrailleDisplayController
    public void connect(
            final BluetoothDevice bluetoothDevice,
            Executor callbackExecutor,
            BrailleDisplayController.BrailleDisplayCallback callback) {
        Objects.requireNonNull(bluetoothDevice);
        Objects.requireNonNull(callbackExecutor);
        Objects.requireNonNull(callback);
        connect(
                new FunctionalUtils.RemoteExceptionIgnoringConsumer() { // from class:
                    // android.accessibilityservice.BrailleDisplayControllerImpl$$ExternalSyntheticLambda1
                    @Override // com.android.internal.util.FunctionalUtils.RemoteExceptionIgnoringConsumer
                    public final void acceptOrThrow(Object obj) {
                        BrailleDisplayControllerImpl.this.lambda$connect$0(
                                bluetoothDevice, (IAccessibilityServiceConnection) obj);
                    }
                },
                callbackExecutor,
                callback);
    }

    public /* synthetic */ void lambda$connect$0(
            BluetoothDevice bluetoothDevice, IAccessibilityServiceConnection serviceConnection)
            throws RemoteException {
        serviceConnection.connectBluetoothBrailleDisplay(
                bluetoothDevice.getAddress(), new IBrailleDisplayControllerWrapper());
    }

    @Override // android.accessibilityservice.BrailleDisplayController
    public void connect(
            UsbDevice usbDevice, BrailleDisplayController.BrailleDisplayCallback callback) {
        connect(usbDevice, this.mAccessibilityService.getMainExecutor(), callback);
    }

    @Override // android.accessibilityservice.BrailleDisplayController
    public void connect(
            final UsbDevice usbDevice,
            Executor callbackExecutor,
            BrailleDisplayController.BrailleDisplayCallback callback) {
        Objects.requireNonNull(usbDevice);
        Objects.requireNonNull(callbackExecutor);
        Objects.requireNonNull(callback);
        connect(
                new FunctionalUtils.RemoteExceptionIgnoringConsumer() { // from class:
                    // android.accessibilityservice.BrailleDisplayControllerImpl$$ExternalSyntheticLambda2
                    @Override // com.android.internal.util.FunctionalUtils.RemoteExceptionIgnoringConsumer
                    public final void acceptOrThrow(Object obj) {
                        BrailleDisplayControllerImpl.this.lambda$connect$1(
                                usbDevice, (IAccessibilityServiceConnection) obj);
                    }
                },
                callbackExecutor,
                callback);
    }

    public /* synthetic */ void lambda$connect$1(
            UsbDevice usbDevice, IAccessibilityServiceConnection serviceConnection)
            throws RemoteException {
        serviceConnection.connectUsbBrailleDisplay(
                usbDevice, new IBrailleDisplayControllerWrapper());
    }

    private void connect(
            FunctionalUtils.RemoteExceptionIgnoringConsumer<IAccessibilityServiceConnection>
                    createConnection,
            Executor callbackExecutor,
            final BrailleDisplayController.BrailleDisplayCallback callback) {
        BrailleDisplayController.checkApiFlagIsEnabled();
        if (!this.mIsHidrawSupported) {
            callbackExecutor.execute(
                    new Runnable() { // from class:
                        // android.accessibilityservice.BrailleDisplayControllerImpl$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            BrailleDisplayController.BrailleDisplayCallback.this.onConnectionFailed(
                                    1);
                        }
                    });
            return;
        }
        if (isConnected()) {
            throw new IllegalStateException("This service already has a connected Braille display");
        }
        IAccessibilityServiceConnection serviceConnection =
                AccessibilityInteractionClient.getConnection(
                        this.mAccessibilityService.getConnectionId());
        if (serviceConnection == null) {
            throw new IllegalStateException("Accessibility service is not connected");
        }
        synchronized (this.mLock) {
            this.mCallbackExecutor = callbackExecutor;
            this.mCallback = callback;
        }
        try {
            createConnection.acceptOrThrow(serviceConnection);
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
    public void write(byte[] buffer) throws IOException {
        BrailleDisplayController.checkApiFlagIsEnabled();
        Objects.requireNonNull(buffer);
        if (buffer.length > IBinder.getSuggestedMaxIpcSizeBytes()) {
            throw new IllegalArgumentException("Invalid write buffer size " + buffer.length);
        }
        synchronized (this.mLock) {
            if (this.mBrailleDisplayConnection == null) {
                throw new IOException("Braille display is not connected");
            }
            try {
                this.mBrailleDisplayConnection.write(buffer);
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
                    if (this.mBrailleDisplayConnection != null) {
                        this.mBrailleDisplayConnection.disconnect();
                    }
                } catch (RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            } finally {
                clearConnectionLocked();
            }
        }
    }

    final class IBrailleDisplayControllerWrapper extends IBrailleDisplayController.Stub {
        private IBrailleDisplayControllerWrapper() {}

        @Override // android.accessibilityservice.IBrailleDisplayController
        public void onConnected(IBrailleDisplayConnection connection, final byte[] hidDescriptor) {
            BrailleDisplayController.checkApiFlagIsEnabled();
            long identity = Binder.clearCallingIdentity();
            try {
                synchronized (BrailleDisplayControllerImpl.this.mLock) {
                    BrailleDisplayControllerImpl.this.mBrailleDisplayConnection = connection;
                    BrailleDisplayControllerImpl.this.mCallbackExecutor.execute(
                            new Runnable() { // from class:
                                // android.accessibilityservice.BrailleDisplayControllerImpl$IBrailleDisplayControllerWrapper$$ExternalSyntheticLambda3
                                @Override // java.lang.Runnable
                                public final void run() {
                                    BrailleDisplayControllerImpl.IBrailleDisplayControllerWrapper
                                            .this
                                            .lambda$onConnected$0(hidDescriptor);
                                }
                            });
                }
            } finally {
                Binder.restoreCallingIdentity(identity);
            }
        }

        public /* synthetic */ void lambda$onConnected$0(byte[] hidDescriptor) {
            BrailleDisplayControllerImpl.this.mCallback.onConnected(hidDescriptor);
        }

        @Override // android.accessibilityservice.IBrailleDisplayController
        public void onConnectionFailed(final int errorCode) {
            BrailleDisplayController.checkApiFlagIsEnabled();
            long identity = Binder.clearCallingIdentity();
            try {
                synchronized (BrailleDisplayControllerImpl.this.mLock) {
                    BrailleDisplayControllerImpl.this.mCallbackExecutor.execute(
                            new Runnable() { // from class:
                                // android.accessibilityservice.BrailleDisplayControllerImpl$IBrailleDisplayControllerWrapper$$ExternalSyntheticLambda0
                                @Override // java.lang.Runnable
                                public final void run() {
                                    BrailleDisplayControllerImpl.IBrailleDisplayControllerWrapper
                                            .this
                                            .lambda$onConnectionFailed$1(errorCode);
                                }
                            });
                }
            } finally {
                Binder.restoreCallingIdentity(identity);
            }
        }

        public /* synthetic */ void lambda$onConnectionFailed$1(int errorCode) {
            BrailleDisplayControllerImpl.this.mCallback.onConnectionFailed(errorCode);
        }

        @Override // android.accessibilityservice.IBrailleDisplayController
        public void onInput(final byte[] input) {
            BrailleDisplayController.checkApiFlagIsEnabled();
            long identity = Binder.clearCallingIdentity();
            try {
                synchronized (BrailleDisplayControllerImpl.this.mLock) {
                    if (BrailleDisplayControllerImpl.this.mBrailleDisplayConnection != null) {
                        BrailleDisplayControllerImpl.this.mCallbackExecutor.execute(
                                new Runnable() { // from class:
                                    // android.accessibilityservice.BrailleDisplayControllerImpl$IBrailleDisplayControllerWrapper$$ExternalSyntheticLambda2
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        BrailleDisplayControllerImpl
                                                .IBrailleDisplayControllerWrapper.this
                                                .lambda$onInput$2(input);
                                    }
                                });
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(identity);
            }
        }

        public /* synthetic */ void lambda$onInput$2(byte[] input) {
            BrailleDisplayControllerImpl.this.mCallback.onInput(input);
        }

        @Override // android.accessibilityservice.IBrailleDisplayController
        public void onDisconnected() {
            BrailleDisplayController.checkApiFlagIsEnabled();
            long identity = Binder.clearCallingIdentity();
            try {
                synchronized (BrailleDisplayControllerImpl.this.mLock) {
                    Executor executor = BrailleDisplayControllerImpl.this.mCallbackExecutor;
                    final BrailleDisplayController.BrailleDisplayCallback brailleDisplayCallback =
                            BrailleDisplayControllerImpl.this.mCallback;
                    Objects.requireNonNull(brailleDisplayCallback);
                    executor.execute(
                            new Runnable() { // from class:
                                // android.accessibilityservice.BrailleDisplayControllerImpl$IBrailleDisplayControllerWrapper$$ExternalSyntheticLambda1
                                @Override // java.lang.Runnable
                                public final void run() {
                                    BrailleDisplayController.BrailleDisplayCallback.this
                                            .onDisconnected();
                                }
                            });
                    BrailleDisplayControllerImpl.this.clearConnectionLocked();
                }
            } finally {
                Binder.restoreCallingIdentity(identity);
            }
        }
    }

    public void clearConnectionLocked() {
        this.mBrailleDisplayConnection = null;
    }
}
