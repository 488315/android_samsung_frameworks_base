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

public final class ViewTreeObserver {
    private static boolean sIllegalOnDrawModificationIsFatal;
    private boolean mAlive = true;
    private CopyOnWriteArray<Consumer<List<Rect>>> mGestureExclusionListeners;
    private boolean mInDispatchOnDraw;
    private StringBuilder mLastDispatchOnPreDrawCanceledReason;
    String mLog;
    private CopyOnWriteArray<OnComputeInternalInsetsListener> mOnComputeInternalInsetsListeners;
    private ArrayList<OnDrawListener> mOnDrawListeners;
    private CopyOnWriteArrayList<OnEnterAnimationCompleteListener>
            mOnEnterAnimationCompleteListeners;
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

        public void setTouchableInsets(int val) {
            this.mTouchableInsets = val;
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
            return this.contentInsets.isEmpty()
                    && this.visibleInsets.isEmpty()
                    && this.touchableRegion.isEmpty()
                    && this.mTouchableInsets == 0;
        }

        public int hashCode() {
            int result = this.contentInsets.hashCode();
            return (((((result * 31) + this.visibleInsets.hashCode()) * 31)
                                    + this.touchableRegion.hashCode())
                            * 31)
                    + this.mTouchableInsets;
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            InternalInsetsInfo other = (InternalInsetsInfo) o;
            if (this.mTouchableInsets == other.mTouchableInsets
                    && this.contentInsets.equals(other.contentInsets)
                    && this.visibleInsets.equals(other.visibleInsets)
                    && this.touchableRegion.equals(other.touchableRegion)
                    && (!CoreRune.FW_MINIMIZED_IME_INSET_ANIM
                            || this.minimizedInsets.equals(other.minimizedInsets))) {
                return true;
            }
            return false;
        }

