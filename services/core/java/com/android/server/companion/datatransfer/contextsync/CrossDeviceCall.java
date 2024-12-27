package com.android.server.companion.datatransfer.contextsync;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.telecom.Call;
import android.telecom.CallAudioState;
import android.telecom.PhoneAccountHandle;
import android.text.TextUtils;
import android.util.Slog;

import com.android.server.AnyMotionDetector$$ExternalSyntheticOutline0;
import com.android.server.NandswapManager$$ExternalSyntheticOutline0;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class CrossDeviceCall {
    public final Call mCall;
    public String mCallerDisplayName;
    public int mCallerDisplayNamePresentation;
    public final byte[] mCallingAppIcon;
    public final String mCallingAppName;
    public final String mCallingAppPackageName;
    public String mContactDisplayName;
    public final Set mControls;
    public int mDirection;
    public Uri mHandle;
    public int mHandlePresentation;
    public final String mId;
    public final boolean mIsCallPlacedByContextSync;
    boolean mIsEnterprise;
    public final boolean mIsMuted;
    public final String mSerializedPhoneAccountHandle;
    public int mStatus;
    public final int mUserId;

    public CrossDeviceCall(Context context, Call call, CallAudioState callAudioState) {
        Call.Details details = call.getDetails();
        boolean z = false;
        this.mStatus = 0;
        this.mControls = new HashSet();
        this.mCall = call;
        String string =
                details.getIntentExtras() != null
                        ? details.getIntentExtras()
                                .getString(
                                        "com.android.companion.datatransfer.contextsync.extra.CALL_ID")
                        : null;
        String uuid = UUID.randomUUID().toString();
        uuid =
                string != null
                        ? AnyMotionDetector$$ExternalSyntheticOutline0.m(uuid, "::", string)
                        : uuid;
        this.mId = uuid;
        call.putExtra("com.android.companion.datatransfer.contextsync.extra.CALL_ID", uuid);
        PhoneAccountHandle accountHandle = details.getAccountHandle();
        int identifier = accountHandle != null ? accountHandle.getUserHandle().getIdentifier() : -1;
        this.mUserId = identifier;
        this.mIsCallPlacedByContextSync =
                accountHandle != null
                        && new ComponentName(
                                        context, (Class<?>) CallMetadataSyncConnectionService.class)
                                .equals(accountHandle.getComponentName());
        String str = "";
        String packageName =
                accountHandle != null
                        ? details.getAccountHandle().getComponentName().getPackageName()
                        : "";
        this.mCallingAppPackageName = packageName;
        if (accountHandle != null) {
            str = accountHandle.getId() + "::" + accountHandle.getComponentName().flattenToString();
        }
        this.mSerializedPhoneAccountHandle = str;
        this.mIsEnterprise = (details.getCallProperties() & 32) == 32;
        PackageManager packageManager = context.getPackageManager();
        try {
            ApplicationInfo applicationInfoAsUser =
                    packageManager.getApplicationInfoAsUser(
                            packageName, PackageManager.ApplicationInfoFlags.of(0L), identifier);
            this.mCallingAppName =
                    packageManager.getApplicationLabel(applicationInfoAsUser).toString();
            this.mCallingAppIcon =
                    BitmapUtils.renderDrawableToByteArray(
                            packageManager.getApplicationIcon(applicationInfoAsUser));
        } catch (PackageManager.NameNotFoundException e) {
            Slog.e(
                    "CrossDeviceCall",
                    "Could not get application info for package " + this.mCallingAppPackageName,
                    e);
        }
        if (callAudioState != null && callAudioState.isMuted()) {
            z = true;
        }
        this.mIsMuted = z;
        updateCallDetails(details);
    }

    public final String getNonContactString() {
        if (!TextUtils.isEmpty(this.mCallerDisplayName)
                && this.mCallerDisplayNamePresentation == 1) {
            return this.mCallerDisplayName;
        }
        Uri uri = this.mHandle;
        if (uri == null || uri.getSchemeSpecificPart() == null || this.mHandlePresentation != 1) {
            return null;
        }
        return this.mHandle.getSchemeSpecificPart();
    }

    public void updateCallDetails(Call.Details details) {
        this.mCallerDisplayName = details.getCallerDisplayName();
        this.mCallerDisplayNamePresentation = details.getCallerDisplayNamePresentation();
        this.mContactDisplayName = details.getContactDisplayName();
        this.mHandle = details.getHandle();
        this.mHandlePresentation = details.getHandlePresentation();
        int callDirection = details.getCallDirection();
        int i = 0;
        if (callDirection == 0) {
            this.mDirection = 1;
        } else if (callDirection == 1) {
            this.mDirection = 2;
        } else {
            this.mDirection = 0;
        }
        int state = details.getState();
        if (state == 1) {
            i = 8;
        } else if (state == 2) {
            i = 1;
        } else if (state == 3) {
            i = 3;
        } else if (state == 4) {
            i = 2;
        } else if (state == 7) {
            i = 7;
        } else if (state == 12) {
            i = 5;
        } else if (state != 13) {
            NandswapManager$$ExternalSyntheticOutline0.m(
                    state, "Couldn't resolve state to status: ", "CrossDeviceCall");
        } else {
            i = 6;
        }
        this.mStatus = i;
        ((HashSet) this.mControls).clear();
        if (this.mStatus == 8) {
            ((HashSet) this.mControls).add(6);
        }
        int i2 = this.mStatus;
        if (i2 == 1 || i2 == 4) {
            ((HashSet) this.mControls).add(1);
            ((HashSet) this.mControls).add(2);
            if (this.mStatus == 1) {
                ((HashSet) this.mControls).add(3);
            }
        }
        int i3 = this.mStatus;
        if (i3 == 2 || i3 == 3) {
            ((HashSet) this.mControls).add(6);
            if (details.can(1)) {
                ((HashSet) this.mControls).add(Integer.valueOf(this.mStatus == 3 ? 8 : 7));
            }
        }
        if (this.mStatus == 2 && details.can(64)) {
            ((HashSet) this.mControls).add(Integer.valueOf(this.mIsMuted ? 5 : 4));
        }
    }
}
