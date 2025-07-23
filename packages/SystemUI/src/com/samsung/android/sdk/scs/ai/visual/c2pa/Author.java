package com.samsung.android.sdk.scs.ai.visual.c2pa;

import androidx.compose.runtime.OpaqueKey$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class Author {
    private final String name;

    public Author(String str) {
        this.name = str;
    }

    public static /* synthetic */ Author copy$default(Author author, String str, int i, Object obj) {
        if ((i & 1) != 0) {
            str = author.name;
        }
        return author.copy(str);
    }

    public final String component1() {
        return this.name;
    }

    public final Author copy(String str) {
        return new Author(str);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof Author) && Intrinsics.areEqual(this.name, ((Author) obj).name);
    }

    public final String getName() {
        return this.name;
    }

    public int hashCode() {
        String str = this.name;
        if (str == null) {
            return 0;
        }
        return str.hashCode();
    }

    public String toString() {
        return OpaqueKey$$ExternalSyntheticOutline0.m(new StringBuilder("Author(name="), this.name, ')');
    }
}
