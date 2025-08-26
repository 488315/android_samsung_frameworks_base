package androidx.compose.foundation.text.selection;

import android.content.ClipData;
import android.view.ActionMode;
import android.view.View;
import androidx.compose.foundation.internal.ClipboardUtils;
import androidx.compose.foundation.text.LegacyTextFieldState;
import androidx.compose.foundation.text.TextLayoutResultProxy;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.geometry.Rect;
import androidx.compose.ui.layout.LayoutCoordinates;
import androidx.compose.ui.platform.AndroidClipboard;
import androidx.compose.ui.platform.AndroidTextToolbar;
import androidx.compose.ui.platform.ClipEntry;
import androidx.compose.ui.platform.Clipboard;
import androidx.compose.ui.platform.TextToolbar;
import androidx.compose.ui.platform.TextToolbarHelperMethods;
import androidx.compose.ui.platform.TextToolbarStatus;
import androidx.compose.ui.platform.actionmodecallback.FloatingTextActionModeCallback;
import androidx.compose.ui.platform.actionmodecallback.TextActionModeCallback;
import androidx.compose.ui.text.TextLayoutResult;
import androidx.compose.ui.text.TextRange;
import androidx.compose.ui.text.input.PasswordVisualTransformation;
import androidx.compose.ui.unit.Dp;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineStart;

