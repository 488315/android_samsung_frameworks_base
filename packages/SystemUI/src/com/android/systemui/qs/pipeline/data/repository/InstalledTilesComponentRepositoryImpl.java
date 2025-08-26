package com.android.systemui.qs.pipeline.data.repository;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.os.UserHandle;
import com.android.systemui.common.data.repository.PackageChangeRepository;
import com.android.systemui.common.data.repository.PackageChangeRepositoryImpl;
import com.android.systemui.util.kotlin.PackageManagerExtKt;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.EmptyList;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StateFlow;

/* loaded from: classes2.dex */
public final class InstalledTilesComponentRepositoryImpl implements InstalledTilesComponentRepository {
    public static final PackageManager.ResolveInfoFlags FLAGS;
    public static final Intent INTENT;
    public final CoroutineScope backgroundScope;
    public final Context context;
    public final PackageChangeRepository packageChangeRepository;
    public final Map userMap = new LinkedHashMap();

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
        Integer numValueOf = Integer.valueOf(i);
        LinkedHashMap linkedHashMap = (LinkedHashMap) map;
        Object objStateIn = linkedHashMap.get(numValueOf);
        if (objStateIn == null) {
            final PackageManager packageManager = this.context.getUserId() == i ? this.context.getPackageManager() : this.context.createContextAsUser(UserHandle.of(i), 0).getPackageManager();
            final FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 = new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new InstalledTilesComponentRepositoryImpl$getForUserLocked$1$1(null), ((PackageChangeRepositoryImpl) this.packageChangeRepository).packageChanged(UserHandle.of(i)));
            objStateIn = FlowKt.stateIn(FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.qs.pipeline.data.repository.InstalledTilesComponentRepositoryImpl$getForUserLocked$lambda$7$$inlined$map$1

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

                    /* JADX WARN: Removed duplicated region for block: B:7:0x0014  */
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                    */
                    public final Object emit(Object obj, Continuation continuation) {
                        AnonymousClass1 anonymousClass1;
                        boolean zIsComponentActuallyEnabled;
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
                            this.$packageManager$inlined.getClass();
                            PackageManager packageManager = this.$packageManager$inlined;
                            Intent intent = InstalledTilesComponentRepositoryImpl.INTENT;
                            this.this$0.getClass();
                            List listQueryIntentServicesAsUser = packageManager.queryIntentServicesAsUser(InstalledTilesComponentRepositoryImpl.INTENT, InstalledTilesComponentRepositoryImpl.FLAGS, this.$userId$inlined);
                            ArrayList arrayList = new ArrayList();
                            Iterator it = listQueryIntentServicesAsUser.iterator();
                            while (it.hasNext()) {
                                ServiceInfo serviceInfo = ((ResolveInfo) it.next()).serviceInfo;
                                if (serviceInfo != null) {
                                    arrayList.add(serviceInfo);
                                }
                            }
                            ArrayList arrayList2 = new ArrayList();
                            int size = arrayList.size();
                            int i3 = 0;
                            while (i3 < size) {
                                Object obj3 = arrayList.get(i3);
                                i3++;
                                if (Intrinsics.areEqual(((ServiceInfo) obj3).permission, "android.permission.BIND_QUICK_SETTINGS_TILE")) {
                                    arrayList2.add(obj3);
                                }
                            }
                            ArrayList arrayList3 = new ArrayList();
                            int size2 = arrayList2.size();
                            int i4 = 0;
                            while (i4 < size2) {
                                Object obj4 = arrayList2.get(i4);
                                i4++;
                                try {
                                    zIsComponentActuallyEnabled = PackageManagerExtKt.isComponentActuallyEnabled(packageManager, (ServiceInfo) obj4);
                                } catch (IllegalArgumentException unused) {
                                    zIsComponentActuallyEnabled = false;
                                }
                                if (zIsComponentActuallyEnabled) {
                                    arrayList3.add(obj4);
                                }
                            }
                            anonymousClass1.label = 1;
                            if (this.$this_unsafeFlow.emit(arrayList3, anonymousClass1) == coroutineSingletons) {
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
                    Object objCollect = flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1.collect(new AnonymousClass2(flowCollector, this, i, packageManager), continuation);
                    return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
                }
            }), this.backgroundScope, SharingStarted.Companion.WhileSubscribed$default(SharingStarted.Companion, 3), EmptyList.INSTANCE);
            linkedHashMap.put(numValueOf, objStateIn);
        }
        return (StateFlow) objStateIn;
    }
}
