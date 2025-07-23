package android.hardware.hdmi;

import android.annotation.SystemApi;
import android.hardware.hdmi.HdmiClient;
import android.hardware.hdmi.HdmiControlManager;
import android.hardware.hdmi.IHdmiControlCallback;
import android.hardware.hdmi.IHdmiVendorCommandListener;
import android.os.Binder;
import android.os.RemoteException;
import android.util.Log;
import com.android.internal.util.FunctionalUtils;
import java.util.concurrent.Executor;

@SystemApi
/* loaded from: classes2.dex */
public abstract class HdmiClient {
    private static final String TAG = "HdmiClient";
    private static final int UNKNOWN_VENDOR_ID = 16777215;
    private IHdmiVendorCommandListener mIHdmiVendorCommandListener;
    final IHdmiControlService mService;

    public interface OnDeviceSelectedListener {
        void onDeviceSelected(int i, int i2);
    }

    abstract int getDeviceType();

    HdmiClient(IHdmiControlService iHdmiControlService) {
        this.mService = iHdmiControlService;
    }

    public void selectDevice(int i, Executor executor, OnDeviceSelectedListener onDeviceSelectedListener) {
        if (onDeviceSelectedListener == null) {
            throw new IllegalArgumentException("listener must not be null.");
        }
        if (executor == null) {
            throw new IllegalArgumentException("executor must not be null.");
        }
        try {
            this.mService.deviceSelect(i, getCallbackWrapper(i, executor, onDeviceSelectedListener));
        } catch (RemoteException e) {
            Log.e(TAG, "failed to select device: ", e);
        }
    }

    /* renamed from: android.hardware.hdmi.HdmiClient$1, reason: invalid class name */
    class AnonymousClass1 extends IHdmiControlCallback.Stub {
        final /* synthetic */ Executor val$executor;
        final /* synthetic */ OnDeviceSelectedListener val$listener;
        final /* synthetic */ int val$logicalAddress;

        AnonymousClass1(Executor executor, OnDeviceSelectedListener onDeviceSelectedListener, int i) {
            this.val$executor = executor;
            this.val$listener = onDeviceSelectedListener;
            this.val$logicalAddress = i;
        }

        @Override // android.hardware.hdmi.IHdmiControlCallback
        public void onComplete(final int i) {
            final Executor executor = this.val$executor;
            final OnDeviceSelectedListener onDeviceSelectedListener = this.val$listener;
            final int i2 = this.val$logicalAddress;
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.hardware.hdmi.HdmiClient$1$$ExternalSyntheticLambda0
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    executor.execute(new Runnable() { // from class: android.hardware.hdmi.HdmiClient$1$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            HdmiClient.OnDeviceSelectedListener.this.onDeviceSelected(r2, r3);
                        }
                    });
                }
            });
        }
    }

    private static IHdmiControlCallback getCallbackWrapper(int i, Executor executor, OnDeviceSelectedListener onDeviceSelectedListener) {
        return new AnonymousClass1(executor, onDeviceSelectedListener, i);
    }

    public HdmiDeviceInfo getActiveSource() {
        try {
            return this.mService.getActiveSource();
        } catch (RemoteException e) {
            Log.e(TAG, "getActiveSource threw exception ", e);
            return null;
        }
    }

    public void sendKeyEvent(int i, boolean z) {
        try {
            this.mService.sendKeyEvent(getDeviceType(), i, z);
        } catch (RemoteException e) {
            Log.e(TAG, "sendKeyEvent threw exception ", e);
        }
    }

    public void sendVolumeKeyEvent(int i, boolean z) {
        try {
            this.mService.sendVolumeKeyEvent(getDeviceType(), i, z);
        } catch (RemoteException e) {
            Log.e(TAG, "sendVolumeKeyEvent threw exception ", e);
            throw e.rethrowFromSystemServer();
        }
    }

    public void sendVendorCommand(int i, byte[] bArr, boolean z) {
        try {
            this.mService.sendVendorCommand(getDeviceType(), i, bArr, z);
        } catch (RemoteException e) {
            Log.e(TAG, "failed to send vendor command: ", e);
        }
    }

    public void setVendorCommandListener(HdmiControlManager.VendorCommandListener vendorCommandListener) {
        setVendorCommandListener(vendorCommandListener, 16777215);
    }

    public void setVendorCommandListener(HdmiControlManager.VendorCommandListener vendorCommandListener, int i) {
        if (vendorCommandListener == null) {
            throw new IllegalArgumentException("listener cannot be null");
        }
        if (this.mIHdmiVendorCommandListener != null) {
            throw new IllegalStateException("listener was already set");
        }
        try {
            IHdmiVendorCommandListener listenerWrapper = getListenerWrapper(vendorCommandListener);
            this.mService.addVendorCommandListener(listenerWrapper, i);
            this.mIHdmiVendorCommandListener = listenerWrapper;
        } catch (RemoteException e) {
            Log.e(TAG, "failed to set vendor command listener: ", e);
        }
    }

    private static IHdmiVendorCommandListener getListenerWrapper(final HdmiControlManager.VendorCommandListener vendorCommandListener) {
        return new IHdmiVendorCommandListener.Stub() { // from class: android.hardware.hdmi.HdmiClient.2
            @Override // android.hardware.hdmi.IHdmiVendorCommandListener
            public void onReceived(int i, int i2, byte[] bArr, boolean z) {
                HdmiControlManager.VendorCommandListener.this.onReceived(i, i2, bArr, z);
            }

            @Override // android.hardware.hdmi.IHdmiVendorCommandListener
            public void onControlStateChanged(boolean z, int i) {
                HdmiControlManager.VendorCommandListener.this.onControlStateChanged(z, i);
            }
        };
    }
}
