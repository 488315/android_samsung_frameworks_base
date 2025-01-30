package com.android.server.credentials;

import android.content.ComponentName;
import android.content.Context;
import android.credentials.ClearCredentialStateRequest;
import android.credentials.CredentialProviderInfo;
import android.credentials.IClearCredentialStateCallback;
import android.credentials.ui.UserSelectionDialogResult;
import android.os.CancellationSignal;
import android.service.credentials.CallingAppInfo;
import android.util.Slog;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

/* loaded from: classes.dex */
public final class ClearRequestSession extends RequestSession
    implements ProviderSession.ProviderInternalCallback {
  @Override // com.android.server.credentials.RequestSession
  public void launchUiWithProviderData(ArrayList arrayList) {}

  @Override // com.android.server.credentials.ProviderSession.ProviderInternalCallback
  public void onFinalErrorReceived(ComponentName componentName, String str, String str2) {}

  @Override // com.android.server.credentials.CredentialManagerUi.CredentialManagerUiCallback
  public void onUiCancellation(boolean z) {}

  @Override // com.android.server.credentials.CredentialManagerUi.CredentialManagerUiCallback
  public void onUiSelectorInvocationFailure() {}

  @Override // com.android.server.credentials.RequestSession,
  // com.android.server.credentials.CredentialManagerUi.CredentialManagerUiCallback
  public /* bridge */ /* synthetic */ void onUiSelection(
      UserSelectionDialogResult userSelectionDialogResult) {
    super.onUiSelection(userSelectionDialogResult);
  }

  public ClearRequestSession(
      Context context,
      RequestSession.SessionLifetime sessionLifetime,
      Object obj,
      int i,
      int i2,
      IClearCredentialStateCallback iClearCredentialStateCallback,
      ClearCredentialStateRequest clearCredentialStateRequest,
      CallingAppInfo callingAppInfo,
      Set set,
      CancellationSignal cancellationSignal,
      long j) {
    super(
        context,
        sessionLifetime,
        obj,
        i,
        i2,
        clearCredentialStateRequest,
        iClearCredentialStateCallback,
        "android.credentials.ui.TYPE_UNDEFINED",
        callingAppInfo,
        set,
        cancellationSignal,
        j);
  }

  @Override // com.android.server.credentials.RequestSession
  public ProviderSession initiateProviderSession(
      CredentialProviderInfo credentialProviderInfo,
      RemoteCredentialService remoteCredentialService) {
    ProviderClearSession createNewSession =
        ProviderClearSession.createNewSession(
            this.mContext, this.mUserId, credentialProviderInfo, this, remoteCredentialService);
    if (createNewSession != null) {
      Slog.i(
          "GetRequestSession",
          "Provider session created and being added for: "
              + credentialProviderInfo.getComponentName());
      this.mProviders.put(createNewSession.getComponentName().flattenToString(), createNewSession);
    }
    return createNewSession;
  }

  @Override // com.android.server.credentials.ProviderSession.ProviderInternalCallback
  public void onProviderStatusChanged(
      ProviderSession.Status status,
      ComponentName componentName,
      ProviderSession.CredentialsSource credentialsSource) {
    Slog.i(
        "GetRequestSession",
        "Provider changed with status: " + status + ", and source: " + credentialsSource);
    if (ProviderSession.isTerminatingStatus(status)) {
      Slog.i("GetRequestSession", "Provider terminating status");
      onProviderTerminated(componentName);
    } else if (ProviderSession.isCompletionStatus(status)) {
      Slog.i("GetRequestSession", "Provider has completion status");
      onProviderResponseComplete(componentName);
    }
  }

  @Override // com.android.server.credentials.ProviderSession.ProviderInternalCallback
  public void onFinalResponseReceived(ComponentName componentName, Void r5) {
    this.mRequestSessionMetric.collectUiResponseData(true, System.nanoTime());
    this.mRequestSessionMetric.updateMetricsOnResponseReceived(
        this.mProviders, componentName, isPrimaryProviderViaProviderInfo(componentName));
    respondToClientWithResponseAndFinish(null);
  }

  public void onProviderResponseComplete(ComponentName componentName) {
    if (isAnyProviderPending()) {
      return;
    }
    onFinalResponseReceived(componentName, (Void) null);
  }

  public void onProviderTerminated(ComponentName componentName) {
    if (isAnyProviderPending()) {
      return;
    }
    processResponses();
  }

  @Override // com.android.server.credentials.RequestSession
  public void invokeClientCallbackSuccess(Void r1) {
    ((IClearCredentialStateCallback) this.mClientCallback).onSuccess();
  }

  @Override // com.android.server.credentials.RequestSession
  public void invokeClientCallbackError(String str, String str2) {
    ((IClearCredentialStateCallback) this.mClientCallback).onError(str, str2);
  }

  public final void processResponses() {
    Iterator it = this.mProviders.values().iterator();
    while (it.hasNext()) {
      if (((ProviderSession) it.next()).isProviderResponseSet().booleanValue()) {
        respondToClientWithResponseAndFinish(null);
        return;
      }
    }
    this.mRequestSessionMetric.collectFrameworkException(
        "android.credentials.ClearCredentialStateException.TYPE_UNKNOWN");
    respondToClientWithErrorAndFinish(
        "android.credentials.ClearCredentialStateException.TYPE_UNKNOWN", "All providers failed");
  }
}
