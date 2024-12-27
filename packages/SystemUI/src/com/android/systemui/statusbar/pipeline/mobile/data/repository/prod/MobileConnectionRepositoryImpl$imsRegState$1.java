package com.android.systemui.statusbar.pipeline.mobile.data.repository.prod;

import android.app.ActivityThread;
import android.util.Log;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import com.android.systemui.statusbar.pipeline.shared.data.model.ImsRegState;
import com.android.systemui.statusbar.pipeline.shared.data.model.ImsRegStateKt;
import com.android.systemui.statusbar.pipeline.shared.data.repository.ImsRegStateUtil;
import com.sec.ims.ImsManager;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
final class MobileConnectionRepositoryImpl$imsRegState$1 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ MobileConnectionRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MobileConnectionRepositoryImpl$imsRegState$1(MobileConnectionRepositoryImpl mobileConnectionRepositoryImpl, Continuation continuation) {
        super(2, continuation);
        this.this$0 = mobileConnectionRepositoryImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        MobileConnectionRepositoryImpl$imsRegState$1 mobileConnectionRepositoryImpl$imsRegState$1 = new MobileConnectionRepositoryImpl$imsRegState$1(this.this$0, continuation);
        mobileConnectionRepositoryImpl$imsRegState$1.L$0 = obj;
        return mobileConnectionRepositoryImpl$imsRegState$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((MobileConnectionRepositoryImpl$imsRegState$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            ProducerScope producerScope = (ProducerScope) this.L$0;
            final MobileConnectionRepositoryImpl$imsRegState$1$mImsRegStateChangedCallback$1 mobileConnectionRepositoryImpl$imsRegState$1$mImsRegStateChangedCallback$1 = new MobileConnectionRepositoryImpl$imsRegState$1$mImsRegStateChangedCallback$1(this.this$0, producerScope);
            MobileConnectionRepositoryImpl mobileConnectionRepositoryImpl = this.this$0;
            final int i2 = mobileConnectionRepositoryImpl.slotId;
            if (i2 != -1) {
                final ImsRegStateUtil imsRegStateUtil = mobileConnectionRepositoryImpl.imsRegStateUtil;
                imsRegStateUtil.getClass();
                Log.d("ImsRegStateUtil", "registerImsRegStateChangedCallback");
                ((List) ((ArrayList) imsRegStateUtil.imsRegStateChangedCallbacks).get(i2)).add(mobileConnectionRepositoryImpl$imsRegState$1$mImsRegStateChangedCallback$1);
                imsRegStateUtil.imsRegStates.put(Integer.valueOf(i2), ImsRegStateKt.DEFAULT_IMS_REG_STATE);
                imsRegStateUtil._ePDGConnected.updateState(null, Boolean.valueOf(imsRegStateUtil.ePDGConnected()));
                String currentProcessName = ActivityThread.currentProcessName();
                if (currentProcessName != null) {
                    if (currentProcessName.startsWith(imsRegStateUtil.context.getApplicationInfo().processName + ":")) {
                        Log.d("ImsRegStateUtil", "We don't need to connect to ims service in sub-process.");
                    }
                }
                if (((LinkedHashMap) imsRegStateUtil.imsManagers).get(Integer.valueOf(i2)) == null) {
                    imsRegStateUtil.imsManagers.put(Integer.valueOf(i2), new ImsManager(imsRegStateUtil.context, new ImsManager.ConnectionListener() { // from class: com.android.systemui.statusbar.pipeline.shared.data.repository.ImsRegStateUtil$getConnectionListener$1
                        @Override // com.sec.ims.ImsManager.ConnectionListener
                        public final void onConnected() {
                            StringBuilder sb = new StringBuilder("ImsManager onConnected, slotId=");
                            int i3 = i2;
                            RecyclerView$$ExternalSyntheticOutline0.m(i3, "ImsRegStateUtil", sb);
                            ImsRegStateUtil imsRegStateUtil2 = imsRegStateUtil;
                            ImsManager imsManager = (ImsManager) ((LinkedHashMap) imsRegStateUtil2.imsManagers).get(Integer.valueOf(i3));
                            if (imsManager != null) {
                                imsManager.registerImsRegistrationListener(new ImsRegStateUtil$getRegistrationListener$1(i3, imsRegStateUtil2), i3);
                            }
                        }

                        @Override // com.sec.ims.ImsManager.ConnectionListener
                        public final void onDisconnected() {
                            StringBuilder sb = new StringBuilder("ImsManager onDisconnected, slotId=");
                            int i3 = i2;
                            RecyclerView$$ExternalSyntheticOutline0.m(i3, "ImsRegStateUtil", sb);
                            ImsRegStateUtil imsRegStateUtil2 = imsRegStateUtil;
                            ImsManager imsManager = (ImsManager) ((LinkedHashMap) imsRegStateUtil2.imsManagers).get(Integer.valueOf(i3));
                            if (imsManager != null) {
                                imsManager.unregisterImsRegistrationListener(new ImsRegStateUtil$getRegistrationListener$1(i3, imsRegStateUtil2), i3);
                            }
                            ImsRegState imsRegState = imsRegStateUtil2.imsRegState;
                            imsRegState.voWifiRegState = false;
                            imsRegState.voLTERegState = false;
                            imsRegState.ePDGRegState = false;
                            imsRegStateUtil2._ePDGConnected.updateState(null, Boolean.valueOf(imsRegStateUtil2.ePDGConnected()));
                        }
                    }, i2));
                    Log.d("ImsRegStateUtil", "Connect ImsManager: slotId=" + i2 + " imsManager=" + ((LinkedHashMap) imsRegStateUtil.imsManagers).get(Integer.valueOf(i2)));
                    ImsManager imsManager = (ImsManager) ((LinkedHashMap) imsRegStateUtil.imsManagers).get(Integer.valueOf(i2));
                    if (imsManager != null) {
                        imsManager.connectService();
                    }
                }
            }
            final MobileConnectionRepositoryImpl mobileConnectionRepositoryImpl2 = this.this$0;
            Function0 function0 = new Function0() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionRepositoryImpl$imsRegState$1.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    MobileConnectionRepositoryImpl mobileConnectionRepositoryImpl3 = MobileConnectionRepositoryImpl.this;
                    int i3 = mobileConnectionRepositoryImpl3.slotId;
                    if (i3 != -1) {
                        MobileConnectionRepositoryImpl$imsRegState$1$mImsRegStateChangedCallback$1 mobileConnectionRepositoryImpl$imsRegState$1$mImsRegStateChangedCallback$12 = mobileConnectionRepositoryImpl$imsRegState$1$mImsRegStateChangedCallback$1;
                        ImsRegStateUtil imsRegStateUtil2 = mobileConnectionRepositoryImpl3.imsRegStateUtil;
                        imsRegStateUtil2.getClass();
                        Log.d("ImsRegStateUtil", "UNregisterImsRegStateChangedCallback");
                        ((List) ((ArrayList) imsRegStateUtil2.imsRegStateChangedCallbacks).get(i3)).remove(mobileConnectionRepositoryImpl$imsRegState$1$mImsRegStateChangedCallback$12);
                        imsRegStateUtil2.imsRegStates.remove(Integer.valueOf(i3));
                        imsRegStateUtil2._ePDGConnected.updateState(null, Boolean.valueOf(imsRegStateUtil2.ePDGConnected()));
                    }
                    return Unit.INSTANCE;
                }
            };
            this.label = 1;
            if (ProduceKt.awaitClose(producerScope, function0, this) == coroutineSingletons) {
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
