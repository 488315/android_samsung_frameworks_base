package com.android.systemui.blur;

import android.content.Context;
import android.net.Uri;
import android.provider.Settings;
import android.util.Log;
import android.view.SemBlurInfo;
import android.view.View;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import com.android.systemui.Dependency;
import com.android.systemui.QpRune;
import com.android.systemui.R;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.util.SettingsHelper;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class SecCoverBlurController implements ConfigurationController.ConfigurationListener, SettingsHelper.OnChangedCallback {
    public final QSColorCurve mColorCurve;
    public final Context mContext;
    public boolean mIsBlurReduced;
    public final View mRootView;

    public SecCoverBlurController(Context context, View view) {
        this.mContext = context;
        this.mRootView = view;
        this.mColorCurve = new QSColorCurve(context);
        ((ConfigurationControllerImpl) ((ConfigurationController) Dependency.sDependency.getDependencyInner(ConfigurationController.class))).addCallback(this);
        ((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).registerCallback(this, Settings.System.getUriFor(SettingsHelper.INDEX_ACCESSIBILITY_REDUCE_TRANSPARENCY));
        updateIsBlurReduced();
    }

    public final void applyBlur() {
        View view = this.mRootView;
        if (view == null) {
            Log.w("SecCoverBlurController", "applyBlur: rootView is null");
            return;
        }
        if (this.mIsBlurReduced) {
            Log.d("SecCoverBlurController", "blockBlur");
            if (QpRune.QUICK_SUBSCREEN_FULLSCREEN_PANEL) {
                view.setBackgroundDrawable(this.mContext.getResources().getDrawable(R.drawable.subscreen_quickpanel_bg_no_corner));
            } else {
                view.setBackgroundDrawable(this.mContext.getResources().getDrawable(R.drawable.subscreen_quickpanel_bg));
            }
            view.semSetBlurInfo(null);
            return;
        }
        Log.d("SecCoverBlurController", "doWindowBlur");
        view.setBackgroundDrawable(null);
        int i = QpRune.QUICK_SUBSCREEN_FULLSCREEN_PANEL ? 0 : 40;
        QSColorCurve qSColorCurve = this.mColorCurve;
        qSColorCurve.setFraction(1.0f);
        float f = i;
        view.semSetBlurInfo(new SemBlurInfo.Builder(0).setRadius((int) qSColorCurve.radius).setColorCurve(qSColorCurve.saturation, qSColorCurve.curve, qSColorCurve.minX, qSColorCurve.maxX, qSColorCurve.minY, qSColorCurve.maxY).setBackgroundCornerRadius(f, f, f, f).build());
    }

    @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
    public final void onChanged(Uri uri) {
        if (uri != null && Settings.System.getUriFor(SettingsHelper.INDEX_ACCESSIBILITY_REDUCE_TRANSPARENCY).equals(uri)) {
            updateIsBlurReduced();
            applyBlur();
        }
    }

    @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
    public final void onUiModeChanged() {
        applyBlur();
    }

    public final void updateIsBlurReduced() {
        this.mIsBlurReduced = Settings.System.getInt(this.mContext.getContentResolver(), SettingsHelper.INDEX_ACCESSIBILITY_REDUCE_TRANSPARENCY, 0) != 0;
        ActionBarContextView$$ExternalSyntheticOutline0.m(new StringBuilder("updateIsBlurReduced: "), this.mIsBlurReduced, "SecCoverBlurController");
    }
}
