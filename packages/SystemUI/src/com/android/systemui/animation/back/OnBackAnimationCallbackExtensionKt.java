package com.android.systemui.animation.back;

import android.util.DisplayMetrics;
import android.window.BackEvent;
import android.window.OnBackAnimationCallback;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public abstract class OnBackAnimationCallbackExtensionKt {
    /* JADX WARN: Type inference failed for: r7v0, types: [com.android.systemui.animation.back.OnBackAnimationCallbackExtensionKt$onBackAnimationCallbackFrom$4] */
    public static OnBackAnimationCallbackExtensionKt$onBackAnimationCallbackFrom$4 onBackAnimationCallbackFrom$default(final BackAnimationSpec backAnimationSpec, final DisplayMetrics displayMetrics, final Function1 function1, final Function0 function0) {
        final OnBackAnimationCallbackExtensionKt$onBackAnimationCallbackFrom$1 onBackAnimationCallbackExtensionKt$onBackAnimationCallbackFrom$1 = new Function1() { // from class: com.android.systemui.animation.back.OnBackAnimationCallbackExtensionKt$onBackAnimationCallbackFrom$1
            @Override // kotlin.jvm.functions.Function1
            public final /* bridge */ /* synthetic */ Object invoke(Object obj) {
                return Unit.INSTANCE;
            }
        };
        final OnBackAnimationCallbackExtensionKt$onBackAnimationCallbackFrom$3 onBackAnimationCallbackExtensionKt$onBackAnimationCallbackFrom$3 = new Function0() { // from class: com.android.systemui.animation.back.OnBackAnimationCallbackExtensionKt$onBackAnimationCallbackFrom$3
            @Override // kotlin.jvm.functions.Function0
            public final /* bridge */ /* synthetic */ Object invoke() {
                return Unit.INSTANCE;
            }
        };
        return new OnBackAnimationCallback() { // from class: com.android.systemui.animation.back.OnBackAnimationCallbackExtensionKt$onBackAnimationCallbackFrom$4
            public float initialY;
            public final BackTransformation lastTransformation = new BackTransformation(0.0f, 0.0f, 0.0f, null, 15, null);

            @Override // android.window.OnBackAnimationCallback
            public final void onBackCancelled() {
                onBackAnimationCallbackExtensionKt$onBackAnimationCallbackFrom$3.invoke();
            }

            @Override // android.window.OnBackInvokedCallback
            public final void onBackInvoked() {
                function0.invoke();
            }

            @Override // android.window.OnBackAnimationCallback
            public final void onBackProgressed(BackEvent backEvent) {
                backAnimationSpec.getBackTransformation(backEvent, (backEvent.getTouchY() - this.initialY) / displayMetrics.heightPixels, this.lastTransformation);
                function1.invoke(this.lastTransformation);
            }

            @Override // android.window.OnBackAnimationCallback
            public final void onBackStarted(BackEvent backEvent) {
                this.initialY = backEvent.getTouchY();
                Function1.this.invoke(backEvent);
            }
        };
    }
}
