package com.android.server.credentials;

import android.R;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.credentials.CredentialProviderInfo;
import android.credentials.ui.ProviderData;
import android.credentials.ui.UserSelectionDialogResult;
import android.os.Binder;
import android.os.CancellationSignal;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import android.os.UserHandle;
import android.service.credentials.CallingAppInfo;
import android.util.Slog;
import com.android.server.credentials.metrics.ApiName;
import com.android.server.credentials.metrics.ApiStatus;
import com.android.server.credentials.metrics.BrowsedAuthenticationMetric;
import com.android.server.credentials.metrics.ProviderSessionMetric;
import com.android.server.credentials.metrics.ProviderStatusForMetrics;
import com.android.server.credentials.metrics.RequestSessionMetric;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

/* loaded from: classes.dex */
public abstract class RequestSession implements CredentialManagerUi.CredentialManagerUiCallback {
  public final int mCallingUid;
  public final CancellationSignal mCancellationSignal;
  public final CallingAppInfo mClientAppInfo;
  public final Object mClientCallback;
  public final Object mClientRequest;
  public final Context mContext;
  public final CredentialManagerUi mCredentialManagerUi;
  public final Set mEnabledProviders;
  public final String mHybridService;
  public final Object mLock;
  public PendingIntent mPendingIntent;
  public final RequestSessionMetric mRequestSessionMetric;
  public final String mRequestType;
  public final SessionLifetime mSessionCallback;
  public final int mUniqueSessionInteger;
  public final int mUserId;
  public final Map mProviders = new ConcurrentHashMap();
  public RequestSessionStatus mRequestSessionStatus = RequestSessionStatus.IN_PROGRESS;
  public final Handler mHandler = new Handler(Looper.getMainLooper(), null, true);
  public final IBinder mRequestId = new Binder();

  enum RequestSessionStatus {
    IN_PROGRESS,
    CANCELLED,
    COMPLETE
  }

  public interface SessionLifetime {
    void onFinishRequestSession(int i, IBinder iBinder);
  }

  public abstract ProviderSession initiateProviderSession(
      CredentialProviderInfo credentialProviderInfo,
      RemoteCredentialService remoteCredentialService);

  public abstract void invokeClientCallbackError(String str, String str2);

  public abstract void invokeClientCallbackSuccess(Object obj);

  public abstract void launchUiWithProviderData(ArrayList arrayList);

  public RequestSession(
      Context context,
      SessionLifetime sessionLifetime,
      Object obj,
      int i,
      int i2,
      Object obj2,
      Object obj3,
      String str,
      CallingAppInfo callingAppInfo,
      Set set,
      CancellationSignal cancellationSignal,
      long j) {
    this.mContext = context;
    this.mLock = obj;
    this.mSessionCallback = sessionLifetime;
    this.mUserId = i;
    this.mCallingUid = i2;
    this.mClientRequest = obj2;
    this.mClientCallback = obj3;
    this.mRequestType = str;
    this.mClientAppInfo = callingAppInfo;
    this.mEnabledProviders = set;
    this.mCancellationSignal = cancellationSignal;
    this.mCredentialManagerUi = new CredentialManagerUi(context, i, this, set);
    this.mHybridService = context.getResources().getString(R.string.date_and_time);
    int highlyUniqueInteger = MetricUtilities.getHighlyUniqueInteger();
    this.mUniqueSessionInteger = highlyUniqueInteger;
    RequestSessionMetric requestSessionMetric =
        new RequestSessionMetric(highlyUniqueInteger, MetricUtilities.getHighlyUniqueInteger());
    this.mRequestSessionMetric = requestSessionMetric;
    requestSessionMetric.collectInitialPhaseMetricInfo(
        j, i2, ApiName.getMetricCodeFromRequestInfo(str));
    setCancellationListener();
  }

