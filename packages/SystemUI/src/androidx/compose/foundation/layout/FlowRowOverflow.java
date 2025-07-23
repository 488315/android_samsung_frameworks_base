package androidx.compose.foundation.layout;

import androidx.compose.foundation.layout.FlowLayoutOverflow;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class FlowRowOverflow extends FlowLayoutOverflow {
    public static final FlowRowOverflow Clip;
    public static final Companion Companion = new Companion(null);

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new FlowRowOverflow(FlowLayoutOverflow.OverflowType.Visible, 0, 0, null, null, 30, null);
        Clip = new FlowRowOverflow(FlowLayoutOverflow.OverflowType.Clip, 0, 0, null, null, 30, null);
    }

    public /* synthetic */ FlowRowOverflow(FlowLayoutOverflow.OverflowType overflowType, int i, int i2, Function1 function1, Function1 function12, DefaultConstructorMarker defaultConstructorMarker) {
        this(overflowType, i, i2, function1, function12);
    }

    public /* synthetic */ FlowRowOverflow(FlowLayoutOverflow.OverflowType overflowType, int i, int i2, Function1 function1, Function1 function12, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(overflowType, (i3 & 2) != 0 ? 0 : i, (i3 & 4) != 0 ? 0 : i2, (i3 & 8) != 0 ? null : function1, (i3 & 16) != 0 ? null : function12);
    }

    private FlowRowOverflow(FlowLayoutOverflow.OverflowType overflowType, int i, int i2, Function1 function1, Function1 function12) {
        super(overflowType, i, i2, function1, function12, null);
    }
}
