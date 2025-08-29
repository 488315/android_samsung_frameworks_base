package com.android.systemui.statusbar.notification;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.ActivityOptions;
import android.app.ActivityTaskManager;
import android.app.IActivityTaskManager;
import android.app.KeyguardManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentCallbacks;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.graphics.drawable.VectorDrawable;
import android.hardware.display.DisplayManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.RemoteException;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.Settings;
import android.service.notification.StatusBarNotification;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.telephony.TelephonyManager;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.util.Property;
import android.view.ContextThemeWrapper;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SemBlurInfo;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.widget.DateTimeView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import androidx.appcompat.app.AlertController$$ExternalSyntheticOutline0;
import androidx.appcompat.app.AppCompatDelegateImpl$AutoBatteryNightModeManager$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.collection.MutableObjectList$$ExternalSyntheticOutline0;
import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import androidx.compose.animation.core.CubicBezierEasing$$ExternalSyntheticOutline0;
import androidx.compose.animation.graphics.vector.PropertyValuesHolder2D$$ExternalSyntheticOutline0;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.core.view.ViewGroupKt;
import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.dynamicanimation.animation.SpringAnimation;
import androidx.dynamicanimation.animation.SpringForce;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import androidx.picker3.widget.SeslColorSpectrumView$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import com.airbnb.lottie.LottieAnimationView;
import com.android.app.animation.Interpolators;
import com.android.keyguard.EmergencyButton$$ExternalSyntheticOutline0;
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardBiometricLockoutLogger$mKeyguardUpdateMonitorCallback$1$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecSimPinViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.StrongAuthPopup$$ExternalSyntheticOutline0;
import com.android.settingslib.SecNotificationBlockManager$$ExternalSyntheticOutline0;
import com.android.settingslib.utils.ThreadUtils;
import com.android.systemui.Dependency;
import com.android.systemui.NotiRune;
import com.android.systemui.Prefs;
import com.android.systemui.R;
import com.android.systemui.bixby2.controller.NotificationController;
import com.android.systemui.edgelighting.effect.utils.SalesCode;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.plugins.subscreen.SubRoom;
import com.android.systemui.popup.util.PopupUIUtil;
import com.android.systemui.qp.util.SubscreenUtil;
import com.android.systemui.settings.UserContextProvider;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.statusbar.NotificationLockscreenUserManager;
import com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl;
import com.android.systemui.statusbar.notification.SubscreenDeviceModelB5;
import com.android.systemui.statusbar.notification.SubscreenNotificationDetailAdapter;
import com.android.systemui.statusbar.notification.SubscreenNotificationGroupAdapter;
import com.android.systemui.statusbar.notification.SubscreenNotificationInfo;
import com.android.systemui.statusbar.notification.SubscreenNotificationListAdapter;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.inflation.BindEventManager;
import com.android.systemui.statusbar.notification.collection.notifcollection.CommonNotifCollection;
import com.android.systemui.statusbar.notification.collection.render.NotificationVisibilityProvider;
import com.android.systemui.statusbar.notification.interruption.NotificationInterruptStateProvider;
import com.android.systemui.statusbar.notification.interruption.NotificationInterruptStateProviderImpl;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.stack.NotificationChildrenContainer;
import com.android.systemui.statusbar.phone.CentralSurfaces;
import com.android.systemui.statusbar.phone.CentralSurfacesImpl;
import com.android.systemui.statusbar.phone.StatusBarNotificationActivityStarter;
import com.android.systemui.statusbar.phone.ongoingactivity.OngoingActivityData;
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
import com.samsung.android.knox.custom.IKnoxCustomManager;
import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;
import com.samsung.android.sdk.scs.ai.translation.NeuralTranslator;
import com.samsung.android.sdk.scs.base.tasks.OnCompleteListener;
import com.samsung.android.sdk.scs.base.tasks.Task;
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
import java.util.MissingResourceException;
import kotlin.Result;
import kotlin.Unit;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.EmptyList;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref$IntRef;
import kotlin.jvm.internal.Ref$ObjectRef;
import kotlin.jvm.internal.StringCompanionObject;
import kotlin.text.Regex;
import kotlin.text.StringsKt__StringsJVMKt;
import kotlin.text.StringsKt__StringsKt;
import notification.src.com.android.systemui.BasePromptProcessor;
import notification.src.com.android.systemui.MultiPromptProcessor;
import notification.src.com.android.systemui.SrPromptProcessor;

/* loaded from: classes3.dex */
public class SubscreenDeviceModelB5 extends SubscreenDeviceModelCommon {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final String DISPLAY_LANG_CODE_DELIMITER;
    public final String SR_LLM_PACKAGE_NAME;
    public final String SUPPORT_FUNCTION_SMART_REPLY;
    public ImageView aiDisclaimerBtn;
    public final SubscreenDeviceModelB5$aodTspUpdateReceiver$1 aodTspUpdateReceiver;
    public final SubscreenDeviceModelB5$broadcastReceiver$1 broadcastReceiver;
    public TextView callBackButtonText;
    public int childGraduateAge;
    public TextView clearButtonText;
    public final SubscreenDeviceModelB5$componentCallbacks$1 componentCallbacks;
    public Account currentAccount;
    public LinearLayout detailButtonContainer;
    public SubscreenNotificationDetailAdapter.ItemViewHolder detailViewHolder;
    public long firstHistoryItemPostTimeInDetailAdapter;
    public int footerBottomMargin;
    public boolean isAiInfoConfirmed;
    public boolean isChildAccount;
    public final boolean isDebug;
    public boolean isPossibleAiReply;
    public boolean isRDUMode;
    public boolean isSALoggedIn;
    public boolean isSuggestResponsesEnabled;
    public boolean isUnusableAccount;
    public ImageView keyboardReplyButton;
    public int lastRotation;
    public int listFirstTopMargin;
    public SubscreenNotificationDialog mDialog;
    public View mHeaderViewLayout;
    public boolean mIsClickedPopupKeyguardUnlockShowing;
    public boolean mIsContentScroll;
    public boolean mIsNaviBarBackButtonClicked;
    public boolean mIsStartedReplyActivity;
    public final KeyguardActionInfo mKeyguardActionInfo;
    public SpringAnimation mProgressScaleAnimationX;
    public SpringAnimation mProgressScaleAnimationY;
    public final StringBuilder mPromptSB;
    public final StringBuilder mPromptSBForLog;
    public float mReplyLayoutCurrentPostionY;
    private final SettingsHelper.OnChangedCallback mSettingsListener;
    public boolean mSmartReplyClickedByUser;
    public final LinkedHashMap mSmartReplyHashMap;
    public int mSmartReplyResult;
    public StringBuilder mSmartReplyResultCompleteMsg;
    public String mSmartReplyResultFailureMsg;
    public final BasePromptProcessor mSrPromptProcessor;
    public final SubscreenDeviceModelB5$mSrResponseCallback$1 mSrResponseCallback;
    public String mUnlockNotificationPendingIntentItemKey;
    public String metaData;
    public boolean needToShowFTU;
    public final List onDeviceLanguageList;
    public TextView openAppButtonText;
    public final SubscreenDeviceModelB5$pkgBroadcastReceiver$1 pkgBroadcastReceiver;
    public LinearLayout progressLayout;
    public LottieAnimationView progressingVi;
    public TextView replyButtonText;
    public PopupWindow sendButtonPopupWindow;
    public TextView smartReplyErrorMessageView;
    public int smartReplyStatus;
    public ImageView smartReplyTriggerBtn;
    public TextView smartReplyTriggerBtnText;
    public LinearLayout suggestResponsesBtn;
    public boolean suggestedRepliesDisabledByUser;
    public CharSequence titleText;
    public final Context windowContext;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public final class KeyguardActionInfo {
        public int action;
        public Context context;
        public SubscreenNotificationDetailAdapter.ItemViewHolder detailAdapterItemViewHolder;
        public NotificationEntry entry;
        public boolean isShowBouncer;
        public NotificationEntry liveEntry;
        public SubscreenParentItemViewHolder subscreenParentItemViewHolder;

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
            StringBuilder sbM = KeyguardBiometricLockoutLogger$mKeyguardUpdateMonitorCallback$1$$ExternalSyntheticOutline0.m(i, "LlmLanguage(order=", ", language=", str, ", languageDisplayName=");
            sbM.append(str2);
            sbM.append(", supportToneConversion=");
            sbM.append(z);
            sbM.append(", supportCorrection=");
            sbM.append(z2);
            sbM.append(", supportReply=");
            sbM.append(z3);
            sbM.append(")");
            return sbM.toString();
        }

        public LlmLanguage(int i, String str, String str2, boolean z, boolean z2, boolean z3) {
            this.order = i;
            this.language = str;
            this.languageDisplayName = str2;
            this.supportToneConversion = z;
            this.supportCorrection = z2;
            this.supportReply = z3;
        }

