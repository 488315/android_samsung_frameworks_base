package com.android.server.credentials;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Slog;
import com.android.internal.util.FrameworkStatsLog;
import com.android.server.credentials.metrics.ApiName;
import com.android.server.credentials.metrics.ApiStatus;
import com.android.server.credentials.metrics.BrowsedAuthenticationMetric;
import com.android.server.credentials.metrics.CandidateAggregateMetric;
import com.android.server.credentials.metrics.CandidateBrowsingPhaseMetric;
import com.android.server.credentials.metrics.CandidatePhaseMetric;
import com.android.server.credentials.metrics.ChosenProviderFinalPhaseMetric;
import com.android.server.credentials.metrics.EntryEnum;
import com.android.server.credentials.metrics.InitialPhaseMetric;
import java.security.SecureRandom;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* loaded from: classes.dex */
public abstract class MetricUtilities {
  public static final int[] DEFAULT_REPEATED_INT_32 = new int[0];
  public static final String[] DEFAULT_REPEATED_STR = new String[0];
  public static final boolean[] DEFAULT_REPEATED_BOOL = new boolean[0];

  public static int getPackageUid(Context context, ComponentName componentName) {
    try {
      return context
          .getPackageManager()
          .getApplicationInfo(
              componentName.getPackageName(), PackageManager.ApplicationInfoFlags.of(0L))
          .uid;
    } catch (Throwable unused) {
      Slog.i("MetricUtilities", "Couldn't find required uid");
      return -1;
    }
  }

  public static int getHighlyUniqueInteger() {
    return new SecureRandom().nextInt();
  }

  public static int getMetricTimestampDifferenceMicroseconds(long j, long j2) {
    long j3 = j - j2;
    if (j3 > 2147483647L) {
      Slog.i(
          "MetricUtilities",
          "Input timestamps are too far apart and unsupported, falling back to default int");
      return -1;
    }
    if (j < j2) {
      Slog.i(
          "MetricUtilities",
          "The timestamps aren't in expected order, falling back to default int");
      return -1;
    }
    return (int) (j3 / 1000);
  }

  public static String generateMetricKey(String str, int i) {
    return str.substring(str.length() - i);
  }

  public static void logApiCalledFinalPhase(
      ChosenProviderFinalPhaseMetric chosenProviderFinalPhaseMetric, List list, int i, int i2) {
    try {
      int size = list.size();
      int[] iArr = new int[size];
      int[] iArr2 = new int[size];
      Iterator it = list.iterator();
      int i3 = 0;
      while (it.hasNext()) {
        CandidateBrowsingPhaseMetric candidateBrowsingPhaseMetric =
            (CandidateBrowsingPhaseMetric) it.next();
        iArr[i3] = candidateBrowsingPhaseMetric.getEntryEnum();
        iArr2[i3] = candidateBrowsingPhaseMetric.getProviderUid();
        i3++;
      }
      FrameworkStatsLog.write(
          FrameworkStatsLog.CREDENTIAL_MANAGER_FINAL_PHASE_REPORTED,
          chosenProviderFinalPhaseMetric.getSessionIdProvider(),
          i2,
          chosenProviderFinalPhaseMetric.isUiReturned(),
          chosenProviderFinalPhaseMetric.getChosenUid(),
          chosenProviderFinalPhaseMetric.getTimestampFromReferenceStartMicroseconds(
              chosenProviderFinalPhaseMetric.getQueryStartTimeNanoseconds()),
          chosenProviderFinalPhaseMetric.getTimestampFromReferenceStartMicroseconds(
              chosenProviderFinalPhaseMetric.getQueryEndTimeNanoseconds()),
          chosenProviderFinalPhaseMetric.getTimestampFromReferenceStartMicroseconds(
              chosenProviderFinalPhaseMetric.getUiCallStartTimeNanoseconds()),
          chosenProviderFinalPhaseMetric.getTimestampFromReferenceStartMicroseconds(
              chosenProviderFinalPhaseMetric.getUiCallEndTimeNanoseconds()),
          chosenProviderFinalPhaseMetric.getTimestampFromReferenceStartMicroseconds(
              chosenProviderFinalPhaseMetric.getFinalFinishTimeNanoseconds()),
          chosenProviderFinalPhaseMetric.getChosenProviderStatus(),
          chosenProviderFinalPhaseMetric.isHasException(),
          DEFAULT_REPEATED_INT_32,
          -1,
          -1,
          -1,
          -1,
          -1,
          iArr,
          iArr2,
          i,
          chosenProviderFinalPhaseMetric.getResponseCollective().getUniqueEntries(),
          chosenProviderFinalPhaseMetric.getResponseCollective().getUniqueEntryCounts(),
          chosenProviderFinalPhaseMetric.getResponseCollective().getUniqueResponseStrings(),
          chosenProviderFinalPhaseMetric.getResponseCollective().getUniqueResponseCounts(),
          chosenProviderFinalPhaseMetric.getFrameworkException(),
          chosenProviderFinalPhaseMetric.isPrimary());
    } catch (Exception e) {
      Slog.w("MetricUtilities", "Unexpected error during final provider uid emit: " + e);
    }
  }

