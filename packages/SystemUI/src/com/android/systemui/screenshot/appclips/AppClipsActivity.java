package com.android.systemui.screenshot.appclips;

import android.app.TaskInfo;
import android.app.assist.AssistContent;
import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.HardwareRenderer;
import android.graphics.RecordingCanvas;
import android.graphics.Rect;
import android.graphics.RenderNode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.os.UserHandle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListPopupWindow;
import android.widget.TextView;
import androidx.activity.ComponentActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.concurrent.futures.CallbackToFutureAdapter;
import androidx.core.view.ViewCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.android.internal.infra.ServiceConnector;
import com.android.internal.logging.UiEventLogger;
import com.android.settingslib.Utils;
import com.android.systemui.R;
import com.android.systemui.log.DebugLogger;
import com.android.systemui.screenshot.AssistContentRequester;
import com.android.systemui.screenshot.AssistContentRequester$$ExternalSyntheticLambda0;
import com.android.systemui.screenshot.appclips.AppClipsViewModel;
import com.android.systemui.screenshot.appclips.InternalBacklinksData;
import com.android.systemui.screenshot.scroll.CropView;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.google.common.base.Function;
import com.google.common.util.concurrent.AbstractFuture;
import com.google.common.util.concurrent.AbstractTransformFuture;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.common.util.concurrent.SettableFuture;
import com.google.common.util.concurrent.TimeoutFuture;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.WeakHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;
import java.util.stream.Stream;
import kotlin.jvm.internal.Reflection;

