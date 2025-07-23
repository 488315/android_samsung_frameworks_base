package android.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.HardwareRenderer;
import android.graphics.Picture;
import android.graphics.RecordingCanvas;
import android.graphics.Rect;
import android.graphics.RenderNode;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Debug;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewDebug;
import android.view.ViewGroup;
import com.android.internal.protolog.PerfettoProtoLogImpl;
import com.android.internal.util.Preconditions;
import com.samsung.android.graphics.imagefilter.ShaderAssembler;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.stream.Stream;
import libcore.util.HexEncoding;

/* loaded from: classes4.dex */
public class ViewDebug {
    private static final int CAPTURE_TIMEOUT = 6000;
    public static final boolean DEBUG_DRAG = false;
    public static final boolean DEBUG_POSITIONING = false;
    private static final String REMOTE_COMMAND_CAPTURE = "CAPTURE";
    private static final String REMOTE_COMMAND_CAPTURE_LAYERS = "CAPTURE_LAYERS";
    private static final String REMOTE_COMMAND_DUMP = "DUMP";
    public static final String REMOTE_COMMAND_DUMP_ENCODED = "DUMP_ENCODED";
    private static final String REMOTE_COMMAND_DUMP_THEME = "DUMP_THEME";
    private static final String REMOTE_COMMAND_INVALIDATE = "INVALIDATE";
    private static final String REMOTE_COMMAND_INVOKE_METHOD = "INVOKE_METHOD";
    private static final String REMOTE_COMMAND_OUTPUT_DISPLAYLIST = "OUTPUT_DISPLAYLIST";
    private static final String REMOTE_COMMAND_REQUEST_LAYOUT = "REQUEST_LAYOUT";
    private static final String REMOTE_PROFILE = "PROFILE";
    private static final char SIG_ARRAY = '[';
    private static final char SIG_BOOLEAN = 'Z';
    private static final char SIG_BYTE = 'B';
    private static final char SIG_CHAR = 'C';
    private static final char SIG_DOUBLE = 'D';
    private static final char SIG_FLOAT = 'F';
    private static final char SIG_INT = 'I';
    private static final char SIG_LONG = 'J';
    private static final char SIG_SHORT = 'S';
    private static final char SIG_STRING = 'R';
    private static final char SIG_VOID = 'V';
    private static final String TAG = "ViewDebug";

    @Deprecated
    public static final boolean TRACE_HIERARCHY = false;

    @Deprecated
    public static final boolean TRACE_RECYCLER = false;
    private static HashMap<Class<?>, PropertyInfo<CapturedViewProperty, ?>[]> sCapturedViewProperties;
    private static HashMap<Class<?>, PropertyInfo<ExportedProperty, ?>[]> sExportProperties;

    public interface CanvasProvider {
        Bitmap createBitmap();

        Canvas getCanvas(View view, int i, int i2);
    }