  public static void logApiCalledAuthenticationMetric(
      BrowsedAuthenticationMetric browsedAuthenticationMetric, int i) {
    try {
      FrameworkStatsLog.write(
          FrameworkStatsLog.CREDENTIAL_MANAGER_AUTH_CLICK_REPORTED,
          browsedAuthenticationMetric.getSessionIdProvider(),
          i,
          browsedAuthenticationMetric.getProviderUid(),
          browsedAuthenticationMetric.getAuthEntryCollective().getUniqueResponseStrings(),
          browsedAuthenticationMetric.getAuthEntryCollective().getUniqueResponseCounts(),
          browsedAuthenticationMetric.getAuthEntryCollective().getUniqueEntries(),
          browsedAuthenticationMetric.getAuthEntryCollective().getUniqueEntryCounts(),
          browsedAuthenticationMetric.getFrameworkException(),
          browsedAuthenticationMetric.isHasException(),
          browsedAuthenticationMetric.getProviderStatus(),
          browsedAuthenticationMetric.isAuthReturned());
    } catch (Exception e) {
      Slog.w("MetricUtilities", "Unexpected error during candidate auth metric logging: " + e);
    }
  }

  public static void logApiCalledCandidateGetMetric(Map map, int i) {
    try {
      Iterator it = map.values().iterator();
      while (it.hasNext()) {
        CandidatePhaseMetric candidatePhasePerProviderMetric =
            ((ProviderSession) it.next())
                .getProviderSessionMetric()
                .getCandidatePhasePerProviderMetric();
        FrameworkStatsLog.write(
            FrameworkStatsLog.CREDENTIAL_MANAGER_GET_REPORTED,
            candidatePhasePerProviderMetric.getSessionIdProvider(),
            i,
            candidatePhasePerProviderMetric.getCandidateUid(),
            candidatePhasePerProviderMetric.getResponseCollective().getUniqueResponseStrings(),
            candidatePhasePerProviderMetric.getResponseCollective().getUniqueResponseCounts());
      }
    } catch (Exception e) {
      Slog.w("MetricUtilities", "Unexpected error during candidate get metric logging: " + e);
    }
  }