/* loaded from: classes.dex */
final class TextFieldSelectionManager$showSelectionToolbar$1 extends SuspendLambda implements Function2 {
    Object L$0;
    Object L$1;
    int label;
    final /* synthetic */ TextFieldSelectionManager this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public TextFieldSelectionManager$showSelectionToolbar$1(TextFieldSelectionManager textFieldSelectionManager, Continuation continuation) {
        super(2, continuation);
        this.this$0 = textFieldSelectionManager;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new TextFieldSelectionManager$showSelectionToolbar$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((TextFieldSelectionManager$showSelectionToolbar$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Removed duplicated region for block: B:103:0x0254  */
    /* JADX WARN: Removed duplicated region for block: B:106:0x0273  */
    /* JADX WARN: Removed duplicated region for block: B:107:0x028b  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x00cc  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x00d4 A[PHI: r2 r6
      0x00d4: PHI (r2v17 kotlin.jvm.functions.Function0) = (r2v9 kotlin.jvm.functions.Function0), (r2v20 kotlin.jvm.functions.Function0) binds: [B:28:0x008b, B:45:0x00ca] A[DONT_GENERATE, DONT_INLINE]
      0x00d4: PHI (r6v6 kotlin.jvm.functions.Function0) = (r6v3 kotlin.jvm.functions.Function0), (r6v18 kotlin.jvm.functions.Function0) binds: [B:28:0x008b, B:45:0x00ca] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:50:0x00f1  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x00f9  */
    /* JADX WARN: Removed duplicated region for block: B:54:0x0102  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x0118  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x011f  */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) {
        LegacyTextFieldState legacyTextFieldState;
        Function0 function0;
        Function0 function02;
        Object clipEntry;
        Function0 function03;
        boolean z;
        Function0 function04;
        Function0 function05;
        Function0 function06;
        TextToolbar textToolbar;
        Function0 function07;
        Rect rect;
        ActionMode actionMode;
        char c;
        long jMo615localToRootMKHz9U;
        long jMo615localToRootMKHz9U2;
        float f;
        float fIntBitsToFloat;
        float fIntBitsToFloat2;
        LayoutCoordinates layoutCoordinates;
        TextLayoutResult textLayoutResult;
        LayoutCoordinates layoutCoordinates2;
        TextLayoutResult textLayoutResult2;
        LayoutCoordinates layoutCoordinates3;
        LayoutCoordinates layoutCoordinates4;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            if (!this.this$0.getEnabled() || ((legacyTextFieldState = this.this$0.state) != null && !((Boolean) ((SnapshotMutableStateImpl) legacyTextFieldState.isInTouchMode$delegate).getValue()).booleanValue())) {
                return Unit.INSTANCE;
            }
            TextFieldSelectionManager textFieldSelectionManager = this.this$0;
            boolean z2 = textFieldSelectionManager.visualTransformation instanceof PasswordVisualTransformation;
            if (TextRange.m749getCollapsedimpl(textFieldSelectionManager.getValue$foundation_release().selection) || z2) {
                function0 = null;
            } else {
                final TextFieldSelectionManager textFieldSelectionManager2 = this.this$0;
                function0 = new Function0() { // from class: androidx.compose.foundation.text.selection.TextFieldSelectionManager$showSelectionToolbar$1$copy$1

                    /* renamed from: androidx.compose.foundation.text.selection.TextFieldSelectionManager$showSelectionToolbar$1$copy$1$1, reason: invalid class name */
                    final class AnonymousClass1 extends SuspendLambda implements Function2 {
                        int label;
                        final /* synthetic */ TextFieldSelectionManager this$0;

                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        public AnonymousClass1(TextFieldSelectionManager textFieldSelectionManager, Continuation continuation) {
                            super(2, continuation);
                            this.this$0 = textFieldSelectionManager;
                        }

                        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                        public final Continuation create(Object obj, Continuation continuation) {
                            return new AnonymousClass1(this.this$0, continuation);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                        }

                        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                        public final Object invokeSuspend(Object obj) {
                            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                            if (this.label != 0) {
                                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                            }
                            ResultKt.throwOnFailure(obj);
                            this.this$0.copy$foundation_release(true);
                            return Unit.INSTANCE;
                        }
                    }

                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        TextFieldSelectionManager textFieldSelectionManager3 = textFieldSelectionManager2;
                        CoroutineScope coroutineScope = textFieldSelectionManager3.coroutineScope;
                        if (coroutineScope != null) {
                            BuildersKt.launch$default(coroutineScope, null, CoroutineStart.UNDISPATCHED, new AnonymousClass1(textFieldSelectionManager3, null), 1);
                        }
                        textFieldSelectionManager2.hideSelectionToolbar$foundation_release();
                        return Unit.INSTANCE;
                    }
                };
            }
            if (TextRange.m749getCollapsedimpl(this.this$0.getValue$foundation_release().selection) || !this.this$0.getEditable() || z2) {
                function02 = null;
            } else {
                final TextFieldSelectionManager textFieldSelectionManager3 = this.this$0;
                function02 = new Function0() { // from class: androidx.compose.foundation.text.selection.TextFieldSelectionManager$showSelectionToolbar$1$cut$1

                    /* renamed from: androidx.compose.foundation.text.selection.TextFieldSelectionManager$showSelectionToolbar$1$cut$1$1, reason: invalid class name */
                    final class AnonymousClass1 extends SuspendLambda implements Function2 {
                        int label;
                        final /* synthetic */ TextFieldSelectionManager this$0;

                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        public AnonymousClass1(TextFieldSelectionManager textFieldSelectionManager, Continuation continuation) {
                            super(2, continuation);
                            this.this$0 = textFieldSelectionManager;
                        }

                        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                        public final Continuation create(Object obj, Continuation continuation) {
                            return new AnonymousClass1(this.this$0, continuation);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                        }

                        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                        public final Object invokeSuspend(Object obj) {
                            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                            if (this.label != 0) {
                                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                            }
                            ResultKt.throwOnFailure(obj);
                            this.this$0.cut$foundation_release();
                            return Unit.INSTANCE;
                        }
                    }

                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        TextFieldSelectionManager textFieldSelectionManager4 = textFieldSelectionManager3;
                        CoroutineScope coroutineScope = textFieldSelectionManager4.coroutineScope;
                        if (coroutineScope != null) {
                            BuildersKt.launch$default(coroutineScope, null, CoroutineStart.UNDISPATCHED, new AnonymousClass1(textFieldSelectionManager4, null), 1);
                        }
                        textFieldSelectionManager3.hideSelectionToolbar$foundation_release();
                        return Unit.INSTANCE;
                    }
                };
            }
            if (this.this$0.getEditable()) {
                Clipboard clipboard = this.this$0.clipboard;
                if (clipboard != null) {
                    this.L$0 = function0;
                    this.L$1 = function02;
                    this.label = 1;
                    ClipData primaryClip = ((AndroidClipboard) clipboard).androidClipboardManager.clipboardManager.getPrimaryClip();
                    clipEntry = primaryClip != null ? new ClipEntry(primaryClip) : null;
                    if (clipEntry == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                    function03 = function02;
                }
                z = false;
                if (z) {
                    final TextFieldSelectionManager textFieldSelectionManager4 = this.this$0;
                    function04 = new Function0() { // from class: androidx.compose.foundation.text.selection.TextFieldSelectionManager$showSelectionToolbar$1$paste$1

                        /* renamed from: androidx.compose.foundation.text.selection.TextFieldSelectionManager$showSelectionToolbar$1$paste$1$1, reason: invalid class name */
                        final class AnonymousClass1 extends SuspendLambda implements Function2 {
                            int label;
                            final /* synthetic */ TextFieldSelectionManager this$0;

                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            public AnonymousClass1(TextFieldSelectionManager textFieldSelectionManager, Continuation continuation) {
                                super(2, continuation);
                                this.this$0 = textFieldSelectionManager;
                            }

                            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                            public final Continuation create(Object obj, Continuation continuation) {
                                return new AnonymousClass1(this.this$0, continuation);
                            }

                            @Override // kotlin.jvm.functions.Function2
                            public final Object invoke(Object obj, Object obj2) {
                                return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                            }

                            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                            public final Object invokeSuspend(Object obj) {
                                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                                if (this.label != 0) {
                                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                                }
                                ResultKt.throwOnFailure(obj);
                                this.this$0.paste$foundation_release();
                                return Unit.INSTANCE;
                            }
                        }

                        {
                            super(0);
                        }

                        @Override // kotlin.jvm.functions.Function0
                        public final Object invoke() {
                            TextFieldSelectionManager textFieldSelectionManager5 = textFieldSelectionManager4;
                            CoroutineScope coroutineScope = textFieldSelectionManager5.coroutineScope;
                            if (coroutineScope != null) {
                                BuildersKt.launch$default(coroutineScope, null, CoroutineStart.UNDISPATCHED, new AnonymousClass1(textFieldSelectionManager5, null), 1);
                            }
                            textFieldSelectionManager4.hideSelectionToolbar$foundation_release();
                            return Unit.INSTANCE;
                        }
                    };
                }
                if (TextRange.m750getLengthimpl(this.this$0.getValue$foundation_release().selection) == this.this$0.getValue$foundation_release().annotatedString.text.length()) {
                    final TextFieldSelectionManager textFieldSelectionManager5 = this.this$0;
                    function05 = new Function0() { // from class: androidx.compose.foundation.text.selection.TextFieldSelectionManager$showSelectionToolbar$1$selectAll$1
                        {
                            super(0);
                        }

                        @Override // kotlin.jvm.functions.Function0
                        public final Object invoke() {
                            textFieldSelectionManager5.selectAll$foundation_release();
                            return Unit.INSTANCE;
                        }
                    };
                } else {
                    function05 = null;
                }
                if (this.this$0.getEditable() || !TextRange.m749getCollapsedimpl(this.this$0.getValue$foundation_release().selection)) {
                    function06 = null;
                } else {
                    final TextFieldSelectionManager textFieldSelectionManager6 = this.this$0;
                    function06 = new Function0() { // from class: androidx.compose.foundation.text.selection.TextFieldSelectionManager$showSelectionToolbar$1$autofill$1
                        {
                            super(0);
                        }

                        /* JADX WARN: Type inference failed for: r0v2, types: [kotlin.jvm.functions.Function0, kotlin.jvm.internal.Lambda] */
                        @Override // kotlin.jvm.functions.Function0
                        public final Object invoke() {
                            ?? r0 = textFieldSelectionManager6.requestAutofillAction;
                            if (r0 != 0) {
                                r0.invoke();
                            }
                            return Unit.INSTANCE;
                        }
                    };
                }
                TextFieldSelectionManager textFieldSelectionManager7 = this.this$0;
                textToolbar = textFieldSelectionManager7.textToolbar;
                if (textToolbar != null) {
                    LegacyTextFieldState legacyTextFieldState2 = textFieldSelectionManager7.state;
                    if (legacyTextFieldState2 == null) {
                        function07 = function02;
                        Rect.Companion.getClass();
                        rect = Rect.Zero;
                        AndroidTextToolbar androidTextToolbar = (AndroidTextToolbar) textToolbar;
                        TextActionModeCallback textActionModeCallback = androidTextToolbar.textActionModeCallback;
                        textActionModeCallback.rect = rect;
                        textActionModeCallback.onCopyRequested = function0;
                        textActionModeCallback.onCutRequested = function07;
                        textActionModeCallback.onPasteRequested = function04;
                        textActionModeCallback.onSelectAllRequested = function05;
                        textActionModeCallback.onAutofillRequested = function06;
                        actionMode = androidTextToolbar.actionMode;
                        if (actionMode != null) {
                            androidTextToolbar.status = TextToolbarStatus.Shown;
                            TextToolbarHelperMethods textToolbarHelperMethods = TextToolbarHelperMethods.INSTANCE;
                            View view = androidTextToolbar.view;
                            FloatingTextActionModeCallback floatingTextActionModeCallback = new FloatingTextActionModeCallback(textActionModeCallback);
                            textToolbarHelperMethods.getClass();
                            androidTextToolbar.actionMode = view.startActionMode(floatingTextActionModeCallback, 1);
                        } else {
                            actionMode.invalidate();
                        }
                    } else {
                        LegacyTextFieldState legacyTextFieldState3 = legacyTextFieldState2.isLayoutResultStale ? null : legacyTextFieldState2;
                        if (legacyTextFieldState3 != null) {
                            int iOriginalToTransformed = textFieldSelectionManager7.offsetMapping.originalToTransformed((int) (textFieldSelectionManager7.getValue$foundation_release().selection >> 32));
                            int iOriginalToTransformed2 = textFieldSelectionManager7.offsetMapping.originalToTransformed((int) (textFieldSelectionManager7.getValue$foundation_release().selection & 4294967295L));
                            LegacyTextFieldState legacyTextFieldState4 = textFieldSelectionManager7.state;
                            if (legacyTextFieldState4 == null || (layoutCoordinates4 = legacyTextFieldState4.getLayoutCoordinates()) == null) {
                                c = ' ';
                                Offset.Companion.getClass();
                                jMo615localToRootMKHz9U = 0;
                            } else {
                                c = ' ';
                                jMo615localToRootMKHz9U = layoutCoordinates4.mo615localToRootMKHz9U(textFieldSelectionManager7.m241getHandlePositiontuRUvjQ$foundation_release(true));
                            }
                            LegacyTextFieldState legacyTextFieldState5 = textFieldSelectionManager7.state;
                            if (legacyTextFieldState5 == null || (layoutCoordinates3 = legacyTextFieldState5.getLayoutCoordinates()) == null) {
                                function07 = function02;
                                Offset.Companion.getClass();
                                jMo615localToRootMKHz9U2 = 0;
                            } else {
                                function07 = function02;
                                jMo615localToRootMKHz9U2 = layoutCoordinates3.mo615localToRootMKHz9U(textFieldSelectionManager7.m241getHandlePositiontuRUvjQ$foundation_release(false));
                            }
                            LegacyTextFieldState legacyTextFieldState6 = textFieldSelectionManager7.state;
                            if (legacyTextFieldState6 == null || (layoutCoordinates2 = legacyTextFieldState6.getLayoutCoordinates()) == null) {
                                f = 0.0f;
                                fIntBitsToFloat = 0.0f;
                            } else {
                                TextLayoutResultProxy layoutResult = legacyTextFieldState3.getLayoutResult();
                                f = 0.0f;
                                long jFloatToRawIntBits = (Float.floatToRawIntBits(0.0f) << c) | (Float.floatToRawIntBits((layoutResult == null || (textLayoutResult2 = layoutResult.value) == null) ? 0.0f : textLayoutResult2.getCursorRect(iOriginalToTransformed).top) & 4294967295L);
                                Offset.Companion companion = Offset.Companion;
                                fIntBitsToFloat = Float.intBitsToFloat((int) (layoutCoordinates2.mo615localToRootMKHz9U(jFloatToRawIntBits) & 4294967295L));
                            }
                            LegacyTextFieldState legacyTextFieldState7 = textFieldSelectionManager7.state;
                            if (legacyTextFieldState7 == null || (layoutCoordinates = legacyTextFieldState7.getLayoutCoordinates()) == null) {
                                fIntBitsToFloat2 = f;
                            } else {
                                TextLayoutResultProxy layoutResult2 = legacyTextFieldState3.getLayoutResult();
                                Offset.Companion companion2 = Offset.Companion;
                                fIntBitsToFloat2 = Float.intBitsToFloat((int) (layoutCoordinates.mo615localToRootMKHz9U((Float.floatToRawIntBits((layoutResult2 == null || (textLayoutResult = layoutResult2.value) == null) ? f : textLayoutResult.getCursorRect(iOriginalToTransformed2).top) & 4294967295L) | (Float.floatToRawIntBits(f) << c)) & 4294967295L));
                            }
                            int i2 = (int) (jMo615localToRootMKHz9U >> c);
                            int i3 = (int) (jMo615localToRootMKHz9U2 >> c);
                            float fMin = Math.min(Float.intBitsToFloat(i2), Float.intBitsToFloat(i3));
                            float fMax = Math.max(Float.intBitsToFloat(i2), Float.intBitsToFloat(i3));
                            float fMin2 = Math.min(fIntBitsToFloat, fIntBitsToFloat2);
                            float fMax2 = Math.max(Float.intBitsToFloat((int) (jMo615localToRootMKHz9U & 4294967295L)), Float.intBitsToFloat((int) (jMo615localToRootMKHz9U2 & 4294967295L)));
                            Dp.Companion companion3 = Dp.Companion;
                            rect = new Rect(fMin, fMin2, fMax, (legacyTextFieldState3.textDelegate.density.getDensity() * 25) + fMax2);
                        }
                        AndroidTextToolbar androidTextToolbar2 = (AndroidTextToolbar) textToolbar;
                        TextActionModeCallback textActionModeCallback2 = androidTextToolbar2.textActionModeCallback;
                        textActionModeCallback2.rect = rect;
                        textActionModeCallback2.onCopyRequested = function0;
                        textActionModeCallback2.onCutRequested = function07;
                        textActionModeCallback2.onPasteRequested = function04;
                        textActionModeCallback2.onSelectAllRequested = function05;
                        textActionModeCallback2.onAutofillRequested = function06;
                        actionMode = androidTextToolbar2.actionMode;
                        if (actionMode != null) {
                        }
                    }
                }
                return Unit.INSTANCE;
            }
            function04 = null;
            if (TextRange.m750getLengthimpl(this.this$0.getValue$foundation_release().selection) == this.this$0.getValue$foundation_release().annotatedString.text.length()) {
            }
            if (this.this$0.getEditable()) {
                function06 = null;
            }
            TextFieldSelectionManager textFieldSelectionManager72 = this.this$0;
            textToolbar = textFieldSelectionManager72.textToolbar;
            if (textToolbar != null) {
            }
            return Unit.INSTANCE;
        }
        if (i != 1) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        function03 = (Function0) this.L$1;
        function0 = (Function0) this.L$0;
        ResultKt.throwOnFailure(obj);
        clipEntry = obj;
        ClipEntry clipEntry2 = (ClipEntry) clipEntry;
        if (clipEntry2 != null) {
            int i4 = ClipboardUtils.$r8$clinit;
            if (clipEntry2.clipData.getDescription().hasMimeType("text/*")) {
                function02 = function03;
                z = true;
                if (z) {
                    function04 = null;
                }
                if (TextRange.m750getLengthimpl(this.this$0.getValue$foundation_release().selection) == this.this$0.getValue$foundation_release().annotatedString.text.length()) {
                }
                if (this.this$0.getEditable()) {
                }
                TextFieldSelectionManager textFieldSelectionManager722 = this.this$0;
                textToolbar = textFieldSelectionManager722.textToolbar;
                if (textToolbar != null) {
                }
                return Unit.INSTANCE;
            }
        }
        function02 = function03;
        z = false;
        if (z) {
        }
        if (TextRange.m750getLengthimpl(this.this$0.getValue$foundation_release().selection) == this.this$0.getValue$foundation_release().annotatedString.text.length()) {
        }
        if (this.this$0.getEditable()) {
        }
        TextFieldSelectionManager textFieldSelectionManager7222 = this.this$0;
        textToolbar = textFieldSelectionManager7222.textToolbar;
        if (textToolbar != null) {
        }
        return Unit.INSTANCE;
    }
}
