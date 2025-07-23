package com.samsung.android.sesl.transparentvideo.mediaplayer;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class IMediaPlayer$MediaError {
    public final String message;
    public final IMediaPlayer$ErrorType type;

    public IMediaPlayer$MediaError(IMediaPlayer$ErrorType iMediaPlayer$ErrorType, String str) {
        this.type = iMediaPlayer$ErrorType;
        this.message = str;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof IMediaPlayer$MediaError)) {
            return false;
        }
        IMediaPlayer$MediaError iMediaPlayer$MediaError = (IMediaPlayer$MediaError) obj;
        return this.type == iMediaPlayer$MediaError.type && Intrinsics.areEqual(this.message, iMediaPlayer$MediaError.message);
    }

    public final int hashCode() {
        int hashCode = this.type.hashCode() * 31;
        String str = this.message;
        return hashCode + (str == null ? 0 : str.hashCode());
    }

    public final String toString() {
        return "MediaError(type=" + this.type + ", message=" + this.message + ")";
    }
}
