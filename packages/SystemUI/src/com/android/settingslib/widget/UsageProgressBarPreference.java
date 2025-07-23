package com.android.settingslib.widget;

import android.content.Context;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.AbsoluteSizeSpan;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;
import com.android.systemui.R;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class UsageProgressBarPreference extends Preference {
    public final Pattern mNumberPattern;
    public final int mPercent;

    public UsageProgressBarPreference(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mNumberPattern = Pattern.compile("[\\d]*[\\٫.,]?[\\d]+");
        this.mPercent = -1;
        this.mLayoutResId = R.layout.preference_usage_progress_bar;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        String str;
        super.onBindViewHolder(preferenceViewHolder);
        preferenceViewHolder.mDividerAllowedAbove = false;
        preferenceViewHolder.mDividerAllowedBelow = false;
        TextView textView = (TextView) preferenceViewHolder.findViewById(R.id.usage_summary);
        if (TextUtils.isEmpty(null)) {
            str = "";
        } else {
            Matcher matcher = this.mNumberPattern.matcher(null);
            if (matcher.find()) {
                SpannableString spannableString = new SpannableString(null);
                spannableString.setSpan(new AbsoluteSizeSpan(64, true), matcher.start(), matcher.end(), 33);
                str = spannableString;
            } else {
                str = null;
            }
        }
        textView.setText(str);
        TextView textView2 = (TextView) preferenceViewHolder.findViewById(R.id.bottom_summary);
        if (TextUtils.isEmpty(null)) {
            textView2.setVisibility(8);
        } else {
            textView2.setVisibility(0);
            textView2.setMovementMethod(LinkMovementMethod.getInstance());
            textView2.setText((CharSequence) null);
            if (!TextUtils.isEmpty(null)) {
                textView2.setContentDescription(null);
            }
        }
        ProgressBar progressBar = (ProgressBar) preferenceViewHolder.findViewById(android.R.id.progress);
        if (this.mPercent < 0) {
            progressBar.setIndeterminate(true);
        } else {
            progressBar.setIndeterminate(false);
            progressBar.setProgress(this.mPercent);
        }
        FrameLayout frameLayout = (FrameLayout) preferenceViewHolder.findViewById(R.id.custom_content);
        frameLayout.removeAllViews();
        frameLayout.setVisibility(8);
    }

    public UsageProgressBarPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mNumberPattern = Pattern.compile("[\\d]*[\\٫.,]?[\\d]+");
        this.mPercent = -1;
        this.mLayoutResId = R.layout.preference_usage_progress_bar;
    }

    public UsageProgressBarPreference(Context context) {
        this(context, null);
    }
}
