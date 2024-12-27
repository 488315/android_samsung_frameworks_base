package com.android.systemui.keyguard.data.quickaffordance;

import java.util.List;
import java.util.Map;
import kotlinx.coroutines.flow.Flow;

public interface KeyguardQuickAffordanceSelectionManager {
    Map getSelections();

    /* renamed from: getSelections */
    Flow mo1960getSelections();

    void setSelections(String str, List list);
}
