package com.android.systemui.media.mediaoutput.activity;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.Log;
import com.android.systemui.popup.util.PopupUIUtil;
import java.util.Arrays;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class MediaOutputWindow$Companion$dismissCallback$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ Context $this_dismissCallback;
    private /* synthetic */ Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MediaOutputWindow$Companion$dismissCallback$1(Context context, Continuation continuation) {
        super(2, continuation);
        this.$this_dismissCallback = context;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        MediaOutputWindow$Companion$dismissCallback$1 mediaOutputWindow$Companion$dismissCallback$1 = new MediaOutputWindow$Companion$dismissCallback$1(this.$this_dismissCallback, continuation);
        mediaOutputWindow$Companion$dismissCallback$1.L$0 = obj;
        return mediaOutputWindow$Companion$dismissCallback$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((MediaOutputWindow$Companion$dismissCallback$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v0, types: [android.app.ActivityManager$SemActivityControllerListener, com.android.systemui.media.mediaoutput.activity.MediaOutputWindow$Companion$dismissCallback$1$activityControllerListener$1] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            final MediaOutputWindow$Companion$dismissCallback$1$systemReceiver$1 mediaOutputWindow$Companion$dismissCallback$1$systemReceiver$1 = new MediaOutputWindow$Companion$dismissCallback$1$systemReceiver$1(producerScope);
            final ?? r3 = new ActivityManager.SemActivityControllerListener() { // from class: com.android.systemui.media.mediaoutput.activity.MediaOutputWindow$Companion$dismissCallback$1$activityControllerListener$1
                public final List allowList = Arrays.asList("com.samsung.android.easysetup", "com.samsung.android.mtp", "com.google.android.gms");

                public final boolean onActivityResuming(String str) {
                    return true;
                }

                public final boolean onActivityStarting(Intent intent, String str) {
                    MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("onActivityStarting() - pkg = ", str, "MediaOutputWindow");
                    List list = this.allowList;
                    if (list.contains(str)) {
                        list = null;
                    }
                    if (list == null) {
                        return true;
                    }
                    ProducerScope producerScope2 = ProducerScope.this;
                    BuildersKt.launch$default(producerScope2, null, null, new MediaOutputWindow$Companion$dismissCallback$1$activityControllerListener$1$onActivityStarting$2$1(producerScope2, null), 3);
                    return true;
                }

                public final boolean onAppCrashed(String str, int i2, String str2, String str3, long j, String str4) {
                    return true;
                }

                public final int onAppEarlyNotResponding(String str, int i2, String str2) {
                    return 0;
                }

                public final int onAppNotResponding(String str, int i2, String str2) {
                    return 0;
                }

                public final int onSystemNotResponding(String str) {
                    return 0;
                }
            };
            Context context = this.$this_dismissCallback;
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(PopupUIUtil.ACTION_CLOSE_SYSTEM_DIALOGS);
            intentFilter.addAction("android.intent.action.SCREEN_OFF");
            intentFilter.addAction("android.intent.action.LOCALE_CHANGED");
            intentFilter.addAction("com.samsung.systemui.statusbar.ANIMATING");
            intentFilter.addAction("com.samsung.android.mdx.quickboard.PHONE_STATE");
            intentFilter.addAction("com.samsung.android.launcher.TASKBAR_PERFORMED");
            Unit unit = Unit.INSTANCE;
            context.registerReceiver(mediaOutputWindow$Companion$dismissCallback$1$systemReceiver$1, intentFilter, 2);
            final ActivityManager activityManager = (ActivityManager) this.$this_dismissCallback.getSystemService(ActivityManager.class);
            if (activityManager != 0) {
                activityManager.semRegisterActivityControllerListener(r3);
            }
            final Context context2 = this.$this_dismissCallback;
            Function0 function0 = new Function0() { // from class: com.android.systemui.media.mediaoutput.activity.MediaOutputWindow$Companion$dismissCallback$1$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Context context3 = context2;
                    ActivityManager activityManager2 = activityManager;
                    Log.d("MediaOutputWindow", "unregister()");
                    context3.unregisterReceiver(mediaOutputWindow$Companion$dismissCallback$1$systemReceiver$1);
                    if (activityManager2 != null) {
                        activityManager2.semUnregisterActivityControllerListener(r3);
                    }
                    return Unit.INSTANCE;
                }
            };
            this.label = 1;
            if (ProduceKt.awaitClose(producerScope, function0, this) == coroutineSingletons) {
                return coroutineSingletons;
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
