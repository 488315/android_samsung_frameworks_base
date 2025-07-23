package com.android.systemui.screenshot;

import android.app.Presentation;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.Settings;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.window.WindowContext;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.internal.logging.UiEventLogger;
import com.android.settingslib.applications.InterestingConfigChanges;
import com.android.systemui.R;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.broadcast.BroadcastSender;
import com.android.systemui.screenshot.ActionExecutor;
import com.android.systemui.screenshot.ScreenshotActionsController;
import com.android.systemui.screenshot.ScreenshotNotificationsController;
import com.android.systemui.screenshot.ScreenshotShelfViewProxy;
import com.android.systemui.screenshot.ScreenshotWindow;
import com.android.systemui.screenshot.TakeScreenshotService;
import com.android.systemui.screenshot.scroll.ScrollCaptureExecutor;
import com.android.systemui.screenshot.sep.BixbyShareController;
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
import com.samsung.android.app.smartcapture.screenshot.lib.RemoteScreenshotInterface;
import com.samsung.systemui.splugins.edgelightingplus.PluginEdgeLightingPlus;
import com.sec.ims.volte2.data.VolteConstants;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        ScreenshotWindow create = factory.create(display);
        this.window = create;
        WindowContext context2 = create.window.getContext();
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
            public final void onDismiss() {
                String str = ScreenshotController.TAG;
                ScreenshotController.this.finishDismiss$1();
            }

            @Override // com.android.systemui.screenshot.ScreenshotShelfViewProxy.ScreenshotViewCallback
            public final void onTouchOutside() {
                View peekDecorView;
                ScreenshotWindow screenshotWindow = ScreenshotController.this.window;
                WindowManager.LayoutParams layoutParams = screenshotWindow.params;
                int i = layoutParams.flags;
                int i2 = i | 8;
                layoutParams.flags = i2;
                if (i2 == i || (peekDecorView = screenshotWindow.window.peekDecorView()) == null || !peekDecorView.isAttachedToWindow()) {
                    return;
                }
                screenshotWindow.windowManager.updateViewLayout(peekDecorView, screenshotWindow.params);
            }

            @Override // com.android.systemui.screenshot.ScreenshotShelfViewProxy.ScreenshotViewCallback
            public final void onUserInteraction() {
                ScreenshotController.this.screenshotHandler.resetTimeout();
            }
        };
        create.window.setContentView(screenshotShelfView);
        ActionExecutor create2 = factory5.create(create.window, proxy, new Function0() { // from class: com.android.systemui.screenshot.ScreenshotController$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                String str = ScreenshotController.TAG;
                ScreenshotController.this.finishDismiss$1();
                return Unit.INSTANCE;
            }
        });
        this.actionExecutor = create2;
        this.actionsController = factory4.getController(create2);
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

    public final void finishDismiss$1() {
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

    /* JADX WARN: Can't wrap try/catch for region: R(32:302|(2:304|(33:308|309|(1:382)(1:313)|314|315|(3:317|318|319)|322|(4:324|(2:326|(2:328|329))|379|329)(1:380)|330|(1:332)|333|334|335|337|338|340|341|342|343|(1:345)(1:370)|346|347|(2:349|(1:351)(2:352|(10:354|355|356|357|(1:359)(1:364)|360|(1:362)|363|20|(0)(0))))|368|355|356|357|(0)(0)|360|(0)|363|20|(0)(0)))(1:387)|386|315|(0)|322|(0)(0)|330|(0)|333|334|335|337|338|340|341|342|343|(0)(0)|346|347|(0)|368|355|356|357|(0)(0)|360|(0)|363|20|(0)(0)) */
    /* JADX WARN: Code restructure failed: missing block: B:366:0x0226, code lost:
    
        r5 = r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:367:0x0233, code lost:
    
        android.util.Log.i(r12, "RemoteException is occurred.");
        r0 = r5;
     */
    /* JADX WARN: Code restructure failed: missing block: B:372:0x0228, code lost:
    
        r1 = null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:374:0x022a, code lost:
    
        r1 = null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:375:0x022b, code lost:
    
        r7 = null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:376:0x022d, code lost:
    
        r1 = null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:377:0x022e, code lost:
    
        r6 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:378:0x0230, code lost:
    
        r1 = null;
        r5 = null;
     */
    /* JADX WARN: Removed duplicated region for block: B:103:0x0644  */
    /* JADX WARN: Removed duplicated region for block: B:108:0x06a7  */
    /* JADX WARN: Removed duplicated region for block: B:111:0x06b7  */
    /* JADX WARN: Removed duplicated region for block: B:114:0x06eb  */
    /* JADX WARN: Removed duplicated region for block: B:118:0x07a5  */
    /* JADX WARN: Removed duplicated region for block: B:121:0x07be  */
    /* JADX WARN: Removed duplicated region for block: B:124:0x07dd  */
    /* JADX WARN: Removed duplicated region for block: B:127:0x07eb  */
    /* JADX WARN: Removed duplicated region for block: B:130:0x07f0  */
    /* JADX WARN: Removed duplicated region for block: B:133:0x07f5  */
    /* JADX WARN: Removed duplicated region for block: B:136:0x0844  */
    /* JADX WARN: Removed duplicated region for block: B:139:0x0849  */
    /* JADX WARN: Removed duplicated region for block: B:142:0x084e  */
    /* JADX WARN: Removed duplicated region for block: B:145:0x0870  */
    /* JADX WARN: Removed duplicated region for block: B:148:0x0880  */
    /* JADX WARN: Removed duplicated region for block: B:164:0x090b  */
    /* JADX WARN: Removed duplicated region for block: B:173:0x0926  */
    /* JADX WARN: Removed duplicated region for block: B:183:0x0873  */
    /* JADX WARN: Removed duplicated region for block: B:184:0x07c1  */
    /* JADX WARN: Removed duplicated region for block: B:185:0x07a8  */
    /* JADX WARN: Removed duplicated region for block: B:186:0x06f3  */
    /* JADX WARN: Removed duplicated region for block: B:197:0x0753  */
    /* JADX WARN: Removed duplicated region for block: B:200:0x0771  */
    /* JADX WARN: Removed duplicated region for block: B:204:0x075c  */
    /* JADX WARN: Removed duplicated region for block: B:216:0x0669  */
    /* JADX WARN: Removed duplicated region for block: B:219:0x0625  */
    /* JADX WARN: Removed duplicated region for block: B:223:0x05e3  */
    /* JADX WARN: Removed duplicated region for block: B:227:0x05bf  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0265  */
    /* JADX WARN: Removed duplicated region for block: B:233:0x04d2  */
    /* JADX WARN: Removed duplicated region for block: B:234:0x0435  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0279  */
    /* JADX WARN: Removed duplicated region for block: B:317:0x00e4  */
    /* JADX WARN: Removed duplicated region for block: B:324:0x011b  */
    /* JADX WARN: Removed duplicated region for block: B:332:0x018a  */
    /* JADX WARN: Removed duplicated region for block: B:345:0x01d6  */
    /* JADX WARN: Removed duplicated region for block: B:349:0x0204 A[Catch: RemoteException -> 0x0233, TryCatch #1 {RemoteException -> 0x0233, blocks: (B:347:0x01d9, B:349:0x0204, B:351:0x020e, B:352:0x0215, B:354:0x021d), top: B:346:0x01d9 }] */
    /* JADX WARN: Removed duplicated region for block: B:359:0x0241  */
    /* JADX WARN: Removed duplicated region for block: B:362:0x0254  */
    /* JADX WARN: Removed duplicated region for block: B:364:0x0246  */
    /* JADX WARN: Removed duplicated region for block: B:370:0x01d8  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x03ef  */
    /* JADX WARN: Removed duplicated region for block: B:380:0x0184  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x0432  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x04ce  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x0550  */
    /* JADX WARN: Removed duplicated region for block: B:77:0x05ab  */
    /* JADX WARN: Removed duplicated region for block: B:82:0x05bc  */
    /* JADX WARN: Removed duplicated region for block: B:84:0x05c2  */
    /* JADX WARN: Removed duplicated region for block: B:88:0x05df  */
    /* JADX WARN: Removed duplicated region for block: B:92:0x05fa  */
    /* JADX WARN: Removed duplicated region for block: B:97:0x0619  */
    /* JADX WARN: Type inference failed for: r0v70, types: [com.samsung.android.app.smartcapture.screenshot.lib.RemoteScreenshotInterface$1] */
    @Override // com.android.systemui.screenshot.ScreenshotHandler
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void handleScreenshot(final com.android.systemui.screenshot.ScreenshotData r33, final java.util.function.Consumer r34, com.android.systemui.screenshot.TakeScreenshotService.RequestCallback r35) {
        /*
            Method dump skipped, instructions count: 2355
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.screenshot.ScreenshotController.handleScreenshot(com.android.systemui.screenshot.ScreenshotData, java.util.function.Consumer, com.android.systemui.screenshot.TakeScreenshotService$RequestCallback):void");
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
        View peekDecorView = screenshotWindow.window.peekDecorView();
        if (peekDecorView != null && peekDecorView.isAttachedToWindow()) {
            screenshotWindow.windowManager.removeViewImmediate(peekDecorView);
        }
        this.viewProxy.stopInputListening();
    }

    @Override // com.android.systemui.screenshot.InteractiveScreenshotHandler
    public final void requestDismissal(ScreenshotEvent screenshotEvent) {
        this.viewProxy.requestDismissal(screenshotEvent, null);
    }

    @Override // com.android.systemui.screenshot.InteractiveScreenshotHandler
    public final void setPartialScreenshotSelector(final Bundle bundle, final ScreenshotData screenshotData, final TakeScreenshotExecutorImpl$sam$java_util_function_Consumer$0 takeScreenshotExecutorImpl$sam$java_util_function_Consumer$0, final TakeScreenshotService.RequestCallback requestCallback) {
        final ScreenshotController screenshotController;
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
                    screenshotSelectorView2.setOnTouchListener(new View.OnTouchListener() { // from class: com.android.systemui.screenshot.ScreenshotController$setPartialScreenshotSelector$1
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
                                    public final void run() {
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
                    screenshotSelectorView3.setOnKeyListener(new View.OnKeyListener() { // from class: com.android.systemui.screenshot.ScreenshotController$setPartialScreenshotSelector$2
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
                (semScreenshotLayout4 != null ? semScreenshotLayout4 : null).post(new Runnable() { // from class: com.android.systemui.screenshot.ScreenshotController$setPartialScreenshotSelector$3
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
        final View view;
        synchronized (shutterEffectLock) {
            isSnackBarShowing = true;
            Unit unit = Unit.INSTANCE;
        }
        attachSemScreenshotLayoutToWindow();
        Presentation presentation = this.presentation;
        if (presentation != null) {
            Window window = presentation.getWindow();
            window.getClass();
            view = window.getDecorView();
        } else {
            view = this.semScreenshotLayout;
            if (view == null) {
                view = null;
            }
        }
        view.getClass();
        view.post(new Runnable() { // from class: com.android.systemui.screenshot.ScreenshotController$showScreenshotErrorMessage$2
            @Override // java.lang.Runnable
            public final void run() {
                Object obj = ScreenshotController.shutterEffectLock;
                ScreenshotController screenshotController = ScreenshotController.this;
                View view2 = view;
                SemScreenshotResult semScreenshotResult2 = semScreenshotResult;
                synchronized (obj) {
                    WindowContext windowContext = screenshotController.context;
                    ScreenCaptureHelper screenCaptureHelper = screenshotController.screenCaptureHelper;
                    screenCaptureHelper.getClass();
                    new SnackbarController(windowContext, screenCaptureHelper.capturedDisplayId, new ScreenshotController$showScreenshotErrorMessage$2$1$1(screenshotController)).showScreenshotError(view2, semScreenshotResult2);
                    Unit unit2 = Unit.INSTANCE;
                }
            }
        });
    }
}
