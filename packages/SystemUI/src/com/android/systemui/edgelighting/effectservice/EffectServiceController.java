package com.android.systemui.edgelighting.effectservice;

import android.app.PendingIntent;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Slog;
import android.view.LayoutInflater;
import androidx.compose.animation.ChangeSize$$ExternalSyntheticOutline0;
import com.android.systemui.edgelighting.effect.data.EdgeEffectInfo;
import com.android.systemui.edgelighting.effectservice.EdgeLightingDispatcher;
import com.android.systemui.edgelighting.reflection.AbsEdgeLightingEffectReflection;
import com.android.systemui.edgelighting.reflection.EffectInfoReflection;
import com.android.systemui.edgelighting.reflection.content.ContextReflection;
import com.android.systemui.edgelighting.reflection.content.ReflectionContentContainer;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class EffectServiceController {
    public final AbsEdgeLightingEffectReflection mAbsEdgeLightingEffectReflection;
    public final ClassLoader mClassLoader;
    public final String mComponentName;
    public final String mPackage;
    public boolean mStarting = false;

    public EffectServiceController(Context context, String str, String str2) {
        AbsEdgeLightingEffectReflection absEdgeLightingEffectReflection = null;
        try {
            Slog.i("EffectServiceController", "createEdgeLightingEffect: " + str + " " + str2);
            if (str != null && str2 != null) {
                this.mPackage = str;
                this.mComponentName = str2;
                clearInflaterConstructMap(str);
                if (ReflectionContentContainer.sContextReflection == null) {
                    ReflectionContentContainer.sContextReflection = new ContextReflection();
                }
                Context createPackageContextAsUser = ReflectionContentContainer.sContextReflection.createPackageContextAsUser(context, this.mPackage);
                if (createPackageContextAsUser != null) {
                    this.mClassLoader = createPackageContextAsUser.getClassLoader();
                }
                absEdgeLightingEffectReflection = new AbsEdgeLightingEffectReflection(Class.forName(this.mComponentName, true, this.mClassLoader), createPackageContextAsUser, context, this.mClassLoader);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.mAbsEdgeLightingEffectReflection = absEdgeLightingEffectReflection;
    }

    public static void clearInflaterConstructMap(String str) {
        HashMap hashMap;
        Slog.i("EffectServiceController", "clearInflaterConstructMap packageName=" + str);
        if (str == null) {
            return;
        }
        try {
            Field declaredField = Class.forName(LayoutInflater.class.getName()).getDeclaredField("sConstructorMap");
            declaredField.setAccessible(true);
            ArrayList arrayList = new ArrayList();
            try {
                hashMap = (HashMap) declaredField.get("");
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                hashMap = null;
            }
            for (String str2 : hashMap.keySet()) {
                if (str2 != null && str2.contains(str)) {
                    arrayList.add(str2);
                }
            }
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                hashMap.remove((String) it.next());
            }
        } catch (ClassNotFoundException e2) {
            e2.printStackTrace();
        } catch (NoSuchFieldException e3) {
            e3.printStackTrace();
        }
    }

    public final EffectInfoReflection convertEffectInfo(EdgeEffectInfo edgeEffectInfo) {
        Class<?> cls;
        try {
            cls = Class.forName("com.samsung.android.sdk.edgelighting.AbsEdgeLightingEffect$EffectInfo", true, this.mClassLoader);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            cls = null;
        }
        EffectInfoReflection effectInfoReflection = new EffectInfoReflection(cls);
        int[] iArr = edgeEffectInfo.mEffectColors;
        Object obj = effectInfoReflection.mInstance;
        effectInfoReflection.invokeNormalMethod(obj, "setColors", new Class[]{int[].class}, iArr);
        effectInfoReflection.invokeNormalMethod(obj, "setApplicationIcon", new Class[]{Drawable.class}, edgeEffectInfo.mAppIcon);
        effectInfoReflection.invokeNormalMethod(obj, "setTickerText", new Class[]{String[].class}, edgeEffectInfo.mText);
        boolean z = edgeEffectInfo.mIsBlackBG;
        Class cls2 = Boolean.TYPE;
        effectInfoReflection.invokeNormalMethod(obj, "setBlackBG", new Class[]{cls2}, Boolean.valueOf(z));
        effectInfoReflection.invokeNormalMethod(obj, "setPendingIntent", new Class[]{PendingIntent.class}, edgeEffectInfo.mPendingIntent);
        effectInfoReflection.invokeNormalMethod(obj, "setInfinite", new Class[]{cls2}, Boolean.valueOf(edgeEffectInfo.mInfiniteLighting));
        effectInfoReflection.invokeNormalMethod(obj, "setDuration", new Class[]{Integer.TYPE}, Integer.valueOf((int) edgeEffectInfo.mLightingDuration));
        float f = edgeEffectInfo.mStrokeWidth;
        Class cls3 = Float.TYPE;
        effectInfoReflection.invokeNormalMethod(obj, "setStrokeWidth", new Class[]{cls3}, Float.valueOf(f));
        effectInfoReflection.invokeNormalMethod(obj, "setStrokeAlpha", new Class[]{cls3}, Float.valueOf(edgeEffectInfo.mStrokeAlpha));
        effectInfoReflection.invokeNormalMethod(obj, "setNotificationKey", new Class[]{String.class}, edgeEffectInfo.mNotificationKey);
        return effectInfoReflection;
    }

    public final void dispatchStart(EdgeEffectInfo edgeEffectInfo) {
        Class<?> cls;
        Slog.i("EffectServiceController", "dispatchStart");
        EffectInfoReflection convertEffectInfo = convertEffectInfo(edgeEffectInfo);
        AbsEdgeLightingEffectReflection absEdgeLightingEffectReflection = this.mAbsEdgeLightingEffectReflection;
        try {
            cls = Class.forName("com.samsung.android.sdk.edgelighting.AbsEdgeLightingEffect$EffectInfo", true, absEdgeLightingEffectReflection.mClassLoader);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            cls = null;
        }
        absEdgeLightingEffectReflection.invokeNormalMethod(absEdgeLightingEffectReflection.mInstance, NetworkAnalyticsConstants.DataPoints.OPEN_TIME, new Class[]{cls}, convertEffectInfo.mInstance);
        this.mStarting = true;
    }

    public final void dispatchStop() {
        Slog.i("EffectServiceController", "dispatchStop");
        if (!this.mStarting) {
            Slog.w("EffectServiceController", "dispatchStop failed because of mStarting is false.");
            return;
        }
        AbsEdgeLightingEffectReflection absEdgeLightingEffectReflection = this.mAbsEdgeLightingEffectReflection;
        absEdgeLightingEffectReflection.invokeNormalMethod(absEdgeLightingEffectReflection.mInstance, "stop", null, new Object[0]);
        this.mStarting = false;
    }

    public final void setOnEventListener(EdgeLightingDispatcher.AnonymousClass1 anonymousClass1) {
        Class<?> cls;
        AbsEdgeLightingEffectReflection absEdgeLightingEffectReflection = this.mAbsEdgeLightingEffectReflection;
        try {
            cls = Class.forName("com.samsung.android.sdk.edgelighting.AbsEdgeLightingEffect$IEdgeLIghtingEffectCallback", true, absEdgeLightingEffectReflection.mClassLoader);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            cls = null;
        }
        absEdgeLightingEffectReflection.invokeNormalMethod(absEdgeLightingEffectReflection.mInstance, "setOnCallback", new Class[]{cls}, anonymousClass1 != null ? anonymousClass1.mProxyInstance : null);
    }

    public final String toString() {
        return ChangeSize$$ExternalSyntheticOutline0.m(new StringBuilder("{EffectServiceController:,mStarting="), this.mStarting, '}');
    }
}
