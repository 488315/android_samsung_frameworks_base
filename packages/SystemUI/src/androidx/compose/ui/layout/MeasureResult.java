package androidx.compose.ui.layout;

import java.util.Map;
import kotlin.jvm.functions.Function1;

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
