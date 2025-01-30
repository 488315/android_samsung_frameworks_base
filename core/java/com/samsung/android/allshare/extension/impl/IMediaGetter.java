package com.samsung.android.allshare.extension.impl;

import com.samsung.android.allshare.ERROR;
import com.samsung.android.allshare.Item;
import com.samsung.android.allshare.extension.FlatProvider;
import com.samsung.android.allshare.media.Provider;
import java.util.ArrayList;

/* loaded from: classes5.dex */
public interface IMediaGetter {

  public interface IMediaGetterConnection {
    void onCancel();

    void onError(ERROR error);

    void onFinish();

    void onProgress(Item[] itemArr);

    void onStart();
  }

  void cancel(FlatProvider.IFlatProviderConnection iFlatProviderConnection);

  void start(
      Provider provider,
      Item.MediaType mediaType,
      FlatProvider.IFlatProviderConnection iFlatProviderConnection);

  void start(
      Provider provider,
      ArrayList<Item.MediaType> arrayList,
      FlatProvider.IFlatProviderConnection iFlatProviderConnection);
}
