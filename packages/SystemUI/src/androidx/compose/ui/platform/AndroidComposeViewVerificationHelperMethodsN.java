package androidx.compose.ui.platform;

import android.content.Context;
import android.view.View;
import androidx.compose.ui.input.pointer.AndroidPointerIcon;
import androidx.compose.ui.input.pointer.AndroidPointerIconType;
import androidx.compose.ui.input.pointer.PointerIcon;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class AndroidComposeViewVerificationHelperMethodsN {
    public static final AndroidComposeViewVerificationHelperMethodsN INSTANCE = new AndroidComposeViewVerificationHelperMethodsN();

    private AndroidComposeViewVerificationHelperMethodsN() {
    }

    public final void setPointerIcon(View view, PointerIcon pointerIcon) {
        Context context = view.getContext();
        android.view.PointerIcon systemIcon = pointerIcon instanceof AndroidPointerIcon ? ((AndroidPointerIcon) pointerIcon).pointerIcon : pointerIcon instanceof AndroidPointerIconType ? android.view.PointerIcon.getSystemIcon(context, ((AndroidPointerIconType) pointerIcon).type) : android.view.PointerIcon.getSystemIcon(context, 1000);
        if (Intrinsics.areEqual(view.getPointerIcon(), systemIcon)) {
            return;
        }
        view.setPointerIcon(systemIcon);
    }
}
