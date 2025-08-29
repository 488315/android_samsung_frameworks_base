package com.android.systemui.statusbar.policy;

import android.app.ActivityOptions;
import android.app.ActivityTaskManager;
import android.app.INotificationManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.RemoteInput;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.graphics.drawable.AdaptiveIconDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.Icon;
import android.os.RemoteException;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.text.TextUtils;
import android.util.ArraySet;
import android.util.Log;
import android.util.Pair;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Button;
import com.android.systemui.NotiRune;
import com.android.systemui.R;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.shared.system.ActivityManagerWrapper;
import com.android.systemui.shared.system.DevicePolicyManagerWrapper;
import com.android.systemui.shared.system.PackageManagerWrapper;
import com.android.systemui.statusbar.NotificationRemoteInputManager;
import com.android.systemui.statusbar.SmartReplyController;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.logging.NotificationLogger;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.phone.ExpandHeadsUpOnInlineReply;
import com.android.systemui.statusbar.policy.InflatedSmartReplyState;
import com.android.systemui.statusbar.policy.SmartReplyView;
import com.android.systemui.util.SystemUIAnalytics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import kotlin.Result;
import kotlin.Unit;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.collections.CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1;
import kotlin.collections.EmptyList;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.EmptySequence;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt__SequencesKt;
import kotlin.sequences.SequencesKt___SequencesKt;
import kotlin.sequences.TransformingIndexedSequence;

/* loaded from: classes3.dex */
public final class SmartReplyStateInflaterImpl implements SmartReplyStateInflater {
    public final ActivityManagerWrapper activityManagerWrapper;
    public final SmartReplyConstants constants;
    public final DevicePolicyManagerWrapper devicePolicyManagerWrapper;
    public final PackageManagerWrapper packageManagerWrapper;
    public final SmartActionInflater smartActionsInflater;
    public final SmartReplyInflater smartRepliesInflater;

    public SmartReplyStateInflaterImpl(SmartReplyConstants smartReplyConstants, ActivityManagerWrapper activityManagerWrapper, PackageManagerWrapper packageManagerWrapper, DevicePolicyManagerWrapper devicePolicyManagerWrapper, SmartReplyInflater smartReplyInflater, SmartActionInflater smartActionInflater) {
        this.constants = smartReplyConstants;
        this.activityManagerWrapper = activityManagerWrapper;
        this.packageManagerWrapper = packageManagerWrapper;
        this.devicePolicyManagerWrapper = devicePolicyManagerWrapper;
        this.smartRepliesInflater = smartReplyInflater;
        this.smartActionsInflater = smartActionInflater;
    }

