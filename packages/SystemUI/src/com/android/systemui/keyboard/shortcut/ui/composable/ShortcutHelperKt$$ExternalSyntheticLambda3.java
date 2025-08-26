package com.android.systemui.keyboard.shortcut.ui.composable;

import androidx.compose.ui.semantics.Role;
import androidx.compose.ui.semantics.SemanticsPropertiesKt;
import androidx.compose.ui.semantics.SemanticsPropertyReceiver;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* loaded from: classes2.dex */
public final /* synthetic */ class ShortcutHelperKt$$ExternalSyntheticLambda3 implements Function1 {
    public final /* synthetic */ int $r8$classId;

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                break;
            case 1:
                SemanticsPropertiesKt.hideFromAccessibility((SemanticsPropertyReceiver) obj);
                break;
            case 2:
                SemanticsPropertiesKt.hideFromAccessibility((SemanticsPropertyReceiver) obj);
                break;
            case 3:
                SemanticsPropertiesKt.setTraversalGroup((SemanticsPropertyReceiver) obj);
                break;
            case 4:
                SemanticsPropertiesKt.setTraversalGroup((SemanticsPropertyReceiver) obj);
                break;
            case 5:
                break;
            case 6:
                ((Boolean) obj).booleanValue();
                break;
            case 7:
                Role.Companion.getClass();
                SemanticsPropertiesKt.m719setRolekuIjeqM((SemanticsPropertyReceiver) obj, 0);
                break;
            case 8:
                SemanticsPropertiesKt.hideFromAccessibility((SemanticsPropertyReceiver) obj);
                break;
            case 9:
                Role.Companion.getClass();
                SemanticsPropertiesKt.m719setRolekuIjeqM((SemanticsPropertyReceiver) obj, Role.Tab);
                break;
            case 10:
                SemanticsPropertiesKt.hideFromAccessibility((SemanticsPropertyReceiver) obj);
                break;
            case 11:
                SemanticsPropertiesKt.hideFromAccessibility((SemanticsPropertyReceiver) obj);
                break;
            case 12:
                SemanticsPropertiesKt.hideFromAccessibility((SemanticsPropertyReceiver) obj);
                break;
            default:
                SemanticsPropertiesKt.hideFromAccessibility((SemanticsPropertyReceiver) obj);
                break;
        }
        return Unit.INSTANCE;
    }
}
