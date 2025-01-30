package com.android.server.people.prediction;

import android.app.usage.UsageEvents;
import android.util.ArrayMap;
import android.util.Pair;
import android.util.Range;
import com.android.internal.app.ChooserActivity;
import com.android.server.display.DisplayPowerController2;
import com.android.server.people.data.AppUsageStatsData;
import com.android.server.people.data.DataManager;
import com.android.server.people.data.Event;
import com.android.server.people.prediction.ShareTargetPredictor;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.function.ToLongFunction;

/* loaded from: classes2.dex */
public abstract class SharesheetModelScorer {
    static final float FOREGROUND_APP_WEIGHT = 0.0f;
    public static final Integer RECENCY_SCORE_COUNT = 6;
    public static final long ONE_MONTH_WINDOW = TimeUnit.DAYS.toMillis(30);
    public static final long FOREGROUND_APP_PROMO_TIME_WINDOW = TimeUnit.MINUTES.toMillis(10);
    static final String CHOOSER_ACTIVITY = ChooserActivity.class.getSimpleName();

    public static float normalizeFreqScore(double d) {
        if (d >= 2.5d) {
            return 0.2f;
        }
        if (d >= 1.5d) {
            return 0.15f;
        }
        if (d >= 1.0d) {
            return 0.1f;
        }
        if (d >= 0.75d) {
            return 0.05f;
        }
        return DisplayPowerController2.RATE_FROM_DOZE_TO_ON;
    }

    public static float normalizeMimeFreqScore(double d) {
        if (d >= 2.0d) {
            return 0.2f;
        }
        if (d >= 1.2d) {
            return 0.15f;
        }
        if (d > 0.0d) {
            return 0.1f;
        }
        return DisplayPowerController2.RATE_FROM_DOZE_TO_ON;
    }

    public static float probOR(float f, float f2) {
        return 1.0f - ((1.0f - f) * (1.0f - f2));
    }

    public static void computeScore(List list, int i, long j) {
        if (list.isEmpty()) {
            return;
        }
        PriorityQueue priorityQueue = new PriorityQueue(RECENCY_SCORE_COUNT.intValue(), Comparator.comparingLong(new ToLongFunction() { // from class: com.android.server.people.prediction.SharesheetModelScorer$$ExternalSyntheticLambda0
            @Override // java.util.function.ToLongFunction
            public final long applyAsLong(Object obj) {
                long lambda$computeScore$0;
                lambda$computeScore$0 = SharesheetModelScorer.lambda$computeScore$0((Pair) obj);
                return lambda$computeScore$0;
            }
        }));
        ArrayList arrayList = new ArrayList(list.size());
        Iterator it = list.iterator();
        int i2 = 0;
        int i3 = 0;
        float f = 0.0f;
        float f2 = 0.0f;
        while (it.hasNext()) {
            ShareTargetPredictor.ShareTarget shareTarget = (ShareTargetPredictor.ShareTarget) it.next();
            ShareTargetRankingScore shareTargetRankingScore = new ShareTargetRankingScore();
            arrayList.add(shareTargetRankingScore);
            if (shareTarget.getEventHistory() != null) {
                List activeTimeSlots = shareTarget.getEventHistory().getEventIndex(Event.SHARE_EVENT_TYPES).getActiveTimeSlots();
                if (!activeTimeSlots.isEmpty()) {
                    Iterator it2 = activeTimeSlots.iterator();
                    while (it2.hasNext()) {
                        shareTargetRankingScore.incrementFrequencyScore(getFreqDecayedOnElapsedTime(j - ((Long) ((Range) it2.next()).getLower()).longValue()));
                    }
                    f += shareTargetRankingScore.getFrequencyScore();
                    i2++;
                }
                List activeTimeSlots2 = shareTarget.getEventHistory().getEventIndex(i).getActiveTimeSlots();
                if (!activeTimeSlots2.isEmpty()) {
                    Iterator it3 = activeTimeSlots2.iterator();
                    while (it3.hasNext()) {
                        shareTargetRankingScore.incrementMimeFrequencyScore(getFreqDecayedOnElapsedTime(j - ((Long) ((Range) it3.next()).getLower()).longValue()));
                    }
                    f2 += shareTargetRankingScore.getMimeFrequencyScore();
                    i3++;
                }
                Range mostRecentActiveTimeSlot = shareTarget.getEventHistory().getEventIndex(Event.SHARE_EVENT_TYPES).getMostRecentActiveTimeSlot();
                if (mostRecentActiveTimeSlot != null) {
                    int size = priorityQueue.size();
                    Integer num = RECENCY_SCORE_COUNT;
                    if (size < num.intValue() || ((Long) mostRecentActiveTimeSlot.getUpper()).longValue() > ((Long) ((Range) ((Pair) priorityQueue.peek()).second).getUpper()).longValue()) {
                        if (priorityQueue.size() == num.intValue()) {
                            priorityQueue.poll();
                        }
                        priorityQueue.offer(new Pair(shareTargetRankingScore, mostRecentActiveTimeSlot));
                    }
                }
            }
        }
        while (!priorityQueue.isEmpty()) {
            ((ShareTargetRankingScore) ((Pair) priorityQueue.poll()).first).setRecencyScore(priorityQueue.size() > 1 ? 0.35f - ((priorityQueue.size() - 2) * 0.02f) : 0.4f);
        }
        Float valueOf = Float.valueOf(i2 != 0 ? f / i2 : 0.0f);
        Float valueOf2 = Float.valueOf(i3 != 0 ? f2 / i3 : 0.0f);
        for (int i4 = 0; i4 < arrayList.size(); i4++) {
            ShareTargetPredictor.ShareTarget shareTarget2 = (ShareTargetPredictor.ShareTarget) list.get(i4);
            ShareTargetRankingScore shareTargetRankingScore2 = (ShareTargetRankingScore) arrayList.get(i4);
            double d = 0.0d;
            shareTargetRankingScore2.setFrequencyScore(normalizeFreqScore(valueOf.equals(Float.valueOf(DisplayPowerController2.RATE_FROM_DOZE_TO_ON)) ? 0.0d : shareTargetRankingScore2.getFrequencyScore() / valueOf.floatValue()));
            if (!valueOf2.equals(Float.valueOf(DisplayPowerController2.RATE_FROM_DOZE_TO_ON))) {
                d = shareTargetRankingScore2.getMimeFrequencyScore() / valueOf2.floatValue();
            }
            shareTargetRankingScore2.setMimeFrequencyScore(normalizeMimeFreqScore(d));
            shareTargetRankingScore2.setTotalScore(probOR(probOR(shareTargetRankingScore2.getRecencyScore(), shareTargetRankingScore2.getFrequencyScore()), shareTargetRankingScore2.getMimeFrequencyScore()));
            shareTarget2.setScore(shareTargetRankingScore2.getTotalScore());
        }
    }

