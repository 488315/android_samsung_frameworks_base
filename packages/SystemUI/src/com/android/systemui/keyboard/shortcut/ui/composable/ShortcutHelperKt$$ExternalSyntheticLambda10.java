package com.android.systemui.keyboard.shortcut.ui.composable;

import com.android.systemui.keyboard.shortcut.shared.model.Shortcut;
import com.android.systemui.keyboard.shortcut.shared.model.ShortcutCommand;
import com.android.systemui.keyboard.shortcut.shared.model.ShortcutCustomizationRequestInfo;
import com.android.systemui.keyboard.shortcut.ui.model.ShortcutCategoryUi;
import java.util.Iterator;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        Object obj;
        Object obj2;
        Object obj3;
        switch (this.$r8$classId) {
            case 0:
                Shortcut shortcut = (Shortcut) this.f$1;
                String str = shortcut.label;
                Iterator it = shortcut.commands.iterator();
                while (true) {
                    if (it.hasNext()) {
                        obj = it.next();
                        if (!((ShortcutCommand) obj).isCustom) {
                        }
                    } else {
                        obj = null;
                    }
                }
                this.f$0.mo779invoke(new ShortcutCustomizationRequestInfo.SingleShortcutCustomization.Add(str, null, null, (ShortcutCommand) obj, 6, null));
                break;
            case 1:
                Shortcut shortcut2 = (Shortcut) this.f$1;
                String str2 = shortcut2.label;
                Iterator it2 = shortcut2.commands.iterator();
                while (true) {
                    obj2 = null;
                    if (it2.hasNext()) {
                        obj3 = it2.next();
                        if (!((ShortcutCommand) obj3).isCustom) {
                        }
                    } else {
                        obj3 = null;
                    }
                }
                ShortcutCommand shortcutCommand = (ShortcutCommand) obj3;
                Iterator it3 = shortcut2.commands.iterator();
                while (true) {
                    if (it3.hasNext()) {
                        Object next = it3.next();
                        if (((ShortcutCommand) next).isCustom) {
                            obj2 = next;
                        }
                    }
                }
                this.f$0.mo779invoke(new ShortcutCustomizationRequestInfo.SingleShortcutCustomization.Delete(str2, null, null, shortcutCommand, (ShortcutCommand) obj2, 6, null));
                break;
            default:
                this.f$0.mo779invoke((ShortcutCategoryUi) this.f$1);
                break;
        }
        return Unit.INSTANCE;
    }
}
