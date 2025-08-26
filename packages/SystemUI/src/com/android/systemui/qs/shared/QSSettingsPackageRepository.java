package com.android.systemui.qs.shared;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.UserHandle;
import com.android.systemui.user.data.repository.UserRepository;
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
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes2.dex */
public final class QSSettingsPackageRepository {
    public final CoroutineScope backgroundScope;
    public final Context context;
    public String settingsPackageName;
    public final UserRepository userRepository;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* renamed from: com.android.systemui.qs.shared.QSSettingsPackageRepository$init$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        int label;

        public AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return QSSettingsPackageRepository.this.new AnonymousClass1(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
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
            QSSettingsPackageRepository qSSettingsPackageRepository = QSSettingsPackageRepository.this;
            PackageManager packageManager = qSSettingsPackageRepository.context.createContextAsUser(UserHandle.of(((UserRepositoryImpl) qSSettingsPackageRepository.userRepository).mainUserId), 0).getPackageManager();
            QSSettingsPackageRepository qSSettingsPackageRepository2 = QSSettingsPackageRepository.this;
            ResolveInfo resolveInfo = (ResolveInfo) CollectionsKt___CollectionsKt.firstOrNull((List) packageManager.queryIntentActivities(new Intent("android.settings.SETTINGS"), 1114112));
            if (resolveInfo == null || (activityInfo = resolveInfo.activityInfo) == null || (str = activityInfo.packageName) == null) {
                str = KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG;
            }
            qSSettingsPackageRepository2.settingsPackageName = str;
            return Unit.INSTANCE;
        }
    }

    static {
        new Companion(null);
    }

    public QSSettingsPackageRepository(Context context, CoroutineScope coroutineScope, UserRepository userRepository) {
        this.context = context;
        this.backgroundScope = coroutineScope;
        this.userRepository = userRepository;
    }

    public final String getSettingsPackageName() {
        String str = this.settingsPackageName;
        return str == null ? KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG : str;
    }

    public final void init() {
        BuildersKt.launch$default(this.backgroundScope, null, null, new AnonymousClass1(null), 3);
    }
}
