package com.android.settingslib.avatarpicker;

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
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.MenuPopupWindow$MenuDropDownListView$$ExternalSyntheticOutline0;
import androidx.core.graphics.drawable.RoundedBitmapDrawable21;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.slice.widget.ActionRow$$ExternalSyntheticOutline0;
import com.android.internal.util.UserIcons;
import com.android.keyguard.KeyguardSecurityContainer$UserSwitcherViewMode$2$$ExternalSyntheticOutline0;
import com.android.settingslib.avatarpicker.AvatarPhotoController;
import com.android.settingslib.avatarpicker.AvatarPickerActivity;
import com.android.systemui.R;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class AvatarPickerActivity extends AppCompatActivity {
    public static final /* synthetic */ int $r8$clinit = 0;
    public AvatarAdapter mAdapter;
    public AvatarPhotoController mAvatarPhotoController;
    public Button mSaveButton;
    public boolean mWaitingForActivityResult;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
                    AvatarPickerActivity.AvatarAdapter avatarAdapter = AvatarPickerActivity.AvatarAdapter.this;
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class AvatarViewHolder extends RecyclerView.ViewHolder {
        public final ImageView mImageView;

        public AvatarViewHolder(View view) {
            super(view);
            this.mImageView = (ImageView) view.findViewById(R.id.avatar_image);
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    public final void initButtons() {
        float f;
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.bottom_button_container);
        Context applicationContext = getApplicationContext();
        WindowManager windowManager = (WindowManager) applicationContext.getSystemService(WindowManager.class);
        if (windowManager != null) {
            Rect rect = new Rect(windowManager.getCurrentWindowMetrics().getBounds());
            float width = rect.width() / applicationContext.getResources().getDisplayMetrics().density;
            f = (width < 589.0f || width > 959.0f || ((float) rect.height()) / applicationContext.getResources().getDisplayMetrics().density < 411.0f) ? width >= 960.0f ? (width - 840.0f) / 2.0f : 10.0f : width * 0.07f;
        } else {
            f = 0.0f;
        }
        int m = ((int) ActionRow$$ExternalSyntheticOutline0.m(applicationContext, 1, f)) - ((int) getApplicationContext().getResources().getDimension(R.dimen.sec_avatar_picker_button_side_padding));
        linearLayout.setPaddingRelative(m, linearLayout.getPaddingTop(), m, linearLayout.getPaddingBottom());
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
                AvatarPickerActivity avatarPickerActivity = AvatarPickerActivity.this;
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
                avatarPickerActivity.initButtons();
            }
        }, 100L);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:53:0x00c7  */
    /* JADX WARN: Removed duplicated region for block: B:54:0x00cb  */
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onCreate(android.os.Bundle r9) {
        /*
            Method dump skipped, instructions count: 560
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.settingslib.avatarpicker.AvatarPickerActivity.onCreate(android.os.Bundle):void");
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
