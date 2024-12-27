package com.android.systemui.statusbar.notification;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.ActivityOptions;
import android.app.ActivityTaskManager;
import android.app.KeyguardManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.Icon;
import android.graphics.drawable.VectorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.RemoteException;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.Settings;
import android.service.notification.StatusBarNotification;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.util.Property;
import android.view.ContextThemeWrapper;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.widget.Button;
import android.widget.DateTimeView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.appcompat.app.AlertController$$ExternalSyntheticOutline0;
import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import androidx.compose.animation.graphics.vector.PropertyValuesHolder2D$$ExternalSyntheticOutline0;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.dynamicanimation.animation.SpringAnimation;
import androidx.dynamicanimation.animation.SpringForce;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import androidx.picker.adapter.AbsAdapter$1$$ExternalSyntheticOutline0;
import androidx.picker3.widget.SeslColorSpectrumView$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView;
import com.airbnb.lottie.LottieAnimationView;
import com.android.app.animation.Interpolators;
import com.android.keyguard.KeyguardBiometricLockoutLogger$mKeyguardUpdateMonitorCallback$1$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardCarrierViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.StrongAuthPopup$$ExternalSyntheticOutline0;
import com.android.settingslib.utils.ThreadUtils;
import com.android.systemui.Dependency;
import com.android.systemui.NotiRune;
import com.android.systemui.R;
import com.android.systemui.bixby2.controller.NotificationController;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.plugins.subscreen.SubRoom;
import com.android.systemui.popup.util.PopupUIUtil;
import com.android.systemui.qp.util.SubscreenUtil;
import com.android.systemui.settings.UserContextProvider;
import com.android.systemui.statusbar.NotificationLockscreenUserManager;
import com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl;
import com.android.systemui.statusbar.notification.SubscreenDeviceModelB5;
import com.android.systemui.statusbar.notification.SubscreenNotificationDetailAdapter;
import com.android.systemui.statusbar.notification.SubscreenNotificationGroupAdapter;
import com.android.systemui.statusbar.notification.SubscreenNotificationInfo;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.inflation.BindEventManager;
import com.android.systemui.statusbar.notification.collection.notifcollection.CommonNotifCollection;
import com.android.systemui.statusbar.notification.collection.render.NotificationVisibilityProvider;
import com.android.systemui.statusbar.notification.interruption.NotificationInterruptStateProvider;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.stack.NotificationChildrenContainer;
import com.android.systemui.statusbar.phone.CentralSurfaces;
import com.android.systemui.statusbar.phone.CentralSurfacesImpl;
import com.android.systemui.statusbar.phone.StatusBarNotificationActivityStarter;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.subscreen.SubHomeActivity;
import com.android.systemui.subscreen.SubScreenManager;
import com.android.systemui.util.DeviceState;
import com.android.systemui.util.DeviceType;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.SystemUIAnalytics;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.samsung.android.desktopsystemui.sharedlib.system.ActivityManagerWrapper;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;
import com.samsung.android.sdk.scs.ai.translation.NeuralTranslator;
import com.samsung.android.sdk.scs.base.tasks.OnCompleteListener;
import com.samsung.android.sdk.scs.base.tasks.Task;
import com.samsung.android.sdk.scs.base.tasks.TaskImpl;
import com.sec.ims.volte2.data.VolteConstants;
import dagger.Lazy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import kotlin.NotImplementedError;
import kotlin.Result;
import kotlin.Unit;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref$IntRef;
import kotlin.jvm.internal.Ref$ObjectRef;
import kotlin.jvm.internal.StringCompanionObject;
import kotlin.text.Regex;
import kotlin.text.StringsKt__StringsJVMKt;
import kotlin.text.StringsKt__StringsKt;
import noticolorpicker.NotificationColorPicker;
import notification.src.com.android.systemui.BasePromptProcessor;
import notification.src.com.android.systemui.CloudPromptProcessor;
import notification.src.com.android.systemui.PromptCallback;
import notification.src.com.android.systemui.SrPromptProcessor;

public final class SubscreenDeviceModelB5 extends SubscreenDeviceModelCommon {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final String DISPLAY_LANG_CODE_DELIMITER;
    public final String SR_LLM_PACKAGE_NAME;
    public ImageView aiDisclaimerBtn;
    public final SubscreenDeviceModelB5$aodTspUpdateReceiver$1 aodTspUpdateReceiver;
    public final SubscreenDeviceModelB5$broadcastReceiver$1 broadcastReceiver;
    public TextView callBackButtonText;
    public int childGraduateAge;
    public TextView clearButtonText;
    public Account currentAccount;
    public LinearLayout detailButtonContainer;
    public SubscreenNotificationDetailAdapter.ItemViewHolder detailViewHolder;
    public boolean isAiInfoConfirmed;
    public boolean isChildAccount;
    public final boolean isDebug;
    public boolean isPossibleAiReply;
    public boolean isRDUMode;
    public boolean isSALoggedIn;
    public boolean isSuggestResponsesEnabled;
    public boolean isUnusableAccount;
    public ImageView keyboardReplyButton;
    public SubscreenNotificationDialog mDialog;
    public View mHeaderViewLayout;
    public boolean mIsClickedPopupKeyguardUnlockShowing;
    public boolean mIsContentScroll;
    public boolean mIsNaviBarBackButtonClicked;
    public boolean mIsReplySendButtonLoading;
    public boolean mIsStartedReplyActivity;
    public final KeyguardActionInfo mKeyguardActionInfo;
    public SpringAnimation mProgressScaleAnimationX;
    public SpringAnimation mProgressScaleAnimationY;
    public final StringBuilder mPromptSB;
    public final StringBuilder mPromptSBForLog;
    public float mReplyLayoutCurrentPostionY;
    public boolean mSmartReplyClickedByUser;
    public final LinkedHashMap mSmartReplyHashMap;
    public int mSmartReplyResult;
    public StringBuilder mSmartReplyResultCompleteMsg;
    public String mSmartReplyResultFailureMsg;
    public final BasePromptProcessor mSrPromptProcessor;
    public final SubscreenDeviceModelB5$mSrResponseCallback$1 mSrResponseCallback;
    public String mUnlockNotificationPendingIntentItemKey;
    public String metaData;
    public final List onDeviceLanguageList;
    public TextView openAppButtonText;
    public final SubscreenDeviceModelB5$pkgBroadcastReceiver$1 pkgBroadcastReceiver;
    public LinearLayout progressLayout;
    public LottieAnimationView progressingVi;
    public TextView replyButtonText;
    public PopupWindow sendButtonPopupWindow;
    public TextView smartReplyAiLogoText;
    public TextView smartReplyErrorMessageView;
    public int smartReplyStatus;
    public ImageView smartReplyTriggerBtn;
    public TextView smartReplyTriggerBtnText;
    public LinearLayout suggestResponsesBtn;
    public CharSequence titleText;

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public final class KeyguardActionInfo {
        public boolean isShowBouncer;
        public int mAction;
        public Context mContext;
        public SubscreenNotificationDetailAdapter.ItemViewHolder mDetailAdapterItemViewHolder;
        public NotificationEntry mEntry;
        public SubscreenParentItemViewHolder mSubscreenParentItemViewHolder;

        public final class Companion {
            private Companion() {
            }

            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }
        }

