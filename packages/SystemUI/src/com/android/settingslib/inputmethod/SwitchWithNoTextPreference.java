package com.android.settingslib.inputmethod;

import android.content.Context;
import androidx.preference.SwitchPreference;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
