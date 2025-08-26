package com.android.systemui.media;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Icon;
import android.media.session.PlaybackState;
import android.os.Handler;
import android.os.UserHandle;
import android.util.Log;
import androidx.appcompat.util.SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0;
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import com.android.systemui.Dependency;
import com.android.systemui.statusbar.NotificationLockscreenUserManager;
import com.android.systemui.statusbar.phone.ongoingactivity.MediaOngoingActivityInfo;
import com.android.systemui.statusbar.phone.ongoingactivity.OngoingActivityController;
import com.android.systemui.statusbar.phone.ongoingactivity.OngoingActivityController$dismissMediaRunnable$1;
import com.android.systemui.statusbar.phone.ongoingactivity.OngoingActivityData;
import com.android.systemui.statusbar.phone.ongoingactivity.OngoingActivityDataHelper;
import com.android.systemui.statusbar.phone.ongoingactivity.OngoingCardController;
import com.android.systemui.util.SettingsHelper;
import java.util.Iterator;
import java.util.function.BooleanSupplier;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes2.dex */
public final class OAMusicChipController {
    public Icon appIcon;
    public Integer bgColor;
    public PendingIntent clickIntent;
    public final BooleanSupplier isPlayerOAPlayedSupplier;
    public Boolean isPlaying;
    public final OngoingActivityController ongoingActivityController;
    public String songTitle;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public OAMusicChipController(Context context, OngoingActivityController ongoingActivityController, BooleanSupplier booleanSupplier) {
        this.ongoingActivityController = ongoingActivityController;
        this.isPlayerOAPlayedSupplier = booleanSupplier;
        Log.d("OAMusicChipController", "OA created");
    }