        void set(InternalInsetsInfo other) {
            this.contentInsets.set(other.contentInsets);
            this.visibleInsets.set(other.visibleInsets);
            this.touchableRegion.set(other.touchableRegion);
            this.mTouchableInsets = other.mTouchableInsets;
            if (CoreRune.FW_MINIMIZED_IME_INSET_ANIM) {
                this.minimizedInsets.set(other.minimizedInsets);
            }
        }
    }

    ViewTreeObserver(Context context) {
        sIllegalOnDrawModificationIsFatal = context.getApplicationInfo().targetSdkVersion >= 26;
    }

    void merge(ViewTreeObserver observer) {
        if (observer.mOnWindowAttachListeners != null) {
            if (this.mOnWindowAttachListeners != null) {
                this.mOnWindowAttachListeners.addAll(observer.mOnWindowAttachListeners);
            } else {
                this.mOnWindowAttachListeners = observer.mOnWindowAttachListeners;
            }
        }
        if (observer.mOnWindowFocusListeners != null) {
            if (this.mOnWindowFocusListeners != null) {
                this.mOnWindowFocusListeners.addAll(observer.mOnWindowFocusListeners);
            } else {
                this.mOnWindowFocusListeners = observer.mOnWindowFocusListeners;
            }
        }
        if (observer.mOnWindowVisibilityListeners != null) {
            if (this.mOnWindowVisibilityListeners != null) {
                this.mOnWindowVisibilityListeners.addAll(observer.mOnWindowVisibilityListeners);
            } else {
                this.mOnWindowVisibilityListeners = observer.mOnWindowVisibilityListeners;
            }
        }
        if (observer.mOnGlobalFocusListeners != null) {
            if (this.mOnGlobalFocusListeners != null) {
                this.mOnGlobalFocusListeners.addAll(observer.mOnGlobalFocusListeners);
            } else {
                this.mOnGlobalFocusListeners = observer.mOnGlobalFocusListeners;
            }
        }
        if (observer.mOnGlobalLayoutListeners != null) {
            if (this.mOnGlobalLayoutListeners != null) {
                this.mOnGlobalLayoutListeners.addAll(observer.mOnGlobalLayoutListeners);
            } else {
                this.mOnGlobalLayoutListeners = observer.mOnGlobalLayoutListeners;
            }
        }
        if (observer.mOnPreDrawListeners != null) {
            if (this.mOnPreDrawListeners != null) {
                this.mOnPreDrawListeners.addAll(observer.mOnPreDrawListeners);
            } else {
                this.mOnPreDrawListeners = observer.mOnPreDrawListeners;
            }
        }
        if (observer.mOnDrawListeners != null) {
            if (this.mOnDrawListeners != null) {
                this.mOnDrawListeners.addAll(observer.mOnDrawListeners);
            } else {
                this.mOnDrawListeners = observer.mOnDrawListeners;
            }
        }
        if (observer.mOnFrameCommitListeners != null) {
            if (this.mOnFrameCommitListeners != null) {
                this.mOnFrameCommitListeners.addAll(observer.captureFrameCommitCallbacks());
            } else {
                this.mOnFrameCommitListeners = observer.captureFrameCommitCallbacks();
            }
        }
        if (observer.mOnTouchModeChangeListeners != null) {
            if (this.mOnTouchModeChangeListeners != null) {
                this.mOnTouchModeChangeListeners.addAll(observer.mOnTouchModeChangeListeners);
            } else {
                this.mOnTouchModeChangeListeners = observer.mOnTouchModeChangeListeners;
            }
        }
        if (observer.mOnComputeInternalInsetsListeners != null) {
            if (this.mOnComputeInternalInsetsListeners != null) {
                this.mOnComputeInternalInsetsListeners.addAll(
                        observer.mOnComputeInternalInsetsListeners);
            } else {
                this.mOnComputeInternalInsetsListeners = observer.mOnComputeInternalInsetsListeners;
            }
        }
        if (observer.mOnScrollChangedListeners != null) {
            if (this.mOnScrollChangedListeners != null) {
                this.mOnScrollChangedListeners.addAll(observer.mOnScrollChangedListeners);
            } else {
                this.mOnScrollChangedListeners = observer.mOnScrollChangedListeners;
            }
        }
        if (observer.mOnWindowShownListeners != null) {
            if (this.mOnWindowShownListeners != null) {
                this.mOnWindowShownListeners.addAll(observer.mOnWindowShownListeners);
            } else {
                this.mOnWindowShownListeners = observer.mOnWindowShownListeners;
            }
        }
        if (observer.mGestureExclusionListeners != null) {
            if (this.mGestureExclusionListeners != null) {
                this.mGestureExclusionListeners.addAll(observer.mGestureExclusionListeners);
            } else {
                this.mGestureExclusionListeners = observer.mGestureExclusionListeners;
            }
        }
        if (observer.mOnStylusButtonEventListeners != null) {
            if (this.mOnStylusButtonEventListeners != null) {
                this.mOnStylusButtonEventListeners.addAll(observer.mOnStylusButtonEventListeners);
            } else {
                this.mOnStylusButtonEventListeners = observer.mOnStylusButtonEventListeners;
            }
        }
        observer.kill();
    }

    public void addOnWindowAttachListener(OnWindowAttachListener listener) {
        checkIsAlive();
        if (this.mOnWindowAttachListeners == null) {
            this.mOnWindowAttachListeners = new CopyOnWriteArrayList<>();
        }
        this.mOnWindowAttachListeners.add(listener);
    }

    public void removeOnWindowAttachListener(OnWindowAttachListener victim) {
        checkIsAlive();
        if (this.mOnWindowAttachListeners == null) {
            return;
        }
        this.mOnWindowAttachListeners.remove(victim);
    }

    public void addOnWindowFocusChangeListener(OnWindowFocusChangeListener listener) {
        checkIsAlive();
        if (this.mOnWindowFocusListeners == null) {
            this.mOnWindowFocusListeners = new CopyOnWriteArrayList<>();
        }
        this.mOnWindowFocusListeners.add(listener);
    }

    public void removeOnWindowFocusChangeListener(OnWindowFocusChangeListener victim) {
        checkIsAlive();
        if (this.mOnWindowFocusListeners == null) {
            return;
        }
        this.mOnWindowFocusListeners.remove(victim);
    }

    public void addOnWindowVisibilityChangeListener(OnWindowVisibilityChangeListener listener) {
        checkIsAlive();
        if (this.mOnWindowVisibilityListeners == null) {
            this.mOnWindowVisibilityListeners = new CopyOnWriteArrayList<>();
        }
        this.mOnWindowVisibilityListeners.add(listener);
    }

    public void removeOnWindowVisibilityChangeListener(OnWindowVisibilityChangeListener victim) {
        checkIsAlive();
        if (this.mOnWindowVisibilityListeners == null) {
            return;
        }
        this.mOnWindowVisibilityListeners.remove(victim);
    }

    public void addOnGlobalFocusChangeListener(OnGlobalFocusChangeListener listener) {
        checkIsAlive();
        if (this.mOnGlobalFocusListeners == null) {
            this.mOnGlobalFocusListeners = new CopyOnWriteArrayList<>();
        }
        this.mOnGlobalFocusListeners.add(listener);
    }

    public void removeOnGlobalFocusChangeListener(OnGlobalFocusChangeListener victim) {
        checkIsAlive();
        if (this.mOnGlobalFocusListeners == null) {
            return;
        }
        this.mOnGlobalFocusListeners.remove(victim);
    }

    public void addOnGlobalLayoutListener(OnGlobalLayoutListener listener) {
        checkIsAlive();
        if (this.mOnGlobalLayoutListeners == null) {
            this.mOnGlobalLayoutListeners = new CopyOnWriteArray<>();
        }
        this.mOnGlobalLayoutListeners.add(listener);
    }

    @Deprecated
    public void removeGlobalOnLayoutListener(OnGlobalLayoutListener victim) {
        removeOnGlobalLayoutListener(victim);
    }

    public void removeOnGlobalLayoutListener(OnGlobalLayoutListener victim) {
        checkIsAlive();
        if (this.mOnGlobalLayoutListeners == null) {
            return;
        }
        this.mOnGlobalLayoutListeners.remove(victim);
    }

    public void addOnPreDrawListener(OnPreDrawListener listener) {
        checkIsAlive();
        if (this.mOnPreDrawListeners == null) {
            this.mOnPreDrawListeners = new CopyOnWriteArray<>();
        }
        this.mOnPreDrawListeners.add(listener);
    }

    public void removeOnPreDrawListener(OnPreDrawListener victim) {
        checkIsAlive();
        if (this.mOnPreDrawListeners == null) {
            return;
        }
        this.mOnPreDrawListeners.remove(victim);
    }

    public void addOnWindowShownListener(OnWindowShownListener listener) {
        checkIsAlive();
        if (this.mOnWindowShownListeners == null) {
            this.mOnWindowShownListeners = new CopyOnWriteArray<>();
        }
        this.mOnWindowShownListeners.add(listener);
        if (this.mWindowShown) {
            listener.onWindowShown();
        }
    }

    public void removeOnWindowShownListener(OnWindowShownListener victim) {
        checkIsAlive();
        if (this.mOnWindowShownListeners == null) {
            return;
        }
        this.mOnWindowShownListeners.remove(victim);
    }

    public void addOnDrawListener(OnDrawListener listener) {
        checkIsAlive();
        if (this.mOnDrawListeners == null) {
            this.mOnDrawListeners = new ArrayList<>();
        }
        if (this.mInDispatchOnDraw) {
            IllegalStateException ex =
                    new IllegalStateException("Cannot call addOnDrawListener inside of onDraw");
            if (sIllegalOnDrawModificationIsFatal) {
                throw ex;
            }
            Log.e("ViewTreeObserver", ex.getMessage(), ex);
        }
        this.mOnDrawListeners.add(listener);
    }

    public void removeOnDrawListener(OnDrawListener victim) {
        checkIsAlive();
        if (this.mOnDrawListeners == null) {
            return;
        }
        if (this.mInDispatchOnDraw) {
            IllegalStateException ex =
                    new IllegalStateException("Cannot call removeOnDrawListener inside of onDraw");
            if (sIllegalOnDrawModificationIsFatal) {
                throw ex;
            }
            Log.e("ViewTreeObserver", ex.getMessage(), ex);
        }
        this.mOnDrawListeners.remove(victim);
    }

    public void registerFrameCommitCallback(Runnable callback) {
        checkIsAlive();
        if (this.mOnFrameCommitListeners == null) {
            this.mOnFrameCommitListeners = new ArrayList<>();
        }
        this.mOnFrameCommitListeners.add(callback);
    }

    ArrayList<Runnable> captureFrameCommitCallbacks() {
        ArrayList<Runnable> ret = this.mOnFrameCommitListeners;
        this.mOnFrameCommitListeners = null;
        return ret;
    }

    public boolean unregisterFrameCommitCallback(Runnable callback) {
        checkIsAlive();
        if (this.mOnFrameCommitListeners == null) {
            return false;
        }
        return this.mOnFrameCommitListeners.remove(callback);
    }

    public void addOnScrollChangedListener(OnScrollChangedListener listener) {
        checkIsAlive();
        if (this.mOnScrollChangedListeners == null) {
            this.mOnScrollChangedListeners = new CopyOnWriteArray<>();
        }
        this.mOnScrollChangedListeners.add(listener);
    }

    public void removeOnScrollChangedListener(OnScrollChangedListener victim) {
        checkIsAlive();
        if (this.mOnScrollChangedListeners == null) {
            return;
        }
        this.mOnScrollChangedListeners.remove(victim);
    }

    public void addOnTouchModeChangeListener(OnTouchModeChangeListener listener) {
        checkIsAlive();
        if (this.mOnTouchModeChangeListeners == null) {
            this.mOnTouchModeChangeListeners = new CopyOnWriteArrayList<>();
        }
        this.mOnTouchModeChangeListeners.add(listener);
    }

    public void removeOnTouchModeChangeListener(OnTouchModeChangeListener victim) {
        checkIsAlive();
        if (this.mOnTouchModeChangeListeners == null) {
            return;
        }
        this.mOnTouchModeChangeListeners.remove(victim);
    }

    public void addOnComputeInternalInsetsListener(OnComputeInternalInsetsListener listener) {
        checkIsAlive();
        if (this.mOnComputeInternalInsetsListeners == null) {
            this.mOnComputeInternalInsetsListeners = new CopyOnWriteArray<>();
        }
        this.mOnComputeInternalInsetsListeners.add(listener);
    }

    public void removeOnComputeInternalInsetsListener(OnComputeInternalInsetsListener victim) {
        checkIsAlive();
        if (this.mOnComputeInternalInsetsListeners == null) {
            return;
        }
        this.mOnComputeInternalInsetsListeners.remove(victim);
    }

    public void addOnEnterAnimationCompleteListener(OnEnterAnimationCompleteListener listener) {
        checkIsAlive();
        if (this.mOnEnterAnimationCompleteListeners == null) {
            this.mOnEnterAnimationCompleteListeners = new CopyOnWriteArrayList<>();
        }
        this.mOnEnterAnimationCompleteListeners.add(listener);
    }

    public void removeOnEnterAnimationCompleteListener(OnEnterAnimationCompleteListener listener) {
        checkIsAlive();
        if (this.mOnEnterAnimationCompleteListeners == null) {
            return;
        }
        this.mOnEnterAnimationCompleteListeners.remove(listener);
    }

    public void addOnSystemGestureExclusionRectsChangedListener(Consumer<List<Rect>> listener) {
        checkIsAlive();
        if (this.mGestureExclusionListeners == null) {
            this.mGestureExclusionListeners = new CopyOnWriteArray<>();
        }
        this.mGestureExclusionListeners.add(listener);
    }

    public void removeOnSystemGestureExclusionRectsChangedListener(Consumer<List<Rect>> listener) {
        checkIsAlive();
        if (this.mGestureExclusionListeners == null) {
            return;
        }
        this.mGestureExclusionListeners.remove(listener);
    }

    private void checkIsAlive() {
        if (!this.mAlive) {
            throw new IllegalStateException(
                    "This ViewTreeObserver is not alive, call getViewTreeObserver() again");
        }
    }

    public boolean isAlive() {
        return this.mAlive;
    }

    private void kill() {
        this.mAlive = false;
    }

    final void dispatchOnWindowAttachedChange(boolean attached) {
        CopyOnWriteArrayList<OnWindowAttachListener> listeners = this.mOnWindowAttachListeners;
        if (listeners != null && listeners.size() > 0) {
            Iterator<OnWindowAttachListener> it = listeners.iterator();
            while (it.hasNext()) {
                OnWindowAttachListener listener = it.next();
                if (attached) {
                    listener.onWindowAttached();
                } else {
                    listener.onWindowDetached();
                }
            }
        }
    }

    final void dispatchOnWindowFocusChange(boolean hasFocus) {
        CopyOnWriteArrayList<OnWindowFocusChangeListener> listeners = this.mOnWindowFocusListeners;
        if (listeners != null && listeners.size() > 0) {
            Iterator<OnWindowFocusChangeListener> it = listeners.iterator();
            while (it.hasNext()) {
                OnWindowFocusChangeListener listener = it.next();
                listener.onWindowFocusChanged(hasFocus);
            }
        }
    }

    void dispatchOnWindowVisibilityChange(int visibility) {
        CopyOnWriteArrayList<OnWindowVisibilityChangeListener> listeners =
                this.mOnWindowVisibilityListeners;
        if (listeners != null && listeners.size() > 0) {
            Iterator<OnWindowVisibilityChangeListener> it = listeners.iterator();
            while (it.hasNext()) {
                OnWindowVisibilityChangeListener listener = it.next();
                listener.onWindowVisibilityChanged(visibility);
            }
        }
    }

    final void dispatchOnGlobalFocusChange(View oldFocus, View newFocus) {
        CopyOnWriteArrayList<OnGlobalFocusChangeListener> listeners = this.mOnGlobalFocusListeners;
        if (listeners != null && listeners.size() > 0) {
            Iterator<OnGlobalFocusChangeListener> it = listeners.iterator();
            while (it.hasNext()) {
                OnGlobalFocusChangeListener listener = it.next();
                listener.onGlobalFocusChanged(oldFocus, newFocus);
            }
        }
    }

    public final void dispatchOnGlobalLayout() {
        CopyOnWriteArray<OnGlobalLayoutListener> listeners = this.mOnGlobalLayoutListeners;
        if (listeners != null && listeners.size() > 0) {
            CopyOnWriteArray.Access<OnGlobalLayoutListener> access = listeners.start();
            try {
                int count = access.size();
                for (int i = 0; i < count; i++) {
                    access.get(i).onGlobalLayout();
                }
            } finally {
                listeners.end();
            }
        }
    }

    final boolean hasOnPreDrawListeners() {
        return this.mOnPreDrawListeners != null && this.mOnPreDrawListeners.size() > 0;
    }

    public final boolean dispatchOnPreDraw() {
        this.mLog = "";
        this.mLastDispatchOnPreDrawCanceledReason = null;
        boolean cancelDraw = false;
        CopyOnWriteArray<OnPreDrawListener> listeners = this.mOnPreDrawListeners;
        if (listeners != null && listeners.size() > 0) {
            CopyOnWriteArray.Access<OnPreDrawListener> access = listeners.start();
            try {
                int count = access.size();
                for (int i = 0; i < count; i++) {
                    OnPreDrawListener preDrawListener = access.get(i);
                    boolean listenerCanceledDraw = !preDrawListener.onPreDraw();
                    cancelDraw |= listenerCanceledDraw;
                    if (listenerCanceledDraw) {
                        String className = preDrawListener.getClass().getName();
                        if (this.mLastDispatchOnPreDrawCanceledReason == null) {
                            this.mLastDispatchOnPreDrawCanceledReason =
                                    new StringBuilder(className);
                        } else {
                            this.mLastDispatchOnPreDrawCanceledReason
                                    .append(NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER)
                                    .append(className);
                        }
                        if (CoreRune.IS_DEBUG_LEVEL_MID || CoreRune.IS_DEBUG_LEVEL_HIGH) {
                            this.mLog += preDrawListener.toString() + " ";
                        }
                    }
                }
            } finally {
                listeners.end();
            }
        }
        return cancelDraw;
    }

    final String getLastDispatchOnPreDrawCanceledReason() {
        if (this.mLastDispatchOnPreDrawCanceledReason != null) {
            return this.mLastDispatchOnPreDrawCanceledReason.toString();
        }
        return null;
    }

    public final void dispatchOnWindowShown() {
        this.mWindowShown = true;
        CopyOnWriteArray<OnWindowShownListener> listeners = this.mOnWindowShownListeners;
        if (listeners != null && listeners.size() > 0) {
            CopyOnWriteArray.Access<OnWindowShownListener> access = listeners.start();
            try {
                int count = access.size();
                for (int i = 0; i < count; i++) {
                    access.get(i).onWindowShown();
                }
            } finally {
                listeners.end();
            }
        }
    }

    public final void dispatchOnDraw() {
        if (this.mOnDrawListeners != null) {
            this.mInDispatchOnDraw = true;
            ArrayList<OnDrawListener> listeners = this.mOnDrawListeners;
            int numListeners = listeners.size();
            for (int i = 0; i < numListeners; i++) {
                listeners.get(i).onDraw();
            }
            this.mInDispatchOnDraw = false;
        }
    }

    final void dispatchOnTouchModeChanged(boolean inTouchMode) {
        CopyOnWriteArrayList<OnTouchModeChangeListener> listeners =
                this.mOnTouchModeChangeListeners;
        if (listeners != null && listeners.size() > 0) {
            Iterator<OnTouchModeChangeListener> it = listeners.iterator();
            while (it.hasNext()) {
                OnTouchModeChangeListener listener = it.next();
                listener.onTouchModeChanged(inTouchMode);
            }
        }
    }

    final void dispatchOnScrollChanged() {
        CopyOnWriteArray<OnScrollChangedListener> listeners = this.mOnScrollChangedListeners;
        if (listeners != null && listeners.size() > 0) {
            CopyOnWriteArray.Access<OnScrollChangedListener> access = listeners.start();
            try {
                int count = access.size();
                for (int i = 0; i < count; i++) {
                    access.get(i).onScrollChanged();
                }
            } finally {
                listeners.end();
            }
        }
    }

    final boolean hasComputeInternalInsetsListeners() {
        CopyOnWriteArray<OnComputeInternalInsetsListener> listeners =
                this.mOnComputeInternalInsetsListeners;
        return listeners != null && listeners.size() > 0;
    }

    final void dispatchOnComputeInternalInsets(InternalInsetsInfo inoutInfo) {
        CopyOnWriteArray<OnComputeInternalInsetsListener> listeners =
                this.mOnComputeInternalInsetsListeners;
        if (listeners != null && listeners.size() > 0) {
            CopyOnWriteArray.Access<OnComputeInternalInsetsListener> access = listeners.start();
            try {
                int count = access.size();
                for (int i = 0; i < count; i++) {
                    access.get(i).onComputeInternalInsets(inoutInfo);
                }
            } finally {
                listeners.end();
            }
        }
    }

    public final void dispatchOnEnterAnimationComplete() {
        CopyOnWriteArrayList<OnEnterAnimationCompleteListener> listeners =
                this.mOnEnterAnimationCompleteListeners;
        if (listeners != null && !listeners.isEmpty()) {
            Iterator<OnEnterAnimationCompleteListener> it = listeners.iterator();
            while (it.hasNext()) {
                OnEnterAnimationCompleteListener listener = it.next();
                listener.onEnterAnimationComplete();
            }
        }
    }

    void dispatchOnSystemGestureExclusionRectsChanged(List<Rect> rects) {
        CopyOnWriteArray<Consumer<List<Rect>>> listeners = this.mGestureExclusionListeners;
        if (listeners != null && listeners.size() > 0) {
            CopyOnWriteArray.Access<Consumer<List<Rect>>> access = listeners.start();
            try {
                int count = access.size();
                for (int i = 0; i < count; i++) {
                    access.get(i).accept(rects);
                }
            } finally {
                listeners.end();
            }
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

            Access() {}

            T get(int index) {
                return this.mData.get(index);
            }

            int size() {
                return this.mSize;
            }
        }

        CopyOnWriteArray() {}

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
            if (this.mDataCopy != null) {
                this.mData = this.mDataCopy;
                ((Access) this.mAccess).mData.clear();
                ((Access) this.mAccess).mSize = 0;
            }
            this.mDataCopy = null;
        }

        int size() {
            return getArray().size();
        }

        void add(T item) {
            getArray().add(item);
        }

        void addAll(CopyOnWriteArray<T> array) {
            getArray().addAll(array.mData);
        }

        void remove(T item) {
            getArray().remove(item);
        }

        void clear() {
            getArray().clear();
        }
    }

    public void semAddOnStylusButtonEventListener(SemOnStylusButtonEventListener listener) {
        checkIsAlive();
        if (this.mOnStylusButtonEventListeners == null) {
            this.mOnStylusButtonEventListeners = new ArrayList<>();
        }
        this.mOnStylusButtonEventListeners.add(listener);
    }

    public void semRemoveOnStylusButtonEventListener(SemOnStylusButtonEventListener victim) {
        checkIsAlive();
        if (this.mOnStylusButtonEventListeners == null) {
            return;
        }
        this.mOnStylusButtonEventListeners.remove(victim);
    }

    public final void dispatchOnPenButtonEventListener(MotionEvent event) {
        if (this.mOnStylusButtonEventListeners != null
                && this.mOnStylusButtonEventListeners.size() > 0) {
            ArrayList<SemOnStylusButtonEventListener> listeners =
                    (ArrayList) this.mOnStylusButtonEventListeners.clone();
            int numListeners = listeners.size();
            for (int i = 0; i < numListeners; i++) {
                listeners.get(i).onStylusButtonEvent(event);
            }
        }
    }
}
