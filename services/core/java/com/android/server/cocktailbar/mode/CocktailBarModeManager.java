package com.android.server.cocktailbar.mode;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Debug;
import android.os.Handler;
import android.os.UserHandle;
import android.util.Log;
import com.android.server.cocktailbar.CocktailBarManagerServiceListener;
import com.android.server.cocktailbar.mode.CocktailBarMode;
import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.knox.SemPersonaManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/* loaded from: classes.dex */
public class CocktailBarModeManager implements CocktailBarMode.OnCocktailBarModeListener {
    public static final boolean DEBUG = Debug.semIsProductDev();
    public static final String TAG = "CocktailBarModeManager";
    public Context mContext;
    public CocktailBarMode mKnoxMode;
    public CocktailBarManagerServiceListener mListener;
    public int mNextPrivateModeId;
    public int mCocktailBarModeId = 1;
    public ArrayList mPrivateModes = new ArrayList();
    public HashMap mPrivateModeMap = new HashMap();
    public CocktailBarMode mNormalMode = new NormalMode();

    public CocktailBarModeManager(Context context, CocktailBarManagerServiceListener cocktailBarManagerServiceListener, BroadcastReceiver broadcastReceiver, Handler handler) {
        this.mNextPrivateModeId = 0;
        this.mContext = context;
        this.mListener = cocktailBarManagerServiceListener;
        this.mKnoxMode = new PrivateKnoxMode(this.mContext, 2, broadcastReceiver, this, this.mListener);
        if (SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_COMMON_SUPPORT_SAFETYCARE")) {
            Context context2 = this.mContext;
            int i = this.mNextPrivateModeId + 1;
            this.mNextPrivateModeId = i;
            setupPrivateMode(new PrivateEmergencyMode(context2, 65536 | i, broadcastReceiver, this));
        }
        setupPrivateMode(this.mKnoxMode);
    }

    public final void setupPrivateMode(CocktailBarMode cocktailBarMode) {
        if (this.mPrivateModeMap.get(cocktailBarMode.getModeName()) == null) {
            this.mPrivateModes.add(cocktailBarMode);
            this.mPrivateModeMap.put(cocktailBarMode.getModeName(), cocktailBarMode);
            return;
        }
        Log.e(TAG, "setupPrivateMode : exist duplicated privateMode : " + cocktailBarMode.getModeName());
    }

    public int refreshCocktailBarMode() {
        int i = this.mCocktailBarModeId;
        Iterator it = this.mPrivateModes.iterator();
        while (it.hasNext()) {
            i = ((CocktailBarMode) it.next()).renewMode(i);
        }
        return i;
    }

    public CocktailBarMode getCocktailBarMode(int i) {
        if (i != 0) {
            if (i == 1) {
                return this.mNormalMode;
            }
            if (i == 2) {
                if (!SemPersonaManager.isKioskModeEnabled(this.mContext)) {
                    return this.mNormalMode;
                }
                return this.mKnoxMode;
            }
            Iterator it = this.mPrivateModes.iterator();
            while (it.hasNext()) {
                CocktailBarMode cocktailBarMode = (CocktailBarMode) it.next();
                if (cocktailBarMode.getModeId() == i) {
                    return cocktailBarMode;
                }
            }
        }
        return null;
    }

    public boolean onBroadcastReceived(Intent intent) {
        Iterator it = this.mPrivateModes.iterator();
        while (it.hasNext()) {
            CocktailBarMode cocktailBarMode = (CocktailBarMode) it.next();
            if (cocktailBarMode.getRegistrationType() == 1) {
                int onBroadcastReceived = cocktailBarMode.onBroadcastReceived(intent);
                if (onBroadcastReceived != 1) {
                    if (onBroadcastReceived == 2) {
                        onSetMode(UserHandle.getCallingUserId(), cocktailBarMode);
                    } else if (onBroadcastReceived == 3) {
                        onResetMode(UserHandle.getCallingUserId(), cocktailBarMode);
                        return true;
                    }
                }
                return true;
            }
        }
        return false;
    }

    public void setMode(int i, int i2) {
        onSetMode(i, getCocktailBarMode(i2));
    }

    public int getCurrentModeId() {
        return this.mCocktailBarModeId;
    }

    public void onSetMode(int i, CocktailBarMode cocktailBarMode) {
        if (cocktailBarMode == null || this.mCocktailBarModeId == cocktailBarMode.getModeId()) {
            return;
        }
        CocktailBarMode cocktailBarMode2 = getCocktailBarMode(this.mCocktailBarModeId);
        if (cocktailBarMode2 != null) {
            this.mListener.onUnsetMode(i, this.mCocktailBarModeId, cocktailBarMode2.getModeName());
        }
        this.mListener.onSetMode(i, cocktailBarMode.getModeId(), cocktailBarMode.getModeName(), cocktailBarMode.getCocktailType());
        this.mCocktailBarModeId = cocktailBarMode.getModeId();
        if (DEBUG) {
            StringBuffer stringBuffer = new StringBuffer("onSetMode: ");
            stringBuffer.append(cocktailBarMode2 != null ? cocktailBarMode2.getModeName() : " no-current");
            stringBuffer.append(" -> ");
            stringBuffer.append(cocktailBarMode.getModeName());
            Log.d(TAG, stringBuffer.toString());
        }
    }

    public void onResetMode(int i, CocktailBarMode cocktailBarMode) {
        if (cocktailBarMode == null || this.mCocktailBarModeId != cocktailBarMode.getModeId()) {
            return;
        }
        this.mListener.onResetMode(i, cocktailBarMode.getModeId(), cocktailBarMode.getModeName());
        this.mCocktailBarModeId = 1;
        if (DEBUG) {
            String str = TAG;
            StringBuffer stringBuffer = new StringBuffer(str);
            stringBuffer.append("onResetMode: ");
            stringBuffer.append(cocktailBarMode.getModeName());
            Log.d(str, stringBuffer.toString());
        }
    }

    public void onSetModeForcely(int i, CocktailBarMode cocktailBarMode) {
        if (cocktailBarMode != null) {
            CocktailBarMode cocktailBarMode2 = getCocktailBarMode(this.mCocktailBarModeId);
            if (cocktailBarMode2 != null) {
                this.mListener.onUnsetMode(i, this.mCocktailBarModeId, cocktailBarMode2.getModeName());
            }
            this.mListener.onSetMode(i, cocktailBarMode.getModeId(), cocktailBarMode.getModeName(), cocktailBarMode.getCocktailType());
            this.mCocktailBarModeId = cocktailBarMode.getModeId();
            if (DEBUG) {
                String str = TAG;
                StringBuffer stringBuffer = new StringBuffer(str);
                stringBuffer.append("onSetModeForcely: ");
                stringBuffer.append(cocktailBarMode2 != null ? cocktailBarMode2.getModeName() : " no-current");
                stringBuffer.append(" -> ");
                stringBuffer.append(cocktailBarMode.getModeName());
                Log.d(str, stringBuffer.toString());
            }
        }
    }
}
