package com.android.systemui.statusbar.pipeline.shared.domain.interactor;

import android.content.res.Resources;
import com.android.systemui.R;
import com.android.systemui.shared.settings.data.repository.SecureSettingsRepository;
import java.util.LinkedHashSet;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.MapsKt__MapsJVMKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;

/* loaded from: classes3.dex */
public final class HomeStatusBarIconBlockListInteractor {
    public final String[] defaultBlockedIcons;
    public final HomeStatusBarIconBlockListInteractor$special$$inlined$map$1 iconBlockList;
    public final String vibrateIconSlot;

    /* JADX WARN: Type inference failed for: r3v1, types: [com.android.systemui.statusbar.pipeline.shared.domain.interactor.HomeStatusBarIconBlockListInteractor$special$$inlined$map$1] */
    public HomeStatusBarIconBlockListInteractor(Resources resources, SecureSettingsRepository secureSettingsRepository) {
        this.defaultBlockedIcons = resources.getStringArray(R.array.config_collapsed_statusbar_icon_blocklist);
        this.vibrateIconSlot = resources.getString(17043307);
        final Flow flowBoolSetting = secureSettingsRepository.boolSetting("status_bar_show_vibrate_icon", false);
        this.iconBlockList = new Flow() { // from class: com.android.systemui.statusbar.pipeline.shared.domain.interactor.HomeStatusBarIconBlockListInteractor$special$$inlined$map$1

            /* renamed from: com.android.systemui.statusbar.pipeline.shared.domain.interactor.HomeStatusBarIconBlockListInteractor$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ HomeStatusBarIconBlockListInteractor this$0;

                /* renamed from: com.android.systemui.statusbar.pipeline.shared.domain.interactor.HomeStatusBarIconBlockListInteractor$special$$inlined$map$1$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, HomeStatusBarIconBlockListInteractor homeStatusBarIconBlockListInteractor) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = homeStatusBarIconBlockListInteractor;
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
                        boolean zBooleanValue = ((Boolean) obj).booleanValue();
                        HomeStatusBarIconBlockListInteractor homeStatusBarIconBlockListInteractor = this.this$0;
                        String[] strArr = homeStatusBarIconBlockListInteractor.defaultBlockedIcons;
                        LinkedHashSet linkedHashSet = new LinkedHashSet(MapsKt__MapsJVMKt.mapCapacity(strArr.length));
                        ArraysKt___ArraysKt.toCollection(linkedHashSet, strArr);
                        String str = homeStatusBarIconBlockListInteractor.vibrateIconSlot;
                        if (zBooleanValue) {
                            linkedHashSet.remove(str);
                        } else {
                            linkedHashSet.add(str);
                        }
                        List list = CollectionsKt___CollectionsKt.toList(linkedHashSet);
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(list, anonymousClass1) == coroutineSingletons) {
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
                Object objCollect = flowBoolSetting.collect(new AnonymousClass2(flowCollector, this), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        };
    }
}
