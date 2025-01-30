package com.android.settingslib.widget;

import android.content.Context;
import android.os.Handler;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.URLSpan;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.preference.Preference;
import androidx.preference.PreferenceGroupAdapter;
import androidx.preference.PreferenceViewHolder;
import com.android.systemui.R;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class FooterPreference extends Preference {
    int mIconVisibility;
    View.OnClickListener mLearnMoreListener;
    public FooterLearnMoreSpan mLearnMoreSpan;
    public CharSequence mLearnMoreText;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class FooterLearnMoreSpan extends URLSpan {
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
        if (2147483646 != this.mOrder) {
            this.mOrder = 2147483646;
            PreferenceGroupAdapter preferenceGroupAdapter = this.mListener;
            if (preferenceGroupAdapter != null) {
                Handler handler = preferenceGroupAdapter.mHandler;
                PreferenceGroupAdapter.RunnableC04171 runnableC04171 = preferenceGroupAdapter.mSyncRunnable;
                handler.removeCallbacks(runnableC04171);
                handler.post(runnableC04171);
            }
        }
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
        super.onBindViewHolder(preferenceViewHolder);
        View view = preferenceViewHolder.itemView;
        TextView textView = (TextView) view.findViewById(android.R.id.title);
        if (textView != null && !TextUtils.isEmpty(null)) {
            textView.setContentDescription(null);
        }
        TextView textView2 = (TextView) view.findViewById(R.id.settingslib_learn_more);
        if (textView2 != null) {
            if (this.mLearnMoreListener != null) {
                textView2.setVisibility(0);
                if (TextUtils.isEmpty(this.mLearnMoreText)) {
                    this.mLearnMoreText = textView2.getText();
                } else {
                    textView2.setText(this.mLearnMoreText);
                }
                SpannableString spannableString = new SpannableString(this.mLearnMoreText);
                FooterLearnMoreSpan footerLearnMoreSpan = this.mLearnMoreSpan;
                if (footerLearnMoreSpan != null) {
                    spannableString.removeSpan(footerLearnMoreSpan);
                }
                FooterLearnMoreSpan footerLearnMoreSpan2 = new FooterLearnMoreSpan(this.mLearnMoreListener);
                this.mLearnMoreSpan = footerLearnMoreSpan2;
                spannableString.setSpan(footerLearnMoreSpan2, 0, spannableString.length(), 0);
                textView2.setText(spannableString);
            } else {
                textView2.setVisibility(8);
            }
        }
        View findViewById = view.findViewById(R.id.icon_frame);
        if (findViewById != null) {
            findViewById.setVisibility(this.mIconVisibility);
        }
    }

    @Override // androidx.preference.Preference
    public final void setSummary(CharSequence charSequence) {
        setTitle(charSequence);
    }

    @Override // androidx.preference.Preference
    public final void setSummary$1() {
        setTitle(R.string.lockscreen_none);
    }

    public FooterPreference(Context context) {
        this(context, null);
    }
}
