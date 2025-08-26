package com.android.systemui.development.data.repository;

import android.content.pm.UserInfo;
import android.os.Build;
import android.os.UserManager;
import com.android.systemui.util.settings.GlobalSettings;
import com.android.systemui.util.settings.SettingsProxyExt;
import kotlin.ResultKt;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.flow.Flow;

/* loaded from: classes2.dex */
public final class DevelopmentSettingRepository {
    public static final Companion Companion = new Companion(null);
    public static final int DEFAULT_ENABLED = Intrinsics.areEqual(Build.TYPE, "eng") ? 1 : 0;
    public final CoroutineDispatcher backgroundDispatcher;
    public final GlobalSettings globalSettings;
    public final Flow settingFlow;
    public final UserManager userManager;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public DevelopmentSettingRepository(GlobalSettings globalSettings, UserManager userManager, CoroutineDispatcher coroutineDispatcher) {
        this.globalSettings = globalSettings;
        this.userManager = userManager;
        this.backgroundDispatcher = coroutineDispatcher;
        this.settingFlow = SettingsProxyExt.INSTANCE.observerFlow(globalSettings, "development_settings_enabled");
    }

    /* JADX WARN: Removed duplicated region for block: B:28:0x0090  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0016  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Object access$checkDevelopmentSettingEnabled(DevelopmentSettingRepository developmentSettingRepository, UserInfo userInfo, ContinuationImpl continuationImpl) throws Throwable {
        DevelopmentSettingRepository$checkDevelopmentSettingEnabled$1 developmentSettingRepository$checkDevelopmentSettingEnabled$1;
        boolean z;
        developmentSettingRepository.getClass();
        if (continuationImpl instanceof DevelopmentSettingRepository$checkDevelopmentSettingEnabled$1) {
            developmentSettingRepository$checkDevelopmentSettingEnabled$1 = (DevelopmentSettingRepository$checkDevelopmentSettingEnabled$1) continuationImpl;
            int i = developmentSettingRepository$checkDevelopmentSettingEnabled$1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                developmentSettingRepository$checkDevelopmentSettingEnabled$1.label = i - Integer.MIN_VALUE;
            } else {
                developmentSettingRepository$checkDevelopmentSettingEnabled$1 = new DevelopmentSettingRepository$checkDevelopmentSettingEnabled$1(developmentSettingRepository, continuationImpl);
            }
        }
        Object objWithContext = developmentSettingRepository$checkDevelopmentSettingEnabled$1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = developmentSettingRepository$checkDevelopmentSettingEnabled$1.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(objWithContext);
            DevelopmentSettingRepository$checkDevelopmentSettingEnabled$hasUserRestriction$1 developmentSettingRepository$checkDevelopmentSettingEnabled$hasUserRestriction$1 = new DevelopmentSettingRepository$checkDevelopmentSettingEnabled$hasUserRestriction$1(developmentSettingRepository, userInfo, null);
            developmentSettingRepository$checkDevelopmentSettingEnabled$1.L$0 = developmentSettingRepository;
            developmentSettingRepository$checkDevelopmentSettingEnabled$1.L$1 = userInfo;
            developmentSettingRepository$checkDevelopmentSettingEnabled$1.label = 1;
            objWithContext = BuildersKt.withContext(developmentSettingRepository.backgroundDispatcher, developmentSettingRepository$checkDevelopmentSettingEnabled$hasUserRestriction$1, developmentSettingRepository$checkDevelopmentSettingEnabled$1);
            if (objWithContext != coroutineSingletons) {
            }
            return coroutineSingletons;
        }
        if (i2 != 1) {
            if (i2 != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            z = developmentSettingRepository$checkDevelopmentSettingEnabled$1.Z$0;
            userInfo = (UserInfo) developmentSettingRepository$checkDevelopmentSettingEnabled$1.L$0;
            ResultKt.throwOnFailure(objWithContext);
            return Boolean.valueOf((userInfo.isAdmin() || z || !((Boolean) objWithContext).booleanValue()) ? false : true);
        }
        userInfo = (UserInfo) developmentSettingRepository$checkDevelopmentSettingEnabled$1.L$1;
        developmentSettingRepository = (DevelopmentSettingRepository) developmentSettingRepository$checkDevelopmentSettingEnabled$1.L$0;
        ResultKt.throwOnFailure(objWithContext);
        boolean zBooleanValue = ((Boolean) objWithContext).booleanValue();
        CoroutineDispatcher coroutineDispatcher = developmentSettingRepository.backgroundDispatcher;
        DevelopmentSettingRepository$checkDevelopmentSettingEnabled$isSettingEnabled$1 developmentSettingRepository$checkDevelopmentSettingEnabled$isSettingEnabled$1 = new DevelopmentSettingRepository$checkDevelopmentSettingEnabled$isSettingEnabled$1(developmentSettingRepository, null);
        developmentSettingRepository$checkDevelopmentSettingEnabled$1.L$0 = userInfo;
        developmentSettingRepository$checkDevelopmentSettingEnabled$1.L$1 = null;
        developmentSettingRepository$checkDevelopmentSettingEnabled$1.Z$0 = zBooleanValue;
        developmentSettingRepository$checkDevelopmentSettingEnabled$1.label = 2;
        Object objWithContext2 = BuildersKt.withContext(coroutineDispatcher, developmentSettingRepository$checkDevelopmentSettingEnabled$isSettingEnabled$1, developmentSettingRepository$checkDevelopmentSettingEnabled$1);
        if (objWithContext2 != coroutineSingletons) {
            objWithContext = objWithContext2;
            z = zBooleanValue;
            return Boolean.valueOf((userInfo.isAdmin() || z || !((Boolean) objWithContext).booleanValue()) ? false : true);
        }
        return coroutineSingletons;
    }
}
