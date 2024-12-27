package com.android.systemui.qp;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.android.systemui.QpRune;
import com.android.systemui.R;
import com.android.systemui.plugins.subscreen.SubRoom;
import com.android.systemui.qs.InjectionInflationController;

public final class SubscreenSubRoomQuickSettings implements SubRoom {
    public static Context mContext;
    public static InjectionInflationController mInjectionInflater;
    public static SubscreenSubRoomQuickSettings sInstance;
    public final View mMainView;
    public SubRoom.StateChangeListener mStateChangeListener;

    private SubscreenSubRoomQuickSettings() {
        if (QpRune.QUICK_SUBSCREEN_PANEL) {
            InjectionInflationController injectionInflationController = mInjectionInflater;
            LayoutInflater from = LayoutInflater.from(mContext);
            injectionInflationController.getClass();
            LayoutInflater cloneInContext = from.cloneInContext(from.getContext());
            cloneInContext.setPrivateFactory(injectionInflationController.mFactory);
            this.mMainView = cloneInContext.inflate(R.layout.subscreen_quick_settings_qp_base, (ViewGroup) null, false);
            return;
        }
        if (QpRune.QUICK_SUBSCREEN_SETTINGS) {
            InjectionInflationController injectionInflationController2 = mInjectionInflater;
            LayoutInflater from2 = LayoutInflater.from(mContext);
            injectionInflationController2.getClass();
            LayoutInflater cloneInContext2 = from2.cloneInContext(from2.getContext());
            cloneInContext2.setPrivateFactory(injectionInflationController2.mFactory);
            this.mMainView = cloneInContext2.inflate(R.layout.subscreen_quick_settings_base, (ViewGroup) null, false);
        }
    }

    public static SubscreenSubRoomQuickSettings getInstance(Context context, InjectionInflationController injectionInflationController) {
        if (sInstance == null) {
            mContext = context;
            mInjectionInflater = injectionInflationController;
            sInstance = new SubscreenSubRoomQuickSettings();
        }
        Log.d("SubscreenSubRoomQuickPanel", "getInstance()");
        return sInstance;
    }

    @Override // com.android.systemui.plugins.subscreen.SubRoom
    public final View getView(Context context) {
        return this.mMainView;
    }

    @Override // com.android.systemui.plugins.subscreen.SubRoom
    public final void onCloseFinished() {
        Log.d("SubscreenSubRoomQuickPanel", "SSRQS onCloseFinished ");
    }

    @Override // com.android.systemui.plugins.subscreen.SubRoom
    public final void onCloseStarted() {
        Log.d("SubscreenSubRoomQuickPanel", "SSRQS onCloseStarted ");
    }

    @Override // com.android.systemui.plugins.subscreen.SubRoom
    public final void onOpenFinished() {
        Log.d("SubscreenSubRoomQuickPanel", "SSRQS onOpenFinished ");
    }

    @Override // com.android.systemui.plugins.subscreen.SubRoom
    public final void onOpenStarted() {
        Log.d("SubscreenSubRoomQuickPanel", "SSRQS onOpenStarted ");
    }

    @Override // com.android.systemui.plugins.subscreen.SubRoom
    public final Bundle request(String str, Bundle bundle) {
        return null;
    }

    @Override // com.android.systemui.plugins.subscreen.SubRoom
    public final void setListener(SubRoom.StateChangeListener stateChangeListener) {
        this.mStateChangeListener = stateChangeListener;
    }

    @Override // com.android.systemui.plugins.subscreen.SubRoom
    public final void removeListener() {
    }
}
