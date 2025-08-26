package com.android.internal.widget.remotecompose.player;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.text.format.DateFormat;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ScrollView;
import com.android.internal.widget.remotecompose.accessibility.RemoteComposeTouchHelper;
import com.android.internal.widget.remotecompose.core.CoreDocument;
import com.android.internal.widget.remotecompose.core.RemoteContext;
import com.android.internal.widget.remotecompose.core.RemoteContextAware;
import com.android.internal.widget.remotecompose.player.platform.AndroidRemoteContext;
import com.android.internal.widget.remotecompose.player.platform.RemoteComposeCanvas;

/* loaded from: classes6.dex */
public class RemoteComposePlayer extends FrameLayout implements RemoteContextAware {
    private static final int MAX_SUPPORTED_MAJOR_VERSION = 1;
    private static final int MAX_SUPPORTED_MINOR_VERSION = 0;
    private static final int[] sHapticTable = {-1, 0, 1, 3, 4, 6, 3, 7, 8, 9, 12, 13, 16, 17, 21, 22, 23, 24, 25, 26, 27};
    Sensor mAcc;
    Sensor mGyro;
    private RemoteComposeCanvas mInner;
    Sensor mLight;
    SensorEventListener mListener;
    Sensor mMag;
    SensorManager mSensorManager;
    private CoreDocument.ShaderControl mShaderControl;

    public interface IdActionCallbacks {
        void onAction(int i, String str);
    }

    static /* synthetic */ boolean lambda$new$1(String str) {
        return false;
    }

    public RemoteComposePlayer(Context context) {
        super(context);
        this.mAcc = null;
        this.mGyro = null;
        this.mMag = null;
        this.mLight = null;
        this.mShaderControl = new CoreDocument.ShaderControl() { // from class: com.android.internal.widget.remotecompose.player.RemoteComposePlayer$$ExternalSyntheticLambda1
            @Override // com.android.internal.widget.remotecompose.core.CoreDocument.ShaderControl
            public final boolean isShaderValid(String str) {
                return RemoteComposePlayer.lambda$new$1(str);
            }
        };
        init(context, null, 0);
    }

    public RemoteComposePlayer(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mAcc = null;
        this.mGyro = null;
        this.mMag = null;
        this.mLight = null;
        this.mShaderControl = new CoreDocument.ShaderControl() { // from class: com.android.internal.widget.remotecompose.player.RemoteComposePlayer$$ExternalSyntheticLambda1
            @Override // com.android.internal.widget.remotecompose.core.CoreDocument.ShaderControl
            public final boolean isShaderValid(String str) {
                return RemoteComposePlayer.lambda$new$1(str);
            }
        };
        init(context, attributeSet, 0);
    }

    public RemoteComposePlayer(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mAcc = null;
        this.mGyro = null;
        this.mMag = null;
        this.mLight = null;
        this.mShaderControl = new CoreDocument.ShaderControl() { // from class: com.android.internal.widget.remotecompose.player.RemoteComposePlayer$$ExternalSyntheticLambda1
            @Override // com.android.internal.widget.remotecompose.core.CoreDocument.ShaderControl
            public final boolean isShaderValid(String str) {
                return RemoteComposePlayer.lambda$new$1(str);
            }
        };
        init(context, attributeSet, i);
    }

    @Override // com.android.internal.widget.remotecompose.core.RemoteContextAware
    public RemoteContext getRemoteContext() {
        return this.mInner.getRemoteContext();
    }

    @Override // android.view.View, android.view.ViewParent
    public void requestLayout() {
        super.requestLayout();
        RemoteComposeCanvas remoteComposeCanvas = this.mInner;
        if (remoteComposeCanvas != null) {
            remoteComposeCanvas.requestLayout();
        }
    }

    @Override // android.view.View
    public void invalidate() {
        super.invalidate();
        RemoteComposeCanvas remoteComposeCanvas = this.mInner;
        if (remoteComposeCanvas != null) {
            remoteComposeCanvas.invalidate();
        }
    }

