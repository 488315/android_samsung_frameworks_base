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

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        new MultiWidgetSelectionDelegate(this.selectableId, new Function0() { // from class: androidx.compose.foundation.text.modifiers.SelectionController$onRemembered$1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return SelectionController.this.params.layoutCoordinates;
            }
        }, new Function0() { // from class: androidx.compose.foundation.text.modifiers.SelectionController$onRemembered$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return SelectionController.this.params.textLayoutResult;
            }
        });
        throw null;
    }

    private SelectionController(final long j, SelectionRegistrar selectionRegistrar, long j2, StaticTextSelectionParams staticTextSelectionParams) {
        Modifier then;
        this.selectableId = j;
        this.params = staticTextSelectionParams;
        final Function0 function0 = new Function0() { // from class: androidx.compose.foundation.text.modifiers.SelectionController$modifier$1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return SelectionController.this.params.layoutCoordinates;
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
            public final void mo202onDragk4lQ0M(long j3) {
                LayoutCoordinates layoutCoordinates = (LayoutCoordinates) Function0.this.invoke();
                if (layoutCoordinates == null || !layoutCoordinates.isAttached()) {
                    return;
                }
                DynamicProvidableCompositionLocal dynamicProvidableCompositionLocal = SelectionRegistrarKt.LocalSelectionRegistrar;
            }

            @Override // androidx.compose.foundation.text.TextDragObserver
            /* renamed from: onStart-k-4lQ0M */
            public final void mo203onStartk4lQ0M(long j3) {
                LayoutCoordinates layoutCoordinates = (LayoutCoordinates) Function0.this.invoke();
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
            public final void mo201onDownk4lQ0M() {
            }

            @Override // androidx.compose.foundation.text.TextDragObserver
            public final void onUp() {
            }
        };
        Modifier selectionGestureInput = SelectionGesturesKt.selectionGestureInput(Modifier.Companion, new MouseSelectionObserver(selectionRegistrar2, j) { // from class: androidx.compose.foundation.text.modifiers.SelectionControllerKt$makeSelectionModifier$mouseSelectionObserver$1
            {
                Offset.Companion.getClass();
            }

            @Override // androidx.compose.foundation.text.selection.MouseSelectionObserver
            /* renamed from: onDrag-3MmeM6k, reason: not valid java name */
            public final boolean mo229onDrag3MmeM6k(long j3, SelectionAdjustment selectionAdjustment) {
                LayoutCoordinates layoutCoordinates = (LayoutCoordinates) Function0.this.invoke();
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
            public final boolean mo230onStart3MmeM6k(long j3, SelectionAdjustment selectionAdjustment) {
                LayoutCoordinates layoutCoordinates = (LayoutCoordinates) Function0.this.invoke();
                if (layoutCoordinates == null || !layoutCoordinates.isAttached()) {
                    return false;
                }
                throw null;
            }
        }, textDragObserver);
        PointerIcon.Companion.getClass();
        then = selectionGestureInput.then(new PointerHoverIconModifierElement(PointerIcon.Companion.Text, false));
        this.modifier = then;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public SelectionController(long r9, androidx.compose.foundation.text.selection.SelectionRegistrar r11, long r12, androidx.compose.foundation.text.modifiers.StaticTextSelectionParams r14, int r15, kotlin.jvm.internal.DefaultConstructorMarker r16) {
        /*
            r8 = this;
            r0 = r15 & 8
            if (r0 == 0) goto Lb
            androidx.compose.foundation.text.modifiers.StaticTextSelectionParams$Companion r14 = androidx.compose.foundation.text.modifiers.StaticTextSelectionParams.Companion
            r14.getClass()
            androidx.compose.foundation.text.modifiers.StaticTextSelectionParams r14 = androidx.compose.foundation.text.modifiers.StaticTextSelectionParams.Empty
        Lb:
            r6 = r14
            r7 = 0
            r0 = r8
            r1 = r9
            r3 = r11
            r4 = r12
            r0.<init>(r1, r3, r4, r6, r7)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.text.modifiers.SelectionController.<init>(long, androidx.compose.foundation.text.selection.SelectionRegistrar, long, androidx.compose.foundation.text.modifiers.StaticTextSelectionParams, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }

    @Override // androidx.compose.runtime.RememberObserver
    public final void onAbandoned() {
    }

    @Override // androidx.compose.runtime.RememberObserver
    public final void onForgotten() {
    }
}
