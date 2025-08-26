package androidx.compose.ui.platform;

import androidx.compose.ui.input.pointer.PointerIcon;
import androidx.compose.ui.input.pointer.PointerIconService;

/* loaded from: classes.dex */
public final class AndroidComposeView$pointerIconService$1 implements PointerIconService {
    public PointerIcon currentStylusHoverIcon;
    public final /* synthetic */ AndroidComposeView this$0;

    public AndroidComposeView$pointerIconService$1(AndroidComposeView androidComposeView) {
        this.this$0 = androidComposeView;
        PointerIcon.Companion.getClass();
        PointerIcon.Companion companion = PointerIcon.Companion.$$INSTANCE;
    }

    public final void setIcon(PointerIcon pointerIcon) {
        if (pointerIcon == null) {
            PointerIcon.Companion.getClass();
            pointerIcon = PointerIcon.Companion.Default;
        }
        AndroidComposeViewVerificationHelperMethodsN.INSTANCE.setPointerIcon(this.this$0, pointerIcon);
    }
}
