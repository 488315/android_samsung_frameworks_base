package com.google.android.material.appbar.model;

import android.content.Context;
import com.google.android.material.appbar.model.view.AppBarView;
import kotlin.jvm.KotlinReflectionNotSupportedError;
import kotlin.jvm.internal.ClassReference;
import kotlin.reflect.KClass;

/* loaded from: classes4.dex */
public class AppBarModel<T extends AppBarView> {
    private final Context context;
    private final KClass kclazz;

    public interface OnClickListener {
        void onClick();
    }

    public AppBarModel(KClass kClass, Context context) {
        this.kclazz = kClass;
        this.context = context;
    }

    public T create() {
        ((ClassReference) this.kclazz).getClass();
        throw new KotlinReflectionNotSupportedError();
    }
}
