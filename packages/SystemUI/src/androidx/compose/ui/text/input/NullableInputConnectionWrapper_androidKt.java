package androidx.compose.ui.text.input;

import android.view.inputmethod.InputConnection;
import kotlin.jvm.functions.Function1;

/* loaded from: classes.dex */
public abstract class NullableInputConnectionWrapper_androidKt {
    public static final NullableInputConnectionWrapper NullableInputConnectionWrapper(InputConnection inputConnection, Function1 function1) {
        return new NullableInputConnectionWrapperApi34(inputConnection, function1);
    }
}
