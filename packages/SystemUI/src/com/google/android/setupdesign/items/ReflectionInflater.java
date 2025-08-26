package com.google.android.setupdesign.items;

import android.content.Context;
import android.util.AttributeSet;
import android.view.InflateException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

/* loaded from: classes4.dex */
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
    public final Object onCreateItem(String str, AttributeSet attributeSet) throws IllegalAccessException, NoSuchMethodException, InstantiationException, SecurityException, IllegalArgumentException, InvocationTargetException {
        String str2 = this.defaultPackage;
        Object[] objArr = this.tempConstructorArgs;
        String strConcat = (str2 == null || str.indexOf(46) != -1) ? str : str2.concat(str);
        HashMap map = constructorMap;
        Constructor<?> constructor = (Constructor) map.get(strConcat);
        if (constructor == null) {
            try {
                constructor = this.context.getClassLoader().loadClass(strConcat).getConstructor(CONSTRUCTOR_SIGNATURE);
                constructor.setAccessible(true);
                map.put(str, constructor);
            } catch (Exception e) {
                throw new InflateException(attributeSet.getPositionDescription() + ": Error inflating class " + strConcat, e);
            }
        }
        objArr[0] = this.context;
        objArr[1] = attributeSet;
        Object objNewInstance = constructor.newInstance(objArr);
        objArr[0] = null;
        objArr[1] = null;
        return objNewInstance;
    }
}
