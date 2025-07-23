package androidx.compose.ui.platform;

import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.ui.input.pointer.PointerKeyboardModifiers;
import androidx.compose.ui.unit.IntSize;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class WindowInfoImpl implements WindowInfo {
    public static final Companion Companion = new Companion(null);
    public static final MutableState GlobalKeyboardModifiers = SnapshotStateKt.mutableStateOf$default(PointerKeyboardModifiers.m596boximpl(0));
    public final MutableState isWindowFocused$delegate;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public WindowInfoImpl() {
        IntSize.Companion.getClass();
        SnapshotStateKt.mutableStateOf$default(IntSize.m859boximpl(0L));
        this.isWindowFocused$delegate = SnapshotStateKt.mutableStateOf$default(Boolean.FALSE);
    }

    @Override // androidx.compose.ui.platform.WindowInfo
    public final boolean isWindowFocused() {
        return ((Boolean) ((SnapshotMutableStateImpl) this.isWindowFocused$delegate).getValue()).booleanValue();
    }
}
