package androidx.compose.ui.text.input;

import android.view.View;
import androidx.core.view.SoftwareKeyboardControllerCompat;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class InputMethodManagerImpl implements InputMethodManager {
    public final Lazy imm$delegate = LazyKt__LazyJVMKt.lazy(LazyThreadSafetyMode.NONE, new Function0() { // from class: androidx.compose.ui.text.input.InputMethodManagerImpl$imm$2
        {
            super(0);
        }

        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return (android.view.inputmethod.InputMethodManager) InputMethodManagerImpl.this.view.getContext().getSystemService("input_method");
        }
    });
    public final SoftwareKeyboardControllerCompat softwareKeyboardControllerCompat;
    public final View view;

    public InputMethodManagerImpl(View view) {
        this.view = view;
        this.softwareKeyboardControllerCompat = new SoftwareKeyboardControllerCompat(view);
    }
}
