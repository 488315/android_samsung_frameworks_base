package com.samsung.android.content.smartclip;

import java.util.ArrayList;

/* loaded from: classes5.dex */
public abstract class SemSmartClipMetaTagArray extends ArrayList<SemSmartClipMetaTag> {
  public abstract boolean addMetaTag(SemSmartClipMetaTag semSmartClipMetaTag);

  public abstract SemSmartClipMetaTagArray getMetaTags(String str);

  public abstract int removeMetaTags(String str);
}
