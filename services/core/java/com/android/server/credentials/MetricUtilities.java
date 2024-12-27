package com.android.server.credentials;

import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Slog;

import com.android.internal.util.FrameworkStatsLog;
import com.android.server.WallpaperUpdateReceiver$$ExternalSyntheticOutline0;
import com.android.server.audio.AudioService$$ExternalSyntheticLambda1;
import com.android.server.credentials.metrics.ApiName;
import com.android.server.credentials.metrics.ApiStatus;
import com.android.server.credentials.metrics.BrowsedAuthenticationMetric;
import com.android.server.credentials.metrics.CandidateAggregateMetric;
import com.android.server.credentials.metrics.CandidateBrowsingPhaseMetric;
import com.android.server.credentials.metrics.CandidatePhaseMetric;
import com.android.server.credentials.metrics.ChosenProviderFinalPhaseMetric;
import com.android.server.credentials.metrics.EntryEnum;
import com.android.server.credentials.metrics.InitialPhaseMetric;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public abstract class MetricUtilities {
    public static final int[] DEFAULT_REPEATED_INT_32 = new int[0];

    public static int getMetricTimestampDifferenceMicroseconds(long j, long j2) {
        long j3 = j - j2;
        if (j3 > 2147483647L) {
            Slog.i(
                    "CredentialManager",
                    "Input timestamps are too far apart and unsupported, falling back to default"
                        + " int");
            return -1;
        }
        if (j >= j2) {
            return (int) (j3 / 1000);
        }
        Slog.i(
                "CredentialManager",
                "The timestamps aren't in expected order, falling back to default int");
        return -1;
    }

    public static int getPackageUid(Context context, String str, int i) {
        if (str == null) {
            return -1;
        }
        try {
            return context.getPackageManager()
                    .getPackageUidAsUser(str, PackageManager.PackageInfoFlags.of(0L), i);
        } catch (Throwable th) {
            Slog.i("CredentialManager", "Couldn't find uid for " + str + ": " + th);
            return -1;
        }
    }

    public static void logApiCalledAggregateCandidate(
            CandidateAggregateMetric candidateAggregateMetric, int i) {
        try {
            int i2 = candidateAggregateMetric.mSessionIdProvider;
            boolean z = candidateAggregateMetric.mQueryReturned;
            int i3 = candidateAggregateMetric.mNumProviders;
            int metricTimestampDifferenceMicroseconds =
                    getMetricTimestampDifferenceMicroseconds(
                            candidateAggregateMetric.mMinProviderTimestampNanoseconds,
                            candidateAggregateMetric.mServiceBeganTimeNanoseconds);
            int metricTimestampDifferenceMicroseconds2 =
                    getMetricTimestampDifferenceMicroseconds(
                            candidateAggregateMetric.mMaxProviderTimestampNanoseconds,
                            candidateAggregateMetric.mServiceBeganTimeNanoseconds);
            String[] uniqueResponseStrings =
                    candidateAggregateMetric.mAggregateCollectiveQuery.getUniqueResponseStrings();
            int[] uniqueResponseCounts =
                    candidateAggregateMetric.mAggregateCollectiveQuery.getUniqueResponseCounts();
            int[] uniqueEntries =
                    candidateAggregateMetric.mAggregateCollectiveQuery.getUniqueEntries();
            int[] uniqueEntryCounts =
                    candidateAggregateMetric.mAggregateCollectiveQuery.getUniqueEntryCounts();
            int i4 = candidateAggregateMetric.mTotalQueryFailures;
            String[] strArr =
                    new String
                            [((LinkedHashMap) candidateAggregateMetric.mExceptionCountQuery)
                                    .keySet()
                                    .size()];
            ((LinkedHashMap) candidateAggregateMetric.mExceptionCountQuery)
                    .keySet()
                    .toArray(strArr);
            int[] array =
                    ((LinkedHashMap) candidateAggregateMetric.mExceptionCountQuery)
                            .values().stream()
                                    .mapToInt(new AudioService$$ExternalSyntheticLambda1(2))
                                    .toArray();
            String[] uniqueResponseStrings2 =
                    candidateAggregateMetric.mAggregateCollectiveAuth.getUniqueResponseStrings();
            int[] uniqueResponseCounts2 =
                    candidateAggregateMetric.mAggregateCollectiveAuth.getUniqueResponseCounts();
            int[] uniqueEntries2 =
                    candidateAggregateMetric.mAggregateCollectiveAuth.getUniqueEntries();
            int[] uniqueEntryCounts2 =
                    candidateAggregateMetric.mAggregateCollectiveAuth.getUniqueEntryCounts();
            String[] strArr2 =
                    new String
                            [((LinkedHashMap) candidateAggregateMetric.mExceptionCountAuth)
                                    .keySet()
                                    .size()];
            ((LinkedHashMap) candidateAggregateMetric.mExceptionCountAuth)
                    .keySet()
                    .toArray(strArr2);
            FrameworkStatsLog.write(
                    FrameworkStatsLog.CREDENTIAL_MANAGER_TOTAL_REPORTED,
                    i2,
                    i,
                    z,
                    i3,
                    metricTimestampDifferenceMicroseconds,
                    metricTimestampDifferenceMicroseconds2,
                    uniqueResponseStrings,
                    uniqueResponseCounts,
                    uniqueEntries,
                    uniqueEntryCounts,
                    i4,
                    strArr,
                    array,
                    uniqueResponseStrings2,
                    uniqueResponseCounts2,
                    uniqueEntries2,
                    uniqueEntryCounts2,
                    0,
                    strArr2,
                    ((LinkedHashMap) candidateAggregateMetric.mExceptionCountAuth)
                            .values().stream()
                                    .mapToInt(new AudioService$$ExternalSyntheticLambda1(2))
                                    .toArray(),
                    candidateAggregateMetric.mNumAuthEntriesTapped,
                    candidateAggregateMetric.mAuthReturned);
        } catch (Exception e) {
            WallpaperUpdateReceiver$$ExternalSyntheticOutline0.m(
                    e,
                    "Unexpected error during total candidate metric logging: ",
                    "CredentialManager");
        }
    }

    public static void logApiCalledAuthenticationMetric(
            BrowsedAuthenticationMetric browsedAuthenticationMetric, int i) {
        try {
            FrameworkStatsLog.write(
                    FrameworkStatsLog.CREDENTIAL_MANAGER_AUTH_CLICK_REPORTED,
                    browsedAuthenticationMetric.mSessionIdProvider,
                    i,
                    browsedAuthenticationMetric.mProviderUid,
                    browsedAuthenticationMetric.mAuthEntryCollective.getUniqueResponseStrings(),
                    browsedAuthenticationMetric.mAuthEntryCollective.getUniqueResponseCounts(),
                    browsedAuthenticationMetric.mAuthEntryCollective.getUniqueEntries(),
                    browsedAuthenticationMetric.mAuthEntryCollective.getUniqueEntryCounts(),
                    browsedAuthenticationMetric.mFrameworkException,
                    browsedAuthenticationMetric.mHasException,
                    browsedAuthenticationMetric.mProviderStatus,
                    browsedAuthenticationMetric.mAuthReturned);
        } catch (Exception e) {
            WallpaperUpdateReceiver$$ExternalSyntheticOutline0.m(
                    e,
                    "Unexpected error during candidate auth metric logging: ",
                    "CredentialManager");
        }
    }

    public static void logApiCalledCandidateGetMetric(int i, Map map) {
        try {
            Iterator it = map.values().iterator();
            while (it.hasNext()) {
                CandidatePhaseMetric candidatePhaseMetric =
                        ((ProviderSession) it.next())
                                .mProviderSessionMetric
                                .mCandidatePhasePerProviderMetric;
                FrameworkStatsLog.write(
                        FrameworkStatsLog.CREDENTIAL_MANAGER_GET_REPORTED,
                        candidatePhaseMetric.mSessionIdProvider,
                        i,
                        candidatePhaseMetric.mCandidateUid,
                        candidatePhaseMetric.mResponseCollective.getUniqueResponseStrings(),
                        candidatePhaseMetric.mResponseCollective.getUniqueResponseCounts());
            }
        } catch (Exception e) {
            WallpaperUpdateReceiver$$ExternalSyntheticOutline0.m(
                    e,
                    "Unexpected error during candidate get metric logging: ",
                    "CredentialManager");
        }
    }

    public static void logApiCalledCandidatePhase(
            Map map, int i, InitialPhaseMetric initialPhaseMetric) {
        int i2;
        int i3;
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
            boolean z = false;
            int i4 = 0;
            int i5 = -1;
            while (it.hasNext()) {
                CandidatePhaseMetric candidatePhaseMetric =
                        ((ProviderSession) it.next())
                                .mProviderSessionMetric
                                .mCandidatePhasePerProviderMetric;
                Iterator it2 = it;
                if (i5 == -1) {
                    i5 = candidatePhaseMetric.mSessionIdProvider;
                }
                if (!z) {
                    z = candidatePhaseMetric.mQueryReturned;
                }
                iArr[i4] = candidatePhaseMetric.mCandidateUid;
                int i6 = i5;
                long j = candidatePhaseMetric.mStartQueryTimeNanoseconds;
                String[] strArr2 = strArr;
                int[] iArr11 = iArr10;
                long j2 = candidatePhaseMetric.mServiceBeganTimeNanoseconds;
                int[] iArr12 = iArr;
                boolean[] zArr3 = zArr2;
                if (j < j2) {
                    Slog.i(
                            "CandidateProviderMetric",
                            "The timestamp is before service started, falling back to default int");
                    i2 = -1;
                } else {
                    i2 = (int) ((j - j2) / 1000);
                }
                iArr2[i4] = i2;
                long j3 = candidatePhaseMetric.mQueryFinishTimeNanoseconds;
                long j4 = candidatePhaseMetric.mServiceBeganTimeNanoseconds;
                if (j3 < j4) {
                    Slog.i(
                            "CandidateProviderMetric",
                            "The timestamp is before service started, falling back to default int");
                    i3 = -1;
                } else {
                    i3 = (int) ((j3 - j4) / 1000);
                }
                iArr3[i4] = i3;
                iArr4[i4] = candidatePhaseMetric.mProviderQueryStatus;
                zArr[i4] = candidatePhaseMetric.mHasException;
                iArr5[i4] =
                        candidatePhaseMetric.mResponseCollective.mEntryCounts.values().stream()
                                .mapToInt(new AudioService$$ExternalSyntheticLambda1(2))
                                .sum();
                iArr6[i4] =
                        candidatePhaseMetric.mResponseCollective.getCountForEntry(
                                EntryEnum.CREDENTIAL_ENTRY);
                iArr7[i4] =
                        candidatePhaseMetric.mResponseCollective.getUniqueResponseStrings().length;
                iArr8[i4] =
                        candidatePhaseMetric.mResponseCollective.getCountForEntry(
                                EntryEnum.ACTION_ENTRY);
                iArr9[i4] =
                        candidatePhaseMetric.mResponseCollective.getCountForEntry(
                                EntryEnum.AUTHENTICATION_ENTRY);
                iArr11[i4] =
                        candidatePhaseMetric.mResponseCollective.getCountForEntry(
                                EntryEnum.REMOTE_ENTRY);
                strArr2[i4] = candidatePhaseMetric.mFrameworkException;
                zArr3[i4] = candidatePhaseMetric.mIsPrimary;
                i4++;
                it = it2;
                i5 = i6;
                iArr10 = iArr11;
                strArr = strArr2;
                iArr = iArr12;
                zArr2 = zArr3;
            }
            int[] iArr13 = iArr;
            boolean z2 = z;
            FrameworkStatsLog.write(
                    FrameworkStatsLog.CREDENTIAL_MANAGER_CANDIDATE_PHASE_REPORTED,
                    i5,
                    i,
                    z2,
                    iArr13,
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
                    initialPhaseMetric.mOriginSpecified,
                    initialPhaseMetric.getUniqueRequestStrings(),
                    initialPhaseMetric.getUniqueRequestCounts(),
                    initialPhaseMetric.mApiName,
                    zArr2);
        } catch (Exception e) {
            WallpaperUpdateReceiver$$ExternalSyntheticOutline0.m(
                    e,
                    "Unexpected error during candidate provider uid metric emit: ",
                    "CredentialManager");
        }
    }

    public static void logApiCalledFinalPhase(
            ChosenProviderFinalPhaseMetric chosenProviderFinalPhaseMetric,
            List list,
            int i,
            int i2) {
        try {
            ArrayList<CandidateBrowsingPhaseMetric> arrayList = (ArrayList) list;
            int size = arrayList.size();
            int[] iArr = new int[size];
            int[] iArr2 = new int[size];
            int i3 = 0;
            for (CandidateBrowsingPhaseMetric candidateBrowsingPhaseMetric : arrayList) {
                iArr[i3] = candidateBrowsingPhaseMetric.mEntryEnum;
                iArr2[i3] = candidateBrowsingPhaseMetric.mProviderUid;
                i3++;
            }
            FrameworkStatsLog.write(
                    FrameworkStatsLog.CREDENTIAL_MANAGER_FINAL_PHASE_REPORTED,
                    chosenProviderFinalPhaseMetric.mSessionIdProvider,
                    i2,
                    chosenProviderFinalPhaseMetric.mUiReturned,
                    chosenProviderFinalPhaseMetric.mChosenUid,
                    chosenProviderFinalPhaseMetric.getTimestampFromReferenceStartMicroseconds(
                            chosenProviderFinalPhaseMetric.mQueryStartTimeNanoseconds),
                    chosenProviderFinalPhaseMetric.getTimestampFromReferenceStartMicroseconds(
                            chosenProviderFinalPhaseMetric.mQueryEndTimeNanoseconds),
                    chosenProviderFinalPhaseMetric.getTimestampFromReferenceStartMicroseconds(
                            chosenProviderFinalPhaseMetric.mUiCallStartTimeNanoseconds),
                    chosenProviderFinalPhaseMetric.getTimestampFromReferenceStartMicroseconds(
                            chosenProviderFinalPhaseMetric.mUiCallEndTimeNanoseconds),
                    chosenProviderFinalPhaseMetric.getTimestampFromReferenceStartMicroseconds(
                            chosenProviderFinalPhaseMetric.mFinalFinishTimeNanoseconds),
                    chosenProviderFinalPhaseMetric.mChosenProviderStatus,
                    chosenProviderFinalPhaseMetric.mHasException,
                    DEFAULT_REPEATED_INT_32,
                    -1,
                    -1,
                    -1,
                    -1,
                    -1,
                    iArr,
                    iArr2,
                    i,
                    chosenProviderFinalPhaseMetric.mResponseCollective.getUniqueEntries(),
                    chosenProviderFinalPhaseMetric.mResponseCollective.getUniqueEntryCounts(),
                    chosenProviderFinalPhaseMetric.mResponseCollective.getUniqueResponseStrings(),
                    chosenProviderFinalPhaseMetric.mResponseCollective.getUniqueResponseCounts(),
                    chosenProviderFinalPhaseMetric.mFrameworkException,
                    chosenProviderFinalPhaseMetric.mIsPrimary);
        } catch (Exception e) {
            WallpaperUpdateReceiver$$ExternalSyntheticOutline0.m(
                    e, "Unexpected error during final provider uid emit: ", "CredentialManager");
        }
    }

    public static void logApiCalledInitialPhase(InitialPhaseMetric initialPhaseMetric, int i) {
        try {
            FrameworkStatsLog.write(
                    FrameworkStatsLog.CREDENTIAL_MANAGER_INIT_PHASE_REPORTED,
                    initialPhaseMetric.mApiName,
                    initialPhaseMetric.mCallerUid,
                    initialPhaseMetric.mSessionIdCaller,
                    i,
                    initialPhaseMetric.mCredentialServiceStartedTimeNanoseconds,
                    initialPhaseMetric.mRequestCounts.size(),
                    initialPhaseMetric.getUniqueRequestStrings(),
                    initialPhaseMetric.getUniqueRequestCounts(),
                    initialPhaseMetric.mOriginSpecified,
                    initialPhaseMetric.mAutofillSessionId,
                    initialPhaseMetric.mAutofillRequestId);
        } catch (Exception e) {
            WallpaperUpdateReceiver$$ExternalSyntheticOutline0.m(
                    e, "Unexpected error during initial metric emit: ", "CredentialManager");
        }
    }

    public static void logApiCalledNoUidFinal(
            ChosenProviderFinalPhaseMetric chosenProviderFinalPhaseMetric,
            List list,
            int i,
            int i2) {
        try {
            ArrayList arrayList = (ArrayList) list;
            int size = arrayList.size();
            int[] iArr = new int[size];
            int[] iArr2 = new int[size];
            Iterator it = arrayList.iterator();
            int i3 = 0;
            while (it.hasNext()) {
                iArr[i3] = ((CandidateBrowsingPhaseMetric) it.next()).mEntryEnum;
                iArr2[i3] = -1;
                i3++;
            }
            FrameworkStatsLog.write(
                    FrameworkStatsLog.CREDENTIAL_MANAGER_FINALNOUID_REPORTED,
                    chosenProviderFinalPhaseMetric.mSessionIdCaller,
                    i2,
                    chosenProviderFinalPhaseMetric.mUiReturned,
                    chosenProviderFinalPhaseMetric.getTimestampFromReferenceStartMicroseconds(
                            chosenProviderFinalPhaseMetric.mQueryStartTimeNanoseconds),
                    chosenProviderFinalPhaseMetric.getTimestampFromReferenceStartMicroseconds(
                            chosenProviderFinalPhaseMetric.mQueryEndTimeNanoseconds),
                    chosenProviderFinalPhaseMetric.getTimestampFromReferenceStartMicroseconds(
                            chosenProviderFinalPhaseMetric.mUiCallStartTimeNanoseconds),
                    chosenProviderFinalPhaseMetric.getTimestampFromReferenceStartMicroseconds(
                            chosenProviderFinalPhaseMetric.mUiCallEndTimeNanoseconds),
                    chosenProviderFinalPhaseMetric.getTimestampFromReferenceStartMicroseconds(
                            chosenProviderFinalPhaseMetric.mFinalFinishTimeNanoseconds),
                    chosenProviderFinalPhaseMetric.mChosenProviderStatus,
                    chosenProviderFinalPhaseMetric.mHasException,
                    chosenProviderFinalPhaseMetric.mResponseCollective.getUniqueEntries(),
                    chosenProviderFinalPhaseMetric.mResponseCollective.getUniqueEntryCounts(),
                    chosenProviderFinalPhaseMetric.mResponseCollective.getUniqueResponseStrings(),
                    chosenProviderFinalPhaseMetric.mResponseCollective.getUniqueResponseCounts(),
                    chosenProviderFinalPhaseMetric.mFrameworkException,
                    iArr,
                    iArr2,
                    i,
                    chosenProviderFinalPhaseMetric.mIsPrimary,
                    chosenProviderFinalPhaseMetric.mOemUiUid,
                    chosenProviderFinalPhaseMetric.mFallbackUiUid,
                    chosenProviderFinalPhaseMetric.mOemUiUsageStatus.getLoggingInt());
        } catch (Exception e) {
            WallpaperUpdateReceiver$$ExternalSyntheticOutline0.m(
                    e,
                    "Unexpected error during final no uid metric logging: ",
                    "CredentialManager");
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
            WallpaperUpdateReceiver$$ExternalSyntheticOutline0.m(
                    e, "Unexpected error during simple v2 metric logging: ", "CredentialManager");
        }
    }
}
