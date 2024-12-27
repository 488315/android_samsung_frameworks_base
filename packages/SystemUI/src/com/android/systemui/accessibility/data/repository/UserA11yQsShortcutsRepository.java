package com.android.systemui.accessibility.data.repository;

import com.android.systemui.util.settings.SecureSettings;
import com.android.systemui.util.settings.SettingsProxyExt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlySharedFlow;
import kotlinx.coroutines.flow.SharingStarted;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final class UserA11yQsShortcutsRepository {
    public final SecureSettings secureSettings;
    public final ReadonlySharedFlow targets;
    public final int userId;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public interface Factory {
    }

    static {
        new Companion(null);
    }

    public UserA11yQsShortcutsRepository(int i, SecureSettings secureSettings, CoroutineScope coroutineScope, CoroutineDispatcher coroutineDispatcher) {
        this.userId = i;
        this.secureSettings = secureSettings;
        final FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 = new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new UserA11yQsShortcutsRepository$targets$1(null), SettingsProxyExt.INSTANCE.observerFlow(secureSettings, i, "accessibility_qs_targets"));
        FlowKt.shareIn(FlowKt.flowOn(new Flow() { // from class: com.android.systemui.accessibility.data.repository.UserA11yQsShortcutsRepository$special$$inlined$map$1

            /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
            /* renamed from: com.android.systemui.accessibility.data.repository.UserA11yQsShortcutsRepository$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ UserA11yQsShortcutsRepository this$0;

                /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
                /* renamed from: com.android.systemui.accessibility.data.repository.UserA11yQsShortcutsRepository$special$$inlined$map$1$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, UserA11yQsShortcutsRepository userA11yQsShortcutsRepository) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = userA11yQsShortcutsRepository;
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
                        boolean r0 = r7 instanceof com.android.systemui.accessibility.data.repository.UserA11yQsShortcutsRepository$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r7
                        com.android.systemui.accessibility.data.repository.UserA11yQsShortcutsRepository$special$$inlined$map$1$2$1 r0 = (com.android.systemui.accessibility.data.repository.UserA11yQsShortcutsRepository$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.accessibility.data.repository.UserA11yQsShortcutsRepository$special$$inlined$map$1$2$1 r0 = new com.android.systemui.accessibility.data.repository.UserA11yQsShortcutsRepository$special$$inlined$map$1$2$1
                        r0.<init>(r7)
                    L18:
                        java.lang.Object r7 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r7)
                        goto L82
                    L27:
                        java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
                        java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
                        r5.<init>(r6)
                        throw r5
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r7)
                        kotlin.Unit r6 = (kotlin.Unit) r6
                        com.android.systemui.accessibility.data.repository.UserA11yQsShortcutsRepository r6 = r5.this$0
                        int r7 = r6.userId
                        com.android.systemui.util.settings.SecureSettings r6 = r6.secureSettings
                        java.lang.String r2 = "accessibility_qs_targets"
                        java.lang.String r6 = r6.getStringForUser(r2, r7)
                        if (r6 != 0) goto L44
                        java.lang.String r6 = ""
                    L44:
                        java.lang.String r7 = ":"
                        java.lang.String[] r7 = new java.lang.String[]{r7}
                        r2 = 6
                        r4 = 0
                        java.util.List r6 = kotlin.text.StringsKt__StringsKt.split$default(r6, r7, r4, r2)
                        java.lang.Iterable r6 = (java.lang.Iterable) r6
                        java.util.ArrayList r7 = new java.util.ArrayList
                        r7.<init>()
                        java.util.Iterator r6 = r6.iterator()
                    L5b:
                        boolean r2 = r6.hasNext()
                        if (r2 == 0) goto L73
                        java.lang.Object r2 = r6.next()
                        r4 = r2
                        java.lang.String r4 = (java.lang.String) r4
                        int r4 = r4.length()
                        if (r4 != 0) goto L6f
                        goto L5b
                    L6f:
                        r7.add(r2)
                        goto L5b
                    L73:
                        java.util.Set r6 = kotlin.collections.CollectionsKt___CollectionsKt.toSet(r7)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r5 = r5.$this_unsafeFlow
                        java.lang.Object r5 = r5.emit(r6, r0)
                        if (r5 != r1) goto L82
                        return r1
                    L82:
                        kotlin.Unit r5 = kotlin.Unit.INSTANCE
                        return r5
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.accessibility.data.repository.UserA11yQsShortcutsRepository$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, this), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, coroutineDispatcher), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(SharingStarted.Companion, 3), 1);
    }
}
