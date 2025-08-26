package com.android.internal.os;

import android.os.Binder;
import android.text.TextUtils;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;

/* loaded from: classes5.dex */
public class BinderTransactionNameResolver {
    private static final Method NO_GET_DEFAULT_TRANSACTION_NAME_METHOD;
    private static final boolean USE_TRANSACTION_CODES_FOR_UNKNOWN_METHODS = Flags.useTransactionCodesForUnknownMethods();
    private final HashMap<Class<? extends Binder>, Method> mGetDefaultTransactionNameMethods = new HashMap<>();

    static {
        try {
            NO_GET_DEFAULT_TRANSACTION_NAME_METHOD = BinderTransactionNameResolver.class.getMethod("noDefaultTransactionName", Integer.TYPE);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    public static String noDefaultTransactionName(int i) {
        return String.valueOf(i);
    }

    public String getMethodName(Class<? extends Binder> cls, int i) throws NoSuchMethodException, SecurityException {
        Method method = this.mGetDefaultTransactionNameMethods.get(cls);
        if (method == null) {
            try {
                method = cls.getMethod("getDefaultTransactionName", Integer.TYPE);
            } catch (NoSuchMethodException unused) {
                method = NO_GET_DEFAULT_TRANSACTION_NAME_METHOD;
            }
            if (method.getReturnType() != String.class || !Modifier.isStatic(method.getModifiers())) {
                method = NO_GET_DEFAULT_TRANSACTION_NAME_METHOD;
            }
            this.mGetDefaultTransactionNameMethods.put(cls, method);
        }
        try {
            String str = (String) method.invoke(null, Integer.valueOf(i));
            return (USE_TRANSACTION_CODES_FOR_UNKNOWN_METHODS && TextUtils.isEmpty(str)) ? String.valueOf(i) : str;
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}
