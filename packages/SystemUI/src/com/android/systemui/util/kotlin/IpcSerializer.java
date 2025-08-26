package com.android.systemui.util.kotlin;

import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CompletableDeferred;
import kotlinx.coroutines.CompletableDeferredImpl;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.channels.BufferedChannel;
import kotlinx.coroutines.channels.Channel;
import kotlinx.coroutines.channels.ChannelKt;

/* loaded from: classes3.dex */
public final class IpcSerializer {
    public static final int $stable = 8;
    private final Channel channel = ChannelKt.Channel$default(0, null, null, 7);

    /* renamed from: com.android.systemui.util.kotlin.IpcSerializer$process$1, reason: invalid class name */
    final class AnonymousClass1 extends ContinuationImpl {
        Object L$0;
        int label;
        /* synthetic */ Object result;

        public AnonymousClass1(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return IpcSerializer.this.process(this);
        }
    }

    /* renamed from: com.android.systemui.util.kotlin.IpcSerializer$runSerialized$1, reason: invalid class name and case insensitive filesystem */
    final class C11611<R> extends ContinuationImpl {
        Object L$0;
        Object L$1;
        Object L$2;
        int label;
        /* synthetic */ Object result;

        public C11611(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return IpcSerializer.this.runSerialized(null, this);
        }
    }

    /* renamed from: com.android.systemui.util.kotlin.IpcSerializer$runSerializedBlocking$1, reason: invalid class name and case insensitive filesystem */
    final class C11621 extends SuspendLambda implements Function2 {
        final /* synthetic */ Function1 $block;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C11621(Function1 function1, Continuation continuation) {
            super(2, continuation);
            this.$block = function1;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return IpcSerializer.this.new C11621(this.$block, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i != 0) {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                return obj;
            }
            ResultKt.throwOnFailure(obj);
            IpcSerializer ipcSerializer = IpcSerializer.this;
            Function1 function1 = this.$block;
            this.label = 1;
            Object objRunSerialized = ipcSerializer.runSerialized(function1, this);
            return objRunSerialized == coroutineSingletons ? coroutineSingletons : objRunSerialized;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((C11621) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:22:0x005a  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x007e  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:23:0x007b -> B:17:0x0047). Please report as a decompilation issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object process(Continuation continuation) throws Throwable {
        AnonymousClass1 anonymousClass1;
        BufferedChannel.BufferedChannelIterator it;
        if (continuation instanceof AnonymousClass1) {
            anonymousClass1 = (AnonymousClass1) continuation;
            int i = anonymousClass1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                anonymousClass1.label = i - Integer.MIN_VALUE;
            } else {
                anonymousClass1 = new AnonymousClass1(continuation);
            }
        }
        Object objHasNext = anonymousClass1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = anonymousClass1.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(objHasNext);
            it = this.channel.iterator();
        } else {
            if (i2 == 1) {
                it = (BufferedChannel.BufferedChannelIterator) anonymousClass1.L$0;
                ResultKt.throwOnFailure(objHasNext);
                if (((Boolean) objHasNext).booleanValue()) {
                    throw new IllegalStateException("Unexpected end of serialization channel");
                }
                Pair pair = (Pair) it.next();
                CompletableDeferred completableDeferred = (CompletableDeferred) pair.component1();
                Job job = (Job) pair.component2();
                ((CompletableDeferredImpl) completableDeferred).makeCompleting$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(Unit.INSTANCE);
                anonymousClass1.L$0 = it;
                anonymousClass1.label = 2;
                if (job.join(anonymousClass1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            }
            if (i2 != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            it = (BufferedChannel.BufferedChannelIterator) anonymousClass1.L$0;
            ResultKt.throwOnFailure(objHasNext);
        }
        anonymousClass1.L$0 = it;
        anonymousClass1.label = 1;
        objHasNext = it.hasNext(anonymousClass1);
        if (objHasNext != coroutineSingletons) {
            if (((Boolean) objHasNext).booleanValue()) {
            }
        }
        return coroutineSingletons;
    }

    /* JADX WARN: Code restructure failed: missing block: B:27:0x00a2, code lost:
    
        if (r10 != r1) goto L29;
     */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final <R> Object runSerialized(Function1 function1, Continuation continuation) {
        C11611 c11611;
        Function1 function12;
        CompletableDeferred completableDeferred;
        CompletableDeferred completableDeferred2;
        Function1 function13;
        if (continuation instanceof C11611) {
            c11611 = (C11611) continuation;
            int i = c11611.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                c11611.label = i - Integer.MIN_VALUE;
            } else {
                c11611 = new C11611(continuation);
            }
        }
        Object objMo781invoke = c11611.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = c11611.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(objMo781invoke);
            Unit unit = Unit.INSTANCE;
            CompletableDeferredImpl completableDeferredImpl = new CompletableDeferredImpl(null);
            completableDeferredImpl.makeCompleting$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(unit);
            CompletableDeferredImpl completableDeferredImpl2 = new CompletableDeferredImpl(null);
            completableDeferredImpl2.makeCompleting$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(unit);
            Channel channel = this.channel;
            Pair pair = new Pair(completableDeferredImpl, completableDeferredImpl2);
            c11611.L$0 = function1;
            c11611.L$1 = completableDeferredImpl;
            c11611.L$2 = completableDeferredImpl2;
            c11611.label = 1;
            if (channel.send(pair, c11611) != coroutineSingletons) {
                function12 = function1;
                completableDeferred = completableDeferredImpl;
                completableDeferred2 = completableDeferredImpl2;
            }
            return coroutineSingletons;
        }
        if (i2 == 1) {
            completableDeferred2 = (CompletableDeferred) c11611.L$2;
            completableDeferred = (CompletableDeferred) c11611.L$1;
            function12 = (Function1) c11611.L$0;
            ResultKt.throwOnFailure(objMo781invoke);
        } else {
            if (i2 != 2) {
                if (i2 != 3) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                completableDeferred2 = (CompletableDeferred) c11611.L$0;
                ResultKt.throwOnFailure(objMo781invoke);
                ((CompletableDeferredImpl) completableDeferred2).makeCompleting$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(Unit.INSTANCE);
                return objMo781invoke;
            }
            completableDeferred2 = (CompletableDeferred) c11611.L$1;
            function13 = (Function1) c11611.L$0;
            ResultKt.throwOnFailure(objMo781invoke);
            c11611.L$0 = completableDeferred2;
            c11611.L$1 = null;
            c11611.label = 3;
            objMo781invoke = function13.mo781invoke(c11611);
        }
        c11611.L$0 = function12;
        c11611.L$1 = completableDeferred2;
        c11611.L$2 = null;
        c11611.label = 2;
        if (((CompletableDeferredImpl) completableDeferred).awaitInternal(c11611) != coroutineSingletons) {
            function13 = function12;
            c11611.L$0 = completableDeferred2;
            c11611.L$1 = null;
            c11611.label = 3;
            objMo781invoke = function13.mo781invoke(c11611);
        }
        return coroutineSingletons;
    }

    public final <R> R runSerializedBlocking(Function1 function1) {
        return (R) BuildersKt.runBlocking(EmptyCoroutineContext.INSTANCE, new C11621(function1, null));
    }
}
