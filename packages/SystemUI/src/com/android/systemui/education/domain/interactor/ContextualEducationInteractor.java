package com.android.systemui.education.domain.interactor;

import android.content.Context;
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler;
import androidx.datastore.preferences.PreferenceDataStoreFile;
import androidx.datastore.preferences.core.PreferenceDataStoreFactory;
import androidx.datastore.preferences.core.Preferences;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.CoreStartable;
import com.android.systemui.contextualeducation.GestureType;
import com.android.systemui.education.data.model.GestureEduModel;
import com.android.systemui.education.data.repository.ContextualEducationRepository;
import com.android.systemui.education.data.repository.UserContextualEducationRepository;
import com.android.systemui.education.data.repository.UserContextualEducationRepository$$ExternalSyntheticLambda0;
import com.android.systemui.education.data.repository.UserContextualEducationRepository$readEduDeviceConnectionTime$$inlined$map$1;
import com.android.systemui.user.domain.interactor.SelectedUserInteractor;
import java.time.Clock;
import java.util.Arrays;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.StringCompanionObject;
import kotlin.reflect.KProperty;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;

/* loaded from: classes2.dex */
public final class ContextualEducationInteractor implements CoreStartable {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final CoroutineDispatcher backgroundDispatcher;
    public final CoroutineScope backgroundScope;
    public final Clock clock;
    public final Flow eduDeviceConnectionTimeFlow;
    public final Flow keyboardShortcutTriggered;
    public final ContextualEducationRepository repository;
    public final SelectedUserInteractor selectedUserInteractor;
    public final Flow backGestureModelFlow = readEduModelsOnSignalCountChanged(GestureType.BACK);
    public final Flow homeGestureModelFlow = readEduModelsOnSignalCountChanged(GestureType.HOME);
    public final Flow overviewGestureModelFlow = readEduModelsOnSignalCountChanged(GestureType.OVERVIEW);
    public final Flow allAppsGestureModelFlow = readEduModelsOnSignalCountChanged(GestureType.ALL_APPS);

