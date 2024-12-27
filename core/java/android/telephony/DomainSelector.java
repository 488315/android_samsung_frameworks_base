package android.telephony;

import android.annotation.SystemApi;

@SystemApi
public interface DomainSelector {
    void finishSelection();

    void reselectDomain(DomainSelectionService.SelectionAttributes selectionAttributes);
}
