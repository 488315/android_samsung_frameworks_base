package com.google.android.setupdesign.template;

import android.os.Handler;
import android.os.Looper;
import com.google.android.setupcompat.internal.TemplateLayout;
import com.google.android.setupcompat.template.Mixin;

/* loaded from: classes4.dex */
public class RequireScrollMixin implements Mixin {
    public final Handler handler = new Handler(Looper.getMainLooper());

    public RequireScrollMixin(TemplateLayout templateLayout) {
    }
}