    public boolean isDraggable() {
        return this.mInner.isDraggable();
    }

    public void setDebug(int i) {
        this.mInner.setDebug(i);
    }

    public RemoteComposeDocument getDocument() {
        return this.mInner.getDocument();
    }

    public void updateDocument(RemoteComposeDocument remoteComposeDocument) {
        AndroidRemoteContext androidRemoteContext = new AndroidRemoteContext();
        remoteComposeDocument.initializeContext(androidRemoteContext);
        float f = getContext().getResources().getDisplayMetrics().density;
        androidRemoteContext.setAnimationEnabled(true);
        androidRemoteContext.setDensity(f);
        androidRemoteContext.setUseChoreographer(false);
        this.mInner.getDocument().mDocument.applyUpdate(remoteComposeDocument.mDocument);
        this.mInner.invalidate();
    }

    public void setDocument(RemoteComposeDocument remoteComposeDocument) {
        if (remoteComposeDocument != null) {
            if (remoteComposeDocument.canBeDisplayed(1, 0, 0L)) {
                if (remoteComposeDocument.isUpdateDoc()) {
                    updateDocument(remoteComposeDocument);
                    return;
                } else {
                    this.mInner.setDocument(remoteComposeDocument);
                    applyContentBehavior(remoteComposeDocument.getDocument().getContentScroll());
                }
            } else {
                Log.e("RemoteComposePlayer", "Unsupported document ");
            }
            RemoteComposeTouchHelper.REGISTRAR.setAccessibilityDelegate(this, remoteComposeDocument.getDocument());
        } else {
            this.mInner.setDocument(null);
            RemoteComposeTouchHelper.REGISTRAR.clearAccessibilityDelegate(this);
        }
        mapColors();
        setupSensors();
        this.mInner.setHapticEngine(new CoreDocument.HapticEngine() { // from class: com.android.internal.widget.remotecompose.player.RemoteComposePlayer.1
            @Override // com.android.internal.widget.remotecompose.core.CoreDocument.HapticEngine
            public void haptic(int i) {
                RemoteComposePlayer.this.provideHapticFeedback(i);
            }
        });
        this.mInner.checkShaders(this.mShaderControl);
    }

