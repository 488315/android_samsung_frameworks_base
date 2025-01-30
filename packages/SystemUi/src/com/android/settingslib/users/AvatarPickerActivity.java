package com.android.settingslib.users;

import android.app.Activity;
import android.app.KeyguardManager;
import android.content.ClipData;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.EventLog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.MenuPopupWindow$MenuDropDownListView$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.drawable.RoundedBitmapDrawable21;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.internal.util.UserIcons;
import com.android.settingslib.users.AvatarPhotoController;
import com.android.settingslib.users.AvatarPickerActivity;
import com.android.settingslib.utils.ThreadUtils;
import com.android.systemui.R;
import com.google.android.setupcompat.PartnerCustomizationLayout;
import com.google.android.setupcompat.partnerconfig.PartnerConfigHelper;
import com.google.android.setupcompat.util.BuildCompatUtils;
import com.google.android.setupcompat.util.Logger;
import com.google.android.setupcompat.util.WizardManagerHelper;
import com.google.android.setupdesign.util.ThemeHelper;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import libcore.io.Streams;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class AvatarPickerActivity extends AppCompatActivity {
    public static final /* synthetic */ int $r8$clinit = 0;
    public AvatarAdapter mAdapter;
    public AvatarPhotoController mAvatarPhotoController;
    public Button mSaveButton;
    public boolean mWaitingForActivityResult;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public class AutoFitGridLayoutManager extends GridLayoutManager {
        public final int mColumnWidth;
        public final int mSpanCount;
        public int mTotalSpace;

        public AutoFitGridLayoutManager(AvatarPickerActivity avatarPickerActivity, Context context, int i) {
            super(context, i);
            this.mTotalSpace = 0;
            this.mColumnWidth = avatarPickerActivity.getResources().getDimensionPixelSize(R.dimen.sec_avatar_full_size_in_picker);
            this.mSpanCount = i;
        }

        @Override // androidx.recyclerview.widget.GridLayoutManager, androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
        public final void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
            int paddingRight = (this.mWidth - getPaddingRight()) - getPaddingLeft();
            if (this.mTotalSpace < paddingRight) {
                this.mTotalSpace = paddingRight;
            }
            int max = Math.max(1, this.mTotalSpace / this.mColumnWidth);
            int i = this.mSpanCount;
            if (max > i) {
                max = i;
            }
            setSpanCount(max);
            super.onLayoutChildren(recycler, state);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class AvatarAdapter extends RecyclerView.Adapter {
        public final List mImageDescriptions;
        public final List mImageDrawables;
        public final TypedArray mPreselectedImages;
        public int mSelectedPosition = -1;
        public final int[] mUserIconColors;

        public AvatarAdapter() {
            this.mPreselectedImages = AvatarPickerActivity.this.getResources().obtainTypedArray(R.array.avatar_images);
            this.mUserIconColors = UserIcons.getUserIconColors(AvatarPickerActivity.this.getResources());
            ArrayList arrayList = new ArrayList();
            int i = 0;
            int i2 = 0;
            while (true) {
                TypedArray typedArray = this.mPreselectedImages;
                int length = typedArray.length();
                AvatarPickerActivity avatarPickerActivity = AvatarPickerActivity.this;
                if (i2 >= length) {
                    if (arrayList.isEmpty()) {
                        while (true) {
                            int[] iArr = this.mUserIconColors;
                            if (i >= iArr.length) {
                                break;
                            }
                            arrayList.add(UserIcons.getDefaultUserIconInColor(avatarPickerActivity.getResources(), iArr[i]));
                            i++;
                        }
                    }
                    this.mImageDrawables = arrayList;
                    this.mImageDescriptions = this.mPreselectedImages.length() > 0 ? Arrays.asList(AvatarPickerActivity.this.getResources().getStringArray(R.array.avatar_image_descriptions)) : null;
                    return;
                }
                Drawable drawable = typedArray.getDrawable(i2);
                if (!(drawable instanceof BitmapDrawable)) {
                    throw new IllegalStateException("Avatar drawables must be bitmaps");
                }
                RoundedBitmapDrawable21 roundedBitmapDrawable21 = new RoundedBitmapDrawable21(avatarPickerActivity.getResources(), ((BitmapDrawable) drawable).getBitmap());
                roundedBitmapDrawable21.mIsCircular = true;
                roundedBitmapDrawable21.mApplyGravity = true;
                roundedBitmapDrawable21.mCornerRadius = Math.min(roundedBitmapDrawable21.mBitmapHeight, roundedBitmapDrawable21.mBitmapWidth) / 2;
                roundedBitmapDrawable21.mPaint.setShader(roundedBitmapDrawable21.mBitmapShader);
                roundedBitmapDrawable21.invalidateSelf();
                arrayList.add(roundedBitmapDrawable21);
                i2++;
            }
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public final int getItemCount() {
            return ((ArrayList) this.mImageDrawables).size() + 0;
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public final void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int i) {
            ImageView imageView = ((AvatarViewHolder) viewHolder).mImageView;
            if (i >= 0) {
                int i2 = i + 0;
                imageView.setSelected(i == this.mSelectedPosition);
                imageView.setImageDrawable((Drawable) ((ArrayList) this.mImageDrawables).get(i2));
                List list = this.mImageDescriptions;
                if (list == null || i2 >= list.size()) {
                    imageView.setContentDescription(AvatarPickerActivity.this.getString(R.string.default_user_icon_description));
                } else {
                    imageView.setContentDescription((String) list.get(i2));
                }
            }
            imageView.setOnClickListener(new View.OnClickListener() { // from class: com.android.settingslib.users.AvatarPickerActivity$AvatarAdapter$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    AvatarPickerActivity.AvatarAdapter avatarAdapter = AvatarPickerActivity.AvatarAdapter.this;
                    int i3 = i;
                    int i4 = avatarAdapter.mSelectedPosition;
                    AvatarPickerActivity avatarPickerActivity = AvatarPickerActivity.this;
                    if (i4 == i3) {
                        avatarAdapter.mSelectedPosition = -1;
                        avatarAdapter.notifyItemChanged(i3);
                        int i5 = AvatarPickerActivity.$r8$clinit;
                        avatarPickerActivity.saveButtonSetEnabled(false);
                        return;
                    }
                    avatarAdapter.mSelectedPosition = i3;
                    avatarAdapter.notifyItemChanged(i3);
                    if (i4 != -1) {
                        avatarAdapter.notifyItemChanged(i4);
                    } else {
                        int i6 = AvatarPickerActivity.$r8$clinit;
                        avatarPickerActivity.saveButtonSetEnabled(true);
                    }
                }
            });
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public final RecyclerView.ViewHolder onCreateViewHolder(RecyclerView recyclerView, int i) {
            return new AvatarViewHolder(LayoutInflater.from(recyclerView.getContext()).inflate(R.layout.avatar_item, (ViewGroup) recyclerView, false));
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class AvatarViewHolder extends RecyclerView.ViewHolder {
        public final ImageView mImageView;

        public AvatarViewHolder(View view) {
            super(view);
            this.mImageView = (ImageView) view.findViewById(R.id.avatar_image);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class GridItemDecoration extends RecyclerView.ItemDecoration {
        public final boolean mIncludeEdge;
        public final boolean mRtl;
        public final int mSpacing;
        public final int mSpacingTop;

        public GridItemDecoration(AvatarPickerActivity avatarPickerActivity, Context context, boolean z) {
            this.mSpacing = context.getResources().getDimensionPixelSize(R.dimen.sec_avatar_item_side_padding);
            this.mSpacingTop = context.getResources().getDimensionPixelSize(R.dimen.sec_avatar_item_top_bottom_padding);
            this.mIncludeEdge = z;
            this.mRtl = MenuPopupWindow$MenuDropDownListView$$ExternalSyntheticOutline0.m11m(context) == 1;
        }

        @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
        public final void getItemOffsets(Rect rect, View view, RecyclerView recyclerView, RecyclerView.State state) {
            RecyclerView.LayoutManager layoutManager$1 = recyclerView.getLayoutManager$1();
            if (layoutManager$1 instanceof GridLayoutManager) {
                int i = ((GridLayoutManager) layoutManager$1).mSpanCount;
                int childAdapterPosition = RecyclerView.getChildAdapterPosition(view) % i;
                boolean z = this.mIncludeEdge;
                int i2 = this.mSpacing;
                if (!z) {
                    rect.left = (childAdapterPosition * i2) / i;
                    rect.right = i2 - (((childAdapterPosition + 1) * i2) / i);
                    return;
                }
                int i3 = i2 / 2;
                rect.left = i3;
                rect.right = i3;
                int i4 = this.mSpacingTop;
                rect.top = i4;
                rect.bottom = i4;
                if (this.mRtl) {
                    rect.left = i3;
                    rect.right = i3;
                }
            }
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public final void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        final boolean z = false;
        this.mWaitingForActivityResult = false;
        final AvatarPhotoController avatarPhotoController = this.mAvatarPhotoController;
        if (i2 != -1) {
            avatarPhotoController.getClass();
            return;
        }
        Uri uri = avatarPhotoController.mTakePictureUri;
        final Uri data = (intent == null || intent.getData() == null) ? uri : intent.getData();
        if (!"content".equals(data.getScheme())) {
            Log.e("AvatarPhotoController", "Invalid pictureUri scheme: " + data.getScheme());
            EventLog.writeEvent(1397638484, "172939189", -1, data.getPath());
            return;
        }
        switch (i) {
            case 1001:
                try {
                    final boolean z2 = true;
                    ThreadUtils.postOnBackgroundThread(new Runnable() { // from class: com.android.settingslib.users.AvatarPhotoController$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            AvatarPhotoController avatarPhotoController2 = AvatarPhotoController.this;
                            Uri uri2 = data;
                            boolean z3 = z2;
                            ContentResolver contentResolver = ((AvatarPhotoController.ContextInjectorImpl) avatarPhotoController2.mContextInjector).mContext.getContentResolver();
                            try {
                                InputStream openInputStream = contentResolver.openInputStream(uri2);
                                try {
                                    OutputStream openOutputStream = contentResolver.openOutputStream(avatarPhotoController2.mPreCropPictureUri);
                                    try {
                                        Streams.copy(openInputStream, openOutputStream);
                                        if (openOutputStream != null) {
                                            openOutputStream.close();
                                        }
                                        if (openInputStream != null) {
                                            openInputStream.close();
                                        }
                                        AvatarPhotoController$$ExternalSyntheticLambda2 avatarPhotoController$$ExternalSyntheticLambda2 = new AvatarPhotoController$$ExternalSyntheticLambda2(avatarPhotoController2, 1);
                                        if (!z3) {
                                            ThreadUtils.postOnMainThread(avatarPhotoController$$ExternalSyntheticLambda2);
                                            return;
                                        }
                                        if (ThreadUtils.sMainThreadHandler == null) {
                                            ThreadUtils.sMainThreadHandler = new Handler(Looper.getMainLooper());
                                        }
                                        ThreadUtils.sMainThreadHandler.postDelayed(avatarPhotoController$$ExternalSyntheticLambda2, 150L);
                                    } finally {
                                    }
                                } finally {
                                }
                            } catch (IOException e) {
                                Log.w("AvatarPhotoController", "Failed to copy photo", e);
                            }
                        }
                    }).get();
                    break;
                } catch (InterruptedException | ExecutionException e) {
                    Log.e("AvatarPhotoController", "Error performing copy-and-crop", e);
                }
            case 1002:
                if (uri.equals(data)) {
                    avatarPhotoController.cropPhoto(data);
                    break;
                } else {
                    try {
                        ThreadUtils.postOnBackgroundThread(new Runnable() { // from class: com.android.settingslib.users.AvatarPhotoController$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                AvatarPhotoController avatarPhotoController2 = AvatarPhotoController.this;
                                Uri uri2 = data;
                                boolean z3 = z;
                                ContentResolver contentResolver = ((AvatarPhotoController.ContextInjectorImpl) avatarPhotoController2.mContextInjector).mContext.getContentResolver();
                                try {
                                    InputStream openInputStream = contentResolver.openInputStream(uri2);
                                    try {
                                        OutputStream openOutputStream = contentResolver.openOutputStream(avatarPhotoController2.mPreCropPictureUri);
                                        try {
                                            Streams.copy(openInputStream, openOutputStream);
                                            if (openOutputStream != null) {
                                                openOutputStream.close();
                                            }
                                            if (openInputStream != null) {
                                                openInputStream.close();
                                            }
                                            AvatarPhotoController$$ExternalSyntheticLambda2 avatarPhotoController$$ExternalSyntheticLambda2 = new AvatarPhotoController$$ExternalSyntheticLambda2(avatarPhotoController2, 1);
                                            if (!z3) {
                                                ThreadUtils.postOnMainThread(avatarPhotoController$$ExternalSyntheticLambda2);
                                                return;
                                            }
                                            if (ThreadUtils.sMainThreadHandler == null) {
                                                ThreadUtils.sMainThreadHandler = new Handler(Looper.getMainLooper());
                                            }
                                            ThreadUtils.sMainThreadHandler.postDelayed(avatarPhotoController$$ExternalSyntheticLambda2, 150L);
                                        } finally {
                                        }
                                    } finally {
                                    }
                                } catch (IOException e2) {
                                    Log.w("AvatarPhotoController", "Failed to copy photo", e2);
                                }
                            }
                        }).get();
                        break;
                    } catch (InterruptedException | ExecutionException e2) {
                        Log.e("AvatarPhotoController", "Error performing copy-and-crop", e2);
                        return;
                    }
                }
            case 1003:
                AvatarPickerActivity avatarPickerActivity = ((AvatarPhotoController.AvatarUiImpl) avatarPhotoController.mAvatarUi).mActivity;
                avatarPickerActivity.getClass();
                Intent intent2 = new Intent();
                intent2.setData(data);
                avatarPickerActivity.setResult(-1, intent2);
                avatarPickerActivity.finish();
                break;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:10:0x003f  */
    /* JADX WARN: Removed duplicated region for block: B:13:0x0111  */
    /* JADX WARN: Removed duplicated region for block: B:16:0x012a  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0174  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0197  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x01b9  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x01be  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x01e2 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:40:0x01fa A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:43:0x0209  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x023c  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x0247  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x01bb  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x0199  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x0046 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r0v21, types: [androidx.recyclerview.widget.RecyclerView] */
    /* JADX WARN: Type inference failed for: r0v29 */
    /* JADX WARN: Type inference failed for: r0v30 */
    /* JADX WARN: Type inference failed for: r0v36 */
    /* JADX WARN: Type inference failed for: r0v41 */
    /* JADX WARN: Type inference failed for: r0v6 */
    /* JADX WARN: Type inference failed for: r0v7 */
    /* JADX WARN: Type inference failed for: r1v19 */
    /* JADX WARN: Type inference failed for: r1v20 */
    /* JADX WARN: Type inference failed for: r1v21 */
    /* JADX WARN: Type inference failed for: r1v23 */
    /* JADX WARN: Type inference failed for: r1v24 */
    /* JADX WARN: Type inference failed for: r1v6 */
    /* JADX WARN: Type inference failed for: r1v7 */
    /* JADX WARN: Type inference failed for: r1v8 */
    /* JADX WARN: Type inference failed for: r1v9 */
    /* JADX WARN: Type inference failed for: r2v6 */
    /* JADX WARN: Type inference failed for: r2v7 */
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void onCreate(Bundle bundle) {
        ?? r0;
        int i;
        Button button;
        Button button2;
        ActionBar supportActionBar;
        Intent intent;
        ?? r1;
        LinearLayout linearLayout;
        LinearLayout linearLayout2;
        String stringExtra;
        super.onCreate(bundle);
        setTheme(2132018513);
        Logger logger = ThemeHelper.LOG;
        ?? r2 = 0;
        final int i2 = 1;
        if (PartnerConfigHelper.applyDynamicColorBundle == null) {
            try {
                PartnerConfigHelper.applyDynamicColorBundle = getContentResolver().call(PartnerConfigHelper.getContentUri(this), PartnerConfigHelper.IS_DYNAMIC_COLOR_ENABLED_METHOD, (String) null, (Bundle) null);
            } catch (IllegalArgumentException | SecurityException unused) {
                Log.w("PartnerConfigHelper", "SetupWizard dynamic color supporting status unknown; return as false.");
                PartnerConfigHelper.applyDynamicColorBundle = null;
            }
        }
        Bundle bundle2 = PartnerConfigHelper.applyDynamicColorBundle;
        if (bundle2 != null && bundle2.getBoolean(PartnerConfigHelper.IS_DYNAMIC_COLOR_ENABLED_METHOD, false)) {
            r0 = true;
            Logger logger2 = ThemeHelper.LOG;
            final int i3 = 3;
            if (r0 == true) {
                logger2.m239w("SetupWizard does not support the dynamic color or supporting status unknown.");
            } else {
                try {
                    Activity lookupActivityFromContext = PartnerCustomizationLayout.lookupActivityFromContext(this);
                    try {
                        boolean isAnySetupWizard = WizardManagerHelper.isAnySetupWizard(PartnerCustomizationLayout.lookupActivityFromContext(this).getIntent());
                        boolean isSetupWizardDayNightEnabled = PartnerConfigHelper.isSetupWizardDayNightEnabled(this);
                        if (!isAnySetupWizard || BuildCompatUtils.isAtLeastU()) {
                            i = isSetupWizardDayNightEnabled ? 2132017945 : 2132017946;
                            logger2.atInfo("Return ".concat(isSetupWizardDayNightEnabled ? "SudFullDynamicColorTheme_DayNight" : "SudFullDynamicColorTheme_Light"));
                        } else {
                            i = isSetupWizardDayNightEnabled ? 2132017931 : 2132017932;
                        }
                        String str = "Gets the dynamic accentColor: [Light] " + ThemeHelper.colorIntToHex(R.color.sud_dynamic_color_accent_glif_v3_light, this) + ", " + ThemeHelper.colorIntToHex(android.R.color.system_accent1_600, this) + ", [Dark] " + ThemeHelper.colorIntToHex(R.color.sud_dynamic_color_accent_glif_v3_dark, this) + ", " + ThemeHelper.colorIntToHex(android.R.color.system_accent1_100, this);
                        if (Log.isLoggable("SetupLibrary", 3)) {
                            Log.d("SetupLibrary", logger2.prefix.concat(str));
                        } else {
                            logger2.getClass();
                        }
                    } catch (IllegalArgumentException e) {
                        String message = e.getMessage();
                        Objects.requireNonNull(message);
                        logger2.m237e(message);
                        i = 0;
                    }
                    if (i != 0) {
                        lookupActivityFromContext.setTheme(i);
                    } else {
                        logger2.m239w("Error occurred on getting dynamic color theme.");
                    }
                } catch (IllegalArgumentException e2) {
                    String message2 = e2.getMessage();
                    Objects.requireNonNull(message2);
                    logger2.m237e(message2);
                }
            }
            setContentView(R.layout.sec_avatar_picker);
            button = (Button) findViewById(R.id.save_button);
            this.mSaveButton = button;
            if (button != null) {
                saveButtonSetEnabled(false);
                final int i4 = 2;
                this.mSaveButton.setOnClickListener(new View.OnClickListener(this) { // from class: com.android.settingslib.users.AvatarPickerActivity$$ExternalSyntheticLambda0
                    public final /* synthetic */ AvatarPickerActivity f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        switch (i4) {
                            case 0:
                                AvatarPhotoController avatarPhotoController = this.f$0.mAvatarPhotoController;
                                avatarPhotoController.getClass();
                                Intent intent2 = new Intent("android.intent.action.GET_CONTENT", (Uri) null);
                                intent2.setPackage("com.sec.android.gallery3d");
                                intent2.setType("image/*");
                                ((AvatarPhotoController.AvatarUiImpl) avatarPhotoController.mAvatarUi).mActivity.startActivityForResult(intent2, 1001);
                                return;
                            case 1:
                                AvatarPhotoController avatarPhotoController2 = this.f$0.mAvatarPhotoController;
                                avatarPhotoController2.getClass();
                                Intent intent3 = new Intent("android.media.action.IMAGE_CAPTURE_SECURE");
                                Uri uri = avatarPhotoController2.mTakePictureUri;
                                intent3.putExtra("output", uri);
                                intent3.addFlags(3);
                                intent3.setClipData(ClipData.newRawUri("output", uri));
                                ((AvatarPhotoController.AvatarUiImpl) avatarPhotoController2.mAvatarUi).mActivity.startActivityForResult(intent3, 1002);
                                return;
                            case 2:
                                AvatarPickerActivity.AvatarAdapter avatarAdapter = this.f$0.mAdapter;
                                int i5 = avatarAdapter.mSelectedPosition + 0;
                                TypedArray typedArray = avatarAdapter.mPreselectedImages;
                                int length = typedArray.length();
                                AvatarPickerActivity avatarPickerActivity = AvatarPickerActivity.this;
                                if (length <= 0) {
                                    int i6 = avatarAdapter.mUserIconColors[i5];
                                    avatarPickerActivity.getClass();
                                    Intent intent4 = new Intent();
                                    intent4.putExtra("default_icon_tint_color", i6);
                                    avatarPickerActivity.setResult(-1, intent4);
                                    avatarPickerActivity.finish();
                                    return;
                                }
                                int resourceId = typedArray.getResourceId(i5, -1);
                                if (resourceId == -1) {
                                    throw new IllegalStateException("Preselected avatar images must be resources.");
                                }
                                Uri build = new Uri.Builder().scheme("android.resource").authority(avatarPickerActivity.getResources().getResourcePackageName(resourceId)).appendPath(avatarPickerActivity.getResources().getResourceTypeName(resourceId)).appendPath(avatarPickerActivity.getResources().getResourceEntryName(resourceId)).build();
                                Intent intent5 = new Intent();
                                intent5.setData(build);
                                avatarPickerActivity.setResult(-1, intent5);
                                avatarPickerActivity.finish();
                                return;
                            default:
                                AvatarPickerActivity avatarPickerActivity2 = this.f$0;
                                int i7 = AvatarPickerActivity.$r8$clinit;
                                avatarPickerActivity2.setResult(0);
                                avatarPickerActivity2.finish();
                                return;
                        }
                    }
                });
            }
            button2 = (Button) findViewById(R.id.cancel_button);
            if (button2 != null) {
                button2.setOnClickListener(new View.OnClickListener(this) { // from class: com.android.settingslib.users.AvatarPickerActivity$$ExternalSyntheticLambda0
                    public final /* synthetic */ AvatarPickerActivity f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        switch (i3) {
                            case 0:
                                AvatarPhotoController avatarPhotoController = this.f$0.mAvatarPhotoController;
                                avatarPhotoController.getClass();
                                Intent intent2 = new Intent("android.intent.action.GET_CONTENT", (Uri) null);
                                intent2.setPackage("com.sec.android.gallery3d");
                                intent2.setType("image/*");
                                ((AvatarPhotoController.AvatarUiImpl) avatarPhotoController.mAvatarUi).mActivity.startActivityForResult(intent2, 1001);
                                return;
                            case 1:
                                AvatarPhotoController avatarPhotoController2 = this.f$0.mAvatarPhotoController;
                                avatarPhotoController2.getClass();
                                Intent intent3 = new Intent("android.media.action.IMAGE_CAPTURE_SECURE");
                                Uri uri = avatarPhotoController2.mTakePictureUri;
                                intent3.putExtra("output", uri);
                                intent3.addFlags(3);
                                intent3.setClipData(ClipData.newRawUri("output", uri));
                                ((AvatarPhotoController.AvatarUiImpl) avatarPhotoController2.mAvatarUi).mActivity.startActivityForResult(intent3, 1002);
                                return;
                            case 2:
                                AvatarPickerActivity.AvatarAdapter avatarAdapter = this.f$0.mAdapter;
                                int i5 = avatarAdapter.mSelectedPosition + 0;
                                TypedArray typedArray = avatarAdapter.mPreselectedImages;
                                int length = typedArray.length();
                                AvatarPickerActivity avatarPickerActivity = AvatarPickerActivity.this;
                                if (length <= 0) {
                                    int i6 = avatarAdapter.mUserIconColors[i5];
                                    avatarPickerActivity.getClass();
                                    Intent intent4 = new Intent();
                                    intent4.putExtra("default_icon_tint_color", i6);
                                    avatarPickerActivity.setResult(-1, intent4);
                                    avatarPickerActivity.finish();
                                    return;
                                }
                                int resourceId = typedArray.getResourceId(i5, -1);
                                if (resourceId == -1) {
                                    throw new IllegalStateException("Preselected avatar images must be resources.");
                                }
                                Uri build = new Uri.Builder().scheme("android.resource").authority(avatarPickerActivity.getResources().getResourcePackageName(resourceId)).appendPath(avatarPickerActivity.getResources().getResourceTypeName(resourceId)).appendPath(avatarPickerActivity.getResources().getResourceEntryName(resourceId)).build();
                                Intent intent5 = new Intent();
                                intent5.setData(build);
                                avatarPickerActivity.setResult(-1, intent5);
                                avatarPickerActivity.finish();
                                return;
                            default:
                                AvatarPickerActivity avatarPickerActivity2 = this.f$0;
                                int i7 = AvatarPickerActivity.$r8$clinit;
                                avatarPickerActivity2.setResult(0);
                                avatarPickerActivity2.finish();
                                return;
                        }
                    }
                });
            }
            ?? r02 = (RecyclerView) findViewById(R.id.avatar_grid);
            AvatarAdapter avatarAdapter = new AvatarAdapter();
            this.mAdapter = avatarAdapter;
            r02.setAdapter(avatarAdapter);
            r02.setLayoutManager(new AutoFitGridLayoutManager(this, this, getResources().getInteger(R.integer.avatar_picker_columns)));
            r02.addItemDecoration(new GridItemDecoration(this, this, true));
            r02.mHasFixedSize = true;
            setSupportActionBar((Toolbar) findViewById(R.id.action_bar));
            supportActionBar = getSupportActionBar();
            if (supportActionBar != null) {
                supportActionBar.setDisplayHomeAsUpEnabled(true);
                supportActionBar.setHomeButtonEnabled();
                supportActionBar.setDisplayShowTitleEnabled(true);
                supportActionBar.setTitle();
            }
            ?? r03 = getPackageManager().queryIntentActivities(new Intent("android.media.action.IMAGE_CAPTURE"), 65536).size() <= 0;
            intent = new Intent("android.intent.action.GET_CONTENT", (Uri) null);
            intent.setPackage("com.sec.android.gallery3d");
            intent.setType("image/*");
            if ((getPackageManager().queryIntentActivities(intent, 0).size() <= 0) != false) {
                KeyguardManager keyguardManager = (KeyguardManager) getSystemService(KeyguardManager.class);
                if ((keyguardManager == null || keyguardManager.isDeviceLocked()) == false) {
                    r1 = true;
                    linearLayout = (LinearLayout) findViewById(R.id.gallery_btn);
                    if (linearLayout != null && r1 != false) {
                        linearLayout.setVisibility(0);
                        final ?? r22 = r2 == true ? 1 : 0;
                        linearLayout.setOnClickListener(new View.OnClickListener(this) { // from class: com.android.settingslib.users.AvatarPickerActivity$$ExternalSyntheticLambda0
                            public final /* synthetic */ AvatarPickerActivity f$0;

                            {
                                this.f$0 = this;
                            }

                            @Override // android.view.View.OnClickListener
                            public final void onClick(View view) {
                                switch (r22) {
                                    case 0:
                                        AvatarPhotoController avatarPhotoController = this.f$0.mAvatarPhotoController;
                                        avatarPhotoController.getClass();
                                        Intent intent2 = new Intent("android.intent.action.GET_CONTENT", (Uri) null);
                                        intent2.setPackage("com.sec.android.gallery3d");
                                        intent2.setType("image/*");
                                        ((AvatarPhotoController.AvatarUiImpl) avatarPhotoController.mAvatarUi).mActivity.startActivityForResult(intent2, 1001);
                                        return;
                                    case 1:
                                        AvatarPhotoController avatarPhotoController2 = this.f$0.mAvatarPhotoController;
                                        avatarPhotoController2.getClass();
                                        Intent intent3 = new Intent("android.media.action.IMAGE_CAPTURE_SECURE");
                                        Uri uri = avatarPhotoController2.mTakePictureUri;
                                        intent3.putExtra("output", uri);
                                        intent3.addFlags(3);
                                        intent3.setClipData(ClipData.newRawUri("output", uri));
                                        ((AvatarPhotoController.AvatarUiImpl) avatarPhotoController2.mAvatarUi).mActivity.startActivityForResult(intent3, 1002);
                                        return;
                                    case 2:
                                        AvatarPickerActivity.AvatarAdapter avatarAdapter2 = this.f$0.mAdapter;
                                        int i5 = avatarAdapter2.mSelectedPosition + 0;
                                        TypedArray typedArray = avatarAdapter2.mPreselectedImages;
                                        int length = typedArray.length();
                                        AvatarPickerActivity avatarPickerActivity = AvatarPickerActivity.this;
                                        if (length <= 0) {
                                            int i6 = avatarAdapter2.mUserIconColors[i5];
                                            avatarPickerActivity.getClass();
                                            Intent intent4 = new Intent();
                                            intent4.putExtra("default_icon_tint_color", i6);
                                            avatarPickerActivity.setResult(-1, intent4);
                                            avatarPickerActivity.finish();
                                            return;
                                        }
                                        int resourceId = typedArray.getResourceId(i5, -1);
                                        if (resourceId == -1) {
                                            throw new IllegalStateException("Preselected avatar images must be resources.");
                                        }
                                        Uri build = new Uri.Builder().scheme("android.resource").authority(avatarPickerActivity.getResources().getResourcePackageName(resourceId)).appendPath(avatarPickerActivity.getResources().getResourceTypeName(resourceId)).appendPath(avatarPickerActivity.getResources().getResourceEntryName(resourceId)).build();
                                        Intent intent5 = new Intent();
                                        intent5.setData(build);
                                        avatarPickerActivity.setResult(-1, intent5);
                                        avatarPickerActivity.finish();
                                        return;
                                    default:
                                        AvatarPickerActivity avatarPickerActivity2 = this.f$0;
                                        int i7 = AvatarPickerActivity.$r8$clinit;
                                        avatarPickerActivity2.setResult(0);
                                        avatarPickerActivity2.finish();
                                        return;
                                }
                            }
                        });
                    }
                    linearLayout2 = (LinearLayout) findViewById(R.id.camera_btn);
                    if (linearLayout2 != null && r03 != false) {
                        linearLayout2.setVisibility(0);
                        linearLayout2.setOnClickListener(new View.OnClickListener(this) { // from class: com.android.settingslib.users.AvatarPickerActivity$$ExternalSyntheticLambda0
                            public final /* synthetic */ AvatarPickerActivity f$0;

                            {
                                this.f$0 = this;
                            }

                            @Override // android.view.View.OnClickListener
                            public final void onClick(View view) {
                                switch (i2) {
                                    case 0:
                                        AvatarPhotoController avatarPhotoController = this.f$0.mAvatarPhotoController;
                                        avatarPhotoController.getClass();
                                        Intent intent2 = new Intent("android.intent.action.GET_CONTENT", (Uri) null);
                                        intent2.setPackage("com.sec.android.gallery3d");
                                        intent2.setType("image/*");
                                        ((AvatarPhotoController.AvatarUiImpl) avatarPhotoController.mAvatarUi).mActivity.startActivityForResult(intent2, 1001);
                                        return;
                                    case 1:
                                        AvatarPhotoController avatarPhotoController2 = this.f$0.mAvatarPhotoController;
                                        avatarPhotoController2.getClass();
                                        Intent intent3 = new Intent("android.media.action.IMAGE_CAPTURE_SECURE");
                                        Uri uri = avatarPhotoController2.mTakePictureUri;
                                        intent3.putExtra("output", uri);
                                        intent3.addFlags(3);
                                        intent3.setClipData(ClipData.newRawUri("output", uri));
                                        ((AvatarPhotoController.AvatarUiImpl) avatarPhotoController2.mAvatarUi).mActivity.startActivityForResult(intent3, 1002);
                                        return;
                                    case 2:
                                        AvatarPickerActivity.AvatarAdapter avatarAdapter2 = this.f$0.mAdapter;
                                        int i5 = avatarAdapter2.mSelectedPosition + 0;
                                        TypedArray typedArray = avatarAdapter2.mPreselectedImages;
                                        int length = typedArray.length();
                                        AvatarPickerActivity avatarPickerActivity = AvatarPickerActivity.this;
                                        if (length <= 0) {
                                            int i6 = avatarAdapter2.mUserIconColors[i5];
                                            avatarPickerActivity.getClass();
                                            Intent intent4 = new Intent();
                                            intent4.putExtra("default_icon_tint_color", i6);
                                            avatarPickerActivity.setResult(-1, intent4);
                                            avatarPickerActivity.finish();
                                            return;
                                        }
                                        int resourceId = typedArray.getResourceId(i5, -1);
                                        if (resourceId == -1) {
                                            throw new IllegalStateException("Preselected avatar images must be resources.");
                                        }
                                        Uri build = new Uri.Builder().scheme("android.resource").authority(avatarPickerActivity.getResources().getResourcePackageName(resourceId)).appendPath(avatarPickerActivity.getResources().getResourceTypeName(resourceId)).appendPath(avatarPickerActivity.getResources().getResourceEntryName(resourceId)).build();
                                        Intent intent5 = new Intent();
                                        intent5.setData(build);
                                        avatarPickerActivity.setResult(-1, intent5);
                                        avatarPickerActivity.finish();
                                        return;
                                    default:
                                        AvatarPickerActivity avatarPickerActivity2 = this.f$0;
                                        int i7 = AvatarPickerActivity.$r8$clinit;
                                        avatarPickerActivity2.setResult(0);
                                        avatarPickerActivity2.finish();
                                        return;
                                }
                            }
                        });
                    }
                    if (bundle != null) {
                        this.mWaitingForActivityResult = bundle.getBoolean("awaiting_result", false);
                        this.mAdapter.mSelectedPosition = bundle.getInt("selected_position", -1);
                        saveButtonSetEnabled(this.mAdapter.mSelectedPosition != -1);
                    }
                    AvatarPhotoController.AvatarUiImpl avatarUiImpl = new AvatarPhotoController.AvatarUiImpl(this);
                    stringExtra = getIntent().getStringExtra("file_authority");
                    if (stringExtra == null) {
                        throw new IllegalStateException("File authority must be provided");
                    }
                    this.mAvatarPhotoController = new AvatarPhotoController(avatarUiImpl, new AvatarPhotoController.ContextInjectorImpl(this, stringExtra), this.mWaitingForActivityResult);
                    return;
                }
            }
            r1 = false;
            linearLayout = (LinearLayout) findViewById(R.id.gallery_btn);
            if (linearLayout != null) {
                linearLayout.setVisibility(0);
                final int r222 = r2 == true ? 1 : 0;
                linearLayout.setOnClickListener(new View.OnClickListener(this) { // from class: com.android.settingslib.users.AvatarPickerActivity$$ExternalSyntheticLambda0
                    public final /* synthetic */ AvatarPickerActivity f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        switch (r222) {
                            case 0:
                                AvatarPhotoController avatarPhotoController = this.f$0.mAvatarPhotoController;
                                avatarPhotoController.getClass();
                                Intent intent2 = new Intent("android.intent.action.GET_CONTENT", (Uri) null);
                                intent2.setPackage("com.sec.android.gallery3d");
                                intent2.setType("image/*");
                                ((AvatarPhotoController.AvatarUiImpl) avatarPhotoController.mAvatarUi).mActivity.startActivityForResult(intent2, 1001);
                                return;
                            case 1:
                                AvatarPhotoController avatarPhotoController2 = this.f$0.mAvatarPhotoController;
                                avatarPhotoController2.getClass();
                                Intent intent3 = new Intent("android.media.action.IMAGE_CAPTURE_SECURE");
                                Uri uri = avatarPhotoController2.mTakePictureUri;
                                intent3.putExtra("output", uri);
                                intent3.addFlags(3);
                                intent3.setClipData(ClipData.newRawUri("output", uri));
                                ((AvatarPhotoController.AvatarUiImpl) avatarPhotoController2.mAvatarUi).mActivity.startActivityForResult(intent3, 1002);
                                return;
                            case 2:
                                AvatarPickerActivity.AvatarAdapter avatarAdapter2 = this.f$0.mAdapter;
                                int i5 = avatarAdapter2.mSelectedPosition + 0;
                                TypedArray typedArray = avatarAdapter2.mPreselectedImages;
                                int length = typedArray.length();
                                AvatarPickerActivity avatarPickerActivity = AvatarPickerActivity.this;
                                if (length <= 0) {
                                    int i6 = avatarAdapter2.mUserIconColors[i5];
                                    avatarPickerActivity.getClass();
                                    Intent intent4 = new Intent();
                                    intent4.putExtra("default_icon_tint_color", i6);
                                    avatarPickerActivity.setResult(-1, intent4);
                                    avatarPickerActivity.finish();
                                    return;
                                }
                                int resourceId = typedArray.getResourceId(i5, -1);
                                if (resourceId == -1) {
                                    throw new IllegalStateException("Preselected avatar images must be resources.");
                                }
                                Uri build = new Uri.Builder().scheme("android.resource").authority(avatarPickerActivity.getResources().getResourcePackageName(resourceId)).appendPath(avatarPickerActivity.getResources().getResourceTypeName(resourceId)).appendPath(avatarPickerActivity.getResources().getResourceEntryName(resourceId)).build();
                                Intent intent5 = new Intent();
                                intent5.setData(build);
                                avatarPickerActivity.setResult(-1, intent5);
                                avatarPickerActivity.finish();
                                return;
                            default:
                                AvatarPickerActivity avatarPickerActivity2 = this.f$0;
                                int i7 = AvatarPickerActivity.$r8$clinit;
                                avatarPickerActivity2.setResult(0);
                                avatarPickerActivity2.finish();
                                return;
                        }
                    }
                });
            }
            linearLayout2 = (LinearLayout) findViewById(R.id.camera_btn);
            if (linearLayout2 != null) {
                linearLayout2.setVisibility(0);
                linearLayout2.setOnClickListener(new View.OnClickListener(this) { // from class: com.android.settingslib.users.AvatarPickerActivity$$ExternalSyntheticLambda0
                    public final /* synthetic */ AvatarPickerActivity f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        switch (i2) {
                            case 0:
                                AvatarPhotoController avatarPhotoController = this.f$0.mAvatarPhotoController;
                                avatarPhotoController.getClass();
                                Intent intent2 = new Intent("android.intent.action.GET_CONTENT", (Uri) null);
                                intent2.setPackage("com.sec.android.gallery3d");
                                intent2.setType("image/*");
                                ((AvatarPhotoController.AvatarUiImpl) avatarPhotoController.mAvatarUi).mActivity.startActivityForResult(intent2, 1001);
                                return;
                            case 1:
                                AvatarPhotoController avatarPhotoController2 = this.f$0.mAvatarPhotoController;
                                avatarPhotoController2.getClass();
                                Intent intent3 = new Intent("android.media.action.IMAGE_CAPTURE_SECURE");
                                Uri uri = avatarPhotoController2.mTakePictureUri;
                                intent3.putExtra("output", uri);
                                intent3.addFlags(3);
                                intent3.setClipData(ClipData.newRawUri("output", uri));
                                ((AvatarPhotoController.AvatarUiImpl) avatarPhotoController2.mAvatarUi).mActivity.startActivityForResult(intent3, 1002);
                                return;
                            case 2:
                                AvatarPickerActivity.AvatarAdapter avatarAdapter2 = this.f$0.mAdapter;
                                int i5 = avatarAdapter2.mSelectedPosition + 0;
                                TypedArray typedArray = avatarAdapter2.mPreselectedImages;
                                int length = typedArray.length();
                                AvatarPickerActivity avatarPickerActivity = AvatarPickerActivity.this;
                                if (length <= 0) {
                                    int i6 = avatarAdapter2.mUserIconColors[i5];
                                    avatarPickerActivity.getClass();
                                    Intent intent4 = new Intent();
                                    intent4.putExtra("default_icon_tint_color", i6);
                                    avatarPickerActivity.setResult(-1, intent4);
                                    avatarPickerActivity.finish();
                                    return;
                                }
                                int resourceId = typedArray.getResourceId(i5, -1);
                                if (resourceId == -1) {
                                    throw new IllegalStateException("Preselected avatar images must be resources.");
                                }
                                Uri build = new Uri.Builder().scheme("android.resource").authority(avatarPickerActivity.getResources().getResourcePackageName(resourceId)).appendPath(avatarPickerActivity.getResources().getResourceTypeName(resourceId)).appendPath(avatarPickerActivity.getResources().getResourceEntryName(resourceId)).build();
                                Intent intent5 = new Intent();
                                intent5.setData(build);
                                avatarPickerActivity.setResult(-1, intent5);
                                avatarPickerActivity.finish();
                                return;
                            default:
                                AvatarPickerActivity avatarPickerActivity2 = this.f$0;
                                int i7 = AvatarPickerActivity.$r8$clinit;
                                avatarPickerActivity2.setResult(0);
                                avatarPickerActivity2.finish();
                                return;
                        }
                    }
                });
            }
            if (bundle != null) {
            }
            AvatarPhotoController.AvatarUiImpl avatarUiImpl2 = new AvatarPhotoController.AvatarUiImpl(this);
            stringExtra = getIntent().getStringExtra("file_authority");
            if (stringExtra == null) {
            }
        }
        r0 = false;
        Logger logger22 = ThemeHelper.LOG;
        final int i32 = 3;
        if (r0 == true) {
        }
        setContentView(R.layout.sec_avatar_picker);
        button = (Button) findViewById(R.id.save_button);
        this.mSaveButton = button;
        if (button != null) {
        }
        button2 = (Button) findViewById(R.id.cancel_button);
        if (button2 != null) {
        }
        ?? r022 = (RecyclerView) findViewById(R.id.avatar_grid);
        AvatarAdapter avatarAdapter2 = new AvatarAdapter();
        this.mAdapter = avatarAdapter2;
        r022.setAdapter(avatarAdapter2);
        r022.setLayoutManager(new AutoFitGridLayoutManager(this, this, getResources().getInteger(R.integer.avatar_picker_columns)));
        r022.addItemDecoration(new GridItemDecoration(this, this, true));
        r022.mHasFixedSize = true;
        setSupportActionBar((Toolbar) findViewById(R.id.action_bar));
        supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
        }
        if (getPackageManager().queryIntentActivities(new Intent("android.media.action.IMAGE_CAPTURE"), 65536).size() <= 0) {
        }
        intent = new Intent("android.intent.action.GET_CONTENT", (Uri) null);
        intent.setPackage("com.sec.android.gallery3d");
        intent.setType("image/*");
        if ((getPackageManager().queryIntentActivities(intent, 0).size() <= 0) != false) {
        }
        r1 = false;
        linearLayout = (LinearLayout) findViewById(R.id.gallery_btn);
        if (linearLayout != null) {
        }
        linearLayout2 = (LinearLayout) findViewById(R.id.camera_btn);
        if (linearLayout2 != null) {
        }
        if (bundle != null) {
        }
        AvatarPhotoController.AvatarUiImpl avatarUiImpl22 = new AvatarPhotoController.AvatarUiImpl(this);
        stringExtra = getIntent().getStringExtra("file_authority");
        if (stringExtra == null) {
        }
    }

    @Override // android.app.Activity
    public final boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == 16908332) {
            setResult(0);
            finish();
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public final void onResume() {
        super.onResume();
        this.mAdapter.getClass();
    }

    @Override // androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public final void onSaveInstanceState(Bundle bundle) {
        bundle.putBoolean("awaiting_result", this.mWaitingForActivityResult);
        bundle.putInt("selected_position", this.mAdapter.mSelectedPosition);
        super.onSaveInstanceState(bundle);
    }

    public final void saveButtonSetEnabled(boolean z) {
        Button button = this.mSaveButton;
        if (button != null) {
            button.setEnabled(z);
            this.mSaveButton.setAlpha(z ? 1.0f : 0.4f);
        }
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public final void startActivityForResult(Intent intent, int i) {
        this.mWaitingForActivityResult = true;
        super.startActivityForResult(intent, i);
    }
}
