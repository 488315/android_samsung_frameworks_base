package android.text.method;

import android.content.Context;
import android.graphics.Rect;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import java.util.Locale;

/* loaded from: classes4.dex */
public class AllCapsTransformationMethod implements TransformationMethod2 {
    private static final String TAG = "AllCapsTransformationMethod";
    private boolean mEnabled;
    private Locale mLocale;

    @Override // android.text.method.TransformationMethod
    public void onFocusChanged(View view, CharSequence charSequence, boolean z, int i, Rect rect) {
    }

    public AllCapsTransformationMethod(Context context) {
        this.mLocale = context.getResources().getConfiguration().getLocales().get(0);
    }

    @Override // android.text.method.TransformationMethod
    public CharSequence getTransformation(CharSequence charSequence, View view) {
        if (!this.mEnabled) {
            Log.w(TAG, "Caller did not enable length changes; not transforming text");
            return charSequence;
        }
        if (charSequence == null) {
            return null;
        }
        Locale textLocale = view instanceof TextView ? ((TextView) view).getTextLocale() : null;
        if (textLocale == null) {
            textLocale = this.mLocale;
        }
        return TextUtils.toUpperCase(textLocale, charSequence, charSequence instanceof Spanned);
    }

    @Override // android.text.method.TransformationMethod2
    public void setLengthChangesAllowed(boolean z) {
        this.mEnabled = z;
    }
}
