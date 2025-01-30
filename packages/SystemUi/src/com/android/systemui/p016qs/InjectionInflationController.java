package com.android.systemui.p016qs;

import android.content.Context;
import android.util.ArrayMap;
import android.util.AttributeSet;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import androidx.constraintlayout.motion.widget.KeyAttributes$$ExternalSyntheticOutline0;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class InjectionInflationController {
    public final InjectionFactory mFactory;
    public final ArrayMap mInjectionMap = new ArrayMap();
    public final ViewInstanceCreator.Factory mViewInstanceCreatorFactory;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class InjectionFactory implements LayoutInflater.Factory2 {
        public /* synthetic */ InjectionFactory(InjectionInflationController injectionInflationController, int i) {
            this();
        }

        @Override // android.view.LayoutInflater.Factory
        public final View onCreateView(String str, Context context, AttributeSet attributeSet) {
            Method method = (Method) InjectionInflationController.this.mInjectionMap.get(str);
            if (method == null) {
                return null;
            }
            try {
                return (View) method.invoke(InjectionInflationController.this.mViewInstanceCreatorFactory.build(context, attributeSet), new Object[0]);
            } catch (IllegalAccessException e) {
                throw new InflateException(KeyAttributes$$ExternalSyntheticOutline0.m21m("Could not inflate ", str), e);
            } catch (InvocationTargetException e2) {
                throw new InflateException(KeyAttributes$$ExternalSyntheticOutline0.m21m("Could not inflate ", str), e2);
            }
        }

        private InjectionFactory() {
        }

        @Override // android.view.LayoutInflater.Factory2
        public final View onCreateView(View view, String str, Context context, AttributeSet attributeSet) {
            return onCreateView(str, context, attributeSet);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface ViewInstanceCreator {

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public interface Factory {
            ViewInstanceCreator build(Context context, AttributeSet attributeSet);
        }
    }

    public InjectionInflationController(ViewInstanceCreator.Factory factory) {
        int i = 0;
        this.mFactory = new InjectionFactory(this, i);
        this.mViewInstanceCreatorFactory = factory;
        Method[] declaredMethods = ViewInstanceCreator.class.getDeclaredMethods();
        int length = declaredMethods.length;
        while (i < length) {
            Method method = declaredMethods[i];
            if (View.class.isAssignableFrom(method.getReturnType()) && (method.getModifiers() & 1) != 0) {
                this.mInjectionMap.put(method.getReturnType().getName(), method);
            }
            i++;
        }
    }
}
