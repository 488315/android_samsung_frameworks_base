package com.samsung.android.sdk.routines.automationservice.data;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class MetaInfo {
    public static final Companion Companion = new Companion(null);
    public final String packageName;
    public final String tag;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
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
