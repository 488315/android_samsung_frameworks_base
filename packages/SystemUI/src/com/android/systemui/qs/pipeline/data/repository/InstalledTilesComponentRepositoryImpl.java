package com.android.systemui.qs.pipeline.data.repository;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.UserHandle;
import com.android.systemui.common.data.repository.PackageChangeRepository;
import com.android.systemui.common.data.repository.PackageChangeRepositoryImpl;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Unit;
import kotlin.collections.EmptyList;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StateFlow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class InstalledTilesComponentRepositoryImpl implements InstalledTilesComponentRepository {
    public static final PackageManager.ResolveInfoFlags FLAGS;
    public static final Intent INTENT;
    public final CoroutineScope backgroundScope;
    public final Context context;
    public final PackageChangeRepository packageChangeRepository;
    public final Map userMap = new LinkedHashMap();

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
        INTENT = new Intent("android.service.quicksettings.action.QS_TILE");
        FLAGS = PackageManager.ResolveInfoFlags.of(786436L);
    }

    public InstalledTilesComponentRepositoryImpl(Context context, CoroutineScope coroutineScope, PackageChangeRepository packageChangeRepository) {
        this.context = context;
        this.backgroundScope = coroutineScope;
        this.packageChangeRepository = packageChangeRepository;
    }

    public final StateFlow getForUserLocked(final int i) {
        Map map = this.userMap;
        Integer valueOf = Integer.valueOf(i);
        LinkedHashMap linkedHashMap = (LinkedHashMap) map;
        Object obj = linkedHashMap.get(valueOf);
        if (obj == null) {
            final PackageManager packageManager = this.context.getUserId() == i ? this.context.getPackageManager() : this.context.createContextAsUser(UserHandle.of(i), 0).getPackageManager();
            final FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 = new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new InstalledTilesComponentRepositoryImpl$getForUserLocked$1$1(null), ((PackageChangeRepositoryImpl) this.packageChangeRepository).packageChanged(UserHandle.of(i)));
            obj = FlowKt.stateIn(FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.qs.pipeline.data.repository.InstalledTilesComponentRepositoryImpl$getForUserLocked$lambda$7$$inlined$map$1

                /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
                /* renamed from: com.android.systemui.qs.pipeline.data.repository.InstalledTilesComponentRepositoryImpl$getForUserLocked$lambda$7$$inlined$map$1$2, reason: invalid class name */
                public final class AnonymousClass2 implements FlowCollector {
                    public final /* synthetic */ PackageManager $packageManager$inlined;
                    public final /* synthetic */ FlowCollector $this_unsafeFlow;
                    public final /* synthetic */ int $userId$inlined;
                    public final /* synthetic */ InstalledTilesComponentRepositoryImpl this$0;

                    /* renamed from: com.android.systemui.qs.pipeline.data.repository.InstalledTilesComponentRepositoryImpl$getForUserLocked$lambda$7$$inlined$map$1$2$1, reason: invalid class name */
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

                    public AnonymousClass2(FlowCollector flowCollector, InstalledTilesComponentRepositoryImpl installedTilesComponentRepositoryImpl, int i, PackageManager packageManager) {
                        this.$this_unsafeFlow = flowCollector;
                        this.this$0 = installedTilesComponentRepositoryImpl;
                        this.$userId$inlined = i;
                        this.$packageManager$inlined = packageManager;
                    }

                    /* JADX WARN: Removed duplicated region for block: B:15:0x0030  */
                    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                        To view partially-correct code enable 'Show inconsistent code' option in preferences
                    */
                    public final java.lang.Object emit(java.lang.Object r11, kotlin.coroutines.Continuation r12) {
                        /*
                            r10 = this;
                            r0 = 1
                            boolean r1 = r12 instanceof com.android.systemui.qs.pipeline.data.repository.InstalledTilesComponentRepositoryImpl$getForUserLocked$lambda$7$$inlined$map$1.AnonymousClass2.AnonymousClass1
                            if (r1 == 0) goto L14
                            r1 = r12
                            com.android.systemui.qs.pipeline.data.repository.InstalledTilesComponentRepositoryImpl$getForUserLocked$lambda$7$$inlined$map$1$2$1 r1 = (com.android.systemui.qs.pipeline.data.repository.InstalledTilesComponentRepositoryImpl$getForUserLocked$lambda$7$$inlined$map$1.AnonymousClass2.AnonymousClass1) r1
                            int r2 = r1.label
                            r3 = -2147483648(0xffffffff80000000, float:-0.0)
                            r4 = r2 & r3
                            if (r4 == 0) goto L14
                            int r2 = r2 - r3
                            r1.label = r2
                            goto L19
                        L14:
                            com.android.systemui.qs.pipeline.data.repository.InstalledTilesComponentRepositoryImpl$getForUserLocked$lambda$7$$inlined$map$1$2$1 r1 = new com.android.systemui.qs.pipeline.data.repository.InstalledTilesComponentRepositoryImpl$getForUserLocked$lambda$7$$inlined$map$1$2$1
                            r1.<init>(r12)
                        L19:
                            java.lang.Object r12 = r1.result
                            kotlin.coroutines.intrinsics.CoroutineSingletons r2 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                            int r3 = r1.label
                            if (r3 == 0) goto L30
                            if (r3 != r0) goto L28
                            kotlin.ResultKt.throwOnFailure(r12)
                            goto Lba
                        L28:
                            java.lang.IllegalStateException r10 = new java.lang.IllegalStateException
                            java.lang.String r11 = "call to 'resume' before 'invoke' with coroutine"
                            r10.<init>(r11)
                            throw r10
                        L30:
                            kotlin.ResultKt.throwOnFailure(r12)
                            com.android.systemui.common.shared.model.PackageChangeModel r11 = (com.android.systemui.common.shared.model.PackageChangeModel) r11
                            android.content.pm.PackageManager r11 = r10.$packageManager$inlined
                            r11.getClass()
                            android.content.pm.PackageManager r11 = r10.$packageManager$inlined
                            android.content.Intent r12 = com.android.systemui.qs.pipeline.data.repository.InstalledTilesComponentRepositoryImpl.INTENT
                            com.android.systemui.qs.pipeline.data.repository.InstalledTilesComponentRepositoryImpl r12 = r10.this$0
                            r12.getClass()
                            android.content.Intent r12 = com.android.systemui.qs.pipeline.data.repository.InstalledTilesComponentRepositoryImpl.INTENT
                            android.content.pm.PackageManager$ResolveInfoFlags r3 = com.android.systemui.qs.pipeline.data.repository.InstalledTilesComponentRepositoryImpl.FLAGS
                            int r4 = r10.$userId$inlined
                            java.util.List r12 = r11.queryIntentServicesAsUser(r12, r3, r4)
                            java.lang.Iterable r12 = (java.lang.Iterable) r12
                            java.util.ArrayList r3 = new java.util.ArrayList
                            r3.<init>()
                            java.util.Iterator r12 = r12.iterator()
                        L58:
                            boolean r4 = r12.hasNext()
                            if (r4 == 0) goto L6c
                            java.lang.Object r4 = r12.next()
                            android.content.pm.ResolveInfo r4 = (android.content.pm.ResolveInfo) r4
                            android.content.pm.ServiceInfo r4 = r4.serviceInfo
                            if (r4 == 0) goto L58
                            r3.add(r4)
                            goto L58
                        L6c:
                            java.util.ArrayList r12 = new java.util.ArrayList
                            r12.<init>()
                            int r4 = r3.size()
                            r5 = 0
                            r6 = r5
                        L77:
                            if (r6 >= r4) goto L8f
                            java.lang.Object r7 = r3.get(r6)
                            int r6 = r6 + r0
                            r8 = r7
                            android.content.pm.ServiceInfo r8 = (android.content.pm.ServiceInfo) r8
                            java.lang.String r8 = r8.permission
                            java.lang.String r9 = "android.permission.BIND_QUICK_SETTINGS_TILE"
                            boolean r8 = kotlin.jvm.internal.Intrinsics.areEqual(r8, r9)
                            if (r8 == 0) goto L77
                            r12.add(r7)
                            goto L77
                        L8f:
                            java.util.ArrayList r3 = new java.util.ArrayList
                            r3.<init>()
                            int r4 = r12.size()
                            r6 = r5
                        L99:
                            if (r6 >= r4) goto Laf
                            java.lang.Object r7 = r12.get(r6)
                            int r6 = r6 + r0
                            r8 = r7
                            android.content.pm.ServiceInfo r8 = (android.content.pm.ServiceInfo) r8
                            boolean r8 = com.android.systemui.util.kotlin.PackageManagerExtKt.isComponentActuallyEnabled(r11, r8)     // Catch: java.lang.IllegalArgumentException -> La8
                            goto La9
                        La8:
                            r8 = r5
                        La9:
                            if (r8 == 0) goto L99
                            r3.add(r7)
                            goto L99
                        Laf:
                            r1.label = r0
                            kotlinx.coroutines.flow.FlowCollector r10 = r10.$this_unsafeFlow
                            java.lang.Object r10 = r10.emit(r3, r1)
                            if (r10 != r2) goto Lba
                            return r2
                        Lba:
                            kotlin.Unit r10 = kotlin.Unit.INSTANCE
                            return r10
                        */
                        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.pipeline.data.repository.InstalledTilesComponentRepositoryImpl$getForUserLocked$lambda$7$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                    }
                }

                @Override // kotlinx.coroutines.flow.Flow
                public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                    Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, this, i, packageManager), continuation);
                    return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
                }
            }), this.backgroundScope, SharingStarted.Companion.WhileSubscribed$default(SharingStarted.Companion, 3), EmptyList.INSTANCE);
            linkedHashMap.put(valueOf, obj);
        }
        return (StateFlow) obj;
    }
}
