package com.android.wm.shell.naturalswitching;

import com.android.wm.shell.naturalswitching.NaturalSwitchingChanger;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* renamed from: com.android.wm.shell.naturalswitching.NaturalSwitchingChanger$PipToSplitChanger$$ExternalSyntheticLambda0 */
/* loaded from: classes2.dex */
public final /* synthetic */ class RunnableC4009x244370c2 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ NaturalSwitchingChanger f$0;

    public /* synthetic */ RunnableC4009x244370c2(NaturalSwitchingChanger naturalSwitchingChanger, int i) {
        this.$r8$classId = i;
        this.f$0 = naturalSwitchingChanger;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                NaturalSwitchingChanger.PipToSplitChanger pipToSplitChanger = (NaturalSwitchingChanger.PipToSplitChanger) this.f$0;
                pipToSplitChanger.mHideLayoutCallback.accept(Boolean.valueOf(pipToSplitChanger.mNeedToReparentCell));
                break;
            case 1:
                NaturalSwitchingChanger.FreeformToSplitChanger freeformToSplitChanger = (NaturalSwitchingChanger.FreeformToSplitChanger) this.f$0;
                freeformToSplitChanger.mHideLayoutCallback.accept(Boolean.valueOf(freeformToSplitChanger.mNeedToReparentCell));
                break;
            default:
                ((NaturalSwitchingChanger.SplitToFreeformChanger) this.f$0).mHideLayoutCallback.accept(Boolean.TRUE);
                break;
        }
    }
}
