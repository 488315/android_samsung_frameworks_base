package com.android.systemui.assist;

import android.app.ActivityManager;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.app.SearchManager;
import android.app.StatusBarManager;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.android.systemui.BasicRune;
import com.android.systemui.Dependency;
import com.android.systemui.Flags;
import com.android.systemui.Prefs;
import com.android.systemui.R;
import com.android.systemui.assist.AssistDisclosure;
import com.android.systemui.assist.AssistManager;
import com.android.systemui.assist.domain.interactor.AssistInteractor;
import com.android.systemui.assist.ui.DefaultUiController;
import com.android.systemui.dreams.conditions.AssistantAttentionCondition;
import com.android.systemui.model.SysUiState;
import com.android.systemui.recents.OverviewProxyService;
import com.android.systemui.settings.DisplayTracker;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.shared.recents.IOverviewProxy;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import com.android.systemui.statusbar.policy.DeviceProvisionedControllerImpl;
import com.android.systemui.user.domain.interactor.SelectedUserInteractor;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.SystemUIAnalytics;
import com.android.systemui.util.settings.SecureSettings;
import com.samsung.android.app.SemRoleManager;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import dagger.Lazy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.IntPredicate;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final class AssistManager {
    public final ActivityManager mActivityManager;
    public final AssistDisclosure mAssistDisclosure;
    public final AssistLogger mAssistLogger;
    public int[] mAssistOverrideInvocationTypes;
    public boolean mAssistPopupPositiveClicked;
    public final AssistUtils mAssistUtils;
    public AlertDialog mAssistanceAppSettingAlertDialog;
    public final CommandQueue mCommandQueue;
    public final Context mContext;
    public final DeviceProvisionedController mDeviceProvisionedController;
    public int mDisabledFlags;
    public final DisplayTracker mDisplayTracker;
    public final AssistInteractor mInteractor;
    public LayoutInflater mLayoutInflater;
    public final OverviewProxyService mOverviewProxyService;
    public final PhoneStateMonitor mPhoneStateMonitor;
    public final SecureSettings mSecureSettings;
    public final SelectedUserInteractor mSelectedUserInteractor;
    public final Lazy mSysUiState;
    public final DefaultUiController mUiController;
    public final UserTracker mUserTracker;
    public final IWindowManager mWindowManager;
    public final List mVisualQueryAttentionListeners = new ArrayList();
    public final AnonymousClass1 mVisualQueryDetectionAttentionListener = new IVisualQueryDetectionAttentionListener.Stub() { // from class: com.android.systemui.assist.AssistManager.1
        public final void onAttentionGained(VisualQueryAttentionResult visualQueryAttentionResult) {
            AssistManager.m886$$Nest$mhandleVisualAttentionChanged(AssistManager.this, true);
        }

        public final void onAttentionLost(int i) {
            AssistManager.m886$$Nest$mhandleVisualAttentionChanged(AssistManager.this, false);
        }
    };
    public boolean mIsAssistAppAvailable = true;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class AssistanceAppItemList {
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

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class AssistanceAppItemListAdapter extends ArrayAdapter {
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
                    } else {
                        checkedTextView.setChecked(false);
                    }
                }
            }
            return linearLayout;
        }
    }

    /* renamed from: -$$Nest$mhandleVisualAttentionChanged, reason: not valid java name */
    public static void m886$$Nest$mhandleVisualAttentionChanged(AssistManager assistManager, boolean z) {
        Consumer consumer;
        StatusBarManager statusBarManager = (StatusBarManager) assistManager.mContext.getSystemService(StatusBarManager.class);
        if (statusBarManager != null) {
            statusBarManager.setIconVisibility("assist_attention", z);
        }
        List list = assistManager.mVisualQueryAttentionListeners;
        if (z) {
            final int i = 0;
            consumer = new Consumer() { // from class: com.android.systemui.assist.AssistManager$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    AssistantAttentionCondition.AnonymousClass1 anonymousClass1 = (AssistantAttentionCondition.AnonymousClass1) obj;
                    switch (i) {
                        case 0:
                            AssistantAttentionCondition.this.updateCondition(true);
                            break;
                        default:
                            AssistantAttentionCondition.this.updateCondition(false);
                            break;
                    }
                }
            };
        } else {
            final int i2 = 1;
            consumer = new Consumer() { // from class: com.android.systemui.assist.AssistManager$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    AssistantAttentionCondition.AnonymousClass1 anonymousClass1 = (AssistantAttentionCondition.AnonymousClass1) obj;
                    switch (i2) {
                        case 0:
                            AssistantAttentionCondition.this.updateCondition(true);
                            break;
                        default:
                            AssistantAttentionCondition.this.updateCondition(false);
                            break;
                    }
                }
            };
        }
        ((ArrayList) list).forEach(consumer);
    }

    /* JADX WARN: Type inference failed for: r4v1, types: [com.android.systemui.assist.AssistManager$1] */
    public AssistManager(DeviceProvisionedController deviceProvisionedController, Context context, AssistUtils assistUtils, CommandQueue commandQueue, PhoneStateMonitor phoneStateMonitor, OverviewProxyService overviewProxyService, Lazy lazy, DefaultUiController defaultUiController, AssistLogger assistLogger, Handler handler, UserTracker userTracker, DisplayTracker displayTracker, SecureSettings secureSettings, SelectedUserInteractor selectedUserInteractor, ActivityManager activityManager, AssistInteractor assistInteractor) {
        this.mContext = context;
        this.mDeviceProvisionedController = deviceProvisionedController;
        this.mCommandQueue = commandQueue;
        this.mAssistUtils = assistUtils;
        this.mAssistDisclosure = new AssistDisclosure(context, handler);
        this.mOverviewProxyService = overviewProxyService;
        this.mPhoneStateMonitor = phoneStateMonitor;
        this.mAssistLogger = assistLogger;
        this.mUserTracker = userTracker;
        this.mDisplayTracker = displayTracker;
        this.mSecureSettings = secureSettings;
        this.mSelectedUserInteractor = selectedUserInteractor;
        this.mActivityManager = activityManager;
        this.mInteractor = assistInteractor;
        assistUtils.registerVoiceInteractionSessionListener(new IVoiceInteractionSessionListener.Stub() { // from class: com.android.systemui.assist.AssistManager.3
            public final void onSetUiHints(Bundle bundle) {
                String string = bundle.getString("action");
                if ("set_assist_gesture_constrained".equals(string)) {
                    SysUiState sysUiState = (SysUiState) AssistManager.this.mSysUiState.get();
                    sysUiState.setFlag(8192L, bundle.getBoolean("should_constrain", false));
                    AssistManager.this.mDisplayTracker.getClass();
                    sysUiState.commitUpdate(0);
                    return;
                }
                if ("show_global_actions".equals(string)) {
                    try {
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
            assistUtils.subscribeVisualQueryRecognitionStatus(new IVisualQueryRecognitionStatusListener.Stub() { // from class: com.android.systemui.assist.AssistManager.5
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
                    AssistManager.m886$$Nest$mhandleVisualAttentionChanged(AssistManager.this, false);
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
        overviewProxyService.addCallback(new OverviewProxyService.OverviewProxyListener() { // from class: com.android.systemui.assist.AssistManager.2
            @Override // com.android.systemui.recents.OverviewProxyService.OverviewProxyListener
            public final void onAssistantGestureCompletion(float f) {
                DefaultUiController defaultUiController2 = AssistManager.this.mUiController;
                defaultUiController2.animateInvocationCompletion();
                defaultUiController2.logInvocationProgressMetrics(1.0f, defaultUiController2.mInvocationInProgress);
            }

            @Override // com.android.systemui.recents.OverviewProxyService.OverviewProxyListener
            public final void onAssistantProgress(float f) {
                AssistManager.this.onInvocationProgress(f);
            }
        });
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
        return iArr != null && Arrays.stream(iArr).anyMatch(new IntPredicate() { // from class: com.android.systemui.assist.AssistManager$$ExternalSyntheticLambda2
            @Override // java.util.function.IntPredicate
            public final boolean test(int i2) {
                return i2 == i;
            }
        });
    }

    public final void showAssistanceAppSettingAlertDialog() {
        String str;
        List<ResolveInfo> list;
        PackageInfo packageInfo;
        View view;
        int i;
        Drawable drawable;
        Drawable drawable2;
        PackageInfo packageInfo2;
        if (this.mAssistanceAppSettingAlertDialog == null) {
            LayoutInflater from = LayoutInflater.from(new ContextThemeWrapper(this.mContext, R.style.Theme_SystemUI_Dialog));
            this.mLayoutInflater = from;
            View inflate = from.inflate(R.layout.assistance_app_setting_alert_dialog_title, (ViewGroup) null);
            TextView textView = (TextView) inflate.findViewById(R.id.title);
            if (BasicRune.NAVBAR_SUPPORT_SEARCLE) {
                textView.setText(this.mContext.getResources().getString(R.string.assistance_app_setting_alert_dialog_title_swipe));
            } else {
                textView.setText(this.mContext.getResources().getString(R.string.assistance_app_setting_alert_dialog_title));
            }
            final ArrayList arrayList = new ArrayList();
            final AssistanceAppItemListAdapter assistanceAppItemListAdapter = new AssistanceAppItemListAdapter(this.mContext, R.layout.assistance_app_setting_item, arrayList);
            ComponentName assistComponentForUser = this.mAssistUtils.getAssistComponentForUser(ActivityManager.getCurrentUser());
            String packageName = assistComponentForUser != null ? this.mAssistUtils.getAssistComponentForUser(ActivityManager.getCurrentUser()).getPackageName() : "";
            PackageManager packageManager = this.mContext.getPackageManager();
            ArrayList arrayList2 = new ArrayList();
            List<ResolveInfo> queryIntentServices = packageManager.queryIntentServices(new Intent("android.service.voice.VoiceInteractionService"), 128);
            Drawable drawable3 = null;
            int i2 = 0;
            String str2 = "";
            int i3 = -1;
            while (true) {
                str = str2;
                if (i2 >= queryIntentServices.size()) {
                    break;
                }
                ResolveInfo resolveInfo = queryIntentServices.get(i2);
                List<ResolveInfo> list2 = queryIntentServices;
                int i4 = i3;
                VoiceInteractionServiceInfo voiceInteractionServiceInfo = new VoiceInteractionServiceInfo(packageManager, resolveInfo.serviceInfo);
                Drawable drawable4 = drawable3;
                String str3 = resolveInfo.serviceInfo.packageName;
                if (voiceInteractionServiceInfo.getSupportsAssist() && !arrayList2.contains(str3)) {
                    arrayList2.add(str3);
                    if (packageName.equals(str3)) {
                        view = inflate;
                        i = arrayList2.size() - 1;
                    } else {
                        view = inflate;
                        i = i4;
                    }
                    try {
                        packageInfo2 = packageManager.getPackageInfo(str3, 0);
                        drawable = packageManager.semGetApplicationIconForIconTray(str3, 1);
                    } catch (PackageManager.NameNotFoundException e) {
                        e = e;
                        drawable2 = drawable4;
                    } catch (Resources.NotFoundException e2) {
                        e = e2;
                        drawable = drawable4;
                    } catch (NullPointerException e3) {
                        e = e3;
                        drawable = drawable4;
                    }
                    try {
                        str2 = packageManager.getApplicationLabel(packageInfo2.applicationInfo).toString();
                    } catch (PackageManager.NameNotFoundException e4) {
                        e = e4;
                        drawable2 = drawable;
                        Log.w("AssistManager", "Failed to add assistance app " + str3 + " not found", e);
                        str2 = str;
                        drawable = drawable2;
                        arrayList.add(new AssistanceAppItemList(new ComponentName(str3, resolveInfo.serviceInfo.name), voiceInteractionServiceInfo, drawable, str2, 1));
                        drawable3 = drawable;
                        i3 = i;
                        i2++;
                        queryIntentServices = list2;
                        inflate = view;
                    } catch (Resources.NotFoundException e5) {
                        e = e5;
                        Log.w("AssistManager", "Failed to add assistance app " + str3, e);
                        str2 = str;
                        arrayList.add(new AssistanceAppItemList(new ComponentName(str3, resolveInfo.serviceInfo.name), voiceInteractionServiceInfo, drawable, str2, 1));
                        drawable3 = drawable;
                        i3 = i;
                        i2++;
                        queryIntentServices = list2;
                        inflate = view;
                    } catch (NullPointerException e6) {
                        e = e6;
                        Log.w("AssistManager", "Failed to add assistance app " + str3, e);
                        str2 = str;
                        arrayList.add(new AssistanceAppItemList(new ComponentName(str3, resolveInfo.serviceInfo.name), voiceInteractionServiceInfo, drawable, str2, 1));
                        drawable3 = drawable;
                        i3 = i;
                        i2++;
                        queryIntentServices = list2;
                        inflate = view;
                    }
                    arrayList.add(new AssistanceAppItemList(new ComponentName(str3, resolveInfo.serviceInfo.name), voiceInteractionServiceInfo, drawable, str2, 1));
                    drawable3 = drawable;
                    i3 = i;
                } else {
                    view = inflate;
                    str2 = str;
                    i3 = i4;
                    drawable3 = drawable4;
                }
                i2++;
                queryIntentServices = list2;
                inflate = view;
            }
            View view2 = inflate;
            List<ResolveInfo> queryIntentActivities = packageManager.queryIntentActivities(new Intent("android.intent.action.ASSIST"), 65536);
            int i5 = i3;
            int i6 = 0;
            while (i6 < queryIntentActivities.size()) {
                ResolveInfo resolveInfo2 = queryIntentActivities.get(i6);
                String str4 = resolveInfo2.activityInfo.packageName;
                if (arrayList2.contains(str4)) {
                    list = queryIntentActivities;
                } else {
                    arrayList2.add(str4);
                    if (packageName.equals(str4)) {
                        list = queryIntentActivities;
                        i5 = arrayList2.size() - 1;
                    } else {
                        list = queryIntentActivities;
                    }
                    int i7 = i5;
                    try {
                        try {
                            packageInfo = packageManager.getPackageInfo(str4, 0);
                        } catch (PackageManager.NameNotFoundException e7) {
                            Log.w("AssistManager", "Failed to add assistance app " + str4 + " not found", e7);
                        } catch (Resources.NotFoundException e8) {
                            Log.w("AssistManager", "Failed to add assistance app " + str4, e8);
                        }
                    } catch (NullPointerException e9) {
                        e = e9;
                    }
                    try {
                        drawable3 = packageManager.semGetApplicationIconForIconTray(str4, 1);
                        str = packageManager.getApplicationLabel(packageInfo.applicationInfo).toString();
                    } catch (NullPointerException e10) {
                        e = e10;
                        Log.w("AssistManager", "Failed to add assistance app " + str4, e);
                        arrayList.add(new AssistanceAppItemList(new ComponentName(str4, resolveInfo2.activityInfo.name), null, drawable3, str, 2));
                        i5 = i7;
                        i6++;
                        queryIntentActivities = list;
                    }
                    arrayList.add(new AssistanceAppItemList(new ComponentName(str4, resolveInfo2.activityInfo.name), null, drawable3, str, 2));
                    i5 = i7;
                }
                i6++;
                queryIntentActivities = list;
            }
            arrayList.add(new AssistanceAppItemList(null, null, this.mContext.getDrawable(R.drawable.ic_remove_circle), this.mContext.getString(R.string.assistance_app_setting_item_none), 0));
            if (i5 < 0) {
                i5 = arrayList2.size();
            }
            assistanceAppItemListAdapter.mSelectedItem = i5;
            Log.d("AssistManager", "Current assistance app - " + assistComponentForUser + " package name - " + packageName + " defaultItem - " + i5);
            final int i8 = 0;
            DialogInterface.OnClickListener onClickListener = new DialogInterface.OnClickListener() { // from class: com.android.systemui.assist.AssistManager$$ExternalSyntheticLambda3
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i9) {
                    String defaultRecognizer;
                    String str5;
                    String str6;
                    switch (i8) {
                        case 0:
                            ArrayList arrayList3 = (ArrayList) arrayList;
                            ArrayAdapter arrayAdapter = assistanceAppItemListAdapter;
                            synchronized (arrayList3) {
                                ((AssistManager.AssistanceAppItemListAdapter) arrayAdapter).mSelectedItem = i9;
                                arrayAdapter.notifyDataSetChanged();
                            }
                            return;
                        default:
                            AssistManager assistManager = (AssistManager) arrayList;
                            ArrayAdapter arrayAdapter2 = assistanceAppItemListAdapter;
                            assistManager.mAssistPopupPositiveClicked = true;
                            Prefs.putBoolean(assistManager.mContext, "AssistanceAppSettingAlreadySelected", true);
                            AssistManager.AssistanceAppItemListAdapter assistanceAppItemListAdapter2 = (AssistManager.AssistanceAppItemListAdapter) arrayAdapter2;
                            AssistManager.AssistanceAppItemList assistanceAppItemList = (AssistManager.AssistanceAppItemList) assistanceAppItemListAdapter2.getItem(assistanceAppItemListAdapter2.mSelectedItem);
                            int i10 = assistanceAppItemList.mAssistanceAppType;
                            final String str7 = "";
                            if (i10 == 0) {
                                defaultRecognizer = assistManager.getDefaultRecognizer();
                                str5 = "";
                                str7 = "None";
                                str6 = str5;
                            } else if (i10 == 1) {
                                str7 = assistanceAppItemList.mAssistanceComponent.getPackageName();
                                str6 = assistanceAppItemList.mAssistanceComponent.flattenToShortString();
                                defaultRecognizer = new ComponentName(str7, assistanceAppItemList.mAssistanceVoiceInteractionService.getRecognitionService()).flattenToShortString();
                                str5 = str6;
                            } else if (i10 != 2) {
                                str5 = "";
                                str6 = str5;
                                defaultRecognizer = str6;
                            } else {
                                String packageName2 = assistanceAppItemList.mAssistanceComponent.getPackageName();
                                str5 = assistanceAppItemList.mAssistanceComponent.flattenToShortString();
                                defaultRecognizer = assistManager.getDefaultRecognizer();
                                str7 = packageName2;
                                str6 = "";
                            }
                            String str8 = str7;
                            new SemRoleManager(assistManager.mContext).addRoleHolderAsUser("android.app.role.ASSISTANT", str8, 0, Process.myUserHandle(), assistManager.mContext.getMainExecutor(), new Consumer() { // from class: com.android.systemui.assist.AssistManager$$ExternalSyntheticLambda6
                                @Override // java.util.function.Consumer
                                public final void accept(Object obj) {
                                    MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(((Boolean) obj).booleanValue() ? "role success = " : "role fail = ", str7, "AssistManager");
                                }
                            });
                            ((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).setAssistant(str5);
                            ((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).setVoiceInteractionServiceAssistant(str6);
                            ((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).setVoiceRecognitionService(defaultRecognizer);
                            SystemUIAnalytics.sendEventLog(SystemUIAnalytics.SID_ASSIST_POPUP_OPENED, SystemUIAnalytics.EID_ASSIST_POPUP_OK, str7);
                            return;
                    }
                }
            };
            AlertDialog.Builder builder = new AlertDialog.Builder(this.mContext);
            builder.setCustomTitle(view2);
            builder.setSingleChoiceItems(assistanceAppItemListAdapter, i5, onClickListener);
            builder.setNegativeButton(android.R.string.cancel, (DialogInterface.OnClickListener) null);
            final int i9 = 1;
            builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() { // from class: com.android.systemui.assist.AssistManager$$ExternalSyntheticLambda3
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i92) {
                    String defaultRecognizer;
                    String str5;
                    String str6;
                    switch (i9) {
                        case 0:
                            ArrayList arrayList3 = (ArrayList) this;
                            ArrayAdapter arrayAdapter = assistanceAppItemListAdapter;
                            synchronized (arrayList3) {
                                ((AssistManager.AssistanceAppItemListAdapter) arrayAdapter).mSelectedItem = i92;
                                arrayAdapter.notifyDataSetChanged();
                            }
                            return;
                        default:
                            AssistManager assistManager = (AssistManager) this;
                            ArrayAdapter arrayAdapter2 = assistanceAppItemListAdapter;
                            assistManager.mAssistPopupPositiveClicked = true;
                            Prefs.putBoolean(assistManager.mContext, "AssistanceAppSettingAlreadySelected", true);
                            AssistManager.AssistanceAppItemListAdapter assistanceAppItemListAdapter2 = (AssistManager.AssistanceAppItemListAdapter) arrayAdapter2;
                            AssistManager.AssistanceAppItemList assistanceAppItemList = (AssistManager.AssistanceAppItemList) assistanceAppItemListAdapter2.getItem(assistanceAppItemListAdapter2.mSelectedItem);
                            int i10 = assistanceAppItemList.mAssistanceAppType;
                            final String str7 = "";
                            if (i10 == 0) {
                                defaultRecognizer = assistManager.getDefaultRecognizer();
                                str5 = "";
                                str7 = "None";
                                str6 = str5;
                            } else if (i10 == 1) {
                                str7 = assistanceAppItemList.mAssistanceComponent.getPackageName();
                                str6 = assistanceAppItemList.mAssistanceComponent.flattenToShortString();
                                defaultRecognizer = new ComponentName(str7, assistanceAppItemList.mAssistanceVoiceInteractionService.getRecognitionService()).flattenToShortString();
                                str5 = str6;
                            } else if (i10 != 2) {
                                str5 = "";
                                str6 = str5;
                                defaultRecognizer = str6;
                            } else {
                                String packageName2 = assistanceAppItemList.mAssistanceComponent.getPackageName();
                                str5 = assistanceAppItemList.mAssistanceComponent.flattenToShortString();
                                defaultRecognizer = assistManager.getDefaultRecognizer();
                                str7 = packageName2;
                                str6 = "";
                            }
                            String str8 = str7;
                            new SemRoleManager(assistManager.mContext).addRoleHolderAsUser("android.app.role.ASSISTANT", str8, 0, Process.myUserHandle(), assistManager.mContext.getMainExecutor(), new Consumer() { // from class: com.android.systemui.assist.AssistManager$$ExternalSyntheticLambda6
                                @Override // java.util.function.Consumer
                                public final void accept(Object obj) {
                                    MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(((Boolean) obj).booleanValue() ? "role success = " : "role fail = ", str7, "AssistManager");
                                }
                            });
                            ((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).setAssistant(str5);
                            ((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).setVoiceInteractionServiceAssistant(str6);
                            ((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).setVoiceRecognitionService(defaultRecognizer);
                            SystemUIAnalytics.sendEventLog(SystemUIAnalytics.SID_ASSIST_POPUP_OPENED, SystemUIAnalytics.EID_ASSIST_POPUP_OK, str7);
                            return;
                    }
                }
            });
            builder.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.android.systemui.assist.AssistManager$$ExternalSyntheticLambda5
                @Override // android.content.DialogInterface.OnDismissListener
                public final void onDismiss(DialogInterface dialogInterface) {
                    AssistManager assistManager = AssistManager.this;
                    assistManager.mAssistanceAppSettingAlertDialog = null;
                    if (assistManager.mAssistPopupPositiveClicked) {
                        return;
                    }
                    SystemUIAnalytics.sendEventLog(SystemUIAnalytics.SID_ASSIST_POPUP_OPENED, SystemUIAnalytics.EID_ASSIST_POPUP_CANCEL);
                }
            });
            this.mAssistPopupPositiveClicked = false;
            AlertDialog create = builder.create();
            this.mAssistanceAppSettingAlertDialog = create;
            create.getWindow().setType(2009);
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
        if (bundle == null) {
            bundle = new Bundle();
        }
        Bundle bundle2 = bundle;
        if (this.mActivityManager.getLockTaskModeState() == 1) {
            if (BasicRune.NAVBAR_ADDITIONAL_LOG) {
                Log.i("AssistManager", "Stop calling assistant by LockTaskMode");
                return;
            }
            return;
        }
        if (!bundle2.containsKey("invocation_type") ? false : shouldOverrideAssist(bundle2.getInt("invocation_type"))) {
            try {
                IOverviewProxy iOverviewProxy = this.mOverviewProxyService.mOverviewProxy;
                if (iOverviewProxy == null) {
                    Log.w("AssistManager", "No OverviewProxyService to invoke assistant override");
                    return;
                } else {
                    ((IOverviewProxy.Stub.Proxy) iOverviewProxy).onAssistantOverrideInvoked(bundle2.getInt("invocation_type"));
                    return;
                }
            } catch (RemoteException e) {
                Log.w("AssistManager", "Unable to invoke assistant via OverviewProxyService override", e);
                return;
            }
        }
        DeviceProvisionedControllerImpl deviceProvisionedControllerImpl = (DeviceProvisionedControllerImpl) this.mDeviceProvisionedController;
        if (((deviceProvisionedControllerImpl.deviceProvisioned.get() && (this.mDisabledFlags & QuickStepContract.SYSUI_STATE_GAME_TOOLS_SHOWING) == 0) ? false : true) == true) {
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
            byte b = Settings.Secure.getIntForUser(this.mContext.getContentResolver(), "assist_long_press_home_enabled", this.mContext.getResources().getBoolean(android.R.bool.config_audio_ringer_mode_affects_alarm_stream) ? 1 : 0, ((UserTrackerImpl) this.mUserTracker).getUserId()) != 0;
            if (bundle2.getInt("invocation_type", 0) == 5 && b == false && !BasicRune.SUPPORT_AI_AGENT) {
                Log.i("AssistManager", "Stop calling assistant by ASSIST_LONG_PRESS_HOME_ENABLED false");
                return;
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
        MetricsLogger.action(new LogMaker(1716).setType(1).setSubtype((phoneState << 4) | (i << 1)));
        AssistInteractor assistInteractor = this.mInteractor;
        assistInteractor.getClass();
        Flags.FEATURE_FLAGS.getClass();
        assistInteractor.repository._latestInvocationType.tryEmit(Integer.valueOf(i));
        if (z && !this.mIsAssistAppAvailable) {
            showAssistanceAppSettingAlertDialog();
            this.mIsAssistAppAvailable = true;
        }
        if (z2) {
            SystemUIAnalytics.sendEventLog(SystemUIAnalytics.getCurrentScreenID(), SystemUIAnalytics.EID_INVOKE_DIGITAL_ASSISTANT);
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
                assistIntent.addFlags(QuickStepContract.SYSUI_STATE_NAV_BAR_VIS_GONE);
                AsyncTask.execute(new Runnable() { // from class: com.android.systemui.assist.AssistManager.4
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
