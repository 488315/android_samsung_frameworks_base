package com.android.systemui.statusbar.notification.stack.ui.view;

import android.service.notification.NotificationListenerService;
import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.internal.statusbar.IStatusBarService;
import com.android.internal.statusbar.NotificationVisibility;
import com.android.systemui.statusbar.notification.logging.NotificationPanelLogger;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.channels.BufferOverflow;
import kotlinx.coroutines.channels.BufferedChannel;
import kotlinx.coroutines.channels.ChannelKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class NotificationStatsLoggerImpl implements NotificationStatsLogger {
    public final CoroutineScope applicationScope;
    public final CoroutineDispatcher bgDispatcher;
    public final NotificationListenerService notificationListenerService;
    public final NotificationPanelLogger notificationPanelLogger;
    public final IStatusBarService statusBarService;
    public final Map expansionStates = new ConcurrentHashMap();
    public final Map lastReportedExpansionValues = new ConcurrentHashMap();
    public final BufferedChannel visibilityLogger = ChannelKt.Channel$default(2, BufferOverflow.DROP_OLDEST, null, 4);

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.statusbar.notification.stack.ui.view.NotificationStatsLoggerImpl$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        int label;

        public AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return NotificationStatsLoggerImpl.this.new AnonymousClass1(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                NotificationStatsLoggerImpl notificationStatsLoggerImpl = NotificationStatsLoggerImpl.this;
                this.label = 1;
                if (NotificationStatsLoggerImpl.access$consumeVisibilityActions(notificationStatsLoggerImpl, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            return Unit.INSTANCE;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract class VisibilityAction {
        public final int activeCount;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        public final class Change extends VisibilityAction {
            public final int activeCount;
            public final Map visibilities;

            public Change(Map<String, VisibilityState> map, int i) {
                super(i, null);
                this.visibilities = map;
                this.activeCount = i;
            }

            public final boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                if (!(obj instanceof Change)) {
                    return false;
                }
                Change change = (Change) obj;
                return Intrinsics.areEqual(this.visibilities, change.visibilities) && this.activeCount == change.activeCount;
            }

            @Override // com.android.systemui.statusbar.notification.stack.ui.view.NotificationStatsLoggerImpl.VisibilityAction
            public final int getActiveCount() {
                return this.activeCount;
            }

            public final int hashCode() {
                return Integer.hashCode(this.activeCount) + (this.visibilities.hashCode() * 31);
            }

            public final String toString() {
                return "Change(visibilities=" + this.visibilities + ", activeCount=" + this.activeCount + ")";
            }
        }

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        public final class Clear extends VisibilityAction {
            public final int activeCount;

            public Clear(int i) {
                super(i, null);
                this.activeCount = i;
            }

            public final boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                return (obj instanceof Clear) && this.activeCount == ((Clear) obj).activeCount;
            }

            @Override // com.android.systemui.statusbar.notification.stack.ui.view.NotificationStatsLoggerImpl.VisibilityAction
            public final int getActiveCount() {
                return this.activeCount;
            }

            public final int hashCode() {
                return Integer.hashCode(this.activeCount);
            }

            public final String toString() {
                return ReorderTile$$ExternalSyntheticOutline0.m(this.activeCount, ")", new StringBuilder("Clear(activeCount="));
            }
        }

        public /* synthetic */ VisibilityAction(int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(i);
        }

        public int getActiveCount() {
            return this.activeCount;
        }

        private VisibilityAction(int i) {
            this.activeCount = i;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class VisibilityState {
        public final String key;
        public final int location;
        public final int rank;

        public VisibilityState(String str, int i, int i2) {
            this.key = str;
            this.location = i;
            this.rank = i2;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof VisibilityState)) {
                return false;
            }
            VisibilityState visibilityState = (VisibilityState) obj;
            return Intrinsics.areEqual(this.key, visibilityState.key) && this.location == visibilityState.location && this.rank == visibilityState.rank;
        }

        public final int hashCode() {
            return Integer.hashCode(this.rank) + ReorderTile$$ExternalSyntheticOutline0.m(this.location, this.key.hashCode() * 31, 31);
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("VisibilityState(key=");
            sb.append(this.key);
            sb.append(", location=");
            sb.append(this.location);
            sb.append(", rank=");
            return ReorderTile$$ExternalSyntheticOutline0.m(this.rank, ")", sb);
        }
    }

    public NotificationStatsLoggerImpl(CoroutineScope coroutineScope, CoroutineDispatcher coroutineDispatcher, NotificationListenerService notificationListenerService, NotificationPanelLogger notificationPanelLogger, IStatusBarService iStatusBarService) {
        this.applicationScope = coroutineScope;
        this.bgDispatcher = coroutineDispatcher;
        this.notificationListenerService = notificationListenerService;
        this.notificationPanelLogger = notificationPanelLogger;
        this.statusBarService = iStatusBarService;
        CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass1(null), 7);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:18:0x00a8  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x00b5 A[Catch: all -> 0x00c9, TryCatch #2 {all -> 0x00c9, blocks: (B:21:0x00ad, B:23:0x00b5, B:25:0x00bf, B:26:0x00d3, B:28:0x012a, B:30:0x0130, B:33:0x0136, B:36:0x0154, B:37:0x00cb, B:39:0x00cf, B:40:0x0186, B:41:0x018b, B:43:0x018c), top: B:20:0x00ad }] */
    /* JADX WARN: Removed duplicated region for block: B:43:0x018c A[Catch: all -> 0x00c9, TRY_LEAVE, TryCatch #2 {all -> 0x00c9, blocks: (B:21:0x00ad, B:23:0x00b5, B:25:0x00bf, B:26:0x00d3, B:28:0x012a, B:30:0x0130, B:33:0x0136, B:36:0x0154, B:37:0x00cb, B:39:0x00cf, B:40:0x0186, B:41:0x018b, B:43:0x018c), top: B:20:0x00ad }] */
    /* JADX WARN: Removed duplicated region for block: B:62:0x007c  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x002c  */
    /* JADX WARN: Type inference failed for: r13v4, types: [java.util.Map] */
    /* JADX WARN: Type inference failed for: r5v9, types: [java.util.Map] */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:32:0x0159 -> B:14:0x0161). Please report as a decompilation issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final java.lang.Object access$consumeVisibilityActions(com.android.systemui.statusbar.notification.stack.ui.view.NotificationStatsLoggerImpl r17, kotlin.coroutines.jvm.internal.ContinuationImpl r18) {
        /*
            Method dump skipped, instructions count: 410
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.stack.ui.view.NotificationStatsLoggerImpl.access$consumeVisibilityActions(com.android.systemui.statusbar.notification.stack.ui.view.NotificationStatsLoggerImpl, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    public static NotificationVisibility[] mapToNotificationVisibilitiesAr(Map map, boolean z, int i) {
        ArrayList arrayList = new ArrayList(map.size());
        for (Map.Entry entry : map.entrySet()) {
            String str = (String) entry.getKey();
            VisibilityState visibilityState = (VisibilityState) entry.getValue();
            arrayList.add(NotificationVisibility.obtain(str, visibilityState.rank, i, z, NotificationStatsLoggerImplKt.access$toNotificationLocation(visibilityState.location)));
        }
        return (NotificationVisibility[]) arrayList.toArray(new NotificationVisibility[0]);
    }

    public final void maybeLogNotificationExpansionChange(ExpansionState expansionState) {
        if (expansionState.visible) {
            ConcurrentHashMap concurrentHashMap = (ConcurrentHashMap) this.lastReportedExpansionValues;
            String str = expansionState.key;
            Boolean bool = (Boolean) concurrentHashMap.get(str);
            boolean z = expansionState.isExpanded;
            if (bool != null || z) {
                if (bool == null || !bool.equals(Boolean.valueOf(z))) {
                    CoroutineTracingKt.launchTraced$default(this.applicationScope, null, null, new NotificationStatsLoggerImpl$logNotificationExpansionChange$1(this, expansionState, null), 7);
                    ((ConcurrentHashMap) this.lastReportedExpansionValues).put(str, Boolean.valueOf(z));
                }
            }
        }
    }

    public final void onLockscreenOrShadeInteractive(List list, boolean z) {
        CoroutineTracingKt.launchTraced$default(this.applicationScope, null, null, new NotificationStatsLoggerImpl$onLockscreenOrShadeInteractive$1(this, z, list, null), 7);
    }

    @Override // com.android.systemui.statusbar.notification.stack.ui.view.NotificationRowStatsLogger
    public final void onNotificationExpansionChanged(String str, int i, boolean z, boolean z2) {
        ExpansionState expansionState = new ExpansionState(str, z, i, z2);
        ((ConcurrentHashMap) this.expansionStates).put(str, expansionState);
        maybeLogNotificationExpansionChange(expansionState);
    }

    public final void updateExpansionStates(Map map, Map map2) {
        for (Map.Entry entry : ((ConcurrentHashMap) this.expansionStates).entrySet()) {
            String str = (String) entry.getKey();
            ExpansionState expansionState = (ExpansionState) entry.getValue();
            if (map.containsKey(str)) {
                ExpansionState copy$default = ExpansionState.copy$default(expansionState, true, ((VisibilityState) MapsKt__MapsKt.getValue(str, map)).location);
                ((ConcurrentHashMap) this.expansionStates).put(str, copy$default);
                maybeLogNotificationExpansionChange(copy$default);
            }
            if (map2.containsKey(str)) {
                ((ConcurrentHashMap) this.expansionStates).put(str, ExpansionState.copy$default(expansionState, false, ((VisibilityState) MapsKt__MapsKt.getValue(str, map2)).location));
            }
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class ExpansionState {
        public final boolean isExpanded;
        public final boolean isUserAction;
        public final String key;
        public final int location;
        public final boolean visible;

        public ExpansionState(String str, boolean z, boolean z2, boolean z3, int i) {
            this.key = str;
            this.isUserAction = z;
            this.isExpanded = z2;
            this.visible = z3;
            this.location = i;
        }

        public static ExpansionState copy$default(ExpansionState expansionState, boolean z, int i) {
            String str = expansionState.key;
            boolean z2 = expansionState.isUserAction;
            boolean z3 = expansionState.isExpanded;
            expansionState.getClass();
            return new ExpansionState(str, z2, z3, z, i);
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof ExpansionState)) {
                return false;
            }
            ExpansionState expansionState = (ExpansionState) obj;
            return Intrinsics.areEqual(this.key, expansionState.key) && this.isUserAction == expansionState.isUserAction && this.isExpanded == expansionState.isExpanded && this.visible == expansionState.visible && this.location == expansionState.location;
        }

        public final int hashCode() {
            return Integer.hashCode(this.location) + TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(this.key.hashCode() * 31, 31, this.isUserAction), 31, this.isExpanded), 31, this.visible);
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("ExpansionState(key=");
            sb.append(this.key);
            sb.append(", isUserAction=");
            sb.append(this.isUserAction);
            sb.append(", isExpanded=");
            sb.append(this.isExpanded);
            sb.append(", visible=");
            sb.append(this.visible);
            sb.append(", location=");
            return ReorderTile$$ExternalSyntheticOutline0.m(this.location, ")", sb);
        }

        public ExpansionState(String str, boolean z, int i, boolean z2) {
            this(str, z2, z, (i & 5) != 0, i);
        }
    }

    public static /* synthetic */ void getLastReportedExpansionValues$annotations() {
    }
}
