package com.android.wm.shell.windowdecor;

import android.widget.ImageButton;
import com.android.wm.shell.apptoweb.OpenByDefaultDialog;
import com.android.wm.shell.shared.desktopmode.DesktopModeTransitionSource;
import com.android.wm.shell.windowdecor.DesktopModeWindowDecoration.AnonymousClass2;
import com.android.wm.shell.windowdecor.viewholder.AppHeaderViewHolder;
import com.android.wm.shell.windowdecor.viewholder.MultiTaskingHeaderViewHolder;
import com.samsung.android.rune.CoreRune;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;

/* loaded from: classes3.dex */
public final /* synthetic */ class DesktopModeWindowDecoration$$ExternalSyntheticLambda6 implements Function0 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ DesktopModeWindowDecoration f$0;

    public /* synthetic */ DesktopModeWindowDecoration$$ExternalSyntheticLambda6(DesktopModeWindowDecoration desktopModeWindowDecoration, int i) {
        this.$r8$classId = i;
        this.f$0 = desktopModeWindowDecoration;
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        ImageButton imageButton;
        switch (this.$r8$classId) {
            case 0:
                DesktopModeWindowDecoration desktopModeWindowDecoration = this.f$0;
                desktopModeWindowDecoration.getClass();
                if (CoreRune.MW_CAPTION) {
                    final MultiTaskingHeaderViewHolder multiTaskingHeaderViewHolderAsMultiTaskingAppHeader = DesktopModeWindowDecoration.asMultiTaskingAppHeader(desktopModeWindowDecoration.mWindowDecorViewHolder);
                    if (multiTaskingHeaderViewHolderAsMultiTaskingAppHeader != null && (imageButton = multiTaskingHeaderViewHolderAsMultiTaskingAppHeader.maximizeWindowButton) != null) {
                        imageButton.post(new Runnable() { // from class: com.android.wm.shell.windowdecor.viewholder.MultiTaskingHeaderViewHolder$requestAccessibilityFocus$1
                            @Override // java.lang.Runnable
                            public final void run() {
                                multiTaskingHeaderViewHolderAsMultiTaskingAppHeader.maximizeWindowButton.sendAccessibilityEvent(8);
                            }
                        });
                    }
                } else {
                    final AppHeaderViewHolder appHeaderViewHolderAsAppHeader = DesktopModeWindowDecoration.asAppHeader(desktopModeWindowDecoration.mWindowDecorViewHolder);
                    if (appHeaderViewHolderAsAppHeader != null) {
                        appHeaderViewHolderAsAppHeader.maximizeWindowButton.post(new Runnable() { // from class: com.android.wm.shell.windowdecor.viewholder.AppHeaderViewHolder$requestAccessibilityFocus$1
                            @Override // java.lang.Runnable
                            public final void run() {
                                appHeaderViewHolderAsAppHeader.maximizeWindowButton.sendAccessibilityEvent(8);
                            }
                        });
                    }
                }
                break;
            case 1:
                this.f$0.closeHandleMenu();
                break;
            case 2:
                this.f$0.mOnToDesktopClickListener.accept(DesktopModeTransitionSource.APP_HANDLE_MENU_BUTTON);
                break;
            case 3:
                DesktopModeWindowDecoration desktopModeWindowDecoration2 = this.f$0;
                if (desktopModeWindowDecoration2.mOpenByDefaultDialog == null) {
                    desktopModeWindowDecoration2.mOpenByDefaultDialog = new OpenByDefaultDialog(desktopModeWindowDecoration2.mContext, desktopModeWindowDecoration2.mTaskInfo, desktopModeWindowDecoration2.mTaskSurface, desktopModeWindowDecoration2.mDisplayController, desktopModeWindowDecoration2.mTaskResourceLoader, desktopModeWindowDecoration2.mSurfaceControlTransactionSupplier, desktopModeWindowDecoration2.mMainDispatcher, desktopModeWindowDecoration2.mBgScope, desktopModeWindowDecoration2.new AnonymousClass2());
                }
                break;
            case 4:
                this.f$0.closeHandleMenu();
                break;
            case 5:
                this.f$0.closeHandleMenu();
                break;
            case 6:
                this.f$0.closeMaximizeMenu();
                break;
            case 7:
                this.f$0.closeManageWindowsMenu();
                break;
            default:
                this.f$0.closeManageWindowsMenu();
                break;
        }
        return Unit.INSTANCE;
    }
}
