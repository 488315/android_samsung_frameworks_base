package com.samsung.android.knox.mtd;

import android.content.Context;
import android.content.Intent;
import android.os.UserHandle;
import android.os.UserManager;
import android.util.Log;
import com.samsung.android.knox.mtd.IMtdCallback;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes6.dex */
public class KMTDManager {
    public static final String MTD_FEATURES = "knoxmtd.analysis.features";
    public static final String SERVICE_LABEL = "knox.mtd";
    private final Context mContext;
    private final IMTDService mMtdService;
    private static List<PackageInfo> mMonitoredPackageList = new ArrayList();
    private static final String TAG = "KnoxAI_KMTDManager";

    public enum PROFILE {
        NORMAL,
        MANAGED,
        DEVICE_OWNER
    }

    public KMTDManager(IMTDService iMTDService, Context context) {
        Log.d(TAG, "mMtdService Constructor called: " + context.toString());
        this.mMtdService = iMTDService;
        this.mContext = context;
    }

    public void analyzeFrameBuffers(List<FrameBuffersInfo> list) {
        IMTDService iMTDService = this.mMtdService;
        if (iMTDService != null) {
            try {
                iMTDService.analyzeFrameBuffers(list);
            } catch (Exception e) {
                Log.e(TAG, "Exception in MTD Service", e);
            }
        }
    }

    public void analyzeUrl(String str, String str2, Intent intent, int i) {
        if (this.mMtdService != null) {
            try {
                int userID = getUserID(i);
                this.mMtdService.analyzeURL(str, str2, userID, getManagedProfileFlag(userID), intent);
            } catch (Exception e) {
                Log.e(TAG, "Exception in KFBP Manager Service", e);
            }
        }
    }

    public void analyzeContent(String str, String str2, int i) {
        if (this.mMtdService != null) {
            try {
                int userID = getUserID(i);
                this.mMtdService.analyzeContent(str, str2, userID, getManagedProfileFlag(userID), i);
            } catch (Exception e) {
                Log.e(TAG, "Exception in KFBP Manager Service", e);
            }
        }
    }

    private int getUserID(int i) {
        return UserHandle.getUserId(i);
    }

    private boolean getManagedProfileFlag(int i) {
        return ((UserManager) this.mContext.getSystemService(UserManager.class)).isManagedProfile(i);
    }

    public void analyzeUrls(List<String> list, final MtdResultCallback mtdResultCallback, String str) {
        if (this.mMtdService != null) {
            try {
                this.mMtdService.analyzeURLs(list, new IMtdCallback.Stub(this) { // from class: com.samsung.android.knox.mtd.KMTDManager.1
                    @Override // com.samsung.android.knox.mtd.IMtdCallback
                    public void onFinished(List<AnalysisResult> list2) {
                        mtdResultCallback.onFinished(list2);
                    }
                }, str);
            } catch (Exception e) {
                Log.e(TAG, "Exception in KFBP Manager Service", e);
            }
        }
    }

    public void analyzeContents(List<String> list, final MtdResultCallback mtdResultCallback) {
        if (this.mMtdService != null) {
            try {
                this.mMtdService.analyzeContents(list, new IMtdCallback.Stub(this) { // from class: com.samsung.android.knox.mtd.KMTDManager.2
                    @Override // com.samsung.android.knox.mtd.IMtdCallback
                    public void onFinished(List<AnalysisResult> list2) {
                        mtdResultCallback.onFinished(list2);
                    }
                });
            } catch (Exception e) {
                Log.e(TAG, "Exception in KFBP Manager Service", e);
            }
        }
    }

    public String getSystemProperty(String str) {
        IMTDService iMTDService = this.mMtdService;
        if (iMTDService != null) {
            try {
                return iMTDService.getSystemProperty(str);
            } catch (Exception e) {
                Log.e(TAG, "Exception in KFBP Manager Service", e);
                return "";
            }
        }
        return "";
    }

    public void setSystemProperty(String str, String str2) {
        IMTDService iMTDService = this.mMtdService;
        if (iMTDService != null) {
            try {
                iMTDService.setSystemProperty(str, str2);
            } catch (Exception e) {
                Log.e(TAG, "Exception in KFBP Manager Service", e);
            }
        }
    }
}
