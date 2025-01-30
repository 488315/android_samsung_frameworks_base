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
import androidx.core.content.res.TypedArrayUtils;
import androidx.preference.PreferenceManager;
import androidx.preference.PreferenceViewHolder;
import androidx.preference.SwitchPreference;
import com.android.systemui.R;
import java.util.function.Supplier;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.RestrictedSwitchPreference);
            TypedValue peekValue = obtainStyledAttributes.peekValue(1);
            if (peekValue != null) {
                this.mUseAdditionalSummary = peekValue.type == 18 && peekValue.data != 0;
            }
            TypedValue peekValue2 = obtainStyledAttributes.peekValue(0);
            obtainStyledAttributes.recycle();
            if (peekValue2 != null && peekValue2.type == 3) {
                int i3 = peekValue2.resourceId;
                if (i3 != 0) {
                    this.mRestrictedSwitchSummary = context.getText(i3);
                } else {
                    this.mRestrictedSwitchSummary = peekValue2.string;
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
        View findViewById = preferenceViewHolder.findViewById(android.R.id.switch_widget);
        if (findViewById != null) {
            findViewById.getRootView().setFilterTouchesWhenObscured(true);
        }
        this.mHelper.onBindViewHolder(preferenceViewHolder);
        CharSequence charSequence = this.mRestrictedSwitchSummary;
        if (charSequence == null) {
            if (this.mChecked) {
                final Context context = this.mContext;
                DevicePolicyResourcesManager resources = ((DevicePolicyManager) context.getSystemService(DevicePolicyManager.class)).getResources();
                final int i = R.string.enabled_by_admin;
                charSequence = resources.getString("Settings.ENABLED_BY_ADMIN_SWITCH_SUMMARY", new Supplier() { // from class: com.android.settingslib.RestrictedSwitchPreference$$ExternalSyntheticLambda0
                    @Override // java.util.function.Supplier
                    public final Object get() {
                        return context.getString(i);
                    }
                });
            } else {
                final Context context2 = this.mContext;
                DevicePolicyResourcesManager resources2 = ((DevicePolicyManager) context2.getSystemService(DevicePolicyManager.class)).getResources();
                final int i2 = R.string.disabled_by_admin;
                charSequence = resources2.getString("Settings.DISABLED_BY_ADMIN_SWITCH_SUMMARY", new Supplier() { // from class: com.android.settingslib.RestrictedSwitchPreference$$ExternalSyntheticLambda0
                    @Override // java.util.function.Supplier
                    public final Object get() {
                        return context2.getString(i2);
                    }
                });
            }
        }
        View findViewById2 = preferenceViewHolder.findViewById(R.id.icon_frame);
        if (this.mUseAdditionalSummary) {
            TextView textView = (TextView) preferenceViewHolder.findViewById(R.id.additional_summary);
            if (textView != null) {
                if (this.mHelper.mDisabledByAdmin) {
                    textView.setText(charSequence);
                    textView.setVisibility(0);
                } else {
                    textView.setVisibility(8);
                }
            }
        } else {
            TextView textView2 = (TextView) preferenceViewHolder.findViewById(android.R.id.summary);
            if (textView2 != null && this.mHelper.mDisabledByAdmin) {
                textView2.setText(charSequence);
                textView2.setVisibility(0);
            }
        }
        if (findViewById2 != null) {
            findViewById2.setVisibility(this.mIconSpaceReserved ? 0 : 8);
        }
    }

    @Override // androidx.preference.Preference
    public final void performClick() {
        if (this.mHelper.performClick()) {
            return;
        }
        super.performClick();
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0024  */
    /* JADX WARN: Removed duplicated region for block: B:15:? A[RETURN, SYNTHETIC] */
    @Override // androidx.preference.Preference
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void setEnabled(boolean z) {
        boolean z2;
        RestrictedPreferenceHelper restrictedPreferenceHelper;
        boolean z3;
        boolean z4 = true;
        if (z) {
            RestrictedPreferenceHelper restrictedPreferenceHelper2 = this.mHelper;
            if (restrictedPreferenceHelper2.mDisabledByAdmin) {
                restrictedPreferenceHelper2.setDisabledByAdmin(null);
                z2 = true;
                if (z || !(z3 = (restrictedPreferenceHelper = this.mHelper).mDisabledByAppOps)) {
                    z4 = z2;
                } else if (z3) {
                    restrictedPreferenceHelper.mDisabledByAppOps = false;
                    restrictedPreferenceHelper.updateDisabledState();
                }
                if (z4) {
                    super.setEnabled(z);
                    return;
                }
                return;
            }
        }
        z2 = false;
        if (z) {
        }
        z4 = z2;
        if (z4) {
        }
    }

    public RestrictedSwitchPreference(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public RestrictedSwitchPreference(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, TypedArrayUtils.getAttr(R.attr.switchPreferenceStyle, context, android.R.attr.switchPreferenceStyle));
    }

    public RestrictedSwitchPreference(Context context) {
        this(context, null);
    }

    public void setAppOps(AppOpsManager appOpsManager) {
    }
}
