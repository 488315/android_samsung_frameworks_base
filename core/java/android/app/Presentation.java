package android.app;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.hardware.display.DisplayManager;
import android.os.Handler;
import android.os.Looper;
import android.provider.ContactsContract;
import android.util.Slog;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.Display;
import android.view.DisplayInfo;
import android.view.Window;
import android.view.WindowManager;

import com.samsung.android.rune.CoreRune;

import java.util.Objects;

/* loaded from: classes.dex */
public class Presentation extends Dialog {
    private static final String TAG = "Presentation";
    private boolean isRearDisplayPresentation;
    private final Display mDisplay;
    private final DisplayManager.DisplayListener mDisplayListener;
    private final DisplayManager mDisplayManager;
    private final Handler mHandler;
    private boolean mIsStarted;
    private final String mOwnerPackageName;

    public Presentation(Context outerContext, Display display) {
        this(outerContext, display, 0);
    }

    public Presentation(Context outerContext, Display display, int theme) {
        this(outerContext, display, theme, -1);
    }

    public Presentation(Context outerContext, Display display, int theme, int type) {
        super(createPresentationContext(outerContext, display, theme, type), theme, false);
        this.mHandler =
                new Handler(
                        (Looper)
                                Objects.requireNonNull(
                                        Looper.myLooper(),
                                        "Presentation must be constructed on a looper thread."));
        this.mDisplayListener =
                new DisplayManager.DisplayListener() { // from class: android.app.Presentation.1
                    @Override // android.hardware.display.DisplayManager.DisplayListener
                    public void onDisplayAdded(int displayId) {}

                    @Override // android.hardware.display.DisplayManager.DisplayListener
                    public void onDisplayRemoved(int displayId) {
                        if (displayId == Presentation.this.mDisplay.getDisplayId()) {
                            Presentation.this.handleDisplayRemoved();
                        }
                    }

                    @Override // android.hardware.display.DisplayManager.DisplayListener
                    public void onDisplayChanged(int displayId) {
                        if (displayId == Presentation.this.mDisplay.getDisplayId()) {
                            Presentation.this.handleDisplayChanged();
                        }
                    }
                };
        this.mDisplay = display;
        this.mDisplayManager = (DisplayManager) getContext().getSystemService(DisplayManager.class);
        this.mOwnerPackageName = outerContext.getPackageName();
        if ((display.getFlags() & 4) != 0) {}
        Window w = getWindow();
        WindowManager.LayoutParams attr = w.getAttributes();
        w.setAttributes(attr);
        w.setGravity(119);
        w.setType(getWindowType(type, display));
        setCanceledOnTouchOutside(false);
    }

    private static int getWindowType(int type, Display display) {
        if (type != -1) {
            return type;
        }
        return (display.getFlags() & 4) != 0 ? 2030 : 2037;
    }

    public Display getDisplay() {
        return this.mDisplay;
    }

    public Resources getResources() {
        return getContext().getResources();
    }

    @Override // android.app.Dialog
    protected void onStart() {
        super.onStart();
        this.mDisplayManager.registerDisplayListener(this.mDisplayListener, this.mHandler);
        if (CoreRune.FW_FLEXIBLE_DUAL_MODE && this.mDisplay.getDisplayId() == 1) {
            DisplayInfo info = new DisplayInfo();
            boolean isValid = this.mDisplay.getDisplayInfo(info);
            Slog.i(
                    TAG,
                    "onStart() FLAG_REAR : "
                            + (info.flags & 8192)
                            + ", isValid="
                            + isValid
                            + " Presentation="
                            + this);
            if (isValid && (info.flags & 8192) != 0) {
                this.isRearDisplayPresentation = true;
            }
        }
        sendPresentationIntent(true);
    }

    @Override // android.app.Dialog
    protected void onStop() {
        sendPresentationIntent(false);
        this.mDisplayManager.unregisterDisplayListener(this.mDisplayListener);
        super.onStop();
    }

    @Override // android.app.Dialog
    public void show() {
        sendPresentationIntent(true);
        super.show();
    }

    @Override // android.app.Dialog
    public void hide() {
        super.hide();
        sendPresentationIntent(false);
    }

    public void onDisplayRemoved() {}

    public void onDisplayChanged() {}

    /* JADX INFO: Access modifiers changed from: private */
    public void handleDisplayRemoved() {
        onDisplayRemoved();
        cancel();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleDisplayChanged() {
        onDisplayChanged();
        if (CoreRune.FW_FLEXIBLE_DUAL_MODE && this.mDisplay.getDisplayId() == 1) {
            DisplayInfo info = new DisplayInfo();
            boolean isValid = this.mDisplay.getDisplayInfo(info);
            Slog.i(
                    TAG,
                    "handleDisplayChanged() FLAG_REAR : "
                            + (info.flags & 8192)
                            + ", isRearDisplayPresentation="
                            + this.isRearDisplayPresentation
                            + ", isValid="
                            + isValid
                            + " Presentation="
                            + this);
            if (this.isRearDisplayPresentation) {
                if (isValid && (info.flags & 8192) == 0) {
                    cancel();
                    return;
                }
                return;
            }
            if (isValid && (info.flags & 8192) != 0) {
                this.isRearDisplayPresentation = true;
            }
        }
    }

    private static Context createPresentationContext(
            Context outerContext, Display display, int theme) {
        return createPresentationContext(outerContext, display, theme, -1);
    }

    private static Context createPresentationContext(
            Context outerContext, Display display, int theme, int type) {
        if (outerContext == null) {
            throw new IllegalArgumentException("outerContext must not be null");
        }
        if (display == null) {
            throw new IllegalArgumentException("display must not be null");
        }
        Context windowContext =
                outerContext
                        .createDisplayContext(display)
                        .createWindowContext(getWindowType(type, display), null);
        if (theme == 0) {
            TypedValue outValue = new TypedValue();
            windowContext.getTheme().resolveAttribute(16843712, outValue, true);
            theme = outValue.resourceId;
        }
        return new ContextThemeWrapper(windowContext, theme);
    }

    private void sendPresentationIntent(boolean isStart) {
        if (isStart && !this.mIsStarted) {
            Intent intent = new Intent(DisplayManager.SEM_PRESENTATION_START);
            intent.putExtra(
                    ContactsContract.Directory.DISPLAY_NAME, this.mDisplay.getName().hashCode());
            intent.putExtra("displayID", this.mDisplay.getDisplayId());
            intent.putExtra("ownerPackageName", this.mOwnerPackageName);
            this.mContext.sendBroadcast(intent);
            this.mIsStarted = true;
            return;
        }
        if (!isStart && this.mIsStarted) {
            Intent intent2 = new Intent(DisplayManager.SEM_PRESENTATION_STOP);
            intent2.putExtra(
                    ContactsContract.Directory.DISPLAY_NAME, this.mDisplay.getName().hashCode());
            intent2.putExtra("displayID", this.mDisplay.getDisplayId());
            intent2.putExtra("ownerPackageName", this.mOwnerPackageName);
            this.mContext.sendBroadcast(intent2);
            this.mIsStarted = false;
        }
    }
}
