package androidx.preference;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.net.Uri;
import android.text.TextUtils;
import android.util.AttributeSet;
import androidx.reflect.SeslBaseReflector;
import androidx.reflect.os.SeslUserHandleReflector;
import com.android.systemui.R;
import java.lang.reflect.Method;

/* loaded from: classes.dex */
public class SeslRingtonePreference extends Preference {
    public SeslRingtonePreference(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.RingtonePreference, i, i2);
        typedArrayObtainStyledAttributes.getInt(0, 1);
        typedArrayObtainStyledAttributes.getBoolean(1, true);
        typedArrayObtainStyledAttributes.getBoolean(2, true);
        this.mIntent = new Intent("android.intent.action.RINGTONE_PICKER");
        Method declaredMethod = SeslBaseReflector.getDeclaredMethod(SeslUserHandleReflector.mClass, "hidden_myUserId", new Class[0]);
        if (declaredMethod != null) {
            Object objInvoke = SeslBaseReflector.invoke(null, declaredMethod, new Object[0]);
            if (objInvoke instanceof Integer) {
                ((Integer) objInvoke).intValue();
            }
        }
        typedArrayObtainStyledAttributes.recycle();
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
        Uri uri = Uri.parse(str);
        persistString(uri != null ? uri.toString() : "");
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