    /* renamed from: com.android.systemui.education.domain.interactor.ContextualEducationInteractor$start$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        int label;

        /* renamed from: com.android.systemui.education.domain.interactor.ContextualEducationInteractor$start$1$1, reason: invalid class name and collision with other inner class name */
        final class C01981 extends SuspendLambda implements Function2 {
            /* synthetic */ int I$0;
            int label;
            final /* synthetic */ ContextualEducationInteractor this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C01981(ContextualEducationInteractor contextualEducationInteractor, Continuation continuation) {
                super(2, continuation);
                this.this$0 = contextualEducationInteractor;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                C01981 c01981 = new C01981(this.this$0, continuation);
                c01981.I$0 = ((Number) obj).intValue();
                return c01981;
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((C01981) create(Integer.valueOf(((Number) obj).intValue()), (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                final int i = this.I$0;
                final UserContextualEducationRepository userContextualEducationRepository = (UserContextualEducationRepository) this.this$0.repository;
                CoroutineScope coroutineScope = userContextualEducationRepository.dataStoreScope;
                if (coroutineScope != null) {
                    CoroutineScopeKt.cancel(coroutineScope, null);
                }
                CoroutineScope coroutineScope2 = (CoroutineScope) userContextualEducationRepository.dataStoreScopeProvider.get();
                KProperty kProperty = UserContextualEducationRepository.$$delegatedProperties[0];
                userContextualEducationRepository.userId$delegate.value = Integer.valueOf(i);
                PreferenceDataStoreFactory preferenceDataStoreFactory = PreferenceDataStoreFactory.INSTANCE;
                ReplaceFileCorruptionHandler replaceFileCorruptionHandler = new ReplaceFileCorruptionHandler(new UserContextualEducationRepository$$ExternalSyntheticLambda0());
                coroutineScope2.getClass();
                userContextualEducationRepository.datastore.updateState(null, PreferenceDataStoreFactory.create$default(preferenceDataStoreFactory, replaceFileCorruptionHandler, coroutineScope2, new Function0() { // from class: com.android.systemui.education.data.repository.UserContextualEducationRepository$$ExternalSyntheticLambda1
                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        Context context = userContextualEducationRepository.applicationContext;
                        int i2 = StringCompanionObject.$r8$clinit;
                        return PreferenceDataStoreFile.preferencesDataStoreFile(context, String.format("education/USER%s_ContextualEducation", Arrays.copyOf(new Object[]{Integer.valueOf(i)}, 1)));
                    }
                }));
                userContextualEducationRepository.dataStoreScope = coroutineScope2;
                return Unit.INSTANCE;
            }
        }

        public AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return ContextualEducationInteractor.this.new AnonymousClass1(continuation);
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
                ContextualEducationInteractor contextualEducationInteractor = ContextualEducationInteractor.this;
                Flow flow = contextualEducationInteractor.selectedUserInteractor.selectedUser;
                C01981 c01981 = new C01981(contextualEducationInteractor, null);
                this.label = 1;
                if (FlowKt.collectLatest(flow, c01981, this) == coroutineSingletons) {
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

    public ContextualEducationInteractor(CoroutineScope coroutineScope, CoroutineDispatcher coroutineDispatcher, Clock clock, SelectedUserInteractor selectedUserInteractor, ContextualEducationRepository contextualEducationRepository) {
        this.backgroundScope = coroutineScope;
        this.backgroundDispatcher = coroutineDispatcher;
        this.clock = clock;
        this.selectedUserInteractor = selectedUserInteractor;
        this.repository = contextualEducationRepository;
        UserContextualEducationRepository userContextualEducationRepository = (UserContextualEducationRepository) contextualEducationRepository;
        this.eduDeviceConnectionTimeFlow = FlowKt.distinctUntilChanged(new UserContextualEducationRepository$readEduDeviceConnectionTime$$inlined$map$1(userContextualEducationRepository.prefData, userContextualEducationRepository));
        this.keyboardShortcutTriggered = userContextualEducationRepository.keyboardShortcutTriggered;
    }

    public final Flow readEduModelsOnSignalCountChanged(final GestureType gestureType) {
        final UserContextualEducationRepository userContextualEducationRepository = (UserContextualEducationRepository) this.repository;
        final ChannelFlowTransformLatest channelFlowTransformLatest = userContextualEducationRepository.prefData;
        return FlowKt.flowOn(FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.education.data.repository.UserContextualEducationRepository$readGestureEduModelFlow$$inlined$map$1

            /* renamed from: com.android.systemui.education.data.repository.UserContextualEducationRepository$readGestureEduModelFlow$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ GestureType $gestureType$inlined;
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ UserContextualEducationRepository this$0;

                /* renamed from: com.android.systemui.education.data.repository.UserContextualEducationRepository$readGestureEduModelFlow$$inlined$map$1$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, UserContextualEducationRepository userContextualEducationRepository, GestureType gestureType) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = userContextualEducationRepository;
                    this.$gestureType$inlined = gestureType;
                }

                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
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
                    Object obj2 = anonymousClass1.result;
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i2 = anonymousClass1.label;
                    if (i2 == 0) {
                        ResultKt.throwOnFailure(obj2);
                        GestureEduModel gestureEduModelAccess$getGestureEduModel = UserContextualEducationRepository.access$getGestureEduModel(this.this$0, this.$gestureType$inlined, (Preferences) obj);
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(gestureEduModelAccess$getGestureEduModel, anonymousClass1) == coroutineSingletons) {
                            return coroutineSingletons;
                        }
                    } else {
                        if (i2 != 1) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        ResultKt.throwOnFailure(obj2);
                    }
                    return Unit.INSTANCE;
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object objCollect = channelFlowTransformLatest.collect(new AnonymousClass2(flowCollector, userContextualEducationRepository, gestureType), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        }, new ContextualEducationInteractor$$ExternalSyntheticLambda0()), this.backgroundDispatcher);
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        CoroutineTracingKt.launchTraced$default(this.backgroundScope, null, null, new AnonymousClass1(null), 7);
    }
}
