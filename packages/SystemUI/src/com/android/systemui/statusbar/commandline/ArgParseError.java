package com.android.systemui.statusbar.commandline;

import androidx.compose.foundation.gestures.ContentInViewNode$Request$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.Intrinsics;

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
