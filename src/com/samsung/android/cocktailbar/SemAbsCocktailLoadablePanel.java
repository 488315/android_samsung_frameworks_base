package com.samsung.android.cocktailbar;

import android.app.PendingIntent;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

/* loaded from: classes6.dex */
public abstract class SemAbsCocktailLoadablePanel {
    public static final String EXTRA_CONFIGURATION_KEY_POSITION = "cocktail_position";
    public static final int POSITION_ON_BOTTOM = 3;
    public static final int POSITION_ON_LEFT = 0;
    public static final int POSITION_ON_RIGHT = 1;
    public static final int POSITION_ON_TOP = 2;
    private static final String TAG = "SemAbsCocktailLoadablePanel";
    protected Context mCocktailContext;
    protected Context mContext;
    private CocktailLoadablePanelListener mListener;
    private OnCocktailClickHandler mOnCocktailClickHandler;

    public interface CocktailLoadablePanelListener {
        void sendOptions(Bundle bundle);
    }

    public interface OnCocktailClickHandler {
        boolean onClickHandler(View view, PendingIntent pendingIntent);
    }

    public abstract View getView();

    public void onConfigurationChanged(Configuration configuration, Bundle bundle) {
    }

    public abstract void onCreate();

    public abstract void onDestroy();

    public void onPause() {
    }

    public void onPostResume() {
    }

    public void onReceiveContentInfo(Bundle bundle) {
    }

    public void onResume() {
    }

    public SemAbsCocktailLoadablePanel(Context context) {
        this.mCocktailContext = null;
        this.mListener = null;
        this.mOnCocktailClickHandler = null;
        this.mContext = context;
    }

    public SemAbsCocktailLoadablePanel(Context context, Context context2) {
        this.mListener = null;
        this.mOnCocktailClickHandler = null;
        this.mContext = context;
        this.mCocktailContext = context2;
    }

    public void setListener(CocktailLoadablePanelListener cocktailLoadablePanelListener) {
        this.mListener = cocktailLoadablePanelListener;
    }

    public void setOnCocktailClickHander(OnCocktailClickHandler onCocktailClickHandler) {
        this.mOnCocktailClickHandler = onCocktailClickHandler;
    }

    public boolean performOnClickInCocktailBar(View view, PendingIntent pendingIntent) {
        OnCocktailClickHandler onCocktailClickHandler = this.mOnCocktailClickHandler;
        if (onCocktailClickHandler != null) {
            return onCocktailClickHandler.onClickHandler(view, pendingIntent);
        }
        Log.e(TAG, "CocktailClickHandler was not set yet");
        return false;
    }

    public void requestCocktailBarOpen() {
        if (this.mListener != null) {
            Bundle bundle = new Bundle();
            bundle.putBoolean("open_panel", true);
            this.mListener.sendOptions(bundle);
            return;
        }
        Log.e(TAG, "CocktailLoadablePanelListener was not set yet");
    }
}
