package accountdeclare;

public class Account {
	private int accNo;
	private String accountHolder;
	private short pinNumber = 1234;
	private int balance;
	private int wrongCount=2;
	private boolean status = true;
	
	public int getAccNo() {
		return accNo;
	}
	public void setAccNo(int accNo) {
		this.accNo = accNo;
	}
	public String getAccountHolder() {
		return accountHolder;
	}
	public void setAccountHolder(String accountHolder) {
		this.accountHolder = accountHolder;
	}
	public short getPinNumber() {
		return pinNumber;
	}
	public void setPinNumber(short pinNumber) {
		this.pinNumber = pinNumber;
	}
	public int getBalance() {
		return balance;
	}
	public void setBalance(int balance) {
		this.balance = balance;
	}
	
	public int getWrongCount() {
		return wrongCount;
	}
	public void setWrongCount(int wrongCount) {
		this.wrongCount = wrongCount;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "AccNo=" + accNo + ", accountHolder=" + accountHolder + ", pinNumber=" + pinNumber
				+ ", balance=" + balance + ", wrongCount=" + wrongCount + ", status=" + status;
	}
	
}
