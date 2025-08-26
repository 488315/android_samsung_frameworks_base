package com.android.systemui.util.settings.repository;

import com.android.systemui.user.data.repository.UserRepository;
import com.android.systemui.user.data.repository.UserRepositoryImpl;
import com.android.systemui.util.settings.SettingsProxyExt;
import com.android.systemui.util.settings.UserSettingsProxy;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;

/* loaded from: classes3.dex */
public abstract class UserAwareSettingsRepository {
    public static final int $stable = 8;
    private final CoroutineDispatcher backgroundDispatcher;
    private final CoroutineContext bgContext;
    private final UserRepository userRepository;
    private final UserSettingsProxy userSettings;

    /* renamed from: com.android.systemui.util.settings.repository.UserAwareSettingsRepository$getInt$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        final /* synthetic */ int $defaultValue;
        final /* synthetic */ String $name;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(String str, int i, Continuation continuation) {
            super(2, continuation);
            this.$name = str;
            this.$defaultValue = i;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return UserAwareSettingsRepository.this.new AnonymousClass2(this.$name, this.$defaultValue, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            return new Integer(UserAwareSettingsRepository.this.userSettings.getIntForUser(this.$name, this.$defaultValue, ((UserRepositoryImpl) UserAwareSettingsRepository.this.userRepository).getSelectedUserInfo().id));
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((AnonymousClass2) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }
    }

    /* renamed from: com.android.systemui.util.settings.repository.UserAwareSettingsRepository$getString$2, reason: invalid class name and case insensitive filesystem */
    final class C11782 extends SuspendLambda implements Function2 {
        final /* synthetic */ String $name;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C11782(String str, Continuation continuation) {
            super(2, continuation);
            this.$name = str;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return UserAwareSettingsRepository.this.new C11782(this.$name, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            return UserAwareSettingsRepository.this.userSettings.getStringForUser(this.$name, ((UserRepositoryImpl) UserAwareSettingsRepository.this.userRepository).getSelectedUserInfo().id);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((C11782) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }
    }

    /* renamed from: com.android.systemui.util.settings.repository.UserAwareSettingsRepository$setBoolean$2, reason: invalid class name and case insensitive filesystem */
    final class C11792 extends SuspendLambda implements Function2 {
        final /* synthetic */ String $name;
        final /* synthetic */ boolean $value;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C11792(String str, boolean z, Continuation continuation) {
            super(2, continuation);
            this.$name = str;
            this.$value = z;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return UserAwareSettingsRepository.this.new C11792(this.$name, this.$value, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            return Boolean.valueOf(UserAwareSettingsRepository.this.userSettings.putBoolForUser(this.$name, this.$value, ((UserRepositoryImpl) UserAwareSettingsRepository.this.userRepository).getSelectedUserInfo().id));
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((C11792) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }
    }

    /* renamed from: com.android.systemui.util.settings.repository.UserAwareSettingsRepository$setInt$2, reason: invalid class name and case insensitive filesystem */
    final class C11802 extends SuspendLambda implements Function2 {
        final /* synthetic */ String $name;
        final /* synthetic */ int $value;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C11802(String str, int i, Continuation continuation) {
            super(2, continuation);
            this.$name = str;
            this.$value = i;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return UserAwareSettingsRepository.this.new C11802(this.$name, this.$value, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            return Boolean.valueOf(UserAwareSettingsRepository.this.userSettings.putIntForUser(this.$name, this.$value, ((UserRepositoryImpl) UserAwareSettingsRepository.this.userRepository).getSelectedUserInfo().id));
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((C11802) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }
    }

    /* renamed from: com.android.systemui.util.settings.repository.UserAwareSettingsRepository$settingObserver$1, reason: invalid class name */
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

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(FlowCollector flowCollector, Continuation continuation) {
            return ((AnonymousClass1) create(flowCollector, continuation)).invokeSuspend(Unit.INSTANCE);
        }
    }

    public UserAwareSettingsRepository(UserSettingsProxy userSettingsProxy, UserRepository userRepository, CoroutineDispatcher coroutineDispatcher, CoroutineContext coroutineContext) {
        this.userSettings = userSettingsProxy;
        this.userRepository = userRepository;
        this.backgroundDispatcher = coroutineDispatcher;
        this.bgContext = coroutineContext;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final <T> Flow settingObserver(String str, int i, final Function0 function0) {
        final FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 = new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new AnonymousClass1(null), SettingsProxyExt.INSTANCE.observerFlow(this.userSettings, i, str));
        return new Flow() { // from class: com.android.systemui.util.settings.repository.UserAwareSettingsRepository$settingObserver$$inlined$map$1

            /* renamed from: com.android.systemui.util.settings.repository.UserAwareSettingsRepository$settingObserver$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2<T> implements FlowCollector {
                final /* synthetic */ Function0 $settingsReader$inlined;
                final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.util.settings.repository.UserAwareSettingsRepository$settingObserver$$inlined$map$1$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, Function0 function0) {
                    this.$this_unsafeFlow = flowCollector;
                    this.$settingsReader$inlined = function0;
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
                        FlowCollector flowCollector = this.$this_unsafeFlow;
                        Object objInvoke = this.$settingsReader$inlined.invoke();
                        anonymousClass1.label = 1;
                        if (flowCollector.emit(objInvoke, anonymousClass1) == coroutineSingletons) {
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
            public Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object objCollect = flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1.collect(new AnonymousClass2(flowCollector, function0), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        };
    }

    public final Flow boolSetting(String str, boolean z) {
        return FlowKt.flowOn(FlowKt.distinctUntilChanged(FlowKt.transformLatest(((UserRepositoryImpl) this.userRepository).selectedUserInfo, new UserAwareSettingsRepository$boolSetting$$inlined$flatMapLatest$1(null, this, str, z))), this.backgroundDispatcher);
    }

    public final Object getInt(String str, int i, Continuation continuation) {
        return BuildersKt.withContext(this.bgContext, new AnonymousClass2(str, i, null), continuation);
    }

    public final Object getString(String str, Continuation continuation) {
        return BuildersKt.withContext(this.bgContext, new C11782(str, null), continuation);
    }

    public final Flow intSetting(String str, int i) {
        return FlowKt.flowOn(FlowKt.distinctUntilChanged(FlowKt.transformLatest(((UserRepositoryImpl) this.userRepository).selectedUserInfo, new UserAwareSettingsRepository$intSetting$$inlined$flatMapLatest$1(null, this, str, i))), this.backgroundDispatcher);
    }

    public final Object setBoolean(String str, boolean z, Continuation continuation) throws Throwable {
        Object objWithContext = BuildersKt.withContext(this.bgContext, new C11792(str, z, null), continuation);
        return objWithContext == CoroutineSingletons.COROUTINE_SUSPENDED ? objWithContext : Unit.INSTANCE;
    }

    public final Object setInt(String str, int i, Continuation continuation) throws Throwable {
        Object objWithContext = BuildersKt.withContext(this.bgContext, new C11802(str, i, null), continuation);
        return objWithContext == CoroutineSingletons.COROUTINE_SUSPENDED ? objWithContext : Unit.INSTANCE;
    }
}
