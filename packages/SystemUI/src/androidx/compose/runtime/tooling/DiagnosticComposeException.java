package androidx.compose.runtime.tooling;

import java.util.List;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class DiagnosticComposeException extends RuntimeException {
    private final List<ComposeStackTraceFrame> trace;

    public DiagnosticComposeException(List<ComposeStackTraceFrame> list) {
        this.trace = list;
    }

    @Override // java.lang.Throwable
    public final Throwable fillInStackTrace() {
        setStackTrace(new StackTraceElement[0]);
        return this;
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x0078  */
    @Override // java.lang.Throwable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.String getMessage() {
        /*
            r13 = this;
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r1 = "Composition stack when thrown:\n"
            r0.<init>(r1)
            java.util.List<androidx.compose.runtime.tooling.ComposeStackTraceFrame> r13 = r13.trace
            kotlin.collections.builders.ListBuilder r1 = kotlin.collections.CollectionsKt__CollectionsJVMKt.createListBuilder()
            kotlin.collections.ReversedListReadOnly r2 = new kotlin.collections.ReversedListReadOnly
            r2.<init>(r13)
            int r13 = r2.getSize()
            r3 = 0
            r4 = 0
            r6 = r3
            r7 = r6
            r5 = r4
        L1b:
            if (r5 >= r13) goto La8
            java.lang.Object r8 = r2.get(r5)
            androidx.compose.runtime.tooling.ComposeStackTraceFrame r8 = (androidx.compose.runtime.tooling.ComposeStackTraceFrame) r8
            androidx.compose.runtime.tooling.ParsedSourceInformation r9 = r8.sourceInfo
            java.lang.String r10 = r9.functionName
            if (r10 != 0) goto L2e
            if (r6 != 0) goto L2f
            java.lang.String r6 = "<unknown function>"
            goto L2f
        L2e:
            r6 = r10
        L2f:
            java.lang.String r10 = r9.fileName
            if (r10 != 0) goto L38
            if (r7 != 0) goto L39
            java.lang.String r7 = "<unknown file>"
            goto L39
        L38:
            r7 = r10
        L39:
            java.lang.Integer r8 = r8.groupOffset
            if (r8 == 0) goto L51
            int r10 = r8.intValue()
            int[] r11 = r9.lineNumbers
            int r12 = r11.length
            if (r10 >= r12) goto L51
            int r8 = r8.intValue()
            r8 = r11[r8]
            java.lang.String r8 = java.lang.String.valueOf(r8)
            goto L53
        L51:
            java.lang.String r8 = "<unknown line>"
        L53:
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            r10.<init>()
            r10.append(r6)
            r11 = 40
            r10.append(r11)
            r10.append(r7)
            r11 = 58
            r10.append(r11)
            r10.append(r8)
            r8 = 41
            r10.append(r8)
            java.lang.String r8 = r10.toString()
            boolean r10 = r9.isCall
            if (r10 != 0) goto L8c
            boolean r10 = r1.isEmpty()
            if (r10 == 0) goto L80
            r10 = r3
            goto L8a
        L80:
            int r10 = r1.size()
            int r10 = r10 + (-1)
            java.lang.Object r10 = r1.remove(r10)
        L8a:
            java.lang.String r10 = (java.lang.String) r10
        L8c:
            java.lang.String r10 = "rememberCompositionContext"
            java.lang.String r11 = r9.functionName
            boolean r10 = kotlin.jvm.internal.Intrinsics.areEqual(r11, r10)
            if (r10 == 0) goto La1
            java.lang.String r10 = "9igjgp"
            java.lang.String r9 = r9.packageHash
            boolean r9 = kotlin.jvm.internal.Intrinsics.areEqual(r9, r10)
            if (r9 == 0) goto La1
            goto La4
        La1:
            r1.add(r8)
        La4:
            int r5 = r5 + 1
            goto L1b
        La8:
            kotlin.collections.builders.ListBuilder r13 = r1.build()
            kotlin.collections.ReversedListReadOnly r1 = new kotlin.collections.ReversedListReadOnly
            r1.<init>(r13)
            int r13 = r1.getSize()
        Lb5:
            if (r4 >= r13) goto Ld6
            java.lang.Object r2 = r1.get(r4)
            java.lang.String r2 = (java.lang.String) r2
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            java.lang.String r5 = "\tat "
            r3.<init>(r5)
            r3.append(r2)
            java.lang.String r2 = r3.toString()
            r0.append(r2)
            r2 = 10
            r0.append(r2)
            int r4 = r4 + 1
            goto Lb5
        Ld6:
            java.lang.String r13 = r0.toString()
            return r13
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.runtime.tooling.DiagnosticComposeException.getMessage():java.lang.String");
    }
}
