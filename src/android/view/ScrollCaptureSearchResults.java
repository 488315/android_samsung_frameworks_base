package android.view;

import android.graphics.Point;
import android.graphics.Rect;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.CancellationSignal;
import android.util.IndentingPrintWriter;
import android.view.ScrollCaptureSearchResults;
import android.view.flags.Flags;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/* loaded from: classes4.dex */
public final class ScrollCaptureSearchResults {
    private static final int AFTER = 1;
    private static final int BEFORE = -1;
    private static final int EQUAL = 0;
    static final Comparator<ScrollCaptureTarget> PRIORITY_ORDER = new Comparator() { // from class: android.view.ScrollCaptureSearchResults$$ExternalSyntheticLambda0
        @Override // java.util.Comparator
        public final int compare(Object obj, Object obj2) {
            return ScrollCaptureSearchResults.lambda$static$3((ScrollCaptureTarget) obj, (ScrollCaptureTarget) obj2);
        }
    };
    private int mCompleted;
    private final Executor mExecutor;
    private Runnable mOnCompleteListener;
    private boolean mComplete = true;
    private final List<ScrollCaptureTarget> mTargets = new ArrayList();
    private final CancellationSignal mCancel = new CancellationSignal();

    public ScrollCaptureSearchResults(Executor executor) {
        this.mExecutor = executor;
    }

    public void addTarget(ScrollCaptureTarget scrollCaptureTarget) {
        Objects.requireNonNull(scrollCaptureTarget);
        this.mTargets.add(scrollCaptureTarget);
        this.mComplete = false;
        final ScrollCaptureCallback callback = scrollCaptureTarget.getCallback();
        final SearchRequest searchRequest = new SearchRequest(scrollCaptureTarget);
        this.mExecutor.execute(new Runnable() { // from class: android.view.ScrollCaptureSearchResults$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                ScrollCaptureSearchResults.this.lambda$addTarget$0(callback, searchRequest);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addTarget$0(ScrollCaptureCallback scrollCaptureCallback, Consumer consumer) {
        scrollCaptureCallback.onScrollCaptureSearch(this.mCancel, consumer);
    }

    public boolean isComplete() {
        return this.mComplete;
    }

    public void setOnCompleteListener(Runnable runnable) {
        if (this.mComplete) {
            runnable.run();
        } else {
            this.mOnCompleteListener = runnable;
        }
    }

    public boolean isEmpty() {
        return this.mTargets.isEmpty();
    }

    public void finish() {
        if (this.mComplete) {
            return;
        }
        this.mCancel.cancel();
        signalComplete();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void signalComplete() {
        this.mComplete = true;
        if (!Flags.scrollCaptureTargetZOrderFix()) {
            this.mTargets.sort(PRIORITY_ORDER);
        }
        Runnable runnable = this.mOnCompleteListener;
        if (runnable != null) {
            runnable.run();
            this.mOnCompleteListener = null;
        }
    }

    public List<ScrollCaptureTarget> getTargets() {
        return new ArrayList(this.mTargets);
    }

    private Rect getScrollBoundsInWindow(ScrollCaptureTarget scrollCaptureTarget) {
        if (scrollCaptureTarget == null || scrollCaptureTarget.getScrollBounds() == null) {
            return new Rect();
        }
        Rect rect = new Rect(scrollCaptureTarget.getScrollBounds());
        Point positionInWindow = scrollCaptureTarget.getPositionInWindow();
        rect.offset(positionInWindow.x, positionInWindow.y);
        return rect;
    }

    public ScrollCaptureTarget getTopResult() {
        int i = 0;
        if (!Flags.scrollCaptureTargetZOrderFix()) {
            ScrollCaptureTarget scrollCaptureTarget = this.mTargets.isEmpty() ? null : this.mTargets.get(0);
            if (scrollCaptureTarget == null || scrollCaptureTarget.getScrollBounds() == null) {
                return null;
            }
            return scrollCaptureTarget;
        }
        ArrayList arrayList = new ArrayList();
        this.mTargets.removeIf(new Predicate() { // from class: android.view.ScrollCaptureSearchResults$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                boolean nullOrEmpty;
                nullOrEmpty = ScrollCaptureSearchResults.nullOrEmpty(((ScrollCaptureTarget) obj).getScrollBounds());
                return nullOrEmpty;
            }
        });
        while (i < this.mTargets.size()) {
            ScrollCaptureTarget scrollCaptureTarget2 = this.mTargets.get(i);
            View containingView = scrollCaptureTarget2.getContainingView();
            i++;
            if (i < this.mTargets.size()) {
                View containingView2 = this.mTargets.get(i).getContainingView();
                if (isDescendant(containingView, containingView2)) {
                    if (hasIncludeHint(containingView) && !hasIncludeHint(containingView2)) {
                    }
                }
            }
            int i2 = i;
            while (true) {
                if (i2 < this.mTargets.size()) {
                    if (Rect.intersects(getScrollBoundsInWindow(scrollCaptureTarget2), getScrollBoundsInWindow(this.mTargets.get(i2)))) {
                        break;
                    }
                    i2++;
                } else {
                    arrayList.add(scrollCaptureTarget2);
                    break;
                }
            }
        }
        arrayList.sort(Comparator.comparing(new Function() { // from class: android.view.ScrollCaptureSearchResults$$ExternalSyntheticLambda3
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                boolean hasIncludeHint;
                hasIncludeHint = ScrollCaptureSearchResults.hasIncludeHint((ScrollCaptureTarget) obj);
                return Boolean.valueOf(hasIncludeHint);
            }
        }).thenComparing(Comparator.comparing(new Function() { // from class: android.view.ScrollCaptureSearchResults$$ExternalSyntheticLambda4
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                Integer valueOf;
                valueOf = Integer.valueOf(ScrollCaptureSearchResults.area((Rect) Objects.requireNonNullElse(((ScrollCaptureTarget) obj).getScrollBounds(), new Rect())));
                return valueOf;
            }
        })));
        if (arrayList.isEmpty()) {
            return null;
        }
        return (ScrollCaptureTarget) arrayList.getLast();
    }

