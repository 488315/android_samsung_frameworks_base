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

/* loaded from: classes2.dex */
public class SubscreenSubRoomQuickSettings implements SubRoom {
    public static Context mContext;
    public static InjectionInflationController mInjectionInflater;
    public static SubscreenSubRoomQuickSettings sInstance;
    public final View mMainView;
    public SubRoom.StateChangeListener mStateChangeListener;

    private SubscreenSubRoomQuickSettings() {
        if (QpRune.QUICK_SUBSCREEN_PANEL) {
            InjectionInflationController injectionInflationController = mInjectionInflater;
            LayoutInflater layoutInflaterFrom = LayoutInflater.from(mContext);
            injectionInflationController.getClass();
            LayoutInflater layoutInflaterCloneInContext = layoutInflaterFrom.cloneInContext(layoutInflaterFrom.getContext());
            layoutInflaterCloneInContext.setPrivateFactory(injectionInflationController.mFactory);
            this.mMainView = layoutInflaterCloneInContext.inflate(R.layout.subscreen_quick_settings_qp_base, (ViewGroup) null, false);
            return;
        }
        if (QpRune.QUICK_SUBSCREEN_SETTINGS) {
            InjectionInflationController injectionInflationController2 = mInjectionInflater;
            LayoutInflater layoutInflaterFrom2 = LayoutInflater.from(mContext);
            injectionInflationController2.getClass();
            LayoutInflater layoutInflaterCloneInContext2 = layoutInflaterFrom2.cloneInContext(layoutInflaterFrom2.getContext());
            layoutInflaterCloneInContext2.setPrivateFactory(injectionInflationController2.mFactory);
            this.mMainView = layoutInflaterCloneInContext2.inflate(R.layout.subscreen_quick_settings_base, (ViewGroup) null, false);
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
