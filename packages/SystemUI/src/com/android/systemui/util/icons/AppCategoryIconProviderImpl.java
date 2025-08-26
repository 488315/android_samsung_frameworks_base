package com.android.systemui.util.icons;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Icon;
import android.os.RemoteException;
import android.util.Log;
import com.android.systemui.assist.AssistManager;
import com.android.systemui.shared.system.PackageManagerWrapper;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes3.dex */
public final class AppCategoryIconProviderImpl implements AppCategoryIconProvider {
    private static final String TAG = "DefaultAppsIconProvider";
    private final AssistManager assistManager;
    private final CoroutineDispatcher backgroundDispatcher;
    private final PackageManager packageManager;
    private final PackageManagerWrapper packageManagerWrapper;
    public static final Companion Companion = new Companion(null);
    public static final int $stable = 8;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* renamed from: com.android.systemui.util.icons.AppCategoryIconProviderImpl$categoryAppIcon$1, reason: invalid class name */
    final class AnonymousClass1 extends ContinuationImpl {
        Object L$0;
        int label;
        /* synthetic */ Object result;

        public AnonymousClass1(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return AppCategoryIconProviderImpl.this.categoryAppIcon(null, this);
        }
    }

    /* renamed from: com.android.systemui.util.icons.AppCategoryIconProviderImpl$getPackageIcon$1, reason: invalid class name and case insensitive filesystem */
    final class C11451 extends ContinuationImpl {
        int label;
        /* synthetic */ Object result;

        public C11451(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return AppCategoryIconProviderImpl.this.getPackageIcon(null, this);
        }
    }

    /* renamed from: com.android.systemui.util.icons.AppCategoryIconProviderImpl$getPackageInfo$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        final /* synthetic */ Intent $intent;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(Intent intent, Continuation continuation) {
            super(2, continuation);
            this.$intent = intent;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return AppCategoryIconProviderImpl.this.new AnonymousClass2(this.$intent, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            ActivityInfo activityInfo;
            String str;
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i != 0) {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                return obj;
            }
            ResultKt.throwOnFailure(obj);
            PackageManagerWrapper packageManagerWrapper = AppCategoryIconProviderImpl.this.packageManagerWrapper;
            Intent intent = this.$intent;
            packageManagerWrapper.getClass();
            ResolveInfo resolveInfoResolveActivity = PackageManagerWrapper.resolveActivity(intent);
            if (resolveInfoResolveActivity == null || (activityInfo = resolveInfoResolveActivity.activityInfo) == null || (str = activityInfo.packageName) == null) {
                return null;
            }
            AppCategoryIconProviderImpl appCategoryIconProviderImpl = AppCategoryIconProviderImpl.this;
            this.label = 1;
            Object packageInfo = appCategoryIconProviderImpl.getPackageInfo(str, this);
            return packageInfo == coroutineSingletons ? coroutineSingletons : packageInfo;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((AnonymousClass2) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }
    }

    /* renamed from: com.android.systemui.util.icons.AppCategoryIconProviderImpl$getPackageInfo$4, reason: invalid class name */
    final class AnonymousClass4 extends SuspendLambda implements Function2 {
        final /* synthetic */ String $packageName;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass4(String str, Continuation continuation) {
            super(2, continuation);
            this.$packageName = str;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return AppCategoryIconProviderImpl.this.new AnonymousClass4(this.$packageName, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            try {
                return AppCategoryIconProviderImpl.this.packageManager.getPackageInfo(this.$packageName, 0);
            } catch (RemoteException unused) {
                Log.e(AppCategoryIconProviderImpl.TAG, "Failed to retrieve package info for " + this.$packageName);
                return null;
            }
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((AnonymousClass4) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }
    }

    public AppCategoryIconProviderImpl(CoroutineDispatcher coroutineDispatcher, AssistManager assistManager, PackageManager packageManager, PackageManagerWrapper packageManagerWrapper) {
        this.backgroundDispatcher = coroutineDispatcher;
        this.assistManager = assistManager;
        this.packageManager = packageManager;
        this.packageManagerWrapper = packageManagerWrapper;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object getPackageIcon(String str, Continuation continuation) {
        C11451 c11451;
        ApplicationInfo applicationInfo;
        int i;
        if (continuation instanceof C11451) {
            c11451 = (C11451) continuation;
            int i2 = c11451.label;
            if ((i2 & Integer.MIN_VALUE) != 0) {
                c11451.label = i2 - Integer.MIN_VALUE;
            } else {
                c11451 = new C11451(continuation);
            }
        }
        Object packageInfo = c11451.result;
        Object obj = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i3 = c11451.label;
        if (i3 == 0) {
            ResultKt.throwOnFailure(packageInfo);
            c11451.label = 1;
            packageInfo = getPackageInfo(str, c11451);
            if (packageInfo == obj) {
                return obj;
            }
        } else {
            if (i3 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(packageInfo);
        }
        PackageInfo packageInfo2 = (PackageInfo) packageInfo;
        if (packageInfo2 == null || (applicationInfo = packageInfo2.applicationInfo) == null || (i = applicationInfo.icon) == 0) {
            return null;
        }
        return Icon.createWithResource(applicationInfo.packageName, i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Object getPackageInfo(Intent intent, Continuation continuation) {
        return BuildersKt.withContext(this.backgroundDispatcher, new AnonymousClass2(intent, null), continuation);
    }

    @Override // com.android.systemui.util.icons.AppCategoryIconProvider
    public Object assistantAppIcon(Continuation continuation) {
        ComponentName assistInfo = this.assistManager.getAssistInfo();
        if (assistInfo == null) {
            return null;
        }
        return getPackageIcon(assistInfo.getPackageName(), continuation);
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    @Override // com.android.systemui.util.icons.AppCategoryIconProvider
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public Object categoryAppIcon(String str, Continuation continuation) {
        AnonymousClass1 anonymousClass1;
        if (continuation instanceof AnonymousClass1) {
            anonymousClass1 = (AnonymousClass1) continuation;
            int i = anonymousClass1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                anonymousClass1.label = i - Integer.MIN_VALUE;
            } else {
                anonymousClass1 = new AnonymousClass1(continuation);
            }
        }
        Object packageInfo = anonymousClass1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = anonymousClass1.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(packageInfo);
            Intent intent = new Intent("android.intent.action.MAIN");
            intent.addCategory(str);
            anonymousClass1.L$0 = this;
            anonymousClass1.label = 1;
            packageInfo = getPackageInfo(intent, anonymousClass1);
            if (packageInfo != coroutineSingletons) {
            }
        }
        if (i2 != 1) {
            if (i2 != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(packageInfo);
            return packageInfo;
        }
        this = (AppCategoryIconProviderImpl) anonymousClass1.L$0;
        ResultKt.throwOnFailure(packageInfo);
        PackageInfo packageInfo2 = (PackageInfo) packageInfo;
        if (packageInfo2 == null) {
            return null;
        }
        String str2 = packageInfo2.packageName;
        anonymousClass1.L$0 = null;
        anonymousClass1.label = 2;
        Object packageIcon = this.getPackageIcon(str2, anonymousClass1);
        return packageIcon == coroutineSingletons ? coroutineSingletons : packageIcon;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Object getPackageInfo(String str, Continuation continuation) {
        return BuildersKt.withContext(this.backgroundDispatcher, new AnonymousClass4(str, null), continuation);
    }
}
