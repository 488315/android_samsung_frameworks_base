package com.samsung.android.knox.profile;

import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.knox.container.RCPPolicy$$ExternalSyntheticOutline0;
import com.samsung.android.knox.license.EnterpriseLicenseManager;
import com.samsung.android.knox.profile.IProfilePolicy;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class ProfilePolicy {
    public static final String KNOX_PROFILE_POLICY_UPDATE = "com.samsung.android.knox.profilepolicy.intent.action.update";
    public static final String RESTRICTION_PROPERTY_CALENDAR_SHARE_TO_OWNER = "restriction_property_calendar_share_to_owner";
    public static final String RESTRICTION_PROPERTY_MOVE_FILES_TO_OWNER = "restriction_property_move_files_to_owner";
    public static final String RESTRICTION_PROPERTY_MOVE_FILES_TO_PROFILE = "restriction_property_move_files_to_profile";
    public static final String RESTRICTION_PROPERTY_SCREENCAPTURE_SAVE_TO_OWNER = "restriction_property_screencapture_save_to_owner";
    public static final int USER_ID_WORK_PROFILE = -1;
    public final String TAG = "ProfilePolicy";
    public ContextInfo mContextInfo;
    public IProfilePolicy mProfilePolicy;
    public int mUserId;

    public ProfilePolicy(ContextInfo contextInfo) {
        this.mUserId = -1;
        if (this.mProfilePolicy == null) {
            this.mProfilePolicy = getService();
        }
        this.mContextInfo = contextInfo;
        this.mUserId = -1;
    }

    public final boolean getRestriction(String str) {
        EnterpriseLicenseManager.log(this.mContextInfo, "ProfilePolicy.getPORestrictionPolicy");
        int i = this.mUserId;
        if (i != -1) {
            return SemPersonaManager.getRestriction(str, i);
        }
        IProfilePolicy iProfilePolicy = this.mProfilePolicy;
        if (iProfilePolicy == null) {
            Log.d("ProfilePolicy", " Profile policy is not yet ready!!!");
            return false;
        }
        try {
            return iProfilePolicy.getRestrictionPolicy(this.mContextInfo, str);
        } catch (RemoteException e) {
            RCPPolicy$$ExternalSyntheticOutline0.m244m(e, new StringBuilder("Failed talking with ProfilePolicy service: "), "ProfilePolicy");
            return false;
        }
    }

    public final IProfilePolicy getService() {
        if (this.mProfilePolicy == null) {
            this.mProfilePolicy = IProfilePolicy.Stub.asInterface(ServiceManager.getService("profilepolicy"));
        }
        return this.mProfilePolicy;
    }

    public final boolean setRestriction(String str, boolean z) {
        EnterpriseLicenseManager.log(this.mContextInfo, "ProfilePolicy.setPORestrictionPolicy");
        StringBuilder sb = new StringBuilder("setRestriction ");
        sb.append(str);
        sb.append(" / ");
        RecyclerView$$ExternalSyntheticOutline0.m46m(sb, this.mUserId, "ProfilePolicy");
        if (this.mUserId != -1) {
            return false;
        }
        IProfilePolicy iProfilePolicy = this.mProfilePolicy;
        if (iProfilePolicy == null) {
            Log.d("ProfilePolicy", " Profile policy is not yet ready!!!");
            return false;
        }
        try {
            return iProfilePolicy.setRestrictionPolicy(this.mContextInfo, str, z);
        } catch (RemoteException e) {
            RCPPolicy$$ExternalSyntheticOutline0.m244m(e, new StringBuilder("Failed talking with ProfilePolicy service: "), "ProfilePolicy");
            return false;
        }
    }

    public ProfilePolicy(ContextInfo contextInfo, int i) {
        this.mUserId = -1;
        if (this.mProfilePolicy == null) {
            this.mProfilePolicy = getService();
        }
        this.mContextInfo = contextInfo;
        this.mUserId = i;
    }

    public final boolean getRestriction(ContextInfo contextInfo, String str) {
        EnterpriseLicenseManager.log(this.mContextInfo, "ProfilePolicy.getPORestrictionPolicy");
        int i = this.mUserId;
        if (i != -1) {
            return SemPersonaManager.getRestriction(str, i);
        }
        IProfilePolicy iProfilePolicy = this.mProfilePolicy;
        if (iProfilePolicy == null) {
            Log.d("ProfilePolicy", " Profile policy is not yet ready!!! false");
            return false;
        }
        try {
            return iProfilePolicy.getRestrictionPolicy(contextInfo, str);
        } catch (Exception e) {
            Log.e("ProfilePolicy", "Failed talking with ProfilePolicy service: " + Log.getStackTraceString(e));
            return false;
        }
    }

    public final boolean setRestriction(ContextInfo contextInfo, String str, boolean z) {
        EnterpriseLicenseManager.log(this.mContextInfo, "ProfilePolicy.setPORestrictionPolicy");
        StringBuilder sb = new StringBuilder("setRestriction ");
        sb.append(str);
        sb.append(" / ");
        RecyclerView$$ExternalSyntheticOutline0.m46m(sb, this.mUserId, "ProfilePolicy");
        if (this.mUserId != -1) {
            return false;
        }
        IProfilePolicy iProfilePolicy = this.mProfilePolicy;
        if (iProfilePolicy == null) {
            Log.d("ProfilePolicy", " Profile policy is not yet ready!!!");
            return false;
        }
        try {
            return iProfilePolicy.setRestrictionPolicy(contextInfo, str, z);
        } catch (Exception e) {
            Log.e("ProfilePolicy", "Failed talking with ProfilePolicy service: " + Log.getStackTraceString(e));
            return false;
        }
    }
}
