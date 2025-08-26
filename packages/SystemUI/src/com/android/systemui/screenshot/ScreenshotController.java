package com.android.systemui.screenshot;

import android.app.ActivityManager;
import android.app.Presentation;
import android.app.SemStatusBarManager;
import android.app.assist.AssistContent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.os.Trace;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.Base64;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.window.WindowContext;
import androidx.appcompat.util.SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.collection.MutableObjectList$$ExternalSyntheticOutline0;
import androidx.compose.foundation.gestures.ContentInViewNode$Request$$ExternalSyntheticOutline0;
import androidx.compose.ui.autofill.PopulateViewStructure_androidKt$$ExternalSyntheticOutline0;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.concurrent.futures.CallbackToFutureAdapter;
import androidx.vectordrawable.graphics.drawable.AnimatorInflaterCompat$$ExternalSyntheticOutline0;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.internal.logging.UiEventLogger;
import com.android.keyguard.KeyguardPluginControllerImpl$$ExternalSyntheticOutline0;
import com.android.settingslib.applications.InterestingConfigChanges;
import com.android.systemui.R;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.broadcast.BroadcastSender;
import com.android.systemui.broadcast.BroadcastSender$$ExternalSyntheticLambda0;
import com.android.systemui.dagger.DaggerReferenceGlobalRootComponent;
import com.android.systemui.screenshot.ActionExecutor;
import com.android.systemui.screenshot.AnnouncementResolver;
import com.android.systemui.screenshot.AssistContentRequester;
import com.android.systemui.screenshot.ImageExporter;
import com.android.systemui.screenshot.ScreenshotActionsController;
import com.android.systemui.screenshot.ScreenshotActionsController.ActionsCallback;
import com.android.systemui.screenshot.ScreenshotController$startCaptureAppRemoteServiceConnection$1;
import com.android.systemui.screenshot.ScreenshotNotificationsController;
import com.android.systemui.screenshot.ScreenshotShelfViewProxy;
import com.android.systemui.screenshot.ScreenshotWindow;
import com.android.systemui.screenshot.TakeScreenshotService;
import com.android.systemui.screenshot.scroll.ScrollCaptureExecutor;
import com.android.systemui.screenshot.sep.AliveShotImageUtils;
import com.android.systemui.screenshot.sep.BixbyShareController;
import com.android.systemui.screenshot.sep.EdmUtils;
import com.android.systemui.screenshot.sep.ScreenCaptureHelper;
import com.android.systemui.screenshot.sep.ScreenshotFeedbackController;
import com.android.systemui.screenshot.sep.ScreenshotSelectorView;
import com.android.systemui.screenshot.sep.ScreenshotUtils;
import com.android.systemui.screenshot.sep.ScreenshotViewUtils;
import com.android.systemui.screenshot.sep.SemImageCaptureImpl;
import com.android.systemui.screenshot.sep.SemScreenshotResult;
import com.android.systemui.screenshot.sep.SmartClipDataExtractor;
import com.android.systemui.screenshot.sep.SnackbarController;
import com.android.systemui.screenshot.sep.widget.SemScreenshotLayout;
import com.android.systemui.screenshot.ui.ScreenshotShelfView;
import com.android.systemui.util.Assert;
import com.android.systemui.util.SettingsHelper;
import com.google.common.util.concurrent.ListenableFuture;
import com.samsung.android.app.SemMultiWindowManager;
import com.samsung.android.app.smartcapture.screenshot.lib.IScreenshotService;
import com.samsung.android.app.smartcapture.screenshot.lib.RemoteScreenshotInterface;
import com.samsung.android.content.smartclip.SemRemoteAppDataExtractionManager;
import com.samsung.android.content.smartclip.SemSmartClipDataRepository;
import com.samsung.android.content.smartclip.SemSmartClipMetaTag;
import com.samsung.android.content.smartclip.SemSmartClipMetaTagArray;
import com.samsung.android.content.smartclip.SpenGestureManager;
import com.samsung.android.view.ScreenshotResult;
import com.samsung.systemui.splugins.edgelightingplus.PluginEdgeLightingPlus;
import com.sec.ims.volte2.data.VolteConstants;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import java.io.File;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.EmptyList;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.text.Charsets;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public final class ScreenshotController implements InteractiveScreenshotHandler {
    public static final String TAG;
    public static boolean isAnimationRunning;
    public static boolean isSnackBarShowing;
    public static final Object shutterEffectLock;
    public static final ArrayList systemAppList;
    public final ActionExecutor actionExecutor;
    public final ScreenshotActionsController actionsController;
    public final AnnouncementResolver announcementResolver;
    public final AssistContentRequester assistContentRequester;
    public BixbyShareController bixbyShareController;
    public final BroadcastDispatcher broadcastDispatcher;
    public final BroadcastSender broadcastSender;
    public final WindowContext context;
    public final AnonymousClass3 copyBroadcastReceiver;
    public final Display display;
    public WindowManager displayContextWindowManager;
    public String imageDisplayName;
    public final ImageExporter imageExporter;
    public String imageFileName;
    public String imageFilePath;
    public boolean isRemoteScreenshotConnectionListenerInvoked;
    public boolean isSavingFailed;
    public boolean isScreenshotDismissed;
    public boolean isScreenshotSaveTaskCompleted;
    public boolean isSemScreenshotLayoutInitialized;
    public final Executor mainExecutor;
    public final MessageContainerController messageContainerController;
    public final ScreenshotNotificationsController notificationController;
    public Presentation presentation;
    public Bitmap screenBitmap;
    public ScreenCaptureHelper screenCaptureHelper;
    public final ScreenshotDetectionController screenshotDetectionController;
    public final ScreenshotFeedbackController screenshotFeedbackController;
    public final TimeoutHandler screenshotHandler;
    public RemoteScreenshotInterface screenshotInterface;
    public ScreenshotSelectorView screenshotSelectorView;
    public final ScreenshotSoundController screenshotSoundController;
    public final ScrollCaptureExecutor scrollCaptureExecutor;
    public SemScreenshotLayout semScreenshotLayout;
    public final SemImageCaptureImpl sepImageCapture;
    public String thumbnailImageFilePath;
    public final UiEventLogger uiEventLogger;
    public final UserManager userManager;
    public final ScreenshotShelfViewProxy viewProxy;
    public SmartClipDataExtractor.WebData webData;
    public final ScreenshotWindow window;
    public final ExecutorService bgExecutor = Executors.newSingleThreadExecutor();
    public final List currentRequestCallbacks = new ArrayList();
    public String packageName = "";
    public List notifiedApps = new ArrayList();
    public final long screenshotTransactionId = System.currentTimeMillis();
    public final Object remoteServiceConnectionLock = new Object();

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
        TAG = "Screenshot";
        shutterEffectLock = new Object();
        systemAppList = new ArrayList(Arrays.asList("System UI"));
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r6v6, types: [android.content.BroadcastReceiver, com.android.systemui.screenshot.ScreenshotController$3] */
    public ScreenshotController(Context context, ScreenshotWindow.Factory factory, ScreenshotShelfViewProxy.Factory factory2, ScreenshotNotificationsController.Factory factory3, ScreenshotActionsController.Factory factory4, ActionExecutor.Factory factory5, ScreenshotSoundController screenshotSoundController, UiEventLogger uiEventLogger, ImageExporter imageExporter, ImageCapture imageCapture, SemImageCaptureImpl semImageCaptureImpl, ScreenshotDetectionController screenshotDetectionController, ScreenshotFeedbackController screenshotFeedbackController, ScrollCaptureExecutor scrollCaptureExecutor, TimeoutHandler timeoutHandler, BroadcastSender broadcastSender, BroadcastDispatcher broadcastDispatcher, UserManager userManager, AssistContentRequester assistContentRequester, MessageContainerController messageContainerController, AnnouncementResolver announcementResolver, Executor executor, ActionIntentCreator actionIntentCreator, Display display) {
        this.screenshotSoundController = screenshotSoundController;
        this.uiEventLogger = uiEventLogger;
        this.imageExporter = imageExporter;
        this.sepImageCapture = semImageCaptureImpl;
        this.screenshotDetectionController = screenshotDetectionController;
        this.screenshotFeedbackController = screenshotFeedbackController;
        this.scrollCaptureExecutor = scrollCaptureExecutor;
        this.screenshotHandler = timeoutHandler;
        this.broadcastSender = broadcastSender;
        this.broadcastDispatcher = broadcastDispatcher;
        this.userManager = userManager;
        this.assistContentRequester = assistContentRequester;
        this.messageContainerController = messageContainerController;
        this.announcementResolver = announcementResolver;
        this.mainExecutor = executor;
        this.display = display;
        this.notificationController = factory3.create(display.getDisplayId());
        InterestingConfigChanges interestingConfigChanges = new InterestingConfigChanges(-2147474556);
        timeoutHandler.mDefaultTimeout = PluginEdgeLightingPlus.VERSION;
        ScreenshotWindow screenshotWindowCreate = factory.create(display);
        this.window = screenshotWindowCreate;
        WindowContext context2 = screenshotWindowCreate.window.getContext();
        this.context = context2;
        ScreenshotShelfViewProxy proxy = factory2.getProxy(context2, display.getDisplayId());
        this.viewProxy = proxy;
        timeoutHandler.mOnTimeout = new Runnable() { // from class: com.android.systemui.screenshot.ScreenshotController.1
            @Override // java.lang.Runnable
            public final void run() {
                ScreenshotController.this.viewProxy.requestDismissal(ScreenshotEvent.SCREENSHOT_INTERACTION_TIMEOUT, null);
            }
        };
        interestingConfigChanges.applyNewConfig(context.getResources());
        ScreenshotShelfView screenshotShelfView = proxy.view;
        messageContainerController.setView(screenshotShelfView);
        proxy.callbacks = new ScreenshotShelfViewProxy.ScreenshotViewCallback() { // from class: com.android.systemui.screenshot.ScreenshotController$reloadAssets$1
            @Override // com.android.systemui.screenshot.ScreenshotShelfViewProxy.ScreenshotViewCallback
            public final void onDismiss() throws RemoteException {
                String str = ScreenshotController.TAG;
                this.this$0.finishDismiss$1();
            }

            @Override // com.android.systemui.screenshot.ScreenshotShelfViewProxy.ScreenshotViewCallback
            public final void onTouchOutside() {
                View viewPeekDecorView;
                ScreenshotWindow screenshotWindow = this.this$0.window;
                WindowManager.LayoutParams layoutParams = screenshotWindow.params;
                int i = layoutParams.flags;
                int i2 = i | 8;
                layoutParams.flags = i2;
                if (i2 == i || (viewPeekDecorView = screenshotWindow.window.peekDecorView()) == null || !viewPeekDecorView.isAttachedToWindow()) {
                    return;
                }
                screenshotWindow.windowManager.updateViewLayout(viewPeekDecorView, screenshotWindow.params);
            }

            @Override // com.android.systemui.screenshot.ScreenshotShelfViewProxy.ScreenshotViewCallback
            public final void onUserInteraction() {
                this.this$0.screenshotHandler.resetTimeout();
            }
        };
        screenshotWindowCreate.window.setContentView(screenshotShelfView);
        ActionExecutor actionExecutorCreate = factory5.create(screenshotWindowCreate.window, proxy, new Function0() { // from class: com.android.systemui.screenshot.ScreenshotController$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() throws RemoteException {
                String str = ScreenshotController.TAG;
                this.f$0.finishDismiss$1();
                return Unit.INSTANCE;
            }
        });
        this.actionExecutor = actionExecutorCreate;
        this.actionsController = factory4.getController(actionExecutorCreate);
        ?? r6 = new BroadcastReceiver() { // from class: com.android.systemui.screenshot.ScreenshotController.3
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context3, Intent intent) {
                if ("com.android.systemui.COPY".equals(intent.getAction())) {
                    ScreenshotController.this.viewProxy.requestDismissal(ScreenshotEvent.SCREENSHOT_DISMISSED_OTHER, null);
                }
            }
        };
        this.copyBroadcastReceiver = r6;
        broadcastDispatcher.registerReceiver(r6, new IntentFilter("com.android.systemui.COPY"), null, null, 4, "com.android.systemui.permission.SELF");
    }

    public static final void access$detachSemScreenshotLayoutToWindow(ScreenshotController screenshotController) {
        screenshotController.getClass();
        Log.i(TAG, "detachSemScreenshotLayoutToWindow");
        Presentation presentation = screenshotController.presentation;
        if (presentation == null) {
            SemScreenshotLayout semScreenshotLayout = screenshotController.semScreenshotLayout;
            if (semScreenshotLayout == null) {
                semScreenshotLayout = null;
            }
            if (semScreenshotLayout.isAttachedToWindow()) {
                WindowManager windowManager = screenshotController.displayContextWindowManager;
                if (windowManager == null) {
                    windowManager = null;
                }
                SemScreenshotLayout semScreenshotLayout2 = screenshotController.semScreenshotLayout;
                windowManager.removeViewImmediate(semScreenshotLayout2 != null ? semScreenshotLayout2 : null);
                return;
            }
            return;
        }
        presentation.dismiss();
        screenshotController.presentation = null;
        SemScreenshotLayout semScreenshotLayout3 = screenshotController.semScreenshotLayout;
        if (semScreenshotLayout3 == null) {
            semScreenshotLayout3 = null;
        }
        if (semScreenshotLayout3.getParent() != null) {
            SemScreenshotLayout semScreenshotLayout4 = screenshotController.semScreenshotLayout;
            if (semScreenshotLayout4 == null) {
                semScreenshotLayout4 = null;
            }
            ViewGroup viewGroup = (ViewGroup) semScreenshotLayout4.getParent();
            SemScreenshotLayout semScreenshotLayout5 = screenshotController.semScreenshotLayout;
            viewGroup.removeView(semScreenshotLayout5 != null ? semScreenshotLayout5 : null);
        }
    }

    public static final void access$logScreenshotResultStatus(ScreenshotController screenshotController, Uri uri, UserHandle userHandle) {
        if (uri == null) {
            screenshotController.uiEventLogger.log(ScreenshotEvent.SCREENSHOT_NOT_SAVED, 0, screenshotController.packageName);
            screenshotController.notificationController.notifyScreenshotError(R.string.screenshot_failed_to_save_text);
        } else {
            screenshotController.uiEventLogger.log(ScreenshotEvent.SCREENSHOT_SAVED, 0, screenshotController.packageName);
            if (screenshotController.userManager.isManagedProfile(userHandle.getIdentifier())) {
                screenshotController.uiEventLogger.log(ScreenshotEvent.SCREENSHOT_SAVED_TO_WORK_PROFILE, 0, screenshotController.packageName);
            }
        }
    }

    public static boolean isFormatPNG(Context context) {
        String stringForUser = Settings.System.getStringForUser(context.getContentResolver(), "smart_capture_screenshot_format", 0);
        Log.i(TAG, AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("screenshotFormatValue : ", stringForUser));
        return stringForUser != null && stringForUser.equals("PNG");
    }

    public final void attachSemScreenshotLayoutToWindow() {
        synchronized (shutterEffectLock) {
            Log.i(TAG, "attachSemScreenshotLayoutToWindow");
            ScreenCaptureHelper screenCaptureHelper = this.screenCaptureHelper;
            screenCaptureHelper.getClass();
            WindowManager.LayoutParams layoutParams = ScreenshotViewUtils.getLayoutParams(screenCaptureHelper);
            try {
                layoutParams.setTitle("ScreenshotAnimation");
                SemScreenshotLayout semScreenshotLayout = this.semScreenshotLayout;
                SemScreenshotLayout semScreenshotLayout2 = null;
                if (semScreenshotLayout == null) {
                    semScreenshotLayout = null;
                }
                ScreenCaptureHelper screenCaptureHelper2 = this.screenCaptureHelper;
                screenCaptureHelper2.getClass();
                float f = screenCaptureHelper2.screenDegrees;
                ImageView imageView = (ImageView) semScreenshotLayout.findViewById(R.id.white_bg);
                semScreenshotLayout.mScreenshotImageView = imageView;
                imageView.setVisibility(4);
                semScreenshotLayout.mScreenDegrees = f;
                ScreenCaptureHelper screenCaptureHelper3 = this.screenCaptureHelper;
                screenCaptureHelper3.getClass();
                int i = screenCaptureHelper3.builtInDisplayId;
                if (ScreenshotUtils.isSubDisplayCapture(i)) {
                    SemScreenshotLayout semScreenshotLayout3 = this.semScreenshotLayout;
                    if (semScreenshotLayout3 == null) {
                        semScreenshotLayout3 = null;
                    }
                    ScreenCaptureHelper screenCaptureHelper4 = this.screenCaptureHelper;
                    screenCaptureHelper4.getClass();
                    semScreenshotLayout3.addCaptureEffectViewInLayout(screenCaptureHelper4);
                    Display display = ScreenshotUtils.getDisplay(i, this.context);
                    if (display == null) {
                        return;
                    }
                    Presentation presentation = new Presentation(this.context, display, 2132018761, VolteConstants.ErrorCode.REG_SUBSCRIBED);
                    this.presentation = presentation;
                    Window window = presentation.getWindow();
                    this.screenCaptureHelper.getClass();
                    window.getClass();
                    window.addFlags(69207432);
                    window.getAttributes().layoutInDisplayCutoutMode = 3;
                    View decorView = window.getDecorView();
                    decorView.setSystemUiVisibility(decorView.getSystemUiVisibility() | 1026);
                    SemScreenshotLayout semScreenshotLayout4 = this.semScreenshotLayout;
                    if (semScreenshotLayout4 != null) {
                        semScreenshotLayout2 = semScreenshotLayout4;
                    }
                    window.addContentView(semScreenshotLayout2, layoutParams);
                    window.setBackgroundDrawable(new ColorDrawable(0));
                    Presentation presentation2 = this.presentation;
                    presentation2.getClass();
                    presentation2.show();
                } else {
                    SemScreenshotLayout semScreenshotLayout5 = this.semScreenshotLayout;
                    if (semScreenshotLayout5 == null) {
                        semScreenshotLayout5 = null;
                    }
                    ScreenCaptureHelper screenCaptureHelper5 = this.screenCaptureHelper;
                    screenCaptureHelper5.getClass();
                    semScreenshotLayout5.addCaptureEffectViewInLayout(screenCaptureHelper5);
                    ScreenCaptureHelper screenCaptureHelper6 = this.screenCaptureHelper;
                    screenCaptureHelper6.getClass();
                    Context context = screenCaptureHelper6.displayContext;
                    context.getClass();
                    WindowManager windowManager = (WindowManager) context.getSystemService("window");
                    this.displayContextWindowManager = windowManager;
                    if (windowManager == null) {
                        windowManager = null;
                    }
                    SemScreenshotLayout semScreenshotLayout6 = this.semScreenshotLayout;
                    if (semScreenshotLayout6 != null) {
                        semScreenshotLayout2 = semScreenshotLayout6;
                    }
                    windowManager.addView(semScreenshotLayout2, layoutParams);
                }
                Unit unit = Unit.INSTANCE;
            } catch (IllegalStateException e) {
                Log.e(TAG, "attachSemScreenshotLayoutToWindow()", e);
            }
        }
    }

    public final void finishDismiss$1() throws RemoteException {
        Log.d(TAG, "finishDismiss");
        this.actionsController.currentScreenshotId = null;
        this.scrollCaptureExecutor.close();
        ArrayList arrayList = (ArrayList) this.currentRequestCallbacks;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            Messenger messenger = ((TakeScreenshotService.RequestCallbackImpl) ((TakeScreenshotService.RequestCallback) obj)).mReplyTo;
            boolean z = TakeScreenshotService.sConfigured;
            try {
                messenger.send(Message.obtain((Handler) null, 2));
            } catch (RemoteException e) {
                Log.d("Screenshot", "ignored remote exception", e);
            }
        }
        ((ArrayList) this.currentRequestCallbacks).clear();
        this.viewProxy.reset();
        removeWindow();
        this.screenshotHandler.removeMessages(2);
        this.isScreenshotDismissed = true;
    }

    /* JADX WARN: Can't wrap try/catch for region: R(24:26|(23:28|(4:380|33|(1:41)(1:37)|42)|47|(3:49|373|50)|53|(4:55|(3:57|(1:59)(1:60)|61)(0)|102|(0)(0))(1:63)|62|(1:65)|66|366|67|371|68|382|69|378|70|(1:72)(1:73)|368|74|(8:83|388|84|94|(1:96)(1:97)|98|(1:100)|101)(10:76|(1:78)(9:79|(1:81)|388|84|94|(0)(0)|98|(0)|101)|83|388|84|94|(0)(0)|98|(0)|101)|102|(0)(0))(1:46)|45|47|(0)|53|(0)(0)|62|(0)|66|366|67|371|68|382|69|378|70|(0)(0)|368|74|(0)(0)|102|(0)(0)) */
    /* JADX WARN: Code restructure failed: missing block: B:87:0x0228, code lost:
    
        r1 = null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:88:0x022a, code lost:
    
        r1 = null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:89:0x022b, code lost:
    
        r7 = null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:90:0x022d, code lost:
    
        r1 = null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:91:0x022e, code lost:
    
        r6 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:92:0x0230, code lost:
    
        r1 = null;
        r5 = null;
     */
    /* JADX WARN: Removed duplicated region for block: B:100:0x0254  */
    /* JADX WARN: Removed duplicated region for block: B:104:0x0265  */
    /* JADX WARN: Removed duplicated region for block: B:106:0x0279  */
    /* JADX WARN: Removed duplicated region for block: B:167:0x03ef  */
    /* JADX WARN: Removed duplicated region for block: B:178:0x0432  */
    /* JADX WARN: Removed duplicated region for block: B:179:0x0435  */
    /* JADX WARN: Removed duplicated region for block: B:189:0x04ce  */
    /* JADX WARN: Removed duplicated region for block: B:190:0x04d2  */
    /* JADX WARN: Removed duplicated region for block: B:193:0x04eb  */
    /* JADX WARN: Removed duplicated region for block: B:198:0x0521  */
    /* JADX WARN: Removed duplicated region for block: B:204:0x0530  */
    /* JADX WARN: Removed duplicated region for block: B:208:0x0550  */
    /* JADX WARN: Removed duplicated region for block: B:215:0x05a1  */
    /* JADX WARN: Removed duplicated region for block: B:218:0x05ab  */
    /* JADX WARN: Removed duplicated region for block: B:221:0x05b7  */
    /* JADX WARN: Removed duplicated region for block: B:224:0x05bc  */
    /* JADX WARN: Removed duplicated region for block: B:225:0x05bf  */
    /* JADX WARN: Removed duplicated region for block: B:227:0x05c2  */
    /* JADX WARN: Removed duplicated region for block: B:229:0x05ca A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:234:0x05df  */
    /* JADX WARN: Removed duplicated region for block: B:236:0x05e3  */
    /* JADX WARN: Removed duplicated region for block: B:239:0x05fa  */
    /* JADX WARN: Removed duplicated region for block: B:244:0x0619  */
    /* JADX WARN: Removed duplicated region for block: B:248:0x0625  */
    /* JADX WARN: Removed duplicated region for block: B:254:0x0644  */
    /* JADX WARN: Removed duplicated region for block: B:257:0x0669  */
    /* JADX WARN: Removed duplicated region for block: B:262:0x06a7  */
    /* JADX WARN: Removed duplicated region for block: B:265:0x06b7  */
    /* JADX WARN: Removed duplicated region for block: B:268:0x06eb  */
    /* JADX WARN: Removed duplicated region for block: B:270:0x06f3  */
    /* JADX WARN: Removed duplicated region for block: B:292:0x0753  */
    /* JADX WARN: Removed duplicated region for block: B:293:0x075c  */
    /* JADX WARN: Removed duplicated region for block: B:296:0x0771  */
    /* JADX WARN: Removed duplicated region for block: B:302:0x07a5  */
    /* JADX WARN: Removed duplicated region for block: B:303:0x07a8  */
    /* JADX WARN: Removed duplicated region for block: B:306:0x07be  */
    /* JADX WARN: Removed duplicated region for block: B:307:0x07c1  */
    /* JADX WARN: Removed duplicated region for block: B:310:0x07dd  */
    /* JADX WARN: Removed duplicated region for block: B:313:0x07eb  */
    /* JADX WARN: Removed duplicated region for block: B:316:0x07f0  */
    /* JADX WARN: Removed duplicated region for block: B:319:0x07f5  */
    /* JADX WARN: Removed duplicated region for block: B:322:0x0844  */
    /* JADX WARN: Removed duplicated region for block: B:325:0x0849  */
    /* JADX WARN: Removed duplicated region for block: B:328:0x084e  */
    /* JADX WARN: Removed duplicated region for block: B:331:0x0870  */
    /* JADX WARN: Removed duplicated region for block: B:332:0x0873  */
    /* JADX WARN: Removed duplicated region for block: B:335:0x0880  */
    /* JADX WARN: Removed duplicated region for block: B:350:0x08be  */
    /* JADX WARN: Removed duplicated region for block: B:353:0x090b  */
    /* JADX WARN: Removed duplicated region for block: B:361:0x0926  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x00e4  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x011b  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x0154  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x0184  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x018a  */
    /* JADX WARN: Removed duplicated region for block: B:72:0x01d6  */
    /* JADX WARN: Removed duplicated region for block: B:73:0x01d8  */
    /* JADX WARN: Removed duplicated region for block: B:76:0x0204 A[Catch: RemoteException -> 0x0233, TryCatch #1 {RemoteException -> 0x0233, blocks: (B:74:0x01d9, B:76:0x0204, B:78:0x020e, B:79:0x0215, B:81:0x021d), top: B:368:0x01d9 }] */
    /* JADX WARN: Removed duplicated region for block: B:83:0x0222  */
    /* JADX WARN: Removed duplicated region for block: B:96:0x0241  */
    /* JADX WARN: Removed duplicated region for block: B:97:0x0246  */
    /* JADX WARN: Type inference failed for: r0v70, types: [com.samsung.android.app.smartcapture.screenshot.lib.RemoteScreenshotInterface$1] */
    @Override // com.android.systemui.screenshot.ScreenshotHandler
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void handleScreenshot(final ScreenshotData screenshotData, final Consumer consumer, TakeScreenshotService.RequestCallback requestCallback) throws JSONException, InterruptedException, Resources.NotFoundException, PackageManager.NameNotFoundException, SecurityException, RemoteException, NumberFormatException {
        int i;
        int i2;
        Context context;
        int i3;
        String str;
        String str2;
        int i4;
        boolean z;
        int failedReason;
        Bitmap bitmapCaptureDisplay;
        Context context2;
        float f;
        int i5;
        int i6;
        Cursor cursorQuery;
        Bitmap bitmap;
        String str3;
        SmartClipDataExtractor.WebData webData;
        List list;
        final View decorView;
        ScreenshotShelfViewProxy screenshotShelfViewProxy;
        ScreenCaptureHelper screenCaptureHelper;
        String topMostApplicationPackage;
        String strReplaceAll;
        String strM;
        String strM2;
        String[] screenshotSaveInfo;
        String string;
        String str4;
        String str5;
        final RemoteScreenshotInterface remoteScreenshotInterface;
        boolean z2;
        PackageManager packageManager;
        String str6;
        String str7;
        String str8;
        String str9;
        String str10;
        String str11;
        String str12;
        WindowContext windowContext;
        String strFlattenToShortString;
        ScreenCaptureHelper screenCaptureHelper2;
        List<ActivityManager.RunningTaskInfo> runningTasks;
        Context context3;
        String string2;
        ScreenCaptureHelper screenCaptureHelper3;
        SmartClipDataExtractor.WebData webData2;
        int mode;
        String value;
        String str13;
        Assert.isMainThread();
        this.screenshotHandler.resetTimeout();
        SemScreenshotResult semScreenshotResult = new SemScreenshotResult(null, -1, null, null);
        String strM3 = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(screenshotData.type, "handleScreenshot: screenshot.getType()=");
        String str14 = TAG;
        Log.i(str14, strM3);
        int i7 = screenshotData.type;
        if (i7 == 3) {
            boolean z3 = screenshotData.secureLayer;
            boolean z4 = screenshotData.disableCapture;
            if (z4 || z3) {
                Log.e(str14, "handleScreenshot: disable screenshot on managed profile., disableCapture=" + z4 + ", secureLayer=" + z3);
                showScreenshotErrorMessage(new SemScreenshotResult(null, z4 ? 32 : 16, null, null));
                ((TakeScreenshotService.RequestCallbackImpl) requestCallback).reportError();
                return;
            }
        }
        if (i7 == 1 || i7 == 2 || i7 == 100) {
            i = 2;
        } else {
            i = 2;
            if (i7 != 101) {
                str = " ";
                str2 = str14;
            }
            bitmap = screenshotData.bitmap;
            if (bitmap == null) {
                Log.e(str2, "handleScreenshot: Screenshot bitmap was null");
                showScreenshotErrorMessage(semScreenshotResult);
                ((TakeScreenshotService.RequestCallbackImpl) requestCallback).reportError();
                return;
            }
            String str15 = str2;
            this.screenBitmap = bitmap;
            String str16 = this.packageName;
            this.packageName = screenshotData.getPackageNameString();
            Log.d(str15, "on getWebData()");
            Trace.beginSection("ScreenshotController_getWebData");
            this.webData = null;
            Log.d(str15, "on getSmartClipWebData()");
            try {
                new SemRemoteAppDataExtractionManager(this.context);
                mode = new SemMultiWindowManager().getMode();
                ListPopupWindow$$ExternalSyntheticOutline0.m(mode, "isMultiWindowStyleAppExist : mode = ", "Screenshot");
            } catch (Exception e) {
                str3 = str;
                Log.e("Screenshot", "isSupportSmartClip, exxception occurred : " + e.toString());
                Log.e("Screenshot", "canExtractWebData : SmartClip is not supported");
            }
            if (mode == 0) {
                WindowContext windowContext2 = this.context;
                ScreenCaptureHelper screenCaptureHelper4 = this.screenCaptureHelper;
                screenCaptureHelper4.getClass();
                int i8 = screenCaptureHelper4.screenWidth;
                ScreenCaptureHelper screenCaptureHelper5 = this.screenCaptureHelper;
                screenCaptureHelper5.getClass();
                int i9 = i8 / 2;
                int i10 = screenCaptureHelper5.screenHeight / 2;
                SemSmartClipDataRepository smartClipDataByScreenRect = ((SpenGestureManager) windowContext2.getSystemService("spengestureservice")).getSmartClipDataByScreenRect(new Rect(i9, i10, i9 + 1, i10 + 1), (IBinder) null, 1, 1);
                if (smartClipDataByScreenRect == null) {
                    Log.e("Screenshot", "getWebData : Failed to extract the SmartClip data");
                    str3 = str;
                } else {
                    Rect contentRect = smartClipDataByScreenRect.getContentRect();
                    if (contentRect != null) {
                        StringBuilder sb = new StringBuilder("getWebData : content Rect w=");
                        sb.append(contentRect.width());
                        sb.append(", h=");
                        sb.append(contentRect.height());
                        str3 = str;
                        sb.append(str3);
                        sb.append(contentRect);
                        Log.d("Screenshot", sb.toString());
                    } else {
                        str3 = str;
                    }
                    SemSmartClipMetaTagArray metaTag = smartClipDataByScreenRect.getMetaTag("url");
                    if (metaTag.size() > 0) {
                        int size = metaTag.size();
                        int i11 = 0;
                        while (true) {
                            if (i11 >= size) {
                                value = null;
                                break;
                            }
                            Object obj = metaTag.get(i11);
                            i11++;
                            value = ((SemSmartClipMetaTag) obj).getValue();
                            String lowerCase = value.toLowerCase();
                            if (lowerCase.startsWith("http://") || lowerCase.startsWith("https://")) {
                                break;
                            }
                        }
                        SmartClipDataExtractor.WebData webData3 = new SmartClipDataExtractor.WebData(value, smartClipDataByScreenRect.getAppPackageName());
                        String str17 = webData3.mUrl;
                        if (str17 == null || (str13 = webData3.mAppPkgName) == null) {
                            Log.e("Screenshot", "isValidWebData : url or appPkgName is null");
                        } else {
                            String lowerCase2 = str17.toLowerCase();
                            if (lowerCase2.startsWith("http://") || lowerCase2.startsWith("https://")) {
                                String[] strArr = SmartClipDataExtractor.mWhiteWebAppList;
                                for (int i12 = 0; i12 < 3; i12++) {
                                    if (strArr[i12].equals(str13)) {
                                        webData = webData3;
                                        break;
                                    }
                                }
                                Log.e("Screenshot", "isValidWebData : Not white app");
                            } else {
                                Log.e("Screenshot", "isValidWebData : Not valid url");
                            }
                        }
                        Log.e("Screenshot", "getWebData : Invalid web data");
                    }
                    this.webData = webData;
                    if (webData == null) {
                        int i13 = screenshotData.taskId;
                        if (i13 < 0) {
                            webData2 = null;
                            this.webData = webData2;
                        } else {
                            Log.d(str15, "on getAssistContentWebData");
                            final CompletableFuture completableFuture = new CompletableFuture();
                            AssistContentRequester.Callback callback = new AssistContentRequester.Callback() { // from class: com.android.systemui.screenshot.ScreenshotController$getAssistContentWebData$1
                                @Override // com.android.systemui.screenshot.AssistContentRequester.Callback
                                public final void onAssistContentAvailable(AssistContent assistContent) {
                                    if (assistContent == null || assistContent.getWebUri() == null) {
                                        completableFuture.complete(null);
                                    } else {
                                        Log.d(ScreenshotController.TAG, "handleScreenshot: webData is extracted from AssistContent");
                                        completableFuture.complete(new SmartClipDataExtractor.WebData(assistContent.getWebUri().toString(), screenshotData.getPackageNameString()));
                                    }
                                }
                            };
                            AssistContentRequester assistContentRequester = this.assistContentRequester;
                            assistContentRequester.mSystemInteractionExecutor.execute(new AssistContentRequester$$ExternalSyntheticLambda0(assistContentRequester, callback, i13));
                            try {
                                webData2 = (SmartClipDataExtractor.WebData) completableFuture.get(500L, TimeUnit.MILLISECONDS);
                            } catch (Exception e2) {
                                Log.e(str15, "exception on getAssistContentWebData", e2);
                            }
                            this.webData = webData2;
                        }
                    }
                    Trace.endSection();
                    ScreenshotDetectionController screenshotDetectionController = this.screenshotDetectionController;
                    screenshotDetectionController.getClass();
                    if (screenshotData.source == 3) {
                        list = EmptyList.INSTANCE;
                    } else {
                        List listNotifyScreenshotListeners = screenshotDetectionController.windowManager.notifyScreenshotListeners(0);
                        listNotifyScreenshotListeners.getClass();
                        List<ComponentName> list2 = listNotifyScreenshotListeners;
                        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list2, 10));
                        for (ComponentName componentName : list2) {
                            CharSequence charSequenceLoadLabel = screenshotDetectionController.packageManager.getActivityInfo(componentName, PackageManager.ComponentInfoFlags.of(512L)).loadLabel(screenshotDetectionController.packageManager);
                            if (charSequenceLoadLabel.length() == 0) {
                                charSequenceLoadLabel = screenshotDetectionController.packageManager.getActivityInfo(componentName, PackageManager.ComponentInfoFlags.of(512L)).packageName;
                            }
                            arrayList.add(charSequenceLoadLabel);
                        }
                        list = arrayList;
                    }
                    this.notifiedApps = list;
                    ((ArrayList) this.currentRequestCallbacks).add(requestCallback);
                    Intent intent = new Intent("com.android.systemui.SCREENSHOT");
                    BroadcastSender broadcastSender = this.broadcastSender;
                    broadcastSender.getClass();
                    broadcastSender.sendInBackground(String.valueOf(intent), new BroadcastSender$$ExternalSyntheticLambda0(broadcastSender, intent, 0));
                    int i14 = this.context.getResources().getConfiguration().orientation;
                    bitmap.setHasAlpha(false);
                    bitmap.prepareToDraw();
                    final Runnable runnable = new Runnable() { // from class: com.android.systemui.screenshot.ScreenshotController$prepareViewForNewScreenshot$1
                        @Override // java.lang.Runnable
                        public final void run() {
                            AnnouncementResolver announcementResolver = this.this$0.announcementResolver;
                            int identifier = screenshotData.userHandle.getIdentifier();
                            final ScreenshotController screenshotController = this.this$0;
                            Consumer consumer2 = new Consumer() { // from class: com.android.systemui.screenshot.ScreenshotController$prepareViewForNewScreenshot$1.1
                                @Override // java.util.function.Consumer
                                public final void accept(Object obj2) {
                                    screenshotController.viewProxy.view.announceForAccessibility((String) obj2);
                                }
                            };
                            announcementResolver.getClass();
                            CoroutineTracingKt.launchTraced$default(announcementResolver.mainScope, null, null, new AnnouncementResolver.AnonymousClass2(consumer2, announcementResolver, identifier, null), 7);
                        }
                    };
                    final ScreenshotWindow screenshotWindow = this.window;
                    decorView = screenshotWindow.window.getDecorView();
                    if (decorView.isAttachedToWindow()) {
                        runnable.run();
                    } else {
                        decorView.getViewTreeObserver().addOnWindowAttachListener(new ViewTreeObserver.OnWindowAttachListener() { // from class: com.android.systemui.screenshot.ScreenshotWindow$whenWindowAttached$1
                            @Override // android.view.ViewTreeObserver.OnWindowAttachListener
                            public final void onWindowAttached() {
                                screenshotWindow.getClass();
                                decorView.getViewTreeObserver().removeOnWindowAttachListener(this);
                                runnable.run();
                            }

                            @Override // android.view.ViewTreeObserver.OnWindowAttachListener
                            public final void onWindowDetached() {
                            }
                        });
                    }
                    screenshotShelfViewProxy = this.viewProxy;
                    screenshotShelfViewProxy.reset();
                    if (screenshotShelfViewProxy.view.isAttachedToWindow() && !screenshotShelfViewProxy.isDismissing) {
                        this.uiEventLogger.log(ScreenshotEvent.SCREENSHOT_REENTERED, 0, str16);
                    }
                    screenshotShelfViewProxy.packageName = this.packageName;
                    ScreenshotActionsController screenshotActionsController = this.actionsController;
                    screenshotActionsController.getClass();
                    final UUID uuidRandomUUID = UUID.randomUUID();
                    screenshotActionsController.currentScreenshotId = uuidRandomUUID;
                    Map map = screenshotActionsController.actionProviders;
                    uuidRandomUUID.getClass();
                    map.put(uuidRandomUUID, ((DaggerReferenceGlobalRootComponent.ReferenceSysUIComponentImpl.SwitchingProvider.AnonymousClass144) screenshotActionsController.actionsProviderFactory).create(uuidRandomUUID, screenshotData, screenshotActionsController.actionExecutor, screenshotActionsController.new ActionsCallback(uuidRandomUUID)));
                    screenCaptureHelper = this.screenCaptureHelper;
                    if (screenCaptureHelper != null && screenCaptureHelper.screenCaptureOrigin == 5) {
                        screenCaptureHelper3 = this.screenCaptureHelper;
                        if (screenCaptureHelper3 != null || (bundle = screenCaptureHelper3.captureSharedBundle) == null) {
                            Bundle bundle = null;
                        }
                        this.bixbyShareController = new BixbyShareController(bundle);
                    }
                    ZonedDateTime zonedDateTimeNow = ZonedDateTime.now(ZoneId.systemDefault());
                    zonedDateTimeNow.getClass();
                    ScreenCaptureHelper screenCaptureHelper6 = this.screenCaptureHelper;
                    screenCaptureHelper6.getClass();
                    Context context4 = screenCaptureHelper6.displayContext;
                    topMostApplicationPackage = ScreenshotUtils.getTopMostApplicationPackage(context4);
                    if (topMostApplicationPackage != null) {
                        PackageManager packageManager2 = context4.getPackageManager();
                        try {
                            ApplicationInfo applicationInfo = packageManager2.getApplicationInfo(topMostApplicationPackage, 128);
                            Resources resourcesForApplication = packageManager2.getResourcesForApplication(applicationInfo);
                            Configuration configuration = new Configuration();
                            configuration.locale = new Locale("en");
                            resourcesForApplication.updateConfiguration(configuration, context4.getResources().getDisplayMetrics());
                            string2 = resourcesForApplication.getString(applicationInfo.labelRes);
                        } catch (PackageManager.NameNotFoundException | Resources.NotFoundException e3) {
                            e3.printStackTrace();
                            string2 = "";
                        }
                        if (string2 != null) {
                            strReplaceAll = string2.replaceAll("[^\\p{ASCII}]", "").replaceAll(System.getProperty("line.separator"), str3).replaceAll("[\\\\/?%*:|\"<>.]", "");
                            KeyguardPluginControllerImpl$$ExternalSyntheticOutline0.m("getTopMostApplicationName() : ", strReplaceAll, "Screenshot");
                        } else {
                            Log.d("Screenshot", "getTopMostApplicationName() appName : null");
                            strReplaceAll = null;
                        }
                    }
                    ScreenCaptureHelper screenCaptureHelper7 = this.screenCaptureHelper;
                    Object systemService = (screenCaptureHelper7 == null || (context3 = screenCaptureHelper7.displayContext) == null) ? null : context3.getSystemService("sem_statusbar");
                    SemStatusBarManager semStatusBarManager = systemService instanceof SemStatusBarManager ? (SemStatusBarManager) systemService : null;
                    if ((strReplaceAll != null && systemAppList.contains(strReplaceAll)) || (semStatusBarManager != null && semStatusBarManager.isPanelExpanded())) {
                        ScreenCaptureHelper screenCaptureHelper8 = this.screenCaptureHelper;
                        screenCaptureHelper8.getClass();
                        strReplaceAll = ScreenshotUtils.isSubDisplayCapture(screenCaptureHelper8.capturedDisplayId) ? "One UI Cover Home" : "One UI Home";
                    }
                    strM = AnimatorInflaterCompat$$ExternalSyntheticOutline0.m("Screenshot_", String.format("%1$tY%<tm%<td_%<tH%<tM%<tS", zonedDateTimeNow), "_", strReplaceAll);
                    if (strReplaceAll != null && strReplaceAll.length() == 0) {
                        strM = strM.substring(0, strM.length() - 1);
                    }
                    this.imageDisplayName = strM;
                    if (isFormatPNG(this.context)) {
                        String str18 = this.imageDisplayName;
                        if (str18 == null) {
                            str18 = null;
                        }
                        strM2 = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(str18, ".png");
                    } else {
                        String str19 = this.imageDisplayName;
                        if (str19 == null) {
                            str19 = null;
                        }
                        strM2 = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(str19, ".jpg");
                    }
                    this.imageFileName = strM2;
                    screenshotSaveInfo = ScreenshotUtils.getScreenshotSaveInfo(this.context);
                    if (screenshotSaveInfo[0].equals("external_primary")) {
                        string = Environment.getExternalStorageDirectory().toString();
                        if (!screenshotSaveInfo[1].isEmpty()) {
                            StringBuilder sbM = PopulateViewStructure_androidKt$$ExternalSyntheticOutline0.m(string);
                            sbM.append(File.separator);
                            sbM.append(screenshotSaveInfo[1]);
                            string = sbM.toString();
                        }
                    } else {
                        StringBuilder sb2 = new StringBuilder();
                        String str20 = File.separator;
                        sb2.append(str20);
                        sb2.append("storage");
                        sb2.append(str20);
                        sb2.append(screenshotSaveInfo[0]);
                        string = sb2.toString();
                        if (!screenshotSaveInfo[1].isEmpty()) {
                            StringBuilder sbM2 = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(string, str20);
                            sbM2.append(screenshotSaveInfo[1]);
                            string = sbM2.toString();
                        }
                    }
                    File file = new File(string);
                    str4 = this.imageFileName;
                    if (str4 == null) {
                        str4 = null;
                    }
                    this.imageFilePath = new File(file, str4).getAbsolutePath();
                    str5 = this.imageDisplayName;
                    if (str5 == null) {
                        str5 = null;
                    }
                    this.thumbnailImageFilePath = new File(file, ContentInViewNode$Request$$ExternalSyntheticOutline0.m("thumbnail_", str5, ".jpg")).getAbsolutePath();
                    long jCurrentTimeMillis = System.currentTimeMillis();
                    remoteScreenshotInterface = new RemoteScreenshotInterface();
                    this.screenshotInterface = remoteScreenshotInterface;
                    WindowContext windowContext3 = this.context;
                    ScreenshotController$startCaptureAppRemoteServiceConnection$1 screenshotController$startCaptureAppRemoteServiceConnection$1 = new ScreenshotController$startCaptureAppRemoteServiceConnection$1(jCurrentTimeMillis, this);
                    Log.d("[ScrCap]_RemoteScreenshotInterface", "connect");
                    final long jCurrentTimeMillis2 = System.currentTimeMillis();
                    if (remoteScreenshotInterface.mService != null) {
                        Log.e("[ScrCap]_RemoteScreenshotInterface", "connect : Already connected");
                    } else if (remoteScreenshotInterface.mConnection != null) {
                        Log.e("[ScrCap]_RemoteScreenshotInterface", "connect : Connection already requested");
                    } else {
                        remoteScreenshotInterface.mContext = windowContext3.getApplicationContext();
                        remoteScreenshotInterface.mConnectionListener = screenshotController$startCaptureAppRemoteServiceConnection$1;
                        remoteScreenshotInterface.mConnection = new ServiceConnection() { // from class: com.samsung.android.app.smartcapture.screenshot.lib.RemoteScreenshotInterface.1
                            public final /* synthetic */ long val$connStartTime;

                            public AnonymousClass1(final long jCurrentTimeMillis22) {
                                j = jCurrentTimeMillis22;
                            }

                            @Override // android.content.ServiceConnection
                            public final void onServiceConnected(ComponentName componentName2, IBinder iBinder) {
                                IScreenshotService proxy;
                                Log.d("[ScrCap]_RemoteScreenshotInterface", "onServiceConnected : Service connected. Elapsed = " + (System.currentTimeMillis() - j) + "ms");
                                RemoteScreenshotInterface remoteScreenshotInterface2 = RemoteScreenshotInterface.this;
                                int i15 = IScreenshotService.Stub.$r8$clinit;
                                if (iBinder == null) {
                                    proxy = null;
                                } else {
                                    IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.samsung.android.app.smartcapture.screenshot.lib.IScreenshotService");
                                    proxy = (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof IScreenshotService)) ? new IScreenshotService.Stub.Proxy(iBinder) : (IScreenshotService) iInterfaceQueryLocalInterface;
                                }
                                remoteScreenshotInterface2.mService = proxy;
                                ScreenshotController$startCaptureAppRemoteServiceConnection$1 screenshotController$startCaptureAppRemoteServiceConnection$12 = RemoteScreenshotInterface.this.mConnectionListener;
                                if (screenshotController$startCaptureAppRemoteServiceConnection$12 != null) {
                                    screenshotController$startCaptureAppRemoteServiceConnection$12.onConnectionResult(true);
                                }
                            }

                            @Override // android.content.ServiceConnection
                            public final void onServiceDisconnected(ComponentName componentName2) {
                                Log.d("[ScrCap]_RemoteScreenshotInterface", "onServiceDisconnected : Service disconnected");
                                RemoteScreenshotInterface.this.mService = null;
                            }
                        };
                        Intent intent2 = new Intent();
                        try {
                            packageManager = remoteScreenshotInterface.mContext.getPackageManager();
                        } catch (PackageManager.NameNotFoundException e4) {
                            Log.e("[ScrCap]_RemoteScreenshotInterface", "isPackageAvailable : not available. e=" + e4);
                        }
                        if (packageManager == null) {
                            Log.e("[ScrCap]_RemoteScreenshotInterface", "isPackageAvailable : Failed to get package manager!");
                            z2 = false;
                            if (z2) {
                                Log.d("[ScrCap]_RemoteScreenshotInterface", "ScrollCapture will be binded.");
                                intent2.setClassName("com.samsung.android.app.smartcapture.screenshot", "com.samsung.android.app.smartcapture.screenshot.core.ScreenshotRemoteService");
                            } else {
                                Log.d("[ScrCap]_RemoteScreenshotInterface", "SmartCapture will be binded.");
                                intent2.setClassName("com.samsung.android.app.smartcapture", "com.samsung.android.app.smartcapture.screenshot.core.ScreenshotRemoteService");
                            }
                            if (remoteScreenshotInterface.mContext.bindService(intent2, remoteScreenshotInterface.mConnection, 1)) {
                                Log.e("[ScrCap]_RemoteScreenshotInterface", "connect : bindService failed");
                                ScreenshotController$startCaptureAppRemoteServiceConnection$1 screenshotController$startCaptureAppRemoteServiceConnection$12 = remoteScreenshotInterface.mConnectionListener;
                                if (screenshotController$startCaptureAppRemoteServiceConnection$12 != null) {
                                    screenshotController$startCaptureAppRemoteServiceConnection$12.onConnectionResult(false);
                                }
                                remoteScreenshotInterface.mContext = null;
                                remoteScreenshotInterface.mConnection = null;
                                remoteScreenshotInterface.mService = null;
                                remoteScreenshotInterface.mConnectionListener = null;
                            }
                            String lowerCase3 = ScreenshotUtils.getScreenshotSaveInfo(this.context)[0].toLowerCase();
                            String[] screenshotSaveInfo2 = ScreenshotUtils.getScreenshotSaveInfo(this.context);
                            String str21 = screenshotSaveInfo2[1].isEmpty() ? File.separator : screenshotSaveInfo2[1];
                            long epochMilli = zonedDateTimeNow.toInstant().toEpochMilli();
                            long epochSecond = zonedDateTimeNow.toEpochSecond();
                            boolean zIsFormatPNG = isFormatPNG(this.context);
                            String str22 = zIsFormatPNG ? "image/png" : "image/jpeg";
                            Bitmap bitmap2 = this.screenBitmap;
                            bitmap2.getClass();
                            int width = bitmap2.getWidth();
                            Bitmap bitmap3 = this.screenBitmap;
                            bitmap3.getClass();
                            int height = bitmap3.getHeight();
                            str6 = this.imageFilePath;
                            if (str6 == null) {
                                str6 = null;
                            }
                            long length = new File(str6).length();
                            str7 = this.imageFilePath;
                            if (str7 == null) {
                                str7 = null;
                            }
                            str8 = this.imageDisplayName;
                            if (str8 == null) {
                                str8 = null;
                            }
                            str9 = this.imageFileName;
                            if (str9 == null) {
                                str9 = null;
                            }
                            StringBuilder sbM3 = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("setScreenshotMediaData:  volumeName= ", lowerCase3, " relativePath= ", str21, " mImageFilePath= ");
                            MoveResult$$ExternalSyntheticOutline0.m(sbM3, str7, " mImageDisplayName= ", str8, " mImageFileName= ");
                            sbM3.append(str9);
                            sbM3.append(" imageTime= ");
                            sbM3.append(epochMilli);
                            sbM3.append(" dateSeconds $=dateSeconds mimeType= ");
                            sbM3.append(str22);
                            sbM3.append(" imageWidth= ");
                            sbM3.append(width);
                            sbM3.append(" imageHeight= ");
                            sbM3.append(height);
                            sbM3.append(" size= ");
                            sbM3.append(length);
                            Log.i(str15, sbM3.toString());
                            str10 = this.imageFilePath;
                            if (str10 == null) {
                                str10 = null;
                            }
                            str11 = this.imageDisplayName;
                            if (str11 == null) {
                                str11 = null;
                            }
                            str12 = this.imageFileName;
                            if (str12 == null) {
                                str12 = null;
                            }
                            SmartClipDataExtractor.WebData webData4 = this.webData;
                            ImageExporter imageExporter = this.imageExporter;
                            imageExporter.getClass();
                            ImageExporter.mImageFileRelativePath = str21;
                            ImageExporter.mVolumeName = lowerCase3;
                            ImageExporter.mImageFilePath = str10;
                            ImageExporter.mImageDisplayName = str11;
                            ImageExporter.mImageFileName = str12;
                            ImageExporter.mImageTime = epochMilli;
                            ImageExporter.mSecDate = epochSecond;
                            ImageExporter.mMimeType = str22;
                            ImageExporter.mWidth = width;
                            ImageExporter.mHeight = height;
                            ImageExporter.mSize = length;
                            ImageExporter.mScreenshotsWebData = webData4;
                            imageExporter.mCompressFormat = zIsFormatPNG ? Bitmap.CompressFormat.PNG : Bitmap.CompressFormat.JPEG;
                            JSONObject jSONObject = new JSONObject();
                            windowContext = this.context;
                            if (windowContext != null) {
                                try {
                                    runningTasks = ((ActivityManager) windowContext.getSystemService("activity")).getRunningTasks(i);
                                } catch (SecurityException e5) {
                                    Log.e("Screenshot", e5.toString());
                                    runningTasks = null;
                                }
                                if (runningTasks == null || runningTasks.isEmpty()) {
                                    strFlattenToShortString = null;
                                } else {
                                    for (int i15 = 0; i15 < runningTasks.size(); i15++) {
                                        ComponentName componentName2 = runningTasks.get(i15).topActivity;
                                        if (componentName2 != null) {
                                            strFlattenToShortString = componentName2.flattenToShortString();
                                            break;
                                        }
                                    }
                                    strFlattenToShortString = null;
                                }
                            }
                            jSONObject.put("comp", strFlattenToShortString);
                            ImageExporter.mCapturedAppInfo = Base64.encodeToString(jSONObject.toString().getBytes(Charsets.UTF_8), 2);
                            final Consumer consumer2 = new Consumer() { // from class: com.android.systemui.screenshot.ScreenshotController.handleScreenshot.1
                                @Override // java.util.function.Consumer
                                public final void accept(Object obj2) {
                                    boolean z5;
                                    ImageExporter.Result result = (ImageExporter.Result) obj2;
                                    if (result.uri != null) {
                                        this.actionsController.setCompletedScreenshot(uuidRandomUUID, new ScreenshotSavedResult(result.uri, screenshotData.userHandle, result.timestamp));
                                        ScreenshotController screenshotController = this;
                                        BixbyShareController bixbyShareController = screenshotController.bixbyShareController;
                                        if (bixbyShareController == null || !(z5 = bixbyShareController.isBixbyCaptureShared) || screenshotController.isSavingFailed) {
                                            return;
                                        }
                                        WindowContext windowContext4 = screenshotController.context;
                                        Uri uri = result.uri;
                                        boolean zIsFormatPNG2 = ScreenshotController.isFormatPNG(windowContext4);
                                        String str23 = BixbyShareController.TAG;
                                        if (uri == null || !z5) {
                                            Log.e(str23, "isBixbyCaptureShared: " + z5 + " uri: " + uri);
                                            return;
                                        }
                                        Intent intent3 = new Intent("com.samsung.android.systemui.screenshot.SCREENSHOT_URI");
                                        String string3 = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, ContentUris.parseId(uri)).toString();
                                        intent3.putExtra("contentUri", string3);
                                        KeyguardPluginControllerImpl$$ExternalSyntheticOutline0.m("Send broadcast : screenshot contentUri = ", string3, str23);
                                        windowContext4.sendBroadcast(intent3, "com.samsung.android.systemui.screenshot.permission.RECEIVE_SCREENSHOT_URI");
                                        StringBuilder sb3 = new StringBuilder("startChooserActivity: sharePackageName = ");
                                        String str24 = bixbyShareController.sharePackageName;
                                        sb3.append(str24);
                                        sb3.append(" shareActivityName = ");
                                        String str25 = bixbyShareController.shareActivityName;
                                        sb3.append(str25);
                                        sb3.append(" uri = ");
                                        sb3.append(uri);
                                        Log.i(str23, sb3.toString());
                                        Intent intent4 = new Intent("android.intent.action.SEND");
                                        if (str24 != null) {
                                            if (str25 != null) {
                                                intent4.setComponent(new ComponentName(str24, str25));
                                            } else {
                                                intent4.setPackage(str24);
                                            }
                                        }
                                        intent4.setType(zIsFormatPNG2 ? "image/png" : "image/jpeg");
                                        intent4.putExtra("android.intent.extra.STREAM", uri);
                                        intent4.setFlags(453509121);
                                        if (str24 != null) {
                                            PackageManager packageManager3 = windowContext4.getPackageManager();
                                            List<ResolveInfo> listQueryIntentActivities = packageManager3 != null ? packageManager3.queryIntentActivities(intent4, 0) : null;
                                            Integer numValueOf = listQueryIntentActivities != null ? Integer.valueOf(listQueryIntentActivities.size()) : null;
                                            numValueOf.getClass();
                                            if (numValueOf.intValue() > 0) {
                                                windowContext4.startActivity(intent4);
                                                return;
                                            }
                                        }
                                        Intent intent5 = new Intent("android.intent.action.SEND");
                                        intent5.setType(zIsFormatPNG2 ? "image/png" : "image/jpeg");
                                        intent5.putExtra("android.intent.extra.STREAM", uri);
                                        Intent intentCreateChooser = Intent.createChooser(intent5, null);
                                        intentCreateChooser.setFlags(453509121);
                                        windowContext4.startActivity(intentCreateChooser);
                                    }
                                }
                            };
                            final CallbackToFutureAdapter.SafeFuture safeFutureExport = this.imageExporter.export(this.bgExecutor, uuidRandomUUID, screenshotData.bitmap, screenshotData.userHandle, this.display.getDisplayId());
                            safeFutureExport.delegate.addListener(new Runnable() { // from class: com.android.systemui.screenshot.ScreenshotController$saveScreenshotInBackground$1
                                @Override // java.lang.Runnable
                                public final void run() throws RemoteException {
                                    try {
                                        ImageExporter.Result result = (ImageExporter.Result) safeFutureExport.get();
                                        Log.d(ScreenshotController.TAG, "Saved screenshot: " + result);
                                        ScreenshotController.access$logScreenshotResultStatus(this, result.uri, screenshotData.userHandle);
                                        consumer2.accept(result);
                                        consumer.accept(result.uri);
                                    } catch (Exception e6) {
                                        this.isSavingFailed = true;
                                        Log.d(ScreenshotController.TAG, "Failed to store screenshot", e6);
                                        consumer.accept(null);
                                    }
                                    ScreenshotController screenshotController = this;
                                    if (screenshotController.isScreenshotDismissed) {
                                        return;
                                    }
                                    if (!screenshotController.bgExecutor.isShutdown()) {
                                        final ScreenshotController screenshotController2 = this;
                                        screenshotController2.bgExecutor.execute(new Runnable() { // from class: com.android.systemui.screenshot.ScreenshotController$saveScreenshotInBackground$1.1
                                            @Override // java.lang.Runnable
                                            public final void run() {
                                                ScreenshotController screenshotController3 = screenshotController2;
                                                if (screenshotController3.screenshotInterface != null) {
                                                    synchronized (screenshotController3.remoteServiceConnectionLock) {
                                                        if (screenshotController3.isRemoteScreenshotConnectionListenerInvoked) {
                                                            Unit unit = Unit.INSTANCE;
                                                        } else {
                                                            long jCurrentTimeMillis3 = System.currentTimeMillis();
                                                            try {
                                                                screenshotController3.remoteServiceConnectionLock.wait(1000L);
                                                            } catch (InterruptedException e7) {
                                                                Log.e(ScreenshotController.TAG, "Exception thrown during waiting remote service connection : " + e7);
                                                            }
                                                            Log.i(ScreenshotController.TAG, "Remote screenshot connection waiting time = " + (System.currentTimeMillis() - jCurrentTimeMillis3));
                                                            Unit unit2 = Unit.INSTANCE;
                                                        }
                                                    }
                                                    Bundle bundle2 = new Bundle();
                                                    bundle2.putCharSequenceArrayList("notifiedApps", (ArrayList) screenshotController3.notifiedApps);
                                                    RemoteScreenshotInterface remoteScreenshotInterface2 = screenshotController3.screenshotInterface;
                                                    remoteScreenshotInterface2.getClass();
                                                    long j = screenshotController3.screenshotTransactionId;
                                                    String str23 = screenshotController3.imageFilePath;
                                                    if (str23 == null) {
                                                        str23 = null;
                                                    }
                                                    Log.d("[ScrCap]_RemoteScreenshotInterface", "notifyGlobalScreenshotFinished");
                                                    IScreenshotService iScreenshotService = remoteScreenshotInterface2.mService;
                                                    if (iScreenshotService != null) {
                                                        try {
                                                            ((IScreenshotService.Stub.Proxy) iScreenshotService).onGlobalScreenshotFinished(j, str23, bundle2);
                                                        } catch (Exception e8) {
                                                            Log.e("[ScrCap]_RemoteScreenshotInterface", "notifyGlobalScreenshotFinished : e=" + e8);
                                                            Log.e("[ScrCap]_RemoteScreenshotInterface", e8.toString());
                                                        }
                                                    } else {
                                                        Log.e("[ScrCap]_RemoteScreenshotInterface", "notifyGlobalScreenshotFinished : No service connection");
                                                    }
                                                    RemoteScreenshotInterface remoteScreenshotInterface3 = screenshotController3.screenshotInterface;
                                                    remoteScreenshotInterface3.getClass();
                                                    remoteScreenshotInterface3.disconnect();
                                                }
                                            }
                                        });
                                    }
                                    ScreenshotController screenshotController3 = this;
                                    screenshotController3.isScreenshotSaveTaskCompleted = true;
                                    if (ScreenshotController.isAnimationRunning) {
                                        return;
                                    }
                                    screenshotController3.finishDismiss$1();
                                }
                            }, this.mainExecutor);
                            screenCaptureHelper2 = this.screenCaptureHelper;
                            screenCaptureHelper2.getClass();
                            if (!screenCaptureHelper2.isShowScreenshotAnimation(this.context)) {
                                this.screenshotFeedbackController.semPlayCameraSound();
                                if (this.isScreenshotSaveTaskCompleted) {
                                    finishDismiss$1();
                                    return;
                                }
                                return;
                            }
                            attachSemScreenshotLayoutToWindow();
                            SemScreenshotLayout semScreenshotLayout = this.semScreenshotLayout;
                            if (semScreenshotLayout == null) {
                                semScreenshotLayout = null;
                            }
                            semScreenshotLayout.requestFocus();
                            SemScreenshotLayout semScreenshotLayout2 = this.semScreenshotLayout;
                            (semScreenshotLayout2 == null ? null : semScreenshotLayout2).post(new Runnable() { // from class: com.android.systemui.screenshot.ScreenshotController.handleScreenshot.2
                                @Override // java.lang.Runnable
                                public final void run() {
                                    Object obj2 = ScreenshotController.shutterEffectLock;
                                    ScreenshotController screenshotController = ScreenshotController.this;
                                    ScreenshotData screenshotData2 = screenshotData;
                                    synchronized (obj2) {
                                        SemScreenshotLayout semScreenshotLayout3 = screenshotController.semScreenshotLayout;
                                        SemScreenshotLayout semScreenshotLayout4 = null;
                                        if (semScreenshotLayout3 == null) {
                                            semScreenshotLayout3 = null;
                                        }
                                        semScreenshotLayout3.mCallback = new ScreenshotController$handleScreenshot$2$1$1(screenshotController, screenshotData2);
                                        screenshotController.screenshotFeedbackController.semPlayCameraSound();
                                        SemScreenshotLayout semScreenshotLayout5 = screenshotController.semScreenshotLayout;
                                        if (semScreenshotLayout5 != null) {
                                            semScreenshotLayout4 = semScreenshotLayout5;
                                        }
                                        Bitmap bitmap4 = screenshotController.screenBitmap;
                                        bitmap4.getClass();
                                        int width2 = bitmap4.getWidth();
                                        Bitmap bitmap5 = screenshotController.screenBitmap;
                                        bitmap5.getClass();
                                        semScreenshotLayout4.startAnimation(width2, bitmap5.getHeight());
                                        ScreenshotController.isAnimationRunning = true;
                                        Unit unit = Unit.INSTANCE;
                                    }
                                }
                            });
                            return;
                        }
                        z2 = packageManager.getPackageInfo("com.samsung.android.app.smartcapture", 0) != null;
                        Log.d("[ScrCap]_RemoteScreenshotInterface", "isPackageAvailable : ".concat(z2 ? "available" : "not available"));
                        if (z2) {
                        }
                        if (remoteScreenshotInterface.mContext.bindService(intent2, remoteScreenshotInterface.mConnection, 1)) {
                        }
                        String lowerCase32 = ScreenshotUtils.getScreenshotSaveInfo(this.context)[0].toLowerCase();
                        String[] screenshotSaveInfo22 = ScreenshotUtils.getScreenshotSaveInfo(this.context);
                        if (screenshotSaveInfo22[1].isEmpty()) {
                        }
                        long epochMilli2 = zonedDateTimeNow.toInstant().toEpochMilli();
                        long epochSecond2 = zonedDateTimeNow.toEpochSecond();
                        boolean zIsFormatPNG2 = isFormatPNG(this.context);
                        if (zIsFormatPNG2) {
                        }
                        Bitmap bitmap22 = this.screenBitmap;
                        bitmap22.getClass();
                        int width2 = bitmap22.getWidth();
                        Bitmap bitmap32 = this.screenBitmap;
                        bitmap32.getClass();
                        int height2 = bitmap32.getHeight();
                        str6 = this.imageFilePath;
                        if (str6 == null) {
                        }
                        long length2 = new File(str6).length();
                        str7 = this.imageFilePath;
                        if (str7 == null) {
                        }
                        str8 = this.imageDisplayName;
                        if (str8 == null) {
                        }
                        str9 = this.imageFileName;
                        if (str9 == null) {
                        }
                        StringBuilder sbM32 = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("setScreenshotMediaData:  volumeName= ", lowerCase32, " relativePath= ", str21, " mImageFilePath= ");
                        MoveResult$$ExternalSyntheticOutline0.m(sbM32, str7, " mImageDisplayName= ", str8, " mImageFileName= ");
                        sbM32.append(str9);
                        sbM32.append(" imageTime= ");
                        sbM32.append(epochMilli2);
                        sbM32.append(" dateSeconds $=dateSeconds mimeType= ");
                        sbM32.append(str22);
                        sbM32.append(" imageWidth= ");
                        sbM32.append(width2);
                        sbM32.append(" imageHeight= ");
                        sbM32.append(height2);
                        sbM32.append(" size= ");
                        sbM32.append(length2);
                        Log.i(str15, sbM32.toString());
                        str10 = this.imageFilePath;
                        if (str10 == null) {
                        }
                        str11 = this.imageDisplayName;
                        if (str11 == null) {
                        }
                        str12 = this.imageFileName;
                        if (str12 == null) {
                        }
                        SmartClipDataExtractor.WebData webData42 = this.webData;
                        ImageExporter imageExporter2 = this.imageExporter;
                        imageExporter2.getClass();
                        ImageExporter.mImageFileRelativePath = str21;
                        ImageExporter.mVolumeName = lowerCase32;
                        ImageExporter.mImageFilePath = str10;
                        ImageExporter.mImageDisplayName = str11;
                        ImageExporter.mImageFileName = str12;
                        ImageExporter.mImageTime = epochMilli2;
                        ImageExporter.mSecDate = epochSecond2;
                        ImageExporter.mMimeType = str22;
                        ImageExporter.mWidth = width2;
                        ImageExporter.mHeight = height2;
                        ImageExporter.mSize = length2;
                        ImageExporter.mScreenshotsWebData = webData42;
                        imageExporter2.mCompressFormat = zIsFormatPNG2 ? Bitmap.CompressFormat.PNG : Bitmap.CompressFormat.JPEG;
                        JSONObject jSONObject2 = new JSONObject();
                        windowContext = this.context;
                        if (windowContext != null) {
                        }
                        jSONObject2.put("comp", strFlattenToShortString);
                        ImageExporter.mCapturedAppInfo = Base64.encodeToString(jSONObject2.toString().getBytes(Charsets.UTF_8), 2);
                        final Consumer<ImageExporter.Result> consumer22 = new Consumer() { // from class: com.android.systemui.screenshot.ScreenshotController.handleScreenshot.1
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj2) {
                                boolean z5;
                                ImageExporter.Result result = (ImageExporter.Result) obj2;
                                if (result.uri != null) {
                                    this.actionsController.setCompletedScreenshot(uuidRandomUUID, new ScreenshotSavedResult(result.uri, screenshotData.userHandle, result.timestamp));
                                    ScreenshotController screenshotController = this;
                                    BixbyShareController bixbyShareController = screenshotController.bixbyShareController;
                                    if (bixbyShareController == null || !(z5 = bixbyShareController.isBixbyCaptureShared) || screenshotController.isSavingFailed) {
                                        return;
                                    }
                                    WindowContext windowContext4 = screenshotController.context;
                                    Uri uri = result.uri;
                                    boolean zIsFormatPNG22 = ScreenshotController.isFormatPNG(windowContext4);
                                    String str23 = BixbyShareController.TAG;
                                    if (uri == null || !z5) {
                                        Log.e(str23, "isBixbyCaptureShared: " + z5 + " uri: " + uri);
                                        return;
                                    }
                                    Intent intent3 = new Intent("com.samsung.android.systemui.screenshot.SCREENSHOT_URI");
                                    String string3 = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, ContentUris.parseId(uri)).toString();
                                    intent3.putExtra("contentUri", string3);
                                    KeyguardPluginControllerImpl$$ExternalSyntheticOutline0.m("Send broadcast : screenshot contentUri = ", string3, str23);
                                    windowContext4.sendBroadcast(intent3, "com.samsung.android.systemui.screenshot.permission.RECEIVE_SCREENSHOT_URI");
                                    StringBuilder sb3 = new StringBuilder("startChooserActivity: sharePackageName = ");
                                    String str24 = bixbyShareController.sharePackageName;
                                    sb3.append(str24);
                                    sb3.append(" shareActivityName = ");
                                    String str25 = bixbyShareController.shareActivityName;
                                    sb3.append(str25);
                                    sb3.append(" uri = ");
                                    sb3.append(uri);
                                    Log.i(str23, sb3.toString());
                                    Intent intent4 = new Intent("android.intent.action.SEND");
                                    if (str24 != null) {
                                        if (str25 != null) {
                                            intent4.setComponent(new ComponentName(str24, str25));
                                        } else {
                                            intent4.setPackage(str24);
                                        }
                                    }
                                    intent4.setType(zIsFormatPNG22 ? "image/png" : "image/jpeg");
                                    intent4.putExtra("android.intent.extra.STREAM", uri);
                                    intent4.setFlags(453509121);
                                    if (str24 != null) {
                                        PackageManager packageManager3 = windowContext4.getPackageManager();
                                        List<ResolveInfo> listQueryIntentActivities = packageManager3 != null ? packageManager3.queryIntentActivities(intent4, 0) : null;
                                        Integer numValueOf = listQueryIntentActivities != null ? Integer.valueOf(listQueryIntentActivities.size()) : null;
                                        numValueOf.getClass();
                                        if (numValueOf.intValue() > 0) {
                                            windowContext4.startActivity(intent4);
                                            return;
                                        }
                                    }
                                    Intent intent5 = new Intent("android.intent.action.SEND");
                                    intent5.setType(zIsFormatPNG22 ? "image/png" : "image/jpeg");
                                    intent5.putExtra("android.intent.extra.STREAM", uri);
                                    Intent intentCreateChooser = Intent.createChooser(intent5, null);
                                    intentCreateChooser.setFlags(453509121);
                                    windowContext4.startActivity(intentCreateChooser);
                                }
                            }
                        };
                        final ListenableFuture safeFutureExport2 = this.imageExporter.export(this.bgExecutor, uuidRandomUUID, screenshotData.bitmap, screenshotData.userHandle, this.display.getDisplayId());
                        safeFutureExport2.delegate.addListener(new Runnable() { // from class: com.android.systemui.screenshot.ScreenshotController$saveScreenshotInBackground$1
                            @Override // java.lang.Runnable
                            public final void run() throws RemoteException {
                                try {
                                    ImageExporter.Result result = (ImageExporter.Result) safeFutureExport2.get();
                                    Log.d(ScreenshotController.TAG, "Saved screenshot: " + result);
                                    ScreenshotController.access$logScreenshotResultStatus(this, result.uri, screenshotData.userHandle);
                                    consumer22.accept(result);
                                    consumer.accept(result.uri);
                                } catch (Exception e6) {
                                    this.isSavingFailed = true;
                                    Log.d(ScreenshotController.TAG, "Failed to store screenshot", e6);
                                    consumer.accept(null);
                                }
                                ScreenshotController screenshotController = this;
                                if (screenshotController.isScreenshotDismissed) {
                                    return;
                                }
                                if (!screenshotController.bgExecutor.isShutdown()) {
                                    final ScreenshotController screenshotController2 = this;
                                    screenshotController2.bgExecutor.execute(new Runnable() { // from class: com.android.systemui.screenshot.ScreenshotController$saveScreenshotInBackground$1.1
                                        @Override // java.lang.Runnable
                                        public final void run() {
                                            ScreenshotController screenshotController3 = screenshotController2;
                                            if (screenshotController3.screenshotInterface != null) {
                                                synchronized (screenshotController3.remoteServiceConnectionLock) {
                                                    if (screenshotController3.isRemoteScreenshotConnectionListenerInvoked) {
                                                        Unit unit2 = Unit.INSTANCE;
                                                    } else {
                                                        long jCurrentTimeMillis3 = System.currentTimeMillis();
                                                        try {
                                                            screenshotController3.remoteServiceConnectionLock.wait(1000L);
                                                        } catch (InterruptedException e7) {
                                                            Log.e(ScreenshotController.TAG, "Exception thrown during waiting remote service connection : " + e7);
                                                        }
                                                        Log.i(ScreenshotController.TAG, "Remote screenshot connection waiting time = " + (System.currentTimeMillis() - jCurrentTimeMillis3));
                                                        Unit unit22 = Unit.INSTANCE;
                                                    }
                                                }
                                                Bundle bundle2 = new Bundle();
                                                bundle2.putCharSequenceArrayList("notifiedApps", (ArrayList) screenshotController3.notifiedApps);
                                                RemoteScreenshotInterface remoteScreenshotInterface2 = screenshotController3.screenshotInterface;
                                                remoteScreenshotInterface2.getClass();
                                                long j = screenshotController3.screenshotTransactionId;
                                                String str23 = screenshotController3.imageFilePath;
                                                if (str23 == null) {
                                                    str23 = null;
                                                }
                                                Log.d("[ScrCap]_RemoteScreenshotInterface", "notifyGlobalScreenshotFinished");
                                                IScreenshotService iScreenshotService = remoteScreenshotInterface2.mService;
                                                if (iScreenshotService != null) {
                                                    try {
                                                        ((IScreenshotService.Stub.Proxy) iScreenshotService).onGlobalScreenshotFinished(j, str23, bundle2);
                                                    } catch (Exception e8) {
                                                        Log.e("[ScrCap]_RemoteScreenshotInterface", "notifyGlobalScreenshotFinished : e=" + e8);
                                                        Log.e("[ScrCap]_RemoteScreenshotInterface", e8.toString());
                                                    }
                                                } else {
                                                    Log.e("[ScrCap]_RemoteScreenshotInterface", "notifyGlobalScreenshotFinished : No service connection");
                                                }
                                                RemoteScreenshotInterface remoteScreenshotInterface3 = screenshotController3.screenshotInterface;
                                                remoteScreenshotInterface3.getClass();
                                                remoteScreenshotInterface3.disconnect();
                                            }
                                        }
                                    });
                                }
                                ScreenshotController screenshotController3 = this;
                                screenshotController3.isScreenshotSaveTaskCompleted = true;
                                if (ScreenshotController.isAnimationRunning) {
                                    return;
                                }
                                screenshotController3.finishDismiss$1();
                            }
                        }, this.mainExecutor);
                        screenCaptureHelper2 = this.screenCaptureHelper;
                        screenCaptureHelper2.getClass();
                        if (!screenCaptureHelper2.isShowScreenshotAnimation(this.context)) {
                        }
                    }
                    String lowerCase322 = ScreenshotUtils.getScreenshotSaveInfo(this.context)[0].toLowerCase();
                    String[] screenshotSaveInfo222 = ScreenshotUtils.getScreenshotSaveInfo(this.context);
                    if (screenshotSaveInfo222[1].isEmpty()) {
                    }
                    long epochMilli22 = zonedDateTimeNow.toInstant().toEpochMilli();
                    long epochSecond22 = zonedDateTimeNow.toEpochSecond();
                    boolean zIsFormatPNG22 = isFormatPNG(this.context);
                    if (zIsFormatPNG22) {
                    }
                    Bitmap bitmap222 = this.screenBitmap;
                    bitmap222.getClass();
                    int width22 = bitmap222.getWidth();
                    Bitmap bitmap322 = this.screenBitmap;
                    bitmap322.getClass();
                    int height22 = bitmap322.getHeight();
                    str6 = this.imageFilePath;
                    if (str6 == null) {
                    }
                    long length22 = new File(str6).length();
                    str7 = this.imageFilePath;
                    if (str7 == null) {
                    }
                    str8 = this.imageDisplayName;
                    if (str8 == null) {
                    }
                    str9 = this.imageFileName;
                    if (str9 == null) {
                    }
                    StringBuilder sbM322 = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("setScreenshotMediaData:  volumeName= ", lowerCase322, " relativePath= ", str21, " mImageFilePath= ");
                    MoveResult$$ExternalSyntheticOutline0.m(sbM322, str7, " mImageDisplayName= ", str8, " mImageFileName= ");
                    sbM322.append(str9);
                    sbM322.append(" imageTime= ");
                    sbM322.append(epochMilli22);
                    sbM322.append(" dateSeconds $=dateSeconds mimeType= ");
                    sbM322.append(str22);
                    sbM322.append(" imageWidth= ");
                    sbM322.append(width22);
                    sbM322.append(" imageHeight= ");
                    sbM322.append(height22);
                    sbM322.append(" size= ");
                    sbM322.append(length22);
                    Log.i(str15, sbM322.toString());
                    str10 = this.imageFilePath;
                    if (str10 == null) {
                    }
                    str11 = this.imageDisplayName;
                    if (str11 == null) {
                    }
                    str12 = this.imageFileName;
                    if (str12 == null) {
                    }
                    SmartClipDataExtractor.WebData webData422 = this.webData;
                    ImageExporter imageExporter22 = this.imageExporter;
                    imageExporter22.getClass();
                    ImageExporter.mImageFileRelativePath = str21;
                    ImageExporter.mVolumeName = lowerCase322;
                    ImageExporter.mImageFilePath = str10;
                    ImageExporter.mImageDisplayName = str11;
                    ImageExporter.mImageFileName = str12;
                    ImageExporter.mImageTime = epochMilli22;
                    ImageExporter.mSecDate = epochSecond22;
                    ImageExporter.mMimeType = str22;
                    ImageExporter.mWidth = width22;
                    ImageExporter.mHeight = height22;
                    ImageExporter.mSize = length22;
                    ImageExporter.mScreenshotsWebData = webData422;
                    imageExporter22.mCompressFormat = zIsFormatPNG22 ? Bitmap.CompressFormat.PNG : Bitmap.CompressFormat.JPEG;
                    JSONObject jSONObject22 = new JSONObject();
                    windowContext = this.context;
                    if (windowContext != null) {
                    }
                    jSONObject22.put("comp", strFlattenToShortString);
                    ImageExporter.mCapturedAppInfo = Base64.encodeToString(jSONObject22.toString().getBytes(Charsets.UTF_8), 2);
                    final Consumer<ImageExporter.Result> consumer222 = new Consumer() { // from class: com.android.systemui.screenshot.ScreenshotController.handleScreenshot.1
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj2) {
                            boolean z5;
                            ImageExporter.Result result = (ImageExporter.Result) obj2;
                            if (result.uri != null) {
                                this.actionsController.setCompletedScreenshot(uuidRandomUUID, new ScreenshotSavedResult(result.uri, screenshotData.userHandle, result.timestamp));
                                ScreenshotController screenshotController = this;
                                BixbyShareController bixbyShareController = screenshotController.bixbyShareController;
                                if (bixbyShareController == null || !(z5 = bixbyShareController.isBixbyCaptureShared) || screenshotController.isSavingFailed) {
                                    return;
                                }
                                WindowContext windowContext4 = screenshotController.context;
                                Uri uri = result.uri;
                                boolean zIsFormatPNG222 = ScreenshotController.isFormatPNG(windowContext4);
                                String str23 = BixbyShareController.TAG;
                                if (uri == null || !z5) {
                                    Log.e(str23, "isBixbyCaptureShared: " + z5 + " uri: " + uri);
                                    return;
                                }
                                Intent intent3 = new Intent("com.samsung.android.systemui.screenshot.SCREENSHOT_URI");
                                String string3 = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, ContentUris.parseId(uri)).toString();
                                intent3.putExtra("contentUri", string3);
                                KeyguardPluginControllerImpl$$ExternalSyntheticOutline0.m("Send broadcast : screenshot contentUri = ", string3, str23);
                                windowContext4.sendBroadcast(intent3, "com.samsung.android.systemui.screenshot.permission.RECEIVE_SCREENSHOT_URI");
                                StringBuilder sb3 = new StringBuilder("startChooserActivity: sharePackageName = ");
                                String str24 = bixbyShareController.sharePackageName;
                                sb3.append(str24);
                                sb3.append(" shareActivityName = ");
                                String str25 = bixbyShareController.shareActivityName;
                                sb3.append(str25);
                                sb3.append(" uri = ");
                                sb3.append(uri);
                                Log.i(str23, sb3.toString());
                                Intent intent4 = new Intent("android.intent.action.SEND");
                                if (str24 != null) {
                                    if (str25 != null) {
                                        intent4.setComponent(new ComponentName(str24, str25));
                                    } else {
                                        intent4.setPackage(str24);
                                    }
                                }
                                intent4.setType(zIsFormatPNG222 ? "image/png" : "image/jpeg");
                                intent4.putExtra("android.intent.extra.STREAM", uri);
                                intent4.setFlags(453509121);
                                if (str24 != null) {
                                    PackageManager packageManager3 = windowContext4.getPackageManager();
                                    List<ResolveInfo> listQueryIntentActivities = packageManager3 != null ? packageManager3.queryIntentActivities(intent4, 0) : null;
                                    Integer numValueOf = listQueryIntentActivities != null ? Integer.valueOf(listQueryIntentActivities.size()) : null;
                                    numValueOf.getClass();
                                    if (numValueOf.intValue() > 0) {
                                        windowContext4.startActivity(intent4);
                                        return;
                                    }
                                }
                                Intent intent5 = new Intent("android.intent.action.SEND");
                                intent5.setType(zIsFormatPNG222 ? "image/png" : "image/jpeg");
                                intent5.putExtra("android.intent.extra.STREAM", uri);
                                Intent intentCreateChooser = Intent.createChooser(intent5, null);
                                intentCreateChooser.setFlags(453509121);
                                windowContext4.startActivity(intentCreateChooser);
                            }
                        }
                    };
                    final ListenableFuture safeFutureExport22 = this.imageExporter.export(this.bgExecutor, uuidRandomUUID, screenshotData.bitmap, screenshotData.userHandle, this.display.getDisplayId());
                    safeFutureExport22.delegate.addListener(new Runnable() { // from class: com.android.systemui.screenshot.ScreenshotController$saveScreenshotInBackground$1
                        @Override // java.lang.Runnable
                        public final void run() throws RemoteException {
                            try {
                                ImageExporter.Result result = (ImageExporter.Result) safeFutureExport22.get();
                                Log.d(ScreenshotController.TAG, "Saved screenshot: " + result);
                                ScreenshotController.access$logScreenshotResultStatus(this, result.uri, screenshotData.userHandle);
                                consumer222.accept(result);
                                consumer.accept(result.uri);
                            } catch (Exception e6) {
                                this.isSavingFailed = true;
                                Log.d(ScreenshotController.TAG, "Failed to store screenshot", e6);
                                consumer.accept(null);
                            }
                            ScreenshotController screenshotController = this;
                            if (screenshotController.isScreenshotDismissed) {
                                return;
                            }
                            if (!screenshotController.bgExecutor.isShutdown()) {
                                final ScreenshotController screenshotController2 = this;
                                screenshotController2.bgExecutor.execute(new Runnable() { // from class: com.android.systemui.screenshot.ScreenshotController$saveScreenshotInBackground$1.1
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        ScreenshotController screenshotController3 = screenshotController2;
                                        if (screenshotController3.screenshotInterface != null) {
                                            synchronized (screenshotController3.remoteServiceConnectionLock) {
                                                if (screenshotController3.isRemoteScreenshotConnectionListenerInvoked) {
                                                    Unit unit22 = Unit.INSTANCE;
                                                } else {
                                                    long jCurrentTimeMillis3 = System.currentTimeMillis();
                                                    try {
                                                        screenshotController3.remoteServiceConnectionLock.wait(1000L);
                                                    } catch (InterruptedException e7) {
                                                        Log.e(ScreenshotController.TAG, "Exception thrown during waiting remote service connection : " + e7);
                                                    }
                                                    Log.i(ScreenshotController.TAG, "Remote screenshot connection waiting time = " + (System.currentTimeMillis() - jCurrentTimeMillis3));
                                                    Unit unit222 = Unit.INSTANCE;
                                                }
                                            }
                                            Bundle bundle2 = new Bundle();
                                            bundle2.putCharSequenceArrayList("notifiedApps", (ArrayList) screenshotController3.notifiedApps);
                                            RemoteScreenshotInterface remoteScreenshotInterface2 = screenshotController3.screenshotInterface;
                                            remoteScreenshotInterface2.getClass();
                                            long j = screenshotController3.screenshotTransactionId;
                                            String str23 = screenshotController3.imageFilePath;
                                            if (str23 == null) {
                                                str23 = null;
                                            }
                                            Log.d("[ScrCap]_RemoteScreenshotInterface", "notifyGlobalScreenshotFinished");
                                            IScreenshotService iScreenshotService = remoteScreenshotInterface2.mService;
                                            if (iScreenshotService != null) {
                                                try {
                                                    ((IScreenshotService.Stub.Proxy) iScreenshotService).onGlobalScreenshotFinished(j, str23, bundle2);
                                                } catch (Exception e8) {
                                                    Log.e("[ScrCap]_RemoteScreenshotInterface", "notifyGlobalScreenshotFinished : e=" + e8);
                                                    Log.e("[ScrCap]_RemoteScreenshotInterface", e8.toString());
                                                }
                                            } else {
                                                Log.e("[ScrCap]_RemoteScreenshotInterface", "notifyGlobalScreenshotFinished : No service connection");
                                            }
                                            RemoteScreenshotInterface remoteScreenshotInterface3 = screenshotController3.screenshotInterface;
                                            remoteScreenshotInterface3.getClass();
                                            remoteScreenshotInterface3.disconnect();
                                        }
                                    }
                                });
                            }
                            ScreenshotController screenshotController3 = this;
                            screenshotController3.isScreenshotSaveTaskCompleted = true;
                            if (ScreenshotController.isAnimationRunning) {
                                return;
                            }
                            screenshotController3.finishDismiss$1();
                        }
                    }, this.mainExecutor);
                    screenCaptureHelper2 = this.screenCaptureHelper;
                    screenCaptureHelper2.getClass();
                    if (!screenCaptureHelper2.isShowScreenshotAnimation(this.context)) {
                    }
                }
                webData = null;
                if (webData != null) {
                    Log.i(str15, "handleScreenshot: webData is extracted from SmartClip");
                }
                this.webData = webData;
                if (webData == null) {
                }
                Trace.endSection();
                ScreenshotDetectionController screenshotDetectionController2 = this.screenshotDetectionController;
                screenshotDetectionController2.getClass();
                if (screenshotData.source == 3) {
                }
                this.notifiedApps = list;
                ((ArrayList) this.currentRequestCallbacks).add(requestCallback);
                Intent intent3 = new Intent("com.android.systemui.SCREENSHOT");
                BroadcastSender broadcastSender2 = this.broadcastSender;
                broadcastSender2.getClass();
                broadcastSender2.sendInBackground(String.valueOf(intent3), new BroadcastSender$$ExternalSyntheticLambda0(broadcastSender2, intent3, 0));
                int i142 = this.context.getResources().getConfiguration().orientation;
                bitmap.setHasAlpha(false);
                bitmap.prepareToDraw();
                final Runnable runnable2 = new Runnable() { // from class: com.android.systemui.screenshot.ScreenshotController$prepareViewForNewScreenshot$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        AnnouncementResolver announcementResolver = this.this$0.announcementResolver;
                        int identifier = screenshotData.userHandle.getIdentifier();
                        final ScreenshotController screenshotController = this.this$0;
                        Consumer consumer23 = new Consumer() { // from class: com.android.systemui.screenshot.ScreenshotController$prepareViewForNewScreenshot$1.1
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj2) {
                                screenshotController.viewProxy.view.announceForAccessibility((String) obj2);
                            }
                        };
                        announcementResolver.getClass();
                        CoroutineTracingKt.launchTraced$default(announcementResolver.mainScope, null, null, new AnnouncementResolver.AnonymousClass2(consumer23, announcementResolver, identifier, null), 7);
                    }
                };
                final ScreenshotWindow screenshotWindow2 = this.window;
                decorView = screenshotWindow2.window.getDecorView();
                if (decorView.isAttachedToWindow()) {
                }
                screenshotShelfViewProxy = this.viewProxy;
                screenshotShelfViewProxy.reset();
                if (screenshotShelfViewProxy.view.isAttachedToWindow()) {
                    this.uiEventLogger.log(ScreenshotEvent.SCREENSHOT_REENTERED, 0, str16);
                }
                screenshotShelfViewProxy.packageName = this.packageName;
                ScreenshotActionsController screenshotActionsController2 = this.actionsController;
                screenshotActionsController2.getClass();
                final UUID uuidRandomUUID2 = UUID.randomUUID();
                screenshotActionsController2.currentScreenshotId = uuidRandomUUID2;
                Map map2 = screenshotActionsController2.actionProviders;
                uuidRandomUUID2.getClass();
                map2.put(uuidRandomUUID2, ((DaggerReferenceGlobalRootComponent.ReferenceSysUIComponentImpl.SwitchingProvider.AnonymousClass144) screenshotActionsController2.actionsProviderFactory).create(uuidRandomUUID2, screenshotData, screenshotActionsController2.actionExecutor, screenshotActionsController2.new ActionsCallback(uuidRandomUUID2)));
                screenCaptureHelper = this.screenCaptureHelper;
                if (screenCaptureHelper != null) {
                    screenCaptureHelper3 = this.screenCaptureHelper;
                    if (screenCaptureHelper3 != null) {
                        Bundle bundle2 = null;
                        this.bixbyShareController = new BixbyShareController(bundle2);
                    }
                }
                ZonedDateTime zonedDateTimeNow2 = ZonedDateTime.now(ZoneId.systemDefault());
                zonedDateTimeNow2.getClass();
                ScreenCaptureHelper screenCaptureHelper62 = this.screenCaptureHelper;
                screenCaptureHelper62.getClass();
                Context context42 = screenCaptureHelper62.displayContext;
                topMostApplicationPackage = ScreenshotUtils.getTopMostApplicationPackage(context42);
                if (topMostApplicationPackage != null) {
                }
                ScreenCaptureHelper screenCaptureHelper72 = this.screenCaptureHelper;
                if (screenCaptureHelper72 == null) {
                }
                if (systemService instanceof SemStatusBarManager) {
                }
                if (strReplaceAll != null) {
                    ScreenCaptureHelper screenCaptureHelper82 = this.screenCaptureHelper;
                    screenCaptureHelper82.getClass();
                    strReplaceAll = ScreenshotUtils.isSubDisplayCapture(screenCaptureHelper82.capturedDisplayId) ? "One UI Cover Home" : "One UI Home";
                } else {
                    ScreenCaptureHelper screenCaptureHelper822 = this.screenCaptureHelper;
                    screenCaptureHelper822.getClass();
                    strReplaceAll = ScreenshotUtils.isSubDisplayCapture(screenCaptureHelper822.capturedDisplayId) ? "One UI Cover Home" : "One UI Home";
                }
                strM = AnimatorInflaterCompat$$ExternalSyntheticOutline0.m("Screenshot_", String.format("%1$tY%<tm%<td_%<tH%<tM%<tS", zonedDateTimeNow2), "_", strReplaceAll);
                if (strReplaceAll != null) {
                    strM = strM.substring(0, strM.length() - 1);
                }
                this.imageDisplayName = strM;
                if (isFormatPNG(this.context)) {
                }
                this.imageFileName = strM2;
                screenshotSaveInfo = ScreenshotUtils.getScreenshotSaveInfo(this.context);
                if (screenshotSaveInfo[0].equals("external_primary")) {
                }
                File file2 = new File(string);
                str4 = this.imageFileName;
                if (str4 == null) {
                }
                this.imageFilePath = new File(file2, str4).getAbsolutePath();
                str5 = this.imageDisplayName;
                if (str5 == null) {
                }
                this.thumbnailImageFilePath = new File(file2, ContentInViewNode$Request$$ExternalSyntheticOutline0.m("thumbnail_", str5, ".jpg")).getAbsolutePath();
                long jCurrentTimeMillis3 = System.currentTimeMillis();
                remoteScreenshotInterface = new RemoteScreenshotInterface();
                this.screenshotInterface = remoteScreenshotInterface;
                WindowContext windowContext32 = this.context;
                ScreenshotController$startCaptureAppRemoteServiceConnection$1 screenshotController$startCaptureAppRemoteServiceConnection$13 = new ScreenshotController$startCaptureAppRemoteServiceConnection$1(jCurrentTimeMillis3, this);
                Log.d("[ScrCap]_RemoteScreenshotInterface", "connect");
                final long jCurrentTimeMillis22 = System.currentTimeMillis();
                if (remoteScreenshotInterface.mService != null) {
                }
                String lowerCase3222 = ScreenshotUtils.getScreenshotSaveInfo(this.context)[0].toLowerCase();
                String[] screenshotSaveInfo2222 = ScreenshotUtils.getScreenshotSaveInfo(this.context);
                if (screenshotSaveInfo2222[1].isEmpty()) {
                }
                long epochMilli222 = zonedDateTimeNow2.toInstant().toEpochMilli();
                long epochSecond222 = zonedDateTimeNow2.toEpochSecond();
                boolean zIsFormatPNG222 = isFormatPNG(this.context);
                if (zIsFormatPNG222) {
                }
                Bitmap bitmap2222 = this.screenBitmap;
                bitmap2222.getClass();
                int width222 = bitmap2222.getWidth();
                Bitmap bitmap3222 = this.screenBitmap;
                bitmap3222.getClass();
                int height222 = bitmap3222.getHeight();
                str6 = this.imageFilePath;
                if (str6 == null) {
                }
                long length222 = new File(str6).length();
                str7 = this.imageFilePath;
                if (str7 == null) {
                }
                str8 = this.imageDisplayName;
                if (str8 == null) {
                }
                str9 = this.imageFileName;
                if (str9 == null) {
                }
                StringBuilder sbM3222 = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("setScreenshotMediaData:  volumeName= ", lowerCase3222, " relativePath= ", str21, " mImageFilePath= ");
                MoveResult$$ExternalSyntheticOutline0.m(sbM3222, str7, " mImageDisplayName= ", str8, " mImageFileName= ");
                sbM3222.append(str9);
                sbM3222.append(" imageTime= ");
                sbM3222.append(epochMilli222);
                sbM3222.append(" dateSeconds $=dateSeconds mimeType= ");
                sbM3222.append(str22);
                sbM3222.append(" imageWidth= ");
                sbM3222.append(width222);
                sbM3222.append(" imageHeight= ");
                sbM3222.append(height222);
                sbM3222.append(" size= ");
                sbM3222.append(length222);
                Log.i(str15, sbM3222.toString());
                str10 = this.imageFilePath;
                if (str10 == null) {
                }
                str11 = this.imageDisplayName;
                if (str11 == null) {
                }
                str12 = this.imageFileName;
                if (str12 == null) {
                }
                SmartClipDataExtractor.WebData webData4222 = this.webData;
                ImageExporter imageExporter222 = this.imageExporter;
                imageExporter222.getClass();
                ImageExporter.mImageFileRelativePath = str21;
                ImageExporter.mVolumeName = lowerCase3222;
                ImageExporter.mImageFilePath = str10;
                ImageExporter.mImageDisplayName = str11;
                ImageExporter.mImageFileName = str12;
                ImageExporter.mImageTime = epochMilli222;
                ImageExporter.mSecDate = epochSecond222;
                ImageExporter.mMimeType = str22;
                ImageExporter.mWidth = width222;
                ImageExporter.mHeight = height222;
                ImageExporter.mSize = length222;
                ImageExporter.mScreenshotsWebData = webData4222;
                imageExporter222.mCompressFormat = zIsFormatPNG222 ? Bitmap.CompressFormat.PNG : Bitmap.CompressFormat.JPEG;
                JSONObject jSONObject222 = new JSONObject();
                windowContext = this.context;
                if (windowContext != null) {
                }
                jSONObject222.put("comp", strFlattenToShortString);
                ImageExporter.mCapturedAppInfo = Base64.encodeToString(jSONObject222.toString().getBytes(Charsets.UTF_8), 2);
                final Consumer<ImageExporter.Result> consumer2222 = new Consumer() { // from class: com.android.systemui.screenshot.ScreenshotController.handleScreenshot.1
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj2) {
                        boolean z5;
                        ImageExporter.Result result = (ImageExporter.Result) obj2;
                        if (result.uri != null) {
                            this.actionsController.setCompletedScreenshot(uuidRandomUUID2, new ScreenshotSavedResult(result.uri, screenshotData.userHandle, result.timestamp));
                            ScreenshotController screenshotController = this;
                            BixbyShareController bixbyShareController = screenshotController.bixbyShareController;
                            if (bixbyShareController == null || !(z5 = bixbyShareController.isBixbyCaptureShared) || screenshotController.isSavingFailed) {
                                return;
                            }
                            WindowContext windowContext4 = screenshotController.context;
                            Uri uri = result.uri;
                            boolean zIsFormatPNG2222 = ScreenshotController.isFormatPNG(windowContext4);
                            String str23 = BixbyShareController.TAG;
                            if (uri == null || !z5) {
                                Log.e(str23, "isBixbyCaptureShared: " + z5 + " uri: " + uri);
                                return;
                            }
                            Intent intent32 = new Intent("com.samsung.android.systemui.screenshot.SCREENSHOT_URI");
                            String string3 = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, ContentUris.parseId(uri)).toString();
                            intent32.putExtra("contentUri", string3);
                            KeyguardPluginControllerImpl$$ExternalSyntheticOutline0.m("Send broadcast : screenshot contentUri = ", string3, str23);
                            windowContext4.sendBroadcast(intent32, "com.samsung.android.systemui.screenshot.permission.RECEIVE_SCREENSHOT_URI");
                            StringBuilder sb3 = new StringBuilder("startChooserActivity: sharePackageName = ");
                            String str24 = bixbyShareController.sharePackageName;
                            sb3.append(str24);
                            sb3.append(" shareActivityName = ");
                            String str25 = bixbyShareController.shareActivityName;
                            sb3.append(str25);
                            sb3.append(" uri = ");
                            sb3.append(uri);
                            Log.i(str23, sb3.toString());
                            Intent intent4 = new Intent("android.intent.action.SEND");
                            if (str24 != null) {
                                if (str25 != null) {
                                    intent4.setComponent(new ComponentName(str24, str25));
                                } else {
                                    intent4.setPackage(str24);
                                }
                            }
                            intent4.setType(zIsFormatPNG2222 ? "image/png" : "image/jpeg");
                            intent4.putExtra("android.intent.extra.STREAM", uri);
                            intent4.setFlags(453509121);
                            if (str24 != null) {
                                PackageManager packageManager3 = windowContext4.getPackageManager();
                                List<ResolveInfo> listQueryIntentActivities = packageManager3 != null ? packageManager3.queryIntentActivities(intent4, 0) : null;
                                Integer numValueOf = listQueryIntentActivities != null ? Integer.valueOf(listQueryIntentActivities.size()) : null;
                                numValueOf.getClass();
                                if (numValueOf.intValue() > 0) {
                                    windowContext4.startActivity(intent4);
                                    return;
                                }
                            }
                            Intent intent5 = new Intent("android.intent.action.SEND");
                            intent5.setType(zIsFormatPNG2222 ? "image/png" : "image/jpeg");
                            intent5.putExtra("android.intent.extra.STREAM", uri);
                            Intent intentCreateChooser = Intent.createChooser(intent5, null);
                            intentCreateChooser.setFlags(453509121);
                            windowContext4.startActivity(intentCreateChooser);
                        }
                    }
                };
                final ListenableFuture safeFutureExport222 = this.imageExporter.export(this.bgExecutor, uuidRandomUUID2, screenshotData.bitmap, screenshotData.userHandle, this.display.getDisplayId());
                safeFutureExport222.delegate.addListener(new Runnable() { // from class: com.android.systemui.screenshot.ScreenshotController$saveScreenshotInBackground$1
                    @Override // java.lang.Runnable
                    public final void run() throws RemoteException {
                        try {
                            ImageExporter.Result result = (ImageExporter.Result) safeFutureExport222.get();
                            Log.d(ScreenshotController.TAG, "Saved screenshot: " + result);
                            ScreenshotController.access$logScreenshotResultStatus(this, result.uri, screenshotData.userHandle);
                            consumer2222.accept(result);
                            consumer.accept(result.uri);
                        } catch (Exception e6) {
                            this.isSavingFailed = true;
                            Log.d(ScreenshotController.TAG, "Failed to store screenshot", e6);
                            consumer.accept(null);
                        }
                        ScreenshotController screenshotController = this;
                        if (screenshotController.isScreenshotDismissed) {
                            return;
                        }
                        if (!screenshotController.bgExecutor.isShutdown()) {
                            final ScreenshotController screenshotController2 = this;
                            screenshotController2.bgExecutor.execute(new Runnable() { // from class: com.android.systemui.screenshot.ScreenshotController$saveScreenshotInBackground$1.1
                                @Override // java.lang.Runnable
                                public final void run() {
                                    ScreenshotController screenshotController3 = screenshotController2;
                                    if (screenshotController3.screenshotInterface != null) {
                                        synchronized (screenshotController3.remoteServiceConnectionLock) {
                                            if (screenshotController3.isRemoteScreenshotConnectionListenerInvoked) {
                                                Unit unit222 = Unit.INSTANCE;
                                            } else {
                                                long jCurrentTimeMillis32 = System.currentTimeMillis();
                                                try {
                                                    screenshotController3.remoteServiceConnectionLock.wait(1000L);
                                                } catch (InterruptedException e7) {
                                                    Log.e(ScreenshotController.TAG, "Exception thrown during waiting remote service connection : " + e7);
                                                }
                                                Log.i(ScreenshotController.TAG, "Remote screenshot connection waiting time = " + (System.currentTimeMillis() - jCurrentTimeMillis32));
                                                Unit unit2222 = Unit.INSTANCE;
                                            }
                                        }
                                        Bundle bundle22 = new Bundle();
                                        bundle22.putCharSequenceArrayList("notifiedApps", (ArrayList) screenshotController3.notifiedApps);
                                        RemoteScreenshotInterface remoteScreenshotInterface2 = screenshotController3.screenshotInterface;
                                        remoteScreenshotInterface2.getClass();
                                        long j = screenshotController3.screenshotTransactionId;
                                        String str23 = screenshotController3.imageFilePath;
                                        if (str23 == null) {
                                            str23 = null;
                                        }
                                        Log.d("[ScrCap]_RemoteScreenshotInterface", "notifyGlobalScreenshotFinished");
                                        IScreenshotService iScreenshotService = remoteScreenshotInterface2.mService;
                                        if (iScreenshotService != null) {
                                            try {
                                                ((IScreenshotService.Stub.Proxy) iScreenshotService).onGlobalScreenshotFinished(j, str23, bundle22);
                                            } catch (Exception e8) {
                                                Log.e("[ScrCap]_RemoteScreenshotInterface", "notifyGlobalScreenshotFinished : e=" + e8);
                                                Log.e("[ScrCap]_RemoteScreenshotInterface", e8.toString());
                                            }
                                        } else {
                                            Log.e("[ScrCap]_RemoteScreenshotInterface", "notifyGlobalScreenshotFinished : No service connection");
                                        }
                                        RemoteScreenshotInterface remoteScreenshotInterface3 = screenshotController3.screenshotInterface;
                                        remoteScreenshotInterface3.getClass();
                                        remoteScreenshotInterface3.disconnect();
                                    }
                                }
                            });
                        }
                        ScreenshotController screenshotController3 = this;
                        screenshotController3.isScreenshotSaveTaskCompleted = true;
                        if (ScreenshotController.isAnimationRunning) {
                            return;
                        }
                        screenshotController3.finishDismiss$1();
                    }
                }, this.mainExecutor);
                screenCaptureHelper2 = this.screenCaptureHelper;
                screenCaptureHelper2.getClass();
                if (!screenCaptureHelper2.isShowScreenshotAnimation(this.context)) {
                }
            } else {
                str3 = str;
                if ((mode & 1) != 0) {
                    Log.i("Screenshot", "isMultiWindowStyleAppExist : MODE_FREEFORM");
                }
                if ((mode & 4) != 0) {
                    Log.i("Screenshot", "isMultiWindowStyleAppExist : MODE_PICTURE_IN_PICTURE");
                }
                if ((mode & 2) != 0) {
                    Log.i("Screenshot", "isMultiWindowStyleAppExist : MODE_SPLIT_SCREEN");
                }
                Log.e("Screenshot", "canExtractWebData : MultiWindow style app exists");
            }
            webData = null;
            this.webData = webData;
            if (webData == null) {
            }
            Trace.endSection();
            ScreenshotDetectionController screenshotDetectionController22 = this.screenshotDetectionController;
            screenshotDetectionController22.getClass();
            if (screenshotData.source == 3) {
            }
            this.notifiedApps = list;
            ((ArrayList) this.currentRequestCallbacks).add(requestCallback);
            Intent intent32 = new Intent("com.android.systemui.SCREENSHOT");
            BroadcastSender broadcastSender22 = this.broadcastSender;
            broadcastSender22.getClass();
            broadcastSender22.sendInBackground(String.valueOf(intent32), new BroadcastSender$$ExternalSyntheticLambda0(broadcastSender22, intent32, 0));
            int i1422 = this.context.getResources().getConfiguration().orientation;
            bitmap.setHasAlpha(false);
            bitmap.prepareToDraw();
            final Runnable runnable22 = new Runnable() { // from class: com.android.systemui.screenshot.ScreenshotController$prepareViewForNewScreenshot$1
                @Override // java.lang.Runnable
                public final void run() {
                    AnnouncementResolver announcementResolver = this.this$0.announcementResolver;
                    int identifier = screenshotData.userHandle.getIdentifier();
                    final ScreenshotController screenshotController = this.this$0;
                    Consumer consumer23 = new Consumer() { // from class: com.android.systemui.screenshot.ScreenshotController$prepareViewForNewScreenshot$1.1
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj2) {
                            screenshotController.viewProxy.view.announceForAccessibility((String) obj2);
                        }
                    };
                    announcementResolver.getClass();
                    CoroutineTracingKt.launchTraced$default(announcementResolver.mainScope, null, null, new AnnouncementResolver.AnonymousClass2(consumer23, announcementResolver, identifier, null), 7);
                }
            };
            final ScreenshotWindow screenshotWindow22 = this.window;
            decorView = screenshotWindow22.window.getDecorView();
            if (decorView.isAttachedToWindow()) {
            }
            screenshotShelfViewProxy = this.viewProxy;
            screenshotShelfViewProxy.reset();
            if (screenshotShelfViewProxy.view.isAttachedToWindow()) {
            }
            screenshotShelfViewProxy.packageName = this.packageName;
            ScreenshotActionsController screenshotActionsController22 = this.actionsController;
            screenshotActionsController22.getClass();
            final UUID uuidRandomUUID22 = UUID.randomUUID();
            screenshotActionsController22.currentScreenshotId = uuidRandomUUID22;
            Map map22 = screenshotActionsController22.actionProviders;
            uuidRandomUUID22.getClass();
            map22.put(uuidRandomUUID22, ((DaggerReferenceGlobalRootComponent.ReferenceSysUIComponentImpl.SwitchingProvider.AnonymousClass144) screenshotActionsController22.actionsProviderFactory).create(uuidRandomUUID22, screenshotData, screenshotActionsController22.actionExecutor, screenshotActionsController22.new ActionsCallback(uuidRandomUUID22)));
            screenCaptureHelper = this.screenCaptureHelper;
            if (screenCaptureHelper != null) {
            }
            ZonedDateTime zonedDateTimeNow22 = ZonedDateTime.now(ZoneId.systemDefault());
            zonedDateTimeNow22.getClass();
            ScreenCaptureHelper screenCaptureHelper622 = this.screenCaptureHelper;
            screenCaptureHelper622.getClass();
            Context context422 = screenCaptureHelper622.displayContext;
            topMostApplicationPackage = ScreenshotUtils.getTopMostApplicationPackage(context422);
            if (topMostApplicationPackage != null) {
            }
            ScreenCaptureHelper screenCaptureHelper722 = this.screenCaptureHelper;
            if (screenCaptureHelper722 == null) {
            }
            if (systemService instanceof SemStatusBarManager) {
            }
            if (strReplaceAll != null) {
            }
            strM = AnimatorInflaterCompat$$ExternalSyntheticOutline0.m("Screenshot_", String.format("%1$tY%<tm%<td_%<tH%<tM%<tS", zonedDateTimeNow22), "_", strReplaceAll);
            if (strReplaceAll != null) {
            }
            this.imageDisplayName = strM;
            if (isFormatPNG(this.context)) {
            }
            this.imageFileName = strM2;
            screenshotSaveInfo = ScreenshotUtils.getScreenshotSaveInfo(this.context);
            if (screenshotSaveInfo[0].equals("external_primary")) {
            }
            File file22 = new File(string);
            str4 = this.imageFileName;
            if (str4 == null) {
            }
            this.imageFilePath = new File(file22, str4).getAbsolutePath();
            str5 = this.imageDisplayName;
            if (str5 == null) {
            }
            this.thumbnailImageFilePath = new File(file22, ContentInViewNode$Request$$ExternalSyntheticOutline0.m("thumbnail_", str5, ".jpg")).getAbsolutePath();
            long jCurrentTimeMillis32 = System.currentTimeMillis();
            remoteScreenshotInterface = new RemoteScreenshotInterface();
            this.screenshotInterface = remoteScreenshotInterface;
            WindowContext windowContext322 = this.context;
            ScreenshotController$startCaptureAppRemoteServiceConnection$1 screenshotController$startCaptureAppRemoteServiceConnection$132 = new ScreenshotController$startCaptureAppRemoteServiceConnection$1(jCurrentTimeMillis32, this);
            Log.d("[ScrCap]_RemoteScreenshotInterface", "connect");
            final long jCurrentTimeMillis222 = System.currentTimeMillis();
            if (remoteScreenshotInterface.mService != null) {
            }
            String lowerCase32222 = ScreenshotUtils.getScreenshotSaveInfo(this.context)[0].toLowerCase();
            String[] screenshotSaveInfo22222 = ScreenshotUtils.getScreenshotSaveInfo(this.context);
            if (screenshotSaveInfo22222[1].isEmpty()) {
            }
            long epochMilli2222 = zonedDateTimeNow22.toInstant().toEpochMilli();
            long epochSecond2222 = zonedDateTimeNow22.toEpochSecond();
            boolean zIsFormatPNG2222 = isFormatPNG(this.context);
            if (zIsFormatPNG2222) {
            }
            Bitmap bitmap22222 = this.screenBitmap;
            bitmap22222.getClass();
            int width2222 = bitmap22222.getWidth();
            Bitmap bitmap32222 = this.screenBitmap;
            bitmap32222.getClass();
            int height2222 = bitmap32222.getHeight();
            str6 = this.imageFilePath;
            if (str6 == null) {
            }
            long length2222 = new File(str6).length();
            str7 = this.imageFilePath;
            if (str7 == null) {
            }
            str8 = this.imageDisplayName;
            if (str8 == null) {
            }
            str9 = this.imageFileName;
            if (str9 == null) {
            }
            StringBuilder sbM32222 = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("setScreenshotMediaData:  volumeName= ", lowerCase32222, " relativePath= ", str21, " mImageFilePath= ");
            MoveResult$$ExternalSyntheticOutline0.m(sbM32222, str7, " mImageDisplayName= ", str8, " mImageFileName= ");
            sbM32222.append(str9);
            sbM32222.append(" imageTime= ");
            sbM32222.append(epochMilli2222);
            sbM32222.append(" dateSeconds $=dateSeconds mimeType= ");
            sbM32222.append(str22);
            sbM32222.append(" imageWidth= ");
            sbM32222.append(width2222);
            sbM32222.append(" imageHeight= ");
            sbM32222.append(height2222);
            sbM32222.append(" size= ");
            sbM32222.append(length2222);
            Log.i(str15, sbM32222.toString());
            str10 = this.imageFilePath;
            if (str10 == null) {
            }
            str11 = this.imageDisplayName;
            if (str11 == null) {
            }
            str12 = this.imageFileName;
            if (str12 == null) {
            }
            SmartClipDataExtractor.WebData webData42222 = this.webData;
            ImageExporter imageExporter2222 = this.imageExporter;
            imageExporter2222.getClass();
            ImageExporter.mImageFileRelativePath = str21;
            ImageExporter.mVolumeName = lowerCase32222;
            ImageExporter.mImageFilePath = str10;
            ImageExporter.mImageDisplayName = str11;
            ImageExporter.mImageFileName = str12;
            ImageExporter.mImageTime = epochMilli2222;
            ImageExporter.mSecDate = epochSecond2222;
            ImageExporter.mMimeType = str22;
            ImageExporter.mWidth = width2222;
            ImageExporter.mHeight = height2222;
            ImageExporter.mSize = length2222;
            ImageExporter.mScreenshotsWebData = webData42222;
            imageExporter2222.mCompressFormat = zIsFormatPNG2222 ? Bitmap.CompressFormat.PNG : Bitmap.CompressFormat.JPEG;
            JSONObject jSONObject2222 = new JSONObject();
            windowContext = this.context;
            if (windowContext != null) {
            }
            jSONObject2222.put("comp", strFlattenToShortString);
            ImageExporter.mCapturedAppInfo = Base64.encodeToString(jSONObject2222.toString().getBytes(Charsets.UTF_8), 2);
            final Consumer<ImageExporter.Result> consumer22222 = new Consumer() { // from class: com.android.systemui.screenshot.ScreenshotController.handleScreenshot.1
                @Override // java.util.function.Consumer
                public final void accept(Object obj2) {
                    boolean z5;
                    ImageExporter.Result result = (ImageExporter.Result) obj2;
                    if (result.uri != null) {
                        this.actionsController.setCompletedScreenshot(uuidRandomUUID22, new ScreenshotSavedResult(result.uri, screenshotData.userHandle, result.timestamp));
                        ScreenshotController screenshotController = this;
                        BixbyShareController bixbyShareController = screenshotController.bixbyShareController;
                        if (bixbyShareController == null || !(z5 = bixbyShareController.isBixbyCaptureShared) || screenshotController.isSavingFailed) {
                            return;
                        }
                        WindowContext windowContext4 = screenshotController.context;
                        Uri uri = result.uri;
                        boolean zIsFormatPNG22222 = ScreenshotController.isFormatPNG(windowContext4);
                        String str23 = BixbyShareController.TAG;
                        if (uri == null || !z5) {
                            Log.e(str23, "isBixbyCaptureShared: " + z5 + " uri: " + uri);
                            return;
                        }
                        Intent intent322 = new Intent("com.samsung.android.systemui.screenshot.SCREENSHOT_URI");
                        String string3 = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, ContentUris.parseId(uri)).toString();
                        intent322.putExtra("contentUri", string3);
                        KeyguardPluginControllerImpl$$ExternalSyntheticOutline0.m("Send broadcast : screenshot contentUri = ", string3, str23);
                        windowContext4.sendBroadcast(intent322, "com.samsung.android.systemui.screenshot.permission.RECEIVE_SCREENSHOT_URI");
                        StringBuilder sb3 = new StringBuilder("startChooserActivity: sharePackageName = ");
                        String str24 = bixbyShareController.sharePackageName;
                        sb3.append(str24);
                        sb3.append(" shareActivityName = ");
                        String str25 = bixbyShareController.shareActivityName;
                        sb3.append(str25);
                        sb3.append(" uri = ");
                        sb3.append(uri);
                        Log.i(str23, sb3.toString());
                        Intent intent4 = new Intent("android.intent.action.SEND");
                        if (str24 != null) {
                            if (str25 != null) {
                                intent4.setComponent(new ComponentName(str24, str25));
                            } else {
                                intent4.setPackage(str24);
                            }
                        }
                        intent4.setType(zIsFormatPNG22222 ? "image/png" : "image/jpeg");
                        intent4.putExtra("android.intent.extra.STREAM", uri);
                        intent4.setFlags(453509121);
                        if (str24 != null) {
                            PackageManager packageManager3 = windowContext4.getPackageManager();
                            List<ResolveInfo> listQueryIntentActivities = packageManager3 != null ? packageManager3.queryIntentActivities(intent4, 0) : null;
                            Integer numValueOf = listQueryIntentActivities != null ? Integer.valueOf(listQueryIntentActivities.size()) : null;
                            numValueOf.getClass();
                            if (numValueOf.intValue() > 0) {
                                windowContext4.startActivity(intent4);
                                return;
                            }
                        }
                        Intent intent5 = new Intent("android.intent.action.SEND");
                        intent5.setType(zIsFormatPNG22222 ? "image/png" : "image/jpeg");
                        intent5.putExtra("android.intent.extra.STREAM", uri);
                        Intent intentCreateChooser = Intent.createChooser(intent5, null);
                        intentCreateChooser.setFlags(453509121);
                        windowContext4.startActivity(intentCreateChooser);
                    }
                }
            };
            final ListenableFuture safeFutureExport2222 = this.imageExporter.export(this.bgExecutor, uuidRandomUUID22, screenshotData.bitmap, screenshotData.userHandle, this.display.getDisplayId());
            safeFutureExport2222.delegate.addListener(new Runnable() { // from class: com.android.systemui.screenshot.ScreenshotController$saveScreenshotInBackground$1
                @Override // java.lang.Runnable
                public final void run() throws RemoteException {
                    try {
                        ImageExporter.Result result = (ImageExporter.Result) safeFutureExport2222.get();
                        Log.d(ScreenshotController.TAG, "Saved screenshot: " + result);
                        ScreenshotController.access$logScreenshotResultStatus(this, result.uri, screenshotData.userHandle);
                        consumer22222.accept(result);
                        consumer.accept(result.uri);
                    } catch (Exception e6) {
                        this.isSavingFailed = true;
                        Log.d(ScreenshotController.TAG, "Failed to store screenshot", e6);
                        consumer.accept(null);
                    }
                    ScreenshotController screenshotController = this;
                    if (screenshotController.isScreenshotDismissed) {
                        return;
                    }
                    if (!screenshotController.bgExecutor.isShutdown()) {
                        final ScreenshotController screenshotController2 = this;
                        screenshotController2.bgExecutor.execute(new Runnable() { // from class: com.android.systemui.screenshot.ScreenshotController$saveScreenshotInBackground$1.1
                            @Override // java.lang.Runnable
                            public final void run() {
                                ScreenshotController screenshotController3 = screenshotController2;
                                if (screenshotController3.screenshotInterface != null) {
                                    synchronized (screenshotController3.remoteServiceConnectionLock) {
                                        if (screenshotController3.isRemoteScreenshotConnectionListenerInvoked) {
                                            Unit unit2222 = Unit.INSTANCE;
                                        } else {
                                            long jCurrentTimeMillis322 = System.currentTimeMillis();
                                            try {
                                                screenshotController3.remoteServiceConnectionLock.wait(1000L);
                                            } catch (InterruptedException e7) {
                                                Log.e(ScreenshotController.TAG, "Exception thrown during waiting remote service connection : " + e7);
                                            }
                                            Log.i(ScreenshotController.TAG, "Remote screenshot connection waiting time = " + (System.currentTimeMillis() - jCurrentTimeMillis322));
                                            Unit unit22222 = Unit.INSTANCE;
                                        }
                                    }
                                    Bundle bundle22 = new Bundle();
                                    bundle22.putCharSequenceArrayList("notifiedApps", (ArrayList) screenshotController3.notifiedApps);
                                    RemoteScreenshotInterface remoteScreenshotInterface2 = screenshotController3.screenshotInterface;
                                    remoteScreenshotInterface2.getClass();
                                    long j = screenshotController3.screenshotTransactionId;
                                    String str23 = screenshotController3.imageFilePath;
                                    if (str23 == null) {
                                        str23 = null;
                                    }
                                    Log.d("[ScrCap]_RemoteScreenshotInterface", "notifyGlobalScreenshotFinished");
                                    IScreenshotService iScreenshotService = remoteScreenshotInterface2.mService;
                                    if (iScreenshotService != null) {
                                        try {
                                            ((IScreenshotService.Stub.Proxy) iScreenshotService).onGlobalScreenshotFinished(j, str23, bundle22);
                                        } catch (Exception e8) {
                                            Log.e("[ScrCap]_RemoteScreenshotInterface", "notifyGlobalScreenshotFinished : e=" + e8);
                                            Log.e("[ScrCap]_RemoteScreenshotInterface", e8.toString());
                                        }
                                    } else {
                                        Log.e("[ScrCap]_RemoteScreenshotInterface", "notifyGlobalScreenshotFinished : No service connection");
                                    }
                                    RemoteScreenshotInterface remoteScreenshotInterface3 = screenshotController3.screenshotInterface;
                                    remoteScreenshotInterface3.getClass();
                                    remoteScreenshotInterface3.disconnect();
                                }
                            }
                        });
                    }
                    ScreenshotController screenshotController3 = this;
                    screenshotController3.isScreenshotSaveTaskCompleted = true;
                    if (ScreenshotController.isAnimationRunning) {
                        return;
                    }
                    screenshotController3.finishDismiss$1();
                }
            }, this.mainExecutor);
            screenCaptureHelper2 = this.screenCaptureHelper;
            screenCaptureHelper2.getClass();
            if (!screenCaptureHelper2.isShowScreenshotAnimation(this.context)) {
            }
        }
        if (this.screenCaptureHelper == null) {
            Log.e(str14, "handleScreenshot: screenCaptureHelper was null");
            ((TakeScreenshotService.RequestCallbackImpl) requestCallback).reportError();
            return;
        }
        int displayId = this.display.getDisplayId();
        ScreenCaptureHelper screenCaptureHelper9 = this.screenCaptureHelper;
        screenCaptureHelper9.getClass();
        SemImageCaptureImpl semImageCaptureImpl = this.sepImageCapture;
        Context context5 = semImageCaptureImpl.context;
        if (context5 != null) {
            if (AliveShotImageUtils.isEdgePanelPresent(context5) && (cursorQuery = context5.getContentResolver().query(AliveShotImageUtils.HANDLER_TRANSPARENCY_CONTENT_URI, null, null, null, null)) != null) {
                try {
                    int i16 = (cursorQuery.getCount() == 0 || !cursorQuery.moveToFirst()) ? 0 : cursorQuery.getInt(0);
                    cursorQuery.close();
                    i2 = i16;
                } catch (Throwable th) {
                    cursorQuery.close();
                    throw th;
                }
            }
            context = semImageCaptureImpl.context;
            if (AliveShotImageUtils.isEdgePanelPresent(context)) {
                Log.i("Screenshot", "Hide edge panel");
                AliveShotImageUtils.resetEdgeTransparency(100, context);
                try {
                    Thread.sleep(100L);
                } catch (InterruptedException unused) {
                    Log.e("Screenshot", "InterruptedException occurred");
                }
            }
            int i17 = screenCaptureHelper9.builtInDisplayId;
            i3 = screenCaptureHelper9.screenCaptureType;
            Rect screenshotRectToCapture = screenCaptureHelper9.getScreenshotRectToCapture();
            int i18 = screenCaptureHelper9.screenWidth;
            int i19 = screenCaptureHelper9.screenHeight;
            str = " ";
            str2 = str14;
            if (Settings.System.getIntForUser(semImageCaptureImpl.context.getContentResolver(), SettingsHelper.INDEX_ONE_HAND_MODE_RUNNING, 0, -2) != 1) {
                String string3 = Settings.System.getString(semImageCaptureImpl.context.getContentResolver(), SettingsHelper.INDEX_ONE_HAND_RUNNING_INFO);
                MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("getOneHandModeInfo() reduce_screen_running_info :", string3, "Screenshot");
                if (string3 != null) {
                    StringTokenizer stringTokenizer = new StringTokenizer(string3, ";");
                    if (stringTokenizer.hasMoreTokens()) {
                        i5 = Integer.parseInt(stringTokenizer.nextToken());
                        i6 = Integer.parseInt(stringTokenizer.nextToken());
                        f = Float.parseFloat(stringTokenizer.nextToken());
                    } else {
                        f = 0.0f;
                        i5 = 0;
                        i6 = 0;
                    }
                    float f2 = i5;
                    float[] fArr = new float[3];
                    fArr[0] = f2;
                    fArr[1] = i6;
                    fArr[i] = f;
                    int i20 = (int) fArr[0];
                    int i21 = (int) fArr[1];
                    float f3 = fArr[i];
                    screenshotRectToCapture.set(i20, i21, ((int) (i18 * f3)) + i20, ((int) (i19 * f3)) + i21);
                    i4 = 2600;
                    z = true;
                }
                bitmap = screenshotData.bitmap;
                if (bitmap == null) {
                }
            } else {
                i4 = 2015;
                z = false;
            }
            if (i3 == 100) {
                i4 = 1;
            }
            StringBuilder sbM4 = MutableObjectList$$ExternalSyntheticOutline0.m(i17, i4, "semCaptureDisplay: takeScreenshot param, builtInDisplayId=", ", targetWindowType=", ", containTargetWindow=");
            sbM4.append(z);
            sbM4.append(", rectToCapture=");
            sbM4.append(screenshotRectToCapture);
            String string4 = sbM4.toString();
            String str23 = semImageCaptureImpl.TAG;
            Log.i(str23, string4);
            ScreenshotResult screenshotResultTakeScreenshotToTargetWindow = semImageCaptureImpl.windowManager.takeScreenshotToTargetWindow(i17, i4, z, screenshotRectToCapture, i18, i19, false, false);
            Bitmap capturedBitmap = screenshotResultTakeScreenshotToTargetWindow.getCapturedBitmap();
            failedReason = screenshotResultTakeScreenshotToTargetWindow.getFailedReason();
            String securedWindowName = screenshotResultTakeScreenshotToTargetWindow.getSecuredWindowName();
            String targetWindowName = screenshotResultTakeScreenshotToTargetWindow.getTargetWindowName();
            Log.i(str23, "semCaptureDisplay: takeScreenshot result, bitmapIsNull=" + (capturedBitmap != null) + ", failedReason=" + failedReason + ", resultSecuredWindowName=" + securedWindowName + ", resultTargetWindowName=" + targetWindowName);
            if (failedReason != 32) {
                bitmapCaptureDisplay = capturedBitmap;
                try {
                    Unit unit = Unit.INSTANCE;
                } catch (RemoteException unused2) {
                    capturedBitmap = bitmapCaptureDisplay;
                    Log.i(str23, "RemoteException is occurred.");
                    bitmapCaptureDisplay = capturedBitmap;
                    Log.i(str23, "useIdentityTransform=false");
                    SemScreenshotResult semScreenshotResult2 = new SemScreenshotResult(bitmapCaptureDisplay == null ? screenCaptureHelper9.onPostScreenshot(bitmapCaptureDisplay) : null, failedReason, targetWindowName, securedWindowName);
                    context2 = semImageCaptureImpl.context;
                    if (AliveShotImageUtils.isEdgePanelPresent(context2)) {
                    }
                    screenshotData.bitmap = semScreenshotResult2.bitmap;
                    semScreenshotResult = semScreenshotResult2;
                    bitmap = screenshotData.bitmap;
                    if (bitmap == null) {
                    }
                }
                Log.i(str23, "useIdentityTransform=false");
                SemScreenshotResult semScreenshotResult22 = new SemScreenshotResult(bitmapCaptureDisplay == null ? screenCaptureHelper9.onPostScreenshot(bitmapCaptureDisplay) : null, failedReason, targetWindowName, securedWindowName);
                context2 = semImageCaptureImpl.context;
                if (AliveShotImageUtils.isEdgePanelPresent(context2)) {
                    Log.i("Screenshot", "Show edge panel");
                    AliveShotImageUtils.resetEdgeTransparency(i2, context2);
                }
                screenshotData.bitmap = semScreenshotResult22.bitmap;
                semScreenshotResult = semScreenshotResult22;
            } else {
                if (semImageCaptureImpl.devicePolicyManager.getScreenCaptureDisabled(null, -1)) {
                    Log.i(str23, "semCaptureDisplay: screenshot disabled by dpm.");
                } else {
                    if (EdmUtils.isScreenCaptureEnabled(semImageCaptureImpl.context)) {
                        bitmapCaptureDisplay = semImageCaptureImpl.captureDisplay(displayId, screenshotRectToCapture);
                    }
                    Unit unit2 = Unit.INSTANCE;
                    Log.i(str23, "useIdentityTransform=false");
                    SemScreenshotResult semScreenshotResult222 = new SemScreenshotResult(bitmapCaptureDisplay == null ? screenCaptureHelper9.onPostScreenshot(bitmapCaptureDisplay) : null, failedReason, targetWindowName, securedWindowName);
                    context2 = semImageCaptureImpl.context;
                    if (AliveShotImageUtils.isEdgePanelPresent(context2)) {
                    }
                    screenshotData.bitmap = semScreenshotResult222.bitmap;
                    semScreenshotResult = semScreenshotResult222;
                }
                bitmapCaptureDisplay = capturedBitmap;
                Unit unit22 = Unit.INSTANCE;
                Log.i(str23, "useIdentityTransform=false");
                SemScreenshotResult semScreenshotResult2222 = new SemScreenshotResult(bitmapCaptureDisplay == null ? screenCaptureHelper9.onPostScreenshot(bitmapCaptureDisplay) : null, failedReason, targetWindowName, securedWindowName);
                context2 = semImageCaptureImpl.context;
                if (AliveShotImageUtils.isEdgePanelPresent(context2)) {
                }
                screenshotData.bitmap = semScreenshotResult2222.bitmap;
                semScreenshotResult = semScreenshotResult2222;
            }
            bitmap = screenshotData.bitmap;
            if (bitmap == null) {
            }
        } else {
            Uri uri = AliveShotImageUtils.HANDLER_TRANSPARENCY_CONTENT_URI;
        }
        i2 = 0;
        context = semImageCaptureImpl.context;
        if (AliveShotImageUtils.isEdgePanelPresent(context)) {
        }
        int i172 = screenCaptureHelper9.builtInDisplayId;
        i3 = screenCaptureHelper9.screenCaptureType;
        Rect screenshotRectToCapture2 = screenCaptureHelper9.getScreenshotRectToCapture();
        int i182 = screenCaptureHelper9.screenWidth;
        int i192 = screenCaptureHelper9.screenHeight;
        str = " ";
        str2 = str14;
        if (Settings.System.getIntForUser(semImageCaptureImpl.context.getContentResolver(), SettingsHelper.INDEX_ONE_HAND_MODE_RUNNING, 0, -2) != 1) {
        }
        if (i3 == 100) {
        }
        StringBuilder sbM42 = MutableObjectList$$ExternalSyntheticOutline0.m(i172, i4, "semCaptureDisplay: takeScreenshot param, builtInDisplayId=", ", targetWindowType=", ", containTargetWindow=");
        sbM42.append(z);
        sbM42.append(", rectToCapture=");
        sbM42.append(screenshotRectToCapture2);
        String string42 = sbM42.toString();
        String str232 = semImageCaptureImpl.TAG;
        Log.i(str232, string42);
        ScreenshotResult screenshotResultTakeScreenshotToTargetWindow2 = semImageCaptureImpl.windowManager.takeScreenshotToTargetWindow(i172, i4, z, screenshotRectToCapture2, i182, i192, false, false);
        Bitmap capturedBitmap2 = screenshotResultTakeScreenshotToTargetWindow2.getCapturedBitmap();
        failedReason = screenshotResultTakeScreenshotToTargetWindow2.getFailedReason();
        String securedWindowName2 = screenshotResultTakeScreenshotToTargetWindow2.getSecuredWindowName();
        String targetWindowName2 = screenshotResultTakeScreenshotToTargetWindow2.getTargetWindowName();
        if (capturedBitmap2 != null) {
        }
        Log.i(str232, "semCaptureDisplay: takeScreenshot result, bitmapIsNull=" + (capturedBitmap2 != null) + ", failedReason=" + failedReason + ", resultSecuredWindowName=" + securedWindowName2 + ", resultTargetWindowName=" + targetWindowName2);
        if (failedReason != 32) {
        }
        bitmap = screenshotData.bitmap;
        if (bitmap == null) {
        }
    }

    @Override // com.android.systemui.screenshot.InteractiveScreenshotHandler
    public final void initSemScreenshotLayout() {
        if (this.isSemScreenshotLayoutInitialized) {
            Log.i(TAG, "initSemScreenshotLayout: SemScreenshotLayout is already initialized.");
            return;
        }
        ScreenCaptureHelper screenCaptureHelper = this.screenCaptureHelper;
        screenCaptureHelper.getClass();
        SemScreenshotLayout semScreenshotLayout = (SemScreenshotLayout) ((LayoutInflater) new ContextThemeWrapper(screenCaptureHelper.displayContext, 2132018761).getSystemService("layout_inflater")).inflate(R.layout.layout_sem_screenshot, (ViewGroup) null);
        this.semScreenshotLayout = semScreenshotLayout;
        ScreenshotSelectorView screenshotSelectorView = (ScreenshotSelectorView) (semScreenshotLayout != null ? semScreenshotLayout : null).findViewById(R.id.global_screenshot_selector);
        this.screenshotSelectorView = screenshotSelectorView;
        if (screenshotSelectorView != null) {
            screenshotSelectorView.setFocusable(true);
        }
        ScreenshotSelectorView screenshotSelectorView2 = this.screenshotSelectorView;
        if (screenshotSelectorView2 != null) {
            screenshotSelectorView2.setFocusableInTouchMode(true);
        }
        this.isSemScreenshotLayoutInitialized = true;
    }

    @Override // com.android.systemui.screenshot.InteractiveScreenshotHandler
    public final boolean isAnimationRunning() {
        boolean z;
        synchronized (shutterEffectLock) {
            try {
                if (isAnimationRunning) {
                    Log.w(TAG, "handleRequest: isAnimationRunning");
                }
                z = isAnimationRunning;
            } catch (Throwable th) {
                throw th;
            }
        }
        return z;
    }

    @Override // com.android.systemui.screenshot.InteractiveScreenshotHandler
    public final boolean isPendingSharedTransition() {
        return this.actionExecutor.isPendingSharedTransition;
    }

    @Override // com.android.systemui.screenshot.InteractiveScreenshotHandler
    public final boolean isScreenshotSelectorViewVisible() {
        ScreenshotSelectorView screenshotSelectorView = this.screenshotSelectorView;
        return screenshotSelectorView != null && screenshotSelectorView.getVisibility() == 0;
    }

    @Override // com.android.systemui.screenshot.InteractiveScreenshotHandler
    public final boolean isSnackBarShowing() {
        boolean z;
        synchronized (shutterEffectLock) {
            try {
                if (isSnackBarShowing) {
                    Log.w(TAG, "handleRequest: isSnackBarShowing");
                }
                z = isSnackBarShowing;
            } catch (Throwable th) {
                throw th;
            }
        }
        return z;
    }

    @Override // com.android.systemui.screenshot.InteractiveScreenshotHandler
    public final void onDestroy() {
        removeWindow();
        ScreenshotSoundControllerImpl screenshotSoundControllerImpl = (ScreenshotSoundControllerImpl) this.screenshotSoundController;
        screenshotSoundControllerImpl.getClass();
        CoroutineTracingKt.launchTraced$default(screenshotSoundControllerImpl.coroutineScope, null, null, new ScreenshotSoundControllerImpl$releaseScreenshotSoundAsync$1(screenshotSoundControllerImpl, null), 7);
        this.broadcastDispatcher.unregisterReceiver(this.copyBroadcastReceiver);
        this.context.release();
        this.bgExecutor.shutdown();
        ScreenCaptureHelper screenCaptureHelper = this.screenCaptureHelper;
        if (screenCaptureHelper != null && screenCaptureHelper.screenCaptureType == 2 && isScreenshotSelectorViewVisible()) {
            ScreenshotSelectorView screenshotSelectorView = this.screenshotSelectorView;
            if (screenshotSelectorView != null) {
                screenshotSelectorView.setVisibility(8);
            }
            WindowManager windowManager = this.displayContextWindowManager;
            if (windowManager == null) {
                windowManager = null;
            }
            SemScreenshotLayout semScreenshotLayout = this.semScreenshotLayout;
            windowManager.removeView(semScreenshotLayout != null ? semScreenshotLayout : null);
        }
    }

    @Override // com.android.systemui.screenshot.InteractiveScreenshotHandler
    public final void removeWindow() {
        ScreenshotWindow screenshotWindow = this.window;
        View viewPeekDecorView = screenshotWindow.window.peekDecorView();
        if (viewPeekDecorView != null && viewPeekDecorView.isAttachedToWindow()) {
            screenshotWindow.windowManager.removeViewImmediate(viewPeekDecorView);
        }
        this.viewProxy.stopInputListening();
    }

    @Override // com.android.systemui.screenshot.InteractiveScreenshotHandler
    public final void requestDismissal(ScreenshotEvent screenshotEvent) {
        this.viewProxy.requestDismissal(screenshotEvent, null);
    }

    @Override // com.android.systemui.screenshot.InteractiveScreenshotHandler
    public final void setPartialScreenshotSelector(final Bundle bundle, final ScreenshotData screenshotData, final TakeScreenshotExecutorImpl$sam$java_util_function_Consumer$0 takeScreenshotExecutorImpl$sam$java_util_function_Consumer$0, final TakeScreenshotService.RequestCallback requestCallback) {
        ScreenshotController screenshotController;
        ScreenshotSelectorView screenshotSelectorView = this.screenshotSelectorView;
        if (screenshotSelectorView == null || !screenshotSelectorView.isAttachedToWindow()) {
            ScreenCaptureHelper screenCaptureHelper = this.screenCaptureHelper;
            screenCaptureHelper.getClass();
            Context context = screenCaptureHelper.displayContext;
            context.getClass();
            this.displayContextWindowManager = (WindowManager) context.getSystemService("window");
            WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(-1, -1, 0, 0, 2036, 84411648, -3);
            layoutParams.layoutInDisplayCutoutMode = 1;
            layoutParams.setFitInsetsTypes(0);
            layoutParams.setTitle("ScreenshotSelectorView");
            SemScreenshotLayout semScreenshotLayout = this.semScreenshotLayout;
            if (semScreenshotLayout == null) {
                semScreenshotLayout = null;
            }
            ImageView imageView = semScreenshotLayout.mScreenshotImageView;
            if (imageView != null) {
                imageView.setImageBitmap(null);
                imageView.setVisibility(8);
            }
            SemScreenshotLayout semScreenshotLayout2 = this.semScreenshotLayout;
            if (semScreenshotLayout2 == null) {
                semScreenshotLayout2 = null;
            }
            IBinder windowToken = semScreenshotLayout2.getWindowToken();
            String str = TAG;
            if (windowToken != null) {
                Log.i(str, "setPartialScreenshotSelector semScreenshot view window token is not null");
                return;
            }
            try {
                WindowManager windowManager = this.displayContextWindowManager;
                if (windowManager == null) {
                    windowManager = null;
                }
                SemScreenshotLayout semScreenshotLayout3 = this.semScreenshotLayout;
                if (semScreenshotLayout3 == null) {
                    semScreenshotLayout3 = null;
                }
                windowManager.addView(semScreenshotLayout3, layoutParams);
                ScreenshotSelectorView screenshotSelectorView2 = this.screenshotSelectorView;
                if (screenshotSelectorView2 != null) {
                    screenshotController = this;
                    screenshotSelectorView2.setOnTouchListener(new View.OnTouchListener() { // from class: com.android.systemui.screenshot.ScreenshotController.setPartialScreenshotSelector.1
                        @Override // android.view.View.OnTouchListener
                        public final boolean onTouch(View view, MotionEvent motionEvent) {
                            ScreenshotSelectorView screenshotSelectorView3 = (ScreenshotSelectorView) view;
                            int action = motionEvent.getAction();
                            if (action == 0) {
                                int x = (int) motionEvent.getX();
                                int y = (int) motionEvent.getY();
                                screenshotSelectorView3.getClass();
                                screenshotSelectorView3.mStartPoint = new Point(x, y);
                                screenshotSelectorView3.mSelectionRect = new Rect(x, y, x, y);
                                return true;
                            }
                            if (action != 1) {
                                if (action != 2) {
                                    return false;
                                }
                                int x2 = (int) motionEvent.getX();
                                int y2 = (int) motionEvent.getY();
                                Rect rect = screenshotSelectorView3.mSelectionRect;
                                if (rect != null) {
                                    rect.left = Math.min(screenshotSelectorView3.mStartPoint.x, x2);
                                    screenshotSelectorView3.mSelectionRect.right = Math.max(screenshotSelectorView3.mStartPoint.x, x2);
                                    screenshotSelectorView3.mSelectionRect.top = Math.min(screenshotSelectorView3.mStartPoint.y, y2);
                                    screenshotSelectorView3.mSelectionRect.bottom = Math.max(screenshotSelectorView3.mStartPoint.y, y2);
                                    screenshotSelectorView3.invalidate();
                                }
                                return true;
                            }
                            screenshotSelectorView3.setVisibility(8);
                            ScreenshotController screenshotController2 = ScreenshotController.this;
                            WindowManager windowManager2 = screenshotController2.displayContextWindowManager;
                            if (windowManager2 == null) {
                                windowManager2 = null;
                            }
                            SemScreenshotLayout semScreenshotLayout4 = screenshotController2.semScreenshotLayout;
                            if (semScreenshotLayout4 == null) {
                                semScreenshotLayout4 = null;
                            }
                            windowManager2.removeView(semScreenshotLayout4);
                            final Rect rect2 = screenshotSelectorView3.mSelectionRect;
                            if ((rect2 == null || rect2.width() != 0) && (rect2 == null || rect2.height() != 0)) {
                                final ScreenshotController screenshotController3 = ScreenshotController.this;
                                SemScreenshotLayout semScreenshotLayout5 = screenshotController3.semScreenshotLayout;
                                if (semScreenshotLayout5 == null) {
                                    semScreenshotLayout5 = null;
                                }
                                final Bundle bundle2 = bundle;
                                final ScreenshotData screenshotData2 = screenshotData;
                                final Consumer consumer = takeScreenshotExecutorImpl$sam$java_util_function_Consumer$0;
                                final TakeScreenshotService.RequestCallback requestCallback2 = requestCallback;
                                semScreenshotLayout5.post(new Runnable() { // from class: com.android.systemui.screenshot.ScreenshotController$setPartialScreenshotSelector$1$onTouch$1
                                    @Override // java.lang.Runnable
                                    public final void run() throws JSONException, InterruptedException, Resources.NotFoundException, PackageManager.NameNotFoundException, SecurityException, RemoteException, NumberFormatException {
                                        Bundle bundle3 = bundle2;
                                        if (bundle3 != null) {
                                            bundle3.putParcelable("rect", rect2);
                                        }
                                        screenshotController3.handleScreenshot(screenshotData2, consumer, requestCallback2);
                                    }
                                });
                            }
                            screenshotSelectorView3.mStartPoint = null;
                            screenshotSelectorView3.mSelectionRect = null;
                            return true;
                        }
                    });
                } else {
                    screenshotController = this;
                }
                ScreenshotSelectorView screenshotSelectorView3 = screenshotController.screenshotSelectorView;
                if (screenshotSelectorView3 != null) {
                    screenshotSelectorView3.setOnKeyListener(new View.OnKeyListener() { // from class: com.android.systemui.screenshot.ScreenshotController.setPartialScreenshotSelector.2
                        @Override // android.view.View.OnKeyListener
                        public final boolean onKey(View view, int i, KeyEvent keyEvent) {
                            if (i != 111 || keyEvent.getAction() != 1) {
                                return false;
                            }
                            view.setVisibility(8);
                            ScreenshotController screenshotController2 = ScreenshotController.this;
                            WindowManager windowManager2 = screenshotController2.displayContextWindowManager;
                            if (windowManager2 == null) {
                                windowManager2 = null;
                            }
                            SemScreenshotLayout semScreenshotLayout4 = screenshotController2.semScreenshotLayout;
                            windowManager2.removeView(semScreenshotLayout4 != null ? semScreenshotLayout4 : null);
                            return true;
                        }
                    });
                }
                SemScreenshotLayout semScreenshotLayout4 = screenshotController.semScreenshotLayout;
                (semScreenshotLayout4 != null ? semScreenshotLayout4 : null).post(new Runnable() { // from class: com.android.systemui.screenshot.ScreenshotController.setPartialScreenshotSelector.3
                    @Override // java.lang.Runnable
                    public final void run() {
                        ScreenshotSelectorView screenshotSelectorView4 = ScreenshotController.this.screenshotSelectorView;
                        if (screenshotSelectorView4 != null) {
                            screenshotSelectorView4.setVisibility(0);
                        }
                        ScreenshotSelectorView screenshotSelectorView5 = ScreenshotController.this.screenshotSelectorView;
                        if (screenshotSelectorView5 != null) {
                            screenshotSelectorView5.requestFocus();
                        }
                    }
                });
            } catch (IllegalStateException e) {
                Log.e(str, "setPartialScreenshotSelector()", e);
            }
        }
    }

    @Override // com.android.systemui.screenshot.InteractiveScreenshotHandler
    public final void setScreenCaptureHelper(ScreenCaptureHelper screenCaptureHelper) {
        this.screenCaptureHelper = screenCaptureHelper;
    }

    public final void showScreenshotErrorMessage(final SemScreenshotResult semScreenshotResult) {
        final View decorView;
        synchronized (shutterEffectLock) {
            isSnackBarShowing = true;
            Unit unit = Unit.INSTANCE;
        }
        attachSemScreenshotLayoutToWindow();
        Presentation presentation = this.presentation;
        if (presentation != null) {
            Window window = presentation.getWindow();
            window.getClass();
            decorView = window.getDecorView();
        } else {
            decorView = this.semScreenshotLayout;
            if (decorView == null) {
                decorView = null;
            }
        }
        decorView.getClass();
        decorView.post(new Runnable() { // from class: com.android.systemui.screenshot.ScreenshotController.showScreenshotErrorMessage.2
            @Override // java.lang.Runnable
            public final void run() {
                Object obj = ScreenshotController.shutterEffectLock;
                ScreenshotController screenshotController = ScreenshotController.this;
                View view = decorView;
                SemScreenshotResult semScreenshotResult2 = semScreenshotResult;
                synchronized (obj) {
                    WindowContext windowContext = screenshotController.context;
                    ScreenCaptureHelper screenCaptureHelper = screenshotController.screenCaptureHelper;
                    screenCaptureHelper.getClass();
                    new SnackbarController(windowContext, screenCaptureHelper.capturedDisplayId, new ScreenshotController$showScreenshotErrorMessage$2$1$1(screenshotController)).showScreenshotError(view, semScreenshotResult2);
                    Unit unit2 = Unit.INSTANCE;
                }
            }
        });
    }
}
