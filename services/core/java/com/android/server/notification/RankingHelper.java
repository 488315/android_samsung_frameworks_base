package com.android.server.notification;

import android.content.Context;
import android.os.Message;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Slog;

import com.android.internal.compat.IPlatformCompat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.concurrent.TimeUnit;

public final class RankingHelper {
    public final Context mContext;
    public final Comparator mPreliminaryComparator;
    public final RankingHandler mRankingHandler;
    public final NotificationSignalExtractor[] mSignalExtractors;
    public final GlobalSortKeyComparator mFinalComparator = new GlobalSortKeyComparator();
    public final ArrayMap mProxyByGroupTmp = new ArrayMap();

    public RankingHelper(
            Context context,
            RankingHandler rankingHandler,
            RankingConfig rankingConfig,
            ZenModeHelper zenModeHelper,
            NotificationUsageStats notificationUsageStats,
            String[] strArr,
            IPlatformCompat iPlatformCompat) {
        NotificationSignalExtractor notificationSignalExtractor;
        this.mContext = context;
        this.mRankingHandler = rankingHandler;
        if (android.app.Flags.sortSectionByTime()) {
            this.mPreliminaryComparator = new NotificationTimeComparator();
        } else {
            this.mPreliminaryComparator = new NotificationComparator(context);
        }
        int length = strArr.length;
        this.mSignalExtractors = new NotificationSignalExtractor[length];
        for (int i = 0; i < length; i++) {
            try {
                notificationSignalExtractor =
                        (NotificationSignalExtractor)
                                this.mContext.getClassLoader().loadClass(strArr[i]).newInstance();
                notificationSignalExtractor.initialize(this.mContext, notificationUsageStats);
                notificationSignalExtractor.setConfig(rankingConfig);
                notificationSignalExtractor.setZenHelper(zenModeHelper);
            } catch (ClassNotFoundException e) {
                Slog.w("RankingHelper", "Couldn't find extractor " + strArr[i] + ".", e);
            } catch (IllegalAccessException e2) {
                Slog.w("RankingHelper", "Problem accessing extractor " + strArr[i] + ".", e2);
            } catch (InstantiationException e3) {
                Slog.w("RankingHelper", "Couldn't instantiate extractor " + strArr[i] + ".", e3);
            }
            if (!android.app.Flags.restrictAudioAttributesAlarm()) {
                if (!android.app.Flags.restrictAudioAttributesMedia()) {
                    if (android.app.Flags.restrictAudioAttributesCall()) {}
                    this.mSignalExtractors[i] = notificationSignalExtractor;
                }
            }
            notificationSignalExtractor.setCompatChangeLogger(iPlatformCompat);
            this.mSignalExtractors[i] = notificationSignalExtractor;
        }
    }

    public final void extractSignals(NotificationRecord notificationRecord) {
        for (NotificationSignalExtractor notificationSignalExtractor : this.mSignalExtractors) {
            try {
                RankingReconsideration process =
                        notificationSignalExtractor.process(notificationRecord);
                if (process != null) {
                    NotificationManagerService.RankingHandlerWorker rankingHandlerWorker =
                            (NotificationManagerService.RankingHandlerWorker) this.mRankingHandler;
                    rankingHandlerWorker.getClass();
                    Message obtain = Message.obtain(rankingHandlerWorker, 1000, process);
                    TimeUnit timeUnit = TimeUnit.MILLISECONDS;
                    rankingHandlerWorker.sendMessageDelayed(
                            obtain, timeUnit.convert(process.mDelay, timeUnit));
                }
            } catch (Throwable th) {
                Slog.w("RankingHelper", "NotificationSignalExtractor failed.", th);
            }
        }
    }

    public final NotificationSignalExtractor findExtractor(Class cls) {
        for (NotificationSignalExtractor notificationSignalExtractor : this.mSignalExtractors) {
            if (cls.equals(notificationSignalExtractor.getClass())) {
                return notificationSignalExtractor;
            }
        }
        return null;
    }

    public final void sort(ArrayList arrayList) {
        String str;
        int size = arrayList.size();
        for (int i = size - 1; i >= 0; i--) {
            ((NotificationRecord) arrayList.get(i)).mGlobalSortKey = null;
        }
        if (android.app.Flags.sortSectionByTime()) {
            arrayList.sort(this.mPreliminaryComparator);
        } else {
            synchronized (((NotificationComparator) this.mPreliminaryComparator).mStateLock) {
                arrayList.sort(this.mPreliminaryComparator);
            }
        }
        synchronized (this.mProxyByGroupTmp) {
            for (int i2 = 0; i2 < size; i2++) {
                try {
                    NotificationRecord notificationRecord = (NotificationRecord) arrayList.get(i2);
                    notificationRecord.mAuthoritativeRank = i2;
                    if (android.app.Flags.sortSectionByTime()) {
                        String groupKey = notificationRecord.sbn.getGroupKey();
                        NotificationRecord notificationRecord2 =
                                (NotificationRecord) this.mProxyByGroupTmp.get(groupKey);
                        if (notificationRecord2 != null
                                && !notificationRecord2.sbn.getNotification().isGroupSummary()) {}
                        this.mProxyByGroupTmp.put(groupKey, notificationRecord);
                    } else {
                        String groupKey2 = notificationRecord.sbn.getGroupKey();
                        if (((NotificationRecord) this.mProxyByGroupTmp.get(groupKey2)) == null) {
                            this.mProxyByGroupTmp.put(groupKey2, notificationRecord);
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            for (int i3 = 0; i3 < size; i3++) {
                NotificationRecord notificationRecord3 = (NotificationRecord) arrayList.get(i3);
                NotificationRecord notificationRecord4 =
                        (NotificationRecord)
                                this.mProxyByGroupTmp.get(notificationRecord3.sbn.getGroupKey());
                String sortKey = notificationRecord3.sbn.getNotification().getSortKey();
                if (sortKey == null) {
                    str = "nsk";
                } else if (sortKey.equals("")) {
                    str = "esk";
                } else {
                    str = "gsk=" + sortKey;
                }
                String str2 = str;
                boolean isGroupSummary = notificationRecord3.sbn.getNotification().isGroupSummary();
                char c = '1';
                char c2 =
                        android.app.Flags.sortSectionByTime()
                                ? '2'
                                : (!notificationRecord3.mRecentlyIntrusive
                                                || notificationRecord3.mImportance <= 1)
                                        ? '1'
                                        : '0';
                Integer valueOf = Integer.valueOf(notificationRecord3.mCriticality);
                Character valueOf2 = Character.valueOf(c2);
                Integer valueOf3 = Integer.valueOf(notificationRecord4.mAuthoritativeRank);
                if (isGroupSummary) {
                    c = '0';
                }
                notificationRecord3.mGlobalSortKey =
                        TextUtils.formatSimple(
                                "crtcl=0x%04x:intrsv=%c:grnk=0x%04x:gsmry=%c:%s:rnk=0x%04x",
                                new Object[] {
                                    valueOf,
                                    valueOf2,
                                    valueOf3,
                                    Character.valueOf(c),
                                    str2,
                                    Integer.valueOf(notificationRecord3.mAuthoritativeRank)
                                });
            }
            this.mProxyByGroupTmp.clear();
        }
        Collections.sort(arrayList, this.mFinalComparator);
    }
}
