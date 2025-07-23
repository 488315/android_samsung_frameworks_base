package androidx.activity;

import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class OnBackPressedDispatcherKt {
    public static void addCallback$default(OnBackPressedDispatcher onBackPressedDispatcher, ComponentDialog componentDialog, final Function1 function1) {
        final boolean z = true;
        onBackPressedDispatcher.addCallback(componentDialog, new OnBackPressedCallback(z) { // from class: androidx.activity.OnBackPressedDispatcherKt$addCallback$callback$1
            @Override // androidx.activity.OnBackPressedCallback
            public final void handleOnBackPressed() {
                function1.mo779invoke(this);
            }
        });
    }
}
