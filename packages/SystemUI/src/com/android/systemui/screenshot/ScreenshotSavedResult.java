package com.android.systemui.screenshot;

import android.net.Uri;
import android.os.UserHandle;
import android.support.v4.media.session.MediaSessionCompat$QueueItem$$ExternalSyntheticOutline0;
import java.text.DateFormat;
import java.util.Arrays;
import java.util.Date;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;

public final class ScreenshotSavedResult {
    public final long imageTime;
    public final String subject;
    public final Uri uri;
    public final UserHandle user;

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public ScreenshotSavedResult(Uri uri, UserHandle userHandle, long j) {
        this.uri = uri;
        this.user = userHandle;
        this.imageTime = j;
        String format = DateFormat.getDateTimeInstance().format(new Date(j));
        int i = StringCompanionObject.$r8$clinit;
        this.subject = String.format("Screenshot (%s)", Arrays.copyOf(new Object[]{format}, 1));
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ScreenshotSavedResult)) {
            return false;
        }
        ScreenshotSavedResult screenshotSavedResult = (ScreenshotSavedResult) obj;
        return Intrinsics.areEqual(this.uri, screenshotSavedResult.uri) && Intrinsics.areEqual(this.user, screenshotSavedResult.user) && this.imageTime == screenshotSavedResult.imageTime;
    }

    public final int hashCode() {
        return Long.hashCode(this.imageTime) + ((this.user.hashCode() + (this.uri.hashCode() * 31)) * 31);
    }

    public final String toString() {
        Uri uri = this.uri;
        UserHandle userHandle = this.user;
        StringBuilder sb = new StringBuilder("ScreenshotSavedResult(uri=");
        sb.append(uri);
        sb.append(", user=");
        sb.append(userHandle);
        sb.append(", imageTime=");
        return MediaSessionCompat$QueueItem$$ExternalSyntheticOutline0.m(this.imageTime, ")", sb);
    }
}
