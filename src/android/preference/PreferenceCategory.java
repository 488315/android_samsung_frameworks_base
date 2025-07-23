package android.preference;

import android.content.Context;
import android.util.AttributeSet;

@Deprecated
/* loaded from: classes3.dex */
public class PreferenceCategory extends PreferenceGroup {
    private static final String TAG = "PreferenceCategory";

    @Override // android.preference.Preference
    public boolean isEnabled() {
        return false;
    }

    public PreferenceCategory(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
    }

    public PreferenceCategory(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public PreferenceCategory(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 16842892);
    }

    public PreferenceCategory(Context context) {
        this(context, null);
    }

    @Override // android.preference.PreferenceGroup
    protected boolean onPrepareAddPreference(Preference preference) {
        if (preference instanceof PreferenceCategory) {
            throw new IllegalArgumentException("Cannot add a PreferenceCategory directly to a PreferenceCategory");
        }
        return super.onPrepareAddPreference(preference);
    }

    @Override // android.preference.Preference
    public boolean shouldDisableDependents() {
        return !super.isEnabled();
    }
}
