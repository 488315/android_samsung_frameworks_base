package com.android.systemui.communal.ui.viewmodel;

import android.appwidget.AppWidgetHost;
import android.appwidget.AppWidgetHostView;
import android.util.SizeF;
import com.android.systemui.communal.shared.model.GlanceableHubMultiUserHelper;
import com.android.systemui.communal.widgets.AppWidgetHostListenerDelegate;
import com.android.systemui.lifecycle.ExclusiveActivatable;
import dagger.Lazy;
import kotlin.KotlinNothingValueException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.channels.BufferOverflow;
import kotlinx.coroutines.channels.BufferedChannel;
import kotlinx.coroutines.channels.ChannelKt;

/* loaded from: classes2.dex */
public final class CommunalAppWidgetViewModel extends ExclusiveActivatable {
    public final Lazy appWidgetHostLazy;
    public final CoroutineContext backgroundContext;
    public final AppWidgetHostListenerDelegate.Factory listenerDelegateFactory;
    public final GlanceableHubMultiUserHelper multiUserHelper;
    public final BufferedChannel requests = ChannelKt.Channel$default(10, BufferOverflow.DROP_OLDEST, null, 4);

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public interface Factory {
        CommunalAppWidgetViewModel create();
    }

    /* renamed from: com.android.systemui.communal.ui.viewmodel.CommunalAppWidgetViewModel$onActivated$1, reason: invalid class name */
    final class AnonymousClass1 extends ContinuationImpl {
        int label;
        /* synthetic */ Object result;

        public AnonymousClass1(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return CommunalAppWidgetViewModel.this.onActivated(this);
        }
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

    /* JADX WARN: Removed duplicated region for block: B:7:0x0016  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Object access$handleSetListener(CommunalAppWidgetViewModel communalAppWidgetViewModel, int i, AppWidgetHost.AppWidgetHostListener appWidgetHostListener, Continuation continuation) {
        CommunalAppWidgetViewModel$handleSetListener$1 communalAppWidgetViewModel$handleSetListener$1;
        communalAppWidgetViewModel.getClass();
        if (continuation instanceof CommunalAppWidgetViewModel$handleSetListener$1) {
            communalAppWidgetViewModel$handleSetListener$1 = (CommunalAppWidgetViewModel$handleSetListener$1) continuation;
            int i2 = communalAppWidgetViewModel$handleSetListener$1.label;
            if ((i2 & Integer.MIN_VALUE) != 0) {
                communalAppWidgetViewModel$handleSetListener$1.label = i2 - Integer.MIN_VALUE;
            } else {
                communalAppWidgetViewModel$handleSetListener$1 = new CommunalAppWidgetViewModel$handleSetListener$1(communalAppWidgetViewModel, continuation);
            }
        }
        Object obj = communalAppWidgetViewModel$handleSetListener$1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i3 = communalAppWidgetViewModel$handleSetListener$1.label;
        if (i3 == 0) {
            ResultKt.throwOnFailure(obj);
            CommunalAppWidgetViewModel$handleSetListener$2 communalAppWidgetViewModel$handleSetListener$2 = new CommunalAppWidgetViewModel$handleSetListener$2(communalAppWidgetViewModel, i, appWidgetHostListener, null);
            communalAppWidgetViewModel$handleSetListener$1.label = 1;
            if (BuildersKt.withContext(communalAppWidgetViewModel.backgroundContext, communalAppWidgetViewModel$handleSetListener$2, communalAppWidgetViewModel$handleSetListener$1) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i3 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        return Unit.INSTANCE;
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0016  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Object access$handleUpdateSize(CommunalAppWidgetViewModel communalAppWidgetViewModel, SizeF sizeF, AppWidgetHostView appWidgetHostView, Continuation continuation) {
        CommunalAppWidgetViewModel$handleUpdateSize$1 communalAppWidgetViewModel$handleUpdateSize$1;
        communalAppWidgetViewModel.getClass();
        if (continuation instanceof CommunalAppWidgetViewModel$handleUpdateSize$1) {
            communalAppWidgetViewModel$handleUpdateSize$1 = (CommunalAppWidgetViewModel$handleUpdateSize$1) continuation;
            int i = communalAppWidgetViewModel$handleUpdateSize$1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                communalAppWidgetViewModel$handleUpdateSize$1.label = i - Integer.MIN_VALUE;
            } else {
                communalAppWidgetViewModel$handleUpdateSize$1 = new CommunalAppWidgetViewModel$handleUpdateSize$1(communalAppWidgetViewModel, continuation);
            }
        }
        Object obj = communalAppWidgetViewModel$handleUpdateSize$1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = communalAppWidgetViewModel$handleUpdateSize$1.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            CommunalAppWidgetViewModel$handleUpdateSize$2 communalAppWidgetViewModel$handleUpdateSize$2 = new CommunalAppWidgetViewModel$handleUpdateSize$2(appWidgetHostView, sizeF, null);
            communalAppWidgetViewModel$handleUpdateSize$1.label = 1;
            if (BuildersKt.withContext(communalAppWidgetViewModel.backgroundContext, communalAppWidgetViewModel$handleUpdateSize$2, communalAppWidgetViewModel$handleUpdateSize$1) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        return Unit.INSTANCE;
    }

    /* JADX WARN: Code restructure failed: missing block: B:20:0x0050, code lost:
    
        if (kotlinx.coroutines.DelayKt.awaitCancellation(r0) == r1) goto L21;
     */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    @Override // com.android.systemui.lifecycle.ExclusiveActivatable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object onActivated(Continuation continuation) {
        AnonymousClass1 anonymousClass1;
        if (continuation instanceof AnonymousClass1) {
            anonymousClass1 = (AnonymousClass1) continuation;
            int i = anonymousClass1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                anonymousClass1.label = i - Integer.MIN_VALUE;
            } else {
                anonymousClass1 = new AnonymousClass1(continuation);
            }
        }
        Object obj = anonymousClass1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = anonymousClass1.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            CommunalAppWidgetViewModel$onActivated$$inlined$coroutineScopeTraced$1 communalAppWidgetViewModel$onActivated$$inlined$coroutineScopeTraced$1 = new CommunalAppWidgetViewModel$onActivated$$inlined$coroutineScopeTraced$1(null, "CommunalAppWidgetViewModel#onActivated", this);
            anonymousClass1.label = 1;
            if (CoroutineScopeKt.coroutineScope(communalAppWidgetViewModel$onActivated$$inlined$coroutineScopeTraced$1, anonymousClass1) != coroutineSingletons) {
            }
            return coroutineSingletons;
        }
        if (i2 != 1) {
            if (i2 != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            throw new KotlinNothingValueException();
        }
        ResultKt.throwOnFailure(obj);
        anonymousClass1.label = 2;
    }
}
