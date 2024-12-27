package com.android.server.display;

import java.util.ArrayList;
import java.util.List;

public final class DisplayGroup {
    public int mChangeCount;
    public final List mDisplays = new ArrayList();
    public final int mGroupId;

    public DisplayGroup(int i) {
        this.mGroupId = i;
    }
}
