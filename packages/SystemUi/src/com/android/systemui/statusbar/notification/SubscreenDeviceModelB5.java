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
import android.graphics.Bitmap;
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
import android.support.v4.media.AbstractC0000x2c234b15;
import android.telephony.TelephonyManager;
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
import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.constraintlayout.motion.widget.KeyAttributes$$ExternalSyntheticOutline0;
import androidx.core.view.ViewGroupKt;
import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.dynamicanimation.animation.SpringAnimation;
import androidx.dynamicanimation.animation.SpringForce;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import androidx.picker.adapter.AbsAdapter$1$$ExternalSyntheticOutline0;
import androidx.picker.model.AppInfo$$ExternalSyntheticOutline0;
import androidx.picker3.widget.SeslColorSpectrumView$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.GridLayoutManager$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView;
import com.airbnb.lottie.LottieAnimationView;
import com.android.app.animation.Interpolators;
import com.android.keyguard.AbstractC0662xaf167275;
import com.android.keyguard.EmergencyButton$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardCarrierViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardFaceListenModel$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.punchhole.VIDirector$$ExternalSyntheticOutline0;
import com.android.settingslib.utils.ThreadUtils;
import com.android.systemui.Dependency;
import com.android.systemui.NotiRune;
import com.android.systemui.R;
import com.android.systemui.bixby2.controller.NotificationController;
import com.android.systemui.edgelighting.effect.utils.SalesCode;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.p014qp.util.SubscreenUtil;
import com.android.systemui.plugins.subscreen.SubRoom;
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
import com.android.systemui.statusbar.phone.StatusBarNotificationActivityStarter;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.subscreen.SubHomeActivity;
import com.android.systemui.subscreen.SubScreenManager;
import com.android.systemui.util.DeviceType;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.SystemUIAnalytics;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.samsung.android.desktopsystemui.sharedlib.system.ActivityManagerWrapper;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;
import com.samsung.android.sdk.scs.base.tasks.OnCompleteListener;
import com.samsung.android.sdk.scs.base.tasks.Task;
import com.samsung.android.sdk.scs.base.tasks.TaskImpl;
import com.samsung.android.sdk.scs.p048ai.translation.NeuralTranslator;
import com.sec.ims.gls.GlsIntent;
import com.sec.ims.volte2.data.VolteConstants;
import dagger.Lazy;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import kotlin.Result;
import kotlin.Unit;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.EmptyList;
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
import notification.src.com.android.systemui.SrPromptProcessor;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class KeyguardActionInfo {
        public boolean isShowBouncer;
        public int mAction;
        public Context mContext;
        public SubscreenNotificationDetailAdapter.ItemViewHolder mDetailAdapterItemViewHolder;
        public NotificationEntry mEntry;
        public SubscreenParentItemViewHolder mSubscreenParentItemViewHolder;

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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

        /* JADX WARN: Multi-variable type inference failed */
        public final int hashCode() {
            int m41m = AppInfo$$ExternalSyntheticOutline0.m41m(this.languageDisplayName, AppInfo$$ExternalSyntheticOutline0.m41m(this.language, Integer.hashCode(this.order) * 31, 31), 31);
            boolean z = this.supportToneConversion;
            int i = z;
            if (z != 0) {
                i = 1;
            }
            int i2 = (m41m + i) * 31;
            boolean z2 = this.supportCorrection;
            int i3 = z2;
            if (z2 != 0) {
                i3 = 1;
            }
            int i4 = (i2 + i3) * 31;
            boolean z3 = this.supportReply;
            return i4 + (z3 ? 1 : z3 ? 1 : 0);
        }

        public final String toString() {
            int i = this.order;
            String str = this.language;
            String str2 = this.languageDisplayName;
            boolean z = this.supportToneConversion;
            boolean z2 = this.supportCorrection;
            boolean z3 = this.supportReply;
            StringBuilder m61m = AbstractC0662xaf167275.m61m("LlmLanguage(order=", i, ", language=", str, ", languageDisplayName=");
            m61m.append(str2);
            m61m.append(", supportToneConversion=");
            m61m.append(z);
            m61m.append(", supportCorrection=");
            m61m.append(z2);
            m61m.append(", supportReply=");
            m61m.append(z3);
            m61m.append(")");
            return m61m.toString();
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class SmartReplyData {
        public String prevPrompt;
        public String replyText;
    }

    static {
        new Companion(null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v7, types: [android.content.BroadcastReceiver, com.android.systemui.statusbar.notification.SubscreenDeviceModelB5$broadcastReceiver$1] */
    /* JADX WARN: Type inference failed for: r3v0, types: [android.content.BroadcastReceiver, com.android.systemui.statusbar.notification.SubscreenDeviceModelB5$pkgBroadcastReceiver$1] */
    /* JADX WARN: Type inference failed for: r4v0, types: [com.android.systemui.statusbar.notification.SubscreenDeviceModelB5$aodTspUpdateReceiver$1] */
    public SubscreenDeviceModelB5(Context context, KeyguardUpdateMonitor keyguardUpdateMonitor, SettingsHelper settingsHelper, UserContextProvider userContextProvider, SubscreenNotificationController subscreenNotificationController, Lazy lazy, CommonNotifCollection commonNotifCollection, LogBuffer logBuffer, NotificationInterruptStateProvider notificationInterruptStateProvider, Lazy lazy2, Lazy lazy3, NotificationVisibilityProvider notificationVisibilityProvider, BindEventManager bindEventManager, NotificationController notificationController, UserManager userManager, ConversationNotificationManager conversationNotificationManager) {
        super(context, keyguardUpdateMonitor, settingsHelper, userContextProvider, subscreenNotificationController, lazy, commonNotifCollection, logBuffer, notificationInterruptStateProvider, lazy2, lazy3, notificationVisibilityProvider, bindEventManager, notificationController, userManager, conversationNotificationManager);
        this.isDebug = DeviceType.getDebugLevel() == 1 || DeviceType.getDebugLevel() == 2;
        this.mIsContentScroll = true;
        this.titleText = "";
        this.mPromptSB = new StringBuilder();
        this.mPromptSBForLog = new StringBuilder();
        this.mSmartReplyHashMap = new LinkedHashMap();
        ?? r1 = new BroadcastReceiver() { // from class: com.android.systemui.statusbar.notification.SubscreenDeviceModelB5$broadcastReceiver$1
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                SubscreenNotificationDetailAdapter subscreenNotificationDetailAdapter;
                Log.d("S.S.N.", "receive " + intent.getAction());
                if (Intrinsics.areEqual(intent.getAction(), "android.intent.action.CLOSE_SYSTEM_DIALOGS")) {
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
        this.broadcastReceiver = r1;
        ?? r3 = new BroadcastReceiver() { // from class: com.android.systemui.statusbar.notification.SubscreenDeviceModelB5$pkgBroadcastReceiver$1
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                Log.d("S.S.N.", "receive " + intent.getAction());
                if (Intrinsics.areEqual(intent.getAction(), "android.intent.action.PACKAGE_ADDED") || Intrinsics.areEqual(intent.getAction(), "android.intent.action.PACKAGE_REPLACED") || Intrinsics.areEqual(intent.getAction(), "android.intent.action.PACKAGE_REMOVED")) {
                    String dataString = intent.getDataString();
                    boolean z = false;
                    if (dataString != null && StringsKt__StringsKt.contains(dataString, SubscreenDeviceModelB5.this.SR_LLM_PACKAGE_NAME, false)) {
                        z = true;
                    }
                    if (z) {
                        Log.d("S.S.N.", "package intent received - loadOnDeviceMetaData again");
                        SubscreenDeviceModelB5 subscreenDeviceModelB5 = SubscreenDeviceModelB5.this;
                        subscreenDeviceModelB5.metaData = null;
                        subscreenDeviceModelB5.loadOnDeviceMetaData();
                    }
                }
            }
        };
        this.pkgBroadcastReceiver = r3;
        this.aodTspUpdateReceiver = new BroadcastReceiver() { // from class: com.android.systemui.statusbar.notification.SubscreenDeviceModelB5$aodTspUpdateReceiver$1
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                Integer valueOf = intent != null ? Integer.valueOf(intent.getIntExtra("info", -1)) : null;
                float[] floatArrayExtra = intent != null ? intent.getFloatArrayExtra(GlsIntent.Extras.EXTRA_LOCATION) : null;
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
                    SeslColorSpectrumView$$ExternalSyntheticOutline0.m44m(sb, f2, "S.S.N.");
                    subscreenDeviceModelB5.mIsContentScroll = true;
                    subscreenDeviceModelB5.detailClicked(subscreenDeviceModelB5.currentPresentationEntry);
                }
            }
        };
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.CLOSE_SYSTEM_DIALOGS");
        intentFilter.addAction("com.samsung.android.action.UNLOCK_NOTIFICATION_PENDING_INTENT");
        intentFilter.addAction("com.samsung.android.action.INTELLIGENCE_SERVICE_SETTINGS_START_INTENT");
        boolean z = NotiRune.NOTI_SUBSCREEN_SUPPORT_SMART_REPLY_AI_FOR_CHINA;
        if (z) {
            intentFilter.addAction("com.samsung.android.action.INTELLIGENCE_SERVICE_PROCESSING_ONLINE_INTENT");
        }
        context.registerReceiverAsUser(r1, UserHandle.ALL, intentFilter, null, null, 2);
        IntentFilter intentFilter2 = new IntentFilter();
        intentFilter2.addAction("android.intent.action.PACKAGE_ADDED");
        intentFilter2.addAction("android.intent.action.PACKAGE_REPLACED");
        intentFilter2.addAction("android.intent.action.PACKAGE_REMOVED");
        intentFilter2.addDataScheme("package");
        context.registerReceiverAsUser(r3, UserHandle.ALL, intentFilter2, null, null, 2);
        KeyguardStateController keyguardStateController = (KeyguardStateController) Dependency.get(KeyguardStateController.class);
        this.mKeyguardStateController = keyguardStateController;
        if (keyguardStateController != null) {
            ((KeyguardStateControllerImpl) keyguardStateController).addCallback(new KeyguardStateController.Callback() { // from class: com.android.systemui.statusbar.notification.SubscreenDeviceModelB5$initKeyguardStateConroller$1
                @Override // com.android.systemui.statusbar.policy.KeyguardStateController.Callback
                public final void onKeyguardShowingChanged() {
                    NotificationEntry entry;
                    SubscreenParentItemViewHolder subscreenParentItemViewHolder;
                    SubscreenParentItemViewHolder subscreenParentItemViewHolder2;
                    SubscreenNotificationInfo subscreenNotificationInfo;
                    ExpandableNotificationRow expandableNotificationRow;
                    SubscreenNotificationDetailAdapter subscreenNotificationDetailAdapter;
                    SubscreenNotificationDetailAdapter subscreenNotificationDetailAdapter2;
                    SubscreenDeviceModelB5 subscreenDeviceModelB5 = SubscreenDeviceModelB5.this;
                    SubscreenNotificationDialog subscreenNotificationDialog = subscreenDeviceModelB5.mDialog;
                    if (subscreenNotificationDialog != null) {
                        subscreenNotificationDialog.dismiss();
                    }
                    boolean z2 = subscreenDeviceModelB5.mIsFolded;
                    SubscreenDeviceModelB5.KeyguardActionInfo keyguardActionInfo = subscreenDeviceModelB5.mKeyguardActionInfo;
                    if (z2) {
                        KeyguardStateController keyguardStateController2 = subscreenDeviceModelB5.mKeyguardStateController;
                        Boolean valueOf = keyguardStateController2 != null ? Boolean.valueOf(((KeyguardStateControllerImpl) keyguardStateController2).mSecure) : null;
                        KeyguardStateController keyguardStateController3 = subscreenDeviceModelB5.mKeyguardStateController;
                        Boolean valueOf2 = keyguardStateController3 != null ? Boolean.valueOf(((KeyguardStateControllerImpl) keyguardStateController3).mShowing) : null;
                        KeyguardStateController keyguardStateController4 = subscreenDeviceModelB5.mKeyguardStateController;
                        Log.d("S.S.N.", " onKeyguardShowingChanged() isMethodSecure : " + valueOf + ", isShowing: " + valueOf2 + ", isUnlocked : " + (keyguardStateController4 != null ? Boolean.valueOf(keyguardStateController4.isUnlocked()) : null) + ", getAction() : " + (keyguardActionInfo != null ? Integer.valueOf(keyguardActionInfo.mAction) : null));
                        if (subscreenDeviceModelB5.isKeyguardStats()) {
                            subscreenDeviceModelB5.clearMainList();
                        } else {
                            if (keyguardActionInfo != null && keyguardActionInfo.isShowBouncer) {
                                KeyguardStateController keyguardStateController5 = subscreenDeviceModelB5.mKeyguardStateController;
                                if (((keyguardStateController5 == null || ((KeyguardStateControllerImpl) keyguardStateController5).mShowing) ? false : true) && keyguardActionInfo != null) {
                                    keyguardActionInfo.isShowBouncer = false;
                                }
                            }
                            if (subscreenDeviceModelB5.isShownDetail()) {
                                SubscreenSubRoomNotification subscreenSubRoomNotification = subscreenDeviceModelB5.mSubRoomNotification;
                                if ((subscreenSubRoomNotification == null || (subscreenNotificationDetailAdapter2 = subscreenSubRoomNotification.mNotificationDetailAdapter) == null || !subscreenNotificationDetailAdapter2.mCallbackClicked) ? false : true) {
                                    subscreenDeviceModelB5.hideDetailNotification();
                                    SubscreenSubRoomNotification subscreenSubRoomNotification2 = subscreenDeviceModelB5.mSubRoomNotification;
                                    if (subscreenSubRoomNotification2 != null && (subscreenNotificationDetailAdapter = subscreenSubRoomNotification2.mNotificationDetailAdapter) != null) {
                                        subscreenNotificationDetailAdapter.cleanAdapter();
                                    }
                                }
                            }
                            Integer valueOf3 = keyguardActionInfo != null ? Integer.valueOf(keyguardActionInfo.mAction) : null;
                            if (valueOf3 != null && valueOf3.intValue() == 4) {
                                if ((keyguardActionInfo != null ? keyguardActionInfo.mEntry : null) != null) {
                                    boolean clickKnoxItem = subscreenDeviceModelB5.clickKnoxItem(keyguardActionInfo != null ? keyguardActionInfo.mEntry : null);
                                    if (clickKnoxItem) {
                                        KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m62m(" isClickedKnoxItem :", clickKnoxItem, "S.S.N.");
                                        subscreenDeviceModelB5.dismissImmediately(keyguardActionInfo != null ? keyguardActionInfo.mEntry : null);
                                    } else {
                                        subscreenDeviceModelB5.detailClicked(keyguardActionInfo != null ? keyguardActionInfo.mEntry : null);
                                    }
                                }
                            } else if (valueOf3 != null && valueOf3.intValue() == 1) {
                                if ((keyguardActionInfo != null ? keyguardActionInfo.mSubscreenParentItemViewHolder : null) != null) {
                                    boolean clickKnoxItem2 = subscreenDeviceModelB5.clickKnoxItem((keyguardActionInfo == null || (subscreenParentItemViewHolder2 = keyguardActionInfo.mSubscreenParentItemViewHolder) == null || (subscreenNotificationInfo = subscreenParentItemViewHolder2.mInfo) == null || (expandableNotificationRow = subscreenNotificationInfo.mRow) == null) ? null : expandableNotificationRow.mEntry);
                                    if (clickKnoxItem2) {
                                        KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m62m(" isClickedKnoxItem :", clickKnoxItem2, "S.S.N.");
                                    } else if (keyguardActionInfo != null && (subscreenParentItemViewHolder = keyguardActionInfo.mSubscreenParentItemViewHolder) != null) {
                                        SubscreenSubRoomNotification subscreenSubRoomNotification3 = subscreenDeviceModelB5.mSubRoomNotification;
                                        subscreenParentItemViewHolder.animateClickNotification(subscreenSubRoomNotification3 != null ? subscreenSubRoomNotification3.mNotificationAnimatorManager : null, subscreenSubRoomNotification3, false);
                                    }
                                }
                            } else if (valueOf3 != null && valueOf3.intValue() == 2) {
                                System.out.println((Object) "ACTION_KEYGUARD_BIO_LIST_HIDE_CONTENT");
                            } else if (valueOf3 != null && valueOf3.intValue() == 3) {
                                Context context2 = keyguardActionInfo != null ? keyguardActionInfo.mContext : null;
                                Intrinsics.checkNotNull(context2);
                                SubscreenNotificationDetailAdapter.ItemViewHolder itemViewHolder = keyguardActionInfo != null ? keyguardActionInfo.mDetailAdapterItemViewHolder : null;
                                Intrinsics.checkNotNull(itemViewHolder);
                                SubscreenDeviceModelB5.access$showReplyActivity(subscreenDeviceModelB5, context2, itemViewHolder);
                            }
                        }
                    } else {
                        String str = subscreenDeviceModelB5.mUnlockNotificationPendingIntentItemKey;
                        if (str != null) {
                            KeyguardStateController keyguardStateController6 = subscreenDeviceModelB5.mKeyguardStateController;
                            if (((keyguardStateController6 == null || ((KeyguardStateControllerImpl) keyguardStateController6).mShowing) ? false : true) && (entry = ((NotifPipeline) subscreenDeviceModelB5.mNotifCollection).getEntry(str)) != null && entry.row != null) {
                                ExifInterface$$ExternalSyntheticOutline0.m35m(new StringBuilder("Unlock click notification : "), entry.mKey, "S.S.N.");
                                NotificationActivityStarter notificationActivityStarter = subscreenDeviceModelB5.mNotificationActivityStarter;
                                if (notificationActivityStarter != null) {
                                    ((StatusBarNotificationActivityStarter) notificationActivityStarter).onNotificationClicked(entry, entry.row);
                                }
                            }
                        }
                        subscreenDeviceModelB5.mUnlockNotificationPendingIntentItemKey = null;
                    }
                    boolean z3 = subscreenDeviceModelB5.mIsClickedPopupKeyguardUnlockShowing;
                    if (z3) {
                        KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m62m("onKeyguardShowingChanged - mIsClickedPopupKeyguardUnlockShowing : ", z3, "S.S.N.");
                    } else if (keyguardActionInfo != null) {
                        keyguardActionInfo.mAction = 0;
                        keyguardActionInfo.mSubscreenParentItemViewHolder = null;
                        keyguardActionInfo.mContext = null;
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
        this.mSrResponseCallback = new SubscreenDeviceModelB5$mSrResponseCallback$1(this);
    }

    public static final void access$handleTextLinkClick(SubscreenDeviceModelB5 subscreenDeviceModelB5, String str) {
        SubRoom.StateChangeListener stateChangeListener;
        subscreenDeviceModelB5.getClass();
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        intent.setData(Uri.parse("https://policies.account.samsung.com/terms?appKey=j5p7ll8g33" + "&applicationRegion=".concat(subscreenDeviceModelB5.getIsoCountryCode()) + KeyAttributes$$ExternalSyntheticOutline0.m21m("&language=", Locale.getDefault().getLanguage()) + "&region=".concat(subscreenDeviceModelB5.getIsoCountryCode()) + "&type=".concat(str)));
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
        SettingsHelper settingsHelper = subscreenDeviceModelB5.mSettingsHelper;
        settingsHelper.getClass();
        if (NotiRune.NOTI_SUBSCREEN_NOTIFICATION_FIFTH) {
            Settings.Global.putInt(settingsHelper.mContext.getContentResolver(), "suggestion_responses", 1);
            settingsHelper.mItemLists.get("suggestion_responses").mIntValue = 1;
        }
        SettingsHelper settingsHelper2 = subscreenDeviceModelB5.mSettingsHelper;
        settingsHelper2.setSuggestResponsesUsed();
        subscreenDeviceModelB5.isSuggestResponsesEnabled = settingsHelper2.isSuggestResponsesEnabled();
        boolean isSmartReplyUnusable = subscreenDeviceModelB5.isSmartReplyUnusable();
        subscreenDeviceModelB5.isUnusableAccount = isSmartReplyUnusable;
        subscreenDeviceModelB5.isPossibleAiReply = !isSmartReplyUnusable;
        LinearLayout linearLayout = subscreenDeviceModelB5.suggestResponsesBtn;
        if (linearLayout == null) {
            linearLayout = null;
        }
        linearLayout.setVisibility(8);
        ImageView imageView = subscreenDeviceModelB5.smartReplyTriggerBtn;
        if (imageView != null) {
            imageView.post(new Runnable() { // from class: com.android.systemui.statusbar.notification.SubscreenDeviceModelB5$handleTurnOnClick$1
                @Override // java.lang.Runnable
                public final void run() {
                    SubscreenRecyclerView subscreenRecyclerView;
                    SubscreenRecyclerView subscreenRecyclerView2;
                    LinearLayout linearLayout2 = SubscreenDeviceModelB5.this.detailButtonContainer;
                    if (linearLayout2 == null) {
                        linearLayout2 = null;
                    }
                    float y = linearLayout2.getY();
                    SubscreenSubRoomNotification subscreenSubRoomNotification = SubscreenDeviceModelB5.this.mSubRoomNotification;
                    int computeVerticalScrollOffset = ((int) y) - ((subscreenSubRoomNotification == null || (subscreenRecyclerView2 = subscreenSubRoomNotification.mNotificationRecyclerView) == null) ? 0 : subscreenRecyclerView2.computeVerticalScrollOffset());
                    if (computeVerticalScrollOffset == 0) {
                        SubscreenDeviceModelB5.this.showAIReply();
                        return;
                    }
                    SubscreenSubRoomNotification subscreenSubRoomNotification2 = SubscreenDeviceModelB5.this.mSubRoomNotification;
                    if (subscreenSubRoomNotification2 == null || (subscreenRecyclerView = subscreenSubRoomNotification2.mNotificationRecyclerView) == null) {
                        return;
                    }
                    subscreenRecyclerView.smoothScrollBy(0, computeVerticalScrollOffset, false);
                }
            });
        }
    }

    public static final boolean access$isSupportableLanguage(SubscreenDeviceModelB5 subscreenDeviceModelB5, String str) {
        subscreenDeviceModelB5.getClass();
        if (!NotiRune.NOTI_SUBSCREEN_SUPPORT_SMART_REPLY_AI_FOR_CHINA) {
            List list = subscreenDeviceModelB5.onDeviceLanguageList;
            if (list != null) {
                Iterator it = ((ArrayList) list).iterator();
                while (it.hasNext()) {
                    if (((LlmLanguage) it.next()).language.equals(str)) {
                    }
                }
            }
            return false;
        }
        return true;
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
            AbstractC0000x2c234b15.m3m("start SubscreenNotificationReplyActivity. key: ", itemViewHolder.mInfo.mKey, "S.S.N.");
            SystemUIAnalytics.sendEventCDLog("QPN102", "QPNE0203", "app", itemViewHolder.mInfo.mPkg);
        }
    }

    public static void bindContent(View view, String str, SubscreenNotificationDetailAdapter.ItemViewHolder itemViewHolder) {
        TextView textView = (TextView) view.findViewById(R.id.detail_content_text);
        boolean z = true;
        if (str != null) {
            if (!(StringsKt__StringsKt.trim(str).toString().length() == 0)) {
                z = false;
            }
        }
        if (z) {
            return;
        }
        textView.setVisibility(0);
        textView.setText(str);
        itemViewHolder.mBodyLayoutString = AbstractResolvableFuture$$ExternalSyntheticOutline0.m14m(itemViewHolder.mBodyLayoutString, str);
    }

    public static void bindTime(View view, long j, SubscreenNotificationDetailAdapter.ItemViewHolder itemViewHolder) {
        if (j <= 0) {
            return;
        }
        DateTimeView findViewById = view.findViewById(R.id.detail_clock);
        findViewById.setVisibility(0);
        findViewById.setTime(j);
        itemViewHolder.mBodyLayoutString = itemViewHolder.mBodyLayoutString + ((Object) findViewById.getText());
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
            SystemUIAnalytics.sendEventLog("QPN100", "QPNE0213");
            return;
        }
        if (subscreenParentItemViewHolder.mInfo.mSbn.getNotification().fullScreenIntent != null && !isLaunchApp(subscreenParentItemViewHolder.mInfo.mRow.mEntry)) {
            String str = subscreenParentItemViewHolder.mInfo.mSbn.getNotification().category;
            if (subscreenParentItemViewHolder.mInfo.mIsCall || "alarm".equals(str)) {
                NotificationEntry notificationEntry2 = subscreenParentItemViewHolder.mInfo.mRow.mEntry;
                StringBuilder sb = new StringBuilder("clickAdapterItem B5 - put fullscreenIntent : ");
                String str2 = notificationEntry2.mKey;
                ExifInterface$$ExternalSyntheticOutline0.m35m(sb, str2, "S.S.N.");
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
        Boolean valueOf = Boolean.valueOf(isKnoxSecurity(notificationEntry));
        Log.d("S.S.N.", " clickKnoxItem - isKnoxSecurity : " + valueOf);
        if (!valueOf.booleanValue() || !isLaunchApp(notificationEntry)) {
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
        if (this.mPresentation != null) {
            LinkedHashMap linkedHashMap = this.mFullScreenIntentEntries;
            if (linkedHashMap.isEmpty()) {
                return;
            }
            linkedHashMap.clear();
            this.mIsFullscreenFullPopupWindowClosing = true;
            dismissImmediately(1);
        }
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
        Context context = this.mContext;
        if (isKeyguardStats) {
            Intrinsics.checkNotNull(notificationEntry);
            if (notificationEntry.row.needsRedaction() || isLaunchApp(notificationEntry)) {
                showBouncer(context, notificationEntry);
                if (keyguardActionInfo != null) {
                    keyguardActionInfo.mAction = 4;
                    keyguardActionInfo.mEntry = notificationEntry;
                    return;
                }
                return;
            }
        }
        if (isKeyguardUnlockShowing()) {
            ((KeyguardManager) context.getSystemService("keyguard")).semDismissKeyguard();
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
            ((SubscreenUtil) Dependency.get(SubscreenUtil.class)).closeSubscreenPanel();
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
                KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m62m("dispatchKeyEvent() - navi back click, ret: ", z, "S.S.N.");
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
        int dimensionPixelSize = 720 - getMDisplayContext().getResources().getDimensionPixelSize(R.dimen.subscreen_noti_cut_out_size_b5);
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
            boolean z2 = true;
            if (!(alpha == f)) {
                ViewPropertyAnimator duration = imageView.animate().alpha(z ? 1.0f : 0.4f).setDuration(200L);
                duration.withEndAction(new Runnable() { // from class: com.android.systemui.statusbar.notification.SubscreenDeviceModelB5$enableSmartReplyTriggerBtn$1$1$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        imageView.setAlpha(f);
                    }
                });
                duration.start();
            }
            if (!Intrinsics.areEqual(str, "unsupportedLanguage") && !Intrinsics.areEqual(str, "emptyMessage")) {
                z2 = z;
            } else if (this.smartReplyStatus == 2) {
                z2 = false;
            }
            imageView.setEnabled(z2);
        }
        TextView textView = this.smartReplyTriggerBtnText;
        if (textView != null) {
            textView.setAlpha(z ? 1.0f : 0.4f);
            textView.setEnabled(z);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:12:0x0022, code lost:
    
        if (((r2 == null || (r2 = r2.itemView) == null || !r2.hasWindowFocus()) ? false : true) == false) goto L15;
     */
    /* JADX WARN: Code restructure failed: missing block: B:13:0x0033, code lost:
    
        r1 = r6.mSubRoomNotification;
     */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x0035, code lost:
    
        if (r1 == null) goto L32;
     */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x0037, code lost:
    
        r2 = r6.mNotificationActivityStarter;
        r1 = r1.mNotificationDetailAdapter;
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x003b, code lost:
    
        if (r1 == null) goto L32;
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x003f, code lost:
    
        if (r2 != null) goto L28;
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x0041, code lost:
    
        android.util.Log.e("SubscreenNotificationDetailAdapter", "notificationActivityStarter is null");
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x0049, code lost:
    
        if (r1.mSelectNotificationInfo == null) goto L31;
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x004b, code lost:
    
        android.util.Log.e("SubscreenNotificationDetailAdapter", "startNotificationActivity  mSelectNotificationInfo : " + r1.mSelectNotificationInfo.mKey);
        r3 = r1.mSelectNotificationInfo.mRow;
        ((com.android.systemui.statusbar.phone.StatusBarNotificationActivityStarter) r2).onNotificationClicked(r3.mEntry, r3);
        ((com.android.systemui.shade.ShadeControllerImpl) ((com.android.systemui.statusbar.phone.CentralSurfacesImpl) ((com.android.systemui.statusbar.phone.CentralSurfaces) com.android.systemui.Dependency.get(com.android.systemui.statusbar.phone.CentralSurfaces.class))).mShadeController).makeExpandedInvisible();
        r1.cleanAdapter();
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x0081, code lost:
    
        android.util.Log.e("SubscreenNotificationDetailAdapter", "startNotificationActivity no select holder...");
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x0087, code lost:
    
        r1 = r6.mSubRoomNotification;
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x0089, code lost:
    
        if (r1 == null) goto L46;
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x008d, code lost:
    
        if (r1.mIsShownDetail == false) goto L46;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x008f, code lost:
    
        r1 = r1.mNotificationDetailAdapter;
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x0091, code lost:
    
        if (r1 == null) goto L41;
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x0093, code lost:
    
        r1 = r1.mSelectNotificationInfo;
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x0095, code lost:
    
        if (r1 == null) goto L41;
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x0097, code lost:
    
        r0 = r1.mPkg;
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x0099, code lost:
    
        com.android.systemui.util.SystemUIAnalytics.sendEventCDLog("QPN102", "QPNE0212", "app", r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x0031, code lost:
    
        if (r1 != false) goto L21;
     */
    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelCommon, com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void foldStateChanged(boolean z) {
        String str = null;
        boolean z2 = false;
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
        } else {
            if (Intrinsics.areEqual(getTopActivityName(), "com.android.systemui.subscreen.SubHomeActivity")) {
                SubscreenNotificationDetailAdapter.ItemViewHolder itemViewHolder = this.detailViewHolder;
            }
            SubscreenNotificationReplyActivity subscreenNotificationReplyActivity = this.mController.replyActivity;
            if (subscreenNotificationReplyActivity != null && subscreenNotificationReplyActivity.hasWindowFocus()) {
                z2 = true;
            }
        }
        super.foldStateChanged(z);
    }

    /* JADX WARN: Code restructure failed: missing block: B:69:0x00ea, code lost:
    
        if ((r8 != null ? r8.mPrevLastHistoryView : null) == null) goto L80;
     */
    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final int getDetailAdapterAutoScrollCurrentPositionByReceive(View view) {
        LinearLayout linearLayout;
        SubscreenNotificationDetailAdapter subscreenNotificationDetailAdapter;
        SubscreenRecyclerView subscreenRecyclerView;
        SubscreenRecyclerView subscreenRecyclerView2;
        SubscreenRecyclerView subscreenRecyclerView3;
        SubscreenNotificationDetailAdapter subscreenNotificationDetailAdapter2;
        SubscreenNotificationDetailAdapter subscreenNotificationDetailAdapter3;
        SubscreenSubRoomNotification subscreenSubRoomNotification = this.mSubRoomNotification;
        if ((subscreenSubRoomNotification == null || subscreenSubRoomNotification.mIsShownDetail) ? false : true) {
            Log.d("S.S.N.", "getDetailAdapterAutoScrollCurrentPositionByReceive B5 - return - mSubRoomNotification?.isShownDetail is false");
            return 0;
        }
        if (this.mController.replyActivity != null) {
            Log.d("S.S.N.", "getDetailAdapterAutoScrollCurrentPositionByReceive B5 - return - show reply activity");
            return 0;
        }
        if (((subscreenSubRoomNotification == null || (subscreenNotificationDetailAdapter3 = subscreenSubRoomNotification.mNotificationDetailAdapter) == null) ? null : subscreenNotificationDetailAdapter3.mSelectNotificationInfo) == null) {
            if (subscreenSubRoomNotification != null && (subscreenNotificationDetailAdapter2 = subscreenSubRoomNotification.mNotificationDetailAdapter) != null) {
                r3 = subscreenNotificationDetailAdapter2.mSelectNotificationInfo;
            }
            Log.d("S.S.N.", "getDetailAdapterAutoScrollCurrentPositionByReceive B5 - return - value is null  View : " + view + ", selectNotificationInfo : " + r3);
            return 0;
        }
        if (((subscreenSubRoomNotification == null || (subscreenRecyclerView3 = subscreenSubRoomNotification.mNotificationRecyclerView) == null) ? null : subscreenRecyclerView3.getChildViewHolder(view)) instanceof SubscreenParentDetailItemViewHolder) {
            SubscreenSubRoomNotification subscreenSubRoomNotification2 = this.mSubRoomNotification;
            SubscreenParentDetailItemViewHolder subscreenParentDetailItemViewHolder = (SubscreenParentDetailItemViewHolder) ((subscreenSubRoomNotification2 == null || (subscreenRecyclerView2 = subscreenSubRoomNotification2.mNotificationRecyclerView) == null) ? null : subscreenRecyclerView2.getChildViewHolder(view));
            if (subscreenParentDetailItemViewHolder.mInfo.mIsMessagingStyle && (linearLayout = subscreenParentDetailItemViewHolder.mContentLayout) != null) {
                int childCount = linearLayout.getChildCount();
                if (childCount == 0) {
                    Log.d("S.S.N.", "getDetailAdapterAutoScrollCurrentPositionByReceive B5 - size is zero");
                    return 0;
                }
                int dimensionPixelSize = getMDisplayContext().getResources().getDimensionPixelSize(R.dimen.subscreen_noti_cut_out_size_b5);
                SubscreenSubRoomNotification subscreenSubRoomNotification3 = this.mSubRoomNotification;
                Integer valueOf = (subscreenSubRoomNotification3 == null || (subscreenRecyclerView = subscreenSubRoomNotification3.mNotificationRecyclerView) == null) ? null : Integer.valueOf(subscreenRecyclerView.computeVerticalScrollOffset());
                Intrinsics.checkNotNull(valueOf);
                int intValue = valueOf.intValue();
                int mainHeaderViewHeight = ((720 - getMainHeaderViewHeight()) - dimensionPixelSize) + intValue;
                SubscreenSubRoomNotification subscreenSubRoomNotification4 = this.mSubRoomNotification;
                SubscreenNotificationDetailAdapter.ScrollInfo scrollInfo = (subscreenSubRoomNotification4 == null || (subscreenNotificationDetailAdapter = subscreenSubRoomNotification4.mNotificationDetailAdapter) == null) ? null : subscreenNotificationDetailAdapter.mScrollInfo;
                if ((scrollInfo != null ? scrollInfo.mPrevFirstHistoryView : null) == null) {
                    ViewGroupKt.get(linearLayout, 0);
                }
                View view2 = scrollInfo != null ? scrollInfo.mPrevLastHistoryView : null;
                if (view2 == null) {
                    view2 = ViewGroupKt.get(linearLayout, childCount - 1);
                }
                if ((scrollInfo != null ? scrollInfo.mPrevFirstHistoryView : null) != null) {
                }
                Log.d("S.S.N.", "getDetailAdapterAutoScrollCurrentPositionByReceive B5 - prevItem is null,scrollInfo?.prevFirstHistoryView :" + (scrollInfo != null ? scrollInfo.mPrevFirstHistoryView : null) + ", scrollInfo?.prevLastHistoryView : " + (scrollInfo != null ? scrollInfo.mPrevLastHistoryView : null));
                float y = view2.getY() + (view2.getHeight() / 3);
                float f = mainHeaderViewHeight;
                if (f > y && intValue < y) {
                    return 3;
                }
                if (f < y) {
                    return 1;
                }
                if (intValue > y) {
                    return 2;
                }
            }
        }
        return 0;
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final int getDetailAdapterContentViewResource() {
        return R.layout.subscreen_notification_detail_adapter_content_layout_item_b5;
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final View getDetailAdapterLayout(RecyclerView recyclerView, int i, Context context) {
        return LayoutInflater.from(context).inflate(i != 0 ? i != 1 ? -1 : R.layout.subscreen_notification_detail_adapter_text_item_b5 : R.layout.subscreen_notification_detail_adapter_item_b5, (ViewGroup) recyclerView, false);
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final int getDetailAdapterReplyWordResource() {
        return R.layout.f766x6f91e6f9;
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final int getDispalyHeight() {
        return 720;
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
        return new Locale((String) split$default.get(0), (String) split$default.get(1)).getDisplayName();
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final int getFullPopupWindowType() {
        return this.mFullScreenIntentEntries.isEmpty() ? 2026 : 2040;
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final View getGroupAdapterLayout(RecyclerView recyclerView, int i, Context context) {
        View inflate = LayoutInflater.from(context).inflate(i != 0 ? i != 1 ? i != 2 ? i != 4 ? i != 5 ? -1 : R.layout.subscreen_notification_group_adapter_custom_view_b5 : R.layout.subscreen_notification_group_adapter_hide_content_b5 : R.layout.subscreen_notification_adapter_header_b5 : R.layout.subscreen_notification_adapter_clear_all_footer_b5 : R.layout.subscreen_notification_group_adapter_item_b5, (ViewGroup) recyclerView, false);
        if (i != 1) {
            inflate.setBackground(getMDisplayContext().getDrawable(R.drawable.subscreen_notification_list_item_bg_selecter_b5));
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
        for (int size3 = arrayList.size() + (-7) < 0 ? 0 : arrayList.size() - 7; size3 < size2; size3++) {
            SubscreenNotificationInfo.MessagingStyleInfo messagingStyleInfo = (SubscreenNotificationInfo.MessagingStyleInfo) arrayList.get(size3);
            long j = messagingStyleInfo.mPostedTime;
            if (j <= 0) {
                j = messagingStyleInfo.mTimeStamp;
            }
            Date date2 = new Date(j);
            if (size3 >= size2 - 1 || (date.getYear() <= date2.getYear() && date.getMonth() <= date2.getMonth() && date.getDay() <= date2.getDay() && date.getHours() - date2.getHours() <= 1)) {
                if (NotiRune.NOTI_SUBSCREEN_SUPPORT_SMART_REPLY_AI_FOR_CHINA) {
                    boolean z = messagingStyleInfo.mIsReply;
                    Context context = this.mContext;
                    if (z) {
                        sb.append(context.getString(R.string.subscreen_notification_smart_reply_user_for_chn));
                        sb2.append(context.getString(R.string.subscreen_notification_smart_reply_user_for_chn));
                    } else {
                        sb.append(context.getString(R.string.subscreen_notification_smart_reply_participant_for_chn));
                        sb2.append(context.getString(R.string.subscreen_notification_smart_reply_participant_for_chn));
                    }
                } else {
                    String obj = StringsKt__StringsKt.trim(messagingStyleInfo.mContentText).toString();
                    if (!(obj == null || obj.length() == 0)) {
                        if (messagingStyleInfo.mIsReply) {
                            sb.append("User: ");
                            sb2.append("User: ");
                        } else {
                            sb.append("Others: ");
                            sb2.append("Others: ");
                        }
                    }
                }
                sb.append(messagingStyleInfo.mContentText);
                sb.append("\n");
                sb2.append((String) TextUtils.trimToLengthWithEllipsis(messagingStyleInfo.mContentText, 2));
                sb2.append("\n");
            }
        }
        return sb.toString();
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0034  */
    /* JADX WARN: Removed duplicated region for block: B:14:0x0037 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:9:0x001f A[Catch: Exception -> 0x0038, TryCatch #0 {Exception -> 0x0038, blocks: (B:2:0x0000, B:4:0x0013, B:9:0x001f, B:10:0x0023), top: B:1:0x0000 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final String getIsoCountryCode() {
        TelephonyManager telephonyManager;
        String simCountryIso;
        boolean z;
        String iSO3Country;
        try {
            telephonyManager = (TelephonyManager) this.mContext.getSystemService("phone");
            simCountryIso = telephonyManager.getSimCountryIso();
        } catch (Exception e) {
            EmergencyButton$$ExternalSyntheticOutline0.m58m("getIsoCountryCode: ", e, "S.S.N.");
        }
        if (simCountryIso != null && simCountryIso.length() != 0) {
            z = false;
            if (z) {
                simCountryIso = telephonyManager.getNetworkCountryIso();
            }
            iSO3Country = new Locale("", simCountryIso).getISO3Country();
            if (iSO3Country.length() > 0) {
                return iSO3Country;
            }
            return Locale.getDefault().getISO3Country();
        }
        z = true;
        if (z) {
        }
        iSO3Country = new Locale("", simCountryIso).getISO3Country();
        if (iSO3Country.length() > 0) {
        }
        return Locale.getDefault().getISO3Country();
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
    public final View getListAdapterLayout(RecyclerView recyclerView, int i, Context context) {
        View inflate = LayoutInflater.from(context).inflate(i != 0 ? i != 1 ? i != 2 ? i != 3 ? i != 4 ? i != 5 ? -1 : R.layout.subscreen_notification_list_adapter_group_summary_layout_b5 : R.layout.subscreen_notification_list_adapter_hide_content_b5 : R.layout.subscreen_notification_adapter_no_notification_b5 : R.layout.subscreen_notification_list_adapter_custom_view_b5 : R.layout.subscreen_notification_adapter_clear_all_footer_b5 : R.layout.subscreen_notification_list_adapter_item_b5, (ViewGroup) recyclerView, false);
        if (i != 1 && i != 3) {
            inflate.setBackground(getMDisplayContext().getDrawable(R.drawable.subscreen_notification_list_item_bg_selecter_b5));
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
        View inflate = LayoutInflater.from(subHomeActivity).inflate(R.layout.f763x9e5f9591, (ViewGroup) null);
        View findViewById = inflate.findViewById(R.id.send);
        if (this.mIsReplySendButtonLoading) {
            findViewById.setEnabled(false);
            findViewById.setAlpha(0.5f);
        } else {
            findViewById.setEnabled(true);
            findViewById.setAlpha(1.0f);
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
        KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m62m("handleProgressLayout() - show : ", z, "S.S.N.");
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

    /* JADX WARN: Removed duplicated region for block: B:134:0x0238  */
    /* JADX WARN: Removed duplicated region for block: B:138:0x023e  */
    /* JADX WARN: Removed duplicated region for block: B:140:0x01f9  */
    /* JADX WARN: Removed duplicated region for block: B:78:0x0191  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void inflateSmartReplyAI(String str) {
        List<String> list;
        SubscreenNotificationDetailAdapter.ItemViewHolder itemViewHolder;
        int i;
        Runnable runnable;
        SubscreenNotificationDetailAdapter.ItemViewHolder itemViewHolder2;
        LinearLayout linearLayout;
        SubscreenNotificationDetailAdapter.ItemViewHolder itemViewHolder3;
        LinearLayout linearLayout2;
        LinearLayout linearLayout3;
        LinearLayout linearLayout4;
        final SubscreenNotificationDetailAdapter subscreenNotificationDetailAdapter;
        final SubscreenNotificationDetailAdapter.ItemViewHolder itemViewHolder4;
        LinearLayout linearLayout5;
        LinearLayout linearLayout6;
        Object obj;
        Log.d("S.S.N.", "inflateSmartReplyAI()");
        Integer num = null;
        if (str != null) {
            ArrayList arrayList = new ArrayList();
            LinkedHashSet linkedHashSet = new LinkedHashSet();
            Iterator it = StringsKt__StringsKt.split$default(str, new String[]{"\n"}, 0, 6).iterator();
            while (it.hasNext()) {
                linkedHashSet.add((String) it.next());
            }
            Iterator it2 = linkedHashSet.iterator();
            while (it2.hasNext()) {
                String str2 = (String) it2.next();
                String obj2 = StringsKt__StringsKt.trim(str2).toString();
                if (!(obj2 == null || obj2.length() == 0)) {
                    arrayList.add(str2);
                }
            }
            ArrayList arrayList2 = new ArrayList();
            ArrayList arrayList3 = new ArrayList();
            ArrayList arrayList4 = new ArrayList();
            Iterator it3 = arrayList.iterator();
            while (it3.hasNext()) {
                arrayList2.add(StringsKt__StringsKt.trim(StringsKt__StringsJVMKt.replace$default(StringsKt__StringsJVMKt.replace$default(StringsKt__StringsJVMKt.replace$default(StringsKt__StringsJVMKt.replace$default(StringsKt__StringsJVMKt.replace$default(StringsKt__StringsJVMKt.replace$default(StringsKt__StringsJVMKt.replace$default(StringsKt__StringsJVMKt.replace$default(StringsKt__StringsJVMKt.replace$default(StringsKt__StringsJVMKt.replace$default(StringsKt__StringsJVMKt.replace$default(StringsKt__StringsJVMKt.replace$default(StringsKt__StringsJVMKt.replace$default(StringsKt__StringsJVMKt.replace$default(StringsKt__StringsJVMKt.replace$default(StringsKt__StringsJVMKt.replace$default((String) it3.next(), "~", ""), "!", ""), "@", ""), "#", ""), "$", ""), "%", ""), "^", ""), "&", ""), "*", "("), ")", ""), "`", ""), ",", ""), ".", ""), "-", ""), "_", ""), "\"", "")).toString());
            }
            int size = arrayList2.size();
            for (int i2 = 0; i2 < size; i2++) {
                if (!arrayList4.contains(Integer.valueOf(i2))) {
                    int size2 = arrayList2.size();
                    for (int i3 = i2; i3 < size2; i3++) {
                        if (i2 != i3 && !arrayList4.contains(Integer.valueOf(i3)) && ((String) arrayList2.get(i2)).equals((String) arrayList2.get(i3))) {
                            arrayList4.add(Integer.valueOf(i3));
                        }
                    }
                }
            }
            int size3 = arrayList.size();
            for (int i4 = 0; i4 < size3; i4++) {
                if (arrayList4.size() == 0 || !arrayList4.contains(Integer.valueOf(i4))) {
                    arrayList3.add((String) arrayList.get(i4));
                }
            }
            if (arrayList3.size() != 0) {
                int size4 = arrayList3.size() < 3 ? arrayList3.size() : 3;
                try {
                    int i5 = Result.$r8$clinit;
                    obj = arrayList3;
                } catch (Throwable th) {
                    int i6 = Result.$r8$clinit;
                    obj = new Result.Failure(th);
                }
                Object obj3 = EmptyList.INSTANCE;
                boolean z = obj instanceof Result.Failure;
                Object obj4 = obj;
                if (z) {
                    obj4 = obj3;
                }
                list = ((List) obj4).subList(0, size4);
                itemViewHolder = this.detailViewHolder;
                if (itemViewHolder != null && (linearLayout6 = itemViewHolder.mSmartReplyLayout) != null) {
                    linearLayout6.removeAllViews();
                }
                if (list == null) {
                    for (String str3 : list) {
                        LayoutInflater from = LayoutInflater.from(getMDisplayContext());
                        SubscreenNotificationDetailAdapter.ItemViewHolder itemViewHolder5 = this.detailViewHolder;
                        final View inflate = from.inflate(R.layout.f765x83d886e4, (ViewGroup) (itemViewHolder5 != null ? itemViewHolder5.mContentLayout : null), false);
                        TextView textView = (TextView) inflate.findViewById(R.id.subscreen_detail_word);
                        if (textView != null) {
                            textView.setText(StringsKt__StringsKt.trim(str3).toString());
                        }
                        SubscreenNotificationDetailAdapter.ItemViewHolder itemViewHolder6 = this.detailViewHolder;
                        if (itemViewHolder6 != null && (linearLayout5 = itemViewHolder6.mSmartReplyLayout) != null) {
                            linearLayout5.addView(inflate);
                        }
                        SubscreenSubRoomNotification subscreenSubRoomNotification = this.mSubRoomNotification;
                        if (subscreenSubRoomNotification != null && (subscreenNotificationDetailAdapter = subscreenSubRoomNotification.mNotificationDetailAdapter) != null && (itemViewHolder4 = this.detailViewHolder) != null && itemViewHolder4.mSmartReplyLayout != null) {
                            inflate.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.statusbar.notification.SubscreenNotificationDetailAdapter$$ExternalSyntheticLambda1
                                @Override // android.view.View.OnClickListener
                                public final void onClick(View view) {
                                    SubscreenNotificationDetailAdapter subscreenNotificationDetailAdapter2 = SubscreenNotificationDetailAdapter.this;
                                    SubscreenNotificationDetailAdapter.ItemViewHolder itemViewHolder7 = itemViewHolder4;
                                    View view2 = inflate;
                                    if (subscreenNotificationDetailAdapter2.mReplyButtonView != null) {
                                        Log.d("SubscreenNotificationDetailAdapter", "CoverReplyButtonView is already existed.");
                                        return;
                                    }
                                    subscreenNotificationDetailAdapter2.mDeviceModel.enableGoToTopButton();
                                    int dispalyHeight = subscreenNotificationDetailAdapter2.mDeviceModel.getDispalyHeight() - subscreenNotificationDetailAdapter2.mDeviceModel.getMainHeaderViewHeight();
                                    int computeVerticalScrollOffset = subscreenNotificationDetailAdapter2.mSubRoomNotification.mNotificationRecyclerView.computeVerticalScrollOffset();
                                    float y = itemViewHolder7.mReplyContainer.getY();
                                    LinearLayout linearLayout7 = itemViewHolder7.mSmartReplyLayout;
                                    subscreenNotificationDetailAdapter2.mSubRoomNotification.mNotificationRecyclerView.smoothScrollBy(0, (int) ((view.getY() + (linearLayout7.getY() + y)) - (((dispalyHeight / 2) + computeVerticalScrollOffset) - (view.getHeight() / 2))), false);
                                    TextView textView2 = (TextView) view2.findViewById(R.id.subscreen_detail_word);
                                    for (int i7 = 0; i7 < linearLayout7.getChildCount(); i7++) {
                                        View childAt = linearLayout7.getChildAt(i7);
                                        if (childAt.equals(view)) {
                                            subscreenNotificationDetailAdapter2.setSmartReplyWordTextStyle(childAt, 0.2f, 1.0f);
                                            subscreenNotificationDetailAdapter2.showReplyButtons(itemViewHolder7, textView2.getText().toString(), view, "AI generated");
                                        } else {
                                            subscreenNotificationDetailAdapter2.setSmartReplyWordTextStyle(childAt, 1.0f, 0.2f);
                                        }
                                    }
                                    int i8 = 0;
                                    while (true) {
                                        LinearLayout linearLayout8 = itemViewHolder7.mReplylayout;
                                        if (i8 >= linearLayout8.getChildCount()) {
                                            subscreenNotificationDetailAdapter2.mNotificationAnimatorManager.alphaViewAnimated(itemViewHolder7.mDetailButtonLayout, null, 250L, 1.0f, 0.2f);
                                            subscreenNotificationDetailAdapter2.mNotificationAnimatorManager.alphaViewAnimated(itemViewHolder7.mEditButton, null, 250L, 1.0f, 0.2f);
                                            SystemUIAnalytics.sendEventLog("QPN102", "QPNE0207");
                                            return;
                                        }
                                        subscreenNotificationDetailAdapter2.setReplyWordTextStyle(linearLayout8.getChildAt(i8), SubscreenNotificationDetailAdapter.REGULAR, false, 1.0f, 0.2f);
                                        i8++;
                                    }
                                }
                            });
                        }
                    }
                    i = list.size();
                } else {
                    i = 0;
                }
                runnable = new Runnable() { // from class: com.android.systemui.statusbar.notification.SubscreenDeviceModelB5$inflateSmartReplyAI$runnable$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        LinearLayout linearLayout7 = SubscreenDeviceModelB5.this.progressLayout;
                        if (linearLayout7 != null) {
                            linearLayout7.setVisibility(8);
                        }
                        LottieAnimationView lottieAnimationView = SubscreenDeviceModelB5.this.progressingVi;
                        if (lottieAnimationView != null) {
                            lottieAnimationView.cancelAnimation();
                        }
                    }
                };
                itemViewHolder2 = this.detailViewHolder;
                if (itemViewHolder2 != null && (linearLayout4 = itemViewHolder2.mReplylayout) != null) {
                    linearLayout4.getY();
                }
                linearLayout = this.progressLayout;
                if ((linearLayout == null && linearLayout.getVisibility() == 0) && (linearLayout3 = this.progressLayout) != null) {
                    startProgressSpringAnimation(linearLayout3, false, runnable);
                }
                itemViewHolder3 = this.detailViewHolder;
                if (itemViewHolder3 != null && (linearLayout2 = itemViewHolder3.mSmartReplyLayout) != null) {
                    num = Integer.valueOf(linearLayout2.getChildCount());
                }
                Intrinsics.checkNotNull(num);
                if (num.intValue() != 0) {
                    updateVisibilityForSmartReplyLayout(8);
                } else {
                    this.mHandler.postDelayed(new Runnable() { // from class: com.android.systemui.statusbar.notification.SubscreenDeviceModelB5$inflateSmartReplyAI$runnable$2
                        /* JADX WARN: Multi-variable type inference failed */
                        /* JADX WARN: Type inference failed for: r10v1 */
                        /* JADX WARN: Type inference failed for: r10v15 */
                        /* JADX WARN: Type inference failed for: r10v2, types: [T, android.view.View] */
                        @Override // java.lang.Runnable
                        public final void run() {
                            LinearLayout linearLayout7;
                            LinearLayout linearLayout8;
                            SubscreenDeviceModelB5 subscreenDeviceModelB5 = SubscreenDeviceModelB5.this;
                            int i7 = SubscreenDeviceModelB5.$r8$clinit;
                            subscreenDeviceModelB5.resetProgressScaleAnimation();
                            SubscreenDeviceModelB5.this.updateVisibilityForSmartReplyLayout(0);
                            SubscreenDeviceModelB5 subscreenDeviceModelB52 = SubscreenDeviceModelB5.this;
                            SubscreenNotificationDetailAdapter.ItemViewHolder itemViewHolder7 = subscreenDeviceModelB52.detailViewHolder;
                            Integer valueOf = (itemViewHolder7 == null || (linearLayout8 = itemViewHolder7.mSmartReplyLayout) == null) ? null : Integer.valueOf(linearLayout8.getChildCount());
                            Intrinsics.checkNotNull(valueOf);
                            float intValue = ((3 - valueOf.intValue()) * 90) - 181.0f;
                            SubscreenNotificationDetailAdapter.ItemViewHolder itemViewHolder8 = subscreenDeviceModelB52.detailViewHolder;
                            LinearLayout linearLayout9 = itemViewHolder8 != null ? itemViewHolder8.mReplylayout : null;
                            if (linearLayout9 != null) {
                                linearLayout9.setTranslationY(intValue);
                            }
                            SubscreenNotificationDetailAdapter.ItemViewHolder itemViewHolder9 = subscreenDeviceModelB52.detailViewHolder;
                            ObjectAnimator ofFloat = ObjectAnimator.ofFloat(itemViewHolder9 != null ? itemViewHolder9.mReplylayout : null, (Property<LinearLayout, Float>) View.TRANSLATION_Y, 0.0f);
                            ofFloat.setInterpolator(Interpolators.FAST_OUT_LINEAR_IN);
                            ofFloat.setDuration(200L);
                            ofFloat.start();
                            final SpringForce springForce = new SpringForce(1.0f);
                            springForce.setStiffness(200.0f);
                            springForce.setDampingRatio(0.75f);
                            int intValue2 = valueOf.intValue();
                            int i8 = 0;
                            for (int i9 = 0; i9 < intValue2; i9++) {
                                final Ref$ObjectRef ref$ObjectRef = new Ref$ObjectRef();
                                SubscreenNotificationDetailAdapter.ItemViewHolder itemViewHolder10 = subscreenDeviceModelB52.detailViewHolder;
                                ?? childAt = (itemViewHolder10 == null || (linearLayout7 = itemViewHolder10.mSmartReplyLayout) == null) ? 0 : linearLayout7.getChildAt(i9);
                                ref$ObjectRef.element = childAt;
                                if (childAt != 0) {
                                    childAt.setScaleX(0.85f);
                                    ((View) ref$ObjectRef.element).setScaleY(0.85f);
                                    ((View) ref$ObjectRef.element).setAlpha(0.0f);
                                    ((View) ref$ObjectRef.element).animate().alpha(1.0f).setDuration(200L).setListener(null);
                                    subscreenDeviceModelB52.mHandler.postDelayed(new Runnable() { // from class: com.android.systemui.statusbar.notification.SubscreenDeviceModelB5$startSmartReplyListSpringAnimation$runnable$1
                                        @Override // java.lang.Runnable
                                        public final void run() {
                                            SpringAnimation springAnimation = new SpringAnimation(ref$ObjectRef.element, DynamicAnimation.SCALE_X);
                                            springAnimation.mSpring = springForce;
                                            springAnimation.start();
                                            SpringAnimation springAnimation2 = new SpringAnimation(ref$ObjectRef.element, DynamicAnimation.SCALE_Y);
                                            springAnimation2.mSpring = springForce;
                                            springAnimation2.start();
                                        }
                                    }, i8);
                                    i8 += 100;
                                }
                            }
                        }
                    }, 200L);
                    SystemUIAnalytics.sendEventCDLog("QPN102", "QPNE0215", "count", String.valueOf(i));
                }
                this.smartReplyStatus = 0;
            }
        }
        list = null;
        itemViewHolder = this.detailViewHolder;
        if (itemViewHolder != null) {
            linearLayout6.removeAllViews();
        }
        if (list == null) {
        }
        runnable = new Runnable() { // from class: com.android.systemui.statusbar.notification.SubscreenDeviceModelB5$inflateSmartReplyAI$runnable$1
            @Override // java.lang.Runnable
            public final void run() {
                LinearLayout linearLayout7 = SubscreenDeviceModelB5.this.progressLayout;
                if (linearLayout7 != null) {
                    linearLayout7.setVisibility(8);
                }
                LottieAnimationView lottieAnimationView = SubscreenDeviceModelB5.this.progressingVi;
                if (lottieAnimationView != null) {
                    lottieAnimationView.cancelAnimation();
                }
            }
        };
        itemViewHolder2 = this.detailViewHolder;
        if (itemViewHolder2 != null) {
            linearLayout4.getY();
        }
        linearLayout = this.progressLayout;
        if (linearLayout == null && linearLayout.getVisibility() == 0) {
            startProgressSpringAnimation(linearLayout3, false, runnable);
        }
        itemViewHolder3 = this.detailViewHolder;
        if (itemViewHolder3 != null) {
            num = Integer.valueOf(linearLayout2.getChildCount());
        }
        Intrinsics.checkNotNull(num);
        if (num.intValue() != 0) {
        }
        this.smartReplyStatus = 0;
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelCommon, com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final void initDetailAdapterItemViewHolder(final Context context, SubscreenNotificationDetailAdapter subscreenNotificationDetailAdapter, final SubscreenNotificationDetailAdapter.ItemViewHolder itemViewHolder) {
        super.initDetailAdapterItemViewHolder(context, subscreenNotificationDetailAdapter, itemViewHolder);
        Log.d("S.S.N.", "initDetailAdapterItemViewHolder() - B5");
        View view = itemViewHolder.itemView;
        this.detailButtonContainer = (LinearLayout) view.findViewById(R.id.detail_button_layout);
        ImageView imageView = (ImageView) view.findViewById(R.id.keyboard_reply_button);
        imageView.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.statusbar.notification.SubscreenDeviceModelB5$initDetailAdapterItemViewHolder$1$1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
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
        this.keyboardReplyButton = imageView;
        this.callBackButtonText = (TextView) view.findViewById(R.id.call_back_button_text);
        this.replyButtonText = (TextView) view.findViewById(R.id.keyboard_reply_button_text);
        this.openAppButtonText = (TextView) view.findViewById(R.id.app_open_button_text);
        this.clearButtonText = (TextView) view.findViewById(R.id.clear_button_text);
        this.smartReplyTriggerBtnText = (TextView) view.findViewById(R.id.smart_reply_trigger_text);
        boolean z = false;
        this.mSmartReplyClickedByUser = false;
        final ImageView imageView2 = (ImageView) view.findViewById(R.id.smart_reply_trigger_button);
        imageView2.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.statusbar.notification.SubscreenDeviceModelB5$initDetailAdapterItemViewHolder$2$1
            /* JADX WARN: Removed duplicated region for block: B:10:0x028c  */
            /* JADX WARN: Removed duplicated region for block: B:12:0x0291  */
            /* JADX WARN: Removed duplicated region for block: B:16:0x0294  */
            /* JADX WARN: Removed duplicated region for block: B:17:0x028e  */
            /* JADX WARN: Type inference failed for: r4v0 */
            /* JADX WARN: Type inference failed for: r4v10 */
            /* JADX WARN: Type inference failed for: r4v8, types: [boolean, int] */
            @Override // android.view.View.OnClickListener
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public final void onClick(View view2) {
                boolean z2;
                SubscreenRecyclerView subscreenRecyclerView;
                boolean z3;
                SubscreenRecyclerView subscreenRecyclerView2;
                SubscreenNotificationDialog subscreenNotificationDialog;
                SubscreenNotificationDialog subscreenNotificationDialog2;
                SubscreenNotificationDialog subscreenNotificationDialog3;
                final SubscreenDeviceModelB5 subscreenDeviceModelB5 = SubscreenDeviceModelB5.this;
                subscreenDeviceModelB5.mSmartReplyClickedByUser = true;
                ?? r4 = 0;
                if (subscreenDeviceModelB5.isUnusableAccount) {
                    if (!subscreenDeviceModelB5.isSALoggedIn) {
                        Log.d("S.S.N.", "checkAndExecuteSuggestResponses() : execute sa sign in");
                        subscreenDeviceModelB5.openPhonePopupForIntelligenceSettings("com.samsung.android.action.INTELLIGENCE_SERVICE_SETTINGS_START_INTENT");
                    } else if (subscreenDeviceModelB5.isChildAccount) {
                        Log.d("S.S.N.", "checkAndExecuteSuggestResponses() : execute child account announce");
                        SubscreenNotificationDialog subscreenNotificationDialog4 = subscreenDeviceModelB5.mDialog;
                        if (subscreenNotificationDialog4 != null) {
                            if ((subscreenNotificationDialog4.mDialog.isShowing()) && (subscreenNotificationDialog3 = subscreenDeviceModelB5.mDialog) != null) {
                                subscreenNotificationDialog3.dismiss();
                            }
                            subscreenDeviceModelB5.mDialog = null;
                        }
                        int i = subscreenDeviceModelB5.childGraduateAge;
                        SubscreenNotificationDialog subscreenNotificationDialog5 = new SubscreenNotificationDialog(subscreenDeviceModelB5.getMDisplayContext(), subscreenDeviceModelB5.getMDisplayContext().getResources().getQuantityString(R.plurals.subscreen_notification_smart_reply_child_account_dialog_content, i, Integer.valueOf(i)));
                        subscreenDeviceModelB5.mDialog = subscreenNotificationDialog5;
                        subscreenNotificationDialog5.show();
                    } else if (subscreenDeviceModelB5.isAiInfoConfirmed) {
                        boolean z4 = NotiRune.NOTI_SUBSCREEN_SUPPORT_SMART_REPLY_AI_FOR_CHINA;
                        if (z4 && subscreenDeviceModelB5.isPreventOnlineProcessing()) {
                            SubscreenNotificationDialog subscreenNotificationDialog6 = subscreenDeviceModelB5.mDialog;
                            if (subscreenNotificationDialog6 != null) {
                                if ((subscreenNotificationDialog6.mDialog.isShowing()) && (subscreenNotificationDialog2 = subscreenDeviceModelB5.mDialog) != null) {
                                    subscreenNotificationDialog2.dismiss();
                                }
                                subscreenDeviceModelB5.mDialog = null;
                            }
                            SubscreenNotificationDialog subscreenNotificationDialog7 = new SubscreenNotificationDialog(subscreenDeviceModelB5.getMDisplayContext(), subscreenDeviceModelB5.getMDisplayContext().getResources().getString(R.string.subscreen_online_processing_title), subscreenDeviceModelB5.getMDisplayContext().getResources().getString(R.string.subscreen_online_processing_message), new Runnable() { // from class: com.android.systemui.statusbar.notification.SubscreenDeviceModelB5$checkAndExecuteSuggestResponses$runnable$1
                                @Override // java.lang.Runnable
                                public final void run() {
                                    SubscreenDeviceModelB5 subscreenDeviceModelB52 = SubscreenDeviceModelB5.this;
                                    int i2 = SubscreenDeviceModelB5.$r8$clinit;
                                    subscreenDeviceModelB52.openPhonePopupForIntelligenceSettings("com.samsung.android.action.INTELLIGENCE_SERVICE_PROCESSING_ONLINE_INTENT");
                                }
                            });
                            subscreenDeviceModelB5.mDialog = subscreenNotificationDialog7;
                            subscreenNotificationDialog7.show();
                        } else if (!subscreenDeviceModelB5.isSuggestResponsesEnabled) {
                            Log.d("S.S.N.", "checkAndExecuteSuggestResponses() : execute suggest replies setting on");
                            SubscreenNotificationDialog subscreenNotificationDialog8 = subscreenDeviceModelB5.mDialog;
                            if (subscreenNotificationDialog8 != null) {
                                if ((subscreenNotificationDialog8.mDialog.isShowing()) && (subscreenNotificationDialog = subscreenDeviceModelB5.mDialog) != null) {
                                    subscreenNotificationDialog.dismiss();
                                }
                                subscreenDeviceModelB5.mDialog = null;
                            }
                            if (z4) {
                                final View inflate = LayoutInflater.from(subscreenDeviceModelB5.getMDisplayContext()).inflate(R.layout.subscreen_notification_smart_reply_disclaimer_dialog, (ViewGroup) null);
                                TextView textView = (TextView) inflate.findViewById(R.id.smart_reply_disclaimer_terms_and_conditions);
                                Button button = (Button) inflate.findViewById(R.id.smart_reply_disclaimer_ok_btn);
                                Button button2 = (Button) inflate.findViewById(R.id.smart_reply_disclaimer_cancel_btn);
                                String string = subscreenDeviceModelB5.mContext.getString(R.string.f802x82c49343);
                                String substringBefore$default = StringsKt__StringsKt.substringBefore$default(StringsKt__StringsKt.substringAfter$default(string, "%1$s"), "%2$s");
                                String substringBefore$default2 = StringsKt__StringsKt.substringBefore$default(StringsKt__StringsKt.substringAfter$default(string, "%3$s"), "%4$s");
                                ArrayList arrayList = new ArrayList();
                                arrayList.add(substringBefore$default);
                                arrayList.add(substringBefore$default2);
                                ClickableSpan clickableSpan = new ClickableSpan() { // from class: com.android.systemui.statusbar.notification.SubscreenDeviceModelB5$getDisclaimerViewForChina$clickableSpanForPrivacyNotice$1
                                    @Override // android.text.style.ClickableSpan
                                    public final void onClick(View view3) {
                                        SubscreenDeviceModelB5.access$handleTextLinkClick(SubscreenDeviceModelB5.this, "PN");
                                    }
                                };
                                ClickableSpan clickableSpan2 = new ClickableSpan() { // from class: com.android.systemui.statusbar.notification.SubscreenDeviceModelB5$getDisclaimerViewForChina$clickableSpanForTermsAndConditions$1
                                    @Override // android.text.style.ClickableSpan
                                    public final void onClick(View view3) {
                                        SubscreenDeviceModelB5.access$handleTextLinkClick(SubscreenDeviceModelB5.this, "TC");
                                    }
                                };
                                ArrayList arrayList2 = new ArrayList();
                                arrayList2.add(clickableSpan);
                                arrayList2.add(clickableSpan2);
                                int i2 = StringCompanionObject.$r8$clinit;
                                String format = String.format(string, Arrays.copyOf(new Object[]{"", "", "", ""}, 4));
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
                                    int indexOf$default = StringsKt__StringsKt.indexOf$default(spannableString, str, r4, r4, 6);
                                    int length = str.length() + indexOf$default;
                                    if (indexOf$default < 0 || length > spannableString.length()) {
                                        textView.setText(format);
                                    } else {
                                        spannableString.setSpan(arrayList2.get(i3), indexOf$default, length, 33);
                                        spannableString.setSpan(new StyleSpan(style), indexOf$default, length, 33);
                                        spannableString.setSpan(new ForegroundColorSpan(subscreenDeviceModelB5.getMDisplayContext().getColor(R.color.subscreen_notification_smart_reply_disclaimer_policy_text_color)), indexOf$default, length, 33);
                                    }
                                    i3 = i4;
                                    r4 = 0;
                                }
                                textView.setText(spannableString);
                                textView.setMovementMethod(LinkMovementMethod.getInstance());
                                button.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.statusbar.notification.SubscreenDeviceModelB5$getDisclaimerViewForChina$2
                                    @Override // android.view.View.OnClickListener
                                    public final void onClick(View view3) {
                                        SubscreenNotificationDialog subscreenNotificationDialog9 = SubscreenDeviceModelB5.this.mDialog;
                                        if (subscreenNotificationDialog9 != null) {
                                            subscreenNotificationDialog9.dismiss();
                                        }
                                        SubscreenDeviceModelB5.access$handleTurnOnClick(SubscreenDeviceModelB5.this);
                                    }
                                });
                                button2.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.statusbar.notification.SubscreenDeviceModelB5$getDisclaimerViewForChina$3
                                    @Override // android.view.View.OnClickListener
                                    public final void onClick(View view3) {
                                        SubscreenNotificationDialog subscreenNotificationDialog9 = SubscreenDeviceModelB5.this.mDialog;
                                        if (subscreenNotificationDialog9 != null) {
                                            subscreenNotificationDialog9.dismiss();
                                        }
                                    }
                                });
                                SubscreenNotificationDialog subscreenNotificationDialog9 = new SubscreenNotificationDialog(subscreenDeviceModelB5.getMDisplayContext(), inflate, new Runnable() { // from class: com.android.systemui.statusbar.notification.SubscreenDeviceModelB5$checkAndExecuteSuggestResponses$showRunnable$1
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        SubscreenSubRoomNotification subscreenSubRoomNotification = SubscreenDeviceModelB5.this.mSubRoomNotification;
                                        if (subscreenSubRoomNotification != null) {
                                            subscreenSubRoomNotification.mSubscreenMainLayout.setAlpha(0.0f);
                                        }
                                        final SubscreenDeviceModelB5 subscreenDeviceModelB52 = SubscreenDeviceModelB5.this;
                                        View view3 = inflate;
                                        int i5 = SubscreenDeviceModelB5.$r8$clinit;
                                        subscreenDeviceModelB52.getClass();
                                        final ScrollView scrollView = (ScrollView) view3.findViewById(R.id.smart_reply_disclaimer_scroll_container);
                                        final LinearLayout linearLayout = (LinearLayout) view3.findViewById(R.id.smart_reply_disclaimer_container);
                                        final Button button3 = (Button) view3.findViewById(R.id.smart_reply_disclaimer_ok_btn);
                                        int height = scrollView.getHeight();
                                        int height2 = linearLayout.getHeight();
                                        Context context2 = subscreenDeviceModelB52.mContext;
                                        if (height >= height2) {
                                            button3.setText(context2.getString(R.string.subscreen_notification_dialog_ok));
                                            button3.setTag(null);
                                        } else {
                                            button3.setText(context2.getString(R.string.subscreen_notification_dialog_more));
                                            button3.setTag("MORE");
                                            scrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() { // from class: com.android.systemui.statusbar.notification.SubscreenDeviceModelB5$setMoreButtonIfNeed$1$1
                                                @Override // android.view.View.OnScrollChangeListener
                                                public final void onScrollChange(View view4, int i6, int i7, int i8, int i9) {
                                                    if (scrollView.getHeight() + i7 >= linearLayout.getHeight()) {
                                                        button3.setText(subscreenDeviceModelB52.mContext.getString(R.string.subscreen_notification_dialog_ok));
                                                        button3.setTag(null);
                                                        scrollView.setOnScrollChangeListener(null);
                                                    }
                                                }
                                            });
                                            button3.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.statusbar.notification.SubscreenDeviceModelB5$setMoreButtonIfNeed$1$2
                                                @Override // android.view.View.OnClickListener
                                                public final void onClick(View view4) {
                                                    if (Intrinsics.areEqual("MORE", view4.getTag())) {
                                                        scrollView.fullScroll(130);
                                                        return;
                                                    }
                                                    SubscreenNotificationDialog subscreenNotificationDialog10 = subscreenDeviceModelB52.mDialog;
                                                    if (subscreenNotificationDialog10 != null) {
                                                        subscreenNotificationDialog10.dismiss();
                                                    }
                                                    SubscreenDeviceModelB5.access$handleTurnOnClick(subscreenDeviceModelB52);
                                                }
                                            });
                                        }
                                    }
                                }, new Runnable() { // from class: com.android.systemui.statusbar.notification.SubscreenDeviceModelB5$checkAndExecuteSuggestResponses$dismissRunnable$1
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        SubscreenSubRoomNotification subscreenSubRoomNotification = SubscreenDeviceModelB5.this.mSubRoomNotification;
                                        if (subscreenSubRoomNotification != null) {
                                            subscreenSubRoomNotification.mSubscreenMainLayout.setAlpha(1.0f);
                                        }
                                    }
                                });
                                subscreenDeviceModelB5.mDialog = subscreenNotificationDialog9;
                                subscreenNotificationDialog9.show();
                            } else {
                                SubscreenNotificationDialog subscreenNotificationDialog10 = new SubscreenNotificationDialog(subscreenDeviceModelB5.getMDisplayContext(), subscreenDeviceModelB5.getMDisplayContext().getResources().getString(R.string.subscreen_notification_smart_reply_turn_on_content), new Runnable() { // from class: com.android.systemui.statusbar.notification.SubscreenDeviceModelB5$checkAndExecuteSuggestResponses$runnable$2
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        SubscreenDeviceModelB5 subscreenDeviceModelB52 = SubscreenDeviceModelB5.this;
                                        int i5 = SubscreenDeviceModelB5.$r8$clinit;
                                        SettingsHelper settingsHelper = subscreenDeviceModelB52.mSettingsHelper;
                                        settingsHelper.getClass();
                                        if (NotiRune.NOTI_SUBSCREEN_NOTIFICATION_FIFTH) {
                                            Settings.Global.putInt(settingsHelper.mContext.getContentResolver(), "suggestion_responses", 1);
                                            settingsHelper.mItemLists.get("suggestion_responses").mIntValue = 1;
                                        }
                                        SubscreenDeviceModelB5 subscreenDeviceModelB53 = SubscreenDeviceModelB5.this;
                                        subscreenDeviceModelB53.isUnusableAccount = subscreenDeviceModelB53.isSmartReplyUnusable();
                                        SubscreenDeviceModelB5 subscreenDeviceModelB54 = SubscreenDeviceModelB5.this;
                                        subscreenDeviceModelB54.isPossibleAiReply = !subscreenDeviceModelB54.isUnusableAccount;
                                        LinearLayout linearLayout = subscreenDeviceModelB54.suggestResponsesBtn;
                                        if (linearLayout == null) {
                                            linearLayout = null;
                                        }
                                        linearLayout.setVisibility(8);
                                        final SubscreenDeviceModelB5 subscreenDeviceModelB55 = SubscreenDeviceModelB5.this;
                                        ImageView imageView3 = subscreenDeviceModelB55.smartReplyTriggerBtn;
                                        if (imageView3 != null) {
                                            imageView3.post(new Runnable() { // from class: com.android.systemui.statusbar.notification.SubscreenDeviceModelB5$checkAndExecuteSuggestResponses$runnable$2.1
                                                @Override // java.lang.Runnable
                                                public final void run() {
                                                    SubscreenRecyclerView subscreenRecyclerView3;
                                                    SubscreenRecyclerView subscreenRecyclerView4;
                                                    LinearLayout linearLayout2 = SubscreenDeviceModelB5.this.detailButtonContainer;
                                                    if (linearLayout2 == null) {
                                                        linearLayout2 = null;
                                                    }
                                                    float y = linearLayout2.getY();
                                                    SubscreenSubRoomNotification subscreenSubRoomNotification = SubscreenDeviceModelB5.this.mSubRoomNotification;
                                                    int computeVerticalScrollOffset = ((int) y) - ((subscreenSubRoomNotification == null || (subscreenRecyclerView4 = subscreenSubRoomNotification.mNotificationRecyclerView) == null) ? 0 : subscreenRecyclerView4.computeVerticalScrollOffset());
                                                    if (computeVerticalScrollOffset == 0) {
                                                        SubscreenDeviceModelB5.this.showAIReply();
                                                        return;
                                                    }
                                                    SubscreenSubRoomNotification subscreenSubRoomNotification2 = SubscreenDeviceModelB5.this.mSubRoomNotification;
                                                    if (subscreenSubRoomNotification2 == null || (subscreenRecyclerView3 = subscreenSubRoomNotification2.mNotificationRecyclerView) == null) {
                                                        return;
                                                    }
                                                    subscreenRecyclerView3.smoothScrollBy(0, computeVerticalScrollOffset, false);
                                                }
                                            });
                                        }
                                    }
                                });
                                subscreenDeviceModelB5.mDialog = subscreenNotificationDialog10;
                                subscreenNotificationDialog10.show();
                            }
                        }
                    } else {
                        Log.d("S.S.N.", "checkAndExecuteSuggestResponses() : execute galaxy ai confirm");
                        subscreenDeviceModelB5.openPhonePopupForIntelligenceSettings("com.samsung.android.action.INTELLIGENCE_SERVICE_SETTINGS_START_INTENT");
                    }
                    z2 = true;
                } else {
                    z2 = true;
                    subscreenDeviceModelB5.isPossibleAiReply = true;
                    int dimensionPixelSize = subscreenDeviceModelB5.getMDisplayContext().getResources().getDimensionPixelSize(R.dimen.subscreen_noti_detail_icon_button_shadow_margin_b5);
                    LinearLayout linearLayout = SubscreenDeviceModelB5.this.detailButtonContainer;
                    float y = (linearLayout == null ? null : linearLayout).getY() + dimensionPixelSize;
                    SubscreenSubRoomNotification subscreenSubRoomNotification = SubscreenDeviceModelB5.this.mSubRoomNotification;
                    int computeVerticalScrollOffset = (int) (y - ((subscreenSubRoomNotification == null || (subscreenRecyclerView2 = subscreenSubRoomNotification.mNotificationRecyclerView) == null) ? 0 : subscreenRecyclerView2.computeVerticalScrollOffset()));
                    if (computeVerticalScrollOffset == 0) {
                        SubscreenDeviceModelB5.this.showAIReply();
                    } else {
                        SubscreenSubRoomNotification subscreenSubRoomNotification2 = SubscreenDeviceModelB5.this.mSubRoomNotification;
                        if (subscreenSubRoomNotification2 != null && (subscreenRecyclerView = subscreenSubRoomNotification2.mNotificationRecyclerView) != null) {
                            z3 = false;
                            subscreenRecyclerView.smoothScrollBy(0, computeVerticalScrollOffset, false);
                            SystemUIAnalytics.sendEventCDLog("QPN102", "QPNE0217", "type", !((imageView2.getAlpha() > 1.0f ? 1 : (imageView2.getAlpha() == 1.0f ? 0 : -1)) != 0 ? z2 : z3) ? "active" : "dimmed");
                        }
                    }
                }
                z3 = false;
                SystemUIAnalytics.sendEventCDLog("QPN102", "QPNE0217", "type", !((imageView2.getAlpha() > 1.0f ? 1 : (imageView2.getAlpha() == 1.0f ? 0 : -1)) != 0 ? z2 : z3) ? "active" : "dimmed");
            }
        });
        this.smartReplyTriggerBtn = imageView2;
        this.smartReplyErrorMessageView = (TextView) view.findViewById(R.id.smart_reply_error_message);
        this.suggestResponsesBtn = (LinearLayout) view.findViewById(R.id.smart_reply_suggest_responses_layout);
        ImageView imageView3 = (ImageView) view.findViewById(R.id.smart_reply_ai_disclaimer);
        imageView3.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.statusbar.notification.SubscreenDeviceModelB5$initDetailAdapterItemViewHolder$4$1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                SubscreenNotificationDialog subscreenNotificationDialog;
                SubscreenNotificationDialog subscreenNotificationDialog2 = SubscreenDeviceModelB5.this.mDialog;
                if (subscreenNotificationDialog2 != null) {
                    if ((subscreenNotificationDialog2.mDialog.isShowing()) && (subscreenNotificationDialog = SubscreenDeviceModelB5.this.mDialog) != null) {
                        subscreenNotificationDialog.dismiss();
                    }
                    SubscreenDeviceModelB5.this.mDialog = null;
                }
                String string = context.getResources().getString(R.string.subscreen_notification_smart_reply_ai_disclaimer_dialog_content);
                String substringBefore$default = StringsKt__StringsKt.substringBefore$default(StringsKt__StringsKt.substringAfter$default(string, "%1$s"), "%2$s");
                final SubscreenDeviceModelB5 subscreenDeviceModelB5 = SubscreenDeviceModelB5.this;
                ClickableSpan clickableSpan = new ClickableSpan() { // from class: com.android.systemui.statusbar.notification.SubscreenDeviceModelB5$initDetailAdapterItemViewHolder$4$1$clickableSpan$1
                    @Override // android.text.style.ClickableSpan
                    public final void onClick(View view3) {
                        SubRoom.StateChangeListener stateChangeListener;
                        SubscreenNotificationDialog subscreenNotificationDialog3 = SubscreenDeviceModelB5.this.mDialog;
                        if (subscreenNotificationDialog3 != null) {
                            subscreenNotificationDialog3.dismiss();
                        }
                        Intent intent = new Intent();
                        SubscreenDeviceModelB5 subscreenDeviceModelB52 = SubscreenDeviceModelB5.this;
                        intent.setAction("android.intent.action.VIEW");
                        Context context2 = subscreenDeviceModelB52.mContext;
                        intent.setData(Uri.parse("https://policies.account.samsung.com/terms?appKey=j5p7ll8g33" + "&applicationRegion=".concat(subscreenDeviceModelB52.getIsoCountryCode()) + KeyAttributes$$ExternalSyntheticOutline0.m21m("&language=", Locale.getDefault().getLanguage()) + "&region=".concat(subscreenDeviceModelB52.getIsoCountryCode()) + "&type=TC"));
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
                int indexOf$default = StringsKt__StringsKt.indexOf$default(spannableString, substringBefore$default, 0, false, 6);
                int length = substringBefore$default.length() + indexOf$default;
                spannableString.setSpan(new StyleSpan(1), indexOf$default, length, 33);
                spannableString.setSpan(clickableSpan, indexOf$default, length, 33);
                View inflate = LayoutInflater.from(new ContextThemeWrapper(SubscreenDeviceModelB5.this.getMDisplayContext(), 2132018527)).inflate(R.layout.subscreen_notification_smart_reply_disclaimer_info, (ViewGroup) null);
                TextView textView = (TextView) inflate.findViewById(R.id.smart_reply_ai_disclaimer_text);
                textView.setText(spannableString);
                textView.setMovementMethod(LinkMovementMethod.getInstance());
                SubscreenDeviceModelB5 subscreenDeviceModelB52 = SubscreenDeviceModelB5.this;
                subscreenDeviceModelB52.mDialog = new SubscreenNotificationDialog(subscreenDeviceModelB52.getMDisplayContext(), inflate);
                SubscreenNotificationDialog subscreenNotificationDialog3 = SubscreenDeviceModelB5.this.mDialog;
                if (subscreenNotificationDialog3 != null) {
                    subscreenNotificationDialog3.show();
                }
                Log.d("S.S.N.", "show smart reply ai disclaimer dialog");
            }
        });
        this.aiDisclaimerBtn = imageView3;
        this.smartReplyAiLogoText = (TextView) view.findViewById(R.id.smart_reply_ai_logo);
        SettingsHelper settingsHelper = this.mSettingsHelper;
        settingsHelper.getClass();
        if (NotiRune.NOTI_SUBSCREEN_NOTIFICATION_FIFTH && settingsHelper.mItemLists.get("ai_info_confirmed").getIntValue() != 0) {
            z = true;
        }
        this.isAiInfoConfirmed = z;
        this.isSuggestResponsesEnabled = settingsHelper.isSuggestResponsesEnabled();
        this.isUnusableAccount = isSmartReplyUnusable();
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final void initDetailAdapterTextViewHolder(SubscreenNotificationDetailAdapter.TextViewHolder textViewHolder) {
        View view = textViewHolder.itemView;
        this.openAppButtonText = (TextView) view.findViewById(R.id.app_open_button_text);
        this.clearButtonText = (TextView) view.findViewById(R.id.clear_button_text);
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
        View inflate = LayoutInflater.from(getMDisplayContext()).inflate(R.layout.subscreen_notification_adapter_header_b5, (ViewGroup) null);
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
        boolean z4;
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
        boolean z5 = (subscreenNotificationInfo != null && subscreenNotificationInfo.mIsMessagingStyle) && subscreenNotificationInfo.mRemoteinput;
        if (textView != null) {
            textView.setText((isShownDetail() && z5) ? this.titleText : subscreenNotificationInfo != null ? subscreenNotificationInfo.mAppName : null);
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
                if (imageView != null) {
                    if ((subscreenNotificationInfo == null || subscreenNotificationInfo.useSmallIcon()) ? false : true) {
                        imageView.setImageDrawable(subscreenNotificationInfo != null ? subscreenNotificationInfo.mAppIcon : null);
                        imageView.setVisibility(0);
                    }
                }
                if (imageView2 != null) {
                    imageView2.setVisibility(0);
                }
                if (imageView2 != null) {
                    imageView2.setImageDrawable(subscreenNotificationInfo != null ? subscreenNotificationInfo.mIcon : null);
                    updateSmallIconSquircleBg(imageView2, true, false);
                    updateIconColor(imageView2, (subscreenNotificationInfo == null || (expandableNotificationRow2 = subscreenNotificationInfo.mRow) == null) ? null : expandableNotificationRow2.mEntry);
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
            if (imageView4 == null) {
                z3 = false;
            } else {
                z3 = false;
                imageView4.setVisibility(0);
            }
        } else {
            z3 = false;
            if (imageView4 != null) {
                imageView4.setVisibility(8);
            }
        }
        if (z2) {
            if ((subscreenNotificationInfo == null || (expandableNotificationRow3 = subscreenNotificationInfo.mRow) == null || (notificationEntry = expandableNotificationRow3.mEntry) == null || (channel = notificationEntry.getChannel()) == null || !channel.isImportantConversation()) ? z3 : true) {
                z4 = true;
                updateImportBadgeIconRing(this.mHeaderViewLayout, z4);
                SubscreenDeviceModelParent.updateKnoxIcon(imageView5, subscreenNotificationInfo);
                SubscreenDeviceModelParent.updateTwoPhoneIcon(imageView7, subscreenNotificationInfo);
            }
        }
        z4 = z3;
        updateImportBadgeIconRing(this.mHeaderViewLayout, z4);
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
        if (this.mSettingsHelper.mItemLists.get("edge_lighting").getIntValue() == 1) {
            return !this.mFullScreenIntentEntries.containsKey(notificationEntry != null ? notificationEntry.mKey : null);
        }
        return false;
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final boolean isDismissiblePopup() {
        LinkedHashMap linkedHashMap = this.mFullScreenIntentEntries;
        return linkedHashMap.isEmpty() || (!linkedHashMap.isEmpty() && useTopPresentation());
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final boolean isKeyguardStats() {
        KeyguardStateController keyguardStateController = this.mKeyguardStateController;
        if (keyguardStateController != null && ((KeyguardStateControllerImpl) keyguardStateController).mSecure) {
            if ((keyguardStateController == null || keyguardStateController.isUnlocked()) ? false : true) {
                return true;
            }
        }
        KeyguardStateController keyguardStateController2 = this.mKeyguardStateController;
        if ((keyguardStateController2 == null || ((KeyguardStateControllerImpl) keyguardStateController2).mSecure) ? false : true) {
            if (keyguardStateController2 != null && ((KeyguardStateControllerImpl) keyguardStateController2).mShowing) {
                return true;
            }
        }
        return false;
    }

    public final boolean isKeyguardUnlockShowing() {
        KeyguardStateController keyguardStateController = this.mKeyguardStateController;
        if (keyguardStateController != null && ((KeyguardStateControllerImpl) keyguardStateController).mSecure) {
            if (keyguardStateController != null && keyguardStateController.isUnlocked()) {
                KeyguardStateController keyguardStateController2 = this.mKeyguardStateController;
                if (keyguardStateController2 != null && ((KeyguardStateControllerImpl) keyguardStateController2).mShowing) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final boolean isKnoxSecurity(NotificationEntry notificationEntry) {
        NotificationLockscreenUserManager notificationLockscreenUserManager = (NotificationLockscreenUserManager) Dependency.get(NotificationLockscreenUserManager.class);
        return ((NotificationLockscreenUserManagerImpl) notificationLockscreenUserManager).mUsersWithSeparateWorkChallenge.get(notificationEntry.mSbn.getUserId(), false);
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final boolean isLaunchApp(NotificationEntry notificationEntry) {
        boolean z = notificationEntry.mSbn.getPackageName() != null && ActivityTaskManager.getService().isPackageEnabledForCoverLauncher(notificationEntry.mSbn.getPackageName(), notificationEntry.mSbn.getUser().getIdentifier());
        if (notificationEntry.mSbn.getNotification().contentIntent != null && (z || isCallNotification(notificationEntry))) {
            SettingsHelper settingsHelper = this.mSettingsHelper;
            if (!settingsHelper.isUltraPowerSavingMode() && !settingsHelper.isEmergencyMode() && !this.mKeyguardUpdateMonitor.isKidsModeRunning()) {
                return true;
            }
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
        int mainHeaderViewHeight = ((720 - getMainHeaderViewHeight()) - getMDisplayContext().getResources().getDimensionPixelSize(R.dimen.subscreen_noti_cut_out_size_b5)) + valueOf.intValue();
        SubscreenNotificationDetailAdapter.ItemViewHolder itemViewHolder = this.detailViewHolder;
        if (itemViewHolder != null) {
            LinearLayout linearLayout = itemViewHolder.mSmartReplyLayout;
            if (!(linearLayout != null && linearLayout.getVisibility() == 0)) {
                linearLayout = itemViewHolder.mReplylayout;
            }
            if (linearLayout != null) {
                float y = linearLayout.getY();
                LinearLayout linearLayout2 = itemViewHolder.mReplyContainer;
                r3 = y + (linearLayout2 != null ? linearLayout2.getY() : 0.0f);
            }
        }
        return ((float) mainHeaderViewHeight) > r3;
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final boolean isSamsungAccountLoggedIn() {
        return this.isSALoggedIn;
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final boolean isShowNotificationAppIcon() {
        return this.mSettingsHelper.isShowNotificationAppIconEnabled();
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
            ArrayList arrayList = (ArrayList) this.onDeviceLanguageList;
            arrayList.clear();
            String replace$default = StringsKt__StringsJVMKt.replace$default(StringsKt__StringsJVMKt.replace$default(new Regex("\\s+").replace("{ \"list\" : " + getOnDeviceMetaData() + "}"), ",}", "}"), ",]", "]");
            if (replace$default.length() > 0) {
                new JsonParser();
                JsonElement jsonElement = JsonParser.parseString(replace$default).getAsJsonObject().get("list");
                if (jsonElement != null) {
                    if (!(jsonElement instanceof JsonArray)) {
                        throw new IllegalStateException("Not a JSON Array: " + jsonElement);
                    }
                    Iterator it = ((JsonArray) jsonElement).iterator();
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
                            arrayList.add(llmLanguage);
                        }
                    }
                }
            }
            Log.d("S.S.N.", "loadOnDeviceMetaData : " + arrayList.size());
        } catch (Exception e) {
            AbsAdapter$1$$ExternalSyntheticOutline0.m39m("loadOnDeviceMetaData e: ", e, "S.S.N.");
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:84:0x015d, code lost:
    
        if ((r6 != null ? r6.mPrevLastHistoryView : null) == null) goto L95;
     */
    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void moveDetailAdapterContentScroll(View view, boolean z, boolean z2, boolean z3) {
        LinearLayout linearLayout;
        SubscreenRecyclerView subscreenRecyclerView;
        SubscreenRecyclerView subscreenRecyclerView2;
        int height;
        String str;
        int i;
        SubscreenNotificationDetailAdapter subscreenNotificationDetailAdapter;
        SubscreenRecyclerView subscreenRecyclerView3;
        SubscreenNotificationDetailAdapter subscreenNotificationDetailAdapter2;
        SubscreenRecyclerView subscreenRecyclerView4;
        SubscreenRecyclerView subscreenRecyclerView5;
        SubscreenRecyclerView subscreenRecyclerView6;
        SubscreenRecyclerView subscreenRecyclerView7;
        SubscreenRecyclerView subscreenRecyclerView8;
        SubscreenNotificationDetailAdapter subscreenNotificationDetailAdapter3;
        SubscreenNotificationDetailAdapter subscreenNotificationDetailAdapter4;
        SubscreenSubRoomNotification subscreenSubRoomNotification = this.mSubRoomNotification;
        if (((subscreenSubRoomNotification == null || (subscreenNotificationDetailAdapter4 = subscreenSubRoomNotification.mNotificationDetailAdapter) == null) ? null : subscreenNotificationDetailAdapter4.mSelectNotificationInfo) == null) {
            Log.d("S.S.N.", "moveDetailAdapterContentScroll B5 - return - value is null  View : " + view + ", selectNotificationInfo : " + ((subscreenSubRoomNotification == null || (subscreenNotificationDetailAdapter3 = subscreenSubRoomNotification.mNotificationDetailAdapter) == null) ? null : subscreenNotificationDetailAdapter3.mSelectNotificationInfo));
            return;
        }
        boolean z4 = this.mIsContentScroll;
        if (!z4) {
            KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m62m("moveDetailAdapterContentScroll B5 - return mIsContentScroll : ", z4, "S.S.N.");
            return;
        }
        this.mIsContentScroll = false;
        if (((subscreenSubRoomNotification == null || (subscreenRecyclerView8 = subscreenSubRoomNotification.mNotificationRecyclerView) == null) ? null : subscreenRecyclerView8.getChildViewHolder(view)) instanceof SubscreenParentDetailItemViewHolder) {
            SubscreenSubRoomNotification subscreenSubRoomNotification2 = this.mSubRoomNotification;
            SubscreenParentDetailItemViewHolder subscreenParentDetailItemViewHolder = (SubscreenParentDetailItemViewHolder) ((subscreenSubRoomNotification2 == null || (subscreenRecyclerView7 = subscreenSubRoomNotification2.mNotificationRecyclerView) == null) ? null : subscreenRecyclerView7.getChildViewHolder(view));
            if (!subscreenParentDetailItemViewHolder.mInfo.mIsMessagingStyle || (linearLayout = subscreenParentDetailItemViewHolder.mContentLayout) == null) {
                return;
            }
            int childCount = linearLayout.getChildCount();
            if (childCount == 0) {
                Log.d("S.S.N.", "moveDetailAdapterContentScroll B5 - size is zero");
                return;
            }
            int dimensionPixelSize = getMDisplayContext().getResources().getDimensionPixelSize(R.dimen.subscreen_noti_cut_out_size_b5);
            int mainHeaderViewHeight = getMainHeaderViewHeight();
            int i2 = childCount - 1;
            View view2 = ViewGroupKt.get(linearLayout, i2);
            float y = view2.getY() + mainHeaderViewHeight + view2.getHeight();
            boolean z5 = y >= ((float) (720 - dimensionPixelSize));
            LinearLayout linearLayout2 = subscreenParentDetailItemViewHolder.mBodyLayout;
            ListPopupWindow$$ExternalSyntheticOutline0.m10m("moveDetailAdapterContentScroll B5 - cuttent body height : ", linearLayout2.getHeight(), "S.S.N.");
            LinearLayout linearLayout3 = subscreenParentDetailItemViewHolder.mReplyContainer;
            if (z) {
                ((TextView) view2.findViewById(R.id.detail_content_text)).getText();
                float y2 = linearLayout3.getY() - linearLayout2.getMinimumHeight();
                SubscreenSubRoomNotification subscreenSubRoomNotification3 = this.mSubRoomNotification;
                float computeVerticalScrollOffset = y2 - ((subscreenSubRoomNotification3 == null || (subscreenRecyclerView6 = subscreenSubRoomNotification3.mNotificationRecyclerView) == null) ? 0 : subscreenRecyclerView6.computeVerticalScrollOffset());
                SubscreenSubRoomNotification subscreenSubRoomNotification4 = this.mSubRoomNotification;
                if (subscreenSubRoomNotification4 != null && (subscreenRecyclerView5 = subscreenSubRoomNotification4.mNotificationRecyclerView) != null) {
                    subscreenRecyclerView5.smoothScrollBy(0, (int) computeVerticalScrollOffset, false);
                }
                Log.d("S.S.N.", "moveDetailAdapterContentScroll B5 - isQuickReply scroll : " + computeVerticalScrollOffset);
                return;
            }
            if (!z2) {
                Integer num = null;
                Log.d("S.S.N.", "moveDetailAdapterContentScroll B5 - totalY : " + y);
                if (!z5 && !z3) {
                    ImageView imageView = this.smartReplyTriggerBtn;
                    if (!(imageView != null && imageView.getVisibility() == 0)) {
                        return;
                    }
                }
                float y3 = linearLayout3.getY() - linearLayout2.getMinimumHeight();
                if (z3) {
                    SubscreenSubRoomNotification subscreenSubRoomNotification5 = this.mSubRoomNotification;
                    if (subscreenSubRoomNotification5 != null && (subscreenRecyclerView2 = subscreenSubRoomNotification5.mNotificationRecyclerView) != null) {
                        num = Integer.valueOf(subscreenRecyclerView2.computeVerticalScrollOffset());
                    }
                    Intrinsics.checkNotNull(num);
                    y3 -= num.intValue();
                    Log.d("S.S.N.", "moveDetailAdapterContentScroll B5 - updated : " + y3);
                }
                SubscreenSubRoomNotification subscreenSubRoomNotification6 = this.mSubRoomNotification;
                if (subscreenSubRoomNotification6 == null || (subscreenRecyclerView = subscreenSubRoomNotification6.mNotificationRecyclerView) == null) {
                    return;
                }
                subscreenRecyclerView.smoothScrollBy(0, (int) y3, false);
                return;
            }
            SubscreenSubRoomNotification subscreenSubRoomNotification7 = this.mSubRoomNotification;
            Integer valueOf = (subscreenSubRoomNotification7 == null || (subscreenRecyclerView4 = subscreenSubRoomNotification7.mNotificationRecyclerView) == null) ? null : Integer.valueOf(subscreenRecyclerView4.computeVerticalScrollOffset());
            Intrinsics.checkNotNull(valueOf);
            int intValue = valueOf.intValue();
            int mainHeaderViewHeight2 = ((720 - getMainHeaderViewHeight()) - dimensionPixelSize) + intValue;
            SubscreenSubRoomNotification subscreenSubRoomNotification8 = this.mSubRoomNotification;
            SubscreenNotificationDetailAdapter.ScrollInfo scrollInfo = (subscreenSubRoomNotification8 == null || (subscreenNotificationDetailAdapter2 = subscreenSubRoomNotification8.mNotificationDetailAdapter) == null) ? null : subscreenNotificationDetailAdapter2.mScrollInfo;
            View view3 = scrollInfo != null ? scrollInfo.mPrevFirstHistoryView : null;
            if (view3 == null) {
                view3 = ViewGroupKt.get(linearLayout, 0);
            }
            View view4 = scrollInfo != null ? scrollInfo.mPrevLastHistoryView : null;
            if (view4 == null) {
                view4 = ViewGroupKt.get(linearLayout, i2);
            }
            Integer valueOf2 = scrollInfo != null ? Integer.valueOf(scrollInfo.mPrevHistoryCount) : null;
            if ((scrollInfo != null ? scrollInfo.mPrevFirstHistoryView : null) != null) {
            }
            Log.d("S.S.N.", "moveDetailAdapterContentScroll B5 - prevItem is null,scrollInfo?.prevFirstHistoryView :" + (scrollInfo != null ? scrollInfo.mPrevFirstHistoryView : null) + ", scrollInfo?.prevLastHistoryView : " + (scrollInfo != null ? scrollInfo.mPrevLastHistoryView : null));
            float y4 = view4.getY() + (view4.getHeight() / 3);
            Integer valueOf3 = scrollInfo != null ? Integer.valueOf(scrollInfo.mPrevFirstHistoryViewBottomMargin) : null;
            int i3 = childCount > 1 ? ((LinearLayout.LayoutParams) ViewGroupKt.get(linearLayout, childCount - 2).getLayoutParams()).bottomMargin : 0;
            float f = mainHeaderViewHeight2;
            if (f <= y4 || intValue >= y4) {
                if (f < y4) {
                    Intrinsics.checkNotNull(valueOf2);
                    if (valueOf2.intValue() >= childCount) {
                        int height2 = view3.getHeight();
                        Intrinsics.checkNotNull(valueOf3);
                        height = -(valueOf3.intValue() + height2);
                        str = "postion top";
                    }
                    str = "";
                } else {
                    if (intValue > y4) {
                        Intrinsics.checkNotNull(valueOf2);
                        if (valueOf2.intValue() >= childCount) {
                            if (view3.getHeight() > view2.getHeight()) {
                                int height3 = view3.getHeight();
                                Intrinsics.checkNotNull(valueOf3);
                                height = -((valueOf3.intValue() + height3) - (view2.getHeight() + i3));
                                str = "postion bottom History Max - prevFirstView > lastitem";
                            } else {
                                int height4 = view2.getHeight() + i3;
                                int height5 = view3.getHeight();
                                Intrinsics.checkNotNull(valueOf3);
                                height = height4 - (valueOf3.intValue() + height5);
                                str = "postion bottom History Max - prevFirstView <= lastitem";
                            }
                        } else if (getMDisplayContext().getResources().getDimensionPixelSize(R.dimen.subscreen_noti_detail_conversation_body_min_height_b5) + getMDisplayContext().getResources().getDimensionPixelSize(R.dimen.subscreen_noti_detail_button_container_translateY_b5) <= linearLayout2.getHeight()) {
                            height = view2.getHeight() + i3;
                            str = "postion bottom History Not Max";
                        }
                    }
                    str = "";
                }
                height = 0;
            } else {
                Intrinsics.checkNotNull(valueOf2);
                if (valueOf2.intValue() < childCount) {
                    str = "latest normal";
                    if (z5) {
                        height = (int) (view2.getY() - view4.getY());
                    }
                    height = 0;
                } else if (view3.getHeight() > view2.getHeight()) {
                    int height6 = view3.getHeight();
                    Intrinsics.checkNotNull(valueOf3);
                    height = -((valueOf3.intValue() + height6) - (view2.getHeight() + i3));
                    str = "latest prevFirstView?.height!! > lastItem.height";
                } else {
                    int height7 = view2.getHeight() + i3;
                    int height8 = view3.getHeight();
                    Intrinsics.checkNotNull(valueOf3);
                    height = height7 - (valueOf3.intValue() + height8);
                    str = "latest prevFirstView?.height!! <= lastItem.height";
                }
            }
            SubscreenSubRoomNotification subscreenSubRoomNotification9 = this.mSubRoomNotification;
            if (subscreenSubRoomNotification9 == null || (subscreenRecyclerView3 = subscreenSubRoomNotification9.mNotificationRecyclerView) == null) {
                i = 0;
            } else {
                i = 0;
                subscreenRecyclerView3.scrollBy(0, height);
            }
            SubscreenSubRoomNotification subscreenSubRoomNotification10 = this.mSubRoomNotification;
            if (subscreenSubRoomNotification10 != null && (subscreenNotificationDetailAdapter = subscreenSubRoomNotification10.mNotificationDetailAdapter) != null) {
                SubscreenNotificationDetailAdapter.ScrollInfo scrollInfo2 = subscreenNotificationDetailAdapter.mScrollInfo;
                scrollInfo2.mPrevFirstHistoryView = null;
                scrollInfo2.mPrevLastHistoryView = null;
                scrollInfo2.mPrevHistoryCount = i;
                scrollInfo2.mPrevFirstHistoryViewBottomMargin = i;
                scrollInfo2.mPrevBodyLayoutHeght = i;
            }
            Log.d("S.S.N.", "moveDetailAdapterContentScroll B5 - isReceive scroll : " + str + ", receiveMoveScroll : " + height);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:85:0x00a4, code lost:
    
        if ((com.android.systemui.NotiRune.NOTI_SUBSCREEN_NOTIFICATION_FIFTH && r12 && r1.mItemLists.get("suggestion_responses_used").getIntValue() != 0) != false) goto L51;
     */
    /* JADX WARN: Type inference failed for: r12v47, types: [T, java.lang.String] */
    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelCommon, com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void onBindDetailAdapterItemViewHolder(SubscreenNotificationDetailAdapter subscreenNotificationDetailAdapter, SubscreenNotificationDetailAdapter.ItemViewHolder itemViewHolder) {
        int dimensionPixelSize;
        int size;
        super.onBindDetailAdapterItemViewHolder(subscreenNotificationDetailAdapter, itemViewHolder);
        View view = itemViewHolder.itemView;
        this.progressLayout = (LinearLayout) view.findViewById(R.id.smart_reply_progress_layout);
        this.progressingVi = (LottieAnimationView) view.findViewById(R.id.smart_reply_progressing_lottie);
        this.detailViewHolder = itemViewHolder;
        TextView textView = this.smartReplyErrorMessageView;
        if (textView != null) {
            textView.setAlpha(0.0f);
        }
        TextView textView2 = this.smartReplyErrorMessageView;
        if (textView2 != null) {
            textView2.setVisibility(8);
        }
        ImageView imageView = this.smartReplyTriggerBtn;
        if (imageView != null) {
            imageView.setVisibility(8);
        }
        TextView textView3 = this.smartReplyAiLogoText;
        if (textView3 != null) {
            textView3.setVisibility(8);
        }
        SubscreenNotificationInfo subscreenNotificationInfo = itemViewHolder.mInfo;
        boolean z = true;
        boolean z2 = subscreenNotificationInfo.mIsMessagingStyle && subscreenNotificationInfo.mRemoteinput;
        TextView textView4 = itemViewHolder.mTitle;
        LinearLayout linearLayout = itemViewHolder.mReplylayout;
        LinearLayout linearLayout2 = itemViewHolder.mBodyLayout;
        if (!z2 || subscreenNotificationDetailAdapter.mItemPostionInGroup > 8) {
            ImageView imageView2 = this.keyboardReplyButton;
            if (imageView2 == null) {
                imageView2 = null;
            }
            imageView2.setVisibility(8);
            if (itemViewHolder.mInfo.mRemoteinput) {
                ImageView imageView3 = this.keyboardReplyButton;
                if (imageView3 == null) {
                    imageView3 = null;
                }
                imageView3.setVisibility(0);
            }
            linearLayout.setVisibility(8);
            setEditButton(itemViewHolder);
            updateVisibilityForSmartReplyLayout(8);
            LinearLayout linearLayout3 = this.progressLayout;
            if (linearLayout3 != null) {
                linearLayout3.setVisibility(8);
            }
            LinearLayout linearLayout4 = this.suggestResponsesBtn;
            if (linearLayout4 == null) {
                linearLayout4 = null;
            }
            linearLayout4.setVisibility(8);
            CharSequence text = textView4.getText();
            if (text != null && text.length() != 0) {
                z = false;
            }
            if (z || StringsKt__StringsJVMKt.isBlank(textView4.getText())) {
                textView4.setVisibility(8);
                dimensionPixelSize = getMDisplayContext().getResources().getDimensionPixelSize(R.dimen.subscreen_noti_detail_content_top_margin_b5);
            } else {
                textView4.setVisibility(0);
                dimensionPixelSize = 0;
            }
            linearLayout2.setBackgroundResource(R.drawable.subscreen_notification_detail_type_item_background_b5);
            linearLayout2.setPadding(0, dimensionPixelSize, 0, 0);
            LinearLayout linearLayout5 = this.detailButtonContainer;
            if (linearLayout5 == null) {
                linearLayout5 = null;
            }
            linearLayout5.setPadding(0, 0, 0, getMDisplayContext().getResources().getDimensionPixelSize(R.dimen.subscreen_noti_cutout_area_height_b5));
            linearLayout2.setMinimumHeight(getMDisplayContext().getResources().getDimensionPixelSize(R.dimen.subscreen_noti_detail_body_min_height_b5));
        } else {
            if (NotiRune.NOTI_SUBSCREEN_SUPPORT_SMART_REPLY_AI || NotiRune.NOTI_SUBSCREEN_SUPPORT_SMART_REPLY_AI_FOR_CHINA) {
                this.isPossibleAiReply = !this.isUnusableAccount;
                if (!this.isSuggestResponsesEnabled) {
                    boolean z3 = NotiRune.NOTI_SUBSCREEN_SUPPORT_SMART_REPLY_AI_FOR_CHINA;
                    if (z3) {
                        SettingsHelper settingsHelper = this.mSettingsHelper;
                        settingsHelper.getClass();
                    }
                    if (this.isRDUMode) {
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
                }
                ImageView imageView6 = this.smartReplyTriggerBtn;
                if (imageView6 != null) {
                    imageView6.setVisibility(0);
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
                                            StringBuilder m69m = KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0.m69m("onBindDetailAdapterItemViewHolder - successful : ", isSuccessful, ", isComplete : ", isComplete, ", result : ");
                                            m69m.append(result);
                                            m69m.append(", isSupport : ");
                                            m69m.append(access$isSupportableLanguage);
                                            Log.d("S.S.N.", m69m.toString());
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
            } else {
                Log.d("S.S.N.", " NOT SUPPORT SMART REPLY AI");
                LinearLayout linearLayout6 = this.progressLayout;
                if (linearLayout6 != null) {
                    linearLayout6.setVisibility(8);
                }
                updateVisibilityForSmartReplyLayout(8);
            }
            ImageView imageView7 = this.keyboardReplyButton;
            if (imageView7 == null) {
                imageView7 = null;
            }
            imageView7.setVisibility(0);
            itemViewHolder.inflateReplyWord();
            linearLayout.setVisibility(0);
            setEditButton(itemViewHolder);
            ImageView imageView8 = (ImageView) linearLayout.findViewById(R.id.subscreen_detail_word_line_top);
            if (imageView8 != null) {
                imageView8.setVisibility(8);
            }
            textView4.setVisibility(8);
            linearLayout2.setBackgroundColor(0);
            linearLayout2.setPadding(0, getMDisplayContext().getResources().getDimensionPixelSize(R.dimen.subscreen_noti_detail_conversation_top_padding_b5), 0, 0);
            linearLayout2.setMinimumHeight(getMDisplayContext().getResources().getDimensionPixelSize(R.dimen.subscreen_noti_detail_conversation_body_min_height_b5));
            LinearLayout linearLayout7 = this.detailButtonContainer;
            if (linearLayout7 == null) {
                linearLayout7 = null;
            }
            linearLayout7.setPadding(0, 0, 0, 0);
            LinearLayout linearLayout8 = this.detailButtonContainer;
            if (linearLayout8 == null) {
                linearLayout8 = null;
            }
            linearLayout8.measure(0, 0);
            int dimensionPixelSize2 = getMDisplayContext().getResources().getDimensionPixelSize(R.dimen.subscreen_noti_detail_icon_button_bg_size_b5);
            int dimensionPixelSize3 = getMDisplayContext().getResources().getDimensionPixelSize(R.dimen.subscreen_noti_detail_icon_button_shadow_margin_b5);
            LinearLayout linearLayout9 = this.detailButtonContainer;
            if (linearLayout9 == null) {
                linearLayout9 = null;
            }
            int measuredHeight = (linearLayout9.getMeasuredHeight() - (dimensionPixelSize2 / 2)) - dimensionPixelSize3;
            int dimensionPixelSize4 = getMDisplayContext().getResources().getDimensionPixelSize(R.dimen.subscreen_noti_detail_reply_container_top_margin_b5);
            LinearLayout linearLayout10 = itemViewHolder.mReplyContainer;
            if (linearLayout10 != null) {
                linearLayout10.setPadding(linearLayout10.getPaddingLeft(), dimensionPixelSize4 + measuredHeight, linearLayout10.getPaddingRight(), linearLayout10.getPaddingBottom());
            }
            int dimensionPixelSize5 = getMDisplayContext().getResources().getDimensionPixelSize(R.dimen.subscreen_noti_detail_ai_disclaimer_button_margin_top_b5);
            LinearLayout linearLayout11 = (LinearLayout) view.findViewById(R.id.smart_reply_ai_disclaimer_layout);
            if (linearLayout11 != null) {
                linearLayout11.setPadding(linearLayout11.getPaddingLeft(), measuredHeight + dimensionPixelSize5, linearLayout11.getPaddingRight(), linearLayout11.getPaddingBottom());
            }
        }
        View view2 = itemViewHolder.mOpenAppButton;
        view2.setVisibility(8);
        TextView textView5 = this.callBackButtonText;
        if (textView5 == null) {
            textView5 = null;
        }
        textView5.setVisibility(itemViewHolder.mCallBackButton.getVisibility());
        TextView textView6 = this.replyButtonText;
        if (textView6 == null) {
            textView6 = null;
        }
        ImageView imageView9 = this.keyboardReplyButton;
        if (imageView9 == null) {
            imageView9 = null;
        }
        textView6.setVisibility(imageView9.getVisibility());
        TextView textView7 = this.openAppButtonText;
        if (textView7 == null) {
            textView7 = null;
        }
        textView7.setVisibility(view2.getVisibility());
        TextView textView8 = this.clearButtonText;
        (textView8 != null ? textView8 : null).setVisibility(itemViewHolder.mClearButton.getVisibility());
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
        view.setVisibility(8);
        TextView textView = this.openAppButtonText;
        if (textView == null) {
            textView = null;
        }
        textView.setVisibility(view.getVisibility());
        TextView textView2 = this.clearButtonText;
        (textView2 != null ? textView2 : null).setVisibility(textViewHolder.mClearButton.getVisibility());
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final void onStateChangedInDeviceStateCallback(int i) {
        SubscreenSubRoomNotification subscreenSubRoomNotification;
        SubscreenNotificationDetailAdapter subscreenNotificationDetailAdapter;
        this.mIsFlexMode = i == 1;
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
            AbsAdapter$1$$ExternalSyntheticOutline0.m39m("exception on openPhonePopupForIntelligenceSettings: ", e, "S.S.N.");
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
        SystemUIAnalytics.sendEventLog("QPN102", "QPNE0214");
        z = true;
        this.mSrPromptProcessor.setNotificationKey(null);
        Log.d("S.S.N.", "performBackClick() - ret: " + z);
        return z;
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final void registerAODTspReceiver() {
        this.mContext.registerReceiver(this.aodTspUpdateReceiver, AppCompatDelegateImpl$$ExternalSyntheticOutline0.m5m("com.samsung.android.app.aodservice.intent.action.CHANGE_AOD_MODE"), "com.samsung.android.app.aodservice.permission.BROADCAST_RECEIVER", null);
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
            ListPopupWindow$$ExternalSyntheticOutline0.m10m("replyActivityFinished() - header visibility: ", i, "S.S.N.");
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
        ListPopupWindow$$ExternalSyntheticOutline0.m10m("runSmartReplyUncompletedOperation() - ", this.mSmartReplyResult, "S.S.N.");
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
        if ((linearLayout2 != null && linearLayout2.getVisibility() == 0) && (linearLayout = this.progressLayout) != null) {
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
        long j2 = ((SubscreenNotificationInfo.MessagingStyleInfo) subscreenNotificationInfo.mMessageingStyleInfoArray.get(r8.size() - 1)).mPostedTime;
        long j3 = ((SubscreenNotificationInfo.MessagingStyleInfo) subscreenNotificationInfo.mMessageingStyleInfoArray.get(r7.size() - 1)).mTimeStamp;
        if (j2 <= 0) {
            j2 = j3;
        }
        dateTimeView.setTime(j2);
        dateTimeView.setVisibility(0);
    }

    /* JADX WARN: Removed duplicated region for block: B:42:0x0120  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x016d  */
    /* JADX WARN: Removed duplicated region for block: B:69:0x0248  */
    /* JADX WARN: Removed duplicated region for block: B:75:0x023b  */
    /* JADX WARN: Removed duplicated region for block: B:78:0x0174  */
    /* JADX WARN: Removed duplicated region for block: B:91:0x0159  */
    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void setContentViewItem(Context context, SubscreenNotificationDetailAdapter.ItemViewHolder itemViewHolder) {
        boolean z;
        Drawable drawable;
        int i;
        int i2;
        LinearLayout.LayoutParams layoutParams;
        String str;
        long j;
        LinearLayout.LayoutParams layoutParams2;
        SubscreenNotificationInfo subscreenNotificationInfo = itemViewHolder.mInfo;
        int i3 = 0;
        boolean z2 = subscreenNotificationInfo.mIsMessagingStyle && subscreenNotificationInfo.mRemoteinput;
        View view = this.mHeaderViewLayout;
        TextView textView = view != null ? (TextView) view.findViewById(R.id.subscreen_header_app_name) : null;
        CharSequence text = itemViewHolder.mTitle.getText();
        this.titleText = text;
        if (textView != null) {
            if (!z2) {
                text = itemViewHolder.mInfo.mAppName;
            }
            textView.setText(text);
        }
        String str2 = "S.S.N.";
        LinearLayout linearLayout = itemViewHolder.mContentLayout;
        if (!z2) {
            Log.d("S.S.N.", "setContentViewItem B5 - key : " + itemViewHolder.mInfo.mKey);
            View inflate = LayoutInflater.from(context).inflate(R.layout.subscreen_notification_detail_adapter_content_layout_item_b5, (ViewGroup) linearLayout, false);
            SubscreenNotificationInfo subscreenNotificationInfo2 = itemViewHolder.mInfo;
            String str3 = subscreenNotificationInfo2.mBigText;
            if (str3 == null) {
                str3 = subscreenNotificationInfo2.mContent;
            }
            bindContent(inflate, str3, itemViewHolder);
            Bitmap bitmap = itemViewHolder.mInfo.mBitmap;
            if (bitmap != null) {
                TextView textView2 = (TextView) inflate.findViewById(R.id.detail_content_text);
                ImageView imageView = (ImageView) inflate.findViewById(R.id.detail_content_image);
                float width = bitmap.getWidth();
                float height = bitmap.getHeight();
                float dimensionPixelSize = context.getResources().getDimensionPixelSize(R.dimen.subscreen_noti_detail_content_image_size_b5);
                float f = 2 * dimensionPixelSize;
                StringBuilder m88m = VIDirector$$ExternalSyntheticOutline0.m88m("bindImageBitmap bitmapWidth : ", width, " bitmapHeight : ", height, " viewWidth : ");
                m88m.append(dimensionPixelSize);
                m88m.append(" viewHeight : ");
                m88m.append(f);
                Log.d("S.S.N.", m88m.toString());
                LinearLayout.LayoutParams layoutParams3 = (LinearLayout.LayoutParams) imageView.getLayoutParams();
                layoutParams3.gravity = 3;
                layoutParams3.width = width > (((float) 200) * dimensionPixelSize) / ((float) IKnoxCustomManager.Stub.TRANSACTION_getAsoc) ? (int) dimensionPixelSize : (int) width;
                layoutParams3.topMargin = textView2.getVisibility() != 8 ? context.getResources().getDimensionPixelSize(R.dimen.subscreen_noti_detail_content_image_top_margin_b5) : 0;
                imageView.setLayoutParams(layoutParams3);
                imageView.setMaxHeight((int) f);
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setVisibility(0);
                imageView.setImageBitmap(bitmap);
            }
            SubscreenNotificationInfo subscreenNotificationInfo3 = itemViewHolder.mInfo;
            if (subscreenNotificationInfo3.mShowWhen) {
                bindTime(inflate, subscreenNotificationInfo3.mWhen, itemViewHolder);
            }
            linearLayout.addView(inflate);
            return;
        }
        int dimensionPixelSize2 = context.getResources().getDimensionPixelSize(R.dimen.subscreen_noti_detail_conversation_bottom_margin_b5);
        int dimensionPixelSize3 = context.getResources().getDimensionPixelSize(R.dimen.subscreen_noti_detail_conversation_bottom_margin_small_b5);
        int dimensionPixelSize4 = context.getResources().getDimensionPixelSize(R.dimen.subscreen_noti_detail_conversation_bottom_margin_last_b5);
        int dimensionPixelSize5 = context.getResources().getDimensionPixelSize(R.dimen.subscreen_noti_detail_icon_button_bg_size_b5);
        ArrayList arrayList = itemViewHolder.mInfo.mMessageingStyleInfoArray;
        int size = arrayList.size();
        Log.d("S.S.N.", "setContentViewItem B5 - conversation key : " + itemViewHolder.mInfo.mKey + ", size : " + size);
        Iterator it = arrayList.iterator();
        boolean z3 = false;
        while (it.hasNext()) {
            SubscreenNotificationInfo.MessagingStyleInfo messagingStyleInfo = (SubscreenNotificationInfo.MessagingStyleInfo) it.next();
            if (messagingStyleInfo.mIsChecked) {
                i3++;
            } else {
                View inflate2 = LayoutInflater.from(context).inflate(messagingStyleInfo.mIsReply ? R.layout.subscreen_notification_detail_adapter_conversation_send_b5 : R.layout.subscreen_notification_detail_adapter_conversation_received_b5, linearLayout, z3);
                LinearLayout.LayoutParams layoutParams4 = (LinearLayout.LayoutParams) inflate2.getLayoutParams();
                ImageView imageView2 = (ImageView) inflate2.findViewById(R.id.detail_conversation_icon);
                if (imageView2 != null) {
                    imageView2.setImageIcon(null);
                }
                String str4 = itemViewHolder.mPrevSender;
                Iterator it2 = it;
                boolean z4 = str4 != null && Intrinsics.areEqual(str4, messagingStyleInfo.mSender);
                LinearLayout linearLayout2 = linearLayout;
                if (Intrinsics.areEqual(Notification.MessagingStyle.class, itemViewHolder.mInfo.mSbn.getNotification().getNotificationStyle())) {
                    NotificationEntry notificationEntry = itemViewHolder.mInfo.mRow.mEntry;
                    if (!(notificationEntry != null ? "com.viber.voip".equals(notificationEntry.mSbn.getPackageName()) : false)) {
                        z = false;
                        boolean z5 = true;
                        int i4 = dimensionPixelSize4;
                        if (!(z | itemViewHolder.mInfo.mIsGroupConversation) || !(!messagingStyleInfo.mIsReply)) {
                            if (!z4 || i3 == 0) {
                                String str5 = messagingStyleInfo.mSender;
                                TextView textView3 = (TextView) inflate2.findViewById(R.id.detail_sender_text);
                                textView3.setVisibility(0);
                                textView3.setText(str5);
                                itemViewHolder.mPrevSender = str5;
                                itemViewHolder.mBodyLayoutString = AbstractResolvableFuture$$ExternalSyntheticOutline0.m14m(itemViewHolder.mBodyLayoutString, str5);
                                if (imageView2 != null) {
                                    imageView2.setVisibility(0);
                                }
                            } else if (imageView2 != null) {
                                imageView2.setVisibility(4);
                            }
                            if (imageView2 != null) {
                                imageView2.setVisibility(8);
                            }
                        } else {
                            itemViewHolder.mPrevSender = null;
                            if (imageView2 != null) {
                                imageView2.setVisibility(8);
                            }
                        }
                        bindContent(inflate2, messagingStyleInfo.mContentText, itemViewHolder);
                        drawable = messagingStyleInfo.mUriImage;
                        if (drawable != null) {
                            i2 = dimensionPixelSize2;
                            i = dimensionPixelSize5;
                            layoutParams = layoutParams4;
                        } else {
                            ImageView imageView3 = (ImageView) inflate2.findViewById(R.id.detail_content_image);
                            imageView3.setVisibility(0);
                            int intrinsicWidth = drawable.getIntrinsicWidth();
                            int intrinsicHeight = drawable.getIntrinsicHeight();
                            i = dimensionPixelSize5;
                            int dimensionPixelSize6 = getMDisplayContext().getResources().getDimensionPixelSize(R.dimen.subscreen_noti_detail_conversation_image_minimum_size_b5);
                            if (intrinsicWidth >= dimensionPixelSize6 || intrinsicHeight >= dimensionPixelSize6) {
                                i2 = dimensionPixelSize2;
                                layoutParams = layoutParams4;
                            } else {
                                layoutParams = layoutParams4;
                                i2 = dimensionPixelSize2;
                                StringBuilder m45m = GridLayoutManager$$ExternalSyntheticOutline0.m45m("resizing image. drawable width = ", intrinsicWidth, ", height = ", intrinsicHeight, " | minimum = ");
                                m45m.append(dimensionPixelSize6);
                                Log.d(str2, m45m.toString());
                                LinearLayout.LayoutParams layoutParams5 = (LinearLayout.LayoutParams) imageView3.getLayoutParams();
                                if (intrinsicWidth > intrinsicHeight) {
                                    layoutParams5.width = dimensionPixelSize6;
                                } else {
                                    layoutParams5.height = dimensionPixelSize6;
                                }
                                imageView3.setLayoutParams(layoutParams5);
                                imageView3.setScaleType(ImageView.ScaleType.CENTER_CROP);
                            }
                            imageView3.setImageDrawable(drawable);
                        }
                        i3++;
                        if (size == i3 && messagingStyleInfo.mIsReply == ((SubscreenNotificationInfo.MessagingStyleInfo) arrayList.get(i3)).mIsReply) {
                            SubscreenNotificationInfo.MessagingStyleInfo messagingStyleInfo2 = (SubscreenNotificationInfo.MessagingStyleInfo) arrayList.get(i3);
                            long j2 = messagingStyleInfo.mPostedTime;
                            if (j2 <= 0) {
                                j2 = messagingStyleInfo.mTimeStamp;
                            }
                            str = str2;
                            long j3 = messagingStyleInfo2.mPostedTime;
                            if (j3 <= 0) {
                                j3 = messagingStyleInfo2.mTimeStamp;
                            }
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
                            Date date = new Date(j2);
                            Date date2 = new Date(j3);
                            String format = simpleDateFormat.format(date);
                            String format2 = simpleDateFormat.format(date2);
                            String str6 = messagingStyleInfo.mSender;
                            String str7 = messagingStyleInfo2.mSender;
                            if (Intrinsics.areEqual(format, format2) && Intrinsics.areEqual(str6, str7)) {
                                z5 = false;
                            }
                            if (!z5) {
                                layoutParams2 = layoutParams;
                                if (i3 == size) {
                                    layoutParams2.bottomMargin = ((i / 2) + i4) - dimensionPixelSize3;
                                }
                                inflate2.setLayoutParams(layoutParams2);
                                linearLayout2.addView(inflate2);
                                z3 = false;
                                str2 = str;
                                it = it2;
                                dimensionPixelSize4 = i4;
                                dimensionPixelSize5 = i;
                                dimensionPixelSize2 = i2;
                                linearLayout = linearLayout2;
                            }
                        } else {
                            str = str2;
                        }
                        j = messagingStyleInfo.mPostedTime;
                        if (j <= 0) {
                            j = messagingStyleInfo.mTimeStamp;
                        }
                        bindTime(inflate2, j, itemViewHolder);
                        layoutParams2 = layoutParams;
                        layoutParams2.bottomMargin = i2 - dimensionPixelSize3;
                        if (i3 == size) {
                        }
                        inflate2.setLayoutParams(layoutParams2);
                        linearLayout2.addView(inflate2);
                        z3 = false;
                        str2 = str;
                        it = it2;
                        dimensionPixelSize4 = i4;
                        dimensionPixelSize5 = i;
                        dimensionPixelSize2 = i2;
                        linearLayout = linearLayout2;
                    }
                }
                z = true;
                boolean z52 = true;
                int i42 = dimensionPixelSize4;
                if (!((z | itemViewHolder.mInfo.mIsGroupConversation) & (messagingStyleInfo.mIsReply ^ true))) {
                }
                bindContent(inflate2, messagingStyleInfo.mContentText, itemViewHolder);
                drawable = messagingStyleInfo.mUriImage;
                if (drawable != null) {
                }
                i3++;
                if (size == i3) {
                }
                str = str2;
                j = messagingStyleInfo.mPostedTime;
                if (j <= 0) {
                }
                bindTime(inflate2, j, itemViewHolder);
                layoutParams2 = layoutParams;
                layoutParams2.bottomMargin = i2 - dimensionPixelSize3;
                if (i3 == size) {
                }
                inflate2.setLayoutParams(layoutParams2);
                linearLayout2.addView(inflate2);
                z3 = false;
                str2 = str;
                it = it2;
                dimensionPixelSize4 = i42;
                dimensionPixelSize5 = i;
                dimensionPixelSize2 = i2;
                linearLayout = linearLayout2;
            }
        }
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
        View view = viewHolder.itemView;
        RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) view.getLayoutParams();
        context.getResources().getDimensionPixelSize(R.dimen.subscreen_noti_group_footer_top_margin_b5);
        ((ViewGroup.MarginLayoutParams) layoutParams).topMargin = 0;
        view.setLayoutParams(layoutParams);
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
        SubscreenNotificationDetailAdapter subscreenNotificationDetailAdapter;
        this.mIsReplySendButtonLoading = false;
        SubscreenSubRoomNotification subscreenSubRoomNotification = this.mSubRoomNotification;
        View view = (subscreenSubRoomNotification == null || (subscreenNotificationDetailAdapter = subscreenSubRoomNotification.mNotificationDetailAdapter) == null) ? null : subscreenNotificationDetailAdapter.mReplyButtonView;
        if (view != null) {
            view.findViewById(R.id.send).setEnabled(true);
        }
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final void setItemDecoration(final SubscreenRecyclerView subscreenRecyclerView) {
        final int dimensionPixelSize = getMDisplayContext().getResources().getDimensionPixelSize(R.dimen.subscreen_noti_list_recyclerview_top_margin_b5);
        subscreenRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() { // from class: com.android.systemui.statusbar.notification.SubscreenDeviceModelB5$setItemDecoration$1
            @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
            public final void getItemOffsets(Rect rect, View view, RecyclerView recyclerView, RecyclerView.State state) {
                RecyclerView.Adapter adapter = RecyclerView.this.mAdapter;
                SubscreenDeviceModelB5 subscreenDeviceModelB5 = this;
                SubscreenSubRoomNotification subscreenSubRoomNotification = subscreenDeviceModelB5.mSubRoomNotification;
                if (Intrinsics.areEqual(adapter, subscreenSubRoomNotification != null ? subscreenSubRoomNotification.mNotificationListAdapter : null)) {
                    SubscreenSubRoomNotification subscreenSubRoomNotification2 = subscreenDeviceModelB5.mSubRoomNotification;
                    if ((subscreenSubRoomNotification2 == null || subscreenSubRoomNotification2.mNotificationInfoManager == null || SubscreenNotificationInfoManager.getNotificationInfoArraySize() != 0) ? false : true) {
                        return;
                    }
                    recyclerView.getClass();
                    if (RecyclerView.getChildAdapterPosition(view) == 0) {
                        rect.top = dimensionPixelSize;
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
        if (findViewById != null) {
            if (findViewById.getVisibility() == 0) {
                dimensionPixelSize = (dimensionPixelSize - context.getResources().getDimensionPixelSize(R.dimen.subscreen_noti_clock_start_margin_b5)) - context.getResources().getDimensionPixelSize(R.dimen.subscreen_noti_top_popup_clock_width_b5);
            }
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
        String str;
        super.setPopupItemInfo(context, notificationEntry, z, z2);
        SubscreenNotificationInfo subscreenNotificationInfo2 = this.popupInfo;
        boolean z3 = subscreenNotificationInfo2 != null ? subscreenNotificationInfo2.mIsGroupConversation : false;
        if (!z && !this.needsRedaction && z3 && (view = this.mPopUpViewLayout) != null && (textView = (TextView) view.findViewById(R.id.subscreen_notification_sender_text)) != null && (subscreenNotificationInfo = this.popupInfo) != null && (arrayList = subscreenNotificationInfo.mMessageingStyleInfoArray) != null) {
            SubscreenNotificationInfo.MessagingStyleInfo messagingStyleInfo = (SubscreenNotificationInfo.MessagingStyleInfo) (arrayList.isEmpty() ? null : arrayList.get(arrayList.size() - 1));
            if (messagingStyleInfo != null && (str = messagingStyleInfo.mSender) != null) {
                SubscreenNotificationInfo subscreenNotificationInfo3 = this.popupInfo;
                String title = subscreenNotificationInfo3 != null ? subscreenNotificationInfo3.getTitle() : null;
                if ((str.length() > 0) && !Intrinsics.areEqual(str, title)) {
                    textView.setText(str);
                    textView.setVisibility(0);
                }
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
        view.setBackground(getMDisplayContext().getDrawable(R.drawable.subscreen_notification_reply_item_bg_selecter_b5));
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final void setSmartReplyResultValue(int i, StringBuilder sb, String str) {
        LinearLayout linearLayout = this.progressLayout;
        boolean z = false;
        if (linearLayout != null && linearLayout.getVisibility() == 0) {
            z = true;
        }
        if (z) {
            this.mSmartReplyResult = i;
            this.mSmartReplyResultCompleteMsg = sb;
            this.mSmartReplyResultFailureMsg = str;
        }
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final void setStartedReplyActivity() {
        this.mIsStartedReplyActivity = false;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:20:0x002e  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0034  */
    /* JADX WARN: Removed duplicated region for block: B:83:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r0v16 */
    /* JADX WARN: Type inference failed for: r0v17 */
    /* JADX WARN: Type inference failed for: r0v19 */
    /* JADX WARN: Type inference failed for: r0v20 */
    /* JADX WARN: Type inference failed for: r0v21 */
    /* JADX WARN: Type inference failed for: r0v24 */
    /* JADX WARN: Type inference failed for: r1v8, types: [T, java.lang.String] */
    /* JADX WARN: Type inference failed for: r3v3 */
    /* JADX WARN: Type inference failed for: r3v4 */
    /* JADX WARN: Type inference failed for: r3v7 */
    /* JADX WARN: Type inference failed for: r5v17 */
    /* JADX WARN: Type inference failed for: r5v3 */
    /* JADX WARN: Type inference failed for: r5v4 */
    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void showAIReply() {
        boolean z;
        LinearLayout linearLayout;
        ?? r0;
        SubscreenNotificationInfo subscreenNotificationInfo;
        SubscreenNotificationInfo subscreenNotificationInfo2;
        boolean z2 = true;
        if (this.isPossibleAiReply) {
            SubscreenNotificationDetailAdapter.ItemViewHolder itemViewHolder = this.detailViewHolder;
            if (((itemViewHolder == null || (subscreenNotificationInfo2 = itemViewHolder.mInfo) == null || !subscreenNotificationInfo2.mIsMessagingStyle) ? false : true) != false) {
                if (((itemViewHolder == null || (subscreenNotificationInfo = itemViewHolder.mInfo) == null || !subscreenNotificationInfo.mRemoteinput) ? false : true) != false) {
                    r0 = true;
                    if (r0 != false) {
                        z = isReplyLayoutShowing();
                        if (z) {
                            return;
                        }
                        final SubscreenNotificationDetailAdapter.ItemViewHolder itemViewHolder2 = this.detailViewHolder;
                        if (itemViewHolder2 != null) {
                            ListPopupWindow$$ExternalSyntheticOutline0.m10m("callAIReply() - start smartReplyStatus : ", this.smartReplyStatus, "S.S.N.");
                            if (this.smartReplyStatus != 0) {
                                Log.d("S.S.N.", "callAIReply() return - CallAIReply is already running");
                            } else {
                                this.smartReplyStatus = 1;
                                final String historyInfo = getHistoryInfo(itemViewHolder2);
                                r4 = null;
                                Float f = null;
                                if (historyInfo != null) {
                                    String obj = StringsKt__StringsKt.trim(historyInfo).toString();
                                    if ((obj == null || obj.length() == 0) == false) {
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
                                                TextView textView = this.smartReplyErrorMessageView;
                                                if (textView != null) {
                                                    textView.setVisibility(8);
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
                                                                StringBuilder m69m = KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0.m69m("callAIReply() - successful : ", isSuccessful2, ", isComplete : ", isComplete, ", result : ");
                                                                m69m.append(result);
                                                                m69m.append(", isSupport : ");
                                                                m69m.append(access$isSupportableLanguage);
                                                                Log.d("S.S.N.", m69m.toString());
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
                                                                    TextView textView2 = subscreenDeviceModelB52.smartReplyErrorMessageView;
                                                                    if (textView2 != null) {
                                                                        textView2.setText(R.string.subscreen_notification_smart_reply_error_unsupported_language);
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
                                            ListPopupWindow$$ExternalSyntheticOutline0.m10m("callAIReply() - end smartReplyStatus : ", this.smartReplyStatus, "S.S.N.");
                                        }
                                    }
                                }
                                handleProgressLayout(false);
                                LinearLayout linearLayout2 = this.progressLayout;
                                if (linearLayout2 != null) {
                                    linearLayout2.setVisibility(8);
                                }
                                String obj2 = historyInfo != null ? StringsKt__StringsKt.trim(historyInfo).toString() : null;
                                if (obj2 != null && obj2.length() != 0) {
                                    z2 = false;
                                }
                                if (z2 && this.mSmartReplyClickedByUser) {
                                    this.mSmartReplyClickedByUser = false;
                                    enableSmartReplyTriggerBtn("", false);
                                    updateVisibilityForSmartReplyLayout(8);
                                    TextView textView2 = this.smartReplyErrorMessageView;
                                    if (textView2 != null) {
                                        textView2.setText(R.string.subscreen_notification_smart_reply_error_other);
                                    }
                                    showErrorMessageWithAnim(this.smartReplyErrorMessageView);
                                }
                                this.smartReplyStatus = 0;
                                AbstractC0000x2c234b15.m3m("callAIReply() return - history is null or empty", historyInfo, "S.S.N.");
                            }
                        }
                        this.isPossibleAiReply = false;
                        return;
                    }
                }
            }
            r0 = false;
            if (r0 != false) {
            }
        }
        z = false;
        if (z) {
        }
    }

    public final void showBouncer(Context context, final NotificationEntry notificationEntry) {
        SubRoom.StateChangeListener stateChangeListener;
        boolean isKeyguardStats = isKeyguardStats();
        KeyguardStateController keyguardStateController = this.mKeyguardStateController;
        Log.d("S.S.N.", "showBouncer B5 -isMethodSecure : " + (keyguardStateController != null ? Boolean.valueOf(((KeyguardStateControllerImpl) keyguardStateController).mSecure) : null) + ", isUnlocked : " + isKeyguardStats);
        KeyguardStateController keyguardStateController2 = this.mKeyguardStateController;
        if (!(keyguardStateController2 != null && ((KeyguardStateControllerImpl) keyguardStateController2).mSecure)) {
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
        int dimensionPixelSize = getMDisplayContext().getResources().getDimensionPixelSize(R.dimen.subscreen_noti_detail_reply_button_margin_b5) + view2.getHeight() + (getMainHeaderViewHeight() / 2);
        if (this.mIsFlexMode) {
            SubscreenSubRoomNotification subscreenSubRoomNotification = this.mSubRoomNotification;
            Integer valueOf = (subscreenSubRoomNotification == null || (linearLayout = subscreenSubRoomNotification.mSubscreenMainLayout) == null) ? null : Integer.valueOf(linearLayout.getHeight());
            Intrinsics.checkNotNull(valueOf);
            dimensionPixelSize += (720 - valueOf.intValue()) / 2;
        }
        final Ref$IntRef ref$IntRef = new Ref$IntRef();
        ref$IntRef.element = getMDisplayContext().getResources().getDimensionPixelSize(R.dimen.subscreen_noti_detail_icon_button_bg_size_b5);
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
                                    ((ImageView) view.findViewById(R.id.send)).performClick();
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
            popupWindow4.showAtLocation(view, 1, 0, dimensionPixelSize);
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
        Result.m2859exceptionOrNullimpl(failure);
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
        boolean areEqual = Intrinsics.areEqual(obj, "Blocked by input safety filter");
        Context context = this.mContext;
        String string = areEqual ? context.getString(R.string.subscreen_notification_smart_reply_error_safety_filter) : Intrinsics.areEqual(obj, "Input is too long") ? context.getString(R.string.subscreen_notification_smart_reply_error_input_is_too_long) : context.getString(R.string.subscreen_notification_smart_reply_error_other);
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
        AnimationDrawable animationDrawable = (AnimationDrawable) ((ImageView) inflate.findViewById(R.id.unlock_icon_view)).getDrawable();
        final PopupWindow popupWindow = new PopupWindow(inflate, -2, -2);
        popupWindow.showAtLocation(inflate, 49, 0, getMDisplayContext().getResources().getDimensionPixelSize(R.dimen.subscreen_noti_unlock_icon_view_top_margin_b5));
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
        return getMDisplayContext().getResources().getDimensionPixelSize(z ? R.dimen.subscreen_noti_header_icon_circle_padding_b5 : z2 ? R.dimen.subscreen_noti_full_popup_icon_circle_padding_b5 : z3 ? R.dimen.subscreen_noti_sub_icon_circle_padding_b5 : R.dimen.subscreen_noti_icon_circle_padding_b5);
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final int squircleRadius(boolean z, boolean z2) {
        return getMDisplayContext().getResources().getDimensionPixelSize(z ? R.dimen.subscreen_noti_header_small_icon_bg_radius_b5 : z2 ? R.dimen.subscreen_noti_popup_small_icon_bg_radius_b5 : R.dimen.subscreen_noti_list_small_icon_bg_radius_b5);
    }

    public final int startNotificationIntent(PendingIntent pendingIntent) {
        Display display = this.mSubDisplay;
        if (display == null) {
            return -1;
        }
        return pendingIntent.sendAndReturnResult(getMDisplayContext(), 0, null, null, null, null, CentralSurfaces.getActivityOptions(display.getDisplayId(), null));
    }

    public final void startProgressSpringAnimation(View view, final boolean z, final Runnable runnable) {
        float f;
        float f2 = 0.0f;
        float f3 = 1.0f;
        if (!z) {
            f = 1.0f;
            f3 = 0.0f;
            f2 = 1.0f;
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
            f = 0.85f;
        }
        view.setScaleX(f);
        view.setScaleY(f);
        view.setAlpha(f2);
        resetProgressScaleAnimation();
        view.animate().alpha(f3).setDuration(200L).setListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.statusbar.notification.SubscreenDeviceModelB5$startProgressSpringAnimation$1
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
        SpringForce springForce = new SpringForce(f3);
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
            public final void onAnimationEnd(DynamicAnimation dynamicAnimation, boolean z2, float f4, float f5) {
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
        frameLayout.setVisibility(z ? 0 : 8);
        findViewById.setVisibility(z ? 8 : 0);
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final void updateImportBadgeIconRing(View view, boolean z) {
        ImageView imageView = view != null ? (ImageView) view.findViewById(R.id.subscreen_notification_important_badge_ring) : null;
        if (imageView == null) {
            return;
        }
        if (!z) {
            imageView.setVisibility(8);
            return;
        }
        int color = getMDisplayContext().getColor(android.R.color.decor_button_light_color);
        if (isShowNotificationAppIcon()) {
            imageView.setImageDrawable((VectorDrawable) getMDisplayContext().getDrawable(R.drawable.squircle_tray_stroke_small));
            imageView.setColorFilter(color);
        } else {
            imageView.setColorFilter((ColorFilter) null);
            imageView.setLayerType(1, null);
            imageView.setImageDrawable(getMDisplayContext().getDrawable(R.drawable.subscreen_notification_conversation_badge_ring_b5));
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
            /* JADX WARN: Removed duplicated region for block: B:16:0x006d  */
            @Override // java.lang.Runnable
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public final void run() {
                boolean z;
                SubscreenDeviceModelB5 subscreenDeviceModelB5;
                int i;
                Bundle call;
                Bundle call2;
                SubscreenDeviceModelB5 subscreenDeviceModelB52 = SubscreenDeviceModelB5.this;
                int i2 = SubscreenDeviceModelB5.$r8$clinit;
                boolean z2 = false;
                try {
                    call2 = subscreenDeviceModelB52.mContext.getContentResolver().call(Uri.parse("content://com.samsung.android.samsungaccount.accountmanagerprovider"), "isChildAccount", "i5to7wq0er", (Bundle) null);
                } catch (Exception e2) {
                    AbstractC0000x2c234b15.m3m("Exception Error isChildAccount : ", e2.getMessage(), "S.S.N.");
                }
                if (call2 != null) {
                    int i3 = call2.getInt("result_code", 1);
                    String string = call2.getString("result_message", "");
                    if (i3 != 0) {
                        AbstractC0000x2c234b15.m3m("isChildAccount Fail : resultMessage = ", string, "S.S.N.");
                    } else {
                        if ("true".equals(string)) {
                            Log.d("S.S.N.", "This account is a child account.");
                            z = true;
                            if (z && !SalesCode.isKor) {
                                z2 = true;
                            }
                            subscreenDeviceModelB52.isChildAccount = z2;
                            subscreenDeviceModelB5 = SubscreenDeviceModelB5.this;
                            if (subscreenDeviceModelB5.isChildAccount) {
                                try {
                                    call = subscreenDeviceModelB5.mContext.getContentResolver().call(Uri.parse("content://com.samsung.android.samsungaccount.accountmanagerprovider"), "getFamilyServiceInfo", "i5to7wq0er", (Bundle) null);
                                } catch (Exception e3) {
                                    AbstractC0000x2c234b15.m3m("Exception Error getFamilyServiceInfo : ", e3.getMessage(), "S.S.N.");
                                }
                                if (call != null) {
                                    if (call.getInt("result_code", 1) == 0) {
                                        Bundle bundle = call.getBundle("result_bundle");
                                        if (bundle != null) {
                                            i = bundle.getInt("childGraduateAge");
                                            subscreenDeviceModelB5.childGraduateAge = i;
                                        }
                                    } else {
                                        AbstractC0000x2c234b15.m3m("getChildGraduateAge() Fail : resultMessage = ", call.getString("result_message", ""), "S.S.N.");
                                    }
                                }
                                i = -1;
                                subscreenDeviceModelB5.childGraduateAge = i;
                            }
                            KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m62m("getChildAccount() : isChildAccount ", SubscreenDeviceModelB5.this.isChildAccount, "S.S.N.");
                        }
                        Log.d("S.S.N.", "This account is not a child account.");
                    }
                } else {
                    Log.d("S.S.N.", "Result bundle is null");
                }
                z = false;
                if (z) {
                    z2 = true;
                }
                subscreenDeviceModelB52.isChildAccount = z2;
                subscreenDeviceModelB5 = SubscreenDeviceModelB5.this;
                if (subscreenDeviceModelB5.isChildAccount) {
                }
                KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m62m("getChildAccount() : isChildAccount ", SubscreenDeviceModelB5.this.isChildAccount, "S.S.N.");
            }
        });
        KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m62m("updateSamsungAccount() : isSALoggedIn ", this.isSALoggedIn, "S.S.N.");
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final void updateShadowIconColor(View view, NotificationEntry notificationEntry) {
        Drawable drawable;
        ExpandableNotificationRow expandableNotificationRow;
        if (view == null) {
            return;
        }
        int appPrimaryColor = (notificationEntry == null || (expandableNotificationRow = notificationEntry.row) == null) ? 0 : ((NotificationColorPicker) Dependency.get(NotificationColorPicker.class)).getAppPrimaryColor(expandableNotificationRow);
        boolean isGrayScaleIcon = isGrayScaleIcon(notificationEntry);
        ImageView imageView = (ImageView) view.findViewById(R.id.group_icon_shadow);
        if (imageView != null) {
            imageView.setVisibility(0);
            if (isShowNotificationAppIcon()) {
                imageView.clearColorFilter();
                Drawable.ConstantState constantState = ((ImageView) view.findViewById(R.id.app_icon)).getDrawable().getConstantState();
                if (constantState == null || (drawable = constantState.newDrawable()) == null) {
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
            Context context = this.mContext;
            int color = context.getColor(R.color.notification_non_grayscale_border_color);
            int color2 = context.getColor(R.color.notification_non_grayscale_fill_color);
            GradientDrawable gradientDrawable = (GradientDrawable) imageView.getDrawable().mutate();
            gradientDrawable.setColor(Color.argb((Color.alpha(color2) * 3) / 10, Color.red(color2), Color.green(color2), Color.blue(color2)));
            gradientDrawable.setStroke(context.getResources().getDimensionPixelSize(R.dimen.notification_icon_border_width), Color.argb((Color.alpha(color) * 3) / 10, Color.red(color), Color.green(color), Color.blue(color)));
        }
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final void updateSmallIconBg(ImageView imageView, boolean z, boolean z2, boolean z3) {
        int smallIconPadding = smallIconPadding(z, z2, z3);
        if (imageView != null) {
            imageView.setBackground(getMDisplayContext().getResources().getDrawable(R.drawable.notification_icon_circle, null));
            imageView.setPadding(smallIconPadding, smallIconPadding, smallIconPadding, smallIconPadding);
        }
    }

    public final void updateSmartReplyVariables() {
        SettingsHelper settingsHelper = this.mSettingsHelper;
        settingsHelper.getClass();
        this.isAiInfoConfirmed = NotiRune.NOTI_SUBSCREEN_NOTIFICATION_FIFTH && settingsHelper.mItemLists.get("ai_info_confirmed").getIntValue() != 0;
        this.isSuggestResponsesEnabled = settingsHelper.isSuggestResponsesEnabled();
        this.isRDUMode = Settings.Secure.getInt(this.mContext.getContentResolver(), "shopdemo", 0) == 1;
        updateSamsungAccount();
        boolean z = this.isRDUMode;
        boolean z2 = this.isSALoggedIn;
        boolean z3 = this.isChildAccount;
        boolean z4 = this.isAiInfoConfirmed;
        boolean z5 = this.isSuggestResponsesEnabled;
        boolean z6 = NotiRune.NOTI_SUBSCREEN_SUPPORT_SMART_REPLY_AI_FOR_CHINA && isPreventOnlineProcessing();
        StringBuilder m69m = KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0.m69m("isRDUMode: ", z, " isSALoggedIn: ", z2, " isChildAccount: ");
        KeyguardFaceListenModel$$ExternalSyntheticOutline0.m67m(m69m, z3, " isAiInfoConfirmed: ", z4, " isSuggestionResponsesEnabled: ");
        KeyguardCarrierViewController$$ExternalSyntheticOutline0.m63m(m69m, z5, " isPreventOnlineProcessing: ", z6, "S.S.N.");
    }

    public final void updateVisibilityForSmartReplyLayout(int i) {
        TextView textView;
        SubscreenNotificationDetailAdapter.ItemViewHolder itemViewHolder = this.detailViewHolder;
        LinearLayout linearLayout = itemViewHolder != null ? itemViewHolder.mSmartReplyLayout : null;
        if (linearLayout != null) {
            linearLayout.setVisibility(i);
        }
        ImageView imageView = this.aiDisclaimerBtn;
        (imageView != null ? imageView : null).setVisibility(i);
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
        if (notificationEntry != null && (statusBarNotification = notificationEntry.mSbn) != null && (notification2 = statusBarNotification.getNotification()) != null && Intrinsics.areEqual("call", notification2.category) && notification2.isStyle(Notification.CallStyle.class)) {
            z = true;
        }
        return !z;
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final void enableGoToTopButton() {
    }
}
