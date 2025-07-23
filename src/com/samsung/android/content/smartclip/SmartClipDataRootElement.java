package com.samsung.android.content.smartclip;

import android.graphics.Rect;
import android.text.TextUtils;
import com.samsung.android.graphics.imagefilter.ShaderAssembler;

/* compiled from: SemSmartClipDataRepository.java */
/* loaded from: classes6.dex */
class SmartClipDataRootElement extends SmartClipDataElementImpl {
    SmartClipDataRootElement() {
    }

    public String collectPlainTextTag() {
        StringBuilder sb = new StringBuilder();
        Rect rect = new Rect();
        SmartClipDataElementImpl smartClipDataElementImpl = this;
        while (smartClipDataElementImpl != null) {
            StringBuilder sb2 = new StringBuilder();
            SemSmartClipMetaTagArray tags = smartClipDataElementImpl.getTags(SemSmartClipMetaTagType.PLAIN_TEXT);
            int size = tags.size();
            Rect metaAreaRect = smartClipDataElementImpl.getMetaAreaRect();
            for (int i = 0; i < size; i++) {
                String value = tags.get(i).getValue();
                if (value != null && !TextUtils.isEmpty(value)) {
                    sb2.append(value);
                    sb2.append(" ");
                }
            }
            if (TextUtils.getTrimmedLength(sb2.toString()) > 0) {
                if (metaAreaRect != null && metaAreaRect.top >= rect.bottom && !TextUtils.isEmpty(sb)) {
                    sb.append(ShaderAssembler.NEWLINE);
                }
                sb.append((CharSequence) sb2);
                sb.append(" ");
                if (metaAreaRect != null) {
                    rect = metaAreaRect;
                }
            }
            smartClipDataElementImpl = smartClipDataElementImpl.traverseNextElement(this);
        }
        String trim = sb.toString().trim();
        if (TextUtils.isEmpty(trim)) {
            return null;
        }
        return trim;
    }
}
