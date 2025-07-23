package androidx.compose.ui.platform.actionmodecallback;

import android.R;
import android.view.Menu;
import androidx.compose.ui.geometry.Rect;
import androidx.compose.ui.platform.actionmodecallback.MenuItemOption;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class TextActionModeCallback {
    public final Function0 onActionModeDestroy;
    public Function0 onAutofillRequested;
    public Function0 onCopyRequested;
    public Function0 onCutRequested;
    public Function0 onPasteRequested;
    public Function0 onSelectAllRequested;
    public Rect rect;

    public TextActionModeCallback() {
        this(null, null, null, null, null, null, null, 127, null);
    }

    public static void addMenuItem$ui_release(Menu menu, MenuItemOption menuItemOption) {
        int i;
        int id = menuItemOption.getId();
        int order = menuItemOption.getOrder();
        int i2 = MenuItemOption.WhenMappings.$EnumSwitchMapping$0[menuItemOption.ordinal()];
        if (i2 == 1) {
            i = R.string.copy;
        } else if (i2 == 2) {
            i = R.string.paste;
        } else if (i2 == 3) {
            i = R.string.cut;
        } else if (i2 == 4) {
            i = R.string.selectAll;
        } else {
            if (i2 != 5) {
                throw new NoWhenBranchMatchedException();
            }
            i = R.string.autofill;
        }
        menu.add(0, id, order, i).setShowAsAction(1);
    }

    public static void addOrRemoveMenuItem(Menu menu, MenuItemOption menuItemOption, Function0 function0) {
        if (function0 != null && menu.findItem(menuItemOption.getId()) == null) {
            addMenuItem$ui_release(menu, menuItemOption);
        } else {
            if (function0 != null || menu.findItem(menuItemOption.getId()) == null) {
                return;
            }
            menu.removeItem(menuItemOption.getId());
        }
    }

    public TextActionModeCallback(Function0 function0, Rect rect, Function0 function02, Function0 function03, Function0 function04, Function0 function05, Function0 function06) {
        this.onActionModeDestroy = function0;
        this.rect = rect;
        this.onCopyRequested = function02;
        this.onPasteRequested = function03;
        this.onCutRequested = function04;
        this.onSelectAllRequested = function05;
        this.onAutofillRequested = function06;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public TextActionModeCallback(kotlin.jvm.functions.Function0 r2, androidx.compose.ui.geometry.Rect r3, kotlin.jvm.functions.Function0 r4, kotlin.jvm.functions.Function0 r5, kotlin.jvm.functions.Function0 r6, kotlin.jvm.functions.Function0 r7, kotlin.jvm.functions.Function0 r8, int r9, kotlin.jvm.internal.DefaultConstructorMarker r10) {
        /*
            r1 = this;
            r10 = r9 & 1
            r0 = 0
            if (r10 == 0) goto L6
            r2 = r0
        L6:
            r10 = r9 & 2
            if (r10 == 0) goto L11
            androidx.compose.ui.geometry.Rect$Companion r3 = androidx.compose.ui.geometry.Rect.Companion
            r3.getClass()
            androidx.compose.ui.geometry.Rect r3 = androidx.compose.ui.geometry.Rect.Zero
        L11:
            r10 = r9 & 4
            if (r10 == 0) goto L16
            r4 = r0
        L16:
            r10 = r9 & 8
            if (r10 == 0) goto L1b
            r5 = r0
        L1b:
            r10 = r9 & 16
            if (r10 == 0) goto L20
            r6 = r0
        L20:
            r10 = r9 & 32
            if (r10 == 0) goto L25
            r7 = r0
        L25:
            r9 = r9 & 64
            if (r9 == 0) goto L2a
            r8 = r0
        L2a:
            r1.<init>(r2, r3, r4, r5, r6, r7, r8)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.platform.actionmodecallback.TextActionModeCallback.<init>(kotlin.jvm.functions.Function0, androidx.compose.ui.geometry.Rect, kotlin.jvm.functions.Function0, kotlin.jvm.functions.Function0, kotlin.jvm.functions.Function0, kotlin.jvm.functions.Function0, kotlin.jvm.functions.Function0, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }
}
