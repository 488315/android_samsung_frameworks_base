package com.android.settingslib.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import androidx.preference.Preference;
import androidx.preference.PreferenceGroup;
import androidx.preference.PreferenceViewHolder;
import com.android.settingslib.widget.preference.banner.R$styleable;
import com.android.systemui.R;
import java.util.ArrayList;
import java.util.List;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
public final class BannerMessagePreferenceGroup extends PreferenceGroup {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final List childPreferences;
    public final Drawable collapseIcon;
    public final String collapseKey;
    public SectionButtonPreference collapsePreference;
    public final String collapseTitle;
    public final String expandKey;
    public NumberButtonPreference expandPreference;
    public final String expandTitle;
    public boolean isExpanded;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public BannerMessagePreferenceGroup(Context context) {
        this(context, null, 0, 0, 14, null);
    }

    @Override // androidx.preference.PreferenceGroup
    public final boolean addPreference(Preference preference) {
        if (!(preference instanceof BannerMessagePreference) || ((ArrayList) this.childPreferences).size() >= 3) {
            return false;
        }
        ((ArrayList) this.childPreferences).add(preference);
        NumberButtonPreference numberButtonPreference = this.expandPreference;
        if (numberButtonPreference != null) {
            numberButtonPreference.count = ((ArrayList) this.childPreferences).size() - 1;
            numberButtonPreference.notifyChanged();
        }
        updateExpandCollapsePreference();
        updateChildrenVisibility$1();
        super.addPreference(preference);
        return true;
    }

    /* JADX WARN: Type inference failed for: r8v18, types: [com.android.settingslib.widget.BannerMessagePreferenceGroup$onBindViewHolder$1$1] */
    @Override // androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        if (((ArrayList) this.childPreferences).size() >= 2) {
            if (this.expandPreference == null) {
                NumberButtonPreference numberButtonPreference = new NumberButtonPreference(this.mContext, null, 0, 0, 14, null);
                numberButtonPreference.setKey(this.expandKey);
                numberButtonPreference.setTitle(this.expandTitle);
                numberButtonPreference.count = ((ArrayList) this.childPreferences).size() - 1;
                numberButtonPreference.notifyChanged();
                numberButtonPreference.notifyChanged();
                numberButtonPreference.setOrder(99);
                numberButtonPreference.clickListener = new View.OnClickListener() { // from class: com.android.settingslib.widget.BannerMessagePreferenceGroup$onBindViewHolder$1$1
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        BannerMessagePreferenceGroup bannerMessagePreferenceGroup = this.this$0;
                        int i = BannerMessagePreferenceGroup.$r8$clinit;
                        bannerMessagePreferenceGroup.isExpanded = !bannerMessagePreferenceGroup.isExpanded;
                        bannerMessagePreferenceGroup.updateExpandCollapsePreference();
                        bannerMessagePreferenceGroup.updateChildrenVisibility$1();
                    }
                };
                this.expandPreference = numberButtonPreference;
                super.addPreference(numberButtonPreference);
            }
            if (this.collapsePreference == null) {
                SectionButtonPreference sectionButtonPreference = new SectionButtonPreference(this.mContext, null, 0, 0, 14, null);
                sectionButtonPreference.setKey(this.collapseKey);
                sectionButtonPreference.setTitle(this.collapseTitle);
                sectionButtonPreference.setIcon(this.collapseIcon);
                sectionButtonPreference.setOrder(100);
                sectionButtonPreference.clickListener = new BannerMessagePreferenceGroup$$ExternalSyntheticLambda0(this);
                sectionButtonPreference.notifyChanged();
                this.collapsePreference = sectionButtonPreference;
                super.addPreference(sectionButtonPreference);
            }
        }
        updateExpandCollapsePreference();
        updateChildrenVisibility$1();
    }

    public final void updateChildrenVisibility$1() {
        int size = ((ArrayList) this.childPreferences).size();
        for (int i = 0; i < size; i++) {
            BannerMessagePreference bannerMessagePreference = (BannerMessagePreference) ((ArrayList) this.childPreferences).get(i);
            if (i == 0) {
                bannerMessagePreference.setVisible(true);
            } else {
                bannerMessagePreference.setVisible(this.isExpanded);
            }
        }
    }

    public final void updateExpandCollapsePreference() {
        NumberButtonPreference numberButtonPreference = this.expandPreference;
        boolean z = false;
        if (numberButtonPreference != null) {
            numberButtonPreference.setVisible(!this.isExpanded && ((ArrayList) this.childPreferences).size() > 1);
        }
        SectionButtonPreference sectionButtonPreference = this.collapsePreference;
        if (sectionButtonPreference != null) {
            if (this.isExpanded && ((ArrayList) this.childPreferences).size() > 1) {
                z = true;
            }
            sectionButtonPreference.setVisible(z);
        }
    }

    public BannerMessagePreferenceGroup(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0, 0, 12, null);
    }

    public BannerMessagePreferenceGroup(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0, 8, null);
    }

    public /* synthetic */ BannerMessagePreferenceGroup(Context context, AttributeSet attributeSet, int i, int i2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i3 & 2) != 0 ? null : attributeSet, (i3 & 4) != 0 ? 0 : i, (i3 & 8) != 0 ? 0 : i2);
    }

    public BannerMessagePreferenceGroup(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.childPreferences = new ArrayList();
        this.mPersistent = false;
        this.mLayoutResId = R.layout.settingslib_banner_message_preference_group;
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.BannerMessagePreferenceGroup, i, 0);
        this.expandKey = typedArrayObtainStyledAttributes.getString(3);
        this.expandTitle = typedArrayObtainStyledAttributes.getString(4);
        this.collapseKey = typedArrayObtainStyledAttributes.getString(1);
        this.collapseTitle = typedArrayObtainStyledAttributes.getString(2);
        this.collapseIcon = typedArrayObtainStyledAttributes.getDrawable(0);
        typedArrayObtainStyledAttributes.recycle();
    }
}
