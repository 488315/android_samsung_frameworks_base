package com.samsung.android.content.smartclip;

import android.graphics.Rect;

/* loaded from: classes5.dex */
public interface SemSmartClipDataElement {
    boolean addTag(SemSmartClipMetaTag semSmartClipMetaTag);

    void clearMetaData();

    SemSmartClipMetaTagArray getAllTags();

    int getExtractionMode();

    Rect getMetaAreaRect();

    SemSmartClipMetaTagArray getTags(String str);

    int removeTags(String str);

    boolean sendSuspendedExtractionData();

    void setMetaAreaRect(Rect rect);

    boolean setTag(SemSmartClipMetaTag semSmartClipMetaTag);
}
