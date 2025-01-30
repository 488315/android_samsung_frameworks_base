package com.android.server.credentials.metrics;

import android.service.credentials.BeginCreateCredentialResponse;
import android.service.credentials.BeginGetCredentialResponse;
import android.service.credentials.CreateEntry;
import android.service.credentials.CredentialEntry;
import android.util.Slog;
import com.android.server.credentials.MetricUtilities;
import com.android.server.credentials.metrics.shared.ResponseCollective;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/* loaded from: classes.dex */
public class ProviderSessionMetric {
  public final List mBrowsedAuthenticationMetric;
  public final CandidatePhaseMetric mCandidatePhasePerProviderMetric;

  public ProviderSessionMetric(int i) {
    ArrayList arrayList = new ArrayList();
    this.mBrowsedAuthenticationMetric = arrayList;
    this.mCandidatePhasePerProviderMetric = new CandidatePhaseMetric(i);
    arrayList.add(new BrowsedAuthenticationMetric(i));
  }

  public CandidatePhaseMetric getCandidatePhasePerProviderMetric() {
    return this.mCandidatePhasePerProviderMetric;
  }

  public List getBrowsedAuthenticationMetric() {
    return this.mBrowsedAuthenticationMetric;
  }

  public void collectCandidateExceptionStatus(boolean z) {
    try {
      this.mCandidatePhasePerProviderMetric.setHasException(z);
    } catch (Exception e) {
      Slog.i("ProviderSessionMetric", "Error while setting candidate metric exception " + e);
    }
  }

  public void collectAuthenticationExceptionStatus(boolean z) {
    try {
      getUsedAuthenticationMetric().setHasException(z);
    } catch (Exception e) {
      Slog.i("ProviderSessionMetric", "Error while setting authentication metric exception " + e);
    }
  }

  public void collectCandidateFrameworkException(String str) {
    try {
      this.mCandidatePhasePerProviderMetric.setFrameworkException(str);
    } catch (Exception e) {
      Slog.i(
          "ProviderSessionMetric",
          "Unexpected error during candidate exception metric logging: " + e);
    }
  }

  public final void collectAuthEntryUpdate(boolean z, boolean z2, int i) {
    BrowsedAuthenticationMetric usedAuthenticationMetric = getUsedAuthenticationMetric();
    usedAuthenticationMetric.setProviderUid(i);
    if (z) {
      usedAuthenticationMetric.setAuthReturned(false);
      usedAuthenticationMetric.setProviderStatus(
          ProviderStatusForMetrics.QUERY_FAILURE.getMetricCode());
    } else if (z2) {
      usedAuthenticationMetric.setAuthReturned(true);
      usedAuthenticationMetric.setProviderStatus(
          ProviderStatusForMetrics.QUERY_SUCCESS.getMetricCode());
    }
  }

  public final BrowsedAuthenticationMetric getUsedAuthenticationMetric() {
    return (BrowsedAuthenticationMetric) this.mBrowsedAuthenticationMetric.get(r1.size() - 1);
  }

  public void collectCandidateMetricUpdate(boolean z, boolean z2, int i, boolean z3, boolean z4) {
    try {
      if (z3) {
        collectAuthEntryUpdate(z, z2, i);
        return;
      }
      this.mCandidatePhasePerProviderMetric.setPrimary(z4);
      this.mCandidatePhasePerProviderMetric.setCandidateUid(i);
      this.mCandidatePhasePerProviderMetric.setQueryFinishTimeNanoseconds(System.nanoTime());
      if (z) {
        this.mCandidatePhasePerProviderMetric.setQueryReturned(false);
        this.mCandidatePhasePerProviderMetric.setProviderQueryStatus(
            ProviderStatusForMetrics.QUERY_FAILURE.getMetricCode());
      } else if (z2) {
        this.mCandidatePhasePerProviderMetric.setQueryReturned(true);
        this.mCandidatePhasePerProviderMetric.setProviderQueryStatus(
            ProviderStatusForMetrics.QUERY_SUCCESS.getMetricCode());
      }
    } catch (Exception e) {
      Slog.i(
          "ProviderSessionMetric", "Unexpected error during candidate update metric logging: " + e);
    }
  }

  public void collectCandidateMetricSetupViaInitialMetric(InitialPhaseMetric initialPhaseMetric) {
    try {
      this.mCandidatePhasePerProviderMetric.setServiceBeganTimeNanoseconds(
          initialPhaseMetric.getCredentialServiceStartedTimeNanoseconds());
      this.mCandidatePhasePerProviderMetric.setStartQueryTimeNanoseconds(System.nanoTime());
    } catch (Exception e) {
      Slog.i(
          "ProviderSessionMetric", "Unexpected error during candidate setup metric logging: " + e);
    }
  }

  public void collectCandidateEntryMetrics(
      Object obj, boolean z, InitialPhaseMetric initialPhaseMetric) {
    try {
      if (obj instanceof BeginGetCredentialResponse) {
        beginGetCredentialResponseCollectionCandidateEntryMetrics(
            (BeginGetCredentialResponse) obj, z);
      } else if (obj instanceof BeginCreateCredentialResponse) {
        beginCreateCredentialResponseCollectionCandidateEntryMetrics(
            (BeginCreateCredentialResponse) obj, initialPhaseMetric);
      } else {
        Slog.i(
            "ProviderSessionMetric",
            "Your response type is unsupported for candidate metric logging");
      }
    } catch (Exception e) {
      Slog.i(
          "ProviderSessionMetric", "Unexpected error during candidate entry metric logging: " + e);
    }
  }

