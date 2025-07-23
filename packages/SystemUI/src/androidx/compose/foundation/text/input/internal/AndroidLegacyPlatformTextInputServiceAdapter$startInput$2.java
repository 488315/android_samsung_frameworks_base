package androidx.compose.foundation.text.input.internal;

import android.view.View;
import androidx.compose.foundation.text.input.internal.LegacyPlatformTextInputServiceAdapter;
import androidx.compose.ui.platform.PlatformTextInputSession;
import kotlin.KotlinNothingValueException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class AndroidLegacyPlatformTextInputServiceAdapter$startInput$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ Function1 $initializeRequest;
    final /* synthetic */ LegacyPlatformTextInputServiceAdapter.LegacyPlatformTextInputNode $node;
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ AndroidLegacyPlatformTextInputServiceAdapter this$0;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: androidx.compose.foundation.text.input.internal.AndroidLegacyPlatformTextInputServiceAdapter$startInput$2$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ PlatformTextInputSession $$this$launchTextInputSession;
        final /* synthetic */ Function1 $initializeRequest;
        final /* synthetic */ LegacyPlatformTextInputServiceAdapter.LegacyPlatformTextInputNode $node;
        private /* synthetic */ Object L$0;
        int label;
        final /* synthetic */ AndroidLegacyPlatformTextInputServiceAdapter this$0;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        /* renamed from: androidx.compose.foundation.text.input.internal.AndroidLegacyPlatformTextInputServiceAdapter$startInput$2$1$1, reason: invalid class name and collision with other inner class name */
        final class C00131 extends SuspendLambda implements Function2 {
            final /* synthetic */ InputMethodManager $inputMethodManager;
            int label;
            final /* synthetic */ AndroidLegacyPlatformTextInputServiceAdapter this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C00131(AndroidLegacyPlatformTextInputServiceAdapter androidLegacyPlatformTextInputServiceAdapter, InputMethodManager inputMethodManager, Continuation continuation) {
                super(2, continuation);
                this.this$0 = androidLegacyPlatformTextInputServiceAdapter;
                this.$inputMethodManager = inputMethodManager;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new C00131(this.this$0, this.$inputMethodManager, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((C00131) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            /* JADX WARN: Code restructure failed: missing block: B:16:0x0054, code lost:
            
                if (kotlinx.coroutines.flow.SharedFlowImpl.collect$suspendImpl(r1, r6, r5) == r0) goto L19;
             */
            /* JADX WARN: Code restructure failed: missing block: B:17:0x0056, code lost:
            
                return r0;
             */
            /* JADX WARN: Code restructure failed: missing block: B:19:0x0034, code lost:
            
                if (androidx.compose.runtime.MonotonicFrameClockKt.getMonotonicFrameClock(getContext()).withFrameNanos(new androidx.compose.runtime.MonotonicFrameClockKt$withFrameMillis$2(r6), r5) == r0) goto L19;
             */
            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public final java.lang.Object invokeSuspend(java.lang.Object r6) {
                /*
                    r5 = this;
                    kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                    int r1 = r5.label
                    r2 = 1
                    r3 = 2
                    if (r1 == 0) goto L1c
                    if (r1 == r2) goto L18
                    if (r1 == r3) goto L14
                    java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
                    java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
                    r5.<init>(r6)
                    throw r5
                L14:
                    kotlin.ResultKt.throwOnFailure(r6)
                    goto L57
                L18:
                    kotlin.ResultKt.throwOnFailure(r6)
                    goto L37
                L1c:
                    kotlin.ResultKt.throwOnFailure(r6)
                    androidx.compose.foundation.text.input.internal.AndroidLegacyPlatformTextInputServiceAdapter$startInput$2$1$1$1 r6 = new kotlin.jvm.functions.Function1() { // from class: androidx.compose.foundation.text.input.internal.AndroidLegacyPlatformTextInputServiceAdapter.startInput.2.1.1.1
                        static {
                            /*
                                androidx.compose.foundation.text.input.internal.AndroidLegacyPlatformTextInputServiceAdapter$startInput$2$1$1$1 r0 = new androidx.compose.foundation.text.input.internal.AndroidLegacyPlatformTextInputServiceAdapter$startInput$2$1$1$1
                                r0.<init>()
                                
                                // error: 0x0005: SPUT (r0 I:androidx.compose.foundation.text.input.internal.AndroidLegacyPlatformTextInputServiceAdapter$startInput$2$1$1$1) androidx.compose.foundation.text.input.internal.AndroidLegacyPlatformTextInputServiceAdapter.startInput.2.1.1.1.INSTANCE androidx.compose.foundation.text.input.internal.AndroidLegacyPlatformTextInputServiceAdapter$startInput$2$1$1$1
                                return
                            */
                            throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.text.input.internal.AndroidLegacyPlatformTextInputServiceAdapter$startInput$2.AnonymousClass1.C00131.C00141.<clinit>():void");
                        }

                        {
                            /*
                                r1 = this;
                                r0 = 1
                                r1.<init>(r0)
                                return
                            */
                            throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.text.input.internal.AndroidLegacyPlatformTextInputServiceAdapter$startInput$2.AnonymousClass1.C00131.C00141.<init>():void");
                        }

                        @Override // kotlin.jvm.functions.Function1
                        /* renamed from: invoke */
                        public final /* bridge */ /* synthetic */ java.lang.Object mo779invoke(java.lang.Object r1) {
                            /*
                                r0 = this;
                                java.lang.Number r1 = (java.lang.Number) r1
                                r1.longValue()
                                kotlin.Unit r0 = kotlin.Unit.INSTANCE
                                return r0
                            */
                            throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.text.input.internal.AndroidLegacyPlatformTextInputServiceAdapter$startInput$2.AnonymousClass1.C00131.C00141.mo779invoke(java.lang.Object):java.lang.Object");
                        }
                    }
                    r5.label = r2
                    kotlin.coroutines.CoroutineContext r1 = r5.getContext()
                    androidx.compose.runtime.MonotonicFrameClock r1 = androidx.compose.runtime.MonotonicFrameClockKt.getMonotonicFrameClock(r1)
                    androidx.compose.runtime.MonotonicFrameClockKt$withFrameMillis$2 r4 = new androidx.compose.runtime.MonotonicFrameClockKt$withFrameMillis$2
                    r4.<init>(r6)
                    java.lang.Object r6 = r1.withFrameNanos(r4, r5)
                    if (r6 != r0) goto L37
                    goto L56
                L37:
                    androidx.compose.foundation.text.input.internal.AndroidLegacyPlatformTextInputServiceAdapter r6 = r5.this$0
                    kotlinx.coroutines.flow.SharedFlowImpl r1 = r6.backingStylusHandwritingTrigger
                    if (r1 == 0) goto L3e
                    goto L47
                L3e:
                    kotlinx.coroutines.channels.BufferOverflow r1 = kotlinx.coroutines.channels.BufferOverflow.DROP_LATEST
                    r4 = 0
                    kotlinx.coroutines.flow.SharedFlowImpl r1 = kotlinx.coroutines.flow.SharedFlowKt.MutableSharedFlow$default(r2, r4, r1, r3)
                    r6.backingStylusHandwritingTrigger = r1
                L47:
                    androidx.compose.foundation.text.input.internal.AndroidLegacyPlatformTextInputServiceAdapter$startInput$2$1$1$2 r6 = new androidx.compose.foundation.text.input.internal.AndroidLegacyPlatformTextInputServiceAdapter$startInput$2$1$1$2
                    androidx.compose.foundation.text.input.internal.InputMethodManager r2 = r5.$inputMethodManager
                    r6.<init>()
                    r5.label = r3
                    kotlin.coroutines.intrinsics.CoroutineSingletons r5 = kotlinx.coroutines.flow.SharedFlowImpl.collect$suspendImpl(r1, r6, r5)
                    if (r5 != r0) goto L57
                L56:
                    return r0
                L57:
                    kotlin.KotlinNothingValueException r5 = new kotlin.KotlinNothingValueException
                    r5.<init>()
                    throw r5
                */
                throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.text.input.internal.AndroidLegacyPlatformTextInputServiceAdapter$startInput$2.AnonymousClass1.C00131.invokeSuspend(java.lang.Object):java.lang.Object");
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(PlatformTextInputSession platformTextInputSession, Function1 function1, AndroidLegacyPlatformTextInputServiceAdapter androidLegacyPlatformTextInputServiceAdapter, LegacyPlatformTextInputServiceAdapter.LegacyPlatformTextInputNode legacyPlatformTextInputNode, Continuation continuation) {
            super(2, continuation);
            this.$$this$launchTextInputSession = platformTextInputSession;
            this.$initializeRequest = function1;
            this.this$0 = androidLegacyPlatformTextInputServiceAdapter;
            this.$node = legacyPlatformTextInputNode;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$$this$launchTextInputSession, this.$initializeRequest, this.this$0, this.$node, continuation);
            anonymousClass1.L$0 = obj;
            return anonymousClass1;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            try {
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
                    Function1 function1 = LegacyPlatformTextInputServiceAdapter_androidKt.inputMethodManagerFactory;
                    View view = this.$$this$launchTextInputSession.getView();
                    ((LegacyPlatformTextInputServiceAdapter_androidKt$inputMethodManagerFactory$1) function1).getClass();
                    InputMethodManagerImpl inputMethodManagerImpl = new InputMethodManagerImpl(view);
                    LegacyTextInputMethodRequest legacyTextInputMethodRequest = new LegacyTextInputMethodRequest(this.$$this$launchTextInputSession.getView(), new AndroidLegacyPlatformTextInputServiceAdapter$startInput$2$1$request$1(this.$node), inputMethodManagerImpl);
                    BuildersKt.launch$default(coroutineScope, null, null, new C00131(this.this$0, inputMethodManagerImpl, null), 3);
                    Function1 function12 = this.$initializeRequest;
                    if (function12 != null) {
                        function12.mo779invoke(legacyTextInputMethodRequest);
                    }
                    this.this$0.currentRequest = legacyTextInputMethodRequest;
                    PlatformTextInputSession platformTextInputSession = this.$$this$launchTextInputSession;
                    this.label = 1;
                    if (platformTextInputSession.startInputMethod(legacyTextInputMethodRequest, this) == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                } else {
                    if (i != 1) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                }
                throw new KotlinNothingValueException();
            } catch (Throwable th) {
                this.this$0.currentRequest = null;
                throw th;
            }
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AndroidLegacyPlatformTextInputServiceAdapter$startInput$2(Function1 function1, AndroidLegacyPlatformTextInputServiceAdapter androidLegacyPlatformTextInputServiceAdapter, LegacyPlatformTextInputServiceAdapter.LegacyPlatformTextInputNode legacyPlatformTextInputNode, Continuation continuation) {
        super(2, continuation);
        this.$initializeRequest = function1;
        this.this$0 = androidLegacyPlatformTextInputServiceAdapter;
        this.$node = legacyPlatformTextInputNode;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        AndroidLegacyPlatformTextInputServiceAdapter$startInput$2 androidLegacyPlatformTextInputServiceAdapter$startInput$2 = new AndroidLegacyPlatformTextInputServiceAdapter$startInput$2(this.$initializeRequest, this.this$0, this.$node, continuation);
        androidLegacyPlatformTextInputServiceAdapter$startInput$2.L$0 = obj;
        return androidLegacyPlatformTextInputServiceAdapter$startInput$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((AndroidLegacyPlatformTextInputServiceAdapter$startInput$2) create((PlatformTextInputSession) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            AnonymousClass1 anonymousClass1 = new AnonymousClass1((PlatformTextInputSession) this.L$0, this.$initializeRequest, this.this$0, this.$node, null);
            this.label = 1;
            if (CoroutineScopeKt.coroutineScope(anonymousClass1, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        throw new KotlinNothingValueException();
    }
}
