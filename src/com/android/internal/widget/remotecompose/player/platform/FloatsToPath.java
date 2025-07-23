package com.android.internal.widget.remotecompose.player.platform;

import android.graphics.Path;
import android.graphics.PathMeasure;
import com.android.internal.widget.remotecompose.core.operations.Utils;

/* loaded from: classes6.dex */
public class FloatsToPath {
    public static void genPath(Path path, float[] fArr, float f, float f2) {
        Path path2 = new Path();
        int i = 0;
        while (i < fArr.length) {
            switch (Utils.idFromNan(fArr[i])) {
                case 10:
                    path2.moveTo(fArr[i + 1], fArr[i + 2]);
                    i += 3;
                    continue;
                case 11:
                    path2.lineTo(fArr[i + 3], fArr[i + 4]);
                    i += 5;
                    continue;
                case 12:
                    path2.quadTo(fArr[i + 3], fArr[i + 4], fArr[i + 5], fArr[i + 6]);
                    i += 7;
                    continue;
                case 13:
                    path2.conicTo(fArr[i + 3], fArr[i + 4], fArr[i + 5], fArr[i + 6], fArr[i + 7]);
                    i += 8;
                    continue;
                case 14:
                    path2.cubicTo(fArr[i + 3], fArr[i + 4], fArr[i + 5], fArr[i + 6], fArr[i + 7], fArr[i + 8]);
                    i += 9;
                    continue;
                case 15:
                    path2.close();
                    break;
                case 16:
                    break;
                default:
                    System.err.println(" Odd command " + Utils.idFromNan(fArr[i]));
                    continue;
            }
            i++;
        }
        path.reset();
        if (f <= 0.0f && f2 >= 1.0f) {
            path.addPath(path2);
        } else if (f < f2) {
            PathMeasure pathMeasure = new PathMeasure();
            pathMeasure.setPath(path2, false);
            float length = pathMeasure.getLength();
            pathMeasure.getSegment(Math.max(f, 0.0f) * length, Math.min(f2, 1.0f) * length, path, true);
        }
    }
}
