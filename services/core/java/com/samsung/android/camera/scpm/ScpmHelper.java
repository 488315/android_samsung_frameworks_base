package com.samsung.android.camera.scpm;

import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Debug;
import android.os.ParcelFileDescriptor;
import android.util.Slog;
import com.samsung.android.knox.custom.KnoxCustomManagerService;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

/* loaded from: classes2.dex */
public class ScpmHelper {
    public static final String APP_VERSION;
    public static final boolean DEBUG;
    public Context mContext;
    public scpmCallback mScpmCallback;
    public String mToken;

    public interface scpmCallback {
        void onListReceived(String str, List list);

        void onRegistered(int i);
    }

    static {
        DEBUG = !Build.TYPE.equals("user") || Debug.semIsProductDev();
        APP_VERSION = Build.VERSION.RELEASE;
    }

    public ScpmHelper(Context context, scpmCallback scpmcallback) {
        this.mContext = context;
        this.mScpmCallback = scpmcallback;
    }

    public void registerScpm() {
        Slog.i("CameraService/ScpmHelper", "registerScpm");
        if (!isAvailable()) {
            Slog.i("CameraService/ScpmHelper", "registerScpm - SCPM is not available");
            this.mScpmCallback.onRegistered(2);
            return;
        }
        try {
            Bundle bundle = new Bundle();
            bundle.putString("packageName", "android");
            bundle.putString("appId", "k0fpqpbykt");
            bundle.putString("version", APP_VERSION);
            bundle.putString("receiverPackageName", "android");
            Bundle call = this.mContext.getContentResolver().call(Uri.parse("content://com.samsung.android.scpm.policy/"), "register", "android", bundle);
            if (call != null) {
                int i = call.getInt(KnoxCustomManagerService.SPCM_KEY_RESULT, -1);
                this.mToken = call.getString(KnoxCustomManagerService.SPCM_KEY_TOKEN, null);
                int i2 = call.getInt(KnoxCustomManagerService.SPCM_KEY_RESULT_CODE, -1);
                String string = call.getString(KnoxCustomManagerService.SPCM_KEY_RESULT_MESSAGE, "");
                if (i == 1) {
                    Slog.v("CameraService/ScpmHelper", "register success : rcode = " + i2 + ", rmsg = " + string);
                    this.mScpmCallback.onRegistered(1);
                } else {
                    Slog.e("CameraService/ScpmHelper", "failed to register: rcode = " + i2 + ", rmsg = " + string);
                    this.mScpmCallback.onRegistered(3);
                }
            }
        } catch (Exception e) {
            Slog.e("CameraService/ScpmHelper", "cannot register package : " + e.getMessage());
            this.mScpmCallback.onRegistered(3);
        }
    }

    public final boolean isAvailable() {
        return this.mContext.getPackageManager().resolveContentProvider(KnoxCustomManagerService.SPCM_PROVIDER_AUTHORITY, PackageManager.ComponentInfoFlags.of(8L)) != null;
    }

    public void getSCPMParameters() {
        if (!isAvailable()) {
            Slog.i("CameraService/ScpmHelper", "getSCPMParameters - SCPM is not available");
            return;
        }
        Uri parse = Uri.parse("content://com.samsung.android.scpm.policy/" + this.mToken + "/camera3rdpartylist-1857");
        if (DEBUG) {
            Slog.v("CameraService/ScpmHelper", "uri: " + parse);
        }
        try {
            ParcelFileDescriptor openFileDescriptor = this.mContext.getContentResolver().openFileDescriptor(parse, "r");
            try {
                if (openFileDescriptor == null) {
                    Bundle bundle = new Bundle();
                    bundle.putString(KnoxCustomManagerService.SPCM_KEY_TOKEN, this.mToken);
                    Bundle call = this.mContext.getContentResolver().call(parse, "getLastError", "android", bundle);
                    Slog.d("CameraService/ScpmHelper", "cannot get new policy : " + call.getInt(KnoxCustomManagerService.SPCM_KEY_RESULT_CODE) + ", " + call.getString(KnoxCustomManagerService.SPCM_KEY_RESULT_MESSAGE));
                } else {
                    parseData(openFileDescriptor);
                }
                if (openFileDescriptor != null) {
                    openFileDescriptor.close();
                }
            } finally {
            }
        } catch (FileNotFoundException e) {
            Slog.e("CameraService/ScpmHelper", "File not found!");
            e.printStackTrace();
        } catch (Exception e2) {
            Slog.e("CameraService/ScpmHelper", "getData fail because of exception! " + e2);
            e2.printStackTrace();
        }
    }

    public final boolean parseData(ParcelFileDescriptor parcelFileDescriptor) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(parcelFileDescriptor.getFileDescriptor()));
            try {
                JSONObject jSONObject = new JSONObject(new JSONTokener((String) bufferedReader.lines().collect(Collectors.joining())));
                JSONArray jSONArray = jSONObject.getJSONArray("packageInfo");
                ArrayList arrayList = new ArrayList();
                for (int i = 0; i < jSONArray.length(); i++) {
                    JSONObject jSONObject2 = jSONArray.getJSONObject(i);
                    arrayList.add(new PolicyListVO(jSONObject2.getString("packageName"), jSONObject2.getString("value"), jSONObject2.getString("disallowUnihalVersion")));
                    if (DEBUG) {
                        Slog.v("CameraService/ScpmHelper", "parseData " + arrayList.get(i));
                    }
                }
                if (DEBUG) {
                    Slog.v("CameraService/ScpmHelper", "parseData total size is " + arrayList.size());
                }
                this.mScpmCallback.onListReceived(jSONObject.getString("policyVersion"), arrayList);
                bufferedReader.close();
                return true;
            } finally {
            }
        } catch (Exception e) {
            Slog.e("CameraService/ScpmHelper", "Unknown exception : " + e.getMessage());
            return false;
        }
    }
}
