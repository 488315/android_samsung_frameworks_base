package androidx.compose.foundation.text.selection;

import androidx.compose.ui.input.pointer.PointerInputChange;
import androidx.compose.ui.platform.ViewConfiguration;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class ClicksCounter {
    public int clicks;
    public PointerInputChange prevClick;
    public final ViewConfiguration viewConfiguration;

    public ClicksCounter(ViewConfiguration viewConfiguration) {
        this.viewConfiguration = viewConfiguration;
    }
}
