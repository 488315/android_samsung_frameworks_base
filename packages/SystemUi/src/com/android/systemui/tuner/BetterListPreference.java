package com.android.systemui.tuner;

import android.content.Context;
import android.util.AttributeSet;
import androidx.preference.ListPreference;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class BetterListPreference extends ListPreference {
    public CharSequence mSummary;

    public BetterListPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override // androidx.preference.ListPreference, androidx.preference.Preference
    public final CharSequence getSummary() {
        return this.mSummary;
    }

    @Override // androidx.preference.ListPreference, androidx.preference.Preference
    public final void setSummary(CharSequence charSequence) {
        super.setSummary(charSequence);
        this.mSummary = charSequence;
    }
}
