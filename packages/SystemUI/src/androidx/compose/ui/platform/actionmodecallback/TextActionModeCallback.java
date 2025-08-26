package androidx.compose.ui.platform.actionmodecallback;

import android.R;
import android.view.Menu;
import androidx.compose.ui.geometry.Rect;
import androidx.compose.ui.platform.actionmodecallback.MenuItemOption;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;

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
    public TextActionModeCallback(Function0 function0, Rect rect, Function0 function02, Function0 function03, Function0 function04, Function0 function05, Function0 function06, int i, DefaultConstructorMarker defaultConstructorMarker) {
        function0 = (i & 1) != 0 ? null : function0;
        if ((i & 2) != 0) {
            Rect.Companion.getClass();
            rect = Rect.Zero;
        }
        this(function0, rect, (i & 4) != 0 ? null : function02, (i & 8) != 0 ? null : function03, (i & 16) != 0 ? null : function04, (i & 32) != 0 ? null : function05, (i & 64) != 0 ? null : function06);
    }
}
