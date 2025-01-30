package com.android.systemui.screenshot;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.HardwareRenderer;
import android.graphics.RecordingCanvas;
import android.graphics.Rect;
import android.graphics.RenderNode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Process;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.Log;
import android.view.ScrollCaptureResponse;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.appcompat.widget.AbsActionBarView$$ExternalSyntheticOutline0;
import androidx.concurrent.futures.CallbackToFutureAdapter;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.android.internal.logging.UiEventLogger;
import com.android.internal.util.CallbackRegistry;
import com.android.systemui.R;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.screenshot.ImageExporter;
import com.android.systemui.screenshot.ImageLoader;
import com.android.systemui.screenshot.LongScreenshotActivity;
import com.android.systemui.screenshot.ScrollCaptureController;
import com.android.systemui.settings.UserTracker;
import com.google.common.util.concurrent.ListenableFuture;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.UUID;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class LongScreenshotActivity extends Activity {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final ActionIntentExecutor mActionExecutor;
    public final Executor mBackgroundExecutor;
    public CallbackToFutureAdapter.SafeFuture mCacheLoadFuture;
    public View mCancel;
    public CropView mCropView;
    public View mEdit;
    public ImageView mEnterTransitionView;
    public final ImageExporter mImageExporter;
    public ScrollCaptureController.LongScreenshot mLongScreenshot;
    public final LongScreenshotData mLongScreenshotHolder;
    public MagnifierView mMagnifierView;
    public Bitmap mOutputBitmap;
    public ImageView mPreview;
    public View mSave;
    public File mSavedImagePath;
    public UserHandle mScreenshotUserHandle;
    public ScrollCaptureResponse mScrollCaptureResponse;
    public View mShare;
    public boolean mTransitionStarted;
    public ImageView mTransitionView;
    public final UiEventLogger mUiEventLogger;
    public final Executor mUiExecutor;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.screenshot.LongScreenshotActivity$2 */
    public abstract /* synthetic */ class AbstractC23322 {

        /* renamed from: $SwitchMap$com$android$systemui$screenshot$LongScreenshotActivity$PendingAction */
        public static final /* synthetic */ int[] f335x6ba3e28f;

        static {
            int[] iArr = new int[PendingAction.values().length];
            f335x6ba3e28f = iArr;
            try {
                iArr[PendingAction.EDIT.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f335x6ba3e28f[PendingAction.SHARE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f335x6ba3e28f[PendingAction.SAVE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public enum PendingAction {
        SHARE,
        EDIT,
        SAVE
    }

    public static void $r8$lambda$Wl8fRzdIVAPxf9MEzKy2r_9Ur3A(LongScreenshotActivity longScreenshotActivity, View view) {
        longScreenshotActivity.getClass();
        int id = view.getId();
        view.setPressed(true);
        longScreenshotActivity.mSave.setEnabled(false);
        longScreenshotActivity.mEdit.setEnabled(false);
        longScreenshotActivity.mShare.setEnabled(false);
        UiEventLogger uiEventLogger = longScreenshotActivity.mUiEventLogger;
        if (id == R.id.save) {
            uiEventLogger.log(ScreenshotEvent.SCREENSHOT_LONG_SCREENSHOT_SAVED);
            longScreenshotActivity.startExport(PendingAction.SAVE);
            return;
        }
        if (id == R.id.edit) {
            uiEventLogger.log(ScreenshotEvent.SCREENSHOT_LONG_SCREENSHOT_EDIT);
            longScreenshotActivity.startExport(PendingAction.EDIT);
        } else if (id == R.id.share) {
            uiEventLogger.log(ScreenshotEvent.SCREENSHOT_LONG_SCREENSHOT_SHARE);
            longScreenshotActivity.startExport(PendingAction.SHARE);
        } else if (id == R.id.cancel) {
            uiEventLogger.log(ScreenshotEvent.SCREENSHOT_LONG_SCREENSHOT_EXIT);
            longScreenshotActivity.finishAndRemoveTask();
        }
    }

    public LongScreenshotActivity(UiEventLogger uiEventLogger, ImageExporter imageExporter, Executor executor, Executor executor2, LongScreenshotData longScreenshotData, ActionIntentExecutor actionIntentExecutor, FeatureFlags featureFlags, UserTracker userTracker) {
        this.mUiEventLogger = uiEventLogger;
        this.mUiExecutor = executor;
        this.mBackgroundExecutor = executor2;
        this.mImageExporter = imageExporter;
        this.mLongScreenshotHolder = longScreenshotData;
        this.mActionExecutor = actionIntentExecutor;
    }

    public final void onCachedImageLoaded(ImageLoader.Result result) {
        this.mUiEventLogger.log(ScreenshotEvent.SCREENSHOT_LONG_SCREENSHOT_ACTIVITY_CACHED_IMAGE_LOADED);
        BitmapDrawable bitmapDrawable = new BitmapDrawable(getResources(), result.bitmap);
        this.mPreview.setImageDrawable(bitmapDrawable);
        this.mPreview.setAlpha(1.0f);
        MagnifierView magnifierView = this.mMagnifierView;
        int width = result.bitmap.getWidth();
        int height = result.bitmap.getHeight();
        magnifierView.mDrawable = bitmapDrawable;
        bitmapDrawable.setBounds(0, 0, width, height);
        magnifierView.invalidate();
        this.mCropView.setVisibility(0);
        this.mSavedImagePath = result.fileName;
        this.mSave.setEnabled(true);
        this.mEdit.setEnabled(true);
        this.mShare.setEnabled(true);
    }

    @Override // android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.long_screenshot);
        this.mPreview = (ImageView) requireViewById(R.id.preview);
        this.mSave = requireViewById(R.id.save);
        this.mEdit = requireViewById(R.id.edit);
        this.mShare = requireViewById(R.id.share);
        this.mCancel = requireViewById(R.id.cancel);
        this.mCropView = (CropView) requireViewById(R.id.crop_view);
        MagnifierView magnifierView = (MagnifierView) requireViewById(R.id.magnifier);
        this.mMagnifierView = magnifierView;
        this.mCropView.mCropInteractionListener = magnifierView;
        this.mTransitionView = (ImageView) requireViewById(R.id.transition);
        this.mEnterTransitionView = (ImageView) requireViewById(R.id.enter_transition);
        final int i = 0;
        this.mSave.setOnClickListener(new View.OnClickListener(this) { // from class: com.android.systemui.screenshot.LongScreenshotActivity$$ExternalSyntheticLambda1
            public final /* synthetic */ LongScreenshotActivity f$0;

            {
                this.f$0 = this;
            }

            /* JADX WARN: Failed to find 'out' block for switch in B:2:0x0002. Please report as an issue. */
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                switch (i) {
                }
                LongScreenshotActivity.$r8$lambda$Wl8fRzdIVAPxf9MEzKy2r_9Ur3A(this.f$0, view);
            }
        });
        final int i2 = 1;
        this.mCancel.setOnClickListener(new View.OnClickListener(this) { // from class: com.android.systemui.screenshot.LongScreenshotActivity$$ExternalSyntheticLambda1
            public final /* synthetic */ LongScreenshotActivity f$0;

            {
                this.f$0 = this;
            }

            /* JADX WARN: Failed to find 'out' block for switch in B:2:0x0002. Please report as an issue. */
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                switch (i2) {
                }
                LongScreenshotActivity.$r8$lambda$Wl8fRzdIVAPxf9MEzKy2r_9Ur3A(this.f$0, view);
            }
        });
        final int i3 = 2;
        this.mEdit.setOnClickListener(new View.OnClickListener(this) { // from class: com.android.systemui.screenshot.LongScreenshotActivity$$ExternalSyntheticLambda1
            public final /* synthetic */ LongScreenshotActivity f$0;

            {
                this.f$0 = this;
            }

            /* JADX WARN: Failed to find 'out' block for switch in B:2:0x0002. Please report as an issue. */
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                switch (i3) {
                }
                LongScreenshotActivity.$r8$lambda$Wl8fRzdIVAPxf9MEzKy2r_9Ur3A(this.f$0, view);
            }
        });
        final int i4 = 3;
        this.mShare.setOnClickListener(new View.OnClickListener(this) { // from class: com.android.systemui.screenshot.LongScreenshotActivity$$ExternalSyntheticLambda1
            public final /* synthetic */ LongScreenshotActivity f$0;

            {
                this.f$0 = this;
            }

            /* JADX WARN: Failed to find 'out' block for switch in B:2:0x0002. Please report as an issue. */
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                switch (i4) {
                }
                LongScreenshotActivity.$r8$lambda$Wl8fRzdIVAPxf9MEzKy2r_9Ur3A(this.f$0, view);
            }
        });
        this.mPreview.addOnLayoutChangeListener(new View.OnLayoutChangeListener() { // from class: com.android.systemui.screenshot.LongScreenshotActivity$$ExternalSyntheticLambda2
            @Override // android.view.View.OnLayoutChangeListener
            public final void onLayoutChange(View view, int i5, int i6, int i7, int i8, int i9, int i10, int i11, int i12) {
                LongScreenshotActivity longScreenshotActivity = LongScreenshotActivity.this;
                int i13 = LongScreenshotActivity.$r8$clinit;
                longScreenshotActivity.updateImageDimensions();
            }
        });
        Intent intent = getIntent();
        this.mScrollCaptureResponse = intent.getParcelableExtra("capture-response");
        UserHandle userHandle = (UserHandle) intent.getParcelableExtra("screenshot-userhandle", UserHandle.class);
        this.mScreenshotUserHandle = userHandle;
        if (userHandle == null) {
            this.mScreenshotUserHandle = Process.myUserHandle();
        }
        if (bundle != null) {
            String string = bundle.getString("saved-image-path");
            if (string == null) {
                Log.e("Screenshot", "Missing saved state entry with key 'saved-image-path'!");
                finishAndRemoveTask();
            } else {
                this.mSavedImagePath = new File(string);
                new ImageLoader(getContentResolver());
                final File file = this.mSavedImagePath;
                this.mCacheLoadFuture = CallbackToFutureAdapter.getFuture(new CallbackToFutureAdapter.Resolver() { // from class: com.android.systemui.screenshot.ImageLoader$$ExternalSyntheticLambda0
                    @Override // androidx.concurrent.futures.CallbackToFutureAdapter.Resolver
                    public final Object attachCompleter(CallbackToFutureAdapter.Completer completer) {
                        File file2 = file;
                        try {
                            BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(file2));
                            try {
                                ImageLoader.Result result = new ImageLoader.Result();
                                result.fileName = file2;
                                result.bitmap = BitmapFactory.decodeStream(bufferedInputStream);
                                completer.set(result);
                                bufferedInputStream.close();
                                return "BitmapFactory#decodeStream";
                            } finally {
                            }
                        } catch (IOException e) {
                            completer.setException(e);
                            return "BitmapFactory#decodeStream";
                        }
                    }
                });
            }
        }
    }

    @Override // android.app.Activity
    public final void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        File file = this.mSavedImagePath;
        if (file != null) {
            bundle.putString("saved-image-path", file.getPath());
        }
    }

    @Override // android.app.Activity
    public final void onStart() {
        super.onStart();
        this.mUiEventLogger.log(ScreenshotEvent.SCREENSHOT_LONG_SCREENSHOT_ACTIVITY_STARTED);
        if (this.mPreview.getDrawable() != null) {
            return;
        }
        if (this.mCacheLoadFuture != null) {
            Log.d("Screenshot", "mCacheLoadFuture != null");
            final CallbackToFutureAdapter.SafeFuture safeFuture = this.mCacheLoadFuture;
            safeFuture.delegate.addListener(new Runnable() { // from class: com.android.systemui.screenshot.LongScreenshotActivity$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    LongScreenshotActivity longScreenshotActivity = LongScreenshotActivity.this;
                    ListenableFuture listenableFuture = safeFuture;
                    int i = LongScreenshotActivity.$r8$clinit;
                    longScreenshotActivity.getClass();
                    Log.d("Screenshot", "cached bitmap load complete");
                    try {
                        longScreenshotActivity.onCachedImageLoaded((ImageLoader.Result) listenableFuture.get());
                    } catch (InterruptedException | CancellationException | ExecutionException e) {
                        Log.e("Screenshot", "Failed to load cached image", e);
                        File file = longScreenshotActivity.mSavedImagePath;
                        if (file != null) {
                            file.delete();
                            longScreenshotActivity.mSavedImagePath = null;
                        }
                        longScreenshotActivity.finishAndRemoveTask();
                    }
                }
            }, this.mUiExecutor);
            this.mCacheLoadFuture = null;
            return;
        }
        ScrollCaptureController.LongScreenshot longScreenshot = (ScrollCaptureController.LongScreenshot) this.mLongScreenshotHolder.mLongScreenshot.getAndSet(null);
        if (longScreenshot == null) {
            Log.e("Screenshot", "No long screenshot available!");
            finishAndRemoveTask();
            return;
        }
        Log.i("Screenshot", "Completed: " + longScreenshot);
        this.mLongScreenshot = longScreenshot;
        ImageTileSet imageTileSet = longScreenshot.mImageTileSet;
        imageTileSet.getClass();
        this.mPreview.setImageDrawable(new TiledImageDrawable(imageTileSet));
        MagnifierView magnifierView = this.mMagnifierView;
        ImageTileSet imageTileSet2 = this.mLongScreenshot.mImageTileSet;
        imageTileSet2.getClass();
        TiledImageDrawable tiledImageDrawable = new TiledImageDrawable(imageTileSet2);
        int width = this.mLongScreenshot.mImageTileSet.getWidth();
        int height = this.mLongScreenshot.mImageTileSet.getHeight();
        magnifierView.mDrawable = tiledImageDrawable;
        tiledImageDrawable.setBounds(0, 0, width, height);
        magnifierView.invalidate();
        Math.max(0.0f, (-this.mLongScreenshot.mImageTileSet.mRegion.getBounds().top) / this.mLongScreenshot.mImageTileSet.getHeight());
        int i = this.mLongScreenshot.mImageTileSet.mRegion.getBounds().bottom;
        this.mLongScreenshot.getClass();
        throw null;
    }

    @Override // android.app.Activity
    public final void onStop() {
        super.onStop();
        if (this.mTransitionStarted) {
            finish();
        }
        if (isFinishing()) {
            this.mUiEventLogger.log(ScreenshotEvent.SCREENSHOT_LONG_SCREENSHOT_ACTIVITY_FINISHED);
            ScrollCaptureResponse scrollCaptureResponse = this.mScrollCaptureResponse;
            if (scrollCaptureResponse != null) {
                scrollCaptureResponse.close();
            }
            File file = this.mSavedImagePath;
            if (file != null) {
                file.delete();
                this.mSavedImagePath = null;
            }
            ScrollCaptureController.LongScreenshot longScreenshot = this.mLongScreenshot;
            if (longScreenshot != null) {
                ImageTileSet imageTileSet = longScreenshot.mImageTileSet;
                ArrayList arrayList = (ArrayList) imageTileSet.mTiles;
                if (arrayList.isEmpty()) {
                    throw null;
                }
                imageTileSet.mRegion.setEmpty();
                Iterator it = arrayList.iterator();
                while (it.hasNext()) {
                    ((ImageTile) it.next()).close();
                    it.remove();
                }
                CallbackRegistry callbackRegistry = imageTileSet.mContentListeners;
                if (callbackRegistry == null) {
                    throw null;
                }
                callbackRegistry.notifyCallbacks(imageTileSet, 0, (Object) null);
                throw null;
            }
        }
    }

    public final void startExport(final PendingAction pendingAction) {
        Drawable drawable = this.mPreview.getDrawable();
        if (drawable == null) {
            Log.e("Screenshot", "No drawable, skipping export!");
            return;
        }
        Rect cropBoundaries = this.mCropView.getCropBoundaries(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        if (cropBoundaries.isEmpty()) {
            Log.w("Screenshot", "Crop bounds empty, skipping export.");
            return;
        }
        updateImageDimensions();
        RenderNode renderNode = new RenderNode("Bitmap Export");
        renderNode.setPosition(0, 0, cropBoundaries.width(), cropBoundaries.height());
        RecordingCanvas beginRecording = renderNode.beginRecording();
        beginRecording.translate(-cropBoundaries.left, -cropBoundaries.top);
        beginRecording.clipRect(cropBoundaries);
        drawable.draw(beginRecording);
        renderNode.endRecording();
        this.mOutputBitmap = HardwareRenderer.createHardwareBitmap(renderNode, cropBoundaries.width(), cropBoundaries.height());
        final CallbackToFutureAdapter.SafeFuture export = this.mImageExporter.export(this.mBackgroundExecutor, UUID.randomUUID(), this.mOutputBitmap, ZonedDateTime.now(), this.mScreenshotUserHandle);
        export.delegate.addListener(new Runnable() { // from class: com.android.systemui.screenshot.LongScreenshotActivity$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                LongScreenshotActivity longScreenshotActivity = LongScreenshotActivity.this;
                LongScreenshotActivity.PendingAction pendingAction2 = pendingAction;
                ListenableFuture listenableFuture = export;
                int i = LongScreenshotActivity.$r8$clinit;
                longScreenshotActivity.mSave.setEnabled(true);
                longScreenshotActivity.mEdit.setEnabled(true);
                longScreenshotActivity.mShare.setEnabled(true);
                try {
                    ImageExporter.Result result = (ImageExporter.Result) listenableFuture.get();
                    int i2 = LongScreenshotActivity.AbstractC23322.f335x6ba3e28f[pendingAction2.ordinal()];
                    ActionIntentExecutor actionIntentExecutor = longScreenshotActivity.mActionExecutor;
                    Bundle bundle = null;
                    if (i2 != 1) {
                        if (i2 != 2) {
                            if (i2 != 3) {
                                return;
                            }
                            longScreenshotActivity.finishAndRemoveTask();
                            return;
                        } else {
                            Uri uri = result.uri;
                            ActionIntentCreator.INSTANCE.getClass();
                            actionIntentExecutor.launchIntentAsync(ActionIntentCreator.createShareIntent(uri, null, null), null, longScreenshotActivity.mScreenshotUserHandle.getIdentifier(), false);
                            return;
                        }
                    }
                    Uri uri2 = result.uri;
                    if (longScreenshotActivity.mScreenshotUserHandle != Process.myUserHandle()) {
                        ActionIntentCreator.INSTANCE.getClass();
                        actionIntentExecutor.launchIntentAsync(ActionIntentCreator.createEditIntent(longScreenshotActivity, uri2), null, longScreenshotActivity.mScreenshotUserHandle.getIdentifier(), false);
                        return;
                    }
                    String string = longScreenshotActivity.getString(R.string.config_screenshotEditor);
                    Intent intent = new Intent("android.intent.action.EDIT");
                    intent.setDataAndType(uri2, "image/png");
                    intent.addFlags(3);
                    if (!TextUtils.isEmpty(string)) {
                        intent.setComponent(ComponentName.unflattenFromString(string));
                        longScreenshotActivity.mTransitionView.setImageBitmap(longScreenshotActivity.mOutputBitmap);
                        longScreenshotActivity.mTransitionView.setVisibility(0);
                        longScreenshotActivity.mTransitionView.setTransitionName("screenshot_preview_image");
                        bundle = ActivityOptions.makeSceneTransitionAnimation(longScreenshotActivity, longScreenshotActivity.mTransitionView, "screenshot_preview_image").toBundle();
                        longScreenshotActivity.mTransitionStarted = true;
                    }
                    longScreenshotActivity.startActivity(intent, bundle);
                } catch (InterruptedException | CancellationException | ExecutionException e) {
                    Log.e("Screenshot", "failed to export", e);
                }
            }
        }, this.mUiExecutor);
    }

    public final void updateImageDimensions() {
        float intrinsicHeight;
        Drawable drawable = this.mPreview.getDrawable();
        if (drawable == null) {
            return;
        }
        Rect bounds = drawable.getBounds();
        float width = bounds.width() / bounds.height();
        int width2 = (this.mPreview.getWidth() - this.mPreview.getPaddingLeft()) - this.mPreview.getPaddingRight();
        int height = (this.mPreview.getHeight() - this.mPreview.getPaddingTop()) - this.mPreview.getPaddingBottom();
        float f = width2;
        float f2 = height;
        float f3 = f / f2;
        int paddingLeft = this.mPreview.getPaddingLeft();
        int paddingTop = this.mPreview.getPaddingTop();
        if (width > f3) {
            int i = (int) ((f2 * f3) / width);
            int i2 = (height - i) / 2;
            CropView cropView = this.mCropView;
            int paddingTop2 = this.mPreview.getPaddingTop() + i2;
            int paddingBottom = this.mPreview.getPaddingBottom() + i2;
            cropView.mExtraTopPadding = paddingTop2;
            cropView.mExtraBottomPadding = paddingBottom;
            cropView.invalidate();
            paddingTop += i2;
            CropView cropView2 = this.mCropView;
            cropView2.mImageWidth = width2;
            cropView2.invalidate();
            intrinsicHeight = f / this.mPreview.getDrawable().getIntrinsicWidth();
            height = i;
        } else {
            int i3 = (int) ((f * width) / f3);
            paddingLeft = AbsActionBarView$$ExternalSyntheticOutline0.m8m(width2, i3, 2, paddingLeft);
            CropView cropView3 = this.mCropView;
            int paddingTop3 = this.mPreview.getPaddingTop();
            int paddingBottom2 = this.mPreview.getPaddingBottom();
            cropView3.mExtraTopPadding = paddingTop3;
            cropView3.mExtraBottomPadding = paddingBottom2;
            cropView3.invalidate();
            CropView cropView4 = this.mCropView;
            cropView4.mImageWidth = (int) (width * f2);
            cropView4.invalidate();
            intrinsicHeight = f2 / this.mPreview.getDrawable().getIntrinsicHeight();
            width2 = i3;
        }
        Rect cropBoundaries = this.mCropView.getCropBoundaries(width2, height);
        this.mTransitionView.setTranslationX(paddingLeft + cropBoundaries.left);
        this.mTransitionView.setTranslationY(paddingTop + cropBoundaries.top);
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) this.mTransitionView.getLayoutParams();
        ((ViewGroup.MarginLayoutParams) layoutParams).width = cropBoundaries.width();
        ((ViewGroup.MarginLayoutParams) layoutParams).height = cropBoundaries.height();
        this.mTransitionView.setLayoutParams(layoutParams);
        if (this.mLongScreenshot == null) {
            return;
        }
        ConstraintLayout.LayoutParams layoutParams2 = (ConstraintLayout.LayoutParams) this.mEnterTransitionView.getLayoutParams();
        Math.max(0.0f, (-this.mLongScreenshot.mImageTileSet.mRegion.getBounds().top) / this.mLongScreenshot.mImageTileSet.getHeight());
        ((ViewGroup.MarginLayoutParams) layoutParams2).width = (int) (intrinsicHeight * drawable.getIntrinsicWidth());
        this.mLongScreenshot.getClass();
        throw null;
    }
}
