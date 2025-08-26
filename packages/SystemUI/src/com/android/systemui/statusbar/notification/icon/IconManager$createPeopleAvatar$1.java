package com.android.systemui.statusbar.notification.icon;

import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes3.dex */
final class IconManager$createPeopleAvatar$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ NotificationEntry $entry;
    int label;
    final /* synthetic */ IconManager this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public IconManager$createPeopleAvatar$1(IconManager iconManager, NotificationEntry notificationEntry, Continuation continuation) {
        super(2, continuation);
        this.this$0 = iconManager;
        this.$entry = notificationEntry;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new IconManager$createPeopleAvatar$1(this.this$0, this.$entry, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((IconManager$createPeopleAvatar$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) throws Throwable {
        Object obj2 = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            IconManager iconManager = this.this$0;
            NotificationEntry notificationEntry = this.$entry;
            this.label = 1;
            iconManager.getClass();
            Object objWithContext = BuildersKt.withContext(iconManager.bgCoroutineContext, new IconManager$getLauncherShortcutIconForPeopleAvatar$2(notificationEntry, iconManager, null), this);
            if (objWithContext != obj2) {
                objWithContext = Unit.INSTANCE;
            }
            if (objWithContext == obj2) {
                return obj2;
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
