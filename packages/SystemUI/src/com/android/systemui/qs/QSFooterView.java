package com.android.systemui.qs;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
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
import com.android.systemui.qs.TouchAnimator;
import com.samsung.android.knox.EnterpriseDeviceManager;
import com.samsung.android.knox.custom.SettingsManager;

public class QSFooterView extends FrameLayout {
    public static final /* synthetic */ int $r8$clinit = 0;
    public TextView mBuildText;
    public final AnonymousClass1 mDeveloperSettingsObserver;
    public View mEditButton;
    public PageIndicator mPageIndicator;

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

    public final void setBuildText() {
        EnterpriseDeviceManager enterpriseDeviceManager;
        if (this.mBuildText == null) {
            return;
        }
        Context context = ((FrameLayout) this).mContext;
        byte b = Settings.Global.getInt(context.getContentResolver(), "development_settings_enabled", Build.TYPE.equals("eng") ? 1 : 0) != 0;
        if (b != false) {
            SettingsManager settingsManager = SettingsManager.getInstance();
            if (((settingsManager != null ? settingsManager.getSettingsHiddenState() : 0) & 256) == 0 && ((enterpriseDeviceManager = EnterpriseDeviceManager.getInstance(context.getApplicationContext())) == null || enterpriseDeviceManager.getRestrictionPolicy().isDeveloperModeAllowed())) {
                UserManager userManager = (UserManager) context.getSystemService("user");
                boolean hasUserRestriction = userManager.hasUserRestriction("no_debugging_features");
                if (userManager.isAdminUser() && !hasUserRestriction && b != false) {
                    this.mBuildText.setText(((FrameLayout) this).mContext.getString(android.R.string.config_globalAppSearchDataQuerierPackage, Build.VERSION.RELEASE_OR_CODENAME, Build.ID));
                    this.mBuildText.setSelected(true);
                    return;
                }
            }
        }
        this.mBuildText.setText((CharSequence) null);
        this.mBuildText.setSelected(false);
    }

    public final void updateResources() {
        TouchAnimator.Builder builder = new TouchAnimator.Builder();
        builder.addFloat(this.mPageIndicator, "alpha", 0.0f, 1.0f);
        builder.addFloat(this.mBuildText, "alpha", 0.0f, 1.0f);
        builder.addFloat(this.mEditButton, "alpha", 0.0f, 1.0f);
        builder.mStartDelay = 0.9f;
        builder.build();
        int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.qs_footer_action_button_size);
        int dimensionPixelSize2 = getResources().getDimensionPixelSize(R.dimen.qs_footer_icon_padding);
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) this.mEditButton.getLayoutParams();
        marginLayoutParams.height = dimensionPixelSize;
        marginLayoutParams.width = dimensionPixelSize;
        this.mEditButton.setLayoutParams(marginLayoutParams);
        this.mEditButton.setPadding(dimensionPixelSize2, dimensionPixelSize2, dimensionPixelSize2, dimensionPixelSize2);
        TextView textView = this.mBuildText;
        TypedArray obtainStyledAttributes = textView.getContext().obtainStyledAttributes(R.style.TextAppearance_QS_Status_Build, new int[]{android.R.attr.textSize});
        textView.setTextSize(0, obtainStyledAttributes.getDimensionPixelSize(0, (int) textView.getTextSize()));
        obtainStyledAttributes.recycle();
        ViewGroup.MarginLayoutParams marginLayoutParams2 = (ViewGroup.MarginLayoutParams) getLayoutParams();
        marginLayoutParams2.height = getResources().getDimensionPixelSize(R.dimen.qs_footer_height);
        int dimensionPixelSize3 = getResources().getDimensionPixelSize(R.dimen.qs_footer_margin);
        marginLayoutParams2.leftMargin = dimensionPixelSize3;
        marginLayoutParams2.rightMargin = dimensionPixelSize3;
        marginLayoutParams2.bottomMargin = getResources().getDimensionPixelSize(R.dimen.qs_footers_margin_bottom);
        setLayoutParams(marginLayoutParams2);
    }
}
