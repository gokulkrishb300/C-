package accountdeclare;

public class Transaction {
	private int transactionNo;
	private String description;
	private String transferType;
	private int amount;
	private int closingBalance;
	public int getTransactionNo() {
		return transactionNo;
	}
	public void setTransactionNo(int transactionNo) {
		this.transactionNo = transactionNo;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getTransferType() {
		return transferType;
	}
	public void setTransferType(String transferType) {
		this.transferType = transferType;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public int getClosingBalance() {
		return closingBalance;
	}
	public void setClosingBalance(int closingBalance) {
		this.closingBalance = closingBalance;
	}
	@Override
	public String toString() {
		return "Transaction [transactionNo=" + transactionNo + ", description=" + description + ", transferType="
				+ transferType + ", amount=" + amount + ", closingBalance=" + closingBalance + "]";
	}
	
}
