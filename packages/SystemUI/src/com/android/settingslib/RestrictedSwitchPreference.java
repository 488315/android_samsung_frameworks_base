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

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
            if (this.mHelper.isRestrictionEnforcedByAdvancedProtection()) {
                charSequence = null;
            } else if (this.mChecked) {
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
                if (!this.mHelper.mDisabledByAdmin || charSequence == null) {
                    textView.setVisibility(8);
                } else {
                    textView.setText(charSequence);
                    textView.setVisibility(0);
                }
            }
        } else {
            TextView textView2 = (TextView) preferenceViewHolder.findViewById(android.R.id.summary);
            if (textView2 != null && this.mHelper.mDisabledByAdmin && charSequence != null) {
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

    /* JADX WARN: Removed duplicated region for block: B:11:0x001f  */
    /* JADX WARN: Removed duplicated region for block: B:14:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0012  */
    @Override // androidx.preference.Preference
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void setEnabled(boolean r5) {
        /*
            r4 = this;
            r0 = 1
            if (r5 == 0) goto Lf
            com.android.settingslib.RestrictedPreferenceHelper r1 = r4.mHelper
            boolean r2 = r1.mDisabledByAdmin
            if (r2 == 0) goto Lf
            r2 = 0
            r1.setDisabledByAdmin(r2)
            r1 = r0
            goto L10
        Lf:
            r1 = 0
        L10:
            if (r5 == 0) goto L1c
            com.android.settingslib.RestrictedPreferenceHelper r2 = r4.mHelper
            boolean r3 = r2.mDisabledByEcm
            if (r3 == 0) goto L1c
            r2.setDisabledByEcm()
            goto L1d
        L1c:
            r0 = r1
        L1d:
            if (r0 != 0) goto L22
            super.setEnabled(r5)
        L22:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.settingslib.RestrictedSwitchPreference.setEnabled(boolean):void");
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
