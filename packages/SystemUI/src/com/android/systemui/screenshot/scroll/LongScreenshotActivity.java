package com.android.systemui.screenshot.scroll;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.ComponentName;
import android.content.ContentProvider;
import android.content.ContentResolver;
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
import com.android.systemui.screenshot.ActionIntentCreator;
import com.android.systemui.screenshot.ActionIntentExecutor;
import com.android.systemui.screenshot.ImageExporter;
import com.android.systemui.screenshot.ImageExporter$$ExternalSyntheticLambda0;
import com.android.systemui.screenshot.ScreenshotEvent;
import com.android.systemui.screenshot.scroll.ImageLoader;
import com.android.systemui.screenshot.scroll.LongScreenshotActivity;
import com.android.systemui.screenshot.scroll.ScrollCaptureController;
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

    enum PendingAction {
        SHARE,
        EDIT,
        SAVE
    }

    public static void $r8$lambda$AKhoAjDWW24UTwZdM5QLD3oY6ZA(LongScreenshotActivity longScreenshotActivity, View view) {
        longScreenshotActivity.getClass();
        int id = view.getId();
        view.setPressed(true);
        longScreenshotActivity.mSave.setEnabled(false);
        longScreenshotActivity.mEdit.setEnabled(false);
        longScreenshotActivity.mShare.setEnabled(false);
        if (id == R.id.save) {
            longScreenshotActivity.mUiEventLogger.log(ScreenshotEvent.SCREENSHOT_LONG_SCREENSHOT_SAVED);
            longScreenshotActivity.startExport(PendingAction.SAVE);
            return;
        }
        if (id == R.id.edit) {
            longScreenshotActivity.mUiEventLogger.log(ScreenshotEvent.SCREENSHOT_LONG_SCREENSHOT_EDIT);
            longScreenshotActivity.startExport(PendingAction.EDIT);
        } else if (id == R.id.share) {
            longScreenshotActivity.mUiEventLogger.log(ScreenshotEvent.SCREENSHOT_LONG_SCREENSHOT_SHARE);
            longScreenshotActivity.startExport(PendingAction.SHARE);
        } else if (id == R.id.cancel) {
            longScreenshotActivity.mUiEventLogger.log(ScreenshotEvent.SCREENSHOT_LONG_SCREENSHOT_EXIT);
            longScreenshotActivity.finishAndRemoveTask();
        }
    }

    public LongScreenshotActivity(UiEventLogger uiEventLogger, ImageExporter imageExporter, Executor executor, Executor executor2, LongScreenshotData longScreenshotData, ActionIntentExecutor actionIntentExecutor) {
        this.mUiEventLogger = uiEventLogger;
        this.mUiExecutor = executor;
        this.mBackgroundExecutor = executor2;
        this.mImageExporter = imageExporter;
        this.mLongScreenshotHolder = longScreenshotData;
        this.mActionExecutor = actionIntentExecutor;
    }

    public final void onCachedImageLoaded(ImageLoader.Result result) {
        this.mUiEventLogger.log(ScreenshotEvent.SCREENSHOT_LONG_SCREENSHOT_ACTIVITY_CACHED_IMAGE_LOADED);
        BitmapDrawable bitmapDrawable = new BitmapDrawable(getResources(), result.mBitmap);
        this.mPreview.setImageDrawable(bitmapDrawable);
        this.mPreview.setAlpha(1.0f);
        MagnifierView magnifierView = this.mMagnifierView;
        int width = result.mBitmap.getWidth();
        int height = result.mBitmap.getHeight();
        magnifierView.mDrawable = bitmapDrawable;
        bitmapDrawable.setBounds(0, 0, width, height);
        magnifierView.invalidate();
        this.mCropView.setVisibility(0);
        this.mSavedImagePath = result.mFilename;
        this.mSave.setEnabled(true);
        this.mEdit.setEnabled(true);
        this.mShare.setEnabled(true);
    }

    @Override // android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getWindow().setDecorFitsSystemWindows(false);
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
        this.mSave.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.screenshot.scroll.LongScreenshotActivity$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                LongScreenshotActivity.$r8$lambda$AKhoAjDWW24UTwZdM5QLD3oY6ZA(LongScreenshotActivity.this, view);
            }
        });
        this.mCancel.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.screenshot.scroll.LongScreenshotActivity$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                LongScreenshotActivity.$r8$lambda$AKhoAjDWW24UTwZdM5QLD3oY6ZA(LongScreenshotActivity.this, view);
            }
        });
        this.mEdit.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.screenshot.scroll.LongScreenshotActivity$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                LongScreenshotActivity.$r8$lambda$AKhoAjDWW24UTwZdM5QLD3oY6ZA(LongScreenshotActivity.this, view);
            }
        });
        this.mShare.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.screenshot.scroll.LongScreenshotActivity$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                LongScreenshotActivity.$r8$lambda$AKhoAjDWW24UTwZdM5QLD3oY6ZA(LongScreenshotActivity.this, view);
            }
        });
        this.mPreview.addOnLayoutChangeListener(new View.OnLayoutChangeListener() { // from class: com.android.systemui.screenshot.scroll.LongScreenshotActivity$$ExternalSyntheticLambda2
            @Override // android.view.View.OnLayoutChangeListener
            public final void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
                LongScreenshotActivity longScreenshotActivity = LongScreenshotActivity.this;
                int i9 = LongScreenshotActivity.$r8$clinit;
                longScreenshotActivity.updateImageDimensions();
            }
        });
        requireViewById(R.id.root).setOnApplyWindowInsetsListener(new LongScreenshotActivity$$ExternalSyntheticLambda3());
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
                this.mCacheLoadFuture = CallbackToFutureAdapter.getFuture(new CallbackToFutureAdapter.Resolver() { // from class: com.android.systemui.screenshot.scroll.ImageLoader$$ExternalSyntheticLambda0
                    @Override // androidx.concurrent.futures.CallbackToFutureAdapter.Resolver
                    public final Object attachCompleter(CallbackToFutureAdapter.Completer completer) {
                        File file2 = file;
                        try {
                            BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(file2));
                            try {
                                ImageLoader.Result result = new ImageLoader.Result();
                                result.mFilename = file2;
                                result.mBitmap = BitmapFactory.decodeStream(bufferedInputStream);
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
            safeFuture.delegate.addListener(new Runnable() { // from class: com.android.systemui.screenshot.scroll.LongScreenshotActivity$$ExternalSyntheticLambda0
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
        int width = this.mLongScreenshot.mImageTileSet.mRegion.getBounds().width();
        int height = this.mLongScreenshot.mImageTileSet.getHeight();
        magnifierView.mDrawable = tiledImageDrawable;
        tiledImageDrawable.setBounds(0, 0, width, height);
        magnifierView.invalidate();
        Log.i("Screenshot", "Completed: " + longScreenshot);
        Math.max(0.0f, ((float) (-this.mLongScreenshot.mImageTileSet.mRegion.getBounds().top)) / ((float) this.mLongScreenshot.mImageTileSet.getHeight()));
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
                if (((ArrayList) imageTileSet.mTiles).isEmpty()) {
                    throw null;
                }
                imageTileSet.mRegion.setEmpty();
                Iterator it = ((ArrayList) imageTileSet.mTiles).iterator();
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
        Executor executor = this.mBackgroundExecutor;
        UUID randomUUID = UUID.randomUUID();
        Bitmap bitmap = this.mOutputBitmap;
        ZonedDateTime now = ZonedDateTime.now();
        UserHandle userHandle = this.mScreenshotUserHandle;
        ImageExporter imageExporter = this.mImageExporter;
        ContentResolver contentResolver = imageExporter.mResolver;
        Bitmap.CompressFormat compressFormat = imageExporter.mCompressFormat;
        final CallbackToFutureAdapter.SafeFuture future = CallbackToFutureAdapter.getFuture(new ImageExporter$$ExternalSyntheticLambda0(executor, new ImageExporter.Task(contentResolver, randomUUID, bitmap, now, compressFormat, imageExporter.mQuality, true, userHandle, imageExporter.mFlags, ImageExporter.createFilename(now, compressFormat, 0))));
        future.delegate.addListener(new Runnable() { // from class: com.android.systemui.screenshot.scroll.LongScreenshotActivity$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                LongScreenshotActivity longScreenshotActivity = LongScreenshotActivity.this;
                LongScreenshotActivity.PendingAction pendingAction2 = pendingAction;
                ListenableFuture listenableFuture = future;
                int i = LongScreenshotActivity.$r8$clinit;
                longScreenshotActivity.mSave.setEnabled(true);
                longScreenshotActivity.mEdit.setEnabled(true);
                longScreenshotActivity.mShare.setEnabled(true);
                try {
                    Uri uriWithoutUserId = ContentProvider.getUriWithoutUserId(((ImageExporter.Result) listenableFuture.get()).uri);
                    Log.e("Screenshot", pendingAction2 + " uri=" + uriWithoutUserId);
                    int ordinal = pendingAction2.ordinal();
                    Bundle bundle = null;
                    if (ordinal == 0) {
                        ActionIntentCreator.INSTANCE.getClass();
                        longScreenshotActivity.mActionExecutor.launchIntentAsync(ActionIntentCreator.createShare(uriWithoutUserId, null, null), longScreenshotActivity.mScreenshotUserHandle, false, null, null);
                        return;
                    }
                    if (ordinal != 1) {
                        if (ordinal != 2) {
                            return;
                        }
                        longScreenshotActivity.finishAndRemoveTask();
                        return;
                    }
                    if (longScreenshotActivity.mScreenshotUserHandle != Process.myUserHandle()) {
                        ActionIntentCreator.INSTANCE.getClass();
                        longScreenshotActivity.mActionExecutor.launchIntentAsync(ActionIntentCreator.createEdit(longScreenshotActivity, uriWithoutUserId), longScreenshotActivity.mScreenshotUserHandle, false, null, null);
                        return;
                    }
                    String string = longScreenshotActivity.getString(R.string.config_screenshotEditor);
                    Intent intent = new Intent("android.intent.action.EDIT");
                    intent.setDataAndType(uriWithoutUserId, "image/png");
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
            paddingLeft = AbsActionBarView$$ExternalSyntheticOutline0.m(width2, i3, 2, paddingLeft);
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
