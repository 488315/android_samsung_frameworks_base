package androidx.compose.ui.input;

import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.SnapshotStateKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
public final class InputModeManagerImpl implements InputModeManager {
    public final MutableState inputMode$delegate;

    public /* synthetic */ InputModeManagerImpl(int i, Function1 function1, DefaultConstructorMarker defaultConstructorMarker) {
        this(i, function1);
    }

    private InputModeManagerImpl(int i, Function1 function1) {
        this.inputMode$delegate = SnapshotStateKt.mutableStateOf$default(InputMode.m574boximpl(i));
    }
}
