package com.android.server.people.prediction;

import android.util.ArrayMap;
import android.util.Pair;
import android.util.Range;

import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;
import com.android.server.people.data.AppUsageStatsData;
import com.android.server.people.data.Event;
import com.android.server.people.data.EventIndex;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

public abstract class SharesheetModelScorer {
    static final float FOREGROUND_APP_WEIGHT = 0.0f;
    public static final long ONE_MONTH_WINDOW = TimeUnit.DAYS.toMillis(30);
    public static final long FOREGROUND_APP_PROMO_TIME_WINDOW = TimeUnit.MINUTES.toMillis(10);

    public final class ShareTargetRankingScore {
        public float mFrequencyScore;
        public float mMimeFrequencyScore;
        public float mRecencyScore;
    }

    public static void computeScore(List list, int i, long j) {
        Iterator it;
        float f;
        Range range;
        char c;
        ArrayList arrayList = (ArrayList) list;
        if (arrayList.isEmpty()) {
            return;
        }
        PriorityQueue priorityQueue =
                new PriorityQueue(
                        6,
                        Comparator.comparingLong(
                                new SharesheetModelScorer$$ExternalSyntheticLambda1()));
        ArrayList arrayList2 = new ArrayList(arrayList.size());
        Iterator it2 = arrayList.iterator();
        float f2 = FullScreenMagnificationGestureHandler.MAX_SCALE;
        float f3 = 0.0f;
        float f4 = 0.0f;
        int i2 = 0;
        int i3 = 0;
        while (it2.hasNext()) {
            ShareTargetPredictor.ShareTarget shareTarget =
                    (ShareTargetPredictor.ShareTarget) it2.next();
            ShareTargetRankingScore shareTargetRankingScore = new ShareTargetRankingScore();
            shareTargetRankingScore.mRecencyScore = f2;
            shareTargetRankingScore.mFrequencyScore = f2;
            shareTargetRankingScore.mMimeFrequencyScore = f2;
            arrayList2.add(shareTargetRankingScore);
            if (shareTarget.getEventHistory() != null) {
                List activeTimeSlots =
                        shareTarget
                                .getEventHistory()
                                .getEventIndex(Event.SHARE_EVENT_TYPES)
                                .getActiveTimeSlots();
                if (!activeTimeSlots.isEmpty()) {
                    Iterator it3 = activeTimeSlots.iterator();
                    while (it3.hasNext()) {
                        shareTargetRankingScore.mFrequencyScore +=
                                getFreqDecayedOnElapsedTime(
                                        j - ((Long) ((Range) it3.next()).getLower()).longValue());
                    }
                    f3 += shareTargetRankingScore.mFrequencyScore;
                    i2++;
                }
                List activeTimeSlots2 =
                        shareTarget.getEventHistory().getEventIndex(i).getActiveTimeSlots();
                if (!activeTimeSlots2.isEmpty()) {
                    Iterator it4 = activeTimeSlots2.iterator();
                    while (it4.hasNext()) {
                        shareTargetRankingScore.mMimeFrequencyScore +=
                                getFreqDecayedOnElapsedTime(
                                        j - ((Long) ((Range) it4.next()).getLower()).longValue());
                    }
                    f4 += shareTargetRankingScore.mMimeFrequencyScore;
                    i3++;
                }
                EventIndex eventIndex =
                        shareTarget.getEventHistory().getEventIndex(Event.SHARE_EVENT_TYPES);
                synchronized (eventIndex.mLock) {
                    int i4 = 3;
                    while (true) {
                        if (i4 < 0) {
                            it = it2;
                            f = f3;
                            range = null;
                            break;
                        }
                        try {
                            if (eventIndex.mEventBitmaps[i4] == 0) {
                                i4--;
                            } else {
                                f = f3;
                                Range range2 =
                                        (Range)
                                                ((Function) EventIndex.TIME_SLOT_FACTORIES.get(i4))
                                                        .apply(
                                                                Long.valueOf(
                                                                        eventIndex
                                                                                .mLastUpdatedTime));
                                it = it2;
                                long duration =
                                        EventIndex.getDuration(range2)
                                                * Long.numberOfTrailingZeros(
                                                        eventIndex.mEventBitmaps[i4]);
                                range =
                                        Range.create(
                                                Long.valueOf(
                                                        ((Long) range2.getLower()).longValue()
                                                                - duration),
                                                Long.valueOf(
                                                        ((Long) range2.getUpper()).longValue()
                                                                - duration));
                            }
                        } finally {
                        }
                    }
                }
                if (range != null
                        && (priorityQueue.size() < 6
                                || ((Long) range.getUpper()).longValue()
                                        > ((Long)
                                                        ((Range)
                                                                        ((Pair)
                                                                                        priorityQueue
                                                                                                .peek())
                                                                                .second)
                                                                .getUpper())
                                                .longValue())) {
                    c = 6;
                    if (priorityQueue.size() == 6) {
                        priorityQueue.poll();
                    }
                    priorityQueue.offer(new Pair(shareTargetRankingScore, range));
                } else {
                    c = 6;
                }
                it2 = it;
                f3 = f;
                f2 = FullScreenMagnificationGestureHandler.MAX_SCALE;
            }
        }
        while (!priorityQueue.isEmpty()) {
            ((ShareTargetRankingScore) ((Pair) priorityQueue.poll()).first).mRecencyScore =
                    priorityQueue.size() > 1 ? 0.35f - ((priorityQueue.size() - 2) * 0.02f) : 0.4f;
        }
        Float valueOf =
                Float.valueOf(i2 != 0 ? f3 / i2 : FullScreenMagnificationGestureHandler.MAX_SCALE);
        Float valueOf2 =
                Float.valueOf(i3 != 0 ? f4 / i3 : FullScreenMagnificationGestureHandler.MAX_SCALE);
        for (int i5 = 0; i5 < arrayList2.size(); i5++) {
            ShareTargetPredictor.ShareTarget shareTarget2 =
                    (ShareTargetPredictor.ShareTarget) arrayList.get(i5);
            ShareTargetRankingScore shareTargetRankingScore2 =
                    (ShareTargetRankingScore) arrayList2.get(i5);
            double d =
                    valueOf.equals(Float.valueOf(FullScreenMagnificationGestureHandler.MAX_SCALE))
                            ? 0.0d
                            : shareTargetRankingScore2.mFrequencyScore / r1;
            float f5 = 0.1f;
            shareTargetRankingScore2.mFrequencyScore =
                    d >= 2.5d
                            ? 0.2f
                            : d >= 1.5d
                                    ? 0.15f
                                    : d >= 1.0d
                                            ? 0.1f
                                            : d >= 0.75d
                                                    ? 0.05f
                                                    : FullScreenMagnificationGestureHandler
                                                            .MAX_SCALE;
            double d2 =
                    valueOf2.equals(Float.valueOf(FullScreenMagnificationGestureHandler.MAX_SCALE))
                            ? 0.0d
                            : shareTargetRankingScore2.mMimeFrequencyScore / r4;
            if (d2 >= 2.0d) {
                f5 = 0.2f;
            } else if (d2 >= 1.2d) {
                f5 = 0.15f;
            } else if (d2 <= 0.0d) {
                f5 = 0.0f;
            }
            shareTargetRankingScore2.mMimeFrequencyScore = f5;
            shareTarget2.setScore(
                    1.0f
                            - ((1.0f - f5)
                                    * (1.0f
                                            - (1.0f
                                                    - ((1.0f
                                                                    - shareTargetRankingScore2
                                                                            .mFrequencyScore)
                                                            * (1.0f
                                                                    - shareTargetRankingScore2
                                                                            .mRecencyScore))))));
        }
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

    public static float promoteApp(Map map, Map map2, Function function, float f, float f2) {
        ArrayMap arrayMap = (ArrayMap) map2;
        Iterator it = arrayMap.values().iterator();
        int i = 0;
        while (it.hasNext()) {
            i = Math.max(i, ((Integer) function.apply((AppUsageStatsData) it.next())).intValue());
        }
        if (i > 0) {
            for (Map.Entry entry : arrayMap.entrySet()) {
                ArrayMap arrayMap2 = (ArrayMap) map;
                if (arrayMap2.containsKey(entry.getKey())) {
                    ShareTargetPredictor.ShareTarget shareTarget =
                            (ShareTargetPredictor.ShareTarget)
                                    ((List) arrayMap2.get(entry.getKey())).get(0);
                    if (shareTarget.getScore() <= FullScreenMagnificationGestureHandler.MAX_SCALE) {
                        float intValue =
                                (((Integer) function.apply((AppUsageStatsData) entry.getValue()))
                                                        .intValue()
                                                * f)
                                        / i;
                        shareTarget.setScore(intValue);
                        if (intValue > FullScreenMagnificationGestureHandler.MAX_SCALE) {
                            f2 = Math.min(f2, intValue);
                        }
                    }
                }
            }
        }
        return f2;
    }
}
