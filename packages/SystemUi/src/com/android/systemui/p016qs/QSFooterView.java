package com.android.systemui.p016qs;

import android.content.Context;
import android.content.res.Configuration;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.UserManager;
import android.provider.Settings;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.android.systemui.R;
import com.android.systemui.p016qs.TouchAnimator;
import com.samsung.android.knox.EnterpriseDeviceManager;
import com.samsung.android.knox.custom.SettingsManager;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class QSFooterView extends FrameLayout {
    public static final /* synthetic */ int $r8$clinit = 0;
    public TextView mBuildText;
    public final C20461 mDeveloperSettingsObserver;
    public View mEditButton;
    public PageIndicator mPageIndicator;

    /* JADX WARN: Type inference failed for: r2v1, types: [com.android.systemui.qs.QSFooterView$1] */
    public QSFooterView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mDeveloperSettingsObserver = new ContentObserver(new Handler(((FrameLayout) this).mContext.getMainLooper())) { // from class: com.android.systemui.qs.QSFooterView.1
            @Override // android.database.ContentObserver
            public final void onChange(boolean z, Uri uri) {
                super.onChange(z, uri);
                QSFooterView qSFooterView = QSFooterView.this;
                int i = QSFooterView.$r8$clinit;
                qSFooterView.setBuildText();
            }
        };
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        ((FrameLayout) this).mContext.getContentResolver().registerContentObserver(Settings.Global.getUriFor("development_settings_enabled"), false, this.mDeveloperSettingsObserver, -1);
    }

    @Override // android.view.View
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        updateResources();
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        ((FrameLayout) this).mContext.getContentResolver().unregisterContentObserver(this.mDeveloperSettingsObserver);
        super.onDetachedFromWindow();
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        this.mPageIndicator = (PageIndicator) findViewById(R.id.footer_page_indicator);
        this.mBuildText = (TextView) findViewById(R.id.build);
        this.mEditButton = findViewById(android.R.id.edit);
        updateResources();
        setImportantForAccessibility(1);
        setBuildText();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0064  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0080  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void setBuildText() {
        Object[] objArr;
        EnterpriseDeviceManager enterpriseDeviceManager;
        if (this.mBuildText == null) {
            return;
        }
        Context context = ((FrameLayout) this).mContext;
        SettingsManager settingsManager = SettingsManager.getInstance();
        if (((settingsManager != null ? settingsManager.getSettingsHiddenState() : 0) & 256) == 0 && ((enterpriseDeviceManager = EnterpriseDeviceManager.getInstance(context.getApplicationContext())) == null || enterpriseDeviceManager.getRestrictionPolicy().isDeveloperModeAllowed(false))) {
            UserManager userManager = (UserManager) context.getSystemService("user");
            Object[] objArr2 = Settings.Global.getInt(context.getContentResolver(), "development_settings_enabled", Build.TYPE.equals("eng") ? 1 : 0) != 0;
            boolean hasUserRestriction = userManager.hasUserRestriction("no_debugging_features");
            if (userManager.isAdminUser() && !hasUserRestriction && objArr2 != false) {
                objArr = true;
                if (objArr == true) {
                    this.mBuildText.setText((CharSequence) null);
                    this.mBuildText.setSelected(false);
                    return;
                } else {
                    this.mBuildText.setText(((FrameLayout) this).mContext.getString(android.R.string.config_emergency_call_number, Build.VERSION.RELEASE_OR_CODENAME, Build.ID));
                    this.mBuildText.setSelected(true);
                    return;
                }
            }
        }
        objArr = false;
        if (objArr == true) {
        }
    }

    public final void updateResources() {
        TouchAnimator.Builder builder = new TouchAnimator.Builder();
        builder.addFloat(this.mPageIndicator, "alpha", 0.0f, 1.0f);
        builder.addFloat(this.mBuildText, "alpha", 0.0f, 1.0f);
        builder.addFloat(this.mEditButton, "alpha", 0.0f, 1.0f);
        builder.mStartDelay = 0.9f;
        builder.build();
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) getLayoutParams();
        marginLayoutParams.bottomMargin = getResources().getDimensionPixelSize(R.dimen.qs_footers_margin_bottom);
        setLayoutParams(marginLayoutParams);
    }
}
