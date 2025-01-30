package com.samsung.android.sdk.routines.automationservice.data;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class MetaInfo {
    public static final Companion Companion = new Companion(null);
    public final String packageName;
    public final String tag;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public /* synthetic */ MetaInfo(String str, String str2, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, str2);
    }

    public final String toString() {
        return this.packageName + '%' + this.tag;
    }

    private MetaInfo(String str, String str2) {
        this.packageName = str;
        this.tag = str2;
    }
}
