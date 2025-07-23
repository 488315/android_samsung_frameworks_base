package com.android.wm.shell.desktopmode;

import com.android.internal.protolog.ProtoLog;
import com.android.wm.shell.desktopmode.persistence.DesktopPersistentRepository;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.SpreadBuilder;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class DesktopUserRepositories$sanitizeUsers$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ List<Integer> $usersToDelete;
    int label;
    final /* synthetic */ DesktopUserRepositories this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DesktopUserRepositories$sanitizeUsers$2(DesktopUserRepositories desktopUserRepositories, List<Integer> list, Continuation continuation) {
        super(2, continuation);
        this.this$0 = desktopUserRepositories;
        this.$usersToDelete = list;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new DesktopUserRepositories$sanitizeUsers$2(this.this$0, this.$usersToDelete, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((DesktopUserRepositories$sanitizeUsers$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v9 */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        try {
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                DesktopPersistentRepository desktopPersistentRepository = this.this$0.persistentRepository;
                List<Integer> list = this.$usersToDelete;
                this.label = 1;
                Object removeUsers = desktopPersistentRepository.removeUsers(list, this);
                this = removeUsers;
                if (removeUsers == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                this = this;
            }
        } catch (Exception e) {
            DesktopUserRepositories desktopUserRepositories = this.this$0;
            Object[] objArr = {e.getStackTrace()};
            int i2 = DesktopUserRepositories.$r8$clinit;
            desktopUserRepositories.getClass();
            ShellProtoLogGroup shellProtoLogGroup = ShellProtoLogGroup.WM_SHELL_DESKTOP_MODE;
            SpreadBuilder m = DesktopDisplayEventHandler$$ExternalSyntheticOutline0.m(2, "DesktopUserRepositories", objArr);
            ProtoLog.e(shellProtoLogGroup, "%s: An exception occurred while updating the persistent repository \n%s", m.list.toArray(new Object[m.list.size()]));
        }
        return Unit.INSTANCE;
    }
}
