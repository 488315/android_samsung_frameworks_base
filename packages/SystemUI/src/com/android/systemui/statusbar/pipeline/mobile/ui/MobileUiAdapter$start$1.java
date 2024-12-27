package com.android.systemui.statusbar.pipeline.mobile.ui;

import android.util.Log;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import com.android.systemui.BasicRune;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.statusbar.phone.StatusBarIconHolder;
import com.android.systemui.statusbar.phone.ui.StatusBarIconControllerImpl;
import com.android.systemui.statusbar.phone.ui.StatusBarIconList;
import java.util.Collections;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

final class MobileUiAdapter$start$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ MobileUiAdapter this$0;

    /* renamed from: com.android.systemui.statusbar.pipeline.mobile.ui.MobileUiAdapter$start$1$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        /* synthetic */ Object L$0;
        int label;
        final /* synthetic */ MobileUiAdapter this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(MobileUiAdapter mobileUiAdapter, Continuation continuation) {
            super(2, continuation);
            this.this$0 = mobileUiAdapter;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.this$0, continuation);
            anonymousClass1.L$0 = obj;
            return anonymousClass1;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((List) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            List<Integer> list = (List) this.L$0;
            MobileViewLogger mobileViewLogger = this.this$0.logger;
            mobileViewLogger.getClass();
            LogLevel logLevel = LogLevel.INFO;
            MobileViewLogger$logUiAdapterSubIdsSentToIconController$2 mobileViewLogger$logUiAdapterSubIdsSentToIconController$2 = new Function1() { // from class: com.android.systemui.statusbar.pipeline.mobile.ui.MobileViewLogger$logUiAdapterSubIdsSentToIconController$2
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj2) {
                    return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("Sub IDs in MobileUiAdapter being sent to icon controller: ", ((LogMessage) obj2).getStr1());
                }
            };
            LogBuffer logBuffer = mobileViewLogger.buffer;
            LogMessage obtain = logBuffer.obtain("MobileViewLogger", logLevel, mobileViewLogger$logUiAdapterSubIdsSentToIconController$2, null);
            ((LogMessageImpl) obtain).str1 = list.toString();
            logBuffer.commit(obtain);
            MobileUiAdapter mobileUiAdapter = this.this$0;
            mobileUiAdapter.lastValue = list;
            StatusBarIconControllerImpl statusBarIconControllerImpl = (StatusBarIconControllerImpl) mobileUiAdapter.iconController;
            String string = statusBarIconControllerImpl.mContext.getString(17043145);
            StatusBarIconList statusBarIconList = statusBarIconControllerImpl.mStatusBarIconList;
            StatusBarIconList.Slot slot = statusBarIconList.getSlot(string);
            statusBarIconControllerImpl.removeUnusedIconsInSlot(string, list);
            String string2 = statusBarIconControllerImpl.mContext.getString(17043146);
            StatusBarIconList.Slot slot2 = statusBarIconList.getSlot(string2);
            if (BasicRune.STATUS_NETWORK_MULTI_SIM) {
                statusBarIconControllerImpl.removeUnusedIconsInSlot(string2, list);
            }
            Collections.reverse(list);
            for (Integer num : list) {
                if (BasicRune.STATUS_NETWORK_MULTI_SIM) {
                    int simOrderByIds = statusBarIconControllerImpl.mSubscriptionsOrder.getSimOrderByIds(num.intValue(), list);
                    if (num.intValue() == Integer.MAX_VALUE) {
                        simOrderByIds = 0;
                    }
                    Log.d("StatusBarIconController", "setNewMobileIconSubIds - subId: " + num + ", mobileslotId: " + simOrderByIds);
                    if ((simOrderByIds == 0 ? slot.getHolderForTag(num.intValue()) : slot2.getHolderForTag(num.intValue())) == null) {
                        Log.d("StatusBarIconController", "add NewMobileIconSubIds - subId: " + num + ", mobileslotId: " + simOrderByIds);
                        int intValue = num.intValue();
                        StatusBarIconHolder.Companion.getClass();
                        StatusBarIconHolder statusBarIconHolder = new StatusBarIconHolder(null);
                        statusBarIconHolder.type = 3;
                        statusBarIconHolder.tag = intValue;
                        statusBarIconControllerImpl.setIcon(simOrderByIds == 0 ? string : string2, statusBarIconHolder);
                    }
                } else if (slot.getHolderForTag(num.intValue()) == null) {
                    int intValue2 = num.intValue();
                    StatusBarIconHolder.Companion.getClass();
                    StatusBarIconHolder statusBarIconHolder2 = new StatusBarIconHolder(null);
                    statusBarIconHolder2.type = 3;
                    statusBarIconHolder2.tag = intValue2;
                    statusBarIconControllerImpl.setIcon(string, statusBarIconHolder2);
                }
            }
            this.this$0.getClass();
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MobileUiAdapter$start$1(MobileUiAdapter mobileUiAdapter, Continuation continuation) {
        super(2, continuation);
        this.this$0 = mobileUiAdapter;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new MobileUiAdapter$start$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((MobileUiAdapter$start$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            MobileUiAdapter mobileUiAdapter = this.this$0;
            mobileUiAdapter.isCollecting = true;
            ReadonlyStateFlow readonlyStateFlow = mobileUiAdapter.mobileIconsViewModel.subscriptionIdsFlow;
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(mobileUiAdapter, null);
            this.label = 1;
            if (FlowKt.collectLatest(readonlyStateFlow, anonymousClass1, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        return Unit.INSTANCE;
    }
}
