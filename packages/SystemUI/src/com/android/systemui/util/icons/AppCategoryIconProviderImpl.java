package com.android.systemui.util.icons;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import com.android.systemui.assist.AssistManager;
import com.android.systemui.shared.system.PackageManagerWrapper;
import kotlin.coroutines.Continuation;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class AppCategoryIconProviderImpl implements AppCategoryIconProvider {
    private static final String TAG = "DefaultAppsIconProvider";
    private final AssistManager assistManager;
    private final CoroutineDispatcher backgroundDispatcher;
    private final PackageManager packageManager;
    private final PackageManagerWrapper packageManagerWrapper;
    public static final Companion Companion = new Companion(null);
    public static final int $stable = 8;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public AppCategoryIconProviderImpl(CoroutineDispatcher coroutineDispatcher, AssistManager assistManager, PackageManager packageManager, PackageManagerWrapper packageManagerWrapper) {
        this.backgroundDispatcher = coroutineDispatcher;
        this.assistManager = assistManager;
        this.packageManager = packageManager;
        this.packageManagerWrapper = packageManagerWrapper;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:24:0x002f  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object getPackageIcon(java.lang.String r5, kotlin.coroutines.Continuation r6) {
        /*
            r4 = this;
            boolean r0 = r6 instanceof com.android.systemui.util.icons.AppCategoryIconProviderImpl$getPackageIcon$1
            if (r0 == 0) goto L13
            r0 = r6
            com.android.systemui.util.icons.AppCategoryIconProviderImpl$getPackageIcon$1 r0 = (com.android.systemui.util.icons.AppCategoryIconProviderImpl$getPackageIcon$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.systemui.util.icons.AppCategoryIconProviderImpl$getPackageIcon$1 r0 = new com.android.systemui.util.icons.AppCategoryIconProviderImpl$getPackageIcon$1
            r0.<init>(r4, r6)
        L18:
            java.lang.Object r6 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L2f
            if (r2 != r3) goto L27
            kotlin.ResultKt.throwOnFailure(r6)
            goto L3b
        L27:
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            r4.<init>(r5)
            throw r4
        L2f:
            kotlin.ResultKt.throwOnFailure(r6)
            r0.label = r3
            java.lang.Object r6 = r4.getPackageInfo(r5, r0)
            if (r6 != r1) goto L3b
            return r1
        L3b:
            android.content.pm.PackageInfo r6 = (android.content.pm.PackageInfo) r6
            r4 = 0
            if (r6 == 0) goto L4f
            android.content.pm.ApplicationInfo r5 = r6.applicationInfo
            if (r5 != 0) goto L45
            goto L4f
        L45:
            int r6 = r5.icon
            if (r6 == 0) goto L4f
            java.lang.String r4 = r5.packageName
            android.graphics.drawable.Icon r4 = android.graphics.drawable.Icon.createWithResource(r4, r6)
        L4f:
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.util.icons.AppCategoryIconProviderImpl.getPackageIcon(java.lang.String, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Object getPackageInfo(Intent intent, Continuation continuation) {
        return BuildersKt.withContext(this.backgroundDispatcher, new AppCategoryIconProviderImpl$getPackageInfo$2(this, intent, null), continuation);
    }

    @Override // com.android.systemui.util.icons.AppCategoryIconProvider
    public Object assistantAppIcon(Continuation continuation) {
        ComponentName assistInfo = this.assistManager.getAssistInfo();
        if (assistInfo == null) {
            return null;
        }
        return getPackageIcon(assistInfo.getPackageName(), continuation);
    }

    /* JADX WARN: Code restructure failed: missing block: B:24:0x004f, code lost:
    
        if (r7 == r1) goto L25;
     */
    /* JADX WARN: Removed duplicated region for block: B:18:0x0057 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0058  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x003a  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0022  */
    @Override // com.android.systemui.util.icons.AppCategoryIconProvider
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.Object categoryAppIcon(java.lang.String r6, kotlin.coroutines.Continuation r7) {
        /*
            r5 = this;
            boolean r0 = r7 instanceof com.android.systemui.util.icons.AppCategoryIconProviderImpl$categoryAppIcon$1
            if (r0 == 0) goto L13
            r0 = r7
            com.android.systemui.util.icons.AppCategoryIconProviderImpl$categoryAppIcon$1 r0 = (com.android.systemui.util.icons.AppCategoryIconProviderImpl$categoryAppIcon$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.systemui.util.icons.AppCategoryIconProviderImpl$categoryAppIcon$1 r0 = new com.android.systemui.util.icons.AppCategoryIconProviderImpl$categoryAppIcon$1
            r0.<init>(r5, r7)
        L18:
            java.lang.Object r7 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 2
            r4 = 1
            if (r2 == 0) goto L3a
            if (r2 == r4) goto L32
            if (r2 != r3) goto L2a
            kotlin.ResultKt.throwOnFailure(r7)
            return r7
        L2a:
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
            java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
            r5.<init>(r6)
            throw r5
        L32:
            java.lang.Object r5 = r0.L$0
            com.android.systemui.util.icons.AppCategoryIconProviderImpl r5 = (com.android.systemui.util.icons.AppCategoryIconProviderImpl) r5
            kotlin.ResultKt.throwOnFailure(r7)
            goto L52
        L3a:
            kotlin.ResultKt.throwOnFailure(r7)
            android.content.Intent r7 = new android.content.Intent
            java.lang.String r2 = "android.intent.action.MAIN"
            r7.<init>(r2)
            r7.addCategory(r6)
            r0.L$0 = r5
            r0.label = r4
            java.lang.Object r7 = r5.getPackageInfo(r7, r0)
            if (r7 != r1) goto L52
            goto L64
        L52:
            android.content.pm.PackageInfo r7 = (android.content.pm.PackageInfo) r7
            r6 = 0
            if (r7 != 0) goto L58
            return r6
        L58:
            java.lang.String r7 = r7.packageName
            r0.L$0 = r6
            r0.label = r3
            java.lang.Object r5 = r5.getPackageIcon(r7, r0)
            if (r5 != r1) goto L65
        L64:
            return r1
        L65:
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.util.icons.AppCategoryIconProviderImpl.categoryAppIcon(java.lang.String, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Object getPackageInfo(String str, Continuation continuation) {
        return BuildersKt.withContext(this.backgroundDispatcher, new AppCategoryIconProviderImpl$getPackageInfo$4(this, str, null), continuation);
    }
}
