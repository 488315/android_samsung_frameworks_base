package com.samsung.android.knox.lockscreen;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class LSOItemView {
    /* JADX WARN: Removed duplicated region for block: B:13:0x0042 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:15:0x0043  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static android.view.View getView(android.content.Context r3, com.samsung.android.knox.lockscreen.LSOItemData r4) {
        /*
            byte r0 = r4.getType()
            r1 = 1
            r2 = 0
            if (r0 == r1) goto L3a
            r1 = 2
            if (r0 == r1) goto L31
            r1 = 3
            if (r0 == r1) goto L28
            r1 = 4
            if (r0 == r1) goto L1e
            r1 = 5
            if (r0 == r1) goto L16
            r3 = r2
            goto L40
        L16:
            r0 = r4
            com.samsung.android.knox.lockscreen.LSOItemWidget r0 = (com.samsung.android.knox.lockscreen.LSOItemWidget) r0
            android.view.View r3 = com.samsung.android.knox.lockscreen.LSOWidgetView.getWidget(r3, r0)
            goto L40
        L1e:
            com.samsung.android.knox.lockscreen.LSOContainerView r0 = new com.samsung.android.knox.lockscreen.LSOContainerView
            r1 = r4
            com.samsung.android.knox.lockscreen.LSOItemContainer r1 = (com.samsung.android.knox.lockscreen.LSOItemContainer) r1
            r0.<init>(r3, r1)
        L26:
            r3 = r0
            goto L40
        L28:
            com.samsung.android.knox.lockscreen.LSOImageView r0 = new com.samsung.android.knox.lockscreen.LSOImageView
            r1 = r4
            com.samsung.android.knox.lockscreen.LSOItemImage r1 = (com.samsung.android.knox.lockscreen.LSOItemImage) r1
            r0.<init>(r3, r1)
            goto L26
        L31:
            com.samsung.android.knox.lockscreen.LSOTextView r0 = new com.samsung.android.knox.lockscreen.LSOTextView
            r1 = r4
            com.samsung.android.knox.lockscreen.LSOItemText r1 = (com.samsung.android.knox.lockscreen.LSOItemText) r1
            r0.<init>(r3, r1)
            goto L26
        L3a:
            android.widget.Space r0 = new android.widget.Space
            r0.<init>(r3)
            goto L26
        L40:
            if (r3 != 0) goto L43
            return r2
        L43:
            r0 = 16
            boolean r0 = r4.isFieldUpdated(r0)
            if (r0 == 0) goto L52
            int r0 = r4.getBgColor()
            r3.setBackgroundColor(r0)
        L52:
            r0 = 64
            boolean r0 = r4.isFieldUpdated(r0)
            if (r0 == 0) goto L82
            com.samsung.android.knox.lockscreen.LSOAttributeSet r4 = r4.getAttrs()
            int r0 = r4.size()
            if (r0 <= 0) goto L82
            java.lang.String r0 = "android:alpha"
            boolean r1 = r4.containsKey(r0)
            if (r1 == 0) goto L82
            java.lang.Float r1 = r4.getAsFloat(r0)
            if (r1 == 0) goto L7e
            java.lang.Float r4 = r4.getAsFloat(r0)
            float r4 = r4.floatValue()
            r3.setAlpha(r4)
            return r3
        L7e:
            r4 = 0
            r3.setAlpha(r4)
        L82:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.knox.lockscreen.LSOItemView.getView(android.content.Context, com.samsung.android.knox.lockscreen.LSOItemData):android.view.View");
    }
}
