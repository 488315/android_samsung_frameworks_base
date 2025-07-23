package com.android.systemui.qs.shared;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.UserHandle;
import com.android.systemui.user.data.repository.UserRepositoryImpl;
import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class QSSettingsPackageRepository$init$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ QSSettingsPackageRepository this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public QSSettingsPackageRepository$init$1(QSSettingsPackageRepository qSSettingsPackageRepository, Continuation continuation) {
        super(2, continuation);
        this.this$0 = qSSettingsPackageRepository;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new QSSettingsPackageRepository$init$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((QSSettingsPackageRepository$init$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        String str;
        ActivityInfo activityInfo;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        QSSettingsPackageRepository qSSettingsPackageRepository = this.this$0;
        PackageManager packageManager = qSSettingsPackageRepository.context.createContextAsUser(UserHandle.of(((UserRepositoryImpl) qSSettingsPackageRepository.userRepository).mainUserId), 0).getPackageManager();
        QSSettingsPackageRepository qSSettingsPackageRepository2 = this.this$0;
        ResolveInfo resolveInfo = (ResolveInfo) CollectionsKt___CollectionsKt.firstOrNull((List) packageManager.queryIntentActivities(new Intent("android.settings.SETTINGS"), 1114112));
        if (resolveInfo == null || (activityInfo = resolveInfo.activityInfo) == null || (str = activityInfo.packageName) == null) {
            str = KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG;
        }
        qSSettingsPackageRepository2.settingsPackageName = str;
        return Unit.INSTANCE;
    }
}