        /*  JADX ERROR: NullPointerException in pass: InitCodeVariables
            java.lang.NullPointerException
            */
        public /* synthetic */ LlmLanguage(int r2, java.lang.String r3, java.lang.String r4, boolean r5, boolean r6, boolean r7, int r8, kotlin.jvm.internal.DefaultConstructorMarker r9) {
            /*
                r1 = this;
                r9 = r8 & 1
                if (r9 == 0) goto L5
                r2 = -1
            L5:
                r9 = r8 & 2
                java.lang.String r0 = ""
                if (r9 == 0) goto Lc
                r3 = r0
            Lc:
                r9 = r8 & 4
                if (r9 == 0) goto L11
                r4 = r0
            L11:
                r9 = r8 & 8
                r0 = 1
                if (r9 == 0) goto L17
                r5 = r0
            L17:
                r9 = r8 & 16
                if (r9 == 0) goto L1c
                r6 = r0
            L1c:
                r8 = r8 & 32
                if (r8 == 0) goto L28
                r9 = r0
                r7 = r5
                r8 = r6
                r5 = r3
                r6 = r4
                r3 = r1
                r4 = r2
                goto L2f
            L28:
                r9 = r7
                r8 = r6
                r6 = r4
                r7 = r5
                r4 = r2
                r5 = r3
                r3 = r1
            L2f:
                r3.<init>(r4, r5, r6, r7, r8, r9)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.SubscreenDeviceModelB5.LlmLanguage.<init>(int, java.lang.String, java.lang.String, boolean, boolean, boolean, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
        }
    }

    public final class SmartReplyData {
        public String prevPrompt;
        public String replyText;
    }

    static {
        new Companion(null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r10v2, types: [com.android.systemui.statusbar.notification.SubscreenDeviceModelB5$aodTspUpdateReceiver$1] */
    /* JADX WARN: Type inference failed for: r7v12, types: [com.android.systemui.statusbar.notification.SubscreenDeviceModelB5$componentCallbacks$1] */
    /* JADX WARN: Type inference failed for: r7v13, types: [android.content.BroadcastReceiver, com.android.systemui.statusbar.notification.SubscreenDeviceModelB5$broadcastReceiver$1] */
    /* JADX WARN: Type inference failed for: r8v5, types: [android.content.BroadcastReceiver, com.android.systemui.statusbar.notification.SubscreenDeviceModelB5$pkgBroadcastReceiver$1] */
    public SubscreenDeviceModelB5(Context context, KeyguardUpdateMonitor keyguardUpdateMonitor, SettingsHelper settingsHelper, UserContextProvider userContextProvider, SubscreenNotificationController subscreenNotificationController, Lazy lazy, CommonNotifCollection commonNotifCollection, LogBuffer logBuffer, NotificationInterruptStateProvider notificationInterruptStateProvider, Lazy lazy2, Lazy lazy3, NotificationVisibilityProvider notificationVisibilityProvider, BindEventManager bindEventManager, NotificationController notificationController, UserManager userManager, ConversationNotificationManager conversationNotificationManager) {
        super(context, keyguardUpdateMonitor, settingsHelper, userContextProvider, subscreenNotificationController, lazy, commonNotifCollection, logBuffer, notificationInterruptStateProvider, lazy2, lazy3, notificationVisibilityProvider, bindEventManager, notificationController, userManager, conversationNotificationManager);
        this.isDebug = DeviceType.getDebugLevel() == DeviceType.DEBUG_LEVEL_MID || DeviceType.getDebugLevel() == DeviceType.DEBUG_LEVEL_HIGH;
        this.mIsContentScroll = true;
        this.titleText = "";
        this.mPromptSB = new StringBuilder();
        this.mPromptSBForLog = new StringBuilder();
        this.mSmartReplyHashMap = new LinkedHashMap();
        Display display = ((DisplayManager) this.mContext.getSystemService("display")).getDisplay(1);
        this.windowContext = display != null ? context.createWindowContext(display, 2, null) : null;
        this.componentCallbacks = new ComponentCallbacks() { // from class: com.android.systemui.statusbar.notification.SubscreenDeviceModelB5$componentCallbacks$1
            @Override // android.content.ComponentCallbacks
            public final void onConfigurationChanged(Configuration configuration) {
                SubscreenNotificationDetailAdapter subscreenNotificationDetailAdapter;
                int rotation = configuration.windowConfiguration.getRotation();
                SubscreenDeviceModelB5 subscreenDeviceModelB5 = this.this$0;
                if (subscreenDeviceModelB5.lastRotation != rotation) {
                    subscreenDeviceModelB5.lastRotation = rotation;
                    if (subscreenDeviceModelB5.isShownDetail()) {
                        Log.d("S.S.N.", " rotation changed and dismiss reply send button if needed");
                        SubscreenSubRoomNotification subscreenSubRoomNotification = this.this$0.mSubRoomNotification;
                        if (subscreenSubRoomNotification == null || (subscreenNotificationDetailAdapter = subscreenSubRoomNotification.mNotificationDetailAdapter) == null) {
                            return;
                        }
                        subscreenNotificationDetailAdapter.dismissReplyButtons(true);
                    }
                }
            }

            @Override // android.content.ComponentCallbacks
            public final void onLowMemory() {
            }
        };
        ?? r7 = new BroadcastReceiver() { // from class: com.android.systemui.statusbar.notification.SubscreenDeviceModelB5$broadcastReceiver$1
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                SubscreenNotificationDetailAdapter subscreenNotificationDetailAdapter;
                Log.d("S.S.N.", "receive " + intent.getAction());
                if (Intrinsics.areEqual(intent.getAction(), PopupUIUtil.ACTION_CLOSE_SYSTEM_DIALOGS)) {
                    if (Intrinsics.areEqual(intent.getStringExtra("reason"), "homekey")) {
                        SubscreenSubRoomNotification subscreenSubRoomNotification = this.this$0.mSubRoomNotification;
                        if (subscreenSubRoomNotification != null && (subscreenNotificationDetailAdapter = subscreenSubRoomNotification.mNotificationDetailAdapter) != null) {
                            subscreenNotificationDetailAdapter.dismissReplyButtons(true);
                        }
                        this.this$0.closeFullscreenFullPopupWindow();
                        this.this$0.isHomeKeyClicked = true;
                        return;
                    }
                    return;
                }
                if (Intrinsics.areEqual(intent.getAction(), "com.samsung.android.action.UNLOCK_NOTIFICATION_PENDING_INTENT")) {
                    SubscreenDeviceModelB5 subscreenDeviceModelB5 = this.this$0;
                    if (subscreenDeviceModelB5.mIsFolded) {
                        return;
                    }
                    subscreenDeviceModelB5.mUnlockNotificationPendingIntentItemKey = intent.getStringExtra("key");
                    return;
                }
                if (Intrinsics.areEqual(intent.getAction(), "com.samsung.android.action.INTELLIGENCE_SERVICE_SETTINGS_START_INTENT")) {
                    if (this.this$0.mIsFolded) {
                        return;
                    }
                    Bundle bundle = new Bundle();
                    bundle.putBoolean("needToShowFTU", this.this$0.needToShowFTU);
                    Intent intent2 = new Intent();
                    intent2.setFlags(335544320);
                    intent2.setClassName("com.android.systemui", "com.android.systemui.statusbar.notification.SubscreenNotificationIntelligenceStartActivity");
                    intent2.putExtras(bundle);
                    this.this$0.mContext.startActivityAsUser(intent2, UserHandle.CURRENT);
                    return;
                }
                if (!Intrinsics.areEqual(intent.getAction(), "com.samsung.android.action.INTELLIGENCE_SERVICE_PROCESSING_ONLINE_INTENT") || this.this$0.mIsFolded) {
                    return;
                }
                Intent intent3 = new Intent();
                intent3.setFlags(335544320);
                intent3.setAction("com.samsung.android.settings.action.INTELLIGENCE_SERVICE_GLOBAL_SETTINGS");
                intent3.setPackage(KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG);
                Bundle bundle2 = new Bundle();
                bundle2.putString(":settings:fragment_args_key", "prevent_online_processing");
                intent3.putExtra(":settings:show_fragment_args", bundle2);
                this.this$0.mContext.startActivityAsUser(intent3, UserHandle.CURRENT);
            }
        };
        this.broadcastReceiver = r7;
        ?? r8 = new BroadcastReceiver() { // from class: com.android.systemui.statusbar.notification.SubscreenDeviceModelB5$pkgBroadcastReceiver$1
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                String dataString;
                Log.d("S.S.N.", "receive " + intent.getAction());
                if ((Intrinsics.areEqual(intent.getAction(), "android.intent.action.PACKAGE_ADDED") || Intrinsics.areEqual(intent.getAction(), "android.intent.action.PACKAGE_REPLACED") || Intrinsics.areEqual(intent.getAction(), "android.intent.action.PACKAGE_REMOVED")) && (dataString = intent.getDataString()) != null && StringsKt__StringsKt.contains(dataString, this.this$0.SR_LLM_PACKAGE_NAME, false)) {
                    Log.d("S.S.N.", "package intent received - loadOnDeviceMetaData again");
                    SubscreenDeviceModelB5 subscreenDeviceModelB5 = this.this$0;
                    subscreenDeviceModelB5.metaData = null;
                    subscreenDeviceModelB5.loadOnDeviceMetaData();
                }
            }
        };
        this.pkgBroadcastReceiver = r8;
        this.aodTspUpdateReceiver = new BroadcastReceiver() { // from class: com.android.systemui.statusbar.notification.SubscreenDeviceModelB5$aodTspUpdateReceiver$1
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                Integer numValueOf = intent != null ? Integer.valueOf(intent.getIntExtra("info", -1)) : null;
                float[] floatArrayExtra = intent != null ? intent.getFloatArrayExtra("location") : null;
                Log.d("S.S.N.", "aodTspUpdateReceiver onReceive() action = " + (intent != null ? intent.getAction() : null) + ", info = " + numValueOf);
                if (numValueOf == null || numValueOf.intValue() != 11) {
                    Log.d("S.S.N.", "aodTspUpdateReceiver onReceive() return - not double tap");
                    return;
                }
                if (floatArrayExtra != null) {
                    SubscreenDeviceModelB5 subscreenDeviceModelB5 = this.this$0;
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
                    SeslColorSpectrumView$$ExternalSyntheticOutline0.m(f2, "S.S.N.", sb);
                    subscreenDeviceModelB5.mIsContentScroll = true;
                    subscreenDeviceModelB5.detailClicked(subscreenDeviceModelB5.currentPresentationEntry);
                }
            }
        };
        this.mSettingsListener = new SettingsHelper.OnChangedCallback() { // from class: com.android.systemui.statusbar.notification.SubscreenDeviceModelB5$mSettingsListener$1
            @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
            public final void onChanged(Uri uri) {
                if (Intrinsics.areEqual(uri, Settings.Global.getUriFor(SettingsHelper.INDEX_SUGGESTION_RESPONSES))) {
                    SubscreenDeviceModelB5 subscreenDeviceModelB5 = this.this$0;
                    boolean zIsSuggestResponsesEnabled = subscreenDeviceModelB5.getMSettingsHelper().isSuggestResponsesEnabled();
                    boolean z = subscreenDeviceModelB5.isSuggestResponsesEnabled;
                    if (z && !zIsSuggestResponsesEnabled && !subscreenDeviceModelB5.suggestedRepliesDisabledByUser) {
                        KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0.m("suggested replies changed ", " -> ", "S.S.N.", z, zIsSuggestResponsesEnabled);
                        subscreenDeviceModelB5.suggestedRepliesDisabledByUser = true;
                        Prefs.putBoolean(((UserTrackerImpl) subscreenDeviceModelB5.mUserContextProvider).getUserContext(), "NotiUserDisabledSuggestedReplies", true);
                    }
                    subscreenDeviceModelB5.isSuggestResponsesEnabled = zIsSuggestResponsesEnabled;
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
        context.registerReceiverAsUser(r7, userHandle, intentFilter, null, null, 2);
        IntentFilter intentFilter2 = new IntentFilter();
        intentFilter2.addAction("android.intent.action.PACKAGE_ADDED");
        intentFilter2.addAction("android.intent.action.PACKAGE_REPLACED");
        intentFilter2.addAction("android.intent.action.PACKAGE_REMOVED");
        intentFilter2.addDataScheme("package");
        context.registerReceiverAsUser(r8, userHandle, intentFilter2, null, null, 2);
        KeyguardStateController keyguardStateController = (KeyguardStateController) Dependency.sDependency.getDependencyInner(KeyguardStateController.class);
        this.mKeyguardStateController = keyguardStateController;
        if (keyguardStateController != null) {
            ((KeyguardStateControllerImpl) keyguardStateController).addCallback(new KeyguardStateController.Callback() { // from class: com.android.systemui.statusbar.notification.SubscreenDeviceModelB5$initKeyguardStateConroller$1
                @Override // com.android.systemui.statusbar.policy.KeyguardStateController.Callback
                public final void onKeyguardShowingChanged() {
                    KeyguardStateController keyguardStateController2;
                    NotificationEntry entry;
                    NotificationEntry notificationEntry;
                    NotificationEntry notificationEntry2;
                    SubscreenParentItemViewHolder subscreenParentItemViewHolder;
                    SubscreenParentItemViewHolder subscreenParentItemViewHolder2;
                    SubscreenNotificationInfo subscreenNotificationInfo;
                    ExpandableNotificationRow expandableNotificationRow;
                    SubscreenSubRoomNotification subscreenSubRoomNotification;
                    SubscreenNotificationDetailAdapter subscreenNotificationDetailAdapter;
                    SubscreenNotificationDetailAdapter subscreenNotificationDetailAdapter2;
                    KeyguardStateController keyguardStateController3;
                    SubscreenDeviceModelB5 subscreenDeviceModelB5 = this.this$0;
                    SubscreenNotificationDialog subscreenNotificationDialog = subscreenDeviceModelB5.mDialog;
                    if (subscreenNotificationDialog != null) {
                        subscreenNotificationDialog.dismiss();
                    }
                    if (subscreenDeviceModelB5.mIsFolded) {
                        KeyguardStateController keyguardStateController4 = subscreenDeviceModelB5.mKeyguardStateController;
                        Boolean boolValueOf = keyguardStateController4 != null ? Boolean.valueOf(((KeyguardStateControllerImpl) keyguardStateController4).mSecure) : null;
                        KeyguardStateController keyguardStateController5 = subscreenDeviceModelB5.mKeyguardStateController;
                        Boolean boolValueOf2 = keyguardStateController5 != null ? Boolean.valueOf(((KeyguardStateControllerImpl) keyguardStateController5).mShowing) : null;
                        KeyguardStateController keyguardStateController6 = subscreenDeviceModelB5.mKeyguardStateController;
                        Boolean boolValueOf3 = keyguardStateController6 != null ? Boolean.valueOf(keyguardStateController6.isUnlocked()) : null;
                        SubscreenDeviceModelB5.KeyguardActionInfo keyguardActionInfo = subscreenDeviceModelB5.mKeyguardActionInfo;
                        Log.d("S.S.N.", " onKeyguardShowingChanged() isMethodSecure : " + boolValueOf + ", isShowing: " + boolValueOf2 + ", isUnlocked : " + boolValueOf3 + ", getAction() : " + (keyguardActionInfo != null ? Integer.valueOf(keyguardActionInfo.action) : null));
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
                            Integer numValueOf = keyguardActionInfo3 != null ? Integer.valueOf(keyguardActionInfo3.action) : null;
                            if (numValueOf != null && numValueOf.intValue() == 4) {
                                SubscreenDeviceModelB5.KeyguardActionInfo keyguardActionInfo4 = subscreenDeviceModelB5.mKeyguardActionInfo;
                                if ((keyguardActionInfo4 != null ? keyguardActionInfo4.entry : null) != null) {
                                    boolean zClickKnoxItem = subscreenDeviceModelB5.clickKnoxItem(keyguardActionInfo4 != null ? keyguardActionInfo4.entry : null);
                                    if (zClickKnoxItem) {
                                        EmergencyButtonController$$ExternalSyntheticOutline0.m(" isClickedKnoxItem :", "S.S.N.", zClickKnoxItem);
                                        SubscreenDeviceModelB5.KeyguardActionInfo keyguardActionInfo5 = subscreenDeviceModelB5.mKeyguardActionInfo;
                                        subscreenDeviceModelB5.dismissImmediately(keyguardActionInfo5 != null ? keyguardActionInfo5.entry : null);
                                    } else {
                                        SubscreenDeviceModelB5.KeyguardActionInfo keyguardActionInfo6 = subscreenDeviceModelB5.mKeyguardActionInfo;
                                        subscreenDeviceModelB5.detailClicked(keyguardActionInfo6 != null ? keyguardActionInfo6.entry : null);
                                    }
                                }
                            } else if (numValueOf != null && numValueOf.intValue() == 1) {
                                SubscreenDeviceModelB5.KeyguardActionInfo keyguardActionInfo7 = subscreenDeviceModelB5.mKeyguardActionInfo;
                                if ((keyguardActionInfo7 != null ? keyguardActionInfo7.subscreenParentItemViewHolder : null) != null) {
                                    boolean zClickKnoxItem2 = subscreenDeviceModelB5.clickKnoxItem((keyguardActionInfo7 == null || (subscreenParentItemViewHolder2 = keyguardActionInfo7.subscreenParentItemViewHolder) == null || (subscreenNotificationInfo = subscreenParentItemViewHolder2.mInfo) == null || (expandableNotificationRow = subscreenNotificationInfo.mRow) == null) ? null : expandableNotificationRow.mEntry);
                                    if (zClickKnoxItem2) {
                                        EmergencyButtonController$$ExternalSyntheticOutline0.m(" isClickedKnoxItem :", "S.S.N.", zClickKnoxItem2);
                                    } else {
                                        SubscreenDeviceModelB5.KeyguardActionInfo keyguardActionInfo8 = subscreenDeviceModelB5.mKeyguardActionInfo;
                                        if (keyguardActionInfo8 != null && (subscreenParentItemViewHolder = keyguardActionInfo8.subscreenParentItemViewHolder) != null) {
                                            SubscreenSubRoomNotification subscreenSubRoomNotification3 = subscreenDeviceModelB5.mSubRoomNotification;
                                            subscreenParentItemViewHolder.animateClickNotification(subscreenSubRoomNotification3 != null ? subscreenSubRoomNotification3.mNotificationAnimatorManager : null, subscreenSubRoomNotification3, false);
                                        }
                                    }
                                }
                            } else if (numValueOf != null && numValueOf.intValue() == 5) {
                                SubscreenDeviceModelB5.KeyguardActionInfo keyguardActionInfo9 = subscreenDeviceModelB5.mKeyguardActionInfo;
                                if (keyguardActionInfo9 == null || (notificationEntry2 = keyguardActionInfo9.liveEntry) == null) {
                                    return;
                                }
                                subscreenDeviceModelB5.launchApp(notificationEntry2);
                                Log.d("S.S.N.", "clickLiveNotification - Unlock and launch app");
                            } else if (numValueOf != null && numValueOf.intValue() == 2) {
                                System.out.println((Object) "ACTION_KEYGUARD_BIO_LIST_HIDE_CONTENT");
                            } else if (numValueOf != null && numValueOf.intValue() == 3) {
                                SubscreenDeviceModelB5.KeyguardActionInfo keyguardActionInfo10 = subscreenDeviceModelB5.mKeyguardActionInfo;
                                Context context2 = keyguardActionInfo10 != null ? keyguardActionInfo10.context : null;
                                context2.getClass();
                                SubscreenDeviceModelB5.KeyguardActionInfo keyguardActionInfo11 = subscreenDeviceModelB5.mKeyguardActionInfo;
                                SubscreenNotificationDetailAdapter.ItemViewHolder itemViewHolder = keyguardActionInfo11 != null ? keyguardActionInfo11.detailAdapterItemViewHolder : null;
                                itemViewHolder.getClass();
                                SubscreenDeviceModelB5.access$showReplyActivity(subscreenDeviceModelB5, context2, itemViewHolder);
                            } else if (numValueOf != null && numValueOf.intValue() == 6) {
                                SubscreenDeviceModelB5.KeyguardActionInfo keyguardActionInfo12 = subscreenDeviceModelB5.mKeyguardActionInfo;
                                boolean zClickKnoxItem3 = subscreenDeviceModelB5.clickKnoxItem(keyguardActionInfo12 != null ? keyguardActionInfo12.entry : null);
                                if (zClickKnoxItem3) {
                                    EmergencyButtonController$$ExternalSyntheticOutline0.m(" ACTION_KEYGUARD_CLICK_OPEN_APP_BTN isClickedKnoxItem :", "S.S.N.", zClickKnoxItem3);
                                } else {
                                    SubscreenDeviceModelB5.KeyguardActionInfo keyguardActionInfo13 = subscreenDeviceModelB5.mKeyguardActionInfo;
                                    if (keyguardActionInfo13 != null && (notificationEntry = keyguardActionInfo13.entry) != null && subscreenDeviceModelB5.launchApp(notificationEntry) && subscreenDeviceModelB5.isShownDetail()) {
                                        SubscreenNotificationReplyActivity subscreenNotificationReplyActivity = subscreenDeviceModelB5.mController.replyActivity;
                                        if (subscreenNotificationReplyActivity != null) {
                                            subscreenNotificationReplyActivity.finish();
                                        }
                                        subscreenDeviceModelB5.hideDetailNotificationAnimated(0, true);
                                    }
                                }
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
                        EmergencyButtonController$$ExternalSyntheticOutline0.m("onKeyguardShowingChanged - mIsClickedPopupKeyguardUnlockShowing : ", "S.S.N.", z2);
                        return;
                    }
                    SubscreenDeviceModelB5.KeyguardActionInfo keyguardActionInfo14 = subscreenDeviceModelB5.mKeyguardActionInfo;
                    if (keyguardActionInfo14 != null) {
                        keyguardActionInfo14.action = 0;
                        keyguardActionInfo14.subscreenParentItemViewHolder = null;
                        keyguardActionInfo14.context = null;
                        keyguardActionInfo14.entry = null;
                    }
                }
            });
        }
        this.mKeyguardActionInfo = new KeyguardActionInfo();
        updateSmartReplyVariables();
        this.suggestedRepliesDisabledByUser = Prefs.getBoolean(((UserTrackerImpl) this.mUserContextProvider).getUserContext(), "NotiUserDisabledSuggestedReplies", false);
        this.onDeviceLanguageList = new ArrayList();
        this.SR_LLM_PACKAGE_NAME = "com.samsung.android.offline.languagemodel";
        this.SUPPORT_FUNCTION_SMART_REPLY = "SmartReply";
        this.DISPLAY_LANG_CODE_DELIMITER = "-";
        this.mSmartReplyResult = -1;
        this.mSrPromptProcessor = z ? new MultiPromptProcessor(this.mContext, getMSettingsHelper()) : new SrPromptProcessor(this.mContext);
        this.mSrResponseCallback = new SubscreenDeviceModelB5$mSrResponseCallback$1(this);
    }

    public static final boolean access$isSupportableLanguage(SubscreenDeviceModelB5 subscreenDeviceModelB5, String str) {
        subscreenDeviceModelB5.getClass();
        if (NotiRune.NOTI_SUBSCREEN_SUPPORT_SMART_REPLY_AI_FOR_CHINA) {
            return true;
        }
        List list = subscreenDeviceModelB5.onDeviceLanguageList;
        if (list != null) {
            ArrayList arrayList = (ArrayList) list;
            int size = arrayList.size();
            int i = 0;
            while (i < size) {
                Object obj = arrayList.get(i);
                i++;
                if (((LlmLanguage) obj).language.equals(str)) {
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
            final ActivityOptions activityOptionsMakeBasic = ActivityOptions.makeBasic();
            activityOptionsMakeBasic.setLaunchDisplayId(display.getDisplayId());
            activityOptionsMakeBasic.setForceLaunchWindowingMode(1);
            SubscreenSubRoomNotification subscreenSubRoomNotification = subscreenDeviceModelB5.mSubRoomNotification;
            if (subscreenSubRoomNotification != null) {
                ObjectAnimator duration = ObjectAnimator.ofFloat(subscreenSubRoomNotification.mSubscreenMainLayout, (Property<LinearLayout, Float>) View.ALPHA, 1.0f, 0.0f).setDuration(300L);
                duration.getClass();
                duration.addListener(new Animator.AnimatorListener() { // from class: com.android.systemui.statusbar.notification.SubscreenDeviceModelB5$showReplyActivity$lambda$22$lambda$21$lambda$20$$inlined$doOnEnd$1
                    @Override // android.animation.Animator.AnimatorListener
                    public final void onAnimationEnd(Animator animator) {
                        context.startActivity(intent, activityOptionsMakeBasic.toBundle());
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
        view.getClass();
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
        DateTimeView dateTimeViewFindViewById = view.findViewById(R.id.detail_clock);
        if (dateTimeViewFindViewById != null) {
            dateTimeViewFindViewById.setVisibility(0);
        }
        if (dateTimeViewFindViewById != null) {
            dateTimeViewFindViewById.setTime(j);
        }
        itemViewHolder.mBodyLayoutString = itemViewHolder.mBodyLayoutString + ((Object) (dateTimeViewFindViewById != null ? dateTimeViewFindViewById.getText() : null));
    }

    public static boolean isCallNotification(NotificationEntry notificationEntry) {
        Notification notification2;
        if (ArraysKt___ArraysKt.indexOf(new String[]{"com.skt.prod.dialer", "com.samsung.android.incallui"}, notificationEntry.mSbn.getPackageName()) < 0 || !notificationEntry.mSbn.isOngoing()) {
            return false;
        }
        StatusBarNotification statusBarNotification = notificationEntry.mSbn;
        return "call".equals((statusBarNotification == null || (notification2 = statusBarNotification.getNotification()) == null) ? null : notification2.category);
    }

    public static void showErrorMessageWithAnim(View view) {
        Log.d("S.S.N.", "showErrorMessageWithAnim - B5");
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
                MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("clickAdapterItem B5 - put fullscreenIntent : ", notificationEntry2.mKey, "S.S.N.");
                this.mFullScreenIntentEntries.put(notificationEntry2.mKey, notificationEntry2);
                makeSubScreenNotification(notificationEntry2);
                showSubscreenNotification();
                return;
            }
        }
        boolean zIsKeyguardStats = isKeyguardStats();
        KeyguardActionInfo keyguardActionInfo = this.mKeyguardActionInfo;
        if (zIsKeyguardStats && subscreenParentItemViewHolder.mInfo.mRow.needsRedaction()) {
            showBouncer(context, subscreenParentItemViewHolder.mInfo.mRow.mEntry);
            if (keyguardActionInfo != null) {
                keyguardActionInfo.action = 1;
                keyguardActionInfo.subscreenParentItemViewHolder = subscreenParentItemViewHolder;
                return;
            }
            return;
        }
        if (isKeyguardUnlockShowing()) {
            ((KeyguardManager) context.getSystemService("keyguard")).semDismissKeyguard();
            if (keyguardActionInfo != null) {
                keyguardActionInfo.action = 1;
                keyguardActionInfo.subscreenParentItemViewHolder = subscreenParentItemViewHolder;
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
        if (notificationEntry != null) {
            boolean z = notificationEntry.isOngoingActivity() && notificationEntry.isPromotedState();
            boolean zIsKnoxSecurity = isKnoxSecurity(notificationEntry);
            KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0.m(" clickKnoxItem - isKnoxSecurity : ", ", isLiveNoti: ", "S.S.N.", zIsKnoxSecurity, z);
            if (zIsKnoxSecurity && (isLaunchApp(notificationEntry) || z)) {
                Notification notification2 = notificationEntry.mSbn.getNotification();
                PendingIntent pendingIntent = notification2 != null ? notification2.contentIntent : null;
                SubscreenSubRoomNotification subscreenSubRoomNotification = this.mSubRoomNotification;
                if (subscreenSubRoomNotification != null && (stateChangeListener = subscreenSubRoomNotification.mStateChangeListener) != null) {
                    stateChangeListener.requestCoverPopup(pendingIntent, notificationEntry.mKey);
                }
                return true;
            }
        }
        return false;
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final void clickLiveNotification(Context context, SubscreenParentItemViewHolder subscreenParentItemViewHolder, OngoingActivityData ongoingActivityData) {
        if (ongoingActivityData == null) {
            return;
        }
        KeyguardManager keyguardManager = (KeyguardManager) context.getSystemService("keyguard");
        NotificationEntry notificationEntry = ongoingActivityData.mNotificationEntry;
        Log.d("S.S.N.", "clickLiveNotification - " + notificationEntry.mKey);
        if (!notificationEntry.isPromotedState()) {
            Log.d("S.S.N.", "clickLiveNotification - it's not promoted");
            clickAdapterItem(context, subscreenParentItemViewHolder);
            return;
        }
        if (isKnoxSecurity(notificationEntry)) {
            Log.d("S.S.N.", "clickLiveNotification - it's knox security");
            clickAdapterItem(context, subscreenParentItemViewHolder);
            return;
        }
        boolean zIsLaunchApp = isLaunchApp(notificationEntry);
        PendingIntent pendingIntent = ongoingActivityData.mNowbarPendingIntentOnSubScreen;
        if (pendingIntent == null && !zIsLaunchApp) {
            Log.d("S.S.N.", "clickLiveNotification - showCoverToast");
            PendingIntent pendingIntent2 = notificationEntry.mSbn.getNotification().contentIntent;
            Intent intent = new Intent();
            intent.putExtra("showCoverToast", true);
            intent.putExtra("ignoreKeyguardState", true);
            intent.putExtra("notificationKey", notificationEntry.mKey);
            keyguardManager.semSetPendingIntentAfterUnlock(pendingIntent2, intent);
            return;
        }
        if (pendingIntent != null) {
            ListPopupWindow$$ExternalSyntheticOutline0.m(startNotificationIntent(pendingIntent), "clickLiveNotification - start widget intent. result = ", "S.S.N.");
            return;
        }
        boolean zIsKeyguardStats = isKeyguardStats();
        KeyguardActionInfo keyguardActionInfo = this.mKeyguardActionInfo;
        if (zIsKeyguardStats && (subscreenParentItemViewHolder.mInfo.mRow.needsRedaction() || zIsLaunchApp)) {
            showBouncer(context, notificationEntry);
            if (keyguardActionInfo != null) {
                keyguardActionInfo.action = 5;
                keyguardActionInfo.liveEntry = notificationEntry;
                return;
            }
            return;
        }
        if (!isKeyguardUnlockShowing()) {
            if (clickKnoxItem(notificationEntry)) {
                Log.d("S.S.N.", "clickLiveNotification - clickKnoxItem is true");
                return;
            } else {
                launchApp(notificationEntry);
                return;
            }
        }
        keyguardManager.semDismissKeyguard();
        if (keyguardActionInfo != null) {
            keyguardActionInfo.action = 5;
            keyguardActionInfo.liveEntry = notificationEntry;
        }
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final boolean clickLiveNotificationActionButtonForActivity(String str, PendingIntent pendingIntent) {
        if (!this.mIsFolded) {
            return true;
        }
        boolean zIsPackageEnabledForCoverLauncher = ActivityTaskManager.getService().isPackageEnabledForCoverLauncher(str, UserHandle.semGetMyUserId());
        if (!zIsPackageEnabledForCoverLauncher) {
            requestOpenPhonePopup(pendingIntent);
        }
        return zIsPackageEnabledForCoverLauncher;
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
    public final int convertAdapterPositionToInfoIndex(int i) {
        return this.mController.isZenMode() ? i - 1 : i;
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final int convertInfoIndexToAdapterPosition(int i) {
        return this.mController.isZenMode() ? i + 1 : i;
    }

    public int coverCutoutSize() {
        Context context = this.mDisplayContext;
        if (context == null) {
            context = null;
        }
        return context.getResources().getDimensionPixelSize(R.dimen.subscreen_noti_cut_out_size_b5);
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
        boolean zIsKeyguardStats = isKeyguardStats();
        KeyguardActionInfo keyguardActionInfo = this.mKeyguardActionInfo;
        if (zIsKeyguardStats) {
            notificationEntry.getClass();
            if (notificationEntry.row.needsRedaction()) {
                showBouncer(this.mContext, notificationEntry);
                if (keyguardActionInfo != null) {
                    keyguardActionInfo.action = 4;
                    keyguardActionInfo.entry = notificationEntry;
                    return;
                }
                return;
            }
        }
        if (isKeyguardUnlockShowing()) {
            ((KeyguardManager) this.mContext.getSystemService("keyguard")).semDismissKeyguard();
            this.mIsClickedPopupKeyguardUnlockShowing = true;
            if (keyguardActionInfo != null) {
                keyguardActionInfo.action = 4;
                keyguardActionInfo.entry = notificationEntry;
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
        View viewFindFocus;
        boolean z = true;
        boolean z2 = keyEvent.getKeyCode() == 20 || keyEvent.getKeyCode() == 19 || keyEvent.getKeyCode() == 61;
        int action = keyEvent.getAction();
        if (action != 0) {
            if (action == 1) {
                if (keyEvent.getKeyCode() == 4 && this.mIsNaviBarBackButtonClicked) {
                    return performBackClick();
                }
                if (z2) {
                    keyEvent.getKeyCode();
                    if (isShownDetail() && (subscreenSubRoomNotification = this.mSubRoomNotification) != null && (subscreenRecyclerView2 = subscreenSubRoomNotification.mNotificationRecyclerView) != null && (viewFindFocus = subscreenRecyclerView2.findFocus()) != null) {
                        int[] iArr = new int[2];
                        viewFindFocus.getLocationOnScreen(iArr);
                        int measuredHeight = viewFindFocus.getMeasuredHeight() + iArr[1];
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
                        if (i < mainHeaderViewHeight) {
                            subscreenRecyclerView2.smoothScrollBy(0, i - mainHeaderViewHeight, false);
                            return false;
                        }
                    }
                }
            }
        } else {
            if (keyEvent.getKeyCode() == 4) {
                this.mIsNaviBarBackButtonClicked = true;
                if (!isShownDetail() && !isShownGroup()) {
                    z = false;
                }
                EmergencyButtonController$$ExternalSyntheticOutline0.m("dispatchKeyEvent() - navi back click, ret: ", "S.S.N.", z);
                return z;
            }
            if (z2) {
                int keyCode = keyEvent.getKeyCode();
                if (!isShownDetail()) {
                    if (keyCode != 20 && keyCode != 61) {
                        z = false;
                    }
                    SubscreenSubRoomNotification subscreenSubRoomNotification2 = this.mSubRoomNotification;
                    if (subscreenSubRoomNotification2 != null && (subscreenRecyclerView = subscreenSubRoomNotification2.mNotificationRecyclerView) != null) {
                        subscreenRecyclerView.scrollToPosition(RecyclerView.getChildAdapterPosition(subscreenRecyclerView.getFocusedChild()) + (z ? 2 : -2));
                    }
                }
            }
        }
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
            imageView.setEnabled((str.equals("unsupportedLanguage") || str.equals("emptyMessage")) ? this.smartReplyStatus != 2 : z);
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
        SubscreenDeviceModelB5$componentCallbacks$1 subscreenDeviceModelB5$componentCallbacks$1 = this.componentCallbacks;
        String str = null;
        if (z) {
            this.mIsClickedPopupKeyguardUnlockShowing = false;
            KeyguardActionInfo keyguardActionInfo = this.mKeyguardActionInfo;
            if (keyguardActionInfo != null) {
                keyguardActionInfo.action = 0;
                keyguardActionInfo.subscreenParentItemViewHolder = null;
                keyguardActionInfo.context = null;
                keyguardActionInfo.entry = null;
            }
            this.mUnlockNotificationPendingIntentItemKey = null;
            updateSmartReplyVariables();
            this.lastRotation = 0;
            Context context = this.windowContext;
            if (context != null) {
                context.registerComponentCallbacks(subscreenDeviceModelB5$componentCallbacks$1);
            }
        } else {
            if ((getTopActivityName().equals("com.android.systemui.subscreen.SubHomeActivity") && (itemViewHolder = this.detailViewHolder) != null && itemViewHolder.itemView.hasWindowFocus()) || ((subscreenNotificationReplyActivity = this.mController.replyActivity) != null && subscreenNotificationReplyActivity.hasWindowFocus())) {
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
            Context context2 = this.windowContext;
            if (context2 != null) {
                context2.unregisterComponentCallbacks(subscreenDeviceModelB5$componentCallbacks$1);
            }
        }
        super.foldStateChanged(z);
    }

    /* JADX WARN: Removed duplicated region for block: B:79:0x00ec  */
    /* JADX WARN: Removed duplicated region for block: B:92:0x012a A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:93:0x012b  */
    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final int getDetailAdapterAutoScrollCurrentPositionByReceive(View view) throws Resources.NotFoundException {
        LinearLayout linearLayout;
        View view2;
        float y;
        float f;
        SubscreenNotificationDetailAdapter subscreenNotificationDetailAdapter;
        SubscreenRecyclerView subscreenRecyclerView;
        SubscreenRecyclerView subscreenRecyclerView2;
        SubscreenRecyclerView subscreenRecyclerView3;
        SubscreenNotificationDetailAdapter subscreenNotificationDetailAdapter2;
        SubscreenNotificationDetailAdapter subscreenNotificationDetailAdapter3;
        SubscreenSubRoomNotification subscreenSubRoomNotification = this.mSubRoomNotification;
        if (subscreenSubRoomNotification != null && !subscreenSubRoomNotification.mIsShownDetail) {
            Log.d("S.S.N.", "getDetailAdapterAutoScrollCurrentPositionByReceive B5 - return - mSubRoomNotification?.isShownDetail is false");
            return 0;
        }
        if (this.mController.replyActivity != null) {
            Log.d("S.S.N.", "getDetailAdapterAutoScrollCurrentPositionByReceive B5 - return - show reply activity");
            return 0;
        }
        if (((subscreenSubRoomNotification == null || (subscreenNotificationDetailAdapter3 = subscreenSubRoomNotification.mNotificationDetailAdapter) == null) ? null : subscreenNotificationDetailAdapter3.mSelectNotificationInfo) == null) {
            if (subscreenSubRoomNotification != null && (subscreenNotificationDetailAdapter2 = subscreenSubRoomNotification.mNotificationDetailAdapter) != null) {
                obj = subscreenNotificationDetailAdapter2.mSelectNotificationInfo;
            }
            Log.d("S.S.N.", "getDetailAdapterAutoScrollCurrentPositionByReceive B5 - return - value is null  View : " + view + ", selectNotificationInfo : " + obj);
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
                Context context = this.mDisplayContext;
                if (context == null) {
                    context = null;
                }
                int dimensionPixelSize = context.getResources().getDimensionPixelSize(R.dimen.subscreen_noti_cut_out_size_b5);
                SubscreenSubRoomNotification subscreenSubRoomNotification3 = this.mSubRoomNotification;
                Integer numValueOf = (subscreenSubRoomNotification3 == null || (subscreenRecyclerView = subscreenSubRoomNotification3.mNotificationRecyclerView) == null) ? null : Integer.valueOf(subscreenRecyclerView.computeVerticalScrollOffset());
                numValueOf.getClass();
                int iIntValue = numValueOf.intValue();
                int dispalyHeight = ((getDispalyHeight() - getMainHeaderViewHeight()) - dimensionPixelSize) + iIntValue;
                SubscreenSubRoomNotification subscreenSubRoomNotification4 = this.mSubRoomNotification;
                SubscreenNotificationDetailAdapter.ScrollInfo scrollInfo = (subscreenSubRoomNotification4 == null || (subscreenNotificationDetailAdapter = subscreenSubRoomNotification4.mNotificationDetailAdapter) == null) ? null : subscreenNotificationDetailAdapter.mScrollInfo;
                if (scrollInfo == null || scrollInfo.mPrevFirstHistoryView == null) {
                    ViewGroupKt.get(subscreenParentDetailItemViewHolder.mContentLayout, 0);
                }
                if (scrollInfo == null || (view2 = scrollInfo.mPrevLastHistoryView) == null) {
                    view2 = ViewGroupKt.get(subscreenParentDetailItemViewHolder.mContentLayout, childCount - 1);
                }
                if ((scrollInfo != null ? scrollInfo.mPrevFirstHistoryView : null) == null) {
                    Log.d("S.S.N.", "getDetailAdapterAutoScrollCurrentPositionByReceive B5 - prevItem is null,scrollInfo?.prevFirstHistoryView :" + (scrollInfo != null ? scrollInfo.mPrevFirstHistoryView : null) + ", scrollInfo?.prevLastHistoryView : " + (scrollInfo != null ? scrollInfo.mPrevLastHistoryView : null));
                    y = view2.getY() + (view2.getHeight() / 3);
                    f = dispalyHeight;
                    if (f <= y && iIntValue < y) {
                        return 3;
                    }
                    if (f >= y) {
                        return 1;
                    }
                    if (iIntValue > y) {
                        return 2;
                    }
                } else {
                    if ((scrollInfo != null ? scrollInfo.mPrevLastHistoryView : null) == null) {
                    }
                    y = view2.getY() + (view2.getHeight() / 3);
                    f = dispalyHeight;
                    if (f <= y) {
                    }
                    if (f >= y) {
                    }
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
    public final View getDetailAdapterLayout(ViewGroup viewGroup, int i, Context context) {
        return LayoutInflater.from(context).inflate(i != 0 ? i != 1 ? -1 : R.layout.subscreen_notification_detail_adapter_text_item_b5 : R.layout.subscreen_notification_detail_adapter_item_b5, viewGroup, false);
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final int getDetailAdapterReplyWordResource() {
        return R.layout.subscreen_notification_detail_adapter_content_layout_item_reply_word_b5;
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public int getDispalyHeight() {
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
        List listSplit$default = StringsKt__StringsKt.split$default(str, new String[]{str2}, 0, 6);
        String displayName = new Locale((String) listSplit$default.get(0), (String) listSplit$default.get(1)).getDisplayName();
        displayName.getClass();
        return displayName;
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final int getFullPopupWindowType() {
        return this.mFullScreenIntentEntries.isEmpty() ? 2026 : 2040;
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public View getGroupAdapterLayout(ViewGroup viewGroup, int i, Context context) {
        View viewInflate = LayoutInflater.from(context).inflate(i != 0 ? i != 1 ? i != 2 ? i != 4 ? i != 5 ? i != 6 ? -1 : R.layout.subscreen_notification_group_adapter_ongoing_view_b5 : R.layout.subscreen_notification_group_adapter_custom_view_b5 : R.layout.subscreen_notification_group_adapter_hide_content_b5 : R.layout.subscreen_notification_adapter_header_b5 : R.layout.subscreen_notification_adapter_clear_all_footer_b5 : R.layout.subscreen_notification_group_adapter_item_b5, viewGroup, false);
        if (i != 1) {
            Context context2 = this.mDisplayContext;
            if (context2 == null) {
                context2 = null;
            }
            viewInflate.setBackground(context2.getDrawable(R.drawable.subscreen_notification_list_item_bg_selecter_b5));
        }
        return viewInflate;
    }

    public final String getHistoryInfo(SubscreenNotificationDetailAdapter.ItemViewHolder itemViewHolder) {
        StringBuilder sb = new StringBuilder();
        ArrayList arrayList = itemViewHolder.mInfo.mMessageingStyleInfoArray;
        int size = arrayList.size();
        if (!arrayList.isEmpty() && ((SubscreenNotificationInfo.MessagingStyleInfo) arrayList.get(size - 1)).mIsReply) {
            Log.d("S.S.N.", "getHistoryInfo() - this is reply notification. so do not call AI");
            return null;
        }
        this.mPromptSB.setLength(0);
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
                if (!NotiRune.NOTI_SUBSCREEN_SUPPORT_SMART_REPLY_AI_FOR_CHINA || isAiCoreFeaturesEnabled()) {
                    String string = StringsKt__StringsKt.trim(messagingStyleInfo.mContentText).toString();
                    if (string != null && string.length() != 0) {
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

    public final String getIsoCountryCode() throws MissingResourceException {
        try {
            TelephonyManager telephonyManager = (TelephonyManager) this.mContext.getSystemService("phone");
            String simCountryIso = telephonyManager.getSimCountryIso();
            if (simCountryIso == null || simCountryIso.length() == 0) {
                simCountryIso = telephonyManager.getNetworkCountryIso();
            }
            String iSO3Country = new Locale("", simCountryIso).getISO3Country();
            iSO3Country.getClass();
            if (iSO3Country.length() > 0) {
                return iSO3Country;
            }
        } catch (Exception e) {
            EmergencyButton$$ExternalSyntheticOutline0.m("getIsoCountryCode: ", e, "S.S.N.");
        }
        return Locale.getDefault().getISO3Country();
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final int getLayoutInDisplayCutoutMode() {
        return 3;
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final int getListAdapterAddItemCnt() {
        return this.mController.isZenMode() ? 2 : 1;
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final int getListAdapterGroupItemResource() {
        return R.layout.subscreen_notification_list_adapter_group_summary_layout_item_b5;
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public View getListAdapterLayout(ViewGroup viewGroup, int i, Context context) {
        int i2;
        switch (i) {
            case 0:
                i2 = R.layout.subscreen_notification_list_adapter_item_b5;
                break;
            case 1:
                i2 = R.layout.subscreen_notification_adapter_clear_all_footer_b5;
                break;
            case 2:
                i2 = R.layout.subscreen_notification_list_adapter_custom_view_b5;
                break;
            case 3:
                i2 = R.layout.subscreen_notification_adapter_no_notification_b5;
                break;
            case 4:
                i2 = R.layout.subscreen_notification_list_adapter_hide_content_b5;
                break;
            case 5:
                i2 = R.layout.subscreen_notification_list_adapter_group_summary_layout_concept_7;
                break;
            case 6:
                i2 = R.layout.subscreen_notification_list_adapter_ongoing_view_b5;
                break;
            case 7:
                i2 = R.layout.subscreen_notification_list_adapter_dnd_text_layout_item;
                break;
            default:
                i2 = -1;
                break;
        }
        View viewInflate = LayoutInflater.from(context).inflate(i2, viewGroup, false);
        if (i != 1 && i != 3 && i != 6 && i != 5 && i != 7) {
            Context context2 = this.mDisplayContext;
            if (context2 == null) {
                context2 = null;
            }
            viewInflate.setBackground(context2.getDrawable(R.drawable.subscreen_notification_list_item_bg_selecter_b5));
        }
        return viewInflate;
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public int getMainHeaderViewHeight() {
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
        FrameLayout frameLayout;
        LinearLayout linearLayout;
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(view, (Property<View, Float>) View.TRANSLATION_Y, 0.0f, -71.0f);
        ObjectAnimator objectAnimatorOfFloat2 = ObjectAnimator.ofFloat(view, (Property<View, Float>) View.ALPHA, 0.95f, 0.0f);
        if (this.mNotiPopupType == 2) {
            View view2 = this.mPopUpViewLayout;
            if (view2 != null && (linearLayout = (LinearLayout) view2.findViewById(R.id.subscreen_notification_top_popup_layout)) != null) {
                linearLayout.semSetBlurInfo(null);
            }
            View view3 = this.mPopUpViewLayout;
            if (view3 != null && (frameLayout = (FrameLayout) view3.findViewById(R.id.subscreen_notification_top_popup_frame)) != null) {
                frameLayout.setBackground(this.mContext.getDrawable(R.drawable.subscreen_notification_hun_top_background_b5));
            }
        }
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(objectAnimatorOfFloat, objectAnimatorOfFloat2);
        animatorSet.setDuration(200L);
        if (this.popupViewShowing) {
            animatorSet.addListener(this.topPopupAnimationListener);
        }
        return animatorSet;
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final Animator getPopUpViewShowAnimator(View view) {
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(view, (Property<View, Float>) View.TRANSLATION_Y, -71.0f, 0.0f);
        ObjectAnimator objectAnimatorOfFloat2 = ObjectAnimator.ofFloat(view, (Property<View, Float>) View.ALPHA, 0.0f, 0.95f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(objectAnimatorOfFloat, objectAnimatorOfFloat2);
        animatorSet.setDuration(200L);
        animatorSet.addListener(new Animator.AnimatorListener() { // from class: com.android.systemui.statusbar.notification.SubscreenDeviceModelB5$getPopUpViewShowAnimator$lambda$4$$inlined$doOnStart$1
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
        View viewInflate = LayoutInflater.from(subHomeActivity).inflate(R.layout.subscreen_notification_detail_adapter_content_layout_item_reply_button_b5, (ViewGroup) null);
        View viewFindViewById = viewInflate.findViewById(R.id.send);
        if (viewFindViewById != null) {
            viewFindViewById.setEnabled(true);
        }
        if (viewFindViewById != null) {
            viewFindViewById.setAlpha(1.0f);
        }
        return viewInflate;
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
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(view, (Property<View, Float>) View.TRANSLATION_Y, 0.0f, -71.0f);
        ObjectAnimator objectAnimatorOfFloat2 = ObjectAnimator.ofFloat(view, (Property<View, Float>) View.ALPHA, 0.95f, 0.0f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(objectAnimatorOfFloat, objectAnimatorOfFloat2);
        animatorSet.setDuration(200L);
        return animatorSet;
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final Intent getWritingAssistFTUIntent() {
        Intent intent = new Intent("com.samsung.android.honeyboard.intent.action.START_FTU_DIALOG");
        intent.setFlags(268468224);
        intent.setPackage("com.samsung.android.honeyboard");
        intent.putExtra("package", this.mContext.getPackageName());
        return intent;
    }

    public final void handleProgressLayout(boolean z) {
        EmergencyButtonController$$ExternalSyntheticOutline0.m("handleProgressLayout() - show : ", "S.S.N.", z);
        if (z) {
            Runnable runnable = new Runnable() { // from class: com.android.systemui.statusbar.notification.SubscreenDeviceModelB5$handleProgressLayout$runnable$1
                @Override // java.lang.Runnable
                public final void run() {
                    SubscreenDeviceModelB5 subscreenDeviceModelB5 = this.this$0;
                    int i = SubscreenDeviceModelB5.$r8$clinit;
                    subscreenDeviceModelB5.updateVisibilityForSmartReplyLayout(8);
                    LinearLayout linearLayout = this.this$0.progressLayout;
                    if (linearLayout != null) {
                        linearLayout.setVisibility(0);
                    }
                    LottieAnimationView lottieAnimationView = this.this$0.progressingVi;
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
                LinearLayout linearLayout2 = this.this$0.progressLayout;
                if (linearLayout2 != null) {
                    linearLayout2.setVisibility(8);
                }
                LottieAnimationView lottieAnimationView = this.this$0.progressingVi;
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

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final void hideSmartReplyErrorMessage() {
        TextView textView = this.smartReplyErrorMessageView;
        if (textView != null) {
            textView.setAlpha(0.0f);
        }
        TextView textView2 = this.smartReplyErrorMessageView;
        if (textView2 != null) {
            textView2.setVisibility(8);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:59:0x0182  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void inflateSmartReplyAI(String str) {
        List<String> listSubList;
        int size;
        LinearLayout linearLayout;
        LinearLayout linearLayout2;
        LinearLayout linearLayout3;
        final SubscreenNotificationDetailAdapter subscreenNotificationDetailAdapter;
        final SubscreenNotificationDetailAdapter.ItemViewHolder itemViewHolder;
        LinearLayout linearLayout4;
        ImageView imageView;
        LinearLayout linearLayout5;
        Object failure;
        Log.d("S.S.N.", "inflateSmartReplyAI()");
        Integer numValueOf = null;
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
                String string = StringsKt__StringsKt.trim(str2).toString();
                if (string != null && string.length() != 0) {
                    arrayList.add(str2);
                }
            }
            ArrayList arrayList2 = new ArrayList();
            ArrayList arrayList3 = new ArrayList();
            ArrayList arrayList4 = new ArrayList();
            int size2 = arrayList.size();
            int i = 0;
            while (i < size2) {
                Object obj = arrayList.get(i);
                i++;
                arrayList2.add(StringsKt__StringsKt.trim(StringsKt__StringsJVMKt.replace$default(StringsKt__StringsJVMKt.replace$default(StringsKt__StringsJVMKt.replace$default(StringsKt__StringsJVMKt.replace$default(StringsKt__StringsJVMKt.replace$default(StringsKt__StringsJVMKt.replace$default(StringsKt__StringsJVMKt.replace$default(StringsKt__StringsJVMKt.replace$default(StringsKt__StringsJVMKt.replace$default(StringsKt__StringsJVMKt.replace$default(StringsKt__StringsJVMKt.replace$default(StringsKt__StringsJVMKt.replace$default(StringsKt__StringsJVMKt.replace$default(StringsKt__StringsJVMKt.replace$default(StringsKt__StringsJVMKt.replace$default(StringsKt__StringsJVMKt.replace$default(StringsKt__StringsJVMKt.replace$default((String) obj, "~", ""), "!", ""), "@", ""), "#", ""), "$", ""), "%", ""), "^", ""), "&", ""), "*", "("), ")", ""), "`", ""), ",", ""), ".", ""), "-", ""), "_", ""), "\"", ""), " ", "")).toString());
            }
            int size3 = arrayList2.size();
            for (int i2 = 0; i2 < size3; i2++) {
                if (!arrayList4.contains(Integer.valueOf(i2))) {
                    int size4 = arrayList2.size();
                    for (int i3 = i2; i3 < size4; i3++) {
                        if (i2 != i3 && !arrayList4.contains(Integer.valueOf(i3)) && ((String) arrayList2.get(i2)).equals((String) arrayList2.get(i3))) {
                            arrayList4.add(Integer.valueOf(i3));
                        }
                    }
                }
            }
            int size5 = arrayList.size();
            for (int i4 = 0; i4 < size5; i4++) {
                if (arrayList4.size() == 0 || !arrayList4.contains(Integer.valueOf(i4))) {
                    arrayList3.add((String) arrayList.get(i4));
                }
            }
            if (arrayList3.size() == 0) {
                listSubList = null;
            } else {
                int size6 = arrayList3.size() < 3 ? arrayList3.size() : 3;
                try {
                    int i5 = Result.$r8$clinit;
                    failure = arrayList3;
                } catch (Throwable th) {
                    int i6 = Result.$r8$clinit;
                    failure = new Result.Failure(th);
                }
                Object obj2 = EmptyList.INSTANCE;
                boolean z = failure instanceof Result.Failure;
                Object obj3 = failure;
                if (z) {
                    obj3 = obj2;
                }
                listSubList = ((List) obj3).subList(0, size6);
            }
        }
        SubscreenNotificationDetailAdapter.ItemViewHolder itemViewHolder2 = this.detailViewHolder;
        if (itemViewHolder2 != null && (linearLayout5 = itemViewHolder2.mSmartReplyLayout) != null) {
            linearLayout5.removeAllViews();
        }
        if (listSubList != null) {
            for (String str3 : listSubList) {
                Context context = this.mDisplayContext;
                if (context == null) {
                    context = null;
                }
                LayoutInflater layoutInflaterFrom = LayoutInflater.from(context);
                SubscreenNotificationDetailAdapter.ItemViewHolder itemViewHolder3 = this.detailViewHolder;
                final View viewInflate = layoutInflaterFrom.inflate(R.layout.subscreen_notification_detail_adapter_content_layout_item_reply_word_ai_b5, (ViewGroup) (itemViewHolder3 != null ? itemViewHolder3.mContentLayout : null), false);
                TextView textView = (TextView) viewInflate.findViewById(R.id.subscreen_detail_word);
                if (textView != null) {
                    textView.setText(StringsKt__StringsKt.trim(str3).toString());
                }
                if (NotiRune.NOTI_SUBSCREEN_SUPPORT_SMART_REPLY_AI_FOR_CHINA && (imageView = (ImageView) viewInflate.findViewById(R.id.suggested_replies_text_item_watermark)) != null) {
                    imageView.setVisibility(0);
                }
                SubscreenNotificationDetailAdapter.ItemViewHolder itemViewHolder4 = this.detailViewHolder;
                if (itemViewHolder4 != null && (linearLayout4 = itemViewHolder4.mSmartReplyLayout) != null) {
                    linearLayout4.addView(viewInflate);
                }
                SubscreenSubRoomNotification subscreenSubRoomNotification = this.mSubRoomNotification;
                if (subscreenSubRoomNotification != null && (subscreenNotificationDetailAdapter = subscreenSubRoomNotification.mNotificationDetailAdapter) != null && (itemViewHolder = this.detailViewHolder) != null && itemViewHolder.mSmartReplyLayout != null) {
                    viewInflate.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.statusbar.notification.SubscreenNotificationDetailAdapter$$ExternalSyntheticLambda1
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            SubscreenNotificationDetailAdapter subscreenNotificationDetailAdapter2 = subscreenNotificationDetailAdapter;
                            SubscreenNotificationDetailAdapter.ItemViewHolder itemViewHolder5 = itemViewHolder;
                            View view2 = viewInflate;
                            if (subscreenNotificationDetailAdapter2.mReplyButtonView != null) {
                                Log.d("SubscreenNotificationDetailAdapter", "CoverReplyButtonView is already existed.");
                                return;
                            }
                            subscreenNotificationDetailAdapter2.mDeviceModel.getClass();
                            int dispalyHeight = subscreenNotificationDetailAdapter2.mDeviceModel.getDispalyHeight() - subscreenNotificationDetailAdapter2.mDeviceModel.getMainHeaderViewHeight();
                            int iComputeVerticalScrollOffset = subscreenNotificationDetailAdapter2.mSubRoomNotification.mNotificationRecyclerView.computeVerticalScrollOffset();
                            subscreenNotificationDetailAdapter2.mSubRoomNotification.mNotificationRecyclerView.smoothScrollBy(0, (int) ((view.getY() + (itemViewHolder5.mSmartReplyLayout.getY() + itemViewHolder5.mReplyContainer.getY())) - (((dispalyHeight / 2) + iComputeVerticalScrollOffset) - (view.getHeight() / 2))), false);
                            TextView textView2 = (TextView) view2.findViewById(R.id.subscreen_detail_word);
                            for (int i7 = 0; i7 < itemViewHolder5.mSmartReplyLayout.getChildCount(); i7++) {
                                View childAt = itemViewHolder5.mSmartReplyLayout.getChildAt(i7);
                                if (childAt.equals(view)) {
                                    subscreenNotificationDetailAdapter2.setSmartReplyWordTextStyle(childAt, 0.2f, 1.0f);
                                    subscreenNotificationDetailAdapter2.showReplyButtons(itemViewHolder5, textView2.getText().toString(), view, "AI generated");
                                } else {
                                    subscreenNotificationDetailAdapter2.setSmartReplyWordTextStyle(childAt, 1.0f, 0.2f);
                                }
                            }
                            int i8 = 0;
                            while (i8 < itemViewHolder5.mReplylayout.getChildCount()) {
                                SubscreenNotificationDetailAdapter subscreenNotificationDetailAdapter3 = subscreenNotificationDetailAdapter2;
                                subscreenNotificationDetailAdapter3.setReplyWordTextStyle(itemViewHolder5.mReplylayout.getChildAt(i8), SubscreenNotificationDetailAdapter.REGULAR, false, 1.0f, 0.2f);
                                i8++;
                                subscreenNotificationDetailAdapter2 = subscreenNotificationDetailAdapter3;
                            }
                            SubscreenNotificationDetailAdapter subscreenNotificationDetailAdapter4 = subscreenNotificationDetailAdapter2;
                            subscreenNotificationDetailAdapter4.mNotificationAnimatorManager.alphaViewAnimated(itemViewHolder5.mDetailButtonLayout, null, 250L, 1.0f, 0.2f);
                            subscreenNotificationDetailAdapter4.mNotificationAnimatorManager.alphaViewAnimated(itemViewHolder5.mEditButton, null, 250L, 1.0f, 0.2f);
                            SystemUIAnalytics.sendEventLog(SystemUIAnalytics.EID_QPNE_COVER_SCREEN_ID_DETAIL, SystemUIAnalytics.EID_QPNE_COVER_REPLY_WITH_PRESET);
                        }
                    });
                }
            }
            size = listSubList.size();
        } else {
            size = 0;
        }
        Runnable runnable = new Runnable() { // from class: com.android.systemui.statusbar.notification.SubscreenDeviceModelB5$inflateSmartReplyAI$runnable$1
            @Override // java.lang.Runnable
            public final void run() {
                LinearLayout linearLayout6 = this.this$0.progressLayout;
                if (linearLayout6 != null) {
                    linearLayout6.setVisibility(8);
                }
                LottieAnimationView lottieAnimationView = this.this$0.progressingVi;
                if (lottieAnimationView != null) {
                    lottieAnimationView.cancelAnimation();
                }
            }
        };
        SubscreenNotificationDetailAdapter.ItemViewHolder itemViewHolder5 = this.detailViewHolder;
        if (itemViewHolder5 != null && (linearLayout3 = itemViewHolder5.mReplylayout) != null) {
            linearLayout3.getY();
        }
        LinearLayout linearLayout6 = this.progressLayout;
        if (linearLayout6 != null && linearLayout6.getVisibility() == 0 && (linearLayout2 = this.progressLayout) != null) {
            startProgressSpringAnimation(linearLayout2, false, runnable);
        }
        SubscreenNotificationDetailAdapter.ItemViewHolder itemViewHolder6 = this.detailViewHolder;
        if (itemViewHolder6 != null && (linearLayout = itemViewHolder6.mSmartReplyLayout) != null) {
            numValueOf = Integer.valueOf(linearLayout.getChildCount());
        }
        numValueOf.getClass();
        if (numValueOf.intValue() == 0) {
            updateVisibilityForSmartReplyLayout(8);
        } else {
            this.mHandler.postDelayed(new Runnable() { // from class: com.android.systemui.statusbar.notification.SubscreenDeviceModelB5$inflateSmartReplyAI$runnable$2
                /* JADX WARN: Multi-variable type inference failed */
                /* JADX WARN: Type inference failed for: r11v1 */
                /* JADX WARN: Type inference failed for: r11v15 */
                /* JADX WARN: Type inference failed for: r11v2, types: [T, android.view.View] */
                @Override // java.lang.Runnable
                public final void run() {
                    LinearLayout linearLayout7;
                    LinearLayout linearLayout8;
                    LinearLayout linearLayout9;
                    SubscreenDeviceModelB5 subscreenDeviceModelB5 = this.this$0;
                    int i7 = SubscreenDeviceModelB5.$r8$clinit;
                    subscreenDeviceModelB5.resetProgressScaleAnimation();
                    this.this$0.updateVisibilityForSmartReplyLayout(0);
                    SubscreenDeviceModelB5 subscreenDeviceModelB52 = this.this$0;
                    SubscreenNotificationDetailAdapter.ItemViewHolder itemViewHolder7 = subscreenDeviceModelB52.detailViewHolder;
                    Integer numValueOf2 = (itemViewHolder7 == null || (linearLayout9 = itemViewHolder7.mSmartReplyLayout) == null) ? null : Integer.valueOf(linearLayout9.getChildCount());
                    numValueOf2.getClass();
                    float fIntValue = ((3 - numValueOf2.intValue()) * 90) - 181.0f;
                    SubscreenNotificationDetailAdapter.ItemViewHolder itemViewHolder8 = subscreenDeviceModelB52.detailViewHolder;
                    if (itemViewHolder8 != null && (linearLayout8 = itemViewHolder8.mReplylayout) != null) {
                        linearLayout8.setTranslationY(fIntValue);
                    }
                    SubscreenNotificationDetailAdapter.ItemViewHolder itemViewHolder9 = subscreenDeviceModelB52.detailViewHolder;
                    ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(itemViewHolder9 != null ? itemViewHolder9.mReplylayout : null, (Property<LinearLayout, Float>) View.TRANSLATION_Y, 0.0f);
                    objectAnimatorOfFloat.setInterpolator(Interpolators.FAST_OUT_LINEAR_IN);
                    objectAnimatorOfFloat.setDuration(200L);
                    objectAnimatorOfFloat.start();
                    final SpringForce springForce = new SpringForce(1.0f);
                    springForce.setStiffness(200.0f);
                    springForce.setDampingRatio(0.75f);
                    int iIntValue = numValueOf2.intValue();
                    int i8 = 0;
                    for (int i9 = 0; i9 < iIntValue; i9++) {
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
            SystemUIAnalytics.sendEventCDLog(SystemUIAnalytics.EID_QPNE_COVER_SCREEN_ID_DETAIL, SystemUIAnalytics.EID_QPNE_COVER_AI_SUGGESTIONS_GENERATED, SystemUIAnalytics.QPNE_KEY_COUNT, String.valueOf(size));
        }
        this.smartReplyStatus = 0;
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
                    if (!this.this$0.isKeyguardStats()) {
                        SubscreenDeviceModelB5.access$showReplyActivity(this.this$0, context, itemViewHolder);
                        return;
                    }
                    this.this$0.showBouncer(context, itemViewHolder.mInfo.mRow.mEntry);
                    SubscreenDeviceModelB5.KeyguardActionInfo keyguardActionInfo = this.this$0.mKeyguardActionInfo;
                    if (keyguardActionInfo != null) {
                        Context context2 = context;
                        SubscreenNotificationDetailAdapter.ItemViewHolder itemViewHolder2 = itemViewHolder;
                        keyguardActionInfo.action = 3;
                        keyguardActionInfo.detailAdapterItemViewHolder = itemViewHolder2;
                        keyguardActionInfo.context = context2;
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
        boolean z = false;
        this.mSmartReplyClickedByUser = false;
        final ImageView imageView3 = (ImageView) itemViewHolder.itemView.findViewById(R.id.smart_reply_trigger_button);
        if (imageView3 != null) {
            imageView3.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.statusbar.notification.SubscreenDeviceModelB5$initDetailAdapterItemViewHolder$2$1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) throws Resources.NotFoundException {
                    SubscreenRecyclerView subscreenRecyclerView;
                    SubscreenRecyclerView subscreenRecyclerView2;
                    SubscreenNotificationDialog subscreenNotificationDialog;
                    SubscreenNotificationDialog subscreenNotificationDialog2;
                    final SubscreenDeviceModelB5 subscreenDeviceModelB5 = this.this$0;
                    subscreenDeviceModelB5.mSmartReplyClickedByUser = true;
                    Float fValueOf = null;
                    if (!subscreenDeviceModelB5.isUnusableAccount) {
                        subscreenDeviceModelB5.isPossibleAiReply = true;
                        Context context2 = subscreenDeviceModelB5.mDisplayContext;
                        if (context2 == null) {
                            context2 = null;
                        }
                        int dimensionPixelSize = context2.getResources().getDimensionPixelSize(R.dimen.subscreen_noti_detail_icon_button_shadow_margin_b5);
                        LinearLayout linearLayout = this.this$0.detailButtonContainer;
                        if (linearLayout != null) {
                            float y = linearLayout.getY() + dimensionPixelSize;
                            SubscreenSubRoomNotification subscreenSubRoomNotification = this.this$0.mSubRoomNotification;
                            fValueOf = Float.valueOf(y - ((subscreenSubRoomNotification == null || (subscreenRecyclerView2 = subscreenSubRoomNotification.mNotificationRecyclerView) == null) ? 0 : subscreenRecyclerView2.computeVerticalScrollOffset()));
                        }
                        if (fValueOf != null) {
                            if (((int) fValueOf.floatValue()) == 0) {
                                this.this$0.showAIReply();
                            } else {
                                SubscreenSubRoomNotification subscreenSubRoomNotification2 = this.this$0.mSubRoomNotification;
                                if (subscreenSubRoomNotification2 != null && (subscreenRecyclerView = subscreenSubRoomNotification2.mNotificationRecyclerView) != null) {
                                    subscreenRecyclerView.smoothScrollBy(0, (int) fValueOf.floatValue(), false);
                                }
                            }
                        }
                    } else if (!subscreenDeviceModelB5.isSALoggedIn) {
                        Log.d("S.S.N.", "checkAndExecuteSuggestResponses() : execute sa sign in");
                        subscreenDeviceModelB5.openPhonePopupForIntelligenceSettings("com.samsung.android.action.INTELLIGENCE_SERVICE_SETTINGS_START_INTENT");
                    } else if (subscreenDeviceModelB5.isChildAccount) {
                        Log.d("S.S.N.", "checkAndExecuteSuggestResponses() : execute child account announce");
                        SubscreenNotificationDialog subscreenNotificationDialog3 = subscreenDeviceModelB5.mDialog;
                        if (subscreenNotificationDialog3 != null) {
                            if (subscreenNotificationDialog3.mDialog.isShowing() && (subscreenNotificationDialog2 = subscreenDeviceModelB5.mDialog) != null) {
                                subscreenNotificationDialog2.dismiss();
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
                        SubscreenNotificationDialog subscreenNotificationDialog4 = new SubscreenNotificationDialog(context4 != null ? context4 : null, quantityString);
                        subscreenDeviceModelB5.mDialog = subscreenNotificationDialog4;
                        subscreenNotificationDialog4.show();
                    } else if (!subscreenDeviceModelB5.isAiInfoConfirmed) {
                        Log.d("S.S.N.", "checkAndExecuteSuggestResponses() : execute galaxy ai confirm");
                        subscreenDeviceModelB5.openPhonePopupForIntelligenceSettings("com.samsung.android.action.INTELLIGENCE_SERVICE_SETTINGS_START_INTENT");
                    } else if (NotiRune.NOTI_SUBSCREEN_SUPPORT_SMART_REPLY_AI_FOR_CHINA && Settings.System.getInt(subscreenDeviceModelB5.mContext.getContentResolver(), "prevent_online_processing", 0) == 1 && !subscreenDeviceModelB5.isAiCoreFeaturesEnabled()) {
                        SubscreenNotificationDialog subscreenNotificationDialog5 = subscreenDeviceModelB5.mDialog;
                        if (subscreenNotificationDialog5 != null) {
                            if (subscreenNotificationDialog5.mDialog.isShowing() && (subscreenNotificationDialog = subscreenDeviceModelB5.mDialog) != null) {
                                subscreenNotificationDialog.dismiss();
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
                                SubscreenDeviceModelB5 subscreenDeviceModelB52 = subscreenDeviceModelB5;
                                int i2 = SubscreenDeviceModelB5.$r8$clinit;
                                subscreenDeviceModelB52.openPhonePopupForIntelligenceSettings("com.samsung.android.action.INTELLIGENCE_SERVICE_PROCESSING_ONLINE_INTENT");
                            }
                        };
                        Context context7 = subscreenDeviceModelB5.mDisplayContext;
                        SubscreenNotificationDialog subscreenNotificationDialog6 = new SubscreenNotificationDialog(context7 != null ? context7 : null, string, string2, runnable);
                        subscreenDeviceModelB5.mDialog = subscreenNotificationDialog6;
                        subscreenNotificationDialog6.show();
                    } else if (subscreenDeviceModelB5.needToShowFTU) {
                        Log.d("S.S.N.", "checkAndExecuteSuggestResponses() : show writing assist FTU");
                        PendingIntent activity = PendingIntent.getActivity(subscreenDeviceModelB5.mContext, 0, subscreenDeviceModelB5.getWritingAssistFTUIntent(), 201326592);
                        activity.getClass();
                        subscreenDeviceModelB5.requestOpenPhonePopup(activity);
                    } else if (!subscreenDeviceModelB5.isSuggestResponsesEnabled && !subscreenDeviceModelB5.suggestedRepliesDisabledByUser) {
                        Log.d("S.S.N.", "checkAndExecuteSuggestResponses() : execute SR settings");
                        PendingIntent activity2 = PendingIntent.getActivity(subscreenDeviceModelB5.mContext, 0, new Intent("com.samsung.android.honeyboard.intent.action.SUGGESTED_REPLIES_SETTING"), 201326592);
                        activity2.getClass();
                        subscreenDeviceModelB5.requestOpenPhonePopup(activity2);
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
                public final void onClick(View view) throws Resources.NotFoundException {
                    TextView textView;
                    SubscreenNotificationDialog subscreenNotificationDialog;
                    SubscreenNotificationDialog subscreenNotificationDialog2 = this.this$0.mDialog;
                    if (subscreenNotificationDialog2 != null) {
                        if (subscreenNotificationDialog2.mDialog.isShowing() && (subscreenNotificationDialog = this.this$0.mDialog) != null) {
                            subscreenNotificationDialog.dismiss();
                        }
                        this.this$0.mDialog = null;
                    }
                    String string = context.getResources().getString(R.string.subscreen_notification_smart_reply_ai_disclaimer_dialog_content);
                    String strSubstringBefore$default = StringsKt__StringsKt.substringBefore$default(StringsKt__StringsKt.substringAfter$default(string, "%1$s"), "%2$s");
                    final SubscreenDeviceModelB5 subscreenDeviceModelB5 = this.this$0;
                    ClickableSpan clickableSpan = new ClickableSpan() { // from class: com.android.systemui.statusbar.notification.SubscreenDeviceModelB5$initDetailAdapterItemViewHolder$4$1$clickableSpan$1
                        @Override // android.text.style.ClickableSpan
                        public final void onClick(View view2) {
                            SubscreenNotificationDialog subscreenNotificationDialog3 = subscreenDeviceModelB5.mDialog;
                            if (subscreenNotificationDialog3 != null) {
                                subscreenNotificationDialog3.dismiss();
                            }
                            Intent intent = new Intent();
                            SubscreenDeviceModelB5 subscreenDeviceModelB52 = subscreenDeviceModelB5;
                            intent.setAction("android.intent.action.VIEW");
                            Context context2 = subscreenDeviceModelB52.mContext;
                            intent.setData(Uri.parse("https://policies.account.samsung.com/terms?appKey=j5p7ll8g33" + AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("&applicationRegion=", subscreenDeviceModelB52.getIsoCountryCode()) + AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("&language=", Locale.getDefault().getLanguage()) + AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("&region=", subscreenDeviceModelB52.getIsoCountryCode()) + "&type=TC"));
                            intent.addFlags(268435456);
                            PendingIntent activityAsUser = PendingIntent.getActivityAsUser(subscreenDeviceModelB5.mContext, 0, intent, 201326592, null, UserHandle.CURRENT);
                            SubscreenDeviceModelB5 subscreenDeviceModelB53 = subscreenDeviceModelB5;
                            activityAsUser.getClass();
                            subscreenDeviceModelB53.requestOpenPhonePopup(activityAsUser);
                        }
                    };
                    int i = StringCompanionObject.$r8$clinit;
                    SpannableString spannableString = new SpannableString(String.format(string, Arrays.copyOf(new Object[]{"", ""}, 2)));
                    int iIndexOf$default = StringsKt__StringsKt.indexOf$default(spannableString, strSubstringBefore$default, 0, false, 6);
                    int length = strSubstringBefore$default.length() + iIndexOf$default;
                    spannableString.setSpan(new StyleSpan(1), iIndexOf$default, length, 33);
                    spannableString.setSpan(clickableSpan, iIndexOf$default, length, 33);
                    Context context2 = this.this$0.mDisplayContext;
                    if (context2 == null) {
                        context2 = null;
                    }
                    LayoutInflater layoutInflaterFrom = LayoutInflater.from(new ContextThemeWrapper(context2, R.style.Theme_SystemUI_Dialog));
                    View viewInflate = layoutInflaterFrom != null ? layoutInflaterFrom.inflate(R.layout.subscreen_notification_smart_reply_disclaimer_info, (ViewGroup) null) : null;
                    if (viewInflate != null && (textView = (TextView) viewInflate.findViewById(R.id.smart_reply_ai_disclaimer_text)) != null) {
                        textView.setText(spannableString);
                        textView.setMovementMethod(LinkMovementMethod.getInstance());
                    }
                    SubscreenDeviceModelB5 subscreenDeviceModelB52 = this.this$0;
                    Context context3 = subscreenDeviceModelB52.mDisplayContext;
                    subscreenDeviceModelB52.mDialog = new SubscreenNotificationDialog(context3 != null ? context3 : null, viewInflate);
                    SubscreenNotificationDialog subscreenNotificationDialog3 = this.this$0.mDialog;
                    if (subscreenNotificationDialog3 != null) {
                        subscreenNotificationDialog3.show();
                    }
                    Log.d("S.S.N.", "show smart reply ai disclaimer dialog");
                }
            });
            imageView2 = imageView4;
        }
        this.aiDisclaimerBtn = imageView2;
        this.isAiInfoConfirmed = getMSettingsHelper().isAiInfoConfirmed();
        boolean zIsSuggestResponsesEnabled = getMSettingsHelper().isSuggestResponsesEnabled();
        this.isSuggestResponsesEnabled = zIsSuggestResponsesEnabled;
        if (!this.isRDUMode && (!this.isSALoggedIn || this.isChildAccount || !this.isAiInfoConfirmed || !zIsSuggestResponsesEnabled || this.needToShowFTU || (NotiRune.NOTI_SUBSCREEN_SUPPORT_SMART_REPLY_AI_FOR_CHINA && Settings.System.getInt(this.mContext.getContentResolver(), "prevent_online_processing", 0) == 1 && !isAiCoreFeaturesEnabled()))) {
            z = true;
        }
        this.isUnusableAccount = z;
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final void initDetailAdapterTextViewHolder(SubscreenNotificationDetailAdapter.TextViewHolder textViewHolder) {
        this.openAppButtonText = (TextView) textViewHolder.itemView.findViewById(R.id.app_open_button_text);
        this.clearButtonText = (TextView) textViewHolder.itemView.findViewById(R.id.clear_button_text);
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final void initFirstHistoryItemPostTimeInDetailAdapter() {
        this.firstHistoryItemPostTimeInDetailAdapter = 0L;
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final void initKeyguardActioninfo() {
        KeyguardActionInfo keyguardActionInfo = this.mKeyguardActionInfo;
        if (keyguardActionInfo != null) {
            keyguardActionInfo.action = 0;
            keyguardActionInfo.subscreenParentItemViewHolder = null;
            keyguardActionInfo.context = null;
            keyguardActionInfo.entry = null;
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
        View viewInflate = LayoutInflater.from(context).inflate(R.layout.subscreen_notification_adapter_header_b5, (ViewGroup) null);
        this.mHeaderViewLayout = viewInflate;
        linearLayout.addView(viewInflate, 0);
        View view = this.mHeaderViewLayout;
        if (view != null) {
            view.setVisibility(8);
        }
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public void initMainHeaderViewItems(Context context, SubscreenNotificationInfo subscreenNotificationInfo, boolean z) throws Resources.NotFoundException {
        FrameLayout frameLayout;
        ImageView imageView;
        ImageView imageView2;
        ImageView imageView3;
        ImageView imageView4;
        ImageView imageView5;
        LinearLayout linearLayout;
        boolean zNeedsRedaction;
        boolean z2;
        ExpandableNotificationRow expandableNotificationRow;
        ExpandableNotificationRow expandableNotificationRow2;
        boolean z3;
        boolean z4;
        ExpandableNotificationRow expandableNotificationRow3;
        ExpandableNotificationRow expandableNotificationRow4;
        boolean z5;
        ExpandableNotificationRow expandableNotificationRow5;
        ExpandableNotificationRow expandableNotificationRow6;
        NotificationEntry notificationEntry;
        NotificationChannel channel;
        ExpandableNotificationRow expandableNotificationRow7;
        ExpandableNotificationRow expandableNotificationRow8;
        final Context context2;
        ExpandableNotificationRow expandableNotificationRow9;
        final NotificationEntry notificationEntry2;
        ExpandableNotificationRow expandableNotificationRow10;
        ImageView imageView6;
        View view = this.mHeaderViewLayout;
        final LinearLayout linearLayout2 = view != null ? (LinearLayout) view.findViewById(R.id.back_key) : null;
        View view2 = this.mHeaderViewLayout;
        TextView textView = view2 != null ? (TextView) view2.findViewById(R.id.subscreen_header_app_name) : null;
        View view3 = this.mHeaderViewLayout;
        ImageView imageView7 = view3 != null ? (ImageView) view3.findViewById(R.id.secure_icon) : null;
        View view4 = this.mHeaderViewLayout;
        ImageView imageView8 = view4 != null ? (ImageView) view4.findViewById(R.id.two_phone_icon) : null;
        View view5 = this.mHeaderViewLayout;
        if (view5 == null || (frameLayout = (FrameLayout) view5.findViewById(R.id.header_app_icon_layout)) == null) {
            frameLayout = null;
        } else {
            frameLayout.setVisibility(0);
        }
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
        View view10 = this.mHeaderViewLayout;
        if (view10 != null && (imageView6 = (ImageView) view10.findViewById(R.id.more_icon_shadow)) != null) {
            imageView6.setImageDrawable(null);
            imageView6.setVisibility(8);
        }
        if (subscreenNotificationInfo != null && (expandableNotificationRow10 = subscreenNotificationInfo.mRow) != null && expandableNotificationRow10.isInsignificantSummary()) {
            updateMoreShadowIconColor(this.mHeaderViewLayout, subscreenNotificationInfo.mRow.mEntry);
        }
        View view11 = this.mHeaderViewLayout;
        if (view11 == null || (imageView5 = (ImageView) view11.findViewById(R.id.subscreen_notification_header_more_icon)) == null) {
            imageView5 = null;
        } else {
            imageView5.setImageDrawable(null);
            imageView5.setVisibility(8);
            imageView5.clearColorFilter();
        }
        View view12 = this.mHeaderViewLayout;
        if (view12 == null || (linearLayout = (LinearLayout) view12.findViewById(R.id.open_app_btn)) == null) {
            linearLayout = null;
        } else {
            if (subscreenNotificationInfo == null || (expandableNotificationRow9 = subscreenNotificationInfo.mRow) == null || (notificationEntry2 = expandableNotificationRow9.mEntry) == null) {
                context2 = context;
            } else {
                linearLayout.setVisibility(isLaunchApp(notificationEntry2) ? 0 : 8);
                context2 = context;
                linearLayout.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.statusbar.notification.SubscreenDeviceModelB5$initMainHeaderViewItems$openAppBtn$1$1$1
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view13) {
                        if (this.this$0.isKeyguardStats()) {
                            this.this$0.showBouncer(context2, notificationEntry2);
                            SubscreenDeviceModelB5.KeyguardActionInfo keyguardActionInfo = this.this$0.mKeyguardActionInfo;
                            if (keyguardActionInfo != null) {
                                NotificationEntry notificationEntry3 = notificationEntry2;
                                keyguardActionInfo.action = 6;
                                keyguardActionInfo.entry = notificationEntry3;
                                return;
                            }
                            return;
                        }
                        if (this.this$0.isKeyguardUnlockShowing()) {
                            ((KeyguardManager) context2.getSystemService("keyguard")).semDismissKeyguard();
                            SubscreenDeviceModelB5.KeyguardActionInfo keyguardActionInfo2 = this.this$0.mKeyguardActionInfo;
                            if (keyguardActionInfo2 != null) {
                                NotificationEntry notificationEntry4 = notificationEntry2;
                                keyguardActionInfo2.action = 6;
                                keyguardActionInfo2.entry = notificationEntry4;
                                return;
                            }
                            return;
                        }
                        if (this.this$0.clickKnoxItem(notificationEntry2)) {
                            MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("clickKnoxItem on header(open app): ", notificationEntry2.mKey, "S.S.N.");
                            return;
                        }
                        if (this.this$0.launchApp(notificationEntry2) && this.this$0.isShownDetail()) {
                            SubscreenNotificationReplyActivity subscreenNotificationReplyActivity = this.this$0.mController.replyActivity;
                            if (subscreenNotificationReplyActivity != null) {
                                subscreenNotificationReplyActivity.finish();
                            }
                            this.this$0.hideDetailNotification();
                        }
                    }
                });
            }
            String string = context2.getResources().getString(R.string.subscreen_detail_adapter_open_app_button_text);
            linearLayout.setContentDescription(string);
            linearLayout.setTooltipText(string);
        }
        Icon icon = subscreenNotificationInfo != null ? subscreenNotificationInfo.mConversationIcon : null;
        Icon icon2 = subscreenNotificationInfo != null ? subscreenNotificationInfo.mLargeIcon : null;
        LinearLayout linearLayout3 = linearLayout;
        Boolean boolValueOf = subscreenNotificationInfo != null ? Boolean.valueOf(subscreenNotificationInfo.mIsMessagingStyle) : null;
        if (isKeyguardStats()) {
            zNeedsRedaction = (subscreenNotificationInfo == null || (expandableNotificationRow8 = subscreenNotificationInfo.mRow) == null) ? false : expandableNotificationRow8.needsRedaction();
        } else {
            zNeedsRedaction = false;
        }
        ImageView imageView9 = imageView8;
        String string2 = context.getResources().getString(R.string.subscreen_back_button_content_description);
        if (linearLayout2 != null) {
            linearLayout2.setContentDescription(string2);
        }
        if (!z) {
            if (linearLayout2 != null) {
                linearLayout2.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.statusbar.notification.SubscreenDeviceModelB5$setBackKeyClickListener$1
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view13) {
                        SubscreenDeviceModelB5 subscreenDeviceModelB5 = this.this$0;
                        int i = SubscreenDeviceModelB5.$r8$clinit;
                        subscreenDeviceModelB5.performBackClick();
                    }
                });
            }
            if (linearLayout2 != null) {
                linearLayout2.addOnUnhandledKeyEventListener(new View.OnUnhandledKeyEventListener() { // from class: com.android.systemui.statusbar.notification.SubscreenDeviceModelB5$setBackKeyClickListener$2
                    @Override // android.view.View.OnUnhandledKeyEventListener
                    public final boolean onUnhandledKeyEvent(View view13, KeyEvent keyEvent) {
                        if (keyEvent.getAction() != 0 || keyEvent.getKeyCode() != 4) {
                            return false;
                        }
                        linearLayout2.callOnClick();
                        return false;
                    }
                });
            }
            if (frameLayout != null) {
                frameLayout.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.statusbar.notification.SubscreenDeviceModelB5.initMainHeaderViewItems.1
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view13) {
                        SubscreenDeviceModelB5 subscreenDeviceModelB5 = SubscreenDeviceModelB5.this;
                        int i = SubscreenDeviceModelB5.$r8$clinit;
                        subscreenDeviceModelB5.performBackClick();
                    }
                });
            }
        }
        if (linearLayout2 != null) {
            linearLayout2.setTooltipText(string2);
        }
        boolean z6 = subscreenNotificationInfo != null && isConversation(subscreenNotificationInfo);
        if (textView != null) {
            textView.setText((isShownDetail() && z6) ? this.titleText : subscreenNotificationInfo != null ? subscreenNotificationInfo.mAppName : null);
        }
        View view13 = this.mHeaderViewLayout;
        if (view13 != null) {
            view13.setContentDescription(textView != null ? textView.getText() : null);
        }
        if (imageView != null) {
            imageView.clearColorFilter();
        }
        if (zNeedsRedaction || !Intrinsics.areEqual(boolValueOf, Boolean.TRUE) || (icon == null && icon2 == null)) {
            if (!isShowNotificationAppIcon()) {
                if (subscreenNotificationInfo == null || (expandableNotificationRow2 = subscreenNotificationInfo.mRow) == null || !expandableNotificationRow2.isInsignificantSummary() || imageView5 == null) {
                    z2 = false;
                    if (imageView2 != null) {
                        imageView2.setVisibility(0);
                        imageView2.setImageDrawable(subscreenNotificationInfo != null ? subscreenNotificationInfo.mIcon : null);
                    }
                    imageView5 = imageView2;
                } else {
                    imageView5.setVisibility(0);
                    imageView5.setImageDrawable(subscreenNotificationInfo.mIcon);
                    z2 = false;
                }
                updateSmallIconBg(imageView5, true, z2, z2);
                updateIconColor(imageView5, (subscreenNotificationInfo == null || (expandableNotificationRow = subscreenNotificationInfo.mRow) == null) ? null : expandableNotificationRow.mEntry);
            } else if (imageView == null || subscreenNotificationInfo == null || subscreenNotificationInfo.useSmallIcon()) {
                if (subscreenNotificationInfo == null || (expandableNotificationRow4 = subscreenNotificationInfo.mRow) == null || !expandableNotificationRow4.isInsignificantSummary() || imageView5 == null) {
                    z4 = false;
                    if (imageView2 != null) {
                        imageView2.setVisibility(0);
                        imageView2.setImageDrawable(subscreenNotificationInfo != null ? subscreenNotificationInfo.mIcon : null);
                    }
                    imageView5 = imageView2;
                } else {
                    imageView5.setVisibility(0);
                    imageView5.setImageDrawable(subscreenNotificationInfo.mIcon);
                    z4 = false;
                }
                updateSmallIconSquircleBg(imageView5, true, z4);
                updateIconColor(imageView5, (subscreenNotificationInfo == null || (expandableNotificationRow3 = subscreenNotificationInfo.mRow) == null) ? null : expandableNotificationRow3.mEntry);
            } else {
                ExpandableNotificationRow expandableNotificationRow11 = subscreenNotificationInfo.mRow;
                if (expandableNotificationRow11 == null || !expandableNotificationRow11.isInsignificantSummary() || imageView5 == null) {
                    imageView.setImageDrawable(subscreenNotificationInfo.mAppIcon);
                    imageView.setVisibility(0);
                } else {
                    imageView5.setVisibility(0);
                    imageView5.setImageDrawable(subscreenNotificationInfo.mAppIcon);
                }
            }
            z3 = false;
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
            z3 = true;
        }
        if (subscreenNotificationInfo != null && subscreenNotificationInfo.isSportsOngoing() && imageView2 != null) {
            imageView2.setVisibility(0);
            imageView2.setImageDrawable(subscreenNotificationInfo.mIcon);
        }
        if (z3) {
            if (!isShowNotificationAppIcon()) {
                updateSmallIconBg(imageView4, false, false, true);
                if (imageView4 != null) {
                    imageView4.setImageDrawable(subscreenNotificationInfo != null ? subscreenNotificationInfo.mIcon : null);
                }
                updateIconColor(imageView4, (subscreenNotificationInfo == null || (expandableNotificationRow7 = subscreenNotificationInfo.mRow) == null) ? null : expandableNotificationRow7.mEntry);
            } else if (imageView4 != null) {
                imageView4.setImageDrawable(subscreenNotificationInfo != null ? subscreenNotificationInfo.mAppIcon : null);
            }
            z5 = false;
            if (imageView4 != null) {
                imageView4.setVisibility(0);
            }
        } else {
            z5 = false;
            if (imageView4 != null) {
                imageView4.setVisibility(8);
            }
        }
        updateImportBadgeIconRing(this.mHeaderViewLayout, (!z3 || subscreenNotificationInfo == null || (expandableNotificationRow6 = subscreenNotificationInfo.mRow) == null || (notificationEntry = expandableNotificationRow6.mEntry) == null || (channel = notificationEntry.mRanking.getChannel()) == null || !channel.isImportantConversation()) ? z5 : true);
        super.updateKnoxIcon(imageView7, subscreenNotificationInfo);
        SubscreenDeviceModelParent.updateTwoPhoneIcon(imageView9, subscreenNotificationInfo);
        if (subscreenNotificationInfo != null && (expandableNotificationRow5 = subscreenNotificationInfo.mRow) != null && expandableNotificationRow5.isInsignificantSummary() && frameLayout != null) {
            frameLayout.setVisibility(8);
        }
        int dimensionPixelSize = context.getResources().getDimensionPixelSize(R.dimen.subscreen_noti_top_popup_text_layout_max_width_b5);
        int dimensionPixelSize2 = context.getResources().getDimensionPixelSize(R.dimen.subscreen_noti_header_extra_icon_margin_b5);
        if (imageView9 != null && imageView9.getVisibility() == 0) {
            dimensionPixelSize = (dimensionPixelSize - dimensionPixelSize2) - context.getResources().getDimensionPixelSize(R.dimen.subscreen_noti_two_phone_icon_width_b5);
        }
        if (imageView7 != null && imageView7.getVisibility() == 0) {
            dimensionPixelSize = (dimensionPixelSize - dimensionPixelSize2) - context.getResources().getDimensionPixelSize(R.dimen.subscreen_noti_extra_icon_size_b5);
        }
        if (linearLayout3 != null && linearLayout3.getVisibility() == 0) {
            dimensionPixelSize = (dimensionPixelSize - dimensionPixelSize2) - context.getResources().getDimensionPixelSize(R.dimen.subscreen_noti_header_icon_btn_ripple_size_b5);
        }
        if (textView != null) {
            textView.setMaxWidth(dimensionPixelSize);
        }
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final void initSmartReplyStatus() {
        this.smartReplyStatus = 0;
        this.mSmartReplyResult = -1;
        this.mSmartReplyResultCompleteMsg = null;
        this.mSmartReplyResultFailureMsg = null;
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelCommon, com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final void initialize() {
        super.initialize();
        Context context = this.mDisplayContext;
        if (context == null) {
            context = null;
        }
        this.listFirstTopMargin = context.getResources().getDimensionPixelSize(R.dimen.subscreen_noti_list_first_top_margin_b5);
        getMSettingsHelper().registerCallback(this.mSettingsListener, Settings.Global.getUriFor(SettingsHelper.INDEX_SUGGESTION_RESPONSES));
    }

    public final boolean isAiCoreFeaturesEnabled() {
        String aiCoreEnabledFeatures = getMSettingsHelper().getAiCoreEnabledFeatures();
        return aiCoreEnabledFeatures != null && StringsKt__StringsKt.contains(aiCoreEnabledFeatures, "on_device_main", false) && StringsKt__StringsKt.contains(aiCoreEnabledFeatures, "smart_reply", false);
    }

    public final boolean isConversation(SubscreenNotificationInfo subscreenNotificationInfo) {
        return (subscreenNotificationInfo.mIsMessagingStyle && subscreenNotificationInfo.mRemoteinput) && !(isKnoxSecurity(subscreenNotificationInfo.mRow.mEntry) && subscreenNotificationInfo.mRow.mEntry.mUserPublic);
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final boolean isCoverBriefAllowed(NotificationEntry notificationEntry) {
        if (notificationEntry == null) {
            return false;
        }
        return ((NotificationInterruptStateProviderImpl) this.mInterruptionStateProvider).isEdgeLightingAllowed(notificationEntry.mSbn) && !this.mFullScreenIntentEntries.containsKey(notificationEntry.mKey);
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final boolean isDismissiblePopup() {
        if (this.mFullScreenIntentEntries.isEmpty()) {
            return true;
        }
        return !this.mFullScreenIntentEntries.isEmpty() && useTopPresentation();
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final boolean isKeyguardStats() {
        KeyguardStateController keyguardStateController = this.mKeyguardStateController;
        if (keyguardStateController == null || !((KeyguardStateControllerImpl) keyguardStateController).mSecure || keyguardStateController.isUnlocked()) {
            KeyguardStateController keyguardStateController2 = this.mKeyguardStateController;
            if (keyguardStateController2 == null) {
                return false;
            }
            KeyguardStateControllerImpl keyguardStateControllerImpl = (KeyguardStateControllerImpl) keyguardStateController2;
            if (keyguardStateControllerImpl.mSecure || !keyguardStateControllerImpl.mShowing) {
                return false;
            }
        }
        return true;
    }

    public final boolean isKeyguardUnlockShowing() {
        KeyguardStateController keyguardStateController;
        KeyguardStateController keyguardStateController2 = this.mKeyguardStateController;
        return keyguardStateController2 != null && ((KeyguardStateControllerImpl) keyguardStateController2).mSecure && keyguardStateController2.isUnlocked() && (keyguardStateController = this.mKeyguardStateController) != null && ((KeyguardStateControllerImpl) keyguardStateController).mShowing;
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final boolean isKeyguardUsed() {
        return true;
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final boolean isKnoxSecurity(NotificationEntry notificationEntry) {
        NotificationLockscreenUserManager notificationLockscreenUserManager = (NotificationLockscreenUserManager) Dependency.sDependency.getDependencyInner(NotificationLockscreenUserManager.class);
        return ((NotificationLockscreenUserManagerImpl) notificationLockscreenUserManager).mUsersWithSeparateWorkChallenge.get(notificationEntry.mSbn.getUserId(), false);
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final boolean isLargeSubscreen() {
        return true;
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final boolean isLaunchApp(NotificationEntry notificationEntry) {
        StringBuilder sb = new StringBuilder(AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("isLaunchApp B5 - ", notificationEntry.mKey));
        IActivityTaskManager service = ActivityTaskManager.getService();
        String packageName = notificationEntry.mSbn.getPackageName();
        boolean z = false;
        if (packageName == null) {
            return false;
        }
        boolean zIsPackageEnabledForCoverLauncher = service.isPackageEnabledForCoverLauncher(packageName, notificationEntry.mSbn.getUser().getIdentifier());
        sb.append(" : isCoverLauncher = " + zIsPackageEnabledForCoverLauncher);
        if (notificationEntry.mSbn.getNotification().contentIntent == null) {
            sb.append(" : No content intent");
        } else if (getMSettingsHelper().isUltraPowerSavingMode()) {
            sb.append(" : Cover app launch not possible due to Ultra Power Saving Mode");
        } else if (getMSettingsHelper().isEmergencyMode()) {
            sb.append(" : Cover app launch not possible due to Emergency Mode");
        } else if (this.mKeyguardUpdateMonitor.isKidsModeRunning()) {
            sb.append(" : Cover app launch not possible due to Kids Mode");
        } else if (zIsPackageEnabledForCoverLauncher || isCallNotification(notificationEntry)) {
            z = true;
        }
        Log.d("S.S.N.", sb.toString());
        return z;
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final boolean isMainHeader() {
        return true;
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final boolean isNotShwonNotificationState(NotificationEntry notificationEntry) {
        if (isKeyguardStats()) {
            return true;
        }
        return isKnoxSecurity(notificationEntry) && notificationEntry.mUserPublic;
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final boolean isOneUI7_0() {
        return true;
    }

    public final boolean isReplyLayoutShowing() {
        SubscreenRecyclerView subscreenRecyclerView;
        SubscreenSubRoomNotification subscreenSubRoomNotification = this.mSubRoomNotification;
        Integer numValueOf = (subscreenSubRoomNotification == null || (subscreenRecyclerView = subscreenSubRoomNotification.mNotificationRecyclerView) == null) ? null : Integer.valueOf(subscreenRecyclerView.computeVerticalScrollOffset());
        numValueOf.getClass();
        int dispalyHeight = ((getDispalyHeight() - getMainHeaderViewHeight()) - coverCutoutSize()) + numValueOf.intValue();
        SubscreenNotificationDetailAdapter.ItemViewHolder itemViewHolder = this.detailViewHolder;
        if (itemViewHolder != null) {
            LinearLayout linearLayout = itemViewHolder.mSmartReplyLayout;
            LinearLayout linearLayout2 = (linearLayout == null || linearLayout.getVisibility() != 0) ? itemViewHolder.mReplylayout : itemViewHolder.mSmartReplyLayout;
            if (linearLayout2 != null) {
                float y = linearLayout2.getY();
                LinearLayout linearLayout3 = itemViewHolder.mReplyContainer;
                y = (linearLayout3 != null ? linearLayout3.getY() : 0.0f) + y;
            }
        }
        return ((float) dispalyHeight) > y;
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final boolean isSamsungAccountLoggedIn() {
        return this.isSALoggedIn;
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final Boolean isShowBouncer() {
        KeyguardActionInfo keyguardActionInfo = this.mKeyguardActionInfo;
        if (keyguardActionInfo != null) {
            return Boolean.valueOf(keyguardActionInfo.isShowBouncer);
        }
        return null;
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final boolean isShowNotificationAppIcon() {
        return getMSettingsHelper().isShowNotificationAppIconEnabled();
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final boolean isSkipFullscreenIntentClicked(NotificationEntry notificationEntry) {
        return (this.mFullScreenIntentEntries.get(notificationEntry.mKey) != null || this.mIsFullscreenFullPopupWindowClosing) && !useTopPresentation();
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final boolean isSupportRemoteView(NotificationEntry notificationEntry) {
        if (notificationEntry.mSbn.getNotification().contentView == null) {
            return false;
        }
        return ArraysKt___ArraysKt.indexOf(new String[]{"com.skt.prod.dialer", "com.samsung.android.incallui", "com.sec.android.app.clockpackage", "com.sec.android.app.voicenote", "com.sec.android.app.voicerecorder", "com.samsung.android.app.interpreter"}, notificationEntry.mSbn.getPackageName()) >= 0;
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final boolean isZenModeViewType(int i) {
        return this.mController.isZenMode() && i == 0;
    }

    public final boolean launchApp(NotificationEntry notificationEntry) {
        try {
            if (!isLaunchApp(notificationEntry)) {
                return false;
            }
            int iStartNotificationIntent = startNotificationIntent(notificationEntry.mSbn.getNotification().contentIntent);
            Log.d("S.S.N.", "launchApp B5 -  Run App : " + notificationEntry.mSbn.getPackageName() + ", result: " + iStartNotificationIntent);
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
            int iStartNotificationIntent = startNotificationIntent(notificationEntry.mSbn.getNotification().fullScreenIntent);
            Log.d("S.S.N.", "launchFullscreenIntent B5 -  Run FullscreenIntent : " + notificationEntry.mKey + ", result: " + iStartNotificationIntent);
            return true;
        } catch (RemoteException e) {
            Log.w("S.S.N.", "unable to get isPackageEnabledForCoverLauncher()", e);
            return false;
        }
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final void loadOnDeviceMetaData() {
        JsonElement jsonElement;
        JsonArray asJsonArray;
        String asString;
        JsonArray asJsonArray2;
        try {
            ((ArrayList) this.onDeviceLanguageList).clear();
            String strReplace$default = StringsKt__StringsJVMKt.replace$default(StringsKt__StringsJVMKt.replace$default(new Regex("\\s+").replace("{ \"list\" : " + getOnDeviceMetaData() + "}", ""), ",}", "}"), ",]", "]");
            if (strReplace$default.length() > 0 && (jsonElement = new JsonParser().parse(strReplace$default).getAsJsonObject().get("list")) != null && (asJsonArray = jsonElement.getAsJsonArray()) != null) {
                Iterator<JsonElement> it = asJsonArray.iterator();
                while (it.hasNext()) {
                    JsonObject asJsonObject = it.next().getAsJsonObject();
                    if (asJsonObject != null) {
                        LlmLanguage llmLanguage = new LlmLanguage(0, null, null, false, false, false, 63, null);
                        JsonElement jsonElement2 = asJsonObject.get("order");
                        llmLanguage.order = jsonElement2 != null ? jsonElement2.getAsInt() : -1;
                        JsonElement jsonElement3 = asJsonObject.get("language");
                        if (jsonElement3 == null || (asString = jsonElement3.getAsString()) == null) {
                            asString = "";
                        }
                        llmLanguage.language = asString;
                        llmLanguage.languageDisplayName = getDisplayName(asString);
                        JsonElement jsonElement4 = asJsonObject.get("supportToneConversion");
                        llmLanguage.supportToneConversion = jsonElement4 != null ? jsonElement4.getAsBoolean() : true;
                        JsonElement jsonElement5 = asJsonObject.get("supportCorrection");
                        llmLanguage.supportCorrection = jsonElement5 != null ? jsonElement5.getAsBoolean() : true;
                        JsonElement jsonElement6 = asJsonObject.get("supportReply");
                        boolean z = false;
                        llmLanguage.supportReply = jsonElement6 != null ? jsonElement6.getAsBoolean() : false;
                        JsonElement jsonElement7 = asJsonObject.get("supportFunction");
                        if (jsonElement7 != null && (asJsonArray2 = jsonElement7.getAsJsonArray()) != null) {
                            Iterator<JsonElement> it2 = asJsonArray2.iterator();
                            while (it2.hasNext()) {
                                if (Intrinsics.areEqual(it2.next().getAsString(), this.SUPPORT_FUNCTION_SMART_REPLY)) {
                                    z = true;
                                }
                            }
                        }
                        if (llmLanguage.order >= 0 && (llmLanguage.supportReply || z)) {
                            ((ArrayList) this.onDeviceLanguageList).add(llmLanguage);
                        }
                    }
                }
            }
            Log.d("S.S.N.", "loadOnDeviceMetaData : " + ((ArrayList) this.onDeviceLanguageList).size());
        } catch (Exception e) {
            KeyguardSecSimPinViewController$$ExternalSyntheticOutline0.m("loadOnDeviceMetaData e: ", e, "S.S.N.");
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:98:0x015b  */
    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void moveDetailAdapterContentScroll(View view, boolean z, boolean z2, boolean z3) throws Resources.NotFoundException {
        LinearLayout linearLayout;
        SubscreenRecyclerView subscreenRecyclerView;
        SubscreenRecyclerView subscreenRecyclerView2;
        ImageView imageView;
        View view2;
        View view3;
        int height;
        String str;
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
            EmergencyButtonController$$ExternalSyntheticOutline0.m("moveDetailAdapterContentScroll B5 - return mIsContentScroll : ", "S.S.N.", z4);
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
            Context context = this.mDisplayContext;
            if (context == null) {
                context = null;
            }
            int dimensionPixelSize = context.getResources().getDimensionPixelSize(R.dimen.subscreen_noti_cut_out_size_b5);
            int mainHeaderViewHeight = getMainHeaderViewHeight();
            int i = childCount - 1;
            View view4 = ViewGroupKt.get(subscreenParentDetailItemViewHolder.mContentLayout, i);
            float y = view4.getY() + mainHeaderViewHeight + view4.getHeight();
            boolean z5 = y >= ((float) (720 - dimensionPixelSize));
            ListPopupWindow$$ExternalSyntheticOutline0.m(subscreenParentDetailItemViewHolder.mBodyLayout.getHeight(), "moveDetailAdapterContentScroll B5 - cuttent body height : ", "S.S.N.");
            if (z) {
                float y2 = subscreenParentDetailItemViewHolder.mReplyContainer.getY() - subscreenParentDetailItemViewHolder.mBodyLayout.getMinimumHeight();
                SubscreenSubRoomNotification subscreenSubRoomNotification3 = this.mSubRoomNotification;
                float fComputeVerticalScrollOffset = y2 - ((subscreenSubRoomNotification3 == null || (subscreenRecyclerView6 = subscreenSubRoomNotification3.mNotificationRecyclerView) == null) ? 0 : subscreenRecyclerView6.computeVerticalScrollOffset());
                SubscreenSubRoomNotification subscreenSubRoomNotification4 = this.mSubRoomNotification;
                if (subscreenSubRoomNotification4 != null && (subscreenRecyclerView5 = subscreenSubRoomNotification4.mNotificationRecyclerView) != null) {
                    subscreenRecyclerView5.smoothScrollBy(0, (int) fComputeVerticalScrollOffset, false);
                }
                Log.d("S.S.N.", "moveDetailAdapterContentScroll B5 - isQuickReply scroll : " + fComputeVerticalScrollOffset);
                return;
            }
            if (!z2) {
                Integer numValueOf = null;
                Log.d("S.S.N.", "moveDetailAdapterContentScroll B5 - totalY : " + y);
                if (z5 || z3 || ((imageView = this.smartReplyTriggerBtn) != null && imageView.getVisibility() == 0)) {
                    float y3 = subscreenParentDetailItemViewHolder.mReplyContainer.getY() - subscreenParentDetailItemViewHolder.mBodyLayout.getMinimumHeight();
                    if (z3) {
                        SubscreenSubRoomNotification subscreenSubRoomNotification5 = this.mSubRoomNotification;
                        if (subscreenSubRoomNotification5 != null && (subscreenRecyclerView2 = subscreenSubRoomNotification5.mNotificationRecyclerView) != null) {
                            numValueOf = Integer.valueOf(subscreenRecyclerView2.computeVerticalScrollOffset());
                        }
                        numValueOf.getClass();
                        y3 -= numValueOf.intValue();
                        Log.d("S.S.N.", "moveDetailAdapterContentScroll B5 - updated : " + y3);
                    }
                    SubscreenSubRoomNotification subscreenSubRoomNotification6 = this.mSubRoomNotification;
                    if (subscreenSubRoomNotification6 == null || (subscreenRecyclerView = subscreenSubRoomNotification6.mNotificationRecyclerView) == null) {
                        return;
                    }
                    subscreenRecyclerView.smoothScrollBy(0, (int) y3, false);
                    return;
                }
                return;
            }
            SubscreenSubRoomNotification subscreenSubRoomNotification7 = this.mSubRoomNotification;
            Integer numValueOf2 = (subscreenSubRoomNotification7 == null || (subscreenRecyclerView4 = subscreenSubRoomNotification7.mNotificationRecyclerView) == null) ? null : Integer.valueOf(subscreenRecyclerView4.computeVerticalScrollOffset());
            numValueOf2.getClass();
            int iIntValue = numValueOf2.intValue();
            int dispalyHeight = ((getDispalyHeight() - getMainHeaderViewHeight()) - dimensionPixelSize) + iIntValue;
            SubscreenSubRoomNotification subscreenSubRoomNotification8 = this.mSubRoomNotification;
            SubscreenNotificationDetailAdapter.ScrollInfo scrollInfo = (subscreenSubRoomNotification8 == null || (subscreenNotificationDetailAdapter2 = subscreenSubRoomNotification8.mNotificationDetailAdapter) == null) ? null : subscreenNotificationDetailAdapter2.mScrollInfo;
            if (scrollInfo == null || (view2 = scrollInfo.mPrevFirstHistoryView) == null) {
                view2 = ViewGroupKt.get(subscreenParentDetailItemViewHolder.mContentLayout, 0);
            }
            if (scrollInfo == null || (view3 = scrollInfo.mPrevLastHistoryView) == null) {
                view3 = ViewGroupKt.get(subscreenParentDetailItemViewHolder.mContentLayout, i);
            }
            Integer numValueOf3 = scrollInfo != null ? Integer.valueOf(scrollInfo.mPrevHistoryCount) : null;
            if ((scrollInfo != null ? scrollInfo.mPrevFirstHistoryView : null) == null) {
                Log.d("S.S.N.", "moveDetailAdapterContentScroll B5 - prevItem is null,scrollInfo?.prevFirstHistoryView :" + (scrollInfo != null ? scrollInfo.mPrevFirstHistoryView : null) + ", scrollInfo?.prevLastHistoryView : " + (scrollInfo != null ? scrollInfo.mPrevLastHistoryView : null));
            } else {
                if ((scrollInfo != null ? scrollInfo.mPrevLastHistoryView : null) == null) {
                }
            }
            float y4 = view3.getY() + (view3.getHeight() / 3);
            Integer numValueOf4 = scrollInfo != null ? Integer.valueOf(scrollInfo.mPrevFirstHistoryViewBottomMargin) : null;
            int i2 = childCount > 1 ? ((LinearLayout.LayoutParams) ViewGroupKt.get(subscreenParentDetailItemViewHolder.mContentLayout, childCount - 2).getLayoutParams()).bottomMargin : 0;
            float f = dispalyHeight;
            if (f <= y4 || iIntValue >= y4) {
                if (f < y4) {
                    numValueOf3.getClass();
                    if (numValueOf3.intValue() >= childCount) {
                        int height2 = view2.getHeight();
                        numValueOf4.getClass();
                        height = -(numValueOf4.intValue() + height2);
                        str = "postion top";
                    }
                    str = "";
                } else {
                    if (iIntValue > y4) {
                        numValueOf3.getClass();
                        if (numValueOf3.intValue() < childCount) {
                            Context context2 = this.mDisplayContext;
                            if (context2 == null) {
                                context2 = null;
                            }
                            int dimensionPixelSize2 = context2.getResources().getDimensionPixelSize(R.dimen.subscreen_noti_detail_conversation_body_min_height_b5);
                            Context context3 = this.mDisplayContext;
                            if (context3 == null) {
                                context3 = null;
                            }
                            if (dimensionPixelSize2 + context3.getResources().getDimensionPixelSize(R.dimen.subscreen_noti_detail_button_container_translateY_b5) <= subscreenParentDetailItemViewHolder.mBodyLayout.getHeight()) {
                                height = view4.getHeight() + i2;
                                str = "postion bottom History Not Max";
                            }
                        } else if (view2.getHeight() > view4.getHeight()) {
                            int height3 = view2.getHeight();
                            numValueOf4.getClass();
                            height = -((numValueOf4.intValue() + height3) - (view4.getHeight() + i2));
                            str = "postion bottom History Max - prevFirstView > lastitem";
                        } else {
                            int height4 = view4.getHeight() + i2;
                            int height5 = view2.getHeight();
                            numValueOf4.getClass();
                            height = height4 - (numValueOf4.intValue() + height5);
                            str = "postion bottom History Max - prevFirstView <= lastitem";
                        }
                    }
                    str = "";
                }
            } else {
                numValueOf3.getClass();
                if (numValueOf3.intValue() < childCount) {
                    str = "latest normal";
                    height = z5 ? (int) (view4.getY() - view3.getY()) : 0;
                } else if (view2.getHeight() > view4.getHeight()) {
                    int height6 = view2.getHeight();
                    numValueOf4.getClass();
                    height = -((numValueOf4.intValue() + height6) - (view4.getHeight() + i2));
                    str = "latest prevFirstView?.height!! > lastItem.height";
                } else {
                    int height7 = view4.getHeight() + i2;
                    int height8 = view2.getHeight();
                    numValueOf4.getClass();
                    height = height7 - (numValueOf4.intValue() + height8);
                    str = "latest prevFirstView?.height!! <= lastItem.height";
                }
            }
            SubscreenSubRoomNotification subscreenSubRoomNotification9 = this.mSubRoomNotification;
            if (subscreenSubRoomNotification9 != null && (subscreenRecyclerView3 = subscreenSubRoomNotification9.mNotificationRecyclerView) != null) {
                subscreenRecyclerView3.scrollBy(0, height);
            }
            SubscreenSubRoomNotification subscreenSubRoomNotification10 = this.mSubRoomNotification;
            if (subscreenSubRoomNotification10 != null && (subscreenNotificationDetailAdapter = subscreenSubRoomNotification10.mNotificationDetailAdapter) != null) {
                SubscreenNotificationDetailAdapter.ScrollInfo scrollInfo2 = subscreenNotificationDetailAdapter.mScrollInfo;
                scrollInfo2.mPrevFirstHistoryView = null;
                scrollInfo2.mPrevLastHistoryView = null;
                scrollInfo2.mPrevHistoryCount = 0;
                scrollInfo2.mPrevFirstHistoryViewBottomMargin = 0;
                scrollInfo2.mPrevBodyLayoutHeght = 0;
            }
            SecNotificationBlockManager$$ExternalSyntheticOutline0.m(height, "moveDetailAdapterContentScroll B5 - isReceive scroll : ", str, ", receiveMoveScroll : ", "S.S.N.");
        }
    }

    /* JADX WARN: Type inference failed for: r8v38, types: [T, java.lang.String] */
    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelCommon, com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final void onBindDetailAdapterItemViewHolder(SubscreenNotificationDetailAdapter subscreenNotificationDetailAdapter, SubscreenNotificationDetailAdapter.ItemViewHolder itemViewHolder) throws Resources.NotFoundException {
        int dimensionPixelSize;
        ImageView imageView;
        int size;
        Resources resources;
        super.onBindDetailAdapterItemViewHolder(subscreenNotificationDetailAdapter, itemViewHolder);
        Log.d("S.S.N.", "onBindDetailAdapterItemViewHolder - B5");
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
        if (!isConversation(itemViewHolder.mInfo) || subscreenNotificationDetailAdapter.mItemPostionInGroup > 8) {
            ImageView imageView3 = this.keyboardReplyButton;
            if (imageView3 != null) {
                imageView3.setVisibility(8);
            }
            if (itemViewHolder.mInfo.mRemoteinput && (imageView = this.keyboardReplyButton) != null) {
                imageView.setVisibility(0);
            }
            itemViewHolder.mReplylayout.setVisibility(8);
            setEditButton(itemViewHolder);
            updateVisibilityForSmartReplyLayout(8);
            LinearLayout linearLayout = this.progressLayout;
            if (linearLayout != null) {
                linearLayout.setVisibility(8);
            }
            LinearLayout linearLayout2 = this.suggestResponsesBtn;
            if (linearLayout2 != null) {
                linearLayout2.setVisibility(8);
            }
            TextView textView3 = itemViewHolder.mTitle;
            CharSequence text = textView3.getText();
            if (text == null || text.length() == 0 || StringsKt__StringsKt.isBlank(textView3.getText())) {
                textView3.setVisibility(8);
                Context context = this.mDisplayContext;
                if (context == null) {
                    context = null;
                }
                dimensionPixelSize = context.getResources().getDimensionPixelSize(R.dimen.subscreen_noti_detail_content_top_margin_b5);
            } else {
                textView3.setVisibility(0);
                dimensionPixelSize = 0;
            }
            itemViewHolder.mBodyLayout.setBackgroundResource(R.drawable.subscreen_notification_detail_type_item_background_b5);
            itemViewHolder.mBodyLayout.setPadding(0, dimensionPixelSize, 0, 0);
            LinearLayout linearLayout3 = this.detailButtonContainer;
            if (linearLayout3 != null) {
                Context context2 = this.mDisplayContext;
                if (context2 == null) {
                    context2 = null;
                }
                linearLayout3.setPadding(0, 0, 0, context2.getResources().getDimensionPixelSize(R.dimen.subscreen_noti_cutout_area_height_b5));
            }
            LinearLayout linearLayout4 = itemViewHolder.mBodyLayout;
            Context context3 = this.mDisplayContext;
            if (context3 == null) {
                context3 = null;
            }
            linearLayout4.setMinimumHeight(context3.getResources().getDimensionPixelSize(R.dimen.subscreen_noti_detail_body_min_height_b5));
        } else {
            if (NotiRune.NOTI_SUBSCREEN_SUPPORT_SMART_REPLY_AI || NotiRune.NOTI_SUBSCREEN_SUPPORT_SMART_REPLY_AI_FOR_CHINA) {
                this.isPossibleAiReply = !this.isUnusableAccount;
                if (this.isSuggestResponsesEnabled || !this.suggestedRepliesDisabledByUser) {
                    ImageView imageView4 = this.smartReplyTriggerBtn;
                    if (imageView4 != null) {
                        imageView4.setVisibility(0);
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
                            neuralTranslator.refresh().addOnCompleteListener(new SubscreenDeviceModelB5$sam$com_samsung_android_sdk_scs_base_tasks_OnCompleteListener$0(new Function1() { // from class: com.android.systemui.statusbar.notification.SubscreenDeviceModelB5$$ExternalSyntheticLambda1
                                /* JADX WARN: Multi-variable type inference failed */
                                @Override // kotlin.jvm.functions.Function1
                                /* renamed from: invoke */
                                public final Object mo781invoke(Object obj) {
                                    int i = SubscreenDeviceModelB5.$r8$clinit;
                                    Task taskIdentifyLanguage = neuralTranslator.identifyLanguage((String) ref$ObjectRef.element);
                                    final SubscreenDeviceModelB5 subscreenDeviceModelB5 = this;
                                    taskIdentifyLanguage.addOnCompleteListener(new OnCompleteListener() { // from class: com.android.systemui.statusbar.notification.SubscreenDeviceModelB5$onBindDetailAdapterItemViewHolder$1$1
                                        @Override // com.samsung.android.sdk.scs.base.tasks.OnCompleteListener
                                        public final void onComplete(Task task) {
                                            if (task.isSuccessful()) {
                                                String strValueOf = String.valueOf(task.getResult());
                                                SubscreenDeviceModelB5 subscreenDeviceModelB52 = subscreenDeviceModelB5;
                                                boolean zAccess$isSupportableLanguage = SubscreenDeviceModelB5.access$isSupportableLanguage(subscreenDeviceModelB52, strValueOf);
                                                boolean zIsSuccessful = task.isSuccessful();
                                                boolean zIsComplete = task.isComplete();
                                                Object result = task.getResult();
                                                StringBuilder sbM = EmergencyButtonController$$ExternalSyntheticOutline0.m("onBindDetailAdapterItemViewHolder - successful : ", ", isComplete : ", ", result : ", zIsSuccessful, zIsComplete);
                                                sbM.append(result);
                                                sbM.append(", isSupport : ");
                                                sbM.append(zAccess$isSupportableLanguage);
                                                Log.d("S.S.N.", sbM.toString());
                                                if (!zAccess$isSupportableLanguage) {
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
                                    return Unit.INSTANCE;
                                }
                            })).getClass();
                        }
                    }
                } else if (this.isRDUMode) {
                    ImageView imageView5 = this.smartReplyTriggerBtn;
                    if (imageView5 != null) {
                        imageView5.setVisibility(0);
                    }
                } else {
                    ImageView imageView6 = this.smartReplyTriggerBtn;
                    if (imageView6 != null) {
                        imageView6.setVisibility(8);
                    }
                }
            } else {
                Log.d("S.S.N.", " NOT SUPPORT SMART REPLY AI");
                LinearLayout linearLayout5 = this.progressLayout;
                if (linearLayout5 != null) {
                    linearLayout5.setVisibility(8);
                }
                updateVisibilityForSmartReplyLayout(8);
            }
            ImageView imageView7 = this.keyboardReplyButton;
            if (imageView7 != null) {
                imageView7.setVisibility(0);
            }
            itemViewHolder.inflateReplyWord();
            itemViewHolder.mReplylayout.setVisibility(0);
            setEditButton(itemViewHolder);
            ImageView imageView8 = (ImageView) itemViewHolder.mReplylayout.findViewById(R.id.subscreen_detail_word_line_top);
            if (imageView8 != null) {
                imageView8.setVisibility(8);
            }
            itemViewHolder.mTitle.setVisibility(8);
            itemViewHolder.mBodyLayout.setBackgroundColor(0);
            LinearLayout linearLayout6 = itemViewHolder.mBodyLayout;
            Context context4 = this.mDisplayContext;
            if (context4 == null) {
                context4 = null;
            }
            linearLayout6.setPadding(0, context4.getResources().getDimensionPixelSize(R.dimen.subscreen_noti_detail_conversation_top_padding_b5), 0, 0);
            LinearLayout linearLayout7 = itemViewHolder.mBodyLayout;
            Context context5 = this.mDisplayContext;
            if (context5 == null) {
                context5 = null;
            }
            Integer numValueOf = (context5 == null || (resources = context5.getResources()) == null) ? null : Integer.valueOf(resources.getDimensionPixelSize(R.dimen.subscreen_noti_detail_conversation_body_min_height_b5));
            numValueOf.getClass();
            linearLayout7.setMinimumHeight(numValueOf.intValue());
            LinearLayout linearLayout8 = this.detailButtonContainer;
            if (linearLayout8 != null) {
                linearLayout8.setPadding(0, 0, 0, 0);
            }
            LinearLayout linearLayout9 = this.detailButtonContainer;
            if (linearLayout9 != null) {
                linearLayout9.measure(0, 0);
            }
            Context context6 = this.mDisplayContext;
            if (context6 == null) {
                context6 = null;
            }
            int dimensionPixelSize2 = context6.getResources().getDimensionPixelSize(R.dimen.subscreen_noti_detail_icon_button_bg_size_b5);
            Context context7 = this.mDisplayContext;
            if (context7 == null) {
                context7 = null;
            }
            int dimensionPixelSize3 = context7.getResources().getDimensionPixelSize(R.dimen.subscreen_noti_detail_icon_button_shadow_margin_b5);
            LinearLayout linearLayout10 = this.detailButtonContainer;
            Integer numValueOf2 = linearLayout10 != null ? Integer.valueOf(linearLayout10.getMeasuredHeight()) : null;
            numValueOf2.getClass();
            int iIntValue = (numValueOf2.intValue() - (dimensionPixelSize2 / 2)) - dimensionPixelSize3;
            Context context8 = this.mDisplayContext;
            if (context8 == null) {
                context8 = null;
            }
            int dimensionPixelSize4 = context8.getResources().getDimensionPixelSize(R.dimen.subscreen_noti_detail_reply_container_top_margin_b5);
            LinearLayout linearLayout11 = itemViewHolder.mReplyContainer;
            if (linearLayout11 != null) {
                linearLayout11.setPadding(linearLayout11.getPaddingLeft(), dimensionPixelSize4 + iIntValue, linearLayout11.getPaddingRight(), linearLayout11.getPaddingBottom());
            }
            Context context9 = this.mDisplayContext;
            if (context9 == null) {
                context9 = null;
            }
            int dimensionPixelSize5 = context9.getResources().getDimensionPixelSize(R.dimen.subscreen_noti_detail_ai_disclaimer_button_margin_top_b5);
            LinearLayout linearLayout12 = (LinearLayout) itemViewHolder.itemView.findViewById(R.id.smart_reply_ai_disclaimer_layout);
            if (linearLayout12 != null) {
                linearLayout12.setPadding(linearLayout12.getPaddingLeft(), iIntValue + dimensionPixelSize5, linearLayout12.getPaddingRight(), linearLayout12.getPaddingBottom());
            }
        }
        itemViewHolder.mOpenAppButton.setVisibility(8);
        TextView textView4 = this.callBackButtonText;
        if (textView4 != null) {
            textView4.setVisibility(itemViewHolder.mCallBackButton.getVisibility());
        }
        TextView textView5 = this.replyButtonText;
        if (textView5 != null) {
            ImageView imageView9 = this.keyboardReplyButton;
            Integer numValueOf3 = imageView9 != null ? Integer.valueOf(imageView9.getVisibility()) : null;
            numValueOf3.getClass();
            textView5.setVisibility(numValueOf3.intValue());
        }
        TextView textView6 = this.openAppButtonText;
        if (textView6 != null) {
            textView6.setVisibility(itemViewHolder.mOpenAppButton.getVisibility());
        }
        TextView textView7 = this.clearButtonText;
        if (textView7 != null) {
            textView7.setVisibility(itemViewHolder.mClearButton.getVisibility());
        }
        TextView textView8 = this.smartReplyTriggerBtnText;
        if (textView8 != null) {
            ImageView imageView10 = this.smartReplyTriggerBtn;
            textView8.setVisibility(imageView10 != null ? imageView10.getVisibility() : 8);
        }
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
        if (textView2 != null) {
            textView2.setVisibility(textViewHolder.mClearButton.getVisibility());
        }
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
        try {
            Intent intent = new Intent();
            intent.setAction(str);
            PendingIntent broadcast = PendingIntent.getBroadcast(this.mContext, 0, intent, 335544320);
            broadcast.getClass();
            requestOpenPhonePopup(broadcast);
        } catch (Exception e) {
            KeyguardSecSimPinViewController$$ExternalSyntheticOutline0.m("exception on openPhonePopupForIntelligenceSettings: ", e, "S.S.N.");
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
        IntentFilter intentFilterM = AppCompatDelegateImpl$AutoBatteryNightModeManager$$ExternalSyntheticOutline0.m("com.samsung.android.app.aodservice.intent.action.CHANGE_AOD_MODE");
        this.mContext.registerReceiverAsUser(this.aodTspUpdateReceiver, UserHandle.ALL, intentFilterM, "com.samsung.android.app.aodservice.permission.BROADCAST_RECEIVER", null, 4);
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
    public final void replyActivityFinished(boolean z) throws Resources.NotFoundException {
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
            } else {
                subscreenSubRoomNotification.mSubscreenMainLayout.setAlpha(1.0f);
            }
            ListPopupWindow$$ExternalSyntheticOutline0.m(i, "replyActivityFinished() - header visibility: ", "S.S.N.");
        }
    }

    public final void requestOpenPhonePopup(PendingIntent pendingIntent) {
        SubRoom.StateChangeListener stateChangeListener;
        Intent intent = new Intent();
        intent.putExtra("runOnCover", false);
        intent.putExtra("ignoreKeyguardState", true);
        intent.putExtra("showCoverToast", true);
        SubscreenSubRoomNotification subscreenSubRoomNotification = this.mSubRoomNotification;
        if (subscreenSubRoomNotification == null || (stateChangeListener = subscreenSubRoomNotification.mStateChangeListener) == null) {
            return;
        }
        stateChangeListener.requestCoverPopup(pendingIntent, intent);
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
        DateTimeView dateTimeViewFindViewById;
        NotificationChildrenContainer notificationChildrenContainer;
        if (view == null || (dateTimeViewFindViewById = view.findViewById(R.id.subscreen_notification_clock)) == null || subscreenNotificationInfo == null) {
            return;
        }
        if (!subscreenNotificationInfo.mShowWhen || subscreenNotificationInfo.mWhen <= 0) {
            dateTimeViewFindViewById.setVisibility(8);
            return;
        }
        if (!subscreenNotificationInfo.mIsMessagingStyle || subscreenNotificationInfo.mMessageingStyleInfoArray.size() <= 0) {
            long j = subscreenNotificationInfo.mWhen;
            if (subscreenNotificationInfo.mSbn.getNotification().isGroupSummary() && (notificationChildrenContainer = subscreenNotificationInfo.mRow.mChildrenContainer) != null) {
                j = notificationChildrenContainer.mWhenMillis;
            }
            dateTimeViewFindViewById.setTime(j);
            dateTimeViewFindViewById.setVisibility(0);
            return;
        }
        long j2 = ((SubscreenNotificationInfo.MessagingStyleInfo) AlertController$$ExternalSyntheticOutline0.m(1, subscreenNotificationInfo.mMessageingStyleInfoArray)).mPostedTime;
        long j3 = ((SubscreenNotificationInfo.MessagingStyleInfo) AlertController$$ExternalSyntheticOutline0.m(1, subscreenNotificationInfo.mMessageingStyleInfoArray)).mTimeStamp;
        if (j2 <= 0) {
            j2 = j3;
        }
        dateTimeViewFindViewById.setTime(j2);
        dateTimeViewFindViewById.setVisibility(0);
    }

    /* JADX WARN: Removed duplicated region for block: B:115:0x0243  */
    /* JADX WARN: Removed duplicated region for block: B:119:0x024d  */
    /* JADX WARN: Removed duplicated region for block: B:122:0x0258  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x0113  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x0124  */
    /* JADX WARN: Removed duplicated region for block: B:67:0x0163  */
    /* JADX WARN: Removed duplicated region for block: B:72:0x0178  */
    /* JADX WARN: Removed duplicated region for block: B:73:0x0182  */
    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void setContentViewItem(Context context, SubscreenNotificationDetailAdapter.ItemViewHolder itemViewHolder) throws Resources.NotFoundException {
        boolean z;
        int i;
        Drawable drawable;
        int i2;
        int i3;
        int i4;
        String str;
        ArrayList arrayList;
        long j;
        long j2;
        SubscreenDeviceModelB5 subscreenDeviceModelB5 = this;
        boolean zIsConversation = subscreenDeviceModelB5.isConversation(itemViewHolder.mInfo);
        View view = subscreenDeviceModelB5.mHeaderViewLayout;
        TextView textView = view != null ? (TextView) view.findViewById(R.id.subscreen_header_app_name) : null;
        CharSequence text = itemViewHolder.mTitle.getText();
        subscreenDeviceModelB5.titleText = text;
        if (textView != null) {
            if (!zIsConversation) {
                text = itemViewHolder.mInfo.mAppName;
            }
            textView.setText(text);
        }
        String str2 = "S.S.N.";
        if (!zIsConversation) {
            Log.d("S.S.N.", "setContentViewItem B5 - key : " + itemViewHolder.mInfo.mKey);
            View viewInflate = LayoutInflater.from(context).inflate(R.layout.subscreen_notification_detail_adapter_content_layout_item_b5, (ViewGroup) itemViewHolder.mContentLayout, false);
            SubscreenNotificationInfo subscreenNotificationInfo = itemViewHolder.mInfo;
            String str3 = subscreenNotificationInfo.mBigText;
            if (str3 == null) {
                str3 = subscreenNotificationInfo.mContent;
            }
            bindContent(viewInflate, str3, itemViewHolder);
            Bitmap bitmap = itemViewHolder.mInfo.mBitmap;
            if (bitmap != null) {
                TextView textView2 = (TextView) viewInflate.findViewById(R.id.detail_content_text);
                ImageView imageView = (ImageView) viewInflate.findViewById(R.id.detail_content_image);
                float width = bitmap.getWidth();
                float height = bitmap.getHeight();
                float dimensionPixelSize = context.getResources().getDimensionPixelSize(R.dimen.subscreen_noti_detail_content_image_size_b5);
                float f = 2 * dimensionPixelSize;
                StringBuilder sbM = CubicBezierEasing$$ExternalSyntheticOutline0.m("bindImageBitmap bitmapWidth : ", width, " bitmapHeight : ", height, " viewWidth : ");
                sbM.append(dimensionPixelSize);
                sbM.append(" viewHeight : ");
                sbM.append(f);
                Log.d("S.S.N.", sbM.toString());
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) (imageView != null ? imageView.getLayoutParams() : null);
                layoutParams.gravity = 3;
                layoutParams.width = width > (((float) 200) * dimensionPixelSize) / ((float) IKnoxCustomManager.Stub.TRANSACTION_getAsoc) ? (int) dimensionPixelSize : (int) width;
                layoutParams.topMargin = (textView2 == null || textView2.getVisibility() != 8) ? context.getResources().getDimensionPixelSize(R.dimen.subscreen_noti_detail_content_image_top_margin_b5) : 0;
                if (imageView != null) {
                    imageView.setLayoutParams(layoutParams);
                }
                if (imageView != null) {
                    imageView.setMaxHeight((int) f);
                }
                if (imageView != null) {
                    imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                }
                if (imageView != null) {
                    imageView.setVisibility(0);
                }
                if (imageView != null) {
                    imageView.setImageBitmap(bitmap);
                }
            }
            SubscreenNotificationInfo subscreenNotificationInfo2 = itemViewHolder.mInfo;
            if (subscreenNotificationInfo2.mShowWhen) {
                bindTime(viewInflate, subscreenNotificationInfo2.mWhen, itemViewHolder);
            }
            itemViewHolder.mContentLayout.addView(viewInflate);
            return;
        }
        int dimensionPixelSize2 = context.getResources().getDimensionPixelSize(R.dimen.subscreen_noti_detail_conversation_bottom_margin_b5);
        int dimensionPixelSize3 = context.getResources().getDimensionPixelSize(R.dimen.subscreen_noti_detail_conversation_bottom_margin_small_b5);
        int dimensionPixelSize4 = context.getResources().getDimensionPixelSize(R.dimen.subscreen_noti_detail_conversation_bottom_margin_last_b5);
        int dimensionPixelSize5 = context.getResources().getDimensionPixelSize(R.dimen.subscreen_noti_detail_icon_button_bg_size_b5);
        ArrayList arrayList2 = itemViewHolder.mInfo.mMessageingStyleInfoArray;
        int size = arrayList2.size();
        SecNotificationBlockManager$$ExternalSyntheticOutline0.m(size, "setContentViewItem B5 - conversation key : ", itemViewHolder.mInfo.mKey, ", size : ", "S.S.N.");
        int size2 = arrayList2.size();
        int i5 = 0;
        int i6 = 0;
        while (i6 < size2) {
            Object obj = arrayList2.get(i6);
            i6++;
            SubscreenNotificationInfo.MessagingStyleInfo messagingStyleInfo = (SubscreenNotificationInfo.MessagingStyleInfo) obj;
            String str4 = str2;
            long j3 = subscreenDeviceModelB5.firstHistoryItemPostTimeInDetailAdapter;
            int i7 = (int) j3;
            if ((i7 == 0 || j3 > messagingStyleInfo.mPostedTime) && messagingStyleInfo.mIsChecked) {
                i5++;
                str2 = str4;
            } else {
                if (i7 == 0) {
                    subscreenDeviceModelB5.firstHistoryItemPostTimeInDetailAdapter = messagingStyleInfo.mPostedTime;
                }
                View viewInflate2 = LayoutInflater.from(context).inflate(messagingStyleInfo.mIsReply ? R.layout.subscreen_notification_detail_adapter_conversation_send_b5 : R.layout.subscreen_notification_detail_adapter_conversation_received_b5, (ViewGroup) itemViewHolder.mContentLayout, false);
                LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) viewInflate2.getLayoutParams();
                ImageView imageView2 = (ImageView) viewInflate2.findViewById(R.id.detail_conversation_icon);
                if (imageView2 != null) {
                    imageView2.setImageIcon(null);
                }
                String str5 = itemViewHolder.mPrevSender;
                int i8 = dimensionPixelSize2;
                boolean z2 = str5 != null && str5.equals(messagingStyleInfo.mSender);
                if (Notification.MessagingStyle.class.equals(itemViewHolder.mInfo.mSbn.getNotification().getNotificationStyle())) {
                    NotificationEntry notificationEntry = itemViewHolder.mInfo.mRow.mEntry;
                    if (!(notificationEntry != null ? "com.viber.voip".equals(notificationEntry.mSbn.getPackageName()) : false)) {
                        z = false;
                    }
                    if (!((z | itemViewHolder.mInfo.mIsGroupConversation) & (messagingStyleInfo.mIsReply ^ true))) {
                    }
                    bindContent(viewInflate2, messagingStyleInfo.mContentText, itemViewHolder);
                    drawable = messagingStyleInfo.mUriImage;
                    if (drawable != null) {
                    }
                    i5++;
                    if (size == i5) {
                        arrayList = arrayList2;
                        j = 0;
                        j2 = messagingStyleInfo.mPostedTime;
                        if (j2 <= j) {
                        }
                        bindTime(viewInflate2, j2, itemViewHolder);
                        layoutParams2.bottomMargin = i8 - i2;
                        if (i5 == size) {
                        }
                        viewInflate2.setLayoutParams(layoutParams2);
                        itemViewHolder.mContentLayout.addView(viewInflate2);
                        subscreenDeviceModelB5 = this;
                        arrayList2 = arrayList;
                        str2 = str;
                        size2 = i;
                        dimensionPixelSize2 = i8;
                        dimensionPixelSize3 = i2;
                        dimensionPixelSize4 = i3;
                        dimensionPixelSize5 = i4;
                    }
                } else {
                    z = true;
                    if (!(z | itemViewHolder.mInfo.mIsGroupConversation) || !(!messagingStyleInfo.mIsReply)) {
                        if (!z2 || i5 == 0) {
                            String str6 = messagingStyleInfo.mSender;
                            TextView textView3 = (TextView) viewInflate2.findViewById(R.id.detail_sender_text);
                            i = size2;
                            if (textView3 != null) {
                                textView3.setVisibility(0);
                            }
                            if (textView3 != null) {
                                textView3.setText(str6);
                            }
                            itemViewHolder.mPrevSender = str6;
                            itemViewHolder.mBodyLayoutString = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(itemViewHolder.mBodyLayoutString, str6);
                            if (imageView2 != null) {
                                imageView2.setVisibility(0);
                            }
                        } else {
                            if (imageView2 != null) {
                                imageView2.setVisibility(4);
                            }
                            i = size2;
                        }
                        if (imageView2 != null) {
                            imageView2.setVisibility(8);
                        }
                    } else {
                        i = size2;
                        itemViewHolder.mPrevSender = null;
                        if (imageView2 != null) {
                            imageView2.setVisibility(8);
                        }
                    }
                    bindContent(viewInflate2, messagingStyleInfo.mContentText, itemViewHolder);
                    drawable = messagingStyleInfo.mUriImage;
                    if (drawable != null) {
                        i2 = dimensionPixelSize3;
                        i3 = dimensionPixelSize4;
                        i4 = dimensionPixelSize5;
                        str = str4;
                    } else {
                        ImageView imageView3 = (ImageView) viewInflate2.findViewById(R.id.detail_content_image);
                        if (imageView3 != null) {
                            imageView3.setVisibility(0);
                        }
                        int intrinsicWidth = drawable.getIntrinsicWidth();
                        int intrinsicHeight = drawable.getIntrinsicHeight();
                        i2 = dimensionPixelSize3;
                        Context context2 = subscreenDeviceModelB5.mDisplayContext;
                        if (context2 == null) {
                            context2 = null;
                        }
                        int dimensionPixelSize6 = context2.getResources().getDimensionPixelSize(R.dimen.subscreen_noti_detail_conversation_image_minimum_size_b5);
                        if (intrinsicWidth >= dimensionPixelSize6 || intrinsicHeight >= dimensionPixelSize6) {
                            i3 = dimensionPixelSize4;
                            i4 = dimensionPixelSize5;
                            str = str4;
                        } else {
                            i3 = dimensionPixelSize4;
                            i4 = dimensionPixelSize5;
                            str = str4;
                            RecyclerView$$ExternalSyntheticOutline0.m(dimensionPixelSize6, str, MutableObjectList$$ExternalSyntheticOutline0.m(intrinsicWidth, intrinsicHeight, "resizing image. drawable width = ", ", height = ", " | minimum = "));
                            LinearLayout.LayoutParams layoutParams3 = (LinearLayout.LayoutParams) (imageView3 != null ? imageView3.getLayoutParams() : null);
                            if (intrinsicWidth > intrinsicHeight) {
                                layoutParams3.width = dimensionPixelSize6;
                            } else {
                                layoutParams3.height = dimensionPixelSize6;
                            }
                            if (imageView3 != null) {
                                imageView3.setLayoutParams(layoutParams3);
                            }
                            if (imageView3 != null) {
                                imageView3.setScaleType(ImageView.ScaleType.CENTER_CROP);
                            }
                        }
                        if (imageView3 != null) {
                            imageView3.setImageDrawable(drawable);
                        }
                    }
                    i5++;
                    if (size == i5 && messagingStyleInfo.mIsReply == ((SubscreenNotificationInfo.MessagingStyleInfo) arrayList2.get(i5)).mIsReply) {
                        SubscreenNotificationInfo.MessagingStyleInfo messagingStyleInfo2 = (SubscreenNotificationInfo.MessagingStyleInfo) arrayList2.get(i5);
                        j = 0;
                        long j4 = messagingStyleInfo.mPostedTime;
                        if (j4 <= 0) {
                            j4 = messagingStyleInfo.mTimeStamp;
                        }
                        arrayList = arrayList2;
                        long j5 = messagingStyleInfo2.mPostedTime;
                        if (j5 <= 0) {
                            j5 = messagingStyleInfo2.mTimeStamp;
                        }
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
                        Date date = new Date(j4);
                        Date date2 = new Date(j5);
                        String str7 = simpleDateFormat.format(date);
                        String str8 = simpleDateFormat.format(date2);
                        String str9 = messagingStyleInfo.mSender;
                        String str10 = messagingStyleInfo2.mSender;
                        if (!Intrinsics.areEqual(str7, str8) || !Intrinsics.areEqual(str9, str10)) {
                        }
                        if (i5 == size) {
                            layoutParams2.bottomMargin = ((i4 / 2) + i3) - i2;
                        }
                        viewInflate2.setLayoutParams(layoutParams2);
                        itemViewHolder.mContentLayout.addView(viewInflate2);
                        subscreenDeviceModelB5 = this;
                        arrayList2 = arrayList;
                        str2 = str;
                        size2 = i;
                        dimensionPixelSize2 = i8;
                        dimensionPixelSize3 = i2;
                        dimensionPixelSize4 = i3;
                        dimensionPixelSize5 = i4;
                    } else {
                        arrayList = arrayList2;
                        j = 0;
                    }
                    j2 = messagingStyleInfo.mPostedTime;
                    if (j2 <= j) {
                        j2 = messagingStyleInfo.mTimeStamp;
                    }
                    bindTime(viewInflate2, j2, itemViewHolder);
                    layoutParams2.bottomMargin = i8 - i2;
                    if (i5 == size) {
                    }
                    viewInflate2.setLayoutParams(layoutParams2);
                    itemViewHolder.mContentLayout.addView(viewInflate2);
                    subscreenDeviceModelB5 = this;
                    arrayList2 = arrayList;
                    str2 = str;
                    size2 = i;
                    dimensionPixelSize2 = i8;
                    dimensionPixelSize3 = i2;
                    dimensionPixelSize4 = i3;
                    dimensionPixelSize5 = i4;
                }
            }
        }
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public void setDimOnMainBackground(View view) {
        view.setBackgroundResource(R.drawable.subscreen_notification_main_layout_background_b5);
        view.setClipToOutline(true);
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final void setFullPopupWindowKeyEventListener(FrameLayout frameLayout) {
        if (frameLayout != null) {
            frameLayout.addOnUnhandledKeyEventListener(new View.OnUnhandledKeyEventListener() { // from class: com.android.systemui.statusbar.notification.SubscreenDeviceModelB5.setFullPopupWindowKeyEventListener.1
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
    public final void setGroupAdapterFooterMargin(Context context, SubscreenNotificationGroupAdapter.FooterViewHolder footerViewHolder) throws Resources.NotFoundException {
        RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) footerViewHolder.itemView.getLayoutParams();
        context.getResources().getDimensionPixelSize(R.dimen.subscreen_noti_group_footer_top_margin_b5);
        ((ViewGroup.MarginLayoutParams) layoutParams).topMargin = 0;
        footerViewHolder.itemView.setLayoutParams(layoutParams);
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0036  */
    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void setGroupAdapterIcon(Context context, SubscreenNotificationGroupAdapter subscreenNotificationGroupAdapter, SubscreenParentItemViewHolder subscreenParentItemViewHolder) throws Resources.NotFoundException {
        boolean z;
        boolean z2 = true;
        if (!isNotShwonNotificationState(subscreenParentItemViewHolder.mInfo.mRow.mEntry)) {
            z = true;
        } else if (isKnoxSecurity(subscreenParentItemViewHolder.mInfo.mRow.mEntry) && subscreenParentItemViewHolder.mInfo.mRow.mEntry.mUserPublic) {
            z = false;
        } else if (isKeyguardStats()) {
            z = !subscreenParentItemViewHolder.mInfo.mRow.needsRedaction();
        }
        subscreenParentItemViewHolder.setIconView(subscreenNotificationGroupAdapter, z);
        setRightIcon(context, subscreenParentItemViewHolder.mInfo, subscreenParentItemViewHolder.itemView);
        if (subscreenParentItemViewHolder.mInfo.mRow.isInsignificant()) {
            return;
        }
        SubscreenDeviceModelParent.updateTwoPhoneIcon(subscreenParentItemViewHolder.mTwoPhoneIcon, subscreenParentItemViewHolder.mInfo);
        super.updateKnoxIcon(subscreenParentItemViewHolder.mSecureIcon, subscreenParentItemViewHolder.mInfo);
        LinearLayout linearLayout = (LinearLayout) subscreenParentItemViewHolder.itemView.findViewById(R.id.subscreen_noti_list_icon_layout);
        SubscreenNotificationInfo subscreenNotificationInfo = subscreenParentItemViewHolder.mInfo;
        boolean z3 = subscreenNotificationInfo.mIsMessagingStyle;
        if (!"MESSAGE_KT_TWO_PHONE_OPPOSITE_RECEIVED".equals(subscreenNotificationInfo.mSbn.getNotification().getGroup()) && !z3) {
            z2 = false;
        }
        if (linearLayout != null) {
            linearLayout.setVisibility(z2 ? 0 : 8);
        }
        if (z2) {
            return;
        }
        Context context2 = this.mDisplayContext;
        if (context2 == null) {
            context2 = null;
        }
        int dimensionPixelSize = context2.getResources().getDimensionPixelSize(R.dimen.subscreen_noti_no_icon_text_layout_start_margin_b5);
        LinearLayout linearLayout2 = (LinearLayout) subscreenParentItemViewHolder.itemView.findViewById(R.id.subscreen_notification_text_layout);
        if (linearLayout2 != null) {
            ((LinearLayout.LayoutParams) linearLayout2.getLayoutParams()).setMarginStart(dimensionPixelSize);
        }
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final void setIsReplySendButtonLoading() {
        View viewFindViewById;
        SubscreenNotificationDetailAdapter subscreenNotificationDetailAdapter;
        SubscreenSubRoomNotification subscreenSubRoomNotification = this.mSubRoomNotification;
        View view = (subscreenSubRoomNotification == null || (subscreenNotificationDetailAdapter = subscreenSubRoomNotification.mNotificationDetailAdapter) == null) ? null : subscreenNotificationDetailAdapter.mReplyButtonView;
        if (view == null || (viewFindViewById = view.findViewById(R.id.send)) == null) {
            return;
        }
        viewFindViewById.setEnabled(true);
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final void setItemDecoration(final RecyclerView recyclerView) throws Resources.NotFoundException {
        Context context = this.mDisplayContext;
        if (context == null) {
            context = null;
        }
        final int dimensionPixelSize = context.getResources().getDimensionPixelSize(R.dimen.subscreen_noti_list_top_margin_b5);
        Context context2 = this.mDisplayContext;
        if (context2 == null) {
            context2 = null;
        }
        final int dimensionPixelSize2 = context2.getResources().getDimensionPixelSize(R.dimen.subscreen_noti_list_horizontal_margin_b5);
        Context context3 = this.mDisplayContext;
        if (context3 == null) {
            context3 = null;
        }
        final int dimensionPixelSize3 = context3.getResources().getDimensionPixelSize(R.dimen.subscreen_noti_list_footer_top_margin_b5);
        Context context4 = this.mDisplayContext;
        final int dimensionPixelSize4 = (context4 != null ? context4 : null).getResources().getDimensionPixelSize(R.dimen.subscreen_noti_ongoing_section_margin_b5);
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() { // from class: com.android.systemui.statusbar.notification.SubscreenDeviceModelB5.setItemDecoration.1
            @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
            public final void getItemOffsets(Rect rect, View view, RecyclerView recyclerView2, RecyclerView.State state) {
                ArrayList arrayList;
                ArrayList arrayList2;
                RecyclerView.Adapter adapter = recyclerView.mAdapter;
                SubscreenDeviceModelB5 subscreenDeviceModelB5 = this;
                SubscreenSubRoomNotification subscreenSubRoomNotification = subscreenDeviceModelB5.mSubRoomNotification;
                SubscreenNotificationInfo subscreenNotificationInfo = null;
                if (Intrinsics.areEqual(adapter, subscreenSubRoomNotification != null ? subscreenSubRoomNotification.mNotificationListAdapter : null)) {
                    SubscreenSubRoomNotification subscreenSubRoomNotification2 = subscreenDeviceModelB5.mSubRoomNotification;
                    SubscreenNotificationInfoManager subscreenNotificationInfoManager = subscreenSubRoomNotification2 != null ? subscreenSubRoomNotification2.mNotificationInfoManager : null;
                    if (subscreenNotificationInfoManager == null || SubscreenNotificationInfoManager.getSubscreenNotificationInfoListSize() != 0) {
                        boolean z = recyclerView2.getChildViewHolder(view) instanceof SubscreenNotificationListAdapter.FooterViewHolder;
                        if (RecyclerView.getChildAdapterPosition(view) == 0) {
                            rect.top = subscreenDeviceModelB5.listFirstTopMargin;
                        } else {
                            rect.top = z ? dimensionPixelSize3 : dimensionPixelSize;
                        }
                        if (!z) {
                            int i = dimensionPixelSize2;
                            rect.left = i;
                            rect.right = i;
                        }
                        if (z) {
                            rect.bottom = subscreenDeviceModelB5.footerBottomMargin;
                        }
                        int childAdapterPosition = RecyclerView.getChildAdapterPosition(view);
                        if (subscreenDeviceModelB5.mController.isZenMode() && childAdapterPosition == 0) {
                            return;
                        }
                        int iConvertAdapterPositionToInfoIndex = subscreenDeviceModelB5.convertAdapterPositionToInfoIndex(childAdapterPosition);
                        int i2 = iConvertAdapterPositionToInfoIndex - 1;
                        boolean z2 = false;
                        int subscreenNotificationInfoListSize = subscreenNotificationInfoManager != null ? SubscreenNotificationInfoManager.getSubscreenNotificationInfoListSize() : 0;
                        SubscreenNotificationInfo subscreenNotificationInfo2 = (childAdapterPosition < 0 || childAdapterPosition >= subscreenNotificationInfoListSize || subscreenNotificationInfoManager == null || (arrayList2 = SubscreenNotificationInfoManager.mSubscreenNotificationInfoList) == null) ? null : (SubscreenNotificationInfo) arrayList2.get(iConvertAdapterPositionToInfoIndex);
                        if (i2 >= 0 && i2 < subscreenNotificationInfoListSize && subscreenNotificationInfoManager != null && (arrayList = SubscreenNotificationInfoManager.mSubscreenNotificationInfoList) != null) {
                            subscreenNotificationInfo = (SubscreenNotificationInfo) arrayList.get(i2);
                        }
                        boolean z3 = subscreenNotificationInfo != null && subscreenNotificationInfo.mRow.mEntry.isOngoingActivity() && subscreenNotificationInfo.mRow.mEntry.isPromotedState();
                        if (subscreenNotificationInfo2 != null && subscreenNotificationInfo2.mRow.mEntry.isOngoingActivity() && subscreenNotificationInfo2.mRow.mEntry.isPromotedState()) {
                            z2 = true;
                        }
                        if (!z3 || z2) {
                            return;
                        }
                        rect.top = dimensionPixelSize4;
                    }
                }
            }
        });
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final void setListItemTextLayout(Context context, View view) throws Resources.NotFoundException {
        if (view == null) {
            return;
        }
        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.subscreen_notification_text_layout);
        TextView textView = (TextView) view.findViewById(R.id.subscreen_notification_title_text);
        DateTimeView dateTimeViewFindViewById = view.findViewById(R.id.subscreen_notification_clock);
        TextView textView2 = (TextView) view.findViewById(R.id.hide_content_app_name);
        LinearLayout linearLayout2 = (LinearLayout) view.findViewById(R.id.subscreen_noti_list_icon_layout);
        ImageView imageView = (ImageView) view.findViewById(R.id.subscreen_right_icon);
        ImageView imageView2 = (ImageView) view.findViewById(R.id.two_phone_icon);
        ImageView imageView3 = (ImageView) view.findViewById(R.id.secure_icon);
        FrameLayout frameLayout = (FrameLayout) view.findViewById(R.id.unread_message_count_layout);
        TextView textView3 = (TextView) view.findViewById(R.id.noti_group_count);
        if (linearLayout != null) {
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) linearLayout.getLayoutParams();
            if (imageView != null && imageView.getVisibility() == 0) {
                layoutParams.setMarginEnd(0);
            }
            linearLayout.setLayoutParams(layoutParams);
        }
        int subScreenCardWidth = this.mController.getSubScreenCardWidth(context);
        int dimensionPixelSize = (((linearLayout2 == null || linearLayout2.getVisibility() != 0) ? subScreenCardWidth - context.getResources().getDimensionPixelSize(R.dimen.subscreen_noti_no_icon_text_layout_start_margin_b5) : subScreenCardWidth - context.getResources().getDimensionPixelSize(R.dimen.subscreen_noti_list_icon_area_width_b5)) - context.getResources().getDimensionPixelSize(R.dimen.subscreen_noti_list_end_margin_b5)) - 30;
        int dimensionPixelSize2 = context.getResources().getDimensionPixelSize(R.dimen.subscreen_noti_list_right_icon_start_margin_b5);
        if (textView3 != null && textView3.getVisibility() == 0) {
            dimensionPixelSize -= context.getResources().getDimensionPixelSize(R.dimen.subscreen_noti_count_text_width_b5) + dimensionPixelSize2;
        }
        if (imageView != null && imageView.getVisibility() == 0) {
            int iM = StrongAuthPopup$$ExternalSyntheticOutline0.m(context, R.dimen.subscreen_noti_right_icon_size_b5, dimensionPixelSize2);
            if (textView3 == null) {
                iM = StrongAuthPopup$$ExternalSyntheticOutline0.m(context, R.dimen.subscreen_noti_list_right_icon_end_margin_b5, iM);
            }
            dimensionPixelSize -= iM;
        }
        if (dateTimeViewFindViewById != null && dateTimeViewFindViewById.getVisibility() == 0) {
            int dimensionPixelSize3 = context.getResources().getDimensionPixelSize(R.dimen.subscreen_noti_clock_start_margin_b5);
            dateTimeViewFindViewById.measure(View.MeasureSpec.makeMeasureSpec(context.getResources().getDimensionPixelSize(R.dimen.subscreen_noti_top_popup_clock_width_b5), Integer.MIN_VALUE), 0);
            dimensionPixelSize = (dimensionPixelSize - dimensionPixelSize3) - dateTimeViewFindViewById.getMeasuredWidth();
        }
        int dimensionPixelSize4 = context.getResources().getDimensionPixelSize(R.dimen.subscreen_noti_extra_icon_margin_b5);
        if (imageView2 != null && imageView2.getVisibility() == 0) {
            dimensionPixelSize = (dimensionPixelSize - context.getResources().getDimensionPixelSize(R.dimen.subscreen_noti_two_phone_icon_width_b5)) - dimensionPixelSize4;
        }
        if (imageView3 != null && imageView3.getVisibility() == 0) {
            dimensionPixelSize = (dimensionPixelSize - context.getResources().getDimensionPixelSize(R.dimen.subscreen_noti_secure_icon_size_b5)) - dimensionPixelSize4;
        }
        if (frameLayout != null && frameLayout.getVisibility() == 0) {
            frameLayout.measure(View.MeasureSpec.makeMeasureSpec(90, Integer.MIN_VALUE), 0);
            dimensionPixelSize = (dimensionPixelSize - frameLayout.getMeasuredWidth()) - context.getResources().getDimensionPixelSize(R.dimen.subscreen_noti_count_start_margin_b5);
        }
        if (textView != null) {
            textView.setMaxWidth(dimensionPixelSize);
        }
        if (textView2 != null) {
            textView2.setMaxWidth(dimensionPixelSize);
        }
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelCommon
    public final void setPopupItemInfo(Context context, NotificationEntry notificationEntry, boolean z) throws Resources.NotFoundException {
        View view;
        FrameLayout frameLayout;
        LinearLayout linearLayout;
        View view2;
        TextView textView;
        SubscreenNotificationInfo subscreenNotificationInfo;
        ArrayList arrayList;
        SubscreenNotificationInfo.MessagingStyleInfo messagingStyleInfo;
        String str;
        super.setPopupItemInfo(context, notificationEntry, z);
        SubscreenNotificationInfo subscreenNotificationInfo2 = this.popupInfo;
        boolean z2 = subscreenNotificationInfo2 != null ? subscreenNotificationInfo2.mIsGroupConversation : false;
        if (!z && !this.needsRedaction && z2 && (view2 = this.mPopUpViewLayout) != null && (textView = (TextView) view2.findViewById(R.id.subscreen_notification_sender_text)) != null && (subscreenNotificationInfo = this.popupInfo) != null && (arrayList = subscreenNotificationInfo.mMessageingStyleInfoArray) != null && (messagingStyleInfo = (SubscreenNotificationInfo.MessagingStyleInfo) CollectionsKt___CollectionsKt.lastOrNull(arrayList)) != null && (str = messagingStyleInfo.mSender) != null) {
            SubscreenNotificationInfo subscreenNotificationInfo3 = this.popupInfo;
            String title = subscreenNotificationInfo3 != null ? subscreenNotificationInfo3.getTitle() : null;
            if (str.length() > 0 && !str.equals(title)) {
                textView.setText(str);
                textView.setVisibility(0);
            }
        }
        if (z || useTopPresentation()) {
            setClock(this.popupInfo, this.mPopUpViewLayout);
            int i = this.mNotiPopupType;
            if (i == 2) {
                SemBlurInfo semBlurInfoBuild = new SemBlurInfo.Builder(0).setColorCurvePreset((this.mContext.getResources().getConfiguration().uiMode & 48) == 32 ? 124 : 114).setBackgroundCornerRadius(this.mContext.getResources().getDimensionPixelOffset(R.dimen.subscreen_noti_top_popup_layout_radius_b5)).setBackgroundColor(this.mContext.getColor(R.color.blend_blur_color)).build();
                View view3 = this.mPopUpViewLayout;
                if (view3 != null && (linearLayout = (LinearLayout) view3.findViewById(R.id.subscreen_notification_top_popup_layout)) != null) {
                    linearLayout.semSetBlurInfo(semBlurInfoBuild);
                }
            } else if (i == 1 && (view = this.mPopUpViewLayout) != null && (frameLayout = (FrameLayout) view.findViewById(R.id.subscreen_notification_top_popup_frame)) != null) {
                frameLayout.setBackground(this.mContext.getDrawable(R.drawable.subscreen_notification_hun_top_background_b5));
            }
        }
        if (z || useTopPresentation()) {
            setListItemTextLayout(context, this.mPopUpViewLayout);
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

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public void setTipViewPadding(View view) throws Resources.NotFoundException {
        Context context = this.mDisplayContext;
        if (context == null) {
            context = null;
        }
        int dimensionPixelSize = context.getResources().getDimensionPixelSize(R.dimen.subscreen_noti_cutout_area_height_b5);
        boolean z = this.mIsFlexMode;
        int i = z ? dimensionPixelSize : 0;
        if (z) {
            dimensionPixelSize = 0;
        }
        view.setPadding(0, i, 0, dimensionPixelSize);
    }

    /* JADX WARN: Removed duplicated region for block: B:36:0x0087  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x008b  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x0092  */
    /* JADX WARN: Type inference failed for: r1v6, types: [T, java.lang.String] */
    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void showAIReply() {
        SubscreenNotificationDetailAdapter.ItemViewHolder itemViewHolder;
        SubscreenNotificationInfo subscreenNotificationInfo;
        final SubscreenDeviceModelB5 subscreenDeviceModelB5;
        String string;
        LinearLayout linearLayout;
        boolean z = true;
        if ((this.isPossibleAiReply && (itemViewHolder = this.detailViewHolder) != null && (subscreenNotificationInfo = itemViewHolder.mInfo) != null && isConversation(subscreenNotificationInfo)) ? isReplyLayoutShowing() : false) {
            final SubscreenNotificationDetailAdapter.ItemViewHolder itemViewHolder2 = this.detailViewHolder;
            if (itemViewHolder2 != null) {
                ListPopupWindow$$ExternalSyntheticOutline0.m(this.smartReplyStatus, "callAIReply() - start smartReplyStatus : ", "S.S.N.");
                if (this.smartReplyStatus != 0) {
                    Log.d("S.S.N.", "callAIReply() return - CallAIReply is already running");
                } else {
                    this.smartReplyStatus = 1;
                    final String historyInfo = getHistoryInfo(itemViewHolder2);
                    fValueOf = null;
                    Float fValueOf = null;
                    if (historyInfo == null || (string = StringsKt__StringsKt.trim(historyInfo).toString()) == null || string.length() == 0) {
                        subscreenDeviceModelB5 = this;
                        subscreenDeviceModelB5.handleProgressLayout(false);
                        LinearLayout linearLayout2 = subscreenDeviceModelB5.progressLayout;
                        if (linearLayout2 != null) {
                            linearLayout2.setVisibility(8);
                        }
                        String string2 = historyInfo != null ? StringsKt__StringsKt.trim(historyInfo).toString() : null;
                        if (string2 != null && string2.length() != 0) {
                            z = false;
                        }
                        if (z && subscreenDeviceModelB5.mSmartReplyClickedByUser) {
                            subscreenDeviceModelB5.mSmartReplyClickedByUser = false;
                            subscreenDeviceModelB5.enableSmartReplyTriggerBtn("", false);
                            subscreenDeviceModelB5.updateVisibilityForSmartReplyLayout(8);
                            TextView textView = subscreenDeviceModelB5.smartReplyErrorMessageView;
                            if (textView != null) {
                                textView.setText(R.string.subscreen_notification_smart_reply_error_other);
                            }
                            showErrorMessageWithAnim(subscreenDeviceModelB5.smartReplyErrorMessageView);
                        }
                        subscreenDeviceModelB5.smartReplyStatus = 0;
                        MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("callAIReply() return - history is null or empty", historyInfo, "S.S.N.");
                    } else {
                        SmartReplyData smartReplyData = (SmartReplyData) this.mSmartReplyHashMap.get(itemViewHolder2.mInfo.mKey);
                        if (smartReplyData != null) {
                            String str = smartReplyData.prevPrompt;
                            if (str != null ? str.equals(historyInfo) : false) {
                                if (this.mSmartReplyClickedByUser) {
                                    this.mSmartReplyClickedByUser = false;
                                    Log.d("S.S.N.", "callAIReply() continue - isAlreadyAiReply but click button by user");
                                    if (this.smartReplyStatus == 1) {
                                    }
                                } else {
                                    inflateSmartReplyAI(smartReplyData.replyText);
                                    Log.d("S.S.N.", "callAIReply() return - isAlreadyAiReply");
                                    this.smartReplyStatus = 0;
                                }
                            }
                        } else if (this.smartReplyStatus == 1) {
                            Log.d("S.S.N.", "callAIReply() return - it's already progressing... ");
                        } else {
                            this.smartReplyStatus = 2;
                            SubscreenNotificationDetailAdapter.ItemViewHolder itemViewHolder3 = this.detailViewHolder;
                            if (itemViewHolder3 != null && (linearLayout = itemViewHolder3.mReplylayout) != null) {
                                fValueOf = Float.valueOf(linearLayout.getY());
                            }
                            fValueOf.getClass();
                            this.mReplyLayoutCurrentPostionY = fValueOf.floatValue();
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
                                subscreenDeviceModelB5 = this;
                                neuralTranslator.refresh().addOnCompleteListener(new SubscreenDeviceModelB5$sam$com_samsung_android_sdk_scs_base_tasks_OnCompleteListener$0(new Function1() { // from class: com.android.systemui.statusbar.notification.SubscreenDeviceModelB5$$ExternalSyntheticLambda0
                                    /* JADX WARN: Multi-variable type inference failed */
                                    @Override // kotlin.jvm.functions.Function1
                                    /* renamed from: invoke */
                                    public final Object mo781invoke(Object obj) {
                                        int i = SubscreenDeviceModelB5.$r8$clinit;
                                        Task taskIdentifyLanguage = neuralTranslator.identifyLanguage((String) ref$ObjectRef.element);
                                        final SubscreenDeviceModelB5 subscreenDeviceModelB52 = subscreenDeviceModelB5;
                                        final SubscreenNotificationDetailAdapter.ItemViewHolder itemViewHolder4 = itemViewHolder2;
                                        final String str2 = historyInfo;
                                        taskIdentifyLanguage.addOnCompleteListener(new OnCompleteListener() { // from class: com.android.systemui.statusbar.notification.SubscreenDeviceModelB5$callAIReply$1$1
                                            @Override // com.samsung.android.sdk.scs.base.tasks.OnCompleteListener
                                            public final void onComplete(final Task task) {
                                                boolean zIsSuccessful = task.isSuccessful();
                                                final SubscreenDeviceModelB5 subscreenDeviceModelB53 = subscreenDeviceModelB52;
                                                if (!zIsSuccessful) {
                                                    subscreenDeviceModelB53.smartReplyStatus = 0;
                                                    return;
                                                }
                                                boolean zAccess$isSupportableLanguage = SubscreenDeviceModelB5.access$isSupportableLanguage(subscreenDeviceModelB53, String.valueOf(task.getResult()));
                                                boolean zIsSuccessful2 = task.isSuccessful();
                                                boolean zIsComplete = task.isComplete();
                                                Object result = task.getResult();
                                                StringBuilder sbM = EmergencyButtonController$$ExternalSyntheticOutline0.m("callAIReply() - successful : ", ", isComplete : ", ", result : ", zIsSuccessful2, zIsComplete);
                                                sbM.append(result);
                                                sbM.append(", isSupport : ");
                                                sbM.append(zAccess$isSupportableLanguage);
                                                Log.d("S.S.N.", sbM.toString());
                                                if (zAccess$isSupportableLanguage) {
                                                    subscreenDeviceModelB53.handleProgressLayout(true);
                                                    final String str3 = str2;
                                                    final SubscreenNotificationDetailAdapter.ItemViewHolder itemViewHolder5 = itemViewHolder4;
                                                    new Thread(new Runnable() { // from class: com.android.systemui.statusbar.notification.SubscreenDeviceModelB5$callAIReply$1$1.1
                                                        @Override // java.lang.Runnable
                                                        public final void run() {
                                                            subscreenDeviceModelB53.mPromptSB.append(str3);
                                                            Log.d("S.S.N.", "call textPrompting");
                                                            if (subscreenDeviceModelB53.mPromptSBForLog.length() > 0) {
                                                                SubscreenDeviceModelB5 subscreenDeviceModelB54 = subscreenDeviceModelB53;
                                                                if (subscreenDeviceModelB54.isDebug) {
                                                                    Log.d("S.S.N.", "textPrompting trimmed :\n" + ((Object) subscreenDeviceModelB54.mPromptSBForLog));
                                                                }
                                                                subscreenDeviceModelB53.mPromptSBForLog.setLength(0);
                                                            }
                                                            subscreenDeviceModelB53.mSrPromptProcessor.setNotificationKey(itemViewHolder5.mInfo.mKey);
                                                            SubscreenDeviceModelB5 subscreenDeviceModelB55 = subscreenDeviceModelB53;
                                                            subscreenDeviceModelB55.mSrPromptProcessor.textPrompting(subscreenDeviceModelB55.mPromptSB.toString(), String.valueOf(task.getResult()), subscreenDeviceModelB53.mSrResponseCallback);
                                                        }
                                                    }).start();
                                                    return;
                                                }
                                                Log.d("S.S.N.", "callAIReply - not Support");
                                                subscreenDeviceModelB53.handleProgressLayout(false);
                                                subscreenDeviceModelB53.smartReplyStatus = 0;
                                                subscreenDeviceModelB53.enableSmartReplyTriggerBtn("unsupportedLanguage", false);
                                                if (subscreenDeviceModelB53.mSmartReplyClickedByUser) {
                                                    subscreenDeviceModelB53.mSmartReplyClickedByUser = false;
                                                    subscreenDeviceModelB53.updateVisibilityForSmartReplyLayout(8);
                                                    subscreenDeviceModelB53.enableSmartReplyTriggerBtn("", false);
                                                    TextView textView3 = subscreenDeviceModelB53.smartReplyErrorMessageView;
                                                    if (textView3 != null) {
                                                        textView3.setText(R.string.subscreen_notification_smart_reply_error_unsupported_language);
                                                    }
                                                    SubscreenDeviceModelB5.showErrorMessageWithAnim(subscreenDeviceModelB53.smartReplyErrorMessageView);
                                                }
                                            }
                                        });
                                        return Unit.INSTANCE;
                                    }
                                })).getClass();
                            } else {
                                subscreenDeviceModelB5 = this;
                                subscreenDeviceModelB5.smartReplyStatus = 0;
                            }
                            ListPopupWindow$$ExternalSyntheticOutline0.m(subscreenDeviceModelB5.smartReplyStatus, "callAIReply() - end smartReplyStatus : ", "S.S.N.");
                        }
                    }
                }
                subscreenDeviceModelB5 = this;
            } else {
                subscreenDeviceModelB5 = this;
            }
            subscreenDeviceModelB5.isPossibleAiReply = false;
        }
    }

    public final void showBouncer(Context context, final NotificationEntry notificationEntry) {
        SubRoom.StateChangeListener stateChangeListener;
        boolean zIsKeyguardStats = isKeyguardStats();
        KeyguardStateController keyguardStateController = this.mKeyguardStateController;
        Log.d("S.S.N.", "showBouncer B5 -isMethodSecure : " + (keyguardStateController != null ? Boolean.valueOf(((KeyguardStateControllerImpl) keyguardStateController).mSecure) : null) + ", isUnlocked : " + zIsKeyguardStats);
        KeyguardStateController keyguardStateController2 = this.mKeyguardStateController;
        if (keyguardStateController2 == null || !((KeyguardStateControllerImpl) keyguardStateController2).mSecure) {
            ((KeyguardManager) context.getSystemService("keyguard")).semDismissKeyguard();
            return;
        }
        if (zIsKeyguardStats) {
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
            Runnable runnable = new Runnable() { // from class: com.android.systemui.statusbar.notification.SubscreenDeviceModelB5.showBouncer.1
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
            popupWindow2.setOnDismissListener(new PopupWindow.OnDismissListener() { // from class: com.android.systemui.statusbar.notification.SubscreenDeviceModelB5.showReplyButtonViewPopupWindow.1
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
        int iM = StrongAuthPopup$$ExternalSyntheticOutline0.m(context, R.dimen.subscreen_noti_detail_reply_button_margin_b5, height);
        if (this.mIsFlexMode) {
            int dispalyHeight = getDispalyHeight();
            SubscreenSubRoomNotification subscreenSubRoomNotification = this.mSubRoomNotification;
            Integer numValueOf = (subscreenSubRoomNotification == null || (linearLayout = subscreenSubRoomNotification.mSubscreenMainLayout) == null) ? null : Integer.valueOf(linearLayout.getHeight());
            numValueOf.getClass();
            iM += (dispalyHeight - numValueOf.intValue()) / 2;
        }
        final Ref$IntRef ref$IntRef = new Ref$IntRef();
        Context context2 = this.mDisplayContext;
        ref$IntRef.element = (context2 != null ? context2 : null).getResources().getDimensionPixelSize(R.dimen.subscreen_noti_detail_icon_button_bg_size_b5);
        PopupWindow popupWindow3 = this.sendButtonPopupWindow;
        if (popupWindow3 != null) {
            popupWindow3.setTouchInterceptor(new View.OnTouchListener() { // from class: com.android.systemui.statusbar.notification.SubscreenDeviceModelB5.showReplyButtonViewPopupWindow.2
                public Boolean downHit = Boolean.FALSE;

                /* JADX WARN: Removed duplicated region for block: B:40:0x0088  */
                @Override // android.view.View.OnTouchListener
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final boolean onTouch(View view3, MotionEvent motionEvent) {
                    boolean z = false;
                    if (motionEvent == null) {
                        return false;
                    }
                    if (motionEvent.getAction() == 0) {
                        float x = motionEvent.getX();
                        float y = motionEvent.getY();
                        if (x > 0.0f) {
                            int i = ref$IntRef.element;
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
                            int i2 = ref$IntRef.element;
                            if (x2 > i2 || y2 < 0.0f || y2 > i2) {
                                PopupWindow popupWindow5 = this.sendButtonPopupWindow;
                                if (popupWindow5 != null) {
                                    popupWindow5.dismiss();
                                }
                            } else if (Intrinsics.areEqual(this.downHit, Boolean.TRUE)) {
                                ImageView imageView = (ImageView) view.findViewById(R.id.send);
                                if (imageView != null) {
                                    imageView.performClick();
                                }
                                this.downHit = bool2;
                            }
                        }
                    }
                    return true;
                }
            });
        }
        PopupWindow popupWindow4 = this.sendButtonPopupWindow;
        if (popupWindow4 != null) {
            popupWindow4.showAtLocation(view, 1, 0, iM);
        }
        this.mSmartReplyClickedByUser = false;
        return this.sendButtonPopupWindow;
    }

    public final void showSmartReplyResultComplete(StringBuilder sb) {
        Object failure;
        SubscreenNotificationInfo subscreenNotificationInfo;
        this.isPossibleAiReply = false;
        try {
            int i = Result.$r8$clinit;
            String string = sb.toString();
            if (string.length() > 0) {
                SubscreenNotificationDetailAdapter.ItemViewHolder itemViewHolder = this.detailViewHolder;
                String str = (itemViewHolder == null || (subscreenNotificationInfo = itemViewHolder.mInfo) == null) ? null : subscreenNotificationInfo.mKey;
                SmartReplyData smartReplyData = new SmartReplyData();
                StringBuilder sb2 = this.mPromptSB;
                smartReplyData.prevPrompt = sb2.toString();
                smartReplyData.replyText = string;
                if (str != null) {
                    this.mSmartReplyHashMap.put(str, smartReplyData);
                }
                inflateSmartReplyAI(string);
                sb2.setLength(0);
            }
            failure = Unit.INSTANCE;
        } catch (Throwable th) {
            int i2 = Result.$r8$clinit;
            failure = new Result.Failure(th);
        }
        Result.m3441exceptionOrNullimpl(failure);
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
        String string = str != null ? StringsKt__StringsKt.trim(str).toString() : null;
        String string2 = Intrinsics.areEqual(string, "Blocked by input safety filter") ? this.mContext.getString(R.string.subscreen_notification_smart_reply_error_safety_filter) : Intrinsics.areEqual(string, "Input is too long") ? this.mContext.getString(R.string.subscreen_notification_smart_reply_error_input_is_too_long) : this.mContext.getString(R.string.subscreen_notification_smart_reply_error_other);
        string2.getClass();
        TextView textView2 = this.smartReplyErrorMessageView;
        if (textView2 != null) {
            textView2.setText(string2);
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
        View viewInflate = LayoutInflater.from(subHomeActivity).inflate(R.layout.subscreen_notification_unlock_icon_view_b5, (ViewGroup) null);
        AnimationDrawable animationDrawable = (AnimationDrawable) ((viewInflate == null || (imageView = (ImageView) viewInflate.findViewById(R.id.unlock_icon_view)) == null) ? null : imageView.getDrawable());
        final PopupWindow popupWindow = new PopupWindow(viewInflate, -2, -2);
        Context context = this.mDisplayContext;
        popupWindow.showAtLocation(viewInflate, 49, 0, (context != null ? context : null).getResources().getDimensionPixelSize(R.dimen.subscreen_noti_unlock_icon_view_top_margin_b5));
        animationDrawable.start();
        viewInflate.animate().alpha(0.0f).setStartDelay(500L).setDuration(500L).withEndAction(new Runnable() { // from class: com.android.systemui.statusbar.notification.SubscreenDeviceModelB5$showUnlockIconAnim$1$1
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
        LinearLayout linearLayout;
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
            if (itemViewHolder != null && (linearLayout = itemViewHolder.mReplylayout) != null) {
                linearLayout.setTranslationY(this.mReplyLayoutCurrentPostionY - 304);
            }
            SubscreenNotificationDetailAdapter.ItemViewHolder itemViewHolder2 = this.detailViewHolder;
            ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(itemViewHolder2 != null ? itemViewHolder2.mReplylayout : null, (Property<LinearLayout, Float>) View.TRANSLATION_Y, 0.0f);
            objectAnimatorOfFloat.setInterpolator(Interpolators.FAST_OUT_LINEAR_IN);
            objectAnimatorOfFloat.setDuration(200L);
            objectAnimatorOfFloat.start();
            f2 = 1.0f;
            f3 = 0.85f;
            f = 1.0f;
        }
        view.setScaleX(f3);
        view.setScaleY(f3);
        view.setAlpha(f4);
        resetProgressScaleAnimation();
        view.animate().alpha(f).setDuration(200L).setListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.statusbar.notification.SubscreenDeviceModelB5.startProgressSpringAnimation.1
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
        springAnimation2.addEndListener(new DynamicAnimation.OnAnimationEndListener() { // from class: com.android.systemui.statusbar.notification.SubscreenDeviceModelB5.startProgressSpringAnimation.2
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
        if (view2 != null) {
            view2.setAlpha(1.0f);
        }
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final void updateMoreShadowIconColor(View view, NotificationEntry notificationEntry) {
        ExpandableNotificationRow expandableNotificationRow;
        Boolean boolValueOf = (notificationEntry == null || (expandableNotificationRow = notificationEntry.row) == null) ? null : Boolean.valueOf(expandableNotificationRow.isInsignificantSummary());
        boolValueOf.getClass();
        if (boolValueOf.booleanValue()) {
            ImageView imageView = view != null ? (ImageView) view.findViewById(R.id.more_icon_shadow) : null;
            if (imageView != null) {
                imageView.setVisibility(0);
                imageView.setBackground(null);
                imageView.setColorFilter((ColorFilter) null);
                imageView.setImageDrawable(null);
                if (getMSettingsHelper().isShowNotificationAppIconEnabled()) {
                    imageView.setBackground(this.mContext.getDrawable(R.drawable.squircle));
                } else {
                    imageView.setBackground(this.mContext.getDrawable(R.drawable.notification_icon_circle));
                }
                int color = this.mContext.getColor(R.color.notification_insignificant_icon_shadow_color);
                imageView.getBackground().setColorFilter(Color.argb((this.mContext.getResources().getConfiguration().uiMode & 48) == 32 ? 70 : 25, Color.red(color), Color.green(color), Color.blue(color)), PorterDuff.Mode.SRC_IN);
            }
        }
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final void updateSamsungAccount() {
        Account[] accountsByTypeAsUser;
        Account account = null;
        try {
            accountsByTypeAsUser = AccountManager.get(this.mContext).getAccountsByTypeAsUser("com.osp.app.signin", UserHandle.of(this.currentUserId));
        } catch (SecurityException e) {
            String message = e.getMessage();
            if (message != null) {
                Log.e("S.S.N.", message);
            }
            accountsByTypeAsUser = null;
        }
        if (accountsByTypeAsUser != null && accountsByTypeAsUser.length > 0) {
            account = accountsByTypeAsUser[0];
        }
        Account account2 = this.currentAccount;
        if (!((account2 == null || account == null) ? (account2 == null && account == null) ? false : true : !account2.name.equals(account.name))) {
            Log.d("S.S.N.", "updateSamsungAccount() : No Change");
            return;
        }
        this.currentAccount = account;
        this.isSALoggedIn = account != null;
        ThreadUtils.postOnBackgroundThread(new Runnable() { // from class: com.android.systemui.statusbar.notification.SubscreenDeviceModelB5$getChildAccount$1
            /* JADX WARN: Removed duplicated region for block: B:20:0x0069  */
            @Override // java.lang.Runnable
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public final void run() {
                SubscreenDeviceModelB5 subscreenDeviceModelB5;
                Bundle bundleCall;
                SubscreenDeviceModelB5 subscreenDeviceModelB52 = this.this$0;
                int i = SubscreenDeviceModelB5.$r8$clinit;
                try {
                    bundleCall = subscreenDeviceModelB52.mContext.getContentResolver().call(Uri.parse("content://com.samsung.android.samsungaccount.accountmanagerprovider"), "isChildAccount", "i5to7wq0er", (Bundle) null);
                } catch (Exception e2) {
                    MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("Exception Error isChildAccount : ", e2.getMessage(), "S.S.N.");
                }
                if (bundleCall != null) {
                    int i2 = bundleCall.getInt("result_code", 1);
                    String string = bundleCall.getString("result_message", "");
                    if (i2 != 0) {
                        MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("isChildAccount Fail : resultMessage = ", string, "S.S.N.");
                    } else {
                        if ("true".equals(string)) {
                            Log.d("S.S.N.", "This account is a child account.");
                            boolean z = SalesCode.isKor ? false : true;
                            subscreenDeviceModelB52.isChildAccount = z;
                            subscreenDeviceModelB5 = this.this$0;
                            if (subscreenDeviceModelB5.isChildAccount) {
                                int i3 = -1;
                                try {
                                    Bundle bundleCall2 = subscreenDeviceModelB5.mContext.getContentResolver().call(Uri.parse("content://com.samsung.android.samsungaccount.accountmanagerprovider"), "getFamilyServiceInfo", "i5to7wq0er", (Bundle) null);
                                    if (bundleCall2 != null) {
                                        if (bundleCall2.getInt("result_code", 1) == 0) {
                                            Bundle bundle = bundleCall2.getBundle("result_bundle");
                                            if (bundle != null) {
                                                i3 = bundle.getInt("childGraduateAge");
                                            }
                                        } else {
                                            MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("getChildGraduateAge() Fail : resultMessage = ", bundleCall2.getString("result_message", ""), "S.S.N.");
                                        }
                                    }
                                } catch (Exception e3) {
                                    MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("Exception Error getFamilyServiceInfo : ", e3.getMessage(), "S.S.N.");
                                }
                                subscreenDeviceModelB5.childGraduateAge = i3;
                            }
                            EmergencyButtonController$$ExternalSyntheticOutline0.m("getChildAccount() : isChildAccount ", "S.S.N.", this.this$0.isChildAccount);
                        }
                        Log.d("S.S.N.", "This account is not a child account.");
                    }
                } else {
                    Log.d("S.S.N.", "Result bundle is null");
                }
                subscreenDeviceModelB52.isChildAccount = z;
                subscreenDeviceModelB5 = this.this$0;
                if (subscreenDeviceModelB5.isChildAccount) {
                }
                EmergencyButtonController$$ExternalSyntheticOutline0.m("getChildAccount() : isChildAccount ", "S.S.N.", this.this$0.isChildAccount);
            }
        });
        EmergencyButtonController$$ExternalSyntheticOutline0.m("updateSamsungAccount() : isSALoggedIn ", "S.S.N.", this.isSALoggedIn);
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final void updateSmallIconBg(ImageView imageView, boolean z, boolean z2, boolean z3) {
        int iSmallIconPadding = smallIconPadding(z, z2, z3);
        if (imageView != null) {
            Context context = this.mDisplayContext;
            if (context == null) {
                context = null;
            }
            imageView.setBackground(context.getResources().getDrawable(R.drawable.notification_icon_circle, null));
            imageView.setPadding(iSmallIconPadding, iSmallIconPadding, iSmallIconPadding, iSmallIconPadding);
        }
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenDeviceModelParent
    public final void updateSmallIconSquircleBg(ImageView imageView, boolean z, boolean z2) {
        int iSmallIconPadding = smallIconPadding(z, z2, false);
        if (imageView != null) {
            Context context = this.mDisplayContext;
            if (context == null) {
                context = null;
            }
            imageView.setBackground(context.getDrawable(R.drawable.squircle));
            imageView.setPadding(iSmallIconPadding, iSmallIconPadding, iSmallIconPadding, iSmallIconPadding);
        }
    }

    public final void updateSmartReplyVariables() {
        this.isAiInfoConfirmed = getMSettingsHelper().isAiInfoConfirmed();
        this.isSuggestResponsesEnabled = getMSettingsHelper().isSuggestResponsesEnabled();
        boolean z = false;
        this.isRDUMode = Settings.Secure.getInt(this.mContext.getContentResolver(), "shopdemo", 0) == 1;
        updateSamsungAccount();
        ThreadUtils.postOnBackgroundThread(new Runnable() { // from class: com.android.systemui.statusbar.notification.SubscreenDeviceModelB5$needToShowWritingAssistFTU$1
            @Override // java.lang.Runnable
            public final void run() {
                try {
                    Bundle bundleCall = this.this$0.mContext.getContentResolver().call(Uri.parse("content://com.samsung.android.honeyboard.settings.aiwriter.provider.WritingAssistProvider/get_features"), "writing_toolkit_settings", (String) null, (Bundle) null);
                    Integer numValueOf = bundleCall != null ? Integer.valueOf(bundleCall.getInt("key_writing_toolkit_ftu")) : null;
                    Boolean boolValueOf = bundleCall != null ? Boolean.valueOf(bundleCall.getBoolean("key_writing_toolkit_on_off")) : null;
                    this.this$0.needToShowFTU = (numValueOf != null && numValueOf.intValue() == 0) || Intrinsics.areEqual(boolValueOf, Boolean.FALSE);
                    Log.d("S.S.N.", "needToShowWritingAssistFTU(): isFirstEntering = " + numValueOf + ", writingAssistSetting = " + boolValueOf);
                } catch (Exception e) {
                    MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("Exception Error WritingAssistProvider : ", e.getMessage(), "S.S.N.");
                }
            }
        });
        boolean z2 = this.isRDUMode;
        boolean z3 = this.isSALoggedIn;
        boolean z4 = this.isChildAccount;
        boolean z5 = this.isAiInfoConfirmed;
        boolean z6 = this.isSuggestResponsesEnabled;
        boolean z7 = NotiRune.NOTI_SUBSCREEN_SUPPORT_SMART_REPLY_AI_FOR_CHINA;
        boolean z8 = z7 && Settings.System.getInt(this.mContext.getContentResolver(), "prevent_online_processing", 0) == 1;
        if (z7 && isAiCoreFeaturesEnabled()) {
            z = true;
        }
        StringBuilder sbM = EmergencyButtonController$$ExternalSyntheticOutline0.m("isRDUMode: ", " isSALoggedIn: ", " isChildAccount: ", z2, z3);
        KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(sbM, z4, " isAiInfoConfirmed: ", z5, " isSuggestionResponsesEnabled: ");
        KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(sbM, z6, " isPreventOnlineProcessing: ", z8, "isAiCoreFeaturesEnabled: ");
        ActionBarContextView$$ExternalSyntheticOutline0.m(sbM, z, "S.S.N.");
    }

    public final void updateVisibilityForSmartReplyLayout(int i) {
        LinearLayout linearLayout;
        SubscreenNotificationDetailAdapter.ItemViewHolder itemViewHolder = this.detailViewHolder;
        if (itemViewHolder != null && (linearLayout = itemViewHolder.mSmartReplyLayout) != null) {
            linearLayout.setVisibility(i);
        }
        ImageView imageView = this.aiDisclaimerBtn;
        if (imageView != null) {
            imageView.setVisibility(i);
        }
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