    @Target({ElementType.FIELD, ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface CapturedViewProperty {
        boolean retrieveReturn() default false;
    }

    @Target({ElementType.FIELD, ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface ExportedProperty {
        String category() default "";

        boolean deepExport() default false;

        FlagToString[] flagMapping() default {};

        boolean formatToHexString() default false;

        boolean hasAdjacentMapping() default false;

        IntToString[] indexMapping() default {};

        IntToString[] mapping() default {};

        String prefix() default "";

        boolean resolveId() default false;
    }

    @Target({ElementType.TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface FlagToString {
        int equals();

        int mask();

        String name();

        boolean outputIf() default true;
    }

    public interface HierarchyHandler {
        void dumpViewHierarchyWithProperties(BufferedWriter bufferedWriter, int i);

        View findHierarchyView(String str, int i);
    }

    @Deprecated
    public enum HierarchyTraceType {
        INVALIDATE,
        INVALIDATE_CHILD,
        INVALIDATE_CHILD_IN_PARENT,
        REQUEST_LAYOUT,
        ON_LAYOUT,
        ON_MEASURE,
        DRAW,
        BUILD_CACHE
    }

    @Target({ElementType.TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface IntToString {
        int from();

        String to();
    }

    @Deprecated
    public enum RecyclerTraceType {
        NEW_VIEW,
        BIND_VIEW,
        RECYCLE_FROM_ACTIVE_HEAP,
        RECYCLE_FROM_SCRAP_HEAP,
        MOVE_TO_SCRAP_HEAP,
        MOVE_FROM_ACTIVE_TO_SCRAP_HEAP
    }

    interface ViewOperation {
        default void pre() {
        }

        void run();
    }

    static /* synthetic */ boolean lambda$convertToPropertyInfos$8(Object obj) {
        return obj != null;
    }

    @Deprecated
    public static void startHierarchyTracing(String str, View view) {
    }

    @Deprecated
    public static void startRecyclerTracing(String str, View view) {
    }

    @Deprecated
    public static void stopHierarchyTracing() {
    }

    @Deprecated
    public static void stopRecyclerTracing() {
    }

    @Deprecated
    public static void trace(View view, HierarchyTraceType hierarchyTraceType) {
    }

    @Deprecated
    public static void trace(View view, RecyclerTraceType recyclerTraceType, int... iArr) {
    }

    /* JADX INFO: Access modifiers changed from: private */
    static abstract class PropertyInfo<T extends Annotation, R extends AccessibleObject & Member> {
        public final R member;
        public final String name;
        public final T property;
        public final Class<?> returnType;
        public String entrySuffix = "";
        public String valueSuffix = "";

        public abstract Object invoke(Object obj) throws Exception;

        PropertyInfo(Class<T> cls, R r, Class<?> cls2) {
            this.member = r;
            this.name = r.getName();
            this.property = (T) r.getAnnotation(cls);
            this.returnType = cls2;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public static <T extends Annotation> PropertyInfo<T, ?> forMethod(Method method, Class<T> cls) {
            try {
                if (method.getReturnType() == Void.class) {
                    return null;
                }
                if (method.getParameterTypes().length != 0 || !method.isAnnotationPresent(cls)) {
                    return null;
                }
                method.setAccessible(true);
                MethodPI methodPI = new MethodPI(method, cls);
                methodPI.entrySuffix = "()";
                methodPI.valueSuffix = NavigationBarInflaterView.GRAVITY_SEPARATOR;
                return methodPI;
            } catch (NoClassDefFoundError unused) {
                return null;
            }
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public static <T extends Annotation> PropertyInfo<T, ?> forField(Field field, Class<T> cls) {
            if (!field.isAnnotationPresent(cls)) {
                return null;
            }
            field.setAccessible(true);
            return new FieldPI(field, cls);
        }
    }

    private static class MethodPI<T extends Annotation> extends PropertyInfo<T, Method> {
        MethodPI(Method method, Class<T> cls) {
            super(cls, method, method.getReturnType());
        }

        @Override // android.view.ViewDebug.PropertyInfo
        public Object invoke(Object obj) throws Exception {
            return ((Method) this.member).invoke(obj, null);
        }
    }

    private static class FieldPI<T extends Annotation> extends PropertyInfo<T, Field> {
        FieldPI(Field field, Class<T> cls) {
            super(cls, field, field.getType());
        }

        @Override // android.view.ViewDebug.PropertyInfo
        public Object invoke(Object obj) throws Exception {
            return ((Field) this.member).get(obj);
        }
    }

    public static long getViewInstanceCount() {
        return Debug.countInstancesOfClass(View.class);
    }

    public static long getViewRootImplCount() {
        return Debug.countInstancesOfClass(ViewRootImpl.class);
    }

    static void dispatchCommand(View view, String str, String str2, OutputStream outputStream) throws IOException {
        View rootView = view.getRootView();
        if (REMOTE_COMMAND_DUMP.equalsIgnoreCase(str)) {
            dump(rootView, false, true, outputStream);
            return;
        }
        if (REMOTE_COMMAND_DUMP_THEME.equalsIgnoreCase(str)) {
            dumpTheme(rootView, outputStream);
            return;
        }
        if (REMOTE_COMMAND_DUMP_ENCODED.equalsIgnoreCase(str)) {
            dumpEncoded(rootView, outputStream);
            return;
        }
        if (REMOTE_COMMAND_CAPTURE_LAYERS.equalsIgnoreCase(str)) {
            captureLayers(rootView, new DataOutputStream(outputStream));
            return;
        }
        String[] split = str2.split(" ");
        if (REMOTE_COMMAND_CAPTURE.equalsIgnoreCase(str)) {
            capture(rootView, outputStream, split[0]);
            return;
        }
        if (REMOTE_COMMAND_OUTPUT_DISPLAYLIST.equalsIgnoreCase(str)) {
            outputDisplayList(rootView, split[0]);
            return;
        }
        if (REMOTE_COMMAND_INVALIDATE.equalsIgnoreCase(str)) {
            invalidate(rootView, split[0]);
            return;
        }
        if (REMOTE_COMMAND_REQUEST_LAYOUT.equalsIgnoreCase(str)) {
            requestLayout(rootView, split[0]);
        } else if (REMOTE_PROFILE.equalsIgnoreCase(str)) {
            profile(rootView, outputStream, split[0]);
        } else if (REMOTE_COMMAND_INVOKE_METHOD.equals(str)) {
            invokeViewMethod(rootView, outputStream, split);
        }
    }

    public static View findView(View view, String str) {
        if (str.indexOf(64) != -1) {
            String[] split = str.split("@");
            String str2 = split[0];
            int parseLong = (int) Long.parseLong(split[1], 16);
            View rootView = view.getRootView();
            if (rootView instanceof ViewGroup) {
                return findView((ViewGroup) rootView, str2, parseLong);
            }
            return null;
        }
        return view.getRootView().findViewById(view.getResources().getIdentifier(str, null, null));
    }

    private static void invalidate(View view, String str) {
        View findView = findView(view, str);
        if (findView != null) {
            findView.postInvalidate();
        }
    }

    private static void requestLayout(View view, String str) {
        final View findView = findView(view, str);
        if (findView != null) {
            view.post(new Runnable() { // from class: android.view.ViewDebug.1
                @Override // java.lang.Runnable
                public void run() {
                    View.this.requestLayout();
                }
            });
        }
    }

    private static void profile(View view, OutputStream outputStream, String str) throws IOException {
        View findView = findView(view, str);
        BufferedWriter bufferedWriter = null;
        try {
            try {
                BufferedWriter bufferedWriter2 = new BufferedWriter(new OutputStreamWriter(outputStream), 32768);
                try {
                    if (findView != null) {
                        profileViewAndChildren(findView, bufferedWriter2);
                    } else {
                        bufferedWriter2.write("-1 -1 -1");
                        bufferedWriter2.newLine();
                    }
                    bufferedWriter2.write("DONE.");
                    bufferedWriter2.newLine();
                    bufferedWriter2.close();
                } catch (Exception e) {
                    e = e;
                    bufferedWriter = bufferedWriter2;
                    Log.w("View", "Problem profiling the view:", e);
                    if (bufferedWriter != null) {
                        bufferedWriter.close();
                    }
                } catch (Throwable th) {
                    th = th;
                    bufferedWriter = bufferedWriter2;
                    if (bufferedWriter != null) {
                        bufferedWriter.close();
                    }
                    throw th;
                }
            } catch (Exception e2) {
                e = e2;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public static void profileViewAndChildren(View view, BufferedWriter bufferedWriter) throws IOException {
        profileViewAndChildren(view, RenderNode.create(TAG, null), bufferedWriter, true);
    }

    private static void profileViewAndChildren(View view, RenderNode renderNode, BufferedWriter bufferedWriter, boolean z) throws IOException {
        long profileViewMeasure = (z || (view.mPrivateFlags & 2048) != 0) ? profileViewMeasure(view) : 0L;
        long profileViewLayout = (z || (view.mPrivateFlags & 8192) != 0) ? profileViewLayout(view) : 0L;
        long profileViewDraw = (!z && view.willNotDraw() && (view.mPrivateFlags & 32) == 0) ? 0L : profileViewDraw(view, renderNode);
        bufferedWriter.write(String.valueOf(profileViewMeasure));
        bufferedWriter.write(32);
        bufferedWriter.write(String.valueOf(profileViewLayout));
        bufferedWriter.write(32);
        bufferedWriter.write(String.valueOf(profileViewDraw));
        bufferedWriter.newLine();
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            int childCount = viewGroup.getChildCount();
            for (int i = 0; i < childCount; i++) {
                profileViewAndChildren(viewGroup.getChildAt(i), renderNode, bufferedWriter, false);
            }
        }
    }

    private static long profileViewMeasure(final View view) {
        return profileViewOperation(view, new ViewOperation() { // from class: android.view.ViewDebug.2
            @Override // android.view.ViewDebug.ViewOperation
            public void pre() {
                forceLayout(View.this);
            }

            private void forceLayout(View view2) {
                view2.forceLayout();
                if (view2 instanceof ViewGroup) {
                    ViewGroup viewGroup = (ViewGroup) view2;
                    int childCount = viewGroup.getChildCount();
                    for (int i = 0; i < childCount; i++) {
                        forceLayout(viewGroup.getChildAt(i));
                    }
                }
            }

            @Override // android.view.ViewDebug.ViewOperation
            public void run() {
                View view2 = View.this;
                view2.measure(view2.mOldWidthMeasureSpec, View.this.mOldHeightMeasureSpec);
            }
        });
    }

    private static long profileViewLayout(final View view) {
        return profileViewOperation(view, new ViewOperation() { // from class: android.view.ViewDebug$$ExternalSyntheticLambda0
            @Override // android.view.ViewDebug.ViewOperation
            public final void run() {
                r0.layout(r0.mLeft, r0.mTop, r0.mRight, View.this.mBottom);
            }
        });
    }

    private static long profileViewDraw(final View view, RenderNode renderNode) {
        DisplayMetrics displayMetrics = view.getResources().getDisplayMetrics();
        if (displayMetrics == null) {
            return 0L;
        }
        if (view.isHardwareAccelerated()) {
            final RecordingCanvas beginRecording = renderNode.beginRecording(displayMetrics.widthPixels, displayMetrics.heightPixels);
            try {
                return profileViewOperation(view, new ViewOperation() { // from class: android.view.ViewDebug$$ExternalSyntheticLambda1
                    @Override // android.view.ViewDebug.ViewOperation
                    public final void run() {
                        View.this.draw(beginRecording);
                    }
                });
            } finally {
                renderNode.endRecording();
            }
        }
        Bitmap createBitmap = Bitmap.createBitmap(displayMetrics, displayMetrics.widthPixels, displayMetrics.heightPixels, Bitmap.Config.RGB_565);
        final Canvas canvas = new Canvas(createBitmap);
        try {
            return profileViewOperation(view, new ViewOperation() { // from class: android.view.ViewDebug$$ExternalSyntheticLambda2
                @Override // android.view.ViewDebug.ViewOperation
                public final void run() {
                    View.this.draw(canvas);
                }
            });
        } finally {
            canvas.setBitmap(null);
            createBitmap.recycle();
        }
    }

    private static long profileViewOperation(View view, final ViewOperation viewOperation) {
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        final long[] jArr = new long[1];
        view.post(new Runnable() { // from class: android.view.ViewDebug$$ExternalSyntheticLambda9
            @Override // java.lang.Runnable
            public final void run() {
                ViewDebug.lambda$profileViewOperation$3(ViewDebug.ViewOperation.this, jArr, countDownLatch);
            }
        });
        try {
            if (countDownLatch.await(6000L, TimeUnit.MILLISECONDS)) {
                return jArr[0];
            }
            Log.w("View", "Could not complete the profiling of the view " + view);
            return -1L;
        } catch (InterruptedException unused) {
            Log.w("View", "Could not complete the profiling of the view " + view);
            Thread.currentThread().interrupt();
            return -1L;
        }
    }

    static /* synthetic */ void lambda$profileViewOperation$3(ViewOperation viewOperation, long[] jArr, CountDownLatch countDownLatch) {
        try {
            viewOperation.pre();
            long threadCpuTimeNanos = Debug.threadCpuTimeNanos();
            viewOperation.run();
            jArr[0] = Debug.threadCpuTimeNanos() - threadCpuTimeNanos;
        } finally {
            countDownLatch.countDown();
        }
    }

    public static void captureLayers(View view, DataOutputStream dataOutputStream) throws IOException {
        try {
            Rect rect = new Rect();
            view.mAttachInfo.mViewRootImpl.getDisplayFrame(rect);
            dataOutputStream.writeInt(rect.width());
            dataOutputStream.writeInt(rect.height());
            captureViewLayer(view, dataOutputStream, true);
            dataOutputStream.write(2);
        } finally {
            dataOutputStream.close();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r8v1 */
    /* JADX WARN: Type inference failed for: r8v2, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r8v3 */
    private static void captureViewLayer(View view, DataOutputStream dataOutputStream, boolean z) throws IOException {
        ?? r8 = (view.getVisibility() == 0 && z) ? 1 : 0;
        if ((view.mPrivateFlags & 128) != 128) {
            int id = view.getId();
            String simpleName = view.getClass().getSimpleName();
            if (id != -1) {
                simpleName = resolveId(view.getContext(), id).toString();
            }
            dataOutputStream.write(1);
            dataOutputStream.writeUTF(simpleName);
            dataOutputStream.writeByte(r8);
            int[] iArr = new int[2];
            view.getLocationInWindow(iArr);
            dataOutputStream.writeInt(iArr[0]);
            dataOutputStream.writeInt(iArr[1]);
            dataOutputStream.flush();
            Bitmap performViewCapture = performViewCapture(view, true);
            if (performViewCapture != null) {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(performViewCapture.getWidth() * performViewCapture.getHeight() * 2);
                performViewCapture.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                dataOutputStream.writeInt(byteArrayOutputStream.size());
                byteArrayOutputStream.writeTo(dataOutputStream);
            }
            dataOutputStream.flush();
        }
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            int childCount = viewGroup.getChildCount();
            for (int i = 0; i < childCount; i++) {
                captureViewLayer(viewGroup.getChildAt(i), dataOutputStream, r8);
            }
        }
        if (view.mOverlay != null) {
            captureViewLayer(view.getOverlay().mOverlayViewGroup, dataOutputStream, r8);
        }
    }

    private static void outputDisplayList(View view, String str) throws IOException {
        View findView = findView(view, str);
        findView.getViewRootImpl().outputDisplayList(findView);
    }

    public static void outputDisplayList(View view, View view2) {
        view.getViewRootImpl().outputDisplayList(view2);
    }

    private static class PictureCallbackHandler implements AutoCloseable, HardwareRenderer.PictureCapturedCallback, Runnable {
        private final Function<Picture, Boolean> mCallback;
        private final Executor mExecutor;
        private final ReentrantLock mLock;
        private final ArrayDeque<Picture> mQueue;
        private Thread mRenderThread;
        private final HardwareRenderer mRenderer;
        private boolean mStopListening;

        private PictureCallbackHandler(HardwareRenderer hardwareRenderer, Function<Picture, Boolean> function, Executor executor) {
            this.mLock = new ReentrantLock(false);
            this.mQueue = new ArrayDeque<>(3);
            this.mRenderer = hardwareRenderer;
            this.mCallback = function;
            this.mExecutor = executor;
            hardwareRenderer.setPictureCaptureCallback(this);
        }

        @Override // java.lang.AutoCloseable
        public void close() {
            this.mLock.lock();
            this.mStopListening = true;
            this.mLock.unlock();
            this.mRenderer.setPictureCaptureCallback(null);
        }

        @Override // android.graphics.HardwareRenderer.PictureCapturedCallback
        public void onPictureCaptured(Picture picture) {
            this.mLock.lock();
            if (this.mStopListening) {
                this.mLock.unlock();
                this.mRenderer.setPictureCaptureCallback(null);
                return;
            }
            if (this.mRenderThread == null) {
                this.mRenderThread = Thread.currentThread();
            }
            Picture removeLast = this.mQueue.size() == 3 ? this.mQueue.removeLast() : null;
            this.mQueue.add(picture);
            this.mLock.unlock();
            if (removeLast == null) {
                this.mExecutor.execute(this);
            } else {
                removeLast.close();
            }
        }

        @Override // java.lang.Runnable
        public void run() {
            this.mLock.lock();
            Picture poll = this.mQueue.poll();
            boolean z = this.mStopListening;
            this.mLock.unlock();
            if (Thread.currentThread() == this.mRenderThread) {
                close();
                throw new IllegalStateException("ViewDebug#startRenderingCommandsCapture must be given an executor that invokes asynchronously");
            }
            if (z) {
                poll.close();
            } else {
                if (this.mCallback.apply(poll).booleanValue()) {
                    return;
                }
                close();
            }
        }
    }

    @Deprecated
    public static AutoCloseable startRenderingCommandsCapture(View view, Executor executor, Function<Picture, Boolean> function) {
        View.AttachInfo attachInfo = view.mAttachInfo;
        if (attachInfo == null) {
            throw new IllegalArgumentException("Given view isn't attached");
        }
        if (attachInfo.mHandler.getLooper() != Looper.myLooper()) {
            throw new IllegalStateException("Called on the wrong thread. Must be called on the thread that owns the given View");
        }
        ThreadedRenderer threadedRenderer = attachInfo.mThreadedRenderer;
        if (threadedRenderer != null) {
            return new PictureCallbackHandler(threadedRenderer, function, executor);
        }
        return null;
    }

    private static class StreamingPictureCallbackHandler implements AutoCloseable, HardwareRenderer.PictureCapturedCallback, Runnable {
        private final Callable<OutputStream> mCallback;
        private final Executor mExecutor;
        private final ReentrantLock mLock;
        private final ArrayDeque<Picture> mQueue;
        private Thread mRenderThread;
        private final HardwareRenderer mRenderer;
        private boolean mStopListening;

        private StreamingPictureCallbackHandler(HardwareRenderer hardwareRenderer, Callable<OutputStream> callable, Executor executor) {
            this.mLock = new ReentrantLock(false);
            this.mQueue = new ArrayDeque<>(3);
            this.mRenderer = hardwareRenderer;
            this.mCallback = callable;
            this.mExecutor = executor;
            hardwareRenderer.setPictureCaptureCallback(this);
        }

        @Override // java.lang.AutoCloseable
        public void close() {
            this.mLock.lock();
            this.mStopListening = true;
            this.mLock.unlock();
            this.mRenderer.setPictureCaptureCallback(null);
        }

        @Override // android.graphics.HardwareRenderer.PictureCapturedCallback
        public void onPictureCaptured(Picture picture) {
            boolean z;
            this.mLock.lock();
            if (this.mStopListening) {
                this.mLock.unlock();
                this.mRenderer.setPictureCaptureCallback(null);
                return;
            }
            if (this.mRenderThread == null) {
                this.mRenderThread = Thread.currentThread();
            }
            if (this.mQueue.size() == 3) {
                this.mQueue.removeLast();
                z = false;
            } else {
                z = true;
            }
            this.mQueue.add(picture);
            this.mLock.unlock();
            if (z) {
                this.mExecutor.execute(this);
            }
        }

        @Override // java.lang.Runnable
        public void run() {
            OutputStream outputStream;
            this.mLock.lock();
            Picture poll = this.mQueue.poll();
            boolean z = this.mStopListening;
            this.mLock.unlock();
            if (Thread.currentThread() == this.mRenderThread) {
                close();
                throw new IllegalStateException("ViewDebug#startRenderingCommandsCapture must be given an executor that invokes asynchronously");
            }
            if (z) {
                return;
            }
            try {
                outputStream = this.mCallback.call();
            } catch (Exception e) {
                Log.w(ViewDebug.TAG, "Aborting rendering commands capture because callback threw exception", e);
                outputStream = null;
            }
            if (outputStream != null) {
                try {
                    poll.writeToStream(outputStream);
                    outputStream.flush();
                    return;
                } catch (IOException e2) {
                    Log.w(ViewDebug.TAG, "Aborting rendering commands capture due to IOException writing to output stream", e2);
                    return;
                }
            }
            close();
        }
    }

    public static AutoCloseable startRenderingCommandsCapture(View view, Executor executor, Callable<OutputStream> callable) {
        View.AttachInfo attachInfo = view.mAttachInfo;
        if (attachInfo == null) {
            throw new IllegalArgumentException("Given view isn't attached");
        }
        if (attachInfo.mHandler.getLooper() != Looper.myLooper()) {
            throw new IllegalStateException("Called on the wrong thread. Must be called on the thread that owns the given View");
        }
        ThreadedRenderer threadedRenderer = attachInfo.mThreadedRenderer;
        if (threadedRenderer != null) {
            return new StreamingPictureCallbackHandler(threadedRenderer, callable, executor);
        }
        return null;
    }

    private static void capture(View view, OutputStream outputStream, String str) throws IOException {
        capture(view, outputStream, findView(view, str));
    }

    public static void capture(View view, OutputStream outputStream, View view2) throws IOException {
        BufferedOutputStream bufferedOutputStream;
        Throwable th;
        Bitmap performViewCapture = performViewCapture(view2, false);
        if (performViewCapture == null) {
            Log.w("View", "Failed to create capture bitmap!");
            performViewCapture = Bitmap.createBitmap(view.getResources().getDisplayMetrics(), 1, 1, Bitmap.Config.ARGB_8888);
        }
        try {
            bufferedOutputStream = new BufferedOutputStream(outputStream, 32768);
        } catch (Throwable th2) {
            bufferedOutputStream = null;
            th = th2;
        }
        try {
            performViewCapture.compress(Bitmap.CompressFormat.PNG, 100, bufferedOutputStream);
            bufferedOutputStream.flush();
            bufferedOutputStream.close();
            performViewCapture.recycle();
        } catch (Throwable th3) {
            th = th3;
            if (bufferedOutputStream != null) {
                bufferedOutputStream.close();
            }
            performViewCapture.recycle();
            throw th;
        }
    }

    private static Bitmap performViewCapture(final View view, final boolean z) {
        if (view == null) {
            return null;
        }
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        final Bitmap[] bitmapArr = new Bitmap[1];
        view.post(new Runnable() { // from class: android.view.ViewDebug$$ExternalSyntheticLambda10
            @Override // java.lang.Runnable
            public final void run() {
                ViewDebug.lambda$performViewCapture$4(View.this, bitmapArr, z, countDownLatch);
            }
        });
        try {
            countDownLatch.await(6000L, TimeUnit.MILLISECONDS);
            return bitmapArr[0];
        } catch (InterruptedException unused) {
            Log.w("View", "Could not complete the capture of the view " + view);
            Thread.currentThread().interrupt();
            return null;
        }
    }

    static /* synthetic */ void lambda$performViewCapture$4(View view, Bitmap[] bitmapArr, boolean z, CountDownLatch countDownLatch) {
        try {
            bitmapArr[0] = view.createSnapshot(view.isHardwareAccelerated() ? new HardwareCanvasProvider() : new SoftwareCanvasProvider(), z);
        } catch (OutOfMemoryError unused) {
            Log.w("View", "Out of memory for bitmap");
        } finally {
            countDownLatch.countDown();
        }
    }

    @Deprecated
    public static void dump(View view, boolean z, boolean z2, OutputStream outputStream) throws IOException {
        Throwable th;
        Exception exc;
        BufferedWriter bufferedWriter = null;
        try {
            try {
                BufferedWriter bufferedWriter2 = new BufferedWriter(new OutputStreamWriter(outputStream, "utf-8"), 32768);
                try {
                    View rootView = view.getRootView();
                    if (rootView instanceof ViewGroup) {
                        ViewGroup viewGroup = (ViewGroup) rootView;
                        dumpViewHierarchy(viewGroup.getContext(), viewGroup, bufferedWriter2, 0, z, z2);
                    }
                    bufferedWriter2.write("DONE.");
                    bufferedWriter2.newLine();
                    bufferedWriter2.close();
                } catch (Exception e) {
                    exc = e;
                    bufferedWriter = bufferedWriter2;
                    Log.w("View", "Problem dumping the view:", exc);
                    if (bufferedWriter != null) {
                        bufferedWriter.close();
                    }
                } catch (Throwable th2) {
                    th = th2;
                    bufferedWriter = bufferedWriter2;
                    if (bufferedWriter != null) {
                        bufferedWriter.close();
                        throw th;
                    }
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
            }
        } catch (Exception e2) {
            exc = e2;
        }
    }

    public static void dumpv2(final View view, ByteArrayOutputStream byteArrayOutputStream) throws InterruptedException {
        final ViewHierarchyEncoder viewHierarchyEncoder = new ViewHierarchyEncoder(byteArrayOutputStream);
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        view.post(new Runnable() { // from class: android.view.ViewDebug.3
            @Override // java.lang.Runnable
            public void run() {
                ViewHierarchyEncoder.this.addProperty("window:left", view.mAttachInfo.mWindowLeft);
                ViewHierarchyEncoder.this.addProperty("window:top", view.mAttachInfo.mWindowTop);
                view.encode(ViewHierarchyEncoder.this);
                countDownLatch.countDown();
            }
        });
        countDownLatch.await(2L, TimeUnit.SECONDS);
        viewHierarchyEncoder.endStream();
    }

    private static void dumpEncoded(View view, OutputStream outputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ViewHierarchyEncoder viewHierarchyEncoder = new ViewHierarchyEncoder(byteArrayOutputStream);
        viewHierarchyEncoder.setUserPropertiesEnabled(false);
        viewHierarchyEncoder.addProperty("window:left", view.mAttachInfo.mWindowLeft);
        viewHierarchyEncoder.addProperty("window:top", view.mAttachInfo.mWindowTop);
        view.encode(viewHierarchyEncoder);
        viewHierarchyEncoder.endStream();
        outputStream.write(byteArrayOutputStream.toByteArray());
    }

    public static void dumpTheme(View view, OutputStream outputStream) throws IOException {
        BufferedWriter bufferedWriter = null;
        try {
            try {
                BufferedWriter bufferedWriter2 = new BufferedWriter(new OutputStreamWriter(outputStream, "utf-8"), 32768);
                try {
                    String[] styleAttributesDump = getStyleAttributesDump(view.getContext().getResources(), view.getContext().getTheme());
                    if (styleAttributesDump != null) {
                        for (int i = 0; i < styleAttributesDump.length; i += 2) {
                            if (styleAttributesDump[i] != null) {
                                bufferedWriter2.write(styleAttributesDump[i] + ShaderAssembler.NEWLINE);
                                bufferedWriter2.write(styleAttributesDump[i + 1] + ShaderAssembler.NEWLINE);
                            }
                        }
                    }
                    bufferedWriter2.write("DONE.");
                    bufferedWriter2.newLine();
                    bufferedWriter2.close();
                } catch (Exception e) {
                    e = e;
                    bufferedWriter = bufferedWriter2;
                    Log.w("View", "Problem dumping View Theme:", e);
                    if (bufferedWriter != null) {
                        bufferedWriter.close();
                    }
                } catch (Throwable th) {
                    th = th;
                    bufferedWriter = bufferedWriter2;
                    if (bufferedWriter != null) {
                        bufferedWriter.close();
                    }
                    throw th;
                }
            } catch (Exception e2) {
                e = e2;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    private static String[] getStyleAttributesDump(Resources resources, Resources.Theme theme) {
        TypedValue typedValue = new TypedValue();
        int[] allAttributes = theme.getAllAttributes();
        String[] strArr = new String[allAttributes.length * 2];
        int i = 0;
        for (int i2 : allAttributes) {
            try {
                strArr[i] = resources.getResourceName(i2);
                strArr[i + 1] = theme.resolveAttribute(i2, typedValue, true) ? typedValue.coerceToString().toString() : PerfettoProtoLogImpl.NULL_STRING;
                int i3 = i + 2;
                try {
                    if (typedValue.type == 1) {
                        strArr[i + 1] = resources.getResourceName(typedValue.resourceId);
                    }
                } catch (Resources.NotFoundException unused) {
                }
                i = i3;
            } catch (Resources.NotFoundException unused2) {
            }
        }
        return strArr;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static View findView(ViewGroup viewGroup, String str, int i) {
        View findHierarchyView;
        View findView;
        if (isRequestedView(viewGroup, str, i)) {
            return viewGroup;
        }
        int childCount = viewGroup.getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            View childAt = viewGroup.getChildAt(i2);
            if (childAt instanceof ViewGroup) {
                View findView2 = findView((ViewGroup) childAt, str, i);
                if (findView2 != null) {
                    return findView2;
                }
            } else if (isRequestedView(childAt, str, i)) {
                return childAt;
            }
            if (childAt.mOverlay != null && (findView = findView(childAt.mOverlay.mOverlayViewGroup, str, i)) != null) {
                return findView;
            }
            if ((childAt instanceof HierarchyHandler) && (findHierarchyView = ((HierarchyHandler) childAt).findHierarchyView(str, i)) != null) {
                return findHierarchyView;
            }
        }
        return null;
    }

    private static boolean isRequestedView(View view, String str, int i) {
        if (view.hashCode() != i) {
            return false;
        }
        String name = view.getClass().getName();
        if (str.equals("ViewOverlay")) {
            return name.equals("android.view.ViewOverlay$OverlayViewGroup");
        }
        return str.equals(name);
    }

    private static void dumpViewHierarchy(final Context context, final ViewGroup viewGroup, final BufferedWriter bufferedWriter, final int i, final boolean z, final boolean z2) {
        cacheExportedProperties(viewGroup.getClass());
        if (!z) {
            cacheExportedPropertiesForChildren(viewGroup);
        }
        Handler handler = viewGroup.getHandler();
        if (handler == null) {
            handler = new Handler(Looper.getMainLooper());
        }
        if (handler.getLooper() == Looper.myLooper()) {
            dumpViewHierarchyOnUIThread(context, viewGroup, bufferedWriter, i, z, z2);
            return;
        }
        FutureTask futureTask = new FutureTask(new Runnable() { // from class: android.view.ViewDebug$$ExternalSyntheticLambda8
            @Override // java.lang.Runnable
            public final void run() {
                ViewDebug.dumpViewHierarchyOnUIThread(Context.this, viewGroup, bufferedWriter, i, z, z2);
            }
        }, null);
        Message obtain = Message.obtain(handler, futureTask);
        obtain.setAsynchronous(true);
        handler.sendMessage(obtain);
        while (true) {
            try {
                futureTask.get(6000L, TimeUnit.MILLISECONDS);
                return;
            } catch (InterruptedException unused) {
            } catch (ExecutionException | TimeoutException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static void cacheExportedPropertiesForChildren(ViewGroup viewGroup) {
        int childCount = viewGroup.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = viewGroup.getChildAt(i);
            cacheExportedProperties(childAt.getClass());
            if (childAt instanceof ViewGroup) {
                cacheExportedPropertiesForChildren((ViewGroup) childAt);
            }
        }
    }

    private static void cacheExportedProperties(Class<?> cls) {
        HashMap<Class<?>, PropertyInfo<ExportedProperty, ?>[]> hashMap = sExportProperties;
        if (hashMap == null || !hashMap.containsKey(cls)) {
            do {
                for (PropertyInfo<ExportedProperty, ?> propertyInfo : getExportedProperties(cls)) {
                    if (!propertyInfo.returnType.isPrimitive() && propertyInfo.property.deepExport()) {
                        cacheExportedProperties(propertyInfo.returnType);
                    }
                }
                cls = cls.getSuperclass();
            } while (cls != Object.class);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public static void dumpViewHierarchyOnUIThread(Context context, ViewGroup viewGroup, BufferedWriter bufferedWriter, int i, boolean z, boolean z2) {
        boolean z3 = z2;
        if (dumpView(context, viewGroup, bufferedWriter, i, z2) && !z) {
            int childCount = viewGroup.getChildCount();
            int i2 = 0;
            while (i2 < childCount) {
                View childAt = viewGroup.getChildAt(i2);
                if (childAt instanceof ViewGroup) {
                    dumpViewHierarchyOnUIThread(context, (ViewGroup) childAt, bufferedWriter, i + 1, z, z3);
                } else {
                    dumpView(context, childAt, bufferedWriter, i + 1, z2);
                }
                if (childAt.mOverlay != null) {
                    dumpViewHierarchyOnUIThread(context, childAt.getOverlay().mOverlayViewGroup, bufferedWriter, i + 2, z, z3);
                }
                i2++;
                z3 = z2;
            }
            if (viewGroup instanceof HierarchyHandler) {
                ((HierarchyHandler) viewGroup).dumpViewHierarchyWithProperties(bufferedWriter, i + 1);
            }
        }
    }

    private static boolean dumpView(Context context, View view, BufferedWriter bufferedWriter, int i, boolean z) {
        for (int i2 = 0; i2 < i; i2++) {
            try {
                bufferedWriter.write(32);
            } catch (IOException unused) {
                Log.w("View", "Error while dumping hierarchy tree");
                return false;
            }
        }
        String name = view.getClass().getName();
        if (name.equals("android.view.ViewOverlay$OverlayViewGroup")) {
            name = "ViewOverlay";
        }
        bufferedWriter.write(name);
        bufferedWriter.write(64);
        bufferedWriter.write(Integer.toHexString(view.hashCode()));
        bufferedWriter.write(32);
        if (z) {
            dumpViewProperties(context, view, bufferedWriter);
        }
        bufferedWriter.newLine();
        return true;
    }

    private static <T extends Annotation> PropertyInfo<T, ?>[] convertToPropertyInfos(Method[] methodArr, Field[] fieldArr, final Class<T> cls) {
        return (PropertyInfo[]) Stream.of((Object[]) new Stream[]{Arrays.stream(methodArr).map(new Function() { // from class: android.view.ViewDebug$$ExternalSyntheticLambda4
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                ViewDebug.PropertyInfo forMethod;
                forMethod = ViewDebug.PropertyInfo.forMethod((Method) obj, cls);
                return forMethod;
            }
        }), Arrays.stream(fieldArr).map(new Function() { // from class: android.view.ViewDebug$$ExternalSyntheticLambda5
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                ViewDebug.PropertyInfo forField;
                forField = ViewDebug.PropertyInfo.forField((Field) obj, cls);
                return forField;
            }
        })}).flatMap(Function.identity()).filter(new Predicate() { // from class: android.view.ViewDebug$$ExternalSyntheticLambda6
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ViewDebug.lambda$convertToPropertyInfos$8(obj);
            }
        }).toArray(new IntFunction() { // from class: android.view.ViewDebug$$ExternalSyntheticLambda7
            @Override // java.util.function.IntFunction
            public final Object apply(int i) {
                return ViewDebug.lambda$convertToPropertyInfos$9(i);
            }
        });
    }

    static /* synthetic */ PropertyInfo[] lambda$convertToPropertyInfos$9(int i) {
        return new PropertyInfo[i];
    }

    private static PropertyInfo<ExportedProperty, ?>[] getExportedProperties(Class<?> cls) {
        if (sExportProperties == null) {
            sExportProperties = new HashMap<>();
        }
        HashMap<Class<?>, PropertyInfo<ExportedProperty, ?>[]> hashMap = sExportProperties;
        PropertyInfo<ExportedProperty, ?>[] propertyInfoArr = hashMap.get(cls);
        if (propertyInfoArr != null) {
            return propertyInfoArr;
        }
        PropertyInfo<ExportedProperty, ?>[] convertToPropertyInfos = convertToPropertyInfos(cls.getDeclaredMethods(), cls.getDeclaredFields(), ExportedProperty.class);
        hashMap.put(cls, convertToPropertyInfos);
        return convertToPropertyInfos;
    }

    private static void dumpViewProperties(Context context, Object obj, BufferedWriter bufferedWriter) throws IOException {
        dumpViewProperties(context, obj, bufferedWriter, "");
    }

    private static void dumpViewProperties(Context context, Object obj, BufferedWriter bufferedWriter, String str) throws IOException {
        if (obj == null) {
            bufferedWriter.write(str + "=4,null ");
            return;
        }
        Class<?> cls = obj.getClass();
        do {
            writeExportedProperties(context, obj, bufferedWriter, cls, str);
            cls = cls.getSuperclass();
        } while (cls != Object.class);
    }

    private static String formatIntToHexString(int i) {
        return "0x" + Integer.toHexString(i).toUpperCase();
    }

    private static void writeExportedProperties(Context context, Object obj, BufferedWriter bufferedWriter, Class<?> cls, String str) throws IOException {
        Object invoke;
        String str2;
        BufferedWriter bufferedWriter2;
        boolean z;
        for (PropertyInfo<ExportedProperty, ?> propertyInfo : getExportedProperties(cls)) {
            try {
                invoke = propertyInfo.invoke(obj);
                str2 = propertyInfo.property.category().length() != 0 ? propertyInfo.property.category() + ":" : "";
            } catch (Exception unused) {
            }
            if (propertyInfo.returnType == Integer.TYPE || propertyInfo.returnType == Byte.TYPE) {
                bufferedWriter2 = bufferedWriter;
                if (propertyInfo.property.resolveId() && context != null) {
                    invoke = resolveId(context, ((Integer) invoke).intValue());
                } else if (propertyInfo.property.formatToHexString()) {
                    if (propertyInfo.returnType == Integer.TYPE) {
                        invoke = formatIntToHexString(((Integer) invoke).intValue());
                    } else if (propertyInfo.returnType == Byte.TYPE) {
                        invoke = "0x" + HexEncoding.encodeToString(((Byte) invoke).byteValue(), true);
                    }
                } else {
                    FlagToString[] flagMapping = propertyInfo.property.flagMapping();
                    if (flagMapping.length > 0) {
                        exportUnrolledFlags(bufferedWriter2, flagMapping, ((Integer) invoke).intValue(), str2 + str + propertyInfo.name + '_');
                    }
                    IntToString[] mapping = propertyInfo.property.mapping();
                    if (mapping.length > 0) {
                        Integer num = (Integer) invoke;
                        int intValue = num.intValue();
                        int length = mapping.length;
                        int i = 0;
                        while (true) {
                            if (i >= length) {
                                z = false;
                                break;
                            }
                            IntToString intToString = mapping[i];
                            if (intToString.from() == intValue) {
                                invoke = intToString.to();
                                z = true;
                                break;
                            }
                            i++;
                        }
                        if (!z) {
                            invoke = num;
                        }
                    }
                }
            } else {
                if (propertyInfo.returnType == int[].class) {
                    exportUnrolledArray(context, bufferedWriter, propertyInfo.property, (int[]) invoke, str2 + str + propertyInfo.name + '_', propertyInfo.entrySuffix);
                } else {
                    bufferedWriter2 = bufferedWriter;
                    if (propertyInfo.returnType == String[].class) {
                        String[] strArr = (String[]) invoke;
                        if (propertyInfo.property.hasAdjacentMapping() && strArr != null) {
                            for (int i2 = 0; i2 < strArr.length; i2 += 2) {
                                if (strArr[i2] != null) {
                                    String str3 = str2 + str;
                                    String str4 = strArr[i2];
                                    String str5 = propertyInfo.entrySuffix;
                                    String str6 = strArr[i2 + 1];
                                    if (str6 == null) {
                                        str6 = PerfettoProtoLogImpl.NULL_STRING;
                                    }
                                    writeEntry(bufferedWriter2, str3, str4, str5, str6);
                                }
                            }
                        }
                    } else if (!propertyInfo.returnType.isPrimitive() && propertyInfo.property.deepExport()) {
                        dumpViewProperties(context, invoke, bufferedWriter2, str + propertyInfo.property.prefix());
                    }
                }
            }
            writeEntry(bufferedWriter2, str2 + str, propertyInfo.name, propertyInfo.entrySuffix, invoke);
        }
    }

    private static void writeEntry(BufferedWriter bufferedWriter, String str, String str2, String str3, Object obj) throws IOException {
        bufferedWriter.write(str);
        bufferedWriter.write(str2);
        bufferedWriter.write(str3);
        bufferedWriter.write("=");
        writeValue(bufferedWriter, obj);
        bufferedWriter.write(32);
    }

    private static void exportUnrolledFlags(BufferedWriter bufferedWriter, FlagToString[] flagToStringArr, int i, String str) throws IOException {
        for (FlagToString flagToString : flagToStringArr) {
            boolean outputIf = flagToString.outputIf();
            int mask = flagToString.mask() & i;
            boolean z = mask == flagToString.equals();
            if ((z && outputIf) || (!z && !outputIf)) {
                writeEntry(bufferedWriter, str, flagToString.name(), "", formatIntToHexString(mask));
            }
        }
    }

    public static String intToString(Class<?> cls, String str, int i) {
        IntToString[] mapping = getMapping(cls, str);
        if (mapping == null) {
            return Integer.toString(i);
        }
        for (IntToString intToString : mapping) {
            if (intToString.from() == i) {
                return intToString.to();
            }
        }
        return Integer.toString(i);
    }

    public static String flagsToString(Class<?> cls, String str, int i) {
        FlagToString[] flagMapping = getFlagMapping(cls, str);
        if (flagMapping == null) {
            return Integer.toHexString(i);
        }
        StringBuilder sb = new StringBuilder();
        for (FlagToString flagToString : flagMapping) {
            boolean outputIf = flagToString.outputIf();
            if ((flagToString.mask() & i) == flagToString.equals() && outputIf) {
                sb.append(flagToString.name());
                sb.append(' ');
            }
        }
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }

    private static FlagToString[] getFlagMapping(Class<?> cls, String str) {
        try {
            return ((ExportedProperty) cls.getDeclaredField(str).getAnnotation(ExportedProperty.class)).flagMapping();
        } catch (NoSuchFieldException unused) {
            return null;
        }
    }

    private static IntToString[] getMapping(Class<?> cls, String str) {
        try {
            return ((ExportedProperty) cls.getDeclaredField(str).getAnnotation(ExportedProperty.class)).mapping();
        } catch (NoSuchFieldException unused) {
            return null;
        }
    }

    private static void exportUnrolledArray(Context context, BufferedWriter bufferedWriter, ExportedProperty exportedProperty, int[] iArr, String str, String str2) throws IOException {
        String str3;
        IntToString[] indexMapping = exportedProperty.indexMapping();
        boolean z = indexMapping.length > 0;
        IntToString[] mapping = exportedProperty.mapping();
        boolean z2 = mapping.length > 0;
        boolean z3 = exportedProperty.resolveId() && context != null;
        int length = iArr.length;
        for (int i = 0; i < length; i++) {
            int i2 = iArr[i];
            String valueOf = String.valueOf(i);
            if (z) {
                int length2 = indexMapping.length;
                int i3 = 0;
                while (true) {
                    if (i3 >= length2) {
                        break;
                    }
                    IntToString intToString = indexMapping[i3];
                    if (intToString.from() == i) {
                        valueOf = intToString.to();
                        break;
                    }
                    i3++;
                }
            }
            if (z2) {
                for (IntToString intToString2 : mapping) {
                    if (intToString2.from() == i2) {
                        str3 = intToString2.to();
                        break;
                    }
                }
            }
            str3 = null;
            if (!z3) {
                str3 = String.valueOf(i2);
            } else if (str3 == null) {
                str3 = (String) resolveId(context, i2);
            }
            writeEntry(bufferedWriter, str, valueOf, str2, str3);
        }
    }

    static Object resolveId(Context context, int i) {
        Resources resources = context.getResources();
        if (i >= 0) {
            try {
                return resources.getResourceTypeName(i) + '/' + resources.getResourceEntryName(i);
            } catch (Resources.NotFoundException unused) {
                return "id/" + formatIntToHexString(i);
            }
        }
        return "NO_ID";
    }

    private static void writeValue(BufferedWriter bufferedWriter, Object obj) throws IOException {
        if (obj != null) {
            try {
                String replace = obj.toString().replace(ShaderAssembler.NEWLINE, "\\n");
                bufferedWriter.write(String.valueOf(replace.length()));
                bufferedWriter.write(",");
                bufferedWriter.write(replace);
                return;
            } catch (Throwable th) {
                bufferedWriter.write(String.valueOf(11));
                bufferedWriter.write(",");
                bufferedWriter.write("[EXCEPTION]");
                throw th;
            }
        }
        bufferedWriter.write("4,null");
    }

    private static PropertyInfo<CapturedViewProperty, ?>[] getCapturedViewProperties(Class<?> cls) {
        if (sCapturedViewProperties == null) {
            sCapturedViewProperties = new HashMap<>();
        }
        HashMap<Class<?>, PropertyInfo<CapturedViewProperty, ?>[]> hashMap = sCapturedViewProperties;
        PropertyInfo<CapturedViewProperty, ?>[] propertyInfoArr = hashMap.get(cls);
        if (propertyInfoArr != null) {
            return propertyInfoArr;
        }
        PropertyInfo<CapturedViewProperty, ?>[] convertToPropertyInfos = convertToPropertyInfos(cls.getMethods(), cls.getFields(), CapturedViewProperty.class);
        hashMap.put(cls, convertToPropertyInfos);
        return convertToPropertyInfos;
    }

    private static String exportCapturedViewProperties(Object obj, Class<?> cls, String str) {
        if (obj == null) {
            return PerfettoProtoLogImpl.NULL_STRING;
        }
        StringBuilder sb = new StringBuilder();
        for (PropertyInfo<CapturedViewProperty, ?> propertyInfo : getCapturedViewProperties(cls)) {
            try {
                Object invoke = propertyInfo.invoke(obj);
                if (propertyInfo.property.retrieveReturn()) {
                    sb.append(exportCapturedViewProperties(invoke, propertyInfo.returnType, propertyInfo.name + "#"));
                } else {
                    sb.append(str);
                    sb.append(propertyInfo.name);
                    sb.append(propertyInfo.entrySuffix);
                    sb.append("=");
                    if (invoke != null) {
                        sb.append(invoke.toString().replace(ShaderAssembler.NEWLINE, "\\n"));
                    } else {
                        sb.append(PerfettoProtoLogImpl.NULL_STRING);
                    }
                    sb.append(propertyInfo.valueSuffix);
                    sb.append(" ");
                }
            } catch (Exception unused) {
            }
        }
        return sb.toString();
    }

    public static void dumpCapturedView(String str, Object obj) {
        Class<?> cls = obj.getClass();
        StringBuilder sb = new StringBuilder(cls.getName() + ": ");
        sb.append(exportCapturedViewProperties(obj, cls, ""));
        Log.d(str, sb.toString());
    }

    private static void invokeViewMethod(View view, OutputStream outputStream, String[] strArr) throws IOException {
        byte[] decode;
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream), 32768);
        try {
            if (strArr.length < 2) {
                throw new IllegalArgumentException("Missing parameter");
            }
            View findView = findView(view, strArr[0]);
            if (findView == null) {
                throw new IllegalArgumentException("View not found: " + strArr[0]);
            }
            String str = strArr[1];
            if (strArr.length < 2) {
                decode = new byte[0];
            } else {
                decode = Base64.decode(strArr[2], 2);
            }
            byte[] invokeViewMethod = invokeViewMethod(findView, str, ByteBuffer.wrap(decode));
            bufferedWriter.write("1");
            bufferedWriter.newLine();
            bufferedWriter.write(Base64.encodeToString(invokeViewMethod, 2));
            bufferedWriter.newLine();
        } catch (Exception e) {
            bufferedWriter.write("-1");
            bufferedWriter.newLine();
            bufferedWriter.write(e.getMessage());
            bufferedWriter.newLine();
        } finally {
            bufferedWriter.close();
        }
    }

    public static byte[] invokeViewMethod(final View view, String str, ByteBuffer byteBuffer) throws ViewMethodInvocationSerializationException {
        final Object[] objArr;
        Class<?>[] clsArr;
        if (!byteBuffer.hasRemaining()) {
            clsArr = new Class[0];
            objArr = new Object[0];
        } else {
            int i = byteBuffer.getInt();
            Class<?>[] clsArr2 = new Class[i];
            Object[] objArr2 = new Object[i];
            deserializeMethodParameters(objArr2, clsArr2, byteBuffer);
            objArr = objArr2;
            clsArr = clsArr2;
        }
        try {
            final Method method = view.getClass().getMethod(str, clsArr);
            try {
                FutureTask futureTask = new FutureTask(new Callable() { // from class: android.view.ViewDebug$$ExternalSyntheticLambda3
                    @Override // java.util.concurrent.Callable
                    public final Object call() {
                        Object invoke;
                        invoke = method.invoke(view, objArr);
                        return invoke;
                    }
                });
                view.post(futureTask);
                Object obj = futureTask.get();
                Class<?> returnType = method.getReturnType();
                return serializeReturnValue(returnType, returnType.cast(obj));
            } catch (Exception e) {
                Log.e(TAG, "Exception while invoking method: " + e.getCause().getMessage());
                String message = e.getCause().getMessage();
                if (message == null) {
                    message = e.getCause().toString();
                }
                throw new RuntimeException(message);
            }
        } catch (NoSuchMethodException e2) {
            Log.e(TAG, "No such method: " + e2.getMessage());
            throw new ViewMethodInvocationSerializationException("No such method: " + e2.getMessage());
        }
    }

    public static void setLayoutParameter(final View view, String str, int i) throws NoSuchFieldException, IllegalAccessException {
        final ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        Field field = layoutParams.getClass().getField(str);
        if (field.getType() != Integer.TYPE) {
            throw new RuntimeException("Only integer layout parameters can be set. Field " + str + " is of type " + field.getType().getSimpleName());
        }
        field.set(layoutParams, Integer.valueOf(i));
        view.post(new Runnable() { // from class: android.view.ViewDebug.4
            @Override // java.lang.Runnable
            public void run() {
                View.this.setLayoutParams(layoutParams);
            }
        });
    }

    public static class SoftwareCanvasProvider implements CanvasProvider {
        private Bitmap mBitmap;
        private Canvas mCanvas;
        private boolean mEnabledHwFeaturesInSwMode;

        @Override // android.view.ViewDebug.CanvasProvider
        public Canvas getCanvas(View view, int i, int i2) {
            Bitmap createBitmap = Bitmap.createBitmap(view.getResources().getDisplayMetrics(), i, i2, Bitmap.Config.ARGB_8888);
            this.mBitmap = createBitmap;
            if (createBitmap == null) {
                throw new OutOfMemoryError();
            }
            createBitmap.setDensity(view.getResources().getDisplayMetrics().densityDpi);
            if (view.mAttachInfo != null) {
                this.mCanvas = view.mAttachInfo.mCanvas;
            }
            if (this.mCanvas == null) {
                this.mCanvas = new Canvas();
            }
            this.mEnabledHwFeaturesInSwMode = this.mCanvas.isHwFeaturesInSwModeEnabled();
            this.mCanvas.setBitmap(this.mBitmap);
            return this.mCanvas;
        }

        @Override // android.view.ViewDebug.CanvasProvider
        public Bitmap createBitmap() {
            this.mCanvas.setBitmap(null);
            this.mCanvas.setHwFeaturesInSwModeEnabled(this.mEnabledHwFeaturesInSwMode);
            return this.mBitmap;
        }
    }

    public static class HardwareCanvasProvider implements CanvasProvider {
        private Picture mPicture;

        @Override // android.view.ViewDebug.CanvasProvider
        public Canvas getCanvas(View view, int i, int i2) {
            Picture picture = new Picture();
            this.mPicture = picture;
            return picture.beginRecording(i, i2);
        }

        @Override // android.view.ViewDebug.CanvasProvider
        public Bitmap createBitmap() {
            this.mPicture.endRecording();
            return Bitmap.createBitmap(this.mPicture);
        }
    }

    public static void deserializeMethodParameters(Object[] objArr, Class<?>[] clsArr, ByteBuffer byteBuffer) throws ViewMethodInvocationSerializationException {
        Preconditions.checkArgument(objArr.length == clsArr.length);
        for (int i = 0; i < objArr.length; i++) {
            char c = byteBuffer.getChar();
            if (c == '[') {
                if (byteBuffer.getChar() != 'B') {
                    throw new ViewMethodInvocationSerializationException("Unsupported array parameter type (" + c + ") to invoke view method @argument " + i);
                }
                int i2 = byteBuffer.getInt();
                if (i2 > byteBuffer.remaining()) {
                    throw new BufferUnderflowException();
                }
                byte[] bArr = new byte[i2];
                byteBuffer.get(bArr);
                clsArr[i] = byte[].class;
                objArr[i] = bArr;
            } else if (c == 'F') {
                clsArr[i] = Float.TYPE;
                objArr[i] = Float.valueOf(byteBuffer.getFloat());
            } else if (c == 'Z') {
                clsArr[i] = Boolean.TYPE;
                objArr[i] = Boolean.valueOf(byteBuffer.get() != 0);
            } else if (c == 'I') {
                clsArr[i] = Integer.TYPE;
                objArr[i] = Integer.valueOf(byteBuffer.getInt());
            } else if (c == 'J') {
                clsArr[i] = Long.TYPE;
                objArr[i] = Long.valueOf(byteBuffer.getLong());
            } else if (c == 'R') {
                clsArr[i] = String.class;
                byte[] bArr2 = new byte[Short.toUnsignedInt(byteBuffer.getShort())];
                byteBuffer.get(bArr2);
                objArr[i] = new String(bArr2, StandardCharsets.UTF_8);
            } else if (c != 'S') {
                switch (c) {
                    case 'B':
                        clsArr[i] = Byte.TYPE;
                        objArr[i] = Byte.valueOf(byteBuffer.get());
                        break;
                    case 'C':
                        clsArr[i] = Character.TYPE;
                        objArr[i] = Character.valueOf(byteBuffer.getChar());
                        break;
                    case 'D':
                        clsArr[i] = Double.TYPE;
                        objArr[i] = Double.valueOf(byteBuffer.getDouble());
                        break;
                    default:
                        Log.e(TAG, "arg " + i + ", unrecognized type: " + c);
                        throw new ViewMethodInvocationSerializationException("Unsupported parameter type (" + c + ") to invoke view method.");
                }
            } else {
                clsArr[i] = Short.TYPE;
                objArr[i] = Short.valueOf(byteBuffer.getShort());
            }
        }
    }

    public static byte[] serializeReturnValue(Class<?> cls, Object obj) throws ViewMethodInvocationSerializationException, IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(1024);
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        if (cls.isArray()) {
            if (!cls.equals(byte[].class)) {
                throw new ViewMethodInvocationSerializationException("Unsupported array return type (" + cls + NavigationBarInflaterView.KEY_CODE_END);
            }
            byte[] bArr = (byte[]) obj;
            dataOutputStream.writeChar(91);
            dataOutputStream.writeChar(66);
            dataOutputStream.writeInt(bArr.length);
            dataOutputStream.write(bArr);
        } else if (Boolean.TYPE.equals(cls)) {
            dataOutputStream.writeChar(90);
            dataOutputStream.write(((Boolean) obj).booleanValue() ? 1 : 0);
        } else if (Byte.TYPE.equals(cls)) {
            dataOutputStream.writeChar(66);
            dataOutputStream.writeByte(((Byte) obj).byteValue());
        } else if (Character.TYPE.equals(cls)) {
            dataOutputStream.writeChar(67);
            dataOutputStream.writeChar(((Character) obj).charValue());
        } else if (Short.TYPE.equals(cls)) {
            dataOutputStream.writeChar(83);
            dataOutputStream.writeShort(((Short) obj).shortValue());
        } else if (Integer.TYPE.equals(cls)) {
            dataOutputStream.writeChar(73);
            dataOutputStream.writeInt(((Integer) obj).intValue());
        } else if (Long.TYPE.equals(cls)) {
            dataOutputStream.writeChar(74);
            dataOutputStream.writeLong(((Long) obj).longValue());
        } else if (Double.TYPE.equals(cls)) {
            dataOutputStream.writeChar(68);
            dataOutputStream.writeDouble(((Double) obj).doubleValue());
        } else if (Float.TYPE.equals(cls)) {
            dataOutputStream.writeChar(70);
            dataOutputStream.writeFloat(((Float) obj).floatValue());
        } else if (String.class.equals(cls)) {
            dataOutputStream.writeChar(82);
            dataOutputStream.writeUTF(obj != null ? (String) obj : "");
        } else {
            dataOutputStream.writeChar(86);
        }
        return byteArrayOutputStream.toByteArray();
    }

    public static class ViewMethodInvocationSerializationException extends Exception {
        ViewMethodInvocationSerializationException(String str) {
            super(str);
        }
    }
}
