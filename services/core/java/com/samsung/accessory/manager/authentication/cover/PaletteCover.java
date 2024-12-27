package com.samsung.accessory.manager.authentication.cover;

import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;

import com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticOutline0;

import com.samsung.accessory.manager.authentication.AuthenticationResult;

import java.util.Arrays;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class PaletteCover {
    public final Context mContext;
    public byte[] mUriData;
    public byte mPackage = 0;
    public boolean mCoverAttached = false;

    public PaletteCover(Context context) {
        this.mContext = context;
    }

    public static boolean isDataChanged(Context context, byte[] bArr) {
        String arrays = bArr != null ? Arrays.toString(bArr) : "";
        String string =
                Settings.System.getString(context.getContentResolver(), "accessory_cover_uri");
        Log.d(
                "SAccessoryManager_PaletteCover",
                "isDataChanged:uriDataString=" + arrays + ",uriSet=" + string);
        return (TextUtils.isEmpty(string) || arrays.equals(string)) ? false : true;
    }

    public final void disableSetting() {
        Log.i("SAccessoryManager_PaletteCover", "disableSetting");
        this.mPackage = (byte) 3;
        this.mUriData = null;
        onCoverStateChanged();
    }

    public final void onCoverStateChanged() {
        try {
            byte[] bArr = this.mUriData;
            String arrays = bArr != null ? Arrays.toString(bArr) : "";
            Log.d("SAccessoryManager_PaletteCover", "On cover state change : data = " + arrays);
            Intent intent = new Intent();
            intent.putExtra("accessoryType", 18);
            if (this.mUriData != null) {
                intent.setAction("com.samsung.android.intent.action.ACCESSORY_COVER_ATTACH");
                this.mPackage = this.mUriData[1];
                Log.d(
                        "SAccessoryManager_PaletteCover",
                        "Attach Target package type: " + ((int) this.mPackage));
                intent.putExtra("URI", this.mUriData);
                this.mCoverAttached = true;
            } else {
                Log.d("SAccessoryManager_PaletteCover", "Palette cover detached");
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
            Settings.System.putString(
                    this.mContext.getContentResolver(), "accessory_cover_uri", arrays);
            Settings.System.putString(
                    this.mContext.getContentResolver(),
                    "cover_type_id",
                    this.mCoverAttached ? "18" : "");
        } catch (Exception unused) {
            Log.d("SAccessoryManager_PaletteCover", "error during cover state change");
        }
    }

    public final void setCoverVerified(boolean z, AuthenticationResult authenticationResult) {
        AccessibilityManagerService$$ExternalSyntheticOutline0.m(
                "setCoverVerified:isVerified=", "SAccessoryManager_PaletteCover", z);
        this.mUriData = z ? authenticationResult.mByteArrayManagerURI : null;
        onCoverStateChanged();
    }
}
