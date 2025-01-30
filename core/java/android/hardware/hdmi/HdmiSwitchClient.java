package android.hardware.hdmi;

import android.annotation.SystemApi;
import android.p009os.Binder;
import android.p009os.RemoteException;
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

  HdmiSwitchClient(IHdmiControlService service) {
    super(service);
  }

  private static IHdmiControlCallback getCallbackWrapper(final OnSelectListener listener) {
    return new IHdmiControlCallback.Stub() { // from class: android.hardware.hdmi.HdmiSwitchClient.1
      @Override // android.hardware.hdmi.IHdmiControlCallback
      public void onComplete(int result) {
        OnSelectListener.this.onSelect(result);
      }
    };
  }

  @Override // android.hardware.hdmi.HdmiClient
  public int getDeviceType() {
    return 6;
  }

  public void selectDevice(int logicalAddress, OnSelectListener listener) {
    Objects.requireNonNull(listener);
    try {
      this.mService.deviceSelect(logicalAddress, getCallbackWrapper(listener));
    } catch (RemoteException e) {
      Log.m97e(TAG, "failed to select device: ", e);
      throw e.rethrowFromSystemServer();
    }
  }

  @SystemApi
  public void selectPort(int portId, OnSelectListener listener) {
    Objects.requireNonNull(listener);
    try {
      this.mService.portSelect(portId, getCallbackWrapper(listener));
    } catch (RemoteException e) {
      Log.m97e(TAG, "failed to select port: ", e);
      throw e.rethrowFromSystemServer();
    }
  }

  @Deprecated
  public void selectDevice(int logicalAddress, Executor executor, OnSelectListener listener) {
    Objects.requireNonNull(listener);
    try {
      this.mService.deviceSelect(logicalAddress, new BinderC12242(executor, listener));
    } catch (RemoteException e) {
      Log.m97e(TAG, "failed to select device: ", e);
      throw e.rethrowFromSystemServer();
    }
  }

  /* renamed from: android.hardware.hdmi.HdmiSwitchClient$2 */
  class BinderC12242 extends IHdmiControlCallback.Stub {
    final /* synthetic */ Executor val$executor;
    final /* synthetic */ OnSelectListener val$listener;

    BinderC12242(Executor executor, OnSelectListener onSelectListener) {
      this.val$executor = executor;
      this.val$listener = onSelectListener;
    }

    @Override // android.hardware.hdmi.IHdmiControlCallback
    public void onComplete(final int result) {
      final Executor executor = this.val$executor;
      final OnSelectListener onSelectListener = this.val$listener;
      Binder.withCleanCallingIdentity(
          new FunctionalUtils
              .ThrowingRunnable() { // from class:
                                    // android.hardware.hdmi.HdmiSwitchClient$2$$ExternalSyntheticLambda1
            @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
            public final void runOrThrow() {
              executor.execute(
                  new Runnable() { // from class:
                                   // android.hardware.hdmi.HdmiSwitchClient$2$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                      HdmiSwitchClient.OnSelectListener.this.onSelect(r2);
                    }
                  });
            }
          });
    }
  }

  @SystemApi
  public void selectPort(int portId, Executor executor, OnSelectListener listener) {
    Objects.requireNonNull(listener);
    try {
      this.mService.portSelect(portId, new BinderC12253(executor, listener));
    } catch (RemoteException e) {
      Log.m97e(TAG, "failed to select port: ", e);
      throw e.rethrowFromSystemServer();
    }
  }

  /* renamed from: android.hardware.hdmi.HdmiSwitchClient$3 */
  class BinderC12253 extends IHdmiControlCallback.Stub {
    final /* synthetic */ Executor val$executor;
    final /* synthetic */ OnSelectListener val$listener;

    BinderC12253(Executor executor, OnSelectListener onSelectListener) {
      this.val$executor = executor;
      this.val$listener = onSelectListener;
    }

    @Override // android.hardware.hdmi.IHdmiControlCallback
    public void onComplete(final int result) {
      final Executor executor = this.val$executor;
      final OnSelectListener onSelectListener = this.val$listener;
      Binder.withCleanCallingIdentity(
          new FunctionalUtils
              .ThrowingRunnable() { // from class:
                                    // android.hardware.hdmi.HdmiSwitchClient$3$$ExternalSyntheticLambda0
            @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
            public final void runOrThrow() {
              executor.execute(
                  new Runnable() { // from class:
                                   // android.hardware.hdmi.HdmiSwitchClient$3$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                      HdmiSwitchClient.OnSelectListener.this.onSelect(r2);
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
      Log.m97e("TAG", "Failed to call getDeviceList():", e);
      return Collections.emptyList();
    }
  }

  @Deprecated
  public List<HdmiPortInfo> getPortInfo() {
    try {
      return this.mService.getPortInfo();
    } catch (RemoteException e) {
      Log.m97e("TAG", "Failed to call getPortInfo():", e);
      return Collections.emptyList();
    }
  }
}
