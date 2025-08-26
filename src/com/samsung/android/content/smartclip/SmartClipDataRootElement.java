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
        SmartClipDataElementImpl smartClipDataElementImplTraverseNextElement = this;
        while (smartClipDataElementImplTraverseNextElement != null) {
            StringBuilder sb2 = new StringBuilder();
            SemSmartClipMetaTagArray tags = smartClipDataElementImplTraverseNextElement.getTags(SemSmartClipMetaTagType.PLAIN_TEXT);
            int size = tags.size();
            Rect metaAreaRect = smartClipDataElementImplTraverseNextElement.getMetaAreaRect();
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
            smartClipDataElementImplTraverseNextElement = smartClipDataElementImplTraverseNextElement.traverseNextElement(this);
        }
        String strTrim = sb.toString().trim();
        if (TextUtils.isEmpty(strTrim)) {
            return null;
        }
        return strTrim;
    }
}
