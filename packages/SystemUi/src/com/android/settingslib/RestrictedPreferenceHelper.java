package com.android.settingslib;

import android.R;
import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.Switch;
import android.widget.TextView;
import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;
import com.android.settingslib.RestrictedLockUtils;
import java.util.function.Supplier;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class RestrictedPreferenceHelper {
    public final String mAttrUserRestriction;
    public final Context mContext;
    public boolean mDisabledByAdmin;
    public boolean mDisabledByAppOps;
    public boolean mDisabledSummary;
    public RestrictedLockUtils.EnforcedAdmin mEnforcedAdmin;
    public final Preference mPreference;
    public final String packageName;
    public final int uid;

    public RestrictedPreferenceHelper(Context context, Preference preference, AttributeSet attributeSet, String str, int i) {
        CharSequence charSequence;
        this.mAttrUserRestriction = null;
        boolean z = false;
        this.mDisabledSummary = false;
        this.mContext = context;
        this.mPreference = preference;
        this.packageName = str;
        this.uid = i;
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

    public final void onAttachedToHierarchy() {
        String str = this.mAttrUserRestriction;
        if (str != null) {
            setDisabledByAdmin(RestrictedLockUtilsInternal.checkIfRestrictionEnforced(this.mContext, str, UserHandle.myUserId()));
        }
    }

    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        TextView textView;
        if (this.mDisabledByAdmin || this.mDisabledByAppOps) {
            preferenceViewHolder.itemView.setEnabled(true);
        }
        if (!this.mDisabledSummary || (textView = (TextView) preferenceViewHolder.findViewById(R.id.summary)) == null) {
            return;
        }
        String string = ((DevicePolicyManager) this.mContext.getSystemService(DevicePolicyManager.class)).getResources().getString("Settings.CONTROLLED_BY_ADMIN_SUMMARY", new Supplier() { // from class: com.android.settingslib.RestrictedPreferenceHelper$$ExternalSyntheticLambda0
            @Override // java.util.function.Supplier
            public final Object get() {
                return RestrictedPreferenceHelper.this.mContext.getString(com.android.systemui.R.string.disabled_by_admin_summary_text);
            }
        });
        if (this.mDisabledByAdmin) {
            textView.setText(string);
        } else if (this.mDisabledByAppOps) {
            textView.setText(com.android.systemui.R.string.disabled_by_app_ops_text);
        } else if (TextUtils.equals(string, textView.getText())) {
            textView.setText((CharSequence) null);
        }
    }

    public final boolean performClick() {
        boolean z = this.mDisabledByAdmin;
        Context context = this.mContext;
        if (z) {
            RestrictedLockUtils.sendShowAdminSupportDetailsIntent(context, this.mEnforcedAdmin);
            return true;
        }
        if (!this.mDisabledByAppOps) {
            return false;
        }
        Intent intent = new Intent("android.settings.SHOW_RESTRICTED_SETTING_DIALOG");
        intent.putExtra("android.intent.extra.PACKAGE_NAME", this.packageName);
        intent.putExtra("android.intent.extra.UID", this.uid);
        context.startActivity(intent);
        return true;
    }

    public final void setDisabledByAdmin(RestrictedLockUtils.EnforcedAdmin enforcedAdmin) {
        boolean z = enforcedAdmin != null;
        this.mEnforcedAdmin = enforcedAdmin;
        if (this.mDisabledByAdmin != z) {
            this.mDisabledByAdmin = z;
            updateDisabledState();
        }
    }

    public final void updateDisabledState() {
        Preference preference = this.mPreference;
        if (!(preference instanceof RestrictedTopLevelPreference)) {
            preference.setEnabled((this.mDisabledByAdmin || this.mDisabledByAppOps) ? false : true);
        }
        if (preference instanceof PrimarySwitchPreference) {
            PrimarySwitchPreference primarySwitchPreference = (PrimarySwitchPreference) preference;
            boolean z = (this.mDisabledByAdmin || this.mDisabledByAppOps) ? false : true;
            primarySwitchPreference.mEnableSwitch = z;
            Switch r4 = primarySwitchPreference.mSwitch;
            if (r4 != null) {
                r4.setEnabled(z);
            }
        }
    }

    public RestrictedPreferenceHelper(Context context, Preference preference, AttributeSet attributeSet) {
        this(context, preference, attributeSet, null, -1);
    }
}
