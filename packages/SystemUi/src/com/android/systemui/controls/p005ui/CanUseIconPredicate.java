package com.android.systemui.controls.p005ui;

import android.content.ContentProvider;
import android.graphics.drawable.Icon;
import android.net.Uri;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class CanUseIconPredicate implements Function1 {
    public final int currentUserId;

    public CanUseIconPredicate(int i) {
        this.currentUserId = i;
    }

    @Override // kotlin.jvm.functions.Function1
    public final Object invoke(Object obj) {
        boolean z;
        Icon icon = (Icon) obj;
        if (icon.getType() == 4 || icon.getType() == 6) {
            Uri uri = icon.getUri();
            int i = this.currentUserId;
            if (ContentProvider.getUserIdFromUri(uri, i) != i) {
                z = false;
                return Boolean.valueOf(z);
            }
        }
        z = true;
        return Boolean.valueOf(z);
    }
}
