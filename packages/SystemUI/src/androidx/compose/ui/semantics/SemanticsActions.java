package androidx.compose.ui.semantics;

import kotlin.Function;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class SemanticsActions {
    public static final SemanticsPropertyKey ClearTextSubstitution;
    public static final SemanticsPropertyKey Collapse;
    public static final SemanticsPropertyKey CopyText;
    public static final SemanticsPropertyKey CustomActions;
    public static final SemanticsPropertyKey CutText;
    public static final SemanticsPropertyKey Dismiss;
    public static final SemanticsPropertyKey Expand;
    public static final SemanticsPropertyKey GetScrollViewportLength;
    public static final SemanticsPropertyKey GetTextLayoutResult;
    public static final SemanticsActions INSTANCE = new SemanticsActions();
    public static final SemanticsPropertyKey InsertTextAtCursor;
    public static final SemanticsPropertyKey OnAutofillText;
    public static final SemanticsPropertyKey OnClick;
    public static final SemanticsPropertyKey OnImeAction;
    public static final SemanticsPropertyKey OnLongClick;
    public static final SemanticsPropertyKey PageDown;
    public static final SemanticsPropertyKey PageLeft;
    public static final SemanticsPropertyKey PageRight;
    public static final SemanticsPropertyKey PageUp;
    public static final SemanticsPropertyKey PasteText;
    public static final SemanticsPropertyKey RequestFocus;
    public static final SemanticsPropertyKey ScrollBy;
    public static final SemanticsPropertyKey ScrollByOffset;
    public static final SemanticsPropertyKey ScrollToIndex;
    public static final SemanticsPropertyKey SetProgress;
    public static final SemanticsPropertyKey SetSelection;
    public static final SemanticsPropertyKey SetText;
    public static final SemanticsPropertyKey SetTextSubstitution;
    public static final SemanticsPropertyKey ShowTextSubstitution;

    static {
        SemanticsPropertiesKt$ActionPropertyKey$1 semanticsPropertiesKt$ActionPropertyKey$1 = new Function2() { // from class: androidx.compose.ui.semantics.SemanticsPropertiesKt$ActionPropertyKey$1
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                String str;
                Function function;
                AccessibilityAction accessibilityAction = (AccessibilityAction) obj;
                AccessibilityAction accessibilityAction2 = (AccessibilityAction) obj2;
                if (accessibilityAction == null || (str = accessibilityAction.label) == null) {
                    str = accessibilityAction2.label;
                }
                if (accessibilityAction == null || (function = accessibilityAction.action) == null) {
                    function = accessibilityAction2.action;
                }
                return new AccessibilityAction(str, function);
            }
        };
        GetTextLayoutResult = new SemanticsPropertyKey("GetTextLayoutResult", true, semanticsPropertiesKt$ActionPropertyKey$1);
        OnClick = new SemanticsPropertyKey("OnClick", true, semanticsPropertiesKt$ActionPropertyKey$1);
        OnLongClick = new SemanticsPropertyKey("OnLongClick", true, semanticsPropertiesKt$ActionPropertyKey$1);
        ScrollBy = new SemanticsPropertyKey("ScrollBy", true, semanticsPropertiesKt$ActionPropertyKey$1);
        ScrollByOffset = new SemanticsPropertyKey("ScrollByOffset", null, 2, null);
        ScrollToIndex = new SemanticsPropertyKey("ScrollToIndex", true, semanticsPropertiesKt$ActionPropertyKey$1);
        OnAutofillText = new SemanticsPropertyKey("OnAutofillText", true, semanticsPropertiesKt$ActionPropertyKey$1);
        SetProgress = new SemanticsPropertyKey("SetProgress", true, semanticsPropertiesKt$ActionPropertyKey$1);
        SetSelection = new SemanticsPropertyKey("SetSelection", true, semanticsPropertiesKt$ActionPropertyKey$1);
        SetText = new SemanticsPropertyKey("SetText", true, semanticsPropertiesKt$ActionPropertyKey$1);
        SetTextSubstitution = new SemanticsPropertyKey("SetTextSubstitution", true, semanticsPropertiesKt$ActionPropertyKey$1);
        ShowTextSubstitution = new SemanticsPropertyKey("ShowTextSubstitution", true, semanticsPropertiesKt$ActionPropertyKey$1);
        ClearTextSubstitution = new SemanticsPropertyKey("ClearTextSubstitution", true, semanticsPropertiesKt$ActionPropertyKey$1);
        InsertTextAtCursor = new SemanticsPropertyKey("InsertTextAtCursor", true, semanticsPropertiesKt$ActionPropertyKey$1);
        OnImeAction = new SemanticsPropertyKey("PerformImeAction", true, semanticsPropertiesKt$ActionPropertyKey$1);
        new SemanticsPropertyKey("PerformImeAction", true, semanticsPropertiesKt$ActionPropertyKey$1);
        CopyText = new SemanticsPropertyKey("CopyText", true, semanticsPropertiesKt$ActionPropertyKey$1);
        CutText = new SemanticsPropertyKey("CutText", true, semanticsPropertiesKt$ActionPropertyKey$1);
        PasteText = new SemanticsPropertyKey("PasteText", true, semanticsPropertiesKt$ActionPropertyKey$1);
        Expand = new SemanticsPropertyKey("Expand", true, semanticsPropertiesKt$ActionPropertyKey$1);
        Collapse = new SemanticsPropertyKey("Collapse", true, semanticsPropertiesKt$ActionPropertyKey$1);
        Dismiss = new SemanticsPropertyKey("Dismiss", true, semanticsPropertiesKt$ActionPropertyKey$1);
        RequestFocus = new SemanticsPropertyKey("RequestFocus", true, semanticsPropertiesKt$ActionPropertyKey$1);
        CustomActions = new SemanticsPropertyKey("CustomActions", true);
        PageUp = new SemanticsPropertyKey("PageUp", true, semanticsPropertiesKt$ActionPropertyKey$1);
        PageLeft = new SemanticsPropertyKey("PageLeft", true, semanticsPropertiesKt$ActionPropertyKey$1);
        PageDown = new SemanticsPropertyKey("PageDown", true, semanticsPropertiesKt$ActionPropertyKey$1);
        PageRight = new SemanticsPropertyKey("PageRight", true, semanticsPropertiesKt$ActionPropertyKey$1);
        GetScrollViewportLength = new SemanticsPropertyKey("GetScrollViewportLength", true, semanticsPropertiesKt$ActionPropertyKey$1);
    }

    private SemanticsActions() {
    }
}
