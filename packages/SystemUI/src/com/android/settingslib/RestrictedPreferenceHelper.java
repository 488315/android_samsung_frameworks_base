package com.android.settingslib;

import android.app.admin.DevicePolicyManager;
import android.app.admin.EnforcingAdmin;
import android.app.admin.UnknownAuthority;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.CompoundButton;
import android.widget.TextView;
import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;
import com.android.settingslib.RestrictedLockUtils;
import com.android.systemui.R;
import java.util.function.Supplier;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class RestrictedPreferenceHelper {
    public final String mAttrUserRestriction;
    public final Context mContext;
    public boolean mDisabledByAdmin;
    public boolean mDisabledByEcm;
    public boolean mDisabledSummary;
    RestrictedLockUtils.EnforcedAdmin mEnforcedAdmin;
    public final Preference mPreference;

    public RestrictedPreferenceHelper(Context context, Preference preference, AttributeSet attributeSet, String str, int i) {
        CharSequence charSequence;
        this.mAttrUserRestriction = null;
        boolean z = false;
        this.mDisabledSummary = false;
        this.mContext = context;
        this.mPreference = preference;
        if (attributeSet != null) {
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.RestrictedPreference);
            TypedValue peekValue = obtainStyledAttributes.peekValue(1);
            if (peekValue == null || peekValue.type != 3) {
                charSequence = null;
            } else {
                int i2 = peekValue.resourceId;
                charSequence = i2 != 0 ? context.getText(i2) : peekValue.string;
            }
            String charSequence2 = charSequence == null ? null : charSequence.toString();
            this.mAttrUserRestriction = charSequence2;
            if (RestrictedLockUtilsInternal.hasBaseUserRestriction(context, charSequence2, UserHandle.myUserId())) {
                this.mAttrUserRestriction = null;
                return;
            }
            TypedValue peekValue2 = obtainStyledAttributes.peekValue(0);
            if (peekValue2 != null) {
                if (peekValue2.type == 18 && peekValue2.data != 0) {
                    z = true;
                }
                this.mDisabledSummary = z;
            }
        }
    }

    public final String getDisabledByAdminSummaryString() {
        if (isRestrictionEnforcedByAdvancedProtection()) {
            return null;
        }
        return ((DevicePolicyManager) this.mContext.getSystemService(DevicePolicyManager.class)).getResources().getString("Settings.CONTROLLED_BY_ADMIN_SUMMARY", new Supplier() { // from class: com.android.settingslib.RestrictedPreferenceHelper$$ExternalSyntheticLambda0
            @Override // java.util.function.Supplier
            public final Object get() {
                return RestrictedPreferenceHelper.this.mContext.getString(R.string.disabled_by_admin_summary_text);
            }
        });
    }

    public final boolean isRestrictionEnforcedByAdvancedProtection() {
        EnforcingAdmin enforcingAdmin;
        RestrictedLockUtils.EnforcedAdmin enforcedAdmin = this.mEnforcedAdmin;
        if (enforcedAdmin == null) {
            return false;
        }
        Context context = this.mContext;
        String str = enforcedAdmin.enforcedRestriction;
        int myUserId = UserHandle.myUserId();
        boolean z = RestrictedLockUtilsInternal.DEBUG;
        if (str == null || (enforcingAdmin = ((DevicePolicyManager) context.getSystemService(DevicePolicyManager.class)).getEnforcingAdmin(myUserId, str)) == null) {
            return false;
        }
        UnknownAuthority authority = enforcingAdmin.getAuthority();
        return (authority instanceof UnknownAuthority) && "android.security.advancedprotection".equals(authority.getName());
    }

    public final void onAttachedToHierarchy() {
        String str = this.mAttrUserRestriction;
        if (str != null) {
            setDisabledByAdmin(RestrictedLockUtilsInternal.checkIfRestrictionEnforced(this.mContext, str, UserHandle.myUserId()));
        }
    }

    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        TextView textView;
        if (this.mDisabledByAdmin || this.mDisabledByEcm) {
            preferenceViewHolder.itemView.setEnabled(true);
        }
        if (!this.mDisabledSummary || (textView = (TextView) preferenceViewHolder.findViewById(android.R.id.summary)) == null) {
            return;
        }
        String disabledByAdminSummaryString = getDisabledByAdminSummaryString();
        if (this.mDisabledByAdmin && disabledByAdminSummaryString != null) {
            textView.setText(disabledByAdminSummaryString);
        } else if (this.mDisabledByEcm) {
            textView.setText(R.string.disabled_by_app_ops_text);
        } else if (TextUtils.equals(disabledByAdminSummaryString, textView.getText())) {
            textView.setText((CharSequence) null);
        }
    }

    public final boolean performClick() {
        if (this.mDisabledByAdmin) {
            RestrictedLockUtils.sendShowAdminSupportDetailsIntent(this.mContext, this.mEnforcedAdmin);
            return true;
        }
        if (!this.mDisabledByEcm) {
            return false;
        }
        this.mContext.startActivity(null);
        return true;
    }

    public final void setDisabledByAdmin(RestrictedLockUtils.EnforcedAdmin enforcedAdmin) {
        boolean z;
        RestrictedLockUtils.EnforcedAdmin enforcedAdmin2 = this.mEnforcedAdmin;
        this.mEnforcedAdmin = null;
        boolean z2 = true;
        if (enforcedAdmin != null) {
            this.mEnforcedAdmin = new RestrictedLockUtils.EnforcedAdmin(enforcedAdmin);
            z = enforcedAdmin2 == null || !enforcedAdmin2.equals(enforcedAdmin);
            r2 = true;
        } else {
            z = false;
        }
        if (this.mDisabledByAdmin != r2) {
            this.mDisabledByAdmin = r2;
        } else {
            z2 = z;
        }
        if (z2) {
            updateDisabledState();
        }
    }

    public final void setDisabledByEcm() {
        if (this.mDisabledByEcm) {
            this.mDisabledByEcm = false;
            updateDisabledState();
        }
    }

    public final void updateDisabledState() {
        String disabledByAdminSummaryString;
        boolean z = (this.mDisabledByAdmin || this.mDisabledByEcm) ? false : true;
        Preference preference = this.mPreference;
        if (!(preference instanceof RestrictedTopLevelPreference)) {
            preference.setEnabled(z);
        }
        if (preference instanceof PrimarySwitchPreference) {
            PrimarySwitchPreference primarySwitchPreference = (PrimarySwitchPreference) preference;
            primarySwitchPreference.mEnableSwitch = z;
            CompoundButton compoundButton = primarySwitchPreference.mSwitch;
            if (compoundButton != null) {
                compoundButton.setEnabled(z);
            }
        }
        if (!z && this.mDisabledByAdmin && (disabledByAdminSummaryString = getDisabledByAdminSummaryString()) != null) {
            preference.setSummary(disabledByAdminSummaryString);
        }
        if (!this.mDisabledByAdmin && TextUtils.equals(preference.getSummary(), getDisabledByAdminSummaryString())) {
            preference.setSummary((CharSequence) null);
        }
        if (z || !this.mDisabledByEcm) {
            return;
        }
        preference.setSummary(R.string.disabled_by_app_ops_text);
    }

    public RestrictedPreferenceHelper(Context context, Preference preference, AttributeSet attributeSet) {
        this(context, preference, attributeSet, null, -1);
    }
}
