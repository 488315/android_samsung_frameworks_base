package androidx.compose.ui.layout;

import java.util.Map;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public interface MeasureScope extends IntrinsicMeasureScope {
    MeasureResult layout(int i, int i2, Map map, Function1 function1);

    default MeasureResult layout$1(int i, int i2, Map map, Function1 function1) {
        return layout(i, i2, map, function1);
    }
}
