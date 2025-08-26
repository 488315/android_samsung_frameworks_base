package androidx.compose.foundation.text.input.internal;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.BaseInputConnection;
import android.view.inputmethod.DeleteGesture;
import android.view.inputmethod.DeleteRangeGesture;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InsertGesture;
import android.view.inputmethod.JoinOrSplitGesture;
import android.view.inputmethod.RemoveSpaceGesture;
import android.view.inputmethod.SelectGesture;
import android.view.inputmethod.SelectRangeGesture;
import androidx.compose.foundation.text.LegacyTextFieldState;
import androidx.compose.foundation.text.selection.TextFieldSelectionManager;
import androidx.compose.ui.platform.PlatformTextInputMethodRequest;
import androidx.compose.ui.platform.ViewConfiguration;
import androidx.compose.ui.text.TextRange;
import androidx.compose.ui.text.input.ImeAction;
import androidx.compose.ui.text.input.ImeOptions;
import androidx.compose.ui.text.input.KeyboardCapitalization;
import androidx.compose.ui.text.input.KeyboardType;
import androidx.compose.ui.text.input.PlatformImeOptions;
import androidx.compose.ui.text.input.TextFieldValue;
import androidx.compose.ui.text.intl.Locale;
import androidx.compose.ui.text.intl.LocaleList;
import androidx.core.os.BuildCompat;
import androidx.emoji2.text.EmojiCompat;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Unit;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

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
        public final /* bridge */ /* synthetic */ Object mo781invoke(Object obj) {
            return Unit.INSTANCE;
        }
    };
    public Function1 onImeActionPerformed = new Function1() { // from class: androidx.compose.foundation.text.input.internal.LegacyTextInputMethodRequest$onImeActionPerformed$1
        @Override // kotlin.jvm.functions.Function1
        /* renamed from: invoke */
        public final /* synthetic */ Object mo781invoke(Object obj) {
            int i = ((ImeAction) obj).value;
            return Unit.INSTANCE;
        }
    };
    public TextFieldValue state;
    public TextFieldSelectionManager textFieldSelectionManager;
    public final View view;
    public ViewConfiguration viewConfiguration;

    /* renamed from: androidx.compose.foundation.text.input.internal.LegacyTextInputMethodRequest$createInputConnection$1, reason: invalid class name */
    public final class AnonymousClass1 implements InputEventCallback2 {
        public AnonymousClass1() {
        }
    }

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
                return new BaseInputConnection(this.this$0.view, false);
            }
        });
        this.cursorAnchorInfoController = new LegacyCursorAnchorInfoController(function1, inputMethodManager);
    }

    @Override // androidx.compose.ui.platform.PlatformTextInputMethodRequest
    public final InputConnection createInputConnection(EditorInfo editorInfo) {
        int i;
        char c;
        int i2;
        String str;
        TextFieldValue textFieldValue = this.state;
        String str2 = textFieldValue.annotatedString.text;
        ImeOptions imeOptions = this.imeOptions;
        int i3 = imeOptions.imeAction;
        ImeAction.Companion.getClass();
        int i4 = ImeAction.Default;
        boolean z = imeOptions.singleLine;
        if (i3 == i4) {
            i = z ? 6 : 0;
        } else if (i3 == 0) {
            i = 1;
        } else if (i3 == ImeAction.Go) {
            i = 2;
        } else if (i3 == ImeAction.Next) {
            i = 5;
        } else if (i3 == ImeAction.Previous) {
            i = 7;
        } else if (i3 == ImeAction.Search) {
            i = 3;
        } else if (i3 == ImeAction.Send) {
            i = 4;
        } else {
            if (i3 != ImeAction.Done) {
                throw new IllegalStateException("invalid ImeAction");
            }
        }
        editorInfo.imeOptions = i;
        PlatformImeOptions platformImeOptions = imeOptions.platformImeOptions;
        if (platformImeOptions != null && (str = platformImeOptions.privateImeOptions) != null) {
            editorInfo.privateImeOptions = str;
        }
        LocaleListHelper.INSTANCE.getClass();
        LocaleList.Companion.getClass();
        LocaleList localeList = LocaleList.Empty;
        LocaleList localeList2 = imeOptions.hintLocales;
        if (Intrinsics.areEqual(localeList2, localeList)) {
            editorInfo.hintLocales = null;
            c = 5;
        } else {
            c = 5;
            ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(localeList2, 10));
            Iterator it = localeList2.localeList.iterator();
            while (it.hasNext()) {
                arrayList.add(((Locale) it.next()).platformLocale);
            }
            java.util.Locale[] localeArr = (java.util.Locale[]) arrayList.toArray(new java.util.Locale[0]);
            editorInfo.hintLocales = new android.os.LocaleList((java.util.Locale[]) Arrays.copyOf(localeArr, localeArr.length));
        }
        KeyboardType.Companion.getClass();
        int i5 = KeyboardType.Text;
        int i6 = imeOptions.keyboardType;
        if (i6 == i5) {
            i2 = 1;
        } else if (i6 == KeyboardType.Ascii) {
            editorInfo.imeOptions |= Integer.MIN_VALUE;
            i2 = 1;
        } else if (i6 == KeyboardType.Number) {
            i2 = 2;
        } else if (i6 == KeyboardType.Phone) {
            i2 = 3;
        } else if (i6 == KeyboardType.Uri) {
            i2 = 17;
        } else if (i6 == KeyboardType.Email) {
            i2 = 33;
        } else if (i6 == KeyboardType.Password) {
            i2 = 129;
        } else if (i6 == KeyboardType.NumberPassword) {
            i2 = 18;
        } else {
            if (i6 != KeyboardType.Decimal) {
                throw new IllegalStateException("Invalid Keyboard Type");
            }
            i2 = 8194;
        }
        editorInfo.inputType = i2;
        if (!z && (i2 & 1) == 1) {
            editorInfo.inputType = i2 | 131072;
            ImeAction.Companion.getClass();
            if (imeOptions.imeAction == ImeAction.Default) {
                editorInfo.imeOptions |= 1073741824;
            }
        }
        if ((editorInfo.inputType & 1) == 1) {
            KeyboardCapitalization.Companion.getClass();
            int i7 = KeyboardCapitalization.Characters;
            int i8 = imeOptions.capitalization;
            if (i8 == i7) {
                editorInfo.inputType |= 4096;
            } else if (i8 == KeyboardCapitalization.Words) {
                editorInfo.inputType |= 8192;
            } else if (i8 == KeyboardCapitalization.Sentences) {
                editorInfo.inputType |= NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT;
            }
            if (imeOptions.autoCorrect) {
                editorInfo.inputType |= NetworkAnalyticsConstants.DataPoints.FLAG_UID;
            }
        }
        TextRange.Companion companion = TextRange.Companion;
        long j = textFieldValue.selection;
        editorInfo.initialSelStart = (int) (j >> 32);
        editorInfo.initialSelEnd = (int) (j & 4294967295L);
        editorInfo.setInitialSurroundingSubText(str2, 0);
        editorInfo.imeOptions |= 33554432;
        if (i6 == KeyboardType.Password || i6 == KeyboardType.NumberPassword) {
            int i9 = BuildCompat.$r8$clinit;
            editorInfo.setStylusHandwritingEnabled(false);
            if (editorInfo.extras == null) {
                editorInfo.extras = new Bundle();
            }
            editorInfo.extras.putBoolean("androidx.core.view.inputmethod.EditorInfoCompat.STYLUS_HANDWRITING_ENABLED", false);
        } else {
            int i10 = BuildCompat.$r8$clinit;
            editorInfo.setStylusHandwritingEnabled(true);
            if (editorInfo.extras == null) {
                editorInfo.extras = new Bundle();
            }
            editorInfo.extras.putBoolean("androidx.core.view.inputmethod.EditorInfoCompat.STYLUS_HANDWRITING_ENABLED", true);
            EditorInfoApi34.INSTANCE.getClass();
            Class[] clsArr = new Class[7];
            clsArr[0] = SelectGesture.class;
            clsArr[1] = DeleteGesture.class;
            clsArr[2] = SelectRangeGesture.class;
            clsArr[3] = DeleteRangeGesture.class;
            clsArr[4] = JoinOrSplitGesture.class;
            clsArr[c] = InsertGesture.class;
            clsArr[6] = RemoveSpaceGesture.class;
            editorInfo.setSupportedHandwritingGestures(Arrays.asList(clsArr));
            editorInfo.setSupportedHandwritingGesturePreviews(ArraysKt___ArraysKt.toSet(new Class[]{SelectGesture.class, DeleteGesture.class, SelectRangeGesture.class, DeleteRangeGesture.class}));
        }
        Function1 function1 = LegacyPlatformTextInputServiceAdapter_androidKt.inputMethodManagerFactory;
        if (EmojiCompat.isConfigured()) {
            EmojiCompat.get().updateEditorInfo(editorInfo);
        }
        RecordingInputConnection recordingInputConnection = new RecordingInputConnection(this.state, new AnonymousClass1(), this.imeOptions.autoCorrect, this.legacyTextFieldState, this.textFieldSelectionManager, this.viewConfiguration);
        ((ArrayList) this.ics).add(new WeakReference(recordingInputConnection));
        return recordingInputConnection;
    }
}
