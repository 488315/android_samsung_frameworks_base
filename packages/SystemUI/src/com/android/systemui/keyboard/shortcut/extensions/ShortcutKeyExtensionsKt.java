package com.android.systemui.keyboard.shortcut.extensions;

import android.content.Context;
import com.android.systemui.R;
import com.android.systemui.keyboard.shortcut.data.repository.ShortcutHelperKeys;
import com.android.systemui.keyboard.shortcut.shared.model.ShortcutKey;
import java.util.Iterator;
import java.util.Map;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public abstract class ShortcutKeyExtensionsKt {
    public static final String toContentDescription(ShortcutKey shortcutKey, Context context) {
        Object obj;
        Function1 function1;
        String string = context.getString(R.string.shortcut_helper_key_combinations_forward_slash);
        if (shortcutKey instanceof ShortcutKey.Text) {
            ShortcutKey.Text text = (ShortcutKey.Text) shortcutKey;
            return Intrinsics.areEqual(text.value, "/") ? string : text.value;
        }
        if (shortcutKey instanceof ShortcutKey.Icon.ResIdIcon) {
            ShortcutKey.Icon.ResIdIcon resIdIcon = (ShortcutKey.Icon.ResIdIcon) shortcutKey;
            int i = resIdIcon.drawableResId;
            ShortcutHelperKeys shortcutHelperKeys = ShortcutHelperKeys.INSTANCE;
            shortcutHelperKeys.getClass();
            if (i == ShortcutHelperKeys.metaModifierIconResId) {
                shortcutHelperKeys.getClass();
                function1 = (Function1) ShortcutHelperKeys.modifierLabels.get(65536);
            } else {
                shortcutHelperKeys.getClass();
                Iterator it = ShortcutHelperKeys.keyIcons.entrySet().iterator();
                while (true) {
                    if (!it.hasNext()) {
                        obj = null;
                        break;
                    }
                    obj = it.next();
                    if (((Number) ((Map.Entry) obj).getValue()).intValue() == resIdIcon.drawableResId) {
                        break;
                    }
                }
                Map.Entry entry = (Map.Entry) obj;
                Integer num = entry != null ? (Integer) entry.getKey() : null;
                ShortcutHelperKeys.INSTANCE.getClass();
                function1 = (Function1) ShortcutHelperKeys.specialKeyLabels.get(num);
            }
            if (function1 != null) {
                return (String) function1.mo779invoke(context);
            }
        } else if (!(shortcutKey instanceof ShortcutKey.Icon.DrawableIcon)) {
            throw new NoWhenBranchMatchedException();
        }
        return null;
    }
}
