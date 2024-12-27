package com.android.systemui.keyguard.data.repository;

import android.content.Context;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.util.settings.SecureSettings;
import com.android.systemui.util.settings.SettingsProxyExt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class KeyguardSmartspaceRepositoryImpl implements KeyguardSmartspaceRepository {
    public final StateFlowImpl _bcSmartspaceVisibility;
    public final ReadonlyStateFlow bcSmartspaceVisibility = FlowKt.asStateFlow(StateFlowKt.MutableStateFlow(8));
    public final ReadonlyStateFlow isWeatherEnabled;
    public final SecureSettings secureSettings;
    public final UserTracker userTracker;

    public KeyguardSmartspaceRepositoryImpl(Context context, SecureSettings secureSettings, UserTracker userTracker, CoroutineScope coroutineScope) {
        this.secureSettings = secureSettings;
        this.userTracker = userTracker;
        final FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 = new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new KeyguardSmartspaceRepositoryImpl$isWeatherEnabled$1(null), SettingsProxyExt.INSTANCE.observerFlow(secureSettings, ((UserTrackerImpl) userTracker).getUserId(), "lockscreen_weather_enabled"));
        this.isWeatherEnabled = FlowKt.stateIn(new Flow() { // from class: com.android.systemui.keyguard.data.repository.KeyguardSmartspaceRepositoryImpl$special$$inlined$map$1

            /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
            /* renamed from: com.android.systemui.keyguard.data.repository.KeyguardSmartspaceRepositoryImpl$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ KeyguardSmartspaceRepositoryImpl this$0;

                /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
                /* renamed from: com.android.systemui.keyguard.data.repository.KeyguardSmartspaceRepositoryImpl$special$$inlined$map$1$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, KeyguardSmartspaceRepositoryImpl keyguardSmartspaceRepositoryImpl) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = keyguardSmartspaceRepositoryImpl;
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
                        boolean r0 = r6 instanceof com.android.systemui.keyguard.data.repository.KeyguardSmartspaceRepositoryImpl$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.keyguard.data.repository.KeyguardSmartspaceRepositoryImpl$special$$inlined$map$1$2$1 r0 = (com.android.systemui.keyguard.data.repository.KeyguardSmartspaceRepositoryImpl$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.keyguard.data.repository.KeyguardSmartspaceRepositoryImpl$special$$inlined$map$1$2$1 r0 = new com.android.systemui.keyguard.data.repository.KeyguardSmartspaceRepositoryImpl$special$$inlined$map$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L5a
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        kotlin.Unit r5 = (kotlin.Unit) r5
                        com.android.systemui.keyguard.data.repository.KeyguardSmartspaceRepositoryImpl r5 = r4.this$0
                        com.android.systemui.settings.UserTracker r6 = r5.userTracker
                        com.android.systemui.settings.UserTrackerImpl r6 = (com.android.systemui.settings.UserTrackerImpl) r6
                        int r6 = r6.getUserId()
                        com.android.systemui.util.settings.SecureSettings r5 = r5.secureSettings
                        java.lang.String r2 = "lockscreen_weather_enabled"
                        int r5 = r5.getIntForUser(r2, r3, r6)
                        if (r5 != r3) goto L4a
                        r5 = r3
                        goto L4b
                    L4a:
                        r5 = 0
                    L4b:
                        java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L5a
                        return r1
                    L5a:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.data.repository.KeyguardSmartspaceRepositoryImpl$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, this), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, coroutineScope, SharingStarted.Companion.WhileSubscribed$default(SharingStarted.Companion, 3), Boolean.valueOf(secureSettings.getIntForUser("lockscreen_weather_enabled", 1, ((UserTrackerImpl) userTracker).getUserId()) == 1));
    }
}
