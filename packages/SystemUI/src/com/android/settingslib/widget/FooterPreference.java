package com.android.settingslib.widget;

import android.content.Context;
import android.content.Intent;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.URLSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;
import com.android.systemui.R;
import java.net.URISyntaxException;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class FooterPreference extends Preference {
    int mIconVisibility;
    View.OnClickListener mLearnMoreListener;
    public FooterLearnMoreSpan mLearnMoreSpan;
    public CharSequence mLearnMoreText;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class FooterLearnMoreSpan extends URLSpan {
        public final View.OnClickListener mClickListener;

        public FooterLearnMoreSpan(View.OnClickListener onClickListener) {
            super("");
            this.mClickListener = onClickListener;
        }

        @Override // android.text.style.URLSpan, android.text.style.ClickableSpan
        public final void onClick(View view) {
            View.OnClickListener onClickListener = this.mClickListener;
            if (onClickListener != null) {
                onClickListener.onClick(view);
            }
        }
    }

    public FooterPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet, R.attr.footerPreferenceStyle);
        this.mIconVisibility = 0;
        this.mLayoutResId = R.layout.preference_footer;
        if (getIcon() == null) {
            setIcon(AppCompatResources.getDrawable(R.drawable.settingslib_ic_info_outline_24, this.mContext));
            this.mIconResId = R.drawable.settingslib_ic_info_outline_24;
        }
        setOrder(2147483646);
        if (TextUtils.isEmpty(this.mKey)) {
            setKey("footer_preference");
        }
        setSelectable(false);
    }

    public CharSequence getContentDescription() {
        return null;
    }

    @Override // androidx.preference.Preference
    public final CharSequence getSummary() {
        return this.mTitle;
    }

    @Override // androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        URLSpan uRLSpan;
        String url;
        super.onBindViewHolder(preferenceViewHolder);
        TextView textView = (TextView) preferenceViewHolder.itemView.findViewById(android.R.id.title);
        if (textView != null) {
            if (!TextUtils.isEmpty(null)) {
                textView.setContentDescription(null);
            }
            CharSequence charSequence = this.mTitle;
            if (charSequence instanceof Spanned) {
                ClickableSpan[] clickableSpanArr = (ClickableSpan[]) ((Spanned) charSequence).getSpans(0, charSequence.length(), ClickableSpan.class);
                if (clickableSpanArr.length != 0) {
                    SpannableString spannableString = new SpannableString(charSequence);
                    for (ClickableSpan clickableSpan : clickableSpanArr) {
                        if ((clickableSpan instanceof URLSpan) && (url = (uRLSpan = (URLSpan) clickableSpan).getURL()) != null && url.startsWith("intent:")) {
                            int spanStart = spannableString.getSpanStart(uRLSpan);
                            int spanEnd = spannableString.getSpanEnd(uRLSpan);
                            spannableString.removeSpan(uRLSpan);
                            try {
                                final Intent parseUri = Intent.parseUri(url, 1);
                                spannableString.setSpan(new ClickableSpan() { // from class: com.android.settingslib.widget.FooterPreference.1
                                    @Override // android.text.style.ClickableSpan
                                    public final void onClick(View view) {
                                        FooterPreference.this.mContext.startActivity(parseUri);
                                    }
                                }, spanStart, spanEnd, 33);
                            } catch (URISyntaxException e) {
                                Log.e("FooterPreference", "Invalid URI ".concat(url), e);
                            }
                        }
                    }
                    textView.setText(spannableString);
                    textView.setMovementMethod(LinkMovementMethod.getInstance());
                }
            }
        }
        TextView textView2 = (TextView) preferenceViewHolder.itemView.findViewById(R.id.settingslib_learn_more);
        if (textView2 != null) {
            if (this.mLearnMoreListener != null) {
                textView2.setVisibility(0);
                if (TextUtils.isEmpty(this.mLearnMoreText)) {
                    this.mLearnMoreText = textView2.getText();
                } else {
                    textView2.setText(this.mLearnMoreText);
                }
                SpannableString spannableString2 = new SpannableString(this.mLearnMoreText);
                FooterLearnMoreSpan footerLearnMoreSpan = this.mLearnMoreSpan;
                if (footerLearnMoreSpan != null) {
                    spannableString2.removeSpan(footerLearnMoreSpan);
                }
                FooterLearnMoreSpan footerLearnMoreSpan2 = new FooterLearnMoreSpan(this.mLearnMoreListener);
                this.mLearnMoreSpan = footerLearnMoreSpan2;
                spannableString2.setSpan(footerLearnMoreSpan2, 0, spannableString2.length(), 0);
                textView2.setText(spannableString2);
            } else {
                textView2.setVisibility(8);
            }
        }
        View findViewById = preferenceViewHolder.itemView.findViewById(R.id.icon_frame);
        if (findViewById != null) {
            findViewById.setVisibility(this.mIconVisibility);
        }
    }

    @Override // androidx.preference.Preference
    public final void setSummary(CharSequence charSequence) {
        setTitle(charSequence);
    }

    @Override // androidx.preference.Preference
    public final void setSummary(int i) {
        setTitle(i);
    }

    public FooterPreference(Context context) {
        this(context, null);
    }
}
