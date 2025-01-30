package com.android.systemui.p016qs.tiles;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import com.android.systemui.Dependency;
import com.android.systemui.knox.EdmMonitor;
import com.android.systemui.knox.KnoxStateMonitor;
import com.android.systemui.knox.KnoxStateMonitorImpl;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* renamed from: com.android.systemui.qs.tiles.BlueLightFilterTile$BlueLightFilterDetailAdapter$$ExternalSyntheticLambda0 */
/* loaded from: classes2.dex */
public final /* synthetic */ class ViewOnTouchListenerC2210x668081db implements View.OnTouchListener {
    @Override // android.view.View.OnTouchListener
    public final boolean onTouch(View view, MotionEvent motionEvent) {
        EdmMonitor edmMonitor = ((KnoxStateMonitorImpl) ((KnoxStateMonitor) Dependency.get(KnoxStateMonitor.class))).mEdmMonitor;
        if (edmMonitor != null) {
            Context context = edmMonitor.knoxStateMonitor.mContext;
            if (!edmMonitor.mSettingsChangesAllowed) {
                return true;
            }
        }
        return false;
    }
}
