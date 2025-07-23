package androidx.compose.ui.draw;

import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.ColorFilter;
import androidx.compose.ui.graphics.painter.Painter;
import androidx.compose.ui.layout.ContentScale;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class PainterModifierKt {
    public static Modifier paint$default(Modifier modifier, Painter painter, Alignment alignment, ContentScale contentScale, float f, ColorFilter colorFilter, int i) {
        if ((i & 4) != 0) {
            Alignment.Companion.getClass();
            alignment = Alignment.Companion.Center;
        }
        Alignment alignment2 = alignment;
        if ((i & 8) != 0) {
            ContentScale.Companion.getClass();
            contentScale = ContentScale.Companion.Inside;
        }
        ContentScale contentScale2 = contentScale;
        if ((i & 16) != 0) {
            f = 1.0f;
        }
        float f2 = f;
        if ((i & 32) != 0) {
            colorFilter = null;
        }
        return modifier.then(new PainterElement(painter, true, alignment2, contentScale2, f2, colorFilter));
    }
}
