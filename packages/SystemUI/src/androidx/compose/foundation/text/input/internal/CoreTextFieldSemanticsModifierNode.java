package androidx.compose.foundation.text.input.internal;

import androidx.compose.foundation.text.HandleState;
import androidx.compose.foundation.text.LegacyTextFieldState;
import androidx.compose.foundation.text.TextFieldDelegate;
import androidx.compose.foundation.text.TextLayoutResultProxy;
import androidx.compose.foundation.text.selection.TextFieldSelectionManager;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.ui.ComposeUiFlags;
import androidx.compose.ui.autofill.ContentDataType;
import androidx.compose.ui.focus.FocusRequester;
import androidx.compose.ui.node.DelegatableNodeKt;
import androidx.compose.ui.node.DelegatingNode;
import androidx.compose.ui.node.LayoutNode;
import androidx.compose.ui.node.LayoutNodeKt;
import androidx.compose.ui.node.SemanticsModifierNode;
import androidx.compose.ui.platform.DelegatingSoftwareKeyboardController;
import androidx.compose.ui.platform.SoftwareKeyboardController;
import androidx.compose.ui.semantics.AccessibilityAction;
import androidx.compose.ui.semantics.SemanticsActions;
import androidx.compose.ui.semantics.SemanticsConfiguration;
import androidx.compose.ui.semantics.SemanticsProperties;
import androidx.compose.ui.semantics.SemanticsPropertiesKt;
import androidx.compose.ui.semantics.SemanticsPropertyKey;
import androidx.compose.ui.semantics.SemanticsPropertyReceiver;
import androidx.compose.ui.text.AnnotatedString;
import androidx.compose.ui.text.TextRange;
import androidx.compose.ui.text.TextRangeKt;
import androidx.compose.ui.text.input.CommitTextCommand;
import androidx.compose.ui.text.input.DeleteAllCommand;
import androidx.compose.ui.text.input.EditProcessor;
import androidx.compose.ui.text.input.FinishComposingTextCommand;
import androidx.compose.ui.text.input.ImeAction;
import androidx.compose.ui.text.input.ImeOptions;
import androidx.compose.ui.text.input.OffsetMapping;
import androidx.compose.ui.text.input.TextFieldValue;
import androidx.compose.ui.text.input.TextInputSession;
import androidx.compose.ui.text.input.TransformedText;
import java.util.Arrays;
import java.util.List;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KProperty;
import kotlin.text.StringsKt__StringsKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class CoreTextFieldSemanticsModifierNode extends DelegatingNode implements SemanticsModifierNode {
    public boolean enabled;
    public FocusRequester focusRequester;
    public ImeOptions imeOptions;
    public final boolean isPassword;
    public TextFieldSelectionManager manager;
    public OffsetMapping offsetMapping;
    public boolean readOnly;
    public LegacyTextFieldState state;
    public TransformedText transformedText;
    public TextFieldValue value;

    /* JADX WARN: Type inference failed for: r1v1, types: [androidx.compose.foundation.text.input.internal.CoreTextFieldSemanticsModifierNode$1, kotlin.jvm.internal.Lambda] */
    public CoreTextFieldSemanticsModifierNode(TransformedText transformedText, TextFieldValue textFieldValue, LegacyTextFieldState legacyTextFieldState, boolean z, boolean z2, boolean z3, OffsetMapping offsetMapping, TextFieldSelectionManager textFieldSelectionManager, ImeOptions imeOptions, FocusRequester focusRequester) {
        this.transformedText = transformedText;
        this.value = textFieldValue;
        this.state = legacyTextFieldState;
        this.readOnly = z;
        this.enabled = z2;
        this.isPassword = z3;
        this.offsetMapping = offsetMapping;
        this.manager = textFieldSelectionManager;
        this.imeOptions = imeOptions;
        this.focusRequester = focusRequester;
        textFieldSelectionManager.requestAutofillAction = new Function0() { // from class: androidx.compose.foundation.text.input.internal.CoreTextFieldSemanticsModifierNode.1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                LayoutNode requireLayoutNode = DelegatableNodeKt.requireLayoutNode(CoreTextFieldSemanticsModifierNode.this);
                if (!requireLayoutNode.isCurrentlyCalculatingSemanticsConfiguration) {
                    LayoutNodeKt.requireOwner(requireLayoutNode);
                    boolean z4 = ComposeUiFlags.isRectTrackingEnabled;
                }
                return Unit.INSTANCE;
            }
        };
    }

    public static final void access$handleTextUpdateFromSemantics(CoreTextFieldSemanticsModifierNode coreTextFieldSemanticsModifierNode, LegacyTextFieldState legacyTextFieldState, String str, boolean z, boolean z2) {
        coreTextFieldSemanticsModifierNode.getClass();
        if (z || !z2) {
            return;
        }
        TextInputSession textInputSession = legacyTextFieldState.inputSession;
        Function1 function1 = legacyTextFieldState.onValueChange;
        Unit unit = null;
        if (textInputSession != null) {
            TextFieldDelegate.Companion companion = TextFieldDelegate.Companion;
            List asList = Arrays.asList(new DeleteAllCommand(), new CommitTextCommand(str, 1));
            companion.getClass();
            TextFieldValue apply = legacyTextFieldState.processor.apply(asList);
            if (Intrinsics.areEqual((TextInputSession) textInputSession.textInputService._currentInputSession.get(), textInputSession)) {
                textInputSession.platformTextInputService.updateState(null, apply);
            }
            function1.mo779invoke(apply);
            unit = Unit.INSTANCE;
        }
        if (unit == null) {
            int length = str.length();
            function1.mo779invoke(new TextFieldValue(str, TextRangeKt.TextRange(length, length), (TextRange) null, 4, (DefaultConstructorMarker) null));
        }
    }

    @Override // androidx.compose.ui.node.SemanticsModifierNode
    public final void applySemantics(final SemanticsPropertyReceiver semanticsPropertyReceiver) {
        AnnotatedString annotatedString = this.transformedText.text;
        KProperty[] kPropertyArr = SemanticsPropertiesKt.$$delegatedProperties;
        SemanticsProperties.INSTANCE.getClass();
        SemanticsPropertyKey semanticsPropertyKey = SemanticsProperties.EditableText;
        KProperty[] kPropertyArr2 = SemanticsPropertiesKt.$$delegatedProperties;
        KProperty kProperty = kPropertyArr2[16];
        semanticsPropertyKey.setValue(semanticsPropertyReceiver, annotatedString);
        long j = this.value.selection;
        SemanticsPropertyKey semanticsPropertyKey2 = SemanticsProperties.TextSelectionRange;
        KProperty kProperty2 = kPropertyArr2[17];
        semanticsPropertyKey2.setValue(semanticsPropertyReceiver, TextRange.m745boximpl(j));
        ContentDataType.Companion.getClass();
        ContentDataType contentDataType = ContentDataType.Companion.Text;
        SemanticsPropertyKey semanticsPropertyKey3 = SemanticsProperties.ContentDataType;
        KProperty kProperty3 = kPropertyArr2[8];
        semanticsPropertyKey3.setValue(semanticsPropertyReceiver, contentDataType);
        Function1 function1 = new Function1() { // from class: androidx.compose.foundation.text.input.internal.CoreTextFieldSemanticsModifierNode$applySemantics$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                MutableState mutableState = CoreTextFieldSemanticsModifierNode.this.state.justAutofilled$delegate;
                Boolean bool = Boolean.TRUE;
                ((SnapshotMutableStateImpl) mutableState).setValue(bool);
                ((SnapshotMutableStateImpl) CoreTextFieldSemanticsModifierNode.this.state.autofillHighlightOn$delegate).setValue(bool);
                CoreTextFieldSemanticsModifierNode coreTextFieldSemanticsModifierNode = CoreTextFieldSemanticsModifierNode.this;
                CoreTextFieldSemanticsModifierNode.access$handleTextUpdateFromSemantics(coreTextFieldSemanticsModifierNode, coreTextFieldSemanticsModifierNode.state, ((AnnotatedString) obj).text, coreTextFieldSemanticsModifierNode.readOnly, coreTextFieldSemanticsModifierNode.enabled);
                return bool;
            }
        };
        SemanticsActions.INSTANCE.getClass();
        SemanticsPropertyKey semanticsPropertyKey4 = SemanticsActions.OnAutofillText;
        AccessibilityAction accessibilityAction = new AccessibilityAction(null, function1);
        SemanticsConfiguration semanticsConfiguration = (SemanticsConfiguration) semanticsPropertyReceiver;
        semanticsConfiguration.set(semanticsPropertyKey4, accessibilityAction);
        if (!this.enabled) {
            SemanticsPropertiesKt.disabled(semanticsPropertyReceiver);
        }
        boolean z = this.isPassword;
        if (z) {
            semanticsConfiguration.set(SemanticsProperties.Password, Unit.INSTANCE);
        }
        boolean z2 = this.enabled && !this.readOnly;
        SemanticsPropertyKey semanticsPropertyKey5 = SemanticsProperties.IsEditable;
        KProperty kProperty4 = kPropertyArr2[23];
        semanticsPropertyKey5.setValue(semanticsPropertyReceiver, Boolean.valueOf(z2));
        SemanticsPropertiesKt.getTextLayoutResult$default(semanticsPropertyReceiver, new Function1() { // from class: androidx.compose.foundation.text.input.internal.CoreTextFieldSemanticsModifierNode$applySemantics$2
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                boolean z3;
                List list = (List) obj;
                if (CoreTextFieldSemanticsModifierNode.this.state.getLayoutResult() != null) {
                    TextLayoutResultProxy layoutResult = CoreTextFieldSemanticsModifierNode.this.state.getLayoutResult();
                    layoutResult.getClass();
                    list.add(layoutResult.value);
                    z3 = true;
                } else {
                    z3 = false;
                }
                return Boolean.valueOf(z3);
            }
        });
        if (z2) {
            semanticsConfiguration.set(SemanticsActions.SetText, new AccessibilityAction(null, new Function1() { // from class: androidx.compose.foundation.text.input.internal.CoreTextFieldSemanticsModifierNode$applySemantics$3
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo779invoke(Object obj) {
                    CoreTextFieldSemanticsModifierNode coreTextFieldSemanticsModifierNode = CoreTextFieldSemanticsModifierNode.this;
                    CoreTextFieldSemanticsModifierNode.access$handleTextUpdateFromSemantics(coreTextFieldSemanticsModifierNode, coreTextFieldSemanticsModifierNode.state, ((AnnotatedString) obj).text, coreTextFieldSemanticsModifierNode.readOnly, coreTextFieldSemanticsModifierNode.enabled);
                    return Boolean.TRUE;
                }
            }));
            semanticsConfiguration.set(SemanticsActions.InsertTextAtCursor, new AccessibilityAction(null, new Function1() { // from class: androidx.compose.foundation.text.input.internal.CoreTextFieldSemanticsModifierNode$applySemantics$4
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo779invoke(Object obj) {
                    AnnotatedString annotatedString2 = (AnnotatedString) obj;
                    CoreTextFieldSemanticsModifierNode coreTextFieldSemanticsModifierNode = CoreTextFieldSemanticsModifierNode.this;
                    if (coreTextFieldSemanticsModifierNode.readOnly || !coreTextFieldSemanticsModifierNode.enabled) {
                        return Boolean.FALSE;
                    }
                    TextInputSession textInputSession = coreTextFieldSemanticsModifierNode.state.inputSession;
                    Unit unit = null;
                    if (textInputSession != null) {
                        TextFieldDelegate.Companion companion = TextFieldDelegate.Companion;
                        List asList = Arrays.asList(new FinishComposingTextCommand(), new CommitTextCommand(annotatedString2, 1));
                        LegacyTextFieldState legacyTextFieldState = coreTextFieldSemanticsModifierNode.state;
                        EditProcessor editProcessor = legacyTextFieldState.processor;
                        Function1 function12 = legacyTextFieldState.onValueChange;
                        companion.getClass();
                        TextFieldValue apply = editProcessor.apply(asList);
                        if (Intrinsics.areEqual((TextInputSession) textInputSession.textInputService._currentInputSession.get(), textInputSession)) {
                            textInputSession.platformTextInputService.updateState(null, apply);
                        }
                        function12.mo779invoke(apply);
                        unit = Unit.INSTANCE;
                    }
                    if (unit == null) {
                        CoreTextFieldSemanticsModifierNode coreTextFieldSemanticsModifierNode2 = CoreTextFieldSemanticsModifierNode.this;
                        TextFieldValue textFieldValue = coreTextFieldSemanticsModifierNode2.value;
                        String str = textFieldValue.annotatedString.text;
                        TextRange.Companion companion2 = TextRange.Companion;
                        long j2 = textFieldValue.selection;
                        String obj2 = StringsKt__StringsKt.replaceRange(str, (int) (j2 >> 32), (int) (j2 & 4294967295L), annotatedString2).toString();
                        int length = annotatedString2.text.length() + ((int) (coreTextFieldSemanticsModifierNode2.value.selection >> 32));
                        coreTextFieldSemanticsModifierNode2.state.onValueChange.mo779invoke(new TextFieldValue(obj2, TextRangeKt.TextRange(length, length), (TextRange) null, 4, (DefaultConstructorMarker) null));
                    }
                    return Boolean.TRUE;
                }
            }));
        }
        semanticsConfiguration.set(SemanticsActions.SetSelection, new AccessibilityAction(null, new Function3() { // from class: androidx.compose.foundation.text.input.internal.CoreTextFieldSemanticsModifierNode$applySemantics$5
            {
                super(3);
            }

            @Override // kotlin.jvm.functions.Function3
            public final Object invoke(Object obj, Object obj2, Object obj3) {
                int intValue = ((Number) obj).intValue();
                int intValue2 = ((Number) obj2).intValue();
                boolean booleanValue = ((Boolean) obj3).booleanValue();
                if (!booleanValue) {
                    intValue = CoreTextFieldSemanticsModifierNode.this.offsetMapping.transformedToOriginal(intValue);
                }
                if (!booleanValue) {
                    intValue2 = CoreTextFieldSemanticsModifierNode.this.offsetMapping.transformedToOriginal(intValue2);
                }
                CoreTextFieldSemanticsModifierNode coreTextFieldSemanticsModifierNode = CoreTextFieldSemanticsModifierNode.this;
                boolean z3 = false;
                if (coreTextFieldSemanticsModifierNode.enabled) {
                    long j2 = coreTextFieldSemanticsModifierNode.value.selection;
                    TextRange.Companion companion = TextRange.Companion;
                    if (intValue != ((int) (j2 >> 32)) || intValue2 != ((int) (j2 & 4294967295L))) {
                        if (Math.min(intValue, intValue2) < 0 || Math.max(intValue, intValue2) > CoreTextFieldSemanticsModifierNode.this.value.annotatedString.text.length()) {
                            TextFieldSelectionManager textFieldSelectionManager = CoreTextFieldSemanticsModifierNode.this.manager;
                            textFieldSelectionManager.updateFloatingToolbar(false);
                            textFieldSelectionManager.setHandleState(HandleState.None);
                        } else {
                            if (booleanValue || intValue == intValue2) {
                                TextFieldSelectionManager textFieldSelectionManager2 = CoreTextFieldSemanticsModifierNode.this.manager;
                                textFieldSelectionManager2.updateFloatingToolbar(false);
                                textFieldSelectionManager2.setHandleState(HandleState.None);
                            } else {
                                CoreTextFieldSemanticsModifierNode.this.manager.enterSelectionMode$foundation_release(true);
                            }
                            CoreTextFieldSemanticsModifierNode.this.state.onValueChange.mo779invoke(new TextFieldValue(CoreTextFieldSemanticsModifierNode.this.value.annotatedString, TextRangeKt.TextRange(intValue, intValue2), (TextRange) null, 4, (DefaultConstructorMarker) null));
                            z3 = true;
                        }
                    }
                }
                return Boolean.valueOf(z3);
            }
        }));
        int i = this.imeOptions.imeAction;
        Function0 function0 = new Function0() { // from class: androidx.compose.foundation.text.input.internal.CoreTextFieldSemanticsModifierNode$applySemantics$6
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                CoreTextFieldSemanticsModifierNode coreTextFieldSemanticsModifierNode = CoreTextFieldSemanticsModifierNode.this;
                coreTextFieldSemanticsModifierNode.state.onImeActionPerformed.mo779invoke(ImeAction.m772boximpl(coreTextFieldSemanticsModifierNode.imeOptions.imeAction));
                return Boolean.TRUE;
            }
        };
        semanticsConfiguration.set(SemanticsProperties.ImeAction, ImeAction.m772boximpl(i));
        semanticsConfiguration.set(SemanticsActions.OnImeAction, new AccessibilityAction(null, function0));
        SemanticsPropertiesKt.onClick(semanticsPropertyReceiver, null, new Function0() { // from class: androidx.compose.foundation.text.input.internal.CoreTextFieldSemanticsModifierNode$applySemantics$7
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                SoftwareKeyboardController softwareKeyboardController;
                CoreTextFieldSemanticsModifierNode coreTextFieldSemanticsModifierNode = CoreTextFieldSemanticsModifierNode.this;
                LegacyTextFieldState legacyTextFieldState = coreTextFieldSemanticsModifierNode.state;
                FocusRequester focusRequester = coreTextFieldSemanticsModifierNode.focusRequester;
                boolean z3 = coreTextFieldSemanticsModifierNode.readOnly;
                if (!legacyTextFieldState.getHasFocus()) {
                    FocusRequester.m376requestFocus3ESFkO8$default(focusRequester);
                } else if (!z3 && (softwareKeyboardController = legacyTextFieldState.keyboardController) != null) {
                    ((DelegatingSoftwareKeyboardController) softwareKeyboardController).show();
                }
                return Boolean.TRUE;
            }
        });
        SemanticsPropertiesKt.onLongClick(semanticsPropertyReceiver, null, new Function0() { // from class: androidx.compose.foundation.text.input.internal.CoreTextFieldSemanticsModifierNode$applySemantics$8
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                CoreTextFieldSemanticsModifierNode.this.manager.enterSelectionMode$foundation_release(true);
                return Boolean.TRUE;
            }
        });
        if (!TextRange.m747getCollapsedimpl(this.value.selection) && !z) {
            semanticsConfiguration.set(SemanticsActions.CopyText, new AccessibilityAction(null, new Function0() { // from class: androidx.compose.foundation.text.input.internal.CoreTextFieldSemanticsModifierNode$applySemantics$9
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    CoreTextFieldSemanticsModifierNode.this.manager.copy$foundation_release(true);
                    return Boolean.TRUE;
                }
            }));
            if (this.enabled && !this.readOnly) {
                semanticsConfiguration.set(SemanticsActions.CutText, new AccessibilityAction(null, new Function0() { // from class: androidx.compose.foundation.text.input.internal.CoreTextFieldSemanticsModifierNode$applySemantics$10
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        CoreTextFieldSemanticsModifierNode.this.manager.cut$foundation_release();
                        return Boolean.TRUE;
                    }
                }));
            }
        }
        if (!this.enabled || this.readOnly) {
            return;
        }
        semanticsConfiguration.set(SemanticsActions.PasteText, new AccessibilityAction(null, new Function0() { // from class: androidx.compose.foundation.text.input.internal.CoreTextFieldSemanticsModifierNode$applySemantics$11
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                CoreTextFieldSemanticsModifierNode.this.manager.paste$foundation_release();
                return Boolean.TRUE;
            }
        }));
    }

    @Override // androidx.compose.ui.node.SemanticsModifierNode
    public final boolean getShouldMergeDescendantSemantics() {
        return true;
    }
}
