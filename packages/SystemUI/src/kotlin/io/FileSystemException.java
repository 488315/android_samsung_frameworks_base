package kotlin.io;

import java.io.File;
import java.io.IOException;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class FileSystemException extends IOException {
    private final File file;
    private final File other;
    private final String reason;

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public FileSystemException(java.io.File r4, java.io.File r5, java.lang.String r6) {
        /*
            r3 = this;
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r1 = r4.toString()
            r0.<init>(r1)
            if (r5 == 0) goto L1c
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r2 = " -> "
            r1.<init>(r2)
            r1.append(r5)
            java.lang.String r1 = r1.toString()
            r0.append(r1)
        L1c:
            if (r6 == 0) goto L27
            java.lang.String r1 = ": "
            java.lang.String r1 = r1.concat(r6)
            r0.append(r1)
        L27:
            java.lang.String r0 = r0.toString()
            r3.<init>(r0)
            r3.file = r4
            r3.other = r5
            r3.reason = r6
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.io.FileSystemException.<init>(java.io.File, java.io.File, java.lang.String):void");
    }

    public /* synthetic */ FileSystemException(File file, File file2, String str, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(file, (i & 2) != 0 ? null : file2, (i & 4) != 0 ? null : str);
    }
}