        static {
            new Companion(null);
        }
    }

    public final class LlmLanguage {
        public String language;
        public String languageDisplayName;
        public int order;
        public boolean supportCorrection;
        public boolean supportReply;
        public boolean supportToneConversion;

        public LlmLanguage() {
            this(0, null, null, false, false, false, 63, null);
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof LlmLanguage)) {
                return false;
            }
            LlmLanguage llmLanguage = (LlmLanguage) obj;
            return this.order == llmLanguage.order && Intrinsics.areEqual(this.language, llmLanguage.language) && Intrinsics.areEqual(this.languageDisplayName, llmLanguage.languageDisplayName) && this.supportToneConversion == llmLanguage.supportToneConversion && this.supportCorrection == llmLanguage.supportCorrection && this.supportReply == llmLanguage.supportReply;
        }

        public final int hashCode() {
            return Boolean.hashCode(this.supportReply) + TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(Integer.hashCode(this.order) * 31, 31, this.language), 31, this.languageDisplayName), 31, this.supportToneConversion), 31, this.supportCorrection);
        }

        public final String toString() {
            int i = this.order;
            String str = this.language;
            String str2 = this.languageDisplayName;
            boolean z = this.supportToneConversion;
            boolean z2 = this.supportCorrection;
            boolean z3 = this.supportReply;
            StringBuilder m = KeyguardBiometricLockoutLogger$mKeyguardUpdateMonitorCallback$1$$ExternalSyntheticOutline0.m(i, "LlmLanguage(order=", ", language=", str, ", languageDisplayName=");
            m.append(str2);
            m.append(", supportToneConversion=");
            m.append(z);
            m.append(", supportCorrection=");
            m.append(z2);
            m.append(", supportReply=");
            m.append(z3);
            m.append(")");
            return m.toString();
        }

        public LlmLanguage(int i, String str, String str2, boolean z, boolean z2, boolean z3) {
            this.order = i;
            this.language = str;
            this.languageDisplayName = str2;
            this.supportToneConversion = z;
            this.supportCorrection = z2;
            this.supportReply = z3;
        }

        public /* synthetic */ LlmLanguage(int i, String str, String str2, boolean z, boolean z2, boolean z3, int i2, DefaultConstructorMarker defaultConstructorMarker) {
            this((i2 & 1) != 0 ? -1 : i, (i2 & 2) != 0 ? "" : str, (i2 & 4) == 0 ? str2 : "", (i2 & 8) != 0 ? true : z, (i2 & 16) != 0 ? true : z2, (i2 & 32) != 0 ? true : z3);
        }
    }

    public final class SmartReplyData {
        public String prevPrompt;
        public String replyText;
    }

    static {
        new Companion(null);
    }

    /* JADX WARN: Type inference failed for: r1v19, types: [com.android.systemui.statusbar.notification.SubscreenDeviceModelB5$mSrResponseCallback$1] */
    /* JADX WARN: Type inference failed for: r4v0, types: [com.android.systemui.statusbar.notification.SubscreenDeviceModelB5$aodTspUpdateReceiver$1] */
    public SubscreenDeviceModelB5(Context context, KeyguardUpdateMonitor keyguardUpdateMonitor, SettingsHelper settingsHelper, UserContextProvider userContextProvider, SubscreenNotificationController subscreenNotificationController, Lazy lazy, CommonNotifCollection commonNotifCollection, LogBuffer logBuffer, NotificationInterruptStateProvider notificationInterruptStateProvider, Lazy lazy2, Lazy lazy3, NotificationVisibilityProvider notificationVisibilityProvider, BindEventManager bindEventManager, NotificationController notificationController, UserManager userManager, ConversationNotificationManager conversationNotificationManager) {
        super(context, keyguardUpdateMonitor, settingsHelper, userContextProvider, subscreenNotificationController, lazy, commonNotifCollection, logBuffer, notificationInterruptStateProvider, lazy2, lazy3, notificationVisibilityProvider, bindEventManager, notificationController, userManager, conversationNotificationManager);
        this.isDebug = DeviceType.getDebugLevel() == DeviceType.DEBUG_LEVEL_MID || DeviceType.getDebugLevel() == DeviceType.DEBUG_LEVEL_HIGH;
        this.mIsContentScroll = true;
        this.titleText = "";
        this.mPromptSB = new StringBuilder();
        this.mPromptSBForLog = new StringBuilder();
        this.mSmartReplyHashMap = new LinkedHashMap();
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() { // from class: com.android.systemui.statusbar.notification.SubscreenDeviceModelB5$broadcastReceiver$1
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                SubscreenNotificationDetailAdapter subscreenNotificationDetailAdapter;
                Log.d("S.S.N.", "receive " + intent.getAction());
                if (Intrinsics.areEqual(intent.getAction(), PopupUIUtil.ACTION_CLOSE_SYSTEM_DIALOGS)) {
                    if (Intrinsics.areEqual(intent.getStringExtra("reason"), ActivityManagerWrapper.CLOSE_SYSTEM_WINDOWS_REASON_HOME_KEY)) {
                        SubscreenSubRoomNotification subscreenSubRoomNotification = SubscreenDeviceModelB5.this.mSubRoomNotification;
                        if (subscreenSubRoomNotification != null && (subscreenNotificationDetailAdapter = subscreenSubRoomNotification.mNotificationDetailAdapter) != null) {
                            subscreenNotificationDetailAdapter.dismissReplyButtons(true);
                        }
                        SubscreenDeviceModelB5.this.closeFullscreenFullPopupWindow();
                        return;
                    }
                    return;
                }
                if (Intrinsics.areEqual(intent.getAction(), "com.samsung.android.action.UNLOCK_NOTIFICATION_PENDING_INTENT")) {
                    SubscreenDeviceModelB5 subscreenDeviceModelB5 = SubscreenDeviceModelB5.this;
                    if (subscreenDeviceModelB5.mIsFolded) {
                        return;
                    }
                    subscreenDeviceModelB5.mUnlockNotificationPendingIntentItemKey = intent.getStringExtra("key");
                    return;
                }
                if (Intrinsics.areEqual(intent.getAction(), "com.samsung.android.action.INTELLIGENCE_SERVICE_SETTINGS_START_INTENT")) {
                    if (SubscreenDeviceModelB5.this.mIsFolded) {
                        return;
                    }
                    Intent intent2 = new Intent();
                    intent2.setFlags(335544320);
                    intent2.setClassName("com.android.systemui", "com.android.systemui.statusbar.notification.SubscreenNotificationIntelligenceStartActivity");
                    SubscreenDeviceModelB5.this.mContext.startActivityAsUser(intent2, UserHandle.CURRENT);
                    return;
                }
                if (!Intrinsics.areEqual(intent.getAction(), "com.samsung.android.action.INTELLIGENCE_SERVICE_PROCESSING_ONLINE_INTENT") || SubscreenDeviceModelB5.this.mIsFolded) {
                    return;
                }
                Intent intent3 = new Intent();
                intent3.setFlags(335544320);
                intent3.setAction("com.samsung.android.settings.action.INTELLIGENCE_SERVICE_GLOBAL_SETTINGS");
                intent3.setPackage(KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG);
                Bundle bundle = new Bundle();
                bundle.putString(":settings:fragment_args_key", "prevent_online_processing");
                intent3.putExtra(":settings:show_fragment_args", bundle);
                SubscreenDeviceModelB5.this.mContext.startActivityAsUser(intent3, UserHandle.CURRENT);
            }
        };
        BroadcastReceiver broadcastReceiver2 = new BroadcastReceiver() { // from class: com.android.systemui.statusbar.notification.SubscreenDeviceModelB5$pkgBroadcastReceiver$1
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                String dataString;
                Log.d("S.S.N.", "receive " + intent.getAction());
                if ((Intrinsics.areEqual(intent.getAction(), "android.intent.action.PACKAGE_ADDED") || Intrinsics.areEqual(intent.getAction(), "android.intent.action.PACKAGE_REPLACED") || Intrinsics.areEqual(intent.getAction(), "android.intent.action.PACKAGE_REMOVED")) && (dataString = intent.getDataString()) != null && StringsKt__StringsKt.contains(dataString, SubscreenDeviceModelB5.this.SR_LLM_PACKAGE_NAME, false)) {
                    Log.d("S.S.N.", "package intent received - loadOnDeviceMetaData again");
                    SubscreenDeviceModelB5 subscreenDeviceModelB5 = SubscreenDeviceModelB5.this;
                    subscreenDeviceModelB5.metaData = null;
                    subscreenDeviceModelB5.loadOnDeviceMetaData();
                }
            }
        };
        this.aodTspUpdateReceiver = new BroadcastReceiver() { // from class: com.android.systemui.statusbar.notification.SubscreenDeviceModelB5$aodTspUpdateReceiver$1
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                Integer valueOf = intent != null ? Integer.valueOf(intent.getIntExtra("info", -1)) : null;
                float[] floatArrayExtra = intent != null ? intent.getFloatArrayExtra("location") : null;
                Log.d("S.S.N.", "aodTspUpdateReceiver onReceive() action = " + (intent != null ? intent.getAction() : null) + ", info = " + valueOf);
                if (valueOf == null || valueOf.intValue() != 11) {
                    Log.d("S.S.N.", "aodTspUpdateReceiver onReceive() return - not double tap");
                    return;
                }
                if (floatArrayExtra != null) {
                    SubscreenDeviceModelB5 subscreenDeviceModelB5 = SubscreenDeviceModelB5.this;
                    if (floatArrayExtra.length != 2) {
                        Log.d("S.S.N.", "aodTspUpdateReceiver onReceive() return - There is no [x,y position] value");
                        return;
                    }
                    NotificationEntry notificationEntry = subscreenDeviceModelB5.currentPresentationEntry;
                    String str = notificationEntry != null ? notificationEntry.mKey : null;
                    float f = floatArrayExtra[0];
                    float f2 = floatArrayExtra[1];
                    StringBuilder sb = new StringBuilder("aodTspUpdateReceiver onReceive() - detailclicked(");
                    sb.append(str);
                    sb.append("), loc = ");
                    sb.append(f);
                    sb.append(" : ");
                    SeslColorSpectrumView$$ExternalSyntheticOutline0.m(sb, f2, "S.S.N.");
                    subscreenDeviceModelB5.mIsContentScroll = true;
                    subscreenDeviceModelB5.detailClicked(subscreenDeviceModelB5.currentPresentationEntry);
                }
            }
        };
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(PopupUIUtil.ACTION_CLOSE_SYSTEM_DIALOGS);
        intentFilter.addAction("com.samsung.android.action.UNLOCK_NOTIFICATION_PENDING_INTENT");
        intentFilter.addAction("com.samsung.android.action.INTELLIGENCE_SERVICE_SETTINGS_START_INTENT");
        boolean z = NotiRune.NOTI_SUBSCREEN_SUPPORT_SMART_REPLY_AI_FOR_CHINA;
        if (z) {
            intentFilter.addAction("com.samsung.android.action.INTELLIGENCE_SERVICE_PROCESSING_ONLINE_INTENT");
        }
        UserHandle userHandle = UserHandle.ALL;
        context.registerReceiverAsUser(broadcastReceiver, userHandle, intentFilter, null, null, 2);
        IntentFilter intentFilter2 = new IntentFilter();
        intentFilter2.addAction("android.intent.action.PACKAGE_ADDED");
        intentFilter2.addAction("android.intent.action.PACKAGE_REPLACED");
        intentFilter2.addAction("android.intent.action.PACKAGE_REMOVED");
        intentFilter2.addDataScheme("package");
        context.registerReceiverAsUser(broadcastReceiver2, userHandle, intentFilter2, null, null, 2);
        KeyguardStateController keyguardStateController = (KeyguardStateController) Dependency.sDependency.getDependencyInner(KeyguardStateController.class);
        this.mKeyguardStateController = keyguardStateController;
        if (keyguardStateController != null) {
            ((KeyguardStateControllerImpl) keyguardStateController).addCallback(new KeyguardStateController.Callback() { // from class: com.android.systemui.statusbar.notification.SubscreenDeviceModelB5$initKeyguardStateConroller$1
                @Override // com.android.systemui.statusbar.policy.KeyguardStateController.Callback
                public final void onKeyguardShowingChanged() {
                    KeyguardStateController keyguardStateController2;
                    NotificationEntry entry;
                    SubscreenParentItemViewHolder subscreenParentItemViewHolder;
                    SubscreenParentItemViewHolder subscreenParentItemViewHolder2;
                    SubscreenNotificationInfo subscreenNotificationInfo;
                    ExpandableNotificationRow expandableNotificationRow;
                    SubscreenSubRoomNotification subscreenSubRoomNotification;
                    SubscreenNotificationDetailAdapter subscreenNotificationDetailAdapter;
                    SubscreenNotificationDetailAdapter subscreenNotificationDetailAdapter2;
                    KeyguardStateController keyguardStateController3;
                    SubscreenDeviceModelB5 subscreenDeviceModelB5 = SubscreenDeviceModelB5.this;
                    SubscreenNotificationDialog subscreenNotificationDialog = subscreenDeviceModelB5.mDialog;
                    if (subscreenNotificationDialog != null) {
                        subscreenNotificationDialog.dismiss();
                    }
                    if (subscreenDeviceModelB5.mIsFolded) {
                        KeyguardStateController keyguardStateController4 = subscreenDeviceModelB5.mKeyguardStateController;
                        Boolean valueOf = keyguardStateController4 != null ? Boolean.valueOf(((KeyguardStateControllerImpl) keyguardStateController4).mSecure) : null;
                        KeyguardStateController keyguardStateController5 = subscreenDeviceModelB5.mKeyguardStateController;
                        Boolean valueOf2 = keyguardStateController5 != null ? Boolean.valueOf(((KeyguardStateControllerImpl) keyguardStateController5).mShowing) : null;
                        KeyguardStateController keyguardStateController6 = subscreenDeviceModelB5.mKeyguardStateController;
                        Boolean valueOf3 = keyguardStateController6 != null ? Boolean.valueOf(keyguardStateController6.isUnlocked()) : null;
                        SubscreenDeviceModelB5.KeyguardActionInfo keyguardActionInfo = subscreenDeviceModelB5.mKeyguardActionInfo;
                        Log.d("S.S.N.", " onKeyguardShowingChanged() isMethodSecure : " + valueOf + ", isShowing: " + valueOf2 + ", isUnlocked : " + valueOf3 + ", getAction() : " + (keyguardActionInfo != null ? Integer.valueOf(keyguardActionInfo.mAction) : null));
                        if (subscreenDeviceModelB5.isKeyguardStats()) {
                            subscreenDeviceModelB5.clearMainList();
                        } else {
                            SubscreenDeviceModelB5.KeyguardActionInfo keyguardActionInfo2 = subscreenDeviceModelB5.mKeyguardActionInfo;
                            if (keyguardActionInfo2 != null && keyguardActionInfo2.isShowBouncer && (keyguardStateController3 = subscreenDeviceModelB5.mKeyguardStateController) != null && !((KeyguardStateControllerImpl) keyguardStateController3).mShowing) {
                                keyguardActionInfo2.isShowBouncer = false;
                            }
                            if (subscreenDeviceModelB5.isShownDetail() && (subscreenSubRoomNotification = subscreenDeviceModelB5.mSubRoomNotification) != null && (subscreenNotificationDetailAdapter = subscreenSubRoomNotification.mNotificationDetailAdapter) != null && subscreenNotificationDetailAdapter.mCallbackClicked) {
                                subscreenDeviceModelB5.hideDetailNotification();
                                SubscreenSubRoomNotification subscreenSubRoomNotification2 = subscreenDeviceModelB5.mSubRoomNotification;
                                if (subscreenSubRoomNotification2 != null && (subscreenNotificationDetailAdapter2 = subscreenSubRoomNotification2.mNotificationDetailAdapter) != null) {
                                    subscreenNotificationDetailAdapter2.cleanAdapter();
                                }
                            }
                            SubscreenDeviceModelB5.KeyguardActionInfo keyguardActionInfo3 = subscreenDeviceModelB5.mKeyguardActionInfo;
                            Integer valueOf4 = keyguardActionInfo3 != null ? Integer.valueOf(keyguardActionInfo3.mAction) : null;
                            if (valueOf4 != null && valueOf4.intValue() == 4) {
                                SubscreenDeviceModelB5.KeyguardActionInfo keyguardActionInfo4 = subscreenDeviceModelB5.mKeyguardActionInfo;
                                if ((keyguardActionInfo4 != null ? keyguardActionInfo4.mEntry : null) != null) {
                                    boolean clickKnoxItem = subscreenDeviceModelB5.clickKnoxItem(keyguardActionInfo4 != null ? keyguardActionInfo4.mEntry : null);
                                    if (clickKnoxItem) {
                                        KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m(" isClickedKnoxItem :", "S.S.N.", clickKnoxItem);
                                        SubscreenDeviceModelB5.KeyguardActionInfo keyguardActionInfo5 = subscreenDeviceModelB5.mKeyguardActionInfo;
                                        subscreenDeviceModelB5.dismissImmediately(keyguardActionInfo5 != null ? keyguardActionInfo5.mEntry : null);
                                    } else {
                                        SubscreenDeviceModelB5.KeyguardActionInfo keyguardActionInfo6 = subscreenDeviceModelB5.mKeyguardActionInfo;
                                        subscreenDeviceModelB5.detailClicked(keyguardActionInfo6 != null ? keyguardActionInfo6.mEntry : null);
                                    }
                                }
                            } else if (valueOf4 != null && valueOf4.intValue() == 1) {
                                SubscreenDeviceModelB5.KeyguardActionInfo keyguardActionInfo7 = subscreenDeviceModelB5.mKeyguardActionInfo;
                                if ((keyguardActionInfo7 != null ? keyguardActionInfo7.mSubscreenParentItemViewHolder : null) != null) {
                                    boolean clickKnoxItem2 = subscreenDeviceModelB5.clickKnoxItem((keyguardActionInfo7 == null || (subscreenParentItemViewHolder2 = keyguardActionInfo7.mSubscreenParentItemViewHolder) == null || (subscreenNotificationInfo = subscreenParentItemViewHolder2.mInfo) == null || (expandableNotificationRow = subscreenNotificationInfo.mRow) == null) ? null : expandableNotificationRow.mEntry);
                                    if (clickKnoxItem2) {
                                        KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m(" isClickedKnoxItem :", "S.S.N.", clickKnoxItem2);
                                    } else {
                                        SubscreenDeviceModelB5.KeyguardActionInfo keyguardActionInfo8 = subscreenDeviceModelB5.mKeyguardActionInfo;
                                        if (keyguardActionInfo8 != null && (subscreenParentItemViewHolder = keyguardActionInfo8.mSubscreenParentItemViewHolder) != null) {
                                            SubscreenSubRoomNotification subscreenSubRoomNotification3 = subscreenDeviceModelB5.mSubRoomNotification;
                                            subscreenParentItemViewHolder.animateClickNotification(subscreenSubRoomNotification3 != null ? subscreenSubRoomNotification3.mNotificationAnimatorManager : null, subscreenSubRoomNotification3, false);
                                        }
                                    }
                                }
                            } else if (valueOf4 != null && valueOf4.intValue() == 2) {
                                System.out.println((Object) "ACTION_KEYGUARD_BIO_LIST_HIDE_CONTENT");
                            } else if (valueOf4 != null && valueOf4.intValue() == 3) {
                                SubscreenDeviceModelB5.KeyguardActionInfo keyguardActionInfo9 = subscreenDeviceModelB5.mKeyguardActionInfo;
                                Context context2 = keyguardActionInfo9 != null ? keyguardActionInfo9.mContext : null;
                                Intrinsics.checkNotNull(context2);
                                SubscreenDeviceModelB5.KeyguardActionInfo keyguardActionInfo10 = subscreenDeviceModelB5.mKeyguardActionInfo;
                                SubscreenNotificationDetailAdapter.ItemViewHolder itemViewHolder = keyguardActionInfo10 != null ? keyguardActionInfo10.mDetailAdapterItemViewHolder : null;
                                Intrinsics.checkNotNull(itemViewHolder);
                                SubscreenDeviceModelB5.access$showReplyActivity(subscreenDeviceModelB5, context2, itemViewHolder);
                            }
                        }
                    } else {
                        String str = subscreenDeviceModelB5.mUnlockNotificationPendingIntentItemKey;
                        if (str != null && (keyguardStateController2 = subscreenDeviceModelB5.mKeyguardStateController) != null && !((KeyguardStateControllerImpl) keyguardStateController2).mShowing && (entry = ((NotifPipeline) subscreenDeviceModelB5.mNotifCollection).mNotifCollection.getEntry(str)) != null && entry.row != null) {
                            ExifInterface$$ExternalSyntheticOutline0.m(new StringBuilder("Unlock click notification : "), entry.mKey, "S.S.N.");
                            NotificationActivityStarter notificationActivityStarter = subscreenDeviceModelB5.mNotificationActivityStarter;
                            if (notificationActivityStarter != null) {
                                ((StatusBarNotificationActivityStarter) notificationActivityStarter).onNotificationClicked(entry, entry.row);
                            }
                        }
                        subscreenDeviceModelB5.mUnlockNotificationPendingIntentItemKey = null;
                    }
                    boolean z2 = subscreenDeviceModelB5.mIsClickedPopupKeyguardUnlockShowing;
                    if (z2) {
                        KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m("onKeyguardShowingChanged - mIsClickedPopupKeyguardUnlockShowing : ", "S.S.N.", z2);
                        return;
                    }
                    SubscreenDeviceModelB5.KeyguardActionInfo keyguardActionInfo11 = subscreenDeviceModelB5.mKeyguardActionInfo;
                    if (keyguardActionInfo11 != null) {
                        keyguardActionInfo11.mAction = 0;
                        keyguardActionInfo11.mSubscreenParentItemViewHolder = null;
                        keyguardActionInfo11.mContext = null;
                    }
                }
            });
        }
        this.mKeyguardActionInfo = new KeyguardActionInfo();
        updateSmartReplyVariables();
        this.onDeviceLanguageList = new ArrayList();
        this.SR_LLM_PACKAGE_NAME = "com.samsung.android.offline.languagemodel";
        this.DISPLAY_LANG_CODE_DELIMITER = "-";
        this.isSuggestResponsesEnabled = true;
        this.mSmartReplyResult = -1;
        this.mSrPromptProcessor = z ? new CloudPromptProcessor(this.mContext) : new SrPromptProcessor(this.mContext);
        this.mSrResponseCallback = new PromptCallback() { // from class: com.android.systemui.statusbar.notification.SubscreenDeviceModelB5$mSrResponseCallback$1
            @Override // notification.src.com.android.systemui.PromptCallback
            public final void onComplete(StringBuilder sb) {
                SubscreenNotificationDetailAdapter subscreenNotificationDetailAdapter;
                SubscreenNotificationInfo subscreenNotificationInfo;
                Log.d("S.S.N.", "SrPromptProcessor onComplete()");
                SubscreenDeviceModelB5 subscreenDeviceModelB5 = SubscreenDeviceModelB5.this;
                String notificationKey = subscreenDeviceModelB5.mSrPromptProcessor.getNotificationKey();
                SubscreenNotificationDetailAdapter.ItemViewHolder itemViewHolder = subscreenDeviceModelB5.detailViewHolder;
                if (!Intrinsics.areEqual(notificationKey, (itemViewHolder == null || (subscreenNotificationInfo = itemViewHolder.mInfo) == null) ? null : subscreenNotificationInfo.mKey)) {
                    Log.d("S.S.N.", "SrPromptProcessor onComplete() - detail notification key does not match");
                    return;
                }
                subscreenDeviceModelB5.enableSmartReplyTriggerBtn("", true);
                if (subscreenDeviceModelB5.smartReplyStatus != 2) {
                    Log.d("S.S.N.", "SrPromptProcessor onComplete() : SmartReplayStatus is not valid");
                    TextView textView = subscreenDeviceModelB5.smartReplyErrorMessageView;
                    if (textView != null) {
                        textView.setVisibility(8);
                    }
                }
                subscreenDeviceModelB5.smartReplyStatus = 0;
                SubscreenSubRoomNotification subscreenSubRoomNotification = subscreenDeviceModelB5.mSubRoomNotification;
                if (subscreenSubRoomNotification == null || (subscreenNotificationDetailAdapter = subscreenSubRoomNotification.mNotificationDetailAdapter) == null || !subscreenNotificationDetailAdapter.mIsShownReplyButtonWindow) {
                    subscreenDeviceModelB5.showSmartReplyResultComplete(sb);
                } else {
                    Log.d("S.S.N.", "SrPromptProcessor onComplete() - isShownReplyButtonWindow");
                    subscreenDeviceModelB5.setSmartReplyResultValue(0, null, sb);
                }
            }

            @Override // notification.src.com.android.systemui.PromptCallback
            public final void onFailure(String str) {
                LinearLayout linearLayout;
                SubscreenNotificationDetailAdapter subscreenNotificationDetailAdapter;
                MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("SrPromptProcessor onFailure() : ", str, "S.S.N.");
                SubscreenDeviceModelB5 subscreenDeviceModelB5 = SubscreenDeviceModelB5.this;
                if (subscreenDeviceModelB5.smartReplyStatus != 2) {
                    Log.d("S.S.N.", "SrPromptProcessor onFailure() : SmartReplayStatus is not valid");
                    subscreenDeviceModelB5.smartReplyStatus = 0;
                    subscreenDeviceModelB5.resetProgressScaleAnimation();
                    LinearLayout linearLayout2 = subscreenDeviceModelB5.progressLayout;
                    if (linearLayout2 != null) {
                        linearLayout2.setVisibility(8);
                    }
                    LottieAnimationView lottieAnimationView = subscreenDeviceModelB5.progressingVi;
                    if (lottieAnimationView != null) {
                        lottieAnimationView.cancelAnimation();
                    }
                    subscreenDeviceModelB5.isPossibleAiReply = false;
                    subscreenDeviceModelB5.mPromptSB.setLength(0);
                    return;
                }
                subscreenDeviceModelB5.smartReplyStatus = 0;
                SubscreenSubRoomNotification subscreenSubRoomNotification = subscreenDeviceModelB5.mSubRoomNotification;
                if (subscreenSubRoomNotification != null && (subscreenNotificationDetailAdapter = subscreenSubRoomNotification.mNotificationDetailAdapter) != null && subscreenNotificationDetailAdapter.mIsShownReplyButtonWindow) {
                    Log.d("S.S.N.", "SrPromptProcessor onFailure() - isShownReplyButtonWindow");
                    subscreenDeviceModelB5.setSmartReplyResultValue(1, str, null);
                    return;
                }
                SubscreenNotificationDetailAdapter.ItemViewHolder itemViewHolder = subscreenDeviceModelB5.detailViewHolder;
                if (itemViewHolder != null && (linearLayout = itemViewHolder.mSmartReplyLayout) != null && linearLayout.getVisibility() == 0) {
                    Log.d("S.S.N.", "SrPromptProcessor onFailure() - it's previous fail message");
                } else {
                    subscreenDeviceModelB5.showSmartReplyResultFailure(str);
                    SystemUIAnalytics.sendEventLog(SystemUIAnalytics.EID_QPNE_COVER_SCREEN_ID_DETAIL, SystemUIAnalytics.EID_QPNE_COVER_AI_SUGGESTIONS_FAILED);
                }
            }

            @Override // notification.src.com.android.systemui.PromptCallback
            public final void onComplete(String str, StringBuilder sb) {
                throw new NotImplementedError("An operation is not implemented: Not yet implemented");
            }
        };
    }

    public static final void access$handleTextLinkClick(SubscreenDeviceModelB5 subscreenDeviceModelB5, String str) {
        SubRoom.StateChangeListener stateChangeListener;
        subscreenDeviceModelB5.getClass();
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        intent.setData(Uri.parse("https://policies.account.samsung.com/terms?appKey=j5p7ll8g33" + "&applicationRegion=".concat(subscreenDeviceModelB5.getIsoCountryCode()) + AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("&language=", Locale.getDefault().getLanguage()) + "&region=".concat(subscreenDeviceModelB5.getIsoCountryCode()) + "&type=".concat(str)));
        intent.addFlags(QuickStepContract.SYSUI_STATE_NAV_BAR_VIS_GONE);
        PendingIntent activity = PendingIntent.getActivity(subscreenDeviceModelB5.mContext, 0, intent, 201326592);
        Intent intent2 = new Intent();
        intent2.putExtra("runOnCover", false);
        intent2.putExtra("ignoreKeyguardState", true);
        intent2.putExtra("showCoverToast", true);
        SubscreenSubRoomNotification subscreenSubRoomNotification = subscreenDeviceModelB5.mSubRoomNotification;
        if (subscreenSubRoomNotification == null || (stateChangeListener = subscreenSubRoomNotification.mStateChangeListener) == null) {
            return;
        }
        stateChangeListener.requestCoverPopup(activity, intent2);
    }

    public static final void access$handleTurnOnClick(final SubscreenDeviceModelB5 subscreenDeviceModelB5) {
        subscreenDeviceModelB5.getMSettingsHelper().setSuggestResponsesEnabled(true);
        subscreenDeviceModelB5.getMSettingsHelper().setSuggestResponsesUsed(true);
        subscreenDeviceModelB5.isSuggestResponsesEnabled = subscreenDeviceModelB5.getMSettingsHelper().isSuggestResponsesEnabled();
        boolean isSmartReplyUnusable = subscreenDeviceModelB5.isSmartReplyUnusable();
        subscreenDeviceModelB5.isUnusableAccount = isSmartReplyUnusable;
        subscreenDeviceModelB5.isPossibleAiReply = !isSmartReplyUnusable;
        LinearLayout linearLayout = subscreenDeviceModelB5.suggestResponsesBtn;
        if (linearLayout != null) {
            linearLayout.setVisibility(8);
        }
        ImageView imageView = subscreenDeviceModelB5.smartReplyTriggerBtn;
        if (imageView != null) {
            imageView.post(new Runnable() { // from class: com.android.systemui.statusbar.notification.SubscreenDeviceModelB5$handleTurnOnClick$1
                @Override // java.lang.Runnable
                public final void run() {
                    SubscreenSubRoomNotification subscreenSubRoomNotification;
                    SubscreenRecyclerView subscreenRecyclerView;
                    SubscreenRecyclerView subscreenRecyclerView2;
                    LinearLayout linearLayout2 = SubscreenDeviceModelB5.this.detailButtonContainer;
                    Float valueOf = linearLayout2 != null ? Float.valueOf(linearLayout2.getY()) : null;
                    SubscreenSubRoomNotification subscreenSubRoomNotification2 = SubscreenDeviceModelB5.this.mSubRoomNotification;
                    Integer valueOf2 = valueOf != null ? Integer.valueOf(((int) valueOf.floatValue()) - ((subscreenSubRoomNotification2 == null || (subscreenRecyclerView2 = subscreenSubRoomNotification2.mNotificationRecyclerView) == null) ? 0 : subscreenRecyclerView2.computeVerticalScrollOffset())) : null;
                    if (valueOf2 != null && valueOf2.intValue() == 0) {
                        SubscreenDeviceModelB5.this.showAIReply();
                    } else {
                        if (valueOf2 == null || (subscreenSubRoomNotification = SubscreenDeviceModelB5.this.mSubRoomNotification) == null || (subscreenRecyclerView = subscreenSubRoomNotification.mNotificationRecyclerView) == null) {
                            return;
                        }
                        subscreenRecyclerView.smoothScrollBy(0, valueOf2.intValue(), false);
                    }
                }
            });
        }
    }

    public static final boolean access$isSupportableLanguage(SubscreenDeviceModelB5 subscreenDeviceModelB5, String str) {
        subscreenDeviceModelB5.getClass();
        if (NotiRune.NOTI_SUBSCREEN_SUPPORT_SMART_REPLY_AI_FOR_CHINA) {
            return true;
        }
        List list = subscreenDeviceModelB5.onDeviceLanguageList;
        if (list != null) {
            Iterator it = ((ArrayList) list).iterator();
            while (it.hasNext()) {
                if (((LlmLanguage) it.next()).language.equals(str)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static final void access$showReplyActivity(SubscreenDeviceModelB5 subscreenDeviceModelB5, final Context context, SubscreenNotificationDetailAdapter.ItemViewHolder itemViewHolder) {
        if (subscreenDeviceModelB5.mIsStartedReplyActivity) {
            Log.d("S.S.N.", "showReplyActivity mIsStartedReplyActivity is true");
            return;
        }
        subscreenDeviceModelB5.mIsStartedReplyActivity = true;
        Display display = subscreenDeviceModelB5.mSubDisplay;
        if (display != null) {
            Bundle bundle = new Bundle();
            bundle.putString("key", itemViewHolder.mInfo.mKey);
            bundle.putInt("maxLength", itemViewHolder.mInfo.mRemoteInputMaxLength);
            bundle.putBoolean("isSms", itemViewHolder.mInfo.mRemoteInputIsSms);
            bundle.putString(com.samsung.android.knox.accounts.Account.SIGNATURE, itemViewHolder.mInfo.mRemoteInputSignature);
            final Intent intent = new Intent();
            intent.setFlags(335544320);
            intent.setClassName("com.android.systemui", "com.android.systemui.statusbar.notification.SubscreenNotificationReplyActivity");
            intent.putExtras(bundle);
            final ActivityOptions makeBasic = ActivityOptions.makeBasic();
            makeBasic.setLaunchDisplayId(display.getDisplayId());
            makeBasic.setForceLaunchWindowingMode(1);
            SubscreenSubRoomNotification subscreenSubRoomNotification = subscreenDeviceModelB5.mSubRoomNotification;
            if (subscreenSubRoomNotification != null) {
                ObjectAnimator duration = ObjectAnimator.ofFloat(subscreenSubRoomNotification.mSubscreenMainLayout, (Property<LinearLayout, Float>) View.ALPHA, 1.0f, 0.0f).setDuration(300L);
                Intrinsics.checkNotNull(duration);
                duration.addListener(new Animator.AnimatorListener() { // from class: com.android.systemui.statusbar.notification.SubscreenDeviceModelB5$showReplyActivity$lambda$19$lambda$18$lambda$17$$inlined$doOnEnd$1
                    @Override // android.animation.Animator.AnimatorListener
                    public final void onAnimationEnd(Animator animator) {
                        context.startActivity(intent, makeBasic.toBundle());
                    }

                    @Override // android.animation.Animator.AnimatorListener
                    public final void onAnimationCancel(Animator animator) {
                    }

                    @Override // android.animation.Animator.AnimatorListener
                    public final void onAnimationRepeat(Animator animator) {
                    }

                    @Override // android.animation.Animator.AnimatorListener
                    public final void onAnimationStart(Animator animator) {
                    }
                });
                duration.start();
            }
            MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("start SubscreenNotificationReplyActivity. key: ", itemViewHolder.mInfo.mKey, "S.S.N.");
            SystemUIAnalytics.sendEventCDLog(SystemUIAnalytics.EID_QPNE_COVER_SCREEN_ID_DETAIL, SystemUIAnalytics.EID_QPNE_COVER_REPLY, SystemUIAnalytics.QPNE_KEY_APP, itemViewHolder.mInfo.mPkg);
        }
    }

    public static void bindContent(View view, String str, SubscreenNotificationDetailAdapter.ItemViewHolder itemViewHolder) {
        TextView textView = (TextView) view.findViewById(R.id.detail_content_text);
        if (str == null || StringsKt__StringsKt.trim(str).toString().length() == 0) {
            return;
        }
        if (textView != null) {
            textView.setVisibility(0);
        }
        if (textView != null) {
            textView.setText(str);
        }
        itemViewHolder.mBodyLayoutString = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(itemViewHolder.mBodyLayoutString, str);
    }

    public static void bindTime(View view, long j, SubscreenNotificationDetailAdapter.ItemViewHolder itemViewHolder) {
        if (j <= 0) {
            return;
        }
        DateTimeView findViewById = view.findViewById(R.id.detail_clock);
        if (findViewById != null) {
            findViewById.setVisibility(0);
        }
        if (findViewById != null) {
            findViewById.setTime(j);
        }
        itemViewHolder.mBodyLayoutString = itemViewHolder.mBodyLayoutString + ((Object) (findViewById != null ? findViewById.getText() : null));
    }

    public static boolean isCallNotification(NotificationEntry notificationEntry) {
        Notification notification2;
        if (!ArraysKt___ArraysKt.contains(new String[]{"com.skt.prod.dialer", "com.samsung.android.incallui"}, notificationEntry.mSbn.getPackageName()) || !notificationEntry.mSbn.isOngoing()) {
            return false;
        }
        StatusBarNotification statusBarNotification = notificationEntry.mSbn;
        return "call".equals((statusBarNotification == null || (notification2 = statusBarNotification.getNotification()) == null) ? null : notification2.category);
    }

    public static void showErrorMessageWithAnim(View view) {
        if (view != null) {
            view.setAlpha(0.0f);
            view.setVisibility(0);
            view.animate().alpha(1.0f).setDuration(200L).start();
        }
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final void cancelReplySendButtonAnimator() {
        this.sendButtonPopupWindow = null;
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final void clickAdapterItem(Context context, SubscreenParentItemViewHolder subscreenParentItemViewHolder) {
        if (isCallNotification(subscreenParentItemViewHolder.mInfo.mRow.mEntry)) {
            NotificationEntry notificationEntry = subscreenParentItemViewHolder.mInfo.mRow.mEntry;
            Log.d("S.S.N.", "callNotificationLaunchApp B5 - " + notificationEntry.mKey + ", result: " + startNotificationIntent(notificationEntry.mSbn.getNotification().contentIntent));
            SystemUIAnalytics.sendEventLog(SystemUIAnalytics.EID_QPNE_COVER_SCREEN_ID_LIST, SystemUIAnalytics.EID_QPNE_COVER_MOVE_TO_CALL);
            return;
        }
        if (subscreenParentItemViewHolder.mInfo.mSbn.getNotification().fullScreenIntent != null && !isLaunchApp(subscreenParentItemViewHolder.mInfo.mRow.mEntry)) {
            String str = subscreenParentItemViewHolder.mInfo.mSbn.getNotification().category;
            if (subscreenParentItemViewHolder.mInfo.mIsCall || "alarm".equals(str)) {
                NotificationEntry notificationEntry2 = subscreenParentItemViewHolder.mInfo.mRow.mEntry;
                StringBuilder sb = new StringBuilder("clickAdapterItem B5 - put fullscreenIntent : ");
                String str2 = notificationEntry2.mKey;
                ExifInterface$$ExternalSyntheticOutline0.m(sb, str2, "S.S.N.");
                this.mFullScreenIntentEntries.put(str2, notificationEntry2);
                makeSubScreenNotification(notificationEntry2);
                showSubscreenNotification();
                return;
            }
        }
        boolean isKeyguardStats = isKeyguardStats();
        KeyguardActionInfo keyguardActionInfo = this.mKeyguardActionInfo;
        if (isKeyguardStats && (subscreenParentItemViewHolder.mInfo.mRow.needsRedaction() || isLaunchApp(subscreenParentItemViewHolder.mInfo.mRow.mEntry))) {
            showBouncer(context, subscreenParentItemViewHolder.mInfo.mRow.mEntry);
            if (keyguardActionInfo != null) {
                keyguardActionInfo.mAction = 1;
                keyguardActionInfo.mSubscreenParentItemViewHolder = subscreenParentItemViewHolder;
                return;
            }
            return;
        }
        if (isKeyguardUnlockShowing()) {
            ((KeyguardManager) context.getSystemService("keyguard")).semDismissKeyguard();
            if (keyguardActionInfo != null) {
                keyguardActionInfo.mAction = 1;
                keyguardActionInfo.mSubscreenParentItemViewHolder = subscreenParentItemViewHolder;
                return;
            }
            return;
        }
        if (clickKnoxItem(subscreenParentItemViewHolder.mInfo.mRow.mEntry)) {
            Log.d("S.S.N.", " clickAdapterItem - clickKnoxItem is true");
        } else {
            SubscreenSubRoomNotification subscreenSubRoomNotification = this.mSubRoomNotification;
            subscreenParentItemViewHolder.animateClickNotification(subscreenSubRoomNotification != null ? subscreenSubRoomNotification.mNotificationAnimatorManager : null, subscreenSubRoomNotification, false);
        }
    }

    public final boolean clickKnoxItem(NotificationEntry notificationEntry) {
        SubRoom.StateChangeListener stateChangeListener;
        StatusBarNotification statusBarNotification;
        Notification notification2;
        Notification notification3;
        Intrinsics.checkNotNull(notificationEntry);
        boolean isKnoxSecurity = isKnoxSecurity(notificationEntry);
        Log.d("S.S.N.", " clickKnoxItem - isKnoxSecurity : " + Boolean.valueOf(isKnoxSecurity));
        if (!isKnoxSecurity || !isLaunchApp(notificationEntry)) {
            return false;
        }
        StatusBarNotification statusBarNotification2 = notificationEntry.mSbn;
        PendingIntent pendingIntent = null;
        if (((statusBarNotification2 == null || (notification3 = statusBarNotification2.getNotification()) == null) ? null : notification3.contentIntent) != null && (statusBarNotification = notificationEntry.mSbn) != null && (notification2 = statusBarNotification.getNotification()) != null) {
            pendingIntent = notification2.contentIntent;
        }
        SubscreenSubRoomNotification subscreenSubRoomNotification = this.mSubRoomNotification;
        if (subscreenSubRoomNotification == null || (stateChangeListener = subscreenSubRoomNotification.mStateChangeListener) == null) {
            return true;
        }
        stateChangeListener.requestCoverPopup(pendingIntent, notificationEntry.mKey);
        return true;
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final void closeFullscreenFullPopupWindow() {
        if (this.mPresentation == null || this.mFullScreenIntentEntries.isEmpty()) {
            return;
        }
        this.mFullScreenIntentEntries.clear();
        this.mIsFullscreenFullPopupWindowClosing = true;
        dismissImmediately(1);
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final void detailClicked(NotificationEntry notificationEntry) {
        Log.d("S.S.N.", " DETAIL CLICKED B5 - " + (notificationEntry != null ? notificationEntry.mKey : null));
        if (skipDetailClicked(notificationEntry)) {
            return;
        }
        closeFullscreenFullPopupWindow();
        if (isShownDetail()) {
            this.mIsContentScroll = true;
            PopupWindow popupWindow = this.sendButtonPopupWindow;
            if (popupWindow != null) {
                popupWindow.dismiss();
            }
        }
        SubscreenNotificationReplyActivity subscreenNotificationReplyActivity = this.mController.replyActivity;
        if (subscreenNotificationReplyActivity != null) {
            subscreenNotificationReplyActivity.finish();
        }
        boolean isKeyguardStats = isKeyguardStats();
        KeyguardActionInfo keyguardActionInfo = this.mKeyguardActionInfo;
        if (isKeyguardStats) {
            Intrinsics.checkNotNull(notificationEntry);
            if (notificationEntry.row.needsRedaction() || isLaunchApp(notificationEntry)) {
                showBouncer(this.mContext, notificationEntry);
                if (keyguardActionInfo != null) {
                    keyguardActionInfo.mAction = 4;
                    keyguardActionInfo.mEntry = notificationEntry;
                    return;
                }
                return;
            }
        }
        if (isKeyguardUnlockShowing()) {
            ((KeyguardManager) this.mContext.getSystemService("keyguard")).semDismissKeyguard();
            this.mIsClickedPopupKeyguardUnlockShowing = true;
            if (keyguardActionInfo != null) {
                keyguardActionInfo.mAction = 4;
                keyguardActionInfo.mEntry = notificationEntry;
            }
        } else if (clickKnoxItem(notificationEntry)) {
            Log.d("S.S.N.", " detailClicked - clickKnoxItem is true");
        } else {
            if (this.mIsClickedPopupKeyguardUnlockShowing) {
                this.mIsClickedPopupKeyguardUnlockShowing = false;
            }
            super.detailClicked(notificationEntry);
        }
        String str = notificationEntry != null ? notificationEntry.mKey : null;
        NotificationEntry notificationEntry2 = this.currentPopupViewEntry;
        if (StringsKt__StringsJVMKt.equals(str, notificationEntry2 != null ? notificationEntry2.mKey : null, false) || isCoverBriefAllowed(notificationEntry)) {
            ((SubscreenUtil) Dependency.sDependency.getDependencyInner(SubscreenUtil.class)).closeSubscreenPanel();
        }
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final boolean dispatchKeyEvent(KeyEvent keyEvent) {
        SubscreenRecyclerView subscreenRecyclerView;
        SubscreenSubRoomNotification subscreenSubRoomNotification;
        SubscreenRecyclerView subscreenRecyclerView2;
        View findFocus;
        boolean z = true;
        boolean z2 = keyEvent.getKeyCode() == 20 || keyEvent.getKeyCode() == 19 || keyEvent.getKeyCode() == 61;
        int action = keyEvent.getAction();
        if (action == 0) {
            if (keyEvent.getKeyCode() == 4) {
                this.mIsNaviBarBackButtonClicked = true;
                if (!isShownDetail() && !isShownGroup()) {
                    z = false;
                }
                KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m("dispatchKeyEvent() - navi back click, ret: ", "S.S.N.", z);
                return z;
            }
            if (!z2) {
                return false;
            }
            int keyCode = keyEvent.getKeyCode();
            if (isShownDetail()) {
                return false;
            }
            if (keyCode != 20 && keyCode != 61) {
                z = false;
            }
            SubscreenSubRoomNotification subscreenSubRoomNotification2 = this.mSubRoomNotification;
            if (subscreenSubRoomNotification2 == null || (subscreenRecyclerView = subscreenSubRoomNotification2.mNotificationRecyclerView) == null) {
                return false;
            }
            subscreenRecyclerView.scrollToPosition(RecyclerView.getChildAdapterPosition(subscreenRecyclerView.getFocusedChild()) + (z ? 2 : -2));
            return false;
        }
        if (action != 1) {
            return false;
        }
        if (keyEvent.getKeyCode() == 4 && this.mIsNaviBarBackButtonClicked) {
            return performBackClick();
        }
        if (!z2) {
            return false;
        }
        keyEvent.getKeyCode();
        if (!isShownDetail() || (subscreenSubRoomNotification = this.mSubRoomNotification) == null || (subscreenRecyclerView2 = subscreenSubRoomNotification.mNotificationRecyclerView) == null || (findFocus = subscreenRecyclerView2.findFocus()) == null) {
            return false;
        }
        int[] iArr = new int[2];
        findFocus.getLocationOnScreen(iArr);
        int measuredHeight = findFocus.getMeasuredHeight() + iArr[1];
        Context context = this.mDisplayContext;
        if (context == null) {
            context = null;
        }
        int dimensionPixelSize = 720 - context.getResources().getDimensionPixelSize(R.dimen.subscreen_noti_cut_out_size_b5);
        if (measuredHeight > dimensionPixelSize) {
            subscreenRecyclerView2.smoothScrollBy(0, measuredHeight - dimensionPixelSize, false);
            return false;
        }
        int mainHeaderViewHeight = getMainHeaderViewHeight();
        int i = iArr[1];
        if (i >= mainHeaderViewHeight) {
            return false;
        }
        subscreenRecyclerView2.smoothScrollBy(0, i - mainHeaderViewHeight, false);
        return false;
    }

    public final void enableSmartReplyTriggerBtn(String str, boolean z) {
        final ImageView imageView = this.smartReplyTriggerBtn;
        if (imageView != null) {
            float alpha = imageView.getAlpha();
            final float f = z ? 1.0f : 0.4f;
            if (alpha != f) {
                ViewPropertyAnimator duration = imageView.animate().alpha(z ? 1.0f : 0.4f).setDuration(200L);
                duration.withEndAction(new Runnable() { // from class: com.android.systemui.statusbar.notification.SubscreenDeviceModelB5$enableSmartReplyTriggerBtn$1$1$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        imageView.setAlpha(f);
                    }
                });
                duration.start();
            }
            imageView.setEnabled((Intrinsics.areEqual(str, "unsupportedLanguage") || Intrinsics.areEqual(str, "emptyMessage")) ? this.smartReplyStatus != 2 : z);
        }
        TextView textView = this.smartReplyTriggerBtnText;
        if (textView != null) {
            textView.setAlpha(z ? 1.0f : 0.4f);
            textView.setEnabled(z);
        }
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelCommon, com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final void foldStateChanged(boolean z) {
        SubscreenNotificationReplyActivity subscreenNotificationReplyActivity;
        SubscreenNotificationInfo subscreenNotificationInfo;
        SubscreenNotificationDetailAdapter.ItemViewHolder itemViewHolder;
        View view;
        String str = null;
        if (z) {
            this.mIsClickedPopupKeyguardUnlockShowing = false;
            KeyguardActionInfo keyguardActionInfo = this.mKeyguardActionInfo;
            if (keyguardActionInfo != null) {
                keyguardActionInfo.mAction = 0;
                keyguardActionInfo.mSubscreenParentItemViewHolder = null;
                keyguardActionInfo.mContext = null;
            }
            this.mUnlockNotificationPendingIntentItemKey = null;
            updateSmartReplyVariables();
        } else if ((getTopActivityName().equals("com.android.systemui.subscreen.SubHomeActivity") && (itemViewHolder = this.detailViewHolder) != null && (view = itemViewHolder.itemView) != null && view.hasWindowFocus()) || ((subscreenNotificationReplyActivity = this.mController.replyActivity) != null && subscreenNotificationReplyActivity.hasWindowFocus())) {
            SubscreenSubRoomNotification subscreenSubRoomNotification = this.mSubRoomNotification;
            if (subscreenSubRoomNotification != null) {
                NotificationActivityStarter notificationActivityStarter = this.mNotificationActivityStarter;
                SubscreenNotificationDetailAdapter subscreenNotificationDetailAdapter = subscreenSubRoomNotification.mNotificationDetailAdapter;
                if (subscreenNotificationDetailAdapter != null) {
                    if (notificationActivityStarter == null) {
                        Log.e("SubscreenNotificationDetailAdapter", "notificationActivityStarter is null");
                    } else if (subscreenNotificationDetailAdapter.mSelectNotificationInfo != null) {
                        Log.e("SubscreenNotificationDetailAdapter", "startNotificationActivity  mSelectNotificationInfo : " + subscreenNotificationDetailAdapter.mSelectNotificationInfo.mKey);
                        ExpandableNotificationRow expandableNotificationRow = subscreenNotificationDetailAdapter.mSelectNotificationInfo.mRow;
                        ((StatusBarNotificationActivityStarter) notificationActivityStarter).onNotificationClicked(expandableNotificationRow.mEntry, expandableNotificationRow);
                        ((CentralSurfacesImpl) ((CentralSurfaces) Dependency.sDependency.getDependencyInner(CentralSurfaces.class))).mShadeController.makeExpandedInvisible();
                        subscreenNotificationDetailAdapter.cleanAdapter();
                    } else {
                        Log.e("SubscreenNotificationDetailAdapter", "startNotificationActivity no select holder...");
                    }
                }
            }
            SubscreenSubRoomNotification subscreenSubRoomNotification2 = this.mSubRoomNotification;
            if (subscreenSubRoomNotification2 != null && subscreenSubRoomNotification2.mIsShownDetail) {
                SubscreenNotificationDetailAdapter subscreenNotificationDetailAdapter2 = subscreenSubRoomNotification2.mNotificationDetailAdapter;
                if (subscreenNotificationDetailAdapter2 != null && (subscreenNotificationInfo = subscreenNotificationDetailAdapter2.mSelectNotificationInfo) != null) {
                    str = subscreenNotificationInfo.mPkg;
                }
                SystemUIAnalytics.sendEventCDLog(SystemUIAnalytics.EID_QPNE_COVER_SCREEN_ID_DETAIL, SystemUIAnalytics.EID_QPNE_COVER_UNFOLD_IN_DETAIL_VIEW, SystemUIAnalytics.QPNE_KEY_APP, str);
            }
        }
        super.foldStateChanged(z);
    }

    /* JADX WARN: Code restructure failed: missing block: B:70:0x00eb, code lost:
    
        if ((r8 != null ? r8.mPrevLastHistoryView : null) == null) goto L81;
     */
    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int getDetailAdapterAutoScrollCurrentPositionByReceive(android.view.View r9) {
        /*
            Method dump skipped, instructions count: 308
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.SubscreenDeviceModelB5.getDetailAdapterAutoScrollCurrentPositionByReceive(android.view.View):int");
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final int getDetailAdapterContentViewResource() {
        return R.layout.subscreen_notification_detail_adapter_content_layout_item_b5;
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final View getDetailAdapterLayout(ViewGroup viewGroup, int i, Context context) {
        return LayoutInflater.from(context).inflate(i != 0 ? i != 1 ? -1 : R.layout.subscreen_notification_detail_adapter_text_item_b5 : R.layout.subscreen_notification_detail_adapter_item_b5, viewGroup, false);
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final int getDetailAdapterReplyWordResource() {
        return R.layout.subscreen_notification_detail_adapter_content_layout_item_reply_word_b5;
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final int getDispalyHeight() {
        return DeviceState.CAPTURED_BLUR_THRESHOLD_WIDTH;
    }

    public final String getDisplayName(String str) {
        if (str.length() == 0) {
            return "";
        }
        String str2 = this.DISPLAY_LANG_CODE_DELIMITER;
        if (!StringsKt__StringsKt.contains(str, str2, false)) {
            return new Locale(str).getDisplayName();
        }
        List split$default = StringsKt__StringsKt.split$default(str, new String[]{str2}, 0, 6);
        String displayName = new Locale((String) split$default.get(0), (String) split$default.get(1)).getDisplayName();
        Intrinsics.checkNotNull(displayName);
        return displayName;
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final int getFullPopupWindowType() {
        return this.mFullScreenIntentEntries.isEmpty() ? 2026 : 2040;
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final View getGroupAdapterLayout(ViewGroup viewGroup, int i, Context context) {
        View inflate = LayoutInflater.from(context).inflate(i != 0 ? i != 1 ? i != 2 ? i != 4 ? i != 5 ? -1 : R.layout.subscreen_notification_group_adapter_custom_view_b5 : R.layout.subscreen_notification_group_adapter_hide_content_b5 : R.layout.subscreen_notification_adapter_header_b5 : R.layout.subscreen_notification_adapter_clear_all_footer_b5 : R.layout.subscreen_notification_group_adapter_item_b5, viewGroup, false);
        if (i != 1) {
            Context context2 = this.mDisplayContext;
            if (context2 == null) {
                context2 = null;
            }
            inflate.setBackground(context2.getDrawable(R.drawable.subscreen_notification_list_item_bg_selecter_b5));
        }
        return inflate;
    }

    public final String getHistoryInfo(SubscreenNotificationDetailAdapter.ItemViewHolder itemViewHolder) {
        StringBuilder sb = new StringBuilder();
        ArrayList arrayList = itemViewHolder.mInfo.mMessageingStyleInfoArray;
        int size = arrayList.size();
        if ((!arrayList.isEmpty()) && ((SubscreenNotificationInfo.MessagingStyleInfo) arrayList.get(size - 1)).mIsReply) {
            Log.d("S.S.N.", "getHistoryInfo() - this is reply notification. so do not call AI");
            return null;
        }
        StringBuilder sb2 = this.mPromptSBForLog;
        sb2.setLength(0);
        int size2 = arrayList.size();
        Date date = new Date(System.currentTimeMillis());
        for (int size3 = arrayList.size() + (-7) >= 0 ? arrayList.size() - 7 : 0; size3 < size2; size3++) {
            SubscreenNotificationInfo.MessagingStyleInfo messagingStyleInfo = (SubscreenNotificationInfo.MessagingStyleInfo) arrayList.get(size3);
            long j = messagingStyleInfo.mPostedTime;
            if (j <= 0) {
                j = messagingStyleInfo.mTimeStamp;
            }
            Date date2 = new Date(j);
            if (size3 >= size2 - 1 || (date.getYear() <= date2.getYear() && date.getMonth() <= date2.getMonth() && date.getDay() <= date2.getDay() && date.getHours() - date2.getHours() <= 1)) {
                if (!NotiRune.NOTI_SUBSCREEN_SUPPORT_SMART_REPLY_AI_FOR_CHINA) {
                    String obj = StringsKt__StringsKt.trim(messagingStyleInfo.mContentText).toString();
                    if (obj != null && obj.length() != 0) {
                        if (messagingStyleInfo.mIsReply) {
                            sb.append("User: ");
                            sb2.append("User: ");
                        } else {
                            sb.append("Others: ");
                            sb2.append("Others: ");
                        }
                    }
                } else if (messagingStyleInfo.mIsReply) {
                    sb.append(this.mContext.getString(R.string.subscreen_notification_smart_reply_user_for_chn));
                    sb2.append(this.mContext.getString(R.string.subscreen_notification_smart_reply_user_for_chn));
                } else {
                    sb.append(this.mContext.getString(R.string.subscreen_notification_smart_reply_participant_for_chn));
                    sb2.append(this.mContext.getString(R.string.subscreen_notification_smart_reply_participant_for_chn));
                }
                sb.append(messagingStyleInfo.mContentText);
                sb.append("\n");
                sb2.append((String) TextUtils.trimToLengthWithEllipsis(messagingStyleInfo.mContentText, 2));
                sb2.append("\n");
            }
        }
        return sb.toString();
    }

    /* JADX WARN: Removed duplicated region for block: B:9:0x0031 A[RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.String getIsoCountryCode() {
        /*
            r2 = this;
            android.content.Context r2 = r2.mContext     // Catch: java.lang.Exception -> L17
            java.lang.String r0 = "phone"
            java.lang.Object r2 = r2.getSystemService(r0)     // Catch: java.lang.Exception -> L17
            android.telephony.TelephonyManager r2 = (android.telephony.TelephonyManager) r2     // Catch: java.lang.Exception -> L17
            java.lang.String r0 = r2.getSimCountryIso()     // Catch: java.lang.Exception -> L17
            if (r0 == 0) goto L19
            int r1 = r0.length()     // Catch: java.lang.Exception -> L17
            if (r1 != 0) goto L1d
            goto L19
        L17:
            r2 = move-exception
            goto L32
        L19:
            java.lang.String r0 = r2.getNetworkCountryIso()     // Catch: java.lang.Exception -> L17
        L1d:
            java.util.Locale r2 = new java.util.Locale     // Catch: java.lang.Exception -> L17
            java.lang.String r1 = ""
            r2.<init>(r1, r0)     // Catch: java.lang.Exception -> L17
            java.lang.String r2 = r2.getISO3Country()     // Catch: java.lang.Exception -> L17
            kotlin.jvm.internal.Intrinsics.checkNotNull(r2)     // Catch: java.lang.Exception -> L17
            int r0 = r2.length()     // Catch: java.lang.Exception -> L17
            if (r0 <= 0) goto L39
            return r2
        L32:
            java.lang.String r0 = "getIsoCountryCode: "
            java.lang.String r1 = "S.S.N."
            com.android.keyguard.EmergencyButton$$ExternalSyntheticOutline0.m(r0, r2, r1)
        L39:
            java.util.Locale r2 = java.util.Locale.getDefault()
            java.lang.String r2 = r2.getISO3Country()
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.SubscreenDeviceModelB5.getIsoCountryCode():java.lang.String");
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final int getLayoutInDisplayCutoutMode() {
        return 3;
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final int getListAdapterGroupItemResource() {
        return R.layout.subscreen_notification_list_adapter_group_summary_layout_item_b5;
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final View getListAdapterLayout(ViewGroup viewGroup, int i, Context context) {
        View inflate = LayoutInflater.from(context).inflate(i != 0 ? i != 1 ? i != 2 ? i != 3 ? i != 4 ? i != 5 ? -1 : R.layout.subscreen_notification_list_adapter_group_summary_layout_b5 : R.layout.subscreen_notification_list_adapter_hide_content_b5 : R.layout.subscreen_notification_adapter_no_notification_b5 : R.layout.subscreen_notification_list_adapter_custom_view_b5 : R.layout.subscreen_notification_adapter_clear_all_footer_b5 : R.layout.subscreen_notification_list_adapter_item_b5, viewGroup, false);
        if (i != 1 && i != 3) {
            Context context2 = this.mDisplayContext;
            if (context2 == null) {
                context2 = null;
            }
            inflate.setBackground(context2.getDrawable(R.drawable.subscreen_notification_list_item_bg_selecter_b5));
        }
        return inflate;
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final int getMainHeaderViewHeight() {
        View view = this.mHeaderViewLayout;
        if (view != null) {
            return view.getHeight();
        }
        return 0;
    }

    public final String getOnDeviceMetaData() {
        String str = this.SR_LLM_PACKAGE_NAME;
        String str2 = this.metaData;
        if (str2 != null) {
            return str2;
        }
        try {
            Bundle bundle = this.mContext.getPackageManager().getApplicationInfo(str, 128).metaData;
            if (bundle != null) {
                String string = bundle.getString(str + ".FUNCTION_INFO");
                this.metaData = string;
                Log.d("S.S.N.", "On-Device LLM MetaData : " + string);
                String str3 = this.metaData;
                return str3 == null ? "" : str3;
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.d("S.S.N.", "On-Device LLM Not Found " + e);
        }
        return "";
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final Animator getPopUpViewDismissAnimator(View view) {
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(view, (Property<View, Float>) View.TRANSLATION_Y, 0.0f, -71.0f);
        ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(view, (Property<View, Float>) View.ALPHA, 0.95f, 0.0f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(ofFloat, ofFloat2);
        animatorSet.setDuration(200L);
        if (this.popupViewShowing) {
            animatorSet.addListener(this.topPopupAnimationListener);
        }
        return animatorSet;
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final Animator getPopUpViewShowAnimator(View view) {
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(view, (Property<View, Float>) View.TRANSLATION_Y, -71.0f, 0.0f);
        ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(view, (Property<View, Float>) View.ALPHA, 0.0f, 0.95f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(ofFloat, ofFloat2);
        animatorSet.setDuration(200L);
        animatorSet.addListener(new Animator.AnimatorListener() { // from class: com.android.systemui.statusbar.notification.SubscreenDeviceModelB5$getPopUpViewShowAnimator$lambda$3$$inlined$doOnStart$1
            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationCancel(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationRepeat(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationStart(Animator animator) {
            }
        });
        return animatorSet;
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final View getReplyButtonView() {
        SubScreenManager subScreenManager;
        SubHomeActivity subHomeActivity;
        Lazy lazy = this.mSubScreenManagerLazy;
        if (lazy == null || (subScreenManager = (SubScreenManager) lazy.get()) == null || (subHomeActivity = subScreenManager.mActivity) == null) {
            Log.e("S.S.N.", "can't inflate ReplyButtonView.");
            return null;
        }
        View inflate = LayoutInflater.from(subHomeActivity).inflate(R.layout.subscreen_notification_detail_adapter_content_layout_item_reply_button_b5, (ViewGroup) null);
        View findViewById = inflate.findViewById(R.id.send);
        if (this.mIsReplySendButtonLoading) {
            if (findViewById != null) {
                findViewById.setEnabled(false);
            }
            if (findViewById != null) {
                findViewById.setAlpha(0.5f);
            }
        } else {
            if (findViewById != null) {
                findViewById.setEnabled(true);
            }
            if (findViewById != null) {
                findViewById.setAlpha(1.0f);
            }
        }
        return inflate;
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final int getSelectedReplyBGColor() {
        return R.color.subscreen_notification_select_reply_background_color_b5;
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final boolean getSubIconVisible(boolean z, boolean z2) {
        return (z2 && z) ? false : true;
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final int getSubscreenNotificationTipResource() {
        return R.layout.subscreen_notification_tip_b5;
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final Animator getTopPresentationDismissAnimator(View view) {
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(view, (Property<View, Float>) View.TRANSLATION_Y, 0.0f, -71.0f);
        ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(view, (Property<View, Float>) View.ALPHA, 0.95f, 0.0f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(ofFloat, ofFloat2);
        animatorSet.setDuration(200L);
        return animatorSet;
    }

    public final void handleProgressLayout(boolean z) {
        KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m("handleProgressLayout() - show : ", "S.S.N.", z);
        if (z) {
            Runnable runnable = new Runnable() { // from class: com.android.systemui.statusbar.notification.SubscreenDeviceModelB5$handleProgressLayout$runnable$1
                @Override // java.lang.Runnable
                public final void run() {
                    SubscreenDeviceModelB5 subscreenDeviceModelB5 = SubscreenDeviceModelB5.this;
                    int i = SubscreenDeviceModelB5.$r8$clinit;
                    subscreenDeviceModelB5.updateVisibilityForSmartReplyLayout(8);
                    LinearLayout linearLayout = SubscreenDeviceModelB5.this.progressLayout;
                    if (linearLayout != null) {
                        linearLayout.setVisibility(0);
                    }
                    LottieAnimationView lottieAnimationView = SubscreenDeviceModelB5.this.progressingVi;
                    if (lottieAnimationView != null) {
                        lottieAnimationView.playAnimation();
                    }
                }
            };
            LinearLayout linearLayout = this.progressLayout;
            if (linearLayout != null) {
                startProgressSpringAnimation(linearLayout, true, runnable);
                return;
            }
            return;
        }
        Runnable runnable2 = new Runnable() { // from class: com.android.systemui.statusbar.notification.SubscreenDeviceModelB5$handleProgressLayout$runnable$2
            @Override // java.lang.Runnable
            public final void run() {
                LinearLayout linearLayout2 = SubscreenDeviceModelB5.this.progressLayout;
                if (linearLayout2 != null) {
                    linearLayout2.setVisibility(8);
                }
                LottieAnimationView lottieAnimationView = SubscreenDeviceModelB5.this.progressingVi;
                if (lottieAnimationView != null) {
                    lottieAnimationView.cancelAnimation();
                }
            }
        };
        LinearLayout linearLayout2 = this.progressLayout;
        if (linearLayout2 != null) {
            startProgressSpringAnimation(linearLayout2, false, runnable2);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:135:0x0236  */
    /* JADX WARN: Removed duplicated region for block: B:139:0x023c  */
    /* JADX WARN: Removed duplicated region for block: B:140:0x01fb  */
    /* JADX WARN: Removed duplicated region for block: B:77:0x0191  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void inflateSmartReplyAI(java.lang.String r12) {
        /*
            Method dump skipped, instructions count: 600
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.SubscreenDeviceModelB5.inflateSmartReplyAI(java.lang.String):void");
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelCommon, com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final void initDetailAdapterItemViewHolder(final Context context, SubscreenNotificationDetailAdapter subscreenNotificationDetailAdapter, final SubscreenNotificationDetailAdapter.ItemViewHolder itemViewHolder) {
        super.initDetailAdapterItemViewHolder(context, subscreenNotificationDetailAdapter, itemViewHolder);
        Log.d("S.S.N.", "initDetailAdapterItemViewHolder() - B5");
        this.detailButtonContainer = (LinearLayout) itemViewHolder.itemView.findViewById(R.id.detail_button_layout);
        ImageView imageView = (ImageView) itemViewHolder.itemView.findViewById(R.id.keyboard_reply_button);
        ImageView imageView2 = null;
        if (imageView != null) {
            imageView.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.statusbar.notification.SubscreenDeviceModelB5$initDetailAdapterItemViewHolder$1$1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    if (!SubscreenDeviceModelB5.this.isKeyguardStats()) {
                        SubscreenDeviceModelB5.access$showReplyActivity(SubscreenDeviceModelB5.this, context, itemViewHolder);
                        return;
                    }
                    SubscreenDeviceModelB5.this.showBouncer(context, itemViewHolder.mInfo.mRow.mEntry);
                    SubscreenDeviceModelB5.KeyguardActionInfo keyguardActionInfo = SubscreenDeviceModelB5.this.mKeyguardActionInfo;
                    if (keyguardActionInfo != null) {
                        Context context2 = context;
                        SubscreenNotificationDetailAdapter.ItemViewHolder itemViewHolder2 = itemViewHolder;
                        keyguardActionInfo.mAction = 3;
                        keyguardActionInfo.mDetailAdapterItemViewHolder = itemViewHolder2;
                        keyguardActionInfo.mContext = context2;
                    }
                }
            });
        } else {
            imageView = null;
        }
        this.keyboardReplyButton = imageView;
        this.callBackButtonText = (TextView) itemViewHolder.itemView.findViewById(R.id.call_back_button_text);
        this.replyButtonText = (TextView) itemViewHolder.itemView.findViewById(R.id.keyboard_reply_button_text);
        this.openAppButtonText = (TextView) itemViewHolder.itemView.findViewById(R.id.app_open_button_text);
        this.clearButtonText = (TextView) itemViewHolder.itemView.findViewById(R.id.clear_button_text);
        this.smartReplyTriggerBtnText = (TextView) itemViewHolder.itemView.findViewById(R.id.smart_reply_trigger_text);
        this.mSmartReplyClickedByUser = false;
        final ImageView imageView3 = (ImageView) itemViewHolder.itemView.findViewById(R.id.smart_reply_trigger_button);
        if (imageView3 != null) {
            imageView3.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.statusbar.notification.SubscreenDeviceModelB5$initDetailAdapterItemViewHolder$2$1
                /* JADX WARN: Type inference failed for: r5v0 */
                /* JADX WARN: Type inference failed for: r5v2, types: [boolean, int] */
                /* JADX WARN: Type inference failed for: r5v4 */
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    Float f;
                    SubscreenRecyclerView subscreenRecyclerView;
                    SubscreenRecyclerView subscreenRecyclerView2;
                    SubscreenNotificationDialog subscreenNotificationDialog;
                    SubscreenNotificationDialog subscreenNotificationDialog2;
                    SubscreenNotificationDialog subscreenNotificationDialog3;
                    final SubscreenDeviceModelB5 subscreenDeviceModelB5 = SubscreenDeviceModelB5.this;
                    subscreenDeviceModelB5.mSmartReplyClickedByUser = true;
                    ?? r5 = 0;
                    if (!subscreenDeviceModelB5.isUnusableAccount) {
                        subscreenDeviceModelB5.isPossibleAiReply = true;
                        Context context2 = subscreenDeviceModelB5.mDisplayContext;
                        if (context2 == null) {
                            context2 = null;
                        }
                        int dimensionPixelSize = context2.getResources().getDimensionPixelSize(R.dimen.subscreen_noti_detail_icon_button_shadow_margin_b5);
                        LinearLayout linearLayout = SubscreenDeviceModelB5.this.detailButtonContainer;
                        if (linearLayout != null) {
                            float y = linearLayout.getY() + dimensionPixelSize;
                            SubscreenSubRoomNotification subscreenSubRoomNotification = SubscreenDeviceModelB5.this.mSubRoomNotification;
                            f = Float.valueOf(y - ((subscreenSubRoomNotification == null || (subscreenRecyclerView2 = subscreenSubRoomNotification.mNotificationRecyclerView) == null) ? 0 : subscreenRecyclerView2.computeVerticalScrollOffset()));
                        } else {
                            f = null;
                        }
                        if (f != null) {
                            if (((int) f.floatValue()) == 0) {
                                SubscreenDeviceModelB5.this.showAIReply();
                            } else {
                                SubscreenSubRoomNotification subscreenSubRoomNotification2 = SubscreenDeviceModelB5.this.mSubRoomNotification;
                                if (subscreenSubRoomNotification2 != null && (subscreenRecyclerView = subscreenSubRoomNotification2.mNotificationRecyclerView) != null) {
                                    subscreenRecyclerView.smoothScrollBy(0, (int) f.floatValue(), false);
                                }
                            }
                        }
                    } else if (!subscreenDeviceModelB5.isSALoggedIn) {
                        Log.d("S.S.N.", "checkAndExecuteSuggestResponses() : execute sa sign in");
                        subscreenDeviceModelB5.openPhonePopupForIntelligenceSettings("com.samsung.android.action.INTELLIGENCE_SERVICE_SETTINGS_START_INTENT");
                    } else if (subscreenDeviceModelB5.isChildAccount) {
                        Log.d("S.S.N.", "checkAndExecuteSuggestResponses() : execute child account announce");
                        SubscreenNotificationDialog subscreenNotificationDialog4 = subscreenDeviceModelB5.mDialog;
                        if (subscreenNotificationDialog4 != null) {
                            if (subscreenNotificationDialog4.mDialog.isShowing() && (subscreenNotificationDialog3 = subscreenDeviceModelB5.mDialog) != null) {
                                subscreenNotificationDialog3.dismiss();
                            }
                            subscreenDeviceModelB5.mDialog = null;
                        }
                        int i = subscreenDeviceModelB5.childGraduateAge;
                        Context context3 = subscreenDeviceModelB5.mDisplayContext;
                        if (context3 == null) {
                            context3 = null;
                        }
                        String quantityString = context3.getResources().getQuantityString(R.plurals.subscreen_notification_smart_reply_child_account_dialog_content, i, Integer.valueOf(i));
                        Context context4 = subscreenDeviceModelB5.mDisplayContext;
                        SubscreenNotificationDialog subscreenNotificationDialog5 = new SubscreenNotificationDialog(context4 != null ? context4 : null, quantityString);
                        subscreenDeviceModelB5.mDialog = subscreenNotificationDialog5;
                        subscreenNotificationDialog5.show();
                    } else if (subscreenDeviceModelB5.isAiInfoConfirmed) {
                        boolean z = NotiRune.NOTI_SUBSCREEN_SUPPORT_SMART_REPLY_AI_FOR_CHINA;
                        if (z && subscreenDeviceModelB5.isPreventOnlineProcessing()) {
                            SubscreenNotificationDialog subscreenNotificationDialog6 = subscreenDeviceModelB5.mDialog;
                            if (subscreenNotificationDialog6 != null) {
                                if (subscreenNotificationDialog6.mDialog.isShowing() && (subscreenNotificationDialog2 = subscreenDeviceModelB5.mDialog) != null) {
                                    subscreenNotificationDialog2.dismiss();
                                }
                                subscreenDeviceModelB5.mDialog = null;
                            }
                            Context context5 = subscreenDeviceModelB5.mDisplayContext;
                            if (context5 == null) {
                                context5 = null;
                            }
                            String string = context5.getResources().getString(R.string.subscreen_online_processing_title);
                            Context context6 = subscreenDeviceModelB5.mDisplayContext;
                            if (context6 == null) {
                                context6 = null;
                            }
                            String string2 = context6.getResources().getString(R.string.subscreen_online_processing_message);
                            Runnable runnable = new Runnable() { // from class: com.android.systemui.statusbar.notification.SubscreenDeviceModelB5$checkAndExecuteSuggestResponses$runnable$1
                                @Override // java.lang.Runnable
                                public final void run() {
                                    SubscreenDeviceModelB5 subscreenDeviceModelB52 = SubscreenDeviceModelB5.this;
                                    int i2 = SubscreenDeviceModelB5.$r8$clinit;
                                    subscreenDeviceModelB52.openPhonePopupForIntelligenceSettings("com.samsung.android.action.INTELLIGENCE_SERVICE_PROCESSING_ONLINE_INTENT");
                                }
                            };
                            Context context7 = subscreenDeviceModelB5.mDisplayContext;
                            SubscreenNotificationDialog subscreenNotificationDialog7 = new SubscreenNotificationDialog(context7 != null ? context7 : null, string, string2, runnable);
                            subscreenDeviceModelB5.mDialog = subscreenNotificationDialog7;
                            subscreenNotificationDialog7.show();
                        } else if (!subscreenDeviceModelB5.isSuggestResponsesEnabled) {
                            Log.d("S.S.N.", "checkAndExecuteSuggestResponses() : execute suggest replies setting on");
                            SubscreenNotificationDialog subscreenNotificationDialog8 = subscreenDeviceModelB5.mDialog;
                            if (subscreenNotificationDialog8 != null) {
                                if (subscreenNotificationDialog8.mDialog.isShowing() && (subscreenNotificationDialog = subscreenDeviceModelB5.mDialog) != null) {
                                    subscreenNotificationDialog.dismiss();
                                }
                                subscreenDeviceModelB5.mDialog = null;
                            }
                            if (z) {
                                Context context8 = subscreenDeviceModelB5.mDisplayContext;
                                if (context8 == null) {
                                    context8 = null;
                                }
                                final View inflate = LayoutInflater.from(context8).inflate(R.layout.subscreen_notification_smart_reply_disclaimer_dialog, (ViewGroup) null);
                                TextView textView = (TextView) inflate.findViewById(R.id.smart_reply_disclaimer_terms_and_conditions);
                                Button button = (Button) inflate.findViewById(R.id.smart_reply_disclaimer_ok_btn);
                                Button button2 = (Button) inflate.findViewById(R.id.smart_reply_disclaimer_cancel_btn);
                                String string3 = subscreenDeviceModelB5.mContext.getString(R.string.subscreen_notification_smart_reply_disclaimer_terms_and_condition);
                                String substringBefore$default = StringsKt__StringsKt.substringBefore$default(StringsKt__StringsKt.substringAfter$default(string3, "%1$s"), "%2$s");
                                String substringBefore$default2 = StringsKt__StringsKt.substringBefore$default(StringsKt__StringsKt.substringAfter$default(string3, "%3$s"), "%4$s");
                                ArrayList arrayList = new ArrayList();
                                arrayList.add(substringBefore$default);
                                arrayList.add(substringBefore$default2);
                                ClickableSpan clickableSpan = new ClickableSpan() { // from class: com.android.systemui.statusbar.notification.SubscreenDeviceModelB5$getDisclaimerViewForChina$clickableSpanForPrivacyNotice$1
                                    @Override // android.text.style.ClickableSpan
                                    public final void onClick(View view2) {
                                        SubscreenDeviceModelB5.access$handleTextLinkClick(SubscreenDeviceModelB5.this, "PN");
                                    }
                                };
                                ClickableSpan clickableSpan2 = new ClickableSpan() { // from class: com.android.systemui.statusbar.notification.SubscreenDeviceModelB5$getDisclaimerViewForChina$clickableSpanForTermsAndConditions$1
                                    @Override // android.text.style.ClickableSpan
                                    public final void onClick(View view2) {
                                        SubscreenDeviceModelB5.access$handleTextLinkClick(SubscreenDeviceModelB5.this, "TC");
                                    }
                                };
                                ArrayList arrayList2 = new ArrayList();
                                arrayList2.add(clickableSpan);
                                arrayList2.add(clickableSpan2);
                                int i2 = StringCompanionObject.$r8$clinit;
                                String format = String.format(string3, Arrays.copyOf(new Object[]{"", "", "", ""}, 4));
                                int style = Typeface.create(null, VolteConstants.ErrorCode.BUSY_EVERYWHERE, false).getStyle();
                                SpannableString spannableString = new SpannableString(format);
                                Iterator it = arrayList.iterator();
                                int i3 = 0;
                                while (it.hasNext()) {
                                    Object next = it.next();
                                    int i4 = i3 + 1;
                                    if (i3 < 0) {
                                        CollectionsKt__CollectionsKt.throwIndexOverflow();
                                        throw null;
                                    }
                                    String str = (String) next;
                                    int indexOf$default = StringsKt__StringsKt.indexOf$default(spannableString, str, (int) r5, (boolean) r5, 6);
                                    int length = str.length() + indexOf$default;
                                    if (indexOf$default >= 0 && length <= spannableString.length()) {
                                        spannableString.setSpan(arrayList2.get(i3), indexOf$default, length, 33);
                                        spannableString.setSpan(new StyleSpan(style), indexOf$default, length, 33);
                                        Context context9 = subscreenDeviceModelB5.mDisplayContext;
                                        if (context9 == null) {
                                            context9 = null;
                                        }
                                        spannableString.setSpan(new ForegroundColorSpan(context9.getColor(R.color.subscreen_notification_smart_reply_disclaimer_policy_text_color)), indexOf$default, length, 33);
                                    } else if (textView != null) {
                                        textView.setText(format);
                                    }
                                    i3 = i4;
                                    r4 = null;
                                    r5 = 0;
                                }
                                Context context10 = r4;
                                if (textView != null) {
                                    textView.setText(spannableString);
                                    textView.setMovementMethod(LinkMovementMethod.getInstance());
                                }
                                if (button != null) {
                                    button.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.statusbar.notification.SubscreenDeviceModelB5$getDisclaimerViewForChina$2
                                        @Override // android.view.View.OnClickListener
                                        public final void onClick(View view2) {
                                            SubscreenNotificationDialog subscreenNotificationDialog9 = SubscreenDeviceModelB5.this.mDialog;
                                            if (subscreenNotificationDialog9 != null) {
                                                subscreenNotificationDialog9.dismiss();
                                            }
                                            SubscreenDeviceModelB5.access$handleTurnOnClick(SubscreenDeviceModelB5.this);
                                        }
                                    });
                                }
                                if (button2 != null) {
                                    button2.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.statusbar.notification.SubscreenDeviceModelB5$getDisclaimerViewForChina$3
                                        @Override // android.view.View.OnClickListener
                                        public final void onClick(View view2) {
                                            SubscreenNotificationDialog subscreenNotificationDialog9 = SubscreenDeviceModelB5.this.mDialog;
                                            if (subscreenNotificationDialog9 != null) {
                                                subscreenNotificationDialog9.dismiss();
                                            }
                                        }
                                    });
                                }
                                Runnable runnable2 = new Runnable() { // from class: com.android.systemui.statusbar.notification.SubscreenDeviceModelB5$checkAndExecuteSuggestResponses$showRunnable$1
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        SubscreenSubRoomNotification subscreenSubRoomNotification3 = SubscreenDeviceModelB5.this.mSubRoomNotification;
                                        if (subscreenSubRoomNotification3 != null) {
                                            subscreenSubRoomNotification3.mSubscreenMainLayout.setAlpha(0.0f);
                                        }
                                        final SubscreenDeviceModelB5 subscreenDeviceModelB52 = SubscreenDeviceModelB5.this;
                                        View view2 = inflate;
                                        int i5 = SubscreenDeviceModelB5.$r8$clinit;
                                        subscreenDeviceModelB52.getClass();
                                        final ScrollView scrollView = (ScrollView) view2.findViewById(R.id.smart_reply_disclaimer_scroll_container);
                                        final LinearLayout linearLayout2 = (LinearLayout) view2.findViewById(R.id.smart_reply_disclaimer_container);
                                        final Button button3 = (Button) view2.findViewById(R.id.smart_reply_disclaimer_ok_btn);
                                        Integer valueOf = scrollView != null ? Integer.valueOf(scrollView.getHeight()) : null;
                                        Integer valueOf2 = linearLayout2 != null ? Integer.valueOf(linearLayout2.getHeight()) : null;
                                        if (valueOf != null) {
                                            int intValue = valueOf.intValue();
                                            Intrinsics.checkNotNull(valueOf2);
                                            if (intValue >= valueOf2.intValue()) {
                                                if (button3 != null) {
                                                    button3.setText(subscreenDeviceModelB52.mContext.getString(R.string.subscreen_notification_dialog_ok));
                                                }
                                                if (button3 == null) {
                                                    return;
                                                }
                                                button3.setTag(null);
                                                return;
                                            }
                                            if (button3 != null) {
                                                button3.setText(subscreenDeviceModelB52.mContext.getString(R.string.subscreen_notification_dialog_more));
                                            }
                                            if (button3 != null) {
                                                button3.setTag("MORE");
                                            }
                                            scrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() { // from class: com.android.systemui.statusbar.notification.SubscreenDeviceModelB5$setMoreButtonIfNeed$1$1
                                                @Override // android.view.View.OnScrollChangeListener
                                                public final void onScrollChange(View view3, int i6, int i7, int i8, int i9) {
                                                    int height = scrollView.getHeight() + i7;
                                                    LinearLayout linearLayout3 = linearLayout2;
                                                    Integer valueOf3 = linearLayout3 != null ? Integer.valueOf(linearLayout3.getHeight()) : null;
                                                    Intrinsics.checkNotNull(valueOf3);
                                                    if (height >= valueOf3.intValue()) {
                                                        Button button4 = button3;
                                                        if (button4 != null) {
                                                            button4.setText(subscreenDeviceModelB52.mContext.getString(R.string.subscreen_notification_dialog_ok));
                                                        }
                                                        Button button5 = button3;
                                                        if (button5 != null) {
                                                            button5.setTag(null);
                                                        }
                                                        scrollView.setOnScrollChangeListener(null);
                                                    }
                                                }
                                            });
                                            if (button3 != null) {
                                                button3.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.statusbar.notification.SubscreenDeviceModelB5$setMoreButtonIfNeed$1$2
                                                    @Override // android.view.View.OnClickListener
                                                    public final void onClick(View view3) {
                                                        if ("MORE".equals(view3.getTag())) {
                                                            scrollView.fullScroll(130);
                                                            return;
                                                        }
                                                        SubscreenNotificationDialog subscreenNotificationDialog9 = subscreenDeviceModelB52.mDialog;
                                                        if (subscreenNotificationDialog9 != null) {
                                                            subscreenNotificationDialog9.dismiss();
                                                        }
                                                        SubscreenDeviceModelB5.access$handleTurnOnClick(subscreenDeviceModelB52);
                                                    }
                                                });
                                            }
                                        }
                                    }
                                };
                                Runnable runnable3 = new Runnable() { // from class: com.android.systemui.statusbar.notification.SubscreenDeviceModelB5$checkAndExecuteSuggestResponses$dismissRunnable$1
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        SubscreenSubRoomNotification subscreenSubRoomNotification3 = SubscreenDeviceModelB5.this.mSubRoomNotification;
                                        if (subscreenSubRoomNotification3 != null) {
                                            subscreenSubRoomNotification3.mSubscreenMainLayout.setAlpha(1.0f);
                                        }
                                    }
                                };
                                Context context11 = subscreenDeviceModelB5.mDisplayContext;
                                if (context11 != null) {
                                    context10 = context11;
                                }
                                SubscreenNotificationDialog subscreenNotificationDialog9 = new SubscreenNotificationDialog(context10, inflate, runnable2, runnable3);
                                subscreenDeviceModelB5.mDialog = subscreenNotificationDialog9;
                                subscreenNotificationDialog9.show();
                            } else {
                                Context context12 = null;
                                Context context13 = subscreenDeviceModelB5.mDisplayContext;
                                if (context13 == null) {
                                    context13 = null;
                                }
                                String string4 = context13.getResources().getString(R.string.subscreen_notification_smart_reply_turn_on_content);
                                Runnable runnable4 = new Runnable() { // from class: com.android.systemui.statusbar.notification.SubscreenDeviceModelB5$checkAndExecuteSuggestResponses$runnable$2
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        SubscreenDeviceModelB5 subscreenDeviceModelB52 = SubscreenDeviceModelB5.this;
                                        int i5 = SubscreenDeviceModelB5.$r8$clinit;
                                        subscreenDeviceModelB52.getMSettingsHelper().setSuggestResponsesEnabled(true);
                                        SubscreenDeviceModelB5 subscreenDeviceModelB53 = SubscreenDeviceModelB5.this;
                                        subscreenDeviceModelB53.isUnusableAccount = subscreenDeviceModelB53.isSmartReplyUnusable();
                                        SubscreenDeviceModelB5 subscreenDeviceModelB54 = SubscreenDeviceModelB5.this;
                                        subscreenDeviceModelB54.isPossibleAiReply = true ^ subscreenDeviceModelB54.isUnusableAccount;
                                        LinearLayout linearLayout2 = subscreenDeviceModelB54.suggestResponsesBtn;
                                        if (linearLayout2 != null) {
                                            linearLayout2.setVisibility(8);
                                        }
                                        final SubscreenDeviceModelB5 subscreenDeviceModelB55 = SubscreenDeviceModelB5.this;
                                        ImageView imageView4 = subscreenDeviceModelB55.smartReplyTriggerBtn;
                                        if (imageView4 != null) {
                                            imageView4.post(new Runnable() { // from class: com.android.systemui.statusbar.notification.SubscreenDeviceModelB5$checkAndExecuteSuggestResponses$runnable$2.1
                                                @Override // java.lang.Runnable
                                                public final void run() {
                                                    SubscreenSubRoomNotification subscreenSubRoomNotification3;
                                                    SubscreenRecyclerView subscreenRecyclerView3;
                                                    SubscreenRecyclerView subscreenRecyclerView4;
                                                    LinearLayout linearLayout3 = SubscreenDeviceModelB5.this.detailButtonContainer;
                                                    Float valueOf = linearLayout3 != null ? Float.valueOf(linearLayout3.getY()) : null;
                                                    SubscreenSubRoomNotification subscreenSubRoomNotification4 = SubscreenDeviceModelB5.this.mSubRoomNotification;
                                                    Integer valueOf2 = valueOf != null ? Integer.valueOf(((int) valueOf.floatValue()) - ((subscreenSubRoomNotification4 == null || (subscreenRecyclerView4 = subscreenSubRoomNotification4.mNotificationRecyclerView) == null) ? 0 : subscreenRecyclerView4.computeVerticalScrollOffset())) : null;
                                                    if (valueOf2 != null && valueOf2.intValue() == 0) {
                                                        SubscreenDeviceModelB5.this.showAIReply();
                                                    } else {
                                                        if (valueOf2 == null || (subscreenSubRoomNotification3 = SubscreenDeviceModelB5.this.mSubRoomNotification) == null || (subscreenRecyclerView3 = subscreenSubRoomNotification3.mNotificationRecyclerView) == null) {
                                                            return;
                                                        }
                                                        subscreenRecyclerView3.smoothScrollBy(0, valueOf2.intValue(), false);
                                                    }
                                                }
                                            });
                                        }
                                    }
                                };
                                Context context14 = subscreenDeviceModelB5.mDisplayContext;
                                if (context14 != null) {
                                    context12 = context14;
                                }
                                SubscreenNotificationDialog subscreenNotificationDialog10 = new SubscreenNotificationDialog(context12, string4, runnable4);
                                subscreenDeviceModelB5.mDialog = subscreenNotificationDialog10;
                                subscreenNotificationDialog10.show();
                            }
                        }
                    } else {
                        Log.d("S.S.N.", "checkAndExecuteSuggestResponses() : execute galaxy ai confirm");
                        subscreenDeviceModelB5.openPhonePopupForIntelligenceSettings("com.samsung.android.action.INTELLIGENCE_SERVICE_SETTINGS_START_INTENT");
                    }
                    SystemUIAnalytics.sendEventCDLog(SystemUIAnalytics.EID_QPNE_COVER_SCREEN_ID_DETAIL, SystemUIAnalytics.EID_QPNE_COVER_AI_SUGGEST_BUTTON, "type", imageView3.getAlpha() == 1.0f ? SystemUIAnalytics.QPBSE_KEY_ACTIVE : "dimmed");
                }
            });
        } else {
            imageView3 = null;
        }
        this.smartReplyTriggerBtn = imageView3;
        this.smartReplyErrorMessageView = (TextView) itemViewHolder.itemView.findViewById(R.id.smart_reply_error_message);
        this.suggestResponsesBtn = (LinearLayout) itemViewHolder.itemView.findViewById(R.id.smart_reply_suggest_responses_layout);
        ImageView imageView4 = (ImageView) itemViewHolder.itemView.findViewById(R.id.smart_reply_ai_disclaimer);
        if (imageView4 != null) {
            imageView4.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.statusbar.notification.SubscreenDeviceModelB5$initDetailAdapterItemViewHolder$4$1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    TextView textView;
                    SubscreenNotificationDialog subscreenNotificationDialog;
                    SubscreenNotificationDialog subscreenNotificationDialog2 = SubscreenDeviceModelB5.this.mDialog;
                    if (subscreenNotificationDialog2 != null) {
                        if (subscreenNotificationDialog2.mDialog.isShowing() && (subscreenNotificationDialog = SubscreenDeviceModelB5.this.mDialog) != null) {
                            subscreenNotificationDialog.dismiss();
                        }
                        SubscreenDeviceModelB5.this.mDialog = null;
                    }
                    String string = context.getResources().getString(R.string.subscreen_notification_smart_reply_ai_disclaimer_dialog_content);
                    String substringBefore$default = StringsKt__StringsKt.substringBefore$default(StringsKt__StringsKt.substringAfter$default(string, "%1$s"), "%2$s");
                    final SubscreenDeviceModelB5 subscreenDeviceModelB5 = SubscreenDeviceModelB5.this;
                    ClickableSpan clickableSpan = new ClickableSpan() { // from class: com.android.systemui.statusbar.notification.SubscreenDeviceModelB5$initDetailAdapterItemViewHolder$4$1$clickableSpan$1
                        @Override // android.text.style.ClickableSpan
                        public final void onClick(View view2) {
                            SubRoom.StateChangeListener stateChangeListener;
                            SubscreenNotificationDialog subscreenNotificationDialog3 = SubscreenDeviceModelB5.this.mDialog;
                            if (subscreenNotificationDialog3 != null) {
                                subscreenNotificationDialog3.dismiss();
                            }
                            Intent intent = new Intent();
                            SubscreenDeviceModelB5 subscreenDeviceModelB52 = SubscreenDeviceModelB5.this;
                            intent.setAction("android.intent.action.VIEW");
                            Context context2 = subscreenDeviceModelB52.mContext;
                            intent.setData(Uri.parse("https://policies.account.samsung.com/terms?appKey=j5p7ll8g33" + "&applicationRegion=".concat(subscreenDeviceModelB52.getIsoCountryCode()) + AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("&language=", Locale.getDefault().getLanguage()) + "&region=".concat(subscreenDeviceModelB52.getIsoCountryCode()) + "&type=TC"));
                            intent.addFlags(QuickStepContract.SYSUI_STATE_NAV_BAR_VIS_GONE);
                            PendingIntent activityAsUser = PendingIntent.getActivityAsUser(SubscreenDeviceModelB5.this.mContext, 0, intent, 201326592, null, UserHandle.CURRENT);
                            Intent intent2 = new Intent();
                            intent2.putExtra("runOnCover", false);
                            intent2.putExtra("ignoreKeyguardState", true);
                            intent2.putExtra("showCoverToast", true);
                            SubscreenSubRoomNotification subscreenSubRoomNotification = SubscreenDeviceModelB5.this.mSubRoomNotification;
                            if (subscreenSubRoomNotification == null || (stateChangeListener = subscreenSubRoomNotification.mStateChangeListener) == null) {
                                return;
                            }
                            stateChangeListener.requestCoverPopup(activityAsUser, intent2);
                        }
                    };
                    int i = StringCompanionObject.$r8$clinit;
                    SpannableString spannableString = new SpannableString(String.format(string, Arrays.copyOf(new Object[]{"", ""}, 2)));
                    int indexOf$default = StringsKt__StringsKt.indexOf$default((CharSequence) spannableString, substringBefore$default, 0, false, 6);
                    int length = substringBefore$default.length() + indexOf$default;
                    spannableString.setSpan(new StyleSpan(1), indexOf$default, length, 33);
                    spannableString.setSpan(clickableSpan, indexOf$default, length, 33);
                    Context context2 = SubscreenDeviceModelB5.this.mDisplayContext;
                    if (context2 == null) {
                        context2 = null;
                    }
                    LayoutInflater from = LayoutInflater.from(new ContextThemeWrapper(context2, R.style.Theme_SystemUI_Dialog));
                    View inflate = from != null ? from.inflate(R.layout.subscreen_notification_smart_reply_disclaimer_info, (ViewGroup) null) : null;
                    if (inflate != null && (textView = (TextView) inflate.findViewById(R.id.smart_reply_ai_disclaimer_text)) != null) {
                        textView.setText(spannableString);
                        textView.setMovementMethod(LinkMovementMethod.getInstance());
                    }
                    SubscreenDeviceModelB5 subscreenDeviceModelB52 = SubscreenDeviceModelB5.this;
                    Context context3 = subscreenDeviceModelB52.mDisplayContext;
                    subscreenDeviceModelB52.mDialog = new SubscreenNotificationDialog(context3 != null ? context3 : null, inflate);
                    SubscreenNotificationDialog subscreenNotificationDialog3 = SubscreenDeviceModelB5.this.mDialog;
                    if (subscreenNotificationDialog3 != null) {
                        subscreenNotificationDialog3.show();
                    }
                    Log.d("S.S.N.", "show smart reply ai disclaimer dialog");
                }
            });
            imageView2 = imageView4;
        }
        this.aiDisclaimerBtn = imageView2;
        this.smartReplyAiLogoText = (TextView) itemViewHolder.itemView.findViewById(R.id.smart_reply_ai_logo);
        this.isAiInfoConfirmed = getMSettingsHelper().isAiInfoConfirmed();
        this.isSuggestResponsesEnabled = getMSettingsHelper().isSuggestResponsesEnabled();
        this.isUnusableAccount = isSmartReplyUnusable();
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final void initDetailAdapterTextViewHolder(SubscreenNotificationDetailAdapter.TextViewHolder textViewHolder) {
        this.openAppButtonText = (TextView) textViewHolder.itemView.findViewById(R.id.app_open_button_text);
        this.clearButtonText = (TextView) textViewHolder.itemView.findViewById(R.id.clear_button_text);
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final void initKeyguardActioninfo() {
        KeyguardActionInfo keyguardActionInfo = this.mKeyguardActionInfo;
        if (keyguardActionInfo != null) {
            keyguardActionInfo.mAction = 0;
            keyguardActionInfo.mSubscreenParentItemViewHolder = null;
            keyguardActionInfo.mContext = null;
            keyguardActionInfo.isShowBouncer = false;
        }
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final void initMainHeaderView(LinearLayout linearLayout) {
        if (((LinearLayout) linearLayout.findViewById(R.id.header_layout)) != null) {
            linearLayout.removeViewAt(0);
        }
        Context context = this.mDisplayContext;
        if (context == null) {
            context = null;
        }
        View inflate = LayoutInflater.from(context).inflate(R.layout.subscreen_notification_adapter_header_b5, (ViewGroup) null);
        this.mHeaderViewLayout = inflate;
        linearLayout.addView(inflate, 0);
        View view = this.mHeaderViewLayout;
        if (view == null) {
            return;
        }
        view.setVisibility(8);
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final void initMainHeaderViewItems(Context context, SubscreenNotificationInfo subscreenNotificationInfo, boolean z) {
        ImageView imageView;
        ImageView imageView2;
        ImageView imageView3;
        ImageView imageView4;
        ExpandableNotificationRow expandableNotificationRow;
        boolean z2;
        ExpandableNotificationRow expandableNotificationRow2;
        boolean z3;
        ExpandableNotificationRow expandableNotificationRow3;
        NotificationEntry notificationEntry;
        NotificationChannel channel;
        ExpandableNotificationRow expandableNotificationRow4;
        ExpandableNotificationRow expandableNotificationRow5;
        View view = this.mHeaderViewLayout;
        FrameLayout frameLayout = view != null ? (FrameLayout) view.findViewById(R.id.header_app_icon_layout) : null;
        View view2 = this.mHeaderViewLayout;
        final LinearLayout linearLayout = view2 != null ? (LinearLayout) view2.findViewById(R.id.back_key) : null;
        View view3 = this.mHeaderViewLayout;
        TextView textView = view3 != null ? (TextView) view3.findViewById(R.id.subscreen_header_app_name) : null;
        View view4 = this.mHeaderViewLayout;
        ImageView imageView5 = view4 != null ? (ImageView) view4.findViewById(R.id.secure_icon) : null;
        View view5 = this.mHeaderViewLayout;
        ImageView imageView6 = view5 != null ? (ImageView) view5.findViewById(R.id.two_phone_icon) : null;
        View view6 = this.mHeaderViewLayout;
        if (view6 == null || (imageView = (ImageView) view6.findViewById(R.id.subscreen_header_app_icon)) == null) {
            imageView = null;
        } else {
            imageView.setImageDrawable(null);
            imageView.setVisibility(8);
        }
        View view7 = this.mHeaderViewLayout;
        if (view7 == null || (imageView2 = (ImageView) view7.findViewById(R.id.subscreen_header_icon)) == null) {
            imageView2 = null;
        } else {
            imageView2.setImageDrawable(null);
            imageView2.setBackground(null);
            imageView2.setPadding(0, 0, 0, 0);
            imageView2.clearColorFilter();
            imageView2.setVisibility(8);
        }
        View view8 = this.mHeaderViewLayout;
        if (view8 == null || (imageView3 = (ImageView) view8.findViewById(R.id.subscreen_notification_header_icon_conversation)) == null) {
            imageView3 = null;
        } else {
            imageView3.setImageIcon(null);
            imageView3.setVisibility(8);
        }
        View view9 = this.mHeaderViewLayout;
        if (view9 == null || (imageView4 = (ImageView) view9.findViewById(R.id.subscreen_notification_sub_icon)) == null) {
            imageView4 = null;
        } else {
            imageView4.setImageDrawable(null);
            imageView4.setBackground(null);
            imageView4.setPadding(0, 0, 0, 0);
            imageView4.clearColorFilter();
            imageView4.setVisibility(8);
        }
        Icon icon = subscreenNotificationInfo != null ? subscreenNotificationInfo.mConversationIcon : null;
        Icon icon2 = subscreenNotificationInfo != null ? subscreenNotificationInfo.mLargeIcon : null;
        Boolean valueOf = subscreenNotificationInfo != null ? Boolean.valueOf(subscreenNotificationInfo.mIsMessagingStyle) : null;
        boolean needsRedaction = (!isKeyguardStats() || subscreenNotificationInfo == null || (expandableNotificationRow5 = subscreenNotificationInfo.mRow) == null) ? false : expandableNotificationRow5.needsRedaction();
        ImageView imageView7 = imageView6;
        String string = context.getResources().getString(R.string.subscreen_back_button_content_description);
        if (linearLayout != null) {
            linearLayout.setContentDescription(string);
        }
        if (!z) {
            if (linearLayout != null) {
                linearLayout.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.statusbar.notification.SubscreenDeviceModelB5$setBackKeyClickListener$1
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view10) {
                        SubscreenDeviceModelB5 subscreenDeviceModelB5 = SubscreenDeviceModelB5.this;
                        int i = SubscreenDeviceModelB5.$r8$clinit;
                        subscreenDeviceModelB5.performBackClick();
                    }
                });
            }
            if (linearLayout != null) {
                linearLayout.addOnUnhandledKeyEventListener(new View.OnUnhandledKeyEventListener() { // from class: com.android.systemui.statusbar.notification.SubscreenDeviceModelB5$setBackKeyClickListener$2
                    @Override // android.view.View.OnUnhandledKeyEventListener
                    public final boolean onUnhandledKeyEvent(View view10, KeyEvent keyEvent) {
                        if (keyEvent.getAction() != 0 || keyEvent.getKeyCode() != 4) {
                            return false;
                        }
                        linearLayout.callOnClick();
                        return false;
                    }
                });
            }
            if (frameLayout != null) {
                frameLayout.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.statusbar.notification.SubscreenDeviceModelB5$initMainHeaderViewItems$1
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view10) {
                        SubscreenDeviceModelB5 subscreenDeviceModelB5 = SubscreenDeviceModelB5.this;
                        int i = SubscreenDeviceModelB5.$r8$clinit;
                        subscreenDeviceModelB5.performBackClick();
                    }
                });
            }
        }
        if (linearLayout != null) {
            linearLayout.setTooltipText(string);
        }
        boolean z4 = subscreenNotificationInfo != null && subscreenNotificationInfo.mIsMessagingStyle && subscreenNotificationInfo.mRemoteinput;
        if (textView != null) {
            textView.setText((isShownDetail() && z4) ? this.titleText : subscreenNotificationInfo != null ? subscreenNotificationInfo.mAppName : null);
        }
        View view10 = this.mHeaderViewLayout;
        if (view10 != null) {
            view10.setContentDescription(textView != null ? textView.getText() : null);
        }
        if (imageView != null) {
            imageView.clearColorFilter();
        }
        if (needsRedaction || !Intrinsics.areEqual(valueOf, Boolean.TRUE) || (icon == null && icon2 == null)) {
            if (isShowNotificationAppIcon()) {
                if (imageView == null || subscreenNotificationInfo == null || subscreenNotificationInfo.useSmallIcon()) {
                    if (imageView2 != null) {
                        imageView2.setVisibility(0);
                    }
                    if (imageView2 != null) {
                        imageView2.setImageDrawable(subscreenNotificationInfo != null ? subscreenNotificationInfo.mIcon : null);
                        updateSmallIconSquircleBg(imageView2, true, false);
                        updateIconColor(imageView2, (subscreenNotificationInfo == null || (expandableNotificationRow2 = subscreenNotificationInfo.mRow) == null) ? null : expandableNotificationRow2.mEntry);
                    }
                } else {
                    imageView.setImageDrawable(subscreenNotificationInfo.mAppIcon);
                    imageView.setVisibility(0);
                }
            } else if (imageView2 != null) {
                imageView2.setVisibility(0);
                updateSmallIconBg(imageView2, true, false, false);
                imageView2.setImageDrawable(subscreenNotificationInfo != null ? subscreenNotificationInfo.mIcon : null);
                updateIconColor(imageView2, (subscreenNotificationInfo == null || (expandableNotificationRow = subscreenNotificationInfo.mRow) == null) ? null : expandableNotificationRow.mEntry);
            }
            z2 = false;
        } else {
            if (imageView3 != null) {
                imageView3.setVisibility(0);
            }
            if (icon != null) {
                if (imageView3 != null) {
                    imageView3.setImageIcon(icon);
                }
            } else if (imageView3 != null) {
                imageView3.setImageIcon(icon2);
            }
            z2 = true;
        }
        if (z2) {
            if (!isShowNotificationAppIcon()) {
                updateSmallIconBg(imageView4, false, false, true);
                if (imageView4 != null) {
                    imageView4.setImageDrawable(subscreenNotificationInfo != null ? subscreenNotificationInfo.mIcon : null);
                }
                updateIconColor(imageView4, (subscreenNotificationInfo == null || (expandableNotificationRow4 = subscreenNotificationInfo.mRow) == null) ? null : expandableNotificationRow4.mEntry);
            } else if (imageView4 != null) {
                imageView4.setImageDrawable(subscreenNotificationInfo != null ? subscreenNotificationInfo.mAppIcon : null);
            }
            z3 = false;
            if (imageView4 != null) {
                imageView4.setVisibility(0);
            }
        } else {
            z3 = false;
            if (imageView4 != null) {
                imageView4.setVisibility(8);
            }
        }
        updateImportBadgeIconRing(this.mHeaderViewLayout, (!z2 || subscreenNotificationInfo == null || (expandableNotificationRow3 = subscreenNotificationInfo.mRow) == null || (notificationEntry = expandableNotificationRow3.mEntry) == null || (channel = notificationEntry.mRanking.getChannel()) == null || !channel.isImportantConversation()) ? z3 : true);
        SubscreenDeviceModelParent.updateKnoxIcon(imageView5, subscreenNotificationInfo);
        SubscreenDeviceModelParent.updateTwoPhoneIcon(imageView7, subscreenNotificationInfo);
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final void initSmartReplyStatus() {
        this.smartReplyStatus = 0;
        this.mSmartReplyResult = -1;
        this.mSmartReplyResultCompleteMsg = null;
        this.mSmartReplyResultFailureMsg = null;
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final boolean isCoverBriefAllowed(NotificationEntry notificationEntry) {
        if (getMSettingsHelper().isPopStyleBrief()) {
            if (!this.mFullScreenIntentEntries.containsKey(notificationEntry != null ? notificationEntry.mKey : null)) {
                return true;
            }
        }
        return false;
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final boolean isDismissiblePopup() {
        return this.mFullScreenIntentEntries.isEmpty() || (!this.mFullScreenIntentEntries.isEmpty() && useTopPresentation());
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final boolean isKeyguardStats() {
        KeyguardStateController keyguardStateController = this.mKeyguardStateController;
        if (keyguardStateController != null && ((KeyguardStateControllerImpl) keyguardStateController).mSecure && !keyguardStateController.isUnlocked()) {
            return true;
        }
        KeyguardStateController keyguardStateController2 = this.mKeyguardStateController;
        if (keyguardStateController2 != null) {
            KeyguardStateControllerImpl keyguardStateControllerImpl = (KeyguardStateControllerImpl) keyguardStateController2;
            if (!keyguardStateControllerImpl.mSecure && keyguardStateControllerImpl.mShowing) {
                return true;
            }
        }
        return false;
    }

    public final boolean isKeyguardUnlockShowing() {
        KeyguardStateController keyguardStateController;
        KeyguardStateController keyguardStateController2 = this.mKeyguardStateController;
        return keyguardStateController2 != null && ((KeyguardStateControllerImpl) keyguardStateController2).mSecure && keyguardStateController2.isUnlocked() && (keyguardStateController = this.mKeyguardStateController) != null && ((KeyguardStateControllerImpl) keyguardStateController).mShowing;
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final boolean isKnoxSecurity(NotificationEntry notificationEntry) {
        NotificationLockscreenUserManager notificationLockscreenUserManager = (NotificationLockscreenUserManager) Dependency.sDependency.getDependencyInner(NotificationLockscreenUserManager.class);
        return ((NotificationLockscreenUserManagerImpl) notificationLockscreenUserManager).mUsersWithSeparateWorkChallenge.get(notificationEntry.mSbn.getUserId(), false);
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final boolean isLaunchApp(NotificationEntry notificationEntry) {
        boolean z = notificationEntry.mSbn.getPackageName() != null && ActivityTaskManager.getService().isPackageEnabledForCoverLauncher(notificationEntry.mSbn.getPackageName(), notificationEntry.mSbn.getUser().getIdentifier());
        if (notificationEntry.mSbn.getNotification().contentIntent != null) {
            return ((!z && !isCallNotification(notificationEntry)) || getMSettingsHelper().isUltraPowerSavingMode() || getMSettingsHelper().isEmergencyMode() || this.mKeyguardUpdateMonitor.isKidsModeRunning()) ? false : true;
        }
        return false;
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final boolean isNotShwonNotificationState(NotificationEntry notificationEntry) {
        return isKeyguardStats() || (isKnoxSecurity(notificationEntry) && notificationEntry.mUserPublic);
    }

    public final boolean isPreventOnlineProcessing() {
        return Settings.System.getInt(this.mContext.getContentResolver(), "prevent_online_processing", 0) == 1;
    }

    public final boolean isReplyLayoutShowing() {
        SubscreenRecyclerView subscreenRecyclerView;
        SubscreenSubRoomNotification subscreenSubRoomNotification = this.mSubRoomNotification;
        Integer valueOf = (subscreenSubRoomNotification == null || (subscreenRecyclerView = subscreenSubRoomNotification.mNotificationRecyclerView) == null) ? null : Integer.valueOf(subscreenRecyclerView.computeVerticalScrollOffset());
        Intrinsics.checkNotNull(valueOf);
        int intValue = valueOf.intValue();
        Context context = this.mDisplayContext;
        int mainHeaderViewHeight = ((720 - getMainHeaderViewHeight()) - (context != null ? context : null).getResources().getDimensionPixelSize(R.dimen.subscreen_noti_cut_out_size_b5)) + intValue;
        SubscreenNotificationDetailAdapter.ItemViewHolder itemViewHolder = this.detailViewHolder;
        if (itemViewHolder != null) {
            LinearLayout linearLayout = itemViewHolder.mSmartReplyLayout;
            LinearLayout linearLayout2 = (linearLayout == null || linearLayout.getVisibility() != 0) ? itemViewHolder.mReplylayout : itemViewHolder.mSmartReplyLayout;
            if (linearLayout2 != null) {
                float y = linearLayout2.getY();
                LinearLayout linearLayout3 = itemViewHolder.mReplyContainer;
                r0 = (linearLayout3 != null ? linearLayout3.getY() : 0.0f) + y;
            }
        }
        return ((float) mainHeaderViewHeight) > r0;
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final boolean isSamsungAccountLoggedIn() {
        return this.isSALoggedIn;
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final boolean isShowNotificationAppIcon() {
        return getMSettingsHelper().isShowNotificationAppIconEnabled();
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final boolean isShowingRemoteView(String str) {
        return ArraysKt___ArraysKt.contains(new String[]{"com.skt.prod.dialer", "com.samsung.android.incallui", "com.sec.android.app.clockpackage", "com.sec.android.app.voicenote", "com.sec.android.app.voicerecorder", "com.samsung.android.app.interpreter"}, str);
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final boolean isSkipFullscreenIntentClicked(NotificationEntry notificationEntry) {
        return (this.mFullScreenIntentEntries.get(notificationEntry.mKey) != null || this.mIsFullscreenFullPopupWindowClosing) && !useTopPresentation();
    }

    public final boolean isSmartReplyUnusable() {
        if (this.isRDUMode) {
            return false;
        }
        return (this.isSALoggedIn && !this.isChildAccount && this.isAiInfoConfirmed && this.isSuggestResponsesEnabled && (!NotiRune.NOTI_SUBSCREEN_SUPPORT_SMART_REPLY_AI_FOR_CHINA || !isPreventOnlineProcessing())) ? false : true;
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final boolean isUpdatedRemoteView(String str) {
        return ArraysKt___ArraysKt.contains(new String[]{"com.skt.prod.dialer", "com.samsung.android.incallui", "com.sec.android.app.clockpackage"}, str);
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final boolean launchApp(NotificationEntry notificationEntry) {
        try {
            if (!isLaunchApp(notificationEntry)) {
                return false;
            }
            int startNotificationIntent = startNotificationIntent(notificationEntry.mSbn.getNotification().contentIntent);
            Log.d("S.S.N.", "launchApp B5 -  Run App : " + notificationEntry.mSbn.getPackageName() + ", result: " + startNotificationIntent);
            return true;
        } catch (RemoteException e) {
            Log.w("S.S.N.", "unable to get isPackageEnabledForCoverLauncher()", e);
            return false;
        }
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final boolean launchFullscreenIntent(NotificationEntry notificationEntry) {
        try {
            if (notificationEntry.mSbn.getPackageName() == null || !ActivityTaskManager.getService().isPackageEnabledForCoverLauncher(notificationEntry.mSbn.getPackageName(), notificationEntry.mSbn.getUser().getIdentifier())) {
                return false;
            }
            int startNotificationIntent = startNotificationIntent(notificationEntry.mSbn.getNotification().fullScreenIntent);
            Log.d("S.S.N.", "launchFullscreenIntent B5 -  Run FullscreenIntent : " + notificationEntry.mKey + ", result: " + startNotificationIntent);
            return true;
        } catch (RemoteException e) {
            Log.w("S.S.N.", "unable to get isPackageEnabledForCoverLauncher()", e);
            return false;
        }
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final void loadOnDeviceMetaData() {
        try {
            ((ArrayList) this.onDeviceLanguageList).clear();
            String replace$default = StringsKt__StringsJVMKt.replace$default(StringsKt__StringsJVMKt.replace$default(new Regex("\\s+").replace("{ \"list\" : " + getOnDeviceMetaData() + "}", ""), ",}", "}"), ",]", "]");
            if (replace$default.length() > 0) {
                new JsonParser();
                JsonElement jsonElement = JsonParser.parseString(replace$default).getAsJsonObject().get("list");
                if (jsonElement != null) {
                    if (!(jsonElement instanceof JsonArray)) {
                        throw new IllegalStateException("Not a JSON Array: " + jsonElement);
                    }
                    Iterator it = ((ArrayList) ((JsonArray) jsonElement).elements).iterator();
                    while (it.hasNext()) {
                        JsonObject asJsonObject = ((JsonElement) it.next()).getAsJsonObject();
                        LlmLanguage llmLanguage = new LlmLanguage(0, null, null, false, false, false, 63, null);
                        JsonElement jsonElement2 = asJsonObject.get("order");
                        llmLanguage.order = jsonElement2 != null ? jsonElement2.getAsInt() : -1;
                        JsonElement jsonElement3 = asJsonObject.get("language");
                        String asString = jsonElement3 != null ? jsonElement3.getAsString() : null;
                        if (asString == null) {
                            asString = "";
                        }
                        llmLanguage.language = asString;
                        llmLanguage.languageDisplayName = getDisplayName(asString);
                        JsonElement jsonElement4 = asJsonObject.get("supportToneConversion");
                        llmLanguage.supportToneConversion = jsonElement4 != null ? jsonElement4.getAsBoolean() : true;
                        JsonElement jsonElement5 = asJsonObject.get("supportCorrection");
                        llmLanguage.supportCorrection = jsonElement5 != null ? jsonElement5.getAsBoolean() : true;
                        JsonElement jsonElement6 = asJsonObject.get("supportReply");
                        boolean asBoolean = jsonElement6 != null ? jsonElement6.getAsBoolean() : true;
                        llmLanguage.supportReply = asBoolean;
                        if (llmLanguage.order >= 0 && asBoolean) {
                            ((ArrayList) this.onDeviceLanguageList).add(llmLanguage);
                        }
                    }
                }
            }
            Log.d("S.S.N.", "loadOnDeviceMetaData : " + ((ArrayList) this.onDeviceLanguageList).size());
        } catch (Exception e) {
            AbsAdapter$1$$ExternalSyntheticOutline0.m("loadOnDeviceMetaData e: ", e, "S.S.N.");
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:87:0x015b, code lost:
    
        if ((r6 != null ? r6.mPrevLastHistoryView : null) == null) goto L99;
     */
    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void moveDetailAdapterContentScroll(android.view.View r17, boolean r18, boolean r19, boolean r20) {
        /*
            Method dump skipped, instructions count: 821
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.SubscreenDeviceModelB5.moveDetailAdapterContentScroll(android.view.View, boolean, boolean, boolean):void");
    }

    /* JADX WARN: Type inference failed for: r8v38, types: [T, java.lang.String] */
    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelCommon, com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final void onBindDetailAdapterItemViewHolder(SubscreenNotificationDetailAdapter subscreenNotificationDetailAdapter, SubscreenNotificationDetailAdapter.ItemViewHolder itemViewHolder) {
        int dimensionPixelSize;
        ImageView imageView;
        int size;
        Resources resources;
        super.onBindDetailAdapterItemViewHolder(subscreenNotificationDetailAdapter, itemViewHolder);
        this.progressLayout = (LinearLayout) itemViewHolder.itemView.findViewById(R.id.smart_reply_progress_layout);
        this.progressingVi = (LottieAnimationView) itemViewHolder.itemView.findViewById(R.id.smart_reply_progressing_lottie);
        this.detailViewHolder = itemViewHolder;
        TextView textView = this.smartReplyErrorMessageView;
        if (textView != null) {
            textView.setAlpha(0.0f);
        }
        TextView textView2 = this.smartReplyErrorMessageView;
        if (textView2 != null) {
            textView2.setVisibility(8);
        }
        ImageView imageView2 = this.smartReplyTriggerBtn;
        if (imageView2 != null) {
            imageView2.setVisibility(8);
        }
        TextView textView3 = this.smartReplyAiLogoText;
        if (textView3 != null) {
            textView3.setVisibility(8);
        }
        SubscreenNotificationInfo subscreenNotificationInfo = itemViewHolder.mInfo;
        if (subscreenNotificationInfo.mIsMessagingStyle && subscreenNotificationInfo.mRemoteinput && subscreenNotificationDetailAdapter.mItemPostionInGroup <= 8) {
            if (NotiRune.NOTI_SUBSCREEN_SUPPORT_SMART_REPLY_AI || NotiRune.NOTI_SUBSCREEN_SUPPORT_SMART_REPLY_AI_FOR_CHINA) {
                this.isPossibleAiReply = !this.isUnusableAccount;
                if (this.isSuggestResponsesEnabled || (NotiRune.NOTI_SUBSCREEN_SUPPORT_SMART_REPLY_AI_FOR_CHINA && !getMSettingsHelper().isSuggestResponsesUsed())) {
                    ImageView imageView3 = this.smartReplyTriggerBtn;
                    if (imageView3 != null) {
                        imageView3.setVisibility(0);
                    }
                    if (!this.isUnusableAccount && (size = itemViewHolder.mInfo.mMessageingStyleInfoArray.size()) > 0) {
                        String historyInfo = getHistoryInfo(itemViewHolder);
                        if (historyInfo == null || historyInfo.length() == 0) {
                            enableSmartReplyTriggerBtn("emptyMessage", false);
                            updateVisibilityForSmartReplyLayout(8);
                        } else {
                            final NeuralTranslator neuralTranslator = new NeuralTranslator(this.mContext);
                            final Ref$ObjectRef ref$ObjectRef = new Ref$ObjectRef();
                            ref$ObjectRef.element = ((SubscreenNotificationInfo.MessagingStyleInfo) itemViewHolder.mInfo.mMessageingStyleInfoArray.get(size - 1)).mContentText;
                            neuralTranslator.refresh().addOnCompleteListener(new OnCompleteListener() { // from class: com.android.systemui.statusbar.notification.SubscreenDeviceModelB5$onBindDetailAdapterItemViewHolder$1
                                /* JADX WARN: Multi-variable type inference failed */
                                @Override // com.samsung.android.sdk.scs.base.tasks.OnCompleteListener
                                public final void onComplete(Task task) {
                                    TaskImpl identifyLanguage = NeuralTranslator.this.identifyLanguage((String) ref$ObjectRef.element);
                                    final SubscreenDeviceModelB5 subscreenDeviceModelB5 = this;
                                    identifyLanguage.addOnCompleteListener(new OnCompleteListener() { // from class: com.android.systemui.statusbar.notification.SubscreenDeviceModelB5$onBindDetailAdapterItemViewHolder$1.1
                                        @Override // com.samsung.android.sdk.scs.base.tasks.OnCompleteListener
                                        public final void onComplete(Task task2) {
                                            if (task2.isSuccessful()) {
                                                String valueOf = String.valueOf(task2.getResult());
                                                SubscreenDeviceModelB5 subscreenDeviceModelB52 = SubscreenDeviceModelB5.this;
                                                boolean access$isSupportableLanguage = SubscreenDeviceModelB5.access$isSupportableLanguage(subscreenDeviceModelB52, valueOf);
                                                boolean isSuccessful = task2.isSuccessful();
                                                boolean isComplete = task2.isComplete();
                                                Object result = task2.getResult();
                                                StringBuilder m = KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0.m("onBindDetailAdapterItemViewHolder - successful : ", ", isComplete : ", ", result : ", isSuccessful, isComplete);
                                                m.append(result);
                                                m.append(", isSupport : ");
                                                m.append(access$isSupportableLanguage);
                                                Log.d("S.S.N.", m.toString());
                                                if (!access$isSupportableLanguage) {
                                                    subscreenDeviceModelB52.enableSmartReplyTriggerBtn("unsupportedLanguage", false);
                                                    subscreenDeviceModelB52.updateVisibilityForSmartReplyLayout(8);
                                                } else {
                                                    if (subscreenDeviceModelB52.isReplyLayoutShowing()) {
                                                        return;
                                                    }
                                                    subscreenDeviceModelB52.enableSmartReplyTriggerBtn("", true);
                                                }
                                            }
                                        }
                                    });
                                }
                            });
                        }
                    }
                } else if (this.isRDUMode) {
                    ImageView imageView4 = this.smartReplyTriggerBtn;
                    if (imageView4 != null) {
                        imageView4.setVisibility(0);
                    }
                } else {
                    ImageView imageView5 = this.smartReplyTriggerBtn;
                    if (imageView5 != null) {
                        imageView5.setVisibility(8);
                    }
                }
            } else {
                Log.d("S.S.N.", " NOT SUPPORT SMART REPLY AI");
                LinearLayout linearLayout = this.progressLayout;
                if (linearLayout != null) {
                    linearLayout.setVisibility(8);
                }
                updateVisibilityForSmartReplyLayout(8);
            }
            ImageView imageView6 = this.keyboardReplyButton;
            if (imageView6 != null) {
                imageView6.setVisibility(0);
            }
            itemViewHolder.inflateReplyWord();
            itemViewHolder.mReplylayout.setVisibility(0);
            setEditButton(itemViewHolder);
            ImageView imageView7 = (ImageView) itemViewHolder.mReplylayout.findViewById(R.id.subscreen_detail_word_line_top);
            if (imageView7 != null) {
                imageView7.setVisibility(8);
            }
            itemViewHolder.mTitle.setVisibility(8);
            itemViewHolder.mBodyLayout.setBackgroundColor(0);
            LinearLayout linearLayout2 = itemViewHolder.mBodyLayout;
            Context context = this.mDisplayContext;
            if (context == null) {
                context = null;
            }
            linearLayout2.setPadding(0, context.getResources().getDimensionPixelSize(R.dimen.subscreen_noti_detail_conversation_top_padding_b5), 0, 0);
            LinearLayout linearLayout3 = itemViewHolder.mBodyLayout;
            Context context2 = this.mDisplayContext;
            if (context2 == null) {
                context2 = null;
            }
            Integer valueOf = (context2 == null || (resources = context2.getResources()) == null) ? null : Integer.valueOf(resources.getDimensionPixelSize(R.dimen.subscreen_noti_detail_conversation_body_min_height_b5));
            Intrinsics.checkNotNull(valueOf);
            linearLayout3.setMinimumHeight(valueOf.intValue());
            LinearLayout linearLayout4 = this.detailButtonContainer;
            if (linearLayout4 != null) {
                linearLayout4.setPadding(0, 0, 0, 0);
            }
            LinearLayout linearLayout5 = this.detailButtonContainer;
            if (linearLayout5 != null) {
                linearLayout5.measure(0, 0);
            }
            Context context3 = this.mDisplayContext;
            if (context3 == null) {
                context3 = null;
            }
            int dimensionPixelSize2 = context3.getResources().getDimensionPixelSize(R.dimen.subscreen_noti_detail_icon_button_bg_size_b5);
            Context context4 = this.mDisplayContext;
            if (context4 == null) {
                context4 = null;
            }
            int dimensionPixelSize3 = context4.getResources().getDimensionPixelSize(R.dimen.subscreen_noti_detail_icon_button_shadow_margin_b5);
            LinearLayout linearLayout6 = this.detailButtonContainer;
            Integer valueOf2 = linearLayout6 != null ? Integer.valueOf(linearLayout6.getMeasuredHeight()) : null;
            Intrinsics.checkNotNull(valueOf2);
            int intValue = (valueOf2.intValue() - (dimensionPixelSize2 / 2)) - dimensionPixelSize3;
            Context context5 = this.mDisplayContext;
            if (context5 == null) {
                context5 = null;
            }
            int dimensionPixelSize4 = context5.getResources().getDimensionPixelSize(R.dimen.subscreen_noti_detail_reply_container_top_margin_b5);
            LinearLayout linearLayout7 = itemViewHolder.mReplyContainer;
            if (linearLayout7 != null) {
                linearLayout7.setPadding(linearLayout7.getPaddingLeft(), dimensionPixelSize4 + intValue, linearLayout7.getPaddingRight(), linearLayout7.getPaddingBottom());
            }
            Context context6 = this.mDisplayContext;
            if (context6 == null) {
                context6 = null;
            }
            int dimensionPixelSize5 = context6.getResources().getDimensionPixelSize(R.dimen.subscreen_noti_detail_ai_disclaimer_button_margin_top_b5);
            LinearLayout linearLayout8 = (LinearLayout) itemViewHolder.itemView.findViewById(R.id.smart_reply_ai_disclaimer_layout);
            if (linearLayout8 != null) {
                linearLayout8.setPadding(linearLayout8.getPaddingLeft(), intValue + dimensionPixelSize5, linearLayout8.getPaddingRight(), linearLayout8.getPaddingBottom());
            }
        } else {
            ImageView imageView8 = this.keyboardReplyButton;
            if (imageView8 != null) {
                imageView8.setVisibility(8);
            }
            if (itemViewHolder.mInfo.mRemoteinput && (imageView = this.keyboardReplyButton) != null) {
                imageView.setVisibility(0);
            }
            itemViewHolder.mReplylayout.setVisibility(8);
            setEditButton(itemViewHolder);
            updateVisibilityForSmartReplyLayout(8);
            LinearLayout linearLayout9 = this.progressLayout;
            if (linearLayout9 != null) {
                linearLayout9.setVisibility(8);
            }
            LinearLayout linearLayout10 = this.suggestResponsesBtn;
            if (linearLayout10 != null) {
                linearLayout10.setVisibility(8);
            }
            TextView textView4 = itemViewHolder.mTitle;
            CharSequence text = textView4.getText();
            if (text == null || text.length() == 0 || StringsKt__StringsJVMKt.isBlank(textView4.getText())) {
                textView4.setVisibility(8);
                Context context7 = this.mDisplayContext;
                if (context7 == null) {
                    context7 = null;
                }
                dimensionPixelSize = context7.getResources().getDimensionPixelSize(R.dimen.subscreen_noti_detail_content_top_margin_b5);
            } else {
                textView4.setVisibility(0);
                dimensionPixelSize = 0;
            }
            itemViewHolder.mBodyLayout.setBackgroundResource(R.drawable.subscreen_notification_detail_type_item_background_b5);
            itemViewHolder.mBodyLayout.setPadding(0, dimensionPixelSize, 0, 0);
            LinearLayout linearLayout11 = this.detailButtonContainer;
            if (linearLayout11 != null) {
                Context context8 = this.mDisplayContext;
                if (context8 == null) {
                    context8 = null;
                }
                linearLayout11.setPadding(0, 0, 0, context8.getResources().getDimensionPixelSize(R.dimen.subscreen_noti_cutout_area_height_b5));
            }
            LinearLayout linearLayout12 = itemViewHolder.mBodyLayout;
            Context context9 = this.mDisplayContext;
            if (context9 == null) {
                context9 = null;
            }
            linearLayout12.setMinimumHeight(context9.getResources().getDimensionPixelSize(R.dimen.subscreen_noti_detail_body_min_height_b5));
        }
        itemViewHolder.mOpenAppButton.setVisibility(8);
        TextView textView5 = this.callBackButtonText;
        if (textView5 != null) {
            textView5.setVisibility(itemViewHolder.mCallBackButton.getVisibility());
        }
        TextView textView6 = this.replyButtonText;
        if (textView6 != null) {
            ImageView imageView9 = this.keyboardReplyButton;
            Integer valueOf3 = imageView9 != null ? Integer.valueOf(imageView9.getVisibility()) : null;
            Intrinsics.checkNotNull(valueOf3);
            textView6.setVisibility(valueOf3.intValue());
        }
        TextView textView7 = this.openAppButtonText;
        if (textView7 != null) {
            textView7.setVisibility(itemViewHolder.mOpenAppButton.getVisibility());
        }
        TextView textView8 = this.clearButtonText;
        if (textView8 != null) {
            textView8.setVisibility(itemViewHolder.mClearButton.getVisibility());
        }
        TextView textView9 = this.smartReplyTriggerBtnText;
        if (textView9 == null) {
            return;
        }
        ImageView imageView10 = this.smartReplyTriggerBtn;
        textView9.setVisibility(imageView10 != null ? imageView10.getVisibility() : 8);
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final void onBindDetailAdapterTextViewHolder(SubscreenNotificationDetailAdapter.TextViewHolder textViewHolder) {
        View view = textViewHolder.mOpenAppButton;
        if (view != null) {
            view.setVisibility(8);
        }
        TextView textView = this.openAppButtonText;
        if (textView != null) {
            textView.setVisibility(textViewHolder.mOpenAppButton.getVisibility());
        }
        TextView textView2 = this.clearButtonText;
        if (textView2 == null) {
            return;
        }
        textView2.setVisibility(textViewHolder.mClearButton.getVisibility());
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final void onStateChangedInDeviceStateCallback(android.hardware.devicestate.DeviceState deviceState) {
        SubscreenSubRoomNotification subscreenSubRoomNotification;
        SubscreenNotificationDetailAdapter subscreenNotificationDetailAdapter;
        this.mIsFlexMode = deviceState.getIdentifier() == 1;
        if (!isShownDetail() || (subscreenSubRoomNotification = this.mSubRoomNotification) == null || (subscreenNotificationDetailAdapter = subscreenSubRoomNotification.mNotificationDetailAdapter) == null) {
            return;
        }
        subscreenNotificationDetailAdapter.dismissReplyButtons(true);
    }

    public final void openPhonePopupForIntelligenceSettings(String str) {
        SubRoom.StateChangeListener stateChangeListener;
        try {
            Intent intent = new Intent();
            intent.setAction(str);
            PendingIntent broadcast = PendingIntent.getBroadcast(this.mContext, 0, intent, 335544320);
            Intent intent2 = new Intent();
            intent2.putExtra("runOnCover", false);
            intent2.putExtra("ignoreKeyguardState", true);
            intent2.putExtra("showCoverToast", true);
            SubscreenSubRoomNotification subscreenSubRoomNotification = this.mSubRoomNotification;
            if (subscreenSubRoomNotification == null || (stateChangeListener = subscreenSubRoomNotification.mStateChangeListener) == null) {
                return;
            }
            stateChangeListener.requestCoverPopup(broadcast, intent2);
        } catch (Exception e) {
            AbsAdapter$1$$ExternalSyntheticOutline0.m("exception on openPhonePopupForIntelligenceSettings: ", e, "S.S.N.");
        }
    }

    public final boolean performBackClick() {
        SubscreenNotificationGroupAdapter subscreenNotificationGroupAdapter;
        SubscreenNotificationInfo subscreenNotificationInfo;
        boolean z = false;
        if (!isShownDetail()) {
            if (isShownGroup()) {
                SubscreenSubRoomNotification subscreenSubRoomNotification = this.mSubRoomNotification;
                String str = (subscreenSubRoomNotification == null || (subscreenNotificationGroupAdapter = subscreenSubRoomNotification.mNotificationGroupAdapter) == null || (subscreenNotificationInfo = subscreenNotificationGroupAdapter.mSummaryInfo) == null) ? null : subscreenNotificationInfo.mKey;
                if (subscreenSubRoomNotification != null) {
                    subscreenSubRoomNotification.mRecyclerViewItemSelectKey = str;
                }
                if (this.mMainViewAnimator == null) {
                    hideGroupNotification();
                }
            }
            this.mSrPromptProcessor.setNotificationKey(null);
            Log.d("S.S.N.", "performBackClick() - ret: " + z);
            return z;
        }
        if (this.mMainViewAnimator == null) {
            hideDetailNotificationAnimated(300, false);
        }
        SystemUIAnalytics.sendEventLog(SystemUIAnalytics.EID_QPNE_COVER_SCREEN_ID_DETAIL, SystemUIAnalytics.EID_QPNE_COVER_UP_BUTTON_IN_DETAIL_VIEW);
        z = true;
        this.mSrPromptProcessor.setNotificationKey(null);
        Log.d("S.S.N.", "performBackClick() - ret: " + z);
        return z;
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final void registerAODTspReceiver() {
        this.mContext.registerReceiver(this.aodTspUpdateReceiver, AppCompatDelegateImpl$$ExternalSyntheticOutline0.m("com.samsung.android.app.aodservice.intent.action.CHANGE_AOD_MODE"), "com.samsung.android.app.aodservice.permission.BROADCAST_RECEIVER", null);
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final void releaseSmartReply() {
        this.mSrPromptProcessor.releaseSmartReply();
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final void removeSmartReplyHashMap(String str) {
        this.mSmartReplyHashMap.remove(str);
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final void replyActivityFinished(boolean z) {
        SubscreenSubRoomNotification subscreenSubRoomNotification = this.mSubRoomNotification;
        if (subscreenSubRoomNotification != null) {
            subscreenSubRoomNotification.mNotificationRecyclerView.requestFocus();
            updateMainHeaderView(subscreenSubRoomNotification.mSubscreenMainLayout);
            if (z) {
                updateMainHeaderViewVisibility(8);
                subscreenSubRoomNotification.mSubscreenMainLayout.setAlpha(1.0f);
                Log.d("S.S.N.", "replyActivityFinished() - forcedFinish");
                return;
            }
            SubscreenNotificationInfo subscreenNotificationInfo = subscreenSubRoomNotification.mIsShownDetail ? subscreenSubRoomNotification.mNotificationDetailAdapter.mSelectNotificationInfo : subscreenSubRoomNotification.mIsShownGroup ? subscreenSubRoomNotification.mNotificationGroupAdapter.mSummaryInfo : null;
            if (subscreenNotificationInfo != null) {
                initMainHeaderViewItems(this.mContext, subscreenNotificationInfo, false);
            }
            int i = (subscreenSubRoomNotification.mIsShownGroup || subscreenSubRoomNotification.mIsShownDetail) ? 0 : 8;
            updateMainHeaderViewVisibility(i);
            if (subscreenSubRoomNotification.mIsShownDetail) {
                subscreenSubRoomNotification.mSubscreenMainLayout.animate().alpha(1.0f).setDuration(300L);
            }
            ListPopupWindow$$ExternalSyntheticOutline0.m(i, "replyActivityFinished() - header visibility: ", "S.S.N.");
        }
    }

    public final void resetProgressScaleAnimation() {
        SpringAnimation springAnimation = this.mProgressScaleAnimationX;
        if (springAnimation != null) {
            springAnimation.cancel();
        }
        SpringAnimation springAnimation2 = this.mProgressScaleAnimationY;
        if (springAnimation2 != null) {
            springAnimation2.cancel();
        }
        this.mProgressScaleAnimationX = null;
        this.mProgressScaleAnimationY = null;
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final void runSmartReplyUncompletedOperation() {
        LinearLayout linearLayout;
        ListPopupWindow$$ExternalSyntheticOutline0.m(this.mSmartReplyResult, "runSmartReplyUncompletedOperation() - ", "S.S.N.");
        int i = this.mSmartReplyResult;
        if (i == 0) {
            StringBuilder sb = this.mSmartReplyResultCompleteMsg;
            if (sb != null) {
                showSmartReplyResultComplete(sb);
            }
        } else if (i == 1) {
            showSmartReplyResultFailure(this.mSmartReplyResultFailureMsg);
        }
        LinearLayout linearLayout2 = this.progressLayout;
        if (linearLayout2 != null && linearLayout2.getVisibility() == 0 && (linearLayout = this.progressLayout) != null) {
            linearLayout.setVisibility(8);
        }
        this.mSmartReplyResult = -1;
        this.mSmartReplyResultCompleteMsg = null;
        this.mSmartReplyResultFailureMsg = null;
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final void setClock(SubscreenNotificationInfo subscreenNotificationInfo, View view) {
        NotificationChildrenContainer notificationChildrenContainer;
        DateTimeView dateTimeView = view != null ? (DateTimeView) view.findViewById(R.id.subscreen_notification_clock) : null;
        if (dateTimeView == null || subscreenNotificationInfo == null) {
            return;
        }
        if (!subscreenNotificationInfo.mShowWhen || subscreenNotificationInfo.mWhen <= 0) {
            dateTimeView.setVisibility(8);
            return;
        }
        if (!subscreenNotificationInfo.mIsMessagingStyle || subscreenNotificationInfo.mMessageingStyleInfoArray.size() <= 0) {
            long j = subscreenNotificationInfo.mWhen;
            if (subscreenNotificationInfo.mSbn.getNotification().isGroupSummary() && (notificationChildrenContainer = subscreenNotificationInfo.mRow.mChildrenContainer) != null) {
                j = notificationChildrenContainer.mWhenMillis;
            }
            dateTimeView.setTime(j);
            dateTimeView.setVisibility(0);
            return;
        }
        long j2 = ((SubscreenNotificationInfo.MessagingStyleInfo) AlertController$$ExternalSyntheticOutline0.m(subscreenNotificationInfo.mMessageingStyleInfoArray, 1)).mPostedTime;
        long j3 = ((SubscreenNotificationInfo.MessagingStyleInfo) AlertController$$ExternalSyntheticOutline0.m(subscreenNotificationInfo.mMessageingStyleInfoArray, 1)).mTimeStamp;
        if (j2 <= 0) {
            j2 = j3;
        }
        dateTimeView.setTime(j2);
        dateTimeView.setVisibility(0);
    }

    /* JADX WARN: Removed duplicated region for block: B:110:0x014e  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x010e  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x0162  */
    /* JADX WARN: Removed duplicated region for block: B:67:0x0240  */
    /* JADX WARN: Removed duplicated region for block: B:73:0x0233  */
    /* JADX WARN: Removed duplicated region for block: B:77:0x0168  */
    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void setContentViewItem(android.content.Context r23, com.android.systemui.statusbar.notification.SubscreenNotificationDetailAdapter.ItemViewHolder r24) {
        /*
            Method dump skipped, instructions count: 839
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.SubscreenDeviceModelB5.setContentViewItem(android.content.Context, com.android.systemui.statusbar.notification.SubscreenNotificationDetailAdapter$ItemViewHolder):void");
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final void setDimOnMainBackground(View view) {
        view.setBackgroundResource(R.drawable.subscreen_notification_main_layout_background_b5);
        view.setClipToOutline(true);
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final void setFullPopupWindowKeyEventListener(FrameLayout frameLayout) {
        if (frameLayout != null) {
            frameLayout.addOnUnhandledKeyEventListener(new View.OnUnhandledKeyEventListener() { // from class: com.android.systemui.statusbar.notification.SubscreenDeviceModelB5$setFullPopupWindowKeyEventListener$1
                @Override // android.view.View.OnUnhandledKeyEventListener
                public final boolean onUnhandledKeyEvent(View view, KeyEvent keyEvent) {
                    if (keyEvent.getAction() != 0 || keyEvent.getKeyCode() != 4) {
                        return false;
                    }
                    SubscreenDeviceModelB5.this.closeFullscreenFullPopupWindow();
                    return false;
                }
            });
        }
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final void setGroupAdapterFooterMargin(Context context, RecyclerView.ViewHolder viewHolder) {
        RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) viewHolder.itemView.getLayoutParams();
        context.getResources().getDimensionPixelSize(R.dimen.subscreen_noti_group_footer_top_margin_b5);
        ((ViewGroup.MarginLayoutParams) layoutParams).topMargin = 0;
        viewHolder.itemView.setLayoutParams(layoutParams);
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final void setGroupAdapterIcon(Context context, SubscreenNotificationGroupAdapter subscreenNotificationGroupAdapter, SubscreenNotificationGroupAdapter.NotificationGroupItemViewHolder notificationGroupItemViewHolder) {
        boolean z = true;
        if (isNotShwonNotificationState(notificationGroupItemViewHolder.mInfo.mRow.mEntry)) {
            if (isKnoxSecurity(notificationGroupItemViewHolder.mInfo.mRow.mEntry) && notificationGroupItemViewHolder.mInfo.mRow.mEntry.mUserPublic) {
                z = false;
            } else if (isKeyguardStats()) {
                z = true ^ notificationGroupItemViewHolder.mInfo.mRow.needsRedaction();
            }
        }
        notificationGroupItemViewHolder.setIconView(subscreenNotificationGroupAdapter, z);
        setRightIcon(context, notificationGroupItemViewHolder.mInfo, notificationGroupItemViewHolder.itemView);
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final void setIsReplySendButtonLoading() {
        View findViewById;
        SubscreenNotificationDetailAdapter subscreenNotificationDetailAdapter;
        this.mIsReplySendButtonLoading = false;
        SubscreenSubRoomNotification subscreenSubRoomNotification = this.mSubRoomNotification;
        View view = (subscreenSubRoomNotification == null || (subscreenNotificationDetailAdapter = subscreenSubRoomNotification.mNotificationDetailAdapter) == null) ? null : subscreenNotificationDetailAdapter.mReplyButtonView;
        if (view == null || (findViewById = view.findViewById(R.id.send)) == null) {
            return;
        }
        findViewById.setEnabled(true);
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final void setItemDecoration(final RecyclerView recyclerView) {
        Context context = this.mDisplayContext;
        if (context == null) {
            context = null;
        }
        final int dimensionPixelSize = context.getResources().getDimensionPixelSize(R.dimen.subscreen_noti_list_recyclerview_top_margin_b5);
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() { // from class: com.android.systemui.statusbar.notification.SubscreenDeviceModelB5$setItemDecoration$1
            @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
            public final void getItemOffsets(Rect rect, View view, RecyclerView recyclerView2, RecyclerView.State state) {
                RecyclerView.Adapter adapter = RecyclerView.this.mAdapter;
                SubscreenDeviceModelB5 subscreenDeviceModelB5 = this;
                SubscreenSubRoomNotification subscreenSubRoomNotification = subscreenDeviceModelB5.mSubRoomNotification;
                if (Intrinsics.areEqual(adapter, subscreenSubRoomNotification != null ? subscreenSubRoomNotification.mNotificationListAdapter : null)) {
                    SubscreenSubRoomNotification subscreenSubRoomNotification2 = subscreenDeviceModelB5.mSubRoomNotification;
                    if (subscreenSubRoomNotification2 == null || subscreenSubRoomNotification2.mNotificationInfoManager == null || SubscreenNotificationInfoManager.getSubscreenNotificationInfoListSize() != 0) {
                        recyclerView2.getClass();
                        if (RecyclerView.getChildAdapterPosition(view) == 0) {
                            rect.top = dimensionPixelSize;
                        }
                    }
                }
            }
        });
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final void setListItemTextLayout(Context context, View view) {
        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.subscreen_notification_text_layout);
        TextView textView = (TextView) view.findViewById(R.id.subscreen_notification_title_text);
        DateTimeView findViewById = view.findViewById(R.id.subscreen_notification_clock);
        ImageView imageView = (ImageView) view.findViewById(R.id.subscreen_right_icon);
        ImageView imageView2 = (ImageView) view.findViewById(R.id.two_phone_icon);
        ImageView imageView3 = (ImageView) view.findViewById(R.id.secure_icon);
        TextView textView2 = (TextView) view.findViewById(R.id.unread_message_count);
        int dimensionPixelSize = context.getResources().getDimensionPixelSize(R.dimen.subscreen_noti_top_popup_text_layout_max_width_b5);
        if (findViewById != null && findViewById.getVisibility() == 0) {
            dimensionPixelSize = (dimensionPixelSize - context.getResources().getDimensionPixelSize(R.dimen.subscreen_noti_clock_start_margin_b5)) - context.getResources().getDimensionPixelSize(R.dimen.subscreen_noti_top_popup_clock_width_b5);
        }
        if (linearLayout != null) {
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) linearLayout.getLayoutParams();
            if (imageView == null || imageView.getVisibility() != 0) {
                layoutParams.setMarginEnd(context.getResources().getDimensionPixelSize(R.dimen.subscreen_noti_list_end_margin_b5));
            } else {
                layoutParams.setMarginEnd(0);
                dimensionPixelSize -= context.getResources().getDimensionPixelSize(R.dimen.subscreen_noti_icon_size_b5);
                context.getResources().getDimensionPixelSize(R.dimen.subscreen_noti_extra_icon_margin_b5);
            }
            linearLayout.setLayoutParams(layoutParams);
        }
        if (imageView2 != null && imageView2.getVisibility() == 0) {
            dimensionPixelSize -= context.getResources().getDimensionPixelSize(R.dimen.subscreen_noti_two_phone_icon_width_b5);
            context.getResources().getDimensionPixelSize(R.dimen.subscreen_noti_extra_icon_margin_b5);
        }
        if (imageView3 != null && imageView3.getVisibility() == 0) {
            dimensionPixelSize -= context.getResources().getDimensionPixelSize(R.dimen.subscreen_noti_secure_icon_size_b5);
            context.getResources().getDimensionPixelSize(R.dimen.subscreen_noti_extra_icon_margin_b5);
        }
        if (textView2 != null && textView2.getVisibility() == 0) {
            dimensionPixelSize -= context.getResources().getDimensionPixelSize(R.dimen.subscreen_noti_count_text_width_b5);
            context.getResources().getDimensionPixelSize(R.dimen.subscreen_noti_count_start_margin_b5);
        }
        if (textView == null) {
            return;
        }
        textView.setMaxWidth(dimensionPixelSize);
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelCommon
    public final void setPopupItemInfo(Context context, NotificationEntry notificationEntry, boolean z, boolean z2) {
        View view;
        TextView textView;
        SubscreenNotificationInfo subscreenNotificationInfo;
        ArrayList arrayList;
        SubscreenNotificationInfo.MessagingStyleInfo messagingStyleInfo;
        String str;
        super.setPopupItemInfo(context, notificationEntry, z, z2);
        SubscreenNotificationInfo subscreenNotificationInfo2 = this.popupInfo;
        boolean z3 = subscreenNotificationInfo2 != null ? subscreenNotificationInfo2.mIsGroupConversation : false;
        if (!z && !this.needsRedaction && z3 && (view = this.mPopUpViewLayout) != null && (textView = (TextView) view.findViewById(R.id.subscreen_notification_sender_text)) != null && (subscreenNotificationInfo = this.popupInfo) != null && (arrayList = subscreenNotificationInfo.mMessageingStyleInfoArray) != null && (messagingStyleInfo = (SubscreenNotificationInfo.MessagingStyleInfo) CollectionsKt___CollectionsKt.lastOrNull(arrayList)) != null && (str = messagingStyleInfo.mSender) != null) {
            SubscreenNotificationInfo subscreenNotificationInfo3 = this.popupInfo;
            String title = subscreenNotificationInfo3 != null ? subscreenNotificationInfo3.getTitle() : null;
            if (str.length() > 0 && !str.equals(title)) {
                textView.setText(str);
                textView.setVisibility(0);
            }
        }
        if (z || useTopPresentation()) {
            setClock(this.popupInfo, this.mPopUpViewLayout);
        }
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelCommon
    public final void setPopupViewLayout(Context context, boolean z, FrameLayout frameLayout) {
        this.mPopUpViewLayout = (z || useTopPresentation()) ? LayoutInflater.from(context).inflate(R.layout.subscreen_notification_detail_popup_top_b5, frameLayout) : LayoutInflater.from(context).inflate(R.layout.subscreen_notification_detail_popup_full_b5, frameLayout);
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final void setQuickReplyFocusBackground(View view) {
        Context context = this.mDisplayContext;
        if (context == null) {
            context = null;
        }
        view.setBackground(context.getDrawable(R.drawable.subscreen_notification_reply_item_bg_selecter_b5));
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final void setSmartReplyResultValue(int i, String str, StringBuilder sb) {
        LinearLayout linearLayout = this.progressLayout;
        if (linearLayout == null || linearLayout.getVisibility() != 0) {
            return;
        }
        this.mSmartReplyResult = i;
        this.mSmartReplyResultCompleteMsg = sb;
        this.mSmartReplyResultFailureMsg = str;
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final void setStartedReplyActivity() {
        this.mIsStartedReplyActivity = false;
    }

    /* JADX WARN: Type inference failed for: r1v8, types: [T, java.lang.String] */
    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final void showAIReply() {
        SubscreenNotificationDetailAdapter.ItemViewHolder itemViewHolder;
        SubscreenNotificationInfo subscreenNotificationInfo;
        String obj;
        LinearLayout linearLayout;
        boolean z = true;
        if ((this.isPossibleAiReply && (itemViewHolder = this.detailViewHolder) != null && (subscreenNotificationInfo = itemViewHolder.mInfo) != null && subscreenNotificationInfo.mIsMessagingStyle && subscreenNotificationInfo.mRemoteinput) ? isReplyLayoutShowing() : false) {
            final SubscreenNotificationDetailAdapter.ItemViewHolder itemViewHolder2 = this.detailViewHolder;
            if (itemViewHolder2 != null) {
                ListPopupWindow$$ExternalSyntheticOutline0.m(this.smartReplyStatus, "callAIReply() - start smartReplyStatus : ", "S.S.N.");
                if (this.smartReplyStatus != 0) {
                    Log.d("S.S.N.", "callAIReply() return - CallAIReply is already running");
                } else {
                    this.smartReplyStatus = 1;
                    final String historyInfo = getHistoryInfo(itemViewHolder2);
                    r4 = null;
                    Float f = null;
                    if (historyInfo == null || (obj = StringsKt__StringsKt.trim(historyInfo).toString()) == null || obj.length() == 0) {
                        handleProgressLayout(false);
                        LinearLayout linearLayout2 = this.progressLayout;
                        if (linearLayout2 != null) {
                            linearLayout2.setVisibility(8);
                        }
                        String obj2 = historyInfo != null ? StringsKt__StringsKt.trim(historyInfo).toString() : null;
                        if (obj2 != null && obj2.length() != 0) {
                            z = false;
                        }
                        if (z && this.mSmartReplyClickedByUser) {
                            this.mSmartReplyClickedByUser = false;
                            enableSmartReplyTriggerBtn("", false);
                            updateVisibilityForSmartReplyLayout(8);
                            TextView textView = this.smartReplyErrorMessageView;
                            if (textView != null) {
                                textView.setText(R.string.subscreen_notification_smart_reply_error_other);
                            }
                            showErrorMessageWithAnim(this.smartReplyErrorMessageView);
                        }
                        this.smartReplyStatus = 0;
                        MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("callAIReply() return - history is null or empty", historyInfo, "S.S.N.");
                    } else {
                        SmartReplyData smartReplyData = (SmartReplyData) this.mSmartReplyHashMap.get(itemViewHolder2.mInfo.mKey);
                        if (smartReplyData != null) {
                            String str = smartReplyData.prevPrompt;
                            if (str != null ? str.equals(historyInfo) : false) {
                                if (this.mSmartReplyClickedByUser) {
                                    this.mSmartReplyClickedByUser = false;
                                    Log.d("S.S.N.", "callAIReply() continue - isAlreadyAiReply but click button by user");
                                } else {
                                    inflateSmartReplyAI(smartReplyData.replyText);
                                    Log.d("S.S.N.", "callAIReply() return - isAlreadyAiReply");
                                    this.smartReplyStatus = 0;
                                }
                            }
                        }
                        if (this.smartReplyStatus != 1) {
                            Log.d("S.S.N.", "callAIReply() return - it's already progressing... ");
                        } else {
                            this.smartReplyStatus = 2;
                            SubscreenNotificationDetailAdapter.ItemViewHolder itemViewHolder3 = this.detailViewHolder;
                            if (itemViewHolder3 != null && (linearLayout = itemViewHolder3.mReplylayout) != null) {
                                f = Float.valueOf(linearLayout.getY());
                            }
                            Intrinsics.checkNotNull(f);
                            this.mReplyLayoutCurrentPostionY = f.floatValue();
                            int size = itemViewHolder2.mInfo.mMessageingStyleInfoArray.size();
                            if (size > 0) {
                                final NeuralTranslator neuralTranslator = new NeuralTranslator(this.mContext);
                                final Ref$ObjectRef ref$ObjectRef = new Ref$ObjectRef();
                                ref$ObjectRef.element = ((SubscreenNotificationInfo.MessagingStyleInfo) itemViewHolder2.mInfo.mMessageingStyleInfoArray.get(size - 1)).mContentText;
                                TextView textView2 = this.smartReplyErrorMessageView;
                                if (textView2 != null) {
                                    textView2.setVisibility(8);
                                }
                                enableSmartReplyTriggerBtn("", false);
                                neuralTranslator.refresh().addOnCompleteListener(new OnCompleteListener() { // from class: com.android.systemui.statusbar.notification.SubscreenDeviceModelB5$callAIReply$1
                                    /* JADX WARN: Multi-variable type inference failed */
                                    @Override // com.samsung.android.sdk.scs.base.tasks.OnCompleteListener
                                    public final void onComplete(Task task) {
                                        TaskImpl identifyLanguage = NeuralTranslator.this.identifyLanguage((String) ref$ObjectRef.element);
                                        final String str2 = historyInfo;
                                        final SubscreenDeviceModelB5 subscreenDeviceModelB5 = this;
                                        final SubscreenNotificationDetailAdapter.ItemViewHolder itemViewHolder4 = itemViewHolder2;
                                        identifyLanguage.addOnCompleteListener(new OnCompleteListener() { // from class: com.android.systemui.statusbar.notification.SubscreenDeviceModelB5$callAIReply$1.1
                                            @Override // com.samsung.android.sdk.scs.base.tasks.OnCompleteListener
                                            public final void onComplete(final Task task2) {
                                                boolean isSuccessful = task2.isSuccessful();
                                                final SubscreenDeviceModelB5 subscreenDeviceModelB52 = SubscreenDeviceModelB5.this;
                                                if (!isSuccessful) {
                                                    subscreenDeviceModelB52.smartReplyStatus = 0;
                                                    return;
                                                }
                                                boolean access$isSupportableLanguage = SubscreenDeviceModelB5.access$isSupportableLanguage(subscreenDeviceModelB52, String.valueOf(task2.getResult()));
                                                boolean isSuccessful2 = task2.isSuccessful();
                                                boolean isComplete = task2.isComplete();
                                                Object result = task2.getResult();
                                                StringBuilder m = KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0.m("callAIReply() - successful : ", ", isComplete : ", ", result : ", isSuccessful2, isComplete);
                                                m.append(result);
                                                m.append(", isSupport : ");
                                                m.append(access$isSupportableLanguage);
                                                Log.d("S.S.N.", m.toString());
                                                if (access$isSupportableLanguage) {
                                                    subscreenDeviceModelB52.handleProgressLayout(true);
                                                    final String str3 = str2;
                                                    final SubscreenNotificationDetailAdapter.ItemViewHolder itemViewHolder5 = itemViewHolder4;
                                                    new Thread(new Runnable() { // from class: com.android.systemui.statusbar.notification.SubscreenDeviceModelB5.callAIReply.1.1.1
                                                        @Override // java.lang.Runnable
                                                        public final void run() {
                                                            SubscreenDeviceModelB5.this.mPromptSB.append(str3);
                                                            Log.d("S.S.N.", "call textPrompting");
                                                            if (SubscreenDeviceModelB5.this.mPromptSBForLog.length() > 0) {
                                                                SubscreenDeviceModelB5 subscreenDeviceModelB53 = SubscreenDeviceModelB5.this;
                                                                if (subscreenDeviceModelB53.isDebug) {
                                                                    Log.d("S.S.N.", "textPrompting trimmed :\n" + ((Object) subscreenDeviceModelB53.mPromptSBForLog));
                                                                }
                                                                SubscreenDeviceModelB5.this.mPromptSBForLog.setLength(0);
                                                            }
                                                            SubscreenDeviceModelB5.this.mSrPromptProcessor.setNotificationKey(itemViewHolder5.mInfo.mKey);
                                                            SubscreenDeviceModelB5 subscreenDeviceModelB54 = SubscreenDeviceModelB5.this;
                                                            subscreenDeviceModelB54.mSrPromptProcessor.textPrompting(subscreenDeviceModelB54.mPromptSB.toString(), String.valueOf(task2.getResult()), SubscreenDeviceModelB5.this.mSrResponseCallback);
                                                        }
                                                    }).start();
                                                    return;
                                                }
                                                Log.d("S.S.N.", "callAIReply - not Support");
                                                subscreenDeviceModelB52.handleProgressLayout(false);
                                                subscreenDeviceModelB52.smartReplyStatus = 0;
                                                subscreenDeviceModelB52.enableSmartReplyTriggerBtn("unsupportedLanguage", false);
                                                if (subscreenDeviceModelB52.mSmartReplyClickedByUser) {
                                                    subscreenDeviceModelB52.mSmartReplyClickedByUser = false;
                                                    subscreenDeviceModelB52.updateVisibilityForSmartReplyLayout(8);
                                                    subscreenDeviceModelB52.enableSmartReplyTriggerBtn("", false);
                                                    TextView textView3 = subscreenDeviceModelB52.smartReplyErrorMessageView;
                                                    if (textView3 != null) {
                                                        textView3.setText(R.string.subscreen_notification_smart_reply_error_unsupported_language);
                                                    }
                                                    SubscreenDeviceModelB5.showErrorMessageWithAnim(subscreenDeviceModelB52.smartReplyErrorMessageView);
                                                }
                                            }
                                        });
                                    }
                                });
                            } else {
                                this.smartReplyStatus = 0;
                            }
                            ListPopupWindow$$ExternalSyntheticOutline0.m(this.smartReplyStatus, "callAIReply() - end smartReplyStatus : ", "S.S.N.");
                        }
                    }
                }
            }
            this.isPossibleAiReply = false;
        }
    }

    public final void showBouncer(Context context, final NotificationEntry notificationEntry) {
        SubRoom.StateChangeListener stateChangeListener;
        boolean isKeyguardStats = isKeyguardStats();
        KeyguardStateController keyguardStateController = this.mKeyguardStateController;
        Log.d("S.S.N.", "showBouncer B5 -isMethodSecure : " + (keyguardStateController != null ? Boolean.valueOf(((KeyguardStateControllerImpl) keyguardStateController).mSecure) : null) + ", isUnlocked : " + isKeyguardStats);
        KeyguardStateController keyguardStateController2 = this.mKeyguardStateController;
        if (keyguardStateController2 == null || !((KeyguardStateControllerImpl) keyguardStateController2).mSecure) {
            ((KeyguardManager) context.getSystemService("keyguard")).semDismissKeyguard();
            return;
        }
        if (isKeyguardStats) {
            Intent intent = new Intent();
            intent.setAction("com.samsung.android.action.UNLOCK_NOTIFICATION_PENDING_INTENT");
            intent.putExtra("key", notificationEntry.mKey);
            PendingIntent broadcast = PendingIntent.getBroadcast(context, 0, intent, 335544320);
            Intent intent2 = new Intent();
            intent2.putExtra("runOnCover", true);
            intent2.putExtra("ignoreKeyguardState", true);
            intent2.putExtra("showCoverToast", true);
            SubscreenSubRoomNotification subscreenSubRoomNotification = this.mSubRoomNotification;
            if (subscreenSubRoomNotification != null && (stateChangeListener = subscreenSubRoomNotification.mStateChangeListener) != null) {
                stateChangeListener.requestCoverPopup(broadcast, intent2);
            }
            KeyguardActionInfo keyguardActionInfo = this.mKeyguardActionInfo;
            if (keyguardActionInfo != null) {
                keyguardActionInfo.isShowBouncer = true;
            }
            Handler handler = this.mHandler;
            Runnable runnable = new Runnable() { // from class: com.android.systemui.statusbar.notification.SubscreenDeviceModelB5$showBouncer$1
                @Override // java.lang.Runnable
                public final void run() {
                    SubscreenDeviceModelB5.this.dismissImmediately(notificationEntry);
                }
            };
            NotificationEntry notificationEntry2 = this.currentPresentationEntry;
            handler.postDelayed(runnable, notificationEntry.mKey.equals(notificationEntry2 != null ? notificationEntry2.mKey : null) ? 300L : 0L);
        }
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final PopupWindow showReplyButtonViewPopupWindow(final View view, View view2) {
        LinearLayout linearLayout;
        PopupWindow popupWindow = new PopupWindow(view, -2, -2);
        this.sendButtonPopupWindow = popupWindow;
        popupWindow.setOutsideTouchable(true);
        PopupWindow popupWindow2 = this.sendButtonPopupWindow;
        if (popupWindow2 != null) {
            popupWindow2.setOnDismissListener(new PopupWindow.OnDismissListener() { // from class: com.android.systemui.statusbar.notification.SubscreenDeviceModelB5$showReplyButtonViewPopupWindow$1
                @Override // android.widget.PopupWindow.OnDismissListener
                public final void onDismiss() {
                    SubscreenDeviceModelB5.this.sendButtonPopupWindow = null;
                }
            });
        }
        int height = view2.getHeight() + (getMainHeaderViewHeight() / 2);
        Context context = this.mDisplayContext;
        if (context == null) {
            context = null;
        }
        int m = StrongAuthPopup$$ExternalSyntheticOutline0.m(context, R.dimen.subscreen_noti_detail_reply_button_margin_b5, height);
        if (this.mIsFlexMode) {
            SubscreenSubRoomNotification subscreenSubRoomNotification = this.mSubRoomNotification;
            Integer valueOf = (subscreenSubRoomNotification == null || (linearLayout = subscreenSubRoomNotification.mSubscreenMainLayout) == null) ? null : Integer.valueOf(linearLayout.getHeight());
            Intrinsics.checkNotNull(valueOf);
            m += (720 - valueOf.intValue()) / 2;
        }
        final Ref$IntRef ref$IntRef = new Ref$IntRef();
        Context context2 = this.mDisplayContext;
        ref$IntRef.element = (context2 != null ? context2 : null).getResources().getDimensionPixelSize(R.dimen.subscreen_noti_detail_icon_button_bg_size_b5);
        PopupWindow popupWindow3 = this.sendButtonPopupWindow;
        if (popupWindow3 != null) {
            popupWindow3.setTouchInterceptor(new View.OnTouchListener() { // from class: com.android.systemui.statusbar.notification.SubscreenDeviceModelB5$showReplyButtonViewPopupWindow$2
                public Boolean downHit = Boolean.FALSE;

                @Override // android.view.View.OnTouchListener
                public final boolean onTouch(View view3, MotionEvent motionEvent) {
                    boolean z = false;
                    if (motionEvent == null) {
                        return false;
                    }
                    if (motionEvent.getAction() == 0) {
                        float x = motionEvent.getX();
                        float y = motionEvent.getY();
                        if (x > 0.0f) {
                            int i = Ref$IntRef.this.element;
                            if (x < i && y > 0.0f && y < i) {
                                z = true;
                            }
                        }
                        this.downHit = Boolean.valueOf(z);
                    } else if (motionEvent.getAction() == 1) {
                        Boolean bool = this.downHit;
                        Boolean bool2 = Boolean.FALSE;
                        if (Intrinsics.areEqual(bool, bool2)) {
                            PopupWindow popupWindow4 = this.sendButtonPopupWindow;
                            if (popupWindow4 != null) {
                                popupWindow4.dismiss();
                            }
                            return true;
                        }
                        float x2 = motionEvent.getX();
                        float y2 = motionEvent.getY();
                        if (x2 >= 0.0f) {
                            int i2 = Ref$IntRef.this.element;
                            if (x2 <= i2 && y2 >= 0.0f && y2 <= i2) {
                                if (Intrinsics.areEqual(this.downHit, Boolean.TRUE)) {
                                    ImageView imageView = (ImageView) view.findViewById(R.id.send);
                                    if (imageView != null) {
                                        imageView.performClick();
                                    }
                                    this.downHit = bool2;
                                }
                            }
                        }
                        PopupWindow popupWindow5 = this.sendButtonPopupWindow;
                        if (popupWindow5 != null) {
                            popupWindow5.dismiss();
                        }
                    }
                    return true;
                }
            });
        }
        PopupWindow popupWindow4 = this.sendButtonPopupWindow;
        if (popupWindow4 != null) {
            popupWindow4.showAtLocation(view, 1, 0, m);
        }
        this.mSmartReplyClickedByUser = false;
        return this.sendButtonPopupWindow;
    }

    public final void showSmartReplyResultComplete(StringBuilder sb) {
        Object failure;
        SubscreenNotificationInfo subscreenNotificationInfo;
        StringBuilder sb2 = this.mPromptSB;
        this.isPossibleAiReply = false;
        try {
            int i = Result.$r8$clinit;
            String sb3 = sb.toString();
            if (sb3.length() > 0) {
                SubscreenNotificationDetailAdapter.ItemViewHolder itemViewHolder = this.detailViewHolder;
                String str = (itemViewHolder == null || (subscreenNotificationInfo = itemViewHolder.mInfo) == null) ? null : subscreenNotificationInfo.mKey;
                SmartReplyData smartReplyData = new SmartReplyData();
                smartReplyData.prevPrompt = sb2.toString();
                smartReplyData.replyText = sb3;
                if (str != null) {
                    this.mSmartReplyHashMap.put(str, smartReplyData);
                }
                inflateSmartReplyAI(sb3);
                sb2.setLength(0);
            }
            failure = Unit.INSTANCE;
        } catch (Throwable th) {
            int i2 = Result.$r8$clinit;
            failure = new Result.Failure(th);
        }
        Result.m2527exceptionOrNullimpl(failure);
    }

    public final void showSmartReplyResultFailure(String str) {
        enableSmartReplyTriggerBtn("", false);
        resetProgressScaleAnimation();
        LinearLayout linearLayout = this.progressLayout;
        if (linearLayout != null) {
            linearLayout.setVisibility(8);
        }
        LottieAnimationView lottieAnimationView = this.progressingVi;
        if (lottieAnimationView != null) {
            lottieAnimationView.cancelAnimation();
        }
        this.isPossibleAiReply = false;
        this.mPromptSB.setLength(0);
        if (NotiRune.NOTI_SUBSCREEN_SUPPORT_SMART_REPLY_AI_FOR_CHINA) {
            if (str == null || str.length() == 0) {
                return;
            }
            TextView textView = this.smartReplyErrorMessageView;
            if (textView != null) {
                textView.setText(str);
            }
            showErrorMessageWithAnim(this.smartReplyErrorMessageView);
            return;
        }
        String obj = str != null ? StringsKt__StringsKt.trim(str).toString() : null;
        String string = Intrinsics.areEqual(obj, "Blocked by input safety filter") ? this.mContext.getString(R.string.subscreen_notification_smart_reply_error_safety_filter) : Intrinsics.areEqual(obj, "Input is too long") ? this.mContext.getString(R.string.subscreen_notification_smart_reply_error_input_is_too_long) : this.mContext.getString(R.string.subscreen_notification_smart_reply_error_other);
        Intrinsics.checkNotNull(string);
        TextView textView2 = this.smartReplyErrorMessageView;
        if (textView2 != null) {
            textView2.setText(string);
        }
        showErrorMessageWithAnim(this.smartReplyErrorMessageView);
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelCommon
    public final void showUnlockIconAnim() {
        SubScreenManager subScreenManager;
        SubHomeActivity subHomeActivity;
        ImageView imageView;
        SubscreenSubRoomNotification subscreenSubRoomNotification = this.mSubRoomNotification;
        boolean z = subscreenSubRoomNotification != null ? subscreenSubRoomNotification.mIsInNotiRoom : false;
        KeyguardActionInfo keyguardActionInfo = this.mKeyguardActionInfo;
        boolean z2 = keyguardActionInfo != null ? keyguardActionInfo.isShowBouncer : false;
        if (!z) {
            Log.d("S.S.N.", "showUnlockIconAnim() return - not in notiRoom");
            return;
        }
        if (z2) {
            Log.d("S.S.N.", "showUnlockIconAnim() return - show bouncer");
            return;
        }
        Lazy lazy = this.mSubScreenManagerLazy;
        if (lazy == null || (subScreenManager = (SubScreenManager) lazy.get()) == null || (subHomeActivity = subScreenManager.mActivity) == null) {
            Log.e("S.S.N.", "can't inflate unlock icon");
            return;
        }
        View inflate = LayoutInflater.from(subHomeActivity).inflate(R.layout.subscreen_notification_unlock_icon_view_b5, (ViewGroup) null);
        AnimationDrawable animationDrawable = (AnimationDrawable) ((inflate == null || (imageView = (ImageView) inflate.findViewById(R.id.unlock_icon_view)) == null) ? null : imageView.getDrawable());
        final PopupWindow popupWindow = new PopupWindow(inflate, -2, -2);
        Context context = this.mDisplayContext;
        popupWindow.showAtLocation(inflate, 49, 0, (context != null ? context : null).getResources().getDimensionPixelSize(R.dimen.subscreen_noti_unlock_icon_view_top_margin_b5));
        animationDrawable.start();
        inflate.animate().alpha(0.0f).setStartDelay(500L).setDuration(500L).withEndAction(new Runnable() { // from class: com.android.systemui.statusbar.notification.SubscreenDeviceModelB5$showUnlockIconAnim$1$1
            @Override // java.lang.Runnable
            public final void run() {
                popupWindow.dismiss();
            }
        });
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final int smallIconPadding(boolean z, boolean z2, boolean z3) {
        Context context = this.mDisplayContext;
        if (context == null) {
            context = null;
        }
        return context.getResources().getDimensionPixelSize(z ? R.dimen.subscreen_noti_header_icon_circle_padding_b5 : z2 ? R.dimen.subscreen_noti_full_popup_icon_circle_padding_b5 : z3 ? R.dimen.subscreen_noti_sub_icon_circle_padding_b5 : R.dimen.subscreen_noti_icon_circle_padding_b5);
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final int squircleRadius(boolean z, boolean z2) {
        Context context = this.mDisplayContext;
        if (context == null) {
            context = null;
        }
        return context.getResources().getDimensionPixelSize(z ? R.dimen.subscreen_noti_header_small_icon_bg_radius_b5 : z2 ? R.dimen.subscreen_noti_popup_small_icon_bg_radius_b5 : R.dimen.subscreen_noti_list_small_icon_bg_radius_b5);
    }

    public final int startNotificationIntent(PendingIntent pendingIntent) {
        Display display = this.mSubDisplay;
        if (display == null) {
            return -1;
        }
        Bundle activityOptions = CentralSurfaces.getActivityOptions(display.getDisplayId(), null);
        Context context = this.mDisplayContext;
        return pendingIntent.sendAndReturnResult(context != null ? context : null, 0, null, null, null, null, activityOptions);
    }

    public final void startProgressSpringAnimation(View view, final boolean z, final Runnable runnable) {
        float f;
        float f2;
        float f3 = 1.0f;
        float f4 = 0.0f;
        if (!z) {
            f = 0.0f;
            f2 = 0.0f;
            f4 = 1.0f;
        } else {
            if (view.getVisibility() == 0) {
                return;
            }
            runnable.run();
            SubscreenNotificationDetailAdapter.ItemViewHolder itemViewHolder = this.detailViewHolder;
            LinearLayout linearLayout = itemViewHolder != null ? itemViewHolder.mReplylayout : null;
            if (linearLayout != null) {
                linearLayout.setTranslationY(this.mReplyLayoutCurrentPostionY - 304);
            }
            SubscreenNotificationDetailAdapter.ItemViewHolder itemViewHolder2 = this.detailViewHolder;
            ObjectAnimator ofFloat = ObjectAnimator.ofFloat(itemViewHolder2 != null ? itemViewHolder2.mReplylayout : null, (Property<LinearLayout, Float>) View.TRANSLATION_Y, 0.0f);
            ofFloat.setInterpolator(Interpolators.FAST_OUT_LINEAR_IN);
            ofFloat.setDuration(200L);
            ofFloat.start();
            f2 = 1.0f;
            f3 = 0.85f;
            f = 1.0f;
        }
        view.setScaleX(f3);
        view.setScaleY(f3);
        view.setAlpha(f4);
        resetProgressScaleAnimation();
        view.animate().alpha(f).setDuration(200L).setListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.statusbar.notification.SubscreenDeviceModelB5$startProgressSpringAnimation$1
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationCancel(Animator animator) {
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationStart(Animator animator) {
            }
        });
        SpringForce springForce = new SpringForce(f2);
        springForce.setStiffness(200.0f);
        springForce.setDampingRatio(0.75f);
        SpringAnimation springAnimation = new SpringAnimation(view, DynamicAnimation.SCALE_X);
        this.mProgressScaleAnimationX = springAnimation;
        springAnimation.mSpring = springForce;
        springAnimation.start();
        SpringAnimation springAnimation2 = new SpringAnimation(view, DynamicAnimation.SCALE_Y);
        this.mProgressScaleAnimationY = springAnimation2;
        springAnimation2.addEndListener(new DynamicAnimation.OnAnimationEndListener() { // from class: com.android.systemui.statusbar.notification.SubscreenDeviceModelB5$startProgressSpringAnimation$2
            @Override // androidx.dynamicanimation.animation.DynamicAnimation.OnAnimationEndListener
            public final void onAnimationEnd(DynamicAnimation dynamicAnimation, boolean z2, float f5, float f6) {
                Runnable runnable2;
                if (!z && (runnable2 = runnable) != null) {
                    runnable2.run();
                }
                SubscreenDeviceModelB5 subscreenDeviceModelB5 = this;
                subscreenDeviceModelB5.mProgressScaleAnimationX = null;
                subscreenDeviceModelB5.mProgressScaleAnimationY = null;
            }
        });
        SpringAnimation springAnimation3 = this.mProgressScaleAnimationY;
        if (springAnimation3 != null) {
            springAnimation3.mSpring = springForce;
            springAnimation3.start();
        }
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final void unregisterAODTspReceiver() {
        try {
            this.mContext.unregisterReceiver(this.aodTspUpdateReceiver);
        } catch (IllegalArgumentException e) {
            Log.e("S.S.N.", " unregisterAODTspReceiver IllegalArgumentException: " + e.getMessage());
        }
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final void updateContentScroll() {
        this.mIsContentScroll = true;
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final void updateIconContainer(View view, boolean z) {
        FrameLayout frameLayout = (FrameLayout) view.findViewById(R.id.icon_container);
        View findViewById = view.findViewById(R.id.spacer);
        if (frameLayout != null) {
            frameLayout.setVisibility(z ? 0 : 8);
        }
        if (findViewById == null) {
            return;
        }
        findViewById.setVisibility(z ? 8 : 0);
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final void updateImportBadgeIconRing(View view, boolean z) {
        ImageView imageView;
        if (view == null || (imageView = (ImageView) view.findViewById(R.id.subscreen_notification_important_badge_ring)) == null) {
            return;
        }
        if (!z) {
            imageView.setVisibility(8);
            return;
        }
        Context context = this.mDisplayContext;
        if (context == null) {
            context = null;
        }
        int color = context.getColor(android.R.color.dim_foreground_inverse_holo_dark);
        if (isShowNotificationAppIcon()) {
            Context context2 = this.mDisplayContext;
            imageView.setImageDrawable((VectorDrawable) (context2 != null ? context2 : null).getDrawable(R.drawable.squircle_tray_stroke_small));
            imageView.setColorFilter(color);
        } else {
            imageView.setColorFilter((ColorFilter) null);
            imageView.setLayerType(1, null);
            Context context3 = this.mDisplayContext;
            imageView.setImageDrawable((context3 != null ? context3 : null).getDrawable(R.drawable.subscreen_notification_conversation_badge_ring_b5));
        }
        imageView.setVisibility(0);
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final void updateMainHeaderView(LinearLayout linearLayout) {
        this.mHeaderViewLayout = linearLayout.findViewById(R.id.header_layout);
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final void updateMainHeaderViewVisibility(int i) {
        View view = this.mHeaderViewLayout;
        if (view != null) {
            view.setVisibility(i);
        }
        View view2 = this.mHeaderViewLayout;
        if (view2 == null) {
            return;
        }
        view2.setAlpha(1.0f);
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final void updateSamsungAccount() {
        Account[] accountArr;
        Account account = null;
        try {
            accountArr = AccountManager.get(this.mContext).getAccountsByTypeAsUser("com.osp.app.signin", UserHandle.of(this.currentUserId));
        } catch (SecurityException e) {
            String message = e.getMessage();
            if (message != null) {
                Log.e("S.S.N.", message);
            }
            accountArr = null;
        }
        if (accountArr != null && accountArr.length > 0) {
            account = accountArr[0];
        }
        Account account2 = this.currentAccount;
        if (!((account2 == null || account == null) ? (account2 == null && account == null) ? false : true : !account2.name.equals(account.name))) {
            Log.d("S.S.N.", "updateSamsungAccount() : No Change");
            return;
        }
        this.currentAccount = account;
        this.isSALoggedIn = account != null;
        ThreadUtils.postOnBackgroundThread(new Runnable() { // from class: com.android.systemui.statusbar.notification.SubscreenDeviceModelB5$getChildAccount$1
            /* JADX WARN: Removed duplicated region for block: B:14:0x0069  */
            @Override // java.lang.Runnable
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public final void run() {
                /*
                    r12 = this;
                    java.lang.String r0 = ""
                    java.lang.String r1 = "result_message"
                    java.lang.String r2 = "result_code"
                    java.lang.String r3 = "i5to7wq0er"
                    java.lang.String r4 = "content://com.samsung.android.samsungaccount.accountmanagerprovider"
                    java.lang.String r5 = "S.S.N."
                    com.android.systemui.statusbar.notification.SubscreenDeviceModelB5 r6 = com.android.systemui.statusbar.notification.SubscreenDeviceModelB5.this
                    int r7 = com.android.systemui.statusbar.notification.SubscreenDeviceModelB5.$r8$clinit
                    android.content.Context r7 = r6.mContext
                    android.content.ContentResolver r7 = r7.getContentResolver()
                    r8 = 0
                    r9 = 1
                    android.net.Uri r10 = android.net.Uri.parse(r4)     // Catch: java.lang.Exception -> L56
                    java.lang.String r11 = "isChildAccount"
                    android.os.Bundle r7 = r7.call(r10, r11, r3, r8)     // Catch: java.lang.Exception -> L56
                    if (r7 == 0) goto L50
                    int r10 = r7.getInt(r2, r9)
                    java.lang.String r7 = r7.getString(r1, r0)
                    if (r10 != 0) goto L4a
                    java.lang.String r10 = "true"
                    boolean r7 = r10.equals(r7)
                    if (r7 == 0) goto L44
                    java.lang.String r7 = "This account is a child account."
                    android.util.Log.d(r5, r7)
                    boolean r7 = com.android.systemui.edgelighting.effect.utils.SalesCode.isKor
                    if (r7 != 0) goto L60
                    r7 = r9
                    goto L61
                L44:
                    java.lang.String r7 = "This account is not a child account."
                    android.util.Log.d(r5, r7)
                    goto L60
                L4a:
                    java.lang.String r10 = "isChildAccount Fail : resultMessage = "
                    android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(r10, r7, r5)
                    goto L60
                L50:
                    java.lang.String r7 = "Result bundle is null"
                    android.util.Log.d(r5, r7)
                    goto L60
                L56:
                    r7 = move-exception
                    java.lang.String r7 = r7.getMessage()
                    java.lang.String r10 = "Exception Error isChildAccount : "
                    android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(r10, r7, r5)
                L60:
                    r7 = 0
                L61:
                    r6.isChildAccount = r7
                    com.android.systemui.statusbar.notification.SubscreenDeviceModelB5 r6 = com.android.systemui.statusbar.notification.SubscreenDeviceModelB5.this
                    boolean r7 = r6.isChildAccount
                    if (r7 == 0) goto La8
                    android.content.Context r7 = r6.mContext
                    android.content.ContentResolver r7 = r7.getContentResolver()
                    r10 = -1
                    android.net.Uri r4 = android.net.Uri.parse(r4)     // Catch: java.lang.Exception -> L9c
                    java.lang.String r11 = "getFamilyServiceInfo"
                    android.os.Bundle r3 = r7.call(r4, r11, r3, r8)     // Catch: java.lang.Exception -> L9c
                    if (r3 == 0) goto La6
                    int r2 = r3.getInt(r2, r9)
                    if (r2 != 0) goto L92
                    java.lang.String r0 = "result_bundle"
                    android.os.Bundle r0 = r3.getBundle(r0)
                    if (r0 == 0) goto La6
                    java.lang.String r1 = "childGraduateAge"
                    int r10 = r0.getInt(r1)
                    goto La6
                L92:
                    java.lang.String r0 = r3.getString(r1, r0)
                    java.lang.String r1 = "getChildGraduateAge() Fail : resultMessage = "
                    android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(r1, r0, r5)
                    goto La6
                L9c:
                    r0 = move-exception
                    java.lang.String r0 = r0.getMessage()
                    java.lang.String r1 = "Exception Error getFamilyServiceInfo : "
                    android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(r1, r0, r5)
                La6:
                    r6.childGraduateAge = r10
                La8:
                    com.android.systemui.statusbar.notification.SubscreenDeviceModelB5 r12 = com.android.systemui.statusbar.notification.SubscreenDeviceModelB5.this
                    boolean r12 = r12.isChildAccount
                    java.lang.String r0 = "getChildAccount() : isChildAccount "
                    com.android.keyguard.KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m(r0, r5, r12)
                    return
                */
                throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.SubscreenDeviceModelB5$getChildAccount$1.run():void");
            }
        });
        KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m("updateSamsungAccount() : isSALoggedIn ", "S.S.N.", this.isSALoggedIn);
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final void updateShadowIconColor(View view, NotificationEntry notificationEntry) {
        Drawable drawable;
        Drawable.ConstantState constantState;
        ExpandableNotificationRow expandableNotificationRow;
        if (view == null) {
            return;
        }
        int appPrimaryColor = (notificationEntry == null || (expandableNotificationRow = notificationEntry.row) == null) ? 0 : ((NotificationColorPicker) Dependency.sDependency.getDependencyInner(NotificationColorPicker.class)).getAppPrimaryColor(expandableNotificationRow);
        boolean isGrayScaleIcon = isGrayScaleIcon(notificationEntry);
        ImageView imageView = (ImageView) view.findViewById(R.id.group_icon_shadow);
        if (imageView != null) {
            imageView.setVisibility(0);
            ImageView imageView2 = (ImageView) view.findViewById(R.id.app_icon);
            if (isShowNotificationAppIcon() && imageView2 != null && imageView2.getVisibility() == 0) {
                imageView.clearColorFilter();
                Drawable drawable2 = imageView2.getDrawable();
                if (drawable2 == null || (constantState = drawable2.getConstantState()) == null || (drawable = constantState.newDrawable()) == null) {
                    drawable = null;
                } else {
                    drawable.mutate().setAlpha(76);
                }
                imageView.setImageDrawable(drawable);
                return;
            }
            if (isGrayScaleIcon) {
                imageView.setColorFilter(Color.argb((Color.alpha(appPrimaryColor) * 3) / 10, Color.red(appPrimaryColor), Color.green(appPrimaryColor), Color.blue(appPrimaryColor)), PorterDuff.Mode.SRC_IN);
                return;
            }
            int color = this.mContext.getColor(R.color.notification_non_grayscale_border_color);
            int color2 = this.mContext.getColor(R.color.notification_non_grayscale_fill_color);
            GradientDrawable gradientDrawable = (GradientDrawable) imageView.getDrawable().mutate();
            gradientDrawable.setColor(Color.argb((Color.alpha(color2) * 3) / 10, Color.red(color2), Color.green(color2), Color.blue(color2)));
            gradientDrawable.setStroke(this.mContext.getResources().getDimensionPixelSize(R.dimen.notification_icon_border_width), Color.argb((Color.alpha(color) * 3) / 10, Color.red(color), Color.green(color), Color.blue(color)));
        }
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final void updateSmallIconBg(ImageView imageView, boolean z, boolean z2, boolean z3) {
        int smallIconPadding = smallIconPadding(z, z2, z3);
        if (imageView != null) {
            Context context = this.mDisplayContext;
            if (context == null) {
                context = null;
            }
            imageView.setBackground(context.getResources().getDrawable(R.drawable.notification_icon_circle, null));
            imageView.setPadding(smallIconPadding, smallIconPadding, smallIconPadding, smallIconPadding);
        }
    }

    public final void updateSmartReplyVariables() {
        this.isAiInfoConfirmed = getMSettingsHelper().isAiInfoConfirmed();
        this.isSuggestResponsesEnabled = getMSettingsHelper().isSuggestResponsesEnabled();
        boolean z = false;
        this.isRDUMode = Settings.Secure.getInt(this.mContext.getContentResolver(), "shopdemo", 0) == 1;
        updateSamsungAccount();
        boolean z2 = this.isRDUMode;
        boolean z3 = this.isSALoggedIn;
        boolean z4 = this.isChildAccount;
        boolean z5 = this.isAiInfoConfirmed;
        boolean z6 = this.isSuggestResponsesEnabled;
        if (NotiRune.NOTI_SUBSCREEN_SUPPORT_SMART_REPLY_AI_FOR_CHINA && isPreventOnlineProcessing()) {
            z = true;
        }
        StringBuilder m = KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0.m("isRDUMode: ", " isSALoggedIn: ", " isChildAccount: ", z2, z3);
        KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(m, z4, " isAiInfoConfirmed: ", z5, " isSuggestionResponsesEnabled: ");
        KeyguardCarrierViewController$$ExternalSyntheticOutline0.m(m, z6, " isPreventOnlineProcessing: ", z, "S.S.N.");
    }

    public final void updateVisibilityForSmartReplyLayout(int i) {
        TextView textView;
        SubscreenNotificationDetailAdapter.ItemViewHolder itemViewHolder = this.detailViewHolder;
        LinearLayout linearLayout = itemViewHolder != null ? itemViewHolder.mSmartReplyLayout : null;
        if (linearLayout != null) {
            linearLayout.setVisibility(i);
        }
        ImageView imageView = this.aiDisclaimerBtn;
        if (imageView != null) {
            imageView.setVisibility(i);
        }
        if (!NotiRune.NOTI_SUBSCREEN_SUPPORT_SMART_REPLY_AI_FOR_CHINA || (textView = this.smartReplyAiLogoText) == null) {
            return;
        }
        textView.setVisibility(i);
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final boolean useTopPresentation() {
        StatusBarNotification statusBarNotification;
        Notification notification2;
        NotificationEntry notificationEntry = this.currentPresentationEntry;
        boolean z = false;
        if (notificationEntry != null && (statusBarNotification = notificationEntry.mSbn) != null && (notification2 = statusBarNotification.getNotification()) != null && "call".equals(notification2.category) && notification2.isStyle(Notification.CallStyle.class)) {
            z = true;
        }
        return !z;
    }
}
