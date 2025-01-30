package com.android.systemui.statusbar.phone;

import android.content.Context;
import android.os.Binder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import com.android.systemui.CoreStartable;
import com.android.systemui.R;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import java.io.PrintWriter;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class SafeUIStatusBarWindowController implements CoreStartable {
    public final Context mContext;
    public WindowManager.LayoutParams mLp;
    public FrameLayout mSafeUIWindowView;
    public WindowManager mWindowManager;

    public SafeUIStatusBarWindowController(Context context) {
        this.mContext = context;
    }

    @Override // com.android.systemui.CoreStartable
    public final void onBootCompleted() {
        Log.d("SafeUIStatusBarWindowController", "onBootCompleted()");
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        Log.d("SafeUIStatusBarWindowController", NetworkAnalyticsConstants.DataPoints.OPEN_TIME);
        Context context = this.mContext;
        this.mWindowManager = (WindowManager) context.getSystemService(WindowManager.class);
        this.mSafeUIWindowView = (FrameLayout) LayoutInflater.from(context).inflate(R.layout.safe_ui_status_bar_view, (ViewGroup) null);
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(-1, context.getResources().getDimensionPixelSize(17106177), 2000, -2138832824, -3);
        this.mLp = layoutParams;
        layoutParams.token = new Binder();
        WindowManager.LayoutParams layoutParams2 = this.mLp;
        layoutParams2.gravity = 48;
        layoutParams2.softInputMode = 16;
        layoutParams2.setTitle("SafeUIBar");
        this.mLp.packageName = context.getPackageName();
        WindowManager.LayoutParams layoutParams3 = this.mLp;
        layoutParams3.layoutInDisplayCutoutMode = 3;
        this.mWindowManager.addView(this.mSafeUIWindowView, layoutParams3);
    }

    @Override // com.android.systemui.CoreStartable, com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
    }
}
