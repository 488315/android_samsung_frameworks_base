package android.os;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.Executor;
import java.util.function.Consumer;

/* loaded from: classes3.dex */
public interface WearModeManagerInternal {
    public static final String OFFBODY_STATE_ID = "off_body";
    public static final String QUICK_DOZE_REQUEST_IDENTIFIER = "quick_doze_request";

    @Target({ElementType.TYPE_USE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Identifier {
    }

    <T> void addActiveStateChangeListener(String str, Executor executor, Consumer<T> consumer);
}
