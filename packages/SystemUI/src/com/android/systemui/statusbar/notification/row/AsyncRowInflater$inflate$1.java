package com.android.systemui.statusbar.notification.row;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import com.android.systemui.statusbar.notification.row.AsyncRowInflater;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes3.dex */
final class AsyncRowInflater$inflate$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ BasicRowInflater $inflater;
    final /* synthetic */ AsyncRowInflater.OnInflateFinishedListener $listener;
    final /* synthetic */ ViewGroup $parent;
    final /* synthetic */ int $resId;
    int label;
    final /* synthetic */ AsyncRowInflater this$0;

    /* renamed from: com.android.systemui.statusbar.notification.row.AsyncRowInflater$inflate$1$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ BasicRowInflater $inflater;
        final /* synthetic */ AsyncRowInflater.OnInflateFinishedListener $listener;
        final /* synthetic */ ViewGroup $parent;
        final /* synthetic */ int $resId;
        final /* synthetic */ View $view;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(View view, BasicRowInflater basicRowInflater, int i, ViewGroup viewGroup, AsyncRowInflater.OnInflateFinishedListener onInflateFinishedListener, Continuation continuation) {
            super(2, continuation);
            this.$view = view;
            this.$inflater = basicRowInflater;
            this.$resId = i;
            this.$parent = viewGroup;
            this.$listener = onInflateFinishedListener;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass1(this.$view, this.$inflater, this.$resId, this.$parent, this.$listener, continuation);
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
            View viewInflate = this.$view;
            if (viewInflate == null) {
                viewInflate = this.$inflater.inflate(this.$resId, this.$parent, false);
            }
            AsyncRowInflater.OnInflateFinishedListener onInflateFinishedListener = this.$listener;
            viewInflate.getClass();
            ((RowInflaterTask) onInflateFinishedListener).onInflateFinished(this.$resId, viewInflate, this.$parent);
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AsyncRowInflater$inflate$1(BasicRowInflater basicRowInflater, int i, ViewGroup viewGroup, AsyncRowInflater asyncRowInflater, AsyncRowInflater.OnInflateFinishedListener onInflateFinishedListener, Continuation continuation) {
        super(2, continuation);
        this.$inflater = basicRowInflater;
        this.$resId = i;
        this.$parent = viewGroup;
        this.this$0 = asyncRowInflater;
        this.$listener = onInflateFinishedListener;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new AsyncRowInflater$inflate$1(this.$inflater, this.$resId, this.$parent, this.this$0, this.$listener, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((AsyncRowInflater$inflate$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        View viewInflate;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            try {
                viewInflate = this.$inflater.inflate(this.$resId, this.$parent, false);
            } catch (RuntimeException e) {
                Log.w("AsyncRowInflater", "Failed to inflate resource in the background! Retrying on the UI thread", e);
                viewInflate = null;
            }
            View view = viewInflate;
            CoroutineDispatcher coroutineDispatcher = this.this$0.mainCoroutineDispatcher;
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(view, this.$inflater, this.$resId, this.$parent, this.$listener, null);
            this.label = 1;
            if (BuildersKt.withContext(coroutineDispatcher, anonymousClass1, this) == coroutineSingletons) {
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
