package com.android.server.wm;

import android.graphics.Rect;
import android.graphics.Region;

import com.android.internal.util.jobs.Preconditions$$ExternalSyntheticOutline0;

import java.io.PrintWriter;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class DisplayContent$$ExternalSyntheticLambda54 implements Consumer {
    public final /* synthetic */ int $r8$classId = 1;
    public final /* synthetic */ int[] f$0;
    public final /* synthetic */ Object f$1;
    public final /* synthetic */ Object f$2;

    public /* synthetic */ DisplayContent$$ExternalSyntheticLambda54(
            PrintWriter printWriter, String str, int[] iArr) {
        this.f$1 = printWriter;
        this.f$2 = str;
        this.f$0 = iArr;
    }

    public /* synthetic */ DisplayContent$$ExternalSyntheticLambda54(
            int[] iArr, int[] iArr2, Region region) {
        this.f$0 = iArr;
        this.f$1 = iArr2;
        this.f$2 = region;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                int[] iArr = this.f$0;
                int[] iArr2 = (int[]) this.f$1;
                Region region = (Region) this.f$2;
                Rect rect = (Rect) obj;
                if (iArr[0] > 0) {
                    int height = rect.height();
                    iArr2[0] = iArr2[0] + height;
                    int i = iArr[0];
                    if (height > i) {
                        rect.top = rect.bottom - i;
                    }
                    iArr[0] = i - height;
                    region.op(rect, Region.Op.UNION);
                    break;
                }
                break;
            default:
                PrintWriter printWriter = (PrintWriter) this.f$1;
                String str = (String) this.f$2;
                int[] iArr3 = this.f$0;
                WindowStateAnimator windowStateAnimator = ((WindowState) obj).mWinAnimator;
                StringBuilder m = Preconditions$$ExternalSyntheticOutline0.m(str, "Window #");
                m.append(iArr3[0]);
                m.append(": ");
                m.append(windowStateAnimator);
                printWriter.println(m.toString());
                iArr3[0] = iArr3[0] + 1;
                break;
        }
    }
}
