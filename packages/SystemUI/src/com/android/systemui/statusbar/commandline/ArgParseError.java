package com.android.systemui.statusbar.commandline;

import androidx.compose.foundation.gestures.ContentInViewNode$Request$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class ArgParseError extends Exception {
    private final String message;

    public ArgParseError(String str) {
        super(str);
        this.message = str;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof ArgParseError) && Intrinsics.areEqual(this.message, ((ArgParseError) obj).message);
    }

    @Override // java.lang.Throwable
    public final String getMessage() {
        return this.message;
    }

    public final int hashCode() {
        return this.message.hashCode();
    }

    @Override // java.lang.Throwable
    public final String toString() {
        return ContentInViewNode$Request$$ExternalSyntheticOutline0.m("ArgParseError(message=", this.message, ")");
    }
}
