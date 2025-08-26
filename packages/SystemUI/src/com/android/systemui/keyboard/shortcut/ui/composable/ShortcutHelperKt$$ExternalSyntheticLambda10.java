package com.android.systemui.keyboard.shortcut.ui.composable;

import com.android.systemui.keyboard.shortcut.shared.model.Shortcut;
import com.android.systemui.keyboard.shortcut.shared.model.ShortcutCommand;
import com.android.systemui.keyboard.shortcut.shared.model.ShortcutCustomizationRequestInfo;
import com.android.systemui.keyboard.shortcut.ui.model.ShortcutCategoryUi;
import java.util.Iterator;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;

/* loaded from: classes2.dex */
public final /* synthetic */ class ShortcutHelperKt$$ExternalSyntheticLambda10 implements Function0 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Function1 f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ ShortcutHelperKt$$ExternalSyntheticLambda10(Function1 function1, Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = function1;
        this.f$1 = obj;
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        Object next;
        Object obj;
        Object next2;
        switch (this.$r8$classId) {
            case 0:
                Shortcut shortcut = (Shortcut) this.f$1;
                String str = shortcut.label;
                Iterator it = shortcut.commands.iterator();
                while (true) {
                    if (it.hasNext()) {
                        next = it.next();
                        if (!((ShortcutCommand) next).isCustom) {
                        }
                    } else {
                        next = null;
                    }
                }
                this.f$0.mo781invoke(new ShortcutCustomizationRequestInfo.SingleShortcutCustomization.Add(str, null, null, (ShortcutCommand) next, 6, null));
                break;
            case 1:
                Shortcut shortcut2 = (Shortcut) this.f$1;
                String str2 = shortcut2.label;
                Iterator it2 = shortcut2.commands.iterator();
                while (true) {
                    obj = null;
                    if (it2.hasNext()) {
                        next2 = it2.next();
                        if (!((ShortcutCommand) next2).isCustom) {
                        }
                    } else {
                        next2 = null;
                    }
                }
                ShortcutCommand shortcutCommand = (ShortcutCommand) next2;
                Iterator it3 = shortcut2.commands.iterator();
                while (true) {
                    if (it3.hasNext()) {
                        Object next3 = it3.next();
                        if (((ShortcutCommand) next3).isCustom) {
                            obj = next3;
                        }
                    }
                }
                this.f$0.mo781invoke(new ShortcutCustomizationRequestInfo.SingleShortcutCustomization.Delete(str2, null, null, shortcutCommand, (ShortcutCommand) obj, 6, null));
                break;
            default:
                this.f$0.mo781invoke((ShortcutCategoryUi) this.f$1);
                break;
        }
        return Unit.INSTANCE;
    }
}
