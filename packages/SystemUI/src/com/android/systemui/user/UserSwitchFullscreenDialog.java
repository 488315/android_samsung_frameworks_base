package com.android.systemui.user;

import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import com.android.systemui.R;
import com.android.systemui.classifier.FalsingCollector;
import com.android.systemui.popup.util.PopupUIUtil;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import com.android.systemui.user.ui.binder.UserSwitcherViewBinder;
import com.android.systemui.user.ui.viewmodel.UserSwitcherViewModel;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* loaded from: classes3.dex */
public final class UserSwitchFullscreenDialog extends SystemUIDialog {
    public final FalsingCollector falsingCollector;
    public final UserSwitcherViewModel userSwitcherViewModel;

    /* renamed from: com.android.systemui.user.UserSwitchFullscreenDialog$onCreate$2, reason: invalid class name */
    final /* synthetic */ class AnonymousClass2 extends FunctionReferenceImpl implements Function0 {
        public AnonymousClass2(Object obj) {
            super(0, obj, UserSwitchFullscreenDialog.class, PopupUIUtil.EXTRA_SIM_CARD_TRAY_WATER_PROTECTION_POPUP_DISMISS, "dismiss()V", 0);
        }

        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            ((UserSwitchFullscreenDialog) this.receiver).dismiss();
            return Unit.INSTANCE;
        }
    }

    public UserSwitchFullscreenDialog(Context context, FalsingCollector falsingCollector, UserSwitcherViewModel userSwitcherViewModel) {
        super(context, R.style.Theme_UserSwitcherFullscreenDialog);
        this.falsingCollector = falsingCollector;
        this.userSwitcherViewModel = userSwitcherViewModel;
    }

    @Override // com.android.systemui.statusbar.phone.SystemUIDialog
    public final int getHeight() {
        return -1;
    }

    @Override // com.android.systemui.statusbar.phone.SystemUIDialog
    public final int getWidth() {
        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        Display display = getContext().getDisplay();
        if (display == null) {
            throw new IllegalStateException("Required value was null.");
        }
        display.getRealMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }

    @Override // com.android.systemui.statusbar.phone.SystemUIDialog, android.app.AlertDialog, android.app.Dialog
    public final void onCreate(Bundle bundle) {
        View decorView;
        WindowInsetsController windowInsetsController;
        super.onCreate(bundle);
        SystemUIDialog.setShowForAllUsers(this);
        setCanceledOnTouchOutside(true);
        Window window = getWindow();
        if (window != null && (decorView = window.getDecorView()) != null && (windowInsetsController = decorView.getWindowInsetsController()) != null) {
            windowInsetsController.setSystemBarsBehavior(2);
            windowInsetsController.hide(WindowInsets.Type.systemBars());
        }
        setContentView(LayoutInflater.from(getContext()).inflate(R.layout.user_switcher_fullscreen, (ViewGroup) null));
        UserSwitcherViewBinder userSwitcherViewBinder = UserSwitcherViewBinder.INSTANCE;
        ViewGroup viewGroup = (ViewGroup) requireViewById(R.id.user_switcher_root);
        UserSwitcherViewModel userSwitcherViewModel = this.userSwitcherViewModel;
        LayoutInflater layoutInflater = getLayoutInflater();
        FalsingCollector falsingCollector = this.falsingCollector;
        AnonymousClass2 anonymousClass2 = new AnonymousClass2(this);
        userSwitcherViewBinder.getClass();
        UserSwitcherViewBinder.bind(viewGroup, userSwitcherViewModel, layoutInflater, falsingCollector, anonymousClass2);
    }
}
