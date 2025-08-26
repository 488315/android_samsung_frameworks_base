package androidx.compose.runtime.tooling;

import java.util.List;
import kotlin.collections.CollectionsKt__CollectionsJVMKt;
import kotlin.collections.ReversedListReadOnly;
import kotlin.collections.builders.ListBuilder;
import kotlin.jvm.internal.Intrinsics;

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

    /* JADX WARN: Removed duplicated region for block: B:19:0x0051  */
    @Override // java.lang.Throwable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final String getMessage() {
        String strValueOf;
        StringBuilder sb = new StringBuilder("Composition stack when thrown:\n");
        List<ComposeStackTraceFrame> list = this.trace;
        ListBuilder listBuilderCreateListBuilder = CollectionsKt__CollectionsJVMKt.createListBuilder();
        ReversedListReadOnly reversedListReadOnly = new ReversedListReadOnly(list);
        int size = reversedListReadOnly.getSize();
        String str = null;
        String str2 = null;
        for (int i = 0; i < size; i++) {
            ComposeStackTraceFrame composeStackTraceFrame = (ComposeStackTraceFrame) reversedListReadOnly.get(i);
            ParsedSourceInformation parsedSourceInformation = composeStackTraceFrame.sourceInfo;
            String str3 = parsedSourceInformation.functionName;
            if (str3 != null) {
                str = str3;
            } else if (str == null) {
                str = "<unknown function>";
            }
            String str4 = parsedSourceInformation.fileName;
            if (str4 != null) {
                str2 = str4;
            } else if (str2 == null) {
                str2 = "<unknown file>";
            }
            Integer num = composeStackTraceFrame.groupOffset;
            if (num != null) {
                int iIntValue = num.intValue();
                int[] iArr = parsedSourceInformation.lineNumbers;
                strValueOf = iIntValue < iArr.length ? String.valueOf(iArr[num.intValue()]) : "<unknown line>";
            }
            String str5 = str + '(' + str2 + ':' + strValueOf + ')';
            if (!parsedSourceInformation.isCall) {
            }
            if (!Intrinsics.areEqual(parsedSourceInformation.functionName, "rememberCompositionContext") || !Intrinsics.areEqual(parsedSourceInformation.packageHash, "9igjgp")) {
                listBuilderCreateListBuilder.add(str5);
            }
        }
        ReversedListReadOnly reversedListReadOnly2 = new ReversedListReadOnly(listBuilderCreateListBuilder.build());
        int size2 = reversedListReadOnly2.getSize();
        for (int i2 = 0; i2 < size2; i2++) {
            sb.append("\tat " + ((String) reversedListReadOnly2.get(i2)));
            sb.append('\n');
        }
        return sb.toString();
    }
}
