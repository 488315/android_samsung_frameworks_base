package com.samsung.accessory.manager.authentication.cover;

import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import com.samsung.accessory.manager.authentication.AuthenticationResult;
import java.util.Arrays;

/* loaded from: classes.dex */
public class PaletteCover {
    public static final String TAG = "SAccessoryManager_" + PaletteCover.class.getSimpleName();
    public Context mContext;
    public byte[] mUriData;
    public byte mPackage = 0;
    public boolean mCoverAttached = false;

    public PaletteCover(Context context) {
        this.mContext = context;
    }

    public boolean isCoverAttached() {
        return this.mCoverAttached;
    }

    public void setCoverVerified(boolean z, AuthenticationResult authenticationResult) {
        Log.d(TAG, "setCoverVerified:isVerified=" + z);
        this.mUriData = z ? authenticationResult.getByteArrayManagerURI() : null;
        onCoverStateChanged();
    }

    public final void onCoverStateChanged() {
        try {
            byte[] bArr = this.mUriData;
            String arrays = bArr != null ? Arrays.toString(bArr) : "";
            String str = TAG;
            Log.d(str, "On cover state change : data = " + arrays);
            Intent intent = new Intent();
            intent.putExtra("accessoryType", 18);
            if (this.mUriData != null) {
                intent.setAction("com.samsung.android.intent.action.ACCESSORY_COVER_ATTACH");
                this.mPackage = this.mUriData[1];
                Log.d(str, "Attach Target package type: " + ((int) this.mPackage));
                intent.putExtra("URI", this.mUriData);
                this.mCoverAttached = true;
            } else {
                Log.d(str, "Palette cover detached");
                intent.putExtra("URI", "");
                intent.setAction("com.samsung.android.intent.action.ACCESSORY_COVER_DETACH");
                this.mCoverAttached = false;
            }
            if ((this.mPackage & 1) != 0) {
                intent.setPackage("com.samsung.android.app.dressroom");
                this.mContext.sendBroadcast(intent);
            }
            if ((this.mPackage & 2) != 0) {
                intent.setPackage("com.samsung.android.app.aodservice");
                this.mContext.sendBroadcast(intent);
            }
            if (this.mUriData == null) {
                this.mPackage = (byte) 0;
            }
            Settings.System.putString(this.mContext.getContentResolver(), "accessory_cover_uri", arrays);
        } catch (Exception unused) {
            Log.d(TAG, "error during cover state change");
        }
    }

    public static boolean isSettingEnabled(Context context) {
        return !TextUtils.isEmpty(getSetURI(context));
    }

    public static boolean isDataChanged(Context context, byte[] bArr) {
        String arrays = bArr != null ? Arrays.toString(bArr) : "";
        String setURI = getSetURI(context);
        Log.d(TAG, "isDataChanged:uriDataString=" + arrays + ",uriSet=" + setURI);
        return (TextUtils.isEmpty(setURI) || arrays.equals(setURI)) ? false : true;
    }

    public static String getSetURI(Context context) {
        return Settings.System.getString(context.getContentResolver(), "accessory_cover_uri");
    }

    public void disableSetting() {
        Log.i(TAG, "disableSetting");
        this.mPackage = (byte) 3;
        this.mUriData = null;
        onCoverStateChanged();
    }
}
