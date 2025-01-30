package com.android.server.display;

import android.R;
import android.content.Context;
import android.database.ContentObserver;
import android.graphics.SurfaceTexture;
import android.hardware.display.DisplayManager;
import android.os.Handler;
import android.os.IBinder;
import android.p005os.IInstalld;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Slog;
import android.view.Display;
import android.view.DisplayShape;
import android.view.Surface;
import android.view.SurfaceControl;
import com.android.internal.util.DumpUtils;
import com.android.internal.util.IndentingPrintWriter;
import com.android.internal.util.jobs.XmlUtils;
import com.android.server.display.DisplayAdapter;
import com.android.server.display.DisplayManagerService;
import com.android.server.display.OverlayDisplayWindow;
import com.android.server.display.mode.DisplayModeDirector;
import com.android.server.enterprise.vpn.knoxvpn.KnoxVpnFirewallHelper;
import com.samsung.android.rune.CoreRune;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* loaded from: classes2.dex */
public final class OverlayDisplayAdapter extends DisplayAdapter {
    public static final Pattern DISPLAY_PATTERN = Pattern.compile("([^,]+)(,[,_a-z]+)*");
    public static final Pattern MODE_PATTERN = Pattern.compile("(\\d+)x(\\d+)/(\\d+)");
    public String mCurrentOverlaySetting;
    public final ArrayList mOverlays;
    public final Handler mUiHandler;

    public static int chooseOverlayGravity(int i) {
        if (i == 1) {
            return 51;
        }
        if (i != 2) {
            return i != 3 ? 83 : 53;
        }
        return 85;
    }

    public OverlayDisplayAdapter(DisplayManagerService.SyncRoot syncRoot, Context context, Handler handler, DisplayAdapter.Listener listener, Handler handler2) {
        super(syncRoot, context, handler, listener, "OverlayDisplayAdapter");
        this.mOverlays = new ArrayList();
        this.mCurrentOverlaySetting = "";
        this.mUiHandler = handler2;
    }

    @Override // com.android.server.display.DisplayAdapter
    public void dumpLocked(PrintWriter printWriter) {
        super.dumpLocked(printWriter);
        printWriter.println("mCurrentOverlaySetting=" + this.mCurrentOverlaySetting);
        printWriter.println("mOverlays: size=" + this.mOverlays.size());
        Iterator it = this.mOverlays.iterator();
        while (it.hasNext()) {
            ((OverlayDisplayHandle) it.next()).dumpLocked(printWriter);
        }
    }

    @Override // com.android.server.display.DisplayAdapter
    public void registerLocked() {
        super.registerLocked();
        getHandler().post(new Runnable() { // from class: com.android.server.display.OverlayDisplayAdapter.1
            @Override // java.lang.Runnable
            public void run() {
                OverlayDisplayAdapter.this.getContext().getContentResolver().registerContentObserver(Settings.Global.getUriFor("overlay_display_devices"), true, new ContentObserver(OverlayDisplayAdapter.this.getHandler()) { // from class: com.android.server.display.OverlayDisplayAdapter.1.1
                    @Override // android.database.ContentObserver
                    public void onChange(boolean z) {
                        OverlayDisplayAdapter.this.updateOverlayDisplayDevices();
                    }
                });
                OverlayDisplayAdapter.this.updateOverlayDisplayDevices();
            }
        });
    }

    public final void updateOverlayDisplayDevices() {
        synchronized (getSyncRoot()) {
            updateOverlayDisplayDevicesLocked();
        }
    }

