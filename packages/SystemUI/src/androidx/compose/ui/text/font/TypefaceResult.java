package androidx.compose.ui.text.font;

import androidx.compose.runtime.State;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public interface TypefaceResult extends State<Object> {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Async implements TypefaceResult, State<Object> {
        public final AsyncFontListLoader current;

        public Async(AsyncFontListLoader asyncFontListLoader) {
            this.current = asyncFontListLoader;
        }

        @Override // androidx.compose.ui.text.font.TypefaceResult
        public final boolean getCacheable() {
            return this.current.cacheable;
        }

        @Override // androidx.compose.runtime.State
        public final Object getValue() {
            return this.current.getValue();
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Immutable implements TypefaceResult {
        public final boolean cacheable;
        public final Object value;

        public Immutable(Object obj, boolean z) {
            this.value = obj;
            this.cacheable = z;
        }

        @Override // androidx.compose.ui.text.font.TypefaceResult
        public final boolean getCacheable() {
            return this.cacheable;
        }

        @Override // androidx.compose.runtime.State
        public final Object getValue() {
            return this.value;
        }

        public /* synthetic */ Immutable(Object obj, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(obj, (i & 2) != 0 ? true : z);
        }
    }

    boolean getCacheable();
}