  public static void logApiCalledCandidatePhase(
      Map map, int i, InitialPhaseMetric initialPhaseMetric) {
    try {
      Collection values = map.values();
      int size = values.size();
      int[] iArr = new int[size];
      int[] iArr2 = new int[size];
      int[] iArr3 = new int[size];
      int[] iArr4 = new int[size];
      boolean[] zArr = new boolean[size];
      int[] iArr5 = new int[size];
      int[] iArr6 = new int[size];
      int[] iArr7 = new int[size];
      int[] iArr8 = new int[size];
      int[] iArr9 = new int[size];
      int[] iArr10 = new int[size];
      String[] strArr = new String[size];
      boolean[] zArr2 = new boolean[size];
      Iterator it = values.iterator();
      int i2 = 0;
      boolean z = false;
      int i3 = -1;
      while (it.hasNext()) {
        CandidatePhaseMetric candidatePhasePerProviderMetric =
            ((ProviderSession) it.next())
                .mProviderSessionMetric.getCandidatePhasePerProviderMetric();
        Iterator it2 = it;
        if (i3 == -1) {
          i3 = candidatePhasePerProviderMetric.getSessionIdProvider();
        }
        if (!z) {
          z = candidatePhasePerProviderMetric.isQueryReturned();
        }
        iArr[i2] = candidatePhasePerProviderMetric.getCandidateUid();
        iArr2[i2] =
            candidatePhasePerProviderMetric.getTimestampFromReferenceStartMicroseconds(
                candidatePhasePerProviderMetric.getStartQueryTimeNanoseconds());
        iArr3[i2] =
            candidatePhasePerProviderMetric.getTimestampFromReferenceStartMicroseconds(
                candidatePhasePerProviderMetric.getQueryFinishTimeNanoseconds());
        iArr4[i2] = candidatePhasePerProviderMetric.getProviderQueryStatus();
        zArr[i2] = candidatePhasePerProviderMetric.isHasException();
        iArr5[i2] = candidatePhasePerProviderMetric.getResponseCollective().getNumEntriesTotal();
        iArr6[i2] =
            candidatePhasePerProviderMetric
                .getResponseCollective()
                .getCountForEntry(EntryEnum.CREDENTIAL_ENTRY);
        iArr7[i2] =
            candidatePhasePerProviderMetric
                .getResponseCollective()
                .getUniqueResponseStrings()
                .length;
        iArr8[i2] =
            candidatePhasePerProviderMetric
                .getResponseCollective()
                .getCountForEntry(EntryEnum.ACTION_ENTRY);
        iArr9[i2] =
            candidatePhasePerProviderMetric
                .getResponseCollective()
                .getCountForEntry(EntryEnum.AUTHENTICATION_ENTRY);
        iArr10[i2] =
            candidatePhasePerProviderMetric
                .getResponseCollective()
                .getCountForEntry(EntryEnum.REMOTE_ENTRY);
        strArr[i2] = candidatePhasePerProviderMetric.getFrameworkException();
        zArr2[i2] = candidatePhasePerProviderMetric.isPrimary();
        i2++;
        it = it2;
        i3 = i3;
      }
      FrameworkStatsLog.write(
          FrameworkStatsLog.CREDENTIAL_MANAGER_CANDIDATE_PHASE_REPORTED,
          i3,
          i,
          z,
          iArr,
          iArr2,
          iArr3,
          iArr4,
          zArr,
          iArr5,
          iArr8,
          iArr6,
          iArr7,
          iArr10,
          iArr9,
          strArr,
          initialPhaseMetric.isOriginSpecified(),
          initialPhaseMetric.getUniqueRequestStrings(),
          initialPhaseMetric.getUniqueRequestCounts(),
          initialPhaseMetric.getApiName(),
          zArr2);
    } catch (Exception e) {
      Slog.w("MetricUtilities", "Unexpected error during candidate provider uid metric emit: " + e);
    }
  }

  public static void logApiCalledSimpleV2(ApiName apiName, ApiStatus apiStatus, int i) {
    try {
      FrameworkStatsLog.write(
          FrameworkStatsLog.CREDENTIAL_MANAGER_APIV2_CALLED,
          apiName.getMetricCode(),
          i,
          apiStatus.getMetricCode());
    } catch (Exception e) {
      Slog.w("MetricUtilities", "Unexpected error during simple v2 metric logging: " + e);
    }
  }

  public static void logApiCalledInitialPhase(InitialPhaseMetric initialPhaseMetric, int i) {
    try {
      FrameworkStatsLog.write(
          FrameworkStatsLog.CREDENTIAL_MANAGER_INIT_PHASE_REPORTED,
          initialPhaseMetric.getApiName(),
          initialPhaseMetric.getCallerUid(),
          initialPhaseMetric.getSessionIdCaller(),
          i,
          initialPhaseMetric.getCredentialServiceStartedTimeNanoseconds(),
          initialPhaseMetric.getCountRequestClassType(),
          initialPhaseMetric.getUniqueRequestStrings(),
          initialPhaseMetric.getUniqueRequestCounts(),
          initialPhaseMetric.isOriginSpecified());
    } catch (Exception e) {
      Slog.w("MetricUtilities", "Unexpected error during initial metric emit: " + e);
    }
  }

