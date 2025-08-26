package android.hardware.hdmi;

import android.annotation.SystemApi;
import android.hardware.hdmi.IHdmiControlCallback;
import android.os.Binder;
import android.os.RemoteException;
import android.util.Log;
import com.android.internal.util.FunctionalUtils;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executor;

@SystemApi
/* loaded from: classes2.dex */
public class HdmiSwitchClient extends HdmiClient {
    private static final String TAG = "HdmiSwitchClient";

    @SystemApi
    public interface OnSelectListener {
        void onSelect(int i);
    }

    @Override // android.hardware.hdmi.HdmiClient
    public int getDeviceType() {
        return 6;
    }

    HdmiSwitchClient(IHdmiControlService iHdmiControlService) {
        super(iHdmiControlService);
    }

    private static IHdmiControlCallback getCallbackWrapper(final OnSelectListener onSelectListener) {
        return new IHdmiControlCallback.Stub() { // from class: android.hardware.hdmi.HdmiSwitchClient.1
            @Override // android.hardware.hdmi.IHdmiControlCallback
            public void onComplete(int i) {
                onSelectListener.onSelect(i);
            }
        };
    }

    public void selectDevice(int i, OnSelectListener onSelectListener) {
        Objects.requireNonNull(onSelectListener);
        try {
            this.mService.deviceSelect(i, getCallbackWrapper(onSelectListener));
        } catch (RemoteException e) {
            Log.e(TAG, "failed to select device: ", e);
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void selectPort(int i, OnSelectListener onSelectListener) {
        Objects.requireNonNull(onSelectListener);
        try {
            this.mService.portSelect(i, getCallbackWrapper(onSelectListener));
        } catch (RemoteException e) {
            Log.e(TAG, "failed to select port: ", e);
            throw e.rethrowFromSystemServer();
        }
    }

    @Deprecated
    public void selectDevice(int i, Executor executor, OnSelectListener onSelectListener) {
        Objects.requireNonNull(onSelectListener);
        try {
            this.mService.deviceSelect(i, new AnonymousClass2(this, executor, onSelectListener));
        } catch (RemoteException e) {
            Log.e(TAG, "failed to select device: ", e);
            throw e.rethrowFromSystemServer();
        }
    }

    /* renamed from: android.hardware.hdmi.HdmiSwitchClient$2, reason: invalid class name */
    class AnonymousClass2 extends IHdmiControlCallback.Stub {
        final /* synthetic */ Executor val$executor;
        final /* synthetic */ OnSelectListener val$listener;

        AnonymousClass2(HdmiSwitchClient hdmiSwitchClient, Executor executor, OnSelectListener onSelectListener) {
            this.val$executor = executor;
            this.val$listener = onSelectListener;
        }

        @Override // android.hardware.hdmi.IHdmiControlCallback
        public void onComplete(final int i) {
            final Executor executor = this.val$executor;
            final OnSelectListener onSelectListener = this.val$listener;
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.hardware.hdmi.HdmiSwitchClient$2$$ExternalSyntheticLambda0
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    executor.execute(new Runnable() { // from class: android.hardware.hdmi.HdmiSwitchClient$2$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            onSelectListener.onSelect(i);
                        }
                    });
                }
            });
        }
    }

    @SystemApi
    public void selectPort(int i, Executor executor, OnSelectListener onSelectListener) {
        Objects.requireNonNull(onSelectListener);
        try {
            this.mService.portSelect(i, new AnonymousClass3(this, executor, onSelectListener));
        } catch (RemoteException e) {
            Log.e(TAG, "failed to select port: ", e);
            throw e.rethrowFromSystemServer();
        }
    }

    /* renamed from: android.hardware.hdmi.HdmiSwitchClient$3, reason: invalid class name */
    class AnonymousClass3 extends IHdmiControlCallback.Stub {
        final /* synthetic */ Executor val$executor;
        final /* synthetic */ OnSelectListener val$listener;

        AnonymousClass3(HdmiSwitchClient hdmiSwitchClient, Executor executor, OnSelectListener onSelectListener) {
            this.val$executor = executor;
            this.val$listener = onSelectListener;
        }

        @Override // android.hardware.hdmi.IHdmiControlCallback
        public void onComplete(final int i) {
            final Executor executor = this.val$executor;
            final OnSelectListener onSelectListener = this.val$listener;
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.hardware.hdmi.HdmiSwitchClient$3$$ExternalSyntheticLambda0
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    executor.execute(new Runnable() { // from class: android.hardware.hdmi.HdmiSwitchClient$3$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            onSelectListener.onSelect(i);
                        }
                    });
                }
            });
        }
    }

    @Deprecated
    public List<HdmiDeviceInfo> getDeviceList() {
        try {
            return this.mService.getDeviceList();
        } catch (RemoteException e) {
            Log.e("TAG", "Failed to call getDeviceList():", e);
            return Collections.EMPTY_LIST;
        }
    }

    @Deprecated
    public List<HdmiPortInfo> getPortInfo() {
        try {
            return this.mService.getPortInfo();
        } catch (RemoteException e) {
            Log.e("TAG", "Failed to call getPortInfo():", e);
            return Collections.EMPTY_LIST;
        }
    }
}
