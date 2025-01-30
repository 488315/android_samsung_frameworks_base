package com.google.android.setupdesign.items;

import android.content.Context;
import android.util.AttributeSet;
import android.view.InflateException;
import java.lang.reflect.Constructor;
import java.util.HashMap;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public abstract class ReflectionInflater extends SimpleInflater {
    public static final Class[] CONSTRUCTOR_SIGNATURE = {Context.class, AttributeSet.class};
    public static final HashMap constructorMap = new HashMap();
    public final Context context;
    public String defaultPackage;
    public final Object[] tempConstructorArgs;

    public ReflectionInflater(Context context) {
        super(context.getResources());
        this.tempConstructorArgs = new Object[2];
        this.context = context;
    }

    @Override // com.google.android.setupdesign.items.SimpleInflater
    public final Object onCreateItem(String str, AttributeSet attributeSet) {
        String str2 = this.defaultPackage;
        Object[] objArr = this.tempConstructorArgs;
        String concat = (str2 == null || str.indexOf(46) != -1) ? str : str2.concat(str);
        HashMap hashMap = constructorMap;
        Constructor<?> constructor = (Constructor) hashMap.get(concat);
        Context context = this.context;
        if (constructor == null) {
            try {
                constructor = context.getClassLoader().loadClass(concat).getConstructor(CONSTRUCTOR_SIGNATURE);
                constructor.setAccessible(true);
                hashMap.put(str, constructor);
            } catch (Exception e) {
                throw new InflateException(attributeSet.getPositionDescription() + ": Error inflating class " + concat, e);
            }
        }
        objArr[0] = context;
        objArr[1] = attributeSet;
        Object newInstance = constructor.newInstance(objArr);
        objArr[0] = null;
        objArr[1] = null;
        return newInstance;
    }
}
