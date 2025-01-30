package com.android.systemui.statusbar.policy;

import android.app.INotificationManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.RemoteInput;
import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.graphics.drawable.AdaptiveIconDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.Icon;
import android.os.RemoteException;
import android.service.notification.StatusBarNotification;
import android.text.TextUtils;
import android.util.ArraySet;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Button;
import com.android.keyguard.AbstractC0689x6838b71d;
import com.android.systemui.NotiRune;
import com.android.systemui.R;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.shared.system.ActivityManagerWrapper;
import com.android.systemui.shared.system.DevicePolicyManagerWrapper;
import com.android.systemui.shared.system.PackageManagerWrapper;
import com.android.systemui.statusbar.NotificationRemoteInputManager;
import com.android.systemui.statusbar.SmartReplyController;
import com.android.systemui.statusbar.notification.collection.NotifCollection$$ExternalSyntheticLambda4;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.coordinator.RemoteInputCoordinator;
import com.android.systemui.statusbar.notification.collection.coordinator.RemoteInputCoordinatorKt;
import com.android.systemui.statusbar.notification.logging.NotificationLogger;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.policy.InflatedSmartReplyState;
import com.android.systemui.statusbar.policy.SmartReplyView;
import com.android.systemui.util.SystemUIAnalytics;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import kotlin.Result;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1;
import kotlin.collections.EmptyList;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.EmptySequence;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt___SequencesKt;
import kotlin.sequences.TransformingIndexedSequence;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
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

    /* JADX WARN: Code restructure failed: missing block: B:105:0x013d, code lost:
    
        if (r0 != false) goto L125;
     */
    /* JADX WARN: Removed duplicated region for block: B:116:0x0168  */
    /* JADX WARN: Removed duplicated region for block: B:119:0x016e  */
    /* JADX WARN: Removed duplicated region for block: B:122:0x018e  */
    /* JADX WARN: Removed duplicated region for block: B:125:0x01b9  */
    /* JADX WARN: Removed duplicated region for block: B:126:0x0187  */
    /* JADX WARN: Removed duplicated region for block: B:86:0x012d A[LOOP:1: B:66:0x00ea->B:86:0x012d, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:87:0x0132 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:93:0x0136 A[LOOP:0: B:49:0x008e->B:93:0x0136, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:94:0x013c A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final InflatedSmartReplyViewHolder inflateSmartReplyViewHolder(Context context, Context context2, final NotificationEntry notificationEntry, InflatedSmartReplyState inflatedSmartReplyState, InflatedSmartReplyState inflatedSmartReplyState2) {
        boolean z;
        final SmartReplyView.SmartActions smartActions;
        Sequence sequence;
        Object obj;
        Object obj2;
        boolean z2;
        boolean z3;
        boolean z4;
        if (!SmartReplyStateInflaterKt.shouldShowSmartReplyView(notificationEntry, inflatedSmartReplyState2)) {
            return new InflatedSmartReplyViewHolder(null, null);
        }
        if (inflatedSmartReplyState != inflatedSmartReplyState2) {
            if (inflatedSmartReplyState != null && inflatedSmartReplyState.hasPhishingAction == inflatedSmartReplyState2.hasPhishingAction) {
                SmartReplyView.SmartReplies smartReplies = inflatedSmartReplyState.smartReplies;
                List list = smartReplies != null ? smartReplies.choices : null;
                if (list == null) {
                    list = EmptyList.INSTANCE;
                }
                SmartReplyView.SmartReplies smartReplies2 = inflatedSmartReplyState2.smartReplies;
                List list2 = smartReplies2 != null ? smartReplies2.choices : null;
                if (list2 == null) {
                    list2 = EmptyList.INSTANCE;
                }
                if (Intrinsics.areEqual(list, list2)) {
                    InflatedSmartReplyState.SuppressedActions suppressedActions = inflatedSmartReplyState.suppressedActions;
                    if (suppressedActions == null || (obj = suppressedActions.suppressedActionIndices) == null) {
                        obj = EmptyList.INSTANCE;
                    }
                    InflatedSmartReplyState.SuppressedActions suppressedActions2 = inflatedSmartReplyState2.suppressedActions;
                    if (suppressedActions2 == null || (obj2 = suppressedActions2.suppressedActionIndices) == null) {
                        obj2 = EmptyList.INSTANCE;
                    }
                    if (Intrinsics.areEqual(obj, obj2)) {
                        SmartReplyView.SmartActions smartActions2 = inflatedSmartReplyState.smartActions;
                        List list3 = smartActions2 != null ? smartActions2.actions : null;
                        if (list3 == null) {
                            list3 = EmptyList.INSTANCE;
                        }
                        SmartReplyView.SmartActions smartActions3 = inflatedSmartReplyState2.smartActions;
                        List list4 = smartActions3 != null ? smartActions3.actions : null;
                        if (list4 == null) {
                            list4 = EmptyList.INSTANCE;
                        }
                        if (list3 != list4) {
                            if (list3 != null && list4 != null && list3.size() == list4.size()) {
                                for (int i = 0; i < list3.size(); i++) {
                                    Notification.Action action = (Notification.Action) list3.get(i);
                                    Notification.Action action2 = (Notification.Action) list4.get(i);
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
                                                                z4 = true;
                                                                if (z4) {
                                                                }
                                                            }
                                                            z4 = false;
                                                            if (z4) {
                                                            }
                                                        }
                                                    }
                                                }
                                                z3 = true;
                                                if (z3) {
                                                }
                                            }
                                            z3 = false;
                                            if (z3) {
                                            }
                                        }
                                    }
                                }
                            }
                            z2 = true;
                            break;
                        }
                        z2 = false;
                    }
                }
            }
            z = false;
            final boolean z5 = !z;
            SmartReplyConstants smartReplyConstants = this.constants;
            int i4 = SmartReplyView.MEASURE_SPEC_ANY_LENGTH;
            final SmartReplyView smartReplyView = (SmartReplyView) LayoutInflater.from(context).inflate(R.layout.smart_reply_view, (ViewGroup) null);
            smartReplyView.mMaxNumActions = smartReplyConstants.mMaxNumActions;
            smartReplyView.mMaxSqueezeRemeasureAttempts = smartReplyConstants.mMaxSqueezeRemeasureAttempts;
            smartReplyView.mMinNumSystemGeneratedReplies = smartReplyConstants.mMinNumSystemGeneratedReplies;
            final SmartReplyView.SmartReplies smartReplies3 = inflatedSmartReplyState2.smartReplies;
            smartReplyView.mSmartRepliesGeneratedByAssistant = smartReplies3 != null ? smartReplies3.fromAssistant : false;
            Sequence transformingIndexedSequence = smartReplies3 == null ? new TransformingIndexedSequence(new CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1(smartReplies3.choices), new Function2() { // from class: com.android.systemui.statusbar.policy.SmartReplyStateInflaterImpl$inflateSmartReplyViewHolder$smartReplyButtons$1$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj3, Object obj4) {
                    final int intValue = ((Number) obj3).intValue();
                    final CharSequence charSequence = (CharSequence) obj4;
                    SmartReplyInflater smartReplyInflater = SmartReplyStateInflaterImpl.this.smartRepliesInflater;
                    final SmartReplyView smartReplyView2 = smartReplyView;
                    final NotificationEntry notificationEntry2 = notificationEntry;
                    final SmartReplyView.SmartReplies smartReplies4 = smartReplies3;
                    boolean z6 = z5;
                    final SmartReplyInflaterImpl smartReplyInflaterImpl = (SmartReplyInflaterImpl) smartReplyInflater;
                    smartReplyInflaterImpl.getClass();
                    final Button button = (Button) LayoutInflater.from(smartReplyView2.getContext()).inflate(R.layout.smart_reply_button, (ViewGroup) smartReplyView2, false);
                    button.setText(charSequence);
                    View.OnClickListener onClickListener = new View.OnClickListener() { // from class: com.android.systemui.statusbar.policy.SmartReplyInflaterImpl$inflateReplyButton$1$onClickListener$1
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            final SmartReplyInflaterImpl smartReplyInflaterImpl2 = SmartReplyInflaterImpl.this;
                            final NotificationEntry notificationEntry3 = notificationEntry2;
                            final SmartReplyView.SmartReplies smartReplies5 = smartReplies4;
                            final int i5 = intValue;
                            final SmartReplyView smartReplyView3 = smartReplyView2;
                            final Button button2 = button;
                            final CharSequence charSequence2 = charSequence;
                            smartReplyInflaterImpl2.getClass();
                            boolean z7 = !notificationEntry3.isRowPinned();
                            final Function0 function0 = new Function0() { // from class: com.android.systemui.statusbar.policy.SmartReplyInflaterImpl$onSmartReplyClick$1
                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                {
                                    super(0);
                                }

                                @Override // kotlin.jvm.functions.Function0
                                public final Object invoke() {
                                    boolean booleanValue;
                                    INotificationManager iNotificationManager;
                                    SmartReplyConstants smartReplyConstants2 = SmartReplyInflaterImpl.this.constants;
                                    int editChoicesBeforeSending = smartReplies5.remoteInput.getEditChoicesBeforeSending();
                                    smartReplyConstants2.getClass();
                                    boolean z8 = true;
                                    if (editChoicesBeforeSending == 1) {
                                        z8 = false;
                                    } else if (editChoicesBeforeSending != 2) {
                                        z8 = smartReplyConstants2.mEditChoicesBeforeSending;
                                    }
                                    if (z8) {
                                        NotificationRemoteInputManager notificationRemoteInputManager = SmartReplyInflaterImpl.this.remoteInputManager;
                                        Button button3 = button2;
                                        SmartReplyView.SmartReplies smartReplies6 = smartReplies5;
                                        RemoteInput remoteInput3 = smartReplies6.remoteInput;
                                        notificationRemoteInputManager.activateRemoteInput(button3, new RemoteInput[]{remoteInput3}, remoteInput3, smartReplies6.pendingIntent, new NotificationEntry.EditedSuggestionInfo(charSequence2, i5), null);
                                    } else {
                                        SmartReplyController smartReplyController = SmartReplyInflaterImpl.this.smartReplyController;
                                        NotificationEntry notificationEntry4 = notificationEntry3;
                                        int i6 = i5;
                                        CharSequence text = button2.getText();
                                        int metricsEventEnum = NotificationLogger.getNotificationLocation(notificationEntry3).toMetricsEventEnum();
                                        RemoteInputCoordinator remoteInputCoordinator = smartReplyController.mCallback.$tmp0;
                                        remoteInputCoordinator.getClass();
                                        booleanValue = ((Boolean) RemoteInputCoordinatorKt.DEBUG$delegate.getValue()).booleanValue();
                                        String str = notificationEntry4.mKey;
                                        if (booleanValue) {
                                            AbstractC0689x6838b71d.m68m("onSmartReplySent(entry=", str, ")", "RemoteInputCoordinator");
                                        }
                                        StatusBarNotification rebuildWithRemoteInputInserted = remoteInputCoordinator.mRebuilder.rebuildWithRemoteInputInserted(notificationEntry4, text, true, null, null);
                                        NotifCollection$$ExternalSyntheticLambda4 notifCollection$$ExternalSyntheticLambda4 = remoteInputCoordinator.mNotifUpdater;
                                        if (notifCollection$$ExternalSyntheticLambda4 == null) {
                                            notifCollection$$ExternalSyntheticLambda4 = null;
                                        }
                                        notifCollection$$ExternalSyntheticLambda4.onInternalNotificationUpdate("Adding smart reply spinner for sent", rebuildWithRemoteInputInserted);
                                        remoteInputCoordinator.mRemoteInputActiveExtender.endLifetimeExtensionAfterDelay(str, 500L);
                                        ((ArraySet) smartReplyController.mSendingKeys).add(notificationEntry4.mKey);
                                        try {
                                            smartReplyController.mBarService.onNotificationSmartReplySent(notificationEntry4.mSbn.getKey(), i6, text, metricsEventEnum, false);
                                            SystemUIAnalytics.sendEventCDLog("QPN001", "QPNE0011", "type", "reply texts", "app", notificationEntry4.mSbn.getPackageName());
                                        } catch (RemoteException unused) {
                                        }
                                        NotificationEntry notificationEntry5 = notificationEntry3;
                                        notificationEntry5.hasSentReply = true;
                                        try {
                                            if (NotiRune.NOTI_SUBSCREEN_SUPPORT_NOTIFICATION_HISTORY && (iNotificationManager = SmartReplyInflaterImpl.this.notifManager) != null) {
                                                iNotificationManager.addReplyHistory(1, notificationEntry5.mKey, notificationEntry5.mSbn.getPackageName(), notificationEntry3.mSbn.getUser().getIdentifier(), "NOUI_2023", button2.getText().toString());
                                            }
                                            smartReplies5.pendingIntent.send(SmartReplyInflaterImpl.this.context, 0, SmartReplyInflaterImpl.access$createRemoteInputIntent(SmartReplyInflaterImpl.this, smartReplies5, charSequence2));
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
                                    return ((Boolean) Function0.this.invoke()).booleanValue();
                                }
                            }, z7, false);
                        }
                    };
                    if (z6) {
                        onClickListener = new DelayedOnClickListener(onClickListener, smartReplyInflaterImpl.constants.mOnClickInitDelay);
                    }
                    button.setOnClickListener(onClickListener);
                    button.setAccessibilityDelegate(new View.AccessibilityDelegate() { // from class: com.android.systemui.statusbar.policy.SmartReplyInflaterImpl$inflateReplyButton$1$1
                        @Override // android.view.View.AccessibilityDelegate
                        public final void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfo accessibilityNodeInfo) {
                            super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
                            accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(16, SmartReplyView.this.getResources().getString(R.string.accessibility_send_smart_reply)));
                        }
                    });
                    ((SmartReplyView.LayoutParams) button.getLayoutParams()).mButtonType = SmartReplyView.SmartButtonType.REPLY;
                    return button;
                }
            }) : EmptySequence.INSTANCE;
            smartActions = inflatedSmartReplyState2.smartActions;
            if (smartActions == null) {
                final ContextThemeWrapper contextThemeWrapper = new ContextThemeWrapper(context2, context.getTheme());
                sequence = new TransformingIndexedSequence(SequencesKt___SequencesKt.filter(new CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1(smartActions.actions), new Function1() { // from class: com.android.systemui.statusbar.policy.SmartReplyStateInflaterImpl$inflateSmartReplyViewHolder$smartActionButtons$1$1
                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj3) {
                        return Boolean.valueOf(((Notification.Action) obj3).actionIntent != null);
                    }
                }), new Function2() { // from class: com.android.systemui.statusbar.policy.SmartReplyStateInflaterImpl$inflateSmartReplyViewHolder$smartActionButtons$1$2
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(2);
                    }

                    @Override // kotlin.jvm.functions.Function2
                    public final Object invoke(Object obj3, Object obj4) {
                        Object failure;
                        Drawable drawable;
                        final int intValue = ((Number) obj3).intValue();
                        final Notification.Action action3 = (Notification.Action) obj4;
                        SmartActionInflater smartActionInflater = SmartReplyStateInflaterImpl.this.smartActionsInflater;
                        SmartReplyView smartReplyView2 = smartReplyView;
                        final NotificationEntry notificationEntry2 = notificationEntry;
                        final SmartReplyView.SmartActions smartActions4 = smartActions;
                        boolean z6 = z5;
                        final ContextThemeWrapper contextThemeWrapper2 = contextThemeWrapper;
                        final SmartActionInflaterImpl smartActionInflaterImpl = (SmartActionInflaterImpl) smartActionInflater;
                        smartActionInflaterImpl.getClass();
                        Button button = (Button) LayoutInflater.from(smartReplyView2.getContext()).inflate(R.layout.smart_action_button, (ViewGroup) smartReplyView2, false);
                        button.setText(action3.title);
                        final int dimensionPixelSize = button.getContext().getResources().getDimensionPixelSize(R.dimen.smart_action_button_icon_size);
                        final Icon icon3 = action3.getIcon();
                        ThreadPoolExecutor threadPoolExecutor = SmartReplyStateInflaterKt.iconTaskThreadPool;
                        if (icon3.getType() == 4 || icon3.getType() == 6) {
                            FutureTask futureTask = new FutureTask(new Callable() { // from class: com.android.systemui.statusbar.policy.SmartReplyStateInflaterKt$loadIconDrawableWithTimeout$bitmapTask$1
                                @Override // java.util.concurrent.Callable
                                public final Object call() {
                                    long currentTimeMillis = System.currentTimeMillis();
                                    ContentResolver contentResolver = contextThemeWrapper2.getContentResolver();
                                    Icon icon4 = icon3;
                                    ImageDecoder.Source createSource = ImageDecoder.createSource(contentResolver, icon4.getUri());
                                    final int i5 = dimensionPixelSize;
                                    Bitmap decodeBitmap = ImageDecoder.decodeBitmap(createSource, new ImageDecoder.OnHeaderDecodedListener() { // from class: com.android.systemui.statusbar.policy.SmartReplyStateInflaterKt$loadIconDrawableWithTimeout$bitmapTask$1$durationMillis$1$1
                                        @Override // android.graphics.ImageDecoder.OnHeaderDecodedListener
                                        public final void onHeaderDecoded(ImageDecoder imageDecoder, ImageDecoder.ImageInfo imageInfo, ImageDecoder.Source source) {
                                            int i6 = i5;
                                            imageDecoder.setTargetSize(i6, i6);
                                            imageDecoder.setAllocator(0);
                                        }
                                    });
                                    long currentTimeMillis2 = System.currentTimeMillis() - currentTimeMillis;
                                    if (currentTimeMillis2 > 500) {
                                        Log.w("SmartReplyViewInflater", "Loading " + icon4 + " took " + (currentTimeMillis2 / 1000.0f) + " sec");
                                    }
                                    if (decodeBitmap != null) {
                                        return decodeBitmap;
                                    }
                                    throw new IllegalStateException("ImageDecoder.decodeBitmap() returned null".toString());
                                }
                            });
                            try {
                                int i5 = Result.$r8$clinit;
                                SmartReplyStateInflaterKt.iconTaskThreadPool.execute(futureTask);
                                failure = (Bitmap) futureTask.get(500L, TimeUnit.MILLISECONDS);
                            } catch (Throwable th) {
                                int i6 = Result.$r8$clinit;
                                failure = new Result.Failure(th);
                            }
                            Throwable m2859exceptionOrNullimpl = Result.m2859exceptionOrNullimpl(failure);
                            if (m2859exceptionOrNullimpl == null) {
                                BitmapDrawable bitmapDrawable = new BitmapDrawable(contextThemeWrapper2.getResources(), (Bitmap) failure);
                                drawable = icon3.getType() == 6 ? new AdaptiveIconDrawable(null, bitmapDrawable) : bitmapDrawable;
                                if (icon3.hasTint()) {
                                    drawable.mutate();
                                    drawable.setTintList(icon3.getTintList());
                                    drawable.setTintBlendMode(icon3.getTintBlendMode());
                                }
                            } else {
                                Log.e("SmartReplyViewInflater", "Failed to load " + icon3 + ": " + m2859exceptionOrNullimpl);
                                futureTask.cancel(true);
                                drawable = null;
                            }
                        } else {
                            drawable = icon3.loadDrawable(contextThemeWrapper2);
                        }
                        if (drawable == null) {
                            drawable = new GradientDrawable();
                        }
                        drawable.setBounds(0, 0, dimensionPixelSize, dimensionPixelSize);
                        button.setCompoundDrawablesRelative(drawable, null, null, null);
                        View.OnClickListener onClickListener = new View.OnClickListener() { // from class: com.android.systemui.statusbar.policy.SmartActionInflaterImpl$inflateActionButton$1$onClickListener$1
                            @Override // android.view.View.OnClickListener
                            public final void onClick(View view) {
                                final SmartActionInflaterImpl smartActionInflaterImpl2 = SmartActionInflaterImpl.this;
                                final NotificationEntry notificationEntry3 = notificationEntry2;
                                final SmartReplyView.SmartActions smartActions5 = smartActions4;
                                final int i7 = intValue;
                                final Notification.Action action4 = action3;
                                smartActionInflaterImpl2.getClass();
                                if (smartActions5.fromAssistant && 11 == action4.getSemanticAction()) {
                                    ExpandableNotificationRow expandableNotificationRow = notificationEntry3.row;
                                    expandableNotificationRow.doSmartActionClick(((int) expandableNotificationRow.getX()) / 2, ((int) notificationEntry3.row.getY()) / 2);
                                    smartActionInflaterImpl2.smartReplyController.smartActionClicked(notificationEntry3, i7, action4, smartActions5.fromAssistant);
                                } else {
                                    PendingIntent pendingIntent = action4.actionIntent;
                                    ExpandableNotificationRow expandableNotificationRow2 = notificationEntry3.row;
                                    final Function0 function0 = new Function0() { // from class: com.android.systemui.statusbar.policy.SmartActionInflaterImpl$onSmartActionClick$1
                                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                        {
                                            super(0);
                                        }

                                        @Override // kotlin.jvm.functions.Function0
                                        public final Object invoke() {
                                            SmartActionInflaterImpl.this.smartReplyController.smartActionClicked(notificationEntry3, i7, action4, smartActions5.fromAssistant);
                                            return Unit.INSTANCE;
                                        }
                                    };
                                    ThreadPoolExecutor threadPoolExecutor2 = SmartReplyStateInflaterKt.iconTaskThreadPool;
                                    smartActionInflaterImpl2.activityStarter.startPendingIntentDismissingKeyguard(pendingIntent, new Runnable() { // from class: com.android.systemui.statusbar.policy.SmartReplyStateInflaterKt$startPendingIntentDismissingKeyguard$1
                                        @Override // java.lang.Runnable
                                        public final void run() {
                                            Function0.this.invoke();
                                        }
                                    }, expandableNotificationRow2);
                                }
                            }
                        };
                        if (z6) {
                            onClickListener = new DelayedOnClickListener(onClickListener, smartActionInflaterImpl.constants.mOnClickInitDelay);
                        }
                        button.setOnClickListener(onClickListener);
                        ((SmartReplyView.LayoutParams) button.getLayoutParams()).mButtonType = SmartReplyView.SmartButtonType.ACTION;
                        return button;
                    }
                });
            } else {
                sequence = EmptySequence.INSTANCE;
            }
            return new InflatedSmartReplyViewHolder(smartReplyView, SequencesKt___SequencesKt.toList(SequencesKt___SequencesKt.plus(transformingIndexedSequence, sequence)));
        }
        z = true;
        final boolean z52 = !z;
        SmartReplyConstants smartReplyConstants2 = this.constants;
        int i42 = SmartReplyView.MEASURE_SPEC_ANY_LENGTH;
        final SmartReplyView smartReplyView2 = (SmartReplyView) LayoutInflater.from(context).inflate(R.layout.smart_reply_view, (ViewGroup) null);
        smartReplyView2.mMaxNumActions = smartReplyConstants2.mMaxNumActions;
        smartReplyView2.mMaxSqueezeRemeasureAttempts = smartReplyConstants2.mMaxSqueezeRemeasureAttempts;
        smartReplyView2.mMinNumSystemGeneratedReplies = smartReplyConstants2.mMinNumSystemGeneratedReplies;
        final SmartReplyView.SmartReplies smartReplies32 = inflatedSmartReplyState2.smartReplies;
        smartReplyView2.mSmartRepliesGeneratedByAssistant = smartReplies32 != null ? smartReplies32.fromAssistant : false;
        Sequence transformingIndexedSequence2 = smartReplies32 == null ? new TransformingIndexedSequence(new CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1(smartReplies32.choices), new Function2() { // from class: com.android.systemui.statusbar.policy.SmartReplyStateInflaterImpl$inflateSmartReplyViewHolder$smartReplyButtons$1$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj3, Object obj4) {
                final int intValue = ((Number) obj3).intValue();
                final CharSequence charSequence = (CharSequence) obj4;
                SmartReplyInflater smartReplyInflater = SmartReplyStateInflaterImpl.this.smartRepliesInflater;
                final SmartReplyView smartReplyView22 = smartReplyView2;
                final NotificationEntry notificationEntry2 = notificationEntry;
                final SmartReplyView.SmartReplies smartReplies4 = smartReplies32;
                boolean z6 = z52;
                final SmartReplyInflaterImpl smartReplyInflaterImpl = (SmartReplyInflaterImpl) smartReplyInflater;
                smartReplyInflaterImpl.getClass();
                final Button button = (Button) LayoutInflater.from(smartReplyView22.getContext()).inflate(R.layout.smart_reply_button, (ViewGroup) smartReplyView22, false);
                button.setText(charSequence);
                View.OnClickListener onClickListener = new View.OnClickListener() { // from class: com.android.systemui.statusbar.policy.SmartReplyInflaterImpl$inflateReplyButton$1$onClickListener$1
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        final SmartReplyInflaterImpl smartReplyInflaterImpl2 = SmartReplyInflaterImpl.this;
                        final NotificationEntry notificationEntry3 = notificationEntry2;
                        final SmartReplyView.SmartReplies smartReplies5 = smartReplies4;
                        final int i5 = intValue;
                        final SmartReplyView smartReplyView3 = smartReplyView22;
                        final Button button2 = button;
                        final CharSequence charSequence2 = charSequence;
                        smartReplyInflaterImpl2.getClass();
                        boolean z7 = !notificationEntry3.isRowPinned();
                        final Function0 function0 = new Function0() { // from class: com.android.systemui.statusbar.policy.SmartReplyInflaterImpl$onSmartReplyClick$1
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(0);
                            }

                            @Override // kotlin.jvm.functions.Function0
                            public final Object invoke() {
                                boolean booleanValue;
                                INotificationManager iNotificationManager;
                                SmartReplyConstants smartReplyConstants22 = SmartReplyInflaterImpl.this.constants;
                                int editChoicesBeforeSending = smartReplies5.remoteInput.getEditChoicesBeforeSending();
                                smartReplyConstants22.getClass();
                                boolean z8 = true;
                                if (editChoicesBeforeSending == 1) {
                                    z8 = false;
                                } else if (editChoicesBeforeSending != 2) {
                                    z8 = smartReplyConstants22.mEditChoicesBeforeSending;
                                }
                                if (z8) {
                                    NotificationRemoteInputManager notificationRemoteInputManager = SmartReplyInflaterImpl.this.remoteInputManager;
                                    Button button3 = button2;
                                    SmartReplyView.SmartReplies smartReplies6 = smartReplies5;
                                    RemoteInput remoteInput3 = smartReplies6.remoteInput;
                                    notificationRemoteInputManager.activateRemoteInput(button3, new RemoteInput[]{remoteInput3}, remoteInput3, smartReplies6.pendingIntent, new NotificationEntry.EditedSuggestionInfo(charSequence2, i5), null);
                                } else {
                                    SmartReplyController smartReplyController = SmartReplyInflaterImpl.this.smartReplyController;
                                    NotificationEntry notificationEntry4 = notificationEntry3;
                                    int i6 = i5;
                                    CharSequence text = button2.getText();
                                    int metricsEventEnum = NotificationLogger.getNotificationLocation(notificationEntry3).toMetricsEventEnum();
                                    RemoteInputCoordinator remoteInputCoordinator = smartReplyController.mCallback.$tmp0;
                                    remoteInputCoordinator.getClass();
                                    booleanValue = ((Boolean) RemoteInputCoordinatorKt.DEBUG$delegate.getValue()).booleanValue();
                                    String str = notificationEntry4.mKey;
                                    if (booleanValue) {
                                        AbstractC0689x6838b71d.m68m("onSmartReplySent(entry=", str, ")", "RemoteInputCoordinator");
                                    }
                                    StatusBarNotification rebuildWithRemoteInputInserted = remoteInputCoordinator.mRebuilder.rebuildWithRemoteInputInserted(notificationEntry4, text, true, null, null);
                                    NotifCollection$$ExternalSyntheticLambda4 notifCollection$$ExternalSyntheticLambda4 = remoteInputCoordinator.mNotifUpdater;
                                    if (notifCollection$$ExternalSyntheticLambda4 == null) {
                                        notifCollection$$ExternalSyntheticLambda4 = null;
                                    }
                                    notifCollection$$ExternalSyntheticLambda4.onInternalNotificationUpdate("Adding smart reply spinner for sent", rebuildWithRemoteInputInserted);
                                    remoteInputCoordinator.mRemoteInputActiveExtender.endLifetimeExtensionAfterDelay(str, 500L);
                                    ((ArraySet) smartReplyController.mSendingKeys).add(notificationEntry4.mKey);
                                    try {
                                        smartReplyController.mBarService.onNotificationSmartReplySent(notificationEntry4.mSbn.getKey(), i6, text, metricsEventEnum, false);
                                        SystemUIAnalytics.sendEventCDLog("QPN001", "QPNE0011", "type", "reply texts", "app", notificationEntry4.mSbn.getPackageName());
                                    } catch (RemoteException unused) {
                                    }
                                    NotificationEntry notificationEntry5 = notificationEntry3;
                                    notificationEntry5.hasSentReply = true;
                                    try {
                                        if (NotiRune.NOTI_SUBSCREEN_SUPPORT_NOTIFICATION_HISTORY && (iNotificationManager = SmartReplyInflaterImpl.this.notifManager) != null) {
                                            iNotificationManager.addReplyHistory(1, notificationEntry5.mKey, notificationEntry5.mSbn.getPackageName(), notificationEntry3.mSbn.getUser().getIdentifier(), "NOUI_2023", button2.getText().toString());
                                        }
                                        smartReplies5.pendingIntent.send(SmartReplyInflaterImpl.this.context, 0, SmartReplyInflaterImpl.access$createRemoteInputIntent(SmartReplyInflaterImpl.this, smartReplies5, charSequence2));
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
                                return ((Boolean) Function0.this.invoke()).booleanValue();
                            }
                        }, z7, false);
                    }
                };
                if (z6) {
                    onClickListener = new DelayedOnClickListener(onClickListener, smartReplyInflaterImpl.constants.mOnClickInitDelay);
                }
                button.setOnClickListener(onClickListener);
                button.setAccessibilityDelegate(new View.AccessibilityDelegate() { // from class: com.android.systemui.statusbar.policy.SmartReplyInflaterImpl$inflateReplyButton$1$1
                    @Override // android.view.View.AccessibilityDelegate
                    public final void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfo accessibilityNodeInfo) {
                        super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
                        accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(16, SmartReplyView.this.getResources().getString(R.string.accessibility_send_smart_reply)));
                    }
                });
                ((SmartReplyView.LayoutParams) button.getLayoutParams()).mButtonType = SmartReplyView.SmartButtonType.REPLY;
                return button;
            }
        }) : EmptySequence.INSTANCE;
        smartActions = inflatedSmartReplyState2.smartActions;
        if (smartActions == null) {
        }
        return new InflatedSmartReplyViewHolder(smartReplyView2, SequencesKt___SequencesKt.toList(SequencesKt___SequencesKt.plus(transformingIndexedSequence2, sequence)));
    }
}
