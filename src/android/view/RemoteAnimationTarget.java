package android.view;

import android.app.ActivityManager;
import android.app.WindowConfiguration;
import android.graphics.GraphicsProtos;
import android.graphics.Point;
import android.graphics.Rect;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.proto.ProtoOutputStream;
import com.samsung.android.rune.CoreRune;
import java.io.PrintWriter;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes4.dex */
public class RemoteAnimationTarget implements Parcelable {
    public static final Parcelable.Creator<RemoteAnimationTarget> CREATOR = new Parcelable.Creator<RemoteAnimationTarget>() { // from class: android.view.RemoteAnimationTarget.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public RemoteAnimationTarget createFromParcel(Parcel parcel) {
            return new RemoteAnimationTarget(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public RemoteAnimationTarget[] newArray(int i) {
            return new RemoteAnimationTarget[i];
        }
    };
    public static final int MODE_CHANGING = 2;
    public static final int MODE_CLOSING = 1;
    public static final int MODE_OPENING = 0;
    public boolean allowEnterPip;
    public int backgroundColor;
    public final Rect clipRect;
    public final Rect contentInsets;
    private int displayId;
    public boolean hasAnimatingParent;
    public boolean isNotInRecents;
    public final boolean isTranslucent;
    public final SurfaceControl leash;
    public final Rect localBounds;
    public final int mode;

    @Deprecated
    public final Point position;

    @Deprecated
    public final int prefixOrderIndex;
    public int rotationChange;
    public final Rect screenSpaceBounds;
    public boolean showBackdrop;

    @Deprecated
    public final Rect sourceContainerBounds;
    public final Rect startBounds;
    public final SurfaceControl startLeash;
    public final int taskId;
    public ActivityManager.RunningTaskInfo taskInfo;
    public boolean willShowImeOnTarget;
    public final WindowConfiguration windowConfiguration;
    public final int windowType;

    @Retention(RetentionPolicy.SOURCE)
    public @interface Mode {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public RemoteAnimationTarget(int i, int i2, SurfaceControl surfaceControl, boolean z, Rect rect, Rect rect2, int i3, Point point, Rect rect3, Rect rect4, WindowConfiguration windowConfiguration, boolean z2, SurfaceControl surfaceControl2, Rect rect5, ActivityManager.RunningTaskInfo runningTaskInfo, boolean z3) {
        this(i, i2, surfaceControl, z, rect, rect2, i3, point, rect3, rect4, windowConfiguration, z2, surfaceControl2, rect5, runningTaskInfo, z3, -1);
    }

    public RemoteAnimationTarget(int i, int i2, SurfaceControl surfaceControl, boolean z, Rect rect, Rect rect2, int i3, Point point, Rect rect3, Rect rect4, WindowConfiguration windowConfiguration, boolean z2, SurfaceControl surfaceControl2, Rect rect5, ActivityManager.RunningTaskInfo runningTaskInfo, boolean z3, int i4) {
        Rect rect6;
        this.mode = i2;
        this.taskId = i;
        this.leash = surfaceControl;
        this.isTranslucent = z;
        this.clipRect = new Rect(rect);
        this.contentInsets = new Rect(rect2);
        this.prefixOrderIndex = i3;
        this.position = point == null ? new Point() : new Point(point);
        this.localBounds = new Rect(rect3);
        this.sourceContainerBounds = new Rect(rect4);
        this.screenSpaceBounds = new Rect(rect4);
        this.windowConfiguration = windowConfiguration;
        this.isNotInRecents = z2;
        this.startLeash = surfaceControl2;
        this.taskInfo = runningTaskInfo;
        this.allowEnterPip = z3;
        this.windowType = i4;
        if (rect5 == null) {
            rect6 = new Rect(rect4);
        } else {
            rect6 = new Rect(rect5);
        }
        this.startBounds = rect6;
    }

    public RemoteAnimationTarget(Parcel parcel) {
        this.taskId = parcel.readInt();
        this.mode = parcel.readInt();
        SurfaceControl surfaceControl = (SurfaceControl) parcel.readTypedObject(SurfaceControl.CREATOR);
        this.leash = surfaceControl;
        if (surfaceControl != null) {
            surfaceControl.setUnreleasedWarningCallSite("RemoteAnimationTarget[leash]");
        }
        this.isTranslucent = parcel.readBoolean();
        this.clipRect = (Rect) parcel.readTypedObject(Rect.CREATOR);
        this.contentInsets = (Rect) parcel.readTypedObject(Rect.CREATOR);
        this.prefixOrderIndex = parcel.readInt();
        this.position = (Point) parcel.readTypedObject(Point.CREATOR);
        this.localBounds = (Rect) parcel.readTypedObject(Rect.CREATOR);
        this.sourceContainerBounds = (Rect) parcel.readTypedObject(Rect.CREATOR);
        this.screenSpaceBounds = (Rect) parcel.readTypedObject(Rect.CREATOR);
        this.windowConfiguration = (WindowConfiguration) parcel.readTypedObject(WindowConfiguration.CREATOR);
        this.isNotInRecents = parcel.readBoolean();
        SurfaceControl surfaceControl2 = (SurfaceControl) parcel.readTypedObject(SurfaceControl.CREATOR);
        this.startLeash = surfaceControl2;
        if (surfaceControl2 != null) {
            surfaceControl2.setUnreleasedWarningCallSite("RemoteAnimationTarget[startLeash]");
        }
        this.startBounds = (Rect) parcel.readTypedObject(Rect.CREATOR);
        this.taskInfo = (ActivityManager.RunningTaskInfo) parcel.readTypedObject(ActivityManager.RunningTaskInfo.CREATOR);
        this.allowEnterPip = parcel.readBoolean();
        this.windowType = parcel.readInt();
        this.hasAnimatingParent = parcel.readBoolean();
        this.backgroundColor = parcel.readInt();
        this.showBackdrop = parcel.readBoolean();
        this.willShowImeOnTarget = parcel.readBoolean();
        this.rotationChange = parcel.readInt();
        if (CoreRune.FW_PREDICTIVE_BACK_ANIM) {
            this.displayId = parcel.readInt();
        }
    }

    public void setShowBackdrop(boolean z) {
        this.showBackdrop = z;
    }

    public void setWillShowImeOnTarget(boolean z) {
        this.willShowImeOnTarget = z;
    }

    public boolean willShowImeOnTarget() {
        return this.willShowImeOnTarget;
    }

    public void setRotationChange(int i) {
        this.rotationChange = i;
    }

    public int getRotationChange() {
        return this.rotationChange;
    }

    public void setDisplayId(int i) {
        this.displayId = i;
    }

    public int getDisplayId() {
        return this.displayId;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.taskId);
        parcel.writeInt(this.mode);
        parcel.writeTypedObject(this.leash, 0);
        parcel.writeBoolean(this.isTranslucent);
        parcel.writeTypedObject(this.clipRect, 0);
        parcel.writeTypedObject(this.contentInsets, 0);
        parcel.writeInt(this.prefixOrderIndex);
        parcel.writeTypedObject(this.position, 0);
        parcel.writeTypedObject(this.localBounds, 0);
        parcel.writeTypedObject(this.sourceContainerBounds, 0);
        parcel.writeTypedObject(this.screenSpaceBounds, 0);
        parcel.writeTypedObject(this.windowConfiguration, 0);
        parcel.writeBoolean(this.isNotInRecents);
        parcel.writeTypedObject(this.startLeash, 0);
        parcel.writeTypedObject(this.startBounds, 0);
        parcel.writeTypedObject(this.taskInfo, 0);
        parcel.writeBoolean(this.allowEnterPip);
        parcel.writeInt(this.windowType);
        parcel.writeBoolean(this.hasAnimatingParent);
        parcel.writeInt(this.backgroundColor);
        parcel.writeBoolean(this.showBackdrop);
        parcel.writeBoolean(this.willShowImeOnTarget);
        parcel.writeInt(this.rotationChange);
        if (CoreRune.FW_PREDICTIVE_BACK_ANIM) {
            parcel.writeInt(this.displayId);
        }
    }

    public void dump(PrintWriter printWriter, String str) {
        printWriter.print(str);
        printWriter.print("mode=");
        printWriter.print(this.mode);
        printWriter.print(" taskId=");
        printWriter.print(this.taskId);
        printWriter.print(" isTranslucent=");
        printWriter.print(this.isTranslucent);
        printWriter.print(" clipRect=");
        this.clipRect.printShortString(printWriter);
        printWriter.print(" contentInsets=");
        this.contentInsets.printShortString(printWriter);
        printWriter.print(" prefixOrderIndex=");
        printWriter.print(this.prefixOrderIndex);
        printWriter.print(" position=");
        printPoint(this.position, printWriter);
        printWriter.print(" sourceContainerBounds=");
        this.sourceContainerBounds.printShortString(printWriter);
        printWriter.print(" screenSpaceBounds=");
        this.screenSpaceBounds.printShortString(printWriter);
        printWriter.print(" localBounds=");
        this.localBounds.printShortString(printWriter);
        printWriter.println();
        printWriter.print(str);
        printWriter.print("windowConfiguration=");
        printWriter.println(this.windowConfiguration);
        printWriter.print(str);
        printWriter.print("leash=");
        printWriter.println(this.leash);
        printWriter.print(str);
        printWriter.print("taskInfo=");
        printWriter.println(this.taskInfo);
        printWriter.print(str);
        printWriter.print("allowEnterPip=");
        printWriter.println(this.allowEnterPip);
        printWriter.print(str);
        printWriter.print("windowType=");
        printWriter.println(this.windowType);
        printWriter.print(str);
        printWriter.print("hasAnimatingParent=");
        printWriter.println(this.hasAnimatingParent);
        printWriter.print(str);
        printWriter.print("backgroundColor=");
        printWriter.println(this.backgroundColor);
        printWriter.print(str);
        printWriter.print("showBackdrop=");
        printWriter.println(this.showBackdrop);
        printWriter.print(str);
        printWriter.print("willShowImeOnTarget=");
        printWriter.println(this.willShowImeOnTarget);
        if (CoreRune.FW_PREDICTIVE_BACK_ANIM) {
            printWriter.print(str);
            printWriter.print("displayId=");
            printWriter.println(this.displayId);
        }
    }

    public void dumpDebug(ProtoOutputStream protoOutputStream, long j) {
        long start = protoOutputStream.start(j);
        protoOutputStream.write(1120986464257L, this.taskId);
        protoOutputStream.write(1120986464258L, this.mode);
        this.leash.dumpDebug(protoOutputStream, 1146756268035L);
        protoOutputStream.write(1133871366148L, this.isTranslucent);
        this.clipRect.dumpDebug(protoOutputStream, 1146756268037L);
        this.contentInsets.dumpDebug(protoOutputStream, 1146756268038L);
        protoOutputStream.write(1120986464263L, this.prefixOrderIndex);
        GraphicsProtos.dumpPointProto(this.position, protoOutputStream, 1146756268040L);
        this.sourceContainerBounds.dumpDebug(protoOutputStream, 1146756268041L);
        this.screenSpaceBounds.dumpDebug(protoOutputStream, 1146756268046L);
        this.localBounds.dumpDebug(protoOutputStream, 1146756268045L);
        this.windowConfiguration.dumpDebug(protoOutputStream, 1146756268042L);
        SurfaceControl surfaceControl = this.startLeash;
        if (surfaceControl != null) {
            surfaceControl.dumpDebug(protoOutputStream, 1146756268043L);
        }
        this.startBounds.dumpDebug(protoOutputStream, 1146756268044L);
        protoOutputStream.end(start);
    }

    private static void printPoint(Point point, PrintWriter printWriter) {
        printWriter.print(NavigationBarInflaterView.SIZE_MOD_START);
        printWriter.print(point.x);
        printWriter.print(",");
        printWriter.print(point.y);
        printWriter.print(NavigationBarInflaterView.SIZE_MOD_END);
    }
}
