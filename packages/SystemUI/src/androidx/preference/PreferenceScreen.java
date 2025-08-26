package androidx.preference;

import android.content.Context;
import android.util.AttributeSet;
import androidx.core.content.res.TypedArrayUtils;
import androidx.preference.PreferenceManager;
import com.android.systemui.R;

/* loaded from: classes.dex */
public final class PreferenceScreen extends PreferenceGroup {
    public final boolean mShouldUseGeneratedIds;

    public PreferenceScreen(Context context, AttributeSet attributeSet) {
        super(context, attributeSet, TypedArrayUtils.getAttr(context, R.attr.preferenceScreenStyle, android.R.attr.preferenceScreenStyle));
        this.mShouldUseGeneratedIds = true;
    }

    @Override // androidx.preference.Preference
    public final void onClick() {
        PreferenceManager.OnNavigateToScreenListener onNavigateToScreenListener;
        if (this.mIntent != null || this.mFragment != null || getPreferenceCount() == 0 || (onNavigateToScreenListener = this.mPreferenceManager.mOnNavigateToScreenListener) == null) {
            return;
        }
        onNavigateToScreenListener.onNavigateToScreen(this);
    }
}
