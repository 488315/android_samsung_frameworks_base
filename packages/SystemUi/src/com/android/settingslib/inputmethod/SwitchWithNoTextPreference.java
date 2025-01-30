package com.android.settingslib.inputmethod;

import android.content.Context;
import androidx.preference.SwitchPreference;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class SwitchWithNoTextPreference extends SwitchPreference {
    public SwitchWithNoTextPreference(Context context) {
        super(context);
        this.mSwitchOn = "";
        notifyChanged();
        this.mSwitchOff = "";
        notifyChanged();
    }
}
