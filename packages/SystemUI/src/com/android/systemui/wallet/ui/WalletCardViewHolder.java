package com.android.systemui.wallet.ui;

import android.view.View;
import android.widget.ImageView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.android.systemui.R;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
