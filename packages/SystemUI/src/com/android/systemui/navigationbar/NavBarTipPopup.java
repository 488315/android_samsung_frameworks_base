package com.android.systemui.navigationbar;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import com.android.keyguard.StrongAuthPopup$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.systemui.basic.util.LogWrapper;
import com.samsung.android.widget.SemTipPopup;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class NavBarTipPopup {
    public final Context context;
    public int currentMessage;
    public final Handler handler;
    public boolean isTipPopupShowing;
    public final LogWrapper logWrapper;
    public int navBarWidth;
    public final NavBarTipPopup$onAttachStateChangeListener$1 onAttachStateChangeListener;
    public final View tipLayout;
    public SemTipPopup tipPopup;
    public final WindowManager windowManager;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public NavBarTipPopup(Context context, WindowManager windowManager, LogWrapper logWrapper) {
        this.context = context;
        this.windowManager = windowManager;
        this.logWrapper = logWrapper;
        View inflate = LayoutInflater.from(context).inflate(R.layout.navbar_tip_popup, (ViewGroup) null);
        this.tipLayout = inflate;
        this.handler = new Handler();
        inflate.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() { // from class: com.android.systemui.navigationbar.NavBarTipPopup$onAttachStateChangeListener$1
            @Override // android.view.View.OnAttachStateChangeListener
            public final void onViewAttachedToWindow(View view) {
                final NavBarTipPopup navBarTipPopup = NavBarTipPopup.this;
                if (view == navBarTipPopup.tipLayout) {
                    navBarTipPopup.isTipPopupShowing = true;
                    int i = navBarTipPopup.navBarWidth / 2;
                    int m = StrongAuthPopup$$ExternalSyntheticOutline0.m(navBarTipPopup.context, R.dimen.navbar_tip_window_padding_bottom, navBarTipPopup.context.getResources().getDimensionPixelOffset(R.dimen.navbar_tip_window_height));
                    SemTipPopup semTipPopup = new SemTipPopup(navBarTipPopup.tipLayout);
                    navBarTipPopup.tipPopup = semTipPopup;
                    semTipPopup.setMessage(navBarTipPopup.context.getResources().getString(navBarTipPopup.currentMessage));
                    SemTipPopup semTipPopup2 = navBarTipPopup.tipPopup;
                    Intrinsics.checkNotNull(semTipPopup2);
                    semTipPopup2.setExpanded(true);
                    SemTipPopup semTipPopup3 = navBarTipPopup.tipPopup;
                    Intrinsics.checkNotNull(semTipPopup3);
                    semTipPopup3.setOutsideTouchEnabled(true);
                    SemTipPopup semTipPopup4 = navBarTipPopup.tipPopup;
                    Intrinsics.checkNotNull(semTipPopup4);
                    semTipPopup4.setTargetPosition(i, m);
                    SemTipPopup semTipPopup5 = navBarTipPopup.tipPopup;
                    Intrinsics.checkNotNull(semTipPopup5);
                    semTipPopup5.setOnStateChangeListener(new SemTipPopup.OnStateChangeListener() { // from class: com.android.systemui.navigationbar.NavBarTipPopup$showTipPopup$1
                        public final void onStateChanged(int i2) {
                            if (i2 == 0) {
                                NavBarTipPopup.this.hide();
                            }
                        }
                    });
                    navBarTipPopup.handler.post(new Runnable() { // from class: com.android.systemui.navigationbar.NavBarTipPopup$showTipPopup$2
                        @Override // java.lang.Runnable
                        public final void run() {
                            if (NavBarTipPopup.this.tipLayout.isAttachedToWindow()) {
                                SemTipPopup semTipPopup6 = NavBarTipPopup.this.tipPopup;
                                Intrinsics.checkNotNull(semTipPopup6);
                                semTipPopup6.show(0);
                            }
                        }
                    });
                }
            }

            @Override // android.view.View.OnAttachStateChangeListener
            public final void onViewDetachedFromWindow(View view) {
                NavBarTipPopup navBarTipPopup = NavBarTipPopup.this;
                if (view == navBarTipPopup.tipLayout) {
                    SemTipPopup semTipPopup = navBarTipPopup.tipPopup;
                    if (semTipPopup != null) {
                        Intrinsics.checkNotNull(semTipPopup);
                        if (semTipPopup.isShowing()) {
                            SemTipPopup semTipPopup2 = NavBarTipPopup.this.tipPopup;
                            Intrinsics.checkNotNull(semTipPopup2);
                            semTipPopup2.dismiss(false);
                            NavBarTipPopup.this.tipPopup = null;
                        }
                    }
                    NavBarTipPopup.this.hide();
                    NavBarTipPopup.this.isTipPopupShowing = false;
                }
            }
        });
    }

    public final void hide() {
        if (this.isTipPopupShowing) {
            this.tipLayout.animate().cancel();
            try {
                this.windowManager.removeViewImmediate(this.tipLayout);
            } catch (RuntimeException e) {
                this.logWrapper.e("Tip", "hide fail=" + e.getStackTrace());
            }
        }
    }
}
