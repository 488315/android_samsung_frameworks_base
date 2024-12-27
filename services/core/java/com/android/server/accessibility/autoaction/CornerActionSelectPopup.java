package com.android.server.accessibility.autoaction;

import android.R;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupMenu;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.DirEncryptService$$ExternalSyntheticOutline0;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class CornerActionSelectPopup {
    public final View mAnchorView;
    public Context mContext;
    public final WindowManager.LayoutParams mParams;
    public final PopupMenu mPopup;
    public final AnonymousClass1 mReceiver;
    public final WindowManager mWindowManager;

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v1, types: [android.content.BroadcastReceiver, com.android.server.accessibility.autoaction.CornerActionSelectPopup$1] */
    public CornerActionSelectPopup(Context context, int i) {
        ?? r1 = new BroadcastReceiver() { // from class: com.android.server.accessibility.autoaction.CornerActionSelectPopup.1
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                PopupMenu popupMenu;
                if (!"android.intent.action.SCREEN_OFF".equals(intent.getAction()) || (popupMenu = CornerActionSelectPopup.this.mPopup) == null) {
                    return;
                }
                popupMenu.dismiss();
            }
        };
        this.mReceiver = r1;
        DirEncryptService$$ExternalSyntheticOutline0.m(i, "CornerActionSelectPopup corner : ", "usibi");
        this.mContext = context;
        this.mWindowManager = (WindowManager) context.getSystemService("window");
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        this.mParams = layoutParams;
        layoutParams.height = 0;
        layoutParams.width = 0;
        layoutParams.type = 2009;
        layoutParams.gravity = new int[]{51, 53, 83, 85}[i];
        layoutParams.flags = 288;
        layoutParams.samsungFlags |= 131072;
        layoutParams.layoutInDisplayCutoutMode = 3;
        View view = new View(this.mContext);
        this.mAnchorView = view;
        this.mWindowManager.addView(view, this.mParams);
        this.mPopup = new PopupMenu(new ContextThemeWrapper(this.mContext, (this.mContext.getResources().getConfiguration().uiMode & 48) == 32 ? R.style.Theme.DeviceDefault : R.style.Theme.DeviceDefault.Light), this.mAnchorView);
        this.mContext.registerReceiver(r1, BatteryService$$ExternalSyntheticOutline0.m("android.intent.action.SCREEN_OFF"));
    }
}
