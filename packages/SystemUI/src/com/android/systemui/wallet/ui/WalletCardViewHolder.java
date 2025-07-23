package com.android.systemui.wallet.ui;

import android.view.View;
import android.widget.ImageView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.android.systemui.R;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class WalletCardViewHolder extends RecyclerView.ViewHolder {
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
