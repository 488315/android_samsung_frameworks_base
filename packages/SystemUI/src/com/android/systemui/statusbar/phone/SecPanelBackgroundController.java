package com.android.systemui.statusbar.phone;

import android.graphics.Color;
import androidx.picker.adapter.layoutmanager.AutoFitGridLayoutManager$$ExternalSyntheticOutline0;
import androidx.picker3.widget.SeslColorSpectrumView$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.systemui.blur.SecQpBlurController;
import com.android.systemui.logging.PanelScreenShotLogger;
import com.android.systemui.util.ViewController;
import java.util.ArrayList;

public final class SecPanelBackgroundController extends ViewController implements PanelScreenShotLogger.LogProvider {
    public SecQpBlurController.AnonymousClass2 mBlurUtils;
    public float mMaxAlpha;
    public int mStatusBarState;
    public final SecPanelBackground mView;

    public SecPanelBackgroundController(SecPanelBackground secPanelBackground) {
        super(secPanelBackground);
        this.mMaxAlpha = 0.3f;
        this.mView = secPanelBackground;
        updatePanel();
        updateBackgroundColor();
        secPanelBackground.animate().translationY(0.0f).setDuration(200L).start();
        PanelScreenShotLogger.INSTANCE.addLogProvider("SecPanelBackground", this);
    }

    @Override // com.android.systemui.logging.PanelScreenShotLogger.LogProvider
    public final ArrayList gatherState() {
        ArrayList arrayList = new ArrayList();
        SecPanelBackground secPanelBackground = this.mView;
        int color = secPanelBackground.mContext.getColor(R.color.open_theme_qp_bg_color);
        arrayList.add("SecPanelBackgroundController ============================================= ");
        arrayList.add("  mMaxAlpha = " + this.mMaxAlpha + "  currentAlpha = " + secPanelBackground.getAlpha() + "  visibility = " + secPanelBackground.getVisibility());
        StringBuilder sb = new StringBuilder("  DIM color = 0x");
        sb.append(Integer.toHexString(color));
        sb.append(", BOX color = 0x");
        sb.append(Integer.toHexString(secPanelBackground.mContext.getColor(R.color.qs_tile_container_bg)));
        arrayList.add(sb.toString());
        arrayList.add("============================================================== ");
        return arrayList;
    }

    @Override // com.android.systemui.util.ViewController
    public final void onViewAttached() {
        updateBackgroundVisibility();
    }

    public final void updateBackgroundColor() {
        SecPanelBackground secPanelBackground = this.mView;
        int color = secPanelBackground.mContext.getColor(R.color.open_theme_qp_bg_color);
        int i = (color >> 16) & 255;
        int i2 = (color >> 8) & 255;
        int i3 = color & 255;
        secPanelBackground.setBackgroundColor(Color.rgb(i, i2, i3));
        StringBuilder sb = new StringBuilder("DIM color = ");
        AutoFitGridLayoutManager$$ExternalSyntheticOutline0.m(sb, i, " ", i2, " ");
        RecyclerView$$ExternalSyntheticOutline0.m(i3, "SecPanelBackground", sb);
    }

