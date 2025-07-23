package com.android.systemui.communal.ui.viewmodel;

import com.android.systemui.communal.shared.model.GlanceableHubMultiUserHelper;
import com.android.systemui.communal.widgets.AppWidgetHostListenerDelegate;
import com.android.systemui.lifecycle.ExclusiveActivatable;
import dagger.Lazy;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.channels.BufferOverflow;
import kotlinx.coroutines.channels.BufferedChannel;
import kotlinx.coroutines.channels.ChannelKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class CommunalAppWidgetViewModel extends ExclusiveActivatable {
    public final Lazy appWidgetHostLazy;
    public final CoroutineContext backgroundContext;
    public final AppWidgetHostListenerDelegate.Factory listenerDelegateFactory;
    public final GlanceableHubMultiUserHelper multiUserHelper;
    public final BufferedChannel requests = ChannelKt.Channel$default(10, BufferOverflow.DROP_OLDEST, null, 4);

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface Factory {
        CommunalAppWidgetViewModel create();
    }

    static {
        new Companion(null);
    }

    public CommunalAppWidgetViewModel(CoroutineContext coroutineContext, Lazy lazy, AppWidgetHostListenerDelegate.Factory factory, Lazy lazy2, GlanceableHubMultiUserHelper glanceableHubMultiUserHelper) {
        this.backgroundContext = coroutineContext;
        this.appWidgetHostLazy = lazy;
        this.listenerDelegateFactory = factory;
        this.multiUserHelper = glanceableHubMultiUserHelper;
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0032  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0024  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final java.lang.Object access$handleSetListener(com.android.systemui.communal.ui.viewmodel.CommunalAppWidgetViewModel r4, int r5, android.appwidget.AppWidgetHost.AppWidgetHostListener r6, kotlin.coroutines.Continuation r7) {
        /*
            r4.getClass()
            boolean r0 = r7 instanceof com.android.systemui.communal.ui.viewmodel.CommunalAppWidgetViewModel$handleSetListener$1
            if (r0 == 0) goto L16
            r0 = r7
            com.android.systemui.communal.ui.viewmodel.CommunalAppWidgetViewModel$handleSetListener$1 r0 = (com.android.systemui.communal.ui.viewmodel.CommunalAppWidgetViewModel$handleSetListener$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L16
            int r1 = r1 - r2
            r0.label = r1
            goto L1b
        L16:
            com.android.systemui.communal.ui.viewmodel.CommunalAppWidgetViewModel$handleSetListener$1 r0 = new com.android.systemui.communal.ui.viewmodel.CommunalAppWidgetViewModel$handleSetListener$1
            r0.<init>(r4, r7)
        L1b:
            java.lang.Object r7 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L32
            if (r2 != r3) goto L2a
            kotlin.ResultKt.throwOnFailure(r7)
            goto L46
        L2a:
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            r4.<init>(r5)
            throw r4
        L32:
            kotlin.ResultKt.throwOnFailure(r7)
            com.android.systemui.communal.ui.viewmodel.CommunalAppWidgetViewModel$handleSetListener$2 r7 = new com.android.systemui.communal.ui.viewmodel.CommunalAppWidgetViewModel$handleSetListener$2
            r2 = 0
            r7.<init>(r4, r5, r6, r2)
            r0.label = r3
            kotlin.coroutines.CoroutineContext r4 = r4.backgroundContext
            java.lang.Object r4 = kotlinx.coroutines.BuildersKt.withContext(r4, r7, r0)
            if (r4 != r1) goto L46
            return r1
        L46:
            kotlin.Unit r4 = kotlin.Unit.INSTANCE
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.communal.ui.viewmodel.CommunalAppWidgetViewModel.access$handleSetListener(com.android.systemui.communal.ui.viewmodel.CommunalAppWidgetViewModel, int, android.appwidget.AppWidgetHost$AppWidgetHostListener, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0032  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0024  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final java.lang.Object access$handleUpdateSize(com.android.systemui.communal.ui.viewmodel.CommunalAppWidgetViewModel r4, android.util.SizeF r5, android.appwidget.AppWidgetHostView r6, kotlin.coroutines.Continuation r7) {
        /*
            r4.getClass()
            boolean r0 = r7 instanceof com.android.systemui.communal.ui.viewmodel.CommunalAppWidgetViewModel$handleUpdateSize$1
            if (r0 == 0) goto L16
            r0 = r7
            com.android.systemui.communal.ui.viewmodel.CommunalAppWidgetViewModel$handleUpdateSize$1 r0 = (com.android.systemui.communal.ui.viewmodel.CommunalAppWidgetViewModel$handleUpdateSize$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L16
            int r1 = r1 - r2
            r0.label = r1
            goto L1b
        L16:
            com.android.systemui.communal.ui.viewmodel.CommunalAppWidgetViewModel$handleUpdateSize$1 r0 = new com.android.systemui.communal.ui.viewmodel.CommunalAppWidgetViewModel$handleUpdateSize$1
            r0.<init>(r4, r7)
        L1b:
            java.lang.Object r7 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L32
            if (r2 != r3) goto L2a
            kotlin.ResultKt.throwOnFailure(r7)
            goto L46
        L2a:
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            r4.<init>(r5)
            throw r4
        L32:
            kotlin.ResultKt.throwOnFailure(r7)
            com.android.systemui.communal.ui.viewmodel.CommunalAppWidgetViewModel$handleUpdateSize$2 r7 = new com.android.systemui.communal.ui.viewmodel.CommunalAppWidgetViewModel$handleUpdateSize$2
            r2 = 0
            r7.<init>(r6, r5, r2)
            r0.label = r3
            kotlin.coroutines.CoroutineContext r4 = r4.backgroundContext
            java.lang.Object r4 = kotlinx.coroutines.BuildersKt.withContext(r4, r7, r0)
            if (r4 != r1) goto L46
            return r1
        L46:
            kotlin.Unit r4 = kotlin.Unit.INSTANCE
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.communal.ui.viewmodel.CommunalAppWidgetViewModel.access$handleUpdateSize(com.android.systemui.communal.ui.viewmodel.CommunalAppWidgetViewModel, android.util.SizeF, android.appwidget.AppWidgetHostView, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX WARN: Code restructure failed: missing block: B:18:0x0050, code lost:
    
        if (kotlinx.coroutines.DelayKt.awaitCancellation(r0) != r1) goto L22;
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x0052, code lost:
    
        return r1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x0047, code lost:
    
        if (kotlinx.coroutines.CoroutineScopeKt.coroutineScope(r7, r0) == r1) goto L21;
     */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0036  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0022  */
    @Override // com.android.systemui.lifecycle.ExclusiveActivatable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object onActivated(kotlin.coroutines.Continuation r7) {
        /*
            r6 = this;
            boolean r0 = r7 instanceof com.android.systemui.communal.ui.viewmodel.CommunalAppWidgetViewModel$onActivated$1
            if (r0 == 0) goto L13
            r0 = r7
            com.android.systemui.communal.ui.viewmodel.CommunalAppWidgetViewModel$onActivated$1 r0 = (com.android.systemui.communal.ui.viewmodel.CommunalAppWidgetViewModel$onActivated$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.systemui.communal.ui.viewmodel.CommunalAppWidgetViewModel$onActivated$1 r0 = new com.android.systemui.communal.ui.viewmodel.CommunalAppWidgetViewModel$onActivated$1
            r0.<init>(r6, r7)
        L18:
            java.lang.Object r7 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 2
            r4 = 1
            if (r2 == 0) goto L36
            if (r2 == r4) goto L32
            if (r2 == r3) goto L2e
            java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
            java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
            r6.<init>(r7)
            throw r6
        L2e:
            kotlin.ResultKt.throwOnFailure(r7)
            goto L53
        L32:
            kotlin.ResultKt.throwOnFailure(r7)
            goto L4a
        L36:
            kotlin.ResultKt.throwOnFailure(r7)
            com.android.systemui.communal.ui.viewmodel.CommunalAppWidgetViewModel$onActivated$$inlined$coroutineScopeTraced$1 r7 = new com.android.systemui.communal.ui.viewmodel.CommunalAppWidgetViewModel$onActivated$$inlined$coroutineScopeTraced$1
            r2 = 0
            java.lang.String r5 = "CommunalAppWidgetViewModel#onActivated"
            r7.<init>(r2, r5, r6)
            r0.label = r4
            java.lang.Object r6 = kotlinx.coroutines.CoroutineScopeKt.coroutineScope(r7, r0)
            if (r6 != r1) goto L4a
            goto L52
        L4a:
            r0.label = r3
            kotlin.coroutines.intrinsics.CoroutineSingletons r6 = kotlinx.coroutines.DelayKt.awaitCancellation(r0)
            if (r6 != r1) goto L53
        L52:
            return r1
        L53:
            kotlin.KotlinNothingValueException r6 = new kotlin.KotlinNothingValueException
            r6.<init>()
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.communal.ui.viewmodel.CommunalAppWidgetViewModel.onActivated(kotlin.coroutines.Continuation):java.lang.Object");
    }
}
