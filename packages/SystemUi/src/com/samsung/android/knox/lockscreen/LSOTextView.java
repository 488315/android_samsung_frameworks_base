package com.samsung.android.knox.lockscreen;

import android.content.Context;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.widget.TextView;
import com.samsung.android.knox.lockscreen.LSOItemText;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class LSOTextView extends TextView {

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.samsung.android.knox.lockscreen.LSOTextView$1 */
    public static /* synthetic */ class C47111 {

        /* renamed from: $SwitchMap$com$samsung$android$knox$lockscreen$LSOItemText$LSOTextSize */
        public static final /* synthetic */ int[] f487x566dc483;

        static {
            int[] iArr = new int[LSOItemText.LSOTextSize.values().length];
            f487x566dc483 = iArr;
            try {
                iArr[LSOItemText.LSOTextSize.TINY.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f487x566dc483[LSOItemText.LSOTextSize.SMALL.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f487x566dc483[LSOItemText.LSOTextSize.NORMAL.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                f487x566dc483[LSOItemText.LSOTextSize.LARGE.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                f487x566dc483[LSOItemText.LSOTextSize.HUGE.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
        }
    }

    public LSOTextView(Context context, LSOItemText lSOItemText) {
        super(context);
        init(lSOItemText);
    }

    public final float getTextSize(LSOItemText lSOItemText) {
        if (!LSOUtils.isTablet()) {
            return lSOItemText.getTextSize().nativeVal;
        }
        float f = lSOItemText.getTextSize().nativeVal;
        int i = C47111.f487x566dc483[lSOItemText.getTextSize().ordinal()];
        if (i == 1) {
            return 1.85f;
        }
        if (i == 2) {
            return 1.93f;
        }
        if (i == 3) {
            return 2.0f;
        }
        if (i == 4) {
            return 2.6f;
        }
        if (i != 5) {
            return f;
        }
        return 3.6f;
    }

    public final void init(LSOItemText lSOItemText) {
        if (lSOItemText.isFieldUpdated(128)) {
            setText(lSOItemText.text);
        }
        if (lSOItemText.isFieldUpdated(256)) {
            setTextColor(lSOItemText.text_color);
        } else {
            setTextColor(EmergencyPhoneWidget.BG_COLOR);
        }
        setTextSize(0, getTextSize(lSOItemText) * getTextSize());
        if (lSOItemText.isFieldUpdated(1024)) {
            setTypeface(Typeface.DEFAULT, lSOItemText.text_style);
        }
        if (lSOItemText.isFieldUpdated(32)) {
            setGravity(lSOItemText.gravity);
        }
        if (lSOItemText.isFieldUpdated(64)) {
            LSOAttributeSet attrs = lSOItemText.getAttrs();
            if (attrs.containsKey(LSOAttrConst.ATTR_MAX_LINES)) {
                setMaxLines(attrs.getAsInteger(LSOAttrConst.ATTR_MAX_LINES).intValue());
                setEllipsize(TextUtils.TruncateAt.END);
            }
            if (attrs.containsKey(LSOAttrConst.ATTR_SINGLE_LINE)) {
                setSingleLine(attrs.getAsBoolean(LSOAttrConst.ATTR_SINGLE_LINE).booleanValue());
            }
        }
    }
}
