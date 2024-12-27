package android.view.inputmethod;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public interface ConnectionlessHandwritingCallback {
    public static final int CONNECTIONLESS_HANDWRITING_ERROR_NO_TEXT_RECOGNIZED = 0;
    public static final int CONNECTIONLESS_HANDWRITING_ERROR_OTHER = 2;
    public static final int CONNECTIONLESS_HANDWRITING_ERROR_UNSUPPORTED = 1;

    @Retention(RetentionPolicy.SOURCE)
    public @interface ConnectionlessHandwritingError {}

    void onError(int i);

    void onResult(CharSequence charSequence);
}
