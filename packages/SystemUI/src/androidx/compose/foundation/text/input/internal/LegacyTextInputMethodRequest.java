package androidx.compose.foundation.text.input.internal;

import android.graphics.Rect;
import android.view.View;
import android.view.inputmethod.BaseInputConnection;
import androidx.compose.foundation.text.LegacyTextFieldState;
import androidx.compose.foundation.text.selection.TextFieldSelectionManager;
import androidx.compose.ui.platform.PlatformTextInputMethodRequest;
import androidx.compose.ui.platform.ViewConfiguration;
import androidx.compose.ui.text.TextRange;
import androidx.compose.ui.text.input.ImeAction;
import androidx.compose.ui.text.input.ImeOptions;
import androidx.compose.ui.text.input.TextFieldValue;
import java.util.ArrayList;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class LegacyTextInputMethodRequest implements PlatformTextInputMethodRequest {
    public final Lazy baseInputConnection$delegate;
    public final LegacyCursorAnchorInfoController cursorAnchorInfoController;
    public Rect focusedRect;
    public final List ics;
    public ImeOptions imeOptions;
    public final InputMethodManager inputMethodManager;
    public LegacyTextFieldState legacyTextFieldState;
    public Function1 onEditCommand = new Function1() { // from class: androidx.compose.foundation.text.input.internal.LegacyTextInputMethodRequest$onEditCommand$1
        @Override // kotlin.jvm.functions.Function1
        /* renamed from: invoke */
        public final /* bridge */ /* synthetic */ Object mo779invoke(Object obj) {
            return Unit.INSTANCE;
        }
    };
    public Function1 onImeActionPerformed = new Function1() { // from class: androidx.compose.foundation.text.input.internal.LegacyTextInputMethodRequest$onImeActionPerformed$1
        @Override // kotlin.jvm.functions.Function1
        /* renamed from: invoke */
        public final /* synthetic */ Object mo779invoke(Object obj) {
            int i = ((ImeAction) obj).value;
            return Unit.INSTANCE;
        }
    };
    public TextFieldValue state;
    public TextFieldSelectionManager textFieldSelectionManager;
    public final View view;
    public ViewConfiguration viewConfiguration;

    public LegacyTextInputMethodRequest(View view, Function1 function1, InputMethodManager inputMethodManager) {
        this.view = view;
        this.inputMethodManager = inputMethodManager;
        TextRange.Companion.getClass();
        this.state = new TextFieldValue("", TextRange.Zero, (TextRange) null, 4, (DefaultConstructorMarker) null);
        ImeOptions.Companion.getClass();
        this.imeOptions = ImeOptions.Default;
        this.ics = new ArrayList();
        this.baseInputConnection$delegate = LazyKt__LazyJVMKt.lazy(LazyThreadSafetyMode.NONE, new Function0() { // from class: androidx.compose.foundation.text.input.internal.LegacyTextInputMethodRequest$baseInputConnection$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return new BaseInputConnection(LegacyTextInputMethodRequest.this.view, false);
            }
        });
        this.cursorAnchorInfoController = new LegacyCursorAnchorInfoController(function1, inputMethodManager);
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x006b  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x00f7  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x010b  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0117  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x0171  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x0194  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x01ec  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x017e  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x00b9  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0051  */
    /* JADX WARN: Removed duplicated region for block: B:81:0x0071  */
    @Override // androidx.compose.ui.platform.PlatformTextInputMethodRequest
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final android.view.inputmethod.InputConnection createInputConnection(android.view.inputmethod.EditorInfo r18) {
        /*
            Method dump skipped, instructions count: 550
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.text.input.internal.LegacyTextInputMethodRequest.createInputConnection(android.view.inputmethod.EditorInfo):android.view.inputmethod.InputConnection");
    }
}
