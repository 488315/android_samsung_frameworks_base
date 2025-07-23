package com.samsung.android.content.smartclip;

import android.graphics.Rect;

/* loaded from: classes6.dex */
public interface SemSmartClipDataElement {
    boolean addTag(SemSmartClipMetaTag semSmartClipMetaTag);

    void clearMetaData();

    SemSmartClipMetaTagArray getAllTags();

    Rect getMetaAreaRect();

    SemSmartClipMetaTagArray getTags(String str);

    int removeTags(String str);

    boolean setTag(SemSmartClipMetaTag semSmartClipMetaTag);
}