  public static void logApiCalledAggregateCandidate(
      CandidateAggregateMetric candidateAggregateMetric, int i) {
    try {
      FrameworkStatsLog.write(
          FrameworkStatsLog.CREDENTIAL_MANAGER_TOTAL_REPORTED,
          candidateAggregateMetric.getSessionIdProvider(),
          i,
          candidateAggregateMetric.isQueryReturned(),
          candidateAggregateMetric.getNumProviders(),
          getMetricTimestampDifferenceMicroseconds(
              candidateAggregateMetric.getMinProviderTimestampNanoseconds(),
              candidateAggregateMetric.getServiceBeganTimeNanoseconds()),
          getMetricTimestampDifferenceMicroseconds(
              candidateAggregateMetric.getMaxProviderTimestampNanoseconds(),
              candidateAggregateMetric.getServiceBeganTimeNanoseconds()),
          candidateAggregateMetric.getAggregateCollectiveQuery().getUniqueResponseStrings(),
          candidateAggregateMetric.getAggregateCollectiveQuery().getUniqueResponseCounts(),
          candidateAggregateMetric.getAggregateCollectiveQuery().getUniqueEntries(),
          candidateAggregateMetric.getAggregateCollectiveQuery().getUniqueEntryCounts(),
          candidateAggregateMetric.getTotalQueryFailures(),
          candidateAggregateMetric.getUniqueExceptionStringsQuery(),
          candidateAggregateMetric.getUniqueExceptionCountsQuery(),
          candidateAggregateMetric.getAggregateCollectiveAuth().getUniqueResponseStrings(),
          candidateAggregateMetric.getAggregateCollectiveAuth().getUniqueResponseCounts(),
          candidateAggregateMetric.getAggregateCollectiveAuth().getUniqueEntries(),
          candidateAggregateMetric.getAggregateCollectiveAuth().getUniqueEntryCounts(),
          candidateAggregateMetric.getTotalAuthFailures(),
          candidateAggregateMetric.getUniqueExceptionStringsAuth(),
          candidateAggregateMetric.getUniqueExceptionCountsAuth(),
          candidateAggregateMetric.getNumAuthEntriesTapped(),
          candidateAggregateMetric.isAuthReturned());
    } catch (Exception e) {
      Slog.w("MetricUtilities", "Unexpected error during total candidate metric logging: " + e);
    }
  }

  public static void logApiCalledNoUidFinal(
      ChosenProviderFinalPhaseMetric chosenProviderFinalPhaseMetric, List list, int i, int i2) {
    try {
      int size = list.size();
      int[] iArr = new int[size];
      int[] iArr2 = new int[size];
      Iterator it = list.iterator();
      int i3 = 0;
      while (it.hasNext()) {
        CandidateBrowsingPhaseMetric candidateBrowsingPhaseMetric =
            (CandidateBrowsingPhaseMetric) it.next();
        iArr[i3] = candidateBrowsingPhaseMetric.getEntryEnum();
        iArr2[i3] = candidateBrowsingPhaseMetric.getProviderUid();
        i3++;
      }
      FrameworkStatsLog.write(
          FrameworkStatsLog.CREDENTIAL_MANAGER_FINALNOUID_REPORTED,
          chosenProviderFinalPhaseMetric.getSessionIdCaller(),
          i2,
          chosenProviderFinalPhaseMetric.isUiReturned(),
          chosenProviderFinalPhaseMetric.getTimestampFromReferenceStartMicroseconds(
              chosenProviderFinalPhaseMetric.getQueryStartTimeNanoseconds()),
          chosenProviderFinalPhaseMetric.getTimestampFromReferenceStartMicroseconds(
              chosenProviderFinalPhaseMetric.getQueryEndTimeNanoseconds()),
          chosenProviderFinalPhaseMetric.getTimestampFromReferenceStartMicroseconds(
              chosenProviderFinalPhaseMetric.getUiCallStartTimeNanoseconds()),
          chosenProviderFinalPhaseMetric.getTimestampFromReferenceStartMicroseconds(
              chosenProviderFinalPhaseMetric.getUiCallEndTimeNanoseconds()),
          chosenProviderFinalPhaseMetric.getTimestampFromReferenceStartMicroseconds(
              chosenProviderFinalPhaseMetric.getFinalFinishTimeNanoseconds()),
          chosenProviderFinalPhaseMetric.getChosenProviderStatus(),
          chosenProviderFinalPhaseMetric.isHasException(),
          chosenProviderFinalPhaseMetric.getResponseCollective().getUniqueEntries(),
          chosenProviderFinalPhaseMetric.getResponseCollective().getUniqueEntryCounts(),
          chosenProviderFinalPhaseMetric.getResponseCollective().getUniqueResponseStrings(),
          chosenProviderFinalPhaseMetric.getResponseCollective().getUniqueResponseCounts(),
          chosenProviderFinalPhaseMetric.getFrameworkException(),
          iArr,
          iArr2,
          i,
          chosenProviderFinalPhaseMetric.isPrimary());
    } catch (Exception e) {
      Slog.w("MetricUtilities", "Unexpected error during final no uid metric logging: " + e);
    }
  }
}
