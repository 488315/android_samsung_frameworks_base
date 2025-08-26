package androidx.window.embedding;

import com.samsung.android.knox.ex.peripheral.PeripheralBarcodeConstants;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
public final class EmbeddingConfiguration$DimAreaBehavior {
    public static final EmbeddingConfiguration$DimAreaBehavior ON_ACTIVITY_STACK;
    public static final EmbeddingConfiguration$DimAreaBehavior UNDEFINED = null;
    public final int value;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
        new EmbeddingConfiguration$DimAreaBehavior(0);
        ON_ACTIVITY_STACK = new EmbeddingConfiguration$DimAreaBehavior(1);
        new EmbeddingConfiguration$DimAreaBehavior(2);
    }

    private EmbeddingConfiguration$DimAreaBehavior(int i) {
        this.value = i;
    }

    public final String toString() {
        int i = this.value;
        return "DimAreaBehavior=".concat(i != 0 ? i != 1 ? i != 2 ? "UNKNOWN" : "ON_TASK" : "ON_ACTIVITY_STACK" : PeripheralBarcodeConstants.Symbology.UNDEFINED);
    }
}
