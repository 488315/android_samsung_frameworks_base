package com.android.systemui.screenshot.appclips;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.HardwareRenderer;
import android.graphics.RecordingCanvas;
import android.graphics.Rect;
import android.graphics.RenderNode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.os.UserHandle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import androidx.activity.ComponentActivity;
import androidx.concurrent.futures.CallbackToFutureAdapter;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.android.internal.infra.ServiceConnector;
import com.android.internal.logging.UiEventLogger;
import com.android.settingslib.Utils;
import com.android.systemui.R;
import com.android.systemui.screenshot.appclips.AppClipsViewModel;
import com.android.systemui.screenshot.scroll.CropView;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import java.util.UUID;

public class AppClipsActivity extends ComponentActivity {
    public static final PackageManager.ApplicationInfoFlags APPLICATION_INFO_FLAGS = PackageManager.ApplicationInfoFlags.of(0);
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
                    AppClipsViewModel appClipsViewModel2 = AppClipsViewModel.this;
                    Drawable drawable2 = drawable;
                    Rect rect = cropBoundaries;
                    UserHandle userHandle = user;
                    appClipsViewModel2.getClass();
                    RenderNode renderNode = new RenderNode("Screenshot save");
                    renderNode.setPosition(0, 0, rect.width(), rect.height());
                    RecordingCanvas beginRecording = renderNode.beginRecording();
                    beginRecording.translate(-rect.left, -rect.top);
                    beginRecording.clipRect(rect);
                    drawable2.draw(beginRecording);
                    renderNode.endRecording();
                    CallbackToFutureAdapter.SafeFuture export = appClipsViewModel2.mImageExporter.export(appClipsViewModel2.mBgExecutor, UUID.randomUUID(), HardwareRenderer.createHardwareBitmap(renderNode, rect.width(), rect.height()), userHandle, 0);
                    export.delegate.addListener(new AppClipsViewModel$$ExternalSyntheticLambda2(appClipsViewModel2, export, 1), appClipsViewModel2.mMainExecutor);
                }
            });
        }
    }

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
        View inflate = getLayoutInflater().inflate(R.layout.app_clips_screenshot, (ViewGroup) null);
        this.mLayout = inflate;
        this.mRoot = inflate.findViewById(R.id.root);
        this.mSave = (Button) this.mLayout.findViewById(R.id.save);
        this.mCancel = (Button) this.mLayout.findViewById(R.id.cancel);
        this.mSave.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.screenshot.appclips.AppClipsActivity$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                AppClipsActivity.$r8$lambda$stcjMVf1JwQ0vVO3T941Qunv0VY(AppClipsActivity.this, view);
            }
        });
        this.mCancel.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.screenshot.appclips.AppClipsActivity$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                AppClipsActivity.$r8$lambda$stcjMVf1JwQ0vVO3T941Qunv0VY(AppClipsActivity.this, view);
            }
        });
        this.mCropView = (CropView) this.mLayout.findViewById(R.id.crop_view);
        ImageView imageView = (ImageView) this.mLayout.findViewById(R.id.preview);
        this.mPreview = imageView;
        imageView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() { // from class: com.android.systemui.screenshot.appclips.AppClipsActivity$$ExternalSyntheticLambda1
            @Override // android.view.View.OnLayoutChangeListener
            public final void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
                AppClipsActivity appClipsActivity = AppClipsActivity.this;
                PackageManager.ApplicationInfoFlags applicationInfoFlags = AppClipsActivity.APPLICATION_INFO_FLAGS;
                appClipsActivity.updateImageDimensions();
            }
        });
        AppClipsViewModel appClipsViewModel = (AppClipsViewModel) new ViewModelProvider(this, this.mViewModelFactory).get(AppClipsViewModel.class);
        this.mViewModel = appClipsViewModel;
        final int i = 1;
        appClipsViewModel.mScreenshotLiveData.observe(this, new Observer(this) { // from class: com.android.systemui.screenshot.appclips.AppClipsActivity$$ExternalSyntheticLambda3
            public final /* synthetic */ AppClipsActivity f$0;

            {
                this.f$0 = this;
            }

            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                AppClipsActivity appClipsActivity = this.f$0;
                switch (i) {
                    case 0:
                        Uri uri = (Uri) obj;
                        if (appClipsActivity.mResultReceiver != null) {
                            appClipsActivity.grantUriPermission(appClipsActivity.mCallingPackageName, uri, 1);
                            Bundle bundle2 = new Bundle();
                            bundle2.putInt("android.intent.extra.CAPTURE_CONTENT_FOR_NOTE_STATUS_CODE", 0);
                            bundle2.putParcelable(AppClipsTrampolineActivity.EXTRA_SCREENSHOT_URI, uri);
                            try {
                                appClipsActivity.mResultReceiver.send(-1, bundle2);
                                appClipsActivity.mUiEventLogger.log(AppClipsEvent.SCREENSHOT_FOR_NOTE_ACCEPTED, appClipsActivity.mCallingPackageUid, appClipsActivity.mCallingPackageName);
                            } catch (Exception unused) {
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
                        break;
                    default:
                        int intValue = ((Integer) obj).intValue();
                        PackageManager.ApplicationInfoFlags applicationInfoFlags2 = AppClipsActivity.APPLICATION_INFO_FLAGS;
                        appClipsActivity.setError(intValue);
                        appClipsActivity.finish();
                        break;
                }
            }
        });
        final int i2 = 0;
        this.mViewModel.mResultLiveData.observe(this, new Observer(this) { // from class: com.android.systemui.screenshot.appclips.AppClipsActivity$$ExternalSyntheticLambda3
            public final /* synthetic */ AppClipsActivity f$0;

            {
                this.f$0 = this;
            }

            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                AppClipsActivity appClipsActivity = this.f$0;
                switch (i2) {
                    case 0:
                        Uri uri = (Uri) obj;
                        if (appClipsActivity.mResultReceiver != null) {
                            appClipsActivity.grantUriPermission(appClipsActivity.mCallingPackageName, uri, 1);
                            Bundle bundle2 = new Bundle();
                            bundle2.putInt("android.intent.extra.CAPTURE_CONTENT_FOR_NOTE_STATUS_CODE", 0);
                            bundle2.putParcelable(AppClipsTrampolineActivity.EXTRA_SCREENSHOT_URI, uri);
                            try {
                                appClipsActivity.mResultReceiver.send(-1, bundle2);
                                appClipsActivity.mUiEventLogger.log(AppClipsEvent.SCREENSHOT_FOR_NOTE_ACCEPTED, appClipsActivity.mCallingPackageUid, appClipsActivity.mCallingPackageName);
                            } catch (Exception unused) {
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
                        break;
                    default:
                        int intValue = ((Integer) obj).intValue();
                        PackageManager.ApplicationInfoFlags applicationInfoFlags2 = AppClipsActivity.APPLICATION_INFO_FLAGS;
                        appClipsActivity.setError(intValue);
                        appClipsActivity.finish();
                        break;
                }
            }
        });
        final int i3 = 2;
        this.mViewModel.mErrorLiveData.observe(this, new Observer(this) { // from class: com.android.systemui.screenshot.appclips.AppClipsActivity$$ExternalSyntheticLambda3
            public final /* synthetic */ AppClipsActivity f$0;

            {
                this.f$0 = this;
            }

            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                AppClipsActivity appClipsActivity = this.f$0;
                switch (i3) {
                    case 0:
                        Uri uri = (Uri) obj;
                        if (appClipsActivity.mResultReceiver != null) {
                            appClipsActivity.grantUriPermission(appClipsActivity.mCallingPackageName, uri, 1);
                            Bundle bundle2 = new Bundle();
                            bundle2.putInt("android.intent.extra.CAPTURE_CONTENT_FOR_NOTE_STATUS_CODE", 0);
                            bundle2.putParcelable(AppClipsTrampolineActivity.EXTRA_SCREENSHOT_URI, uri);
                            try {
                                appClipsActivity.mResultReceiver.send(-1, bundle2);
                                appClipsActivity.mUiEventLogger.log(AppClipsEvent.SCREENSHOT_FOR_NOTE_ACCEPTED, appClipsActivity.mCallingPackageUid, appClipsActivity.mCallingPackageName);
                            } catch (Exception unused) {
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
                        break;
                    default:
                        int intValue = ((Integer) obj).intValue();
                        PackageManager.ApplicationInfoFlags applicationInfoFlags2 = AppClipsActivity.APPLICATION_INFO_FLAGS;
                        appClipsActivity.setError(intValue);
                        appClipsActivity.finish();
                        break;
                }
            }
        });
        if (bundle == null) {
            final AppClipsViewModel appClipsViewModel2 = this.mViewModel;
            appClipsViewModel2.mBgExecutor.execute(new Runnable() { // from class: com.android.systemui.screenshot.appclips.AppClipsViewModel$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    Bitmap bitmap;
                    AppClipsViewModel appClipsViewModel3 = AppClipsViewModel.this;
                    final AppClipsCrossProcessHelper appClipsCrossProcessHelper = appClipsViewModel3.mAppClipsCrossProcessHelper;
                    appClipsCrossProcessHelper.getClass();
                    try {
                        ScreenshotHardwareBufferInternal screenshotHardwareBufferInternal = (ScreenshotHardwareBufferInternal) appClipsCrossProcessHelper.mProxyConnector.postForResult(new ServiceConnector.Job() { // from class: com.android.systemui.screenshot.appclips.AppClipsCrossProcessHelper$$ExternalSyntheticLambda1
                            public final Object run(Object obj) {
                                AppClipsCrossProcessHelper.this.mDisplayTracker.getClass();
                                return ((IAppClipsScreenshotHelperService) obj).takeScreenshot(0);
                            }
                        }).get();
                        bitmap = Bitmap.wrapHardwareBuffer(screenshotHardwareBufferInternal.mHardwareBuffer, screenshotHardwareBufferInternal.mParcelableColorSpace.getColorSpace());
                        screenshotHardwareBufferInternal.mHardwareBuffer.close();
                    } catch (Exception unused) {
                        bitmap = null;
                    }
                    appClipsViewModel3.mMainExecutor.execute(new AppClipsViewModel$$ExternalSyntheticLambda2(appClipsViewModel3, bitmap, 0));
                }
            });
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
        } catch (Exception unused) {
        }
        this.mResultReceiver = null;
    }

    public final void updateImageDimensions() {
        Drawable drawable = this.mPreview.getDrawable();
        if (drawable == null) {
            return;
        }
        Rect bounds = drawable.getBounds();
        float width = bounds.width() / bounds.height();
        int width2 = (this.mPreview.getWidth() - this.mPreview.getPaddingLeft()) - this.mPreview.getPaddingRight();
        int height = (this.mPreview.getHeight() - this.mPreview.getPaddingTop()) - this.mPreview.getPaddingBottom();
        float f = height;
        float f2 = width2 / f;
        if (width > f2) {
            int i = (height - ((int) ((f * f2) / width))) / 2;
            CropView cropView = this.mCropView;
            cropView.mExtraTopPadding = i;
            cropView.mExtraBottomPadding = i;
            cropView.invalidate();
            CropView cropView2 = this.mCropView;
            cropView2.mImageWidth = width2;
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
        cropView4.mImageWidth = (int) (f * width);
        cropView4.invalidate();
    }
}
