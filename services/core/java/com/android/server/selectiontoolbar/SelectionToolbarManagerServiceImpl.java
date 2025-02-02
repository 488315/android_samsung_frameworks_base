package com.android.server.selectiontoolbar;

import android.app.AppGlobals;
import android.content.ComponentName;
import android.content.pm.PackageManager;
import android.content.pm.ServiceInfo;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.service.selectiontoolbar.ISelectionToolbarRenderServiceCallback;
import android.util.Slog;
import android.view.selectiontoolbar.ISelectionToolbarCallback;
import android.view.selectiontoolbar.ShowInfo;
import com.android.server.LocalServices;
import com.android.server.infra.AbstractPerUserSystemService;
import com.android.server.input.InputManagerInternal;

/* loaded from: classes3.dex */
public final class SelectionToolbarManagerServiceImpl extends AbstractPerUserSystemService {
  public InputManagerInternal mInputManagerInternal;
  public RemoteSelectionToolbarRenderService mRemoteService;
  public final SelectionToolbarRenderServiceRemoteCallback mRemoteServiceCallback;

  public SelectionToolbarManagerServiceImpl(
      SelectionToolbarManagerService selectionToolbarManagerService, Object obj, int i) {
    super(selectionToolbarManagerService, obj, i);
    this.mRemoteServiceCallback = new SelectionToolbarRenderServiceRemoteCallback();
    this.mInputManagerInternal =
        (InputManagerInternal) LocalServices.getService(InputManagerInternal.class);
    updateRemoteServiceLocked();
  }

  @Override // com.android.server.infra.AbstractPerUserSystemService
  public ServiceInfo newServiceInfoLocked(ComponentName componentName) {
    return getServiceInfoOrThrow(componentName, this.mUserId);
  }

  @Override // com.android.server.infra.AbstractPerUserSystemService
  public boolean updateLocked(boolean z) {
    boolean updateLocked = super.updateLocked(z);
    updateRemoteServiceLocked();
    return updateLocked;
  }

  public final void updateRemoteServiceLocked() {
    if (this.mRemoteService != null) {
      Slog.d(
          "SelectionToolbarManagerServiceImpl",
          "updateRemoteService(): destroying old remote service");
      this.mRemoteService.unbind();
      this.mRemoteService = null;
    }
  }

  public void showToolbar(ShowInfo showInfo, ISelectionToolbarCallback iSelectionToolbarCallback) {
    RemoteSelectionToolbarRenderService ensureRemoteServiceLocked = ensureRemoteServiceLocked();
    if (ensureRemoteServiceLocked != null) {
      ensureRemoteServiceLocked.onShow(Binder.getCallingUid(), showInfo, iSelectionToolbarCallback);
    }
  }

  public void hideToolbar(long j) {
    RemoteSelectionToolbarRenderService ensureRemoteServiceLocked = ensureRemoteServiceLocked();
    if (ensureRemoteServiceLocked != null) {
      ensureRemoteServiceLocked.onHide(j);
    }
  }

  public void dismissToolbar(long j) {
    RemoteSelectionToolbarRenderService ensureRemoteServiceLocked = ensureRemoteServiceLocked();
    if (ensureRemoteServiceLocked != null) {
      ensureRemoteServiceLocked.onDismiss(Binder.getCallingUid(), j);
    }
  }

  public final RemoteSelectionToolbarRenderService ensureRemoteServiceLocked() {
    if (this.mRemoteService == null) {
      this.mRemoteService =
          new RemoteSelectionToolbarRenderService(
              getContext(),
              ComponentName.unflattenFromString(getComponentNameLocked()),
              this.mUserId,
              this.mRemoteServiceCallback);
    }
    return this.mRemoteService;
  }

  public static ServiceInfo getServiceInfoOrThrow(ComponentName componentName, int i) {
    ServiceInfo serviceInfo;
    try {
      serviceInfo = AppGlobals.getPackageManager().getServiceInfo(componentName, 128, i);
    } catch (RemoteException unused) {
      serviceInfo = null;
    }
    if (serviceInfo != null) {
      return serviceInfo;
    }
    throw new PackageManager.NameNotFoundException(
        "Could not get serviceInfo for " + componentName.flattenToShortString());
  }

  public final void transferTouchFocus(IBinder iBinder, IBinder iBinder2) {
    this.mInputManagerInternal.transferTouchFocus(iBinder, iBinder2);
  }

  public final class SelectionToolbarRenderServiceRemoteCallback
      extends ISelectionToolbarRenderServiceCallback.Stub {
    public SelectionToolbarRenderServiceRemoteCallback() {}

    public void transferTouch(IBinder iBinder, IBinder iBinder2) {
      SelectionToolbarManagerServiceImpl.this.transferTouchFocus(iBinder, iBinder2);
    }
  }
}
