package com.android.systemui.keyboard.shortcut.ui.viewmodel;

import com.android.systemui.settings.UserTrackerImpl;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class ShortcutHelperViewModel$isLauncherApp$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ String $packageName;
    int label;
    final /* synthetic */ ShortcutHelperViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ShortcutHelperViewModel$isLauncherApp$2(ShortcutHelperViewModel shortcutHelperViewModel, String str, Continuation continuation) {
        super(2, continuation);
        this.this$0 = shortcutHelperViewModel;
        this.$packageName = str;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new ShortcutHelperViewModel$isLauncherApp$2(this.this$0, this.$packageName, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((ShortcutHelperViewModel$isLauncherApp$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        ShortcutHelperViewModel shortcutHelperViewModel = this.this$0;
        return Boolean.valueOf(Intrinsics.areEqual(CollectionsKt___CollectionsKt.firstOrNull(shortcutHelperViewModel.roleManager.getRoleHoldersAsUser("android.app.role.HOME", ((UserTrackerImpl) shortcutHelperViewModel.userTracker).getUserHandle())), this.$packageName));
    }
}
