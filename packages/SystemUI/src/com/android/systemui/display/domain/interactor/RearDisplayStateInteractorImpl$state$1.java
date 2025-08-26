package com.android.systemui.display.domain.interactor;

import android.view.Display;
import com.android.systemui.display.data.repository.DeviceStateRepository;
import com.android.systemui.display.domain.interactor.RearDisplayStateInteractor;
import java.util.Iterator;
import java.util.Set;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function4;
import kotlinx.coroutines.flow.FlowCollector;

/* loaded from: classes2.dex */
final class RearDisplayStateInteractorImpl$state$1 extends SuspendLambda implements Function4 {
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    /* synthetic */ Object L$2;
    int label;

    public RearDisplayStateInteractorImpl$state$1(Continuation continuation) {
        super(4, continuation);
    }

    @Override // kotlin.jvm.functions.Function4
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4) {
        RearDisplayStateInteractorImpl$state$1 rearDisplayStateInteractorImpl$state$1 = new RearDisplayStateInteractorImpl$state$1((Continuation) obj4);
        rearDisplayStateInteractorImpl$state$1.L$0 = (FlowCollector) obj;
        rearDisplayStateInteractorImpl$state$1.L$1 = (DeviceStateRepository.DeviceState) obj2;
        rearDisplayStateInteractorImpl$state$1.L$2 = (Set) obj3;
        return rearDisplayStateInteractorImpl$state$1.invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:20:0x0058, code lost:
    
        if (r9.emit(r1, r8) == r0) goto L25;
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x006c, code lost:
    
        if (r9.emit(r1, r8) == r0) goto L25;
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x006e, code lost:
    
        return r0;
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) {
        Object next;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            DeviceStateRepository.DeviceState deviceState = (DeviceStateRepository.DeviceState) this.L$1;
            Iterator it = ((Set) this.L$2).iterator();
            while (true) {
                if (!it.hasNext()) {
                    next = null;
                    break;
                }
                next = it.next();
                if ((((Display) next).getFlags() & 8192) != 0) {
                    break;
                }
            }
            Display display = (Display) next;
            if (deviceState != DeviceStateRepository.DeviceState.REAR_DISPLAY_OUTER_DEFAULT) {
                RearDisplayStateInteractor.State.Disabled disabled = RearDisplayStateInteractor.State.Disabled.INSTANCE;
                this.L$0 = null;
                this.L$1 = null;
                this.label = 1;
            } else if (display != null) {
                RearDisplayStateInteractor.State.Enabled enabled = new RearDisplayStateInteractor.State.Enabled(display);
                this.L$0 = null;
                this.L$1 = null;
                this.label = 2;
            }
        } else {
            if (i != 1 && i != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        return Unit.INSTANCE;
    }
}
