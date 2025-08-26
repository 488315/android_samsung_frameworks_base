package com.android.compose.animation.scene;

import kotlin.jvm.internal.DefaultConstructorMarker;

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