    /* JADX INFO: Access modifiers changed from: private */
    class SearchRequest implements Consumer<Rect> {
        private ScrollCaptureTarget mTarget;

        SearchRequest(ScrollCaptureTarget scrollCaptureTarget) {
            this.mTarget = scrollCaptureTarget;
        }

        @Override // java.util.function.Consumer
        public void accept(final Rect rect) {
            if (this.mTarget == null || ScrollCaptureSearchResults.this.mCancel.isCanceled()) {
                return;
            }
            ScrollCaptureSearchResults.this.mExecutor.execute(new Runnable() { // from class: android.view.ScrollCaptureSearchResults$SearchRequest$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    ScrollCaptureSearchResults.SearchRequest.this.lambda$accept$0(rect);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* renamed from: consume, reason: merged with bridge method [inline-methods] */
        public void lambda$accept$0(Rect rect) {
            if (this.mTarget == null || ScrollCaptureSearchResults.this.mCancel.isCanceled()) {
                return;
            }
            if (!ScrollCaptureSearchResults.nullOrEmpty(rect)) {
                this.mTarget.setScrollBounds(rect);
                this.mTarget.updatePositionInWindow();
            }
            ScrollCaptureSearchResults.this.mCompleted++;
            this.mTarget = null;
            if (ScrollCaptureSearchResults.this.mCompleted == ScrollCaptureSearchResults.this.mTargets.size()) {
                ScrollCaptureSearchResults.this.signalComplete();
            }
        }
    }

    static /* synthetic */ int lambda$static$3(ScrollCaptureTarget scrollCaptureTarget, ScrollCaptureTarget scrollCaptureTarget2) {
        if (scrollCaptureTarget == null && scrollCaptureTarget2 == null) {
            return 0;
        }
        if (scrollCaptureTarget == null || scrollCaptureTarget2 == null) {
            return scrollCaptureTarget == null ? 1 : -1;
        }
        boolean nullOrEmpty = nullOrEmpty(scrollCaptureTarget.getScrollBounds());
        boolean nullOrEmpty2 = nullOrEmpty(scrollCaptureTarget2.getScrollBounds());
        if (nullOrEmpty || nullOrEmpty2) {
            if (nullOrEmpty && nullOrEmpty2) {
                return 0;
            }
            return nullOrEmpty ? 1 : -1;
        }
        View containingView = scrollCaptureTarget.getContainingView();
        View containingView2 = scrollCaptureTarget2.getContainingView();
        boolean hasIncludeHint = hasIncludeHint(containingView);
        if (hasIncludeHint != hasIncludeHint(containingView2)) {
            return hasIncludeHint ? -1 : 1;
        }
        if (isDescendant(containingView, containingView2)) {
            return -1;
        }
        return (!isDescendant(containingView2, containingView) && area(scrollCaptureTarget.getScrollBounds()) >= area(scrollCaptureTarget2.getScrollBounds())) ? -1 : 1;
    }

    private static int area(Rect rect) {
        return rect.width() * rect.height();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean nullOrEmpty(Rect rect) {
        return rect == null || rect.isEmpty();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean hasIncludeHint(ScrollCaptureTarget scrollCaptureTarget) {
        return hasIncludeHint(scrollCaptureTarget.getContainingView());
    }

    private static boolean hasIncludeHint(View view) {
        return (view.getScrollCaptureHint() & 2) != 0;
    }

    private static boolean isDescendant(View view, View view2) {
        if (view == view2) {
            return false;
        }
        ViewParent parent = view2.getParent();
        while (parent != view && parent != null) {
            parent = parent.getParent();
        }
        return parent == view;
    }

    void dump(IndentingPrintWriter indentingPrintWriter) {
        indentingPrintWriter.println("results:");
        indentingPrintWriter.increaseIndent();
        indentingPrintWriter.println("complete: " + isComplete());
        indentingPrintWriter.println("cancelled: " + this.mCancel.isCanceled());
        indentingPrintWriter.println("targets:");
        indentingPrintWriter.increaseIndent();
        if (isEmpty()) {
            indentingPrintWriter.println("None");
        } else {
            for (int i = 0; i < this.mTargets.size(); i++) {
                indentingPrintWriter.println(NavigationBarInflaterView.SIZE_MOD_START + i + NavigationBarInflaterView.SIZE_MOD_END);
                indentingPrintWriter.increaseIndent();
                this.mTargets.get(i).dump(indentingPrintWriter);
                indentingPrintWriter.decreaseIndent();
            }
            indentingPrintWriter.decreaseIndent();
        }
        indentingPrintWriter.decreaseIndent();
    }
}
