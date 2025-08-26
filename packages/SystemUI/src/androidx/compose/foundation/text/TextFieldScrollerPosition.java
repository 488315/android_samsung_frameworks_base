package androidx.compose.foundation.text;

import androidx.compose.foundation.gestures.Orientation;
import androidx.compose.runtime.MutableFloatState;
import androidx.compose.runtime.MutableIntState;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.PrimitiveSnapshotStateKt;
import androidx.compose.runtime.SnapshotIntStateKt;
import androidx.compose.runtime.SnapshotMutableFloatStateImpl;
import androidx.compose.runtime.SnapshotMutableIntStateImpl;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.saveable.ListSaverKt;
import androidx.compose.runtime.saveable.SaverKt$Saver$1;
import androidx.compose.ui.geometry.Rect;
import androidx.compose.ui.text.TextRange;
import java.util.Arrays;
import java.util.List;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.ranges.RangesKt___RangesKt;

/* loaded from: classes.dex */
public final class TextFieldScrollerPosition {
    public static final Companion Companion = new Companion(null);
    public static final SaverKt$Saver$1 Saver = ListSaverKt.listSaver(new Function2() { // from class: androidx.compose.foundation.text.TextFieldScrollerPosition$Companion$Saver$1
        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            TextFieldScrollerPosition textFieldScrollerPosition = (TextFieldScrollerPosition) obj2;
            return Arrays.asList(Float.valueOf(((SnapshotMutableFloatStateImpl) textFieldScrollerPosition.offset$delegate).getFloatValue()), Boolean.valueOf(((Orientation) ((SnapshotMutableStateImpl) textFieldScrollerPosition.orientation$delegate).getValue()) == Orientation.Vertical));
        }
    }, new Function1() { // from class: androidx.compose.foundation.text.TextFieldScrollerPosition$Companion$Saver$2
        @Override // kotlin.jvm.functions.Function1
        /* renamed from: invoke */
        public final Object mo781invoke(Object obj) {
            List list = (List) obj;
            return new TextFieldScrollerPosition(((Boolean) list.get(1)).booleanValue() ? Orientation.Vertical : Orientation.Horizontal, ((Float) list.get(0)).floatValue());
        }
    });
    public final MutableFloatState maximum$delegate;
    public final MutableFloatState offset$delegate;
    public final MutableState orientation$delegate;
    public Rect previousCursorRect;
    public long previousSelection;
    public final MutableIntState viewportSize$delegate;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public TextFieldScrollerPosition(Orientation orientation, float f) {
        this.offset$delegate = PrimitiveSnapshotStateKt.mutableFloatStateOf(f);
        this.maximum$delegate = PrimitiveSnapshotStateKt.mutableFloatStateOf(0.0f);
        this.viewportSize$delegate = SnapshotIntStateKt.mutableIntStateOf(0);
        Rect.Companion.getClass();
        this.previousCursorRect = Rect.Zero;
        TextRange.Companion.getClass();
        this.previousSelection = TextRange.Zero;
        this.orientation$delegate = SnapshotStateKt.mutableStateOf(orientation, SnapshotStateKt.structuralEqualityPolicy());
    }

    public final void update(Orientation orientation, Rect rect, int i, int i2) {
        float f = i2 - i;
        ((SnapshotMutableFloatStateImpl) this.maximum$delegate).setFloatValue(f);
        Rect rect2 = this.previousCursorRect;
        float f2 = rect2.left;
        float f3 = rect.left;
        MutableFloatState mutableFloatState = this.offset$delegate;
        float f4 = rect.top;
        if (f3 != f2 || f4 != rect2.top) {
            boolean z = orientation == Orientation.Vertical;
            if (z) {
                f3 = f4;
            }
            float f5 = z ? rect.bottom : rect.right;
            SnapshotMutableFloatStateImpl snapshotMutableFloatStateImpl = (SnapshotMutableFloatStateImpl) mutableFloatState;
            float floatValue = snapshotMutableFloatStateImpl.getFloatValue();
            float f6 = i;
            float f7 = floatValue + f6;
            ((SnapshotMutableFloatStateImpl) mutableFloatState).setFloatValue(snapshotMutableFloatStateImpl.getFloatValue() + ((f5 <= f7 && (f3 >= floatValue || f5 - f3 <= f6)) ? (f3 >= floatValue || f5 - f3 > f6) ? 0.0f : f3 - floatValue : f5 - f7));
            this.previousCursorRect = rect;
        }
        ((SnapshotMutableFloatStateImpl) mutableFloatState).setFloatValue(RangesKt___RangesKt.coerceIn(((SnapshotMutableFloatStateImpl) mutableFloatState).getFloatValue(), 0.0f, f));
        ((SnapshotMutableIntStateImpl) this.viewportSize$delegate).setIntValue(i);
    }

    public /* synthetic */ TextFieldScrollerPosition(Orientation orientation, float f, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(orientation, (i & 2) != 0 ? 0.0f : f);
    }

    public TextFieldScrollerPosition() {
        this(Orientation.Vertical, 0.0f, 2, null);
    }
}