    private void applyContentBehavior(int i) {
        if (i == 1) {
            if (this.mInner.getParent() instanceof HorizontalScrollView) {
                return;
            }
            ((ViewGroup) this.mInner.getParent()).removeView(this.mInner);
            removeAllViews();
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-2, -1);
            HorizontalScrollView horizontalScrollView = new HorizontalScrollView(getContext());
            horizontalScrollView.setBackgroundColor(0);
            horizontalScrollView.setFillViewport(true);
            horizontalScrollView.addView(this.mInner, layoutParams);
            addView(horizontalScrollView, new FrameLayout.LayoutParams(-1, -1));
            return;
        }
        if (i == 2) {
            if (this.mInner.getParent() instanceof ScrollView) {
                return;
            }
            ((ViewGroup) this.mInner.getParent()).removeView(this.mInner);
            removeAllViews();
            FrameLayout.LayoutParams layoutParams2 = new FrameLayout.LayoutParams(-1, -2);
            ScrollView scrollView = new ScrollView(getContext());
            scrollView.setBackgroundColor(0);
            scrollView.setFillViewport(true);
            scrollView.addView(this.mInner, layoutParams2);
            addView(scrollView, new FrameLayout.LayoutParams(-1, -1));
            return;
        }
        if (this.mInner.getParent() != this) {
            ((ViewGroup) this.mInner.getParent()).removeView(this.mInner);
            removeAllViews();
            addView(this.mInner, new FrameLayout.LayoutParams(-1, -1));
        }
    }

    private void init(Context context, AttributeSet attributeSet, int i) {
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-1, -1);
        setBackgroundColor(0);
        RemoteComposeCanvas remoteComposeCanvas = new RemoteComposeCanvas(context, attributeSet, i);
        this.mInner = remoteComposeCanvas;
        remoteComposeCanvas.setBackgroundColor(0);
        addView(this.mInner, layoutParams);
    }

    public void setLocalString(String str, String str2, String str3) {
        this.mInner.setLocalString(str + ":" + str2, str3);
    }

    public void clearLocalString(String str, String str2) {
        this.mInner.clearLocalString(str + ":" + str2);
    }

    public void setUserLocalString(String str, String str2) {
        this.mInner.setLocalString("USER:" + str, str2);
    }

    public void setUserLocalInt(String str, int i) {
        this.mInner.setLocalInt("USER:" + str, i);
    }

    public void setUserLocalColor(String str, int i) {
        this.mInner.setLocalColor("USER:" + str, i);
    }

    public void setUserLocalFloat(String str, float f) {
        this.mInner.setLocalFloat("USER:" + str, Float.valueOf(f));
    }

    public void setUserLocalBitmap(String str, Bitmap bitmap) {
        this.mInner.setLocalBitmap("USER:" + str, bitmap);
    }

    public void clearUserLocalBitmap(String str) {
        this.mInner.clearLocalBitmap("USER:" + str);
    }

    public void clearUserLocalString(String str) {
        this.mInner.clearLocalString("USER:" + str);
    }

    public void clearUserLocalInt(String str) {
        this.mInner.clearLocalInt("USER:" + str);
    }

    public void clearUserLocalColor(String str) {
        this.mInner.clearLocalColor("USER:" + str);
    }

    public void clearUserLocalFloat(String str) {
        this.mInner.clearLocalFloat("USER:" + str);
    }

    public void setSystemLocalString(String str, String str2) {
        this.mInner.setLocalString("SYSTEM:" + str, str2);
    }

    public void clearSystemLocalString(String str) {
        this.mInner.clearLocalString("SYSTEM:" + str);
    }

    public int getOpsPerFrame() {
        return this.mInner.getDocument().mDocument.getOpsPerFrame();
    }

    public void setUseChoreographer(boolean z) {
        this.mInner.setUseChoreographer(z);
    }

    public void addIdActionListener(final IdActionCallbacks idActionCallbacks) {
        this.mInner.addIdActionListener(new RemoteComposeCanvas.ClickCallbacks() { // from class: com.android.internal.widget.remotecompose.player.RemoteComposePlayer$$ExternalSyntheticLambda0
            @Override // com.android.internal.widget.remotecompose.player.platform.RemoteComposeCanvas.ClickCallbacks
            public final void click(int i, String str) {
                idActionCallbacks.onAction(i, str);
            }
        });
    }

    public void setTheme(int i) {
        if (this.mInner.getTheme() != i) {
            this.mInner.setTheme(i);
            this.mInner.invalidate();
        }
    }

    public String[] getNamedColors() {
        return this.mInner.getNamedColors();
    }

    public String[] getNamedFloats() {
        return this.mInner.getNamedVariables(1);
    }

    public String[] getNamedStrings() {
        return this.mInner.getNamedVariables(0);
    }

    public String[] getNamedImages() {
        return this.mInner.getNamedVariables(3);
    }

    public void setColor(String str, int i) {
        this.mInner.setColor(str, i);
    }

    public void setLong(String str, long j) {
        this.mInner.setLong(str, j);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Removed duplicated region for block: B:18:0x0036  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void mapColors() {
        String[] namedColors = getNamedColors();
        if (namedColors == null) {
            return;
        }
        for (String str : namedColors) {
            if (str.startsWith("android.")) {
                for (String str2 : namedColors) {
                    if (str2.startsWith("android.")) {
                        char c = '\b';
                        String strSubstring = str2.substring(8);
                        strSubstring.hashCode();
                        switch (strSubstring.hashCode()) {
                            case -2102088395:
                                if (strSubstring.equals("colorPrimaryDark")) {
                                    c = 0;
                                    break;
                                } else {
                                    c = 65535;
                                    break;
                                }
                            case -2039515683:
                                if (strSubstring.equals("textColorHint")) {
                                    c = 1;
                                    break;
                                }
                                break;
                            case -2039396528:
                                if (strSubstring.equals("textColorLink")) {
                                    c = 2;
                                    break;
                                }
                                break;
                            case -1782536627:
                                if (strSubstring.equals("panelColorBackground")) {
                                    c = 3;
                                    break;
                                }
                                break;
                            case -1650386285:
                                if (strSubstring.equals("textColorHintInverse")) {
                                    c = 4;
                                    break;
                                }
                                break;
                            case -1593570486:
                                if (strSubstring.equals("colorForegroundInverse")) {
                                    c = 5;
                                    break;
                                }
                                break;
                            case -1592065711:
                                if (strSubstring.equals("colorSecondary")) {
                                    c = 6;
                                    break;
                                }
                                break;
                            case -1576541890:
                                if (strSubstring.equals("windowBackground")) {
                                    c = 7;
                                    break;
                                }
                                break;
                            case -1288811174:
                                if (!strSubstring.equals("colorControlHighlight")) {
                                }
                                break;
                            case -1125023003:
                                if (strSubstring.equals("editTextBackground")) {
                                    c = '\t';
                                    break;
                                }
                                break;
                            case -1063571914:
                                if (strSubstring.equals("textColor")) {
                                    c = '\n';
                                    break;
                                }
                                break;
                            case -975843166:
                                if (strSubstring.equals("colorMultiSelectHighlight")) {
                                    c = 11;
                                    break;
                                }
                                break;
                            case -965862626:
                                if (strSubstring.equals("textColorHighlight")) {
                                    c = '\f';
                                    break;
                                }
                                break;
                            case -914062549:
                                if (strSubstring.equals("numbersBackgroundColor")) {
                                    c = '\r';
                                    break;
                                }
                                break;
                            case -704278530:
                                if (strSubstring.equals("actionBarItemBackground")) {
                                    c = 14;
                                    break;
                                }
                                break;
                            case -626360975:
                                if (strSubstring.equals("colorEdgeEffect")) {
                                    c = 15;
                                    break;
                                }
                                break;
                            case -549202682:
                                if (strSubstring.equals("colorForeground")) {
                                    c = 16;
                                    break;
                                }
                                break;
                            case -403207561:
                                if (strSubstring.equals("colorControlActivated")) {
                                    c = 17;
                                    break;
                                }
                                break;
                            case -363801547:
                                if (strSubstring.equals("colorPressedHighlight")) {
                                    c = 18;
                                    break;
                                }
                                break;
                            case -362557888:
                                if (strSubstring.equals("windowBackgroundFallback")) {
                                    c = 19;
                                    break;
                                }
                                break;
                            case -226037738:
                                if (strSubstring.equals("queryBackground")) {
                                    c = 20;
                                    break;
                                }
                                break;
                            case -122595674:
                                if (strSubstring.equals("colorActivatedHighlight")) {
                                    c = 21;
                                    break;
                                }
                                break;
                            case 21670977:
                                if (strSubstring.equals("panelFullBackground")) {
                                    c = 22;
                                    break;
                                }
                                break;
                            case 45652631:
                                if (strSubstring.equals("selectableItemBackground")) {
                                    c = 23;
                                    break;
                                }
                                break;
                            case 221609330:
                                if (strSubstring.equals("textColorSecondaryInverse")) {
                                    c = 24;
                                    break;
                                }
                                break;
                            case 235417089:
                                if (strSubstring.equals("colorControlNormal")) {
                                    c = 25;
                                    break;
                                }
                                break;
                            case 283746088:
                                if (strSubstring.equals("detailsElementBackground")) {
                                    c = 26;
                                    break;
                                }
                                break;
                            case 429113585:
                                if (strSubstring.equals("colorBackground")) {
                                    c = 27;
                                    break;
                                }
                                break;
                            case 450722317:
                                if (strSubstring.equals("colorAccent")) {
                                    c = 28;
                                    break;
                                }
                                break;
                            case 554839815:
                                if (strSubstring.equals("actionModeBackground")) {
                                    c = 29;
                                    break;
                                }
                                break;
                            case 643196032:
                                if (strSubstring.equals("colorFocusedHighlight")) {
                                    c = 30;
                                    break;
                                }
                                break;
                            case 764079441:
                                if (strSubstring.equals("colorLongPressedHighlight")) {
                                    c = 31;
                                    break;
                                }
                                break;
                            case 828846950:
                                if (strSubstring.equals("submitBackground")) {
                                    c = ' ';
                                    break;
                                }
                                break;
                            case 971680791:
                                if (strSubstring.equals("colorBackgroundFloating")) {
                                    c = '!';
                                    break;
                                }
                                break;
                            case 1167221096:
                                if (strSubstring.equals("textColorTertiaryInverse")) {
                                    c = '\"';
                                    break;
                                }
                                break;
                            case 1171326120:
                                if (strSubstring.equals("textColorTertiary")) {
                                    c = '#';
                                    break;
                                }
                                break;
                            case 1171855791:
                                if (strSubstring.equals("actionModeSplitBackground")) {
                                    c = '$';
                                    break;
                                }
                                break;
                            case 1201383868:
                                if (strSubstring.equals("colorButtonNormal")) {
                                    c = '%';
                                    break;
                                }
                                break;
                            case 1265621093:
                                if (strSubstring.equals("colorError")) {
                                    c = '&';
                                    break;
                                }
                                break;
                            case 1295462561:
                                if (strSubstring.equals("itemBackground")) {
                                    c = DateFormat.QUOTE;
                                    break;
                                }
                                break;
                            case 1432553371:
                                if (strSubstring.equals("headerBackground")) {
                                    c = '(';
                                    break;
                                }
                                break;
                            case 1466294948:
                                if (strSubstring.equals("textColorPrimaryInverse")) {
                                    c = ')';
                                    break;
                                }
                                break;
                            case 1467366547:
                                if (strSubstring.equals("galleryItemBackground")) {
                                    c = '*';
                                    break;
                                }
                                break;
                            case 1592962406:
                                if (strSubstring.equals("colorSwitchThumbNormal")) {
                                    c = '+';
                                    break;
                                }
                                break;
                            case 1614106424:
                                if (strSubstring.equals("colorBackgroundCacheHint")) {
                                    c = ',';
                                    break;
                                }
                                break;
                            case 1649444434:
                                if (strSubstring.equals("panelBackground")) {
                                    c = '-';
                                    break;
                                }
                                break;
                            case 1747237626:
                                if (strSubstring.equals("popupBackground")) {
                                    c = '.';
                                    break;
                                }
                                break;
                            case 1950347551:
                                if (strSubstring.equals("colorPrimary")) {
                                    c = '/';
                                    break;
                                }
                                break;
                            case 2137120496:
                                if (strSubstring.equals("activatedBackgroundIndicator")) {
                                    c = '0';
                                    break;
                                }
                                break;
                        }
                        switch (c) {
                            case 0:
                                setRColor(str2, 16843828);
                                break;
                            case 1:
                                setRColor(str2, 16842906);
                                break;
                            case 2:
                                setRColor(str2, 16842907);
                                break;
                            case 3:
                                setRColor(str2, 16842849);
                                break;
                            case 4:
                                setRColor(str2, 16842815);
                                break;
                            case 5:
                                setRColor(str2, 16843270);
                                break;
                            case 6:
                                setRColor(str2, 16844080);
                                break;
                            case 7:
                                setRColor(str2, 16842836);
                                break;
                            case '\b':
                                setRColor(str2, 16843820);
                                break;
                            case '\t':
                                setRColor(str2, 16843602);
                                break;
                            case '\n':
                                setRColor(str2, 16842904);
                                break;
                            case 11:
                                setRColor(str2, 16843665);
                                break;
                            case '\f':
                                setRColor(str2, 16842905);
                                break;
                            case '\r':
                                setRColor(str2, 16843938);
                                break;
                            case 14:
                                setRColor(str2, 16843676);
                                break;
                            case 15:
                                setRColor(str2, 16843982);
                                break;
                            case 16:
                                setRColor(str2, 16842800);
                                break;
                            case 17:
                                setRColor(str2, 16843818);
                                break;
                            case 18:
                                setRColor(str2, 16843661);
                                break;
                            case 19:
                                setRColor(str2, 16844035);
                                break;
                            case 20:
                                setRColor(str2, 16843911);
                                break;
                            case 21:
                                setRColor(str2, 16843664);
                                break;
                            case 22:
                                setRColor(str2, 16842847);
                                break;
                            case 23:
                                setRColor(str2, 16843534);
                                break;
                            case 24:
                                setRColor(str2, 16842810);
                                break;
                            case 25:
                                setRColor(str2, 16843817);
                                break;
                            case 26:
                                setRColor(str2, 16843598);
                                break;
                            case 27:
                                setRColor(str2, 16842801);
                                break;
                            case 28:
                                setRColor(str2, 16843829);
                                break;
                            case 29:
                                setRColor(str2, 16843483);
                                break;
                            case 30:
                                setRColor(str2, 16843663);
                                break;
                            case 31:
                                setRColor(str2, 16843662);
                                break;
                            case ' ':
                                setRColor(str2, 16843912);
                                break;
                            case '!':
                                setRColor(str2, 16844002);
                                break;
                            case '\"':
                                setRColor(str2, 16843283);
                                break;
                            case '#':
                                setRColor(str2, 16843282);
                                break;
                            case '$':
                                setRColor(str2, 16843677);
                                break;
                            case '%':
                                setRColor(str2, 16843819);
                                break;
                            case '&':
                                setRColor(str2, 16844099);
                                break;
                            case '\'':
                                setRColor(str2, 16843056);
                                break;
                            case '(':
                                setRColor(str2, 16843055);
                                break;
                            case ')':
                                setRColor(str2, 16842809);
                                break;
                            case '*':
                                setRColor(str2, 16842828);
                                break;
                            case '+':
                                setRColor(str2, 16843817);
                                break;
                            case ',':
                                setRColor(str2, 16843435);
                                break;
                            case '-':
                                setRColor(str2, 16842846);
                                break;
                            case '.':
                                setRColor(str2, 16843126);
                                break;
                            case '/':
                                setRColor(str2, 16843827);
                                break;
                            case '0':
                                setRColor(str2, 16843517);
                                break;
                        }
                    }
                }
                return;
            }
        }
    }

    private void setRColor(String str, int i) {
        setColor(str, getColorFromResource(i));
    }

    private int getColorFromResource(int i) throws Resources.NotFoundException {
        TypedArray typedArrayObtainStyledAttributes = getContext().getApplicationContext().obtainStyledAttributes(new TypedValue().data, new int[]{i});
        try {
            int color = typedArrayObtainStyledAttributes.getColor(0, -1);
            if (typedArrayObtainStyledAttributes != null) {
                typedArrayObtainStyledAttributes.close();
            }
            return color;
        } catch (Throwable th) {
            if (typedArrayObtainStyledAttributes != null) {
                try {
                    typedArrayObtainStyledAttributes.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void provideHapticFeedback(int i) {
        int[] iArr = sHapticTable;
        performHapticFeedback(iArr[i % iArr.length]);
    }

    private void setupSensors() {
        int[] iArr = new int[10];
        int iHasSensorListeners = this.mInner.hasSensorListeners(iArr);
        this.mAcc = null;
        this.mGyro = null;
        this.mMag = null;
        this.mLight = null;
        if (iHasSensorListeners > 0) {
            this.mSensorManager = (SensorManager) ((Application) getContext().getApplicationContext()).getSystemService(Context.SENSOR_SERVICE);
            for (int i = 0; i < iHasSensorListeners; i++) {
                switch (iArr[i]) {
                    case 17:
                    case 18:
                    case 19:
                        if (this.mAcc == null) {
                            this.mAcc = this.mSensorManager.getDefaultSensor(1);
                            break;
                        } else {
                            break;
                        }
                    case 20:
                    case 21:
                    case 22:
                        if (this.mGyro == null) {
                            this.mGyro = this.mSensorManager.getDefaultSensor(4);
                            break;
                        } else {
                            break;
                        }
                    case 23:
                    case 24:
                    case 25:
                        if (this.mMag == null) {
                            this.mMag = this.mSensorManager.getDefaultSensor(2);
                            break;
                        } else {
                            break;
                        }
                    case 26:
                        if (this.mLight == null) {
                            this.mLight = this.mSensorManager.getDefaultSensor(5);
                            break;
                        } else {
                            break;
                        }
                }
            }
        }
        registerListener();
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        unregisterListener();
    }

    public void registerListener() {
        Sensor[] sensorArr = new Sensor[4];
        if (this.mListener != null) {
            unregisterListener();
        }
        SensorEventListener sensorEventListener = new SensorEventListener() { // from class: com.android.internal.widget.remotecompose.player.RemoteComposePlayer.2
            @Override // android.hardware.SensorEventListener
            public void onAccuracyChanged(Sensor sensor, int i) {
            }

            @Override // android.hardware.SensorEventListener
            public void onSensorChanged(SensorEvent sensorEvent) {
                if (sensorEvent.sensor == RemoteComposePlayer.this.mAcc) {
                    RemoteComposePlayer.this.mInner.setExternalFloat(17, sensorEvent.values[0]);
                    RemoteComposePlayer.this.mInner.setExternalFloat(18, sensorEvent.values[1]);
                    RemoteComposePlayer.this.mInner.setExternalFloat(19, sensorEvent.values[2]);
                } else if (sensorEvent.sensor == RemoteComposePlayer.this.mGyro) {
                    RemoteComposePlayer.this.mInner.setExternalFloat(20, sensorEvent.values[0]);
                    RemoteComposePlayer.this.mInner.setExternalFloat(21, sensorEvent.values[1]);
                    RemoteComposePlayer.this.mInner.setExternalFloat(22, sensorEvent.values[2]);
                } else if (sensorEvent.sensor == RemoteComposePlayer.this.mMag) {
                    RemoteComposePlayer.this.mInner.setExternalFloat(23, sensorEvent.values[0]);
                    RemoteComposePlayer.this.mInner.setExternalFloat(24, sensorEvent.values[1]);
                    RemoteComposePlayer.this.mInner.setExternalFloat(25, sensorEvent.values[2]);
                } else if (sensorEvent.sensor == RemoteComposePlayer.this.mLight) {
                    RemoteComposePlayer.this.mInner.setExternalFloat(26, sensorEvent.values[0]);
                }
            }
        };
        Sensor[] sensorArr2 = {this.mAcc, this.mGyro, this.mMag, this.mLight};
        for (int i = 0; i < 4; i++) {
            Sensor sensor = sensorArr2[i];
            if (sensor != null) {
                this.mListener = sensorEventListener;
                this.mSensorManager.registerListener(sensorEventListener, sensor, 3);
            }
        }
    }

    public void unregisterListener() {
        SensorManager sensorManager;
        SensorEventListener sensorEventListener = this.mListener;
        if (sensorEventListener != null && (sensorManager = this.mSensorManager) != null) {
            sensorManager.unregisterListener(sensorEventListener);
        }
        this.mListener = null;
    }

    public float getEvalTime() {
        return this.mInner.getEvalTime();
    }

    public void setShaderControl(CoreDocument.ShaderControl shaderControl) {
        this.mShaderControl = shaderControl;
    }
}
