package com.android.systemui.volume.panel.component.mediaoutput.domain.interactor;

import android.content.Context;
import com.android.settingslib.bluetooth.LocalBluetoothManager;
import com.android.settingslib.media.InfoMediaManager;
import com.android.settingslib.media.LocalMediaManager;
import com.android.settingslib.volume.data.repository.LocalMediaRepositoryImpl;
import com.android.systemui.media.controls.util.LocalMediaManagerFactory;
import com.android.systemui.volume.panel.component.mediaoutput.data.repository.LocalMediaRepositoryFactory;
import com.android.systemui.volume.panel.component.mediaoutput.data.repository.LocalMediaRepositoryFactoryImpl;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.flow.FlowCollector;

final class MediaOutputInteractor$localMediaRepository$2 extends SuspendLambda implements Function3 {
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;
    final /* synthetic */ MediaOutputInteractor this$0;

    /* renamed from: com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaOutputInteractor$localMediaRepository$2$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ FlowCollector $$this$transformLatest;
        final /* synthetic */ String $it;
        private /* synthetic */ Object L$0;
        int label;
        final /* synthetic */ MediaOutputInteractor this$0;

        public AnonymousClass1(FlowCollector flowCollector, MediaOutputInteractor mediaOutputInteractor, String str, Continuation continuation) {
            super(2, continuation);
            this.$$this$transformLatest = flowCollector;
            this.this$0 = mediaOutputInteractor;
            this.$it = str;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$$this$transformLatest, this.this$0, this.$it, continuation);
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
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
                FlowCollector flowCollector = this.$$this$transformLatest;
                LocalMediaRepositoryFactory localMediaRepositoryFactory = this.this$0.localMediaRepositoryFactory;
                String str = this.$it;
                LocalMediaRepositoryFactoryImpl localMediaRepositoryFactoryImpl = (LocalMediaRepositoryFactoryImpl) localMediaRepositoryFactory;
                LocalMediaManagerFactory localMediaManagerFactory = localMediaRepositoryFactoryImpl.localMediaManagerFactory;
                Context context = localMediaManagerFactory.context;
                LocalBluetoothManager localBluetoothManager = localMediaManagerFactory.localBluetoothManager;
                LocalMediaRepositoryImpl localMediaRepositoryImpl = new LocalMediaRepositoryImpl(localMediaRepositoryFactoryImpl.eventsReceiver, new LocalMediaManager(localMediaManagerFactory.context, localBluetoothManager, InfoMediaManager.createInstance(context, str, null, localBluetoothManager, null), str), coroutineScope);
                this.label = 1;
                if (flowCollector.emit(localMediaRepositoryImpl, this) == coroutineSingletons) {
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

    public MediaOutputInteractor$localMediaRepository$2(MediaOutputInteractor mediaOutputInteractor, Continuation continuation) {
        super(3, continuation);
        this.this$0 = mediaOutputInteractor;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        MediaOutputInteractor$localMediaRepository$2 mediaOutputInteractor$localMediaRepository$2 = new MediaOutputInteractor$localMediaRepository$2(this.this$0, (Continuation) obj3);
        mediaOutputInteractor$localMediaRepository$2.L$0 = (FlowCollector) obj;
        mediaOutputInteractor$localMediaRepository$2.L$1 = (String) obj2;
        return mediaOutputInteractor$localMediaRepository$2.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            AnonymousClass1 anonymousClass1 = new AnonymousClass1((FlowCollector) this.L$0, this.this$0, (String) this.L$1, null);
            this.L$0 = null;
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
        return Unit.INSTANCE;
    }
}
