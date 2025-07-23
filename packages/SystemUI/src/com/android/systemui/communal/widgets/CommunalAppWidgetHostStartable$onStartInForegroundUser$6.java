package com.android.systemui.communal.widgets;

import android.content.pm.UserInfo;
import android.os.UserHandle;
import com.android.systemui.communal.shared.model.CommunalWidgetContentModel;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.util.kotlin.WithPrev;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class CommunalAppWidgetHostStartable$onStartInForegroundUser$6 extends SuspendLambda implements Function2 {
    /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ CommunalAppWidgetHostStartable this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CommunalAppWidgetHostStartable$onStartInForegroundUser$6(CommunalAppWidgetHostStartable communalAppWidgetHostStartable, Continuation continuation) {
        super(2, continuation);
        this.this$0 = communalAppWidgetHostStartable;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        CommunalAppWidgetHostStartable$onStartInForegroundUser$6 communalAppWidgetHostStartable$onStartInForegroundUser$6 = new CommunalAppWidgetHostStartable$onStartInForegroundUser$6(this.this$0, continuation);
        communalAppWidgetHostStartable$onStartInForegroundUser$6.L$0 = obj;
        return communalAppWidgetHostStartable$onStartInForegroundUser$6;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((CommunalAppWidgetHostStartable$onStartInForegroundUser$6) create((Pair) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Integer valueOf;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        Pair pair = (Pair) this.L$0;
        WithPrev withPrev = (WithPrev) pair.component1();
        List list = (List) pair.component2();
        if (((Boolean) withPrev.component2()).booleanValue()) {
            CommunalAppWidgetHostStartable communalAppWidgetHostStartable = this.this$0;
            int i = CommunalAppWidgetHostStartable.$r8$clinit;
            communalAppWidgetHostStartable.getClass();
            List list2 = list;
            ArrayList arrayList = new ArrayList();
            for (Object obj2 : list2) {
                CommunalWidgetContentModel communalWidgetContentModel = (CommunalWidgetContentModel) obj2;
                if ((communalWidgetContentModel instanceof CommunalWidgetContentModel.Available) && (((CommunalWidgetContentModel.Available) communalWidgetContentModel).providerInfo.widgetCategory & 8) != 0) {
                    arrayList.add(obj2);
                }
            }
            int size = arrayList.size();
            int i2 = 0;
            int i3 = 0;
            while (i3 < size) {
                Object obj3 = arrayList.get(i3);
                i3++;
                communalAppWidgetHostStartable.getCommunalInteractor().widgetRepository.deleteWidget(((CommunalWidgetContentModel) obj3).getAppWidgetId());
            }
            CommunalAppWidgetHostStartable communalAppWidgetHostStartable2 = this.this$0;
            List userProfiles = ((UserTrackerImpl) ((UserTracker) communalAppWidgetHostStartable2.userTracker$delegate.getValue())).getUserProfiles();
            ArrayList arrayList2 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(userProfiles, 10));
            Iterator it = userProfiles.iterator();
            while (it.hasNext()) {
                arrayList2.add(Integer.valueOf(((UserInfo) it.next()).id));
            }
            Set set = CollectionsKt___CollectionsKt.toSet(arrayList2);
            ArrayList arrayList3 = new ArrayList();
            for (Object obj4 : list2) {
                CommunalWidgetContentModel communalWidgetContentModel2 = (CommunalWidgetContentModel) obj4;
                if (communalWidgetContentModel2 instanceof CommunalWidgetContentModel.Available) {
                    UserHandle profile = ((CommunalWidgetContentModel.Available) communalWidgetContentModel2).providerInfo.getProfile();
                    valueOf = profile != null ? Integer.valueOf(profile.getIdentifier()) : null;
                } else {
                    if (!(communalWidgetContentModel2 instanceof CommunalWidgetContentModel.Pending)) {
                        throw new NoWhenBranchMatchedException();
                    }
                    valueOf = Integer.valueOf(((CommunalWidgetContentModel.Pending) communalWidgetContentModel2).user.getIdentifier());
                }
                if (!CollectionsKt___CollectionsKt.contains(set, valueOf)) {
                    arrayList3.add(obj4);
                }
            }
            int size2 = arrayList3.size();
            while (i2 < size2) {
                Object obj5 = arrayList3.get(i2);
                i2++;
                communalAppWidgetHostStartable2.getCommunalInteractor().widgetRepository.deleteWidget(((CommunalWidgetContentModel) obj5).getAppWidgetId());
            }
        }
        return Unit.INSTANCE;
    }
}
