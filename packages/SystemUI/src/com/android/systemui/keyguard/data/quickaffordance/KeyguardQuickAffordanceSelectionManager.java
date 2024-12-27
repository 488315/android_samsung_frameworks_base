package com.android.systemui.keyguard.data.quickaffordance;

import java.util.List;
import java.util.Map;
import kotlinx.coroutines.flow.Flow;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public interface KeyguardQuickAffordanceSelectionManager {
    Map getSelections();

    /* renamed from: getSelections */
    Flow mo1960getSelections();

    void setSelections(String str, List list);
}
