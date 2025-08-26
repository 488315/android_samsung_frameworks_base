package androidx.compose.foundation.text.modifiers;

import androidx.compose.foundation.text.TextDragObserver;
import androidx.compose.foundation.text.selection.MouseSelectionObserver;
import androidx.compose.foundation.text.selection.MultiWidgetSelectionDelegate;
import androidx.compose.foundation.text.selection.SelectionAdjustment;
import androidx.compose.foundation.text.selection.SelectionGesturesKt;
import androidx.compose.foundation.text.selection.SelectionRegistrar;
import androidx.compose.foundation.text.selection.SelectionRegistrarKt;
import androidx.compose.runtime.DynamicProvidableCompositionLocal;
import androidx.compose.runtime.RememberObserver;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.input.pointer.PointerHoverIconModifierElement;
import androidx.compose.ui.input.pointer.PointerIcon;
import androidx.compose.ui.layout.LayoutCoordinates;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
public final class SelectionController implements RememberObserver {
    public final Modifier modifier;
    public StaticTextSelectionParams params;
    public final long selectableId;

    public /* synthetic */ SelectionController(long j, SelectionRegistrar selectionRegistrar, long j2, StaticTextSelectionParams staticTextSelectionParams, DefaultConstructorMarker defaultConstructorMarker) {
        this(j, selectionRegistrar, j2, staticTextSelectionParams);
    }

    @Override // androidx.compose.runtime.RememberObserver
    public final void onRemembered() {
        new MultiWidgetSelectionDelegate(this.selectableId, new Function0() { // from class: androidx.compose.foundation.text.modifiers.SelectionController.onRemembered.1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return SelectionController.this.params.layoutCoordinates;
            }
        }, new Function0() { // from class: androidx.compose.foundation.text.modifiers.SelectionController.onRemembered.2
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return SelectionController.this.params.textLayoutResult;
            }
        });
        throw null;
    }

    private SelectionController(final long j, SelectionRegistrar selectionRegistrar, long j2, StaticTextSelectionParams staticTextSelectionParams) {
        this.selectableId = j;
        this.params = staticTextSelectionParams;
        final Function0 function0 = new Function0() { // from class: androidx.compose.foundation.text.modifiers.SelectionController$modifier$1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return this.this$0.params.layoutCoordinates;
            }
        };
        final SelectionRegistrar selectionRegistrar2 = null;
        TextDragObserver textDragObserver = new TextDragObserver(selectionRegistrar2, j) { // from class: androidx.compose.foundation.text.modifiers.SelectionControllerKt$makeSelectionModifier$longPressDragObserver$1
            {
                Offset.Companion.getClass();
            }

            @Override // androidx.compose.foundation.text.TextDragObserver
            public final void onCancel() {
                DynamicProvidableCompositionLocal dynamicProvidableCompositionLocal = SelectionRegistrarKt.LocalSelectionRegistrar;
            }

            @Override // androidx.compose.foundation.text.TextDragObserver
            /* renamed from: onDrag-k-4lQ0M */
            public final void mo203onDragk4lQ0M(long j3) {
                LayoutCoordinates layoutCoordinates = (LayoutCoordinates) this.$layoutCoordinates.invoke();
                if (layoutCoordinates == null || !layoutCoordinates.isAttached()) {
                    return;
                }
                DynamicProvidableCompositionLocal dynamicProvidableCompositionLocal = SelectionRegistrarKt.LocalSelectionRegistrar;
            }

            @Override // androidx.compose.foundation.text.TextDragObserver
            /* renamed from: onStart-k-4lQ0M */
            public final void mo204onStartk4lQ0M(long j3) {
                LayoutCoordinates layoutCoordinates = (LayoutCoordinates) this.$layoutCoordinates.invoke();
                if (layoutCoordinates == null) {
                    DynamicProvidableCompositionLocal dynamicProvidableCompositionLocal = SelectionRegistrarKt.LocalSelectionRegistrar;
                } else if (layoutCoordinates.isAttached()) {
                    SelectionAdjustment.Companion.getClass();
                    SelectionAdjustment.Companion companion = SelectionAdjustment.Companion.$$INSTANCE;
                    throw null;
                }
            }

            @Override // androidx.compose.foundation.text.TextDragObserver
            public final void onStop() {
                DynamicProvidableCompositionLocal dynamicProvidableCompositionLocal = SelectionRegistrarKt.LocalSelectionRegistrar;
            }

            @Override // androidx.compose.foundation.text.TextDragObserver
            /* renamed from: onDown-k-4lQ0M */
            public final void mo202onDownk4lQ0M() {
            }

            @Override // androidx.compose.foundation.text.TextDragObserver
            public final void onUp() {
            }
        };
        Modifier modifierSelectionGestureInput = SelectionGesturesKt.selectionGestureInput(Modifier.Companion, new MouseSelectionObserver(selectionRegistrar2, j) { // from class: androidx.compose.foundation.text.modifiers.SelectionControllerKt$makeSelectionModifier$mouseSelectionObserver$1
            {
                Offset.Companion.getClass();
            }

            @Override // androidx.compose.foundation.text.selection.MouseSelectionObserver
            /* renamed from: onDrag-3MmeM6k, reason: not valid java name */
            public final boolean mo230onDrag3MmeM6k(long j3, SelectionAdjustment selectionAdjustment) {
                LayoutCoordinates layoutCoordinates = (LayoutCoordinates) this.$layoutCoordinates.invoke();
                if (layoutCoordinates == null) {
                    return true;
                }
                if (!layoutCoordinates.isAttached()) {
                    return false;
                }
                DynamicProvidableCompositionLocal dynamicProvidableCompositionLocal = SelectionRegistrarKt.LocalSelectionRegistrar;
                return false;
            }

            @Override // androidx.compose.foundation.text.selection.MouseSelectionObserver
            public final void onDragDone() {
                throw null;
            }

            @Override // androidx.compose.foundation.text.selection.MouseSelectionObserver
            /* renamed from: onStart-3MmeM6k, reason: not valid java name */
            public final boolean mo231onStart3MmeM6k(long j3, SelectionAdjustment selectionAdjustment) {
                LayoutCoordinates layoutCoordinates = (LayoutCoordinates) this.$layoutCoordinates.invoke();
                if (layoutCoordinates == null || !layoutCoordinates.isAttached()) {
                    return false;
                }
                throw null;
            }
        }, textDragObserver);
        PointerIcon.Companion.getClass();
        this.modifier = modifierSelectionGestureInput.then(new PointerHoverIconModifierElement(PointerIcon.Companion.Text, false));
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public SelectionController(long j, SelectionRegistrar selectionRegistrar, long j2, StaticTextSelectionParams staticTextSelectionParams, int i, DefaultConstructorMarker defaultConstructorMarker) {
        if ((i & 8) != 0) {
            StaticTextSelectionParams.Companion.getClass();
            staticTextSelectionParams = StaticTextSelectionParams.Empty;
        }
        this(j, selectionRegistrar, j2, staticTextSelectionParams, null);
    }

    @Override // androidx.compose.runtime.RememberObserver
    public final void onAbandoned() {
    }

    @Override // androidx.compose.runtime.RememberObserver
    public final void onForgotten() {
    }
}
