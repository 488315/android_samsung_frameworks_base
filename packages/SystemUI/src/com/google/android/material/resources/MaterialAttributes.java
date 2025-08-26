package com.google.android.material.resources;

import android.content.Context;
import android.util.TypedValue;

/* loaded from: classes4.dex */
public class MaterialAttributes {
    public static TypedValue resolve(int i, Context context) {
        TypedValue typedValue = new TypedValue();
        if (context.getTheme().resolveAttribute(i, typedValue, true)) {
            return typedValue;
        }
        return null;
    }

    public static boolean resolveBoolean(Context context, int i, boolean z) {
        TypedValue typedValueResolve = resolve(i, context);
        return (typedValueResolve == null || typedValueResolve.type != 18) ? z : typedValueResolve.data != 0;
    }

    public static TypedValue resolveTypedValueOrThrow(Context context, String str, int i) {
        TypedValue typedValueResolve = resolve(i, context);
        if (typedValueResolve != null) {
            return typedValueResolve;
        }
        throw new IllegalArgumentException(String.format("%1$s requires a value for the %2$s attribute to be set in your app theme. You can either set the attribute in your theme or update your theme to inherit from Theme.MaterialComponents (or a descendant).", str, context.getResources().getResourceName(i)));
    }
}
