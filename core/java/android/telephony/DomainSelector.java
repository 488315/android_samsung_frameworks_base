package android.telephony;

import android.annotation.SystemApi;

@SystemApi
/* loaded from: classes4.dex */
public interface DomainSelector {
    void finishSelection();

    void reselectDomain(DomainSelectionService.SelectionAttributes selectionAttributes);
}
