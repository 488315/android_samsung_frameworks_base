package com.android.internal.widget.remotecompose.player;

import android.app.Application;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
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
import com.android.internal.widget.remotecompose.player.RemoteComposePlayer;
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
                RemoteComposePlayer.IdActionCallbacks.this.onAction(i, str);
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
    /* JADX WARN: Code restructure failed: missing block: B:241:0x0274, code lost:
    
        if (r6.equals("colorControlHighlight") == false) goto L18;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void mapColors() {
        /*
            Method dump skipped, instructions count: 1420
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.internal.widget.remotecompose.player.RemoteComposePlayer.mapColors():void");
    }

    private void setRColor(String str, int i) {
        setColor(str, getColorFromResource(i));
    }

    private int getColorFromResource(int i) {
        TypedArray obtainStyledAttributes = getContext().getApplicationContext().obtainStyledAttributes(new TypedValue().data, new int[]{i});
        try {
            int color = obtainStyledAttributes.getColor(0, -1);
            if (obtainStyledAttributes != null) {
                obtainStyledAttributes.close();
            }
            return color;
        } catch (Throwable th) {
            if (obtainStyledAttributes != null) {
                try {
                    obtainStyledAttributes.close();
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
        int hasSensorListeners = this.mInner.hasSensorListeners(iArr);
        this.mAcc = null;
        this.mGyro = null;
        this.mMag = null;
        this.mLight = null;
        if (hasSensorListeners > 0) {
            this.mSensorManager = (SensorManager) ((Application) getContext().getApplicationContext()).getSystemService(Context.SENSOR_SERVICE);
            for (int i = 0; i < hasSensorListeners; i++) {
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
