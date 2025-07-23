package com.android.systemui.controls.ui;

import android.content.ContentProvider;
import android.graphics.drawable.Icon;
import android.net.Uri;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class CanUseIconPredicate implements Function1 {
    public final int currentUserId;

    public CanUseIconPredicate(int i) {
        this.currentUserId = i;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        Icon icon = (Icon) obj;
        boolean z = true;
        if (icon.getType() == 4 || icon.getType() == 6) {
            Uri uri = icon.getUri();
            int i = this.currentUserId;
            if (ContentProvider.getUserIdFromUri(uri, i) != i) {
                z = false;
            }
        }
        return Boolean.valueOf(z);
    }
}
