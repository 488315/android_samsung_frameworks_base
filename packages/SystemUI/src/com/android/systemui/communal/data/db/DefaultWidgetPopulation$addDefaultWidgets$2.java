package com.android.systemui.communal.data.db;

import android.content.ComponentName;
import androidx.room.util.DBUtil;
import com.android.systemui.communal.widgets.CommunalWidgetHost;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
final class DefaultWidgetPopulation$addDefaultWidgets$2 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ DefaultWidgetPopulation this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DefaultWidgetPopulation$addDefaultWidgets$2(DefaultWidgetPopulation defaultWidgetPopulation, Continuation continuation) {
        super(2, continuation);
        this.this$0 = defaultWidgetPopulation;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new DefaultWidgetPopulation$addDefaultWidgets$2(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((DefaultWidgetPopulation$addDefaultWidgets$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        DefaultWidgetPopulation defaultWidgetPopulation = this.this$0;
        String[] strArr = defaultWidgetPopulation.defaultWidgets;
        int length = strArr.length;
        int i = 0;
        int i2 = 0;
        while (i < length) {
            int i3 = i2 + 1;
            ComponentName unflattenFromString = ComponentName.unflattenFromString(strArr[i]);
            if (unflattenFromString != null) {
                CommunalWidgetHost.Companion companion = CommunalWidgetHost.Companion;
                Integer allocateIdAndBindWidget = defaultWidgetPopulation.communalWidgetHost.allocateIdAndBindWidget(unflattenFromString, null);
                if (allocateIdAndBindWidget != null) {
                    CommunalWidgetDao communalWidgetDao = (CommunalWidgetDao) defaultWidgetPopulation.communalWidgetDaoProvider.get();
                    int intValue = allocateIdAndBindWidget.intValue();
                    int length2 = defaultWidgetPopulation.defaultWidgets.length - i2;
                    CommunalWidgetDao_Impl communalWidgetDao_Impl = (CommunalWidgetDao_Impl) communalWidgetDao;
                    communalWidgetDao_Impl.getClass();
                    ((Long) DBUtil.performBlocking(communalWidgetDao_Impl.__db, false, true, new CommunalWidgetDao_Impl$$ExternalSyntheticLambda0(communalWidgetDao_Impl, intValue, unflattenFromString, length2))).longValue();
                }
            }
            i++;
            i2 = i3;
        }
        return Unit.INSTANCE;
    }
}
