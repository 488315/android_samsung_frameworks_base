package com.android.compose.animation.scene;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class ContentKey extends Key {
    public /* synthetic */ ContentKey(String str, Object obj, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, obj);
    }

    public abstract String getTestTag();

    private ContentKey(String str, Object obj) {
        super(str, obj, null);
    }

    public static /* synthetic */ void getTestTag$annotations() {
    }
}
