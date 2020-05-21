package ro.uvt.dp.client.account;
import ro.uvt.dp.client.account.Account.TYPE;

public class AccountFactory {
	public static Account createAccount(TYPE curr, double sum) throws Exception {
		if (curr == Account.TYPE.EUR) {
			return new AccountEUR(sum);
		}
		else if (curr == Account.TYPE.RON) {
			return new AccountRON(sum);
		}
		else
			return null;
	}
}
