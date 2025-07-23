package com.samsung.android.content.clipboard;

import com.samsung.android.content.clipboard.data.SemClipData;

/* loaded from: classes6.dex */
public interface SemClipboardEventListener {
    void onClipboardUpdated(int i, SemClipData semClipData);

    void onFilterUpdated(int i);
}