    public final void updateOverlayDisplayDevicesLocked() {
        int i;
        int i2;
        int i3;
        String[] strArr;
        String[] strArr2;
        int i4;
        int i5;
        int parseInt;
        String[] split;
        String string = Settings.Global.getString(getContext().getContentResolver(), "overlay_display_devices");
        if (string == null) {
            string = "";
        }
        int i6 = 2;
        int i7 = 0;
        int i8 = 1;
        if (CoreRune.MD_DEX_EMULATOR && (split = string.split("#")) != null && split.length == 3 && split[0].equals("dex")) {
            string = split[1];
            i = Integer.parseInt(split[2]);
        } else {
            i = -1;
        }
        String str = string;
        int i9 = i;
        if (str.equals(this.mCurrentOverlaySetting)) {
            return;
        }
        this.mCurrentOverlaySetting = str;
        if (!this.mOverlays.isEmpty()) {
            Slog.i("OverlayDisplayAdapter", "Dismissing all overlay display devices.");
            Iterator it = this.mOverlays.iterator();
            while (it.hasNext()) {
                ((OverlayDisplayHandle) it.next()).dismissLocked();
            }
            this.mOverlays.clear();
        }
        String[] split2 = str.split(KnoxVpnFirewallHelper.DELIMITER);
        int length = split2.length;
        int i10 = 0;
        int i11 = 0;
        while (i11 < length) {
            Matcher matcher = DISPLAY_PATTERN.matcher(split2[i11]);
            if (!matcher.matches()) {
                i2 = i11;
                i3 = length;
                strArr = split2;
            } else {
                if (i10 >= 4) {
                    Slog.w("OverlayDisplayAdapter", "Too many overlay display devices specified: " + str);
                    return;
                }
                String group = matcher.group(i8);
                String group2 = matcher.group(i6);
                ArrayList arrayList = new ArrayList();
                String[] split3 = group.split("\\|");
                int length2 = split3.length;
                while (i7 < length2) {
                    String str2 = split3[i7];
                    Matcher matcher2 = MODE_PATTERN.matcher(str2);
                    if (matcher2.matches()) {
                        strArr2 = split3;
                        try {
                            parseInt = Integer.parseInt(matcher2.group(i8), 10);
                            i4 = length2;
                        } catch (NumberFormatException unused) {
                            i4 = length2;
                        }
                        try {
                            int parseInt2 = Integer.parseInt(matcher2.group(2), 10);
                            int parseInt3 = Integer.parseInt(matcher2.group(3), 10);
                            i5 = i11;
                            if (parseInt >= 100 && parseInt <= 4096 && parseInt2 >= 100 && parseInt2 <= 4096 && parseInt3 >= 120 && parseInt3 <= 640) {
                                try {
                                    arrayList.add(new OverlayMode(parseInt, parseInt2, parseInt3));
                                } catch (NumberFormatException unused2) {
                                }
                            } else {
                                Slog.w("OverlayDisplayAdapter", "Ignoring out-of-range overlay display mode: " + str2);
                            }
                        } catch (NumberFormatException unused3) {
                            i5 = i11;
                            i7++;
                            length2 = i4;
                            split3 = strArr2;
                            i11 = i5;
                            i8 = 1;
                        }
                    } else {
                        strArr2 = split3;
                        i4 = length2;
                        i5 = i11;
                        str2.isEmpty();
                    }
                    i7++;
                    length2 = i4;
                    split3 = strArr2;
                    i11 = i5;
                    i8 = 1;
                }
                int i12 = i11;
                if (arrayList.isEmpty()) {
                    i3 = length;
                    strArr = split2;
                    i2 = i12;
                } else {
                    int i13 = i10 + 1;
                    String string2 = getContext().getResources().getString(R.string.granularity_label_link, Integer.valueOf(i13));
                    int chooseOverlayGravity = chooseOverlayGravity(i13);
                    OverlayFlags parseFlags = OverlayFlags.parseFlags(group2);
                    Slog.i("OverlayDisplayAdapter", "Showing overlay display device #" + i13 + ": name=" + string2 + ", modes=" + Arrays.toString(arrayList.toArray()) + ", flags=" + parseFlags);
                    if (CoreRune.MD_DEX_EMULATOR) {
                        i2 = i12;
                        i3 = length;
                        strArr = split2;
                        this.mOverlays.add(new OverlayDisplayHandle(string2, arrayList, chooseOverlayGravity, parseFlags, i13, i9));
                    } else {
                        i3 = length;
                        strArr = split2;
                        i2 = i12;
                        this.mOverlays.add(new OverlayDisplayHandle(this, string2, arrayList, chooseOverlayGravity, parseFlags, i13));
                    }
                    i10 = i13;
                    i11 = i2 + 1;
                    length = i3;
                    split2 = strArr;
                    i6 = 2;
                    i7 = 0;
                    i8 = 1;
                }
            }
            Slog.w("OverlayDisplayAdapter", "Malformed overlay display devices setting: " + str);
            i11 = i2 + 1;
            length = i3;
            split2 = strArr;
            i6 = 2;
            i7 = 0;
            i8 = 1;
        }
    }

    public abstract class OverlayDisplayDevice extends DisplayDevice {
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

        @Override // com.android.server.display.DisplayDevice
        public boolean hasStableUniqueId() {
            return false;
        }

        public abstract void onModeChangedLocked(int i);

