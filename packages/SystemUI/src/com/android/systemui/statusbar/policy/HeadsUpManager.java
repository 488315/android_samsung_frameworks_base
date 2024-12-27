package com.android.systemui.statusbar.policy;

import com.android.systemui.Dumpable;

public interface HeadsUpManager extends Dumpable {
    boolean canRemoveImmediately(String str);

    boolean isTrackingHeadsUp();

    void snooze();
}
