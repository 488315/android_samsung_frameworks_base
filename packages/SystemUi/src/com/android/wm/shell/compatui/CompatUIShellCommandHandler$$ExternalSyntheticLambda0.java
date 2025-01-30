package com.android.wm.shell.compatui;

import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class CompatUIShellCommandHandler$$ExternalSyntheticLambda0 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ CompatUIConfiguration f$0;

    public /* synthetic */ CompatUIShellCommandHandler$$ExternalSyntheticLambda0(CompatUIConfiguration compatUIConfiguration, int i) {
        this.$r8$classId = i;
        this.f$0 = compatUIConfiguration;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                this.f$0.mIsRestartDialogOverrideEnabled = ((Boolean) obj).booleanValue();
                break;
            default:
                CompatUIConfiguration compatUIConfiguration = this.f$0;
                ((Boolean) obj).booleanValue();
                compatUIConfiguration.getClass();
                break;
        }
    }
}
