package com.android.systemui.volume.panel.component.popup.ui.composable;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.geometry.Rect;
import androidx.compose.ui.layout.LayoutCoordinates;
import androidx.compose.ui.layout.LayoutCoordinatesKt;
import com.android.systemui.animation.DialogTransitionAnimator;
import com.android.systemui.animation.Expandable;
import com.android.systemui.animation.TransitionAnimator;
import com.android.systemui.statusbar.phone.ComponentSystemUIDialog;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import com.android.systemui.statusbar.phone.SystemUIDialogFactory;
import com.android.systemui.statusbar.phone.SystemUIDialogFactoryExtKt;
import com.android.systemui.volume.panel.component.popup.ui.composable.VolumePanelPopup;
import kotlin.Unit;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class VolumePanelPopup {
    public static final Companion Companion = new Companion(null);
    public final SystemUIDialogFactory dialogFactory;
    public final DialogTransitionAnimator dialogTransitionAnimator;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static int calculateGravity(LayoutCoordinates layoutCoordinates, float f) {
            Rect boundsInRoot = LayoutCoordinatesKt.boundsInRoot(layoutCoordinates);
            float f2 = boundsInRoot.right;
            float f3 = boundsInRoot.left;
            Offset.Companion companion = Offset.Companion;
            float intBitsToFloat = Float.intBitsToFloat((int) (((Float.floatToRawIntBits(((f2 - f3) / 2.0f) + f3) << 32) | (Float.floatToRawIntBits(boundsInRoot.bottom) & 4294967295L)) >> 32));
            float f4 = f / 2;
            if (intBitsToFloat < f4) {
                return 3;
            }
            return intBitsToFloat > f4 ? 5 : 1;
        }

        private Companion() {
        }
    }

    public VolumePanelPopup(SystemUIDialogFactory systemUIDialogFactory, DialogTransitionAnimator dialogTransitionAnimator) {
        this.dialogFactory = systemUIDialogFactory;
        this.dialogTransitionAnimator = dialogTransitionAnimator;
    }

    /* JADX WARN: Code restructure failed: missing block: B:38:0x0086, code lost:
    
        if (r14 == androidx.compose.runtime.Composer.Companion.Empty) goto L37;
     */
    /* JADX WARN: Code restructure failed: missing block: B:72:0x025f, code lost:
    
        if (r6 == androidx.compose.runtime.Composer.Companion.Empty) goto L80;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void PopupComposable(final com.android.systemui.statusbar.phone.SystemUIDialog r24, final kotlin.jvm.functions.Function3 r25, final kotlin.jvm.functions.Function3 r26, androidx.compose.runtime.Composer r27, final int r28) {
        /*
            Method dump skipped, instructions count: 677
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.volume.panel.component.popup.ui.composable.VolumePanelPopup.PopupComposable(com.android.systemui.statusbar.phone.SystemUIDialog, kotlin.jvm.functions.Function3, kotlin.jvm.functions.Function3, androidx.compose.runtime.Composer, int):void");
    }

    public final void show(Expandable expandable, int i, final ComposableLambdaImpl composableLambdaImpl, final ComposableLambdaImpl composableLambdaImpl2) {
        ComponentSystemUIDialog create$default = SystemUIDialogFactoryExtKt.create$default(this.dialogFactory, null, Integer.valueOf(i), null, new ComposableLambdaImpl(-1804575553, true, new Function3() { // from class: com.android.systemui.volume.panel.component.popup.ui.composable.VolumePanelPopup$show$dialog$1
            @Override // kotlin.jvm.functions.Function3
            public final Object invoke(Object obj, Object obj2, Object obj3) {
                SystemUIDialog systemUIDialog = (SystemUIDialog) obj;
                Composer composer = (Composer) obj2;
                int intValue = ((Number) obj3).intValue();
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventStart("com.android.systemui.volume.panel.component.popup.ui.composable.VolumePanelPopup.show.<anonymous> (VolumePanelPopup.kt:74)");
                }
                int i2 = intValue & 14;
                VolumePanelPopup.Companion companion = VolumePanelPopup.Companion;
                VolumePanelPopup.this.PopupComposable(systemUIDialog, composableLambdaImpl, composableLambdaImpl2, composer, i2);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
                return Unit.INSTANCE;
            }
        }), 21);
        DialogTransitionAnimator.Controller controller = null;
        if (expandable != null) {
            Expandable.Companion companion = Expandable.Companion;
            controller = expandable.dialogTransitionController(null);
        }
        if (controller == null) {
            create$default.show();
        } else {
            TransitionAnimator.Timings timings = DialogTransitionAnimator.TIMINGS;
            this.dialogTransitionAnimator.show(create$default, controller, false);
        }
    }
}
