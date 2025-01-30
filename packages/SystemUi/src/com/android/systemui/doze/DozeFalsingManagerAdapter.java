package com.android.systemui.doze;

import com.android.systemui.classifier.FalsingCollector;
import com.android.systemui.classifier.FalsingCollectorImpl;
import com.android.systemui.doze.DozeMachine;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class DozeFalsingManagerAdapter implements DozeMachine.Part {
    public final FalsingCollector mFalsingCollector;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.doze.DozeFalsingManagerAdapter$1 */
    public abstract /* synthetic */ class AbstractC12381 {
        public static final /* synthetic */ int[] $SwitchMap$com$android$systemui$doze$DozeMachine$State;

        static {
            int[] iArr = new int[DozeMachine.State.values().length];
            $SwitchMap$com$android$systemui$doze$DozeMachine$State = iArr;
            try {
                iArr[DozeMachine.State.DOZE_AOD.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$android$systemui$doze$DozeMachine$State[DozeMachine.State.DOZE_AOD_PAUSING.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$android$systemui$doze$DozeMachine$State[DozeMachine.State.DOZE_AOD_PAUSED.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    public DozeFalsingManagerAdapter(FalsingCollector falsingCollector) {
        this.mFalsingCollector = falsingCollector;
    }

    @Override // com.android.systemui.doze.DozeMachine.Part
    public final void transitionTo(DozeMachine.State state, DozeMachine.State state2) {
        int i = AbstractC12381.$SwitchMap$com$android$systemui$doze$DozeMachine$State[state2.ordinal()];
        boolean z = true;
        if (i != 1 && i != 2 && i != 3) {
            z = false;
        }
        FalsingCollectorImpl falsingCollectorImpl = (FalsingCollectorImpl) this.mFalsingCollector;
        falsingCollectorImpl.mShowingAod = z;
        falsingCollectorImpl.sessionEnd();
    }
}
