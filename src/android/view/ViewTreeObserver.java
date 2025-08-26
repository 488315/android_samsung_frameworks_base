package android.view;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.Region;
import android.util.Log;
import android.util.NtpTrustedTime;
import com.samsung.android.rune.CoreRune;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

/* loaded from: classes4.dex */
public final class ViewTreeObserver {
    private static boolean sIllegalOnDrawModificationIsFatal;
    private boolean mAlive = true;
    private CopyOnWriteArray<Consumer<List<Rect>>> mGestureExclusionListeners;
    private boolean mInDispatchOnDraw;
    private StringBuilder mLastDispatchOnPreDrawCanceledReason;
    String mLog;
    private CopyOnWriteArray<OnComputeInternalInsetsListener> mOnComputeInternalInsetsListeners;
    private ArrayList<OnDrawListener> mOnDrawListeners;
    private CopyOnWriteArrayList<OnEnterAnimationCompleteListener> mOnEnterAnimationCompleteListeners;
    private ArrayList<Runnable> mOnFrameCommitListeners;
    private CopyOnWriteArrayList<OnGlobalFocusChangeListener> mOnGlobalFocusListeners;
    private CopyOnWriteArray<OnGlobalLayoutListener> mOnGlobalLayoutListeners;
    private CopyOnWriteArray<OnPreDrawListener> mOnPreDrawListeners;
    private CopyOnWriteArray<OnScrollChangedListener> mOnScrollChangedListeners;
    private ArrayList<SemOnStylusButtonEventListener> mOnStylusButtonEventListeners;
    private CopyOnWriteArrayList<OnTouchModeChangeListener> mOnTouchModeChangeListeners;
    private CopyOnWriteArrayList<OnWindowAttachListener> mOnWindowAttachListeners;
    private CopyOnWriteArrayList<OnWindowFocusChangeListener> mOnWindowFocusListeners;
    private CopyOnWriteArray<OnWindowShownListener> mOnWindowShownListeners;
    private CopyOnWriteArrayList<OnWindowVisibilityChangeListener> mOnWindowVisibilityListeners;
    private boolean mWindowShown;

    public interface OnComputeInternalInsetsListener {
        void onComputeInternalInsets(InternalInsetsInfo internalInsetsInfo);
    }

    public interface OnDrawListener {
        void onDraw();
    }

    public interface OnEnterAnimationCompleteListener {
        void onEnterAnimationComplete();
    }

    public interface OnGlobalFocusChangeListener {
        void onGlobalFocusChanged(View view, View view2);
    }

    public interface OnGlobalLayoutListener {
        void onGlobalLayout();
    }

    public interface OnPreDrawListener {
        boolean onPreDraw();
    }

    public interface OnScrollChangedListener {
        void onScrollChanged();
    }

    public interface OnTouchModeChangeListener {
        void onTouchModeChanged(boolean z);
    }

    public interface OnWindowAttachListener {
        void onWindowAttached();

        void onWindowDetached();
    }

    public interface OnWindowFocusChangeListener {
        void onWindowFocusChanged(boolean z);
    }

    public interface OnWindowShownListener {
        void onWindowShown();
    }

    public interface OnWindowVisibilityChangeListener {
        void onWindowVisibilityChanged(int i);
    }

    public interface SemOnStylusButtonEventListener {
        void onStylusButtonEvent(MotionEvent motionEvent);
    }

    public static final class InternalInsetsInfo {
        public static final int TOUCHABLE_INSETS_CONTENT = 1;
        public static final int TOUCHABLE_INSETS_FRAME = 0;
        public static final int TOUCHABLE_INSETS_REGION = 3;
        public static final int TOUCHABLE_INSETS_VISIBLE = 2;
        int mTouchableInsets;
        public final Rect contentInsets = new Rect();
        public final Rect visibleInsets = new Rect();
        public final Rect minimizedInsets = new Rect();
        public final Region touchableRegion = new Region();

        public void setTouchableInsets(int i) {
            this.mTouchableInsets = i;
        }

        void reset() {
            this.contentInsets.setEmpty();
            this.visibleInsets.setEmpty();
            this.touchableRegion.setEmpty();
            this.mTouchableInsets = 0;
            if (CoreRune.FW_MINIMIZED_IME_INSET_ANIM) {
                this.minimizedInsets.setEmpty();
            }
        }

        boolean isEmpty() {
            return this.contentInsets.isEmpty() && this.visibleInsets.isEmpty() && this.touchableRegion.isEmpty() && this.mTouchableInsets == 0;
        }

