package com.android.settingslib.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;
import com.android.settingslib.widget.preference.statusbanner.R$styleable;
import com.android.systemui.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
public final class StatusBannerPreference extends Preference {
    public final BannerStatus buttonLevel;
    public final String buttonText;
    public final BannerStatus iconLevel;

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    public final class BannerStatus {
        public static final /* synthetic */ BannerStatus[] $VALUES;
        public static final BannerStatus GENERIC;
        public static final BannerStatus HIGH;
        public static final BannerStatus LOADING_DETERMINATE;
        public static final BannerStatus LOADING_INDETERMINATE;
        public static final BannerStatus LOW;
        public static final BannerStatus MEDIUM;
        public static final BannerStatus OFF;

        static {
            BannerStatus bannerStatus = new BannerStatus("GENERIC", 0);
            GENERIC = bannerStatus;
            BannerStatus bannerStatus2 = new BannerStatus("LOW", 1);
            LOW = bannerStatus2;
            BannerStatus bannerStatus3 = new BannerStatus("MEDIUM", 2);
            MEDIUM = bannerStatus3;
            BannerStatus bannerStatus4 = new BannerStatus("HIGH", 3);
            HIGH = bannerStatus4;
            BannerStatus bannerStatus5 = new BannerStatus("OFF", 4);
            OFF = bannerStatus5;
            BannerStatus bannerStatus6 = new BannerStatus("LOADING_DETERMINATE", 5);
            LOADING_DETERMINATE = bannerStatus6;
            BannerStatus bannerStatus7 = new BannerStatus("LOADING_INDETERMINATE", 6);
            LOADING_INDETERMINATE = bannerStatus7;
            BannerStatus[] bannerStatusArr = {bannerStatus, bannerStatus2, bannerStatus3, bannerStatus4, bannerStatus5, bannerStatus6, bannerStatus7};
            $VALUES = bannerStatusArr;
            EnumEntriesKt.enumEntries(bannerStatusArr);
        }

        private BannerStatus(String str, int i) {
        }

        public static BannerStatus valueOf(String str) {
            return (BannerStatus) Enum.valueOf(BannerStatus.class, str);
        }

        public static BannerStatus[] values() {
            return (BannerStatus[]) $VALUES.clone();
        }
    }

    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[BannerStatus.values().length];
            try {
                iArr[BannerStatus.LOW.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[BannerStatus.MEDIUM.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[BannerStatus.HIGH.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[BannerStatus.OFF.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public StatusBannerPreference(Context context) {
        this(context, null, 0, 0, 14, null);
    }

    public final int getBackgroundColor(BannerStatus bannerStatus) {
        int i = WhenMappings.$EnumSwitchMapping$0[bannerStatus.ordinal()];
        return i != 1 ? i != 2 ? i != 3 ? i != 4 ? this.mContext.getColor(R.color.settingslib_materialColorPrimary) : this.mContext.getColor(R.color.settingslib_expressive_color_status_level_off) : this.mContext.getColor(R.color.settingslib_expressive_color_status_level_high) : this.mContext.getColor(R.color.settingslib_expressive_color_status_level_medium) : this.mContext.getColor(R.color.settingslib_expressive_color_status_level_low);
    }

    @Override // androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        BannerStatus bannerStatus;
        super.onBindViewHolder(preferenceViewHolder);
        preferenceViewHolder.mDividerAllowedBelow = false;
        preferenceViewHolder.mDividerAllowedAbove = false;
        View viewFindViewById = preferenceViewHolder.findViewById(R.id.icon_background);
        ImageView imageView = viewFindViewById instanceof ImageView ? (ImageView) viewFindViewById : null;
        if (imageView != null) {
            int i = WhenMappings.$EnumSwitchMapping$0[this.iconLevel.ordinal()];
            imageView.setImageDrawable(i != 1 ? i != 2 ? i != 3 ? this.mContext.getDrawable(R.drawable.settingslib_expressive_background_generic) : this.mContext.getDrawable(R.drawable.settingslib_expressive_background_level_high) : this.mContext.getDrawable(R.drawable.settingslib_expressive_background_level_medium) : this.mContext.getDrawable(R.drawable.settingslib_expressive_background_level_low));
        }
        View viewFindViewById2 = preferenceViewHolder.findViewById(android.R.id.icon_frame);
        if (viewFindViewById2 != null) {
            viewFindViewById2.setVisibility((getIcon() != null || (bannerStatus = this.iconLevel) == BannerStatus.LOADING_DETERMINATE || bannerStatus == BannerStatus.LOADING_INDETERMINATE) ? 0 : 8);
        }
        View viewFindViewById3 = preferenceViewHolder.findViewById(android.R.id.icon);
        if (viewFindViewById3 != null) {
            BannerStatus bannerStatus2 = this.iconLevel;
            viewFindViewById3.setVisibility((bannerStatus2 == BannerStatus.LOADING_DETERMINATE || bannerStatus2 == BannerStatus.LOADING_INDETERMINATE) ? 8 : 0);
        }
        View viewFindViewById4 = preferenceViewHolder.findViewById(R.id.progress_indicator);
        CircularProgressIndicator circularProgressIndicator = viewFindViewById4 instanceof CircularProgressIndicator ? (CircularProgressIndicator) viewFindViewById4 : null;
        if (circularProgressIndicator != null) {
            circularProgressIndicator.setVisibility(this.iconLevel != BannerStatus.LOADING_DETERMINATE ? 8 : 0);
        }
        View viewFindViewById5 = preferenceViewHolder.findViewById(R.id.status_banner_button);
        MaterialButton materialButton = viewFindViewById5 instanceof MaterialButton ? (MaterialButton) viewFindViewById5 : null;
        if (materialButton != null) {
            BannerStatus bannerStatus3 = this.buttonLevel;
            materialButton.setBackgroundColor(bannerStatus3 == BannerStatus.OFF ? getBackgroundColor(BannerStatus.GENERIC) : getBackgroundColor(bannerStatus3));
            materialButton.setText(this.buttonText);
            materialButton.setOnClickListener(null);
            materialButton.setVisibility(8);
        }
    }

    public StatusBannerPreference(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0, 0, 12, null);
    }

    public StatusBannerPreference(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0, 8, null);
    }

    public /* synthetic */ StatusBannerPreference(Context context, AttributeSet attributeSet, int i, int i2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i3 & 2) != 0 ? null : attributeSet, (i3 & 4) != 0 ? 0 : i, (i3 & 8) != 0 ? 0 : i2);
    }

