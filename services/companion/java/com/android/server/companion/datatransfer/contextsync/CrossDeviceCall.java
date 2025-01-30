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
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/* loaded from: classes.dex */
public class CrossDeviceCall {
    public final Call mCall;
    public String mCallerDisplayName;
    public int mCallerDisplayNamePresentation;
    public byte[] mCallingAppIcon;
    public String mCallingAppName;
    public final String mCallingAppPackageName;
    public String mContactDisplayName;
    public final Set mControls;
    public int mDirection;
    public Uri mHandle;
    public int mHandlePresentation;
    public final String mId;
    public final boolean mIsCallPlacedByContextSync;
    boolean mIsEnterprise;
    public boolean mIsMuted;
    public final String mSerializedPhoneAccountHandle;
    public int mStatus;
    public final int mUserId;

    public static int convertStatusToState(int i) {
        switch (i) {
            case 1:
            case 4:
                return 2;
            case 2:
                return 4;
            case 3:
                return 3;
            case 5:
                return 12;
            case 6:
                return 13;
            case 7:
                return 7;
            case 8:
                return 1;
            default:
                return 0;
        }
    }

    public CrossDeviceCall(Context context, Call call, CallAudioState callAudioState) {
        this(context, call, call.getDetails(), callAudioState);
    }

    public CrossDeviceCall(Context context, Call call, Call.Details details, CallAudioState callAudioState) {
        boolean z = false;
        this.mStatus = 0;
        this.mControls = new HashSet();
        this.mCall = call;
        String string = details.getIntentExtras() != null ? details.getIntentExtras().getString("com.android.companion.datatransfer.contextsync.extra.CALL_ID") : null;
        String uuid = UUID.randomUUID().toString();
        if (string != null) {
            uuid = uuid + "::" + string;
        }
        this.mId = uuid;
        if (call != null) {
            call.putExtra("com.android.companion.datatransfer.contextsync.extra.CALL_ID", uuid);
        }
        PhoneAccountHandle accountHandle = details.getAccountHandle();
        int identifier = accountHandle != null ? accountHandle.getUserHandle().getIdentifier() : -1;
        this.mUserId = identifier;
        this.mIsCallPlacedByContextSync = accountHandle != null && new ComponentName(context, (Class<?>) CallMetadataSyncConnectionService.class).equals(accountHandle.getComponentName());
        String str = "";
        String packageName = accountHandle != null ? details.getAccountHandle().getComponentName().getPackageName() : "";
        this.mCallingAppPackageName = packageName;
        if (accountHandle != null) {
            str = accountHandle.getId() + "::" + accountHandle.getComponentName().flattenToString();
        }
        this.mSerializedPhoneAccountHandle = str;
        this.mIsEnterprise = (details.getCallProperties() & 32) == 32;
        PackageManager packageManager = context.getPackageManager();
        try {
            ApplicationInfo applicationInfoAsUser = packageManager.getApplicationInfoAsUser(packageName, PackageManager.ApplicationInfoFlags.of(0L), identifier);
            this.mCallingAppName = packageManager.getApplicationLabel(applicationInfoAsUser).toString();
            this.mCallingAppIcon = BitmapUtils.renderDrawableToByteArray(packageManager.getApplicationIcon(applicationInfoAsUser));
        } catch (PackageManager.NameNotFoundException e) {
            Slog.e("CrossDeviceCall", "Could not get application info for package " + this.mCallingAppPackageName, e);
        }
        if (callAudioState != null && callAudioState.isMuted()) {
            z = true;
        }
        this.mIsMuted = z;
        updateCallDetails(details);
    }

    public void updateCallDetails(Call.Details details) {
        this.mCallerDisplayName = details.getCallerDisplayName();
        this.mCallerDisplayNamePresentation = details.getCallerDisplayNamePresentation();
        this.mContactDisplayName = details.getContactDisplayName();
        this.mHandle = details.getHandle();
        this.mHandlePresentation = details.getHandlePresentation();
        int callDirection = details.getCallDirection();
        if (callDirection == 0) {
            this.mDirection = 1;
        } else if (callDirection == 1) {
            this.mDirection = 2;
        } else {
            this.mDirection = 0;
        }
        this.mStatus = convertStateToStatus(details.getState());
        this.mControls.clear();
        if (this.mStatus == 8) {
            this.mControls.add(6);
        }
        int i = this.mStatus;
        if (i == 1 || i == 4) {
            this.mControls.add(1);
            this.mControls.add(2);
            if (this.mStatus == 1) {
                this.mControls.add(3);
            }
        }
        int i2 = this.mStatus;
        if (i2 == 2 || i2 == 3) {
            this.mControls.add(6);
            if (details.can(1)) {
                this.mControls.add(Integer.valueOf(this.mStatus != 3 ? 7 : 8));
            }
        }
        if (this.mStatus == 2 && details.can(64)) {
            this.mControls.add(Integer.valueOf(this.mIsMuted ? 5 : 4));
        }
    }

    public static int convertStateToStatus(int i) {
        int i2 = 1;
        if (i == 1) {
            return 8;
        }
        if (i != 2) {
            i2 = 3;
            if (i != 3) {
                if (i == 4) {
                    return 2;
                }
                if (i == 7) {
                    return 7;
                }
                if (i == 12) {
                    return 5;
                }
                if (i == 13) {
                    return 6;
                }
                Slog.e("CrossDeviceCall", "Couldn't resolve state to status: " + i);
                return 0;
            }
        }
        return i2;
    }

    public String getId() {
        return this.mId;
    }

    public int getUserId() {
        return this.mUserId;
    }

    public String getCallingAppName() {
        return this.mCallingAppName;
    }

    public byte[] getCallingAppIcon() {
        return this.mCallingAppIcon;
    }

    public String getCallingAppPackageName() {
        return this.mCallingAppPackageName;
    }

    public String getSerializedPhoneAccountHandle() {
        return this.mSerializedPhoneAccountHandle;
    }

    public String getReadableCallerId(boolean z) {
        if (this.mIsEnterprise && z) {
            return getNonContactString();
        }
        return TextUtils.isEmpty(this.mContactDisplayName) ? getNonContactString() : this.mContactDisplayName;
    }

    public final String getNonContactString() {
        if (!TextUtils.isEmpty(this.mCallerDisplayName) && this.mCallerDisplayNamePresentation == 1) {
            return this.mCallerDisplayName;
        }
        Uri uri = this.mHandle;
        if (uri == null || uri.getSchemeSpecificPart() == null || this.mHandlePresentation != 1) {
            return null;
        }
        return this.mHandle.getSchemeSpecificPart();
    }

    public int getStatus() {
        return this.mStatus;
    }

    public int getDirection() {
        return this.mDirection;
    }

    public Set getControls() {
        return this.mControls;
    }

    public boolean isCallPlacedByContextSync() {
        return this.mIsCallPlacedByContextSync;
    }

    public void doAccept() {
        this.mCall.answer(0);
    }

    public void doReject() {
        if (this.mStatus == 1) {
            this.mCall.reject(1);
        }
    }

    public void doEnd() {
        this.mCall.disconnect();
    }

    public void doPutOnHold() {
        this.mCall.hold();
    }

    public void doTakeOffHold() {
        this.mCall.unhold();
    }
}
