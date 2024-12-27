package android.service.quickaccesswallet;

public interface GetWalletCardsCallback {
    void onFailure(GetWalletCardsError getWalletCardsError);

    void onSuccess(GetWalletCardsResponse getWalletCardsResponse);
}
