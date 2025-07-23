package com.android.systemui.assist;

import android.app.ActivityManager;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.app.KeyguardManager;
import android.app.SearchManager;
import android.app.StatusBarManager;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.metrics.LogMaker;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Process;
import android.os.RemoteException;
import android.os.SystemClock;
import android.os.UserHandle;
import android.provider.Settings;
import android.service.voice.VisualQueryAttentionResult;
import android.service.voice.VoiceInteractionServiceInfo;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.IWindowManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.WindowManagerGlobal;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.internal.app.AssistUtils;
import com.android.internal.app.IVisualQueryDetectionAttentionListener;
import com.android.internal.app.IVisualQueryRecognitionStatusListener;
import com.android.internal.app.IVoiceInteractionSessionListener;
import com.android.internal.app.IVoiceInteractionSessionShowCallback;
import com.android.internal.logging.MetricsLogger;
import com.android.keyguard.KeyguardKnoxDualDarInnerPasswordViewController$$ExternalSyntheticOutline0;
import com.android.systemui.BasicRune;
import com.android.systemui.Dependency;
import com.android.systemui.LsRune;
import com.android.systemui.Prefs;
import com.android.systemui.R;
import com.android.systemui.assist.AssistDisclosure;
import com.android.systemui.assist.AssistManager;
import com.android.systemui.assist.domain.interactor.AssistInteractor;
import com.android.systemui.assist.ui.DefaultUiController;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.dreams.conditions.AssistantAttentionCondition;
import com.android.systemui.dreams.conditions.AssistantAttentionCondition$visualQueryAttentionListener$1;
import com.android.systemui.model.SysUiState;
import com.android.systemui.model.SysUiStateImpl;
import com.android.systemui.navigationbar.NavigationModeController;
import com.android.systemui.popup.util.PopupUIUtil;
import com.android.systemui.recents.LauncherProxyService;
import com.android.systemui.settings.DisplayTracker;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.shared.recents.ILauncherProxy;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import com.android.systemui.statusbar.policy.DeviceProvisionedControllerImpl;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.subscreen.SubHomeActivity;
import com.android.systemui.subscreen.SubScreenManager;
import com.android.systemui.user.domain.interactor.SelectedUserInteractor;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.SystemUIAnalytics;
import com.android.systemui.util.settings.SecureSettings;
import com.samsung.android.app.SemRoleManager;
import com.samsung.android.sdk.scs.ai.visual.c2pa.C2paManifestList;
import com.samsung.android.view.SemWindowManager;
import dagger.Lazy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.IntPredicate;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class AssistManager {
    public static final Map invocationTypeMap = new HashMap<Integer, String>() { // from class: com.android.systemui.assist.AssistManager.2
        {
            put(0, C2paManifestList.UNKNOWN_VALUE);
            put(1, "Diagonal swipe");
            put(2, "Physical gesture");
            put(3, "Voice");
            put(4, "Quick search bar");
            put(5, "Home button Long press");
            put(6, "Side button");
            put(7, "Assist button");
            put(8, "Nav handle Long press");
        }
    };
    public final ActivityManager mActivityManager;
    public final AssistDisclosure mAssistDisclosure;
    public final AssistLogger mAssistLogger;
    public int[] mAssistOverrideInvocationTypes;
    public boolean mAssistPopupPositiveClicked;
    public final AssistUtils mAssistUtils;
    public AlertDialog mAssistanceAppSettingAlertDialog;
    public final BroadcastDispatcher mBroadcastDispatcher;
    public final CommandQueue mCommandQueue;
    public final Context mContext;
    public final DeviceProvisionedController mDeviceProvisionedController;
    public int mDisabledFlags;
    public final DisplayTracker mDisplayTracker;
    public final IntentFilter mFilter;
    public final AssistInteractor mInteractor;
    public final KeyguardManager mKeyguardManager;
    public final LauncherProxyService mLauncherProxyService;
    public LayoutInflater mLayoutInflater;
    public final PhoneStateMonitor mPhoneStateMonitor;
    public final SecureSettings mSecureSettings;
    public final SelectedUserInteractor mSelectedUserInteractor;
    public final SubScreenManager mSubScreenManager;
    public final Lazy mSysUiState;
    public final DefaultUiController mUiController;
    public final UserTracker mUserTracker;
    public final IWindowManager mWindowManager;
    public int navBarMode;
    public final List mVisualQueryAttentionListeners = new ArrayList();
    public final AnonymousClass1 mVisualQueryDetectionAttentionListener = new IVisualQueryDetectionAttentionListener.Stub() { // from class: com.android.systemui.assist.AssistManager.1
        public final void onAttentionGained(VisualQueryAttentionResult visualQueryAttentionResult) {
            AssistManager.m1012$$Nest$mhandleVisualAttentionChanged(AssistManager.this, true);
        }

        public final void onAttentionLost(int i) {
            AssistManager.m1012$$Nest$mhandleVisualAttentionChanged(AssistManager.this, false);
        }
    };
    public boolean mIsAssistAppAvailable = true;
    public final AnonymousClass8 mBroadcastReceiver = new BroadcastReceiver() { // from class: com.android.systemui.assist.AssistManager.8
        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            AlertDialog alertDialog;
            if (intent != null && PopupUIUtil.ACTION_CLOSE_SYSTEM_DIALOGS.equals(intent.getAction()) && "homekey".equals(intent.getStringExtra("reason")) && (alertDialog = AssistManager.this.mAssistanceAppSettingAlertDialog) != null && alertDialog.isShowing()) {
                AssistManager.this.mAssistanceAppSettingAlertDialog.dismiss();
                AssistManager.this.mAssistanceAppSettingAlertDialog = null;
            }
        }
    };

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class AssistanceAppItemList {
        public final Drawable mAssistanceAppIcon;
        public final String mAssistanceAppName;
        public final int mAssistanceAppType;
        public final ComponentName mAssistanceComponent;
        public final VoiceInteractionServiceInfo mAssistanceVoiceInteractionService;

        public AssistanceAppItemList(ComponentName componentName, VoiceInteractionServiceInfo voiceInteractionServiceInfo, Drawable drawable, String str, int i) {
            this.mAssistanceComponent = componentName;
            this.mAssistanceVoiceInteractionService = voiceInteractionServiceInfo;
            this.mAssistanceAppIcon = drawable;
            this.mAssistanceAppName = str;
            this.mAssistanceAppType = i;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class AssistanceAppItemListAdapter extends ArrayAdapter {
        public final int mResourceId;
        public int mSelectedItem;

        public AssistanceAppItemListAdapter(Context context, int i, ArrayList<AssistanceAppItemList> arrayList) {
            super(context, i, arrayList);
            this.mResourceId = i;
        }

        @Override // android.widget.ArrayAdapter, android.widget.Adapter
        public final View getView(int i, View view, ViewGroup viewGroup) {
            LinearLayout linearLayout = (LinearLayout) view;
            if (linearLayout == null) {
                linearLayout = (LinearLayout) AssistManager.this.mLayoutInflater.inflate(this.mResourceId, viewGroup, false);
            }
            AssistanceAppItemList assistanceAppItemList = (AssistanceAppItemList) getItem(i);
            if (assistanceAppItemList != null) {
                CheckedTextView checkedTextView = (CheckedTextView) linearLayout.findViewById(R.id.check_box);
                ImageView imageView = (ImageView) linearLayout.findViewById(R.id.app_icon);
                TextView textView = (TextView) linearLayout.findViewById(R.id.app_name);
                if (textView != null) {
                    textView.setText(assistanceAppItemList.mAssistanceAppName);
                }
                if (imageView != null) {
                    imageView.setImageDrawable(assistanceAppItemList.mAssistanceAppIcon);
                }
                if (checkedTextView != null) {
                    if (this.mSelectedItem == i) {
                        checkedTextView.setChecked(true);
                        return linearLayout;
                    }
                    checkedTextView.setChecked(false);
                }
            }
            return linearLayout;
        }
    }

    /* renamed from: -$$Nest$mhandleVisualAttentionChanged, reason: not valid java name */
    public static void m1012$$Nest$mhandleVisualAttentionChanged(AssistManager assistManager, boolean z) {
        Consumer consumer;
        StatusBarManager statusBarManager = (StatusBarManager) assistManager.mContext.getSystemService(StatusBarManager.class);
        if (statusBarManager != null) {
            statusBarManager.setIconVisibility("assist_attention", z);
        }
        List list = assistManager.mVisualQueryAttentionListeners;
        if (z) {
            final int i = 0;
            consumer = new Consumer() { // from class: com.android.systemui.assist.AssistManager$$ExternalSyntheticLambda6
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    int i2 = i;
                    AssistantAttentionCondition$visualQueryAttentionListener$1 assistantAttentionCondition$visualQueryAttentionListener$1 = (AssistantAttentionCondition$visualQueryAttentionListener$1) obj;
                    assistantAttentionCondition$visualQueryAttentionListener$1.getClass();
                    switch (i2) {
                        case 0:
                            int i3 = AssistantAttentionCondition.$r8$clinit;
                            assistantAttentionCondition$visualQueryAttentionListener$1.this$0.updateCondition(true);
                            break;
                        default:
                            int i4 = AssistantAttentionCondition.$r8$clinit;
                            assistantAttentionCondition$visualQueryAttentionListener$1.this$0.updateCondition(false);
                            break;
                    }
                }
            };
        } else {
            final int i2 = 1;
            consumer = new Consumer() { // from class: com.android.systemui.assist.AssistManager$$ExternalSyntheticLambda6
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    int i22 = i2;
                    AssistantAttentionCondition$visualQueryAttentionListener$1 assistantAttentionCondition$visualQueryAttentionListener$1 = (AssistantAttentionCondition$visualQueryAttentionListener$1) obj;
                    assistantAttentionCondition$visualQueryAttentionListener$1.getClass();
                    switch (i22) {
                        case 0:
                            int i3 = AssistantAttentionCondition.$r8$clinit;
                            assistantAttentionCondition$visualQueryAttentionListener$1.this$0.updateCondition(true);
                            break;
                        default:
                            int i4 = AssistantAttentionCondition.$r8$clinit;
                            assistantAttentionCondition$visualQueryAttentionListener$1.this$0.updateCondition(false);
                            break;
                    }
                }
            };
        }
        ((ArrayList) list).forEach(consumer);
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.systemui.assist.AssistManager$1] */
    /* JADX WARN: Type inference failed for: r0v4, types: [com.android.systemui.assist.AssistManager$8] */
    public AssistManager(SubScreenManager subScreenManager, DeviceProvisionedController deviceProvisionedController, Context context, AssistUtils assistUtils, CommandQueue commandQueue, PhoneStateMonitor phoneStateMonitor, LauncherProxyService launcherProxyService, Lazy lazy, DefaultUiController defaultUiController, AssistLogger assistLogger, Handler handler, UserTracker userTracker, DisplayTracker displayTracker, SecureSettings secureSettings, SelectedUserInteractor selectedUserInteractor, ActivityManager activityManager, AssistInteractor assistInteractor, WindowManager windowManager, NavigationModeController navigationModeController, KeyguardManager keyguardManager) {
        this.navBarMode = 0;
        this.mContext = context;
        this.mDeviceProvisionedController = deviceProvisionedController;
        this.mCommandQueue = commandQueue;
        this.mAssistUtils = assistUtils;
        this.mAssistDisclosure = new AssistDisclosure(context, handler, windowManager);
        this.mLauncherProxyService = launcherProxyService;
        this.mPhoneStateMonitor = phoneStateMonitor;
        this.mAssistLogger = assistLogger;
        this.mUserTracker = userTracker;
        this.mDisplayTracker = displayTracker;
        this.mSecureSettings = secureSettings;
        this.mSelectedUserInteractor = selectedUserInteractor;
        this.mActivityManager = activityManager;
        this.mInteractor = assistInteractor;
        assistUtils.registerVoiceInteractionSessionListener(new IVoiceInteractionSessionListener.Stub() { // from class: com.android.systemui.assist.AssistManager.4
            public final void onSetUiHints(Bundle bundle) {
                String string = bundle.getString("action");
                if (BasicRune.NAVBAR_ADDITIONAL_LOG) {
                    KeyguardKnoxDualDarInnerPasswordViewController$$ExternalSyntheticOutline0.m("onSetUiHints(action:", string, ")", "AssistManager");
                }
                if ("set_assist_gesture_constrained".equals(string)) {
                    SysUiState flag = ((SysUiState) AssistManager.this.mSysUiState.get()).setFlag(8192L, bundle.getBoolean("should_constrain", false));
                    AssistManager.this.mDisplayTracker.getClass();
                    ((SysUiStateImpl) flag).commitUpdate();
                } else if ("show_global_actions".equals(string)) {
                    try {
                        Log.d("AssistManager", "onSetUiHints() Gemini call WindowManager.showGlobalActions()");
                        AssistManager.this.mWindowManager.showGlobalActions();
                    } catch (RemoteException e) {
                        Log.e("AssistManager", "showGlobalActions failed", e);
                    }
                }
            }

            public final void onVoiceSessionHidden() {
                AssistManager.this.mAssistLogger.reportAssistantSessionEvent(AssistantSessionEvent.ASSISTANT_SESSION_CLOSE);
            }

            public final void onVoiceSessionShown() {
                AssistManager.this.mAssistLogger.reportAssistantSessionEvent(AssistantSessionEvent.ASSISTANT_SESSION_UPDATE);
            }

            public final void onVoiceSessionWindowVisibilityChanged(boolean z) {
                if (!z || AssistManager.this.getAssistInfo() == null || AssistManager.this.getAssistInfo().getPackageName() == null) {
                    return;
                }
                SystemUIAnalytics.sendEventLog(SystemUIAnalytics.getCurrentScreenID(), SystemUIAnalytics.EID_ASSIST_VISIBLE, AssistManager.this.getAssistInfo().getPackageName());
            }
        });
        if (context.getResources().getBoolean(R.bool.config_enableVisualQueryAttentionDetection)) {
            assistUtils.subscribeVisualQueryRecognitionStatus(new IVisualQueryRecognitionStatusListener.Stub() { // from class: com.android.systemui.assist.AssistManager.6
                public final void onStartPerceiving() {
                    AssistManager assistManager = AssistManager.this;
                    assistManager.mAssistUtils.enableVisualQueryDetection(assistManager.mVisualQueryDetectionAttentionListener);
                    StatusBarManager statusBarManager = (StatusBarManager) AssistManager.this.mContext.getSystemService(StatusBarManager.class);
                    if (statusBarManager != null) {
                        statusBarManager.setIcon("assist_attention", R.drawable.ic_assistant_attention_indicator, 0, "Attention Icon for Assistant");
                        statusBarManager.setIconVisibility("assist_attention", false);
                    }
                }

                public final void onStopPerceiving() {
                    AssistManager.m1012$$Nest$mhandleVisualAttentionChanged(AssistManager.this, false);
                    AssistManager.this.mAssistUtils.disableVisualQueryDetection();
                    StatusBarManager statusBarManager = (StatusBarManager) AssistManager.this.mContext.getSystemService(StatusBarManager.class);
                    if (statusBarManager != null) {
                        statusBarManager.removeIcon("assist_attention");
                    }
                }
            });
        }
        this.mUiController = defaultUiController;
        this.mWindowManager = WindowManagerGlobal.getWindowManagerService();
        this.mSysUiState = lazy;
        this.mBroadcastDispatcher = (BroadcastDispatcher) Dependency.sDependency.getDependencyInner(BroadcastDispatcher.class);
        IntentFilter intentFilter = new IntentFilter();
        this.mFilter = intentFilter;
        intentFilter.addAction(PopupUIUtil.ACTION_CLOSE_SYSTEM_DIALOGS);
        launcherProxyService.addCallback(new LauncherProxyService.LauncherProxyListener() { // from class: com.android.systemui.assist.AssistManager.3
            @Override // com.android.systemui.recents.LauncherProxyService.LauncherProxyListener
            public final void onAssistantGestureCompletion(float f) {
                DefaultUiController defaultUiController2 = AssistManager.this.mUiController;
                defaultUiController2.animateInvocationCompletion();
                defaultUiController2.logInvocationProgressMetrics(1.0f, defaultUiController2.mInvocationInProgress);
            }

            @Override // com.android.systemui.recents.LauncherProxyService.LauncherProxyListener
            public final void onAssistantProgress(float f) {
                AssistManager.this.onInvocationProgress(f);
            }
        });
        if (BasicRune.NAVBAR_ENABLED) {
            this.navBarMode = navigationModeController.addListener(new NavigationModeController.ModeChangedListener() { // from class: com.android.systemui.assist.AssistManager$$ExternalSyntheticLambda0
                @Override // com.android.systemui.navigationbar.NavigationModeController.ModeChangedListener
                public final void onNavigationModeChanged(int i) {
                    AssistManager.this.navBarMode = i;
                }
            });
            this.mKeyguardManager = keyguardManager;
        }
        this.mSubScreenManager = subScreenManager;
    }

    public final ComponentName getAssistInfo() {
        return this.mAssistUtils.getAssistComponentForUser(this.mSelectedUserInteractor.getSelectedUserId());
    }

    public final String getDefaultRecognizer() {
        ResolveInfo resolveService = this.mContext.getPackageManager().resolveService(new Intent("android.speech.RecognitionService"), 128);
        if (resolveService == null || resolveService.serviceInfo == null) {
            Log.w("AssistManager", "Unable to resolve default voice recognition service.");
            return "";
        }
        ServiceInfo serviceInfo = resolveService.serviceInfo;
        return new ComponentName(serviceInfo.packageName, serviceInfo.name).flattenToShortString();
    }

    public final void hideAssist() {
        this.mAssistUtils.hideCurrentSession();
    }

    public final void onInvocationProgress(float f) {
        DefaultUiController defaultUiController = this.mUiController;
        boolean z = defaultUiController.mInvocationInProgress;
        if (f == 1.0f) {
            defaultUiController.animateInvocationCompletion();
        } else if (f == 0.0f) {
            defaultUiController.hide();
        } else {
            if (!z) {
                if (!defaultUiController.mAttached) {
                    defaultUiController.mWindowManager.addView(defaultUiController.mRoot, defaultUiController.mLayoutParams);
                    defaultUiController.mAttached = true;
                }
                defaultUiController.mInvocationInProgress = true;
            }
            defaultUiController.setProgressInternal(f);
        }
        defaultUiController.mLastInvocationProgress = f;
        defaultUiController.logInvocationProgressMetrics(f, z);
    }

    public final boolean shouldOverrideAssist(final int i) {
        int[] iArr = this.mAssistOverrideInvocationTypes;
        return iArr != null && Arrays.stream(iArr).anyMatch(new IntPredicate() { // from class: com.android.systemui.assist.AssistManager$$ExternalSyntheticLambda1
            @Override // java.util.function.IntPredicate
            public final boolean test(int i2) {
                int i3 = i;
                Map map = AssistManager.invocationTypeMap;
                return i2 == i3;
            }
        });
    }

    public final void showAssistanceAppSettingAlertDialog() {
        String str;
        ArrayList arrayList;
        PackageInfo packageInfo;
        int i;
        Drawable drawable;
        String str2;
        if (this.mAssistanceAppSettingAlertDialog == null) {
            LayoutInflater from = LayoutInflater.from(new ContextThemeWrapper(this.mContext, R.style.Theme_SystemUI_Dialog));
            this.mLayoutInflater = from;
            View inflate = from.inflate(R.layout.assistance_app_setting_alert_dialog_title, (ViewGroup) null);
            TextView textView = (TextView) inflate.findViewById(R.id.title);
            if (!BasicRune.NAVBAR_SUPPORT_SEARCLE) {
                textView.setText(this.mContext.getResources().getString(R.string.assistance_app_setting_alert_dialog_title_home));
            } else if (BasicRune.SUPPORT_AI_AGENT) {
                textView.setText(this.mContext.getResources().getString(R.string.assistance_app_setting_alert_dialog_title_side));
            } else {
                textView.setText(this.mContext.getResources().getString(R.string.assistance_app_setting_alert_dialog_title_swipe));
            }
            final ArrayList arrayList2 = new ArrayList();
            final AssistanceAppItemListAdapter assistanceAppItemListAdapter = new AssistanceAppItemListAdapter(this.mContext, R.layout.assistance_app_setting_item, arrayList2);
            ComponentName assistComponentForUser = this.mAssistUtils.getAssistComponentForUser(ActivityManager.getCurrentUser());
            String packageName = assistComponentForUser != null ? this.mAssistUtils.getAssistComponentForUser(ActivityManager.getCurrentUser()).getPackageName() : "";
            PackageManager packageManager = this.mContext.getPackageManager();
            ArrayList arrayList3 = new ArrayList();
            List<ResolveInfo> queryIntentServices = packageManager.queryIntentServices(new Intent("android.service.voice.VoiceInteractionService"), 128);
            String str3 = "";
            int i2 = -1;
            Drawable drawable2 = null;
            int i3 = 0;
            while (true) {
                str = str3;
                if (i3 >= queryIntentServices.size()) {
                    break;
                }
                ResolveInfo resolveInfo = queryIntentServices.get(i3);
                List<ResolveInfo> list = queryIntentServices;
                int i4 = i2;
                VoiceInteractionServiceInfo voiceInteractionServiceInfo = new VoiceInteractionServiceInfo(packageManager, resolveInfo.serviceInfo);
                String str4 = resolveInfo.serviceInfo.packageName;
                if (voiceInteractionServiceInfo.getSupportsAssist() && !arrayList3.contains(str4)) {
                    arrayList3.add(str4);
                    int size = packageName.equals(str4) ? arrayList3.size() - 1 : i4;
                    i = i3;
                    try {
                        PackageInfo packageInfo2 = packageManager.getPackageInfo(str4, 0);
                        drawable2 = packageManager.semGetApplicationIconForIconTray(str4, 1);
                        str2 = packageManager.getApplicationLabel(packageInfo2.applicationInfo).toString();
                        drawable = drawable2;
                    } catch (PackageManager.NameNotFoundException e) {
                        Log.w("AssistManager", "Failed to add assistance app " + str4 + " not found", e);
                        drawable = drawable2;
                        str2 = str;
                        arrayList2.add(new AssistanceAppItemList(new ComponentName(str4, resolveInfo.serviceInfo.name), voiceInteractionServiceInfo, drawable, str2, 1));
                        drawable2 = drawable;
                        str3 = str2;
                        i2 = size;
                        i3 = i + 1;
                        queryIntentServices = list;
                    } catch (Resources.NotFoundException e2) {
                        Log.w("AssistManager", "Failed to add assistance app " + str4, e2);
                        drawable = drawable2;
                        str2 = str;
                        arrayList2.add(new AssistanceAppItemList(new ComponentName(str4, resolveInfo.serviceInfo.name), voiceInteractionServiceInfo, drawable, str2, 1));
                        drawable2 = drawable;
                        str3 = str2;
                        i2 = size;
                        i3 = i + 1;
                        queryIntentServices = list;
                    } catch (NullPointerException e3) {
                        Log.w("AssistManager", "Failed to add assistance app " + str4, e3);
                        drawable = drawable2;
                        str2 = str;
                        arrayList2.add(new AssistanceAppItemList(new ComponentName(str4, resolveInfo.serviceInfo.name), voiceInteractionServiceInfo, drawable, str2, 1));
                        drawable2 = drawable;
                        str3 = str2;
                        i2 = size;
                        i3 = i + 1;
                        queryIntentServices = list;
                    }
                    arrayList2.add(new AssistanceAppItemList(new ComponentName(str4, resolveInfo.serviceInfo.name), voiceInteractionServiceInfo, drawable, str2, 1));
                    drawable2 = drawable;
                    str3 = str2;
                    i2 = size;
                } else {
                    i = i3;
                    str3 = str;
                    i2 = i4;
                }
                i3 = i + 1;
                queryIntentServices = list;
            }
            List<ResolveInfo> queryIntentActivities = packageManager.queryIntentActivities(new Intent("android.intent.action.ASSIST"), 65536);
            int i5 = i2;
            int i6 = 0;
            while (i6 < queryIntentActivities.size()) {
                ResolveInfo resolveInfo2 = queryIntentActivities.get(i6);
                List<ResolveInfo> list2 = queryIntentActivities;
                String str5 = resolveInfo2.activityInfo.packageName;
                if (arrayList3.contains(str5)) {
                    arrayList = arrayList3;
                } else {
                    arrayList3.add(str5);
                    if (packageName.equals(str5)) {
                        arrayList = arrayList3;
                        i5 = arrayList3.size() - 1;
                    } else {
                        arrayList = arrayList3;
                    }
                    int i7 = i5;
                    try {
                        try {
                            packageInfo = packageManager.getPackageInfo(str5, 0);
                        } catch (PackageManager.NameNotFoundException e4) {
                            Log.w("AssistManager", "Failed to add assistance app " + str5 + " not found", e4);
                        } catch (Resources.NotFoundException e5) {
                            Log.w("AssistManager", "Failed to add assistance app " + str5, e5);
                        }
                        try {
                            drawable2 = packageManager.semGetApplicationIconForIconTray(str5, 1);
                            str = packageManager.getApplicationLabel(packageInfo.applicationInfo).toString();
                        } catch (NullPointerException e6) {
                            e = e6;
                            Log.w("AssistManager", "Failed to add assistance app " + str5, e);
                            Drawable drawable3 = drawable2;
                            String str6 = str;
                            arrayList2.add(new AssistanceAppItemList(new ComponentName(str5, resolveInfo2.activityInfo.name), null, drawable3, str6, 2));
                            i5 = i7;
                            drawable2 = drawable3;
                            str = str6;
                            i6++;
                            queryIntentActivities = list2;
                            arrayList3 = arrayList;
                        }
                    } catch (NullPointerException e7) {
                        e = e7;
                    }
                    Drawable drawable32 = drawable2;
                    String str62 = str;
                    arrayList2.add(new AssistanceAppItemList(new ComponentName(str5, resolveInfo2.activityInfo.name), null, drawable32, str62, 2));
                    i5 = i7;
                    drawable2 = drawable32;
                    str = str62;
                }
                i6++;
                queryIntentActivities = list2;
                arrayList3 = arrayList;
            }
            assistanceAppItemListAdapter.mSelectedItem = i5;
            Log.d("AssistManager", "Current assistance app - " + assistComponentForUser + " package name - " + packageName + " defaultItem - " + i5);
            final int i8 = 0;
            DialogInterface.OnClickListener onClickListener = new DialogInterface.OnClickListener() { // from class: com.android.systemui.assist.AssistManager$$ExternalSyntheticLambda2
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i9) {
                    String defaultRecognizer;
                    final String str7;
                    String str8;
                    switch (i8) {
                        case 0:
                            ArrayList arrayList4 = (ArrayList) arrayList2;
                            AssistManager.AssistanceAppItemListAdapter assistanceAppItemListAdapter2 = assistanceAppItemListAdapter;
                            Map map = AssistManager.invocationTypeMap;
                            synchronized (arrayList4) {
                                assistanceAppItemListAdapter2.mSelectedItem = i9;
                                assistanceAppItemListAdapter2.notifyDataSetChanged();
                            }
                            return;
                        default:
                            AssistManager assistManager = (AssistManager) arrayList2;
                            AssistManager.AssistanceAppItemListAdapter assistanceAppItemListAdapter3 = assistanceAppItemListAdapter;
                            assistManager.mAssistPopupPositiveClicked = true;
                            Prefs.putBoolean(assistManager.mContext, "AssistanceAppSettingAlreadySelected", true);
                            int i10 = assistanceAppItemListAdapter3.mSelectedItem;
                            AssistManager.AssistanceAppItemList assistanceAppItemList = i10 < 0 ? null : (AssistManager.AssistanceAppItemList) assistanceAppItemListAdapter3.getItem(i10);
                            if (assistanceAppItemList == null) {
                                return;
                            }
                            int i11 = assistanceAppItemList.mAssistanceAppType;
                            String str9 = "";
                            if (i11 == 0) {
                                defaultRecognizer = assistManager.getDefaultRecognizer();
                                str7 = "None";
                                str8 = "";
                            } else if (i11 == 1) {
                                String packageName2 = assistanceAppItemList.mAssistanceComponent.getPackageName();
                                str8 = assistanceAppItemList.mAssistanceComponent.flattenToShortString();
                                defaultRecognizer = new ComponentName(packageName2, assistanceAppItemList.mAssistanceVoiceInteractionService.getRecognitionService()).flattenToShortString();
                                str7 = packageName2;
                                str9 = str8;
                            } else if (i11 != 2) {
                                defaultRecognizer = "";
                                str8 = defaultRecognizer;
                                str7 = str8;
                            } else {
                                str7 = assistanceAppItemList.mAssistanceComponent.getPackageName();
                                str8 = "";
                                str9 = assistanceAppItemList.mAssistanceComponent.flattenToShortString();
                                defaultRecognizer = assistManager.getDefaultRecognizer();
                            }
                            new SemRoleManager(assistManager.mContext).addRoleHolderAsUser("android.app.role.ASSISTANT", str7, 0, Process.myUserHandle(), assistManager.mContext.getMainExecutor(), new Consumer() { // from class: com.android.systemui.assist.AssistManager$$ExternalSyntheticLambda8
                                @Override // java.util.function.Consumer
                                public final void accept(Object obj) {
                                    String str10 = str7;
                                    Map map2 = AssistManager.invocationTypeMap;
                                    MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(((Boolean) obj).booleanValue() ? "role success = " : "role fail = ", str10, "AssistManager");
                                }
                            });
                            ((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).setAssistant(str9);
                            ((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).setVoiceInteractionServiceAssistant(str8);
                            ((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).setVoiceRecognitionService(defaultRecognizer);
                            SystemUIAnalytics.sendEventLog(SystemUIAnalytics.SID_ASSIST_POPUP_OPENED, SystemUIAnalytics.EID_ASSIST_POPUP_OK, str7);
                            return;
                    }
                }
            };
            AlertDialog.Builder builder = new AlertDialog.Builder(this.mContext);
            builder.setCustomTitle(inflate);
            builder.setSingleChoiceItems(assistanceAppItemListAdapter, i5, onClickListener);
            builder.setNegativeButton(android.R.string.cancel, (DialogInterface.OnClickListener) null);
            final int i9 = 1;
            builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() { // from class: com.android.systemui.assist.AssistManager$$ExternalSyntheticLambda2
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i92) {
                    String defaultRecognizer;
                    final String str7;
                    String str8;
                    switch (i9) {
                        case 0:
                            ArrayList arrayList4 = (ArrayList) this;
                            AssistManager.AssistanceAppItemListAdapter assistanceAppItemListAdapter2 = assistanceAppItemListAdapter;
                            Map map = AssistManager.invocationTypeMap;
                            synchronized (arrayList4) {
                                assistanceAppItemListAdapter2.mSelectedItem = i92;
                                assistanceAppItemListAdapter2.notifyDataSetChanged();
                            }
                            return;
                        default:
                            AssistManager assistManager = (AssistManager) this;
                            AssistManager.AssistanceAppItemListAdapter assistanceAppItemListAdapter3 = assistanceAppItemListAdapter;
                            assistManager.mAssistPopupPositiveClicked = true;
                            Prefs.putBoolean(assistManager.mContext, "AssistanceAppSettingAlreadySelected", true);
                            int i10 = assistanceAppItemListAdapter3.mSelectedItem;
                            AssistManager.AssistanceAppItemList assistanceAppItemList = i10 < 0 ? null : (AssistManager.AssistanceAppItemList) assistanceAppItemListAdapter3.getItem(i10);
                            if (assistanceAppItemList == null) {
                                return;
                            }
                            int i11 = assistanceAppItemList.mAssistanceAppType;
                            String str9 = "";
                            if (i11 == 0) {
                                defaultRecognizer = assistManager.getDefaultRecognizer();
                                str7 = "None";
                                str8 = "";
                            } else if (i11 == 1) {
                                String packageName2 = assistanceAppItemList.mAssistanceComponent.getPackageName();
                                str8 = assistanceAppItemList.mAssistanceComponent.flattenToShortString();
                                defaultRecognizer = new ComponentName(packageName2, assistanceAppItemList.mAssistanceVoiceInteractionService.getRecognitionService()).flattenToShortString();
                                str7 = packageName2;
                                str9 = str8;
                            } else if (i11 != 2) {
                                defaultRecognizer = "";
                                str8 = defaultRecognizer;
                                str7 = str8;
                            } else {
                                str7 = assistanceAppItemList.mAssistanceComponent.getPackageName();
                                str8 = "";
                                str9 = assistanceAppItemList.mAssistanceComponent.flattenToShortString();
                                defaultRecognizer = assistManager.getDefaultRecognizer();
                            }
                            new SemRoleManager(assistManager.mContext).addRoleHolderAsUser("android.app.role.ASSISTANT", str7, 0, Process.myUserHandle(), assistManager.mContext.getMainExecutor(), new Consumer() { // from class: com.android.systemui.assist.AssistManager$$ExternalSyntheticLambda8
                                @Override // java.util.function.Consumer
                                public final void accept(Object obj) {
                                    String str10 = str7;
                                    Map map2 = AssistManager.invocationTypeMap;
                                    MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(((Boolean) obj).booleanValue() ? "role success = " : "role fail = ", str10, "AssistManager");
                                }
                            });
                            ((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).setAssistant(str9);
                            ((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).setVoiceInteractionServiceAssistant(str8);
                            ((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).setVoiceRecognitionService(defaultRecognizer);
                            SystemUIAnalytics.sendEventLog(SystemUIAnalytics.SID_ASSIST_POPUP_OPENED, SystemUIAnalytics.EID_ASSIST_POPUP_OK, str7);
                            return;
                    }
                }
            });
            builder.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.android.systemui.assist.AssistManager$$ExternalSyntheticLambda4
                @Override // android.content.DialogInterface.OnDismissListener
                public final void onDismiss(DialogInterface dialogInterface) {
                    AssistManager assistManager = AssistManager.this;
                    assistManager.mAssistanceAppSettingAlertDialog = null;
                    if (!assistManager.mAssistPopupPositiveClicked) {
                        SystemUIAnalytics.sendEventLog(SystemUIAnalytics.SID_ASSIST_POPUP_OPENED, SystemUIAnalytics.EID_ASSIST_POPUP_CANCEL);
                    }
                    assistManager.mBroadcastDispatcher.unregisterReceiver(assistManager.mBroadcastReceiver);
                }
            });
            this.mAssistPopupPositiveClicked = false;
            AlertDialog create = builder.create();
            this.mAssistanceAppSettingAlertDialog = create;
            create.getWindow().setType(2009);
            this.mAssistanceAppSettingAlertDialog.setOnShowListener(new DialogInterface.OnShowListener() { // from class: com.android.systemui.assist.AssistManager$$ExternalSyntheticLambda5
                @Override // android.content.DialogInterface.OnShowListener
                public final void onShow(DialogInterface dialogInterface) {
                    AssistManager assistManager = AssistManager.this;
                    AssistManager.AnonymousClass8 anonymousClass8 = assistManager.mBroadcastReceiver;
                    assistManager.mBroadcastDispatcher.registerReceiver(assistManager.mFilter, anonymousClass8);
                }
            });
            this.mAssistanceAppSettingAlertDialog.show();
            SystemUIAnalytics.sendEventLog(SystemUIAnalytics.SID_ASSIST_POPUP_OPENED, SystemUIAnalytics.EID_ASSIST_POPUP_SHOW);
        }
    }

    public final void showDisclosure() {
        AssistDisclosure assistDisclosure = this.mAssistDisclosure;
        assistDisclosure.getClass();
        if (!((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).isAssistDisclosureEnabled()) {
            Log.d("AssistDisclosure", "AssistDisclosure VI is disabled");
            return;
        }
        AssistDisclosure.AnonymousClass1 anonymousClass1 = assistDisclosure.mShowRunnable;
        Handler handler = assistDisclosure.mHandler;
        handler.removeCallbacks(anonymousClass1);
        handler.post(assistDisclosure.mShowRunnable);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void startAssist(Bundle bundle) {
        final Intent assistIntent;
        SubHomeActivity subHomeActivity;
        String str;
        Bundle bundle2 = bundle == null ? new Bundle() : bundle;
        if (this.mActivityManager.getLockTaskModeState() == 1) {
            if (BasicRune.NAVBAR_ADDITIONAL_LOG) {
                Log.i("AssistManager", "Stop calling assistant by LockTaskMode");
                return;
            }
            return;
        }
        if (!bundle2.containsKey("invocation_type") ? false : shouldOverrideAssist(bundle2.getInt("invocation_type"))) {
            try {
                ILauncherProxy iLauncherProxy = this.mLauncherProxyService.mLauncherProxy;
                if (iLauncherProxy == null) {
                    Log.w("AssistManager", "No LauncherProxyService to invoke assistant override");
                    return;
                } else {
                    ((ILauncherProxy.Stub.Proxy) iLauncherProxy).onAssistantOverrideInvoked(bundle2.getInt("invocation_type"));
                    return;
                }
            } catch (RemoteException e) {
                Log.w("AssistManager", "Unable to invoke assistant via LauncherProxyService override", e);
                return;
            }
        }
        DeviceProvisionedControllerImpl deviceProvisionedControllerImpl = (DeviceProvisionedControllerImpl) this.mDeviceProvisionedController;
        if (((deviceProvisionedControllerImpl.deviceProvisioned.get() && (this.mDisabledFlags & 33554432) == 0) ? false : true) == true) {
            if (BasicRune.NAVBAR_ADDITIONAL_LOG) {
                Log.i("AssistManager", "Stop calling assistant by DISABLE_SEARCH");
                return;
            }
            return;
        }
        if (bundle2.getBoolean("android.intent.extra.ASSIST_INPUT_HINT_KEYBOARD") && !Prefs.getBoolean(this.mContext, "AssistanceAppSettingAlreadySelected", false) && !Prefs.getBoolean(this.mContext, "AssistanceMetaKeyPressed", false)) {
            String permissionControllerPackageName = this.mContext.getPackageManager().getPermissionControllerPackageName();
            Intent putExtra = permissionControllerPackageName != null ? new Intent("android.intent.action.MANAGE_DEFAULT_APP").setPackage(permissionControllerPackageName).putExtra("android.intent.extra.ROLE_NAME", "android.app.role.ASSISTANT") : null;
            if (putExtra != null) {
                this.mContext.startActivityAsUser(putExtra, UserHandle.CURRENT);
                Prefs.putBoolean(this.mContext, "AssistanceMetaKeyPressed", true);
            }
            if (BasicRune.NAVBAR_ADDITIONAL_LOG) {
                Log.i("AssistManager", "Stop calling assistant by showing AssistantSettingActivity");
                return;
            }
            return;
        }
        boolean z = BasicRune.ASSIST_ASSISTANCE_APP_SETTING_POPUP;
        if (z && !Prefs.getBoolean(this.mContext, "AssistanceAppSettingAlreadySelected", false)) {
            showAssistanceAppSettingAlertDialog();
            if (BasicRune.NAVBAR_ADDITIONAL_LOG) {
                Log.i("AssistManager", "Stop calling assistant by showing AssistanceAppSettingAlertDialog");
                return;
            }
            return;
        }
        boolean z2 = BasicRune.NAVBAR_ENABLED;
        if (z2) {
            byte b = Settings.Secure.getIntForUser(this.mContext.getContentResolver(), "assist_long_press_home_enabled", this.mContext.getResources().getBoolean(android.R.bool.config_autoPowerModePrefetchLocation) ? 1 : 0, ((UserTrackerImpl) this.mUserTracker).getUserId()) != 0;
            if (bundle2.getInt("invocation_type", 0) == 5) {
                if (b != true) {
                    Log.i("AssistManager", "Stop calling assistant by ASSIST_LONG_PRESS_HOME_ENABLED false");
                    return;
                } else if (BasicRune.SUPPORT_AI_AGENT) {
                    if (((((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).getNavigationBarSPluginFlags() & 8) != 0) == false) {
                        Log.i("AssistManager", "Stop calling assistant by AI_AGENT");
                        return;
                    }
                }
            }
        }
        ComponentName assistInfo = getAssistInfo();
        if (assistInfo == null) {
            if (BasicRune.NAVBAR_ADDITIONAL_LOG) {
                Log.i("AssistManager", "Stop calling assistant by assistComponent null");
                return;
            }
            return;
        }
        boolean equals = assistInfo.equals(this.mAssistUtils.getActiveServiceComponentName());
        int i = bundle2.getInt("invocation_type", 0);
        int phoneState = this.mPhoneStateMonitor.getPhoneState();
        bundle2.putInt("invocation_phone_state", phoneState);
        bundle2.putLong("invocation_time_ms", SystemClock.elapsedRealtime());
        this.mAssistLogger.reportAssistantInvocationEventFromLegacy(i, true, assistInfo, Integer.valueOf(phoneState));
        MetricsLogger.action(new LogMaker(1716).setType(1).setSubtype((i << 1) | (phoneState << 4)));
        this.mInteractor.repository._latestInvocationType.tryEmit(Integer.valueOf(i));
        if (z && !this.mIsAssistAppAvailable) {
            showAssistanceAppSettingAlertDialog();
            this.mIsAssistAppAvailable = true;
        }
        if (z2) {
            HashMap hashMap = new HashMap();
            int i2 = bundle2.getInt("invocation_type", 0);
            HashMap hashMap2 = (HashMap) invocationTypeMap;
            hashMap.put(SystemUIAnalytics.NAVBAR_KEY_ASSISTANT_INVOCATION_TYPE, hashMap2.containsKey(Integer.valueOf(i2)) ? i2 == 1 ? this.navBarMode == 3 ? "Swipe up handler and pause" : "Diagonal swipe" : (String) hashMap2.get(Integer.valueOf(i2)) : "others");
            hashMap.put(SystemUIAnalytics.NAVBAR_KEY_DIGITAL_ASSISTANT_APP, this.mAssistUtils.getAssistComponentForUser(ActivityManager.getCurrentUser()) != null ? this.mAssistUtils.getAssistComponentForUser(ActivityManager.getCurrentUser()).getPackageName() : "");
            try {
                str = ((ActivityManager.RunningTaskInfo) this.mActivityManager.getRunningTasks(1).getFirst()).topActivity.getPackageName();
            } catch (Exception e2) {
                e2.printStackTrace();
                str = C2paManifestList.UNKNOWN_VALUE;
            }
            hashMap.put(SystemUIAnalytics.NAVBAR_KEY_ASSISTANT_FOREGROUND_APP, str);
            hashMap.put(SystemUIAnalytics.NAVBAR_KEY_ASSISTANT_LOCK_STATE, this.mKeyguardManager.isKeyguardLocked() ? "Locked" : "Unlocked");
            if (BasicRune.BASIC_FOLDABLE_TYPE_FOLD || BasicRune.BASIC_FOLDABLE_TYPE_FLIP) {
                hashMap.put(SystemUIAnalytics.NAVBAR_KEY_ASSISTANT_COVER_STATE, SemWindowManager.getInstance().isFolded() ? "Cover" : "Main");
            }
            SystemUIAnalytics.sendEventCDLog(SystemUIAnalytics.getCurrentScreenID(), SystemUIAnalytics.EID_INVOKE_DIGITAL_ASSISTANT, hashMap);
        }
        if (BasicRune.NAVBAR_ADDITIONAL_LOG) {
            Log.d("AssistManager", "startAssistInternal");
        }
        if (LsRune.SUBSCREEN_LARGE_FRONT_SUB_DISPLAY) {
            SubScreenManager subScreenManager = this.mSubScreenManager;
            if (subScreenManager.mSubScreenPlugin == null) {
                Log.i("SubScreenManager", "prepareStartAssistOnCover plugin is null");
            } else if (!subScreenManager.mIsFolderOpened) {
                if (((KeyguardStateControllerImpl) subScreenManager.mKeyguardStateController).mShowing && ((subHomeActivity = subScreenManager.mActivity) == null || !subHomeActivity.semIsResumed())) {
                    subScreenManager.startSubHomeActivity();
                }
                subScreenManager.mSubScreenPlugin.requestDismissBouncer();
            }
        }
        if (equals) {
            this.mAssistUtils.showSessionForActiveService(bundle2, 4, this.mContext.getAttributionTag(), (IVoiceInteractionSessionShowCallback) null, (IBinder) null);
            return;
        }
        if (deviceProvisionedControllerImpl.deviceProvisioned.get()) {
            this.mCommandQueue.animateCollapsePanels(3, false);
            boolean z3 = this.mSecureSettings.getIntForUser("assist_structure_enabled", 1, -2) != 0;
            SearchManager searchManager = (SearchManager) this.mContext.getSystemService("search");
            if (searchManager == null || (assistIntent = searchManager.getAssistIntent(z3)) == null) {
                return;
            }
            assistIntent.setComponent(assistInfo);
            assistIntent.putExtras(bundle2);
            if (z3 && ((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).isAssistDisclosureEnabled()) {
                showDisclosure();
            }
            try {
                final ActivityOptions makeCustomAnimation = ActivityOptions.makeCustomAnimation(this.mContext, R.anim.search_launch_enter, R.anim.search_launch_exit);
                assistIntent.addFlags(268435456);
                AsyncTask.execute(new Runnable() { // from class: com.android.systemui.assist.AssistManager.5
                    @Override // java.lang.Runnable
                    public final void run() {
                        AssistManager.this.mContext.startActivityAsUser(assistIntent, makeCustomAnimation.toBundle(), ((UserTrackerImpl) AssistManager.this.mUserTracker).getUserHandle());
                    }
                });
            } catch (ActivityNotFoundException unused) {
                Log.w("AssistManager", "Activity not found for " + assistIntent.getAction());
            }
        }
    }
}
