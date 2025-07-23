package com.android.systemui.development.data.repository;

import android.os.Build;
import android.os.UserManager;
import com.android.systemui.util.settings.GlobalSettings;
import com.android.systemui.util.settings.SettingsProxyExt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.flow.Flow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class DevelopmentSettingRepository {
    public static final Companion Companion = new Companion(null);
    public static final int DEFAULT_ENABLED = Intrinsics.areEqual(Build.TYPE, "eng") ? 1 : 0;
    public final CoroutineDispatcher backgroundDispatcher;
    public final GlobalSettings globalSettings;
    public final Flow settingFlow;
    public final UserManager userManager;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* JADX WARN: Code restructure failed: missing block: B:27:0x005d, code lost:
    
        if (r10 == r1) goto L21;
     */
    /* JADX WARN: Removed duplicated region for block: B:25:0x007c  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0049  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0026  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final java.lang.Object access$checkDevelopmentSettingEnabled(com.android.systemui.development.data.repository.DevelopmentSettingRepository r8, android.content.pm.UserInfo r9, kotlin.coroutines.jvm.internal.ContinuationImpl r10) {
        /*
            r8.getClass()
            boolean r0 = r10 instanceof com.android.systemui.development.data.repository.DevelopmentSettingRepository$checkDevelopmentSettingEnabled$1
            if (r0 == 0) goto L16
            r0 = r10
            com.android.systemui.development.data.repository.DevelopmentSettingRepository$checkDevelopmentSettingEnabled$1 r0 = (com.android.systemui.development.data.repository.DevelopmentSettingRepository$checkDevelopmentSettingEnabled$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L16
            int r1 = r1 - r2
            r0.label = r1
            goto L1b
        L16:
            com.android.systemui.development.data.repository.DevelopmentSettingRepository$checkDevelopmentSettingEnabled$1 r0 = new com.android.systemui.development.data.repository.DevelopmentSettingRepository$checkDevelopmentSettingEnabled$1
            r0.<init>(r8, r10)
        L1b:
            java.lang.Object r10 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 2
            r4 = 0
            r5 = 1
            if (r2 == 0) goto L49
            if (r2 == r5) goto L3c
            if (r2 != r3) goto L34
            boolean r8 = r0.Z$0
            java.lang.Object r9 = r0.L$0
            android.content.pm.UserInfo r9 = (android.content.pm.UserInfo) r9
            kotlin.ResultKt.throwOnFailure(r10)
            goto L7f
        L34:
            java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
            java.lang.String r9 = "call to 'resume' before 'invoke' with coroutine"
            r8.<init>(r9)
            throw r8
        L3c:
            java.lang.Object r8 = r0.L$1
            r9 = r8
            android.content.pm.UserInfo r9 = (android.content.pm.UserInfo) r9
            java.lang.Object r8 = r0.L$0
            com.android.systemui.development.data.repository.DevelopmentSettingRepository r8 = (com.android.systemui.development.data.repository.DevelopmentSettingRepository) r8
            kotlin.ResultKt.throwOnFailure(r10)
            goto L60
        L49:
            kotlin.ResultKt.throwOnFailure(r10)
            com.android.systemui.development.data.repository.DevelopmentSettingRepository$checkDevelopmentSettingEnabled$hasUserRestriction$1 r10 = new com.android.systemui.development.data.repository.DevelopmentSettingRepository$checkDevelopmentSettingEnabled$hasUserRestriction$1
            r10.<init>(r8, r9, r4)
            r0.L$0 = r8
            r0.L$1 = r9
            r0.label = r5
            kotlinx.coroutines.CoroutineDispatcher r2 = r8.backgroundDispatcher
            java.lang.Object r10 = kotlinx.coroutines.BuildersKt.withContext(r2, r10, r0)
            if (r10 != r1) goto L60
            goto L7b
        L60:
            java.lang.Boolean r10 = (java.lang.Boolean) r10
            boolean r10 = r10.booleanValue()
            kotlinx.coroutines.CoroutineDispatcher r2 = r8.backgroundDispatcher
            com.android.systemui.development.data.repository.DevelopmentSettingRepository$checkDevelopmentSettingEnabled$isSettingEnabled$1 r6 = new com.android.systemui.development.data.repository.DevelopmentSettingRepository$checkDevelopmentSettingEnabled$isSettingEnabled$1
            r6.<init>(r8, r4)
            r0.L$0 = r9
            r0.L$1 = r4
            r0.Z$0 = r10
            r0.label = r3
            java.lang.Object r8 = kotlinx.coroutines.BuildersKt.withContext(r2, r6, r0)
            if (r8 != r1) goto L7c
        L7b:
            return r1
        L7c:
            r7 = r10
            r10 = r8
            r8 = r7
        L7f:
            java.lang.Boolean r10 = (java.lang.Boolean) r10
            boolean r10 = r10.booleanValue()
            boolean r9 = r9.isAdmin()
            if (r9 == 0) goto L90
            if (r8 != 0) goto L90
            if (r10 == 0) goto L90
            goto L91
        L90:
            r5 = 0
        L91:
            java.lang.Boolean r8 = java.lang.Boolean.valueOf(r5)
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.development.data.repository.DevelopmentSettingRepository.access$checkDevelopmentSettingEnabled(com.android.systemui.development.data.repository.DevelopmentSettingRepository, android.content.pm.UserInfo, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }
}
