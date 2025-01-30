package com.samsung.android.widget;

import android.os.Bundle;
import java.util.List;

/* loaded from: classes6.dex */
public class SemArrayIndexer extends SemAbstractIndexer {
  private final String TAG;
  private final boolean debug;
  protected List<String> mData;

  @Deprecated
  public SemArrayIndexer(List<String> listData, CharSequence indexCharacters) {
    super(indexCharacters);
    this.TAG = "SemArrayIndexer";
    this.debug = false;
    this.mData = listData;
  }

  @Override // com.samsung.android.widget.SemAbstractIndexer
  protected int getItemCount() {
    return this.mData.size();
  }

  @Override // com.samsung.android.widget.SemAbstractIndexer
  protected String getItemAt(int pos) {
    return this.mData.get(pos);
  }

  @Override // com.samsung.android.widget.SemAbstractIndexer
  protected Bundle getBundle() {
    return null;
  }

  @Override // com.samsung.android.widget.SemAbstractIndexer
  protected boolean isDataToBeIndexedAvailable() {
    return getItemCount() > 0;
  }
}
