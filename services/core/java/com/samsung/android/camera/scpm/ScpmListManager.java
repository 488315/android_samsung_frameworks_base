package com.samsung.android.camera.scpm;

import android.os.Build;
import android.os.ParcelFileDescriptor;
import android.util.Slog;

import com.samsung.android.camera.scpm.list.Camera3rdPartyList;
import com.samsung.android.camera.scpm.list.HiddenIdPermittedList;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class ScpmListManager {
    public static final boolean DEBUG;
    public final CopyOnWriteArrayList mCurrentPolicyList = new CopyOnWriteArrayList();
    public final CopyOnWriteArrayList mDefaultPolicyList = new CopyOnWriteArrayList();
    public final CopyOnWriteArrayList mCoverFlexRotatePackageList = new CopyOnWriteArrayList();

    static {
        String str = Build.TYPE;
        DEBUG = str.equals("eng") || str.equals("userdebug");
    }

    public ScpmListManager() {
        for (ScpmList.PolicyType policyType : ScpmList.PolicyType.values()) {
            loadDefaultScpmList(policyType);
        }
    }

    public static JSONObject getJsonObject(ParcelFileDescriptor parcelFileDescriptor) {
        try {
            BufferedReader bufferedReader =
                    new BufferedReader(new FileReader(parcelFileDescriptor.getFileDescriptor()));
            try {
                JSONObject jSONObject =
                        new JSONObject(
                                new JSONTokener(
                                        (String)
                                                bufferedReader
                                                        .lines()
                                                        .collect(Collectors.joining())));
                bufferedReader.close();
                return jSONObject;
            } finally {
            }
        } catch (Exception e) {
            throw new RuntimeException("getJsonObject : " + e.getMessage());
        }
    }

    public final ScpmList getCurrentPolicy(ScpmList.PolicyType policyType) {
        return (ScpmList)
                this.mCurrentPolicyList.stream()
                        .filter(new ScpmListManager$$ExternalSyntheticLambda0(1, policyType))
                        .findAny()
                        .orElse(null);
    }

    public final synchronized List getCurrentPolicyList(ScpmList.PolicyType policyType) {
        return getCurrentPolicy(policyType).mPackageList;
    }

    public final synchronized String getDefaultVersion(ScpmList.PolicyType policyType) {
        return ((ScpmList)
                        this.mDefaultPolicyList.stream()
                                .filter(
                                        new ScpmListManager$$ExternalSyntheticLambda0(
                                                0, policyType))
                                .findAny()
                                .orElse(null))
                .mVersion;
    }

    public final synchronized void loadDefaultScpmList(ScpmList.PolicyType policyType) {
        ScpmList scpmList;
        try {
            this.mCurrentPolicyList.removeIf(
                    new ScpmListManager$$ExternalSyntheticLambda0(2, policyType));
            this.mDefaultPolicyList.removeIf(
                    new ScpmListManager$$ExternalSyntheticLambda0(3, policyType));
            int ordinal = policyType.ordinal();
            if (ordinal == 0) {
                scpmList = Camera3rdPartyList.INSTANCE;
            } else if (ordinal != 1) {
                Slog.e(
                        "CameraService/ScpmListManager",
                        "loadDefaultScpmList  Unknown Policy type" + policyType);
                scpmList = null;
            } else {
                scpmList = HiddenIdPermittedList.INSTANCE;
            }
            this.mDefaultPolicyList.add(scpmList);
            this.mCurrentPolicyList.add(scpmList);
            if (policyType == ScpmList.PolicyType.CAMERA_3RD_PARTY) {
                setCoverFlexRotatePkgList();
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    public final void parseAndUpdateData(JSONObject jSONObject, ScpmList scpmList) {
        boolean z;
        Slog.i("CameraService/ScpmListManager", "parseAndUpdateData " + scpmList.mType);
        try {
            JSONArray jSONArray = jSONObject.getJSONArray("packageInfo");
            CopyOnWriteArrayList copyOnWriteArrayList = new CopyOnWriteArrayList();
            String string = jSONObject.getString("policyVersion");
            if (!string.startsWith("20") || string.length() != 10) {
                Slog.e(
                        "CameraService/ScpmListManager",
                        "parseAndUpdateData : invalid form. ".concat(string));
                throw new RuntimeException();
            }
            int i = 0;
            while (true) {
                int length = jSONArray.length();
                z = DEBUG;
                if (i >= length) {
                    break;
                }
                JSONObject jSONObject2 = jSONArray.getJSONObject(i);
                copyOnWriteArrayList.add(
                        new PolicyListVO(
                                jSONObject2.getString(scpmList.mItemNames[0]),
                                jSONObject2.getString(scpmList.mItemNames[1]),
                                jSONObject2.getString(scpmList.mItemNames[2])));
                if (z) {
                    Slog.i(
                            "CameraService/ScpmListManager",
                            "parseAndUpdateData " + copyOnWriteArrayList.get(i));
                }
                i++;
            }
            if (z) {
                Slog.i(
                        "CameraService/ScpmListManager",
                        "parseAndUpdateData total size is " + copyOnWriteArrayList.size());
            }
            scpmList.mVersion = string;
            scpmList.mPackageList.clear();
            scpmList.mPackageList.addAll(copyOnWriteArrayList);
            if (scpmList.mType == ScpmList.PolicyType.CAMERA_3RD_PARTY) {
                setCoverFlexRotatePkgList();
            }
        } catch (Exception e) {
            throw new RuntimeException("parseAndUpdateData : " + e.getMessage());
        }
    }

    public final synchronized void setCoverFlexRotatePkgList() {
        List currentPolicyList = getCurrentPolicyList(ScpmList.PolicyType.CAMERA_3RD_PARTY);
        if (currentPolicyList != null) {
            Iterator it = ((CopyOnWriteArrayList) currentPolicyList).iterator();
            while (it.hasNext()) {
                PolicyListVO policyListVO = (PolicyListVO) it.next();
                if ((Integer.parseInt(policyListVO.value) & 2048) != 0) {
                    this.mCoverFlexRotatePackageList.addIfAbsent(policyListVO.packageName);
                }
            }
        }
    }
}
