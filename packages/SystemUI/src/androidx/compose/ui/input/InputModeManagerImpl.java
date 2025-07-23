package androidx.compose.ui.input;

import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.SnapshotStateKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class InputModeManagerImpl implements InputModeManager {
    public final MutableState inputMode$delegate;

    public /* synthetic */ InputModeManagerImpl(int i, Function1 function1, DefaultConstructorMarker defaultConstructorMarker) {
        this(i, function1);
    }

    private InputModeManagerImpl(int i, Function1 function1) {
        this.inputMode$delegate = SnapshotStateKt.mutableStateOf$default(InputMode.m572boximpl(i));
    }
}
