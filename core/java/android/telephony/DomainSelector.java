package android.telephony;


/* loaded from: classes3.dex */
public interface DomainSelector {
  void cancelSelection();

  void finishSelection();

  void reselectDomain(DomainSelectionService.SelectionAttributes selectionAttributes);
}
