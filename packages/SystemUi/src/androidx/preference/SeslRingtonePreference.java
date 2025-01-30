package androidx.preference;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.net.Uri;
import android.text.TextUtils;
import android.util.AttributeSet;
import androidx.reflect.p001os.SeslUserHandleReflector;
import com.android.systemui.R;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class SeslRingtonePreference extends Preference {
    public SeslRingtonePreference(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.RingtonePreference, i, i2);
        obtainStyledAttributes.getInt(0, 1);
        obtainStyledAttributes.getBoolean(1, true);
        obtainStyledAttributes.getBoolean(2, true);
        this.mIntent = new Intent("android.intent.action.RINGTONE_PICKER");
        SeslUserHandleReflector.myUserId();
        obtainStyledAttributes.recycle();
    }

    @Override // androidx.preference.Preference
    public final void onAttachedToHierarchy(PreferenceManager preferenceManager) {
        super.onAttachedToHierarchy(preferenceManager);
    }

    @Override // androidx.preference.Preference
    public final Object onGetDefaultValue(TypedArray typedArray, int i) {
        return typedArray.getString(i);
    }

    @Override // androidx.preference.Preference
    public final void onSetInitialValue(Object obj, boolean z) {
        String str = (String) obj;
        if (z || TextUtils.isEmpty(str)) {
            return;
        }
        Uri parse = Uri.parse(str);
        persistString(parse != null ? parse.toString() : "");
    }

    public SeslRingtonePreference(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public SeslRingtonePreference(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.ringtonePreferenceStyle);
    }

    public SeslRingtonePreference(Context context) {
        this(context, null);
    }
}
