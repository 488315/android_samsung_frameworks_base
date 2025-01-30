package com.android.server.credentials;

import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.credentials.GetCredentialRequest;
import android.credentials.IGetCredentialCallback;
import android.credentials.IPrepareGetCredentialCallback;
import android.credentials.PrepareGetCredentialResponseInternal;
import android.credentials.ui.GetCredentialProviderData;
import android.credentials.ui.ProviderData;
import android.credentials.ui.RequestInfo;
import android.os.CancellationSignal;
import android.os.RemoteException;
import android.service.credentials.CallingAppInfo;
import android.service.credentials.PermissionUtils;
import android.util.Slog;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/* loaded from: classes.dex */
public class PrepareGetRequestSession extends GetRequestSession {
  public final IPrepareGetCredentialCallback mPrepareGetCredentialCallback;
  public Set mPrimaryProviders;

  public PrepareGetRequestSession(
      Context context,
      RequestSession.SessionLifetime sessionLifetime,
      Object obj,
      int i,
      int i2,
      IGetCredentialCallback iGetCredentialCallback,
      GetCredentialRequest getCredentialRequest,
      CallingAppInfo callingAppInfo,
      Set set,
      Set set2,
      CancellationSignal cancellationSignal,
      long j,
      IPrepareGetCredentialCallback iPrepareGetCredentialCallback) {
    super(
        context,
        sessionLifetime,
        obj,
        i,
        i2,
        iGetCredentialCallback,
        getCredentialRequest,
        callingAppInfo,
        set,
        set2,
        cancellationSignal,
        j);
    ((Set)
            getCredentialRequest.getCredentialOptions().stream()
                .map(new C1087x1051f624())
                .collect(Collectors.toSet()))
        .size();
    this.mRequestSessionMetric.collectGetFlowInitialMetricInfo(getCredentialRequest);
    this.mPrepareGetCredentialCallback = iPrepareGetCredentialCallback;
    this.mPrimaryProviders = set2;
  }

  @Override // com.android.server.credentials.GetRequestSession,
  // com.android.server.credentials.ProviderSession.ProviderInternalCallback
  public void onProviderStatusChanged(
      ProviderSession.Status status,
      ComponentName componentName,
      ProviderSession.CredentialsSource credentialsSource) {
    Slog.i(
        "PrepareGetRequestSession",
        "Provider Status changed with status: " + status + ", and source: " + credentialsSource);
    int i = AbstractC10891.f1672x2a5dfd67[credentialsSource.ordinal()];
    if (i != 1) {
      if (i == 2) {
        if (status == ProviderSession.Status.NO_CREDENTIALS_FROM_AUTH_ENTRY) {
          super.handleEmptyAuthenticationSelection(componentName);
          return;
        } else {
          if (status == ProviderSession.Status.CREDENTIALS_RECEIVED) {
            getProviderDataAndInitiateUi();
            return;
          }
          return;
        }
      }
      Slog.w("PrepareGetRequestSession", "Unexpected source");
      return;
    }
    if (isAnyProviderPending()) {
      return;
    }
    boolean hasPermission =
        PermissionUtils.hasPermission(
            this.mContext,
            this.mClientAppInfo.getPackageName(),
            "android.permission.CREDENTIAL_MANAGER_QUERY_CANDIDATE_CREDENTIALS");
    if (isUiInvocationNeeded()) {
      ArrayList providerDataForUi = getProviderDataForUi();
      if (!providerDataForUi.isEmpty()) {
        constructPendingResponseAndInvokeCallback(
            hasPermission,
            getCredentialResultTypes(hasPermission),
            hasAuthenticationResults(providerDataForUi, hasPermission),
            hasRemoteResults(providerDataForUi, hasPermission),
            getUiIntent());
        return;
      }
    }
    constructEmptyPendingResponseAndInvokeCallback(hasPermission);
  }

  /* renamed from: com.android.server.credentials.PrepareGetRequestSession$1 */
  public abstract /* synthetic */ class AbstractC10891 {

    /* renamed from: $SwitchMap$com$android$server$credentials$ProviderSession$CredentialsSource */
    public static final /* synthetic */ int[] f1672x2a5dfd67;

    static {
      int[] iArr = new int[ProviderSession.CredentialsSource.values().length];
      f1672x2a5dfd67 = iArr;
      try {
        iArr[ProviderSession.CredentialsSource.REMOTE_PROVIDER.ordinal()] = 1;
      } catch (NoSuchFieldError unused) {
      }
      try {
        f1672x2a5dfd67[ProviderSession.CredentialsSource.AUTH_ENTRY.ordinal()] = 2;
      } catch (NoSuchFieldError unused2) {
      }
    }
  }

  public final void constructPendingResponseAndInvokeCallback(
      boolean z, Set set, boolean z2, boolean z3, PendingIntent pendingIntent) {
    try {
      this.mPrepareGetCredentialCallback.onResponse(
          new PrepareGetCredentialResponseInternal(z, set, z2, z3, pendingIntent));
    } catch (RemoteException e) {
      Slog.e("PrepareGetRequestSession", "EXCEPTION while mPendingCallback.onResponse", e);
    }
  }

  public final void constructEmptyPendingResponseAndInvokeCallback(boolean z) {
    try {
      this.mPrepareGetCredentialCallback.onResponse(
          new PrepareGetCredentialResponseInternal(
              z, (Set) null, false, false, (PendingIntent) null));
    } catch (RemoteException e) {
      Slog.e("PrepareGetRequestSession", "EXCEPTION while mPendingCallback.onResponse", e);
    }
  }

