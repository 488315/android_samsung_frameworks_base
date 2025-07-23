package com.android.systemui.util.settings.repository;

import com.android.systemui.util.settings.SettingsProxyExt;
import com.android.systemui.util.settings.UserSettingsProxy;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function0;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public abstract class SettingsForUserRepository {
    public static final int $stable = 8;
    private final CoroutineContext backgroundContext;
    private final CoroutineDispatcher backgroundDispatcher;
    private final UserSettingsProxy userSettings;

    public SettingsForUserRepository(UserSettingsProxy userSettingsProxy, CoroutineDispatcher coroutineDispatcher, CoroutineContext coroutineContext) {
        this.userSettings = userSettingsProxy;
        this.backgroundDispatcher = coroutineDispatcher;
        this.backgroundContext = coroutineContext;
    }

    public static /* synthetic */ Flow boolSettingForUser$default(SettingsForUserRepository settingsForUserRepository, int i, String str, boolean z, int i2, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: boolSettingForUser");
        }
        if ((i2 & 4) != 0) {
            z = false;
        }
        return settingsForUserRepository.boolSettingForUser(i, str, z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean boolSettingForUser$lambda$0(SettingsForUserRepository settingsForUserRepository, String str, boolean z, int i) {
        return settingsForUserRepository.userSettings.getBoolForUser(str, z, i);
    }

    public static /* synthetic */ Object getBoolForUser$default(SettingsForUserRepository settingsForUserRepository, int i, String str, boolean z, Continuation continuation, int i2, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: getBoolForUser");
        }
        if ((i2 & 4) != 0) {
            z = false;
        }
        return settingsForUserRepository.getBoolForUser(i, str, z, continuation);
    }

    public static /* synthetic */ Object getIntForUser$default(SettingsForUserRepository settingsForUserRepository, int i, String str, int i2, Continuation continuation, int i3, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: getIntForUser");
        }
        if ((i3 & 4) != 0) {
            i2 = 0;
        }
        return settingsForUserRepository.getIntForUser(i, str, i2, continuation);
    }

    public static /* synthetic */ Flow intSettingForUser$default(SettingsForUserRepository settingsForUserRepository, int i, String str, int i2, int i3, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: intSettingForUser");
        }
        if ((i3 & 4) != 0) {
            i2 = 0;
        }
        return settingsForUserRepository.intSettingForUser(i, str, i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final int intSettingForUser$lambda$1(SettingsForUserRepository settingsForUserRepository, String str, int i, int i2) {
        return settingsForUserRepository.userSettings.getIntForUser(str, i, i2);
    }

    public final Flow boolSettingForUser(final int i, final String str, final boolean z) {
        return FlowKt.flowOn(FlowKt.distinctUntilChanged(settingObserver(str, i, new Function0() { // from class: com.android.systemui.util.settings.repository.SettingsForUserRepository$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                boolean boolSettingForUser$lambda$0;
                boolSettingForUser$lambda$0 = SettingsForUserRepository.boolSettingForUser$lambda$0(SettingsForUserRepository.this, str, z, i);
                return Boolean.valueOf(boolSettingForUser$lambda$0);
            }
        })), this.backgroundDispatcher);
    }

    public final Object getBoolForUser(int i, String str, boolean z, Continuation continuation) {
        return BuildersKt.withContext(this.backgroundContext, new SettingsForUserRepository$getBoolForUser$2(this, str, z, i, null), continuation);
    }

    public final Object getIntForUser(int i, String str, int i2, Continuation continuation) {
        return BuildersKt.withContext(this.backgroundContext, new SettingsForUserRepository$getIntForUser$2(this, str, i2, i, null), continuation);
    }

    public final Flow intSettingForUser(final int i, final String str, final int i2) {
        return FlowKt.flowOn(FlowKt.distinctUntilChanged(settingObserver(str, i, new Function0() { // from class: com.android.systemui.util.settings.repository.SettingsForUserRepository$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                int intSettingForUser$lambda$1;
                intSettingForUser$lambda$1 = SettingsForUserRepository.intSettingForUser$lambda$1(SettingsForUserRepository.this, str, i2, i);
                return Integer.valueOf(intSettingForUser$lambda$1);
            }
        })), this.backgroundDispatcher);
    }

    public final Object setBoolForUser(int i, String str, boolean z, Continuation continuation) {
        Object withContext = BuildersKt.withContext(this.backgroundContext, new SettingsForUserRepository$setBoolForUser$2(this, str, z, i, null), continuation);
        return withContext == CoroutineSingletons.COROUTINE_SUSPENDED ? withContext : Unit.INSTANCE;
    }

    public final Object setIntForUser(int i, String str, int i2, Continuation continuation) {
        Object withContext = BuildersKt.withContext(this.backgroundContext, new SettingsForUserRepository$setIntForUser$2(this, str, i2, i, null), continuation);
        return withContext == CoroutineSingletons.COROUTINE_SUSPENDED ? withContext : Unit.INSTANCE;
    }

    public final <T> Flow settingObserver(String str, int i, final Function0 function0) {
        final FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 = new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new SettingsForUserRepository$settingObserver$1(null), SettingsProxyExt.INSTANCE.observerFlow(this.userSettings, i, str));
        return new Flow() { // from class: com.android.systemui.util.settings.repository.SettingsForUserRepository$settingObserver$$inlined$map$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.util.settings.repository.SettingsForUserRepository$settingObserver$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2<T> implements FlowCollector {
                final /* synthetic */ Function0 $settingsReader$inlined;
                final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.util.settings.repository.SettingsForUserRepository$settingObserver$$inlined$map$1$2$1, reason: invalid class name */
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

                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                    /*
                        r4 = this;
                        boolean r0 = r6 instanceof com.android.systemui.util.settings.repository.SettingsForUserRepository$settingObserver$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.util.settings.repository.SettingsForUserRepository$settingObserver$$inlined$map$1$2$1 r0 = (com.android.systemui.util.settings.repository.SettingsForUserRepository$settingObserver$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.util.settings.repository.SettingsForUserRepository$settingObserver$$inlined$map$1$2$1 r0 = new com.android.systemui.util.settings.repository.SettingsForUserRepository$settingObserver$$inlined$map$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L45
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        kotlinx.coroutines.flow.FlowCollector r6 = r4.$this_unsafeFlow
                        kotlin.Unit r5 = (kotlin.Unit) r5
                        kotlin.jvm.functions.Function0 r4 = r4.$settingsReader$inlined
                        java.lang.Object r4 = r4.invoke()
                        r0.label = r3
                        java.lang.Object r4 = r6.emit(r4, r0)
                        if (r4 != r1) goto L45
                        return r1
                    L45:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.util.settings.repository.SettingsForUserRepository$settingObserver$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, function0), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
    }
}
