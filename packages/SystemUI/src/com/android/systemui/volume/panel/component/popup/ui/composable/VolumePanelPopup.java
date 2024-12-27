package com.android.systemui.volume.panel.component.popup.ui.composable;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.geometry.OffsetKt;
import androidx.compose.ui.geometry.Rect;
import androidx.compose.ui.layout.LayoutCoordinates;
import androidx.compose.ui.layout.LayoutCoordinatesKt;
import com.android.systemui.R;
import com.android.systemui.animation.DialogTransitionAnimator;
import com.android.systemui.animation.Expandable;
import com.android.systemui.animation.TransitionAnimator;
import com.android.systemui.statusbar.phone.ComponentSystemUIDialog;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import com.android.systemui.statusbar.phone.SystemUIDialogFactory;
import com.android.systemui.statusbar.phone.SystemUIDialogFactoryExtKt;
import kotlin.Unit;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class VolumePanelPopup {
    public static final Companion Companion = new Companion(null);
    public final SystemUIDialogFactory dialogFactory;
    public final DialogTransitionAnimator dialogTransitionAnimator;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public static int calculateGravity(LayoutCoordinates layoutCoordinates, float f) {
            Rect localBoundingBoxOf = LayoutCoordinatesKt.findRootCoordinates(layoutCoordinates).localBoundingBoxOf(layoutCoordinates, true);
            float m325getXimpl = Offset.m325getXimpl(OffsetKt.Offset((localBoundingBoxOf.getWidth() / 2.0f) + localBoundingBoxOf.left, localBoundingBoxOf.bottom));
            float f2 = f / 2;
            if (m325getXimpl < f2) {
                return 3;
            }
            return m325getXimpl > f2 ? 5 : 1;
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public VolumePanelPopup(SystemUIDialogFactory systemUIDialogFactory, DialogTransitionAnimator dialogTransitionAnimator) {
        this.dialogFactory = systemUIDialogFactory;
        this.dialogTransitionAnimator = dialogTransitionAnimator;
    }

    /* JADX WARN: Code restructure failed: missing block: B:4:0x0037, code lost:
    
        if (r8 == androidx.compose.runtime.Composer.Companion.Empty) goto L6;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void access$PopupComposable(final com.android.systemui.volume.panel.component.popup.ui.composable.VolumePanelPopup r19, final com.android.systemui.statusbar.phone.SystemUIDialog r20, final kotlin.jvm.functions.Function3 r21, final kotlin.jvm.functions.Function3 r22, androidx.compose.runtime.Composer r23, final int r24) {
        /*
            Method dump skipped, instructions count: 574
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.volume.panel.component.popup.ui.composable.VolumePanelPopup.access$PopupComposable(com.android.systemui.volume.panel.component.popup.ui.composable.VolumePanelPopup, com.android.systemui.statusbar.phone.SystemUIDialog, kotlin.jvm.functions.Function3, kotlin.jvm.functions.Function3, androidx.compose.runtime.Composer, int):void");
    }

    public final void show(Expandable expandable, int i, final ComposableLambdaImpl composableLambdaImpl, final ComposableLambdaImpl composableLambdaImpl2) {
        ComponentSystemUIDialog create$default = SystemUIDialogFactoryExtKt.create$default(this.dialogFactory, null, R.style.Theme_VolumePanel_Popup, Integer.valueOf(i), new ComposableLambdaImpl(1290029988, true, new Function3() { // from class: com.android.systemui.volume.panel.component.popup.ui.composable.VolumePanelPopup$show$dialog$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(3);
            }

            @Override // kotlin.jvm.functions.Function3
            public final Object invoke(Object obj, Object obj2, Object obj3) {
                ((Number) obj3).intValue();
                OpaqueKey opaqueKey = ComposerKt.invocation;
                VolumePanelPopup.access$PopupComposable(VolumePanelPopup.this, (SystemUIDialog) obj, composableLambdaImpl, composableLambdaImpl2, (Composer) obj2, 4104);
                return Unit.INSTANCE;
            }
        }), 5);
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
