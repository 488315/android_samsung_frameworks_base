package com.android.systemui.samsung.quicksetting.ui.panel;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelKt;
import com.android.settingslib.volume.domain.interactor.AudioVolumeInteractor;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class SecVolumeBarViewModel extends ViewModel {
    public final StateFlowImpl _volumeLevel;
    public final CoroutineContext coroutineContext;
    public final AudioVolumeInteractor volumeInteractor;
    public final ReadonlyStateFlow volumeLevel;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.samsung.quicksetting.ui.panel.SecVolumeBarViewModel$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        Object L$0;
        int label;

        public AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return SecVolumeBarViewModel.this.new AnonymousClass1(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Code restructure failed: missing block: B:13:0x0049, code lost:
        
            if (r1.$$delegate_0.collect(r3, r4) == r0) goto L15;
         */
        /* JADX WARN: Code restructure failed: missing block: B:14:0x004b, code lost:
        
            return r0;
         */
        /* JADX WARN: Code restructure failed: missing block: B:16:0x002f, code lost:
        
            if (r5 == r0) goto L15;
         */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final java.lang.Object invokeSuspend(java.lang.Object r5) {
            /*
                r4 = this;
                kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                int r1 = r4.label
                r2 = 2
                r3 = 1
                if (r1 == 0) goto L20
                if (r1 == r3) goto L18
                if (r1 == r2) goto L14
                java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                r4.<init>(r5)
                throw r4
            L14:
                kotlin.ResultKt.throwOnFailure(r5)
                goto L4c
            L18:
                java.lang.Object r1 = r4.L$0
                kotlinx.coroutines.flow.MutableStateFlow r1 = (kotlinx.coroutines.flow.MutableStateFlow) r1
                kotlin.ResultKt.throwOnFailure(r5)
                goto L32
            L20:
                kotlin.ResultKt.throwOnFailure(r5)
                com.android.systemui.samsung.quicksetting.ui.panel.SecVolumeBarViewModel r5 = com.android.systemui.samsung.quicksetting.ui.panel.SecVolumeBarViewModel.this
                kotlinx.coroutines.flow.StateFlowImpl r1 = r5._volumeLevel
                r4.L$0 = r1
                r4.label = r3
                java.lang.Object r5 = com.android.systemui.samsung.quicksetting.ui.panel.SecVolumeBarViewModel.access$getMusicStreamVolume(r5, r4)
                if (r5 != r0) goto L32
                goto L4b
            L32:
                r1.setValue(r5)
                com.android.systemui.samsung.quicksetting.ui.panel.SecVolumeBarViewModel r5 = com.android.systemui.samsung.quicksetting.ui.panel.SecVolumeBarViewModel.this
                kotlinx.coroutines.flow.ReadonlyStateFlow r1 = r5.volumeLevel
                com.android.systemui.samsung.quicksetting.ui.panel.SecVolumeBarViewModel$1$1 r3 = new com.android.systemui.samsung.quicksetting.ui.panel.SecVolumeBarViewModel$1$1
                r3.<init>()
                r5 = 0
                r4.L$0 = r5
                r4.label = r2
                kotlinx.coroutines.flow.StateFlow r5 = r1.$$delegate_0
                java.lang.Object r4 = r5.collect(r3, r4)
                if (r4 != r0) goto L4c
            L4b:
                return r0
            L4c:
                kotlin.KotlinNothingValueException r4 = new kotlin.KotlinNothingValueException
                r4.<init>()
                throw r4
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.samsung.quicksetting.ui.panel.SecVolumeBarViewModel.AnonymousClass1.invokeSuspend(java.lang.Object):java.lang.Object");
        }
    }

    public SecVolumeBarViewModel(AudioVolumeInteractor audioVolumeInteractor, CoroutineContext coroutineContext) {
        this.volumeInteractor = audioVolumeInteractor;
        this.coroutineContext = coroutineContext;
        StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(Float.valueOf(0.5f));
        this._volumeLevel = MutableStateFlow;
        this.volumeLevel = FlowKt.asStateFlow(MutableStateFlow);
        BuildersKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new AnonymousClass1(null), 3);
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0032  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0024  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final java.lang.Object access$getMusicStreamVolume(com.android.systemui.samsung.quicksetting.ui.panel.SecVolumeBarViewModel r4, kotlin.coroutines.jvm.internal.ContinuationImpl r5) {
        /*
            r4.getClass()
            boolean r0 = r5 instanceof com.android.systemui.samsung.quicksetting.ui.panel.SecVolumeBarViewModel$getMusicStreamVolume$1
            if (r0 == 0) goto L16
            r0 = r5
            com.android.systemui.samsung.quicksetting.ui.panel.SecVolumeBarViewModel$getMusicStreamVolume$1 r0 = (com.android.systemui.samsung.quicksetting.ui.panel.SecVolumeBarViewModel$getMusicStreamVolume$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L16
            int r1 = r1 - r2
            r0.label = r1
            goto L1b
        L16:
            com.android.systemui.samsung.quicksetting.ui.panel.SecVolumeBarViewModel$getMusicStreamVolume$1 r0 = new com.android.systemui.samsung.quicksetting.ui.panel.SecVolumeBarViewModel$getMusicStreamVolume$1
            r0.<init>(r4, r5)
        L1b:
            java.lang.Object r5 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L32
            if (r2 != r3) goto L2a
            kotlin.ResultKt.throwOnFailure(r5)
            goto L46
        L2a:
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            r4.<init>(r5)
            throw r4
        L32:
            kotlin.ResultKt.throwOnFailure(r5)
            com.android.systemui.samsung.quicksetting.ui.panel.SecVolumeBarViewModel$getMusicStreamVolume$volume$1 r5 = new com.android.systemui.samsung.quicksetting.ui.panel.SecVolumeBarViewModel$getMusicStreamVolume$volume$1
            r2 = 0
            r5.<init>(r4, r2)
            r0.label = r3
            kotlin.coroutines.CoroutineContext r4 = r4.coroutineContext
            java.lang.Object r5 = kotlinx.coroutines.BuildersKt.withContext(r4, r5, r0)
            if (r5 != r1) goto L46
            return r1
        L46:
            java.lang.Number r5 = (java.lang.Number) r5
            float r4 = r5.floatValue()
            java.lang.Float r5 = new java.lang.Float
            r5.<init>(r4)
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.samsung.quicksetting.ui.panel.SecVolumeBarViewModel.access$getMusicStreamVolume(com.android.systemui.samsung.quicksetting.ui.panel.SecVolumeBarViewModel, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }
}
