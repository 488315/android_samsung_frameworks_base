package com.android.settingslib.wifi;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.security.advancedprotection.AdvancedProtectionManager;
import com.android.settingslib.wifi.WifiUtils;
import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.android.HandlerContext;
import kotlinx.coroutines.internal.MainDispatcherLoader;
import kotlinx.coroutines.scheduling.DefaultScheduler;

/* loaded from: classes.dex */
final class WifiUtils$Companion$checkWepAllowed$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ Context $context;
    final /* synthetic */ int $dialogWindowType;
    final /* synthetic */ Function0 $onAllowed;
    final /* synthetic */ Function1 $onStartAapmActivity;
    final /* synthetic */ Function1 $onStartActivity;
    final /* synthetic */ String $ssid;
    Object L$0;
    int label;

    /* renamed from: com.android.settingslib.wifi.WifiUtils$Companion$checkWepAllowed$2$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ Intent $intent;
        final /* synthetic */ Function1 $onStartAapmActivity;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(Function1 function1, Intent intent, Continuation continuation) {
            super(2, continuation);
            this.$onStartAapmActivity = function1;
            this.$intent = intent;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass1(this.$onStartAapmActivity, this.$intent, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            this.$onStartAapmActivity.mo781invoke(this.$intent);
            return Unit.INSTANCE;
        }
    }

    /* renamed from: com.android.settingslib.wifi.WifiUtils$Companion$checkWepAllowed$2$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        final /* synthetic */ Function0 $onAllowed;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(Function0 function0, Continuation continuation) {
            super(2, continuation);
            this.$onAllowed = function0;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass2(this.$onAllowed, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            this.$onAllowed.invoke();
            return Unit.INSTANCE;
        }
    }

    /* renamed from: com.android.settingslib.wifi.WifiUtils$Companion$checkWepAllowed$2$3, reason: invalid class name */
    final class AnonymousClass3 extends SuspendLambda implements Function2 {
        final /* synthetic */ Intent $intent;
        final /* synthetic */ Function1 $onStartActivity;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass3(Function1 function1, Intent intent, Continuation continuation) {
            super(2, continuation);
            this.$onStartActivity = function1;
            this.$intent = intent;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass3(this.$onStartActivity, this.$intent, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass3) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            this.$onStartActivity.mo781invoke(this.$intent);
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public WifiUtils$Companion$checkWepAllowed$2(Context context, int i, Function1 function1, Function0 function0, String str, Function1 function12, Continuation continuation) {
        super(2, continuation);
        this.$context = context;
        this.$dialogWindowType = i;
        this.$onStartAapmActivity = function1;
        this.$onAllowed = function0;
        this.$ssid = str;
        this.$onStartActivity = function12;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new WifiUtils$Companion$checkWepAllowed$2(this.$context, this.$dialogWindowType, this.$onStartAapmActivity, this.$onAllowed, this.$ssid, this.$onStartActivity, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((WifiUtils$Companion$checkWepAllowed$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:25:0x0083, code lost:
    
        if (kotlinx.coroutines.BuildersKt.withContext(r1, r2, r10) == r0) goto L39;
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x00bc, code lost:
    
        if (kotlinx.coroutines.BuildersKt.withContext(r11, r1, r10) == r0) goto L39;
     */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x00f8, code lost:
    
        if (kotlinx.coroutines.BuildersKt.withContext(r1, r2, r10) == r0) goto L39;
     */
    /* JADX WARN: Removed duplicated region for block: B:34:0x00ab  */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) throws Throwable {
        WifiManager wifiManager;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            wifiManager = (WifiManager) this.$context.getSystemService(WifiManager.class);
            if (wifiManager == null) {
                return Unit.INSTANCE;
            }
            WifiUtils.Companion companion = WifiUtils.Companion;
            this.L$0 = wifiManager;
            this.label = 1;
            companion.getClass();
            obj = Boolean.FALSE;
            if (obj != coroutineSingletons) {
            }
            return coroutineSingletons;
        }
        if (i != 1) {
            if (i != 2) {
                if (i == 3) {
                    ResultKt.throwOnFailure(obj);
                    if (((Boolean) obj).booleanValue()) {
                        DefaultScheduler defaultScheduler = Dispatchers.Default;
                        HandlerContext handlerContext = MainDispatcherLoader.dispatcher;
                        AnonymousClass2 anonymousClass2 = new AnonymousClass2(this.$onAllowed, null);
                        this.label = 4;
                    }
                    Intent intent = new Intent("android.intent.action.MAIN");
                    int i2 = this.$dialogWindowType;
                    String str = this.$ssid;
                    intent.setComponent(new ComponentName(KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG, "com.android.settings.network.WepNetworkDialogActivity"));
                    intent.putExtra("dialog_window_type", i2);
                    intent.putExtra("ssid", str);
                    Intent intentAddFlags = intent.addFlags(268435456);
                    DefaultScheduler defaultScheduler2 = Dispatchers.Default;
                    HandlerContext handlerContext2 = MainDispatcherLoader.dispatcher;
                    AnonymousClass3 anonymousClass3 = new AnonymousClass3(this.$onStartActivity, intentAddFlags, null);
                    this.L$0 = null;
                    this.label = 5;
                } else if (i != 4 && i != 5) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
            }
            ResultKt.throwOnFailure(obj);
            return Unit.INSTANCE;
        }
        wifiManager = (WifiManager) this.L$0;
        ResultKt.throwOnFailure(obj);
        if (((Boolean) obj).booleanValue()) {
            Intent intentCreateSupportIntent = AdvancedProtectionManager.createSupportIntent(3, 1);
            intentCreateSupportIntent.putExtra("dialog_window_type", this.$dialogWindowType);
            DefaultScheduler defaultScheduler3 = Dispatchers.Default;
            HandlerContext handlerContext3 = MainDispatcherLoader.dispatcher;
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$onStartAapmActivity, intentCreateSupportIntent, null);
            this.L$0 = null;
            this.label = 2;
        } else if (wifiManager.isWepSupported()) {
            WifiUtils.Companion companion2 = WifiUtils.Companion;
            this.L$0 = null;
            this.label = 3;
            companion2.getClass();
            obj = BuildersKt.withContext(Dispatchers.Default, new WifiUtils$Companion$queryWepAllowed$2(wifiManager, null), this);
            if (obj != coroutineSingletons) {
                if (((Boolean) obj).booleanValue()) {
                }
                Intent intent2 = new Intent("android.intent.action.MAIN");
                int i22 = this.$dialogWindowType;
                String str2 = this.$ssid;
                intent2.setComponent(new ComponentName(KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG, "com.android.settings.network.WepNetworkDialogActivity"));
                intent2.putExtra("dialog_window_type", i22);
                intent2.putExtra("ssid", str2);
                Intent intentAddFlags2 = intent2.addFlags(268435456);
                DefaultScheduler defaultScheduler22 = Dispatchers.Default;
                HandlerContext handlerContext22 = MainDispatcherLoader.dispatcher;
                AnonymousClass3 anonymousClass32 = new AnonymousClass3(this.$onStartActivity, intentAddFlags2, null);
                this.L$0 = null;
                this.label = 5;
            }
        } else {
            Intent intent22 = new Intent("android.intent.action.MAIN");
            int i222 = this.$dialogWindowType;
            String str22 = this.$ssid;
            intent22.setComponent(new ComponentName(KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG, "com.android.settings.network.WepNetworkDialogActivity"));
            intent22.putExtra("dialog_window_type", i222);
            intent22.putExtra("ssid", str22);
            Intent intentAddFlags22 = intent22.addFlags(268435456);
            DefaultScheduler defaultScheduler222 = Dispatchers.Default;
            HandlerContext handlerContext222 = MainDispatcherLoader.dispatcher;
            AnonymousClass3 anonymousClass322 = new AnonymousClass3(this.$onStartActivity, intentAddFlags22, null);
            this.L$0 = null;
            this.label = 5;
        }
        return coroutineSingletons;
    }
}
