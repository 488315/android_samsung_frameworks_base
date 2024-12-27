package com.android.server.people.prediction;

import android.R;
import android.app.prediction.AppPredictionContext;
import android.app.prediction.AppPredictionManager;
import android.app.prediction.AppPredictor;
import android.app.prediction.AppTarget;
import android.app.prediction.AppTargetEvent;
import android.app.prediction.AppTargetId;
import android.app.usage.UsageEvents;
import android.app.usage.UsageStats;
import android.content.ComponentName;
import android.content.Context;
import android.content.IntentFilter;
import android.content.LocusId;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.os.UserHandle;
import android.provider.DeviceConfig;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Log;
import android.util.Slog;

import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;
import com.android.server.people.PeopleService$LocalService$$ExternalSyntheticLambda0;
import com.android.server.people.SessionInfo$$ExternalSyntheticLambda0;
import com.android.server.people.data.AggregateEventHistoryImpl;
import com.android.server.people.data.AppUsageStatsData;
import com.android.server.people.data.ConversationInfo;
import com.android.server.people.data.ConversationStore;
import com.android.server.people.data.DataManager;
import com.android.server.people.data.Event;
import com.android.server.people.data.EventHistory;
import com.android.server.people.data.EventHistoryImpl;
import com.android.server.people.data.EventStore;
import com.android.server.people.data.PackageData;
import com.android.server.people.data.UsageStatsQueryHelper$$ExternalSyntheticLambda0;
import com.android.server.people.data.UserData;
import com.android.server.people.data.UserData$$ExternalSyntheticLambda0;
import com.android.server.usage.UsageStatsService;

