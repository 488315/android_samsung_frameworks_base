package com.android.wm.shell.bubbles;

import android.content.Context;
import android.content.pm.ShortcutInfo;
import android.content.res.Resources;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.android.keyguard.KeyguardSecurityContainer$UserSwitcherViewMode$2$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.wm.shell.bubbles.BadgedImageView;
import java.util.List;
import java.util.function.Consumer;

/* loaded from: classes3.dex */
public class BubbleOverflowAdapter extends RecyclerView.Adapter {
    public final List mBubbles;
    public final Context mContext;
    public final BubblePositioner mPositioner;
    public final Consumer mPromoteBubbleFromOverflow;

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final BadgedImageView iconView;
        public final TextView textView;

        public ViewHolder(LinearLayout linearLayout, BubblePositioner bubblePositioner) {
            super(linearLayout);
            BadgedImageView badgedImageView = (BadgedImageView) linearLayout.findViewById(R.id.bubble_view);
            this.iconView = badgedImageView;
            badgedImageView.initialize(bubblePositioner);
            this.textView = (TextView) linearLayout.findViewById(R.id.bubble_view_name);
        }
    }

    public BubbleOverflowAdapter(Context context, List<Bubble> list, Consumer<Bubble> consumer, BubblePositioner bubblePositioner) {
        this.mContext = context;
        this.mBubbles = list;
        this.mPromoteBubbleFromOverflow = consumer;
        this.mPositioner = bubblePositioner;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final int getItemCount() {
        return this.mBubbles.size();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) throws Resources.NotFoundException {
        ViewHolder viewHolder2 = (ViewHolder) viewHolder;
        final Bubble bubble = (Bubble) this.mBubbles.get(i);
        viewHolder2.iconView.setRenderedBubble(bubble);
        BadgedImageView.SuppressionFlag suppressionFlag = BadgedImageView.SuppressionFlag.FLYOUT_VISIBLE;
        BadgedImageView badgedImageView = viewHolder2.iconView;
        badgedImageView.removeDotSuppressionFlag(suppressionFlag);
        badgedImageView.setOnClickListener(new View.OnClickListener() { // from class: com.android.wm.shell.bubbles.BubbleOverflowAdapter$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                BubbleOverflowAdapter bubbleOverflowAdapter = this.f$0;
                Bubble bubble2 = bubble;
                bubbleOverflowAdapter.mBubbles.remove(bubble2);
                bubbleOverflowAdapter.notifyDataSetChanged();
                bubbleOverflowAdapter.mPromoteBubbleFromOverflow.accept(bubble2);
            }
        });
        String string = bubble.mTitle;
        if (string == null) {
            string = this.mContext.getResources().getString(R.string.notification_bubble_title);
        }
        badgedImageView.setContentDescription(this.mContext.getResources().getString(R.string.bubble_content_description_single, string, bubble.mAppName));
        badgedImageView.setAccessibilityDelegate(new View.AccessibilityDelegate() { // from class: com.android.wm.shell.bubbles.BubbleOverflowAdapter.1
            @Override // android.view.View.AccessibilityDelegate
            public final void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfo accessibilityNodeInfo) {
                super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
                accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(16, BubbleOverflowAdapter.this.mContext.getResources().getString(R.string.bubble_accessibility_action_add_back)));
            }
        });
        ShortcutInfo shortcutInfo = bubble.mShortcutInfo;
        viewHolder2.textView.setText(shortcutInfo != null ? shortcutInfo.getLabel() : bubble.mAppName);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder((LinearLayout) KeyguardSecurityContainer$UserSwitcherViewMode$2$$ExternalSyntheticOutline0.m(viewGroup, R.layout.sec_bubble_overflow_view, viewGroup, false), this.mPositioner);
    }
}
