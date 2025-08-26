package android.app;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Process;
import android.os.SystemProperties;
import android.util.Log;
import android.view.Choreographer;
import android.view.View;
import android.view.ViewTreeObserver;
import java.util.concurrent.atomic.AtomicBoolean;

/* loaded from: classes.dex */
public final class IdsController {
    private static final String IDS_KEY = "IDSCount";
    private static final String IDS_URI = "android.app.ActivityThread.IDS";
    private static final int NO_IDS = -1;
    public static final int REASON_BACKGROUND_CHANGED = 0;
    public static final int REASON_BACKGROUND_COLOR_CHANGED = 1;
    public static final int REASON_LAYOUT_CHANGED = 2;
    public static final int REASON_SETCONTENTVIEW = 3;
    public static final int REASON_VIEW_INFLATED = 4;
    private static final String TAG = "IDS_TAG";
    private Context mContext;
    private AtomicBoolean mHasUiUpdated = new AtomicBoolean();
    private AtomicBoolean mIdsWindow = new AtomicBoolean();
    private static final int DO_IDS = SystemProperties.getInt("debug.ids.setWindowSize", 10);
    private static boolean sClearData = false;

    public IdsController(Context context) {
        this.mContext = context;
    }

    void openIdsWindow(View view, Choreographer choreographer) {
        if (sClearData) {
            Log.i(TAG, "Clearing training data of " + this.mContext);
            Context context = this.mContext;
            if (context != null) {
                context.deleteSharedPreferences(IDS_URI);
            }
        }
        Log.i(TAG, "Starting IDS observe window");
        registerLayoutListener(view, choreographer);
        setIdsWindowActive(true);
    }

    public void closeIdsWindow() {
        int i;
        try {
            if (isIdsWindowActive()) {
                Log.i(TAG, "Closing IDS observe window");
                setIdsWindowActive(false);
                SharedPreferences idsSharedPreference = getIdsSharedPreference();
                if (idsSharedPreference == null || (i = idsSharedPreference.getInt(IDS_KEY, 0)) >= DO_IDS) {
                    return;
                }
                int i2 = -1;
                if (i != -1) {
                    if (getUiUpdated()) {
                        Log.i(TAG, "IDS disabled for " + this.mContext);
                    } else {
                        i2 = i + 1;
                    }
                    SharedPreferences.Editor editorEdit = idsSharedPreference.edit();
                    editorEdit.putInt(IDS_KEY, i2);
                    editorEdit.apply();
                    Log.i(TAG, "IDS count updated to " + i2 + " for " + this.mContext);
                }
            }
        } catch (Exception unused) {
            Log.i(TAG, "Clearing data while writing to SP of " + this.mContext);
            Context context = this.mContext;
            if (context != null) {
                context.deleteSharedPreferences(IDS_URI);
            }
        }
    }

    public boolean doIds() {
        SharedPreferences idsSharedPreference = getIdsSharedPreference();
        if (idsSharedPreference != null) {
            int i = idsSharedPreference.getInt(IDS_KEY, 0);
            if (i == DO_IDS) {
                Log.i(TAG, "App " + this.mContext + " being boosted by IDS");
                return true;
            }
            if (i == -1) {
                Log.i(TAG, "App " + this.mContext + " in NO_IDS list");
                return false;
            }
        }
        Log.i(TAG, "App " + this.mContext + " has not finished training");
        return false;
    }

    public static void clearTrainingData(boolean z) {
        sClearData = z;
    }

    private SharedPreferences getIdsSharedPreference() {
        try {
            Log.i(TAG, "Getting Shared Preference for " + this.mContext + " uid = " + Process.myUid());
            if (Process.myUid() != 1000) {
                return this.mContext.getSharedPreferences(IDS_URI, 0);
            }
            Log.e(TAG, "System UID: no SharedPreferences here, no IDS");
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void registerLayoutListener(View view, Choreographer choreographer) {
        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: android.app.IdsController.1
            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public void onGlobalLayout() {
                IdsController.this.uiUpdated(2);
            }
        });
    }

    private boolean getUiUpdated() {
        return this.mHasUiUpdated.get();
    }

    public void uiUpdated(int i) {
        if (isIdsWindowActive()) {
            Log.i(TAG, getReasonForUpdate(i) + " updated UI for IDS");
            this.mHasUiUpdated.set(true);
        }
    }

    private String getReasonForUpdate(int i) {
        if (i == 0) {
            return "Background Changed";
        }
        if (i == 1) {
            return "Background Color Changed";
        }
        if (i == 2) {
            return "Layout Changed";
        }
        if (i == 3) {
            return "setContentView";
        }
        if (i == 4) {
            return "View Inflated";
        }
        return "Invalid reason";
    }

    private boolean isIdsWindowActive() {
        return this.mIdsWindow.get();
    }

    private void setIdsWindowActive(boolean z) {
        this.mIdsWindow.set(z);
    }
}