  public final void setCancellationListener() {
    this.mCancellationSignal.setOnCancelListener(
        new CancellationSignal.OnCancelListener() { // from class:
          // com.android.server.credentials.RequestSession$$ExternalSyntheticLambda0
          @Override // android.os.CancellationSignal.OnCancelListener
          public final void onCancel() {
            RequestSession.this.lambda$setCancellationListener$0();
          }
        });
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$setCancellationListener$0() {
    finishSession(!maybeCancelUi());
  }

  public final boolean maybeCancelUi() {
    if (this.mCredentialManagerUi.getStatus() != CredentialManagerUi.UiStatus.USER_INTERACTION) {
      return false;
    }
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      this.mContext.startActivityAsUser(
          this.mCredentialManagerUi
              .createCancelIntent(this.mRequestId, this.mClientAppInfo.getPackageName())
              .addFlags(268435456),
          UserHandle.of(this.mUserId));
      Binder.restoreCallingIdentity(clearCallingIdentity);
      return true;
    } catch (Throwable th) {
      Binder.restoreCallingIdentity(clearCallingIdentity);
      throw th;
    }
  }

  public void addProviderSession(ComponentName componentName, ProviderSession providerSession) {
    this.mProviders.put(componentName.flattenToString(), providerSession);
  }

  @Override // com.android.server.credentials.CredentialManagerUi.CredentialManagerUiCallback
  public void onUiSelection(UserSelectionDialogResult userSelectionDialogResult) {
    if (this.mRequestSessionStatus == RequestSessionStatus.COMPLETE) {
      Slog.w("RequestSession", "Request has already been completed. This is strange.");
      return;
    }
    if (isSessionCancelled()) {
      finishSession(true);
      return;
    }
    ProviderSession providerSession =
        (ProviderSession) this.mProviders.get(userSelectionDialogResult.getProviderId());
    if (providerSession == null) {
      Slog.w("RequestSession", "providerSession not found in onUiSelection. This is strange.");
      return;
    }
    ProviderSessionMetric providerSessionMetric = providerSession.mProviderSessionMetric;
    int size = providerSessionMetric.getBrowsedAuthenticationMetric().size();
    this.mRequestSessionMetric.collectMetricPerBrowsingSelect(
        userSelectionDialogResult,
        providerSession.mProviderSessionMetric.getCandidatePhasePerProviderMetric());
    providerSession.onUiEntrySelected(
        userSelectionDialogResult.getEntryKey(),
        userSelectionDialogResult.getEntrySubkey(),
        userSelectionDialogResult.getPendingIntentProviderResponse());
    int size2 = providerSessionMetric.getBrowsedAuthenticationMetric().size();
    if (size2 - size == 1) {
      this.mRequestSessionMetric.logAuthEntry(
          (BrowsedAuthenticationMetric)
              providerSession
                  .mProviderSessionMetric
                  .getBrowsedAuthenticationMetric()
                  .get(size2 - 1));
    }
  }

