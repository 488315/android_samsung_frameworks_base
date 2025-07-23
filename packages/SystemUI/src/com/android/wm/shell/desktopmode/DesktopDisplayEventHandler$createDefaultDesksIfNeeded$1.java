package com.android.wm.shell.desktopmode;

import com.android.wm.shell.desktopmode.persistence.DesktopRepositoryInitializerImpl;
import com.android.wm.shell.shared.desktopmode.DesktopStateImpl;
import java.util.Iterator;
import java.util.Set;
import kotlin.KotlinNothingValueException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.StateFlowImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class DesktopDisplayEventHandler$createDefaultDesksIfNeeded$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ Set<Integer> $displayIds;
    final /* synthetic */ Integer $userId;
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ DesktopDisplayEventHandler this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DesktopDisplayEventHandler$createDefaultDesksIfNeeded$1(DesktopDisplayEventHandler desktopDisplayEventHandler, Set<Integer> set, Integer num, Continuation continuation) {
        super(2, continuation);
        this.this$0 = desktopDisplayEventHandler;
        this.$displayIds = set;
        this.$userId = num;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        DesktopDisplayEventHandler$createDefaultDesksIfNeeded$1 desktopDisplayEventHandler$createDefaultDesksIfNeeded$1 = new DesktopDisplayEventHandler$createDefaultDesksIfNeeded$1(this.this$0, this.$displayIds, this.$userId, continuation);
        desktopDisplayEventHandler$createDefaultDesksIfNeeded$1.L$0 = obj;
        return desktopDisplayEventHandler$createDefaultDesksIfNeeded$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((DesktopDisplayEventHandler$createDefaultDesksIfNeeded$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
            final DesktopDisplayEventHandler desktopDisplayEventHandler = this.this$0;
            StateFlowImpl stateFlowImpl = ((DesktopRepositoryInitializerImpl) desktopDisplayEventHandler.desktopRepositoryInitializer).isInitialized;
            final Set<Integer> set = this.$displayIds;
            final Integer num = this.$userId;
            FlowCollector flowCollector = new FlowCollector() { // from class: com.android.wm.shell.desktopmode.DesktopDisplayEventHandler$createDefaultDesksIfNeeded$1.1
                @Override // kotlinx.coroutines.flow.FlowCollector
                public final Object emit(Object obj2, Continuation continuation) {
                    boolean booleanValue = ((Boolean) obj2).booleanValue();
                    DesktopDisplayEventHandler desktopDisplayEventHandler2 = DesktopDisplayEventHandler.this;
                    if (!booleanValue) {
                        ((DesktopRepositoryInitializerImpl) desktopDisplayEventHandler2.desktopRepositoryInitializer).addedDisplayIdsBeforeInitialized.addAll(set);
                        return Unit.INSTANCE;
                    }
                    Integer num2 = num;
                    DesktopRepository profile = num2 != null ? desktopDisplayEventHandler2.desktopUserRepositories.getProfile(num2.intValue()) : desktopDisplayEventHandler2.desktopUserRepositories.getCurrent();
                    Iterator it = set.iterator();
                    while (it.hasNext()) {
                        int intValue = ((Number) it.next()).intValue();
                        int i2 = DesktopDisplayEventHandler.$r8$clinit;
                        if (intValue == -1) {
                            desktopDisplayEventHandler2.getClass();
                            DesktopDisplayEventHandler.logV$3("shouldCreateOrWarmUpDesk skipping reason: invalid display", new Object[0]);
                        } else if (!((DesktopStateImpl) desktopDisplayEventHandler2.desktopState).isDesktopModeSupportedOnDisplay(intValue) && intValue != 0) {
                            DesktopDisplayEventHandler.logV$3("shouldCreateOrWarmUpDesk skipping displayId=%d reason: desktop ineligible", Integer.valueOf(intValue));
                        } else if (profile.desktopData.getNumberOfDesks(intValue) > 0) {
                            DesktopDisplayEventHandler.logV$3("shouldCreateOrWarmUpDesk skipping displayId=%d reason: has desk(s)", Integer.valueOf(intValue));
                        } else if (desktopDisplayEventHandler2.displayController.mDisplayManager.getDisplay(intValue) == null) {
                            DesktopDisplayEventHandler.logV$3("shouldCreateOrWarmUpDesk skipping displayId=%d reason: display null", Integer.valueOf(intValue));
                        } else if (intValue != 0) {
                            DesktopDisplayEventHandler.logV$3("Display %d is desktop-first and needs a default desk", new Integer(intValue));
                            DesktopTasksController.createDesk$default(desktopDisplayEventHandler2.desktopTasksController, intValue, profile.userId, true, null, 48);
                        } else {
                            DesktopDisplayEventHandler.logV$3("Display %d is touch-first and needs a default desk that is not activated", new Integer(intValue));
                            DesktopTasksController.createDesk$default(desktopDisplayEventHandler2.desktopTasksController, intValue, profile.userId, false, null, 52);
                        }
                    }
                    CoroutineScopeKt.cancel(coroutineScope, null);
                    return Unit.INSTANCE;
                }
            };
            this.label = 1;
            if (stateFlowImpl.collect(flowCollector, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        throw new KotlinNothingValueException();
    }
}
