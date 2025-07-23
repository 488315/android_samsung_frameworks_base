package com.android.systemui.development.domain.interactor;

import android.content.ClipData;
import android.content.ClipboardManager;
import com.android.systemui.user.data.repository.UserRepositoryImpl;
import com.android.systemui.user.utils.UserScopedServiceImpl;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class BuildNumberInteractor$copyBuildNumber$2 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ BuildNumberInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public BuildNumberInteractor$copyBuildNumber$2(BuildNumberInteractor buildNumberInteractor, Continuation continuation) {
        super(2, continuation);
        this.this$0 = buildNumberInteractor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new BuildNumberInteractor$copyBuildNumber$2(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((BuildNumberInteractor$copyBuildNumber$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        BuildNumberInteractor buildNumberInteractor = this.this$0;
        ClipboardManager clipboardManager = (ClipboardManager) ((UserScopedServiceImpl) buildNumberInteractor.clipboardManagerProvider).forUser(((UserRepositoryImpl) buildNumberInteractor.userRepository).getSelectedUserInfo().getUserHandle());
        BuildNumberInteractor buildNumberInteractor2 = this.this$0;
        clipboardManager.setPrimaryClip(ClipData.newPlainText(buildNumberInteractor2.clipLabel, buildNumberInteractor2.buildText));
        return Unit.INSTANCE;
    }
}
