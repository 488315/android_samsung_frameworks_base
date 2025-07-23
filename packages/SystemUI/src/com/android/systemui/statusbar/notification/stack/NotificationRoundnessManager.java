package com.android.systemui.statusbar.notification.stack;

import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.systemui.Dumpable;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.statusbar.notification.Roundable;
import com.android.systemui.statusbar.notification.SourceType;
import com.android.systemui.statusbar.notification.SourceType$Companion$from$1;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Iterator;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class NotificationRoundnessManager implements Dumpable {
    public static final SourceType$Companion$from$1 DISMISS_ANIMATION = SourceType.from("DismissAnimation");
    public HashSet mAnimatedChildren;
    public boolean mIsClearAllInProgress;
    public boolean mRoundForPulsingViews;
    public ExpandableNotificationRow mSwipedView = null;
    public Roundable mViewBeforeSwipedView = null;
    public Roundable mViewAfterSwipedView = null;

    public NotificationRoundnessManager(DumpManager dumpManager) {
        dumpManager.getClass();
        DumpManager.registerDumpable$default(dumpManager, "NotificationRoundnessManager", this);
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(new StringBuilder("roundForPulsingViews="), this.mRoundForPulsingViews, printWriter, "isClearAllInProgress="), this.mIsClearAllInProgress, printWriter);
    }

    public final void setViewsAffectedBySwipe(Roundable roundable, ExpandableNotificationRow expandableNotificationRow, Roundable roundable2) {
        HashSet hashSet = new HashSet();
        Roundable roundable3 = this.mViewBeforeSwipedView;
        if (roundable3 != null) {
            hashSet.add(roundable3);
        }
        ExpandableNotificationRow expandableNotificationRow2 = this.mSwipedView;
        if (expandableNotificationRow2 != null) {
            hashSet.add(expandableNotificationRow2);
        }
        Roundable roundable4 = this.mViewAfterSwipedView;
        if (roundable4 != null) {
            hashSet.add(roundable4);
        }
        this.mViewBeforeSwipedView = roundable;
        if (roundable != null) {
            hashSet.remove(roundable);
        }
        this.mSwipedView = expandableNotificationRow;
        if (expandableNotificationRow != null) {
            hashSet.remove(expandableNotificationRow);
        }
        this.mViewAfterSwipedView = roundable2;
        if (roundable2 != null) {
            hashSet.remove(roundable2);
        }
        Iterator it = hashSet.iterator();
        while (it.hasNext()) {
            ((Roundable) it.next()).requestRoundnessReset(DISMISS_ANIMATION);
        }
    }
}
