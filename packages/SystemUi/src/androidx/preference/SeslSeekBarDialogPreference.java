package androidx.preference;

import android.R;
import android.content.Context;
import android.util.AttributeSet;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
class SeslSeekBarDialogPreference extends DialogPreference {
    public SeslSeekBarDialogPreference(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mPositiveButtonText = this.mContext.getString(R.string.ok);
        this.mNegativeButtonText = this.mContext.getString(R.string.cancel);
        this.mDialogIcon = null;
    }

    public SeslSeekBarDialogPreference(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public SeslSeekBarDialogPreference(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, com.android.systemui.R.attr.seekBarDialogPreferenceStyle);
    }

    public SeslSeekBarDialogPreference(Context context) {
        this(context, null);
    }
}
