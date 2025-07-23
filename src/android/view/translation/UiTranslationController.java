package android.view.translation;

import android.app.Activity;
import android.app.assist.ActivityId;
import android.content.Context;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Dumpable;
import android.util.IntArray;
import android.util.Log;
import android.util.LongSparseArray;
import android.util.Pair;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewRootImpl;
import android.view.WindowManagerGlobal;
import android.view.autofill.AutofillId;
import android.view.translation.TranslationContext;
import android.view.translation.TranslationRequest;
import android.widget.TextView;
import android.widget.TextViewTranslationCallback;
import com.android.internal.protolog.PerfettoProtoLogImpl;
import com.android.internal.util.function.QuadConsumer;
import com.android.internal.util.function.TriConsumer;
import com.android.internal.util.function.pooled.PooledLambda;
import java.io.PrintWriter;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/* loaded from: classes4.dex */
public class UiTranslationController implements Dumpable {
    public static final boolean DEBUG = Log.isLoggable(UiTranslationManager.LOG_TAG, 3);
    public static final String DUMPABLE_NAME = "UiTranslationController";
    private static final String TAG = "UiTranslationController";
    private final Activity mActivity;
    private final Context mContext;
    private int mCurrentState;
    private ArraySet<AutofillId> mLastRequestAutofillIds;
    private final Handler mWorkerHandler;
    private final HandlerThread mWorkerThread;
    private final Object mLock = new Object();
    private final ArrayMap<AutofillId, WeakReference<View>> mViews = new ArrayMap<>();
    private final ArrayMap<Pair<TranslationSpec, TranslationSpec>, Translator> mTranslators = new ArrayMap<>();
    private final ArraySet<AutofillId> mViewsToPadContent = new ArraySet<>();

    public UiTranslationController(Activity activity, Context context) {
        this.mActivity = activity;
        this.mContext = context;
        HandlerThread handlerThread = new HandlerThread("UiTranslationController_" + activity.getComponentName(), -2);
        this.mWorkerThread = handlerThread;
        handlerThread.start();
        this.mWorkerHandler = handlerThread.getThreadHandler();
        activity.addDumpable(this);
    }

