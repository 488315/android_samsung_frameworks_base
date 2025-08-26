package com.android.settingslib.avatarpicker;

import android.app.Activity;
import android.app.KeyguardManager;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.EventLog;
import android.util.Log;
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
import com.android.keyguard.KeyguardSecurityContainer$UserSwitcherViewMode$2$$ExternalSyntheticOutline0;
import com.android.settingslib.avatarpicker.AvatarPhotoController;
import com.android.settingslib.avatarpicker.AvatarPickerActivity;
import com.android.systemui.R;
import com.google.android.setupcompat.PartnerCustomizationLayout;
import com.google.android.setupcompat.partnerconfig.PartnerConfigHelper;
import com.google.android.setupcompat.util.Logger;
import com.google.android.setupcompat.util.WizardManagerHelper;
import com.google.android.setupdesign.util.ThemeHelper;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/* loaded from: classes.dex */
public class AvatarPickerActivity extends AppCompatActivity {
    public static final /* synthetic */ int $r8$clinit = 0;
    public AvatarAdapter mAdapter;
    public AvatarPhotoController mAvatarPhotoController;
    public Button mSaveButton;
    public boolean mWaitingForActivityResult;

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
            int iMax = Math.max(1, this.mTotalSpace / this.mColumnWidth);
            int i = this.mSpanCount;
            if (iMax > i) {
                iMax = i;
            }
            setSpanCount(iMax);
            super.onLayoutChildren(recycler, state);
        }
    }

    public class AvatarAdapter extends RecyclerView.Adapter {
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
                int length = this.mPreselectedImages.length();
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
                Drawable drawable = this.mPreselectedImages.getDrawable(i2);
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
            return ((ArrayList) this.mImageDrawables).size();
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public final void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int i) {
            AvatarViewHolder avatarViewHolder = (AvatarViewHolder) viewHolder;
            if (i >= 0) {
                avatarViewHolder.mImageView.setSelected(i == this.mSelectedPosition);
                avatarViewHolder.mImageView.setImageDrawable((Drawable) ((ArrayList) this.mImageDrawables).get(i));
                List list = this.mImageDescriptions;
                if (list == null || i >= list.size()) {
                    avatarViewHolder.mImageView.setContentDescription(AvatarPickerActivity.this.getString(R.string.default_user_icon_description));
                } else {
                    avatarViewHolder.mImageView.setContentDescription((String) this.mImageDescriptions.get(i));
                }
            }
            avatarViewHolder.mImageView.setOnClickListener(new View.OnClickListener() { // from class: com.android.settingslib.avatarpicker.AvatarPickerActivity$AvatarAdapter$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    AvatarPickerActivity.AvatarAdapter avatarAdapter = this.f$0;
                    int i2 = i;
                    int i3 = avatarAdapter.mSelectedPosition;
                    AvatarPickerActivity avatarPickerActivity = AvatarPickerActivity.this;
                    if (i3 == i2) {
                        avatarAdapter.mSelectedPosition = -1;
                        avatarAdapter.notifyItemChanged(i2);
                        int i4 = AvatarPickerActivity.$r8$clinit;
                        avatarPickerActivity.saveButtonSetEnabled(false);
                        return;
                    }
                    avatarAdapter.mSelectedPosition = i2;
                    avatarAdapter.notifyItemChanged(i2);
                    if (i3 != -1) {
                        avatarAdapter.notifyItemChanged(i3);
                    } else {
                        int i5 = AvatarPickerActivity.$r8$clinit;
                        avatarPickerActivity.saveButtonSetEnabled(true);
                    }
                }
            });
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public final RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            return new AvatarViewHolder(KeyguardSecurityContainer$UserSwitcherViewMode$2$$ExternalSyntheticOutline0.m(viewGroup, R.layout.avatar_item, viewGroup, false));
        }
    }

    public class AvatarViewHolder extends RecyclerView.ViewHolder {
        public final ImageView mImageView;

        public AvatarViewHolder(View view) {
            super(view);
            this.mImageView = (ImageView) view.findViewById(R.id.avatar_image);
        }
    }

    public class GridItemDecoration extends RecyclerView.ItemDecoration {
        public final boolean mIncludeEdge;
        public final boolean mRtl;
        public final int mSpacing;
        public final int mSpacingTop;

        public GridItemDecoration(AvatarPickerActivity avatarPickerActivity, Context context, boolean z) {
            this.mSpacing = context.getResources().getDimensionPixelSize(R.dimen.sec_avatar_item_side_padding);
            this.mSpacingTop = context.getResources().getDimensionPixelSize(R.dimen.sec_avatar_item_top_bottom_padding);
            this.mIncludeEdge = z;
            this.mRtl = MenuPopupWindow$MenuDropDownListView$$ExternalSyntheticOutline0.m(context) == 1;
        }

        @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
        public final void getItemOffsets(Rect rect, View view, RecyclerView recyclerView, RecyclerView.State state) {
            RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
            if (layoutManager instanceof GridLayoutManager) {
                int i = ((GridLayoutManager) layoutManager).mSpanCount;
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

    public final void cancel$1$1() {
        setResult(0);
        finish();
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public final void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        this.mWaitingForActivityResult = false;
        AvatarPhotoController avatarPhotoController = this.mAvatarPhotoController;
        avatarPhotoController.getClass();
        if (i2 != -1) {
            return;
        }
        Uri data = (intent == null || intent.getData() == null) ? avatarPhotoController.mTakePictureUri : intent.getData();
        if (!"content".equals(data.getScheme())) {
            Log.e("AvatarPhotoController", "Invalid pictureUri scheme: " + data.getScheme());
            EventLog.writeEvent(1397638484, "172939189", -1, data.getPath());
        }
        switch (i) {
            case 1001:
                avatarPhotoController.copyAndCropPhoto(data, true);
                break;
            case 1002:
                if (!avatarPhotoController.mTakePictureUri.equals(data)) {
                    avatarPhotoController.copyAndCropPhoto(data, false);
                    break;
                } else {
                    avatarPhotoController.cropPhoto(data);
                    break;
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

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.activity.ComponentActivity, android.app.Activity, android.content.ComponentCallbacks
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        new Handler().postDelayed(new Runnable() { // from class: com.android.settingslib.avatarpicker.AvatarPickerActivity$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                AvatarPickerActivity avatarPickerActivity = this.f$0;
                int i = AvatarPickerActivity.$r8$clinit;
                RecyclerView recyclerView = (RecyclerView) avatarPickerActivity.findViewById(R.id.avatar_grid);
                int size = recyclerView.mItemDecorations.size();
                if (size <= 0) {
                    throw new IndexOutOfBoundsException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(size, "0 is an invalid index for size "));
                }
                int size2 = recyclerView.mItemDecorations.size();
                if (size2 <= 0) {
                    throw new IndexOutOfBoundsException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(size2, "0 is an invalid index for size "));
                }
                recyclerView.removeItemDecoration((RecyclerView.ItemDecoration) recyclerView.mItemDecorations.get(0));
                recyclerView.setLayoutManager(new AvatarPickerActivity.AutoFitGridLayoutManager(avatarPickerActivity, avatarPickerActivity, avatarPickerActivity.getResources().getInteger(R.integer.avatar_picker_columns)));
                recyclerView.addItemDecoration(new AvatarPickerActivity.GridItemDecoration(avatarPickerActivity, avatarPickerActivity, true));
            }
        }, 100L);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:31:0x00c7  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x00cb  */
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void onCreate(Bundle bundle) {
        int i;
        KeyguardManager keyguardManager;
        char c = 1;
        final int i2 = 0;
        super.onCreate(bundle);
        setTheme(R.style.Theme_SecSettings);
        Logger logger = ThemeHelper.LOG;
        boolean zIsGlifExpressiveEnabled = PartnerConfigHelper.isGlifExpressiveEnabled(this);
        Logger logger2 = ThemeHelper.LOG;
        if (zIsGlifExpressiveEnabled) {
            logger2.w("Dynamic color theme isn't needed to set in glif expressive theme.");
        } else if (PartnerConfigHelper.isSetupWizardDynamicColorEnabled(this)) {
            try {
                Logger logger3 = PartnerCustomizationLayout.LOG;
                Activity activityLookupActivityFromContext = PartnerConfigHelper.lookupActivityFromContext(this);
                if (PartnerConfigHelper.isGlifExpressiveEnabled(this)) {
                    i = 0;
                    if (i == 0) {
                        activityLookupActivityFromContext.setTheme(i);
                    } else {
                        logger2.w("Error occurred on getting dynamic color theme.");
                    }
                } else {
                    try {
                        boolean zIsAnySetupWizard = WizardManagerHelper.isAnySetupWizard(PartnerConfigHelper.lookupActivityFromContext(this).getIntent());
                        boolean zIsSetupWizardDayNightEnabled = PartnerConfigHelper.isSetupWizardDayNightEnabled(this);
                        boolean zIsSetupWizardFullDynamicColorEnabled = PartnerConfigHelper.isSetupWizardFullDynamicColorEnabled(this);
                        if (!zIsAnySetupWizard || zIsSetupWizardFullDynamicColorEnabled) {
                            i = zIsSetupWizardDayNightEnabled ? R.style.SudFullDynamicColorTheme_DayNight : R.style.SudFullDynamicColorTheme_Light;
                            logger2.atInfo("Return ".concat(zIsSetupWizardDayNightEnabled ? "SudFullDynamicColorTheme_DayNight" : "SudFullDynamicColorTheme_Light"));
                        } else {
                            i = zIsSetupWizardDayNightEnabled ? R.style.SudDynamicColorTheme_DayNight : R.style.SudDynamicColorTheme_Light;
                        }
                        logger2.atDebug("Gets the dynamic accentColor: [Light] " + ThemeHelper.colorIntToHex(this, R.color.sud_dynamic_color_accent_glif_v3_light) + ", " + ThemeHelper.colorIntToHex(this, android.R.color.system_accent1_600) + ", [Dark] " + ThemeHelper.colorIntToHex(this, R.color.sud_dynamic_color_accent_glif_v3_dark) + ", " + ThemeHelper.colorIntToHex(this, android.R.color.system_accent1_100));
                    } catch (IllegalArgumentException e) {
                        String message = e.getMessage();
                        Objects.requireNonNull(message);
                        logger2.e(message);
                    }
                    if (i == 0) {
                    }
                }
            } catch (IllegalArgumentException e2) {
                String message2 = e2.getMessage();
                Objects.requireNonNull(message2);
                logger2.e(message2);
            }
        } else {
            logger2.w("SetupWizard does not support the dynamic color or supporting status unknown.");
        }
        setContentView(R.layout.sec_avatar_picker);
        Button button = (Button) findViewById(R.id.save_button);
        this.mSaveButton = button;
        if (button != null) {
            saveButtonSetEnabled(false);
            final int i3 = 2;
            this.mSaveButton.setOnClickListener(new View.OnClickListener(this) { // from class: com.android.settingslib.avatarpicker.AvatarPickerActivity$$ExternalSyntheticLambda1
                public final /* synthetic */ AvatarPickerActivity f$0;

                {
                    this.f$0 = this;
                }

                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    int i4 = i3;
                    AvatarPickerActivity avatarPickerActivity = this.f$0;
                    switch (i4) {
                        case 0:
                            AvatarPhotoController avatarPhotoController = avatarPickerActivity.mAvatarPhotoController;
                            avatarPhotoController.getClass();
                            Intent intent = new Intent("android.intent.action.GET_CONTENT", (Uri) null);
                            intent.setPackage("com.sec.android.gallery3d");
                            intent.setType("image/*");
                            ((AvatarPhotoController.AvatarUiImpl) avatarPhotoController.mAvatarUi).mActivity.startActivityForResult(intent, 1001);
                            return;
                        case 1:
                            AvatarPhotoController avatarPhotoController2 = avatarPickerActivity.mAvatarPhotoController;
                            avatarPhotoController2.getClass();
                            Intent intent2 = new Intent("android.media.action.IMAGE_CAPTURE_SECURE");
                            Uri uri = avatarPhotoController2.mTakePictureUri;
                            intent2.putExtra("output", uri);
                            intent2.addFlags(3);
                            intent2.setClipData(ClipData.newRawUri("output", uri));
                            ((AvatarPhotoController.AvatarUiImpl) avatarPhotoController2.mAvatarUi).mActivity.startActivityForResult(intent2, 1002);
                            return;
                        case 2:
                            AvatarPickerActivity.AvatarAdapter avatarAdapter = avatarPickerActivity.mAdapter;
                            int i5 = avatarAdapter.mSelectedPosition;
                            int length = avatarAdapter.mPreselectedImages.length();
                            AvatarPickerActivity avatarPickerActivity2 = AvatarPickerActivity.this;
                            if (length <= 0) {
                                int i6 = avatarAdapter.mUserIconColors[i5];
                                avatarPickerActivity2.getClass();
                                Intent intent3 = new Intent();
                                intent3.putExtra("default_icon_tint_color", i6);
                                avatarPickerActivity2.setResult(-1, intent3);
                                avatarPickerActivity2.finish();
                                return;
                            }
                            int resourceId = avatarAdapter.mPreselectedImages.getResourceId(i5, -1);
                            if (resourceId == -1) {
                                throw new IllegalStateException("Preselected avatar images must be resources.");
                            }
                            Uri uriBuild = new Uri.Builder().scheme("android.resource").authority(avatarPickerActivity2.getResources().getResourcePackageName(resourceId)).appendPath(avatarPickerActivity2.getResources().getResourceTypeName(resourceId)).appendPath(avatarPickerActivity2.getResources().getResourceEntryName(resourceId)).build();
                            Intent intent4 = new Intent();
                            intent4.setData(uriBuild);
                            avatarPickerActivity2.setResult(-1, intent4);
                            avatarPickerActivity2.finish();
                            return;
                        default:
                            int i7 = AvatarPickerActivity.$r8$clinit;
                            avatarPickerActivity.cancel$1$1();
                            return;
                    }
                }
            });
        }
        Button button2 = (Button) findViewById(R.id.cancel_button);
        if (button2 != null) {
            final int i4 = 3;
            button2.setOnClickListener(new View.OnClickListener(this) { // from class: com.android.settingslib.avatarpicker.AvatarPickerActivity$$ExternalSyntheticLambda1
                public final /* synthetic */ AvatarPickerActivity f$0;

                {
                    this.f$0 = this;
                }

                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    int i42 = i4;
                    AvatarPickerActivity avatarPickerActivity = this.f$0;
                    switch (i42) {
                        case 0:
                            AvatarPhotoController avatarPhotoController = avatarPickerActivity.mAvatarPhotoController;
                            avatarPhotoController.getClass();
                            Intent intent = new Intent("android.intent.action.GET_CONTENT", (Uri) null);
                            intent.setPackage("com.sec.android.gallery3d");
                            intent.setType("image/*");
                            ((AvatarPhotoController.AvatarUiImpl) avatarPhotoController.mAvatarUi).mActivity.startActivityForResult(intent, 1001);
                            return;
                        case 1:
                            AvatarPhotoController avatarPhotoController2 = avatarPickerActivity.mAvatarPhotoController;
                            avatarPhotoController2.getClass();
                            Intent intent2 = new Intent("android.media.action.IMAGE_CAPTURE_SECURE");
                            Uri uri = avatarPhotoController2.mTakePictureUri;
                            intent2.putExtra("output", uri);
                            intent2.addFlags(3);
                            intent2.setClipData(ClipData.newRawUri("output", uri));
                            ((AvatarPhotoController.AvatarUiImpl) avatarPhotoController2.mAvatarUi).mActivity.startActivityForResult(intent2, 1002);
                            return;
                        case 2:
                            AvatarPickerActivity.AvatarAdapter avatarAdapter = avatarPickerActivity.mAdapter;
                            int i5 = avatarAdapter.mSelectedPosition;
                            int length = avatarAdapter.mPreselectedImages.length();
                            AvatarPickerActivity avatarPickerActivity2 = AvatarPickerActivity.this;
                            if (length <= 0) {
                                int i6 = avatarAdapter.mUserIconColors[i5];
                                avatarPickerActivity2.getClass();
                                Intent intent3 = new Intent();
                                intent3.putExtra("default_icon_tint_color", i6);
                                avatarPickerActivity2.setResult(-1, intent3);
                                avatarPickerActivity2.finish();
                                return;
                            }
                            int resourceId = avatarAdapter.mPreselectedImages.getResourceId(i5, -1);
                            if (resourceId == -1) {
                                throw new IllegalStateException("Preselected avatar images must be resources.");
                            }
                            Uri uriBuild = new Uri.Builder().scheme("android.resource").authority(avatarPickerActivity2.getResources().getResourcePackageName(resourceId)).appendPath(avatarPickerActivity2.getResources().getResourceTypeName(resourceId)).appendPath(avatarPickerActivity2.getResources().getResourceEntryName(resourceId)).build();
                            Intent intent4 = new Intent();
                            intent4.setData(uriBuild);
                            avatarPickerActivity2.setResult(-1, intent4);
                            avatarPickerActivity2.finish();
                            return;
                        default:
                            int i7 = AvatarPickerActivity.$r8$clinit;
                            avatarPickerActivity.cancel$1$1();
                            return;
                    }
                }
            });
        }
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.avatar_grid);
        AvatarAdapter avatarAdapter = new AvatarAdapter();
        this.mAdapter = avatarAdapter;
        recyclerView.setAdapter(avatarAdapter);
        recyclerView.setLayoutManager(new AutoFitGridLayoutManager(this, this, getResources().getInteger(R.integer.avatar_picker_columns)));
        recyclerView.addItemDecoration(new GridItemDecoration(this, this, true));
        recyclerView.mHasFixedSize = true;
        setSupportActionBar((Toolbar) findViewById(R.id.action_bar));
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
            supportActionBar.setHomeButtonEnabled();
            supportActionBar.setDisplayShowTitleEnabled(true);
            supportActionBar.setTitle();
        }
        Object[] objArr = getPackageManager().queryIntentActivities(new Intent("android.media.action.IMAGE_CAPTURE"), 65536).size() > 0;
        Intent intent = new Intent("android.intent.action.GET_CONTENT", (Uri) null);
        intent.setPackage("com.sec.android.gallery3d");
        intent.setType("image/*");
        Object[] objArr2 = (getPackageManager().queryIntentActivities(intent, 0).size() <= 0 || (keyguardManager = (KeyguardManager) getSystemService(KeyguardManager.class)) == null || keyguardManager.isDeviceLocked()) ? false : true;
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.gallery_btn);
        if (linearLayout != null && objArr2 != false) {
            linearLayout.setVisibility(0);
            linearLayout.setOnClickListener(new View.OnClickListener(this) { // from class: com.android.settingslib.avatarpicker.AvatarPickerActivity$$ExternalSyntheticLambda1
                public final /* synthetic */ AvatarPickerActivity f$0;

                {
                    this.f$0 = this;
                }

                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    int i42 = i2;
                    AvatarPickerActivity avatarPickerActivity = this.f$0;
                    switch (i42) {
                        case 0:
                            AvatarPhotoController avatarPhotoController = avatarPickerActivity.mAvatarPhotoController;
                            avatarPhotoController.getClass();
                            Intent intent2 = new Intent("android.intent.action.GET_CONTENT", (Uri) null);
                            intent2.setPackage("com.sec.android.gallery3d");
                            intent2.setType("image/*");
                            ((AvatarPhotoController.AvatarUiImpl) avatarPhotoController.mAvatarUi).mActivity.startActivityForResult(intent2, 1001);
                            return;
                        case 1:
                            AvatarPhotoController avatarPhotoController2 = avatarPickerActivity.mAvatarPhotoController;
                            avatarPhotoController2.getClass();
                            Intent intent22 = new Intent("android.media.action.IMAGE_CAPTURE_SECURE");
                            Uri uri = avatarPhotoController2.mTakePictureUri;
                            intent22.putExtra("output", uri);
                            intent22.addFlags(3);
                            intent22.setClipData(ClipData.newRawUri("output", uri));
                            ((AvatarPhotoController.AvatarUiImpl) avatarPhotoController2.mAvatarUi).mActivity.startActivityForResult(intent22, 1002);
                            return;
                        case 2:
                            AvatarPickerActivity.AvatarAdapter avatarAdapter2 = avatarPickerActivity.mAdapter;
                            int i5 = avatarAdapter2.mSelectedPosition;
                            int length = avatarAdapter2.mPreselectedImages.length();
                            AvatarPickerActivity avatarPickerActivity2 = AvatarPickerActivity.this;
                            if (length <= 0) {
                                int i6 = avatarAdapter2.mUserIconColors[i5];
                                avatarPickerActivity2.getClass();
                                Intent intent3 = new Intent();
                                intent3.putExtra("default_icon_tint_color", i6);
                                avatarPickerActivity2.setResult(-1, intent3);
                                avatarPickerActivity2.finish();
                                return;
                            }
                            int resourceId = avatarAdapter2.mPreselectedImages.getResourceId(i5, -1);
                            if (resourceId == -1) {
                                throw new IllegalStateException("Preselected avatar images must be resources.");
                            }
                            Uri uriBuild = new Uri.Builder().scheme("android.resource").authority(avatarPickerActivity2.getResources().getResourcePackageName(resourceId)).appendPath(avatarPickerActivity2.getResources().getResourceTypeName(resourceId)).appendPath(avatarPickerActivity2.getResources().getResourceEntryName(resourceId)).build();
                            Intent intent4 = new Intent();
                            intent4.setData(uriBuild);
                            avatarPickerActivity2.setResult(-1, intent4);
                            avatarPickerActivity2.finish();
                            return;
                        default:
                            int i7 = AvatarPickerActivity.$r8$clinit;
                            avatarPickerActivity.cancel$1$1();
                            return;
                    }
                }
            });
        }
        LinearLayout linearLayout2 = (LinearLayout) findViewById(R.id.camera_btn);
        if (linearLayout2 != null && objArr != false) {
            linearLayout2.setVisibility(0);
            final char c2 = c == true ? 1 : 0;
            linearLayout2.setOnClickListener(new View.OnClickListener(this) { // from class: com.android.settingslib.avatarpicker.AvatarPickerActivity$$ExternalSyntheticLambda1
                public final /* synthetic */ AvatarPickerActivity f$0;

                {
                    this.f$0 = this;
                }

                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    int i42 = c2;
                    AvatarPickerActivity avatarPickerActivity = this.f$0;
                    switch (i42) {
                        case 0:
                            AvatarPhotoController avatarPhotoController = avatarPickerActivity.mAvatarPhotoController;
                            avatarPhotoController.getClass();
                            Intent intent2 = new Intent("android.intent.action.GET_CONTENT", (Uri) null);
                            intent2.setPackage("com.sec.android.gallery3d");
                            intent2.setType("image/*");
                            ((AvatarPhotoController.AvatarUiImpl) avatarPhotoController.mAvatarUi).mActivity.startActivityForResult(intent2, 1001);
                            return;
                        case 1:
                            AvatarPhotoController avatarPhotoController2 = avatarPickerActivity.mAvatarPhotoController;
                            avatarPhotoController2.getClass();
                            Intent intent22 = new Intent("android.media.action.IMAGE_CAPTURE_SECURE");
                            Uri uri = avatarPhotoController2.mTakePictureUri;
                            intent22.putExtra("output", uri);
                            intent22.addFlags(3);
                            intent22.setClipData(ClipData.newRawUri("output", uri));
                            ((AvatarPhotoController.AvatarUiImpl) avatarPhotoController2.mAvatarUi).mActivity.startActivityForResult(intent22, 1002);
                            return;
                        case 2:
                            AvatarPickerActivity.AvatarAdapter avatarAdapter2 = avatarPickerActivity.mAdapter;
                            int i5 = avatarAdapter2.mSelectedPosition;
                            int length = avatarAdapter2.mPreselectedImages.length();
                            AvatarPickerActivity avatarPickerActivity2 = AvatarPickerActivity.this;
                            if (length <= 0) {
                                int i6 = avatarAdapter2.mUserIconColors[i5];
                                avatarPickerActivity2.getClass();
                                Intent intent3 = new Intent();
                                intent3.putExtra("default_icon_tint_color", i6);
                                avatarPickerActivity2.setResult(-1, intent3);
                                avatarPickerActivity2.finish();
                                return;
                            }
                            int resourceId = avatarAdapter2.mPreselectedImages.getResourceId(i5, -1);
                            if (resourceId == -1) {
                                throw new IllegalStateException("Preselected avatar images must be resources.");
                            }
                            Uri uriBuild = new Uri.Builder().scheme("android.resource").authority(avatarPickerActivity2.getResources().getResourcePackageName(resourceId)).appendPath(avatarPickerActivity2.getResources().getResourceTypeName(resourceId)).appendPath(avatarPickerActivity2.getResources().getResourceEntryName(resourceId)).build();
                            Intent intent4 = new Intent();
                            intent4.setData(uriBuild);
                            avatarPickerActivity2.setResult(-1, intent4);
                            avatarPickerActivity2.finish();
                            return;
                        default:
                            int i7 = AvatarPickerActivity.$r8$clinit;
                            avatarPickerActivity.cancel$1$1();
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
        String stringExtra = getIntent().getStringExtra("file_authority");
        if (stringExtra == null) {
            Log.e(getClass().getName(), "File authority must be provided");
            finish();
        }
        this.mAvatarPhotoController = new AvatarPhotoController(avatarUiImpl, new AvatarPhotoController.ContextInjectorImpl(this, stringExtra), this.mWaitingForActivityResult);
    }

    @Override // android.app.Activity
    public final boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == 16908332) {
            cancel$1$1();
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

    @Override // android.app.Activity
    public final void startActivityForResult(Intent intent, int i) {
        this.mWaitingForActivityResult = true;
        super.startActivityForResult(intent, i);
    }
}