import libcore.util.EmptyArray;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class ShareTargetPredictor extends AppTargetPredictor {
    public static final boolean DEBUG = Log.isLoggable("ShareTargetPredictor", 3);
    public final String mChooserActivity;
    public final IntentFilter mIntentFilter;
    public final AppPredictor mRemoteAppPredictor;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    class ShareTarget {
        public final AppTarget mAppTarget;
        public final ConversationInfo mConversationInfo;
        public final EventHistory mEventHistory;
        public float mScore = FullScreenMagnificationGestureHandler.MAX_SCALE;

        public ShareTarget(
                AppTarget appTarget, EventHistory eventHistory, ConversationInfo conversationInfo) {
            this.mAppTarget = appTarget;
            this.mEventHistory = eventHistory;
            this.mConversationInfo = conversationInfo;
        }

        public AppTarget getAppTarget() {
            return this.mAppTarget;
        }

        public ConversationInfo getConversationInfo() {
            return this.mConversationInfo;
        }

        public EventHistory getEventHistory() {
            return this.mEventHistory;
        }

        public float getScore() {
            return this.mScore;
        }

        public void setScore(float f) {
            this.mScore = f;
        }
    }

    public ShareTargetPredictor(
            AppPredictionContext appPredictionContext,
            SessionInfo$$ExternalSyntheticLambda0 sessionInfo$$ExternalSyntheticLambda0,
            DataManager dataManager,
            int i,
            Context context) {
        super(appPredictionContext, sessionInfo$$ExternalSyntheticLambda0, dataManager, i);
        this.mIntentFilter =
                (IntentFilter)
                        appPredictionContext
                                .getExtras()
                                .getParcelable("intent_filter", IntentFilter.class);
        if (DeviceConfig.getBoolean(
                "systemui", "dark_launch_remote_prediction_service_enabled", false)) {
            appPredictionContext.getExtras().putBoolean("remote_app_predictor", true);
            this.mRemoteAppPredictor =
                    ((AppPredictionManager)
                                    context.createContextAsUser(UserHandle.of(i), 0)
                                            .getSystemService(AppPredictionManager.class))
                            .createAppPredictionSession(appPredictionContext);
        } else {
            this.mRemoteAppPredictor = null;
        }
        ComponentName unflattenFromString =
                ComponentName.unflattenFromString(
                        context.getResources()
                                .getString(R.string.crossSimFormat_spn_cross_sim_calling));
        this.mChooserActivity =
                unflattenFromString != null ? unflattenFromString.getShortClassName() : null;
    }

    @Override // com.android.server.people.prediction.AppTargetPredictor
    public final void predictTargets() {
        int i;
        ConversationInfo conversationInfo;
        EventHistory eventHistory;
        EventHistory eventHistory2;
        EventHistory eventHistory3;
        if (DEBUG) {
            Slog.d("ShareTargetPredictor", "predictTargets");
        }
        if (this.mIntentFilter == null) {
            this.mUpdatePredictionsMethod.accept(List.of());
            return;
        }
        ArrayList arrayList = new ArrayList();
        IntentFilter intentFilter = this.mIntentFilter;
        DataManager dataManager = this.mDataManager;
        Iterator it =
                dataManager
                        .mShortcutServiceInternal
                        .getShareTargets(
                                dataManager.mContext.getPackageName(),
                                intentFilter,
                                this.mCallingUserId)
                        .iterator();
        while (true) {
            r4 = null;
            AggregateEventHistoryImpl aggregateEventHistoryImpl = null;
            if (!it.hasNext()) {
                break;
            }
            ShortcutManager.ShareShortcutInfo shareShortcutInfo =
                    (ShortcutManager.ShareShortcutInfo) it.next();
            ShortcutInfo shortcutInfo = shareShortcutInfo.getShortcutInfo();
            AppTarget build =
                    new AppTarget.Builder(new AppTargetId(shortcutInfo.getId()), shortcutInfo)
                            .setClassName(shareShortcutInfo.getTargetComponent().getClassName())
                            .setRank(shortcutInfo.getRank())
                            .build();
            PackageData packageData =
                    dataManager.getPackage(shortcutInfo.getUserId(), shortcutInfo.getPackage());
            if (packageData != null) {
                String id = shortcutInfo.getId();
                ConversationStore conversationStore = packageData.mConversationStore;
                conversationInfo = conversationStore.getConversation(id);
                if (conversationInfo != null) {
                    aggregateEventHistoryImpl = new AggregateEventHistoryImpl();
                    ConversationInfo conversation = conversationStore.getConversation(id);
                    if (conversation != null) {
                        EventStore eventStore = packageData.mEventStore;
                        EventHistory eventHistory4 = eventStore.getEventHistory(0, id);
                        if (eventHistory4 != null) {
                            ((ArrayList) aggregateEventHistoryImpl.mEventHistoryList)
                                    .add(eventHistory4);
                        }
                        LocusId locusId = conversation.mLocusId;
                        if (locusId != null
                                && (eventHistory3 = eventStore.getEventHistory(1, locusId.getId()))
                                        != null) {
                            ((ArrayList) aggregateEventHistoryImpl.mEventHistoryList)
                                    .add(eventHistory3);
                        }
                        String str = conversation.mContactPhoneNumber;
                        if (!TextUtils.isEmpty(str)) {
                            Predicate predicate = packageData.mIsDefaultDialerPredicate;
                            String str2 = packageData.mPackageName;
                            if (predicate.test(str2)
                                    && (eventHistory2 = eventStore.getEventHistory(2, str))
                                            != null) {
                                ((ArrayList) aggregateEventHistoryImpl.mEventHistoryList)
                                        .add(eventHistory2);
                            }
                            if (packageData.mIsDefaultSmsAppPredicate.test(str2)
                                    && (eventHistory = eventStore.getEventHistory(3, str))
                                            != null) {
                                ((ArrayList) aggregateEventHistoryImpl.mEventHistoryList)
                                        .add(eventHistory);
                            }
                        }
                    }
                }
            } else {
                conversationInfo = null;
            }
            arrayList.add(new ShareTarget(build, aggregateEventHistoryImpl, conversationInfo));
        }
        IntentFilter intentFilter2 = this.mIntentFilter;
        String dataType = intentFilter2 != null ? intentFilter2.getDataType(0) : null;
        dataManager.getClass();
        SharesheetModelScorer.computeScore(
                arrayList,
                DataManager.mimeTypeToShareEventType(dataType),
                System.currentTimeMillis());
        final int i2 = 0;
        final int i3 = 1;
        Collections.sort(
                arrayList,
                Comparator.comparing(
                                new Function() { // from class:
                                                 // com.android.server.people.prediction.ShareTargetPredictor$$ExternalSyntheticLambda0
                                    @Override // java.util.function.Function
                                    public final Object apply(Object obj) {
                                        ShareTargetPredictor.ShareTarget shareTarget =
                                                (ShareTargetPredictor.ShareTarget) obj;
                                        switch (i2) {
                                            case 0:
                                                return Float.valueOf(shareTarget.getScore());
                                            default:
                                                return Integer.valueOf(
                                                        shareTarget.getAppTarget().getRank());
                                        }
                                    }
                                },
                                Collections.reverseOrder())
                        .thenComparing(
                                new Function() { // from class:
                                                 // com.android.server.people.prediction.ShareTargetPredictor$$ExternalSyntheticLambda0
                                    @Override // java.util.function.Function
                                    public final Object apply(Object obj) {
                                        ShareTargetPredictor.ShareTarget shareTarget =
                                                (ShareTargetPredictor.ShareTarget) obj;
                                        switch (i3) {
                                            case 0:
                                                return Float.valueOf(shareTarget.getScore());
                                            default:
                                                return Integer.valueOf(
                                                        shareTarget.getAppTarget().getRank());
                                        }
                                    }
                                }));
        ArrayList arrayList2 = new ArrayList();
        for (i = 0;
                i < Math.min(this.mPredictionContext.getPredictedTargetCount(), arrayList.size());
                i++) {
            arrayList2.add(((ShareTarget) arrayList.get(i)).getAppTarget());
        }
        this.mUpdatePredictionsMethod.accept(arrayList2);
    }

    @Override // com.android.server.people.prediction.AppTargetPredictor
    public final void reportAppTargetEvent(AppTargetEvent appTargetEvent) {
        UserData unlockedUserData;
        EventHistoryImpl orCreateEventHistory;
        if (DEBUG) {
            Slog.d("ShareTargetPredictor", "reportAppTargetEvent");
        }
        IntentFilter intentFilter = this.mIntentFilter;
        if (intentFilter != null) {
            DataManager dataManager = this.mDataManager;
            dataManager.getClass();
            AppTarget target = appTargetEvent.getTarget();
            if (target != null
                    && appTargetEvent.getAction() == 1
                    && (unlockedUserData =
                                    dataManager.getUnlockedUserData(
                                            target.getUser().getIdentifier()))
                            != null) {
                String packageName = target.getPackageName();
                PackageData packageData =
                        (PackageData)
                                unlockedUserData.mPackageDataMap.computeIfAbsent(
                                        packageName,
                                        new UserData$$ExternalSyntheticLambda0(
                                                unlockedUserData, packageName));
                int mimeTypeToShareEventType =
                        DataManager.mimeTypeToShareEventType(intentFilter.getDataType(0));
                if (!"direct_share".equals(appTargetEvent.getLaunchLocation())) {
                    orCreateEventHistory =
                            packageData.mEventStore.getOrCreateEventHistory(
                                    4, target.getClassName());
                } else if (target.getShortcutInfo() != null) {
                    String id = target.getShortcutInfo().getId();
                    if (!"chooser_target".equals(id)) {
                        if (packageData.mConversationStore.getConversation(id) == null) {
                            dataManager.addOrUpdateConversationInfo(target.getShortcutInfo());
                        }
                        orCreateEventHistory =
                                packageData.mEventStore.getOrCreateEventHistory(0, id);
                    }
                }
                orCreateEventHistory.addEvent(
                        new Event(System.currentTimeMillis(), mimeTypeToShareEventType));
            }
        }
        AppPredictor appPredictor = this.mRemoteAppPredictor;
        if (appPredictor != null) {
            appPredictor.notifyAppTargetEvent(appTargetEvent);
        }
    }

    @Override // com.android.server.people.prediction.AppTargetPredictor
    public final void sortTargets(
            List list,
            PeopleService$LocalService$$ExternalSyntheticLambda0
                    peopleService$LocalService$$ExternalSyntheticLambda0) {
        DataManager dataManager;
        String str;
        int i;
        String str2;
        EventHistory eventHistory;
        if (DEBUG) {
            Slog.d("ShareTargetPredictor", "sortTargets");
        }
        if (this.mIntentFilter == null) {
            peopleService$LocalService$$ExternalSyntheticLambda0.accept(list);
            return;
        }
        ArrayList arrayList = new ArrayList();
        Iterator it = list.iterator();
        while (true) {
            boolean hasNext = it.hasNext();
            dataManager = this.mDataManager;
            str = null;
            if (!hasNext) {
                break;
            }
            AppTarget appTarget = (AppTarget) it.next();
            PackageData packageData =
                    dataManager.getPackage(
                            appTarget.getUser().getIdentifier(), appTarget.getPackageName());
            if (packageData == null) {
                eventHistory = null;
            } else {
                eventHistory = packageData.mEventStore.getEventHistory(4, appTarget.getClassName());
                if (eventHistory == null) {
                    eventHistory = new AggregateEventHistoryImpl();
                }
            }
            arrayList.add(new ShareTarget(appTarget, eventHistory, null));
        }
        IntentFilter intentFilter = this.mIntentFilter;
        int i2 = 0;
        String dataType = intentFilter != null ? intentFilter.getDataType(0) : null;
        dataManager.getClass();
        int mimeTypeToShareEventType = DataManager.mimeTypeToShareEventType(dataType);
        int predictedTargetCount = this.mPredictionContext.getPredictedTargetCount();
        SharesheetModelScorer.computeScore(
                arrayList, mimeTypeToShareEventType, System.currentTimeMillis());
        ArrayMap arrayMap = new ArrayMap();
        Iterator it2 = arrayList.iterator();
        while (it2.hasNext()) {
            ShareTarget shareTarget = (ShareTarget) it2.next();
            String packageName = shareTarget.getAppTarget().getPackageName();
            final int i3 = 0;
            arrayMap.computeIfAbsent(
                    packageName,
                    new Function() { // from class:
                                     // com.android.server.people.prediction.SharesheetModelScorer$$ExternalSyntheticLambda0
                        @Override // java.util.function.Function
                        public final Object apply(Object obj) {
                            switch (i3) {
                                case 0:
                                    return new ArrayList();
                                case 1:
                                    return Integer.valueOf(((AppUsageStatsData) obj).mChosenCount);
                                default:
                                    return Integer.valueOf(((AppUsageStatsData) obj).mLaunchCount);
                            }
                        }
                    });
            List list2 = (List) arrayMap.get(packageName);
            int i4 = 0;
            while (i4 < list2.size()
                    && shareTarget.getScore() <= ((ShareTarget) list2.get(i4)).getScore()) {
                i4++;
            }
            list2.add(i4, shareTarget);
        }
        long currentTimeMillis = System.currentTimeMillis();
        long j = currentTimeMillis - SharesheetModelScorer.FOREGROUND_APP_PROMO_TIME_WINDOW;
        dataManager.getClass();
        ArrayList arrayList2 = new ArrayList();
        UsageStatsService usageStatsService = UsageStatsService.this;
        usageStatsService.getClass();
        int[] iArr = EmptyArray.INT;
        int i5 = this.mCallingUserId;
        UsageEvents queryEventsWithQueryFilters =
                usageStatsService.queryEventsWithQueryFilters(
                        i5, j, currentTimeMillis, 10, iArr, null);
        if (queryEventsWithQueryFilters != null) {
            while (queryEventsWithQueryFilters.hasNextEvent()) {
                UsageEvents.Event event = new UsageEvents.Event();
                queryEventsWithQueryFilters.getNextEvent(event);
                if (event.getEventType() == 1) {
                    arrayList2.add(event);
                }
            }
        }
        int size = arrayList2.size() - 1;
        String str3 = null;
        while (true) {
            if (size < 0) {
                break;
            }
            String className = ((UsageEvents.Event) arrayList2.get(size)).getClassName();
            String packageName2 = ((UsageEvents.Event) arrayList2.get(size)).getPackageName();
            if (packageName2 != null
                    && (className == null
                            || (str2 = this.mChooserActivity) == null
                            || !className.contains(str2))) {
                if (str3 == null) {
                    str3 = packageName2;
                } else if (!packageName2.equals(str3) && arrayMap.containsKey(packageName2)) {
                    str = packageName2;
                    break;
                }
            }
            size--;
        }
        float f = 1.0f;
        if (str != null) {
            ShareTarget shareTarget2 = (ShareTarget) ((List) arrayMap.get(str)).get(0);
            shareTarget2.setScore(1.0f - ((1.0f - shareTarget2.getScore()) * 1.0f));
        }
        Iterator it3 = arrayMap.values().iterator();
        int i6 = 0;
        while (it3.hasNext()) {
            for (ShareTarget shareTarget3 : (List) it3.next()) {
                if (shareTarget3.getScore() > FullScreenMagnificationGestureHandler.MAX_SCALE) {
                    i6++;
                    f = Math.min(shareTarget3.getScore(), f);
                }
            }
        }
        if (i6 < predictedTargetCount) {
            long currentTimeMillis2 = System.currentTimeMillis();
            long j2 = currentTimeMillis2 - SharesheetModelScorer.ONE_MONTH_WINDOW;
            Set keySet = arrayMap.keySet();
            List<UsageStats> queryUsageStats =
                    UsageStatsService.this.queryUsageStats(i5, j2, currentTimeMillis2, 4, false);
            ArrayMap arrayMap2 = new ArrayMap();
            if (queryUsageStats != null) {
                for (UsageStats usageStats : queryUsageStats) {
                    String packageName3 = usageStats.getPackageName();
                    if (keySet.contains(packageName3)) {
                        AppUsageStatsData appUsageStatsData =
                                (AppUsageStatsData)
                                        arrayMap2.computeIfAbsent(
                                                packageName3,
                                                new UsageStatsQueryHelper$$ExternalSyntheticLambda0());
                        ArrayMap arrayMap3 = usageStats.mChooserCounts;
                        if (arrayMap3 == null) {
                            i = i2;
                        } else {
                            int size2 = arrayMap3.size();
                            int i7 = i2;
                            i = i7;
                            while (i7 < size2) {
                                ArrayMap arrayMap4 = (ArrayMap) arrayMap3.valueAt(i7);
                                if (arrayMap4 != null) {
                                    int size3 = arrayMap4.size();
                                    for (int i8 = 0; i8 < size3; i8++) {
                                        i = ((Integer) arrayMap4.valueAt(i8)).intValue() + i;
                                    }
                                }
                                i7++;
                            }
                        }
                        appUsageStatsData.mChosenCount += i;
                        appUsageStatsData.mLaunchCount += usageStats.getAppLaunchCount();
                    }
                    i2 = 0;
                }
            }
            final int i9 = 1;
            float promoteApp =
                    SharesheetModelScorer.promoteApp(
                            arrayMap,
                            arrayMap2,
                            new Function() { // from class:
                                             // com.android.server.people.prediction.SharesheetModelScorer$$ExternalSyntheticLambda0
                                @Override // java.util.function.Function
                                public final Object apply(Object obj) {
                                    switch (i9) {
                                        case 0:
                                            return new ArrayList();
                                        case 1:
                                            return Integer.valueOf(
                                                    ((AppUsageStatsData) obj).mChosenCount);
                                        default:
                                            return Integer.valueOf(
                                                    ((AppUsageStatsData) obj).mLaunchCount);
                                    }
                                }
                            },
                            0.9f * f,
                            f);
            final int i10 = 2;
            SharesheetModelScorer.promoteApp(
                    arrayMap,
                    arrayMap2,
                    new Function() { // from class:
                                     // com.android.server.people.prediction.SharesheetModelScorer$$ExternalSyntheticLambda0
                        @Override // java.util.function.Function
                        public final Object apply(Object obj) {
                            switch (i10) {
                                case 0:
                                    return new ArrayList();
                                case 1:
                                    return Integer.valueOf(((AppUsageStatsData) obj).mChosenCount);
                                default:
                                    return Integer.valueOf(((AppUsageStatsData) obj).mLaunchCount);
                            }
                        }
                    },
                    0.3f * promoteApp,
                    promoteApp);
        }
        Collections.sort(arrayList, new ShareTargetPredictor$$ExternalSyntheticLambda2());
        ArrayList arrayList3 = new ArrayList();
        Iterator it4 = arrayList.iterator();
        while (it4.hasNext()) {
            ShareTarget shareTarget4 = (ShareTarget) it4.next();
            AppTarget appTarget2 = shareTarget4.getAppTarget();
            arrayList3.add(
                    new AppTarget.Builder(
                                    appTarget2.getId(),
                                    appTarget2.getPackageName(),
                                    appTarget2.getUser())
                            .setClassName(appTarget2.getClassName())
                            .setRank(
                                    shareTarget4.getScore()
                                                    > FullScreenMagnificationGestureHandler
                                                            .MAX_SCALE
                                            ? (int) (shareTarget4.getScore() * 1000.0f)
                                            : 0)
                            .build());
        }
        peopleService$LocalService$$ExternalSyntheticLambda0.accept(arrayList3);
    }
}
