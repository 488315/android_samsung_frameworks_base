package com.android.server.input;

import android.util.IndentingPrintWriter;

import java.util.List;
import java.util.function.BiConsumer;

public final /* synthetic */ class InputManagerService$$ExternalSyntheticLambda0
        implements BiConsumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ InputManagerService$$ExternalSyntheticLambda0(int i, Object obj) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.util.function.BiConsumer
    public final void accept(Object obj, Object obj2) {
        int i = this.$r8$classId;
        Object obj3 = this.f$0;
        switch (i) {
            case 0:
                InputManagerService.lambda$dump$4(
                        (IndentingPrintWriter) obj3, (String) obj, (String) obj2);
                break;
            case 1:
                InputManagerService.lambda$dumpAssociations$8(
                        (IndentingPrintWriter) obj3, (String) obj, (String) obj2);
                break;
            case 2:
                InputManagerService.lambda$dumpAssociations$9(
                        (IndentingPrintWriter) obj3, (String) obj, (String) obj2);
                break;
            case 3:
                InputManagerService.lambda$dumpAssociations$5(
                        (IndentingPrintWriter) obj3, (String) obj, (Integer) obj2);
                break;
            case 4:
                InputManagerService.lambda$dumpAssociations$6(
                        (IndentingPrintWriter) obj3, (String) obj, (Integer) obj2);
                break;
            case 5:
                InputManagerService.lambda$dumpAssociations$7(
                        (IndentingPrintWriter) obj3, (String) obj, (String) obj2);
                break;
            default:
                InputManagerService.lambda$flatten$10((List) obj3, (String) obj, obj2);
                break;
        }
    }
}
