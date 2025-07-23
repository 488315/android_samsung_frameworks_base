package androidx.compose.ui.text.platform;

import androidx.compose.ui.graphics.Brush;
import androidx.compose.ui.graphics.Canvas;
import androidx.compose.ui.graphics.Shadow;
import androidx.compose.ui.graphics.drawscope.DrawStyle;
import androidx.compose.ui.text.AndroidParagraph;
import androidx.compose.ui.text.MultiParagraph;
import androidx.compose.ui.text.ParagraphInfo;
import androidx.compose.ui.text.style.TextDecoration;
import java.util.ArrayList;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class AndroidMultiParagraphDraw_androidKt {
    /* renamed from: drawParagraphs-7AXcY_I, reason: not valid java name */
    public static final void m780drawParagraphs7AXcY_I(MultiParagraph multiParagraph, Canvas canvas, Brush brush, float f, Shadow shadow, TextDecoration textDecoration, DrawStyle drawStyle, int i) {
        ArrayList arrayList = (ArrayList) multiParagraph.paragraphInfoList;
        int size = arrayList.size();
        for (int i2 = 0; i2 < size; i2++) {
            ParagraphInfo paragraphInfo = (ParagraphInfo) arrayList.get(i2);
            ((AndroidParagraph) paragraphInfo.paragraph).m728painthn5TExg(canvas, brush, f, shadow, textDecoration, drawStyle, i);
            canvas.translate(0.0f, ((AndroidParagraph) paragraphInfo.paragraph).getHeight());
        }
    }
}