        public OverlayDisplayDevice(IBinder iBinder, String str, List list, int i, int i2, float f, long j, OverlayFlags overlayFlags, int i3, SurfaceTexture surfaceTexture, int i4) {
            super(OverlayDisplayAdapter.this, iBinder, "overlay:" + i4, OverlayDisplayAdapter.this.getContext());
            this.mName = str;
            this.mRefreshRate = f;
            this.mDisplayPresentationDeadlineNanos = j;
            this.mFlags = overlayFlags;
            this.mState = i3;
            this.mSurfaceTexture = surfaceTexture;
            this.mRawModes = list;
            this.mModes = new Display.Mode[list.size()];
            for (int i5 = 0; i5 < list.size(); i5++) {
                OverlayMode overlayMode = (OverlayMode) list.get(i5);
                this.mModes[i5] = DisplayAdapter.createMode(overlayMode.mWidth, overlayMode.mHeight, f);
            }
            this.mActiveMode = i;
            this.mDefaultMode = i2;
        }

        public void destroyLocked() {
            this.mSurfaceTexture = null;
            Surface surface = this.mSurface;
            if (surface != null) {
                surface.release();
                this.mSurface = null;
            }
            DisplayControl.destroyDisplay(getDisplayTokenLocked());
        }

        @Override // com.android.server.display.DisplayDevice
        public void performTraversalLocked(SurfaceControl.Transaction transaction) {
            if (this.mSurfaceTexture != null) {
                if (this.mSurface == null) {
                    this.mSurface = new Surface(this.mSurfaceTexture);
                }
                setSurfaceLocked(transaction, this.mSurface);
            }
        }

        public void setStateLocked(int i) {
            this.mState = i;
            this.mInfo = null;
        }

        @Override // com.android.server.display.DisplayDevice
        public DisplayDeviceInfo getDisplayDeviceInfoLocked() {
            if (this.mInfo == null) {
                Display.Mode[] modeArr = this.mModes;
                int i = this.mActiveMode;
                Display.Mode mode = modeArr[i];
                OverlayMode overlayMode = (OverlayMode) this.mRawModes.get(i);
                DisplayDeviceInfo displayDeviceInfo = new DisplayDeviceInfo();
                this.mInfo = displayDeviceInfo;
                displayDeviceInfo.name = this.mName;
                displayDeviceInfo.uniqueId = getUniqueId();
                this.mInfo.width = mode.getPhysicalWidth();
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
                displayDeviceInfo2.presentationDeadlineNanos = this.mDisplayPresentationDeadlineNanos + (1000000000 / ((int) this.mRefreshRate));
                displayDeviceInfo2.flags = 64;
                OverlayFlags overlayFlags = this.mFlags;
                if (overlayFlags.mSecure) {
                    displayDeviceInfo2.flags = 64 | 4;
                }
                if (overlayFlags.mOwnContentOnly) {
                    displayDeviceInfo2.flags |= 128;
                }
                if (overlayFlags.mShouldShowSystemDecorations) {
                    displayDeviceInfo2.flags |= IInstalld.FLAG_USE_QUOTA;
                }
                displayDeviceInfo2.type = 4;
                displayDeviceInfo2.touch = 3;
                displayDeviceInfo2.state = this.mState;
                displayDeviceInfo2.flags |= IInstalld.FLAG_FORCE;
                displayDeviceInfo2.displayShape = DisplayShape.createDefaultDisplayShape(displayDeviceInfo2.width, displayDeviceInfo2.height, false);
            }
            return this.mInfo;
        }

