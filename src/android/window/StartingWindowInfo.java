package android.window;

import android.app.ActivityManager;
import android.content.pm.ActivityInfo;
import android.graphics.Rect;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.view.SurfaceControl;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.window.IWindowlessStartingSurfaceCallback;

/* loaded from: classes5.dex */
public final class StartingWindowInfo implements Parcelable {
    public static final Parcelable.Creator<StartingWindowInfo> CREATOR = new Parcelable.Creator<StartingWindowInfo>() { // from class: android.window.StartingWindowInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public StartingWindowInfo createFromParcel(Parcel parcel) {
            return new StartingWindowInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public StartingWindowInfo[] newArray(int i) {
            return new StartingWindowInfo[i];
        }
    };
    public static final int STARTING_WINDOW_TYPE_LEGACY_SPLASH_SCREEN = 4;
    public static final int STARTING_WINDOW_TYPE_NONE = 0;
    public static final int STARTING_WINDOW_TYPE_SNAPSHOT = 2;
    public static final int STARTING_WINDOW_TYPE_SOLID_COLOR_SPLASH_SCREEN = 3;
    public static final int STARTING_WINDOW_TYPE_SPLASH_SCREEN = 1;
    public static final int STARTING_WINDOW_TYPE_WINDOWLESS = 5;
    public static final int TYPE_PARAMETER_ACTIVITY_CREATED = 16;
    public static final int TYPE_PARAMETER_ACTIVITY_DRAWN = 64;
    public static final int TYPE_PARAMETER_ALLOW_HANDLE_SOLID_COLOR_SCREEN = 128;
    public static final int TYPE_PARAMETER_ALLOW_TASK_SNAPSHOT = 8;
    public static final int TYPE_PARAMETER_APP_PREFERS_ICON = 512;
    public static final int TYPE_PARAMETER_LEGACY_SPLASH_SCREEN = Integer.MIN_VALUE;
    public static final int TYPE_PARAMETER_NEW_TASK = 1;
    public static final int TYPE_PARAMETER_PROCESS_RUNNING = 4;
    public static final int TYPE_PARAMETER_TASK_SWITCH = 2;
    public static final int TYPE_PARAMETER_USE_SOLID_COLOR_SPLASH_SCREEN = 32;
    public static final int TYPE_PARAMETER_WINDOWLESS = 256;
    public IBinder appToken;
    public boolean isKeyguardOccluded;
    public WindowManager.LayoutParams mainWindowLayoutParams;
    public int requestedVisibleTypes;
    public SurfaceControl rootSurface;
    public int splashScreenThemeResId;
    public int startingWindowTypeParameter;
    public ActivityInfo targetActivityInfo;
    public final Rect taskBounds;
    public ActivityManager.RunningTaskInfo taskInfo;
    public TaskSnapshot taskSnapshot;
    public IWindowlessStartingSurfaceCallback windowlessStartingSurfaceCallback;

    public @interface StartingTypeParams {
    }

    public @interface StartingWindowType {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public void notifyAddComplete(SurfaceControl surfaceControl) {
        IWindowlessStartingSurfaceCallback iWindowlessStartingSurfaceCallback = this.windowlessStartingSurfaceCallback;
        if (iWindowlessStartingSurfaceCallback != null) {
            try {
                iWindowlessStartingSurfaceCallback.onSurfaceAdded(surfaceControl);
            } catch (RemoteException unused) {
            }
        }
    }

    public StartingWindowInfo() {
        this.taskBounds = new Rect();
        this.isKeyguardOccluded = false;
        this.requestedVisibleTypes = WindowInsets.Type.defaultVisible();
    }

    private StartingWindowInfo(Parcel parcel) {
        this.taskBounds = new Rect();
        this.isKeyguardOccluded = false;
        this.requestedVisibleTypes = WindowInsets.Type.defaultVisible();
        readFromParcel(parcel);
    }

    public boolean allowHandleSolidColorSplashScreen() {
        return (this.startingWindowTypeParameter & 128) != 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedObject(this.taskInfo, i);
        this.taskBounds.writeToParcel(parcel, i);
        parcel.writeTypedObject(this.targetActivityInfo, i);
        parcel.writeInt(this.startingWindowTypeParameter);
        parcel.writeTypedObject(this.mainWindowLayoutParams, i);
        parcel.writeInt(this.splashScreenThemeResId);
        parcel.writeBoolean(this.isKeyguardOccluded);
        parcel.writeTypedObject(this.taskSnapshot, i);
        parcel.writeInt(this.requestedVisibleTypes);
        parcel.writeStrongBinder(this.appToken);
        parcel.writeStrongInterface(this.windowlessStartingSurfaceCallback);
        parcel.writeTypedObject(this.rootSurface, i);
    }

    void readFromParcel(Parcel parcel) {
        this.taskInfo = (ActivityManager.RunningTaskInfo) parcel.readTypedObject(ActivityManager.RunningTaskInfo.CREATOR);
        this.taskBounds.readFromParcel(parcel);
        this.targetActivityInfo = (ActivityInfo) parcel.readTypedObject(ActivityInfo.CREATOR);
        this.startingWindowTypeParameter = parcel.readInt();
        this.mainWindowLayoutParams = (WindowManager.LayoutParams) parcel.readTypedObject(WindowManager.LayoutParams.CREATOR);
        this.splashScreenThemeResId = parcel.readInt();
        this.isKeyguardOccluded = parcel.readBoolean();
        this.taskSnapshot = (TaskSnapshot) parcel.readTypedObject(TaskSnapshot.CREATOR);
        this.requestedVisibleTypes = parcel.readInt();
        this.appToken = parcel.readStrongBinder();
        this.windowlessStartingSurfaceCallback = IWindowlessStartingSurfaceCallback.Stub.asInterface(parcel.readStrongBinder());
        this.rootSurface = (SurfaceControl) parcel.readTypedObject(SurfaceControl.CREATOR);
    }

    public String toString() {
        return "StartingWindowInfo{taskId=" + this.taskInfo.taskId + " targetActivityInfo=" + this.targetActivityInfo + " displayId=" + this.taskInfo.displayId + " topActivityType=" + this.taskInfo.topActivityType + " preferredStartingWindowType=" + Integer.toHexString(this.startingWindowTypeParameter) + " mainWindowLayoutParams=" + this.mainWindowLayoutParams + " splashScreenThemeResId " + Integer.toHexString(this.splashScreenThemeResId);
    }
}
