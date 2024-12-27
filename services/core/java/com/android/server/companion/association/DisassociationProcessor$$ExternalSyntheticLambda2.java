package com.android.server.companion.association;

import com.android.internal.util.FunctionalUtils;

public final /* synthetic */ class DisassociationProcessor$$ExternalSyntheticLambda2
        implements FunctionalUtils.ThrowingRunnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ DisassociationProcessor f$0;

    public /* synthetic */ DisassociationProcessor$$ExternalSyntheticLambda2(
            DisassociationProcessor disassociationProcessor, int i) {
        this.$r8$classId = i;
        this.f$0 = disassociationProcessor;
    }

    public final void runOrThrow() {
        int i = this.$r8$classId;
        DisassociationProcessor disassociationProcessor = this.f$0;
        switch (i) {
            case 0:
                disassociationProcessor.mActivityManager.removeOnUidImportanceListener(
                        disassociationProcessor.mOnPackageVisibilityChangeListener);
                break;
            default:
                disassociationProcessor.mActivityManager.addOnUidImportanceListener(
                        disassociationProcessor.mOnPackageVisibilityChangeListener, 200);
                break;
        }
    }
}
