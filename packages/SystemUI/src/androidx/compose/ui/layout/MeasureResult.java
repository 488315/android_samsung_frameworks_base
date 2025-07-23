package androidx.compose.ui.layout;

import java.util.Map;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public interface MeasureResult {
    Map getAlignmentLines();

    int getHeight();

    default Function1 getRulers() {
        return null;
    }

    int getWidth();

    void placeChildren();
}
