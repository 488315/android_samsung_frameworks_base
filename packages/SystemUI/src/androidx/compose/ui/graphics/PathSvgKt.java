package androidx.compose.ui.graphics;

import androidx.compose.ui.graphics.vector.PathParser;
import androidx.compose.ui.graphics.vector.PathParserKt;
import java.util.ArrayList;

/* loaded from: classes.dex */
public abstract class PathSvgKt {
    public static final void addSvg(AndroidPath androidPath, String str) {
        PathParser pathParser = new PathParser();
        pathParser.parsePathString(str);
        ArrayList arrayList = pathParser.nodes;
        if (arrayList != null) {
            PathParserKt.toPath(arrayList, androidPath);
        } else {
            AndroidPath_androidKt.Path();
        }
    }
}
