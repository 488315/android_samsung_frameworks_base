package com.android.systemui.media.controls.ui.controller;

import com.android.systemui.util.settings.SettingsProxyExt;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;

final class MediaCarouselController$listenForLockscreenSettingChanges$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ MediaCarouselController this$0;

    /* renamed from: com.android.systemui.media.controls.ui.controller.MediaCarouselController$listenForLockscreenSettingChanges$1$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        private /* synthetic */ Object L$0;
        int label;

        public AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(continuation);
            anonymousClass1.L$0 = obj;
            return anonymousClass1;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((FlowCollector) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                FlowCollector flowCollector = (FlowCollector) this.L$0;
                Unit unit = Unit.INSTANCE;
                this.label = 1;
                if (flowCollector.emit(unit, this) == coroutineSingletons) {
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

    /* renamed from: com.android.systemui.media.controls.ui.controller.MediaCarouselController$listenForLockscreenSettingChanges$1$3, reason: invalid class name */
    final class AnonymousClass3 extends SuspendLambda implements Function2 {
        /* synthetic */ boolean Z$0;
        int label;
        final /* synthetic */ MediaCarouselController this$0;

        public AnonymousClass3(MediaCarouselController mediaCarouselController, Continuation continuation) {
            super(2, continuation);
            this.this$0 = mediaCarouselController;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass3 anonymousClass3 = new AnonymousClass3(this.this$0, continuation);
            anonymousClass3.Z$0 = ((Boolean) obj).booleanValue();
            return anonymousClass3;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            Boolean bool = (Boolean) obj;
            bool.booleanValue();
            return ((AnonymousClass3) create(bool, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            boolean z = this.Z$0;
            MediaCarouselController mediaCarouselController = this.this$0;
            mediaCarouselController.allowMediaPlayerOnLockScreen = z;
            mediaCarouselController.updateHostVisibility.invoke();
            return Unit.INSTANCE;
        }
    }

    public MediaCarouselController$listenForLockscreenSettingChanges$1(MediaCarouselController mediaCarouselController, Continuation continuation) {
        super(2, continuation);
        this.this$0 = mediaCarouselController;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new MediaCarouselController$listenForLockscreenSettingChanges$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((MediaCarouselController$listenForLockscreenSettingChanges$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 = new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new AnonymousClass1(null), SettingsProxyExt.INSTANCE.observerFlow(this.this$0.secureSettings, -1, "media_controls_lock_screen"));
            final MediaCarouselController mediaCarouselController = this.this$0;
            Flow distinctUntilChanged = FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.media.controls.ui.controller.MediaCarouselController$listenForLockscreenSettingChanges$1$invokeSuspend$$inlined$map$1

                /* renamed from: com.android.systemui.media.controls.ui.controller.MediaCarouselController$listenForLockscreenSettingChanges$1$invokeSuspend$$inlined$map$1$2, reason: invalid class name */
                public final class AnonymousClass2 implements FlowCollector {
                    public final /* synthetic */ FlowCollector $this_unsafeFlow;
                    public final /* synthetic */ MediaCarouselController this$0;

                    /* renamed from: com.android.systemui.media.controls.ui.controller.MediaCarouselController$listenForLockscreenSettingChanges$1$invokeSuspend$$inlined$map$1$2$1, reason: invalid class name */
                    public final class AnonymousClass1 extends ContinuationImpl {
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
                            return AnonymousClass2.this.emit(null, this);
                        }
                    }

                    public AnonymousClass2(FlowCollector flowCollector, MediaCarouselController mediaCarouselController) {
                        this.$this_unsafeFlow = flowCollector;
                        this.this$0 = mediaCarouselController;
                    }

                    @Override // kotlinx.coroutines.flow.FlowCollector
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                        To view partially-correct code enable 'Show inconsistent code' option in preferences
                    */
                    public final java.lang.Object emit(java.lang.Object r7, kotlin.coroutines.Continuation r8) {
                        /*
                            r6 = this;
                            boolean r0 = r8 instanceof com.android.systemui.media.controls.ui.controller.MediaCarouselController$listenForLockscreenSettingChanges$1$invokeSuspend$$inlined$map$1.AnonymousClass2.AnonymousClass1
                            if (r0 == 0) goto L13
                            r0 = r8
                            com.android.systemui.media.controls.ui.controller.MediaCarouselController$listenForLockscreenSettingChanges$1$invokeSuspend$$inlined$map$1$2$1 r0 = (com.android.systemui.media.controls.ui.controller.MediaCarouselController$listenForLockscreenSettingChanges$1$invokeSuspend$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                            int r1 = r0.label
                            r2 = -2147483648(0xffffffff80000000, float:-0.0)
                            r3 = r1 & r2
                            if (r3 == 0) goto L13
                            int r1 = r1 - r2
                            r0.label = r1
                            goto L18
                        L13:
                            com.android.systemui.media.controls.ui.controller.MediaCarouselController$listenForLockscreenSettingChanges$1$invokeSuspend$$inlined$map$1$2$1 r0 = new com.android.systemui.media.controls.ui.controller.MediaCarouselController$listenForLockscreenSettingChanges$1$invokeSuspend$$inlined$map$1$2$1
                            r0.<init>(r8)
                        L18:
                            java.lang.Object r8 = r0.result
                            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                            int r2 = r0.label
                            r3 = 0
                            r4 = 2
                            r5 = 1
                            if (r2 == 0) goto L3b
                            if (r2 == r5) goto L33
                            if (r2 != r4) goto L2b
                            kotlin.ResultKt.throwOnFailure(r8)
                            goto L67
                        L2b:
                            java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
                            java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
                            r6.<init>(r7)
                            throw r6
                        L33:
                            java.lang.Object r6 = r0.L$0
                            kotlinx.coroutines.flow.FlowCollector r6 = (kotlinx.coroutines.flow.FlowCollector) r6
                            kotlin.ResultKt.throwOnFailure(r8)
                            goto L5c
                        L3b:
                            kotlin.ResultKt.throwOnFailure(r8)
                            kotlin.Unit r7 = (kotlin.Unit) r7
                            kotlinx.coroutines.flow.FlowCollector r7 = r6.$this_unsafeFlow
                            r0.L$0 = r7
                            r0.label = r5
                            com.android.systemui.media.controls.ui.controller.MediaCarouselController$Companion r8 = com.android.systemui.media.controls.ui.controller.MediaCarouselController.Companion
                            com.android.systemui.media.controls.ui.controller.MediaCarouselController r6 = r6.this$0
                            r6.getClass()
                            com.android.systemui.media.controls.ui.controller.MediaCarouselController$getMediaLockScreenSetting$2 r8 = new com.android.systemui.media.controls.ui.controller.MediaCarouselController$getMediaLockScreenSetting$2
                            r8.<init>(r6, r3)
                            kotlinx.coroutines.CoroutineDispatcher r6 = r6.backgroundDispatcher
                            java.lang.Object r8 = kotlinx.coroutines.BuildersKt.withContext(r6, r8, r0)
                            if (r8 != r1) goto L5b
                            return r1
                        L5b:
                            r6 = r7
                        L5c:
                            r0.L$0 = r3
                            r0.label = r4
                            java.lang.Object r6 = r6.emit(r8, r0)
                            if (r6 != r1) goto L67
                            return r1
                        L67:
                            kotlin.Unit r6 = kotlin.Unit.INSTANCE
                            return r6
                        */
                        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.controls.ui.controller.MediaCarouselController$listenForLockscreenSettingChanges$1$invokeSuspend$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                    }
                }

                @Override // kotlinx.coroutines.flow.Flow
                public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                    Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, mediaCarouselController), continuation);
                    return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
                }
            });
            AnonymousClass3 anonymousClass3 = new AnonymousClass3(this.this$0, null);
            this.label = 1;
            if (FlowKt.collectLatest(distinctUntilChanged, anonymousClass3, this) == coroutineSingletons) {
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