  public void collectCandidateEntryMetrics(List list) {
    int size = list.size();
    LinkedHashMap linkedHashMap = new LinkedHashMap();
    final LinkedHashMap linkedHashMap2 = new LinkedHashMap();
    linkedHashMap.put(EntryEnum.REMOTE_ENTRY, 0);
    linkedHashMap.put(EntryEnum.CREDENTIAL_ENTRY, Integer.valueOf(size));
    linkedHashMap.put(EntryEnum.ACTION_ENTRY, 0);
    linkedHashMap.put(EntryEnum.AUTHENTICATION_ENTRY, 0);
    list.forEach(
        new Consumer() { // from class:
          // com.android.server.credentials.metrics.ProviderSessionMetric$$ExternalSyntheticLambda0
          @Override // java.util.function.Consumer
          public final void accept(Object obj) {
            ProviderSessionMetric.lambda$collectCandidateEntryMetrics$0(
                linkedHashMap2, (CredentialEntry) obj);
          }
        });
    this.mCandidatePhasePerProviderMetric.setResponseCollective(
        new ResponseCollective(linkedHashMap2, linkedHashMap));
  }

  public static /* synthetic */ void lambda$collectCandidateEntryMetrics$0(
      Map map, CredentialEntry credentialEntry) {
    String generateMetricKey = MetricUtilities.generateMetricKey(credentialEntry.getType(), 20);
    map.put(
        generateMetricKey,
        Integer.valueOf(((Integer) map.getOrDefault(generateMetricKey, 0)).intValue() + 1));
  }

  public void createAuthenticationBrowsingMetric() {
    this.mBrowsedAuthenticationMetric.add(
        new BrowsedAuthenticationMetric(
            this.mCandidatePhasePerProviderMetric.getSessionIdProvider()));
  }

  public final void beginCreateCredentialResponseCollectionCandidateEntryMetrics(
      BeginCreateCredentialResponse beginCreateCredentialResponse,
      InitialPhaseMetric initialPhaseMetric) {
    LinkedHashMap linkedHashMap = new LinkedHashMap();
    List<CreateEntry> createEntries = beginCreateCredentialResponse.getCreateEntries();
    int i = beginCreateCredentialResponse.getRemoteCreateEntry() == null ? 0 : 1;
    int size = createEntries.size();
    linkedHashMap.put(EntryEnum.REMOTE_ENTRY, Integer.valueOf(i));
    linkedHashMap.put(EntryEnum.CREDENTIAL_ENTRY, Integer.valueOf(size));
    LinkedHashMap linkedHashMap2 = new LinkedHashMap();
    String[] uniqueRequestStrings =
        initialPhaseMetric == null ? new String[0] : initialPhaseMetric.getUniqueRequestStrings();
    if (uniqueRequestStrings.length > 0) {
      linkedHashMap2.put(
          uniqueRequestStrings[0], Integer.valueOf(initialPhaseMetric.getUniqueRequestCounts()[0]));
    }
    this.mCandidatePhasePerProviderMetric.setResponseCollective(
        new ResponseCollective(linkedHashMap2, linkedHashMap));
  }

  public final void beginGetCredentialResponseCollectionCandidateEntryMetrics(
      BeginGetCredentialResponse beginGetCredentialResponse, boolean z) {
    LinkedHashMap linkedHashMap = new LinkedHashMap();
    final LinkedHashMap linkedHashMap2 = new LinkedHashMap();
    int size = beginGetCredentialResponse.getCredentialEntries().size();
    int size2 = beginGetCredentialResponse.getActions().size();
    int size3 = beginGetCredentialResponse.getAuthenticationActions().size();
    linkedHashMap.put(
        EntryEnum.REMOTE_ENTRY,
        Integer.valueOf(beginGetCredentialResponse.getRemoteCredentialEntry() != null ? 0 : 1));
    linkedHashMap.put(EntryEnum.CREDENTIAL_ENTRY, Integer.valueOf(size));
    linkedHashMap.put(EntryEnum.ACTION_ENTRY, Integer.valueOf(size2));
    linkedHashMap.put(EntryEnum.AUTHENTICATION_ENTRY, Integer.valueOf(size3));
    beginGetCredentialResponse
        .getCredentialEntries()
        .forEach(
            new Consumer() { // from class:
              // com.android.server.credentials.metrics.ProviderSessionMetric$$ExternalSyntheticLambda1
              @Override // java.util.function.Consumer
              public final void accept(Object obj) {
                ProviderSessionMetric.m6x11667ec1(linkedHashMap2, (CredentialEntry) obj);
              }
            });
    ResponseCollective responseCollective = new ResponseCollective(linkedHashMap2, linkedHashMap);
    if (!z) {
      this.mCandidatePhasePerProviderMetric.setResponseCollective(responseCollective);
    } else {
      List list = this.mBrowsedAuthenticationMetric;
      ((BrowsedAuthenticationMetric) list.get(list.size() - 1))
          .setAuthEntryCollective(responseCollective);
    }
  }

  /* renamed from: lambda$beginGetCredentialResponseCollectionCandidateEntryMetrics$1 */
  public static /* synthetic */ void m6x11667ec1(Map map, CredentialEntry credentialEntry) {
    String generateMetricKey = MetricUtilities.generateMetricKey(credentialEntry.getType(), 20);
    map.put(
        generateMetricKey,
        Integer.valueOf(((Integer) map.getOrDefault(generateMetricKey, 0)).intValue() + 1));
  }
}