    /* JADX WARN: Removed duplicated region for block: B:43:0x0092  */
    /* JADX WARN: Removed duplicated region for block: B:93:0x018c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void updatePlaybackState(PlaybackState playbackState) {
        Icon icon;
        String str;
        Integer num;
        boolean z;
        boolean z2;
        PendingIntent pendingIntent;
        Intent intent;
        int i = 0;
        if (playbackState != null) {
            this.isPlaying = Boolean.valueOf(playbackState.getState() == 3);
        }
        EmergencyButtonController$$ExternalSyntheticOutline0.m("updateChip Called, isPlayerOAPlayed : ", "OAMusicChipController", this.isPlayerOAPlayedSupplier.getAsBoolean());
        if (!this.isPlayerOAPlayedSupplier.getAsBoolean() || (icon = this.appIcon) == null || (str = this.songTitle) == null || (num = this.bgColor) == null || this.clickIntent == null) {
            return;
        }
        num.getClass();
        int iIntValue = num.intValue();
        PendingIntent pendingIntent2 = this.clickIntent;
        pendingIntent2.getClass();
        Boolean bool = this.isPlaying;
        final OngoingActivityController ongoingActivityController = this.ongoingActivityController;
        if (ongoingActivityController.currentMediaOngoingActivityInfo != null) {
            OngoingActivityDataHelper ongoingActivityDataHelper = OngoingActivityDataHelper.INSTANCE;
            Intent intent2 = pendingIntent2.getIntent();
            ComponentName component = intent2 != null ? intent2.getComponent() : null;
            ongoingActivityDataHelper.getClass();
            OngoingActivityData mediaData = OngoingActivityDataHelper.getMediaData();
            ComponentName component2 = (mediaData == null || (pendingIntent = mediaData.mPendingIntent) == null || (intent = pendingIntent.getIntent()) == null) ? null : intent.getComponent();
            z = !((component2 == null || component == null) ? false : Intrinsics.areEqual(component2.getPackageName(), component.getPackageName()));
        }
        ongoingActivityController.currentMediaOngoingActivityInfo = new MediaOngoingActivityInfo(str, iIntValue, icon, pendingIntent2);
        StringBuilder sbM = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m(" U.M.C.D. - ", str, " ", Integer.toHexString(iIntValue), " ");
        sbM.append(bool);
        Log.d("MediaOngoingActivity", sbM.toString());
        if (bool != null) {
            boolean zBooleanValue = bool.booleanValue();
            boolean z3 = ongoingActivityController.isMediaPlaying;
            Handler handler = ongoingActivityController.mediaPauseTimerHandler;
            OngoingActivityController$dismissMediaRunnable$1 ongoingActivityController$dismissMediaRunnable$1 = ongoingActivityController.dismissMediaRunnable;
            if (z3 && !zBooleanValue) {
                Log.i("MediaOngoingActivity", "dismissMediaTimerRun start. wait 60 sec");
                handler.postDelayed(ongoingActivityController$dismissMediaRunnable$1, 60000L);
            } else if (!z3 && zBooleanValue) {
                if (ongoingActivityController.isMediaVisible) {
                    OngoingActivityDataHelper.INSTANCE.getClass();
                    if (OngoingActivityDataHelper.getMediaData() == null && ((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).isMediaOngoingAllowed()) {
                        NotificationManager notificationManager = (NotificationManager) ongoingActivityController.mContext.getSystemService(NotificationManager.class);
                        Log.i("MediaOngoingActivity", "updateMediaChipData. nm.notifyAsUser");
                        if (notificationManager != null) {
                            notificationManager.notifyAsUser(null, 12030705, ongoingActivityController.getMediaDummyNotification(notificationManager), UserHandle.CURRENT);
                        }
                    }
                }
                Log.i("MediaOngoingActivity", "dismissMediaTimerStop start. dismiss runnable request");
                handler.removeCallbacks(ongoingActivityController$dismissMediaRunnable$1);
            }
            ongoingActivityController.isMediaPlaying = zBooleanValue;
        }
        OngoingActivityDataHelper.INSTANCE.getClass();
        OngoingActivityData mediaData2 = OngoingActivityDataHelper.getMediaData();
        if (mediaData2 == null || !mediaData2.mDismissRequested) {
            ongoingActivityController.updateMediaChipData();
        } else {
            Log.i("{OngoingActivityController}", "Media data mDismissRequested is true. Do not update this media data");
            z = false;
        }
        if (z) {
            OngoingCardController ongoingCardController = ongoingActivityController.mOngoingCardController;
            NotificationLockscreenUserManager notificationLockscreenUserManager = ongoingActivityController.userManager;
            if (ongoingCardController == null) {
                z2 = false;
            } else {
                Iterator it = OngoingActivityDataHelper.mOngoingActivityLists.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    if (((OngoingActivityData) it.next()).mIsMediaOngoingData) {
                        OngoingActivityDataHelper.INSTANCE.getClass();
                        OngoingActivityData mediaData3 = OngoingActivityDataHelper.getMediaData();
                        if (mediaData3 == null || OngoingActivityDataHelper.shouldHide(notificationLockscreenUserManager, mediaData3)) {
                            z2 = true;
                        }
                    }
                }
            }
            if (!z2) {
                OngoingActivityDataHelper.INSTANCE.getClass();
                OngoingActivityDataHelper.updateOngoingList(notificationLockscreenUserManager);
                return;
            }
            OngoingActivityDataHelper.INSTANCE.getClass();
            int size = OngoingActivityDataHelper.mOngoingActivityLists.size();
            if (size == 0) {
                i = -1;
            } else if (size != 1) {
                if (!OngoingActivityDataHelper.getDataByIndex(0).mIsMediaOngoingData) {
                    if (OngoingActivityDataHelper.getDataByIndex(1).mIsMediaOngoingData) {
                        i = 1;
                    }
                }
            } else if (!OngoingActivityDataHelper.getDataByIndex(0).mIsMediaOngoingData) {
            }
            Log.i("{OngoingActivityController}", "removeMediaCardFromCardStack. dataSize:" + size + ", mediaPosition:" + i);
            if (i == 0) {
                OngoingCardController ongoingCardController2 = ongoingActivityController.mOngoingCardController;
                if (ongoingCardController2 != null) {
                    ongoingCardController2.runCardRemoveAnimation(new Function0() { // from class: com.android.systemui.statusbar.phone.ongoingactivity.OngoingActivityController$$ExternalSyntheticLambda6
                        @Override // kotlin.jvm.functions.Function0
                        public final Object invoke() throws Exception {
                            int i2 = OngoingActivityController.$r8$clinit;
                            Log.i("{OngoingActivityController}", "removeMediaCardFromCardStack. animation done.");
                            OngoingActivityDataHelper ongoingActivityDataHelper2 = OngoingActivityDataHelper.INSTANCE;
                            OngoingActivityController ongoingActivityController2 = ongoingActivityController;
                            NotificationLockscreenUserManager notificationLockscreenUserManager2 = ongoingActivityController2.userManager;
                            ongoingActivityDataHelper2.getClass();
                            OngoingActivityDataHelper.updateOngoingList(notificationLockscreenUserManager2);
                            if (OngoingActivityDataHelper.mOngoingActivityLists.size() == 0) {
                                OngoingCardController ongoingCardController3 = ongoingActivityController2.mOngoingCardController;
                                if (ongoingCardController3 != null) {
                                    ongoingCardController3.update$1$1();
                                }
                                OngoingCardController ongoingCardController4 = ongoingActivityController2.mOngoingCardController;
                                if (ongoingCardController4 != null) {
                                    ongoingCardController4.fadeOutCard();
                                }
                            } else {
                                OngoingCardController ongoingCardController5 = ongoingActivityController2.mOngoingCardController;
                                if (ongoingCardController5 != null) {
                                    ongoingCardController5.mCardStackView.removeItem(0);
                                }
                                OngoingCardController ongoingCardController6 = ongoingActivityController2.mOngoingCardController;
                                if (ongoingCardController6 != null) {
                                    ongoingCardController6.update$1$1();
                                }
                            }
                            Log.i("{OngoingActivityController}", "removeMediaCardFromCardStack. animation end callback done.");
                            return Unit.INSTANCE;
                        }
                    });
                    return;
                }
                return;
            }
            if (i != 1) {
                OngoingActivityDataHelper.updateOngoingList(notificationLockscreenUserManager);
                return;
            }
            OngoingActivityDataHelper.updateOngoingList(notificationLockscreenUserManager);
            OngoingCardController ongoingCardController3 = ongoingActivityController.mOngoingCardController;
            if (ongoingCardController3 != null) {
                ongoingCardController3.mCardStackView.removeItem(1);
            }
            OngoingCardController ongoingCardController4 = ongoingActivityController.mOngoingCardController;
            if (ongoingCardController4 != null) {
                ongoingCardController4.update$1$1();
            }
        }
    }
}
