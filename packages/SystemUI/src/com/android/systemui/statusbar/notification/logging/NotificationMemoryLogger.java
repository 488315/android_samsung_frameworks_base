package com.android.systemui.statusbar.notification.logging;

import android.app.StatsManager;
import android.os.Trace;
import android.util.Log;
import com.android.app.tracing.TraceUtilsKt;
import com.android.systemui.shared.system.SysUiStatsLog;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1;
import kotlin.comparisons.ComparisonsKt__ComparisonsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.math.MathKt__MathJVMKt;
import kotlin.sequences.SequencesKt___SequencesKt;
import kotlin.sequences.TransformingSequence;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes3.dex */
public final class NotificationMemoryLogger implements StatsManager.StatsPullAtomCallback {
    public final Executor backgroundExecutor;
    public final CoroutineDispatcher mainDispatcher;
    public final NotifPipeline notificationPipeline;
    public final StatsManager statsManager;

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
            return ReorderTile$$ExternalSyntheticOutline0.m(this.style, ")", sb);
        }
    }

    /* renamed from: com.android.systemui.statusbar.notification.logging.NotificationMemoryLogger$getAllNotificationsOnMainThread$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        int label;

        public AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return NotificationMemoryLogger.this.new AnonymousClass1(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            NotificationMemoryLogger notificationMemoryLogger = NotificationMemoryLogger.this;
            boolean zIsEnabled = Trace.isEnabled();
            if (zIsEnabled) {
                TraceUtilsKt.beginSlice("NML#getNotifications");
            }
            try {
                return CollectionsKt___CollectionsKt.toList(notificationMemoryLogger.notificationPipeline.getAllNotifs());
            } finally {
                if (zIsEnabled) {
                    TraceUtilsKt.endSlice();
                }
            }
        }
    }

    public NotificationMemoryLogger(NotifPipeline notifPipeline, StatsManager statsManager, CoroutineDispatcher coroutineDispatcher, Executor executor) {
        this.notificationPipeline = notifPipeline;
        this.statsManager = statsManager;
        this.mainDispatcher = coroutineDispatcher;
        this.backgroundExecutor = executor;
    }

    public final List getAllNotificationsOnMainThread() {
        return (List) BuildersKt.runBlocking(this.mainDispatcher, new AnonymousClass1(null));
    }

    /* JADX WARN: Removed duplicated region for block: B:35:0x00fc  */
    /* JADX WARN: Removed duplicated region for block: B:47:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final int onPullAtom(int i, List list) {
        int i2;
        boolean zIsEnabled = Trace.isEnabled();
        if (zIsEnabled) {
            TraceUtilsKt.beginSlice("NML#onPullAtom");
        }
        int i3 = 1;
        try {
            if (i != 10174) {
            }
            try {
                List allNotificationsOnMainThread = getAllNotificationsOnMainThread();
                NotificationMemoryMeter.INSTANCE.getClass();
                final int i4 = 0;
                final int i5 = 1;
                final int i6 = 2;
                Iterator<Map.Entry<Pair<String, Integer>, NotificationMemoryUseAtomBuilder>> it = NotificationMemoryLoggerKt.aggregateMemoryUsageData(CollectionsKt___CollectionsKt.sortedWith(SequencesKt___SequencesKt.toList(new TransformingSequence(new CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1(allNotificationsOnMainThread), new NotificationMemoryMeter$$ExternalSyntheticLambda0())), ComparisonsKt__ComparisonsKt.compareBy(new Function1() { // from class: com.android.systemui.statusbar.notification.logging.NotificationMemoryLogger$$ExternalSyntheticLambda0
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo781invoke(Object obj) {
                        NotificationMemoryUsage notificationMemoryUsage = (NotificationMemoryUsage) obj;
                        switch (i4) {
                            case 0:
                                return notificationMemoryUsage.packageName;
                            case 1:
                                return Integer.valueOf(notificationMemoryUsage.objectUsage.style);
                            default:
                                return notificationMemoryUsage.notificationKey;
                        }
                    }
                }, new Function1() { // from class: com.android.systemui.statusbar.notification.logging.NotificationMemoryLogger$$ExternalSyntheticLambda0
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo781invoke(Object obj) {
                        NotificationMemoryUsage notificationMemoryUsage = (NotificationMemoryUsage) obj;
                        switch (i5) {
                            case 0:
                                return notificationMemoryUsage.packageName;
                            case 1:
                                return Integer.valueOf(notificationMemoryUsage.objectUsage.style);
                            default:
                                return notificationMemoryUsage.notificationKey;
                        }
                    }
                }, new Function1() { // from class: com.android.systemui.statusbar.notification.logging.NotificationMemoryLogger$$ExternalSyntheticLambda0
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo781invoke(Object obj) {
                        NotificationMemoryUsage notificationMemoryUsage = (NotificationMemoryUsage) obj;
                        switch (i6) {
                            case 0:
                                return notificationMemoryUsage.packageName;
                            case 1:
                                return Integer.valueOf(notificationMemoryUsage.objectUsage.style);
                            default:
                                return notificationMemoryUsage.notificationKey;
                        }
                    }
                }))).entrySet().iterator();
                while (it.hasNext()) {
                    NotificationMemoryUseAtomBuilder value = it.next().getValue();
                    i2 = i3;
                    try {
                        list.add(SysUiStatsLog.buildStatsEvent(value.uid, value.style, value.count, value.countWithInflatedViews, MathKt__MathJVMKt.roundToInt(value.smallIconObject / 1024.0f), value.smallIconBitmapCount, MathKt__MathJVMKt.roundToInt(value.largeIconObject / 1024.0f), value.largeIconBitmapCount, MathKt__MathJVMKt.roundToInt(value.bigPictureObject / 1024.0f), value.bigPictureBitmapCount, MathKt__MathJVMKt.roundToInt(value.extras / 1024.0f), MathKt__MathJVMKt.roundToInt(value.extenders / 1024.0f), MathKt__MathJVMKt.roundToInt(value.smallIconViews / 1024.0f), MathKt__MathJVMKt.roundToInt(value.largeIconViews / 1024.0f), MathKt__MathJVMKt.roundToInt(value.systemIconViews / 1024.0f), MathKt__MathJVMKt.roundToInt(value.styleViews / 1024.0f), MathKt__MathJVMKt.roundToInt(value.customViews / 1024.0f), MathKt__MathJVMKt.roundToInt(value.softwareBitmaps / 1024.0f)));
                        i3 = i2;
                    } catch (InterruptedException e) {
                        e = e;
                        Log.w("NotificationLogger", "Timed out when measuring notification memory.", e);
                        if (zIsEnabled) {
                            return i2;
                        }
                        TraceUtilsKt.endSlice();
                        return i2;
                    } catch (Exception e2) {
                        e = e2;
                        Log.wtf("NotificationLogger", "Failed to measure notification memory.", e);
                        if (zIsEnabled) {
                        }
                    }
                }
                if (zIsEnabled) {
                    TraceUtilsKt.endSlice();
                }
                return 0;
            } catch (InterruptedException e3) {
                e = e3;
                i2 = i3;
            } catch (Exception e4) {
                e = e4;
                i2 = i3;
            }
        } finally {
            if (zIsEnabled) {
                TraceUtilsKt.endSlice();
            }
        }
    }
}
