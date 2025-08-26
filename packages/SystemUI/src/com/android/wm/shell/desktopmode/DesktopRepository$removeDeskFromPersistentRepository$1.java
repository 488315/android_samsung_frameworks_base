package com.android.wm.shell.desktopmode;

import com.android.wm.shell.desktopmode.DesktopRepository;
import com.android.wm.shell.desktopmode.persistence.DesktopPersistentRepository;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes3.dex */
final class DesktopRepository$removeDeskFromPersistentRepository$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ DesktopRepository.Desk $desk;
    int label;
    final /* synthetic */ DesktopRepository this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DesktopRepository$removeDeskFromPersistentRepository$1(DesktopRepository desktopRepository, DesktopRepository.Desk desk, Continuation continuation) {
        super(2, continuation);
        this.this$0 = desktopRepository;
        this.$desk = desk;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new DesktopRepository$removeDeskFromPersistentRepository$1(this.this$0, this.$desk, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((DesktopRepository$removeDeskFromPersistentRepository$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r6v8 */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        try {
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                DesktopRepository desktopRepository = this.this$0;
                desktopRepository.logD("updatePersistentRepositoryForRemovedDesk user=%d desk=%d", new Integer(desktopRepository.userId), new Integer(this.$desk.deskId));
                DesktopRepository desktopRepository2 = this.this$0;
                DesktopPersistentRepository desktopPersistentRepository = desktopRepository2.persistentRepository;
                int i2 = desktopRepository2.userId;
                int i3 = this.$desk.deskId;
                this.label = 1;
                Object objRemoveDesktop = desktopPersistentRepository.removeDesktop(i2, i3, this);
                this = objRemoveDesktop;
                if (objRemoveDesktop == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                this = this;
            }
        } catch (Throwable th) {
            DesktopRepository desktopRepository3 = this.this$0;
            Object[] objArr = {th.getStackTrace()};
            int i4 = DesktopRepository.$r8$clinit;
            desktopRepository3.logE("An exception occurred while updating the persistent repository \n%s", objArr);
        }
        return Unit.INSTANCE;
    }
}
