package com.android.systemui.wallet.ui;

import android.view.View;
import android.widget.ImageView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.android.systemui.R;

public final class WalletCardViewHolder extends RecyclerView.ViewHolder {
    public final CardView mCardView;
    public WalletCardViewInfo mCardViewInfo;
    public final ImageView mImageView;

    public WalletCardViewHolder(View view) {
        super(view);
        CardView cardView = (CardView) view.requireViewById(R.id.card);
        this.mCardView = cardView;
        this.mImageView = (ImageView) cardView.requireViewById(R.id.card_image);
    }
}
