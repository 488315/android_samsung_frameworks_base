package com.android.systemui.samsung.quicksetting.ui.panel;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelKt;
import com.android.settingslib.volume.domain.interactor.AudioVolumeInteractor;
import kotlin.KotlinNothingValueException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.MutableStateFlow;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* loaded from: classes2.dex */
public final class SecVolumeBarViewModel extends ViewModel {
    public final StateFlowImpl _volumeLevel;
    public final CoroutineContext coroutineContext;
    public final AudioVolumeInteractor volumeInteractor;
    public final ReadonlyStateFlow volumeLevel;

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

        /* JADX WARN: Code restructure failed: missing block: B:14:0x0049, code lost:
        
            if (r1.$$delegate_0.collect(r3, r4) == r0) goto L15;
         */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final Object invokeSuspend(Object obj) throws Throwable {
            MutableStateFlow mutableStateFlow;
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                SecVolumeBarViewModel secVolumeBarViewModel = SecVolumeBarViewModel.this;
                mutableStateFlow = secVolumeBarViewModel._volumeLevel;
                this.L$0 = mutableStateFlow;
                this.label = 1;
                obj = SecVolumeBarViewModel.access$getMusicStreamVolume(secVolumeBarViewModel, this);
                if (obj != coroutineSingletons) {
                }
                return coroutineSingletons;
            }
            if (i != 1) {
                if (i != 2) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                throw new KotlinNothingValueException();
            }
            mutableStateFlow = (MutableStateFlow) this.L$0;
            ResultKt.throwOnFailure(obj);
            mutableStateFlow.setValue(obj);
            final SecVolumeBarViewModel secVolumeBarViewModel2 = SecVolumeBarViewModel.this;
            ReadonlyStateFlow readonlyStateFlow = secVolumeBarViewModel2.volumeLevel;
            FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.samsung.quicksetting.ui.panel.SecVolumeBarViewModel.1.1
                @Override // kotlinx.coroutines.flow.FlowCollector
                public final Object emit(Object obj2, Continuation continuation) throws Throwable {
                    float fFloatValue = ((Number) obj2).floatValue();
                    SecVolumeBarViewModel secVolumeBarViewModel3 = secVolumeBarViewModel2;
                    secVolumeBarViewModel3.getClass();
                    Object objWithContext = BuildersKt.withContext(secVolumeBarViewModel3.coroutineContext, new SecVolumeBarViewModel$setMusicStreamVolume$2(secVolumeBarViewModel3, fFloatValue, null), continuation);
                    CoroutineSingletons coroutineSingletons2 = CoroutineSingletons.COROUTINE_SUSPENDED;
                    if (objWithContext != coroutineSingletons2) {
                        objWithContext = Unit.INSTANCE;
                    }
                    return objWithContext == coroutineSingletons2 ? objWithContext : Unit.INSTANCE;
                }
            };
            this.L$0 = null;
            this.label = 2;
        }
    }

    public SecVolumeBarViewModel(AudioVolumeInteractor audioVolumeInteractor, CoroutineContext coroutineContext) {
        this.volumeInteractor = audioVolumeInteractor;
        this.coroutineContext = coroutineContext;
        StateFlowImpl stateFlowImplMutableStateFlow = StateFlowKt.MutableStateFlow(Float.valueOf(0.5f));
        this._volumeLevel = stateFlowImplMutableStateFlow;
        this.volumeLevel = FlowKt.asStateFlow(stateFlowImplMutableStateFlow);
        BuildersKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new AnonymousClass1(null), 3);
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0016  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Object access$getMusicStreamVolume(SecVolumeBarViewModel secVolumeBarViewModel, ContinuationImpl continuationImpl) throws Throwable {
        SecVolumeBarViewModel$getMusicStreamVolume$1 secVolumeBarViewModel$getMusicStreamVolume$1;
        secVolumeBarViewModel.getClass();
        if (continuationImpl instanceof SecVolumeBarViewModel$getMusicStreamVolume$1) {
            secVolumeBarViewModel$getMusicStreamVolume$1 = (SecVolumeBarViewModel$getMusicStreamVolume$1) continuationImpl;
            int i = secVolumeBarViewModel$getMusicStreamVolume$1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                secVolumeBarViewModel$getMusicStreamVolume$1.label = i - Integer.MIN_VALUE;
            } else {
                secVolumeBarViewModel$getMusicStreamVolume$1 = new SecVolumeBarViewModel$getMusicStreamVolume$1(secVolumeBarViewModel, continuationImpl);
            }
        }
        Object objWithContext = secVolumeBarViewModel$getMusicStreamVolume$1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = secVolumeBarViewModel$getMusicStreamVolume$1.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(objWithContext);
            SecVolumeBarViewModel$getMusicStreamVolume$volume$1 secVolumeBarViewModel$getMusicStreamVolume$volume$1 = new SecVolumeBarViewModel$getMusicStreamVolume$volume$1(secVolumeBarViewModel, null);
            secVolumeBarViewModel$getMusicStreamVolume$1.label = 1;
            objWithContext = BuildersKt.withContext(secVolumeBarViewModel.coroutineContext, secVolumeBarViewModel$getMusicStreamVolume$volume$1, secVolumeBarViewModel$getMusicStreamVolume$1);
            if (objWithContext == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(objWithContext);
        }
        return new Float(((Number) objWithContext).floatValue());
    }
}
