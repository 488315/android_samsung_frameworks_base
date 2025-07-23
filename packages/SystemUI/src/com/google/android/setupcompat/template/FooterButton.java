package com.google.android.setupcompat.template;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.PersistableBundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import com.android.systemui.qs.QSOnboardingActivity$onCreate$1;
import com.google.android.setupcompat.R$styleable;
import com.google.android.setupcompat.logging.CustomEvent;
import com.google.android.setupcompat.partnerconfig.PartnerConfigHelper;
import com.google.android.setupcompat.template.FooterBarMixin;
import com.sec.android.secsetupwizardlib.SuwBaseActivity;
import java.util.Locale;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class FooterButton implements View.OnClickListener {
    static final String KEY_BUTTON_ON_CLICK_COUNT = "_onClickCount";
    static final String KEY_BUTTON_TEXT = "_text";
    static final String KEY_BUTTON_TEXT_RESOURCE_NAME = "_textResName";
    static final String KEY_BUTTON_TYPE = "_type";
    public FooterBarMixin.AnonymousClass1 buttonListener;
    public final int buttonType;
    public int clickCount;
    public final boolean enabled;
    public View.OnClickListener onClickListener;
    public CharSequence text;
    public String textResourceName;
    public final int theme;
    public int visibility;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class Builder {
        public final Context context;
        public QSOnboardingActivity$onCreate$1 onClickListener = null;
        public String textResourceName = "";

        public Builder(Context context) {
            this.context = context;
        }
    }

    public static String getTextResourceName(Context context, int i) {
        return (context == null || context.getResources() == null || i == 0) ? "" : context.getResources().getResourceEntryName(i);
    }

    public final PersistableBundle getMetrics(String str) {
        String str2;
        PersistableBundle persistableBundle = new PersistableBundle();
        persistableBundle.putString(str.concat(KEY_BUTTON_TEXT), CustomEvent.trimsStringOverMaxLength(this.text.toString()));
        String concat = str.concat(KEY_BUTTON_TYPE);
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
        persistableBundle.putString(concat, str2);
        persistableBundle.putInt(str.concat(KEY_BUTTON_ON_CLICK_COUNT), this.clickCount);
        String str3 = this.textResourceName;
        if (str3 != null && !str3.equals("")) {
            persistableBundle.putString(str.concat(KEY_BUTTON_TEXT_RESOURCE_NAME), CustomEvent.trimsStringOverMaxLength(this.textResourceName));
        }
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

    public final void setText(SuwBaseActivity suwBaseActivity, int i) {
        FooterBarMixin footerBarMixin;
        LinearLayout linearLayout;
        Button button;
        this.textResourceName = getTextResourceName(suwBaseActivity, i);
        CharSequence text = suwBaseActivity.getText(i);
        this.text = text;
        FooterBarMixin.AnonymousClass1 anonymousClass1 = this.buttonListener;
        if (anonymousClass1 == null || (linearLayout = (footerBarMixin = FooterBarMixin.this).buttonContainer) == null || (button = (Button) linearLayout.findViewById(anonymousClass1.val$id)) == null) {
            return;
        }
        if (PartnerConfigHelper.isGlifExpressiveEnabled(footerBarMixin.context)) {
            footerBarMixin.buttonContainer.post(new FooterBarMixin$$ExternalSyntheticLambda2(footerBarMixin));
        }
        button.setText(text);
    }

    public FooterButton(Context context, AttributeSet attributeSet) {
        this.enabled = true;
        this.visibility = 0;
        this.clickCount = 0;
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

    private FooterButton(CharSequence charSequence, View.OnClickListener onClickListener, int i, int i2, Locale locale, int i3, int i4, String str) {
        this.enabled = true;
        this.clickCount = 0;
        this.text = charSequence;
        this.onClickListener = onClickListener;
        this.buttonType = i;
        this.theme = i2;
        this.visibility = i4;
        this.textResourceName = str;
    }
}