  public void finishSession(boolean z) {
    Slog.i("RequestSession", "finishing session with propagateCancellation " + z);
    if (z) {
      this.mProviders
          .values()
          .forEach(
              new Consumer() { // from class:
                // com.android.server.credentials.RequestSession$$ExternalSyntheticLambda1
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                  ((ProviderSession) obj).cancelProviderRemoteSession();
                }
              });
    }
    this.mRequestSessionStatus = RequestSessionStatus.COMPLETE;
    this.mProviders.clear();
    clearRequestSessionLocked();
  }

  public void cancelExistingPendingIntent() {
    PendingIntent pendingIntent = this.mPendingIntent;
    if (pendingIntent != null) {
      try {
        pendingIntent.cancel();
        this.mPendingIntent = null;
      } catch (Exception e) {
        Slog.e("RequestSession", "Unable to cancel existing pending intent", e);
      }
    }
  }

  public final void clearRequestSessionLocked() {
    synchronized (this.mLock) {
      this.mSessionCallback.onFinishRequestSession(this.mUserId, this.mRequestId);
    }
  }

  public boolean isAnyProviderPending() {
    Iterator it = this.mProviders.values().iterator();
    while (it.hasNext()) {
      if (ProviderSession.isStatusWaitingForRemoteResponse(
          ((ProviderSession) it.next()).getStatus())) {
        return true;
      }
    }
    return false;
  }

  public boolean isSessionCancelled() {
    return this.mCancellationSignal.isCanceled();
  }

  public boolean isUiInvocationNeeded() {
    for (ProviderSession providerSession : this.mProviders.values()) {
      if (ProviderSession.isUiInvokingStatus(providerSession.getStatus())) {
        return true;
      }
      if (ProviderSession.isStatusWaitingForRemoteResponse(providerSession.getStatus())) {
        break;
      }
    }
    return false;
  }

  public void getProviderDataAndInitiateUi() {
    ArrayList providerDataForUi = getProviderDataForUi();
    if (providerDataForUi.isEmpty()) {
      return;
    }
    launchUiWithProviderData(providerDataForUi);
  }

  public ArrayList getProviderDataForUi() {
    Slog.i("RequestSession", "For ui, provider data size: " + this.mProviders.size());
    ArrayList arrayList = new ArrayList();
    this.mRequestSessionMetric.logCandidatePhaseMetrics(this.mProviders);
    if (isSessionCancelled()) {
      finishSession(true);
      return arrayList;
    }
    Iterator it = this.mProviders.values().iterator();
    while (it.hasNext()) {
      ProviderData mo4615prepareUiData = ((ProviderSession) it.next()).mo4615prepareUiData();
      if (mo4615prepareUiData != null) {
        arrayList.add(mo4615prepareUiData);
      }
    }
    return arrayList;
  }

  public void respondToClientWithResponseAndFinish(Object obj) {
    this.mRequestSessionMetric.logCandidateAggregateMetrics(this.mProviders);
    this.mRequestSessionMetric.collectFinalPhaseProviderMetricStatus(
        false, ProviderStatusForMetrics.FINAL_SUCCESS);
    if (this.mRequestSessionStatus == RequestSessionStatus.COMPLETE) {
      Slog.w("RequestSession", "Request has already been completed. This is strange.");
      return;
    }
    if (isSessionCancelled()) {
      this.mRequestSessionMetric.logApiCalledAtFinish(ApiStatus.CLIENT_CANCELED.getMetricCode());
      finishSession(true);
      return;
    }
    try {
      invokeClientCallbackSuccess(obj);
      this.mRequestSessionMetric.logApiCalledAtFinish(ApiStatus.SUCCESS.getMetricCode());
    } catch (RemoteException e) {
      this.mRequestSessionMetric.collectFinalPhaseProviderMetricStatus(
          true, ProviderStatusForMetrics.FINAL_FAILURE);
      Slog.e("RequestSession", "Issue while responding to client with a response : " + e);
      this.mRequestSessionMetric.logApiCalledAtFinish(ApiStatus.FAILURE.getMetricCode());
    }
    finishSession(false);
  }

  public void respondToClientWithErrorAndFinish(String str, String str2) {
    this.mRequestSessionMetric.logCandidateAggregateMetrics(this.mProviders);
    this.mRequestSessionMetric.collectFinalPhaseProviderMetricStatus(
        true, ProviderStatusForMetrics.FINAL_FAILURE);
    if (this.mRequestSessionStatus == RequestSessionStatus.COMPLETE) {
      Slog.w("RequestSession", "Request has already been completed. This is strange.");
      return;
    }
    if (isSessionCancelled()) {
      this.mRequestSessionMetric.logApiCalledAtFinish(ApiStatus.CLIENT_CANCELED.getMetricCode());
      finishSession(true);
      return;
    }
    try {
      invokeClientCallbackError(str, str2);
    } catch (RemoteException e) {
      Slog.e("RequestSession", "Issue while responding to client with error : " + e);
    }
    this.mRequestSessionMetric.logFailureOrUserCancel(str.contains("TYPE_USER_CANCELED"));
    finishSession(false);
  }

  public boolean isPrimaryProviderViaProviderInfo(ComponentName componentName) {
    CredentialProviderInfo credentialProviderInfo;
    ProviderSession providerSession =
        (ProviderSession) this.mProviders.get(componentName.flattenToString());
    return (providerSession == null
            || (credentialProviderInfo = providerSession.mProviderInfo) == null
            || !credentialProviderInfo.isPrimary())
        ? false
        : true;
  }
}
