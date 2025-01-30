package com.android.systemui.keyguard.data.quickaffordance;

import java.util.List;
import java.util.Map;
import kotlinx.coroutines.flow.Flow;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public interface KeyguardQuickAffordanceSelectionManager {
    Map getSelections();

    /* renamed from: getSelections */
    Flow mo1577getSelections();

    void setSelections(String str, List list);
}
