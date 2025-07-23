package androidx.preference;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import androidx.core.content.res.TypedArrayUtils;
import androidx.preference.PreferenceManager;
import com.android.systemui.R;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class DialogPreference extends Preference {
    public final Drawable mDialogIcon;
    public final int mDialogLayoutResId;
    public final CharSequence mDialogMessage;
    public final CharSequence mDialogTitle;
    public final CharSequence mNegativeButtonText;
    public final CharSequence mPositiveButtonText;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface TargetFragment {
        Preference findPreference(CharSequence charSequence);
    }

    public DialogPreference(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.DialogPreference, i, i2);
        String string = obtainStyledAttributes.getString(9);
        string = string == null ? obtainStyledAttributes.getString(0) : string;
        this.mDialogTitle = string;
        if (string == null) {
            this.mDialogTitle = this.mTitle;
        }
        String string2 = obtainStyledAttributes.getString(8);
        this.mDialogMessage = string2 == null ? obtainStyledAttributes.getString(1) : string2;
        Drawable drawable = obtainStyledAttributes.getDrawable(6);
        this.mDialogIcon = drawable == null ? obtainStyledAttributes.getDrawable(2) : drawable;
        String string3 = obtainStyledAttributes.getString(11);
        this.mPositiveButtonText = string3 == null ? obtainStyledAttributes.getString(3) : string3;
        String string4 = obtainStyledAttributes.getString(10);
        this.mNegativeButtonText = string4 == null ? obtainStyledAttributes.getString(4) : string4;
        this.mDialogLayoutResId = obtainStyledAttributes.getResourceId(7, obtainStyledAttributes.getResourceId(5, 0));
        obtainStyledAttributes.recycle();
    }

    @Override // androidx.preference.Preference
    public void onClick() {
        PreferenceManager.OnDisplayPreferenceDialogListener onDisplayPreferenceDialogListener = this.mPreferenceManager.mOnDisplayPreferenceDialogListener;
        if (onDisplayPreferenceDialogListener != null) {
            onDisplayPreferenceDialogListener.onDisplayPreferenceDialog(this);
        }
    }

    public DialogPreference(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public DialogPreference(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, TypedArrayUtils.getAttr(context, R.attr.dialogPreferenceStyle, android.R.attr.dialogPreferenceStyle));
    }

    public DialogPreference(Context context) {
        this(context, null);
    }
}
