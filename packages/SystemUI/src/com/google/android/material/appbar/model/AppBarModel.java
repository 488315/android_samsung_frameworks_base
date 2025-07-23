package com.google.android.material.appbar.model;

import android.content.Context;
import com.google.android.material.appbar.model.view.AppBarView;
import kotlin.jvm.KotlinReflectionNotSupportedError;
import kotlin.jvm.internal.ClassReference;
import kotlin.reflect.KClass;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class AppBarModel<T extends AppBarView> {
    private final Context context;
    private final KClass kclazz;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
