package android.widget;

import android.app.RemoteAction;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.graphics.RectF;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.LocaleList;
import android.text.Layout;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextUtils;
import android.text.util.Linkify;
import android.util.EventLog;
import android.util.Log;
import android.view.ActionMode;
import android.view.ViewConfiguration;
import android.view.textclassifier.ExtrasUtils;
import android.view.textclassifier.SelectionEvent;
import android.view.textclassifier.TextClassification;
import android.view.textclassifier.TextClassificationConstants;
import android.view.textclassifier.TextClassificationContext;
import android.view.textclassifier.TextClassificationManager;
import android.view.textclassifier.TextClassifier;
import android.view.textclassifier.TextClassifierEvent;
import android.view.textclassifier.TextSelection;
import android.widget.Editor;
import android.widget.SelectionActionModeHelper;
import android.widget.SmartSelectSprite;
import com.android.internal.util.Preconditions;
import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.regex.Pattern;

/* loaded from: classes5.dex */
public class SelectionActionModeHelper {
    private static final String LOG_TAG = "SelectActionModeHelper";
    private final Editor mEditor;
    private final SelectionTracker mSelectionTracker;
    private final SmartSelectSprite mSmartSelectSprite;
    private TextClassification mTextClassification;
    private AsyncTask mTextClassificationAsyncTask;
    private final TextClassificationHelper mTextClassificationHelper;
    private final TextView mTextView;

    private static int getActionType(int i) {
        if (i == 16908337) {
            return 102;
        }
        if (i == 16908341) {
            return 104;
        }
        if (i == 16908353) {
            return 105;
        }
        switch (i) {
            case 16908319:
                return 200;
            case 16908320:
                return 103;
            case 16908321:
                return 101;
            case 16908322:
                return 102;
            default:
                return 108;
        }
    }

    SelectionActionModeHelper(Editor editor) {
        Editor editor2 = (Editor) Objects.requireNonNull(editor);
        this.mEditor = editor2;
        final TextView textView = editor2.getTextView();
        this.mTextView = textView;
        Context context = textView.getContext();
        Objects.requireNonNull(textView);
        this.mTextClassificationHelper = new TextClassificationHelper(context, new SelectionActionModeHelper$$ExternalSyntheticLambda10(textView), getText(textView), 0, 1, textView.getTextLocales());
        this.mSelectionTracker = new SelectionTracker(textView);
        if (getTextClassificationSettings().isSmartSelectionAnimationEnabled()) {
            Context context2 = textView.getContext();
            int i = editor.getTextView().mHighlightColor;
            Objects.requireNonNull(textView);
            this.mSmartSelectSprite = new SmartSelectSprite(context2, i, new Runnable() { // from class: android.widget.SelectionActionModeHelper$$ExternalSyntheticLambda11
                @Override // java.lang.Runnable
                public final void run() {
                    textView.invalidate();
                }
            });
            return;
        }
        this.mSmartSelectSprite = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int[] sortSelectionIndices(int i, int i2) {
        if (i < i2) {
            return new int[]{i, i2};
        }
        return new int[]{i2, i};
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int[] sortSelectionIndicesFromTextView(TextView textView) {
        return sortSelectionIndices(textView.getSelectionStart(), textView.getSelectionEnd());
    }

    public void startSelectionActionModeAsync(boolean z) {
        Supplier selectionActionModeHelper$$ExternalSyntheticLambda3;
        Consumer consumer;
        boolean zIsSmartSelectionEnabled = z & getTextClassificationSettings().isSmartSelectionEnabled();
        int[] iArrSortSelectionIndicesFromTextView = sortSelectionIndicesFromTextView(this.mTextView);
        this.mSelectionTracker.onOriginalSelection(getText(this.mTextView), iArrSortSelectionIndicesFromTextView[0], iArrSortSelectionIndicesFromTextView[1], false);
        cancelAsyncTask();
        if (skipTextClassification()) {
            startSelectionActionMode(null);
            return;
        }
        resetTextClassificationHelper();
        SmartSelectSprite smartSelectSprite = this.mSmartSelectSprite;
        if (smartSelectSprite != null && smartSelectSprite.isAnimationActive()) {
            this.mSmartSelectSprite.cancelAnimation();
        }
        TextView textView = this.mTextView;
        int timeoutDuration = this.mTextClassificationHelper.getTimeoutDuration();
        if (zIsSmartSelectionEnabled) {
            final TextClassificationHelper textClassificationHelper = this.mTextClassificationHelper;
            Objects.requireNonNull(textClassificationHelper);
            selectionActionModeHelper$$ExternalSyntheticLambda3 = new Supplier() { // from class: android.widget.SelectionActionModeHelper$$ExternalSyntheticLambda2
                @Override // java.util.function.Supplier
                public final Object get() {
                    return textClassificationHelper.suggestSelection();
                }
            };
        } else {
            TextClassificationHelper textClassificationHelper2 = this.mTextClassificationHelper;
            Objects.requireNonNull(textClassificationHelper2);
            selectionActionModeHelper$$ExternalSyntheticLambda3 = new SelectionActionModeHelper$$ExternalSyntheticLambda3(textClassificationHelper2);
        }
        Supplier supplier = selectionActionModeHelper$$ExternalSyntheticLambda3;
        if (this.mSmartSelectSprite != null) {
            consumer = new Consumer() { // from class: android.widget.SelectionActionModeHelper$$ExternalSyntheticLambda4
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.startSelectionActionModeWithSmartSelectAnimation((SelectionActionModeHelper.SelectionResult) obj);
                }
            };
        } else {
            consumer = new Consumer() { // from class: android.widget.SelectionActionModeHelper$$ExternalSyntheticLambda5
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.startSelectionActionMode((SelectionActionModeHelper.SelectionResult) obj);
                }
            };
        }
        Consumer consumer2 = consumer;
        TextClassificationHelper textClassificationHelper3 = this.mTextClassificationHelper;
        Objects.requireNonNull(textClassificationHelper3);
        this.mTextClassificationAsyncTask = new TextClassificationAsyncTask(textView, timeoutDuration, supplier, consumer2, new SelectionActionModeHelper$$ExternalSyntheticLambda6(textClassificationHelper3)).execute(new Void[0]);
    }

