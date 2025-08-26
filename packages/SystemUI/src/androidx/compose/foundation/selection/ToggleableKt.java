package androidx.compose.foundation.selection;

import androidx.compose.foundation.interaction.MutableInteractionSource;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.semantics.Role;
import androidx.compose.ui.state.ToggleableState;
import com.samsung.sesl.compose.foundation.SeslRecoilNodeFactory;
import kotlin.jvm.functions.Function0;

/* loaded from: classes.dex */
public abstract class ToggleableKt {
    /* renamed from: triStateToggleable-O2vRcR0, reason: not valid java name */
    public static final Modifier m183triStateToggleableO2vRcR0(Modifier modifier, ToggleableState toggleableState, MutableInteractionSource mutableInteractionSource, SeslRecoilNodeFactory seslRecoilNodeFactory, boolean z, Role role, Function0 function0) {
        return modifier.then(new TriStateToggleableElement(toggleableState, mutableInteractionSource, seslRecoilNodeFactory, z, role, function0, null));
    }
}
