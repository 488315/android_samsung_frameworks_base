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
    private static final String TAG = "KnoxAI_" + KMTDManager.class.getSimpleName();

    public KMTDManager(IMTDService service, Context context) {
        Log.d(TAG, "mMtdService Constructor called: " + context.toString());
        this.mMtdService = service;
        this.mContext = context;
    }

    public void analyzeFrameBuffers(List<FrameBuffersInfo> framebufferList) {
        if (this.mMtdService != null) {
            try {
                this.mMtdService.analyzeFrameBuffers(framebufferList);
            } catch (Exception e) {
                Log.e(TAG, "Exception in MTD Service", e);
            }
        }
    }

    public void analyzeUrl(String url, String pkgName, Intent launchIntent, int uid) {
        if (this.mMtdService != null) {
            try {
                int userID = getUserID(uid);
                this.mMtdService.analyzeURL(url, pkgName, userID, getManagedProfileFlag(userID), launchIntent);
            } catch (Exception e) {
                Log.e(TAG, "Exception in KFBP Manager Service", e);
            }
        }
    }

    public void analyzeContent(String content, String pkgName, int uid) {
        if (this.mMtdService != null) {
            try {
                int userID = getUserID(uid);
                this.mMtdService.analyzeContent(content, pkgName, userID, getManagedProfileFlag(userID), uid);
            } catch (Exception e) {
                Log.e(TAG, "Exception in KFBP Manager Service", e);
            }
        }
    }

    private int getUserID(int uid) {
        return UserHandle.getUserId(uid);
    }

    private boolean getManagedProfileFlag(int userID) {
        UserManager mUm = (UserManager) this.mContext.getSystemService(UserManager.class);
        boolean isManagedProfileFlag = mUm.isManagedProfile(userID);
        return isManagedProfileFlag;
    }

    public void analyzeUrls(List<String> urlList, final MtdResultCallback cb, String location) {
        if (this.mMtdService != null) {
            IMtdCallback mtdCallback = new IMtdCallback.Stub() { // from class: com.samsung.android.knox.mtd.KMTDManager.1
                @Override // com.samsung.android.knox.mtd.IMtdCallback
                public void onFinished(List<AnalysisResult> result) {
                    cb.onFinished(result);
                }
            };
            try {
                this.mMtdService.analyzeURLs(urlList, mtdCallback, location);
            } catch (Exception e) {
                Log.e(TAG, "Exception in KFBP Manager Service", e);
            }
        }
    }

    public void analyzeContents(List<String> contentList, final MtdResultCallback cb) {
        if (this.mMtdService != null) {
            IMtdCallback mtdCallback = new IMtdCallback.Stub() { // from class: com.samsung.android.knox.mtd.KMTDManager.2
                @Override // com.samsung.android.knox.mtd.IMtdCallback
                public void onFinished(List<AnalysisResult> result) {
                    cb.onFinished(result);
                }
            };
            try {
                this.mMtdService.analyzeContents(contentList, mtdCallback);
            } catch (Exception e) {
                Log.e(TAG, "Exception in KFBP Manager Service", e);
            }
        }
    }

    public String getSystemProperty(String key) {
        if (this.mMtdService != null) {
            try {
                return this.mMtdService.getSystemProperty(key);
            } catch (Exception e) {
                Log.e(TAG, "Exception in KFBP Manager Service", e);
                return "";
            }
        }
        return "";
    }

    public void setSystemProperty(String key, String value) {
        if (this.mMtdService != null) {
            try {
                this.mMtdService.setSystemProperty(key, value);
            } catch (Exception e) {
                Log.e(TAG, "Exception in KFBP Manager Service", e);
            }
        }
    }
}
