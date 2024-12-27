package com.android.systemui;

import android.content.Context;
import android.widget.TextView;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.shade.domain.interactor.ShadeInteractor;
import com.android.systemui.shade.domain.interactor.ShadeInteractorImpl;
import com.android.systemui.statusbar.NotificationShelf;
import com.android.systemui.statusbar.NotificationShelfManager;
import com.android.systemui.statusbar.notification.row.ExpandableView;
import com.android.systemui.statusbar.notification.stack.AmbientState;
import com.samsung.android.widget.SemTipPopup;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function4;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class ShelfToolTipManager {
    public boolean alreadyToolTipShown;
    public boolean isTappedNotiSettings;
    public final AmbientState mAmbientState;
    public final Context mContext;
    public boolean mFirstExpanded;
    public final TextView mNotiSettingButton;
    public SemTipPopup mNotiSettingTip;
    public final NotificationShelf mNotificationShelf;
    public int mOrientation;
    public int mStatusBarState;
    public final StatusBarStateController mStatusBarStateController;
    public final ShelfToolTipManager$stateListener$1 stateListener;
    public final int THRESHOLD_COUNT = 200;
    public int panelExpandedCount = -1;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    /* renamed from: com.android.systemui.ShelfToolTipManager$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function4 {
        /* synthetic */ boolean Z$0;
        /* synthetic */ boolean Z$1;
        /* synthetic */ boolean Z$2;
        int label;

        public AnonymousClass1(Continuation continuation) {
            super(4, continuation);
        }

        @Override // kotlin.jvm.functions.Function4
        public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4) {
            boolean booleanValue = ((Boolean) obj).booleanValue();
            boolean booleanValue2 = ((Boolean) obj2).booleanValue();
            boolean booleanValue3 = ((Boolean) obj3).booleanValue();
            AnonymousClass1 anonymousClass1 = ShelfToolTipManager.this.new AnonymousClass1((Continuation) obj4);
            anonymousClass1.Z$0 = booleanValue;
            anonymousClass1.Z$1 = booleanValue2;
            anonymousClass1.Z$2 = booleanValue3;
            return anonymousClass1.invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            boolean z = this.Z$0;
            boolean z2 = this.Z$1;
            boolean z3 = this.Z$2;
            if (z) {
                ShelfToolTipManager shelfToolTipManager = ShelfToolTipManager.this;
                if (!shelfToolTipManager.mFirstExpanded && z2) {
                    shelfToolTipManager.mFirstExpanded = true;
                    if (shelfToolTipManager.mStatusBarState == 0) {
                        int i = shelfToolTipManager.panelExpandedCount;
                        if (i < shelfToolTipManager.THRESHOLD_COUNT) {
                            shelfToolTipManager.panelExpandedCount = i + 1;
                        }
                        if (shelfToolTipManager.mNotiSettingTip == null && shelfToolTipManager.needsToShow()) {
                            shelfToolTipManager.mOrientation = shelfToolTipManager.mContext.getResources().getConfiguration().orientation;
                            shelfToolTipManager.getToolTip();
                            SemTipPopup semTipPopup = shelfToolTipManager.mNotiSettingTip;
                            if (semTipPopup != null) {
                                semTipPopup.show(1);
                            }
                        }
                    }
                } else if ((!z2 || z3) && shelfToolTipManager.mNotiSettingTip != null) {
                    shelfToolTipManager.releaseToolTip(true);
                }
            } else {
                ShelfToolTipManager.this.mFirstExpanded = false;
            }
            return Unit.INSTANCE;
        }
    }

    public ShelfToolTipManager(Context context, NotificationShelfManager notificationShelfManager, AmbientState ambientState, StatusBarStateController statusBarStateController, ShadeInteractor shadeInteractor, CoroutineScope coroutineScope) {
        this.mContext = context;
        this.mAmbientState = ambientState;
        this.mStatusBarStateController = statusBarStateController;
        this.mStatusBarState = 1;
        StatusBarStateController.StateListener stateListener = new StatusBarStateController.StateListener() { // from class: com.android.systemui.ShelfToolTipManager$stateListener$1
            @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
            public final void onStateChanged(int i) {
                ShelfToolTipManager shelfToolTipManager = ShelfToolTipManager.this;
                if (shelfToolTipManager.mStatusBarState != i) {
                    shelfToolTipManager.mStatusBarState = i;
                }
                if (i != 1 || shelfToolTipManager.mNotiSettingTip == null) {
                    return;
                }
                shelfToolTipManager.releaseToolTip(true);
            }
        };
        releaseToolTip(true);
        this.alreadyToolTipShown = Prefs.getBoolean(context, "NotificationSettingsToolTipShown", false);
        NotificationShelf notificationShelf = notificationShelfManager.shelf;
        this.mNotificationShelf = notificationShelf;
        this.mNotiSettingButton = notificationShelf != null ? (TextView) notificationShelf.findViewById(R.id.noti_setting) : null;
        this.mStatusBarState = statusBarStateController.getState();
        statusBarStateController.removeCallback(stateListener);
        statusBarStateController.addCallback(stateListener);
        ShadeInteractorImpl shadeInteractorImpl = (ShadeInteractorImpl) shadeInteractor;
        FlowKt.launchIn(FlowKt.distinctUntilChanged(FlowKt.combine(shadeInteractorImpl.baseShadeInteractor.isAnyExpanded(), shadeInteractorImpl.isShadeFullyExpanded, shadeInteractorImpl.baseShadeInteractor.isQsExpanded(), new AnonymousClass1(null))), coroutineScope);
    }

    public final void getToolTip() {
        TextView textView = this.mNotiSettingButton;
        SemTipPopup semTipPopup = textView != null ? new SemTipPopup(textView, 1) : null;
        this.mNotiSettingTip = semTipPopup;
        if (semTipPopup != null) {
            semTipPopup.setMessage(this.mContext.getString(R.string.noti_setting_tooltip_text));
        }
        SemTipPopup semTipPopup2 = this.mNotiSettingTip;
        if (semTipPopup2 != null) {
            semTipPopup2.setBorderColor(this.mContext.getResources().getColor(R.color.notification_shelf_tooltip_border_color));
        }
        SemTipPopup semTipPopup3 = this.mNotiSettingTip;
        if (semTipPopup3 != null) {
            semTipPopup3.setBackgroundColorWithAlpha(this.mContext.getResources().getColor(R.color.notification_shelf_tooltip_background_color));
        }
        SemTipPopup semTipPopup4 = this.mNotiSettingTip;
        if (semTipPopup4 != null) {
            semTipPopup4.setExpanded(true);
        }
        SemTipPopup semTipPopup5 = this.mNotiSettingTip;
        if (semTipPopup5 != null) {
            semTipPopup5.setOutsideTouchEnabled(false);
        }
        SemTipPopup semTipPopup6 = this.mNotiSettingTip;
        if (semTipPopup6 != null) {
            semTipPopup6.setOnStateChangeListener(new SemTipPopup.OnStateChangeListener() { // from class: com.android.systemui.ShelfToolTipManager$getToolTip$2
                public final void onStateChanged(int i) {
                    if (i == 0) {
                        ShelfToolTipManager shelfToolTipManager = ShelfToolTipManager.this;
                        if (shelfToolTipManager.mOrientation == shelfToolTipManager.mContext.getResources().getConfiguration().orientation) {
                            Prefs.putBoolean(ShelfToolTipManager.this.mContext, "NotificationSettingsToolTipShown", true);
                            ShelfToolTipManager shelfToolTipManager2 = ShelfToolTipManager.this;
                            shelfToolTipManager2.alreadyToolTipShown = true;
                            shelfToolTipManager2.mNotiSettingTip = null;
                        }
                    }
                }
            });
        }
    }

    public final boolean needsToShow() {
        NotificationShelf notificationShelf;
        AmbientState ambientState;
        ExpandableView expandableView;
        if (!this.alreadyToolTipShown && !this.isTappedNotiSettings && this.panelExpandedCount == this.THRESHOLD_COUNT && (notificationShelf = this.mNotificationShelf) != null && (expandableView = (ambientState = this.mAmbientState).mLastVisibleBackgroundChild) != null) {
            if (ambientState.mLastVisibleBackgroundChild.getIntrinsicHeight() + ((int) expandableView.getTranslationY()) >= ((int) notificationShelf.getTranslationY())) {
                return true;
            }
        }
        return false;
    }

    public final void releaseToolTip(boolean z) {
        SemTipPopup semTipPopup = this.mNotiSettingTip;
        if (semTipPopup != null) {
            semTipPopup.dismiss(z);
        }
    }
}
