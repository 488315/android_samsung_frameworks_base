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
import com.android.systemui.R$styleable;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class SecSeekBarWithIconButtonsView extends LinearLayout {
    public final ImageView mIconEnd;
    public final ViewGroup mIconEndFrame;
    public final ImageView mIconStart;
    public final ViewGroup mIconStartFrame;
    public final SeekBarChangeListener mSeekBarListener;
    public final SeslSeekBar mSeekbar;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class SeekBarChangeListener implements SeslSeekBar.OnSeekBarChangeListener {
        public SeslSeekBar.OnSeekBarChangeListener mOnSeekBarChangeListener;

        public /* synthetic */ SeekBarChangeListener(SecSeekBarWithIconButtonsView secSeekBarWithIconButtonsView, int i) {
            this();
        }

        @Override // androidx.appcompat.widget.SeslSeekBar.OnSeekBarChangeListener
        public final void onProgressChanged(SeslSeekBar seslSeekBar, int i, boolean z) {
            SecSeekBarWithIconButtonsView secSeekBarWithIconButtonsView = SecSeekBarWithIconButtonsView.this;
            secSeekBarWithIconButtonsView.getClass();
            SeslSeekBar.OnSeekBarChangeListener onSeekBarChangeListener = this.mOnSeekBarChangeListener;
            if (onSeekBarChangeListener != null) {
                onSeekBarChangeListener.onProgressChanged(seslSeekBar, i, z);
            }
            SecSeekBarWithIconButtonsView.setIconViewAndFrameEnabled(secSeekBarWithIconButtonsView.mIconStart, i > 0);
            SecSeekBarWithIconButtonsView.setIconViewAndFrameEnabled(secSeekBarWithIconButtonsView.mIconEnd, i < secSeekBarWithIconButtonsView.mSeekbar.getMax());
        }

        @Override // androidx.appcompat.widget.SeslSeekBar.OnSeekBarChangeListener
        public final void onStartTrackingTouch(SeslSeekBar seslSeekBar) {
            SeslSeekBar.OnSeekBarChangeListener onSeekBarChangeListener = this.mOnSeekBarChangeListener;
            if (onSeekBarChangeListener != null) {
                onSeekBarChangeListener.onStartTrackingTouch(seslSeekBar);
            }
        }

        @Override // androidx.appcompat.widget.SeslSeekBar.OnSeekBarChangeListener
        public final void onStopTrackingTouch(SeslSeekBar seslSeekBar) {
            SeslSeekBar.OnSeekBarChangeListener onSeekBarChangeListener = this.mOnSeekBarChangeListener;
            if (onSeekBarChangeListener != null) {
                onSeekBarChangeListener.onStopTrackingTouch(seslSeekBar);
            }
        }

        private SeekBarChangeListener() {
            this.mOnSeekBarChangeListener = null;
        }
    }

    public static /* synthetic */ void $r8$lambda$62gLd_NovLEjP4K_OGyLwmWACGo(SecSeekBarWithIconButtonsView secSeekBarWithIconButtonsView) {
        int progress = secSeekBarWithIconButtonsView.mSeekbar.getProgress();
        if (progress > 0) {
            secSeekBarWithIconButtonsView.mSeekbar.setProgress(progress - 1);
            setIconViewAndFrameEnabled(secSeekBarWithIconButtonsView.mIconStart, secSeekBarWithIconButtonsView.mSeekbar.getProgress() > 0);
            A11yLogger.insertLog(((LinearLayout) secSeekBarWithIconButtonsView).mContext, "A11Y3196");
        }
    }

    public static /* synthetic */ void $r8$lambda$CCVuGNT7BndHdXfvrKiI38GDFEY(SecSeekBarWithIconButtonsView secSeekBarWithIconButtonsView) {
        int progress = secSeekBarWithIconButtonsView.mSeekbar.getProgress();
        if (progress < secSeekBarWithIconButtonsView.mSeekbar.getMax()) {
            secSeekBarWithIconButtonsView.mSeekbar.setProgress(progress + 1);
            setIconViewAndFrameEnabled(secSeekBarWithIconButtonsView.mIconEnd, secSeekBarWithIconButtonsView.mSeekbar.getProgress() < secSeekBarWithIconButtonsView.mSeekbar.getMax());
            A11yLogger.insertLog(((LinearLayout) secSeekBarWithIconButtonsView).mContext, "A11Y3198");
        }
    }

    public SecSeekBarWithIconButtonsView(Context context) {
        this(context, null);
    }

    public static void setIconViewAndFrameEnabled(View view, boolean z) {
        view.setEnabled(z);
        ((ViewGroup) view.getParent()).setEnabled(z);
    }

    public final void setSeekbarStateDescription(float f) {
        this.mSeekbar.setStateDescription(((LinearLayout) this).mContext.getResources().getString(R.string.font_scale_percentage, Integer.valueOf(((int) f) * 100)));
    }

    public SecSeekBarWithIconButtonsView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public SecSeekBarWithIconButtonsView(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public SecSeekBarWithIconButtonsView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        final int i3 = 0;
        SeekBarChangeListener seekBarChangeListener = new SeekBarChangeListener(this, i3);
        this.mSeekBarListener = seekBarChangeListener;
        final int i4 = 1;
        LayoutInflater.from(context).inflate(R.layout.sec_seekbar_with_icon_buttons, (ViewGroup) this, true);
        ViewGroup viewGroup = (ViewGroup) findViewById(R.id.icon_start_frame);
        this.mIconStartFrame = viewGroup;
        ViewGroup viewGroup2 = (ViewGroup) findViewById(R.id.icon_end_frame);
        this.mIconEndFrame = viewGroup2;
        ImageView imageView = (ImageView) findViewById(R.id.icon_start);
        this.mIconStart = imageView;
        ImageView imageView2 = (ImageView) findViewById(R.id.icon_end);
        this.mIconEnd = imageView2;
        SeslSeekBar seslSeekBar = (SeslSeekBar) findViewById(R.id.seekbar);
        this.mSeekbar = seslSeekBar;
        if (attributeSet != null) {
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.SeekBarWithIconButtonsView_Layout, i, i2);
            int i5 = obtainStyledAttributes.getInt(2, 7);
            int i6 = obtainStyledAttributes.getInt(3, 0);
            seslSeekBar.setMax(i5);
            seslSeekBar.setProgress(i6);
            setIconViewAndFrameEnabled(imageView, i6 > 0);
            setIconViewAndFrameEnabled(imageView2, i6 < seslSeekBar.getMax());
            seslSeekBar.setMode(8);
            int resourceId = obtainStyledAttributes.getResourceId(1, 0);
            int resourceId2 = obtainStyledAttributes.getResourceId(0, 0);
            if (resourceId != 0) {
                viewGroup.setContentDescription(context.getString(resourceId));
            }
            if (resourceId2 != 0) {
                viewGroup2.setContentDescription(context.getString(resourceId2));
            }
        } else {
            seslSeekBar.setMax(7);
            seslSeekBar.setProgress(0);
            setIconViewAndFrameEnabled(imageView, false);
            setIconViewAndFrameEnabled(imageView2, seslSeekBar.getMax() > 0);
        }
        seslSeekBar.mOnSeekBarChangeListener = seekBarChangeListener;
        viewGroup.setOnClickListener(new View.OnClickListener(this) { // from class: com.android.systemui.accessibility.SecSeekBarWithIconButtonsView$$ExternalSyntheticLambda0
            public final /* synthetic */ SecSeekBarWithIconButtonsView f$0;

            {
                this.f$0 = this;
            }

            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                switch (i3) {
                    case 0:
                        SecSeekBarWithIconButtonsView.$r8$lambda$62gLd_NovLEjP4K_OGyLwmWACGo(this.f$0);
                        break;
                    default:
                        SecSeekBarWithIconButtonsView.$r8$lambda$CCVuGNT7BndHdXfvrKiI38GDFEY(this.f$0);
                        break;
                }
            }
        });
        viewGroup2.setOnClickListener(new View.OnClickListener(this) { // from class: com.android.systemui.accessibility.SecSeekBarWithIconButtonsView$$ExternalSyntheticLambda0
            public final /* synthetic */ SecSeekBarWithIconButtonsView f$0;

            {
                this.f$0 = this;
            }

            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                switch (i4) {
                    case 0:
                        SecSeekBarWithIconButtonsView.$r8$lambda$62gLd_NovLEjP4K_OGyLwmWACGo(this.f$0);
                        break;
                    default:
                        SecSeekBarWithIconButtonsView.$r8$lambda$CCVuGNT7BndHdXfvrKiI38GDFEY(this.f$0);
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
