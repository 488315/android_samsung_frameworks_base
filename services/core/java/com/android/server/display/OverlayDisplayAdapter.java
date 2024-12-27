package com.android.server.display;

import android.R;
import android.content.Context;
import android.database.ContentObserver;
import android.graphics.SurfaceTexture;
import android.hardware.biometrics.face.V1_0.OptionalBool$$ExternalSyntheticOutline0;
import android.hardware.broadcastradio.V2_0.AmFmBandRange$$ExternalSyntheticOutline0;
import android.os.Handler;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Slog;
import android.view.Display;
import android.view.DisplayShape;
import android.view.Surface;
import android.view.SurfaceControl;

import com.android.internal.util.DumpUtils;
import com.android.internal.util.IndentingPrintWriter;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.DirEncryptService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.BrailleDisplayConnection$$ExternalSyntheticOutline0;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;
import com.android.server.display.feature.DisplayManagerFlags;
import com.android.server.display.mode.DisplayModeDirector;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class OverlayDisplayAdapter extends DisplayAdapter {
    public static final Pattern DISPLAY_PATTERN = Pattern.compile("([^,]+)(,[,_a-z]+)*");
    public static final Pattern MODE_PATTERN = Pattern.compile("(\\d+)x(\\d+)/(\\d+)");
    public String mCurrentOverlaySetting;
    public final ArrayList mOverlays;
    public final Handler mUiHandler;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.display.OverlayDisplayAdapter$1, reason: invalid class name */
    public final class AnonymousClass1 implements Runnable {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ Object this$0;

        public /* synthetic */ AnonymousClass1(int i, Object obj) {
            this.$r8$classId = i;
            this.this$0 = obj;
        }

        @Override // java.lang.Runnable
        public final void run() {
            OverlayDisplayWindow overlayDisplayWindow;
            switch (this.$r8$classId) {
                case 0:
                    ((OverlayDisplayAdapter) this.this$0)
                            .mContext
                            .getContentResolver()
                            .registerContentObserver(
                                    Settings.Global.getUriFor("overlay_display_devices"),
                                    true,
                                    new ContentObserver(
                                            ((OverlayDisplayAdapter) this.this$0)
                                                    .mHandler) { // from class:
                                                                 // com.android.server.display.OverlayDisplayAdapter.1.1
                                        @Override // android.database.ContentObserver
                                        public final void onChange(boolean z) {
                                            OverlayDisplayAdapter overlayDisplayAdapter =
                                                    (OverlayDisplayAdapter)
                                                            AnonymousClass1.this.this$0;
                                            Pattern pattern = OverlayDisplayAdapter.DISPLAY_PATTERN;
                                            synchronized (overlayDisplayAdapter.mSyncRoot) {
                                                overlayDisplayAdapter
                                                        .updateOverlayDisplayDevicesLocked();
                                            }
                                        }
                                    });
                    OverlayDisplayAdapter overlayDisplayAdapter =
                            (OverlayDisplayAdapter) this.this$0;
                    synchronized (overlayDisplayAdapter.mSyncRoot) {
                        overlayDisplayAdapter.updateOverlayDisplayDevicesLocked();
                    }
                    return;
                case 1:
                    OverlayDisplayHandle overlayDisplayHandle = (OverlayDisplayHandle) this.this$0;
                    OverlayMode overlayMode =
                            (OverlayMode)
                                    overlayDisplayHandle.mModes.get(
                                            overlayDisplayHandle.mActiveMode);
                    OverlayDisplayHandle overlayDisplayHandle2 = (OverlayDisplayHandle) this.this$0;
                    OverlayDisplayWindow overlayDisplayWindow2 =
                            new OverlayDisplayWindow(
                                    OverlayDisplayAdapter.this.mContext,
                                    overlayDisplayHandle2.mName,
                                    overlayMode.mWidth,
                                    overlayMode.mHeight,
                                    overlayMode.mDensityDpi,
                                    overlayDisplayHandle2.mGravity,
                                    overlayDisplayHandle2.mFlags.mSecure,
                                    overlayDisplayHandle2);
                    if (!overlayDisplayWindow2.mWindowVisible) {
                        overlayDisplayWindow2.mDisplayManager.registerDisplayListener(
                                overlayDisplayWindow2.mDisplayListener, null);
                        if (overlayDisplayWindow2.updateDefaultDisplayInfo()) {
                            overlayDisplayWindow2.mLiveTranslationX =
                                    FullScreenMagnificationGestureHandler.MAX_SCALE;
                            overlayDisplayWindow2.mLiveTranslationY =
                                    FullScreenMagnificationGestureHandler.MAX_SCALE;
                            overlayDisplayWindow2.mLiveScale = 1.0f;
                            overlayDisplayWindow2.updateWindowParams();
                            overlayDisplayWindow2.mWindowManager.addView(
                                    overlayDisplayWindow2.mWindowContent,
                                    overlayDisplayWindow2.mWindowParams);
                            overlayDisplayWindow2.mWindowVisible = true;
                        } else {
                            overlayDisplayWindow2.mDisplayManager.unregisterDisplayListener(
                                    overlayDisplayWindow2.mDisplayListener);
                        }
                    }
                    synchronized (OverlayDisplayAdapter.this.mSyncRoot) {
                        ((OverlayDisplayHandle) this.this$0).mWindow = overlayDisplayWindow2;
                    }
                    return;
                case 2:
                    synchronized (OverlayDisplayAdapter.this.mSyncRoot) {
                        OverlayDisplayHandle overlayDisplayHandle3 =
                                (OverlayDisplayHandle) this.this$0;
                        overlayDisplayWindow = overlayDisplayHandle3.mWindow;
                        overlayDisplayHandle3.mWindow = null;
                    }
                    if (overlayDisplayWindow != null) {
                        overlayDisplayWindow.dismiss();
                        return;
                    }
                    return;
                default:
                    synchronized (OverlayDisplayAdapter.this.mSyncRoot) {
                        try {
                            OverlayDisplayHandle overlayDisplayHandle4 =
                                    (OverlayDisplayHandle) this.this$0;
                            if (overlayDisplayHandle4.mWindow == null) {
                                return;
                            }
                            OverlayMode overlayMode2 =
                                    (OverlayMode)
                                            overlayDisplayHandle4.mModes.get(
                                                    overlayDisplayHandle4.mActiveMode);
                            ((OverlayDisplayHandle) this.this$0)
                                    .mWindow.resize(
                                            overlayMode2.mWidth,
                                            overlayMode2.mHeight,
                                            overlayMode2.mDensityDpi,
                                            true);
                            return;
                        } finally {
                        }
                    }
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class OverlayDisplayHandle {
        public int mActiveMode;
        public AnonymousClass1 mDevice;
        public final AnonymousClass1 mDismissRunnable;
        public final OverlayFlags mFlags;
        public final int mGravity;
        public final List mModes;
        public final String mName;
        public final int mNumber;
        public final AnonymousClass1 mResizeRunnable;
        public final AnonymousClass1 mShowRunnable;
        public OverlayDisplayWindow mWindow;

        /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
        /* renamed from: com.android.server.display.OverlayDisplayAdapter$OverlayDisplayHandle$1, reason: invalid class name */
        public final class AnonymousClass1 extends DisplayDevice {
            public int mActiveMode;
            public final int mDefaultMode;
            public final long mDisplayPresentationDeadlineNanos;
            public final OverlayFlags mFlags;
            public DisplayDeviceInfo mInfo;
            public final Display.Mode[] mModes;
            public final String mName;
            public final List mRawModes;
            public final float mRefreshRate;
            public int mState;
            public Surface mSurface;
            public SurfaceTexture mSurfaceTexture;
            public final /* synthetic */ OverlayDisplayAdapter this$0;

            /* JADX WARN: Illegal instructions before constructor call */
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public AnonymousClass1(
                    android.os.IBinder r19,
                    java.lang.String r20,
                    java.util.List r21,
                    int r22,
                    float r23,
                    long r24,
                    com.android.server.display.OverlayDisplayAdapter.OverlayFlags r26,
                    int r27,
                    android.graphics.SurfaceTexture r28,
                    int r29) {
                /*
                    r17 = this;
                    r6 = r17
                    r0 = r18
                    r7 = r21
                    com.android.server.display.OverlayDisplayAdapter.OverlayDisplayHandle.this = r0
                    com.android.server.display.OverlayDisplayAdapter r1 = com.android.server.display.OverlayDisplayAdapter.this
                    r6.this$0 = r1
                    java.lang.String r0 = "overlay:"
                    r2 = r29
                    java.lang.String r3 = android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0.m(r2, r0)
                    android.content.Context r4 = r1.mContext
                    r5 = 0
                    r0 = r17
                    r2 = r19
                    r0.<init>(r1, r2, r3, r4, r5)
                    r0 = r20
                    r6.mName = r0
                    r0 = r23
                    r6.mRefreshRate = r0
                    r1 = r24
                    r6.mDisplayPresentationDeadlineNanos = r1
                    r1 = r26
                    r6.mFlags = r1
                    r1 = r27
                    r6.mState = r1
                    r1 = r28
                    r6.mSurfaceTexture = r1
                    r6.mRawModes = r7
                    r1 = r7
                    java.util.ArrayList r1 = (java.util.ArrayList) r1
                    int r2 = r1.size()
                    android.view.Display$Mode[] r2 = new android.view.Display.Mode[r2]
                    r6.mModes = r2
                    r2 = 0
                    r3 = r2
                L46:
                    int r4 = r1.size()
                    if (r3 >= r4) goto L73
                    java.lang.Object r4 = r1.get(r3)
                    com.android.server.display.OverlayDisplayAdapter$OverlayMode r4 = (com.android.server.display.OverlayDisplayAdapter.OverlayMode) r4
                    android.view.Display$Mode[] r5 = r6.mModes
                    int r9 = r4.mWidth
                    float[] r14 = new float[r2]
                    int[] r15 = new int[r2]
                    android.view.Display$Mode r16 = new android.view.Display$Mode
                    java.util.concurrent.atomic.AtomicInteger r7 = com.android.server.display.DisplayAdapter.NEXT_DISPLAY_MODE_ID
                    int r8 = r7.getAndIncrement()
                    r13 = 0
                    int r10 = r4.mHeight
                    r7 = r16
                    r11 = r23
                    r12 = r23
                    r7.<init>(r8, r9, r10, r11, r12, r13, r14, r15)
                    r5[r3] = r16
                    int r3 = r3 + 1
                    goto L46
                L73:
                    r3 = r22
                    r6.mActiveMode = r3
                    r6.mDefaultMode = r2
                    return
                */
                throw new UnsupportedOperationException(
                        "Method not decompiled:"
                            + " com.android.server.display.OverlayDisplayAdapter.OverlayDisplayHandle.AnonymousClass1.<init>(com.android.server.display.OverlayDisplayAdapter$OverlayDisplayHandle,"
                            + " android.os.IBinder, java.lang.String, java.util.List, int, float,"
                            + " long,"
                            + " com.android.server.display.OverlayDisplayAdapter$OverlayFlags, int,"
                            + " android.graphics.SurfaceTexture, int):void");
            }

            @Override // com.android.server.display.DisplayDevice
            public final DisplayDeviceInfo getDisplayDeviceInfoLocked() {
                if (this.mInfo == null) {
                    Display.Mode[] modeArr = this.mModes;
                    int i = this.mActiveMode;
                    Display.Mode mode = modeArr[i];
                    OverlayMode overlayMode = (OverlayMode) this.mRawModes.get(i);
                    DisplayDeviceInfo displayDeviceInfo = new DisplayDeviceInfo();
                    this.mInfo = displayDeviceInfo;
                    displayDeviceInfo.name = this.mName;
                    displayDeviceInfo.uniqueId = this.mUniqueId;
                    displayDeviceInfo.width = mode.getPhysicalWidth();
                    this.mInfo.height = mode.getPhysicalHeight();
                    this.mInfo.modeId = mode.getModeId();
                    this.mInfo.renderFrameRate = mode.getRefreshRate();
                    this.mInfo.defaultModeId = this.mModes[0].getModeId();
                    DisplayDeviceInfo displayDeviceInfo2 = this.mInfo;
                    displayDeviceInfo2.supportedModes = this.mModes;
                    int i2 = overlayMode.mDensityDpi;
                    displayDeviceInfo2.densityDpi = i2;
                    displayDeviceInfo2.xDpi = i2;
                    displayDeviceInfo2.yDpi = i2;
                    displayDeviceInfo2.presentationDeadlineNanos =
                            (1000000000 / ((int) this.mRefreshRate))
                                    + this.mDisplayPresentationDeadlineNanos;
                    displayDeviceInfo2.flags = 64;
                    OverlayFlags overlayFlags = this.mFlags;
                    if (overlayFlags.mSecure) {
                        displayDeviceInfo2.flags = 64 | 4;
                    }
                    if (overlayFlags.mOwnContentOnly) {
                        displayDeviceInfo2.flags |= 128;
                    }
                    if (overlayFlags.mShouldShowSystemDecorations) {
                        displayDeviceInfo2.flags |= 4096;
                    }
                    displayDeviceInfo2.type = 4;
                    displayDeviceInfo2.touch = 3;
                    displayDeviceInfo2.state = this.mState;
                    displayDeviceInfo2.flags |= 8192;
                    displayDeviceInfo2.displayShape =
                            DisplayShape.createDefaultDisplayShape(
                                    displayDeviceInfo2.width, displayDeviceInfo2.height, false);
                }
                return this.mInfo;
            }

            @Override // com.android.server.display.DisplayDevice
            public final boolean hasStableUniqueId() {
                return false;
            }

            @Override // com.android.server.display.DisplayDevice
            public final void performTraversalLocked(SurfaceControl.Transaction transaction) {
                if (this.mSurfaceTexture != null) {
                    if (this.mSurface == null) {
                        this.mSurface = new Surface(this.mSurfaceTexture);
                    }
                    Surface surface = this.mSurface;
                    if (this.mCurrentSurface != surface) {
                        this.mCurrentSurface = surface;
                        transaction.setDisplaySurface(this.mDisplayToken, surface);
                    }
                }
            }

            @Override // com.android.server.display.DisplayDevice
            public final void setDesiredDisplayModeSpecsLocked(
                    DisplayModeDirector.DesiredDisplayModeSpecs desiredDisplayModeSpecs) {
                int i = desiredDisplayModeSpecs.baseModeId;
                int i2 = 0;
                if (i != 0) {
                    while (true) {
                        Display.Mode[] modeArr = this.mModes;
                        if (i2 >= modeArr.length) {
                            i2 = -1;
                            break;
                        } else if (modeArr[i2].getModeId() == i) {
                            break;
                        } else {
                            i2++;
                        }
                    }
                }
                if (i2 == -1) {
                    BrailleDisplayConnection$$ExternalSyntheticOutline0.m(
                            i,
                            "Unable to locate mode ",
                            ", reverting to default.",
                            "OverlayDisplayAdapter");
                    i2 = this.mDefaultMode;
                }
                if (this.mActiveMode == i2) {
                    return;
                }
                this.mActiveMode = i2;
                this.mInfo = null;
                this.this$0.sendDisplayDeviceEventLocked(this, 2);
                OverlayDisplayHandle overlayDisplayHandle = OverlayDisplayHandle.this;
                OverlayDisplayAdapter overlayDisplayAdapter = OverlayDisplayAdapter.this;
                Handler handler = overlayDisplayAdapter.mUiHandler;
                AnonymousClass1 anonymousClass1 = overlayDisplayHandle.mResizeRunnable;
                handler.removeCallbacks(anonymousClass1);
                overlayDisplayHandle.mActiveMode = i2;
                if (overlayDisplayHandle.mWindow != null) {
                    overlayDisplayAdapter.mUiHandler.post(anonymousClass1);
                }
            }
        }

        public OverlayDisplayHandle(
                String str, List list, int i, OverlayFlags overlayFlags, int i2) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(1, this);
            this.mShowRunnable = anonymousClass1;
            this.mDismissRunnable = new AnonymousClass1(2, this);
            this.mResizeRunnable = new AnonymousClass1(3, this);
            this.mName = str;
            this.mModes = list;
            this.mGravity = i;
            this.mFlags = overlayFlags;
            this.mNumber = i2;
            this.mActiveMode = 0;
            OverlayDisplayAdapter.this.mUiHandler.post(anonymousClass1);
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class OverlayFlags {
        public final boolean mOwnContentOnly;
        public final boolean mSecure;
        public final boolean mShouldShowSystemDecorations;

        public OverlayFlags(boolean z, boolean z2, boolean z3) {
            this.mSecure = z;
            this.mOwnContentOnly = z2;
            this.mShouldShowSystemDecorations = z3;
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("{secure=");
            sb.append(this.mSecure);
            sb.append(", ownContentOnly=");
            sb.append(this.mOwnContentOnly);
            sb.append(", shouldShowSystemDecorations=");
            return OptionalBool$$ExternalSyntheticOutline0.m(
                    "}", sb, this.mShouldShowSystemDecorations);
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class OverlayMode {
        public final int mDensityDpi;
        public final int mHeight;
        public final int mWidth;

        public OverlayMode(int i, int i2, int i3) {
            this.mWidth = i;
            this.mHeight = i2;
            this.mDensityDpi = i3;
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("{width=");
            sb.append(this.mWidth);
            sb.append(", height=");
            sb.append(this.mHeight);
            sb.append(", densityDpi=");
            return AmFmBandRange$$ExternalSyntheticOutline0.m(this.mDensityDpi, sb, "}");
        }
    }

    public OverlayDisplayAdapter(
            DisplayManagerService.SyncRoot syncRoot,
            Context context,
            Handler handler,
            DisplayAdapter.Listener listener,
            Handler handler2,
            DisplayManagerFlags displayManagerFlags) {
        super(syncRoot, context, handler, listener, "OverlayDisplayAdapter", displayManagerFlags);
        this.mOverlays = new ArrayList();
        this.mCurrentOverlaySetting = "";
        this.mUiHandler = handler2;
    }

    @Override // com.android.server.display.DisplayAdapter
    public final void dumpLocked(PrintWriter printWriter) {
        StringBuilder m =
                BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                        printWriter,
                        this.mCurrentOverlaySetting,
                        "mOverlays: size=",
                        new StringBuilder("mCurrentOverlaySetting="));
        m.append(this.mOverlays.size());
        printWriter.println(m.toString());
        Iterator it = this.mOverlays.iterator();
        while (it.hasNext()) {
            OverlayDisplayHandle overlayDisplayHandle = (OverlayDisplayHandle) it.next();
            printWriter.println("  " + overlayDisplayHandle.mName + ":");
            StringBuilder sb = new StringBuilder("    mModes=");
            sb.append(Arrays.toString(overlayDisplayHandle.mModes.toArray()));
            printWriter.println(sb.toString());
            StringBuilder m2 =
                    BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                            BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                    new StringBuilder("    mActiveMode="),
                                    overlayDisplayHandle.mActiveMode,
                                    printWriter,
                                    "    mGravity="),
                            overlayDisplayHandle.mGravity,
                            printWriter,
                            "    mFlags=");
            m2.append(overlayDisplayHandle.mFlags);
            printWriter.println(m2.toString());
            AccessibilityManagerService$$ExternalSyntheticOutline0.m(
                    new StringBuilder("    mNumber="), overlayDisplayHandle.mNumber, printWriter);
            if (overlayDisplayHandle.mWindow != null) {
                IndentingPrintWriter indentingPrintWriter =
                        new IndentingPrintWriter(printWriter, "    ");
                indentingPrintWriter.increaseIndent();
                DumpUtils.dumpAsync(
                        OverlayDisplayAdapter.this.mUiHandler,
                        overlayDisplayHandle.mWindow,
                        indentingPrintWriter,
                        "",
                        200L);
            }
        }
    }

    public final void updateOverlayDisplayDevicesLocked() {
        OverlayFlags overlayFlags;
        String[] strArr;
        int i;
        String string =
                Settings.Global.getString(
                        this.mContext.getContentResolver(), "overlay_display_devices");
        if (string == null) {
            string = "";
        }
        String str = string;
        if (str.equals(this.mCurrentOverlaySetting)) {
            return;
        }
        this.mCurrentOverlaySetting = str;
        if (!this.mOverlays.isEmpty()) {
            Slog.i("OverlayDisplayAdapter", "Dismissing all overlay display devices.");
            Iterator it = this.mOverlays.iterator();
            while (it.hasNext()) {
                OverlayDisplayHandle overlayDisplayHandle = (OverlayDisplayHandle) it.next();
                OverlayDisplayAdapter overlayDisplayAdapter = OverlayDisplayAdapter.this;
                overlayDisplayAdapter.mUiHandler.removeCallbacks(
                        overlayDisplayHandle.mShowRunnable);
                overlayDisplayAdapter.mUiHandler.post(overlayDisplayHandle.mDismissRunnable);
            }
            this.mOverlays.clear();
        }
        int i2 = 0;
        for (String str2 : str.split(";")) {
            Matcher matcher = DISPLAY_PATTERN.matcher(str2);
            if (matcher.matches()) {
                if (i2 >= 4) {
                    Slog.w(
                            "OverlayDisplayAdapter",
                            "Too many overlay display devices specified: ".concat(str));
                    return;
                }
                int i3 = 1;
                String group = matcher.group(1);
                String group2 = matcher.group(2);
                ArrayList arrayList = new ArrayList();
                String[] split = group.split("\\|");
                int length = split.length;
                int i4 = 0;
                while (i4 < length) {
                    String str3 = split[i4];
                    Matcher matcher2 = MODE_PATTERN.matcher(str3);
                    if (matcher2.matches()) {
                        try {
                            int parseInt = Integer.parseInt(matcher2.group(i3), 10);
                            strArr = split;
                            try {
                                int parseInt2 = Integer.parseInt(matcher2.group(2), 10);
                                int parseInt3 = Integer.parseInt(matcher2.group(3), 10);
                                i = length;
                                if (parseInt < 100
                                        || parseInt > 4096
                                        || parseInt2 < 100
                                        || parseInt2 > 4096
                                        || parseInt3 < 120
                                        || parseInt3 > 640) {
                                    Slog.w(
                                            "OverlayDisplayAdapter",
                                            "Ignoring out-of-range overlay display mode: " + str3);
                                } else {
                                    try {
                                        arrayList.add(
                                                new OverlayMode(parseInt, parseInt2, parseInt3));
                                    } catch (NumberFormatException unused) {
                                    }
                                }
                            } catch (NumberFormatException unused2) {
                                i = length;
                                i4++;
                                length = i;
                                split = strArr;
                                i3 = 1;
                            }
                        } catch (NumberFormatException unused3) {
                            strArr = split;
                        }
                    } else {
                        strArr = split;
                        i = length;
                        str3.getClass();
                    }
                    i4++;
                    length = i;
                    split = strArr;
                    i3 = 1;
                }
                if (!arrayList.isEmpty()) {
                    int i5 = i2 + 1;
                    String string2 =
                            this.mContext
                                    .getResources()
                                    .getString(R.string.imTypeOther, Integer.valueOf(i5));
                    int i6 = i5 != 1 ? i5 != 2 ? i5 != 3 ? 83 : 53 : 85 : 51;
                    if (TextUtils.isEmpty(group2)) {
                        overlayFlags = new OverlayFlags(false, false, false);
                    } else {
                        boolean z = false;
                        String[] split2 = group2.split(",");
                        int length2 = split2.length;
                        boolean z2 = false;
                        int i7 = 0;
                        boolean z3 = false;
                        while (i7 < length2) {
                            int i8 = length2;
                            String str4 = split2[i7];
                            String[] strArr2 = split2;
                            if ("secure".equals(str4)) {
                                z3 = true;
                            }
                            if ("own_content_only".equals(str4)) {
                                z2 = true;
                            }
                            if ("should_show_system_decorations".equals(str4)) {
                                z = true;
                            }
                            i7++;
                            length2 = i8;
                            split2 = strArr2;
                        }
                        overlayFlags = new OverlayFlags(z3, z2, z);
                    }
                    StringBuilder m =
                            DirEncryptService$$ExternalSyntheticOutline0.m(
                                    i5,
                                    "Showing overlay display device #",
                                    ": name=",
                                    string2,
                                    ", modes=");
                    m.append(Arrays.toString(arrayList.toArray()));
                    m.append(", flags=");
                    m.append(overlayFlags);
                    Slog.i("OverlayDisplayAdapter", m.toString());
                    this.mOverlays.add(
                            new OverlayDisplayHandle(string2, arrayList, i6, overlayFlags, i5));
                    i2 = i5;
                }
            }
            Slog.w(
                    "OverlayDisplayAdapter",
                    "Malformed overlay display devices setting: ".concat(str));
        }
    }
}
