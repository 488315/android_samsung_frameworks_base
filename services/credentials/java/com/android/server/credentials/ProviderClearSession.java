package com.android.server.credentials;

import android.content.Context;
import android.credentials.ClearCredentialStateException;
import android.credentials.ClearCredentialStateRequest;
import android.credentials.CredentialProviderInfo;
import android.credentials.ui.ProviderData;
import android.credentials.ui.ProviderPendingIntentResponse;
import android.os.ICancellationSignal;
import android.service.credentials.CallingAppInfo;
import android.util.Slog;

/* loaded from: classes.dex */
public final class ProviderClearSession extends ProviderSession {
  public ClearCredentialStateException mProviderException;

  @Override // com.android.server.credentials.ProviderSession
  public void onUiEntrySelected(
      String str, String str2, ProviderPendingIntentResponse providerPendingIntentResponse) {}

  @Override // com.android.server.credentials.ProviderSession
  /* renamed from: prepareUiData */
  public ProviderData mo4615prepareUiData() {
    return null;
  }

  public static ProviderClearSession createNewSession(
      Context context,
      int i,
      CredentialProviderInfo credentialProviderInfo,
      ClearRequestSession clearRequestSession,
      RemoteCredentialService remoteCredentialService) {
    return new ProviderClearSession(
        context,
        credentialProviderInfo,
        clearRequestSession,
        i,
        remoteCredentialService,
        createProviderRequest(
            (ClearCredentialStateRequest) clearRequestSession.mClientRequest,
            clearRequestSession.mClientAppInfo));
  }

  public static android.service.credentials.ClearCredentialStateRequest createProviderRequest(
      ClearCredentialStateRequest clearCredentialStateRequest, CallingAppInfo callingAppInfo) {
    return new android.service.credentials.ClearCredentialStateRequest(
        callingAppInfo, clearCredentialStateRequest.getData());
  }

  public ProviderClearSession(
      Context context,
      CredentialProviderInfo credentialProviderInfo,
      ProviderSession.ProviderInternalCallback providerInternalCallback,
      int i,
      RemoteCredentialService remoteCredentialService,
      android.service.credentials.ClearCredentialStateRequest clearCredentialStateRequest) {
    super(
        context,
        clearCredentialStateRequest,
        providerInternalCallback,
        credentialProviderInfo.getComponentName(),
        i,
        remoteCredentialService);
    setStatus(ProviderSession.Status.PENDING);
  }

  @Override // com.android.server.credentials.RemoteCredentialService.ProviderCallbacks
  public void onProviderResponseSuccess(Void r2) {
    Slog.i(
        "ProviderClearSession",
        "Remote provider responded with a valid response: " + this.mComponentName);
    this.mProviderResponseSet = Boolean.TRUE;
    updateStatusAndInvokeCallback(
        ProviderSession.Status.COMPLETE, ProviderSession.CredentialsSource.REMOTE_PROVIDER);
  }

  @Override // com.android.server.credentials.RemoteCredentialService.ProviderCallbacks
  public void onProviderResponseFailure(int i, Exception exc) {
    if (exc instanceof ClearCredentialStateException) {
      ClearCredentialStateException clearCredentialStateException =
          (ClearCredentialStateException) exc;
      this.mProviderException = clearCredentialStateException;
      this.mProviderSessionMetric.collectCandidateFrameworkException(
          clearCredentialStateException.getType());
    }
    this.mProviderSessionMetric.collectCandidateExceptionStatus(true);
    updateStatusAndInvokeCallback(
        ProviderSession.Status.CANCELED, ProviderSession.CredentialsSource.REMOTE_PROVIDER);
  }

  @Override // com.android.server.credentials.RemoteCredentialService.ProviderCallbacks
  public void onProviderServiceDied(RemoteCredentialService remoteCredentialService) {
    if (remoteCredentialService.getComponentName().equals(this.mComponentName)) {
      updateStatusAndInvokeCallback(
          ProviderSession.Status.SERVICE_DEAD, ProviderSession.CredentialsSource.REMOTE_PROVIDER);
    } else {
      Slog.w(
          "ProviderClearSession",
          "Component names different in onProviderServiceDied - this should not happen");
    }
  }

  @Override // com.android.server.credentials.RemoteCredentialService.ProviderCallbacks
  public void onProviderCancellable(ICancellationSignal iCancellationSignal) {
    this.mProviderCancellationSignal = iCancellationSignal;
  }

  @Override // com.android.server.credentials.ProviderSession
  public void invokeSession() {
    if (this.mRemoteCredentialService != null) {
      startCandidateMetrics();
      this.mRemoteCredentialService.setCallback(this);
      this.mRemoteCredentialService.onClearCredentialState(
          (android.service.credentials.ClearCredentialStateRequest) this.mProviderRequest);
    }
  }
}
