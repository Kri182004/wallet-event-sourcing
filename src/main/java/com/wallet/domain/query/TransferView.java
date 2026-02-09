package com.wallet.domain.query;

import java.util.UUID;

public class TransferView {
    private final UUID transferId;
    private final UUID fromWalletId;
    private final UUID toWalletId;
    private final long amount;
    private final String status;
    
    public TransferView(
        UUID tranferId,
        UUID fromWalletId,
        UUID toWalletId,
        long amount,
        String status
    ){
        this.transferId=tranferId;
        this.fromWalletId=fromWalletId;
        this.toWalletId=toWalletId;
        this.amount=amount;
        this.status=status;
    }
    public UUID getTransferId(){
        return transferId;
    }
    public UUID getFromWalletId(){
        return fromWalletId;
    }
    public UUID getToWalletId(){
        return toWalletId;
    }
    public long getAmount(){
        return amount;
    }
    public String getStatus(){
        return status;
    }
}
//one line in the transcation history.
