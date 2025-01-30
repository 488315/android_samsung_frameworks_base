package com.samsung.android.knox.lockscreen;

import android.content.Context;
import android.util.Log;
import android.view.View;
import com.android.keyguard.EmergencyButton$$ExternalSyntheticOutline0;
import java.lang.reflect.Method;
import java.util.Map;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class LSOWidgetView {
    public static final String TAG = "LSO";

    /* JADX WARN: Removed duplicated region for block: B:17:0x0053 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:18:0x0054  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static View getWidget(Context context, LSOItemWidget lSOItemWidget) {
        Class<?> cls;
        View view;
        String str = lSOItemWidget.packageName;
        if (str == null || str.length() == 0) {
            return null;
        }
        try {
            cls = Class.forName(str);
        } catch (ClassNotFoundException unused) {
            cls = null;
        } catch (Exception e) {
            e = e;
            cls = null;
        }
        try {
        } catch (ClassNotFoundException unused2) {
            Log.e("LSO", str.concat(" Class not found Exception: "));
            view = null;
            if (view == null) {
            }
        } catch (Exception e2) {
            e = e2;
            EmergencyButton$$ExternalSyntheticOutline0.m58m("Unhandled Exception: ", e, "LSO");
            view = null;
            if (view == null) {
            }
        }
        if (!View.class.isAssignableFrom(cls)) {
            Log.d("LSO", str.concat(" directly/indirectly not inherited from View object"));
            return null;
        }
        view = (View) cls.getConstructor(Context.class).newInstance(context);
        if (view == null) {
            return null;
        }
        LSOAttributeSet attrs = lSOItemWidget.getAttrs();
        if (attrs.size() <= 0) {
            return view;
        }
        try {
            Method method = cls.getMethod("setAttribute", String.class, Object.class);
            Object[] objArr = new Object[2];
            if (lSOItemWidget.isFieldUpdated(32)) {
                objArr[0] = LSOAttrConst.ATTR_GRAVITY;
                objArr[1] = Integer.valueOf(lSOItemWidget.gravity);
                method.invoke(view, objArr);
            }
            for (Map.Entry<String, Object> entry : attrs.valueSet()) {
                objArr[0] = entry.getKey();
                objArr[1] = entry.getValue();
                method.invoke(view, objArr);
            }
        } catch (NoSuchMethodException e3) {
            Log.e("LSO", str + " does not support method setAttribute(String,Object) : " + e3);
        } catch (Exception e4) {
            EmergencyButton$$ExternalSyntheticOutline0.m58m("Exception: ", e4, "LSO");
        }
        return view;
    }
}