  public final boolean hasRemoteResults(ArrayList arrayList, boolean z) {
    if (z) {
      return arrayList.stream()
          .map(
              new Function() { // from class:
                // com.android.server.credentials.PrepareGetRequestSession$$ExternalSyntheticLambda4
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                  GetCredentialProviderData lambda$hasRemoteResults$0;
                  lambda$hasRemoteResults$0 =
                      PrepareGetRequestSession.lambda$hasRemoteResults$0((ProviderData) obj);
                  return lambda$hasRemoteResults$0;
                }
              })
          .anyMatch(
              new Predicate() { // from class:
                // com.android.server.credentials.PrepareGetRequestSession$$ExternalSyntheticLambda5
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                  boolean lambda$hasRemoteResults$1;
                  lambda$hasRemoteResults$1 =
                      PrepareGetRequestSession.lambda$hasRemoteResults$1(
                          (GetCredentialProviderData) obj);
                  return lambda$hasRemoteResults$1;
                }
              });
    }
    return false;
  }

  public static /* synthetic */ GetCredentialProviderData lambda$hasRemoteResults$0(
      ProviderData providerData) {
    return (GetCredentialProviderData) providerData;
  }

  public static /* synthetic */ boolean lambda$hasRemoteResults$1(
      GetCredentialProviderData getCredentialProviderData) {
    return getCredentialProviderData.getRemoteEntry() != null;
  }

  public final boolean hasAuthenticationResults(ArrayList arrayList, boolean z) {
    if (z) {
      return arrayList.stream()
          .map(
              new Function() { // from class:
                // com.android.server.credentials.PrepareGetRequestSession$$ExternalSyntheticLambda2
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                  GetCredentialProviderData lambda$hasAuthenticationResults$2;
                  lambda$hasAuthenticationResults$2 =
                      PrepareGetRequestSession.lambda$hasAuthenticationResults$2(
                          (ProviderData) obj);
                  return lambda$hasAuthenticationResults$2;
                }
              })
          .anyMatch(
              new Predicate() { // from class:
                // com.android.server.credentials.PrepareGetRequestSession$$ExternalSyntheticLambda3
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                  boolean lambda$hasAuthenticationResults$3;
                  lambda$hasAuthenticationResults$3 =
                      PrepareGetRequestSession.lambda$hasAuthenticationResults$3(
                          (GetCredentialProviderData) obj);
                  return lambda$hasAuthenticationResults$3;
                }
              });
    }
    return false;
  }

  public static /* synthetic */ GetCredentialProviderData lambda$hasAuthenticationResults$2(
      ProviderData providerData) {
    return (GetCredentialProviderData) providerData;
  }

  public static /* synthetic */ boolean lambda$hasAuthenticationResults$3(
      GetCredentialProviderData getCredentialProviderData) {
    return !getCredentialProviderData.getAuthenticationEntries().isEmpty();
  }

  public final Set getCredentialResultTypes(boolean z) {
    if (z) {
      return (Set)
          this.mProviders.values().stream()
              .map(
                  new Function() { // from class:
                    // com.android.server.credentials.PrepareGetRequestSession$$ExternalSyntheticLambda0
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                      ProviderGetSession lambda$getCredentialResultTypes$4;
                      lambda$getCredentialResultTypes$4 =
                          PrepareGetRequestSession.lambda$getCredentialResultTypes$4(
                              (ProviderSession) obj);
                      return lambda$getCredentialResultTypes$4;
                    }
                  })
              .flatMap(
                  new Function() { // from class:
                    // com.android.server.credentials.PrepareGetRequestSession$$ExternalSyntheticLambda1
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                      Stream lambda$getCredentialResultTypes$5;
                      lambda$getCredentialResultTypes$5 =
                          PrepareGetRequestSession.lambda$getCredentialResultTypes$5(
                              (ProviderGetSession) obj);
                      return lambda$getCredentialResultTypes$5;
                    }
                  })
              .collect(Collectors.toSet());
    }
    return null;
  }

  public static /* synthetic */ ProviderGetSession lambda$getCredentialResultTypes$4(
      ProviderSession providerSession) {
    return (ProviderGetSession) providerSession;
  }

  public static /* synthetic */ Stream lambda$getCredentialResultTypes$5(
      ProviderGetSession providerGetSession) {
    return providerGetSession.getCredentialEntryTypes().stream();
  }

  public final PendingIntent getUiIntent() {
    ArrayList arrayList = new ArrayList();
    Iterator it = this.mProviders.values().iterator();
    while (it.hasNext()) {
      ProviderData mo4615prepareUiData = ((ProviderSession) it.next()).mo4615prepareUiData();
      if (mo4615prepareUiData != null) {
        arrayList.add(mo4615prepareUiData);
      }
    }
    if (arrayList.isEmpty()) {
      return null;
    }
    ArrayList arrayList2 = new ArrayList();
    Iterator it2 = this.mPrimaryProviders.iterator();
    while (it2.hasNext()) {
      arrayList2.add(((ComponentName) it2.next()).flattenToString());
    }
    return this.mCredentialManagerUi.createPendingIntent(
        RequestInfo.newGetRequestInfo(
            this.mRequestId,
            (GetCredentialRequest) this.mClientRequest,
            this.mClientAppInfo.getPackageName(),
            PermissionUtils.hasPermission(
                this.mContext,
                this.mClientAppInfo.getPackageName(),
                "android.permission.CREDENTIAL_MANAGER_SET_ALLOWED_PROVIDERS"),
            arrayList2),
        arrayList);
  }
}