    public void startLinkActionModeAsync(int i, int i2) {
        int[] iArrSortSelectionIndices = sortSelectionIndices(i, i2);
        this.mSelectionTracker.onOriginalSelection(getText(this.mTextView), iArrSortSelectionIndices[0], iArrSortSelectionIndices[1], true);
        cancelAsyncTask();
        if (skipTextClassification()) {
            startLinkActionMode(null);
            return;
        }
        resetTextClassificationHelper(iArrSortSelectionIndices[0], iArrSortSelectionIndices[1]);
        TextView textView = this.mTextView;
        int timeoutDuration = this.mTextClassificationHelper.getTimeoutDuration();
        TextClassificationHelper textClassificationHelper = this.mTextClassificationHelper;
        Objects.requireNonNull(textClassificationHelper);
        SelectionActionModeHelper$$ExternalSyntheticLambda3 selectionActionModeHelper$$ExternalSyntheticLambda3 = new SelectionActionModeHelper$$ExternalSyntheticLambda3(textClassificationHelper);
        Consumer consumer = new Consumer() { // from class: android.widget.SelectionActionModeHelper$$ExternalSyntheticLambda9
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.startLinkActionMode((SelectionActionModeHelper.SelectionResult) obj);
            }
        };
        TextClassificationHelper textClassificationHelper2 = this.mTextClassificationHelper;
        Objects.requireNonNull(textClassificationHelper2);
        this.mTextClassificationAsyncTask = new TextClassificationAsyncTask(textView, timeoutDuration, selectionActionModeHelper$$ExternalSyntheticLambda3, consumer, new SelectionActionModeHelper$$ExternalSyntheticLambda6(textClassificationHelper2)).execute(new Void[0]);
    }

    public void invalidateActionModeAsync() {
        cancelAsyncTask();
        if (skipTextClassification()) {
            invalidateActionMode(null);
            return;
        }
        resetTextClassificationHelper();
        TextView textView = this.mTextView;
        int timeoutDuration = this.mTextClassificationHelper.getTimeoutDuration();
        TextClassificationHelper textClassificationHelper = this.mTextClassificationHelper;
        Objects.requireNonNull(textClassificationHelper);
        SelectionActionModeHelper$$ExternalSyntheticLambda3 selectionActionModeHelper$$ExternalSyntheticLambda3 = new SelectionActionModeHelper$$ExternalSyntheticLambda3(textClassificationHelper);
        Consumer consumer = new Consumer() { // from class: android.widget.SelectionActionModeHelper$$ExternalSyntheticLambda7
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.invalidateActionMode((SelectionActionModeHelper.SelectionResult) obj);
            }
        };
        TextClassificationHelper textClassificationHelper2 = this.mTextClassificationHelper;
        Objects.requireNonNull(textClassificationHelper2);
        this.mTextClassificationAsyncTask = new TextClassificationAsyncTask(textView, timeoutDuration, selectionActionModeHelper$$ExternalSyntheticLambda3, consumer, new SelectionActionModeHelper$$ExternalSyntheticLambda6(textClassificationHelper2)).execute(new Void[0]);
    }

    public void onSelectionAction(int i, String str) {
        int[] iArrSortSelectionIndicesFromTextView = sortSelectionIndicesFromTextView(this.mTextView);
        this.mSelectionTracker.onSelectionAction(iArrSortSelectionIndicesFromTextView[0], iArrSortSelectionIndicesFromTextView[1], getActionType(i), str, this.mTextClassification);
    }

    public void onSelectionDrag() {
        int[] iArrSortSelectionIndicesFromTextView = sortSelectionIndicesFromTextView(this.mTextView);
        this.mSelectionTracker.onSelectionAction(iArrSortSelectionIndicesFromTextView[0], iArrSortSelectionIndicesFromTextView[1], 106, null, this.mTextClassification);
    }

    public void onTextChanged(int i, int i2) {
        int[] iArrSortSelectionIndices = sortSelectionIndices(i, i2);
        this.mSelectionTracker.onTextChanged(iArrSortSelectionIndices[0], iArrSortSelectionIndices[1], this.mTextClassification);
    }

    public boolean resetSelection(int i) {
        if (!this.mSelectionTracker.resetSelection(i, this.mEditor)) {
            return false;
        }
        invalidateActionModeAsync();
        return true;
    }

    public TextClassification getTextClassification() {
        return this.mTextClassification;
    }

    public void onDestroyActionMode() {
        cancelSmartSelectAnimation();
        this.mSelectionTracker.onSelectionDestroyed();
        cancelAsyncTask();
    }

    public void onDraw(Canvas canvas) {
        SmartSelectSprite smartSelectSprite;
        if (!isDrawingHighlight() || (smartSelectSprite = this.mSmartSelectSprite) == null) {
            return;
        }
        smartSelectSprite.draw(canvas);
    }

    public boolean isDrawingHighlight() {
        SmartSelectSprite smartSelectSprite = this.mSmartSelectSprite;
        return smartSelectSprite != null && smartSelectSprite.isAnimationActive();
    }

    private TextClassificationConstants getTextClassificationSettings() {
        return TextClassificationManager.getSettings(this.mTextView.getContext());
    }

    private void cancelAsyncTask() {
        AsyncTask asyncTask = this.mTextClassificationAsyncTask;
        if (asyncTask != null) {
            asyncTask.cancel(true);
            this.mTextClassificationAsyncTask = null;
        }
        this.mTextClassification = null;
    }

    private boolean skipTextClassification() {
        return this.mTextView.usesNoOpTextClassifier() || (this.mTextView.getSelectionEnd() == this.mTextView.getSelectionStart()) || (this.mTextView.hasPasswordTransformationMethod() || TextView.isPasswordInputType(this.mTextView.getInputType()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startLinkActionMode(SelectionResult selectionResult) {
        startActionMode(2, selectionResult);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startSelectionActionMode(SelectionResult selectionResult) {
        startActionMode(0, selectionResult);
    }

    private void startActionMode(int i, SelectionResult selectionResult) {
        CharSequence text = getText(this.mTextView);
        if (selectionResult != null && (text instanceof Spannable) && (this.mTextView.isTextSelectable() || this.mTextView.isTextEditable())) {
            if (!getTextClassificationSettings().isModelDarkLaunchEnabled()) {
                Selection.setSelection((Spannable) text, selectionResult.mStart, selectionResult.mEnd);
                this.mTextView.invalidate();
            }
            this.mTextClassification = selectionResult.mClassification;
        } else if (selectionResult != null && i == 2) {
            this.mTextClassification = selectionResult.mClassification;
        } else {
            this.mTextClassification = null;
        }
        if (this.mEditor.startActionModeInternal(i)) {
            Editor.SelectionModifierCursorController selectionController = this.mEditor.getSelectionController();
            if (selectionController != null && (this.mTextView.isTextSelectable() || this.mTextView.isTextEditable())) {
                if (this.mTextView.showUIForTouchScreen()) {
                    selectionController.show();
                } else {
                    selectionController.hide();
                }
            }
            if (selectionResult != null) {
                if (i == 0) {
                    this.mSelectionTracker.onSmartSelection(selectionResult);
                } else if (i == 2) {
                    this.mSelectionTracker.onLinkSelected(selectionResult);
                }
            }
        }
        this.mEditor.setRestartActionModeOnNextRefresh(false);
        this.mTextClassificationAsyncTask = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startSelectionActionModeWithSmartSelectAnimation(final SelectionResult selectionResult) {
        Runnable runnable = new Runnable() { // from class: android.widget.SelectionActionModeHelper$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$startSelectionActionModeWithSmartSelectAnimation$0(selectionResult);
            }
        };
        int[] iArrSortSelectionIndicesFromTextView = sortSelectionIndicesFromTextView(this.mTextView);
        if (selectionResult == null || (iArrSortSelectionIndicesFromTextView[0] == selectionResult.mStart && iArrSortSelectionIndicesFromTextView[1] == selectionResult.mEnd)) {
            runnable.run();
            return;
        }
        List<SmartSelectSprite.RectangleWithTextSelectionLayout> listConvertSelectionToRectangles = convertSelectionToRectangles(this.mTextView, selectionResult.mStart, selectionResult.mEnd);
        this.mSmartSelectSprite.startAnimation(movePointInsideNearestRectangle(new PointF(this.mEditor.getLastUpPositionX(), this.mEditor.getLastUpPositionY()), listConvertSelectionToRectangles, new SelectionActionModeHelper$$ExternalSyntheticLambda1()), listConvertSelectionToRectangles, runnable);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startSelectionActionModeWithSmartSelectAnimation$0(SelectionResult selectionResult) {
        if (selectionResult == null || selectionResult.mStart < 0 || selectionResult.mEnd > getText(this.mTextView).length() || selectionResult.mStart > selectionResult.mEnd) {
            selectionResult = null;
        }
        startSelectionActionMode(selectionResult);
    }

    private List<SmartSelectSprite.RectangleWithTextSelectionLayout> convertSelectionToRectangles(TextView textView, int i, int i2) {
        final ArrayList arrayList = new ArrayList();
        Layout.SelectionRectangleConsumer selectionRectangleConsumer = new Layout.SelectionRectangleConsumer() { // from class: android.widget.SelectionActionModeHelper$$ExternalSyntheticLambda12
            @Override // android.text.Layout.SelectionRectangleConsumer
            public final void accept(float f, float f2, float f3, float f4, int i3) {
                SelectionActionModeHelper.mergeRectangleIntoList(arrayList, new RectF(f, f2, f3, f4), new SelectionActionModeHelper$$ExternalSyntheticLambda1(), new Function() { // from class: android.widget.SelectionActionModeHelper$$ExternalSyntheticLambda8
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        return SelectionActionModeHelper.lambda$convertSelectionToRectangles$1(i3, (RectF) obj);
                    }
                });
            }
        };
        textView.getLayout().getSelection(textView.originalToTransformed(i, 1), textView.originalToTransformed(i2, 1), selectionRectangleConsumer);
        arrayList.sort(Comparator.comparing(new SelectionActionModeHelper$$ExternalSyntheticLambda1(), SmartSelectSprite.RECTANGLE_COMPARATOR));
        return arrayList;
    }

    static /* synthetic */ SmartSelectSprite.RectangleWithTextSelectionLayout lambda$convertSelectionToRectangles$1(int i, RectF rectF) {
        return new SmartSelectSprite.RectangleWithTextSelectionLayout(rectF, i);
    }

    public static <T> void mergeRectangleIntoList(List<T> list, RectF rectF, Function<T, RectF> function, Function<RectF, T> function2) {
        if (rectF.isEmpty()) {
            return;
        }
        int size = list.size();
        int i = 0;
        while (true) {
            boolean z = true;
            if (i < size) {
                RectF rectFApply = function.apply(list.get(i));
                if (rectFApply.contains(rectF)) {
                    return;
                }
                if (rectF.contains(rectFApply)) {
                    rectFApply.setEmpty();
                } else {
                    if (rectF.left != rectFApply.right && rectF.right != rectFApply.left) {
                        z = false;
                    }
                    if (rectF.top == rectFApply.top && rectF.bottom == rectFApply.bottom && (RectF.intersects(rectF, rectFApply) || z)) {
                        rectF.union(rectFApply);
                        rectFApply.setEmpty();
                    }
                }
                i++;
            } else {
                for (int i2 = size - 1; i2 >= 0; i2--) {
                    if (function.apply(list.get(i2)).isEmpty()) {
                        list.remove(i2);
                    }
                }
                list.add(function2.apply(rectF));
                return;
            }
        }
    }

    public static <T> PointF movePointInsideNearestRectangle(PointF pointF, List<T> list, Function<T, RectF> function) {
        float f;
        PointF pointF2 = pointF;
        int size = list.size();
        float f2 = -1.0f;
        int i = 0;
        double d = Double.MAX_VALUE;
        float f3 = -1.0f;
        while (i < size) {
            RectF rectFApply = function.apply(list.get(i));
            float fCenterY = rectFApply.centerY();
            if (pointF2.x > rectFApply.right) {
                f = rectFApply.right;
            } else if (pointF2.x < rectFApply.left) {
                f = rectFApply.left;
            } else {
                f = pointF2.x;
            }
            int i2 = size;
            double dPow = Math.pow(pointF2.x - f, 2.0d) + Math.pow(pointF2.y - fCenterY, 2.0d);
            if (dPow < d) {
                f2 = f;
                f3 = fCenterY;
                d = dPow;
            }
            i++;
            pointF2 = pointF;
            size = i2;
        }
        return new PointF(f2, f3);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void invalidateActionMode(SelectionResult selectionResult) {
        cancelSmartSelectAnimation();
        this.mTextClassification = selectionResult != null ? selectionResult.mClassification : null;
        ActionMode textActionMode = this.mEditor.getTextActionMode();
        if (textActionMode != null) {
            textActionMode.invalidate();
        }
        int[] iArrSortSelectionIndicesFromTextView = sortSelectionIndicesFromTextView(this.mTextView);
        this.mSelectionTracker.onSelectionUpdated(iArrSortSelectionIndicesFromTextView[0], iArrSortSelectionIndicesFromTextView[1], this.mTextClassification);
        this.mTextClassificationAsyncTask = null;
    }

    private void resetTextClassificationHelper(int i, int i2) {
        int i3;
        int i4;
        if (i < 0 || i2 < 0) {
            int[] iArrSortSelectionIndicesFromTextView = sortSelectionIndicesFromTextView(this.mTextView);
            int i5 = iArrSortSelectionIndicesFromTextView[0];
            i3 = iArrSortSelectionIndicesFromTextView[1];
            i4 = i5;
        } else {
            i4 = i;
            i3 = i2;
        }
        TextClassificationHelper textClassificationHelper = this.mTextClassificationHelper;
        TextView textView = this.mTextView;
        Objects.requireNonNull(textView);
        textClassificationHelper.init(new SelectionActionModeHelper$$ExternalSyntheticLambda10(textView), getText(this.mTextView), i4, i3, this.mTextView.getTextLocales());
    }

    private void resetTextClassificationHelper() {
        resetTextClassificationHelper(-1, -1);
    }

    private void cancelSmartSelectAnimation() {
        SmartSelectSprite smartSelectSprite = this.mSmartSelectSprite;
        if (smartSelectSprite != null) {
            smartSelectSprite.cancelAnimation();
        }
    }

    private static final class SelectionTracker {
        private boolean mAllowReset;
        private final LogAbandonRunnable mDelayedLogAbandon = new LogAbandonRunnable();
        private SelectionMetricsLogger mLogger;
        private int mOriginalEnd;
        private int mOriginalStart;
        private int mSelectionEnd;
        private int mSelectionStart;
        private final TextView mTextView;

        SelectionTracker(TextView textView) {
            this.mTextView = (TextView) Objects.requireNonNull(textView);
            this.mLogger = new SelectionMetricsLogger(textView);
        }

        public void onOriginalSelection(CharSequence charSequence, int i, int i2, boolean z) {
            this.mDelayedLogAbandon.flush();
            this.mSelectionStart = i;
            this.mOriginalStart = i;
            this.mSelectionEnd = i2;
            this.mOriginalEnd = i2;
            this.mAllowReset = false;
            maybeInvalidateLogger();
            this.mLogger.logSelectionStarted(this.mTextView.getTextClassificationSession(), this.mTextView.getTextClassificationContext(), charSequence, i, z ? 2 : 1);
        }

        public void onSmartSelection(SelectionResult selectionResult) {
            onClassifiedSelection(selectionResult);
            this.mTextView.notifyContentCaptureTextChanged();
            this.mLogger.logSelectionModified(selectionResult.mStart, selectionResult.mEnd, selectionResult.mClassification, selectionResult.mSelection);
        }

        public void onLinkSelected(SelectionResult selectionResult) {
            onClassifiedSelection(selectionResult);
        }

        private void onClassifiedSelection(SelectionResult selectionResult) {
            if (isSelectionStarted()) {
                this.mSelectionStart = selectionResult.mStart;
                int i = selectionResult.mEnd;
                this.mSelectionEnd = i;
                this.mAllowReset = (this.mSelectionStart == this.mOriginalStart && i == this.mOriginalEnd) ? false : true;
            }
        }

        public void onSelectionUpdated(int i, int i2, TextClassification textClassification) {
            if (isSelectionStarted()) {
                this.mSelectionStart = i;
                this.mSelectionEnd = i2;
                this.mAllowReset = false;
                this.mTextView.notifyContentCaptureTextChanged();
                this.mLogger.logSelectionModified(i, i2, textClassification, null);
            }
        }

        public void onSelectionDestroyed() {
            this.mAllowReset = false;
            this.mTextView.notifyContentCaptureTextChanged();
            this.mDelayedLogAbandon.schedule(100);
        }

        public void onSelectionAction(int i, int i2, int i3, String str, TextClassification textClassification) {
            if (isSelectionStarted()) {
                this.mAllowReset = false;
                this.mLogger.logSelectionAction(i, i2, i3, str, textClassification);
            }
        }

        public boolean resetSelection(int i, Editor editor) {
            TextView textView = editor.getTextView();
            if (!isSelectionStarted() || !this.mAllowReset || i < this.mSelectionStart || i > this.mSelectionEnd || !(SelectionActionModeHelper.getText(textView) instanceof Spannable)) {
                return false;
            }
            this.mAllowReset = false;
            boolean zSelectCurrentWord = editor.selectCurrentWord();
            if (zSelectCurrentWord) {
                int[] iArrSortSelectionIndicesFromTextView = SelectionActionModeHelper.sortSelectionIndicesFromTextView(textView);
                int i2 = iArrSortSelectionIndicesFromTextView[0];
                this.mSelectionStart = i2;
                int i3 = iArrSortSelectionIndicesFromTextView[1];
                this.mSelectionEnd = i3;
                this.mLogger.logSelectionAction(i2, i3, 201, null, null);
            }
            return zSelectCurrentWord;
        }

        public void onTextChanged(int i, int i2, TextClassification textClassification) {
            if (isSelectionStarted() && i == this.mSelectionStart && i2 == this.mSelectionEnd) {
                onSelectionAction(i, i2, 100, null, textClassification);
            }
        }

        private void maybeInvalidateLogger() {
            if (this.mLogger.isEditTextLogger() != this.mTextView.isTextEditable()) {
                this.mLogger = new SelectionMetricsLogger(this.mTextView);
            }
        }

        private boolean isSelectionStarted() {
            int i;
            int i2 = this.mSelectionStart;
            return i2 >= 0 && (i = this.mSelectionEnd) >= 0 && i2 != i;
        }

        private final class LogAbandonRunnable implements Runnable {
            private boolean mIsPending;

            private LogAbandonRunnable() {
            }

            void schedule(int i) {
                if (this.mIsPending) {
                    Log.e(SelectionActionModeHelper.LOG_TAG, "Force flushing abandon due to new scheduling request");
                    flush();
                }
                this.mIsPending = true;
                SelectionTracker.this.mTextView.postDelayed(this, i);
            }

            void flush() {
                SelectionTracker.this.mTextView.removeCallbacks(this);
                run();
            }

            @Override // java.lang.Runnable
            public void run() {
                if (this.mIsPending) {
                    SelectionTracker.this.mLogger.logSelectionAction(SelectionTracker.this.mSelectionStart, SelectionTracker.this.mSelectionEnd, 107, null, null);
                    SelectionTracker selectionTracker = SelectionTracker.this;
                    selectionTracker.mSelectionEnd = -1;
                    selectionTracker.mSelectionStart = -1;
                    SelectionTracker.this.mLogger.endTextClassificationSession();
                    this.mIsPending = false;
                }
            }
        }
    }

    private static final class SelectionMetricsLogger {
        private static final String LOG_TAG = "SelectionMetricsLogger";
        private static final Pattern PATTERN_WHITESPACE = Pattern.compile("\\s+");
        private TextClassificationContext mClassificationContext;
        private TextClassifier mClassificationSession;
        private final boolean mEditTextLogger;
        private int mStartIndex;
        private String mText;
        private final BreakIterator mTokenIterator;
        private TextClassifierEvent mTranslateClickEvent;
        private TextClassifierEvent mTranslateViewEvent;

        SelectionMetricsLogger(TextView textView) {
            Objects.requireNonNull(textView);
            this.mEditTextLogger = textView.isTextEditable();
            this.mTokenIterator = BreakIterator.getWordInstance(textView.getTextLocale());
        }

        public void logSelectionStarted(TextClassifier textClassifier, TextClassificationContext textClassificationContext, CharSequence charSequence, int i, int i2) {
            try {
                Objects.requireNonNull(charSequence);
                Preconditions.checkArgumentInRange(i, 0, charSequence.length(), "index");
                String str = this.mText;
                if (str == null || !str.contentEquals(charSequence)) {
                    this.mText = charSequence.toString();
                }
                this.mTokenIterator.setText(this.mText);
                this.mStartIndex = i;
                this.mClassificationSession = textClassifier;
                this.mClassificationContext = textClassificationContext;
                if (hasActiveClassificationSession()) {
                    this.mClassificationSession.onSelectionEvent(SelectionEvent.createSelectionStartedEvent(i2, 0));
                }
            } catch (Exception e) {
                Log.e(LOG_TAG, "" + e.getMessage(), e);
            }
        }

        public void logSelectionModified(int i, int i2, TextClassification textClassification, TextSelection textSelection) {
            try {
                if (hasActiveClassificationSession()) {
                    Preconditions.checkArgumentInRange(i, 0, this.mText.length(), "start");
                    Preconditions.checkArgumentInRange(i2, i, this.mText.length(), "end");
                    int[] wordDelta = getWordDelta(i, i2);
                    if (textSelection != null) {
                        this.mClassificationSession.onSelectionEvent(SelectionEvent.createSelectionModifiedEvent(wordDelta[0], wordDelta[1], textSelection));
                    } else if (textClassification != null) {
                        this.mClassificationSession.onSelectionEvent(SelectionEvent.createSelectionModifiedEvent(wordDelta[0], wordDelta[1], textClassification));
                    } else {
                        this.mClassificationSession.onSelectionEvent(SelectionEvent.createSelectionModifiedEvent(wordDelta[0], wordDelta[1]));
                    }
                    maybeGenerateTranslateViewEvent(textClassification);
                }
            } catch (Exception e) {
                Log.e(LOG_TAG, "" + e.getMessage(), e);
            }
        }

        public void logSelectionAction(int i, int i2, int i3, String str, TextClassification textClassification) {
            try {
                if (hasActiveClassificationSession()) {
                    Preconditions.checkArgumentInRange(i, 0, this.mText.length(), "start");
                    Preconditions.checkArgumentInRange(i2, i, this.mText.length(), "end");
                    int[] wordDelta = getWordDelta(i, i2);
                    if (textClassification != null) {
                        this.mClassificationSession.onSelectionEvent(SelectionEvent.createSelectionActionEvent(wordDelta[0], wordDelta[1], i3, textClassification));
                    } else {
                        this.mClassificationSession.onSelectionEvent(SelectionEvent.createSelectionActionEvent(wordDelta[0], wordDelta[1], i3));
                    }
                    maybeGenerateTranslateClickEvent(textClassification, str);
                    if (SelectionEvent.isTerminal(i3)) {
                        endTextClassificationSession();
                    }
                }
            } catch (Exception e) {
                Log.e(LOG_TAG, "" + e.getMessage(), e);
            }
        }

        public boolean isEditTextLogger() {
            return this.mEditTextLogger;
        }

        public void endTextClassificationSession() {
            if (hasActiveClassificationSession()) {
                maybeReportTranslateEvents();
                this.mClassificationSession.destroy();
            }
        }

        private boolean hasActiveClassificationSession() {
            TextClassifier textClassifier = this.mClassificationSession;
            return (textClassifier == null || textClassifier.isDestroyed()) ? false : true;
        }

        private int[] getWordDelta(int i, int i2) {
            int[] iArr = new int[2];
            int i3 = this.mStartIndex;
            if (i == i3) {
                iArr[0] = 0;
            } else if (i < i3) {
                iArr[0] = -countWordsForward(i);
            } else {
                iArr[0] = countWordsBackward(i);
                if (!this.mTokenIterator.isBoundary(i) && !isWhitespace(this.mTokenIterator.preceding(i), this.mTokenIterator.following(i))) {
                    iArr[0] = iArr[0] - 1;
                }
            }
            int i4 = this.mStartIndex;
            if (i2 == i4) {
                iArr[1] = 0;
                return iArr;
            }
            if (i2 < i4) {
                iArr[1] = -countWordsForward(i2);
                return iArr;
            }
            iArr[1] = countWordsBackward(i2);
            return iArr;
        }

        private int countWordsBackward(int i) {
            int i2 = 0;
            Preconditions.checkArgument(i >= this.mStartIndex);
            while (i > this.mStartIndex) {
                int iPreceding = this.mTokenIterator.preceding(i);
                if (!isWhitespace(iPreceding, i)) {
                    i2++;
                }
                i = iPreceding;
            }
            return i2;
        }

        private int countWordsForward(int i) {
            int i2 = 0;
            Preconditions.checkArgument(i <= this.mStartIndex);
            while (i < this.mStartIndex) {
                int iFollowing = this.mTokenIterator.following(i);
                if (!isWhitespace(i, iFollowing)) {
                    i2++;
                }
                i = iFollowing;
            }
            return i2;
        }

        private boolean isWhitespace(int i, int i2) {
            return PATTERN_WHITESPACE.matcher(this.mText.substring(i, i2)).matches();
        }

        private void maybeGenerateTranslateViewEvent(TextClassification textClassification) {
            if (textClassification != null) {
                TextClassifierEvent textClassifierEventGenerateTranslateEvent = generateTranslateEvent(6, textClassification, this.mClassificationContext, null);
                if (textClassifierEventGenerateTranslateEvent == null) {
                    textClassifierEventGenerateTranslateEvent = this.mTranslateViewEvent;
                }
                this.mTranslateViewEvent = textClassifierEventGenerateTranslateEvent;
            }
        }

        private void maybeGenerateTranslateClickEvent(TextClassification textClassification, String str) {
            if (textClassification != null) {
                this.mTranslateClickEvent = generateTranslateEvent(13, textClassification, this.mClassificationContext, str);
            }
        }

        private void maybeReportTranslateEvents() {
            TextClassifierEvent textClassifierEvent = this.mTranslateViewEvent;
            if (textClassifierEvent != null) {
                this.mClassificationSession.onTextClassifierEvent(textClassifierEvent);
                this.mTranslateViewEvent = null;
            }
            TextClassifierEvent textClassifierEvent2 = this.mTranslateClickEvent;
            if (textClassifierEvent2 != null) {
                this.mClassificationSession.onTextClassifierEvent(textClassifierEvent2);
                this.mTranslateClickEvent = null;
            }
        }

        private static TextClassifierEvent generateTranslateEvent(int i, TextClassification textClassification, TextClassificationContext textClassificationContext, String str) {
            RemoteAction remoteActionFindTranslateAction = ExtrasUtils.findTranslateAction(textClassification);
            if (remoteActionFindTranslateAction == null) {
                return null;
            }
            if (i == 13 && !remoteActionFindTranslateAction.getTitle().toString().equals(str)) {
                return null;
            }
            Bundle foreignLanguageExtra = ExtrasUtils.getForeignLanguageExtra(textClassification);
            ExtrasUtils.getEntityType(foreignLanguageExtra);
            return new TextClassifierEvent.LanguageDetectionEvent.Builder(i).setEventContext(textClassificationContext).setResultId(textClassification.getId()).setScores(ExtrasUtils.getScore(foreignLanguageExtra)).setActionIndices(textClassification.getActions().indexOf(remoteActionFindTranslateAction)).setModelName(ExtrasUtils.getModelName(foreignLanguageExtra)).build();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class TextClassificationAsyncTask extends AsyncTask<Void, Void, SelectionResult> {
        private final String mOriginalText;
        private final Consumer<SelectionResult> mSelectionResultCallback;
        private final Supplier<SelectionResult> mSelectionResultSupplier;
        private final TextView mTextView;
        private final int mTimeOutDuration;
        private final Supplier<SelectionResult> mTimeOutResultSupplier;

        TextClassificationAsyncTask(TextView textView, int i, Supplier<SelectionResult> supplier, Consumer<SelectionResult> consumer, Supplier<SelectionResult> supplier2) {
            super(textView != null ? textView.getHandler() : null);
            TextView textView2 = (TextView) Objects.requireNonNull(textView);
            this.mTextView = textView2;
            this.mTimeOutDuration = i;
            this.mSelectionResultSupplier = (Supplier) Objects.requireNonNull(supplier);
            this.mSelectionResultCallback = (Consumer) Objects.requireNonNull(consumer);
            this.mTimeOutResultSupplier = (Supplier) Objects.requireNonNull(supplier2);
            this.mOriginalText = SelectionActionModeHelper.getText(textView2).toString();
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public SelectionResult doInBackground(Void... voidArr) {
            SelectionResult selectionResult;
            Runnable runnable = new Runnable() { // from class: android.widget.SelectionActionModeHelper$TextClassificationAsyncTask$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.onTimeOut();
                }
            };
            this.mTextView.postDelayed(runnable, this.mTimeOutDuration);
            try {
                selectionResult = this.mSelectionResultSupplier.get();
            } catch (IllegalStateException e) {
                Log.w(SelectionActionModeHelper.LOG_TAG, "TextClassificationAsyncTask failed.", e);
                selectionResult = null;
            }
            this.mTextView.removeCallbacks(runnable);
            return selectionResult;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public void onPostExecute(SelectionResult selectionResult) {
            if (!TextUtils.equals(this.mOriginalText, SelectionActionModeHelper.getText(this.mTextView))) {
                selectionResult = null;
            }
            this.mSelectionResultCallback.accept(selectionResult);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void onTimeOut() {
            Log.d(SelectionActionModeHelper.LOG_TAG, "Timeout in TextClassificationAsyncTask");
            if (getStatus() == AsyncTask.Status.RUNNING) {
                onPostExecute(this.mTimeOutResultSupplier.get());
            }
            cancel(true);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class TextClassificationHelper {
        private static final int TRIM_DELTA_UPPER_BOUND = 240;
        private final Context mContext;
        private LocaleList mDefaultLocales;
        private boolean mInitialized;
        private LocaleList mLastClassificationLocales;
        private SelectionResult mLastClassificationResult;
        private int mLastClassificationSelectionEnd;
        private int mLastClassificationSelectionStart;
        private CharSequence mLastClassificationText;
        private int mRelativeEnd;
        private int mRelativeStart;
        private int mSelectionEnd;
        private int mSelectionStart;
        private String mText;
        private Supplier<TextClassifier> mTextClassifier;
        private int mTrimStart;
        private CharSequence mTrimmedText;
        private final ViewConfiguration mViewConfiguration;

        TextClassificationHelper(Context context, Supplier<TextClassifier> supplier, CharSequence charSequence, int i, int i2, LocaleList localeList) {
            init(supplier, charSequence, i, i2, localeList);
            Context context2 = (Context) Objects.requireNonNull(context);
            this.mContext = context2;
            this.mViewConfiguration = ViewConfiguration.get(context2);
        }

        public void init(Supplier<TextClassifier> supplier, CharSequence charSequence, int i, int i2, LocaleList localeList) {
            this.mTextClassifier = (Supplier) Objects.requireNonNull(supplier);
            this.mText = ((CharSequence) Objects.requireNonNull(charSequence)).toString();
            this.mLastClassificationText = null;
            if (i2 < i) {
                this.mSelectionStart = i2;
                this.mSelectionEnd = i;
            } else {
                this.mSelectionStart = i;
                this.mSelectionEnd = i2;
            }
            this.mDefaultLocales = localeList;
        }

        public SelectionResult classifyText() {
            this.mInitialized = true;
            return performClassification(null);
        }

        public SelectionResult suggestSelection() {
            TextSelection textSelectionSuggestSelection;
            this.mInitialized = true;
            trimText();
            if (this.mContext.getApplicationInfo().targetSdkVersion >= 28) {
                textSelectionSuggestSelection = this.mTextClassifier.get().suggestSelection(new TextSelection.Request.Builder(this.mTrimmedText, this.mRelativeStart, this.mRelativeEnd).setDefaultLocales(this.mDefaultLocales).setDarkLaunchAllowed(true).setIncludeTextClassification(true).build());
            } else {
                textSelectionSuggestSelection = this.mTextClassifier.get().suggestSelection(this.mTrimmedText, this.mRelativeStart, this.mRelativeEnd, this.mDefaultLocales);
            }
            if (!isDarkLaunchEnabled()) {
                this.mSelectionStart = Math.max(0, textSelectionSuggestSelection.getSelectionStartIndex() + this.mTrimStart);
                this.mSelectionEnd = Math.min(this.mText.length(), textSelectionSuggestSelection.getSelectionEndIndex() + this.mTrimStart);
            }
            return performClassification(textSelectionSuggestSelection);
        }

        public SelectionResult getOriginalSelection() {
            return new SelectionResult(this.mSelectionStart, this.mSelectionEnd, null, null);
        }

        public int getTimeoutDuration() {
            if (this.mInitialized) {
                return this.mViewConfiguration.getSmartSelectionInitializedTimeout();
            }
            return this.mViewConfiguration.getSmartSelectionInitializingTimeout();
        }

        private boolean isDarkLaunchEnabled() {
            return TextClassificationManager.getSettings(this.mContext).isModelDarkLaunchEnabled();
        }

        private SelectionResult performClassification(TextSelection textSelection) {
            TextClassification textClassificationClassifyText;
            if (!Objects.equals(this.mText, this.mLastClassificationText) || this.mSelectionStart != this.mLastClassificationSelectionStart || this.mSelectionEnd != this.mLastClassificationSelectionEnd || !Objects.equals(this.mDefaultLocales, this.mLastClassificationLocales)) {
                this.mLastClassificationText = this.mText;
                this.mLastClassificationSelectionStart = this.mSelectionStart;
                this.mLastClassificationSelectionEnd = this.mSelectionEnd;
                this.mLastClassificationLocales = this.mDefaultLocales;
                trimText();
                if (Linkify.containsUnsupportedCharacters(this.mText)) {
                    EventLog.writeEvent(1397638484, "116321860", -1, "");
                    textClassificationClassifyText = TextClassification.EMPTY;
                } else if (textSelection != null && textSelection.getTextClassification() != null) {
                    textClassificationClassifyText = textSelection.getTextClassification();
                } else if (this.mContext.getApplicationInfo().targetSdkVersion >= 28) {
                    textClassificationClassifyText = this.mTextClassifier.get().classifyText(new TextClassification.Request.Builder(this.mTrimmedText, this.mRelativeStart, this.mRelativeEnd).setDefaultLocales(this.mDefaultLocales).build());
                } else {
                    textClassificationClassifyText = this.mTextClassifier.get().classifyText(this.mTrimmedText, this.mRelativeStart, this.mRelativeEnd, this.mDefaultLocales);
                }
                this.mLastClassificationResult = new SelectionResult(this.mSelectionStart, this.mSelectionEnd, textClassificationClassifyText, textSelection);
            }
            return this.mLastClassificationResult;
        }

        private void trimText() {
            int iMin = Math.min(TextClassificationManager.getSettings(this.mContext).getSmartSelectionTrimDelta(), 240);
            this.mTrimStart = Math.max(0, this.mSelectionStart - iMin);
            this.mTrimmedText = this.mText.subSequence(this.mTrimStart, Math.min(this.mText.length(), this.mSelectionEnd + iMin));
            int i = this.mSelectionStart;
            int i2 = this.mTrimStart;
            this.mRelativeStart = i - i2;
            this.mRelativeEnd = this.mSelectionEnd - i2;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class SelectionResult {
        private final TextClassification mClassification;
        private final int mEnd;
        private final TextSelection mSelection;
        private final int mStart;

        SelectionResult(int i, int i2, TextClassification textClassification, TextSelection textSelection) {
            int[] iArrSortSelectionIndices = SelectionActionModeHelper.sortSelectionIndices(i, i2);
            this.mStart = iArrSortSelectionIndices[0];
            this.mEnd = iArrSortSelectionIndices[1];
            this.mClassification = textClassification;
            this.mSelection = textSelection;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static CharSequence getText(TextView textView) {
        CharSequence text = textView.getText();
        return text != null ? text : "";
    }
}
