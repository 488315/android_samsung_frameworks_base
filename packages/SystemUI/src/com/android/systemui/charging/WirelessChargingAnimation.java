package com.android.systemui.charging;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.util.Slog;
import android.view.WindowManager;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.shade.NotificationShadeWindowControllerImpl;
import com.android.systemui.statusbar.phone.CentralSurfacesImpl;
import com.android.systemui.surfaceeffects.ripple.RippleShader;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class WirelessChargingAnimation {
    public static final boolean DEBUG = Log.isLoggable("WirelessChargingView", 3);
    public static WirelessChargingView mPreviousWirelessChargingView;
    public final WirelessChargingView mCurrentWirelessChargingView;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public interface Callback {
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class WirelessChargingView {
        public final Callback mCallback;
        public final AnonymousClass1 mHandler;
        public WirelessChargingLayout mNextView;
        public final WindowManager.LayoutParams mParams;
        public final UiEventLogger mUiEventLogger;
        public WirelessChargingLayout mView;
        public WindowManager mWM;

        /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
        enum WirelessChargingRippleEvent implements UiEventLogger.UiEventEnum {
            WIRELESS_RIPPLE_PLAYED(830);

            private final int mInt;

            WirelessChargingRippleEvent(int i) {
                this.mInt = i;
            }

            public final int getId() {
                return this.mInt;
            }
        }

        /* JADX WARN: Type inference failed for: r8v8, types: [com.android.systemui.charging.WirelessChargingAnimation$WirelessChargingView$1] */
        public WirelessChargingView(Context context, Looper looper, int i, int i2, Callback callback, boolean z, RippleShader.RippleShape rippleShape, UiEventLogger uiEventLogger) {
            WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
            this.mParams = layoutParams;
            this.mCallback = callback;
            this.mNextView = new WirelessChargingLayout(context, i, i2, z, rippleShape);
            this.mUiEventLogger = uiEventLogger;
            layoutParams.height = -1;
            layoutParams.width = -1;
            layoutParams.format = -3;
            layoutParams.type = 2009;
            layoutParams.setTitle("Charging Animation");
            layoutParams.layoutInDisplayCutoutMode = 3;
            layoutParams.setFitInsetsTypes(0);
            layoutParams.flags = 24;
            layoutParams.setTrustedOverlay();
            if (looper == null && (looper = Looper.myLooper()) == null) {
                throw new RuntimeException("Can't display wireless animation on a thread that has not called Looper.prepare()");
            }
            this.mHandler = new Handler(looper, null) { // from class: com.android.systemui.charging.WirelessChargingAnimation.WirelessChargingView.1
                @Override // android.os.Handler
                public final void handleMessage(Message message) {
                    int i3 = message.what;
                    WirelessChargingView wirelessChargingView = WirelessChargingView.this;
                    if (i3 != 0) {
                        if (i3 != 1) {
                            return;
                        }
                        wirelessChargingView.handleHide();
                        wirelessChargingView.mNextView = null;
                        return;
                    }
                    wirelessChargingView.getClass();
                    boolean z2 = WirelessChargingAnimation.DEBUG;
                    if (z2) {
                        Slog.d("WirelessChargingView", "HANDLE SHOW: " + wirelessChargingView + " mView=" + wirelessChargingView.mView + " mNextView=" + wirelessChargingView.mNextView);
                    }
                    if (wirelessChargingView.mView != wirelessChargingView.mNextView) {
                        wirelessChargingView.handleHide();
                        WirelessChargingLayout wirelessChargingLayout = wirelessChargingView.mNextView;
                        wirelessChargingView.mView = wirelessChargingLayout;
                        Context applicationContext = wirelessChargingLayout.getContext().getApplicationContext();
                        String opPackageName = wirelessChargingView.mView.getContext().getOpPackageName();
                        if (applicationContext == null) {
                            applicationContext = wirelessChargingView.mView.getContext();
                        }
                        wirelessChargingView.mWM = (WindowManager) applicationContext.getSystemService("window");
                        WindowManager.LayoutParams layoutParams2 = wirelessChargingView.mParams;
                        layoutParams2.packageName = opPackageName;
                        layoutParams2.hideTimeoutMilliseconds = 1500L;
                        if (wirelessChargingView.mView.getParent() != null) {
                            if (z2) {
                                Slog.d("WirelessChargingView", "REMOVE! " + wirelessChargingView.mView + " in " + wirelessChargingView);
                            }
                            wirelessChargingView.mWM.removeView(wirelessChargingView.mView);
                        }
                        if (z2) {
                            Slog.d("WirelessChargingView", "ADD! " + wirelessChargingView.mView + " in " + wirelessChargingView);
                        }
                        try {
                            Callback callback2 = wirelessChargingView.mCallback;
                            if (callback2 != null) {
                                ((NotificationShadeWindowControllerImpl) CentralSurfacesImpl.this.mNotificationShadeWindowController).setRequestTopUi("CentralSurfaces", true);
                            }
                            wirelessChargingView.mWM.addView(wirelessChargingView.mView, wirelessChargingView.mParams);
                            wirelessChargingView.mUiEventLogger.log(WirelessChargingRippleEvent.WIRELESS_RIPPLE_PLAYED);
                        } catch (WindowManager.BadTokenException e) {
                            Slog.d("WirelessChargingView", "Unable to add wireless charging view. " + e);
                        }
                    }
                }
            };
        }

        public final void handleHide() {
            boolean z = WirelessChargingAnimation.DEBUG;
            if (z) {
                Slog.d("WirelessChargingView", "HANDLE HIDE: " + this + " mView=" + this.mView);
            }
            WirelessChargingLayout wirelessChargingLayout = this.mView;
            if (wirelessChargingLayout != null) {
                if (wirelessChargingLayout.getParent() != null) {
                    if (z) {
                        Slog.d("WirelessChargingView", "REMOVE! " + this.mView + " in " + this);
                    }
                    Callback callback = this.mCallback;
                    if (callback != null) {
                        ((NotificationShadeWindowControllerImpl) CentralSurfacesImpl.this.mNotificationShadeWindowController).setRequestTopUi("CentralSurfaces", false);
                    }
                    this.mWM.removeViewImmediate(this.mView);
                }
                this.mView = null;
            }
        }

        public final void hide(long j) {
            AnonymousClass1 anonymousClass1 = this.mHandler;
            anonymousClass1.removeMessages(1);
            if (WirelessChargingAnimation.DEBUG) {
                Slog.d("WirelessChargingView", "HIDE: " + this);
            }
            anonymousClass1.sendMessageDelayed(Message.obtain(anonymousClass1, 1), j);
        }
    }

    private WirelessChargingAnimation(Context context, Looper looper, int i, int i2, Callback callback, boolean z, RippleShader.RippleShape rippleShape, UiEventLogger uiEventLogger) {
        this.mCurrentWirelessChargingView = new WirelessChargingView(context, looper, i, i2, callback, z, rippleShape, uiEventLogger);
    }

    public static WirelessChargingAnimation makeWirelessChargingAnimation(Context context, int i, CentralSurfacesImpl.AnonymousClass7 anonymousClass7, RippleShader.RippleShape rippleShape, UiEventLogger uiEventLogger) {
        return new WirelessChargingAnimation(context, null, -1, i, anonymousClass7, false, rippleShape, uiEventLogger);
    }
}