    /* JADX WARN: Code restructure failed: missing block: B:17:0x003a, code lost:
    
        if (com.android.systemui.blur.SecQpBlurController.this.mSettingsHelper.isUltraPowerSavingMode() != false) goto L32;
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x0044, code lost:
    
        if (com.android.systemui.blur.SecQpBlurController.m930$$Nest$mhasCustomColorForBackground(com.android.systemui.blur.SecQpBlurController.this) != false) goto L32;
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x0061, code lost:
    
        if (com.android.systemui.blur.SecQpBlurController.m930$$Nest$mhasCustomColorForBackground(com.android.systemui.blur.SecQpBlurController.this) != false) goto L32;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void updateBackgroundVisibility() {
        /*
            r5 = this;
            com.android.systemui.blur.SecQpBlurController$2 r0 = r5.mBlurUtils
            java.lang.String r1 = "SecPanelBackground"
            if (r0 != 0) goto Ld
            java.lang.String r5 = "updateBackgroundVisibility: mBlurUtils is null"
            android.util.Log.w(r1, r5)
            return
        Ld:
            boolean r2 = com.android.systemui.QpRune.QUICK_PANEL_BLUR_DEFAULT
            r3 = 0
            r4 = 1
            com.android.systemui.blur.SecQpBlurController r0 = com.android.systemui.blur.SecQpBlurController.this
            if (r2 == 0) goto L47
            boolean r2 = r0.mIsBlurReduced
            if (r2 != 0) goto L65
            com.android.systemui.plugins.statusbar.StatusBarStateController r2 = r0.mStatusBarStateController
            int r2 = r2.getState()
            if (r2 != 0) goto L23
            r2 = r4
            goto L24
        L23:
            r2 = r3
        L24:
            if (r2 != 0) goto L3c
            com.android.systemui.statusbar.policy.KeyguardStateController r0 = r0.mKeyguardStateController
            com.android.systemui.statusbar.policy.KeyguardStateControllerImpl r0 = (com.android.systemui.statusbar.policy.KeyguardStateControllerImpl) r0
            boolean r0 = r0.mOccluded
            if (r0 != 0) goto L3c
            com.android.systemui.blur.SecQpBlurController$2 r0 = r5.mBlurUtils
            com.android.systemui.blur.SecQpBlurController r0 = com.android.systemui.blur.SecQpBlurController.this
            com.android.systemui.util.SettingsHelper r0 = com.android.systemui.blur.SecQpBlurController.m929$$Nest$fgetmSettingsHelper(r0)
            boolean r0 = r0.isUltraPowerSavingMode()
            if (r0 != 0) goto L65
        L3c:
            com.android.systemui.blur.SecQpBlurController$2 r0 = r5.mBlurUtils
            com.android.systemui.blur.SecQpBlurController r0 = com.android.systemui.blur.SecQpBlurController.this
            boolean r0 = com.android.systemui.blur.SecQpBlurController.m930$$Nest$mhasCustomColorForBackground(r0)
            if (r0 == 0) goto L64
            goto L65
        L47:
            boolean r2 = com.android.systemui.QpRune.QUICK_PANEL_BLUR_MASSIVE
            if (r2 == 0) goto L64
            boolean r2 = r0.mIsBlurReduced
            if (r2 != 0) goto L65
            com.android.systemui.util.SettingsHelper r0 = com.android.systemui.blur.SecQpBlurController.m929$$Nest$fgetmSettingsHelper(r0)
            boolean r0 = r0.isUltraPowerSavingMode()
            if (r0 != 0) goto L65
            com.android.systemui.blur.SecQpBlurController$2 r0 = r5.mBlurUtils
            com.android.systemui.blur.SecQpBlurController r0 = com.android.systemui.blur.SecQpBlurController.this
            boolean r0 = com.android.systemui.blur.SecQpBlurController.m930$$Nest$mhasCustomColorForBackground(r0)
            if (r0 == 0) goto L64
            goto L65
        L64:
            r4 = r3
        L65:
            if (r4 == 0) goto L68
            goto L6a
        L68:
            r3 = 8
        L6a:
            com.android.systemui.statusbar.phone.SecPanelBackground r5 = r5.mView
            r5.setVisibility(r3)
            if (r4 == 0) goto L74
            java.lang.String r5 = "VISIBLE"
            goto L76
        L74:
            java.lang.String r5 = "GONE"
        L76:
            java.lang.String r0 = "DIM visibility = "
            java.lang.String r5 = r0.concat(r5)
            android.util.Log.d(r1, r5)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.phone.SecPanelBackgroundController.updateBackgroundVisibility():void");
    }

    public final void updatePanel() {
        updateBackgroundVisibility();
        SecQpBlurController.AnonymousClass2 anonymousClass2 = this.mBlurUtils;
        if (anonymousClass2 != null) {
            SecQpBlurController secQpBlurController = SecQpBlurController.this;
            if (secQpBlurController.mIsBlurReduced) {
                this.mMaxAlpha = 1.0f;
            } else if (SecQpBlurController.m930$$Nest$mhasCustomColorForBackground(secQpBlurController)) {
                this.mMaxAlpha = ((this.mView.mContext.getColor(R.color.open_theme_qp_bg_color) >> 24) & 255) / 255.0f;
            } else {
                this.mMaxAlpha = 0.3f;
            }
            SeslColorSpectrumView$$ExternalSyntheticOutline0.m(new StringBuilder("DIM mMaxAlpha = "), this.mMaxAlpha, "SecPanelBackground");
        }
        updateBackgroundColor();
    }

    @Override // com.android.systemui.util.ViewController
    public final void onViewDetached() {
    }
}
