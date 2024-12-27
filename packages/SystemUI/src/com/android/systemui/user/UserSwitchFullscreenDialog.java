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
import com.android.systemui.statusbar.phone.SystemUIDialog;
import com.android.systemui.user.ui.binder.UserSwitcherViewBinder;
import com.android.systemui.user.ui.viewmodel.UserSwitcherViewModel;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class UserSwitchFullscreenDialog extends SystemUIDialog {
    public final FalsingCollector falsingCollector;
    public final UserSwitcherViewModel userSwitcherViewModel;

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
            throw new IllegalStateException("Required value was null.".toString());
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
        UserSwitchFullscreenDialog$onCreate$2 userSwitchFullscreenDialog$onCreate$2 = new UserSwitchFullscreenDialog$onCreate$2(this);
        userSwitcherViewBinder.getClass();
        UserSwitcherViewBinder.bind(viewGroup, userSwitcherViewModel, layoutInflater, falsingCollector, userSwitchFullscreenDialog$onCreate$2);
    }
}
