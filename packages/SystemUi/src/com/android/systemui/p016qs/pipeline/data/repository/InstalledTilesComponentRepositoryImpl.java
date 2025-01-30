package com.android.systemui.p016qs.pipeline.data.repository;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import com.android.systemui.common.coroutine.ConflatedCallbackFlow;
import com.android.systemui.util.Assert;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import com.samsung.android.nexus.video.VideoPlayer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class InstalledTilesComponentRepositoryImpl implements InstalledTilesComponentRepository {
    public static final PackageManager.ResolveInfoFlags FLAGS;
    public static final Intent INTENT;
    public static final IntentFilter INTENT_FILTER;
    public final Context applicationContext;
    public final CoroutineDispatcher backgroundDispatcher;
    public final PackageManager packageManager;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.PACKAGE_ADDED");
        intentFilter.addAction("android.intent.action.PACKAGE_CHANGED");
        intentFilter.addAction("android.intent.action.PACKAGE_REMOVED");
        intentFilter.addAction("android.intent.action.PACKAGE_REPLACED");
        intentFilter.addDataScheme("package");
        INTENT_FILTER = intentFilter;
        INTENT = new Intent("android.service.quicksettings.action.QS_TILE");
        FLAGS = PackageManager.ResolveInfoFlags.of(786436L);
    }

    public InstalledTilesComponentRepositoryImpl(Context context, PackageManager packageManager, CoroutineDispatcher coroutineDispatcher) {
        this.applicationContext = context;
        this.packageManager = packageManager;
        this.backgroundDispatcher = coroutineDispatcher;
    }

    public final Flow getInstalledTilesComponents(final int i) {
        ConflatedCallbackFlow conflatedCallbackFlow = ConflatedCallbackFlow.INSTANCE;
        C2187x143d695c c2187x143d695c = new C2187x143d695c(this, i, null);
        conflatedCallbackFlow.getClass();
        final FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 = new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new C2189x143d695d(null), ConflatedCallbackFlow.conflatedCallbackFlow(c2187x143d695c));
        return FlowKt.flowOn(FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.qs.pipeline.data.repository.InstalledTilesComponentRepositoryImpl$getInstalledTilesComponents$$inlined$map$1

            /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
            /* renamed from: com.android.systemui.qs.pipeline.data.repository.InstalledTilesComponentRepositoryImpl$getInstalledTilesComponents$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ int $userId$inlined;
                public final /* synthetic */ InstalledTilesComponentRepositoryImpl this$0;

                /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
                @DebugMetadata(m276c = "com.android.systemui.qs.pipeline.data.repository.InstalledTilesComponentRepositoryImpl$getInstalledTilesComponents$$inlined$map$1$2", m277f = "InstalledTilesComponentRepository.kt", m278l = {IKnoxCustomManager.Stub.TRANSACTION_getLockScreenShortcut}, m279m = "emit")
                /* renamed from: com.android.systemui.qs.pipeline.data.repository.InstalledTilesComponentRepositoryImpl$getInstalledTilesComponents$$inlined$map$1$2$1, reason: invalid class name */
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
                        this.label |= VideoPlayer.MEDIA_ERROR_SYSTEM;
                        return AnonymousClass2.this.emit(null, this);
                    }
                }

                public AnonymousClass2(FlowCollector flowCollector, InstalledTilesComponentRepositoryImpl installedTilesComponentRepositoryImpl, int i) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = installedTilesComponentRepositoryImpl;
                    this.$userId$inlined = i;
                }

                /* JADX WARN: Removed duplicated region for block: B:15:0x0030  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    int i;
                    if (continuation instanceof AnonymousClass1) {
                        anonymousClass1 = (AnonymousClass1) continuation;
                        int i2 = anonymousClass1.label;
                        if ((i2 & VideoPlayer.MEDIA_ERROR_SYSTEM) != 0) {
                            anonymousClass1.label = i2 - VideoPlayer.MEDIA_ERROR_SYSTEM;
                            Object obj2 = anonymousClass1.result;
                            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                            i = anonymousClass1.label;
                            if (i != 0) {
                                ResultKt.throwOnFailure(obj2);
                                Intent intent = InstalledTilesComponentRepositoryImpl.INTENT;
                                PackageManager packageManager = this.this$0.packageManager;
                                List queryIntentServicesAsUser = packageManager.queryIntentServicesAsUser(intent, InstalledTilesComponentRepositoryImpl.FLAGS, this.$userId$inlined);
                                ArrayList arrayList = new ArrayList();
                                Iterator it = queryIntentServicesAsUser.iterator();
                                while (it.hasNext()) {
                                    ServiceInfo serviceInfo = ((ResolveInfo) it.next()).serviceInfo;
                                    if (serviceInfo != null) {
                                        arrayList.add(serviceInfo);
                                    }
                                }
                                ArrayList arrayList2 = new ArrayList();
                                Iterator it2 = arrayList.iterator();
                                while (it2.hasNext()) {
                                    Object next = it2.next();
                                    if (Intrinsics.areEqual(((ServiceInfo) next).permission, "android.permission.BIND_QUICK_SETTINGS_TILE")) {
                                        arrayList2.add(next);
                                    }
                                }
                                ArrayList arrayList3 = new ArrayList();
                                Iterator it3 = arrayList2.iterator();
                                while (it3.hasNext()) {
                                    Object next2 = it3.next();
                                    ServiceInfo serviceInfo2 = (ServiceInfo) next2;
                                    Assert.isNotMainThread();
                                    int componentEnabledSetting = packageManager.getComponentEnabledSetting(serviceInfo2.getComponentName());
                                    if (componentEnabledSetting != 0 ? componentEnabledSetting == 1 : serviceInfo2.isEnabled()) {
                                        arrayList3.add(next2);
                                    }
                                }
                                LinkedHashSet linkedHashSet = new LinkedHashSet();
                                Iterator it4 = arrayList3.iterator();
                                while (it4.hasNext()) {
                                    linkedHashSet.add(((ServiceInfo) it4.next()).getComponentName());
                                }
                                anonymousClass1.label = 1;
                                if (this.$this_unsafeFlow.emit(linkedHashSet, anonymousClass1) == coroutineSingletons) {
                                    return coroutineSingletons;
                                }
                            } else {
                                if (i != 1) {
                                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                                }
                                ResultKt.throwOnFailure(obj2);
                            }
                            return Unit.INSTANCE;
                        }
                    }
                    anonymousClass1 = new AnonymousClass1(continuation);
                    Object obj22 = anonymousClass1.result;
                    CoroutineSingletons coroutineSingletons2 = CoroutineSingletons.COROUTINE_SUSPENDED;
                    i = anonymousClass1.label;
                    if (i != 0) {
                    }
                    return Unit.INSTANCE;
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, this, i), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }), this.backgroundDispatcher);
    }
}
