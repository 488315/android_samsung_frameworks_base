package com.android.systemui.util.settings.repository;

import com.android.systemui.user.data.repository.UserRepository;
import com.android.systemui.user.data.repository.UserRepositoryImpl;
import com.android.systemui.util.settings.SecureSettings;
import com.android.systemui.util.settings.SettingsProxyExt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class UserAwareSecureSettingsRepositoryImpl implements UserAwareSecureSettingsRepository {
    public static final int $stable = 8;
    private final CoroutineDispatcher backgroundDispatcher;
    private final SecureSettings secureSettings;
    private final UserRepository userRepository;

    public UserAwareSecureSettingsRepositoryImpl(SecureSettings secureSettings, UserRepository userRepository, CoroutineDispatcher coroutineDispatcher) {
        this.secureSettings = secureSettings;
        this.userRepository = userRepository;
        this.backgroundDispatcher = coroutineDispatcher;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Flow settingObserver(final String str, final boolean z, final int i) {
        final FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 = new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new UserAwareSecureSettingsRepositoryImpl$settingObserver$1(null), SettingsProxyExt.INSTANCE.observerFlow(this.secureSettings, i, str));
        return new Flow() { // from class: com.android.systemui.util.settings.repository.UserAwareSecureSettingsRepositoryImpl$settingObserver$$inlined$map$1

            /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
            /* renamed from: com.android.systemui.util.settings.repository.UserAwareSecureSettingsRepositoryImpl$settingObserver$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2<T> implements FlowCollector {
                final /* synthetic */ boolean $defaultValue$inlined;
                final /* synthetic */ String $name$inlined;
                final /* synthetic */ FlowCollector $this_unsafeFlow;
                final /* synthetic */ int $userId$inlined;
                final /* synthetic */ UserAwareSecureSettingsRepositoryImpl this$0;

                /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
                /* renamed from: com.android.systemui.util.settings.repository.UserAwareSecureSettingsRepositoryImpl$settingObserver$$inlined$map$1$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, UserAwareSecureSettingsRepositoryImpl userAwareSecureSettingsRepositoryImpl, String str, boolean z, int i) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = userAwareSecureSettingsRepositoryImpl;
                    this.$name$inlined = str;
                    this.$defaultValue$inlined = z;
                    this.$userId$inlined = i;
                }

                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r6, kotlin.coroutines.Continuation r7) {
                    /*
                        r5 = this;
                        boolean r0 = r7 instanceof com.android.systemui.util.settings.repository.UserAwareSecureSettingsRepositoryImpl$settingObserver$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r7
                        com.android.systemui.util.settings.repository.UserAwareSecureSettingsRepositoryImpl$settingObserver$$inlined$map$1$2$1 r0 = (com.android.systemui.util.settings.repository.UserAwareSecureSettingsRepositoryImpl$settingObserver$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.util.settings.repository.UserAwareSecureSettingsRepositoryImpl$settingObserver$$inlined$map$1$2$1 r0 = new com.android.systemui.util.settings.repository.UserAwareSecureSettingsRepositoryImpl$settingObserver$$inlined$map$1$2$1
                        r0.<init>(r7)
                    L18:
                        java.lang.Object r7 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r7)
                        goto L53
                    L27:
                        java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
                        java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
                        r5.<init>(r6)
                        throw r5
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r7)
                        kotlinx.coroutines.flow.FlowCollector r7 = r5.$this_unsafeFlow
                        kotlin.Unit r6 = (kotlin.Unit) r6
                        com.android.systemui.util.settings.repository.UserAwareSecureSettingsRepositoryImpl r6 = r5.this$0
                        com.android.systemui.util.settings.SecureSettings r6 = com.android.systemui.util.settings.repository.UserAwareSecureSettingsRepositoryImpl.access$getSecureSettings$p(r6)
                        java.lang.String r2 = r5.$name$inlined
                        boolean r4 = r5.$defaultValue$inlined
                        int r5 = r5.$userId$inlined
                        boolean r5 = r6.getBoolForUser(r2, r4, r5)
                        java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)
                        r0.label = r3
                        java.lang.Object r5 = r7.emit(r5, r0)
                        if (r5 != r1) goto L53
                        return r1
                    L53:
                        kotlin.Unit r5 = kotlin.Unit.INSTANCE
                        return r5
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.util.settings.repository.UserAwareSecureSettingsRepositoryImpl$settingObserver$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, this, str, z, i), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
    }

    @Override // com.android.systemui.util.settings.repository.UserAwareSecureSettingsRepository
    public Flow boolSettingForActiveUser(String str, boolean z) {
        return FlowKt.flowOn(FlowKt.distinctUntilChanged(FlowKt.transformLatest(((UserRepositoryImpl) this.userRepository).selectedUserInfo, new UserAwareSecureSettingsRepositoryImpl$boolSettingForActiveUser$$inlined$flatMapLatest$1(null, this, str, z))), this.backgroundDispatcher);
    }
}
