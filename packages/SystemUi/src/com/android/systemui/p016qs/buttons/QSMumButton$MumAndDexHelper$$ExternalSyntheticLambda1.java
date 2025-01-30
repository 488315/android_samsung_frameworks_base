package com.android.systemui.p016qs.buttons;

import com.android.systemui.p016qs.buttons.QSMumButton;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class QSMumButton$MumAndDexHelper$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ QSMumButton.MumAndDexHelper f$0;

    public /* synthetic */ QSMumButton$MumAndDexHelper$$ExternalSyntheticLambda1(QSMumButton.MumAndDexHelper mumAndDexHelper, int i) {
        this.$r8$classId = i;
        this.f$0 = mumAndDexHelper;
    }

    /* JADX WARN: Failed to find 'out' block for switch in B:2:0x0002. Please report as an issue. */
    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
        }
        this.f$0.updateMumSwitchVisibility();
    }
}