/* loaded from: classes2.dex */
public class AppClipsActivity extends ComponentActivity {
    public static final PackageManager.ApplicationInfoFlags APPLICATION_INFO_FLAGS = PackageManager.ApplicationInfoFlags.of(0);
    public TextView mBacklinksCrossProfileError;
    public TextView mBacklinksDataTextView;
    public CheckBox mBacklinksIncludeDataCheckBox;
    public String mCallingPackageName;
    public int mCallingPackageUid;
    public Button mCancel;
    public CropView mCropView;
    public View mLayout;
    public final PackageManager mPackageManager;
    public ImageView mPreview;
    public ResultReceiver mResultReceiver;
    public View mRoot;
    public Button mSave;
    public final UiEventLogger mUiEventLogger;
    public final UserTracker mUserTracker;
    public AppClipsViewModel mViewModel;
    public final AppClipsViewModel.Factory mViewModelFactory;
    public final AnonymousClass1 mBroadcastReceiver = new BroadcastReceiver() { // from class: com.android.systemui.screenshot.appclips.AppClipsActivity.1
        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            if (!AppClipsTrampolineActivity.ACTION_FINISH_FROM_TRAMPOLINE.equals(intent.getAction()) || AppClipsActivity.this.isFinishing()) {
                return;
            }
            AppClipsActivity appClipsActivity = AppClipsActivity.this;
            appClipsActivity.mResultReceiver = null;
            appClipsActivity.finish();
        }
    };
    public final IntentFilter mIntentFilter = new IntentFilter(AppClipsTrampolineActivity.ACTION_FINISH_FROM_TRAMPOLINE);

    public static void $r8$lambda$stcjMVf1JwQ0vVO3T941Qunv0VY(AppClipsActivity appClipsActivity, View view) {
        appClipsActivity.mSave.setEnabled(false);
        appClipsActivity.mCancel.setEnabled(false);
        if (view.getId() != R.id.save) {
            appClipsActivity.setError(2);
            appClipsActivity.finish();
            return;
        }
        final Drawable drawable = appClipsActivity.mPreview.getDrawable();
        if (drawable == null) {
            appClipsActivity.setError(1);
            appClipsActivity.finish();
            return;
        }
        final Rect cropBoundaries = appClipsActivity.mCropView.getCropBoundaries(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        if (cropBoundaries.isEmpty()) {
            appClipsActivity.setError(1);
            appClipsActivity.finish();
        } else {
            appClipsActivity.updateImageDimensions();
            final AppClipsViewModel appClipsViewModel = appClipsActivity.mViewModel;
            final UserHandle user = appClipsActivity.getUser();
            appClipsViewModel.mBgExecutor.execute(new Runnable() { // from class: com.android.systemui.screenshot.appclips.AppClipsViewModel$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    AppClipsViewModel appClipsViewModel2 = appClipsViewModel;
                    Drawable drawable2 = drawable;
                    Rect rect = cropBoundaries;
                    UserHandle userHandle = user;
                    appClipsViewModel2.getClass();
                    RenderNode renderNode = new RenderNode("Screenshot save");
                    renderNode.setPosition(0, 0, rect.width(), rect.height());
                    RecordingCanvas recordingCanvasBeginRecording = renderNode.beginRecording();
                    recordingCanvasBeginRecording.translate(-rect.left, -rect.top);
                    recordingCanvasBeginRecording.clipRect(rect);
                    drawable2.draw(recordingCanvasBeginRecording);
                    renderNode.endRecording();
                    CallbackToFutureAdapter.SafeFuture safeFutureExport = appClipsViewModel2.mImageExporter.export(appClipsViewModel2.mBgExecutor, UUID.randomUUID(), HardwareRenderer.createHardwareBitmap(renderNode, rect.width(), rect.height()), userHandle, 0);
                    safeFutureExport.delegate.addListener(new AppClipsViewModel$$ExternalSyntheticLambda2(appClipsViewModel2, safeFutureExport, 0), appClipsViewModel2.mMainExecutor);
                }
            });
        }
    }

    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.screenshot.appclips.AppClipsActivity$1] */
    public AppClipsActivity(AppClipsViewModel.Factory factory, PackageManager packageManager, UserTracker userTracker, UiEventLogger uiEventLogger) {
        this.mViewModelFactory = factory;
        this.mPackageManager = packageManager;
        this.mUserTracker = userTracker;
        this.mUiEventLogger = uiEventLogger;
    }

    @Override // android.app.Activity
    public final void finish() {
        super.finish();
        overridePendingTransition(0, 0);
    }

    @Override // androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        final int i = 1;
        final int i2 = 0;
        overridePendingTransition(0, 0);
        super.onCreate(bundle);
        registerReceiver(this.mBroadcastReceiver, this.mIntentFilter, "com.android.systemui.permission.SELF", null, 4);
        Intent intent = getIntent();
        String stringExtra = intent.getStringExtra(AppClipsTrampolineActivity.EXTRA_CALLING_PACKAGE_NAME);
        this.mCallingPackageName = stringExtra;
        this.mCallingPackageUid = 0;
        try {
            this.mCallingPackageUid = this.mPackageManager.getApplicationInfoAsUser(stringExtra, APPLICATION_INFO_FLAGS, ((UserTrackerImpl) this.mUserTracker).getUserId()).uid;
        } catch (PackageManager.NameNotFoundException e) {
            Log.d("AppClipsActivity", "Couldn't find notes app UID " + e);
        }
        ResultReceiver resultReceiver = (ResultReceiver) intent.getParcelableExtra(AppClipsTrampolineActivity.EXTRA_RESULT_RECEIVER, ResultReceiver.class);
        this.mResultReceiver = resultReceiver;
        if (resultReceiver == null) {
            setError(1);
            finish();
            return;
        }
        View viewInflate = getLayoutInflater().inflate(R.layout.app_clips_screenshot, (ViewGroup) null);
        this.mLayout = viewInflate;
        View viewFindViewById = viewInflate.findViewById(R.id.root);
        this.mRoot = viewFindViewById;
        AppClipsActivity$$ExternalSyntheticLambda0 appClipsActivity$$ExternalSyntheticLambda0 = new AppClipsActivity$$ExternalSyntheticLambda0();
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        ViewCompat.Api21Impl.setOnApplyWindowInsetsListener(viewFindViewById, appClipsActivity$$ExternalSyntheticLambda0);
        this.mSave = (Button) this.mLayout.findViewById(R.id.save);
        this.mCancel = (Button) this.mLayout.findViewById(R.id.cancel);
        this.mSave.setOnClickListener(new AppClipsActivity$$ExternalSyntheticLambda1(this, i2));
        this.mCancel.setOnClickListener(new AppClipsActivity$$ExternalSyntheticLambda1(this, i2));
        this.mCropView = (CropView) this.mLayout.findViewById(R.id.crop_view);
        ImageView imageView = (ImageView) this.mLayout.findViewById(R.id.preview);
        this.mPreview = imageView;
        imageView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() { // from class: com.android.systemui.screenshot.appclips.AppClipsActivity$$ExternalSyntheticLambda2
            @Override // android.view.View.OnLayoutChangeListener
            public final void onLayoutChange(View view, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10) {
                AppClipsActivity appClipsActivity = this.f$0;
                PackageManager.ApplicationInfoFlags applicationInfoFlags = AppClipsActivity.APPLICATION_INFO_FLAGS;
                appClipsActivity.updateImageDimensions();
            }
        });
        this.mBacklinksDataTextView = (TextView) this.mLayout.findViewById(R.id.backlinks_data);
        CheckBox checkBox = (CheckBox) this.mLayout.findViewById(R.id.backlinks_include_data);
        this.mBacklinksIncludeDataCheckBox = checkBox;
        checkBox.setOnCheckedChangeListener(new AppClipsActivity$$ExternalSyntheticLambda3(this));
        this.mBacklinksCrossProfileError = (TextView) this.mLayout.findViewById(R.id.backlinks_cross_profile_error);
        AppClipsViewModel appClipsViewModel = (AppClipsViewModel) new ViewModelProvider(this, this.mViewModelFactory).get(AppClipsViewModel.class);
        this.mViewModel = appClipsViewModel;
        appClipsViewModel.mScreenshotLiveData.observe(this, new Observer(this) { // from class: com.android.systemui.screenshot.appclips.AppClipsActivity$$ExternalSyntheticLambda5
            public final /* synthetic */ AppClipsActivity f$0;

            {
                this.f$0 = this;
            }

            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) throws Resources.NotFoundException {
                final AppClipsActivity appClipsActivity = this.f$0;
                switch (i) {
                    case 0:
                        Uri uri = (Uri) obj;
                        if (appClipsActivity.mResultReceiver != null) {
                            appClipsActivity.grantUriPermission(appClipsActivity.mCallingPackageName, uri, 1);
                            Bundle bundle2 = new Bundle();
                            bundle2.putInt("android.intent.extra.CAPTURE_CONTENT_FOR_NOTE_STATUS_CODE", 0);
                            bundle2.putParcelable(AppClipsTrampolineActivity.EXTRA_SCREENSHOT_URI, uri);
                            InternalBacklinksData internalBacklinksData = (InternalBacklinksData) appClipsActivity.mViewModel.mSelectedBacklinksLiveData.getValue();
                            if (appClipsActivity.mBacklinksIncludeDataCheckBox.getVisibility() == 0 && appClipsActivity.mBacklinksIncludeDataCheckBox.isChecked() && (internalBacklinksData instanceof InternalBacklinksData.BacklinksData)) {
                                bundle2.putParcelable(AppClipsTrampolineActivity.EXTRA_CLIP_DATA, ((InternalBacklinksData.BacklinksData) internalBacklinksData).clipData);
                                DebugLogger.INSTANCE.getClass();
                                boolean z = Build.IS_DEBUGGABLE;
                                Reflection.getOrCreateKotlinClass(appClipsActivity.getClass()).getSimpleName();
                            }
                            try {
                                appClipsActivity.mResultReceiver.send(-1, bundle2);
                                appClipsActivity.mUiEventLogger.log(AppClipsEvent.SCREENSHOT_FOR_NOTE_ACCEPTED, appClipsActivity.mCallingPackageUid, appClipsActivity.mCallingPackageName);
                            } catch (Exception e2) {
                                Log.e("AppClipsActivity", "Error while sending data to trampoline activity", e2);
                            }
                            appClipsActivity.mResultReceiver = null;
                            appClipsActivity.finish();
                            break;
                        }
                        break;
                    case 1:
                        PackageManager.ApplicationInfoFlags applicationInfoFlags = AppClipsActivity.APPLICATION_INFO_FLAGS;
                        appClipsActivity.getClass();
                        appClipsActivity.mRoot.setBackgroundColor(Utils.getColorAttr(android.R.attr.colorBackgroundFloating, appClipsActivity).getDefaultColor());
                        appClipsActivity.mPreview.setImageDrawable(new BitmapDrawable(appClipsActivity.getResources(), (Bitmap) obj));
                        appClipsActivity.mPreview.setAlpha(1.0f);
                        appClipsActivity.setContentView(appClipsActivity.mLayout);
                        appClipsActivity.mRoot.requestApplyInsets();
                        break;
                    case 2:
                        int iIntValue = ((Integer) obj).intValue();
                        PackageManager.ApplicationInfoFlags applicationInfoFlags2 = AppClipsActivity.APPLICATION_INFO_FLAGS;
                        appClipsActivity.setError(iIntValue);
                        appClipsActivity.finish();
                        break;
                    case 3:
                        final List<InternalBacklinksData> list = (List) obj;
                        appClipsActivity.mBacklinksIncludeDataCheckBox.setVisibility(0);
                        appClipsActivity.mBacklinksDataTextView.setVisibility(appClipsActivity.mBacklinksIncludeDataCheckBox.isChecked() ? 0 : 8);
                        if (list.size() > 1) {
                            HashMap map = new HashMap();
                            for (InternalBacklinksData internalBacklinksData2 : list) {
                                boolean zContainsKey = map.containsKey(internalBacklinksData2.backlinkDisplayInfo.displayLabel);
                                BacklinkDisplayInfo backlinkDisplayInfo = internalBacklinksData2.backlinkDisplayInfo;
                                if (zContainsKey) {
                                    int iIntValue2 = ((Integer) map.get(backlinkDisplayInfo.displayLabel)).intValue();
                                    if (iIntValue2 == 0) {
                                        map.put(backlinkDisplayInfo.displayLabel, 2);
                                    } else {
                                        map.put(backlinkDisplayInfo.displayLabel, Integer.valueOf(iIntValue2 + 1));
                                    }
                                } else {
                                    map.put(backlinkDisplayInfo.displayLabel, 0);
                                }
                            }
                            for (InternalBacklinksData internalBacklinksData3 : list.reversed()) {
                                String str = internalBacklinksData3.backlinkDisplayInfo.displayLabel;
                                Integer num = (Integer) map.get(str);
                                int iIntValue3 = num.intValue();
                                if (iIntValue3 > 0) {
                                    internalBacklinksData3.backlinkDisplayInfo.displayLabel = appClipsActivity.getString(R.string.backlinks_duplicate_label_format, new Object[]{str, num});
                                    map.put(str, Integer.valueOf(iIntValue3 - 1));
                                }
                            }
                            TextView textView = appClipsActivity.mBacklinksDataTextView;
                            final ListPopupWindow listPopupWindow = new ListPopupWindow(appClipsActivity);
                            listPopupWindow.setAnchorView(textView);
                            listPopupWindow.setOverlapAnchor(true);
                            listPopupWindow.setBackgroundDrawable(AppCompatResources.getDrawable(R.drawable.backlinks_rounded_rectangle, appClipsActivity));
                            listPopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.android.systemui.screenshot.appclips.AppClipsActivity$$ExternalSyntheticLambda9
                                @Override // android.widget.AdapterView.OnItemClickListener
                                public final void onItemClick(AdapterView adapterView, View view, int i3, long j) {
                                    AppClipsActivity appClipsActivity2 = appClipsActivity;
                                    List list2 = list;
                                    ListPopupWindow listPopupWindow2 = listPopupWindow;
                                    appClipsActivity2.mViewModel.mSelectedBacklinksLiveData.setValue((InternalBacklinksData) list2.get(i3));
                                    listPopupWindow2.dismiss();
                                }
                            });
                            ArrayAdapter arrayAdapter = new ArrayAdapter(appClipsActivity, R.layout.app_clips_backlinks_drop_down_entry) { // from class: com.android.systemui.screenshot.appclips.AppClipsActivity.2
                                @Override // android.widget.ArrayAdapter, android.widget.Adapter
                                public final View getView(int i3, View view, ViewGroup viewGroup) throws Resources.NotFoundException {
                                    TextView textView2 = (TextView) super.getView(i3, view, viewGroup);
                                    InternalBacklinksData internalBacklinksData4 = (InternalBacklinksData) list.get(i3);
                                    textView2.setText(internalBacklinksData4.backlinkDisplayInfo.displayLabel);
                                    Drawable drawable = internalBacklinksData4.backlinkDisplayInfo.appIcon;
                                    AppClipsActivity appClipsActivity2 = AppClipsActivity.this;
                                    PackageManager.ApplicationInfoFlags applicationInfoFlags3 = AppClipsActivity.APPLICATION_INFO_FLAGS;
                                    int dimensionPixelSize = appClipsActivity2.getResources().getDimensionPixelSize(R.dimen.appclips_backlinks_icon_size);
                                    Rect rect = new Rect();
                                    rect.set(0, 0, dimensionPixelSize, dimensionPixelSize);
                                    drawable.setBounds(rect);
                                    textView2.setCompoundDrawablesRelative(drawable, null, null, null);
                                    return textView2;
                                }
                            };
                            arrayAdapter.addAll(list);
                            listPopupWindow.setAdapter(arrayAdapter);
                            appClipsActivity.mBacklinksDataTextView.setOnClickListener(new AppClipsActivity$$ExternalSyntheticLambda1(listPopupWindow, 1));
                            break;
                        }
                        break;
                    default:
                        InternalBacklinksData internalBacklinksData4 = (InternalBacklinksData) obj;
                        appClipsActivity.mBacklinksDataTextView.setText(internalBacklinksData4.backlinkDisplayInfo.displayLabel);
                        Drawable drawable = internalBacklinksData4.backlinkDisplayInfo.appIcon;
                        int dimensionPixelSize = appClipsActivity.getResources().getDimensionPixelSize(R.dimen.appclips_backlinks_icon_size);
                        Rect rect = new Rect();
                        rect.set(0, 0, dimensionPixelSize, dimensionPixelSize);
                        drawable.setBounds(rect);
                        Drawable drawable2 = appClipsActivity.mBacklinksDataTextView.getCompoundDrawablesRelative()[2];
                        if (((List) appClipsActivity.mViewModel.mBacklinksLiveData.getValue()).size() > 1 && drawable2 == null) {
                            drawable2 = AppCompatResources.getDrawable(R.drawable.arrow_pointing_down, appClipsActivity);
                            drawable2.setBounds(rect);
                            drawable2.setTint(Utils.getColorAttr(android.R.attr.textColorSecondary, appClipsActivity).getDefaultColor());
                        }
                        appClipsActivity.mBacklinksDataTextView.setCompoundDrawablesRelative(drawable, null, drawable2, null);
                        appClipsActivity.mBacklinksIncludeDataCheckBox.setOnCheckedChangeListener(null);
                        if (internalBacklinksData4 instanceof InternalBacklinksData.CrossProfileError) {
                            appClipsActivity.mBacklinksIncludeDataCheckBox.setEnabled(false);
                            appClipsActivity.mBacklinksIncludeDataCheckBox.setChecked(false);
                            appClipsActivity.mBacklinksIncludeDataCheckBox.setAlpha(0.5f);
                            appClipsActivity.mBacklinksCrossProfileError.setVisibility(0);
                        } else {
                            appClipsActivity.mBacklinksIncludeDataCheckBox.setEnabled(true);
                            appClipsActivity.mBacklinksIncludeDataCheckBox.setChecked(true);
                            appClipsActivity.mBacklinksIncludeDataCheckBox.setAlpha(1.0f);
                            appClipsActivity.mBacklinksCrossProfileError.setVisibility(8);
                        }
                        appClipsActivity.mBacklinksIncludeDataCheckBox.setOnCheckedChangeListener(new AppClipsActivity$$ExternalSyntheticLambda3(appClipsActivity));
                        break;
                }
            }
        });
        this.mViewModel.mResultLiveData.observe(this, new Observer(this) { // from class: com.android.systemui.screenshot.appclips.AppClipsActivity$$ExternalSyntheticLambda5
            public final /* synthetic */ AppClipsActivity f$0;

            {
                this.f$0 = this;
            }

            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) throws Resources.NotFoundException {
                final Context appClipsActivity = this.f$0;
                switch (i2) {
                    case 0:
                        Uri uri = (Uri) obj;
                        if (appClipsActivity.mResultReceiver != null) {
                            appClipsActivity.grantUriPermission(appClipsActivity.mCallingPackageName, uri, 1);
                            Bundle bundle2 = new Bundle();
                            bundle2.putInt("android.intent.extra.CAPTURE_CONTENT_FOR_NOTE_STATUS_CODE", 0);
                            bundle2.putParcelable(AppClipsTrampolineActivity.EXTRA_SCREENSHOT_URI, uri);
                            InternalBacklinksData internalBacklinksData = (InternalBacklinksData) appClipsActivity.mViewModel.mSelectedBacklinksLiveData.getValue();
                            if (appClipsActivity.mBacklinksIncludeDataCheckBox.getVisibility() == 0 && appClipsActivity.mBacklinksIncludeDataCheckBox.isChecked() && (internalBacklinksData instanceof InternalBacklinksData.BacklinksData)) {
                                bundle2.putParcelable(AppClipsTrampolineActivity.EXTRA_CLIP_DATA, ((InternalBacklinksData.BacklinksData) internalBacklinksData).clipData);
                                DebugLogger.INSTANCE.getClass();
                                boolean z = Build.IS_DEBUGGABLE;
                                Reflection.getOrCreateKotlinClass(appClipsActivity.getClass()).getSimpleName();
                            }
                            try {
                                appClipsActivity.mResultReceiver.send(-1, bundle2);
                                appClipsActivity.mUiEventLogger.log(AppClipsEvent.SCREENSHOT_FOR_NOTE_ACCEPTED, appClipsActivity.mCallingPackageUid, appClipsActivity.mCallingPackageName);
                            } catch (Exception e2) {
                                Log.e("AppClipsActivity", "Error while sending data to trampoline activity", e2);
                            }
                            appClipsActivity.mResultReceiver = null;
                            appClipsActivity.finish();
                            break;
                        }
                        break;
                    case 1:
                        PackageManager.ApplicationInfoFlags applicationInfoFlags = AppClipsActivity.APPLICATION_INFO_FLAGS;
                        appClipsActivity.getClass();
                        appClipsActivity.mRoot.setBackgroundColor(Utils.getColorAttr(android.R.attr.colorBackgroundFloating, appClipsActivity).getDefaultColor());
                        appClipsActivity.mPreview.setImageDrawable(new BitmapDrawable(appClipsActivity.getResources(), (Bitmap) obj));
                        appClipsActivity.mPreview.setAlpha(1.0f);
                        appClipsActivity.setContentView(appClipsActivity.mLayout);
                        appClipsActivity.mRoot.requestApplyInsets();
                        break;
                    case 2:
                        int iIntValue = ((Integer) obj).intValue();
                        PackageManager.ApplicationInfoFlags applicationInfoFlags2 = AppClipsActivity.APPLICATION_INFO_FLAGS;
                        appClipsActivity.setError(iIntValue);
                        appClipsActivity.finish();
                        break;
                    case 3:
                        final List list = (List) obj;
                        appClipsActivity.mBacklinksIncludeDataCheckBox.setVisibility(0);
                        appClipsActivity.mBacklinksDataTextView.setVisibility(appClipsActivity.mBacklinksIncludeDataCheckBox.isChecked() ? 0 : 8);
                        if (list.size() > 1) {
                            HashMap map = new HashMap();
                            for (InternalBacklinksData internalBacklinksData2 : list) {
                                boolean zContainsKey = map.containsKey(internalBacklinksData2.backlinkDisplayInfo.displayLabel);
                                BacklinkDisplayInfo backlinkDisplayInfo = internalBacklinksData2.backlinkDisplayInfo;
                                if (zContainsKey) {
                                    int iIntValue2 = ((Integer) map.get(backlinkDisplayInfo.displayLabel)).intValue();
                                    if (iIntValue2 == 0) {
                                        map.put(backlinkDisplayInfo.displayLabel, 2);
                                    } else {
                                        map.put(backlinkDisplayInfo.displayLabel, Integer.valueOf(iIntValue2 + 1));
                                    }
                                } else {
                                    map.put(backlinkDisplayInfo.displayLabel, 0);
                                }
                            }
                            for (InternalBacklinksData internalBacklinksData3 : list.reversed()) {
                                String str = internalBacklinksData3.backlinkDisplayInfo.displayLabel;
                                Integer num = (Integer) map.get(str);
                                int iIntValue3 = num.intValue();
                                if (iIntValue3 > 0) {
                                    internalBacklinksData3.backlinkDisplayInfo.displayLabel = appClipsActivity.getString(R.string.backlinks_duplicate_label_format, new Object[]{str, num});
                                    map.put(str, Integer.valueOf(iIntValue3 - 1));
                                }
                            }
                            TextView textView = appClipsActivity.mBacklinksDataTextView;
                            final ListPopupWindow listPopupWindow = new ListPopupWindow(appClipsActivity);
                            listPopupWindow.setAnchorView(textView);
                            listPopupWindow.setOverlapAnchor(true);
                            listPopupWindow.setBackgroundDrawable(AppCompatResources.getDrawable(R.drawable.backlinks_rounded_rectangle, appClipsActivity));
                            listPopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.android.systemui.screenshot.appclips.AppClipsActivity$$ExternalSyntheticLambda9
                                @Override // android.widget.AdapterView.OnItemClickListener
                                public final void onItemClick(AdapterView adapterView, View view, int i3, long j) {
                                    AppClipsActivity appClipsActivity2 = appClipsActivity;
                                    List list2 = list;
                                    ListPopupWindow listPopupWindow2 = listPopupWindow;
                                    appClipsActivity2.mViewModel.mSelectedBacklinksLiveData.setValue((InternalBacklinksData) list2.get(i3));
                                    listPopupWindow2.dismiss();
                                }
                            });
                            ArrayAdapter arrayAdapter = new ArrayAdapter(appClipsActivity, R.layout.app_clips_backlinks_drop_down_entry) { // from class: com.android.systemui.screenshot.appclips.AppClipsActivity.2
                                @Override // android.widget.ArrayAdapter, android.widget.Adapter
                                public final View getView(int i3, View view, ViewGroup viewGroup) throws Resources.NotFoundException {
                                    TextView textView2 = (TextView) super.getView(i3, view, viewGroup);
                                    InternalBacklinksData internalBacklinksData4 = (InternalBacklinksData) list.get(i3);
                                    textView2.setText(internalBacklinksData4.backlinkDisplayInfo.displayLabel);
                                    Drawable drawable = internalBacklinksData4.backlinkDisplayInfo.appIcon;
                                    AppClipsActivity appClipsActivity2 = AppClipsActivity.this;
                                    PackageManager.ApplicationInfoFlags applicationInfoFlags3 = AppClipsActivity.APPLICATION_INFO_FLAGS;
                                    int dimensionPixelSize = appClipsActivity2.getResources().getDimensionPixelSize(R.dimen.appclips_backlinks_icon_size);
                                    Rect rect = new Rect();
                                    rect.set(0, 0, dimensionPixelSize, dimensionPixelSize);
                                    drawable.setBounds(rect);
                                    textView2.setCompoundDrawablesRelative(drawable, null, null, null);
                                    return textView2;
                                }
                            };
                            arrayAdapter.addAll(list);
                            listPopupWindow.setAdapter(arrayAdapter);
                            appClipsActivity.mBacklinksDataTextView.setOnClickListener(new AppClipsActivity$$ExternalSyntheticLambda1(listPopupWindow, 1));
                            break;
                        }
                        break;
                    default:
                        InternalBacklinksData internalBacklinksData4 = (InternalBacklinksData) obj;
                        appClipsActivity.mBacklinksDataTextView.setText(internalBacklinksData4.backlinkDisplayInfo.displayLabel);
                        Drawable drawable = internalBacklinksData4.backlinkDisplayInfo.appIcon;
                        int dimensionPixelSize = appClipsActivity.getResources().getDimensionPixelSize(R.dimen.appclips_backlinks_icon_size);
                        Rect rect = new Rect();
                        rect.set(0, 0, dimensionPixelSize, dimensionPixelSize);
                        drawable.setBounds(rect);
                        Drawable drawable2 = appClipsActivity.mBacklinksDataTextView.getCompoundDrawablesRelative()[2];
                        if (((List) appClipsActivity.mViewModel.mBacklinksLiveData.getValue()).size() > 1 && drawable2 == null) {
                            drawable2 = AppCompatResources.getDrawable(R.drawable.arrow_pointing_down, appClipsActivity);
                            drawable2.setBounds(rect);
                            drawable2.setTint(Utils.getColorAttr(android.R.attr.textColorSecondary, appClipsActivity).getDefaultColor());
                        }
                        appClipsActivity.mBacklinksDataTextView.setCompoundDrawablesRelative(drawable, null, drawable2, null);
                        appClipsActivity.mBacklinksIncludeDataCheckBox.setOnCheckedChangeListener(null);
                        if (internalBacklinksData4 instanceof InternalBacklinksData.CrossProfileError) {
                            appClipsActivity.mBacklinksIncludeDataCheckBox.setEnabled(false);
                            appClipsActivity.mBacklinksIncludeDataCheckBox.setChecked(false);
                            appClipsActivity.mBacklinksIncludeDataCheckBox.setAlpha(0.5f);
                            appClipsActivity.mBacklinksCrossProfileError.setVisibility(0);
                        } else {
                            appClipsActivity.mBacklinksIncludeDataCheckBox.setEnabled(true);
                            appClipsActivity.mBacklinksIncludeDataCheckBox.setChecked(true);
                            appClipsActivity.mBacklinksIncludeDataCheckBox.setAlpha(1.0f);
                            appClipsActivity.mBacklinksCrossProfileError.setVisibility(8);
                        }
                        appClipsActivity.mBacklinksIncludeDataCheckBox.setOnCheckedChangeListener(new AppClipsActivity$$ExternalSyntheticLambda3(appClipsActivity));
                        break;
                }
            }
        });
        final int i3 = 2;
        this.mViewModel.mErrorLiveData.observe(this, new Observer(this) { // from class: com.android.systemui.screenshot.appclips.AppClipsActivity$$ExternalSyntheticLambda5
            public final /* synthetic */ AppClipsActivity f$0;

            {
                this.f$0 = this;
            }

            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) throws Resources.NotFoundException {
                final Context appClipsActivity = this.f$0;
                switch (i3) {
                    case 0:
                        Uri uri = (Uri) obj;
                        if (appClipsActivity.mResultReceiver != null) {
                            appClipsActivity.grantUriPermission(appClipsActivity.mCallingPackageName, uri, 1);
                            Bundle bundle2 = new Bundle();
                            bundle2.putInt("android.intent.extra.CAPTURE_CONTENT_FOR_NOTE_STATUS_CODE", 0);
                            bundle2.putParcelable(AppClipsTrampolineActivity.EXTRA_SCREENSHOT_URI, uri);
                            InternalBacklinksData internalBacklinksData = (InternalBacklinksData) appClipsActivity.mViewModel.mSelectedBacklinksLiveData.getValue();
                            if (appClipsActivity.mBacklinksIncludeDataCheckBox.getVisibility() == 0 && appClipsActivity.mBacklinksIncludeDataCheckBox.isChecked() && (internalBacklinksData instanceof InternalBacklinksData.BacklinksData)) {
                                bundle2.putParcelable(AppClipsTrampolineActivity.EXTRA_CLIP_DATA, ((InternalBacklinksData.BacklinksData) internalBacklinksData).clipData);
                                DebugLogger.INSTANCE.getClass();
                                boolean z = Build.IS_DEBUGGABLE;
                                Reflection.getOrCreateKotlinClass(appClipsActivity.getClass()).getSimpleName();
                            }
                            try {
                                appClipsActivity.mResultReceiver.send(-1, bundle2);
                                appClipsActivity.mUiEventLogger.log(AppClipsEvent.SCREENSHOT_FOR_NOTE_ACCEPTED, appClipsActivity.mCallingPackageUid, appClipsActivity.mCallingPackageName);
                            } catch (Exception e2) {
                                Log.e("AppClipsActivity", "Error while sending data to trampoline activity", e2);
                            }
                            appClipsActivity.mResultReceiver = null;
                            appClipsActivity.finish();
                            break;
                        }
                        break;
                    case 1:
                        PackageManager.ApplicationInfoFlags applicationInfoFlags = AppClipsActivity.APPLICATION_INFO_FLAGS;
                        appClipsActivity.getClass();
                        appClipsActivity.mRoot.setBackgroundColor(Utils.getColorAttr(android.R.attr.colorBackgroundFloating, appClipsActivity).getDefaultColor());
                        appClipsActivity.mPreview.setImageDrawable(new BitmapDrawable(appClipsActivity.getResources(), (Bitmap) obj));
                        appClipsActivity.mPreview.setAlpha(1.0f);
                        appClipsActivity.setContentView(appClipsActivity.mLayout);
                        appClipsActivity.mRoot.requestApplyInsets();
                        break;
                    case 2:
                        int iIntValue = ((Integer) obj).intValue();
                        PackageManager.ApplicationInfoFlags applicationInfoFlags2 = AppClipsActivity.APPLICATION_INFO_FLAGS;
                        appClipsActivity.setError(iIntValue);
                        appClipsActivity.finish();
                        break;
                    case 3:
                        final List list = (List) obj;
                        appClipsActivity.mBacklinksIncludeDataCheckBox.setVisibility(0);
                        appClipsActivity.mBacklinksDataTextView.setVisibility(appClipsActivity.mBacklinksIncludeDataCheckBox.isChecked() ? 0 : 8);
                        if (list.size() > 1) {
                            HashMap map = new HashMap();
                            for (InternalBacklinksData internalBacklinksData2 : list) {
                                boolean zContainsKey = map.containsKey(internalBacklinksData2.backlinkDisplayInfo.displayLabel);
                                BacklinkDisplayInfo backlinkDisplayInfo = internalBacklinksData2.backlinkDisplayInfo;
                                if (zContainsKey) {
                                    int iIntValue2 = ((Integer) map.get(backlinkDisplayInfo.displayLabel)).intValue();
                                    if (iIntValue2 == 0) {
                                        map.put(backlinkDisplayInfo.displayLabel, 2);
                                    } else {
                                        map.put(backlinkDisplayInfo.displayLabel, Integer.valueOf(iIntValue2 + 1));
                                    }
                                } else {
                                    map.put(backlinkDisplayInfo.displayLabel, 0);
                                }
                            }
                            for (InternalBacklinksData internalBacklinksData3 : list.reversed()) {
                                String str = internalBacklinksData3.backlinkDisplayInfo.displayLabel;
                                Integer num = (Integer) map.get(str);
                                int iIntValue3 = num.intValue();
                                if (iIntValue3 > 0) {
                                    internalBacklinksData3.backlinkDisplayInfo.displayLabel = appClipsActivity.getString(R.string.backlinks_duplicate_label_format, new Object[]{str, num});
                                    map.put(str, Integer.valueOf(iIntValue3 - 1));
                                }
                            }
                            TextView textView = appClipsActivity.mBacklinksDataTextView;
                            final ListPopupWindow listPopupWindow = new ListPopupWindow(appClipsActivity);
                            listPopupWindow.setAnchorView(textView);
                            listPopupWindow.setOverlapAnchor(true);
                            listPopupWindow.setBackgroundDrawable(AppCompatResources.getDrawable(R.drawable.backlinks_rounded_rectangle, appClipsActivity));
                            listPopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.android.systemui.screenshot.appclips.AppClipsActivity$$ExternalSyntheticLambda9
                                @Override // android.widget.AdapterView.OnItemClickListener
                                public final void onItemClick(AdapterView adapterView, View view, int i32, long j) {
                                    AppClipsActivity appClipsActivity2 = appClipsActivity;
                                    List list2 = list;
                                    ListPopupWindow listPopupWindow2 = listPopupWindow;
                                    appClipsActivity2.mViewModel.mSelectedBacklinksLiveData.setValue((InternalBacklinksData) list2.get(i32));
                                    listPopupWindow2.dismiss();
                                }
                            });
                            ArrayAdapter arrayAdapter = new ArrayAdapter(appClipsActivity, R.layout.app_clips_backlinks_drop_down_entry) { // from class: com.android.systemui.screenshot.appclips.AppClipsActivity.2
                                @Override // android.widget.ArrayAdapter, android.widget.Adapter
                                public final View getView(int i32, View view, ViewGroup viewGroup) throws Resources.NotFoundException {
                                    TextView textView2 = (TextView) super.getView(i32, view, viewGroup);
                                    InternalBacklinksData internalBacklinksData4 = (InternalBacklinksData) list.get(i32);
                                    textView2.setText(internalBacklinksData4.backlinkDisplayInfo.displayLabel);
                                    Drawable drawable = internalBacklinksData4.backlinkDisplayInfo.appIcon;
                                    AppClipsActivity appClipsActivity2 = AppClipsActivity.this;
                                    PackageManager.ApplicationInfoFlags applicationInfoFlags3 = AppClipsActivity.APPLICATION_INFO_FLAGS;
                                    int dimensionPixelSize = appClipsActivity2.getResources().getDimensionPixelSize(R.dimen.appclips_backlinks_icon_size);
                                    Rect rect = new Rect();
                                    rect.set(0, 0, dimensionPixelSize, dimensionPixelSize);
                                    drawable.setBounds(rect);
                                    textView2.setCompoundDrawablesRelative(drawable, null, null, null);
                                    return textView2;
                                }
                            };
                            arrayAdapter.addAll(list);
                            listPopupWindow.setAdapter(arrayAdapter);
                            appClipsActivity.mBacklinksDataTextView.setOnClickListener(new AppClipsActivity$$ExternalSyntheticLambda1(listPopupWindow, 1));
                            break;
                        }
                        break;
                    default:
                        InternalBacklinksData internalBacklinksData4 = (InternalBacklinksData) obj;
                        appClipsActivity.mBacklinksDataTextView.setText(internalBacklinksData4.backlinkDisplayInfo.displayLabel);
                        Drawable drawable = internalBacklinksData4.backlinkDisplayInfo.appIcon;
                        int dimensionPixelSize = appClipsActivity.getResources().getDimensionPixelSize(R.dimen.appclips_backlinks_icon_size);
                        Rect rect = new Rect();
                        rect.set(0, 0, dimensionPixelSize, dimensionPixelSize);
                        drawable.setBounds(rect);
                        Drawable drawable2 = appClipsActivity.mBacklinksDataTextView.getCompoundDrawablesRelative()[2];
                        if (((List) appClipsActivity.mViewModel.mBacklinksLiveData.getValue()).size() > 1 && drawable2 == null) {
                            drawable2 = AppCompatResources.getDrawable(R.drawable.arrow_pointing_down, appClipsActivity);
                            drawable2.setBounds(rect);
                            drawable2.setTint(Utils.getColorAttr(android.R.attr.textColorSecondary, appClipsActivity).getDefaultColor());
                        }
                        appClipsActivity.mBacklinksDataTextView.setCompoundDrawablesRelative(drawable, null, drawable2, null);
                        appClipsActivity.mBacklinksIncludeDataCheckBox.setOnCheckedChangeListener(null);
                        if (internalBacklinksData4 instanceof InternalBacklinksData.CrossProfileError) {
                            appClipsActivity.mBacklinksIncludeDataCheckBox.setEnabled(false);
                            appClipsActivity.mBacklinksIncludeDataCheckBox.setChecked(false);
                            appClipsActivity.mBacklinksIncludeDataCheckBox.setAlpha(0.5f);
                            appClipsActivity.mBacklinksCrossProfileError.setVisibility(0);
                        } else {
                            appClipsActivity.mBacklinksIncludeDataCheckBox.setEnabled(true);
                            appClipsActivity.mBacklinksIncludeDataCheckBox.setChecked(true);
                            appClipsActivity.mBacklinksIncludeDataCheckBox.setAlpha(1.0f);
                            appClipsActivity.mBacklinksCrossProfileError.setVisibility(8);
                        }
                        appClipsActivity.mBacklinksIncludeDataCheckBox.setOnCheckedChangeListener(new AppClipsActivity$$ExternalSyntheticLambda3(appClipsActivity));
                        break;
                }
            }
        });
        final int i4 = 3;
        this.mViewModel.mBacklinksLiveData.observe(this, new Observer(this) { // from class: com.android.systemui.screenshot.appclips.AppClipsActivity$$ExternalSyntheticLambda5
            public final /* synthetic */ AppClipsActivity f$0;

            {
                this.f$0 = this;
            }

            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) throws Resources.NotFoundException {
                final Context appClipsActivity = this.f$0;
                switch (i4) {
                    case 0:
                        Uri uri = (Uri) obj;
                        if (appClipsActivity.mResultReceiver != null) {
                            appClipsActivity.grantUriPermission(appClipsActivity.mCallingPackageName, uri, 1);
                            Bundle bundle2 = new Bundle();
                            bundle2.putInt("android.intent.extra.CAPTURE_CONTENT_FOR_NOTE_STATUS_CODE", 0);
                            bundle2.putParcelable(AppClipsTrampolineActivity.EXTRA_SCREENSHOT_URI, uri);
                            InternalBacklinksData internalBacklinksData = (InternalBacklinksData) appClipsActivity.mViewModel.mSelectedBacklinksLiveData.getValue();
                            if (appClipsActivity.mBacklinksIncludeDataCheckBox.getVisibility() == 0 && appClipsActivity.mBacklinksIncludeDataCheckBox.isChecked() && (internalBacklinksData instanceof InternalBacklinksData.BacklinksData)) {
                                bundle2.putParcelable(AppClipsTrampolineActivity.EXTRA_CLIP_DATA, ((InternalBacklinksData.BacklinksData) internalBacklinksData).clipData);
                                DebugLogger.INSTANCE.getClass();
                                boolean z = Build.IS_DEBUGGABLE;
                                Reflection.getOrCreateKotlinClass(appClipsActivity.getClass()).getSimpleName();
                            }
                            try {
                                appClipsActivity.mResultReceiver.send(-1, bundle2);
                                appClipsActivity.mUiEventLogger.log(AppClipsEvent.SCREENSHOT_FOR_NOTE_ACCEPTED, appClipsActivity.mCallingPackageUid, appClipsActivity.mCallingPackageName);
                            } catch (Exception e2) {
                                Log.e("AppClipsActivity", "Error while sending data to trampoline activity", e2);
                            }
                            appClipsActivity.mResultReceiver = null;
                            appClipsActivity.finish();
                            break;
                        }
                        break;
                    case 1:
                        PackageManager.ApplicationInfoFlags applicationInfoFlags = AppClipsActivity.APPLICATION_INFO_FLAGS;
                        appClipsActivity.getClass();
                        appClipsActivity.mRoot.setBackgroundColor(Utils.getColorAttr(android.R.attr.colorBackgroundFloating, appClipsActivity).getDefaultColor());
                        appClipsActivity.mPreview.setImageDrawable(new BitmapDrawable(appClipsActivity.getResources(), (Bitmap) obj));
                        appClipsActivity.mPreview.setAlpha(1.0f);
                        appClipsActivity.setContentView(appClipsActivity.mLayout);
                        appClipsActivity.mRoot.requestApplyInsets();
                        break;
                    case 2:
                        int iIntValue = ((Integer) obj).intValue();
                        PackageManager.ApplicationInfoFlags applicationInfoFlags2 = AppClipsActivity.APPLICATION_INFO_FLAGS;
                        appClipsActivity.setError(iIntValue);
                        appClipsActivity.finish();
                        break;
                    case 3:
                        final List list = (List) obj;
                        appClipsActivity.mBacklinksIncludeDataCheckBox.setVisibility(0);
                        appClipsActivity.mBacklinksDataTextView.setVisibility(appClipsActivity.mBacklinksIncludeDataCheckBox.isChecked() ? 0 : 8);
                        if (list.size() > 1) {
                            HashMap map = new HashMap();
                            for (InternalBacklinksData internalBacklinksData2 : list) {
                                boolean zContainsKey = map.containsKey(internalBacklinksData2.backlinkDisplayInfo.displayLabel);
                                BacklinkDisplayInfo backlinkDisplayInfo = internalBacklinksData2.backlinkDisplayInfo;
                                if (zContainsKey) {
                                    int iIntValue2 = ((Integer) map.get(backlinkDisplayInfo.displayLabel)).intValue();
                                    if (iIntValue2 == 0) {
                                        map.put(backlinkDisplayInfo.displayLabel, 2);
                                    } else {
                                        map.put(backlinkDisplayInfo.displayLabel, Integer.valueOf(iIntValue2 + 1));
                                    }
                                } else {
                                    map.put(backlinkDisplayInfo.displayLabel, 0);
                                }
                            }
                            for (InternalBacklinksData internalBacklinksData3 : list.reversed()) {
                                String str = internalBacklinksData3.backlinkDisplayInfo.displayLabel;
                                Integer num = (Integer) map.get(str);
                                int iIntValue3 = num.intValue();
                                if (iIntValue3 > 0) {
                                    internalBacklinksData3.backlinkDisplayInfo.displayLabel = appClipsActivity.getString(R.string.backlinks_duplicate_label_format, new Object[]{str, num});
                                    map.put(str, Integer.valueOf(iIntValue3 - 1));
                                }
                            }
                            TextView textView = appClipsActivity.mBacklinksDataTextView;
                            final ListPopupWindow listPopupWindow = new ListPopupWindow(appClipsActivity);
                            listPopupWindow.setAnchorView(textView);
                            listPopupWindow.setOverlapAnchor(true);
                            listPopupWindow.setBackgroundDrawable(AppCompatResources.getDrawable(R.drawable.backlinks_rounded_rectangle, appClipsActivity));
                            listPopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.android.systemui.screenshot.appclips.AppClipsActivity$$ExternalSyntheticLambda9
                                @Override // android.widget.AdapterView.OnItemClickListener
                                public final void onItemClick(AdapterView adapterView, View view, int i32, long j) {
                                    AppClipsActivity appClipsActivity2 = appClipsActivity;
                                    List list2 = list;
                                    ListPopupWindow listPopupWindow2 = listPopupWindow;
                                    appClipsActivity2.mViewModel.mSelectedBacklinksLiveData.setValue((InternalBacklinksData) list2.get(i32));
                                    listPopupWindow2.dismiss();
                                }
                            });
                            ArrayAdapter arrayAdapter = new ArrayAdapter(appClipsActivity, R.layout.app_clips_backlinks_drop_down_entry) { // from class: com.android.systemui.screenshot.appclips.AppClipsActivity.2
                                @Override // android.widget.ArrayAdapter, android.widget.Adapter
                                public final View getView(int i32, View view, ViewGroup viewGroup) throws Resources.NotFoundException {
                                    TextView textView2 = (TextView) super.getView(i32, view, viewGroup);
                                    InternalBacklinksData internalBacklinksData4 = (InternalBacklinksData) list.get(i32);
                                    textView2.setText(internalBacklinksData4.backlinkDisplayInfo.displayLabel);
                                    Drawable drawable = internalBacklinksData4.backlinkDisplayInfo.appIcon;
                                    AppClipsActivity appClipsActivity2 = AppClipsActivity.this;
                                    PackageManager.ApplicationInfoFlags applicationInfoFlags3 = AppClipsActivity.APPLICATION_INFO_FLAGS;
                                    int dimensionPixelSize = appClipsActivity2.getResources().getDimensionPixelSize(R.dimen.appclips_backlinks_icon_size);
                                    Rect rect = new Rect();
                                    rect.set(0, 0, dimensionPixelSize, dimensionPixelSize);
                                    drawable.setBounds(rect);
                                    textView2.setCompoundDrawablesRelative(drawable, null, null, null);
                                    return textView2;
                                }
                            };
                            arrayAdapter.addAll(list);
                            listPopupWindow.setAdapter(arrayAdapter);
                            appClipsActivity.mBacklinksDataTextView.setOnClickListener(new AppClipsActivity$$ExternalSyntheticLambda1(listPopupWindow, 1));
                            break;
                        }
                        break;
                    default:
                        InternalBacklinksData internalBacklinksData4 = (InternalBacklinksData) obj;
                        appClipsActivity.mBacklinksDataTextView.setText(internalBacklinksData4.backlinkDisplayInfo.displayLabel);
                        Drawable drawable = internalBacklinksData4.backlinkDisplayInfo.appIcon;
                        int dimensionPixelSize = appClipsActivity.getResources().getDimensionPixelSize(R.dimen.appclips_backlinks_icon_size);
                        Rect rect = new Rect();
                        rect.set(0, 0, dimensionPixelSize, dimensionPixelSize);
                        drawable.setBounds(rect);
                        Drawable drawable2 = appClipsActivity.mBacklinksDataTextView.getCompoundDrawablesRelative()[2];
                        if (((List) appClipsActivity.mViewModel.mBacklinksLiveData.getValue()).size() > 1 && drawable2 == null) {
                            drawable2 = AppCompatResources.getDrawable(R.drawable.arrow_pointing_down, appClipsActivity);
                            drawable2.setBounds(rect);
                            drawable2.setTint(Utils.getColorAttr(android.R.attr.textColorSecondary, appClipsActivity).getDefaultColor());
                        }
                        appClipsActivity.mBacklinksDataTextView.setCompoundDrawablesRelative(drawable, null, drawable2, null);
                        appClipsActivity.mBacklinksIncludeDataCheckBox.setOnCheckedChangeListener(null);
                        if (internalBacklinksData4 instanceof InternalBacklinksData.CrossProfileError) {
                            appClipsActivity.mBacklinksIncludeDataCheckBox.setEnabled(false);
                            appClipsActivity.mBacklinksIncludeDataCheckBox.setChecked(false);
                            appClipsActivity.mBacklinksIncludeDataCheckBox.setAlpha(0.5f);
                            appClipsActivity.mBacklinksCrossProfileError.setVisibility(0);
                        } else {
                            appClipsActivity.mBacklinksIncludeDataCheckBox.setEnabled(true);
                            appClipsActivity.mBacklinksIncludeDataCheckBox.setChecked(true);
                            appClipsActivity.mBacklinksIncludeDataCheckBox.setAlpha(1.0f);
                            appClipsActivity.mBacklinksCrossProfileError.setVisibility(8);
                        }
                        appClipsActivity.mBacklinksIncludeDataCheckBox.setOnCheckedChangeListener(new AppClipsActivity$$ExternalSyntheticLambda3(appClipsActivity));
                        break;
                }
            }
        });
        final int i5 = 4;
        this.mViewModel.mSelectedBacklinksLiveData.observe(this, new Observer(this) { // from class: com.android.systemui.screenshot.appclips.AppClipsActivity$$ExternalSyntheticLambda5
            public final /* synthetic */ AppClipsActivity f$0;

            {
                this.f$0 = this;
            }

            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) throws Resources.NotFoundException {
                final Context appClipsActivity = this.f$0;
                switch (i5) {
                    case 0:
                        Uri uri = (Uri) obj;
                        if (appClipsActivity.mResultReceiver != null) {
                            appClipsActivity.grantUriPermission(appClipsActivity.mCallingPackageName, uri, 1);
                            Bundle bundle2 = new Bundle();
                            bundle2.putInt("android.intent.extra.CAPTURE_CONTENT_FOR_NOTE_STATUS_CODE", 0);
                            bundle2.putParcelable(AppClipsTrampolineActivity.EXTRA_SCREENSHOT_URI, uri);
                            InternalBacklinksData internalBacklinksData = (InternalBacklinksData) appClipsActivity.mViewModel.mSelectedBacklinksLiveData.getValue();
                            if (appClipsActivity.mBacklinksIncludeDataCheckBox.getVisibility() == 0 && appClipsActivity.mBacklinksIncludeDataCheckBox.isChecked() && (internalBacklinksData instanceof InternalBacklinksData.BacklinksData)) {
                                bundle2.putParcelable(AppClipsTrampolineActivity.EXTRA_CLIP_DATA, ((InternalBacklinksData.BacklinksData) internalBacklinksData).clipData);
                                DebugLogger.INSTANCE.getClass();
                                boolean z = Build.IS_DEBUGGABLE;
                                Reflection.getOrCreateKotlinClass(appClipsActivity.getClass()).getSimpleName();
                            }
                            try {
                                appClipsActivity.mResultReceiver.send(-1, bundle2);
                                appClipsActivity.mUiEventLogger.log(AppClipsEvent.SCREENSHOT_FOR_NOTE_ACCEPTED, appClipsActivity.mCallingPackageUid, appClipsActivity.mCallingPackageName);
                            } catch (Exception e2) {
                                Log.e("AppClipsActivity", "Error while sending data to trampoline activity", e2);
                            }
                            appClipsActivity.mResultReceiver = null;
                            appClipsActivity.finish();
                            break;
                        }
                        break;
                    case 1:
                        PackageManager.ApplicationInfoFlags applicationInfoFlags = AppClipsActivity.APPLICATION_INFO_FLAGS;
                        appClipsActivity.getClass();
                        appClipsActivity.mRoot.setBackgroundColor(Utils.getColorAttr(android.R.attr.colorBackgroundFloating, appClipsActivity).getDefaultColor());
                        appClipsActivity.mPreview.setImageDrawable(new BitmapDrawable(appClipsActivity.getResources(), (Bitmap) obj));
                        appClipsActivity.mPreview.setAlpha(1.0f);
                        appClipsActivity.setContentView(appClipsActivity.mLayout);
                        appClipsActivity.mRoot.requestApplyInsets();
                        break;
                    case 2:
                        int iIntValue = ((Integer) obj).intValue();
                        PackageManager.ApplicationInfoFlags applicationInfoFlags2 = AppClipsActivity.APPLICATION_INFO_FLAGS;
                        appClipsActivity.setError(iIntValue);
                        appClipsActivity.finish();
                        break;
                    case 3:
                        final List list = (List) obj;
                        appClipsActivity.mBacklinksIncludeDataCheckBox.setVisibility(0);
                        appClipsActivity.mBacklinksDataTextView.setVisibility(appClipsActivity.mBacklinksIncludeDataCheckBox.isChecked() ? 0 : 8);
                        if (list.size() > 1) {
                            HashMap map = new HashMap();
                            for (InternalBacklinksData internalBacklinksData2 : list) {
                                boolean zContainsKey = map.containsKey(internalBacklinksData2.backlinkDisplayInfo.displayLabel);
                                BacklinkDisplayInfo backlinkDisplayInfo = internalBacklinksData2.backlinkDisplayInfo;
                                if (zContainsKey) {
                                    int iIntValue2 = ((Integer) map.get(backlinkDisplayInfo.displayLabel)).intValue();
                                    if (iIntValue2 == 0) {
                                        map.put(backlinkDisplayInfo.displayLabel, 2);
                                    } else {
                                        map.put(backlinkDisplayInfo.displayLabel, Integer.valueOf(iIntValue2 + 1));
                                    }
                                } else {
                                    map.put(backlinkDisplayInfo.displayLabel, 0);
                                }
                            }
                            for (InternalBacklinksData internalBacklinksData3 : list.reversed()) {
                                String str = internalBacklinksData3.backlinkDisplayInfo.displayLabel;
                                Integer num = (Integer) map.get(str);
                                int iIntValue3 = num.intValue();
                                if (iIntValue3 > 0) {
                                    internalBacklinksData3.backlinkDisplayInfo.displayLabel = appClipsActivity.getString(R.string.backlinks_duplicate_label_format, new Object[]{str, num});
                                    map.put(str, Integer.valueOf(iIntValue3 - 1));
                                }
                            }
                            TextView textView = appClipsActivity.mBacklinksDataTextView;
                            final ListPopupWindow listPopupWindow = new ListPopupWindow(appClipsActivity);
                            listPopupWindow.setAnchorView(textView);
                            listPopupWindow.setOverlapAnchor(true);
                            listPopupWindow.setBackgroundDrawable(AppCompatResources.getDrawable(R.drawable.backlinks_rounded_rectangle, appClipsActivity));
                            listPopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.android.systemui.screenshot.appclips.AppClipsActivity$$ExternalSyntheticLambda9
                                @Override // android.widget.AdapterView.OnItemClickListener
                                public final void onItemClick(AdapterView adapterView, View view, int i32, long j) {
                                    AppClipsActivity appClipsActivity2 = appClipsActivity;
                                    List list2 = list;
                                    ListPopupWindow listPopupWindow2 = listPopupWindow;
                                    appClipsActivity2.mViewModel.mSelectedBacklinksLiveData.setValue((InternalBacklinksData) list2.get(i32));
                                    listPopupWindow2.dismiss();
                                }
                            });
                            ArrayAdapter arrayAdapter = new ArrayAdapter(appClipsActivity, R.layout.app_clips_backlinks_drop_down_entry) { // from class: com.android.systemui.screenshot.appclips.AppClipsActivity.2
                                @Override // android.widget.ArrayAdapter, android.widget.Adapter
                                public final View getView(int i32, View view, ViewGroup viewGroup) throws Resources.NotFoundException {
                                    TextView textView2 = (TextView) super.getView(i32, view, viewGroup);
                                    InternalBacklinksData internalBacklinksData4 = (InternalBacklinksData) list.get(i32);
                                    textView2.setText(internalBacklinksData4.backlinkDisplayInfo.displayLabel);
                                    Drawable drawable = internalBacklinksData4.backlinkDisplayInfo.appIcon;
                                    AppClipsActivity appClipsActivity2 = AppClipsActivity.this;
                                    PackageManager.ApplicationInfoFlags applicationInfoFlags3 = AppClipsActivity.APPLICATION_INFO_FLAGS;
                                    int dimensionPixelSize = appClipsActivity2.getResources().getDimensionPixelSize(R.dimen.appclips_backlinks_icon_size);
                                    Rect rect = new Rect();
                                    rect.set(0, 0, dimensionPixelSize, dimensionPixelSize);
                                    drawable.setBounds(rect);
                                    textView2.setCompoundDrawablesRelative(drawable, null, null, null);
                                    return textView2;
                                }
                            };
                            arrayAdapter.addAll(list);
                            listPopupWindow.setAdapter(arrayAdapter);
                            appClipsActivity.mBacklinksDataTextView.setOnClickListener(new AppClipsActivity$$ExternalSyntheticLambda1(listPopupWindow, 1));
                            break;
                        }
                        break;
                    default:
                        InternalBacklinksData internalBacklinksData4 = (InternalBacklinksData) obj;
                        appClipsActivity.mBacklinksDataTextView.setText(internalBacklinksData4.backlinkDisplayInfo.displayLabel);
                        Drawable drawable = internalBacklinksData4.backlinkDisplayInfo.appIcon;
                        int dimensionPixelSize = appClipsActivity.getResources().getDimensionPixelSize(R.dimen.appclips_backlinks_icon_size);
                        Rect rect = new Rect();
                        rect.set(0, 0, dimensionPixelSize, dimensionPixelSize);
                        drawable.setBounds(rect);
                        Drawable drawable2 = appClipsActivity.mBacklinksDataTextView.getCompoundDrawablesRelative()[2];
                        if (((List) appClipsActivity.mViewModel.mBacklinksLiveData.getValue()).size() > 1 && drawable2 == null) {
                            drawable2 = AppCompatResources.getDrawable(R.drawable.arrow_pointing_down, appClipsActivity);
                            drawable2.setBounds(rect);
                            drawable2.setTint(Utils.getColorAttr(android.R.attr.textColorSecondary, appClipsActivity).getDefaultColor());
                        }
                        appClipsActivity.mBacklinksDataTextView.setCompoundDrawablesRelative(drawable, null, drawable2, null);
                        appClipsActivity.mBacklinksIncludeDataCheckBox.setOnCheckedChangeListener(null);
                        if (internalBacklinksData4 instanceof InternalBacklinksData.CrossProfileError) {
                            appClipsActivity.mBacklinksIncludeDataCheckBox.setEnabled(false);
                            appClipsActivity.mBacklinksIncludeDataCheckBox.setChecked(false);
                            appClipsActivity.mBacklinksIncludeDataCheckBox.setAlpha(0.5f);
                            appClipsActivity.mBacklinksCrossProfileError.setVisibility(0);
                        } else {
                            appClipsActivity.mBacklinksIncludeDataCheckBox.setEnabled(true);
                            appClipsActivity.mBacklinksIncludeDataCheckBox.setChecked(true);
                            appClipsActivity.mBacklinksIncludeDataCheckBox.setAlpha(1.0f);
                            appClipsActivity.mBacklinksCrossProfileError.setVisibility(8);
                        }
                        appClipsActivity.mBacklinksIncludeDataCheckBox.setOnCheckedChangeListener(new AppClipsActivity$$ExternalSyntheticLambda3(appClipsActivity));
                        break;
                }
            }
        });
        if (bundle == null) {
            final int displayId = getDisplayId();
            final AppClipsViewModel appClipsViewModel2 = this.mViewModel;
            appClipsViewModel2.mBgExecutor.execute(new Runnable() { // from class: com.android.systemui.screenshot.appclips.AppClipsViewModel$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    Bitmap bitmapWrapHardwareBuffer;
                    AppClipsViewModel appClipsViewModel3 = appClipsViewModel2;
                    final int i6 = displayId;
                    AppClipsCrossProcessHelper appClipsCrossProcessHelper = appClipsViewModel3.mAppClipsCrossProcessHelper;
                    appClipsCrossProcessHelper.getClass();
                    try {
                        ScreenshotHardwareBufferInternal screenshotHardwareBufferInternal = (ScreenshotHardwareBufferInternal) appClipsCrossProcessHelper.mProxyConnector.postForResult(new ServiceConnector.Job() { // from class: com.android.systemui.screenshot.appclips.AppClipsCrossProcessHelper$$ExternalSyntheticLambda1
                            public final Object run(Object obj) {
                                return ((IAppClipsScreenshotHelperService) obj).takeScreenshot(i6);
                            }
                        }).get();
                        bitmapWrapHardwareBuffer = Bitmap.wrapHardwareBuffer(screenshotHardwareBufferInternal.mHardwareBuffer, screenshotHardwareBufferInternal.mParcelableColorSpace.getColorSpace());
                        screenshotHardwareBufferInternal.mHardwareBuffer.close();
                    } catch (Exception e2) {
                        Log.e("AppClipsCrossProcessHelper", String.format("Error while capturing a screenshot of displayId %d", Integer.valueOf(i6)), e2);
                        bitmapWrapHardwareBuffer = null;
                    }
                    appClipsViewModel3.mMainExecutor.execute(new AppClipsViewModel$$ExternalSyntheticLambda2(appClipsViewModel3, bitmapWrapHardwareBuffer, 1));
                }
            });
            final Set setOf = Set.of(Integer.valueOf(getTaskId()), Integer.valueOf(intent.getIntExtra(AppClipsTrampolineActivity.EXTRA_CALLING_PACKAGE_TASK_ID, -1)));
            final AppClipsViewModel appClipsViewModel3 = this.mViewModel;
            appClipsViewModel3.getClass();
            DebugLogger.INSTANCE.getClass();
            boolean z = Build.IS_DEBUGGABLE;
            Reflection.getOrCreateKotlinClass(AppClipsViewModel.class).getSimpleName();
            final SettableFuture settableFutureCreate = SettableFuture.create();
            appClipsViewModel3.mBgExecutor.execute(new Runnable() { // from class: com.android.systemui.screenshot.appclips.AppClipsViewModel$$ExternalSyntheticLambda6
                @Override // java.lang.Runnable
                public final void run() {
                    AppClipsViewModel appClipsViewModel4 = appClipsViewModel3;
                    int i6 = displayId;
                    SettableFuture settableFuture = settableFutureCreate;
                    appClipsViewModel4.getClass();
                    try {
                        settableFuture.set(appClipsViewModel4.mAtmService.getTasks(Integer.MAX_VALUE, false, false, i6).stream().map(new AppClipsViewModel$$ExternalSyntheticLambda10()).toList());
                    } catch (Exception e2) {
                        Log.e("AppClipsViewModel", String.format("Error getting all tasks on displayId %d", Integer.valueOf(i6)), e2);
                        settableFuture.set(Collections.EMPTY_LIST);
                    }
                }
            });
            TimeUnit timeUnit = TimeUnit.SECONDS;
            ScheduledExecutorService scheduledExecutorServiceNewSingleThreadScheduledExecutor = Executors.newSingleThreadScheduledExecutor();
            boolean zIsDone = settableFutureCreate.isDone();
            AbstractFuture.Trusted trustedCreate = settableFutureCreate;
            if (!zIsDone) {
                trustedCreate = TimeoutFuture.create(settableFutureCreate, scheduledExecutorServiceNewSingleThreadScheduledExecutor);
            }
            AbstractTransformFuture.TransformFuture transformFutureTransform = Futures.transform(trustedCreate, new Function() { // from class: com.android.systemui.screenshot.appclips.AppClipsViewModel$$ExternalSyntheticLambda4
                @Override // com.google.common.base.Function
                public final Object apply(Object obj) {
                    final Set set = setOf;
                    final AppClipsViewModel appClipsViewModel4 = appClipsViewModel3;
                    appClipsViewModel4.getClass();
                    final int i6 = 0;
                    Stream map = ((List) obj).stream().filter(new Predicate() { // from class: com.android.systemui.screenshot.appclips.AppClipsViewModel$$ExternalSyntheticLambda7
                        @Override // java.util.function.Predicate
                        public final boolean test(Object obj2) {
                            AppClipsViewModel appClipsViewModel5 = appClipsViewModel4;
                            Set set2 = set;
                            TaskInfo taskInfo = (TaskInfo) obj2;
                            appClipsViewModel5.getClass();
                            DebugLogger.INSTANCE.getClass();
                            boolean z2 = Build.IS_DEBUGGABLE;
                            Reflection.getOrCreateKotlinClass(AppClipsViewModel.class).getSimpleName();
                            return !set2.contains(Integer.valueOf(taskInfo.taskId)) && taskInfo.isVisible && taskInfo.isRunning && taskInfo.numActivities > 0 && taskInfo.topActivity != null && taskInfo.topActivityInfo != null && taskInfo.getActivityType() == 1;
                        }
                    }).map(new java.util.function.Function() { // from class: com.android.systemui.screenshot.appclips.AppClipsViewModel$$ExternalSyntheticLambda8
                        @Override // java.util.function.Function
                        public final Object apply(Object obj2) {
                            int i7 = i6;
                            final AppClipsViewModel appClipsViewModel5 = appClipsViewModel4;
                            switch (i7) {
                                case 0:
                                    TaskInfo taskInfo = (TaskInfo) obj2;
                                    appClipsViewModel5.getClass();
                                    ActivityInfo activityInfo = taskInfo.topActivityInfo;
                                    int i8 = taskInfo.taskId;
                                    int i9 = taskInfo.userId;
                                    return new InternalTaskInfo(activityInfo, i8, i9, appClipsViewModel5.mContext.getUserId() == i9 ? appClipsViewModel5.mContext.getPackageManager() : appClipsViewModel5.mContext.createContextAsUser(UserHandle.of(i9), 0).getPackageManager());
                                default:
                                    final InternalTaskInfo internalTaskInfo = (InternalTaskInfo) obj2;
                                    appClipsViewModel5.getClass();
                                    DebugLogger.INSTANCE.getClass();
                                    boolean z2 = Build.IS_DEBUGGABLE;
                                    Reflection.getOrCreateKotlinClass(AppClipsViewModel.class).getSimpleName();
                                    if (internalTaskInfo.userId != UserHandle.myUserId()) {
                                        return Futures.immediateFuture(new InternalBacklinksData.CrossProfileError((Drawable) internalTaskInfo.topActivityAppIcon$delegate.getValue(), (String) internalTaskInfo.topActivityAppName$delegate.getValue()));
                                    }
                                    final SettableFuture settableFutureCreate2 = SettableFuture.create();
                                    AssistContentRequester.Callback callback = new AssistContentRequester.Callback() { // from class: com.android.systemui.screenshot.appclips.AppClipsViewModel$$ExternalSyntheticLambda11
                                        /* JADX WARN: Removed duplicated region for block: B:10:0x0072  */
                                        /* JADX WARN: Removed duplicated region for block: B:15:0x009e  */
                                        @Override // com.android.systemui.screenshot.AssistContentRequester.Callback
                                        /*
                                            Code decompiled incorrectly, please refer to instructions dump.
                                        */
                                        public final void onAssistContentAvailable(AssistContent assistContent) {
                                            AppClipsViewModel appClipsViewModel6 = appClipsViewModel5;
                                            appClipsViewModel6.getClass();
                                            DebugLogger.INSTANCE.getClass();
                                            boolean z3 = Build.IS_DEBUGGABLE;
                                            Reflection.getOrCreateKotlinClass(AppClipsViewModel.class).getSimpleName();
                                            InternalTaskInfo internalTaskInfo2 = internalTaskInfo;
                                            String str = (String) internalTaskInfo2.topActivityAppName$delegate.getValue();
                                            InternalBacklinksData.BacklinksData backlinksData = new InternalBacklinksData.BacklinksData(ClipData.newIntent(str, AppClipsViewModel.getMainLauncherIntentForTask(internalTaskInfo2.packageManager, internalTaskInfo2.topActivityPackageName)), (Drawable) internalTaskInfo2.topActivityAppIcon$delegate.getValue());
                                            if (assistContent != null) {
                                                if (assistContent.isAppProvidedWebUri()) {
                                                    Reflection.getOrCreateKotlinClass(AppClipsViewModel.class).getSimpleName();
                                                    Uri webUri = assistContent.getWebUri();
                                                    BacklinkDisplayInfo infoThatResolvesIntent = appClipsViewModel6.getInfoThatResolvesIntent(new Intent("android.intent.action.VIEW").setData(webUri), internalTaskInfo2);
                                                    if (infoThatResolvesIntent != null) {
                                                        Reflection.getOrCreateKotlinClass(AppClipsViewModel.class).getSimpleName();
                                                        backlinksData = new InternalBacklinksData.BacklinksData(ClipData.newRawUri(infoThatResolvesIntent.displayLabel, webUri), infoThatResolvesIntent.appIcon);
                                                    } else if (assistContent.isAppProvidedIntent()) {
                                                        Reflection.getOrCreateKotlinClass(AppClipsViewModel.class).getSimpleName();
                                                        Intent intent2 = assistContent.getIntent();
                                                        BacklinkDisplayInfo infoThatResolvesIntent2 = appClipsViewModel6.getInfoThatResolvesIntent(intent2, internalTaskInfo2);
                                                        if (infoThatResolvesIntent2 != null) {
                                                            Reflection.getOrCreateKotlinClass(AppClipsViewModel.class).getSimpleName();
                                                            backlinksData = new InternalBacklinksData.BacklinksData(ClipData.newIntent(infoThatResolvesIntent2.displayLabel, intent2), infoThatResolvesIntent2.appIcon);
                                                        } else {
                                                            Reflection.getOrCreateKotlinClass(AppClipsViewModel.class).getSimpleName();
                                                        }
                                                    }
                                                }
                                            }
                                            settableFutureCreate2.set(backlinksData);
                                        }
                                    };
                                    AssistContentRequester assistContentRequester = appClipsViewModel5.mAssistContentRequester;
                                    assistContentRequester.mSystemInteractionExecutor.execute(new AssistContentRequester$$ExternalSyntheticLambda0(assistContentRequester, callback, internalTaskInfo.taskId));
                                    TimeUnit timeUnit2 = TimeUnit.SECONDS;
                                    return settableFutureCreate2.isDone() ? settableFutureCreate2 : TimeoutFuture.create(settableFutureCreate2, Executors.newSingleThreadScheduledExecutor());
                            }
                        }
                    });
                    final int i7 = 1;
                    return map.map(new java.util.function.Function() { // from class: com.android.systemui.screenshot.appclips.AppClipsViewModel$$ExternalSyntheticLambda8
                        @Override // java.util.function.Function
                        public final Object apply(Object obj2) {
                            int i72 = i7;
                            final AppClipsViewModel appClipsViewModel5 = appClipsViewModel4;
                            switch (i72) {
                                case 0:
                                    TaskInfo taskInfo = (TaskInfo) obj2;
                                    appClipsViewModel5.getClass();
                                    ActivityInfo activityInfo = taskInfo.topActivityInfo;
                                    int i8 = taskInfo.taskId;
                                    int i9 = taskInfo.userId;
                                    return new InternalTaskInfo(activityInfo, i8, i9, appClipsViewModel5.mContext.getUserId() == i9 ? appClipsViewModel5.mContext.getPackageManager() : appClipsViewModel5.mContext.createContextAsUser(UserHandle.of(i9), 0).getPackageManager());
                                default:
                                    final InternalTaskInfo internalTaskInfo = (InternalTaskInfo) obj2;
                                    appClipsViewModel5.getClass();
                                    DebugLogger.INSTANCE.getClass();
                                    boolean z2 = Build.IS_DEBUGGABLE;
                                    Reflection.getOrCreateKotlinClass(AppClipsViewModel.class).getSimpleName();
                                    if (internalTaskInfo.userId != UserHandle.myUserId()) {
                                        return Futures.immediateFuture(new InternalBacklinksData.CrossProfileError((Drawable) internalTaskInfo.topActivityAppIcon$delegate.getValue(), (String) internalTaskInfo.topActivityAppName$delegate.getValue()));
                                    }
                                    final SettableFuture settableFutureCreate2 = SettableFuture.create();
                                    AssistContentRequester.Callback callback = new AssistContentRequester.Callback() { // from class: com.android.systemui.screenshot.appclips.AppClipsViewModel$$ExternalSyntheticLambda11
                                        /* JADX WARN: Removed duplicated region for block: B:10:0x0072  */
                                        /* JADX WARN: Removed duplicated region for block: B:15:0x009e  */
                                        @Override // com.android.systemui.screenshot.AssistContentRequester.Callback
                                        /*
                                            Code decompiled incorrectly, please refer to instructions dump.
                                        */
                                        public final void onAssistContentAvailable(AssistContent assistContent) {
                                            AppClipsViewModel appClipsViewModel6 = appClipsViewModel5;
                                            appClipsViewModel6.getClass();
                                            DebugLogger.INSTANCE.getClass();
                                            boolean z3 = Build.IS_DEBUGGABLE;
                                            Reflection.getOrCreateKotlinClass(AppClipsViewModel.class).getSimpleName();
                                            InternalTaskInfo internalTaskInfo2 = internalTaskInfo;
                                            String str = (String) internalTaskInfo2.topActivityAppName$delegate.getValue();
                                            InternalBacklinksData.BacklinksData backlinksData = new InternalBacklinksData.BacklinksData(ClipData.newIntent(str, AppClipsViewModel.getMainLauncherIntentForTask(internalTaskInfo2.packageManager, internalTaskInfo2.topActivityPackageName)), (Drawable) internalTaskInfo2.topActivityAppIcon$delegate.getValue());
                                            if (assistContent != null) {
                                                if (assistContent.isAppProvidedWebUri()) {
                                                    Reflection.getOrCreateKotlinClass(AppClipsViewModel.class).getSimpleName();
                                                    Uri webUri = assistContent.getWebUri();
                                                    BacklinkDisplayInfo infoThatResolvesIntent = appClipsViewModel6.getInfoThatResolvesIntent(new Intent("android.intent.action.VIEW").setData(webUri), internalTaskInfo2);
                                                    if (infoThatResolvesIntent != null) {
                                                        Reflection.getOrCreateKotlinClass(AppClipsViewModel.class).getSimpleName();
                                                        backlinksData = new InternalBacklinksData.BacklinksData(ClipData.newRawUri(infoThatResolvesIntent.displayLabel, webUri), infoThatResolvesIntent.appIcon);
                                                    } else if (assistContent.isAppProvidedIntent()) {
                                                        Reflection.getOrCreateKotlinClass(AppClipsViewModel.class).getSimpleName();
                                                        Intent intent2 = assistContent.getIntent();
                                                        BacklinkDisplayInfo infoThatResolvesIntent2 = appClipsViewModel6.getInfoThatResolvesIntent(intent2, internalTaskInfo2);
                                                        if (infoThatResolvesIntent2 != null) {
                                                            Reflection.getOrCreateKotlinClass(AppClipsViewModel.class).getSimpleName();
                                                            backlinksData = new InternalBacklinksData.BacklinksData(ClipData.newIntent(infoThatResolvesIntent2.displayLabel, intent2), infoThatResolvesIntent2.appIcon);
                                                        } else {
                                                            Reflection.getOrCreateKotlinClass(AppClipsViewModel.class).getSimpleName();
                                                        }
                                                    }
                                                }
                                            }
                                            settableFutureCreate2.set(backlinksData);
                                        }
                                    };
                                    AssistContentRequester assistContentRequester = appClipsViewModel5.mAssistContentRequester;
                                    assistContentRequester.mSystemInteractionExecutor.execute(new AssistContentRequester$$ExternalSyntheticLambda0(assistContentRequester, callback, internalTaskInfo.taskId));
                                    TimeUnit timeUnit2 = TimeUnit.SECONDS;
                                    return settableFutureCreate2.isDone() ? settableFutureCreate2 : TimeoutFuture.create(settableFutureCreate2, Executors.newSingleThreadScheduledExecutor());
                            }
                        }
                    }).toList();
                }
            }, appClipsViewModel3.mBgExecutor);
            AppClipsViewModel$$ExternalSyntheticLambda5 appClipsViewModel$$ExternalSyntheticLambda5 = new AppClipsViewModel$$ExternalSyntheticLambda5();
            Executor executor = appClipsViewModel3.mBgExecutor;
            int i6 = AbstractTransformFuture.$r8$clinit;
            executor.getClass();
            AbstractTransformFuture.AsyncTransformFuture asyncTransformFuture = new AbstractTransformFuture.AsyncTransformFuture(transformFutureTransform, appClipsViewModel$$ExternalSyntheticLambda5);
            transformFutureTransform.addListener(asyncTransformFuture, MoreExecutors.rejectionPropagatingExecutor(executor, asyncTransformFuture));
            asyncTransformFuture.addListener(new Futures.CallbackListener(asyncTransformFuture, new FutureCallback() { // from class: com.android.systemui.screenshot.appclips.AppClipsViewModel.1
                public AnonymousClass1() {
                }

                @Override // com.google.common.util.concurrent.FutureCallback
                public final void onFailure(Throwable th) {
                    Log.e("AppClipsViewModel", "Error querying for Backlinks data", th);
                }

                @Override // com.google.common.util.concurrent.FutureCallback
                public final void onSuccess(Object obj) {
                    List list = (List) obj;
                    if (list == null || list.isEmpty()) {
                        return;
                    }
                    AppClipsViewModel appClipsViewModel4 = AppClipsViewModel.this;
                    appClipsViewModel4.mBacklinksLiveData.setValue(list);
                    appClipsViewModel4.mSelectedBacklinksLiveData.setValue((InternalBacklinksData) list.get(0));
                }
            }), appClipsViewModel3.mMainExecutor);
        }
    }

    @Override // android.app.Activity
    public final void onDestroy() {
        super.onDestroy();
        unregisterReceiver(this.mBroadcastReceiver);
        if (isFinishing() && this.mViewModel.mErrorLiveData.getValue() == null && this.mViewModel.mResultLiveData.getValue() == null) {
            setError(1);
        }
    }

    public final void setError(int i) {
        if (this.mResultReceiver == null) {
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putInt("android.intent.extra.CAPTURE_CONTENT_FOR_NOTE_STATUS_CODE", i);
        try {
            this.mResultReceiver.send(-1, bundle);
            if (i == 2) {
                this.mUiEventLogger.log(AppClipsEvent.SCREENSHOT_FOR_NOTE_CANCELLED, this.mCallingPackageUid, this.mCallingPackageName);
            }
        } catch (Exception e) {
            Log.e("AppClipsActivity", "Error while sending trampoline activity error code: " + i, e);
        }
        this.mResultReceiver = null;
    }

    public final void updateImageDimensions() {
        Drawable drawable = this.mPreview.getDrawable();
        if (drawable == null) {
            return;
        }
        Rect bounds = drawable.getBounds();
        float fWidth = bounds.width() / bounds.height();
        int width = (this.mPreview.getWidth() - this.mPreview.getPaddingLeft()) - this.mPreview.getPaddingRight();
        int height = (this.mPreview.getHeight() - this.mPreview.getPaddingTop()) - this.mPreview.getPaddingBottom();
        float f = height;
        float f2 = width / f;
        if (fWidth > f2) {
            int i = (height - ((int) ((f * f2) / fWidth))) / 2;
            CropView cropView = this.mCropView;
            cropView.mExtraTopPadding = i;
            cropView.mExtraBottomPadding = i;
            cropView.invalidate();
            CropView cropView2 = this.mCropView;
            cropView2.mImageWidth = width;
            cropView2.invalidate();
            return;
        }
        CropView cropView3 = this.mCropView;
        int paddingTop = this.mPreview.getPaddingTop();
        int paddingBottom = this.mPreview.getPaddingBottom();
        cropView3.mExtraTopPadding = paddingTop;
        cropView3.mExtraBottomPadding = paddingBottom;
        cropView3.invalidate();
        CropView cropView4 = this.mCropView;
        cropView4.mImageWidth = (int) (f * fWidth);
        cropView4.invalidate();
    }
}