    public StatusBannerPreference(Context context, AttributeSet attributeSet, int i, int i2) {
        BannerStatus bannerStatus;
        Drawable drawable;
        super(context, attributeSet, i, i2);
        BannerStatus bannerStatus2 = BannerStatus.GENERIC;
        this.iconLevel = bannerStatus2;
        this.buttonLevel = bannerStatus2;
        this.buttonText = "";
        this.mLayoutResId = R.layout.settingslib_expressive_preference_statusbanner;
        setSelectable(false);
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.StatusBanner, i, 0);
        switch (typedArrayObtainStyledAttributes.getInteger(2, 0)) {
            case 1:
                bannerStatus = BannerStatus.LOW;
                break;
            case 2:
                bannerStatus = BannerStatus.MEDIUM;
                break;
            case 3:
                bannerStatus = BannerStatus.HIGH;
                break;
            case 4:
                bannerStatus = BannerStatus.OFF;
                break;
            case 5:
                bannerStatus = BannerStatus.LOADING_DETERMINATE;
                break;
            case 6:
                bannerStatus = BannerStatus.LOADING_INDETERMINATE;
                break;
            default:
                bannerStatus = bannerStatus2;
                break;
        }
        this.iconLevel = bannerStatus;
        Drawable icon = getIcon();
        if (icon != null) {
            icon.setTintList(ColorStateList.valueOf(getBackgroundColor(bannerStatus)));
        }
        notifyChanged();
        if (getIcon() == null) {
            int i3 = WhenMappings.$EnumSwitchMapping$0[this.iconLevel.ordinal()];
            if (i3 == 1) {
                drawable = this.mContext.getDrawable(R.drawable.settingslib_expressive_icon_status_level_low);
            } else if (i3 == 2) {
                drawable = this.mContext.getDrawable(R.drawable.settingslib_expressive_icon_status_level_medium);
            } else if (i3 != 3) {
                drawable = i3 != 4 ? null : this.mContext.getDrawable(R.drawable.settingslib_expressive_icon_status_level_off);
            } else {
                drawable = this.mContext.getDrawable(R.drawable.settingslib_expressive_icon_status_level_high);
            }
            setIcon(drawable);
        } else {
            BannerStatus bannerStatus3 = this.iconLevel;
            Drawable icon2 = getIcon();
            if (icon2 != null) {
                icon2.setTintList(ColorStateList.valueOf(getBackgroundColor(bannerStatus3)));
            }
        }
        switch (typedArrayObtainStyledAttributes.getInteger(0, 0)) {
            case 1:
                bannerStatus2 = BannerStatus.LOW;
                break;
            case 2:
                bannerStatus2 = BannerStatus.MEDIUM;
                break;
            case 3:
                bannerStatus2 = BannerStatus.HIGH;
                break;
            case 4:
                bannerStatus2 = BannerStatus.OFF;
                break;
            case 5:
                bannerStatus2 = BannerStatus.LOADING_DETERMINATE;
                break;
            case 6:
                bannerStatus2 = BannerStatus.LOADING_INDETERMINATE;
                break;
        }
        this.buttonLevel = bannerStatus2;
        notifyChanged();
        String string = typedArrayObtainStyledAttributes.getString(1);
        this.buttonText = string != null ? string : "";
        notifyChanged();
        typedArrayObtainStyledAttributes.recycle();
    }
}
