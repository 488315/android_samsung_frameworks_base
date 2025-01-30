package com.google.android.material.resources;

import android.content.Context;
import android.util.TypedValue;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class MaterialAttributes {
    public static TypedValue resolve(int i, Context context) {
        TypedValue typedValue = new TypedValue();
        if (context.getTheme().resolveAttribute(i, typedValue, true)) {
            return typedValue;
        }
        return null;
    }

    public static boolean resolveBoolean(Context context, int i, boolean z) {
        TypedValue resolve = resolve(i, context);
        return (resolve == null || resolve.type != 18) ? z : resolve.data != 0;
    }

    public static TypedValue resolveTypedValueOrThrow(Context context, String str, int i) {
        TypedValue resolve = resolve(i, context);
        if (resolve != null) {
            return resolve;
        }
        throw new IllegalArgumentException(String.format("%1$s requires a value for the %2$s attribute to be set in your app theme. You can either set the attribute in your theme or update your theme to inherit from Theme.MaterialComponents (or a descendant).", str, context.getResources().getResourceName(i)));
    }
}
