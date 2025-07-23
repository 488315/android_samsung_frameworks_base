package com.android.systemui.media.mediaoutput.controller.media;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class SessionController$special$$inlined$map$1 implements Flow {
    public final /* synthetic */ Flow $this_unsafeTransform$inlined;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.media.mediaoutput.controller.media.SessionController$special$$inlined$map$1$2, reason: invalid class name */
    public final class AnonymousClass2 implements FlowCollector {
        public final /* synthetic */ FlowCollector $this_unsafeFlow;

        /* renamed from: com.android.systemui.media.mediaoutput.controller.media.SessionController$special$$inlined$map$1$2$1, reason: invalid class name */
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

        public AnonymousClass2(FlowCollector flowCollector) {
            this.$this_unsafeFlow = flowCollector;
        }

        /* JADX WARN: Removed duplicated region for block: B:15:0x0030  */
        /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
        @Override // kotlinx.coroutines.flow.FlowCollector
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final java.lang.Object emit(java.lang.Object r9, kotlin.coroutines.Continuation r10) {
            /*
                r8 = this;
                boolean r0 = r10 instanceof com.android.systemui.media.mediaoutput.controller.media.SessionController$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                if (r0 == 0) goto L13
                r0 = r10
                com.android.systemui.media.mediaoutput.controller.media.SessionController$special$$inlined$map$1$2$1 r0 = (com.android.systemui.media.mediaoutput.controller.media.SessionController$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                int r1 = r0.label
                r2 = -2147483648(0xffffffff80000000, float:-0.0)
                r3 = r1 & r2
                if (r3 == 0) goto L13
                int r1 = r1 - r2
                r0.label = r1
                goto L18
            L13:
                com.android.systemui.media.mediaoutput.controller.media.SessionController$special$$inlined$map$1$2$1 r0 = new com.android.systemui.media.mediaoutput.controller.media.SessionController$special$$inlined$map$1$2$1
                r0.<init>(r10)
            L18:
                java.lang.Object r10 = r0.result
                kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                int r2 = r0.label
                r3 = 1
                if (r2 == 0) goto L30
                if (r2 != r3) goto L28
                kotlin.ResultKt.throwOnFailure(r10)
                goto Lb4
            L28:
                java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
                java.lang.String r9 = "call to 'resume' before 'invoke' with coroutine"
                r8.<init>(r9)
                throw r8
            L30:
                kotlin.ResultKt.throwOnFailure(r10)
                com.android.systemui.monet.ColorScheme r9 = (com.android.systemui.monet.ColorScheme) r9
                if (r9 != 0) goto L39
                r9 = 0
                goto La9
            L39:
                com.android.systemui.media.mediaoutput.entity.MediaInfoExt r10 = com.android.systemui.media.mediaoutput.entity.MediaInfoExt.INSTANCE
                com.android.systemui.monet.TonalPalette r2 = r9.mAccent2
                int r2 = r2.getS700()
                r10.getClass()
                r10 = 3
                float[] r4 = new float[r10]
                android.graphics.Color.colorToHSV(r2, r4)
                r5 = 2
                r6 = r4[r5]
                r7 = 1045220557(0x3e4ccccd, float:0.2)
                int r6 = (r6 > r7 ? 1 : (r6 == r7 ? 0 : -1))
                if (r6 > 0) goto L55
                goto L5b
            L55:
                r4[r5] = r7
                int r2 = android.graphics.Color.HSVToColor(r4)
            L5b:
                com.android.systemui.monet.TonalPalette r9 = r9.mAccent1
                int r9 = r9.getS700()
                float[] r10 = new float[r10]
                android.graphics.Color.colorToHSV(r9, r10)
                r4 = r10[r5]
                int r4 = (r4 > r7 ? 1 : (r4 == r7 ? 0 : -1))
                if (r4 > 0) goto L6d
                goto L73
            L6d:
                r10[r5] = r7
                int r9 = android.graphics.Color.HSVToColor(r10)
            L73:
                int r10 = android.graphics.Color.red(r2)
                int r4 = android.graphics.Color.green(r2)
                int r2 = android.graphics.Color.blue(r2)
                r5 = 114(0x72, float:1.6E-43)
                int r10 = android.graphics.Color.argb(r5, r10, r4, r2)
                java.lang.Integer r2 = new java.lang.Integer
                r2.<init>(r10)
                int r10 = android.graphics.Color.red(r9)
                int r4 = android.graphics.Color.green(r9)
                int r9 = android.graphics.Color.blue(r9)
                r5 = 255(0xff, float:3.57E-43)
                int r9 = android.graphics.Color.argb(r5, r10, r4, r9)
                java.lang.Integer r10 = new java.lang.Integer
                r10.<init>(r9)
                java.lang.Integer[] r9 = new java.lang.Integer[]{r2, r10}
                java.util.List r9 = java.util.Arrays.asList(r9)
            La9:
                r0.label = r3
                kotlinx.coroutines.flow.FlowCollector r8 = r8.$this_unsafeFlow
                java.lang.Object r8 = r8.emit(r9, r0)
                if (r8 != r1) goto Lb4
                return r1
            Lb4:
                kotlin.Unit r8 = kotlin.Unit.INSTANCE
                return r8
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.controller.media.SessionController$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
        }
    }

    public SessionController$special$$inlined$map$1(Flow flow) {
        this.$this_unsafeTransform$inlined = flow;
    }

    @Override // kotlinx.coroutines.flow.Flow
    public final Object collect(FlowCollector flowCollector, Continuation continuation) {
        Object collect = this.$this_unsafeTransform$inlined.collect(new AnonymousClass2(flowCollector), continuation);
        return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
    }
}
