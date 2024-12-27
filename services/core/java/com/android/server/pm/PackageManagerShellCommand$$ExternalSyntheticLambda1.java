package com.android.server.pm;

import com.android.internal.util.jobs.DumpUtils$$ExternalSyntheticOutline0;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import java.io.PrintWriter;
import java.util.function.BiConsumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class PackageManagerShellCommand$$ExternalSyntheticLambda1 implements BiConsumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ PackageManagerShellCommand$$ExternalSyntheticLambda1(int i, Object obj) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.util.function.BiConsumer
    public final void accept(Object obj, Object obj2) {
        int i = this.$r8$classId;
        Object obj3 = this.f$0;
        switch (i) {
            case 0:
                String[] strArr = (String[]) obj2;
                StringBuilder m = DumpUtils$$ExternalSyntheticOutline0.m("app: ", (String) obj, ", ");
                m.append(strArr[0]);
                m.append(", ");
                m.append(strArr[1]);
                m.append(", ");
                BinaryTransparencyService$$ExternalSyntheticOutline0.m(m, strArr[2], (PrintWriter) obj3);
                break;
            case 1:
                ((PrintWriter) obj3).println("app: " + ((String) obj) + ", category: " + ((String) obj2));
                break;
            default:
                ((PackageManagerShellCommand) obj3).getOutPrintWriter().println(((String) obj) + " granted:" + ((Boolean) obj2));
                break;
        }
    }
}
