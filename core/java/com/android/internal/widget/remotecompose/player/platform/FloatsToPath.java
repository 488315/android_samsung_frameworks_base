package com.android.internal.widget.remotecompose.player.platform;

import android.graphics.Path;
import android.graphics.PathMeasure;
import com.android.internal.widget.remotecompose.core.operations.Utils;

/* loaded from: classes5.dex */
public class FloatsToPath {
    public static void genPath(Path retPath, float[] floatPath, float start, float stop) {
        int i = 0;
        Path path = new Path();
        while (i < floatPath.length) {
            switch (Utils.idFromNan(floatPath[i])) {
                case 10:
                    int i2 = i + 1;
                    path.moveTo(floatPath[i2 + 0], floatPath[i2 + 1]);
                    i = i2 + 2;
                    break;
                case 11:
                    int i3 = i + 3;
                    path.lineTo(floatPath[i3 + 0], floatPath[i3 + 1]);
                    i = i3 + 2;
                    break;
                case 12:
                    int i4 = i + 3;
                    path.quadTo(floatPath[i4 + 0], floatPath[i4 + 1], floatPath[i4 + 2], floatPath[i4 + 3]);
                    i = i4 + 4;
                    break;
                case 13:
                    int i5 = i + 3;
                    path.conicTo(floatPath[i5 + 0], floatPath[i5 + 1], floatPath[i5 + 2], floatPath[i5 + 3], floatPath[i5 + 4]);
                    i = i5 + 5;
                    break;
                case 14:
                    int i6 = i + 3;
                    path.cubicTo(floatPath[i6 + 0], floatPath[i6 + 1], floatPath[i6 + 2], floatPath[i6 + 3], floatPath[i6 + 4], floatPath[i6 + 5]);
                    i = i6 + 6;
                    break;
                case 15:
                    path.close();
                    i++;
                    break;
                case 16:
                    i++;
                    break;
                default:
                    System.err.println(" Odd command " + Utils.idFromNan(floatPath[i]));
                    break;
            }
        }
        retPath.reset();
        if (start <= 0.0f && stop >= 1.0f) {
            retPath.addPath(path);
            return;
        }
        if (start < stop) {
            PathMeasure measure = new PathMeasure();
            measure.setPath(path, false);
            float len = measure.getLength();
            float scaleStart = Math.max(start, 0.0f) * len;
            float scaleStop = Math.min(stop, 1.0f) * len;
            measure.getSegment(scaleStart, scaleStop, retPath, true);
        }
    }
}
