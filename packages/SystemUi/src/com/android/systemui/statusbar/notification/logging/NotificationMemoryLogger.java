package com.android.systemui.statusbar.notification.logging;

import android.app.StatsManager;
import android.os.Trace;
import android.util.Log;
import androidx.constraintlayout.core.widgets.ConstraintWidget$$ExternalSyntheticOutline0;
import com.android.systemui.shared.system.SysUiStatsLog;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import kotlin.Pair;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.comparisons.ComparisonsKt__ComparisonsKt;
import kotlin.jvm.functions.Function1;
import kotlin.math.MathKt__MathJVMKt;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class NotificationMemoryLogger implements StatsManager.StatsPullAtomCallback {
    public final Executor backgroundExecutor;
    public final CoroutineDispatcher mainDispatcher;
    public final NotifPipeline notificationPipeline;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class NotificationMemoryUseAtomBuilder {
        public int bigPictureBitmapCount;
        public int bigPictureObject;
        public int count;
        public int countWithInflatedViews;
        public int customViews;
        public int extenders;
        public int extras;
        public int largeIconBitmapCount;
        public int largeIconObject;
        public int largeIconViews;
        public int smallIconBitmapCount;
        public int smallIconObject;
        public int smallIconViews;
        public int softwareBitmaps;
        public final int style;
        public int styleViews;
        public int systemIconViews;
        public final int uid;

        public NotificationMemoryUseAtomBuilder(int i, int i2) {
            this.uid = i;
            this.style = i2;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof NotificationMemoryUseAtomBuilder)) {
                return false;
            }
            NotificationMemoryUseAtomBuilder notificationMemoryUseAtomBuilder = (NotificationMemoryUseAtomBuilder) obj;
            return this.uid == notificationMemoryUseAtomBuilder.uid && this.style == notificationMemoryUseAtomBuilder.style;
        }

        public final int hashCode() {
            return Integer.hashCode(this.style) + (Integer.hashCode(this.uid) * 31);
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("NotificationMemoryUseAtomBuilder(uid=");
            sb.append(this.uid);
            sb.append(", style=");
            return ConstraintWidget$$ExternalSyntheticOutline0.m19m(sb, this.style, ")");
        }
    }

    public NotificationMemoryLogger(NotifPipeline notifPipeline, StatsManager statsManager, CoroutineDispatcher coroutineDispatcher, Executor executor) {
        this.notificationPipeline = notifPipeline;
        this.mainDispatcher = coroutineDispatcher;
        this.backgroundExecutor = executor;
    }

    public final int onPullAtom(int i, List list) {
        int roundToInt;
        int roundToInt2;
        int roundToInt3;
        int roundToInt4;
        int roundToInt5;
        int roundToInt6;
        int roundToInt7;
        int roundToInt8;
        int roundToInt9;
        int roundToInt10;
        int roundToInt11;
        int roundToInt12;
        int roundToInt13;
        int roundToInt14;
        int roundToInt15;
        int roundToInt16;
        int roundToInt17;
        int roundToInt18;
        int roundToInt19;
        int roundToInt20;
        int roundToInt21;
        int roundToInt22;
        if (!Trace.isTagEnabled(4096L)) {
            if (i != 10174) {
                return 1;
            }
            try {
                Collection collection = (Collection) BuildersKt.runBlocking(this.mainDispatcher, new NotificationMemoryLogger$getAllNotificationsOnMainThread$1(this, null));
                NotificationMemoryMeter.INSTANCE.getClass();
                Iterator<Map.Entry<Pair<String, Integer>, NotificationMemoryUseAtomBuilder>> it = NotificationMemoryLoggerKt.aggregateMemoryUsageData(CollectionsKt___CollectionsKt.sortedWith(NotificationMemoryMeter.notificationMemoryUse(collection), ComparisonsKt__ComparisonsKt.compareBy(new Function1() { // from class: com.android.systemui.statusbar.notification.logging.NotificationMemoryLogger$onPullAtom$1$notificationMemoryUse$1
                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj) {
                        return ((NotificationMemoryUsage) obj).packageName;
                    }
                }, new Function1() { // from class: com.android.systemui.statusbar.notification.logging.NotificationMemoryLogger$onPullAtom$1$notificationMemoryUse$2
                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj) {
                        return Integer.valueOf(((NotificationMemoryUsage) obj).objectUsage.style);
                    }
                }, new Function1() { // from class: com.android.systemui.statusbar.notification.logging.NotificationMemoryLogger$onPullAtom$1$notificationMemoryUse$3
                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj) {
                        return ((NotificationMemoryUsage) obj).notificationKey;
                    }
                }))).entrySet().iterator();
                while (it.hasNext()) {
                    NotificationMemoryUseAtomBuilder value = it.next().getValue();
                    int i2 = value.uid;
                    int i3 = value.style;
                    int i4 = value.count;
                    int i5 = value.countWithInflatedViews;
                    roundToInt = MathKt__MathJVMKt.roundToInt(value.smallIconObject / 1024.0f);
                    int i6 = value.smallIconBitmapCount;
                    roundToInt2 = MathKt__MathJVMKt.roundToInt(value.largeIconObject / 1024.0f);
                    int i7 = value.largeIconBitmapCount;
                    roundToInt3 = MathKt__MathJVMKt.roundToInt(value.bigPictureObject / 1024.0f);
                    int i8 = value.bigPictureBitmapCount;
                    roundToInt4 = MathKt__MathJVMKt.roundToInt(value.extras / 1024.0f);
                    roundToInt5 = MathKt__MathJVMKt.roundToInt(value.extenders / 1024.0f);
                    roundToInt6 = MathKt__MathJVMKt.roundToInt(value.smallIconViews / 1024.0f);
                    roundToInt7 = MathKt__MathJVMKt.roundToInt(value.largeIconViews / 1024.0f);
                    roundToInt8 = MathKt__MathJVMKt.roundToInt(value.systemIconViews / 1024.0f);
                    roundToInt9 = MathKt__MathJVMKt.roundToInt(value.styleViews / 1024.0f);
                    roundToInt10 = MathKt__MathJVMKt.roundToInt(value.customViews / 1024.0f);
                    roundToInt11 = MathKt__MathJVMKt.roundToInt(value.softwareBitmaps / 1024.0f);
                    list.add(SysUiStatsLog.buildStatsEvent(i2, i3, i4, i5, roundToInt, i6, roundToInt2, i7, roundToInt3, i8, roundToInt4, roundToInt5, roundToInt6, roundToInt7, roundToInt8, roundToInt9, roundToInt10, roundToInt11));
                }
                return 0;
            } catch (InterruptedException e) {
                Log.w("NotificationLogger", "Timed out when measuring notification memory.", e);
                return 1;
            } catch (Exception e2) {
                Log.wtf("NotificationLogger", "Failed to measure notification memory.", e2);
                return 1;
            }
        }
        Trace.traceBegin(4096L, "NML#onPullAtom");
        try {
            if (i != 10174) {
                return 1;
            }
            try {
                Collection collection2 = (Collection) BuildersKt.runBlocking(this.mainDispatcher, new NotificationMemoryLogger$getAllNotificationsOnMainThread$1(this, null));
                NotificationMemoryMeter.INSTANCE.getClass();
                Iterator<Map.Entry<Pair<String, Integer>, NotificationMemoryUseAtomBuilder>> it2 = NotificationMemoryLoggerKt.aggregateMemoryUsageData(CollectionsKt___CollectionsKt.sortedWith(NotificationMemoryMeter.notificationMemoryUse(collection2), ComparisonsKt__ComparisonsKt.compareBy(new Function1() { // from class: com.android.systemui.statusbar.notification.logging.NotificationMemoryLogger$onPullAtom$1$notificationMemoryUse$1
                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj) {
                        return ((NotificationMemoryUsage) obj).packageName;
                    }
                }, new Function1() { // from class: com.android.systemui.statusbar.notification.logging.NotificationMemoryLogger$onPullAtom$1$notificationMemoryUse$2
                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj) {
                        return Integer.valueOf(((NotificationMemoryUsage) obj).objectUsage.style);
                    }
                }, new Function1() { // from class: com.android.systemui.statusbar.notification.logging.NotificationMemoryLogger$onPullAtom$1$notificationMemoryUse$3
                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj) {
                        return ((NotificationMemoryUsage) obj).notificationKey;
                    }
                }))).entrySet().iterator();
                while (it2.hasNext()) {
                    NotificationMemoryUseAtomBuilder value2 = it2.next().getValue();
                    int i9 = value2.uid;
                    int i10 = value2.style;
                    int i11 = value2.count;
                    int i12 = value2.countWithInflatedViews;
                    roundToInt12 = MathKt__MathJVMKt.roundToInt(value2.smallIconObject / 1024.0f);
                    int i13 = value2.smallIconBitmapCount;
                    roundToInt13 = MathKt__MathJVMKt.roundToInt(value2.largeIconObject / 1024.0f);
                    int i14 = value2.largeIconBitmapCount;
                    roundToInt14 = MathKt__MathJVMKt.roundToInt(value2.bigPictureObject / 1024.0f);
                    int i15 = value2.bigPictureBitmapCount;
                    roundToInt15 = MathKt__MathJVMKt.roundToInt(value2.extras / 1024.0f);
                    roundToInt16 = MathKt__MathJVMKt.roundToInt(value2.extenders / 1024.0f);
                    roundToInt17 = MathKt__MathJVMKt.roundToInt(value2.smallIconViews / 1024.0f);
                    roundToInt18 = MathKt__MathJVMKt.roundToInt(value2.largeIconViews / 1024.0f);
                    roundToInt19 = MathKt__MathJVMKt.roundToInt(value2.systemIconViews / 1024.0f);
                    roundToInt20 = MathKt__MathJVMKt.roundToInt(value2.styleViews / 1024.0f);
                    roundToInt21 = MathKt__MathJVMKt.roundToInt(value2.customViews / 1024.0f);
                    roundToInt22 = MathKt__MathJVMKt.roundToInt(value2.softwareBitmaps / 1024.0f);
                    list.add(SysUiStatsLog.buildStatsEvent(i9, i10, i11, i12, roundToInt12, i13, roundToInt13, i14, roundToInt14, i15, roundToInt15, roundToInt16, roundToInt17, roundToInt18, roundToInt19, roundToInt20, roundToInt21, roundToInt22));
                }
                Trace.traceEnd(4096L);
                return 0;
            } catch (InterruptedException e3) {
                Log.w("NotificationLogger", "Timed out when measuring notification memory.", e3);
                return 1;
            } catch (Exception e4) {
                Log.wtf("NotificationLogger", "Failed to measure notification memory.", e4);
                return 1;
            }
        } finally {
            Trace.traceEnd(4096L);
        }
    }
}
