package com.samsung.android.knox.lockscreen;

import android.content.Context;
import android.util.Log;
import android.view.View;
import com.android.keyguard.EmergencyButton$$ExternalSyntheticOutline0;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

/* loaded from: classes4.dex */
public class LSOWidgetView {
    public static final String TAG = "LSO";

    /* JADX WARN: Removed duplicated region for block: B:24:0x0056 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0057  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static View getWidget(Context context, LSOItemWidget lSOItemWidget) throws IllegalAccessException, NoSuchMethodException, ClassNotFoundException, SecurityException, IllegalArgumentException, InvocationTargetException {
        Class<?> cls;
        View view;
        String widget = lSOItemWidget.getWidget();
        if (widget == null || widget.length() == 0) {
            return null;
        }
        try {
            cls = Class.forName(widget);
        } catch (ClassNotFoundException unused) {
            cls = null;
        } catch (Exception e) {
            e = e;
            cls = null;
        }
        try {
        } catch (ClassNotFoundException unused2) {
            Log.e("LSO", widget.concat(" Class not found Exception: "));
            view = null;
            if (view == null) {
            }
        } catch (Exception e2) {
            e = e2;
            EmergencyButton$$ExternalSyntheticOutline0.m("Unhandled Exception: ", e, "LSO");
            view = null;
            if (view == null) {
            }
        }
        if (!View.class.isAssignableFrom(cls)) {
            Log.d("LSO", widget.concat(" directly/indirectly not inherited from View object"));
            return null;
        }
        view = (View) cls.getConstructor(Context.class).newInstance(context);
        if (view == null) {
            return null;
        }
        LSOAttributeSet attrs = lSOItemWidget.getAttrs();
        if (attrs.size() > 0) {
            try {
                Method method = cls.getMethod("setAttribute", String.class, Object.class);
                Object[] objArr = new Object[2];
                if (lSOItemWidget.isFieldUpdated(32)) {
                    objArr[0] = LSOAttrConst.ATTR_GRAVITY;
                    objArr[1] = Integer.valueOf(lSOItemWidget.getGravity());
                    method.invoke(view, objArr);
                }
                for (Map.Entry<String, Object> entry : attrs.valueSet()) {
                    objArr[0] = entry.getKey();
                    objArr[1] = entry.getValue();
                    method.invoke(view, objArr);
                }
            } catch (NoSuchMethodException e3) {
                Log.e("LSO", widget + " does not support method setAttribute(String,Object) : " + e3);
            } catch (Exception e4) {
                EmergencyButton$$ExternalSyntheticOutline0.m("Exception: ", e4, "LSO");
            }
        }
        return view;
    }
}
