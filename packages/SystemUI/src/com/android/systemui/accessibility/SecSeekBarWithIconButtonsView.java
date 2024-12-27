package com.android.systemui.accessibility;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.A11yLogger;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import androidx.appcompat.widget.SeslSeekBar;
import com.android.systemui.R;
import com.android.systemui.accessibility.MagnificationSettingsController;
import com.android.systemui.accessibility.WindowMagnificationSettings;
import com.android.systemui.res.R$styleable;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public class SecSeekBarWithIconButtonsView extends LinearLayout {
    public final ImageView mIconEnd;
    public final ViewGroup mIconEndFrame;
    public final ImageView mIconStart;
    public final ViewGroup mIconStartFrame;
    public final int mSeekBarChangeMagnitude;
    public final SeekBarChangeListener mSeekBarListener;
    public final SeslSeekBar mSeekbar;
    public boolean mSetProgressFromButtonFlag;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class AccessibilityDelegate extends View.AccessibilityDelegate {
        public /* synthetic */ AccessibilityDelegate(SecSeekBarWithIconButtonsView secSeekBarWithIconButtonsView, int i) {
            this();
        }

        @Override // android.view.View.AccessibilityDelegate
        public final void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfo accessibilityNodeInfo) {
            super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
            if (view.getId() == R.id.seekbar) {
                accessibilityNodeInfo.setClassName(SeekBar.class.getName());
                accessibilityNodeInfo.setContentDescription(((LinearLayout) SecSeekBarWithIconButtonsView.this).mContext.getString(R.string.accessibility_magnification_zoom));
            } else if (view.getId() == R.id.icon_start_frame || view.getId() == R.id.icon_end_frame) {
                accessibilityNodeInfo.setClassName(Button.class.getName());
            }
        }

        private AccessibilityDelegate() {
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public interface OnSeekBarWithIconButtonsChangeListener extends SeslSeekBar.OnSeekBarChangeListener {
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class SeekBarChangeListener implements SeslSeekBar.OnSeekBarChangeListener {
        public OnSeekBarWithIconButtonsChangeListener mOnSeekBarChangeListener;

        public /* synthetic */ SeekBarChangeListener(SecSeekBarWithIconButtonsView secSeekBarWithIconButtonsView, int i) {
            this();
        }

        @Override // androidx.appcompat.widget.SeslSeekBar.OnSeekBarChangeListener
        public final void onProgressChanged(SeslSeekBar seslSeekBar, int i, boolean z) {
            SecSeekBarWithIconButtonsView secSeekBarWithIconButtonsView = SecSeekBarWithIconButtonsView.this;
            secSeekBarWithIconButtonsView.getClass();
            OnSeekBarWithIconButtonsChangeListener onSeekBarWithIconButtonsChangeListener = this.mOnSeekBarChangeListener;
            if (onSeekBarWithIconButtonsChangeListener != null) {
                if (secSeekBarWithIconButtonsView.mSetProgressFromButtonFlag) {
                    secSeekBarWithIconButtonsView.mSetProgressFromButtonFlag = false;
                    ((WindowMagnificationSettings.ZoomSeekbarChangeListener) onSeekBarWithIconButtonsChangeListener).onProgressChanged(seslSeekBar, i, true);
                    WindowMagnificationSettings.ZoomSeekbarChangeListener zoomSeekbarChangeListener = (WindowMagnificationSettings.ZoomSeekbarChangeListener) this.mOnSeekBarChangeListener;
                    zoomSeekbarChangeListener.getClass();
                    float progress = seslSeekBar.getProgress();
                    ((MagnificationSettingsController.AnonymousClass1) WindowMagnificationSettings.this.mCallback).onMagnifierScale((progress / r3.mSeekBarMagnitude) + 1.0f, true);
                } else {
                    ((WindowMagnificationSettings.ZoomSeekbarChangeListener) onSeekBarWithIconButtonsChangeListener).onProgressChanged(seslSeekBar, i, z);
                }
            }
            secSeekBarWithIconButtonsView.updateIconViewIfNeeded(i);
        }

        @Override // androidx.appcompat.widget.SeslSeekBar.OnSeekBarChangeListener
        public final void onStopTrackingTouch(SeslSeekBar seslSeekBar) {
            OnSeekBarWithIconButtonsChangeListener onSeekBarWithIconButtonsChangeListener = this.mOnSeekBarChangeListener;
            if (onSeekBarWithIconButtonsChangeListener != null) {
                WindowMagnificationSettings.ZoomSeekbarChangeListener zoomSeekbarChangeListener = (WindowMagnificationSettings.ZoomSeekbarChangeListener) onSeekBarWithIconButtonsChangeListener;
                zoomSeekbarChangeListener.getClass();
                float progress = seslSeekBar.getProgress();
                ((MagnificationSettingsController.AnonymousClass1) WindowMagnificationSettings.this.mCallback).onMagnifierScale((progress / r1.mSeekBarMagnitude) + 1.0f, true);
            }
        }

        private SeekBarChangeListener() {
            this.mOnSeekBarChangeListener = null;
        }

        @Override // androidx.appcompat.widget.SeslSeekBar.OnSeekBarChangeListener
        public final void onStartTrackingTouch() {
        }
    }

    public static void $r8$lambda$NXdfyFJWWSMlxhe0liHePata5KI(SecSeekBarWithIconButtonsView secSeekBarWithIconButtonsView) {
        int progress = secSeekBarWithIconButtonsView.mSeekbar.getProgress();
        if (progress < secSeekBarWithIconButtonsView.mSeekbar.getMax()) {
            int i = progress + secSeekBarWithIconButtonsView.mSeekBarChangeMagnitude;
            secSeekBarWithIconButtonsView.mSetProgressFromButtonFlag = true;
            secSeekBarWithIconButtonsView.mSeekbar.setProgress(i);
            secSeekBarWithIconButtonsView.updateIconViewIfNeeded(secSeekBarWithIconButtonsView.mSeekbar.getProgress());
            A11yLogger.insertLog(((LinearLayout) secSeekBarWithIconButtonsView).mContext, "A11Y3198");
        }
    }

    /* renamed from: $r8$lambda$Ryx6DOIbVKj-7tZSU8RwyATh5A0, reason: not valid java name */
    public static void m881$r8$lambda$Ryx6DOIbVKj7tZSU8RwyATh5A0(SecSeekBarWithIconButtonsView secSeekBarWithIconButtonsView) {
        int progress = secSeekBarWithIconButtonsView.mSeekbar.getProgress();
        if (progress > 0) {
            int i = progress - secSeekBarWithIconButtonsView.mSeekBarChangeMagnitude;
            secSeekBarWithIconButtonsView.mSetProgressFromButtonFlag = true;
            secSeekBarWithIconButtonsView.mSeekbar.setProgress(i);
            secSeekBarWithIconButtonsView.updateIconViewIfNeeded(secSeekBarWithIconButtonsView.mSeekbar.getProgress());
            A11yLogger.insertLog(((LinearLayout) secSeekBarWithIconButtonsView).mContext, "A11Y3196");
        }
    }

    public SecSeekBarWithIconButtonsView(Context context) {
        this(context, null);
    }

    public OnSeekBarWithIconButtonsChangeListener getOnSeekBarWithIconButtonsChangeListener() {
        return this.mSeekBarListener.mOnSeekBarChangeListener;
    }

    public int getProgress() {
        return this.mSeekbar.getProgress();
    }

    public SeslSeekBar getSeekbar() {
        return this.mSeekbar;
    }

    public final void setProgress(int i) {
        this.mSeekbar.setProgress(i);
        updateIconViewIfNeeded(this.mSeekbar.getProgress());
    }

    public final void setSeekbarStateDescription(float f) {
        this.mSeekbar.setStateDescription(((LinearLayout) this).mContext.getResources().getString(R.string.font_scale_percentage, Integer.valueOf(((int) f) * 100)));
    }

    public final void updateIconViewIfNeeded(int i) {
        ImageView imageView = this.mIconStart;
        boolean z = i > 0;
        imageView.setEnabled(z);
        ((ViewGroup) imageView.getParent()).setEnabled(z);
        ImageView imageView2 = this.mIconEnd;
        boolean z2 = i < this.mSeekbar.getMax();
        imageView2.setEnabled(z2);
        ((ViewGroup) imageView2.getParent()).setEnabled(z2);
    }

    public SecSeekBarWithIconButtonsView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public SecSeekBarWithIconButtonsView(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public SecSeekBarWithIconButtonsView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mSeekBarChangeMagnitude = 1;
        int i3 = 0;
        this.mSetProgressFromButtonFlag = false;
        SeekBarChangeListener seekBarChangeListener = new SeekBarChangeListener(this, i3);
        this.mSeekBarListener = seekBarChangeListener;
        LayoutInflater.from(context).inflate(R.layout.sec_seekbar_with_icon_buttons, (ViewGroup) this, true);
        ViewGroup viewGroup = (ViewGroup) findViewById(R.id.icon_start_frame);
        this.mIconStartFrame = viewGroup;
        ViewGroup viewGroup2 = (ViewGroup) findViewById(R.id.icon_end_frame);
        this.mIconEndFrame = viewGroup2;
        this.mIconStart = (ImageView) findViewById(R.id.icon_start);
        this.mIconEnd = (ImageView) findViewById(R.id.icon_end);
        SeslSeekBar seslSeekBar = (SeslSeekBar) findViewById(R.id.seekbar);
        this.mSeekbar = seslSeekBar;
        if (attributeSet != null) {
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.SeekBarWithIconButtonsView_Layout, i, i2);
            int i4 = obtainStyledAttributes.getInt(2, 7);
            int i5 = obtainStyledAttributes.getInt(3, 0);
            seslSeekBar.setMax(i4);
            setProgress(i5);
            seslSeekBar.setMode(8);
            int resourceId = obtainStyledAttributes.getResourceId(1, 0);
            int resourceId2 = obtainStyledAttributes.getResourceId(0, 0);
            if (resourceId != 0) {
                viewGroup.setContentDescription(context.getString(resourceId));
            }
            if (resourceId2 != 0) {
                viewGroup2.setContentDescription(context.getString(resourceId2));
            }
            int resourceId3 = obtainStyledAttributes.getResourceId(5, 0);
            if (resourceId3 != 0) {
                seslSeekBar.setTickMark(getResources().getDrawable(resourceId3));
            }
            this.mSeekBarChangeMagnitude = obtainStyledAttributes.getInt(4, 1);
        } else {
            seslSeekBar.setMax(7);
            setProgress(0);
        }
        seslSeekBar.mOnSeekBarChangeListener = seekBarChangeListener;
        final int i6 = 0;
        viewGroup.setOnClickListener(new View.OnClickListener(this) { // from class: com.android.systemui.accessibility.SecSeekBarWithIconButtonsView$$ExternalSyntheticLambda0
            public final /* synthetic */ SecSeekBarWithIconButtonsView f$0;

            {
                this.f$0 = this;
            }

            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                int i7 = i6;
                SecSeekBarWithIconButtonsView secSeekBarWithIconButtonsView = this.f$0;
                switch (i7) {
                    case 0:
                        SecSeekBarWithIconButtonsView.m881$r8$lambda$Ryx6DOIbVKj7tZSU8RwyATh5A0(secSeekBarWithIconButtonsView);
                        break;
                    default:
                        SecSeekBarWithIconButtonsView.$r8$lambda$NXdfyFJWWSMlxhe0liHePata5KI(secSeekBarWithIconButtonsView);
                        break;
                }
            }
        });
        final int i7 = 1;
        viewGroup2.setOnClickListener(new View.OnClickListener(this) { // from class: com.android.systemui.accessibility.SecSeekBarWithIconButtonsView$$ExternalSyntheticLambda0
            public final /* synthetic */ SecSeekBarWithIconButtonsView f$0;

            {
                this.f$0 = this;
            }

            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                int i72 = i7;
                SecSeekBarWithIconButtonsView secSeekBarWithIconButtonsView = this.f$0;
                switch (i72) {
                    case 0:
                        SecSeekBarWithIconButtonsView.m881$r8$lambda$Ryx6DOIbVKj7tZSU8RwyATh5A0(secSeekBarWithIconButtonsView);
                        break;
                    default:
                        SecSeekBarWithIconButtonsView.$r8$lambda$NXdfyFJWWSMlxhe0liHePata5KI(secSeekBarWithIconButtonsView);
                        break;
                }
            }
        });
        seslSeekBar.setAccessibilityDelegate(new AccessibilityDelegate(this, i3));
        viewGroup.setAccessibilityDelegate(new AccessibilityDelegate(this, i3));
        viewGroup2.setAccessibilityDelegate(new AccessibilityDelegate(this, i3));
        viewGroup.setTooltipText(((LinearLayout) this).mContext.getString(R.string.accessibility_magnification_zoom_out));
        viewGroup2.setTooltipText(((LinearLayout) this).mContext.getString(R.string.accessibility_magnification_zoom_in));
    }
}
