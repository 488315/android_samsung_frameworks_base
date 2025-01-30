package com.android.systemui.statusbar.phone;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import com.android.systemui.demomode.DemoMode;
import com.android.systemui.demomode.DemoModeCommandReceiver;
import com.android.systemui.demomode.DemoModeController;
import com.android.systemui.navigationbar.NavigationBarController;
import com.android.systemui.plugins.DarkIconDispatcher;
import com.android.systemui.plugins.subscreen.SubRoom;
import com.android.systemui.statusbar.policy.QSClockIndicatorView;
import com.android.systemui.util.ViewController;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class StatusBarDemoMode extends ViewController implements DemoMode {
    public final QSClockIndicatorView mClockView;
    public final DemoModeController mDemoModeController;
    public final int mDisplayId;
    public final NavigationBarController mNavigationBarController;
    public final View mOperatorNameView;
    public final PhoneStatusBarTransitions mPhoneStatusBarTransitions;

    public StatusBarDemoMode(QSClockIndicatorView qSClockIndicatorView, View view, DemoModeController demoModeController, PhoneStatusBarTransitions phoneStatusBarTransitions, NavigationBarController navigationBarController, int i) {
        super(qSClockIndicatorView);
        this.mClockView = qSClockIndicatorView;
        this.mOperatorNameView = view;
        this.mDemoModeController = demoModeController;
        this.mPhoneStatusBarTransitions = phoneStatusBarTransitions;
        this.mNavigationBarController = navigationBarController;
        this.mDisplayId = i;
    }

    @Override // com.android.systemui.demomode.DemoMode
    public final List demoCommands() {
        ArrayList arrayList = new ArrayList();
        arrayList.add("bars");
        arrayList.add(SubRoom.EXTRA_VALUE_CLOCK);
        arrayList.add("operator");
        return arrayList;
    }

    @Override // com.android.systemui.demomode.DemoModeCommandReceiver
    public final void dispatchDemoCommand(Bundle bundle, String str) {
        if (str.equals(SubRoom.EXTRA_VALUE_CLOCK)) {
            DarkIconDispatcher.DarkReceiver darkReceiver = this.mClockView;
            if (darkReceiver instanceof DemoModeCommandReceiver) {
                ((DemoModeCommandReceiver) darkReceiver).dispatchDemoCommand(bundle, str);
            }
        }
        if (str.equals("operator")) {
            KeyEvent.Callback callback = this.mOperatorNameView;
            if (callback instanceof DemoModeCommandReceiver) {
                ((DemoModeCommandReceiver) callback).dispatchDemoCommand(bundle, str);
            }
        }
        if (str.equals("bars")) {
            String string = bundle.getString("mode");
            int i = "opaque".equals(string) ? 4 : "translucent".equals(string) ? 2 : "semi-transparent".equals(string) ? 1 : "transparent".equals(string) ? 0 : "warning".equals(string) ? 5 : -1;
            if (i != -1) {
                this.mPhoneStatusBarTransitions.transitionTo(i, true);
                this.mNavigationBarController.transitionTo(this.mDisplayId, i);
            }
        }
    }

    @Override // com.android.systemui.demomode.DemoModeCommandReceiver
    public final void onDemoModeFinished() {
        DarkIconDispatcher.DarkReceiver darkReceiver = this.mClockView;
        if (darkReceiver instanceof DemoModeCommandReceiver) {
            ((DemoModeCommandReceiver) darkReceiver).onDemoModeFinished();
        }
        KeyEvent.Callback callback = this.mOperatorNameView;
        if (callback instanceof DemoModeCommandReceiver) {
            ((DemoModeCommandReceiver) callback).onDemoModeFinished();
        }
    }

    @Override // com.android.systemui.util.ViewController
    public final void onViewAttached() {
        this.mDemoModeController.addCallback((DemoMode) this);
    }

    @Override // com.android.systemui.util.ViewController
    public final void onViewDetached() {
        this.mDemoModeController.removeCallback((DemoMode) this);
    }
}
