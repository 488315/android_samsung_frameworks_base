package androidx.compose.ui.graphics.vector;

import java.util.ArrayList;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class PathParser {
    public float[] nodeData = new float[64];
    public ArrayList nodes;

    public final void parsePathString(String str) {
        ArrayList arrayList = this.nodes;
        if (arrayList == null) {
            arrayList = new ArrayList();
            this.nodes = arrayList;
        } else {
            arrayList.clear();
        }
        pathStringToNodes(str, arrayList);
    }

    /* JADX WARN: Code restructure failed: missing block: B:366:0x016d, code lost:
    
        r3 = 0;
     */
    /* JADX WARN: Removed duplicated region for block: B:165:0x041d A[ADDED_TO_REGION, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:167:0x03f2  */
    /* JADX WARN: Removed duplicated region for block: B:225:0x0198  */
    /* JADX WARN: Removed duplicated region for block: B:226:0x01a8  */
    /* JADX WARN: Removed duplicated region for block: B:292:0x02cd  */
    /* JADX WARN: Removed duplicated region for block: B:302:0x02ee  */
    /* JADX WARN: Removed duplicated region for block: B:306:0x02fc  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x03da  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x03f9  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x0406  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void pathStringToNodes(java.lang.String r44, java.util.ArrayList r45) {
        /*
            Method dump skipped, instructions count: 1792
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.graphics.vector.PathParser.pathStringToNodes(java.lang.String, java.util.ArrayList):void");
    }
}