    /* JADX WARN: Removed duplicated region for block: B:30:0x0077  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x0119  */
    /* JADX WARN: Removed duplicated region for block: B:86:0x0161  */
    /* JADX WARN: Removed duplicated region for block: B:99:0x0185  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final InflatedSmartReplyState inflateSmartReplyState(NotificationEntry notificationEntry) {
        SmartReplyView.SmartReplies smartReplies;
        boolean z;
        boolean z2;
        List list;
        boolean zIsLockTaskPermitted;
        Intent intent;
        PendingIntent pendingIntent;
        PendingIntent pendingIntent2;
        CharSequence[] choices;
        Notification notification2 = notificationEntry.mSbn.getNotification();
        Pair<RemoteInput, Notification.Action> pairFindRemoteInputActionPair = notification2.findRemoteInputActionPair(false);
        Pair<RemoteInput, Notification.Action> pairFindRemoteInputActionPair2 = notification2.findRemoteInputActionPair(true);
        InflatedSmartReplyState.SuppressedActions suppressedActions = null;
        if (!this.constants.mEnabled) {
            if (SmartReplyStateInflaterKt.DEBUG) {
                MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("Smart suggestions not enabled, not adding suggestions for ", notificationEntry.mSbn.getKey(), "SmartReplyViewInflater");
            }
            return new InflatedSmartReplyState(null, null, null, false);
        }
        boolean z3 = !this.constants.mRequiresTargetingP || notificationEntry.targetSdk >= 28;
        List<Notification.Action> contextualActions = notification2.getContextualActions();
        if (!z3 || pairFindRemoteInputActionPair == null || (pendingIntent2 = ((Notification.Action) pairFindRemoteInputActionPair.second).actionIntent) == null || (choices = ((RemoteInput) pairFindRemoteInputActionPair.first).getChoices()) == null) {
            smartReplies = null;
        } else if (!(choices.length == 0)) {
            smartReplies = new SmartReplyView.SmartReplies(Arrays.asList(((RemoteInput) pairFindRemoteInputActionPair.first).getChoices()), (RemoteInput) pairFindRemoteInputActionPair.first, pendingIntent2, false);
        }
        SmartReplyView.SmartActions smartActions = !contextualActions.isEmpty() ? new SmartReplyView.SmartActions(contextualActions, false) : null;
        if (smartReplies == null && smartActions == null) {
            List<CharSequence> smartReplies2 = notificationEntry.mRanking.getSmartReplies();
            List<Notification.Action> smartActions2 = notificationEntry.mRanking.getSmartActions();
            if (!smartReplies2.isEmpty() && pairFindRemoteInputActionPair2 != null && ((Notification.Action) pairFindRemoteInputActionPair2.second).getAllowGeneratedReplies() && (pendingIntent = ((Notification.Action) pairFindRemoteInputActionPair2.second).actionIntent) != null) {
                smartReplies = new SmartReplyView.SmartReplies(smartReplies2, (RemoteInput) pairFindRemoteInputActionPair2.first, pendingIntent, true);
            }
            if (!smartActions2.isEmpty() && notification2.getAllowSystemGeneratedContextualActions()) {
                this.activityManagerWrapper.getClass();
                try {
                    if (ActivityTaskManager.getService().getLockTaskModeState() == 1) {
                        ArrayList arrayList = new ArrayList();
                        for (Object obj : smartActions2) {
                            PendingIntent pendingIntent3 = ((Notification.Action) obj).actionIntent;
                            if (pendingIntent3 == null || (intent = pendingIntent3.getIntent()) == null) {
                                zIsLockTaskPermitted = false;
                            } else {
                                this.packageManagerWrapper.getClass();
                                ResolveInfo resolveInfoResolveActivity = PackageManagerWrapper.resolveActivity(intent);
                                if (resolveInfoResolveActivity != null) {
                                    String str = resolveInfoResolveActivity.activityInfo.packageName;
                                    this.devicePolicyManagerWrapper.getClass();
                                    zIsLockTaskPermitted = DevicePolicyManagerWrapper.sDevicePolicyManager.isLockTaskPermitted(str);
                                }
                            }
                            if (zIsLockTaskPermitted) {
                                arrayList.add(obj);
                            }
                        }
                        smartActions2 = arrayList;
                    }
                } catch (RemoteException unused) {
                }
                smartActions = new SmartReplyView.SmartActions(smartActions2, true);
            }
        }
        if (smartActions == null || (list = smartActions.actions) == null) {
            z = false;
        } else {
            List<Notification.Action> list2 = list;
            if (!(list2 instanceof Collection) || !list2.isEmpty()) {
                for (Notification.Action action : list2) {
                    if (action.isContextual() && action.getSemanticAction() == 12) {
                        z = true;
                        break;
                    }
                }
                z = false;
            }
        }
        if (z) {
            Notification.Action[] actionArr = notification2.actions;
            ArrayList arrayList2 = new ArrayList();
            int length = actionArr.length;
            int i = 0;
            int i2 = 0;
            while (i < length) {
                int i3 = i2 + 1;
                RemoteInput[] remoteInputs = actionArr[i].getRemoteInputs();
                if (remoteInputs == null) {
                    z2 = false;
                } else if (!(remoteInputs.length == 0)) {
                    z2 = true;
                }
                Integer numValueOf = z2 ? Integer.valueOf(i2) : null;
                if (numValueOf != null) {
                    arrayList2.add(numValueOf);
                }
                i++;
                i2 = i3;
            }
            suppressedActions = new InflatedSmartReplyState.SuppressedActions(arrayList2);
        }
        return new InflatedSmartReplyState(smartReplies, smartActions, suppressedActions, z);
    }

    /* JADX WARN: Removed duplicated region for block: B:110:0x0124 A[LOOP:0: B:58:0x0087->B:110:0x0124, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:128:0x0083 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0017  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final InflatedSmartReplyViewHolder inflateSmartReplyViewHolder(Context context, Context context2, final NotificationEntry notificationEntry, InflatedSmartReplyState inflatedSmartReplyState, InflatedSmartReplyState inflatedSmartReplyState2) {
        Object obj;
        Object obj2;
        Object obj3;
        Object obj4;
        List list;
        List list2;
        boolean z;
        boolean z2;
        boolean z3;
        Sequence transformingIndexedSequence;
        if (!SmartReplyStateInflaterKt.shouldShowSmartReplyView(notificationEntry, inflatedSmartReplyState2)) {
            return new InflatedSmartReplyViewHolder(null, null);
        }
        if (inflatedSmartReplyState != inflatedSmartReplyState2) {
            if (inflatedSmartReplyState != null && inflatedSmartReplyState.hasPhishingAction == inflatedSmartReplyState2.hasPhishingAction) {
                SmartReplyView.SmartReplies smartReplies = inflatedSmartReplyState.smartReplies;
                if (smartReplies == null || (obj = smartReplies.choices) == null) {
                    obj = EmptyList.INSTANCE;
                }
                SmartReplyView.SmartReplies smartReplies2 = inflatedSmartReplyState2.smartReplies;
                if (smartReplies2 == null || (obj2 = smartReplies2.choices) == null) {
                    obj2 = EmptyList.INSTANCE;
                }
                if (Intrinsics.areEqual(obj, obj2)) {
                    InflatedSmartReplyState.SuppressedActions suppressedActions = inflatedSmartReplyState.suppressedActions;
                    if (suppressedActions == null || (obj3 = suppressedActions.suppressedActionIndices) == null) {
                        obj3 = EmptyList.INSTANCE;
                    }
                    InflatedSmartReplyState.SuppressedActions suppressedActions2 = inflatedSmartReplyState2.suppressedActions;
                    if (suppressedActions2 == null || (obj4 = suppressedActions2.suppressedActionIndices) == null) {
                        obj4 = EmptyList.INSTANCE;
                    }
                    if (Intrinsics.areEqual(obj3, obj4)) {
                        SmartReplyView.SmartActions smartActions = inflatedSmartReplyState.smartActions;
                        if (smartActions == null || (list = smartActions.actions) == null) {
                            list = EmptyList.INSTANCE;
                        }
                        SmartReplyView.SmartActions smartActions2 = inflatedSmartReplyState2.smartActions;
                        if (smartActions2 == null || (list2 = smartActions2.actions) == null) {
                            list2 = EmptyList.INSTANCE;
                        }
                        if (list != list2) {
                            if (list != null && list2 != null && list.size() == list2.size()) {
                                for (int i = 0; i < list.size(); i++) {
                                    Notification.Action action = (Notification.Action) list.get(i);
                                    Notification.Action action2 = (Notification.Action) list2.get(i);
                                    if (TextUtils.equals(action.title, action2.title)) {
                                        Icon icon = action.getIcon();
                                        Icon icon2 = action2.getIcon();
                                        if (!(icon == icon2 ? false : (icon == null || icon2 == null) ? true : !icon.sameAs(icon2)) && Objects.equals(action.actionIntent, action2.actionIntent)) {
                                            RemoteInput[] remoteInputs = action.getRemoteInputs();
                                            RemoteInput[] remoteInputs2 = action2.getRemoteInputs();
                                            if (remoteInputs != remoteInputs2) {
                                                if (remoteInputs != null && remoteInputs2 != null && remoteInputs.length == remoteInputs2.length) {
                                                    for (int i2 = 0; i2 < remoteInputs.length; i2++) {
                                                        RemoteInput remoteInput = remoteInputs[i2];
                                                        RemoteInput remoteInput2 = remoteInputs2[i2];
                                                        if (TextUtils.equals(remoteInput.getLabel(), remoteInput2.getLabel())) {
                                                            CharSequence[] choices = remoteInput.getChoices();
                                                            CharSequence[] choices2 = remoteInput2.getChoices();
                                                            if (choices != choices2) {
                                                                if (choices != null && choices2 != null && choices.length == choices2.length) {
                                                                    for (int i3 = 0; i3 < choices.length; i3++) {
                                                                        if (TextUtils.equals(choices[i3], choices2[i3])) {
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                    z2 = false;
                                                    if (!z2) {
                                                    }
                                                }
                                                z2 = true;
                                                if (!z2) {
                                                }
                                            } else {
                                                z2 = false;
                                                if (!z2) {
                                                }
                                            }
                                        }
                                    }
                                }
                                z = false;
                                z3 = z ? false : true;
                            }
                            z = true;
                            if (z) {
                            }
                        } else {
                            z = false;
                            if (z) {
                            }
                        }
                    }
                }
            }
        }
        final boolean z4 = !z3;
        SmartReplyConstants smartReplyConstants = this.constants;
        int i4 = SmartReplyView.MEASURE_SPEC_ANY_LENGTH;
        final SmartReplyView smartReplyView = (SmartReplyView) LayoutInflater.from(context).inflate(R.layout.smart_reply_view, (ViewGroup) null);
        smartReplyView.mMaxNumActions = smartReplyConstants.mMaxNumActions;
        smartReplyView.mMaxSqueezeRemeasureAttempts = smartReplyConstants.mMaxSqueezeRemeasureAttempts;
        smartReplyView.mMinNumSystemGeneratedReplies = smartReplyConstants.mMinNumSystemGeneratedReplies;
        final SmartReplyView.SmartReplies smartReplies3 = inflatedSmartReplyState2.smartReplies;
        smartReplyView.mSmartRepliesGeneratedByAssistant = smartReplies3 != null ? smartReplies3.fromAssistant : false;
        Sequence transformingIndexedSequence2 = smartReplies3 != null ? new TransformingIndexedSequence(new CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1(smartReplies3.choices), new Function2() { // from class: com.android.systemui.statusbar.policy.SmartReplyStateInflaterImpl$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj5, Object obj6) {
                SmartReplyStateInflaterImpl smartReplyStateInflaterImpl = this.f$0;
                final SmartReplyView smartReplyView2 = smartReplyView;
                final NotificationEntry notificationEntry2 = notificationEntry;
                final SmartReplyView.SmartReplies smartReplies4 = smartReplies3;
                boolean z5 = z4;
                final int iIntValue = ((Integer) obj5).intValue();
                final CharSequence charSequence = (CharSequence) obj6;
                SmartReplyInflater smartReplyInflater = smartReplyStateInflaterImpl.smartRepliesInflater;
                smartReplyView2.getClass();
                charSequence.getClass();
                final SmartReplyInflaterImpl smartReplyInflaterImpl = (SmartReplyInflaterImpl) smartReplyInflater;
                smartReplyInflaterImpl.getClass();
                final Button button = (Button) LayoutInflater.from(smartReplyView2.getContext()).inflate(R.layout.smart_reply_button, (ViewGroup) smartReplyView2, false);
                button.setText(charSequence);
                View.OnClickListener delayedOnClickListener = new View.OnClickListener() { // from class: com.android.systemui.statusbar.policy.SmartReplyInflaterImpl$inflateReplyButton$1$onClickListener$1
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        final SmartReplyInflaterImpl smartReplyInflaterImpl2 = smartReplyInflaterImpl;
                        final NotificationEntry notificationEntry3 = notificationEntry2;
                        final SmartReplyView.SmartReplies smartReplies5 = smartReplies4;
                        final int i5 = iIntValue;
                        final SmartReplyView smartReplyView3 = smartReplyView2;
                        final Button button2 = button;
                        final CharSequence charSequence2 = charSequence;
                        smartReplyInflaterImpl2.getClass();
                        boolean z6 = !notificationEntry3.isRowPinned();
                        final Function0 function0 = new Function0() { // from class: com.android.systemui.statusbar.policy.SmartReplyInflaterImpl$$ExternalSyntheticLambda0
                            @Override // kotlin.jvm.functions.Function0
                            public final Object invoke() throws PendingIntent.CanceledException {
                                INotificationManager iNotificationManager;
                                Button button3 = button2;
                                CharSequence charSequence3 = charSequence2;
                                SmartReplyInflaterImpl smartReplyInflaterImpl3 = smartReplyInflaterImpl2;
                                SmartReplyConstants smartReplyConstants2 = smartReplyInflaterImpl3.constants;
                                SmartReplyView.SmartReplies smartReplies6 = smartReplies5;
                                int editChoicesBeforeSending = smartReplies6.remoteInput.getEditChoicesBeforeSending();
                                smartReplyConstants2.getClass();
                                boolean z7 = editChoicesBeforeSending != 1 ? editChoicesBeforeSending != 2 ? smartReplyConstants2.mEditChoicesBeforeSending : true : false;
                                int i6 = i5;
                                if (z7) {
                                    RemoteInput remoteInput3 = smartReplies6.remoteInput;
                                    PendingIntent pendingIntent = smartReplies6.pendingIntent;
                                    NotificationEntry.EditedSuggestionInfo editedSuggestionInfo = new NotificationEntry.EditedSuggestionInfo(charSequence3, i6);
                                    NotificationRemoteInputManager notificationRemoteInputManager = smartReplyInflaterImpl3.remoteInputManager;
                                    notificationRemoteInputManager.getClass();
                                    int i7 = ExpandHeadsUpOnInlineReply.$r8$clinit;
                                    notificationRemoteInputManager.activateRemoteInputOnExpanded(button3, new RemoteInput[]{remoteInput3}, remoteInput3, pendingIntent, editedSuggestionInfo);
                                } else {
                                    CharSequence text = button3.getText();
                                    NotificationEntry notificationEntry4 = notificationEntry3;
                                    int metricsEventEnum = NotificationLogger.getNotificationLocation(notificationEntry4).toMetricsEventEnum();
                                    SmartReplyController smartReplyController = smartReplyInflaterImpl3.smartReplyController;
                                    smartReplyController.mCallback.onSmartReplySent(notificationEntry4, text);
                                    ((ArraySet) smartReplyController.mSendingKeys).add(notificationEntry4.mKey);
                                    try {
                                        smartReplyController.mBarService.onNotificationSmartReplySent(notificationEntry4.mSbn.getKey(), i6, text, metricsEventEnum, false);
                                        SystemUIAnalytics.sendEventCDLog(SystemUIAnalytics.SID_QUICKPANEL_OPENED, SystemUIAnalytics.EID_QPNE_QUICK_REPLY_BUTTON_AND_ACTIONS, "type", SystemUIAnalytics.QPNE_VID_REPLY_TEXT, SystemUIAnalytics.QPNE_KEY_APP, notificationEntry4.mSbn.getPackageName());
                                    } catch (RemoteException unused) {
                                    }
                                    notificationEntry4.hasSentReply = true;
                                    try {
                                        if (NotiRune.NOTI_SUBSCREEN_SUPPORT_NOTIFICATION_HISTORY && (iNotificationManager = smartReplyInflaterImpl3.notifManager) != null) {
                                            iNotificationManager.addReplyHistory(1, notificationEntry4.mKey, notificationEntry4.mSbn.getPackageName(), notificationEntry4.mSbn.getUser().getIdentifier(), "NOUI_2023", button3.getText().toString());
                                        }
                                        Intent intentCreateRemoteInputIntent = SmartReplyInflaterImpl.createRemoteInputIntent(smartReplies6, charSequence3);
                                        ActivityOptions activityOptionsMakeBasic = ActivityOptions.makeBasic();
                                        activityOptionsMakeBasic.setPendingIntentBackgroundActivityStartMode(1);
                                        smartReplies6.pendingIntent.send(smartReplyInflaterImpl3.context, 0, intentCreateRemoteInputIntent, null, null, null, activityOptionsMakeBasic.toBundle());
                                    } catch (PendingIntent.CanceledException e) {
                                        Log.w("SmartReplyViewInflater", "Unable to send smart reply", e);
                                    } catch (RemoteException e2) {
                                        Log.w("SmartReplyViewInflater", "Unable to write smart reply to history", e2);
                                    }
                                    View view2 = smartReplyView3.mSmartReplyContainer;
                                    if (view2 != null) {
                                        view2.setVisibility(8);
                                    }
                                }
                                return Boolean.FALSE;
                            }
                        };
                        ThreadPoolExecutor threadPoolExecutor = SmartReplyStateInflaterKt.iconTaskThreadPool;
                        smartReplyInflaterImpl2.keyguardDismissUtil.executeWhenUnlocked(new ActivityStarter.OnDismissAction() { // from class: com.android.systemui.statusbar.policy.SmartReplyStateInflaterKt$sam$com_android_systemui_plugins_ActivityStarter_OnDismissAction$0
                            @Override // com.android.systemui.plugins.ActivityStarter.OnDismissAction
                            public final /* synthetic */ boolean onDismiss() {
                                return ((Boolean) function0.invoke()).booleanValue();
                            }
                        }, z6, false);
                    }
                };
                if (z5) {
                    delayedOnClickListener = new DelayedOnClickListener(delayedOnClickListener, smartReplyInflaterImpl.constants.mOnClickInitDelay);
                }
                button.setOnClickListener(delayedOnClickListener);
                button.setAccessibilityDelegate(new View.AccessibilityDelegate() { // from class: com.android.systemui.statusbar.policy.SmartReplyInflaterImpl$inflateReplyButton$1$1
                    @Override // android.view.View.AccessibilityDelegate
                    public final void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfo accessibilityNodeInfo) {
                        super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
                        accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(16, smartReplyView2.getResources().getString(R.string.accessibility_send_smart_reply)));
                    }
                });
                ((SmartReplyView.LayoutParams) button.getLayoutParams()).mButtonType = SmartReplyView.SmartButtonType.REPLY;
                return button;
            }
        }) : EmptySequence.INSTANCE;
        final SmartReplyView.SmartActions smartActions3 = inflatedSmartReplyState2.smartActions;
        if (smartActions3 != null) {
            final ContextThemeWrapper contextThemeWrapper = new ContextThemeWrapper(context2, context.getTheme());
            transformingIndexedSequence = new TransformingIndexedSequence(SequencesKt___SequencesKt.filter(new CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1(smartActions3.actions), new SmartReplyStateInflaterImpl$$ExternalSyntheticLambda1()), new Function2() { // from class: com.android.systemui.statusbar.policy.SmartReplyStateInflaterImpl$$ExternalSyntheticLambda2
                /* JADX WARN: Removed duplicated region for block: B:19:0x0099  */
                /* JADX WARN: Removed duplicated region for block: B:26:0x00c9  */
                /* JADX WARN: Removed duplicated region for block: B:28:0x00ed  */
                /* JADX WARN: Removed duplicated region for block: B:31:0x0100  */
                @Override // kotlin.jvm.functions.Function2
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object invoke(Object obj5, Object obj6) throws Resources.NotFoundException {
                    Icon icon3;
                    Object failure;
                    Throwable thM3441exceptionOrNullimpl;
                    Drawable gradientDrawable;
                    SmartReplyStateInflaterImpl smartReplyStateInflaterImpl = this.f$0;
                    SmartReplyView smartReplyView2 = smartReplyView;
                    final NotificationEntry notificationEntry2 = notificationEntry;
                    final SmartReplyView.SmartActions smartActions4 = smartActions3;
                    boolean z5 = z4;
                    final ContextThemeWrapper contextThemeWrapper2 = contextThemeWrapper;
                    final int iIntValue = ((Integer) obj5).intValue();
                    final Notification.Action action3 = (Notification.Action) obj6;
                    SmartActionInflater smartActionInflater = smartReplyStateInflaterImpl.smartActionsInflater;
                    smartReplyView2.getClass();
                    action3.getClass();
                    final SmartActionInflaterImpl smartActionInflaterImpl = (SmartActionInflaterImpl) smartActionInflater;
                    smartActionInflaterImpl.getClass();
                    Button button = (Button) LayoutInflater.from(smartReplyView2.getContext()).inflate(R.layout.smart_action_button, (ViewGroup) smartReplyView2, false);
                    button.setText(action3.title);
                    final int dimensionPixelSize = button.getContext().getResources().getDimensionPixelSize(R.dimen.smart_action_button_icon_size);
                    final Icon icon4 = action3.getIcon();
                    ThreadPoolExecutor threadPoolExecutor = SmartReplyStateInflaterKt.iconTaskThreadPool;
                    if (icon4.getType() == 4 || icon4.getType() == 6) {
                        FutureTask futureTask = new FutureTask(new Callable() { // from class: com.android.systemui.statusbar.policy.SmartReplyStateInflaterKt$loadIconDrawableWithTimeout$bitmapTask$1
                            @Override // java.util.concurrent.Callable
                            public final Object call() throws IOException {
                                Context context3 = contextThemeWrapper2;
                                Icon icon5 = icon4;
                                long jCurrentTimeMillis = System.currentTimeMillis();
                                ImageDecoder.Source sourceCreateSource = ImageDecoder.createSource(context3.getContentResolver(), icon5.getUri());
                                final int i5 = dimensionPixelSize;
                                Bitmap bitmapDecodeBitmap = ImageDecoder.decodeBitmap(sourceCreateSource, new ImageDecoder.OnHeaderDecodedListener() { // from class: com.android.systemui.statusbar.policy.SmartReplyStateInflaterKt$loadIconDrawableWithTimeout$bitmapTask$1$durationMillis$1$1
                                    @Override // android.graphics.ImageDecoder.OnHeaderDecodedListener
                                    public final void onHeaderDecoded(ImageDecoder imageDecoder, ImageDecoder.ImageInfo imageInfo, ImageDecoder.Source source) {
                                        int i6 = i5;
                                        imageDecoder.setTargetSize(i6, i6);
                                        imageDecoder.setAllocator(0);
                                    }
                                });
                                long jCurrentTimeMillis2 = System.currentTimeMillis() - jCurrentTimeMillis;
                                if (jCurrentTimeMillis2 > 500) {
                                    Log.w("SmartReplyViewInflater", "Loading " + icon4 + " took " + (jCurrentTimeMillis2 / 1000.0f) + " sec");
                                }
                                if (bitmapDecodeBitmap != null) {
                                    return bitmapDecodeBitmap;
                                }
                                throw new IllegalStateException("ImageDecoder.decodeBitmap() returned null");
                            }
                        });
                        try {
                            int i5 = Result.$r8$clinit;
                            SmartReplyStateInflaterKt.iconTaskThreadPool.execute(futureTask);
                            icon3 = icon4;
                            try {
                                failure = (Bitmap) futureTask.get(500L, TimeUnit.MILLISECONDS);
                            } catch (Throwable th) {
                                th = th;
                                int i6 = Result.$r8$clinit;
                                failure = new Result.Failure(th);
                                thM3441exceptionOrNullimpl = Result.m3441exceptionOrNullimpl(failure);
                                if (thM3441exceptionOrNullimpl != null) {
                                }
                                if (gradientDrawable == null) {
                                }
                                gradientDrawable.setBounds(0, 0, dimensionPixelSize, dimensionPixelSize);
                                button.setCompoundDrawablesRelative(gradientDrawable, null, null, null);
                                View.OnClickListener delayedOnClickListener = new View.OnClickListener() { // from class: com.android.systemui.statusbar.policy.SmartActionInflaterImpl$inflateActionButton$1$onClickListener$1
                                    @Override // android.view.View.OnClickListener
                                    public final void onClick(View view) {
                                        final SmartActionInflaterImpl smartActionInflaterImpl2 = smartActionInflaterImpl;
                                        final NotificationEntry notificationEntry3 = notificationEntry2;
                                        final SmartReplyView.SmartActions smartActions5 = smartActions4;
                                        final int i7 = iIntValue;
                                        final Notification.Action action4 = action3;
                                        smartActionInflaterImpl2.getClass();
                                        if (smartActions5.fromAssistant && 11 == action4.getSemanticAction()) {
                                            ExpandableNotificationRow expandableNotificationRow = notificationEntry3.row;
                                            expandableNotificationRow.doSmartActionClick(((int) expandableNotificationRow.getX()) / 2, ((int) notificationEntry3.row.getY()) / 2);
                                            smartActionInflaterImpl2.smartReplyController.smartActionClicked(notificationEntry3, i7, action4, smartActions5.fromAssistant);
                                        } else {
                                            PendingIntent pendingIntent = action4.actionIntent;
                                            ExpandableNotificationRow expandableNotificationRow2 = notificationEntry3.row;
                                            final Function0 function0 = new Function0() { // from class: com.android.systemui.statusbar.policy.SmartActionInflaterImpl$$ExternalSyntheticLambda0
                                                @Override // kotlin.jvm.functions.Function0
                                                public final Object invoke() {
                                                    Notification.Action action5 = action4;
                                                    smartActionInflaterImpl2.smartReplyController.smartActionClicked(notificationEntry3, i7, action5, smartActions5.fromAssistant);
                                                    return Unit.INSTANCE;
                                                }
                                            };
                                            ThreadPoolExecutor threadPoolExecutor2 = SmartReplyStateInflaterKt.iconTaskThreadPool;
                                            smartActionInflaterImpl2.activityStarter.startPendingIntentDismissingKeyguard(pendingIntent, new Runnable() { // from class: com.android.systemui.statusbar.policy.SmartReplyStateInflaterKt$startPendingIntentDismissingKeyguard$1
                                                @Override // java.lang.Runnable
                                                public final void run() {
                                                    function0.invoke();
                                                }
                                            }, expandableNotificationRow2);
                                        }
                                    }
                                };
                                if (z5) {
                                }
                                button.setOnClickListener(delayedOnClickListener);
                                ((SmartReplyView.LayoutParams) button.getLayoutParams()).mButtonType = SmartReplyView.SmartButtonType.ACTION;
                                return button;
                            }
                        } catch (Throwable th2) {
                            th = th2;
                            icon3 = icon4;
                        }
                        thM3441exceptionOrNullimpl = Result.m3441exceptionOrNullimpl(failure);
                        if (thM3441exceptionOrNullimpl != null) {
                            Drawable bitmapDrawable = new BitmapDrawable(contextThemeWrapper2.getResources(), (Bitmap) failure);
                            if (icon3.getType() == 6) {
                                bitmapDrawable = new AdaptiveIconDrawable(null, bitmapDrawable);
                            }
                            if (icon3.hasTint()) {
                                bitmapDrawable.mutate();
                                bitmapDrawable.setTintList(icon3.getTintList());
                                bitmapDrawable.setTintBlendMode(icon3.getTintBlendMode());
                            }
                            gradientDrawable = bitmapDrawable;
                        } else {
                            Log.e("SmartReplyViewInflater", "Failed to load " + icon3 + ": " + thM3441exceptionOrNullimpl);
                            futureTask.cancel(true);
                            gradientDrawable = null;
                        }
                    } else {
                        gradientDrawable = icon4.loadDrawable(contextThemeWrapper2);
                    }
                    if (gradientDrawable == null) {
                        gradientDrawable = new GradientDrawable();
                    }
                    gradientDrawable.setBounds(0, 0, dimensionPixelSize, dimensionPixelSize);
                    button.setCompoundDrawablesRelative(gradientDrawable, null, null, null);
                    View.OnClickListener delayedOnClickListener2 = new View.OnClickListener() { // from class: com.android.systemui.statusbar.policy.SmartActionInflaterImpl$inflateActionButton$1$onClickListener$1
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            final SmartActionInflaterImpl smartActionInflaterImpl2 = smartActionInflaterImpl;
                            final NotificationEntry notificationEntry3 = notificationEntry2;
                            final SmartReplyView.SmartActions smartActions5 = smartActions4;
                            final int i7 = iIntValue;
                            final Notification.Action action4 = action3;
                            smartActionInflaterImpl2.getClass();
                            if (smartActions5.fromAssistant && 11 == action4.getSemanticAction()) {
                                ExpandableNotificationRow expandableNotificationRow = notificationEntry3.row;
                                expandableNotificationRow.doSmartActionClick(((int) expandableNotificationRow.getX()) / 2, ((int) notificationEntry3.row.getY()) / 2);
                                smartActionInflaterImpl2.smartReplyController.smartActionClicked(notificationEntry3, i7, action4, smartActions5.fromAssistant);
                            } else {
                                PendingIntent pendingIntent = action4.actionIntent;
                                ExpandableNotificationRow expandableNotificationRow2 = notificationEntry3.row;
                                final Function0 function0 = new Function0() { // from class: com.android.systemui.statusbar.policy.SmartActionInflaterImpl$$ExternalSyntheticLambda0
                                    @Override // kotlin.jvm.functions.Function0
                                    public final Object invoke() {
                                        Notification.Action action5 = action4;
                                        smartActionInflaterImpl2.smartReplyController.smartActionClicked(notificationEntry3, i7, action5, smartActions5.fromAssistant);
                                        return Unit.INSTANCE;
                                    }
                                };
                                ThreadPoolExecutor threadPoolExecutor2 = SmartReplyStateInflaterKt.iconTaskThreadPool;
                                smartActionInflaterImpl2.activityStarter.startPendingIntentDismissingKeyguard(pendingIntent, new Runnable() { // from class: com.android.systemui.statusbar.policy.SmartReplyStateInflaterKt$startPendingIntentDismissingKeyguard$1
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        function0.invoke();
                                    }
                                }, expandableNotificationRow2);
                            }
                        }
                    };
                    if (z5) {
                        delayedOnClickListener2 = new DelayedOnClickListener(delayedOnClickListener2, smartActionInflaterImpl.constants.mOnClickInitDelay);
                    }
                    button.setOnClickListener(delayedOnClickListener2);
                    ((SmartReplyView.LayoutParams) button.getLayoutParams()).mButtonType = SmartReplyView.SmartButtonType.ACTION;
                    return button;
                }
            });
        } else {
            transformingIndexedSequence = EmptySequence.INSTANCE;
        }
        return new InflatedSmartReplyViewHolder(smartReplyView, SequencesKt___SequencesKt.toList(SequencesKt__SequencesKt.flatten(ArraysKt___ArraysKt.asSequence(new Sequence[]{transformingIndexedSequence2, transformingIndexedSequence}))));
    }
}