        public int hashCode() {
            return (((((this.contentInsets.hashCode() * 31) + this.visibleInsets.hashCode()) * 31) + this.touchableRegion.hashCode()) * 31) + this.mTouchableInsets;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj != null && getClass() == obj.getClass()) {
                InternalInsetsInfo internalInsetsInfo = (InternalInsetsInfo) obj;
                if (this.mTouchableInsets == internalInsetsInfo.mTouchableInsets && this.contentInsets.equals(internalInsetsInfo.contentInsets) && this.visibleInsets.equals(internalInsetsInfo.visibleInsets) && this.touchableRegion.equals(internalInsetsInfo.touchableRegion) && (!CoreRune.FW_MINIMIZED_IME_INSET_ANIM || this.minimizedInsets.equals(internalInsetsInfo.minimizedInsets))) {
                    return true;
                }
            }
            return false;
        }

        void set(InternalInsetsInfo internalInsetsInfo) {
            this.contentInsets.set(internalInsetsInfo.contentInsets);
            this.visibleInsets.set(internalInsetsInfo.visibleInsets);
            this.touchableRegion.set(internalInsetsInfo.touchableRegion);
            this.mTouchableInsets = internalInsetsInfo.mTouchableInsets;
            if (CoreRune.FW_MINIMIZED_IME_INSET_ANIM) {
                this.minimizedInsets.set(internalInsetsInfo.minimizedInsets);
            }
        }
    }

    ViewTreeObserver(Context context) {
        sIllegalOnDrawModificationIsFatal = context.getApplicationInfo().targetSdkVersion >= 26;
    }

    void merge(ViewTreeObserver viewTreeObserver) {
        CopyOnWriteArrayList<OnWindowAttachListener> copyOnWriteArrayList = viewTreeObserver.mOnWindowAttachListeners;
        if (copyOnWriteArrayList != null) {
            CopyOnWriteArrayList<OnWindowAttachListener> copyOnWriteArrayList2 = this.mOnWindowAttachListeners;
            if (copyOnWriteArrayList2 != null) {
                copyOnWriteArrayList2.addAll(copyOnWriteArrayList);
            } else {
                this.mOnWindowAttachListeners = copyOnWriteArrayList;
            }
        }
        CopyOnWriteArrayList<OnWindowFocusChangeListener> copyOnWriteArrayList3 = viewTreeObserver.mOnWindowFocusListeners;
        if (copyOnWriteArrayList3 != null) {
            CopyOnWriteArrayList<OnWindowFocusChangeListener> copyOnWriteArrayList4 = this.mOnWindowFocusListeners;
            if (copyOnWriteArrayList4 != null) {
                copyOnWriteArrayList4.addAll(copyOnWriteArrayList3);
            } else {
                this.mOnWindowFocusListeners = copyOnWriteArrayList3;
            }
        }
        CopyOnWriteArrayList<OnWindowVisibilityChangeListener> copyOnWriteArrayList5 = viewTreeObserver.mOnWindowVisibilityListeners;
        if (copyOnWriteArrayList5 != null) {
            CopyOnWriteArrayList<OnWindowVisibilityChangeListener> copyOnWriteArrayList6 = this.mOnWindowVisibilityListeners;
            if (copyOnWriteArrayList6 != null) {
                copyOnWriteArrayList6.addAll(copyOnWriteArrayList5);
            } else {
                this.mOnWindowVisibilityListeners = copyOnWriteArrayList5;
            }
        }
        CopyOnWriteArrayList<OnGlobalFocusChangeListener> copyOnWriteArrayList7 = viewTreeObserver.mOnGlobalFocusListeners;
        if (copyOnWriteArrayList7 != null) {
            CopyOnWriteArrayList<OnGlobalFocusChangeListener> copyOnWriteArrayList8 = this.mOnGlobalFocusListeners;
            if (copyOnWriteArrayList8 != null) {
                copyOnWriteArrayList8.addAll(copyOnWriteArrayList7);
            } else {
                this.mOnGlobalFocusListeners = copyOnWriteArrayList7;
            }
        }
        CopyOnWriteArray<OnGlobalLayoutListener> copyOnWriteArray = viewTreeObserver.mOnGlobalLayoutListeners;
        if (copyOnWriteArray != null) {
            CopyOnWriteArray<OnGlobalLayoutListener> copyOnWriteArray2 = this.mOnGlobalLayoutListeners;
            if (copyOnWriteArray2 != null) {
                copyOnWriteArray2.addAll(copyOnWriteArray);
            } else {
                this.mOnGlobalLayoutListeners = copyOnWriteArray;
            }
        }
        CopyOnWriteArray<OnPreDrawListener> copyOnWriteArray3 = viewTreeObserver.mOnPreDrawListeners;
        if (copyOnWriteArray3 != null) {
            CopyOnWriteArray<OnPreDrawListener> copyOnWriteArray4 = this.mOnPreDrawListeners;
            if (copyOnWriteArray4 != null) {
                copyOnWriteArray4.addAll(copyOnWriteArray3);
            } else {
                this.mOnPreDrawListeners = copyOnWriteArray3;
            }
        }
        ArrayList<OnDrawListener> arrayList = viewTreeObserver.mOnDrawListeners;
        if (arrayList != null) {
            ArrayList<OnDrawListener> arrayList2 = this.mOnDrawListeners;
            if (arrayList2 != null) {
                arrayList2.addAll(arrayList);
            } else {
                this.mOnDrawListeners = arrayList;
            }
        }
        if (viewTreeObserver.mOnFrameCommitListeners != null) {
            ArrayList<Runnable> arrayList3 = this.mOnFrameCommitListeners;
            if (arrayList3 != null) {
                arrayList3.addAll(viewTreeObserver.captureFrameCommitCallbacks());
            } else {
                this.mOnFrameCommitListeners = viewTreeObserver.captureFrameCommitCallbacks();
            }
        }
        CopyOnWriteArrayList<OnTouchModeChangeListener> copyOnWriteArrayList9 = viewTreeObserver.mOnTouchModeChangeListeners;
        if (copyOnWriteArrayList9 != null) {
            CopyOnWriteArrayList<OnTouchModeChangeListener> copyOnWriteArrayList10 = this.mOnTouchModeChangeListeners;
            if (copyOnWriteArrayList10 != null) {
                copyOnWriteArrayList10.addAll(copyOnWriteArrayList9);
            } else {
                this.mOnTouchModeChangeListeners = copyOnWriteArrayList9;
            }
        }
        CopyOnWriteArray<OnComputeInternalInsetsListener> copyOnWriteArray5 = viewTreeObserver.mOnComputeInternalInsetsListeners;
        if (copyOnWriteArray5 != null) {
            CopyOnWriteArray<OnComputeInternalInsetsListener> copyOnWriteArray6 = this.mOnComputeInternalInsetsListeners;
            if (copyOnWriteArray6 != null) {
                copyOnWriteArray6.addAll(copyOnWriteArray5);
            } else {
                this.mOnComputeInternalInsetsListeners = copyOnWriteArray5;
            }
        }
        CopyOnWriteArray<OnScrollChangedListener> copyOnWriteArray7 = viewTreeObserver.mOnScrollChangedListeners;
        if (copyOnWriteArray7 != null) {
            CopyOnWriteArray<OnScrollChangedListener> copyOnWriteArray8 = this.mOnScrollChangedListeners;
            if (copyOnWriteArray8 != null) {
                copyOnWriteArray8.addAll(copyOnWriteArray7);
            } else {
                this.mOnScrollChangedListeners = copyOnWriteArray7;
            }
        }
        CopyOnWriteArray<OnWindowShownListener> copyOnWriteArray9 = viewTreeObserver.mOnWindowShownListeners;
        if (copyOnWriteArray9 != null) {
            CopyOnWriteArray<OnWindowShownListener> copyOnWriteArray10 = this.mOnWindowShownListeners;
            if (copyOnWriteArray10 != null) {
                copyOnWriteArray10.addAll(copyOnWriteArray9);
            } else {
                this.mOnWindowShownListeners = copyOnWriteArray9;
            }
        }
        CopyOnWriteArray<Consumer<List<Rect>>> copyOnWriteArray11 = viewTreeObserver.mGestureExclusionListeners;
        if (copyOnWriteArray11 != null) {
            CopyOnWriteArray<Consumer<List<Rect>>> copyOnWriteArray12 = this.mGestureExclusionListeners;
            if (copyOnWriteArray12 != null) {
                copyOnWriteArray12.addAll(copyOnWriteArray11);
            } else {
                this.mGestureExclusionListeners = copyOnWriteArray11;
            }
        }
        ArrayList<SemOnStylusButtonEventListener> arrayList4 = viewTreeObserver.mOnStylusButtonEventListeners;
        if (arrayList4 != null) {
            ArrayList<SemOnStylusButtonEventListener> arrayList5 = this.mOnStylusButtonEventListeners;
            if (arrayList5 != null) {
                arrayList5.addAll(arrayList4);
            } else {
                this.mOnStylusButtonEventListeners = arrayList4;
            }
        }
        viewTreeObserver.kill();
    }

    public void addOnWindowAttachListener(OnWindowAttachListener onWindowAttachListener) {
        checkIsAlive();
        if (this.mOnWindowAttachListeners == null) {
            this.mOnWindowAttachListeners = new CopyOnWriteArrayList<>();
        }
        this.mOnWindowAttachListeners.add(onWindowAttachListener);
    }

    public void removeOnWindowAttachListener(OnWindowAttachListener onWindowAttachListener) {
        checkIsAlive();
        CopyOnWriteArrayList<OnWindowAttachListener> copyOnWriteArrayList = this.mOnWindowAttachListeners;
        if (copyOnWriteArrayList == null) {
            return;
        }
        copyOnWriteArrayList.remove(onWindowAttachListener);
    }

    public void addOnWindowFocusChangeListener(OnWindowFocusChangeListener onWindowFocusChangeListener) {
        checkIsAlive();
        if (this.mOnWindowFocusListeners == null) {
            this.mOnWindowFocusListeners = new CopyOnWriteArrayList<>();
        }
        this.mOnWindowFocusListeners.add(onWindowFocusChangeListener);
    }

    public void removeOnWindowFocusChangeListener(OnWindowFocusChangeListener onWindowFocusChangeListener) {
        checkIsAlive();
        CopyOnWriteArrayList<OnWindowFocusChangeListener> copyOnWriteArrayList = this.mOnWindowFocusListeners;
        if (copyOnWriteArrayList == null) {
            return;
        }
        copyOnWriteArrayList.remove(onWindowFocusChangeListener);
    }

    public void addOnWindowVisibilityChangeListener(OnWindowVisibilityChangeListener onWindowVisibilityChangeListener) {
        checkIsAlive();
        if (this.mOnWindowVisibilityListeners == null) {
            this.mOnWindowVisibilityListeners = new CopyOnWriteArrayList<>();
        }
        this.mOnWindowVisibilityListeners.add(onWindowVisibilityChangeListener);
    }

    public void removeOnWindowVisibilityChangeListener(OnWindowVisibilityChangeListener onWindowVisibilityChangeListener) {
        checkIsAlive();
        CopyOnWriteArrayList<OnWindowVisibilityChangeListener> copyOnWriteArrayList = this.mOnWindowVisibilityListeners;
        if (copyOnWriteArrayList == null) {
            return;
        }
        copyOnWriteArrayList.remove(onWindowVisibilityChangeListener);
    }

    public void addOnGlobalFocusChangeListener(OnGlobalFocusChangeListener onGlobalFocusChangeListener) {
        checkIsAlive();
        if (this.mOnGlobalFocusListeners == null) {
            this.mOnGlobalFocusListeners = new CopyOnWriteArrayList<>();
        }
        this.mOnGlobalFocusListeners.add(onGlobalFocusChangeListener);
    }

    public void removeOnGlobalFocusChangeListener(OnGlobalFocusChangeListener onGlobalFocusChangeListener) {
        checkIsAlive();
        CopyOnWriteArrayList<OnGlobalFocusChangeListener> copyOnWriteArrayList = this.mOnGlobalFocusListeners;
        if (copyOnWriteArrayList == null) {
            return;
        }
        copyOnWriteArrayList.remove(onGlobalFocusChangeListener);
    }

    public void addOnGlobalLayoutListener(OnGlobalLayoutListener onGlobalLayoutListener) {
        checkIsAlive();
        if (this.mOnGlobalLayoutListeners == null) {
            this.mOnGlobalLayoutListeners = new CopyOnWriteArray<>();
        }
        this.mOnGlobalLayoutListeners.add(onGlobalLayoutListener);
    }

    @Deprecated
    public void removeGlobalOnLayoutListener(OnGlobalLayoutListener onGlobalLayoutListener) {
        removeOnGlobalLayoutListener(onGlobalLayoutListener);
    }

    public void removeOnGlobalLayoutListener(OnGlobalLayoutListener onGlobalLayoutListener) {
        checkIsAlive();
        CopyOnWriteArray<OnGlobalLayoutListener> copyOnWriteArray = this.mOnGlobalLayoutListeners;
        if (copyOnWriteArray == null) {
            return;
        }
        copyOnWriteArray.remove(onGlobalLayoutListener);
    }

    public void addOnPreDrawListener(OnPreDrawListener onPreDrawListener) {
        checkIsAlive();
        if (this.mOnPreDrawListeners == null) {
            this.mOnPreDrawListeners = new CopyOnWriteArray<>();
        }
        this.mOnPreDrawListeners.add(onPreDrawListener);
    }

    public void removeOnPreDrawListener(OnPreDrawListener onPreDrawListener) {
        checkIsAlive();
        CopyOnWriteArray<OnPreDrawListener> copyOnWriteArray = this.mOnPreDrawListeners;
        if (copyOnWriteArray == null) {
            return;
        }
        copyOnWriteArray.remove(onPreDrawListener);
    }

    public void addOnWindowShownListener(OnWindowShownListener onWindowShownListener) {
        checkIsAlive();
        if (this.mOnWindowShownListeners == null) {
            this.mOnWindowShownListeners = new CopyOnWriteArray<>();
        }
        this.mOnWindowShownListeners.add(onWindowShownListener);
        if (this.mWindowShown) {
            onWindowShownListener.onWindowShown();
        }
    }

    public void removeOnWindowShownListener(OnWindowShownListener onWindowShownListener) {
        checkIsAlive();
        CopyOnWriteArray<OnWindowShownListener> copyOnWriteArray = this.mOnWindowShownListeners;
        if (copyOnWriteArray == null) {
            return;
        }
        copyOnWriteArray.remove(onWindowShownListener);
    }

    public void addOnDrawListener(OnDrawListener onDrawListener) {
        checkIsAlive();
        if (this.mOnDrawListeners == null) {
            this.mOnDrawListeners = new ArrayList<>();
        }
        if (this.mInDispatchOnDraw) {
            IllegalStateException illegalStateException = new IllegalStateException("Cannot call addOnDrawListener inside of onDraw");
            if (sIllegalOnDrawModificationIsFatal) {
                throw illegalStateException;
            }
            Log.e("ViewTreeObserver", illegalStateException.getMessage(), illegalStateException);
        }
        this.mOnDrawListeners.add(onDrawListener);
    }

    public void removeOnDrawListener(OnDrawListener onDrawListener) {
        checkIsAlive();
        if (this.mOnDrawListeners == null) {
            return;
        }
        if (this.mInDispatchOnDraw) {
            IllegalStateException illegalStateException = new IllegalStateException("Cannot call removeOnDrawListener inside of onDraw");
            if (sIllegalOnDrawModificationIsFatal) {
                throw illegalStateException;
            }
            Log.e("ViewTreeObserver", illegalStateException.getMessage(), illegalStateException);
        }
        this.mOnDrawListeners.remove(onDrawListener);
    }

    public void registerFrameCommitCallback(Runnable runnable) {
        checkIsAlive();
        if (this.mOnFrameCommitListeners == null) {
            this.mOnFrameCommitListeners = new ArrayList<>();
        }
        this.mOnFrameCommitListeners.add(runnable);
    }

    ArrayList<Runnable> captureFrameCommitCallbacks() {
        ArrayList<Runnable> arrayList = this.mOnFrameCommitListeners;
        this.mOnFrameCommitListeners = null;
        return arrayList;
    }

    public boolean unregisterFrameCommitCallback(Runnable runnable) {
        checkIsAlive();
        ArrayList<Runnable> arrayList = this.mOnFrameCommitListeners;
        if (arrayList == null) {
            return false;
        }
        return arrayList.remove(runnable);
    }

    public void addOnScrollChangedListener(OnScrollChangedListener onScrollChangedListener) {
        checkIsAlive();
        if (this.mOnScrollChangedListeners == null) {
            this.mOnScrollChangedListeners = new CopyOnWriteArray<>();
        }
        this.mOnScrollChangedListeners.add(onScrollChangedListener);
    }

    public void removeOnScrollChangedListener(OnScrollChangedListener onScrollChangedListener) {
        checkIsAlive();
        CopyOnWriteArray<OnScrollChangedListener> copyOnWriteArray = this.mOnScrollChangedListeners;
        if (copyOnWriteArray == null) {
            return;
        }
        copyOnWriteArray.remove(onScrollChangedListener);
    }

    public void addOnTouchModeChangeListener(OnTouchModeChangeListener onTouchModeChangeListener) {
        checkIsAlive();
        if (this.mOnTouchModeChangeListeners == null) {
            this.mOnTouchModeChangeListeners = new CopyOnWriteArrayList<>();
        }
        this.mOnTouchModeChangeListeners.add(onTouchModeChangeListener);
    }

    public void removeOnTouchModeChangeListener(OnTouchModeChangeListener onTouchModeChangeListener) {
        checkIsAlive();
        CopyOnWriteArrayList<OnTouchModeChangeListener> copyOnWriteArrayList = this.mOnTouchModeChangeListeners;
        if (copyOnWriteArrayList == null) {
            return;
        }
        copyOnWriteArrayList.remove(onTouchModeChangeListener);
    }

    public void addOnComputeInternalInsetsListener(OnComputeInternalInsetsListener onComputeInternalInsetsListener) {
        checkIsAlive();
        if (this.mOnComputeInternalInsetsListeners == null) {
            this.mOnComputeInternalInsetsListeners = new CopyOnWriteArray<>();
        }
        this.mOnComputeInternalInsetsListeners.add(onComputeInternalInsetsListener);
    }

    public void removeOnComputeInternalInsetsListener(OnComputeInternalInsetsListener onComputeInternalInsetsListener) {
        checkIsAlive();
        CopyOnWriteArray<OnComputeInternalInsetsListener> copyOnWriteArray = this.mOnComputeInternalInsetsListeners;
        if (copyOnWriteArray == null) {
            return;
        }
        copyOnWriteArray.remove(onComputeInternalInsetsListener);
    }

    public void addOnEnterAnimationCompleteListener(OnEnterAnimationCompleteListener onEnterAnimationCompleteListener) {
        checkIsAlive();
        if (this.mOnEnterAnimationCompleteListeners == null) {
            this.mOnEnterAnimationCompleteListeners = new CopyOnWriteArrayList<>();
        }
        this.mOnEnterAnimationCompleteListeners.add(onEnterAnimationCompleteListener);
    }

    public void removeOnEnterAnimationCompleteListener(OnEnterAnimationCompleteListener onEnterAnimationCompleteListener) {
        checkIsAlive();
        CopyOnWriteArrayList<OnEnterAnimationCompleteListener> copyOnWriteArrayList = this.mOnEnterAnimationCompleteListeners;
        if (copyOnWriteArrayList == null) {
            return;
        }
        copyOnWriteArrayList.remove(onEnterAnimationCompleteListener);
    }

    public void addOnSystemGestureExclusionRectsChangedListener(Consumer<List<Rect>> consumer) {
        checkIsAlive();
        if (this.mGestureExclusionListeners == null) {
            this.mGestureExclusionListeners = new CopyOnWriteArray<>();
        }
        this.mGestureExclusionListeners.add(consumer);
    }

    public void removeOnSystemGestureExclusionRectsChangedListener(Consumer<List<Rect>> consumer) {
        checkIsAlive();
        CopyOnWriteArray<Consumer<List<Rect>>> copyOnWriteArray = this.mGestureExclusionListeners;
        if (copyOnWriteArray == null) {
            return;
        }
        copyOnWriteArray.remove(consumer);
    }

    private void checkIsAlive() {
        if (!this.mAlive) {
            throw new IllegalStateException("This ViewTreeObserver is not alive, call getViewTreeObserver() again");
        }
    }

    public boolean isAlive() {
        return this.mAlive;
    }

    private void kill() {
        this.mAlive = false;
    }

    final void dispatchOnWindowAttachedChange(boolean z) {
        CopyOnWriteArrayList<OnWindowAttachListener> copyOnWriteArrayList = this.mOnWindowAttachListeners;
        if (copyOnWriteArrayList == null || copyOnWriteArrayList.size() <= 0) {
            return;
        }
        Iterator<OnWindowAttachListener> it = copyOnWriteArrayList.iterator();
        while (it.hasNext()) {
            OnWindowAttachListener next = it.next();
            if (z) {
                next.onWindowAttached();
            } else {
                next.onWindowDetached();
            }
        }
    }

    final void dispatchOnWindowFocusChange(boolean z) {
        CopyOnWriteArrayList<OnWindowFocusChangeListener> copyOnWriteArrayList = this.mOnWindowFocusListeners;
        if (copyOnWriteArrayList == null || copyOnWriteArrayList.size() <= 0) {
            return;
        }
        Iterator<OnWindowFocusChangeListener> it = copyOnWriteArrayList.iterator();
        while (it.hasNext()) {
            it.next().onWindowFocusChanged(z);
        }
    }

    void dispatchOnWindowVisibilityChange(int i) {
        CopyOnWriteArrayList<OnWindowVisibilityChangeListener> copyOnWriteArrayList = this.mOnWindowVisibilityListeners;
        if (copyOnWriteArrayList == null || copyOnWriteArrayList.size() <= 0) {
            return;
        }
        Iterator<OnWindowVisibilityChangeListener> it = copyOnWriteArrayList.iterator();
        while (it.hasNext()) {
            it.next().onWindowVisibilityChanged(i);
        }
    }

    final void dispatchOnGlobalFocusChange(View view, View view2) {
        CopyOnWriteArrayList<OnGlobalFocusChangeListener> copyOnWriteArrayList = this.mOnGlobalFocusListeners;
        if (copyOnWriteArrayList == null || copyOnWriteArrayList.size() <= 0) {
            return;
        }
        Iterator<OnGlobalFocusChangeListener> it = copyOnWriteArrayList.iterator();
        while (it.hasNext()) {
            it.next().onGlobalFocusChanged(view, view2);
        }
    }

    public final void dispatchOnGlobalLayout() {
        CopyOnWriteArray<OnGlobalLayoutListener> copyOnWriteArray = this.mOnGlobalLayoutListeners;
        if (copyOnWriteArray == null || copyOnWriteArray.size() <= 0) {
            return;
        }
        CopyOnWriteArray.Access<OnGlobalLayoutListener> accessStart = copyOnWriteArray.start();
        try {
            int size = accessStart.size();
            for (int i = 0; i < size; i++) {
                accessStart.get(i).onGlobalLayout();
            }
        } finally {
            copyOnWriteArray.end();
        }
    }

    final boolean hasOnPreDrawListeners() {
        CopyOnWriteArray<OnPreDrawListener> copyOnWriteArray = this.mOnPreDrawListeners;
        return copyOnWriteArray != null && copyOnWriteArray.size() > 0;
    }

    public final boolean dispatchOnPreDraw() {
        this.mLog = "";
        this.mLastDispatchOnPreDrawCanceledReason = null;
        CopyOnWriteArray<OnPreDrawListener> copyOnWriteArray = this.mOnPreDrawListeners;
        if (copyOnWriteArray == null || copyOnWriteArray.size() <= 0) {
            return false;
        }
        CopyOnWriteArray.Access<OnPreDrawListener> accessStart = copyOnWriteArray.start();
        try {
            int size = accessStart.size();
            boolean z = false;
            for (int i = 0; i < size; i++) {
                OnPreDrawListener onPreDrawListener = accessStart.get(i);
                boolean zOnPreDraw = onPreDrawListener.onPreDraw();
                z |= !zOnPreDraw;
                if (!zOnPreDraw) {
                    String name = onPreDrawListener.getClass().getName();
                    StringBuilder sb = this.mLastDispatchOnPreDrawCanceledReason;
                    if (sb == null) {
                        this.mLastDispatchOnPreDrawCanceledReason = new StringBuilder(name);
                    } else {
                        sb.append(NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER);
                        sb.append(name);
                    }
                    if (CoreRune.IS_DEBUG_LEVEL_MID || CoreRune.IS_DEBUG_LEVEL_HIGH) {
                        this.mLog += onPreDrawListener.toString() + " ";
                    }
                }
            }
            return z;
        } finally {
            copyOnWriteArray.end();
        }
    }

    final String getLastDispatchOnPreDrawCanceledReason() {
        StringBuilder sb = this.mLastDispatchOnPreDrawCanceledReason;
        if (sb != null) {
            return sb.toString();
        }
        return null;
    }

    public final void dispatchOnWindowShown() {
        this.mWindowShown = true;
        CopyOnWriteArray<OnWindowShownListener> copyOnWriteArray = this.mOnWindowShownListeners;
        if (copyOnWriteArray == null || copyOnWriteArray.size() <= 0) {
            return;
        }
        CopyOnWriteArray.Access<OnWindowShownListener> accessStart = copyOnWriteArray.start();
        try {
            int size = accessStart.size();
            for (int i = 0; i < size; i++) {
                accessStart.get(i).onWindowShown();
            }
        } finally {
            copyOnWriteArray.end();
        }
    }

    public final void dispatchOnDraw() {
        ArrayList<OnDrawListener> arrayList = this.mOnDrawListeners;
        if (arrayList != null) {
            this.mInDispatchOnDraw = true;
            int size = arrayList.size();
            for (int i = 0; i < size; i++) {
                arrayList.get(i).onDraw();
            }
            this.mInDispatchOnDraw = false;
        }
    }

    final void dispatchOnTouchModeChanged(boolean z) {
        CopyOnWriteArrayList<OnTouchModeChangeListener> copyOnWriteArrayList = this.mOnTouchModeChangeListeners;
        if (copyOnWriteArrayList == null || copyOnWriteArrayList.size() <= 0) {
            return;
        }
        Iterator<OnTouchModeChangeListener> it = copyOnWriteArrayList.iterator();
        while (it.hasNext()) {
            it.next().onTouchModeChanged(z);
        }
    }

    public final void dispatchOnScrollChanged() {
        CopyOnWriteArray<OnScrollChangedListener> copyOnWriteArray = this.mOnScrollChangedListeners;
        if (copyOnWriteArray == null || copyOnWriteArray.size() <= 0) {
            return;
        }
        CopyOnWriteArray.Access<OnScrollChangedListener> accessStart = copyOnWriteArray.start();
        try {
            int size = accessStart.size();
            for (int i = 0; i < size; i++) {
                accessStart.get(i).onScrollChanged();
            }
        } finally {
            copyOnWriteArray.end();
        }
    }

    final boolean hasComputeInternalInsetsListeners() {
        CopyOnWriteArray<OnComputeInternalInsetsListener> copyOnWriteArray = this.mOnComputeInternalInsetsListeners;
        return copyOnWriteArray != null && copyOnWriteArray.size() > 0;
    }

    final void dispatchOnComputeInternalInsets(InternalInsetsInfo internalInsetsInfo) {
        CopyOnWriteArray<OnComputeInternalInsetsListener> copyOnWriteArray = this.mOnComputeInternalInsetsListeners;
        if (copyOnWriteArray == null || copyOnWriteArray.size() <= 0) {
            return;
        }
        CopyOnWriteArray.Access<OnComputeInternalInsetsListener> accessStart = copyOnWriteArray.start();
        try {
            int size = accessStart.size();
            for (int i = 0; i < size; i++) {
                accessStart.get(i).onComputeInternalInsets(internalInsetsInfo);
            }
        } finally {
            copyOnWriteArray.end();
        }
    }

    public final void dispatchOnEnterAnimationComplete() {
        CopyOnWriteArrayList<OnEnterAnimationCompleteListener> copyOnWriteArrayList = this.mOnEnterAnimationCompleteListeners;
        if (copyOnWriteArrayList == null || copyOnWriteArrayList.isEmpty()) {
            return;
        }
        Iterator<OnEnterAnimationCompleteListener> it = copyOnWriteArrayList.iterator();
        while (it.hasNext()) {
            it.next().onEnterAnimationComplete();
        }
    }

    void dispatchOnSystemGestureExclusionRectsChanged(List<Rect> list) {
        CopyOnWriteArray<Consumer<List<Rect>>> copyOnWriteArray = this.mGestureExclusionListeners;
        if (copyOnWriteArray == null || copyOnWriteArray.size() <= 0) {
            return;
        }
        CopyOnWriteArray.Access<Consumer<List<Rect>>> accessStart = copyOnWriteArray.start();
        try {
            int size = accessStart.size();
            for (int i = 0; i < size; i++) {
                accessStart.get(i).accept(list);
            }
        } finally {
            copyOnWriteArray.end();
        }
    }

    static class CopyOnWriteArray<T> {
        private ArrayList<T> mDataCopy;
        private boolean mStart;
        private ArrayList<T> mData = new ArrayList<>();
        private final Access<T> mAccess = new Access<>();

        static class Access<T> {
            private ArrayList<T> mData;
            private int mSize;

            Access() {
            }

            T get(int i) {
                return this.mData.get(i);
            }

            int size() {
                return this.mSize;
            }
        }

        CopyOnWriteArray() {
        }

        private ArrayList<T> getArray() {
            if (this.mStart) {
                if (this.mDataCopy == null) {
                    this.mDataCopy = new ArrayList<>(this.mData);
                }
                return this.mDataCopy;
            }
            return this.mData;
        }

        Access<T> start() {
            if (this.mStart) {
                throw new IllegalStateException("Iteration already started");
            }
            this.mStart = true;
            this.mDataCopy = null;
            ((Access) this.mAccess).mData = this.mData;
            ((Access) this.mAccess).mSize = this.mData.size();
            return this.mAccess;
        }

        void end() {
            if (!this.mStart) {
                throw new IllegalStateException("Iteration not started");
            }
            this.mStart = false;
            ArrayList<T> arrayList = this.mDataCopy;
            if (arrayList != null) {
                this.mData = arrayList;
                ((Access) this.mAccess).mData.clear();
                ((Access) this.mAccess).mSize = 0;
            }
            this.mDataCopy = null;
        }

        int size() {
            return getArray().size();
        }

        void add(T t) {
            getArray().add(t);
        }

        void addAll(CopyOnWriteArray<T> copyOnWriteArray) {
            getArray().addAll(copyOnWriteArray.mData);
        }

        void remove(T t) {
            getArray().remove(t);
        }

        void clear() {
            getArray().clear();
        }
    }

    public void semAddOnStylusButtonEventListener(SemOnStylusButtonEventListener semOnStylusButtonEventListener) {
        checkIsAlive();
        if (this.mOnStylusButtonEventListeners == null) {
            this.mOnStylusButtonEventListeners = new ArrayList<>();
        }
        this.mOnStylusButtonEventListeners.add(semOnStylusButtonEventListener);
    }

    public void semRemoveOnStylusButtonEventListener(SemOnStylusButtonEventListener semOnStylusButtonEventListener) {
        checkIsAlive();
        ArrayList<SemOnStylusButtonEventListener> arrayList = this.mOnStylusButtonEventListeners;
        if (arrayList == null) {
            return;
        }
        arrayList.remove(semOnStylusButtonEventListener);
    }

    public final void dispatchOnPenButtonEventListener(MotionEvent motionEvent) {
        ArrayList<SemOnStylusButtonEventListener> arrayList = this.mOnStylusButtonEventListeners;
        if (arrayList == null || arrayList.size() <= 0) {
            return;
        }
        ArrayList arrayList2 = (ArrayList) this.mOnStylusButtonEventListeners.clone();
        int size = arrayList2.size();
        for (int i = 0; i < size; i++) {
            ((SemOnStylusButtonEventListener) arrayList2.get(i)).onStylusButtonEvent(motionEvent);
        }
    }
}
