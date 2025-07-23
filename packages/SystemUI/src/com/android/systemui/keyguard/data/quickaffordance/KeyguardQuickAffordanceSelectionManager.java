package com.android.systemui.keyguard.data.quickaffordance;

import java.util.List;
import java.util.Map;
import kotlinx.coroutines.flow.Flow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public interface KeyguardQuickAffordanceSelectionManager {
    Map getSelections();

    /* renamed from: getSelections */
    Flow mo2597getSelections();

    void setSelections(String str, List list);
}
