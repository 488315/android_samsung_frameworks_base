package androidx.compose.ui.platform;

import androidx.compose.ui.Modifier;

/* loaded from: classes.dex */
public abstract class TestTagKt {
    public static final Modifier testTag(Modifier modifier, String str) {
        return modifier.then(new TestTagElement(str));
    }
}
