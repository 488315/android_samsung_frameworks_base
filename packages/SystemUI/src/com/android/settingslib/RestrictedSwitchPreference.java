package com.android.settingslib;

import android.app.AppOpsManager;
import android.app.admin.DevicePolicyManager;
import android.app.admin.DevicePolicyResourcesManager;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;
import androidx.preference.PreferenceManager;
import androidx.preference.PreferenceViewHolder;
import androidx.preference.SwitchPreference;
import com.android.systemui.R;
import java.util.function.Supplier;

/* loaded from: classes.dex */
public class RestrictedSwitchPreference extends SwitchPreference {
    public final RestrictedPreferenceHelper mHelper;
    public final CharSequence mRestrictedSwitchSummary;
    public final boolean mUseAdditionalSummary;

    public RestrictedSwitchPreference(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mUseAdditionalSummary = false;
        RestrictedPreferenceHelper restrictedPreferenceHelper = new RestrictedPreferenceHelper(context, this, attributeSet);
        this.mHelper = restrictedPreferenceHelper;
        if (attributeSet != null) {
            TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.RestrictedSwitchPreference);
            TypedValue typedValuePeekValue = typedArrayObtainStyledAttributes.peekValue(1);
            if (typedValuePeekValue != null) {
                this.mUseAdditionalSummary = typedValuePeekValue.type == 18 && typedValuePeekValue.data != 0;
            }
            TypedValue typedValuePeekValue2 = typedArrayObtainStyledAttributes.peekValue(0);
            typedArrayObtainStyledAttributes.recycle();
            if (typedValuePeekValue2 != null && typedValuePeekValue2.type == 3) {
                int i3 = typedValuePeekValue2.resourceId;
                if (i3 != 0) {
                    this.mRestrictedSwitchSummary = context.getText(i3);
                } else {
                    this.mRestrictedSwitchSummary = typedValuePeekValue2.string;
                }
            }
        }
        if (this.mUseAdditionalSummary) {
            this.mLayoutResId = R.layout.restricted_switch_preference;
            restrictedPreferenceHelper.mDisabledSummary = false;
        }
    }

    @Override // androidx.preference.Preference
    public final void onAttachedToHierarchy(PreferenceManager preferenceManager) {
        this.mHelper.onAttachedToHierarchy();
        super.onAttachedToHierarchy(preferenceManager);
    }

    @Override // androidx.preference.SwitchPreference, androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        View viewFindViewById = preferenceViewHolder.findViewById(android.R.id.switch_widget);
        if (viewFindViewById != null) {
            viewFindViewById.getRootView().setFilterTouchesWhenObscured(true);
        }
        this.mHelper.onBindViewHolder(preferenceViewHolder);
        CharSequence string = this.mRestrictedSwitchSummary;
        if (string == null) {
            if (this.mHelper.isRestrictionEnforcedByAdvancedProtection()) {
                string = null;
            } else if (this.mChecked) {
                final Context context = this.mContext;
                DevicePolicyResourcesManager resources = ((DevicePolicyManager) context.getSystemService(DevicePolicyManager.class)).getResources();
                final int i = R.string.enabled_by_admin;
                string = resources.getString("Settings.ENABLED_BY_ADMIN_SWITCH_SUMMARY", new Supplier() { // from class: com.android.settingslib.RestrictedSwitchPreference$$ExternalSyntheticLambda0
                    @Override // java.util.function.Supplier
                    public final Object get() {
                        return context.getString(i);
                    }
                });
            } else {
                final Context context2 = this.mContext;
                DevicePolicyResourcesManager resources2 = ((DevicePolicyManager) context2.getSystemService(DevicePolicyManager.class)).getResources();
                final int i2 = R.string.disabled_by_admin;
                string = resources2.getString("Settings.DISABLED_BY_ADMIN_SWITCH_SUMMARY", new Supplier() { // from class: com.android.settingslib.RestrictedSwitchPreference$$ExternalSyntheticLambda0
                    @Override // java.util.function.Supplier
                    public final Object get() {
                        return context2.getString(i2);
                    }
                });
            }
        }
        View viewFindViewById2 = preferenceViewHolder.findViewById(R.id.icon_frame);
        if (this.mUseAdditionalSummary) {
            TextView textView = (TextView) preferenceViewHolder.findViewById(R.id.additional_summary);
            if (textView != null) {
                if (!this.mHelper.mDisabledByAdmin || string == null) {
                    textView.setVisibility(8);
                } else {
                    textView.setText(string);
                    textView.setVisibility(0);
                }
            }
        } else {
            TextView textView2 = (TextView) preferenceViewHolder.findViewById(android.R.id.summary);
            if (textView2 != null && this.mHelper.mDisabledByAdmin && string != null) {
                textView2.setText(string);
                textView2.setVisibility(0);
            }
        }
        if (viewFindViewById2 != null) {
            viewFindViewById2.setVisibility(this.mIconSpaceReserved ? 0 : 8);
        }
    }

    @Override // androidx.preference.Preference
    public final void performClick() {
        if (this.mHelper.performClick()) {
            return;
        }
        super.performClick();
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x001c  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x000f  */
    @Override // androidx.preference.Preference
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void setEnabled(boolean z) {
        boolean z2;
        boolean z3 = true;
        if (z) {
            RestrictedPreferenceHelper restrictedPreferenceHelper = this.mHelper;
            if (restrictedPreferenceHelper.mDisabledByAdmin) {
                restrictedPreferenceHelper.setDisabledByAdmin(null);
                z2 = true;
            } else {
                z2 = false;
            }
        }
        if (z) {
            RestrictedPreferenceHelper restrictedPreferenceHelper2 = this.mHelper;
            if (restrictedPreferenceHelper2.mDisabledByEcm) {
                restrictedPreferenceHelper2.setDisabledByEcm();
            } else {
                z3 = z2;
            }
        }
        if (z3) {
            return;
        }
        super.setEnabled(z);
    }

    public RestrictedSwitchPreference(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public RestrictedSwitchPreference(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.switchPreferenceCompatStyle);
    }

    public RestrictedSwitchPreference(Context context) {
        this(context, null);
    }

    public void setAppOps(AppOpsManager appOpsManager) {
    }
}
