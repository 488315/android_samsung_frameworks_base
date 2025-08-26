package com.android.systemui.compose.modifiers;

import androidx.compose.ui.Modifier;
import androidx.compose.ui.platform.TestTagKt;
import androidx.compose.ui.semantics.SemanticsModifierKt;

/* loaded from: classes2.dex */
public abstract class SysuiTestTagKt {
    public static final Modifier TestTagAsResourceIdModifier = SemanticsModifierKt.semantics(Modifier.Companion, false, new SysuiTestTagKt$$ExternalSyntheticLambda0());

    public static final Modifier sysuiResTag(Modifier modifier, String str) {
        return TestTagKt.testTag(modifier.then(TestTagAsResourceIdModifier), "com.android.systemui:id/" + str);
    }
}
