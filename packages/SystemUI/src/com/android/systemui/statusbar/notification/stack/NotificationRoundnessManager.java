package com.android.systemui.statusbar.notification.stack;

import com.android.keyguard.KeyguardClockSwitchController$$ExternalSyntheticOutline0;
import com.android.systemui.Dumpable;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.statusbar.notification.Roundable;
import com.android.systemui.statusbar.notification.SourceType;
import com.android.systemui.statusbar.notification.SourceType$Companion$from$1;
import com.android.systemui.statusbar.notification.row.ExpandableView;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Iterator;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class NotificationRoundnessManager implements Dumpable {
    public static final SourceType$Companion$from$1 DISMISS_ANIMATION = SourceType.from("DismissAnimation");
    public HashSet mAnimatedChildren;
    public boolean mIsClearAllInProgress;
    public boolean mRoundForPulsingViews;
    public ExpandableView mSwipedView = null;
    public Roundable mViewBeforeSwipedView = null;
    public Roundable mViewAfterSwipedView = null;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public interface SectionStateProvider {
    }

    public NotificationRoundnessManager(DumpManager dumpManager) {
        dumpManager.getClass();
        DumpManager.registerDumpable$default(dumpManager, "NotificationRoundnessManager", this);
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        KeyguardClockSwitchController$$ExternalSyntheticOutline0.m(KeyguardClockSwitchController$$ExternalSyntheticOutline0.m(new StringBuilder("roundForPulsingViews="), this.mRoundForPulsingViews, printWriter, "isClearAllInProgress="), this.mIsClearAllInProgress, printWriter);
    }

    public final void setViewsAffectedBySwipe(Roundable roundable, ExpandableView expandableView, Roundable roundable2) {
        HashSet hashSet = new HashSet();
        Roundable roundable3 = this.mViewBeforeSwipedView;
        if (roundable3 != null) {
            hashSet.add(roundable3);
        }
        ExpandableView expandableView2 = this.mSwipedView;
        if (expandableView2 != null) {
            hashSet.add(expandableView2);
        }
        Roundable roundable4 = this.mViewAfterSwipedView;
        if (roundable4 != null) {
            hashSet.add(roundable4);
        }
        this.mViewBeforeSwipedView = roundable;
        SourceType$Companion$from$1 sourceType$Companion$from$1 = DISMISS_ANIMATION;
        if (roundable != null) {
            hashSet.remove(roundable);
            roundable.requestRoundness(0.0f, 1.0f, sourceType$Companion$from$1);
        }
        this.mSwipedView = expandableView;
        if (expandableView != null) {
            hashSet.remove(expandableView);
            expandableView.requestRoundness(1.0f, 1.0f, sourceType$Companion$from$1);
        }
        this.mViewAfterSwipedView = roundable2;
        if (roundable2 != null) {
            hashSet.remove(roundable2);
            roundable2.requestRoundness(1.0f, 0.0f, sourceType$Companion$from$1);
        }
        Iterator it = hashSet.iterator();
        while (it.hasNext()) {
            ((Roundable) it.next()).requestRoundnessReset(sourceType$Companion$from$1);
        }
    }
}
