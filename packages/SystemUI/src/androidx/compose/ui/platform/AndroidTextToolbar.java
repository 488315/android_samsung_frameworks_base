package androidx.compose.ui.platform;

import android.view.ActionMode;
import android.view.View;
import androidx.compose.ui.platform.actionmodecallback.TextActionModeCallback;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class AndroidTextToolbar implements TextToolbar {
    public ActionMode actionMode;
    public final View view;
    public final TextActionModeCallback textActionModeCallback = new TextActionModeCallback(new Function0() { // from class: androidx.compose.ui.platform.AndroidTextToolbar$textActionModeCallback$1
        {
            super(0);
        }

        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            AndroidTextToolbar.this.actionMode = null;
            return Unit.INSTANCE;
        }
    }, null, null, null, null, null, null, 126, null);
    public TextToolbarStatus status = TextToolbarStatus.Hidden;

    public AndroidTextToolbar(View view) {
        this.view = view;
    }
}