    public static /* synthetic */ long lambda$computeScore$0(Pair pair) {
        return ((Long) ((Range) pair.second).getUpper()).longValue();
    }

    public static void computeScoreForAppShare(List list, int i, int i2, long j, DataManager dataManager, int i3) {
        computeScore(list, i, j);
        postProcess(list, i2, dataManager, i3);
    }

    public static void postProcess(List list, int i, DataManager dataManager, int i2) {
        ArrayMap arrayMap = new ArrayMap();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            ShareTargetPredictor.ShareTarget shareTarget = (ShareTargetPredictor.ShareTarget) it.next();
            String packageName = shareTarget.getAppTarget().getPackageName();
            arrayMap.computeIfAbsent(packageName, new Function() { // from class: com.android.server.people.prediction.SharesheetModelScorer$$ExternalSyntheticLambda1
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    List lambda$postProcess$1;
                    lambda$postProcess$1 = SharesheetModelScorer.lambda$postProcess$1((String) obj);
                    return lambda$postProcess$1;
                }
            });
            List list2 = (List) arrayMap.get(packageName);
            int i3 = 0;
            while (i3 < list2.size() && shareTarget.getScore() <= ((ShareTargetPredictor.ShareTarget) list2.get(i3)).getScore()) {
                i3++;
            }
            list2.add(i3, shareTarget);
        }
        promoteForegroundApp(arrayMap, dataManager, i2);
        promoteMostChosenAndFrequentlyUsedApps(arrayMap, i, dataManager, i2);
    }

    public static /* synthetic */ List lambda$postProcess$1(String str) {
        return new ArrayList();
    }

    public static void promoteMostChosenAndFrequentlyUsedApps(Map map, int i, DataManager dataManager, int i2) {
        Iterator it = map.values().iterator();
        int i3 = 0;
        float f = 1.0f;
        while (it.hasNext()) {
            for (ShareTargetPredictor.ShareTarget shareTarget : (List) it.next()) {
                if (shareTarget.getScore() > DisplayPowerController2.RATE_FROM_DOZE_TO_ON) {
                    i3++;
                    f = Math.min(shareTarget.getScore(), f);
                }
            }
        }
        if (i3 >= i) {
            return;
        }
        long currentTimeMillis = System.currentTimeMillis();
        Map queryAppUsageStats = dataManager.queryAppUsageStats(i2, currentTimeMillis - ONE_MONTH_WINDOW, currentTimeMillis, map.keySet());
        float promoteApp = promoteApp(map, queryAppUsageStats, new Function() { // from class: com.android.server.people.prediction.SharesheetModelScorer$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Integer.valueOf(((AppUsageStatsData) obj).getChosenCount());
            }
        }, 0.9f * f, f);
        promoteApp(map, queryAppUsageStats, new Function() { // from class: com.android.server.people.prediction.SharesheetModelScorer$$ExternalSyntheticLambda3
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Integer.valueOf(((AppUsageStatsData) obj).getLaunchCount());
            }
        }, 0.3f * promoteApp, promoteApp);
    }

    public static float promoteApp(Map map, Map map2, Function function, float f, float f2) {
        Iterator it = map2.values().iterator();
        int i = 0;
        while (it.hasNext()) {
            i = Math.max(i, ((Integer) function.apply((AppUsageStatsData) it.next())).intValue());
        }
        if (i > 0) {
            for (Map.Entry entry : map2.entrySet()) {
                if (map.containsKey(entry.getKey())) {
                    ShareTargetPredictor.ShareTarget shareTarget = (ShareTargetPredictor.ShareTarget) ((List) map.get(entry.getKey())).get(0);
                    if (shareTarget.getScore() <= DisplayPowerController2.RATE_FROM_DOZE_TO_ON) {
                        float intValue = (((Integer) function.apply((AppUsageStatsData) entry.getValue())).intValue() * f) / i;
                        shareTarget.setScore(intValue);
                        if (intValue > DisplayPowerController2.RATE_FROM_DOZE_TO_ON) {
                            f2 = Math.min(f2, intValue);
                        }
                    }
                }
            }
        }
        return f2;
    }

    public static void promoteForegroundApp(Map map, DataManager dataManager, int i) {
        String findSharingForegroundApp = findSharingForegroundApp(map, dataManager, i);
        if (findSharingForegroundApp != null) {
            ShareTargetPredictor.ShareTarget shareTarget = (ShareTargetPredictor.ShareTarget) ((List) map.get(findSharingForegroundApp)).get(0);
            shareTarget.setScore(probOR(shareTarget.getScore(), DisplayPowerController2.RATE_FROM_DOZE_TO_ON));
        }
    }

    public static String findSharingForegroundApp(Map map, DataManager dataManager, int i) {
        long currentTimeMillis = System.currentTimeMillis();
        List queryAppMovingToForegroundEvents = dataManager.queryAppMovingToForegroundEvents(i, currentTimeMillis - FOREGROUND_APP_PROMO_TIME_WINDOW, currentTimeMillis);
        String str = null;
        for (int size = queryAppMovingToForegroundEvents.size() - 1; size >= 0; size--) {
            String className = ((UsageEvents.Event) queryAppMovingToForegroundEvents.get(size)).getClassName();
            String packageName = ((UsageEvents.Event) queryAppMovingToForegroundEvents.get(size)).getPackageName();
            if (packageName != null && ((className == null || !className.contains(CHOOSER_ACTIVITY)) && !packageName.contains(CHOOSER_ACTIVITY))) {
                if (str == null) {
                    str = packageName;
                } else if (!packageName.equals(str) && map.containsKey(packageName)) {
                    return packageName;
                }
            }
        }
        return null;
    }

    public static float getFreqDecayedOnElapsedTime(long j) {
        Duration ofMillis = Duration.ofMillis(j);
        if (ofMillis.compareTo(Duration.ofDays(1L)) <= 0) {
            return 1.0f;
        }
        if (ofMillis.compareTo(Duration.ofDays(3L)) <= 0) {
            return 0.9f;
        }
        if (ofMillis.compareTo(Duration.ofDays(7L)) <= 0) {
            return 0.8f;
        }
        return ofMillis.compareTo(Duration.ofDays(14L)) <= 0 ? 0.7f : 0.6f;
    }

    public class ShareTargetRankingScore {
        public float mFrequencyScore;
        public float mMimeFrequencyScore;
        public float mRecencyScore;
        public float mTotalScore;

        public ShareTargetRankingScore() {
            this.mRecencyScore = DisplayPowerController2.RATE_FROM_DOZE_TO_ON;
            this.mFrequencyScore = DisplayPowerController2.RATE_FROM_DOZE_TO_ON;
            this.mMimeFrequencyScore = DisplayPowerController2.RATE_FROM_DOZE_TO_ON;
            this.mTotalScore = DisplayPowerController2.RATE_FROM_DOZE_TO_ON;
        }

        public float getTotalScore() {
            return this.mTotalScore;
        }

        public void setTotalScore(float f) {
            this.mTotalScore = f;
        }

        public float getRecencyScore() {
            return this.mRecencyScore;
        }

        public void setRecencyScore(float f) {
            this.mRecencyScore = f;
        }

        public float getFrequencyScore() {
            return this.mFrequencyScore;
        }

        public void setFrequencyScore(float f) {
            this.mFrequencyScore = f;
        }

        public void incrementFrequencyScore(float f) {
            this.mFrequencyScore += f;
        }

        public float getMimeFrequencyScore() {
            return this.mMimeFrequencyScore;
        }

        public void setMimeFrequencyScore(float f) {
            this.mMimeFrequencyScore = f;
        }

        public void incrementMimeFrequencyScore(float f) {
            this.mMimeFrequencyScore += f;
        }
    }
}
