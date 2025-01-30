package com.google.android.setupcompat.template;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.util.AttributeSet;
import android.view.View;
import com.google.android.setupcompat.R$styleable;
import com.google.android.setupcompat.logging.CustomEvent;
import java.util.Locale;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class FooterButton implements View.OnClickListener {
    public final int buttonType;
    public final View.OnClickListener onClickListener;
    public final CharSequence text;
    public final int theme;
    public final boolean enabled = true;
    public int clickCount = 0;

    public FooterButton(Context context, AttributeSet attributeSet) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.SucFooterButton);
        this.text = obtainStyledAttributes.getString(1);
        this.onClickListener = null;
        int i = obtainStyledAttributes.getInt(2, 0);
        if (i < 0 || i > 8) {
            throw new IllegalArgumentException("Not a ButtonType");
        }
        this.buttonType = i;
        this.theme = obtainStyledAttributes.getResourceId(0, 0);
        obtainStyledAttributes.recycle();
    }

    public final PersistableBundle getMetrics(String str) {
        String str2;
        PersistableBundle persistableBundle = new PersistableBundle();
        String concat = str.concat("_text");
        String charSequence = this.text.toString();
        Parcelable.Creator<CustomEvent> creator = CustomEvent.CREATOR;
        if (charSequence.length() > 50) {
            charSequence = String.format("%s…", charSequence.substring(0, 49));
        }
        persistableBundle.putString(concat, charSequence);
        String concat2 = str.concat("_type");
        switch (this.buttonType) {
            case 1:
                str2 = "ADD_ANOTHER";
                break;
            case 2:
                str2 = "CANCEL";
                break;
            case 3:
                str2 = "CLEAR";
                break;
            case 4:
                str2 = "DONE";
                break;
            case 5:
                str2 = "NEXT";
                break;
            case 6:
                str2 = "OPT_IN";
                break;
            case 7:
                str2 = "SKIP";
                break;
            case 8:
                str2 = "STOP";
                break;
            default:
                str2 = "OTHER";
                break;
        }
        persistableBundle.putString(concat2, str2);
        persistableBundle.putInt(str.concat("_onClickCount"), this.clickCount);
        return persistableBundle;
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        View.OnClickListener onClickListener = this.onClickListener;
        if (onClickListener != null) {
            this.clickCount++;
            onClickListener.onClick(view);
        }
    }

    private FooterButton(CharSequence charSequence, View.OnClickListener onClickListener, int i, int i2, Locale locale, int i3) {
        this.text = charSequence;
        this.onClickListener = onClickListener;
        this.buttonType = i;
        this.theme = i2;
    }
}
