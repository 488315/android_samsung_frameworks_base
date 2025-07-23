package androidx.navigation;

import android.content.Intent;
import android.net.Uri;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class NavDeepLinkRequest {
    public final String action;
    public final String mimeType;
    public final Uri uri;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Builder {
        public static final Companion Companion = new Companion(null);
        public Uri uri;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        public final class Companion {
            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            private Companion() {
            }
        }

        public /* synthetic */ Builder(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Builder() {
        }
    }

    public NavDeepLinkRequest(Uri uri, String str, String str2) {
        this.uri = uri;
        this.action = str;
        this.mimeType = str2;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("NavDeepLinkRequest{");
        if (this.uri != null) {
            sb.append(" uri=");
            sb.append(String.valueOf(this.uri));
        }
        String str = this.action;
        if (str != null) {
            sb.append(" action=");
            sb.append(str);
        }
        String str2 = this.mimeType;
        if (str2 != null) {
            sb.append(" mimetype=");
            sb.append(str2);
        }
        sb.append(" }");
        return sb.toString();
    }

    public NavDeepLinkRequest(Intent intent) {
        this(intent.getData(), intent.getAction(), intent.getType());
    }
}
