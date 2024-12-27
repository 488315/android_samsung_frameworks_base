package com.android.systemui.keyevent.domain.interactor;

import com.android.systemui.back.domain.interactor.BackActionInteractor;
import com.android.systemui.keyguard.domain.interactor.KeyguardKeyEventInteractor;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class SysUIKeyEventHandler {
    public static final Companion Companion = new Companion(null);
    public final BackActionInteractor backActionInteractor;
    public final KeyguardKeyEventInteractor keyguardKeyEventInteractor;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public SysUIKeyEventHandler(BackActionInteractor backActionInteractor, KeyguardKeyEventInteractor keyguardKeyEventInteractor) {
        this.backActionInteractor = backActionInteractor;
        this.keyguardKeyEventInteractor = keyguardKeyEventInteractor;
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x007a A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:12:0x007b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean dispatchKeyEvent(android.view.KeyEvent r8) {
        /*
            r7 = this;
            com.android.systemui.keyguard.domain.interactor.KeyguardKeyEventInteractor r0 = r7.keyguardKeyEventInteractor
            com.android.systemui.plugins.statusbar.StatusBarStateController r1 = r0.statusBarStateController
            boolean r2 = r1.isDozing()
            r3 = 1
            r4 = 0
            com.android.systemui.keyevent.domain.interactor.SysUIKeyEventHandler$Companion r5 = com.android.systemui.keyevent.domain.interactor.SysUIKeyEventHandler.Companion
            if (r2 == 0) goto L2d
            int r2 = r8.getKeyCode()
            r6 = 24
            if (r2 == r6) goto L1b
            r6 = 25
            if (r2 == r6) goto L1b
            goto L2d
        L1b:
            android.content.Context r1 = r0.context
            com.android.systemui.media.controls.util.MediaSessionLegacyHelperWrapper r0 = r0.mediaSessionLegacyHelperWrapper
            r0.getClass()
            android.media.session.MediaSessionLegacyHelper r0 = android.media.session.MediaSessionLegacyHelper.getHelper(r1)
            r1 = -2147483648(0xffffffff80000000, float:-0.0)
            r0.sendVolumeKeyEvent(r8, r1, r3)
        L2b:
            r0 = r3
            goto L78
        L2d:
            r5.getClass()
            int r2 = r8.getAction()
            if (r2 == 0) goto L77
            int r2 = r8.getKeyCode()
            boolean r2 = android.view.KeyEvent.isConfirmKey(r2)
            if (r2 == 0) goto L6a
            com.android.systemui.power.domain.interactor.PowerInteractor r2 = r0.powerInteractor
            kotlinx.coroutines.flow.ReadonlyStateFlow r2 = r2.detailedWakefulness
            kotlinx.coroutines.flow.StateFlow r2 = r2.$$delegate_0
            java.lang.Object r2 = r2.getValue()
            com.android.systemui.power.shared.model.WakefulnessModel r2 = (com.android.systemui.power.shared.model.WakefulnessModel) r2
            boolean r2 = r2.isAwake()
            if (r2 == 0) goto L6a
            int r1 = r1.getState()
            if (r1 == r3) goto L64
            r2 = 2
            if (r1 == r2) goto L5c
            goto L77
        L5c:
            r1 = 1065353216(0x3f800000, float:1.0)
            com.android.systemui.shade.ShadeController r0 = r0.shadeController
            r0.animateCollapseShade(r1, r4, r3, r4)
            goto L2b
        L64:
            com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager r0 = r0.statusBarKeyguardViewManager
            r0.showPrimaryBouncer(r3)
            goto L2b
        L6a:
            int r1 = r8.getKeyCode()
            r2 = 82
            if (r1 != r2) goto L77
            boolean r0 = r0.dispatchMenuKeyEvent()
            goto L78
        L77:
            r0 = r4
        L78:
            if (r0 == 0) goto L7b
            return r3
        L7b:
            int r0 = r8.getKeyCode()
            r1 = 4
            if (r0 != r1) goto L94
            r5.getClass()
            int r8 = r8.getAction()
            if (r8 == 0) goto L8c
            r4 = r3
        L8c:
            if (r4 == 0) goto L93
            com.android.systemui.back.domain.interactor.BackActionInteractor r7 = r7.backActionInteractor
            r7.onBackRequested()
        L93:
            return r3
        L94:
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyevent.domain.interactor.SysUIKeyEventHandler.dispatchKeyEvent(android.view.KeyEvent):boolean");
    }

    /* JADX WARN: Code restructure failed: missing block: B:97:0x0043, code lost:
    
        if (r8 != r12[r10 - 1]) goto L28;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean interceptMediaKey(final android.view.KeyEvent r19) {
        /*
            Method dump skipped, instructions count: 518
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyevent.domain.interactor.SysUIKeyEventHandler.interceptMediaKey(android.view.KeyEvent):boolean");
    }
}