        @Override // com.android.server.display.DisplayDevice
        public void setDesiredDisplayModeSpecsLocked(DisplayModeDirector.DesiredDisplayModeSpecs desiredDisplayModeSpecs) {
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
                Slog.w("OverlayDisplayAdapter", "Unable to locate mode " + i + ", reverting to default.");
                i2 = this.mDefaultMode;
            }
            if (this.mActiveMode == i2) {
                return;
            }
            this.mActiveMode = i2;
            this.mInfo = null;
            OverlayDisplayAdapter.this.sendDisplayDeviceEventLocked(this, 2);
            onModeChangedLocked(i2);
        }
    }

    public final class OverlayDisplayHandle implements OverlayDisplayWindow.Listener {
        public int mActiveMode;
        public OverlayDisplayDevice mDevice;
        public final Runnable mDismissRunnable;
        public int mDisplayId;
        public final OverlayFlags mFlags;
        public final int mGravity;
        public final List mModes;
        public final String mName;
        public final int mNumber;
        public final Runnable mResizeRunnable;
        public final Runnable mShowRunnable;
        public OverlayDisplayWindow mWindow;

        public OverlayDisplayHandle(OverlayDisplayAdapter overlayDisplayAdapter, String str, List list, int i, OverlayFlags overlayFlags, int i2) {
            this(str, list, i, overlayFlags, i2, -1);
        }

        public OverlayDisplayHandle(String str, List list, int i, OverlayFlags overlayFlags, int i2, int i3) {
            this.mDisplayId = -1;
            this.mShowRunnable = new Runnable() { // from class: com.android.server.display.OverlayDisplayAdapter.OverlayDisplayHandle.2
                @Override // java.lang.Runnable
                public void run() {
                    OverlayMode overlayMode = (OverlayMode) OverlayDisplayHandle.this.mModes.get(OverlayDisplayHandle.this.mActiveMode);
                    OverlayDisplayWindow overlayDisplayWindow = new OverlayDisplayWindow(CoreRune.MD_DEX_EMULATOR ? OverlayDisplayHandle.this.getDisplayContext() : OverlayDisplayAdapter.this.getContext(), OverlayDisplayHandle.this.mName, overlayMode.mWidth, overlayMode.mHeight, overlayMode.mDensityDpi, OverlayDisplayHandle.this.mGravity, OverlayDisplayHandle.this.mFlags.mSecure, OverlayDisplayHandle.this);
                    overlayDisplayWindow.show();
                    synchronized (OverlayDisplayAdapter.this.getSyncRoot()) {
                        OverlayDisplayHandle.this.mWindow = overlayDisplayWindow;
                    }
                }
            };
            this.mDismissRunnable = new Runnable() { // from class: com.android.server.display.OverlayDisplayAdapter.OverlayDisplayHandle.3
                @Override // java.lang.Runnable
                public void run() {
                    OverlayDisplayWindow overlayDisplayWindow;
                    synchronized (OverlayDisplayAdapter.this.getSyncRoot()) {
                        overlayDisplayWindow = OverlayDisplayHandle.this.mWindow;
                        OverlayDisplayHandle.this.mWindow = null;
                    }
                    if (overlayDisplayWindow != null) {
                        overlayDisplayWindow.dismiss();
                    }
                }
            };
            this.mResizeRunnable = new Runnable() { // from class: com.android.server.display.OverlayDisplayAdapter.OverlayDisplayHandle.4
                @Override // java.lang.Runnable
                public void run() {
                    synchronized (OverlayDisplayAdapter.this.getSyncRoot()) {
                        if (OverlayDisplayHandle.this.mWindow == null) {
                            return;
                        }
                        OverlayMode overlayMode = (OverlayMode) OverlayDisplayHandle.this.mModes.get(OverlayDisplayHandle.this.mActiveMode);
                        OverlayDisplayHandle.this.mWindow.resize(overlayMode.mWidth, overlayMode.mHeight, overlayMode.mDensityDpi);
                    }
                }
            };
            this.mName = str;
            this.mModes = list;
            this.mGravity = i;
            this.mFlags = overlayFlags;
            this.mNumber = i2;
            this.mActiveMode = 0;
            if (CoreRune.MD_DEX_EMULATOR) {
                this.mDisplayId = i3;
            }
            showLocked();
        }

        public final void showLocked() {
            OverlayDisplayAdapter.this.mUiHandler.post(this.mShowRunnable);
        }

        public void dismissLocked() {
            OverlayDisplayAdapter.this.mUiHandler.removeCallbacks(this.mShowRunnable);
            OverlayDisplayAdapter.this.mUiHandler.post(this.mDismissRunnable);
        }

        public final void onActiveModeChangedLocked(int i) {
            OverlayDisplayAdapter.this.mUiHandler.removeCallbacks(this.mResizeRunnable);
            this.mActiveMode = i;
            if (this.mWindow != null) {
                OverlayDisplayAdapter.this.mUiHandler.post(this.mResizeRunnable);
            }
        }

        @Override // com.android.server.display.OverlayDisplayWindow.Listener
        public void onWindowCreated(SurfaceTexture surfaceTexture, float f, long j, int i) {
            synchronized (OverlayDisplayAdapter.this.getSyncRoot()) {
                OverlayDisplayDevice overlayDisplayDevice = new OverlayDisplayDevice(DisplayControl.createDisplay(this.mName, this.mFlags.mSecure), this.mName, this.mModes, this.mActiveMode, 0, f, j, this.mFlags, i, surfaceTexture, this.mNumber) { // from class: com.android.server.display.OverlayDisplayAdapter.OverlayDisplayHandle.1
                    {
                        OverlayDisplayAdapter overlayDisplayAdapter = OverlayDisplayAdapter.this;
                    }

                    @Override // com.android.server.display.OverlayDisplayAdapter.OverlayDisplayDevice
                    public void onModeChangedLocked(int i2) {
                        OverlayDisplayHandle.this.onActiveModeChangedLocked(i2);
                    }
                };
                this.mDevice = overlayDisplayDevice;
                OverlayDisplayAdapter.this.sendDisplayDeviceEventLocked(overlayDisplayDevice, 1);
            }
        }

        @Override // com.android.server.display.OverlayDisplayWindow.Listener
        public void onWindowDestroyed() {
            synchronized (OverlayDisplayAdapter.this.getSyncRoot()) {
                OverlayDisplayDevice overlayDisplayDevice = this.mDevice;
                if (overlayDisplayDevice != null) {
                    overlayDisplayDevice.destroyLocked();
                    OverlayDisplayAdapter.this.sendDisplayDeviceEventLocked(this.mDevice, 3);
                }
            }
        }

        @Override // com.android.server.display.OverlayDisplayWindow.Listener
        public void onStateChanged(int i) {
            synchronized (OverlayDisplayAdapter.this.getSyncRoot()) {
                OverlayDisplayDevice overlayDisplayDevice = this.mDevice;
                if (overlayDisplayDevice != null) {
                    overlayDisplayDevice.setStateLocked(i);
                    OverlayDisplayAdapter.this.sendDisplayDeviceEventLocked(this.mDevice, 2);
                }
            }
        }

        public void dumpLocked(PrintWriter printWriter) {
            printWriter.println("  " + this.mName + XmlUtils.STRING_ARRAY_SEPARATOR);
            StringBuilder sb = new StringBuilder();
            sb.append("    mModes=");
            sb.append(Arrays.toString(this.mModes.toArray()));
            printWriter.println(sb.toString());
            printWriter.println("    mActiveMode=" + this.mActiveMode);
            printWriter.println("    mGravity=" + this.mGravity);
            printWriter.println("    mFlags=" + this.mFlags);
            printWriter.println("    mNumber=" + this.mNumber);
            if (this.mWindow != null) {
                IndentingPrintWriter indentingPrintWriter = new IndentingPrintWriter(printWriter, "    ");
                indentingPrintWriter.increaseIndent();
                DumpUtils.dumpAsync(OverlayDisplayAdapter.this.mUiHandler, this.mWindow, indentingPrintWriter, "", 200L);
            }
        }

        public final Context getDisplayContext() {
            Display display;
            Context context = OverlayDisplayAdapter.this.getContext();
            int i = this.mDisplayId;
            return (i == -1 || i == 0 || (display = ((DisplayManager) context.getSystemService("display")).getDisplay(this.mDisplayId)) == null) ? context : context.createDisplayContext(display);
        }
    }

    public final class OverlayMode {
        public final int mDensityDpi;
        public final int mHeight;
        public final int mWidth;

        public OverlayMode(int i, int i2, int i3) {
            this.mWidth = i;
            this.mHeight = i2;
            this.mDensityDpi = i3;
        }

        public String toString() {
            return "{width=" + this.mWidth + ", height=" + this.mHeight + ", densityDpi=" + this.mDensityDpi + "}";
        }
    }

    public final class OverlayFlags {
        public final boolean mOwnContentOnly;
        public final boolean mSecure;
        public final boolean mShouldShowSystemDecorations;

        public OverlayFlags(boolean z, boolean z2, boolean z3) {
            this.mSecure = z;
            this.mOwnContentOnly = z2;
            this.mShouldShowSystemDecorations = z3;
        }

        public static OverlayFlags parseFlags(String str) {
            if (TextUtils.isEmpty(str)) {
                return new OverlayFlags(false, false, false);
            }
            boolean z = false;
            boolean z2 = false;
            boolean z3 = false;
            for (String str2 : str.split(",")) {
                if ("secure".equals(str2)) {
                    z = true;
                }
                if ("own_content_only".equals(str2)) {
                    z2 = true;
                }
                if ("should_show_system_decorations".equals(str2)) {
                    z3 = true;
                }
            }
            return new OverlayFlags(z, z2, z3);
        }

        public String toString() {
            return "{secure=" + this.mSecure + ", ownContentOnly=" + this.mOwnContentOnly + ", shouldShowSystemDecorations=" + this.mShouldShowSystemDecorations + "}";
        }
    }
}
