package androidx.compose.ui.layout;

import java.util.Map;
import kotlin.jvm.functions.Function1;

/* loaded from: classes.dex */
public interface MeasureScope extends IntrinsicMeasureScope {
    MeasureResult layout(int i, int i2, Map map, Function1 function1);

    default MeasureResult layout$1(int i, int i2, Map map, Function1 function1) {
        return layout(i, i2, map, function1);
    }
}
