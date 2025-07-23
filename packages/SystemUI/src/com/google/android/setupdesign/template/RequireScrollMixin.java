package com.google.android.setupdesign.template;

import android.os.Handler;
import android.os.Looper;
import com.google.android.setupcompat.internal.TemplateLayout;
import com.google.android.setupcompat.template.Mixin;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class RequireScrollMixin implements Mixin {
    public final Handler handler = new Handler(Looper.getMainLooper());

    public RequireScrollMixin(TemplateLayout templateLayout) {
    }
}
