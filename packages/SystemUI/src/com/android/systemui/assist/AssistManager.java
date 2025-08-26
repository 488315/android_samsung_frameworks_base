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
            AssistManager.m1014$$Nest$mhandleVisualAttentionChanged(AssistManager.this, true);
        }

        public final void onAttentionLost(int i) {
            AssistManager.m1014$$Nest$mhandleVisualAttentionChanged(AssistManager.this, false);
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
    public static void m1014$$Nest$mhandleVisualAttentionChanged(AssistManager assistManager, boolean z) {
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
                    AssistManager.m1014$$Nest$mhandleVisualAttentionChanged(AssistManager.this, false);
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
                    this.f$0.navBarMode = i;
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
        ResolveInfo resolveInfoResolveService = this.mContext.getPackageManager().resolveService(new Intent("android.speech.RecognitionService"), 128);
        if (resolveInfoResolveService == null || resolveInfoResolveService.serviceInfo == null) {
            Log.w("AssistManager", "Unable to resolve default voice recognition service.");
            return "";
        }
        ServiceInfo serviceInfo = resolveInfoResolveService.serviceInfo;
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

    public final void showAssistanceAppSettingAlertDialog() throws PackageManager.NameNotFoundException {
        String string;
        ArrayList arrayList;
        PackageInfo packageInfo;
        int i;
        Drawable drawable;
        String string2;
        if (this.mAssistanceAppSettingAlertDialog == null) {
            LayoutInflater layoutInflaterFrom = LayoutInflater.from(new ContextThemeWrapper(this.mContext, R.style.Theme_SystemUI_Dialog));
            this.mLayoutInflater = layoutInflaterFrom;
            View viewInflate = layoutInflaterFrom.inflate(R.layout.assistance_app_setting_alert_dialog_title, (ViewGroup) null);
            TextView textView = (TextView) viewInflate.findViewById(R.id.title);
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
            List<ResolveInfo> listQueryIntentServices = packageManager.queryIntentServices(new Intent("android.service.voice.VoiceInteractionService"), 128);
            String str = "";
            int i2 = -1;
            Drawable drawableSemGetApplicationIconForIconTray = null;
            int i3 = 0;
            while (true) {
                string = str;
                if (i3 >= listQueryIntentServices.size()) {
                    break;
                }
                ResolveInfo resolveInfo = listQueryIntentServices.get(i3);
                List<ResolveInfo> list = listQueryIntentServices;
                int i4 = i2;
                VoiceInteractionServiceInfo voiceInteractionServiceInfo = new VoiceInteractionServiceInfo(packageManager, resolveInfo.serviceInfo);
                String str2 = resolveInfo.serviceInfo.packageName;
                if (voiceInteractionServiceInfo.getSupportsAssist() && !arrayList3.contains(str2)) {
                    arrayList3.add(str2);
                    int size = packageName.equals(str2) ? arrayList3.size() - 1 : i4;
                    i = i3;
                    try {
                        PackageInfo packageInfo2 = packageManager.getPackageInfo(str2, 0);
                        drawableSemGetApplicationIconForIconTray = packageManager.semGetApplicationIconForIconTray(str2, 1);
                        string2 = packageManager.getApplicationLabel(packageInfo2.applicationInfo).toString();
                        drawable = drawableSemGetApplicationIconForIconTray;
                    } catch (PackageManager.NameNotFoundException e) {
                        Log.w("AssistManager", "Failed to add assistance app " + str2 + " not found", e);
                        drawable = drawableSemGetApplicationIconForIconTray;
                        string2 = string;
                        arrayList2.add(new AssistanceAppItemList(new ComponentName(str2, resolveInfo.serviceInfo.name), voiceInteractionServiceInfo, drawable, string2, 1));
                        drawableSemGetApplicationIconForIconTray = drawable;
                        str = string2;
                        i2 = size;
                        i3 = i + 1;
                        listQueryIntentServices = list;
                    } catch (Resources.NotFoundException e2) {
                        Log.w("AssistManager", "Failed to add assistance app " + str2, e2);
                        drawable = drawableSemGetApplicationIconForIconTray;
                        string2 = string;
                        arrayList2.add(new AssistanceAppItemList(new ComponentName(str2, resolveInfo.serviceInfo.name), voiceInteractionServiceInfo, drawable, string2, 1));
                        drawableSemGetApplicationIconForIconTray = drawable;
                        str = string2;
                        i2 = size;
                        i3 = i + 1;
                        listQueryIntentServices = list;
                    } catch (NullPointerException e3) {
                        Log.w("AssistManager", "Failed to add assistance app " + str2, e3);
                        drawable = drawableSemGetApplicationIconForIconTray;
                        string2 = string;
                        arrayList2.add(new AssistanceAppItemList(new ComponentName(str2, resolveInfo.serviceInfo.name), voiceInteractionServiceInfo, drawable, string2, 1));
                        drawableSemGetApplicationIconForIconTray = drawable;
                        str = string2;
                        i2 = size;
                        i3 = i + 1;
                        listQueryIntentServices = list;
                    }
                    arrayList2.add(new AssistanceAppItemList(new ComponentName(str2, resolveInfo.serviceInfo.name), voiceInteractionServiceInfo, drawable, string2, 1));
                    drawableSemGetApplicationIconForIconTray = drawable;
                    str = string2;
                    i2 = size;
                } else {
                    i = i3;
                    str = string;
                    i2 = i4;
                }
                i3 = i + 1;
                listQueryIntentServices = list;
            }
            List<ResolveInfo> listQueryIntentActivities = packageManager.queryIntentActivities(new Intent("android.intent.action.ASSIST"), 65536);
            int size2 = i2;
            int i5 = 0;
            while (i5 < listQueryIntentActivities.size()) {
                ResolveInfo resolveInfo2 = listQueryIntentActivities.get(i5);
                List<ResolveInfo> list2 = listQueryIntentActivities;
                String str3 = resolveInfo2.activityInfo.packageName;
                if (arrayList3.contains(str3)) {
                    arrayList = arrayList3;
                } else {
                    arrayList3.add(str3);
                    if (packageName.equals(str3)) {
                        arrayList = arrayList3;
                        size2 = arrayList3.size() - 1;
                    } else {
                        arrayList = arrayList3;
                    }
                    int i6 = size2;
                    try {
                        try {
                            packageInfo = packageManager.getPackageInfo(str3, 0);
                        } catch (NullPointerException e4) {
                            e = e4;
                        }
                        try {
                            drawableSemGetApplicationIconForIconTray = packageManager.semGetApplicationIconForIconTray(str3, 1);
                            string = packageManager.getApplicationLabel(packageInfo.applicationInfo).toString();
                        } catch (NullPointerException e5) {
                            e = e5;
                            Log.w("AssistManager", "Failed to add assistance app " + str3, e);
                            Drawable drawable2 = drawableSemGetApplicationIconForIconTray;
                            String str4 = string;
                            arrayList2.add(new AssistanceAppItemList(new ComponentName(str3, resolveInfo2.activityInfo.name), null, drawable2, str4, 2));
                            size2 = i6;
                            drawableSemGetApplicationIconForIconTray = drawable2;
                            string = str4;
                            i5++;
                            listQueryIntentActivities = list2;
                            arrayList3 = arrayList;
                        }
                    } catch (PackageManager.NameNotFoundException e6) {
                        Log.w("AssistManager", "Failed to add assistance app " + str3 + " not found", e6);
                    } catch (Resources.NotFoundException e7) {
                        Log.w("AssistManager", "Failed to add assistance app " + str3, e7);
                    }
                    Drawable drawable22 = drawableSemGetApplicationIconForIconTray;
                    String str42 = string;
                    arrayList2.add(new AssistanceAppItemList(new ComponentName(str3, resolveInfo2.activityInfo.name), null, drawable22, str42, 2));
                    size2 = i6;
                    drawableSemGetApplicationIconForIconTray = drawable22;
                    string = str42;
                }
                i5++;
                listQueryIntentActivities = list2;
                arrayList3 = arrayList;
            }
            assistanceAppItemListAdapter.mSelectedItem = size2;
            Log.d("AssistManager", "Current assistance app - " + assistComponentForUser + " package name - " + packageName + " defaultItem - " + size2);
            final int i7 = 0;
            DialogInterface.OnClickListener onClickListener = new DialogInterface.OnClickListener() { // from class: com.android.systemui.assist.AssistManager$$ExternalSyntheticLambda2
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i8) {
                    String defaultRecognizer;
                    final String packageName2;
                    String strFlattenToShortString;
                    switch (i7) {
                        case 0:
                            ArrayList arrayList4 = (ArrayList) arrayList2;
                            AssistManager.AssistanceAppItemListAdapter assistanceAppItemListAdapter2 = assistanceAppItemListAdapter;
                            Map map = AssistManager.invocationTypeMap;
                            synchronized (arrayList4) {
                                assistanceAppItemListAdapter2.mSelectedItem = i8;
                                assistanceAppItemListAdapter2.notifyDataSetChanged();
                            }
                            return;
                        default:
                            AssistManager assistManager = (AssistManager) arrayList2;
                            AssistManager.AssistanceAppItemListAdapter assistanceAppItemListAdapter3 = assistanceAppItemListAdapter;
                            assistManager.mAssistPopupPositiveClicked = true;
                            Prefs.putBoolean(assistManager.mContext, "AssistanceAppSettingAlreadySelected", true);
                            int i9 = assistanceAppItemListAdapter3.mSelectedItem;
                            AssistManager.AssistanceAppItemList assistanceAppItemList = i9 < 0 ? null : (AssistManager.AssistanceAppItemList) assistanceAppItemListAdapter3.getItem(i9);
                            if (assistanceAppItemList == null) {
                                return;
                            }
                            int i10 = assistanceAppItemList.mAssistanceAppType;
                            String strFlattenToShortString2 = "";
                            if (i10 == 0) {
                                defaultRecognizer = assistManager.getDefaultRecognizer();
                                packageName2 = "None";
                                strFlattenToShortString = "";
                            } else if (i10 == 1) {
                                String packageName3 = assistanceAppItemList.mAssistanceComponent.getPackageName();
                                strFlattenToShortString = assistanceAppItemList.mAssistanceComponent.flattenToShortString();
                                defaultRecognizer = new ComponentName(packageName3, assistanceAppItemList.mAssistanceVoiceInteractionService.getRecognitionService()).flattenToShortString();
                                packageName2 = packageName3;
                                strFlattenToShortString2 = strFlattenToShortString;
                            } else if (i10 != 2) {
                                defaultRecognizer = "";
                                strFlattenToShortString = defaultRecognizer;
                                packageName2 = strFlattenToShortString;
                            } else {
                                packageName2 = assistanceAppItemList.mAssistanceComponent.getPackageName();
                                strFlattenToShortString = "";
                                strFlattenToShortString2 = assistanceAppItemList.mAssistanceComponent.flattenToShortString();
                                defaultRecognizer = assistManager.getDefaultRecognizer();
                            }
                            new SemRoleManager(assistManager.mContext).addRoleHolderAsUser("android.app.role.ASSISTANT", packageName2, 0, Process.myUserHandle(), assistManager.mContext.getMainExecutor(), new Consumer() { // from class: com.android.systemui.assist.AssistManager$$ExternalSyntheticLambda8
                                @Override // java.util.function.Consumer
                                public final void accept(Object obj) {
                                    String str5 = packageName2;
                                    Map map2 = AssistManager.invocationTypeMap;
                                    MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(((Boolean) obj).booleanValue() ? "role success = " : "role fail = ", str5, "AssistManager");
                                }
                            });
                            ((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).setAssistant(strFlattenToShortString2);
                            ((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).setVoiceInteractionServiceAssistant(strFlattenToShortString);
                            ((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).setVoiceRecognitionService(defaultRecognizer);
                            SystemUIAnalytics.sendEventLog(SystemUIAnalytics.SID_ASSIST_POPUP_OPENED, SystemUIAnalytics.EID_ASSIST_POPUP_OK, packageName2);
                            return;
                    }
                }
            };
            AlertDialog.Builder builder = new AlertDialog.Builder(this.mContext);
            builder.setCustomTitle(viewInflate);
            builder.setSingleChoiceItems(assistanceAppItemListAdapter, size2, onClickListener);
            builder.setNegativeButton(android.R.string.cancel, (DialogInterface.OnClickListener) null);
            final int i8 = 1;
            builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() { // from class: com.android.systemui.assist.AssistManager$$ExternalSyntheticLambda2
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i82) {
                    String defaultRecognizer;
                    final String packageName2;
                    String strFlattenToShortString;
                    switch (i8) {
                        case 0:
                            ArrayList arrayList4 = (ArrayList) this;
                            AssistManager.AssistanceAppItemListAdapter assistanceAppItemListAdapter2 = assistanceAppItemListAdapter;
                            Map map = AssistManager.invocationTypeMap;
                            synchronized (arrayList4) {
                                assistanceAppItemListAdapter2.mSelectedItem = i82;
                                assistanceAppItemListAdapter2.notifyDataSetChanged();
                            }
                            return;
                        default:
                            AssistManager assistManager = (AssistManager) this;
                            AssistManager.AssistanceAppItemListAdapter assistanceAppItemListAdapter3 = assistanceAppItemListAdapter;
                            assistManager.mAssistPopupPositiveClicked = true;
                            Prefs.putBoolean(assistManager.mContext, "AssistanceAppSettingAlreadySelected", true);
                            int i9 = assistanceAppItemListAdapter3.mSelectedItem;
                            AssistManager.AssistanceAppItemList assistanceAppItemList = i9 < 0 ? null : (AssistManager.AssistanceAppItemList) assistanceAppItemListAdapter3.getItem(i9);
                            if (assistanceAppItemList == null) {
                                return;
                            }
                            int i10 = assistanceAppItemList.mAssistanceAppType;
                            String strFlattenToShortString2 = "";
                            if (i10 == 0) {
                                defaultRecognizer = assistManager.getDefaultRecognizer();
                                packageName2 = "None";
                                strFlattenToShortString = "";
                            } else if (i10 == 1) {
                                String packageName3 = assistanceAppItemList.mAssistanceComponent.getPackageName();
                                strFlattenToShortString = assistanceAppItemList.mAssistanceComponent.flattenToShortString();
                                defaultRecognizer = new ComponentName(packageName3, assistanceAppItemList.mAssistanceVoiceInteractionService.getRecognitionService()).flattenToShortString();
                                packageName2 = packageName3;
                                strFlattenToShortString2 = strFlattenToShortString;
                            } else if (i10 != 2) {
                                defaultRecognizer = "";
                                strFlattenToShortString = defaultRecognizer;
                                packageName2 = strFlattenToShortString;
                            } else {
                                packageName2 = assistanceAppItemList.mAssistanceComponent.getPackageName();
                                strFlattenToShortString = "";
                                strFlattenToShortString2 = assistanceAppItemList.mAssistanceComponent.flattenToShortString();
                                defaultRecognizer = assistManager.getDefaultRecognizer();
                            }
                            new SemRoleManager(assistManager.mContext).addRoleHolderAsUser("android.app.role.ASSISTANT", packageName2, 0, Process.myUserHandle(), assistManager.mContext.getMainExecutor(), new Consumer() { // from class: com.android.systemui.assist.AssistManager$$ExternalSyntheticLambda8
                                @Override // java.util.function.Consumer
                                public final void accept(Object obj) {
                                    String str5 = packageName2;
                                    Map map2 = AssistManager.invocationTypeMap;
                                    MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(((Boolean) obj).booleanValue() ? "role success = " : "role fail = ", str5, "AssistManager");
                                }
                            });
                            ((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).setAssistant(strFlattenToShortString2);
                            ((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).setVoiceInteractionServiceAssistant(strFlattenToShortString);
                            ((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).setVoiceRecognitionService(defaultRecognizer);
                            SystemUIAnalytics.sendEventLog(SystemUIAnalytics.SID_ASSIST_POPUP_OPENED, SystemUIAnalytics.EID_ASSIST_POPUP_OK, packageName2);
                            return;
                    }
                }
            });
            builder.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.android.systemui.assist.AssistManager$$ExternalSyntheticLambda4
                @Override // android.content.DialogInterface.OnDismissListener
                public final void onDismiss(DialogInterface dialogInterface) {
                    AssistManager assistManager = this.f$0;
                    assistManager.mAssistanceAppSettingAlertDialog = null;
                    if (!assistManager.mAssistPopupPositiveClicked) {
                        SystemUIAnalytics.sendEventLog(SystemUIAnalytics.SID_ASSIST_POPUP_OPENED, SystemUIAnalytics.EID_ASSIST_POPUP_CANCEL);
                    }
                    assistManager.mBroadcastDispatcher.unregisterReceiver(assistManager.mBroadcastReceiver);
                }
            });
            this.mAssistPopupPositiveClicked = false;
            AlertDialog alertDialogCreate = builder.create();
            this.mAssistanceAppSettingAlertDialog = alertDialogCreate;
            alertDialogCreate.getWindow().setType(2009);
            this.mAssistanceAppSettingAlertDialog.setOnShowListener(new DialogInterface.OnShowListener() { // from class: com.android.systemui.assist.AssistManager$$ExternalSyntheticLambda5
                @Override // android.content.DialogInterface.OnShowListener
                public final void onShow(DialogInterface dialogInterface) {
                    AssistManager assistManager = this.f$0;
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
    public final void startAssist(Bundle bundle) throws PackageManager.NameNotFoundException {
        final Intent assistIntent;
        SubHomeActivity subHomeActivity;
        String packageName;
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
            Intent intentPutExtra = permissionControllerPackageName != null ? new Intent("android.intent.action.MANAGE_DEFAULT_APP").setPackage(permissionControllerPackageName).putExtra("android.intent.extra.ROLE_NAME", "android.app.role.ASSISTANT") : null;
            if (intentPutExtra != null) {
                this.mContext.startActivityAsUser(intentPutExtra, UserHandle.CURRENT);
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
            Object[] objArr = Settings.Secure.getIntForUser(this.mContext.getContentResolver(), "assist_long_press_home_enabled", this.mContext.getResources().getBoolean(android.R.bool.config_autoPowerModePrefetchLocation) ? 1 : 0, ((UserTrackerImpl) this.mUserTracker).getUserId()) != 0;
            if (bundle2.getInt("invocation_type", 0) == 5) {
                if (objArr != true) {
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
        boolean zEquals = assistInfo.equals(this.mAssistUtils.getActiveServiceComponentName());
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
            HashMap map = new HashMap();
            int i2 = bundle2.getInt("invocation_type", 0);
            HashMap map2 = (HashMap) invocationTypeMap;
            map.put(SystemUIAnalytics.NAVBAR_KEY_ASSISTANT_INVOCATION_TYPE, map2.containsKey(Integer.valueOf(i2)) ? i2 == 1 ? this.navBarMode == 3 ? "Swipe up handler and pause" : "Diagonal swipe" : (String) map2.get(Integer.valueOf(i2)) : "others");
            map.put(SystemUIAnalytics.NAVBAR_KEY_DIGITAL_ASSISTANT_APP, this.mAssistUtils.getAssistComponentForUser(ActivityManager.getCurrentUser()) != null ? this.mAssistUtils.getAssistComponentForUser(ActivityManager.getCurrentUser()).getPackageName() : "");
            try {
                packageName = ((ActivityManager.RunningTaskInfo) this.mActivityManager.getRunningTasks(1).getFirst()).topActivity.getPackageName();
            } catch (Exception e2) {
                e2.printStackTrace();
                packageName = C2paManifestList.UNKNOWN_VALUE;
            }
            map.put(SystemUIAnalytics.NAVBAR_KEY_ASSISTANT_FOREGROUND_APP, packageName);
            map.put(SystemUIAnalytics.NAVBAR_KEY_ASSISTANT_LOCK_STATE, this.mKeyguardManager.isKeyguardLocked() ? "Locked" : "Unlocked");
            if (BasicRune.BASIC_FOLDABLE_TYPE_FOLD || BasicRune.BASIC_FOLDABLE_TYPE_FLIP) {
                map.put(SystemUIAnalytics.NAVBAR_KEY_ASSISTANT_COVER_STATE, SemWindowManager.getInstance().isFolded() ? "Cover" : "Main");
            }
            SystemUIAnalytics.sendEventCDLog(SystemUIAnalytics.getCurrentScreenID(), SystemUIAnalytics.EID_INVOKE_DIGITAL_ASSISTANT, map);
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
        if (zEquals) {
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
                final ActivityOptions activityOptionsMakeCustomAnimation = ActivityOptions.makeCustomAnimation(this.mContext, R.anim.search_launch_enter, R.anim.search_launch_exit);
                assistIntent.addFlags(268435456);
                AsyncTask.execute(new Runnable() { // from class: com.android.systemui.assist.AssistManager.5
                    @Override // java.lang.Runnable
                    public final void run() {
                        AssistManager.this.mContext.startActivityAsUser(assistIntent, activityOptionsMakeCustomAnimation.toBundle(), ((UserTrackerImpl) AssistManager.this.mUserTracker).getUserHandle());
                    }
                });
            } catch (ActivityNotFoundException unused) {
                Log.w("AssistManager", "Activity not found for " + assistIntent.getAction());
            }
        }
    }
}
