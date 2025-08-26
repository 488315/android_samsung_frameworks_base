package android.app;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.hardware.display.DisplayManager;
import android.os.Handler;
import android.os.Looper;
import android.provider.ContactsContract;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.Display;
import android.view.Window;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
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

    public void onDisplayChanged() {
    }

    public void onDisplayRemoved() {
    }

    public Presentation(Context context, Display display) {
        this(context, display, 0);
    }

    public Presentation(Context context, Display display, int i) {
        this(context, display, i, -1);
    }

    public Presentation(Context context, Display display, int i, int i2) {
        super(createPresentationContext(context, display, i, i2), i, false);
        this.mHandler = new Handler((Looper) Objects.requireNonNull(Looper.myLooper(), "Presentation must be constructed on a looper thread."));
        this.mDisplayListener = new DisplayManager.DisplayListener() { // from class: android.app.Presentation.1
            @Override // android.hardware.display.DisplayManager.DisplayListener
            public void onDisplayAdded(int i3) {
            }

            @Override // android.hardware.display.DisplayManager.DisplayListener
            public void onDisplayRemoved(int i3) {
                if (i3 == Presentation.this.mDisplay.getDisplayId()) {
                    Presentation.this.handleDisplayRemoved();
                }
            }

            @Override // android.hardware.display.DisplayManager.DisplayListener
            public void onDisplayChanged(int i3) {
                if (i3 == Presentation.this.mDisplay.getDisplayId()) {
                    Presentation.this.handleDisplayChanged();
                }
            }
        };
        this.mDisplay = display;
        this.mDisplayManager = (DisplayManager) getContext().getSystemService(DisplayManager.class);
        this.mOwnerPackageName = context.getPackageName();
        display.getFlags();
        Window window = getWindow();
        window.setAttributes(window.getAttributes());
        window.setGravity(119);
        window.setType(getWindowType(i2, display));
        setCanceledOnTouchOutside(false);
    }

    private static int getWindowType(int i, Display display) {
        return i != -1 ? i : (display.getFlags() & 4) != 0 ? 2030 : 2037;
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
        WindowInsetsController insetsController = getWindow().getInsetsController();
        if (insetsController == null || !com.android.internal.hidden_from_bootclasspath.com.android.window.flags.Flags.enablePresentationForConnectedDisplays()) {
            return;
        }
        insetsController.hide(WindowInsets.Type.systemBars());
    }

    @Override // android.app.Dialog
    public void hide() {
        super.hide();
        sendPresentationIntent(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleDisplayRemoved() {
        onDisplayRemoved();
        cancel();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleDisplayChanged() {
        onDisplayChanged();
    }

    private static Context createPresentationContext(Context context, Display display, int i) {
        return createPresentationContext(context, display, i, -1);
    }

    private static Context createPresentationContext(Context context, Display display, int i, int i2) {
        if (context == null) {
            throw new IllegalArgumentException("outerContext must not be null");
        }
        if (display == null) {
            throw new IllegalArgumentException("display must not be null");
        }
        Context contextCreateWindowContext = context.createDisplayContext(display).createWindowContext(getWindowType(i2, display), null);
        if (i == 0) {
            TypedValue typedValue = new TypedValue();
            contextCreateWindowContext.getTheme().resolveAttribute(16843712, typedValue, true);
            i = typedValue.resourceId;
        }
        return new ContextThemeWrapper(contextCreateWindowContext, i);
    }

    private void sendPresentationIntent(boolean z) {
        if (z && !this.mIsStarted) {
            Intent intent = new Intent(DisplayManager.SEM_PRESENTATION_START);
            intent.putExtra(ContactsContract.Directory.DISPLAY_NAME, this.mDisplay.getName().hashCode());
            intent.putExtra("displayID", this.mDisplay.getDisplayId());
            intent.putExtra("ownerPackageName", this.mOwnerPackageName);
            this.mContext.sendBroadcast(intent);
            this.mIsStarted = true;
            return;
        }
        if (z || !this.mIsStarted) {
            return;
        }
        Intent intent2 = new Intent(DisplayManager.SEM_PRESENTATION_STOP);
        intent2.putExtra(ContactsContract.Directory.DISPLAY_NAME, this.mDisplay.getName().hashCode());
        intent2.putExtra("displayID", this.mDisplay.getDisplayId());
        intent2.putExtra("ownerPackageName", this.mOwnerPackageName);
        this.mContext.sendBroadcast(intent2);
        this.mIsStarted = false;
    }
}