    public void updateUiTranslationState(int i, TranslationSpec translationSpec, TranslationSpec translationSpec2, List<AutofillId> list, UiTranslationSpec uiTranslationSpec) {
        String str;
        if (this.mActivity.isDestroyed()) {
            Log.i("UiTranslationController", "Cannot update " + stateToString(i) + " for destroyed " + this.mActivity);
            return;
        }
        boolean isLoggable = Log.isLoggable(UiTranslationManager.LOG_TAG, 3);
        StringBuilder sb = new StringBuilder("updateUiTranslationState state: ");
        sb.append(stateToString(i));
        if (isLoggable) {
            str = ", views: " + list + ", spec: " + uiTranslationSpec;
        } else {
            str = "";
        }
        sb.append(str);
        Log.i("UiTranslationController", sb.toString());
        synchronized (this.mLock) {
            this.mCurrentState = i;
            if (list != null) {
                setLastRequestAutofillIdsLocked(list);
            }
        }
        if (i == 0) {
            if (uiTranslationSpec != null && uiTranslationSpec.shouldPadContentForCompat()) {
                synchronized (this.mLock) {
                    this.mViewsToPadContent.addAll(list);
                }
            }
            Pair pair = new Pair(translationSpec, translationSpec2);
            if (!this.mTranslators.containsKey(pair)) {
                this.mWorkerHandler.sendMessage(PooledLambda.obtainMessage(new QuadConsumer() { // from class: android.view.translation.UiTranslationController$$ExternalSyntheticLambda3
                    @Override // com.android.internal.util.function.QuadConsumer
                    public final void accept(Object obj, Object obj2, Object obj3, Object obj4) {
                        ((UiTranslationController) obj).createTranslatorAndStart((TranslationSpec) obj2, (TranslationSpec) obj3, (List) obj4);
                    }
                }, this, translationSpec, translationSpec2, list));
                return;
            } else {
                onUiTranslationStarted(this.mTranslators.get(pair), list);
                return;
            }
        }
        if (i == 1) {
            runForEachView(new BiConsumer() { // from class: android.view.translation.UiTranslationController$$ExternalSyntheticLambda4
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    ((ViewTranslationCallback) obj2).onHideTranslation((View) obj);
                }
            });
            return;
        }
        if (i == 2) {
            runForEachView(new BiConsumer() { // from class: android.view.translation.UiTranslationController$$ExternalSyntheticLambda5
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    ((ViewTranslationCallback) obj2).onShowTranslation((View) obj);
                }
            });
            return;
        }
        if (i == 3) {
            destroyTranslators();
            runForEachView(new BiConsumer() { // from class: android.view.translation.UiTranslationController$$ExternalSyntheticLambda6
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    ((View) obj).clearTranslationState();
                }
            });
            notifyTranslationFinished(false);
            synchronized (this.mLock) {
                this.mViews.clear();
            }
            return;
        }
        Log.w("UiTranslationController", "onAutoTranslationStateChange(): unknown state: " + i);
    }

    public void onActivityDestroyed() {
        synchronized (this.mLock) {
            Log.i("UiTranslationController", "onActivityDestroyed(): mCurrentState is " + stateToString(this.mCurrentState));
            if (this.mCurrentState != 3) {
                notifyTranslationFinished(true);
            }
            this.mViews.clear();
            destroyTranslators();
            this.mWorkerThread.quitSafely();
        }
    }

    private void notifyTranslationFinished(boolean z) {
        UiTranslationManager uiTranslationManager = (UiTranslationManager) this.mContext.getSystemService(UiTranslationManager.class);
        if (uiTranslationManager != null) {
            uiTranslationManager.onTranslationFinished(z, new ActivityId(this.mActivity.getTaskId(), this.mActivity.getShareableActivityToken()), this.mActivity.getComponentName());
        }
    }

    private void setLastRequestAutofillIdsLocked(List<AutofillId> list) {
        if (this.mLastRequestAutofillIds == null) {
            this.mLastRequestAutofillIds = new ArraySet<>();
        }
        if (this.mLastRequestAutofillIds.size() > 0) {
            this.mLastRequestAutofillIds.clear();
        }
        this.mLastRequestAutofillIds.addAll(list);
    }

    @Override // android.util.Dumpable
    public String getDumpableName() {
        return "UiTranslationController";
    }

    @Override // android.util.Dumpable
    public void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.print("");
        printWriter.println("UiTranslationController:");
        printWriter.print("  ");
        printWriter.print("activity: ");
        printWriter.print(this.mActivity);
        printWriter.print("  ");
        printWriter.print("resumed: ");
        printWriter.println(this.mActivity.isResumed());
        printWriter.print("  ");
        printWriter.print("current state: ");
        printWriter.println(this.mCurrentState);
        int size = this.mTranslators.size();
        printWriter.print("");
        printWriter.print("number translator: ");
        printWriter.println(size);
        for (int i = 0; i < size; i++) {
            printWriter.print("");
            printWriter.print("#");
            printWriter.println(i);
            this.mTranslators.valueAt(i).dump("", printWriter);
            printWriter.println();
        }
        synchronized (this.mLock) {
            int size2 = this.mViews.size();
            printWriter.print("");
            printWriter.print("number views: ");
            printWriter.println(size2);
            for (int i2 = 0; i2 < size2; i2++) {
                printWriter.print("");
                printWriter.print("#");
                printWriter.println(i2);
                AutofillId keyAt = this.mViews.keyAt(i2);
                View view = this.mViews.valueAt(i2).get();
                printWriter.print("  ");
                printWriter.print("autofillId: ");
                printWriter.println(keyAt);
                printWriter.print("  ");
                printWriter.print("view:");
                printWriter.println(view);
            }
            printWriter.print("");
            printWriter.print("padded views: ");
            printWriter.println(this.mViewsToPadContent);
        }
        if (Log.isLoggable(UiTranslationManager.LOG_TAG, 3)) {
            dumpViewByTraversal("", printWriter);
        }
    }

    private void dumpViewByTraversal(String str, PrintWriter printWriter) {
        ArrayList<ViewRootImpl> rootViews = WindowManagerGlobal.getInstance().getRootViews(this.mActivity.getActivityToken());
        printWriter.print(str);
        printWriter.println("Dump views:");
        for (int i = 0; i < rootViews.size(); i++) {
            View view = rootViews.get(i).getView();
            if (view instanceof ViewGroup) {
                dumpChildren((ViewGroup) view, str, printWriter);
            } else {
                dumpViewInfo(view, str, printWriter);
            }
        }
    }

    private void dumpChildren(ViewGroup viewGroup, String str, PrintWriter printWriter) {
        int childCount = viewGroup.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = viewGroup.getChildAt(i);
            if (childAt instanceof ViewGroup) {
                printWriter.print(str);
                printWriter.println("Children: ");
                printWriter.print(str);
                printWriter.print(str);
                printWriter.println(childAt);
                dumpChildren((ViewGroup) childAt, str, printWriter);
            } else {
                printWriter.print(str);
                printWriter.println("End Children: ");
                printWriter.print(str);
                printWriter.print(str);
                printWriter.print(childAt);
                dumpViewInfo(childAt, str, printWriter);
            }
        }
    }

    private void dumpViewInfo(View view, String str, PrintWriter printWriter) {
        boolean contains;
        boolean z;
        AutofillId autofillId = view.getAutofillId();
        printWriter.print(str);
        printWriter.print("autofillId: ");
        printWriter.print(autofillId);
        synchronized (this.mLock) {
            contains = this.mLastRequestAutofillIds.contains(autofillId);
            WeakReference<View> weakReference = this.mViews.get(autofillId);
            z = (weakReference == null || weakReference.get() == null) ? false : true;
        }
        printWriter.print(str);
        printWriter.print("isContainsView: ");
        printWriter.print(z);
        printWriter.print(str);
        printWriter.print("isRequestedView: ");
        printWriter.println(contains);
    }

    public void onTranslationCompleted(TranslationResponse translationResponse) {
        Object valueOf;
        LongSparseArray<ViewTranslationResponse> longSparseArray;
        if (translationResponse == null || translationResponse.getTranslationStatus() != 0) {
            StringBuilder sb = new StringBuilder("Fail result from TranslationService, status=");
            if (translationResponse == null) {
                valueOf = PerfettoProtoLogImpl.NULL_STRING;
            } else {
                valueOf = Integer.valueOf(translationResponse.getTranslationStatus());
            }
            sb.append(valueOf);
            Log.w("UiTranslationController", sb.toString());
            return;
        }
        SparseArray<ViewTranslationResponse> viewTranslationResponses = translationResponse.getViewTranslationResponses();
        SparseArray<ViewTranslationResponse> sparseArray = new SparseArray<>();
        SparseArray<LongSparseArray<ViewTranslationResponse>> sparseArray2 = new SparseArray<>();
        IntArray intArray = new IntArray(1);
        for (int i = 0; i < viewTranslationResponses.size(); i++) {
            ViewTranslationResponse valueAt = viewTranslationResponses.valueAt(i);
            AutofillId autofillId = valueAt.getAutofillId();
            if (intArray.indexOf(autofillId.getViewId()) < 0) {
                intArray.add(autofillId.getViewId());
            }
            if (autofillId.isNonVirtual()) {
                sparseArray.put(viewTranslationResponses.keyAt(i), valueAt);
            } else {
                boolean z = sparseArray2.indexOfKey(autofillId.getViewId()) >= 0;
                if (z) {
                    longSparseArray = sparseArray2.get(autofillId.getViewId());
                } else {
                    longSparseArray = new LongSparseArray<>();
                }
                longSparseArray.put(autofillId.getVirtualChildLongId(), valueAt);
                if (!z) {
                    sparseArray2.put(autofillId.getViewId(), longSparseArray);
                }
            }
        }
        findViewsTraversalByAutofillIds(intArray);
        if (sparseArray.size() > 0) {
            onTranslationCompleted(sparseArray);
        }
        if (sparseArray2.size() > 0) {
            onVirtualViewTranslationCompleted(sparseArray2);
        }
    }

    private void onVirtualViewTranslationCompleted(SparseArray<LongSparseArray<ViewTranslationResponse>> sparseArray) {
        final boolean isLoggable = Log.isLoggable(UiTranslationManager.LOG_TAG, 3);
        if (this.mActivity.isDestroyed()) {
            Log.v("UiTranslationController", "onTranslationCompleted:" + this.mActivity + "is destroyed.");
            return;
        }
        synchronized (this.mLock) {
            if (this.mCurrentState == 3) {
                Log.w("UiTranslationController", "onTranslationCompleted: the translation state is finished now. Skip to show the translated text.");
                return;
            }
            for (int i = 0; i < sparseArray.size(); i++) {
                AutofillId autofillId = new AutofillId(sparseArray.keyAt(i));
                WeakReference<View> weakReference = this.mViews.get(autofillId);
                if (weakReference != null) {
                    final View view = weakReference.get();
                    if (view == null) {
                        Log.w("UiTranslationController", "onTranslationCompleted: the view for autofill id " + autofillId + " may be gone.");
                    } else {
                        LongSparseArray<ViewTranslationResponse> valueAt = sparseArray.valueAt(i);
                        if (isLoggable) {
                            Log.v("UiTranslationController", "onVirtualViewTranslationCompleted: received response for AutofillId " + autofillId);
                        }
                        view.onVirtualViewTranslationResponses(valueAt);
                        if (this.mCurrentState == 1) {
                            return;
                        } else {
                            this.mActivity.runOnUiThread(new Runnable() { // from class: android.view.translation.UiTranslationController$$ExternalSyntheticLambda9
                                @Override // java.lang.Runnable
                                public final void run() {
                                    UiTranslationController.lambda$onVirtualViewTranslationCompleted$3(View.this, isLoggable);
                                }
                            });
                        }
                    }
                }
            }
        }
    }

    static /* synthetic */ void lambda$onVirtualViewTranslationCompleted$3(View view, boolean z) {
        if (view.getViewTranslationCallback() != null) {
            if (view.getViewTranslationCallback() != null) {
                view.getViewTranslationCallback().onShowTranslation(view);
            }
        } else if (z) {
            Log.d("UiTranslationController", view + " doesn't support showing translation because of null ViewTranslationCallback.");
        }
    }

    private void onTranslationCompleted(SparseArray<ViewTranslationResponse> sparseArray) {
        final UiTranslationController uiTranslationController;
        final boolean isLoggable = Log.isLoggable(UiTranslationManager.LOG_TAG, 3);
        if (this.mActivity.isDestroyed()) {
            Log.v("UiTranslationController", "onTranslationCompleted:" + this.mActivity + "is destroyed.");
            return;
        }
        int size = sparseArray.size();
        if (isLoggable) {
            Log.v("UiTranslationController", "onTranslationCompleted: receive " + size + " responses.");
        }
        synchronized (this.mLock) {
            if (this.mCurrentState == 3) {
                Log.w("UiTranslationController", "onTranslationCompleted: the translation state is finished now. Skip to show the translated text.");
                return;
            }
            int i = 0;
            while (i < size) {
                final ViewTranslationResponse valueAt = sparseArray.valueAt(i);
                if (isLoggable) {
                    Log.v("UiTranslationController", "onTranslationCompleted: " + sanitizedViewTranslationResponse(valueAt));
                }
                final AutofillId autofillId = valueAt.getAutofillId();
                if (autofillId == null) {
                    Log.w("UiTranslationController", "No AutofillId is set in ViewTranslationResponse");
                } else {
                    WeakReference<View> weakReference = this.mViews.get(autofillId);
                    if (weakReference != null) {
                        final View view = weakReference.get();
                        if (view == null) {
                            Log.w("UiTranslationController", "onTranslationCompleted: the view for autofill id " + autofillId + " may be gone.");
                        } else {
                            final int i2 = this.mCurrentState;
                            uiTranslationController = this;
                            this.mActivity.runOnUiThread(new Runnable() { // from class: android.view.translation.UiTranslationController$$ExternalSyntheticLambda2
                                @Override // java.lang.Runnable
                                public final void run() {
                                    UiTranslationController.this.lambda$onTranslationCompleted$4(view, valueAt, isLoggable, autofillId, i2);
                                }
                            });
                            i++;
                            this = uiTranslationController;
                        }
                    }
                }
                uiTranslationController = this;
                i++;
                this = uiTranslationController;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onTranslationCompleted$4(View view, ViewTranslationResponse viewTranslationResponse, boolean z, AutofillId autofillId, int i) {
        ViewTranslationCallback viewTranslationCallback = view.getViewTranslationCallback();
        if (view.getViewTranslationResponse() != null && view.getViewTranslationResponse().equals(viewTranslationResponse) && (viewTranslationCallback instanceof TextViewTranslationCallback)) {
            TextViewTranslationCallback textViewTranslationCallback = (TextViewTranslationCallback) viewTranslationCallback;
            if (textViewTranslationCallback.isShowingTranslation() || textViewTranslationCallback.isAnimationRunning()) {
                if (z) {
                    Log.d("UiTranslationController", "Duplicate ViewTranslationResponse for " + autofillId + ". Ignoring.");
                    return;
                }
                return;
            }
        }
        if (viewTranslationCallback == null) {
            if (!(view instanceof TextView)) {
                if (z) {
                    Log.d("UiTranslationController", view + " doesn't support showing translation because of null ViewTranslationCallback.");
                    return;
                }
                return;
            }
            viewTranslationCallback = new TextViewTranslationCallback();
            view.setViewTranslationCallback(viewTranslationCallback);
        }
        viewTranslationCallback.setAnimationDurationMillis(250);
        if (this.mViewsToPadContent.contains(autofillId)) {
            viewTranslationCallback.enableContentPadding();
        }
        view.onViewTranslationResponse(viewTranslationResponse);
        if (i == 1) {
            return;
        }
        viewTranslationCallback.onShowTranslation(view);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void createTranslatorAndStart(TranslationSpec translationSpec, TranslationSpec translationSpec2, List<AutofillId> list) {
        Translator createTranslatorIfNeeded = createTranslatorIfNeeded(translationSpec, translationSpec2);
        if (createTranslatorIfNeeded == null) {
            Log.w("UiTranslationController", "Can not create Translator for sourceSpec:" + translationSpec + " targetSpec:" + translationSpec2);
            return;
        }
        onUiTranslationStarted(createTranslatorIfNeeded, list);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendTranslationRequest(Translator translator, List<ViewTranslationRequest> list) {
        if (list.size() == 0) {
            Log.w("UiTranslationController", "No ViewTranslationRequest was collected.");
            return;
        }
        TranslationRequest build = new TranslationRequest.Builder().setViewTranslationRequests(list).build();
        if (Log.isLoggable(UiTranslationManager.LOG_TAG, 3)) {
            StringBuilder sb = new StringBuilder("sendTranslationRequest:{requests=[");
            for (ViewTranslationRequest viewTranslationRequest : list) {
                sb.append("{request=");
                sb.append(sanitizedViewTranslationRequest(viewTranslationRequest));
                sb.append("}, ");
            }
            Log.d("UiTranslationController", "sendTranslationRequest: " + sb.toString());
        }
        translator.requestUiTranslate(build, new Executor() { // from class: android.view.translation.UiTranslationController$$ExternalSyntheticLambda7
            @Override // java.util.concurrent.Executor
            public final void execute(Runnable runnable) {
                runnable.run();
            }
        }, new Consumer() { // from class: android.view.translation.UiTranslationController$$ExternalSyntheticLambda8
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                UiTranslationController.this.onTranslationCompleted((TranslationResponse) obj);
            }
        });
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void onUiTranslationStarted(final Translator translator, List<AutofillId> list) {
        long[] jArr;
        int i;
        synchronized (this.mLock) {
            SparseIntArray requestVirtualViewChildCount = getRequestVirtualViewChildCount(list);
            final ArrayMap arrayMap = new ArrayMap();
            ArrayMap arrayMap2 = null;
            for (int i2 = 0; i2 < list.size(); i2++) {
                AutofillId autofillId = list.get(i2);
                if (autofillId.isNonVirtual()) {
                    arrayMap.put(autofillId, null);
                } else {
                    if (arrayMap2 == null) {
                        arrayMap2 = new ArrayMap();
                    }
                    AutofillId autofillId2 = new AutofillId(autofillId.getViewId());
                    if (arrayMap.containsKey(autofillId2)) {
                        jArr = (long[]) arrayMap.get(autofillId2);
                        i = ((Integer) arrayMap2.get(autofillId2)).intValue();
                    } else {
                        jArr = new long[requestVirtualViewChildCount.get(autofillId.getViewId())];
                        arrayMap.put(autofillId2, jArr);
                        i = 0;
                    }
                    arrayMap2.put(autofillId2, Integer.valueOf(i + 1));
                    jArr[i] = autofillId.getVirtualChildLongId();
                }
            }
            final ArrayList arrayList = new ArrayList();
            final int[] supportedFormatsLocked = getSupportedFormatsLocked();
            final ArrayList<ViewRootImpl> rootViews = WindowManagerGlobal.getInstance().getRootViews(this.mActivity.getActivityToken());
            final TranslationCapability translationCapability = getTranslationCapability(translator.getTranslationContext());
            this.mActivity.runOnUiThread(new Runnable() { // from class: android.view.translation.UiTranslationController$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    UiTranslationController.this.lambda$onUiTranslationStarted$6(rootViews, arrayMap, supportedFormatsLocked, translationCapability, arrayList, translator);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onUiTranslationStarted$6(ArrayList arrayList, Map map, int[] iArr, TranslationCapability translationCapability, ArrayList arrayList2, Translator translator) {
        for (int i = 0; i < arrayList.size(); i++) {
            View view = ((ViewRootImpl) arrayList.get(i)).getView();
            if (view != null) {
                view.dispatchCreateViewTranslationRequest(map, iArr, translationCapability, arrayList2);
            } else {
                Log.w("UiTranslationController", "onUiTranslationStarted(): rootView is null");
            }
        }
        this.mWorkerHandler.sendMessage(PooledLambda.obtainMessage(new TriConsumer() { // from class: android.view.translation.UiTranslationController$$ExternalSyntheticLambda10
            @Override // com.android.internal.util.function.TriConsumer
            public final void accept(Object obj, Object obj2, Object obj3) {
                ((UiTranslationController) obj).sendTranslationRequest((Translator) obj2, (ArrayList) obj3);
            }
        }, this, translator, arrayList2));
    }

    private SparseIntArray getRequestVirtualViewChildCount(List<AutofillId> list) {
        SparseIntArray sparseIntArray = new SparseIntArray();
        for (int i = 0; i < list.size(); i++) {
            AutofillId autofillId = list.get(i);
            if (!autofillId.isNonVirtual()) {
                int viewId = autofillId.getViewId();
                if (sparseIntArray.indexOfKey(viewId) < 0) {
                    sparseIntArray.put(viewId, 1);
                } else {
                    sparseIntArray.put(viewId, sparseIntArray.get(viewId) + 1);
                }
            }
        }
        return sparseIntArray;
    }

    private int[] getSupportedFormatsLocked() {
        return new int[]{1};
    }

    private TranslationCapability getTranslationCapability(TranslationContext translationContext) {
        return new TranslationCapability(3, translationContext.getSourceSpec(), translationContext.getTargetSpec(), true, 0);
    }

    private void findViewsTraversalByAutofillIds(IntArray intArray) {
        ArrayList<ViewRootImpl> rootViews = WindowManagerGlobal.getInstance().getRootViews(this.mActivity.getActivityToken());
        for (int i = 0; i < rootViews.size(); i++) {
            View view = rootViews.get(i).getView();
            if (view instanceof ViewGroup) {
                findViewsTraversalByAutofillIds((ViewGroup) view, intArray);
            }
            addViewIfNeeded(intArray, view);
        }
    }

    private void findViewsTraversalByAutofillIds(ViewGroup viewGroup, IntArray intArray) {
        int childCount = viewGroup.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = viewGroup.getChildAt(i);
            if (childAt instanceof ViewGroup) {
                findViewsTraversalByAutofillIds((ViewGroup) childAt, intArray);
            }
            addViewIfNeeded(intArray, childAt);
        }
    }

    private void addViewIfNeeded(IntArray intArray, View view) {
        AutofillId autofillId = view.getAutofillId();
        if (autofillId == null || intArray.indexOf(autofillId.getViewId()) < 0 || this.mViews.containsKey(autofillId)) {
            return;
        }
        this.mViews.put(autofillId, new WeakReference<>(view));
    }

    private void runForEachView(final BiConsumer<View, ViewTranslationCallback> biConsumer) {
        synchronized (this.mLock) {
            final boolean isLoggable = Log.isLoggable(UiTranslationManager.LOG_TAG, 3);
            final ArrayMap arrayMap = new ArrayMap(this.mViews);
            if (arrayMap.size() == 0) {
                Log.w("UiTranslationController", "No views can be excuted for runForEachView.");
            }
            this.mActivity.runOnUiThread(new Runnable() { // from class: android.view.translation.UiTranslationController$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    UiTranslationController.lambda$runForEachView$7(ArrayMap.this, isLoggable, biConsumer);
                }
            });
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:20:0x0048, code lost:
    
        android.util.Log.d("UiTranslationController", "View was gone or ViewTranslationCallback for autofillId = " + r6.keyAt(r2));
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    static /* synthetic */ void lambda$runForEachView$7(android.util.ArrayMap r6, boolean r7, java.util.function.BiConsumer r8) {
        /*
            java.lang.String r0 = "UiTranslationController"
            int r1 = r6.size()     // Catch: java.lang.Exception -> L63
            r2 = 0
        L7:
            if (r2 >= r1) goto L76
            java.lang.Object r3 = r6.valueAt(r2)     // Catch: java.lang.Exception -> L63
            java.lang.ref.WeakReference r3 = (java.lang.ref.WeakReference) r3     // Catch: java.lang.Exception -> L63
            java.lang.Object r3 = r3.get()     // Catch: java.lang.Exception -> L63
            android.view.View r3 = (android.view.View) r3     // Catch: java.lang.Exception -> L63
            if (r7 == 0) goto L35
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch: java.lang.Exception -> L63
            r4.<init>()     // Catch: java.lang.Exception -> L63
            java.lang.String r5 = "runForEachView for autofillId = "
            r4.append(r5)     // Catch: java.lang.Exception -> L63
            if (r3 == 0) goto L29
            android.view.autofill.AutofillId r5 = r3.getAutofillId()     // Catch: java.lang.Exception -> L63
            goto L2b
        L29:
            java.lang.String r5 = " null"
        L2b:
            r4.append(r5)     // Catch: java.lang.Exception -> L63
            java.lang.String r4 = r4.toString()     // Catch: java.lang.Exception -> L63
            android.util.Log.d(r0, r4)     // Catch: java.lang.Exception -> L63
        L35:
            if (r3 == 0) goto L46
            android.view.translation.ViewTranslationCallback r4 = r3.getViewTranslationCallback()     // Catch: java.lang.Exception -> L63
            if (r4 != 0) goto L3e
            goto L46
        L3e:
            android.view.translation.ViewTranslationCallback r4 = r3.getViewTranslationCallback()     // Catch: java.lang.Exception -> L63
            r8.accept(r3, r4)     // Catch: java.lang.Exception -> L63
            goto L60
        L46:
            if (r7 == 0) goto L60
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch: java.lang.Exception -> L63
            r3.<init>()     // Catch: java.lang.Exception -> L63
            java.lang.String r4 = "View was gone or ViewTranslationCallback for autofillId = "
            r3.append(r4)     // Catch: java.lang.Exception -> L63
            java.lang.Object r4 = r6.keyAt(r2)     // Catch: java.lang.Exception -> L63
            r3.append(r4)     // Catch: java.lang.Exception -> L63
            java.lang.String r3 = r3.toString()     // Catch: java.lang.Exception -> L63
            android.util.Log.d(r0, r3)     // Catch: java.lang.Exception -> L63
        L60:
            int r2 = r2 + 1
            goto L7
        L63:
            r6 = move-exception
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            java.lang.String r8 = "runForEachView: "
            r7.<init>(r8)
            r7.append(r6)
            java.lang.String r6 = r7.toString()
            android.util.Log.w(r0, r6)
        L76:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.view.translation.UiTranslationController.lambda$runForEachView$7(android.util.ArrayMap, boolean, java.util.function.BiConsumer):void");
    }

    private Translator createTranslatorIfNeeded(TranslationSpec translationSpec, TranslationSpec translationSpec2) {
        TranslationManager translationManager = (TranslationManager) this.mContext.getSystemService(TranslationManager.class);
        if (translationManager == null) {
            Log.e("UiTranslationController", "Can not find TranslationManager when trying to create translator.");
            return null;
        }
        Translator createTranslator = translationManager.createTranslator(new TranslationContext.Builder(translationSpec, translationSpec2).setActivityId(new ActivityId(this.mActivity.getTaskId(), this.mActivity.getShareableActivityToken())).build());
        if (createTranslator != null) {
            this.mTranslators.put(new Pair<>(translationSpec, translationSpec2), createTranslator);
        }
        return createTranslator;
    }

    private void destroyTranslators() {
        synchronized (this.mLock) {
            int size = this.mTranslators.size();
            for (int i = 0; i < size; i++) {
                this.mTranslators.valueAt(i).destroy();
            }
            this.mTranslators.clear();
        }
    }

    public static String stateToString(int i) {
        if (i == 0) {
            return "UI_TRANSLATION_STARTED";
        }
        if (i == 1) {
            return "UI_TRANSLATION_PAUSED";
        }
        if (i == 2) {
            return "UI_TRANSLATION_RESUMED";
        }
        if (i == 3) {
            return "UI_TRANSLATION_FINISHED";
        }
        return "Unknown state (" + i + NavigationBarInflaterView.KEY_CODE_END;
    }

    private static String sanitizedViewTranslationRequest(ViewTranslationRequest viewTranslationRequest) {
        String str;
        StringBuilder sb = new StringBuilder("ViewTranslationRequest:{values=[");
        Iterator<String> it = viewTranslationRequest.getKeys().iterator();
        while (it.hasNext()) {
            TranslationRequestValue value = viewTranslationRequest.getValue(it.next());
            sb.append("{text=");
            if (value.getText() == null) {
                str = PerfettoProtoLogImpl.NULL_STRING;
            } else {
                str = "string[" + value.getText().length() + "]}, ";
            }
            sb.append(str);
        }
        return sb.toString();
    }

    private static String sanitizedViewTranslationResponse(ViewTranslationResponse viewTranslationResponse) {
        StringBuilder sb = new StringBuilder("ViewTranslationResponse:{values=[");
        Iterator<String> it = viewTranslationResponse.getKeys().iterator();
        while (it.hasNext()) {
            TranslationResponseValue value = viewTranslationResponse.getValue(it.next());
            sb.append("{status=");
            sb.append(value.getStatusCode());
            sb.append(", text=");
            CharSequence text = value.getText();
            String str = PerfettoProtoLogImpl.NULL_STRING;
            sb.append(text == null ? PerfettoProtoLogImpl.NULL_STRING : "string[" + value.getText().length() + "], ");
            Bundle bundle = (Bundle) value.getExtras().get(TranslationResponseValue.EXTRA_DEFINITIONS);
            if (bundle != null) {
                sb.append("definitions={");
                for (String str2 : bundle.keySet()) {
                    sb.append(str2);
                    sb.append(":[");
                    for (CharSequence charSequence : bundle.getCharSequenceArray(str2)) {
                        sb.append(charSequence == null ? "null, " : "string[" + charSequence.length() + "], ");
                    }
                    sb.append("], ");
                }
                sb.append("}");
            }
            sb.append("transliteration=");
            if (value.getTransliteration() != null) {
                str = "string[" + value.getTransliteration().length() + "]}, ";
            }
            sb.append(str);
        }
        return sb.toString();
    }
}
