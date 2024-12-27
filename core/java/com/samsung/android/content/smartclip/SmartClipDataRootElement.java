package com.samsung.android.content.smartclip;

import android.graphics.Rect;
import android.text.TextUtils;

/* compiled from: SemSmartClipDataRepository.java */
/* loaded from: classes5.dex */
class SmartClipDataRootElement extends SmartClipDataElementImpl {
    SmartClipDataRootElement() {}

    public String collectPlainTextTag() {
        SmartClipDataElementImpl element = this;
        StringBuilder resultString = new StringBuilder();
        Rect prevTextTagRect = new Rect();
        while (element != null) {
            StringBuilder plainText = new StringBuilder();
            SemSmartClipMetaTagArray textTags = element.getTags(SemSmartClipMetaTagType.PLAIN_TEXT);
            int textTagCount = textTags.size();
            Rect curTextTagRect = element.getMetaAreaRect();
            for (int i = 0; i < textTagCount; i++) {
                String curTagValue = textTags.get(i).getValue();
                if (curTagValue != null && !TextUtils.isEmpty(curTagValue)) {
                    plainText.append(curTagValue).append(" ");
                }
            }
            if (TextUtils.getTrimmedLength(plainText.toString()) > 0) {
                if (curTextTagRect != null
                        && curTextTagRect.top >= prevTextTagRect.bottom
                        && !TextUtils.isEmpty(resultString)) {
                    resultString.append("\n");
                }
                resultString.append((CharSequence) plainText).append(" ");
                if (curTextTagRect != null) {
                    prevTextTagRect = curTextTagRect;
                }
            }
            element = element.traverseNextElement(this);
        }
        String plainTextResult = resultString.toString().trim();
        if (TextUtils.isEmpty(plainTextResult)) {
            return null;
        }
        return plainTextResult;
    }
}
