package com.android.systemui.statusbar.pipeline.mobile.ui;

import android.content.Context;
import android.util.Log;
import androidx.constraintlayout.motion.widget.KeyAttributes$$ExternalSyntheticOutline0;
import com.android.systemui.BasicRune;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogLevel;
import com.android.systemui.log.LogMessage;
import com.android.systemui.statusbar.phone.StatusBarIconControllerImpl;
import com.android.systemui.statusbar.phone.StatusBarIconHolder;
import com.android.systemui.statusbar.phone.StatusBarIconList;
import java.util.Collections;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.statusbar.pipeline.mobile.ui.MobileUiAdapter$start$1", m277f = "MobileUiAdapter.kt", m278l = {62}, m279m = "invokeSuspend")
/* loaded from: classes2.dex */
final class MobileUiAdapter$start$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ MobileUiAdapter this$0;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    @DebugMetadata(m276c = "com.android.systemui.statusbar.pipeline.mobile.ui.MobileUiAdapter$start$1$1", m277f = "MobileUiAdapter.kt", m278l = {}, m279m = "invokeSuspend")
    /* renamed from: com.android.systemui.statusbar.pipeline.mobile.ui.MobileUiAdapter$start$1$1 */
    final class C33241 extends SuspendLambda implements Function2 {
        /* synthetic */ Object L$0;
        int label;
        final /* synthetic */ MobileUiAdapter this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C33241(MobileUiAdapter mobileUiAdapter, Continuation<? super C33241> continuation) {
            super(2, continuation);
            this.this$0 = mobileUiAdapter;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            C33241 c33241 = new C33241(this.this$0, continuation);
            c33241.L$0 = obj;
            return c33241;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((C33241) create((List) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
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
                    return KeyAttributes$$ExternalSyntheticOutline0.m21m("Sub IDs in MobileUiAdapter being sent to icon controller: ", ((LogMessage) obj2).getStr1());
                }
            };
            LogBuffer logBuffer = mobileViewLogger.buffer;
            LogMessage obtain = logBuffer.obtain("MobileViewLogger", logLevel, mobileViewLogger$logUiAdapterSubIdsSentToIconController$2, null);
            obtain.setStr1(list.toString());
            logBuffer.commit(obtain);
            MobileUiAdapter mobileUiAdapter = this.this$0;
            mobileUiAdapter.lastValue = list;
            StatusBarIconControllerImpl statusBarIconControllerImpl = (StatusBarIconControllerImpl) mobileUiAdapter.iconController;
            statusBarIconControllerImpl.mStatusBarPipelineFlags.useNewMobileIcons();
            Context context = statusBarIconControllerImpl.mContext;
            String string = context.getString(17042930);
            StatusBarIconList statusBarIconList = statusBarIconControllerImpl.mStatusBarIconList;
            StatusBarIconList.Slot slot = statusBarIconList.getSlot(string);
            statusBarIconControllerImpl.removeUnusedIconsInSlot(string, list);
            String string2 = context.getString(17042931);
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
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MobileUiAdapter$start$1(MobileUiAdapter mobileUiAdapter, Continuation<? super MobileUiAdapter$start$1> continuation) {
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
            C33241 c33241 = new C33241(mobileUiAdapter, null);
            this.label = 1;
            if (FlowKt.collectLatest(readonlyStateFlow, c33241, this) == coroutineSingletons) {
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
