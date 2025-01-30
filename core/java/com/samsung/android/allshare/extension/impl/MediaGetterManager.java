package com.samsung.android.allshare.extension.impl;

import com.samsung.android.allshare.media.Provider;

/* loaded from: classes5.dex */
public class MediaGetterManager {
  private MediaGetterManager() {}

  public static IMediaGetter createMediaGetter(Provider provider) {
    if (provider == null) {
      return null;
    }
    if (provider.isSearchable()) {
      if (isWM7Provider(provider)) {
        return new WM7SearchMediaGetter();
      }
      return new SearchMediaGetter();
    }
    return new BrowseMediaGetter();
  }

  private static boolean isWM7Provider(Provider provider) {
    return provider.getModelName().startsWith("Windows Media");
  }
}
