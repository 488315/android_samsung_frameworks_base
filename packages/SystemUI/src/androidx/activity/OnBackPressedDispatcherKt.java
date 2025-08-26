package androidx.activity;

import kotlin.jvm.functions.Function1;

/* loaded from: classes.dex */
public abstract class OnBackPressedDispatcherKt {
    public static void addCallback$default(OnBackPressedDispatcher onBackPressedDispatcher, ComponentDialog componentDialog, final Function1 function1) {
        final boolean z = true;
        onBackPressedDispatcher.addCallback(componentDialog, new OnBackPressedCallback(z) { // from class: androidx.activity.OnBackPressedDispatcherKt$addCallback$callback$1
            @Override // androidx.activity.OnBackPressedCallback
            public final void handleOnBackPressed() {
                function1.mo781invoke(this);
            }
        });
    }
}
