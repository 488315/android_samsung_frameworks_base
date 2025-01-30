package com.facebook.rebound.p042ui;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import com.facebook.rebound.OrigamiValueConverter;
import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringConfig;
import com.facebook.rebound.SpringConfigRegistry;
import com.facebook.rebound.SpringListener;
import com.facebook.rebound.SpringSystem;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class SpringConfiguratorView extends FrameLayout {
    public static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#.#");
    public TextView mFrictionLabel;
    public SeekBar mFrictionSeekBar;
    public final float mRevealPx;
    public final Spring mRevealerSpring;
    public SpringConfig mSelectedSpringConfig;
    public final List mSpringConfigs;
    public Spinner mSpringSelectorSpinner;
    public final float mStashPx;
    public TextView mTensionLabel;
    public SeekBar mTensionSeekBar;
    public final int mTextColor;
    public final SpinnerAdapter spinnerAdapter;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class OnNubTouchListener implements View.OnTouchListener {
        private OnNubTouchListener() {
        }

        @Override // android.view.View.OnTouchListener
        public final boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() != 0) {
                return true;
            }
            Spring spring = SpringConfiguratorView.this.mRevealerSpring;
            spring.setEndValue(spring.mEndValue == 1.0d ? 0.0d : 1.0d);
            return true;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class SpinnerAdapter extends BaseAdapter {
        public final Context mContext;
        public final List mStrings = new ArrayList();

        public SpinnerAdapter(Context context) {
            this.mContext = context;
        }

        @Override // android.widget.Adapter
        public final int getCount() {
            return ((ArrayList) this.mStrings).size();
        }

        @Override // android.widget.Adapter
        public final Object getItem(int i) {
            return ((ArrayList) this.mStrings).get(i);
        }

        @Override // android.widget.Adapter
        public final long getItemId(int i) {
            return i;
        }

        @Override // android.widget.Adapter
        public final View getView(int i, View view, ViewGroup viewGroup) {
            TextView textView;
            if (view == null) {
                textView = new TextView(this.mContext);
                textView.setLayoutParams(new AbsListView.LayoutParams(-1, -1));
                int dpToPx = Util.dpToPx(12.0f, SpringConfiguratorView.this.getResources());
                textView.setPadding(dpToPx, dpToPx, dpToPx, dpToPx);
                textView.setTextColor(SpringConfiguratorView.this.mTextColor);
            } else {
                textView = (TextView) view;
            }
            textView.setText((CharSequence) ((ArrayList) this.mStrings).get(i));
            return textView;
        }
    }

    public SpringConfiguratorView(Context context) {
        this(context, null);
    }

    public SpringConfiguratorView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public SpringConfiguratorView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        ArrayList arrayList = new ArrayList();
        this.mSpringConfigs = arrayList;
        int argb = Color.argb(255, IKnoxCustomManager.Stub.TRANSACTION_getUsbConnectionType, IKnoxCustomManager.Stub.TRANSACTION_getUsbConnectionType, IKnoxCustomManager.Stub.TRANSACTION_getUsbConnectionType);
        this.mTextColor = argb;
        SpringSystem create = SpringSystem.create();
        SpringConfigRegistry springConfigRegistry = SpringConfigRegistry.INSTANCE;
        SpinnerAdapter spinnerAdapter = new SpinnerAdapter(context);
        this.spinnerAdapter = spinnerAdapter;
        Resources resources = getResources();
        this.mRevealPx = Util.dpToPx(40.0f, resources);
        this.mStashPx = Util.dpToPx(280.0f, resources);
        Spring createSpring = create.createSpring();
        this.mRevealerSpring = createSpring;
        RevealerSpringListener revealerSpringListener = new RevealerSpringListener();
        createSpring.setCurrentValue(1.0d);
        createSpring.setEndValue(1.0d);
        createSpring.addListener(revealerSpringListener);
        Resources resources2 = getResources();
        int dpToPx = Util.dpToPx(5.0f, resources2);
        int dpToPx2 = Util.dpToPx(10.0f, resources2);
        int dpToPx3 = Util.dpToPx(20.0f, resources2);
        TableLayout.LayoutParams layoutParams = new TableLayout.LayoutParams(0, -2, 1.0f);
        layoutParams.setMargins(0, 0, dpToPx, 0);
        FrameLayout frameLayout = new FrameLayout(context);
        frameLayout.setLayoutParams(new FrameLayout.LayoutParams(-1, Util.dpToPx(300.0f, resources2)));
        FrameLayout frameLayout2 = new FrameLayout(context);
        FrameLayout.LayoutParams layoutParams2 = new FrameLayout.LayoutParams(-1, -1);
        layoutParams2.setMargins(0, dpToPx3, 0, 0);
        frameLayout2.setLayoutParams(layoutParams2);
        frameLayout2.setBackgroundColor(Color.argb(100, 0, 0, 0));
        frameLayout.addView(frameLayout2);
        this.mSpringSelectorSpinner = new Spinner(context, 0);
        FrameLayout.LayoutParams layoutParams3 = new FrameLayout.LayoutParams(-1, -2);
        layoutParams3.gravity = 48;
        layoutParams3.setMargins(dpToPx2, dpToPx2, dpToPx2, 0);
        this.mSpringSelectorSpinner.setLayoutParams(layoutParams3);
        frameLayout2.addView(this.mSpringSelectorSpinner);
        LinearLayout linearLayout = new LinearLayout(context);
        FrameLayout.LayoutParams layoutParams4 = new FrameLayout.LayoutParams(-1, -2);
        layoutParams4.setMargins(0, 0, 0, Util.dpToPx(80.0f, resources2));
        layoutParams4.gravity = 80;
        linearLayout.setLayoutParams(layoutParams4);
        linearLayout.setOrientation(1);
        frameLayout2.addView(linearLayout);
        LinearLayout linearLayout2 = new LinearLayout(context);
        FrameLayout.LayoutParams layoutParams5 = new FrameLayout.LayoutParams(-1, -2);
        layoutParams5.setMargins(dpToPx2, dpToPx2, dpToPx2, dpToPx3);
        linearLayout2.setPadding(dpToPx2, dpToPx2, dpToPx2, dpToPx2);
        linearLayout2.setLayoutParams(layoutParams5);
        linearLayout2.setOrientation(0);
        linearLayout.addView(linearLayout2);
        SeekBar seekBar = new SeekBar(context);
        this.mTensionSeekBar = seekBar;
        seekBar.setLayoutParams(layoutParams);
        linearLayout2.addView(this.mTensionSeekBar);
        TextView textView = new TextView(getContext());
        this.mTensionLabel = textView;
        textView.setTextColor(argb);
        FrameLayout.LayoutParams layoutParams6 = new FrameLayout.LayoutParams(Util.dpToPx(50.0f, resources2), -1);
        this.mTensionLabel.setGravity(19);
        this.mTensionLabel.setLayoutParams(layoutParams6);
        this.mTensionLabel.setMaxLines(1);
        linearLayout2.addView(this.mTensionLabel);
        LinearLayout linearLayout3 = new LinearLayout(context);
        FrameLayout.LayoutParams layoutParams7 = new FrameLayout.LayoutParams(-1, -2);
        layoutParams7.setMargins(dpToPx2, dpToPx2, dpToPx2, dpToPx3);
        linearLayout3.setPadding(dpToPx2, dpToPx2, dpToPx2, dpToPx2);
        linearLayout3.setLayoutParams(layoutParams7);
        linearLayout3.setOrientation(0);
        linearLayout.addView(linearLayout3);
        SeekBar seekBar2 = new SeekBar(context);
        this.mFrictionSeekBar = seekBar2;
        seekBar2.setLayoutParams(layoutParams);
        linearLayout3.addView(this.mFrictionSeekBar);
        TextView textView2 = new TextView(getContext());
        this.mFrictionLabel = textView2;
        textView2.setTextColor(argb);
        FrameLayout.LayoutParams layoutParams8 = new FrameLayout.LayoutParams(Util.dpToPx(50.0f, resources2), -1);
        this.mFrictionLabel.setGravity(19);
        this.mFrictionLabel.setLayoutParams(layoutParams8);
        this.mFrictionLabel.setMaxLines(1);
        linearLayout3.addView(this.mFrictionLabel);
        View view = new View(context);
        FrameLayout.LayoutParams layoutParams9 = new FrameLayout.LayoutParams(Util.dpToPx(60.0f, resources2), Util.dpToPx(40.0f, resources2));
        layoutParams9.gravity = 49;
        view.setLayoutParams(layoutParams9);
        view.setOnTouchListener(new OnNubTouchListener());
        view.setBackgroundColor(Color.argb(255, 0, 164, IKnoxCustomManager.Stub.TRANSACTION_getVibrationIntensity));
        frameLayout.addView(view);
        addView(frameLayout);
        SeekbarListener seekbarListener = new SeekbarListener();
        this.mTensionSeekBar.setMax(100000);
        this.mTensionSeekBar.setOnSeekBarChangeListener(seekbarListener);
        this.mFrictionSeekBar.setMax(100000);
        this.mFrictionSeekBar.setOnSeekBarChangeListener(seekbarListener);
        this.mSpringSelectorSpinner.setAdapter((android.widget.SpinnerAdapter) spinnerAdapter);
        this.mSpringSelectorSpinner.setOnItemSelectedListener(new SpringSelectedListener());
        Map unmodifiableMap = Collections.unmodifiableMap(springConfigRegistry.mSpringConfigMap);
        ((ArrayList) spinnerAdapter.mStrings).clear();
        spinnerAdapter.notifyDataSetChanged();
        arrayList.clear();
        for (Map.Entry entry : unmodifiableMap.entrySet()) {
            if (entry.getKey() != SpringConfig.defaultConfig) {
                ((ArrayList) this.mSpringConfigs).add(entry.getKey());
                SpinnerAdapter spinnerAdapter2 = this.spinnerAdapter;
                ((ArrayList) spinnerAdapter2.mStrings).add((String) entry.getValue());
                spinnerAdapter2.notifyDataSetChanged();
            }
        }
        List list = this.mSpringConfigs;
        SpringConfig springConfig = SpringConfig.defaultConfig;
        ((ArrayList) list).add(springConfig);
        SpinnerAdapter spinnerAdapter3 = this.spinnerAdapter;
        ((ArrayList) spinnerAdapter3.mStrings).add((String) unmodifiableMap.get(springConfig));
        spinnerAdapter3.notifyDataSetChanged();
        this.spinnerAdapter.notifyDataSetChanged();
        if (((ArrayList) this.mSpringConfigs).size() > 0) {
            this.mSpringSelectorSpinner.setSelection(0);
        }
        setTranslationY(this.mStashPx);
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class RevealerSpringListener implements SpringListener {
        private RevealerSpringListener() {
        }

        @Override // com.facebook.rebound.SpringListener
        public final void onSpringUpdate(Spring spring) {
            float f = (float) spring.mCurrentState.position;
            SpringConfiguratorView springConfiguratorView = SpringConfiguratorView.this;
            float f2 = springConfiguratorView.mRevealPx;
            springConfiguratorView.setTranslationY(((springConfiguratorView.mStashPx - f2) * f) + f2);
        }

        @Override // com.facebook.rebound.SpringListener
        public final void onSpringActivate(Spring spring) {
        }

        @Override // com.facebook.rebound.SpringListener
        public final void onSpringAtRest(Spring spring) {
        }

        @Override // com.facebook.rebound.SpringListener
        public final void onSpringEndStateChange(Spring spring) {
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class SeekbarListener implements SeekBar.OnSeekBarChangeListener {
        private SeekbarListener() {
        }

        @Override // android.widget.SeekBar.OnSeekBarChangeListener
        public final void onProgressChanged(SeekBar seekBar, int i, boolean z) {
            SpringConfiguratorView springConfiguratorView = SpringConfiguratorView.this;
            if (seekBar == springConfiguratorView.mTensionSeekBar) {
                double d = ((i * 200.0f) / 100000.0f) + 0.0f;
                springConfiguratorView.mSelectedSpringConfig.tension = OrigamiValueConverter.tensionFromOrigamiValue(d);
                String format = SpringConfiguratorView.DECIMAL_FORMAT.format(d);
                SpringConfiguratorView.this.mTensionLabel.setText("T:" + format);
            }
            SpringConfiguratorView springConfiguratorView2 = SpringConfiguratorView.this;
            if (seekBar == springConfiguratorView2.mFrictionSeekBar) {
                double d2 = ((i * 50.0f) / 100000.0f) + 0.0f;
                springConfiguratorView2.mSelectedSpringConfig.friction = OrigamiValueConverter.frictionFromOrigamiValue(d2);
                String format2 = SpringConfiguratorView.DECIMAL_FORMAT.format(d2);
                SpringConfiguratorView.this.mFrictionLabel.setText("F:" + format2);
            }
        }

        @Override // android.widget.SeekBar.OnSeekBarChangeListener
        public final void onStartTrackingTouch(SeekBar seekBar) {
        }

        @Override // android.widget.SeekBar.OnSeekBarChangeListener
        public final void onStopTrackingTouch(SeekBar seekBar) {
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class SpringSelectedListener implements AdapterView.OnItemSelectedListener {
        private SpringSelectedListener() {
        }

        @Override // android.widget.AdapterView.OnItemSelectedListener
        public final void onItemSelected(AdapterView adapterView, View view, int i, long j) {
            SpringConfiguratorView springConfiguratorView = SpringConfiguratorView.this;
            springConfiguratorView.mSelectedSpringConfig = (SpringConfig) ((ArrayList) springConfiguratorView.mSpringConfigs).get(i);
            SpringConfiguratorView springConfiguratorView2 = SpringConfiguratorView.this;
            SpringConfig springConfig = springConfiguratorView2.mSelectedSpringConfig;
            springConfiguratorView2.getClass();
            double d = springConfig.tension;
            int round = Math.round(((((float) (d == 0.0d ? 0.0d : ((d - 194.0d) / 3.62d) + 30.0d)) - 0.0f) * 100000.0f) / 200.0f);
            double d2 = springConfig.friction;
            int round2 = Math.round(((((float) (d2 != 0.0d ? 8.0d + ((d2 - 25.0d) / 3.0d) : 0.0d)) - 0.0f) * 100000.0f) / 50.0f);
            springConfiguratorView2.mTensionSeekBar.setProgress(round);
            springConfiguratorView2.mFrictionSeekBar.setProgress(round2);
        }

        @Override // android.widget.AdapterView.OnItemSelectedListener
        public final void onNothingSelected(AdapterView adapterView) {
        }
    }
}
