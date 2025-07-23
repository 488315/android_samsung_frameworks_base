package com.android.systemui.communal.data.db;

import android.content.ComponentName;
import android.os.UserHandle;
import com.android.systemui.communal.shared.model.SpanValue;
import com.android.systemui.log.core.Logger;
import com.android.systemui.user.data.repository.UserRepositoryImpl;
import com.android.systemui.user.domain.interactor.UserLockedInteractor;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class DefaultWidgetPopulation$onCreate$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ DefaultWidgetPopulation this$0;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.communal.data.db.DefaultWidgetPopulation$onCreate$1$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        /* synthetic */ boolean Z$0;
        int label;

        public AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(continuation);
            anonymousClass1.Z$0 = ((Boolean) obj).booleanValue();
            return anonymousClass1;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            Boolean bool = (Boolean) obj;
            bool.booleanValue();
            return ((AnonymousClass1) create(bool, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            return Boolean.valueOf(this.Z$0);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DefaultWidgetPopulation$onCreate$1(DefaultWidgetPopulation defaultWidgetPopulation, Continuation continuation) {
        super(2, continuation);
        this.this$0 = defaultWidgetPopulation;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new DefaultWidgetPopulation$onCreate$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((DefaultWidgetPopulation$onCreate$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Integer allocateIdAndBindWidget;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            DefaultWidgetPopulation defaultWidgetPopulation = this.this$0;
            UserLockedInteractor userLockedInteractor = defaultWidgetPopulation.userLockedInteractor;
            Flow flowOn = FlowKt.flowOn(((UserRepositoryImpl) userLockedInteractor.userRepository).isUserUnlocked(defaultWidgetPopulation.userManager.getMainUser()), userLockedInteractor.backgroundDispatcher);
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(null);
            this.label = 1;
            if (FlowKt.first(flowOn, anonymousClass1, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        DefaultWidgetPopulation defaultWidgetPopulation2 = this.this$0;
        UserHandle mainUser = defaultWidgetPopulation2.userManager.getMainUser();
        if (mainUser != null) {
            int userSerialNumber = defaultWidgetPopulation2.userManager.getUserSerialNumber(mainUser.getIdentifier());
            String[] strArr = defaultWidgetPopulation2.defaultWidgets;
            int length = strArr.length;
            int i2 = 0;
            int i3 = 0;
            while (i2 < length) {
                String str = strArr[i2];
                int i4 = i3 + 1;
                ComponentName unflattenFromString = ComponentName.unflattenFromString(str);
                if (unflattenFromString != null && (allocateIdAndBindWidget = defaultWidgetPopulation2.communalWidgetHost.allocateIdAndBindWidget(unflattenFromString, mainUser)) != null) {
                    ((CommunalWidgetDao_Impl) ((CommunalWidgetDao) defaultWidgetPopulation2.communalWidgetDaoProvider.get())).addWidget(allocateIdAndBindWidget.intValue(), str, Integer.valueOf(i3), userSerialNumber, SpanValue.Fixed.m1075boximpl(3));
                }
                i2++;
                i3 = i4;
            }
            Logger.i$default(defaultWidgetPopulation2.logger, "Populated default widgets in the database.", null, 2, null);
        }
        return Unit.INSTANCE;
    }
}
