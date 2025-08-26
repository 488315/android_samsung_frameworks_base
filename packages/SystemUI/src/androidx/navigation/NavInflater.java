package androidx.navigation;

import android.content.Context;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
public final class NavInflater {

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
        new ThreadLocal();
    }

    public NavInflater(Context context, NavigatorProvider navigatorProvider) {
    }
}
