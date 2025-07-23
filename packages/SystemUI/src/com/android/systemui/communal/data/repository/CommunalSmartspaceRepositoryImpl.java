package com.android.systemui.communal.data.repository;

import android.app.smartspace.SmartspaceTarget;
import android.widget.RemoteViews;
import com.android.systemui.communal.data.model.CommunalSmartspaceTimer;
import com.android.systemui.communal.smartspace.CommunalSmartspaceController;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.log.core.Logger;
import com.android.systemui.plugins.BcSmartspaceDataPlugin;
import com.android.systemui.util.time.SystemClock;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.EmptyList;
import kotlin.collections.MapsKt__MapsJVMKt;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.text.StringsKt__StringsKt;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class CommunalSmartspaceRepositoryImpl implements CommunalSmartspaceRepository, BcSmartspaceDataPlugin.SmartspaceTargetListener {
    public static final Companion Companion = new Companion(null);
    public final StateFlowImpl _timers;
    public final CommunalSmartspaceController communalSmartspaceController;
    public final Logger logger;
    public final SystemClock systemClock;
    public Map targetCreationTimes;
    public final StateFlowImpl timers;
    public final Executor uiExecutor;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final String stableId(String str) {
            return CollectionsKt___CollectionsKt.joinToString$default(CollectionsKt___CollectionsKt.take(StringsKt__StringsKt.split$default(str, new String[]{"-"}, 0, 6), 2), "-", null, null, null, 62);
        }

        private Companion() {
        }
    }

    public CommunalSmartspaceRepositoryImpl(CommunalSmartspaceController communalSmartspaceController, Executor executor, SystemClock systemClock, LogBuffer logBuffer) {
        this.communalSmartspaceController = communalSmartspaceController;
        this.uiExecutor = executor;
        this.systemClock = systemClock;
        this.logger = new Logger(logBuffer, "CommunalSmartspaceRepository");
        StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(EmptyList.INSTANCE);
        this._timers = MutableStateFlow;
        this.timers = MutableStateFlow;
        this.targetCreationTimes = MapsKt__MapsKt.emptyMap();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0, types: [kotlin.collections.EmptyList] */
    /* JADX WARN: Type inference failed for: r0v1 */
    /* JADX WARN: Type inference failed for: r0v19, types: [java.util.ArrayList] */
    @Override // com.android.systemui.plugins.BcSmartspaceDataPlugin.SmartspaceTargetListener
    public final void onSmartspaceTargetsUpdated(List list) {
        ?? r0;
        if (list != null) {
            r0 = new ArrayList();
            for (Object obj : list) {
                if (obj instanceof SmartspaceTarget) {
                    r0.add(obj);
                }
            }
        } else {
            r0 = EmptyList.INSTANCE;
        }
        ArrayList arrayList = new ArrayList();
        for (Object obj2 : (Iterable) r0) {
            SmartspaceTarget smartspaceTarget = (SmartspaceTarget) obj2;
            if (smartspaceTarget.getFeatureType() == 21 && smartspaceTarget.getRemoteViews() != null) {
                arrayList.add(obj2);
            }
        }
        int mapCapacity = MapsKt__MapsJVMKt.mapCapacity(CollectionsKt__IterablesKt.collectionSizeOrDefault(arrayList, 10));
        if (mapCapacity < 16) {
            mapCapacity = 16;
        }
        LinkedHashMap linkedHashMap = new LinkedHashMap(mapCapacity);
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj3 = arrayList.get(i);
            i++;
            linkedHashMap.put(Companion.stableId(((SmartspaceTarget) obj3).getSmartspaceTargetId()), obj3);
        }
        LinkedHashMap linkedHashMap2 = new LinkedHashMap(MapsKt__MapsJVMKt.mapCapacity(linkedHashMap.size()));
        for (Map.Entry entry : linkedHashMap.entrySet()) {
            Object key = entry.getKey();
            Long l = (Long) this.targetCreationTimes.get((String) entry.getKey());
            linkedHashMap2.put(key, Long.valueOf(l != null ? l.longValue() : this.systemClock.currentTimeMillis()));
        }
        this.targetCreationTimes = linkedHashMap2;
        ArrayList arrayList2 = new ArrayList(linkedHashMap.size());
        for (Map.Entry entry2 : linkedHashMap.entrySet()) {
            String str = (String) entry2.getKey();
            SmartspaceTarget smartspaceTarget2 = (SmartspaceTarget) entry2.getValue();
            Object obj4 = this.targetCreationTimes.get(str);
            obj4.getClass();
            long longValue = ((Number) obj4).longValue();
            RemoteViews remoteViews = smartspaceTarget2.getRemoteViews();
            remoteViews.getClass();
            arrayList2.add(new CommunalSmartspaceTimer(str, longValue, remoteViews));
        }
        StateFlowImpl stateFlowImpl = this._timers;
        if (!arrayList2.equals(stateFlowImpl.getValue())) {
            Logger logger = this.logger;
            LogMessage obtain = logger.getBuffer().obtain(logger.getTag(), LogLevel.DEBUG, new CommunalSmartspaceRepositoryImpl$$ExternalSyntheticLambda0(), null);
            obtain.setStr1(arrayList2.toString());
            logger.getBuffer().commit(obtain);
        }
        stateFlowImpl.updateState(null, arrayList2);
    }
}
