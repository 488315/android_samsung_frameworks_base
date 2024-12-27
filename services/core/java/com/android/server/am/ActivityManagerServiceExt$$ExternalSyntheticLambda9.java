package com.android.server.am;

import android.util.ArrayMap;

import com.android.internal.util.jobs.DumpUtils$$ExternalSyntheticOutline0;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;

import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import java.util.function.BiConsumer;

public final /* synthetic */ class ActivityManagerServiceExt$$ExternalSyntheticLambda9
        implements BiConsumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ PrintWriter f$0;

    public /* synthetic */ ActivityManagerServiceExt$$ExternalSyntheticLambda9(
            int i, PrintWriter printWriter) {
        this.$r8$classId = i;
        this.f$0 = printWriter;
    }

    @Override // java.util.function.BiConsumer
    public final void accept(Object obj, Object obj2) {
        int i = this.$r8$classId;
        PrintWriter printWriter = this.f$0;
        String str = (String) obj;
        switch (i) {
            case 0:
                printWriter.println("Action [" + str + "]");
                printWriter.println("  Packages :");
                Iterator it = ((List) obj2).iterator();
                while (it.hasNext()) {
                    BinaryTransparencyService$$ExternalSyntheticOutline0.m50m(
                            printWriter, "   - ", (String) it.next());
                }
                break;
            case 1:
                printWriter.println("    Package [" + str + "]");
                ((ArrayMap) obj2)
                        .forEach(
                                new ActivityManagerServiceExt$$ExternalSyntheticLambda9(
                                        2, printWriter));
                break;
            default:
                ActivityManagerServiceExt.BrCountInfo brCountInfo =
                        (ActivityManagerServiceExt.BrCountInfo) obj2;
                StringBuilder m =
                        DumpUtils$$ExternalSyntheticOutline0.m("        action=", str, " cnt=");
                m.append(brCountInfo.mCnt);
                m.append(" max=");
                m.append(brCountInfo.mMaxCnt);
                m.append(" total=");
                m.append(brCountInfo.mTotalCnt);
                printWriter.println(m.toString());
                break;
        }
    }
}
