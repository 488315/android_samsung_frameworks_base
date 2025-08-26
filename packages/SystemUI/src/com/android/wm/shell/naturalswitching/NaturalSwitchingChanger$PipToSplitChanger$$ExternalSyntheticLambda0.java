package com.android.wm.shell.naturalswitching;

import com.android.wm.shell.naturalswitching.NaturalSwitchingChanger;

/* loaded from: classes3.dex */
public final /* synthetic */ class NaturalSwitchingChanger$PipToSplitChanger$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ NaturalSwitchingChanger f$0;

    public /* synthetic */ NaturalSwitchingChanger$PipToSplitChanger$$ExternalSyntheticLambda0(NaturalSwitchingChanger naturalSwitchingChanger, int i) {
        this.$r8$classId = i;
        this.f$0 = naturalSwitchingChanger;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        NaturalSwitchingChanger naturalSwitchingChanger = this.f$0;
        switch (i) {
            case 0:
                NaturalSwitchingChanger.PipToSplitChanger pipToSplitChanger = (NaturalSwitchingChanger.PipToSplitChanger) naturalSwitchingChanger;
                pipToSplitChanger.mHideLayoutCallback.accept(Boolean.valueOf(pipToSplitChanger.mNeedToReparentCell));
                break;
            case 1:
                NaturalSwitchingChanger.FreeformToSplitChanger freeformToSplitChanger = (NaturalSwitchingChanger.FreeformToSplitChanger) naturalSwitchingChanger;
                freeformToSplitChanger.mHideLayoutCallback.accept(Boolean.valueOf(freeformToSplitChanger.mNeedToReparentCell));
                break;
            default:
                ((NaturalSwitchingChanger.SplitToFreeformChanger) naturalSwitchingChanger).mHideLayoutCallback.accept(Boolean.TRUE);
                break;
        }
    }
}
