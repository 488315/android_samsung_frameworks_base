package com.android.systemui.statusbar.phone;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import androidx.lifecycle.Observer;
import com.android.internal.statusbar.LetterboxDetails;
import com.android.internal.view.AppearanceRegion;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.notification.collection.NotifLiveDataImpl;
import com.android.systemui.statusbar.notification.collection.NotifLiveDataStore;
import com.android.systemui.statusbar.notification.collection.NotifLiveDataStoreImpl;
import com.android.systemui.util.ListenerSet;
import com.android.systemui.util.ViewController;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class LightsOutNotifController extends ViewController {
    int mAppearance;
    public final C30722 mCallback;
    public final CommandQueue mCommandQueue;
    public int mDisplayId;
    public final NotifLiveDataStore mNotifDataStore;
    public final LightsOutNotifController$$ExternalSyntheticLambda0 mObserver;
    public final WindowManager mWindowManager;

    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.statusbar.phone.LightsOutNotifController$$ExternalSyntheticLambda0] */
    /* JADX WARN: Type inference failed for: r1v2, types: [com.android.systemui.statusbar.phone.LightsOutNotifController$2] */
    public LightsOutNotifController(View view, WindowManager windowManager, NotifLiveDataStore notifLiveDataStore, CommandQueue commandQueue) {
        super(view);
        this.mObserver = new Observer() { // from class: com.android.systemui.statusbar.phone.LightsOutNotifController$$ExternalSyntheticLambda0
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                LightsOutNotifController.this.updateLightsOutView();
            }
        };
        this.mCallback = new CommandQueue.Callbacks() { // from class: com.android.systemui.statusbar.phone.LightsOutNotifController.2
            @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
            public final void onSystemBarAttributesChanged(int i, int i2, AppearanceRegion[] appearanceRegionArr, boolean z, int i3, int i4, String str, LetterboxDetails[] letterboxDetailsArr) {
                LightsOutNotifController lightsOutNotifController = LightsOutNotifController.this;
                if (i != lightsOutNotifController.mDisplayId) {
                    return;
                }
                lightsOutNotifController.mAppearance = i2;
                lightsOutNotifController.updateLightsOutView();
            }
        };
        this.mWindowManager = windowManager;
        this.mNotifDataStore = notifLiveDataStore;
        this.mCommandQueue = commandQueue;
    }

    public boolean areLightsOut() {
        return (this.mAppearance & 4) != 0;
    }

    public boolean isShowingDot() {
        return this.mView.getVisibility() == 0 && this.mView.getAlpha() == 1.0f;
    }

    @Override // com.android.systemui.util.ViewController
    public final void onViewAttached() {
        this.mView.setVisibility(8);
        this.mView.setAlpha(0.0f);
        this.mDisplayId = this.mWindowManager.getDefaultDisplay().getDisplayId();
        NotifLiveDataImpl notifLiveDataImpl = ((NotifLiveDataStoreImpl) this.mNotifDataStore).hasActiveNotifs;
        notifLiveDataImpl.syncObservers.addIfAbsent(this.mObserver);
        this.mCommandQueue.addCallback((CommandQueue.Callbacks) this.mCallback);
        updateLightsOutView();
    }

    @Override // com.android.systemui.util.ViewController
    public final void onViewDetached() {
        NotifLiveDataImpl notifLiveDataImpl = ((NotifLiveDataStoreImpl) this.mNotifDataStore).hasActiveNotifs;
        ListenerSet listenerSet = notifLiveDataImpl.syncObservers;
        LightsOutNotifController$$ExternalSyntheticLambda0 lightsOutNotifController$$ExternalSyntheticLambda0 = this.mObserver;
        listenerSet.remove(lightsOutNotifController$$ExternalSyntheticLambda0);
        notifLiveDataImpl.asyncObservers.remove(lightsOutNotifController$$ExternalSyntheticLambda0);
        this.mCommandQueue.removeCallback((CommandQueue.Callbacks) this.mCallback);
    }

    public boolean shouldShowDot() {
        return ((Boolean) ((NotifLiveDataStoreImpl) this.mNotifDataStore).hasActiveNotifs.getValue()).booleanValue() && areLightsOut();
    }

    public void updateLightsOutView() {
        final boolean shouldShowDot = shouldShowDot();
        if (shouldShowDot != isShowingDot()) {
            if (shouldShowDot) {
                this.mView.setAlpha(0.0f);
                this.mView.setVisibility(0);
            }
            this.mView.animate().alpha(shouldShowDot ? 1.0f : 0.0f).setDuration(shouldShowDot ? 750L : 250L).setInterpolator(new AccelerateInterpolator(2.0f)).setListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.statusbar.phone.LightsOutNotifController.1
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationEnd(Animator animator) {
                    LightsOutNotifController.this.mView.setAlpha(shouldShowDot ? 1.0f : 0.0f);
                    LightsOutNotifController.this.mView.setVisibility(shouldShowDot ? 0 : 8);
                    LightsOutNotifController.this.mView.animate().setListener(null);
                }
            }).start();
        }
    }
}
