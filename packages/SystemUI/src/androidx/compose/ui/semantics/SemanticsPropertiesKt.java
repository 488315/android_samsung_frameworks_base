package androidx.compose.ui.semantics;

import androidx.compose.ui.state.ToggleableState;
import com.sec.ims.scab.CABContract;
import java.util.Collections;
import java.util.List;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.MutablePropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.jvm.internal.ReflectionFactory;
import kotlin.reflect.KProperty;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class SemanticsPropertiesKt {
    public static final /* synthetic */ KProperty[] $$delegatedProperties;

    static {
        MutablePropertyReference1Impl mutablePropertyReference1Impl = new MutablePropertyReference1Impl(SemanticsPropertiesKt.class, "stateDescription", "getStateDescription(Landroidx/compose/ui/semantics/SemanticsPropertyReceiver;)Ljava/lang/String;", 1);
        ReflectionFactory reflectionFactory = Reflection.factory;
        reflectionFactory.getClass();
        MutablePropertyReference1Impl mutablePropertyReference1Impl2 = new MutablePropertyReference1Impl(SemanticsPropertiesKt.class, "progressBarRangeInfo", "getProgressBarRangeInfo(Landroidx/compose/ui/semantics/SemanticsPropertyReceiver;)Landroidx/compose/ui/semantics/ProgressBarRangeInfo;", 1);
        reflectionFactory.getClass();
        $$delegatedProperties = new KProperty[]{mutablePropertyReference1Impl, mutablePropertyReference1Impl2, SemanticsPropertiesKt$$ExternalSyntheticOutline0.m(SemanticsPropertiesKt.class, "paneTitle", "getPaneTitle(Landroidx/compose/ui/semantics/SemanticsPropertyReceiver;)Ljava/lang/String;", 1, reflectionFactory), SemanticsPropertiesKt$$ExternalSyntheticOutline0.m(SemanticsPropertiesKt.class, "liveRegion", "getLiveRegion(Landroidx/compose/ui/semantics/SemanticsPropertyReceiver;)I", 1, reflectionFactory), SemanticsPropertiesKt$$ExternalSyntheticOutline0.m(SemanticsPropertiesKt.class, "focused", "getFocused(Landroidx/compose/ui/semantics/SemanticsPropertyReceiver;)Z", 1, reflectionFactory), SemanticsPropertiesKt$$ExternalSyntheticOutline0.m(SemanticsPropertiesKt.class, "isContainer", "isContainer(Landroidx/compose/ui/semantics/SemanticsPropertyReceiver;)Z", 1, reflectionFactory), SemanticsPropertiesKt$$ExternalSyntheticOutline0.m(SemanticsPropertiesKt.class, "isTraversalGroup", "isTraversalGroup(Landroidx/compose/ui/semantics/SemanticsPropertyReceiver;)Z", 1, reflectionFactory), SemanticsPropertiesKt$$ExternalSyntheticOutline0.m(SemanticsPropertiesKt.class, "contentType", "getContentType(Landroidx/compose/ui/semantics/SemanticsPropertyReceiver;)Landroidx/compose/ui/autofill/ContentType;", 1, reflectionFactory), SemanticsPropertiesKt$$ExternalSyntheticOutline0.m(SemanticsPropertiesKt.class, "contentDataType", "getContentDataType(Landroidx/compose/ui/semantics/SemanticsPropertyReceiver;)Landroidx/compose/ui/autofill/ContentDataType;", 1, reflectionFactory), SemanticsPropertiesKt$$ExternalSyntheticOutline0.m(SemanticsPropertiesKt.class, "traversalIndex", "getTraversalIndex(Landroidx/compose/ui/semantics/SemanticsPropertyReceiver;)F", 1, reflectionFactory), SemanticsPropertiesKt$$ExternalSyntheticOutline0.m(SemanticsPropertiesKt.class, "horizontalScrollAxisRange", "getHorizontalScrollAxisRange(Landroidx/compose/ui/semantics/SemanticsPropertyReceiver;)Landroidx/compose/ui/semantics/ScrollAxisRange;", 1, reflectionFactory), SemanticsPropertiesKt$$ExternalSyntheticOutline0.m(SemanticsPropertiesKt.class, "verticalScrollAxisRange", "getVerticalScrollAxisRange(Landroidx/compose/ui/semantics/SemanticsPropertyReceiver;)Landroidx/compose/ui/semantics/ScrollAxisRange;", 1, reflectionFactory), SemanticsPropertiesKt$$ExternalSyntheticOutline0.m(SemanticsPropertiesKt.class, CABContract.CABBusinessContactOrgan.ROLE, "getRole(Landroidx/compose/ui/semantics/SemanticsPropertyReceiver;)I", 1, reflectionFactory), SemanticsPropertiesKt$$ExternalSyntheticOutline0.m(SemanticsPropertiesKt.class, "testTag", "getTestTag(Landroidx/compose/ui/semantics/SemanticsPropertyReceiver;)Ljava/lang/String;", 1, reflectionFactory), SemanticsPropertiesKt$$ExternalSyntheticOutline0.m(SemanticsPropertiesKt.class, "textSubstitution", "getTextSubstitution(Landroidx/compose/ui/semantics/SemanticsPropertyReceiver;)Landroidx/compose/ui/text/AnnotatedString;", 1, reflectionFactory), SemanticsPropertiesKt$$ExternalSyntheticOutline0.m(SemanticsPropertiesKt.class, "isShowingTextSubstitution", "isShowingTextSubstitution(Landroidx/compose/ui/semantics/SemanticsPropertyReceiver;)Z", 1, reflectionFactory), SemanticsPropertiesKt$$ExternalSyntheticOutline0.m(SemanticsPropertiesKt.class, "editableText", "getEditableText(Landroidx/compose/ui/semantics/SemanticsPropertyReceiver;)Landroidx/compose/ui/text/AnnotatedString;", 1, reflectionFactory), SemanticsPropertiesKt$$ExternalSyntheticOutline0.m(SemanticsPropertiesKt.class, "textSelectionRange", "getTextSelectionRange(Landroidx/compose/ui/semantics/SemanticsPropertyReceiver;)J", 1, reflectionFactory), SemanticsPropertiesKt$$ExternalSyntheticOutline0.m(SemanticsPropertiesKt.class, "imeAction", "getImeAction(Landroidx/compose/ui/semantics/SemanticsPropertyReceiver;)I", 1, reflectionFactory), SemanticsPropertiesKt$$ExternalSyntheticOutline0.m(SemanticsPropertiesKt.class, "selected", "getSelected(Landroidx/compose/ui/semantics/SemanticsPropertyReceiver;)Z", 1, reflectionFactory), SemanticsPropertiesKt$$ExternalSyntheticOutline0.m(SemanticsPropertiesKt.class, "collectionInfo", "getCollectionInfo(Landroidx/compose/ui/semantics/SemanticsPropertyReceiver;)Landroidx/compose/ui/semantics/CollectionInfo;", 1, reflectionFactory), SemanticsPropertiesKt$$ExternalSyntheticOutline0.m(SemanticsPropertiesKt.class, "collectionItemInfo", "getCollectionItemInfo(Landroidx/compose/ui/semantics/SemanticsPropertyReceiver;)Landroidx/compose/ui/semantics/CollectionItemInfo;", 1, reflectionFactory), SemanticsPropertiesKt$$ExternalSyntheticOutline0.m(SemanticsPropertiesKt.class, "toggleableState", "getToggleableState(Landroidx/compose/ui/semantics/SemanticsPropertyReceiver;)Landroidx/compose/ui/state/ToggleableState;", 1, reflectionFactory), SemanticsPropertiesKt$$ExternalSyntheticOutline0.m(SemanticsPropertiesKt.class, "isEditable", "isEditable(Landroidx/compose/ui/semantics/SemanticsPropertyReceiver;)Z", 1, reflectionFactory), SemanticsPropertiesKt$$ExternalSyntheticOutline0.m(SemanticsPropertiesKt.class, "maxTextLength", "getMaxTextLength(Landroidx/compose/ui/semantics/SemanticsPropertyReceiver;)I", 1, reflectionFactory), SemanticsPropertiesKt$$ExternalSyntheticOutline0.m(SemanticsPropertiesKt.class, "customActions", "getCustomActions(Landroidx/compose/ui/semantics/SemanticsPropertyReceiver;)Ljava/util/List;", 1, reflectionFactory)};
        SemanticsProperties semanticsProperties = SemanticsProperties.INSTANCE;
        semanticsProperties.getClass();
        semanticsProperties.getClass();
        semanticsProperties.getClass();
        semanticsProperties.getClass();
        semanticsProperties.getClass();
        semanticsProperties.getClass();
        semanticsProperties.getClass();
        semanticsProperties.getClass();
        semanticsProperties.getClass();
        semanticsProperties.getClass();
        semanticsProperties.getClass();
        semanticsProperties.getClass();
        semanticsProperties.getClass();
        semanticsProperties.getClass();
        semanticsProperties.getClass();
        semanticsProperties.getClass();
        semanticsProperties.getClass();
        semanticsProperties.getClass();
        semanticsProperties.getClass();
        semanticsProperties.getClass();
        semanticsProperties.getClass();
        semanticsProperties.getClass();
        semanticsProperties.getClass();
        semanticsProperties.getClass();
        semanticsProperties.getClass();
        SemanticsActions.INSTANCE.getClass();
    }

    public static final void disabled(SemanticsPropertyReceiver semanticsPropertyReceiver) {
        SemanticsProperties.INSTANCE.getClass();
        ((SemanticsConfiguration) semanticsPropertyReceiver).set(SemanticsProperties.Disabled, Unit.INSTANCE);
    }

    public static void getScrollViewportLength$default(SemanticsPropertyReceiver semanticsPropertyReceiver, final Function0 function0) {
        SemanticsActions.INSTANCE.getClass();
        ((SemanticsConfiguration) semanticsPropertyReceiver).set(SemanticsActions.GetScrollViewportLength, new AccessibilityAction(null, new Function1() { // from class: androidx.compose.ui.semantics.SemanticsPropertiesKt$getScrollViewportLength$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                boolean z;
                List list = (List) obj;
                Float f = (Float) Function0.this.invoke();
                if (f == null) {
                    z = false;
                } else {
                    list.add(f);
                    z = true;
                }
                return Boolean.valueOf(z);
            }
        }));
    }

    public static void getTextLayoutResult$default(SemanticsPropertyReceiver semanticsPropertyReceiver, Function1 function1) {
        SemanticsActions.INSTANCE.getClass();
        ((SemanticsConfiguration) semanticsPropertyReceiver).set(SemanticsActions.GetTextLayoutResult, new AccessibilityAction(null, function1));
    }

    public static final void hideFromAccessibility(SemanticsPropertyReceiver semanticsPropertyReceiver) {
        SemanticsProperties.INSTANCE.getClass();
        ((SemanticsConfiguration) semanticsPropertyReceiver).set(SemanticsProperties.HideFromAccessibility, Unit.INSTANCE);
    }

    public static final void onClick(SemanticsPropertyReceiver semanticsPropertyReceiver, String str, Function0 function0) {
        SemanticsActions.INSTANCE.getClass();
        ((SemanticsConfiguration) semanticsPropertyReceiver).set(SemanticsActions.OnClick, new AccessibilityAction(str, function0));
    }

    public static final void onLongClick(SemanticsPropertyReceiver semanticsPropertyReceiver, String str, Function0 function0) {
        SemanticsActions.INSTANCE.getClass();
        ((SemanticsConfiguration) semanticsPropertyReceiver).set(SemanticsActions.OnLongClick, new AccessibilityAction(str, function0));
    }

    public static final void setContentDescription(SemanticsPropertyReceiver semanticsPropertyReceiver, String str) {
        SemanticsProperties.INSTANCE.getClass();
        ((SemanticsConfiguration) semanticsPropertyReceiver).set(SemanticsProperties.ContentDescription, Collections.singletonList(str));
    }

    public static final void setCustomActions(SemanticsPropertyReceiver semanticsPropertyReceiver, List list) {
        SemanticsActions.INSTANCE.getClass();
        SemanticsPropertyKey semanticsPropertyKey = SemanticsActions.CustomActions;
        KProperty kProperty = $$delegatedProperties[25];
        semanticsPropertyKey.setValue(semanticsPropertyReceiver, list);
    }

    /* renamed from: setLiveRegion-hR3wRGc, reason: not valid java name */
    public static final void m716setLiveRegionhR3wRGc(SemanticsPropertyReceiver semanticsPropertyReceiver) {
        SemanticsProperties.INSTANCE.getClass();
        SemanticsPropertyKey semanticsPropertyKey = SemanticsProperties.LiveRegion;
        KProperty kProperty = $$delegatedProperties[3];
        semanticsPropertyKey.setValue(semanticsPropertyReceiver, LiveRegionMode.m712boximpl());
    }

    public static final void setPaneTitle(SemanticsPropertyReceiver semanticsPropertyReceiver, String str) {
        SemanticsProperties.INSTANCE.getClass();
        SemanticsPropertyKey semanticsPropertyKey = SemanticsProperties.PaneTitle;
        KProperty kProperty = $$delegatedProperties[2];
        semanticsPropertyKey.setValue(semanticsPropertyReceiver, str);
    }

    public static void setProgress$default(SemanticsPropertyReceiver semanticsPropertyReceiver, Function1 function1) {
        SemanticsActions.INSTANCE.getClass();
        ((SemanticsConfiguration) semanticsPropertyReceiver).set(SemanticsActions.SetProgress, new AccessibilityAction(null, function1));
    }

    public static final void setProgressBarRangeInfo(SemanticsPropertyReceiver semanticsPropertyReceiver, ProgressBarRangeInfo progressBarRangeInfo) {
        SemanticsProperties.INSTANCE.getClass();
        SemanticsPropertyKey semanticsPropertyKey = SemanticsProperties.ProgressBarRangeInfo;
        KProperty kProperty = $$delegatedProperties[1];
        semanticsPropertyKey.setValue(semanticsPropertyReceiver, progressBarRangeInfo);
    }

    /* renamed from: setRole-kuIjeqM, reason: not valid java name */
    public static final void m717setRolekuIjeqM(SemanticsPropertyReceiver semanticsPropertyReceiver, int i) {
        SemanticsProperties.INSTANCE.getClass();
        SemanticsPropertyKey semanticsPropertyKey = SemanticsProperties.Role;
        KProperty kProperty = $$delegatedProperties[12];
        semanticsPropertyKey.setValue(semanticsPropertyReceiver, Role.m713boximpl(i));
    }

    public static final void setStateDescription(SemanticsPropertyReceiver semanticsPropertyReceiver, String str) {
        SemanticsProperties.INSTANCE.getClass();
        SemanticsPropertyKey semanticsPropertyKey = SemanticsProperties.StateDescription;
        KProperty kProperty = $$delegatedProperties[0];
        semanticsPropertyKey.setValue(semanticsPropertyReceiver, str);
    }

    public static final void setToggleableState(SemanticsPropertyReceiver semanticsPropertyReceiver, ToggleableState toggleableState) {
        SemanticsProperties.INSTANCE.getClass();
        SemanticsPropertyKey semanticsPropertyKey = SemanticsProperties.ToggleableState;
        KProperty kProperty = $$delegatedProperties[22];
        semanticsPropertyKey.setValue(semanticsPropertyReceiver, toggleableState);
    }

    public static final void setTraversalGroup(SemanticsPropertyReceiver semanticsPropertyReceiver) {
        SemanticsProperties.INSTANCE.getClass();
        SemanticsPropertyKey semanticsPropertyKey = SemanticsProperties.IsTraversalGroup;
        KProperty kProperty = $$delegatedProperties[6];
        semanticsPropertyKey.setValue(semanticsPropertyReceiver, Boolean.TRUE);
    }
}
